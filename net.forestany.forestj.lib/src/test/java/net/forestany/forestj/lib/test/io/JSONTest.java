package net.forestany.forestj.lib.test.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class JSONTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testJSON() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			
			String s_currentDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory();
			String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testJSON" + net.forestany.forestj.lib.io.File.DIR;
			
			if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
				net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			}
			
			net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
			assertTrue(
					net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
					"directory[" + s_testDirectory + "] does not exist"
			);
			
				jsonValidate();
				jsonVirtualFiles(s_testDirectory, "_0");
				jsonObject(s_testDirectory, "_1");
				jsonClass(s_testDirectory, "TestJSONSchemaClass.json", "_2");
				jsonArray(s_testDirectory, "TestJSONSchemaSimpleClassArray.json", "_A");
				jsonArray(s_testDirectory, "TestJSONSchemaSimpleClassObjectMultiReferences.json", "_B");
				jsonArray(s_testDirectory, "TestJSONSchemaSimpleClassObjectOneReference.json", "_C");
				jsonArray(s_testDirectory, "TestJSONSchemaSimpleClassNoReferences.json", "_D");
				jsonComplex(s_testDirectory, true, false, false, false, "_E");
				jsonComplex(s_testDirectory, false, true, false, false, "_F");
				jsonComplex(s_testDirectory, false, false, true, false, "_G");
				jsonComplex(s_testDirectory, false, false, false, true, "_H");
				jsonMultipleUseOfOneSchemaObject(s_testDirectory);
				
			net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			assertFalse(
					net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
					"directory[" + s_testDirectory + "] does exist"
			);
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
	
	private static void jsonValidate() throws Exception {
		String s_jsonSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "json" + net.forestany.forestj.lib.io.File.DIR + "TestJSONSchemaClassRootWithRef.json";
		String s_jsonFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "json" + net.forestany.forestj.lib.io.File.DIR + "ValidateJSON.json";
		String s_invalidJsonFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "json" + net.forestany.forestj.lib.io.File.DIR + "ValidateJSONIsInvalid.json";
		
		assertTrue(
				new net.forestany.forestj.lib.io.JSON(s_jsonSchemaFile).validateAgainstSchema(s_jsonFile),
				"file 'ValidateYJSON.json' is not valid with schema 'TestJSONSchemaClassRootWithRef.json'"
				);
		
		boolean b_check = true;
		
		try {
			assertFalse(
					new net.forestany.forestj.lib.io.JSON(s_jsonSchemaFile).validateAgainstSchema(s_invalidJsonFile),
					"file 'ValidateJSONIsInvalid.json' is valid with schema 'TestJSONSchemaClassRootWithRef.json'"
					);
		} catch (Exception o_exc) {
			b_check = false;
		}
		
		if (b_check) {
			assertTrue(
					false,
					"file 'ValidateJSONIsInvalid.json' is valid with schema 'TestJSONSchemaClassRootWithRef.json'"
			);
		}
	}
	
	private static void jsonVirtualFiles(String p_s_testDirectory, String p_s_fileNameSuffix) throws Exception {
		net.forestany.forestj.lib.test.sql.AllTypesRecord o_recordOut = new net.forestany.forestj.lib.test.sql.AllTypesRecord();
		o_recordOut.ColumnId = 1;
		o_recordOut.ColumnUUID = "ab1824ce-72b8-4aad-bb12-dbc448682437";
		o_recordOut.ColumnShortText = "Datensatz Eins";
		o_recordOut.ColumnText = "Die Handelsstreitigkeiten zwischen den; \"USA und China\" sorgen für eine Art Umdenken auf beiden Seiten. Während US-Unternehmen chinesische Hardware meiden, tun dies chinesische Unternehmen wohl mittlerweile auch: So denken laut einem Bericht der Nachrichtenagentur Bloomberg viele chinesische Hersteller stark darüber nach, ihre IT-Infrastruktur von lokalen Unternehmen statt von den US-Konzernen Oracle und IBM zu kaufen. Für diese Unternehmen sei der asiatische Markt wichtig. 16 respektive mehr als 20 Prozent des Umsatzes stammen aus dieser Region.";
		o_recordOut.ColumnSmallInt = 1;
		o_recordOut.ColumnInt = 10001;
		o_recordOut.ColumnBigInt = 100001111;
		o_recordOut.ColumnLocalDateTime = java.time.LocalDateTime.of(2019, 2, 2, 2, 2, 2);
		
		net.forestany.forestj.lib.io.JSON o_jsonEmpty = new net.forestany.forestj.lib.io.JSON();
		net.forestany.forestj.lib.io.JSON.JSONElement o_jsonSchema = o_jsonEmpty.new JSONElement("Root");
		o_jsonSchema.setType("object");
		o_jsonSchema.setMappingClass("net.forestany.forestj.lib.test.sql.AllTypesRecord");
		
			net.forestany.forestj.lib.io.JSON.JSONElement o_jsonElement = o_jsonEmpty.new JSONElement("Id");
			o_jsonElement.setType("integer");
			o_jsonElement.setMappingClass("ColumnId");
			o_jsonElement.setRequired(true);
			o_jsonSchema.getChildren().add(o_jsonElement);
			
			o_jsonElement = o_jsonEmpty.new JSONElement("UUID");
			o_jsonElement.setType("string");
			o_jsonElement.setMappingClass("ColumnUUID");
			o_jsonElement.setRequired(true);
			o_jsonSchema.getChildren().add(o_jsonElement);
			
			o_jsonElement = o_jsonEmpty.new JSONElement("ShortText");
			o_jsonElement.setType("string");
			o_jsonElement.setMappingClass("ColumnShortText");
			o_jsonElement.setRequired(true);
			o_jsonSchema.getChildren().add(o_jsonElement);
			
			o_jsonElement = o_jsonEmpty.new JSONElement("Text");
			o_jsonElement.setType("string");
			o_jsonElement.setMappingClass("ColumnText");
			o_jsonSchema.getChildren().add(o_jsonElement);
			
			o_jsonElement = o_jsonEmpty.new JSONElement("SmallInt");
			o_jsonElement.setType("integer");
			o_jsonElement.setMappingClass("ColumnSmallInt");
			o_jsonSchema.getChildren().add(o_jsonElement);
			
			o_jsonElement = o_jsonEmpty.new JSONElement("Int");
			o_jsonElement.setType("integer");
			o_jsonElement.setMappingClass("ColumnInt");
			o_jsonSchema.getChildren().add(o_jsonElement);
			
			o_jsonElement = o_jsonEmpty.new JSONElement("BigInt");
			o_jsonElement.setType("integer");
			o_jsonElement.setMappingClass("ColumnBigInt");
			o_jsonSchema.getChildren().add(o_jsonElement);
			
			o_jsonElement = o_jsonEmpty.new JSONElement("LocalDateTime");
			o_jsonElement.setType("string");
			o_jsonElement.setMappingClass("ColumnLocalDateTime");
			o_jsonSchema.getChildren().add(o_jsonElement);
		
		net.forestany.forestj.lib.io.JSON o_json = new net.forestany.forestj.lib.io.JSON(o_jsonSchema);
		
		String s_jsonEncoded = o_json.jsonEncode(o_recordOut);
		
		java.util.List<String> a_fileLines = new java.util.ArrayList<String>();

		for (String s_line : s_jsonEncoded.split(System.lineSeparator())) {
			a_fileLines.add(s_line);
		}
		
		net.forestany.forestj.lib.test.sql.AllTypesRecord o_recordIn = (net.forestany.forestj.lib.test.sql.AllTypesRecord)o_json.jsonDecode(a_fileLines);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_recordOut, o_recordIn, o_json.getUsePropertyMethods()),
				"output object class is not equal to input object class"
				);
		
		String s_virtualJsonFile = ""
				+ "{" + System.lineSeparator()
				+ "	\"Id\": 42," + System.lineSeparator()
				+ "	\"UUID\": \"421824ce-72b8-4aad-bb12-dbc448682437\"," + System.lineSeparator()
				+ "	\"ShortText\": \"Datensatz 42\"," + System.lineSeparator()
				+ "	\"Text\": \"Die Handelsstreitigkeiten zwischen den; \\\"42 und 42\\\" sorgen für eine Art Umdenken auf beiden Seiten.\"," + System.lineSeparator()
				+ "	\"SmallInt\": -42," + System.lineSeparator()
				+ "	\"Int\": 40002," + System.lineSeparator()
				+ "	\"BigInt\": 400001112," + System.lineSeparator()
				+ "	\"LocalDateTime\": \"2042-02-02T01:02:02Z\"" + System.lineSeparator()
				+ "}" + System.lineSeparator();
		
		o_recordOut = new net.forestany.forestj.lib.test.sql.AllTypesRecord();
		o_recordOut.ColumnId = 42;
		o_recordOut.ColumnUUID = "421824ce-72b8-4aad-bb12-dbc448682437";
		o_recordOut.ColumnShortText = "Datensatz 42";
		o_recordOut.ColumnText = "Die Handelsstreitigkeiten zwischen den; \"42 und 42\" sorgen für eine Art Umdenken auf beiden Seiten.";
		o_recordOut.ColumnSmallInt = -42;
		o_recordOut.ColumnInt = 40002;
		o_recordOut.ColumnBigInt = 400001112;
		o_recordOut.ColumnLocalDateTime = java.time.LocalDateTime.of(2042, 2, 2, 2, 2, 2);
		
		a_fileLines = new java.util.ArrayList<String>();

		for (String s_line : s_virtualJsonFile.split(System.lineSeparator())) {
			a_fileLines.add(s_line);
		}
		
		o_recordIn = (net.forestany.forestj.lib.test.sql.AllTypesRecord)o_json.jsonDecode(a_fileLines);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_recordOut, o_recordIn, o_json.getUsePropertyMethods()),
				"output object class is not equal to input object class"
				);
	}
	
	private static void jsonObject(String p_s_testDirectory, String p_s_fileNameSuffix) throws Exception {
		net.forestany.forestj.lib.test.sql.AllTypesRecord o_recordOut = new net.forestany.forestj.lib.test.sql.AllTypesRecord();
		o_recordOut.ColumnId = 1;
		o_recordOut.ColumnUUID = "ab1824ce-72b8-4aad-bb12-dbc448682437";
		o_recordOut.ColumnShortText = "Datensatz Eins";
		o_recordOut.ColumnText = "Die Handelsstreitigkeiten zwischen den; \"USA und China\" sorgen für eine Art Umdenken auf beiden Seiten. Während US-Unternehmen chinesische Hardware meiden, tun dies chinesische Unternehmen wohl mittlerweile auch: So denken laut einem Bericht der Nachrichtenagentur Bloomberg viele chinesische Hersteller stark darüber nach, ihre IT-Infrastruktur von lokalen Unternehmen statt von den US-Konzernen Oracle und IBM zu kaufen. Für diese Unternehmen sei der asiatische Markt wichtig. 16 respektive mehr als 20 Prozent des Umsatzes stammen aus dieser Region.";
		o_recordOut.ColumnSmallInt = 1;
		o_recordOut.ColumnInt = 10001;
		o_recordOut.ColumnBigInt = 100001111;
		o_recordOut.ColumnLocalDateTime = java.time.LocalDateTime.of(2019, 2, 2, 2, 2, 2);
		
		net.forestany.forestj.lib.io.JSON o_jsonEmpty = new net.forestany.forestj.lib.io.JSON();
		net.forestany.forestj.lib.io.JSON.JSONElement o_jsonSchema = o_jsonEmpty.new JSONElement("Root");
		o_jsonSchema.setType("object");
		o_jsonSchema.setMappingClass("net.forestany.forestj.lib.test.sql.AllTypesRecord");
		
			net.forestany.forestj.lib.io.JSON.JSONElement o_jsonElement = o_jsonEmpty.new JSONElement("Id");
			o_jsonElement.setType("integer");
			o_jsonElement.setMappingClass("ColumnId");
			o_jsonElement.setRequired(true);
			o_jsonSchema.getChildren().add(o_jsonElement);
			
			o_jsonElement = o_jsonEmpty.new JSONElement("UUID");
			o_jsonElement.setType("string");
			o_jsonElement.setMappingClass("ColumnUUID");
			o_jsonElement.setRequired(true);
			o_jsonSchema.getChildren().add(o_jsonElement);
			
			o_jsonElement = o_jsonEmpty.new JSONElement("ShortText");
			o_jsonElement.setType("string");
			o_jsonElement.setMappingClass("ColumnShortText");
			o_jsonElement.setRequired(true);
			o_jsonSchema.getChildren().add(o_jsonElement);
			
			o_jsonElement = o_jsonEmpty.new JSONElement("Text");
			o_jsonElement.setType("string");
			o_jsonElement.setMappingClass("ColumnText");
			o_jsonSchema.getChildren().add(o_jsonElement);
			
			o_jsonElement = o_jsonEmpty.new JSONElement("SmallInt");
			o_jsonElement.setType("integer");
			o_jsonElement.setMappingClass("ColumnSmallInt");
			o_jsonSchema.getChildren().add(o_jsonElement);
			
			o_jsonElement = o_jsonEmpty.new JSONElement("Int");
			o_jsonElement.setType("integer");
			o_jsonElement.setMappingClass("ColumnInt");
			o_jsonSchema.getChildren().add(o_jsonElement);
			
			o_jsonElement = o_jsonEmpty.new JSONElement("BigInt");
			o_jsonElement.setType("integer");
			o_jsonElement.setMappingClass("ColumnBigInt");
			o_jsonSchema.getChildren().add(o_jsonElement);
			
			o_jsonElement = o_jsonEmpty.new JSONElement("LocalDateTime");
			o_jsonElement.setType("string");
			o_jsonElement.setMappingClass("ColumnLocalDateTime");
			o_jsonSchema.getChildren().add(o_jsonElement);
		
		net.forestany.forestj.lib.io.JSON o_json = new net.forestany.forestj.lib.io.JSON(o_jsonSchema);
		
		String s_fileSimpleClass = p_s_testDirectory + "TestJSONObject" + p_s_fileNameSuffix + ".json";
		
		@SuppressWarnings("unused")
		net.forestany.forestj.lib.io.File o_fileSimpleClass = o_json.jsonEncode(o_recordOut, s_fileSimpleClass, true);
		
		net.forestany.forestj.lib.test.sql.AllTypesRecord o_recordIn = (net.forestany.forestj.lib.test.sql.AllTypesRecord)o_json.jsonDecode(s_fileSimpleClass);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_recordOut, o_recordIn, o_json.getUsePropertyMethods()),
				"output object class is not equal to input object class"
				);
	}
	
	private static void jsonClass(String p_s_testDirectory, String p_s_jsonSchemaFileName, String p_s_fileNameSuffix) throws Exception {
		net.forestany.forestj.lib.test.sql.AllTypesRecord o_recordOut = new net.forestany.forestj.lib.test.sql.AllTypesRecord();
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
		
		String s_jsonSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "json" + net.forestany.forestj.lib.io.File.DIR + p_s_jsonSchemaFileName;
		net.forestany.forestj.lib.io.JSON o_json = new net.forestany.forestj.lib.io.JSON(s_jsonSchemaFile);
		
		String s_fileSimpleClass = p_s_testDirectory + "TestJSONClass" + p_s_fileNameSuffix + ".json";
		
		@SuppressWarnings("unused")
		net.forestany.forestj.lib.io.File o_fileSimpleClass = o_json.jsonEncode(o_recordOut, s_fileSimpleClass, true);
		
		net.forestany.forestj.lib.test.sql.AllTypesRecord o_recordIn = (net.forestany.forestj.lib.test.sql.AllTypesRecord)o_json.jsonDecode(s_fileSimpleClass);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_recordOut, o_recordIn, o_json.getUsePropertyMethods()),
				"output object class is not equal to input object class"
				);
	}
	
	private static void jsonArray(String p_s_testDirectory, String p_s_jsonSchemaFileName, String p_s_fileNameSuffix) throws Exception {
		Data o_data = new Data();
		java.util.List<Data.SimpleClass> a_dataOut = new java.util.ArrayList<Data.SimpleClass>();
		a_dataOut.add(o_data.new SimpleClass("Record #1 Value A", "Record #1 Value B", "Record #1 Value C"));
		a_dataOut.add(o_data.new SimpleClass("Record #2 Value A", "Record #2 Value B", "", java.util.Arrays.asList(1, 2, -3, -4)));
		a_dataOut.add(o_data.new SimpleClass("Record #3; Value A", "null", "Record #3 Value C", java.util.Arrays.asList(9, 8, -7, -6), new float[] {42.0f, 21.25f, 54987.456999f}));
		a_dataOut.add(o_data.new SimpleClass("Record 4 Value A", "Record $4 ;Value B \"", null, java.util.Arrays.asList(16, 32, null, 128, 0), new float[] {21.0f, 10.625f}));
		
		Data.SimpleClassCollection o_collectionOut = o_data.new SimpleClassCollection(a_dataOut);
		
		String[] a_out = new String[a_dataOut.size()];
		String[] a_in = new String[a_dataOut.size()];
		int i_cnt = 0;
		
		for (Data.SimpleClass o_simpleClassObject : a_dataOut) {
			a_out[i_cnt++] = o_simpleClassObject.toString();
		}
		
		String s_jsonSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "json" + net.forestany.forestj.lib.io.File.DIR + p_s_jsonSchemaFileName;
		net.forestany.forestj.lib.io.JSON o_json = new net.forestany.forestj.lib.io.JSON(s_jsonSchemaFile);
		
		String s_fileSimpleClass = p_s_testDirectory + "TestJSONSimpleClass" + p_s_fileNameSuffix + ".json";
		
		if (p_s_jsonSchemaFileName.contentEquals("TestJSONSchemaSimpleClassArray.json")) {
			@SuppressWarnings("unused")
			net.forestany.forestj.lib.io.File o_fileSimpleClass = o_json.jsonEncode(a_dataOut, s_fileSimpleClass, true);
		} else {
			@SuppressWarnings("unused")
			net.forestany.forestj.lib.io.File o_fileSimpleClass = o_json.jsonEncode(o_collectionOut, s_fileSimpleClass, true);
		}
		
		Data.SimpleClassCollection o_collectionIn = null;
		java.util.List<Data.SimpleClass> a_dataIn = null;
		
		if (p_s_jsonSchemaFileName.contentEquals("TestJSONSchemaSimpleClassArray.json")) {
			@SuppressWarnings("unchecked")
			java.util.List<Data.SimpleClass> a_foo = (java.util.List<Data.SimpleClass>)o_json.jsonDecode(s_fileSimpleClass);
			a_dataIn = a_foo;
		} else {
			o_collectionIn = (Data.SimpleClassCollection)o_json.jsonDecode(s_fileSimpleClass);
			a_dataIn = o_collectionIn.SimpleClasses;
		}
		
		i_cnt = 0;
		
		for (Data.SimpleClass o_simpleClassObject : a_dataIn) {
			a_in[i_cnt++] = o_simpleClassObject.toString();
		}
		
		if (!p_s_jsonSchemaFileName.contentEquals("TestJSONSchemaSimpleClassArray.json")) {
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_collectionOut, o_collectionIn, o_json.getUsePropertyMethods()),
					"output object inner class collection is not equal to input object inner class collection"
					);
		}
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut, a_dataIn, o_json.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		for (int i = 0; i < a_dataOut.size(); i++) {
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut.get(i), a_dataIn.get(i), o_json.getUsePropertyMethods(), true),
					"output object inner class array element object is not equal to input object inner class array element object"
					);
			
			assertEquals(
					a_out[i],
					a_in[i],
					"output object inner class array element toString is not equal to input object inner class array element toString"
					);
		}
	}

	private static void jsonComplex(String p_s_testDirectory, boolean p_b_classRoot, boolean p_b_listRoot, boolean p_b_classRootWithRef, boolean p_b_listRootWithRef, String p_s_fileNameSuffix) throws Exception {
		Data o_data = new Data();
		java.util.List<Data.ShipOrder> a_shipOrdersOut = Data.generateData();
		
		Data.ShipOrderCollection o_shipOrderCollectionOut = o_data.new ShipOrderCollection();
		o_shipOrderCollectionOut.setShipOrders(a_shipOrdersOut);
		o_shipOrderCollectionOut.setOrderAmount(a_shipOrdersOut.size());
		
		String[] a_out = new String[a_shipOrdersOut.size()];
		String[] a_in = new String[a_shipOrdersOut.size()];
		int i_cnt = 0;
		
		for (Data.ShipOrder o_shipOrderObject : a_shipOrdersOut) {
			a_out[i_cnt++] = o_shipOrderObject.toString();
	    }
		
		String s_jsonSchemaFile = null;
		
		if (p_b_classRoot) {
			s_jsonSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "json" + net.forestany.forestj.lib.io.File.DIR + "TestJSONSchemaClassRoot.json";
		} else if (p_b_listRoot) {
			s_jsonSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "json" + net.forestany.forestj.lib.io.File.DIR + "TestJSONSchemaListRoot.json";
		} else if (p_b_classRootWithRef) {
			s_jsonSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "json" + net.forestany.forestj.lib.io.File.DIR + "TestJSONSchemaClassRootWithRef.json";
		} else if (p_b_listRootWithRef) {
			s_jsonSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "json" + net.forestany.forestj.lib.io.File.DIR + "TestJSONSchemaListRootWithRef.json";
		}
		
		net.forestany.forestj.lib.io.JSON o_json = new net.forestany.forestj.lib.io.JSON(s_jsonSchemaFile);
		o_json.setUsePropertyMethods(true);
		
		String s_file = p_s_testDirectory + "TestJSON" + p_s_fileNameSuffix + ".json";
		
		if ( (p_b_classRoot) || (p_b_classRootWithRef) ) {
			@SuppressWarnings("unused")
			net.forestany.forestj.lib.io.File o_file = o_json.jsonEncode(o_shipOrderCollectionOut, s_file, true);
		} else {
			@SuppressWarnings("unused")
			net.forestany.forestj.lib.io.File o_file = o_json.jsonEncode(a_shipOrdersOut, s_file, true);
		}
		
		java.util.List<Data.ShipOrder> a_shipOrdersIn = null;
		Data.ShipOrderCollection o_shipOrderCollectionIn = null;
		
		if ( (p_b_classRoot) || (p_b_classRootWithRef) ) {
			o_shipOrderCollectionIn = (Data.ShipOrderCollection)o_json.jsonDecode(s_file);
			a_shipOrdersIn = o_shipOrderCollectionIn.getShipOrders();
			o_shipOrderCollectionIn.setShipOrders(a_shipOrdersIn);
			o_shipOrderCollectionIn.setOrderAmount(a_shipOrdersIn.size());
		} else {
			@SuppressWarnings("unchecked")
			java.util.List<Data.ShipOrder> a_foo = (java.util.List<Data.ShipOrder>)o_json.jsonDecode(s_file);
			a_shipOrdersIn = a_foo;
		}
		
		i_cnt = 0;
		
		for (Data.ShipOrder o_shipOrderObject : a_shipOrdersIn) {
			a_in[i_cnt++] = o_shipOrderObject.toString();
    	}
		
		if ( (p_b_classRoot) || (p_b_classRootWithRef) ) {
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_shipOrderCollectionOut, o_shipOrderCollectionIn, o_json.getUsePropertyMethods()),
					"output object inner class collection is not equal to input object inner class collection"
					);
		}
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut, a_shipOrdersIn, o_json.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		for (int i = 0; i < a_shipOrdersIn.size(); i++) {
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut.get(i), a_shipOrdersIn.get(i), o_json.getUsePropertyMethods(), true),
					"output object inner class array element object is not equal to input object inner class array element object"
					);
			
			assertEquals(
					a_out[i],
					a_in[i],
					"output object inner class array element string is not equal to input object inner class array element string"
					);
		}
		
		if (p_b_listRootWithRef) {
			s_file = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "json" + net.forestany.forestj.lib.io.File.DIR + "TestJSONPrimitiveArrayOneLine.json";
			a_in = new String[a_shipOrdersOut.size()];
			a_shipOrdersIn = null;
			o_shipOrderCollectionIn = null;
			
			@SuppressWarnings("unchecked")
			java.util.List<Data.ShipOrder> a_foo = (java.util.List<Data.ShipOrder>)o_json.jsonDecode(s_file);
			a_shipOrdersIn = a_foo;
			
			i_cnt = 0;
			
			for (Data.ShipOrder o_shipOrderObject : a_shipOrdersIn) {
				a_in[i_cnt++] = o_shipOrderObject.toString();
	    	}
			
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut, a_shipOrdersIn, o_json.getUsePropertyMethods()),
					"output object inner class array is not equal to input object inner class array"
					);
			
			for (int i = 0; i < a_shipOrdersIn.size(); i++) {
				assertTrue(
						net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut.get(i), a_shipOrdersIn.get(i), o_json.getUsePropertyMethods(), true),
						"output object inner class array element object is not equal to input object inner class array element object"
						);
				
				assertEquals(
						a_out[i],
						a_in[i],
						"output object inner class array element string is not equal to input object inner class array element string"
						);
			}
		}
	}

	private static void jsonMultipleUseOfOneSchemaObject(String p_s_testDirectory) throws Exception {
		
		/* Simple #1 */
		
		Data o_data = new Data();
		java.util.List<Data.SimpleClass> a_dataOut = new java.util.ArrayList<Data.SimpleClass>();
		a_dataOut.add(o_data.new SimpleClass("Record #1 Value A", "Record #1 Value B", "Record #1 Value C"));
		a_dataOut.add(o_data.new SimpleClass("Record #2 Value A", "Record #2 Value B", "", java.util.Arrays.asList(1, 2, -3, -4)));
		a_dataOut.add(o_data.new SimpleClass("Record #3; Value A", "null", "Record #3 Value C", java.util.Arrays.asList(9, 8, -7, -6)));
		a_dataOut.add(o_data.new SimpleClass("Record #4 Value A", "Record $4 ;Value B \"", null, java.util.Arrays.asList(16, 32, null, 128, 0)));
		
		Data.SimpleClassCollection o_collectionOut = o_data.new SimpleClassCollection(a_dataOut);
		
		String[] a_out = new String[a_dataOut.size()];
		String[] a_in = new String[a_dataOut.size()];
		int i_cnt = 0;
		
		for (Data.SimpleClass o_simpleClassObject : a_dataOut) {
			a_out[i_cnt++] = o_simpleClassObject.toString();
		}
		
		String s_jsonSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "json" + net.forestany.forestj.lib.io.File.DIR + "TestJSONSchemaSimpleClassObjectMultiReferences.json";
		net.forestany.forestj.lib.io.JSON o_json = new net.forestany.forestj.lib.io.JSON(s_jsonSchemaFile);
		
		String s_fileSimpleClass = p_s_testDirectory + "TestJSONSimpleClass_I.json";
		
		@SuppressWarnings("unused")
		net.forestany.forestj.lib.io.File o_fileSimpleClass = o_json.jsonEncode(o_collectionOut, s_fileSimpleClass, true);
	
		Data.SimpleClassCollection o_collectionIn = null;
		java.util.List<Data.SimpleClass> a_dataIn = null;
		
		o_collectionIn = (Data.SimpleClassCollection)o_json.jsonDecode(s_fileSimpleClass);
		a_dataIn = o_collectionIn.SimpleClasses;
		
		i_cnt = 0;
		
		for (Data.SimpleClass o_simpleClassObject : a_dataIn) {
			a_in[i_cnt++] = o_simpleClassObject.toString();
		}
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_collectionOut, o_collectionIn, o_json.getUsePropertyMethods()),
				"output object inner class collection is not equal to input object inner class collection"
				);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut, a_dataIn, o_json.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		for (int i = 0; i < a_dataOut.size(); i++) {
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut.get(i), a_dataIn.get(i), o_json.getUsePropertyMethods(), true),
					"output object inner class array element object is not equal to input object inner class array element object"
					);
			
			assertEquals(
					a_out[i],
					a_in[i],
					"output object inner class array element toString is not equal to input object inner class array element toString"
					);
		}
		
		/* Simple #2 */
		
		a_dataOut = new java.util.ArrayList<Data.SimpleClass>();
		a_dataOut.add(o_data.new SimpleClass("Record #1 Value A #2", "Record #1 Value B", "Record #1 Value C"));
		a_dataOut.add(o_data.new SimpleClass("Record #4 Value A #2", "Record $4 ;Value B \"", null, java.util.Arrays.asList(16, 32, null, 128, 0)));
		a_dataOut.add(o_data.new SimpleClass("Record #3; Value A #2", "null", "Record #3 Value C", java.util.Arrays.asList(9, 8, -7, -6)));
		a_dataOut.add(o_data.new SimpleClass("Record #2 Value A #2", "Record #2 Value B", "", java.util.Arrays.asList(1, 2, -3, -4)));
		
		o_collectionOut = o_data.new SimpleClassCollection(a_dataOut);
		
		a_out = new String[a_dataOut.size()];
		a_in = new String[a_dataOut.size()];
		i_cnt = 0;
		
		for (Data.SimpleClass o_simpleClassObject : a_dataOut) {
			a_out[i_cnt++] = o_simpleClassObject.toString();
		}
		
		s_fileSimpleClass = p_s_testDirectory + "TestJSONSimpleClass_J.json";
		o_fileSimpleClass = o_json.jsonEncode(o_collectionOut, s_fileSimpleClass, true);
	
		o_collectionIn = null;
		a_dataIn = null;
		
		o_collectionIn = (Data.SimpleClassCollection)o_json.jsonDecode(s_fileSimpleClass);
		a_dataIn = o_collectionIn.SimpleClasses;
		
		i_cnt = 0;
		
		for (Data.SimpleClass o_simpleClassObject : a_dataIn) {
			a_in[i_cnt++] = o_simpleClassObject.toString();
		}
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_collectionOut, o_collectionIn, o_json.getUsePropertyMethods()),
				"output object inner class collection is not equal to input object inner class collection"
				);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut, a_dataIn, o_json.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		for (int i = 0; i < a_dataOut.size(); i++) {
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut.get(i), a_dataIn.get(i), o_json.getUsePropertyMethods(), true),
					"output object inner class array element object is not equal to input object inner class array element object"
					);
			
			assertEquals(
					a_out[i],
					a_in[i],
					"output object inner class array element toString is not equal to input object inner class array element toString"
					);
		}
		
		/* Complex #1 */
		
		java.util.List<Data.ShipOrder> a_shipOrdersOut = Data.generateData();
		
		Data.ShipOrderCollection o_shipOrderCollectionOut = o_data.new ShipOrderCollection();
		o_shipOrderCollectionOut.setShipOrders(a_shipOrdersOut);
		o_shipOrderCollectionOut.setOrderAmount(a_shipOrdersOut.size());
		
		a_out = new String[a_shipOrdersOut.size()];
		a_in = new String[a_shipOrdersOut.size()];
		i_cnt = 0;
		
		for (Data.ShipOrder o_shipOrderObject : a_shipOrdersOut) {
			a_out[i_cnt++] = o_shipOrderObject.toString();
	    }
		
		s_jsonSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "json" + net.forestany.forestj.lib.io.File.DIR + "TestJSONSchemaClassRootWithRef.json";
		
		o_json = new net.forestany.forestj.lib.io.JSON(s_jsonSchemaFile);
		o_json.setUsePropertyMethods(true);
		
		String s_file = p_s_testDirectory + "TestJSON_K.json";
		
		@SuppressWarnings("unused")
		net.forestany.forestj.lib.io.File o_file = o_json.jsonEncode(o_shipOrderCollectionOut, s_file, true);
	
		java.util.List<Data.ShipOrder> a_shipOrdersIn = null;
		Data.ShipOrderCollection o_shipOrderCollectionIn = null;
		
		o_shipOrderCollectionIn = (Data.ShipOrderCollection)o_json.jsonDecode(s_file);
		a_shipOrdersIn = o_shipOrderCollectionIn.getShipOrders();
		o_shipOrderCollectionIn.setShipOrders(a_shipOrdersIn);
		o_shipOrderCollectionIn.setOrderAmount(a_shipOrdersIn.size());
	
		i_cnt = 0;
		
		for (Data.ShipOrder o_shipOrderObject : a_shipOrdersIn) {
			a_in[i_cnt++] = o_shipOrderObject.toString();
    	}
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_shipOrderCollectionOut, o_shipOrderCollectionIn, o_json.getUsePropertyMethods()),
				"output object inner class collection is not equal to input object inner class collection"
				);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut, a_shipOrdersIn, o_json.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		for (int i = 0; i < a_shipOrdersIn.size(); i++) {
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut.get(i), a_shipOrdersIn.get(i), o_json.getUsePropertyMethods(), true),
					"output object inner class array element object is not equal to input object inner class array element object"
					);
			
			assertEquals(
					a_out[i],
					a_in[i],
					"output object inner class array element string is not equal to input object inner class array element string"
					);
		}
		
		/* Complex #2 */
		
		a_shipOrdersOut = Data.generateData();
		
		for (Data.ShipOrder o_shipOrderObject : a_shipOrdersOut) {
			o_shipOrderObject.setOrderId(o_shipOrderObject.getOrderId() + " #2");
			
			for (Data.ShipItem o_shipItemObject : o_shipOrderObject.getShipItems()) {
				o_shipItemObject.setNote(o_shipItemObject.getNote() + " #2");
			}
		}
		
		o_shipOrderCollectionOut = o_data.new ShipOrderCollection();
		o_shipOrderCollectionOut.setShipOrders(a_shipOrdersOut);
		o_shipOrderCollectionOut.setOrderAmount(a_shipOrdersOut.size());
		
		a_out = new String[a_shipOrdersOut.size()];
		a_in = new String[a_shipOrdersOut.size()];
		i_cnt = 0;
		
		for (Data.ShipOrder o_shipOrderObject : a_shipOrdersOut) {
			a_out[i_cnt++] = o_shipOrderObject.toString();
	    }
		
		s_file = p_s_testDirectory + "TestJSON_L.json";
		o_file = o_json.jsonEncode(o_shipOrderCollectionOut, s_file, true);
	
		a_shipOrdersIn = null;
		o_shipOrderCollectionIn = null;
		
		o_shipOrderCollectionIn = (Data.ShipOrderCollection)o_json.jsonDecode(s_file);
		a_shipOrdersIn = o_shipOrderCollectionIn.getShipOrders();
		o_shipOrderCollectionIn.setShipOrders(a_shipOrdersIn);
		o_shipOrderCollectionIn.setOrderAmount(a_shipOrdersIn.size());
	
		i_cnt = 0;
		
		for (Data.ShipOrder o_shipOrderObject : a_shipOrdersIn) {
			a_in[i_cnt++] = o_shipOrderObject.toString();
    	}
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_shipOrderCollectionOut, o_shipOrderCollectionIn, o_json.getUsePropertyMethods()),
				"output object inner class collection is not equal to input object inner class collection"
				);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut, a_shipOrdersIn, o_json.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		for (int i = 0; i < a_shipOrdersIn.size(); i++) {
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut.get(i), a_shipOrdersIn.get(i), o_json.getUsePropertyMethods(), true),
					"output object inner class array element object is not equal to input object inner class array element object"
					);
			
			assertEquals(
					a_out[i],
					a_in[i],
					"output object inner class array element string is not equal to input object inner class array element string"
					);
		}
	}
}
