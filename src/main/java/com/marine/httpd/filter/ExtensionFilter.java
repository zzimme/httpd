package com.marine.httpd.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marine.httpd.codec.Request;

public class ExtensionFilter implements Filter{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean execute(Request request) {
		// TODO Auto-generated method stub
		
		String fileName = request.getFileName();
		String[] info = fileName.split("\\.");
		
		if (info.length < 1) {
			return false;
		}
		
		if ("exe".equals(info[1].toLowerCase()) == true) {
			logger.error("extension is not accept! - {}", info[1]);
			return false;
		}
		
		return true;
	}

}
