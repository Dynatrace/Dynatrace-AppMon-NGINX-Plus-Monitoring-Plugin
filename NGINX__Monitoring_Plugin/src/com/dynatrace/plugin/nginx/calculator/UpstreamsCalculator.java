package com.dynatrace.plugin.nginx.calculator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import com.dynatrace.plugin.nginx.dto.NginxStatus;
import com.dynatrace.plugin.nginx.dto.StateT;
import com.dynatrace.plugin.nginx.dto.upstreams.ServerDTO;
import com.google.common.collect.HashBasedTable;

public class UpstreamsCalculator extends TimeFrameCalculator implements Calculator {

	public static final String UpstreamOther = "other";

	private HashMap<String, Double> RequestsRatePerUpstreamMap = new HashMap<String, Double>();
	private HashMap<String, Double> ResponsesRatePerUpstreamMap = new HashMap<String, Double>();
	private HashMap<String, Double> Responses1xxRatePerUpstreamMap = new HashMap<String, Double>();
	private HashMap<String, Double> Responses2xxRatePerUpstreamMap = new HashMap<String, Double>();
	private HashMap<String, Double> Responses3xxRatePerUpstreamMap = new HashMap<String, Double>();
	private HashMap<String, Double> Responses4xxRatePerUpstreamMap = new HashMap<String, Double>();
	private HashMap<String, Double> Responses5xxRatePerUpstreamMap = new HashMap<String, Double>();
	private HashMap<String, Double> TrafficSentRatePerUpstreamMap = new HashMap<String, Double>();
	private HashMap<String, Double> TrafficRecvRatePerUpstreamMap = new HashMap<String, Double>();

    private Double totalStateUp = 0.0;
	private Double totalStateDraining = 0.0;
    private Double totalStateDown = 0.0;
    private Double totalStateUnavail = 0.0;
    private Double totalStateUnhealthy = 0.0;

	private Double totalActive = 0.0;

	private HashBasedTable<String, String, Double> requestsRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> responsesRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> responses1xxRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> responses2xxRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> responses3xxRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> responses4xxRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> responses5xxRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> trafficSentRate  = HashBasedTable.create();
	private HashBasedTable<String, String, Double> trafficRecvRate  = HashBasedTable.create();

	@Override
	public void calculate(NginxStatus prev, NginxStatus cur) {
		double time_frame = this.calculateTimeFrame(prev, cur);
		this.calculateUpstreams(prev, cur, time_frame);
	}

