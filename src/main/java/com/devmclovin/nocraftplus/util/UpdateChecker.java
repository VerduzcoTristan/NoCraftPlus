package net.corruptmc.nocraftplus.util;

import net.corruptmc.nocraftplus.NoCraftPlugin;
import net.corruptmc.nocraftplus.listeners.UpdateListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
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
        int version = config.getInt("config_version");
        int temp = version;
        if (version < 3)
        {
            if (!config.isSet("check_for_updates"))
                config.set("check_for_updates", true);
            config.set("config_version", 3);
            version = 3;
        }
        if (version == 3)
        {
            config.set("disable_all", false);
            config.set("config_version", 4);
            version = 4;
        }
        if (version == 4)
        {
            config.set("enable_metrics", true);
            config.set("config_version", 5);
            version = 5;
        }
        if (version == 5)
        {
            config.set("enable-alert", true);
            config.set("config_version", 6);
            version = 6;
        }
        if (version == 6)
        {
            config.set("blacklist", true);
            config.set("disable_all", null);
            config.set("config_version", 7);
            version = 7;
        }
        ncp.saveConfig();
        if (version != temp)
            plugin.getLogger().info("config.yml updated.");
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
