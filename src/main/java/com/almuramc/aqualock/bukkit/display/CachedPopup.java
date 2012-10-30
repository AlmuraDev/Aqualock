package com.almuramc.aqualock.bukkit.display;

import org.getspout.spoutapi.gui.*;

public class CachedPopup extends GenericPopup {
    public void clear() {
        for (Widget widget : getAttachedWidgets()) {
            if (widget instanceof GenericTextField) {
                ((GenericTextField) widget).setText("");
            } else if (widget instanceof GenericCheckBox) {
                ((GenericCheckBox) widget).setChecked(false);
            } else if (widget instanceof GenericLabel) {
                ((GenericLabel) widget).setTextColor(new Color("ffffff"));
            }
        }
    }
}
