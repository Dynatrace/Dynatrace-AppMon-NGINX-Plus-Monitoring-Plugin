package com.dynatrace.plugin.nginx.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.CacheDTO;

public class CachesParser implements ParserInterface {

	public static String GROUP = "NGINX Plus Monitor Caches";

	@Override
	public Object parse(JSONObject jsonObject) throws JSONException {
		Collection<CacheDTO> caches_ = new ArrayList<>();
		JSONObject caches = jsonObject.optJSONObject("caches");
		if (caches == null) {
			return caches_;
		}

		final String responses = "responses";
		final String bytes = "bytes";
		final String responses_written = "responses_written";
		final String bytes_written = "bytes_written";

		Iterator<?> cacheNames = caches.keys();
		while (cacheNames.hasNext()) {
			String cacheName = (String)cacheNames.next();
			CacheDTO cacheDTO = new CacheDTO(cacheName);
			JSONObject cache = caches.getJSONObject(cacheName);

			cacheDTO.setSize(cache.optDouble("size", Double.NaN));
			cacheDTO.setMaxSize(cache.optDouble("max_size", Double.NaN));
			cacheDTO.setCold(cache.optBoolean("cold", true));

			JSONObject hit = cache.optJSONObject("hit");
			if (hit != null) {
				cacheDTO.setHitResponses(hit.optDouble(responses, Double.NaN));
				cacheDTO.setHitBytes(hit.optDouble(bytes, Double.NaN));
			}

			JSONObject stale = cache.optJSONObject("stale");
			if (stale != null) {
				cacheDTO.setStaleResponses(stale.optDouble(responses, Double.NaN));
				cacheDTO.setStaleBytes(stale.optDouble(bytes, Double.NaN));
			}

			JSONObject updating = cache.optJSONObject("updating");
			if (updating != null) {
				cacheDTO.setUpdatingResponses(updating.optDouble(responses, Double.NaN));
				cacheDTO.setUpdatingBytes(updating.optDouble(bytes, Double.NaN));
			}

			JSONObject revalidated = cache.optJSONObject("revalidated");
			if (revalidated != null) {
				cacheDTO.setRevalidatedResponses(revalidated.optDouble(responses, Double.NaN));
				cacheDTO.setRevalidatedBytes(revalidated.optDouble(bytes, Double.NaN));
			}

			JSONObject miss = cache.optJSONObject("miss");
			if (miss != null) {
				cacheDTO.setMissResponses(miss.optDouble(responses, Double.NaN));
				cacheDTO.setMissBytes(miss.optDouble(bytes, Double.NaN));
				cacheDTO.setMissResponsesWritten(miss.optDouble(responses_written, Double.NaN));
				cacheDTO.setMissBytesWritten(miss.optDouble(bytes_written, Double.NaN));
			}

			JSONObject expired = cache.optJSONObject("expired");
			if (expired != null) {
				cacheDTO.setExpiredResponses(expired.optDouble(responses, Double.NaN));
				cacheDTO.setExpiredBytes(expired.optDouble(bytes, Double.NaN));
				cacheDTO.setExpiredResponsesWritten(expired.optDouble(responses_written, Double.NaN));
				cacheDTO.setExpiredBytesWritten(expired.optDouble(bytes_written, Double.NaN));
			}

			JSONObject bypass = cache.optJSONObject("bypass");
			if (bypass != null) {
				cacheDTO.setBypassResponses(bypass.optDouble(responses, Double.NaN));
				cacheDTO.setBypassBytes(bypass.optDouble(bytes, Double.NaN));
				cacheDTO.setBypassResponsesWritten(bypass.optDouble(responses_written, Double.NaN));
				cacheDTO.setBypassBytesWritten(bypass.optDouble(bytes_written, Double.NaN));
			}

			caches_.add(cacheDTO);
		}
		return caches_;
	}
}
