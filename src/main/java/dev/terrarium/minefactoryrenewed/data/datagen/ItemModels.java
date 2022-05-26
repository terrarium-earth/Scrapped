package dev.terrarium.minefactoryrenewed.data.datagen;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ItemModels extends ItemModelProvider {

    private static final ResourceLocation ITEM_GENERATED = new ResourceLocation("item/generated");

    public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MinefactoryRenewed.MODID, existingFileHelper);
    }

    @Override
    public void registerModels() {
        for (RegistryObject<Item> item : ModItems.ITEMS.getEntries()) {
            ResourceLocation registryName = item.get().getRegistryName();
            if (registryName == null) continue;

            //Upgrade item models
            if (registryName.getPath().endsWith("_upgrade")) {
                withExistingParent(registryName.toString(), ITEM_GENERATED)
                        .texture("layer0", registryName.getNamespace() + ":item/" + registryName.getPath());
            }

            if (registryName.getPath().endsWith("_focus")) {
                String token = registryName.getPath().replace("_focus", "");

                withExistingParent(registryName.toString(), ITEM_GENERATED)
                        .texture("layer0", registryName.getNamespace() + ":item/" + token + "_laser_focus");
            }
        }
    }
}
