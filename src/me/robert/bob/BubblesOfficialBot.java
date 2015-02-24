package me.robert.bob;

import java.io.IOException;
import java.util.ArrayList;

import me.robert.bob.gui.Window;
import me.robert.bob.gui.tabs.ChatTab;
import me.robert.bob.gui.tabs.ConsoleTab;
import me.robert.bob.gui.tabs.ConsoleTab.Level;
import me.robert.bob.util.HTTPHelper;
import me.robert.bob.util.files.FollowerFile;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BubblesOfficialBot extends PircBot {
	public static boolean isDebug = true;
	public static JsonParser json;

	private static ArrayList<String> viewers;

	private boolean inChannel = false;

	private String currentChannel;
	public String[] mods;

	public String[] noMods = { "o3bubbles09" };

	// Files
	public FollowerFile followersFile;

	public BubblesOfficialBot() {
		json = new JsonParser();
	}

	public boolean connectToTwitch(String name, String password) {
		setName(name);
		setLogin(name);
		setVerbose(isDebug);
		try {
			connect("irc.twitch.tv", 6667, password);
			ConsoleTab.output(Level.Info, "Successfully connected to twitch.");
			return true;
		} catch (IOException | IrcException e) {
			ConsoleTab.output(Level.Error, "Couldn't connect to twitch.");
			return false;
		}
	}

	public void disconnectFromTwitch() {
		disconnect();
		ConsoleTab.output(Level.Info, "Successfully disconnected from twitch.");
	}

	public boolean joinStream(String channel) {
		if (!inStream()) {
			currentChannel = "#" + channel;
			joinChannel(currentChannel);
			ConsoleTab.output(Level.Info, "You joined: " + currentChannel);
			inChannel = true;
			this.sendMessage(getChannel(true), "/mods");
			loadViewers();

			try {
				followersFile = new FollowerFile(this);
				followersFile.initFollowerTracker();
			} catch (IOException e) {
				ConsoleTab.output(Level.Error, "Unable to create the Followers File!");
			} catch (IllegalStateException e) {
				leaveStream();
				ConsoleTab.output(Level.Error, "The channel you tried to connect to is invalid!");
			}
		} else {
			leaveStream();
		}
		return inChannel;
	}

	public void leaveStream() {
		partChannel(getChannel(true));
		followersFile.stopFollowerTracker();
		ConsoleTab.output(Level.Info, "You left: " + currentChannel);
		currentChannel = "";
		inChannel = false;
	}

	public boolean inStream() {
		return inChannel;
	}

	public String getChannel(boolean symbol) {
		if (symbol)
			return currentChannel;
		else
			return currentChannel.substring(1);
	}

	public ArrayList<String> getViewers() {
		return viewers;
	}

	/**
	 * Loads all of the viewers for the the current channel that the bot is in.
	 */
	public synchronized void loadViewers() {
		JsonObject obj = json.parse(HTTPHelper.GetResponsefrom("https://tmi.twitch.tv/group/user/" + this.getChannel(false) + "/chatters"))
				.getAsJsonObject();
		viewers = new ArrayList<String>();
		obj = obj.get("chatters").getAsJsonObject();
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
	}

	/**
	 * Returns the current permission level of the given username.
	 * 
	 * @param user
	 *            The username to get the permission level of.
	 * @return The permission level of the given username.
	 */
	public String getPermLevel(String user) {
		if (user.equalsIgnoreCase(getChannel(false)))
			return "Streamer";
		else if (isMod(user) || user.equalsIgnoreCase("o3bubbles09"))
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

	// Chat stuff
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		ChatTab.writeToChat(sender, message);
	}

	/**
	 * Used to listen for private messages. Currently used to listen for the mod
	 * list call back.
	 */
	public void onPrivateMessage(String sender, String login, String hostname, String message) {
		if (message.contains("The moderators of this channel are:")) {
			message = message.substring(message.indexOf(":") + 2);
			message += ", " + getChannel(true).substring(getChannel(true).indexOf("#") + 1);
			mods = message.split(", ");
			ConsoleTab.output(Level.Info, "BubblesBot has received the list of Mods for this channel!");
		} else if (message.contains("There are no moderators of this channel.")) {
			mods = noMods;
		}
	}

	public void sendMessageToStream(String message) {
		sendMessage(getChannel(true), message);
	}

	/**
	 * Called when someone join the channel that the bot is in.
	 */
	public void onJoin(String channel, String sender, String login, String hostname) {
		if (!viewers.contains(sender)) {
			viewers.add(sender);
			Window.reload();
		}
	}

	/**
	 * Called when someone join the channel that the bot is in.
	 */
	public void onPart(String channel, String sender, String login, String hostname) {
		if (viewers.contains(sender)) {
			viewers.remove(sender);
			Window.reload();
		}
	}
}
