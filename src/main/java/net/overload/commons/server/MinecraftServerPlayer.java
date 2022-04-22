package net.overload.commons.server;

import java.util.UUID;

public class MinecraftServerPlayer {

	private UUID uuid;

	public MinecraftServerPlayer(UUID uuid) {
		super();
		this.uuid = uuid;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
}
