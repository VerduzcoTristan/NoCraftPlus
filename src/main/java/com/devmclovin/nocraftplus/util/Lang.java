package net.corruptmc.nocraftplus.util;

import net.corruptmc.nocraftplus.NoCraftPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public enum Lang
{
    //Plugin info
    TITLE("title", "&3&lNCP » "),
    PLUGIN_INFO("plugin-info", "&8Currently running &cNoCraftPlus v%ver%&8."),

    CRAFTING_DISABLED("crafting-disabled", "&8You are not allowed to craft &c%item%&8."),

    GET_HELP("get-help", "&8Use &c/ncp help &8to view available commands."),
    UPDATE_READY("update-ready", "&8NoCraftPlus update available."),

    //command messages
    FILTER_ADDED("filter-added", "&8Successfully added an item filter for: &c%item%&8."),
    FILTER_REMOVED("filter-removed", "&8Successfully removed item filter for: &c%item%&8."),
    NO_FILTERS("no-filters", "&8No filters detected."),
    CURRENT_FILTERS("current-filters", "&8Current filters: &c%filters%"),
    FILTERS_RELOADED("filters-reloaded", "&8Successfully reloaded filters."),
    BLACKLIST_TOGGLE("blacklist-toggle", "&c%mode% &8mode enabled."),

    //Command errors
    FILTER_EXISTS("filter-exists", "&cThat item already has a filter set."),
    INVALID_FILTER("invalid-filter", "&cThat item does not have a filter."),
    INVALID_ITEM("invalid-item", "&cThat item does not exist."),

    //Unspecific errors
    NO_PERMISSION("no-permission", "&cYou do not have permission to use this feature."),
    INVALID_SUBCOMMAND("invalid-subcommand", "&cInvalid subcommand."),
    USAGE("usage", "&8Usage: &c%cmd%");

    private String path;
    private String def;
    private static YamlConfiguration LANG;

    Lang(String path, String start)
    {
        this.path = path;
        this.def = start;
    }

    public static void setFile(YamlConfiguration config)
    {
        LANG = config;
    }

    @Override
    public String toString()
    {
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }

    public String getDefault()
    {
        return this.def;
    }

    public String getPath()
    {
        return this.path;
    }

    public static void loadLang(NoCraftPlugin ncp)
    {
        File lang = new File(ncp.getDataFolder(), "lang.yml");

        YamlConfiguration langConfig = YamlConfiguration.loadConfiguration(lang);
        for (Lang item : Lang.values())
        {
            if (langConfig.getString(item.getPath()) == null)
            {
                langConfig.set(item.getPath(), item.getDefault());
            }
        }
        Lang.setFile(langConfig);
        try
        {
            langConfig.save(lang);
        } catch (IOException e)
        {
            Logger log = ncp.getLogger();
            log.info("Could not save language file.");
            log.info("Disabling plugin.");
            ncp.getServer().getPluginManager().disablePlugin(ncp);
        }
    }

}
