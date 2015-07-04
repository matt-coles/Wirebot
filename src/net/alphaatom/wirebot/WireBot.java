package net.alphaatom.wirebot;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

public class WireBot extends PircBot {
	
	protected final static String password = "oauth:5uu4jdazasnz57xh35spcmrkq5joxq";
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
				this.reportBadThing(50);
				e.printStackTrace();
			}
		} else {
			bannedWords = new HashMap<String, ArrayList<String>>();
		}
		commandHandler = new CommandHandler(this);
	}
	
	@Override
	public void onUnknown(String line) {
		Map<String, String> cmdInfo;
		String lineArray[], userInfoArray[], senderInfoArray[], commandArgs[], twitchCmdType, 
		       userInfo, sender, channel, message, infoName, infoValue, command;
		if (line.startsWith("@")) {
			lineArray = line.split(" ");
			twitchCmdType = lineArray[2];
			if (twitchCmdType.equals("ROOMSTATE")) {
				//ROOMSTATE change?
			} else if (twitchCmdType.equals("PRIVMSG")) {
				userInfo = lineArray[0].substring(1);
				sender = lineArray[1];
				channel = lineArray[3];
				message = lineArray[4].substring(1);
				
				// Command Handling Begin
				if (message.startsWith(this.getPrefix())) {
					cmdInfo = new HashMap<String, String>();
					userInfoArray = userInfo.split(";");
					for (String info : userInfoArray) {
						infoName = info.split("=")[0];
						infoValue = (info.split("=").length > 1) ? info.split("=")[1] : ""; //empty string if we don't know
						cmdInfo.put(infoName, infoValue);
					}
					senderInfoArray = sender.split("!");
					cmdInfo.put("senderName", senderInfoArray[0]);
					cmdInfo.put("senderLogin", senderInfoArray[1].split("@")[0]);
					cmdInfo.put("senderHost", senderInfoArray[1].split("@")[1]);
					cmdInfo.put("channel", channel);
					command = message.split(" ")[0].substring(1);
					commandArgs = this.joinUpArrayFrom(line.split(" "), 5, ' ').split(" ");
					commandHandler.processCommand(command, cmdInfo, commandArgs, this);
				} // Command Handling End
				
			} else if (twitchCmdType.equals("USERSTATE")) {
				//USERSTATE change??
			}
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
	
	public ArrayList<String> getBannedWords(String channel) {
		if (bannedWords.containsKey(channel)) {
			return bannedWords.get(channel);
		} else {
			return new ArrayList<String>();
		}
	}
	
	public User getUserInChannel(String channel, String user) {
		for (User u : this.getUsers(channel)) {
			if (u.getNick().equalsIgnoreCase(user)) {
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
	
	public String getPrefix() {
		return prefix;
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
