package xyz.amymialee.visiblebarriers.mixin.client;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EmptyRenderer;
import net.minecraft.client.renderer.PoseStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Marker;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.visiblebarriers.VisibleBarriers;
import xyz.amymialee.visiblebarriers.mixin.boxing.EntityRendererMixin;

@Mixin(EmptyRenderer.class)
public abstract class EmptyEntityRendererMixin<T extends Entity> extends EntityRendererMixin<T> {
    @Override
    protected void visibleBarriers$renderHead(T entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource consumers, int light, CallbackInfo ci) {
        if (!VisibleBarriers.isVisibilityEnabled()) return;
        if (entity instanceof AreaEffectCloud cloud) {
            ItemStack potion = Items.LINGERING_POTION.getDefaultInstance();
            potion.getOrCreateTag().putInt("CustomPotionColor", cloud.getColor());
            this.floater.setItem(potion);
            this.floater.render(entity, tickDelta, matrices, consumers, light);
        } else if (entity instanceof Marker) {
            this.floater.setItem(Items.STRUCTURE_BLOCK.getDefaultInstance());
            this.floater.render(entity, tickDelta, matrices, consumers, light);
        }
    }
}
