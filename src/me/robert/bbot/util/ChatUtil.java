package me.robert.bbot.util;

import me.robert.bbot.gui.GUIChat;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;

public class ChatUtil extends ListenerAdapter<PircBotX> {
	public static Channel channel;
	public static User user;
	public static String botName;

	public ChatUtil() {

	}

	@Override
	public void onJoin(JoinEvent<PircBotX> event) throws Exception {
		ChatUtil.channel = event.getChannel();
		ChatUtil.botName = event.getBot().getNick();
	}

	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		String message = event.getMessage();
		String userName = event.getUser().getNick();

		ChatUtil.user = event.getUser();

		super.onMessage(event);
		GUIChat.getInstance().writeToChat(userName + ": " + message, user);
	}

	public static void sendMessageToChat(String s) {
		channel.send().message(s);
		GUIChat.getInstance().writeToChat(botName + ": " + s, user);
	}

	public static void sendMessageToBot(String s) {
		GUIChat.getInstance().writeToChat(s, user);
	}
}
