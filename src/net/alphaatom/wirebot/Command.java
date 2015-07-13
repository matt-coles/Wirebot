package net.alphaatom.wirebot;

import java.util.Map;

public abstract class Command {
	
	public String[] aliases;
	
	public abstract void exec(Map<String, String> cmdinfo, String[] args, WireBot wireBot);
	
	public abstract String[] getAliases();
	
	/**
	 * Finds out if the user referenced in {@code cmdinfo} has elevation above that of a
	 * normal user, if the user-type is not empty or if we know the user is the
	 * broadcaster - the display-name is the same as the channel name. Use sender-name over
	 * display-name from USERSTATE because it can be empty.
	 * 
	 * @param cmdinfo cmdinfo associated with the command
	 * @return True if the user has elevation, false if not.
	 */
	public boolean userHasElevation(Map<String, String> cmdinfo) {
		 return (!cmdinfo.get("user-type").equals("") || cmdinfo.get("sender-name").equalsIgnoreCase(cmdinfo.get("channel").substring(1)));
	}

}
