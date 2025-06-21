package net.forestany.forestj.lib.test.sqltest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class to test base instances
 */
public class BaseTest {
	/**
	 * empty constructor
	 */
	public BaseTest() {
		
	}
	
	/**
	 * base host ip
	 */
	//public static final String s_baseHost = "172.28.234.162";
	public static final String s_baseHost = "192.168.122.105";
	
	/**
	 * method to test base instances
	 */
	public static void testBase() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			
			net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
			
			/* o_glob.LogCompleteSqlQuery(true); */
				
			int i = 1;
			
			try {
				java.util.Date o_dateTime = null;
				java.util.Date o_date = null;
				java.util.Date o_time = null;
				
				try {
					o_dateTime = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss", java.util.Locale.GERMAN).parse("15.12.2003 08:33:03");
					o_date = new java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.GERMAN).parse("29.06.2009");
					o_time = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("11:01:43");
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException(o_exc);
				}
				
				java.time.LocalDateTime o_localDateTime = java.time.LocalDateTime.of(2010, 9, 2, 5, 55, 13);
				java.time.LocalDate o_localDate = java.time.LocalDate.of(2018, 11, 16);
				java.time.LocalTime o_localTime = java.time.LocalTime.of(17, 42, 23);
				
				int i_start = 1;
				int i_end = 23;
				
