package com.maxwellli.enchantping.mixin;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.inventory.EnchantmentMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(EnchantmentMenu.class)
public interface EnchantmentMenuInvoker {
	@Invoker("getEnchantmentList")
	List<EnchantmentInstance> enchantping$getEnchantmentList(
			RegistryAccess registryAccess,
			ItemStack itemStack,
			int slotIndex,
			int cost
	);
}
