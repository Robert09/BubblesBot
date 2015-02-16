package me.robert.bbot.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.ho.yaml.Yaml;

public class ConfigManager {
	public HashMap<String, String> commands = new HashMap<String, String>();

	File file = new File("commands.yml");

	private ConfigManager() {
	}

	private static ConfigManager instance = new ConfigManager();

	public static ConfigManager getInstance() {
		return instance;
	}

	public void setup() {
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public void setCommands() {
		try {
			Yaml.dump(commands, file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Object getCommands() {
		try {
			Object object = Yaml.load(file);
			return object;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
}
