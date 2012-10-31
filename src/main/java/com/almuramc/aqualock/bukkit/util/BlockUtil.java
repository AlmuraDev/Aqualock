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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.material.Door;

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
	public static List<Location> getDoubleDoor(Location location) {
		//Passed in block is not a double door
		final Block block = location.getBlock();
		if (!isDoorMaterial(block.getType())) {
			return Collections.emptyList();
		}
		final ArrayList<Location> doors = new ArrayList<Location>(2);
		doors.add(block.getLocation());
		Block other = findAdjacentBlock(location.getBlock(), location.getBlock().getType());
		if (other != null) {
			doors.add(other.getLocation());
		}
		return doors;
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
	 *
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

	private static boolean isDoorMaterial(Material material) {
		return material == Material.IRON_DOOR_BLOCK || material == Material.WOODEN_DOOR || material == Material.FENCE_GATE;
	}

	public static boolean onDoorInteract(Block block) {
		if (isDoorMaterial(block.getType())) {
			return false;
		}
		// Are we looking at the top half?
		// If we are, we need to get the bottom half instead
		Door source = (Door) block.getState().getData();
		if (source.isTopHalf()) {
			// Inspect the bottom half instead, fool!
			block = block.getRelative(BlockFace.DOWN);
		}
		final List <Location> other = getDoubleDoor(block.getLocation());
		Block oBlock = other.get(2).getBlock();
		final Door otherSrc = (Door) oBlock.getState().getData();
		if (otherSrc.isTopHalf()) {
			oBlock = oBlock.getRelative(BlockFace.DOWN);
		}
		if (other != null) {
			changeDoorStates(true, ((block.getType() == Material.WOODEN_DOOR || block.getType() == Material.FENCE_GATE) ? null : block), oBlock);
		}
		return true;
	}
}
