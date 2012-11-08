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
import com.almuramc.aqualock.bukkit.display.AquaPass;
import com.almuramc.aqualock.bukkit.display.CachedGeoPopup;
import com.almuramc.aqualock.bukkit.lock.BukkitLock;
import com.almuramc.aqualock.bukkit.util.BlockUtil;
import com.almuramc.aqualock.bukkit.util.LockUtil;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.player.SpoutPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Furnace;

public class UnlockButton extends GenericButton {
	private final AqualockPlugin plugin;

	public UnlockButton(AqualockPlugin plugin) {
		super("Unlock");
		this.plugin = plugin;
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		final Location location = ((CachedGeoPopup) event.getScreen()).getLocation();
		final SpoutPlayer player = SpoutManager.getPlayer(event.getPlayer());
		final Block block = location.getBlock();
		final String password = ((AquaPass) event.getScreen()).getPassword();
		final BukkitLock lock = (BukkitLock) plugin.getRegistry().getLock(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ());
		if (!lock.getPasscode().equals(password)) {
			player.sendNotification("Aqualock", "Invalid password!", Material.LAVA_BUCKET);
			player.damage(lock.getDamage());
			return;
		}
		if (!LockUtil.use(player.getName(), password, block.getLocation(), lock.getUseCost())) {
			return;
		}
		((CachedGeoPopup) event.getScreen()).onClose();
		switch (location.getBlock().getType()) {
			case CHEST:
				final Chest chest = (Chest) location.getBlock().getState();
				Bukkit.getScheduler().scheduleSyncDelayedTask(AqualockPlugin.getInstance(), new Runnable() {
					@Override
					public void run() {
						player.openInventory(chest.getInventory());
					}
				}, 10L);
				break;
			case WOODEN_DOOR:
				BlockUtil.onDoorInteract(block, false);
				break;
			case IRON_DOOR_BLOCK:
				BlockUtil.onDoorInteract(block, false);
				break;
			case IRON_DOOR:
				BlockUtil.onDoorInteract(block, false);
				break;
			case FURNACE:
				final Furnace furnace = (Furnace) block.getState();
				Bukkit.getScheduler().scheduleSyncDelayedTask(AqualockPlugin.getInstance(), new Runnable() {
					@Override
					public void run() {
						player.openInventory(furnace.getInventory());
					}
				}, 10L);
				break;
		}
	}
}
