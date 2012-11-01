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
package com.almuramc.aqualock.bukkit.command;

import java.util.logging.Level;

import com.almuramc.aqualock.bukkit.AqualockPlugin;
import com.almuramc.aqualock.bukkit.util.BlockUtil;
import com.almuramc.aqualock.bukkit.util.LockUtil;
import com.almuramc.aqualock.bukkit.util.PermissionUtil;
import com.almuramc.bolt.lock.Lock;
import com.almuramc.bolt.registry.Registry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AqualockCommands implements CommandExecutor {
	private final AqualockPlugin plugin;

	public AqualockCommands(AqualockPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (!(commandSender instanceof Player)) {
			Bukkit.getLogger().log(Level.SEVERE, plugin.getPrefix() + "Cannot execute Aqualock commands as the console!");
			return true;
		}
		if (!command.getName().equalsIgnoreCase("aqualock")) {
			return false;
		}
		if (strings.length < 1) {
			return false;
		}
		Player player = (Player) commandSender;
		if (strings[0].equalsIgnoreCase("check")) {
			Block target = BlockUtil.getTarget(player, null, 4);
			if (target == null) {
				player.sendMessage(plugin.getPrefix() + "No valid block found in a four block line of sight.");
			}
			check(player, target.getLocation());
			return true;
		} else if (strings[0].equalsIgnoreCase("clear")) {
			if (!PermissionUtil.has(player, "aqualock.admin")) {
				player.sendMessage(plugin.getPrefix() + "You do not have permission to clear a person's locks!");
				return true;
			}
			if (strings.length < 2) {
				player.sendMessage(plugin.getPrefix() + "Need to specify a player's name as a parameter.");
				return true;
			}
			final Player playerToClear = Bukkit.getPlayerExact(strings[1]);
			if (playerToClear == null) {
				player.sendMessage(plugin.getPrefix() + "Need to specify a valid player's name as a parameter.");
				return true;
			}
			int count = 0;
			//EXPENSIVE, BLAME DOCKTER
			for (Lock lock : plugin.getRegistry().getAll()) {
				if (lock.getOwner().equals(playerToClear.getName())) {
					plugin.getRegistry().removeLock(lock);
					plugin.getBackend().removeLock(lock);
					count++;
				}
			}
			if (count == 0) {
				player.sendMessage(plugin.getPrefix() + playerToClear.getName() + " had no locks!");
			} else {
				player.sendMessage(plugin.getPrefix() + "Cleared " + count + " lock(s) from " + playerToClear.getName());
			}
			return true;
		}
		return false;
	}

	private void check(Player player, Location loc) {
		if (player == null || loc == null) {
			return;
		}
		Registry reg = AqualockPlugin.getRegistry();
		if (reg.contains(loc.getWorld().getUID(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())) {
			player.sendMessage(AqualockPlugin.getPrefix() + "Found lock: " + reg.getLock(loc.getWorld().getUID(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()).toString());
			return;
		}
		player.sendMessage(AqualockPlugin.getPrefix() + "Location: " + loc.toString() + " has no lock.");
	}
}
