package io.github.cyborgcabbage.novusexplodus.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.TntEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class BombBlock extends TemplateBlock {
    public BombBlock(Identifier identifier) {
        super(identifier, Material.TNT);
    }

    /*public void onPlaced(World world, int x, int y, int z) {
        super.onPlaced(world, x, y, z);
        if (world.isEmittingRedstonePower(x, y, z)) {
            prime(world, x, y, z, false);
        }
    }

    public void neighborUpdate(World world, int x, int y, int z, int id) {
        if (id > 0 && Block.BLOCKS[id].canEmitRedstonePower() && world.isEmittingRedstonePower(x, y, z)) {
            prime(world, x, y, z, false);
        }
    }

    private void prime(World world, int x, int y, int z, boolean shortFuse) {
        world.setBlock(x, y, z, 0);
        TntEntity entity = new TntEntity(world, (float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
        if (shortFuse) {
            entity.fuse = world.random.nextInt(entity.fuse / 4) + entity.fuse / 8;
        }
        world.spawnEntity(entity);
    }


    // So we are not dropped by explosion
    public int getDroppedItemCount(Random random) {
        return 0;
    }*/

    /*@Override
    public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, int meta) {
        super.afterBreak(world, playerEntity, x, y, z, meta);
    }*/

    //public void onDestroyedByExplosion(World world, int x, int y, int z) {
    //    prime(world, x, y, z, true);
    //}

    /*public void onMetadataChange(World world, int x, int y, int z, int meta) {
        if (!world.isRemote) {
            if ((meta & 1) == 0) {
                this.dropStack(world, x, y, z, new ItemStack(Block.TNT.id, 1, 0));
            } else {
                TntEntity var6 = new TntEntity(world, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F));
                world.spawnEntity(var6);
                world.playSound(var6, "random.fuse", 1.0F, 1.0F);
            }

        }
    }*/

    /*public void onBlockBreakStart(World world, int x, int y, int z, PlayerEntity player) {
        if (player.getHand() != null && player.getHand().itemId == Item.FLINT_AND_STEEL.id) {
            prime(world, x, y, z, false);
        }

        super.onBlockBreakStart(world, x, y, z, player);
    }*/
}
