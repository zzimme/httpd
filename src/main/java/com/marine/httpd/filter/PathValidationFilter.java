package com.marine.httpd.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.marine.httpd.codec.Request;

public class PathValidationFilter implements Filter{

	@Override
	public boolean execute(Request request) {
		// TODO Auto-generated method stub
		Pattern pattern = Pattern.compile("(.*)(\\.\\.)(.*$)");
		Matcher matcher = pattern.matcher(request.getPathInfo());
		
		if(matcher.matches()){
			return false;
		}
		
		return true;
	}
}
