package me.robert.bbot;

import java.io.IOException;

import me.robert.bbot.util.Vars;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

public class BBot
{
	private String botName = GUIChat.getBotName().getText();
	private String channelName = GUIChat.getChannelName().getText();
	@SuppressWarnings("deprecation")
	private String oAuth = GUIChat.getoAuth().getText();

	Configuration<PircBotX> bot = new Configuration.Builder<PircBotX>()
			.setServerHostname(Vars.SERVER_NAME)
			.setServerPort(Vars.SERVER_PORT).setName(botName).setLogin(botName)
			.setServerPassword(oAuth).addAutoJoinChannel(channelName)
			.buildConfiguration();

	public BBot()
	{
		PircBotX twitch = new PircBotX(bot);
		try
		{
			twitch.startBot();
		} catch (IOException | IrcException e)
		{
			e.printStackTrace();
		}
	}
}
