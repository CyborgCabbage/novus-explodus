package io.github.cyborgcabbage.explodus.block;

import io.github.cyborgcabbage.explodus.entity.PrimedBombEntity;
import io.github.cyborgcabbage.explodus.explosion.ExplosionFactory;
import io.github.cyborgcabbage.explodus.explosion.NeoExplosion;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;


public class BombBlock extends TemplateBlock {
    private final ExplosionFactory explosionFactory;
    private final float power;
    private final int fuseMultiplier;
    private final float dropChance;

    public BombBlock(Identifier identifier, ExplosionFactory explosionFactory, float power, int fuseMultiplier, float dropChance) {
        super(identifier, Material.TNT);
        this.explosionFactory = explosionFactory;

        this.power = power;
        this.fuseMultiplier = fuseMultiplier;
        this.dropChance = dropChance;
    }

    public NeoExplosion createExplosion(World world, Entity cause, double x, double y, double z) {
        return explosionFactory.create(world, cause, x, y, z, power, dropChance);
    }

    @Override
    public BombBlock setTranslationKey(Namespace namespace, String translationKey) {
        return (BombBlock) super.setTranslationKey(namespace, translationKey);
    }

    public void onPlaced(World world, int x, int y, int z) {
        super.onPlaced(world, x, y, z);
        if (world.isPowered(x, y, z)) {
            prime(world, x, y, z, false);
        }
    }

    public void neighborUpdate(World world, int x, int y, int z, int id) {
        if (id > 0 && Block.BLOCKS[id].canEmitRedstonePower() && world.isPowered(x, y, z)) {
            prime(world, x, y, z, false);
        }
    }

    private void prime(World world, int x, int y, int z, boolean shortFuse) {
        world.setBlock(x, y, z, 0);
        PrimedBombEntity entity = new PrimedBombEntity(world, (float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, id);
        if (shortFuse) {
            entity.fuse = world.random.nextInt(entity.fuse / 4) + entity.fuse / 8;
        }
        entity.fuse *= fuseMultiplier;
        world.spawnEntity(entity);
    }

    @Override
    public void onDestroyedByExplosion(World world, int x, int y, int z) {
        prime(world, x, y, z, true);
    }

    private int textureBottom;
    private int textureSide;
    private int textureTop;

    public void setTextureIds(int bottom, int side, int top) {
        this.textureBottom = bottom;
        this.textureSide = side;
        this.textureTop = top;
    }

    @Override
    public int getTexture(int side, int meta) {
        return switch (side){
            case 0 -> textureBottom;
            case 1 -> textureTop;
            default -> textureSide;
        };
    }

    @Override
    public void dropStacks(World world, int x, int y, int z, int meta, float luck) {
        // By only dropping when at 1.0 luck, we avoid dropping TNT when destroyed by explosion
        if (luck >= 1.0f) {
            super.dropStacks(world, x, y, z, meta, luck);
        }
    }

    public void onBlockBreakStart(World world, int x, int y, int z, PlayerEntity player) {
        if (player.getHand() != null && player.getHand().itemId == Item.FLINT_AND_STEEL.id) {
            prime(world, x, y, z, false);
        }
        super.onBlockBreakStart(world, x, y, z, player);
    }
}
