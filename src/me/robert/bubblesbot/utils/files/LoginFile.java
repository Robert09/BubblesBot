package me.robert.bubblesbot.utils.files;

import java.io.File;
import java.io.IOException;

import me.robert.bubblesbot.BubblesBot;

public class LoginFile extends BotFile {

	public LoginFile(BubblesBot b) throws IOException {
		super(b, "C:" + File.separator + "BubblesBot" + File.separator + "login.properties");
		addDefaults();
	}

	public boolean getBoolean(String path) {
		return Boolean.parseBoolean(super.getSetting(path));
	}

	public String getString(String path) {
		return super.getSetting(path);
	}

	public void setSetting(String path, Object o) {
		super.setSetting(path, o);
	}

	public void addDefaults() {
		System.out.println("Added default");
		if (!exists) {
			setSetting("lastBotName", "");
			setSetting("lastOAuth", "");
			setSetting("lastChannel", "o3bubbles09");
		} else {
			return;
		}
	}
}
