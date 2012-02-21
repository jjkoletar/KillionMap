package me.omlet.command;

import me.omlet.killionmap.KillionMap;
import me.omlet.util.MiscUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;

/**
 *
 * @author NickGaming
 */
public class MapGive extends MapCommand {
    
    public MapGive(KillionMap plugin, String[] args, Player player) {
        super(plugin, args, player);
    }
    
    short mapId;
    
    @Override
    public Boolean process() {
        if (args.length >= 2 && args.length <= 4) {
            int amount = 1;
            if (args.length >= 3) {
                try {
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    MiscUtil.errorMessage(player, "Invalid amount " + args[2]);
                }
            }
            
            Player targetPlayer = player;
            if (args.length >= 4) {
                targetPlayer = Bukkit.getPlayer(args[3]);
            }
            
            try {
                mapId = Short.parseShort(args[1]);
            } catch (NumberFormatException e) {
                MiscUtil.errorMessage(player, "Invalid map ID " + args[1]);
            }
            
            if (amount < 1)
                amount = 1;
            if (amount > 64)
                amount = 64;
            
            if (Bukkit.getServer().getMap(mapId) == null) {
                MapView mv = Bukkit.getServer().createMap(player.getWorld());
                mapId = mv.getId();
            }
            
            ItemStack stack = new ItemStack(Material.MAP, amount);
            stack.setDurability(mapId);
            if (targetPlayer != null) {
                targetPlayer.getInventory().addItem(stack);
            } else {
                MiscUtil.errorMessage(player, "Player doesn't exist!");
                return true;
            }
            targetPlayer.updateInventory();
            String s = amount == 1 ? "" : "s";
            MiscUtil.statusMessage(player, String.format("Gave %d map%s (map_%d) to %s", amount, s, mapId, targetPlayer.getName()));
            if (player != targetPlayer) {
                MiscUtil.statusMessage(targetPlayer, String.format("You received %d map%s of type &6map_%d&-", amount, s, mapId));
            }
        } 
        
        else {
            MiscUtil.generalMessage(player, "Usage:");
            MiscUtil.generalMessage(player, "/km give <id> [<amount>] [<player>]");
        }
        return true;
    }
}
