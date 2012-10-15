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

import org.yaml.snakeyaml.events.CollectionStartEvent;

import org.bukkit.Bukkit;
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
	 * Returns if the block is apart of a double door.
	 * @param block The block to check
	 * @return true if a part of a double door, false if not
	 */
	public static boolean isDoubleDoor(Block block) {
		return !getDoubleDoor(block).isEmpty();
	}

	/**
	 * Gets a list containing all blocks a part of a double door. The list will have no fewer or no more than 4 elements.
	 * Note: the search for double door blocks is 2D and based on the material of the source block passed in.
	 *
	 * If the list returned is empty, it isn't a double door.
	 * @param block The block to determine if in a double door
	 * @return Empty list if not in a double door or the 4 blocks comprising the double door.
	 */
	public static List<Block> getDoubleDoor(Block block) {
		//Passed in block is not a double door
		if (!isDoorMaterial(block.getType())) {
			return Collections.emptyList();
		}
		Bukkit.getLogger().info(">>>INTERACTED DOOR.");
		final ArrayList<Block> doors = new ArrayList<Block>(4);
		//Now we need to do a check around this block to find out if its in a double door
		Door source = new Door(block.getType(), block.getData());
		//Check the immediate east and west of this block
		Block east = block.getRelative(BlockFace.EAST);
		Block west = block.getRelative(BlockFace.WEST);
		if (!isDoorMaterial(east.getType(), block.getType())) {
			if (!isDoorMaterial(west.getType(), block.getType())) {
				return Collections.emptyList();
			}
			doors.add(west);
			Bukkit.getLogger().info(">>>WEST is a door.");
		} else {
			Bukkit.getLogger().info(">>>EAST is a door.");
			doors.add(east);
		}
		//If we are this far then we know 2 of the four blocks are doors
		if (source.isTopHalf()) {
			Block bottom = block.getRelative(BlockFace.DOWN);
			//The original block was the top-half of the door, so check the bottom
			if (!isDoorMaterial(bottom.getType(), block.getType())) {
				return Collections.emptyList();
			}
			Bukkit.getLogger().info(">>>BOTTOM is a door.");
			doors.add(bottom);
			//At this point we know that 3 of the 4 blocks are doors, lets seek out the 4th block
			Block bottomEast = bottom.getRelative(BlockFace.EAST);
			//Check if the diagonally down eastern block from the source is a door and the block directly above it is the eastern door. If so its block 4 and its a double door
			if (!isDoorMaterial(bottomEast.getType(), block.getType()) || !bottomEast.getRelative(BlockFace.UP).equals(east)) {
				Block bottomWest = bottom.getRelative(BlockFace.WEST);
				if (!isDoorMaterial(bottomWest.getType(), block.getType()) || !bottomWest.getRelative(BlockFace.UP).equals(west)) {
					return Collections.emptyList();
				}
				doors.add(bottomWest);
				Bukkit.getLogger().info(">>>BOTTOM-WEST is a door.");
			} else {
				Bukkit.getLogger().info(">>>BOTTOM-EAST is a door.");
				doors.add(bottomEast);
			}
		} else {
			Block top = block.getRelative(BlockFace.UP);
			//The original block was the bottom-half of the door, so check the top
			if (!isDoorMaterial(top.getType(), block.getType())) {
				return Collections.emptyList();
			}
			Bukkit.getLogger().info(">>>TOP is a door.");
			doors.add(top);
			//At this point we know that 3 of the 4 blocks are doors, lets seek out the 4th block
			Block topEast = top.getRelative(BlockFace.EAST);
			//Check if the diagonally top eastern block from the source is a door and the block directly below it is the eastern door. If so its block 4 and its a double door
			if (!isDoorMaterial(topEast.getType(), block.getType()) || !topEast.getRelative(BlockFace.DOWN).equals(east)) {
				Block topWest = top.getRelative(BlockFace.WEST);
				if (!isDoorMaterial(topWest.getType(), block.getType()) || !topWest.getRelative(BlockFace.DOWN).equals(west)) {
					return Collections.emptyList();
				}
				Bukkit.getLogger().info(">>>TOP-WEST is a door.");
				doors.add(topWest);
			} else {
				Bukkit.getLogger().info(">>>TOP-EAST is a door.");
				doors.add(topEast);
			}
		}
		return doors;
	}

	/**
	 * Toggles blocks passed in to the close data state of blocks. This will have adverse affects on blocks that are not
	 * doors and can cause unexpected results.
	 * @param doors List of blocks that should have data toggled
	 * @param open Flag to determine if the blocks should have the "open" databit turned "off" or "on"
	 */
	public static void toggleDoubleDoors(List<Block> doors, boolean open) {
		for (Block block : doors) {
			Door door = new Door(block.getType(), block.getData());
			door.setOpen(open);
		}
	}

	private static boolean isDoorMaterial(Material material) {
		return isDoorMaterial(material, null);
	}

	private static boolean isDoorMaterial(Material material, Material toMatch) {
		System.out.println("ToMatch: " + toMatch);
		if (toMatch == null) {
			toMatch = material;
		}
		System.out.println("ToMatch: " + toMatch);
		System.out.println("Material: " + material);
		if ((material.equals(Material.WOODEN_DOOR) || material.equals(Material.IRON_DOOR_BLOCK)) && toMatch.equals(material)) {
			return true;
		}
		return false;
	}
}
