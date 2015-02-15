package me.robert.bbot.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;

import me.robert.bbot.BBot;

public class GUIChat implements WritableGUI
{

	private JFrame frame;
	private static JTextField channelName;
	private static JTextField botName;
	private static JPasswordField oAuth;
	private static JTextArea chatArea = new JTextArea();
	private static JTextField messageArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		GUIChat window = new GUIChat();
		window.frame.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public GUIChat()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 791, 526);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		channelName = new JTextField();
		channelName.setText("o3bubbles09");
		channelName.setBounds(10, 11, 163, 20);
		frame.getContentPane().add(channelName);
		channelName.setColumns(10);

		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				(new Thread(new BBot())).start();
			}
		});
		btnConnect.setBounds(676, 10, 89, 23);
		frame.getContentPane().add(btnConnect);

		botName = new JTextField();
		botName.setText("bubblesofficialbot");
		botName.setBounds(183, 11, 163, 20);
		frame.getContentPane().add(botName);
		botName.setColumns(10);

		oAuth = new JPasswordField();
		oAuth.setBounds(356, 11, 310, 20);
		frame.getContentPane().add(oAuth);

		JButton btnSend = new JButton("Send");
		btnSend.setBounds(676, 454, 89, 23);
		frame.getContentPane().add(btnSend);

		messageArea = new JTextField();
		messageArea.setBounds(10, 455, 656, 20);
		frame.getContentPane().add(messageArea);
		messageArea.setColumns(10);

		chatArea.setEditable(false);
		chatArea.setBounds(10, 42, 656, 406);
		frame.getContentPane().add(chatArea);
	}

	public static JTextField getChannelName()
	{
		return channelName;
	}

	public static JTextField getBotName()
	{
		return botName;
	}

	public static JPasswordField getoAuth()
	{
		return oAuth;
	}

	public static void setChannelName(JTextField channelName)
	{
		GUIChat.channelName = channelName;
	}

	public static void setBotName(JTextField botName)
	{
		GUIChat.botName = botName;
	}

	public static void setoAuth(JPasswordField oAuth)
	{
		GUIChat.oAuth = oAuth;
	}

	@Override
	public void write(String s)
	{
		chatArea.append(s + System.lineSeparator());
	}
}
