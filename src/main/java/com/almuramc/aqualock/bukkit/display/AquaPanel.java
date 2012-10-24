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

import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class AquaPanel extends GenericPopup {
	private final AqualockPlugin plugin;
	private final SpoutPlayer player;
	private final GenericTexture gt;
	//private String string1;
	//private int windowtype = 1;
	//private MyComboBox combo1;
	//private ListWidget list1;
	//private GenericButton button1;

	public AquaPanel(AqualockPlugin plugin, SpoutPlayer player) {
		this.plugin = plugin;
		this.player = player;

		final GenericTexture border = new GenericTexture("http://www.almuramc.com/images/playerplus.png");
		border.setAnchor(WidgetAnchor.CENTER_CENTER);
		border.setPriority(RenderPriority.Lowest);
		border.setWidth(420).setHeight(345);
		border.shiftXPos(-205).shiftYPos(-120);

		final GenericLabel label = new GenericLabel();
		label.setText("Aqualock");
		label.setAnchor(WidgetAnchor.CENTER_CENTER);
		label.shiftXPos(-50).shiftYPos(-112);
		label.setScale(1.2F).setWidth(-1).setHeight(-1);

		gt = new GenericTexture();
		gt.setAnchor(WidgetAnchor.CENTER_CENTER);
		gt.setHeight(150).setWidth(150);
		gt.shiftXPos(10).shiftYPos(-90);

		final CloseButton close = new CloseButton(plugin);
		close.setAnchor(WidgetAnchor.CENTER_CENTER);
		close.setHeight(20).setWidth(50);
		close.shiftXPos(150).shiftYPos(95);

		attachWidgets(plugin, border, label, gt, close);
	}
}
