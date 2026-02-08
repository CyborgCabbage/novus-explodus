package io.github.cyborgcabbage.novusexplodus.explosion;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ExtinguishingExplosion extends NeoExplosion {
    public ExtinguishingExplosion(World world, Entity cause, double x, double y, double z, float power) {
        super(world, cause, x, y, z, power);
    }

    @Override
    public void updateDamagedBlocks(RayProvider rayProvider, float randomisation) {
        damagedBlocks.clear();
        ArrayList<ExplosionRay> rays = rayProvider.getRays((int)(this.power*this.power)*8);
        for (ExplosionRay explosionRay: rays) {
            Vec3d dir = explosionRay.dir;
            float ray_power = explosionRay.multiplier * explosionRay.multiplier * this.power * this.power * 5.0f;
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
                    if(block_id == Block.LAVA.id || block_id == Block.FLOWING_LAVA.id){
                        ray_power -= (Block.DIRT.getBlastResistance(this.cause) + 0.3F) * step_size;
                    }else {
                        ray_power -= (Block.BLOCKS[block_id].getBlastResistance(this.cause) + 0.3F) * step_size;
                    }
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

    @Override
    public void destroyBlocks(int replacementBlock, float dropChance) {
        ArrayList<BlockPos> blocks = new ArrayList<>(this.damagedBlocks);
        for(int block_index = blocks.size() - 1; block_index >= 0; --block_index) {
            BlockPos BlockPos = blocks.get(block_index);
            int blockId = this.world.getBlockId(BlockPos.x, BlockPos.y, BlockPos.z);
            if (blockId > 0) {
                if(blockId == Block.FIRE.id){
                    this.world.setBlock(BlockPos.x, BlockPos.y, BlockPos.z,0);
                }else if(blockId == Block.LAVA.id || blockId == Block.FLOWING_LAVA.id){
                    this.world.setBlock(BlockPos.x, BlockPos.y, BlockPos.z,Block.STONE.id);
                }
            }
        }
    }
}
