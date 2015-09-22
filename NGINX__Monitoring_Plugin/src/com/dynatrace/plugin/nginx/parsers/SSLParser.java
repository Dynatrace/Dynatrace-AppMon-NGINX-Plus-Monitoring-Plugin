package com.dynatrace.plugin.nginx.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.SSLDTO;

public class SSLParser implements ParserInterface {

	public static String GROUP = "NGINX Plus Monitor SSL";

	@Override
	public Object parse(JSONObject jsonObject) throws JSONException {
		JSONObject ssl = jsonObject.getJSONObject("ssl");
		SSLDTO sslDTO = new SSLDTO();
		sslDTO.setHandshakes(ssl.getDouble("handshakes"));
		sslDTO.setHandshakes_failed(ssl.getDouble("handshakes_failed"));
		sslDTO.setSession_reuses(ssl.getDouble("session_reuses"));
        return sslDTO;
	}
}
