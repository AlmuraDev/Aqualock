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

import com.almuramc.bolt.lock.type.MaterialLock;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.bukkit.Material;

/**
 * Basic Bukkit-like Material lock extension that stores block data and Material (if id isn't desired).
 */
public class BukkitMaterialLock extends MaterialLock {
	private Material material;
	private byte data;

	public BukkitMaterialLock(String owner, List<String> coowners, int x, int y, int z, int id, byte data) {
		super(owner, coowners, x, y, z, id);
		this.material = Material.getMaterial(id);
		this.data = data;
	}

	public BukkitMaterialLock(String owner, List<String> coowners, int x, int y, int z, int id) {
		this(owner, coowners, x, y, z, id, (byte) 0);
	}

	public BukkitMaterialLock(String owner, int x, int y, int z, int id) {
		this(owner, null, x, y, z, id);
	}

	public Material getMaterial() {
		return material;
	}

	public byte getData() {
		return data;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		final BukkitMaterialLock other = (BukkitMaterialLock) obj;
		return new org.apache.commons.lang3.builder.EqualsBuilder()
				.append(this.material, other.material)
				.append(this.data, other.data)
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(super.toString())
				.append("material", material)
				.append("data", data)
				.toString();
	}
}
