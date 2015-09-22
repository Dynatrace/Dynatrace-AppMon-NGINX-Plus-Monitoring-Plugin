package com.dynatrace.plugin.nginx.dto;

public class MetaDTO {

	private Double version;
	private String nginxVersion;
	private String address;
	private Double generation;
	private Double load_timestamp;
	private Double timestamp;
	private Double pid;

	public Double getVersion() {
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

	public Double getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Double timestamp) {
		this.timestamp = timestamp;
	}

	public Double getGeneration() {
		return generation;
	}

	public void setGeneration(Double generation) {
		this.generation = generation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLoad_timestamp() {
		return load_timestamp;
	}

	public void setLoad_timestamp(Double load_timestamp) {
		this.load_timestamp = load_timestamp;
	}

	public Double getPid() {
		return pid;
	}

	public void setPid(Double pid) {
		this.pid = pid;
	}
}
