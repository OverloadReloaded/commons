package net.overload.commons.logger;

public class Logger {
	private String prefix;

	private String msg = "";

	public Logger(String prefix) {
		this.setPrefix(prefix);
	}

	public void send(String message) {
		System.out.println("\033[33;1m[" + this.getPrefix() + "] \033[0m" + message);
	}

	public void send(LogLevel level, String message) {
		System.out.println("\033[33;1m[" + this.getPrefix() + "] \033[0m" + level.getLevel() + message);
	}

	public void send(LogLevel level, String inBracket, String message) {
		System.out.println("\033[33;1m[" + this.getPrefix() + "] \033[0m\033[33;1m[" + inBracket + "] \033[0m" + level.getLevel() + message);
	}

	public void line() {
		System.out.println(" ");
	}

	public String getMessage() {
		return this.msg;
	}

	public void setMessage(String msg) {
		this.msg = msg;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
