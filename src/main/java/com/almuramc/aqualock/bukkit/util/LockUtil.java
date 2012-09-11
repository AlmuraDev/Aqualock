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

import java.util.List;

import com.almuramc.aqualock.bukkit.AqualockPlugin;
import com.almuramc.aqualock.bukkit.lock.BukkitLock;
import com.almuramc.bolt.registry.CommonRegistry;
import com.almuramc.bolt.storage.Storage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * The core utility class of Aqualock. Handles common functions between commands/GUI (to spare a LOT of duplicate code) such as lock, unlock, use, and change
 */
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
		//Make sure the owner is present on this server (sanity check).
		if (player == null) {
			throw new IllegalArgumentException("The owner's name specified to lock the voxel was not found on this server!");
		}
		BukkitLock lock = new BukkitLock(owner, coowners, passcode, location, data);
		//Make sure we aren't relocking blocks
		if (registry.contains(lock)) {
			//TODO call change
			return;
		}
		//lock is not in the registry, so we are creating a new lock. Check if they have lock perms
		if (PermissionUtil.has(player, player.getWorld(), "aqualock.lock")) {
			//If the server has an economy system, use it
			if (AqualockPlugin.getEconomies() != null) {
				//Check if they need to be charged for this lock
				if (PermissionUtil.has(player, player.getWorld(), "aqualock.lock.cost")) {
					//No account? Let the player know some message and return
					if (!EconomyUtil.hasAccount(player)) {
						//TODO message
						return;
					} else {
						double cost = EconomyUtil.getCostFor(player);
						//Find out if they have the money.
						if (EconomyUtil.hasEnough(player, cost)) {
							//TODO If the cost was zero then say something like "Locking was free!"
							if (cost == 0) {
								//player.sendMessage
								//TODO If cost was less than zero then say something like "Locking gave you monies!"
							} else if (cost < 0) {
								//player.sendMessage
								//TODO Tell the user how much they were charged
							} else {
								//player.sendMessage
							}
							//TODO message
							//Don't have enough? Tell them that and return
						} else {
							//TODO message
							return;
						}
					}
				}
			}
		}
		//Is it a double door? Lets lock both doors
		if (BlockUtil.isDoubleDoor(location.getBlock())) {
			//Get the double door blocks
			List<Block> doors = BlockUtil.getDoubleDoor(location.getBlock());
			//Loop through the blocks (will be 2 iterations)
			for (Block b : doors) {
				//If the lock created in this method's location is not the location of this iteration then its the second door, lock it.
				if (!b.getLocation().equals(location)) {
					BukkitLock other = new BukkitLock(lock.getOwner(), lock.getCoOwners(), lock.getPasscode(), b.getLocation(), b.getData());
					registry.addLock(other);
					backend.addLock(other);
				}
			}
		}
		//After all that is said and done, add the lock made to the registry and backend.
		registry.addLock(lock);
		backend.addLock(lock);
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
