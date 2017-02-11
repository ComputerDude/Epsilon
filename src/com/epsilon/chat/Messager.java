package com.epsilon.chat;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static com.epsilon.util.ColorUtil.color;

/**
 * A class for sending colored and formated messages. 
 * Please just keep this. Even if you don't use this.
 * @author JustBru00
 */
public class Messager {
	
	/**
	 * The default color for messages. Easy to override by typing color codes.
	 */
	public final static String DEFAULT_COLOR = color("&f");
	// The default color is not always &f (e.g. it's &0 in books and signs), better to put &r --null

	/**
	 * To send a message to a {@link Player}. Message is colored automatically.
	 * @param msg The message to send.
	 * @param player The {@link Player} to send the message to.
	 */
	public static void msgPlayer(String msg, Player player) {
		player.sendMessage(color(DEFAULT_COLOR + msg));
	}
	
	/**
	 * To send a message to a {@link CommandSender}. Message is colored automatically.
	 * @param msg The message to send.
	 * @param sender The {@link CommandSender} to send the message to.
	 */
	public static void msgSender(String msg, CommandSender sender) {
		sender.sendMessage(color(DEFAULT_COLOR + msg));
	}
	
	/**
	 * For sending non critical messages to the console.
	 * @param msg The message you want to send.
	 */
	public static void msgConsole(String msg) {
		Bukkit.getLogger().log(Level.INFO, msg);
	}
	
	/**
	 * For sending critical and error messsages.
	 * @param msg The message to send.
	 * @param l The logger {@link Level}
	 */
	public static void msgConsole(String msg, Level l) {
		Bukkit.getLogger().log(l, msg);
	}
	
}
