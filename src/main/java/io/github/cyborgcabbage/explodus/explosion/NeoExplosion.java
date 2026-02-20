package io.github.cyborgcabbage.explodus.explosion;

import io.github.cyborgcabbage.explodus.events.ItemListener;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.math.Vec3d;

import java.util.*;

public class NeoExplosion {
    private static float BEDROCK_SHARD_POWER = 10.0f;
    private static int BEDROCK_SHARD_MAX = 8;
    protected final Random random = new Random();
    protected final World world;
    public double x;
    public double y;
    public double z;
    public Entity cause;
    public float power;
    protected float dropChance;
    public HashSet<BlockPos> damagedBlocks = new HashSet<>();
    public HashSet<BlockPos> bedrockBlocks = new HashSet<>();

    protected boolean destroyBlocks = true;
    protected boolean stopAfterOneBlock = false;
    protected boolean harmEntities = true;
    protected boolean pushEntities = true;
    protected float fireChance = 0.0f;

    protected float particleChance;
    protected int maxBedrockShards;
    protected boolean createSound = true;
    protected float rayRandomisation = 0.3f;
    protected int replacementBlockId = 0;
    
    public NeoExplosion(World world, Entity cause, double x, double y, double z, ExplosionParameters parameters) {
        this.world = world;
        this.cause = cause;
        this.x = x;
        this.y = y;
        this.z = z;
        this.power = parameters.power();
        this.dropChance = parameters.dropChance();
        this.particleChance = parameters.particleChance();
        this.maxBedrockShards = parameters.maxBedrockShards();
    }

    public void explode(){
        damagedBlocks.clear();
        bedrockBlocks.clear();
        if (fireChance > 0.0 || destroyBlocks) this.updateDamagedBlocks();
        if (!harmEntities || !pushEntities) this.effectEntities();
        if (destroyBlocks) this.destroyBlocks();
        if (fireChance > 0.0) this.createFires();
        if (particleChance > 0.0) this.createParticles();
        if (createSound) this.createSound();
        if (maxBedrockShards > 0) this.spawnBedrockShards();
    }

    protected void processRay(double dirX, double dirY, double dirZ, float multiplier) {
        double rayPower = multiplier * multiplier * this.power * this.power * 7.5f;
        rayPower *= ((1.0f-this.rayRandomisation) + this.world.random.nextFloat() * (2.0f*this.rayRandomisation)); //Randomly sample between 70% and 130% of the power
        double rayX = this.x;
        double rayY = this.y;
        double rayZ = this.z;
        double stepSize = 0.3F;
        double distance = 0.0f;
        while (rayPower > 0.3F) {
            int blockX = MathHelper.floor(rayX);
            int blockY = MathHelper.floor(rayY);
            int blockZ = MathHelper.floor(rayZ);
            int blockId = this.world.getBlockId(blockX, blockY, blockZ);
            if (blockId == Block.BEDROCK.id) {
                bedrockBlocks.add(new BlockPos(blockX, blockY, blockZ));
                break;
            }
            rayPower -= getBlastResistance(blockId) * stepSize;
            if (rayPower > 0.0F) {
                damagedBlocks.add(new BlockPos(blockX, blockY, blockZ));
                if (stopAfterOneBlock && blockId > 0) {
                    break;
                }
            }

            rayX += dirX * stepSize;
            rayY += dirY * stepSize;
            rayZ += dirZ * stepSize;
            distance += stepSize;
            rayPower *= Math.pow(distance/(distance+stepSize),2.0); //applies inverse square law incrementally
        }
    }

    protected float getBlastResistance(int blockId) {
        if (blockId > 0) {
            return Block.BLOCKS[blockId].getBlastResistance(this.cause);
        }
        return 0.0f;
    }

