package earth.terrarium.minefactoryrenewed.client.utils;

import earth.terrarium.minefactoryrenewed.client.widgets.DeferredRevealingWidget;
import earth.terrarium.minefactoryrenewed.common.utils.ConcatUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class ScreenUtils {
    public static final ResourceLocation BAR_TEXTURE = new ResourceLocation("minefactoryrenewed", "textures/gui/widget/bars.png");
    private static final String ENERGY_TRANSLATION_KEY = ConcatUtils.modidConcat("gui", "energy_bar");
    private static final String PROGRESS_TRANSLATION_KEY = ConcatUtils.modidConcat("gui", "progress_bar");

    public static DeferredRevealingWidget createEnergyBar(Supplier<Integer> storedEnergy, int maxEnergy, int x, int y) {
        return new DeferredRevealingWidget(BAR_TEXTURE, () -> Component.translatable(ENERGY_TRANSLATION_KEY, storedEnergy, maxEnergy), () -> ((float) storedEnergy.get() / maxEnergy), x, y, 18, 72, 0, 18, 12, 66);
    }

    public static DeferredRevealingWidget createProgressBar(Supplier<Integer> progress, int maxWork, int x, int y) {
        return new DeferredRevealingWidget(BAR_TEXTURE, () -> Component.translatable(PROGRESS_TRANSLATION_KEY, progress.get(), maxWork), () -> ((float) progress.get() / maxWork), x, y, 12, 72, 30, 42, 6, 66);
    }
}
