package com.almuramc.aqualock.bukkit.util;

import java.util.List;

import com.almuramc.aqualock.bukkit.AqualockPlugin;
import com.almuramc.aqualock.bukkit.lock.BukkitLock;
import com.almuramc.bolt.registry.CommonRegistry;
import com.almuramc.bolt.storage.Storage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LockUtil {
	private static final CommonRegistry registry;
	private static final Storage backend;

	static {
		registry = AqualockPlugin.getRegistry();
		backend = AqualockPlugin.getBackend();
	}

	public static void lock(String owner, List<String> coowners, String passcode, Location location, byte data) {
		checkOwner(owner);
		checkLocation(location);

		Player player = Bukkit.getPlayerExact(owner);
		if (player == null) {
			throw new IllegalArgumentException("Specified a player not found by Bukkit!");
		}
		BukkitLock lock = new BukkitLock(owner, coowners, passcode, location, data);
		if (registry.contains(lock)) {
			return;
		} else if (PermissionUtil.has(player, player.getWorld(), "aqualock.lock")) {
			if (PermissionUtil.has(player, player.getWorld(), "aqualock.lock.cost")) {
				if (!EconomyUtil.hasAccount(player)) {
					//message
					return;
				} else {
					if (EconomyUtil.hasEnough(player, 0)) {
						//message
					} else {
						//message
						return;
					}
				}
			}
			registry.addLock(lock);
			backend.addLock(lock);
		}
	}

	public static void lock(String owner, List<String> coowners, String passcode, Location location) {
		checkLocation(location);
		lock(owner, coowners, passcode, location, location.getBlock().getData());
	}

	public static void lock(String owner, List<String> coowners, Location location) {
		checkLocation(location);
		lock(owner, coowners, "", location, location.getBlock().getData());
	}

	public static void lock(String owner, Location location) {
		checkLocation(location);
		lock(owner, null, "", location, location.getBlock().getData());
	}

	public static void unlock(String owner, List<String> coowners, String passcode, Location location, byte data) {
		checkOwner(owner);
		checkLocation(location);
	}

	public static void unlock(String owner, List<String> coowners, String passcode, Location location) {
		checkLocation(location);
		unlock(owner, coowners, passcode, location, location.getBlock().getData());
	}

	public static void unlock(String owner, List<String> coowners, Location location) {
		checkLocation(location);
		unlock(owner, coowners, "", location, location.getBlock().getData());
	}

	public static void unlock(String owner, Location location) {
		checkLocation(location);
		unlock(owner, null, "", location, location.getBlock().getData());
	}

	private static void checkLocation(Location location) {
		if (location == null) {
			throw new IllegalArgumentException("Location cannot be null!");
		}
	}

	private static void checkOwner(String owner) {
		if (owner == null || owner.isEmpty()) {
			throw new IllegalArgumentException("Owner cannot be null or empty!");
		}
	}
}
