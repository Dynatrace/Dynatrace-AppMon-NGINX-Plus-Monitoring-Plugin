package com.dynatrace.plugin.nginx.calculator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.dynatrace.plugin.nginx.dto.NginxStatus;
import com.dynatrace.plugin.nginx.dto.stream.StreamServerDTO;
import com.dynatrace.plugin.nginx.dto.stream.StreamServerZoneDTO;
import com.google.common.collect.HashBasedTable;

public class StreamCalculator extends TimeFrameCalculator implements Calculator {

	private HashMap<String, Double> ConnectionsRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> ConnectionsPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> SentRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> SentPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> ReceivedRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> ReceivedPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> FailsRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> FailsPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> UnavailPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> UnavailRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> HealthChecksTotalPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> HealthChecksTotalRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> HealthChecksFailedPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> HealthChecksFailedRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> HealthChecksUnhealthyPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> HealthChecksUnhealthyRatePerUpstream = new HashMap<String, Double>();

	private HashMap<String, Double> serverZoneConnectionsRate = new HashMap<String, Double>();
	private HashMap<String, Double> serverZoneReceivedRate = new HashMap<String, Double>();
	private HashMap<String, Double> serverZoneSentRate = new HashMap<String, Double>();

	private HashBasedTable<String, String, Double> upstreamsConnectionsRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> upstreamsSentRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> upstreamsReceivedRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> upstreamsFailsRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> upstreamsUnavailRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> upstreamsHealthChecksRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> upstreamsHealthChecksFailedRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> upstreamsHealthChecksUnhealthyRate = HashBasedTable.create();

	private Double totalActive = 0.0;

	@Override
	public void calculate(NginxStatus prev, NginxStatus cur) {
		double time_frame = calculateTimeFrame(prev, cur);
		this.calculateStream(prev, cur, time_frame);
	}

