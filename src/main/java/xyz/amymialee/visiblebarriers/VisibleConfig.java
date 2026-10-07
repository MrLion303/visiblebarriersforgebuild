package xyz.amymialee.visiblebarriers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraftforge.fml.loading.FMLPaths;
import xyz.amymialee.visiblebarriers.common.VisibleBarriersCommon;

import java.nio.file.Files;
import java.nio.file.Path;

public class VisibleConfig {
    private static final Path configFile = FMLPaths.CONFIGDIR.get().resolve("visiblebarriers.json");
    private static boolean visibleAir = false;
    private static boolean hideParticles = true;
    private static boolean sendFeedback = true;
    private static boolean solidLights = false;
    private static float baseZoom = 2.8f;
    private static long forcedTime = 6000;

    public static void setVisibleAir(boolean value) { visibleAir = value; saveConfig(); }
    public static void setHideParticles(boolean value) { hideParticles = value; saveConfig(); }
    public static void setSendFeedback(boolean value) { sendFeedback = value; saveConfig(); }

    public static void setForcedTime(long value) {
        forcedTime = value;
        VisibleBarriers.setTime(true);
        if (net.minecraft.client.Minecraft.getInstance().level != null) {
            net.minecraft.client.Minecraft.getInstance().level.setDayTime(forcedTime);
        }
    }

    public static boolean isAirVisible() { return visibleAir; }
    public static boolean shouldHideParticles() { return hideParticles; }
    public static boolean shouldSendFeedback() { return sendFeedback; }
    public static float getBaseZoom() { return baseZoom; }
    public static long getForcedTime() { return forcedTime; }
    public static boolean areLightsSolid() { return solidLights; }

    protected static void saveConfig() {
        try {
            JsonObject json = new JsonObject();
            json.addProperty("visibleAir", visibleAir);
            json.addProperty("hideParticles", hideParticles);
            json.addProperty("sendFeedback", sendFeedback);
            json.addProperty("baseZoom", baseZoom);
            if (solidLights) json.addProperty("solidLights", true);
            Files.writeString(configFile, new GsonBuilder().setPrettyPrinting().create().toJson(json));
        } catch (Exception e) {
            VisibleBarriersCommon.LOGGER.info(e.toString());
        }
    }

    protected static void loadConfig() {
        try {
            if (!Files.exists(configFile)) {
                VisibleBarriersCommon.LOGGER.info("Config data not found.");
                return;
            }
            JsonObject data = new Gson().fromJson(Files.readString(configFile), JsonObject.class);
            if (data.has("visibleAir")) visibleAir = data.get("visibleAir").getAsBoolean();
            if (data.has("hideParticles")) hideParticles = data.get("hideParticles").getAsBoolean();
            if (data.has("sendFeedback")) sendFeedback = data.get("sendFeedback").getAsBoolean();
            if (data.has("baseZoom")) baseZoom = data.get("baseZoom").getAsFloat();
            if (data.has("solidLights")) solidLights = data.get("solidLights").getAsBoolean();
        } catch (Exception e) {
            VisibleBarriersCommon.LOGGER.info("Error loading config data.");
            VisibleBarriersCommon.LOGGER.info(e.toString());
        }
    }
}
