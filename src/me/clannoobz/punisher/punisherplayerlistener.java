package me.clannoobz.punisher;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;



public class punisherplayerlistener extends PlayerListener {
	private final punishermain plugin;
	public punisherplayerlistener(punishermain instance)
	{
		plugin = instance;
	}
	public void onPlayerChat(PlayerChatEvent event) {
		Player player = event.getPlayer();
		String msg = event.getMessage();
		if (msg.contains("fuck"))
		{
		if (!plugin.check(player, "punisher.chat.exempt"))
		{
			player.kickPlayer("Do not say that");
		}
		}
	}

}
