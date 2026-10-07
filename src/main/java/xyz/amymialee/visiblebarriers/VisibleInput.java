package xyz.amymialee.visiblebarriers;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.TimeArgument;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import xyz.amymialee.visiblebarriers.common.VisibleBarriersCommon;

@Mod.EventBusSubscriber(modid=VisibleBarriersCommon.MOD_ID, value=net.minecraftforge.api.distmarker.Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.FORGE)
public class VisibleInput {
    private static KeyMapping keyVisibility, keyBarriers, keyLights, keyStructureVoids, keyBubbleColumns, keyFullBright, keyTime, keyZoom;

    public static void registerKeys(RegisterKeyMappingsEvent event) {
        keyVisibility = new KeyMapping("key.visiblebarriers.visible", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, "category.visiblebarriers");
        keyBarriers = new KeyMapping("key.visiblebarriers.barriers", InputConstants.UNKNOWN.getValue(), "category.visiblebarriers");
        keyLights = new KeyMapping("key.visiblebarriers.lights", InputConstants.UNKNOWN.getValue(), "category.visiblebarriers");
        keyStructureVoids = new KeyMapping("key.visiblebarriers.structurevoids", InputConstants.UNKNOWN.getValue(), "category.visiblebarriers");
        keyBubbleColumns = new KeyMapping("key.visiblebarriers.bubblecolumns", InputConstants.UNKNOWN.getValue(), "category.bubblecolumns");
        keyFullBright = new KeyMapping("key.visiblebarriers.fullbright", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_M, "category.visiblebarriers");
        keyTime = new KeyMapping("key.visiblebarriers.time", InputConstants.UNKNOWN.getValue(), "category.visiblebarriers");
        keyZoom = new KeyMapping("key.visiblebarriers.zoom", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, "category.visiblebarriers");
        event.register(keyVisibility); event.register(keyBarriers); event.register(keyLights); event.register(keyStructureVoids);
        event.register(keyBubbleColumns); event.register(keyFullBright); event.register(keyTime); event.register(keyZoom);
    }

    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || Minecraft.getInstance().player == null) return;
        while (keyVisibility != null && keyVisibility.consumeClick()) VisibleBarriers.toggleVisible();
        while (keyBarriers != null && keyBarriers.consumeClick()) VisibleBarriers.toggleBarriers();
        while (keyLights != null && keyLights.consumeClick()) VisibleBarriers.toggleLights();
        while (keyStructureVoids != null && keyStructureVoids.consumeClick()) VisibleBarriers.toggleStructureVoids();
        while (keyBubbleColumns != null && keyBubbleColumns.consumeClick()) VisibleBarriers.toggleBubbleColumns();
        while (keyFullBright != null && keyFullBright.consumeClick()) VisibleBarriers.toggleFullBright();
        while (keyTime != null && keyTime.consumeClick()) VisibleBarriers.toggleTime();
        if (keyZoom != null && keyZoom.isDown()) {
            VisibleBarriers.holdingZoom = true;
        } else if (VisibleBarriers.holdingZoom) {
            VisibleBarriers.holdingZoom = false;
            VisibleBarriers.sendFeedback("visiblebarriers.feedback.zoom", "100");
            VisibleBarriers.zoomScroll = VisibleConfig.getBaseZoom();
        }
    }

    @SubscribeEvent
    public static void commands(RegisterClientCommandsEvent event) {
        var root = Commands.literal("visiblebarriers");
        root.then(Commands.literal("reload").executes(c -> {
            VisibleConfig.loadConfig(); VisibleConfig.saveConfig(); VisibleBarriers.reloadWorldRenderer();
            VisibleBarriers.sendFeedback("visiblebarriers.command.reload"); return 1;
        }));
        root.then(Commands.literal("visibility")
            .then(Commands.literal("everything").executes(c -> { VisibleBarriers.toggleVisible(); return 1; })
                .then(Commands.argument("visible", BoolArgumentType.bool()).executes(c -> { VisibleBarriers.setVisible(BoolArgumentType.getBool(c,"visible")); return 1; })))
            .then(Commands.literal("barriers").executes(c -> { VisibleBarriers.toggleBarriers(); return 1; })
                .then(Commands.argument("visible", BoolArgumentType.bool()).executes(c -> { VisibleBarriers.setBarriers(BoolArgumentType.getBool(c,"visible")); return 1; })))
            .then(Commands.literal("lights").executes(c -> { VisibleBarriers.toggleLights(); return 1; })
                .then(Commands.argument("visible", BoolArgumentType.bool()).executes(c -> { VisibleBarriers.setLights(BoolArgumentType.getBool(c,"visible")); return 1; })))
            .then(Commands.literal("structurevoids").executes(c -> { VisibleBarriers.toggleStructureVoids(); return 1; })
                .then(Commands.argument("visible", BoolArgumentType.bool()).executes(c -> { VisibleBarriers.setStructureVoids(BoolArgumentType.getBool(c,"visible")); return 1; })))
            .then(Commands.literal("bubblecolumns").executes(c -> { VisibleBarriers.toggleBubbleColumns(); return 1; })
                .then(Commands.argument("visible", BoolArgumentType.bool()).executes(c -> { VisibleBarriers.setBubbleColumns(BoolArgumentType.getBool(c,"visible")); return 1; }))));
        root.then(Commands.literal("fullbright").executes(c -> { VisibleBarriers.toggleFullBright(); return 1; })
            .then(Commands.argument("visible", BoolArgumentType.bool()).executes(c -> { VisibleBarriers.setFullBright(BoolArgumentType.getBool(c,"visible")); return 1; })));
        root.then(Commands.literal("time")
            .then(Commands.literal("enable").executes(c -> { VisibleConfig.setForcedTime(VisibleConfig.getForcedTime()); return 1; }))
            .then(Commands.literal("disable").executes(c -> { VisibleBarriers.setTime(false); return 1; }))
            .then(Commands.literal("set")
                .then(Commands.literal("day").executes(c -> { VisibleConfig.setForcedTime(1000); return 1; }))
                .then(Commands.literal("noon").executes(c -> { VisibleConfig.setForcedTime(6000); return 1; }))
                .then(Commands.literal("night").executes(c -> { VisibleConfig.setForcedTime(13000); return 1; }))
                .then(Commands.literal("midnight").executes(c -> { VisibleConfig.setForcedTime(18000); return 1; }))
                .then(Commands.argument("time", TimeArgument.time()).executes(c -> { VisibleConfig.setForcedTime(IntegerArgumentType.getInteger(c,"time")); return 1; }))));
        root.then(Commands.literal("weather")
            .then(Commands.literal("default").executes(c -> { VisibleBarriers.setWeather(VisibleBarriers.Weather.DEFAULT); return 1; }))
            .then(Commands.literal("clear").executes(c -> { VisibleBarriers.setWeather(VisibleBarriers.Weather.CLEAR); return 1; }))
            .then(Commands.literal("rain").executes(c -> { VisibleBarriers.setWeather(VisibleBarriers.Weather.RAIN); return 1; }))
            .then(Commands.literal("thunder").executes(c -> { VisibleBarriers.setWeather(VisibleBarriers.Weather.THUNDER); return 1; })));
        root.then(Commands.literal("settings")
            .then(Commands.literal("visibleair").executes(c -> { VisibleConfig.setVisibleAir(!VisibleConfig.isAirVisible()); VisibleBarriers.reloadWorldRenderer(); return 1; })
                .then(Commands.argument("visible", BoolArgumentType.bool()).executes(c -> { VisibleConfig.setVisibleAir(BoolArgumentType.getBool(c,"visible")); VisibleBarriers.reloadWorldRenderer(); return 1; })))
            .then(Commands.literal("hiddenparticles").executes(c -> { VisibleConfig.setHideParticles(!VisibleConfig.shouldHideParticles()); return 1; })
                .then(Commands.argument("visible", BoolArgumentType.bool()).executes(c -> { VisibleConfig.setHideParticles(BoolArgumentType.getBool(c,"visible")); return 1; })))
            .then(Commands.literal("sendfeedback").executes(c -> { VisibleConfig.setSendFeedback(!VisibleConfig.shouldSendFeedback()); return 1; })
                .then(Commands.argument("visible", BoolArgumentType.bool()).executes(c -> { VisibleConfig.setSendFeedback(BoolArgumentType.getBool(c,"visible")); return 1; }))));
        event.getDispatcher().register(root);
    }
}
