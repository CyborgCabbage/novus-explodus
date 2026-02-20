package io.github.cyborgcabbage.explodus.events;

import io.github.cyborgcabbage.explodus.block.BombBlock;
import io.github.cyborgcabbage.explodus.explosion.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

public class BlockListener {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    public static BombBlock bombNormal;
    public static BombBlock bombCompressed;
    public static BombBlock bombNuclear;

    public static BombBlock bombColdNormal;
    public static BombBlock bombColdCompressed;
    public static BombBlock bombColdNuclear;

    public static BombBlock bombExtinguishingNormal;
    public static BombBlock bombExtinguishingCompressed;
    public static BombBlock bombExtinguishingNuclear;

    public static BombBlock bombFireNormal;
    public static BombBlock bombFireCompressed;
    public static BombBlock bombFireNuclear;

    public static BombBlock bombLevellingNormal;
    public static BombBlock bombLevellingCompressed;
    public static BombBlock bombLevellingNuclear;

    public static BombBlock bombLifeNormal;
    public static BombBlock bombLifeCompressed;
    public static BombBlock bombLifeNuclear;

    public static BombBlock bombMagneticNormal;
    public static BombBlock bombMagneticCompressed;
    public static BombBlock bombMagneticNuclear;

    public static BombBlock bombStarNormal;
    public static BombBlock bombStarCompressed;
    public static BombBlock bombStarNuclear;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        bombNormal = new BombBlock(NAMESPACE.id("bomb_normal"), NeoExplosion::new, 5.0f, 1, 0.3f).setTranslationKey(NAMESPACE, "bombNormal");
        bombCompressed = new BombBlock(NAMESPACE.id("bomb_compressed"), NeoExplosion::new, 16.0f, 2, 0.1f).setTranslationKey(NAMESPACE, "bombCompressed");
        bombNuclear = new BombBlock(NAMESPACE.id("bomb_nuclear"), NeoExplosion::new, 64.0f, 4, 0.03f).setTranslationKey(NAMESPACE, "bombNuclear");

        bombColdNormal = new BombBlock(NAMESPACE.id("bomb_cold_normal"), ColdExplosion::new, 5.0f, 1, 0.3f).setTranslationKey(NAMESPACE, "bombColdNormal");
        bombColdCompressed = new BombBlock(NAMESPACE.id("bomb_cold_compressed"), ColdExplosion::new, 16.0f, 2, 0.1f).setTranslationKey(NAMESPACE, "bombColdCompressed");
        bombColdNuclear = new BombBlock(NAMESPACE.id("bomb_cold_nuclear"), ColdExplosion::new, 64.0f, 4, 0.03f).setTranslationKey(NAMESPACE, "bombColdNuclear");

        bombExtinguishingNormal = new BombBlock(NAMESPACE.id("bomb_extinguishing_normal"), ExtinguishingExplosion::new, 5.0f, 1, 0.3f).setTranslationKey(NAMESPACE, "bombExtinguishingNormal");
        bombExtinguishingCompressed = new BombBlock(NAMESPACE.id("bomb_extinguishing_compressed"), ExtinguishingExplosion::new, 16.0f, 2, 0.1f).setTranslationKey(NAMESPACE, "bombExtinguishingCompressed");
        bombExtinguishingNuclear = new BombBlock(NAMESPACE.id("bomb_extinguishing_nuclear"), ExtinguishingExplosion::new, 64.0f, 4, 0.03f).setTranslationKey(NAMESPACE, "bombExtinguishingNuclear");

        bombFireNormal = new BombBlock(NAMESPACE.id("bomb_fire_normal"), FireExplosion::new, 5.0f, 1, 0.3f).setTranslationKey(NAMESPACE, "bombFireNormal");
        bombFireCompressed = new BombBlock(NAMESPACE.id("bomb_fire_compressed"), FireExplosion::new, 16.0f, 2, 0.1f).setTranslationKey(NAMESPACE, "bombFireCompressed");
        bombFireNuclear = new BombBlock(NAMESPACE.id("bomb_fire_nuclear"), FireExplosion::new, 64.0f, 4, 0.03f).setTranslationKey(NAMESPACE, "bombFireNuclear");

        bombLevellingNormal = new BombBlock(NAMESPACE.id("bomb_levelling_normal"), LevellingExplosion::new, 5.0f, 1, 0.3f).setTranslationKey(NAMESPACE, "bombLevellingNormal");
        bombLevellingCompressed = new BombBlock(NAMESPACE.id("bomb_levelling_compressed"), LevellingExplosion::new, 16.0f, 2, 0.1f).setTranslationKey(NAMESPACE, "bombLevellingCompressed");
        bombLevellingNuclear = new BombBlock(NAMESPACE.id("bomb_levelling_nuclear"), LevellingExplosion::new, 64.0f, 4, 0.03f).setTranslationKey(NAMESPACE, "bombLevellingNuclear");

        bombLifeNormal = new BombBlock(NAMESPACE.id("bomb_life_normal"), LifeExplosion::new, 5.0f, 1, 0.3f).setTranslationKey(NAMESPACE, "bombLifeNormal");
        bombLifeCompressed = new BombBlock(NAMESPACE.id("bomb_life_compressed"), LifeExplosion::new, 16.0f, 2, 0.1f).setTranslationKey(NAMESPACE, "bombLifeCompressed");
        bombLifeNuclear = new BombBlock(NAMESPACE.id("bomb_life_nuclear"), LifeExplosion::new, 64.0f, 4, 0.03f).setTranslationKey(NAMESPACE, "bombLifeNuclear");

        bombMagneticNormal = new BombBlock(NAMESPACE.id("bomb_magnetic_normal"), MagneticExplosion::new, 5.0f, 1, 0.3f).setTranslationKey(NAMESPACE, "bombMagneticNormal");
        bombMagneticCompressed = new BombBlock(NAMESPACE.id("bomb_magnetic_compressed"), MagneticExplosion::new, 16.0f, 2, 0.1f).setTranslationKey(NAMESPACE, "bombMagneticCompressed");
        bombMagneticNuclear = new BombBlock(NAMESPACE.id("bomb_magnetic_nuclear"), MagneticExplosion::new, 64.0f, 4, 0.03f).setTranslationKey(NAMESPACE, "bombMagneticNuclear");

        bombStarNormal = new BombBlock(NAMESPACE.id("bomb_star_normal"), StarExplosion::new, 6.0f, 1, 0.3f).setTranslationKey(NAMESPACE, "bombStarNormal");
        bombStarCompressed = new BombBlock(NAMESPACE.id("bomb_star_compressed"), StarExplosion::new, 16.0f, 2, 0.1f).setTranslationKey(NAMESPACE, "bombStarCompressed");
        bombStarNuclear = new BombBlock(NAMESPACE.id("bomb_star_nuclear"), StarExplosion::new, 44.0f, 4, 0.03f).setTranslationKey(NAMESPACE, "bombStarNuclear");
    }
}
