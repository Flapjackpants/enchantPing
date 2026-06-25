package com.maxwellli.enchantping;

import com.maxwellli.enchantping.config.EnchantPingConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class EnchantPingClient implements ClientModInitializer {
	private final XpLevelTracker xpLevelTracker = new XpLevelTracker();

	@Override
	public void onInitializeClient() {
		AutoConfig.register(EnchantPingConfig.class, GsonConfigSerializer::new);
		ClientTickEvents.END_CLIENT_TICK.register(xpLevelTracker::onClientTick);
	}
}
