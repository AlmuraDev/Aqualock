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
package com.almuramc.aqualock.common;

import java.util.Collection;
import java.util.HashSet;

import com.almuramc.aqualock.common.api.lock.Lock;
import com.almuramc.aqualock.common.api.registry.Registry;

public class CommonLockRegistry implements Registry {
	private final HashSet<Lock> registry;

	public CommonLockRegistry() {
		registry = new HashSet<Lock>();
	}

	@Override
	public Registry addLock(Lock lock) {
		if (lock == null) {
			throw new NullPointerException("Trying to add a null lock to the registry!");
		}
		if (!registry.contains(lock)) {
			registry.add(lock);
		}
		return this;
	}

	@Override
	public Registry addLocks(Collection<Lock> locks) {
		if (locks == null) {
			throw new NullPointerException("Trying to add a null collection of locks to the registry!");
		}

		//Registry either contains all of the locks or none of the locks
		if (registry.containsAll(locks)) {
			return this;
		} else if (!registry.containsAll(locks)) {
			registry.addAll(locks);
			return this;
		}

		//Collection contains some locks that the registry doesn't, loop through and add them.
		for (Lock lock : locks) {
			if (registry.contains(lock)) {
				continue;
			}
			registry.add(lock);
		}

		return this;
	}

	@Override
	public Registry removeLock(Lock lock) {
		if (lock == null) {
			throw new NullPointerException("Trying to remove a null lock from the registry!");
		}
		if (registry.contains(lock)) {
			registry.remove(lock);
		}
		return this;
	}

	@Override
	public Registry removeLocks(Collection<Lock> locks) {
		if (locks == null) {
			throw new NullPointerException("Trying to remove a null collection of locks from the registry!");
		}

		//Registry either doesn't contain any of the locks or all the locks
		if (!registry.containsAll(locks)) {
			return this;
		} else if (registry.containsAll(locks)) {
			registry.removeAll(locks);
			return this;
		}

		//Registry contains some of the locks, loop through and remove them
		for (Lock lock : locks) {
			if (!registry.contains(lock)) {
				continue;
			}
			registry.remove(lock);
		}

		return this;
	}
}
