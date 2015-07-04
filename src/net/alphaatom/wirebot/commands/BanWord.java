package net.alphaatom.wirebot.commands;

import java.util.ArrayList;
import java.util.Map;

import net.alphaatom.wirebot.Command;
import net.alphaatom.wirebot.WireBot;

public class BanWord extends Command {
	
	private String[] aliases = { "banword", "banphrase", "bw", "wordban" };
	
	/*
	 * (non-Javadoc)
	 * @see net.alphaatom.wirebot.Command#exec(java.util.Map, java.lang.String[], net.alphaatom.wirebot.WireBot)
	 */
	@Override
	public void exec(Map<String, String> cmdinfo, String[] args, WireBot wireBot) {
		if (this.userHasElevation(cmdinfo)) {
			if (!args[0].isEmpty()) {
				wireBot.addBannedWord(cmdinfo.get("channel"), args[0]);
			} else {
				wireBot.sendMessage(cmdinfo.get("channel"), 
						"Banned words: " + this.buildBannedWordString(wireBot.getBannedWords(cmdinfo.get("channel"))));
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.alphaatom.wirebot.Command#getAliases()
	 */
	@Override
	public String[] getAliases() {
		return aliases;
	}
	
	/**
	 * Build a comma separated banned word list from the bannedList array.
	 * 
	 * @param bannedList ArrayList containing the banned words
	 * @return String containing banned word list, separated by commas.
	 */
	private String buildBannedWordString(ArrayList<String> bannedList) {
		String bannedWordString = "";
		if (bannedList.size() == 0) {
			return "";
		}
		for (String bannedWord : bannedList) {
			bannedWordString = bannedWordString + bannedWord + ",";
		}
		return bannedWordString.substring(0, bannedWordString.length()-1);
	}

}
