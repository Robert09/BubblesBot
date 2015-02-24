package me.robert.bubblesbot.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

import me.robert.bubblesbot.BubblesBot;
import me.robert.bubblesbot.StartBot;
import me.robert.bubblesbot.utils.Vars;

public class BotWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextField txtMessage;
	private JTextField txtBotName;
	private JTextField txtChannelName;

	private Point initialClick;

	private static JTextArea txtChat;
	private static DefaultCaret caret;

	private JPasswordField oAuth;

	private static BubblesBot bot;
	private JButton btnJoin;
	private JScrollPane scrollPane;
	private JButton btnViewers;
	private JButton btnRefreshViewers;

	/**
	 * Create the frame.
	 */
	public BotWindow(BubblesBot bbot) {
		setOpacity(1f);
		BotWindow.bot = bbot;
		setUndecorated(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(BotWindow.class.getResource("/images/BubblesBot.png")));
		setResizable(false);
		setTitle("BubblesOfficialBot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 773, 521);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnSend = new JButton("");
		btnSend.setIcon(new ImageIcon(BotWindow.class.getResource("/images/send.png")));
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeToWindow();
				txtMessage.requestFocus();
			}
		});
		btnSend.setBounds(709, 462, 48, 48);
		contentPane.add(btnSend);

		txtMessage = new JTextField();
		txtMessage.setBackground(Color.LIGHT_GRAY);
		txtMessage.setBounds(10, 490, 689, 20);
		contentPane.add(txtMessage);
		txtMessage.setColumns(10);

		txtBotName = new JTextField();
		txtBotName.setBackground(Color.LIGHT_GRAY);
		txtBotName.setBounds(10, 11, 238, 20);
		contentPane.add(txtBotName);
		txtBotName.setColumns(10);

		JButton btnConnect = new JButton("");
		btnConnect.setIcon(new ImageIcon(BotWindow.class.getResource("/images/connect.png")));
		btnConnect.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				bot.loginFile.setSetting("lastBotName", txtBotName.getText().toLowerCase());
				bot.loginFile.setSetting("lastOAuth", oAuth.getText());
				(new Thread(new StartBot(bot, txtBotName.getText().toLowerCase(), oAuth.getText()))).start();
			}
		});
		btnConnect.setBounds(709, 101, 48, 48);
		contentPane.add(btnConnect);

		txtChannelName = new JTextField();
		txtChannelName.setBackground(Color.LIGHT_GRAY);
		txtChannelName.setBounds(518, 11, 239, 20);
		contentPane.add(txtChannelName);
		txtChannelName.setColumns(10);

		oAuth = new JPasswordField();
		oAuth.setBackground(Color.LIGHT_GRAY);
		oAuth.setEchoChar('*');
		oAuth.setBounds(258, 11, 250, 20);
		contentPane.add(oAuth);

		txtChat = new JTextArea();
		txtChat.setBackground(Color.LIGHT_GRAY);
		txtChat.setWrapStyleWord(true);
		txtChat.setLineWrap(true);
		txtChat.setEditable(false);
		txtChat.setBounds(10, 42, 638, 400);

		btnJoin = new JButton("");
		btnJoin.setIcon(new ImageIcon(BotWindow.class.getResource("/images/join.png")));
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						bot.connectToChannel(txtChannelName.getText().toLowerCase());
						bot.loginFile.setSetting("lastChannel", txtChannelName.getText().toLowerCase());
					}
				}).start();
			}
		});
		btnJoin.setBounds(709, 160, 48, 48);
		contentPane.add(btnJoin);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 689, 437);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(txtChat);

		btnViewers = new JButton("");
		btnViewers.setIcon(new ImageIcon(BotWindow.class.getResource("/images/viewers.png")));
		btnViewers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Viewers frame = new Viewers();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnViewers.setBounds(709, 278, 48, 48);
		contentPane.add(btnViewers);

		btnRefreshViewers = new JButton("");
		btnRefreshViewers.setIcon(new ImageIcon(BotWindow.class.getResource("/images/refresh.png")));
		btnRefreshViewers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BotWindow.getBot().loadViewers();
				BotWindow.getBot().followersFile.stopFollowerTracker();
				BotWindow.getBot().followersFile.initFollowerTracker();
				BotWindow.output(LEVEL.Info, "Refreshed viewers list.");
			}
		});
		btnRefreshViewers.setBounds(709, 337, 48, 48);
		contentPane.add(btnRefreshViewers);

		JButton btnLeave = new JButton("");
		btnLeave.setIcon(new ImageIcon(BotWindow.class.getResource("/images/disconnect.png")));
		btnLeave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bot.disconnectFromChannel();
			}
		});
		btnLeave.setBounds(709, 219, 48, 48);
		contentPane.add(btnLeave);

		JButton btnExit = new JButton("");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setIcon(new ImageIcon(BotWindow.class.getResource("/images/close.png")));
		btnExit.setBounds(709, 42, 48, 48);
		contentPane.add(btnExit);

		txtMessage.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "sendMessage");

		Action sendMessage = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent arg0) {
				writeToWindow();
				txtMessage.requestFocus();
			}
		};

		txtMessage.getActionMap().put("sendMessage", sendMessage);

		caret = (DefaultCaret) txtChat.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				initialClick = e.getPoint();
				getComponentAt(initialClick);
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {

				// get location of Window
				int thisX = getLocation().x;
				int thisY = getLocation().y;

				// Determine how much the mouse moved since the initial click
				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				// Move window to this position
				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				setLocation(X, Y);
			}
		});

		while (bot.filesLoaded) {
			if (bot.filesLoaded) {
				if (!bot.loginFile.getString("lastBotName").equals("") && !bot.loginFile.getString("lastOAuth").equals("")) {
					txtBotName.setText(bot.loginFile.getString("lastBotName"));
					oAuth.setText(bot.loginFile.getString("lastOAuth"));
				}
				txtChannelName.setText(bot.loginFile.getString("lastChannel"));
				bot.filesLoaded = false;
			}
		}
	}

	public static void output(LEVEL level, String message) {
		if (level == LEVEL.DeBug) {
			if (Vars.isDebug) {
				txtChat.append("[" + level.getLevel() + "]: " + message + "\n");
			}
		} else {
			txtChat.append("[" + level.getLevel() + "] " + message + "\n");
		}
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}

	public enum LEVEL {
		None(""), Chat("Chat"), Info("Info"), Important("IMPORTANT"), Alert("Alert"), Warning("Warning"), DeBug("DeBug"), Error("ERROR");

		private String level;

		private LEVEL(String level) {
			this.level = level;
		}

		public String getLevel() {
			return level;
		}
	}

	public static BubblesBot getBot() {
		return bot;
	}

	public void writeToWindow() {
		if (bot.isInChannel) {
			output(LEVEL.Chat, bot.botName + ": " + txtMessage.getText());
			bot.message(txtMessage.getText());
			txtMessage.setText("");
		} else {
			output(LEVEL.Error, "You must be in a channel to send messages!");
		}
	}

	public static void onMessage(String sender, String message) {
		output(LEVEL.Chat, sender + ": " + message);
	}
}
