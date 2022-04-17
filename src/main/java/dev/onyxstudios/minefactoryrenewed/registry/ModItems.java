package dev.onyxstudios.minefactoryrenewed.registry;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import dev.onyxstudios.minefactoryrenewed.item.*;
import dev.onyxstudios.minefactoryrenewed.item.syringe.*;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MinefactoryRenewed.MODID);

    public static final Item.Properties PROPERTIES = new Item.Properties().tab(MinefactoryRenewed.TAB);
    private static final Item.Properties BUCKET_PROPS = new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(MinefactoryRenewed.TAB);

    //Upgrades
    public static final RegistryObject<Item> IRON_UPGRADE = ITEMS.register("iron_upgrade", () -> new MachineUpgradeItem(1));
    public static final RegistryObject<Item> COPPER_UPGRADE = ITEMS.register("copper_upgrade", () -> new MachineUpgradeItem(2));
    public static final RegistryObject<Item> GOLD_UPGRADE = ITEMS.register("gold_upgrade", () -> new MachineUpgradeItem(3));
    public static final RegistryObject<Item> OBSIDIAN_UPGRADE = ITEMS.register("obsidian_upgrade", () -> new MachineUpgradeItem(5));
    public static final RegistryObject<Item> QUARTZ_UPGRADE = ITEMS.register("quartz_upgrade", () -> new MachineUpgradeItem(4));
    public static final RegistryObject<Item> DIAMOND_UPGRADE = ITEMS.register("diamond_upgrade", () -> new MachineUpgradeItem(6));
    public static final RegistryObject<Item> EMERALD_UPGRADE = ITEMS.register("emerald_upgrade", () -> new MachineUpgradeItem(7));
    public static final RegistryObject<Item> NETHERITE_UPGRADE = ITEMS.register("netherite_upgrade", () -> new MachineUpgradeItem(8));

    //Rubber Items
    public static final RegistryObject<Item> RAW_RUBBER = ITEMS.register("raw_rubber", () -> new Item(PROPERTIES));
    public static final RegistryObject<Item> RUBBER = ITEMS.register("rubber", () -> new Item(PROPERTIES));
    public static final RegistryObject<Item> RAW_PLASTIC = ITEMS.register("raw_plastic", () -> new Item(PROPERTIES));
    public static final RegistryObject<Item> PLASTIC_SHEETS = ITEMS.register("plastic_sheets", () -> new Item(PROPERTIES));

    public static final RegistryObject<Item> SAFARI_NET = ITEMS.register("safari_net", () -> new SafariNetItem(false));
    public static final RegistryObject<Item> SAFARI_NET_SINGLE = ITEMS.register("safari_net_single", () -> new SafariNetItem(true));
    public static final RegistryObject<Item> SAFARI_NET_LAUNCHER = ITEMS.register("safari_net_launcher", SafariNetLauncherItem::new);

    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench", WrenchItem::new);
    public static final RegistryObject<Item> SLUDGE_BUCKET = ITEMS.register("sludge_bucket", () -> new BucketItem(ModBlocks.SLUDGE, BUCKET_PROPS));
    public static final RegistryObject<Item> INDUSTRIAL_FERTILIZER = ITEMS.register("industrial_fertilizer", () -> new Item(PROPERTIES));

    public static final RegistryObject<Item> MEAT_BUCKET = ITEMS.register("meat_bucket", () -> new BucketItem(ModBlocks.MEAT, BUCKET_PROPS));
    public static final RegistryObject<Item> RAW_MEAT_INGOT = ITEMS.register("raw_meat_ingot", () -> new MeatIngotItem(4, 0.2f));
    public static final RegistryObject<Item> RAW_MEAT_NUGGET = ITEMS.register("raw_meat_nugget", () -> new MeatIngotItem(1, 0.1f));
    public static final RegistryObject<Item> COOKED_MEAT_INGOT = ITEMS.register("cooked_meat_ingot", () -> new MeatIngotItem(10, 1));

    public static final RegistryObject<Item> PINK_SLIME_BUCKET = ITEMS.register("pink_slime_bucket", () -> new BucketItem(ModBlocks.PINK_SLIME, BUCKET_PROPS));
    public static final RegistryObject<Item> PINK_SLIME_BALL = ITEMS.register("pink_slime_ball", () -> new Item(PROPERTIES));
    public static final RegistryObject<Item> PINK_SLIME_CRYSTAL = ITEMS.register("pink_slime_crystal", () -> new Item(PROPERTIES));

    public static final RegistryObject<Item> ESSENCE_BUCKET = ITEMS.register("essence_bucket", () -> new BucketItem(ModBlocks.ESSENCE, BUCKET_PROPS));
    public static final RegistryObject<Item> SEWAGE_BUCKET = ITEMS.register("sewage_bucket", () -> new BucketItem(ModBlocks.SEWAGE, BUCKET_PROPS));
    public static final RegistryObject<Item> STEAM_BUCKET = ITEMS.register("steam_bucket", () -> new BucketItem(ModBlocks.STEAM, BUCKET_PROPS));

    //Syringes
    public static final RegistryObject<Item> EMPTY_SYRINGE = ITEMS.register("empty_syringe", () ->
            new Item(new Item.Properties().tab(MinefactoryRenewed.TAB).stacksTo(1)));
    public static final RegistryObject<Item> DE_ZOMBIFICATION_SYRINGE =
            ITEMS.register("de_zombification_syringe", DeZombieSyringe::new);
    public static final RegistryObject<Item> SLIME_EMBIGGENING_SYRINGE =
            ITEMS.register("slime_embiggening_syringe", SlimeSyringe::new);
    public static final RegistryObject<Item> GROWTH_SYRINGE = ITEMS.register("growth_syringe", GrowthSyringe::new);
    public static final RegistryObject<Item> HEALTH_SYRINGE = ITEMS.register("health_syringe", HealthSyringe::new);
    public static final RegistryObject<Item> ZOMBIE_SYRINGE = ITEMS.register("zombie_syringe", ZombieSyringe::new);

    //Laser Focus
    public static final RegistryObject<Item> WHITE_FOCUS = ITEMS.register("white_focus", () ->
            new FocusItem(ModTags.WHITE_FOCUS, DyeColor.WHITE));
    public static final RegistryObject<Item> YELLOW_FOCUS = ITEMS.register("yellow_focus", () ->
            new FocusItem(ModTags.YELLOW_FOCUS, DyeColor.YELLOW));
    public static final RegistryObject<Item> BLACK_FOCUS = ITEMS.register("black_focus", () ->
            new FocusItem(ModTags.BLACK_FOCUS, DyeColor.BLACK));
    public static final RegistryObject<Item> BLUE_FOCUS = ITEMS.register("blue_focus", () ->
            new FocusItem(ModTags.BLUE_FOCUS, DyeColor.BLUE));
    public static final RegistryObject<Item> BROWN_FOCUS = ITEMS.register("brown_focus", () ->
            new FocusItem(ModTags.BROWN_FOCUS, DyeColor.BROWN));
    public static final RegistryObject<Item> CYAN_FOCUS = ITEMS.register("cyan_focus", () ->
            new FocusItem(ModTags.CYAN_FOCUS, DyeColor.CYAN));
    public static final RegistryObject<Item> GRAY_FOCUS = ITEMS.register("gray_focus", () ->
            new FocusItem(ModTags.GRAY_FOCUS, DyeColor.GRAY));
    public static final RegistryObject<Item> GREEN_FOCUS = ITEMS.register("green_focus", () ->
            new FocusItem(ModTags.GREEN_FOCUS, DyeColor.GREEN));
    public static final RegistryObject<Item> LIGHT_BLUE_FOCUS = ITEMS.register("light_blue_focus", () ->
            new FocusItem(ModTags.LIGHT_BLUE_FOCUS, DyeColor.LIGHT_BLUE));
    public static final RegistryObject<Item> LIGHT_GRAY_FOCUS = ITEMS.register("light_gray_focus", () ->
            new FocusItem(ModTags.LIGHT_GRAY_FOCUS, DyeColor.LIGHT_GRAY));
    public static final RegistryObject<Item> LIME_FOCUS = ITEMS.register("lime_focus", () ->
            new FocusItem(ModTags.LIME_FOCUS, DyeColor.LIME));
    public static final RegistryObject<Item> MAGENTA_FOCUS = ITEMS.register("magenta_focus", () ->
            new FocusItem(ModTags.MAGENTA_FOCUS, DyeColor.MAGENTA));
    public static final RegistryObject<Item> ORANGE_FOCUS = ITEMS.register("orange_focus", () ->
            new FocusItem(ModTags.ORANGE_FOCUS, DyeColor.ORANGE));
    public static final RegistryObject<Item> PINK_FOCUS = ITEMS.register("pink_focus", () ->
            new FocusItem(ModTags.PINK_FOCUS, DyeColor.PINK));
    public static final RegistryObject<Item> PURPLE_FOCUS = ITEMS.register("purple_focus", () ->
            new FocusItem(ModTags.PURPLE_FOCUS, DyeColor.PURPLE));
    public static final RegistryObject<Item> RED_FOCUS = ITEMS.register("red_focus", () ->
            new FocusItem(ModTags.RED_FOCUS, DyeColor.RED));
}
