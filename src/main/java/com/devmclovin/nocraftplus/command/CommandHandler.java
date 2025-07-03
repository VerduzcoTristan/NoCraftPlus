package net.corruptmc.nocraftplus.command;

import net.corruptmc.nocraftplus.util.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CommandHandler implements CommandExecutor
{
    private static HashMap<String, CommandInterface> commands = new HashMap<String, CommandInterface>();

    public void register(String name, CommandInterface cmd)
    {
        commands.put(name, cmd);
    }

    public boolean exists(String name)
    {
        return commands.containsKey(name);
    }

    public CommandInterface getExecutor(String name)
    {
        return commands.get(name);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!sender.hasPermission("nocraftplus.command"))
        {
            sender.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMISSION.toString());
            return true;
        }
        if (args.length == 0)
        {
            getExecutor("nocraftplus").onCommand(sender, args);
            return true;
        }

        if (exists(args[0]))
        {
            if (!sender.hasPermission("nocraftplus.command." + args[0].toLowerCase())){
                sender.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMISSION.toString());
            } else {
                getExecutor(args[0]).onCommand(sender, args);
            }
        } else
        {
            sender.sendMessage(Lang.TITLE.toString() + Lang.INVALID_SUBCOMMAND.toString().replaceAll("%subcmd%", args[0]));
        }

        return true;
    }
}
