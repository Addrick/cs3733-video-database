package lambdaTests;

import org.junit.Test;

import db.DatabaseUtil;

import java.sql.Connection;

import junit.framework.TestCase;

public class SampleTest extends TestCase {
	
	@Test
	public void testSOmething()  {
		try {
			Connection conn = DatabaseUtil.connect();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
