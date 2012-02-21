/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.omlet.command;

import me.omlet.killionmap.KillionMap;
import me.omlet.map.KillionMapImage;
import me.omlet.util.MiscUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 *
 * @author NickGaming
 */
public class MapWrite extends MapCommand {
    
    public MapWrite(KillionMap plugin, String[] args, Player player) {
        super(plugin, args, player);
    }
    
    KillionMapImage mapImage = new KillionMapImage();
    
    @Override
    public Boolean process() {
        
        if (args.length == 2) {
                
            if (args[1].startsWith("http:") && args[1].endsWith(".png")) {
                
                short id = player.getItemInHand().getType() == Material.MAP ? player.getItemInHand().getDurability() : -1;
                if (id != -1) {
                    mapImage.setupMap(id, args[1]);
                    MiscUtil.alertMessage(player, "Image has been set on map_" + id);
                    return true;
                } else {
                   MiscUtil.errorMessage(player, "You must be holding a map!"); 
                   return true;
                }
            } else {
                MiscUtil.errorMessage(player, "Incorrect url syntax!");
                return true;
            }
        } 
        
        //Incorrect command format
        else {
            MiscUtil.generalMessage(player, "Usage:");
            MiscUtil.generalMessage(player, "/km write <url>");
        }
        return true;
    }
}
