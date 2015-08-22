package net.alphaatom.wirebot;

import java.io.IOException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

import net.alphaatom.wirebot.logger.Level;

public class WireBotLauncher {
	
	private static Level loggingLevel = Level.INFO;
	
	public static void main(String[] args) {
		WireBotLauncher.setLogLevel(Level.BABBLE);
		WireBot wBot = new WireBot();
		wBot.setVerbose(true);
		try {
			wBot.connect("irc.twitch.tv", 6667, WireBot.password);
		} catch (NickAlreadyInUseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IrcException e) {
			e.printStackTrace();
		}
		wBot.sendRawLine("CAP REQ :twitch.tv/membership");
		wBot.sendRawLine("CAP REQ :twitch.tv/commands");
		wBot.sendRawLine("CAP REQ :twitch.tv/tags");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wBot.joinChannel("#alphaatom");
	}
	
	public static void setLogLevel(Level lvl) {
		loggingLevel = lvl;
	}
	
	public static Level getLogLevel() {
		return loggingLevel;
	}

}
