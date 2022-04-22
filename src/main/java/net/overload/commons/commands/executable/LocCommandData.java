package net.overload.commons.commands.executable;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocCommandData {

	public static HashMap<UUID, LocCommandData> all = new HashMap<UUID, LocCommandData>();

	private Player player;
	
	private Location loc1;
	private Location loc2;
	
	
	public LocCommandData(Player p) {
		if(!all.containsKey(p.getUniqueId())) {
			this.setPlayer(p);
			
			all.put(p.getUniqueId(), this);
		} else {
			this.setLoc1(all.get(p.getUniqueId()).getLoc1());
			this.setLoc2(all.get(p.getUniqueId()).getLoc2());
			this.setPlayer(all.get(p.getUniqueId()).getPlayer());
		}
	}
	
	
	public Location getLoc1() {
		return loc1;
	}
	public void setLoc1(Location loc1) {
		this.loc1 = loc1;
	}
	public Location getLoc2() {
		return loc2;
	}
	public void setLoc2(Location loc2) {
		this.loc2 = loc2;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
}
