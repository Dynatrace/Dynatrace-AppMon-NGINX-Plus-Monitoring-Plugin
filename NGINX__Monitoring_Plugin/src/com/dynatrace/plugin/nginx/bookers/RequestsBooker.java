package com.dynatrace.plugin.nginx.bookers;

import java.util.Collection;

import com.dynatrace.diagnostics.pdk.MonitorEnvironment;
import com.dynatrace.diagnostics.pdk.MonitorMeasure;
import com.dynatrace.plugin.nginx.calculator.CalculatorImpl;
import com.dynatrace.plugin.nginx.dto.RequestsDTO;
import com.dynatrace.plugin.nginx.parsers.RequestsParser;

public class RequestsBooker {
	public static final String MSR_TOTAL = "Total";
	public static final String MSR_CURRENT = "Current";
	public static final String MSR_REQUESTS = "Requests";

	public static void book(RequestsDTO requestsDTO, MonitorEnvironment env, CalculatorImpl calculator) {
        Collection<MonitorMeasure> totalM = env.getMonitorMeasures(RequestsParser.GROUP, MSR_TOTAL);
        Collection<MonitorMeasure> currentM = env.getMonitorMeasures(RequestsParser.GROUP, MSR_CURRENT);
        Collection<MonitorMeasure> requestsM = env.getMonitorMeasures(RequestsParser.GROUP, MSR_REQUESTS);

		for (MonitorMeasure m : totalM) {
			m.setValue(requestsDTO.getTotal());
		}

		for (MonitorMeasure m : currentM) {
			m.setValue(requestsDTO.getCurrent());
		}

		for (MonitorMeasure m : requestsM) {
			m.setValue(calculator.getRequestsCalculator().getTotalRequests());
		}
	}
}
