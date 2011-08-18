package me.clannoobz.punisher;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.ChatColor;
public class punisherblocklistener extends BlockListener {
	private final punishermain plugin;
	public punisherblocklistener(punishermain instance) 
	{
		plugin = instance;
	}
	Logger log = Logger.getLogger("Minecraft");
	@Override
	public void onBlockPlace(BlockPlaceEvent event) {
		Block block = event.getBlockPlaced();
		Player player = event.getPlayer();
		Server server = player.getServer();
		{
			
			if (!plugin.permissionHandler.has(player, "punisher.blockplace.exempt")) {
			{
				if	(block.getType() == Material.matchMaterial("46")) {
				String playername = player.getName();
				server.broadcastMessage(ChatColor.RED + "[Punisher] " + playername + " has placed " + block.getType() + " (" + block.getTypeId() + ")" + " in world: " + block.getWorld().getName() + " at " + block.getX() + "," + block.getY() + "," + block.getZ() + ".");
				log.warning("[Punisher] " + playername + " has placed " + block.getType() + " (" + block.getTypeId() + ")" + " in world: " + block.getWorld().getName() + " at " + block.getX() + "," + block.getY() + "," + block.getZ() + ".");
				if (plugin.kickonplace.equals(true));
				{
					player.kickPlayer(plugin.kickmessage);
				}
				block.setTypeId(0);
				player.getWorld().strikeLightning(player.getLocation());
				}	
			}
		    }
		
}
}
}