package xyz.amymialee.visiblebarriers;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.IExtensionPoint;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.DistExecutor;
import xyz.amymialee.visiblebarriers.common.VisibleBarriersCommon;

@Mod(VisibleBarriersCommon.MOD_ID)
public class VisibleBarriersForge {
    public VisibleBarriersForge() {
        // Este mod solo cambia el renderizado y los controles del cliente.
        ModLoadingContext.get().registerExtensionPoint(
                IExtensionPoint.DisplayTest.class,
                () -> new IExtensionPoint.DisplayTest(
                        () -> "visiblebarriers-client-only",
                        (remoteVersion, isFromServer) -> true
                )
        );

        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        VisibleBarriersCommon.init(modBus);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientOnly::init);
    }

    private static final class ClientOnly {
        private static void init() {
            var modBus = FMLJavaModLoadingContext.get().getModEventBus();
            modBus.addListener(VisibleInput::registerKeys);
            MinecraftForge.EVENT_BUS.register(VisibleInput.class);
        }
    }
}
