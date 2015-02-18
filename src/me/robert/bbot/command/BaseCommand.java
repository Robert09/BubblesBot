package me.robert.bbot.command;

import me.robert.bbot.util.ChatUtil;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public abstract class BaseCommand extends ListenerAdapter<PircBotX> {

	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		String message = event.getMessage();
		String userName = event.getUser().getNick();

		if (message.equalsIgnoreCase("!bubbles")) {
			ChatUtil.sendMessageToChat("/me O3Bubbles09 is awesome!");
		}

		if (message.equalsIgnoreCase("!sayhi")) {
			ChatUtil.sendMessageToChat("/me Hello, I am O3Bubbles09's bot");
		}

		if (message.equalsIgnoreCase("!lurk")) {
			ChatUtil.sendMessageToChat(userName + " will be right back!");
		}

		if (message.equalsIgnoreCase("!explain")) {
			ChatUtil.sendMessageToChat("I am a twitch bot made with Java and PircBotX. You can find PircBotX here: https://code.google.com/p/pircbotx/");
		}
	}
}