	public void calculateStream(NginxStatus prev, NginxStatus cur, double time_frame) {
		{
			Iterator<StreamServerZoneDTO> curIter = cur.getStream().getServerZones().iterator();
			Iterator<StreamServerZoneDTO> prevIter = prev.getStream().getServerZones().iterator();

			while(curIter.hasNext()) {
				StreamServerZoneDTO cur_ = curIter.next();
				StreamServerZoneDTO prev_;
				Double connectionsRate = 0.0;
				Double receivedRate = 0.0;
				Double sentRate = 0.0;
				try {
					prev_ = prevIter.next();
					connectionsRate = (cur_.getConnections() - prev_.getConnections()) / time_frame;
					receivedRate = (cur_.getReceived() - prev_.getReceived()) / time_frame;
					sentRate = (cur_.getSent() - prev_.getSent()) / time_frame;

				} catch (NoSuchElementException e) {
					connectionsRate = Double.NaN;
					receivedRate = Double.NaN;
					sentRate = Double.NaN;
				}
				this.serverZoneConnectionsRate.put(cur_.getServerZoneName(), connectionsRate);
				this.serverZoneReceivedRate.put(cur_.getServerZoneName(), receivedRate);
				this.serverZoneSentRate.put(cur_.getServerZoneName(), sentRate);
			}
		}

		for (String serverGroupName : cur.getStream().getUpstreams().get().keySet()) {

			Double connectionsPerUpstream = 0.0;
			Double connectionsRatePerUpstream = 0.0;
			Double sentPerUpstream = 0.0;
			Double sentRatePerUpstream = 0.0;
			Double receivedPerUpstream = 0.0;
			Double receivedRatePerUpstream = 0.0;
			Double failsPerUpstream = 0.0;
			Double failsRatePerUpstream = 0.0;
			Double unavailPerUpstream = 0.0;
			Double unavailRatePerUpstream = 0.0;
			Double healthChecksTotalPerUpstream = 0.0;
			Double healthChecksTotalRatePerUpstream = 0.0;
			Double healthChecksFailedPerUpstream = 0.0;
			Double healthChecksFailedRatePerUpstream = 0.0;
			Double healthChecksUnhealthyPerUpstream = 0.0;
			Double healthChecksUnhealthyRatePerUpstream = 0.0;

			Iterator<StreamServerDTO> curIter = cur.getStream().getUpstreams().get().get(serverGroupName).iterator();
			Iterator<StreamServerDTO> prevIter = prev.getStream().getUpstreams().get().get(serverGroupName).iterator();

			while(curIter.hasNext()) {
				StreamServerDTO cur_ = curIter.next();
				StreamServerDTO prev_;

				Double ConnectionsRate = 0.0;
				Double SentRate = 0.0;
				Double ReceivedRate = 0.0;
				Double FailsRate = 0.0;
				Double UnavailRate = 0.0;
				Double HealthChecksRate = 0.0;
				Double HealthChecksFailedRate = 0.0;
				Double HealthChecksUnhealthyRate = 0.0;

				try {
					prev_ = prevIter.next();
					this.totalActive += cur_.getActive();
					ConnectionsRate = (cur_.getConnections() - prev_.getConnections()) / time_frame;
					SentRate = (cur_.getSent() - prev_.getSent()) / time_frame;
					ReceivedRate = (cur_.getReceived() - prev_.getReceived()) / time_frame;
					FailsRate = (cur_.getFails() - prev_.getFails()) / time_frame;
					UnavailRate = (cur_.getUnavail() - prev_.getUnavail()) / time_frame;
					HealthChecksRate = (cur_.getHealthChecksTotal() - prev_.getHealthChecksTotal()) / time_frame;
					HealthChecksFailedRate = (cur_.getHealthChecksFails() - prev_.getHealthChecksFails()) / time_frame;
					HealthChecksUnhealthyRate = (cur_.getHealthChecksUnhealthy() - prev_.getHealthChecksUnhealthy()) / time_frame;
				} catch (NoSuchElementException e) {
					this.totalActive += cur_.getActive();
					ConnectionsRate = Double.NaN;
					SentRate = Double.NaN;
					ReceivedRate = Double.NaN;
					FailsRate = Double.NaN;
					UnavailRate = Double.NaN;
					HealthChecksRate = Double.NaN;
					HealthChecksFailedRate = Double.NaN;
					HealthChecksUnhealthyRate = Double.NaN;
				}

				this.upstreamsConnectionsRate.put(serverGroupName, cur_.getServer(), ConnectionsRate);
				this.upstreamsSentRate.put(serverGroupName, cur_.getServer(), SentRate);
				this.upstreamsReceivedRate.put(serverGroupName, cur_.getServer(), ReceivedRate);
				this.upstreamsFailsRate.put(serverGroupName, cur_.getServer(), FailsRate);
				this.upstreamsUnavailRate.put(serverGroupName, cur_.getServer(), UnavailRate);
				this.upstreamsHealthChecksRate.put(serverGroupName, cur_.getServer(), HealthChecksRate);
				this.upstreamsHealthChecksFailedRate.put(serverGroupName, cur_.getServer(), HealthChecksFailedRate);
				this.upstreamsHealthChecksUnhealthyRate.put(serverGroupName, cur_.getServer(), HealthChecksUnhealthyRate);

				connectionsPerUpstream += cur_.getConnections();
				connectionsRatePerUpstream += ConnectionsRate;
				sentPerUpstream += cur_.getSent();
				sentRatePerUpstream += SentRate;
				receivedPerUpstream += cur_.getReceived();
				receivedRatePerUpstream += ReceivedRate;
				failsPerUpstream += cur_.getFails();
				failsRatePerUpstream += FailsRate;
				unavailPerUpstream += cur_.getUnavail();
				unavailRatePerUpstream += UnavailRate;
				healthChecksTotalPerUpstream += cur_.getHealthChecksTotal();
				healthChecksTotalRatePerUpstream += HealthChecksRate;
				healthChecksFailedPerUpstream += cur_.getHealthChecksFails();
				healthChecksFailedRatePerUpstream += HealthChecksFailedRate;
				healthChecksUnhealthyPerUpstream += cur_.getHealthChecksUnhealthy();
				healthChecksUnhealthyRatePerUpstream += HealthChecksUnhealthyRate;
			}

			this.ConnectionsPerUpstream.put(serverGroupName, connectionsPerUpstream);
			this.ConnectionsRatePerUpstream.put(serverGroupName, connectionsRatePerUpstream);
			this.SentPerUpstream.put(serverGroupName, sentPerUpstream);
			this.SentRatePerUpstream.put(serverGroupName, sentRatePerUpstream);
			this.ReceivedPerUpstream.put(serverGroupName, receivedPerUpstream);
			this.ReceivedRatePerUpstream.put(serverGroupName, receivedRatePerUpstream);
			this.FailsPerUpstream.put(serverGroupName, failsPerUpstream);
			this.FailsRatePerUpstream.put(serverGroupName, failsRatePerUpstream);
			this.UnavailPerUpstream.put(serverGroupName, unavailPerUpstream);
			this.UnavailRatePerUpstream.put(serverGroupName, unavailRatePerUpstream);
			this.HealthChecksTotalPerUpstream.put(serverGroupName, healthChecksTotalPerUpstream);
			this.HealthChecksTotalRatePerUpstream.put(serverGroupName, healthChecksTotalRatePerUpstream);
			this.HealthChecksFailedPerUpstream.put(serverGroupName, healthChecksFailedPerUpstream);
			this.HealthChecksFailedRatePerUpstream.put(serverGroupName, healthChecksFailedRatePerUpstream);
			this.HealthChecksUnhealthyPerUpstream.put(serverGroupName, healthChecksUnhealthyPerUpstream);
			this.HealthChecksUnhealthyRatePerUpstream.put(serverGroupName, healthChecksUnhealthyRatePerUpstream);
		}
	}