    public void effectEntities() {
        double damagingRange = this.power * 2.0;
        int min_x = MathHelper.floor(this.x - damagingRange - 1.0D);
        int max_x = MathHelper.floor(this.x + damagingRange + 1.0D);
        int min_y = MathHelper.floor(this.y - damagingRange - 1.0D);
        int max_y = MathHelper.floor(this.y + damagingRange + 1.0D);
        int min_z = MathHelper.floor(this.z - damagingRange - 1.0D);
        int max_z = MathHelper.floor(this.z + damagingRange + 1.0D);
        List inBounds = this.world.getEntities(this.cause, Box.create(min_x,  min_y,  min_z,  max_x,  max_y,  max_z));
        var explosionOrigin = net.minecraft.util.math.Vec3d.create(this.x, this.y, this.z);

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
                if (this.harmEntities) {
                    entity.damage(this.cause, (int) ((impactForce * impactForce + impactForce) / 2.0D * 8.0D * damagingRange + 1.0D));
                }
                if (this.pushEntities) {
                    entity.velocityX += vecX * impactForce;
                    entity.velocityY += vecY * impactForce;
                    entity.velocityZ += vecZ * impactForce;
                }
            }
        }
    }
    public void createFires() {
        ArrayList<BlockPos> blocks = new ArrayList<>(this.damagedBlocks);
        for(int block_index = blocks.size() - 1; block_index >= 0; --block_index) {
            BlockPos BlockPos = blocks.get(block_index);
            int blockId = this.world.getBlockId(BlockPos.x, BlockPos.y, BlockPos.z);
            int blockBelowId = this.world.getBlockId(BlockPos.x, BlockPos.y - 1, BlockPos.z);
            if ((blockId == 0 || blockId == Block.SNOW.id) && Block.BLOCKS_OPAQUE[blockBelowId]) {
                if(this.random.nextFloat() < this.fireChance) {
                    this.world.setBlock(BlockPos.x, BlockPos.y, BlockPos.z, Block.FIRE.id);
                }
            }
        }
    }

    public void createSound(){
        this.world.playSound(this.x, this.y, this.z, "random.explode", 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);
    }

    public void createParticles() {
        for (BlockPos blockPos : this.damagedBlocks) {
            if (this.world.random.nextFloat() < this.particleChance) {
                double particleX = (float) blockPos.x + this.world.random.nextFloat();
                double particleY = (float) blockPos.y + this.world.random.nextFloat();
                double particleZ = (float) blockPos.z + this.world.random.nextFloat();
                double vecX = particleX - this.x;
                double vecY = particleY - this.y;
                double vecZ = particleZ - this.z;
                double distance = MathHelper.sqrt(vecX * vecX + vecY * vecY + vecZ * vecZ);
                vecX /= distance;
                vecY /= distance;
                vecZ /= distance;
                double inverse_distance = 0.5D / (distance / (double) this.power + 0.1D); //is 5 at the centre, decays as you move away
                inverse_distance *= this.world.random.nextFloat() * this.world.random.nextFloat() + 0.3F;
                vecX *= inverse_distance;
                vecY *= inverse_distance;
                vecZ *= inverse_distance;
                this.world.addParticle("explode", (particleX + this.x) / 2.0D, (particleY + this.y) / 2.0D, (particleZ + this.z) / 2.0D, vecX, vecY, vecZ);
                this.world.addParticle("smoke", particleX, particleY, particleZ, vecX, vecY, vecZ);
            }
        }
    }

    public void destroyBlocks() {
        ArrayList<BlockPos> blocks = new ArrayList<>(this.damagedBlocks);

        for(int block_index = blocks.size() - 1; block_index >= 0; --block_index) {
            BlockPos BlockPos = blocks.get(block_index);
            int blockId = this.world.getBlockId(BlockPos.x, BlockPos.y, BlockPos.z);
            if (blockId > 0) {
                int meta = this.world.getBlockMeta(BlockPos.x, BlockPos.y, BlockPos.z);
                Block.BLOCKS[blockId].dropStacks(this.world, BlockPos.x, BlockPos.y, BlockPos.z, meta, this.dropChance);
                if (blockId == Block.ICE.id) {
                    this.world.setBlock(BlockPos.x, BlockPos.y, BlockPos.z, Block.FLOWING_WATER.id);
                } else {
                    this.world.setBlock(BlockPos.x, BlockPos.y, BlockPos.z, this.replacementBlockId);
                }
                Block.BLOCKS[blockId].onDestroyedByExplosion(this.world, BlockPos.x, BlockPos.y, BlockPos.z);

            }else{
                this.world.setBlock(BlockPos.x, BlockPos.y, BlockPos.z, this.replacementBlockId);
            }
        }
    }

    public void updateDamagedBlocks() {
        double phi = Math.PI * (3.0 - Math.sqrt(5.0)); // golden angle in radians
        int count = (int)(this.power*this.power)*8;
        for(int i = 0; i < count; i++) {
            double index = (float)i;
            double y = 1.0 - (index / (float)(count - 1))*2.0;  //y goes from 1 to - 1
            double radius = Math.sqrt(1.0 - y * y); //radius at y

            double theta = phi * index; //golden angle increment

            double x = Math.cos(theta) * radius;
            double z = Math.sin(theta) * radius;

            processRay(x, y, z, 1.0f);
        }
    }

    private void spawnBedrockShards() {
        // Get spawn points from bedrock with air above or below (nether roof) it
        List<Vec3d> spawnPoints = new ArrayList<>();
        for (BlockPos blockPos : bedrockBlocks) {
            if (this.world.getBlockId(blockPos.x, blockPos.y + 1, blockPos.z) == 0) {
                spawnPoints.add(new Vec3d(blockPos.x + 0.5f, blockPos.y + 1.2f, blockPos.z + 0.5f));
            }else if (this.world.getBlockId(blockPos.x, blockPos.y - 1, blockPos.z) == 0) {
                spawnPoints.add(new Vec3d(blockPos.x + 0.5f, blockPos.y - 0.2f, blockPos.z + 0.5f));
            }
        }
        int shardCount = Math.min(spawnPoints.size() / 2, this.maxBedrockShards);
        for (int i = 0; i < shardCount; i++) {
            Vec3d point = spawnPoints.get(this.world.random.nextInt(spawnPoints.size()));
            ItemEntity entity = new ItemEntity(this.world, point.x, point.y, point.z, new ItemStack(ItemListener.bedrockShard, 1));
            entity.pickupDelay = 10;
            this.world.spawnEntity(entity);
        }
    }
}