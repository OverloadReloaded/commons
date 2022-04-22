package net.overload.commons.server;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.gson.GsonBuilder;

import net.overload.commons.CommonsPluginBukkit;
import net.overload.commons.databases.OverloadRedisStrings;
import net.overload.commons.utils.Utils;
import redis.clients.jedis.Jedis;

public class MinecraftServerInfo {

	private String serverName;
	private Integer serverPort;
	private MinecraftServerState state;
	private MinecraftServerType type;
	
	private Integer maxPlayers;
	private Integer currentPlayers;
	
	private ArrayList<MinecraftServerPlayer> players = new ArrayList<>();
	
	private HashMap<String, Object> otherData = new HashMap<>();

	public MinecraftServerInfo(String serverName, Integer serverPort, MinecraftServerState state,
			MinecraftServerType type, Integer maxPlayers, Integer currentPlayers,
			ArrayList<MinecraftServerPlayer> players, HashMap<String, Object> otherData) {
		super();
		this.serverName = serverName;
		this.serverPort = serverPort;
		this.state = state;
		this.type = type;
		this.maxPlayers = maxPlayers;
		this.currentPlayers = currentPlayers;
		this.players = players;
		this.otherData = otherData;
	}
	
	public MinecraftServerInfo() {
	}

	public void update(Boolean publish) {
		this.setCurrentPlayers(Bukkit.getOnlinePlayers().size());
		this.setMaxPlayers(Bukkit.getMaxPlayers());
		
		this.getPlayers().clear();
		for(Player p : Bukkit.getOnlinePlayers()) this.getPlayers().add(new MinecraftServerPlayer(p.getUniqueId()));
		
		Jedis db = CommonsPluginBukkit.get().redis.getDatabase();
		String redisKey = String.format(OverloadRedisStrings.SERVER_KEEP_ALIVE.s(), this.getServerName());
		
		db.set(redisKey, String.format("Last updated at %s", Utils.getCurrentTime()));
		db.expire(redisKey, 7);
		
		db.set(String.format(OverloadRedisStrings.SERVER_LIST.s(), this.getServerName()), new GsonBuilder().create().toJson(this));
		
		if(publish) {
			if(Utils.isBukkit()) CommonsPluginBukkit.get().redis.getDatabase().publish("updateServer", new GsonBuilder().create().toJson(this));
		}
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Integer getServerPort() {
		return serverPort;
	}

	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
	}

	public Integer getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(Integer maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public Integer getCurrentPlayers() {
		return currentPlayers;
	}

	public void setCurrentPlayers(Integer currentPlayers) {
		this.currentPlayers = currentPlayers;
	}

	public ArrayList<MinecraftServerPlayer> getPlayers() {
		return players;
	}

	public void addPlayer(MinecraftServerPlayer msp) {
		this.players.add(msp);
	}

	public HashMap<String, Object> getOtherData() {
		return otherData;
	}

	public void addOtherData(String key, Object data) {
		this.otherData.put(key, data);
	}

	public MinecraftServerState getState() {
		return state;
	}

	public void setState(MinecraftServerState state) {
		this.state = state;
	}

	public MinecraftServerType getType() {
		return type;
	}

	public void setType(MinecraftServerType type) {
		this.type = type;
	}
}
