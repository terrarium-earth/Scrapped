package earth.terrarium.minefactoryrenewed.client.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class DeferredRevealingWidget extends AbstractWidget {
    private final ResourceLocation texture;
    private final Supplier<Float> percentage;
    private final Supplier<Component> tooltip;
    private final int x;
    private final int y;
    private final int borderWidth;
    private final int width;
    private final int height;
    private final int bgXStart;
    private final int bgYStart;
    private final int fgXStart;
    private final int fgYStart;
    private final int fgWidth;
    private final int fgHeight;

    public DeferredRevealingWidget(ResourceLocation texture, Supplier<Component> tooltip, Supplier<Float> percentage, int x, int y, int fgOffset, int width, int height, int bgXStart, int bgYStart, int fgXStart, int fgYStart, int fgWidth, int fgHeight) {
        super(x, y, width, height, tooltip.get());
        this.texture = texture;
        this.percentage = percentage;
        this.tooltip = tooltip;
        this.x = x;
        this.y = y;
        this.borderWidth = fgOffset;
        this.width = width;
        this.height = height;
        this.bgXStart = bgXStart;
        this.bgYStart = bgYStart;
        this.fgXStart = fgXStart;
        this.fgYStart = fgYStart;
        this.fgWidth = fgWidth;
        this.fgHeight = fgHeight;
    }

    public DeferredRevealingWidget(ResourceLocation texture, Supplier<Component> tooltip, Supplier<Float> percentage, int x, int y, int width, int height, int bgXStart, int fgXStart, int fgWidth, int fgHeight) {
        this(texture, tooltip, percentage, x, y, 3, width, height, bgXStart, 0, fgXStart, 0, fgWidth, fgHeight);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int i, int j, float f) {
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShaderColor(1, 1, 1, 1f);
        Screen.blit(poseStack, x, y, bgXStart, bgYStart, width, height, 256, 256);
        int barHeight = Mth.clamp((int) (fgHeight * percentage.get()), 0, fgHeight);
        Screen.blit(poseStack, x + borderWidth, y + borderWidth + fgHeight - barHeight, fgXStart, fgYStart + fgHeight - barHeight, fgWidth, barHeight, 256, 256);
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput narrationElementOutput) {

    }

    @Override
    public void renderToolTip(@NotNull PoseStack poseStack, int mouseX, int mouseY) {
        if(Minecraft.getInstance().screen != null && mouseX < this.x && mouseX < this.x + width && mouseY < this.y && mouseY < this.y + height) {
            Minecraft.getInstance().screen.renderTooltip(poseStack, tooltip.get(), mouseX, mouseY);
        }
    }
}
