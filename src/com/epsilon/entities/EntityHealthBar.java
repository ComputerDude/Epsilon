package com.epsilon.entities;

public class EntityHealthBar {

	/**
	 * For formating the health bar.
	 * @param health The health of the entity
	 * @param maxHealth The max health of the entity.
	 * @author JustBru00
	 */
	private String formatBar(int health, int maxHealth) { // Design is: "[:::::healthLeft:::::]"
		int percent = (health / maxHealth) * 100;
		
		if (percent >= 90) {
			return "&8[&a:::::&c" + health + "&a:::::&8]";
		} else if (percent >= 80 && percent <= 90) {
			return "&8[&a:::::&c" + health + "&a::::&c:&8]";
		} else if (percent >= 70 && percent <= 80) {
			return "&8[&a:::::&c" + health + "&a:::&c::&8]";
		} else if (percent >= 60 && percent <= 70) {
			return "&8[&a:::::&c" + health + "&a::&c:::&8]";
		} else if (percent >= 50 && percent <= 60) {
			return "&8[&a:::::&c" + health + "&a:&c::::&8]";
		} else if (percent >= 40 && percent <= 50) {
			return "&8[&a:::::&c" + health + "&c:::::&8]";
		} else if (percent >= 30 && percent <= 40) {
			return "&8[&a::::&c:" + health + "&c:::::&8]";
		} else if (percent >= 20 && percent <= 30) {
			return "&8[&a:::&c::" + health + "&c:::::&8]";
		} else if (percent >= 10 && percent <= 20) {
			return "&8[&a::&c:::" + health + "&c:::::&8]";
		} else if (percent > 0 && percent <= 10) {
			return "&8[&a:&c::::" + health + "&c:::::&8]";
		} else if (percent == 0) {
			return "&8[&c:::::" + health + "&c:::::&8]";
		} else if (percent < 0) {
			return "&cThis really should be dead.";
		} else {
			return "&cSomeone messed up";
		}
	}
	
}
