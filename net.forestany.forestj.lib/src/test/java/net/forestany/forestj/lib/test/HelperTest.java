package net.forestany.forestj.lib.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HelperTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testHelper() {
		assertTrue(
				net.forestany.forestj.lib.Helper.isStringEmpty(null),
				"null is not string empty"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.isStringEmpty(""),
				"empty string is not string empty"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.isStringEmpty("  "),
				"two white spaces is not string empty"
		);
		assertFalse(
				net.forestany.forestj.lib.Helper.isStringEmpty("notEmpty"),
				"'notEmpty' is string empty"
		);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.isString("test"),
				"'test' is not string"
		);
		assertFalse(
				net.forestany.forestj.lib.Helper.isString(123),
				"123 is a string"
		);
	
		assertFalse(
				net.forestany.forestj.lib.Helper.isShort("test"),
				"'test' is a short"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.isShort("123"),
				"123 is not a short"
		);
		
		assertFalse(
				net.forestany.forestj.lib.Helper.isInteger("test"),
				"'test' is an integer"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.isInteger("123550"),
				"1234550 is not an integer"
		);
		
		assertFalse(
				net.forestany.forestj.lib.Helper.isLong("test"),
				"'test' is a long"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.isLong("1235464545464654550"),
				"1235464545464654550 is not a long"
		);
		
		assertFalse(
				net.forestany.forestj.lib.Helper.isFloat("test"),
				"'test' is a float"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.isFloat("1235464.454644545464654550"),
				"1235464.454644545464654550 is no a float"
		);
		
		assertFalse(
				net.forestany.forestj.lib.Helper.isDouble("test"),
				"'test' is a double"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.isDouble("12354645.45446445464654550"),
				"12354645.45446445464654550 is not a double"
		);
		
		assertFalse(
				net.forestany.forestj.lib.Helper.isBoolean("1"),
				"'1' is boolean true"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.isBoolean("true"),
				"'true' is not boolean true"
		);
		
		assertFalse(
				net.forestany.forestj.lib.Helper.matchesRegex("123e4567-e89b-12Q3-a456-426614174000", "[a-f0-9\\-]*"),
				"'123e4567-e89b-12___Q___3-a456-426614174000' matches regex '[a-f0-9\\-]*'"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.matchesRegex("123e4567-e89b-12d3-a456-426614174000", "[a-f0-9\\-]*"),
				"'123e4567-e89b-12d3-a456-426614174000' not matches regex '[a-f0-9\\-]*'"
		);
		
		assertFalse(
				net.forestany.forestj.lib.Helper.countSubStrings("HelloabcdefgHelloabcdefgHelloHello", "Hallo") == 4,
				"'Hallo' is 4 times in 'HelloabcdefgHelloabcdefgHelloHello'"
		);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.countSubStrings("HelloabcdefgHelloabcdefgHelloHello", "Hello") == 4,
				"'Hello' is not 4 times in 'HelloabcdefgHelloabcdefgHelloHello'"
		);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.isDate("31-03-2020"),
				"'31-03-2020' is not a date"
		);
		assertFalse(
				net.forestany.forestj.lib.Helper.isDate("03-31-2020"),
				"'03-31-2020' is a date"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.isDate("31.03.2020"),
				"'31.03.2020' is not a date"
		);
		assertFalse(
				net.forestany.forestj.lib.Helper.isDate("03.31.2020"),
				"'03.31.2020' is a date"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.isDate("31/03/2020"),
				"'31/03/2020' is not a date"
		);
		assertFalse(
				net.forestany.forestj.lib.Helper.isDate("31/13/2020"),
				"'31/13/2020' is a date"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.isDate("2020/03/31"),
				"'2020/03/31' is not a date"
		);
		assertFalse(
				net.forestany.forestj.lib.Helper.isDate("2020/13/31"),
				"'2020/13/31' is a date"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.isDate("2020-03-31"),
				"'2020-03-31' is not a date"
		);
		assertFalse(
				net.forestany.forestj.lib.Helper.isDate("2020-31-03"),
				"'2020-31-03' is a date"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.isDate("03/31/2020"),
				"'03/31/2020' is not a date"
		);
		assertFalse(
				net.forestany.forestj.lib.Helper.isDate("13/31/2020"),
				"'13/31/2020' is a date"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.isDate("2020/31/03"),
				"'2020/31/03' is not a date"
		);
		assertFalse(
				net.forestany.forestj.lib.Helper.isDate("2020/31/13"),
				"'2020/31/13' is a date"
		);
		
		assertFalse(
				net.forestany.forestj.lib.Helper.isTime("53:45:32"),
				"'53:45:32' is a time"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.isTime("13:45:32"),
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
			"2020/31/03 13:45",
			"31-03-2020 13:45:32.576",
			"31-03-2020T13:45:32.576",
			"31-03-2020 13:45:32.576Z",
			"31-03-2020T13:45:32.576Z"
		};
		
		for (String s_testTrue : a_testTrue) {
			assertTrue(
					net.forestany.forestj.lib.Helper.isDateTime(s_testTrue),
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
			"31-03-2020 13:45:32.57",
			"31-03-2020T13:45:32.5764"
		};
		
		for (String s_testFalse : a_testFalse) {
			assertFalse(
					net.forestany.forestj.lib.Helper.isDateTime(s_testFalse),
					"'" + s_testFalse + "' is a date time"
			);
		}
				
		assertFalse(
				net.forestany.forestj.lib.Helper.isDateInterval("P2DQ6Y"),
				"'P2DQ6Y' is a date interval"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.isDateInterval("P4Y"),
				"'P4Y' is not a date interval"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.isDateInterval("PT15S"),
				"'PT15S' is not a date interval"
		);
		assertTrue(
				net.forestany.forestj.lib.Helper.isDateInterval("P2DT2H3M55S"),
				"'P2DT2H3M55S' is not a date interval"
		);
		
		try {
			boolean b_isDaySavingTime = java.time.ZonedDateTime.now( java.time.ZoneId.of( "Europe/Berlin" ) ).getZone().getRules().isDaylightSavings( java.time.ZonedDateTime.now( java.time.ZoneId.of( "Europe/Berlin" ) ).toInstant() );
			
			java.time.LocalDateTime o_localDateTime = java.time.LocalDateTime.of(2020, 03, 14, 06, 02, 03).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime();
			assertEquals(
					"2020-03-14T05:02:03Z",
					net.forestany.forestj.lib.Helper.toISO8601UTC(o_localDateTime),
					"local date time object[" + net.forestany.forestj.lib.Helper.toISO8601UTC(o_localDateTime) + "] is not equal with toISO8601UTC to '2020-03-14T05:02:03Z'"
			);
			assertFalse(
					"2030-03-14T05:02:03Z".contentEquals(net.forestany.forestj.lib.Helper.toISO8601UTC(o_localDateTime)),
					"local date time object[" + net.forestany.forestj.lib.Helper.toISO8601UTC(o_localDateTime) + "] is equal with toISO8601UTC to '2030-03-14T05:02:03Z'"
			);
			
			assertEquals(
					"Sat, 14 Mar 2020 05:02:03 GMT",
					net.forestany.forestj.lib.Helper.toRFC1123(o_localDateTime),
					"local date time object[" + net.forestany.forestj.lib.Helper.toRFC1123(o_localDateTime) + "] is not equal with toRFC1123 to 'Sat, 14 Mar 2020 05:02:03 GMT'"
			);
			assertFalse(
					"Sat, 14 Mar 2030 05:02:03 GMT".contentEquals(net.forestany.forestj.lib.Helper.toRFC1123(o_localDateTime)),
					"local date time object[" + net.forestany.forestj.lib.Helper.toRFC1123(o_localDateTime) + "] is equal with toRFC1123 to 'Sat, 14 Mar 2030 05:02:03 GMT'"
			);
			
			if (b_isDaySavingTime) {
				o_localDateTime = o_localDateTime.minusHours(1);
			}
			
			assertEquals(
					"2020-03-14T06:02:03",
					net.forestany.forestj.lib.Helper.toDateTimeString(o_localDateTime),
					"local date time object[" + net.forestany.forestj.lib.Helper.toDateTimeString(o_localDateTime) + "] is not equal with toDateTimeString to '2020-03-14T06:02:03'"
			);
			assertFalse(
					"2030-03-14T06:02:03".contentEquals(net.forestany.forestj.lib.Helper.toDateTimeString(o_localDateTime)),
					"local date time object[" + net.forestany.forestj.lib.Helper.toDateTimeString(o_localDateTime) + "] is equal with toDateTimeString to '2030-03-14T06:02:03'"
			);
			
			o_localDateTime = java.time.LocalDateTime.of(2020, 03, 14, 06, 02).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime();
			assertEquals(
					"2020-03-14T05:02:00Z",
					net.forestany.forestj.lib.Helper.toISO8601UTC(o_localDateTime),
					"local date time object[" + net.forestany.forestj.lib.Helper.toISO8601UTC(o_localDateTime) + "] is not equal with toISO8601UTC to '2020-03-14T05:02:00Z'"
			);
			assertFalse(
					"2030-03-14T05:02:00Z".contentEquals(net.forestany.forestj.lib.Helper.toISO8601UTC(o_localDateTime)),
					"local date time object[" + net.forestany.forestj.lib.Helper.toISO8601UTC(o_localDateTime) + "] is equal with toISO8601UTC to '2030-03-14T05:02:00Z'"
			);
			
			assertEquals(
					"Sat, 14 Mar 2020 05:02:00 GMT",
					net.forestany.forestj.lib.Helper.toRFC1123(o_localDateTime),
					"local date time object[" + net.forestany.forestj.lib.Helper.toRFC1123(o_localDateTime) + "] is not equal with toRFC1123 to 'Sat, 14 Mar 2020 05:02:00 GMT'"
			);
			assertFalse(
					"Sat, 14 Mar 2030 05:02:00 GMT".contentEquals(net.forestany.forestj.lib.Helper.toRFC1123(o_localDateTime)),
					"local date time object[" + net.forestany.forestj.lib.Helper.toRFC1123(o_localDateTime) + "] is equal with toRFC1123 to 'Sat, 14 Mar 2030 05:02:00 GMT'"
			);
			
			if (b_isDaySavingTime) {
				o_localDateTime = o_localDateTime.minusHours(1);
			}
			
			assertEquals(
					"2020-03-14T06:02:00",
					net.forestany.forestj.lib.Helper.toDateTimeString(o_localDateTime),
					"local date time object[" + net.forestany.forestj.lib.Helper.toDateTimeString(o_localDateTime) + "] is not equal with toDateTimeString to '2020-03-14T06:02:00'"
			);
			assertFalse(
					"2030-03-14T06:02:00".contentEquals(net.forestany.forestj.lib.Helper.toDateTimeString(o_localDateTime)),
					"local date time object[" + net.forestany.forestj.lib.Helper.toDateTimeString(o_localDateTime) + "] is equal with toDateTimeString to '2030-03-14T06:02:00'"
			);
			
			o_localDateTime = java.time.LocalDateTime.of(2020, 03, 14, 06, 02, 0, 576000000).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime();
			assertEquals(
					"2020-03-14T05:02:00.576Z",
					net.forestany.forestj.lib.Helper.toISO8601UTC(o_localDateTime),
					"local date time object[" + net.forestany.forestj.lib.Helper.toISO8601UTC(o_localDateTime) + "] is not equal with toISO8601UTC to '2020-03-14T05:02:00.576Z'"
			);
			assertFalse(
					"2030-03-14T05:02:00.576Z".contentEquals(net.forestany.forestj.lib.Helper.toISO8601UTC(o_localDateTime)),
					"local date time object[" + net.forestany.forestj.lib.Helper.toISO8601UTC(o_localDateTime) + "] is equal with toISO8601UTC to '2030-03-14T05:02:00.576Z'"
			);
			
			assertEquals(
					"Sat, 14 Mar 2020 05:02:00 GMT",
					net.forestany.forestj.lib.Helper.toRFC1123(o_localDateTime),
					"local date time object[" + net.forestany.forestj.lib.Helper.toRFC1123(o_localDateTime) + "] is not equal with toRFC1123 to 'Sat, 14 Mar 2020 05:02:00 GMT'"
			);
			assertFalse(
					"Sat, 14 Mar 2030 05:02:00 GMT".contentEquals(net.forestany.forestj.lib.Helper.toRFC1123(o_localDateTime)),
					"local date time object[" + net.forestany.forestj.lib.Helper.toRFC1123(o_localDateTime) + "] is equal with toRFC1123 to 'Sat, 14 Mar 2030 05:02:00 GMT'"
			);
			
			if (b_isDaySavingTime) {
				o_localDateTime = o_localDateTime.minusHours(1);
			}
			
			assertEquals(
					"2020-03-14T06:02:00.576",
					net.forestany.forestj.lib.Helper.toDateTimeString(o_localDateTime),
					"local date time object[" + net.forestany.forestj.lib.Helper.toDateTimeString(o_localDateTime) + "] is not equal with toDateTimeString to '2020-03-14T06:02:00.576'"
			);
			assertFalse(
					"2030-03-14T06:02:00.576".contentEquals(net.forestany.forestj.lib.Helper.toDateTimeString(o_localDateTime)),
					"local date time object[" + net.forestany.forestj.lib.Helper.toDateTimeString(o_localDateTime) + "] is equal with toDateTimeString to '2030-03-14T06:02:00.576'"
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
				java.time.LocalDateTime.of(2020, 03, 14, 06, 02, 03).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime(),
				java.time.LocalDateTime.of(2020, 03, 14, 06, 02, 03, 576000000).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime()
			};
			
			a_testTrue = new String[] {
				"14-03-2020 05:02",
				"14-03-2020T05:02",
				"14-03-2020 05:02:03",
				"14-03-2020T05:02:03",
				"14-03-2020T05:02:03Z",
				"14-03-2020T05:02:03.576",
				
				"14.03.2020 05:02",
				"14.03.2020T05:02",
				"14.03.2020 05:02:03",
				"14.03.2020T05:02:03",
				"14.03.2020T05:02:03Z",
				"14.03.2020T05:02:03.576Z",
				
				"14/03/2020 05:02",
				"14/03/2020T05:02",
				"14/03/2020 05:02:03",
				"14/03/2020T05:02:03",
				"14/03/2020T05:02:03Z",
				"14/03/2020T05:02:03.576",
				
				"03/14/2020 05:02",
				"03/14/2020T05:02",
				"03/14/2020 05:02:03",
				"03/14/2020T05:02:03",
				"03/14/2020T05:02:03Z",
				"03/14/2020T05:02:03.576Z",
				
				"2020-03-14 05:02",
				"2020-03-14T05:02",
				"2020-03-14 05:02:03",
				"2020-03-14T05:02:03",
				"2020-03-14T05:02:03Z",
				"2020-03-14T05:02:03.576",
				
				"2020/03/14 05:02",
				"2020/03/14T05:02",
				"2020/03/14 05:02:03",
				"2020/03/14T05:02:03",
				"2020/03/14T05:02:03Z",
				"2020/03/14T05:02:03.576Z",
				
				"2020/14/03 05:02",
				"2020/14/03T05:02",
				"2020/14/03 05:02:03",
				"2020/14/03T05:02:03",
				"2020/14/03T05:02:03Z",
				"2020/14/03T05:02:03.576",
			};
			
			int i = 0;
			
			for (String s_testTrue : a_testTrue) {
				assertEquals(
						a_validLocalDateTime[i],
						net.forestany.forestj.lib.Helper.fromISO8601UTC(s_testTrue),
						"'" + s_testTrue + "' fromISO8601UTC() is not equal local date time object '" + a_validLocalDateTime[i] + "'"
				);
				
				assertEquals(
						a_validLocalDateTime[i].minusHours(1),
						net.forestany.forestj.lib.Helper.fromDateTimeString(s_testTrue),
						"'" + s_testTrue + "' fromDateTimeString() is not equal local date time object '" + a_validLocalDateTime[i] + "'"
				);
				
				if (i == 5) {
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
						net.forestany.forestj.lib.Helper.fromDateString(s_testTrue),
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
				java.time.LocalTime.of(05, 02, 03),
				java.time.LocalTime.of(05, 02, 03, 576000000)
			};
			
			String[] a_testTrue3rd = new String[] {
				"05:02",
				"05:02",
				"05:02:03",
				"05:02:03",
				"05:02:03",
				"05:02:03.576",
				
				"05:02",
				"05:02",
				"05:02:03",
				"05:02:03",
				"05:02:03",
				"05:02:03.576",
				
				"05:02",
				"05:02",
				"05:02:03",
				"05:02:03",
				"05:02:03",
				"05:02:03.576",
				
				"05:02",
				"05:02",
				"05:02:03",
				"05:02:03",
				"05:02:03",
				"05:02:03.576",
				
				"05:02",
				"05:02",
				"05:02:03",
				"05:02:03",
				"05:02:03",
				"05:02:03.576",
				
				"05:02",
				"05:02",
				"05:02:03",
				"05:02:03",
				"05:02:03",
				"05:02:03.576",
				
				"05:02",
				"05:02",
				"05:02:03",
				"05:02:03",
				"05:02:03",
				"05:02:03.576",
			};
			
			i = 0;
			
			for (String s_testTrue : a_testTrue3rd) {
				assertEquals(
						o_validLocalTimes[i],
						net.forestany.forestj.lib.Helper.fromTimeString(s_testTrue),
						"'" + s_testTrue + "' fromTimeString() is not equal local date time object '" + o_validLocalTimes[i] + "'"
				);
				
				if (i == 5) {
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
				java.time.LocalDateTime.of(2020, 03, 14, 06, 02, 03).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime(),
				java.time.LocalDateTime.of(2020, 03, 14, 06, 02, 03, 576000000).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime()
			};
			
			a_testFalse = new String[] {
				"03-14-2020 05:02",
				"03-14-2020T05:02",
				"03-14-2020 05:02:03",
				"03-14-2020T05:02:03",
				"03-14-2020T05:02:03Z",
				"03-14-2020T05:02:03.576Z",
				"14-03-2020 55:02",
				"14-03-2020T55:02",
				"14-03-2020 55:02:03",
				"14-03-2020T55:02:03",
				"14-03-2020T55:02:03Z",
				"03-14-2020T05:02:03.57Z",
				
				"03.14.2020 05:02",
				"03.14.2020T05:02",
				"03.14.2020 05:02:03",
				"03.14.2020T05:02:03",
				"03.14.2020T05:02:03Z",
				"03.14.2020T05:02:03.576",
				"14.03.2020 55:02",
				"14.03.2020T55:02",
				"14.03.2020 55:02:03",
				"14.03.2020T55:02:03",
				"14.03.2020T55:02:03Z",
				"03.14.2020T05:02:03.57",
				
				"14/13/2020 05:02",
				"14/13/2020T05:02",
				"14/13/2020 05:02:03",
				"14/13/2020T05:02:03",
				"14/13/2020T05:02:03Z",
				"14/13/2020T05:02:03.576Z",
				"14/03/2020 55:02",
				"14/03/2020T55:02",
				"14/03/2020 55:02:03",
				"14/03/2020T55:02:03",
				"14/03/2020T55:02:03Z",
				"14/13/2020T05:02:03.57Z",
				
				"13/14/2020 05:02",
				"13/14/2020T05:02",
				"13/14/2020 05:02:03",
				"13/14/2020T05:02:03",
				"13/14/2020T05:02:03Z",
				"13/14/2020T05:02:03.576",
				"03/14/2020 55:02",
				"03/14/2020T55:02",
				"03/14/2020 55:02:03",
				"03/14/2020T55:02:03",
				"03/14/2020T55:02:03Z",
				"13/14/2020T05:02:03.57",
				
				"2020-14-03 05:02",
				"2020-14-03T05:02",
				"2020-14-03 05:02:03",
				"2020-14-03T05:02:03",
				"2020-14-03T05:02:03Z",
				"2020-14-03T05:02:03.576Z",
				"2020-03-14 55:02",
				"2020-03-14T55:02",
				"2020-03-14 55:02:03",
				"2020-03-14T55:02:03",
				"2020-03-14T55:02:03Z",
				"2020-03-14T05:02:03.57Z",
				
				"2020/13/14 05:02",
				"2020/13/14T05:02",
				"2020/13/14 05:02:03",
				"2020/13/14T05:02:03",
				"2020/13/14T05:02:03Z",
				"2020/13/14T05:02:03.576",
				"2020/03/14 55:02",
				"2020/03/14T55:02",
				"2020/03/14 55:02:03",
				"2020/03/14T55:02:03",
				"2020/03/14T55:02:03Z",
				"2020/03/14T05:02:03.57",
				
				"2020/14/13 05:02",
				"2020/14/13T05:02",
				"2020/14/13 05:02:03",
				"2020/14/13T05:02:03",
				"2020/14/13T05:02:03Z",
				"2020/14/13T05:02:03.576Z",
				"2020/14/03 55:02",
				"2020/14/03T55:02",
				"2020/14/03 55:02:03",
				"2020/14/03T55:02:03",
				"2020/14/03T55:02:03Z",
				"2020/14/03T05:02:03.57Z"
			};
			
			i = 0;
			
			for (String s_testFalse : a_testFalse) {
				boolean b_check = true;
				
				try {
					net.forestany.forestj.lib.Helper.fromISO8601UTC(s_testFalse);
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
					net.forestany.forestj.lib.Helper.fromDateTimeString(s_testFalse);
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
					net.forestany.forestj.lib.Helper.fromDateString(s_testFalse);
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
					net.forestany.forestj.lib.Helper.fromTimeString(s_testFalse);
				} catch (Exception o_exc) {
					b_check = false;
				}
				
				if (b_check) {
					assertTrue(
							false,
							"'" + s_testFalse + "' fromTimeString() could be parsed to local time object '" + a_validLocalDateTime[i] + "'"
					);
				}
				
				if (i == 5) {
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
					net.forestany.forestj.lib.Helper.utilDateToISO8601UTC(o_date),
					"local date time object[" + net.forestany.forestj.lib.Helper.utilDateToISO8601UTC(o_date) + "] is not equal with toISO8601UTC to '2020-03-14T05:02:03Z'"
			);
			assertFalse(
					"2030-03-14T05:02:03Z".contentEquals(net.forestany.forestj.lib.Helper.utilDateToISO8601UTC(o_date)),
					"local date time object[" + net.forestany.forestj.lib.Helper.utilDateToISO8601UTC(o_date) + "] is equal with toISO8601UTC to '2030-03-14T05:02:03Z'"
			);
			
			assertEquals(
					"2020-03-14T06:02:03",
					net.forestany.forestj.lib.Helper.utilDateToDateTimeString(o_date),
					"local date time object[" + net.forestany.forestj.lib.Helper.utilDateToDateTimeString(o_date) + "] is not equal with utilDateToDateTimeString to '2020-03-14T06:02:03Z'"
			);
			assertFalse(
					"2030-03-14T06:02:03".contentEquals(net.forestany.forestj.lib.Helper.utilDateToDateTimeString(o_date)),
					"local date time object[" + net.forestany.forestj.lib.Helper.utilDateToDateTimeString(o_date) + "] is equal with utilDateToDateTimeString to '2030-03-14T06:02:03'"
			);
			
			o_date = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS").parse("14.03.2020 06:02:03.576");
			assertEquals(
					"2020-03-14T05:02:03Z",
					net.forestany.forestj.lib.Helper.utilDateToISO8601UTC(o_date),
					"local date time object[" + net.forestany.forestj.lib.Helper.utilDateToISO8601UTC(o_date) + "] is not equal with toISO8601UTC to '2020-03-14T05:02:03Z'"
			);
			assertFalse(
					"2030-03-14T05:02:03.576Z".contentEquals(net.forestany.forestj.lib.Helper.utilDateToISO8601UTC(o_date)),
					"local date time object[" + net.forestany.forestj.lib.Helper.utilDateToISO8601UTC(o_date) + "] is equal with toISO8601UTC to '2030-03-14T05:02:03.576Z'"
			);
			
			assertEquals(
					"2020-03-14T06:02:03",
					net.forestany.forestj.lib.Helper.utilDateToDateTimeString(o_date),
					"local date time object[" + net.forestany.forestj.lib.Helper.utilDateToDateTimeString(o_date) + "] is not equal with utilDateToDateTimeString to '2020-03-14T06:02:03Z'"
			);
			assertFalse(
					"2030-03-14T06:02:03.576".contentEquals(net.forestany.forestj.lib.Helper.utilDateToDateTimeString(o_date)),
					"local date time object[" + net.forestany.forestj.lib.Helper.utilDateToDateTimeString(o_date) + "] is equal with utilDateToDateTimeString to '2030-03-14T06:02:03.576'"
			);
		} catch (java.text.ParseException o_exc) {
			fail("Could not parse java util date object to '2020-03-14 06:02[:03][.576]': " + o_exc.getMessage());
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
		
		try {
			String s_date = "2020-03-14T05:02:03Z";
			assertEquals(
					new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("14.03.2020 06:02:03"),
					net.forestany.forestj.lib.Helper.fromISO8601UTCToUtilDate(s_date),
					"fromISO8601UTCToUtilDate '2020-03-14T05:02:03Z' is not equal with java util date object[" + net.forestany.forestj.lib.Helper.fromISO8601UTCToUtilDate(s_date) + "]"
			);
			assertFalse(
					new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("14.03.2020 05:02:03").equals(net.forestany.forestj.lib.Helper.fromISO8601UTCToUtilDate(s_date)),
					"fromISO8601UTCToUtilDate '2020-03-14T05:02:03Z' is not equal with java util date object[" + net.forestany.forestj.lib.Helper.fromISO8601UTCToUtilDate(s_date) + "]"
			);
			
			s_date = s_date.substring(0, s_date.length() - 1);
			assertEquals(
					new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("14.03.2020 05:02:03"),
					net.forestany.forestj.lib.Helper.fromDateTimeStringToUtilDate(s_date),
					"fromDateTimeStringToUtilDate '2020-03-14T05:02:03' is not equal with java util date object[" + net.forestany.forestj.lib.Helper.fromDateTimeStringToUtilDate(s_date) + "]"
			);
			assertFalse(
					new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("14.03.2020 06:02:03").equals(net.forestany.forestj.lib.Helper.fromDateTimeStringToUtilDate(s_date)),
					"fromDateTimeStringToUtilDate '2020-03-14T05:02:03' is not equal with java util date object[" + net.forestany.forestj.lib.Helper.fromDateTimeStringToUtilDate(s_date) + "]"
			);
		} catch (java.text.ParseException o_exc) {
			fail("Could not parse '2020-03-14T05:02:03Z' to a java util date object: " + o_exc.getMessage());
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
		
		for (int i = 0; i < 1000; i++) {
			int i_random = net.forestany.forestj.lib.Helper.randomIntegerRange(1, 10);
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
			double d_random = net.forestany.forestj.lib.Helper.randomDoubleRange(1.5, 10.75);
			assertTrue(
					Double.compare(d_random, 1.5) >= 0 && Double.compare(d_random, 10.75) < 1,
					"random double is not between 1.5 .. 10.75"
			);
			assertFalse(
					Double.compare(d_random, 1.5) < 0 && Double.compare(d_random, 10.75) >= 1,
					"random double is not between 1.5 .. 10.75"
			);
		}
		
		byte[] bytes = net.forestany.forestj.lib.Helper.shortToByteArray((short)558);
		short sh_test = net.forestany.forestj.lib.Helper.byteArrayToShort(bytes);
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
				net.forestany.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of short are not matching printed byte array"
		);
		assertEquals(
				"00000000 00000000 00000010 00101110",
				net.forestany.forestj.lib.Helper.printByteArray(bytes, true),
				"bytes of short are not matching printed byte array"
		);
		
		bytes = net.forestany.forestj.lib.Helper.shortToByteArray((short)25134);
		sh_test = net.forestany.forestj.lib.Helper.byteArrayToShort(bytes);
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
				net.forestany.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of short are not matching printed byte array"
		);
		assertEquals(
				"00000000 00000000 01100010 00101110",
				net.forestany.forestj.lib.Helper.printByteArray(bytes, true),
				"bytes of short are not matching printed byte array"
		);
		
		bytes = net.forestany.forestj.lib.Helper.intToByteArray(916040294);
		int i_test = net.forestany.forestj.lib.Helper.byteArrayToInt(bytes);
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
				net.forestany.forestj.lib.Helper.printByteArray(bytes),
				"bytes of int are not matching printed byte array"
		);
		assertEquals(
				"00110110 10011001 10101010 01100110",
				net.forestany.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of int are not matching printed byte array"
		);
		
		bytes = net.forestany.forestj.lib.Helper.intToByteArray(10070630);
		i_test = net.forestany.forestj.lib.Helper.byteArrayToInt(bytes);		
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
				net.forestany.forestj.lib.Helper.printByteArray(bytes),
				"bytes of int are not matching printed byte array"
		);
		assertEquals(
				"10011001 10101010 01100110",
				net.forestany.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of int are not matching printed byte array"
		);
				
		bytes = net.forestany.forestj.lib.Helper.intToByteArray(43622);
		i_test = net.forestany.forestj.lib.Helper.byteArrayToInt(bytes);
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
				net.forestany.forestj.lib.Helper.printByteArray(bytes),
				"bytes of int are not matching printed byte array"
		);
		assertEquals(
				"10101010 01100110",
				net.forestany.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of int are not matching printed byte array"
		);
		
		bytes = net.forestany.forestj.lib.Helper.intToByteArray(102);
		i_test = net.forestany.forestj.lib.Helper.byteArrayToInt(bytes);
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
				net.forestany.forestj.lib.Helper.printByteArray(bytes),
				"bytes of int are not matching printed byte array"
		);
		assertEquals(
				"01100110",
				net.forestany.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of int are not matching printed byte array"
		);
		
		
		
		bytes = net.forestany.forestj.lib.Helper.longToByteArray(9070052179665454l);
		long l_test = net.forestany.forestj.lib.Helper.byteArrayToLong(bytes);
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
				net.forestany.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of long are not matching printed byte array"
		);
		
		bytes = net.forestany.forestj.lib.Helper.longToByteArray(3467834566000206382l);
		l_test = net.forestany.forestj.lib.Helper.byteArrayToLong(bytes);
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
				net.forestany.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of long are not matching printed byte array"
		);
		
		bytes = net.forestany.forestj.lib.Helper.amountToNByteArray(101458034, 4);
		l_test = net.forestany.forestj.lib.Helper.byteArrayToLong(bytes);
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
				net.forestany.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of long are not matching printed byte array"
		);
		
		bytes = net.forestany.forestj.lib.Helper.amountToNByteArray(101458034, 8);
		l_test = net.forestany.forestj.lib.Helper.byteArrayToLong(bytes);
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
				net.forestany.forestj.lib.Helper.printByteArray(bytes, false),
				"bytes of long are not matching printed byte array"
		);
		
		assertEquals(
				"2,12 GB",
				net.forestany.forestj.lib.Helper.formatBytes(2123456797),
				"2123456797 is not 2,12 GB"
		);
		assertEquals(
				"1,98 GiB",
				net.forestany.forestj.lib.Helper.formatBytes(2123456797, true),
				"2123456797 is not 1,98 GiB"
		);
		
		assertFalse(
				net.forestany.forestj.lib.Helper.formatBytes(129456797).contentEquals("2,12 GB"),
				"129456797 is 2,12 GB"
		);
		assertFalse(
				net.forestany.forestj.lib.Helper.formatBytes(129456797, true).contentEquals("1,98 GiB"),
				"129456797 is 1,98 GiB"
		);
		
		assertEquals(
				"126,8 KB",
				net.forestany.forestj.lib.Helper.formatBytes(126797),
				"126797 is not 126,8 KB"
		);
		assertEquals(
				"123,83 KiB",
				net.forestany.forestj.lib.Helper.formatBytes(126797, true),
				"126797 is not 123,83 KiB"
		);
		
		assertFalse(
				net.forestany.forestj.lib.Helper.formatBytes(33126797).contentEquals("126,8 KB"),
				"33126797 is 126,8 KB"
		);
		assertFalse(
				net.forestany.forestj.lib.Helper.formatBytes(6797, true).contentEquals("123,83 KiB"),
				"6797 is 123,83 KiB"
		);
		
		try {
			assertEquals(
					"9f2778195bb08930f6455ca6c191d9dc25b77f33145141a2e89fac794d5e7c47",
					net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", net.forestany.forestj.lib.Helper.intToByteArray(43622)),
					"SHA-256 ist not '9f2778195bb08930f6455ca6c191d9dc25b77f33145141a2e89fac794d5e7c47'"
			);
			assertEquals(
					"4dd7d9b43afd5ca29a794b59ee924f2226e7776c7d25e052060ab71dcd7254da9ff5c342f8e943d85336d7d97bad8cb9",
					net.forestany.forestj.lib.Helper.hashByteArray("SHA-384", net.forestany.forestj.lib.Helper.intToByteArray(10070630)),
					"SHA-384 ist not '4dd7d9b43afd5ca29a794b59ee924f2226e7776c7d25e052060ab71dcd7254da9ff5c342f8e943d85336d7d97bad8cb9'"
			);
			assertEquals(
					"38c320515e85995fc7acfefd5126eba8edb6133e6e552565899534d03e8af6d6ff9bb1c165c58bbb43aed8de01fdd3fb0c9f4f6d384ad8bcd419421ac10ab9c1",
					net.forestany.forestj.lib.Helper.hashByteArray("SHA-512", net.forestany.forestj.lib.Helper.intToByteArray(916040294)),
					"SHA-512 ist not '38c320515e85995fc7acfefd5126eba8edb6133e6e552565899534d03e8af6d6ff9bb1c165c58bbb43aed8de01fdd3fb0c9f4f6d384ad8bcd419421ac10ab9c1'"
			);
			
			assertFalse(
					net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", net.forestany.forestj.lib.Helper.intToByteArray(43622)).contentEquals("9f2778195bb08930f6455ca6c191d9dc25c77f33145141a2e89fac794d5e7c47"),
					"SHA-256 is '9f2778195bb08930f6455ca6c191d9dc25c77f33145141a2e89fac794d5e7c47'"
			);
			assertFalse(
					net.forestany.forestj.lib.Helper.hashByteArray("SHA-384", net.forestany.forestj.lib.Helper.intToByteArray(10070630)).contentEquals("4dd7d9b43afd5ca29a794b59ee924f2226e7771c7d25e052060ab71dcd7254da9ff5c342f8e943d85336d7d97bad8cb9"),
					"SHA-384 is '4dd7d9b43afd5ca29a794b59ee924f2226e7771c7d25e052060ab71dcd7254da9ff5c342f8e943d85336d7d97bad8cb9'"
			);
			assertFalse(
					net.forestany.forestj.lib.Helper.hashByteArray("SHA-512", net.forestany.forestj.lib.Helper.intToByteArray(916040294)).contentEquals("38c320515e85995fc7acfefd5126eba8edb6133e6e552565899534d03e8af6d6ff9bb1c165c58bbb43aed8de01fdd3fb0c9fef6d384ad8bcd419421ac10ab9c1"),
					"SHA-512 is '38c320515e85995fc7acfefd5126eba8edb6133e6e552565899534d03e8af6d6ff9bb1c165c58bbb43aed8de01fdd3fb0c9fef6d384ad8bcd419421ac10ab9c1'"
			);
			
			assertEquals(
					"0x44 0x61 0x73 0x20 0x69 0x73 0x74 0x20 0x64 0x61 0x73 0x20 0x48 0x61 0x75 0x73 0x20 0x76 0x6f 0x6d 0x20 0x4e 0x69 0x6b 0x6f 0x6c 0x61 0x75 0x73 0x2e",
					net.forestany.forestj.lib.Helper.bytesToHexString("Das ist das Haus vom Nikolaus.".getBytes(), true),
					"BytesToHexString result for 'Das ist das Haus vom Nikolaus.' does not match expected value."
			);
			assertEquals(
					"0x41 0x42 0x43 0x44 0x45 0x46 0x47 0x48 0x49 0x4a 0x4b 0x4c 0x3f",
					net.forestany.forestj.lib.Helper.bytesToHexString("ABCDEFGHIJKL€".getBytes(java.nio.charset.Charset.forName("ISO-8859-1")), true),
					"BytesToHexString result for 'ABCDEFGHIJKL€' does not match expected value for charset 'ISO-8859-1'."
			);
			assertEquals(
					"0x41 0x42 0x43 0x44 0x45 0x46 0x47 0x48 0x49 0x4a 0x4b 0x4c 0xe2 0x82 0xac",
					net.forestany.forestj.lib.Helper.bytesToHexString("ABCDEFGHIJKL€".getBytes(java.nio.charset.Charset.forName("UTF-8")), true),
					"BytesToHexString result for 'ABCDEFGHIJKL€' does not match expected value for charset 'UTF-8'."
			);
			assertEquals(
					"0xfe 0xff 0x00 0x41 0x00 0x42 0x00 0x43 0x00 0x44 0x00 0x45 0x00 0x46 0x00 0x47 0x00 0x48 0x00 0x49 0x00 0x4a 0x00 0x4b 0x00 0x4c 0x20 0xac",
					net.forestany.forestj.lib.Helper.bytesToHexString("ABCDEFGHIJKL€".getBytes(java.nio.charset.Charset.forName("UTF-16")), true),
					"BytesToHexString result for 'ABCDEFGHIJKL€' does not match expected value for charset 'UTF-16'."
			);
			
			assertArrayEquals(
	                new byte[] { 0x44, 0x61, 0x73, 0x20, 0x69, 0x73, 0x74, 0x20, 0x64, 0x61, 0x73, 0x20, 0x48, 0x61, 0x75, 0x73, 0x20, 0x76 },
	                net.forestany.forestj.lib.Helper.hexStringToBytes("0x44 0x61 0x73 0x20 0x69 0x73 0x74 0x20 0x64 0x61 0x73 0x20 0x48 0x61 0x75 0x73 0x20 0x76"),
	                "HexStringToBytes result for '0x44 0x61 0x73 0x20 0x69 0x73 0x74 0x20 0x64 0x61 0x73 0x20 0x48 0x61 0x75 0x73 0x20 0x76' does not match expected value."
            );
			assertArrayEquals(
					new byte[] { 0x44, 0x61, 0x73, 0x20, 0x69, 0x73, 0x74, 0x20, 0x64, 0x61, 0x73, 0x20, 0x48, 0x61, 0x75, 0x73, 0x20, 0x76 },
	                net.forestany.forestj.lib.Helper.hexStringToBytes("446173206973742064617320486175732076"),
	                "HexStringToBytes result for '446173206973742064617320486175732076' does not match expected value."
            );
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}

		java.util.List<String> a_list1 = java.util.Arrays.asList("a", "bc", "def", "ghij", "klmno");
		assertTrue(
				net.forestany.forestj.lib.Helper.joinList(a_list1, ',').contentEquals("a,bc,def,ghij,klmno"),
				"concated list'" + net.forestany.forestj.lib.Helper.joinList(a_list1, ',') + "' is not equal 'a,bc,def,ghij,klmno'"
		);
		
		java.util.List<Integer> a_list2 = java.util.Arrays.asList(1, 23, 45, 678, 910111213);
		assertTrue(
				net.forestany.forestj.lib.Helper.joinList(a_list2, ':').contentEquals("1:23:45:678:910111213"),
				"concated list'" + net.forestany.forestj.lib.Helper.joinList(a_list1, ':') + "' is not equal '1:23:45:678:910111213'"
		);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.getIndexOfObjectInList(java.util.Arrays.asList("two", "one", "three"), "one") > 0,
				"'one' not found and no index returned from array list"
		);
		assertFalse(
				net.forestany.forestj.lib.Helper.getIndexOfObjectInList(java.util.Arrays.asList("two", "four", "three"), "one") > 0,
				"'one' found and index returned from array list"
		);

		assertTrue(
				net.forestany.forestj.lib.Helper.isIndexValid(java.util.Arrays.asList("two", "one", "three"), 2),
				"index '2' is not valid"
		);
		assertFalse(
				net.forestany.forestj.lib.Helper.isIndexValid(java.util.Arrays.asList("two", "four", "three"), 42),
				"index '42' is valid"
		);
		
		java.time.Duration o_duration = java.time.Duration.ofSeconds(597845641);
		assertTrue(
				net.forestany.forestj.lib.Helper.formatDuration(o_duration).contentEquals("2767:48:14"),
				"duration is not '2767:48:14'" 
		);
		assertFalse(
				net.forestany.forestj.lib.Helper.formatDuration(o_duration).contentEquals("767:18:14"),
				"duration is '767:48:14'"
		);
		
		String s_random = net.forestany.forestj.lib.Helper.generateRandomString(32);
		
		assertTrue(
				s_random.length() == 32,
				"random generated string has not a length of 32 characters" 
		);
		
		s_random = net.forestany.forestj.lib.Helper.generateRandomString(10, net.forestany.forestj.lib.Helper.DIGITS_CHARACTERS);
		
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
		
		s_random = net.forestany.forestj.lib.Helper.generateUUID();
		
		assertTrue(
				s_random.length() == 36,
				"random generated string has not a length of 36 characters" 
		);
		
		String s_original = "ftps://user:password@expample.com:21";
		String s_disguised = net.forestany.forestj.lib.Helper.disguiseSubstring(s_original, "//", "@", '*');
		
		assertTrue(
				s_disguised.contentEquals("ftps://*************@expample.com:21"),
				"disguised string is not 'ftps://*************@expample.com:21', but '" + s_disguised + "'" 
		);
		
		s_original = "This is just a \"test\"";
		s_disguised = net.forestany.forestj.lib.Helper.disguiseSubstring(s_original, "\"", "\"", '+');
		
		assertTrue(
				s_disguised.contentEquals("This is just a \"++++\""),
				"disguised string is not 'This is just a \"++++\"', but '" + s_disguised + "'" 
		);
		
		s_original = "No disguise at all";
		s_disguised = net.forestany.forestj.lib.Helper.disguiseSubstring(s_original, ".", "&", '_');
		
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
				net.forestany.forestj.lib.Helper.isIpv4WithinRange(a_ipAddresses[i * 2], a_ipAddresses[(i * 2) + 1]),
				a_ipAddressesResults[i],
				"ip '" + a_ipAddresses[i * 2] + "' is " + ((!a_ipAddressesResults[i]) ? "not" : "") + " within '" + a_ipAddresses[(i * 2) + 1] + "'"
			);
		}
		
		assertEquals(
			net.forestany.forestj.lib.Helper.isIpv4MulticastAddress("225.4.228.87"),
            true,
            "'225.4.228.87' is not an ipv4 multicast address"
        );

		assertEquals(
    		net.forestany.forestj.lib.Helper.isIpv4MulticastAddress("225.4.228.87/16"),
            false,
            "'225.4.228.87/16' is not an ipv4 multicast address"
        );

		assertEquals(
    		net.forestany.forestj.lib.Helper.isIpv4MulticastAddress("192.4.228.87"),
            false,
            "'192.4.228.87' is not an ipv4 multicast address"
        );

		assertEquals(
    		net.forestany.forestj.lib.Helper.isIpv4MulticastAddress("240.4.228.87"),
            false,
            "'240.4.228.87' is not an ipv4 multicast address"
        );

		assertEquals(
    		net.forestany.forestj.lib.Helper.isIpv4MulticastAddress("239.4.228.87"),
            true,
            "'239.4.228.87' is not an ipv4 multicast address"
        );
		
		assertEquals(
    		net.forestany.forestj.lib.Helper.isIpv6Address("2001:0db8:85a3:08d3:1319:8a2e:0370:7347"),
            true,
            "'2001:0db8:85a3:08d3:1319:8a2e:0370:7347' is not an ipv6 address"
        );
		
		assertEquals(
    		net.forestany.forestj.lib.Helper.isIpv6Address("fe80::ca32:231b:f27e:b696"),
            true,
            "'fe80::ca32:231b:f27e:b696' is not an ipv6 address"
        );
		
		assertEquals(
    		net.forestany.forestj.lib.Helper.isIpv6Address("FE80:CD00:0000:0CDE:1257:0000:211E:729C"),
            true,
            "'FE80:CD00:0000:0CDE:1257:0000:211E:729C' is not an ipv6 address"
        );
		
		assertEquals(
    		net.forestany.forestj.lib.Helper.isIpv6Address("fe80::ca32:231b:f27z:b696"),
            false,
            "'fe80::ca32:231b:f27z:b696' is an ipv6 address"
        );
		
		assertEquals(
    		net.forestany.forestj.lib.Helper.isIpv6MulticastAddress("FF05::342"),
            true,
            "'FF05::342' is not an ipv6 multicast address"
        );
		
		assertEquals(
    		net.forestany.forestj.lib.Helper.isIpv6MulticastAddress("FF05:0:0:0:0:0:0:342"),
            true,
            "'FF05:0:0:0:0:0:0:342' is not an ipv6 multicast address"
        );
		
		assertEquals(
    		net.forestany.forestj.lib.Helper.isIpv6MulticastAddress("ff02::2"),
            true,
            "'ff02::2' is not an ipv6 multicast address"
        );
		
		assertEquals(
    		net.forestany.forestj.lib.Helper.isIpv6MulticastAddress("FE80:CD00:0000:0CDE:1257:0000:211E:729C"),
            false,
            "'FE80:CD00:0000:0CDE:1257:0000:211E:729C' is an ipv6 multicast address"
        );
		
		try {			
			CompareObject o_compareOne = new CompareObject("One", 1, java.time.LocalTime.of(20, 06, 21), new long[] { 42135792468l, 21135792468l, 12135792468l, 14378135792468l }, new java.util.ArrayList<Short>( java.util.Arrays.asList((short)42, (short)21, (short)12, (short)14378) ), new SubCompareObject(42.125d, true, new java.math.BigDecimal[] { new java.math.BigDecimal("1.602176634"), new java.math.BigDecimal("8.8541878128"), new java.math.BigDecimal("6.62607015"), new java.math.BigDecimal("9.80665"), new java.math.BigDecimal("3.14159265359") }, java.time.LocalDateTime.of(2020, 12, 21, 06, 12, 24)));
            /* o_compareOne and o_compareTwo are identical */
            CompareObject o_compareTwo = new CompareObject("One", 1, java.time.LocalTime.of(20, 06, 21), new long[] { 42135792468l, 21135792468l, 12135792468l, 14378135792468l }, new java.util.ArrayList<Short>( java.util.Arrays.asList((short)42, (short)21, (short)12, (short)14378) ), new SubCompareObject(42.125d, true, new java.math.BigDecimal[] { new java.math.BigDecimal("1.602176634"), new java.math.BigDecimal("8.8541878128"), new java.math.BigDecimal("6.62607015"), new java.math.BigDecimal("9.80665"), new java.math.BigDecimal("3.14159265359") }, java.time.LocalDateTime.of(2020, 12, 21, 06, 12, 24)));
            /* o_compareOne and o_compareThree are not identical -> see LocalTime seconds of CompareObject instance ... it is 12 and not 21 && see Boolean of SubCompareObject instance ... it is false and not true */
            CompareObject o_compareThree = new CompareObject("One", 1, java.time.LocalTime.of(20, 06, 12), new long[] { 42135792468l, 21135792468l, 12135792468l, 14378135792468l }, new java.util.ArrayList<Short>( java.util.Arrays.asList((short)42, (short)21, (short)12, (short)14378) ), new SubCompareObject(42.125d, false, new java.math.BigDecimal[] { new java.math.BigDecimal("1.602176634"), new java.math.BigDecimal("8.8541878128"), new java.math.BigDecimal("6.62607015"), new java.math.BigDecimal("9.80665"), new java.math.BigDecimal("3.14159265359") }, java.time.LocalDateTime.of(2020, 12, 21, 06, 12, 24)));
            /* o_compareOne and o_compareFour are not identical, but only with deep comparison -> see LocalDateTime of SubCompareObject instance ... it is 1920 and not 2020 */
            CompareObject o_compareFour = new CompareObject("One", 1, java.time.LocalTime.of(20, 06, 21), new long[] { 42135792468l, 21135792468l, 12135792468l, 14378135792468l }, new java.util.ArrayList<Short>( java.util.Arrays.asList((short)42, (short)21, (short)12, (short)14378) ), new SubCompareObject(42.125d, true, new java.math.BigDecimal[] { new java.math.BigDecimal("1.602176634"), new java.math.BigDecimal("8.8541878128"), new java.math.BigDecimal("6.62607015"), new java.math.BigDecimal("9.80665"), new java.math.BigDecimal("3.14159265359") }, java.time.LocalDateTime.of(1920, 12, 21, 06, 12, 24)));

            CompareObjectProperties o_compareOneProp = new CompareObjectProperties("One", 1, java.time.LocalTime.of(20, 06, 21), new long[] { 42135792468l, 21135792468l, 12135792468l, 14378135792468l }, new java.util.ArrayList<Short>( java.util.Arrays.asList((short)42, (short)21, (short)12, (short)14378) ), new SubCompareObjectProperties(42.125d, true, new java.math.BigDecimal[] { new java.math.BigDecimal("1.602176634"), new java.math.BigDecimal("8.8541878128"), new java.math.BigDecimal("6.62607015"), new java.math.BigDecimal("9.80665"), new java.math.BigDecimal("3.14159265359") }, java.time.LocalDateTime.of(2020, 12, 21, 06, 12, 24)));
            /* o_compareOneProp and o_compareTwoProp are identical */
            CompareObjectProperties o_compareTwoProp = new CompareObjectProperties("One", 1, java.time.LocalTime.of(20, 06, 21), new long[] { 42135792468l, 21135792468l, 12135792468l, 14378135792468l }, new java.util.ArrayList<Short>( java.util.Arrays.asList((short)42, (short)21, (short)12, (short)14378) ), new SubCompareObjectProperties(42.125d, true, new java.math.BigDecimal[] { new java.math.BigDecimal("1.602176634"), new java.math.BigDecimal("8.8541878128"), new java.math.BigDecimal("6.62607015"), new java.math.BigDecimal("9.80665"), new java.math.BigDecimal("3.14159265359") }, java.time.LocalDateTime.of(2020, 12, 21, 06, 12, 24)));
            /* o_compareOneProp and o_compareThreeProp are not identical -> see LocalTime seconds of CompareObjectProperties instance ... it is 12 and not 21 && see Boolean of SubCompareObjectProperties instance ... it is false and not true */
            CompareObjectProperties o_compareThreeProp = new CompareObjectProperties("One", 1, java.time.LocalTime.of(20, 06, 12), new long[] { 42135792468l, 21135792468l, 12135792468l, 14378135792468l }, new java.util.ArrayList<Short>( java.util.Arrays.asList((short)42, (short)21, (short)12, (short)14378) ), new SubCompareObjectProperties(42.125d, false, new java.math.BigDecimal[] { new java.math.BigDecimal("1.602176634"), new java.math.BigDecimal("8.8541878128"), new java.math.BigDecimal("6.62607015"), new java.math.BigDecimal("9.80665"), new java.math.BigDecimal("3.14159265359") }, java.time.LocalDateTime.of(2020, 12, 21, 06, 12, 24)));
            /* o_compareOneProp and o_compareFourProp are not identical, but only with deep comparison -> see LocalDateTime of SubCompareObjectProperties instance ... it is 1920 and not 2020 */
            CompareObjectProperties o_compareFourProp = new CompareObjectProperties("One", 1, java.time.LocalTime.of(20, 06, 21), new long[] { 42135792468l, 21135792468l, 12135792468l, 14378135792468l }, new java.util.ArrayList<Short>( java.util.Arrays.asList((short)42, (short)21, (short)12, (short)14378) ), new SubCompareObjectProperties(42.125d, true, new java.math.BigDecimal[] { new java.math.BigDecimal("1.602176634"), new java.math.BigDecimal("8.8541878128"), new java.math.BigDecimal("6.62607015"), new java.math.BigDecimal("9.80665"), new java.math.BigDecimal("3.14159265359") }, java.time.LocalDateTime.of(1920, 12, 21, 06, 12, 24)));

            java.util.List<CompareObject> o_listOne = new java.util.ArrayList<CompareObject>
            (
            	java.util.Arrays.asList(
	                o_compareOne,
	                o_compareTwo,
	                o_compareThree,
	                o_compareFour
	            )
            );
            /* o_listOne and o_listTwo are identical */
            java.util.List<CompareObject> o_listTwo = new java.util.ArrayList<CompareObject>
            (
            	java.util.Arrays.asList(
	                o_compareOne,
	                o_compareTwo,
	                o_compareThree,
	                o_compareFour
	            )
            );
            /* o_listOne and o_listThree are not identical -> see missing fourth element */
            java.util.List<CompareObject> o_listThree = new java.util.ArrayList<CompareObject>
            (
            	java.util.Arrays.asList(
	                o_compareOne,
	                o_compareTwo,
	                o_compareThree
	            )
            );

            java.util.List<CompareObjectProperties> o_listOneProp = new java.util.ArrayList<CompareObjectProperties>
            (
            	java.util.Arrays.asList(
	                o_compareOneProp,
	                o_compareTwoProp,
	                o_compareThreeProp,
	                o_compareFourProp
	            )
            );
            /* o_listOneProp and o_listTwoProp are identical */
            java.util.List<CompareObjectProperties> o_listTwoProp = new java.util.ArrayList<CompareObjectProperties>
            (
            	java.util.Arrays.asList(
	                o_compareOneProp,
	                o_compareTwoProp,
	                o_compareThreeProp,
	                o_compareFourProp
	            )
            );
            /* o_listOne and o_listThreeProp are not identical -> see missing fourth element */
            java.util.List<CompareObjectProperties> o_listThreeProp = new java.util.ArrayList<CompareObjectProperties>
            (
            	java.util.Arrays.asList(
	                o_compareOneProp,
	                o_compareTwoProp,
	                o_compareThreeProp
	            )
            );
            
            assertTrue(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_compareOne, o_compareTwo, false),
                "o_compareOne[" + o_compareOne + "] is not equal to o_compareTwo[" + o_compareTwo + "]"
            );

            assertTrue(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_compareOneProp, o_compareTwoProp, true),
                "o_compareOneProp[" + o_compareOneProp + "] is not equal to o_compareTwoProp[" + o_compareTwoProp + "]"
            );

            assertTrue(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_compareOne, o_compareTwo, false, true),
                "o_compareOne[" + o_compareOne + "] is not equal to o_compareTwo[" + o_compareTwo + "]"
            );

            assertTrue(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_compareOneProp, o_compareTwoProp, true, true),
                "o_compareOneProp[" + o_compareOneProp + "] is not equal to o_compareTwoProp[" + o_compareTwoProp + "]"
            );

            assertFalse(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_compareOne, o_compareThree, false),
                "o_compareOne[" + o_compareOne + "] is equal to o_compareThree[" + o_compareThree + "]"
            );

            assertFalse(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_compareOneProp, o_compareThreeProp, true),
                "o_compareOneProp[" + o_compareOneProp + "] is equal to o_compareThreeProp[" + o_compareThreeProp + "]"
            );

            assertFalse(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_compareOne, o_compareThree, false, true),
                "o_compareOne[" + o_compareOne + "] is equal to o_compareThree[" + o_compareThree + "]"
            );

            assertFalse(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_compareOneProp, o_compareThreeProp, true, true),
                "o_compareOneProp[" + o_compareOneProp + "] is equal to o_compareThreeProp[" + o_compareThreeProp + "]"
            );

            assertTrue(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_compareOne, o_compareFour, false),
                "o_compareOne[" + o_compareOne + "] is not equal to o_compareFour[" + o_compareFour + "]"
            );

            assertTrue(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_compareOneProp, o_compareFourProp, true),
                "o_compareOneProp[" + o_compareOneProp + "] is not equal to o_compareFourProp[" + o_compareFourProp + "]"
            );

            assertFalse(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_compareOne, o_compareFour, false, true),
                "o_compareOne[" + o_compareOne + "] is equal to o_compareFour[" + o_compareFour + "]"
            );

            assertFalse(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_compareOneProp, o_compareFourProp, true, true),
                "o_compareOneProp[" + o_compareOneProp + "] is equal to o_compareFourProp[" + o_compareFourProp + "]"
            );

            assertTrue(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_listOne, o_listTwo, false),
                "o_listOne[" + o_listOne + "] is not equal to o_listTwo[" + o_listTwo + "]"
            );

            assertTrue(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_listOneProp, o_listTwoProp, true),
                "o_listOneProp[" + o_listOneProp + "] is not equal to o_listTwoProp[" + o_listTwoProp + "]"
            );

            assertTrue(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_listOne, o_listTwo, false, true),
                "o_listOne[" + o_listOne + "] is not equal to o_listTwo[" + o_listTwo + "]"
            );

            assertTrue(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_listOneProp, o_listTwoProp, true, true),
                "o_listOneProp[" + o_listOneProp + "] is not equal to o_listTwoProp[" + o_listTwoProp + "]"
            );

            assertFalse(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_listOne, o_listThree, false),
                "o_listOne[" + o_listOne + "] is equal to o_listThree[" + o_listThree + "]"
            );

            assertFalse(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_listOneProp, o_listThreeProp, true),
                "o_listOneProp[" + o_listOneProp + "] is equal to o_listThreeProp[" + o_listThreeProp + "]"
            );

            assertFalse(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_listOne, o_listThree, false, true),
                "o_listOne[" + o_listOne + "] is equal to o_listThree[" + o_listThree + "]"
            );

            assertFalse(
                net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_listOneProp, o_listThreeProp, true, true),
                "o_listOneProp[" + o_listOneProp + "] is equal to o_listThreeProp[" + o_listThreeProp + "]"
            );
		} catch (Exception o_exc) {
			fail(o_exc);
		}
	}

	public class CompareObject {
		public String ValueStr;
		public int ValueInt;
		public java.time.LocalTime ValueTime;
		public long[] ValueLongArray;
		public java.util.List<Short> ValueListShorts;
		public SubCompareObject ValueSubObject;
		
		public CompareObject(String p_s_str, int p_i_int, java.time.LocalTime p_o_localTime, long[] p_a_longArray, java.util.List<Short> p_a_shortList, SubCompareObject p_o_subValue) {
			this.ValueStr = p_s_str;
			this.ValueInt = p_i_int;
			this.ValueTime = p_o_localTime;
			this.ValueLongArray = p_a_longArray;
			this.ValueListShorts = p_a_shortList;
			this.ValueSubObject = p_o_subValue;
		}
		
		@Override
		public String toString() {
			return this.ValueStr + "|" + this.ValueInt + "|" + this.ValueTime + "|" + net.forestany.forestj.lib.Helper.printArrayList( this.ValueListShorts ) + "|" + net.forestany.forestj.lib.Helper.printArrayList( java.util.Arrays.asList( this.ValueLongArray ) ) + "|" + this.ValueSubObject;
		}
	}
	
	public class SubCompareObject {
		public double ValueDbl;
		public boolean ValueBool;
		public java.math.BigDecimal[] ValueDecimalArray;
		public java.time.LocalDateTime ValueDateTime;
		
		public SubCompareObject(double p_s_str, boolean p_i_int, java.math.BigDecimal[] p_o_localTime, java.time.LocalDateTime p_a_longArray) {
			this.ValueDbl = p_s_str;
			this.ValueBool = p_i_int;
			this.ValueDecimalArray = p_o_localTime;
			this.ValueDateTime = p_a_longArray;
		}
		
		@Override
		public String toString() {
			return this.ValueDbl + "|" + this.ValueBool + "|" + net.forestany.forestj.lib.Helper.printArrayList( java.util.Arrays.asList( this.ValueDecimalArray ) ) + "|" + this.ValueDateTime;
		}
	}
	
	public class CompareObjectProperties {
		private String ValueStr;
		private int ValueInt;
		private java.time.LocalTime ValueTime;
		private long[] ValueLongArray;
		private java.util.List<Short> ValueListShorts;
		private SubCompareObjectProperties ValueSubObject;
		
		public String getValueStr() {
			return this.ValueStr;
		}
		
		public int getValueInt() {
			return this.ValueInt;
		}
		
		public java.time.LocalTime getValueTime() {
			return this.ValueTime;
		}
		
		public long[] getValueLongArray() {
			return this.ValueLongArray;
		}
		
		public java.util.List<Short> getValueListShorts() {
			return this.ValueListShorts;
		}
		
		public SubCompareObjectProperties getValueSubObject() {
			return this.ValueSubObject;
		}
		
		public CompareObjectProperties(String p_s_str, int p_i_int, java.time.LocalTime p_o_localTime, long[] p_a_longArray, java.util.List<Short> p_a_shortList, SubCompareObjectProperties p_o_subValue) {
			this.ValueStr = p_s_str;
			this.ValueInt = p_i_int;
			this.ValueTime = p_o_localTime;
			this.ValueLongArray = p_a_longArray;
			this.ValueListShorts = p_a_shortList;
			this.ValueSubObject = p_o_subValue;
		}
		
		@Override
		public String toString() {
			return this.ValueStr + "|" + this.ValueInt + "|" + this.ValueTime + "|" + net.forestany.forestj.lib.Helper.printArrayList( this.ValueListShorts ) + "|" + net.forestany.forestj.lib.Helper.printArrayList( java.util.Arrays.asList( this.ValueLongArray ) ) + "|" + this.ValueSubObject;
		}
	}
	
	public class SubCompareObjectProperties {
		private double ValueDbl;
		private boolean ValueBool;
		private java.math.BigDecimal[] ValueDecimalArray;
		private java.time.LocalDateTime ValueDateTime;
		
		public double getValueDbl() {
			return this.ValueDbl;
		}
		
		public boolean getValueBool() {
			return this.ValueBool;
		}
		
		public java.math.BigDecimal[] getValueDecimalArray() {
			return this.ValueDecimalArray;
		}
		
		public java.time.LocalDateTime getValueDateTime() {
			return this.ValueDateTime;
		}
		
		public SubCompareObjectProperties(double p_s_str, boolean p_i_int, java.math.BigDecimal[] p_o_localTime, java.time.LocalDateTime p_a_longArray) {
			this.ValueDbl = p_s_str;
			this.ValueBool = p_i_int;
			this.ValueDecimalArray = p_o_localTime;
			this.ValueDateTime = p_a_longArray;
		}
		
		@Override
		public String toString() {
			return this.ValueDbl + "|" + this.ValueBool + "|" + net.forestany.forestj.lib.Helper.printArrayList( java.util.Arrays.asList( this.ValueDecimalArray ) ) + "|" + this.ValueDateTime;
		}
	}
}
