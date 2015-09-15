package com.dynatrace.plugin.nginx.dto;

public class SSLDTO {
	private double handshakes;
	private double handshakes_failed;
	private double session_reuses;

	public double getHandshakes() {
		return handshakes;
	}

	public void setHandshakes(double handshakes) {
		this.handshakes = handshakes;
	}

	public double getHandshakes_failed() {
		return handshakes_failed;
	}

	public void setHandshakes_failed(double handshakes_failed) {
		this.handshakes_failed = handshakes_failed;
	}

	public double getSession_reuses() {
		return session_reuses;
	}

	public void setSession_reuses(double session_reuses) {
		this.session_reuses = session_reuses;
	}
}
