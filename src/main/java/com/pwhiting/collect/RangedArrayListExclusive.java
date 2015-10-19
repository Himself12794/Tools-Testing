package com.pwhiting.collect;

import java.util.Collection;

/**
 * Unlike {@link RangedArrayList}, values added to this list that do not fall within the range
 * are ignored.
 * 
 * @author Philip
 *
 * @param <C>
 */
public class RangedArrayListExclusive<C extends Comparable<?>> extends AbstractRangedList<C> {
	
	@Override
	public boolean add(C c) {
		return range.contains(c) ? data.add(c) && limitedData.add(c) : false;
	}
	
	@Override
	public boolean addAll(Collection<? extends C> c) {
		boolean hasResult = false;
		for (C item : c) {
			if (range.contains(item)) {
				hasResult |= data.add(item) && limitedData.add(item);
			}
		}
		
		return hasResult;
	}

	@Override
	public boolean addAll(int index, Collection<? extends C> c) {
		return addAll(c);
	}

	@Override
	public void add(int index, C element) {
		// Add at index unsupported,defaults to regular add
		add(element);
	}

	@Override
	public C set(int index, C element) {
		return null;
	}

}
