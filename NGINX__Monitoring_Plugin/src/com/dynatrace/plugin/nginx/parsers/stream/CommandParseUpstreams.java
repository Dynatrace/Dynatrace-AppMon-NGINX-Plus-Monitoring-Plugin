package com.dynatrace.plugin.nginx.parsers.stream;

import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.stream.Stream;


public interface CommandParseUpstreams {

	void parse(Stream streamDTO, JSONObject upstreams) throws JSONException;
}
