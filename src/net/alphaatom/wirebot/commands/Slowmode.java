	package net.alphaatom.wirebot.commands;

import java.util.Map;

import net.alphaatom.wirebot.Command;
import net.alphaatom.wirebot.WireBot;

public class Slowmode extends Command {
	
	private String[] aliases = { "slowmode", "sm", "slow", "slowdown" };
	
	/*
	 * (non-Javadoc)
	 * @see net.alphaatom.wirebot.Command#exec(java.util.Map, java.lang.String[], net.alphaatom.wirebot.WireBot)
	 */
	@Override
	public void exec(Map<String, String> cmdinfo, String[] args, WireBot wireBot) {
		if (this.userHasElevation(cmdinfo)) {
			String channel = cmdinfo.get("channel");
			if (args.length > 0) {
				try {
					int argument = new Integer(args[0]);
					wireBot.sendMessage(channel, ".slow " + argument);
				} catch (NumberFormatException nfe) {
					if (args[0].equalsIgnoreCase("off")) {
						wireBot.sendMessage(channel, ".slowoff");
					} else {
						wireBot.sendMessage(channel, ".slow");
					}
				}
			} else {
				wireBot.sendMessage(channel, ".slow");
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
	
	

}
