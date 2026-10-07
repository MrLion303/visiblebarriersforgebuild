package xyz.amymialee.visiblebarriers.mixin.client;

import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeModeTab.TabVisibility.class)
public class DisplayContextMixin {
    @Inject(method = "contains", at = @At("HEAD"), cancellable = true)
    private void visibleBarriers$overridePermission(CreativeModeTab.TabVisibility visibility, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
