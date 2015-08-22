package net.alphaatom.wirebot.logger;

public enum Level {
	
	BABBLE(3, "BABBLE"),
	DEBUG(2, "DEBUG"),
	INFO(1, "INFO"),
	WARNING(0, "WARNING"),
	ERROR(0, "ERROR");
	
	private int logValue;
	private String identifier;
	
	private Level(int value, String id) {
		this.logValue = value;
		this.identifier = id;
	}
	
	public int getValue() {
		return this.logValue;
	}
	
	public String getIdentifier() {
		return this.identifier;
	}
	
}
