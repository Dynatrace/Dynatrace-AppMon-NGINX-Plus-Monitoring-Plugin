package com.dynatrace.plugin.nginx.parsers;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.StateT;
import com.dynatrace.plugin.nginx.dto.Stream;
import com.dynatrace.plugin.nginx.dto.StreamServerDTO;
import com.dynatrace.plugin.nginx.dto.StreamServerZoneDTO;

public class StreamParser {

	public static String GROUP = "NGINX Plus Monitor Stream";

	public static Stream parse(JSONObject jsonObject) throws JSONException {
		Stream streamDTO = new Stream();
		JSONObject stream = jsonObject.getJSONObject("stream");
		JSONObject serverZones = stream.getJSONObject("server_zones");

		Iterator<?> serverZoneNames = serverZones.keys();
		while (serverZoneNames.hasNext()) {
			String serverZoneName = (String)serverZoneNames.next();
			StreamServerZoneDTO server = new StreamServerZoneDTO(serverZoneName);
			JSONObject serverZone = serverZones.getJSONObject(serverZoneName);
			server.setProcessing(serverZone.getDouble("processing"));
			server.setConnections(serverZone.getDouble("connections"));
			server.setReceived(serverZone.getDouble("received"));
			server.setSent(serverZone.getDouble("sent"));
			streamDTO.getServerZones().add(server);
		}

		JSONObject upstreams = stream.getJSONObject("upstreams");

		Iterator<?> upstreamsNames = upstreams.keys();
		while (upstreamsNames.hasNext()) {
			String upstreamName = (String)upstreamsNames.next();
			streamDTO.getUpstreams().createNewServerGroup(upstreamName);
			JSONArray upstream = upstreams.getJSONObject(upstreamName).getJSONArray("peers");
			for (int i = 0; i < upstream.length(); i ++) {
				JSONObject server = upstream.getJSONObject(i);
				StreamServerDTO streamServerDTO = new StreamServerDTO();
				streamServerDTO.setId(server.getDouble("id"));
				streamServerDTO.setServer(server.getString("server"));
				streamServerDTO.setBackup(server.getBoolean("backup"));
				streamServerDTO.setWeight(server.getDouble("weight"));
				streamServerDTO.setState(new StateT(server.getString("state")));
				streamServerDTO.setActive(server.getDouble("active"));
				if (server.has("max_conns")) {
					streamServerDTO.setMaxConns(server.getDouble("max_conns"));
				}
				if (server.has("connections")) {
					streamServerDTO.setConnections(server.getDouble("connections"));
				}
				if (server.has("connect_time")) {
					streamServerDTO.setConnectTime(server.getDouble("connect_time"));
				}
				if (server.has("first_byte_time")) {
					streamServerDTO.setFirstByteTime(server.getDouble("first_byte_time"));
				}
				if (server.has("response_time")) {
					streamServerDTO.setResponseTime(server.getDouble("response_time"));
				}
				streamServerDTO.setSent(server.getDouble("sent"));
				streamServerDTO.setReceived(server.getDouble("received"));
				streamServerDTO.setFails(server.getDouble("fails"));
				streamServerDTO.setUnavail(server.getDouble("unavail"));
				{
					JSONObject health_checks = server.getJSONObject("health_checks");
					streamServerDTO.setHealthChecksTotal(health_checks.getDouble("checks"));
					streamServerDTO.setHealthChecksFails(health_checks.getDouble("fails"));
					streamServerDTO.setHealthCheckUnhealthy(health_checks.getDouble("unhealthy"));
					if (health_checks.has("last_passed")) {
						streamServerDTO.setHealthChecksLastPassed(health_checks.getBoolean("last_passed"));
					}
				}
				streamServerDTO.setDowntime(server.getDouble("downtime"));
				streamServerDTO.setDownstart(server.getDouble("downstart"));
				streamServerDTO.setSelected(server.getDouble("selected"));

				streamDTO.getUpstreams().addServerByGroup(upstreamName, streamServerDTO);
			}
		}

		return streamDTO;
	}
}
