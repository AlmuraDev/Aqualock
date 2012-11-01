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

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.bukkit.Location;

public class DoorBukkitLock extends BukkitLock {
	private long autocloseTimer;

	public DoorBukkitLock(String owner, List<String> coowners, List<String> users, String passcode, Location location, byte data, double useCost, long autocloseTimer) {
		super(owner, coowners, users, passcode, location, data, useCost);
		this.autocloseTimer = autocloseTimer;
	}

	public long getAutocloseTimer() {
		return autocloseTimer;
	}

	public void setAutocloseTimer(long autocloseTimer) {
		this.autocloseTimer = autocloseTimer;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		final DoorBukkitLock other = (DoorBukkitLock) obj;
		return new org.apache.commons.lang3.builder.EqualsBuilder()
				.append(this.autocloseTimer, other.autocloseTimer)
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(super.toString())
				.append("autoclosetimer", autocloseTimer)
				.toString();
	}
}
