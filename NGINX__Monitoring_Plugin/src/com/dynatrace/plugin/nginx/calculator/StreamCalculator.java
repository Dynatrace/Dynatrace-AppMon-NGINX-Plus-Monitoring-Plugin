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

	private Map<String, Double> serverZoneConnectionsRate = new HashMap<String, Double>();
	private Map<String, Double> serverZoneReceivedRate = new HashMap<String, Double>();
	private Map<String, Double> serverZoneSentRate = new HashMap<String, Double>();

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
					ServerZoneConnectionsRate = cur_.getConnections() / time_frame;
					ServerZoneReceivedRate = cur_.getReceived() / time_frame;
					ServerZoneSentRate = cur_.getSent() / time_frame;
				}
				this.serverZoneConnectionsRate.put(cur_.getServerZoneName(), ServerZoneConnectionsRate);
				this.serverZoneReceivedRate.put(cur_.getServerZoneName(), ServerZoneReceivedRate);
				this.serverZoneSentRate.put(cur_.getServerZoneName(), ServerZoneSentRate);
			}
		}

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
					UpstreamsConnectionsRate = cur_.getConnections() / time_frame;
					UpstreamsSentRate = cur_.getSent() / time_frame;
					UpstreamsReceivedRate = cur_.getReceived() / time_frame;
					UpstreamsFailsRate = cur_.getFails() / time_frame;
				}
				this.upstreamsConnectionsRate.put(streamServerGroupName, cur_.getServer(), UpstreamsConnectionsRate);
				this.upstreamsSentRate.put(streamServerGroupName, cur_.getServer(), UpstreamsSentRate);
				this.upstreamsReceivedRate.put(streamServerGroupName, cur_.getServer(), UpstreamsReceivedRate);
				this.upstreamsFailsRate.put(streamServerGroupName, cur_.getServer(), UpstreamsFailsRate);
			}
		}
	}

	public String generateKey(String serverIp, String streamServerGroupName) {
		return serverIp + "(" + streamServerGroupName + ")";
	}

	public Map<String, Double> getServerZoneReceivedRate() {
		return serverZoneReceivedRate;
	}

	public Map<String, Double> getServerZoneSentRate() {
		return serverZoneSentRate;
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

	public Map<String, Double> getServerZoneConnectionsRate() {
		return serverZoneConnectionsRate;
	}

	public Double getTotalActive() {
		return totalActive;
	}

	public void setTotalActive(Double totalActive) {
		this.totalActive = totalActive;
	}
}
