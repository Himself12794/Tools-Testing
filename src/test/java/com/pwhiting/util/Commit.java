package com.pwhiting.util;

import com.google.common.collect.Range;

public class Commit implements Comparable<Integer> {

	private final int value;
	
	public Commit(int value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public int compareTo(Integer arg0) {
		return Integer.compare(value, arg0);
	}

}
