package xyz.amymialee.visiblebarriers.mixin;

import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.visiblebarriers.VisibleBarriers;
import xyz.amymialee.visiblebarriers.common.VisibleBarriersCommon;
import xyz.amymialee.visiblebarriers.mixin.boxing.BlockMixin;

@Mixin(BubbleColumnBlock.class)
public abstract class BubbleColumnBlockMixin extends BlockMixin {
    @Override
    public void visibleBarriers$getPlacementState(BlockPlaceContext ctx, CallbackInfoReturnable<BlockState> cir) {
        ItemStack stack = ctx.getItemInHand();
        CompoundTag tag = stack.getTagElement("BlockStateTag");
        boolean drag = tag == null || !"false".equals(tag.getString("drag"));
        cir.setReturnValue(defaultBlockState().setValue(BlockStateProperties.DRAG, drag));
    }

    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    public void visibleBarriers$visibleOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (VisibleBarriers.isVisibilityEnabled() || VisibleBarriers.areBubbleColumnsEnabled() || context == CollisionContext.empty()) cir.setReturnValue(Shapes.block());
    }
}
