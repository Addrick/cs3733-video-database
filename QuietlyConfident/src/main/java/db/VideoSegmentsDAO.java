package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.VideoSegment;

/**
 * Note that CAPITALIZATION matters regarding the table id_video. If you create with 
 * a capital "videos" then it must be "videos" in the SQL queries.
 * 
 * @author 
 *
 */
public class VideoSegmentsDAO { 

	java.sql.Connection conn;

    public VideoSegmentsDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    		System.out.println("didnt connect");
    	}
    }

    public VideoSegment getVideoSegment(String id_video) throws Exception {
        
        try {
        	VideoSegment video = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM videos WHERE id_video=?;");
            ps.setString(1,  id_video);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                video = generateVideoSegment(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return video;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting video: " + e.getMessage());
        }
    }
    
    public boolean updateVideoSegment(VideoSegment video) throws Exception {
        try {
        	String query = "UPDATE videos SET value=? WHERE id_video=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, video.id_video);
            ps.setString(2, video.characters);
            ps.setString(3, video.transcript);
            ps.setString(4, video.url_video);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update report: " + e.getMessage());
        }
    }
    
    public boolean deleteVideoSegment(VideoSegment video) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM videos WHERE id_video = ?;");
            ps.setString(1, video.id_video);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to insert video: " + e.getMessage());
        }
    }


    public boolean addVideoSegment(VideoSegment video) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM videos WHERE id_video = ?;");
            ps.setString(1, video.id_video);
            ResultSet resultSet = ps.executeQuery();
            
            // already present? 
            while (resultSet.next()) {
            	VideoSegment c = generateVideoSegment(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO videos (id_video,characters,transcript,url_video) values(?,?,?,?);");
            ps.setString(1,  video.id_video);
            ps.setString(2,  video.characters);
            ps.setString(3,  video.transcript);
            ps.setString(4,  video.url_video);
            ps.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Failed to insert video: " + e.getMessage());

            throw new Exception("Failed to insert video: " + e.getMessage());
        }
    }

    public List<VideoSegment> getAllVideoSegments() throws Exception {
        
        List<VideoSegment> allvideos = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM `Video and Playlist DB`.videos";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {         System.out.println("nice");

            VideoSegment c = generateVideoSegment(resultSet);
                System.out.println(c.id_video);

                allvideos.add(c);
            }
            resultSet.close();
            statement.close();
            return allvideos;

        } catch (Exception e) {
            throw new Exception("Failed in getting books: " + e.getMessage());
        }
    }
    
    private VideoSegment generateVideoSegment(ResultSet resultSet) throws Exception {
    	System.out.println("got ruleset");
        String id_video  = resultSet.getString("id_video");
    	System.out.println("got id");
        String characters  = resultSet.getString("characters");
        String transcript  = resultSet.getString("transcript");
        String url_video  = resultSet.getString("url_video");

        return new VideoSegment (id_video, characters, transcript, url_video, false);
    }

}