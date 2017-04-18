package com.marine.httpd.codec;

public interface Request {

	public String getParameter(String name);
	public String getMethod();
	public String getPathInfo();
	public String getQueryString();
	public String getHost();
	public String getRequestURI();
	public String getHeader(String name);
	public String getFileName();
	
}
