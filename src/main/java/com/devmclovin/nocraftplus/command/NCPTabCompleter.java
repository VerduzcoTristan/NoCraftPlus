package net.corruptmc.nocraftplus.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NCPTabCompleter implements TabCompleter
{
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        List<String> subCommands = Arrays.asList("add", "help", "list", "reload", "remove");
        String input = args[0].toLowerCase();
        if (args.length == 1)
        {
            List<String> completions = new ArrayList<>();
            for (String s : subCommands)
            {
                if (s.startsWith(input))
                {
                    completions.add(s);
                }
            }
            return completions;
        } else if (args.length == 2)
        {
            if ("add".startsWith(input) || "remove".startsWith(input))
            {
                return Arrays.asList("item");
            }
        }
        return null;
    }
}
