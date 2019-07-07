package com.MinecraftStory;

import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.EventPriority;

public class Death implements Listener {

	public Kits plugin;
	public Death(Kits Kits) {
		this.plugin = Kits;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onDeath(PlayerDeathEvent e) throws IOException {
		Player p = e.getEntity();
		if(p.hasPermission("minecraftstory.kits.deathinv")) {
			InventoryNBTSer.setInv(p, plugin.folder);
			e.getDrops().clear();
		}
		if(p.hasPermission("minecraftstory.kits.deathexp")) {
			e.setKeepLevel(true);
			e.setDroppedExp(0);
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) throws IOException {
		Player p = e.getPlayer();
		if(p.hasPermission("minecraftstory.kits.deathinv")) {
			InventoryNBTSer.getInv(p, plugin.folder);
		}
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) throws IOException {
		Player p = e.getPlayer();
		if(!p.hasPlayedBefore()) {
			if(e.getPlayer().hasPermission("minecraftstory.kits.starter")) {
				InventoryNBTSer.getKit(p, plugin.folder, "starter", true);
			}
		}
	}
}
