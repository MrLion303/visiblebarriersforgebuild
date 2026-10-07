package xyz.amymialee.visiblebarriers.mixin;

import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.visiblebarriers.mixin.boxing.BlockMixin;

@Mixin(AirBlock.class)
public abstract class AirBlockMixin extends BlockMixin {
    @Override
    public void visibleBarriers$isSideInvisible(BlockState state, net.minecraft.core.Direction direction, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Override
    public void visibleBarriers$isTranslucent(BlockState state, net.minecraft.core.Direction direction, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    public void visibleBarriers$visibleOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (this.asItem() != Items.AIR && context.isHolding(this.asItem()) || context == CollisionContext.empty()) {
            cir.setReturnValue(Shapes.block());
        }
    }

    @Override
    public void visibleBarriers$getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (!(context instanceof net.minecraft.world.phys.shapes.EntityCollisionContext entityContext) ||
            !(entityContext.getEntity() instanceof net.minecraft.world.entity.player.Player)) {
            cir.setReturnValue(Shapes.empty());
        }
        super.visibleBarriers$getCollisionShape(state, world, pos, context, cir);
    }
}
