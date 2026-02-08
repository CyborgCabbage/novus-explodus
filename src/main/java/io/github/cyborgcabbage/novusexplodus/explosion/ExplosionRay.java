package io.github.cyborgcabbage.novusexplodus.explosion;

import net.minecraft.util.math.Vec3d;

public class ExplosionRay {
    public Vec3d dir;
    public float multiplier;
    public float power;
    public Vec3d pos;
    public float distance;

    public ExplosionRay(Vec3d dir, float multiplier) {
        this.dir = dir;
        this.multiplier = multiplier;
        this.power = 0.0f;
        this.pos = Vec3d.create(0.0,0.0,0.0);
        this.distance = 0.0f;
    }
}
