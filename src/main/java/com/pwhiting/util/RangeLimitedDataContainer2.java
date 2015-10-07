package com.pwhiting.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;

/**
 * Represents a data structure whose data entries can be restricted to a certain
 * range. Unlike {@link RangeLimitedDataContainer}, this is not recursive.
 *
 * @author phwhitin
 *
 * @param <T>
 * @param <C>
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class RangeLimitedDataContainer2<C extends Comparable> implements List<C> {

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
		/*for (final C t : data) {
			if (t instanceof RangeLimitedDataContainer2) {
				((RangeLimitedDataContainer2) t).limitToRange(Range.all());
			}
		}*/
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
	
	/**
	 * Called when data is added to check to add to limited data
	 */
	private void reassess() {
		limitToRange(getRange());
	}

	@Override
	public void add(int index, C element) {
		data.add(index, element);
		reassess();
	}

	@Override
	public boolean addAll(Collection<? extends C> c) {
		boolean v = data.addAll(c);
		reassess();
		return v;
	}

	@Override
	public boolean addAll(int index, Collection<? extends C> c) {
		boolean v = data.addAll(index, c);
		reassess();
		return v;
	}

	@Override
	public void clear() {
		data.clear();
		limitedData.clear();
	}

	@Override
	public boolean contains(Object o) {
		return isLimited() ? limitedData.contains(o) : data.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return isLimited() ? limitedData.containsAll(c) : data.containsAll(c);
	}

	@Override
	public int indexOf(Object o) {
		return isLimited() ? limitedData.indexOf(o) : data.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return isLimited() ? limitedData.isEmpty() : data.isEmpty();
	}

	@Override
	public int lastIndexOf(Object o) {
		return isLimited() ? limitedData.lastIndexOf(o) : data.lastIndexOf(o);
	}

	@Override
	public ListIterator<C> listIterator() {
		return isLimited() ? limitedData.listIterator() : data.listIterator();
	}

	@Override
	public ListIterator<C> listIterator(int index) {
		return isLimited() ? limitedData.listIterator(index) : data.listIterator(index);
	}

	@Override
	public boolean remove(Object o) {
		boolean v = data.remove(o);
		reassess();
		return v;
	}

	@Override
	public C remove(int index) {
		C c = data.remove(index);
		reassess();
		return c;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean v = data.removeAll(c);
		reassess();
		return v;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean v = data.retainAll(c);
		reassess();
		return v;
	}

	@Override
	public C set(int index, C element) {
		C v = data.set(index, element);
		reassess();
		return v;
	}

	@Override
	public List<C> subList(int fromIndex, int toIndex) {
		return isLimited() ? limitedData.subList(fromIndex, toIndex) : data.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return isLimited() ? limitedData.toArray() : data.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return isLimited() ? limitedData.toArray(a) : data.toArray(a);
	}

}
