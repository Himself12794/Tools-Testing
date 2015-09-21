package com.pwhiting.util;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.sun.istack.internal.Nullable;

public final class RandomUtils {

	private RandomUtils() {}
	
	@SafeVarargs
	public static <T extends IWeightedItem> T selectRandomWeightedItem(T...items) {
		return selectRandomWeightedItem(null, items);
	}
	
	@SafeVarargs
	public static <T extends IWeightedItem> T selectRandomWeightedItem(T...items) {
		return selectRandomWeightedItem(rand, Lists.newArrayList(items));
	}

	/**
	 * Selects a random weighted item out of the collection. Returns null is
	 * collection is empty, or all items have a weight of null.
	 * 
	 * @param items
	 * @return
	 */
	public static <T extends IWeightedItem> T selectRandomWeightedItem(Collection<T> items) {
		return selectRandomWeightedItem(null, items);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T extends IWeightedItem> T selectRandomWeightedItem(Collection<T> items) {

		if (rand == null) rand = new Random();

		float totalWeight = 0.0F;
		Map<Range, T> ranges = Maps.newHashMap();

		for (T item : items) {
			
			if (item.getWeight() > 0.0F) {
				float temp = totalWeight + item.getWeight();
				ranges.put(
						Range.closedOpen(totalWeight,
							temp ), item);
				totalWeight = temp;
			}
			
		}

		float choice = rand.nextFloat() * totalWeight;

		for (Entry<Range, T> entry : ranges.entrySet()) {

			if (entry.getKey().contains(choice)) {
				return entry.getValue();
			}

		}

		return null;
	}
	
	public static interface IWeightedItem {
		
		float getWeight();

	}

}
