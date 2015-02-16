package me.robert.bbot.command;

import me.robert.bbot.util.ConfigManager;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public abstract class BaseCommand extends ListenerAdapter<PircBotX> {
	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		String message = event.getMessage();
		String userName = event.getUser().getNick();
		Channel channel = event.getChannel();

		ConfigManager.getInstance().setup();

		if (message.equalsIgnoreCase("!bubbles")) {
			channel.send().message(
					"/me " + userName + ", O3Bubbles09 is awesome!");
		}

		if (message.equalsIgnoreCase("!addcom")) {
			if (message.split(" ").length > 1) {
				for (int i = 0; i < message.split(" ").length; i++) {
					String msg = "";
					if (i != 0 && i != 1)
						msg += message.split(" ")[i];

					ConfigManager.getInstance().commands.put(
							message.split(" ")[1], msg);
					ConfigManager.getInstance().setCommands();
				}
			}
		}
	}
}
