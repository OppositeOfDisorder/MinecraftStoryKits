package com.MinecraftStory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MinecraftStoryKits extends JavaPlugin {
	File folder = getDataFolder();
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		getDataFolder().mkdirs();
		folder.mkdir();
		PluginManager pm = getServer().getPluginManager();
		MinecraftStoryDeath d = new MinecraftStoryDeath(this);
		pm.registerEvents(d, this);
		getLogger().info("MinecraftStoryKits Enabled!");
		}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("kit") && sender instanceof Player) {
			try {
				if (args.length == 3) {
					if(sender.hasPermission("minecraftstory.admin") && args[0].matches("^[A-Za-z0-9]{1,10}$") && args[1].matches("^[A-Za-z0-9]{1,10}$") && args[2].matches("^[0-9]{1,32}$")) {
						long time = Long.parseLong(args[2]);
						if(args[0].equalsIgnoreCase("set") && !args[1].equalsIgnoreCase("time")) {
							InventoryNBTSer.setKit((Player) sender, folder, args[1], time);
							return true;
						}
						if(args[0].equalsIgnoreCase("time")) {
							InventoryNBTSer.setTime((Player) sender, folder, args[1], time);
							return true;
						}
					}
				}
				if (args.length == 2) {
					if(sender.hasPermission("minecraftstory.admin") && args[0].matches("^[A-Za-z0-9]{1,10}$") && args[1].matches("^[A-Za-z0-9_]{1,10}$")) {
						if(args[0].equalsIgnoreCase("set") && !args[1].equalsIgnoreCase("time")) {
							InventoryNBTSer.setKit((Player) sender, folder, args[1], 86400);
							return true;
						}
						if(args[0].equalsIgnoreCase("del")) {
							InventoryNBTSer.delKit(folder, args[1], (Player) sender);
							return true;
						}
						Player pl = Bukkit.getPlayer(args[1]);
						if(pl==null) {
							sender.sendMessage("�4����� �� ������");
							((Player) sender).playSound(((Entity) sender).getLocation(), Sound.EXPLODE, 1, 2);
							return true;
						}
						InventoryNBTSer.getKit(pl, folder, args[0], true);
						return true;
					}
				}
				if (args.length == 1) {
					if(sender.hasPermission("minecraftstory.kit."+args[0]) && args[0].matches("^[A-Za-z0-9]{1,10}$")) {
						InventoryNBTSer.getKit((Player) sender, folder, args[0], sender.hasPermission("minecraftstory.bypass"));
						return true;
					} else {
						sender.sendMessage("�4� ��� ��� ���������� �� ���� �����!");
						Player p = (Player) sender;
						p.playSound((p).getLocation(), Sound.EXPLODE, 1, 2);
						return true;
					}
				}
				if (args.length == 0) {
					List<String> l = new ArrayList<String>();
					for(String s : InventoryNBTSer.getLogs(folder)) {
						if(sender.hasPermission("minecraftstory.kit."+s)) {
							l.add(s);
						}
					}
					sender.sendMessage("�6������ �f"+l.toString().substring(1, l.toString().length()-1).replace(",", ""));
					Player p = (Player) sender;
					p.playSound((p).getLocation(), Sound.CHEST_OPEN, 1, 1);
					return true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (cmd.getName().equalsIgnoreCase("kits")) {
			List<String> l = new ArrayList<String>();
			for(String s : InventoryNBTSer.getLogs(folder)) {
				if(sender.hasPermission("minecraftstory.kit."+s)) {
					l.add(s);
				}
			}
			sender.sendMessage("�6������ �f"+l.toString().substring(1, l.toString().length()-1).replace(",", ""));
			Player p = (Player) sender;
			p.playSound((p).getLocation(), Sound.CHEST_OPEN, 1.0F, 1.0F);
			return true;
		}
		return false;
	}
}