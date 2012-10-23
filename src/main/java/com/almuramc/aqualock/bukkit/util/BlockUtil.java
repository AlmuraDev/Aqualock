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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.almuramc.aqualock.bukkit.AqualockPlugin;

import org.yaml.snakeyaml.events.CollectionStartEvent;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
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
	 * Returns if the block is apart of a double door.
	 * @return true if a part of a double door, false if not
	 */
	public static boolean isDoubleDoor(Location location, BlockFace clicked) {
		return !getDoubleDoor(location, clicked).isEmpty();
	}

	/**
	 * Gets a list containing all blocks a part of a double door. The list will have no fewer or no more than 4 elements.
	 * Note: the search for double door blocks is 2D and based on the material of the source block passed in.
	 *
	 * If the list returned is empty, it isn't a double door.
	 * @return Empty list if not in a double door or the 4 blocks comprising the double door.
	 */
	public static List<Location> getDoubleDoor(Location location, BlockFace clicked) {
		//Passed in block is not a double door
		final Block block = location.getBlock();
		if (!isDoorMaterial(block.getType())) {
			return Collections.emptyList();
		}
		final ArrayList<Location> doors = new ArrayList<Location>(4);
		doors.add(block.getLocation());
		//Now we need to do a check around this block to find out if its in a double door
		Door source = new Door(block.getType(), block.getData());
		BlockFace checkLeft;
		BlockFace checkRight;
		if (clicked.equals(BlockFace.NORTH) || clicked.equals(BlockFace.SOUTH)) {
			checkLeft = BlockFace.WEST;
			checkRight = BlockFace.EAST;
		} else {
			checkLeft = BlockFace.SOUTH;
			checkRight = BlockFace.NORTH;
		}
		//Check the immediate east and west of this block
		Block east = block.getRelative(checkLeft);
		Block west = block.getRelative(checkRight);
		if (!isDoorMaterial(east.getType(), block.getType())) {
			if (!isDoorMaterial(west.getType(), block.getType())) {
				return Collections.emptyList();
			}
			doors.add(west.getLocation());
		} else {
			doors.add(east.getLocation());
		}
		//If we are this far then we know 2 of the four blocks are doors
		if (source.isTopHalf()) {
			Block bottom = block.getRelative(BlockFace.DOWN);
			//The original block was the top-half of the door, so check the bottom
			if (!isDoorMaterial(bottom.getType(), block.getType())) {
				return Collections.emptyList();
			}
			doors.add(bottom.getLocation());
			//At this point we know that 3 of the 4 blocks are doors, lets seek out the 4th block
			Block bottomEast = bottom.getRelative(checkLeft);
			//Check if the diagonally down eastern block from the source is a door and the block directly above it is the eastern door. If so its block 4 and its a double door
			if (!isDoorMaterial(bottomEast.getType(), block.getType()) || !bottomEast.getRelative(BlockFace.UP).equals(east)) {
				Block bottomWest = bottom.getRelative(checkRight);
				if (!isDoorMaterial(bottomWest.getType(), block.getType()) || !bottomWest.getRelative(BlockFace.UP).equals(west)) {
					return Collections.emptyList();
				}
				doors.add(bottomWest.getLocation());
			} else {
				doors.add(bottomEast.getLocation());
			}
		} else {
			Block top = block.getRelative(BlockFace.UP);
			//The original block was the bottom-half of the door, so check the top
			if (!isDoorMaterial(top.getType(), block.getType())) {
				return Collections.emptyList();
			}
			doors.add(top.getLocation());
			//At this point we know that 3 of the 4 blocks are doors, lets seek out the 4th block
			Block topEast = top.getRelative(checkLeft);
			//Check if the diagonally top eastern block from the source is a door and the block directly below it is the eastern door. If so its block 4 and its a double door
			if (!isDoorMaterial(topEast.getType(), block.getType()) || !topEast.getRelative(BlockFace.DOWN).equals(east)) {
				Block topWest = top.getRelative(checkRight);
				if (!isDoorMaterial(topWest.getType(), block.getType()) || !topWest.getRelative(BlockFace.DOWN).equals(west)) {
					return Collections.emptyList();
				}
				doors.add(topWest.getLocation());
			} else {
				doors.add(topEast.getLocation());
			}
		}
		return doors;
	}

	private static boolean isDoorMaterial(Material material) {
		return isDoorMaterial(material, null);
	}

	private static boolean isDoorMaterial(Material material, Material toMatch) {
		if (toMatch == null) {
			toMatch = material;
		}
		if ((material.equals(Material.WOODEN_DOOR) || material.equals(Material.IRON_DOOR_BLOCK)) && toMatch.equals(material)) {
			return true;
		}
		return false;
	}

	public static boolean isDoorClosed(Block block) {
		byte data = block.getData();
		if ((data & 0x8) == 0x8) {
			block = block.getRelative(BlockFace.DOWN);
			data = block.getData();
		}
		return ((data & 0x4) == 0);
	}

	public static void openDoor(Block block) {
		byte data = block.getData();
		if ((data & 0x8) == 0x8) {
			block = block.getRelative(BlockFace.DOWN);
			data = block.getData();
		}
		if (isDoorClosed(block)) {
			data = (byte) (data | 0x4);
			block.setData(data, true);
			block.getState().update(true);
			block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
		}
	}

	public static void closeDoor(Block block) {
		byte data = block.getData();
		if ((data & 0x8) == 0x8) {
			block = block.getRelative(BlockFace.DOWN);
			data = block.getData();
		}
		if (!isDoorClosed(block)) {
			data = (byte) (data & 0xb);
			block.setData(data, true);
			block.getState().update(true);
			block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
		}
	}
}
