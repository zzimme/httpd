package com.marine.httpd.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marine.httpd.servlet.SimpleServlet;

public class ClassFileBeanFactory implements BeanFactory{
	
	private final Logger log = LoggerFactory.getLogger(ClassFileBeanFactory.class);
	private Map<String, SimpleServlet> map = new HashMap<String, SimpleServlet>();
	
	
	public ClassFileBeanFactory(){
		String scanPackageName = "com.marine.httpd.servlet";
		
		Reflections reflections = new Reflections(scanPackageName);
		Set<Class<? extends SimpleServlet>> list = reflections.getSubTypesOf(SimpleServlet.class);
		
		for (Class<?> item : list) {
			String key = item.getName().substring(scanPackageName.length()+1);
			try {
				map.put(key, (SimpleServlet) item.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("class mapping error!", e);
			}
		
		}
		
	}


	@Override
	public Map<String,SimpleServlet> getMap(){
		return map;
	}
	
	

}