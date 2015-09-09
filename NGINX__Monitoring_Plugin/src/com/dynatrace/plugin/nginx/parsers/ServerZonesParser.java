package com.dynatrace.plugin.nginx.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.ServerZoneDTO;

public class ServerZonesParser {

	public static String GROUP = "NGINX Plus Monitor Server Zones";

	public static Collection<ServerZoneDTO> parse(JSONObject jsonObject) throws JSONException {
		Collection<ServerZoneDTO> serverZones_ = new ArrayList<>();
		JSONObject serverZones = jsonObject.getJSONObject("server_zones");
		Iterator<?> serverZoneNames = serverZones.keys();
		while (serverZoneNames.hasNext()) {
			String serverZoneName = (String)serverZoneNames.next();
			ServerZoneDTO serverZoneDTO = new ServerZoneDTO(serverZoneName);
			JSONObject serverZone = serverZones.getJSONObject(serverZoneName);
			serverZoneDTO.setProcessing(serverZone.getDouble("processing"));
			serverZoneDTO.setRequests(serverZone.getDouble("requests"));

			{
				JSONObject responses = serverZone.getJSONObject("responses");
				serverZoneDTO.setResponses1xx(responses.getDouble("1xx"));
				serverZoneDTO.setResponses2xx(responses.getDouble("2xx"));
				serverZoneDTO.setResponses3xx(responses.getDouble("3xx"));
				serverZoneDTO.setResponses4xx(responses.getDouble("4xx"));
				serverZoneDTO.setResponses5xx(responses.getDouble("5xx"));
				serverZoneDTO.setTotalResponses(responses.getDouble("total"));
			}

			serverZoneDTO.setReceived(serverZone.getDouble("received"));
			serverZoneDTO.setSent(serverZone.getDouble("sent"));
			serverZones_.add(serverZoneDTO);
		}
		return serverZones_;
	}
}
