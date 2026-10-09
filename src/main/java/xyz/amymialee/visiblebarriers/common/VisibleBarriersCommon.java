package xyz.amymialee.visiblebarriers.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisibleBarriersCommon implements ModInitializer {
    public static final String MOD_ID = "visiblebarriers";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Identifier MOD_INSTALLED_PACKET = id("mod_installed");

    @Override
    public void onInitialize() {
        ServerPlayNetworking.registerGlobalReceiver(MOD_INSTALLED_PACKET, (server, player, handler, buf, responseSender) -> {
            LOGGER.info("{} has mod Visible Barriers installed.", player.getEntityName());
            ServerPlayNetworking.send(player, MOD_INSTALLED_PACKET, buf);
        });
    }

    public static Identifier id(String... path) {
        return new Identifier(MOD_ID, String.join(".", path));
    }
}