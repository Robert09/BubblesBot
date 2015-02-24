package me.robert.bob.gui.tabs;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import me.robert.bob.gui.BotTab;
import me.robert.bob.gui.Window;

public class ChatTab extends BotTab {
	private static final long serialVersionUID = 1L;

	private static JTextField txtMessage;
	private static JTextArea txtrChat;

	/**
	 * Create the panel.
	 */
	public ChatTab() {
		setBackground(Color.DARK_GRAY);
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Window.getBot().sendMessageToStream(txtMessage.getText().replace(" i ", " I "));
				writeToChat(Window.getBot().getName(), txtMessage.getText());
				txtMessage.setText("");
				txtMessage.requestFocus();
			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, btnSend, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnSend, -10, SpringLayout.EAST, this);
		add(btnSend);

		txtMessage = new JTextField();
		txtMessage.setBackground(Color.LIGHT_GRAY);
		springLayout.putConstraint(SpringLayout.WEST, txtMessage, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, txtMessage, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, txtMessage, -6, SpringLayout.WEST, btnSend);
		add(txtMessage);
		txtMessage.setColumns(10);

		txtrChat = new JTextArea();
		txtrChat.setBackground(Color.LIGHT_GRAY);
		txtrChat.setWrapStyleWord(true);
		txtrChat.setLineWrap(true);
		txtrChat.setEditable(false);

		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.NORTH, txtMessage);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, this);
		add(scrollPane);
		scrollPane.add(txtrChat);
		scrollPane.setViewportView(txtrChat);
	}

	public static void writeToChat(String sender, String message) {
		txtrChat.append("[" + sender + "] " + message.replace(" i ", " I ") + System.lineSeparator());
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
