package com.marine.httpd.servlet;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.marine.httpd.codec.HttpRequest;
import com.marine.httpd.codec.HttpResponse;

public interface SimpleServlet {
	
	public static final String HTTP_METHOD_GET = "GET";
	public static final String HTTP_METHOD_POST = "POST";
	public static final String HTTP_METHOD_PUT = "PUT";
	public static final String HTTP_METHOD_DELETE = "DELETE";
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface Method {
		public String value();
	}

	void service(HttpRequest req, HttpResponse res) throws IOException ;
}
