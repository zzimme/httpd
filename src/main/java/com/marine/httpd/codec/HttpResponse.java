package com.marine.httpd.codec;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse implements Response {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static final Map<String, String> status = new HashMap<String, String>();
	static{
		status.put("200", "OK");
		status.put("302", "Found");
		status.put("400", "Bad Request");
		status.put("403", "Forbidden");
		status.put("404", "Not Found");
		status.put("500", "Internal Server Error");
	}
	
	private Writer writer;
	private Map<String,String> header;
	private byte[] body;
	private String statusCode;
	private String version;
	
	public HttpResponse(){
		this.header = new HashMap<String,String>();
		this.writer =  new StringWriter();
	}
	

	@Override
	public Writer getWriter() {
		return this.writer;
	}
	
	public String getHeader(String name) {
		return header.get(name);
	}

	public void addHeader(String key, String value) {
		this.header.put(key, value);
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public byte[] getBytes() throws UnsupportedEncodingException{
		
		String httpVersion = String.format("%s %s %s\r\n", this.version, this.statusCode, status.get(this.statusCode));
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(httpVersion);
		
		for (Map.Entry<String, String> entry : header.entrySet()){
			sb.append(entry.getKey()+": "+entry.getValue()+"\r\n");
		}
		
		if(this.getBody() != null) 
			sb.append("Content-Length: "+this.getBody().length + "\r\n");
		
		sb.append("\r\n");
		
		if(this.getBody() != null) 
			sb.append(new String(this.getBody()));
		
		return sb.toString().getBytes("UTF-8");
		
		
	}
}
