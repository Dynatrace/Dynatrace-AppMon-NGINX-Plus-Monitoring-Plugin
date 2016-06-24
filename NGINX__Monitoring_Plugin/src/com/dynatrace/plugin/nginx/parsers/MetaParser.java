package com.dynatrace.plugin.nginx.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.MetaDTO;


public class MetaParser implements ParserInterface {

	@Override
	public Object parse(JSONObject jsonObject) throws JSONException {
		MetaDTO metaDTO = new MetaDTO();
		metaDTO.setVersion(jsonObject.optDouble("version", Double.NaN));
		metaDTO.setNginx_version(jsonObject.optString("nginx_version", ""));
		metaDTO.setAddress(jsonObject.optString("address", ""));
		metaDTO.setGeneration(jsonObject.optDouble("generation", Double.NaN));
		metaDTO.setLoad_timestamp(jsonObject.optDouble("load_timestamp", Double.NaN));
		metaDTO.setTimestamp(jsonObject.getDouble("timestamp") / 1000);
		metaDTO.setPid(jsonObject.optDouble("pid", Double.NaN));
		return metaDTO;
	}
}
