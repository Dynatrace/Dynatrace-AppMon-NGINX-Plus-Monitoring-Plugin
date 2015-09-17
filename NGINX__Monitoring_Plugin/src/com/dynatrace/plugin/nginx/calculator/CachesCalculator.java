package com.dynatrace.plugin.nginx.calculator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import com.dynatrace.plugin.nginx.dto.CacheDTO;
import com.dynatrace.plugin.nginx.dto.NginxStatus;

public class CachesCalculator extends TimeFrameCalculator implements Calculator {
	private Map<String, Double> hitResponsesRate = new HashMap<String, Double>();
	private Double totalHitResponsesRate = 0.0;
	private Map<String, Double> hitBytesRate = new HashMap<String, Double>();
	private Double totalHitBytesRate = 0.0;

	private Map<String, Double> staleResponsesRate = new HashMap<String, Double>();
	private Double totalStaleResponsesRate = 0.0;
	private Map<String, Double> staleBytesRate = new HashMap<String, Double>();
	private Double totalStaleBytesRate = 0.0;

	private Map<String, Double> updatingResponsesRate = new HashMap<String, Double>();
	private Double totalUpdatingResponsesRate = 0.0;
	private Map<String, Double> updatingBytesRate = new HashMap<String, Double>();
	private Double totalUpdatingBytesRate = 0.0;

	private Map<String, Double> revalidatedResponsesRate = new HashMap<String, Double>();
	private Double totalRevalidatedResponsesRate = 0.0;
	private Map<String, Double> revalidatedBytesRate = new HashMap<String, Double>();
	private Double totalRevalidatedBytesRate = 0.0;

	private Map<String, Double> missResponsesRate = new HashMap<String, Double>();
	private Double totalMissResponsesRate = 0.0;
	private Map<String, Double> missBytesRate = new HashMap<String, Double>();
	private Double totalMissBytesRate = 0.0;
	private Map<String, Double> missResponsesWrittenRate = new HashMap<String, Double>();
	private Double totalMissResponsesWrittenRate = 0.0;
	private Map<String, Double> missBytesWrittenRate = new HashMap<String, Double>();
	private Double totalMissBytesWrittenRate = 0.0;

	private Map<String, Double> expiredResponsesRate = new HashMap<String, Double>();
	private Double totalExpiredResponsesRate = 0.0;
	private Map<String, Double> expiredBytesRate = new HashMap<String, Double>();
	private Double totalExpiredBytesRate = 0.0;
	private Map<String, Double> expiredResponsesWrittenRate = new HashMap<String, Double>();
	private Double totalExpiredResponsesWrittenRate = 0.0;
	private Map<String, Double> expiredBytesWrittenRate = new HashMap<String, Double>();
	private Double totalExpiredBytesWrittenRate = 0.0;

	private Map<String, Double> bypassResponsesRate = new HashMap<String, Double>();
	private Double totalBypassResponsesRate = 0.0;
	private Map<String, Double> bypassBytesRate = new HashMap<String, Double>();
	private Double totalBypassBytesRate = 0.0;
	private Map<String, Double> bypassResponsesWrittenRate = new HashMap<String, Double>();
	private Double totalBypassResponsesWrittenRate = 0.0;
	private Map<String, Double> bypassBytesWrittenRate = new HashMap<String, Double>();
	private Double totalBypassBytesWrittenRate = 0.0;

	@Override
	public void calculate(NginxStatus prev, NginxStatus cur) {
		double time_frame = calculateTimeFrame(prev, cur);
		this.calculateCaches(prev, cur, time_frame);

	}

