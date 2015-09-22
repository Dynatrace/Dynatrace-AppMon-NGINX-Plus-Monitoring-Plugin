package com.dynatrace.plugin.nginx.test;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.dynatrace.plugin.nginx.NginxPlusMonitoringConnection;
import com.dynatrace.plugin.nginx.dto.CacheDTO;
import com.dynatrace.plugin.nginx.dto.ConnectionsDTO;
import com.dynatrace.plugin.nginx.dto.MetaDTO;
import com.dynatrace.plugin.nginx.dto.RequestsDTO;
import com.dynatrace.plugin.nginx.dto.SSLDTO;
import com.dynatrace.plugin.nginx.dto.serverzone.ServerZoneDTO;
import com.dynatrace.plugin.nginx.dto.stream.Stream;
import com.dynatrace.plugin.nginx.dto.stream.StreamServerDTO;
import com.dynatrace.plugin.nginx.dto.stream.StreamServerZoneDTO;
import com.dynatrace.plugin.nginx.dto.upstreams.ServerDTO;
import com.dynatrace.plugin.nginx.dto.upstreams.ServerGroups;
import com.dynatrace.plugin.nginx.parsers.ParserCollection;
import com.dynatrace.plugin.nginx.parsers.ParserFactory;

public class ParsersAcceptanceTest {

	private ParserCollection parserCollection;
	private JSONObject statusJSON;

	@Before
	public void setUp() throws Exception {
		String protocol = "http";
		String host = "demo.nginx.com";
		String file = "/status";
		NginxPlusMonitoringConnection connection_ = new NginxPlusMonitoringConnection(protocol, host, 80, file);
		statusJSON = connection_.getStatusJson();
		parserCollection = new ParserFactory().getParserCollection(statusJSON.getInt("version"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void TestMetaParser() throws Exception {
		MetaDTO metaDTO = (MetaDTO) parserCollection.getMetaParser().parse(statusJSON);
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
		RequestsDTO requestsDTO = (RequestsDTO) parserCollection.getRequestsParser().parse(statusJSON);
		assertNotNull(requestsDTO.getCurrent());
		assertNotNull(requestsDTO.getTotal());
	}

	@Test
	public void TestSSLParser() throws Exception {
		SSLDTO sslDTO = (SSLDTO) parserCollection.getSslParser().parse(statusJSON);
		assertNotNull(sslDTO.getHandshakes());
		assertNotNull(sslDTO.getHandshakes_failed());
		assertNotNull(sslDTO.getSession_reuses());
	}

	@Test
	public void TestConnectionsParser() throws Exception {
		ConnectionsDTO connectionsDTO = (ConnectionsDTO) parserCollection.getConnectionsParser().parse(statusJSON);
		assertNotNull(connectionsDTO.getAccepted());
		assertNotNull(connectionsDTO.getActive());
		assertNotNull(connectionsDTO.getDropped());
		assertNotNull(connectionsDTO.getIdle());
	}

	@Test
	public void TestCachesParser() throws Exception {
		@SuppressWarnings("unchecked")
		Collection<CacheDTO> caches = (Collection<CacheDTO>) parserCollection.getCachesParser().parse(statusJSON);
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
		@SuppressWarnings("unchecked")
		Collection<ServerZoneDTO> serverZones = (Collection<ServerZoneDTO>) parserCollection.getServerZonesParser().parse(statusJSON);
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
		ServerGroups serverGroups = (ServerGroups) parserCollection.getUpstreamsParser().parse(statusJSON);
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
		Stream streamDTO = (Stream) parserCollection.getStreamParser().parse(statusJSON);
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
