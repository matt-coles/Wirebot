package net.alphaatom.wirebot.commands;

import net.alphaatom.wirebot.Command;
import net.alphaatom.wirebot.WireBot;

import org.jibble.pircbot.User;

public class UnbanWord extends Command {
	
	private String[] aliases = { "unbanword", "unbanphrase", "ubw", "wordunban" };

	@Override
	public void exec(String[] cmdinfo, String[] args, WireBot wireBot) {
		for (User u : wireBot.getUsers(cmdinfo[0])) {
			if ((!u.isOp() && u.getNick().equals(cmdinfo[1])) && !u.getNick().equals(cmdinfo[0].substring(1))) {
				return;
			}
		}
		if (!args[0].isEmpty()) {
			wireBot.removeBannedWord(cmdinfo[0], args[0]);
		}
	}

	@Override
	public String[] getAliases() {
		return aliases;
	}

}
