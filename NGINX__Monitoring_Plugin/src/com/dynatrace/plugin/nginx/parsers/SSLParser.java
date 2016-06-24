package com.dynatrace.plugin.nginx.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.SSLDTO;

public class SSLParser implements ParserInterface {

	public static String GROUP = "NGINX Plus Monitor SSL";

	@Override
	public Object parse(JSONObject jsonObject) throws JSONException {
		SSLDTO sslDTO = new SSLDTO();
		JSONObject ssl = jsonObject.optJSONObject("ssl");

		if (ssl == null) {
			return sslDTO;
		}

		sslDTO.setHandshakes(ssl.optDouble("handshakes", Double.NaN));
		sslDTO.setHandshakes_failed(ssl.optDouble("handshakes_failed", Double.NaN));
		sslDTO.setSession_reuses(ssl.optDouble("session_reuses", Double.NaN));
		return sslDTO;
	}
}
