package net.alphaatom.wirebot.commands;

import java.util.Map;

import net.alphaatom.wirebot.Command;
import net.alphaatom.wirebot.WireBot;

public class UnbanWord extends Command {
	
	private String[] aliases = { "unbanword", "unbanphrase", "ubw", "wordunban" };

	@Override
	public void exec(Map<String, String> cmdinfo, String[] args, WireBot wireBot) {
		if (this.userHasElevation(cmdinfo)) {
			if (!args[0].isEmpty()) {
				wireBot.removeBannedWord(cmdinfo.get("channel"), args[0]);
			}
		}
	}

	@Override
	public String[] getAliases() {
		return aliases;
	}

}
