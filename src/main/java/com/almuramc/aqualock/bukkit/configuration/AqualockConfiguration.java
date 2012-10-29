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
package com.almuramc.aqualock.bukkit.configuration;

import java.io.File;

import com.almuramc.aqualock.bukkit.AqualockPlugin;
import com.alta189.simplesave.Configuration;
import com.alta189.simplesave.h2.H2Configuration;
import com.alta189.simplesave.mysql.MySQLConfiguration;
import com.alta189.simplesave.sqlite.SQLiteConfiguration;

import org.yaml.snakeyaml.error.YAMLException;

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

	public final CostConfiguration getCosts() {
		return costConfig;
	}

	public final LanguageConfiguration getMessages() {
		return langConfig;
	}

	public double getDoubleDoorCloseTimer() {
		return config.getDouble("double-door-close-timer");
	}

	public Configuration getSqlConfiguration() {
		final String mode = config.getString("sql.mode");
		switch (mode) {
			case "sql":
				return new MySQLConfiguration();
			case "sqlite":
				return new SQLiteConfiguration();
			case "h2":
				return new H2Configuration();
			default:
				throw new YAMLException("Specified mode for SQL configuration: " + mode + " is invalid.");
		}
	}
}
