package me.omlet.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author NickGaming
 */
public class MiscUtil {
	private static String prevColour = ChatColor.WHITE.toString();
	protected static final Logger logger = Logger.getLogger("Minecraft");
	protected static final String messageFormat = "[KillionMap]: %s";

	public static void errorMessage(Player player, String string) {
		prevColour = ChatColor.RED.toString();
		message(player, string, ChatColor.RED, Level.WARNING);
	}

	public static void statusMessage(Player player, String string) {
		prevColour = ChatColor.AQUA.toString();
		message(player, string, ChatColor.AQUA, Level.INFO);
	}

	public static void alertMessage(Player player, String string) {
		prevColour = ChatColor.YELLOW.toString();
		message(player, string, ChatColor.YELLOW, Level.INFO);
	}

	public static void generalMessage(Player player, String string) {
		prevColour = ChatColor.WHITE.toString();
		message(player, string, Level.INFO);
	}

	public static void broadcastMessage(String string) {
		prevColour = ChatColor.YELLOW.toString();
		Bukkit.getServer().broadcastMessage(parseColourSpec("&4::&-" + string)); //$NON-NLS-1$
	}
	
	public static void rawMessage(Player player, String string) {
		for (String line : string.split("\\n")) { //$NON-NLS-1$
			if (player != null) {
				player.sendMessage(line);
			} else {
				log(Level.INFO, ChatColor.stripColor(line));
			}
		}
	}

	private static void message(Player player, String string, Level level) {
		for (String line : string.split("\\n")) { //$NON-NLS-1$
			if (player != null) {
				player.sendMessage(parseColourSpec(line));
			} else {
				log(level, ChatColor.stripColor(parseColourSpec(line)));
			}
		}
	}

	private static void message(Player player, String string, ChatColor colour, Level level) {
		for (String line : string.split("\\n")) { //$NON-NLS-1$
			if (player != null) {
				player.sendMessage(colour + parseColourSpec(line));
			} else {
				log(level, ChatColor.stripColor(parseColourSpec(line)));
			}
		}
	}

	public static String parseColourSpec(String spec) {
		String res = spec.replaceAll("&(?<!&&)(?=[0-9a-fA-F])", "\u00A7"); 
		return res.replace("&-", prevColour).replace("&&", "&");
	}

	public static void log(String message) {
		if (message != null) {
			logger.log(Level.INFO, String.format(messageFormat, message));
		}
	}

	public static void log(Level level, String message) {
		if (level == null) {
			level = Level.INFO;
		}
		if (message != null) {
			logger.log(level, String.format(messageFormat, message));
		}
	}

	public static void log(Level level, String message, Exception err) {
		if (err == null) {
			log(level, message);
		} else {
			logger.log(level, String.format(messageFormat, message == null ? err.getMessage() : message), err);
		}
	}

	public static String unParseColourSpec(String spec) {
		return spec.replaceAll("\u00A7", "&");
	}


	public static String deColourise(String s) {
		return s.replaceAll("\u00A7.", "");
	}


}