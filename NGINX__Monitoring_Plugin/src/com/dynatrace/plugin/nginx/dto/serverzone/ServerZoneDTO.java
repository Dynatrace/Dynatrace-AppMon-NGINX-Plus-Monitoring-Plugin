package com.dynatrace.plugin.nginx.dto.serverzone;

public class ServerZoneDTO {
	private String serverZoneName;
	private Double processing;
	private Double requests;
	private Double responses1xx;
	private Double responses2xx;
	private Double responses3xx;
	private Double responses4xx;
	private Double responses5xx;
	private Double totalResponses;
	private Double discarded;
	private Double received;
	private Double sent;

	public ServerZoneDTO(String serverZoneName) {
		this.setServerZoneName(serverZoneName);
	}

	public String getServerZoneName() {
		return serverZoneName;
	}

	public void setServerZoneName(String serverZoneName) {
		this.serverZoneName = serverZoneName;
	}

	public Double getProcessing() {
		return processing;
	}

	public void setProcessing(Double processing) {
		this.processing = processing;
	}

	public Double getRequests() {
		return requests;
	}

	public void setRequests(Double requests) {
		this.requests = requests;
	}

	public Double getResponses1xx() {
		return responses1xx;
	}

	public void setResponses1xx(Double responses1xx) {
		this.responses1xx = responses1xx;
	}

	public Double getResponses2xx() {
		return responses2xx;
	}

	public void setResponses2xx(Double responses2xx) {
		this.responses2xx = responses2xx;
	}

	public Double getResponses3xx() {
		return responses3xx;
	}

	public void setResponses3xx(Double responses3xx) {
		this.responses3xx = responses3xx;
	}

	public Double getResponses4xx() {
		return responses4xx;
	}

	public void setResponses4xx(Double responses4xx) {
		this.responses4xx = responses4xx;
	}

	public Double getResponses5xx() {
		return responses5xx;
	}

	public void setResponses5xx(Double responses5xx) {
		this.responses5xx = responses5xx;
	}

	public Double getTotalResponses() {
		return totalResponses;
	}

	public void setTotalResponses(Double responsestotal) {
		this.totalResponses = responsestotal;
	}

	public Double getReceived() {
		return received;
	}

	public void setReceived(Double received) {
		this.received = received;
	}

	public Double getSent() {
		return sent;
	}

	public void setSent(Double sent) {
		this.sent = sent;
	}

	public Double getDiscarded() {
		return discarded;
	}

	public void setDiscarded(Double discarded) {
		this.discarded = discarded;
	}
}
