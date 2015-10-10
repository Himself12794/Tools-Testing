package com.pwhiting.collect;

import java.util.List;

import com.google.common.base.Predicate;

public class LimitedArrayList<E> extends AbstractLimitedList<E> {
	
	public LimitedArrayList() {
		super();
	}
	
	public LimitedArrayList(Predicate<? super E> filter) {
		super(filter);
	}
	
	public LimitedArrayList(List<E> list) {
		super(list);
	}
	
	public LimitedArrayList(List<E> list, Predicate<E> filter) {
		super(list, filter);
	}
	
	@Override
	public boolean isLimited() {
		return filter != DEFAULT_FILTER;
	}

}
