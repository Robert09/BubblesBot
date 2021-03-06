package me.robert.bob.util.files;

import java.io.File;
import java.io.IOException;

import me.robert.bob.BubblesOfficialBot;
import me.robert.bob.gui.tabs.ConsoleTab;
import me.robert.bob.gui.tabs.ConsoleTab.Level;
import me.robert.bob.util.HTTPHelper;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FollowerFile extends BotFile implements Runnable {
	private JsonParser json;
	private JsonArray list;
	public static boolean run = true;
	private int i = 0;

	public FollowerFile(BubblesOfficialBot b) throws IOException {
		super(b, "C:" + File.separator + "BubblesOfficialBot" + File.separator + "follower tracking" + File.separator + b.getChannel(false)
				+ ".properties");
		json = BubblesOfficialBot.json;
	}

	/**
	 * Starts the follower tracker for the bot in the channel
	 */
	public void initFollowerTracker() {
		if (super.properties.isEmpty())
			loadFollowers();
		else
			checkFollowers(false);
		run = true;
		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	 * Stops the follower tracker for the bot in the channel
	 */
	public void stopFollowerTracker() {
		run = false;
	}

	/**
	 * Starts the thread the controls the follower check
	 */
	@Override
	public void run() {
		while (run) {
			checkFollowers(true);
			ConsoleTab.output(Level.DeBug, "Checked for new followers");
			try {
				synchronized (this) {
					this.wait(10000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Checks for any new followers since last time the bot ran.
	 */
	public void loadFollowers() {
		JsonObject obj = json.parse(
				HTTPHelper.GetResponsefrom("https://api.twitch.tv/kraken/channels/" + this.bot.getChannel(false) + "/follows?limit=100"))
				.getAsJsonObject();

		int total = obj.get("_total").getAsInt();
		int current = 0;
		String nexturl = "https://api.twitch.tv/kraken/channels/" + this.bot.getChannel(false) + "/follows?limit=100";
		while (current < total) {
			boolean success = false;
			int loops = 0;
			while (!success) {
				try {
					obj = json.parse(HTTPHelper.GetResponsefrom(nexturl)).getAsJsonObject();
					success = true;
				} catch (IllegalStateException e) {
					loops++;
					if (loops > 10)
						return;
				}
			}
			try {
				nexturl = obj.get("_links").getAsJsonObject().get("next").getAsString();
			} catch (IndexOutOfBoundsException e) {
				nexturl = obj.get("_links").getAsJsonObject().get("next").getAsString();
			}
			JsonArray list = obj.get("follows").getAsJsonArray();
			for (int i = 0; i < list.size(); i++) {
				String temp = list.get(i).getAsJsonObject().get("user").getAsJsonObject().get("display_name").getAsString();
				super.setSetting(temp.toLowerCase(), list.get(i).getAsJsonObject().get("created_at"));
			}
			current += 100;
		}
		ConsoleTab.output(Level.Info, "Updated follower list for " + this.bot.getChannel(false));
	}

	/**
	 * Checks for new followers.
	 */
	public void checkFollowers(boolean output) {
		JsonObject obj;
		try {
			obj = json.parse(
					HTTPHelper.GetResponsefrom("https://api.twitch.tv/kraken/channels/" + this.bot.getChannel(false) + "/follows?limit=2" + i))
					.getAsJsonObject();
			i = i == 0 ? 1 : 0;
		} catch (IllegalStateException ex) {
			return;
		}
		list = obj.get("follows").getAsJsonArray();
		for (int i = 0; i < list.size(); i++) {
			String temp = list.get(i).getAsJsonObject().get("user").getAsJsonObject().get("display_name").getAsString();

			if (!super.properties.containsKey(temp.toLowerCase())) {
				if (output) {
					this.bot.sendMessageToStream(temp + " Has just followed!!!");
				}
				super.setSetting(temp.toLowerCase(), list.get(i).getAsJsonObject().get("created_at"));
			}
		}
	}
}
