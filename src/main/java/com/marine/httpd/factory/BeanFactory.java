package com.marine.httpd.factory;

import java.util.Map;

import com.marine.httpd.servlet.SimpleServlet;

public interface BeanFactory {

	public Map<String,SimpleServlet> getMap();
}
