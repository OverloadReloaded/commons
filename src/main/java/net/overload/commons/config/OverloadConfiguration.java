package net.overload.commons.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.overload.commons.CommonsPluginBukkit;
import net.overload.commons.CommonsPluginBungee;
import net.overload.commons.logger.LogLevel;
import net.overload.commons.utils.Utils;

public class OverloadConfiguration {
	private File file;

	private String redisIp;
	private String redisPassword;
	private Integer redisPort;
	
	private String mongoIp;
	private String mongoPassword;
	private String mongoDatabase;
	private String mongoUser;
	private Integer mongoPort;

	private Boolean debug = false;
	private Boolean printStackTrace = false;
	private Boolean showErrorsMessages = false;
	private Boolean showJedisMessaging = false;
	private Boolean configFileLoaded = false;
	private Boolean showJsonMessages = false;

	public OverloadConfiguration(String func_file) {
		setFile(new File(func_file));
	}

	public OverloadConfiguration(File func_file) {
		setFile(func_file);
	}

	public Boolean check() {
		try {
			Properties properties = new Properties();
			InputStream input = null;
			if (getFile().exists()) {
				if(Utils.isBukkit()) CommonsPluginBukkit.get().logger().send(LogLevel.INFO, "Config", "Configuration file is loading...");
				else CommonsPluginBungee.get().logger().send(LogLevel.INFO, "Config", "Configuration file is loading...");
				
				input = new FileInputStream(getFile());
				properties.load(input);
				
				setRedisIp(properties.getProperty("redisIp"));
				setRedisPort(Integer.parseInt(properties.getProperty("redisPort")));
				setRedisPassword(properties.getProperty("redisPassword"));
				
				setMongoIp(properties.getProperty("mongoIp"));
				setMongoPort(Integer.parseInt(properties.getProperty("mongoPort")));
				setMongoPassword(properties.getProperty("mongoPassword"));
				setMongoUser(properties.getProperty("mongoUser"));
				setMongoDatabase(properties.getProperty("mongoDatabase"));
				
				setDebug(Boolean.parseBoolean(properties.getProperty("consoleDebug")));
				setPrintStackTrace(Boolean.parseBoolean(properties.getProperty("printStackTrace")));
				setShowErrorsMessages(Boolean.parseBoolean(properties.getProperty("showErrorsMessages")));
				setShowJedisMessaging(Boolean.parseBoolean(properties.getProperty("showJedisMessages")));
				setShowJsonMessages(Boolean.parseBoolean(properties.getProperty("showJsonMessages")));
				setConfigFileLoaded(true);
				
				if(Utils.isBukkit()) CommonsPluginBukkit.get().logger().send(LogLevel.SUCCESS, "Config", "Configuration file loaded!");
				else CommonsPluginBungee.get().logger().send(LogLevel.SUCCESS, "Config", "Configuration file loaded!");
				return true;
			}
			try {
				if(Utils.isBukkit()) CommonsPluginBukkit.get().logger().send(LogLevel.INFO, "Config", "Configuration file doesn't exist. Creating it...");
				else CommonsPluginBungee.get().logger().send(LogLevel.SUCCESS, "Config", "Configuration file doesn't exist. Creating it...");
				List<String> strgs = new ArrayList<String>();
				strgs.add("#         ---------");
				strgs.add("#    OverLoad Configurations: Redis");
				strgs.add("#         ---------");
				strgs.add("redisIp=127.0.0.1");
				strgs.add("redisPort=6379");
				strgs.add("redisPassword=SET_PASSWORD_HERE");
				strgs.add(" ");
				strgs.add("mongoIp=127.0.0.1");
				strgs.add("mongoPort=27017");
				strgs.add("mongoPassword=SET_PASSWORD_HERE");
				strgs.add("mongoUser=SET_USER_HERE");
				strgs.add("mongoDatabase=SET_DATABASE_NAME_HERE");
				strgs.add(" ");
				strgs.add("consoleDebug=false");
				strgs.add("printStackTrace=false");
				strgs.add("showErrorsMessages=false");
				strgs.add("showJedisMessages=false");
				strgs.add("showJsonMessages=false");
				
				Utils.createTextFile(strgs, getFile().toPath());
				if(Utils.isBukkit()) {
					CommonsPluginBukkit.get().logger().send(LogLevel.SUCCESS, "Config", "Configuration file created!");
					CommonsPluginBukkit.get().logger().send(LogLevel.SUCCESS, "Config", "Check it out to configure the Redis Connection!");
				} else {
					CommonsPluginBungee.get().logger().send(LogLevel.SUCCESS, "Config", "Configuration file created!");
					CommonsPluginBungee.get().logger().send(LogLevel.SUCCESS, "Config", "Check it out to configure the Redis Connection!");
				}
				
				System.exit(2);
				return false;
			} catch (Exception e) {
				if(Utils.isBukkit()) {
					CommonsPluginBukkit.get().logger().send(LogLevel.ERROR, "Config", "Error while creating the configuration file: " + this.file.toString());
					if (CommonsPluginBukkit.get().config.getPrintStackTrace()) e.printStackTrace();
					if (CommonsPluginBukkit.get().config.getShowErrorsMessages()) CommonsPluginBukkit.get().logger().send(LogLevel.ERROR_MESSAGE, "Config", "Error message: " + e.toString());
				} else {
					CommonsPluginBungee.get().logger().send(LogLevel.ERROR, "Config", "Error while creating the configuration file: " + this.file.toString());
					if (CommonsPluginBungee.get().config.getPrintStackTrace()) e.printStackTrace();
					if (CommonsPluginBungee.get().config.getShowErrorsMessages()) CommonsPluginBungee.get().logger().send(LogLevel.ERROR_MESSAGE, "Config", "Error message: " + e.toString());
				}
				return false;
			}
		} catch (Exception e) {
			if(Utils.isBukkit()) {
				CommonsPluginBukkit.get().logger().send(LogLevel.ERROR, "Config", "Error while checking the configuration file: " + this.file.toString());
				if (CommonsPluginBukkit.get().config.getPrintStackTrace()) e.printStackTrace();
				if (CommonsPluginBukkit.get().config.getShowErrorsMessages()) CommonsPluginBukkit.get().logger().send(LogLevel.ERROR_MESSAGE, "Config", "Error message: " + e.toString());
			} else {
				CommonsPluginBungee.get().logger().send(LogLevel.ERROR, "Config", "Error while checking the configuration file: " + this.file.toString());
				if (CommonsPluginBungee.get().config.getPrintStackTrace()) e.printStackTrace();
				if (CommonsPluginBungee.get().config.getShowErrorsMessages()) CommonsPluginBungee.get().logger().send(LogLevel.ERROR_MESSAGE, "Config", "Error message: " + e.toString());
			}
			
			System.exit(2);
			return false;
		}
	}

