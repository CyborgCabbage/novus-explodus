package io.github.cyborgcabbage.explodus.explosion;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LevellingExplosion extends NeoExplosion {
    public LevellingExplosion(World world, Entity cause, double x, double y, double z, ExplosionParameters parameters) {
        super(world, cause, x, y, z, parameters);
    }

    @Override
    protected void processRay(double dirX, double dirY, double dirZ, float multiplier) {
        // Ignore rays that point down
        if (dirY > 0.0f) {
            super.processRay(dirX, dirY, dirZ, multiplier);
        }
    }
}