				if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
					i_end = 28;
				}
				
				for (i = i_start; i <= i_end; i++) {
					/* other order for nosqlmdb */
					if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
						if ( (i == 3) || (i == 8) ) {
							continue;
						}
						
						if (i == 12) {
							i = 8;
						} else if (i == 13) {
							i = 3;
						} else if (i >= 14) {
							i = i - 2;
						}
					}
					
					net.forestany.forestj.lib.sql.Query<?> o_query = net.forestany.forestj.lib.test.sqltest.QueryTest.testQueryGenerator(i);
					java.util.List<java.util.LinkedHashMap<String, Object>> a_result = o_glob.Base.fetchQuery(o_query);
					
					if (i <= 8) {
						int i_expectedAffectedRows = 0;
						
						if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
							i_expectedAffectedRows = 1;
						}
						
						assertTrue(
							a_result.size() == 1,
							"Result row amount of query #" + i + " is not '1', it is '" + a_result.size() + "'"
						);
						
						java.util.Map.Entry<String, Object> o_resultEntry = a_result.get(0).entrySet().iterator().next();
						
						assertTrue(
							o_resultEntry.getKey().contentEquals("AffectedRows"),
							"Result row key of query #" + i + " is not 'AffectedRows', it is '" + o_resultEntry.getKey() + "'"
						);
						
						assertTrue(
							Integer.valueOf(o_resultEntry.getValue().toString()) == i_expectedAffectedRows,
							"Result row value of query #" + i + " is not '" + i_expectedAffectedRows + "', it is '" + o_resultEntry.getValue().toString() + "'"
						);
					} else if ( (i >= 9) && (i <= 11) ) {
						assertTrue(
							a_result.size() == 1,
							"Result row amount of query #" + i + " is not '1', it is '" + a_result.size() + "'"
						);
						
						for (java.util.LinkedHashMap<String, Object> o_row : a_result) {
							int j = 0;
							
							for (java.util.Map.Entry<String, Object> o_column : o_row.entrySet()) {
								if (j == 0) {
									assertTrue(
										o_column.getKey().contentEquals("AffectedRows"),
										"Result row key of query #" + i + " is not 'AffectedRows', it is '" + o_column.getKey() + "'"
									);
									
									assertTrue(
										Integer.valueOf(o_column.getValue().toString()) == 1,
										"Result row value of query #" + i + " is not '1'"
									);
								} else {
									assertTrue(
											o_column.getKey().contentEquals("LastInsertId"),
										"Result row key of query #" + i + " is not 'LastInsertId', it is '" + o_column.getKey() + "'"
									);
									
									int i_lastInsertId = 1;
									
									if (i == 10) {
										i_lastInsertId = 2;
									} else if (i == 11) {
										i_lastInsertId = 3;
									}
									
									assertTrue(
										Integer.valueOf(o_column.getValue().toString()) == i_lastInsertId,
										"Result row value of query #" + i + " is not '" + i_lastInsertId + "', it is '" + o_column.getValue().toString() + "'"
									);
								}
								
								j++;
							}
						}
					} else if (i == 12) {
						assertTrue(
							a_result.size() == 0,
							"Result row amount of query #" + i + " is not '0', it is '" + a_result.size() + "'"
						);
					} else if (i == 13) {
						assertTrue(
							a_result.size() == 1,
							"Result row amount of query #" + i + " is not '1', it is '" + a_result.size() + "'"
						);
						
						java.util.Map.Entry<String, Object> o_resultEntry = a_result.get(0).entrySet().iterator().next();
						
						assertTrue(
							o_resultEntry.getKey().contentEquals("AffectedRows"),
							"Result row key of query #" + i + " is not 'AffectedRows', it is '" + o_resultEntry.getKey() + "'"
						);
						
						assertTrue(
							Integer.valueOf(o_resultEntry.getValue().toString()) == 3,
							"Result row value of query #" + i + " is not '3', it is '" + o_resultEntry.getValue().toString() + "'"
						);
					} else if ( (i >= 14) && (i <= 15) ) {
						assertTrue(
							a_result.size() == 0,
							"Result row amount of query #" + i + " is not '0', it is '" + a_result.size() + "'"
						);
					} else if (i == 16) {
						assertTrue(
							a_result.size() == 3,
							"Result row amount of query #" + i + " is not '3', it is '" + a_result.size() + "'"
						);
						
						/* very strict unit test for expected types and values */
						
						int j = 0;
						
						for (java.util.LinkedHashMap<String, Object> o_row : a_result) {
							int k = 0;
							
							for (java.util.Map.Entry<String, Object> o_column : o_row.entrySet()) {
								Object o_object = o_column.getValue();
								int l = 0;
								
								if (k == l++) { /* Id */
									if (j == 0) {
										assertEquals(Integer.valueOf(1), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '1'");
									} else if (j == 1) {
										assertEquals(Integer.valueOf(2), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '2'");
									} else if (j == 2) {
										assertEquals(Integer.valueOf(3), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '3'");
									}
								} else if (k == l++) { /* UUID */
									if (j == 0) {
										assertEquals("123e4567-e89b-42d3-a456-556642440000", o_object, "object[" + o_object.toString() + "] is not equal to '123e4567-e89b-42d3-a456-556642440000'");
									} else if (j == 1) {
										assertEquals("223e4567-e89b-42d3-a456-556642440000", o_object, "object[" + o_object.toString() + "] is not equal to '223e4567-e89b-42d3-a456-556642440000'");
									} else if (j == 2) {
										assertEquals("323e4567-e89b-42d3-a456-556642440000", o_object, "object[" + o_object.toString() + "] is not equal to '323e4567-e89b-42d3-a456-556642440000'");
									}
								} else if (k == l++) { /* ShortText */
									assertEquals("Wert", o_object, "object[" + o_object.toString() + "] is not equal to 'Wert'");
								} else if (k == l++) { /* Text */
									assertEquals("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.", o_object, "object[" + o_object.toString() + "] is not equal to 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.'");
								} else if (k == l++) { /* SmallInt */
									/* nosqlmdb does not support short or smallint, only int32 and long */
									if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
										if (j == 0) {
											assertEquals(Integer.valueOf("123"), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '123'");
										} else if (j == 1) {
											assertEquals(Integer.valueOf("223"), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '223'");
										} else if (j == 2) {
											assertEquals(Integer.valueOf("323"), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '323'");
										}
									} else {
										if (j == 0) {
											assertEquals(Short.valueOf("123"), Short.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '123'");
										} else if (j == 1) {
											assertEquals(Short.valueOf("223"), Short.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '223'");
										} else if (j == 2) {
											assertEquals(Short.valueOf("323"), Short.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '323'");
										}
									}
								} else if (k == l++) { /* Int */
									assertEquals(Integer.valueOf(1337), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '1337'");
								} else if (k == l++) { /* BigInt */
									if (j == 0) {
										assertEquals(Long.valueOf("1234567890123"), Long.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '1234567890123'");
									} else if (j == 1) {
										assertEquals(Long.valueOf("2234567890123"), Long.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '2234567890123'");
									} else if (j == 2) {
										assertEquals(Long.valueOf("3234567890123"), Long.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '3234567890123'");
									}
								} else if (k == l++) { /* DateTime */
									assertTrue(o_object instanceof java.sql.Timestamp, "object class[" + o_object.getClass().getTypeName() + "] is not of instance 'java.sql.Timestamp'");
									java.util.Date o_dateTimeAsDate = new java.util.Date((java.sql.Timestamp.class.cast(o_object)).getTime());
									assertEquals(o_dateTimeAsDate, o_dateTime, "object[" + o_dateTimeAsDate + "] is not equal to [" + o_dateTime + "]");
								} else if (k == l++) { /* Date */
									assertTrue(o_object instanceof java.sql.Timestamp, "object class[" + o_object.getClass().getTypeName() + "] is not of instance 'java.sql.Timestamp'");
									java.util.Date o_dateAsDate = new java.util.Date((java.sql.Timestamp.class.cast(o_object)).getTime());
									assertEquals(o_dateAsDate, o_date, "object[" + o_dateAsDate + "] is not equal to [" + o_date + "]");
								} else if (k == l++) { /* Time */
									assertTrue(o_object instanceof java.sql.Time, "object class[" + o_object.getClass().getTypeName() + "] is not of instance 'java.sql.Time'");
									java.util.Date o_timeAsDate = new java.util.Date((java.sql.Time.class.cast(o_object)).getTime());
									assertEquals(o_timeAsDate, o_time, "object[" + o_timeAsDate + "] is not equal to [" + o_time + "]");
								} else if (k == l++) { /* LocalDateTime */
									assertTrue(o_object instanceof java.sql.Timestamp, "object class[" + o_object.getClass().getTypeName() + "] is not of instance 'java.sql.Timestamp'");
									assertEquals((java.sql.Timestamp.class.cast(o_object)).toLocalDateTime(), o_localDateTime, "object[" + (java.sql.Timestamp.class.cast(o_object)).toLocalDateTime() + "] is not equal to [" + o_localDateTime + "]");
								} else if (k == l++) { /* LocalDate */
									assertTrue(o_object instanceof java.sql.Timestamp, "object class[" + o_object.getClass().getTypeName() + "] is not of instance 'java.sql.Timestamp'");
									assertEquals((java.sql.Timestamp.class.cast(o_object)).toLocalDateTime().toLocalDate(), o_localDate, "object[" + (java.sql.Timestamp.class.cast(o_object)).toLocalDateTime().toLocalDate() + "] is not equal to [" + o_localDate + "]");
								} else if (k == l++) { /* LocalTime */
									assertTrue(o_object instanceof java.sql.Time, "object class[" + o_object.getClass().getTypeName() + "] is not of instance 'java.sql.Time'");
									assertEquals((java.sql.Time.class.cast(o_object)).toLocalTime(), o_localTime, "object[" + (java.sql.Time.class.cast(o_object)).toLocalTime() + "] is not equal to [" + o_localTime + "]");
								} else if (k == l++) { /* DoubleCol */
									assertEquals(Double.valueOf(35.67d), Double.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '35.67' double");
								} else if (k == l++) { /* Decimal */
									assertTrue(o_object instanceof java.math.BigDecimal, "object class[" + o_object.getClass().getTypeName() + "] is not of instance 'java.math.BigDecimal'");
									assertEquals(new java.math.BigDecimal("2.718281828"), java.math.BigDecimal.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '2.718281828' bigdecimal");
								} else if (k == l++) { /* Bool */
									String s_objectValue = o_object.toString();
									
									/* convert '1' to 'true' for PGSQL */
									if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.PGSQL) {
										if (s_objectValue.contentEquals("1")) {
											s_objectValue = "true";
										}
									}
									
									if (j == 0) {
										assertEquals(true, Boolean.parseBoolean(s_objectValue), "object[" + o_object.toString() + "] is not equal to 'true'");
									} else if (j == 1) {
										assertEquals(false, Boolean.parseBoolean(s_objectValue), "object[" + o_object.toString() + "] is not equal to 'false'");
									} else if (j == 2) {
										assertEquals(true, Boolean.parseBoolean(s_objectValue), "object[" + o_object.toString() + "] is not equal to 'true'");
									}
								} else if (k == l++) { /* Text2Changed */
									/* because we renamed Text2Changed to Text2 in nosqlmdb it is at the end, JSON is unordered by RFC default */
									if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
										assertEquals("another short text", o_object, "object[" + o_object.toString() + "] is not equal to 'another short text'");
									} else {
										assertEquals("At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.", o_object, "object[" + o_object.toString() + "] is not equal to 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.'");
									}
								} else if (k == l++) { /* ShortText2 */	
									/* because we renamed Text2Changed to Text2 in nosqlmdb it is at the end, JSON is unordered by RFC default */
									if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
										assertEquals("At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.", o_object, "object[" + o_object.toString() + "] is not equal to 'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.'");
									} else {
										assertEquals("another short text", o_object, "object[" + o_object.toString() + "] is not equal to 'another short text'");
									}
								} else if (k == l++) { /* ShortText3 */
									/* only nosqlmdb has ShortText3 column */
									if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
										assertEquals(null, o_object, "object is not equal to 'null'");
									}
								} else if (k == l++) { /* Text3 */
									/* only nosqlmdb has Text3 column */
									if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
										assertEquals(null, o_object, "object is not equal to 'null'");
									}
								}
								
								k++;
							}
						
							j++;
						}
					} else if ( (i >= 17) && (i <= 23) ) {
						int i_expectedAffectedRows = 0;
						
						if ( (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.SQLITE) && ((i >= 18) && (i <= 20)) ) {
							i_expectedAffectedRows = 3;
						}
						
						if ( (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.MSSQL) && (i == 21) ) {
							i_expectedAffectedRows = -1;
						}
						
						if ( (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) && ((i >= 18) && (i <= 23)) ) {
							i_expectedAffectedRows = 1;
						}
						
						assertTrue(
							a_result.size() == 1,
							"Result row amount of query #" + i + " is not '1', it is '" + a_result.size() + "'"
						);
						
						java.util.Map.Entry<String, Object> o_resultEntry = a_result.get(0).entrySet().iterator().next();
						
						assertTrue(
							o_resultEntry.getKey().contentEquals("AffectedRows"),
							"Result row key of query #" + i + " is not 'AffectedRows', it is '" + o_resultEntry.getKey() + "'"
						);
						
						assertTrue(
							Integer.valueOf(o_resultEntry.getValue().toString()) == i_expectedAffectedRows,
							"Result row value of query #" + i + " is not '" + i_expectedAffectedRows + "', it is '" + o_resultEntry.getValue().toString() + "'"
						);
					} else if (i == 24) {
						assertTrue(
							a_result.size() == 16,
							"Result row amount of query #" + i + " is not '16', it is '" + a_result.size() + "'"
						);
						
						int[] a_rowNums = new int[] {1, 5, 9, 12};
						
						for (int i_rowNum : a_rowNums) {
							int i_rowPointer = 0;
							
							for (java.util.Map.Entry<String, Object> o_column : a_result.get(i_rowNum).entrySet()) {
								Object o_object = o_column.getValue();
								
								if (i_rowPointer == 0) { /* ProductID */
									if (i_rowNum == 1) {
										assertEquals(Integer.valueOf(64), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '64'");
									} else if (i_rowNum == 5) {
										assertEquals(Integer.valueOf(73), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '73'");
									} else if (i_rowNum == 9) {
										assertEquals(Integer.valueOf(54), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '54'");
									} else if (i_rowNum == 12) {
										assertEquals(Integer.valueOf(57), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '57'");
									}
								} else if (i_rowPointer == 1) { /* ProductName */
									if (i_rowNum == 1) {
										assertEquals("Wimmers gute Semmelknödel", o_object, "object[" + o_object.toString() + "] is not equal to 'Wimmers gute Semmelknödel'");
									} else if (i_rowNum == 5) {
										assertEquals("Röd Kaviar", o_object, "object[" + o_object.toString() + "] is not equal to 'Röd Kaviar'");
									} else if (i_rowNum == 9) {
										assertEquals("Tourtière", o_object, "object[" + o_object.toString() + "] is not equal to 'Tourtière'");
									} else if (i_rowNum == 12) {
										assertEquals("Ravioli Angelo", o_object, "object[" + o_object.toString() + "] is not equal to 'Ravioli Angelo'");
									}
								} else if (i_rowPointer == 2) { /* SupplierID */
									if (i_rowNum == 1) {
										assertEquals(Integer.valueOf(12), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '12'");
									} else if (i_rowNum == 5) {
										assertEquals(Integer.valueOf(17), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '17'");
									} else if (i_rowNum == 9) {
										assertEquals(Integer.valueOf(25), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '25'");
									} else if (i_rowNum == 12) {
										assertEquals(Integer.valueOf(26), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '26'");
									}
								} else if (i_rowPointer == 3) { /* CategoryID */
									if (i_rowNum == 1) {
										assertEquals(Integer.valueOf(5), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '5'");
									} else if (i_rowNum == 5) {
										assertEquals(Integer.valueOf(8), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '8'");
									} else if (i_rowNum == 9) {
										assertEquals(Integer.valueOf(6), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '6'");
									} else if (i_rowNum == 12) {
										assertEquals(Integer.valueOf(5), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '5'");
									}
								} else if (i_rowPointer == 4) { /* Unit */
									if (i_rowNum == 1) {
										assertEquals("20 bags x 4 pieces", o_object, "object[" + o_object.toString() + "] is not equal to '20 bags x 4 pieces'");
									} else if (i_rowNum == 5) {
										assertEquals("24 - 150 g jars", o_object, "object[" + o_object.toString() + "] is not equal to '24 - 150 g jars'");
									} else if (i_rowNum == 9) {
										assertEquals("16 pies", o_object, "object[" + o_object.toString() + "] is not equal to '16 pies'");
									} else if (i_rowNum == 12) {
										assertEquals("24 - 250 g pkgs.", o_object, "object[" + o_object.toString() + "] is not equal to '24 - 250 g pkgs.'");
									}
								} else if (i_rowPointer == 5) { /* Price */
									if (i_rowNum == 1) {
										assertEquals(Double.valueOf(33.25d), Double.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '33.25' double");
									} else if (i_rowNum == 5) {
										assertEquals(Integer.valueOf(15), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '15' integer");
									} else if (i_rowNum == 9) {
										assertEquals(Double.valueOf(7.45d), Double.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '7.45' double");
									} else if (i_rowNum == 12) {
										assertEquals(Double.valueOf(19.5d), Double.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '19.5' double");
									}
								} else if (i_rowPointer == 5) { /* CategoryName */
									if (i_rowNum == 1) {
										assertEquals("Grains/Cereals", o_object, "object[" + o_object.toString() + "] is not equal to 'Grains/Cereals'");
									} else if (i_rowNum == 5) {
										assertEquals("Seafood", o_object, "object[" + o_object.toString() + "] is not equal to 'Seafood'");
									} else if (i_rowNum == 9) {
										assertEquals("Meat/Poultry", o_object, "object[" + o_object.toString() + "] is not equal to 'Meat/Poultry'");
									} else if (i_rowNum == 12) {
										assertEquals("Grains/Cereals", o_object, "object[" + o_object.toString() + "] is not equal to 'Grains/Cereals'");
									}
								} else if (i_rowPointer == 5) { /* Description */
									if (i_rowNum == 1) {
										assertEquals("Breads, crackers, pasta, and cereal", o_object, "object[" + o_object.toString() + "] is not equal to 'Breads, crackers, pasta, and cereal'");
									} else if (i_rowNum == 5) {
										assertEquals("Seaweed and fish", o_object, "object[" + o_object.toString() + "] is not equal to 'Seaweed and fish'");
									} else if (i_rowNum == 9) {
										assertEquals("Prepared meats", o_object, "object[" + o_object.toString() + "] is not equal to 'Prepared meats'");
									} else if (i_rowNum == 12) {
										assertEquals("Breads, crackers, pasta, and cereal", o_object, "object[" + o_object.toString() + "] is not equal to 'Breads, crackers, pasta, and cereal'");
									}
								}
								
								i_rowPointer++;
							}
						}
					} else if (i == 25) {
						assertTrue(
							a_result.size() == 7,
							"Result row amount of query #" + i + " is not '7', it is '" + a_result.size() + "'"
						);
						
						int[] a_rowNums = new int[] {0, 2, 5};
						
						for (int i_rowNum : a_rowNums) {
							int i_rowPointer = 0;
							
							for (java.util.Map.Entry<String, Object> o_column : a_result.get(i_rowNum).entrySet()) {
								Object o_object = o_column.getValue();
								
								if (i_rowPointer == 0) { /* ProductName */
									if (i_rowNum == 0) {
										assertEquals("Raclette Courdavault", o_object, "object[" + o_object.toString() + "] is not equal to 'Raclette Courdavault'");
									} else if (i_rowNum == 2) {
										assertEquals("Manjimup Dried Apples", o_object, "object[" + o_object.toString() + "] is not equal to 'Manjimup Dried Apples'");
									} else if (i_rowNum == 5) {
										assertEquals("Carnarvon Tigers", o_object, "object[" + o_object.toString() + "] is not equal to 'Carnarvon Tigers'");
									}
								} else if (i_rowPointer == 1) { /* SupplierID */
									if (i_rowNum == 0) {
										assertEquals(Integer.valueOf(28), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '28'");
									} else if (i_rowNum == 2) {
										assertEquals(Integer.valueOf(24), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '24'");
									} else if (i_rowNum == 5) {
										assertEquals(Integer.valueOf(7), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '7'");
									}
								} else if (i_rowPointer == 2) { /* Unit */
									if (i_rowNum == 0) {
										assertEquals("5 kg pkg.", o_object, "object[" + o_object.toString() + "] is not equal to '5 kg pkg.'");
									} else if (i_rowNum == 2) {
										assertEquals("50 - 300 g pkgs.", o_object, "object[" + o_object.toString() + "] is not equal to '50 - 300 g pkgs.'");
									} else if (i_rowNum == 5) {
										assertEquals("16 kg pkg.", o_object, "object[" + o_object.toString() + "] is not equal to '16 kg pkg.'");
									}
								} else if (i_rowPointer == 3) { /* Price */
									if (i_rowNum == 0) {
										assertEquals(Integer.valueOf(55), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '55' integer");
									} else if (i_rowNum == 2) {
										assertEquals(Integer.valueOf(53), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '53' integer");
									} else if (i_rowNum == 5) {
										assertEquals(Double.valueOf(62.5d), Double.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '62.5' double");
									}
								} else if (i_rowPointer == 4) { /* ProductID */
									if (i_rowNum == 0) {
										assertEquals(Integer.valueOf(2), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '2'");
									} else if (i_rowNum == 2) {
										assertEquals(Integer.valueOf(3), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '3'");
									} else if (i_rowNum == 5) {
										assertEquals(Integer.valueOf(5), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '5'");
									}
								}
								
								i_rowPointer++;
							}
						}
					} else if (i == 26) {
						assertTrue(
							a_result.size() == 4,
							"Result row amount of query #" + i + " is not '4', it is '" + a_result.size() + "'"
						);
						
						int[] a_rowNums = new int[] {1, 3};
						
						for (int i_rowNum : a_rowNums) {
							int i_rowPointer = 0;
							
							for (java.util.Map.Entry<String, Object> o_column : a_result.get(i_rowNum).entrySet()) {
								Object o_object = o_column.getValue();
								
							if (i_rowPointer == 0) { /* ProductName */
								if (i_rowNum == 1) {
									assertEquals("Queso Cabrales", o_object, "object[" + o_object.toString() + "] is not equal to 'Queso Cabrales'");
								} else if (i_rowNum == 3) {
									assertEquals("Sirop d'érable", o_object, "object[" + o_object.toString() + "] is not equal to 'Sirop d'érable'");
								}
							} else if (i_rowPointer == 1) { /* SupplierID */
								if (i_rowNum == 1) {
									assertEquals(Integer.valueOf(5), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '5'");
								} else if (i_rowNum == 3) {
									assertEquals(Integer.valueOf(29), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '29'");
								}
							} else if (i_rowPointer == 2) { /* CategoryID */
								if (i_rowNum == 1) {
									assertEquals(Integer.valueOf(4), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '4'");
								} else if (i_rowNum == 3) {
									assertEquals(Integer.valueOf(2), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '2'");
								}
							} else if (i_rowPointer == 3) { /* Unit */
								if (i_rowNum == 1) {
									assertEquals("1 kg pkg.", o_object, "object[" + o_object.toString() + "] is not equal to '1 kg pkg.'");
								} else if (i_rowNum == 3) {
									assertEquals("24 - 500 ml bottles", o_object, "object[" + o_object.toString() + "] is not equal to '24 - 500 ml bottles'");
								}
							} else if (i_rowPointer == 4) { /* CategoryName */
								if (i_rowNum == 1) {
									assertEquals("Dairy Products", o_object, "object[" + o_object.toString() + "] is not equal to 'Dairy Products'");
								} else if (i_rowNum == 3) {
									assertEquals("Condiments", o_object, "object[" + o_object.toString() + "] is not equal to 'Condiments'");
								}
							} else if (i_rowPointer == 5) { /* Description */
								if (i_rowNum == 1) {
									assertEquals("Cheeses", o_object, "object[" + o_object.toString() + "] is not equal to 'Cheeses'");
								} else if (i_rowNum == 3) {
									assertEquals("Sweet and savory sauces, relishes, spreads, and seasonings", o_object, "object[" + o_object.toString() + "] is not equal to 'Sweet and savory sauces, relishes, spreads, and seasonings'");
								}
							} else if (i_rowPointer == 6) { /* Price */
								if (i_rowNum == 1) {
									assertEquals(Integer.valueOf(21), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '21' integer");
								} else if (i_rowNum == 3) {
									assertEquals(Double.valueOf(28.5d), Double.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '28.5' double");
								}
							} else if (i_rowPointer == 7) { /* ProductID */
								if (i_rowNum == 1) {
									assertEquals(Integer.valueOf(2), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '2'");
								} else if (i_rowNum == 3) {
									assertEquals(Integer.valueOf(2), Integer.class.cast(o_object), "object[" + o_object.toString() + "] is not equal to '2'");
								}
							}
								
								i_rowPointer++;
							}
						}
					}
					
					/* re-order for nosqlmdb for for loop increase */
					if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
						if (i == 8) {
							i = 12; 
						} else if (i == 3) {
							i = 13;
						} else if (i >= 12) {
							i = i + 2;
						}
						
						if (i == 28) {
							net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Drop> o_queryDrop = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Drop>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.DROP, "sys_forestj_categories");
							
							@SuppressWarnings("unused")
							java.util.List<java.util.LinkedHashMap<String, Object>> a_resultDrop = o_glob.Base.fetchQuery(o_queryDrop);
							
							o_queryDrop = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Drop>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.DROP, "sys_forestj_products");
							
							a_resultDrop = o_glob.Base.fetchQuery(o_queryDrop);
						}
					}
				}
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
				fail("Query #" + i + ": " + o_exc.getMessage());
			} finally {
				o_glob.Base.closeConnection();
			}
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
			fail(o_exc.getMessage());
		}
	}
}
