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
package com.almuramc.aqualock.bukkit.configuration;

import java.io.File;

import com.almuramc.aqualock.bukkit.AqualockPlugin;

import org.bukkit.configuration.file.FileConfiguration;

public final class AqualockConfiguration {
	private final AqualockPlugin plugin;
	//Configurations
	private final FileConfiguration config;
	private final CostConfiguration costConfig;
	private final LanguageConfiguration langConfig;

	public AqualockConfiguration(AqualockPlugin plugin) {
		this.plugin = plugin;
		//Read in default config.yml
		config = plugin.getConfig();
		//Setup cost file
		File costYml = new File(plugin.getDataFolder(), "cost.yml");
		if (!costYml.exists()) {
			plugin.saveResource("cost.yml", true);
		}
		costConfig = new CostConfiguration(costYml);
		//Setup language file
		File langYml = new File(plugin.getDataFolder(), "lang.yml");
		if (!langYml.exists()) {
			plugin.saveResource("lang.yml", true);
		}
		langConfig = new LanguageConfiguration(langYml);
	}

	public final void reload() {
		plugin.reloadConfig();
		costConfig.reload();
		langConfig.reload();
	}

	public final FileConfiguration getBuiltin() {
		return config;
	}

	public final CostConfiguration getCosts() {
		return costConfig;
	}

	public final LanguageConfiguration getMessages() {
		return langConfig;
	}
}
