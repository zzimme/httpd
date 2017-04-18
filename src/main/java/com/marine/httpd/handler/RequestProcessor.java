package com.marine.httpd.handler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marine.httpd.codec.HttpRequest;
import com.marine.httpd.codec.HttpResponse;
import com.marine.httpd.exception.ForbiddenException;
import com.marine.httpd.exception.NotFoundException;
import com.marine.httpd.filter.ExtensionFilter;
import com.marine.httpd.filter.FilterChain;
import com.marine.httpd.filter.PathValidationFilter;
import com.marine.httpd.model.Host;
import com.marine.httpd.servlet.SimpleServlet;

public class RequestProcessor {
	Host host;
	String encoding;
	Map<String, SimpleServlet> requestMapping;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public RequestProcessor(Host host, Map<String, SimpleServlet> requsetMapping, String encoding) {
		this.host = host;
		this.encoding = encoding;
		this.requestMapping = requsetMapping;
	}

	public void getResponse(HttpRequest req, HttpResponse res)
			throws IOException, NotFoundException, ForbiddenException {
		SimpleServlet servlet;

		if (this.requestMapping != null && (servlet = requestMapping.get(req.getFileName())) != null) {

			try {
				
				/*
				 * Todo
				 * Method check;
				 * */
				servlet.service(req, res);

				String body = res.getWriter().toString();

				res.setVersion("HTTP/1.0");
				res.setStatusCode("200");
				res.addHeader("Content-Type", "text/html; charset=UTF-8");
				res.setBody(body.getBytes());

			} catch (Exception ex) {
				logger.info(ex.getMessage());
				ex.printStackTrace();
			}

		} else {

			String documentRoot = host.getDocumentRoot();

			if (documentRoot.length() < 1) {
				throw new ForbiddenException();
			} else {

				logger.debug("documentRoot:{},serverName:{}", documentRoot, host.getServerName());

				String fileName = req.getPath() + req.getFileName();
				File root = new File(documentRoot);
				File file = new File(root, fileName);

				String contentType = new MimetypesFileTypeMap().getContentType(file);

				logger.debug("request fileName:{},", fileName);

				if (!file.exists()) {
					throw new NotFoundException();
				}
				
				FilterChain chain = new FilterChain();
				chain.addFilter(new PathValidationFilter());
				chain.addFilter(new ExtensionFilter());
				
				if (!chain.execute(req)) {
					throw new ForbiddenException();
				}

				if (file.canRead() && file.getCanonicalPath().startsWith(documentRoot)) {

					byte[] data = Files.readAllBytes(file.toPath());
					res.setVersion("HTTP/1.0");
					res.setStatusCode("200");
					res.addHeader("Content-Type", contentType);
					res.setBody(data);

				} else {
					throw new NotFoundException();
				}

			}

		}
	}

	
	/*
	 * Todo
	 * Filter chain pattern으로 변경
	 * 확장자 처
	 * */
	public boolean validFile(File file) throws IOException {
		String contentType = new MimetypesFileTypeMap().getContentType(file);

		if (contentType.equals("application/octet-stream") == true || file.getName().endsWith("exe")) {
			return false;
		}

		return true;
	}
}
