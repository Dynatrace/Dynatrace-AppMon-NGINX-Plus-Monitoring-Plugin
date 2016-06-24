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
		streamServerDTO.setId(server.optDouble("id", Double.NaN));
		streamServerDTO.setServer(server.optString("server", ""));
		streamServerDTO.setBackup(server.optBoolean("backup", false));
		streamServerDTO.setWeight(server.optDouble("weight", Double.NaN));
		streamServerDTO.setState(new StateT(server.optString("state", "")));
		streamServerDTO.setActive(server.optDouble("active", Double.NaN));
		streamServerDTO.setMaxConns(server.optDouble("max_conns", Double.NaN));
		streamServerDTO.setConnections(server.optDouble("connections", Double.NaN));
		streamServerDTO.setConnectTime(server.optDouble("connect_time", Double.NaN));
		streamServerDTO.setFirstByteTime(server.optDouble("first_byte_time", Double.NaN));
		streamServerDTO.setResponseTime(server.optDouble("response_time", Double.NaN));
		streamServerDTO.setSent(server.optDouble("sent", Double.NaN));
		streamServerDTO.setReceived(server.optDouble("received", Double.NaN));
		streamServerDTO.setFails(server.optDouble("fails", Double.NaN));
		streamServerDTO.setUnavail(server.optDouble("unavail", Double.NaN));
		{
			JSONObject health_checks = server.optJSONObject("health_checks");
			if (health_checks != null) {
				streamServerDTO.setHealthChecksTotal(health_checks.optDouble("checks", Double.NaN));
				streamServerDTO.setHealthChecksFails(health_checks.optDouble("fails", Double.NaN));
				streamServerDTO.setHealthCheckUnhealthy(health_checks.optDouble("unhealthy", Double.NaN));
				streamServerDTO.setHealthChecksLastPassed(health_checks.optBoolean("last_passed", false));
			}
		}
		streamServerDTO.setDowntime(server.optDouble("downtime", Double.NaN));
		streamServerDTO.setDownstart(server.optDouble("downstart", Double.NaN));
		streamServerDTO.setSelected(server.optDouble("selected", Double.NaN));

		return streamServerDTO;
	}

	private StreamServerZoneDTO createStreamServerZoneDtoFromServer(String serverZoneName, JSONObject serverZone) throws JSONException {
		StreamServerZoneDTO server = new StreamServerZoneDTO(serverZoneName);
		server.setProcessing(serverZone.optDouble("processing", Double.NaN));
		server.setConnections(serverZone.optDouble("connections", Double.NaN));
		server.setReceived(serverZone.optDouble("received", Double.NaN));
		server.setSent(serverZone.optDouble("sent", Double.NaN));
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
		Stream streamDTO = new Stream();
		JSONObject stream = jsonObject.optJSONObject("stream");

		if (stream == null) {
			return streamDTO;
		}

		JSONObject serverZones = stream.optJSONObject("server_zones");
		if (serverZones != null) {
			parseServerZones(streamDTO, serverZones);
		}

		JSONObject upstreams = stream.optJSONObject("upstreams");
		if (upstreams != null) {
			cmd.parse(streamDTO, upstreams);
		}

		return streamDTO;
	}
}
