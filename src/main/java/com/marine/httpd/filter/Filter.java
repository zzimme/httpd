package com.marine.httpd.filter;

import com.marine.httpd.codec.Request;

public interface Filter {

	public boolean execute(Request request);
}
