package com.marine.httpd.servlet;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import com.marine.httpd.codec.HttpRequest;
import com.marine.httpd.codec.HttpResponse;

public class Time implements SimpleServlet{

	@Override
	@Method(value=HTTP_METHOD_GET)
	public void service(HttpRequest req, HttpResponse res) throws IOException {
		Date now = new Date();
		Writer writer = res.getWriter();
		writer.write("Date:"+now);
	}

}
