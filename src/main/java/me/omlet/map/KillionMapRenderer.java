package me.omlet.map;

import java.awt.image.BufferedImage;
import me.omlet.command.MapWriteCommand;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

/**
 *
 * @author NickGaming
 */
public class KillionMapRenderer extends MapRenderer {
    
    MapWriteCommand mapView;
    
    public void render(MapView map, MapCanvas canvas, Player player) {
        if (mapView.isDirty(player.getName())) {
            drawImage(canvas, mapView.getImage());
        }
        
        mapView.setDirty(player.getName(), false);
        player.sendMap(map);
    }
    
    private void drawImage(MapCanvas canvas, BufferedImage image) {
        if (image != null) {
            canvas.drawImage(0, 0, image);
        }
    }
}
