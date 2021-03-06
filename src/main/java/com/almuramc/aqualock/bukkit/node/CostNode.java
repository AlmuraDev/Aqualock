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
package com.almuramc.aqualock.bukkit.node;

import org.bukkit.Material;

/**
 * A "Cost" node
 */
public final class CostNode {
	private Material material;
	private double lock, unlock, update, use;

	public CostNode(Material material, double lock, double unlock, double update, double use) {
		this.material = material;
		this.lock = lock;
		this.unlock = unlock;
		this.update = update;
		this.use = use;
	}

	public Material getMaterial() {
		return material;
	}

	public double getLock() {
		return lock;
	}

	public double getUnlock() {
		return unlock;
	}

	public double getUpdate() {
		return update;
	}

	public double getUse() {
		return use;
	}
}
