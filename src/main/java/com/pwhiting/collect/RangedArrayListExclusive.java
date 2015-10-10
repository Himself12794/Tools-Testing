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
public class RangedArrayListExclusive<C extends Comparable> extends RangedArrayList<C> {
	
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

}