	public void calculateUpstreams(NginxStatus prev, NginxStatus cur, double time_frame) {
		Double totalRequestsRate = 0.0;
		Double totalResponsesRate = 0.0;
		Double totalResponses1xxRate = 0.0;
		Double totalResponses2xxRate = 0.0;
		Double totalResponses3xxRate = 0.0;
		Double totalResponses4xxRate = 0.0;
		Double totalResponses5xxRate = 0.0;
		Double totalTrafficSentRate = 0.0;
		Double totalTrafficRecvRate = 0.0;
		for (String serverGroupName : cur.getUpstreams().get().keySet()) {
			Iterator<ServerDTO> curIter = cur.getUpstreams().get().get(serverGroupName).iterator();
			Iterator<ServerDTO> prevIter = prev.getUpstreams().get().get(serverGroupName).iterator();
			while(curIter.hasNext()) {
				ServerDTO cur_ = curIter.next();
				ServerDTO prev_;

				Double RequestsRate = 0.0;
				Double ResponsesRate = 0.0;
				Double Responses1xxRate = 0.0;
				Double Responses2xxRate = 0.0;
				Double Responses3xxRate = 0.0;
				Double Responses4xxRate = 0.0;
				Double Responses5xxRate = 0.0;
				Double TrafficSentRate = 0.0;
				Double TrafficRecvRate = 0.0;

				try {
					prev_ = prevIter.next();

					addState(cur_.getState());

					totalActive += cur_.getActive();

					RequestsRate = (cur_.getRequests() - prev_.getRequests()) / time_frame;
					ResponsesRate = (cur_.getTotalResponses() - prev_.getTotalResponses()) / time_frame;
					Responses1xxRate = (cur_.getResponses1xx() - prev_.getResponses1xx()) / time_frame;
					Responses2xxRate = (cur_.getResponses2xx() - prev_.getResponses2xx()) / time_frame;
					Responses3xxRate = (cur_.getResponses3xx() - prev_.getResponses3xx()) / time_frame;
					Responses4xxRate = (cur_.getResponses4xx() - prev_.getResponses4xx()) / time_frame;
					Responses5xxRate = (cur_.getResponses5xx() - prev_.getResponses5xx()) / time_frame;
					TrafficSentRate = (cur_.getSent() - prev_.getSent()) /time_frame;
					TrafficRecvRate = (cur_.getReceived() - prev_.getReceived()) / time_frame;
				} catch (NoSuchElementException e) {

					addState(cur_.getState());

					totalActive += cur_.getActive();

					RequestsRate = Double.NaN;
					ResponsesRate = Double.NaN;
					Responses1xxRate = Double.NaN;
					Responses2xxRate = Double.NaN;
					Responses3xxRate = Double.NaN;
					Responses4xxRate = Double.NaN;
					Responses5xxRate = Double.NaN;
					TrafficSentRate = Double.NaN;
					TrafficRecvRate = Double.NaN;
				}

				this.requestsRate.put(serverGroupName, cur_.getServer(), RequestsRate);
				this.responsesRate.put(serverGroupName, cur_.getServer(), ResponsesRate);
				this.responses1xxRate.put(serverGroupName, cur_.getServer(), Responses1xxRate);
				this.responses2xxRate.put(serverGroupName, cur_.getServer(), Responses2xxRate);
				this.responses3xxRate.put(serverGroupName, cur_.getServer(), Responses3xxRate);
				this.responses4xxRate.put(serverGroupName, cur_.getServer(), Responses4xxRate);
				this.responses5xxRate.put(serverGroupName, cur_.getServer(), Responses5xxRate);
				this.trafficSentRate.put(serverGroupName, cur_.getServer(), TrafficSentRate);
				this.trafficRecvRate.put(serverGroupName, cur_.getServer(), TrafficRecvRate);

				totalRequestsRate += RequestsRate;
				totalResponsesRate += ResponsesRate;
				totalResponses1xxRate += Responses1xxRate;
				totalResponses2xxRate += Responses2xxRate;
				totalResponses3xxRate += Responses3xxRate;
				totalResponses4xxRate += Responses4xxRate;
				totalResponses5xxRate += Responses5xxRate;
				totalTrafficSentRate += TrafficSentRate;
				totalTrafficRecvRate += TrafficRecvRate;
			}
		}

		for (String serverGroupName : cur.getUpstreams().get().keySet()) {
			Double RequestsRate = 0.0;
			Double ResponsesRate = 0.0;
			Double Responses1xxRate = 0.0;
			Double Responses2xxRate = 0.0;
			Double Responses3xxRate = 0.0;
			Double Responses4xxRate = 0.0;
			Double Responses5xxRate = 0.0;
			Double TrafficSentRate = 0.0;
			Double TrafficRecvRate = 0.0;


			Map<String, Double> requestRateMap = this.requestsRate.row(serverGroupName);
			for (String server : requestRateMap.keySet()) {
				RequestsRate += requestRateMap.get(server);
			}

			Map<String, Double> responseRateMap = this.responsesRate.row(serverGroupName);
			for (String server : responseRateMap.keySet()) {
				ResponsesRate += responseRateMap.get(server);
			}

			Map<String, Double> response1xxRateMap = this.responses1xxRate.row(serverGroupName);
			for (String server : response1xxRateMap.keySet()) {
				Responses1xxRate += response1xxRateMap.get(server);
			}

			Map<String, Double> response2xxRateMap = this.responses2xxRate.row(serverGroupName);
			for (String server : response2xxRateMap.keySet()) {
				Responses2xxRate += response2xxRateMap.get(server);
			}

			Map<String, Double> response3xxRateMap = this.responses3xxRate.row(serverGroupName);
			for (String server : response3xxRateMap.keySet()) {
				Responses3xxRate += response3xxRateMap.get(server);
			}

			Map<String, Double> response4xxRateMap = this.responses4xxRate.row(serverGroupName);
			for (String server : responseRateMap.keySet()) {
				Responses4xxRate += response4xxRateMap.get(server);
			}

			Map<String, Double> response5xxRateMap = this.responses5xxRate.row(serverGroupName);
			for (String server : response5xxRateMap.keySet()) {
				Responses5xxRate += response5xxRateMap.get(server);
			}

			Map<String, Double> trafficSentRateMap = this.trafficSentRate.row(serverGroupName);
			for (String server : trafficSentRateMap.keySet()) {
				TrafficSentRate += trafficSentRateMap.get(server);
			}

			Map<String, Double> trafficRecvRateMap = this.trafficRecvRate.row(serverGroupName);
			for (String server : trafficRecvRateMap.keySet()) {
				TrafficRecvRate += trafficRecvRateMap.get(server);
			}

			this.RequestsRatePerUpstreamMap.put(serverGroupName, RequestsRate);
			this.ResponsesRatePerUpstreamMap.put(serverGroupName, ResponsesRate);
			this.Responses1xxRatePerUpstreamMap.put(serverGroupName, Responses1xxRate);
			this.Responses2xxRatePerUpstreamMap.put(serverGroupName, Responses2xxRate);
			this.Responses3xxRatePerUpstreamMap.put(serverGroupName, Responses3xxRate);
			this.Responses4xxRatePerUpstreamMap.put(serverGroupName, Responses4xxRate);
			this.Responses5xxRatePerUpstreamMap.put(serverGroupName, Responses5xxRate);
			this.TrafficSentRatePerUpstreamMap.put(serverGroupName, TrafficSentRate);
			this.TrafficRecvRatePerUpstreamMap.put(serverGroupName, TrafficRecvRate);

			this.requestsRate.put(serverGroupName, UpstreamOther, totalRequestsRate - RequestsRate);
			this.responsesRate.put(serverGroupName, UpstreamOther, totalRequestsRate - ResponsesRate);
			this.responses1xxRate.put(serverGroupName, UpstreamOther, totalResponses1xxRate - Responses1xxRate);
			this.responses2xxRate.put(serverGroupName, UpstreamOther, totalResponses2xxRate - Responses2xxRate);
			this.responses3xxRate.put(serverGroupName, UpstreamOther, totalResponses3xxRate - Responses3xxRate);
			this.responses4xxRate.put(serverGroupName, UpstreamOther, totalResponses4xxRate - Responses4xxRate);
			this.responses5xxRate.put(serverGroupName, UpstreamOther, totalResponses5xxRate - Responses5xxRate);
			this.trafficSentRate.put(serverGroupName, UpstreamOther, totalTrafficSentRate - TrafficSentRate);
			this.trafficRecvRate.put(serverGroupName, UpstreamOther, totalTrafficRecvRate - TrafficRecvRate);
		}
	}

