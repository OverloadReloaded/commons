package net.overload.commons.players.managers;

import java.util.ArrayList;
import java.util.HashMap;

import net.overload.commons.players.enums.Rank;

public class PermissionsManager {

	private HashMap<Rank, ArrayList<String>> bukkitPerms;
	private HashMap<Rank, ArrayList<String>> bungeePerms;
	
	public PermissionsManager() {
		this.bukkitPerms = new HashMap<Rank, ArrayList<String>>();
		this.bungeePerms = new HashMap<Rank, ArrayList<String>>();
	}
	
	public void init() {
		ArrayList<String> none = new ArrayList<>();
		none.add("");
		
		ArrayList<String> vip = new ArrayList<>();
		vip.add("protocollib.protocol");
		
		ArrayList<String> mvp = new ArrayList<>();
		mvp.add("protocollib.packet");
		
		
		ArrayList<String> yt = new ArrayList<>();
		yt.add("protocollib.filter");
		
		
		ArrayList<String> staff = new ArrayList<>();
		staff.add("protocollib.packetlog");
		
		
		ArrayList<String> helper = new ArrayList<>();
		helper.add("");
		
		
		ArrayList<String> mod = new ArrayList<>();
		mod.add("");
		
		
		ArrayList<String> dev = new ArrayList<>();
		dev.add("");
		
		
		ArrayList<String> admin = new ArrayList<>();
		admin.add("");
		
		bukkitPerms.put(Rank.NONE, none);
		bukkitPerms.put(Rank.VIP, vip);
		bukkitPerms.put(Rank.MVP, mvp);
		bukkitPerms.put(Rank.YT, yt);
		bukkitPerms.put(Rank.STAFF, staff);
		bukkitPerms.put(Rank.HELPER, helper);
		bukkitPerms.put(Rank.MOD, mod);
		bukkitPerms.put(Rank.DEV, dev);
		bukkitPerms.put(Rank.ADMIN, admin);
		
	}
	
