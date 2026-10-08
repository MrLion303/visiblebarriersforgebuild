package xyz.amymialee.visiblebarriers;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.amymialee.visiblebarriers.common.VisibleBarriersCommon;

@Mod(VisibleBarriersCommon.MOD_ID)
public class VisibleBarriersForge {
    public VisibleBarriersForge() {
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        VisibleBarriersCommon.init(modBus);
        modBus.addListener(VisibleInput::registerKeys);
        MinecraftForge.EVENT_BUS.register(VisibleInput.class);
    }
}
