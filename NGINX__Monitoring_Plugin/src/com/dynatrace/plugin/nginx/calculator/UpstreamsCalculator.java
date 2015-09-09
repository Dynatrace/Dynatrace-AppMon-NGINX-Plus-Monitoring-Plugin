package com.dynatrace.plugin.nginx.calculator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.dynatrace.plugin.nginx.dto.NginxStatus;
import com.dynatrace.plugin.nginx.dto.ServerDTO;
import com.dynatrace.plugin.nginx.dto.StateT;
import com.google.common.collect.HashBasedTable;

public class UpstreamsCalculator extends TimeFrameCalculator implements Calculator {
    private Double totalStateUp = 0.0;
	private Double totalStateDraining = 0.0;
    private Double totalStateDown = 0.0;
    private Double totalStateUnavail = 0.0;
    private Double totalStateUnhealthy = 0.0;

	private Double totalActive = 0.0;

	private HashBasedTable<String, String, Double> requestsRate     = HashBasedTable.create();
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

				this.requestsRate.put(serverGroupName, cur_.getServer(), RequestsRate);
				this.responsesRate.put(serverGroupName, cur_.getServer(), ResponsesRate);
				this.responses1xxRate.put(serverGroupName, cur_.getServer(), Responses1xxRate);
				this.responses2xxRate.put(serverGroupName, cur_.getServer(), Responses2xxRate);
				this.responses3xxRate.put(serverGroupName, cur_.getServer(), Responses3xxRate);
				this.responses4xxRate.put(serverGroupName, cur_.getServer(), Responses4xxRate);
				this.responses5xxRate.put(serverGroupName, cur_.getServer(), Responses5xxRate);
				this.trafficSentRate.put(serverGroupName, cur_.getServer(), TrafficSentRate);
				this.trafficRecvRate.put(serverGroupName, cur_.getServer(), TrafficRecvRate);
			}
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
}
