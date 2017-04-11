package com.marine.httpd.codec;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marine.httpd.model.Host;
import com.marine.httpd.server.ConfigMap;


public class HttpRequestTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String firstSite;
	private String secondSite;
	
	@Before
	public void setUp() throws Exception {
		StringBuffer a = new StringBuffer();
		a.append("GET /index.html HTTP 1.1\r\n");
		a.append("HOST: a.com:8080\r\n");
		a.append("\r\n");
		firstSite = a.toString();
		
		StringBuffer b = new StringBuffer();
		b.append("GET /index.html HTTP 1.1\r\n");
		b.append("HOST: b.com:8080\r\n");
		b.append("\r\n");
		secondSite = b.toString();
	}

	@Test
	public void checkHostHeader() throws IOException{
		
		InputStream is = new ByteArrayInputStream(firstSite.getBytes());

		HttpRequest request = new HttpRequest(is);
				
		assertEquals("a.com:8080",request.getHost());
	}
	
	@Test
	public void checkVirtualHost() throws IOException{
		
		ConfigMap cf = new ConfigMap();
		
		InputStream is = new ByteArrayInputStream(firstSite.getBytes());
		HttpRequest firstRequest = new HttpRequest(is);
		
		InputStream is_ = new ByteArrayInputStream(secondSite.getBytes());
		HttpRequest secondRequest = new HttpRequest(is_);
		
		Host aHost = ConfigMap.getHost(firstRequest.getHost());
		Host bHost = ConfigMap.getHost(secondRequest.getHost());

		assertThat(aHost.getDocumentRoot(), not(bHost.getDocumentRoot()));
		
	}
	
	@After
	public void after() throws IOException{
	
	}

}
