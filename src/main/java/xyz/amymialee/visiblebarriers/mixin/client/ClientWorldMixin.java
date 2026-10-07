package xyz.amymialee.visiblebarriers.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientLevel.ClientLevelData;
import net.minecraft.world.level.GameRules;
import net.minecraft.core.particles.ParticleOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.visiblebarriers.VisibleBarriers;
import xyz.amymialee.visiblebarriers.VisibleConfig;
import xyz.amymialee.visiblebarriers.mixin.boxing.WorldMixin;

@Mixin(ClientLevel.class)
public class ClientWorldMixin extends WorldMixin {
    @Inject(method = "tickTime", at = @At("HEAD"), cancellable = true)
    private void visibleBarriers$stopTime(CallbackInfo ci) {
        if (VisibleBarriers.isTimeEnabled()) ci.cancel();
    }

    @Inject(method = "doAnimateTick", at = @At("HEAD"), cancellable = true)
    private void visibleBarriers$removeParticles(int x, int y, int z, int radius, net.minecraft.util.RandomSource random, CallbackInfo ci) {
        if (VisibleConfig.shouldHideParticles()) ci.cancel();
    }

    @Override
    protected void visibleBarriers$setRain(float delta, CallbackInfoReturnable<Float> cir) {
        int rain = VisibleBarriers.getWeather().getRain();
        if (rain >= 0) cir.setReturnValue((float)rain);
    }
    @Override
    protected void visibleBarriers$setThunder(float delta, CallbackInfoReturnable<Float> cir) {
        int thunder = VisibleBarriers.getWeather().getThunder();
        if (thunder >= 0) cir.setReturnValue((float)thunder);
    }

    @Mixin(ClientLevelData.class)
    static class ClientWorldPropertiesMixin {
        @Inject(method = "getDayTime", at = @At("HEAD"), cancellable = true)
        private void visibleBarriers$forceTime(CallbackInfoReturnable<Long> cir) {
            if (VisibleBarriers.isTimeEnabled()) cir.setReturnValue(VisibleConfig.getForcedTime());
        }
    }
}
