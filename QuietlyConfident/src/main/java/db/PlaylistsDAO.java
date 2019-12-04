package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Playlist;

/**
 * Note that CAPITALIZATION matters regarding the table name. If you create with 
 * a capital "Playlists" then it must be "Playlists" in the SQL queries.
 * 
 * @author Jon aka beast
 *
 */
public class PlaylistsDAO { 

	java.sql.Connection conn;

    public PlaylistsDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    		System.out.println("didnt connect");
    	}
    }

    public Playlist getPlaylist(String name) throws Exception {
        
        try {
            Playlist playlist = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `Video and Playlist DB`.playlists WHERE name=?;");
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                playlist = generatePlaylist(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return playlist;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting playlist: " + e.getMessage());
        }
    }
    
    public boolean updatePlaylist(Playlist playlist) throws Exception {
        try {
        	String query = "UPDATE playlists SET value=? WHERE name=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
            ps.setDouble(1, playlist.order_playlist);
            ps.setString(2, playlist.id_playlist);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update report: " + e.getMessage());
        }
    }
    
    public boolean deletePlaylist(Playlist playlist) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM `Video and Playlist DB`.playlists WHERE name = ?;");
            ps.setString(1, playlist.id_playlist);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to insert playlist: " + e.getMessage());
        }
    }


    public boolean addPlaylist(Playlist playlist) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `Video and Playlist DB`.playlists WHERE name = ?;");
            ps.setString(1, playlist.id_playlist);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Playlist c = generatePlaylist(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO `Video and Playlist DB`.playlists (name,value) values(?,?);");
            ps.setString(1,  playlist.id_playlist);
            ps.setDouble(2,  playlist.order_playlist);
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert playlist: " + e.getMessage());
        }
    }

    public List<Playlist> getAllPlaylists() throws Exception {
        
        List<Playlist> allPlaylists = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM `Video and Playlist DB`.playlists";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {         System.out.println("nice");

                Playlist c = generatePlaylist(resultSet);
                System.out.println(c.id_playlist);

                allPlaylists.add(c);
            }
            resultSet.close();
            statement.close();
            return allPlaylists;

        } catch (Exception e) {
            throw new Exception("Failed in getting books: " + e.getMessage());
        }
    }
    
    private Playlist generatePlaylist(ResultSet resultSet) throws Exception {
    	System.out.println("got ruleset");
        String name  = resultSet.getString("id_playlist");
    	System.out.println("got id");

        int value = (int)resultSet.getDouble("order_playlist");
        return new Playlist (name, value);
    }

}