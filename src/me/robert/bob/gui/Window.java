package me.robert.bob.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import me.robert.bob.BubblesOfficialBot;
import me.robert.bob.gui.tabs.ChatTab;
import me.robert.bob.gui.tabs.ConsoleTab;
import me.robert.bob.gui.tabs.ViewersTab;

public class Window extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem item;
	private SpringLayout sl_contentPane;

	// Tabs
	private static BotTab currentTab;
	private static ChatTab chatTab;
	private static ConsoleTab consoleTab;
	private ViewersTab viewersTab;

	private static BubblesOfficialBot bot;

	/**
	 * Create the frame.
	 */

	public Window(BubblesOfficialBot b) {
		bot = b;
		chatTab = new ChatTab();
		consoleTab = new ConsoleTab();
		viewersTab = new ViewersTab();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 550);

		contentPane = new JPanel();
		sl_contentPane = new SpringLayout();

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Add chat.
		menu = new JMenu("Chat");
		menuBar.add(menu);

		// Chat
		item = new JMenuItem("Chat");
		item.addActionListener(this);
		menu.add(item);

		// Console
		item = new JMenuItem("Console");
		item.addActionListener(this);
		menu.add(item);

		currentTab = consoleTab;
		super.add(currentTab);
		currentTab.load();

		// Add Viewers.
		menu = new JMenu("Viewers");
		menuBar.add(menu);

		// Chat
		item = new JMenuItem("Viewers");
		item.addActionListener(this);
		menu.add(item);

		setVisible(true);
		this.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JMenuItem) {
			JMenuItem eItem = (JMenuItem) e.getSource();
			currentTab.unload();
			super.remove(currentTab);
			if (eItem.getText().equalsIgnoreCase("Chat"))
				currentTab = chatTab;
			else if (eItem.getText().equalsIgnoreCase("Console"))
				currentTab = consoleTab;
			else if (eItem.getText().equalsIgnoreCase("Viewers"))
				currentTab = viewersTab;

			sl_contentPane.putConstraint(SpringLayout.NORTH, currentTab, 0, SpringLayout.NORTH, contentPane);
			sl_contentPane.putConstraint(SpringLayout.WEST, currentTab, 0, SpringLayout.WEST, contentPane);
			sl_contentPane.putConstraint(SpringLayout.SOUTH, currentTab, 0, SpringLayout.SOUTH, contentPane);
			sl_contentPane.putConstraint(SpringLayout.EAST, currentTab, 0, SpringLayout.EAST, contentPane);
			contentPane.setLayout(sl_contentPane);
			setContentPane(contentPane);

			super.add(currentTab);
			currentTab.load();
			this.revalidate();
			this.repaint();
		}
	}

	public static BubblesOfficialBot getBot() {
		return bot;
	}

	public static void reload() {
		currentTab.unload();
		currentTab.load();
	}
}
