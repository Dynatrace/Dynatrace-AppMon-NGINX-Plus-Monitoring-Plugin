package com.dynatrace.plugin.nginx.dto.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StreamServerGroups {
	private Map<String, ArrayList<StreamServerDTO>> serverGroups;

	public StreamServerGroups() {
		this.serverGroups = new HashMap<>();
	}

	public ArrayList<StreamServerDTO> createNewServerGroup(String serverGroupName) {
		ArrayList<StreamServerDTO> serverGroup = new ArrayList<>();
		this.serverGroups.put(serverGroupName, serverGroup);
		return serverGroup;
	}

	public void addServerByGroup(String serverGroup, StreamServerDTO server) {
		ArrayList<StreamServerDTO> upstream = serverGroups.get(serverGroup);
		upstream.add(server);
	}

	public Map<String, ArrayList<StreamServerDTO>> get() {
		return this.serverGroups;
	}
}
