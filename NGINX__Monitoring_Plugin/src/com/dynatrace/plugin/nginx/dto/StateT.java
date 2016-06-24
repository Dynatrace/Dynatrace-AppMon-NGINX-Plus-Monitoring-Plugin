package com.dynatrace.plugin.nginx.dto;

import org.json.JSONException;

public class StateT {
	public static final String UP = "up";
	public static final String DRAINING = "draining";
	public static final String DOWN = "down";
	public static final String UNAVAIL = "unavail";
	public static final String UNHEALTHY = "unhealthy";
	private String state;

	public StateT(String state) {
		this.state = state;
	}

	public Boolean equals(String state) {
		return this.state.equals(state);
	}

	public Double toDouble() throws JSONException {
		if (UP.equals(this.state)) {
            return 0.0;
        } else if (DRAINING.equals(this.state)) {
        	return 1.0;
        } else if (DOWN.equals(this.state)) {
            return 2.0;
        } else if (UNAVAIL.equals(this.state)) {
            return 3.0;
        } else if (UNHEALTHY.equals(this.state)) {
            return 4.0;
        } else {
        	throw new JSONException("Invalid State");
        }
	}

}
