package xyz.amymialee.visiblebarriers.mixin.client;

import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.amymialee.visiblebarriers.VisibleBarriers;

@Mixin(LightTexture.class)
public class LightmapTextureManagerMixin {
    @Redirect(method = "updateLightTexture", at = @At(value = "INVOKE", target = "java/lang/Double.floatValue()F"))
    private float visibleBarriers$fullBright(Double number) {
        return VisibleBarriers.isFullBrightEnabled() ? 255f : number.floatValue();
    }
}
