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
}
