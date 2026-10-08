package xyz.amymialee.visiblebarriers.common;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import org.slf4j.Logger;

public class VisibleBarriersCommon {
    public static final String MOD_ID = "visiblebarriers";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init(IEventBus modBus) {
        // El mod no registra ítems ni añade contenido a las pestañas creativas.
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
