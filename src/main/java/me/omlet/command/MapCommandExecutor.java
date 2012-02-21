/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.omlet.command;

import java.util.logging.Level;
import me.omlet.killionmap.KillionMap;
import me.omlet.util.MiscUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author NickGaming
 */
public class MapCommandExecutor implements CommandExecutor {
    
        protected KillionMap plugin;
	private MapCommand cmd;
	
	public MapCommandExecutor(KillionMap plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandlabel, final String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			// Valid command format
			if(args.length >= 1) {
				
				// Map add
				if(args[0].equalsIgnoreCase("write")) {
					cmd = new MapWrite(plugin, args, player);
				}
				
				// casino addmanaged
				else if(args[0].equalsIgnoreCase("give")) {
					cmd = new MapGive(plugin, args, player);
				}
				
				// invalid command
				else {
					return false;
				}
				
			}
			
			// no arguments
			else {
				cmd = new MapHelp(plugin, args, player);
			}
			
			return cmd.process();
		}
		
		// No commands by console
		else {
			MiscUtil.log(Level.INFO, "You cannot use this command within the console!");
		}
		return true;
	}

}

