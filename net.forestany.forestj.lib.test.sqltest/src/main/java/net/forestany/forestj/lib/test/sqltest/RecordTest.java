package net.forestany.forestj.lib.test.sqltest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class to test record instances
 */
public class RecordTest {
	/**
	 * empty constructor
	 */
	public RecordTest() {
		
	}
	
	/**
	 * method to test record instances
	 */
	public static void testRecord() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			
			net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
			
			try {
				/* o_glob.LogCompleteSqlQuery(true); */
				
				try {
					cleanupRecordTest(false);
				} catch (Exception o_exc) {
					/* does not matter */
				}
				
				prepareRecordTest();
				testLanguageRecord();
				testDDLRecord();
				cleanupRecordTest(true);
			} catch (Exception o_exc) {
				throw o_exc;
			} finally {
				o_glob.Base.closeConnection();
			}
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
	
	private static void prepareRecordTest() throws Exception {
		net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
		
		/* #### CREATE ############################################################################################# */
		net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create> o_queryCreate = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.CREATE, "sys_forestj_language");
		/* #### Columns ############################################################################################ */
		java.util.List<java.util.Properties> a_columnsDefinition = new java.util.ArrayList<java.util.Properties>();
		
		java.util.Properties o_properties = new java.util.Properties();
		o_properties.put("name", "Id");
		o_properties.put("columnType", "integer [int]");
		o_properties.put("constraints", "NOT NULL;PRIMARY KEY;AUTO_INCREMENT");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "UUID");
		o_properties.put("columnType", "text [36]");
		o_properties.put("constraints", "NOT NULL;UNIQUE");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "Code");
		o_properties.put("columnType", "text [36]");
		o_properties.put("constraints", "NOT NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "Language");
		o_properties.put("columnType", "text [36]");
		o_properties.put("constraints", "NULL;DEFAULT");
		o_properties.put("constraintDefaultValue", "NULL");
		a_columnsDefinition.add(o_properties);
		
		/* #### Query ############################################################################ */
		
		for (java.util.Properties o_columnDefinition : a_columnsDefinition) {
			net.forestany.forestj.lib.sql.ColumnStructure o_column = new net.forestany.forestj.lib.sql.ColumnStructure(o_queryCreate);
			o_column.columnTypeAllocation(o_columnDefinition.getProperty("columnType"));
			o_column.s_name = o_columnDefinition.getProperty("name");
			o_column.setAlterOperation("ADD");
			
			if (o_columnDefinition.containsKey("constraints")) {
				String[] a_constraints = o_columnDefinition.getProperty("constraints").split(";");
				
				for (int i = 0; i < a_constraints.length; i++) {
					o_column.addConstraint(o_queryCreate.constraintTypeAllocation(a_constraints[i]));
					
					if ( (a_constraints[i].compareTo("DEFAULT") == 0) && (o_columnDefinition.containsKey("constraintDefaultValue")) ) {
						o_column.setConstraintDefaultValue((Object)o_columnDefinition.getProperty("constraintDefaultValue"));
					}
				}
			}
			
			o_queryCreate.getQuery().a_columns.add(o_column);
		}
		
		java.util.List<java.util.LinkedHashMap<String, Object>> a_result = o_glob.Base.fetchQuery(o_queryCreate);
		
			int i_expectedAffectedRows = 0;
			
			if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
				i_expectedAffectedRows = 1;
			}
			
			assertTrue(
				a_result.size() == 1,
				"Result row amount of create query is not '1', it is '" + a_result.size() + "'"
			);
			
			java.util.Map.Entry<String, Object> o_resultEntry = a_result.get(0).entrySet().iterator().next();
			
			assertTrue(
				o_resultEntry.getKey().contentEquals("AffectedRows"),
				"Result row key of create query is not 'AffectedRows', it is '" + o_resultEntry.getKey() + "'"
			);
			
			assertTrue(
				Integer.valueOf(o_resultEntry.getValue().toString()) == i_expectedAffectedRows,
				"Result row value of create query is not '" + i_expectedAffectedRows + "', it is '" + o_resultEntry.getValue().toString() + "'"
			);
		
		/* #### ALTER ########################################################################################### */
		net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter> o_queryAlter = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Alter>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.ALTER, "sys_forestj_language");
		/* #### Constraints ##################################################################################### */
		net.forestany.forestj.lib.sql.Constraint o_constraint = new net.forestany.forestj.lib.sql.Constraint(o_queryAlter, "UNIQUE", "sys_forestj_language_unique", "", "ADD");
			o_constraint.a_columns.add("Code");
			o_constraint.a_columns.add("Language");
			
		o_queryAlter.getQuery().a_constraints.add(o_constraint);
		
		a_result = o_glob.Base.fetchQuery(o_queryAlter);
		
			i_expectedAffectedRows = 0;
			
			if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
				i_expectedAffectedRows = 1;
			}
			
			assertTrue(
				a_result.size() == 1,
				"Result row amount of create query is not '1', it is '" + a_result.size() + "'"
			);
			
			o_resultEntry = a_result.get(0).entrySet().iterator().next();
			
			assertTrue(
				o_resultEntry.getKey().contentEquals("AffectedRows"),
				"Result row key of create query is not 'AffectedRows', it is '" + o_resultEntry.getKey() + "'"
			);
			
			assertTrue(
				Integer.valueOf(o_resultEntry.getValue().toString()) == i_expectedAffectedRows,
				"Result row value of create query is not '" + i_expectedAffectedRows + "', it is '" + o_resultEntry.getValue().toString() + "'"
			);
		
		/* #### CREATE ############################################################################# */
		o_queryCreate = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.CREATE, "sys_forestj_testddl2");
		/* #### Columns ############################################################################ */
		a_columnsDefinition = new java.util.ArrayList<java.util.Properties>();
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "Id");
		o_properties.put("columnType", "integer [int]");
		o_properties.put("constraints", "NOT NULL;PRIMARY KEY;AUTO_INCREMENT");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "UUID");
		o_properties.put("columnType", "text [36]");
		o_properties.put("constraints", "NOT NULL;UNIQUE");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "ShortText");
		o_properties.put("columnType", "text [255]");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "Text");
		o_properties.put("columnType", "text");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "SmallInt");
		o_properties.put("columnType", "integer [small]");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "Int");
		o_properties.put("columnType", "integer [int]");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "BigInt");
		o_properties.put("columnType", "integer [big]");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "Timestamp");
		o_properties.put("columnType", "datetime");
		o_properties.put("constraints", "NULL;DEFAULT");
		o_properties.put("constraintDefaultValue", "1999-12-31 23:00:00");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "Date");
		o_properties.put("columnType", "datetime");
		o_properties.put("constraints", "NULL;DEFAULT");
		o_properties.put("constraintDefaultValue", "2004-04-04 00:00:00");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "Time");
		o_properties.put("columnType", "time");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "LocalDateTime");
		o_properties.put("columnType", "datetime");
		o_properties.put("constraints", "NULL;DEFAULT");
		o_properties.put("constraintDefaultValue", "CURRENT_TIMESTAMP");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "LocalDate");
		o_properties.put("columnType", "datetime");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "LocalTime");
		o_properties.put("columnType", "time");
		o_properties.put("constraints", "DEFAULT");
		o_properties.put("constraintDefaultValue", "12:24:46");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "DoubleCol");
		o_properties.put("columnType", "double");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "Decimal");
		o_properties.put("columnType", "decimal");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);
		
		o_properties = new java.util.Properties();
		o_properties.put("name", "Bool");
		o_properties.put("columnType", "bool");
		o_properties.put("constraints", "NULL");
		a_columnsDefinition.add(o_properties);

		o_properties = new java.util.Properties();
		o_properties.put("name", "Text2");
		o_properties.put("columnType", "text [36]");
		o_properties.put("constraints", "DEFAULT");
		o_properties.put("constraintDefaultValue", "Das ist das Haus vom Nikolaus");
		a_columnsDefinition.add(o_properties);

		o_properties = new java.util.Properties();
		o_properties.put("name", "ShortText2");
		o_properties.put("columnType", "text [255]");
		o_properties.put("constraints", "DEFAULT");
		o_properties.put("constraintDefaultValue", "NULL");
		a_columnsDefinition.add(o_properties);
		
		/* #### Query ############################################################################ */
		
		for (java.util.Properties o_columnDefinition : a_columnsDefinition) {
			net.forestany.forestj.lib.sql.ColumnStructure o_column = new net.forestany.forestj.lib.sql.ColumnStructure(o_queryCreate);
			o_column.columnTypeAllocation(o_columnDefinition.getProperty("columnType"));
			o_column.s_name = o_columnDefinition.getProperty("name");
			o_column.setAlterOperation("ADD");
			
			if (o_columnDefinition.containsKey("constraints")) {
				String[] a_constraints = o_columnDefinition.getProperty("constraints").split(";");
				
				for (int i = 0; i < a_constraints.length; i++) {
					o_column.addConstraint(o_queryCreate.constraintTypeAllocation(a_constraints[i]));
					
					if ( (a_constraints[i].compareTo("DEFAULT") == 0) && (o_columnDefinition.containsKey("constraintDefaultValue")) ) {
						o_column.setConstraintDefaultValue((Object)o_columnDefinition.getProperty("constraintDefaultValue"));
					}
				}
			}
			
			o_queryCreate.getQuery().a_columns.add(o_column);
		}
		
		a_result = o_glob.Base.fetchQuery(o_queryCreate);
		
			i_expectedAffectedRows = 0;
			
			if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
				i_expectedAffectedRows = 1;
			}
			
			assertTrue(
				a_result.size() == 1,
				"Result row amount of create query is not '1', it is '" + a_result.size() + "'"
			);
			
			o_resultEntry = a_result.get(0).entrySet().iterator().next();
			
			assertTrue(
				o_resultEntry.getKey().contentEquals("AffectedRows"),
				"Result row key of create query is not 'AffectedRows', it is '" + o_resultEntry.getKey() + "'"
			);
			
			assertTrue(
				Integer.valueOf(o_resultEntry.getValue().toString()) == i_expectedAffectedRows,
				"Result row value of create query is not '" + i_expectedAffectedRows + "', it is '" + o_resultEntry.getValue().toString() + "'"
			);
		
		/* #### INSERT ############################################################################ */
		
		java.util.List<java.util.Properties> a_insertColumnsDefinition = new java.util.ArrayList<java.util.Properties>();
		
		java.util.Properties o_insertProperties = new java.util.Properties();
		o_insertProperties.put("Id", 1);
		o_insertProperties.put("UUID", "9230337b-6cd9-11e9-b874-1062e50d1fcb");
		o_insertProperties.put("Code", "de-DE");
		o_insertProperties.put("Language", "Deutsch, Deutschland");
		a_insertColumnsDefinition.add(o_insertProperties);
		
		o_insertProperties = new java.util.Properties();
		o_insertProperties.put("Id", 2);
		o_insertProperties.put("UUID", "942b5547-6cd9-11e9-b874-1062e50d1fcb");
		o_insertProperties.put("Code", "en-US");
		o_insertProperties.put("Language", "English, United States");
		a_insertColumnsDefinition.add(o_insertProperties);
		
		o_insertProperties = new java.util.Properties();
		o_insertProperties.put("Id", 3);
		o_insertProperties.put("UUID", "966996d3-6cd9-11e9-b874-1062e50d1fcb");
		o_insertProperties.put("Code", "en-GB");
		o_insertProperties.put("Language", "English, Großbritannien");
		a_insertColumnsDefinition.add(o_insertProperties);
		
		o_insertProperties = new java.util.Properties();
		o_insertProperties.put("Id", 4);
		o_insertProperties.put("UUID", "44c37d45-222f-11ea-80e3-c85b7608f0ba");
		o_insertProperties.put("Code", "it-IT");
		o_insertProperties.put("Language", "Italian, Italy");
		a_insertColumnsDefinition.add(o_insertProperties);
		
		o_insertProperties = new java.util.Properties();
		o_insertProperties.put("Id", 5);
		o_insertProperties.put("UUID", "4f4bc151-222f-11ea-80e3-c85b7608f0ba");
		o_insertProperties.put("Code", "es-SP");
		o_insertProperties.put("Language", "Spanisch, Spaniens");
		a_insertColumnsDefinition.add(o_insertProperties);
		
		o_insertProperties = new java.util.Properties();
		o_insertProperties.put("Id", 6);
		o_insertProperties.put("UUID", "5bb8176f-222f-11ea-80e3-c85b7608f0ba");
		o_insertProperties.put("Code", "jp-JP");
		o_insertProperties.put("Language", "Japanese, Japan");
		a_insertColumnsDefinition.add(o_insertProperties);
		
		o_insertProperties = new java.util.Properties();
		o_insertProperties.put("Id", 7);
		o_insertProperties.put("UUID", "fe01d7d6-2232-11ea-80e3-c85b7608f0ba");
		o_insertProperties.put("Code", "cz-CZ");
		o_insertProperties.put("Language", "Tschechisch, Tschechien");
		a_insertColumnsDefinition.add(o_insertProperties);
		
		o_insertProperties = new java.util.Properties();
		o_insertProperties.put("Id", 8);
		o_insertProperties.put("UUID", "176176e2-57ff-4cef-baf5-7e2597f2a520");
		o_insertProperties.put("Code", "en-AU");
		o_insertProperties.put("Language", "English, Australia");
		a_insertColumnsDefinition.add(o_insertProperties);
		
		o_insertProperties = new java.util.Properties();
		o_insertProperties.put("Id", 9);
		o_insertProperties.put("UUID", "d38e447d-de17-47be-a26a-f57423fc439f");
		o_insertProperties.put("Code", "de-OE");
		o_insertProperties.put("Language", "Deutsch, Österreich");
		a_insertColumnsDefinition.add(o_insertProperties);
		
		int i = 1;
		
		for (java.util.Properties o_insertColumnDefinition : a_insertColumnsDefinition) {
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert> o_queryInsert = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.INSERT, "sys_forestj_language");
			/* #### Columns ############################################################################ */
			o_queryInsert.getQuery().o_nosqlmdbColumnAutoIncrement = new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Id");
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), o_insertColumnDefinition.get("UUID")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Code"), o_insertColumnDefinition.get("Code")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Language"), o_insertColumnDefinition.get("Language")) );
			
			a_result = o_glob.Base.fetchQuery(o_queryInsert);
			
				assertTrue(
					a_result.size() == 1,
					"Result row amount of insert query #" + i + " is not '1', it is '" + a_result.size() + "'"
				);
				
				for (java.util.LinkedHashMap<String, Object> o_row : a_result) {
					int j = 0;
					
					for (java.util.Map.Entry<String, Object> o_column : o_row.entrySet()) {
						if (j == 0) {
							assertTrue(
								o_column.getKey().contentEquals("AffectedRows"),
								"Result row key of insert query #" + i + " is not 'AffectedRows', it is '" + o_column.getKey() + "'"
							);
							
							assertTrue(
								Integer.valueOf(o_column.getValue().toString()) == 1,
								"Result row value of insert query #" + i + " is not '1'"
							);
						} else {
							assertTrue(
									o_column.getKey().contentEquals("LastInsertId"),
								"Result row key of insert query #" + i + " is not 'LastInsertId', it is '" + o_column.getKey() + "'"
							);
							
							assertTrue(
								Integer.valueOf(o_column.getValue().toString()) == i,
								"Result row value of insert query #" + i + " is not '" + i + "', it is '" + o_column.getValue().toString() + "'"
							);
						}
						
						j++;
					}
				}
				
			i++;
		}
		
		/* #### INSERT ############################################################################ */
		
		java.util.Date o_dateTime = null;
		java.util.Date o_date = null;
		java.util.Date o_time = null;
		
		try {
			o_dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMAN).parse("2019-01-01 00:01:01");
			o_date = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.GERMAN).parse("2001-01-01");
			o_time = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("01:01:01");
		} catch (Exception o_exc) {
			throw o_exc;
		}
		
		java.time.LocalDateTime o_localDateTime = java.time.LocalDateTime.of(2019, 1, 1, 0, 1, 1);
		java.time.LocalDate o_localDate = java.time.LocalDate.of(2001, 1, 1);
		java.time.LocalTime o_localTime = java.time.LocalTime.of(1, 1, 1);
		
		a_insertColumnsDefinition = new java.util.ArrayList<java.util.Properties>();
		
		o_insertProperties = new java.util.Properties();
		o_insertProperties.put("Id", 1);
		o_insertProperties.put("UUID", "32f80a3a-ff6e-4cc0-bb8f-ce630595382b");
		o_insertProperties.put("ShortText", "Datensatz Eins");
		o_insertProperties.put("Text", "Die Handelsstreitigkeiten zwischen den USA und China sorgen für eine Art Umdenken auf beiden Seiten. Während US-Unternehmen chinesische Hardware meiden, tun dies chinesische Unternehmen wohl mittlerweile auch: So denken laut einem Bericht der Nachrichtenagentur Bloomberg viele chinesische Hersteller stark darüber nach, ihre IT-Infrastruktur von lokalen Unternehmen statt von den US-Konzernen Oracle und IBM zu kaufen. Für diese Unternehmen sei der asiatische Markt wichtig. 16 respektive mehr als 20 Prozent des Umsatzes stammen aus dieser Region.");
		o_insertProperties.put("SmallInt", 1);
		o_insertProperties.put("Int", 10_001);
		o_insertProperties.put("BigInt", java.lang.Long.valueOf("100001111"));
		o_insertProperties.put("Timestamp", o_dateTime);
		o_insertProperties.put("Date", o_date);
		o_insertProperties.put("Time", o_time);
		o_insertProperties.put("LocalDateTime", o_localDateTime);
		o_insertProperties.put("LocalDate", o_localDate);
		o_insertProperties.put("LocalTime", o_localTime);
		o_insertProperties.put("DoubleCol", 1.23456789d);
		o_insertProperties.put("Decimal", java.math.BigDecimal.valueOf(12345678.90d));
		o_insertProperties.put("Bool", true);
		o_insertProperties.put("Text2", "Das ist das Haus vom Nikolaus #1");
		o_insertProperties.put("ShortText2", "Eins Datensatz");
		a_insertColumnsDefinition.add(o_insertProperties);
		
		try {
			o_dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMAN).parse("2019-02-02 01:02:02");
			o_date = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.GERMAN).parse("2002-02-02");
			o_time = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("02:02:02");
		} catch (Exception o_exc) {
			throw o_exc;
		}
		
		o_localDateTime = java.time.LocalDateTime.of(2019, 2, 2, 1, 2, 2);
		o_localDate = java.time.LocalDate.of(2002, 2, 2);
		o_localTime = java.time.LocalTime.of(2, 2, 2);
		
		o_insertProperties = new java.util.Properties();
		o_insertProperties.put("Id", 2);
		o_insertProperties.put("UUID", "390e413a-09df-41e3-aafe-8a116197da7f");
		o_insertProperties.put("ShortText", "Datensatz Zwei");
		o_insertProperties.put("Text", "Und hier ein single quote \'. Und dann noch ein Backslash \\. Das Tech-Startup Pingcap ist eines der lokalen Unternehmen, die den Handelsstreit zu ihrem Vorteil nutzen, für lokale chinesische Produkte werben und selbst von US-Hardware wegmigrieren. Mehr als 300 Kunden betreut die Firma, darunter der Fahrradsharing-Dienst Mobike und der chinesische Smartphone-Hersteller Xiaomi. Piingcap bietet beispielsweise auf Mysql basierende Datenbanken wie TiDB an.");
		o_insertProperties.put("SmallInt", 2);
		o_insertProperties.put("Int", 20_002);
		o_insertProperties.put("BigInt", java.lang.Long.valueOf("200002222"));
		o_insertProperties.put("Timestamp", o_dateTime);
		o_insertProperties.put("Date", o_date);
		o_insertProperties.put("Time", o_time);
		o_insertProperties.put("LocalDateTime", o_localDateTime);
		o_insertProperties.put("LocalDate", o_localDate);
		o_insertProperties.put("LocalTime", o_localTime);
		o_insertProperties.put("DoubleCol", 12.3456789d);
		o_insertProperties.put("Decimal", java.math.BigDecimal.valueOf(1234567.890d));
		o_insertProperties.put("Bool", false);
		o_insertProperties.put("Text2", "Das ist das Haus vom Nikolaus #2");
		o_insertProperties.put("ShortText2", "Zwei Datensatz");
		a_insertColumnsDefinition.add(o_insertProperties);
		
		try {
			o_dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMAN).parse("2019-03-03 02:03:03");
			o_date = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.GERMAN).parse("2003-03-03");
			o_time = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("03:03:03");
		} catch (Exception o_exc) {
			throw o_exc;
		}
		
		o_localDateTime = java.time.LocalDateTime.of(2019, 3, 3, 2, 3, 3);
		o_localDate = java.time.LocalDate.of(2003, 3, 3);
		o_localTime = java.time.LocalTime.of(3, 3, 3);
		
		o_insertProperties = new java.util.Properties();
		o_insertProperties.put("Id", 3);
		o_insertProperties.put("UUID", "ab0f2622-57d4-406e-a866-41e1bc5e4a3a");
		o_insertProperties.put("ShortText", "Datensatz Drei");
		o_insertProperties.put("Text", "\"Viele Firmen, die auf Oracle und IBM gesetzt haben, dachten es sei noch ein entfernter Meilenstein, diese zu ersetzen\", sagt Pingcap-CEO Huang Dongxu. \"Wir schauen uns aber mittlerweile Plan B ernsthaft an\". Allerdings seien chinesische Unternehmen laut dem lokalen Analystenunternehmen UOB Kay Hian noch nicht ganz bereit, wettbewerbsfähige Chips zu produzieren. \"Wenn sie aber genug gereift sind, werden [viele Unternehmen, Anm. d. Red.] ausländische Chips mit den lokalen ersetzen\", sagt die Firma.");
		o_insertProperties.put("SmallInt", 3);
		o_insertProperties.put("Int", 30_003);
		o_insertProperties.put("BigInt", java.lang.Long.valueOf("300003333"));
		o_insertProperties.put("Timestamp", o_dateTime);
		o_insertProperties.put("Date", o_date);
		o_insertProperties.put("Time", o_time);
		o_insertProperties.put("LocalDateTime", o_localDateTime);
		o_insertProperties.put("LocalDate", o_localDate);
		o_insertProperties.put("LocalTime", o_localTime);
		o_insertProperties.put("DoubleCol", 123.456789d);
		o_insertProperties.put("Decimal", java.math.BigDecimal.valueOf(123456.7890d));
		o_insertProperties.put("Bool", true);
		o_insertProperties.put("Text2", "Das ist das Haus vom Nikolaus #3");
		o_insertProperties.put("ShortText2", "Drei Datensatz");
		a_insertColumnsDefinition.add(o_insertProperties);
		
		try {
			o_dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMAN).parse("2019-04-04 03:04:04");
			o_date = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.GERMAN).parse("2004-04-04");
			o_time = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("04:04:04");
		} catch (Exception o_exc) {
			throw o_exc;
		}
		
		o_localDateTime = java.time.LocalDateTime.of(2019, 4, 4, 3, 4, 4);
		o_localDate = java.time.LocalDate.of(2004, 4, 4);
		o_localTime = java.time.LocalTime.of(4, 4, 4);
		
		o_insertProperties = new java.util.Properties();
		o_insertProperties.put("Id", 4);
		o_insertProperties.put("UUID", "9df3326e-b061-45e0-a3a1-4691ce0a349e");
		o_insertProperties.put("ShortText", "Datensatz Vier");
		o_insertProperties.put("Text", "China migriert schneller von US-Hardware auf lokale Chips. Immer mehr chinesische Unternehmen wollen anscheinend von amerikanischen Produkten auf lokal hergestellte Hardware setzen. Davon betroffen sind beispielsweise IBM und Oracle, die einen großen Teil ihres Umsatzes in Asien machen. Noch sei die chinesische Technik aber nicht weit genug.");
		o_insertProperties.put("SmallInt", 4);
		o_insertProperties.put("Int", 40_004);
		o_insertProperties.put("BigInt", java.lang.Long.valueOf("400004444"));
		o_insertProperties.put("Timestamp", o_dateTime);
		o_insertProperties.put("Date", o_date);
		o_insertProperties.put("Time", o_time);
		o_insertProperties.put("LocalDateTime", o_localDateTime);
		o_insertProperties.put("LocalDate", o_localDate);
		o_insertProperties.put("LocalTime", o_localTime);
		o_insertProperties.put("DoubleCol", 1234.56789d);
		o_insertProperties.put("Decimal", java.math.BigDecimal.valueOf(12345.67890d));
		o_insertProperties.put("Bool", false);
		o_insertProperties.put("Text2", "Das ist das Haus vom Nikolaus #4");
		o_insertProperties.put("ShortText2", "Vier Datensatz");
		a_insertColumnsDefinition.add(o_insertProperties);
		
		try {
			o_dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMAN).parse("2019-05-05 04:05:05");
			o_date = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.GERMAN).parse("2005-05-05");
			o_time = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("05:05:05");
		} catch (Exception o_exc) {
			throw o_exc;
		}
		
		o_localDateTime = java.time.LocalDateTime.of(2019, 5, 5, 4, 5, 5);
		o_localDate = java.time.LocalDate.of(2005, 5, 5);
		o_localTime = java.time.LocalTime.of(5, 5, 5);
		
		o_insertProperties = new java.util.Properties();
		o_insertProperties.put("Id", 5);
		o_insertProperties.put("UUID", "1b851742-fc49-4fc4-b1c4-0b7cac6ae5af");
		o_insertProperties.put("ShortText", "Datensatz Fünf");
		o_insertProperties.put("Text", "Weder IBM noch Oracle haben Bloomberg auf eine Anfrage hin geantwortet. SQL INJECTION:\';DELETE FROM items;. Dass die US-Regierung China wirtschaftlich unter Druck setzt, könnte allerdings zu unerwünschten Ergebnissen und dem schnellen Verlust eines Marktes mit fast 1,4 Milliarden Einwohnern führen.");
		o_insertProperties.put("SmallInt", 5);
		o_insertProperties.put("Int", 50_005);
		o_insertProperties.put("BigInt", java.lang.Long.valueOf("500005555"));
		o_insertProperties.put("Timestamp", o_dateTime);
		o_insertProperties.put("Date", o_date);
		o_insertProperties.put("Time", o_time);
		o_insertProperties.put("LocalDateTime", o_localDateTime);
		o_insertProperties.put("LocalDate", o_localDate);
		o_insertProperties.put("LocalTime", o_localTime);
		o_insertProperties.put("DoubleCol", 12345.6789d);
		o_insertProperties.put("Decimal", java.math.BigDecimal.valueOf(1234.567890d));
		o_insertProperties.put("Bool", true);
		o_insertProperties.put("Text2", "Das ist das Haus vom Nikolaus #5");
		o_insertProperties.put("ShortText2", "Fünf Datensatz");
		a_insertColumnsDefinition.add(o_insertProperties);
		
		i = 1;
		
		for (java.util.Properties o_insertColumnDefinition : a_insertColumnsDefinition) {
			net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert> o_queryInsert = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.INSERT, "sys_forestj_testddl2");
			/* #### Columns ############################################################################ */
			
			o_queryInsert.getQuery().o_nosqlmdbColumnAutoIncrement = new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Id");
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "UUID"), o_insertColumnDefinition.get("UUID")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "ShortText"), o_insertColumnDefinition.get("ShortText")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text"), o_insertColumnDefinition.get("Text")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "SmallInt"), o_insertColumnDefinition.get("SmallInt")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Int"), o_insertColumnDefinition.get("Int")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "BigInt"), o_insertColumnDefinition.get("BigInt")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Timestamp"), o_insertColumnDefinition.get("Timestamp")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Date"), o_insertColumnDefinition.get("Date")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Time"), o_insertColumnDefinition.get("Time")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "LocalDateTime"), o_insertColumnDefinition.get("LocalDateTime")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "LocalDate"), o_insertColumnDefinition.get("LocalDate")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "LocalTime"), o_insertColumnDefinition.get("LocalTime")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "DoubleCol"), o_insertColumnDefinition.get("DoubleCol")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Decimal"), o_insertColumnDefinition.get("Decimal")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Bool"), o_insertColumnDefinition.get("Bool")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "Text2"), o_insertColumnDefinition.get("Text2")) );
			o_queryInsert.getQuery().a_columnValues.add( new net.forestany.forestj.lib.sql.ColumnValue(new net.forestany.forestj.lib.sql.Column(o_queryInsert, "ShortText2"), o_insertColumnDefinition.get("ShortText2")) );
			
			a_result = o_glob.Base.fetchQuery(o_queryInsert);
			
				assertTrue(
					a_result.size() == 1,
					"Result row amount of insert query #" + i + " is not '1', it is '" + a_result.size() + "'"
				);
				
				for (java.util.LinkedHashMap<String, Object> o_row : a_result) {
					int j = 0;
					
					for (java.util.Map.Entry<String, Object> o_column : o_row.entrySet()) {
						if (j == 0) {
							assertTrue(
								o_column.getKey().contentEquals("AffectedRows"),
								"Result row key of insert query #" + i + " is not 'AffectedRows', it is '" + o_column.getKey() + "'"
							);
							
							assertTrue(
								Integer.valueOf(o_column.getValue().toString()) == 1,
								"Result row value of insert query #" + i + " is not '1'"
							);
						} else {
							assertTrue(
									o_column.getKey().contentEquals("LastInsertId"),
								"Result row key of insert query #" + i + " is not 'LastInsertId', it is '" + o_column.getKey() + "'"
							);
							
							assertTrue(
								Integer.valueOf(o_column.getValue().toString()) == i,
								"Result row value of insert query #" + i + " is not '" + i + "', it is '" + o_column.getValue().toString() + "'"
							);
						}
						
						j++;
					}
				}
				
			i++;
		}
	}
	
	private static void cleanupRecordTest(boolean p_b_checkResult) throws Exception {
		net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
		
		net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Drop> o_queryDrop = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Drop>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.DROP, "sys_forestj_language");

		java.util.List<java.util.LinkedHashMap<String, Object>> a_result = o_glob.Base.fetchQuery(o_queryDrop);
		
		int i_expectedAffectedRows = 0;
		
		/* nosqlmdb has expected value 1 */
		if (net.forestany.forestj.lib.Global.get().BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
			i_expectedAffectedRows = 1;
		}
		
		if (p_b_checkResult) {
			assertTrue(
				a_result.size() == 1,
				"Result row amount of drop query #1 is not '1', it is '" + a_result.size() + "'"
			);
			
			java.util.Map.Entry<String, Object> o_resultEntry = a_result.get(0).entrySet().iterator().next();
			
			assertTrue(
				o_resultEntry.getKey().contentEquals("AffectedRows"),
				"Result row key of query #1 is not 'AffectedRows', it is '" + o_resultEntry.getKey() + "'"
			);
			
			assertTrue(
				Integer.valueOf(o_resultEntry.getValue().toString()) == i_expectedAffectedRows,
				"Result row value of query #1 is not '" + i_expectedAffectedRows + "', it is '" + o_resultEntry.getValue().toString() + "'"
			);
		}
			
		o_queryDrop = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Drop>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.DROP, "sys_forestj_testddl2");
		
		a_result = o_glob.Base.fetchQuery(o_queryDrop);
		
		i_expectedAffectedRows = 0;
		
		/* nosqlmdb has expected value 1 */
		if (net.forestany.forestj.lib.Global.get().BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
			i_expectedAffectedRows = 1;
		}
		
		if (p_b_checkResult) {
			assertTrue(
				a_result.size() == 1,
				"Result row amount of drop query #2 is not '1', it is '" + a_result.size() + "'"
			);
			
			java.util.Map.Entry<String, Object> o_resultEntry = a_result.get(0).entrySet().iterator().next();
			
			assertTrue(
				o_resultEntry.getKey().contentEquals("AffectedRows"),
				"Result row key of query #2 is not 'AffectedRows', it is '" + o_resultEntry.getKey() + "'"
			);
			
			assertTrue(
				Integer.valueOf(o_resultEntry.getValue().toString()) == i_expectedAffectedRows,
				"Result row value of query #2 is not '" + i_expectedAffectedRows + "', it is '" + o_resultEntry.getValue().toString() + "'"
			);
		}
	}
		
	private static void testLanguageRecord() throws Exception {
		/* test GetRecord */
		LanguageRecord o_languageRecord = new LanguageRecord();
		
		assertTrue(
			o_languageRecord.getRecord(java.util.Arrays.asList(2)),
			"Record with Id=2 not found"
		);
		
		assertEquals(Integer.valueOf(2), o_languageRecord.ColumnId, "ColumnId[" + o_languageRecord.ColumnId + "] is not equal to '" + 2 + "'");
		assertEquals("942b5547-6cd9-11e9-b874-1062e50d1fcb", o_languageRecord.ColumnUUID, "ColumnUUID[" + o_languageRecord.ColumnUUID + "] is not equal to '942b5547-6cd9-11e9-b874-1062e50d1fcb'");
		assertEquals("en-US", o_languageRecord.ColumnCode, "ColumnCode[" + o_languageRecord.ColumnCode + "] is not equal to 'en-US'");
		assertEquals("English, United States", o_languageRecord.ColumnLanguage, "ColumnLanguage[" + o_languageRecord.ColumnLanguage + "] is not equal to 'English, United States'");
		
		/* test GetOneRecord */
		
		o_languageRecord = new LanguageRecord();
		
		assertTrue(
			o_languageRecord.getOneRecord(java.util.Arrays.asList("Code"), java.util.Arrays.asList("de-DE")),
			"Record with Code='de-DE' not found"
		);
		
		assertEquals(Integer.valueOf(1), o_languageRecord.ColumnId, "ColumnId[" + o_languageRecord.ColumnId + "] is not equal to '" + 1 + "'");
		assertEquals("9230337b-6cd9-11e9-b874-1062e50d1fcb", o_languageRecord.ColumnUUID, "ColumnUUID[" + o_languageRecord.ColumnUUID + "] is not equal to '9230337b-6cd9-11e9-b874-1062e50d1fcb'");
		assertEquals("de-DE", o_languageRecord.ColumnCode, "ColumnCode[" + o_languageRecord.ColumnCode + "] is not equal to 'de-DE'");
		assertEquals("Deutsch, Deutschland", o_languageRecord.ColumnLanguage, "ColumnLanguage[" + o_languageRecord.ColumnLanguage + "] is not equal to 'Deutsch, Deutschland'");
		
		/* test GetRecords(true) */
		
		o_languageRecord = new LanguageRecord();
		
		java.util.List<LanguageRecord> a_records = o_languageRecord.getRecords(true);
		
		assertTrue(
			a_records.size() == 9,
			"GetRecords(true) result rows are not '9', but '" + a_records.size() + "'"
		);
		
		int i = 1;
		
		for (LanguageRecord a_record : a_records) {
			if (i == 1) {
				assertEquals(Integer.valueOf(i), a_record.ColumnId, "ColumnId[" + a_record.ColumnId + "] is not equal to '" + i + "'");
				assertEquals("9230337b-6cd9-11e9-b874-1062e50d1fcb", a_record.ColumnUUID, "ColumnUUID[" + a_record.ColumnUUID + "] is not equal to '9230337b-6cd9-11e9-b874-1062e50d1fcb'");
				assertEquals("de-DE", a_record.ColumnCode, "ColumnCode[" + a_record.ColumnCode + "] is not equal to 'de-DE'");
				assertEquals("Deutsch, Deutschland", a_record.ColumnLanguage, "ColumnLanguage[" + a_record.ColumnLanguage + "] is not equal to 'Deutsch, Deutschland'");
			} else if (i == 2) {
				assertEquals(Integer.valueOf(i), a_record.ColumnId, "ColumnId[" + a_record.ColumnId + "] is not equal to '" + i + "'");
				assertEquals("942b5547-6cd9-11e9-b874-1062e50d1fcb", a_record.ColumnUUID, "ColumnUUID[" + a_record.ColumnUUID + "] is not equal to '942b5547-6cd9-11e9-b874-1062e50d1fcb'");
				assertEquals("en-US", a_record.ColumnCode, "ColumnCode[" + a_record.ColumnCode + "] is not equal to 'en-US'");
				assertEquals("English, United States", a_record.ColumnLanguage, "ColumnLanguage[" + a_record.ColumnLanguage + "] is not equal to 'English, United States'");
			} else if (i == 3) {
				assertEquals(Integer.valueOf(i), a_record.ColumnId, "ColumnId[" + a_record.ColumnId + "] is not equal to '" + i + "'");
				assertEquals("966996d3-6cd9-11e9-b874-1062e50d1fcb", a_record.ColumnUUID, "ColumnUUID[" + a_record.ColumnUUID + "] is not equal to '966996d3-6cd9-11e9-b874-1062e50d1fcb'");
				assertEquals("en-GB", a_record.ColumnCode, "ColumnCode[" + a_record.ColumnCode + "] is not equal to 'en-GB'");
				assertEquals("English, Großbritannien", a_record.ColumnLanguage, "ColumnLanguage[" + a_record.ColumnLanguage + "] is not equal to 'English, Großbritannien'");
			} else if (i == 4) {
				assertEquals(Integer.valueOf(i), a_record.ColumnId, "ColumnId[" + a_record.ColumnId + "] is not equal to '" + i + "'");
				assertEquals("44c37d45-222f-11ea-80e3-c85b7608f0ba", a_record.ColumnUUID, "ColumnUUID[" + a_record.ColumnUUID + "] is not equal to '44c37d45-222f-11ea-80e3-c85b7608f0ba'");
				assertEquals("it-IT", a_record.ColumnCode, "ColumnCode[" + a_record.ColumnCode + "] is not equal to 'it-IT'");
				assertEquals("Italian, Italy", a_record.ColumnLanguage, "ColumnLanguage[" + a_record.ColumnLanguage + "] is not equal to 'Italian, Italy'");
			} else if (i == 5) {
				assertEquals(Integer.valueOf(i), a_record.ColumnId, "ColumnId[" + a_record.ColumnId + "] is not equal to '" + i + "'");
				assertEquals("4f4bc151-222f-11ea-80e3-c85b7608f0ba", a_record.ColumnUUID, "ColumnUUID[" + a_record.ColumnUUID + "] is not equal to '4f4bc151-222f-11ea-80e3-c85b7608f0ba'");
				assertEquals("es-SP", a_record.ColumnCode, "ColumnCode[" + a_record.ColumnCode + "] is not equal to 'es-SP'");
				assertEquals("Spanisch, Spaniens", a_record.ColumnLanguage, "ColumnLanguage[" + a_record.ColumnLanguage + "] is not equal to 'Spanisch, Spaniens'");
			} else if (i == 6) {
				assertEquals(Integer.valueOf(i), a_record.ColumnId, "ColumnId[" + a_record.ColumnId + "] is not equal to '" + i + "'");
				assertEquals("5bb8176f-222f-11ea-80e3-c85b7608f0ba", a_record.ColumnUUID, "ColumnUUID[" + a_record.ColumnUUID + "] is not equal to '5bb8176f-222f-11ea-80e3-c85b7608f0ba'");
				assertEquals("jp-JP", a_record.ColumnCode, "ColumnCode[" + a_record.ColumnCode + "] is not equal to 'jp-JP'");
				assertEquals("Japanese, Japan", a_record.ColumnLanguage, "ColumnLanguage[" + a_record.ColumnLanguage + "] is not equal to 'Japanese, Japan'");
			} else if (i == 7) {
				assertEquals(Integer.valueOf(i), a_record.ColumnId, "ColumnId[" + a_record.ColumnId + "] is not equal to '" + i + "'");
				assertEquals("fe01d7d6-2232-11ea-80e3-c85b7608f0ba", a_record.ColumnUUID, "ColumnUUID[" + a_record.ColumnUUID + "] is not equal to 'fe01d7d6-2232-11ea-80e3-c85b7608f0ba'");
				assertEquals("cz-CZ", a_record.ColumnCode, "ColumnCode[" + a_record.ColumnCode + "] is not equal to 'cz-CZ'");
				assertEquals("Tschechisch, Tschechien", a_record.ColumnLanguage, "ColumnLanguage[" + a_record.ColumnLanguage + "] is not equal to 'Tschechisch, Tschechien'");
			} else if (i == 8) {
				assertEquals(Integer.valueOf(i), a_record.ColumnId, "ColumnId[" + a_record.ColumnId + "] is not equal to '" + i + "'");
				assertEquals("176176e2-57ff-4cef-baf5-7e2597f2a520", a_record.ColumnUUID, "ColumnUUID[" + a_record.ColumnUUID + "] is not equal to '176176e2-57ff-4cef-baf5-7e2597f2a520'");
				assertEquals("en-AU", a_record.ColumnCode, "ColumnCode[" + a_record.ColumnCode + "] is not equal to 'en-AU'");
				assertEquals("English, Australia", a_record.ColumnLanguage, "ColumnLanguage[" + a_record.ColumnLanguage + "] is not equal to 'English, Australia'");
			} else if (i == 9) {
				assertEquals(Integer.valueOf(i), a_record.ColumnId, "ColumnId[" + a_record.ColumnId + "] is not equal to '" + i + "'");
				assertEquals("d38e447d-de17-47be-a26a-f57423fc439f", a_record.ColumnUUID, "ColumnUUID[" + a_record.ColumnUUID + "] is not equal to 'd38e447d-de17-47be-a26a-f57423fc439f'");
				assertEquals("de-OE", a_record.ColumnCode, "ColumnCode[" + a_record.ColumnCode + "] is not equal to 'de-OE'");
				assertEquals("Deutsch, Österreich", a_record.ColumnLanguage, "ColumnLanguage[" + a_record.ColumnLanguage + "] is not equal to 'Deutsch, Österreich'");
			}
			
			i++;
		}
		
		/* test change record object with other sort and filter */
		
		o_languageRecord = new LanguageRecord();
		
		o_languageRecord.Columns.add("Code");
		o_languageRecord.Columns.add("Language");
		
		o_languageRecord.Sort.put("Code", false);
		
		o_languageRecord.Filters.add( new net.forestany.forestj.lib.sql.Filter("Id", 2, ">=") );
		o_languageRecord.Filters.add( new net.forestany.forestj.lib.sql.Filter("Id", 6, "<=", "AND") );
		o_languageRecord.Filters.add( new net.forestany.forestj.lib.sql.Filter("Language", "%e%", "LIKE", "AND") );
		
		o_languageRecord.Interval = 2;
		o_languageRecord.Page = 2;
		
		a_records = o_languageRecord.getRecords();
		
		assertTrue(
			a_records.size() == 2,
			"GetRecords result rows are not '2', but '" + a_records.size() + "'"
		);
		
		i = 1;
		
		for (LanguageRecord a_record : a_records) {
			if (i == 1) {
				assertEquals(Integer.valueOf(0), a_record.ColumnId, "ColumnId[" + a_record.ColumnId + "] is not equal to '" + 0 + "'");
				assertEquals(null, a_record.ColumnUUID, "ColumnUUID[" + a_record.ColumnUUID + "] is not equal to 'null'");
				assertEquals("en-US", a_record.ColumnCode, "ColumnCode[" + a_record.ColumnCode + "] is not equal to 'en-US'");
				assertEquals("English, United States", a_record.ColumnLanguage, "ColumnLanguage[" + a_record.ColumnLanguage + "] is not equal to 'English, United States'");
			} else if (i == 2) {
				assertEquals(Integer.valueOf(0), a_record.ColumnId, "ColumnId[" + a_record.ColumnId + "] is not equal to '" + 0 + "'");
				assertEquals(null, a_record.ColumnUUID, "ColumnUUID[" + a_record.ColumnUUID + "] is not equal to 'null'");
				assertEquals("en-GB", a_record.ColumnCode, "ColumnCode[" + a_record.ColumnCode + "] is not equal to 'en-GB'");
				assertEquals("English, Großbritannien", a_record.ColumnLanguage, "ColumnLanguage[" + a_record.ColumnLanguage + "] is not equal to 'English, Großbritannien'");
			}
			
			i++;
		}
		
		/* test InsertRecord */
		
		o_languageRecord = new LanguageRecord();
		
		String s_temp = o_languageRecord.ColumnUUID = java.util.UUID.randomUUID().toString();
		o_languageRecord.ColumnCode = "de-XX";
		o_languageRecord.ColumnLanguage = "DeleteMePlz";
		
		int i_foo = o_languageRecord.insertRecord();
		
		assertTrue(
			i_foo == 10,
			"InsertRecord result is not '10', but '" + i_foo + "'"
		);
		
		o_languageRecord = new LanguageRecord();
		
		assertTrue(
			o_languageRecord.getRecord(java.util.Arrays.asList(10)),
			"Record with Id=10 not found"
		);
		
		assertEquals(Integer.valueOf(10), o_languageRecord.ColumnId, "ColumnId[" + o_languageRecord.ColumnId + "] is not equal to '" + 10 + "'");
		assertEquals(s_temp, o_languageRecord.ColumnUUID, "ColumnUUID[" + o_languageRecord.ColumnUUID + "] is not equal to '" + s_temp + "'");
		assertEquals("de-XX", o_languageRecord.ColumnCode, "ColumnCode[" + o_languageRecord.ColumnCode + "] is not equal to 'de-XX'");
		assertEquals("DeleteMePlz", o_languageRecord.ColumnLanguage, "ColumnLanguage[" + o_languageRecord.ColumnLanguage + "] is not equal to 'DeleteMePlz'");
		
		/* test UpdateRecord with GetRecord */
		
		o_languageRecord = new LanguageRecord();
		
		assertTrue(
			o_languageRecord.getRecord(java.util.Arrays.asList(5)),
			"Record with Id=5 not found"
		);
		
		o_languageRecord.ColumnLanguage = "Spanisch, Spanien";
		
		assertEquals(
			Integer.valueOf(1),
			o_languageRecord.updateRecord(),
			"UpdateRecord of record #5 was not successful"
		);
		
		o_languageRecord = new LanguageRecord();
		
		assertTrue(
			o_languageRecord.getRecord(java.util.Arrays.asList(5)),
			"Record with Id=5 not found"
		);
		
		assertEquals(Integer.valueOf(5), o_languageRecord.ColumnId, "ColumnId[" + o_languageRecord.ColumnId + "] is not equal to '" + 5 + "'");
		assertEquals("4f4bc151-222f-11ea-80e3-c85b7608f0ba", o_languageRecord.ColumnUUID, "ColumnUUID[" + o_languageRecord.ColumnUUID + "] is not equal to '4f4bc151-222f-11ea-80e3-c85b7608f0ba'");
		assertEquals("es-SP", o_languageRecord.ColumnCode, "ColumnCode[" + o_languageRecord.ColumnCode + "] is not equal to 'es-SP'");
		assertEquals("Spanisch, Spanien", o_languageRecord.ColumnLanguage, "ColumnLanguage[" + o_languageRecord.ColumnLanguage + "] is not equal to 'Spanisch, Spanien'");
		
		/* test UpdateRecord with GetOneRecord */
		
		o_languageRecord = new LanguageRecord();
		
		assertTrue(
			o_languageRecord.getOneRecord(java.util.Arrays.asList("Language"), java.util.Arrays.asList("Deutsch, Österreich")),
			"Record with Language='Deutsch, Österreich' not found"
		);
		
		o_languageRecord.ColumnLanguage = "German, Austria";
		
		assertEquals(
			Integer.valueOf(1),
			o_languageRecord.updateRecord(),
			"UpdateRecord of record #9 was not successful"
		);
		
		o_languageRecord = new LanguageRecord();
		
		assertTrue(
			o_languageRecord.getRecord(java.util.Arrays.asList(9)),
			"Record with Id=9 not found"
		);
		
		assertEquals(Integer.valueOf(9), o_languageRecord.ColumnId, "ColumnId[" + o_languageRecord.ColumnId + "] is not equal to '" + 9 + "'");
		assertEquals("d38e447d-de17-47be-a26a-f57423fc439f", o_languageRecord.ColumnUUID, "ColumnUUID[" + o_languageRecord.ColumnUUID + "] is not equal to 'd38e447d-de17-47be-a26a-f57423fc439f'");
		assertEquals("de-OE", o_languageRecord.ColumnCode, "ColumnCode[" + o_languageRecord.ColumnCode + "] is not equal to 'de-OE'");
		assertEquals("German, Austria", o_languageRecord.ColumnLanguage, "ColumnLanguage[" + o_languageRecord.ColumnLanguage + "] is not equal to 'German, Austria'");
		
		/* test GetOneRecord but false result */
		
		o_languageRecord = new LanguageRecord();
		
		assertTrue(
			!o_languageRecord.getOneRecord(java.util.Arrays.asList("Code"), java.util.Arrays.asList("de-XF")),
			"Record with Code='de-XF' found"
		);
		
		/* test DeleteRecord with GetOneRecord but true result */
		
		o_languageRecord = new LanguageRecord();
		
		assertTrue(
			o_languageRecord.getOneRecord(java.util.Arrays.asList("Code"), java.util.Arrays.asList("de-XX")),
			"Record with Code='de-XX' not found"
		);
		
		assertEquals(
			Integer.valueOf(1),
			o_languageRecord.deleteRecord(),
			"DeleteRecord of record #10 was not successful"
		);
		
		/* test null record, UpdateRecord and GetOneRecord */
		
		int i_id = 11;
		
		/* nosqlmdb has id 10, because we deleted 10th record before */
		if (net.forestany.forestj.lib.Global.get().BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
			i_id = 10;
		}
		
		o_languageRecord = new LanguageRecord();
		
		o_languageRecord.ColumnUUID = "804ce8e2-4951-4480-a0fa-e539f9303741";
		o_languageRecord.ColumnCode = "em-TY";
		
		i_foo = o_languageRecord.insertRecord();
		
		assertTrue(
			i_foo == i_id,
			"InsertRecord result is not '" + i_id + "', but '" + i_foo + "'"
		);
		
		o_languageRecord = new LanguageRecord();
		
		assertTrue(
			o_languageRecord.getRecord(java.util.Arrays.asList(i_id)),
			"Record with Id=" + i_id + " not found"
		);
		
		assertEquals(Integer.valueOf(i_id), o_languageRecord.ColumnId, "ColumnId[" + o_languageRecord.ColumnId + "] is not equal to '" + i_id + "'");
		assertEquals("804ce8e2-4951-4480-a0fa-e539f9303741", o_languageRecord.ColumnUUID, "ColumnUUID[" + o_languageRecord.ColumnUUID + "] is not equal to '804ce8e2-4951-4480-a0fa-e539f9303741'");
		assertEquals("em-TY", o_languageRecord.ColumnCode, "ColumnCode[" + o_languageRecord.ColumnCode + "] is not equal to 'em-TY'");
		assertEquals(null, o_languageRecord.ColumnLanguage, "ColumnLanguage[" + o_languageRecord.ColumnLanguage + "] is not equal to 'null'");
		
		o_languageRecord.ColumnCode = "es-MX";
		
		assertEquals(
			Integer.valueOf(1),
			o_languageRecord.updateRecord(),
			"UpdateRecord of record #" + i_id + " was not successful"
		);
		
		o_languageRecord = new LanguageRecord();
		
		assertTrue(
			o_languageRecord.getRecord(java.util.Arrays.asList(i_id)),
			"Record with Id=" + i_id + " not found"
		);
		
		assertEquals(Integer.valueOf(i_id), o_languageRecord.ColumnId, "ColumnId[" + o_languageRecord.ColumnId + "] is not equal to '" + i_id + "'");
		assertEquals("804ce8e2-4951-4480-a0fa-e539f9303741", o_languageRecord.ColumnUUID, "ColumnUUID[" + o_languageRecord.ColumnUUID + "] is not equal to '804ce8e2-4951-4480-a0fa-e539f9303741'");
		assertEquals("es-MX", o_languageRecord.ColumnCode, "ColumnCode[" + o_languageRecord.ColumnCode + "] is not equal to 'es-MX'");
		assertEquals(null, o_languageRecord.ColumnLanguage, "ColumnLanguage[" + o_languageRecord.ColumnLanguage + "] is not equal to 'null'");
		
		/* test truncate */

		int i_expectedValue = 0;
		
		/* nosqlmdb has expected value 1 */
		if (net.forestany.forestj.lib.Global.get().BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
			i_expectedValue = 1;
		}
		
		o_languageRecord = new LanguageRecord();
		
		assertEquals(
			Integer.valueOf(i_expectedValue),
			o_languageRecord.truncateTable(),
			"TruncateTable was not successful"
		);
	}
	
	private static void testDDLRecord() throws Exception {
		/* test GetRecord */
		
		DDLRecord o_ddlRecord = new DDLRecord();
		
		assertTrue(
			o_ddlRecord.getRecord(java.util.Arrays.asList(1)),
			"Record with Id=1 not found"
		);
		
		checkDDLRecord(o_ddlRecord, 1);
		
		/* test GetOneRecord */
		
		o_ddlRecord = new DDLRecord();
		
		assertTrue(
			o_ddlRecord.getOneRecord(java.util.Arrays.asList("Int"), java.util.Arrays.asList(20002)),
			"Record with Int=20002 not found"
		);
		
		checkDDLRecord(o_ddlRecord, 2);
		
		/* test GetRecords(true) */
		
		o_ddlRecord = new DDLRecord();
		
		java.util.List<DDLRecord> a_records = o_ddlRecord.getRecords(true);
		
		assertTrue(
			a_records.size() == 5,
			"GetRecords(true) result rows are not '5', but '" + a_records.size() + "'"
		);
		
		int i = 1;
		
		/* very strict unit test for expected types and values */
		
		for (DDLRecord a_record : a_records) {
			checkDDLRecord(a_record, i);
			
			i++;
		}
		
		/* test change record object with other sort and filter */
		
		o_ddlRecord = new DDLRecord();
		
		o_ddlRecord.Columns.add("ShortText");
		o_ddlRecord.Columns.add("BigInt");
		o_ddlRecord.Columns.add("Timestamp");
		o_ddlRecord.Columns.add("Time");
		o_ddlRecord.Columns.add("DoubleCol");
		o_ddlRecord.Columns.add("Decimal");
		
		o_ddlRecord.Sort.put("ShortText", false);
		
		o_ddlRecord.Filters.add( new net.forestany.forestj.lib.sql.Filter("Bool", true, "=") );
		
		o_ddlRecord.Interval = 2;
		o_ddlRecord.Page = 1;
		
		a_records = o_ddlRecord.getRecords();
		
		assertTrue(
			a_records.size() == 2,
			"GetRecords result rows are not '2', but '" + a_records.size() + "'"
		);
		
		i = 10;
		
		for (DDLRecord a_record : a_records) {
			checkDDLRecord(a_record, i);
			
			i++;
		}
		
		/* test InsertRecord */
		
		o_ddlRecord = new DDLRecord();
		
		o_ddlRecord.ColumnUUID = "6df565d2-30fa-48ab-9f53-83b041f7210e";
		o_ddlRecord.ColumnShortText = "Datensatz Sechs";
		o_ddlRecord.ColumnText = " Die Berliner Staatsanwaltschaft hat das erste justizielle Rechtshilfeersuchen erst am 6. Dezember der russischen Generalstaatsanwaltschaft äbersandt. Das geht aus einer schriftlichen Anfrage der Linken-Abgeordneten Sevim Dagdelen hervor, die dem ARD-Hauptstadtstudio exklusiv vorliegt. Ein zweites Rechtshilfeersuchen ist demnach am 10. Dezember äbersandt worden. Schon Tage zuvor, nämlich am 4. Dezember, hatte die Bundesrepublik zwei russische Diplomaten ausgewiesen, da die russische Seite die Zusammenarbeit bei der Aufklärung des Mordes verzägert und erschwert habe.";
		o_ddlRecord.ColumnSmallInt = Short.valueOf("6");
		o_ddlRecord.ColumnInt = 60006;
		o_ddlRecord.ColumnBigInt = Long.valueOf("600006666");
		o_ddlRecord.ColumnTimestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMAN).parse("2019-06-06 05:06:06");
		o_ddlRecord.ColumnDate = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.GERMAN).parse("2006-06-06");
		o_ddlRecord.ColumnTime = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("06:06:06");
		o_ddlRecord.ColumnLocalDateTime = java.time.LocalDateTime.of(2019, 6, 6, 5, 6, 6);
		o_ddlRecord.ColumnLocalDate = java.time.LocalDate.of(2006, 6, 6);
		o_ddlRecord.ColumnLocalTime = java.time.LocalTime.of(6, 6, 6);
		o_ddlRecord.ColumnDoubleCol = 123456.789;
		o_ddlRecord.ColumnDecimal = new java.math.BigDecimal("123.456789");
		o_ddlRecord.ColumnBool = false;
		o_ddlRecord.ColumnText2 = "Das ist das Haus vom Nikolaus #6";
		o_ddlRecord.ColumnShortText2 = "Sechs Datensatz";
		
		int i_foo = o_ddlRecord.insertRecord();
		
		assertTrue(
			i_foo == 6,
			"InsertRecord result is not '6', but '" + i_foo + "'"
		);
		
		o_ddlRecord = new DDLRecord();
		
		assertTrue(
			o_ddlRecord.getRecord(java.util.Arrays.asList(6)),
			"Record with Id=6 not found"
		);
	
		checkDDLRecord(o_ddlRecord, 6);
		
		/* test UpdateRecord with GetRecord */
		
		o_ddlRecord = new DDLRecord();
		
		assertTrue(
			o_ddlRecord.getRecord(java.util.Arrays.asList(3)),
			"Record with Id=3 not found"
		);
		
		o_ddlRecord.ColumnShortText = "Datensatz Drei geändert";
		
		assertEquals(
			Integer.valueOf(1),
			o_ddlRecord.updateRecord(),
			"UpdateRecord of record #3 was not successful"
		);
		
		o_ddlRecord = new DDLRecord();
		
		assertTrue(
			o_ddlRecord.getRecord(java.util.Arrays.asList(3)),
			"Record with Id=3 not found"
		);
		
		checkDDLRecord(o_ddlRecord, 3, true);
		
		/* test UpdateRecord with GetOneRecord */
		
		o_ddlRecord = new DDLRecord();
		
		assertTrue(
			o_ddlRecord.getOneRecord(java.util.Arrays.asList("Int"), java.util.Arrays.asList(20002)),
			"Record with Int=20002 not found"
		);
		
		o_ddlRecord.ColumnShortText = "Datensatz Zwei geändert";
		
		assertEquals(
			Integer.valueOf(1),
			o_ddlRecord.updateRecord(),
			"UpdateRecord of record #2 was not successful"
		);
		
		o_ddlRecord = new DDLRecord();
		
		assertTrue(
			o_ddlRecord.getRecord(java.util.Arrays.asList(2)),
			"Record with Id=2 not found"
		);
		
		checkDDLRecord(o_ddlRecord, 2, true);
		
		/* test DeleteRecord with GetOneRecord but false result */
		
		o_ddlRecord = new DDLRecord();
		
		assertTrue(
			!o_ddlRecord.getOneRecord(java.util.Arrays.asList("Int"), java.util.Arrays.asList(123456)),
			"Record with Int=123465 found"
		);
		
		/* test DeleteRecord with GetOneRecord but true result */
		
		o_ddlRecord = new DDLRecord();
		
		assertTrue(
			o_ddlRecord.getOneRecord(java.util.Arrays.asList("ShortText"), java.util.Arrays.asList("Datensatz Sechs")),
			"Record with ShortText='Datensatz Sechs' not found"
		);
		
		assertEquals(
			Integer.valueOf(1),
			o_ddlRecord.deleteRecord(),
			"DeleteRecord of record #6 was not successful"
		);
		
		/* test null record, UpdateRecord and GetOneRecord */
		
		int i_id = 7;
		
		/* nosqlmdb has id 6, because we deleted 6th record before */
		if (net.forestany.forestj.lib.Global.get().BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
			i_id = 6;
		}
		
		o_ddlRecord = new DDLRecord();
		o_ddlRecord.ColumnUUID = "cf998a07-03c1-4ce1-a063-5f52a940c3a1";
		
		i_foo = o_ddlRecord.insertRecord();
		
		assertTrue(
			i_foo == i_id,
			"InsertRecord result is not '" + i_id + "', but '" + i_foo + "'"
		);
		
		o_ddlRecord = new DDLRecord();
		
		assertTrue(
			o_ddlRecord.getRecord(java.util.Arrays.asList(i_id)),
			"Record with Id=" + i_id + " not found"
		);
		
		checkDDLRecord(o_ddlRecord, (net.forestany.forestj.lib.Global.get().BaseGateway != net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) ? i_id : i_id + 1);
		
		o_ddlRecord.ColumnShortText = "short text";
		
		assertEquals(
			Integer.valueOf(1),
			o_ddlRecord.updateRecord(),
			"UpdateRecord of record #" + i_id + " was not successful"
		);
		
		o_ddlRecord = new DDLRecord();
		
		assertTrue(
			o_ddlRecord.getOneRecord(java.util.Arrays.asList("ShortText"), java.util.Arrays.asList("short text")),
			"Record with ShortText='short text' not found"
		);
		
		checkDDLRecord(o_ddlRecord, (net.forestany.forestj.lib.Global.get().BaseGateway != net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) ? i_id : i_id + 1, true);
		
		/* test truncate */
		
		int i_expectedValue = 0;
		
		/* nosqlmdb has expected value 1 */
		if (net.forestany.forestj.lib.Global.get().BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
			i_expectedValue = 1;
		}
		
		o_ddlRecord = new DDLRecord();
		
		assertEquals(
			Integer.valueOf(i_expectedValue),
			o_ddlRecord.truncateTable(),
			"TruncateTable was not successful"
		);
	}

	private static void checkDDLRecord(DDLRecord p_a_record, int p_i_id) throws Exception {
		checkDDLRecord(p_a_record, p_i_id, false);
	}
	
	private static void checkDDLRecord(DDLRecord p_a_record, int p_i_id, boolean p_b_changed) throws Exception {
		java.util.Date o_dateTime = null;
		java.util.Date o_date = null;
		java.util.Date o_time = null;
		
		java.time.LocalDateTime o_localDateTime = null;
		java.time.LocalDate o_localDate = null;
		java.time.LocalTime o_localTime = null;
		
		if (p_i_id == 1) {
			try {
				o_dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMAN).parse("2019-01-01 00:01:01");
				o_date = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.GERMAN).parse("2001-01-01");
				o_time = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("01:01:01");
			} catch (Exception o_exc) {
				throw o_exc;
			}
			
			o_localDateTime = java.time.LocalDateTime.of(2019, 1, 1, 0, 1, 1);
			o_localDate = java.time.LocalDate.of(2001, 1, 1);
			o_localTime = java.time.LocalTime.of(1, 1, 1);
		} else if (p_i_id == 2) {
			try {
				o_dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMAN).parse("2019-02-02 01:02:02");
				o_date = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.GERMAN).parse("2002-02-02");
				o_time = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("02:02:02");
			} catch (Exception o_exc) {
				throw o_exc;
			}
			
			o_localDateTime = java.time.LocalDateTime.of(2019, 2, 2, 1, 2, 2);
			o_localDate = java.time.LocalDate.of(2002, 2, 2);
			o_localTime = java.time.LocalTime.of(2, 2, 2);
		} else if (p_i_id == 3) {
			try {
				o_dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMAN).parse("2019-03-03 02:03:03");
				o_date = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.GERMAN).parse("2003-03-03");
				o_time = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("03:03:03");
			} catch (Exception o_exc) {
				throw o_exc;
			}
			
			o_localDateTime = java.time.LocalDateTime.of(2019, 3, 3, 2, 3, 3);
			o_localDate = java.time.LocalDate.of(2003, 3, 3);
			o_localTime = java.time.LocalTime.of(3, 3, 3);
		} else if (p_i_id == 4) {
			try {
				o_dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMAN).parse("2019-04-04 03:04:04");
				o_date = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.GERMAN).parse("2004-04-04");
				o_time = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("04:04:04");
			} catch (Exception o_exc) {
				throw o_exc;
			}
			
			o_localDateTime = java.time.LocalDateTime.of(2019, 4, 4, 3, 4, 4);
			o_localDate = java.time.LocalDate.of(2004, 4, 4);
			o_localTime = java.time.LocalTime.of(4, 4, 4);
		} else if (p_i_id == 5) {
			try {
				o_dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMAN).parse("2019-05-05 04:05:05");
				o_date = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.GERMAN).parse("2005-05-05");
				o_time = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("05:05:05");
			} catch (Exception o_exc) {
				throw o_exc;
			}
			
			o_localDateTime = java.time.LocalDateTime.of(2019, 5, 5, 4, 5, 5);
			o_localDate = java.time.LocalDate.of(2005, 5, 5);
			o_localTime = java.time.LocalTime.of(5, 5, 5);
		} else if (p_i_id == 6) {
			try {
				o_dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMAN).parse("2019-06-06 05:06:06");
				o_date = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.GERMAN).parse("2006-06-06");
				o_time = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("06:06:06");
			} catch (Exception o_exc) {
				throw o_exc;
			}
			
			o_localDateTime = java.time.LocalDateTime.of(2019, 6, 6, 5, 6, 6);
			o_localDate = java.time.LocalDate.of(2006, 6, 6);
			o_localTime = java.time.LocalTime.of(6, 6, 6);
		} else if (p_i_id == 7) {
			/* all null */
		} else if (p_i_id == 10) {
			try {
				o_dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMAN).parse("2019-05-05 04:05:05");
				o_date = null;
				o_time = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("05:05:05");
			} catch (Exception o_exc) {
				throw o_exc;
			}
			
			o_localDateTime = null;
			o_localDate = null;
			o_localTime = null;
		} else if (p_i_id == 11) {
			try {
				o_dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMAN).parse("2019-01-01 00:01:01");
				o_date = null;
				o_time = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("01:01:01");
			} catch (Exception o_exc) {
				throw o_exc;
			}
			
			o_localDateTime = null;
			o_localDate = null;
			o_localTime = null;
		}
		
		if (p_i_id == 1) {
			assertEquals(Integer.valueOf(p_i_id), p_a_record.ColumnId, "ColumnId[" + p_a_record.ColumnId + "] is not equal to '" + p_i_id + "'");
			assertEquals("32f80a3a-ff6e-4cc0-bb8f-ce630595382b", p_a_record.ColumnUUID, "ColumnUUID[" + p_a_record.ColumnUUID + "] is not equal to '32f80a3a-ff6e-4cc0-bb8f-ce630595382b'");
			assertEquals("Datensatz Eins", p_a_record.ColumnShortText, "ColumnShortText[" + p_a_record.ColumnShortText + "] is not equal to 'Datensatz Eins'");
			assertEquals("Die Handelsstreitigkeiten zwischen den USA und China sorgen für eine Art Umdenken auf beiden Seiten. Während US-Unternehmen chinesische Hardware meiden, tun dies chinesische Unternehmen wohl mittlerweile auch: So denken laut einem Bericht der Nachrichtenagentur Bloomberg viele chinesische Hersteller stark darüber nach, ihre IT-Infrastruktur von lokalen Unternehmen statt von den US-Konzernen Oracle und IBM zu kaufen. Für diese Unternehmen sei der asiatische Markt wichtig. 16 respektive mehr als 20 Prozent des Umsatzes stammen aus dieser Region.", p_a_record.ColumnText, "ColumnText[" + p_a_record.ColumnText + "] is not equal to expected text");
			/* nosqlmdb does not support short or smallint, only int32 and long */
			assertEquals((net.forestany.forestj.lib.Global.get().BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) ? Integer.valueOf("1") : Short.valueOf("1"), p_a_record.ColumnSmallInt, "ColumnSmallInt[" + p_a_record.ColumnSmallInt + "] is not equal to '" + 1 + "'");
			assertEquals(Integer.valueOf("10001"), p_a_record.ColumnInt, "ColumnInt[" + p_a_record.ColumnInt + "] is not equal to '10001'");
			assertEquals(Long.valueOf("100001111"), p_a_record.ColumnBigInt, "ColumnBigInt[" + p_a_record.ColumnBigInt + "] is not equal to '100001111'");
			assertEquals(o_dateTime, p_a_record.ColumnTimestamp, "ColumnTimestamp[" + p_a_record.ColumnTimestamp + "] is not equal to '" + o_dateTime + "'");
			assertEquals(o_date, p_a_record.ColumnDate, "ColumnDate[" + p_a_record.ColumnDate + "] is not equal to '" + o_date + "'");
			assertEquals(o_time, p_a_record.ColumnTime, "ColumnTime[" + p_a_record.ColumnTime + "] is not equal to '" + o_time + "'");
			assertEquals(o_localDateTime, p_a_record.ColumnLocalDateTime, "ColumnLocalDateTime[" + p_a_record.ColumnLocalDateTime + "] is not equal to '" + o_localDateTime + "'");
			assertEquals(o_localDate, p_a_record.ColumnLocalDate, "ColumnLocalDate[" + p_a_record.ColumnLocalDate + "] is not equal to '" + o_localDate + "'");
			assertEquals(o_localTime, p_a_record.ColumnLocalTime, "ColumnLocalTime[" + p_a_record.ColumnLocalTime + "] is not equal to '" + o_localTime + "'");
			assertEquals(Double.valueOf(1.23456789d), p_a_record.ColumnDoubleCol, "ColumnDoubleCol[" + p_a_record.ColumnDoubleCol + "] is not equal to '" + 1.23456789d + "'");
			assertEquals(new java.math.BigDecimal("12345678.90").setScale(2, java.math.RoundingMode.CEILING), p_a_record.ColumnDecimal.setScale(2, java.math.RoundingMode.CEILING), "ColumnDecimal[" + p_a_record.ColumnDecimal + "] is not equal to '" + new java.math.BigDecimal("12345678.90") + "'");
			assertEquals(true, p_a_record.ColumnBool, "ColumnBool[" + p_a_record.ColumnBool + "] is not equal to 'true'");
			assertEquals("Das ist das Haus vom Nikolaus #1", p_a_record.ColumnText2, "ColumnText2[" + p_a_record.ColumnText2 + "] is not equal to 'Das ist das Haus vom Nikolaus #1'");				
			assertEquals("Eins Datensatz", p_a_record.ColumnShortText2, "ColumnShortText2[" + p_a_record.ColumnShortText2 + "] is not equal to 'Eins Datensatz'");
		} else if (p_i_id == 2) {
			assertEquals(Integer.valueOf(p_i_id), p_a_record.ColumnId, "ColumnId[" + p_a_record.ColumnId + "] is not equal to '" + p_i_id + "'");
			assertEquals("390e413a-09df-41e3-aafe-8a116197da7f", p_a_record.ColumnUUID, "ColumnUUID[" + p_a_record.ColumnUUID + "] is not equal to '390e413a-09df-41e3-aafe-8a116197da7f'");
			assertEquals((p_b_changed) ? "Datensatz Zwei geändert" : "Datensatz Zwei", p_a_record.ColumnShortText, "ColumnShortText[" + p_a_record.ColumnShortText + "] is not equal to 'Datensatz Zwei geändert'");
			assertEquals("Und hier ein single quote \'. Und dann noch ein Backslash \\. Das Tech-Startup Pingcap ist eines der lokalen Unternehmen, die den Handelsstreit zu ihrem Vorteil nutzen, für lokale chinesische Produkte werben und selbst von US-Hardware wegmigrieren. Mehr als 300 Kunden betreut die Firma, darunter der Fahrradsharing-Dienst Mobike und der chinesische Smartphone-Hersteller Xiaomi. Piingcap bietet beispielsweise auf Mysql basierende Datenbanken wie TiDB an.", p_a_record.ColumnText, "ColumnText[" + p_a_record.ColumnText + "] is not equal to expected text");
			/* nosqlmdb does not support short or smallint, only int32 and long */
			assertEquals((net.forestany.forestj.lib.Global.get().BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) ? Integer.valueOf("2") : Short.valueOf("2"), p_a_record.ColumnSmallInt, "ColumnSmallInt[" + p_a_record.ColumnSmallInt + "] is not equal to '" + 2 + "'");
			assertEquals(Integer.valueOf("20002"), p_a_record.ColumnInt, "ColumnInt[" + p_a_record.ColumnInt + "] is not equal to '20002'");
			assertEquals(Long.valueOf("200002222"), p_a_record.ColumnBigInt, "ColumnBigInt[" + p_a_record.ColumnBigInt + "] is not equal to '200002222'");
			assertEquals(o_dateTime, p_a_record.ColumnTimestamp, "ColumnTimestamp[" + p_a_record.ColumnTimestamp + "] is not equal to '" + o_dateTime + "'");
			assertEquals(o_date, p_a_record.ColumnDate, "ColumnDate[" + p_a_record.ColumnDate + "] is not equal to '" + o_date + "'");
			assertEquals(o_time, p_a_record.ColumnTime, "ColumnTime[" + p_a_record.ColumnTime + "] is not equal to '" + o_time + "'");
			assertEquals(o_localDateTime, p_a_record.ColumnLocalDateTime, "ColumnLocalDateTime[" + p_a_record.ColumnLocalDateTime + "] is not equal to '" + o_localDateTime + "'");
			assertEquals(o_localDate, p_a_record.ColumnLocalDate, "ColumnLocalDate[" + p_a_record.ColumnLocalDate + "] is not equal to '" + o_localDate + "'");
			assertEquals(o_localTime, p_a_record.ColumnLocalTime, "ColumnLocalTime[" + p_a_record.ColumnLocalTime + "] is not equal to '" + o_localTime + "'");
			assertEquals(Double.valueOf(12.3456789d), p_a_record.ColumnDoubleCol, "ColumnDoubleCol[" + p_a_record.ColumnDoubleCol + "] is not equal to '" + 12.3456789d + "'");
			assertEquals(new java.math.BigDecimal("1234567.890").setScale(2, java.math.RoundingMode.CEILING), p_a_record.ColumnDecimal.setScale(2, java.math.RoundingMode.CEILING), "ColumnDecimal[" + p_a_record.ColumnDecimal + "] is not equal to '" + new java.math.BigDecimal("1234567.890") + "'");
			assertEquals(false, p_a_record.ColumnBool, "ColumnBool[" + p_a_record.ColumnBool + "] is not equal to 'false'");
			assertEquals("Das ist das Haus vom Nikolaus #2", p_a_record.ColumnText2, "ColumnText2[" + p_a_record.ColumnText2 + "] is not equal to 'Das ist das Haus vom Nikolaus #2'");
			assertEquals("Zwei Datensatz", p_a_record.ColumnShortText2, "ColumnShortText2[" + p_a_record.ColumnShortText2 + "] is not equal to 'Zwei Datensatz'");
		} else if (p_i_id == 3) {
			assertEquals(Integer.valueOf(p_i_id), p_a_record.ColumnId, "ColumnId[" + p_a_record.ColumnId + "] is not equal to '" + p_i_id + "'");
			assertEquals("ab0f2622-57d4-406e-a866-41e1bc5e4a3a", p_a_record.ColumnUUID, "ColumnUUID[" + p_a_record.ColumnUUID + "] is not equal to 'ab0f2622-57d4-406e-a866-41e1bc5e4a3a'");
			assertEquals((p_b_changed) ? "Datensatz Drei geändert" : "Datensatz Drei", p_a_record.ColumnShortText, "ColumnShortText[" + p_a_record.ColumnShortText + "] is not equal to 'Datensatz Drei geändert'");
			assertEquals("\"Viele Firmen, die auf Oracle und IBM gesetzt haben, dachten es sei noch ein entfernter Meilenstein, diese zu ersetzen\", sagt Pingcap-CEO Huang Dongxu. \"Wir schauen uns aber mittlerweile Plan B ernsthaft an\". Allerdings seien chinesische Unternehmen laut dem lokalen Analystenunternehmen UOB Kay Hian noch nicht ganz bereit, wettbewerbsfähige Chips zu produzieren. \"Wenn sie aber genug gereift sind, werden [viele Unternehmen, Anm. d. Red.] ausländische Chips mit den lokalen ersetzen\", sagt die Firma.", p_a_record.ColumnText, "ColumnText[" + p_a_record.ColumnText + "] is not equal to expected text");
			/* nosqlmdb does not support short or smallint, only int32 and long */
			assertEquals((net.forestany.forestj.lib.Global.get().BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) ? Integer.valueOf("3") : Short.valueOf("3"), p_a_record.ColumnSmallInt, "ColumnSmallInt[" + p_a_record.ColumnSmallInt + "] is not equal to '" + 3 + "'");
			assertEquals(Integer.valueOf("30003"), p_a_record.ColumnInt, "ColumnInt[" + p_a_record.ColumnInt + "] is not equal to '30003'");
			assertEquals(Long.valueOf("300003333"), p_a_record.ColumnBigInt, "ColumnBigInt[" + p_a_record.ColumnBigInt + "] is not equal to '300003333'");
			assertEquals(o_dateTime, p_a_record.ColumnTimestamp, "ColumnTimestamp[" + p_a_record.ColumnTimestamp + "] is not equal to '" + o_dateTime + "'");
			assertEquals(o_date, p_a_record.ColumnDate, "ColumnDate[" + p_a_record.ColumnDate + "] is not equal to '" + o_date + "'");
			assertEquals(o_time, p_a_record.ColumnTime, "ColumnTime[" + p_a_record.ColumnTime + "] is not equal to '" + o_time + "'");
			assertEquals(o_localDateTime, p_a_record.ColumnLocalDateTime, "ColumnLocalDateTime[" + p_a_record.ColumnLocalDateTime + "] is not equal to '" + o_localDateTime + "'");
			assertEquals(o_localDate, p_a_record.ColumnLocalDate, "ColumnLocalDate[" + p_a_record.ColumnLocalDate + "] is not equal to '" + o_localDate + "'");
			assertEquals(o_localTime, p_a_record.ColumnLocalTime, "ColumnLocalTime[" + p_a_record.ColumnLocalTime + "] is not equal to '" + o_localTime + "'");
			assertEquals(Double.valueOf(123.456789d), p_a_record.ColumnDoubleCol, "ColumnDoubleCol[" + p_a_record.ColumnDoubleCol + "] is not equal to '" + 123.456789d + "'");
			assertEquals(new java.math.BigDecimal("123456.7890").setScale(2, java.math.RoundingMode.CEILING), p_a_record.ColumnDecimal.setScale(2, java.math.RoundingMode.CEILING), "ColumnDecimal[" + p_a_record.ColumnDecimal + "] is not equal to '" + new java.math.BigDecimal("123456.7890") + "'");
			assertEquals(true, p_a_record.ColumnBool, "ColumnBool[" + p_a_record.ColumnBool + "] is not equal to 'true'");
			assertEquals("Das ist das Haus vom Nikolaus #3", p_a_record.ColumnText2, "ColumnText2[" + p_a_record.ColumnText2 + "] is not equal to 'Das ist das Haus vom Nikolaus #3'");
			assertEquals("Drei Datensatz", p_a_record.ColumnShortText2, "ColumnShortText2[" + p_a_record.ColumnShortText2 + "] is not equal to 'Drei Datensatz'");
		} else if (p_i_id == 4) {
			assertEquals(Integer.valueOf(p_i_id), p_a_record.ColumnId, "ColumnId[" + p_a_record.ColumnId + "] is not equal to '" + p_i_id + "'");
			assertEquals("9df3326e-b061-45e0-a3a1-4691ce0a349e", p_a_record.ColumnUUID, "ColumnUUID[" + p_a_record.ColumnUUID + "] is not equal to '9df3326e-b061-45e0-a3a1-4691ce0a349e'");
			assertEquals("Datensatz Vier", p_a_record.ColumnShortText, "ColumnShortText[" + p_a_record.ColumnShortText + "] is not equal to 'Datensatz Vier'");
			assertEquals("China migriert schneller von US-Hardware auf lokale Chips. Immer mehr chinesische Unternehmen wollen anscheinend von amerikanischen Produkten auf lokal hergestellte Hardware setzen. Davon betroffen sind beispielsweise IBM und Oracle, die einen großen Teil ihres Umsatzes in Asien machen. Noch sei die chinesische Technik aber nicht weit genug.", p_a_record.ColumnText, "ColumnText[" + p_a_record.ColumnText + "] is not equal to expected text");
			/* nosqlmdb does not support short or smallint, only int32 and long */
			assertEquals((net.forestany.forestj.lib.Global.get().BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) ? Integer.valueOf("4") : Short.valueOf("4"), p_a_record.ColumnSmallInt, "ColumnSmallInt[" + p_a_record.ColumnSmallInt + "] is not equal to '" + 4 + "'");
			assertEquals(Integer.valueOf("40004"), p_a_record.ColumnInt, "ColumnInt[" + p_a_record.ColumnInt + "] is not equal to '40004'");
			assertEquals(Long.valueOf("400004444"), p_a_record.ColumnBigInt, "ColumnBigInt[" + p_a_record.ColumnBigInt + "] is not equal to '400004444'");
			assertEquals(o_dateTime, p_a_record.ColumnTimestamp, "ColumnTimestamp[" + p_a_record.ColumnTimestamp + "] is not equal to '" + o_dateTime + "'");
			assertEquals(o_date, p_a_record.ColumnDate, "ColumnDate[" + p_a_record.ColumnDate + "] is not equal to '" + o_date + "'");
			assertEquals(o_time, p_a_record.ColumnTime, "ColumnTime[" + p_a_record.ColumnTime + "] is not equal to '" + o_time + "'");
			assertEquals(o_localDateTime, p_a_record.ColumnLocalDateTime, "ColumnLocalDateTime[" + p_a_record.ColumnLocalDateTime + "] is not equal to '" + o_localDateTime + "'");
			assertEquals(o_localDate, p_a_record.ColumnLocalDate, "ColumnLocalDate[" + p_a_record.ColumnLocalDate + "] is not equal to '" + o_localDate + "'");
			assertEquals(o_localTime, p_a_record.ColumnLocalTime, "ColumnLocalTime[" + p_a_record.ColumnLocalTime + "] is not equal to '" + o_localTime + "'");
			assertEquals(Double.valueOf(1234.56789d), p_a_record.ColumnDoubleCol, "ColumnDoubleCol[" + p_a_record.ColumnDoubleCol + "] is not equal to '" + 1234.56789d + "'");
			assertEquals(new java.math.BigDecimal("12345.67890").setScale(2, java.math.RoundingMode.CEILING), p_a_record.ColumnDecimal.setScale(2, java.math.RoundingMode.CEILING), "ColumnDecimal[" + p_a_record.ColumnDecimal + "] is not equal to '" + new java.math.BigDecimal("12345.67890") + "'");
			assertEquals(false, p_a_record.ColumnBool, "ColumnBool[" + p_a_record.ColumnBool + "] is not equal to 'false'");
			assertEquals("Das ist das Haus vom Nikolaus #4", p_a_record.ColumnText2, "ColumnText2[" + p_a_record.ColumnText2 + "] is not equal to 'Das ist das Haus vom Nikolaus #4'");
			assertEquals("Vier Datensatz", p_a_record.ColumnShortText2, "ColumnShortText2[" + p_a_record.ColumnShortText2 + "] is not equal to 'Vier Datensatz'");
		} else if (p_i_id == 5) {
			assertEquals(Integer.valueOf(p_i_id), p_a_record.ColumnId, "ColumnId[" + p_a_record.ColumnId + "] is not equal to '" + p_i_id + "'");
			assertEquals("1b851742-fc49-4fc4-b1c4-0b7cac6ae5af", p_a_record.ColumnUUID, "ColumnUUID[" + p_a_record.ColumnUUID + "] is not equal to '1b851742-fc49-4fc4-b1c4-0b7cac6ae5af'");
			assertEquals("Datensatz Fünf", p_a_record.ColumnShortText, "ColumnShortText[" + p_a_record.ColumnShortText + "] is not equal to 'Datensatz Fünf'");
			assertEquals("Weder IBM noch Oracle haben Bloomberg auf eine Anfrage hin geantwortet. SQL INJECTION:\';DELETE FROM items;. Dass die US-Regierung China wirtschaftlich unter Druck setzt, könnte allerdings zu unerwünschten Ergebnissen und dem schnellen Verlust eines Marktes mit fast 1,4 Milliarden Einwohnern führen.", p_a_record.ColumnText, "ColumnText[" + p_a_record.ColumnText + "] is not equal to expected text");
			/* nosqlmdb does not support short or smallint, only int32 and long */
			assertEquals((net.forestany.forestj.lib.Global.get().BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) ? Integer.valueOf("5") : Short.valueOf("5"), p_a_record.ColumnSmallInt, "ColumnSmallInt[" + p_a_record.ColumnSmallInt + "] is not equal to '" + 5 + "'");
			assertEquals(Integer.valueOf("50005"), p_a_record.ColumnInt, "ColumnInt[" + p_a_record.ColumnInt + "] is not equal to '50005'");
			assertEquals(Long.valueOf("500005555"), p_a_record.ColumnBigInt, "ColumnBigInt[" + p_a_record.ColumnBigInt + "] is not equal to '500005555'");
			assertEquals(o_dateTime, p_a_record.ColumnTimestamp, "ColumnTimestamp[" + p_a_record.ColumnTimestamp + "] is not equal to '" + o_dateTime + "'");
			assertEquals(o_date, p_a_record.ColumnDate, "ColumnDate[" + p_a_record.ColumnDate + "] is not equal to '" + o_date + "'");
			assertEquals(o_time, p_a_record.ColumnTime, "ColumnTime[" + p_a_record.ColumnTime + "] is not equal to '" + o_time + "'");
			assertEquals(o_localDateTime, p_a_record.ColumnLocalDateTime, "ColumnLocalDateTime[" + p_a_record.ColumnLocalDateTime + "] is not equal to '" + o_localDateTime + "'");
			assertEquals(o_localDate, p_a_record.ColumnLocalDate, "ColumnLocalDate[" + p_a_record.ColumnLocalDate + "] is not equal to '" + o_localDate + "'");
			assertEquals(o_localTime, p_a_record.ColumnLocalTime, "ColumnLocalTime[" + p_a_record.ColumnLocalTime + "] is not equal to '" + o_localTime + "'");
			assertEquals(Double.valueOf(12345.6789d), p_a_record.ColumnDoubleCol, "ColumnDoubleCol[" + p_a_record.ColumnDoubleCol + "] is not equal to '" + 12345.6789d + "'");
			assertEquals(new java.math.BigDecimal("1234.567890").setScale(2, java.math.RoundingMode.CEILING), p_a_record.ColumnDecimal.setScale(2, java.math.RoundingMode.CEILING), "ColumnDecimal[" + p_a_record.ColumnDecimal + "] is not equal to '" + new java.math.BigDecimal("1234.567890") + "'");
			assertEquals(true, p_a_record.ColumnBool, "ColumnBool[" + p_a_record.ColumnBool + "] is not equal to 'true'");
			assertEquals("Das ist das Haus vom Nikolaus #5", p_a_record.ColumnText2, "ColumnText2[" + p_a_record.ColumnText2 + "] is not equal to 'Das ist das Haus vom Nikolaus #5'");
			assertEquals("Fünf Datensatz", p_a_record.ColumnShortText2, "ColumnShortText2[" + p_a_record.ColumnShortText2 + "] is not equal to 'Fünf Datensatz'");
		} else if (p_i_id == 6) {
			assertEquals(Integer.valueOf(p_i_id), p_a_record.ColumnId, "ColumnId[" + p_a_record.ColumnId + "] is not equal to '" + p_i_id + "'");
			assertEquals("6df565d2-30fa-48ab-9f53-83b041f7210e", p_a_record.ColumnUUID, "ColumnUUID[" + p_a_record.ColumnUUID + "] is not equal to '6df565d2-30fa-48ab-9f53-83b041f7210e'");
			assertEquals("Datensatz Sechs", p_a_record.ColumnShortText, "ColumnShortText[" + p_a_record.ColumnShortText + "] is not equal to 'Datensatz Sechs'");
			assertEquals(" Die Berliner Staatsanwaltschaft hat das erste justizielle Rechtshilfeersuchen erst am 6. Dezember der russischen Generalstaatsanwaltschaft äbersandt. Das geht aus einer schriftlichen Anfrage der Linken-Abgeordneten Sevim Dagdelen hervor, die dem ARD-Hauptstadtstudio exklusiv vorliegt. Ein zweites Rechtshilfeersuchen ist demnach am 10. Dezember äbersandt worden. Schon Tage zuvor, nämlich am 4. Dezember, hatte die Bundesrepublik zwei russische Diplomaten ausgewiesen, da die russische Seite die Zusammenarbeit bei der Aufklärung des Mordes verzägert und erschwert habe.", p_a_record.ColumnText, "ColumnText[" + p_a_record.ColumnText + "] is not equal to expected text");
			/* nosqlmdb does not support short or smallint, only int32 and long */
			assertEquals((net.forestany.forestj.lib.Global.get().BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) ? Integer.valueOf("6") : Short.valueOf("6"), p_a_record.ColumnSmallInt, "ColumnSmallInt[" + p_a_record.ColumnSmallInt + "] is not equal to '" + 6 + "'");
			assertEquals(Integer.valueOf("60006"), p_a_record.ColumnInt, "ColumnInt[" + p_a_record.ColumnInt + "] is not equal to '60006'");
			assertEquals(Long.valueOf("600006666"), p_a_record.ColumnBigInt, "ColumnBigInt[" + p_a_record.ColumnBigInt + "] is not equal to '600006666'");
			assertEquals(o_dateTime, p_a_record.ColumnTimestamp, "ColumnTimestamp[" + p_a_record.ColumnTimestamp + "] is not equal to '" + o_dateTime + "'");
			assertEquals(o_date, p_a_record.ColumnDate, "ColumnDate[" + p_a_record.ColumnDate + "] is not equal to '" + o_date + "'");
			assertEquals(o_time, p_a_record.ColumnTime, "ColumnTime[" + p_a_record.ColumnTime + "] is not equal to '" + o_time + "'");
			assertEquals(o_localDateTime, p_a_record.ColumnLocalDateTime, "ColumnLocalDateTime[" + p_a_record.ColumnLocalDateTime + "] is not equal to '" + o_localDateTime + "'");
			assertEquals(o_localDate, p_a_record.ColumnLocalDate, "ColumnLocalDate[" + p_a_record.ColumnLocalDate + "] is not equal to '" + o_localDate + "'");
			assertEquals(o_localTime, p_a_record.ColumnLocalTime, "ColumnLocalTime[" + p_a_record.ColumnLocalTime + "] is not equal to '" + o_localTime + "'");
			assertEquals(Double.valueOf(123456.789d), p_a_record.ColumnDoubleCol, "ColumnDoubleCol[" + p_a_record.ColumnDoubleCol + "] is not equal to '" + 123456.789d + "'");
			assertEquals(new java.math.BigDecimal("123.4567890").setScale(2, java.math.RoundingMode.CEILING), p_a_record.ColumnDecimal.setScale(2, java.math.RoundingMode.CEILING), "ColumnDecimal[" + p_a_record.ColumnDecimal + "] is not equal to '" + new java.math.BigDecimal("123.4567890") + "'");
			assertEquals(false, p_a_record.ColumnBool, "ColumnBool[" + p_a_record.ColumnBool + "] is not equal to 'false'");
			assertEquals("Das ist das Haus vom Nikolaus #6", p_a_record.ColumnText2, "ColumnText2[" + p_a_record.ColumnText2 + "] is not equal to 'Das ist das Haus vom Nikolaus #6'");
			assertEquals("Sechs Datensatz", p_a_record.ColumnShortText2, "ColumnShortText2[" + p_a_record.ColumnShortText2 + "] is not equal to 'Sechs Datensatz'");
		} else if (p_i_id == 7) {
			/* nosqlmdb has id 6, because we deleted 6th record before */
			assertEquals((net.forestany.forestj.lib.Global.get().BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) ? Integer.valueOf("6") : Integer.valueOf("7"), p_a_record.ColumnId, "ColumnId[" + p_a_record.ColumnId + "] is not equal to '" + 6 + "'");
			assertEquals("cf998a07-03c1-4ce1-a063-5f52a940c3a1", p_a_record.ColumnUUID, "ColumnUUID[" + p_a_record.ColumnUUID + "] is not equal to 'cf998a07-03c1-4ce1-a063-5f52a940c3a1'");
			assertEquals((!p_b_changed) ? null : "short text", p_a_record.ColumnShortText, "ColumnShortText[" + p_a_record.ColumnShortText + "] is not equal to 'null'");
			assertEquals(null, p_a_record.ColumnText, "ColumnText[" + p_a_record.ColumnText + "] is not equal to expected text");
			/* nosqlmdb does not support short or smallint, only int32 and long */
			assertEquals((net.forestany.forestj.lib.Global.get().BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) ? Integer.valueOf("0") : Short.valueOf("0"), p_a_record.ColumnSmallInt, "ColumnSmallInt[" + p_a_record.ColumnSmallInt + "] is not equal to '" + 0 + "'");
			assertEquals(Integer.valueOf("0"), p_a_record.ColumnInt, "ColumnInt[" + p_a_record.ColumnInt + "] is not equal to '0'");
			assertEquals(Long.valueOf("0"), p_a_record.ColumnBigInt, "ColumnBigInt[" + p_a_record.ColumnBigInt + "] is not equal to '0'");
			assertEquals(null, p_a_record.ColumnTimestamp, "ColumnTimestamp[" + p_a_record.ColumnTimestamp + "] is not equal to 'null'");
			assertEquals(null, p_a_record.ColumnDate, "ColumnDate[" + p_a_record.ColumnDate + "] is not equal to 'null'");
			assertEquals(null, p_a_record.ColumnTime, "ColumnTime[" + p_a_record.ColumnTime + "] is not equal to 'null'");
			assertEquals(null, p_a_record.ColumnLocalDateTime, "ColumnLocalDateTime[" + p_a_record.ColumnLocalDateTime + "] is not equal to 'null'");
			assertEquals(null, p_a_record.ColumnLocalDate, "ColumnLocalDate[" + p_a_record.ColumnLocalDate + "] is not equal to 'null'");
			assertEquals(null, p_a_record.ColumnLocalTime, "ColumnLocalTime[" + p_a_record.ColumnLocalTime + "] is not equal to 'null'");
			assertEquals(Double.valueOf(0.0d), p_a_record.ColumnDoubleCol, "ColumnDoubleCol[" + p_a_record.ColumnDoubleCol + "] is not equal to '" + 0.0d + "'");
			assertEquals(null, p_a_record.ColumnDecimal, "ColumnDecimal[" + p_a_record.ColumnDecimal + "] is not equal to 'null'");
			assertEquals(false, p_a_record.ColumnBool, "ColumnBool[" + p_a_record.ColumnBool + "] is not equal to 'false'");
			assertEquals(null, p_a_record.ColumnText2, "ColumnText2[" + p_a_record.ColumnText2 + "] is not equal to 'null'");
			assertEquals(null, p_a_record.ColumnShortText2, "ColumnShortText2[" + p_a_record.ColumnShortText2 + "] is not equal to 'null'");
		} else if (p_i_id == 10) {
			assertEquals(Integer.valueOf("0"), p_a_record.ColumnId, "ColumnId[" + p_a_record.ColumnId + "] is not equal to '" + 0 + "'");
			assertEquals(null, p_a_record.ColumnUUID, "ColumnUUID[" + p_a_record.ColumnUUID + "] is not equal to 'null'");
			assertEquals("Datensatz Fünf", p_a_record.ColumnShortText, "ColumnShortText[" + p_a_record.ColumnShortText + "] is not equal to 'Datensatz Fünf'");
			assertEquals(null, p_a_record.ColumnText, "ColumnText[" + p_a_record.ColumnText + "] is not equal to expected text");
			/* nosqlmdb does not support short or smallint, only int32 and long */
			assertEquals((net.forestany.forestj.lib.Global.get().BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) ? Integer.valueOf("0") : Short.valueOf("0"), p_a_record.ColumnSmallInt, "ColumnSmallInt[" + p_a_record.ColumnSmallInt + "] is not equal to '" + 0 + "'");
			assertEquals(Integer.valueOf("0"), p_a_record.ColumnInt, "ColumnInt[" + p_a_record.ColumnInt + "] is not equal to '0'");
			assertEquals(Long.valueOf("500005555"), p_a_record.ColumnBigInt, "ColumnBigInt[" + p_a_record.ColumnBigInt + "] is not equal to '500005555'");
			assertEquals(o_dateTime, p_a_record.ColumnTimestamp, "ColumnTimestamp[" + p_a_record.ColumnTimestamp + "] is not equal to '" + o_dateTime + "'");
			assertEquals(o_date, p_a_record.ColumnDate, "ColumnDate[" + p_a_record.ColumnDate + "] is not equal to '" + o_date + "'");
			assertEquals(o_time, p_a_record.ColumnTime, "ColumnTime[" + p_a_record.ColumnTime + "] is not equal to '" + o_time + "'");
			assertEquals(o_localDateTime, p_a_record.ColumnLocalDateTime, "ColumnLocalDateTime[" + p_a_record.ColumnLocalDateTime + "] is not equal to '" + o_localDateTime + "'");
			assertEquals(o_localDate, p_a_record.ColumnLocalDate, "ColumnLocalDate[" + p_a_record.ColumnLocalDate + "] is not equal to '" + o_localDate + "'");
			assertEquals(o_localTime, p_a_record.ColumnLocalTime, "ColumnLocalTime[" + p_a_record.ColumnLocalTime + "] is not equal to '" + o_localTime + "'");
			assertEquals(Double.valueOf(12345.6789d), p_a_record.ColumnDoubleCol, "ColumnDoubleCol[" + p_a_record.ColumnDoubleCol + "] is not equal to '" + 12345.6789d + "'");
			assertEquals(new java.math.BigDecimal("1234.567890").setScale(2, java.math.RoundingMode.CEILING), p_a_record.ColumnDecimal.setScale(2, java.math.RoundingMode.CEILING), "ColumnDecimal[" + p_a_record.ColumnDecimal + "] is not equal to '" + new java.math.BigDecimal("1234.567890") + "'");
			assertEquals(false, p_a_record.ColumnBool, "ColumnBool[" + p_a_record.ColumnBool + "] is not equal to 'false'");
			assertEquals(null, p_a_record.ColumnText2, "ColumnText2[" + p_a_record.ColumnText2 + "] is not equal to 'null'");
			assertEquals(null, p_a_record.ColumnShortText2, "ColumnShortText2[" + p_a_record.ColumnShortText2 + "] is not equal to 'null'");
		} else if (p_i_id == 11) {
			assertEquals(Integer.valueOf("0"), p_a_record.ColumnId, "ColumnId[" + p_a_record.ColumnId + "] is not equal to '" + 0 + "'");
			assertEquals(null, p_a_record.ColumnUUID, "ColumnUUID[" + p_a_record.ColumnUUID + "] is not equal to 'null'");
			assertEquals("Datensatz Eins", p_a_record.ColumnShortText, "ColumnShortText[" + p_a_record.ColumnShortText + "] is not equal to 'Datensatz Eins'");
			assertEquals(null, p_a_record.ColumnText, "ColumnText[" + p_a_record.ColumnText + "] is not equal to expected text");
			/* nosqlmdb does not support short or smallint, only int32 and long */
			assertEquals((net.forestany.forestj.lib.Global.get().BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) ? Integer.valueOf("0") : Short.valueOf("0"), p_a_record.ColumnSmallInt, "ColumnSmallInt[" + p_a_record.ColumnSmallInt + "] is not equal to '" + 0 + "'");
			assertEquals(Integer.valueOf("0"), p_a_record.ColumnInt, "ColumnInt[" + p_a_record.ColumnInt + "] is not equal to '0'");
			assertEquals(Long.valueOf("100001111"), p_a_record.ColumnBigInt, "ColumnBigInt[" + p_a_record.ColumnBigInt + "] is not equal to '100001111'");
			assertEquals(o_dateTime, p_a_record.ColumnTimestamp, "ColumnTimestamp[" + p_a_record.ColumnTimestamp + "] is not equal to '" + o_dateTime + "'");
			assertEquals(o_date, p_a_record.ColumnDate, "ColumnDate[" + p_a_record.ColumnDate + "] is not equal to '" + o_date + "'");
			assertEquals(o_time, p_a_record.ColumnTime, "ColumnTime[" + p_a_record.ColumnTime + "] is not equal to '" + o_time + "'");
			assertEquals(o_localDateTime, p_a_record.ColumnLocalDateTime, "ColumnLocalDateTime[" + p_a_record.ColumnLocalDateTime + "] is not equal to '" + o_localDateTime + "'");
			assertEquals(o_localDate, p_a_record.ColumnLocalDate, "ColumnLocalDate[" + p_a_record.ColumnLocalDate + "] is not equal to '" + o_localDate + "'");
			assertEquals(o_localTime, p_a_record.ColumnLocalTime, "ColumnLocalTime[" + p_a_record.ColumnLocalTime + "] is not equal to '" + o_localTime + "'");
			assertEquals(Double.valueOf(1.23456789d), p_a_record.ColumnDoubleCol, "ColumnDoubleCol[" + p_a_record.ColumnDoubleCol + "] is not equal to '" + 1.23456789d + "'");
			assertEquals(new java.math.BigDecimal("12345678.90").setScale(2, java.math.RoundingMode.CEILING), p_a_record.ColumnDecimal.setScale(2, java.math.RoundingMode.CEILING), "ColumnDecimal[" + p_a_record.ColumnDecimal + "] is not equal to '" + new java.math.BigDecimal("12345678.90") + "'");
			assertEquals(false, p_a_record.ColumnBool, "ColumnBool[" + p_a_record.ColumnBool + "] is not equal to 'false'");
			assertEquals(null, p_a_record.ColumnText2, "ColumnText2[" + p_a_record.ColumnText2 + "] is not equal to 'null'");				
			assertEquals(null, p_a_record.ColumnShortText2, "ColumnShortText2[" + p_a_record.ColumnShortText2 + "] is not equal to 'null'");
		}
	}
}
