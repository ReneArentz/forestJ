package net.forestany.forestj.lib.test.sqltest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class to test record instances with copy and compare functionalities
 */
public class RecordCopyAndCompareTest {
	private net.forestany.forestj.lib.sqlcore.Base o_sourceBase = null;
	private net.forestany.forestj.lib.sqlcore.Base o_destinationBase = null;
	private net.forestany.forestj.lib.sqlcore.BaseGateway e_sourceGateway;
	private net.forestany.forestj.lib.sqlcore.BaseGateway e_destinationGateway;

	/**
	 * constructor
	 * 
	 * @param p_o_sourceBase					source base instance
	 * @param p_o_destinationBase				destination base instance
	 * @param p_e_sourceGateway					source gateway
	 * @param p_e_destinationGateway			destination gateway
	 */
	public RecordCopyAndCompareTest(net.forestany.forestj.lib.sqlcore.Base p_o_sourceBase, net.forestany.forestj.lib.sqlcore.Base p_o_destinationBase, net.forestany.forestj.lib.sqlcore.BaseGateway p_e_sourceGateway, net.forestany.forestj.lib.sqlcore.BaseGateway p_e_destinationGateway) {
		this.o_sourceBase = p_o_sourceBase;
		this.o_destinationBase = p_o_destinationBase;
		this.e_sourceGateway = p_e_sourceGateway;
		this.e_destinationGateway = p_e_destinationGateway;
	}
	
	/**
	 * method to test record instances
	 */
	public void testRecordCopyAndCompare() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			
			net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
			
			try {
				/* o_glob.setLogCompleteSqlQuery(true); */
				
				try {
					o_glob.Base = this.o_sourceBase;
					o_glob.BaseGateway = this.e_sourceGateway;
					cleanupRecordCopyAndCompareTest(false);
					o_glob.Base = this.o_destinationBase;
					o_glob.BaseGateway = this.e_destinationGateway;
					cleanupRecordCopyAndCompareTest(false);
				} catch (Exception o_exc) {
					/* does not matter */
				}
				
				o_glob.Base = this.o_sourceBase;
				o_glob.BaseGateway = this.e_sourceGateway;
				prepareRecordCopyAndCompareTest(true);
				o_glob.Base = this.o_destinationBase;
				o_glob.BaseGateway = this.e_destinationGateway;
				prepareRecordCopyAndCompareTest(false);

				testDDLRecordCopyAndCompare();
			} catch (Exception o_exc) {
				throw o_exc;
			} finally {
				o_glob.Base = this.o_sourceBase;
				o_glob.BaseGateway = this.e_sourceGateway;
				cleanupRecordCopyAndCompareTest(true);
				o_glob.Base = this.o_destinationBase;
				o_glob.BaseGateway = this.e_destinationGateway;
				cleanupRecordCopyAndCompareTest(true);

				this.o_sourceBase.closeConnection();
				this.o_destinationBase.closeConnection();
			}
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
	
	private void prepareRecordCopyAndCompareTest(boolean p_b_insertData) throws Exception {
		net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
		
		/* #### CREATE ############################################################################# */
		net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create> o_queryCreate = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Create>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.CREATE, "sys_forestj_testddl3");
		/* #### Columns ############################################################################ */
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
		
		/* #### INSERT ############################################################################ */

		if (p_b_insertData) {
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
			
			java.util.List<java.util.Properties> a_insertColumnsDefinition = new java.util.ArrayList<java.util.Properties>();
			
			java.util.Properties o_insertProperties = new java.util.Properties();
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
			
			int i = 1;
			
			for (java.util.Properties o_insertColumnDefinition : a_insertColumnsDefinition) {
				net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert> o_queryInsert = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Insert>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.INSERT, "sys_forestj_testddl3");
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
	}
	
	private void cleanupRecordCopyAndCompareTest(boolean p_b_checkResult) throws Exception {
		net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
		
		net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Drop> o_queryDrop = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Drop>(o_glob.BaseGateway, net.forestany.forestj.lib.sqlcore.SqlType.DROP, "sys_forestj_testddl3");

		java.util.List<java.util.LinkedHashMap<String, Object>> a_result = o_glob.Base.fetchQuery(o_queryDrop);
		
		if (p_b_checkResult) {
            int i_expectedAffectedRows = 0;
            
			/* SQLITE only remembers the last data change -> last insert of 6 records */
			if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.SQLITE) {
				i_expectedAffectedRows = 6;
			}

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
	}
		
