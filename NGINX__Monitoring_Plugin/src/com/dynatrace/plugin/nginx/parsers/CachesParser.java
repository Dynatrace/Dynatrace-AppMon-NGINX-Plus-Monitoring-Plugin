package com.dynatrace.plugin.nginx.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.CacheDTO;

public class CachesParser {

	public static String GROUP = "NGINX Plus Monitor Caches";

	public static Collection<CacheDTO> parse(JSONObject jsonObject) throws JSONException {
		JSONObject caches = jsonObject.getJSONObject("caches");
		Collection<CacheDTO> caches_ = new ArrayList<>();

		final String responses = "responses";
		final String bytes = "bytes";
		final String responses_written = "responses_written";
		final String bytes_written = "bytes_written";

		Iterator<?> cacheNames = caches.keys();
		while (cacheNames.hasNext()) {
			String cacheName = (String)cacheNames.next();
			CacheDTO cacheDTO = new CacheDTO(cacheName);
			JSONObject cache = caches.getJSONObject(cacheName);

			cacheDTO.setSize(cache.getDouble("size"));
			cacheDTO.setMaxSize(cache.getDouble("max_size"));
			cacheDTO.setCold(cache.getBoolean("cold"));

			JSONObject hit = cache.getJSONObject("hit");
			cacheDTO.setHitResponses(hit.getDouble(responses));
			cacheDTO.setHitBytes(hit.getDouble(bytes));

			JSONObject stale = cache.getJSONObject("stale");
			cacheDTO.setStaleResponses(stale.getDouble(responses));
			cacheDTO.setStaleBytes(stale.getDouble(bytes));

			JSONObject updating = cache.getJSONObject("updating");
			cacheDTO.setUpdatingResponses(updating.getDouble(responses));
			cacheDTO.setUpdatingBytes(updating.getDouble(bytes));

			JSONObject revalidated = cache.getJSONObject("revalidated");
			cacheDTO.setRevalidatedResponses(revalidated.getDouble(responses));
			cacheDTO.setRevalidatedBytes(revalidated.getDouble(bytes));

			JSONObject miss = cache.getJSONObject("miss");
			cacheDTO.setMissResponses(miss.getDouble(responses));
			cacheDTO.setMissBytes(miss.getDouble(bytes));
			cacheDTO.setMissResponsesWritten(miss.getDouble(responses_written));
			cacheDTO.setMissBytesWritten(miss.getDouble(bytes_written));

			JSONObject expired = cache.getJSONObject("expired");
			cacheDTO.setExpiredResponses(expired.getDouble(responses));
			cacheDTO.setExpiredBytes(expired.getDouble(bytes));
			cacheDTO.setExpiredResponsesWritten(expired.getDouble(responses_written));
			cacheDTO.setExpiredBytesWritten(expired.getDouble(bytes_written));

			JSONObject bypass = cache.getJSONObject("bypass");
			cacheDTO.setBypassResponses(bypass.getDouble(responses));
			cacheDTO.setBypassBytes(bypass.getDouble(bytes));
			cacheDTO.setBypassResponsesWritten(bypass.getDouble(responses_written));
			cacheDTO.setBypassBytesWritten(bypass.getDouble(bytes_written));

			caches_.add(cacheDTO);
		}
		return caches_;
	}
}
