package me.robert.bob;

import java.io.IOException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

public class BOB extends PircBot {
	public static boolean isDebug = true;

	private boolean inChannel = false;

	private String name, pass, currentChannel;

	public BOB() {

	}

	public boolean connectToTwitch() {
		setName(name);
		setLogin(name);
		setVerbose(isDebug);

		try {
			connect("irc.twitch.tv", 6667, pass);
			return true;
		} catch (IOException | IrcException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void disconnectFromTwitch() {
		disconnect();
	}

	public boolean joinStream(String channel) {
		if (!inStream()) {
			joinChannel(channel);
			currentChannel = channel;
			inChannel = true;
		} else {
			leaveStream(currentChannel);
			inChannel = false;
		}
		return inChannel;
	}

	public void leaveStream(String channel) {
		partChannel(channel);
	}

	public boolean inStream() {
		return inChannel;
	}
}
