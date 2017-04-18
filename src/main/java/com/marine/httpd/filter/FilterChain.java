package com.marine.httpd.filter;

import java.util.ArrayList;
import java.util.List;

import com.marine.httpd.codec.Request;

public class FilterChain {

	public List<Filter> filters = new ArrayList<Filter>();
	
	public void addFilter(Filter filter){
		this.filters.add(filter);
	}
	
	
	
	public boolean execute(Request request){
		for (Filter filter : filters) {
			if(!filter.execute(request)){
				return false;
			}
		}
		return true;
	}
}
