package com.maxwellli.enchantping.sound;

import com.maxwellli.enchantping.config.NotificationSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;

public final class SoundPlayer {
	private SoundPlayer() {
	}

	public static void play(NotificationSound sound, float volume) {
		Minecraft client = Minecraft.getInstance();
		if (client.getSoundManager() == null) {
			return;
		}

		client.getSoundManager().play(SimpleSoundInstance.forUI(sound.getSoundEvent().value(), 1.0F, volume));
	}
}
