package net.overload.commons.players.objects;

import java.util.ArrayList;
import java.util.List;

import dev.morphia.annotations.Embedded;
import net.overload.commons.players.enums.Rank;

public class PlayerData {

	private String uuid;
	private Rank rank = Rank.NONE;
	private List<Punishment> punishment = new ArrayList<>();
	private List<String> permissions = new ArrayList<>();
	
	private Integer coins = 0;
	
	@Embedded
	private PlayerSettings settings = new PlayerSettings();

	public PlayerData() {}
	
	public PlayerData(String uuid) {
		this.setUuid(uuid);
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
		
	}

	public List<Punishment> getPunishment() {
		return punishment;
	}

	public void setPunishment(List<Punishment> punishment) {
		this.punishment = punishment;
	}

	public PlayerSettings getSettings() {
		return settings;
	}

	public void setSettings(PlayerSettings settings) {
		this.settings = settings;
	}

	public Integer getCoins() {
		return coins;
	}

	public void setCoins(Integer coins) {
		this.coins = coins;
	}

	public List<String> getPermissions() {
		return permissions;
	}
	
	public void addPermission(String permission) {
		this.permissions.add(permission);
	}
	
	public void removePermissions(String permission) {
		if(this.permissions.contains(permission)) this.permissions.remove(permission);
	}
}
