package com.pwhiting.util;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

import com.google.common.collect.Maps;
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

	}

}
