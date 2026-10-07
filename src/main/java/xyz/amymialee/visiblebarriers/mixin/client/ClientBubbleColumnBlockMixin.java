package xyz.amymialee.visiblebarriers.mixin.client;

import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.visiblebarriers.mixin.boxing.BlockMixin;

@Mixin(BubbleColumnBlock.class)
public abstract class ClientBubbleColumnBlockMixin extends BlockMixin {
    @Override
    public void visibleBarriers$isSideInvisible(BlockState state, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
    @Override
    public void visibleBarriers$isTranslucent(BlockState state, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
