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
package com.almuramc.aqualock.bukkit;

import java.util.UUID;

import com.almuramc.aqualock.bukkit.lock.BukkitIdLock;
import com.almuramc.bolt.lock.Lock;
import com.almuramc.bolt.registry.Registry;
import com.almuramc.bolt.registry.WorldRegistry;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public class AqualockListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onWorldLoad(WorldLoadEvent event) {
		UUID worldIdentifier = event.getWorld().getUID();
		//TODO Fetch from Storage
		WorldRegistry.addWorld(worldIdentifier);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onWorldUnload(WorldUnloadEvent event) {
		UUID worldIdentifier = event.getWorld().getUID();
		//TODO save to Storage
		WorldRegistry.removeWorld(worldIdentifier);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		final Block block = event.getBlock();
		final UUID ident = block.getWorld().getUID();
		if (!WorldRegistry.contains(ident)) {
			 return;
		}
		final Registry registry = WorldRegistry.getRegistry(ident);
		final int x = block.getX();
		final int y = block.getY();
		final int z = block.getZ();
		if (!registry.contains(x, y, z)) {
			return;
		}
		final String playerName = event.getPlayer().getName();
		final Lock lock = registry.getLock(x, y, z);
		//Handle generic-like characteristics
		if ((lock.getOwner().equals(playerName)) || (lock.getCoOwners().contains(playerName))) {
			event.setCancelled(true);
		}
		//Handle Bukkit-like characteristics
		if (lock instanceof BukkitIdLock) {
			final BukkitIdLock temp = (BukkitIdLock) lock;
			if (!temp.getMaterial().equals(block.getType()) || temp.getData() != block.getData()) {
				event.setCancelled(true);
			}
		}
	}
}