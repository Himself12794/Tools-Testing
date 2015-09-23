package com.pwhiting.util;

import com.google.common.collect.Range;

public class Commit implements RangeLimitedData<Integer> {

	private final int value;
	
	public Commit(int value) {
		this.value = value;
	}
	
	@Override
	public boolean isInRange(Range<Integer> range) {
		return range.contains(value);
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}

}
