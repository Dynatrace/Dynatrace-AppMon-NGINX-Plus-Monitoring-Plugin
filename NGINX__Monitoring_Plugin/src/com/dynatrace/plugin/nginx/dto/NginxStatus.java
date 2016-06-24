package com.dynatrace.plugin.nginx.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.serverzone.ServerZoneDTO;
import com.dynatrace.plugin.nginx.dto.stream.Stream;
import com.dynatrace.plugin.nginx.dto.upstreams.ServerGroups;
import com.dynatrace.plugin.nginx.parsers.ParserCollection;
import com.dynatrace.plugin.nginx.parsers.ParserFactory;

public class NginxStatus {

	private MetaDTO meta;
	private ConnectionsDTO connections;
	private SSLDTO ssl;
	private RequestsDTO requests;
	private Collection<ServerZoneDTO> serverZones;
	private ServerGroups upstreams;
	private Collection<CacheDTO> caches;
	private Stream stream;

	public NginxStatus(JSONObject jsonObject) throws JSONException, UnsupportedOperationException {
		int version = jsonObject.getInt("version");
		ParserCollection parserCollection = new ParserFactory().getParserCollection(version);
		meta = (MetaDTO) parserCollection.getMetaParser().parse(jsonObject);
		connections = (ConnectionsDTO) parserCollection.getConnectionsParser().parse(jsonObject);
		ssl = (SSLDTO) parserCollection.getSslParser().parse(jsonObject);
		requests = (RequestsDTO) parserCollection.getRequestsParser().parse(jsonObject);
		serverZones = (Collection<ServerZoneDTO>) parserCollection.getServerZonesParser().parse(jsonObject);
		upstreams = (ServerGroups) parserCollection.getUpstreamsParser().parse(jsonObject);
		caches = (Collection<CacheDTO>) parserCollection.getCachesParser().parse(jsonObject);
		stream = (Stream) parserCollection.getStreamParser().parse(jsonObject);
	}

	public NginxStatus() {
		meta = new MetaDTO();
		connections = new ConnectionsDTO();
		ssl = new SSLDTO();
		requests = new RequestsDTO();
		serverZones = new ArrayList<>();
		upstreams = new ServerGroups();
		caches = new ArrayList<>();
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
