package xyz.amymialee.visiblebarriers.mixin;

import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.visiblebarriers.common.VisibleBarriersCommon;
import xyz.amymialee.visiblebarriers.mixin.boxing.BlockMixin;

@Mixin(PistonHeadBlock.class)
public abstract class PistonExtensionBlockMixin extends BlockMixin {
    @Override
    public void visibleBarriers$getPlacementState(BlockPlaceContext ctx, CallbackInfoReturnable<BlockState> cir) {
        cir.setReturnValue(defaultBlockState().setValue(PistonHeadBlock.FACING, ctx.getNearestLookingDirection().getOpposite()));
    }

    @Inject(method = "getCloneItemStack", at = @At("HEAD"), cancellable = true)
    public void visibleBarriers$pickStack(BlockState state, BlockGetter world, BlockPos pos, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack stack = new ItemStack(VisibleBarriersCommon.MOVING_PISTON_BLOCK_ITEM.get());
        CompoundTag tag = new CompoundTag();
        tag.putString(PistonHeadBlock.TYPE.getName(), state.getValue(PistonHeadBlock.TYPE).getSerializedName());
        stack.getOrCreateTag().put("BlockStateTag", tag);
        cir.setReturnValue(stack);
    }
}
