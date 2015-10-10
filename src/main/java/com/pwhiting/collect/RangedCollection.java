package com.pwhiting.collect;

import java.util.Collection;
import com.google.common.collect.Range;

/**
 * A collection whose members can be limited to a certain range. 
 * 
 * @author Philip
 *
 * @param <C>
 */
public interface RangedCollection<C extends Comparable> extends Collection<C> {

	/**
	 * The current range used for this collection.
	 *
	 * @return
	 */
	Range<C> getRange();

	/**
	 * Removes any limits imposed.
	 *
	 * @return the limited object
	 */
	void includeAll();

	/**
	 * Determines if the data is limited.
	 *
	 * @return whether or not the data is limited
	 */
	boolean isLimited();

	/**
	 * Limits returned data to a specific range. 
	 *
	 * @param range
	 *            the range to use
	 */
	void limitToRange(final Range<C> range);
	
}
