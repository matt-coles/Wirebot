package net.alphaatom.wirebot.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.alphaatom.wirebot.WireBotLauncher;

public class WireLogger {
	
	@Deprecated
	public void log (Level type, String message) {
		this.log(type, message, this.getClass());
	}
	
	public void log(Level type, String message, Class<?> source) {
		String formattedMessage;
		int logValue = type.getValue();
		int botValue = WireBotLauncher.getLogLevel().getValue();
		if (botValue <= logValue) {
			formattedMessage = "[" + getFormattedTime() + "][" + type.getIdentifier() + "] " + source.getSimpleName() + ": " + message;
			if (logValue < 1) {
				System.err.println(formattedMessage);
				if (logValue < 0) {
					System.exit(-1);
				}
			} else {
				System.out.println(formattedMessage);
			}
		}
	}
	
	public String getFormattedTime() {
		SimpleDateFormat sdf;
		Date date = new Date();
		if (WireBotLauncher.getLogLevel() == Level.BABBLE) {
			sdf = new SimpleDateFormat("HH:mm:ss:SS");
		} else {
			sdf = new SimpleDateFormat("HH:mm:ss");
		}
		return sdf.format(date);
	}

}
