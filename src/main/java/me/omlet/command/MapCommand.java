package me.omlet.command;

import me.omlet.killionmap.KillionMap;
import me.omlet.util.MiscUtil;
import org.bukkit.entity.Player;

/**
 *
 * @author NickGaming
 */
public abstract class MapCommand {
	
	public KillionMap plugin;
	public Player player;
	public String[] args;
	
	// Initializes new command
	public MapCommand(KillionMap plugin, String[] args, Player player) {
		
		this.plugin = plugin;
		this.args = args;
		this.player = player;
		
	}
	
	// Processes command, handled by subclasses
	public Boolean process() {
		return false;
	}
	
	// Called when a player is denied permission to a command
	public void noPermission() {
		MiscUtil.errorMessage(player, "You don't have permission!");
	}

}


