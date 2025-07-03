package net.corruptmc.nocraftplus;

import net.corruptmc.nocraftplus.command.*;
import net.corruptmc.nocraftplus.listeners.CraftListener;
import net.corruptmc.nocraftplus.util.Lang;
import net.corruptmc.nocraftplus.util.Metrics;
import net.corruptmc.nocraftplus.util.UpdateChecker;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class NoCraftPlugin extends JavaPlugin
{
    private Logger log;

    private List<String> filters;
    private boolean blacklist;

    private static NoCraftPlugin plugin;

    @Override
    public void onEnable()
    {
        plugin = this;
        this.log = getLogger();

        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists())
        {
            log.info("No config file detected. Creating one now.");
            saveDefaultConfig();
        }

        Lang.loadLang(plugin);
        UpdateChecker.checkForUpdates(plugin);
        loadFilters();
        registerCommands();
        registerListeners();

        if (getConfig().getBoolean("enable_metrics"))
            Metrics.loadMetrics(plugin);
    }

    @Override
    public void onDisable()
    {
        plugin = null;
    }

    public void loadFilters()
    {
        FileConfiguration config = getConfig();
        filters = config.getStringList("disabled_items");
        this.blacklist = config.getBoolean("blacklist");
    }

    public void registerListeners()
    {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new CraftListener(plugin), plugin);
    }

    public void registerCommands()
    {
        CommandHandler handler = new CommandHandler();

        handler.register("nocraftplus", new CmdBase(plugin));

        handler.register("add", new CmdAdd(plugin));
        handler.register("help", new CmdHelp());
        handler.register("list", new CmdList(plugin));
        handler.register("reload", new CmdReload(plugin));
        handler.register("remove", new CmdRemove(plugin));
        handler.register("toggle", new CmdToggle(plugin));

        getCommand("nocraftplus").setExecutor(handler);
        getCommand("nocraftplus").setTabCompleter(new NCPTabCompleter());
    }

    /*
    API Methods
     */

    //Get current filter mode
    //Returns "WHITELIST" or "BLACKLIST"
    public String getMode()
    {
        return this.blacklist ? "BLACKLIST" : "WHITELIST";
    }

    //Get all current crafting list
    public List<String> getFilters()
    {
        return this.filters;
    }

    //Block an item from crafting
    public void addFilter(Material material)
    {
        FileConfiguration config = getConfig();
        List<String> temp = config.getStringList("disabled_items");
        temp.add(material.toString());
        config.set("disabled_items", temp);
        saveConfig();
        this.filters.add(material.toString());
    }

    //Remove a blocked item
    public void removeFilter(Material material)
    {
        FileConfiguration config = getConfig();
        List<String> temp = config.getStringList("disabled_items");
        temp.remove(material.toString());
        config.set("disabled_items", temp);
        saveConfig();
        this.filters.remove(material.toString());
    }

    //Check if a material is blocked
    public boolean isBlocked(Material type)
    {
        boolean hasMat = this.filters.contains(type.toString());
        return this.blacklist && hasMat || !this.blacklist && !hasMat;
    }

    //Toggle blacklist mode
    public void toggleBlacklist()
    {
        this.blacklist = !this.blacklist;
        getConfig().set("blacklist", this.blacklist);
        saveConfig();
    }

    //for API
    public static NoCraftPlugin getNoCraftPlusPlugin()
    {
        return plugin;
    }
}