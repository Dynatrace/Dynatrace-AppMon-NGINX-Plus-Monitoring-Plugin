package com.dynatrace.plugin.nginx.bookers;

import java.util.Collection;
import java.util.logging.Logger;

import com.dynatrace.diagnostics.pdk.MonitorEnvironment;
import com.dynatrace.diagnostics.pdk.MonitorMeasure;


public class Booker {

	private static final Logger log = Logger.getLogger(Booker.class.getName());

	public static void setDynamicMeasure(MonitorEnvironment env, Collection<MonitorMeasure> measure_collection, String dynamicKey, String dynamicValue, Double value) {
		for (MonitorMeasure monitorMeasure : measure_collection) {
			log.info("Creating dynamic measure for dynamicKey:" + dynamicKey + ", dynamicValue:" + dynamicValue + ", and setting value to:" + value + ", FOR:" + measure_collection.toString());
			MonitorMeasure dm = env.createDynamicMeasure(monitorMeasure, dynamicKey, dynamicValue);
			dm.setValue(value);
		}
	}
}
