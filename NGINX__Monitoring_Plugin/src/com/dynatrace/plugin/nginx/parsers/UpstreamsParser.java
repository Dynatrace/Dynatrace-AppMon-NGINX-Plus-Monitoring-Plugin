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
		ServerGroups serverGroups = new ServerGroups();

		JSONObject upstreams = jsonObject.optJSONObject("upstreams");

		if (upstreams == null) {
			return serverGroups;
		}

		Iterator<?> upStreamNames = upstreams.keys();
		while (upStreamNames.hasNext()) {
			String upStreamName = (String) upStreamNames.next();
			serverGroups.createNewServerGroup(upStreamName);
			JSONArray upStream = upstreams.getJSONObject(upStreamName).getJSONArray("peers");

			for (int i = 0; i < upStream.length(); i++) {
				JSONObject server = upStream.getJSONObject(i);
				ServerDTO serverDTO = new ServerDTO();

				serverDTO.setId(server.optDouble("id", Double.NaN));
				serverDTO.setServer(server.optString("server", ""));
				serverDTO.setBackup(server.optBoolean("backup", false));
				serverDTO.setWeight(server.optDouble("weight", Double.NaN));
				serverDTO.setState(new StateT(server.optString("state", "")));
				serverDTO.setActive(server.optDouble("active", Double.NaN));
				serverDTO.setMaxConns(server.optDouble("max_conns", Double.NaN));
				serverDTO.setRequests(server.optDouble("requests", Double.NaN));

				{
					JSONObject responses = server.optJSONObject("responses");
					if (responses != null) {
						serverDTO.setResponses1xx(responses.optDouble("1xx", Double.NaN));
						serverDTO.setResponses2xx(responses.optDouble("2xx", Double.NaN));
						serverDTO.setResponses3xx(responses.optDouble("3xx", Double.NaN));
						serverDTO.setResponses4xx(responses.optDouble("4xx", Double.NaN));
						serverDTO.setResponses5xx(responses.optDouble("5xx", Double.NaN));
						serverDTO.setTotalResponses(responses.optDouble("total", Double.NaN));
					}
				}

				serverDTO.setSent(server.optDouble("sent", Double.NaN));
				serverDTO.setReceived(server.optDouble("received", Double.NaN));
				serverDTO.setFails(server.optDouble("fails", Double.NaN));
				serverDTO.setUnavail(server.optDouble("unavail", Double.NaN));

				{
					JSONObject health_checks = server.optJSONObject("health_checks");
					if (health_checks != null) {
						serverDTO.setHealthChecksTotal(health_checks.optDouble("checks", Double.NaN));
						serverDTO.setHealthChecksFails(health_checks.optDouble("fails", Double.NaN));
						serverDTO.setHealthCheckUnhealthy(health_checks.optDouble("unhealthy", Double.NaN));
						serverDTO.setHealthChecksLastPassed(health_checks.optBoolean("last_passed", true));
					}
				}

				serverDTO.setDowntime(server.optDouble("downtime", Double.NaN));
				serverDTO.setDownstart(server.optDouble("downstart", Double.NaN));
				serverDTO.setSelected(server.optDouble("selected", Double.NaN));

				serverGroups.addServerByGroup(upStreamName, serverDTO);
			}
		}
		return serverGroups;
	}
}
