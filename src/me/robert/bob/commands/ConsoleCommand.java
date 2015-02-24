package me.robert.bob.commands;

import me.robert.bob.gui.Window;
import me.robert.bob.gui.tabs.ConsoleTab;
import me.robert.bob.gui.tabs.ConsoleTab.Level;

public class ConsoleCommand {
	public static void onCommand(String[] args) {
		if (args[0].substring(0, 1).equalsIgnoreCase("/")) {
			String command = args[0].substring(1);

			if (command.equalsIgnoreCase("test")) {
				ConsoleTab.output(Level.Alert, "Test");
				System.out.println("TEST");
			} else if (command.equalsIgnoreCase("Join")) {
				if (args.length == 2) {
					Window.getBot().joinStream(args[1].toLowerCase());
				} else {
					ConsoleTab.output(Level.Alert, "Correct usage: /Join <Channel Name>");
				}
			} else if (command.equalsIgnoreCase("leave")) {
				Window.getBot().leaveStream();
			}

			else if (command.equalsIgnoreCase("connect")) {
				if (args.length < 3) {
					ConsoleTab.output(Level.Error, "Correct usage: /connect <Bot Name> <Bot oAuth>");
				} else {
					Window.getBot().connectToTwitch(args[1], args[2]);
				}
			} else if (command.equalsIgnoreCase("disconnect")) {
				Window.getBot().disconnectFromTwitch();
			} else {
				ConsoleTab.output(Level.Alert, "That is not a valid Command");
			}
		} else {
			ConsoleTab.output(Level.Error, "This window is only for commands!");
		}
	}
}
