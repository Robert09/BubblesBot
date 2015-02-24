package me.robert.bubblesbot.utils;

import me.robert.bubblesbot.gui.BotWindow;
import me.robert.bubblesbot.gui.BotWindow.LEVEL;

public class Settings implements Runnable {

	public static boolean silentJoin = true;
	public static boolean silentLeave = true;
	public static boolean notifyFollow = false;

	private boolean run;;

	public void run() {
		while (run) {
			reload();
			BotWindow.output(LEVEL.DeBug, "Reloaded the settings.");
			try {
				synchronized (this) {
					this.wait(10000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void initSettings() {
		run = true;
		Thread thread = new Thread(this);
		thread.start();
	}

	public void reload() {
		silentJoin = BotWindow.getBot().settingsFile.getBoolean("silentJoin");
		silentLeave = BotWindow.getBot().settingsFile.getBoolean("silentLeave");
		notifyFollow = BotWindow.getBot().settingsFile.getBoolean("notifyFollow");
	}
}
