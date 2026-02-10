package io.github.cyborgcabbage.explodus.mixin;

import io.github.cyborgcabbage.explodus.Explodus;
import io.github.cyborgcabbage.explodus.events.ItemListener;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.GhastEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "dropItems", at = @At(value = "TAIL"))
    void addGhastDrop(CallbackInfo ci) {
        Explodus.LOGGER.info("AHIUHAF");
        if (((Object)this) instanceof GhastEntity ghastEntity) {
            ghastEntity.dropItem(ItemListener.ghastHeart.id, 1);
        }
    }
}
