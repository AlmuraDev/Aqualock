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

import com.almuramc.aqualock.bukkit.AqualockPlugin;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class PermissionUtil {
	private static final Permission permission;

	static {
		permission = AqualockPlugin.getPermissions();
	}

	public static boolean has(Player player, World world, String perm) {
		return permission.has(world, player.getName(), perm);
	}

	public static boolean has(Player player, String perm) {
		return has(player, player.getWorld(), perm);
	}

	public static boolean canLock(Player player) {
		return has(player, player.getWorld(), "aqualock.lock") || has(player, player.getWorld(), "aqualock.admin");
	}

	public static boolean canUnlock(Player player) {
		return has(player, player.getWorld(), "aqualock.unlock") || has(player, player.getWorld(), "aqualock.admin");
	}

	public static boolean canUpdate(Player player) {
		return has(player, player.getWorld(), "aqualock.update") || has(player, player.getWorld(), "aqualock.admin");
	}

	public static boolean canUse(Player player) {
		return has(player, player.getWorld(), "aqualock.use") || has(player, player.getWorld(), "aqualock.admin");
	}
}