	private void testDDLRecordCopyAndCompare() throws Exception {
		net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
		
		o_glob.Base = this.o_sourceBase;
		o_glob.BaseGateway = this.e_sourceGateway;
		
		/* test GetRecord */
		
		DDLRecordCopyAndCompare o_sourceRecord = new DDLRecordCopyAndCompare();
		
		assertTrue(
			o_sourceRecord.getRecord(java.util.Arrays.asList(1)),
			"Record with Id=1 not found"
		);
		
		checkDDLRecord(o_sourceRecord, 1);
		
		/* test InsertRecord */
		
		o_sourceRecord = new DDLRecordCopyAndCompare();
		
		o_sourceRecord.ColumnUUID = "6df565d2-30fa-48ab-9f53-83b041f7210e";
		o_sourceRecord.ColumnShortText = "Datensatz Sechs";
		o_sourceRecord.ColumnText = " Die Berliner Staatsanwaltschaft hat das erste justizielle Rechtshilfeersuchen erst am 6. Dezember der russischen Generalstaatsanwaltschaft äbersandt. Das geht aus einer schriftlichen Anfrage der Linken-Abgeordneten Sevim Dagdelen hervor, die dem ARD-Hauptstadtstudio exklusiv vorliegt. Ein zweites Rechtshilfeersuchen ist demnach am 10. Dezember äbersandt worden. Schon Tage zuvor, nämlich am 4. Dezember, hatte die Bundesrepublik zwei russische Diplomaten ausgewiesen, da die russische Seite die Zusammenarbeit bei der Aufklärung des Mordes verzägert und erschwert habe.";
		o_sourceRecord.ColumnSmallInt = Short.valueOf("6");
		o_sourceRecord.ColumnInt = 60006;
		o_sourceRecord.ColumnBigInt = Long.valueOf("600006666");
		o_sourceRecord.ColumnTimestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.GERMAN).parse("2019-06-06 05:06:06");
		o_sourceRecord.ColumnDate = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.GERMAN).parse("2006-06-06");
		o_sourceRecord.ColumnTime = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("06:06:06");
		o_sourceRecord.ColumnLocalDateTime = java.time.LocalDateTime.of(2019, 6, 6, 5, 6, 6);
		o_sourceRecord.ColumnLocalDate = java.time.LocalDate.of(2006, 6, 6);
		o_sourceRecord.ColumnLocalTime = java.time.LocalTime.of(6, 6, 6);
		o_sourceRecord.ColumnDoubleCol = 123456.789;
		o_sourceRecord.ColumnDecimal = new java.math.BigDecimal("123.456789");
		o_sourceRecord.ColumnBool = false;
		o_sourceRecord.ColumnText2 = "Das ist das Haus vom Nikolaus #6";
		o_sourceRecord.ColumnShortText2 = "Sechs Datensatz";
		
		int i_foo = o_sourceRecord.insertRecord();
		
		assertTrue(
			i_foo == 6,
			"InsertRecord result is not '6', but '" + i_foo + "'"
		);
		
		/* test GetRecords(true) */
		
		o_sourceRecord = new DDLRecordCopyAndCompare();
		
		java.util.List<DDLRecordCopyAndCompare> a_sourceRecords = o_sourceRecord.getRecords(true);
		
		assertTrue(
			a_sourceRecords.size() == 6,
			"GetRecords(true) result rows are not '6', but '" + a_sourceRecords.size() + "'"
		);
		
		int i = 1;
		
		/* very strict unit test for expected types and values */
		
		for (DDLRecordCopyAndCompare o_record : a_sourceRecords) {
			checkDDLRecord(o_record, i);
			
			i++;
		}

		/* copy records from source to destination */
		
		o_glob.Base = this.o_destinationBase;
		o_glob.BaseGateway = this.e_destinationGateway;

		java.util.List<DDLRecordCopyAndCompare> a_newRecords = new java.util.ArrayList<DDLRecordCopyAndCompare>();

		for (DDLRecordCopyAndCompare o_record : a_sourceRecords) {
			DDLRecordCopyAndCompare o_newRecord = new DDLRecordCopyAndCompare();
			o_newRecord.MarkStringValues = true;
			o_newRecord.copyFrom(o_record);
			a_newRecords.add(o_newRecord);
		}

		DDLRecordCopyAndCompare o_destinationRecord = new DDLRecordCopyAndCompare();
		i_foo = o_destinationRecord.insertManyRecords(a_newRecords, true);

		assertTrue(
			i_foo == 6,
			"Amount of inserted rows is not '6', but '" + i_foo + "'"
		);

		/* test GetRecords(true) */
		
		o_destinationRecord = new DDLRecordCopyAndCompare();
		
		java.util.List<DDLRecordCopyAndCompare> a_destinationRecords = o_destinationRecord.getRecords(true);
		
		assertTrue(
			a_destinationRecords.size() == 6,
			"GetRecords(true) result rows are not '6', but '" + a_destinationRecords.size() + "'"
		);
		
		i = 1;
		
		/* very strict unit test for expected types and values */
		
		for (DDLRecordCopyAndCompare o_record : a_destinationRecords) {
			checkDDLRecord(o_record, i);
			
			i++;
		}

		/* compare records between source and destination */

		for (i = 0; i < a_destinationRecords.size(); i++) {
			assertTrue(
				a_destinationRecords.get(i).equalsTo(a_sourceRecords.get(i)),
				"records are not equal\n" + a_destinationRecords.get(i).returnColumns() + "\n" + a_sourceRecords.get(i).returnColumns()
			);
		}
	}

	private void checkDDLRecord(DDLRecordCopyAndCompare p_a_record, int p_i_id) throws Exception {
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
			assertEquals("Datensatz Zwei", p_a_record.ColumnShortText, "ColumnShortText[" + p_a_record.ColumnShortText + "] is not equal to 'Datensatz Zwei'");
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
			assertEquals("Datensatz Drei", p_a_record.ColumnShortText, "ColumnShortText[" + p_a_record.ColumnShortText + "] is not equal to 'Datensatz Drei'");
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
		}
	}
}
