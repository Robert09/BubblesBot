package me.robert.bob.gui.tabs;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import me.robert.bob.gui.BotTab;

public class ConsoleTab extends BotTab {
	private static final long serialVersionUID = 1L;

	private static JTextArea txtrChat;

	/**
	 * Create the panel.
	 */
	public ConsoleTab() {
		setBackground(Color.DARK_GRAY);
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		txtrChat = new JTextArea();
		springLayout.putConstraint(SpringLayout.NORTH, txtrChat, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, txtrChat, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, txtrChat, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, txtrChat, -10, SpringLayout.EAST, this);
		txtrChat.setBackground(Color.LIGHT_GRAY);
		txtrChat.setWrapStyleWord(true);
		txtrChat.setLineWrap(true);
		txtrChat.setEditable(false);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.add(txtrChat);
		add(scrollPane);

	}

	/**
	 * Outputs the given message to the console.
	 * 
	 * @param level
	 *            The level of the message. Added onto the beginning of the
	 *            message in the console window.
	 * @param message
	 *            The message to be added to the console window.
	 */
	public static void output(Level level, String message) {
		if (level == Level.Chat) {
			txtrChat.append(message + "\n");
		} else {
			txtrChat.append("[" + level.getLevel() + "]: " + message + "\n");
		}
	}

	public enum Level {
		None(""), Chat("Chat"), Info("Info"), Important("IMPORTANT"), Alert("Alert"), Warning("Warning"), DeBug("DeBug"), Error("ERROR");

		private String level;

		private Level(String level) {
			this.level = level;
		}

		public String getLevel() {
			return level;
		}
	}

	public void load() {
		System.out.println("Loading Console tab!");
		super.setVisible(true);
	}

	public void unload() {
		System.out.println("Unloading Console tab!");
		super.setVisible(false);
	}
}
