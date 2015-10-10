package com.pwhiting.collect;

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
public class RangeLimitedDataContainer<T extends RangeLimitedData<? extends Comparable>> implements Iterable<T> {

	private final List<T> data;

	private List<T> limitedData = Lists.newArrayList();

	private Range dateRange = Range.all();

	public RangeLimitedDataContainer() {
		this(new ArrayList<T>());
	}

	public RangeLimitedDataContainer(final List<T> data) {
		this.data = data;
		includeAll();
	}

	/**
	 * Delegate for {@link List#add(Object)}.
	 *
	 * @param t
	 * @return
	 */
	public boolean add(final T t) {
		return t.isInRange(dateRange) ? data.add(t) && limitedData.add(t)
				: data.add(t);
	}

	public RangeLimitedDataContainer<T> copy() {

		final RangeLimitedDataContainer<T> theCopy = new RangeLimitedDataContainer<T>(data);
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
	public T get(final int index) {
		return isLimited() ? limitedData.get(index) : data.get(index);
	}

	/**
	 * The current date range used for this repo.
	 *
	 * @return
	 */
	public Range getRange() {
		return dateRange;
	}

	/**
	 * Removes any date limits imposed.
	 *
	 * @return the limited object
	 */
	public RangeLimitedDataContainer<T> includeAll() {
		for (final T t : data) {
			if (t instanceof RangeLimitedDataContainer) {
				((RangeLimitedDataContainer) t).limitToRange(Range.all());
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
	public Iterator<T> iterator() {
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
	public void limitToRange(final Range dateRange) {

		includeAll();
		this.dateRange = dateRange;

		for (final T t : data) {

			if (t.isInRange(dateRange)) {

				if (t instanceof RangeLimitedDataContainer) {
					((RangeLimitedDataContainer) t).limitToRange(dateRange);
				}

				limitedData.add(t);
			}
		}

	}
	
	public int size() {
		return isLimited() ? limitedData.size() : data.size();
	}

}
