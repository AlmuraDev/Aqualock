package com.almuramc.aqualock.bukkit.util;

import com.almuramc.aqualock.bukkit.AqualockPlugin;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Player;

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
}
