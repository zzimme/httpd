package com.marine.httpd.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marine.httpd.factory.ClassFileBeanFactory;
import com.marine.httpd.handler.RequestHandler;
import com.marine.httpd.model.ServerConfig;

public class HttpServer {

	private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
	private static final int NUM_THREADS = 50;
	private final int DEFAULT_PORT = 8080;

	private int port;
	private ServerSocket server;
	private ClassFileBeanFactory factory;
	
	public HttpServer() throws IOException {

		ConfigMap configMap = new ConfigMap();
		ServerConfig config = ConfigMap.getConfig();
		this.factory = new ClassFileBeanFactory();

		this.port = config.getPort() < 0 || config.getPort() > 65535 ? this.DEFAULT_PORT : config.getPort();
		
	}

	public void start() throws IOException {
		ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
		server = new ServerSocket(this.port);

		logger.info("Starting Simple Web Application Server ... on port {}", this.port);

		while (true) {
			try {
				Socket connection = server.accept();
				logger.info("connection:{}",connection);
				
				RequestHandler requestHandler = new RequestHandler(connection,factory);
				pool.submit(requestHandler);
			} catch (IOException ex) {
				logger.error("Error accepting connection", ex);
			}
		}
		
		
	}

	public void stop() throws IOException{
		server.close();
	}
	
	public static void main(String[] args) throws IOException {

		HttpServer server = new HttpServer();
		server.start();
	}
}
