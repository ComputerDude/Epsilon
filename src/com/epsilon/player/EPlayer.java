package com.epsilon.player;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.epsilon.ranks.Rank;
import com.epsilon.util.ColorUtil;
import com.epsilon.util.MySQL;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class EPlayer {

    private static final List<EPlayer> PLAYERS = new LinkedList<>();

    public static EPlayer getPlayer(OfflinePlayer player) {
        for (EPlayer ep : PLAYERS) {
            // TODO replace with binary search
            if (ep.getUniqueId().equals(player.getUniqueId())) return ep;
        }
        final EPlayer ep = new EPlayer(player);
        PLAYERS.add(ep);
        return ep;
    }

    private final OfflinePlayer player;

    /**
     * @deprecated This constructor should be private--other classes, use {@link #getPlayer()}
     */
    @Deprecated
    public EPlayer(OfflinePlayer player) {
        this.player = player;
    }

    /**
     * @deprecated I told you not to put this
     */
    @Deprecated
    public static Rank getRank(Player player) {
        return new EPlayer(player).getRank();
    }

    /**
     * <b>Queries MySQL, please run asynchronously.</b>
     */
    public Rank getRank() {
        Integer rank = MySQL.getProperty(player.getUniqueId(), "rank");
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

    public OfflinePlayer getPlayer() {
        return player;
    }

    public Player getOnlinePlayer() {
        if (!player.isOnline()) throw new IllegalStateException("Player is not online");
        return (Player) player;
    }

    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof EPlayer && this.getUniqueId().equals(((EPlayer) that).getUniqueId());
    }

    @Override
    public int hashCode() {
        return getUniqueId().hashCode();
    }

}
