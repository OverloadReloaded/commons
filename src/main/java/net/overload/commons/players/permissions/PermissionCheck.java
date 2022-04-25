package net.overload.commons.players.permissions;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.ServerOperator;

import net.overload.commons.CommonsPluginBukkit;
import net.overload.commons.logger.LogLevel;

public class PermissionCheck extends PermissibleBase {
	Player p;

	public PermissionCheck(Player p) {
		super((ServerOperator) p);
		this.p = p;
	}

	public boolean hasPermission(String s) {
		boolean hasPerm = (s == null || isOp());
		try {
			CommonsPluginBukkit.get().pm.handleProfileCreation(p.getUniqueId().toString());
			
			Map<String, Boolean> pa = CommonsPluginBukkit.get().perms.getPlayerAttachment(p.getUniqueId().toString()).getPermissions();
			
			hasPerm = (pa.containsKey("*") || pa.containsKey(s) || s == null || isOp() || children(pa, s));
		} catch (Exception e) {
			if (CommonsPluginBukkit.get().config.getShowErrorsMessages()) CommonsPluginBukkit.get().logger().send(LogLevel.ERROR_MESSAGE, "Common", "Error while hasPermission : " + e.toString());
			if (CommonsPluginBukkit.get().config.getPrintStackTrace()) e.printStackTrace();
		}
		
		try {
			PermissionCheckEvent check = new PermissionCheckEvent(this.p, s, hasPerm);
			Bukkit.getPluginManager().callEvent(check);
			return check.hasPerm();
		} catch (Exception ex) {
			ex.printStackTrace();
			return hasPerm;
		}
	}

	public boolean hasPermission(Permission p) {
		return hasPermission(p.getName());
	}

	public boolean children(Map<String, Boolean> pa, String perm) {
		String[] split = perm.split("\\.");
		if (split.length > 0) {
			String s = split[0];
			for (int i = 1; i < split.length; i++) {
				if (pa.containsKey(s + ".*"))
					return true;
				s = s + split[i];
			}
		}
		return false;
	}
}