	public void calculateCaches(NginxStatus prev, NginxStatus cur, double time_frame) {
		Iterator<CacheDTO> curIter = cur.getCaches().iterator();
		Iterator<CacheDTO> prevIter = prev.getCaches().iterator();

		while(curIter.hasNext()) {
			CacheDTO cur_ = curIter.next();
			CacheDTO prev_;
			String cacheName = cur_.getCacheName();

			Double HitResponsesRate = 0.0;
			Double HitBytesRate = 0.0;
			Double StaleResponsesRate = 0.0;
			Double StaleBytesRate = 0.0;
			Double UpdatingResponsesRate = 0.0;
			Double UpdatingBytesRate = 0.0;
			Double RevalidatedResponsesRate = 0.0;
			Double RevalidatedBytesRate = 0.0;
			Double MissResponsesRate = 0.0;
			Double MissBytesRate = 0.0;
			Double MissResponsesWrittenRate = 0.0;
			Double MissBytesWrittenRate = 0.0;
			Double ExpiredResponsesRate = 0.0;
			Double ExpiredBytesRate = 0.0;
			Double ExpiredResponsesWrittenRate = 0.0;
			Double ExpiredBytesWrittenRate = 0.0;
			Double BypassResponsesRate = 0.0;
			Double BypassBytesRate = 0.0;
			Double BypassResponsesWrittenRate = 0.0;
			Double BypassBytesWrittenRate = 0.0;
			try {
				prev_ = prevIter.next();

				HitResponsesRate = (cur_.getHitResponses() - prev_.getHitResponses()) / time_frame;
				HitBytesRate = (cur_.getHitBytes() - prev_.getHitBytes()) / time_frame;
				StaleResponsesRate = (cur_.getStaleResponses() - prev_.getStaleResponses()) / time_frame;
				StaleBytesRate = (cur_.getStaleBytes() - prev_.getStaleBytes()) / time_frame;
				UpdatingResponsesRate = (cur_.getUpdatingResponses() - prev_.getUpdatingResponses()) / time_frame;
				UpdatingBytesRate = (cur_.getUpdatingBytes() - prev_.getUpdatingBytes()) / time_frame;
				RevalidatedResponsesRate = (cur_.getRevalidatedResponses() - prev_.getRevalidatedResponses()) / time_frame;
				RevalidatedBytesRate = (cur_.getRevalidatedBytes() - prev_.getRevalidatedBytes()) / time_frame;
				MissResponsesRate = (cur_.getMissResponses() - prev_.getMissResponses()) / time_frame;
				MissBytesRate = (cur_.getMissBytes() - prev_.getMissBytes()) / time_frame;
				MissResponsesWrittenRate = (cur_.getMissResponsesWritten() - prev_.getMissResponsesWritten()) / time_frame;
				MissBytesWrittenRate = (cur_.getMissBytesWritten() - prev_.getMissBytesWritten()) / time_frame;
				ExpiredResponsesRate = (cur_.getExpiredResponses() - prev_.getExpiredResponses()) / time_frame;
				ExpiredBytesRate = (cur_.getExpiredBytes() - prev_.getExpiredBytes()) / time_frame;
				ExpiredResponsesWrittenRate = (cur_.getExpiredResponsesWritten() - prev_.getExpiredResponsesWritten()) / time_frame;
				ExpiredBytesWrittenRate = (cur_.getExpiredBytesWritten() - prev_.getExpiredBytesWritten()) / time_frame;
				BypassResponsesRate = (cur_.getBypassResponses() - prev_.getBypassResponses()) / time_frame;
				BypassBytesRate = (cur_.getBypassBytes() - prev_.getBypassBytes()) / time_frame;
				BypassResponsesWrittenRate = (cur_.getBypassResponsesWritten() - prev_.getBypassResponsesWritten()) / time_frame;
				BypassBytesWrittenRate = (cur_.getBypassBytesWritten() - prev_.getBypassBytesWritten()) / time_frame;
			} catch (NoSuchElementException e) {
				HitResponsesRate = Double.NaN;
				HitBytesRate = Double.NaN;
				StaleResponsesRate = Double.NaN;
				StaleBytesRate = Double.NaN;
				UpdatingResponsesRate = Double.NaN;
				UpdatingBytesRate = Double.NaN;
				RevalidatedResponsesRate = Double.NaN;
				RevalidatedBytesRate = Double.NaN;
				MissResponsesRate = Double.NaN;
				MissBytesRate = Double.NaN;
				MissResponsesWrittenRate = Double.NaN;
				MissBytesWrittenRate = Double.NaN;
				ExpiredResponsesRate = Double.NaN;
				ExpiredBytesRate = Double.NaN;
				ExpiredResponsesWrittenRate = Double.NaN;
				ExpiredBytesWrittenRate = Double.NaN;
				BypassResponsesRate = Double.NaN;
				BypassBytesRate = Double.NaN;
				BypassResponsesWrittenRate = Double.NaN;
				BypassBytesWrittenRate = Double.NaN;
			}
			this.hitResponsesRate.put(cacheName, HitResponsesRate);
			this.totalHitResponsesRate += HitResponsesRate;

			this.hitBytesRate.put(cacheName, HitBytesRate);
			this.totalHitBytesRate += HitBytesRate;

			this.staleResponsesRate.put(cacheName, StaleResponsesRate);
			this.totalStaleResponsesRate += StaleResponsesRate;

			this.staleBytesRate.put(cacheName, StaleBytesRate);
			this.totalStaleBytesRate += StaleBytesRate;

			this.updatingResponsesRate.put(cacheName, UpdatingResponsesRate);
			this.totalUpdatingResponsesRate += UpdatingResponsesRate;

			this.updatingBytesRate.put(cacheName, UpdatingBytesRate);
			this.totalUpdatingBytesRate += UpdatingBytesRate;

			this.revalidatedResponsesRate.put(cacheName, RevalidatedResponsesRate);
			this.totalRevalidatedResponsesRate += RevalidatedResponsesRate;

			this.revalidatedBytesRate.put(cacheName, RevalidatedBytesRate);
			this.totalRevalidatedBytesRate += RevalidatedBytesRate;

			this.missResponsesRate.put(cacheName, MissResponsesRate);
			this.totalMissResponsesRate += MissResponsesRate;

			this.missBytesRate.put(cacheName, MissBytesRate);
			this.totalMissBytesRate += MissBytesRate;

			this.missResponsesWrittenRate.put(cacheName, MissResponsesWrittenRate);
			this.totalMissResponsesWrittenRate += MissResponsesWrittenRate;

			this.missBytesWrittenRate.put(cacheName, MissBytesWrittenRate);
			this.totalMissBytesWrittenRate += MissBytesWrittenRate;

			this.expiredResponsesRate.put(cacheName, ExpiredResponsesRate);
			this.totalExpiredResponsesRate += ExpiredResponsesRate;

			this.expiredBytesRate.put(cacheName, ExpiredBytesRate);
			this.totalExpiredBytesRate += ExpiredBytesRate;

			this.expiredResponsesWrittenRate.put(cacheName, ExpiredResponsesWrittenRate);
			this.totalExpiredResponsesWrittenRate += ExpiredResponsesWrittenRate;

			this.expiredBytesWrittenRate.put(cacheName, ExpiredBytesWrittenRate);
			this.totalExpiredBytesWrittenRate += ExpiredBytesWrittenRate;

			this.bypassResponsesRate.put(cacheName, BypassResponsesRate);
			this.totalBypassResponsesRate += BypassResponsesRate;

			this.bypassBytesRate.put(cacheName, BypassBytesRate);
			this.totalBypassBytesRate += BypassBytesRate;

			this.bypassResponsesWrittenRate.put(cacheName, BypassResponsesWrittenRate);
			this.totalBypassResponsesWrittenRate += BypassResponsesWrittenRate;

			this.bypassBytesWrittenRate.put(cacheName, BypassBytesWrittenRate);
			this.totalBypassBytesWrittenRate += BypassBytesWrittenRate;
		}
	}

