package xyz.amymialee.visiblebarriers.mixin.client;

import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.visiblebarriers.VisibleBarriers;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow private float oldFov;
    @Shadow private float fov;
    @Inject(method = "tickFov", at = @At("TAIL"))
    private void visibleBarriers$zoom(CallbackInfo ci) {
        if (VisibleBarriers.isHoldingZoom()) {
            oldFov *= VisibleBarriers.getZoomModifier();
            fov *= VisibleBarriers.getZoomModifier();
        }
    }
}
