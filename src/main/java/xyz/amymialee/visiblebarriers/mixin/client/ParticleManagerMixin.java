package xyz.amymialee.visiblebarriers.mixin.client;

import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.visiblebarriers.VisibleConfig;

@Mixin(ParticleEngine.class)
public class ParticleManagerMixin {
    @Inject(method = "crack", at = @At("HEAD"), cancellable = true)
    private void visibleBarriers$removeWaterBreakParticles(BlockPos pos, net.minecraft.core.Direction direction, CallbackInfo ci) {
        FluidState fluid = net.minecraft.client.Minecraft.getInstance().level.getFluidState(pos);
        if (!fluid.isEmpty()) ci.cancel();
    }
}
