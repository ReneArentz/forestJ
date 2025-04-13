package net.forestany.forestj.lib.test.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class XMLTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testXML() {
		try {
			net.forestany.forestj.lib.LoggingConfig.initiateTestLogging();
			
			String s_currentDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory();
			String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testXML" + net.forestany.forestj.lib.io.File.DIR;
			
			if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
				net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			}
			
			net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
			assertTrue(
					net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
					"directory[" + s_testDirectory + "] does not exist"
			);
				
				xmlValidate();
				xmlVirtualFiles();
				xmlObject(s_testDirectory, "_0");
				xmlClass(s_testDirectory, "TestXSDSchemaClass.xsd", "_1");
				xmlArray(s_testDirectory, "TestXSDSchemaSimpleClassArray.xsd", "_A");
				xmlArray(s_testDirectory, "TestXSDSchemaSimpleClassObjectMultiReferences.xsd", "_B");
				xmlArray(s_testDirectory, "TestXSDSchemaSimpleClassObjectOneReference.xsd", "_C");
				xmlArray(s_testDirectory, "TestXSDSchemaSimpleClassNoReferences.xsd", "_D");
				xmlComplex(s_testDirectory, true, false, false, false, false, false, false, "_E", true);
				xmlComplex(s_testDirectory, false, true, false, false, false, false, false, "_F", true);
				xmlComplex(s_testDirectory, false, false, true, false, false, false, false, "_G", true);
				xmlComplex(s_testDirectory, false, false, false, true, false, false, false, "_H", true);
				xmlComplex(s_testDirectory, false, false, false, false, true, false, false, "_I", true);
				xmlComplex(s_testDirectory, false, false, false, false, false, true, false, "_J", true);
				xmlComplex(s_testDirectory, false, false, false, false, false, false, true, "_K", true);
				xmlComplex(s_testDirectory, true, false, false, false, false, false, false, "_L", false);
				xmlComplex(s_testDirectory, false, true, false, false, false, false, false, "_M", false);
				xmlComplex(s_testDirectory, false, false, true, false, false, false, false, "_N", false);
				xmlComplex(s_testDirectory, false, false, false, true, false, false, false, "_O", false);
				xmlComplex(s_testDirectory, false, false, false, false, true, false, false, "_P", false);
				xmlComplex(s_testDirectory, false, false, false, false, false, true, false, "_Q", false);
				xmlComplex(s_testDirectory, false, false, false, false, false, false, true, "_R", false);
				xmlComplexPart(s_testDirectory, true, false, false, "_S", false);
				xmlComplexPart(s_testDirectory, false, true, false, "_T", false);
				xmlComplexPart(s_testDirectory, false, false, true, "_U", false);
				xmlComplexPart(s_testDirectory, true, false, false, "_V", true);
				xmlComplexPart(s_testDirectory, false, true, false, "_W", true);
				xmlComplexPart(s_testDirectory, false, false, true, "_X", true);
				xmlMultipleUseOfOneSchemaObject(s_testDirectory);
				
			net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			assertFalse(
					net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
					"directory[" + s_testDirectory + "] does exist"
			);
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
	
	private static void xmlValidate() throws Exception {
		String s_xsdSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "xml" + net.forestany.forestj.lib.io.File.DIR + "TestXSDSchemaDividedClassRoot.xsd";
		String s_xmlFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "xml" + net.forestany.forestj.lib.io.File.DIR + "ValidateXML.xml";
		String s_invalidXmlFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "xml" + net.forestany.forestj.lib.io.File.DIR + "ValidateXMLIsInvalid.xml";
		String s_partXmlFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "xml" + net.forestany.forestj.lib.io.File.DIR + "ValidateXMLPart.xml";
		
		assertTrue(
				new net.forestany.forestj.lib.io.XML(s_xsdSchemaFile).validateAgainstSchema(s_xmlFile),
				"file 'ValidateXML.xml' is not valid with schema 'TestXSDSchemaDividedClassRoot.xsd'"
				);
		
		boolean b_check = true;
		
		try {
			assertFalse(
					new net.forestany.forestj.lib.io.XML(s_xsdSchemaFile).validateAgainstSchema(s_invalidXmlFile),
					"file 'ValidateXMLsInvalid.xml' is valid with schema 'TestXSDSchemaDividedClassRoot.xsd'"
					);
		} catch (Exception o_exc) {
			b_check = false;
		}
		
		if (b_check) {
			assertTrue(
					false,
					"file 'ValidateXMLIsInvalid.xml' is valid with schema 'TestXSDSchemaDividedClassRoot.xsd'"
			);
		}
		
		assertTrue(
				new net.forestany.forestj.lib.io.XML(s_xsdSchemaFile).validateAgainstSchema(s_partXmlFile),
				"file 'ValidateXMLPart.xml' is not valid with schema 'TestXSDSchemaDividedClassRoot.xsd'"
				);
	}
	
	private static void xmlVirtualFiles() throws Exception {
		net.forestany.forestj.lib.test.sql.AllTypesRecord o_recordOut = new net.forestany.forestj.lib.test.sql.AllTypesRecord();
		o_recordOut.ColumnId = 1;
		o_recordOut.ColumnUUID = "ab1824ce-72b8-4aad-bb12-dbc448682437";
		o_recordOut.ColumnShortText = "Datensatz Eins";
		o_recordOut.ColumnText = "Die Handelsstreitigkeiten zwischen den; \"USA und China\" sorgen für eine Art Umdenken auf beiden Seiten. Während US-Unternehmen chinesische Hardware meiden, tun dies chinesische Unternehmen wohl mittlerweile auch: So denken laut einem Bericht der Nachrichtenagentur Bloomberg viele chinesische Hersteller stark darüber nach, ihre IT-Infrastruktur von lokalen Unternehmen statt von den US-Konzernen Oracle und IBM zu kaufen. Für diese Unternehmen sei der asiatische Markt wichtig. 16 respektive mehr als 20 Prozent des Umsatzes stammen aus dieser Region.";
		o_recordOut.ColumnSmallInt = 1;
		o_recordOut.ColumnInt = 10001;
		o_recordOut.ColumnBigInt = 100001111;
		o_recordOut.ColumnLocalDateTime = java.time.LocalDateTime.of(2019, 2, 2, 2, 2, 2);
		o_recordOut.ColumnLocalDate = java.time.LocalDate.of(2022, 2, 2);
		o_recordOut.ColumnLocalTime = java.time.LocalTime.of(12, 20, 22);
		o_recordOut.ColumnTimestamp = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("02.02.2020 22:20:12");
		o_recordOut.ColumnDate = new java.text.SimpleDateFormat("dd.MM.yyyy").parse("02.02.2020");
		o_recordOut.ColumnTime = new java.text.SimpleDateFormat("HH:mm:ss").parse("22:20:12");
		
		net.forestany.forestj.lib.io.XML o_xmlEmpty = new net.forestany.forestj.lib.io.XML();
		net.forestany.forestj.lib.io.XML.XSDElement o_xsdSchema = o_xmlEmpty.new XSDElement("AllTypesRecord");
		o_xsdSchema.setType("object");
		o_xsdSchema.setMapping("net.forestany.forestj.lib.test.sql.AllTypesRecord");
		
			net.forestany.forestj.lib.io.XML.XSDElement o_xsdElement = o_xmlEmpty.new XSDElement("Id");
			o_xsdElement.setType("integer");
			o_xsdElement.setMapping("ColumnId");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("UUID");
			o_xsdElement.setType("string");
			o_xsdElement.setMapping("ColumnUUID");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("ShortText");
			o_xsdElement.setType("string");
			o_xsdElement.setMapping("ColumnShortText");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("Text");
			o_xsdElement.setType("string");
			o_xsdElement.setMapping("ColumnText");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("SmallInt");
			o_xsdElement.setType("short");
			o_xsdElement.setMapping("ColumnSmallInt");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("Int");
			o_xsdElement.setType("integer");
			o_xsdElement.setMapping("ColumnInt");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("BigInt");
			o_xsdElement.setType("long");
			o_xsdElement.setMapping("ColumnBigInt");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("LocalDateTime");
			o_xsdElement.setType("datetime");
			o_xsdElement.setMapping("ColumnLocalDateTime");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("LocalDate");
			o_xsdElement.setType("date");
			o_xsdElement.setMapping("ColumnLocalDate");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("LocalTime");
			o_xsdElement.setType("time");
			o_xsdElement.setMapping("ColumnLocalTime");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("Timestamp");
			o_xsdElement.setType("datetime");
			o_xsdElement.setMapping("ColumnTimestamp");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("Date");
			o_xsdElement.setType("date");
			o_xsdElement.setMapping("ColumnDate");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("Time");
			o_xsdElement.setType("time");
			o_xsdElement.setMapping("ColumnTime");
			o_xsdSchema.getChildren().add(o_xsdElement);
		
		net.forestany.forestj.lib.io.XML o_xml = new net.forestany.forestj.lib.io.XML(o_xsdSchema);
		String s_xmlEncoded = o_xml.xmlEncode(o_recordOut);
		java.util.List<String> a_fileLines = new java.util.ArrayList<String>();

		for (String s_line : s_xmlEncoded.split(System.lineSeparator())) {
			a_fileLines.add(s_line);
		}
		
		net.forestany.forestj.lib.test.sql.AllTypesRecord o_recordIn = (net.forestany.forestj.lib.test.sql.AllTypesRecord)o_xml.xmlDecode(a_fileLines);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_recordOut, o_recordIn, o_xml.getUsePropertyMethods()),
				"output object class is not equal to input object class"
				);
		
		o_xml.setUseISO8601UTC(false);
		s_xmlEncoded = o_xml.xmlEncode(o_recordOut);
		a_fileLines = new java.util.ArrayList<String>();

		for (String s_line : s_xmlEncoded.split(System.lineSeparator())) {
			a_fileLines.add(s_line);
		}
		
		o_recordIn = (net.forestany.forestj.lib.test.sql.AllTypesRecord)o_xml.xmlDecode(a_fileLines);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_recordOut, o_recordIn, o_xml.getUsePropertyMethods()),
				"output object class is not equal to input object class"
				);

		String s_virtualXmlFile = ""
			+ "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + System.lineSeparator()
			+ "<AllTypesRecord>" + System.lineSeparator()
			+ "	<Id>42</Id>" + System.lineSeparator()
			+ "	<UUID>421824ce-72b8-4aad-bb12-dbc448682437</UUID>" + System.lineSeparator()
			+ "	<ShortText>Datensatz 42</ShortText>" + System.lineSeparator()
			+ "	<Text>Die Handelsstreitigkeiten zwischen den; \"42 und 42\" sorgen für eine Art Umdenken auf beiden Seiten.</Text>" + System.lineSeparator()
			+ "	<SmallInt>-42</SmallInt>" + System.lineSeparator()
			+ "	<Int>40002</Int>" + System.lineSeparator()
			+ "	<BigInt>400001112</BigInt>" + System.lineSeparator()
			+ "	<LocalDateTime>02.02.2042 02:02:02</LocalDateTime>" + System.lineSeparator()
			+ "	<LocalDate>02.02.2042</LocalDate>" + System.lineSeparator()
			+ "	<LocalTime>12:20:42</LocalTime>" + System.lineSeparator()
			+ "	<Timestamp>02.02.2042 22:20:12</Timestamp>" + System.lineSeparator()
			+ "	<Date>02.02.2042</Date>" + System.lineSeparator()
			+ "	<Time>22:20:42</Time>" + System.lineSeparator()
			+ "</AllTypesRecord>";
		
		o_recordOut = new net.forestany.forestj.lib.test.sql.AllTypesRecord();
		o_recordOut.ColumnId = 42;
		o_recordOut.ColumnUUID = "421824ce-72b8-4aad-bb12-dbc448682437";
		o_recordOut.ColumnShortText = "Datensatz 42";
		o_recordOut.ColumnText = "Die Handelsstreitigkeiten zwischen den; \"42 und 42\" sorgen für eine Art Umdenken auf beiden Seiten.";
		o_recordOut.ColumnSmallInt = -42;
		o_recordOut.ColumnInt = 40002;
		o_recordOut.ColumnBigInt = 400001112;
		o_recordOut.ColumnLocalDateTime = java.time.LocalDateTime.of(2042, 2, 2, 2, 2, 2);
		o_recordOut.ColumnLocalDate = java.time.LocalDate.of(2042, 2, 2);
		o_recordOut.ColumnLocalTime = java.time.LocalTime.of(12, 20, 42);
		o_recordOut.ColumnTimestamp = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("02.02.2042 22:20:12");
		o_recordOut.ColumnDate = new java.text.SimpleDateFormat("dd.MM.yyyy").parse("02.02.2042");
		o_recordOut.ColumnTime = new java.text.SimpleDateFormat("HH:mm:ss").parse("22:20:42");
		
		a_fileLines = new java.util.ArrayList<String>();

		for (String s_line : s_virtualXmlFile.split(System.lineSeparator())) {
			a_fileLines.add(s_line);
		}
		
		o_recordIn = (net.forestany.forestj.lib.test.sql.AllTypesRecord)o_xml.xmlDecode(a_fileLines);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_recordOut, o_recordIn, o_xml.getUsePropertyMethods()),
				"output object class is not equal to input object class"
				);
	}
	
	private static void xmlObject(String p_s_testDirectory, String p_s_fileNameSuffix) throws Exception {
		net.forestany.forestj.lib.test.sql.AllTypesRecord o_recordOut = new net.forestany.forestj.lib.test.sql.AllTypesRecord();
		o_recordOut.ColumnId = 1;
		o_recordOut.ColumnUUID = "ab1824ce-72b8-4aad-bb12-dbc448682437";
		o_recordOut.ColumnShortText = "Datensatz Eins";
		o_recordOut.ColumnText = "Die Handelsstreitigkeiten zwischen den; \"USA und China\" sorgen für eine Art Umdenken auf beiden Seiten. Während US-Unternehmen chinesische Hardware meiden, tun dies chinesische Unternehmen wohl mittlerweile auch: So denken laut einem Bericht der Nachrichtenagentur Bloomberg viele chinesische Hersteller stark darüber nach, ihre IT-Infrastruktur von lokalen Unternehmen statt von den US-Konzernen Oracle und IBM zu kaufen. Für diese Unternehmen sei der asiatische Markt wichtig. 16 respektive mehr als 20 Prozent des Umsatzes stammen aus dieser Region.";
		o_recordOut.ColumnSmallInt = 1;
		o_recordOut.ColumnInt = 10001;
		o_recordOut.ColumnBigInt = 100001111;
		o_recordOut.ColumnLocalDateTime = java.time.LocalDateTime.of(2019, 2, 2, 2, 2, 2);
		o_recordOut.ColumnLocalDate = java.time.LocalDate.of(2022, 2, 2);
		o_recordOut.ColumnLocalTime = java.time.LocalTime.of(12, 20, 22);
		o_recordOut.ColumnTimestamp = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("02.02.2020 22:20:12");
		o_recordOut.ColumnDate = new java.text.SimpleDateFormat("dd.MM.yyyy").parse("02.02.2020");
		o_recordOut.ColumnTime = new java.text.SimpleDateFormat("HH:mm:ss").parse("22:20:12");
		
		net.forestany.forestj.lib.io.XML o_xmlEmpty = new net.forestany.forestj.lib.io.XML();
		net.forestany.forestj.lib.io.XML.XSDElement o_xsdSchema = o_xmlEmpty.new XSDElement("AllTypesRecord");
		o_xsdSchema.setType("object");
		o_xsdSchema.setMapping("net.forestany.forestj.lib.test.sql.AllTypesRecord");
		
			net.forestany.forestj.lib.io.XML.XSDElement o_xsdElement = o_xmlEmpty.new XSDElement("Id");
			o_xsdElement.setType("integer");
			o_xsdElement.setMapping("ColumnId");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("UUID");
			o_xsdElement.setType("string");
			o_xsdElement.setMapping("ColumnUUID");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("ShortText");
			o_xsdElement.setType("string");
			o_xsdElement.setMapping("ColumnShortText");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("Text");
			o_xsdElement.setType("string");
			o_xsdElement.setMapping("ColumnText");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("SmallInt");
			o_xsdElement.setType("short");
			o_xsdElement.setMapping("ColumnSmallInt");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("Int");
			o_xsdElement.setType("integer");
			o_xsdElement.setMapping("ColumnInt");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("BigInt");
			o_xsdElement.setType("long");
			o_xsdElement.setMapping("ColumnBigInt");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("LocalDateTime");
			o_xsdElement.setType("datetime");
			o_xsdElement.setMapping("ColumnLocalDateTime");
			o_xsdSchema.getChildren().add(o_xsdElement);
		
			o_xsdElement = o_xmlEmpty.new XSDElement("LocalDate");
			o_xsdElement.setType("date");
			o_xsdElement.setMapping("ColumnLocalDate");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("LocalTime");
			o_xsdElement.setType("time");
			o_xsdElement.setMapping("ColumnLocalTime");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("Timestamp");
			o_xsdElement.setType("datetime");
			o_xsdElement.setMapping("ColumnTimestamp");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("Date");
			o_xsdElement.setType("date");
			o_xsdElement.setMapping("ColumnDate");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
			o_xsdElement = o_xmlEmpty.new XSDElement("Time");
			o_xsdElement.setType("time");
			o_xsdElement.setMapping("ColumnTime");
			o_xsdSchema.getChildren().add(o_xsdElement);
			
		net.forestany.forestj.lib.io.XML o_xml = new net.forestany.forestj.lib.io.XML(o_xsdSchema);
		
		String s_fileSimpleClass = p_s_testDirectory + "TestXMLObject" + p_s_fileNameSuffix + ".xml";
		
		@SuppressWarnings("unused")
		net.forestany.forestj.lib.io.File o_fileSimpleClass = o_xml.xmlEncode(o_recordOut, s_fileSimpleClass, true);
		
		net.forestany.forestj.lib.test.sql.AllTypesRecord o_recordIn = (net.forestany.forestj.lib.test.sql.AllTypesRecord)o_xml.xmlDecode(s_fileSimpleClass);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_recordOut, o_recordIn, o_xml.getUsePropertyMethods()),
				"output object class is not equal to input object class"
				);
		
		o_xml.setPrintEmptyString(false);
		o_xml.setUseISO8601UTC(false);
		
		o_fileSimpleClass = o_xml.xmlEncode(o_recordOut, s_fileSimpleClass, true);
		o_recordIn = (net.forestany.forestj.lib.test.sql.AllTypesRecord)o_xml.xmlDecode(s_fileSimpleClass);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_recordOut, o_recordIn, o_xml.getUsePropertyMethods()),
				"output object class is not equal to input object class"
				);
	}
	
	private static void xmlClass(String p_s_testDirectory, String p_s_xsdSchemaFileName, String p_s_fileNameSuffix) throws Exception {
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
		
		String s_xsdSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "xml" + net.forestany.forestj.lib.io.File.DIR + p_s_xsdSchemaFileName;
		net.forestany.forestj.lib.io.XML o_xml = new net.forestany.forestj.lib.io.XML(s_xsdSchemaFile);
		
		String s_fileSimpleClass = p_s_testDirectory + "TestXMLClass" + p_s_fileNameSuffix + ".xml";
		
		@SuppressWarnings("unused")
		net.forestany.forestj.lib.io.File o_fileSimpleClass = o_xml.xmlEncode(o_recordOut, s_fileSimpleClass, true);
		
		net.forestany.forestj.lib.test.sql.AllTypesRecord o_recordIn = (net.forestany.forestj.lib.test.sql.AllTypesRecord)o_xml.xmlDecode(s_fileSimpleClass);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_recordOut, o_recordIn, o_xml.getUsePropertyMethods()),
				"output object class is not equal to input object class"
				);
		
		o_xml.setUseISO8601UTC(false);
		s_fileSimpleClass = p_s_testDirectory + "TestXMLClass" + p_s_fileNameSuffix + ".xml";
		o_fileSimpleClass = o_xml.xmlEncode(o_recordOut, s_fileSimpleClass, true);
		o_recordIn = (net.forestany.forestj.lib.test.sql.AllTypesRecord)o_xml.xmlDecode(s_fileSimpleClass);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_recordOut, o_recordIn, o_xml.getUsePropertyMethods()),
				"output object class is not equal to input object class"
				);
	}
	
	private static void xmlArray(String p_s_testDirectory, String p_s_xsdSchemaFileName, String p_s_fileNameSuffix) throws Exception {
		Data o_data = new Data();
		java.util.List<Data.SimpleClass> a_dataOut = new java.util.ArrayList<Data.SimpleClass>();
		a_dataOut.add(o_data.new SimpleClass("Record #1 Value A", "Record #1 Value B", "Record #1 Value C"));
		a_dataOut.add(o_data.new SimpleClass("Record #2 Value A", "Record #2 Value B", "", java.util.Arrays.asList(1, 2, -3, -4)));
		a_dataOut.add(o_data.new SimpleClass("Record #3; Value A", "null", "Record #3 Value C", java.util.Arrays.asList(9, 8, -7, -6), new float[] {42.0f, 21.25f, 54987.456999f}));
		a_dataOut.add(o_data.new SimpleClass("Record #4 Value A", "Record $4 ;Value B \"", null, java.util.Arrays.asList(16, 32, null, 128, 0), new float[] {21.0f, 10.625f}));
		
		Data.SimpleClassCollection o_collectionOut = o_data.new SimpleClassCollection(a_dataOut);
		
		String[] a_out = new String[a_dataOut.size()];
		String[] a_in = new String[a_dataOut.size()];
		int i_cnt = 0;
		
		for (Data.SimpleClass o_simpleClassObject : a_dataOut) {
			a_out[i_cnt++] = o_simpleClassObject.toString();
		}
		
		String s_xsdSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "xml" + net.forestany.forestj.lib.io.File.DIR + p_s_xsdSchemaFileName;
		net.forestany.forestj.lib.io.XML o_xml = new net.forestany.forestj.lib.io.XML(s_xsdSchemaFile);
		
		if (p_s_xsdSchemaFileName.contentEquals("TestXSDSchemaSimpleClassObjectMultiReferences.xsd")) {
			o_xml.setPrintEmptyString(false);
		}
		
		String s_fileSimpleClass = p_s_testDirectory + "TestXMLSimpleClass" + p_s_fileNameSuffix + ".xml";
		
		if (p_s_xsdSchemaFileName.contentEquals("TestXSDSchemaSimpleClassArray.xsd")) {
			@SuppressWarnings("unused")
			net.forestany.forestj.lib.io.File o_fileSimpleClass = o_xml.xmlEncode(a_dataOut, s_fileSimpleClass, true);
		} else {
			@SuppressWarnings("unused")
			net.forestany.forestj.lib.io.File o_fileSimpleClass = o_xml.xmlEncode(o_collectionOut, s_fileSimpleClass, true);
		}
		
		Data.SimpleClassCollection o_collectionIn = null;
		java.util.List<Data.SimpleClass> a_dataIn = null;
		
		if (p_s_xsdSchemaFileName.contentEquals("TestXSDSchemaSimpleClassArray.xsd")) {
			@SuppressWarnings("unchecked")
			java.util.List<Data.SimpleClass> a_foo = (java.util.List<Data.SimpleClass>)o_xml.xmlDecode(s_fileSimpleClass);
			a_dataIn = a_foo;
		} else {
			o_collectionIn = (Data.SimpleClassCollection)o_xml.xmlDecode(s_fileSimpleClass);
			a_dataIn = o_collectionIn.SimpleClasses;
		}
		
		i_cnt = 0;
		
		for (Data.SimpleClass o_simpleClassObject : a_dataIn) {
			a_in[i_cnt++] = o_simpleClassObject.toString();
		}
		
		if (!p_s_xsdSchemaFileName.contentEquals("TestXSDSchemaSimpleClassArray.xsd")) {
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_collectionOut, o_collectionIn, o_xml.getUsePropertyMethods()),
					"output object inner class collection is not equal to input object inner class collection"
					);
		}
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut, a_dataIn, o_xml.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		for (int i = 0; i < a_dataOut.size(); i++) {
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut.get(i), a_dataIn.get(i), o_xml.getUsePropertyMethods(), true),
					"output object inner class array element object is not equal to input object inner class array element object"
					);
		
			if (!p_s_xsdSchemaFileName.contentEquals("TestXSDSchemaSimpleClassObjectMultiReferences.xsd")) {
				assertEquals(
						a_out[i],
						a_in[i],
						"output object inner class array element toString is not equal to input object inner class array element toString"
						);
			}
		}
	}

	private static void xmlComplex(String p_s_testDirectory, boolean p_b_classRoot, boolean p_b_listRoot, boolean p_b_listRootOnlyComplex, boolean p_b_dividedClassRoot, boolean p_b_dividedListRoot, boolean p_b_dividedListRootOnlyComplex, boolean p_b_dividedListRootOnlyComplexNoRef, String p_s_fileNameSuffix, boolean p_b_useISO8601UTC) throws Exception {
		Data o_data = new Data();
		java.util.List<Data.ShipOrder> a_shipOrdersOut = Data.generateData();
		
		/* Remove ShipFrom of 2nd ShipOrder-record in ShipMoreInfo, because xsd-schemas only accept two object within ShipMoreInfo */
		a_shipOrdersOut.get(1).getShipMoreInfo().setShipFrom(null);
		
		Data.ShipOrderCollection o_shipOrderCollectionOut = o_data.new ShipOrderCollection();
		o_shipOrderCollectionOut.setShipOrders(a_shipOrdersOut);
		o_shipOrderCollectionOut.setOrderAmount(a_shipOrdersOut.size());
		
		String[] a_out = new String[a_shipOrdersOut.size()];
		String[] a_in = new String[a_shipOrdersOut.size()];
		int i_cnt = 0;
		
		for (Data.ShipOrder o_shipOrderObject : a_shipOrdersOut) {
			a_out[i_cnt++] = o_shipOrderObject.toString();
	    }
		
		String s_xsdSchemaFile = null;
		
		if (p_b_classRoot) {
			s_xsdSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "xml" + net.forestany.forestj.lib.io.File.DIR + "TestXSDSchemaClassRoot.xsd";
		} else if (p_b_listRoot) {
			s_xsdSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "xml" + net.forestany.forestj.lib.io.File.DIR + "TestXSDSchemaListRoot.xsd";
		} else if (p_b_listRootOnlyComplex) {
			s_xsdSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "xml" + net.forestany.forestj.lib.io.File.DIR + "TestXSDSchemaListRootOnlyComplex.xsd";
		} else if (p_b_dividedClassRoot) {
			s_xsdSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "xml" + net.forestany.forestj.lib.io.File.DIR + "TestXSDSchemaDividedClassRoot.xsd";
		} else if (p_b_dividedListRoot) {
			s_xsdSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "xml" + net.forestany.forestj.lib.io.File.DIR + "TestXSDSchemaDividedListRoot.xsd";
		} else if (p_b_dividedListRootOnlyComplex) {
			s_xsdSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "xml" + net.forestany.forestj.lib.io.File.DIR + "TestXSDSchemaDividedListRootOnlyComplex.xsd";
		} else if (p_b_dividedListRootOnlyComplexNoRef) {
			s_xsdSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "xml" + net.forestany.forestj.lib.io.File.DIR + "TestXSDSchemaDividedListRootOnlyComplexNoRef.xsd";
		}
		
		net.forestany.forestj.lib.io.XML o_xml = new net.forestany.forestj.lib.io.XML(s_xsdSchemaFile);
		o_xml.setUsePropertyMethods(true);
		o_xml.setUseISO8601UTC(p_b_useISO8601UTC);
		
		String s_file = p_s_testDirectory + "TestXML" + p_s_fileNameSuffix + ".xml";
		
		if ( (p_b_classRoot) || (p_b_dividedClassRoot) ) {
			@SuppressWarnings("unused")
			net.forestany.forestj.lib.io.File o_file = o_xml.xmlEncode(o_shipOrderCollectionOut, s_file, true);
		} else {
			@SuppressWarnings("unused")
			net.forestany.forestj.lib.io.File o_file = o_xml.xmlEncode(a_shipOrdersOut, s_file, true);
		}
		
		java.util.List<Data.ShipOrder> a_shipOrdersIn = null;
		Data.ShipOrderCollection o_shipOrderCollectionIn = null;
		
		if ( (p_b_classRoot) || (p_b_dividedClassRoot) ) {
			o_shipOrderCollectionIn = (Data.ShipOrderCollection)o_xml.xmlDecode(s_file);
			a_shipOrdersIn = o_shipOrderCollectionIn.getShipOrders();
			o_shipOrderCollectionIn.setShipOrders(a_shipOrdersIn);
			o_shipOrderCollectionIn.setOrderAmount(a_shipOrdersIn.size());
		} else {
			@SuppressWarnings("unchecked")
			java.util.List<Data.ShipOrder> a_foo = (java.util.List<Data.ShipOrder>)o_xml.xmlDecode(s_file);
			a_shipOrdersIn = a_foo;
		}
		
		i_cnt = 0;
		
		for (Data.ShipOrder o_shipOrderObject : a_shipOrdersIn) {
			a_in[i_cnt++] = o_shipOrderObject.toString();
    	}
		
		if ( (p_b_classRoot) || (p_b_dividedClassRoot) ) {
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_shipOrderCollectionOut, o_shipOrderCollectionIn, o_xml.getUsePropertyMethods()),
					"output object inner class collection is not equal to input object inner class collection"
					);
		}
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut, a_shipOrdersIn, o_xml.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		for (int i = 0; i < a_shipOrdersIn.size(); i++) {
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut.get(i), a_shipOrdersIn.get(i), o_xml.getUsePropertyMethods(), true),
					"output object inner class array element object is not equal to input object inner class array element object"
					);
			
			assertEquals(
					a_out[i],
					a_in[i],
					"output object inner class array element string is not equal to input object inner class array element string"
					);
		}
	}

	private static void xmlComplexPart(String p_s_testDirectory, boolean p_b_dividedClassRoot, boolean p_b_dividedListRoot, boolean p_b_dividedListRootOnlyComplex, String p_s_fileNameSuffix, boolean p_b_useISO8601UTC) throws Exception {
		java.util.List<Data.ShipOrder> a_shipOrdersOut = Data.generateData();
		
		/* Remove ShipFrom of 2nd ShipOrder-record in ShipMoreInfo, because xsd-schemas only accept two object within ShipMoreInfo */
		a_shipOrdersOut.get(1).getShipMoreInfo().setShipFrom(null);
		
		Data.ShipItem o_shipItemOut = a_shipOrdersOut.get(0).getShipItems().get(1);
		Data.ShipMoreInfo o_shipMoreInfoOut = a_shipOrdersOut.get(1).getShipMoreInfo();
		java.util.List<Data.ShipItem> a_shipItemsOut = a_shipOrdersOut.get(2).getShipItems();
		
		String s_xsdSchemaFile = null;
		String s_out = null;
		String s_in = null;
		
		if (p_b_dividedClassRoot) {
			s_xsdSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "xml" + net.forestany.forestj.lib.io.File.DIR + "TestXSDSchemaDividedClassRoot.xsd";
			s_out = o_shipItemOut.toString();
		} else if (p_b_dividedListRoot) {
			s_xsdSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "xml" + net.forestany.forestj.lib.io.File.DIR + "TestXSDSchemaDividedListRoot.xsd";
			s_out = o_shipMoreInfoOut.toString();
		} else if (p_b_dividedListRootOnlyComplex) {
			s_xsdSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "xml" + net.forestany.forestj.lib.io.File.DIR + "TestXSDSchemaDividedListRootOnlyComplex.xsd";
			
			for (Data.ShipItem o_foo : a_shipItemsOut) {
				s_out += o_foo.toString();
			}
		}
		
		net.forestany.forestj.lib.io.XML o_xml = new net.forestany.forestj.lib.io.XML(s_xsdSchemaFile);
		o_xml.setUsePropertyMethods(true);
		o_xml.setUseISO8601UTC(p_b_useISO8601UTC);
		
		String s_file = p_s_testDirectory + "TestXML" + p_s_fileNameSuffix + ".xml";
		
		if (p_b_dividedClassRoot) {
			@SuppressWarnings("unused")
			net.forestany.forestj.lib.io.File o_file = o_xml.xmlEncode(o_shipItemOut, s_file, true);
			
			o_xml.validateAgainstSchema(s_file);
			
			Data.ShipItem o_shipItemIn = (Data.ShipItem)o_xml.xmlDecode(s_file);
			s_in = o_shipItemIn.toString();
			
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_shipItemOut, o_shipItemIn, o_xml.getUsePropertyMethods()),
					"output object inner class collection is not equal to input object inner class collection"
			);
		} else if (p_b_dividedListRoot) {
			@SuppressWarnings("unused")
			net.forestany.forestj.lib.io.File o_file = o_xml.xmlEncode(o_shipMoreInfoOut, s_file, true);
			
			o_xml.validateAgainstSchema(s_file);
			
			Data.ShipMoreInfo o_shipMoreInfoIn = (Data.ShipMoreInfo)o_xml.xmlDecode(s_file);
			s_in = o_shipMoreInfoIn.toString();
			
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_shipMoreInfoOut, o_shipMoreInfoIn, o_xml.getUsePropertyMethods()),
					"output object inner class collection is not equal to input object inner class collection"
			);
		} else if (p_b_dividedListRootOnlyComplex) {
			@SuppressWarnings("unused")
			net.forestany.forestj.lib.io.File o_file = o_xml.xmlEncode(a_shipItemsOut, s_file, true);
			
			o_xml.validateAgainstSchema(s_file);
			
			@SuppressWarnings("unchecked")
			java.util.List<Data.ShipItem> a_foo = (java.util.List<Data.ShipItem>)o_xml.xmlDecode(s_file);
			java.util.List<Data.ShipItem> a_shipItemsIn = a_foo;
			
			for (Data.ShipItem o_foo : a_shipItemsIn) {
				s_in += o_foo.toString();
			}
			
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_shipItemsOut, a_shipItemsIn, o_xml.getUsePropertyMethods()),
					"output object inner class collection is not equal to input object inner class collection"
			);
		}
		
		assertEquals(
				s_out,
				s_in,
				"output object inner class array element string is not equal to input object inner class array element string"
		);
	}
	
	private static void xmlMultipleUseOfOneSchemaObject(String p_s_testDirectory) throws Exception {
		
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
		
		String s_xsdSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "xml" + net.forestany.forestj.lib.io.File.DIR + "TestXSDSchemaSimpleClassObjectOneReference.xsd";
		net.forestany.forestj.lib.io.XML o_xml = new net.forestany.forestj.lib.io.XML(s_xsdSchemaFile);
		
		String s_fileSimpleClass = p_s_testDirectory + "TestXMLSimpleClass_Y.xml";
		
		@SuppressWarnings("unused")
		net.forestany.forestj.lib.io.File o_fileSimpleClass = o_xml.xmlEncode(o_collectionOut, s_fileSimpleClass, true);
	
		Data.SimpleClassCollection o_collectionIn = null;
		java.util.List<Data.SimpleClass> a_dataIn = null;
		
		o_collectionIn = (Data.SimpleClassCollection)o_xml.xmlDecode(s_fileSimpleClass);
		a_dataIn = o_collectionIn.SimpleClasses;
		
		i_cnt = 0;
		
		for (Data.SimpleClass o_simpleClassObject : a_dataIn) {
			a_in[i_cnt++] = o_simpleClassObject.toString();
		}
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_collectionOut, o_collectionIn, o_xml.getUsePropertyMethods()),
				"output object inner class collection is not equal to input object inner class collection"
				);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut, a_dataIn, o_xml.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		for (int i = 0; i < a_dataOut.size(); i++) {
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut.get(i), a_dataIn.get(i), o_xml.getUsePropertyMethods(), true),
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
		
		s_fileSimpleClass = p_s_testDirectory + "TestXMLSimpleClass_Z.xml";
		o_fileSimpleClass = o_xml.xmlEncode(o_collectionOut, s_fileSimpleClass, true);
	
		o_collectionIn = null;
		a_dataIn = null;
		
		o_collectionIn = (Data.SimpleClassCollection)o_xml.xmlDecode(s_fileSimpleClass);
		a_dataIn = o_collectionIn.SimpleClasses;
		
		i_cnt = 0;
		
		for (Data.SimpleClass o_simpleClassObject : a_dataIn) {
			a_in[i_cnt++] = o_simpleClassObject.toString();
		}
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_collectionOut, o_collectionIn, o_xml.getUsePropertyMethods()),
				"output object inner class collection is not equal to input object inner class collection"
				);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut, a_dataIn, o_xml.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		for (int i = 0; i < a_dataOut.size(); i++) {
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_dataOut.get(i), a_dataIn.get(i), o_xml.getUsePropertyMethods(), true),
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
		
		/* Remove ShipFrom of 2nd ShipOrder-record in ShipMoreInfo, because xsd-schemas only accept two object within ShipMoreInfo */
		a_shipOrdersOut.get(1).getShipMoreInfo().setShipFrom(null);
		
		Data.ShipOrderCollection o_shipOrderCollectionOut = o_data.new ShipOrderCollection();
		o_shipOrderCollectionOut.setShipOrders(a_shipOrdersOut);
		o_shipOrderCollectionOut.setOrderAmount(a_shipOrdersOut.size());
		
		a_out = new String[a_shipOrdersOut.size()];
		a_in = new String[a_shipOrdersOut.size()];
		i_cnt = 0;
		
		for (Data.ShipOrder o_shipOrderObject : a_shipOrdersOut) {
			a_out[i_cnt++] = o_shipOrderObject.toString();
	    }
		
		s_xsdSchemaFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "xml" + net.forestany.forestj.lib.io.File.DIR + "TestXSDSchemaDividedClassRoot.xsd";
		
		o_xml = new net.forestany.forestj.lib.io.XML(s_xsdSchemaFile);
		o_xml.setUsePropertyMethods(true);
		
		String s_file = p_s_testDirectory + "TestXML_Alpha.xml";
		
		@SuppressWarnings("unused")
		net.forestany.forestj.lib.io.File o_file = o_xml.xmlEncode(o_shipOrderCollectionOut, s_file, true);
	
		java.util.List<Data.ShipOrder> a_shipOrdersIn = null;
		Data.ShipOrderCollection o_shipOrderCollectionIn = null;
		
		o_shipOrderCollectionIn = (Data.ShipOrderCollection)o_xml.xmlDecode(s_file);
		a_shipOrdersIn = o_shipOrderCollectionIn.getShipOrders();
		o_shipOrderCollectionIn.setShipOrders(a_shipOrdersIn);
		o_shipOrderCollectionIn.setOrderAmount(a_shipOrdersIn.size());
	
		i_cnt = 0;
		
		for (Data.ShipOrder o_shipOrderObject : a_shipOrdersIn) {
			a_in[i_cnt++] = o_shipOrderObject.toString();
    	}
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_shipOrderCollectionOut, o_shipOrderCollectionIn, o_xml.getUsePropertyMethods()),
				"output object inner class collection is not equal to input object inner class collection"
				);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut, a_shipOrdersIn, o_xml.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		for (int i = 0; i < a_shipOrdersIn.size(); i++) {
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut.get(i), a_shipOrdersIn.get(i), o_xml.getUsePropertyMethods(), true),
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
		
		/* Remove ShipFrom of 2nd ShipOrder-record in ShipMoreInfo, because xsd-schemas only accept two object within ShipMoreInfo */
		a_shipOrdersOut.get(1).getShipMoreInfo().setShipFrom(null);
		
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
		
		s_file = p_s_testDirectory + "TestXML_Beta.xml";
		o_xml.setUseISO8601UTC(false);
		o_file = o_xml.xmlEncode(o_shipOrderCollectionOut, s_file, true);
	
		a_shipOrdersIn = null;
		o_shipOrderCollectionIn = null;
		
		o_shipOrderCollectionIn = (Data.ShipOrderCollection)o_xml.xmlDecode(s_file);
		a_shipOrdersIn = o_shipOrderCollectionIn.getShipOrders();
		o_shipOrderCollectionIn.setShipOrders(a_shipOrdersIn);
		o_shipOrderCollectionIn.setOrderAmount(a_shipOrdersIn.size());
	
		i_cnt = 0;
		
		for (Data.ShipOrder o_shipOrderObject : a_shipOrdersIn) {
			a_in[i_cnt++] = o_shipOrderObject.toString();
    	}
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_shipOrderCollectionOut, o_shipOrderCollectionIn, o_xml.getUsePropertyMethods()),
				"output object inner class collection is not equal to input object inner class collection"
				);
		
		assertTrue(
				net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut, a_shipOrdersIn, o_xml.getUsePropertyMethods()),
				"output object inner class array is not equal to input object inner class array"
				);
		
		for (int i = 0; i < a_shipOrdersIn.size(); i++) {
			assertTrue(
					net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(a_shipOrdersOut.get(i), a_shipOrdersIn.get(i), o_xml.getUsePropertyMethods(), true),
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
