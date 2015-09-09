package com.dynatrace.plugin.nginx.dto;

import java.util.ArrayList;
import java.util.Collection;

public class Stream {
	private Collection<StreamServerZoneDTO> serverZones = new ArrayList<StreamServerZoneDTO>();
	private StreamServerGroups upstreams = new StreamServerGroups();

	public Collection<StreamServerZoneDTO> getServerZones() {
		return serverZones;
	}

	public StreamServerGroups getUpstreams() {
		return upstreams;
	}
}
