package xyz.amymialee.visiblebarriers.mixin.client;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.visiblebarriers.VisibleBarriers;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "hideTooltipPart", at = @At("HEAD"))
    private void visibleBarriers$showDetails(ItemStack.TooltipPart section, CallbackInfo ci) {
        if (VisibleBarriers.isVisibilityEnabled()) ci.cancel();
    }
}
