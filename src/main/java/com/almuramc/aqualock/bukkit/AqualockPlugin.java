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
package com.almuramc.aqualock.bukkit;

import com.almuramc.aqualock.bukkit.command.AqualockCommands;
import com.almuramc.bolt.lock.Lock;
import com.almuramc.bolt.registry.CommonRegistry;
import com.almuramc.bolt.storage.SqlStorage;
import com.almuramc.bolt.storage.Storage;
import com.alta189.simplesave.h2.H2Configuration;
import com.alta189.simplesave.sqlite.SQLiteConfiguration;

import org.bukkit.plugin.java.JavaPlugin;

public class AqualockPlugin extends JavaPlugin {
	private static final CommonRegistry registry;
	private static Storage backend;

	static {
		registry = new CommonRegistry();
	}

	@Override
	public void onDisable() {
		backend.onUnLoad();
	}

	@Override
	public void onEnable() {
		backend = new SqlStorage(new SQLiteConfiguration(), getDataFolder());
		backend.onLoad();
		for (Lock lock : backend.getAll()) {
			registry.addLock(lock);
		}
		this.getCommand("aqualock").setExecutor(new AqualockCommands(this));
		this.getServer().getPluginManager().registerEvents(new AqualockListener(this), this);
	}

	public CommonRegistry getRegistry() {
		return registry;
	}

	public Storage getBackend() {
		return backend;
	}
}
