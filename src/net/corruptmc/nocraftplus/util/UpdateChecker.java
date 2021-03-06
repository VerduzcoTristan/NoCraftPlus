package net.corruptmc.nocraftplus.util;

import net.corruptmc.nocraftplus.NoCraftPlugin;
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
        if (version < 3)
        {
            if (!config.isSet("check_for_updates"))
                config.set("check_for_updates", true);
            config.set("config_version", 3);
        }
        if (version == 3)
        {
            config.set("disable_all", false);
            config.set("config_version", 4);
        }
        if (version == 4)
        {
            config.set("enable_metrics", true);
            config.set("config_version", 5);
        }
        if (version == 5)
        {
            config.set("enable-alert", true);
            config.set("config_version", 6);
        }
        ncp.saveConfig();
    }
}
