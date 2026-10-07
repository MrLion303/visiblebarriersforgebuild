package xyz.amymialee.visiblebarriers.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.amymialee.visiblebarriers.VisibleConfig;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {
    @Redirect(
        method = "continueAttack",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$BlockStateBase;isAir()Z"
        )
    )
    private boolean visibleBarriers$breakAir(BlockState state) {
        return VisibleConfig.isAirVisible() ? false : state.isAir();
    }
}
