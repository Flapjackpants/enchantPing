package com.maxwellli.enchantping.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.BiConsumer;

@Mixin(EnchantmentMenu.class)
public class EnchantmentMenuMixin {
	@Redirect(
			method = "slotsChanged",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/inventory/ContainerLevelAccess;execute(Ljava/util/function/BiConsumer;)V"
			)
	)
	private void enchantping$useServerSyncedEnchantments(
			ContainerLevelAccess access,
			BiConsumer<Level, BlockPos> callback
	) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft != null && minecraft.level != null && minecraft.level.isClientSide()) {
			return;
		}

		access.execute(callback);
	}
}
