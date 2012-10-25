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

import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericCheckBox;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;

public class AquaPanel extends GenericPopup {
	public AquaPanel(AqualockPlugin plugin) {
		final GenericTexture borderTexture = new GenericTexture("http://www.almuramc.com/images/playerplus.png");
		borderTexture
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setPriority(RenderPriority.High)
				.setWidth(626)
				.setHeight(240)
				.shiftXPos(-220)
				.shiftYPos(-128);
		final GenericButton closeButton = new CloseButton(plugin);
		closeButton
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(40)
				.shiftXPos(142)
				.shiftYPos(87);
		final GenericButton applyButton = new ApplyButton(plugin);
		applyButton
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(40)
				.shiftXPos(90)
				.shiftYPos(87);
		this.setTransparent(true);
		final GenericTextField usersBox = new GenericTextField();
		usersBox
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(165)
				.shiftXPos(15)
				.shiftYPos(30);
		final GenericLabel usersLabel = new GenericLabel("Users:");
		usersLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(40)
				.shiftXPos(15)
				.shiftYPos(13);
		final GenericTextField coownersBox = new GenericTextField();
		coownersBox
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(165)
				.shiftXPos(15)
				.shiftYPos(-13);
		final GenericLabel coownersLabel = new GenericLabel("Co-Owners:");
		coownersLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(40)
				.shiftXPos(15)
				.shiftYPos(-29);
		final GenericLabel costToUseOutputLabel = new GenericLabel("Test:");
		costToUseOutputLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(40)
				.shiftXPos(-70)
				.shiftYPos(35);
		final GenericLabel costToUseLabel = new GenericLabel("Cost to use:");
		costToUseLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(40)
				.shiftXPos(-132)
				.shiftYPos(35);
		final GenericLabel damageOnFailOutputLabel = new GenericLabel("Test2:");
		damageOnFailOutputLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(40)
				.shiftXPos(-70)
				.shiftYPos(13);
		final GenericLabel damageOnFailLabel = new GenericLabel("Damage on Fail:");
		damageOnFailLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(40)
				.shiftXPos(-146)
				.shiftYPos(13);
		final GenericLabel costToCreateOutputLabel = new GenericLabel("Test3:");
		costToCreateOutputLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(40)
				.shiftXPos(-70)
				.shiftYPos(-9);
		final GenericLabel costToCreateLabel = new GenericLabel("Cost to create:");
		costToCreateLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(40)
				.shiftXPos(-148)
				.shiftYPos(-9);
		final GenericCheckBox everyoneCheckbox = new EveryoneCheckbox();
		everyoneCheckbox
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(40)
				.shiftXPos(-148)
				.shiftYPos(-45);
		final GenericTextField passwordBox = new GenericTextField();
		passwordBox
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(107)
				.shiftXPos(70)
				.shiftYPos(-57);
		final GenericLabel passwordLabel = new GenericLabel("Password:");
		passwordLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(40)
				.shiftXPos(15)
				.shiftYPos(-50);
		final GenericTextField ownerBox = new GenericTextField();
		ownerBox
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(107)
				.shiftXPos(70)
				.shiftYPos(-80);
		final GenericLabel ownerLabel = new GenericLabel("Owner:");
		ownerLabel
				.setAuto(true)
				.setAnchor(WidgetAnchor.CENTER_CENTER)
				.setHeight(18)
				.setWidth(40)
				.shiftXPos(15)
				.shiftYPos(-74);
		attachWidgets(plugin, borderTexture, closeButton, applyButton, usersBox, usersLabel, coownersBox, coownersLabel,
				costToUseOutputLabel, costToUseLabel, damageOnFailOutputLabel, damageOnFailLabel, costToCreateOutputLabel,
				costToCreateLabel, everyoneCheckbox, passwordBox, passwordLabel, ownerBox, ownerLabel);
	}
}
