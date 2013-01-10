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
package com.almuramc.aqualock.bukkit.util;

import com.almuramc.aqualock.bukkit.AqualockPlugin;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class EconomyUtil {
	private static final Economy economy;

	static {
		economy = AqualockPlugin.getEconomies();
	}

	public static boolean hasAccount(OfflinePlayer player) {
		return economy.hasAccount(player.getName());
	}

	public static boolean hasEnough(OfflinePlayer player, double amount) {
		return economy.has(player.getName(), amount);
	}

	public static void apply(OfflinePlayer player, double amount) {
		economy.withdrawPlayer(player.getName(), amount);
	}

	public static double getCostForLock(Player player, Material material) {
		return shouldChargeForLock(player) ? AqualockPlugin.getConfiguration().getCosts().getLockCost(material) : 0.0;
	}

	public static double getCostForUnlock(Player player, Material material) {
		return shouldChargeForUnlock(player) ? AqualockPlugin.getConfiguration().getCosts().getUnlockCost(material) : 0.0;
	}

	public static double getCostForUse(Player player, Material material) {
		return shouldChargeForUse(player) ? AqualockPlugin.getConfiguration().getCosts().getUseCost(material) : 0.0;
	}

	public static double getCostForUpdate(Player player, Material material) {
		return shouldChargeForUpdate(player) ? AqualockPlugin.getConfiguration().getCosts().getUpdateCost(material) : 0.0;
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
