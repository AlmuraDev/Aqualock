/*
 * This file is part of Aqualock.
 *
 * Copyright (c) 2012, AlmuraDev <http://www.almuramc.com/>
 * Aqualock is licensed under the Almura Development License.
 *
 * Aqualock is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * As an exception, all classes which do not reference GPL licensed code
 * are hereby licensed under the GNU Lesser Public License, as described
 * in Almura Development License.
 *
 * Aqualock is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License,
 * the GNU Lesser Public License (for classes that fulfill the exception)
 * and the Almura Development License along with this program. If not, see
 * <http://www.gnu.org/licenses/> for the GNU General Public License and
 * the GNU Lesser Public License.
 */
package com.almuramc.aqualock.bukkit.util;

import java.util.HashMap;
import java.util.UUID;

import com.almuramc.aqualock.bukkit.AqualockPlugin;
import com.almuramc.aqualock.bukkit.display.AquaPanel;

import org.getspout.spoutapi.player.SpoutPlayer;

import org.bukkit.entity.Player;

public class GuiUtil {
	private static final HashMap<UUID, AquaPanel> store = new HashMap<UUID, AquaPanel>();
	private static final AqualockPlugin plugin;

	static {
		AqualockPlugin temp = null;
		try {
			temp = AqualockPlugin.class.newInstance();
		} catch (Exception ignore) {
		}
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

	public static void open(Player player) {
		if (player == null) {
			throw new IllegalArgumentException("Player cannot be null!");
		}
		if (!(player instanceof SpoutPlayer)) {
			throw new IllegalStateException("Player isn't an instance of SpoutPlayer, SpoutPlugin missing or encountering an issue!");
		}
		((SpoutPlayer) player).getMainScreen().attachPopupScreen(fetch((SpoutPlayer) player));
	}

	public static void close(Player player) {
		if (player == null) {
			throw new IllegalArgumentException("Player cannot be null!");
		}
		if (!(player instanceof SpoutPlayer)) {
			throw new IllegalStateException("Player isn't an instance of SpoutPlayer, SpoutPlugin missing or encountering an issue!");
		}
		SpoutPlayer spoutPlayer = (SpoutPlayer) player;
		if (spoutPlayer.getMainScreen().getActivePopup() instanceof AquaPanel) {
			store(spoutPlayer, (AquaPanel) spoutPlayer.getMainScreen().getActivePopup());
			spoutPlayer.getMainScreen().getActivePopup().close();
		}
	}
}
