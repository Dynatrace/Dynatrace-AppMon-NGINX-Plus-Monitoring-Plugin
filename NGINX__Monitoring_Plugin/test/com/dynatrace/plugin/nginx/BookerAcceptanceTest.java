package com.dynatrace.plugin.nginx;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.ArrayList;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.dynatrace.plugin.nginx.NginxPlusMonitor;
import com.dynatrace.plugin.nginx.NginxPlusMonitoringConnection;
import com.dynatrace.plugin.nginx.bookers.CachesBooker;
import com.dynatrace.plugin.nginx.bookers.ConnectionsBooker;
import com.dynatrace.plugin.nginx.bookers.RequestsBooker;
import com.dynatrace.plugin.nginx.bookers.ServerZonesBooker;
import com.dynatrace.plugin.nginx.bookers.StreamBooker;
import com.dynatrace.plugin.nginx.bookers.UpstreamsBooker;
import com.dynatrace.plugin.nginx.calculator.CalculatorImpl;
import com.dynatrace.plugin.nginx.dto.NginxStatus;
import com.dynatrace.plugin.nginx.parsers.CachesParser;
import com.dynatrace.plugin.nginx.parsers.ConnectionsParser;
import com.dynatrace.plugin.nginx.parsers.RequestsParser;
import com.dynatrace.plugin.nginx.parsers.ServerZonesParser;
import com.dynatrace.plugin.nginx.parsers.StreamParser;
import com.dynatrace.plugin.nginx.parsers.UpstreamsParser;
import com.dynatrace.diagnostics.global.MonitorMeasureConfig;
import com.dynatrace.diagnostics.global.PluginInstanceConfig;
import com.dynatrace.diagnostics.global.PluginTypeConfig;
import com.dynatrace.diagnostics.pdk.PluginEnvironment;
import com.dynatrace.diagnostics.pdk.Status;
import com.dynatrace.diagnostics.sdk.MonitorEnvironment30Impl;
import com.dynatrace.diagnostics.sdk.MonitorMeasure30Impl;
import com.dynatrace.diagnostics.sdk.scheduling.DummyTaskFeedbackInterface;



public class BookerAcceptanceTest {

  private void createMeasure(MonitorEnvironment30Impl env, String metric_group, String measure_name) {
	  List<MonitorMeasure30Impl> measures = new ArrayList<MonitorMeasure30Impl>();
	  MonitorMeasure30Impl measure = new MonitorMeasure30Impl();
	  measures.add(measure);
	  measure.setMetricGroupName(metric_group);
	  measure.setMetricName(measure_name);
	  measure.setMeasureName(measure_name);
	  MonitorMeasureConfig measure_config = new MonitorMeasureConfig();
	  List<MonitorMeasureConfig.ConfigurationItem> configurationItems = new ArrayList<MonitorMeasureConfig.ConfigurationItem>();
	  measure_config.setConfigurationItems(configurationItems);
	  measure.setConfig(measure_config);
	  env.setMeasures(measures);
	}

