package com.epsilon.player;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.epsilon.ranks.Rank;
import com.epsilon.util.ColorUtil;
import com.epsilon.util.MySQL;

public class EPlayer {
	

	private String uuid;
	
	public EPlayer(Player player) {
		this.uuid = player.getUniqueId().toString();
	}

	public static Rank getRank(Player player) {
		UUID uuid = player.getUniqueId();

		int rank = MySQL.<Integer>getProperty(uuid, "rank");
		if(rank == 0) {
			return Rank.DEFAULT;
		} else if(rank == 9001) {
			return Rank.OWNER;
		} else if(rank == 8900) {
			return Rank.ADMIN;
		} else if(rank == 8800) {
			return Rank.DEV;
		} else if(rank == 8700) {
			return Rank.ART;
		} else if(rank == 8600) {
			return Rank.MODERATOR;
		} else if(rank == 8500) {
			return Rank.TRIAL;
		} else {
			player.kickPlayer(ColorUtil.color("&cAn Error Occured when loading your rank."));
			return Rank.DEFAULT;
		}
		
		
	}
	
}
