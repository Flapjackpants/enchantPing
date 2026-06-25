package com.maxwellli.enchantping.enchant;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.inventory.EnchantmentMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class EnchantmentPreviewCalculator {
	private EnchantmentPreviewCalculator() {
	}

	public static List<EnchantmentInstance> preview(EnchantmentMenu menu, int slotIndex, RegistryAccess registryAccess) {
		if (slotIndex < 0 || slotIndex >= menu.costs.length) {
			return List.of();
		}

		int cost = menu.costs[slotIndex];
		if (cost <= 0) {
			return List.of();
		}

		ItemStack itemStack = menu.getSlot(0).getItem();
		if (itemStack.isEmpty()) {
			return List.of();
		}

		RandomSource random = RandomSource.create();
		random.setSeed(menu.getEnchantmentSeed() + slotIndex);

		var enchantmentRegistry = registryAccess.lookupOrThrow(Registries.ENCHANTMENT);
		var enchantingTablePool = enchantmentRegistry.getOrThrow(EnchantmentTags.IN_ENCHANTING_TABLE);

		ItemStack enchanted = EnchantmentHelper.enchantItem(
				random,
				itemStack.copy(),
				cost,
				registryAccess,
				Optional.of(enchantingTablePool)
		);

		ItemEnchantments enchantments = enchanted.getEnchantments();
		if (enchantments.isEmpty()) {
			return List.of();
		}

		List<EnchantmentInstance> preview = new ArrayList<>();
		for (var entry : enchantments.entrySet()) {
			Holder<Enchantment> enchantment = entry.getKey();
			int level = entry.getIntValue();
			preview.add(new EnchantmentInstance(enchantment, level));
		}

		return preview;
	}
}
