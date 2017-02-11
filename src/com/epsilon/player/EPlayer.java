package com.epsilon.player;

import java.util.UUID;

import com.epsilon.ranks.Rank;
import com.epsilon.util.ColorUtil;
import com.epsilon.util.MySQL;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class EPlayer {

    private final OfflinePlayer player;
    private UUID uuid;

    public EPlayer(Player player) {
        this.player = player;
        uuid = player.getUniqueId();
    }

    /**
     * @deprecated I told you not to put this
     */
    @Deprecated
    public static Rank getRank(Player player) {
        return new EPlayer(player).getRank();
    }

    public Rank getRank() {
        Integer rank = MySQL.getProperty(uuid, "rank");
        if (rank == null) {
            return Rank.DEFAULT;
        } else {
            for (Rank r : Rank.values()) {
                if (r.getLevel() == rank) return r;
            }
            if (player.isOnline())
                ((Player) player).kickPlayer(ColorUtil.color("&cAn error occured when loading your rank. Please " +
                        "contact an administrator."));
            return Rank.DEFAULT;
        }


    }

}
