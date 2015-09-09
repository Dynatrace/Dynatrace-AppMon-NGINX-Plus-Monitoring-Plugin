package com.dynatrace.plugin.nginx.bookers;

import java.util.Collection;

import com.dynatrace.diagnostics.pdk.MonitorEnvironment;
import com.dynatrace.diagnostics.pdk.MonitorMeasure;
import com.dynatrace.plugin.nginx.calculator.CachesCalculator;
import com.dynatrace.plugin.nginx.dto.CacheDTO;
import com.dynatrace.plugin.nginx.parsers.CachesParser;

public class CachesBooker extends Booker {
	public static final String MSR_SIZE = "Size";
	public static final String MSR_MAX_SIZE = "Max Size";
	public static final String MSR_COLD = "Cold";

	public static final String MSR_HIT_RESPONSES = "Hit Responses";
	public static final String MSR_HIT_RESPONSES_RATE = "Hit Responses Rate";
	public static final String MSR_HIT_BYTES = "Hit Bytes";
	public static final String MSR_HIT_BYTES_RATE = "Hit Bytes Rate";

	public static final String MSR_STALE_RESPONSES = "Stale Responses";
	public static final String MSR_STALE_RESPONSES_RATE = "Stale Responses Rate";
	public static final String MSR_STALE_BYTES = "Stale Bytes";
	public static final String MSR_STALE_BYTES_RATE = "Stale Bytes Rate";

	public static final String MSR_UPDATING_RESPONSES = "Updating Responses";
	public static final String MSR_UPDATING_RESPONSES_RATE = "Updating Responses Rate";
	public static final String MSR_UPDATING_BYTES = "Updating Bytes";
	public static final String MSR_UPDATING_BYTES_RATE = "Updating Bytes Rate";

	public static final String MSR_REVALIDATED_RESPONSES = "Revalidated Responses";
	public static final String MSR_REVALIDATED_RESPONSES_RATE = "Revalidated Responses Rate";
	public static final String MSR_REVALIDATED_BYTES = "Revalidated Bytes";
	public static final String MSR_REVALIDATED_BYTES_RATE = "Revalidated Bytes Rate";

	public static final String MSR_MISS_RESPOSNES = "Miss Responses";
	public static final String MSR_MISS_RESPOSNES_RATE = "Miss Responses Rate";
	public static final String MSR_MISS_BYTES = "Miss Bytes";
	public static final String MSR_MISS_BYTES_RATE = "Miss Bytes Rate";
	public static final String MSR_MISS_RESPONSES_WRITTEN = "Miss Responses Written";
	public static final String MSR_MISS_RESPONSES_WRITTEN_RATE = "Miss Responses Written Rate";
	public static final String MSR_MISS_BYTES_WRITTEN = "Miss Bytes Written";
	public static final String MSR_MISS_BYTES_WRITTEN_RATE = "Miss Bytes Written Rate";

	public static final String MSR_EXPIRED_RESPOSNES = "Expired Responses";
	public static final String MSR_EXPIRED_RESPOSNES_RATE = "Expired Responses Rate";
	public static final String MSR_EXPIRED_BYTES = "Expired Bytes";
	public static final String MSR_EXPIRED_BYTES_RATE = "Expired Bytes Rate";
	public static final String MSR_EXPIRED_RESPONSES_WRITTEN = "Expired Responses Written";
	public static final String MSR_EXPIRED_RESPONSES_WRITTEN_RATE = "Expired Responses Written Rate";
	public static final String MSR_EXPIRED_BYTES_WRITTEN = "Expired Bytes Written";
	public static final String MSR_EXPIRED_BYTES_WRITTEN_RATE = "Expired Bytes Written Rate";

