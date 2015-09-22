package com.dynatrace.plugin.nginx.parsers;


public class ParserCollection {
	private ParserInterface metaParser;
	private ParserInterface cachesParser;
	private ParserInterface connectionsParser;
	private ParserInterface requestsParser;
	private ParserInterface sslParser;
	private ParserInterface serverZonesParser;
	private ParserInterface streamParser;
	private ParserInterface upstreamsParser;

	public ParserCollection() {
	}

	public ParserInterface getMetaParser() {
		return metaParser;
	}

	public void setMetaParser(ParserInterface metaParser) {
		this.metaParser = metaParser;
	}

	public ParserInterface getCachesParser() {
		return cachesParser;
	}

	public void setCachesParser(ParserInterface cachesParser) {
		this.cachesParser = cachesParser;
	}

	public ParserInterface getConnectionsParser() {
		return connectionsParser;
	}

	public void setConnectionsParser(ParserInterface connectionsParser) {
		this.connectionsParser = connectionsParser;
	}

	public ParserInterface getRequestsParser() {
		return requestsParser;
	}

	public void setRequestsParser(ParserInterface requestsParser) {
		this.requestsParser = requestsParser;
	}

	public ParserInterface getSslParser() {
		return sslParser;
	}

	public void setSslParser(ParserInterface sslParser) {
		this.sslParser = sslParser;
	}

	public ParserInterface getServerZonesParser() {
		return serverZonesParser;
	}

	public void setServerZonesParser(ParserInterface serverZonesParser) {
		this.serverZonesParser = serverZonesParser;
	}

	public ParserInterface getStreamParser() {
		return streamParser;
	}

	public void setStreamParser(ParserInterface streamParser) {
		this.streamParser = streamParser;
	}

	public ParserInterface getUpstreamsParser() {
		return upstreamsParser;
	}

	public void setUpstreamsParser(ParserInterface upstreamsParser) {
		this.upstreamsParser = upstreamsParser;
	}
}
