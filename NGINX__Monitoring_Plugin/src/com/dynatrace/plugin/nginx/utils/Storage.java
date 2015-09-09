package com.dynatrace.plugin.nginx.utils;

public class Storage<A> {
	private A status;

	public void put(A status) {
		this.status = status;
	}

	public A get() {
		return this.status;
	}

	public boolean isEmpty() {
		if (this.status != null) {
			return false;
		} else {
			return true;
		}
	}
}
