package com.marine.httpd.servlet;

import java.io.IOException;
import java.io.Writer;

import com.marine.httpd.codec.HttpRequest;
import com.marine.httpd.codec.HttpResponse;

public class Hello implements SimpleServlet {

	@Override
	public void service(HttpRequest req, HttpResponse res) throws IOException {

		Writer writer = res.getWriter();
		writer.write("Hello, ");
		writer.write(req.getParameter("name"));

	}

}
