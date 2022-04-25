package net.overload.commons.players.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionAttachment;

import net.overload.commons.CommonsPluginBukkit;
import net.overload.commons.players.objects.Profile;
import net.overload.commons.utils.Utils;

public class PermissionManager {

	private Map<UUID, PermissionAttachment> attachments = new HashMap<>();
	
	public PermissionManager() {
	}
	
	public PermissionAttachment getPlayerAttachment(String uuid) {
		if(Utils.isBukkit()) {
			if(this.getAttachments().containsKey(UUID.fromString(uuid))) {
				return this.getAttachments().get(UUID.fromString(uuid));
			} else {
				CommonsPluginBukkit.get().pm.handleProfileCreation(uuid);
				Profile profile = CommonsPluginBukkit.get().pm.getProfile(uuid);
				
		    	PermissionAttachment permissionAttachment = Bukkit.getPlayer(UUID.fromString(uuid)).addAttachment(CommonsPluginBukkit.get());
		    	this.addAttachmentIfNotExist(UUID.fromString(uuid), permissionAttachment);
		
		    	for(String permissions : CommonsPluginBukkit.get().pmm.getBukkitPerms().get(profile.getData().getRank())) {
		    		permissionAttachment.setPermission(permissions, true);
		    	}
		    	
		    	for(String permissions : CommonsPluginBukkit.get().pmm.getBungeePerms().get(profile.getData().getRank())) {
		    		permissionAttachment.setPermission(permissions, true);
		    	}
		
		    	for(String permissions : profile.getData().getPermissions()) {
		    		permissionAttachment.setPermission(permissions, true);
		    	}
		    	
		    	if(profile.getData().getPermissions().contains("overload.op")) Bukkit.getPlayer(UUID.fromString(profile.getUuid())).setOp(true);
			    else Bukkit.getPlayer(UUID.fromString(profile.getUuid())).setOp(false);
		    	
		    	return permissionAttachment;
			}
	    	
		} else return null;
	}

	
	public void addAttachmentIfNotExist(UUID uuid, PermissionAttachment attachment) {
		if(this.getAttachments().containsKey(uuid)) return;
		else this.getAttachments().put(uuid, attachment);
	}

	public Map<UUID, PermissionAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Map<UUID, PermissionAttachment> attachments) {
		this.attachments = attachments;
	}
}
