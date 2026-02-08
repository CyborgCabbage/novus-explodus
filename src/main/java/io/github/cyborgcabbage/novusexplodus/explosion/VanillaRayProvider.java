package io.github.cyborgcabbage.novusexplodus.explosion;

import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class VanillaRayProvider implements RayProvider {
    public ArrayList<ExplosionRay> getRays(int count){
        ArrayList<ExplosionRay> arrayList = new ArrayList<>();
        byte e_size = 16;
        for (int x = 0; x < e_size; ++x) {
            for (int y = 0; y < e_size; ++y) {
                for (int z = 0; z < e_size; ++z) {
                    if (x == 0 || x == e_size - 1 || y == 0 || y == e_size - 1 || z == 0 || z == e_size - 1) {
                        // Maps 0 to 15 -> -1.0 to 1.0 (gets vector to centre)
                        Vec3d direction = Vec3d.create(
                                ((float) x / ((float) e_size - 1.0F) * 2.0F - 1.0F),
                                ((float) y / ((float) e_size - 1.0F) * 2.0F - 1.0F),
                                ((float) z / ((float) e_size - 1.0F) * 2.0F - 1.0F)
                        );
                        double length = Math.sqrt(direction.x * direction.x + direction.y * direction.y + direction.z * direction.z);
                        direction.x /= length;
                        direction.y /= length;
                        direction.z /= length;
                        arrayList.add(new ExplosionRay(direction,1.0f));
                    }
                }
            }
        }
        return arrayList;
    }
}
