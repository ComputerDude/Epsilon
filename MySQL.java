package com.computerdude.epsilon.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.computerdude.epsilon.Main;

public class MySQL implements Listener {

	private static Connection con;

	public static String SQL1 = Main.storageFile.getString("SQL-1");
	public static String SQL2 = Main.storageFile.getString("SQL-2");
	public static String SQL3 = Main.storageFile.getString("SQL-3");
	
	public static void disconnect() {

		try {
			if (con != null || !con.isClosed())
				con.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public synchronized static void openConnection() {

		try {
			con = DriverManager.getConnection(SQL1, SQL2, SQL3);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public synchronized static void closeConnection() {

		try {
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public synchronized static boolean playerDataContainsPlayer(Player player) {

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

	public static void setCoins(Player player, int coins) {
		
		openConnection();
		try {
			
			PreparedStatement coinUpdate = con.prepareStatement("UPDATE `player_data` SET coins=? WHERE uuid=?");
			coinUpdate.setInt(1, coins);
			coinUpdate.setString(2, player.getUniqueId().toString());
			coinUpdate.executeUpdate();
			
			coinUpdate.close();
			
		} catch(Exception e) {
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
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			closeConnection();
		}
		return coins;
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
			
		} catch(Exception e) {
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
		
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {

		openConnection();
		try {

			@SuppressWarnings("unused")
			String lastName = "";
			
			if(playerDataContainsPlayer(e.getPlayer())) {
				PreparedStatement sql = con.prepareStatement("SELECT player FROM `player_data` WHERE uuid=?");
				sql.setString(1, e.getPlayer().getUniqueId().toString());
				
				ResultSet rs = sql.executeQuery();
				rs.next();
				
				lastName = rs.getString("player");
				
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
