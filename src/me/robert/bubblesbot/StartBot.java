package me.robert.bubblesbot;

public class StartBot implements Runnable {

	BubblesBot bot;
	String botName;
	String oAuth;

	public StartBot(BubblesBot bot, String botName, String oAuth) {
		this.bot = bot;
		this.botName = botName;
		this.oAuth = oAuth;
	}

	@Override
	public void run() {
		bot.connectToTwitch(botName, oAuth);
	}
}