	public Map<String, Double> getHitResponsesRate() {
		return hitResponsesRate;
	}

	public Map<String, Double> getHitBytesRate() {
		return hitBytesRate;
	}

	public Map<String, Double> getStaleResponsesRate() {
		return staleResponsesRate;
	}

	public Map<String, Double> getStaleBytesRate() {
		return staleBytesRate;
	}

	public Map<String, Double> getUpdatingResponsesRate() {
		return updatingResponsesRate;
	}

	public Map<String, Double> getUpdatingBytesRate() {
		return updatingBytesRate;
	}

	public Map<String, Double> getRevalidatedResponsesRate() {
		return revalidatedResponsesRate;
	}

	public Map<String, Double> getRevalidatedBytesRate() {
		return revalidatedBytesRate;
	}

	public Map<String, Double> getMissResponsesRate() {
		return missResponsesRate;
	}

	public Map<String, Double> getMissBytesRate() {
		return missBytesRate;
	}

	public Map<String, Double> getMissResponsesWrittenRate() {
		return missResponsesWrittenRate;
	}

	public Map<String, Double> getMissBytesWrittenRate() {
		return missBytesWrittenRate;
	}

	public Map<String, Double> getExpiredResponsesRate() {
		return expiredResponsesRate;
	}

	public Map<String, Double> getExpiredBytesRate() {
		return expiredBytesRate;
	}

