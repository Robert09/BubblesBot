package me.robert.bob.gui.tabs;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import me.robert.bob.commands.ConsoleCommand;
import me.robert.bob.gui.BotTab;

public class ConsoleTab extends BotTab implements KeyListener {
	private static final long serialVersionUID = 1L;

	private static JTextArea txtrConsole;
	private static JTextField textField;

	/**
	 * Create the panel.
	 */
	public ConsoleTab() {
		setBackground(Color.DARK_GRAY);
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		txtrConsole = new JTextArea();
		txtrConsole.setBackground(Color.LIGHT_GRAY);
		txtrConsole.setWrapStyleWord(true);
		txtrConsole.setLineWrap(true);
		txtrConsole.setEditable(false);

		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, this);
		scrollPane.add(txtrConsole);
		scrollPane.setViewportView(txtrConsole);
		add(scrollPane);

		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -30, SpringLayout.SOUTH, textField);
		springLayout.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, textField, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, textField, -10, SpringLayout.EAST, this);
		add(textField);
		textField.setColumns(10);
		textField.addKeyListener(this);
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
			txtrConsole.append(message + "\n");
		} else {
			txtrConsole.append("[" + level.getLevel() + "]: " + message + "\n");
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

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == textField) {
			// output("" + e.getKeyCode());
			if (e.getKeyChar() == KeyEvent.VK_ENTER) {
				String[] args = textField.getText().split(" ");
				ConsoleCommand.onCommand(args);
				textField.setText("");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