	private void addState(StateT state) {
		if (state.equals(StateT.UP)) {
            this.totalStateUp++;
        } else if (state.equals(StateT.DRAINING)) {
        	this.totalStateDraining++;
        } else if (state.equals(StateT.DOWN)) {
            this.totalStateDown++;
        } else if (state.equals(StateT.UNAVAIL)) {
            this.totalStateUnavail++;
        } else if (state.equals(StateT.UNHEALTHY)) {
            this.totalStateUnhealthy++;
        } else {
        	assert(false); //invalid state
        }
	}

	public HashBasedTable<String, String, Double> getRequestsRate() {
		return requestsRate;
	}

	public HashBasedTable<String, String, Double> getResponsesRate() {
		return responsesRate;
	}

	public HashBasedTable<String, String, Double> getTrafficSentRate() {
		return trafficSentRate;
	}

	public HashBasedTable<String, String, Double> getTrafficRecvRate() {
		return trafficRecvRate;
	}

	public HashBasedTable<String, String, Double> getResponses1xxRate() {
		return responses1xxRate;
	}

	public HashBasedTable<String, String, Double> getResponses2xxRate() {
		return responses2xxRate;
	}

	public HashBasedTable<String, String, Double> getResponses3xxRate() {
		return responses3xxRate;
	}

	public HashBasedTable<String, String, Double> getResponses4xxRate() {
		return responses4xxRate;
	}

	public HashBasedTable<String, String, Double> getResponses5xxRate() {
		return responses5xxRate;
	}

	public Double getTotalActive() {
		return totalActive;
	}

	public Double getTotalStateDraining() {
		return totalStateDraining;
	}

	public Double getTotalStateUp() {
		return totalStateUp;
	}

	public Double getTotalStateDown() {
		return totalStateDown;
	}

	public Double getTotalStateUnavail() {
		return totalStateUnavail;
	}

	public Double getTotalStateUnhealthy() {
		return totalStateUnhealthy;
	}

	public Double GetTotalTrafficRecvRate() {
		return this.GetTotalTrafficRecvRate();
	}

	public HashMap<String, Double> getRequestsRatePerUpstreamMap() {
		return RequestsRatePerUpstreamMap;
	}

	public HashMap<String, Double> getResponsesRatePerUpstreamMap() {
		return ResponsesRatePerUpstreamMap;
	}

	public HashMap<String, Double> getResponses1xxRatePerUpstreamMap() {
		return Responses1xxRatePerUpstreamMap;
	}

	public HashMap<String, Double> getResponses2xxRatePerUpstreamMap() {
		return Responses2xxRatePerUpstreamMap;
	}

	public HashMap<String, Double> getResponses3xxRatePerUpstreamMap() {
		return Responses3xxRatePerUpstreamMap;
	}

	public HashMap<String, Double> getResponses4xxRatePerUpstreamMap() {
		return Responses4xxRatePerUpstreamMap;
	}

	public HashMap<String, Double> getResponses5xxRatePerUpstreamMap() {
		return Responses5xxRatePerUpstreamMap;
	}

	public HashMap<String, Double> getTrafficSentRatePerUpstreamMap() {
		return TrafficSentRatePerUpstreamMap;
	}

	public HashMap<String, Double> getTrafficRecvRatePerUpstreamMap() {
		return TrafficRecvRatePerUpstreamMap;
	}
}
