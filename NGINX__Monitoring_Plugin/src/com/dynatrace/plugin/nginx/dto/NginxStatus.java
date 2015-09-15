package com.dynatrace.plugin.nginx.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.parsers.CachesParser;
import com.dynatrace.plugin.nginx.parsers.ConnectionsParser;
import com.dynatrace.plugin.nginx.parsers.MetaParser;
import com.dynatrace.plugin.nginx.parsers.RequestsParser;
import com.dynatrace.plugin.nginx.parsers.SSLParser;
import com.dynatrace.plugin.nginx.parsers.ServerZonesParser;
import com.dynatrace.plugin.nginx.parsers.StreamParser;
import com.dynatrace.plugin.nginx.parsers.UpstreamsParser;

public class NginxStatus {
	private MetaDTO meta;
	private ConnectionsDTO connections;
	private SSLDTO ssl;
	private RequestsDTO requests;
	private Collection<ServerZoneDTO> serverZones;
	private ServerGroups upstreams;
	private Collection<CacheDTO> caches;
	private Stream stream;

	public NginxStatus(JSONObject jsonObject) throws JSONException {
		meta = MetaParser.parse(jsonObject);
		connections = ConnectionsParser.parse(jsonObject);
		ssl = SSLParser.parse(jsonObject);
		requests = RequestsParser.parse(jsonObject);
		serverZones = ServerZonesParser.parse(jsonObject);
		upstreams = UpstreamsParser.parse(jsonObject);
		caches = CachesParser.parse(jsonObject);
		stream = StreamParser.parse(jsonObject);
	}

	public NginxStatus() {
		meta = new MetaDTO();
		connections = new ConnectionsDTO();
		ssl = new SSLDTO();
		requests = new RequestsDTO();
		serverZones = new ArrayList<ServerZoneDTO>();
		upstreams = new ServerGroups();
		caches = new ArrayList<CacheDTO>();
		stream = new Stream();
	}

	public MetaDTO getMeta() {
		return this.meta;
	}

	public ConnectionsDTO getConnections() {
		return this.connections;
	}

	public SSLDTO getSSL() {
		return this.ssl;
	}

	public RequestsDTO getRequests() {
		return this.requests;
	}

	public Collection<ServerZoneDTO> getServerZones() {
		return this.serverZones;
	}

	public ServerGroups getUpstreams() {
		return this.upstreams;
	}

	public Collection<CacheDTO> getCaches() {
		return this.caches;
	}

	public Stream getStream() {
		return stream;
	}
}
