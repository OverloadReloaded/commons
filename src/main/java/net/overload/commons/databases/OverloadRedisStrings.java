package net.overload.commons.databases;

public enum OverloadRedisStrings {

	SERVER_KEEP_ALIVE("MinecraftServerInfo:%s:keepAlive"),
	SERVER_LIST("MinecraftServerInfo:%s:info"),
	SERVER_LIST_WATERFALL("MinecraftServerInfo:waterfall"),
	SERVER_LIST_MINECRAFT("MinecraftServerInfo:minecraft");
	
	private String key;

	private OverloadRedisStrings(String key) {
		this.key = key;
	}
	
	public String s() {
		return key;
	}
	
}
