package com.pwhiting.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;

/**
 * Represents a data structure whose data entries can be restricted to a certain
 * range. This class is recursive, so if {@code T} is also an instance of
 * DateLimitedDataContainer, it will check the date limit on that as well, and
 * so on. If desired, this can also work similarly to a list as long as all of
 * its entries implement {@link RangeLimitedData}
 *
 * @author phwhitin
 *
 * @param <T>
 * @param <C>
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class RangeLimitedDataContainer2<C extends Comparable> implements Iterable<C> {

	private final List<C> data;

	private List<C> limitedData = Lists.newArrayList();

	private Range<C> dateRange = Range.all();

	public RangeLimitedDataContainer2() {
		this(new ArrayList<C>());
	}

	public RangeLimitedDataContainer2(final List<C> data) {
		this.data = data;
		includeAll();
	}

	/**
	 * Delegate for {@link List#add(Object)}.
	 *
	 * @param c
	 * @return
	 */
	public boolean add(final C c) {
		return dateRange.contains(c) ? data.add(c) && limitedData.add(c)
				: data.add(c);
	}

	public RangeLimitedDataContainer2<C> copy() {

		final RangeLimitedDataContainer2<C> theCopy = new RangeLimitedDataContainer2<C>(data);
		theCopy.dateRange = dateRange;
		theCopy.limitedData = limitedData;

		return theCopy;
	}

	/**
	 * Delegate for {@link List#get(int)}. Works appropriately if the data is
	 * limited.
	 *
	 * @param index
	 * @return
	 */
	public C get(final int index) {
		return isLimited() ? limitedData.get(index) : data.get(index);
	}

	/**
	 * The current date range used for this repo.
	 *
	 * @return
	 */
	public Range<C> getRange() {
		return dateRange;
	}

	/**
	 * Removes any date limits imposed.
	 *
	 * @return the limited object
	 */
	public RangeLimitedDataContainer2<C> includeAll() {
		for (final C t : data) {
			if (t instanceof RangeLimitedDataContainer2) {
				((RangeLimitedDataContainer2) t).limitToRange(Range.all());
			}
		}
		limitedData = Lists.newArrayList();
		dateRange = Range.all();
		return this;
	}

	/**
	 * Determines if the data is limited.
	 *
	 * @return whether or not the data is limited
	 */
	public boolean isLimited() {
		return !this.dateRange.equals(Range.all());
	}

	@Override
	public Iterator<C> iterator() {
		return isLimited() ? limitedData.iterator() : data.iterator();
	}

	/**
	 * Limits returned data to a specific range. If you need to override this,
	 * don't forget to call super.{@link #limitToRange(DateRange)} or it
	 * won't work correctly.
	 *
	 * @param dateRange
	 *            the date range to use
	 */
	public void limitToRange(final Range<C> dateRange) {

		includeAll();
		this.dateRange = dateRange;

		for (final C c : data) {

			if (dateRange.contains(c)) {

				if (c instanceof RangeLimitedDataContainer2) {
					((RangeLimitedDataContainer2) c).limitToRange(dateRange);
				}

				limitedData.add(c);
			}
		}

	}
	
	public int size() {
		return isLimited() ? limitedData.size() : data.size();
	}

}
