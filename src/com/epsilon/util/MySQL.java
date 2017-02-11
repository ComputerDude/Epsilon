package com.epsilon.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.epsilon.Epsilon;
import com.epsilon.chat.Messager;

public class MySQL implements Listener {

    private static Connection con;

    public static String host = Epsilon.storageFile.getString("host");
    public static String username = Epsilon.storageFile.getString("username");
    public static String password = Epsilon.storageFile.getString("password");
    public static String databaseName = Epsilon.storageFile.getString("database-name");

    /**
     * Create the tables if necessary. Will not create the database (TODO I can't figure out how to do that
     * without an SQL syntax error).
     */
    public static void createTables() {
        openConnection();
        try {
            // It turns out you can't really create a database without having an SQL injection vulnerability.
            // So I give up on creating the database. You're gonna have to create it manually.
            final PreparedStatement statement = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS player_data(\n" +
                    "uuid CHAR(36) NOT NULL,\n" + // Player UUID
                    "player VARCHAR(16) NOT NULL,\n" + // Cached player name
                    "coins BIGINT NOT NULL DEFAULT 0,\n" + // Coins
                    "level INT NOT NULL DEFAULT 1,\n" + // Level
                    "exp BIGINT NOT NULL DEFAULT 0);" // Experience
            );
            statement.execute();
            statement.close();
        } catch (Exception e) {
            //e.printStackTrace();
        	Messager.msgConsole("Problem in the MySQL.java constructor!!!", Level.SEVERE); // To stop error spam.
        } finally {
            closeConnection();
        }
    }

    public static void disconnect() {

        try {
            if (con != null && !con.isClosed()) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static synchronized void openConnection() {
        try {
            con = DriverManager.getConnection(host, username, password);
            con.setCatalog(databaseName);
        } catch (SQLException e) {
            //e.printStackTrace();
        	Messager.msgConsole("Failed to connect to database!!! (Is the database info correct?)", Level.SEVERE);
        }
    }

    public static synchronized void closeConnection() {

        try {
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static synchronized boolean playerDataContainsPlayer(Player player) {

        try {
            PreparedStatement sql = con.prepareStatement("SELECT * FROM `player_data` WHERE uuid=?;");
            sql.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = sql.executeQuery();
            boolean containsPlayer = resultSet.next();

            sql.close();
            resultSet.close();

            return containsPlayer;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static <T> T getProperty(OfflinePlayer player, String key) throws ClassCastException {
        return getProperty(player.getUniqueId(), key);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProperty(UUID uuid, String key) throws ClassCastException {
        final T value;
        openConnection();
        try {
            PreparedStatement sql = con.prepareStatement("SELECT `" + key + "` FROM `player_data` WHERE uuid=?");
            sql.setString(1, uuid.toString());
            ResultSet set = sql.executeQuery();
            set.next();
            value = (T) set.getObject(key);
            set.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection();
        }
        return value;
    }

    public static <T> void setProperty(OfflinePlayer player, String key, T value) throws ClassCastException {
        setProperty(player.getUniqueId(), key, value);
    }

    public static <T> void setProperty(UUID uuid, String key, T value) throws ClassCastException {
        openConnection();
        try {
            // FIXME fix sql injection
            PreparedStatement sql = con.prepareStatement("UPDATE `player_data` SET `" + key + "`=? WHERE uuid=?");
            sql.setObject(1, value);
            sql.setString(2, uuid.toString());
            sql.execute();
            sql.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public static long getCoins(Player player) {
        return getProperty(player, "coins");
    }

    public static void setCoins(Player player, int coins) {
        setProperty(player, "coins", coins);
    }

    public static int getLevel(Player player) {
        return getProperty(player, "level");
    }

    public static void setLevel(Player player, int level) {
        setProperty(player, "level", level);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        openConnection();
        try {
            @SuppressWarnings("unused")
			String lastName = null;
            if (playerDataContainsPlayer(e.getPlayer())) {
                // lastName = getProperty(e.getPlayer(), "player");
                setProperty(e.getPlayer(), "player", e.getPlayer().getName());
            } else {
                PreparedStatement sql = con.prepareStatement("INSERT INTO `player_data` VALUES(?, ?, DEFAULT, " +
                        "DEFAULT, DEFAULT);");
                sql.setString(1, e.getPlayer().getUniqueId().toString());
                sql.setString(2, e.getPlayer().getName());
                sql.execute();
                sql.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
    }

}
