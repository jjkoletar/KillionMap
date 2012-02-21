package me.omlet.killionmap.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.omlet.killionmap.KillionMap;
import me.omlet.killionmap.map.KillionMapImage;
import me.omlet.killionmap.map.KillionMapRenderer;
import me.omlet.killionmap.util.MiscUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

/**
 *
 * @author NickGaming
 */
public class MapRemove extends MapCommand {
    
        KillionMapRenderer mapRenderer = null;
        KillionMapImage mapImage = new KillionMapImage();
        private MapView mapView = null;
        private static HashMap<Short, KillionMapImage> views = new HashMap<Short, KillionMapImage>();
    
    public MapRemove(KillionMap plugin, String[] args, Player player) {
        super(plugin, args, player);
    }
    
    public KillionMapRenderer getMapRenderer() {
        return mapRenderer;
    }
    
    @Override
    public Boolean process() {
        
       short id = player.getItemInHand().getType() == Material.MAP ? player.getItemInHand().getDurability() : -1;
       mapView = Bukkit.getServer().getMap(id);
       
       if (id != -1) {
            if (mapView != null) {
                MiscUtil.errorMessage(player, mapView + " has been removed!");
            }
            
            else {
                MiscUtil.errorMessage(player, "There is nothing to remove!");
            }
       }
       
       else {
           MiscUtil.errorMessage(player, "You must be holding a map!");
       }
       return true;
    }
}
