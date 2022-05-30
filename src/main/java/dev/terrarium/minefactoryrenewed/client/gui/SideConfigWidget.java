package dev.terrarium.minefactoryrenewed.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.terrarium.minefactoryrenewed.MinefactoryRenewed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SideConfigWidget extends GuiComponent implements Widget, GuiEventListener, NarratableEntry {

    private static final ResourceLocation COMPONENTS = new ResourceLocation(MinefactoryRenewed.MODID, "textures/gui/machine_components.png");
    private static final int MAX_WIDTH = 93;
    private static final int MAX_HEIGHT = 82;
    private static final int MIN_WIDTH = 23;
    private static final int MIN_HEIGHT = 24;

    private int backgroundColor = 0xFFFFFF;
    private int textColor = 0x3F3F3F;

    private final ResourceLocation icon;
    private final Component title;
    private final int x;
    private final int y;

    private int width = MIN_WIDTH;
    private int height = MIN_HEIGHT;

    private boolean open = false;
    private boolean scaling = false;

    private final List<ConfigComponent> components = new ArrayList<>();

    public SideConfigWidget(int x, int y, ResourceLocation icon, Component title) {
        this.x = x;
        this.y = y;
        this.icon = icon;
        this.title = title;
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        boolean hovered = isHovered(mouseX, mouseY);
        float red = (backgroundColor >> 16 & 0xFF) / 255.0f;
        float green = (backgroundColor >> 8 & 0xFF) / 255.0f;
        float blue = (backgroundColor & 0xFF) / 255.0f;
        update();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, COMPONENTS);
        RenderSystem.setShaderColor(red, green, blue, 1.0F);

        blit(poseStack, x, y + 4, 0, 107 - height + 4, 4, height - 4);
        blit(poseStack, x + 4, y, 93 - width + 4, 25, width - 4, 4);
        blit(poseStack, x, y, 0, 25, 4, 4);
        blit(poseStack, x + 4, y + 4, 93 - width + 4, 107 - height + 4, width - 4, height - 4);

        if (open && !scaling) {
            for (ConfigComponent component : components) {
                component.render(poseStack, mouseX, mouseY, partialTicks);
            }

            if (title != null) {
                Font font = Minecraft.getInstance().font;
                font.drawShadow(poseStack, title, x + 20, y + 8, textColor);
            }
        }

        if (icon != null) {
            RenderSystem.setShaderTexture(0, icon);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            blit(poseStack, x + 2, y + 4, 0, 0, 16, 16, 16, 16);
            RenderSystem.setShaderTexture(0, COMPONENTS);
            RenderSystem.setShaderColor(red, green, blue, 1.0F);
        }
    }

    public void update() {
        if (scaling) {
            if (open && width < MAX_WIDTH) {
                width = Math.min(width + 4, MAX_WIDTH);
            } else if (!open && width > MIN_WIDTH) {
                width = Math.max(width - 4, MIN_WIDTH);
            }

            if (open && height < MAX_HEIGHT) {
                height = Math.min(height + 4, MAX_HEIGHT);
            } else if (!open && height > MIN_HEIGHT) {
                height = Math.max(height - 4, MIN_HEIGHT);
            }

            if ((open && width == MAX_WIDTH && height == MAX_HEIGHT) ||
                    (!open && width == MIN_WIDTH && height == MIN_HEIGHT)) {
                scaling = false;
            }
        }
    }

    public void onPress() {
        this.open = !open;
        scaling = true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            boolean flag = this.isHovered((int) mouseX, (int) mouseY);
            if (flag) {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                this.onPress();
                return true;
            }
        }

        if (open && !scaling) {
            for (ConfigComponent component : components) {
                if (component.mouseClicked(mouseX, mouseY, button)) return true;
            }
        }

        return false;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public <T extends ConfigComponent> T addComponent(T component) {
        this.components.add(component);
        return component;
    }

    //For clicking this button, we only care about the min width/height
    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + MIN_WIDTH && mouseY < y + MIN_HEIGHT;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isOpen() {
        return open;
    }

    @Override
    public @NotNull NarrationPriority narrationPriority() {
        return NarratableEntry.NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput narrationElementOutput) {
    }
}
