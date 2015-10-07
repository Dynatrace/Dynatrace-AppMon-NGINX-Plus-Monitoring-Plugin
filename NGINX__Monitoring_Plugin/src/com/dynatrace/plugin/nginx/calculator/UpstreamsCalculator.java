package com.dynatrace.plugin.nginx.calculator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.dynatrace.plugin.nginx.dto.NginxStatus;
import com.dynatrace.plugin.nginx.dto.StateT;
import com.dynatrace.plugin.nginx.dto.upstreams.ServerDTO;
import com.google.common.collect.HashBasedTable;

public class UpstreamsCalculator extends TimeFrameCalculator implements Calculator {

	private HashMap<String, Double> RequestsRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> RequestsPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> ResponsesRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> ResponsesPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> Responses1xxRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> Responses1xxPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> Responses2xxRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> Responses2xxPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> Responses3xxRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> Responses3xxPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> Responses4xxRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> Responses4xxPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> Responses5xxRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> Responses5xxPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> SentRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> SentPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> ReceivedRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> ReceivedPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> FailsPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> FailsRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> UnavailPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> UnavailRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> HealthChecksTotalPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> HealthChecksTotalRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> HealthChecksFailedPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> HealthChecksFailedRatePerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> HealthChecksUnhealthyPerUpstream = new HashMap<String, Double>();
	private HashMap<String, Double> HealthChecksUnhealthyRatePerUpstream = new HashMap<String, Double>();

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
	private HashBasedTable<String, String, Double> sentRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> receivedRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> failsRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> unavailRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> healthChecksRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> healthChecksFailedRate = HashBasedTable.create();
	private HashBasedTable<String, String, Double> healthChecksUnhealthyRate = HashBasedTable.create();

	Double UnavailRate = 0.0;
	Double HealthChecksRate = 0.0;
	Double HealthChecksFailedRate = 0.0;
	Double HealthChecksUnhealthyRate = 0.0;

	@Override
	public void calculate(NginxStatus prev, NginxStatus cur) {
		double time_frame = this.calculateTimeFrame(prev, cur);
		this.calculateUpstreams(prev, cur, time_frame);
	}

