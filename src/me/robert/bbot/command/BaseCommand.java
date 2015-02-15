package me.robert.bbot.command;

import me.robert.bbot.gui.GUIChat;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class BaseCommand extends ListenerAdapter<PircBotX>
{
	GUIChat chat = new GUIChat();
	
	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception
	{
		super.onMessage(event);
		chat.write(event.getMessage());
	}
}
