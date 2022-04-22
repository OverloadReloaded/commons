package net.overload.commons.utils.texts;

public class PrefixParser {

	private PrefixUtils prefix;
	private String message;

	public PrefixParser(PrefixUtils prefix, String message) {
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
