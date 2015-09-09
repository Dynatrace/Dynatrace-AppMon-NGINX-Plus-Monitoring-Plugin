package com.dynatrace.plugin.nginx.calculator;

import com.dynatrace.plugin.nginx.dto.NginxStatus;

public class RequestsCalculator extends TimeFrameCalculator implements Calculator {
	private double totalRequests = 0.0;

	public void calculate(NginxStatus prev, NginxStatus cur) {
		double time_frame = calculateTimeFrame(prev, cur);
		this.calculateTotalRequests(prev, cur, time_frame);
	}

	public void calculateTotalRequests(NginxStatus prev, NginxStatus cur, double time_frame) {
		this.setTotalRequests((cur.getRequests().getTotal() - prev.getRequests().getTotal()) / time_frame);
	}

	public double getTotalRequests() {
		return totalRequests;
	}

	public void setTotalRequests(double totalRequests) {
		this.totalRequests = totalRequests;
	}
}
