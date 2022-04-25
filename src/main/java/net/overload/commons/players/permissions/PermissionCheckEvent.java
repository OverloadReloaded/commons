package net.overload.commons.players.permissions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PermissionCheckEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();

	private Player player;

	private String permission;

	private boolean hasPerm;

	public PermissionCheckEvent(Player player, String permission, boolean hasPerm) {
		this.player = player;
		this.permission = permission.toLowerCase();
		this.hasPerm = hasPerm;
	}

	public boolean hasPerm() {
		return this.hasPerm;
	}

	public void setHasPerm(boolean hasPerm) {
		this.hasPerm = hasPerm;
	}

	public Player getPlayer() {
		return this.player;
	}

	public String getPermission() {
		return this.permission;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
