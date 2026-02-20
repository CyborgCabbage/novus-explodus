package io.github.cyborgcabbage.explodus.explosion;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ColdExplosion extends NeoExplosion{
    public ColdExplosion(World world, Entity cause, double x, double y, double z, float power, float dropChance) {
        super(world, cause, x, y, z, power, dropChance);
        this.harmEntities = false;
    }

    @Override
    protected float getBlastResistance(int blockId) {
        // Penetrate water as if it were dirt
        if (blockId == Block.WATER.id || blockId == Block.FLOWING_WATER.id) {
            return super.getBlastResistance(Block.DIRT.id);
        }
        return super.getBlastResistance(blockId);
    }

    @Override
    public void destroyBlocks() {
        for(BlockPos blockPos : this.damagedBlocks) {
            int blockId = this.world.getBlockId(blockPos.x, blockPos.y, blockPos.z);
            if (blockId == 0) {
                continue;
            }
            int aboveBlockId = this.world.getBlockId(blockPos.x, blockPos.y+1, blockPos.z);
            boolean isWater = (blockId == Block.WATER.id || blockId == Block.FLOWING_WATER.id);
            boolean isWaterAbove = (aboveBlockId == Block.WATER.id || aboveBlockId == Block.FLOWING_WATER.id || aboveBlockId == Block.ICE.id);
            if (isWater && !isWaterAbove) {
                this.world.setBlock(blockPos.x, blockPos.y, blockPos.z, Block.ICE.id);
            }
            if (aboveBlockId == 0 && Block.SNOW.canPlaceAt(this.world, blockPos.x, blockPos.y+1, blockPos.z)) {
                this.world.setBlock(blockPos.x, blockPos.y+1, blockPos.z, Block.SNOW.id);
            }
        }
    }
}
