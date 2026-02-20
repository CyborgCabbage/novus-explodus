package io.github.cyborgcabbage.explodus.explosion;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class SingleRayExplosion extends NeoExplosion {
    private final float dirX;
    private final float dirY;
    private final float dirZ;

    public SingleRayExplosion(World world, Entity cause, double x, double y, double z, ExplosionParameters parameters, float dirX, float dirY, float dirZ) {
        super(world, cause, x, y, z, parameters);
        this.dirX = dirX;
        this.dirY = dirY;
        this.dirZ = dirZ;
    }

    @Override
    public void updateDamagedBlocks() {
        processRay(dirX, dirY, dirZ, 1.0f);
    }
}
