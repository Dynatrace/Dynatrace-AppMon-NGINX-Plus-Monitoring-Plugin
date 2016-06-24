package com.dynatrace.plugin.nginx.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.RequestsDTO;

public class RequestsParser implements ParserInterface {

	public static String GROUP = "NGINX Plus Monitor Requests";

	@Override
	public Object parse(JSONObject jsonObject) throws JSONException {
		RequestsDTO requestDTO = new RequestsDTO();
		JSONObject requests = jsonObject.optJSONObject("requests");
		if (requests == null) {
			return requestDTO;
		}
		requestDTO.setTotal(requests.optDouble("total", Double.NaN));
		requestDTO.setCurrent(requests.optDouble("current", Double.NaN));
		return requestDTO;
	}
}
