package io.github.cyborgcabbage.novusexplodus.events.init;

import io.github.cyborgcabbage.novusexplodus.block.BombBlock;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;

public class InitListener {
    static {
        EntrypointManager.registerLookup(MethodHandles.lookup());
    }

    @SuppressWarnings("UnstableApiUsage")
    public static final Namespace NAMESPACE = Namespace.resolve();

    public static final Logger LOGGER = NAMESPACE.getLogger();

    @EventListener
    private static void serverInit(InitEvent event) {
        LOGGER.info(NAMESPACE.toString());
    }

    // A static object holding our bock
    public static Block bombBlock;

    // An event listener listening to the BlockRegistryEvent
    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        bombBlock = new BombBlock(NAMESPACE.id("bomb")).setTranslationKey(NAMESPACE, "bomb");
    }
}
