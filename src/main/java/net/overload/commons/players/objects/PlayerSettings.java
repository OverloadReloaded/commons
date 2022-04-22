package net.overload.commons.players.objects;

import org.bson.types.ObjectId;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("PlayerSettings")
public class PlayerSettings {
	
	@Id
	private ObjectId id;
	
	private Boolean showPlayers = true;
	private Boolean hearPrivateMessages = true;
	private Boolean hearFriendInvitation = true;
	private Boolean hearPartyInvitation = true;
	private Boolean acceptPrivateMessages = true;
	private Boolean acceptFriendInvitation = true;
	private Boolean acceptPartyInvitation = true;
	private Boolean hearChatMention = true;
	private Boolean showJoinMessageInLobby = true;
	
	
	public Boolean getShowPlayers() {
		return showPlayers;
	}

	public void setShowPlayers(Boolean showPlayers) {
		this.showPlayers = showPlayers;
	}

	public Boolean getHearPrivateMessages() {
		return hearPrivateMessages;
	}

	public void setHearPrivateMessages(Boolean hearPrivateMessages) {
		this.hearPrivateMessages = hearPrivateMessages;
	}

	public Boolean getHearFriendInvitation() {
		return hearFriendInvitation;
	}

	public void setHearFriendInvitation(Boolean hearFriendInvitation) {
		this.hearFriendInvitation = hearFriendInvitation;
	}

	public Boolean getHearPartyInvitation() {
		return hearPartyInvitation;
	}

	public void setHearPartyInvitation(Boolean hearPartyInvitation) {
		this.hearPartyInvitation = hearPartyInvitation;
	}

	public Boolean getAcceptPrivateMessages() {
		return acceptPrivateMessages;
	}

	public void setAcceptPrivateMessages(Boolean acceptPrivateMessages) {
		this.acceptPrivateMessages = acceptPrivateMessages;
	}

	public Boolean getAcceptFriendInvitation() {
		return acceptFriendInvitation;
	}

	public void setAcceptFriendInvitation(Boolean acceptFriendInvitation) {
		this.acceptFriendInvitation = acceptFriendInvitation;
	}

	public Boolean getAcceptPartyInvitation() {
		return acceptPartyInvitation;
	}

	public void setAcceptPartyInvitation(Boolean acceptPartyInvitation) {
		this.acceptPartyInvitation = acceptPartyInvitation;
	}

	public Boolean getHearChatMention() {
		return hearChatMention;
	}

	public void setHearChatMention(Boolean hearChatMention) {
		this.hearChatMention = hearChatMention;
	}

	public Boolean getShowJoinMessageInLobby() {
		return showJoinMessageInLobby;
	}

	public void setShowJoinMessageInLobby(Boolean showJoinMessageInLobby) {
		this.showJoinMessageInLobby = showJoinMessageInLobby;
	}	
	
}
