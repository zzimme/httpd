package com.marine.httpd.servlet;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.marine.httpd.codec.HttpRequest;
import com.marine.httpd.codec.HttpResponse;

public interface SimpleServlet {
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface Mapping {
		public String value();
	}

	void service(HttpRequest req, HttpResponse res) throws IOException ;
}
