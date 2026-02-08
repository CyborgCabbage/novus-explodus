package io.github.cyborgcabbage.novusexplodus.explosion;


import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class StarRayProvider implements RayProvider{
    /*
    (±1, ±1, ±1)
    (0, ±ϕ, ±1/ϕ)
    (±1/ϕ, 0, ±ϕ)
    (±ϕ, ±1/ϕ, 0)
     */
    @Override
    public ArrayList<ExplosionRay> getRays(int count) {
        ArrayList<Vec3d> vertices = new ArrayList<>();
        double phi = (1+Math.sqrt(5.0))/2.0;
        double inverse_phi = 1.0/phi;

        for(double i=-1.0; i<2.0; i+=2.0){
            for(double j=-1.0; j<2.0; j+=2.0){
                for(double k=-1.0; k<2.0; k+=2.0){
                    vertices.add(Vec3d.create(i,j,k));
                }
                vertices.add(Vec3d.create(0.0,i*phi,j*inverse_phi));
                vertices.add(Vec3d.create(i*inverse_phi,0.0,j*phi));
                vertices.add(Vec3d.create(i*phi,j*inverse_phi,0.0));
            }
        }
        ArrayList<ExplosionRay> explosionRays = new GoldenRayProvider().getRays(count);
        for(Vec3d vertex: vertices) {
            explosionRays.add(new ExplosionRay(vertex.normalize(),1.0f));
        }
        for(ExplosionRay ray: explosionRays){
            double min_distance = 1.0f;
            for(Vec3d vertex: vertices){
                double distance = vertex.normalize().distanceTo(ray.dir.normalize());
                if(distance < min_distance) min_distance = distance;
            }
            ray.multiplier = (float)Math.pow(Math.abs(1.0-min_distance*1.5),4.0);
            ray.multiplier = (ray.multiplier+0.3f)/1.3f;
        }
        return explosionRays;
    }
    private ExplosionRay cr(double x, double y, double z){
        return new ExplosionRay(Vec3d.create(x,y,z),1.0f);
    }
}
