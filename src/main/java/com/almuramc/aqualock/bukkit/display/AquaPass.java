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

import java.util.List;

import com.almuramc.aqualock.bukkit.AqualockPlugin;
import com.almuramc.aqualock.bukkit.display.button.ApplyButton;
import com.almuramc.aqualock.bukkit.display.button.CloseButton;
import com.almuramc.aqualock.bukkit.display.button.UnlockButton;
import com.almuramc.aqualock.bukkit.display.checkbox.EveryoneCheckbox;
import com.almuramc.aqualock.bukkit.display.field.CloseTimerField;
import com.almuramc.aqualock.bukkit.display.field.CoOwnerField;
import com.almuramc.aqualock.bukkit.display.field.DamageField;
import com.almuramc.aqualock.bukkit.display.field.OwnerField;
import com.almuramc.aqualock.bukkit.display.field.PasswordField;
import com.almuramc.aqualock.bukkit.display.field.UseCostField;
import com.almuramc.aqualock.bukkit.display.field.UserField;
import com.almuramc.aqualock.bukkit.display.label.CloseTimerLabel;
import com.almuramc.aqualock.bukkit.display.label.CoOwnerLabel;
import com.almuramc.aqualock.bukkit.display.label.CostToUseValueLabel;
import com.almuramc.aqualock.bukkit.display.label.CreateCostLabel;
import com.almuramc.aqualock.bukkit.display.label.CreateCostValueLabel;
import com.almuramc.aqualock.bukkit.display.label.DamageLabel;
import com.almuramc.aqualock.bukkit.display.label.OwnerLabel;
import com.almuramc.aqualock.bukkit.display.label.PasswordLabel;
import com.almuramc.aqualock.bukkit.display.label.RealOwnersLabel;
import com.almuramc.aqualock.bukkit.display.label.UseCostLabel;
import com.almuramc.aqualock.bukkit.display.label.UserLabel;
import com.almuramc.aqualock.bukkit.lock.BukkitLock;
import com.almuramc.aqualock.bukkit.lock.DoorBukkitLock;
import com.almuramc.aqualock.bukkit.util.LockUtil;
import com.almuramc.bolt.lock.Lock;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericCheckBox;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.gui.WidgetAnchor;

import org.bukkit.Location;

public class AquaPass extends GenericPopup {
	private final AqualockPlugin plugin;
	//Widgets
	private final GenericButton unlockButton, closeButton;
	
	private final GenericLabel costToUseLabel, costToUseOutputLabel, passwordLabel, ownerLabel, realOwnersOutputLabel;
	private final GenericTextField passwordField;
	private final GenericTexture borderTexture, aquaPhoto;
	//Geo
	private Location location;

	public AquaPass(AqualockPlugin plugin) {
		this.plugin = plugin;
		borderTexture = new GenericTexture("http://www.almuramc.com/images/playerplus.png");
		borderTexture
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setPriority(RenderPriority.High)
				.setWidth(200)
				.setHeight(100)
				.shiftXPos(-105)
				.shiftYPos(-60);
		aquaPhoto = new GenericTexture("http://www.almuramc.com/images/aqualock.png");
		aquaPhoto
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setPriority(RenderPriority.Normal)
				.setWidth(60)
				.setHeight(60)
				.shiftXPos(-90)
				.shiftYPos(-55);
		closeButton = new CloseButton(plugin);
		closeButton
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(40)
				.shiftXPos(142)
				.shiftYPos(87);		
		unlockButton = new UnlockButton(plugin);
		unlockButton
				.setAuto(true)
				.setEnabled(false)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(40)
				.shiftXPos(40)
				.shiftYPos(87);	
		costToUseLabel = new UseCostLabel("Cost to use:");
		costToUseLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(10)
				.setWidth(40)
				.shiftXPos(-132)
				.shiftYPos(43);
		costToUseOutputLabel = new CostToUseValueLabel("");
		costToUseOutputLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(10)
				.setWidth(40)
				.shiftXPos(-68)
				.shiftYPos(0);
		passwordField = new PasswordField();
		passwordField
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(14)
				.setWidth(107)
				.shiftXPos(70)
				.shiftYPos(-49);
		passwordLabel = new PasswordLabel("Password:");
		passwordLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(8)
				.setWidth(40)
				.shiftXPos(15)
				.shiftYPos(-45);
		realOwnersOutputLabel = new RealOwnersLabel("");
		realOwnersOutputLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(10)
				.setWidth(40)
				.shiftXPos(-68)
				.shiftYPos(0);
		ownerLabel = new OwnerLabel("Owner:");
		ownerLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(10)
				.setWidth(40)
				.shiftXPos(15)
				.shiftYPos(-65);
		attachWidgets(plugin, borderTexture, aquaPhoto, closeButton, unlockButton, realOwnersOutputLabel, costToUseLabel, costToUseOutputLabel, passwordField, passwordLabel, ownerLabel);
		passwordField.setFocus(true);
		passwordField.setTabIndex(0);
		passwordField.setMaximumCharacters(15);
		passwordField.setMaximumLines(1);
		passwordField.setPasswordField(true);
		this.setTransparent(true);
	}

	/**
	 * Populates the panel with information from the backend
	 */
	public void populate(Lock lock) {		
		
		realOwnersOutputLabel.setText(lock.getOwner());		
		this.setDirty(true);
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
