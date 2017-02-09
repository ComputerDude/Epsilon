package com.computerdude.epsilon.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.computerdude.epsilon.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class MySQL implements Listener {

    private static Connection con;

    public static String host = Main.storageFile.getString("host");
    public static String username = Main.storageFile.getString("username");
    public static String password = Main.storageFile.getString("password");

    public static void createTables() {
        openConnection();
        try {
            final PreparedStatement statement = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS player_data(" +
                    "uuid CHAR(36) NOT NULL, " +
                    "player VARCHAR(16) NOT NULL, " +
                    "coins BIGINT NOT NULL DEFAULT 0, " +
                    "level INT NOT NULL DEFAULT 1, " +
                    "exp BIGINT NOT NULL DEFAULT 0)"
            );
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

    @SuppressWarnings("unchecked")
    public static <T> T getProperty(Player player, String key) throws ClassCastException {
        final T value;
        openConnection();
        try {
            PreparedStatement sql = con.prepareStatement("SELECT ? FROM `player_data` WHERE uuid=?");
            sql.setString(1, key);
            sql.setString(2, player.getUniqueId().toString());
            ResultSet set = sql.executeQuery();
            set.next();
            value = (T) set.getObject("level");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection();
        }
        return value;
    }

    public static <T> void setProperty(Player player, String key, T value) throws ClassCastException {
        openConnection();
        try {
            PreparedStatement sql = con.prepareStatement("UPDATE `player_data` SET ?=? WHERE uuid=?");
            sql.setString(1, key);
            sql.setObject(2, value);
            sql.setString(3, player.getUniqueId().toString());
            sql.execute();
            sql.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public static int getCoins(Player player) {

        int coins = 0;

        openConnection();
        try {

            PreparedStatement sql = con.prepareStatement("SELECT coins FROM `player_data` WHERE uuid=?");
            sql.setString(1, player.getUniqueId().toString());

            ResultSet rs = sql.executeQuery();
            rs.next();

            coins = rs.getInt("coins");

            rs.close();
            sql.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return coins;
    }

    public static void setCoins(Player player, int coins) {

        openConnection();
        try {

            PreparedStatement coinUpdate = con.prepareStatement("UPDATE `player_data` SET coins=? WHERE uuid=?");
            coinUpdate.setInt(1, coins);
            coinUpdate.setString(2, player.getUniqueId().toString());
            coinUpdate.executeUpdate();

            coinUpdate.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

    }

    public static int getLevel(Player player) {

        int level = 0;

        openConnection();

        try {

            PreparedStatement sql = con.prepareStatement("SELECT level FROM `player_data` WHERE uuid=?");
            sql.setString(1, player.getUniqueId().toString());

            ResultSet rs = sql.executeQuery();
            rs.next();

            level = rs.getInt("level");

            rs.close();
            sql.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return level;
    }

    public static void setLevel(Player player, int level) {

        openConnection();
        try {

            PreparedStatement levelUpdate = con.prepareStatement("UPDATE `player_data` SET level=? WHERE uuid=?");
            levelUpdate.setInt(1, level);
            levelUpdate.setString(2, player.getUniqueId().toString());
            levelUpdate.executeUpdate();

            levelUpdate.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {

        openConnection();
        try {

            String lastName = "";

            if (playerDataContainsPlayer(e.getPlayer())) {
                PreparedStatement sql = con.prepareStatement("SELECT player FROM `player_data` WHERE uuid=?");
                sql.setString(1, e.getPlayer().getUniqueId().toString());

                ResultSet rs = sql.executeQuery();
                rs.next();

                // lastName = rs.getString("player");

                PreparedStatement nameUpdate = con.prepareStatement("UPDATE `player_data` SET player=? WHERE uuid=?");
                nameUpdate.setString(1, e.getPlayer().getName());
                nameUpdate.setString(2, e.getPlayer().getUniqueId().toString());
                nameUpdate.executeUpdate();

                nameUpdate.close();
                sql.close();
                rs.close();
            } else {
                PreparedStatement newPlayer = con.prepareStatement("INSERT INTO `player_data` values(?,?,0,1,0);");
                newPlayer.setString(1, e.getPlayer().getName());
                newPlayer.setString(2, e.getPlayer().getUniqueId().toString());
                newPlayer.execute();
                newPlayer.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }

    }

}
