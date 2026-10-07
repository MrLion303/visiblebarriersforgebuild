package xyz.amymialee.visiblebarriers.mixin;

import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityType.Builder.class)
public class EntityTypeMixin {
    @Shadow private int clientTrackingRange;
    @Inject(method = "clientTrackingRange", at = @At("RETURN"))
    private void visibleBarriers$minTrackingRange(int range, CallbackInfoReturnable<EntityType.Builder<?>> cir) {
        if (clientTrackingRange == 0) clientTrackingRange = 2;
    }
}
