package com.dynatrace.plugin.nginx.dto;


public class ServerDTO {

	private Double id;
	private String server;
	private Boolean backup;
	private Double weight;
	private StateT state;
	private Double active;
	private Double maxConns;
	private Double requests;
	private Double responses1xx;
	private Double responses2xx;
	private Double responses3xx;
	private Double responses4xx;
	private Double responses5xx;
	private Double totalResponses;
	private Double sent;
	private Double received;
	private Double fails;
	private Double unavailable;
	private Double healthChecksTotal;
	private Double healthChecksFails;
	private Double healthChecksUnhealthy;
	private Boolean healthChecksLastPassed;
	private Double downtime;
	private Double downstart;
	private Double selected;

	public Double getId() {
		return id;
	}

	public void setId(Double id) {
		this.id = id;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public Boolean getBackup() {
		return backup;
	}

	public void setBackup(Boolean backup) {
		this.backup = backup;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public StateT getState() {
		return state;
	}

	public void setState(StateT state) {
		this.state = state;
	}

	public Double getActive() {
		return active;
	}

	public void setActive(Double active) {
		this.active = active;
	}

	public Double getMaxConns() {
		return maxConns;
	}

	public void setMaxConns(Double maxConns) {
		this.maxConns = maxConns;
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

	public void setTotalResponses(Double totalResponses) {
		this.totalResponses = totalResponses;
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

	public Double getFails() {
		return fails;
	}

	public void setFails(Double fails) {
		this.fails = fails;
	}

	public Double getUnavail() {
		return unavailable;
	}

	public void setUnavail(Double unavailable) {
		this.unavailable = unavailable;
	}

	public Double getHealthChecksTotal() {
		return healthChecksTotal;
	}

	public void setHealthChecksTotal(Double healthChecksTotal) {
		this.healthChecksTotal = healthChecksTotal;
	}

	public Double getHealthChecksFails() {
		return healthChecksFails;
	}

	public void setHealthChecksFails(Double healthChecksFails) {
		this.healthChecksFails = healthChecksFails;
	}

	public Double getHealthChecksUnhealthy() {
		return healthChecksUnhealthy;
	}

	public void setHealthCheckUnhealthy(Double healthChecksUnhealthy) {
		this.healthChecksUnhealthy = healthChecksUnhealthy;
	}

	public Boolean getHealthChecksLastPassed() {
		return healthChecksLastPassed;
	}

	public void setHealthChecksLastPassed(Boolean healthChecksLastPassed) {
		this.healthChecksLastPassed = healthChecksLastPassed;
	}

	public Double getDowntime() {
		return downtime;
	}

	public void setDowntime(Double downtime) {
		this.downtime = downtime;
	}

	public Double getDownstart() {
		return downstart;
	}

	public void setDownstart(Double downstart) {
		this.downstart = downstart;
	}

	public Double getSelected() {
		return selected;
	}

	public void setSelected(Double selected) {
		this.selected = selected;
	}
}
