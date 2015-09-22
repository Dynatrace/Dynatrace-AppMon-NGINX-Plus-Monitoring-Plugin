package com.dynatrace.plugin.nginx.dto.stream;

import com.dynatrace.plugin.nginx.dto.StateT;


public class StreamServerDTO {
	private Double id;
	private String server;
	private Boolean backup;
	private Double weight;
	private StateT state;
	private Double active;
	private Double maxConns;
	private Double connections;
	private Double connectTime;  //least_time
	private Double firstByteTime;//least_time
	private Double responseTime; //least_time
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

	public Double getConnections() {
		return connections;
	}

	public void setConnections(Double connections) {
		this.connections = connections;
	}

	public Double getFirstByteTime() {
		return firstByteTime;
	}

	public void setFirstByteTime(Double firstByteTime) {
		this.firstByteTime = firstByteTime;
	}

	public Double getConnectTime() {
		return connectTime;
	}

	public void setConnectTime(Double connectTime) {
		this.connectTime = connectTime;
	}

	public Double getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Double responseTime) {
		this.responseTime = responseTime;
	}
}
