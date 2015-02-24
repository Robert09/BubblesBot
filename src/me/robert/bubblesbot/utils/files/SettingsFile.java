package me.robert.bubblesbot.utils.files;

import java.io.File;
import java.io.IOException;

import me.robert.bubblesbot.BubblesBot;
import me.robert.bubblesbot.utils.Settings;

public class SettingsFile extends BotFile {

	public SettingsFile(BubblesBot b) throws IOException {
		super(b, "C:" + File.separator + "BubblesBot" + File.separator + "settings.properties");
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
			setSetting("silentJoin", Settings.silentJoin);
			setSetting("silentLeave", Settings.silentLeave);
			setSetting("notifyFollow", Settings.notifyFollow);
		} else {
			return;
		}
	}
}
