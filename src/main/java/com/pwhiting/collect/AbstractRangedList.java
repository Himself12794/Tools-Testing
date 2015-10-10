package com.pwhiting.collect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;

public abstract class AbstractRangedList<C extends Comparable> implements RangedList<C >{


	protected final List<C> data;

	protected List<C> limitedData = Lists.newArrayList();

	protected Range<C> range = Range.all();

	public AbstractRangedList() {
		this(new ArrayList<C>());
	}

	public AbstractRangedList(final List<C> data) {
		this.data = data;
		includeAll();
	}

	public RangedArrayList<C> copy() {

		final RangedArrayList<C> theCopy = new RangedArrayList<C>(data);
		theCopy.range = range;
		theCopy.limitedData = Lists.newArrayList(limitedData);

		return theCopy;
	}

	@Override
	public C get(final int index) {
		return isLimited() ? limitedData.get(index) : data.get(index);
	}

	@Override
	public Range<C> getRange() {
		return range;
	}

	@Override
	public void includeAll() {
		limitedData = Lists.newArrayList();
		range = Range.all();
	}

	@Override
	public boolean isLimited() {
		return !this.range.equals(Range.all());
	}

	@Override
	public Iterator<C> iterator() {
		return isLimited() ? limitedData.iterator() : data.iterator();
	}

	@Override
	public void limitToRange(final Range<C> range) {

		includeAll();
		this.range = range;

		for (final C c : data) {

			if (range.contains(c)) {

				if (c instanceof RangedArrayList) {
					((RangedArrayList) c).limitToRange(range);
				}

				limitedData.add(c);
			}
		}

	}
	
	@Override
	public int size() {
		return isLimited() ? limitedData.size() : data.size();
	}
	
	/**
	 * Called when data is added to check to add to limited data
	 */
	protected void reassess() {
		limitToRange(getRange());
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
		boolean result = false;
		if (isLimited()){
			result = limitedData.remove(o);
		}
		result |= data.remove(o);
		
		return result;
	}

	@Override
	public C remove(int index) {
		C c = data.remove(index);
		reassess();
		return c;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return data.removeAll(c) || limitedData.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean v = data.retainAll(c);
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
