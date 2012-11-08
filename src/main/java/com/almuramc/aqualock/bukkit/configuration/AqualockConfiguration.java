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
import java.util.logging.Level;

import com.almuramc.aqualock.bukkit.AqualockPlugin;
import com.alta189.simplesave.Configuration;
import com.alta189.simplesave.h2.H2Configuration;
import com.alta189.simplesave.mysql.MySQLConfiguration;
import com.alta189.simplesave.sqlite.SQLiteConfiguration;

import org.getspout.spoutapi.keyboard.Keyboard;
import org.yaml.snakeyaml.error.YAMLException;

import org.bukkit.Bukkit;
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
		if (!new File(plugin.getDataFolder(), "config.yml").exists()) {
			plugin.saveDefaultConfig();
		}
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

	public final long getDoubleDoorTimer() {
		return config.getLong("default-double-door-timer", 5L);
	}

	public final CostConfiguration getCosts() {
		return costConfig;
	}

	public final LanguageConfiguration getMessages() {
		return langConfig;
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

	public Keyboard getHotkey() {
		final String hotkey = config.getString("hotkey", "KEY_L");
		Keyboard key = Keyboard.KEY_L;
		try {
			key = Keyboard.valueOf(hotkey);
		} catch (Exception e) {
			Bukkit.getLogger().log(Level.WARNING, AqualockPlugin.getPrefix() + "The entry " + hotkey + " in your config is invalid! Defaulting to l...");
		}
		return key;
	}

	public final String getDatabaseName() {
		return config.getString("sql.mode.name", "minecraft");
	}

	public final String getUsername() {
		return config.getString("sql.mode.username", "minecraft");
	}

	public final String getPassword() {
		return config.getString("sql.mode.password", "minecraft");
	}

	public final String getHost() {
		return config.getString("sql.mode.host", "localhost");
	}

	public final int getPort() {
		return config.getInt("sql.mode.port", 25564);
	}
}
