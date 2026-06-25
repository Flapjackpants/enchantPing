package com.maxwellli.enchantping.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "enchantping")
public class EnchantPingConfig implements ConfigData {
	@ConfigEntry.Gui.Tooltip(count = 2)
	public boolean enabled = true;

	@ConfigEntry.Gui.Tooltip(count = 2)
	@ConfigEntry.BoundedDiscrete(min = 1, max = 100)
	public int targetLevel = 30;

	@ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
	public NotificationSound sound = NotificationSound.ENCHANTMENT_TABLE;

	@ConfigEntry.BoundedDiscrete(min = 0, max = 100)
	public int volume = 100;
}
