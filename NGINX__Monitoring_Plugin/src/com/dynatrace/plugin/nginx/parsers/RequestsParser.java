package com.dynatrace.plugin.nginx.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.RequestsDTO;

public class RequestsParser {

	public static String GROUP = "NGINX Plus Monitor Requests";

	public static RequestsDTO parse(JSONObject jsonObject) throws JSONException {
		JSONObject requests = jsonObject.getJSONObject("requests");
		RequestsDTO requestDTO = new RequestsDTO();
        requestDTO.setTotal(requests.getDouble("total"));
        requestDTO.setCurrent(requests.getDouble("current"));
        return requestDTO;
	}
}
