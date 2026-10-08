package xyz.amymialee.visiblebarriers.common;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import org.slf4j.Logger;

public class VisibleBarriersCommon {
    public static final String MOD_ID = "visiblebarriers";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<?> ITEMS = DeferredRegister.create(net.minecraft.core.registries.Registries.ITEM, MOD_ID);

    public static void init(IEventBus modBus) {
        // No se registran ítems propios. El mod utiliza únicamente los bloques vanilla existentes.
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
