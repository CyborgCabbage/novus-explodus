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

    public static BombBlock bombFireNormal;
    public static BombBlock bombFireCompressed;
    public static BombBlock bombFireNuclear;

    public static BombBlock bombLevellingNormal;
    public static BombBlock bombLevellingCompressed;
    public static BombBlock bombLevellingNuclear;

    public static BombBlock bombLifeNormal;
    public static BombBlock bombLifeCompressed;
    public static BombBlock bombLifeNuclear;

    public static BombBlock bombStarNormal;
    public static BombBlock bombStarCompressed;
    public static BombBlock bombStarNuclear;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        ExplosionParameters normalParams = new ExplosionParameters(5.0f,  1, 0.3f, 1.0f, 3);
        ExplosionParameters compressedParams = new ExplosionParameters(20.0f, 2, 0.1f, 0.3f, 6);
        ExplosionParameters nuclearParams = new ExplosionParameters(65.0f, 4, 0.03f, 0.1f, 12);
        ExplosionParameters starNuclearParams = new ExplosionParameters(44.0f, 4, 0.03f, 0.1f, 12);

        bombNormal = new BombBlock(NAMESPACE.id("bomb_normal"), NeoExplosion::new, normalParams).setTranslationKey(NAMESPACE, "bombNormal");
        bombCompressed = new BombBlock(NAMESPACE.id("bomb_compressed"), NeoExplosion::new, compressedParams).setTranslationKey(NAMESPACE, "bombCompressed");
        bombNuclear = new BombBlock(NAMESPACE.id("bomb_nuclear"), NeoExplosion::new, nuclearParams).setTranslationKey(NAMESPACE, "bombNuclear");

        bombColdNormal = new BombBlock(NAMESPACE.id("bomb_cold_normal"), ColdExplosion::new, normalParams).setTranslationKey(NAMESPACE, "bombColdNormal");
        bombColdCompressed = new BombBlock(NAMESPACE.id("bomb_cold_compressed"), ColdExplosion::new, compressedParams).setTranslationKey(NAMESPACE, "bombColdCompressed");
        bombColdNuclear = new BombBlock(NAMESPACE.id("bomb_cold_nuclear"), ColdExplosion::new, nuclearParams).setTranslationKey(NAMESPACE, "bombColdNuclear");

        bombFireNormal = new BombBlock(NAMESPACE.id("bomb_fire_normal"), FireExplosion::new, normalParams).setTranslationKey(NAMESPACE, "bombFireNormal");
        bombFireCompressed = new BombBlock(NAMESPACE.id("bomb_fire_compressed"), FireExplosion::new, compressedParams).setTranslationKey(NAMESPACE, "bombFireCompressed");
        bombFireNuclear = new BombBlock(NAMESPACE.id("bomb_fire_nuclear"), FireExplosion::new, nuclearParams).setTranslationKey(NAMESPACE, "bombFireNuclear");

        bombLevellingNormal = new BombBlock(NAMESPACE.id("bomb_levelling_normal"), LevellingExplosion::new, normalParams).setTranslationKey(NAMESPACE, "bombLevellingNormal");
        bombLevellingCompressed = new BombBlock(NAMESPACE.id("bomb_levelling_compressed"), LevellingExplosion::new, compressedParams).setTranslationKey(NAMESPACE, "bombLevellingCompressed");
        bombLevellingNuclear = new BombBlock(NAMESPACE.id("bomb_levelling_nuclear"), LevellingExplosion::new, nuclearParams).setTranslationKey(NAMESPACE, "bombLevellingNuclear");

        bombLifeNormal = new BombBlock(NAMESPACE.id("bomb_life_normal"), LifeExplosion::new, normalParams).setTranslationKey(NAMESPACE, "bombLifeNormal");
        bombLifeCompressed = new BombBlock(NAMESPACE.id("bomb_life_compressed"), LifeExplosion::new, compressedParams).setTranslationKey(NAMESPACE, "bombLifeCompressed");
        bombLifeNuclear = new BombBlock(NAMESPACE.id("bomb_life_nuclear"), LifeExplosion::new, nuclearParams).setTranslationKey(NAMESPACE, "bombLifeNuclear");

        bombStarNormal = new BombBlock(NAMESPACE.id("bomb_star_normal"), StarExplosion::new, normalParams).setTranslationKey(NAMESPACE, "bombStarNormal");
        bombStarCompressed = new BombBlock(NAMESPACE.id("bomb_star_compressed"), StarExplosion::new, compressedParams).setTranslationKey(NAMESPACE, "bombStarCompressed");
        bombStarNuclear = new BombBlock(NAMESPACE.id("bomb_star_nuclear"), StarExplosion::new, starNuclearParams).setTranslationKey(NAMESPACE, "bombStarNuclear");
    }
}
