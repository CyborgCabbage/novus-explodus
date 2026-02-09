package io.github.cyborgcabbage.explodus.explosion;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ColdExplosion extends NeoExplosion{
    public ColdExplosion(World world, Entity cause, double x, double y, double z, float power) {
        super(world, cause, x, y, z, power);
        this.harmEntities = false;
    }

    @Override
    public void destroyBlocks() {
        ArrayList<BlockPos> blocks = new ArrayList<>(this.damagedBlocks);
        for(int block_index = blocks.size() - 1; block_index >= 0; --block_index) {
            BlockPos BlockPos = blocks.get(block_index);
            int blockId = this.world.getBlockId(BlockPos.x, BlockPos.y, BlockPos.z);
            int belowBlockId = this.world.getBlockId(BlockPos.x, BlockPos.y-1, BlockPos.z);
            if (blockId == 0 && belowBlockId > 0) {
                if (belowBlockId == Block.WATER.id || belowBlockId == Block.FLOWING_WATER.id) {
                    this.world.setBlock(BlockPos.x, BlockPos.y-1, BlockPos.z, Block.ICE.id);
                    this.world.setBlock(BlockPos.x, BlockPos.y, BlockPos.z, Block.SNOW.id);
                }else if(Block.BLOCKS[belowBlockId].isFullCube()){
                    this.world.setBlock(BlockPos.x, BlockPos.y, BlockPos.z, Block.SNOW.id);
                }
            }
        }
    }
}
