package com.dynatrace.plugin.nginx.bookers;

import java.util.Collection;

import com.dynatrace.diagnostics.pdk.MonitorEnvironment;
import com.dynatrace.diagnostics.pdk.MonitorMeasure;


public class Booker {
	public static void setDynamicMeasure(MonitorEnvironment env, Collection<MonitorMeasure> measure_collection, String dynamicKey, String dynamicValue, Double value) {
		for (MonitorMeasure monitorMeasure : measure_collection) {
			MonitorMeasure dm = env.createDynamicMeasure(monitorMeasure, dynamicKey, dynamicValue);
			dm.setValue(value);
		}
	}
}
