package com.devmclovin.nocraftplus.command;

import com.devmclovin.nocraftplus.util.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CmdBase implements CommandInterface
{
    private String version;

    public CmdBase(JavaPlugin plugin)
    {
        this.version = plugin.getDescription().getVersion();
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args)
    {
        String title = Lang.TITLE.toString();
        String message = Lang.PLUGIN_INFO.toString().replaceAll("%ver%", this.version);
        sender.sendMessage(title + message);
        message = Lang.GET_HELP.toString();
        sender.sendMessage(title + message);
        return true;
    }
}
