package com.maxwellli.enchantping;

import com.maxwellli.enchantping.config.EnchantPingConfig;
import com.maxwellli.enchantping.config.NotificationSound;
import com.maxwellli.enchantping.sound.SoundPlayer;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public final class XpLevelTracker {
	private int lastLevel = -1;

	public void onClientTick(Minecraft client) {
		LocalPlayer player = client.player;
		if (player == null) {
			lastLevel = -1;
			return;
		}

		int currentLevel = player.experienceLevel;
		if (lastLevel < 0) {
			lastLevel = currentLevel;
			return;
		}

		EnchantPingConfig config = AutoConfig.getConfigHolder(EnchantPingConfig.class).getConfig();
		if (config.enabled && lastLevel < config.targetLevel && currentLevel >= config.targetLevel) {
			SoundPlayer.play(config.sound, config.volume / 100.0F);
		}

		lastLevel = currentLevel;
	}
}
