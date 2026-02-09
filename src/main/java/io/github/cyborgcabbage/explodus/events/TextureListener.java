package io.github.cyborgcabbage.explodus.events;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.TextureUtil;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.client.texture.atlas.ExpandableAtlas;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

public class TextureListener {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    // A static int that will hold the texture id so we can later reference it from the block itself
    public static int bombColdCompressedSide;
    public static int bombColdNormalSide;
    public static int bombColdNuclearSide;

    public static int bombExtinguishingCompressedSide;
    public static int bombExtinguishingNormalSide;
    public static int bombExtinguishingNuclearSide;

    public static int bombFireCompressedSide;
    public static int bombFireNormalSide;
    public static int bombFireNuclearSide;

    public static int bombLevellingCompressedSide;
    public static int bombLevellingNormalSide;
    public static int bombLevellingNuclearSide;

    public static int bombLifeBottom;
    public static int bombLifeCompressedSide;
    public static int bombLifeCompressedTop;
    public static int bombLifeNormalSide;
    public static int bombLifeNormalTop;
    public static int bombLifeNuclearSide;
    public static int bombLifeNuclearTop;

    public static int bombMagneticCompressedBottom;
    public static int bombMagneticCompressedSide;
    public static int bombMagneticCompressedTop;
    public static int bombMagneticNormalBottom;
    public static int bombMagneticNormalSide;
    public static int bombMagneticNormalTop;
    public static int bombMagneticNuclearBottom;
    public static int bombMagneticNuclearSide;
    public static int bombMagneticNuclearTop;

    public static int bombStarCompressedSide;
    public static int bombStarNormalSide;
    public static int bombStarNuclearSide;

    public static int bombCompressedBottom;
    public static int bombCompressedSide;
    public static int bombCompressedTop;
    public static int bombNormalBottom;
    public static int bombNormalSide;
    public static int bombNormalTop;
    public static int bombNuclearBottom;
    public static int bombNuclearSide;
    public static int bombNuclearTop;

    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        // Get the atlas and store it in a field, this is not necessary but will come in handy when you are adding multiple textures
        ExpandableAtlas atlas = Atlases.getTerrain();

        bombColdCompressedSide = atlas.addTexture(NAMESPACE.id("block/bomb/cold/compressed_side")).index;
        bombColdNormalSide = atlas.addTexture(NAMESPACE.id("block/bomb/cold/normal_side")).index;
        bombColdNuclearSide = atlas.addTexture(NAMESPACE.id("block/bomb/cold/nuclear_side")).index;

        bombExtinguishingCompressedSide = atlas.addTexture(NAMESPACE.id("block/bomb/extinguishing/compressed_side")).index;
        bombExtinguishingNormalSide = atlas.addTexture(NAMESPACE.id("block/bomb/extinguishing/normal_side")).index;
        bombExtinguishingNuclearSide = atlas.addTexture(NAMESPACE.id("block/bomb/extinguishing/nuclear_side")).index;

        bombFireCompressedSide = atlas.addTexture(NAMESPACE.id("block/bomb/fire/compressed_side")).index;
        bombFireNormalSide = atlas.addTexture(NAMESPACE.id("block/bomb/fire/normal_side")).index;
        bombFireNuclearSide = atlas.addTexture(NAMESPACE.id("block/bomb/fire/nuclear_side")).index;

        bombLevellingCompressedSide = atlas.addTexture(NAMESPACE.id("block/bomb/levelling/compressed_side")).index;
        bombLevellingNormalSide = atlas.addTexture(NAMESPACE.id("block/bomb/levelling/normal_side")).index;
        bombLevellingNuclearSide = atlas.addTexture(NAMESPACE.id("block/bomb/levelling/nuclear_side")).index;

        bombLifeBottom = atlas.addTexture(NAMESPACE.id("block/bomb/life/bottom")).index;
        bombLifeCompressedSide = atlas.addTexture(NAMESPACE.id("block/bomb/life/compressed_side")).index;
        bombLifeCompressedTop = atlas.addTexture(NAMESPACE.id("block/bomb/life/compressed_top")).index;
        bombLifeNormalSide = atlas.addTexture(NAMESPACE.id("block/bomb/life/normal_side")).index;
        bombLifeNormalTop = atlas.addTexture(NAMESPACE.id("block/bomb/life/normal_top")).index;
        bombLifeNuclearSide = atlas.addTexture(NAMESPACE.id("block/bomb/life/nuclear_side")).index;
        bombLifeNuclearTop = atlas.addTexture(NAMESPACE.id("block/bomb/life/nuclear_top")).index;

        bombMagneticCompressedBottom = atlas.addTexture(NAMESPACE.id("block/bomb/magnetic/compressed_bottom")).index;
        bombMagneticCompressedSide = atlas.addTexture(NAMESPACE.id("block/bomb/magnetic/compressed_side")).index;
        bombMagneticCompressedTop = atlas.addTexture(NAMESPACE.id("block/bomb/magnetic/compressed_top")).index;
        bombMagneticNormalBottom = atlas.addTexture(NAMESPACE.id("block/bomb/magnetic/normal_bottom")).index;
        bombMagneticNormalSide = atlas.addTexture(NAMESPACE.id("block/bomb/magnetic/normal_side")).index;
        bombMagneticNormalTop = atlas.addTexture(NAMESPACE.id("block/bomb/magnetic/normal_top")).index;
        bombMagneticNuclearBottom = atlas.addTexture(NAMESPACE.id("block/bomb/magnetic/nuclear_bottom")).index;
        bombMagneticNuclearSide = atlas.addTexture(NAMESPACE.id("block/bomb/magnetic/nuclear_side")).index;
        bombMagneticNuclearTop = atlas.addTexture(NAMESPACE.id("block/bomb/magnetic/nuclear_top")).index;

