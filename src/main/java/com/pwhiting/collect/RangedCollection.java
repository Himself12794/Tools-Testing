package com.pwhiting.collect;

import java.util.Collection;
import java.util.Iterator;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;

public interface RangedCollection<C extends Comparable> extends Collection<C> {



	/**
	 * The current date range used for this list.
	 *
	 * @return
	 */
	Range<C> getRange();

	/**
	 * Removes any date limits imposed.
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
	 * Limits returned data to a specific range. If you need to override this,
	 * don't forget to call super.{@link #limitToRange(DateRange)} or it
	 * won't work correctly.
	 *
	 * @param dateRange
	 *            the date range to use
	 */
	void limitToRange(final Range<C> dateRange);
	
}
