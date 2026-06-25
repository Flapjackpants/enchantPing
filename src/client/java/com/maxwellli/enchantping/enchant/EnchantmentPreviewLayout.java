package com.maxwellli.enchantping.enchant;

public final class EnchantmentPreviewLayout {
	public static final int IMAGE_WIDTH = 176;
	public static final int IMAGE_HEIGHT = 166;
	public static final int BUTTON_X_OFFSET = 60;
	public static final int BUTTON_Y_OFFSET = 14;
	public static final int BUTTON_HEIGHT = 19;
	public static final int BUTTON_WIDTH = 108;
	public static final int BUTTON_COUNT = 3;

	private EnchantmentPreviewLayout() {
	}

	public static boolean isHoveringButton(int leftPos, int topPos, int mouseX, int mouseY, int buttonIndex) {
		if (buttonIndex < 0 || buttonIndex >= BUTTON_COUNT) {
			return false;
		}

		int buttonX = leftPos + BUTTON_X_OFFSET;
		int buttonY = topPos + BUTTON_Y_OFFSET + buttonIndex * BUTTON_HEIGHT;

		return mouseX >= buttonX
				&& mouseX < buttonX + BUTTON_WIDTH
				&& mouseY >= buttonY
				&& mouseY < buttonY + BUTTON_HEIGHT;
	}

	public static int getHoveredButtonIndex(int leftPos, int topPos, int mouseX, int mouseY) {
		for (int i = 0; i < BUTTON_COUNT; i++) {
			if (isHoveringButton(leftPos, topPos, mouseX, mouseY, i)) {
				return i;
			}
		}

		return -1;
	}
}
