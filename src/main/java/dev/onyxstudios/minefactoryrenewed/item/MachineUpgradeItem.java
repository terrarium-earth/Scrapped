package dev.onyxstudios.minefactoryrenewed.item;

import dev.onyxstudios.minefactoryrenewed.MinefactoryRenewed;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MachineUpgradeItem extends Item {

    private final int radiusIncrease;

    public MachineUpgradeItem(int radiusIncrease) {
        super(new Item.Properties().tab(MinefactoryRenewed.TAB).stacksTo(1));
        this.radiusIncrease = radiusIncrease;
    }

    public int getRadiusIncrease() {
        return radiusIncrease;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltip, isAdvanced);
        Component amount = new TextComponent(String.valueOf(radiusIncrease)).setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_GRAY));
        Component hoverText = new TranslatableComponent("tooltip.machine.upgrade").append(": ")
                .append(amount).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY));

        tooltip.add(hoverText);
    }
}
