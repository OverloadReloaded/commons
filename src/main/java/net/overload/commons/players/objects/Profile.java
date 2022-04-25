package net.overload.commons.players.objects;

import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.NotSaved;
import net.overload.commons.CommonsPluginBukkit;
import net.overload.commons.CommonsPluginBungee;
import net.overload.commons.utils.Utils;

@Entity("Profile")
public class Profile {
	
	@Id
	private String uuid;
	
	@Embedded
    private PlayerData data = new PlayerData(uuid);
	
	
	@NotSaved
	private Object attachment;

	public Profile(String uuid) {
    	this.setUuid(uuid);
    }
    
    public void save() {
    	if(Utils.isBukkit())
    		CommonsPluginBukkit.get().database.getStore().save(this);
    	else
    		CommonsPluginBungee.get().database.getStore().save(this);
    }

	public PlayerData getData() {
		return data;
	}

	public void setData(PlayerData data) {
		this.data = data;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Object getPermissionAttachment() {
		return attachment;
	}

	public void setPermissionAttachment(Object permissionAttachment) {
		this.attachment = permissionAttachment;
	}
}
