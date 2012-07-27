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
package com.almuramc.aqualock.common.api.lock;

import java.util.List;

/**
 * The blueprint for an object representing a lock. Implementations are welcome to provide their
 * own characteristics.
 */
public interface Lock {
	/**
	 * Gets the owner of this lock
	 * @return The name of the owner
	 */
	public String getOwner();

	/**
	 * Sets the owner of this lock.
	 * @param owner The name of the new owner of this lock.
	 */
	public void setOwner(String owner);

	/**
	 * Gets the Co-Owners of this lock. A null value indicates
	 * that the lock is available to everyone.
	 * @return List of names representing co-owners or null for everyone
	 */
	public List getCoOwners();

	/**
	 * Sets the Co-Owners of this lock. A null value indicates
	 * that the lock will be available to everyone.
	 * @param owner List of names representing co-owners
	 */
	public void setCoOwners(List<String> owner);

	/**
	 * Gets the x coordinate of the position of this lock
	 * @return The x coordinate of this lock's position
	 */
	public int getX();

	/**
	 * Gets the y coordinate of the position of this lock
	 * @return The y coordinate of this lock's position
	 */
	public int getY();

	/**
	 * Gets the z coordinate of the position of this lock
	 * @return The z coordinate of this lock's position
	 */
	public int getZ();
}
