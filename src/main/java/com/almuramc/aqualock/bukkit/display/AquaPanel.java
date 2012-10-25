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

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.almuramc.aqualock.bukkit.AqualockPlugin;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class AquaPanel extends GenericPopup {
	private final AqualockPlugin plugin;
	private final SpoutPlayer player;
	//final GenericTextField guideField, guideInvisible;
	//final GenericLabel guideName, guideDate, pagelabel;
	final GenericButton close;

	public AquaPanel(AqualockPlugin plugin, SpoutPlayer player) {
		super();
		this.player = player;
		this.plugin = plugin;

		GenericTexture border = new GenericTexture("http://www.almuramc.com/images/playerplus.png");
		border.setAnchor(WidgetAnchor.CENTER_CENTER);
		border.setPriority(RenderPriority.High);
		border.setWidth(626).setHeight(240);
		border.shiftXPos(-220).shiftYPos(-128);

		close = new CloseButton(plugin);
		close.setAuto(true);
		close.setAnchor(WidgetAnchor.CENTER_CENTER);
		close.setHeight(18).setWidth(40);
		close.shiftXPos(142).shiftYPos(87);
	

		this.setTransparent(true);
		attachWidget(plugin, border);
		attachWidget(plugin, close);
		
		player.getMainScreen().attachPopupScreen(this);
	}
}
