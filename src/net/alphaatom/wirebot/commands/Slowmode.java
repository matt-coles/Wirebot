package net.alphaatom.wirebot.commands;

import org.jibble.pircbot.User;

import net.alphaatom.wirebot.Command;
import net.alphaatom.wirebot.WireBot;

public class Slowmode extends Command {
	
	private String[] aliases = { "slowmode", "sm", "slow" };

	@Override
	public void exec(String[] cmdinfo, String[] args, WireBot wireBot) {
		for (User u : wireBot.getUsers(cmdinfo[0])) {
			if ((!u.isOp() && u.getNick().equals(cmdinfo[1])) && !u.getNick().equals(cmdinfo[0].substring(1))) {
				return;
			}
		}
		if (args.length > 0) {
			try {
				int argument = new Integer(args[0]);
				wireBot.sendMessage(cmdinfo[0], ".slow " + argument);
			} catch (NumberFormatException nfe) {
				if (args[0].equalsIgnoreCase("off")) {
					wireBot.sendMessage(cmdinfo[0], ".slowoff");
				} else {
					wireBot.sendMessage(cmdinfo[0], ".slow");
				}
			}
		} else {
			wireBot.sendMessage(cmdinfo[0], ".slow");
		}
	}

	@Override
	public String[] getAliases() {
		return aliases;
	}
	
	

}
