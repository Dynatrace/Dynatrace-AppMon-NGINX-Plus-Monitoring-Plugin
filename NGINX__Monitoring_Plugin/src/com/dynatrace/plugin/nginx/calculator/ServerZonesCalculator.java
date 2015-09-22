package com.dynatrace.plugin.nginx.calculator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import com.dynatrace.plugin.nginx.dto.NginxStatus;
import com.dynatrace.plugin.nginx.dto.serverzone.ServerZoneDTO;

public class ServerZonesCalculator extends TimeFrameCalculator implements Calculator {
	private Map<String, Double> requestsRate = new HashMap<String, Double>();
	private Map<String, Double> responsesRate = new HashMap<String, Double>();
	private Map<String, Double> discarded = new HashMap<String, Double>();
	private Map<String, Double> trafficSentRate = new HashMap<String, Double>();
	private Map<String, Double> trafficRecvRate = new HashMap<String, Double>();
	private Map<String, Double> responses1xxRate = new HashMap<String, Double>();
	private Map<String, Double> responses2xxRate = new HashMap<String, Double>();
	private Map<String, Double> responses3xxRate = new HashMap<String, Double>();
	private Map<String, Double> responses4xxRate = new HashMap<String, Double>();
	private Map<String, Double> responses5xxRate = new HashMap<String, Double>();

	@Override
	public void calculate(NginxStatus prev, NginxStatus cur) {
		double time_frame = calculateTimeFrame(prev, cur);
		this.calculateServerZones(prev, cur, time_frame);
	}

	public void calculateServerZones(NginxStatus prev, NginxStatus cur, double time_frame) {
		Iterator<ServerZoneDTO> curIter = cur.getServerZones().iterator();
		Iterator<ServerZoneDTO> prevIter = prev.getServerZones().iterator();

		while(curIter.hasNext()) {
			ServerZoneDTO cur_ = curIter.next();
			ServerZoneDTO prev_;
			Double RequestsRate;
			Double ResponsesRate;
			Double Responses1xxRate;
			Double Responses2xxRate;
			Double Responses3xxRate;
			Double Responses4xxRate;
			Double Responses5xxRate;
			Double Discarded;
			Double TrafficSentRate;
			Double TrafficRecvRate;

			String serverZoneName = cur_.getServerZoneName();

			try {
				prev_ = prevIter.next();

				RequestsRate = (cur_.getRequests() - prev_.getRequests()) / time_frame;
				ResponsesRate = (cur_.getTotalResponses() - prev_.getTotalResponses()) / time_frame;
				Responses1xxRate = (cur_.getResponses1xx() - prev_.getResponses1xx()) / time_frame;
				Responses2xxRate = (cur_.getResponses2xx() - prev_.getResponses2xx()) / time_frame;
				Responses3xxRate = (cur_.getResponses3xx() - prev_.getResponses3xx()) / time_frame;
				Responses4xxRate = (cur_.getResponses4xx() - prev_.getResponses4xx()) / time_frame;
				Responses5xxRate = (cur_.getResponses5xx() - prev_.getResponses5xx()) / time_frame;
				Discarded = (cur_.getDiscarded() - prev_.getDiscarded()) / time_frame;
				TrafficSentRate = (cur_.getSent() - prev_.getSent()) / time_frame;
				TrafficRecvRate = (cur_.getReceived() - prev_.getReceived()) / time_frame;

			} catch (NoSuchElementException e) {
				RequestsRate = Double.NaN;
				ResponsesRate = Double.NaN;
				Responses1xxRate = Double.NaN;
				Responses2xxRate = Double.NaN;
				Responses3xxRate = Double.NaN;
				Responses4xxRate = Double.NaN;
				Responses5xxRate = Double.NaN;
				Discarded = Double.NaN;
				TrafficSentRate = Double.NaN;
				TrafficRecvRate = Double.NaN;
			}
			this.requestsRate.put(serverZoneName, RequestsRate);
			this.responsesRate.put(serverZoneName, ResponsesRate);
			this.responses1xxRate.put(serverZoneName, Responses1xxRate);
			this.responses2xxRate.put(serverZoneName, Responses2xxRate);
			this.responses3xxRate.put(serverZoneName, Responses3xxRate);
			this.responses4xxRate.put(serverZoneName, Responses4xxRate);
			this.responses5xxRate.put(serverZoneName, Responses5xxRate);
			this.discarded.put(serverZoneName, Discarded);
			this.trafficSentRate.put(serverZoneName, TrafficSentRate);
			this.trafficRecvRate.put(serverZoneName, TrafficRecvRate);
		}
	}

	public Map<String, Double> getRequestsRate() {
		return requestsRate;
	}

	public Map<String, Double> getResponsesRate() {
		return responsesRate;
	}

	public Map<String, Double> getTrafficSentRate() {
		return trafficSentRate;
	}

	public Map<String, Double> getTrafficRecvRate() {
		return trafficRecvRate;
	}

	public Map<String, Double> getResponses1xxRate() {
		return responses1xxRate;
	}

	public Map<String, Double> getResponses2xxRate() {
		return responses2xxRate;
	}

	public Map<String, Double> getResponses3xxRate() {
		return responses3xxRate;
	}

	public Map<String, Double> getResponses4xxRate() {
		return responses4xxRate;
	}

	public Map<String, Double> getResponses5xxRate() {
		return responses5xxRate;
	}

	public Map<String, Double> getDiscardedRate() {
		return discarded;
	}
}
