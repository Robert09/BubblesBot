package me.robert.bubblesbot.gui;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
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

	private static JTextArea txtChat;

	private JPasswordField oAuth;

	private static BubblesBot bot;
	private JButton btnJoin;
	private JScrollPane scrollPane;
	private JButton btnViewers;
	private JButton btnRefreshViewers;

	/**
	 * Create the frame.
	 */
	public BotWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(BotWindow.class.getResource("/images/BubblesBot.png")));
		bot = new BubblesBot();

		setResizable(false);
		setTitle("BubblesOfficialBot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 773, 521);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeToWindow();
				txtMessage.requestFocus();
			}
		});
		btnSend.setBounds(658, 459, 89, 23);
		contentPane.add(btnSend);

		txtMessage = new JTextField();
		txtMessage.setBounds(10, 460, 638, 20);
		contentPane.add(txtMessage);
		txtMessage.setColumns(10);

		txtBotName = new JTextField();
		txtBotName.setBounds(10, 11, 175, 20);
		contentPane.add(txtBotName);
		txtBotName.setColumns(10);

		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				(new Thread(new StartBot(bot, txtBotName.getText().toLowerCase(), oAuth.getText()))).start();
			}
		});
		btnConnect.setBounds(399, 10, 89, 23);
		contentPane.add(btnConnect);

		txtChannelName = new JTextField();
		txtChannelName.setBounds(498, 11, 150, 20);
		contentPane.add(txtChannelName);
		txtChannelName.setColumns(10);

		oAuth = new JPasswordField();
		oAuth.setEchoChar('*');
		oAuth.setBounds(214, 11, 175, 20);
		contentPane.add(oAuth);

		txtChat = new JTextArea();
		txtChat.setWrapStyleWord(true);
		txtChat.setLineWrap(true);
		txtChat.setEditable(false);
		txtChat.setBounds(10, 42, 638, 400);

		btnJoin = new JButton("Join");
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						bot.connectToChannel(txtChannelName.getText().toLowerCase());
					}
				}).start();
			}
		});
		btnJoin.setBounds(658, 10, 89, 23);
		contentPane.add(btnJoin);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 638, 406);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(txtChat);

		btnViewers = new JButton("Viewers");
		btnViewers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							BotWindow.output(LEVEL.DeBug, BotWindow.getBot().link);
							Viewers frame = new Viewers();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnViewers.setBounds(658, 44, 89, 23);
		contentPane.add(btnViewers);

		btnRefreshViewers = new JButton("Refresh");
		btnRefreshViewers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BotWindow.getBot().loadViewers();
				BotWindow.output(LEVEL.Info, "Refreshed viewers list.");
			}
		});
		btnRefreshViewers.setBounds(658, 78, 89, 23);
		contentPane.add(btnRefreshViewers);

		txtMessage.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "sendMessage");

		Action sendMessage = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent arg0) {
				writeToWindow();
				txtMessage.requestFocus();
			}
		};

		txtMessage.getActionMap().put("sendMessage", sendMessage);

		DefaultCaret caret = (DefaultCaret) txtChat.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}

	public static void output(LEVEL level, String message) {
		if (level == LEVEL.DeBug) {
			if (Vars.isDebug) {
				txtChat.append("[" + level.getLevel() + "]: " + message + "\n");
			}
		} else {
			txtChat.append("[" + level.getLevel() + "]: " + message + "\n");
		}
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
