package xyz.amymialee.visiblebarriers.mixin.client;

import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Inject;
import org.spongepowered.asm.mixin.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.visiblebarriers.VisibleConfig;
import xyz.amymialee.visiblebarriers.VisibleBarriers;
import xyz.amymialee.visiblebarriers.mixin.boxing.BlockMixin;

@Mixin(LightBlock.class)
public abstract class LightBlockMixin extends BlockMixin {
    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    public void visibleBarriers$visibleOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (VisibleBarriers.isVisibilityEnabled() || VisibleBarriers.areLightsEnabled() || context == CollisionContext.empty()) cir.setReturnValue(Shapes.block());
    }

    @Override
    public void visibleBarriers$getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        boolean isPlayer = context instanceof net.minecraft.world.phys.shapes.EntityCollisionContext entityContext
                && entityContext.getEntity() instanceof net.minecraft.world.entity.player.Player;
        if (!isPlayer || !VisibleConfig.areLightsSolid()) cir.setReturnValue(Shapes.empty());
        super.visibleBarriers$getCollisionShape(state, world, pos, context, cir);
    }
}
