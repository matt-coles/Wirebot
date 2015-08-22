package net.alphaatom.wirebot;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jibble.pircbot.PircBot;

import net.alphaatom.wirebot.logger.Level;
import net.alphaatom.wirebot.logger.WireLogger;

public class WireBot extends PircBot {
	
	protected final static String password = "oauth:5uu4jdazasnz57xh35spcmrkq5joxq";
	protected HashMap<String, ArrayList<String>> bannedWords;
	protected HashMap<String, HashMap<String, String>> channelStates;
	private final String prefix = "!";
	private final CommandHandler commandHandler;
	
	/**
	 * Note on version numbers
	 * 
	 * Major is incremented on large code rewrites
	 * Minor is incremented on large feature implementation
	 * Revision is incremented on small feature implementation
	 * Build is incremented on each successful build during feature implementation
	 * 
	 * Each lower value is reset to zero when the value above it is incremented and version number will not
	 * be incremented until first development release.
	 * 
	 */
	private final int major = 0; 
	private final int minor = 0; 
	private final int revision = 0; 
	private final int build = 1; 
	
	public WireLogger logger;
	
	@SuppressWarnings("unchecked")
	public WireBot() {
		logger.log(Level.INFO, "Starting new WireBot instance.", this.getClass());
		setName("WireBot");
		logger.log(Level.BABBLE, "this is version " + major + "." + minor + "." + revision + "#-" + build, this.getClass());
		if ((new File("bannedwords.bin").exists())) {
			try {
				logger.log(Level.BABBLE, "Loading banned words file into HashMap.", this.getClass());
				bannedWords = (HashMap<String, ArrayList<String>>) SLAPI.load("bannedwords.bin");
			} catch (Exception e) {
				logger.log(Level.WARNING, "Banned words file is corrupted.", this.getClass());
				e.printStackTrace();
			}
		} else {
			logger.log(Level.DEBUG, "Creating new banned words HashMap.", this.getClass());
			bannedWords = new HashMap<String, ArrayList<String>>();
		}
		logger.log(Level.BABBLE, "Instantiating new CommandHandler.", this.getClass());
		commandHandler = new CommandHandler(this);
	}
	
	@Override
	public void onUnknown(String line) {
		Map<String, String> cmdInfo;
		String lineArray[], userInfoArray[], senderInfoArray[], channelInfoArray[], commandArgs[], 
			   twitchCmdType, userInfo, sender, channel, message, infoName, infoValue, 
			   command, channelInfo;
		if (line.startsWith("@")) {
			lineArray = line.split(" ");
			twitchCmdType = lineArray[2];
			if (twitchCmdType.equals("ROOMSTATE")) {
				logger.log(Level.DEBUG, "Processing ROOMSTATE command", this.getClass());
				channelInfo = lineArray[0].substring(1);
				sender = lineArray[1].substring(1);
				channel = lineArray[3];
				
				// Room state handling begin
				channelInfoArray = channelInfo.split(";");
				HashMap<String, String> states;
				for (String chinfo : channelInfoArray) {
					if (channelStates.containsKey(channel)) {
						states = channelStates.get(channel);
					} else {
						states = new HashMap<String, String>();
					}
					states.put(chinfo.split("=")[0], chinfo.split("=").length > 1 ? chinfo.split("=")[1] : "");
					channelStates.put(channel, states);
				} //Room state handling end
				
			} else if (twitchCmdType.equals("PRIVMSG")) {
				logger.log(Level.BABBLE, "Procesing PRIVMSG command", this.getClass()); //Babble to prevent console spam
				userInfo = lineArray[0].substring(1);
				sender = lineArray[1].substring(1);
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
					cmdInfo.put("sender-name", senderInfoArray[0]);
					cmdInfo.put("sender-login", senderInfoArray[1].split("@")[0]);
					cmdInfo.put("sender-host", senderInfoArray[1].split("@")[1]);
					cmdInfo.put("channel", channel);
					command = message.split(" ")[0].substring(1);
					commandArgs = this.joinUpArrayFrom(line.split(" "), 5, ' ').split(" ");
					commandHandler.processCommand(command, cmdInfo, commandArgs, this);
				} // Command Handling End
				
			} else if (twitchCmdType.equals("USERSTATE")) {
				//USERSTATE change??
				logger.log(Level.DEBUG, "Processing USERSTATE command.", this.getClass());
				logger.log(Level.WARNING, "Nothing to do for USERSTATE command.", this.getClass());
			}
		}
	}
	
	@Override
	public void onJoin(String channel, String sender, String login, String hostname) {
		logger.log(Level.BABBLE, "Processing JOIN command.", this.getClass());
		if (sender.equalsIgnoreCase("WireBot")) {
			this.sendMessage(channel, "WireBot has arrived!");
		}
	}
	
	public void addBannedWord(String channel, String bannedWord) {
		if (!bannedWords.containsKey(channel)) {
			logger.log(Level.INFO, "Adding " + bannedWord + " to banned word list.", this.getClass());
			bannedWords.put(channel, new ArrayList<String>());
		}
		if (!bannedWords.get(channel).contains(bannedWord)) {
			logger.log(Level.INFO, "Adding " + bannedWord + " to banned word list.", this.getClass());
			bannedWords.get(channel).add(bannedWord);
		}
		try {
			SLAPI.save(bannedWords, "bannedwords.bin");
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed to save banned words file.", this.getClass());
			e.printStackTrace();
		}
	}
	
	public void removeBannedWord(String channel, String bannedWord) {
		if (bannedWords.containsKey(channel) && bannedWords.get(channel).contains(bannedWord)) {
			logger.log(Level.INFO, "Removing " + bannedWord + " from banned word list.", this.getClass());
			bannedWords.get(channel).remove(bannedWord);
		}
		try {
			SLAPI.save(bannedWords, "bannedwords.bin");
		} catch (Exception e) {
			logger.log(Level.WARNING, "Failed to save banned words file.", this.getClass());
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
	
	public String getPrefix() {
		return prefix;
	}

	public String joinUpArrayFrom(String[] array, int beginIndex, char inbetween) {
		if (!(array.length >= beginIndex)) {
			logger.log(Level.ERROR, "Internal Error, tried to format too small array.", this.getClass());
			throw new RuntimeException(); //this code will never be reached but removes compiler error
		} else {
			String str = "";
			for (int i = beginIndex; i < array.length; i++) {
				str = str + array[i] + inbetween;
			}
			return str;
		}
	}
	
}
