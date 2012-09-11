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
import com.almuramc.bolt.lock.Lock;
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

	/**
	 *
	 * @param owner
	 * @param coowners
	 * @param passcode
	 * @param location
	 * @param data
	 */
	public static void lock(String owner, List<String> coowners, String passcode, Location location, byte data) {
		checkLocation(location);
		Player player = checkNameAndGetPlayer(owner);

		BukkitLock lock = new BukkitLock(owner, coowners, passcode, location, data);
		//Make sure we aren't relocking blocks
		if (registry.contains(lock)) {
			//TODO call change
			return;
		}
			//If the server has an economy system, use it
		if (AqualockPlugin.getEconomies() != null) {
			//Check if they need to be charged for this lock
			if (EconomyUtil.shouldChargeForLock(player)) {
				//No account? Let the player know some message and return
				if (!EconomyUtil.hasAccount(player)) {
					//TODO message
					return;
				} else {
					double cost = EconomyUtil.getCostForLock(player);
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

	/**
	 *
	 * @param playerName
	 * @param passcode
	 * @param location
	 */
	public static void unlock(String playerName, String passcode, Location location) {
		checkLocation(location);
		Player player = checkNameAndGetPlayer(playerName);

		Lock lock = registry.getLock(location.getWorld().getUID(), location.getBlockX(), location.getBlockY(), location.getBlockZ());

		if (lock == null) {
			//TODO Tell the user there was no lock at this location...
			return;
		} else {
			String owner = lock.getOwner();
			//The owner of the lock at the location doesn't match the player's name, check co-owners next
			if (!owner.equals(playerName)) {
				//They aren't a co-owner either, so return and print a message
				if (!lock.getCoOwners().contains(playerName)) {
					//TODO message
					//TODO Hurt them?
					return;
				}
			}
			//At this point they are either an owner or co-owner but that is irrelevant.
			//TODO Owner-level only privileges?
			//Now lets handle Bukkit-like lock characteristics to determine if we can unlock
			if (lock instanceof BukkitLock) {
				//Is a Bukkit lock but the passcode passed in fails so fail to unlock (even if it was the owner...)
				if (!((BukkitLock) lock).getPasscode().equals(passcode)) {
					//TODO message
					return;
				}
			}

			//If the server has an economy system, use it
			if (AqualockPlugin.getEconomies() != null) {
				//Check if they need to be charged for this lock
				if (EconomyUtil.shouldChargeForUnlock(player)) {
					//No account? Let the player know some message and return
					if (!EconomyUtil.hasAccount(player)) {
						//TODO message
						return;
					} else {
						double cost = EconomyUtil.getCostForUnlock(player);
						//Find out if they have the money.
						if (EconomyUtil.hasEnough(player, cost)) {
							//TODO If the cost was zero then say something like "Unlocking was free!"
							if (cost == 0) {
								//player.sendMessage
								//TODO If cost was less than zero then say something like "Unlocking gave you monies!"
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
		registry.removeLock(lock);
		backend.removeLock(lock);
	}

	/**
	 *
	 * @param playerName
	 * @param owner
	 * @param coowners
	 * @param passcode
	 * @param location
	 * @param data
	 */
	public static void update(String playerName, String owner, List<String> coowners, String passcode, Location location, byte data) {
		//First check if the registry has a lock at this location, if not then lock.
		if (!registry.contains(location.getWorld().getUID(), location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
			lock(owner, coowners, passcode, location, data);
			return;
		}

		Lock lock = registry.getLock(location.getWorld().getUID(), location.getBlockX(), location.getBlockY(), location.getBlockZ());

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////Overriden Methods///////////////////////////////////////////////
	//////////////////////////////////////Don't touch these!//////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////

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

	public static void unlock(String playerName, Location location) {
		unlock(playerName, "", location);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////Private helpers//////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////

	private static void checkLocation(Location location) {
		if (location == null) {
			throw new IllegalArgumentException("Location cannot be null!");
		}
	}

	private static Player checkNameAndGetPlayer(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty!");
		}
		Player player = Bukkit.getPlayerExact(name);
		if (player == null) {
			throw new IllegalArgumentException("No player found matching name: " + name + " found on this server!");
		}
		return player;
	}
}
