package me.robert.bob.gui.tabs;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;

import me.robert.bob.gui.BotTab;
import me.robert.bob.gui.Window;

public class ViewersTab extends BotTab {
	private static final long serialVersionUID = 1L;
	private JTable table;

	private ArrayList<String> viewers;
	public static final String[] columnNames = { "User Name", "Rank", "Following since", "" };

	private JScrollPane scrollPane;

	/**
	 * Create the frame.
	 */
	public ViewersTab() {

	}

	public void load() {
		setBackground(Color.DARK_GRAY);
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, this);

		viewers = Window.getBot().getViewers();
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
						rows[y][x] = Window.getBot().getPermLevel(viewers.get(y));
					} else if (x == 2) {
						String response = Window.getBot().followersFile.getSetting(viewers.get(y));
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

		scrollPane.setLocation(0, 0);
		scrollPane.setSize(765, 500);
		scrollPane.setVisible(true);
		scrollPane.add(table);
		scrollPane.setViewportView(table);
		super.add(scrollPane);
		super.setVisible(true);
	}

	public void unload() {
		super.remove(scrollPane);
		super.setVisible(false);
	}
}