	public File getFile() {
		return this.file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Boolean getDebug() {
		return this.debug;
	}

	public void setDebug(Boolean b) {
		this.debug = b;
	}

	public Boolean getPrintStackTrace() {
		return this.printStackTrace;
	}

	public void setPrintStackTrace(Boolean printStackTrace) {
		this.printStackTrace = printStackTrace;
	}

	public Boolean getShowErrorsMessages() {
		return this.showErrorsMessages;
	}

	public void setShowErrorsMessages(Boolean showErrorsMessages) {
		this.showErrorsMessages = showErrorsMessages;
	}

	public Boolean getConfigFileLoaded() {
		return this.configFileLoaded;
	}

	public void setConfigFileLoaded(Boolean configFileLoaded) {
		this.configFileLoaded = configFileLoaded;
	}

	public Boolean getShowJedisMessaging() {
		return this.showJedisMessaging;
	}

	public void setShowJedisMessaging(Boolean showJedisMessaging) {
		this.showJedisMessaging = showJedisMessaging;
	}

	public Boolean getShowJsonMessages() {
		return this.showJsonMessages;
	}

	public void setShowJsonMessages(Boolean showJsonMessages) {
		this.showJsonMessages = showJsonMessages;
	}

	public String getRedisIp() {
		return redisIp;
	}

	public void setRedisIp(String redisIp) {
		this.redisIp = redisIp;
	}

	public String getRedisPassword() {
		return redisPassword;
	}

	public void setRedisPassword(String redisPassword) {
		this.redisPassword = redisPassword;
	}

	public Integer getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(Integer redisPort) {
		this.redisPort = redisPort;
	}

	public String getMongoIp() {
		return mongoIp;
	}

	public void setMongoIp(String mongoIp) {
		this.mongoIp = mongoIp;
	}

	public String getMongoPassword() {
		return mongoPassword;
	}

	public void setMongoPassword(String mongoPassword) {
		this.mongoPassword = mongoPassword;
	}

	public Integer getMongoPort() {
		return mongoPort;
	}

	public void setMongoPort(Integer mongoPort) {
		this.mongoPort = mongoPort;
	}

	public String getMongoDatabase() {
		return mongoDatabase;
	}

	public void setMongoDatabase(String mongoDatabase) {
		this.mongoDatabase = mongoDatabase;
	}

	public String getMongoUser() {
		return mongoUser;
	}

	public void setMongoUser(String mongoUser) {
		this.mongoUser = mongoUser;
	}
}
