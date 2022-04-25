package net.overload.commons.players.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Rank {

	NONE("none", "§8", "§8", 0, Arrays.asList("coucou"), Arrays.asList("")),
	VIP("vip", "§a§lVIP §7", "§f", 10, Arrays.asList(""), Arrays.asList("protocollib.protocol")),
	MVP("mvp", "§b§lMVP §7", "§f", 20, Arrays.asList(""), Arrays.asList("protocollib.packet")),
	YT("yt", "§5§lYT §7", "§f", 20, Arrays.asList(""), Arrays.asList("protocollib.filter")),
	STAFF("staff", "§e§lSTAFF §7", "§f", 30, Arrays.asList(""), Arrays.asList("protocollib.packetlog")),
	HELPER("helper", "§1§lHELPER §7", "§f", 40, Arrays.asList(""), Arrays.asList("")),
	MOD("mod", "§6§lMOD §7", "§f", 50, Arrays.asList(""), Arrays.asList("")),
	DEV("dev", "§c§lDEV §7", "§f", 60, Arrays.asList(""), Arrays.asList("")),
	ADMIN("admin", "§4§lADMIN §7", "§f", 80, Arrays.asList(""), Arrays.asList("minecraft.command.stop"));

	private String databaseName;

	private String prefix;

	private String chatColor;
	
	private Integer level;

	private List<String> bukkitPermissions;
	private List<String> bungeePermissions;

	Rank(String dbName, String prefix, String chatColor, Integer lvl, List<String> bukkitPermissions, List<String> bungeePermissions) {
		setDatabaseName(dbName);
		setPrefix(prefix);
		setChatColor(chatColor);
		setLevel(lvl);
		setBukkitPermissions(bukkitPermissions);
		setBungeePermissions(bungeePermissions);
		
	}
	
	public List<String> getAllBungeePermissions() {
		List<String> perms = new ArrayList<>();
		
		switch(this) {

		case NONE:
			perms.addAll(Rank.NONE.getBungeePermissions());
			break;
		case VIP:
			perms.addAll(Rank.NONE.getBungeePermissions());
			perms.addAll(Rank.VIP.getBungeePermissions());
			break;
		case MVP:
			perms.addAll(Rank.NONE.getBungeePermissions());
			perms.addAll(Rank.VIP.getBungeePermissions());
			perms.addAll(Rank.MVP.getBungeePermissions());
			break;
		case YT:
			perms.addAll(Rank.NONE.getBungeePermissions());
			perms.addAll(Rank.VIP.getBungeePermissions());
			perms.addAll(Rank.MVP.getBungeePermissions());
			perms.addAll(Rank.YT.getBungeePermissions());
			break;
		case STAFF:
			perms.addAll(Rank.NONE.getBungeePermissions());
			perms.addAll(Rank.VIP.getBungeePermissions());
			perms.addAll(Rank.MVP.getBungeePermissions());
			perms.addAll(Rank.STAFF.getBungeePermissions());
			break;
		case HELPER:
			perms.addAll(Rank.NONE.getBungeePermissions());
			perms.addAll(Rank.VIP.getBungeePermissions());
			perms.addAll(Rank.MVP.getBungeePermissions());
			perms.addAll(Rank.STAFF.getBungeePermissions());
			perms.addAll(Rank.HELPER.getBungeePermissions());
			break;
		case MOD:
			perms.addAll(Rank.NONE.getBungeePermissions());
			perms.addAll(Rank.VIP.getBungeePermissions());
			perms.addAll(Rank.MVP.getBungeePermissions());
			perms.addAll(Rank.STAFF.getBungeePermissions());
			perms.addAll(Rank.HELPER.getBungeePermissions());
			perms.addAll(Rank.MOD.getBungeePermissions());
			break;
		case DEV:
			perms.add("*");
			break;
		case ADMIN:
			perms.add("*");
			break;
		
		default:
			break;
		
		}
		return perms;
	}

	public String getDatabaseName() {
		return this.databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getChatColor() {
		return this.chatColor;
	}

	public void setChatColor(String chatColor) {
		this.chatColor = chatColor;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public List<String> getBukkitPermissions() {
		return bukkitPermissions;
	}

	public void setBukkitPermissions(List<String> bukkitPermissions) {
		this.bukkitPermissions = bukkitPermissions;
	}

	public List<String> getBungeePermissions() {
		return bungeePermissions;
	}

	public void setBungeePermissions(List<String> bungeePermissions) {
		this.bungeePermissions = bungeePermissions;
	}
}
