/**
 * This template file was generated by dynaTrace client.
 * The dynaTrace community portal can be found here: http://community.compuwareapm.com/
 * For information how to publish a plugin please visit http://community.compuwareapm.com/plugins/contribute/
 **/

package com.dynatrace.plugin.nginx;

import com.dynatrace.diagnostics.pdk.MonitorEnvironment;
import com.dynatrace.diagnostics.pdk.Status;
import com.dynatrace.plugin.nginx.bookers.*;
import com.dynatrace.plugin.nginx.calculator.CalculatorImpl;
import com.dynatrace.plugin.nginx.calculator.TimeFrameCalculator;
import com.dynatrace.plugin.nginx.dto.NginxStatus;
import com.dynatrace.plugin.nginx.utils.Storage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Logger;

public class NginxPlusMonitor implements com.dynatrace.diagnostics.pdk.Monitor {

	private final Storage<NginxStatus> nginxStatusStorage = new Storage<>();

	private final static Logger log = Logger.getLogger(NginxPlusMonitor.class.getName());

	@Override
	public Status setup(MonitorEnvironment env) throws Exception {
		return new Status();
	}

	@Override
	public Status execute(MonitorEnvironment env) throws Exception {
		String host = env.getHost().getAddress();
		String protocol = env.getConfigString("ConnectionProtocol");
		String port = env.getConfigString("HostPort");
		String statusdataendpoint = env.getConfigString("StatusDataEndpoint");
		log.info("Executing Nginx Plus Monitor for host: " + host + " and statusdataendpoint: " + statusdataendpoint);
		try {
			NginxPlusMonitoringConnection connection;
			JSONObject jsonObject;
			try {
				connection = new NginxPlusMonitoringConnection(protocol, host, port, statusdataendpoint);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				Status status = new Status();
				status.setStatusCode(Status.StatusCode.ErrorInternal);
				status.setShortMessage("URL creation failed");
				status.setMessage("MalformedURLException");
				status.setException(e);
				log.severe("URL creation failed");
				return status;
			} catch (IOException e) {
				e.printStackTrace();
				Status status = new Status();
				status.setStatusCode(Status.StatusCode.ErrorTargetService);
				status.setShortMessage("Url connection failed");
				status.setMessage("IOException");
				status.setException(e);
				log.severe("URL connection failed");
				return status;
			}

			try {
				jsonObject = connection.getStatusJson();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				Status status = new Status();
				status.setStatusCode(Status.StatusCode.ErrorTargetService);
				status.setShortMessage("Invalid Content-type");
				status.setMessage("IllegalArgumentException");
				status.setException(e);
				log.severe("Invalid Content-type");
				return status;
			} catch (IOException e) {
				e.printStackTrace();
				Status status = new Status();
				status.setStatusCode(Status.StatusCode.ErrorTargetService);
				status.setShortMessage("Create input stream failed");
				status.setMessage("IOException");
				status.setException(e);
				log.severe("Create input stream failed");
				return status;
			} catch (JSONException e) {
				e.printStackTrace();
				Status status = new Status();
				status.setStatusCode(Status.StatusCode.ErrorInternal);
				status.setShortMessage("JSONObject on data failed");
				status.setMessage("JSONException");
				status.setException(e);
				log.severe("JSONObject on data failed");
				return status;
			}

			NginxStatus nginxStatusDTO;

			try {
				nginxStatusDTO = new NginxStatus(jsonObject);
			} catch (JSONException e) {
				e.printStackTrace();
				Status status = new Status();
				status.setStatusCode(Status.StatusCode.ErrorInternalException);
				status.setShortMessage("Booker JSON exception");
				status.setMessage("JSONException");
				status.setException(e);
				log.severe("Booker JSON exception");
				return status;
			}

			// This is a temporary fix to scheduler logic
			try {
				if (!nginxStatusStorage.isEmpty()) {
					TimeFrameCalculator timeFrameCalculator = new TimeFrameCalculator();
					if (timeFrameCalculator.calculateTimeFrame(nginxStatusStorage.get(), nginxStatusDTO) < 1.0) {
						nginxStatusStorage.put(nginxStatusDTO);
						return new Status();
					}
				}
			} catch (Exception e) {
				log.severe(e.toString());
			}

			CalculatorImpl calculator = new CalculatorImpl();
			if (nginxStatusStorage.isEmpty()) {
				calculator.calculate(nginxStatusDTO, nginxStatusDTO);
				nginxStatusStorage.put(nginxStatusDTO);
			} else {
				NginxStatus nginxStatusDTOPrev = nginxStatusStorage.get();
				calculator.calculate(nginxStatusDTOPrev, nginxStatusDTO);
				nginxStatusStorage.put(nginxStatusDTO);
			}

			ConnectionsBooker.book(nginxStatusDTO.getConnections(), env, calculator);
			SSLBooker.book(nginxStatusDTO.getSSL(), env, calculator);
			RequestsBooker.book(nginxStatusDTO.getRequests(), env, calculator);
			ServerZonesBooker.book(nginxStatusDTO.getServerZones(), env, calculator.getServerZonesCalculator());
			UpstreamsBooker.book(nginxStatusDTO.getUpstreams(), env, calculator.getUpstreamsCalculator());
			CachesBooker.book(nginxStatusDTO.getCaches(), env, calculator.getCachesCalculator());
			StreamBooker.book(nginxStatusDTO.getStream(), env, calculator.getStreamCalculator());
		} catch (Throwable e) {
			log.severe("Had throwable while running Nginx Plus Monitor: " + ExceptionUtils.getStackTrace(e));
			throw new Exception(e);
		}
		return new Status();
	}

	@Override
	public void teardown(MonitorEnvironment env) throws Exception {
	}
}
