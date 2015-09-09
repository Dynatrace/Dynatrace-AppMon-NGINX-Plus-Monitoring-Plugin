package com.dynatrace.plugin.nginx.calculator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import com.dynatrace.plugin.nginx.dto.NginxStatus;
import com.dynatrace.plugin.nginx.dto.ServerZoneDTO;

public class ServerZonesCalculator extends TimeFrameCalculator implements Calculator {
	private Double totalProcessing = 0.0;

	private Map<String, Double> requestsRate = new HashMap<String, Double>();
	private Double totalRequestsRate = 0.0;

	private Map<String, Double> responsesRate = new HashMap<String, Double>();
	private Double totalResponsesRate = 0.0;

	private Map<String, Double> trafficSentRate = new HashMap<String, Double>();
	private Double totalTrafficSentRate = 0.0;

	private Map<String, Double> trafficRecvRate = new HashMap<String, Double>();
	private Double totalTrafficRecvRate = 0.0;

	private Map<String, Double> responses1xxRate = new HashMap<String, Double>();
	private Double totalResponses1xxRate = 0.0;

	private Map<String, Double> responses2xxRate = new HashMap<String, Double>();
	private Double totalResponses2xxRate = 0.0;

	private Map<String, Double> responses3xxRate = new HashMap<String, Double>();
	private Double totalResponses3xxRate = 0.0;

	private Map<String, Double> responses4xxRate = new HashMap<String, Double>();
	private Double totalResponses4xxRate = 0.0;

	private Map<String, Double> responses5xxRate = new HashMap<String, Double>();
	private Double totalResponses5xxRate = 0.0;

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
			Double TrafficSentRate;
			Double TrafficRecvRate;

			String serverZoneName = cur_.getServerZoneName();

			try {
				prev_ = prevIter.next();

				totalProcessing += cur_.getProcessing();

				RequestsRate = (cur_.getRequests() - prev_.getRequests()) / time_frame;
				ResponsesRate = (cur_.getTotalResponses() - prev_.getTotalResponses()) / time_frame;
				Responses1xxRate = (cur_.getResponses1xx() - prev_.getResponses1xx()) / time_frame;
				Responses2xxRate = (cur_.getResponses2xx() - prev_.getResponses2xx()) / time_frame;
				Responses3xxRate = (cur_.getResponses3xx() - prev_.getResponses3xx()) / time_frame;
				Responses4xxRate = (cur_.getResponses4xx() - prev_.getResponses4xx()) / time_frame;
				Responses5xxRate = (cur_.getResponses5xx() - prev_.getResponses5xx()) / time_frame;
				TrafficSentRate = (cur_.getSent() - prev_.getSent()) / time_frame;
				TrafficRecvRate = (cur_.getReceived() - prev_.getReceived()) / time_frame;

			} catch (NoSuchElementException e) {
				totalProcessing += cur_.getProcessing();

				RequestsRate = cur_.getRequests() / time_frame;
				ResponsesRate = cur_.getTotalResponses() / time_frame;
				Responses1xxRate = cur_.getResponses1xx() / time_frame;
				Responses2xxRate = cur_.getResponses2xx() / time_frame;
				Responses3xxRate = cur_.getResponses3xx() / time_frame;
				Responses4xxRate = cur_.getResponses4xx() / time_frame;
				Responses5xxRate = cur_.getResponses5xx() / time_frame;
				TrafficSentRate = cur_.getSent() / time_frame;
				TrafficRecvRate = cur_.getReceived() / time_frame;
			}
			this.totalRequestsRate += RequestsRate;
			this.requestsRate.put(serverZoneName, RequestsRate);

			this.totalResponsesRate += ResponsesRate;
			this.responsesRate.put(serverZoneName, ResponsesRate);

			this.totalResponses1xxRate += Responses1xxRate;
			this.responses1xxRate.put(serverZoneName, Responses1xxRate);

			this.totalResponses2xxRate += Responses2xxRate;
			this.responses2xxRate.put(serverZoneName, Responses2xxRate);

			this.totalResponses3xxRate += Responses3xxRate;
			this.responses3xxRate.put(serverZoneName, Responses3xxRate);

			this.totalResponses4xxRate += Responses4xxRate;
			this.responses4xxRate.put(serverZoneName, Responses4xxRate);

			this.totalResponses5xxRate += Responses5xxRate;
			this.responses5xxRate.put(serverZoneName, Responses5xxRate);

			this.totalTrafficSentRate += TrafficSentRate;
			this.trafficSentRate.put(serverZoneName, TrafficSentRate);

			this.totalTrafficRecvRate += TrafficRecvRate;
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

	public Double getTotalRequestsRate() {
		return totalRequestsRate;
	}

	public Double getTotalResponsesRate() {
		return totalResponsesRate;
	}

	public Double getTotalTrafficSentRate() {
		return totalTrafficSentRate;
	}

	public Double getTotalTrafficRecvRate() {
		return totalTrafficRecvRate;
	}

	public Double getTotalResponses1xxRate() {
		return totalResponses1xxRate;
	}

	public Double getTotalResponses2xxRate() {
		return totalResponses2xxRate;
	}

	public Double getTotalResponses3xxRate() {
		return totalResponses3xxRate;
	}

	public Double getTotalResponses4xxRate() {
		return totalResponses4xxRate;
	}

	public Double getTotalResponses5xxRate() {
		return totalResponses5xxRate;
	}

	public Double getTotalProcessing() {
		return totalProcessing;
	}
}
