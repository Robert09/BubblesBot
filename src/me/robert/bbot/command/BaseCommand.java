package me.robert.bbot.command;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public abstract class BaseCommand extends ListenerAdapter<PircBotX>
{
	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception
	{
		String message = event.getMessage();
		String userName = event.getUser().getNick();
		Channel channel = event.getChannel();

		if (message.equalsIgnoreCase("!bubbles"))
		{
			channel.send().message(
					"/me " + userName + ", O3Bubbles09 is awesome!");
		}
	}
}
