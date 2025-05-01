package net.forestany.forestj.lib.test.nettest.sock.com;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * class to test shared memory
 */
public class SharedMemoryTest {
	/**
	 * method to test shared memory
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testSharedMemory() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
						
			SharedMemoryExample o_testSharedMemory = new SharedMemoryExample();
			o_testSharedMemory.initiateMirrors();
			
			/* ---------- ---------- ---------- ---------- ---------- */
			
			assertEquals(
				o_testSharedMemory.returnFields(),
				"Id = 0|UUID = NULL|ShortText = NULL|Text = NULL|SmallInt = 0|Int = 0|BigInt = 0|Timestamp = NULL|Date = NULL|Time = NULL|LocalDateTime = NULL|LocalDate = NULL|LocalTime = NULL|DoubleCol = 0.0|Decimal = NULL|Bool = false|Text2 = NULL|ShortText2 = NULL|FloatValue = 0.0|",
				"shared memory ReturnFields returns wrong string"
			);
			
			/* ---------- ---------- ---------- ---------- ---------- */
			
			assertTrue(o_testSharedMemory.returnFieldName(6).contentEquals("Int"), "ReturnFieldName('6') must be 'Int'");
			assertTrue(o_testSharedMemory.returnFieldNumber("Int") == 6, "ReturnFieldNumber('Int') must be '6'");
			assertTrue(o_testSharedMemory.returnFieldName(8).contentEquals("Timestamp"), "ReturnFieldName('8') must be 'Timestamp'");
			assertTrue(o_testSharedMemory.returnFieldNumber("Timestamp") == 8, "ReturnFieldNumber('Timestamp') must be '8'");
			assertTrue(o_testSharedMemory.returnFieldName(7).contentEquals("BigInt"), "ReturnFieldName('7') must be 'BigInt'");
			assertTrue(o_testSharedMemory.returnFieldNumber("BigInt") == 7, "ReturnFieldNumber('BigInt') must be '7'");
			
			/* ---------- ---------- ---------- ---------- ---------- */
			
