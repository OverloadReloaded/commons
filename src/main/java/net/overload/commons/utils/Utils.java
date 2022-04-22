package net.overload.commons.utils;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.bson.types.Binary;

import net.overload.commons.CommonsPluginBukkit;
import net.overload.commons.CommonsPluginBungee;
import net.overload.commons.logger.LogLevel;

public class Utils {

	public static void createTextFile(List<String> lines, Path file) {
		try {
			Files.write(file, lines, Charset.forName("UTF-8"), new java.nio.file.OpenOption[0]);
		} catch (Exception e) {
			if (CommonsPluginBukkit.get() != null) {
				CommonsPluginBukkit.get().logger().send(LogLevel.ERROR, "Error while creating file :" + file.toString());
				if (CommonsPluginBukkit.get().config.getPrintStackTrace().booleanValue())
					e.printStackTrace();
				if (CommonsPluginBukkit.get().config.getShowErrorsMessages().booleanValue())
					CommonsPluginBukkit.get().logger().send(LogLevel.ERROR_MESSAGE, "Error message: " + e.toString());
			}

			if (CommonsPluginBungee.get() != null) {
				CommonsPluginBungee.get().logger().send(LogLevel.ERROR, "Error while creating file :" + file.toString());
				if (CommonsPluginBungee.get().config.getPrintStackTrace().booleanValue())
					e.printStackTrace();
				if (CommonsPluginBungee.get().config.getShowErrorsMessages().booleanValue())
					CommonsPluginBungee.get().logger().send(LogLevel.ERROR_MESSAGE, "Error message: " + e.toString());
			}
		}
	}

	public static boolean isClass(String className) {
		try {
			Class.forName(className);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
	
	public static boolean isBukkit() {
		if (Utils.isClass("org.bukkit.plugin.java.JavaPlugin")) return true;
		else return false;
	}
	
	public static Binary toStandardBinaryUUID(UUID uuid) {
	    long msb = uuid.getMostSignificantBits();
	    long lsb = uuid.getLeastSignificantBits();

	    byte[] uuidBytes = new byte[16];

	    for (int i = 15; i >= 8; i--) {
	        uuidBytes[i] = (byte) (lsb & 0xFFL);
	        lsb >>= 8;
	    }
	    
	    for (int i = 7; i >= 0; i--) {
	        uuidBytes[i] = (byte) (msb & 0xFFL);
	        msb >>= 8;
	    }

	    return new Binary((byte) 0x04, uuidBytes);
	}
	
	public static String getCurrentTime() {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    return sdf.format(cal.getTime()).toString();
	  }
}
