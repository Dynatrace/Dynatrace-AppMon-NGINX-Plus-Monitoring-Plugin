package com.dynatrace.plugin.nginx.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.RequestsDTO;

public class RequestsParser implements ParserInterface {

	public static String GROUP = "NGINX Plus Monitor Requests";

	@Override
	public Object parse(JSONObject jsonObject) throws JSONException {
		JSONObject requests = jsonObject.getJSONObject("requests");

		RequestsDTO requestDTO = new RequestsDTO();
        requestDTO.setTotal(requests.getDouble("total"));
        requestDTO.setCurrent(requests.getDouble("current"));
        return requestDTO;
	}
}
