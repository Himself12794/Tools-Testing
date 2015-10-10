package com.pwhiting.collect;

import com.google.common.collect.Range;

/**
 * Indicates a data type that can fall within a time frame.
 *
 * @author phwhitin
 *
 */
public interface RangeLimitedData<C extends Comparable<?>> {

	/**
	 * Check whether or not this data falls in the date range.
	 *
	 * @param start
	 * @param end
	 * @param inclusive
	 * @return
	 */
	boolean isInRange(Range<C> range);

}
