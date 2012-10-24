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
import java.util.HashMap;

import com.almuramc.aqualock.bukkit.AqualockPlugin;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class LanguageConfiguration {
	private final FileConfiguration config;
	private final HashMap<String, String> nodes = new HashMap<String, String>();

	public LanguageConfiguration(File langYml) {
		config = YamlConfiguration.loadConfiguration(langYml);
		construct();
	}

	private final void construct() {
		ConfigurationSection language = config.getConfigurationSection("language");
		if (language == null) {
			throw new IllegalStateException("Missing language section in your lang.yml.");
		}
		for (String key : language.getKeys(false)) {
			final String value = language.getString(key);
			if (value.isEmpty() || value == null) {
				Bukkit.getLogger().warning(AqualockPlugin.getPrefix() + "Encountered " + key + " in lang.yml but its either empty or invalid! Skipping...");
				continue;
			}
			if (nodes.containsKey(key)) {
				Bukkit.getLogger().warning(AqualockPlugin.getPrefix() + "Encountered duplicate " + key + " in lang.yml. Replacing with new value...");
			}
			nodes.put(key, value);
		}
	}

	public void reload() {
		nodes.clear();
		construct();
	}

	public String getCostNotEnoughLockMessage() {
		return nodes.get("cost-not-enough-lock");
	}

	public String getCostEnoughLockMessage() {
		return nodes.get("cost-enough-lock");
	}

	public String getCostMoreThanEnoughLockMessage() {
		return nodes.get("cost-more-than-enough-lock");
	}

	public String getCostNotEnoughUnLockMessage() {
		return nodes.get("cost-not-enough-unlock");
	}

	public String getCostEnoughUnLockMessage() {
		return nodes.get("cost-enough-unlock");
	}

	public String getCostMoreThanEnoughUnLockMessage() {
		return nodes.get("cost-more-than-enough-unlock");
	}

	public String getCostNotEnoughUseMessage() {
		return nodes.get("cost-not-enough-use");
	}

	public String getCostEnoughUseMessage() {
		return nodes.get("cost-enough-use");
	}

	public String getCostMoreThanEnoughUseMessage() {
		return nodes.get("cost-more-than-enough-lock");
	}

	public String getNoPermsToLockMessage() {
		return nodes.get("no-permissions-lock");
	}

	public String getNoPermsToUnlockMessage() {
		return nodes.get("no-permissions-unlock");
	}

	public String getNoPermsToUseMessage() {
		return nodes.get("no-permissions-use");
	}
}

