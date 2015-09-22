package com.dynatrace.plugin.nginx.dto.upstreams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerGroups {
	private Map<String, ArrayList<ServerDTO>> serverGroups;

	public ServerGroups() {
		this.serverGroups = new HashMap<String, ArrayList<ServerDTO>>();
	}

	public ArrayList<ServerDTO> createNewServerGroup(String serverGroupName) {
		ArrayList<ServerDTO> serverGroup = new ArrayList<ServerDTO>();
		this.serverGroups.put(serverGroupName, serverGroup);
		return serverGroup;
	}

	public void addServerByGroup(String serverGroup, ServerDTO server) {
		ArrayList<ServerDTO> upstream = serverGroups.get(serverGroup);
		upstream.add(server.getId().intValue(), server);
	}

	public Map<String, ArrayList<ServerDTO>> get() {
		return this.serverGroups;
	}
}
