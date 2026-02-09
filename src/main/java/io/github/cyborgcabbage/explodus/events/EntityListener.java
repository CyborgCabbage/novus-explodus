package io.github.cyborgcabbage.explodus.events;

import io.github.cyborgcabbage.explodus.block.BombBlock;
import io.github.cyborgcabbage.explodus.entity.PrimedBombEntity;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.EntityHandlerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

public class EntityListener {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    public static Block bombBlock;

    @EventListener
    public void registerEntities(EntityRegister event) {
        event.register(PrimedBombEntity.class, PrimedBombEntity.ID.toString());
    }

    @EventListener
    public void registerEntityHandlers(EntityHandlerRegistryEvent event) {
        event.register(PrimedBombEntity.ID, PrimedBombEntity::new);
    }
}
