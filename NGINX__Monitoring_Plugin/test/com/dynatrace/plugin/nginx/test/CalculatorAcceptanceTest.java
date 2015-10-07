package com.dynatrace.plugin.nginx.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.dynatrace.plugin.nginx.calculator.CachesCalculator;
import com.dynatrace.plugin.nginx.calculator.CalculatorImpl;
import com.dynatrace.plugin.nginx.calculator.ServerZonesCalculator;
import com.dynatrace.plugin.nginx.calculator.StreamCalculator;
import com.dynatrace.plugin.nginx.calculator.UpstreamsCalculator;
import com.dynatrace.plugin.nginx.dto.CacheDTO;
import com.dynatrace.plugin.nginx.dto.NginxStatus;
import com.dynatrace.plugin.nginx.dto.StateT;
import com.dynatrace.plugin.nginx.dto.serverzone.ServerZoneDTO;
import com.dynatrace.plugin.nginx.dto.stream.StreamServerDTO;
import com.dynatrace.plugin.nginx.dto.stream.StreamServerZoneDTO;
import com.dynatrace.plugin.nginx.dto.upstreams.ServerDTO;

public class CalculatorAcceptanceTest {
	CalculatorImpl calculator;
	NginxStatus request2;
	NginxStatus request1;

	private final double DELTA = 1e-15;

