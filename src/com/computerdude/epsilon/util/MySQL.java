package com.computerdude.epsilon.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.computerdude.epsilon.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class MySQL implements Listener {

    private static Connection con;

    public static String host = Main.storageFile.getString("host");
    public static String username = Main.storageFile.getString("username");
    public static String password = Main.storageFile.getString("password");
    public static String databaseName = Main.storageFile.getString("database-name");

    /**
     * Create the database and tables if necessary.
     */
    public static void createTables() {
        openConnection();
        try {
            final PreparedStatement statement = con.prepareStatement(
                    "CREATE DATABASE IF NOT EXISTS ?; " +
                    "USE ?; " +
                    "CREATE TABLE IF NOT EXISTS player_data(" +
                    "uuid CHAR(36) NOT NULL, " +
                    "player VARCHAR(16) NOT NULL, " +
                    "coins BIGINT NOT NULL DEFAULT 0, " +
                    "level INT NOT NULL DEFAULT 1, " +
                    "exp BIGINT NOT NULL DEFAULT 0);"
            );
            statement.setString(1, databaseName);
            statement.setString(1, databaseName);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
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

        } catch (SQLException e) {
            e.printStackTrace();
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
            PreparedStatement sql = con.prepareStatement("USE ?; SELECT ? FROM `player_data` WHERE uuid=?");
            sql.setString(1, databaseName);
            sql.setString(2, key);
            sql.setString(3, uuid.toString());
            ResultSet set = sql.executeQuery();
            set.next();
            value = (T) set.getObject("level");
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
            PreparedStatement sql = con.prepareStatement("USE ?; UPDATE `player_data` SET ?=? WHERE uuid=?");
            sql.setString(1, databaseName);
            sql.setString(2, key);
            sql.setObject(3, value);
            sql.setString(4, uuid.toString());
            sql.execute();
            sql.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public static int getCoins(Player player) {
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
            String lastName = null;
            if (playerDataContainsPlayer(e.getPlayer())) {
                // lastName = getProperty(e.getPlayer(), "player");
                setProperty(e.getPlayer(), "player", e.getPlayer().getName());
            } else {
                PreparedStatement sql = con.prepareStatement("USE ?; INSERT INTO `player_data` VALUES(?, ?, NULL, " +
                        "NULL, NULL, NULL);");
                sql.setString(1, databaseName);
                sql.setString(2, e.getPlayer().getUniqueId().toString());
                sql.setString(3, e.getPlayer().getName());
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
