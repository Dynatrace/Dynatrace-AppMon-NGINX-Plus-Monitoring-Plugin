package com.dynatrace.plugin.nginx.bookers;

import java.util.Collection;

import com.dynatrace.diagnostics.pdk.MonitorEnvironment;
import com.dynatrace.diagnostics.pdk.MonitorMeasure;
import com.dynatrace.plugin.nginx.calculator.CalculatorImpl;
import com.dynatrace.plugin.nginx.dto.ConnectionsDTO;
import com.dynatrace.plugin.nginx.parsers.ConnectionsParser;


public class ConnectionsBooker {
	public static final String MSR_ACCEPTED = "Accepted";
	public static final String MSR_DROPPED = "Dropped";
	public static final String MSR_ACTIVE = "Active";
	public static final String MSR_IDLE = "Idle";



	public static void book(ConnectionsDTO connectionsDTO, MonitorEnvironment env, CalculatorImpl calculator) {

		Collection<MonitorMeasure> acceptedM = env.getMonitorMeasures(ConnectionsParser.GROUP, MSR_ACCEPTED);
		Collection<MonitorMeasure> droppedM = env.getMonitorMeasures(ConnectionsParser.GROUP, MSR_DROPPED);
		Collection<MonitorMeasure> activeM = env.getMonitorMeasures(ConnectionsParser.GROUP, MSR_ACTIVE);
		Collection<MonitorMeasure> idleM = env.getMonitorMeasures(ConnectionsParser.GROUP, MSR_IDLE);

		for (MonitorMeasure m : acceptedM) {
			m.setValue(calculator.getConnectionsCalculator().getAccepted());
		}

		for (MonitorMeasure m : droppedM) {
			m.setValue(calculator.getConnectionsCalculator().getDropped());
		}

		for (MonitorMeasure m : activeM) {
			m.setValue(connectionsDTO.getActive());
		}

		for (MonitorMeasure m : idleM) {
			m.setValue(connectionsDTO.getIdle());
		}
	}
}
