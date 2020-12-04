package net.corruptmc.nocraftplus.command;

import net.corruptmc.nocraftplus.util.Lang;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdHelp implements CommandInterface
{
    String title;

    public CmdHelp()
    {
        this.title = Lang.TITLE.toString();
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args)
    {
        msg("&e=======︳&6&lNCP&r&e︳=======", sender);
        msg("&8Add item filter: &f/ncp add <item>", sender);
        msg("&8Remove item filter: &f/ncp remove <item>", sender);
        msg("&8List item filters: &f/ncp list", sender);
        msg("&8Reload filters: &f/ncp reload", sender);
        msg("&8Open GUI: &f/ncp gui", sender);
        msg("&e=========================", sender);
        return true;
    }

    private void msg(String message, CommandSender sender)
    {
        message = ChatColor.translateAlternateColorCodes('&', message);
        sender.sendMessage(message);
    }
}
