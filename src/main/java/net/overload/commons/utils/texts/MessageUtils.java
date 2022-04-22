package net.overload.commons.utils.texts;

public enum MessageUtils {

	LOBBY_SUCCESSFULL_SEND(PrefixUtils.LOBBY, "§aTeleported to: §6%s§a."),
	LOBBY_NO_SERVERS(PrefixUtils.LOBBY, "§cNo Lobby server found. Try again in a moment!");
	
	private PrefixUtils prefix;
	private String message;
	
	private MessageUtils(PrefixUtils prefix, String message) {
		this.prefix = prefix;
		this.message = message;
	}

	public PrefixUtils getPrefix() {
		return prefix;
	}

	public void setPrefix(PrefixUtils prefix) {
		this.prefix = prefix;
	}

	public String getMessage() {
		return this.prefix.getPrefix() + this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}

class MessageFactory {

	private PrefixUtils prefix;
	private String message;

	public MessageFactory(PrefixUtils prefix, String message) {
		super();
		this.prefix = prefix;
		this.message = message;
	}

	public PrefixUtils getPrefix() {
		return prefix;
	}

	public void setPrefix(PrefixUtils prefix) {
		this.prefix = prefix;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

