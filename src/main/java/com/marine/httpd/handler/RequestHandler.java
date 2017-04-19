package com.marine.httpd.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.nio.file.Files;

import javax.activation.MimetypesFileTypeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marine.httpd.codec.HttpRequest;
import com.marine.httpd.codec.HttpResponse;
import com.marine.httpd.exception.ForbiddenException;
import com.marine.httpd.exception.NotFoundException;
import com.marine.httpd.factory.BeanFactory;
import com.marine.httpd.model.Host;
import com.marine.httpd.server.ConfigMap;

public class RequestHandler extends Thread {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Socket connection;
	private Host host;
	private BeanFactory factory;
	
	public RequestHandler(Socket connectionSocket, BeanFactory factory) {
		this.connection = connectionSocket;
		this.factory = factory;
	}

	public void run() {
		logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
		
		try{
			
			OutputStream out	= connection.getOutputStream();
			InputStream is		= connection.getInputStream();
			HttpRequest req		= new HttpRequest(is);
			HttpResponse res	= new HttpResponse();
			this.host			= ConfigMap.getHost(req.getHost());
			
			if(this.host == null) throw new NullPointerException();
			
			RequestProcessor rm = new RequestProcessor(this.host, this.factory.getMap(), "UTF-8");
			
			rm.getResponse(req, res);
			
			out.write(res.getBytes());
			out.flush();
			
			logger.info("server response\r\n{}",new String(res.getBytes()));

		} catch (IOException e) {
			logger.error("IOException", e);
		} catch (NotFoundException n) {
			try {
				sendError(new OutputStreamWriter(connection.getOutputStream()), "404", host);
			} catch (IOException e) {
				
			}
		} catch (ForbiddenException f) {
			try {
				sendError(new OutputStreamWriter(connection.getOutputStream()), "403", host);
			} catch (IOException e) {
				
			}
		} catch ( Exception ex){
			
			try {
				sendError(new OutputStreamWriter(connection.getOutputStream()), "500", host);
			} catch (IOException e) {
				
			}
			logger.error("Exception", ex);
		}finally{
			try {
				connection.close();
			} catch (IOException e) {
				logger.error("IOException", e);
			}
		}
	}
	
	private void sendError(Writer out, String statusCode, Host host) throws IOException{
		
		String fileName = "";
		String httpVersion = String.format("%s %s %s \r\n", "HTTP/1.1", statusCode, HttpResponse.status.get(statusCode));
		out.write(httpVersion);
	
		if(host != null){
			switch (statusCode) {
			case "403":
				fileName = host.getForbidden();
				break;
			case "404":
				fileName = host.getNotFound();
				break;
			case "500":
				fileName = host.getServerError();
				break;
			default:
				fileName = host.getServerError();
				break;
			}
			
			
			File root = new File(host.getDocumentRoot());
			File file = new File(root, fileName);
			
			String contentType = new MimetypesFileTypeMap().getContentType(file);
			
			if (file.canRead() && file.getCanonicalPath().startsWith(host.getDocumentRoot())) {
				byte[] data = Files.readAllBytes(file.toPath());
				out.write("Content-Length: "+ data.length + "\r\n");
				out.write("Content-Type: "+ contentType + "\r\n");
				out.write("\r\n");
				out.write(new String(data));
				
			}else{
				String data = "Server Error!";
				out.write("Content-length: "+ data.length() + "\r\n");
				out.write("Content-Type: text/html;charset=UTF-8 \r\n");
				out.write("\r\n");
				out.write(new String(data));
			}
		}else{
			String data = "Server Error!";
			out.write("Content-length: "+ data.length() + "\r\n");
			out.write("Content-Type: text/html;charset=UTF-8 \r\n");
			out.write("\r\n");
			out.write(new String(data));
		}
		out.flush();
	}
}