	public Map<String, Double> getExpiredResponsesWrittenRate() {
		return expiredResponsesWrittenRate;
	}

	public Map<String, Double> getExpiredBytesWrittenRate() {
		return expiredBytesWrittenRate;
	}

	public Map<String, Double> getBypassResponsesRate() {
		return bypassResponsesRate;
	}

	public Map<String, Double> getBypassBytesRate() {
		return bypassBytesRate;
	}

	public Map<String, Double> getBypassResponsesWrittenRate() {
		return bypassResponsesWrittenRate;
	}

	public Map<String, Double> getBypassBytesWrittenRate() {
		return bypassBytesWrittenRate;
	}

	public Double getTotalHitResponsesRate() {
		return totalHitResponsesRate;
	}

	public Double getTotalHitBytesRate() {
		return totalHitBytesRate;
	}

	public Double getTotalStaleResponsesRate() {
		return totalStaleResponsesRate;
	}

	public Double getTotalStaleBytesRate() {
		return totalStaleBytesRate;
	}

	public Double getTotalUpdatingResponsesRate() {
		return totalUpdatingResponsesRate;
	}

	public Double getTotalUpdatingBytesRate() {
		return totalUpdatingBytesRate;
	}

	public Double getTotalRevalidatedResponsesRate() {
		return totalRevalidatedResponsesRate;
	}

	public Double getTotalRevalidatedBytesRate() {
		return totalRevalidatedBytesRate;
	}

	public void setTotalRevalidatedBytesRate(Double totalRevalidatedBytesRate) {
		this.totalRevalidatedBytesRate = totalRevalidatedBytesRate;
	}

	public Double getTotalMissResponsesRate() {
		return totalMissResponsesRate;
	}

	public Double getTotalMissBytesRate() {
		return totalMissBytesRate;
	}

	public Double getTotalMissBytesWrittenRate() {
		return totalMissBytesWrittenRate;
	}

	public Double getTotalExpiredResponsesRate() {
		return totalExpiredResponsesRate;
	}

	public Double getTotalMissResponsesWrittenRate() {
		return totalMissResponsesWrittenRate;
	}

	public Double getTotalExpiredBytesRate() {
		return totalExpiredBytesRate;
	}

	public Double getTotalExpiredResponsesWrittenRate() {
		return totalExpiredResponsesWrittenRate;
	}

	public Double getTotalExpiredBytesWrittenRate() {
		return totalExpiredBytesWrittenRate;
	}

	public Double getTotalBypassResponsesRate() {
		return totalBypassResponsesRate;
	}

	public Double getTotalBypassBytesRate() {
		return totalBypassBytesRate;
	}

	public Double getTotalBypassResponsesWrittenRate() {
		return totalBypassResponsesWrittenRate;
	}

	public Double getTotalBypassBytesWrittenRate() {
		return totalBypassBytesWrittenRate;
	}
}
