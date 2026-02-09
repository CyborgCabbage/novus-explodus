package io.github.cyborgcabbage.explodus.events;

import io.github.cyborgcabbage.explodus.entity.PrimedBombEntity;
import io.github.cyborgcabbage.explodus.render.PrimedBombRenderer;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

public class EntityRendererListener {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @EventListener
    public static void registerEntityRenderers(EntityRendererRegisterEvent event) {
        event.renderers.put(PrimedBombEntity.class, new PrimedBombRenderer());
    }
}
