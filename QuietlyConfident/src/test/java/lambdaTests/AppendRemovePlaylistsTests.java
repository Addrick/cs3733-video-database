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
        AppendVideoSegmentResponse appendResp2 = new AppendVideoSegmentHandler().handleRequest(appendReq, createContext("append"));
        Assert.assertEquals(409, appendResp2.httpCode);
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
        Assert.assertEquals(409, removeResp2.httpCode);
        removeReq.setPL("thisplaylistdoesnotexist");
        Assert.assertEquals("thisplaylistdoesnotexist", removeReq.getPL());
        RemoveVideoSegmentResponse removeResp3 = new RemoveVideoSegmentHandler().handleRequest(removeReq, createContext("remove"));
        Assert.assertEquals(409, removeResp3.httpCode);
        Assert.assertEquals("Response("+removeResp3.response+")", removeResp3.toString());
        
	}
	
}
