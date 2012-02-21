package me.omlet.killionmap;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import me.omlet.command.MapCommandExecutor;
import me.omlet.map.KillionMapImage;
import me.omlet.listener.KillionMapWorldListener;
import me.omlet.map.KillionMapRenderer;
import me.omlet.util.MiscUtil;
import me.omlet.util.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Omlet
 */
public class KillionMap extends JavaPlugin {
    
    private FileConfiguration config;
    
    public Permissions permission = new Permissions(this);
    
    private MapCommandExecutor commandExecutor;
    
    private static KillionMap instance = null;
    
    public static KillionMap getInstance() {
        return instance;
    }
    
    private static void setInstance(KillionMap plugin) {
        instance = plugin;
    }
    
    public void onEnable() {
        setInstance(this);
        
        KMConfig.init(this);
        
        Bukkit.getServer().getPluginManager().registerEvents(new KillionMapWorldListener(), this);
        
        commandExecutor = new MapCommandExecutor(this);
        getCommand("km").setExecutor(commandExecutor);
        
        MiscUtil.log(Level.INFO, getDescription().getName() + " version " + getDescription().getVersion() + " (by Omlet) is enabled!" );
    }
    
    public void onDisable() {
        MiscUtil.log(Level.INFO, getDescription().getName() + " version " + getDescription().getVersion() + " (by Omlet) is disabled!" );
    }
    
    public static URL makeImageURL(String path) throws MalformedURLException {
        if (path == null || path.isEmpty()) {
            throw new MalformedURLException("file must be non-null and not an empty string");
        }
        
        return makeImageURL(KMConfig.getConfig().getString("sms.resource_base_url"), path);
    }
    
    public static URL makeImageURL(String base, String path) throws MalformedURLException {
        if (path == null || path.isEmpty()) {
            throw new MalformedURLException("file must be non-null and not an empty string");
        }
        
        if ((base == null || base.isEmpty()) && !path.startsWith("http:")) {
            throw new MalformedURLException("base URL must be set (use /sms setcfg resource_base_url ...");
        }
        
        if (path.startsWith("http:") || base == null) {
            return new URL(path);
        } else {
            return new URL(new URL(base), path);
        }
    }
    
}
