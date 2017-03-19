package com.epsilon.player;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.epsilon.Epsilon;
import com.epsilon.ranks.Rank;
import com.epsilon.util.LevelUtil;
import com.epsilon.util.MySQL;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import static com.epsilon.util.ColorUtil.colorf;

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
    private int level = -1;
    private long xp = -1;
    private Rank rank = Rank.DEFAULT;

    /**
     * @deprecated This constructor should be private--other classes, use {@link #getPlayer()}
     */
    @Deprecated
    public EPlayer(final OfflinePlayer player) {
        this.player = player;
        new BukkitRunnable() {
            @Override
            public void run() {
                final int level = MySQL.getProperty(player, "level");
                final long xp = MySQL.getProperty(player, "xp");
                final Integer ranklevel = MySQL.getProperty(player, "rank");
                final Rank rank;
                if (ranklevel == null) rank = null;
                else rank = Rank.fromLevel(ranklevel);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        EPlayer.this.level = level;
                        EPlayer.this.xp = xp;
                        EPlayer.this.rank = rank == null ? Rank.DEFAULT : rank;
                    }
                }.runTask(Epsilon.getInstance());
            }
        }.runTaskAsynchronously(Epsilon.getInstance());
    }

    public Rank getRank() {
        return rank;
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

    public void addXP(long xp) {
        if (level < 0) return;
        this.xp += xp;
        final long toLevelUp = LevelUtil.getXP(level);
        while (xp >= toLevelUp) {
            xp -= toLevelUp;
            level += 1;
            if (player.isOnline())
                ((Player) player).sendMessage(colorf("&6&lLevel up!&e You are now level %d.", level));
        }
        updateXPBar();
    }

    public int getLevel() {
        return level;
    }

    public long getXP() {
        return xp;
    }

    public void updateXPBar() {
        if (player.isOnline()) LevelUtil.setXPBar((Player) player, level, xp);
    }

}
