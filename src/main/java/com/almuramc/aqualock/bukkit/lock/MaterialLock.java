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

import com.almuramc.bolt.lock.Lock;

import org.bukkit.Material;

/**
 * The basic Bukkit-like Material lock.
 */
public class MaterialLock extends Lock {
	private String owner;
	private List<String> coowners;
	private int x, y, z, id;
	private byte data;

	public MaterialLock(String owner, List<String> coowners, int x, int y, int z, Material type, byte data) {
		this.owner = owner;
		this.coowners = coowners;
		this.x = x;
		this.y = y;
		this.z = z;
		this.id = type.getId();
		this.data = data;
	}

	@Override
	public String getOwner() {
		return owner;
	}

	@Override
	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public List getCoOwners() {
		return coowners;
	}

	@Override
	public void setCoOwners(List<String> coowners) {
		this.coowners = coowners;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getZ() {
		return z;
	}

	public Material getType() {
		return Material.getMaterial(id);
	}

	public byte getData() {
		return this.data;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof MaterialLock)) {
			return false;
		}
		MaterialLock mobj = (MaterialLock) obj;
		if (this.owner.equals(mobj.owner) && this.coowners.equals(mobj.coowners) && this.x == mobj.x &&
				this.y == mobj.y && this.z == mobj.z && this.id == mobj.id &&
				this.data == mobj.data) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out
				.append("owner: " + this.owner)
				.append("coowners:" + this.coowners.toString())
				.append("x: " + this.x)
				.append("y: " + this.y)
				.append("z: " + this.z)
				.append("material id: " + this.id)
				.append("data: " + this.data);
		return out.toString();
	}
}
