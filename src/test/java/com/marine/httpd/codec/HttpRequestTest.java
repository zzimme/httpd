package com.marine.httpd.codec;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private String pathCheck;
	
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
		
		StringBuffer path = new StringBuffer();
		path.append("GET /../../../get/index.html HTTP 1.1\r\n");
		path.append("HOST: b.com:8080\r\n");
		path.append("\r\n");
		pathCheck = path.toString();
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
	
	@Test
	public void checkPath(){
		InputStream is = new ByteArrayInputStream(pathCheck.getBytes());
		
		HttpRequest request = new HttpRequest(is);
		
		System.out.println(request.getPath());
		System.out.println(request.getFileName());
		System.out.println(request.getMethod());
		System.out.println(request.getRequestURI());
		
		
		
	}
	
	
	@After
	public void after() throws IOException{
	
	}

}
