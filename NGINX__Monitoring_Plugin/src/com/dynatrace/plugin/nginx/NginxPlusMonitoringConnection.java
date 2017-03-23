package com.dynatrace.plugin.nginx;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;


public class NginxPlusMonitoringConnection {
	private HttpsURLConnection connection;
	private URLConnection plainconnection;

	public NginxPlusMonitoringConnection(String protocol, String host, int port, String file) throws IOException {
		URL url = new URL(protocol, host, port, file);
		if (protocol=="https") {
			try {
				this.connection = (HttpsURLConnection)url.openConnection();
				this.connection.setConnectTimeout((int) TimeUnit.SECONDS.toMillis(10));
				this.connection.setReadTimeout((int) TimeUnit.SECONDS.toMillis(10));
			} catch(MalformedURLException e) {
					throw e;
				}
			} else {
				this.plainconnection = url.openConnection();
				this.plainconnection.setConnectTimeout((int) TimeUnit.SECONDS.toMillis(10));
				this.plainconnection.setReadTimeout((int) TimeUnit.SECONDS.toMillis(10));
		}
	}

	public JSONObject getStatusJson() throws IllegalArgumentException, IOException, JSONException {
		String header ;
		InputStream inputStream;
			header = plainconnection.getHeaderField(HttpHeaders.CONTENT_TYPE);
			if (!MediaType.APPLICATION_JSON.equals(header)) {
				throw new IllegalArgumentException("Invalid response header, expected " + MediaType.APPLICATION_JSON + ", but got " + header);
			}
			inputStream = plainconnection.getInputStream();

		String charset = "UTF-8";
		Scanner InputStreamScanner = new Scanner(inputStream, charset);
		String jsonString = InputStreamScanner.useDelimiter("\\A").next();
		InputStreamScanner.close();
		return new JSONObject(jsonString);
	}
}
