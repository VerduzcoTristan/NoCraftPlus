package net.corruptmc.nocraftplus.listeners;

import net.corruptmc.nocraftplus.NoCraftPlugin;
import net.corruptmc.nocraftplus.events.BlockedCraftingEvent;
import net.corruptmc.nocraftplus.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.InventoryView;

public class CraftListener implements Listener
{
    private NoCraftPlugin plugin;
    private boolean alert;

    public CraftListener(NoCraftPlugin plugin)
    {
        this.plugin = plugin;
        this.alert = plugin.getConfig().getBoolean("enable_alert");
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event)
    {
        //Check if the current recipe exists
        if (event.getRecipe() != null)
        {
            InventoryView view = event.getView();
            Player player = (Player) view.getPlayer();

            Material material = event.getRecipe().getResult().getType();
            String name = material.name();

            //Check whether or not the current recipe is disabled
            if (plugin.isBlocked(material))
            { //recipe is disabled
                //Check whether or not the player is allowed to use the current recipe
                if (!player.hasPermission("nocraftplus.bypass." + name.toLowerCase()) && !player.hasPermission("nocraftplus.bypass.*"))
                {
                    //Run custom event for API
                    BlockedCraftingEvent craftEvent = new BlockedCraftingEvent(event);
                    Bukkit.getServer().getPluginManager().callEvent(craftEvent);

                    //Check if an external plugin cancelled the event
                    if (!craftEvent.isCancelled())
                    {//event was not cancelled
                        event.getInventory().setResult(null);

                        if (alert)
                            player.sendMessage(Lang.TITLE.toString() +
                                    Lang.CRAFTING_DISABLED.toString().replaceAll("%item%", name));
                    }
                }
            }
        }
    }

}
