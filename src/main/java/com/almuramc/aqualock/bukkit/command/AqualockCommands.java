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

import com.almuramc.aqualock.bukkit.AqualockPlugin;
import com.almuramc.bolt.lock.type.BasicLock;

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
			plugin.getLogger().severe("Cannot execute Aqualock commands as the console!");
			return true;
		}
		if (!command.getName().equalsIgnoreCase("aqualock")) {
			return false;
		}
		if (!strings[0].equalsIgnoreCase("lock")) {
			return false;
		}
		Player player = (Player) commandSender;
		Block lookingAt = player.getEyeLocation().getBlock();
		BasicLock lock = new BasicLock("Charlie", null, player.getWorld().getUID(), lookingAt.getX(), lookingAt.getY(), lookingAt.getZ());
		plugin.getLogger().info(lock.toString());
		plugin.getRegistry().addLock(lock);
		plugin.getBackend().addLock(lock);
		return true;
	}
}
