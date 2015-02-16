package me.robert.bbot.gui;

import org.pircbotx.User;

public interface WritableGUI {
	public void writeToChat(String s, User user);
	public void writeToUsers(String s);
}
