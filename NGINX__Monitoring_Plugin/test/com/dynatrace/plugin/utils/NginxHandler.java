package com.dynatrace.plugin.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class NginxHandler implements HttpHandler {

	private static File jsonFile;

	public NginxHandler(String json_file_path) throws FileNotFoundException {
		jsonFile = new File(json_file_path);
		if (!jsonFile.exists() || jsonFile.isDirectory()) {
			throw new FileNotFoundException("File not exists, or is a directory");
		}
	}

	@Override
	public void handle(HttpExchange t) throws IOException {
		byte[] bytearray = GetByteArrayFromFile(jsonFile);

		Headers h = t.getResponseHeaders();
		h.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
		h.add(HttpHeaders.ACCEPT_ENCODING, "UTF-8");
		t.sendResponseHeaders(200, jsonFile.length());
		OutputStream os = t.getResponseBody();
		os.write(bytearray, 0, bytearray.length);
		os.close();
	}

	private byte[] GetByteArrayFromFile(File jsonFile) throws IOException {
		byte[] bytearray = new byte[(int) jsonFile.length()];
		FileInputStream fis = new FileInputStream(jsonFile);
		BufferedInputStream bis = new BufferedInputStream(fis);
		bis.read(bytearray, 0, bytearray.length);
		bis.close();
		return bytearray;
	}
}
