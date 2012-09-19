package com.almuramc.aqualock.bukkit.util;

import java.util.HashMap;
import java.util.UUID;

import com.almuramc.aqualock.bukkit.AqualockPlugin;
import com.almuramc.aqualock.bukkit.display.AquaPanel;

import org.getspout.spoutapi.player.SpoutPlayer;

public class GuiUtil {
	private static final HashMap<UUID, AquaPanel> store = new HashMap<UUID, AquaPanel>();
	private static final AqualockPlugin plugin;

	static {
		AqualockPlugin temp = null;
		try {
			temp = AqualockPlugin.class.newInstance();
		} catch (Exception ignore) {}
		plugin = temp;
	}

	private static AquaPanel fetch(SpoutPlayer player) {
		if (player == null) {
			throw new IllegalArgumentException("Player cannot be null!");
		}
		return store.containsKey(player.getUniqueId()) ? store.get(player.getUniqueId()) : new AquaPanel(plugin, player);
	}

	private static void store(SpoutPlayer player, AquaPanel panel) {
		if (player == null) {
			throw new IllegalArgumentException("Player cannot be null!");
		}
		if (panel == null) {
			store.remove(player.getUniqueId());
		} else {
			store.put(player.getUniqueId(), panel);
		}
	}

	public static void open(SpoutPlayer player) {
		if (player == null) {
			throw new IllegalArgumentException("Player cannot be null!");
		}
		player.getMainScreen().attachPopupScreen(fetch(player));
	}

	public static void close(SpoutPlayer player) {
		if (player == null) {
			throw new IllegalArgumentException("Player cannot be null!");
		}
		if (player.getMainScreen().getActivePopup() instanceof AquaPanel) {
			store(player, (AquaPanel) player.getMainScreen().getActivePopup());
			player.getMainScreen().getActivePopup().close();
		}
	}
}
