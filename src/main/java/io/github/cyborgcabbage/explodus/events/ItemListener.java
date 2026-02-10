package io.github.cyborgcabbage.explodus.events;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Namespace;

public class ItemListener {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    public static Item compressedGunPowder;
    public static Item supercompressedGunPowder;
    public static Item ghastHeart;
    public static Item bedrockShard;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        compressedGunPowder = new TemplateItem(NAMESPACE.id("compressed_gunpowder")).setTranslationKey(NAMESPACE, "compressedGunpowder");
        supercompressedGunPowder = new TemplateItem(NAMESPACE.id("supercompressed_gunpowder")).setTranslationKey(NAMESPACE, "supercompressedGunpowder");
        ghastHeart = new TemplateItem(NAMESPACE.id("ghast_heart")).setTranslationKey(NAMESPACE, "ghastHeart");
        bedrockShard = new TemplateItem(NAMESPACE.id("bedrock_shard")).setTranslationKey(NAMESPACE, "bedrockShard");
    }
}
