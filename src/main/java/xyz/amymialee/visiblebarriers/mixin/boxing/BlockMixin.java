package xyz.amymialee.visiblebarriers.mixin.boxing;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlockMixin {
    @Shadow public abstract BlockState defaultBlockState();

    @Inject(method = "skipRendering", at = @At("HEAD"), cancellable = true)
    public void visibleBarriers$isTranslucent(BlockState state, net.minecraft.core.Direction direction, CallbackInfoReturnable<Boolean> cir) {}

    @Inject(method = "getStateForPlacement", at = @At("HEAD"), cancellable = true)
    public void visibleBarriers$getPlacementState(BlockPlaceContext ctx, CallbackInfoReturnable<BlockState> cir) {}
}
