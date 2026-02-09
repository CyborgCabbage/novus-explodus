package io.github.cyborgcabbage.explodus.explosion;


import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class MagneticExplosion extends NeoExplosion{
    public MagneticExplosion(World world, Entity cause, double x, double y, double z, float power) {
        super(world, cause, x, y, z, power);
        this.harmEntities = false;
        this.pushEntities = false;
        this.createParticles = false;
        this.dropChance = 0.8f;
    }

    @Override
    public void destroyBlocks() {
        List blockList = Arrays.asList(
                Block.REDSTONE_WIRE.id,
                Block.LIT_REDSTONE_TORCH.id,
                Block.REDSTONE_TORCH.id,
                Block.POWERED_REPEATER.id,
                Block.REPEATER.id
        );
        for (BlockPos blockPos : damagedBlocks) {
            int blockId = this.world.getBlockId(blockPos.x, blockPos.y, blockPos.z);
            if (blockList.contains(blockId)) {
                int meta = this.world.getBlockMeta(blockPos.x, blockPos.y, blockPos.z);
                Block.BLOCKS[blockId].dropStacks(this.world, blockPos.x, blockPos.y, blockPos.z, meta, this.dropChance);
                this.world.setBlock(blockPos.x, blockPos.y, blockPos.z, this.replacementBlockId);
                Block.BLOCKS[blockId].onDestroyedByExplosion(this.world, blockPos.x, blockPos.y, blockPos.z);
            }
        }
    }
}
