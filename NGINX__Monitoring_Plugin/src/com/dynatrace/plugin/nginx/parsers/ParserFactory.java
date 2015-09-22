package com.dynatrace.plugin.nginx.parsers;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dynatrace.plugin.nginx.dto.stream.Stream;
import com.dynatrace.plugin.nginx.dto.stream.StreamServerDTO;
import com.dynatrace.plugin.nginx.parsers.stream.CommandParseUpstreams;
import com.dynatrace.plugin.nginx.parsers.stream.StreamParser;


public class ParserFactory {

	public ParserCollection getParserCollection(int version) throws UnsupportedOperationException {
		ParserCollection parserCollection = new ParserCollection();
		if (version == 6) {
			parserCollection.setMetaParser(new MetaParser());
			parserCollection.setCachesParser(new CachesParser());
			parserCollection.setConnectionsParser(new ConnectionsParser());
			parserCollection.setRequestsParser(new RequestsParser());
			parserCollection.setSslParser(new SSLParser());
			parserCollection.setServerZonesParser(new ServerZonesParser());
			parserCollection.setStreamParser(new StreamParser(new CommandParseUpstreams() {

				@Override
				public void parse(Stream streamDTO, JSONObject upstreams) throws JSONException {
					Iterator<?> upstreamsNames = upstreams.keys();
					while (upstreamsNames.hasNext()) {
						String upstreamName = (String) upstreamsNames.next();
						streamDTO.getUpstreams().createNewServerGroup(upstreamName);
						JSONArray upstream = upstreams.getJSONObject(upstreamName).getJSONArray("peers");
						for (int i = 0; i < upstream.length(); i++) {
							JSONObject server = upstream.getJSONObject(i);
							StreamServerDTO streamServerDTO = StreamParser.createStreamServerDtoFromServer(server);
							streamDTO.getUpstreams().addServerByGroup(upstreamName, streamServerDTO);
						}
					}
				}
			}));
			parserCollection.setUpstreamsParser(new UpstreamsParser());
			return parserCollection;
		} else if (version == 5) {
			parserCollection.setMetaParser(new MetaParser());
			parserCollection.setCachesParser(new CachesParser());
			parserCollection.setConnectionsParser(new ConnectionsParser());
			parserCollection.setSslParser(new StubParser());
			parserCollection.setRequestsParser(new RequestsParser());
			parserCollection.setServerZonesParser(new ServerZonesParser());
			parserCollection.setStreamParser(new StreamParser(new CommandParseUpstreams() {

				@Override
				public void parse(Stream streamDTO, JSONObject upstreams) throws JSONException {
					Iterator<?> upstreamsNames = upstreams.keys();
					while (upstreamsNames.hasNext()) {
						String upstreamName = (String) upstreamsNames.next();
						streamDTO.getUpstreams().createNewServerGroup(upstreamName);
						JSONArray upstream = upstreams.getJSONArray(upstreamName);
						for (int i = 0; i < upstream.length(); i++) {
							JSONObject server = upstream.getJSONObject(i);
							StreamServerDTO streamServerDTO = StreamParser.createStreamServerDtoFromServer(server);
							streamDTO.getUpstreams().addServerByGroup(upstreamName, streamServerDTO);
						}
					}
				}
			}));
			parserCollection.setUpstreamsParser(new UpstreamsParser());
			return parserCollection;
		} else {
			throw new UnsupportedOperationException("Unsupported Version");
		}
	}
}
