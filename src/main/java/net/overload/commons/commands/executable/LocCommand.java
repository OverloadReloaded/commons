package net.overload.commons.commands.executable;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.gson.Gson;

import net.overload.commons.CommonsPluginBukkit;
import net.overload.commons.commands.CustomCommand;
import net.overload.commons.utils.RegionUtils;

public class LocCommand extends CustomCommand {

	private String prefix = "§e§lLoc ➜ ";
	
	public LocCommand() {
		super("loc", null, CommonsPluginBukkit.get());
	}

	public void playerExecute(Player p, String cmd, String[] args) {
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("1")) {
				LocCommandData data = new LocCommandData(p);
				data.setLoc1(p.getLocation());
				p.sendMessage(prefix + "Position 1 set !");
			} else if(args[0].equalsIgnoreCase("2")) {
				LocCommandData data = new LocCommandData(p);
				data.setLoc2(p.getLocation());
				p.sendMessage(prefix + "Position 2 set !");
			} else if(args[0].equalsIgnoreCase("save")) {
				LocCommandData data = new LocCommandData(p);
				
				Bukkit.broadcastMessage(new Gson().toJson(data).toString());
				
				RegionUtils.blocksFromTwoPoints(data.getLoc1(), data.getLoc2());
				

				Bukkit.broadcastMessage(new Gson().toJson(RegionUtils.blocksFromTwoPoints(data.getLoc1(), data.getLoc2())).toString());
				
				p.sendMessage(prefix + "Saved");
			}
		} else p.sendMessage(prefix + "§r§cUsage: /loc <1/2/save>");
	}


	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return null;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

}
