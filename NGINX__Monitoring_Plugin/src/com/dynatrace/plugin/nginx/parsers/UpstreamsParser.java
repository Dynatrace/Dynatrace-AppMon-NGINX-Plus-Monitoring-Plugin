package com.dynatrace.plugin.nginx.parsers;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.StateT;
import com.dynatrace.plugin.nginx.dto.upstreams.ServerDTO;
import com.dynatrace.plugin.nginx.dto.upstreams.ServerGroups;

public class UpstreamsParser implements ParserInterface {

	public static String GROUP = "NGINX Plus Monitor Upstreams";

	@Override
	public Object parse(JSONObject jsonObject) throws JSONException {
		JSONObject upstreams = jsonObject.getJSONObject("upstreams");

		ServerGroups serverGroups = new ServerGroups();

		Iterator<?> upStreamNames = upstreams.keys();
		while (upStreamNames.hasNext()) {
			String upStreamName = (String)upStreamNames.next();
			serverGroups.createNewServerGroup(upStreamName);
			JSONArray upStream = upstreams.getJSONObject(upStreamName).getJSONArray("peers");

			for (int i = 0; i < upStream.length(); i++) {
				JSONObject server = upStream.getJSONObject(i);
				ServerDTO serverDTO = new ServerDTO();

				serverDTO.setId(server.getDouble("id"));
				serverDTO.setServer(server.getString("server"));
				serverDTO.setBackup(server.getBoolean("backup"));
				serverDTO.setWeight(server.getDouble("weight"));
				serverDTO.setState(new StateT(server.getString("state")));
				serverDTO.setActive(server.getDouble("active"));
				if (server.has("max_conns")) {
					serverDTO.setMaxConns(server.getDouble("max_conns"));
				}
				serverDTO.setRequests(server.getDouble("requests"));

				{
					JSONObject responses = server.getJSONObject("responses");
					serverDTO.setResponses1xx(responses.getDouble("1xx"));
					serverDTO.setResponses2xx(responses.getDouble("2xx"));
					serverDTO.setResponses3xx(responses.getDouble("3xx"));
					serverDTO.setResponses4xx(responses.getDouble("4xx"));
					serverDTO.setResponses5xx(responses.getDouble("5xx"));
					serverDTO.setTotalResponses(responses.getDouble("total"));
				}

				serverDTO.setSent(server.getDouble("sent"));
				serverDTO.setReceived(server.getDouble("received"));
				serverDTO.setFails(server.getDouble("fails"));
				serverDTO.setUnavail(server.getDouble("unavail"));

				{
					JSONObject health_checks = server.getJSONObject("health_checks");
					serverDTO.setHealthChecksTotal(health_checks.getDouble("checks"));
					serverDTO.setHealthChecksFails(health_checks.getDouble("fails"));
					serverDTO.setHealthCheckUnhealthy(health_checks.getDouble("unhealthy"));
					if (health_checks.has("last_passed")) {
						serverDTO.setHealthChecksLastPassed(health_checks.getBoolean("last_passed"));
					}
				}

				serverDTO.setDowntime(server.getDouble("downtime"));
				serverDTO.setDownstart(server.getDouble("downstart"));
				serverDTO.setSelected(server.getDouble("selected"));

				serverGroups.addServerByGroup(upStreamName, serverDTO);
			}
		}
		return serverGroups;
	}
}
