package xyz.amymialee.visiblebarriers.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.visiblebarriers.VisibleBarriers;

@Mixin(MouseHandler.class)
public class MouseMixin {
    @Shadow @Final private Minecraft minecraft;
    @Shadow private double accumulatedScrollY;

    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void visibleBarriers$scroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (window == minecraft.getWindow().getWindow() && VisibleBarriers.isHoldingZoom()) {
            double d = vertical * minecraft.options.mouseWheelSensitivity().get();
            accumulatedScrollY += d;
            int i = (int) accumulatedScrollY;
            if (i != 0) {
                accumulatedScrollY -= i;
                VisibleBarriers.modifyZoomModifier(-i);
            }
            ci.cancel();
        }
    }
}