	private MonitorEnvironment30Impl createEnvironment()
	{
		final String host = "demo.nginx.com";
		final String COM_DYNATRACE_DIAGNOSTICS_PDK_PLUGINCONFIG = "com.dynatrace.diagnostics.pdk.pluginconfig";

		PluginInstanceConfig pluginInstanceConfig = new PluginInstanceConfig();
		pluginInstanceConfig.setKey(COM_DYNATRACE_DIAGNOSTICS_PDK_PLUGINCONFIG);
		PluginTypeConfig pluginTypeConfig = new PluginTypeConfig();
		pluginTypeConfig.setKey(COM_DYNATRACE_DIAGNOSTICS_PDK_PLUGINCONFIG);
		MonitorEnvironment30Impl env = new MonitorEnvironment30Impl(null, pluginInstanceConfig, pluginTypeConfig, false, new DummyTaskFeedbackInterface());

		createMeasure(env, ConnectionsParser.GROUP, ConnectionsBooker.MSR_ACTIVE);
		createMeasure(env, ConnectionsParser.GROUP, ConnectionsBooker.MSR_IDLE);

		createMeasure(env, RequestsParser.GROUP, RequestsBooker.MSR_CURRENT);
		createMeasure(env, RequestsParser.GROUP, RequestsBooker.MSR_TOTAL);
		createMeasure(env, RequestsParser.GROUP, RequestsBooker.MSR_REQUESTS);

		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_SIZE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_MAX_SIZE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_COLD);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_HIT_RESPONSES);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_HIT_RESPONSES_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_HIT_BYTES);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_HIT_BYTES_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_STALE_RESPONSES);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_STALE_RESPONSES_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_STALE_BYTES);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_STALE_BYTES_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_UPDATING_RESPONSES);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_UPDATING_RESPONSES_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_UPDATING_BYTES);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_UPDATING_BYTES_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_REVALIDATED_RESPONSES);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_REVALIDATED_RESPONSES_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_REVALIDATED_BYTES);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_REVALIDATED_BYTES_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_MISS_RESPOSNES);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_MISS_RESPOSNES_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_MISS_BYTES);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_MISS_BYTES_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_MISS_RESPONSES_WRITTEN);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_MISS_RESPONSES_WRITTEN_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_MISS_BYTES_WRITTEN);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_MISS_BYTES_WRITTEN_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_EXPIRED_RESPOSNES);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_EXPIRED_RESPOSNES_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_EXPIRED_BYTES);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_EXPIRED_BYTES_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_EXPIRED_RESPONSES_WRITTEN);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_EXPIRED_RESPONSES_WRITTEN_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_EXPIRED_BYTES_WRITTEN);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_EXPIRED_BYTES_WRITTEN_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_BYPASS_RESPONSES);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_BYPASS_RESPONSES_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_BYPASS_BYTES);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_BYPASS_BYTES_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_BYPASS_RESPONSES_WRITTEN);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_BYPASS_RESPONSES_WRITTEN_RATE);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_BYPASS_BYTES_WRITTEN);
		createMeasure(env, CachesParser.GROUP, CachesBooker.MSR_BYPASS_BYTES_WRITTEN_RATE);


		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_BACKUP);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_WEIGHT);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_STATE);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_ACTIVE);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_REQUESTS);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_REQUESTS_RATE);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_1XX);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_1XX_RATE);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_2XX);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_2XX_RATE);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_3XX);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_3XX_RATE);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_4XX);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_4XX_RATE);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_5XX);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_5XX_RATE);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_RESPONSES);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_RESPONSES_RATE);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_RECEIVED);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_RECEIVED_RATE);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_SENT);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_SENT_RATE);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_FAILS);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_UNAVAILABLE);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_TOTAL_HEALTH_CHECKS);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_FAILED_HEALTH_CHECKS);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_UNHEALTHY_HEALTH_CHECKS);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_LAST_HEALTH_CHECK_PASSED);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_DOWNSTART);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_DOWNTIME);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_STATE_UP);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_STATE_DRAINING);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_STATE_DOWN);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_STATE_UNAVAIL);
		createMeasure(env, UpstreamsParser.GROUP, UpstreamsBooker.MSR_STATE_UNHEALTHY);


		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_PROCESSING);
		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_REQUESTS);
		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_REQUESTS_RATE);
		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_1XX);
		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_1XX_RATE);
		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_2XX);
		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_2XX_RATE);
		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_3XX);
		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_3XX_RATE);
		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_4XX);
		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_4XX_RATE);
		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_5XX);
		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_5XX_RATE);
		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_RECEIVED);
		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_RECEIVED_RATE);
		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_RESPONSES);
		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_RESPONSES_RATE);
		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_SENT);
		createMeasure(env, ServerZonesParser.GROUP, ServerZonesBooker.MSR_SENT_RATE);

		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_SZ_PROCESSING);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_SZ_CONNECTIONS);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_SZ_CONNECTIONS_RATE);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_SZ_RECEIVED);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_SZ_RECEIVED_RATE);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_SZ_SENT);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_SZ_SEND_RATE);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_BACKUP);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_WEIGHT);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_STATE);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_ACTIVE);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_TOTAL_ACTIVE);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_MAXCONNS);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_CONNECTIONS);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_CONNECTIONS_RATE);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_CONNECT_TIME);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_FIRST_BYTE_TIME);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_RESPONSE_TIME);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_SENT);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_SENT_RATE);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_RECEIVED);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_RECEIVED_RATE);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_FAILS);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_FAILS_RATE);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_UNAVAILABLE);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_TOTAL_HEALTH_CHECKS);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_FAILED_HEALTH_CHECKS);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_UNHEALTHY_HEALTH_CHECKS);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_LAST_HEALTH_CHECK_PASSED);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_DOWNSTART);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_DOWNTIME);
		createMeasure(env, StreamParser.GROUP, StreamBooker.MSR_US_SELECTED);


		env.setHost(new PluginEnvironment.Host() {
			@Override
			public String getAddress() {
				return host;
			}
		});

		return env;
	}


	private MonitorEnvironment30Impl env;
	private JSONObject jsonObject;
	private CalculatorImpl calculator;
	private NginxStatus status;


	@Before
	public void setUp() throws Exception {
		env = createEnvironment();
		calculator = new CalculatorImpl();
		String protocol = "http";
		String host = "demo.nginx.com";
		String file = "/status";
		NginxPlusMonitoringConnection connection = new NginxPlusMonitoringConnection(protocol, host, file);
		jsonObject = connection.getStatusJson();
		status = new NginxStatus(jsonObject);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void TestConnectionBooker() throws Exception {
		calculator.getConnectionsCalculator().calculate(status, status);
		ConnectionsBooker.book(status.getConnections(), env, calculator);
	}

	@Test
	public void TestRequestBooker() throws Exception {
		calculator.getRequestsCalculator().calculate(status, status);
		RequestsBooker.book(status.getRequests(), env, calculator);
	}

	@Test
	public void TestCachesBooker() throws Exception {
		calculator.getCachesCalculator().calculate(status, status);
		CachesBooker.book(status.getCaches(), env, calculator.getCachesCalculator());
	}

	@Test
	public void TestUpstreamBooker() throws Exception {
		calculator.getUpstreamsCalculator().calculate(status, status);
		UpstreamsBooker.book(status.getUpstreams(), env, calculator.getUpstreamsCalculator());
	}

	@Test
	public void TestServerZoneBooker() throws Exception {
		calculator.getServerZonesCalculator().calculate(status, status);
		ServerZonesBooker.book(status.getServerZones(), env, calculator.getServerZonesCalculator());
	}

	@Test
	public void TestStreamBooker() throws Exception {
		calculator.getStreamCalculator().calculate(status, status);
		StreamBooker.book(status.getStream(), env, calculator.getStreamCalculator());
	}

	@Test
	public void TestNginxPlusMonitor() throws Exception {
		NginxPlusMonitor monitor = new NginxPlusMonitor();
		assertEquals(Status.StatusCode.Success, monitor.setup(env).getStatusCode());
		assertEquals(Status.StatusCode.Success, monitor.execute(env).getStatusCode());
		monitor.teardown(env);
	}
}
