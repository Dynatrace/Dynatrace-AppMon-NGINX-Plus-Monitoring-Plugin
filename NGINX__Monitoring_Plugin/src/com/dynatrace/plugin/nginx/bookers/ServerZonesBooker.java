package com.dynatrace.plugin.nginx.bookers;

import java.util.Collection;

import com.dynatrace.diagnostics.pdk.MonitorEnvironment;
import com.dynatrace.diagnostics.pdk.MonitorMeasure;
import com.dynatrace.plugin.nginx.calculator.ServerZonesCalculator;
import com.dynatrace.plugin.nginx.dto.ServerZoneDTO;
import com.dynatrace.plugin.nginx.parsers.ServerZonesParser;


public class ServerZonesBooker extends Booker {
	public static final String MSR_PROCESSING = "Processing";

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

	public static final String MSR_DISCARDED = "Discarded";
	public static final String MSR_DISCARDED_RATE = "Discarded Rate";

	public static final String MSR_RECEIVED = "Received";
	public static final String MSR_RECEIVED_RATE = "Received Rate";

	public static final String MSR_SENT = "Sent";
	public static final String MSR_SENT_RATE = "Sent Rate";



	public static final String DYNAMIC_KEY = "Server Zone";

	public static void book(Collection<ServerZoneDTO> serverZonesDTO, MonitorEnvironment env, ServerZonesCalculator calculator) {
		Collection<MonitorMeasure> processingM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_PROCESSING);

		Collection<MonitorMeasure> requestsM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_REQUESTS);
		Collection<MonitorMeasure> requestsRateM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_REQUESTS_RATE);

		Collection<MonitorMeasure> responses1xxM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_1XX);
		Collection<MonitorMeasure> responses1xxRateM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_1XX_RATE);

		Collection<MonitorMeasure> responses2xxM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_2XX);
		Collection<MonitorMeasure> responses2xxRateM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_2XX_RATE);

		Collection<MonitorMeasure> responses3xxM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_3XX);
		Collection<MonitorMeasure> responses3xxRateM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_3XX_RATE);

		Collection<MonitorMeasure> responses4xxM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_4XX);
		Collection<MonitorMeasure> responses4xxRateM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_4XX_RATE);

		Collection<MonitorMeasure> responses5xxM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_5XX);
		Collection<MonitorMeasure> responses5xxRateM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_5XX_RATE);

		Collection<MonitorMeasure> responsesM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_RESPONSES);
		Collection<MonitorMeasure> responsesRateM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_RESPONSES_RATE);

		Collection<MonitorMeasure> discardedM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_DISCARDED);
		Collection<MonitorMeasure> discardedRateM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_DISCARDED_RATE);

		Collection<MonitorMeasure> receivedM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_RECEIVED);
		Collection<MonitorMeasure> receivedRateM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_RECEIVED_RATE);

		Collection<MonitorMeasure> sentM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_SENT);
		Collection<MonitorMeasure> sentRateM = env.getMonitorMeasures(ServerZonesParser.GROUP, MSR_SENT_RATE);

		for (ServerZoneDTO s : serverZonesDTO) {
			setDynamicMeasure(env, processingM, DYNAMIC_KEY, s.getServerZoneName(), s.getProcessing());
			setDynamicMeasure(env, requestsM, DYNAMIC_KEY, s.getServerZoneName(), s.getRequests());
			setDynamicMeasure(env, requestsRateM, DYNAMIC_KEY, s.getServerZoneName(), calculator.getRequestsRate().get(s.getServerZoneName()));
			setDynamicMeasure(env, responsesM, DYNAMIC_KEY, s.getServerZoneName(), s.getTotalResponses());
			setDynamicMeasure(env, responsesRateM, DYNAMIC_KEY, s.getServerZoneName(), calculator.getResponsesRate().get(s.getServerZoneName()));
			setDynamicMeasure(env, responses1xxM, DYNAMIC_KEY, s.getServerZoneName(), s.getResponses1xx());
			setDynamicMeasure(env, responses1xxRateM, DYNAMIC_KEY, s.getServerZoneName(), calculator.getResponses1xxRate().get(s.getServerZoneName()));
			setDynamicMeasure(env, responses2xxM, DYNAMIC_KEY, s.getServerZoneName(), s.getResponses2xx());
			setDynamicMeasure(env, responses2xxRateM, DYNAMIC_KEY, s.getServerZoneName(), calculator.getResponses2xxRate().get(s.getServerZoneName()));
			setDynamicMeasure(env, responses3xxM, DYNAMIC_KEY, s.getServerZoneName(), s.getResponses3xx());
			setDynamicMeasure(env, responses3xxRateM, DYNAMIC_KEY, s.getServerZoneName(), calculator.getResponses3xxRate().get(s.getServerZoneName()));
			setDynamicMeasure(env, responses4xxM, DYNAMIC_KEY, s.getServerZoneName(), s.getResponses4xx());
			setDynamicMeasure(env, responses4xxRateM, DYNAMIC_KEY, s.getServerZoneName(), calculator.getResponses4xxRate().get(s.getServerZoneName()));
			setDynamicMeasure(env, responses5xxM, DYNAMIC_KEY, s.getServerZoneName(), s.getResponses5xx());
			setDynamicMeasure(env, responses5xxRateM, DYNAMIC_KEY, s.getServerZoneName(), calculator.getResponses5xxRate().get(s.getServerZoneName()));
			setDynamicMeasure(env, discardedM, DYNAMIC_KEY, s.getServerZoneName(), s.getDiscarded());
			setDynamicMeasure(env, discardedRateM, DYNAMIC_KEY, s.getServerZoneName(), calculator.getDiscardedRate().get(s.getServerZoneName()));
			setDynamicMeasure(env, receivedM, DYNAMIC_KEY, s.getServerZoneName(), s.getReceived());
			setDynamicMeasure(env, receivedRateM, DYNAMIC_KEY, s.getServerZoneName(), calculator.getTrafficRecvRate().get(s.getServerZoneName()));
			setDynamicMeasure(env, sentM, DYNAMIC_KEY, s.getServerZoneName(), s.getSent());
			setDynamicMeasure(env, sentRateM, DYNAMIC_KEY, s.getServerZoneName(), calculator.getTrafficSentRate().get(s.getServerZoneName()));
		}
	}
}
