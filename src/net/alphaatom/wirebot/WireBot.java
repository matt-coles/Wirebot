package net.alphaatom.wirebot;

import org.jibble.pircbot.PircBot;

public class WireBot extends PircBot {
	
	protected final static String password = "oauth:3fjpl3rg7qcd66rhb0itna60k98alb";
	private final String prefix = "!";
	private final CommandHandler commandHandler;
	private final int major = 0;
	private final int minor = 0;
	private final int revision = 0;
	private final int build = 1;
	
	public WireBot() {
		setName("WireBot");
		System.out.println("wirebot has been started");
		System.out.println("this is version " + major + "." + minor + "." + revision + "#-" + build);
		commandHandler = new CommandHandler(this);
	}
	
	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		if (message.startsWith(prefix)) {
			String messageArray[] = message.split(" ");
			String commandInformationArray[] = { channel, sender, login, hostname };
			String commandArgumentArray[] = joinUpArrayFrom(messageArray, 1, ' ').split(" ");
			commandHandler.processCommand(messageArray[0].substring(1), commandInformationArray, commandArgumentArray, this);
		}
	}
	
	@Override
	public void onJoin(String channel, String sender, String login, String hostname) {
		if (sender.equalsIgnoreCase("WireBot")) {
			this.sendMessage(channel, "WireBot has arrived!");
		}
	}
	
	public void reportBadThing(int badnessLevel) {
		System.err.print("something ");
		for (int i = 0; i < badnessLevel; i++) {
			System.err.print(" really");
		}
		System.err.println(" bad happened.");
	}
	
	public String joinUpArrayFrom(String[] array, int beginIndex, char inbetween) {
		if (!(array.length >= beginIndex)) {
			throw new IndexOutOfBoundsException();
		} else {
			String str = "";
			for (int i = beginIndex; i < array.length; i++) {
				str = str + array[i] + inbetween;
			}
			return str;
		}
	}
	
}
