package me.omlet.map;

import java.awt.image.BufferedImage;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

/**
 *
 * @author NickGaming
 */
public class KillionMapRenderer extends MapRenderer {
    
    //MapWriteCommand mapView;
    
    // A mapRenderer remembers what image it's supposed to be rendering
    private BufferedImage image;
    
    public KillionMapRenderer(BufferedImage image) {
        this.image = image;
    }
    
    public void render(MapView map, MapCanvas canvas, Player player) {
        if (image != null) {
            canvas.drawImage(0, 0, image);
        }
    }
    
   /* private void drawImage(MapCanvas canvas, BufferedImage image) {
        if (image != null) {
            canvas.drawImage(0, 0, image);
        }
    }*/
}