	public static final String MSR_BYPASS_RESPONSES = "Bypass Responses";
	public static final String MSR_BYPASS_RESPONSES_RATE = "Bypass Responses Rate";
	public static final String MSR_BYPASS_BYTES = "Bypass Bytes";
	public static final String MSR_BYPASS_BYTES_RATE = "Bypass Bytes Rate";
	public static final String MSR_BYPASS_RESPONSES_WRITTEN = "Bypass Responses Written";
	public static final String MSR_BYPASS_RESPONSES_WRITTEN_RATE = "Bypass Responses Written Rate";
	public static final String MSR_BYPASS_BYTES_WRITTEN = "Bypass Bytes Written";
	public static final String MSR_BYPASS_BYTES_WRITTEN_RATE = "Bypass Bytes Written Rate";

	public static String DYNAMIC_KEY = "Cache Name";

	public static void book(Collection<CacheDTO> caches, MonitorEnvironment env, CachesCalculator calculator) {

		Collection<MonitorMeasure> sizeM = env.getMonitorMeasures(CachesParser.GROUP, MSR_SIZE);
		Collection<MonitorMeasure> maxSizeM = env.getMonitorMeasures(CachesParser.GROUP, MSR_MAX_SIZE);
		Collection<MonitorMeasure> coldM = env.getMonitorMeasures(CachesParser.GROUP, MSR_COLD);

		Collection<MonitorMeasure> hitResponsesM = env.getMonitorMeasures(CachesParser.GROUP, MSR_HIT_RESPONSES);
		Collection<MonitorMeasure> hitResponsesRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_HIT_RESPONSES_RATE);
		Collection<MonitorMeasure> hitBytesM = env.getMonitorMeasures(CachesParser.GROUP, MSR_HIT_BYTES);
		Collection<MonitorMeasure> hitBytesRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_HIT_BYTES_RATE);

		Collection<MonitorMeasure> staleResponsesM = env.getMonitorMeasures(CachesParser.GROUP, MSR_STALE_RESPONSES);
		Collection<MonitorMeasure> staleResponsesRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_STALE_RESPONSES_RATE);
		Collection<MonitorMeasure> staleBytesM = env.getMonitorMeasures(CachesParser.GROUP, MSR_STALE_BYTES);
		Collection<MonitorMeasure> staleBytesRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_STALE_BYTES_RATE);

		Collection<MonitorMeasure> updatingResponsesM = env.getMonitorMeasures(CachesParser.GROUP, MSR_UPDATING_RESPONSES);
		Collection<MonitorMeasure> updatingResponsesRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_UPDATING_RESPONSES_RATE);
		Collection<MonitorMeasure> updatingBytesM = env.getMonitorMeasures(CachesParser.GROUP, MSR_UPDATING_BYTES);
		Collection<MonitorMeasure> updatingBytesRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_UPDATING_BYTES_RATE);

		Collection<MonitorMeasure> revalidatedResponsesM = env.getMonitorMeasures(CachesParser.GROUP, MSR_REVALIDATED_RESPONSES);
		Collection<MonitorMeasure> revalidatedResponsesRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_REVALIDATED_RESPONSES_RATE);
		Collection<MonitorMeasure> revalidatedBytesM = env.getMonitorMeasures(CachesParser.GROUP, MSR_REVALIDATED_BYTES);
		Collection<MonitorMeasure> revalidatedBytesRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_REVALIDATED_BYTES_RATE);

		Collection<MonitorMeasure> missResponsesM = env.getMonitorMeasures(CachesParser.GROUP, MSR_MISS_RESPOSNES);
		Collection<MonitorMeasure> missResponsesRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_MISS_RESPOSNES_RATE);
		Collection<MonitorMeasure> missBytesM = env.getMonitorMeasures(CachesParser.GROUP, MSR_MISS_BYTES);
		Collection<MonitorMeasure> missBytesRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_MISS_BYTES_RATE);
		Collection<MonitorMeasure> missResponsesWrittenM = env.getMonitorMeasures(CachesParser.GROUP, MSR_MISS_RESPONSES_WRITTEN);
		Collection<MonitorMeasure> missResponsesWrittenRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_MISS_RESPONSES_WRITTEN_RATE);
		Collection<MonitorMeasure> missBytesWrittenM = env.getMonitorMeasures(CachesParser.GROUP, MSR_MISS_BYTES_WRITTEN);
		Collection<MonitorMeasure> missBytesWrittenRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_MISS_BYTES_WRITTEN_RATE);

		Collection<MonitorMeasure> expiredResponsesM = env.getMonitorMeasures(CachesParser.GROUP, MSR_EXPIRED_RESPOSNES);
		Collection<MonitorMeasure> expiredResponsesRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_EXPIRED_RESPOSNES_RATE);
		Collection<MonitorMeasure> expiredBytesM = env.getMonitorMeasures(CachesParser.GROUP, MSR_EXPIRED_BYTES);
		Collection<MonitorMeasure> expiredBytesRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_EXPIRED_BYTES_RATE);
		Collection<MonitorMeasure> expiredResponsesWrittenM = env.getMonitorMeasures(CachesParser.GROUP, MSR_EXPIRED_RESPONSES_WRITTEN);
		Collection<MonitorMeasure> expiredResponsesWrittenRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_EXPIRED_RESPONSES_WRITTEN_RATE);
		Collection<MonitorMeasure> expiredBytesWrittenM = env.getMonitorMeasures(CachesParser.GROUP, MSR_EXPIRED_BYTES_WRITTEN);
		Collection<MonitorMeasure> expiredBytesWrittenRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_EXPIRED_BYTES_WRITTEN_RATE);

		Collection<MonitorMeasure> bypassResponsesM = env.getMonitorMeasures(CachesParser.GROUP, MSR_BYPASS_RESPONSES);
		Collection<MonitorMeasure> bypassResponsesRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_BYPASS_RESPONSES_RATE);
		Collection<MonitorMeasure> bypassBytesM = env.getMonitorMeasures(CachesParser.GROUP, MSR_BYPASS_BYTES);
		Collection<MonitorMeasure> bypassBytesRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_BYPASS_BYTES_RATE);
		Collection<MonitorMeasure> bypassResponsesWrittenM = env.getMonitorMeasures(CachesParser.GROUP, MSR_BYPASS_RESPONSES_WRITTEN);
		Collection<MonitorMeasure> bypassResponsesWrittenRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_BYPASS_RESPONSES_WRITTEN_RATE);
		Collection<MonitorMeasure> bypassBytesWrittenM = env.getMonitorMeasures(CachesParser.GROUP, MSR_BYPASS_BYTES_WRITTEN);
		Collection<MonitorMeasure> bypassBytesWrittenRateM = env.getMonitorMeasures(CachesParser.GROUP, MSR_BYPASS_BYTES_WRITTEN_RATE);

		for (CacheDTO c: caches) {
			String cacheName = c.getCacheName();
			setDynamicMeasure(env, sizeM, DYNAMIC_KEY, cacheName, c.getSize());
			setDynamicMeasure(env, maxSizeM, DYNAMIC_KEY, cacheName, c.getMaxSize());
			setDynamicMeasure(env, coldM, DYNAMIC_KEY, cacheName, (double) (c.getCold() ? 1 : 0));

			setDynamicMeasure(env, hitResponsesM, DYNAMIC_KEY, cacheName, c.getHitResponses());
			setDynamicMeasure(env, hitResponsesRateM, DYNAMIC_KEY, cacheName, calculator.getHitResponsesRate().get(cacheName));
			setDynamicMeasure(env, hitBytesM, DYNAMIC_KEY, cacheName, c.getHitBytes());
			setDynamicMeasure(env, hitBytesRateM, DYNAMIC_KEY, cacheName, calculator.getHitBytesRate().get(cacheName));

			setDynamicMeasure(env, staleResponsesM, DYNAMIC_KEY, cacheName, c.getStaleResponses());
			setDynamicMeasure(env, staleResponsesRateM, DYNAMIC_KEY, cacheName, calculator.getStaleResponsesRate().get(cacheName));
			setDynamicMeasure(env, staleBytesM, DYNAMIC_KEY, cacheName, c.getStaleBytes());
			setDynamicMeasure(env, staleBytesRateM, DYNAMIC_KEY, cacheName, calculator.getStaleBytesRate().get(cacheName));

			setDynamicMeasure(env, updatingResponsesM, DYNAMIC_KEY, cacheName, c.getUpdatingResponses());
			setDynamicMeasure(env, updatingResponsesRateM, DYNAMIC_KEY, cacheName, calculator.getUpdatingResponsesRate().get(cacheName));
			setDynamicMeasure(env, updatingBytesM, DYNAMIC_KEY, cacheName, c.getUpdatingBytes());
			setDynamicMeasure(env, updatingBytesRateM, DYNAMIC_KEY, cacheName, calculator.getUpdatingBytesRate().get(cacheName));

			setDynamicMeasure(env, revalidatedResponsesM, DYNAMIC_KEY, cacheName, c.getRevalidatedResponses());
			setDynamicMeasure(env, revalidatedResponsesRateM, DYNAMIC_KEY, cacheName, calculator.getRevalidatedResponsesRate().get(cacheName));
			setDynamicMeasure(env, revalidatedBytesM, DYNAMIC_KEY, cacheName, c.getRevalidatedBytes());
			setDynamicMeasure(env, revalidatedBytesRateM, DYNAMIC_KEY, cacheName, calculator.getRevalidatedBytesRate().get(cacheName));

			setDynamicMeasure(env, missResponsesM, DYNAMIC_KEY, cacheName, c.getMissResponses());
			setDynamicMeasure(env, missResponsesRateM, DYNAMIC_KEY, cacheName, calculator.getMissResponsesRate().get(cacheName));
			setDynamicMeasure(env, missBytesM, DYNAMIC_KEY, cacheName, c.getMissBytes());
			setDynamicMeasure(env, missBytesRateM, DYNAMIC_KEY, cacheName, calculator.getMissBytesRate().get(cacheName));
			setDynamicMeasure(env, missResponsesWrittenM, DYNAMIC_KEY, cacheName, c.getMissResponsesWritten());
			setDynamicMeasure(env, missResponsesWrittenRateM, DYNAMIC_KEY, cacheName, calculator.getMissResponsesWrittenRate().get(cacheName));
			setDynamicMeasure(env, missBytesWrittenM, DYNAMIC_KEY, cacheName, c.getMissBytesWritten());
			setDynamicMeasure(env, missBytesWrittenRateM, DYNAMIC_KEY, cacheName, calculator.getMissBytesWrittenRate().get(cacheName));

			setDynamicMeasure(env, expiredResponsesM, DYNAMIC_KEY, cacheName, c.getExpiredResponses());
			setDynamicMeasure(env, expiredResponsesRateM, DYNAMIC_KEY, cacheName, calculator.getExpiredResponsesRate().get(cacheName));
			setDynamicMeasure(env, expiredBytesM, DYNAMIC_KEY, cacheName, c.getExpiredBytes());
			setDynamicMeasure(env, expiredBytesRateM, DYNAMIC_KEY, cacheName, calculator.getExpiredBytesRate().get(cacheName));
			setDynamicMeasure(env, expiredResponsesWrittenM, DYNAMIC_KEY, cacheName, c.getExpiredResponsesWritten());
			setDynamicMeasure(env, expiredResponsesWrittenRateM, DYNAMIC_KEY, cacheName, calculator.getExpiredResponsesWrittenRate().get(cacheName));
			setDynamicMeasure(env, expiredBytesWrittenM, DYNAMIC_KEY, cacheName, c.getExpiredBytesWritten());
			setDynamicMeasure(env, expiredBytesWrittenRateM, DYNAMIC_KEY, cacheName, calculator.getExpiredBytesWrittenRate().get(cacheName));

			setDynamicMeasure(env, bypassResponsesM, DYNAMIC_KEY, cacheName, c.getBypassResponses());
			setDynamicMeasure(env, bypassResponsesRateM, DYNAMIC_KEY, cacheName, calculator.getBypassResponsesRate().get(cacheName));
			setDynamicMeasure(env, bypassBytesM, DYNAMIC_KEY, cacheName, c.getBypassBytes());
			setDynamicMeasure(env, bypassBytesRateM, DYNAMIC_KEY, cacheName, calculator.getBypassBytesRate().get(cacheName));
			setDynamicMeasure(env, bypassResponsesWrittenM, DYNAMIC_KEY, cacheName, c.getBypassResponsesWritten());
			setDynamicMeasure(env, bypassResponsesWrittenRateM, DYNAMIC_KEY, cacheName, calculator.getBypassResponsesWrittenRate().get(cacheName));
			setDynamicMeasure(env, bypassBytesWrittenM, DYNAMIC_KEY, cacheName, c.getBypassBytesWritten());
			setDynamicMeasure(env, bypassBytesWrittenRateM, DYNAMIC_KEY, cacheName, calculator.getBypassBytesWrittenRate().get(cacheName));
		}

		for (MonitorMeasure m : hitResponsesRateM) {
			m.setValue(calculator.getTotalHitResponsesRate());
		}

		for (MonitorMeasure m : hitBytesRateM) {
			m.setValue(calculator.getTotalHitBytesRate());
		}

		for (MonitorMeasure m : staleResponsesRateM) {
			m.setValue(calculator.getTotalStaleResponsesRate());
		}

		for (MonitorMeasure m : staleBytesRateM) {
			m.setValue(calculator.getTotalStaleBytesRate());
		}

		for (MonitorMeasure m : updatingResponsesRateM) {
			m.setValue(calculator.getTotalUpdatingResponsesRate());
		}

		for (MonitorMeasure m : updatingBytesRateM) {
			m.setValue(calculator.getTotalUpdatingBytesRate());
		}

		for (MonitorMeasure m : revalidatedResponsesRateM) {
			m.setValue(calculator.getTotalRevalidatedResponsesRate());
		}

		for (MonitorMeasure m : revalidatedBytesRateM) {
			m.setValue(calculator.getTotalRevalidatedBytesRate());
		}

		for (MonitorMeasure m : missResponsesRateM) {
			m.setValue(calculator.getTotalMissResponsesRate());
		}

		for (MonitorMeasure m : missBytesRateM) {
			m.setValue(calculator.getTotalMissBytesRate());
		}

		for (MonitorMeasure m : missResponsesWrittenRateM) {
			m.setValue(calculator.getTotalMissResponsesWrittenRate());
		}

		for (MonitorMeasure m : missBytesWrittenRateM) {
			m.setValue(calculator.getTotalMissBytesWrittenRate());
		}

		for (MonitorMeasure m : expiredResponsesRateM) {
			m.setValue(calculator.getTotalExpiredResponsesRate());
		}

		for (MonitorMeasure m : expiredBytesRateM) {
			m.setValue(calculator.getTotalExpiredBytesRate());
		}

		for (MonitorMeasure m : expiredResponsesWrittenRateM) {
			m.setValue(calculator.getTotalExpiredResponsesWrittenRate());
		}

		for (MonitorMeasure m : expiredBytesWrittenRateM) {
			m.setValue(calculator.getTotalExpiredBytesWrittenRate());
		}

		for (MonitorMeasure m : bypassResponsesRateM) {
			m.setValue(calculator.getTotalBypassResponsesRate());
		}

		for (MonitorMeasure m : bypassBytesRateM) {
			m.setValue(calculator.getTotalBypassBytesRate());
		}

		for (MonitorMeasure m : bypassResponsesWrittenRateM) {
			m.setValue(calculator.getTotalBypassResponsesWrittenRate());
		}

		for (MonitorMeasure m : bypassBytesWrittenRateM) {
			m.setValue(calculator.getTotalBypassBytesWrittenRate());
		}
	}
}
