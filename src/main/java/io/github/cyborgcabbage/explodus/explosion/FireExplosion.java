package io.github.cyborgcabbage.explodus.explosion;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class FireExplosion extends NeoExplosion {
    public FireExplosion(World world, Entity cause, double x, double y, double z, ExplosionParameters parameters) {
        super(world, cause, x, y, z, parameters);
        this.fireChance = 0.8f;
        this.destroyBlocks = false;
        this.stopAfterOneBlock = true;
    }
}
