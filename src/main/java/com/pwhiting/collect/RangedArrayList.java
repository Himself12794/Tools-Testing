package com.pwhiting.collect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;

/**
 * Represents a data structure whose data entries can be restricted to a certain
 * range. New elements added to this list are kept, but also hidden if they do 
 * not fall into the range.
 *
 * @author phwhitin
 *
 * @param <T>
 * @param <C>
 */
public class RangedArrayList<C extends Comparable> extends AbstractRangedList<C> {

	public RangedArrayList() {
		super(new ArrayList<C>());
	}

	public RangedArrayList(final List<C> data) {
		super(data);
	}

	@Override
	public boolean add(final C c) {
		return range.contains(c) ? data.add(c) && limitedData.add(c)
				: data.add(c);
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
	public C set(int index, C element) {
		C v = data.set(index, element);
		reassess();
		return v;
	}

}
