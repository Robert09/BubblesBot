package me.robert.bubblesbot;

import java.awt.EventQueue;

import me.robert.bubblesbot.gui.BotWindow;

public class Launch {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BotWindow frame = new BotWindow(new BubblesBot());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
