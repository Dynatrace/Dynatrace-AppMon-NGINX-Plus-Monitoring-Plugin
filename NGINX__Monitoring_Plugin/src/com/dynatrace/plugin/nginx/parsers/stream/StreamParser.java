package com.dynatrace.plugin.nginx.parsers.stream;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.StateT;
import com.dynatrace.plugin.nginx.dto.stream.Stream;
import com.dynatrace.plugin.nginx.dto.stream.StreamServerDTO;
import com.dynatrace.plugin.nginx.dto.stream.StreamServerZoneDTO;
import com.dynatrace.plugin.nginx.parsers.ParserInterface;

public class StreamParser implements ParserInterface {

	public static String GROUP = "NGINX Plus Monitor Stream";
	private CommandParseUpstreams cmd;

	public StreamParser(CommandParseUpstreams cmd) {
		this.cmd = cmd;
	}

	public static StreamServerDTO createStreamServerDtoFromServer(JSONObject server) throws JSONException {
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



		return streamServerDTO;
	}

	private StreamServerZoneDTO createStreamServerZoneDtoFromServer(String serverZoneName, JSONObject serverZone) throws JSONException {
		StreamServerZoneDTO server = new StreamServerZoneDTO(serverZoneName);
		server.setProcessing(serverZone.getDouble("processing"));
		server.setConnections(serverZone.getDouble("connections"));
		server.setReceived(serverZone.getDouble("received"));
		server.setSent(serverZone.getDouble("sent"));
		return server;
	}

	private void parseServerZones(Stream streamDTO, JSONObject serverZones) throws JSONException {
		Iterator<?> serverZoneNames = serverZones.keys();
		while (serverZoneNames.hasNext()) {
			String serverZoneName = (String)serverZoneNames.next();
			JSONObject serverZone = serverZones.getJSONObject(serverZoneName);
			StreamServerZoneDTO server = createStreamServerZoneDtoFromServer(serverZoneName, serverZone);
			streamDTO.getServerZones().add(server);
		}
	}

	@Override
	public Object parse(JSONObject jsonObject) throws JSONException {
		JSONObject stream = jsonObject.getJSONObject("stream");

		Stream streamDTO = new Stream();

		JSONObject serverZones = stream.getJSONObject("server_zones");
		parseServerZones(streamDTO, serverZones);

		JSONObject upstreams = stream.getJSONObject("upstreams");
		cmd.parse(streamDTO, upstreams);


		return streamDTO;
	}
}
