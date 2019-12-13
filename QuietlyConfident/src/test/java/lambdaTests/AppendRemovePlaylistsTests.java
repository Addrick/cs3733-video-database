package lambdaTests;

import org.junit.Assert;
import org.junit.Test;

import db.*;
import http.*;
import lambda.*;
import model.*;
import java.io.IOException;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class AppendRemovePlaylistsTests extends LambdaTest {
	@Test
	public void testAppendRemove() throws IOException {
		CreatePlaylistRequest cpr = new CreatePlaylistRequest("appendRemoveTest");
        CreatePlaylistResponse cprs = new CreatePlaylistHandler().handleRequest(cpr, createContext("create"));
        
        AppendVideoSegmentRequest appendReq = new AppendVideoSegmentRequest("appendRemoveTest","null");
        AppendVideoSegmentRequest appendBlank = new AppendVideoSegmentRequest();
        Assert.assertEquals("appendVS(appendRemoveTest, null)", appendReq.toString());
        
        AppendVideoSegmentResponse appendResp = new AppendVideoSegmentHandler().handleRequest(appendReq, createContext("append"));
        appendReq.setVS("thisvideodoesnotexist");
        Assert.assertEquals("thisvideodoesnotexist", appendReq.getVS());
        appendReq.setPL("thisplaylistdoesnotexist");
        Assert.assertEquals("thisplaylistdoesnotexist", appendReq.getPL());
        
        AppendVideoSegmentResponse appendResp3 = new AppendVideoSegmentHandler().handleRequest(appendReq, createContext("append"));
        Assert.assertEquals(409, appendResp3.httpCode);
        Assert.assertEquals("Response("+appendResp3.response+")", appendResp3.toString());
        
        RemoveVideoSegmentRequest removeReq = new RemoveVideoSegmentRequest("appendRemoveTest","null", 1);
        RemoveVideoSegmentRequest removeBlank = new RemoveVideoSegmentRequest();
        Assert.assertEquals("removeVS(appendRemoveTest, null, 1)", removeReq.toString());
        
        RemoveVideoSegmentResponse removeResp = new RemoveVideoSegmentHandler().handleRequest(removeReq, createContext("remove"));
        removeReq.setVS("thisvideodoesnotexist");
        Assert.assertEquals("thisvideodoesnotexist", removeReq.getVS());
        
        RemoveVideoSegmentResponse removeResp2 = new RemoveVideoSegmentHandler().handleRequest(removeReq, createContext("remove"));
        Assert.assertEquals(200, removeResp2.httpCode);
        
        removeReq.setPL("thisplaylistdoesnotexist");
        Assert.assertEquals("thisplaylistdoesnotexist", removeReq.getPL());
        
        RemoveVideoSegmentResponse removeResp3 = new RemoveVideoSegmentHandler().handleRequest(removeReq, createContext("remove"));
        Assert.assertEquals(409, removeResp3.httpCode);
        Assert.assertEquals("Response("+removeResp3.response+")", removeResp3.toString());
               
	}

	@Test
	public void testRemoveBunkTestVideos() throws IOException {
        RemoveVideoSegmentHandler handler = new RemoveVideoSegmentHandler();
        
		RemoveVideoSegmentRequest remReq = new RemoveVideoSegmentRequest("appendRemoveTest", "thisvideodoesnotexist", 1);
        RemoveVideoSegmentResponse resp = handler.handleRequest(remReq, createContext("remove_vid"));
        Assert.assertEquals(200, resp.httpCode);
        
        RemoveVideoSegmentRequest rem2Req = new RemoveVideoSegmentRequest("appendRemoveTest", "null", 1);
        RemoveVideoSegmentResponse resp2 = handler.handleRequest(rem2Req, createContext("remove_vid"));
        Assert.assertEquals(200, resp2.httpCode);
        
        DeletePlaylistHandler phandler = new DeletePlaylistHandler();
        DeletePlaylistRequest req = new DeletePlaylistRequest("appendRemoveTest");
        DeletePlaylistResponse resp3 = phandler.handleRequest(req, createContext("delete_pl"));

	}
}
