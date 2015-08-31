package com.phwhitin.util;

public interface IWeightedItem<T> {
	
	float getWeight();
	
	/**
	 * If this object is chosen, this value is returned.
	 * 
	 * @return
	 */
	T getValue();

}
