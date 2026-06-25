package com.maxwellli.enchantping.enchant;

import com.maxwellli.enchantping.mixin.EnchantmentMenuInvoker;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.inventory.EnchantmentMenu;

import java.util.List;

public final class EnchantmentPreviewCalculator {
	private EnchantmentPreviewCalculator() {
	}

	public static List<EnchantmentInstance> preview(EnchantmentMenu menu, int slotIndex, RegistryAccess registryAccess) {
		if (slotIndex < 0 || slotIndex >= menu.costs.length) {
			return List.of();
		}

		int cost = menu.costs[slotIndex];
		if (cost <= 0 || menu.levelClue[slotIndex] < 0) {
			return List.of();
		}

		ItemStack itemStack = menu.getSlot(0).getItem();
		if (itemStack.isEmpty()) {
			return List.of();
		}

		synchronized (menu) {
			List<EnchantmentInstance> enchantments = ((EnchantmentMenuInvoker) menu).enchantping$getEnchantmentList(
					registryAccess,
					itemStack,
					slotIndex,
					cost
			);
			return List.copyOf(enchantments);
		}
	}
}
