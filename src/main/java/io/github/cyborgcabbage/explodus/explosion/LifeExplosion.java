package io.github.cyborgcabbage.explodus.explosion;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

import java.util.Arrays;
import java.util.List;

public class LifeExplosion extends NeoExplosion{
    private static final List<Integer> toAir;
    private static final List<Integer> toGrass;
    private static final List<Integer> undergrowthBlocks;
    private static final List<Feature> treeFeatures;
    static {
        toAir = Arrays.asList(
                Block.SNOW.id,
                Block.SNOW_BLOCK.id
        );
        toGrass = Arrays.asList(
                Block.STONE.id,
                Block.DIRT.id,
                Block.COBBLESTONE.id,
                Block.SAND.id,
                Block.GRAVEL.id,
                Block.SANDSTONE.id,
                Block.MOSSY_COBBLESTONE.id,
                Block.NETHERRACK.id,
                Block.SOUL_SAND.id,
                Block.GRASS_BLOCK.id
        );
        undergrowthBlocks = Arrays.asList(
                Block.GRASS.id,
                Block.DANDELION.id,
                Block.ROSE.id
        );
        treeFeatures = Arrays.asList(
                new BirchTreeFeature(),
                new SpruceTreeFeature(),
                new PineTreeFeature(),
                new OakTreeFeature(),
                new LargeOakTreeFeature()
        );
    }
    public LifeExplosion(World world, Entity cause, double x, double y, double z, ExplosionParameters parameters) {
        super(world, cause, x, y, z, parameters);
        this.harmEntities = false;
        this.stopAfterOneBlock = true;
    }

    @Override
    public void destroyBlocks() {
        // To Air
        for (BlockPos BlockPos : this.damagedBlocks) {
            int blockId = this.world.getBlockId(BlockPos.x, BlockPos.y, BlockPos.z);
            if (blockId <= 0) {
                continue;
            }
            if (toAir.contains(blockId)) {
                this.world.setBlock(BlockPos.x, BlockPos.y, BlockPos.z, 0);
            }
        }
        // Grow stuff
        for (BlockPos BlockPos : this.damagedBlocks) {
            int blockId = this.world.getBlockId(BlockPos.x, BlockPos.y, BlockPos.z);
            if (blockId <= 0) {
                continue;
            }
            // Check that above is air
            int aboveBlockId = this.world.getBlockId(BlockPos.x, BlockPos.y+1, BlockPos.z);
            if (aboveBlockId != 0) {
                continue;
            }
            // To Grass
            if (toGrass.contains(blockId)) {
                this.world.setBlock(BlockPos.x, BlockPos.y, BlockPos.z, Block.GRASS_BLOCK.id);
                blockId = Block.GRASS_BLOCK.id;
            }
            if (blockId != Block.GRASS_BLOCK.id) {
                continue;
            }
            // Grow Light
            if (this.random.nextInt(25) == 0) {
                this.world.setBlock(BlockPos.x, BlockPos.y + 1, BlockPos.z, Block.JACK_O_LANTERN.id, this.random.nextInt(4));
                continue;
            }
            // Grow Tree
            if (this.random.nextInt(16) == 0) {
                Feature feature = treeFeatures.get(this.random.nextInt(treeFeatures.size()));
                feature.generate(this.world, this.world.random, BlockPos.x, BlockPos.y + 1, BlockPos.z);
                continue;
            }
            // Grow Shrubs
            if (this.random.nextInt(6) == 0) {
                int undergrowthId = undergrowthBlocks.get(this.random.nextInt(undergrowthBlocks.size()));
                // Tall grass needs a meta of 1, otherwise it will look like a dead bush
                this.world.setBlock(BlockPos.x, BlockPos.y + 1, BlockPos.z, undergrowthId, undergrowthId == Block.GRASS.id ? 1 : 0);
            }
        }
    }
}
