package com.amazonaws.lambda.demo;

import org.junit.Test;

import java.sql.Connection;

import junit.framework.TestCase;
import sample.DatabaseUtil;

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
