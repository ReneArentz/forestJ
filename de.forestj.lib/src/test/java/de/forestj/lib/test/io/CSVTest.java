package de.forestj.lib.test.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CSVTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testCSV() {
		try {
			String s_currentDirectory = de.forestj.lib.io.File.getCurrentDirectory();
			String s_file = s_currentDirectory + de.forestj.lib.io.File.DIR + "file.csv";
			de.forestj.lib.io.CSV o_csv = new de.forestj.lib.io.CSV(";", "*");
			
			if (de.forestj.lib.io.File.exists(s_file)) de.forestj.lib.io.File.deleteFile(s_file);
			assertFalse( de.forestj.lib.io.File.exists(s_file), "file[" + s_file + "] does exist" );
			
			CSVTest.csvClass(o_csv, s_file);
			
			if (de.forestj.lib.io.File.exists(s_file)) de.forestj.lib.io.File.deleteFile(s_file);
			assertFalse( de.forestj.lib.io.File.exists(s_file), "file[" + s_file + "] does exist" );
			
			CSVTest.csvClassArray(o_csv, s_file);
			
			if (de.forestj.lib.io.File.exists(s_file)) de.forestj.lib.io.File.deleteFile(s_file);
			assertFalse( de.forestj.lib.io.File.exists(s_file), "file[" + s_file + "] does exist" );
			
			CSVTest.csvInnerClass(o_csv, s_file);
			
			if (de.forestj.lib.io.File.exists(s_file)) de.forestj.lib.io.File.deleteFile(s_file);
			assertFalse( de.forestj.lib.io.File.exists(s_file), "file[" + s_file + "] does exist" );
			
			CSVTest.csvInnerClassArray(o_csv, s_file);
			
			if (de.forestj.lib.io.File.exists(s_file)) de.forestj.lib.io.File.deleteFile(s_file);
			assertFalse( de.forestj.lib.io.File.exists(s_file), "file[" + s_file + "] does exist" );
			
			CSVTest.csvOtherInnerClass(o_csv, s_file);
			
			if (de.forestj.lib.io.File.exists(s_file)) de.forestj.lib.io.File.deleteFile(s_file);
			assertFalse( de.forestj.lib.io.File.exists(s_file), "file[" + s_file + "] does exist" );
			
			CSVTest.csvOtherInnerClassArray(o_csv, s_file);
			
			if (de.forestj.lib.io.File.exists(s_file)) de.forestj.lib.io.File.deleteFile(s_file);
			assertFalse( de.forestj.lib.io.File.exists(s_file), "file[" + s_file + "] does exist" );
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
	
	private static void csvClass(de.forestj.lib.io.CSV p_o_csv, String p_s_file) throws Exception {
		de.forestj.lib.test.sql.AllTypesRecord o_recordOut = new de.forestj.lib.test.sql.AllTypesRecord();
		o_recordOut.ColumnId = 1;
		o_recordOut.ColumnUUID = "ab1824ce-72b8-4aad-bb12-dbc448682437";
		o_recordOut.ColumnShortText = "Datensatz Eins";
		o_recordOut.ColumnText = "Die Handelsstreitigkeiten zwischen den; \"USA und China\" sorgen für eine Art Umdenken auf beiden Seiten. Während US-Unternehmen chinesische Hardware meiden, tun dies chinesische Unternehmen wohl mittlerweile auch: So denken laut einem Bericht der Nachrichtenagentur Bloomberg viele chinesische Hersteller stark darüber nach, ihre IT-Infrastruktur von lokalen Unternehmen statt von den US-Konzernen Oracle und IBM zu kaufen. Für diese Unternehmen sei der asiatische Markt wichtig. 16 respektive mehr als 20 Prozent des Umsatzes stammen aus dieser Region.";
		o_recordOut.ColumnSmallInt = 1;
		o_recordOut.ColumnInt = 10001;
		o_recordOut.ColumnBigInt = 100001111;
		o_recordOut.ColumnTimestamp = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss", java.util.Locale.GERMAN).parse("01.01.2019 01:01:01");
		o_recordOut.ColumnDate = new java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.GERMAN).parse("01.01.2001");
		o_recordOut.ColumnTime = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("01:01:01");
		o_recordOut.ColumnLocalTime = java.time.LocalTime.of(2, 2, 2);
		o_recordOut.ColumnLocalDate = java.time.LocalDate.of(2002, 2, 2);
		o_recordOut.ColumnLocalDateTime = java.time.LocalDateTime.of(2019, 2, 2, 2, 2, 2);
		o_recordOut.ColumnDoubleCol = 1.23456789;
		o_recordOut.ColumnDecimal = new java.math.BigDecimal(12345678.9);
		o_recordOut.ColumnBool = true;
		o_recordOut.ColumnText2 = "Das ist das Haus vom Nikolaus* #1";
		o_recordOut.ColumnShortText2 = "Eins Datensatz";
		
		de.forestj.lib.io.File o_file = p_o_csv.encodeCSV(o_recordOut, p_s_file, true, true);
		
		de.forestj.lib.test.sql.AllTypesRecord o_recordIn = new de.forestj.lib.test.sql.AllTypesRecord();
		p_o_csv.decodeCSV(p_s_file, o_recordIn);
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(o_recordOut, o_recordIn, p_o_csv.getUsePropertyMethods()),
				"output object class is not equal to input object class"
				);
		
		assertEquals(
				o_file.getFileContent(),
				p_o_csv.printCSV(o_recordIn),
				"output object class csv string is not equal to input object class csv string"
				);
	}

	private static void csvClassArray(de.forestj.lib.io.CSV p_o_csv, String p_s_file) throws Exception {
		java.util.List<de.forestj.lib.test.sql.AllTypesRecord> a_recordsOut = new java.util.ArrayList<de.forestj.lib.test.sql.AllTypesRecord>();
		
		de.forestj.lib.test.sql.AllTypesRecord o_recordTemp = new de.forestj.lib.test.sql.AllTypesRecord();
		o_recordTemp.ColumnId = 1;
		o_recordTemp.ColumnUUID = "ab1824ce-72b8-4aad-bb12-dbc448682437";
		o_recordTemp.ColumnShortText = "Datensatz Eins";
		o_recordTemp.ColumnText = "Die Handelsstreitigkeiten zwischen den; \"USA und China\" sorgen für eine Art Umdenken auf beiden Seiten. Während US-Unternehmen chinesische Hardware meiden, tun dies chinesische Unternehmen wohl mittlerweile auch: So denken laut einem Bericht der Nachrichtenagentur Bloomberg viele chinesische Hersteller stark darüber nach, ihre IT-Infrastruktur von lokalen Unternehmen statt von den US-Konzernen Oracle und IBM zu kaufen. Für diese Unternehmen sei der asiatische Markt wichtig. 16 respektive mehr als 20 Prozent des Umsatzes stammen aus dieser Region.";
		o_recordTemp.ColumnSmallInt = 1;
		o_recordTemp.ColumnInt = 10001;
		o_recordTemp.ColumnBigInt = 100001111;
		o_recordTemp.ColumnTimestamp = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss", java.util.Locale.GERMAN).parse("01.01.2019 01:01:01");
		o_recordTemp.ColumnDate = new java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.GERMAN).parse("01.01.2001");
		o_recordTemp.ColumnTime = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("01:01:01");
		o_recordTemp.ColumnLocalTime = null;
		o_recordTemp.ColumnLocalDate = java.time.LocalDate.of(2002, 2, 2);
		o_recordTemp.ColumnLocalDateTime = java.time.LocalDateTime.of(2019, 2, 2, 2, 2, 2);
		o_recordTemp.ColumnDoubleCol = 1.23456789;
		o_recordTemp.ColumnDecimal = new java.math.BigDecimal(12345678.9);
		o_recordTemp.ColumnBool = true;
		o_recordTemp.ColumnText2 = "Das ist das Haus vom Nikolaus* #1";
		o_recordTemp.ColumnShortText2 = "Eins Datensatz";
		
		a_recordsOut.add(o_recordTemp);
		
		o_recordTemp = new de.forestj.lib.test.sql.AllTypesRecord();
		o_recordTemp.ColumnId = 2;
		o_recordTemp.ColumnUUID = "bb1824ce-72b8-4aad-bb12-dbc448682437";
		o_recordTemp.ColumnShortText = "Datensatz Zwei";
		o_recordTemp.ColumnText = "Die Handelsstreitigkeiten zwischen den; \"USA und China\" sorgen für eine Art Umdenken auf beiden Seiten. Während US-Unternehmen chinesische Hardware meiden, tun dies chinesische Unternehmen wohl mittlerweile auch: So denken laut einem Bericht der Nachrichtenagentur Bloomberg viele chinesische Hersteller stark darüber nach, ihre IT-Infrastruktur von lokalen Unternehmen statt von den US-Konzernen Oracle und IBM zu kaufen. Für diese Unternehmen sei der asiatische Markt wichtig. 16 respektive mehr als 20 Prozent des Umsatzes stammen aus dieser Region.";
		o_recordTemp.ColumnSmallInt = 2;
		o_recordTemp.ColumnInt = 20002;
		o_recordTemp.ColumnBigInt = 200002222;
		o_recordTemp.ColumnTimestamp = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss", java.util.Locale.GERMAN).parse("02.02.2019 02:02:02");
		o_recordTemp.ColumnDate = new java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.GERMAN).parse("02.02.2002");
		o_recordTemp.ColumnTime = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("02:02:02");
		o_recordTemp.ColumnLocalTime = java.time.LocalTime.of(3, 3, 3);
		o_recordTemp.ColumnLocalDate = java.time.LocalDate.of(2003, 3, 3);
		o_recordTemp.ColumnLocalDateTime = null;
		o_recordTemp.ColumnDoubleCol = 12.3456789;
		o_recordTemp.ColumnDecimal = new java.math.BigDecimal(1234567.89);
		o_recordTemp.ColumnBool = false;
		o_recordTemp.ColumnText2 = "Das ist das Haus vom Nikolaus* #2";
		o_recordTemp.ColumnShortText2 = "Zwei Datensatz";
		
		a_recordsOut.add(o_recordTemp);
		
		o_recordTemp = new de.forestj.lib.test.sql.AllTypesRecord();
		o_recordTemp.ColumnId = 3;
		o_recordTemp.ColumnUUID = "cb1824ce-72b8-4aad-bb12-dbc448682437";
		o_recordTemp.ColumnShortText = "Datensatz Drei";
		o_recordTemp.ColumnText = "Die Handelsstreitigkeiten zwischen den; \"USA und China\" sorgen für eine Art Umdenken auf beiden Seiten. Während US-Unternehmen chinesische Hardware meiden, tun dies chinesische Unternehmen wohl mittlerweile auch: So denken laut einem Bericht der Nachrichtenagentur Bloomberg viele chinesische Hersteller stark darüber nach, ihre IT-Infrastruktur von lokalen Unternehmen statt von den US-Konzernen Oracle und IBM zu kaufen. Für diese Unternehmen sei der asiatische Markt wichtig. 16 respektive mehr als 20 Prozent des Umsatzes stammen aus dieser Region.";
		o_recordTemp.ColumnSmallInt = 3;
		o_recordTemp.ColumnInt = 30003;
		o_recordTemp.ColumnBigInt = 300003333;
		o_recordTemp.ColumnTimestamp = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss", java.util.Locale.GERMAN).parse("03.03.2019 03:03:03");
		o_recordTemp.ColumnDate = new java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.GERMAN).parse("03.03.2003");
		o_recordTemp.ColumnTime = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.GERMAN).parse("03:03:03");
		o_recordTemp.ColumnLocalTime = java.time.LocalTime.of(4, 4, 4);
		o_recordTemp.ColumnLocalDate = null;
		o_recordTemp.ColumnLocalDateTime = java.time.LocalDateTime.of(2019, 4, 4, 4, 4, 4);
		o_recordTemp.ColumnDoubleCol = 123.456789;
		o_recordTemp.ColumnDecimal = new java.math.BigDecimal(123456.789);
		o_recordTemp.ColumnBool = true;
		o_recordTemp.ColumnText2 = "Das ist das Haus vom Nikolaus* #3";
		o_recordTemp.ColumnShortText2 = "Drei Datensatz";
		
		a_recordsOut.add(o_recordTemp);
		
		p_o_csv.setReturnArrayElementNullNotZero(true);
		
		de.forestj.lib.io.File o_file = p_o_csv.encodeCSV(a_recordsOut, p_s_file, true, true);
		
		java.util.List<de.forestj.lib.test.sql.AllTypesRecord> a_recordsIn = new java.util.ArrayList<de.forestj.lib.test.sql.AllTypesRecord>();
		p_o_csv.decodeCSV(p_s_file, a_recordsIn, de.forestj.lib.test.sql.AllTypesRecord.class);
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(a_recordsOut, a_recordsIn, p_o_csv.getUsePropertyMethods()),
				"output object class array is not equal to input object class array"
				);
		
		assertEquals(
				o_file.getFileContent(),
				p_o_csv.printCSV(a_recordsIn),
				"output object class array csv string is not equal to input object class array csv string"
				);
		
		p_o_csv.setReturnArrayElementNullNotZero(false);
	}

	private static void csvInnerClass(de.forestj.lib.io.CSV p_o_csv, String p_s_file) throws Exception {
		Data o_data = new Data();
		Data.ShipItem o_shipItemOut = o_data.new ShipItem();
		o_shipItemOut.setTitle("Item #1");
		o_shipItemOut.setNote("high; value");
		o_shipItemOut.setManufacturedTime(java.time.LocalTime.of(12, 6, 3));
		o_shipItemOut.setQuantity(2);
		o_shipItemOut.setPrice(new java.math.BigDecimal(500.2d));
		o_shipItemOut.setCurrency("USD");
		o_shipItemOut.setSkonto(12.50d);
		o_shipItemOut.setSomeDecimals(
			new java.math.BigDecimal[] {
				new java.math.BigDecimal("1.602176634"),
				new java.math.BigDecimal("8.8541878128"),
				new java.math.BigDecimal("6.62607015"),
				new java.math.BigDecimal("9.80665"),
				new java.math.BigDecimal("3.14159265359")
			}
		);
		o_shipItemOut.setShipItemInfo(o_data.new ShipItemInfo());
		o_shipItemOut.getShipItemInfo().setDevelopment("Development 2.2");
		o_shipItemOut.getShipItemInfo().setImplementation("Implementation 2.2");
		
		p_o_csv.setUsePropertyMethods(true);
		
		de.forestj.lib.io.File o_file = p_o_csv.encodeCSV(o_shipItemOut, p_s_file, true, true);
		
		Data.ShipItem o_shipItemIn = o_data.new ShipItem();
		p_o_csv.decodeCSV(p_s_file, o_shipItemIn);
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(o_shipItemOut, o_shipItemIn, p_o_csv.getUsePropertyMethods()),
				"output object inner class is not equal to input object inner class"
				);
		
		assertEquals(
				o_file.getFileContent(),
				p_o_csv.printCSV(o_shipItemIn),
				"output object inner class csv string is not equal to input object inner class csv string"
				);
		
		p_o_csv.setUsePropertyMethods(false);
	}
	
	private static void csvInnerClassArray(de.forestj.lib.io.CSV p_o_csv, String p_s_file) throws Exception {
		Data o_data = new Data();
		java.util.List<Data.SimpleClass> a_dataOut = new java.util.ArrayList<Data.SimpleClass>();
		a_dataOut.add(o_data.new SimpleClass("Record #1 Value A", "Record #1 Value B", "Record #1 Value C"));
		a_dataOut.add(o_data.new SimpleClass("Record #2 Value A", "Record #2 Value B", "", java.util.Arrays.asList(1, 2, -3, -4)));
		a_dataOut.add(o_data.new SimpleClass("\"Record #3; Value A", "null", "Record #3 Value C", java.util.Arrays.asList(9, 8, -7, -6), new float[] {42.0f, 21.25f, 54987.456999f}));
		a_dataOut.add(o_data.new SimpleClass("Record #4 Value A", "Record $4 ;Value B \"", null, java.util.Arrays.asList(16, 32, null, 128, 0)));
		
		p_o_csv.setReturnArrayElementNullNotZero(true);
		
		de.forestj.lib.io.File o_file = p_o_csv.encodeCSV(a_dataOut, p_s_file, true, true);
		
		java.util.List<Data.SimpleClass> a_dataIn = new java.util.ArrayList<Data.SimpleClass>();
		p_o_csv.decodeCSV(p_s_file, a_dataIn, Data.SimpleClass.class);
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut, a_dataIn, p_o_csv.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		assertEquals(
				o_file.getFileContent(),
				p_o_csv.printCSV(a_dataIn),
				"output object inner class array csv string is not equal to input object inner class array csv string"
				);
		
		p_o_csv.setReturnArrayElementNullNotZero(false);
	}

	private static void csvOtherInnerClass(de.forestj.lib.io.CSV p_o_csv, String p_s_file) throws Exception {
		Data o_data = new Data();
		Data.ShipOrder o_shipOrderOut = o_data.new ShipOrder();
		o_shipOrderOut.setOrderId("ORD0001");
		o_shipOrderOut.setOrderPerson("Jon Doe");
		o_shipOrderOut.setOrderDate(java.time.LocalDate.of(2020, 1, 25));
		o_shipOrderOut.setOverallPrice(388.95f);
		o_shipOrderOut.setSomeBools( new boolean[] { false, true, false } );
		
		p_o_csv.setUsePropertyMethods(true);
		
		de.forestj.lib.io.File o_file = p_o_csv.encodeCSV(o_shipOrderOut, p_s_file, true, true);
		
		Data.ShipOrder o_shipOrderIn = o_data.new ShipOrder();
		p_o_csv.decodeCSV(p_s_file, o_shipOrderIn);
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(o_shipOrderOut, o_shipOrderIn, p_o_csv.getUsePropertyMethods()),
				"output object other inner class is not equal to input object other inner class"
				);
		
		assertEquals(
				o_file.getFileContent(),
				p_o_csv.printCSV(o_shipOrderIn),
				"output object other inner class csv string is not equal to input object other inner class csv string"
				);
		
		p_o_csv.setUsePropertyMethods(false);
	}
	
	private static void csvOtherInnerClassArray(de.forestj.lib.io.CSV p_o_csv, String p_s_file) throws Exception {
		Data o_data = new Data();
		Data.ShipItem o_shipItem1 = o_data.new ShipItem();
			o_shipItem1.setTitle("Item #1");
			o_shipItem1.setNote("----");
			o_shipItem1.setManufacturedTime(java.time.LocalTime.of(13, 31, 0));
			o_shipItem1.setQuantity(15);
			o_shipItem1.setPrice(new java.math.BigDecimal(5.25d));
			o_shipItem1.setCurrency("EUR");
			o_shipItem1.setSkonto(2.15d);
			o_shipItem1.setShipItemInfo(o_data.new ShipItemInfo());
			o_shipItem1.getShipItemInfo().setDevelopment("Development 1.1");
			
		Data.ShipItem o_shipItem2 = o_data.new ShipItem();
			o_shipItem2.setTitle("Item #2");
			o_shipItem2.setNote("be careful");
			o_shipItem2.setManufacturedTime(java.time.LocalTime.of(20, 15, 33));
			o_shipItem2.setQuantity(35);
			o_shipItem2.setPrice(new java.math.BigDecimal(1.88d));
			o_shipItem2.setCurrency("EUR");
			o_shipItem2.setSkonto(5.00d);
			o_shipItem2.setShipItemInfo(o_data.new ShipItemInfo());
			o_shipItem2.getShipItemInfo().setDevelopment("Development 1.2");
			o_shipItem2.getShipItemInfo().setImplementation("Implementation 1.2");
			
		Data.ShipItem o_shipItem3 = o_data.new ShipItem();
			o_shipItem3.setTitle("Item #3");
			o_shipItem3.setNote("store cold");
			o_shipItem3.setManufacturedTime(java.time.LocalTime.of(3, 7, 12));
			o_shipItem3.setQuantity(5);
			o_shipItem3.setPrice(new java.math.BigDecimal(12.23d));
			o_shipItem3.setCurrency("USD");
			o_shipItem3.setSkonto(7.86d);
			o_shipItem3.setShipItemInfo(o_data.new ShipItemInfo());
			o_shipItem3.getShipItemInfo().setConstruction("Construction 1.3");
			
		java.util.List<Data.ShipItem> a_dataShipItemOut = new java.util.ArrayList<Data.ShipItem>();
		a_dataShipItemOut.add(o_shipItem1);
		a_dataShipItemOut.add(o_shipItem2);
		a_dataShipItemOut.add(o_shipItem3);
		
		p_o_csv.setUsePropertyMethods(true);
		
		de.forestj.lib.io.File o_file = p_o_csv.encodeCSV(a_dataShipItemOut, p_s_file, true, true);
		
		java.util.List<Data.ShipItem> a_dataShipItemIn = new java.util.ArrayList<Data.ShipItem>();
		p_o_csv.decodeCSV(p_s_file, a_dataShipItemIn, Data.ShipItem.class);
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(a_dataShipItemOut, a_dataShipItemIn, p_o_csv.getUsePropertyMethods()),
				"output object other inner class array is not equal to input object other inner class array"
				);
		
		assertEquals(
				o_file.getFileContent(),
				p_o_csv.printCSV(a_dataShipItemIn),
				"output object other inner class array csv string is not equal to input object other inner class array csv string"
				);
		
		p_o_csv.setUsePropertyMethods(false);
	}
}
