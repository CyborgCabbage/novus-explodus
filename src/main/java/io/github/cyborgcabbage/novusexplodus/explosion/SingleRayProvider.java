package io.github.cyborgcabbage.novusexplodus.explosion;

import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class SingleRayProvider implements RayProvider{
    public Vec3d dir;
    public SingleRayProvider(double x, double y, double z){
        this.dir = Vec3d.create(x,y,z);
    }

    @Override
    public ArrayList<ExplosionRay> getRays(int count) {
        ArrayList<ExplosionRay> arrayList = new ArrayList<>();
        arrayList.add(new ExplosionRay(this.dir,1.0f));
        return arrayList;
    }
}
