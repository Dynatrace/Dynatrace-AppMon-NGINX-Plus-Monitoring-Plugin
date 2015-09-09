package com.dynatrace.plugin.nginx.dto;

public class RequestsDTO {
	private double total;
	private double current;

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getCurrent() {
		return current;
	}

	public void setCurrent(double current) {
		this.current = current;
	}
}
