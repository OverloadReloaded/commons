package net.overload.commons.utils.texts;

public enum PrefixUtils {

	LOBBY("§9§lLobby »§r "),
	SERVER("§9§lServers »§r ");
	
	private String prefix;
	
	
	private PrefixUtils(String prefix) {
		this.prefix = prefix;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
