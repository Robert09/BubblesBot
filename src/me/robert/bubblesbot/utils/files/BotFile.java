package me.robert.bubblesbot.utils.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import me.robert.bubblesbot.BubblesBot;

public class BotFile {
	protected BubblesBot bot;
	protected InputStream iStream;
	protected File file;
	protected Properties properties;

	protected boolean exists;

	public BotFile(BubblesBot b, String path) throws IOException {
		bot = b;
		properties = new Properties();
		file = new File(path);
		exists = file.exists();
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		iStream = new FileInputStream(file);
		properties.load(iStream);
	}

	/**
	 * Outputs a given setting to the file.
	 * 
	 * @param key
	 *            The key of the setting.
	 * @param value
	 *            The value of the key to be stored.
	 */
	public void setSetting(String key, Object o) {
		properties.setProperty(key, o.toString());
		save();
	}

	/**
	 * Gets a given value or setting based on the given key.
	 * 
	 * @param key
	 *            The key of the setting to get the value of.
	 * @return The value of the given key.
	 */
	public String getSetting(String key) {
		try {
			iStream = new FileInputStream(file);
			properties.load(iStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties.getProperty(key);
	}

	/**
	 * Saves the given file.
	 */
	public void save() {
		try {
			properties.store(new FileOutputStream(file), "");
		} catch (Exception e) {
		}
	}
}
