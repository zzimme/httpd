package com.marine.httpd.codec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpRequest implements Request{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Map<String,String> parameter;
	private String host;
	private String queryString;
	private String method;
	private String requestURI;
	private String pathInfo;
	private String fileName;
	private String path;
	private InputStream is;
	
	private Map<String,String> headers;
	
	public HttpRequest(InputStream in){
	
		this.is = in;
		this.parameter = new HashMap<String,String>();
		this.headers = new HashMap<String,String>();
		
		try {
			this.setup(is);
			
		} catch (IOException e) {
			logger.error("error", e);
		} 
		
	}
	
	public void setup(InputStream in) throws IOException{
		
		String firstLine = "";
		String line = "";
			
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		firstLine = inputReader.readLine();
		
		String[] infos = firstLine.split(" ");
		if(infos.length < 2) throw new IOException();
		
		this.method = infos[0];
		this.requestURI = infos[1];
		
		Pattern pattern = Pattern.compile("(.*\\/)([^\\/?]*)\\??(.*$)");
		Matcher matcher = pattern.matcher(this.requestURI);
		
		if(matcher.matches()){
			this.path = matcher.group(1);
			this.fileName = matcher.group(2);
			this.queryString = matcher.group(3);
			this.pathInfo = this.requestURI.substring(matcher.group(1).length());
			
			if(method.equals("GET") && this.queryString.length() > 0){
				String[] querys = this.queryString.split("&");
				for (String query : querys) {
					if(query.isEmpty() || query.length() < 1) 
						continue;
					
					String[] token = query.split("=");
					if(token.length != 2) 
						continue;
					
					this.parameter.put(token[0], token[1]);
				}
			}
			
		}else{
			logger.error("error IOException");
			throw new IOException();
		}
		
		
		while(!"".equals(line = inputReader.readLine())){
			String[] headers = line.split(": ");
			this.headers.put(headers[0], headers[1]);
			
			if("host".equals(headers[0].toLowerCase()) == true) this.host = headers[1];
			
			if ("\r\n".equals(line)) {
				break;
			}
		}
			
		logger.info("new request :{}",firstLine);
		
		
	}
	
	
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName(){
		return this.fileName;
	}
	
	@Override
	public String getParameter(String name) {
		return this.parameter.get(name);
	}

	@Override
	public String getMethod() {
		return this.method;
	}

	@Override
	public String getPathInfo() {
		return this.pathInfo;
	}

	@Override
	public String getQueryString() {
		return this.queryString;
	}

	@Override
	public String getHost() {
		return this.host;
	}

	@Override
	public String getRequestURI() {
		return this.requestURI;
	}

	@Override
	public String getHeader(String name) {
		return this.headers.get(name);
	}

	
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
