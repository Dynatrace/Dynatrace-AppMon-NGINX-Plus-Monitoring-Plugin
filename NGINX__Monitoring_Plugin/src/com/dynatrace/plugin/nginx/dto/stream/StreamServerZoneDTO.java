package com.dynatrace.plugin.nginx.dto.stream;

public class StreamServerZoneDTO {
	private String serverZoneName;
	private Double processing;
	private Double connections;
	private Double received;
	private Double sent;

	public StreamServerZoneDTO(String serverZoneName) {
		this.serverZoneName = serverZoneName;
	}

	public String getServerZoneName() {
		return serverZoneName;
	}

	public Double getProcessing() {
		return processing;
	}

	public void setProcessing(Double processing) {
		this.processing = processing;
	}

	public Double getSent() {
		return sent;
	}

	public void setSent(Double sent) {
		this.sent = sent;
	}

	public Double getReceived() {
		return received;
	}

	public void setReceived(Double received) {
		this.received = received;
	}

	public Double getConnections() {
		return connections;
	}

	public void setConnections(Double connections) {
		this.connections = connections;
	}
}
