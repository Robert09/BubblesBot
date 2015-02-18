package me.robert.bbot.gui;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import me.robert.bbot.BBot;
import me.robert.bbot.util.ChatUtil;
import me.robert.bbot.util.UserList;

import org.pircbotx.User;

public class GUIChat implements WritableGUI {

	private JFrame frmBubblesofficialbot;
	public static JTextField channelName;
	public static JTextField botName;
	public static JPasswordField oAuth;
	private static JTextArea chatArea = new JTextArea();
	private static JTextArea userList = new JTextArea();
	private static JTextField messageArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		GUIChat window = new GUIChat();
		window.frmBubblesofficialbot.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	private GUIChat() {
		initialize();
	}

	private static GUIChat instance = new GUIChat();
	private JScrollPane userList_ScrollPane;

	public static GUIChat getInstance() {
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBubblesofficialbot = new JFrame();
		frmBubblesofficialbot.setTitle("BubblesOfficalBot");
		frmBubblesofficialbot.setIconImage(Toolkit.getDefaultToolkit().getImage(GUIChat.class.getResource("/images/BubblesBot.png")));
		frmBubblesofficialbot.getContentPane().setBackground(Color.DARK_GRAY);
		frmBubblesofficialbot.setBackground(Color.DARK_GRAY);
		frmBubblesofficialbot.setBounds(100, 100, 791, 526);
		frmBubblesofficialbot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBubblesofficialbot.getContentPane().setLayout(null);

		channelName = new JTextField();
		channelName.setToolTipText("Channel to join");
		channelName.setBackground(Color.LIGHT_GRAY);
		channelName.setText("o3bubbles09");
		channelName.setBounds(10, 11, 163, 20);
		frmBubblesofficialbot.getContentPane().add(channelName);
		channelName.setColumns(10);

		JButton btnConnect = new JButton("Connect");
		btnConnect.setToolTipText("Click to connect to the channel");
		btnConnect.setBackground(Color.LIGHT_GRAY);
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				(new Thread(new BBot())).start();
			}
		});
		btnConnect.setBounds(676, 10, 89, 23);
		frmBubblesofficialbot.getContentPane().add(btnConnect);

		botName = new JTextField();
		botName.setToolTipText("Your bot's name");
		botName.setBackground(Color.LIGHT_GRAY);
		botName.setText("bubblesofficialbot");
		botName.setBounds(183, 11, 163, 20);
		frmBubblesofficialbot.getContentPane().add(botName);
		botName.setColumns(10);

		oAuth = new JPasswordField();
		oAuth.setToolTipText("Your bot's oAuth key");
		oAuth.setBackground(Color.LIGHT_GRAY);
		oAuth.setBounds(356, 11, 310, 20);
		frmBubblesofficialbot.getContentPane().add(oAuth);

		JButton btnSend = new JButton("Send");
		btnSend.setToolTipText("Click to send your message");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChatUtil.sendMessageToChat(messageArea.getText());
				messageArea.setText("");
			}
		});
		btnSend.setBackground(Color.LIGHT_GRAY);
		btnSend.setBounds(676, 454, 89, 23);
		frmBubblesofficialbot.getContentPane().add(btnSend);

		messageArea = new JTextField();
		messageArea.setToolTipText("Type your message here");
		messageArea.setBackground(Color.LIGHT_GRAY);
		messageArea.setBounds(10, 455, 656, 20);
		frmBubblesofficialbot.getContentPane().add(messageArea);
		messageArea.setColumns(10);
		chatArea.setWrapStyleWord(true);

		chatArea.setLineWrap(true);
		chatArea.setBackground(Color.LIGHT_GRAY);
		chatArea.setForeground(Color.RED);
		chatArea.setEditable(false);
		chatArea.setBounds(10, 42, 583, 406);
		frmBubblesofficialbot.getContentPane().add(chatArea);

		userList.setBackground(Color.LIGHT_GRAY);
		userList.setForeground(Color.DARK_GRAY);
		userList.setEditable(false);
		userList.setBounds(630, 44, 135, 406);
		frmBubblesofficialbot.getContentPane().add(userList);

		JScrollPane chatArea_ScrollPane = new JScrollPane(chatArea);
		chatArea_ScrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		chatArea_ScrollPane.setBounds(10, 42, 609, 406);
		frmBubblesofficialbot.getContentPane().add(chatArea_ScrollPane);

		userList_ScrollPane = new JScrollPane(userList);
		userList_ScrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		userList_ScrollPane.setBounds(629, 78, 136, 372);
		frmBubblesofficialbot.getContentPane().add(userList_ScrollPane);

		JButton btnRefreshUserList = new JButton("Refresh User List");
		btnRefreshUserList.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				UserList.getInstance().refreshUserList();
				System.out.println("Refreshed Chat!");
				System.out.println("oAuth: " + oAuth.getText());
				System.out.println("channel: " + channelName.getText());
				System.out.println("bot: " + botName.getText());
			}
		});
		btnRefreshUserList.setBackground(Color.LIGHT_GRAY);
		btnRefreshUserList.setToolTipText("This will refresh the User list");
		btnRefreshUserList.setBounds(629, 44, 136, 23);
		frmBubblesofficialbot.getContentPane().add(btnRefreshUserList);

		hasDefaultFile();
	}

	public static JTextField getChannelName() {
		return channelName;
	}

	public static JTextField getBotName() {
		return botName;
	}

	public static JPasswordField getoAuth() {
		return oAuth;
	}

	public static void setChannelName(JTextField channelName) {
		GUIChat.channelName = channelName;
	}

	public static void setBotName(JTextField botName) {
		GUIChat.botName = botName;
	}

	public static void setoAuth(JPasswordField oAuth) {
		GUIChat.oAuth = oAuth;
	}

	@Override
	public void writeToChat(String s, User user) {
		chatArea.append(s + System.lineSeparator());
		chatArea.append("------------------------------" + System.lineSeparator());
		chatArea.setCaretPosition(chatArea.getDocument().getLength());
	}

	@Override
	public void writeToUsers(String s) {
		userList.append(s + System.lineSeparator());
	}

	public void clearUsers() {
		userList.setText("");
	}

	public void hasDefaultFile() {
		Properties prop = new Properties();
		String propFileName = "default.properties";

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

		try {
			prop.load(inputStream);
			GUIChat.oAuth.setText("oauth:" + prop.getProperty("oAuth"));
			GUIChat.channelName.setText(prop.getProperty("channelName"));
			GUIChat.botName.setText(prop.getProperty("botName"));

			System.out.println("oauth:" + prop.getProperty("oAuth"));
			System.out.println(prop.getProperty("channelName"));
			System.out.println(prop.getProperty("botName"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
