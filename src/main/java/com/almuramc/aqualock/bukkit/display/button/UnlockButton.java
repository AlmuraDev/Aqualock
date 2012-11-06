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
package com.almuramc.aqualock.bukkit.display.button;

import com.almuramc.aqualock.bukkit.AqualockPlugin;
import com.almuramc.aqualock.bukkit.display.CachedGeoPopup;

import net.minecraft.server.Block;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemInWorldManager;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class UnlockButton extends GenericButton {
	private final AqualockPlugin plugin;

	public UnlockButton(AqualockPlugin plugin) {
		super("Unlock");
		this.plugin = plugin;
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		final Location location = ((CachedGeoPopup) event.getScreen()).getLocation();
		final Material material = location.getBlock().getType();
		final Player player = event.getPlayer();
		final ItemInWorldManager hack = new ItemInWorldManager(((CraftWorld) location.getWorld()).getHandle());
		hack.player = ((CraftPlayer) player).getHandle();
		hack.dig(location.getBlockX(), location.getBlockY(), location.getBlockZ(), 0);
	}
}
