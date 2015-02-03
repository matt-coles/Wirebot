package net.alphaatom.wirebot;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

public class WireBot extends PircBot {
	
	protected final static String password = "oauth:3fjpl3rg7qcd66rhb0itna60k98alb";
	protected HashMap<String, ArrayList<String>> bannedWords;
	private final String prefix = "!";
	private final CommandHandler commandHandler;
	private final int major = 0;
	private final int minor = 0;
	private final int revision = 0;
	private final int build = 1;
	
	@SuppressWarnings("unchecked")
	public WireBot() {
		setName("WireBot");
		System.out.println("wirebot has been started");
		System.out.println("this is version " + major + "." + minor + "." + revision + "#-" + build);
		if ((new File("bannedwords.bin").exists())) {
			try {
				bannedWords = (HashMap<String, ArrayList<String>>) SLAPI.load("bannedwords.bin");
			} catch (Exception e) {
				this.reportBadThing(40);
				e.printStackTrace();
			}
		} else {
			bannedWords = new HashMap<String, ArrayList<String>>();
		}
		commandHandler = new CommandHandler(this);
	}
	
	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		User user = getUserInChannel(channel, sender);
		if (!user.isOp()) {
			for (String s : bannedWords.get(channel.substring(1))) {
				if (message.contains(s)) {
					this.sendMessage(channel, ".timeout " + sender + " 5");
				}
			}
		}
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
	
	public void addBannedWord(String channel, String bannedWord) {
		if (!bannedWords.containsKey(channel)) {
			bannedWords.put(channel, new ArrayList<String>());
		}
		if (!bannedWords.get(channel).contains(bannedWord)) {
			bannedWords.get(channel).add(bannedWord);
		}
		try {
			SLAPI.save(bannedWords, "bannedwords.bin");
		} catch (Exception e) {
			this.reportBadThing(50);
			e.printStackTrace();
		}
	}
	
	public void removeBannedWord(String channel, String bannedWord) {
		if (bannedWords.containsKey(channel) && bannedWords.get(channel).contains(bannedWord)) {
			bannedWords.get(channel).remove(bannedWord);
		}
		try {
			SLAPI.save(bannedWords, "bannedwords.bin");
		} catch (Exception e) {
			this.reportBadThing(50);
			e.printStackTrace();
		}
	}
	
	public User getUserInChannel(String channel, String user) {
		for (User u : this.getUsers(channel)) {
			if (u.getNick().equals(user)) {
				return u;
			}
		}
		this.reportBadThing(2);
		return null;
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
			this.reportBadThing(5);
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
