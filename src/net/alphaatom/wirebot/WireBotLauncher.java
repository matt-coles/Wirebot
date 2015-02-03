package net.alphaatom.wirebot;

import java.io.IOException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

public class WireBotLauncher {
	
	public static void main(String[] args) {
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
		wBot.joinChannel("#alphaatom");
	}

}
