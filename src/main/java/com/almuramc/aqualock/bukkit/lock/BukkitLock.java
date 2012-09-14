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
package com.almuramc.aqualock.bukkit.lock;

import java.util.List;

import com.almuramc.bolt.lock.type.BasicLock;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.bukkit.Location;

/**
 * Basic Bukkit-like lock extension that stores block data.
 */
public class BukkitLock extends BasicLock {
	private String passcode;
	private byte data;

	public BukkitLock(String owner, List<String> coowners, String passcode, Location location, byte data) {
		super(owner, coowners, location.getWorld().getUID(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
		this.passcode = passcode;
		this.data = data;
	}

	public String getPasscode() {
		return passcode;
	}

	public byte getData() {
		return data;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		final BukkitLock other = (BukkitLock) obj;
		return new org.apache.commons.lang3.builder.EqualsBuilder()
				.append(this.passcode, other.passcode)
				.append(this.data, other.data)
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(super.toString())
				.append("passcode", passcode)
				.append("data", data)
				.toString();
	}
}
