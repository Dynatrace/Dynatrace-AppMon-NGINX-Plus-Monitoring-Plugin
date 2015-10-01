package com.dynatrace.plugin.nginx.calculator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import com.dynatrace.plugin.nginx.dto.NginxStatus;
import com.dynatrace.plugin.nginx.dto.stream.StreamServerDTO;
import com.dynatrace.plugin.nginx.dto.stream.StreamServerZoneDTO;
import com.google.common.collect.HashBasedTable;

public class StreamCalculator extends TimeFrameCalculator implements Calculator {

	public static final String Stream = "Stream";
	public static final String StreamOther = "other";

	private HashMap<String, Double> ConnectionsRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> SentRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> ReceivedRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> FailsRatePerUpstream = new HashMap<String, Double>();

	private HashBasedTable<String, String, Double> serverZoneConnectionsRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> serverZoneReceivedRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> serverZoneSentRate = HashBasedTable.create();

	private HashBasedTable<String, String, Double> upstreamsConnectionsRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> upstreamsSentRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> upstreamsReceivedRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> upstreamsFailsRate = HashBasedTable.create();

	private Double totalActive = 0.0;

	@Override
	public void calculate(NginxStatus prev, NginxStatus cur) {
		double time_frame = calculateTimeFrame(prev, cur);
		this.calculateStream(prev, cur, time_frame);
	}

