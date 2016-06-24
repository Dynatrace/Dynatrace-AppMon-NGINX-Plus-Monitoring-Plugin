package com.dynatrace.plugin.nginx.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.serverzone.ServerZoneDTO;

public class ServerZonesParser implements ParserInterface {

	public static String GROUP = "NGINX Plus Monitor Server Zones";

	@Override
	public Object parse(JSONObject jsonObject) throws JSONException {
		Collection<ServerZoneDTO> serverZones_ = new ArrayList<>();

		JSONObject serverZones = jsonObject.optJSONObject("server_zones");

		if (serverZones == null) {
			return serverZones_;
		}

		Iterator<?> serverZoneNames = serverZones.keys();
		while (serverZoneNames.hasNext()) {
			String serverZoneName = (String) serverZoneNames.next();
			ServerZoneDTO serverZoneDTO = new ServerZoneDTO(serverZoneName);
			JSONObject serverZone = serverZones.getJSONObject(serverZoneName);
			serverZoneDTO.setProcessing(serverZone.optDouble("processing", Double.NaN));
			serverZoneDTO.setRequests(serverZone.optDouble("requests", Double.NaN));

			{
				JSONObject responses = serverZone.optJSONObject("responses");
				if (responses != null) {
					serverZoneDTO.setResponses1xx(responses.optDouble("1xx", Double.NaN));
					serverZoneDTO.setResponses2xx(responses.optDouble("2xx", Double.NaN));
					serverZoneDTO.setResponses3xx(responses.optDouble("3xx", Double.NaN));
					serverZoneDTO.setResponses4xx(responses.optDouble("4xx", Double.NaN));
					serverZoneDTO.setResponses5xx(responses.optDouble("5xx", Double.NaN));
					serverZoneDTO.setTotalResponses(responses.optDouble("total", Double.NaN));
				}
			}
			serverZoneDTO.setDiscarded(serverZone.optDouble("discarded", Double.NaN));
			serverZoneDTO.setReceived(serverZone.optDouble("received", Double.NaN));
			serverZoneDTO.setSent(serverZone.optDouble("sent", Double.NaN));
			serverZones_.add(serverZoneDTO);
		}
		return serverZones_;
	}
}
