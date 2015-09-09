package com.dynatrace.plugin.nginx.dto;

public class ConnectionsDTO {
	private double accepted;
	private double dropped;
	private double active;
	private double idle;

	public double getAccepted() {
		return accepted;
	}

	public void setAccepted(double accepted) {
		this.accepted = accepted;
	}

	public double getDropped() {
		return dropped;
	}

	public void setDropped(double dropped) {
		this.dropped = dropped;
	}

	public double getActive() {
		return active;
	}

	public void setActive(double active) {
		this.active = active;
	}

	public double getIdle() {
		return idle;
	}

	public void setIdle(double idle) {
		this.idle = idle;
	}
}
