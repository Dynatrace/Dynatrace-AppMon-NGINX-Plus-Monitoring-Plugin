package com.dynatrace.plugin.nginx.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.MetaDTO;


public class MetaParser {
	public static MetaDTO parse(JSONObject jsonObject) throws JSONException {
		MetaDTO metaDTO = new MetaDTO();
		metaDTO.setVersion(jsonObject.getDouble("version"));
		metaDTO.setNginx_version(jsonObject.getString("nginx_version"));
		metaDTO.setTimestamp(jsonObject.getLong("timestamp")/1000);
		return metaDTO;
	}
}
