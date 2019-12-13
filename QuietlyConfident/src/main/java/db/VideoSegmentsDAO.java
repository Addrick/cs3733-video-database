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
    
    public List<VideoSegment> searchVideoSegment(String criteria) throws Exception {
        
        List<VideoSegment> allvideos = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM `Video and Playlist DB`.videos";
            ResultSet resultSet = statement.executeQuery(query);
            
            // filter out all videos that don't contain criteria
            System.out.println("looking based on criteria: " + criteria);
            while (resultSet.next()) {
            	VideoSegment c = generateVideoSegment(resultSet);
            	System.out.println("Got here");
            	if (c.characters != null)
            	{           		
            		if (c.text != null) {
            			if (c.characters.contains(criteria) || c.text.contains(criteria)) {
            				allvideos.add(c);
            				System.out.println("added a video");
            			}
            		}
            		else if (c.characters.contains(criteria)) {
                		allvideos.add(c);
                		System.out.println("added a video");
            		}
            	}    
            	else if (c.text != null) {           		
                	if (c.text.contains(criteria)) {
                		allvideos.add(c);
                		System.out.println("added a video");
                	}
                }     
            }
            resultSet.close();
            statement.close();
            return allvideos;

        } catch (Exception e) {
            throw new Exception("Failed in getting videos: " + e.getMessage());
        }
    }
    
    public List<VideoSegment> getPublicVideoSegments() throws Exception {
        
        try {
            List<VideoSegment> allvideos = new ArrayList<>();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM videos WHERE public=1;");
            ResultSet resultSet = ps.executeQuery();
            

            while (resultSet.next()) {
            	VideoSegment c = generateVideoSegment(resultSet);
                System.out.println(c.id_video);

            	allvideos.add(c);
            }
            resultSet.close();
            ps.close();
            return allvideos;
            
        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting video: " + e.getMessage());
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

            ps = conn.prepareStatement("INSERT INTO `Video and Playlist DB`.videos values(?,?,?,?,?,?);");
            ps.setString(1,  video.id_video);
            ps.setString(2,  video.characters);
            ps.setString(3,  video.text);
            ps.setString(4,  video.url);
            ps.setBoolean(5, video.system);
            ps.setDouble(6, 1);
            ps.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Failed to insert video: " + e.getMessage());

            throw new Exception("Failed to insert video: " + e.getMessage());
        }
    }
    
    public boolean markVideoSegment(VideoSegment video) throws Exception {
        try {
        	String query = "UPDATE `Video and Playlist DB`.`videos` SET public=? WHERE id_video=? AND system=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
            ps.setDouble(1, 0);
            ps.setString(2, video.id_video);
            ps.setDouble(3, 1);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to mark video: " + e.getMessage());
        }
    }
    
    public boolean unmarkVideoSegment(VideoSegment video) throws Exception {
        try {
        	String query = "UPDATE `Video and Playlist DB`.`videos` SET public=? WHERE id_video=? AND system=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
            ps.setDouble(1, 1);
            ps.setString(2, video.id_video);
            ps.setDouble(3, 1);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to unmark video: " + e.getMessage());
        }
    }

    public List<VideoSegment> getAllVideoSegments() throws Exception {
        
        List<VideoSegment> allvideos = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM `Video and Playlist DB`.videos";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
            	VideoSegment c = generateVideoSegment(resultSet);
            	allvideos.add(c);
            }
            resultSet.close();
            statement.close();
            return allvideos;

        } catch (Exception e) {
            System.out.println("Failed in getting videos: " + e.getMessage());
            throw new Exception("Failed in getting videos: " + e.getMessage());
        }
    }
    
    private VideoSegment generateVideoSegment(ResultSet resultSet) throws Exception {
    	System.out.println("got ruleset");
        String id_video  = resultSet.getString("id_video");
    	System.out.println("got id");
        String characters  = resultSet.getString("characters");
        String text  = resultSet.getString("text");
        String url  = resultSet.getString("url");
        System.out.println("characters: " + characters);
        System.out.println("text: " + text);
        boolean system = resultSet.getBoolean("system");
        boolean visible = resultSet.getBoolean("public");

        return new VideoSegment (id_video, characters, text, url, system, visible);
      }

}