	public ArrayList<String> getBukkitPermsFromRank(Rank rank) {
		ArrayList<String> perms = new ArrayList<>();
		
		switch(rank) {

		case NONE:
			perms.addAll(this.getBukkitPerms().get(Rank.NONE));
			break;
		case VIP:
			perms.addAll(this.getBukkitPerms().get(Rank.NONE));
			perms.addAll(this.getBukkitPerms().get(Rank.VIP));
			break;
		case MVP:
			perms.addAll(this.getBukkitPerms().get(Rank.NONE));
			perms.addAll(this.getBukkitPerms().get(Rank.VIP));
			perms.addAll(this.getBukkitPerms().get(Rank.MVP));
			break;
		case YT:
			perms.addAll(this.getBukkitPerms().get(Rank.NONE));
			perms.addAll(this.getBukkitPerms().get(Rank.VIP));
			perms.addAll(this.getBukkitPerms().get(Rank.MVP));
			perms.addAll(this.getBukkitPerms().get(Rank.YT));
			break;
		case STAFF:
			perms.addAll(this.getBukkitPerms().get(Rank.NONE));
			perms.addAll(this.getBukkitPerms().get(Rank.VIP));
			perms.addAll(this.getBukkitPerms().get(Rank.MVP));
			perms.addAll(this.getBukkitPerms().get(Rank.YT));
			perms.addAll(this.getBukkitPerms().get(Rank.STAFF));
			break;
		case HELPER:
			perms.addAll(this.getBukkitPerms().get(Rank.NONE));
			perms.addAll(this.getBukkitPerms().get(Rank.VIP));
			perms.addAll(this.getBukkitPerms().get(Rank.MVP));
			perms.addAll(this.getBukkitPerms().get(Rank.YT));
			perms.addAll(this.getBukkitPerms().get(Rank.STAFF));
			perms.addAll(this.getBukkitPerms().get(Rank.HELPER));
			break;
		case MOD:
			perms.addAll(this.getBukkitPerms().get(Rank.NONE));
			perms.addAll(this.getBukkitPerms().get(Rank.VIP));
			perms.addAll(this.getBukkitPerms().get(Rank.MVP));
			perms.addAll(this.getBukkitPerms().get(Rank.YT));
			perms.addAll(this.getBukkitPerms().get(Rank.STAFF));
			perms.addAll(this.getBukkitPerms().get(Rank.HELPER));
			perms.addAll(this.getBukkitPerms().get(Rank.MOD));
			break;
		case DEV:
			perms.addAll(this.getBukkitPerms().get(Rank.NONE));
			perms.addAll(this.getBukkitPerms().get(Rank.VIP));
			perms.addAll(this.getBukkitPerms().get(Rank.MVP));
			perms.addAll(this.getBukkitPerms().get(Rank.YT));
			perms.addAll(this.getBukkitPerms().get(Rank.STAFF));
			perms.addAll(this.getBukkitPerms().get(Rank.HELPER));
			perms.addAll(this.getBukkitPerms().get(Rank.MOD));
			perms.addAll(this.getBukkitPerms().get(Rank.DEV));
			perms.add("*");
			break;
		case ADMIN:
			perms.addAll(this.getBukkitPerms().get(Rank.NONE));
			perms.addAll(this.getBukkitPerms().get(Rank.VIP));
			perms.addAll(this.getBukkitPerms().get(Rank.MVP));
			perms.addAll(this.getBukkitPerms().get(Rank.YT));
			perms.addAll(this.getBukkitPerms().get(Rank.STAFF));
			perms.addAll(this.getBukkitPerms().get(Rank.HELPER));
			perms.addAll(this.getBukkitPerms().get(Rank.MOD));
			perms.addAll(this.getBukkitPerms().get(Rank.DEV));
			perms.addAll(this.getBukkitPerms().get(Rank.ADMIN));
			perms.add("*");
			break;
		default:
			break;
		
		}
		return perms;
	}
	

	
	public ArrayList<String> getBungeePermsFromRank(Rank rank) {
		ArrayList<String> perms = new ArrayList<>();
		
		switch(rank) {

		case NONE:
			perms.addAll(this.getBungeePerms().get(Rank.NONE));
			break;
		case VIP:
			perms.addAll(this.getBungeePerms().get(Rank.NONE));
			perms.addAll(this.getBungeePerms().get(Rank.VIP));
			break;
		case MVP:
			perms.addAll(this.getBungeePerms().get(Rank.NONE));
			perms.addAll(this.getBungeePerms().get(Rank.VIP));
			perms.addAll(this.getBungeePerms().get(Rank.MVP));
			break;
		case YT:
			perms.addAll(this.getBungeePerms().get(Rank.NONE));
			perms.addAll(this.getBungeePerms().get(Rank.VIP));
			perms.addAll(this.getBungeePerms().get(Rank.MVP));
			perms.addAll(this.getBungeePerms().get(Rank.YT));
			break;
		case STAFF:
			perms.addAll(this.getBungeePerms().get(Rank.NONE));
			perms.addAll(this.getBungeePerms().get(Rank.VIP));
			perms.addAll(this.getBungeePerms().get(Rank.MVP));
			perms.addAll(this.getBungeePerms().get(Rank.YT));
			perms.addAll(this.getBungeePerms().get(Rank.STAFF));
			break;
		case HELPER:
			perms.addAll(this.getBungeePerms().get(Rank.NONE));
			perms.addAll(this.getBungeePerms().get(Rank.VIP));
			perms.addAll(this.getBungeePerms().get(Rank.MVP));
			perms.addAll(this.getBungeePerms().get(Rank.YT));
			perms.addAll(this.getBungeePerms().get(Rank.STAFF));
			perms.addAll(this.getBungeePerms().get(Rank.HELPER));
			break;
		case MOD:
			perms.addAll(this.getBungeePerms().get(Rank.NONE));
			perms.addAll(this.getBungeePerms().get(Rank.VIP));
			perms.addAll(this.getBungeePerms().get(Rank.MVP));
			perms.addAll(this.getBungeePerms().get(Rank.YT));
			perms.addAll(this.getBungeePerms().get(Rank.STAFF));
			perms.addAll(this.getBungeePerms().get(Rank.HELPER));
			perms.addAll(this.getBungeePerms().get(Rank.MOD));
			break;
		case DEV:
			perms.addAll(this.getBungeePerms().get(Rank.NONE));
			perms.addAll(this.getBungeePerms().get(Rank.VIP));
			perms.addAll(this.getBungeePerms().get(Rank.MVP));
			perms.addAll(this.getBungeePerms().get(Rank.YT));
			perms.addAll(this.getBungeePerms().get(Rank.STAFF));
			perms.addAll(this.getBungeePerms().get(Rank.HELPER));
			perms.addAll(this.getBungeePerms().get(Rank.MOD));
			perms.addAll(this.getBungeePerms().get(Rank.DEV));
			perms.add("*");
			break;
		case ADMIN:
			perms.addAll(this.getBungeePerms().get(Rank.NONE));
			perms.addAll(this.getBungeePerms().get(Rank.VIP));
			perms.addAll(this.getBungeePerms().get(Rank.MVP));
			perms.addAll(this.getBungeePerms().get(Rank.YT));
			perms.addAll(this.getBungeePerms().get(Rank.STAFF));
			perms.addAll(this.getBungeePerms().get(Rank.HELPER));
			perms.addAll(this.getBungeePerms().get(Rank.MOD));
			perms.addAll(this.getBungeePerms().get(Rank.DEV));
			perms.addAll(this.getBungeePerms().get(Rank.ADMIN));
			perms.add("*");
			break;
		default:
			break;
		
		}
		return perms;
	}

	public HashMap<Rank, ArrayList<String>> getBukkitPerms() {
		return bukkitPerms;
	}

	public void setBukkitPerms(HashMap<Rank, ArrayList<String>> bukkitPerms) {
		this.bukkitPerms = bukkitPerms;
	}

	public HashMap<Rank, ArrayList<String>> getBungeePerms() {
		return bungeePerms;
	}

	public void setBungeePerms(HashMap<Rank, ArrayList<String>> bungeePerms) {
		this.bungeePerms = bungeePerms;
	}

}
