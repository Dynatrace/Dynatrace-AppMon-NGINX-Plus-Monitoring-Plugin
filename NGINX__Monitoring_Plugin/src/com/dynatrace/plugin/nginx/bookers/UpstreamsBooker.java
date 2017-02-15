package com.dynatrace.plugin.nginx.bookers;

import java.util.Collection;

import org.json.JSONException;

import com.dynatrace.diagnostics.pdk.MonitorEnvironment;
import com.dynatrace.diagnostics.pdk.MonitorMeasure;
import com.dynatrace.plugin.nginx.calculator.UpstreamsCalculator;
import com.dynatrace.plugin.nginx.dto.upstreams.ServerDTO;
import com.dynatrace.plugin.nginx.dto.upstreams.ServerGroups;
import com.dynatrace.plugin.nginx.parsers.UpstreamsParser;
import com.dynatrace.plugin.nginx.utils.ServerMatcher;

public class UpstreamsBooker extends Booker {
	public static final String MSR_BACKUP = "Backup";
	public static final String MSR_WEIGHT = "Weight";
	public static final String MSR_STATE = "State";
	public static final String MSRS_STATES = "State By Upstream";

	public static final String MSR_ACTIVE = "Active";

	public static final String MSR_REQUESTS = "Requests";
	public static final String MSR_REQUESTS_RATE = "Requests Rate";

	public static final String MSR_1XX = "1xx Responses";
	public static final String MSR_1XX_RATE = "1xx Responses Rate";

	public static final String MSR_2XX = "2xx Responses";
	public static final String MSR_2XX_RATE = "2xx Responses Rate";

	public static final String MSR_3XX = "3xx Responses";
	public static final String MSR_3XX_RATE = "3xx Responses Rate";

	public static final String MSR_4XX = "4xx Responses";
	public static final String MSR_4XX_RATE = "4xx Responses Rate";

	public static final String MSR_5XX = "5xx Responses";
	public static final String MSR_5XX_RATE = "5xx Responses Rate";

	public static final String MSR_RESPONSES = "Responses";
	public static final String MSR_RESPONSES_RATE = "Responses Rate";

	public static final String MSR_RECEIVED = "Received";
	public static final String MSR_RECEIVED_RATE = "Received Rate";

	public static final String MSR_SENT = "Sent";
	public static final String MSR_SENT_RATE = "Sent Rate";

	public static final String MSR_FAILS = "Fails";
	public static final String MSR_FAILS_RATE = "Fails Rate";

	public static final String MSR_UNAVAILABLE = "Unavailable";
	public static final String MSR_UNAVAILABLE_RATE = "Unavailable Rate";

	public static final String MSR_TOTAL_HEALTH_CHECKS = "Total Health Checks";
	public static final String MSR_TOTAL_HEALTH_CHECKS_RATE = "Total Health Checks Rate";

	public static final String MSR_FAILED_HEALTH_CHECKS = "Failed Health Checks";
	public static final String MSR_FAILED_HEALTH_CHECKS_RATE = "Failed Health Checks Rate";

	public static final String MSR_UNHEALTHY_HEALTH_CHECKS = "Unhealthy Health Checks";
	public static final String MSR_UNHEALTHY_HEALTH_CHECKS_RATE = "Unhealthy Health Checks Rate";

	public static final String MSR_LAST_HEALTH_CHECK_PASSED = "Last Health Check Passed";
	public static final String MSR_DOWNSTART = "Downstart";
	public static final String MSR_DOWNTIME = "Downtime";
	public static final String MSR_STATE_UP = "Total State Up";
	public static final String MSR_STATE_DRAINING = "Total State Draining";
	public static final String MSR_STATE_DOWN = "Total State Down";
	public static final String MSR_STATE_UNAVAIL = "Total State Unavailable";
	public static final String MSR_STATE_UNHEALTHY = "Total State Unhealthy";

