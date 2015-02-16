package me.robert.bbot.events;

import me.robert.bbot.gui.GUIChat;

import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.UserListEvent;

public class JoinEvent extends ListenerAdapter<PircBotX> {

	@Override
	public void onJoin(org.pircbotx.hooks.events.JoinEvent<PircBotX> event)
			throws Exception {
		for (User user : event.getChannel().getUsers()) {
			GUIChat.getInstance().writeToUsers(user.getNick());
		}
	}

	@Override
	public void onUserList(UserListEvent<PircBotX> event) throws Exception {
		for (User user : event.getUsers()) {
			GUIChat.getInstance().writeToUsers(user.getNick());
		}
	}
}
