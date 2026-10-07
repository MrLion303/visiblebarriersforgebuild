package xyz.amymialee.visiblebarriers.mixin.boxing;

import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.visiblebarriers.VisibleBarriers;
import xyz.amymialee.visiblebarriers.util.FloatyRenderer;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {
    @Unique protected FloatyRenderer<T> floater;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void visibleBarriers$giveRenderer(EntityRendererProvider.Context context, CallbackInfo ci) {
        this.floater = new FloatyRenderer<>(context.getItemRenderer(), Items.BARRIER.getDefaultInstance());
    }

    @Inject(method = "render", at = @At("HEAD"))
    protected void visibleBarriers$renderHead(T entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, CallbackInfo ci) {
        if (VisibleBarriers.isVisibilityEnabled() && entity.isInvisible()) {
            ItemStack stack = entity.getPickResult();
            if (stack != null) {
                if (!this.floater.getItem().is(stack.getItem())) this.floater.setItem(stack);
            } else {
                this.floater.setItem(Items.STRUCTURE_VOID.getDefaultInstance());
            }
            this.floater.render(entity, tickDelta, matrices, vertexConsumers, light);
        }
    }
}
