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
 * Aqualock is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License. If not,
 * see <http://www.gnu.org/licenses/> for the GNU General Public License.
 */
package com.almuramc.aqualock.bukkit.display;

import com.almuramc.bolt.lock.Lock;

import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.Screen;
import org.getspout.spoutapi.player.SpoutPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * A popup that stores the location a Player goes and opens a popup at with a keybind.
 *
 * Ex. Looking at a block and pressing the L key typically opens an AquaPanel which
 * stores the location this popup opened at that block's location.
 */
public abstract class PopulateLocationPopup extends GenericPopup {
	private Location location;

	public void setLocation(Location location) {
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}

	/**
	 * Populates the panel.
	 */
	public abstract void populate(Lock lock);

	/**
	 * Hack to workaround the remote chance of SpoutPlugin losing track of screens behind popups.
	 * @return
	 */
	@Override
	public Screen getScreen() {
		final Screen screen = super.getScreen();
		if (screen== null) {
			Bukkit.getLogger().warning("Attempting to get a null screen!" +
					"SpoutPlugin's issue.");
		}
		return screen;
	}

	/**
	 * Hack to workaround the remote chance of SpoutPlugin losing track of Players with a screen
	 * @return
	 */
	@Override
	public SpoutPlayer getPlayer() {
		final SpoutPlayer player = super.getPlayer();
		if (player == null) {
			Bukkit.getLogger().warning("Attempting to get the player of a null screen!" +
					"SpoutPlugin's issue.");
		}
		return player;
	}
	/**
	 * Hack to workaround SpoutPlugin removal issues.
	 */
	public void onClose() {
		getScreen().removeWidget(this);
		getScreen().getPlayer().closeActiveWindow();
	}
}
