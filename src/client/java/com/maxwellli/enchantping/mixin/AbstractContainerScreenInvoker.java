package com.maxwellli.enchantping.mixin;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractContainerScreen.class)
public interface AbstractContainerScreenInvoker {
	@Invoker("isHovering")
	boolean enchantping$isHovering(int x, int y, int width, int height, double mouseX, double mouseY);
}
