package xyz.amymialee.visiblebarriers.mixin.boxing;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.world.level.block.state.BlockBehaviour.class)
public abstract class AbstractBlockMixin {
    @Shadow public abstract Item asItem();

    @Inject(method = "skipRendering", at = @At("HEAD"), cancellable = true)
    public void visibleBarriers$isSideInvisible(BlockState state, BlockState adjacentState, Direction direction, CallbackInfoReturnable<Boolean> cir) {}

    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    public void visibleBarriers$getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {}

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void visibleBarriers$use(BlockState state, Level level, BlockPos pos, Player player,
                                    InteractionHand hand, BlockHitResult hit,
                                    CallbackInfoReturnable<InteractionResult> cir) {}
}
