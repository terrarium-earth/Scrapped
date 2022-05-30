package dev.terrarium.minefactoryrenewed.compat.jei;

import dev.terrarium.minefactoryrenewed.client.gui.SideConfigWidget;
import dev.terrarium.minefactoryrenewed.client.gui.power.EnergyCellScreen;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rect2i;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MFRContainerHandler implements IGuiContainerHandler<EnergyCellScreen> {

    @NotNull
    @Override
    public List<Rect2i> getGuiExtraAreas(@NotNull EnergyCellScreen containerScreen) {
        List<Rect2i> list = new ArrayList<>();
        SideConfigWidget configButton = containerScreen.getConfigWidget();
        list.add(new Rect2i(configButton.getX(), configButton.getY(), configButton.getWidth(), configButton.getHeight()));
        return list;
    }
}
