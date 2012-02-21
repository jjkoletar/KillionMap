package me.omlet.killionmap.command;

import me.omlet.killionmap.KillionMap;
import me.omlet.killionmap.util.MiscUtil;
import org.bukkit.entity.Player;

/**
 *
 * @author NickGaming
 */
public class MapHelp extends MapCommand {
    
    public MapHelp(KillionMap plugin, String[] args, Player player) {
        super(plugin, args, player);
    }
    
    @Override
    public Boolean process() {
        
        if(plugin.permission.canUse(player)) {
            MiscUtil.generalMessage(player, "/km write - Draws image to a map");
            MiscUtil.generalMessage(player, "/km give - Gives a specified map");
        } 
        
        else {
            MiscUtil.errorMessage(player, "You have no permission to do this!");
        }
        return true;
    }
}
