package me.robert.bbot;

import java.io.IOException;

import me.robert.bbot.command.BaseCommand;
import me.robert.bbot.gui.GUIChat;
import me.robert.bbot.util.ChatUtil;
import me.robert.bbot.util.UserList;
import me.robert.bbot.util.Vars;

import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

public class BBot implements Runnable {
	private static String botName = GUIChat.getBotName().getText();
	private static String channelName = "#" + GUIChat.getChannelName().getText();
	@SuppressWarnings("deprecation")
	private static String oAuth = GUIChat.getoAuth().getText();

	Channel channel;

	public static Configuration<PircBotX> bot = new Configuration.Builder<PircBotX>().setServerHostname(Vars.SERVER_NAME)
			.setServerPort(Vars.SERVER_PORT).setName(botName).setLogin(botName).setServerPassword(oAuth).addAutoJoinChannel(channelName)
			.addListener(new ChatUtil()).addListener(new BaseCommand() {
			}).addListener(UserList.getInstance()).buildConfiguration();

	public BBot() {

	}

	@Override
	public void run() {
		PircBotX twitch = new PircBotX(bot);
		try {
			twitch.startBot();
		} catch (IOException | IrcException e) {
			e.printStackTrace();
		}
	}
}
