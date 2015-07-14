package net.alphaatom.wirebot.logger;

import net.alphaatom.wirebot.WireBot;

public class WireLogger {

	private WireBot bot;
	
	public WireLogger(WireBot botInst) {
		this.bot = botInst;
	}
	
	public void log (Level type, String message) {
		this.log(type, message, new Object());
	}
	
	public void log(Level type, String message, Object source) {
		if (type.equals(Level.WARNING) || type.equals(Level.ERROR)) {
			System.err.println(message + "\nSource: " + source.getClass().getSimpleName());
			if (type.equals(Level.ERROR)) {
				System.err.println("Exiting with exit code 1.");
				System.exit(1);
			}
		} else {
			if (bot.getLogLevel().equals(Level.VERBOSE) && type.equals(Level.VERBOSE)) {
				System.out.println(message + "\nSource: " + source.getClass().getSimpleName());
			} else if (bot.getLogLevel().equals(Level.NORMAL) || bot.getLogLevel().equals(Level.VERBOSE)) {
				System.out.println(message + "\nSource: " + source.getClass().getSimpleName());
			}
		}
	}

}
