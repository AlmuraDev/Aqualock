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

import java.util.Iterator;
import java.util.List;

import com.almuramc.aqualock.bukkit.display.AquaPass;
import com.almuramc.aqualock.bukkit.lock.BukkitLock;
import com.almuramc.aqualock.bukkit.util.BlockUtil;
import com.almuramc.aqualock.bukkit.util.LockUtil;
import com.almuramc.aqualock.bukkit.util.PermissionUtil;
import com.almuramc.bolt.lock.Lock;
import com.almuramc.bolt.registry.CommonRegistry;

import org.getspout.spoutapi.SpoutManager;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.LightningStrikeEvent;

public class AqualockListener implements Listener {
	private final AqualockPlugin plugin;
	private static final CommonRegistry registry;

	static {
		registry = AqualockPlugin.getRegistry();
	}

	public AqualockListener(AqualockPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		final Player breaker = event.getPlayer();
		final Block breaking = event.getBlock();
		final Lock lock = registry.getLock(breaking.getWorld().getUID(), breaking.getX(), breaking.getY(), breaking.getZ());
		if (lock != null) {
			if (!lock.getOwner().equals(breaker.getName())) {
				if (!(lock.getCoOwners().contains(breaker.getName()))) {
					SpoutManager.getPlayer(breaker).sendNotification("Aqualock", "This block is locked!", Material.LAVA_BUCKET);
					event.setCancelled(true);
					breaker.damage(((BukkitLock) lock).getDamage());
					return;
				}
			}
			SpoutManager.getPlayer(breaker).sendNotification("Aqualock", "Unlock first!", Material.LAVA_BUCKET);
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		final Player player = event.getPlayer();
		final Block block = event.getBlock();
		final Lock lock = registry.getLock(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ());
		if (lock != null) {
			if (!lock.getOwner().equals(player.getName())) {
				if (!(lock.getCoOwners().contains(player.getName()))) {
					SpoutManager.getPlayer(player).sendNotification("Aqualock", "This block is locked!", Material.LAVA_BUCKET);
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockDamage(BlockDamageEvent event) {
		final Player player = event.getPlayer();
		final Block block = event.getBlock();
		final Lock lock = registry.getLock(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ());
		if (lock != null) {
			if (!lock.getOwner().equals(player.getName())) {
				if (!(lock.getCoOwners().contains(player.getName()))) {
					SpoutManager.getPlayer(player).sendNotification("Aqualock", "This block is locked!", Material.LAVA_BUCKET);
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockFromTo(BlockFromToEvent event) {
		final Block block = event.getBlock();
		event.setCancelled(registry.getLock(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ()) != null);
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onLeavesDecay(LeavesDecayEvent event) {
		final Block block = event.getBlock();
		event.setCancelled(registry.getLock(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ()) != null);
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
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
					SpoutManager.getPlayer(interacter).sendNotification("Aqualock", "Break denied!", Material.LAVA_BUCKET);
				} else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					SpoutManager.getPlayer(interacter).sendNotification("Aqualock", "Interact denied!", Material.LAVA_BUCKET);
				}
				event.setCancelled(true);
				return;
			}
			if (BlockUtil.shouldOpenPassPanel(interacted.getType())) {
				final String password = ((BukkitLock) lock).getPasscode();
				if (!PermissionUtil.has(interacter, interacted.getWorld(), "aqualock.admin") && password != null && !password.isEmpty()) {
					AquaPass passwordPopup = new AquaPass(AqualockPlugin.getInstance());
					passwordPopup.setLocation(interacted.getLocation());
					SpoutManager.getPlayer(interacter).getMainScreen().attachPopupScreen(passwordPopup);
					passwordPopup.populate(lock);
					event.setCancelled(true);
					return;
				}
				if (!LockUtil.use(interacter.getName(), "", interacted.getLocation(), ((BukkitLock) lock).getUseCost())) {
					event.setCancelled(true);
					return;
				}
				SpoutManager.getPlayer(interacter).sendNotification("Aqualock", "Opened with fingerprint", Material.CAKE);
			} else {
				if (!LockUtil.use(interacter.getName(), "", interacted.getLocation(), ((BukkitLock) lock).getUseCost())) {
					event.setCancelled(true);
					return;
				}
			}
			BlockUtil.onDoorInteract(interacted, true);
			BlockUtil.onGateInteract(interacted);
		}
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onSignChange(SignChangeEvent event) {
		final Player player = event.getPlayer();
		final Block block = event.getBlock();
		final Lock lock = registry.getLock(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ());
		if (lock != null) {
			if (!lock.getOwner().equals(player.getName())) {
				if (!(lock.getCoOwners().contains(player.getName()))) {
					SpoutManager.getPlayer(player).sendNotification("Aqualock", "This block is locked!", Material.LAVA_BUCKET);
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockIgnite(BlockIgniteEvent event) {
		final Block block = event.getBlock().getLocation().getBlock();
		event.setCancelled(registry.getLock(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ()) != null);
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onLightningStrike(LightningStrikeEvent event) {
		final Block block = event.getLightning().getLocation().getBlock();
		event.setCancelled(registry.getLock(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ()) != null);
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockFade(BlockFadeEvent event) {
		final Block block = event.getBlock();
		event.setCancelled(registry.getLock(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ()) != null);
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
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

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onPistonRetract(BlockPistonRetractEvent event) {
		final Block moved = event.getRetractLocation().getBlock();
		if (registry.contains(moved.getWorld().getUID(), moved.getX(), moved.getY(), moved.getZ())) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockBurn(BlockBurnEvent event) {
		final Block block = event.getBlock();
		event.setCancelled(registry.getLock(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ()) != null);
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onEntityExplode(EntityExplodeEvent event) {
		Iterator<Block> iter = event.blockList().iterator();
		while (iter.hasNext()) {
			Block block = iter.next();
			if (registry.contains(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ())) {
				iter.remove();
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockPhysics(BlockPhysicsEvent event) {
		final Block block = event.getBlock();
		event.setCancelled(registry.contains(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ()));
	}
}
