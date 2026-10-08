package xyz.amymialee.visiblebarriers.mixin;

import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.visiblebarriers.mixin.boxing.BlockMixin;

@Mixin(PistonHeadBlock.class)
public abstract class PistonExtensionBlockMixin extends BlockMixin {
    @Override
    public void visibleBarriers$isSideInvisible(BlockState state, BlockState stateFrom, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        Block block = (Block) (Object) this;
        if (stateFrom.is(block)) {
            cir.setReturnValue(true);
        }
    }

    @Override
    public void visibleBarriers$getPlacementState(BlockPlaceContext ctx, CallbackInfoReturnable<BlockState> cir) {
        cir.setReturnValue(defaultBlockState().setValue(PistonHeadBlock.FACING, ctx.getNearestLookingDirection().getOpposite()));
    }
}
