package net.corruptmc.nocraftplus.command;

import net.corruptmc.nocraftplus.NoCraftPlugin;
import net.corruptmc.nocraftplus.util.Lang;
import org.bukkit.command.CommandSender;

public class CmdToggle implements CommandInterface
{
    private final NoCraftPlugin plugin;

    public CmdToggle(NoCraftPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args)
    {
        plugin.toggleBlacklist();
        sender.sendMessage(Lang.TITLE.toString() + Lang.BLACKLIST_TOGGLE.toString()
                .replaceAll("%mode%", plugin.getMode()));
        return true;
    }
}
