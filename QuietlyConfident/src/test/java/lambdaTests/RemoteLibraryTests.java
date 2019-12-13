package lambdaTests;

import org.junit.Assert;
import org.junit.Test;
import com.google.gson.Gson;

import http.AddRemoteRequest;
import http.AddRemoteResponse;
import http.UnregisterRemoteRequest;
import http.UnregisterRemoteResponse;
import lambda.AddRemoteHandler;
import lambda.UnregisterRemoteHandler;
import java.io.IOException;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class RemoteLibraryTests extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	AddRemoteHandler handler = new AddRemoteHandler();
    	AddRemoteRequest req = new Gson().fromJson(incoming, AddRemoteRequest.class);
    	req.toString();
       
        AddRemoteResponse resp = handler.handleRequest(req, createContext("create"));
        resp.toString();
        Assert.assertEquals(200, resp.httpCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	AddRemoteHandler handler = new AddRemoteHandler();
    	AddRemoteRequest req = new Gson().fromJson(incoming, AddRemoteRequest.class);

    	AddRemoteResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(failureCode, resp.httpCode);
    }

    @Test
    public void testAddRemoteSite() {
    	// create VideoSegment
        AddRemoteRequest csr = new AddRemoteRequest("to-delete-again", "some-key");
        
        AddRemoteResponse resp = new AddRemoteHandler().handleRequest(csr, createContext("create"));
        Assert.assertEquals(200, resp.httpCode);
    }
    @Test
    public void testRemoveRemoteSite() {
    	// create VideoSegment
        UnregisterRemoteRequest csr = new UnregisterRemoteRequest("to-delete-again");
        csr.toString();
        
        UnregisterRemoteResponse resp = new UnregisterRemoteHandler().handleRequest(csr, createContext("create"));
        resp.toString();
        Assert.assertEquals(200, resp.httpCode);
    }
    
}
