package com.almuramc.aqualock.bukkit.display.button;

import com.almuramc.aqualock.bukkit.AqualockPlugin;
import com.almuramc.aqualock.bukkit.display.AquaPanel;
import com.almuramc.bolt.lock.Lock;
import org.bukkit.Location;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

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
        final Lock lock = plugin.getRegistry().getLock(location.getWorld().getUID(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        plugin.getRegistry().removeLock(lock);
        plugin.getBackend().removeLock(lock);
    }
}
