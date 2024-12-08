package de.forestj.lib.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DateIntervalTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testDateInterval() {
		de.forestj.lib.DateInterval o_dateInterval = null;
		
		try {
			o_dateInterval = new de.forestj.lib.DateInterval("P1Y2M3DT4H5M6S");
			
			assertTrue(
					o_dateInterval.toString().contentEquals("1 Jahr(e) 2 Monat(e) 3 Tag(e) 4 Stunde(n) 5 Minute(n) 6 Sekunde(n)"),
					"!= 1 Jahr(e) 2 Monat(e) 3 Tag(e) 4 Stunde(n) 5 Minute(n) 6 Sekunde(n)"
			);
			assertTrue(
					o_dateInterval.toDuration() == 273906000,
					"!= 273906000"
			);
			
			assertFalse(
					o_dateInterval.toString().contentEquals("1 Jahr 6 Sekund"),
					"== 1 Jahr 6 Sekund"
			);
			assertFalse(
					o_dateInterval.toDuration() == 1234,
					"== 1234"
			);
			
			o_dateInterval = new de.forestj.lib.DateInterval("P1Y2M3D");
			
			assertTrue(
					o_dateInterval.toString().contentEquals("1 Jahr(e) 2 Monat(e) 3 Tag(e)"),
					"!= 1 Jahr(e) 2 Monat(e) 3 Tag(e)"
			);
			assertTrue(
					o_dateInterval.toDuration() == 259200000,
					"!= 259200000"
			);
			
			assertFalse(
					o_dateInterval.toString().contentEquals("1 Jahr 6 Sekund"),
					"== 1 Jahr 6 Sekund"
			);
			assertFalse(
					o_dateInterval.toDuration() == 1234,
					"== 1234"
			);
			
			o_dateInterval = new de.forestj.lib.DateInterval("PT4H5M6S");
			
			assertTrue(
					o_dateInterval.toString().contentEquals("4 Stunde(n) 5 Minute(n) 6 Sekunde(n)"),
					"!= 4 Stunde(n) 5 Minute(n) 6 Sekunde(n)"
			);
			assertTrue(
					o_dateInterval.toDuration() == 14706000,
					"!= 14706000"
			);
			
			assertFalse(
					o_dateInterval.toString().contentEquals("1 Jahr 6 Sekund"),
					"== 1 Jahr 6 Sekund"
			);
			assertFalse(
					o_dateInterval.toDuration() == 1234,
					"== 1234"
			);
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
		
		try {
			o_dateInterval = new de.forestj.lib.DateInterval(null);
		} catch (IllegalArgumentException o_exc) {
			assertTrue(
					o_exc.getMessage().contentEquals("String encoded date interval parameter is null or empty."),
					"wrong exception"
			);
		}
		
		try {
			o_dateInterval = new de.forestj.lib.DateInterval("");
		} catch (IllegalArgumentException o_exc) {
			assertTrue(
					o_exc.getMessage().contentEquals("String encoded date interval parameter is null or empty."),
					"wrong exception"
			);
		}
		
		try {
			o_dateInterval = new de.forestj.lib.DateInterval("PT2Mq");
		} catch (IllegalArgumentException o_exc) {
			assertTrue(
					o_exc.getMessage().contentEquals("Parameter[PT2Mq] does not match date interval format."),
					"wrong exception"
			);
		}
		
		try {
			o_dateInterval = new de.forestj.lib.DateInterval("PT2Mq");
		} catch (IllegalArgumentException o_exc) {
			o_dateInterval.setDateInterval(new de.forestj.lib.DateInterval("PT2M"));

			assertTrue(
					o_dateInterval.toString().contentEquals("2 Minute(n)"),
					"!= 2 Minute(n)"
			);
			assertTrue(
					o_dateInterval.toDuration() == 120000,
					"!= 120000"
			);
			
			assertFalse(
					o_dateInterval.toString().contentEquals("1 Jahr 6 Sekund"),
					"== 1 Jahr 6 Sekund"
			);
			assertFalse(
					o_dateInterval.toDuration() == 1234,
					"== 1234"
			);
		}
		
		try {
			o_dateInterval = new de.forestj.lib.DateInterval("P1Y2M3DT4H5M6S");
			
			assertTrue(
					o_dateInterval.toString(java.util.Locale.ENGLISH).contentEquals("1 year(s) 2 month(s) 3 day(s) 4 hour(s) 5 minute(s) 6 second(s)"),
					"!= 1 year(s) 2 month(s) 3 day(s) 4 hour(s) 5 minute(s) 6 second(s)"
			);
			assertTrue(
					o_dateInterval.toDuration() == 273906000,
					"!= 273906000"
			);
			
			assertFalse(
					o_dateInterval.toString(java.util.Locale.ENGLISH).contentEquals("1 Jahr 6 Sekund"),
					"== 1 Jahr 6 Sekund"
			);
			assertFalse(
					o_dateInterval.toDuration() == 1234,
					"== 1234"
			);
			
			o_dateInterval = new de.forestj.lib.DateInterval("P1Y2M3D");
			
			assertTrue(
					o_dateInterval.toString(java.util.Locale.ENGLISH).contentEquals("1 year(s) 2 month(s) 3 day(s)"),
					"!= 1 year(s) 2 month(s) 3 day(s)"
			);
			assertTrue(
					o_dateInterval.toDuration() == 259200000,
					"!= 259200000"
			);
			
			assertFalse(
					o_dateInterval.toString(java.util.Locale.ENGLISH).contentEquals("1 Jahr 6 Sekund"),
					"== 1 Jahr 6 Sekund"
			);
			assertFalse(
					o_dateInterval.toDuration() == 1234,
					"== 1234"
			);
			
			o_dateInterval = new de.forestj.lib.DateInterval("PT4H5M6S");
			
			assertTrue(
					o_dateInterval.toString(java.util.Locale.ENGLISH).contentEquals("4 hour(s) 5 minute(s) 6 second(s)"),
					"!= 4 hour(s) 5 minute(s) 6 second(s)"
			);
			assertTrue(
					o_dateInterval.toDuration() == 14706000,
					"!= 14706000"
			);
			
			assertFalse(
					o_dateInterval.toString(java.util.Locale.ENGLISH).contentEquals("1 Jahr 6 Sekund"),
					"== 1 Jahr 6 Sekund"
			);
			assertFalse(
					o_dateInterval.toDuration() == 1234,
					"== 1234"
			);
		} catch (IllegalArgumentException o_exc) {
			fail(o_exc.getMessage());
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
		
		try {
			o_dateInterval = new de.forestj.lib.DateInterval("P1Y2M3DT4H5M6S", java.util.Locale.ENGLISH);
			
			assertTrue(
					o_dateInterval.toString().contentEquals("1 year(s) 2 month(s) 3 day(s) 4 hour(s) 5 minute(s) 6 second(s)"),
					"!= 1 year(s) 2 month(s) 3 day(s) 4 hour(s) 5 minute(s) 6 second(s)"
			);
			assertTrue(
					o_dateInterval.toDuration() == 273906000,
					"!= 273906000"
			);
			
			assertFalse(
					o_dateInterval.toString().contentEquals("1 Jahr 6 Sekund"),
					"== 1 Jahr 6 Sekund"
			);
			assertFalse(
					o_dateInterval.toDuration() == 1234,
					"== 1234"
			);
			
			o_dateInterval = new de.forestj.lib.DateInterval("P1Y2M3D", java.util.Locale.ENGLISH);
			
			assertTrue(
					o_dateInterval.toString().contentEquals("1 year(s) 2 month(s) 3 day(s)"),
					"!= 1 year(s) 2 month(s) 3 day(s)"
					);
			assertTrue(
					o_dateInterval.toDuration() == 259200000,
					"!= 259200000"
					);
			
			assertFalse(
					o_dateInterval.toString().contentEquals("1 Jahr 6 Sekund"),
					"== 1 Jahr 6 Sekund"
					);
			assertFalse(
					o_dateInterval.toDuration() == 1234,
					"== 1234"
					);
			
			o_dateInterval = new de.forestj.lib.DateInterval("PT4H5M6S", java.util.Locale.ENGLISH);
			
			assertTrue(
					o_dateInterval.toString().contentEquals("4 hour(s) 5 minute(s) 6 second(s)"),
					"!= 4 hour(s) 5 minute(s) 6 second(s)"
					);
			assertTrue(
					o_dateInterval.toDuration() == 14706000,
					"!= 14706000"
					);
			
			assertFalse(
					o_dateInterval.toString().contentEquals("1 Jahr 6 Sekund"),
					"== 1 Jahr 6 Sekund"
					);
			assertFalse(
					o_dateInterval.toDuration() == 1234,
					"== 1234"
					);
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
