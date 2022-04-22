package net.overload.commons.players.objects;

import java.util.Date;

import net.overload.commons.players.enums.PunishmentType;

public class Punishment {
	
	private String punishmentUuid;
	
	private String victimUuid;
	private String punisherUuid;
	
	private String serverName;
	
	private PunishmentType type;
	private Date executedAt;
	private Date expireAt;
	private String reason;

	public Punishment() {}
	
	public Punishment(String punishmentUuid, String victimUuid, String punisherUuid, String serverName,
			PunishmentType type, Date executedAt, Date expireAt, String reason) {
		super();
		this.punishmentUuid = punishmentUuid;
		this.victimUuid = victimUuid;
		this.punisherUuid = punisherUuid;
		this.serverName = serverName;
		this.type = type;
		this.executedAt = executedAt;
		this.expireAt = expireAt;
		this.reason = reason;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public PunishmentType getType() {
		return type;
	}

	public void setType(PunishmentType type) {
		this.type = type;
	}

	public Date getExecutedAt() {
		return executedAt;
	}

	public void setExecutedAt(Date executedAt) {
		this.executedAt = executedAt;
	}

	public Date getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(Date expireAt) {
		this.expireAt = expireAt;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPunishmentUuid() {
		return punishmentUuid;
	}

	public void setPunishmentUuid(String punishmentUuid) {
		this.punishmentUuid = punishmentUuid;
	}

	public String getVictimUuid() {
		return victimUuid;
	}

	public void setVictimUuid(String victimUuid) {
		this.victimUuid = victimUuid;
	}

	public String getPunisherUuid() {
		return punisherUuid;
	}

	public void setPunisherUuid(String punisherUuid) {
		this.punisherUuid = punisherUuid;
	}
}
