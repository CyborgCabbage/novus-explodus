package io.github.cyborgcabbage.explodus.explosion;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ExtinguishingExplosion extends NeoExplosion {
    public ExtinguishingExplosion(World world, Entity cause, double x, double y, double z, float power, float dropChance) {
        super(world, cause, x, y, z, power, dropChance);
        this.stopAfterOneBlock = true;
    }

    @Override
    protected float getBlastResistance(int blockId) {
        // Penetrate lava as if it were dirt
        if (blockId == Block.LAVA.id || blockId == Block.FLOWING_LAVA.id) {
            return super.getBlastResistance(Block.DIRT.id);
        }
        return super.getBlastResistance(blockId);
    }

    @Override
    public void destroyBlocks() {
        ArrayList<BlockPos> blocks = new ArrayList<>(this.damagedBlocks);
        for(int block_index = blocks.size() - 1; block_index >= 0; --block_index) {
            BlockPos BlockPos = blocks.get(block_index);
            int blockId = this.world.getBlockId(BlockPos.x, BlockPos.y, BlockPos.z);
            if (blockId > 0) {
                if(blockId == Block.FIRE.id){
                    this.world.setBlock(BlockPos.x, BlockPos.y, BlockPos.z,0);
                }else if(blockId == Block.LAVA.id || blockId == Block.FLOWING_LAVA.id){
                    this.world.setBlock(BlockPos.x, BlockPos.y, BlockPos.z, Block.COBBLESTONE.id);
                }
            }
        }
    }
}
