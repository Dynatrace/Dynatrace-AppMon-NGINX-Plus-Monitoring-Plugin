package com.dynatrace.plugin.nginx.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.dynatrace.plugin.nginx.NginxPlusMonitoringConnection;
import com.dynatrace.plugin.nginx.dto.NginxStatus;
import com.dynatrace.plugin.utils.NginxHandler;
import com.dynatrace.plugin.utils.SimpleHttpServer;
import com.sun.net.httpserver.HttpHandler;


public class VersionBackwardCompabilityAcceptanceTest {

	private SimpleHttpServer server;
	private Map<String, HttpHandler> contextMap = new HashMap<>();

	@Before
	public void setUp() throws Exception {
		contextMap.put("/status_R6", new NginxHandler("test/com/dynatrace/plugin/data/nginx_status_R6"));
		contextMap.put("/status_R5", new NginxHandler("test/com/dynatrace/plugin/data/nginx_status_R5"));
		server = new SimpleHttpServer(contextMap);
		server.start();
	}

	@After
	public void tearDown() throws Exception {
		server.stop();
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void TestFakeHttpServerGet() throws Exception {
		String currentEndpoint = "";
		try {
			for (String endpoint : contextMap.keySet()) {
				System.out.println("Running test case : " + endpoint);
				currentEndpoint = endpoint;
				NginxPlusMonitoringConnection connection = new NginxPlusMonitoringConnection("http", "localhost", server.getPort(), endpoint);
				new NginxStatus(connection.getStatusJson());
			}
		} catch (UnsupportedOperationException exception) {
			throw new Exception("[Endpoint : " + currentEndpoint + "] FAILED");
		}
	}
}
