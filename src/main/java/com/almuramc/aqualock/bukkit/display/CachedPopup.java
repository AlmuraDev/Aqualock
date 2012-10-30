package com.almuramc.aqualock.bukkit.display;

import org.getspout.spoutapi.gui.GenericCheckBox;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.Widget;

public class CachedPopup extends GenericPopup {
    public void clear() {
        for (Widget widget : getAttachedWidgets()) {
            if (widget instanceof GenericTextField) {
                ((GenericTextField) widget).setText("");
            } else if (widget instanceof GenericCheckBox) {
                ((GenericCheckBox) widget).setChecked(false);
            }
        }
    }
}
