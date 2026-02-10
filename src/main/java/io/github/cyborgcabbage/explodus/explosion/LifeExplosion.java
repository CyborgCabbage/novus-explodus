package io.github.cyborgcabbage.explodus.explosion;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.BirchTreeFeature;
import net.minecraft.world.gen.feature.OakTreeFeature;
import net.minecraft.world.gen.feature.SpruceTreeFeature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LifeExplosion extends NeoExplosion{

    public LifeExplosion(World world, Entity cause, double x, double y, double z, float power, float dropChance) {
        super(world, cause, x, y, z, power, dropChance);
        this.harmEntities = false;
    }

    @Override
    public void destroyBlocks() {
        List blockList = Arrays.asList(
                Block.STONE.id,
                Block.DIRT.id,
                Block.COBBLESTONE.id,
                Block.LOG.id,
                Block.SAND.id,
                Block.GRAVEL.id,
                Block.SANDSTONE.id,
                Block.DOUBLE_SLAB.id,
                Block.SLAB.id,
                Block.BRICKS.id,
                Block.MOSSY_COBBLESTONE.id,
                Block.OBSIDIAN.id,
                Block.WOODEN_STAIRS.id,
                Block.COBBLESTONE_STAIRS.id,
                Block.SNOW_BLOCK.id,
                Block.NETHERRACK.id,
                Block.SOUL_SAND.id,
                Block.GRASS_BLOCK.id
        );
        for (BlockPos BlockPos : this.damagedBlocks) {
            int blockId = this.world.getBlockId(BlockPos.x, BlockPos.y, BlockPos.z);
            if (blockId <= 0) {
                continue;
            }
            if (!blockList.contains(blockId)) {
                continue;
            }
            int aboveBlockId = this.world.getBlockId(BlockPos.x, BlockPos.y+1, BlockPos.z);
            if (aboveBlockId != 0 && aboveBlockId != Block.SNOW.id) {
                continue;
            }
            this.world.setBlock(BlockPos.x, BlockPos.y, BlockPos.z, Block.GRASS_BLOCK.id);
            this.world.setBlock(BlockPos.x, BlockPos.y+1, BlockPos.z, 0);

            switch (this.random.nextInt(4)) {
                // Trees
                case 0 -> {
                    switch (this.random.nextInt(3)) {
                        case 0 ->
                                new BirchTreeFeature().generate(this.world, this.world.random, BlockPos.x, BlockPos.y + 1, BlockPos.z);
                        case 1 ->
                                new SpruceTreeFeature().generate(this.world, this.world.random, BlockPos.x, BlockPos.y + 1, BlockPos.z);
                        default ->
                                new OakTreeFeature().generate(this.world, this.world.random, BlockPos.x, BlockPos.y + 1, BlockPos.z);
                    }
                }
                // Grass and flowers
                case 1 -> {
                    switch (this.random.nextInt(3)) {
                        case 0 -> world.setBlock(BlockPos.x, BlockPos.y + 1, BlockPos.z, Block.GRASS.id, 1);
                        case 1 ->
                                world.setBlock(BlockPos.x, BlockPos.y + 1, BlockPos.z, Block.DANDELION.id);
                        case 2 -> world.setBlock(BlockPos.x, BlockPos.y + 1, BlockPos.z, Block.ROSE.id);
                    }
                }
                // Leaves
                case 2 -> {
                    int meta = this.world.random.nextInt(3);
                    this.world.setBlock(BlockPos.x, BlockPos.y + 1, BlockPos.z, Block.LEAVES.id, meta);
                    if (this.world.getBlockId(BlockPos.x, BlockPos.y + 2, BlockPos.z) == 0 && this.random.nextInt(2) == 0) {
                        this.world.setBlock(BlockPos.x, BlockPos.y + 2, BlockPos.z, Block.LEAVES.id, meta);
                    }
                }
            }
        }
    }
}
