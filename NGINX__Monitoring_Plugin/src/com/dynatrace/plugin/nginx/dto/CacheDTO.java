package com.dynatrace.plugin.nginx.dto;

public class CacheDTO {

	private String cacheName;
	private Double size;
	private Double maxSize;
	private Boolean cold;
	private Double hitResponses;
	private Double hitBytes;
	private Double staleResponses;
	private Double staleBytes;
	private Double updatingResponses;
	private Double updatingBytes;
	private Double revalidatedResponses;
	private Double revalidatedBytes;
	private Double missResponses;
	private Double missBytes;
	private Double missResponsesWritten;
	private Double missBytesWritten;
	private Double expiredResponses;
	private Double expiredBytes;
	private Double expiredResponsesWritten;
	private Double expiredBytesWritten;
	private Double bypassResponses;
	private Double bypassBytes;
	private Double bypassResponsesWritten;
	private Double bypassBytesWritten;

	public CacheDTO(String cache_name) {
		this.cacheName = cache_name;
	}

	public Double getSize() {
		return size;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public Double getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Double max_size) {
		this.maxSize = max_size;
	}

	public Boolean getCold() {
		return cold;
	}

	public void setCold(Boolean cold) {
		this.cold = cold;
	}

	public String getCacheName() {
		return cacheName;
	}

	public Double getHitResponses() {
		return hitResponses;
	}

	public void setHitResponses(Double hitResponses) {
		this.hitResponses = hitResponses;
	}

	public Double getHitBytes() {
		return hitBytes;
	}

	public void setHitBytes(Double hitBytes) {
		this.hitBytes = hitBytes;
	}

	public Double getStaleResponses() {
		return staleResponses;
	}

	public void setStaleResponses(Double staleResponses) {
		this.staleResponses = staleResponses;
	}

	public Double getStaleBytes() {
		return staleBytes;
	}

	public void setStaleBytes(Double staleBytes) {
		this.staleBytes = staleBytes;
	}

	public Double getUpdatingResponses() {
		return updatingResponses;
	}

	public void setUpdatingResponses(Double updatingResponses) {
		this.updatingResponses = updatingResponses;
	}

	public Double getUpdatingBytes() {
		return updatingBytes;
	}

	public void setUpdatingBytes(Double updatingBytes) {
		this.updatingBytes = updatingBytes;
	}

	public Double getRevalidatedResponses() {
		return revalidatedResponses;
	}

	public void setRevalidatedResponses(Double revalidatedResponses) {
		this.revalidatedResponses = revalidatedResponses;
	}

	public Double getRevalidatedBytes() {
		return revalidatedBytes;
	}

	public void setRevalidatedBytes(Double revalidatedBytes) {
		this.revalidatedBytes = revalidatedBytes;
	}

	public Double getMissResponses() {
		return missResponses;
	}

	public void setMissResponses(Double missResponses) {
		this.missResponses = missResponses;
	}

	public Double getMissBytes() {
		return missBytes;
	}

	public void setMissBytes(Double missBytes) {
		this.missBytes = missBytes;
	}

	public Double getMissResponsesWritten() {
		return missResponsesWritten;
	}

	public void setMissResponsesWritten(Double missResponsesWritten) {
		this.missResponsesWritten = missResponsesWritten;
	}

	public Double getExpiredResponses() {
		return expiredResponses;
	}

	public void setExpiredResponses(Double expiredResponses) {
		this.expiredResponses = expiredResponses;
	}

	public Double getMissBytesWritten() {
		return missBytesWritten;
	}

	public void setMissBytesWritten(Double missBytesWritten) {
		this.missBytesWritten = missBytesWritten;
	}

	public Double getExpiredBytes() {
		return expiredBytes;
	}

	public void setExpiredBytes(Double expiredBytes) {
		this.expiredBytes = expiredBytes;
	}

	public Double getExpiredResponsesWritten() {
		return expiredResponsesWritten;
	}

	public void setExpiredResponsesWritten(Double expiredResponsesWritten) {
		this.expiredResponsesWritten = expiredResponsesWritten;
	}

	public Double getExpiredBytesWritten() {
		return expiredBytesWritten;
	}

	public void setExpiredBytesWritten(Double expiredBytesWritten) {
		this.expiredBytesWritten = expiredBytesWritten;
	}

	public Double getBypassResponses() {
		return bypassResponses;
	}

	public void setBypassResponses(Double bypassResponses) {
		this.bypassResponses = bypassResponses;
	}

	public Double getBypassBytes() {
		return bypassBytes;
	}

	public void setBypassBytes(Double bypassBytes) {
		this.bypassBytes = bypassBytes;
	}

	public Double getBypassResponsesWritten() {
		return bypassResponsesWritten;
	}

	public void setBypassResponsesWritten(Double bypassResponsesWritten) {
		this.bypassResponsesWritten = bypassResponsesWritten;
	}

	public Double getBypassBytesWritten() {
		return bypassBytesWritten;
	}

	public void setBypassBytesWritten(Double bypassBytesWritten) {
		this.bypassBytesWritten = bypassBytesWritten;
	}
}
