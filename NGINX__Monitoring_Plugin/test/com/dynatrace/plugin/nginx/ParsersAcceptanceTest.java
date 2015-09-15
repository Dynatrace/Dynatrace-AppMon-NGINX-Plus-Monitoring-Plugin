package com.dynatrace.plugin.nginx;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.dynatrace.plugin.nginx.dto.*;
import com.dynatrace.plugin.nginx.parsers.CachesParser;
import com.dynatrace.plugin.nginx.parsers.ConnectionsParser;
import com.dynatrace.plugin.nginx.parsers.MetaParser;
import com.dynatrace.plugin.nginx.parsers.RequestsParser;
import com.dynatrace.plugin.nginx.parsers.SSLParser;
import com.dynatrace.plugin.nginx.parsers.ServerZonesParser;
import com.dynatrace.plugin.nginx.parsers.StreamParser;
import com.dynatrace.plugin.nginx.parsers.UpstreamsParser;

public class ParsersAcceptanceTest {
	private NginxPlusMonitoringConnection connection_;

	@Before
	public void setUp() throws Exception {
		String protocol = "http";
		String host = "demo.nginx.com";
		String file = "/status";
		connection_ = new NginxPlusMonitoringConnection(protocol, host, file);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void TestMetaParser() throws Exception {
		JSONObject jsonObject = connection_.getStatusJson();

		MetaDTO metaDTO = MetaParser.parse(jsonObject);
		assertNotNull(metaDTO.getVersion());
		assertNotNull(metaDTO.getNginx_version());
		assertNotNull(metaDTO.getAddress());
		assertNotNull(metaDTO.getGeneration());
		assertNotNull(metaDTO.getLoad_timestamp());
		assertNotNull(metaDTO.getTimestamp());
		assertNotNull(metaDTO.getPid());
	}

	@Test
	public void TestRequestsParser() throws Exception {
		JSONObject jsonObject = connection_.getStatusJson();
		RequestsDTO requestsDTO = RequestsParser.parse(jsonObject);
		assertNotNull(requestsDTO.getCurrent());
		assertNotNull(requestsDTO.getTotal());
	}

	@Test
	public void TestSSLParser() throws Exception {
		JSONObject jsonObject = connection_.getStatusJson();
		SSLDTO sslDTO = SSLParser.parse(jsonObject);
		assertNotNull(sslDTO.getHandshakes());
		assertNotNull(sslDTO.getHandshakes_failed());
		assertNotNull(sslDTO.getSession_reuses());
	}

	@Test
	public void TestConnectionsParser() throws Exception {
		JSONObject jsonObject = connection_.getStatusJson();
		ConnectionsDTO connectionsParser = ConnectionsParser.parse(jsonObject);
		assertNotNull(connectionsParser.getAccepted());
		assertNotNull(connectionsParser.getActive());
		assertNotNull(connectionsParser.getDropped());
		assertNotNull(connectionsParser.getIdle());
	}

	@Test
	public void TestCachesParser() throws Exception {
		JSONObject jsonObject = connection_.getStatusJson();
		Collection<CacheDTO> caches = CachesParser.parse(jsonObject);
		for (CacheDTO c : caches) {
			assertNotNull(c.getCacheName());
			assertNotNull(c.getSize());
			assertNotNull(c.getMaxSize());
			assertNotNull(c.getCold());
			assertNotNull(c.getHitResponses());
			assertNotNull(c.getHitBytes());
			assertNotNull(c.getStaleResponses());
			assertNotNull(c.getStaleBytes());
			assertNotNull(c.getUpdatingResponses());
			assertNotNull(c.getUpdatingBytes());
			assertNotNull(c.getRevalidatedResponses());
			assertNotNull(c.getRevalidatedBytes());
			assertNotNull(c.getMissResponses());
			assertNotNull(c.getMissBytes());
			assertNotNull(c.getMissResponsesWritten());
			assertNotNull(c.getMissBytesWritten());
			assertNotNull(c.getExpiredResponses());
			assertNotNull(c.getExpiredBytes());
			assertNotNull(c.getExpiredResponsesWritten());
			assertNotNull(c.getExpiredBytesWritten());
			assertNotNull(c.getBypassResponses());
			assertNotNull(c.getBypassBytes());
			assertNotNull(c.getBypassResponsesWritten());
			assertNotNull(c.getBypassBytesWritten());
		}
	}

	@Test
	public void TestServerZonesParser() throws Exception {
		JSONObject jsonObject = connection_.getStatusJson();
		Collection<ServerZoneDTO> serverZones = ServerZonesParser.parse(jsonObject);
		for (ServerZoneDTO s : serverZones) {
			assertNotNull(s.getProcessing());
			assertNotNull(s.getRequests());
			assertNotNull(s.getResponses1xx());
			assertNotNull(s.getResponses2xx());
			assertNotNull(s.getResponses3xx());
			assertNotNull(s.getResponses4xx());
			assertNotNull(s.getResponses5xx());
			assertNotNull(s.getTotalResponses());
			assertNotNull(s.getDiscarded());
			assertNotNull(s.getReceived());
			assertNotNull(s.getSent());
		}
	}

	@Test
	public void TestUpstreamsParser() throws Exception {
		JSONObject jsonObject = connection_.getStatusJson();
		ServerGroups serverGroups = UpstreamsParser.parse(jsonObject);
		for (String serverGroupName: serverGroups.get().keySet()) {
			for (ServerDTO s : serverGroups.get().get(serverGroupName)) {
				assertNotNull(s.getId());
				assertNotNull(s.getServer());
				assertNotNull(s.getBackup());
				assertNotNull(s.getWeight());
				assertNotNull(s.getState());
				assertNotNull(s.getActive());
				assertNotNull(s.getRequests());
				assertNotNull(s.getResponses1xx());
				assertNotNull(s.getResponses2xx());
				assertNotNull(s.getResponses3xx());
				assertNotNull(s.getResponses4xx());
				assertNotNull(s.getResponses5xx());
				assertNotNull(s.getTotalResponses());
				assertNotNull(s.getSent());
				assertNotNull(s.getReceived());
				assertNotNull(s.getFails());
				assertNotNull(s.getUnavail());
				assertNotNull(s.getHealthChecksTotal());
				assertNotNull(s.getHealthChecksFails());
				assertNotNull(s.getHealthChecksUnhealthy());
				//assertNotNull(s.getHealthChecksLastPassed()); This field is optional.
				assertNotNull(s.getDowntime());
				assertNotNull(s.getDownstart());
				assertNotNull(s.getSelected());
			}
		}
	}

	@Test
	public void TestStreamParser() throws Exception {
		JSONObject jsonObject = connection_.getStatusJson();
		Stream streamDTO = StreamParser.parse(jsonObject);
		for (StreamServerZoneDTO s : streamDTO.getServerZones()) {
			assertNotNull(s.getServerZoneName());
			assertNotNull(s.getProcessing());
			assertNotNull(s.getConnections());
			assertNotNull(s.getReceived());
			assertNotNull(s.getSent());
		}
		for (String streamServerGroupName : streamDTO.getUpstreams().get().keySet()) {
			for (StreamServerDTO s : streamDTO.getUpstreams().get().get(streamServerGroupName)) {
				assertNotNull(s.getId());
				assertNotNull(s.getServer());
				assertNotNull(s.getBackup());
				assertNotNull(s.getWeight());
				assertNotNull(s.getState());
				assertNotNull(s.getActive());
				//assertNotNull(s.getMaxConns()); This field is optional.
				//assertNotNull(s.getConnections()); This field is optional.
				//assertNotNull(s.getConnectTime());
				//assertNotNull(s.getFirstByteTime());
				//assertNotNull(s.getResponseTime());
				assertNotNull(s.getSent());
				assertNotNull(s.getReceived());
				assertNotNull(s.getFails());
				assertNotNull(s.getUnavail());
				assertNotNull(s.getHealthChecksTotal());
				assertNotNull(s.getHealthChecksFails());
				assertNotNull(s.getHealthChecksUnhealthy());
				//assertNotNull(s.getHealthChecksLastPassed()); This field is optional.
				assertNotNull(s.getDowntime());
				assertNotNull(s.getDownstart());
				assertNotNull(s.getSelected());
			}
		}
	}
}
