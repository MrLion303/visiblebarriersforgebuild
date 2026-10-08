package xyz.amymialee.visiblebarriers.mixin.client;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.visiblebarriers.VisibleBarriers;
import xyz.amymialee.visiblebarriers.VisibleConfig;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class AbstractBlockStateMixin {
    @Shadow public abstract Block getBlock();
    @Shadow public abstract boolean isAir();

    @Inject(method = "getRenderShape", at = @At("HEAD"), cancellable = true)
    private void visibleBarriers$renderInvisibleBlocks(CallbackInfoReturnable<RenderShape> cir) {
        Block block = getBlock();

        // The global switch reveals all invisible block models, with air controlled separately.
        if (VisibleBarriers.isVisibilityEnabled()) {
            if (!isAir() || VisibleConfig.isAirVisible()) {
                cir.setReturnValue(RenderShape.MODEL);
            }
            return;
        }

        // Individual hotkeys must work even when the global switch is off.
        if ((block == Blocks.BARRIER && VisibleBarriers.areBarriersEnabled())
                || (block == Blocks.LIGHT && VisibleBarriers.areLightsEnabled())
                || (block == Blocks.BUBBLE_COLUMN && VisibleBarriers.areBubbleColumnsEnabled())
                || (block == Blocks.STRUCTURE_VOID && VisibleBarriers.areStructureVoidsEnabled())) {
            cir.setReturnValue(RenderShape.MODEL);
        }
    }
}
