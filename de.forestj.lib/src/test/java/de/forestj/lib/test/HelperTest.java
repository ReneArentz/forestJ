package de.forestj.lib.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HelperTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testHelper() {
		assertTrue(
				de.forestj.lib.Helper.isStringEmpty(null),
				"null is not string empty"
		);
		assertTrue(
				de.forestj.lib.Helper.isStringEmpty(""),
				"empty string is not string empty"
		);
		assertTrue(
				de.forestj.lib.Helper.isStringEmpty("  "),
				"two white spaces is not string empty"
		);
		assertFalse(
				de.forestj.lib.Helper.isStringEmpty("notEmpty"),
				"'notEmpty' is string empty"
		);
		
		assertTrue(
				de.forestj.lib.Helper.isString("test"),
				"'test' is not string"
		);
		assertFalse(
				de.forestj.lib.Helper.isString(123),
				"123 is a string"
		);
	
		assertFalse(
				de.forestj.lib.Helper.isShort("test"),
				"'test' is a short"
		);
		assertTrue(
				de.forestj.lib.Helper.isShort("123"),
				"123 is not a short"
		);
		
		assertFalse(
				de.forestj.lib.Helper.isInteger("test"),
				"'test' is an integer"
		);
		assertTrue(
				de.forestj.lib.Helper.isInteger("123550"),
				"1234550 is not an integer"
		);
		
		assertFalse(
				de.forestj.lib.Helper.isLong("test"),
				"'test' is a long"
		);
		assertTrue(
				de.forestj.lib.Helper.isLong("1235464545464654550"),
				"1235464545464654550 is not a long"
		);
		
		assertFalse(
				de.forestj.lib.Helper.isFloat("test"),
				"'test' is a float"
		);
		assertTrue(
				de.forestj.lib.Helper.isFloat("1235464.454644545464654550"),
				"1235464.454644545464654550 is no a float"
		);
		
		assertFalse(
				de.forestj.lib.Helper.isDouble("test"),
				"'test' is a double"
		);
		assertTrue(
				de.forestj.lib.Helper.isDouble("12354645.45446445464654550"),
				"12354645.45446445464654550 is not a double"
		);
		
		assertFalse(
				de.forestj.lib.Helper.isBoolean("1"),
				"'1' is boolean true"
		);
		assertTrue(
				de.forestj.lib.Helper.isBoolean("true"),
				"'true' is not boolean true"
		);
		
		assertFalse(
				de.forestj.lib.Helper.matchesRegex("123e4567-e89b-12Q3-a456-426614174000", "[a-f0-9\\-]*"),
				"'123e4567-e89b-12___Q___3-a456-426614174000' matches regex '[a-f0-9\\-]*'"
		);
		assertTrue(
				de.forestj.lib.Helper.matchesRegex("123e4567-e89b-12d3-a456-426614174000", "[a-f0-9\\-]*"),
				"'123e4567-e89b-12d3-a456-426614174000' not matches regex '[a-f0-9\\-]*'"
		);
		
		assertFalse(
				de.forestj.lib.Helper.countSubStrings("HelloabcdefgHelloabcdefgHelloHello", "Hallo") == 4,
				"'Hallo' is 4 times in 'HelloabcdefgHelloabcdefgHelloHello'"
		);
		
		assertTrue(
				de.forestj.lib.Helper.countSubStrings("HelloabcdefgHelloabcdefgHelloHello", "Hello") == 4,
				"'Hello' is not 4 times in 'HelloabcdefgHelloabcdefgHelloHello'"
		);
		
		assertTrue(
				de.forestj.lib.Helper.isDate("31-03-2020"),
				"'31-03-2020' is not a date"
		);
		assertFalse(
				de.forestj.lib.Helper.isDate("03-31-2020"),
				"'03-31-2020' is a date"
		);
		assertTrue(
				de.forestj.lib.Helper.isDate("31.03.2020"),
				"'31.03.2020' is not a date"
		);
		assertFalse(
				de.forestj.lib.Helper.isDate("03.31.2020"),
				"'03.31.2020' is a date"
		);
		assertTrue(
				de.forestj.lib.Helper.isDate("31/03/2020"),
				"'31/03/2020' is not a date"
		);
		assertFalse(
				de.forestj.lib.Helper.isDate("31/13/2020"),
				"'31/13/2020' is a date"
		);
		assertTrue(
				de.forestj.lib.Helper.isDate("2020/03/31"),
				"'2020/03/31' is not a date"
		);
		assertFalse(
				de.forestj.lib.Helper.isDate("2020/13/31"),
				"'2020/13/31' is a date"
		);
		assertTrue(
				de.forestj.lib.Helper.isDate("2020-03-31"),
				"'2020-03-31' is not a date"
		);
		assertFalse(
				de.forestj.lib.Helper.isDate("2020-31-03"),
				"'2020-31-03' is a date"
		);
		assertTrue(
				de.forestj.lib.Helper.isDate("03/31/2020"),
				"'03/31/2020' is not a date"
		);
		assertFalse(
				de.forestj.lib.Helper.isDate("13/31/2020"),
				"'13/31/2020' is a date"
		);
		assertTrue(
				de.forestj.lib.Helper.isDate("2020/31/03"),
				"'2020/31/03' is not a date"
		);
		assertFalse(
				de.forestj.lib.Helper.isDate("2020/31/13"),
				"'2020/31/13' is a date"
		);
		
		assertFalse(
				de.forestj.lib.Helper.isTime("53:45:32"),
				"'53:45:32' is a time"
		);
		assertTrue(
				de.forestj.lib.Helper.isTime("13:45:32"),
				"'13:45:32' is not a time"
		);

		String[] a_testTrue = new String[] {
			"31-03-2020 13:45:32",
			"31-03-2020T13:45:32",
			"31-03-2020 13:45:32Z",
			"31-03-2020T13:45:32Z",
			"31-03-2020 13:45",
			"31.03.2020 13:45:32",
			"31.03.2020 13:45",
			"31/03/2020 13:45:32",
			"31/03/2020 13:45",
			"2020/03/31 13:45:32",
			"2020/03/31 13:45",
			"2020-03-31 13:45:32",
			"2020-03-31 13:45",
			"03/31/2020 13:45:32",
			"03/31/2020 13:45",
			"2020/31/03 13:45:32",
			"2020/31/03 13:45"
		};
		
		for (String s_testTrue : a_testTrue) {
			assertTrue(
					de.forestj.lib.Helper.isDateTime(s_testTrue),
					"'" + s_testTrue + "' is not a date time"
			);
		}
		
		String[] a_testFalse = new String[] {
			"31-03-202013:45:32",
			"03-31-2020 13:45:32",
			"03-31-2020 13:45",
			"31-03-2020 53:45:32",
			"03.31.2020 13:45:32",
			"03.31.2020 13:45",
			"31.03.2020 53:45:32",
			"31/13/2020 13:45:32",
			"31/13/2020 13:45",
			"31/03/2020 53:45:32",
			"2020/13/31 13:45:32",
			"2020/13/31 13:45",
			"2020/03/31 53:45:32",
			"2020-31-03 13:45:32",
			"2020-31-03 13:45",
			"2020-03-31 53:45:32",
			"13/31/2020 13:45:32",
			"13/31/2020 13:45",
			"03/31/2020 53:45:32",
			"2020/31/13 13:45:32",
			"2020/31/13 13:45",
			"2020/31/03 53:45:32",
		};
		
		for (String s_testFalse : a_testFalse) {
			assertFalse(
					de.forestj.lib.Helper.isDateTime(s_testFalse),
					"'" + s_testFalse + "' is a date time"
			);
		}
				
		assertFalse(
				de.forestj.lib.Helper.isDateInterval("P2DQ6Y"),
				"'P2DQ6Y' is a date interval"
		);
		assertTrue(
				de.forestj.lib.Helper.isDateInterval("P4Y"),
				"'P4Y' is not a date interval"
		);
		assertTrue(
				de.forestj.lib.Helper.isDateInterval("PT15S"),
				"'PT15S' is not a date interval"
		);
		assertTrue(
				de.forestj.lib.Helper.isDateInterval("P2DT2H3M55S"),
				"'P2DT2H3M55S' is not a date interval"
		);
		
		try {
			boolean b_isDaySavingTime = java.time.ZonedDateTime.now( java.time.ZoneId.of( "Europe/Berlin" ) ).getZone().getRules().isDaylightSavings( java.time.ZonedDateTime.now( java.time.ZoneId.of( "Europe/Berlin" ) ).toInstant() );
			
			java.time.LocalDateTime o_localDateTime = java.time.LocalDateTime.of(2020, 03, 14, 06, 02, 03).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime();
			assertEquals(
					"2020-03-14T05:02:03Z",
					de.forestj.lib.Helper.toISO8601UTC(o_localDateTime),
					"local date time object[" + de.forestj.lib.Helper.toISO8601UTC(o_localDateTime) + "] is not equal with toISO8601UTC to '2020-03-14T05:02:03Z'"
			);
			assertFalse(
					"2030-03-14T05:02:03Z".contentEquals(de.forestj.lib.Helper.toISO8601UTC(o_localDateTime)),
					"local date time object[" + de.forestj.lib.Helper.toISO8601UTC(o_localDateTime) + "] is equal with toISO8601UTC to '2030-03-14T05:02:03Z'"
			);
			
			assertEquals(
					"Sat, 14 Mar 2020 05:02:03 GMT",
					de.forestj.lib.Helper.toRFC1123(o_localDateTime),
					"local date time object[" + de.forestj.lib.Helper.toRFC1123(o_localDateTime) + "] is not equal with toRFC1123 to 'Sat, 14 Mar 2020 05:02:03 GMT'"
			);
			assertFalse(
					"Sat, 14 Mar 2030 05:02:03 GMT".contentEquals(de.forestj.lib.Helper.toRFC1123(o_localDateTime)),
					"local date time object[" + de.forestj.lib.Helper.toRFC1123(o_localDateTime) + "] is equal with toRFC1123 to 'Sat, 14 Mar 2030 05:02:03 GMT'"
			);
			
			if (b_isDaySavingTime) {
				o_localDateTime = o_localDateTime.minusHours(1);
			}
			
			assertEquals(
					"2020-03-14T06:02:03",
					de.forestj.lib.Helper.toDateTimeString(o_localDateTime),
					"local date time object[" + de.forestj.lib.Helper.toDateTimeString(o_localDateTime) + "] is not equal with toDateTimeString to '2020-03-14T06:02:03'"
			);
			assertFalse(
					"2030-03-14T06:02:03".contentEquals(de.forestj.lib.Helper.toDateTimeString(o_localDateTime)),
					"local date time object[" + de.forestj.lib.Helper.toDateTimeString(o_localDateTime) + "] is equal with toDateTimeString to '2030-03-14T06:02:03'"
			);
			
			o_localDateTime = java.time.LocalDateTime.of(2020, 03, 14, 06, 02).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime();
			assertEquals(
					"2020-03-14T05:02:00Z",
					de.forestj.lib.Helper.toISO8601UTC(o_localDateTime),
					"local date time object[" + de.forestj.lib.Helper.toISO8601UTC(o_localDateTime) + "] is not equal with toISO8601UTC to '2020-03-14T05:02:00Z'"
			);
			assertFalse(
					"2030-03-14T05:02:00Z".contentEquals(de.forestj.lib.Helper.toISO8601UTC(o_localDateTime)),
					"local date time object[" + de.forestj.lib.Helper.toISO8601UTC(o_localDateTime) + "] is equal with toISO8601UTC to '2030-03-14T05:02:00Z'"
			);
			
			assertEquals(
					"Sat, 14 Mar 2020 05:02:00 GMT",
					de.forestj.lib.Helper.toRFC1123(o_localDateTime),
					"local date time object[" + de.forestj.lib.Helper.toRFC1123(o_localDateTime) + "] is not equal with toRFC1123 to 'Sat, 14 Mar 2020 05:02:00 GMT'"
			);
			assertFalse(
					"Sat, 14 Mar 2030 05:02:00 GMT".contentEquals(de.forestj.lib.Helper.toRFC1123(o_localDateTime)),
					"local date time object[" + de.forestj.lib.Helper.toRFC1123(o_localDateTime) + "] is equal with toRFC1123 to 'Sat, 14 Mar 2030 05:02:00 GMT'"
			);
			
			if (b_isDaySavingTime) {
				o_localDateTime = o_localDateTime.minusHours(1);
			}
			
			assertEquals(
					"2020-03-14T06:02:00",
					de.forestj.lib.Helper.toDateTimeString(o_localDateTime),
					"local date time object[" + de.forestj.lib.Helper.toDateTimeString(o_localDateTime) + "] is not equal with toDateTimeString to '2020-03-14T06:02:00'"
			);
			assertFalse(
					"2030-03-14T06:02:00".contentEquals(de.forestj.lib.Helper.toDateTimeString(o_localDateTime)),
					"local date time object[" + de.forestj.lib.Helper.toDateTimeString(o_localDateTime) + "] is equal with toDateTimeString to '2030-03-14T06:02:00'"
			);
		} catch (java.time.DateTimeException o_exc) {
			fail("Could not parse local date time object to '2020-03-14 06:02[:03]': " + o_exc.getMessage());
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
		
		try {
			java.time.LocalDateTime[] a_validLocalDateTime = new java.time.LocalDateTime[] {
				java.time.LocalDateTime.of(2020, 03, 14, 06, 02).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime(),
				java.time.LocalDateTime.of(2020, 03, 14, 06, 02).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime(),
				java.time.LocalDateTime.of(2020, 03, 14, 06, 02, 03).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime(),
				java.time.LocalDateTime.of(2020, 03, 14, 06, 02, 03).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime(),
				java.time.LocalDateTime.of(2020, 03, 14, 06, 02, 03).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime()
			};
			
			a_testTrue = new String[] {
				"14-03-2020 05:02",
				"14-03-2020T05:02",
				"14-03-2020 05:02:03",
				"14-03-2020T05:02:03",
				"14-03-2020T05:02:03Z",
				"14.03.2020 05:02",
				"14.03.2020T05:02",
				"14.03.2020 05:02:03",
				"14.03.2020T05:02:03",
				"14.03.2020T05:02:03Z",
				"14/03/2020 05:02",
				"14/03/2020T05:02",
				"14/03/2020 05:02:03",
				"14/03/2020T05:02:03",
				"14/03/2020T05:02:03Z",
				"03/14/2020 05:02",
				"03/14/2020T05:02",
				"03/14/2020 05:02:03",
				"03/14/2020T05:02:03",
				"03/14/2020T05:02:03Z",
				"2020-03-14 05:02",
				"2020-03-14T05:02",
				"2020-03-14 05:02:03",
				"2020-03-14T05:02:03",
				"2020-03-14T05:02:03Z",
				"2020/03/14 05:02",
				"2020/03/14T05:02",
				"2020/03/14 05:02:03",
				"2020/03/14T05:02:03",
				"2020/03/14T05:02:03Z",
				"2020/14/03 05:02",
				"2020/14/03T05:02",
				"2020/14/03 05:02:03",
				"2020/14/03T05:02:03",
				"2020/14/03T05:02:03Z"
			};
			
			int i = 0;
			
			for (String s_testTrue : a_testTrue) {
				assertEquals(
						a_validLocalDateTime[i],
						de.forestj.lib.Helper.fromISO8601UTC(s_testTrue),
						"'" + s_testTrue + "' fromISO8601UTC() is not equal local date time object '" + a_validLocalDateTime[i] + "'"
				);
				
				assertEquals(
						a_validLocalDateTime[i].minusHours(1),
						de.forestj.lib.Helper.fromDateTimeString(s_testTrue),
						"'" + s_testTrue + "' fromDateTimeString() is not equal local date time object '" + a_validLocalDateTime[i] + "'"
				);
				
				if (i == 4) {
					i = 0;
				} else {
					i++;
				}
			}
			
			java.time.LocalDate[] o_validLocalDates = new java.time.LocalDate[] {
				java.time.LocalDate.of(2020, 03, 14),
				java.time.LocalDate.of(2020, 03, 14),
				java.time.LocalDate.of(2020, 03, 14),
				java.time.LocalDate.of(2020, 03, 14),
				java.time.LocalDate.of(2020, 03, 14)
			};
			
			String[] a_testTrue2nd = new String[] {
				"14-03-2020",
				"14-03-2020",
				"14-03-2020",
				"14-03-2020",
				"14-03-2020",
				"14.03.2020",
				"14.03.2020",
				"14.03.2020",
				"14.03.2020",
				"14.03.2020",
				"14/03/2020",
				"14/03/2020",
				"14/03/2020",
				"14/03/2020",
				"14/03/2020",
				"03/14/2020",
				"03/14/2020",
				"03/14/2020",
				"03/14/2020",
				"03/14/2020",
				"2020-03-14",
				"2020-03-14",
				"2020-03-14",
				"2020-03-14",
				"2020-03-14",
				"2020/03/14",
				"2020/03/14",
				"2020/03/14",
				"2020/03/14",
				"2020/03/14",
				"2020/14/03",
				"2020/14/03",
				"2020/14/03",
				"2020/14/03",
				"2020/14/03"
			};
			
			i = 0;
			
			for (String s_testTrue : a_testTrue2nd) {
				assertEquals(
						o_validLocalDates[i],
						de.forestj.lib.Helper.fromDateString(s_testTrue),
						"'" + s_testTrue + "' fromDateString() is not equal local date time object '" + o_validLocalDates[i] + "'"
				);
				
				if (i == 4) {
					i = 0;
				} else {
					i++;
				}
			}
			
			java.time.LocalTime[] o_validLocalTimes = new java.time.LocalTime[] {
				java.time.LocalTime.of(05, 02),
				java.time.LocalTime.of(05, 02),
				java.time.LocalTime.of(05, 02, 03),
				java.time.LocalTime.of(05, 02, 03),
				java.time.LocalTime.of(05, 02, 03)
			};
			
			String[] a_testTrue3rd = new String[] {
				"05:02",
				"05:02",
				"05:02:03",
				"05:02:03",
				"05:02:03",
				"05:02",
				"05:02",
				"05:02:03",
				"05:02:03",
				"05:02:03",
				"05:02",
				"05:02",
				"05:02:03",
				"05:02:03",
				"05:02:03",
				"05:02",
				"05:02",
				"05:02:03",
				"05:02:03",
				"05:02:03",
				"05:02",
				"05:02",
				"05:02:03",
				"05:02:03",
				"05:02:03",
				"05:02",
				"05:02",
				"05:02:03",
				"05:02:03",
				"05:02:03",
				"05:02",
				"05:02",
				"05:02:03",
				"05:02:03",
				"05:02:03"
			};
			
			i = 0;
			
			for (String s_testTrue : a_testTrue3rd) {
				assertEquals(
						o_validLocalTimes[i],
						de.forestj.lib.Helper.fromTimeString(s_testTrue),
						"'" + s_testTrue + "' fromTimeString() is not equal local date time object '" + o_validLocalTimes[i] + "'"
				);
				
				if (i == 4) {
					i = 0;
				} else {
					i++;
				}
			}
			
			a_validLocalDateTime = new java.time.LocalDateTime[] {
				java.time.LocalDateTime.of(2020, 03, 14, 06, 02).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime(),
				java.time.LocalDateTime.of(2020, 03, 14, 06, 02).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime(),
				java.time.LocalDateTime.of(2020, 03, 14, 06, 02, 03).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime(),
				java.time.LocalDateTime.of(2020, 03, 14, 06, 02, 03).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime(),
				java.time.LocalDateTime.of(2020, 03, 14, 06, 02, 03).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime()
			};
			
			a_testFalse = new String[] {
				"03-14-2020 05:02",
				"03-14-2020T05:02",
				"03-14-2020 05:02:03",
				"03-14-2020T05:02:03",
				"03-14-2020T05:02:03Z",
				"14-03-2020 55:02",
				"14-03-2020T55:02",
				"14-03-2020 55:02:03",
				"14-03-2020T55:02:03",
				"14-03-2020T55:02:03Z",
				
				"03.14.2020 05:02",
				"03.14.2020T05:02",
				"03.14.2020 05:02:03",
				"03.14.2020T05:02:03",
				"03.14.2020T05:02:03Z",
				"14.03.2020 55:02",
				"14.03.2020T55:02",
				"14.03.2020 55:02:03",
				"14.03.2020T55:02:03",
				"14.03.2020T55:02:03Z",
								
				"14/13/2020 05:02",
				"14/13/2020T05:02",
				"14/13/2020 05:02:03",
				"14/13/2020T05:02:03",
				"14/13/2020T05:02:03Z",
				"14/03/2020 55:02",
				"14/03/2020T55:02",
				"14/03/2020 55:02:03",
				"14/03/2020T55:02:03",
				"14/03/2020T55:02:03Z",
				
				"13/14/2020 05:02",
				"13/14/2020T05:02",
				"13/14/2020 05:02:03",
				"13/14/2020T05:02:03",
				"13/14/2020T05:02:03Z",
				"03/14/2020 55:02",
				"03/14/2020T55:02",
				"03/14/2020 55:02:03",
				"03/14/2020T55:02:03",
				"03/14/2020T55:02:03Z",
				
				"2020-14-03 05:02",
				"2020-14-03T05:02",
				"2020-14-03 05:02:03",
				"2020-14-03T05:02:03",
				"2020-14-03T05:02:03Z",
				"2020-03-14 55:02",
				"2020-03-14T55:02",
				"2020-03-14 55:02:03",
				"2020-03-14T55:02:03",
				"2020-03-14T55:02:03Z",
				
				"2020/13/14 05:02",
				"2020/13/14T05:02",
				"2020/13/14 05:02:03",
				"2020/13/14T05:02:03",
				"2020/13/14T05:02:03Z",
				"2020/03/14 55:02",
				"2020/03/14T55:02",
				"2020/03/14 55:02:03",
				"2020/03/14T55:02:03",
				"2020/03/14T55:02:03Z",
				
				"2020/14/13 05:02",
				"2020/14/13T05:02",
				"2020/14/13 05:02:03",
				"2020/14/13T05:02:03",
				"2020/14/13T05:02:03Z",
				"2020/14/03 55:02",
				"2020/14/03T55:02",
				"2020/14/03 55:02:03",
				"2020/14/03T55:02:03",
				"2020/14/03T55:02:03Z"
			};
			
			i = 0;
			
			for (String s_testFalse : a_testFalse) {
				boolean b_check = true;
				
				try {
					de.forestj.lib.Helper.fromISO8601UTC(s_testFalse);
				} catch (Exception o_exc) {
					b_check = false;
				}
				
				if (b_check) {
					assertTrue(
							false,
							"'" + s_testFalse + "' fromISO8601UTC() could be parsed to local date time object '" + a_validLocalDateTime[i] + "'"
					);
				}
				
				try {
					de.forestj.lib.Helper.fromDateTimeString(s_testFalse);
				} catch (Exception o_exc) {
					b_check = false;
				}
				
				if (b_check) {
					assertTrue(
							false,
							"'" + s_testFalse + "' fromDateTimeString() could be parsed to local date time object '" + a_validLocalDateTime[i] + "'"
					);
				}
				
				try {
					de.forestj.lib.Helper.fromDateString(s_testFalse);
				} catch (Exception o_exc) {
					b_check = false;
				}
				
				if (b_check) {
					assertTrue(
							false,
							"'" + s_testFalse + "' fromDateString() could be parsed to local date object '" + a_validLocalDateTime[i] + "'"
					);
				}
				
				try {
					de.forestj.lib.Helper.fromTimeString(s_testFalse);
				} catch (Exception o_exc) {
					b_check = false;
				}
				
				if (b_check) {
					assertTrue(
							false,
							"'" + s_testFalse + "' fromTimeString() could be parsed to local time object '" + a_validLocalDateTime[i] + "'"
					);
				}
				
				if (i == 4) {
					i = 0;
				} else {
					i++;
				}
			}
		} catch (java.time.DateTimeException o_exc) {
			fail("Could not parse ISO8601UTC string to a local date time object: " + o_exc.getMessage());
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}

		try {
			java.util.Date o_date = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("14.03.2020 06:02:03");
			assertEquals(
					"2020-03-14T05:02:03Z",
					de.forestj.lib.Helper.utilDateToISO8601UTC(o_date),
					"local date time object[" + de.forestj.lib.Helper.utilDateToISO8601UTC(o_date) + "] is not equal with toISO8601UTC to '2020-03-14T05:02:03Z'"
			);
			assertFalse(
					"2030-03-14T05:02:03Z".contentEquals(de.forestj.lib.Helper.utilDateToISO8601UTC(o_date)),
					"local date time object[" + de.forestj.lib.Helper.utilDateToISO8601UTC(o_date) + "] is equal with toISO8601UTC to '2030-03-14T05:02:03Z'"
			);
			
			assertEquals(
					"2020-03-14T06:02:03",
					de.forestj.lib.Helper.utilDateToDateTimeString(o_date),
					"local date time object[" + de.forestj.lib.Helper.utilDateToDateTimeString(o_date) + "] is not equal with utilDateToDateTimeString to '2020-03-14T06:02:03Z'"
			);
			assertFalse(
					"2030-03-14T06:02:03".contentEquals(de.forestj.lib.Helper.utilDateToDateTimeString(o_date)),
					"local date time object[" + de.forestj.lib.Helper.utilDateToDateTimeString(o_date) + "] is equal with utilDateToDateTimeString to '2030-03-14T06:02:03'"
			);
		} catch (java.text.ParseException o_exc) {
			fail("Could not parse java util date object to '2020-03-14 06:02[:03]': " + o_exc.getMessage());
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
		
		try {
			String s_date = "2020-03-14T05:02:03Z";
			assertEquals(
					new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("14.03.2020 06:02:03"),
					de.forestj.lib.Helper.fromISO8601UTCToUtilDate(s_date),
					"fromISO8601UTCToUtilDate '2020-03-14T05:02:03Z' is not equal with java util date object[" + de.forestj.lib.Helper.fromISO8601UTCToUtilDate(s_date) + "]"
			);
			assertFalse(
					new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("14.03.2020 05:02:03").equals(de.forestj.lib.Helper.fromISO8601UTCToUtilDate(s_date)),
					"fromISO8601UTCToUtilDate '2020-03-14T05:02:03Z' is not equal with java util date object[" + de.forestj.lib.Helper.fromISO8601UTCToUtilDate(s_date) + "]"
			);
			
			s_date = s_date.substring(0, s_date.length() - 1);
			assertEquals(
					new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("14.03.2020 05:02:03"),
					de.forestj.lib.Helper.fromDateTimeStringToUtilDate(s_date),
					"fromDateTimeStringToUtilDate '2020-03-14T05:02:03' is not equal with java util date object[" + de.forestj.lib.Helper.fromDateTimeStringToUtilDate(s_date) + "]"
			);
			assertFalse(
					new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("14.03.2020 06:02:03").equals(de.forestj.lib.Helper.fromDateTimeStringToUtilDate(s_date)),
					"fromDateTimeStringToUtilDate '2020-03-14T05:02:03' is not equal with java util date object[" + de.forestj.lib.Helper.fromDateTimeStringToUtilDate(s_date) + "]"
			);
		} catch (java.text.ParseException o_exc) {
			fail("Could not parse '2020-03-14T05:02:03Z' to a java util date object: " + o_exc.getMessage());
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
		
		for (int i = 0; i < 1000; i++) {
			int i_random = de.forestj.lib.Helper.randomIntegerRange(1, 10);
			assertTrue(
					i_random >= 1 && i_random <= 10,
					"random integer is not between 1..10"
			);
			assertFalse(
					i_random < 1 && i_random > 10,
					"random integer is not between 1..10"
			);
		}
		
		for (int i = 0; i < 1000; i++) {
			double d_random = de.forestj.lib.Helper.randomDoubleRange(1.5, 10.75);
			assertTrue(
					Double.compare(d_random, 1.5) >= 0 && Double.compare(d_random, 10.75) < 1,
					"random double is not between 1.5 .. 10.75"
			);
			assertFalse(
					Double.compare(d_random, 1.5) < 0 && Double.compare(d_random, 10.75) >= 1,
					"random double is not between 1.5 .. 10.75"
			);
		}
		
		byte[] bytes = de.forestj.lib.Helper.shortToByteArray((short)558);
		short sh_test = de.forestj.lib.Helper.byteArrayToShort(bytes);
		assertTrue(
				sh_test == 558,
				"short to byte and back to short != 558"
		);
		assertFalse(
				sh_test == 131630,
				"short to byte and back to short == 131630"
		);
		
		assertEquals(
				"00000010 00101110",
				de.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of short are not matching printed byte array"
		);
		assertEquals(
				"00000000 00000000 00000010 00101110",
				de.forestj.lib.Helper.printByteArray(bytes, true),
				"bytes of short are not matching printed byte array"
		);
		
		bytes = de.forestj.lib.Helper.shortToByteArray((short)25134);
		sh_test = de.forestj.lib.Helper.byteArrayToShort(bytes);
		assertTrue(
				sh_test == 25134,
				"short to byte and back to short != 25134"
		);
		assertFalse(
				sh_test == 131630,
				"short to byte and back to short == 131630"
		);
		
		assertEquals(
				"01100010 00101110",
				de.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of short are not matching printed byte array"
		);
		assertEquals(
				"00000000 00000000 01100010 00101110",
				de.forestj.lib.Helper.printByteArray(bytes, true),
				"bytes of short are not matching printed byte array"
		);
		
		bytes = de.forestj.lib.Helper.intToByteArray(916040294);
		int i_test = de.forestj.lib.Helper.byteArrayToInt(bytes);
		assertTrue(
				i_test == 916040294,
				"int to byte and back to int != 916040294"
		);
		assertFalse(
				i_test == 116040294,
				"int to byte and back to int == 116040294"
		);
		
		assertEquals(
				"00110110 10011001 10101010 01100110",
				de.forestj.lib.Helper.printByteArray(bytes),
				"bytes of int are not matching printed byte array"
		);
		assertEquals(
				"00110110 10011001 10101010 01100110",
				de.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of int are not matching printed byte array"
		);
		
		bytes = de.forestj.lib.Helper.intToByteArray(10070630);
		i_test = de.forestj.lib.Helper.byteArrayToInt(bytes);		
		assertTrue(
				i_test == 10070630,
				"int to byte and back to int != 10070630"
		);
		assertFalse(
				i_test == 90070630,
				"int to byte and back to int == 90070630"
		);
		
		assertEquals(
				"00000000 10011001 10101010 01100110",
				de.forestj.lib.Helper.printByteArray(bytes),
				"bytes of int are not matching printed byte array"
		);
		assertEquals(
				"10011001 10101010 01100110",
				de.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of int are not matching printed byte array"
		);
				
		bytes = de.forestj.lib.Helper.intToByteArray(43622);
		i_test = de.forestj.lib.Helper.byteArrayToInt(bytes);
		assertTrue(
				i_test == 43622,
				"int to byte and back to int != 43622"
		);
		assertFalse(
				i_test == 13622,
				"int to byte and back to int == 13622"
		);
		
		assertEquals(
				"00000000 00000000 10101010 01100110",
				de.forestj.lib.Helper.printByteArray(bytes),
				"bytes of int are not matching printed byte array"
		);
		assertEquals(
				"10101010 01100110",
				de.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of int are not matching printed byte array"
		);
		
		bytes = de.forestj.lib.Helper.intToByteArray(102);
		i_test = de.forestj.lib.Helper.byteArrayToInt(bytes);
		assertTrue(
				i_test == 102,
				"int to byte and back to int != 102"
		);
		assertFalse(
				i_test == 902,
				"int to byte and back to int == 902"
		);
		
		assertEquals(
				"00000000 00000000 00000000 01100110",
				de.forestj.lib.Helper.printByteArray(bytes),
				"bytes of int are not matching printed byte array"
		);
		assertEquals(
				"01100110",
				de.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of int are not matching printed byte array"
		);
		
		
		
		bytes = de.forestj.lib.Helper.longToByteArray(9070052179665454l);
		long l_test = de.forestj.lib.Helper.byteArrayToLong(bytes);
		assertTrue(
				l_test == 9070052179665454l,
				"long to byte and back to long != 9070052179665454"
		);
		assertFalse(
				l_test == 4620756070607053358l,
				"long to byte and back to long == 4620756070607053358"
		);
		
		assertEquals(
				"00100000 00111001 00101010 00010110 01000011 01100010 00101110",
				de.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of long are not matching printed byte array"
		);
		
		bytes = de.forestj.lib.Helper.longToByteArray(3467834566000206382l);
		l_test = de.forestj.lib.Helper.byteArrayToLong(bytes);
		assertTrue(
				l_test == 3467834566000206382l,
				"long to byte and back to long != 3467834566000206382"
		);
		assertFalse(
				l_test == 4620756070607053358l,
				"long to byte and back to long == 4620756070607053358"
		);
		
		assertEquals(
				"00110000 00100000 00111001 00101010 00010110 01000011 01100010 00101110",
				de.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of long are not matching printed byte array"
		);
		
		bytes = de.forestj.lib.Helper.amountToNByteArray(101458034, 4);
		l_test = de.forestj.lib.Helper.byteArrayToLong(bytes);
		assertTrue(
				l_test == 101458034,
				"long to byte and back to long != 101458034"
		);
		assertFalse(
				l_test == 4620756070607053358l,
				"long to byte and back to long == 4620756070607053358"
		);
		
		assertEquals(
				"00000110 00001100 00100000 01110010",
				de.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of long are not matching printed byte array"
		);
		
		bytes = de.forestj.lib.Helper.amountToNByteArray(101458034, 8);
		l_test = de.forestj.lib.Helper.byteArrayToLong(bytes);
		assertTrue(
				l_test == 101458034,
				"long to byte and back to long != 101458034"
		);
		assertFalse(
				l_test == 4620756070607053358l,
				"long to byte and back to long == 4620756070607053358"
		);
		
		assertEquals(
				"00000000 00000000 00000000 00000000 00000110 00001100 00100000 01110010",
				de.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of long are not matching printed byte array"
		);
		
		assertEquals(
				"2,12 GB",
				de.forestj.lib.Helper.formatBytes(2123456797),
				"2123456797 is not 2,12 GB"
		);
		assertEquals(
				"1,98 GiB",
				de.forestj.lib.Helper.formatBytes(2123456797, true),
				"2123456797 is not 1,98 GiB"
		);
		
		assertFalse(
				de.forestj.lib.Helper.formatBytes(129456797).contentEquals("2,12 GB"),
				"129456797 is 2,12 GB"
		);
		assertFalse(
				de.forestj.lib.Helper.formatBytes(129456797, true).contentEquals("1,98 GiB"),
				"129456797 is 1,98 GiB"
		);
		
		assertEquals(
				"126,8 KB",
				de.forestj.lib.Helper.formatBytes(126797),
				"126797 is not 126,8 KB"
		);
		assertEquals(
				"123,83 KiB",
				de.forestj.lib.Helper.formatBytes(126797, true),
				"126797 is not 123,83 KiB"
		);
		
		assertFalse(
				de.forestj.lib.Helper.formatBytes(33126797).contentEquals("126,8 KB"),
				"33126797 is 126,8 KB"
		);
		assertFalse(
				de.forestj.lib.Helper.formatBytes(6797, true).contentEquals("123,83 KiB"),
				"6797 is 123,83 KiB"
		);
		
		try {
			assertEquals(
					"9f2778195bb08930f6455ca6c191d9dc25b77f33145141a2e89fac794d5e7c47",
					de.forestj.lib.Helper.hashByteArray("SHA-256", de.forestj.lib.Helper.intToByteArray(43622)),
					"SHA-256 ist not '9f2778195bb08930f6455ca6c191d9dc25b77f33145141a2e89fac794d5e7c47'"
			);
			assertEquals(
					"4dd7d9b43afd5ca29a794b59ee924f2226e7776c7d25e052060ab71dcd7254da9ff5c342f8e943d85336d7d97bad8cb9",
					de.forestj.lib.Helper.hashByteArray("SHA-384", de.forestj.lib.Helper.intToByteArray(10070630)),
					"SHA-384 ist not '4dd7d9b43afd5ca29a794b59ee924f2226e7776c7d25e052060ab71dcd7254da9ff5c342f8e943d85336d7d97bad8cb9'"
			);
			assertEquals(
					"38c320515e85995fc7acfefd5126eba8edb6133e6e552565899534d03e8af6d6ff9bb1c165c58bbb43aed8de01fdd3fb0c9f4f6d384ad8bcd419421ac10ab9c1",
					de.forestj.lib.Helper.hashByteArray("SHA-512", de.forestj.lib.Helper.intToByteArray(916040294)),
					"SHA-512 ist not '38c320515e85995fc7acfefd5126eba8edb6133e6e552565899534d03e8af6d6ff9bb1c165c58bbb43aed8de01fdd3fb0c9f4f6d384ad8bcd419421ac10ab9c1'"
			);
			
			assertFalse(
					de.forestj.lib.Helper.hashByteArray("SHA-256", de.forestj.lib.Helper.intToByteArray(43622)).contentEquals("9f2778195bb08930f6455ca6c191d9dc25c77f33145141a2e89fac794d5e7c47"),
					"SHA-256 is '9f2778195bb08930f6455ca6c191d9dc25c77f33145141a2e89fac794d5e7c47'"
			);
			assertFalse(
					de.forestj.lib.Helper.hashByteArray("SHA-384", de.forestj.lib.Helper.intToByteArray(10070630)).contentEquals("4dd7d9b43afd5ca29a794b59ee924f2226e7771c7d25e052060ab71dcd7254da9ff5c342f8e943d85336d7d97bad8cb9"),
					"SHA-384 is '4dd7d9b43afd5ca29a794b59ee924f2226e7771c7d25e052060ab71dcd7254da9ff5c342f8e943d85336d7d97bad8cb9'"
			);
			assertFalse(
					de.forestj.lib.Helper.hashByteArray("SHA-512", de.forestj.lib.Helper.intToByteArray(916040294)).contentEquals("38c320515e85995fc7acfefd5126eba8edb6133e6e552565899534d03e8af6d6ff9bb1c165c58bbb43aed8de01fdd3fb0c9fef6d384ad8bcd419421ac10ab9c1"),
					"SHA-512 is '38c320515e85995fc7acfefd5126eba8edb6133e6e552565899534d03e8af6d6ff9bb1c165c58bbb43aed8de01fdd3fb0c9fef6d384ad8bcd419421ac10ab9c1'"
			);
			
			assertEquals(
					"0x44 0x61 0x73 0x20 0x69 0x73 0x74 0x20 0x64 0x61 0x73 0x20 0x48 0x61 0x75 0x73 0x20 0x76 0x6f 0x6d 0x20 0x4e 0x69 0x6b 0x6f 0x6c 0x61 0x75 0x73 0x2e",
					de.forestj.lib.Helper.bytesToHexString("Das ist das Haus vom Nikolaus.".getBytes(), true),
					"BytesToHexString result for 'Das ist das Haus vom Nikolaus.' does not match expected value."
			);
			assertEquals(
					"0x41 0x42 0x43 0x44 0x45 0x46 0x47 0x48 0x49 0x4a 0x4b 0x4c 0x3f",
					de.forestj.lib.Helper.bytesToHexString("ABCDEFGHIJKL€".getBytes(java.nio.charset.Charset.forName("ISO-8859-1")), true),
					"BytesToHexString result for 'ABCDEFGHIJKL€' does not match expected value for charset 'ISO-8859-1'."
			);
			assertEquals(
					"0x41 0x42 0x43 0x44 0x45 0x46 0x47 0x48 0x49 0x4a 0x4b 0x4c 0xe2 0x82 0xac",
					de.forestj.lib.Helper.bytesToHexString("ABCDEFGHIJKL€".getBytes(java.nio.charset.Charset.forName("UTF-8")), true),
					"BytesToHexString result for 'ABCDEFGHIJKL€' does not match expected value for charset 'UTF-8'."
			);
			assertEquals(
					"0xfe 0xff 0x00 0x41 0x00 0x42 0x00 0x43 0x00 0x44 0x00 0x45 0x00 0x46 0x00 0x47 0x00 0x48 0x00 0x49 0x00 0x4a 0x00 0x4b 0x00 0x4c 0x20 0xac",
					de.forestj.lib.Helper.bytesToHexString("ABCDEFGHIJKL€".getBytes(java.nio.charset.Charset.forName("UTF-16")), true),
					"BytesToHexString result for 'ABCDEFGHIJKL€' does not match expected value for charset 'UTF-16'."
			);
			
			assertArrayEquals(
	                new byte[] { 0x44, 0x61, 0x73, 0x20, 0x69, 0x73, 0x74, 0x20, 0x64, 0x61, 0x73, 0x20, 0x48, 0x61, 0x75, 0x73, 0x20, 0x76 },
	                de.forestj.lib.Helper.hexStringToBytes("0x44 0x61 0x73 0x20 0x69 0x73 0x74 0x20 0x64 0x61 0x73 0x20 0x48 0x61 0x75 0x73 0x20 0x76"),
	                "HexStringToBytes result for '0x44 0x61 0x73 0x20 0x69 0x73 0x74 0x20 0x64 0x61 0x73 0x20 0x48 0x61 0x75 0x73 0x20 0x76' does not match expected value."
            );
			assertArrayEquals(
					new byte[] { 0x44, 0x61, 0x73, 0x20, 0x69, 0x73, 0x74, 0x20, 0x64, 0x61, 0x73, 0x20, 0x48, 0x61, 0x75, 0x73, 0x20, 0x76 },
	                de.forestj.lib.Helper.hexStringToBytes("446173206973742064617320486175732076"),
	                "HexStringToBytes result for '446173206973742064617320486175732076' does not match expected value."
            );
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}

		java.util.List<String> a_list1 = java.util.Arrays.asList("a", "bc", "def", "ghij", "klmno");
		assertTrue(
				de.forestj.lib.Helper.joinList(a_list1, ',').contentEquals("a,bc,def,ghij,klmno"),
				"concated list'" + de.forestj.lib.Helper.joinList(a_list1, ',') + "' is not equal 'a,bc,def,ghij,klmno'"
		);
		
		java.util.List<Integer> a_list2 = java.util.Arrays.asList(1, 23, 45, 678, 910111213);
		assertTrue(
				de.forestj.lib.Helper.joinList(a_list2, ':').contentEquals("1:23:45:678:910111213"),
				"concated list'" + de.forestj.lib.Helper.joinList(a_list1, ':') + "' is not equal '1:23:45:678:910111213'"
		);
		
		assertTrue(
				de.forestj.lib.Helper.getIndexOfObjectInList(java.util.Arrays.asList("two", "one", "three"), "one") > 0,
				"'one' not found and no index returned from array list"
		);
		assertFalse(
				de.forestj.lib.Helper.getIndexOfObjectInList(java.util.Arrays.asList("two", "four", "three"), "one") > 0,
				"'one' found and index returned from array list"
		);

		assertTrue(
				de.forestj.lib.Helper.isIndexValid(java.util.Arrays.asList("two", "one", "three"), 2),
				"index '2' is not valid"
		);
		assertFalse(
				de.forestj.lib.Helper.isIndexValid(java.util.Arrays.asList("two", "four", "three"), 42),
				"index '42' is valid"
		);
		
		java.time.Duration o_duration = java.time.Duration.ofSeconds(597845641);
		assertTrue(
				de.forestj.lib.Helper.formatDuration(o_duration).contentEquals("2767:48:14"),
				"duration is not '2767:48:14'" 
		);
		assertFalse(
				de.forestj.lib.Helper.formatDuration(o_duration).contentEquals("767:18:14"),
				"duration is '767:48:14'"
		);
		
		String s_random = de.forestj.lib.Helper.generateRandomString(32);
		
		assertTrue(
				s_random.length() == 32,
				"random generated string has not a length of 32 characters" 
		);
		
		s_random = de.forestj.lib.Helper.generateRandomString(10, de.forestj.lib.Helper.DIGITS_CHARACTERS);
		
		assertTrue(
				s_random.length() == 10,
				"random generated string has not a length of 10 characters" 
		);
		
		for (int i = 0; i < s_random.length(); i++) {
			assertTrue(
					Character.isDigit(s_random.charAt(i)),
					"random generated string, only digits, has a character which is not a digit: " + s_random 
			);
		}
		
		s_random = de.forestj.lib.Helper.generateUUID();
		
		assertTrue(
				s_random.length() == 36,
				"random generated string has not a length of 36 characters" 
		);
		
		String s_original = "ftps://user:password@expample.com:21";
		String s_disguised = de.forestj.lib.Helper.disguiseSubstring(s_original, "//", "@", '*');
		
		assertTrue(
				s_disguised.contentEquals("ftps://*************@expample.com:21"),
				"disguised string is not 'ftps://*************@expample.com:21', but '" + s_disguised + "'" 
		);
		
		s_original = "This is just a \"test\"";
		s_disguised = de.forestj.lib.Helper.disguiseSubstring(s_original, "\"", "\"", '+');
		
		assertTrue(
				s_disguised.contentEquals("This is just a \"++++\""),
				"disguised string is not 'This is just a \"++++\"', but '" + s_disguised + "'" 
		);
		
		s_original = "No disguise at all";
		s_disguised = de.forestj.lib.Helper.disguiseSubstring(s_original, ".", "&", '_');
		
		assertTrue(
				s_disguised.contentEquals(s_original),
				"disguised string is not '" + s_original + "', but '" + s_disguised + "'" 
		);
		
		String[] a_ipAddresses = new String[] {
			"192.168.2.100",
			"157.166.224.26/10",
			"157.128.64.254",
			"157.166.224.26/10",
			"10.10.128.23",
			"207.4.228.64/27",
			"207.4.228.87",
			"207.4.228.64/27",
			"192.168.2.1",
			"152.107.20.137/21",
			"152.107.20.13",
			"152.107.20.137/21",
			"10.10.10.255",
			"3.184.239.36/17",
			"3.184.202.202",
			"3.184.239.36/17",
			"192.168.2.100",
			"192.168.2.100/24"
		};
		
		boolean[] a_ipAddressesResults = new boolean[] {
			false, true, false, true, false, true, false, true, true
		};
		
		for (int i = 0; i < a_ipAddressesResults.length; i++) {
			assertEquals(
				de.forestj.lib.Helper.isIpv4WithinRange(a_ipAddresses[i * 2], a_ipAddresses[(i * 2) + 1]),
				a_ipAddressesResults[i],
				"ip '" + a_ipAddresses[i * 2] + "' is " + ((!a_ipAddressesResults[i]) ? "not" : "") + " within '" + a_ipAddresses[(i * 2) + 1] + "'"
			);
		}
	}
}
