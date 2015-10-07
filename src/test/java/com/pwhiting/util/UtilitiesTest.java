package com.pwhiting.util;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.pwhiting.util.RandomUtils.IWeightedItem;
import com.pwhiting.util.lang.ClocData;
import com.pwhiting.util.lang.ClocData.Header;
import com.pwhiting.util.lang.ClocData.LangStats;
import com.pwhiting.util.lang.ClocService;
import com.pwhiting.util.lang.CodeSniffer.Language;

public class UtilitiesTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UtilitiesTest.class.getSimpleName());
	
	@Before
	public void config() {
		Util.setLoggingLevel(Level.DEBUG);
		ClocService.init();
	}
	
	@Test
	public void clocTest() {
		assertTrue(ClocService.canGetCLOCStats());
	}

	@Test
	public void testUtility() {

		final String test1 = "test1";
		final String test2 = "test2";

		final Map<String, Boolean> map = Maps.newHashMap();

		map.put(test1, true);

		assertTrue(Util.getOrDefault(map, test1, false));
		assertTrue(Util.getOrDefault(map, test2, true));
		assertTrue(Util.putIfAbsent(map, test1, false));
		assertTrue(Util.putIfAbsent(map, test2, true));

		final Map<Language, LangStats> map2 = Maps.newHashMap();

		final ClocData data = new ClocData(new Header(), map2);

		LOGGER.debug(data.toString());
		
		RangeLimitedDataContainer2<Integer> rldc = new RangeLimitedDataContainer2<Integer>();
		
		rldc.add(15);
		rldc.add(25);
		
		assertTrue(rldc.size() == 2);
		
		Range<Integer> range = Range.closed(10, 20);
		
		rldc.limitToRange(range);
		
		assertTrue(rldc.size() == 1);

	}
	
	@Test
	public void randomWeightedSelectionTest() {
		IWeightedItem w1 = new IWeightedItem() {

			@Override
			public float getWeight() {
				return 1.0F;
			}

			@Override
			public String toString() {
				return "w1";
			}

		};

		IWeightedItem w2 = new IWeightedItem() {

			@Override
			public float getWeight() {
				return 5.0F;
			}

			@Override
			public String toString() {
				return "w2";
			}

		};

		IWeightedItem w3 = new IWeightedItem() {

			@Override
			public float getWeight() {
				return 3.0F;
			}

			@Override
			public String toString() {
				return "w3";
			}

		};

		IWeightedItem w4 = new IWeightedItem() {

			@Override
			public float getWeight() {
				return 0.001F;
			}
			
			@Override
			public String toString() {
				return "w4";
			}

		};

		IWeightedItem w5 = new IWeightedItem() {

			@Override
			public float getWeight() {
				return 10.0F;
			}

			@Override
			public String toString() {
				return "w5";
			}

		};

		List<IWeightedItem> items = Lists.newArrayList(w1, w2, w3, w4, w5);
		Map<IWeightedItem, Integer> choices = Maps.newHashMap();
		int iters = 50000;

		Random rand = new Random(0L);
		
		for (int i = 0; i < iters; i++) {

			IWeightedItem selection = RandomUtils.selectRandomWeightedItem(rand, items);

			if (!choices.containsKey(selection)) {
				choices.put(selection, 1);
			} else {
				choices.put(selection, choices.get(selection) + 1);
			}

		}

		System.out.println("Out of " + iters + " iterations, Our choices are: ");

		for (Entry<IWeightedItem, Integer> entry : choices.entrySet()) {

			System.out.println(entry.getKey() + " was chosen "
					+ entry.getValue() + " times, with a weight of "
					+ entry.getKey().getWeight());

		}
		
		IWeightedItem item = null;
		int iterations = 0;
		
		while (item != w4) {
			item = RandomUtils.selectRandomWeightedItem(items);
			iterations++;
		}
		
		System.out.println("Took " + iterations + " iterations for " + item + " to be chosen");
	}

}
