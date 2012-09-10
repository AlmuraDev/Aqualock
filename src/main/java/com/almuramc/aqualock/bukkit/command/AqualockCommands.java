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
package com.almuramc.aqualock.bukkit.command;

import java.util.logging.Level;

import com.almuramc.aqualock.bukkit.AqualockPlugin;
import com.almuramc.bolt.lock.Lock;
import com.almuramc.bolt.lock.type.BasicLock;

import org.bukkit.Bukkit;
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
		if (strings[0].equalsIgnoreCase("lock")) {
			onLock(player);
		} else if (strings[0].equalsIgnoreCase("unlock")) {
			onUnlock(player);
		}
		return false;
	}

	public boolean onLock(Player player) {
		Block lookingAt = player.getEyeLocation().getBlock();
		boolean contains = false;
		if (plugin.getRegistry().contains(lookingAt.getWorld().getUID(), lookingAt.getX() + 1, lookingAt.getY(), lookingAt.getZ())) {
			if (!plugin.getRegistry().getLock(lookingAt.getWorld().getUID(), lookingAt.getX() + 1, lookingAt.getY(), lookingAt.getZ()).getOwner().equals(player.getName())) {
				player.kickPlayer("Aqualock says its not nice to lock other's locks!");
				return true;
			}

			contains = true;
		}
		BasicLock lock = new BasicLock(player.getName(), null, player.getWorld().getUID(), lookingAt.getX() + 1, lookingAt.getY(), lookingAt.getZ());
		plugin.getRegistry().addLock(lock);
		plugin.getBackend().addLock(lock);
		if (contains) {
			player.sendMessage(plugin.getPrefix() + "Updated lock at x: " + lock.getX() + ", y: " + lock.getY() + ", z: " + lock.getZ());
		} else {
			player.sendMessage(plugin.getPrefix() + "Locked voxel at x: " + lock.getX() + ", y: " + lock.getY() + ", z: " + lock.getZ());
		}
		return true;
	}

	public boolean onUnlock(Player player) {
		Block lookingAt = player.getEyeLocation().getBlock();
		if (plugin.getRegistry().contains(lookingAt.getWorld().getUID(), lookingAt.getX() + 1, lookingAt.getY(), lookingAt.getZ())) {
			Lock lock = plugin.getRegistry().getLock(lookingAt.getWorld().getUID(), lookingAt.getX() + 1, lookingAt.getY(), lookingAt.getZ());
			if (lock.getOwner().equals(player.getName())) {
				plugin.getRegistry().removeLock(lock);
				plugin.getBackend().removeLock(lock);
				player.sendMessage(plugin.getPrefix() + "Removed lock at x: " + lock.getX() + ", y: " + lock.getY() + ", z: " + lock.getZ());
			} else {
				player.kickPlayer("Aqualock says its not nice to unlock other's locks!");
			}
		} else {
			player.sendMessage(plugin.getPrefix() + "Did not find a lock at this location!");
		}
		return true;
	}
}
