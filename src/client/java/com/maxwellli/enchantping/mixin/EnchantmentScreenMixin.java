package com.maxwellli.enchantping.mixin;

import com.maxwellli.enchantping.config.EnchantPingConfig;
import com.maxwellli.enchantping.enchant.EnchantmentPreviewCalculator;
import com.maxwellli.enchantping.enchant.EnchantmentPreviewLayout;
import com.maxwellli.enchantping.enchant.EnchantmentPreviewTooltip;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.inventory.EnchantmentMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(EnchantmentScreen.class)
public class EnchantmentScreenMixin {
	@Redirect(
			method = "extractRenderState",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;setComponentTooltipForNextFrame(Lnet/minecraft/client/gui/Font;Ljava/util/List;II)V"
			)
	)
	private void enchantping$replaceEnchantmentTooltip(
			GuiGraphicsExtractor graphics,
			Font font,
			List<Component> vanillaTooltip,
			int mouseX,
			int mouseY
	) {
		EnchantPingConfig config = AutoConfig.getConfigHolder(EnchantPingConfig.class).getConfig();
		if (!config.showEnchantmentPreview) {
			graphics.setComponentTooltipForNextFrame(font, vanillaTooltip, mouseX, mouseY);
			return;
		}

		Minecraft minecraft = Minecraft.getInstance();
		EnchantmentScreen screen = (EnchantmentScreen) (Object) this;

		if (minecraft.level == null) {
			graphics.setComponentTooltipForNextFrame(font, vanillaTooltip, mouseX, mouseY);
			return;
		}

		int slotIndex = EnchantmentPreviewLayout.getHoveredSlot(screen, mouseX, mouseY);
		if (slotIndex < 0) {
			graphics.setComponentTooltipForNextFrame(font, vanillaTooltip, mouseX, mouseY);
			return;
		}

		EnchantmentMenu menu = screen.getMenu();
		List<EnchantmentInstance> preview = EnchantmentPreviewCalculator.preview(
				menu,
				slotIndex,
				minecraft.level.registryAccess()
		);

		List<Component> tooltip = EnchantmentPreviewTooltip.build(preview, vanillaTooltip);
		graphics.setComponentTooltipForNextFrame(font, tooltip, mouseX, mouseY);
	}
}
