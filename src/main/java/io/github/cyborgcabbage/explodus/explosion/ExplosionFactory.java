package io.github.cyborgcabbage.explodus.explosion;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public interface ExplosionFactory {
    NeoExplosion create(World world, Entity cause, double x, double y, double z, float power);
}
