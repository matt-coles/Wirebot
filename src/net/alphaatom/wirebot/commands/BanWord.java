package net.alphaatom.wirebot.commands;

import org.jibble.pircbot.User;

import net.alphaatom.wirebot.Command;
import net.alphaatom.wirebot.WireBot;

public class BanWord extends Command {
	
	private String[] aliases = { "banword", "banphrase", "bw", "wordban" };

	@Override
	public void exec(String[] cmdinfo, String[] args, WireBot wireBot) {
		for (User u : wireBot.getUsers(cmdinfo[0])) {
			if ((!u.isOp() && u.getNick().equals(cmdinfo[1])) && !u.getNick().equals(cmdinfo[0].substring(1))) {
				return;
			}
		}
		if (!args[0].isEmpty()) {
			wireBot.addBannedWord(cmdinfo[0], args[0]);
		}
	}

	@Override
	public String[] getAliases() {
		return aliases;
	}
	
	

}
