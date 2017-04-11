package com.marine.httpd.server;

import java.io.InputStream;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.marine.httpd.model.Host;
import com.marine.httpd.model.ServerConfig;

public class ConfigMap {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigMap.class);
	private static ServerConfig config;;

	public ConfigMap() {
		StringBuilder result = new StringBuilder("");

		ClassLoader classLoader = getClass().getClassLoader();
		InputStream input = classLoader.getResourceAsStream("application.json");

		Scanner scanner = new Scanner(input);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			result.append(line).append("\n");
		}

		scanner.close();

		config = new Gson().fromJson(result.toString(), ServerConfig.class);
	}

	public static ServerConfig getConfig() {
		return config;
	}

	public static Host getHost(String serverName) {
		Host result = null;

		serverName = serverName.toLowerCase();

		for (Host host : config.getHosts()) {
			if (serverName.equals(host.getServerName().toLowerCase()) == true) {
				result = host;
				break;
			}
		}

		return result;
	}

}
