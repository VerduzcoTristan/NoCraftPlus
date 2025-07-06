package com.devmclovin.nocraftplus.command;

import com.devmclovin.nocraftplus.NoCraftPlugin;
import com.devmclovin.nocraftplus.util.Lang;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class CommandHandler implements CommandExecutor {

    private NoCraftPlugin plugin;

    public CommandHandler(NoCraftPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("nocraftplus.command")) {
            sender.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMISSION.toString());
            return true;
        }
        if (args.length == 0) {
            msg("&e=======︳&6&lNCP&r&e︳=======", sender);
            msg("&8Filter modification available in config.yml", sender);
            msg("&8Reload config: &f/ncp reload", sender);
            msg("&e=========================", sender);
        }

        if(args[0].equalsIgnoreCase("reload")){
            plugin.reloadConfig();
            plugin.loadFilters();
            Lang.loadLang(plugin);

            sender.sendMessage(Lang.TITLE.toString() + Lang.FILTERS_RELOADED.toString());
        }

        sender.sendMessage(Lang.TITLE.toString() + Lang.INVALID_SUBCOMMAND.toString().replaceAll("%subcmd%", args[0]));

        return true;
    }


    private void msg(String message, CommandSender sender) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        sender.sendMessage(message);
    }

}
