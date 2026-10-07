package xyz.amymialee.visiblebarriers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import xyz.amymialee.visiblebarriers.common.VisibleBarriersCommon;

@Mod.EventBusSubscriber(modid=VisibleBarriersCommon.MOD_ID, value=Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
public class VisibleBarriers {
    protected static boolean toggleVisible = false;
    protected static boolean toggleBarriers = false;
    protected static boolean toggleLights = false;
    protected static boolean toggleStructureVoids = false;
    protected static boolean toggleBubbleColumns = false;
    protected static boolean toggleFullBright = false;
    protected static boolean toggleTime = false;
    protected static boolean holdingZoom = false;
    protected static Weather setWeather = Weather.DEFAULT;
    protected static float zoomScroll = 1.0F;

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(Blocks.BARRIER, RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Blocks.STRUCTURE_VOID, RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Blocks.LIGHT, RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Blocks.AIR, RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Blocks.CAVE_AIR, RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Blocks.VOID_AIR, RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Blocks.MOVING_PISTON, RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Blocks.BUBBLE_COLUMN, RenderType.translucent());

            ItemProperties.register(VisibleBarriersCommon.MOVING_PISTON_BLOCK_ITEM.get(),
                    new ResourceLocation("sticky"),
                    (stack, level, entity, seed) -> {
                        CompoundTag tag = stack.getTagElement("BlockStateTag");
                        return tag != null && "sticky".equals(tag.getString(PistonHeadBlock.TYPE.getName())) ? 1.0F : 0.0F;
                    });
            ItemProperties.register(VisibleBarriersCommon.BUBBLE_COLUMN_BLOCK_ITEM.get(),
                    new ResourceLocation("drag"),
                    (stack, level, entity, seed) -> {
                        CompoundTag tag = stack.getTagElement("BlockStateTag");
                        return tag != null && "true".equals(tag.getString("drag")) ? 1.0F : 0.0F;
                    });
            VisibleConfig.loadConfig();
        });
    }

    public static void sendFeedback(String translatable, Object... args) {
        if (VisibleConfig.shouldSendFeedback()) {
            Player player = Minecraft.getInstance().player;
            if (player != null) player.displayClientMessage(net.minecraft.network.chat.Component.translatable(translatable, args), true);
        }
    }

    public static void booleanFeedback(String key, boolean value) {
        sendFeedback("[Visible Barriers] %s %s", net.minecraft.network.chat.Component.translatable(key),
                net.minecraft.network.chat.Component.translatable(value ? "visiblebarriers.enabled" : "visiblebarriers.disabled"));
    }

    public static void reloadWorldRenderer() {
        if (Minecraft.getInstance().levelRenderer != null) Minecraft.getInstance().levelRenderer.allChanged();
    }

    public static boolean isVisibilityEnabled() { return toggleVisible; }
    public static void toggleVisible() { setVisible(!toggleVisible); }
    public static void setVisible(boolean visible) { toggleVisible=visible; booleanFeedback("visiblebarriers.feedback.visible", visible); reloadWorldRenderer(); }
    public static boolean isFullBrightEnabled() { return toggleFullBright; }
    public static void toggleFullBright() { setFullBright(!toggleFullBright); }
    public static void setFullBright(boolean value) { toggleFullBright=value; booleanFeedback("visiblebarriers.feedback.fullbright", value); }
    public static boolean isTimeEnabled() { return toggleTime; }
    public static void toggleTime() { setTime(!toggleTime); }
    public static void setTime(boolean value) { toggleTime=value; booleanFeedback("visiblebarriers.feedback.time", value); }
    public static boolean areBarriersEnabled() { return toggleBarriers; }
    public static void toggleBarriers() { setBarriers(!toggleBarriers); }
    public static void setBarriers(boolean value) { toggleBarriers=value; booleanFeedback("visiblebarriers.feedback.barriers", value); reloadWorldRenderer(); }
    public static boolean areLightsEnabled() { return toggleLights; }
    public static void toggleLights() { setLights(!toggleLights); }
    public static void setLights(boolean value) { toggleLights=value; booleanFeedback("visiblebarriers.feedback.lights", value); reloadWorldRenderer(); }
    public static boolean areStructureVoidsEnabled() { return toggleStructureVoids; }
    public static void toggleStructureVoids() { setStructureVoids(!toggleStructureVoids); }
    public static void setStructureVoids(boolean value) { toggleStructureVoids=value; booleanFeedback("visiblebarriers.feedback.structurevoids", value); reloadWorldRenderer(); }
    public static boolean areBubbleColumnsEnabled() { return toggleBubbleColumns; }
    public static void toggleBubbleColumns() { setBubbleColumns(!toggleBubbleColumns); }
    public static void setBubbleColumns(boolean value) { toggleBubbleColumns=value; booleanFeedback("visiblebarriers.feedback.bubblecolumns", value); reloadWorldRenderer(); }
    public static Weather getWeather() { return setWeather; }
    public static void setWeather(Weather value) { setWeather=value; sendFeedback("visiblebarriers.command.weather", net.minecraft.network.chat.Component.translatable(value.translationKey)); }
    public static boolean isHoldingZoom() { return holdingZoom; }
    public static float getZoomModifier() { return Mth.clamp((float)(4f / Math.pow(zoomScroll,2)),0.001f,1f); }
    public static void modifyZoomModifier(float amount) { zoomScroll=Mth.clamp(zoomScroll-amount,0.01f,1000f); sendFeedback("visiblebarriers.feedback.zoom","%.0f".formatted(10000f/(getZoomModifier()*100))); }

    public enum Weather {
        DEFAULT(-1,-1,"visiblebarriers.weather.default"), CLEAR(0,0,"visiblebarriers.weather.clear"),
        RAIN(1,0,"visiblebarriers.weather.rain"), THUNDER(1,1,"visiblebarriers.weather.thunder");
        final int rain, thunder; final String translationKey;
        Weather(int rain,int thunder,String key){this.rain=rain;this.thunder=thunder;this.translationKey=key;}
        public int getRain(){return rain;} public int getThunder(){return thunder;}
    }
}
