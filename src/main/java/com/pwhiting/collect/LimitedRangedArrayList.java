package com.pwhiting.collect;

import java.util.Collection;
import java.util.Iterator;

import com.google.common.collect.Range;

/**
 * Unlike other implementations, this refuses to accept any values not contained in 
 * the range, and they are not stored in a list of total values, meaning they are
 * completely forgotten.
 * 
 * @author Philip
 *
 * @param <C>
 */
public class LimitedRangedArrayList<C extends Comparable<?>> extends AbstractRangedList<C> {

	@Override
	public boolean add(C e) {
		return range.contains(e) ? limitedData.add(e) : false; 
	}

	@Override
	public boolean addAll(Collection<? extends C> c) {
		boolean result = false;
		for (C item : c) {
			if (range.contains(item)) {
				result |= limitedData.add(item);
			}
		}
		return result;
	}

	@Override
	public boolean addAll(int index, Collection<? extends C> c) {
		boolean result = false;
		int start = index;
		for (C item : c) {
			if (range.contains(item)) {
				try {
					limitedData.add(start, item);
					if (!result) result = true;
					start++;
				} catch (Exception uoe ) {
					// We don't need to do anything
				}
			}
		}
		return result;
	}

	@Override
	public C set(int index, C element) {
		if (range.contains(element)) {
			return limitedData.set(index, element);
		}
		return null;
	}

	@Override
	public void add(int index, C element) {
		if (range.contains(element)) {
			limitedData.add(index, element);
		}
	}

	@Override
	public void includeAll() {
		range = Range.all();
	}
	
	@Override
	public boolean isLimited() {
		return true;
	}

	@Override
	public void limitToRange(final Range<C> range) {

		this.range = range;
		
		Iterator<C> itr = limitedData.iterator();
		
		while (itr.hasNext()) {
			final C c = itr.next();

			if (!range.contains(c)) {
				itr.remove();
			}
		}

	}

	@Override
	public C remove(int index) {
		return limitedData.remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return limitedData.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean hasResult = false;
		
		Iterator<C> itr = limitedData.iterator();
		while (itr.hasNext()) {
			C item = itr.next();
			if (!c.contains(item)) {
				itr.remove();
				if (!hasResult) hasResult = true;
			}
			
		}
		
		return hasResult;
		
	}

}
