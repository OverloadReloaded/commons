package net.overload.commons;

import java.util.Timer;
import java.util.TimerTask;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.overload.commons.config.OverloadConfiguration;
import net.overload.commons.databases.OverloadRedis;
import net.overload.commons.databases.mongo.OverloadMongoDatabase;
import net.overload.commons.events.CommonsEventBungee;
import net.overload.commons.logger.LogLevel;
import net.overload.commons.players.managers.PermissionsManager;
import net.overload.commons.players.managers.ProfileManager;

public class CommonsPluginBungee extends Plugin {

	private static CommonsPluginBungee instance;

	private static net.overload.commons.logger.Logger log = new net.overload.commons.logger.Logger("Commons");
	
	public OverloadConfiguration config;
	public OverloadRedis redis;
	public OverloadMongoDatabase database;
	
	public ProfileManager profileManager;
	public PermissionsManager pmm;
	
	/**
	 * Plugin interface
	 */

	@Override
	public void onLoad() {
	}

	@Override
	public void onEnable() {
		instance = this;
		
		loadConfig();
		startRedis();
		startMongo();
		checkConnections();

		
		ProxyServer.getInstance().getPluginManager().registerListener(this, new CommonsEventBungee());
			
		profileManager = new ProfileManager(this);
		pmm = new PermissionsManager();
		pmm.init();
		
		
		log.send(LogLevel.SUCCESS, "Enabled Commons plugin !");
	}

	@Override
	public void onDisable() {
		log.send(LogLevel.INFO, "Disabling Commons plugin!");
	}

	/**
	 * Custom functions
	 */

	private void loadConfig() {
		try {
			config = new OverloadConfiguration("../redis.properties");
			config.check();
		} catch (Exception e) {
			logger().send(LogLevel.CRITICAL, "Config",
					"Could not load the configuration file. Please contact administrators or developers.");
			if (config.getDebug())
				logger().send(LogLevel.DEBUG, "Config", e.getStackTrace().toString());
		}
	}

	private void startRedis() {
		redis = new OverloadRedis();
		redis.connect(true);
	}

	private void startMongo() {
		database = new OverloadMongoDatabase();
		database.connect();
	}
	
	private void checkConnections() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					public void run() {
						if(!redis.getDatabase().isConnected() || !redis.getJedis().isConnected()) {
							redis.connect(false);
						}
						
						if(database.getClient() == null) {
							database.connect();
						}
					}
				}, 3 * (1000*60));
			}
		}).start();
	}


	/**
	 * Getters & Setters
	 */

	public net.overload.commons.logger.Logger logger() {
		return log;
	}

	public static CommonsPluginBungee get() {
		return instance;
	}

	public ProfileManager getProfileManager() {
		return profileManager;
	}
}
