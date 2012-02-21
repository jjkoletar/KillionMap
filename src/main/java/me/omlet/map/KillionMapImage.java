package me.omlet.map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import me.omlet.killionmap.KMConfig;
import me.omlet.killionmap.KillionMap;
import me.omlet.util.MiscUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class KillionMapImage {
    
    private MapView mapView = null;
    private BufferedImage image = null;
    
    public static KillionMap plugin;
    public FileConfiguration config;
    
    private static final String CACHED_FILE_FORMAT = "png";
    public static final String IMAGE_FILE = "imagefile";
    
    private static Map<Short,KillionMapImage> allMapViews = new HashMap<Short, KillionMapImage>();
    
    public void setupMap(short id, String str) {
        
        KillionMapRenderer renderer = new KillionMapRenderer(loadImage(str));
        mapView = Bukkit.getServer().getMap(id);
        
        for (MapRenderer r : mapView.getRenderers()) {
            //previousRenderers.add(r);
            mapView.removeRenderer(r);
        }
        
        mapView.addRenderer(renderer);
        
        allMapViews.put(mapView.getId(), this);
        
    }
    
    private BufferedImage loadImage(String file) {
        image = null;
        config = plugin.getConfig();
        
        try {
            URL url = KillionMap.makeImageURL(file);
            File cached = getCachedFile(url);
            BufferedImage resizedImage;
            if (cached != null && cached.canRead()) {
                resizedImage = ImageIO.read(cached);
            } else {
                BufferedImage orig = ImageIO.read(url);
                resizedImage = MapPalette.resizeImage(orig);
                if (cached != null) {
                    ImageIO.write(resizedImage, CACHED_FILE_FORMAT, cached);
                    MiscUtil.log(Level.INFO, "Cached image " + url + " as " + cached);
                }
            }
            return resizedImage;
        } catch (MalformedURLException e) {
            MiscUtil.log(Level.WARNING, "malformed image URL for map view " + ": " + e.getMessage());
        } catch (IOException e) {
            MiscUtil.log(Level.WARNING, "cannot load image URL for map view " + ": " + e.getMessage());
        }
        return null;
    }
    
    private static File getCachedFile(URL url) {
        byte[] bytes = url.toString().getBytes();
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] d = md.digest(bytes);
            BigInteger i = new BigInteger(d);
            return new File(KMConfig.getImgCacheFolder(), String.format("%1$032X", i) + "." + CACHED_FILE_FORMAT);
        } catch (NoSuchAlgorithmException e) {
            MiscUtil.log(Level.WARNING, "Can't get MD5 MessageDigest algorithm - image caching is unavailable!");
            return null;
        }
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
}
