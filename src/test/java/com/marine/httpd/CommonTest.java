package com.marine.httpd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class CommonTest {


	@Test
	public void checkRegular(){
		//String url = "http://www.host.com:8080/../../asdf?/index.html?asdf" ;
		String url = "/ss/../asdf";
		//Pattern pattern = Pattern.compile("(http://[^/?]*)/(.*/)([^/?]*)?(.*$)");
		Pattern pattern = Pattern.compile("(.*)(\\.\\.)(.*$)");
		Matcher matcher = pattern.matcher(url);
		
		if(matcher.matches()){
			System.out.println("====");
			System.out.println("0 "+ matcher.group(0));
			System.out.println("1 "+ matcher.group(1));
			System.out.println("2 "+ matcher.group(2));
			System.out.println("3 "+ matcher.group(3));
		}
	}
}
