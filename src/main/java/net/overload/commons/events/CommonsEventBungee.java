package net.overload.commons.events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.overload.commons.CommonsPluginBungee;
import net.overload.commons.players.objects.Profile;

public class CommonsEventBungee implements Listener {

	private CommonsPluginBungee plugin = CommonsPluginBungee.get();
	
	@EventHandler
	public void onLogin(PostLoginEvent event) {
		ProxiedPlayer p = event.getPlayer();
		
		plugin.getProfileManager().handleProfileCreation(p.getUniqueId().toString());
		Profile profile = plugin.getProfileManager().getProfile(p.getUniqueId());
		profile.save();
	}
	
	@EventHandler
	public void onLogout(PlayerDisconnectEvent event) {
		ProxiedPlayer p = event.getPlayer();
		plugin.getProfileManager().remove(p.getUniqueId());
	}
}
