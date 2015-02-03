package net.alphaatom.wirebot.commands;

import org.jibble.pircbot.User;

import net.alphaatom.wirebot.Command;
import net.alphaatom.wirebot.WireBot;

public class Timeout extends Command {
	
	private String[] aliases = { "timeout", "to" };

	@Override
	public void exec(String[] cmdinfo, String[] args, WireBot wireBot) {
		for (User u : wireBot.getUsers(cmdinfo[0])) {
			if ((!u.isOp() && u.getNick().equals(cmdinfo[1])) && !u.getNick().equals(cmdinfo[0].substring(1))) {
				return;
			}
		}
		if (args.length > 1) {
			wireBot.sendMessage(cmdinfo[0], ".timeout " + args[0] + " " + args[1]);
		} else {
			wireBot.sendMessage(cmdinfo[0], ".timeout " + args[0]);
		}
	}

	@Override
	public String[] getAliases() {
		return aliases;
	}

}
