package com.dynatrace.plugin.nginx.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.ConnectionsDTO;

public class ConnectionsParser implements ParserInterface {

	public static String GROUP = "NGINX Plus Monitor Connections";

	@Override
	public Object parse(JSONObject jsonObject) throws JSONException {
		ConnectionsDTO connectionsDTO = new ConnectionsDTO();
		JSONObject connections = jsonObject.optJSONObject("connections");
		if (connections == null) {
			return connectionsDTO;
		}
		connectionsDTO.setAccepted(connections.optDouble("accepted", Double.NaN));
		connectionsDTO.setDropped(connections.optDouble("dropped", Double.NaN));
		connectionsDTO.setActive(connections.optDouble("active", Double.NaN));
		connectionsDTO.setIdle(connections.optDouble("idle", Double.NaN));
		return connectionsDTO;
	}
}
