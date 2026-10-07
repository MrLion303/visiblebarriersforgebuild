package xyz.amymialee.visiblebarriers;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.amymialee.visiblebarriers.common.VisibleBarriersCommon;

@Mod(VisibleBarriersCommon.MOD_ID)
public class VisibleBarriersForge {
    public VisibleBarriersForge() {
        VisibleBarriersCommon.init(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
