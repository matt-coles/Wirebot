package net.alphaatom.wirebot;

public abstract class Command {
	
	public String[] aliases;
	
	public abstract void exec(String[] cmdinfo, String[] args, WireBot wireBot);
	
	public abstract String[] getAliases();

}
