package io.github.cyborgcabbage.novusexplodus.explosion;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.*;

public class NeoExplosion {
    protected final Random random = new Random();
    protected final World world;
    public double x;
    public double y;
    public double z;
    public Entity cause;
    public float power;
    public HashSet<BlockPos> damagedBlocks = new HashSet<>();

    public NeoExplosion(World world, Entity cause, double x, double y, double z, float power) {
        this.world = world;
        this.cause = cause;
        this.power = power;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void explode(RayProvider rayProvider, float dropChance, boolean destroyBlocks, boolean harmEntities, boolean pushEntities, boolean createParticles, float fireChance, boolean createSound){
        this.explode(rayProvider, dropChance, destroyBlocks, harmEntities,  pushEntities, createParticles, fireChance, createSound, 0,0.3f);
    }

    public void explode(RayProvider rayProvider, float dropChance, boolean destroyBlocks, boolean harmEntities, boolean pushEntities, boolean createParticles, float fireChance, boolean createSound, int replacementBlock, float randomisation){
        if (fireChance > 0.0 || destroyBlocks) this.updateDamagedBlocks(rayProvider,randomisation);
        this.effectEntities(harmEntities, pushEntities);
        if (destroyBlocks) this.destroyBlocks(replacementBlock,dropChance);
        if (fireChance > 0.0) this.createFires(fireChance);
        if (createParticles) this.createParticles();
        if (createSound) this.createSound();
    }
    public void explode() {
        this.explode(new GoldenRayProvider());
    }
    public void explode(RayProvider rayProvider) {
        this.explode(rayProvider,0.3F,true,true,true,true, 0.0f, true);
    }

    public void alternateUpdateDamagedBlocks(RayProvider rayProvider, float randomisation) {
        damagedBlocks.clear();
        ArrayList<ExplosionRay> rays = rayProvider.getRays((int)(this.power*this.power)*8);

        for (ExplosionRay ray: rays) {
            ray.power = ray.multiplier * ray.multiplier * this.power * this.power * 7.5f;
            ray.power *= ((1.0f-randomisation) + this.world.random.nextFloat() * (2.0f*randomisation));
            ray.pos = Vec3d.create(this.x,this.y,this.z);
            ray.distance = 0.3f;
        }
        float step_size = 0.3f;
        int active_ray_count = rays.size();
        float previous_average_blast_resistance = 1.0f;
        while(active_ray_count > 0){
            float total_blast_resistance = 0.0f;
            active_ray_count = 0;
            for (ExplosionRay ray : rays) {
                if (ray.power > 0.3f) {
                    int block_x = MathHelper.floor(ray.pos.x);
                    int block_y = MathHelper.floor(ray.pos.y);
                    int block_z = MathHelper.floor(ray.pos.z);
                    int block_id = this.world.getBlockId(block_x, block_y, block_z);
                    if (block_id > 0) {
                        float blast_resistance = Block.BLOCKS[block_id].getBlastResistance(this.cause) + 0.3F;
                        ray.power -= (blast_resistance + 2.0f * (blast_resistance + previous_average_blast_resistance)) * step_size;
                        total_blast_resistance += blast_resistance;
                    } else {
                        ray.power -= (0.0f + 2.0f * (0.0f + previous_average_blast_resistance)) * step_size;
                    }

                    if (ray.power > 0.0F) {
                        damagedBlocks.add(new BlockPos(block_x, block_y, block_z));
                        active_ray_count++;
                    }

                    ray.pos.x += ray.dir.x * (double) step_size;
                    ray.pos.y += ray.dir.y * (double) step_size;
                    ray.pos.z += ray.dir.z * (double) step_size;

                    ray.power *= Math.pow(ray.distance / (ray.distance + step_size), 2.0); //applies inverse square law incrementally
                    ray.distance += step_size;
                }
            }
            previous_average_blast_resistance = total_blast_resistance/(float)active_ray_count;
        }
    }

    public void effectEntities(boolean harm, boolean push) {
        if (!harm && !push) return;
        double damagingRange = this.power * 2.0;
        int min_x = MathHelper.floor(this.x - damagingRange - 1.0D);
        int max_x = MathHelper.floor(this.x + damagingRange + 1.0D);
        int min_y = MathHelper.floor(this.y - damagingRange - 1.0D);
        int max_y = MathHelper.floor(this.y + damagingRange + 1.0D);
        int min_z = MathHelper.floor(this.z - damagingRange - 1.0D);
        int max_z = MathHelper.floor(this.z + damagingRange + 1.0D);
        List inBounds = this.world.getEntities(this.cause, Box.create(min_x,  min_y,  min_z,  max_x,  max_y,  max_z));
        Vec3d explosionOrigin = Vec3d.create(this.x, this.y, this.z);

        for (Object inBound : inBounds) {
            Entity entity = (Entity) inBound;
            double distanceFraction = entity.getDistance(this.x, this.y, this.z) / damagingRange;
            if (distanceFraction <= 1.0D) { //Is distance less than damagingRange?
                double vecX = entity.x - this.x;
                double vecY = entity.y - this.y;
                double vecZ = entity.z - this.z;
                double length = MathHelper.sqrt(vecX * vecX + vecY * vecY + vecZ * vecZ);
                vecX /= length;
                vecY /= length;
                vecZ /= length;
                //Calculates what fraction of an entity is exposed to the explosion?
                double exposedFraction = this.world.getVisibilityRatio(explosionOrigin, entity.boundingBox);
                double impactForce = (1.0D - distanceFraction) * exposedFraction;
                if (harm) {
                    entity.damage(this.cause, (int) ((impactForce * impactForce + impactForce) / 2.0D * 8.0D * damagingRange + 1.0D));
                }
                if (push) {
                    entity.velocityX += vecX * impactForce;
                    entity.velocityY += vecY * impactForce;
                    entity.velocityZ += vecZ * impactForce;
                }
            }
        }
    }
    public void createFires(float fireChance) {
        ArrayList<BlockPos> blocks = new ArrayList<>(this.damagedBlocks);
        for(int block_index = blocks.size() - 1; block_index >= 0; --block_index) {
            BlockPos BlockPos = blocks.get(block_index);
            int blockId = this.world.getBlockId(BlockPos.x, BlockPos.y, BlockPos.z);
            int blockBelowId = this.world.getBlockId(BlockPos.x, BlockPos.y - 1, BlockPos.z);
            if ((blockId == 0 || blockId == Block.SNOW.id) && Block.BLOCKS_OPAQUE[blockBelowId]) {
                if(this.random.nextFloat() < fireChance) {
                    this.world.setBlock(BlockPos.x, BlockPos.y, BlockPos.z, Block.FIRE.id);
                }
            }
        }
    }

    public void createSound(){
        this.world.playSound(this.x, this.y, this.z, "random.explode", 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);
    }

    public void createParticles() {
        ArrayList<BlockPos> blocks = new ArrayList<>(this.damagedBlocks);

        for (int block_index = blocks.size() - 1; block_index >= 0; --block_index) {
            BlockPos BlockPos = blocks.get(block_index);
            double particle_x = (float) BlockPos.x + this.world.random.nextFloat();
            double particle_y = (float) BlockPos.y + this.world.random.nextFloat();
            double particle_z = (float) BlockPos.z + this.world.random.nextFloat();
            double vec_x = particle_x - this.x;
            double vec_y = particle_y - this.y;
            double vec_z = particle_z - this.z;
            double distance = MathHelper.sqrt(vec_x * vec_x + vec_y * vec_y + vec_z * vec_z);
            vec_x /= distance;
            vec_y /= distance;
            vec_z /= distance;
            double inverse_distance = 0.5D / (distance / (double) this.power + 0.1D); //is 5 at the centre, decays as you move away
            inverse_distance *= this.world.random.nextFloat() * this.world.random.nextFloat() + 0.3F;
            vec_x *= inverse_distance;
            vec_y *= inverse_distance;
            vec_z *= inverse_distance;
            this.world.addParticle("explode", (particle_x + this.x) / 2.0D, (particle_y + this.y) / 2.0D, (particle_z + this.z) / 2.0D, vec_x, vec_y, vec_z);
            this.world.addParticle("smoke", particle_x, particle_y, particle_z, vec_x, vec_y, vec_z);
        }
    }

    public void destroyBlocks(int replacementBlock, float dropChance) {
        ArrayList<BlockPos> blocks = new ArrayList<>(this.damagedBlocks);

        for(int block_index = blocks.size() - 1; block_index >= 0; --block_index) {
            BlockPos BlockPos = blocks.get(block_index);
            int blockId = this.world.getBlockId(BlockPos.x, BlockPos.y, BlockPos.z);
            if (blockId > 0) {
                int meta = this.world.getBlockMeta(BlockPos.x, BlockPos.y, BlockPos.z);
                Block.BLOCKS[blockId].dropStacks(this.world, BlockPos.x, BlockPos.y, BlockPos.z, meta, dropChance);
                if (blockId == Block.ICE.id) {
                    this.world.setBlock(BlockPos.x, BlockPos.y, BlockPos.z, Block.FLOWING_WATER.id);
                } else {
                    this.world.setBlock(BlockPos.x, BlockPos.y, BlockPos.z, replacementBlock);
                }
                Block.BLOCKS[blockId].onDestroyedByExplosion(this.world, BlockPos.x, BlockPos.y, BlockPos.z);

            }else{
                this.world.setBlock(BlockPos.x, BlockPos.y, BlockPos.z, replacementBlock);
            }
        }
    }

    public void updateDamagedBlocks(RayProvider rayProvider, float randomisation) {
        damagedBlocks.clear();
        ArrayList<ExplosionRay> rays = rayProvider.getRays((int)(this.power*this.power)*8);
        for (ExplosionRay explosionRay: rays) {
            Vec3d dir = explosionRay.dir;
            float ray_power = explosionRay.multiplier * explosionRay.multiplier * this.power * this.power * 7.5f;
            ray_power *= ((1.0f-randomisation) + this.world.random.nextFloat() * (2.0f*randomisation)); //Randomly sample between 70% and 130% of the power
            double ray_x = this.x;
            double ray_y = this.y;
            double ray_z = this.z;
            float step_size = 0.3F;
            float distance = 0.0f;
            while (ray_power > 0.3F) {
                int block_x = MathHelper.floor(ray_x);
                int block_y = MathHelper.floor(ray_y);
                int block_z = MathHelper.floor(ray_z);
                int block_id = this.world.getBlockId(block_x, block_y, block_z);
                if (block_id > 0) {
                    ray_power -= (Block.BLOCKS[block_id].getBlastResistance(this.cause) + 0.3F) * step_size;
                }

                if (ray_power > 0.0F) {
                    damagedBlocks.add(new BlockPos(block_x, block_y, block_z));
                }

                ray_x += dir.x * (double) step_size;
                ray_y += dir.y * (double) step_size;
                ray_z += dir.z * (double) step_size;
                distance += step_size;
                //ray_power -= step_size;
                ray_power *= Math.pow(distance/(distance+step_size),2.0); //applies inverse square law incrementally
            }
        }
    }
}