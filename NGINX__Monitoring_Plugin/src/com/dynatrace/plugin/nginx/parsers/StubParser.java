package com.dynatrace.plugin.nginx.parsers;

import org.json.JSONException;
import org.json.JSONObject;


public class StubParser implements ParserInterface {

	@Override
	public Object parse(JSONObject jsonObject) throws JSONException {
		return null;
	}
}
