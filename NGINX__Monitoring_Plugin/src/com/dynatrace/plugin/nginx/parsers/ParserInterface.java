package com.dynatrace.plugin.nginx.parsers;

import org.json.JSONException;
import org.json.JSONObject;


public interface ParserInterface {

	public Object parse(JSONObject jsonObject) throws JSONException;
}
