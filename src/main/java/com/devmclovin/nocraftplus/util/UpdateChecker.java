package com.devmclovin.nocraftplus.util;

import com.devmclovin.nocraftplus.NoCraftPlugin;
import com.devmclovin.nocraftplus.listeners.UpdateListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Consumer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker
{

    private Plugin plugin;
    private int resourceId;
    private NoCraftPlugin ncp;

    public UpdateChecker(Plugin plugin, int resourceId, NoCraftPlugin ncp)
    {
        this.plugin = plugin;
        this.resourceId = resourceId;
        this.ncp = ncp;
        this.updateConfig();
    }

    public void getVersion(final Consumer<String> consumer)
    {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () ->
        {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream))
            {
                if (scanner.hasNext())
                {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception)
            {
                this.plugin.getLogger().info("Cannot look for updates: " + exception.getMessage());
            }
        });
    }

    public void updateConfig()
    {
        FileConfiguration config = ncp.getConfig();
        if (config.contains("config_version")) {
            plugin.getLogger().severe("When updating to NCP verison 3 a complete config reset is required. Backup current config and remove from plugin directory then restart server.");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
            // plugin.getLogger().info("config.yml updated.");
    }
    public static void checkForUpdates(NoCraftPlugin plugin)
    {
        new UpdateChecker(plugin, 79378, plugin).getVersion(version ->
        {
            if (plugin.getDescription().getVersion().equalsIgnoreCase(version))
            {
                plugin.getLogger().info("Up to date!");
            } else
            {
                plugin.getLogger().info("Update available.");

                if (plugin.getConfig().getBoolean("check_for_updates"))
                    plugin.getServer().getPluginManager().registerEvents(new UpdateListener(), plugin);
            }
        });
    }
}
