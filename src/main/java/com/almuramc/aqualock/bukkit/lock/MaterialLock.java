/*
 * This file is part of AquaLock.
 *
 * Copyright (c) 2012, AlmuraDev <http://www.almuramc.com/>
 * AquaLock is licensed under the Almura Development License.
 *
 * AquaLock is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * As an exception, all classes which do not reference GPL licensed code
 * are hereby licensed under the GNU Lesser Public License, as described
 * in Almura Development License.
 *
 * AquaLock is distributed in the hope that it will be useful,
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

import com.almuramc.aqualock.common.api.lock.Lock;

import org.bukkit.Material;

/**
 * The basic Bukkit-like Material lock.
 */
public class MaterialLock implements Lock {
	private String owner;
	private List<String> coowners;
	private int x, y, z;
	private Material type;
	private short data;

	public MaterialLock(String owner, List<String> coowners, int x, int y, int z, Material type, short data) {
		this.owner = owner;
		this.coowners = coowners;
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
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
	public void setCoOwners(List<String> owner) {
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
}
