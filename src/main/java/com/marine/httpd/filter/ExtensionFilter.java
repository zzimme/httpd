package com.marine.httpd.filter;

import com.marine.httpd.codec.Request;

public class ExtensionFilter implements Filter{

	@Override
	public boolean execute(Request request) {
		// TODO Auto-generated method stub
		
		String fileName = request.getFileName();
		String[] info = fileName.split(".");
		
		if (info.length < 1) {
			
		}
		
		if ("exe".equals(info[0].toLowerCase()) == false) {
			return false;
		}
		
		return true;
	}

}
