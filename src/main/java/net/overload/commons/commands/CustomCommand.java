package net.overload.commons.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.overload.commons.CommonsPluginBukkit;
import net.overload.commons.logger.LogLevel;

public abstract class CustomCommand implements CommandExecutor, TabCompleter {
	protected PluginCommand cmd = null;

	public CustomCommand(String command, JavaPlugin plugin) {
		this(command, null, plugin);
	}

	public CustomCommand(String command, String permission, JavaPlugin plugin) {
		this.cmd = plugin.getCommand(command);
		if (this.cmd != null) {
			if (permission != null && permission.length() > 1)
				this.cmd.setPermission(permission);
			this.cmd.setExecutor(this);
			this.cmd.setTabCompleter(this);
		} else {
			CommonsPluginBukkit.get().logger().send(LogLevel.ERROR_MESSAGE, "§cCan't register the command " + command + " because the command probably not in plugin.yml");
		}
	}

	public void playerExecute(Player p, String cmd, String[] args) {
		p.sendMessage("Unknown command. Type \"/help\" for help.");
	}

	public void consoleExecute(ConsoleCommandSender console, String cmd, String[] args) {
		console.sendMessage("Impossible d'executer cette commande via la console.");
	}

	public void cmdBlockExecute(BlockCommandSender sender, String cmd, String[] args) {
		sender.sendMessage("Impossible d'executer cette commande via un CommandBlock.");
	}

	public void otherExecute(CommandSender sender, String cmd, String[] args) {
		sender.sendMessage("Impossible d'executer cette commande via " + sender.getName() + ".");
	}

	public List<String> onTabComplete(CommandSender sender, String alias, String[] args) {
		return Collections.emptyList();
	}

	public boolean onCommand(CommandSender sender, CustomCommand command, String label, String[] args) {
		if (sender instanceof Player) {
			playerExecute((Player) sender, label, args);
		} else if (sender instanceof ConsoleCommandSender) {
			consoleExecute((ConsoleCommandSender) sender, label, args);
		} else if (sender instanceof BlockCommandSender) {
			cmdBlockExecute((BlockCommandSender) sender, label, args);
		} else {
			otherExecute(sender, label, args);
		}
		return true;
	}

	public List<String> onTabComplete(CommandSender sender, CustomCommand command, String alias, String[] args) {
		return onTabComplete(sender, alias, args);
	}
}
