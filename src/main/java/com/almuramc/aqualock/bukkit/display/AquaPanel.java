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
package com.almuramc.aqualock.bukkit.display;

import com.almuramc.aqualock.bukkit.AqualockPlugin;
import com.almuramc.aqualock.bukkit.display.button.ApplyButton;
import com.almuramc.aqualock.bukkit.display.button.CloseButton;
import com.almuramc.aqualock.bukkit.display.checkbox.EveryoneCheckbox;
import com.almuramc.aqualock.bukkit.display.field.CoOwnerField;
import com.almuramc.aqualock.bukkit.display.field.DamageField;
import com.almuramc.aqualock.bukkit.display.field.OwnerField;
import com.almuramc.aqualock.bukkit.display.field.PasswordField;
import com.almuramc.aqualock.bukkit.display.field.UseCostField;
import com.almuramc.aqualock.bukkit.display.field.UserField;
import com.almuramc.aqualock.bukkit.display.label.CoOwnerLabel;
import com.almuramc.aqualock.bukkit.display.label.CreateCostLabel;
import com.almuramc.aqualock.bukkit.display.label.CreateCostValueLabel;
import com.almuramc.aqualock.bukkit.display.label.DamageLabel;
import com.almuramc.aqualock.bukkit.display.label.OwnerLabel;
import com.almuramc.aqualock.bukkit.display.label.PasswordLabel;
import com.almuramc.aqualock.bukkit.display.label.UseCostLabel;
import com.almuramc.aqualock.bukkit.display.label.UserLabel;
import com.almuramc.bolt.lock.Lock;

import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericCheckBox;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;

import org.bukkit.Location;

public class AquaPanel extends GenericPopup {
	private final AqualockPlugin plugin;
	//Widgets
	private final GenericButton closeButton, applyButton;
	private final GenericCheckBox everyoneCheckbox;
	private final GenericLabel usersLabel, coownersLabel, costToUseLabel, damageOnFailLabel, costToCreateOutputLabel, costToCreateLabel, passwordLabel, ownerLabel;
	private final GenericTextField usersField, coownersField, costToUseField, damageOnFailField, passwordField, ownerField;
	private final GenericTexture borderTexture;
	//geo
	private Location location;

	public AquaPanel(AqualockPlugin plugin) {
		this.plugin = plugin;
		borderTexture = new GenericTexture("http://www.almuramc.com/images/playerplus.png");
		borderTexture
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setPriority(RenderPriority.High)
				.setWidth(400)
				.setHeight(200)
				.shiftXPos(-185)
				.shiftYPos(-80);
		closeButton = new CloseButton(plugin);
		closeButton
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(40)
				.shiftXPos(142)
				.shiftYPos(87);
		applyButton = new ApplyButton(plugin);
		applyButton
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(40)
				.shiftXPos(90)
				.shiftYPos(87);
		this.setTransparent(true);
		usersField = new UserField();
		usersField
				.setMaximumLines(5)
				.setMaximumCharacters(100)
				.setTabIndex(3)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(10)
				.setWidth(165)
				.shiftXPos(15)
				.shiftYPos(60);
		usersLabel = new UserLabel("Users:");
		usersLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(10)
				.setWidth(40)
				.shiftXPos(15)
				.shiftYPos(43);
		coownersField = new CoOwnerField();
		coownersField
				.setMaximumLines(5)
				.setMaximumCharacters(100)
				.setTabIndex(2)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(10)
				.setWidth(165)
				.shiftXPos(15)
				.shiftYPos(23);
		coownersLabel = new CoOwnerLabel("Co-Owners:");
		coownersLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(10)
				.setWidth(40)
				.shiftXPos(15)
				.shiftYPos(6);
		costToUseField = new UseCostField();
		costToUseField
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(10)
				.setWidth(40)
				.shiftXPos(-70)
				.shiftYPos(60);
		costToUseLabel = new UseCostLabel("Cost to use:");
		costToUseLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(10)
				.setWidth(40)
				.shiftXPos(-132)
				.shiftYPos(60);
		damageOnFailField = new DamageField();
		damageOnFailField
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(10)
				.setWidth(40)
				.shiftXPos(-70)
				.shiftYPos(43);
		damageOnFailLabel = new DamageLabel("Damage on fail:");
		damageOnFailLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(10)
				.setWidth(40)
				.shiftXPos(-146)
				.shiftYPos(43);
		costToCreateOutputLabel = new CreateCostValueLabel("Test3:");
		costToCreateOutputLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(10)
				.setWidth(40)
				.shiftXPos(-70)
				.shiftYPos(23);
		costToCreateLabel = new CreateCostLabel("Cost to create:");
		costToCreateLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(10)
				.setWidth(40)
				.shiftXPos(-148)
				.shiftYPos(23);
		everyoneCheckbox = new EveryoneCheckbox();
		everyoneCheckbox
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(10)
				.setWidth(40)
				.shiftXPos(-148)
				.shiftYPos(-5);
		passwordField = new PasswordField();
		passwordField
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(12)
				.setWidth(107)
				.shiftXPos(70)
				.shiftYPos(-20);
		passwordLabel = new PasswordLabel("Password:");
		passwordLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(8)
				.setWidth(40)
				.shiftXPos(15)
				.shiftYPos(-20);
		ownerField = new OwnerField();
		ownerField
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(12)
				.setWidth(107)
				.shiftXPos(70)
				.shiftYPos(-40);
		ownerLabel = new OwnerLabel("Owner:");
		ownerLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(10)
				.setWidth(40)
				.shiftXPos(15)
				.shiftYPos(-40);
		attachWidgets(plugin, borderTexture, closeButton, applyButton, usersField, usersLabel, coownersField, coownersLabel,
				costToUseField, costToUseLabel, damageOnFailField, damageOnFailLabel, costToCreateOutputLabel,
				costToCreateLabel, everyoneCheckbox, passwordField, passwordLabel, ownerField, ownerLabel);
		passwordField.setFocus(true);
		passwordField.setTabIndex(0);
		passwordField.setMaximumCharacters(15);
		passwordField.setMaximumLines(1);
		passwordField.setPasswordField(true);
	}

	/**
	 * Populates the panel with information from the backend
	 */
	public void populate(Lock lock) {
		if (lock == null) {
			return;
		}
		ownerField.setText(lock.getOwner());
		//Loop through all co-owners and build a string to insert into the field
		final StringBuilder output = new StringBuilder();
		for (String coowner : lock.getCoOwners()) {
			if (coowner.length() != 0) {
				output.append(" ,");
			}
			output.append(coowner);
		}
		coownersField.setText(output.toString());
		costToCreateOutputLabel.setText(Double.toString(plugin.getConfiguration().getCosts().getUnlockCost(org.bukkit.Material.AIR)));
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
