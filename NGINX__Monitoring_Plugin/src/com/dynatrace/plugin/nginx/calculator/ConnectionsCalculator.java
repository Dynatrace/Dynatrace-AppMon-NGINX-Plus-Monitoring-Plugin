package com.dynatrace.plugin.nginx.calculator;

import com.dynatrace.plugin.nginx.dto.NginxStatus;

public class ConnectionsCalculator extends TimeFrameCalculator implements Calculator{
	private double accepted = 0.0;
	private double dropped = 0.0;

	public void calculate(NginxStatus prev, NginxStatus cur) {
		double time_frame = calculateTimeFrame(prev, cur);
		this.calculateAccepted(prev, cur, time_frame);
		this.calculateDropped(prev, cur, time_frame);
	}

	public void calculateAccepted(NginxStatus prev, NginxStatus cur, double time_frame) {
		this.setAccepted((cur.getConnections().getAccepted() - prev.getConnections().getAccepted()) / time_frame);
	}

	public void calculateDropped(NginxStatus prev, NginxStatus cur, double time_frame) {
		this.setDropped((cur.getConnections().getDropped() - prev.getConnections().getDropped()) / time_frame);
	}

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
}
