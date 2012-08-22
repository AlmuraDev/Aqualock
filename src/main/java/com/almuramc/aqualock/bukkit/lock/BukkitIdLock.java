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
import java.util.UUID;

import com.almuramc.bolt.lock.type.IdLock;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.bukkit.Material;

/**
 * Basic Bukkit-like Material lock extension that stores block data.
 */
public class BukkitIdLock extends IdLock {
	private int id;
	private byte data;

	public BukkitIdLock(String owner, List<String> coowners, UUID worldIdentifier, int x, int y, int z, Material material, byte data) {
		super(owner, coowners, worldIdentifier, x, y, z, material.getId());
		this.id = material.getId();
		this.data = data;
	}

	public BukkitIdLock(String owner, List<String> coowners, UUID worldIdentifier, int x, int y, int z, Material material) {
		this(owner, coowners, worldIdentifier, x, y, z, material, (byte) 0);
	}

	public BukkitIdLock(String owner, UUID worldIdentifier, int x, int y, int z, Material material) {
		this(owner, null, worldIdentifier, x, y, z, material);
	}

	public Material getMaterial() {
		return Material.getMaterial(id);
	}

	public byte getData() {
		return data;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		final BukkitIdLock other = (BukkitIdLock) obj;
		return new org.apache.commons.lang3.builder.EqualsBuilder()
				.append(this.data, other.data)
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(super.toString())
				.append("data", data)
				.toString();
	}
}
