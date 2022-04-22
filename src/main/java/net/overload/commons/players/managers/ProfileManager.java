package net.overload.commons.players.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.overload.commons.CommonsPluginBukkit;
import net.overload.commons.CommonsPluginBungee;
import net.overload.commons.logger.LogLevel;
import net.overload.commons.players.objects.Profile;
import net.overload.commons.utils.Utils;

public class ProfileManager {

	private Map<UUID, Profile> profiles = new HashMap<>();

	public ProfileManager(CommonsPluginBukkit plugin) {
		super();
	}
	public ProfileManager(CommonsPluginBungee plugin) {
		super();
	}

	public Profile handleProfileCreation(String uuid) {
		if (!this.profiles.containsKey(UUID.fromString(uuid))) {
			Profile p = new Profile(uuid);
			try {
				if (Utils.isBukkit())
					p = CommonsPluginBukkit.get().database.getStore().createQuery(Profile.class).field("uuid").contains(uuid.toString()).find().next();
				else
					p = CommonsPluginBungee.get().database.getStore().createQuery(Profile.class).field("uuid").contains(uuid.toString()).find().next();
			} catch (Exception e) {
				p.getData().addPermission("join.message");
				if(Utils.isBukkit()) {
					CommonsPluginBukkit.get().database.getStore().save(p);
					CommonsPluginBukkit.get().logger().send(LogLevel.ERROR, "Can't find " + uuid.toString() + " to database, creating a new one.");
				} else {
					CommonsPluginBungee.get().database.getStore().save(p);
					CommonsPluginBungee.get().logger().send(LogLevel.ERROR, "Can't find " + uuid.toString() + " to database, creating a new one.");
				}
			}
			profiles.put(UUID.fromString(uuid), p);
			return p;
		}
		return null;
	}
	
	public void remove(UUID uuid) {
		save(uuid);
		if(this.profiles.containsKey(uuid)) this.profiles.remove(uuid);
	}
	
	public void save(UUID uuid) {
		if(Utils.isBukkit()) 
		CommonsPluginBukkit.get().database.getStore().save(getProfile(uuid));
	else 
		CommonsPluginBungee.get().database.getStore().save(getProfile(uuid));
	}

	public Profile getProfile(Object object) {
		if(Utils.isBukkit()) {
			if (object instanceof Player) {
				Player target = (Player) object;
				if (!this.profiles.containsKey(target.getUniqueId())) {
					return null;
				}
				return profiles.get(target.getUniqueId());
			}
		} else {
			if(object instanceof ProxiedPlayer) {
				ProxiedPlayer target = (ProxiedPlayer) object;
				if (!this.profiles.containsKey(target.getUniqueId())) {
					return null;
				}
				return profiles.get(target.getUniqueId());
			}
		}
		
		if (object instanceof UUID) {
			UUID uuid = (UUID) object;
			if (!this.profiles.containsKey(uuid)) {
				return null;
			}
			return profiles.get(uuid);
		}
		
		if (object instanceof String) {
			UUID uuid = UUID.fromString((String) object);
			if (!this.profiles.containsKey(uuid)) {
				return null;
			}
			return profiles.get(uuid);
		}
		return null;
	}

	public Map<UUID, Profile> getProfiles() {
		return this.profiles;
	}

}
