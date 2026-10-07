package xyz.amymialee.visiblebarriers.mixin.client;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.RenderShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.visiblebarriers.VisibleBarriers;
import xyz.amymialee.visiblebarriers.VisibleConfig;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class AbstractBlockStateMixin {
    @Shadow public abstract net.minecraft.world.level.block.Block getBlock();
    @Shadow public abstract boolean isAir();

    @Inject(method = "getRenderShape", at = @At("RETURN"), cancellable = true)
    private void visibleBarriers$invisibleModels(CallbackInfoReturnable<RenderShape> cir) {
        if (VisibleBarriers.isVisibilityEnabled()) {
            if (cir.getReturnValue() == RenderShape.INVISIBLE && (VisibleConfig.isAirVisible() || !isAir())) cir.setReturnValue(RenderShape.MODEL);
        } else if (getBlock() == Blocks.BARRIER && VisibleBarriers.areBarriersEnabled()
                || getBlock() == Blocks.LIGHT && VisibleBarriers.areLightsEnabled()
                || getBlock() == Blocks.BUBBLE_COLUMN && VisibleBarriers.areBubbleColumnsEnabled()
                || getBlock() == Blocks.STRUCTURE_VOID && VisibleBarriers.areStructureVoidsEnabled()) {
            cir.setReturnValue(RenderShape.MODEL);
        }
    }
}
