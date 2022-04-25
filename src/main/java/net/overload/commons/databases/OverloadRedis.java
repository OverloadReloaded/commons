package net.overload.commons.databases;

import org.bukkit.Bukkit;

import net.overload.commons.CommonsPluginBukkit;
import net.overload.commons.CommonsPluginBungee;
import net.overload.commons.config.OverloadConfiguration;
import net.overload.commons.logger.LogLevel;
import net.overload.commons.utils.Utils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class OverloadRedis {

	private Jedis database;
	private Jedis jedis;
	private JedisPubSub pubSub;

	public OverloadRedis() {
		if (Utils.isBukkit())
			CommonsPluginBukkit.get().logger().send(LogLevel.INFO, "Redis", "Connecting to Redis Server...");
		else
			CommonsPluginBungee.get().logger().send(LogLevel.INFO, "Redis", "Connecting to Redis Server...");
	}

	public Boolean connect(Boolean showMessages) {
		try {
			OverloadConfiguration c;
			if (Utils.isBukkit())
				c = CommonsPluginBukkit.get().config;
			else
				c = CommonsPluginBungee.get().config;

			if (c.getConfigFileLoaded()) {
				database = new Jedis(c.getRedisIp(), c.getRedisPort());
				database.auth(c.getRedisPassword());

				jedis = new Jedis(c.getRedisIp(), c.getRedisPort());
				jedis.auth(c.getRedisPassword());

				if(showMessages) {
					if (Utils.isBukkit()) CommonsPluginBukkit.get().logger().send(LogLevel.SUCCESS, "Redis", "Connected to Redis Server!");
					else CommonsPluginBungee.get().logger().send(LogLevel.SUCCESS, "Redis", "Connected to Redis Server!");
				}
				return true;
			}
			if(showMessages) {
				if (Utils.isBukkit()) {
					CommonsPluginBukkit.get().logger().send(LogLevel.CRITICAL, "Redis", "The configuration file is not loaded.");
					CommonsPluginBukkit.get().logger().send(LogLevel.CRITICAL, "Redis", "Please check it out.");
				} else {
					CommonsPluginBungee.get().logger().send(LogLevel.CRITICAL, "Redis", "The configuration file is not loaded.");
					CommonsPluginBungee.get().logger().send(LogLevel.CRITICAL, "Redis", "Please check it out.");
				}
			}
			CommonsPluginBukkit.get().stop();
			return false;
		} catch (Exception e) {
			if (Utils.isBukkit()) {
				CommonsPluginBukkit.get().logger().send(LogLevel.ERROR, "Redis", "Error while connection to the Redis Server.");
				CommonsPluginBukkit.get().logger().send(LogLevel.ERROR, "Redis", "Please check out the configuration file or enable the printStackTrace setting.");
				if (CommonsPluginBukkit.get().config.getPrintStackTrace()) e.printStackTrace();
				if (CommonsPluginBukkit.get().config.getShowErrorsMessages()) CommonsPluginBukkit.get().logger().send(LogLevel.ERROR_MESSAGE, "Redis", "Error message: " + e.toString());
			} else {
				CommonsPluginBungee.get().logger().send(LogLevel.ERROR, "Redis", "Error while connection to the Redis Server.");
				CommonsPluginBungee.get().logger().send(LogLevel.ERROR, "Redis", "Please check out the configuration file or enable the printStackTrace setting.");
				if (CommonsPluginBungee.get().config.getPrintStackTrace()) e.printStackTrace();
				if (CommonsPluginBungee.get().config.getShowErrorsMessages()) CommonsPluginBungee.get().logger().send(LogLevel.ERROR_MESSAGE, "Redis", "Error message: " + e.toString());
			}
			CommonsPluginBukkit.get().stop();
			return false;
		}
	}

	@SuppressWarnings("unused")
	public void subscribeCommons() {
		pubSub = new JedisPubSub() {
			public void onMessage(String channel, String message) {
				OverloadConfiguration c;
				if (Utils.isBukkit()) c = CommonsPluginBukkit.get().config;
				else c = CommonsPluginBungee.get().config;

				if (c.getShowJedisMessaging()) if (Utils.isBukkit()) CommonsPluginBukkit.get().logger().send(LogLevel.CMD, "Redis", "Received from " + channel + ": " + message); else CommonsPluginBungee.get().logger().send(LogLevel.CMD, "Redis", "Received from " + channel + ": " + message);

				if (Utils.isBukkit()) {
					if (channel.equals("toAllServers")) {
						if(message.equals("stop")) System.exit(0);
					}

					if (channel.equals("to." + Bukkit.getServerName())) {
						if (database.exists("Server:" + Bukkit.getServerName())) {
							if(message.equals("stop")) System.exit(0);
						}
					}
				} else {
					if (channel.equals("toAllBungee" )) {
						
					}
				}
			}

			public void onSubscribe(String channel, Integer subscribedChannels) {
				if (Utils.isBukkit()) CommonsPluginBukkit.get().logger().send(LogLevel.CMD, "Redis", "Subscribed to channel: " + channel + ". (Total Subs: " + subscribedChannels + ")");
				else CommonsPluginBungee.get().logger().send(LogLevel.CMD, "Redis", "Subscribed to channel: " + channel + ". (Total Subs: " + subscribedChannels + ")");
			}

			public void onUnsubscribe(String channel, Integer subscribedChannels) {
				if (Utils.isBukkit()) CommonsPluginBukkit.get().logger().send(LogLevel.CMD, "Redis", "Unsubscribed to channel: " + channel + ". (Total Subs: " + subscribedChannels + ")");
				else CommonsPluginBungee.get().logger().send(LogLevel.CMD, "Redis", "Unsubscribed to channel: " + channel + ". (Total Subs: " + subscribedChannels + ")");
			}
		};
				
		if(Utils.isBukkit()) jedis.subscribe(pubSub, new String[] { "toAllBungee", "toAllServers", "to." + Bukkit.getServerName() });
	}

	public Jedis getDatabase() {
		return database;
	}
	public void setDatabase(Jedis database) {
		this.database = database;
	}
	public Jedis getJedis() {
		return jedis;
	}
	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}
	public JedisPubSub getPubSub() {
		return pubSub;
	}
	public void setPubSub(JedisPubSub pubSub) {
		this.pubSub = pubSub;
	}
}
