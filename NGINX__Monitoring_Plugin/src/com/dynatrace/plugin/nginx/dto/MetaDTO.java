package com.dynatrace.plugin.nginx.dto;

public class MetaDTO {
	private double version;
	private String nginxVersion;
	private long timestamp;

	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}

	public String getNginx_version() {
		return nginxVersion;
	}

	public void setNginx_version(String nginxVersion) {
		this.nginxVersion = nginxVersion;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
