package com.maxwellli.enchantping.mixin;

import com.maxwellli.enchantping.config.EnchantPingConfig;
import com.maxwellli.enchantping.enchant.EnchantmentPreviewCalculator;
import com.maxwellli.enchantping.enchant.EnchantmentPreviewLayout;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.inventory.EnchantmentMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantmentScreen.class)
public class EnchantmentScreenMixin {
	@Inject(method = "extractRenderState", at = @At("TAIL"))
	private void enchantping$renderPreviewTooltip(
			GuiGraphicsExtractor graphics,
			int mouseX,
			int mouseY,
			float partialTick,
			CallbackInfo ci
	) {
		EnchantPingConfig config = AutoConfig.getConfigHolder(EnchantPingConfig.class).getConfig();
		if (!config.showEnchantmentPreview) {
			return;
		}

		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level == null) {
			return;
		}

		EnchantmentScreen screen = (EnchantmentScreen) (Object) this;
		EnchantmentMenu menu = screen.getMenu();

		int leftPos = (screen.width - EnchantmentPreviewLayout.IMAGE_WIDTH) / 2;
		int topPos = (screen.height - EnchantmentPreviewLayout.IMAGE_HEIGHT) / 2;

		int hoveredButton = EnchantmentPreviewLayout.getHoveredButtonIndex(leftPos, topPos, mouseX, mouseY);
		if (hoveredButton < 0) {
			return;
		}

		ItemStack itemToEnchant = menu.getSlot(0).getItem();
		if (itemToEnchant.isEmpty() || menu.costs[hoveredButton] <= 0) {
			return;
		}

		ItemStack lapisStack = menu.getSlot(1).getItem();
		if (!lapisStack.is(Items.LAPIS_LAZULI) || lapisStack.getCount() < menu.costs[hoveredButton]) {
			return;
		}

		List<EnchantmentInstance> preview = EnchantmentPreviewCalculator.preview(
				menu,
				hoveredButton,
				minecraft.level.registryAccess()
		);

		List<Component> tooltip = buildTooltip(preview, menu.costs[hoveredButton]);
		if (tooltip.isEmpty()) {
			return;
		}

		graphics.setComponentTooltipForNextFrame(minecraft.font, tooltip, mouseX, mouseY);
	}

	private static List<Component> buildTooltip(List<EnchantmentInstance> preview, int cost) {
		List<Component> tooltip = new ArrayList<>();

		if (preview.isEmpty()) {
			tooltip.add(Component.translatable("text.enchantping.preview.empty"));
			return tooltip;
		}

		tooltip.add(Component.translatable("text.enchantping.preview.title"));
		tooltip.add(Component.empty());

		for (EnchantmentInstance instance : preview) {
			tooltip.add(Component.literal("• ")
					.append(Enchantment.getFullname(instance.enchantment(), instance.level())));
		}

		tooltip.add(Component.empty());
		tooltip.add(Component.translatable("text.enchantping.preview.cost", cost));
		tooltip.add(Component.translatable("text.enchantping.preview.lapis", cost));

		return tooltip;
	}
}
