package dev.terrarium.minefactoryrenewed.compat.jei;

import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import dev.terrarium.minefactoryrenewed.client.gui.power.EnergyCellScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class MFRJeiPlugin implements IModPlugin {

    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
        IModPlugin.super.registerGuiHandlers(registration);
        registration.addGuiContainerHandler(EnergyCellScreen.class, new MFRContainerHandler());
    }

    @NotNull
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MinefactoryRenewed.MODID, "jei_plugin");
    }
}
