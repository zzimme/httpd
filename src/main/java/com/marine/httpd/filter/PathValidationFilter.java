package com.marine.httpd.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marine.httpd.codec.Request;

public class PathValidationFilter implements Filter{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean execute(Request request) {
		
		logger.debug("path: {}", request.getPathInfo());
		Pattern pattern = Pattern.compile("(.*)(\\.\\.)(.*$)");
		Matcher matcher = pattern.matcher(request.getPathInfo());
		
		if(matcher.matches()){
			return false;
		}
		
		return true;
	}
}
