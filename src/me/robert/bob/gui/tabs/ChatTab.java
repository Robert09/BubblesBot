package me.robert.bob.gui.tabs;

import me.robert.bob.gui.BotTab;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Color;

public class ChatTab extends BotTab {
	private static final long serialVersionUID = 1L;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public ChatTab() {
		setBackground(Color.DARK_GRAY);
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JButton btnSend = new JButton("Send");
		springLayout.putConstraint(SpringLayout.SOUTH, btnSend, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnSend, -10, SpringLayout.EAST, this);
		add(btnSend);
		
		textField = new JTextField();
		textField.setBackground(Color.LIGHT_GRAY);
		springLayout.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, textField, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, textField, -6, SpringLayout.WEST, btnSend);
		add(textField);
		textField.setColumns(10);
		
		JTextArea txtrChat = new JTextArea();
		txtrChat.setBackground(Color.LIGHT_GRAY);
		springLayout.putConstraint(SpringLayout.NORTH, txtrChat, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, txtrChat, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, txtrChat, -10, SpringLayout.NORTH, textField);
		springLayout.putConstraint(SpringLayout.EAST, txtrChat, -10, SpringLayout.EAST, this);
		txtrChat.setWrapStyleWord(true);
		txtrChat.setLineWrap(true);
		txtrChat.setEditable(false);
		add(txtrChat);
		
	}

	public void load() {
		System.out.println("Loading Chat tab!");
		super.setVisible(true);
	}

	public void unload() {
		System.out.println("Unloading Chat tab!");
		super.setVisible(false);
	}
}
