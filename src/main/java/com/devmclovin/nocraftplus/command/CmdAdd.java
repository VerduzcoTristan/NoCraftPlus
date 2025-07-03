package net.corruptmc.nocraftplus.command;

import net.corruptmc.nocraftplus.NoCraftPlugin;
import net.corruptmc.nocraftplus.util.Lang;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

public class CmdAdd implements CommandInterface
{
    private NoCraftPlugin plugin;

    public CmdAdd(NoCraftPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args)
    {
        String title = Lang.TITLE.toString();

        if (args.length != 2)
        {
            sender.sendMessage(title + Lang.USAGE.toString().replaceAll("%cmd%", "/ncp add <item>"));
        } else
        {
            Material mat = Material.matchMaterial(args[1]);

            if (mat == null)
            {
                sender.sendMessage(title + Lang.INVALID_ITEM.toString());
            } else
            {
                if (plugin.getFilters().contains(mat.name()))
                {
                    sender.sendMessage(title + Lang.FILTER_EXISTS.toString());
                } else
                {
                    plugin.addFilter(mat);
                    sender.sendMessage(title + Lang.FILTER_ADDED.toString().replaceAll("%item%", mat.name()));
                }
            }
        }
        return true;
    }
}
