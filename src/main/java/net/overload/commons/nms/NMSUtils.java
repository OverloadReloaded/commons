package net.overload.commons.nms;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NMSUtils {
	private static String version = null;

	public static String getVersion() {
		if (version != null)
			return version;
		String name = null;
		try {
			name = Class.forName("org.bukkit.Bukkit").getDeclaredMethod("getServer", new Class[0])
					.invoke(null, new Object[0]).getClass().getPackage().getName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (name != null)
			return version = name.substring(name.lastIndexOf('.')).replace(".", "");
		return "ERROR_O";
	}

	public static Object getHandle(Object o) {
		try {
			return getMethod(o.getClass(), "getHandle", new Class[0]).invoke(o, new Object[0]);
		} catch (IllegalAccessException | IllegalArgumentException
				| java.lang.reflect.InvocationTargetException illegalAccessException) {
			return null;
		}
	}

	public static Method getMethod(Class<?> clazz, String name, Class<?>... args) {
		for (Method m : clazz.getMethods()) {
			if (m.getName().equals(name)) {
				if ((args.length == 0 && (m.getParameterTypes()).length == 0)
						|| ClassListEqual(args, m.getParameterTypes())) {
					m.setAccessible(true);
					return m;
				}
				System.out.println(
						"Getting the method but doesn't have correct parameters in " + clazz.getCanonicalName());
				System.out.println("Parameters are ");
				for (Class<?> i : m.getParameterTypes())
					System.out.println("   - " + i.getName());
				System.out.println("But gived : ");
				for (Class<?> i : args)
					System.out.println("   - " + i.getName());
			}
		}
		System.out.println("Unable to find the method " + name + " in " + clazz.getName());
		return null;
	}

	public static Field getField(Class<?> clazz, String name) {
		Field[] arrayOfField;
		int j = (arrayOfField = clazz.getFields()).length;
		int i;
		for (i = 0; i < j; i++) {
			Field f = arrayOfField[i];
			if (f.getName().equalsIgnoreCase(name)) {
				f.setAccessible(true);
				return f;
			}
		}
		j = (arrayOfField = clazz.getDeclaredFields()).length;
		for (i = 0; i < j; i++) {
			Field f = arrayOfField[i];
			if (f.getName().equalsIgnoreCase(name)) {
				f.setAccessible(true);
				return f;
			}
		}
		try {
			throw new Exception("No such field > " + name + " in class " + clazz.getSimpleName());
		} catch (Exception exception) {
			return null;
		}
	}

	public static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
		boolean equal = true;
		if (l1.length != l2.length)
			return false;
		for (int i = 0; i < l1.length; i++) {
			if (l1[i] != l2[i]) {
				equal = false;
				break;
			}
		}
		return equal;
	}

	public static void setField(Object instance, Field f, Object value) {
		try {
			f.set(instance, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static Object invokeMethod(Object instance, Method m, Object... args) {
		try {
			return m.invoke(instance, args);
		} catch (IllegalAccessException | IllegalArgumentException
				| java.lang.reflect.InvocationTargetException illegalAccessException) {
			return null;
		}
	}

	public static Object invokeMethod(Object instance, Method m) {
		try {
			return m.invoke(instance, new Object[0]);
		} catch (IllegalAccessException | IllegalArgumentException
				| java.lang.reflect.InvocationTargetException illegalAccessException) {
			return null;
		}
	}

	public static Class<?> getNMSClass(String name) {
		try {
			return getClassWithException(name);
		} catch (ClassNotFoundException e) {
			try {
				return getCraftClassWithException(name);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
				return null;
			}
		}
	}

	private static Class<?> getClassWithException(String name) throws ClassNotFoundException {
		String classname = "net.minecraft.server." + getVersion() + "." + name;
		return Class.forName(classname);
	}

	private static Class<?> getCraftClassWithException(String name) throws ClassNotFoundException {
		String classname = "org.bukkit.craftbukkit." + getVersion() + "." + name;
		return Class.forName(classname);
	}

	public static Class<?> getMojangClass(String name) {
		try {
			String classname = "com.mojang.authlib." + name;
			return Class.forName(classname);
		} catch (ClassNotFoundException e) {
			String classname = "net.minecraft.util.com.mojang.authlib." + name;
			try {
				return Class.forName(classname);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
				e.printStackTrace();
				return null;
			}
		}
	}
}