        bombStarCompressedSide = atlas.addTexture(NAMESPACE.id("block/bomb/star/compressed_side")).index;
        bombStarNormalSide = atlas.addTexture(NAMESPACE.id("block/bomb/star/normal_side")).index;
        bombStarNuclearSide = atlas.addTexture(NAMESPACE.id("block/bomb/star/nuclear_side")).index;

        bombCompressedBottom = atlas.addTexture(NAMESPACE.id("block/bomb/compressed_bottom")).index;
        bombCompressedSide = atlas.addTexture(NAMESPACE.id("block/bomb/compressed_side")).index;
        bombCompressedTop = atlas.addTexture(NAMESPACE.id("block/bomb/compressed_top")).index;
        bombNormalBottom = atlas.addTexture(NAMESPACE.id("block/bomb/normal_bottom")).index;
        bombNormalSide = atlas.addTexture(NAMESPACE.id("block/bomb/normal_side")).index;
        bombNormalTop = atlas.addTexture(NAMESPACE.id("block/bomb/normal_top")).index;
        bombNuclearBottom = atlas.addTexture(NAMESPACE.id("block/bomb/nuclear_bottom")).index;
        bombNuclearSide = atlas.addTexture(NAMESPACE.id("block/bomb/nuclear_side")).index;
        bombNuclearTop = atlas.addTexture(NAMESPACE.id("block/bomb/nuclear_top")).index;

        BlockListener.bombNormal.setTextureIds(bombNormalBottom, bombNormalSide, bombNormalTop);
        BlockListener.bombCompressed.setTextureIds(bombCompressedBottom, bombCompressedSide, bombCompressedTop);
        BlockListener.bombNuclear.setTextureIds(bombNuclearBottom, bombNuclearSide, bombNuclearTop);

        BlockListener.bombColdNormal.setTextureIds(bombNormalBottom, bombColdNormalSide, bombNormalTop);
        BlockListener.bombColdCompressed.setTextureIds(bombCompressedBottom, bombColdCompressedSide, bombCompressedTop);
        BlockListener.bombColdNuclear.setTextureIds(bombNuclearBottom, bombColdNuclearSide, bombNuclearTop);

        BlockListener.bombExtinguishingNormal.setTextureIds(bombNormalBottom, bombExtinguishingNormalSide, bombNormalTop);
        BlockListener.bombExtinguishingCompressed.setTextureIds(bombCompressedBottom, bombExtinguishingCompressedSide, bombCompressedTop);
        BlockListener.bombExtinguishingNuclear.setTextureIds(bombNuclearBottom, bombExtinguishingNuclearSide, bombNuclearTop);

        BlockListener.bombFireNormal.setTextureIds(bombNormalBottom, bombFireNormalSide, bombNormalTop);
        BlockListener.bombFireCompressed.setTextureIds(bombCompressedBottom, bombFireCompressedSide, bombCompressedTop);
        BlockListener.bombFireNuclear.setTextureIds(bombNuclearBottom, bombFireNuclearSide, bombNuclearTop);

        BlockListener.bombLevellingNormal.setTextureIds(Block.OBSIDIAN.textureId, bombLevellingNormalSide, bombNormalTop);
        BlockListener.bombLevellingCompressed.setTextureIds(Block.OBSIDIAN.textureId, bombLevellingCompressedSide, bombCompressedTop);
        BlockListener.bombLevellingNuclear.setTextureIds(Block.OBSIDIAN.textureId, bombLevellingNuclearSide, bombNuclearTop);

        BlockListener.bombLifeNormal.setTextureIds(bombLifeBottom, bombLifeNormalSide, bombLifeNormalTop);
        BlockListener.bombLifeCompressed.setTextureIds(bombLifeBottom, bombLifeCompressedSide, bombLifeCompressedTop);
        BlockListener.bombLifeNuclear.setTextureIds(bombLifeBottom, bombLifeNuclearSide, bombLifeNuclearTop);

        BlockListener.bombMagneticNormal.setTextureIds(bombMagneticNormalBottom, bombMagneticNormalSide, bombMagneticNormalTop);
        BlockListener.bombMagneticCompressed.setTextureIds(bombMagneticCompressedBottom, bombMagneticCompressedSide, bombMagneticCompressedTop);
        BlockListener.bombMagneticNuclear.setTextureIds(bombMagneticNuclearBottom, bombMagneticNuclearSide, bombMagneticNuclearTop);

        BlockListener.bombStarNormal.setTextureIds(bombNormalBottom, bombStarNormalSide, bombNormalTop);
        BlockListener.bombStarCompressed.setTextureIds(bombCompressedBottom, bombStarCompressedSide, bombCompressedTop);
        BlockListener.bombStarNuclear.setTextureIds(bombNuclearBottom, bombStarNuclearSide, bombNuclearTop);
    }
}
