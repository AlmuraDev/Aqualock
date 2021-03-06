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
package com.almuramc.aqualock.bukkit.lock;

import java.util.List;

import com.almuramc.bolt.lock.type.BasicLock;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.bukkit.Location;

/**
 * Basic Bukkit-like lock extension that stores block data.
 */
public class BukkitLock extends BasicLock {
	private String passcode;
	private byte data;
	private double useCost;
	private int damage;

	public BukkitLock(String owner, List<String> coowners, List<String> users, String passcode, Location location, byte data, double useCost, int damage) {
		super(owner, coowners, users, location.getWorld().getUID(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
		this.passcode = passcode;
		this.data = data;
		this.useCost = useCost;
		this.damage = damage;
	}

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}

	public byte getData() {
		return data;
	}

	public void setData(byte data) {
		this.data = data;
	}

	public double getUseCost() {
		return useCost;
	}

	public void setUseCost(double useCost) {
		this.useCost = useCost;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		final BukkitLock other = (BukkitLock) obj;
		return new EqualsBuilder()
				.append(this.passcode, other.passcode)
				.append(this.data, other.data)
				.append(this.useCost, other.useCost)
				.append(this.damage, other.damage)
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(super.toString())
				.append("passcode", passcode)
				.append("data", data)
				.append("cost", useCost)
				.append("damage", damage)
				.toString();
	}
}
