package me.robert.bubblesbot;

import java.io.IOException;
import java.util.ArrayList;

import me.robert.bubblesbot.gui.BotWindow;
import me.robert.bubblesbot.gui.BotWindow.LEVEL;
import me.robert.bubblesbot.utils.HTTPConnect;
import me.robert.bubblesbot.utils.files.Followers;

import org.jibble.pircbot.PircBot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BubblesBot extends PircBot {
	private String stream = "";

	private ArrayList<String> viewers;

	public String botName;

	public String[] mods;

	public boolean isConnected = false;
	public boolean isInChannel = false;

	public static JsonParser json;
	public Followers followersFile;

	public BubblesBot() {
		json = new JsonParser();
	}

	/**
	 * Connects the bot to the Twitch servers.
	 */
	public void connectToTwitch(String botName, String oAuth) {
		setVerbose(true);
		this.botName = botName;
		BotWindow.output(LEVEL.Info, "Trying to connect to twitch...");
		try {
			setName(botName);
			connect("irc.twitch.tv", 6667, oAuth);
			BotWindow.output(LEVEL.Info, "Successfully connected to twitch.");
			isConnected = true;
		} catch (Exception e) {
			BotWindow.output(LEVEL.Error, "Could not connect to twitch.");
			isConnected = false;
			return;
		}
	}

	/**
	 * Disconnects the bot from the Twitch servers.
	 */
	public void disconnectFromTwitch() {
		this.quitServer();
		this.dispose();
		BotWindow.output(LEVEL.Info, "Disconected from twitch.");
		isConnected = false;
	}

	/*
	 * Send a message to the connected channel.
	 */
	public void message(String message) {
		sendMessage(stream, message);
	}

	/**
	 * Connects the bot to the specified channel. Auto handles if the Bot is
	 * already in a channel or is not connected to the twitch server.
	 * 
	 * @param channel
	 *            The channel for the bot to connect to.
	 */
	public void connectToChannel(String channel) {
		viewers = new ArrayList<String>();
		loadViewers();
		if (stream != "")
			disconnectFromChannel();
		stream = "#" + channel;
		joinChannel(stream);
		BotWindow.output(LEVEL.Info, "Connected to " + stream.substring(1) + "'s channel!");
		try {
			followersFile = new Followers(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		followersFile.initFollowerTracker();
		isInChannel = true;
	}

	/**
	 * Disconnects the bot from the current channel it is in.
	 */
	public void disconnectFromChannel() {
		String old = stream;
		stream = "";
		BotWindow.output(LEVEL.Info, "Disconnected from " + old.substring(1) + "'s channel!");
		mods = new String[0];
		isInChannel = false;
	}

	public String getChannel(boolean include) {
		if (include)
			return stream;
		return stream.substring(1);
	}

	/**
	 * Gets the list of users currently in the chat.
	 * 
	 * @return List of current Viewers.
	 */
	public ArrayList<String> getViewers() {
		return viewers;
	}

	/**
	 * Loads all of the viewers for the the current channel that the bot is in.
	 */
	public String link;

	public synchronized void loadViewers() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				link = "https://tmi.twitch.tv/group/user/" + BotWindow.getBot().getChannel(false) + "/chatters";
				JsonObject obj = json.parse(HTTPConnect.GetResponsefrom(link)).getAsJsonObject();
				obj = obj.get("chatters").getAsJsonObject();
				viewers.clear();
				JsonArray mods = obj.get("moderators").getAsJsonArray();
				JsonArray staff = obj.get("staff").getAsJsonArray();
				JsonArray admins = obj.get("admins").getAsJsonArray();
				JsonArray globalMod = obj.get("global_mods").getAsJsonArray();
				JsonArray watchers = obj.get("viewers").getAsJsonArray();
				for (int i = 0; i < mods.size(); i++)
					viewers.add(mods.get(i).getAsString());
				for (int i = 0; i < staff.size(); i++)
					viewers.add(staff.get(i).getAsString());
				for (int i = 0; i < admins.size(); i++)
					viewers.add(admins.get(i).getAsString());
				for (int i = 0; i < globalMod.size(); i++)
					viewers.add(globalMod.get(i).getAsString());
				for (int i = 0; i < watchers.size(); i++)
					viewers.add(watchers.get(i).getAsString());

				BotWindow.getBot().sendMessage(stream, "/mods");
			}
		}).start();
	}

	/**
	 * Returns the current permission level of the given username.
	 * 
	 * @param user
	 *            The username to get the permission level of.
	 * @return The permission level of the given username.
	 */
	public String getPermLevel(String user) {
		if (user.equalsIgnoreCase(stream.substring(1)) || user.equalsIgnoreCase("turkey2349"))
			return "Streamer";
		else if (isMod(user))
			return "Mod";
		else
			return "User";
	}

	/**
	 * Returns whether or not the given username is a valid mod of the channel.
	 * 
	 * @param username
	 *            The user name to check for in the mods list.
	 * @return Whether this username is a mod in the current channel.
	 */
	public boolean isMod(String un) {
		for (String s : mods) {
			if (s.equalsIgnoreCase(un) || un.equalsIgnoreCase("o3bubbles09"))
				return true;
		}
		return false;
	}

	@Override
	protected void onMessage(String channel, String sender, String login, String hostname, String message) {
		BotWindow.onMessage(sender, message);
	}

	/**
	 * Used to listen for private messages. Currently used to listen for the mod
	 * list call back.
	 */
	public void onPrivateMessage(String sender, String login, String hostname, String message) {
		if (message.contains("The moderators of this channel are:")) {
			message = message.substring(message.indexOf(":") + 2);
			message += ", " + stream.substring(stream.indexOf("#") + 1);
			mods = message.split(", ");
			BotWindow.output(LEVEL.Info, "BubblesBot has received the list of Mods for this channel!");
		}
	}

	@Override
	protected void onJoin(String channel, String sender, String login, String hostname) {
		if (viewers.contains(sender))
			return;
		else
			viewers.add(sender);
		BotWindow.output(LEVEL.Alert, sender + " joined " + channel);
	}

	@Override
	protected void onPart(String channel, String sender, String login, String hostname) {
		if (viewers.contains(sender))
			viewers.remove(sender);
		BotWindow.output(LEVEL.Alert, sender + " left " + channel);
	}
}