			o_testSharedMemory.setField("Text", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
			o_testSharedMemory.setField("Date", new java.text.SimpleDateFormat("dd.MM.yyyy").parse("02.02.2020"));
			o_testSharedMemory.setField("Int", 13579);
			
			java.util.List<String> a_changedFields = o_testSharedMemory.getChangedFields(true);
			
			assertTrue(a_changedFields.size() == 3, "changed fields amount is not '3', but '" + a_changedFields.size() + "'");
			
			int i = 0;
			
			for (String s_field : a_changedFields) {
				if (i == 0) {
					assertTrue(s_field.contentEquals("Text"), "Field is not 'Text', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == 4, "Field number if 'Text' is not '4'");
					assertEquals(o_testSharedMemory.getField(s_field), "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", "unexpected field value for 'Text'");
				} else if (i == 1) {
					assertTrue(s_field.contentEquals("Int"), "Field is not 'Int', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == 6, "Field number if 'Int' is not '6'");
					assertEquals((int)o_testSharedMemory.getField(s_field), 13579, "unexpected field value for 'Int'");
				} else if (i == 2) {
					assertTrue(s_field.contentEquals("Date"), "Field is not 'Date', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == 9, "Field number if 'Date' is not '9'");
					assertEquals(o_testSharedMemory.getField(s_field), new java.text.SimpleDateFormat("dd.MM.yyyy").parse("02.02.2020"), "unexpected field value for 'Date'");
				}
				
				i++;
			}
			
			/* ---------- ---------- ---------- ---------- ---------- */
			
			o_testSharedMemory.setField("SmallInt", (short)815);
					
			a_changedFields = o_testSharedMemory.getChangedFields(true);
			
			assertTrue(a_changedFields.size() == 1, "changed fields amount is not '1', but '" + a_changedFields.size() + "'");
			
			assertTrue(a_changedFields.get(0).contentEquals("SmallInt"), "Field is not 'SmallInt', but '" + a_changedFields.get(0) + "'");
			assertTrue(o_testSharedMemory.returnFieldNumber(a_changedFields.get(0)) == 5, "Field number if 'SmallInt' is not '5'");
			assertEquals((short)o_testSharedMemory.getField(a_changedFields.get(0)), 815, "unexpected field value for 'SmallInt'");
			
			/* ---------- ---------- ---------- ---------- ---------- */
			
			a_changedFields = o_testSharedMemory.getChangedFields(true);
			
			assertTrue(a_changedFields.size() == 0, "changed fields amount is not '0', but '" + a_changedFields.size() + "'");
			
			/* ---------- ---------- ---------- ---------- ---------- */
			
			o_testSharedMemory.setField("Id", (int)42);
			o_testSharedMemory.setField("UUID", "8a9804b2-cbd0-11ec-9d64-0242ac120002");
			o_testSharedMemory.setField("ShortText", "this is just a short text");
			o_testSharedMemory.setField("BigInt", (long)154735207);
			o_testSharedMemory.setField("Timestamp", new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("02.02.2020 22:20:12"));
			o_testSharedMemory.setField("Time", new java.text.SimpleDateFormat("HH:mm:ss").parse("22:20:12"));
			o_testSharedMemory.setField("LocalDateTime", java.time.LocalDateTime.of(2019, 2, 2, 2, 2, 2));
			o_testSharedMemory.setField("LocalDate", java.time.LocalDate.of(2019, 2, 2));
			o_testSharedMemory.setField("LocalTime", java.time.LocalTime.of(2, 2, 2));
			o_testSharedMemory.setField("DoubleCol", 1337.0d);
			o_testSharedMemory.setField("Decimal", new java.math.BigDecimal(31415926.535897932384));
			o_testSharedMemory.setField("Bool", true);
			o_testSharedMemory.setField("Text2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
			o_testSharedMemory.setField("ShortText2", "another short text");
			o_testSharedMemory.setField("FloatValue", 2.7182818284590f);
			
			a_changedFields = o_testSharedMemory.getChangedFields(true, true);
			
			assertTrue(a_changedFields.size() == 19, "changed fields amount is not '19', but '" + a_changedFields.size() + "'");
			
			i = 0;
			
			for (String s_field : a_changedFields) {
				if (i == 0) {
					assertTrue(s_field.contentEquals("Id"), "Field is not 'Id', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'Id' is not '" + (i + 1) + "'");
					assertEquals((int)o_testSharedMemory.getField(s_field), 42, "unexpected field value for 'Id'");
				} else if (i == 1) {
					assertTrue(s_field.contentEquals("UUID"), "Field is not 'UUID', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'UUID' is not '" + (i + 1) + "'");
					assertEquals(o_testSharedMemory.getField(s_field), "8a9804b2-cbd0-11ec-9d64-0242ac120002", "unexpected field value for 'UUID'");
				} else if (i == 2) {
					assertTrue(s_field.contentEquals("ShortText"), "Field is not 'ShortText', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'ShortText' is not '" + (i + 1) + "'");
					assertEquals(o_testSharedMemory.getField(s_field), "this is just a short text", "unexpected field value for 'ShortText'");
				} else if (i == 3) {
					assertTrue(s_field.contentEquals("Text"), "Field is not 'Text', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'Text' is not '" + (i + 1) + "'");
					assertEquals(o_testSharedMemory.getField(s_field), "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", "unexpected field value for 'Text'");
				} else if (i == 4) {
					assertTrue(s_field.contentEquals("SmallInt"), "Field is not 'SmallInt', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'SmallInt' is not '" + (i + 1) + "'");
					assertEquals((short)o_testSharedMemory.getField(s_field), (short)815, "unexpected field value for 'SmallInt'");
				} else if (i == 5) {
					assertTrue(s_field.contentEquals("Int"), "Field is not 'Int', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'Text' is not '" + (i + 1) + "'");
					assertEquals((int)o_testSharedMemory.getField(s_field), 13579, "unexpected field value for 'Int'");
				} else if (i == 6) {
					assertTrue(s_field.contentEquals("BigInt"), "Field is not 'BigInt', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'BigInt' is not '" + (i + 1) + "'");
					assertEquals((long)o_testSharedMemory.getField(s_field), (long)154735207, "unexpected field value for 'BigInt'");
				} else if (i == 7) {
					assertTrue(s_field.contentEquals("Timestamp"), "Field is not 'Timestamp', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'Timestamp' is not '" + (i + 1) + "'");
					assertEquals(o_testSharedMemory.getField(s_field), new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("02.02.2020 22:20:12"), "unexpected field value for 'Timestamp'");
				} else if (i == 8) {
					assertTrue(s_field.contentEquals("Date"), "Field is not 'Date', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'Date' is not '" + (i + 1) + "'");
					assertEquals(o_testSharedMemory.getField(s_field), new java.text.SimpleDateFormat("dd.MM.yyyy").parse("02.02.2020"), "unexpected field value for 'Date'");
				} else if (i == 9) {
					assertTrue(s_field.contentEquals("Time"), "Field is not 'Time', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'Time' is not '" + (i + 1) + "'");
					assertEquals(o_testSharedMemory.getField(s_field), new java.text.SimpleDateFormat("HH:mm:ss").parse("22:20:12"), "unexpected field value for 'Time'");
				} else if (i == 10) {
					assertTrue(s_field.contentEquals("LocalDateTime"), "Field is not 'LocalDateTime', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'LocalDateTime' is not '" + (i + 1) + "'");
					assertEquals(o_testSharedMemory.getField(s_field), java.time.LocalDateTime.of(2019, 2, 2, 2, 2, 2), "unexpected field value for 'LocalDateTime'");
				} else if (i == 11) {
					assertTrue(s_field.contentEquals("LocalDate"), "Field is not 'LocalDate', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'LocalDate' is not '" + (i + 1) + "'");
					assertEquals(o_testSharedMemory.getField(s_field), java.time.LocalDate.of(2019, 2, 2), "unexpected field value for 'LocalDate'");
				} else if (i == 12) {
					assertTrue(s_field.contentEquals("LocalTime"), "Field is not 'LocalTime', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'LocalTime' is not '" + (i + 1) + "'");
					assertEquals(o_testSharedMemory.getField(s_field), java.time.LocalTime.of(2, 2, 2), "unexpected field value for 'LocalTime'");
				} else if (i == 13) {
					assertTrue(s_field.contentEquals("DoubleCol"), "Field is not 'DoubleCol', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'DoubleCol' is not '" + (i + 1) + "'");
					assertEquals((double)o_testSharedMemory.getField(s_field), 1337.0d, "unexpected field value for 'DoubleCol'");
				} else if (i == 14) {
					assertTrue(s_field.contentEquals("Decimal"), "Field is not 'Decimal', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'Decimal' is not '" + (i + 1) + "'");
					assertEquals(o_testSharedMemory.getField(s_field), new java.math.BigDecimal(31415926.535897932384), "unexpected field value for 'Decimal'");
				} else if (i == 15) {
					assertTrue(s_field.contentEquals("Bool"), "Field is not 'Bool', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'Bool' is not '" + (i + 1) + "'");
					assertEquals((boolean)o_testSharedMemory.getField(s_field), true, "unexpected field value for 'Bool'");
				} else if (i == 16) {
					assertTrue(s_field.contentEquals("Text2"), "Field is not 'Text2', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'Text2' is not '" + (i + 1) + "'");
					assertEquals(o_testSharedMemory.getField(s_field), "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", "unexpected field value for 'Text2'");
				} else if (i == 17) {
					assertTrue(s_field.contentEquals("ShortText2"), "Field is not 'ShortText2', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'ShortText2' is not '" + (i + 1) + "'");
					assertEquals(o_testSharedMemory.getField(s_field), "another short text", "unexpected field value for 'ShortText2'");
				} else if (i == 18) {
					assertTrue(s_field.contentEquals("FloatValue"), "Field is not 'FloatValue', but '" + s_field + "'");
					assertTrue(o_testSharedMemory.returnFieldNumber(s_field) == (i + 1), "Field number if 'FloatValue' is not '" + (i + 1) + "'");
					assertEquals((float)o_testSharedMemory.getField(s_field), 2.7182818284590f, "unexpected field value for 'FloatValue'");
				}
				
				i++;
			}
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
			fail(o_exc.getMessage());
		}
	}
}
