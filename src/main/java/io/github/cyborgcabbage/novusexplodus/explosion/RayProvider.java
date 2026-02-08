package io.github.cyborgcabbage.novusexplodus.explosion;

import java.util.ArrayList;

public interface RayProvider {
    ArrayList<ExplosionRay> getRays(int count);
}
