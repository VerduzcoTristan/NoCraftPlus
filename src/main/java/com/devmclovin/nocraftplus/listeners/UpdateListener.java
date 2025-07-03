package net.corruptmc.nocraftplus.listeners;

import net.corruptmc.nocraftplus.util.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateListener implements Listener
{
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if (player.hasPermission("nocraftplus.updatecheck"))
        {
            player.sendMessage(Lang.TITLE.toString() + Lang.UPDATE_READY.toString());
        }
    }
}
