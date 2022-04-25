package net.overload.commons.events;

import java.lang.reflect.Field;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.overload.commons.CommonsPluginBukkit;
import net.overload.commons.nms.ReflectionManager;
import net.overload.commons.players.enums.Rank;
import net.overload.commons.players.objects.Profile;
import net.overload.commons.players.permissions.PermissionCheck;

public class CommonsEventBukkit implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Profile profile = CommonsPluginBukkit.get().pm.handleProfileCreation(e.getPlayer().getUniqueId().toString());
		CommonsPluginBukkit.get().perms.getPlayerAttachment(e.getPlayer().getUniqueId().toString());
		
		if (profile.getData().getRank() != Rank.NONE) {
			if (profile.getData().getSettings().getShowJoinMessageInLobby()) {
				e.setJoinMessage("§a§b➔ " + profile.getData().getRank().getPrefix() + e.getPlayer().getName() + " §eà rejoint le Hub !");
			}
		} else {
			e.setJoinMessage(null);
		}

		try {
			Field field = ReflectionManager.getField("CraftHumanEntity", ReflectionManager.PackageType.CRAFTBUKKIT_ENTITY, true, "perm");
			Field modifiersField = field.getClass().getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
			field.set(e.getPlayer(), new PermissionCheck(e.getPlayer()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		/*for (String perm : CommonsPluginBukkit.get().pmm.getBukkitPermsFromRank(profile.getData().getRank())) {
			e.getPlayer().sendMessage(perm);
			e.getPlayer().addAttachment(CommonsPluginBukkit.get()).setPermission(perm, true);
		}*/
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		CommonsPluginBukkit.get().pm.remove(e.getPlayer().getUniqueId());
	}
}
