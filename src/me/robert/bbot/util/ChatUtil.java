package me.robert.bbot.util;

import me.robert.bbot.gui.GUIChat;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class ChatUtil extends ListenerAdapter<PircBotX>
{
	public static Channel channel;
	public static User user;

	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception
	{
		String message = event.getMessage();
		String userName = event.getUser().getNick();
		ChatUtil.channel = event.getChannel();

		ChatUtil.user = event.getUser();

		super.onMessage(event);
		GUIChat.getInstance().write(userName + ": " + message, user);
	}

	public static void sendMessage(String s)
	{
		channel.send().message(s);
		GUIChat.getInstance().write("BubblesOfficialBot: " + s, user);
	}
}
