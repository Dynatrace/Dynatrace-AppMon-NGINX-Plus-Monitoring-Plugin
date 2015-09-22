package com.dynatrace.plugin.nginx.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.ConnectionsDTO;

public class ConnectionsParser implements ParserInterface {

	public static String GROUP = "NGINX Plus Monitor Connections";

	@Override
	public Object parse(JSONObject jsonObject) throws JSONException {
		JSONObject connections = jsonObject.getJSONObject("connections");
		ConnectionsDTO connectionsDTO = new ConnectionsDTO();
		connectionsDTO.setAccepted(connections.getDouble("accepted"));
		connectionsDTO.setDropped(connections.getDouble("dropped"));
		connectionsDTO.setActive(connections.getDouble("active"));
		connectionsDTO.setIdle(connections.getDouble("idle"));
		return connectionsDTO;
	}
}
