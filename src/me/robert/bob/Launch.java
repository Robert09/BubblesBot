package me.robert.bob;

import java.awt.EventQueue;

import me.robert.bob.gui.Window;

public class Launch {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window(new BubblesOfficialBot());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
