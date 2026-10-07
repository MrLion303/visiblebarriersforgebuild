package xyz.amymialee.visiblebarriers.util;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import com.mojang.math.Axis;

public class FloatyRenderer<T extends Entity> {
    private final ItemRenderer renderer;
    private ItemStack stack;

    public FloatyRenderer(ItemRenderer renderer, ItemStack stack) { this.renderer = renderer; this.stack = stack; }
    public void render(T entity, float partialTick, PoseStack matrices, MultiBufferSource consumers, int light) {
        matrices.pushPose();
        BakedModel model = renderer.getModel(stack, entity.level(), null, entity.getId());
        matrices.translate(0.0D, entity.getBbHeight() / 2.0D, 0.0D);
        matrices.mulPose(Axis.YP.rotation(-((entity.tickCount + partialTick) * 8) / 20.0f));
        renderer.render(stack, ItemDisplayContext.GROUND, false, matrices, consumers, light, OverlayTexture.NO_OVERLAY, model);
        matrices.popPose();
    }
    public ItemStack getItem(){return stack;}
    public void setItem(ItemStack stack){this.stack=stack;}
}
