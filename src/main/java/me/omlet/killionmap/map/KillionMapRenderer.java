package me.omlet.killionmap.map;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import me.omlet.killionmap.util.MiscUtil;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
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
        drawImage(canvas, image);
    }
    
    private void drawImage(MapCanvas canvas, BufferedImage image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
   
        int x0 = (128 / 2) - (width / 2);
        int y0 = (128 / 2) - (height / 2);
   
        int[] pixels = new int[width * height];
        PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
 
        try {
            pg.grabPixels();
            int tel = 0;
            byte color;
            if (pixels.length > 0) {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int c = pixels[tel];
                        int red = (c & 0x00ff0000) >> 16;
                        int green = (c & 0x0000ff00) >> 8;
                        int blue = c & 0x000000ff;
                    
                    // Don't know if this is the right way to check if the pixel is
                    // transparent, but it worked for me. You can uncomment
                    // the below if part to print the first pixels color to the console.
                    //if(x == 0 && y == 0) {
                    //    System.out.print("  c: " + c);
                    //}
                        if (c != -16777216) {
                            color = MapPalette.matchColor(red, green, blue);
                            canvas.setPixel(x0 + x, y0 + y, color);
                        }
                        tel++;
                    }
                }
            }
        } catch (InterruptedException e) {
            
        }
    }
}
