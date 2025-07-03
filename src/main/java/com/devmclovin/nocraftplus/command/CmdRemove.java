package net.corruptmc.nocraftplus.command;

import net.corruptmc.nocraftplus.NoCraftPlugin;
import net.corruptmc.nocraftplus.util.Lang;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

public class CmdRemove implements CommandInterface
{
    private NoCraftPlugin plugin;

    public CmdRemove(NoCraftPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args)
    {
        String title = Lang.TITLE.toString();

        if (args.length != 2)
        {
            sender.sendMessage(title + Lang.USAGE.toString().replaceAll("%cmd%", "/ncp remove <item>"));
        } else
        {
            Material mat;
            try
            {
                mat = Material.matchMaterial(args[1]);
            } catch (Exception e)
            {
                sender.sendMessage(title + Lang.INVALID_ITEM.toString());
                return true;
            }

            if (!plugin.isBlocked(mat))
            {
                sender.sendMessage(title + Lang.INVALID_FILTER.toString());
            } else
            {
                plugin.removeFilter(mat);
                sender.sendMessage(title + Lang.FILTER_REMOVED.toString().replaceAll("%item%", mat.name()));
            }
        }
        return true;
    }
}
