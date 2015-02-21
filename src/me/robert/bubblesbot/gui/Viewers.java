package me.robert.bubblesbot.gui;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

public class Viewers extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	private ArrayList<String> viewers;
	public static final String[] columnNames = { "User Name", "Rank", "Following since", "" };

	private JScrollPane scroller;

	/**
	 * Create the frame.
	 */
	public Viewers() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Viewers.class.getResource("/images/BubblesBot.png")));
		setBackground(Color.DARK_GRAY);
		setTitle("Viewers");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 781, 531);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		viewers = BotWindow.getBot().getViewers();
		String[][] rows;

		if (viewers == null || viewers.size() == 0) {
			rows = new String[1][4];
			rows[0][0] = "No viewers in chat!";
			rows[0][1] = "";
			rows[0][2] = "";
			rows[0][3] = "";
		} else {
			rows = new String[viewers.size()][4];

			for (int y = 0; y < viewers.size(); y++) {
				for (int x = 0; x < 4; x++) {
					if (x == 0)
						rows[y][x] = viewers.get(y);
					else if (x == 1) {
						rows[y][x] = BotWindow.getBot().getPermLevel(viewers.get(y));
					} else if (x == 2) {
						String response = BotWindow.getBot().followersFile.getSetting(viewers.get(y));
						response = response != null ? response.replaceAll("\"", "") : null;
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
						format.setTimeZone(TimeZone.getTimeZone("GMT"));
						String parse = "";
						try {
							parse = response != null ? format.parse(response).toString() : "Not following";
						} catch (ParseException e) {
							parse = "Error";
						}
						rows[y][x] = parse;
					} else if (x == 3) {
						rows[y][x] = "";
					}
				}
			}
		}
		table = new JTable(rows, columnNames) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		getContentPane().add(table);
		scroller = new JScrollPane(table);
		scroller.setLocation(0, 0);
		scroller.setSize(765, 500);
		scroller.setVisible(true);
		super.add(scroller);
		super.setVisible(true);

		table.getInputMap().put(KeyStroke.getKeyStroke("X"), "close");

		Action sendMessage = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		};

		table.getActionMap().put("close", sendMessage);
	}
}
