package com.pwhiting.util;

public class Commit implements Comparable {

	private final int value;
	
	public Commit(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public int compareTo(Object arg0) {
		return arg0 instanceof Commit ? Integer.compare(value, ((Commit)arg0).value) : -1;
	}

}