	public static void book (ServerGroups serverGroups, MonitorEnvironment env, UpstreamsCalculator calculator) throws JSONException {
		Collection<MonitorMeasure> totalStateUpM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_STATE_UP);
		Collection<MonitorMeasure> totalStateDrainingM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_STATE_DRAINING);
		Collection<MonitorMeasure> totalStateDownM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_STATE_DOWN);
		Collection<MonitorMeasure> totalStateUnavailM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_STATE_UNAVAIL);
		Collection<MonitorMeasure> totalStateUnhealthyM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_STATE_UNHEALTHY);

		Collection<MonitorMeasure> backupM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_BACKUP);
		Collection<MonitorMeasure> weightM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_WEIGHT);

		Collection<MonitorMeasure> stateM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_STATE);
		Collection<MonitorMeasure> statesM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSRS_STATES);
		Collection<MonitorMeasure> activeM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_ACTIVE);

		Collection<MonitorMeasure> requestsM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_REQUESTS);
		Collection<MonitorMeasure> requestsRateM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_REQUESTS_RATE);

		Collection<MonitorMeasure> responses1xxM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_1XX);
		Collection<MonitorMeasure> responses1xxRateM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_1XX_RATE);

		Collection<MonitorMeasure> responses2xxM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_2XX);
		Collection<MonitorMeasure> responses2xxRateM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_2XX_RATE);

		Collection<MonitorMeasure> responses3xxM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_3XX);
		Collection<MonitorMeasure> responses3xxRateM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_3XX_RATE);

		Collection<MonitorMeasure> responses4xxM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_4XX);
		Collection<MonitorMeasure> responses4xxRateM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_4XX_RATE);

		Collection<MonitorMeasure> responses5xxM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_5XX);
		Collection<MonitorMeasure> responses5xxRateM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_5XX_RATE);

		Collection<MonitorMeasure> responsesM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_RESPONSES);
		Collection<MonitorMeasure> responsesRateM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_RESPONSES_RATE);

		Collection<MonitorMeasure> sentM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_SENT);
		Collection<MonitorMeasure> sentRateM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_SENT_RATE);

		Collection<MonitorMeasure> receivedM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_RECEIVED);
		Collection<MonitorMeasure> receivedRateM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_RECEIVED_RATE);

		Collection<MonitorMeasure> failsM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_FAILS);
		Collection<MonitorMeasure> failsRateM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_FAILS_RATE);

		Collection<MonitorMeasure> unavailableM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_UNAVAILABLE);
		Collection<MonitorMeasure> unavailableRateM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_UNAVAILABLE_RATE);

		Collection<MonitorMeasure> totalHealthChecksM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_TOTAL_HEALTH_CHECKS);
		Collection<MonitorMeasure> totalHealthChecksRateM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_TOTAL_HEALTH_CHECKS_RATE);
		Collection<MonitorMeasure> failedHealthChecksM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_FAILED_HEALTH_CHECKS);
		Collection<MonitorMeasure> failedHealthChecksRateM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_FAILED_HEALTH_CHECKS_RATE);
		Collection<MonitorMeasure> unhealthyHealthChecksM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_UNHEALTHY_HEALTH_CHECKS);
		Collection<MonitorMeasure> unhealthyHealthChecksRateM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_UNHEALTHY_HEALTH_CHECKS_RATE);
		Collection<MonitorMeasure> lastHealthCheckPassedM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_LAST_HEALTH_CHECK_PASSED);

		Collection<MonitorMeasure> downtimeM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_DOWNTIME);
		Collection<MonitorMeasure> downstartM = env.getMonitorMeasures(UpstreamsParser.GROUP, MSR_DOWNSTART);

		for (String serverGroupName: serverGroups.get().keySet()) {
			for (ServerDTO s : serverGroups.get().get(serverGroupName)) {
				Double RequestsRateTmp = calculator.getRequestsRate().get(serverGroupName, s.getServer());
				Double ResponsesRateTmp = calculator.getResponsesRate().get(serverGroupName, s.getServer());
				Double Responses1xxRateTmp = calculator.getResponses1xxRate().get(serverGroupName, s.getServer());
				Double Responses2xxRateTmp = calculator.getResponses2xxRate().get(serverGroupName, s.getServer());
				Double Responses3xxRateTmp = calculator.getResponses3xxRate().get(serverGroupName, s.getServer());
				Double Responses4xxRateTmp = calculator.getResponses4xxRate().get(serverGroupName, s.getServer());
				Double Responses5xxRateTmp = calculator.getResponses5xxRate().get(serverGroupName, s.getServer());
				Double SentRateTmp = calculator.getSentRate().get(serverGroupName, s.getServer());
				Double ReceivedRateTmp = calculator.getReceivedRate().get(serverGroupName, s.getServer());
				Double FailsRateTmp = calculator.getFailsRate().get(serverGroupName, s.getServer());
				Double UnavailRateTmp = calculator.getUnavailRate().get(serverGroupName, s.getServer());
				Double HealthChecksRate = calculator.getHealthChecksRate().get(serverGroupName, s.getServer());
				Double HealthChecksFailedRate = calculator.getHealthChecksFailedRate().get(serverGroupName, s.getServer());
				Double HealthChecksUnhealthyRate = calculator.getHealthChecksUnhealthyRate().get(serverGroupName, s.getServer());

				if (ServerMatcher.Match(s.getServer(), env.getConfigString("UpstreamsServerFilter"))) {
					String dynamicValue = s.getServer();
					setDynamicMeasure(env, backupM, serverGroupName, dynamicValue, (double) (s.getBackup() ? 1 : 0));
					setDynamicMeasure(env, weightM, serverGroupName, dynamicValue, s.getWeight());
					setDynamicMeasure(env, stateM, serverGroupName, dynamicValue, s.getState().toDouble());
					setDynamicMeasure(env, activeM, serverGroupName, dynamicValue, s.getActive());
					setDynamicMeasure(env, requestsM, serverGroupName, dynamicValue, s.getRequests());
					setDynamicMeasure(env, requestsRateM, serverGroupName, dynamicValue, RequestsRateTmp);
					setDynamicMeasure(env, responsesM, serverGroupName, dynamicValue, s.getTotalResponses());
					setDynamicMeasure(env, responsesRateM, serverGroupName, dynamicValue, ResponsesRateTmp);
					setDynamicMeasure(env, responses1xxM, serverGroupName, dynamicValue, s.getResponses1xx());
					setDynamicMeasure(env, responses1xxRateM, serverGroupName, dynamicValue, Responses1xxRateTmp);
					setDynamicMeasure(env, responses2xxM, serverGroupName, dynamicValue, s.getResponses2xx());
					setDynamicMeasure(env, responses2xxRateM, serverGroupName, dynamicValue, Responses2xxRateTmp);
					setDynamicMeasure(env, responses3xxM, serverGroupName, dynamicValue, s.getResponses3xx());
					setDynamicMeasure(env, responses3xxRateM, serverGroupName, dynamicValue, Responses3xxRateTmp);
					setDynamicMeasure(env, responses4xxM, serverGroupName, dynamicValue, s.getResponses4xx());
					setDynamicMeasure(env, responses4xxRateM, serverGroupName, dynamicValue, Responses4xxRateTmp);
					setDynamicMeasure(env, responses5xxM, serverGroupName, dynamicValue, s.getResponses5xx());
					setDynamicMeasure(env, responses5xxRateM, serverGroupName, dynamicValue, Responses5xxRateTmp);
					setDynamicMeasure(env, sentM, serverGroupName, dynamicValue, s.getSent());
					setDynamicMeasure(env, sentRateM, serverGroupName, dynamicValue, SentRateTmp);
					setDynamicMeasure(env, receivedM, serverGroupName, dynamicValue, s.getReceived());
					setDynamicMeasure(env, receivedRateM, serverGroupName, dynamicValue, ReceivedRateTmp);
					setDynamicMeasure(env, failsM, serverGroupName, dynamicValue, s.getFails());
					setDynamicMeasure(env, failsRateM, serverGroupName, dynamicValue, FailsRateTmp);
					setDynamicMeasure(env, unavailableM, serverGroupName, dynamicValue, s.getUnavail());
					setDynamicMeasure(env, unavailableRateM, serverGroupName, dynamicValue, UnavailRateTmp);
					setDynamicMeasure(env, totalHealthChecksM, serverGroupName, dynamicValue, s.getHealthChecksTotal());
					setDynamicMeasure(env, totalHealthChecksRateM, serverGroupName, dynamicValue, HealthChecksRate);
					setDynamicMeasure(env, failedHealthChecksM, serverGroupName, dynamicValue, s.getHealthChecksFails());
					setDynamicMeasure(env, failedHealthChecksRateM, serverGroupName, dynamicValue, HealthChecksFailedRate);
					setDynamicMeasure(env, unhealthyHealthChecksM, serverGroupName, dynamicValue, s.getHealthChecksUnhealthy());
					setDynamicMeasure(env, unhealthyHealthChecksRateM, serverGroupName, dynamicValue, HealthChecksUnhealthyRate);
					if (s.getHealthChecksLastPassed() != null) {
						setDynamicMeasure(env, lastHealthCheckPassedM, serverGroupName, dynamicValue, (double) (s.getHealthChecksLastPassed() ? 1 : 0));
					}
					setDynamicMeasure(env, downtimeM, serverGroupName, dynamicValue, s.getDowntime());
					setDynamicMeasure(env, downstartM, serverGroupName, dynamicValue, s.getDownstart());
				}
			}

			String dynamicKey = "Upstream Name";

			setDynamicMeasure(env, requestsRateM, dynamicKey, serverGroupName, calculator.getRequestsRatePerUpstream().get(serverGroupName));
			setDynamicMeasure(env, requestsM, dynamicKey, serverGroupName, calculator.getRequestsPerUpstream().get(serverGroupName));
			setDynamicMeasure(env, responsesRateM, dynamicKey, serverGroupName, calculator.getResponsesRatePerUpstream().get(serverGroupName));
			setDynamicMeasure(env, responsesM, dynamicKey, serverGroupName, calculator.getResponsesPerUpstream().get(serverGroupName));
			setDynamicMeasure(env, statesM, dynamicKey, serverGroupName, calculator.getStatePerUpstream().get(serverGroupName));
			setDynamicMeasure(env, responses1xxRateM, dynamicKey, serverGroupName, calculator.getResponses1xxRatePerUpstream().get(serverGroupName));
			setDynamicMeasure(env, responses1xxM, dynamicKey, serverGroupName, calculator.getResponses1xxPerUpstream().get(serverGroupName));
			setDynamicMeasure(env, responses2xxRateM, dynamicKey, serverGroupName, calculator.getResponses2xxRatePerUpstream().get(serverGroupName));
			setDynamicMeasure(env, responses2xxM, dynamicKey, serverGroupName, calculator.getResponses2xxPerUpstream().get(serverGroupName));
			setDynamicMeasure(env, responses3xxRateM, dynamicKey, serverGroupName, calculator.getResponses3xxRatePerUpstream().get(serverGroupName));
			setDynamicMeasure(env, responses3xxM, dynamicKey, serverGroupName, calculator.getResponses3xxPerUpstream().get(serverGroupName));
			setDynamicMeasure(env, responses4xxRateM, dynamicKey, serverGroupName, calculator.getResponses4xxRatePerUpstream().get(serverGroupName));
			setDynamicMeasure(env, responses4xxM, dynamicKey, serverGroupName, calculator.getResponses4xxPerUpstream().get(serverGroupName));
			setDynamicMeasure(env, responses5xxRateM, dynamicKey, serverGroupName, calculator.getResponses5xxRatePerUpstream().get(serverGroupName));
			setDynamicMeasure(env, responses5xxM, dynamicKey, serverGroupName, calculator.getResponses5xxPerUpstream().get(serverGroupName));
			setDynamicMeasure(env, sentRateM, dynamicKey, serverGroupName, calculator.getSentRatePerUpstream().get(serverGroupName));
			setDynamicMeasure(env, sentM, dynamicKey, serverGroupName, calculator.getSentPerUpstream().get(serverGroupName));
			setDynamicMeasure(env, receivedRateM, dynamicKey, serverGroupName, calculator.getReceivedRatePerUpstream().get(serverGroupName));
			setDynamicMeasure(env, receivedM, dynamicKey, serverGroupName, calculator.getReceivedPerUpstream().get(serverGroupName));
			setDynamicMeasure(env, failsM, dynamicKey, serverGroupName, calculator.getFailsPerUpstream().get(serverGroupName));
			setDynamicMeasure(env, failsRateM, dynamicKey, serverGroupName, calculator.getFailsRatePerUpstream().get(serverGroupName));
			setDynamicMeasure(env, unavailableM, dynamicKey, serverGroupName, calculator.getUnavailPerUpstream().get(serverGroupName));
			setDynamicMeasure(env, unavailableRateM, dynamicKey, serverGroupName, calculator.getUnavailRatePerUpstream().get(serverGroupName));
			setDynamicMeasure(env, totalHealthChecksM, dynamicKey, serverGroupName, calculator.getHealthChecksTotalPerUpstream().get(serverGroupName));
			setDynamicMeasure(env, totalHealthChecksRateM, dynamicKey, serverGroupName, calculator.getHealthChecksTotalRatePerUpstream().get(serverGroupName));
			setDynamicMeasure(env, failedHealthChecksM, dynamicKey, serverGroupName, calculator.getHealthChecksFailedPerUpstream().get(serverGroupName));
			setDynamicMeasure(env, failedHealthChecksRateM, dynamicKey, serverGroupName, calculator.getHealthChecksFailedRatePerUpstream().get(serverGroupName));
			setDynamicMeasure(env, unhealthyHealthChecksM, dynamicKey, serverGroupName, calculator.getHealthChecksUnhealthyPerUpstream().get(serverGroupName));
			setDynamicMeasure(env, unhealthyHealthChecksRateM, dynamicKey, serverGroupName, calculator.getHealthChecksUnhealthyRatePerUpstream().get(serverGroupName));
		}

		for (MonitorMeasure m : totalStateUpM) {
			m.setValue(calculator.getTotalStateUp());
		}

		for (MonitorMeasure m : totalStateDrainingM) {
			m.setValue(calculator.getTotalStateDraining());
		}

		for (MonitorMeasure m : totalStateDownM) {
			m.setValue(calculator.getTotalStateDown());
		}

		for (MonitorMeasure m : totalStateUnavailM) {
			m.setValue(calculator.getTotalStateUnavail());
		}

		for (MonitorMeasure m : totalStateUnhealthyM) {
			m.setValue(calculator.getTotalStateUnhealthy());
		}

		for (MonitorMeasure m : activeM) {
			m.setValue(calculator.getTotalActive());
		}
	}
}
