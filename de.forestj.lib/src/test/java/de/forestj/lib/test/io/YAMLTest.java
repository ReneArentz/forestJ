package de.forestj.lib.test.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class YAMLTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testYAML() {
		try {
			de.forestj.lib.LoggingConfig.initiateTestLogging();
			
			String s_currentDirectory = de.forestj.lib.io.File.getCurrentDirectory();
			String s_testDirectory = s_currentDirectory + de.forestj.lib.io.File.DIR + "testYAML" + de.forestj.lib.io.File.DIR;
			
			if ( de.forestj.lib.io.File.folderExists(s_testDirectory) ) {
				de.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			}
			
			de.forestj.lib.io.File.createDirectory(s_testDirectory);
			assertTrue(
					de.forestj.lib.io.File.folderExists(s_testDirectory),
					"directory[" + s_testDirectory + "] does not exist"
			);
			
				yamlValidate();
				yamlVirtualFiles();
				yamlObject(s_testDirectory, "_0");
				yamlClass(s_testDirectory, "TestYAMLSchemaClass.yaml", "_1");
				yamlArray(s_testDirectory, "TestYAMLSchemaSimpleClassArray.yaml", "_A");
				yamlArray(s_testDirectory, "TestYAMLSchemaSimpleClassObjectMultiReferences.yaml", "_B");
				yamlArray(s_testDirectory, "TestYAMLSchemaSimpleClassObjectOneReference.yaml", "_C");
				yamlArray(s_testDirectory, "TestYAMLSchemaSimpleClassNoReferences.yaml", "_D");
				yamlComplex(s_testDirectory, true, false, false, false, "_E");
				yamlComplex(s_testDirectory, false, true, false, false, "_F");
				yamlComplex(s_testDirectory, false, false, true, false, "_G");
				yamlComplex(s_testDirectory, false, false, false, true, "_H");
				yamlMultipleUseOfOneSchemaObject(s_testDirectory);
				
			de.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			assertFalse(
					de.forestj.lib.io.File.folderExists(s_testDirectory),
					"directory[" + s_testDirectory + "] does exist"
			);
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
	
	private static void yamlValidate() throws Exception {
		String s_yamlSchemaFile = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "yaml" + de.forestj.lib.io.File.DIR + "TestYAMLSchemaClassRootWithRef.yaml";
		String s_yamlFile = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "yaml" + de.forestj.lib.io.File.DIR + "ValidateYAML.yaml";
		String s_invalidYamlFile = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "yaml" + de.forestj.lib.io.File.DIR + "ValidateYAMLIsInvalid.yaml";
		
		assertTrue(
				new de.forestj.lib.io.YAML(s_yamlSchemaFile, 4).validateAgainstSchema(s_yamlFile),
				"file 'ValidateYAML.yaml' is not valid with schema 'TestYAMLSchemaClassRootWithRef.yaml'"
				);
		
		boolean b_check = true;
		
		try {
			assertFalse(
					new de.forestj.lib.io.YAML(s_yamlSchemaFile, 4).validateAgainstSchema(s_invalidYamlFile),
					"file 'ValidateYAML.yaml' is valid with schema 'TestYAMLSchemaClassRootWithRef.yaml'"
					);
		} catch (Exception o_exc) {
			b_check = false;
		}
		
		if (b_check) {
			assertTrue(
					false,
					"file 'ValidateYAMLIsInvalid.yaml' is valid with schema 'TestYAMLSchemaClassRootWithRef.yaml'"
			);
		}
	}
	
	private static void yamlVirtualFiles() throws Exception {
		de.forestj.lib.test.sql.AllTypesRecord o_recordOut = new de.forestj.lib.test.sql.AllTypesRecord();
		o_recordOut.ColumnId = 1;
		o_recordOut.ColumnUUID = "ab1824ce-72b8-4aad-bb12-dbc448682437";
		o_recordOut.ColumnShortText = "Datensatz Eins";
		o_recordOut.ColumnText = "Die Handelsstreitigkeiten zwischen den; \"USA und China\" sorgen für eine Art Umdenken auf beiden Seiten. Während US-Unternehmen chinesische Hardware meiden, tun dies chinesische Unternehmen wohl mittlerweile auch: So denken laut einem Bericht der Nachrichtenagentur Bloomberg viele chinesische Hersteller stark darüber nach, ihre IT-Infrastruktur von lokalen Unternehmen statt von den US-Konzernen Oracle und IBM zu kaufen. Für diese Unternehmen sei der asiatische Markt wichtig. 16 respektive mehr als 20 Prozent des Umsatzes stammen aus dieser Region.";
		o_recordOut.ColumnSmallInt = 1;
		o_recordOut.ColumnInt = 10001;
		o_recordOut.ColumnBigInt = 100001111;
		o_recordOut.ColumnLocalDateTime = java.time.LocalDateTime.of(2019, 2, 2, 2, 2, 2);
		
		de.forestj.lib.io.YAML o_yamlEmpty = new de.forestj.lib.io.YAML();
		de.forestj.lib.io.YAML.YAMLElement o_yamlSchema = o_yamlEmpty.new YAMLElement("Root");
		o_yamlSchema.setType("object");
		o_yamlSchema.setMappingClass("de.forestj.lib.test.sql.AllTypesRecord");
		
			de.forestj.lib.io.YAML.YAMLElement o_yamlElement = o_yamlEmpty.new YAMLElement("Id");
			o_yamlElement.setType("integer");
			o_yamlElement.setMappingClass("ColumnId");
			o_yamlElement.setRequired(true);
			o_yamlSchema.getChildren().add(o_yamlElement);
			
			o_yamlElement = o_yamlEmpty.new YAMLElement("UUID");
			o_yamlElement.setType("string");
			o_yamlElement.setMappingClass("ColumnUUID");
			o_yamlElement.setRequired(true);
			o_yamlSchema.getChildren().add(o_yamlElement);
			
			o_yamlElement = o_yamlEmpty.new YAMLElement("ShortText");
			o_yamlElement.setType("string");
			o_yamlElement.setMappingClass("ColumnShortText");
			o_yamlElement.setRequired(true);
			o_yamlSchema.getChildren().add(o_yamlElement);
			
			o_yamlElement = o_yamlEmpty.new YAMLElement("Text");
			o_yamlElement.setType("string");
			o_yamlElement.setMappingClass("ColumnText");
			o_yamlSchema.getChildren().add(o_yamlElement);
			
			o_yamlElement = o_yamlEmpty.new YAMLElement("SmallInt");
			o_yamlElement.setType("integer");
			o_yamlElement.setMappingClass("ColumnSmallInt");
			o_yamlSchema.getChildren().add(o_yamlElement);
			
			o_yamlElement = o_yamlEmpty.new YAMLElement("Int");
			o_yamlElement.setType("integer");
			o_yamlElement.setMappingClass("ColumnInt");
			o_yamlSchema.getChildren().add(o_yamlElement);
			
			o_yamlElement = o_yamlEmpty.new YAMLElement("BigInt");
			o_yamlElement.setType("integer");
			o_yamlElement.setMappingClass("ColumnBigInt");
			o_yamlSchema.getChildren().add(o_yamlElement);
			
			o_yamlElement = o_yamlEmpty.new YAMLElement("LocalDateTime");
			o_yamlElement.setType("string");
			o_yamlElement.setMappingClass("ColumnLocalDateTime");
			o_yamlSchema.getChildren().add(o_yamlElement);
		
		de.forestj.lib.io.YAML o_yaml = new de.forestj.lib.io.YAML(o_yamlSchema, 4);
		
		String s_yamlEncoded = o_yaml.yamlEncode(o_recordOut);
		
		java.util.List<String> a_fileLines = new java.util.ArrayList<String>();

		for (String s_line : s_yamlEncoded.split(System.lineSeparator())) {
			a_fileLines.add(s_line);
		}
		
		de.forestj.lib.test.sql.AllTypesRecord o_recordIn = (de.forestj.lib.test.sql.AllTypesRecord)o_yaml.yamlDecode(a_fileLines);
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(o_recordOut, o_recordIn, o_yaml.getUsePropertyMethods()),
				"output object class is not equal to input object class"
				);
		
		String s_virtualYamlFile = ""
				+ "---" + System.lineSeparator()
				+ "Id: 42" + System.lineSeparator()
				+ "UUID: \"421824ce-72b8-4aad-bb12-dbc448682437\"" + System.lineSeparator()
				+ "ShortText: \"Datensatz 42\"" + System.lineSeparator()
				+ "Text: \"Die Handelsstreitigkeiten zwischen den; \"42 und 42\" sorgen für eine Art Umdenken auf beiden Seiten.\"" + System.lineSeparator()
				+ "SmallInt: -42" + System.lineSeparator()
				+ "Int: 40002" + System.lineSeparator()
				+ "BigInt: 400001112" + System.lineSeparator()
				+ "LocalDateTime: \"2042-02-02T01:02:02Z\"" + System.lineSeparator()
				+ "..." + System.lineSeparator();
		
		o_recordOut = new de.forestj.lib.test.sql.AllTypesRecord();
		o_recordOut.ColumnId = 42;
		o_recordOut.ColumnUUID = "421824ce-72b8-4aad-bb12-dbc448682437";
		o_recordOut.ColumnShortText = "Datensatz 42";
		o_recordOut.ColumnText = "Die Handelsstreitigkeiten zwischen den; \"42 und 42\" sorgen für eine Art Umdenken auf beiden Seiten.";
		o_recordOut.ColumnSmallInt = -42;
		o_recordOut.ColumnInt = 40002;
		o_recordOut.ColumnBigInt = 400001112;
		o_recordOut.ColumnLocalDateTime = java.time.LocalDateTime.of(2042, 2, 2, 2, 2, 2);
		
		a_fileLines = new java.util.ArrayList<String>();

		for (String s_line : s_virtualYamlFile.split(System.lineSeparator())) {
			a_fileLines.add(s_line);
		}
		
		o_recordIn = (de.forestj.lib.test.sql.AllTypesRecord)o_yaml.yamlDecode(a_fileLines);
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(o_recordOut, o_recordIn, o_yaml.getUsePropertyMethods()),
				"output object class is not equal to input object class"
				);
		
	}
	
	private static void yamlObject(String p_s_testDirectory, String p_s_fileNameSuffix) throws Exception {
		de.forestj.lib.test.sql.AllTypesRecord o_recordOut = new de.forestj.lib.test.sql.AllTypesRecord();
		o_recordOut.ColumnId = 1;
		o_recordOut.ColumnUUID = "ab1824ce-72b8-4aad-bb12-dbc448682437";
		o_recordOut.ColumnShortText = "Datensatz Eins";
		o_recordOut.ColumnText = "Die Handelsstreitigkeiten zwischen den; \"USA und China\" sorgen für eine Art Umdenken auf beiden Seiten. Während US-Unternehmen chinesische Hardware meiden, tun dies chinesische Unternehmen wohl mittlerweile auch: So denken laut einem Bericht der Nachrichtenagentur Bloomberg viele chinesische Hersteller stark darüber nach, ihre IT-Infrastruktur von lokalen Unternehmen statt von den US-Konzernen Oracle und IBM zu kaufen. Für diese Unternehmen sei der asiatische Markt wichtig. 16 respektive mehr als 20 Prozent des Umsatzes stammen aus dieser Region.";
		o_recordOut.ColumnSmallInt = 1;
		o_recordOut.ColumnInt = 10001;
		o_recordOut.ColumnBigInt = 100001111;
		o_recordOut.ColumnLocalDateTime = java.time.LocalDateTime.of(2019, 2, 2, 2, 2, 2);
		
		de.forestj.lib.io.YAML o_yamlEmpty = new de.forestj.lib.io.YAML();
		de.forestj.lib.io.YAML.YAMLElement o_yamlSchema = o_yamlEmpty.new YAMLElement("Root");
		o_yamlSchema.setType("object");
		o_yamlSchema.setMappingClass("de.forestj.lib.test.sql.AllTypesRecord");
		
			de.forestj.lib.io.YAML.YAMLElement o_yamlElement = o_yamlEmpty.new YAMLElement("Id");
			o_yamlElement.setType("integer");
			o_yamlElement.setMappingClass("ColumnId");
			o_yamlElement.setRequired(true);
			o_yamlSchema.getChildren().add(o_yamlElement);
			
			o_yamlElement = o_yamlEmpty.new YAMLElement("UUID");
			o_yamlElement.setType("string");
			o_yamlElement.setMappingClass("ColumnUUID");
			o_yamlElement.setRequired(true);
			o_yamlSchema.getChildren().add(o_yamlElement);
			
			o_yamlElement = o_yamlEmpty.new YAMLElement("ShortText");
			o_yamlElement.setType("string");
			o_yamlElement.setMappingClass("ColumnShortText");
			o_yamlElement.setRequired(true);
			o_yamlSchema.getChildren().add(o_yamlElement);
			
			o_yamlElement = o_yamlEmpty.new YAMLElement("Text");
			o_yamlElement.setType("string");
			o_yamlElement.setMappingClass("ColumnText");
			o_yamlSchema.getChildren().add(o_yamlElement);
			
			o_yamlElement = o_yamlEmpty.new YAMLElement("SmallInt");
			o_yamlElement.setType("integer");
			o_yamlElement.setMappingClass("ColumnSmallInt");
			o_yamlSchema.getChildren().add(o_yamlElement);
			
			o_yamlElement = o_yamlEmpty.new YAMLElement("Int");
			o_yamlElement.setType("integer");
			o_yamlElement.setMappingClass("ColumnInt");
			o_yamlSchema.getChildren().add(o_yamlElement);
			
			o_yamlElement = o_yamlEmpty.new YAMLElement("BigInt");
			o_yamlElement.setType("integer");
			o_yamlElement.setMappingClass("ColumnBigInt");
			o_yamlSchema.getChildren().add(o_yamlElement);
			
			o_yamlElement = o_yamlEmpty.new YAMLElement("LocalDateTime");
			o_yamlElement.setType("string");
			o_yamlElement.setMappingClass("ColumnLocalDateTime");
			o_yamlSchema.getChildren().add(o_yamlElement);
		
		de.forestj.lib.io.YAML o_yaml = new de.forestj.lib.io.YAML(o_yamlSchema, 4);
		
		String s_fileSimpleClass = p_s_testDirectory + "TestYAMLObject" + p_s_fileNameSuffix + ".yaml";
		
		@SuppressWarnings("unused")
		de.forestj.lib.io.File o_fileSimpleClass = o_yaml.yamlEncode(o_recordOut, s_fileSimpleClass, true);
		
		de.forestj.lib.test.sql.AllTypesRecord o_recordIn = (de.forestj.lib.test.sql.AllTypesRecord)o_yaml.yamlDecode(s_fileSimpleClass);
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(o_recordOut, o_recordIn, o_yaml.getUsePropertyMethods()),
				"output object class is not equal to input object class"
				);
	}
	
	private static void yamlClass(String p_s_testDirectory, String p_s_yamlSchemaFileName, String p_s_fileNameSuffix) throws Exception {
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
		
		String s_yamlSchemaFile = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "yaml" + de.forestj.lib.io.File.DIR + p_s_yamlSchemaFileName;
		de.forestj.lib.io.YAML o_yaml = new de.forestj.lib.io.YAML(s_yamlSchemaFile, 4);
		
		String s_fileSimpleClass = p_s_testDirectory + "TestYAMLClass" + p_s_fileNameSuffix + ".yaml";
		
		@SuppressWarnings("unused")
		de.forestj.lib.io.File o_fileSimpleClass = o_yaml.yamlEncode(o_recordOut, s_fileSimpleClass, true);
		
		de.forestj.lib.test.sql.AllTypesRecord o_recordIn = (de.forestj.lib.test.sql.AllTypesRecord)o_yaml.yamlDecode(s_fileSimpleClass);
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(o_recordOut, o_recordIn, o_yaml.getUsePropertyMethods()),
				"output object class is not equal to input object class"
				);
	}
	
	private static void yamlArray(String p_s_testDirectory, String p_s_yamlSchemaFileName, String p_s_fileNameSuffix) throws Exception {
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
		
		String s_yamlSchemaFile = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "yaml" + de.forestj.lib.io.File.DIR + p_s_yamlSchemaFileName;
		de.forestj.lib.io.YAML o_yaml = new de.forestj.lib.io.YAML(s_yamlSchemaFile, 4);
		
		String s_fileSimpleClass = p_s_testDirectory + "TestYAMLSimpleClass" + p_s_fileNameSuffix + ".yaml";
		
		if (p_s_yamlSchemaFileName.contentEquals("TestYAMLSchemaSimpleClassArray.yaml")) {
			@SuppressWarnings("unused")
			de.forestj.lib.io.File o_fileSimpleClass = o_yaml.yamlEncode(a_dataOut, s_fileSimpleClass, true);
		} else {
			@SuppressWarnings("unused")
			de.forestj.lib.io.File o_fileSimpleClass = o_yaml.yamlEncode(o_collectionOut, s_fileSimpleClass, true);
		}
		
		Data.SimpleClassCollection o_collectionIn = null;
		java.util.List<Data.SimpleClass> a_dataIn = null;
		
		if (p_s_yamlSchemaFileName.contentEquals("TestYAMLSchemaSimpleClassArray.yaml")) {
			@SuppressWarnings("unchecked")
			java.util.List<Data.SimpleClass> a_foo = (java.util.List<Data.SimpleClass>)o_yaml.yamlDecode(s_fileSimpleClass);
			a_dataIn = a_foo;
		} else {
			o_collectionIn = (Data.SimpleClassCollection)o_yaml.yamlDecode(s_fileSimpleClass);
			a_dataIn = o_collectionIn.SimpleClasses;
		}
		
		i_cnt = 0;
		
		for (Data.SimpleClass o_simpleClassObject : a_dataIn) {
			a_in[i_cnt++] = o_simpleClassObject.toString();
		}
		
		if (!p_s_yamlSchemaFileName.contentEquals("TestYAMLSchemaSimpleClassArray.yaml")) {
			assertTrue(
					de.forestj.lib.Helper.objectsEqualUsingReflections(o_collectionOut, o_collectionIn, o_yaml.getUsePropertyMethods()),
					"output object inner class collection is not equal to input object inner class collection"
					);
		}
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut, a_dataIn, o_yaml.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		for (int i = 0; i < a_dataOut.size(); i++) {
			assertTrue(
					de.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut.get(i), a_dataIn.get(i), o_yaml.getUsePropertyMethods(), true),
					"output object inner class array element object is not equal to input object inner class array element object"
					);
			
			assertEquals(
					a_out[i],
					a_in[i],
					"output object inner class array element toString is not equal to input object inner class array element toString"
					);
		}
	}

	private static void yamlComplex(String p_s_testDirectory, boolean p_b_classRoot, boolean p_b_listRoot, boolean p_b_classRootWithRef, boolean p_b_listRootWithRef, String p_s_fileNameSuffix) throws Exception {
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
		
		String s_yamlSchemaFile = null;
		
		if (p_b_classRoot) {
			s_yamlSchemaFile = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "yaml" + de.forestj.lib.io.File.DIR + "TestYAMLSchemaClassRoot.yaml";
		} else if (p_b_listRoot) {
			s_yamlSchemaFile = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "yaml" + de.forestj.lib.io.File.DIR + "TestYAMLSchemaListRoot.yaml";
		} else if (p_b_classRootWithRef) {
			s_yamlSchemaFile = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "yaml" + de.forestj.lib.io.File.DIR + "TestYAMLSchemaClassRootWithRef.yaml";
		} else if (p_b_listRootWithRef) {
			s_yamlSchemaFile = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "yaml" + de.forestj.lib.io.File.DIR + "TestYAMLSchemaListRootWithRef.yaml";
		}
		
		de.forestj.lib.io.YAML o_yaml = new de.forestj.lib.io.YAML(s_yamlSchemaFile, 4);
		o_yaml.setUsePropertyMethods(true);
		
		String s_file = p_s_testDirectory + "TestYAML" + p_s_fileNameSuffix + ".yaml";
		
		if ( (p_b_classRoot) || (p_b_classRootWithRef) ) {
			@SuppressWarnings("unused")
			de.forestj.lib.io.File o_file = o_yaml.yamlEncode(o_shipOrderCollectionOut, s_file, true);
		} else {
			@SuppressWarnings("unused")
			de.forestj.lib.io.File o_file = o_yaml.yamlEncode(a_shipOrdersOut, s_file, true);
		}
		
		java.util.List<Data.ShipOrder> a_shipOrdersIn = null;
		Data.ShipOrderCollection o_shipOrderCollectionIn = null;
		
		if ( (p_b_classRoot) || (p_b_classRootWithRef) ) {
			o_shipOrderCollectionIn = (Data.ShipOrderCollection)o_yaml.yamlDecode(s_file);
			a_shipOrdersIn = o_shipOrderCollectionIn.getShipOrders();
			o_shipOrderCollectionIn.setShipOrders(a_shipOrdersIn);
			o_shipOrderCollectionIn.setOrderAmount(a_shipOrdersIn.size());
		} else {
			@SuppressWarnings("unchecked")
			java.util.List<Data.ShipOrder> a_foo = (java.util.List<Data.ShipOrder>)o_yaml.yamlDecode(s_file);
			a_shipOrdersIn = a_foo;
		}
		
		i_cnt = 0;
		
		for (Data.ShipOrder o_shipOrderObject : a_shipOrdersIn) {
			a_in[i_cnt++] = o_shipOrderObject.toString();
    	}
		
		if ( (p_b_classRoot) || (p_b_classRootWithRef) ) {
			assertTrue(
					de.forestj.lib.Helper.objectsEqualUsingReflections(o_shipOrderCollectionOut, o_shipOrderCollectionIn, o_yaml.getUsePropertyMethods()),
					"output object inner class collection is not equal to input object inner class collection"
					);
		}
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut, a_shipOrdersIn, o_yaml.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		for (int i = 0; i < a_shipOrdersIn.size(); i++) {
			assertTrue(
					de.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut.get(i), a_shipOrdersIn.get(i), o_yaml.getUsePropertyMethods(), true),
					"output object inner class array element object is not equal to input object inner class array element object"
					);
			
			assertEquals(
					a_out[i],
					a_in[i],
					"output object inner class array element string is not equal to input object inner class array element string"
					);
		}
		
		if (p_b_listRootWithRef) {
			s_file = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "yaml" + de.forestj.lib.io.File.DIR + "TestYAMLPrimitiveArrayOneLine.yaml";
			a_in = new String[a_shipOrdersOut.size()];
			a_shipOrdersIn = null;
			o_shipOrderCollectionIn = null;
			
			@SuppressWarnings("unchecked")
			java.util.List<Data.ShipOrder> a_foo = (java.util.List<Data.ShipOrder>)o_yaml.yamlDecode(s_file);
			a_shipOrdersIn = a_foo;
			
			i_cnt = 0;
			
			for (Data.ShipOrder o_shipOrderObject : a_shipOrdersIn) {
				a_in[i_cnt++] = o_shipOrderObject.toString();
	    	}
			
			assertTrue(
					de.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut, a_shipOrdersIn, o_yaml.getUsePropertyMethods()),
					"output object inner class array is not equal to input object inner class array"
					);
			
			for (int i = 0; i < a_shipOrdersIn.size(); i++) {
				assertTrue(
						de.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut.get(i), a_shipOrdersIn.get(i), o_yaml.getUsePropertyMethods(), true),
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

	private static void yamlMultipleUseOfOneSchemaObject(String p_s_testDirectory) throws Exception {
		
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
		
		String s_yamlSchemaFile = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "yaml" + de.forestj.lib.io.File.DIR + "TestYAMLSchemaSimpleClassObjectMultiReferences.yaml";
		de.forestj.lib.io.YAML o_yaml = new de.forestj.lib.io.YAML(s_yamlSchemaFile, 4);
		
		String s_fileSimpleClass = p_s_testDirectory + "TestYAMLSimpleClass_I.yaml";
		
		@SuppressWarnings("unused")
		de.forestj.lib.io.File o_fileSimpleClass = o_yaml.yamlEncode(o_collectionOut, s_fileSimpleClass, true);
	
		Data.SimpleClassCollection o_collectionIn = null;
		java.util.List<Data.SimpleClass> a_dataIn = null;
		
		o_collectionIn = (Data.SimpleClassCollection)o_yaml.yamlDecode(s_fileSimpleClass);
		a_dataIn = o_collectionIn.SimpleClasses;
		
		i_cnt = 0;
		
		for (Data.SimpleClass o_simpleClassObject : a_dataIn) {
			a_in[i_cnt++] = o_simpleClassObject.toString();
		}
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(o_collectionOut, o_collectionIn, o_yaml.getUsePropertyMethods()),
				"output object inner class collection is not equal to input object inner class collection"
				);
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut, a_dataIn, o_yaml.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		for (int i = 0; i < a_dataOut.size(); i++) {
			assertTrue(
					de.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut.get(i), a_dataIn.get(i), o_yaml.getUsePropertyMethods(), true),
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
		
		s_fileSimpleClass = p_s_testDirectory + "TestYAMLSimpleClass_J.yaml";
		o_fileSimpleClass = o_yaml.yamlEncode(o_collectionOut, s_fileSimpleClass, true);
	
		o_collectionIn = null;
		a_dataIn = null;
		
		o_collectionIn = (Data.SimpleClassCollection)o_yaml.yamlDecode(s_fileSimpleClass);
		a_dataIn = o_collectionIn.SimpleClasses;
		
		i_cnt = 0;
		
		for (Data.SimpleClass o_simpleClassObject : a_dataIn) {
			a_in[i_cnt++] = o_simpleClassObject.toString();
		}
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(o_collectionOut, o_collectionIn, o_yaml.getUsePropertyMethods()),
				"output object inner class collection is not equal to input object inner class collection"
				);
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut, a_dataIn, o_yaml.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		for (int i = 0; i < a_dataOut.size(); i++) {
			assertTrue(
					de.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut.get(i), a_dataIn.get(i), o_yaml.getUsePropertyMethods(), true),
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
		
		s_yamlSchemaFile = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "yaml" + de.forestj.lib.io.File.DIR + "TestYAMLSchemaClassRootWithRef.yaml";
		
		o_yaml = new de.forestj.lib.io.YAML(s_yamlSchemaFile, 4);
		o_yaml.setUsePropertyMethods(true);
		
		String s_file = p_s_testDirectory + "TestYAML_K.yaml";
		
		@SuppressWarnings("unused")
		de.forestj.lib.io.File o_file = o_yaml.yamlEncode(o_shipOrderCollectionOut, s_file, true);
	
		java.util.List<Data.ShipOrder> a_shipOrdersIn = null;
		Data.ShipOrderCollection o_shipOrderCollectionIn = null;
		
		o_shipOrderCollectionIn = (Data.ShipOrderCollection)o_yaml.yamlDecode(s_file);
		a_shipOrdersIn = o_shipOrderCollectionIn.getShipOrders();
		o_shipOrderCollectionIn.setShipOrders(a_shipOrdersIn);
		o_shipOrderCollectionIn.setOrderAmount(a_shipOrdersIn.size());
	
		i_cnt = 0;
		
		for (Data.ShipOrder o_shipOrderObject : a_shipOrdersIn) {
			a_in[i_cnt++] = o_shipOrderObject.toString();
    	}
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(o_shipOrderCollectionOut, o_shipOrderCollectionIn, o_yaml.getUsePropertyMethods()),
				"output object inner class collection is not equal to input object inner class collection"
				);
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut, a_shipOrdersIn, o_yaml.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		for (int i = 0; i < a_shipOrdersIn.size(); i++) {
			assertTrue(
					de.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut.get(i), a_shipOrdersIn.get(i), o_yaml.getUsePropertyMethods(), true),
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
		
		s_file = p_s_testDirectory + "TestYAML_L.yaml";
		o_file = o_yaml.yamlEncode(o_shipOrderCollectionOut, s_file, true);
	
		a_shipOrdersIn = null;
		o_shipOrderCollectionIn = null;
		
		o_shipOrderCollectionIn = (Data.ShipOrderCollection)o_yaml.yamlDecode(s_file);
		a_shipOrdersIn = o_shipOrderCollectionIn.getShipOrders();
		o_shipOrderCollectionIn.setShipOrders(a_shipOrdersIn);
		o_shipOrderCollectionIn.setOrderAmount(a_shipOrdersIn.size());
	
		i_cnt = 0;
		
		for (Data.ShipOrder o_shipOrderObject : a_shipOrdersIn) {
			a_in[i_cnt++] = o_shipOrderObject.toString();
    	}
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(o_shipOrderCollectionOut, o_shipOrderCollectionIn, o_yaml.getUsePropertyMethods()),
				"output object inner class collection is not equal to input object inner class collection"
				);
		
		assertTrue(
				de.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut, a_shipOrdersIn, o_yaml.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		for (int i = 0; i < a_shipOrdersIn.size(); i++) {
			assertTrue(
					de.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut.get(i), a_shipOrdersIn.get(i), o_yaml.getUsePropertyMethods(), true),
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
