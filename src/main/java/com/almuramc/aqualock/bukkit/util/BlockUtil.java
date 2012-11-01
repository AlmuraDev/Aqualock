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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.almuramc.aqualock.bukkit.AqualockPlugin;
import com.almuramc.aqualock.bukkit.lock.DoorBukkitLock;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

/**
 * Class with helper functions that deal with blocks
 */
public class BlockUtil {
	/**
	 * Gets the immediate block found within the distance of the player's line of sight
	 * @param player The player looking
	 * @param distance The distance to search
	 * @return The block found or null if no valid blocks found
	 */
	public static Block getTarget(Player player, HashSet<Byte> idsToIgnore, int distance) {
		List<Block> blocks = player.getLineOfSight(idsToIgnore, distance);
		if (blocks == null || blocks.isEmpty()) {
			return null;
		}
		Block toReturn = null;
		for (Block block : blocks) {
			if (block.getType().equals(Material.AIR)) {
				continue;
			}
			toReturn = block;
			break;
		}

		return toReturn;
	}

	/**
	 * Gets a list containing all blocks a part of a double door. The list will have no fewer or no more than 4 elements.
	 * Note: the search for double door blocks is 2D and based on the material of the source block passed in.
	 * <p/>
	 * If the list returned is empty, it isn't a double door.
	 * @return Empty list if not in a double door or the 4 blocks comprising the double door.
	 */
	public static Block getDoubleDoor(Location location) {
		//Passed in block is not a double door
		final Block block = location.getBlock();
		if (!isDoorMaterial(block.getType())) {
			return null;
		}
		Block found;

		// Try a wooden door
		if ((found = findAdjacentBlock(block, Material.WOODEN_DOOR)) != null) {
			return found;
		}

		// Now an iron door
		if ((found = findAdjacentBlock(block, Material.IRON_DOOR_BLOCK)) != null) {
			return found;
		}

		// Nothing at all :-(
		return null;
	}

	public static void changeDoorStates(boolean allowDoorToOpen, Block... doors) {
		for (Block door : doors) {
			if (door == null) {
				continue;
			}

			// If we aren't allowing the door to open, check if it's already closed
			if (!allowDoorToOpen && (door.getData() & 0x4) == 0) {
				// The door is already closed and we don't want to open it
				// the bit 0x4 is set when the door is open
				continue;
			}

			// Get the top half of the door
			Block topHalf = door.getRelative(BlockFace.UP);

			// Now xor both data values with 0x4, the flag that states if the door is open
			door.setData((byte) (door.getData() ^ 0x4));

			// Play the door open/close sound
			door.getWorld().playEffect(door.getLocation(), Effect.DOOR_TOGGLE, 0);

			// Only change the block above it if it is something we can open or close
			if (isDoorMaterial(topHalf.getType())) {
				topHalf.setData((byte) (topHalf.getData() ^ 0x4));
			}
		}
	}

	/**
	 * Find a block that is adjacent to another block given a Material
	 * @param block
	 * @param material
	 * @param ignore
	 * @return
	 */
	public static Block findAdjacentBlock(Block block, Material material, Block... ignore) {
		BlockFace[] faces = new BlockFace[]{BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST};
		List<Block> ignoreList = Arrays.asList(ignore);

		for (BlockFace face : faces) {
			Block adjacentBlock = block.getRelative(face);

			if (adjacentBlock.getType() == material && !ignoreList.contains(adjacentBlock)) {
				return adjacentBlock;
			}
		}

		return null;
	}

	public static boolean isDoorMaterial(Material material) {
		return material == Material.IRON_DOOR_BLOCK || material == Material.WOODEN_DOOR || material == Material.FENCE_GATE;
	}

	public static boolean onDoorInteract(Block block) {
		if (!isDoorMaterial(block.getType())) {
			return false;
		}
		// Are we looking at the top half?
		if ((block.getData() & 0x8) == 0x8) {
			// Inspect the bottom half instead.
			block = block.getRelative(BlockFace.DOWN);
		}
		Block oBlock = getDoubleDoor(block.getLocation());
		System.out.println(oBlock);
		changeDoorStates(true, (block.getType() == Material.WOODEN_DOOR ? null : block), oBlock);
		if ((block.getData() & 0x4) != 0) {
			final Block finalBlock = block;
			final Block finalOBlock = oBlock;
			final DoorBukkitLock lock = (DoorBukkitLock) AqualockPlugin.getRegistry().getLock(finalBlock.getWorld().getUID(), finalBlock.getX(), finalBlock.getY(), finalBlock.getZ());
			System.out.println(lock.getAutocloseTimer());
			if (lock.getAutocloseTimer() > 0) {
				AqualockPlugin.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(AqualockPlugin.getInstance(), new Runnable() {
					@Override
					public void run() {
						changeDoorStates(false, finalBlock, finalOBlock);
					}
				}, lock.getAutocloseTimer() * 20);
			}
		}
		return true;
	}
}
