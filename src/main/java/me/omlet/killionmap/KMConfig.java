package me.omlet.killionmap;

import java.io.File;
import java.util.logging.Level;
import me.omlet.killionmap.util.MiscUtil;
import org.bukkit.configuration.Configuration;

/**
 *
 * @author NickGaming
 */
public class KMConfig {
    
    private static File pluginDir;
    private static File imgCacheDir;
    
    private static final String imgCacheDirName = "imagecache";
    
    static void init(KillionMap plugin) {
        setupDirectoryStructure();
        initConfigFile();
    }
    
    private static void setupDirectoryStructure() {
        pluginDir = KillionMap.getInstance().getDataFolder();
        
        imgCacheDir = new File(pluginDir, imgCacheDirName);
        
        createDirectory(imgCacheDir);
    }
    
    private static void createDirectory(File dir) {
        if (dir.isDirectory()) {
            return;
        }
        
        if (!dir.mkdir()) {
            MiscUtil.log(Level.WARNING, "Can't make directory " + dir.getName());
        }
    }
    
    private static void initConfigFile() {
        KillionMap.getInstance().saveConfig();
    }
    
    public static File getImgCacheFolder() {
        return imgCacheDir; 
    }
    
    public static Configuration getConfig() {
        return KillionMap.getInstance().getConfig();
    }
}
