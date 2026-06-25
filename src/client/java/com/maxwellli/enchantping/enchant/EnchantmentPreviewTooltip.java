package com.maxwellli.enchantping.enchant;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.ArrayList;
import java.util.List;

public final class EnchantmentPreviewTooltip {
	private EnchantmentPreviewTooltip() {
	}

	public static List<Component> build(List<EnchantmentInstance> preview, List<Component> vanillaTooltip) {
		if (preview.isEmpty()) {
			return vanillaTooltip;
		}

		List<Component> tooltip = new ArrayList<>();

		for (EnchantmentInstance instance : preview) {
			tooltip.add(Enchantment.getFullname(instance.enchantment(), instance.level())
					.copy()
					.withStyle(ChatFormatting.WHITE));
		}

		if (vanillaTooltip.size() > 1) {
			tooltip.addAll(vanillaTooltip.subList(1, vanillaTooltip.size()));
		}

		return tooltip;
	}
}
