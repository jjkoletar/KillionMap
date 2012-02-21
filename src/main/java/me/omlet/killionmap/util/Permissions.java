package me.omlet.killionmap.util;

import me.omlet.killionmap.KillionMap;
import org.bukkit.entity.Player;

/**
 *
 * @author NickGaming
 */
public class Permissions {
    
    protected KillionMap plugin;
    
    private String use = "killionmap.use";
    
    public Permissions(KillionMap plugin) {
        this.plugin = plugin;
    }
    
    public Boolean canUse(Player player) {
        if(player.isOp() || player.hasPermission(use) ) {
            return true;
        }
        return false;
    }
}
