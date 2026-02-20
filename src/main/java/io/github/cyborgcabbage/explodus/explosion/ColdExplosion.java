package io.github.cyborgcabbage.explodus.explosion;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ColdExplosion extends NeoExplosion{
    private static final List<Integer> lowBlastResistance;
    private static final Map<Integer, Integer> transformRequiringAir;
    static {
        lowBlastResistance = Arrays.asList(
                Block.WATER.id,
                Block.FLOWING_WATER.id,
                Block.LAVA.id,
                Block.FLOWING_LAVA.id
        );
        transformRequiringAir = Map.of(
                Block.WATER.id, Block.ICE.id,
                Block.FLOWING_WATER.id, Block.ICE.id,
                Block.LAVA.id, Block.OBSIDIAN.id,
                Block.FLOWING_LAVA.id, Block.OBSIDIAN.id
        );
    }

    public ColdExplosion(World world, Entity cause, double x, double y, double z, ExplosionParameters parameters) {
        super(world, cause, x, y, z, parameters);
        this.harmEntities = false;
    }

    @Override
    protected float getBlastResistance(int blockId) {
        // Penetrate water as if it were dirt
        if (lowBlastResistance.contains(blockId)) {
            return super.getBlastResistance(Block.DIRT.id);
        }
        return super.getBlastResistance(blockId);
    }

    @Override
    public void destroyBlocks() {
        // Remove Fire
        for(BlockPos blockPos : this.damagedBlocks) {
            int blockId = this.world.getBlockId(blockPos.x, blockPos.y, blockPos.z);
            if (blockId == Block.FIRE.id) {
                this.world.setBlock(blockPos.x, blockPos.y, blockPos.z, 0);
            }
        }
        // Cool liquids and lay down slow
        for(BlockPos blockPos : this.damagedBlocks) {
            int blockId = this.world.getBlockId(blockPos.x, blockPos.y, blockPos.z);
            if (blockId == 0) {
                continue;
            }
            int aboveBlockId = this.world.getBlockId(blockPos.x, blockPos.y + 1, blockPos.z);
            if (aboveBlockId == 0) {
                Integer transformed = transformRequiringAir.get(blockId);
                if (transformed != null) {
                    this.world.setBlock(blockPos.x, blockPos.y, blockPos.z, transformed);
                }
                if (Block.SNOW.canPlaceAt(this.world, blockPos.x, blockPos.y+1, blockPos.z)) {
                    this.world.setBlock(blockPos.x, blockPos.y+1, blockPos.z, Block.SNOW.id);
                }
            }
        }
    }
}
