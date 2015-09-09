package com.dynatrace.plugin.nginx.calculator;

import com.dynatrace.plugin.nginx.dto.NginxStatus;

public class CalculatorImpl implements Calculator{
	private ConnectionsCalculator connectionsCalculator;
	private RequestsCalculator requestsCalculator;
	private UpstreamsCalculator upstreamsCalculator;
	private ServerZonesCalculator serverZonesCalculator;
	private CachesCalculator cachesCalculator;
	private StreamCalculator streamCalculator;

	public CalculatorImpl() {
		setConnectionsCalculator(new ConnectionsCalculator());
		setRequestsCalculator(new RequestsCalculator());
		setUpstreamsCalculator(new UpstreamsCalculator());
		setServerZonesCalculator(new ServerZonesCalculator());
		setCachesCalculator(new CachesCalculator());
		setStreamCalculator(new StreamCalculator());
	}

	public void calculate(NginxStatus prev, NginxStatus cur) {
		if(prev == null) {
			return;
		}

		getConnectionsCalculator().calculate(prev, cur);
		getRequestsCalculator().calculate(prev, cur);
		getUpstreamsCalculator().calculate(prev, cur);
		getServerZonesCalculator().calculate(prev, cur);
		getCachesCalculator().calculate(prev, cur);
		getStreamCalculator().calculate(prev, cur);
	}

	public ConnectionsCalculator getConnectionsCalculator() {
		return connectionsCalculator;
	}

	public void setConnectionsCalculator(ConnectionsCalculator connectionsCalculator) {
		this.connectionsCalculator = connectionsCalculator;
	}

	public RequestsCalculator getRequestsCalculator() {
		return requestsCalculator;
	}

	public void setRequestsCalculator(RequestsCalculator requestsCalculator) {
		this.requestsCalculator = requestsCalculator;
	}

	public UpstreamsCalculator getUpstreamsCalculator() {
		return upstreamsCalculator;
	}

	public void setUpstreamsCalculator(UpstreamsCalculator upstreamsCalculator) {
		this.upstreamsCalculator = upstreamsCalculator;
	}

	public ServerZonesCalculator getServerZonesCalculator() {
		return serverZonesCalculator;
	}

	public void setServerZonesCalculator(ServerZonesCalculator serverZonesCalculator) {
		this.serverZonesCalculator = serverZonesCalculator;
	}

	public CachesCalculator getCachesCalculator() {
		return cachesCalculator;
	}

	public void setCachesCalculator(CachesCalculator cachesCalculator) {
		this.cachesCalculator = cachesCalculator;
	}

	public StreamCalculator getStreamCalculator() {
		return streamCalculator;
	}

	public void setStreamCalculator(StreamCalculator streamCalculator) {
		this.streamCalculator = streamCalculator;
	}
}
