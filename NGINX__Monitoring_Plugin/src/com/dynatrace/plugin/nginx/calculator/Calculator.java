package com.dynatrace.plugin.nginx.calculator;

import com.dynatrace.plugin.nginx.dto.NginxStatus;

public interface Calculator {
	void calculate(NginxStatus prev, NginxStatus cur);
}
