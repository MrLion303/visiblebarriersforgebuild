package xyz.amymialee.visiblebarriers.common;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PistonBaseBlock;
import net.minecraft.world.level.block.PistonHeadBlock;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

public class VisibleBarriersCommon {
    public static final String MOD_ID = "visiblebarriers";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MOD_ID);

    public static final RegistryObject<BlockItem> MOVING_PISTON_BLOCK_ITEM =
            ITEMS.register("moving_piston", () -> new BlockItem(Blocks.MOVING_PISTON, new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<BlockItem> AIR_BLOCK_ITEM =
            ITEMS.register("air", () -> new BlockItem(Blocks.AIR, new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<BlockItem> CAVE_AIR_BLOCK_ITEM =
            ITEMS.register("cave_air", () -> new BlockItem(Blocks.CAVE_AIR, new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<BlockItem> VOID_AIR_BLOCK_ITEM =
            ITEMS.register("void_air", () -> new BlockItem(Blocks.VOID_AIR, new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<BlockItem> END_PORTAL_BLOCK_ITEM =
            ITEMS.register("end_portal", () -> new BlockItem(Blocks.END_PORTAL, new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<BlockItem> END_GATEWAY_BLOCK_ITEM =
            ITEMS.register("end_gateway", () -> new BlockItem(Blocks.END_GATEWAY, new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<BlockItem> BUBBLE_COLUMN_BLOCK_ITEM =
            ITEMS.register("bubble_column", () -> new BlockItem(Blocks.BUBBLE_COLUMN, new Item.Properties().rarity(Rarity.EPIC)));

    public static void init(IEventBus modBus) {
        ITEMS.register(modBus);
        modBus.addListener(VisibleBarriersCommon::creativeTab);
    }

    private static void creativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != CreativeModeTabs.OP_BLOCKS) return;

        for (PistonType type : PistonType.values()) {
            event.accept(makeVariant(MOVING_PISTON_BLOCK_ITEM.get(), PistonHeadBlock.TYPE.getName(), type.getSerializedName()));
        }
        event.accept(AIR_BLOCK_ITEM);
        event.accept(CAVE_AIR_BLOCK_ITEM);
        event.accept(VOID_AIR_BLOCK_ITEM);
        event.accept(END_PORTAL_BLOCK_ITEM);
        event.accept(END_GATEWAY_BLOCK_ITEM);
        event.accept(makeVariant(BUBBLE_COLUMN_BLOCK_ITEM.get(), "drag", "true"));
        event.accept(makeVariant(BUBBLE_COLUMN_BLOCK_ITEM.get(), "drag", "false"));
    }

    private static ItemStack makeVariant(Item item, String key, String value) {
        ItemStack stack = new ItemStack(item);
        CompoundTag state = new CompoundTag();
        state.putString(key, value);
        stack.getOrCreateTag().put("BlockStateTag", state);
        return stack;
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
