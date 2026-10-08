package xyz.amymialee.visiblebarriers;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import xyz.amymialee.visiblebarriers.common.VisibleBarriersCommon;

@Mod(VisibleBarriersCommon.MOD_ID)
public class VisibleBarriersForge {
    public VisibleBarriersForge() {
        // This mod changes only the local client's rendering and controls.
        ModLoadingContext.get().registerExtensionPoint(
                IExtensionPoint.DisplayTest.class,
                () -> new IExtensionPoint.DisplayTest(
                        () -> "visiblebarriers-client-only",
                        (remoteVersion, isFromServer) -> true
                )
        );

        var modBus = net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext.get().getModEventBus();
        VisibleBarriersCommon.init(modBus);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientOnly::init);
    }

    private static final class ClientOnly {
        private static void init() {
            MinecraftForge.EVENT_BUS.register(VisibleInput.class);
            net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext.get()
                    .getModEventBus().addListener(VisibleInput::registerKeys);
        }
    }
}
