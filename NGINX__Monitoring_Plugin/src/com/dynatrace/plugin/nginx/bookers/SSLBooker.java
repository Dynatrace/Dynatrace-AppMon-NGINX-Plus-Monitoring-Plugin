package com.dynatrace.plugin.nginx.bookers;

import java.util.Collection;
import java.util.logging.Logger;

import com.dynatrace.diagnostics.pdk.MonitorEnvironment;
import com.dynatrace.diagnostics.pdk.MonitorMeasure;
import com.dynatrace.plugin.nginx.calculator.CalculatorImpl;
import com.dynatrace.plugin.nginx.dto.SSLDTO;
import com.dynatrace.plugin.nginx.parsers.SSLParser;

public class SSLBooker {

	public static final String MSR_HANDSHAKES = "Handshakes";
	public static final String MSR_HANDSHAKES_FAILED = "Handshakes failed";
	public static final String MSR_SESSION_REUSES = "Session reuses";

	public static void book(SSLDTO sslDTO, MonitorEnvironment env, CalculatorImpl calculator) {
		Collection<MonitorMeasure> handshakesM = env.getMonitorMeasures(SSLParser.GROUP, MSR_HANDSHAKES);
		Collection<MonitorMeasure> handshakesFailedM = env.getMonitorMeasures(SSLParser.GROUP, MSR_HANDSHAKES_FAILED);
		Collection<MonitorMeasure> sessionReusesM = env.getMonitorMeasures(SSLParser.GROUP, MSR_SESSION_REUSES);

		Logger log = Logger.getLogger(SSLBooker.class.getName());

		log.info("[KD] RAZ");
		for (MonitorMeasure m : handshakesM) {
			log.info("[KD] DWA");
			m.setValue(sslDTO.getHandshakes());
		}
		log.info("[KD] RAZ");
		for (MonitorMeasure m : handshakesFailedM) {
			log.info("[KD] DWA");
			m.setValue(sslDTO.getHandshakes_failed());
		}
		log.info("[KD] RAZ");
		for (MonitorMeasure m : sessionReusesM) {
			log.info("[KD] DWA");
			m.setValue(sslDTO.getSession_reuses());
		}
	}
}