	public HashMap<String, Double> getServerZoneReceivedRate() {
		return serverZoneReceivedRate;
	}

	public HashMap<String, Double> getServerZoneSentRate() {
		return serverZoneSentRate;
	}

	public HashMap<String, Double> getServerZoneConnectionsRate() {
		return serverZoneConnectionsRate;
	}

	public HashBasedTable<String, String, Double> getUpstreamsFailsRate() {
		return upstreamsFailsRate;
	}

	public HashBasedTable<String, String, Double> getUpstreamsReceivedRate() {
		return upstreamsReceivedRate;
	}

	public HashBasedTable<String, String, Double> getUpstreamsSentRate() {
		return upstreamsSentRate;
	}

	public HashBasedTable<String, String, Double> getUpstreamsConnectionsRate() {
		return upstreamsConnectionsRate;
	}

	public Double getTotalActive() {
		return totalActive;
	}

	public HashMap<String, Double> getConnectionsRatePerUpstream() {
		return ConnectionsRatePerUpstream;
	}

	public HashMap<String, Double> getSentRatePerUpstream() {
		return SentRatePerUpstream;
	}

	public HashMap<String, Double> getReceivedRatePerUpstream() {
		return ReceivedRatePerUpstream;
	}

	public HashMap<String, Double> getFailsRatePerUpstream() {
		return FailsRatePerUpstream;
	}

	public HashMap<String, Double> getConnectionsPerUpstream() {
		return ConnectionsPerUpstream;
	}

	public HashMap<String, Double> getSentPerUpstream() {
		return SentPerUpstream;
	}

	public HashMap<String, Double> getReceivedPerUpstream() {
		return ReceivedPerUpstream;
	}

	public HashMap<String, Double> getFailsPerUpstream() {
		return FailsPerUpstream;
	}

	public HashMap<String, Double> getUnavailPerUpstream() {
		return UnavailPerUpstream;
	}

	public HashMap<String, Double> getHealthChecksTotalPerUpstream() {
		return HealthChecksTotalPerUpstream;
	}

	public HashMap<String, Double> getHealthChecksFailedPerUpstream() {
		return HealthChecksFailedPerUpstream;
	}

	public HashMap<String, Double> getHealthChecksUnhealthyPerUpstream() {
		return HealthChecksUnhealthyPerUpstream;
	}

	public HashBasedTable<String, String, Double> getUpstreamsUnavailRate() {
		return upstreamsUnavailRate;
	}

	public HashBasedTable<String, String, Double> getUpstreamsHealthChecksRate() {
		return upstreamsHealthChecksRate;
	}

	public HashBasedTable<String, String, Double> getUpstreamsHealthChecksFailedRate() {
		return upstreamsHealthChecksFailedRate;
	}

	public HashBasedTable<String, String, Double> getUpstreamsHealthChecksUnhealthyRate() {
		return upstreamsHealthChecksUnhealthyRate;
	}

	public HashMap<String, Double> getUnavailRatePerUpstream() {
		return UnavailRatePerUpstream;
	}

	public HashMap<String, Double> getHealthChecksTotalRatePerUpstream() {
		return HealthChecksTotalRatePerUpstream;
	}

	public HashMap<String, Double> getHealthChecksFailedRatePerUpstream() {
		return HealthChecksFailedRatePerUpstream;
	}

	public HashMap<String, Double> getHealthChecksUnhealthyRatePerUpstream() {
		return HealthChecksUnhealthyRatePerUpstream;
	}
}
