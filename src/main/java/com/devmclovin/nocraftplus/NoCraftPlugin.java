package com.devmclovin.nocraftplus;

import com.devmclovin.nocraftplus.command.*;
import com.devmclovin.nocraftplus.listeners.CraftListener;
import com.devmclovin.nocraftplus.util.Lang;
//import com.devmclovin.nocraftplus.util.Metrics;
import com.devmclovin.nocraftplus.util.UpdateChecker;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class NoCraftPlugin extends JavaPlugin
{
    private Logger log;

    private List<String> recipeFilters;
    public String recipeMode; // Options: blacklist, whitelist, disabled

    private boolean alert;

    private NoCraftPlugin plugin;

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

//        if (getConfig().getBoolean("enable_metrics"))
//            Metrics.loadMetrics(plugin);
    }

    @Override
    public void onDisable()
    {
        plugin = null;
    }

    public void loadFilters()
    {
        FileConfiguration config = getConfig();
        recipeFilters = config.getStringList("recipe.list");
        recipeFilters.replaceAll(String::toLowerCase);
        this.recipeMode = config.getString("recipe.mode").toLowerCase();
    }

    public void registerListeners()
    {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new CraftListener(plugin), plugin);
    }

    public void registerCommands()
    {
        plugin.getCommand("nocraftplus").setExecutor(new CommandHandler(plugin));
    }

    public boolean alertPlayer(){
        return alert;
    }

    public boolean recipeBlocked(String recipe) {
        if (recipeMode.equals("disabled")) return true;

        recipe = recipe.toLowerCase();
        boolean listHas;

        for (String s : recipeFilters) {
            if (recipe.contains(s)){
                listHas = true;
            }
        }

        switch (recipeMode) {
            case "blacklist":
                return true;
            case "whitelist":
                return false;
            default:
                throw new Error("Invalid recipe mode. Options: Blacklist, Whitelist, Disabled");
        }
    }

//    /*
//    API Methods
//     */
//
//    //Get current filter mode
//    //Returns "WHITELIST" or "BLACKLIST"
//    public String getMode()
//    {
//        return this.blacklist ? "BLACKLIST" : "WHITELIST";
//    }
//
//    //Get all current crafting list
//    public List<String> getFilters()
//    {
//        return this.filters;
//    }
//
//    //Block an item from crafting
//    public void addFilter(Material material)
//    {
//        FileConfiguration config = getConfig();
//        List<String> temp = config.getStringList("disabled_items");
//        temp.add(material.toString());
//        config.set("disabled_items", temp);
//        saveConfig();
//        this.filters.add(material.toString());
//    }
//
//    //Remove a blocked item
//    public void removeFilter(Material material)
//    {
//        FileConfiguration config = getConfig();
//        List<String> temp = config.getStringList("disabled_items");
//        temp.remove(material.toString());
//        config.set("disabled_items", temp);
//        saveConfig();
//        this.filters.remove(material.toString());
//    }
//
//    //Check if a material is blocked
//    public boolean isBlocked(Material type)
//    {
//        boolean hasMat = this.filters.contains(type.toString());
//        return this.blacklist && hasMat || !this.blacklist && !hasMat;
//    }
//
//    //Toggle blacklist mode
//    public void toggleBlacklist()
//    {
//        this.blacklist = !this.blacklist;
//        getConfig().set("blacklist", this.blacklist);
//        saveConfig();
//    }
//
//    //for API
//    public static NoCraftPlugin getNoCraftPlusPlugin()
//    {
//        return plugin;
//    }
}