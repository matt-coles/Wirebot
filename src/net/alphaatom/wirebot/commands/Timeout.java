package net.alphaatom.wirebot.commands;

import net.alphaatom.wirebot.Command;
import net.alphaatom.wirebot.WireBot;

public class Timeout extends Command {
	
	private String[] aliases = { "timeout", "to" };

	@Override
	public void exec(String[] cmdinfo, String[] args, WireBot wireBot) {
		
	}

	@Override
	public String[] getAliases() {
		return aliases;
	}

}
