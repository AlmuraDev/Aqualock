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
package com.almuramc.aqualock.bukkit;

import java.util.List;

import com.almuramc.aqualock.bukkit.util.BlockUtil;
import com.almuramc.aqualock.bukkit.util.LockUtil;
import com.almuramc.aqualock.bukkit.util.PermissionUtil;
import com.almuramc.bolt.lock.Lock;
import com.almuramc.bolt.registry.CommonRegistry;
import com.almuramc.bolt.registry.Registry;

import org.getspout.spoutapi.SpoutManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.material.Door;

public class AqualockListener implements Listener {
	private final AqualockPlugin plugin;
	private static final CommonRegistry registry;

	static {
		registry = AqualockPlugin.getRegistry();
	}

	public AqualockListener(AqualockPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		final Player breaker = event.getPlayer();
		final Block breaking = event.getBlock();
		final Lock lock = registry.getLock(breaking.getWorld().getUID(), breaking.getX(), breaking.getY(), breaking.getZ());
		if (lock != null) {
			if (!lock.getOwner().equals(breaker.getName())) {
				if (!(lock.getCoOwners().contains(breaker.getName()))) {
					breaker.sendMessage(plugin.getPrefix() + "This block is locked.");
					event.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent event) {
		final Player player = event.getPlayer();
		final Block block = event.getBlock();
		final Lock lock = registry.getLock(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ());
		if (lock != null) {
			if (!lock.getOwner().equals(player.getName())) {
				if (!(lock.getCoOwners().contains(player.getName()))) {
					SpoutManager.getPlayer(player).sendNotification("Aqua", "This block is locked!", Material.LAVA_BUCKET);
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockDamage(BlockDamageEvent event) {
		final Player player = event.getPlayer();
		final Block block = event.getBlock();
		final Lock lock = registry.getLock(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ());
		if (lock != null) {
			if (!lock.getOwner().equals(player.getName())) {
				if (!(lock.getCoOwners().contains(player.getName()))) {
					SpoutManager.getPlayer(player).sendNotification("Aqua", "This block is locked!", Material.LAVA_BUCKET);
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockFromTo(BlockFromToEvent event) {
		final Block block = event.getBlock();
		event.setCancelled(registry.getLock(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ()) != null);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onLeavesDecay(LeavesDecayEvent event) {
		final Block block = event.getBlock();
		event.setCancelled(registry.getLock(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ()) != null);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player interacter = event.getPlayer();
		Block interacted = event.getClickedBlock();
		if (interacted == null) {
			return;
		}
		final Lock lock = registry.getLock(interacted.getWorld().getUID(), interacted.getX(), interacted.getY(), interacted.getZ());
		if (lock != null) {
			if (!LockUtil.canPerformAction(interacter, "USE")) {
				if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					interacter.sendMessage(plugin.getPrefix() + "You lack the permission to break locks!");
				} else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					interacter.sendMessage(plugin.getPrefix() + "You lack the permission to use locks!");
				}
				event.setCancelled(true);
				return;
			}
			if (!LockUtil.performAction(interacter, "", interacted.getLocation(), "USE")) {
				event.setCancelled(true);
				return;
			}
			final List<Location> doors = BlockUtil.getDoubleDoor(interacted.getLocation());
			if (!doors.isEmpty()) {
				Door state = (Door) interacted.getState().getData();
				if (state.isTopHalf()) {
					final Block bottom = interacted.getRelative(BlockFace.DOWN);
					state = (Door) bottom.getState().getData();
				}
				boolean open = state.isOpen();
				for (Location loc : doors) {
					final Block block = loc.getBlock();
					if (block.equals(interacted) || block.equals(interacted.getRelative(BlockFace.UP)) || block.equals(interacted.getRelative(BlockFace.DOWN))) {
						continue;
					}
					if (open) {
						BlockUtil.closeDoor(block);
					} else {
						BlockUtil.openDoor(block);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onSignChange(SignChangeEvent event) {
		final Player player = event.getPlayer();
		final Block block = event.getBlock();
		final Lock lock = registry.getLock(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ());
		if (lock != null) {
			if (!lock.getOwner().equals(player.getName())) {
				if (!(lock.getCoOwners().contains(player.getName()))) {
					SpoutManager.getPlayer(player).sendNotification("Aqua", "This block is locked!", Material.LAVA_BUCKET);
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockIgnite(BlockIgniteEvent event) {
		final Block block = event.getBlock().getLocation().getBlock();
		event.setCancelled(registry.getLock(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ()) != null);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onLightningStrike(LightningStrikeEvent event) {
		final Block block = event.getLightning().getLocation().getBlock();
		event.setCancelled(registry.getLock(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ()) != null);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockFade(BlockFadeEvent event) {
		final Block block = event.getBlock();
		event.setCancelled(registry.getLock(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ()) != null);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPistonExtend(BlockPistonExtendEvent event) {
		if (event.getLength() > 0) {
			final List<Block> moving = event.getBlocks();
			for (Block b : moving) {
				if (registry.contains(b.getWorld().getUID(), b.getX(), b.getY(), b.getZ())) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPistonRetract(BlockPistonRetractEvent event) {
		final Block moved = event.getRetractLocation().getBlock();
		if (registry.contains(moved.getWorld().getUID(), moved.getX(), moved.getY(), moved.getZ())) {
			event.setCancelled(true);
		}
	}
}
