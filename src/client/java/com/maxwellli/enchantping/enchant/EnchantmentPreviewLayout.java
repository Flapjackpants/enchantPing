package com.maxwellli.enchantping.enchant;

import com.maxwellli.enchantping.mixin.AbstractContainerScreenInvoker;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.world.inventory.EnchantmentMenu;

public final class EnchantmentPreviewLayout {
	public static final int BUTTON_X_OFFSET = 60;
	public static final int BUTTON_Y_OFFSET = 14;
	public static final int BUTTON_ROW_HEIGHT = 19;
	public static final int BUTTON_WIDTH = 108;
	public static final int BUTTON_HEIGHT = 17;
	public static final int BUTTON_COUNT = 3;

	private EnchantmentPreviewLayout() {
	}

	public static int getHoveredSlot(EnchantmentScreen screen, double mouseX, double mouseY) {
		EnchantmentMenu menu = screen.getMenu();
		AbstractContainerScreenInvoker accessor = (AbstractContainerScreenInvoker) screen;

		for (int slot = 0; slot < BUTTON_COUNT; slot++) {
			if (!accessor.enchantping$isHovering(
					BUTTON_X_OFFSET,
					BUTTON_Y_OFFSET + slot * BUTTON_ROW_HEIGHT,
					BUTTON_WIDTH,
					BUTTON_HEIGHT,
					mouseX,
					mouseY
			)) {
				continue;
			}

			if (menu.costs[slot] > 0 && menu.levelClue[slot] >= 0) {
				return slot;
			}
		}

		return -1;
	}
}
