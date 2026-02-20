package io.github.cyborgcabbage.explodus.explosion;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.math.Vec3d;

import java.util.ArrayList;

public class StarExplosion extends NeoExplosion {
    private static final ArrayList<Vec3d> vertices = new ArrayList<>();
    static {
        // Generate vertices
        double phi = (1+Math.sqrt(5.0))/2.0;
        double inverse_phi = 1.0/phi;
        double[] values = {-1.0, 1.0};
        for(double i : values){
            for(double j : values){
                for(double k : values){
                    vertices.add(new Vec3d(i, j, k).normalize());
                }
                vertices.add(new Vec3d(0.0,i*phi,j*inverse_phi).normalize());
                vertices.add(new Vec3d(i*inverse_phi,0.0,j*phi).normalize());
                vertices.add(new Vec3d(i*phi,j*inverse_phi,0.0).normalize());
            }
        }
    }

    public StarExplosion(World world, Entity cause, double x, double y, double z, ExplosionParameters parameters) {
        super(world, cause, x, y, z, parameters);
        this.replacementBlockId = Block.GLOWSTONE.id;
        this.dropChance = 0.0f;
        this.harmEntities = false;
        this.rayRandomisation = 0.0f;
    }

    @Override
    protected void processRay(double dirX, double dirY, double dirZ, float multiplier) {
        // Change rays based on distance to vertices
        double min_distance = 1.0f;
        Vec3d dir = new Vec3d(dirX, dirY, dirZ).normalize();
        for(Vec3d vertex: vertices){
            min_distance = Math.min(min_distance, vertex.distanceTo(dir));
        }
        multiplier = (float)Math.pow(Math.abs(1.0-min_distance*1.5),4.0);
        multiplier = (multiplier+0.3f)/4.0f;
        super.processRay(dirX, dirY, dirZ, multiplier);
    }

    @Override
    public void updateDamagedBlocks() {
        super.updateDamagedBlocks();
        // Make sure every vertex has a ray right on it
        for (Vec3d vertex : vertices) {
            this.processRay(vertex.x, vertex.y, vertex.z, 1.0f);
        }
    }
}