	public void calculateStream(NginxStatus prev, NginxStatus cur, double time_frame) {
		Double totalServerZoneConnectionsRate = 0.0;
		Double totalServerZoneReceivedRate = 0.0;
		Double totalServerZoneSentRate = 0.0;
		{
			Iterator<StreamServerZoneDTO> curIter = cur.getStream().getServerZones().iterator();
			Iterator<StreamServerZoneDTO> prevIter = prev.getStream().getServerZones().iterator();

			while(curIter.hasNext()) {
				StreamServerZoneDTO cur_ = curIter.next();
				StreamServerZoneDTO prev_;
				Double ServerZoneConnectionsRate = 0.0;
				Double ServerZoneReceivedRate = 0.0;
				Double ServerZoneSentRate = 0.0;
				try {
					prev_ = prevIter.next();
					ServerZoneConnectionsRate = (cur_.getConnections() - prev_.getConnections()) / time_frame;
					ServerZoneReceivedRate = (cur_.getReceived() - prev_.getReceived()) / time_frame;
					ServerZoneSentRate = (cur_.getSent() - prev_.getSent()) / time_frame;

				} catch (NoSuchElementException e) {
					ServerZoneConnectionsRate = Double.NaN;
					ServerZoneReceivedRate = Double.NaN;
					ServerZoneSentRate = Double.NaN;
				}
				this.serverZoneConnectionsRate.put(cur_.getServerZoneName(), Stream, ServerZoneConnectionsRate);
				this.serverZoneReceivedRate.put(cur_.getServerZoneName(), Stream, ServerZoneReceivedRate);
				this.serverZoneSentRate.put(cur_.getServerZoneName(), Stream, ServerZoneSentRate);

				totalServerZoneConnectionsRate += ServerZoneConnectionsRate;
				totalServerZoneReceivedRate += ServerZoneReceivedRate;
				totalServerZoneSentRate += ServerZoneSentRate;
			}
		}

		{
			Iterator<StreamServerZoneDTO> StreamServerZones = cur.getStream().getServerZones().iterator();
			while (StreamServerZones.hasNext()) {
				String streamServerZone = StreamServerZones.next().getServerZoneName();

				this.serverZoneConnectionsRate.put(streamServerZone, StreamOther, totalServerZoneConnectionsRate - this.serverZoneConnectionsRate.get(streamServerZone, Stream));
				this.serverZoneReceivedRate.put(streamServerZone, StreamOther, totalServerZoneReceivedRate - this.serverZoneReceivedRate.get(streamServerZone, Stream));
				this.serverZoneSentRate.put(streamServerZone, StreamOther, totalServerZoneSentRate - this.serverZoneSentRate.get(streamServerZone, Stream));
			}
		}

		Double totalUpstreamsConnectionsRate = 0.0;
		Double totalUpstreamsSentRate = 0.0;
		Double totalUpstreamsReceivedRate = 0.0;
		Double totalUpstreamsFailsRate = 0.0;

		for (String streamServerGroupName : cur.getStream().getUpstreams().get().keySet()) {
			Iterator<StreamServerDTO> curIter = cur.getStream().getUpstreams().get().get(streamServerGroupName).iterator();
			Iterator<StreamServerDTO> prevIter = prev.getStream().getUpstreams().get().get(streamServerGroupName).iterator();

			while(curIter.hasNext()) {
				StreamServerDTO cur_ = curIter.next();
				StreamServerDTO prev_;

				Double UpstreamsConnectionsRate = 0.0;
				Double UpstreamsSentRate = 0.0;
				Double UpstreamsReceivedRate = 0.0;
				Double UpstreamsFailsRate = 0.0;

				try {
					prev_ = prevIter.next();
					this.totalActive += cur_.getActive();
					UpstreamsConnectionsRate =  (cur_.getConnections() - prev_.getConnections()) / time_frame;
					UpstreamsSentRate = (cur_.getSent() - prev_.getSent()) / time_frame;
					UpstreamsReceivedRate = (cur_.getReceived() - prev_.getReceived()) / time_frame;
					UpstreamsFailsRate = (cur_.getFails() - prev_.getFails()) / time_frame;
				} catch (NoSuchElementException e) {
					this.totalActive += cur_.getActive();
					UpstreamsConnectionsRate = Double.NaN;
					UpstreamsSentRate = Double.NaN;
					UpstreamsReceivedRate = Double.NaN;
					UpstreamsFailsRate = Double.NaN;
				}
				this.upstreamsConnectionsRate.put(streamServerGroupName, cur_.getServer(), UpstreamsConnectionsRate);
				this.upstreamsSentRate.put(streamServerGroupName, cur_.getServer(), UpstreamsSentRate);
				this.upstreamsReceivedRate.put(streamServerGroupName, cur_.getServer(), UpstreamsReceivedRate);
				this.upstreamsFailsRate.put(streamServerGroupName, cur_.getServer(), UpstreamsFailsRate);

				totalUpstreamsConnectionsRate += UpstreamsConnectionsRate;
				totalUpstreamsSentRate += UpstreamsSentRate;
				totalUpstreamsReceivedRate += UpstreamsReceivedRate;
				totalUpstreamsFailsRate += UpstreamsFailsRate;
			}
		}

		for (String streamServerGroupName : cur.getStream().getUpstreams().get().keySet()) {
			Double UpstreamsConnectionsRate = 0.0;
			Double UpstreamsSentRate = 0.0;
			Double UpstreamsReceivedRate = 0.0;
			Double UpstreamsFailsRate = 0.0;

			Map<String, Double> upstreamsConnectionsRateMap = this.upstreamsConnectionsRate.row(streamServerGroupName);
			for (String server : upstreamsConnectionsRateMap.keySet()) {
				UpstreamsConnectionsRate += upstreamsConnectionsRateMap.get(server);
			}

			Map<String, Double> upstreamsSentRateMap = this.upstreamsSentRate.row(streamServerGroupName);
			for (String server : upstreamsSentRateMap.keySet()) {
				UpstreamsSentRate += upstreamsSentRateMap.get(server);
			}

			Map<String, Double> upstreamsReceivedRateMap = this.upstreamsReceivedRate.row(streamServerGroupName);
			for (String server : upstreamsReceivedRateMap.keySet()) {
				UpstreamsReceivedRate += upstreamsReceivedRateMap.get(server);
			}

			Map<String, Double> upstreamsFailsRateMap = this.upstreamsFailsRate.row(streamServerGroupName);
			for (String server : upstreamsFailsRateMap.keySet()) {
				UpstreamsFailsRate += upstreamsFailsRateMap.get(server);
			}

			this.ConnectionsRatePerUpstream.put(streamServerGroupName, UpstreamsConnectionsRate);
			this.SentRatePerUpstream.put(streamServerGroupName, UpstreamsSentRate);
			this.ReceivedRatePerUpstream.put(streamServerGroupName, UpstreamsReceivedRate);
			this.FailsRatePerUpstream.put(streamServerGroupName, UpstreamsFailsRate);

			this.upstreamsConnectionsRate.put(streamServerGroupName, StreamOther, totalUpstreamsConnectionsRate - UpstreamsConnectionsRate);
			this.upstreamsSentRate.put(streamServerGroupName, StreamOther, totalUpstreamsSentRate - UpstreamsSentRate);
			this.upstreamsReceivedRate.put(streamServerGroupName, StreamOther, totalUpstreamsReceivedRate - UpstreamsReceivedRate);
			this.upstreamsFailsRate.put(streamServerGroupName, StreamOther, totalUpstreamsFailsRate - UpstreamsFailsRate);
		}
	}

	public String generateKey(String serverIp, String streamServerGroupName) {
		return serverIp + "(" + streamServerGroupName + ")";
	}

	public HashBasedTable<String, String, Double> getServerZoneReceivedRate() {
		return serverZoneReceivedRate;
	}

	public HashBasedTable<String, String, Double> getServerZoneSentRate() {
		return serverZoneSentRate;
	}

	public HashBasedTable<String, String, Double> getServerZoneConnectionsRate() {
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
}
