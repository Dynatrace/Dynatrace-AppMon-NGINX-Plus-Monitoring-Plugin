package com.dynatrace.plugin.nginx.utils;

public class ServerMatcher {

	public static boolean Match(String server, String pattern) {
		return server.startsWith(pattern);
	}
}
