package xyz.amymialee.visiblebarriers.mixin.client;

import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.visiblebarriers.VisibleBarriers;
import xyz.amymialee.visiblebarriers.mixin.boxing.BlockMixin;

@Mixin(PistonHeadBlock.class)
public abstract class ClientPistonExtensionBlockMixin extends BlockMixin {
    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    public void visibleBarriers$makeOutlineVisible(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (VisibleBarriers.isVisibilityEnabled()) {
            cir.setReturnValue(Shapes.block());
        }
    }
}
