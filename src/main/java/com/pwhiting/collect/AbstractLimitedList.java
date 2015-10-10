package com.pwhiting.collect;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

public abstract class AbstractLimitedList<E> implements LimitedList<E>, LimitedCollection<E> {
	
	static final Predicate DEFAULT_FILTER = Predicates.alwaysTrue();
	
	protected final List<E> data;
	
	protected Predicate<? super E> filter = DEFAULT_FILTER;
	
	AbstractLimitedList() {
		this(Lists.<E>newArrayList());
	}
	
	AbstractLimitedList(Predicate<? super E> filter) {
		this();
		setFilter(filter);
	}
	
	AbstractLimitedList(List<E> list) {
		data = list;
	}
	
	AbstractLimitedList(List<E> list, Predicate<? super E> filter) {
		this(filter);
		
		addAll(list);
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return data.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return data.iterator();
	}

	@Override
	public Object[] toArray() {
		return data.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return data.toArray(a);
	}

	@Override
	public boolean add(E e) {
		return filter.apply(e) ? data.add(e) : false;
	}

	@Override
	public boolean remove(Object o) {
		return data.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return data.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean hasResult = false;
		
		for (E item : c) {
			if (filter.apply(item)) {
				data.add(item);
				if (!hasResult) hasResult = true;
			}
		}
		
		return hasResult;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return data.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return data.retainAll(c);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFilter(Predicate<? super E> filter) {
		this.filter = filter;
	}

	@Override
	public void removeLimit() {
		this.filter = DEFAULT_FILTER;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		boolean hasResult = false;
		int start = index;
		for (E item : c) {
			if (filter.apply(item)) {
				try {
					data.add(start, item);
					if (!hasResult) hasResult = true;
					start++;
				} catch (Exception uoe ) {
					// We don't need to do anything
				}
			}
		}
		return hasResult;
	}

	@Override
	public E get(int index) {
		return data.get(index);
	}

	@Override
	public E set(int index, E element) {
		return filter.apply(element) ? data.set(index, element) : null;
	}

	@Override
	public void add(int index, E element) {
		if (filter.apply(element)) data.add(index, element);
	}

	@Override
	public E remove(int index) {
		return data.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return data.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return data.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return data.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return data.listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return data.subList(fromIndex, toIndex);
	}
	
	@Override
	public boolean equals(Object o) {
		return data.equals(o);
	}
	
	@Override
	public int hashCode() {
		return data.hashCode();
	}
	
}
