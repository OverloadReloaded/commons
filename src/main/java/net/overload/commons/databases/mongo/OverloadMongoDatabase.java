package net.overload.commons.databases.mongo;

import org.bson.UuidRepresentation;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.MapperOptions;
import net.overload.commons.CommonsPluginBukkit;
import net.overload.commons.CommonsPluginBungee;
import net.overload.commons.config.OverloadConfiguration;
import net.overload.commons.logger.LogLevel;
import net.overload.commons.players.objects.Profile;
import net.overload.commons.utils.Utils;

public class OverloadMongoDatabase {

	private Morphia morphia;
	private Datastore store;

	private MongoClientOptions clientOptions;
	private MongoCredential credential;
	private MongoClient client;

	public OverloadMongoDatabase() {
		super();
	}

	@SuppressWarnings({ "deprecation" })
	public void connect() {
		OverloadConfiguration c = null;
		try {
			if (Utils.isBukkit()) {
				CommonsPluginBukkit.get().logger().send(LogLevel.INFO, "Mongo", "Connecting to Mongo Server...");
				c = CommonsPluginBukkit.get().config;				
			} else {
				CommonsPluginBungee.get().logger().send(LogLevel.INFO, "Mongo", "Connecting to Mongo Server...");
				c = CommonsPluginBungee.get().config;
			}

			this.morphia = new Morphia();
			this.morphia.map(Profile.class);
	
			if (Utils.isBukkit()) this.morphia.getMapper().setOptions(MapperOptions.builder().classLoader(CommonsPluginBukkit.class.getClassLoader()).build());
			else this.morphia.getMapper().setOptions(MapperOptions.builder().classLoader(CommonsPluginBungee.class.getClassLoader()).build());
	
			this.clientOptions = MongoClientOptions.builder().uuidRepresentation(UuidRepresentation.STANDARD).build();
			this.credential = MongoCredential.createCredential(c.getMongoUser(), c.getMongoDatabase(), c.getMongoPassword().toCharArray());
	
			this.client = new MongoClient(new ServerAddress(c.getMongoIp(), c.getMongoPort()), this.credential, this.clientOptions);
	
			this.store = morphia.createDatastore(this.client, c.getMongoDatabase());
			this.store.ensureIndexes();

			if (Utils.isBukkit()) CommonsPluginBukkit.get().logger().send(LogLevel.SUCCESS, "Mongo", "Connected to Mongo Server!");
			else CommonsPluginBungee.get().logger().send(LogLevel.SUCCESS, "Mongo", "Connected to Mongo Server!");
		} catch (Exception e) {
			if (Utils.isBukkit()) {
				CommonsPluginBukkit.get().logger().send(LogLevel.ERROR, "Mongo", "Error while connecting to the Mongo Server.");
				CommonsPluginBukkit.get().logger().send(LogLevel.ERROR, "Mongo", "Please check out the configuration file or enable the printStackTrace setting.");
				if (CommonsPluginBukkit.get().config.getPrintStackTrace()) e.printStackTrace();
				if (CommonsPluginBukkit.get().config.getShowErrorsMessages())
					CommonsPluginBukkit.get().logger().send(LogLevel.ERROR_MESSAGE, "Mongo", "Error message: " + e.toString());
			} else {
				CommonsPluginBungee.get().logger().send(LogLevel.ERROR, "Mongo", "Error while connecting to the Mongo Server.");
				CommonsPluginBungee.get().logger().send(LogLevel.ERROR, "Mongo", "Please check out the configuration file or enable the printStackTrace setting.");
				if (CommonsPluginBungee.get().config.getPrintStackTrace()) e.printStackTrace();
				if (CommonsPluginBungee.get().config.getShowErrorsMessages()) CommonsPluginBungee.get().logger().send(LogLevel.ERROR_MESSAGE, "Mongo", "Error message: " + e.toString());
			}
			CommonsPluginBukkit.get().stop();
		}

	}

	public Morphia getMorphia() {
		return morphia;
	}

	public void setMorphia(Morphia morphia) {
		this.morphia = morphia;
	}

	public Datastore getStore() {
		return store;
	}

	public void setStore(Datastore store) {
		this.store = store;
	}

	public MongoClientOptions getClientOptions() {
		return clientOptions;
	}

	public void setClientOptions(MongoClientOptions clientOptions) {
		this.clientOptions = clientOptions;
	}

	public MongoClient getClient() {
		return client;
	}

	public void setClient(MongoClient client) {
		this.client = client;
	}

	public MongoCredential getCredential() {
		return credential;
	}

	public void setCredential(MongoCredential credential) {
		this.credential = credential;
	}
}