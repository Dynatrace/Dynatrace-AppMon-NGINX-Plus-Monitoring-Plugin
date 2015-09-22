package com.dynatrace.plugin.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class SimpleHttpServer {

	private final static Logger log = Logger.getLogger(SimpleHttpServer.class.getName());
	private HttpServer server;

	public SimpleHttpServer(Map<String, HttpHandler> contextMap) throws IOException {
		server = HttpServer.create(new InetSocketAddress(0), 0);
		for (String endpoint : contextMap.keySet()) {
			server.createContext(endpoint, contextMap.get(endpoint));
		}
		server.setExecutor(null);
	}

	public void start() {
		server.start();
		log.info("FakeHttpServer started on port : " + server.getAddress().getPort());
	}

	public void stop() {
		server.stop(0);
		log.info("FakeHttpServer stopped on port : " + server.getAddress().getPort());
	}

	public int getPort() {
		return server.getAddress().getPort();
	}
}
