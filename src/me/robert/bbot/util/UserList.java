package me.robert.bbot.util;

import java.util.ArrayList;

import me.robert.bbot.gui.GUIChat;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PartEvent;
import org.pircbotx.hooks.events.PingEvent;

public class UserList extends ListenerAdapter<PircBotX> {
	public static ArrayList<User> userList = new ArrayList<User>();

	private UserList() {
	}

	private static UserList instance = new UserList();

	public static UserList getInstance() {
		return instance;
	}

	@Override
	public void onJoin(org.pircbotx.hooks.events.JoinEvent<PircBotX> event) throws Exception {
		refreshUserList();
		ChatUtil.sendMessageToBot(event.getUser().getNick() + " has joined!");
	}

	@Override
	public void onPing(PingEvent<PircBotX> event) throws Exception {
		refreshUserList();
	}

	@Override
	public void onPart(PartEvent<PircBotX> event) throws Exception {
		refreshUserList();
		ChatUtil.sendMessageToBot(event.getUser().getNick() + " has left!");
	}

	public ArrayList<User> refreshUserList() {
		Channel channel = ChatUtil.channel;
		clearUserList();
		if (channel != null) {
			for (User user : channel.getUsers()) {
				if (!userList.contains(user))
					userList.add(user);
			}

			for (User user : userList) {
				GUIChat.getInstance().writeToUsers(user.getNick());
				System.out.println(userList.toString());
			}
			return userList;
		}
		System.out.println("Channel is null!");
		return null;
	}

	public void clearUserList() {
		if (!userList.isEmpty()) {
			for (int i = 0; i < userList.size(); i++) {
				userList.remove(i);
			}
		}
		GUIChat.getInstance().clearUsers();
	}
}
