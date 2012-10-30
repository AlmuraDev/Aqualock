package com.almuramc.aqualock.bukkit.display.button;

import com.almuramc.aqualock.bukkit.AqualockPlugin;
import com.almuramc.aqualock.bukkit.display.AquaPanel;
import com.almuramc.aqualock.bukkit.display.field.PasswordField;
import com.almuramc.aqualock.bukkit.util.LockUtil;
import com.almuramc.bolt.lock.Lock;
import org.bukkit.Location;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.Widget;

public class UnlockButton extends GenericButton {
    private final AqualockPlugin plugin;

    public UnlockButton(AqualockPlugin plugin) {
        super("Unlock");
        this.plugin = plugin;
    }

    @Override
    public void onButtonClick(ButtonClickEvent event) {
        //TODO perms and such...
        final AquaPanel panel = (AquaPanel) getScreen();
        final Location location = panel.getLocation();
        String password = "";
        for (Widget widget : panel.getAttachedWidgets()) {
            if (widget instanceof PasswordField) {
                password = ((PasswordField) widget).getText();
            }
        }
        if (LockUtil.unlock(getScreen().getPlayer().getName(), password, panel.getLocation())) {
            panel.close();
        }
    }
}
