package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.*;

/**
 * Note that CAPITALIZATION matters regarding the table id_playlist. If you create with 
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

    public Playlist getPlaylist(String id_playlist) throws Exception {
        
        try {
            Playlist playlist = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `Video and Playlist DB`.playlists WHERE id_playlist=?;");
            ps.setString(1,  id_playlist);
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
        	String query = "UPDATE `Video and Playlist DB`.playlists SET order_playlist=? WHERE id_playlist=?;";
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
            PreparedStatement ps = conn.prepareStatement("DELETE FROM `Video and Playlist DB`.playlists WHERE id_playlist = ?;");
            ps.setString(1, playlist.id_playlist);
            int numAffected = ps.executeUpdate();
            ps = conn.prepareStatement("DROP TABLE `Video and Playlist DB`.`" + playlist.id_playlist + "`;");
            ps.execute();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to insert playlist: " + e.getMessage());
        }
    }


    public boolean addPlaylist(Playlist playlist) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `Video and Playlist DB`.playlists WHERE id_playlist = ?;");
            ps.setString(1, playlist.id_playlist);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
            	resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO `Video and Playlist DB`.playlists (id_playlist,order_playlist) values(?,?);");
            ps.setString(1,  playlist.id_playlist);
            ps.setDouble(2,  playlist.order_playlist);
            ps.execute();
            ps = conn.prepareStatement("CREATE TABLE `Video and Playlist DB`.`" + playlist.id_playlist
            							+ "` (`id_video` VARCHAR(64) NOT NULL);");
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert playlist: " + e.getMessage());
        }
    }
    
    public boolean appendToPlaylist(Playlist playlist, VideoSegment video) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `Video and Playlist DB`.playlists WHERE id_playlist = ?;");
            ps.setString(1, playlist.id_playlist);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) // First checks if playlist exists
            {
                System.out.println("Found playlist");
                resultSet.close();
                ps = conn.prepareStatement("SELECT * FROM `Video and Playlist DB`.videos WHERE id_video = ?;");
                ps.setString(1, video.id_video);
                resultSet = ps.executeQuery();
                System.out.println("Searching for video segment in database");
                while (resultSet.next()) // Next checks if video segment exists
                {
                    System.out.println("Found video segment");
                    resultSet.close();
                    ps = conn.prepareStatement("INSERT INTO `Video and Playlist DB`.`" + playlist.id_playlist + "` (id_video) values(?);");
                    ps.setString(1, video.id_video);
                    ps.execute();
                    ps = conn.prepareStatement("UPDATE `Video and Playlist DB`.`playlists` SET `order_playlist` = ? WHERE `id_playlist` = ?;");
                    ps.setDouble(1, playlist.order_playlist + 1);
                    ps.setString(2, playlist.id_playlist);
                    ps.execute();
                    System.out.println("Appended video segment");
                    return true; // Returns true when both playlist and video segment exists, meaning the VS was added to the playlist
                }
                System.out.println("Didn't find video segment");
            }
            return false; // Returns false if either the playlist or video segment does not exist in the databases

        } catch (Exception e) {
            throw new Exception("Failed to append video segment: " + e.getMessage());
        }
    }
    
    public boolean removeFromPlaylist(Playlist playlist, VideoSegment video, int num) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `Video and Playlist DB`.playlists WHERE id_playlist = ?;");
            ps.setString(1, playlist.id_playlist);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) // First checks if playlist exists
            {
                System.out.println("Found playlist");
                resultSet.close();
                ps = conn.prepareStatement("SELECT * FROM `Video and Playlist DB`.videos WHERE id_video = ?;");
                ps.setString(1, video.id_video);
                resultSet = ps.executeQuery();
                System.out.println("Searching for video segment in database");
                while (resultSet.next()) // Next checks if video segment exists
                {
                    System.out.println("Found video segment");
                    resultSet.close();
                    ps = conn.prepareStatement("DELETE FROM `Video and Playlist DB`.`" + playlist.id_playlist + "` WHERE id_video = ?;");
                    ps.setString(1, video.id_video);
                    ps.execute();
                    ps = conn.prepareStatement("UPDATE `Video and Playlist DB`.`playlists` SET `order_playlist` = ? WHERE `id_playlist` = ?;");
                    ps.setDouble(1, playlist.order_playlist - 1);
                    ps.setString(2, playlist.id_playlist);
                    ps.execute();
                    System.out.println("Removed video segment from " + playlist.id_playlist);
                    return true; // Returns true when both playlist and video segment exists, meaning the VS was removed to the playlist
                }
                System.out.println("Didn't find video segment");
            }
            return false; // Returns false if either the playlist or video segment does not exist in the databases

        } catch (Exception e) {
            throw new Exception("Failed to remove video segment from playlist: " + e.getMessage());
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
        String id_playlist  = resultSet.getString("id_playlist");
        int order_playlist = (int)resultSet.getDouble("order_playlist");
        List<String> videos = new ArrayList<>();
        Statement statement2 = conn.createStatement();
        String query = "SELECT * FROM `Video and Playlist DB`.`" + id_playlist + "`;";
        System.out.println("selected playlist");
        ResultSet vids = statement2.executeQuery(query);
        while (vids.next()) {videos.add(vids.getString("id_video"));}
        vids.close();
        statement2.close();
        return new Playlist(id_playlist, order_playlist, videos);
    }

}