	@Before
	public void setUp() throws Exception {
		request2 = new NginxStatus();
		request1 = new NginxStatus();
		calculator = new CalculatorImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void TestCalculateConnectionsAccepted() {
		request2.getConnections().setAccepted(100.0);
		request1.getConnections().setAccepted(200.0);

		calculator.getConnectionsCalculator().calculateAccepted(request2, request1, 25.0);

		assertEquals(4.0, calculator.getConnectionsCalculator().getAccepted(), DELTA);
	}

	@Test
	public void TestCalculateConnectionsDropped() {
		request2.getConnections().setDropped(100.0);
		request1.getConnections().setDropped(200.0);

		calculator.getConnectionsCalculator().calculateDropped(request2, request1, 25.0);

		assertEquals(4.0, calculator.getConnectionsCalculator().getDropped(), DELTA);
	}

	@Test
	public void TestCalculateRequestsRate() {
		request2.getRequests().setTotal(100.0);
		request1.getRequests().setTotal(200.0);


		calculator.getRequestsCalculator().calculateTotalRequests(request2, request1, 25.0);

		assertEquals(4.0, calculator.getRequestsCalculator().getTotalRequests(), DELTA);
	}

	@Test
	public void TestCalculateUpstreams() {
		ServerDTO serv;
		ServerDTO serv2;

		String serv1S = "serv1";
		String serv2S = "serv2";

		String group1S = "group1";
		String group2S = "group2";

		serv = new ServerDTO();
		serv.setServer(serv1S);
		serv.setActive(5.0);
		serv.setState(new StateT("up"));
		serv.setSent(100.0);
		serv.setReceived(100.0);
		serv.setRequests(100.0);
		serv.setTotalResponses(100.0);
		serv.setResponses1xx(100.0);
		serv.setResponses2xx(100.0);
		serv.setResponses3xx(100.0);
		serv.setResponses4xx(100.0);
		serv.setResponses5xx(100.0);
		serv.setFails(100.0);
		serv.setUnavail(100.0);
		serv.setHealthChecksTotal(100.0);
		serv.setHealthChecksFails(100.0);
		serv.setHealthCheckUnhealthy(100.0);
		request1.getUpstreams().createNewServerGroup(group1S).add(serv);

		serv2 = new ServerDTO();
		serv2.setServer(serv2S);
		serv2.setActive(5.0);
		serv2.setState(new StateT("up"));
		serv2.setSent(100.0);
		serv2.setReceived(100.0);
		serv2.setRequests(100.0);
		serv2.setTotalResponses(100.0);
		serv2.setResponses1xx(100.0);
		serv2.setResponses2xx(100.0);
		serv2.setResponses3xx(100.0);
		serv2.setResponses4xx(100.0);
		serv2.setResponses5xx(100.0);
		serv2.setFails(100.0);
		serv2.setUnavail(100.0);
		serv2.setHealthChecksTotal(100.0);
		serv2.setHealthChecksFails(100.0);
		serv2.setHealthCheckUnhealthy(100.0);
		request1.getUpstreams().createNewServerGroup(group2S).add(serv2);


		serv = new ServerDTO();
		serv.setServer(serv1S);
		serv.setActive(5.0);
		serv.setState(new StateT("up"));
		serv.setSent(200.0);
		serv.setReceived(200.0);
		serv.setRequests(200.0);
		serv.setTotalResponses(200.0);
		serv.setResponses1xx(200.0);
		serv.setResponses2xx(200.0);
		serv.setResponses3xx(200.0);
		serv.setResponses4xx(200.0);
		serv.setResponses5xx(200.0);
		serv.setFails(200.0);
		serv.setUnavail(200.0);
		serv.setHealthChecksTotal(200.0);
		serv.setHealthChecksFails(200.0);
		serv.setHealthCheckUnhealthy(200.0);
		request2.getUpstreams().createNewServerGroup(group1S).add(serv);

		serv2 = new ServerDTO();
		serv2.setServer(serv2S);
		serv2.setActive(5.0);
		serv2.setState(new StateT("up"));
		serv2.setSent(400.0);
		serv2.setReceived(400.0);
		serv2.setRequests(400.0);
		serv2.setTotalResponses(400.0);
		serv2.setResponses1xx(400.0);
		serv2.setResponses2xx(400.0);
		serv2.setResponses3xx(400.0);
		serv2.setResponses4xx(400.0);
		serv2.setResponses5xx(400.0);
		serv2.setFails(400.0);
		serv2.setUnavail(400.0);
		serv2.setHealthChecksTotal(400.0);
		serv2.setHealthChecksFails(400.0);
		serv2.setHealthCheckUnhealthy(400.0);
		request2.getUpstreams().createNewServerGroup(group2S).add(serv2);

		UpstreamsCalculator upstreamsCalculator = calculator.getUpstreamsCalculator();
		upstreamsCalculator.calculateUpstreams(request1, request2, 25.0);

		assertEquals(4.0, upstreamsCalculator.getSentRate().get(group1S, serv1S), DELTA);
		assertEquals(12.0, upstreamsCalculator.getSentRate().get(group2S, serv2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getReceivedRate().get(group1S, serv1S), DELTA);
		assertEquals(12.0, upstreamsCalculator.getReceivedRate().get(group2S, serv2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getRequestsRate().get(group1S, serv1S), DELTA);
		assertEquals(12.0, upstreamsCalculator.getRequestsRate().get(group2S, serv2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getResponsesRate().get(group1S, serv1S), DELTA);
		assertEquals(12.0, upstreamsCalculator.getResponsesRate().get(group2S, serv2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getResponses1xxRate().get(group1S, serv1S), DELTA);
		assertEquals(12.0, upstreamsCalculator.getResponses1xxRate().get(group2S, serv2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getResponses2xxRate().get(group1S, serv1S), DELTA);
		assertEquals(12.0, upstreamsCalculator.getResponses2xxRate().get(group2S, serv2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getResponses3xxRate().get(group1S, serv1S), DELTA);
		assertEquals(12.0, upstreamsCalculator.getResponses3xxRate().get(group2S, serv2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getResponses4xxRate().get(group1S, serv1S), DELTA);
		assertEquals(12.0, upstreamsCalculator.getResponses4xxRate().get(group2S, serv2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getResponses5xxRate().get(group1S, serv1S), DELTA);
		assertEquals(12.0, upstreamsCalculator.getResponses5xxRate().get(group2S, serv2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getFailsRate().get(group1S, serv1S), DELTA);
		assertEquals(12.0, upstreamsCalculator.getFailsRate().get(group2S, serv2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getUnavailRate().get(group1S, serv1S), DELTA);
		assertEquals(12.0, upstreamsCalculator.getUnavailRate().get(group2S, serv2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getHealthChecksRate().get(group1S, serv1S), DELTA);
		assertEquals(12.0, upstreamsCalculator.getHealthChecksRate().get(group2S, serv2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getHealthChecksFailedRate().get(group1S, serv1S), DELTA);
		assertEquals(12.0, upstreamsCalculator.getHealthChecksFailedRate().get(group2S, serv2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getHealthChecksUnhealthyRate().get(group1S, serv1S), DELTA);
		assertEquals(12.0, upstreamsCalculator.getHealthChecksUnhealthyRate().get(group2S, serv2S), DELTA);

		assertEquals(10.0, upstreamsCalculator.getTotalActive(), DELTA);

		assertEquals(2.0, upstreamsCalculator.getTotalStateUp(), DELTA);
		assertEquals(0.0, upstreamsCalculator.getTotalStateDown(), DELTA);
		assertEquals(0.0, upstreamsCalculator.getTotalStateDraining(), DELTA);
		assertEquals(0.0, upstreamsCalculator.getTotalStateUnavail(), DELTA);

		assertEquals(4.0, upstreamsCalculator.getRequestsRatePerUpstream().get(group1S), DELTA);
		assertEquals(200.0, upstreamsCalculator.getRequestsPerUpstream().get(group1S), DELTA);

		assertEquals(12.0, upstreamsCalculator.getRequestsRatePerUpstream().get(group2S), DELTA);
		assertEquals(400.0, upstreamsCalculator.getRequestsPerUpstream().get(group2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getResponsesRatePerUpstream().get(group1S), DELTA);
		assertEquals(200.0, upstreamsCalculator.getResponsesPerUpstream().get(group1S), DELTA);

		assertEquals(12.0, upstreamsCalculator.getResponsesRatePerUpstream().get(group2S), DELTA);
		assertEquals(400.0, upstreamsCalculator.getResponsesPerUpstream().get(group2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getResponses1xxRatePerUpstream().get(group1S), DELTA);
		assertEquals(200.0, upstreamsCalculator.getResponses1xxPerUpstream().get(group1S), DELTA);

		assertEquals(12.0, upstreamsCalculator.getResponses1xxRatePerUpstream().get(group2S), DELTA);
		assertEquals(400.0, upstreamsCalculator.getResponses1xxPerUpstream().get(group2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getResponses2xxRatePerUpstream().get(group1S), DELTA);
		assertEquals(200.0, upstreamsCalculator.getResponses2xxPerUpstream().get(group1S), DELTA);

		assertEquals(12.0, upstreamsCalculator.getResponses2xxRatePerUpstream().get(group2S), DELTA);
		assertEquals(400.0, upstreamsCalculator.getResponses2xxPerUpstream().get(group2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getResponses3xxRatePerUpstream().get(group1S), DELTA);
		assertEquals(200.0, upstreamsCalculator.getResponses3xxPerUpstream().get(group1S), DELTA);

		assertEquals(12.0, upstreamsCalculator.getResponses3xxRatePerUpstream().get(group2S), DELTA);
		assertEquals(400.0, upstreamsCalculator.getResponses3xxPerUpstream().get(group2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getResponses4xxRatePerUpstream().get(group1S), DELTA);
		assertEquals(200.0, upstreamsCalculator.getResponses4xxPerUpstream().get(group1S), DELTA);

		assertEquals(12.0, upstreamsCalculator.getResponses4xxRatePerUpstream().get(group2S), DELTA);
		assertEquals(400.0, upstreamsCalculator.getResponses4xxPerUpstream().get(group2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getResponses5xxRatePerUpstream().get(group1S), DELTA);
		assertEquals(200.0, upstreamsCalculator.getResponses5xxPerUpstream().get(group1S), DELTA);

		assertEquals(12.0, upstreamsCalculator.getResponses5xxRatePerUpstream().get(group2S), DELTA);
		assertEquals(400.0, upstreamsCalculator.getResponses5xxPerUpstream().get(group2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getSentRatePerUpstream().get(group1S), DELTA);
		assertEquals(200.0, upstreamsCalculator.getSentPerUpstream().get(group1S), DELTA);

		assertEquals(12.0, upstreamsCalculator.getSentRatePerUpstream().get(group2S), DELTA);
		assertEquals(400.0, upstreamsCalculator.getSentPerUpstream().get(group2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getReceivedRatePerUpstream().get(group1S), DELTA);
		assertEquals(200.0, upstreamsCalculator.getReceivedPerUpstream().get(group1S), DELTA);

		assertEquals(12.0, upstreamsCalculator.getReceivedRatePerUpstream().get(group2S), DELTA);
		assertEquals(400.0, upstreamsCalculator.getReceivedPerUpstream().get(group2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getFailsRatePerUpstream().get(group1S), DELTA);
		assertEquals(200.0, upstreamsCalculator.getFailsPerUpstream().get(group1S), DELTA);

		assertEquals(12.0, upstreamsCalculator.getFailsRatePerUpstream().get(group2S), DELTA);
		assertEquals(400.0, upstreamsCalculator.getFailsPerUpstream().get(group2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getUnavailRatePerUpstream().get(group1S), DELTA);
		assertEquals(200.0, upstreamsCalculator.getUnavailPerUpstream().get(group1S), DELTA);

		assertEquals(12.0, upstreamsCalculator.getUnavailRatePerUpstream().get(group2S), DELTA);
		assertEquals(400.0, upstreamsCalculator.getUnavailPerUpstream().get(group2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getHealthChecksTotalRatePerUpstream().get(group1S), DELTA);
		assertEquals(200.0, upstreamsCalculator.getHealthChecksTotalPerUpstream().get(group1S), DELTA);
		assertEquals(12.0, upstreamsCalculator.getHealthChecksTotalRatePerUpstream().get(group2S), DELTA);
		assertEquals(400.0, upstreamsCalculator.getHealthChecksTotalPerUpstream().get(group2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getHealthChecksFailedRatePerUpstream().get(group1S), DELTA);
		assertEquals(200.0, upstreamsCalculator.getHealthChecksFailedPerUpstream().get(group1S), DELTA);
		assertEquals(12.0, upstreamsCalculator.getHealthChecksFailedRatePerUpstream().get(group2S), DELTA);
		assertEquals(400.0, upstreamsCalculator.getHealthChecksFailedPerUpstream().get(group2S), DELTA);

		assertEquals(4.0, upstreamsCalculator.getHealthChecksUnhealthyRatePerUpstream().get(group1S), DELTA);
		assertEquals(200.0, upstreamsCalculator.getHealthChecksUnhealthyPerUpstream().get(group1S), DELTA);
		assertEquals(12.0, upstreamsCalculator.getHealthChecksUnhealthyRatePerUpstream().get(group2S), DELTA);
		assertEquals(400.0, upstreamsCalculator.getHealthChecksUnhealthyPerUpstream().get(group2S), DELTA);
	}

	@Test
	public void TestCalculateServerZones() {
		String serv1S = "serv1";
		String serv2S = "serv2";
		String serv3S = "serv3";

		for (String server : new String[] {serv1S, serv2S}) {
			ServerZoneDTO serv = new ServerZoneDTO(server);
			serv.setProcessing(100.0);
			serv.setRequests(100.0);
			serv.setDiscarded(100.0);
			serv.setSent(100.0);
			serv.setReceived(100.0);
			serv.setResponses1xx(100.0);
			serv.setResponses2xx(100.0);
			serv.setResponses3xx(100.0);
			serv.setResponses4xx(100.0);
			serv.setResponses5xx(100.0);
			serv.setTotalResponses(100.0);
			request1.getServerZones().add(serv);
		}

		for (String server : new String[] {serv1S, serv2S, serv3S}) {
			ServerZoneDTO serv = new ServerZoneDTO(server);
			serv.setProcessing(200.0);
			serv.setRequests(200.0);
			serv.setDiscarded(200.0);
			serv.setSent(200.0);
			serv.setReceived(200.0);
			serv.setResponses1xx(200.0);
			serv.setResponses2xx(200.0);
			serv.setResponses3xx(200.0);
			serv.setResponses4xx(200.0);
			serv.setResponses5xx(200.0);
			serv.setTotalResponses(200.0);
			request2.getServerZones().add(serv);
		}



		ServerZonesCalculator serverZonesCalculator = calculator.getServerZonesCalculator();
		serverZonesCalculator.calculateServerZones(request1, request2, 25.0);

		for (String server : new String[] {serv1S, serv2S}) {
			assertEquals(4.0, serverZonesCalculator.getRequestsRate().get(server), DELTA);
			assertEquals(4.0, serverZonesCalculator.getResponses1xxRate().get(server), DELTA);
			assertEquals(4.0, serverZonesCalculator.getResponses2xxRate().get(server), DELTA);
			assertEquals(4.0, serverZonesCalculator.getResponses3xxRate().get(server), DELTA);
			assertEquals(4.0, serverZonesCalculator.getResponses4xxRate().get(server), DELTA);
			assertEquals(4.0, serverZonesCalculator.getResponses5xxRate().get(server), DELTA);
			assertEquals(4.0, serverZonesCalculator.getDiscardedRate().get(server), DELTA);
			assertEquals(4.0, serverZonesCalculator.getTrafficSentRate().get(server), DELTA);
			assertEquals(4.0, serverZonesCalculator.getTrafficRecvRate().get(server), DELTA);
		}

		assertEquals(Double.NaN, serverZonesCalculator.getRequestsRate().get(serv3S), DELTA);
		assertEquals(Double.NaN, serverZonesCalculator.getResponses1xxRate().get(serv3S), DELTA);
		assertEquals(Double.NaN, serverZonesCalculator.getResponses2xxRate().get(serv3S), DELTA);
		assertEquals(Double.NaN, serverZonesCalculator.getResponses3xxRate().get(serv3S), DELTA);
		assertEquals(Double.NaN, serverZonesCalculator.getResponses4xxRate().get(serv3S), DELTA);
		assertEquals(Double.NaN, serverZonesCalculator.getResponses5xxRate().get(serv3S), DELTA);
		assertEquals(Double.NaN, serverZonesCalculator.getDiscardedRate().get(serv3S), DELTA);
		assertEquals(Double.NaN, serverZonesCalculator.getTrafficSentRate().get(serv3S), DELTA);
		assertEquals(Double.NaN, serverZonesCalculator.getTrafficRecvRate().get(serv3S), DELTA);
	}

	@Test
	public void TestCalculateCaches() {
		String cache1S = "cache1";
		String cache2S = "cache2";
		String cache3S = "cache3";

		for (String cacheName : new String[] {cache1S, cache2S}) {
			CacheDTO cache = new CacheDTO(cacheName);
			cache.setMaxSize(100.0);
			cache.setSize(100.0);
			cache.setHitResponses(100.0);
			cache.setHitBytes(100.0);
			cache.setStaleResponses(100.0);
			cache.setStaleBytes(100.0);
			cache.setUpdatingResponses(100.0);
			cache.setUpdatingBytes(100.0);
			cache.setRevalidatedResponses(100.0);
			cache.setRevalidatedBytes(100.0);
			cache.setMissResponses(100.0);
			cache.setMissBytes(100.0);
			cache.setMissResponsesWritten(100.0);
			cache.setMissBytesWritten(100.0);
			cache.setExpiredResponses(100.0);
			cache.setExpiredBytes(100.0);
			cache.setExpiredResponsesWritten(100.0);
			cache.setExpiredBytesWritten(100.0);
			cache.setBypassResponses(100.0);
			cache.setBypassBytes(100.0);
			cache.setBypassResponsesWritten(100.0);
			cache.setBypassBytesWritten(100.0);
			request1.getCaches().add(cache);
		}

		for (String cacheName : new String[] {cache1S, cache2S, cache3S}) {
			CacheDTO cache = new CacheDTO(cacheName);
			cache.setMaxSize(200.0);
			cache.setSize(200.0);
			cache.setHitResponses(200.0);
			cache.setHitBytes(200.0);
			cache.setStaleResponses(200.0);
			cache.setStaleBytes(200.0);
			cache.setUpdatingResponses(200.0);
			cache.setUpdatingBytes(200.0);
			cache.setRevalidatedResponses(200.0);
			cache.setRevalidatedBytes(200.0);
			cache.setMissResponses(200.0);
			cache.setMissBytes(200.0);
			cache.setMissResponsesWritten(200.0);
			cache.setMissBytesWritten(200.0);
			cache.setExpiredResponses(200.0);
			cache.setExpiredBytes(200.0);
			cache.setExpiredResponsesWritten(200.0);
			cache.setExpiredBytesWritten(200.0);
			cache.setBypassResponses(200.0);
			cache.setBypassBytes(200.0);
			cache.setBypassResponsesWritten(200.0);
			cache.setBypassBytesWritten(200.0);
			request2.getCaches().add(cache);
		}

		CachesCalculator cachesCalculator = calculator.getCachesCalculator();
		cachesCalculator.calculateCaches(request1, request2, 25.0);

		for (String cacheName : new String[] {cache1S, cache2S}) {
			assertEquals(4.0, cachesCalculator.getHitResponsesRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getHitResponsesRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getHitBytesRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getStaleResponsesRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getStaleBytesRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getUpdatingResponsesRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getUpdatingBytesRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getRevalidatedResponsesRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getRevalidatedBytesRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getMissResponsesRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getMissBytesRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getMissResponsesWrittenRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getMissBytesWrittenRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getExpiredResponsesRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getExpiredBytesRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getExpiredResponsesWrittenRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getExpiredBytesWrittenRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getBypassResponsesRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getBypassBytesRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getBypassResponsesWrittenRate().get(cacheName), DELTA);
			assertEquals(4.0, cachesCalculator.getBypassBytesWrittenRate().get(cacheName), DELTA);
		}

		assertEquals(Double.NaN, cachesCalculator.getHitResponsesRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getHitBytesRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getStaleResponsesRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getStaleBytesRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getUpdatingResponsesRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getUpdatingBytesRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getRevalidatedResponsesRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getRevalidatedBytesRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getMissResponsesRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getMissBytesRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getMissResponsesWrittenRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getMissBytesWrittenRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getExpiredResponsesRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getExpiredBytesRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getExpiredResponsesWrittenRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getExpiredBytesWrittenRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getBypassResponsesRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getBypassBytesRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getBypassResponsesWrittenRate().get(cache3S), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getBypassBytesWrittenRate().get(cache3S), DELTA);

		assertEquals(Double.NaN, cachesCalculator.getTotalHitResponsesRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalHitBytesRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalStaleResponsesRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalStaleBytesRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalUpdatingResponsesRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalUpdatingBytesRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalRevalidatedResponsesRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalRevalidatedBytesRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalMissResponsesRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalMissBytesRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalMissResponsesWrittenRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalMissBytesWrittenRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalExpiredResponsesRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalExpiredBytesRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalExpiredResponsesWrittenRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalExpiredBytesWrittenRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalBypassResponsesRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalBypassBytesRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalBypassResponsesWrittenRate(), DELTA);
		assertEquals(Double.NaN, cachesCalculator.getTotalBypassBytesWrittenRate(), DELTA);
	}

	@Test
	public void TestCalculateStream() {
		StreamServerZoneDTO streamServerZoneDTO;

		StreamServerDTO serv;

		String serv1S = "serv1";
		String serv2S = "serv2";
		String serv3S = "serv3";

		String group1S = "group1";
		String group2S = "group2";

		streamServerZoneDTO = new StreamServerZoneDTO("frontend");
		streamServerZoneDTO.setConnections(100.0);
		streamServerZoneDTO.setReceived(100.0);
		streamServerZoneDTO.setSent(100.0);

		request1.getStream().getServerZones().add(streamServerZoneDTO);

		streamServerZoneDTO = new StreamServerZoneDTO("backend");
		streamServerZoneDTO.setConnections(100.0);
		streamServerZoneDTO.setReceived(100.0);
		streamServerZoneDTO.setSent(100.0);

		request1.getStream().getServerZones().add(streamServerZoneDTO);

		streamServerZoneDTO = new StreamServerZoneDTO("frontend");
		streamServerZoneDTO.setConnections(200.0);
		streamServerZoneDTO.setReceived(200.0);
		streamServerZoneDTO.setSent(200.0);

		request2.getStream().getServerZones().add(streamServerZoneDTO);

		streamServerZoneDTO = new StreamServerZoneDTO("backend");
		streamServerZoneDTO.setConnections(200.0);
		streamServerZoneDTO.setReceived(200.0);
		streamServerZoneDTO.setSent(200.0);

		request2.getStream().getServerZones().add(streamServerZoneDTO);



		request1.getStream().getUpstreams().createNewServerGroup(group1S);
		request1.getStream().getUpstreams().createNewServerGroup(group2S);
		request2.getStream().getUpstreams().createNewServerGroup(group1S);
		request2.getStream().getUpstreams().createNewServerGroup(group2S);

		for (String s : new String[] {serv1S ,serv2S, serv3S}) {
			serv = new StreamServerDTO();
			serv.setActive(100.0);
			serv.setServer(s);
			serv.setConnections(100.0);
			serv.setSent(100.0);
			serv.setReceived(100.0);
			serv.setFails(100.0);
			serv.setUnavail(100.0);
			serv.setHealthChecksTotal(100.0);
			serv.setHealthChecksFails(100.0);
			serv.setHealthCheckUnhealthy(100.0);
			request1.getStream().getUpstreams().addServerByGroup(group1S, serv);
			serv = new StreamServerDTO();
			serv.setActive(100.0);
			serv.setServer(s);
			serv.setConnections(200.0);
			serv.setSent(200.0);
			serv.setReceived(200.0);
			serv.setFails(200.0);
			serv.setUnavail(200.0);
			serv.setHealthChecksTotal(200.0);
			serv.setHealthChecksFails(200.0);
			serv.setHealthCheckUnhealthy(200.0);
			request2.getStream().getUpstreams().addServerByGroup(group1S, serv);
		}

		for (String s : new String[] { serv1S, serv2S, serv3S }) {
			serv = new StreamServerDTO();
			serv.setActive(100.0);
			serv.setServer(s);
			serv.setConnections(100.0);
			serv.setSent(100.0);
			serv.setReceived(100.0);
			serv.setFails(100.0);
			serv.setUnavail(100.0);
			serv.setHealthChecksTotal(100.0);
			serv.setHealthChecksFails(100.0);
			serv.setHealthCheckUnhealthy(100.0);
			request1.getStream().getUpstreams().addServerByGroup(group2S, serv);
			serv = new StreamServerDTO();
			serv.setActive(100.0);
			serv.setServer(s);
			serv.setConnections(300.0);
			serv.setSent(300.0);
			serv.setReceived(300.0);
			serv.setFails(300.0);
			serv.setUnavail(300.0);
			serv.setHealthChecksTotal(300.0);
			serv.setHealthChecksFails(300.0);
			serv.setHealthCheckUnhealthy(300.0);
			request2.getStream().getUpstreams().addServerByGroup(group2S, serv);
		}

		StreamCalculator streamCalculator = calculator.getStreamCalculator();
		streamCalculator.calculateStream(request1, request2, 25.0);

		assertEquals(4.0, streamCalculator.getServerZoneConnectionsRate().get("frontend"), DELTA);

		assertEquals(4.0, streamCalculator.getServerZoneConnectionsRate().get("backend"), DELTA);

		assertEquals(4.0, streamCalculator.getServerZoneReceivedRate().get("frontend"), DELTA);

		assertEquals(4.0, streamCalculator.getServerZoneReceivedRate().get("backend"), DELTA);

		assertEquals(4.0, streamCalculator.getServerZoneSentRate().get("frontend"), DELTA);

		assertEquals(4.0, streamCalculator.getServerZoneSentRate().get("backend"), DELTA);

		for (String s : new String[] {serv1S, serv2S, serv3S}) {
			assertEquals(4.0, streamCalculator.getUpstreamsConnectionsRate().get(group1S, s), DELTA);
			assertEquals(4.0, streamCalculator.getUpstreamsSentRate().get(group1S, s), DELTA);
			assertEquals(4.0, streamCalculator.getUpstreamsReceivedRate().get(group1S, s), DELTA);
			assertEquals(4.0, streamCalculator.getUpstreamsFailsRate().get(group1S, s), DELTA);
			assertEquals(4.0, streamCalculator.getUpstreamsUnavailRate().get(group1S, s), DELTA);
			assertEquals(4.0, streamCalculator.getUpstreamsHealthChecksRate().get(group1S, s), DELTA);
			assertEquals(4.0, streamCalculator.getUpstreamsHealthChecksFailedRate().get(group1S, s), DELTA);
			assertEquals(4.0, streamCalculator.getUpstreamsHealthChecksUnhealthyRate().get(group1S, s), DELTA);
		}

		for (String s : new String[] { serv1S, serv2S, serv3S }) {
			assertEquals(8.0, streamCalculator.getUpstreamsConnectionsRate().get(group2S, s), DELTA);
			assertEquals(8.0, streamCalculator.getUpstreamsSentRate().get(group2S, s), DELTA);
			assertEquals(8.0, streamCalculator.getUpstreamsReceivedRate().get(group2S, s), DELTA);
			assertEquals(8.0, streamCalculator.getUpstreamsFailsRate().get(group2S, s), DELTA);
			assertEquals(8.0, streamCalculator.getUpstreamsUnavailRate().get(group2S, s), DELTA);
			assertEquals(8.0, streamCalculator.getUpstreamsHealthChecksRate().get(group2S, s), DELTA);
			assertEquals(8.0, streamCalculator.getUpstreamsHealthChecksFailedRate().get(group2S, s), DELTA);
			assertEquals(8.0, streamCalculator.getUpstreamsHealthChecksUnhealthyRate().get(group2S, s), DELTA);

		}

		assertEquals(600.0, streamCalculator.getConnectionsPerUpstream().get(group1S), DELTA);
		assertEquals(12.0, streamCalculator.getConnectionsRatePerUpstream().get(group1S), DELTA);

		assertEquals(600.0, streamCalculator.getSentPerUpstream().get(group1S), DELTA);
		assertEquals(12.0, streamCalculator.getSentRatePerUpstream().get(group1S), DELTA);

		assertEquals(600.0, streamCalculator.getReceivedPerUpstream().get(group1S), DELTA);
		assertEquals(12.0, streamCalculator.getReceivedRatePerUpstream().get(group1S), DELTA);

		assertEquals(600.0, streamCalculator.getFailsPerUpstream().get(group1S), DELTA);
		assertEquals(12.0, streamCalculator.getFailsRatePerUpstream().get(group1S), DELTA);


		assertEquals(900.0, streamCalculator.getConnectionsPerUpstream().get(group2S), DELTA);
		assertEquals(24.0, streamCalculator.getConnectionsRatePerUpstream().get(group2S), DELTA);

		assertEquals(900.0, streamCalculator.getSentPerUpstream().get(group2S), DELTA);
		assertEquals(24.0, streamCalculator.getSentRatePerUpstream().get(group2S), DELTA);

		assertEquals(900.0, streamCalculator.getReceivedPerUpstream().get(group2S), DELTA);
		assertEquals(24.0, streamCalculator.getReceivedRatePerUpstream().get(group2S), DELTA);

		assertEquals(900.0, streamCalculator.getFailsPerUpstream().get(group2S), DELTA);
		assertEquals(24.0, streamCalculator.getFailsRatePerUpstream().get(group2S), DELTA);
	}
}
