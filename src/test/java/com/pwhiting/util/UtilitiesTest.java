package com.pwhiting.util;

import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.pwhiting.collect.LimitedArrayList;
import com.pwhiting.collect.RangedArrayList;
import com.pwhiting.collect.RangedList;
import com.pwhiting.game.ChessPiece;
import com.pwhiting.game.Gameboard;
import com.pwhiting.game.Gameboard.BoardPosition;
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
		
		RangedList<Integer> rl = new RangedArrayList<Integer>();
		
		rl.add(15);
		rl.add(25);
		
		assertTrue(rl.size() == 2);
		
		Range<Integer> range = Range.closed(10, 20);
		
		rl.limitToRange(range);
		
		assertTrue(rl.size() == 1);

	}
	
	@Test
	public void randomWeightedSelectionTest() {
		IWeightedItem w1 = createWeightedItem(1.0F, "w1");
		IWeightedItem w2 = createWeightedItem(5.0F, "w2");
		IWeightedItem w3 = createWeightedItem(3.0F, "w3");
		IWeightedItem w4 = createWeightedItem(0.001F, "w4");
		IWeightedItem w5 = createWeightedItem(10.0F, "w5");

		Map<IWeightedItem, Integer> choices = Maps.newHashMap();
		int iters = 50000;

		Random rand = new Random(0L);
		
		for (int i = 0; i < iters; i++) {

			IWeightedItem selection = RandomUtils.selectRandomWeightedItem(rand, w1, w2, w3, w4, w5);

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
			item = RandomUtils.selectRandomWeightedItem(w1, w2, w3, w4, w5);
			iterations++;
		}
		
		System.out.println("Took " + iterations + " iterations for " + item + " to be chosen");
	}
	
	@Test
	public void testLimitedList() {
		
		Predicate<Integer> filter = new Predicate<Integer>() {

			@Override
			public boolean apply(Integer input) {
				return input >= 1 && input <= 5;
			}
			
		};
		
		List<Integer> integers = Lists.newArrayList(Util.asIterable(Range.open(0, 15), 3));	
		List<Integer> commits = new LimitedArrayList<Integer>(integers, filter);
		
		for (int commit : commits) {
			assertTrue(filter.apply(commit));
		}
		
	}
	
	@Test
	public void gameTest() {
		
		Gameboard<ChessPiece> chessBoard = new Gameboard<ChessPiece>(8);
		
		chessBoard.setPieceAt(new BoardPosition<ChessPiece>(ChessPiece.KING, Color.BLACK), 	1, 1);
		
		assertTrue(chessBoard.getPieceAt(1, 1).getPiece() == ChessPiece.KING);
		
		for (BoardPosition<ChessPiece> bp : chessBoard) {
			System.out.println(bp);
		}
		
	}
	
	private IWeightedItem createWeightedItem(final float value, final String name) {
		return new IWeightedItem() {

			@Override
			public float getWeight() {
				return value;
			}

			@Override
			public String toString() {
				return name;
			}

		};
	}

}
