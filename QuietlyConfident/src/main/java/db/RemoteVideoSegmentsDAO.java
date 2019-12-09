package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.RemoteLibrary;

/**
 * Note that CAPITALIZATION matters regarding the table id_video. If you create with 
 * a capital "videos" then it must be "videos" in the SQL queries.
 * 
 * @author 
 *
 */
public class RemoteVideoSegmentsDAO { 

	java.sql.Connection conn;

    public RemoteVideoSegmentsDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    		System.out.println("didnt connect");
    	}
    }

    public RemoteLibrary getRemoteLibrary(String id_video) throws Exception {
        
        try {
        	RemoteLibrary video = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM videos WHERE id_video=?;");
            ps.setString(1,  id_video);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                video = generateRemoteLibrary(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return video;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting video: " + e.getMessage());
        }
    }
    
    public boolean deleteRemoteLibrary(RemoteLibrary video) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM videos WHERE id_video = ?;");
            ps.setString(1, video.url);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to insert video: " + e.getMessage());
        }
    }


    public boolean addRemoteLibrary(RemoteLibrary video) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM 'remote libraries' WHERE url = ?;");
            ps.setString(1, video.url);
            ResultSet resultSet = ps.executeQuery();
            
            // already present? 
            while (resultSet.next()) {
            	RemoteLibrary c = generateRemoteLibrary(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO `Video and Playlist DB`.'remote libraries' values(?,);");
            ps.setString(1,  video.url);
            ps.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Failed to insert video: " + e.getMessage());

            throw new Exception("Failed to insert video: " + e.getMessage());
        }
    }

    public List<RemoteLibrary> getAllRemoteLibrarys() throws Exception {
        
        List<RemoteLibrary> allvideos = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM `Video and Playlist DB`.'remote libraries'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {         System.out.println("nice");

            RemoteLibrary c = generateRemoteLibrary(resultSet);
                System.out.println(c.url);

                allvideos.add(c);
            }
            resultSet.close();
            statement.close();
            return allvideos;

        } catch (Exception e) {
            throw new Exception("Failed in getting books: " + e.getMessage());
        }
    }
    
    private RemoteLibrary generateRemoteLibrary(ResultSet resultSet) throws Exception {
        String url  = resultSet.getString("url");
        return new RemoteLibrary (url);
    }

}