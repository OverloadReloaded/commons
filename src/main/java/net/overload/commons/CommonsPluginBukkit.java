package net.overload.commons;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import net.overload.commons.config.OverloadConfiguration;
import net.overload.commons.databases.OverloadRedis;
import net.overload.commons.databases.mongo.OverloadMongoDatabase;
import net.overload.commons.events.CommonsEventBukkit;
import net.overload.commons.logger.LogLevel;
import net.overload.commons.logger.Logger;
import net.overload.commons.nms.NMSUtils;
import net.overload.commons.nms.ReflectionManager;
import net.overload.commons.players.managers.PermissionManager;
import net.overload.commons.players.managers.ProfileManager;
import net.overload.commons.players.managers.RankPermissionsManager;
import net.overload.commons.players.permissions.PermissionCheck;
import net.overload.commons.server.MinecraftServerInfo;
import net.overload.commons.server.MinecraftServerState;
import net.overload.commons.server.MinecraftServerType;

public class CommonsPluginBukkit extends JavaPlugin {

	private static CommonsPluginBukkit instance;

	public Logger log = new Logger("Commons");

	public OverloadConfiguration config;
	public OverloadRedis redis;
	public OverloadMongoDatabase database;

	public MinecraftServerInfo msi;
	public ProfileManager pm;
	public PermissionManager perms;
	public RankPermissionsManager pmm;

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
		runServerUpdate();
		definePermissionMessage();
		checkConnections();

		msi = new MinecraftServerInfo(Bukkit.getServerName(), Bukkit.getPort(), MinecraftServerState.STARTING, MinecraftServerType.NONE, Bukkit.getMaxPlayers(), 0, new ArrayList<>(), new HashMap<>());
		pm = new ProfileManager(this);
		perms = new PermissionManager();
		pmm = new RankPermissionsManager();
		pmm.init();

		getServer().getPluginManager().registerEvents(new CommonsEventBukkit(), this);
		// this.getCommand("loc").setExecutor(new LocCommand());

		ProtocolLibrary.getProtocolManager().addPacketListener(getTabCompleteListener());

		log.send(LogLevel.SUCCESS, "Enabled Commons plugin !");
	}

	@Override
	public void onDisable() {
		log.send(LogLevel.INFO, "Disabling Commons plugin!");
	}

	
	/**
	 * Custom functions
	 */
	
	public void stop() {
		/*redis.getJedis().publish("deleteServer", Bukkit.getServerName());
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		System.exit(0);
	}

	private void loadConfig() {
		try {
			config = new OverloadConfiguration("../../redis.properties");
			config.check();
		} catch (Exception e) {
			logger().send(LogLevel.CRITICAL, "Config",
					"Could not load the configuration file. Please contact administrators or developers.");
			if (config.getDebug())
				logger().send(LogLevel.DEBUG, "Config", e.getStackTrace().toString());
		}
	}

	@SuppressWarnings("deprecation")
	private void startRedis() {
		try {
			redis = new OverloadRedis();
			redis.connect(true);

			new Thread(new Runnable() {
				public void run() {
					redis.subscribeCommons();
				}
			}).start();
			
		} catch (Exception e) {
			logger().send(LogLevel.CRITICAL, "Redis", "Error with Redis Please contact administrators or developers.");
			logger().send(LogLevel.STACK_TRACE, "Redis", e.getStackTrace().toString());
		}
		logger().line();
		
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				redis.connect(false);
			}
		}, 5*20, 5*20);
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

	private void runServerUpdate() {
		BukkitScheduler scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				msi.update(false);
			}
		}, 60L, 60L);
	}

	public PacketAdapter getTabCompleteListener() {
		return new PacketAdapter(this, new PacketType[] { PacketType.Play.Client.TAB_COMPLETE }) {
			public void onPacketReceiving(PacketEvent event) {
				Player player = event.getPlayer();
				if (event.getPacketType().equals(PacketType.Play.Client.TAB_COMPLETE)) {
					String message = (String) event.getPacket().getStrings().read(0);
					if (message.startsWith("/")) {
						String command = message.split(" ")[0].substring(1).toLowerCase();
						PacketContainer completions = new PacketContainer(PacketType.Play.Server.TAB_COMPLETE);
						ArrayList<String> cmds = new ArrayList<>();
						for (Plugin pl : Bukkit.getPluginManager().getPlugins()) {
							if (pl.getDescription() != null) {
								if (pl.getDescription().getCommands() != null) {
									for (String cmd : pl.getDescription().getCommands().keySet()) {
										if(pm.getProfile(player.getUniqueId()) != null) {
											/*if (pmm.getBukkitPermsFromRank(pm.getProfile(player.getUniqueId()).getData().getRank()).contains((pl.getName() + "." + cmd).toLowerCase())) {
												cmds.add("/" + cmd);
											}*/
											if(new PermissionCheck(player).hasPermission(cmd)) {
												cmds.add(cmd);
											}
										}
									}
								}
							}
						}
						completions.getStringArrays().write(0, cmds.toArray(new String[cmds.size()]));
						if (!message.contains(" ")) {
							try {
								ProtocolLibrary.getProtocolManager().sendServerPacket(player, completions);
								event.setCancelled(true);
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						} else if (!cmds.contains(command)) {
							event.setCancelled(true);
						}
					}
				}
			}
		};
	}

	@SuppressWarnings("unchecked")
	private void definePermissionMessage() {
		try {
			Field map = NMSUtils.getField(Bukkit.getPluginManager().getClass(), "commandMap");
			Method m = ReflectionManager.getMethod(map.getType(), "getCommands", new Class[0]);
			Collection<Command> knownCommands = (Collection<Command>) m.invoke(ReflectionManager.getValue(Bukkit.getPluginManager(), true, "commandMap"), new Object[0]);
			
			Integer count = 0;
			
			for (Command cmd : knownCommands) {
				if (cmd != null) {
					count++;
					cmd.setPermissionMessage(ChatColor.WHITE + "Unknown command. Type \"/help\" for help.");
				}
			}
			System.out.println("command Registered = " + count);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		for (Plugin pl : Bukkit.getPluginManager().getPlugins()) {
			try {
				if (pl != null && pl.getDescription().getCommands() != null && (pl.getName().contains("obby") || pl.getName().contains("ersion") || pl.getName().contains("orld") || pl.getName().contains("rotocol") || pl.getName().contains("wrapp"))) {
					for (Map.Entry<String, Map<String, Object>> cmd : (Iterable<Map.Entry<String, Map<String, Object>>>) pl .getDescription().getCommands().entrySet()) {
						if (getServer().getPluginCommand(cmd.getKey()) != null) {
							System.out.println("Ancienne : " + Bukkit.getPluginCommand(cmd.getKey()).getPermission());
							Bukkit.getPluginCommand(cmd.getKey()).setPermission(String.valueOf(pl.getName() + "." + cmd.getKey()).toLowerCase());
							System.out.println(" -> Nouvelle : " + Bukkit.getPluginCommand(cmd.getKey()).getPermission());
							System.out.println("--------------");
						} else {
							System.out.println(cmd + " est nulle !");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/*for (Plugin pl : Bukkit.getPluginManager().getPlugins()) {
			for (Map.Entry<String, Map<String, Object>> cmd : (Iterable<Map.Entry<String, Map<String, Object>>>) pl .getDescription().getCommands().entrySet()) {
				System.out.println(cmd);
			}
		}*/
	}

	/**
	 * Getters & Setters
	 */

	public Logger logger() {
		return log;
	}

	public static CommonsPluginBukkit get() {
		return instance;
	}
}
