package dev.terrarium.minefactoryrenewed.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.events.GuiEventListener;

public abstract class ConfigComponent extends GuiComponent implements GuiEventListener {

    private int x;
    private int y;

    public ConfigComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks);

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
