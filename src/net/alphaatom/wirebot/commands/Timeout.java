package net.alphaatom.wirebot.commands;

import java.util.Map;

import net.alphaatom.wirebot.Command;
import net.alphaatom.wirebot.WireBot;

public class Timeout extends Command {
	
	private String[] aliases = { "timeout", "to" };

	@Override
	public void exec(Map<String, String> cmdinfo, String[] args, WireBot wireBot) {
		String channel = cmdinfo.get("channel");
		if (this.userHasElevation(cmdinfo)) {
			if (args.length > 1) {
				wireBot.sendMessage(channel, ".timeout " + args[0] + " " + args[1]);
			} else {
				wireBot.sendMessage(channel, ".timeout " + args[0]);
			}
		}
	}

	@Override
	public String[] getAliases() {
		return aliases;
	}

}
