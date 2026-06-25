package com.maxwellli.enchantping.config;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public enum NotificationSound {
	ENCHANTMENT_TABLE(SoundEvents.ENCHANTMENT_TABLE_USE, "text.enchantping.sound.enchantment_table"),
	NOTE_BLOCK_PLING(SoundEvents.NOTE_BLOCK_PLING, "text.enchantping.sound.note_block_pling"),
	EXPERIENCE_ORB(SoundEvents.EXPERIENCE_ORB_PICKUP, "text.enchantping.sound.experience_orb"),
	PLAYER_LEVELUP(SoundEvents.PLAYER_LEVELUP, "text.enchantping.sound.player_levelup"),
	BELL(SoundEvents.BELL_BLOCK, "text.enchantping.sound.bell"),
	UI_TOAST_CHALLENGE(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, "text.enchantping.sound.ui_toast_challenge");

	private final Holder<SoundEvent> soundEvent;
	private final String translationKey;

	NotificationSound(SoundEvent soundEvent, String translationKey) {
		this.soundEvent = Holder.direct(soundEvent);
		this.translationKey = translationKey;
	}

	NotificationSound(Holder<SoundEvent> soundEvent, String translationKey) {
		this.soundEvent = soundEvent;
		this.translationKey = translationKey;
	}

	public Holder<SoundEvent> getSoundEvent() {
		return soundEvent;
	}

	@Override
	public String toString() {
		return Component.translatable(translationKey).getString();
	}
}
