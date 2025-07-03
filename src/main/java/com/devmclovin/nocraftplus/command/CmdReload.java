package com.devmclovin.nocraftplus.command;

import com.devmclovin.nocraftplus.NoCraftPlugin;
import com.devmclovin.nocraftplus.util.Lang;
import org.bukkit.command.CommandSender;

public class CmdReload implements CommandInterface
{
    private NoCraftPlugin plugin;

    public CmdReload(NoCraftPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args)
    {
        if (args.length != 1)
        {
            sender.sendMessage(Lang.TITLE.toString() + Lang.USAGE.toString().replaceAll("%cmd%", "/ncp reload"));
        } else
        {
            plugin.reloadConfig();
            plugin.loadFilters();
            Lang.loadLang(plugin);

            sender.sendMessage(Lang.TITLE.toString() + Lang.FILTERS_RELOADED.toString());
        }
        return true;
    }

}
