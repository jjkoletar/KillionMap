package me.omlet.command;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import me.omlet.killionmap.KMConfig;
import me.omlet.killionmap.KillionMap;
import me.omlet.map.KillionMapRenderer;
import me.omlet.util.MiscUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class MapWriteCommand implements CommandExecutor {
    
    private MapView mapView = null;
    private KillionMapRenderer mapRenderer = null;
    private BufferedImage image = null;
    private List<MapRenderer> previousRenderers = new ArrayList<MapRenderer>();
    
    public static KillionMap plugin;
    
    private static final String CACHED_FILE_FORMAT = "png";
    public static final String IMAGE_FILE = "imagefile";
    
    private static Map<Short,MapWriteCommand> allMapViews = new HashMap<Short, MapWriteCommand>();
    
    public MapWriteCommand (KillionMap instance) {
        plugin = instance;
    }
    
    public void setMapId(short id) {
        mapView = Bukkit.getServer().getMap(id);
        
        for (MapRenderer r : mapView.getRenderers()) {
            previousRenderers.add(r);
            mapView.removeRenderer(r);
        }
        
        mapView.addRenderer(getMapRenderer());
        
        allMapViews.put(mapView.getId(), this);
        
        loadBackgroundImage();
    }
    
    private void loadBackgroundImage() {
        image = null;
        
        String file = getAttributeAsString(IMAGE_FILE, "");
        if (file.isEmpty()) {
            return;
        }
        
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
            image = resizedImage;
        } catch (MalformedURLException e) {
            MiscUtil.log(Level.WARNING, "malformed image URL for map view " + getName() + ": " + e.getMessage());
        } catch (IOException e) {
            MiscUtil.log(Level.WARNING, "cannot load image URL for map view " + getName() + ": " + e.getMessage());
        }
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
    
    public KillionMapRenderer getMapRenderer() {
        return mapRenderer;
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        String str;     
        if(args.length >= 1) {
            if (cmd.getName().equalsIgnoreCase("km")) {
                str = args[0];
                
                if(str.equalsIgnoreCase("write")) {
                    
                    sender.sendMessage("Map" + " 's image has been sucessfully changed!");
                    return true;
                }
            }
        }
        return false;
    }
}
