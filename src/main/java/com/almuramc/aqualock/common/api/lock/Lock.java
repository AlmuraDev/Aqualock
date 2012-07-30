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

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The blueprint for an object representing a lock. Implementations are welcome to provide their
 * own characteristics.
 */
public abstract class Lock implements Serializable {
	private boolean isHashed = false;
	private int hashcode = 0;

	/**
	 * Gets the owner of this lock
	 * @return The name of the owner
	 */
	public abstract String getOwner();

	/**
	 * Sets the owner of this lock.
	 * @param owner The name of the new owner of this lock.
	 */
	public abstract void setOwner(String owner);

	/**
	 * Gets the Co-Owners of this lock. A null value indicates
	 * that the lock is available to everyone.
	 * @return List of names representing co-owners or null for everyone
	 */
	public abstract List getCoOwners();

	/**
	 * Sets the Co-Owners of this lock. A null value indicates
	 * that the lock will be available to everyone.
	 * @param coowners List of names representing co-owners
	 */
	public abstract void setCoOwners(List<String> coowners);

	/**
	 * Gets the x coordinate of the position of this lock.
	 * @return The x coordinate of this lock's position
	 */
	public abstract int getX();

	/**
	 * Gets the y coordinate of the position of this lock.
	 * @return The y coordinate of this lock's position
	 */
	public abstract int getY();

	/**
	 * Gets the z coordinate of the position of this lock.
	 * @return The z coordinate of this lock's position
	 */
	public abstract int getZ();

	/**
	 * Returns whether one object is equal to another. Locks are forced to override
	 * this so the registry can find out if locks are the same.
	 * @param other the object that will be compared for equality
	 * @return True if equals, false if not
	 */
	@Override
	public abstract boolean equals(Object other);

	/**
	 * Returns a string representation of this object. Locks are forced to override
	 * this so the registry can printout detailed information.
	 * @return String object representing detailed information of this object
	 */
	@Override
	public abstract String toString();

	/**
	 * Generates a unique hashcode for this object, used when comparing.
	 * @return the hashcode of this object.
	 */
	@Override
	public int hashCode() {
		if (!isHashed) {
			hashcode = new HashCodeBuilder(7, 11).append(getX()).append(getY()).append(getZ()).toHashCode();
			isHashed = true;
		}
		return hashcode;
	}
}
