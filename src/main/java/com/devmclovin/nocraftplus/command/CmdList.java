package net.corruptmc.nocraftplus.command;

import net.corruptmc.nocraftplus.NoCraftPlugin;
import net.corruptmc.nocraftplus.util.Lang;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CmdList implements CommandInterface
{
    private NoCraftPlugin plugin;

    public CmdList(NoCraftPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args)
    {
        String title = Lang.TITLE.toString();

        if (args.length != 1)
        {
            sender.sendMessage(title + Lang.USAGE.toString().replaceAll("%cmd%", "/ncp list"));
        } else
        {
            List<String> filters = plugin.getFilters();
            if (filters.size() == 0)
            {
                sender.sendMessage(Lang.NO_FILTERS.toString());
            } else
            {
                sender.sendMessage(Lang.CURRENT_FILTERS.toString().replaceAll("%filters%",
                        String.join(", ", filters)));
            }
        }
        return true;
    }
}
