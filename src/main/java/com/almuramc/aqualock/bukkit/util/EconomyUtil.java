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

import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class EconomyUtil {
	private static final Economy economy;

	static {
		economy = AqualockPlugin.getEconomies();
	}

	public static boolean hasAccount(Player player) {
		if (economy.hasAccount(player.getName())) {
			return true;
		}
		return false;
	}

	public static boolean hasEnough(Player player, double amount) {
		if (economy.has(player.getName(), amount)) {
			return true;
		}
		return false;
	}

	public static void apply(Player player, double amount) {
		economy.withdrawPlayer(player.getName(), amount);
	}

	public static double getCostForLock(Player player) {
		for (PermissionAttachmentInfo perm : player.getEffectivePermissions()) {
			if (perm.getPermission().contains("aqualock.lock.cost.")) {
				try {
					return Double.parseDouble(perm.getPermission().split("aqualock.lock.cost.")[1]);
				} catch (Exception ignore) {
				}
			}
		}
		return 0;
	}

	public static double getCostForUnlock(Player player) {
		for (PermissionAttachmentInfo perm : player.getEffectivePermissions()) {
			if (perm.getPermission().contains("aqualock.unlock.cost.")) {
				try {
					return Double.parseDouble(perm.getPermission().split("aqualock.unlock.cost.")[1]);
				} catch (Exception ignore) {
				}
			}
		}
		return 0;
	}

	public static double getCostForUse(Player player) {
		for (PermissionAttachmentInfo perm : player.getEffectivePermissions()) {
			if (perm.getPermission().contains("aqualock.use.cost.")) {
				try {
					return Double.parseDouble(perm.getPermission().split("aqualock.use.cost.")[1]);
				} catch (Exception ignore) {
				}
			}
		}
		return 0;
	}

	public static double getCostForUpdate(Player player) {
		for (PermissionAttachmentInfo perm : player.getEffectivePermissions()) {
			if (perm.getPermission().contains("aqualock.update.cost.")) {
				try {
					return Double.parseDouble(perm.getPermission().split("aqualock.update.cost.")[1]);
				} catch (Exception ignore) {
				}
			}
		}
		return 0;
	}

	public static boolean shouldChargeForLock(Player player) {
		return PermissionUtil.has(player, player.getWorld(), "aqualock.lock.cost") && !PermissionUtil.has(player, player.getWorld(), "aqualock.admin");
	}

	public static boolean shouldChargeForUnlock(Player player) {
		return PermissionUtil.has(player, player.getWorld(), "aqualock.unlock.cost") && !PermissionUtil.has(player, player.getWorld(), "aqualock.admin");
	}

	public static boolean shouldChargeForUse(Player player) {
		return PermissionUtil.has(player, player.getWorld(), "aqualock.use.cost") && !PermissionUtil.has(player, player.getWorld(), "aqualock.admin");
	}

	public static boolean shouldChargeForUpdate(Player player) {
		return PermissionUtil.has(player, player.getWorld(), "aqualock.update.cost") && !PermissionUtil.has(player, player.getWorld(), "aqualock.admin");
	}
}