	public void calculateUpstreams(NginxStatus prev, NginxStatus cur, double time_frame) {
		for (String serverGroupName : cur.getUpstreams().get().keySet()) {
			Double requestsPerUpstream = 0.0;
			Double requestsRatePerUpstream = 0.0;
			Double responsesPerUpstream = 0.0;
			Double responsesRatePerUpstream = 0.0;
			Double responses1xxPerUpstream = 0.0;
			Double responses1xxRatePerUpstream = 0.0;
			Double responses2xxPerUpstream = 0.0;
			Double responses2xxRatePerUpstream = 0.0;
			Double responses3xxPerUpstream = 0.0;
			Double responses3xxRatePerUpstream = 0.0;
			Double responses4xxPerUpstream = 0.0;
			Double responses4xxRatePerUpstream = 0.0;
			Double responses5xxPerUpstream = 0.0;
			Double responses5xxRatePerUpstream = 0.0;
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
				Double SentRate = 0.0;
				Double ReceivedRate = 0.0;
				Double FailsRate = 0.0;
				Double UnavailRate = 0.0;
				Double HealthChecksRate = 0.0;
				Double HealthChecksFailedRate = 0.0;
				Double HealthChecksUnhealthyRate = 0.0;

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
					SentRate = (cur_.getSent() - prev_.getSent()) /time_frame;
					ReceivedRate = (cur_.getReceived() - prev_.getReceived()) / time_frame;
					FailsRate = (cur_.getFails() - prev_.getFails()) / time_frame;
					UnavailRate = (cur_.getUnavail() - prev_.getUnavail()) / time_frame;
					HealthChecksRate = (cur_.getHealthChecksTotal() - prev_.getHealthChecksTotal()) / time_frame;
					HealthChecksFailedRate = (cur_.getHealthChecksFails() - prev_.getHealthChecksFails()) / time_frame;
					HealthChecksUnhealthyRate = (cur_.getHealthChecksUnhealthy() - prev_.getHealthChecksUnhealthy()) / time_frame;

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
					SentRate = Double.NaN;
					ReceivedRate = Double.NaN;
					FailsRate = Double.NaN;
					UnavailRate = Double.NaN;
					HealthChecksRate = Double.NaN;
					HealthChecksFailedRate = Double.NaN;
					HealthChecksUnhealthyRate = Double.NaN;
				}

				this.requestsRate.put(serverGroupName, cur_.getServer(), RequestsRate);
				this.responsesRate.put(serverGroupName, cur_.getServer(), ResponsesRate);
				this.responses1xxRate.put(serverGroupName, cur_.getServer(), Responses1xxRate);
				this.responses2xxRate.put(serverGroupName, cur_.getServer(), Responses2xxRate);
				this.responses3xxRate.put(serverGroupName, cur_.getServer(), Responses3xxRate);
				this.responses4xxRate.put(serverGroupName, cur_.getServer(), Responses4xxRate);
				this.responses5xxRate.put(serverGroupName, cur_.getServer(), Responses5xxRate);
				this.sentRate.put(serverGroupName, cur_.getServer(), SentRate);
				this.receivedRate.put(serverGroupName, cur_.getServer(), ReceivedRate);
				this.failsRate.put(serverGroupName, cur_.getServer(), FailsRate);
				this.unavailRate.put(serverGroupName, serverGroupName, UnavailRate);
				this.healthChecksRate.put(serverGroupName, cur_.getServer(), HealthChecksRate);
				this.healthChecksFailedRate.put(serverGroupName, cur_.getServer(), HealthChecksFailedRate);
				this.healthChecksUnhealthyRate.put(serverGroupName, cur_.getServer(), HealthChecksUnhealthyRate);

				requestsPerUpstream += cur_.getRequests();
				requestsRatePerUpstream += RequestsRate;
				responsesPerUpstream += cur_.getTotalResponses();
				responsesRatePerUpstream += ResponsesRate;
				responses1xxPerUpstream += cur_.getResponses1xx();
				responses1xxRatePerUpstream += Responses1xxRate;
				responses2xxPerUpstream += cur_.getResponses2xx();
				responses2xxRatePerUpstream += Responses2xxRate;
				responses3xxPerUpstream += cur_.getResponses3xx();
				responses3xxRatePerUpstream += Responses3xxRate;
				responses4xxPerUpstream += cur_.getResponses4xx();
				responses4xxRatePerUpstream += Responses4xxRate;
				responses5xxPerUpstream += cur_.getResponses5xx();
				responses5xxRatePerUpstream += Responses5xxRate;
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
			this.RequestsPerUpstream.put(serverGroupName, requestsPerUpstream);
			this.RequestsRatePerUpstream.put(serverGroupName, requestsRatePerUpstream);
			this.ResponsesPerUpstream.put(serverGroupName, responsesPerUpstream);
			this.ResponsesRatePerUpstream.put(serverGroupName, responsesRatePerUpstream);
			this.Responses1xxPerUpstream.put(serverGroupName, responses1xxPerUpstream);
			this.Responses1xxRatePerUpstream.put(serverGroupName, responses1xxRatePerUpstream);
			this.Responses2xxPerUpstream.put(serverGroupName, responses2xxPerUpstream);
			this.Responses2xxRatePerUpstream.put(serverGroupName, responses2xxRatePerUpstream);
			this.Responses3xxPerUpstream.put(serverGroupName, responses3xxPerUpstream);
			this.Responses3xxRatePerUpstream.put(serverGroupName, responses3xxRatePerUpstream);
			this.Responses4xxPerUpstream.put(serverGroupName, responses4xxPerUpstream);
			this.Responses4xxRatePerUpstream.put(serverGroupName, responses4xxRatePerUpstream);
			this.Responses5xxPerUpstream.put(serverGroupName, responses5xxPerUpstream);
			this.Responses5xxRatePerUpstream.put(serverGroupName, responses5xxRatePerUpstream);
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

	public HashBasedTable<String, String, Double> getSentRate() {
		return sentRate;
	}

	public HashBasedTable<String, String, Double> getReceivedRate() {
		return receivedRate;
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

	public HashMap<String, Double> getRequestsRatePerUpstream() {
		return RequestsRatePerUpstream;
	}

	public HashMap<String, Double> getResponsesRatePerUpstream() {
		return ResponsesRatePerUpstream;
	}

	public HashMap<String, Double> getResponses1xxRatePerUpstream() {
		return Responses1xxRatePerUpstream;
	}

	public HashMap<String, Double> getResponses2xxRatePerUpstream() {
		return Responses2xxRatePerUpstream;
	}

	public HashMap<String, Double> getResponses3xxRatePerUpstream() {
		return Responses3xxRatePerUpstream;
	}

	public HashMap<String, Double> getResponses4xxRatePerUpstream() {
		return Responses4xxRatePerUpstream;
	}

	public HashMap<String, Double> getResponses5xxRatePerUpstream() {
		return Responses5xxRatePerUpstream;
	}

	public HashMap<String, Double> getSentRatePerUpstream() {
		return SentRatePerUpstream;
	}

	public HashMap<String, Double> getReceivedRatePerUpstream() {
		return ReceivedRatePerUpstream;
	}

	public HashMap<String, Double> getRequestsPerUpstream() {
		return RequestsPerUpstream;
	}

	public HashMap<String, Double> getResponsesPerUpstream() {
		return ResponsesPerUpstream;
	}

	public HashMap<String, Double> getResponses1xxPerUpstream() {
		return Responses1xxPerUpstream;
	}

	public HashMap<String, Double> getResponses2xxPerUpstream() {
		return Responses2xxPerUpstream;
	}

	public HashMap<String, Double> getResponses4xxPerUpstream() {
		return Responses4xxPerUpstream;
	}

	public HashMap<String, Double> getResponses3xxPerUpstream() {
		return Responses3xxPerUpstream;
	}

	public HashMap<String, Double> getResponses5xxPerUpstream() {
		return Responses5xxPerUpstream;
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

	public HashBasedTable<String, String, Double> getFailsRate() {
		return failsRate;
	}

	public HashBasedTable<String, String, Double> getUnavailRate() {
		return unavailRate;
	}

	public HashBasedTable<String, String, Double> getHealthChecksRate() {
		return healthChecksRate;
	}

	public HashBasedTable<String, String, Double> getHealthChecksFailedRate() {
		return healthChecksFailedRate;
	}

	public HashBasedTable<String, String, Double> getHealthChecksUnhealthyRate() {
		return healthChecksUnhealthyRate;
	}

	public HashMap<String, Double> getFailsRatePerUpstream() {
		return FailsRatePerUpstream;
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
