package de.forestj.lib.test.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FixedLengthRecordTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testFixedLengthRecord() {
		try {
			de.forestj.lib.LoggingConfig.initiateTestLogging();
			
			String s_currentDirectory = de.forestj.lib.io.File.getCurrentDirectory();
			String s_testDirectory = s_currentDirectory + de.forestj.lib.io.File.DIR + "testFLR" + de.forestj.lib.io.File.DIR;
			
			if ( de.forestj.lib.io.File.folderExists(s_testDirectory) ) {
				de.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			}
			
			de.forestj.lib.io.File.createDirectory(s_testDirectory);
			assertTrue(
					de.forestj.lib.io.File.folderExists(s_testDirectory),
					"directory[" + s_testDirectory + "] does not exist"
			);
			
				flrReadWithoutGroups("TestFLRWithoutGroups.txt");
				flrReadWithGroupHeader("TestFLRWithGroupHeader.txt");
				flrReadWithGroupFooter("TestFLRWithGroupFooter.txt");
				flrReadWithGroupHeaderAndFooter("TestFLRWithGroupHeaderAndGroupFooter.txt");
				flrReadEverything("TestFLREverything.txt");
				flrReadEverythingWithSubtypes("TestFLREverythingWithSubtypes.txt");
				
				flrWriteTests(s_testDirectory, "TestWriteFLRWithoutGroups.txt", "TestFLRWithoutGroups.txt", 0);
				flrWriteTests(s_testDirectory, "TestWriteFLRWithGroupHeader.txt", "TestFLRWithGroupHeader.txt", 1);
				flrWriteTests(s_testDirectory, "TestWriteFLRWithGroupFooter.txt", "TestFLRWithGroupFooter.txt", 2);
				flrWriteTests(s_testDirectory, "TestWriteFLRWithGroupHeaderAndGroupFooter.txt", "TestFLRWithGroupHeaderAndGroupFooter.txt", 3);
				flrWriteTests(s_testDirectory, "TestWriteFLREverything.txt", "TestFLREverything.txt", 4);
				flrWriteTests(s_testDirectory, "TestWriteFLREverythingWithSubtypes.txt", "TestFLREverythingWithSubtypes.txt", 5);
			
			de.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			assertFalse(
					de.forestj.lib.io.File.folderExists(s_testDirectory),
					"directory[" + s_testDirectory + "] does exist"
			);
		} catch (Exception o_exc) {
			o_exc.printStackTrace();
			fail(o_exc.getMessage());
		}
	}

	
	private static void flrReadWithoutGroups(String p_s_flrFileName) throws Exception {
		String s_file = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "flr" + de.forestj.lib.io.File.DIR + p_s_flrFileName;
		
		/* test with flr regex */
		FixedLengthRecordData o_flrData = new FixedLengthRecordData();
		de.forestj.lib.io.FixedLengthRecordFile o_flrFile = new de.forestj.lib.io.FixedLengthRecordFile(o_flrData, "^000.*$");
		o_flrFile.readFile(s_file);
		
		for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecordFile.FixedLengthRecordStack> o_stack : o_flrFile.getStacks().entrySet()) {
			int i = 0;
			
			for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecord<?>> o_foo : o_stack.getValue().getFixedLengthRecords().entrySet()) {
				FixedLengthRecordData o_record = (FixedLengthRecordData)o_foo.getValue();
				compareRecords(i++, o_record);
			}
		}
		
		/* test with flr known length */
		o_flrFile = new de.forestj.lib.io.FixedLengthRecordFile(o_flrData, null, 320);
		o_flrFile.readFile(s_file);
		
		for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecordFile.FixedLengthRecordStack> o_stack : o_flrFile.getStacks().entrySet()) {
			int i = 0;
			
			for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecord<?>> o_foo : o_stack.getValue().getFixedLengthRecords().entrySet()) {
				FixedLengthRecordData o_record = (FixedLengthRecordData)o_foo.getValue();
				compareRecords(i++, o_record);
			}
		}
	}
	
	private static void flrReadWithGroupHeader(String p_s_flrFileName) throws Exception {
		String s_file = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "flr" + de.forestj.lib.io.File.DIR + p_s_flrFileName;
		
		/* test with flr regex */
		FixedLengthRecordData o_flrData = new FixedLengthRecordData();
		FixedLengthRecordGroupHeaderData o_groupHeaderData = new FixedLengthRecordGroupHeaderData();
		de.forestj.lib.io.FixedLengthRecordFile o_flrFile = new de.forestj.lib.io.FixedLengthRecordFile(o_flrData, "^000.*$");
		o_flrFile.setGroupHeader(o_flrFile.new FLRType(o_groupHeaderData, "^\\+H\\+.*$"));
		o_flrFile.readFile(s_file);
		
		int i = 0;
		int j = 0;
		
		for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecordFile.FixedLengthRecordStack> o_stack : o_flrFile.getStacks().entrySet()) {
			FixedLengthRecordGroupHeaderData o_groupHeader = (FixedLengthRecordGroupHeaderData)o_stack.getValue().getGroupHeader();
			compareGroupHeaders(i++, o_groupHeader);
			
			for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecord<?>> o_foo : o_stack.getValue().getFixedLengthRecords().entrySet()) {
				FixedLengthRecordData o_record = (FixedLengthRecordData)o_foo.getValue();
				compareRecords(j++, o_record);
			}
		}
		
		/* test with flr known length */
		o_flrFile = new de.forestj.lib.io.FixedLengthRecordFile(o_flrData, null, 320);
		o_flrFile.setGroupHeader(o_flrFile.new FLRType(o_groupHeaderData, null, 106));
		o_flrFile.readFile(s_file);
		
		i = 0;
		j = 0;
		
		for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecordFile.FixedLengthRecordStack> o_stack : o_flrFile.getStacks().entrySet()) {
			FixedLengthRecordGroupHeaderData o_groupHeader = (FixedLengthRecordGroupHeaderData)o_stack.getValue().getGroupHeader();
			compareGroupHeaders(i++, o_groupHeader);
			
			for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecord<?>> o_foo : o_stack.getValue().getFixedLengthRecords().entrySet()) {
				FixedLengthRecordData o_record = (FixedLengthRecordData)o_foo.getValue();
				compareRecords(j++, o_record);
			}
		}
	}
	
	private static void flrReadWithGroupFooter(String p_s_flrFileName) throws Exception {
		String s_file = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "flr" + de.forestj.lib.io.File.DIR + p_s_flrFileName;
		
		/* test with flr regex */
		FixedLengthRecordData o_flrData = new FixedLengthRecordData();
		FixedLengthRecordGroupFooterData o_groupFooterData = new FixedLengthRecordGroupFooterData();
		de.forestj.lib.io.FixedLengthRecordFile o_flrFile = new de.forestj.lib.io.FixedLengthRecordFile(o_flrData, "^000.*$");
		o_flrFile.setGroupFooter(o_flrFile.new FLRType(o_groupFooterData, "^\\+F\\+.*$"));
		o_flrFile.readFile(s_file);
		
		int j = 0;
		int k = 0;
		
		for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecordFile.FixedLengthRecordStack> o_stack : o_flrFile.getStacks().entrySet()) {
			int i_sumInt = 0;
			
			for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecord<?>> o_foo : o_stack.getValue().getFixedLengthRecords().entrySet()) {
				FixedLengthRecordData o_record = (FixedLengthRecordData)o_foo.getValue();
				compareRecords(j++, o_record);
				
				i_sumInt += o_record.FieldInt / 2;
			}
			
			FixedLengthRecordGroupFooterData o_groupFooter = (FixedLengthRecordGroupFooterData)o_stack.getValue().getGroupFooter();
			compareGroupFooters(k++, o_groupFooter);
			
			assertEquals(i_sumInt, o_groupFooter.FieldSumInt, "sum over the field Int has unexpected value for stack #" + (k + 1) + ": '" + i_sumInt + "' != '" + o_groupFooter.FieldSumInt + "'");
		}
		
		/* test with flr known length */
		o_flrFile = new de.forestj.lib.io.FixedLengthRecordFile(o_flrData, null, 320);
		o_flrFile.setGroupFooter(o_flrFile.new FLRType(o_groupFooterData, null, 81));
		o_flrFile.readFile(s_file);
		
		j = 0;
		k = 0;
		
		for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecordFile.FixedLengthRecordStack> o_stack : o_flrFile.getStacks().entrySet()) {
			int i_sumInt = 0;
			
			for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecord<?>> o_foo : o_stack.getValue().getFixedLengthRecords().entrySet()) {
				FixedLengthRecordData o_record = (FixedLengthRecordData)o_foo.getValue();
				compareRecords(j++, o_record);
				
				i_sumInt += o_record.FieldInt / 2;
			}
			
			FixedLengthRecordGroupFooterData o_groupFooter = (FixedLengthRecordGroupFooterData)o_stack.getValue().getGroupFooter();
			compareGroupFooters(k++, o_groupFooter);
			
			assertEquals(i_sumInt, o_groupFooter.FieldSumInt, "sum over the field Int has unexpected value for stack #" + (k + 1) + ": '" + i_sumInt + "' != '" + o_groupFooter.FieldSumInt + "'");
		}
	}
	
	private static void flrReadWithGroupHeaderAndFooter(String p_s_flrFileName) throws Exception {
		String s_file = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "flr" + de.forestj.lib.io.File.DIR + p_s_flrFileName;
		
		/* test with flr regex */
		FixedLengthRecordData o_flrData = new FixedLengthRecordData();
		FixedLengthRecordGroupHeaderData o_groupHeaderData = new FixedLengthRecordGroupHeaderData();
		FixedLengthRecordGroupFooterData o_groupFooterData = new FixedLengthRecordGroupFooterData();
		de.forestj.lib.io.FixedLengthRecordFile o_flrFile = new de.forestj.lib.io.FixedLengthRecordFile(o_flrData, "^000.*$");
		o_flrFile.setGroupHeader(o_flrFile.new FLRType(o_groupHeaderData, "^\\+H\\+.*$"));
		o_flrFile.setGroupFooter(o_flrFile.new FLRType(o_groupFooterData, "^\\+F\\+.*$"));
		o_flrFile.readFile(s_file);
		
		int i = 0;
		int j = 0;
		int k = 0;
		
		for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecordFile.FixedLengthRecordStack> o_stack : o_flrFile.getStacks().entrySet()) {
			int i_sumInt = 0;
			
			FixedLengthRecordGroupHeaderData o_groupHeader = (FixedLengthRecordGroupHeaderData)o_stack.getValue().getGroupHeader();
			compareGroupHeaders(i++, o_groupHeader);
			
			for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecord<?>> o_foo : o_stack.getValue().getFixedLengthRecords().entrySet()) {
				FixedLengthRecordData o_record = (FixedLengthRecordData)o_foo.getValue();
				compareRecords(j++, o_record);
				
				i_sumInt += o_record.FieldInt / 2;
			}
			
			FixedLengthRecordGroupFooterData o_groupFooter = (FixedLengthRecordGroupFooterData)o_stack.getValue().getGroupFooter();
			compareGroupFooters(k++, o_groupFooter);
			
			assertEquals(i_sumInt, o_groupFooter.FieldSumInt, "sum over the field Int has unexpected value for stack #" + (i + 1) + ": '" + i_sumInt + "' != '" + o_groupFooter.FieldSumInt + "'");
		}
		
		/* test with flr known length */
		o_flrFile = new de.forestj.lib.io.FixedLengthRecordFile(o_flrData, null, 320);
		o_flrFile.setGroupHeader(o_flrFile.new FLRType(o_groupHeaderData, null, 106));
		o_flrFile.setGroupFooter(o_flrFile.new FLRType(o_groupFooterData, null, 81));
		o_flrFile.readFile(s_file);
		
		i = 0;
		j = 0;
		k = 0;
		
		for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecordFile.FixedLengthRecordStack> o_stack : o_flrFile.getStacks().entrySet()) {
			int i_sumInt = 0;
			
			FixedLengthRecordGroupHeaderData o_groupHeader = (FixedLengthRecordGroupHeaderData)o_stack.getValue().getGroupHeader();
			compareGroupHeaders(i++, o_groupHeader);
			
			for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecord<?>> o_foo : o_stack.getValue().getFixedLengthRecords().entrySet()) {
				FixedLengthRecordData o_record = (FixedLengthRecordData)o_foo.getValue();
				compareRecords(j++, o_record);
				
				i_sumInt += o_record.FieldInt / 2;
			}
			
			FixedLengthRecordGroupFooterData o_groupFooter = (FixedLengthRecordGroupFooterData)o_stack.getValue().getGroupFooter();
			compareGroupFooters(k++, o_groupFooter);
			
			assertEquals(i_sumInt, o_groupFooter.FieldSumInt, "sum over the field Int has unexpected value for stack #" + (i + 1) + ": '" + i_sumInt + "' != '" + o_groupFooter.FieldSumInt + "'");
		}
	}
	
	private static void flrReadEverything(String p_s_flrFileName) throws Exception {
		String s_file = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "flr" + de.forestj.lib.io.File.DIR + p_s_flrFileName;
		
		/* test with flr regex */
		FixedLengthRecordData o_flrData = new FixedLengthRecordData();
		FixedLengthRecordOtherData o_flrOtherData = new FixedLengthRecordOtherData();
		FixedLengthRecordAnotherData o_flrAnotherData = new FixedLengthRecordAnotherData();
		FixedLengthRecordGroupHeaderData o_groupHeaderData = new FixedLengthRecordGroupHeaderData();
		FixedLengthRecordGroupFooterData o_groupFooterData = new FixedLengthRecordGroupFooterData();
		de.forestj.lib.io.FixedLengthRecordFile o_flrFile = new de.forestj.lib.io.FixedLengthRecordFile(o_flrData, "^000.*$", o_groupHeaderData, "^\\+H\\+.*$", o_groupFooterData, "^\\+F\\+.*$");
		o_flrFile.addFLRType(o_flrFile.new FLRType(o_flrOtherData, "^100.*$"));
		o_flrFile.addFLRType(o_flrFile.new FLRType(o_flrAnotherData, "^200.*$"));
		o_flrFile.readFile(s_file);
		
		int i = 0;
		int j = 0;
		int k = 0;
		
		int l = 0;
		int m = 0;
		
		for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecordFile.FixedLengthRecordStack> o_stack : o_flrFile.getStacks().entrySet()) {
			int i_sumInt = 0;
			
			FixedLengthRecordGroupHeaderData o_groupHeader = (FixedLengthRecordGroupHeaderData)o_stack.getValue().getGroupHeader();
			compareGroupHeaders(i++, o_groupHeader);
			
			for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecord<?>> o_foo : o_stack.getValue().getFixedLengthRecords().entrySet()) {
				if (o_foo.getValue() instanceof FixedLengthRecordData) {
					FixedLengthRecordData o_record = (FixedLengthRecordData)o_foo.getValue();
					compareRecords(j++, o_record);
					i_sumInt += o_record.FieldInt / 2;
				} else if (o_foo.getValue() instanceof FixedLengthRecordOtherData) {
					FixedLengthRecordOtherData o_record = (FixedLengthRecordOtherData)o_foo.getValue();
					compareOtherRecords(l++, o_record);
					i_sumInt += o_record.FieldInt / 2;
				} else if (o_foo.getValue() instanceof FixedLengthRecordAnotherData) {
					FixedLengthRecordAnotherData o_record = (FixedLengthRecordAnotherData)o_foo.getValue();
					compareAnotherRecords(m++, o_record);
					i_sumInt += o_record.FieldInt / 2;
				}
			}
			
			FixedLengthRecordGroupFooterData o_groupFooter = (FixedLengthRecordGroupFooterData)o_stack.getValue().getGroupFooter();
			compareGroupFootersEverything(k++, o_groupFooter);
			
			assertEquals(i_sumInt, o_groupFooter.FieldSumInt, "sum over the field Int has unexpected value for stack #" + (i + 1) + ": '" + i_sumInt + "' != '" + o_groupFooter.FieldSumInt + "'");
		}
		
		/* test with flr known length */
		o_flrFile = new de.forestj.lib.io.FixedLengthRecordFile(o_flrData, null, 320, o_groupHeaderData, null, 106, o_groupFooterData, null, 81);
		o_flrFile.addFLRType(o_flrFile.new FLRType(o_flrOtherData, null, 58));
		o_flrFile.addFLRType(o_flrFile.new FLRType(o_flrAnotherData, null, 72));
		o_flrFile.readFile(s_file);
		
		i = 0;
		j = 0;
		k = 0;
		
		l = 0;
		m = 0;
		
		for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecordFile.FixedLengthRecordStack> o_stack : o_flrFile.getStacks().entrySet()) {
			int i_sumInt = 0;
			
			FixedLengthRecordGroupHeaderData o_groupHeader = (FixedLengthRecordGroupHeaderData)o_stack.getValue().getGroupHeader();
			compareGroupHeaders(i++, o_groupHeader);
			
			for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecord<?>> o_foo : o_stack.getValue().getFixedLengthRecords().entrySet()) {
				if (o_foo.getValue() instanceof FixedLengthRecordData) {
					FixedLengthRecordData o_record = (FixedLengthRecordData)o_foo.getValue();
					compareRecords(j++, o_record);
					i_sumInt += o_record.FieldInt / 2;
				} else if (o_foo.getValue() instanceof FixedLengthRecordOtherData) {
					FixedLengthRecordOtherData o_record = (FixedLengthRecordOtherData)o_foo.getValue();
					compareOtherRecords(l++, o_record);
					i_sumInt += o_record.FieldInt / 2;
				} else if (o_foo.getValue() instanceof FixedLengthRecordAnotherData) {
					FixedLengthRecordAnotherData o_record = (FixedLengthRecordAnotherData)o_foo.getValue();
					compareAnotherRecords(m++, o_record);
					i_sumInt += o_record.FieldInt / 2;
				}
			}
			
			FixedLengthRecordGroupFooterData o_groupFooter = (FixedLengthRecordGroupFooterData)o_stack.getValue().getGroupFooter();
			compareGroupFootersEverything(k++, o_groupFooter);
			
			assertEquals(i_sumInt, o_groupFooter.FieldSumInt, "sum over the field Int has unexpected value for stack #" + (i + 1) + ": '" + i_sumInt + "' != '" + o_groupFooter.FieldSumInt + "'");
		}
	}
	
	private static void flrReadEverythingWithSubtypes(String p_s_flrFileName) throws Exception {
		String s_file = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "flr" + de.forestj.lib.io.File.DIR + p_s_flrFileName;
		
		/* test with flr regex */
		FixedLengthRecordData o_flrData = new FixedLengthRecordData();
		FixedLengthRecordOtherData o_flrOtherData = new FixedLengthRecordOtherData();
		FixedLengthRecordAnotherData o_flrAnotherData = new FixedLengthRecordAnotherData();
		FixedLengthRecordDataWithSubtypes o_flrDataWithSubtypes = new FixedLengthRecordDataWithSubtypes();
		FixedLengthRecordGroupHeaderData o_groupHeaderData = new FixedLengthRecordGroupHeaderData();
		FixedLengthRecordGroupFooterData o_groupFooterData = new FixedLengthRecordGroupFooterData();
		de.forestj.lib.io.FixedLengthRecordFile o_flrFile = new de.forestj.lib.io.FixedLengthRecordFile(o_flrData, "^000.*$", o_groupHeaderData, "^\\+H\\+.*$", o_groupFooterData, "^\\+F\\+.*$");
		o_flrFile.addFLRType(o_flrFile.new FLRType(o_flrOtherData, "^100.*$"));
		o_flrFile.addFLRType(o_flrFile.new FLRType(o_flrAnotherData, "^200.*$"));
		o_flrFile.addFLRType(o_flrFile.new FLRType(o_flrDataWithSubtypes, "^300.*$"));
		o_flrFile.readFile(s_file);
		
		int i = 0;
		int j = 0;
		int k = 0;
		
		int l = 0;
		int m = 0;
		int n = 0;
		
		for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecordFile.FixedLengthRecordStack> o_stack : o_flrFile.getStacks().entrySet()) {
			int i_sumInt = 0;
			
			FixedLengthRecordGroupHeaderData o_groupHeader = (FixedLengthRecordGroupHeaderData)o_stack.getValue().getGroupHeader();
			compareGroupHeaders(i++, o_groupHeader);
			
			for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecord<?>> o_foo : o_stack.getValue().getFixedLengthRecords().entrySet()) {
				if (o_foo.getValue() instanceof FixedLengthRecordData) {
					FixedLengthRecordData o_record = (FixedLengthRecordData)o_foo.getValue();
					compareRecords(j++, o_record);
					i_sumInt += o_record.FieldInt / 2;
				} else if (o_foo.getValue() instanceof FixedLengthRecordOtherData) {
					FixedLengthRecordOtherData o_record = (FixedLengthRecordOtherData)o_foo.getValue();
					compareOtherRecords(l++, o_record);
					i_sumInt += o_record.FieldInt / 2;
				} else if (o_foo.getValue() instanceof FixedLengthRecordAnotherData) {
					FixedLengthRecordAnotherData o_record = (FixedLengthRecordAnotherData)o_foo.getValue();
					compareAnotherRecords(m++, o_record);
					i_sumInt += o_record.FieldInt / 2;
				} else if (o_foo.getValue() instanceof FixedLengthRecordDataWithSubtypes) {
					FixedLengthRecordDataWithSubtypes o_record = (FixedLengthRecordDataWithSubtypes)o_foo.getValue();
					compareRecordsWithSubtypes(n++, o_record);
				}
			}
			
			FixedLengthRecordGroupFooterData o_groupFooter = (FixedLengthRecordGroupFooterData)o_stack.getValue().getGroupFooter();
			compareGroupFootersEverythingWithSubtypes(k++, o_groupFooter);
			
			assertEquals(i_sumInt, o_groupFooter.FieldSumInt, "sum over the field Int has unexpected value for stack #" + (i + 1) + ": '" + i_sumInt + "' != '" + o_groupFooter.FieldSumInt + "'");
		}
		
		/* test with flr known length */
		o_flrFile = new de.forestj.lib.io.FixedLengthRecordFile(o_flrData, null, 320, o_groupHeaderData, null, 106, o_groupFooterData, null, 81);
		o_flrFile.addFLRType(o_flrFile.new FLRType(o_flrOtherData, null, 58));
		o_flrFile.addFLRType(o_flrFile.new FLRType(o_flrAnotherData, null, 72));
		o_flrFile.addFLRType(o_flrFile.new FLRType(o_flrDataWithSubtypes, null, 217));
		o_flrFile.readFile(s_file);
		
		i = 0;
		j = 0;
		k = 0;
		
		l = 0;
		m = 0;
		n = 0;
		
		for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecordFile.FixedLengthRecordStack> o_stack : o_flrFile.getStacks().entrySet()) {
			int i_sumInt = 0;
			
			FixedLengthRecordGroupHeaderData o_groupHeader = (FixedLengthRecordGroupHeaderData)o_stack.getValue().getGroupHeader();
			compareGroupHeaders(i++, o_groupHeader);
			
			for (java.util.Map.Entry<Integer, de.forestj.lib.io.FixedLengthRecord<?>> o_foo : o_stack.getValue().getFixedLengthRecords().entrySet()) {
				if (o_foo.getValue() instanceof FixedLengthRecordData) {
					FixedLengthRecordData o_record = (FixedLengthRecordData)o_foo.getValue();
					compareRecords(j++, o_record);
					i_sumInt += o_record.FieldInt / 2;
				} else if (o_foo.getValue() instanceof FixedLengthRecordOtherData) {
					FixedLengthRecordOtherData o_record = (FixedLengthRecordOtherData)o_foo.getValue();
					compareOtherRecords(l++, o_record);
					i_sumInt += o_record.FieldInt / 2;
				} else if (o_foo.getValue() instanceof FixedLengthRecordAnotherData) {
					FixedLengthRecordAnotherData o_record = (FixedLengthRecordAnotherData)o_foo.getValue();
					compareAnotherRecords(m++, o_record);
					i_sumInt += o_record.FieldInt / 2;
				} else if (o_foo.getValue() instanceof FixedLengthRecordDataWithSubtypes) {
					FixedLengthRecordDataWithSubtypes o_record = (FixedLengthRecordDataWithSubtypes)o_foo.getValue();
					compareRecordsWithSubtypes(n++, o_record);
				}
			}
			
			FixedLengthRecordGroupFooterData o_groupFooter = (FixedLengthRecordGroupFooterData)o_stack.getValue().getGroupFooter();
			compareGroupFootersEverythingWithSubtypes(k++, o_groupFooter);
			
			assertEquals(i_sumInt, o_groupFooter.FieldSumInt, "sum over the field Int has unexpected value for stack #" + (i + 1) + ": '" + i_sumInt + "' != '" + o_groupFooter.FieldSumInt + "'");
		}
	}
	
	private static void compareGroupHeaders(int p_i_groupHeader, FixedLengthRecordGroupHeaderData o_groupHeader) throws Exception {
		if (p_i_groupHeader == 0) {
			assertEquals(123,											o_groupHeader.FieldCustomerNumber,			"values for field 'CustomerNumber' are not equal");
			assertEquals(java.time.LocalDate.of(2011, 1, 1),			o_groupHeader.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(314598.443589d,								o_groupHeader.FieldDoubleWithSeparator,		"values for field 'DoubleWithSeparator' are not equal");
		} else if (p_i_groupHeader == 1) {
			assertEquals(321,											o_groupHeader.FieldCustomerNumber,			"values for field 'CustomerNumber' are not equal");
			assertEquals(java.time.LocalDate.of(2022, 4, 2),			o_groupHeader.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(157783.965224d,								o_groupHeader.FieldDoubleWithSeparator,		"values for field 'DoubleWithSeparator' are not equal");
		} else if (p_i_groupHeader == 2) {
			assertEquals(132,											o_groupHeader.FieldCustomerNumber,			"values for field 'CustomerNumber' are not equal");
			assertEquals(java.time.LocalDate.of(2033, 6, 3),			o_groupHeader.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(453665.357896d,								o_groupHeader.FieldDoubleWithSeparator,		"values for field 'DoubleWithSeparator' are not equal");
		}
	}
	
	private static void compareGroupFooters(int p_i_groupFooter, FixedLengthRecordGroupFooterData o_groupFooter) throws Exception {
		if (p_i_groupFooter == 0) {
			assertEquals(3,												o_groupFooter.FieldAmountRecords,		"values for field 'AmountRecords' are not equal");
			assertEquals(1200061721,									o_groupFooter.FieldSumInt,				"values for field 'SumInt' are not equal");
		} else if (p_i_groupFooter == 1) {
			assertEquals(3,												o_groupFooter.FieldAmountRecords,		"values for field 'AmountRecords' are not equal");
			assertEquals(766660500,										o_groupFooter.FieldSumInt,				"values for field 'SumInt' are not equal");
		} else if (p_i_groupFooter == 2) {
			assertEquals(2,												o_groupFooter.FieldAmountRecords,		"values for field 'AmountRecords' are not equal");
			assertEquals(260606060,										o_groupFooter.FieldSumInt,				"values for field 'SumInt' are not equal");
		}
	}
	
	private static void compareGroupFootersEverything(int p_i_groupFooter, FixedLengthRecordGroupFooterData o_groupFooter) throws Exception {
		if (p_i_groupFooter == 0) {
			assertEquals(6,												o_groupFooter.FieldAmountRecords,		"values for field 'AmountRecords' are not equal");
			assertEquals(1200132092,									o_groupFooter.FieldSumInt,				"values for field 'SumInt' are not equal");
		} else if (p_i_groupFooter == 1) {
			assertEquals(6,												o_groupFooter.FieldAmountRecords,		"values for field 'AmountRecords' are not equal");
			assertEquals(766737533,										o_groupFooter.FieldSumInt,				"values for field 'SumInt' are not equal");
		} else if (p_i_groupFooter == 2) {
			assertEquals(8,												o_groupFooter.FieldAmountRecords,		"values for field 'AmountRecords' are not equal");
			assertEquals(260642429,										o_groupFooter.FieldSumInt,				"values for field 'SumInt' are not equal");
		}
	}
	
	private static void compareGroupFootersEverythingWithSubtypes(int p_i_groupFooter, FixedLengthRecordGroupFooterData o_groupFooter) throws Exception {
		if (p_i_groupFooter == 0) {
			assertEquals(7,												o_groupFooter.FieldAmountRecords,		"values for field 'AmountRecords' are not equal");
			assertEquals(1200132092,									o_groupFooter.FieldSumInt,				"values for field 'SumInt' are not equal");
		} else if (p_i_groupFooter == 1) {
			assertEquals(7,												o_groupFooter.FieldAmountRecords,		"values for field 'AmountRecords' are not equal");
			assertEquals(766737533,										o_groupFooter.FieldSumInt,				"values for field 'SumInt' are not equal");
		} else if (p_i_groupFooter == 2) {
			assertEquals(10,											o_groupFooter.FieldAmountRecords,		"values for field 'AmountRecords' are not equal");
			assertEquals(260642429,										o_groupFooter.FieldSumInt,				"values for field 'SumInt' are not equal");
		}
	}
		
	private static void compareRecords(int p_i_record, FixedLengthRecordData o_record) throws Exception {
		java.text.DateFormat o_datetimeFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.text.DateFormat o_dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.text.DateFormat o_timeFormat = new java.text.SimpleDateFormat("HH:mm:ss");
		
		if (p_i_record == 0) {
			assertEquals(1,																			o_record.FieldId,					"values for field 'Id' are not equal");
			assertEquals("9d08862f-a9d0-4970-bba2-eb95dc9245f8",									o_record.FieldUUID,					"values for field 'UUID' are not equal");
			assertEquals("Das ist einfach ",														o_record.FieldShortText,			"values for field 'ShortText' are not equal");
			assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et.",		o_record.FieldText,					"values for field 'Text' are not equal");
			assertEquals((short)1001,																o_record.FieldSmallInt,				"values for field 'SmallInt' are not equal");
			assertEquals(900000123,																	o_record.FieldInt,					"values for field 'Int' are not equal");
			assertEquals(653398433456789458L,														o_record.FieldBigInt,				"values for field 'BigInt' are not equal");
			assertEquals(o_datetimeFormat.parse("2011-01-01 02:04:08"),								o_record.FieldTimestamp,			"values for field 'Timestamp' are not equal");
			assertEquals(o_dateFormat.parse("2011-01-01"),											o_record.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(o_timeFormat.parse("02:04:08"),											o_record.FieldTime,					"values for field 'Time' are not equal");
			assertEquals(java.time.LocalTime.of(1, 4, 8),											o_record.FieldLocalTime,			"values for field 'LocalTime' are not equal");
			assertEquals(java.time.LocalDate.of(2011, 1, 1),										o_record.FieldLocalDate,			"values for field 'LocalDate' are not equal");
			assertEquals(de.forestj.lib.Helper.fromISO8601UTC("2011-01-01T01:04:08Z"),				o_record.FieldLocalDateTime,		"values for field 'LocalDateTime' are not equal");
			assertEquals((byte)127,																	o_record.FieldByteCol,				"values for field 'ByteCol' are not equal");
			assertEquals(1448.83f,																	o_record.FieldFloatCol,				"values for field 'FloatCol' are not equal");
			assertEquals(1511.171755d,																o_record.FieldDoubleCol,			"values for field 'DoubleCol' are not equal");
			assertEquals(new java.math.BigDecimal("208.22104724543748"),							o_record.FieldDecimal,				"values for field 'Decimal' are not equal");
			assertEquals(false,																		o_record.FieldBool,					"values for field 'Bool' are not equal");
			assertEquals("Living valley had silent eat mer",										o_record.FieldText2,				"values for field 'Text2' are not equal");
			assertEquals("One Two ",																o_record.FieldShortText2,			"values for field 'ShortText2' are not equal");
		} else if (p_i_record == 1) {
			assertEquals(2,																			o_record.FieldId,					"values for field 'Id' are not equal");
			assertEquals("7ac8ac59-055c-46e2-894b-2ae02f7ed26e",									o_record.FieldUUID,					"values for field 'UUID' are not equal");
			assertEquals("Test Test Test T",														o_record.FieldShortText,			"values for field 'ShortText' are not equal");
			assertEquals("Procuring education on consulted assurance in do. Is sympathize.",		o_record.FieldText,					"values for field 'Text' are not equal");
			assertEquals((short)1002,																o_record.FieldSmallInt,				"values for field 'SmallInt' are not equal");
			assertEquals(800000321,																	o_record.FieldInt,					"values for field 'Int' are not equal");
			assertEquals(726273033145988523L,														o_record.FieldBigInt,				"values for field 'BigInt' are not equal");
			assertEquals(o_datetimeFormat.parse("2022-02-02 04:08:16"),								o_record.FieldTimestamp,			"values for field 'Timestamp' are not equal");
			assertEquals(o_dateFormat.parse("2022-02-02"),											o_record.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(o_timeFormat.parse("04:08:16"),											o_record.FieldTime,					"values for field 'Time' are not equal");
			assertEquals(java.time.LocalTime.of(3, 8, 16),											o_record.FieldLocalTime,			"values for field 'LocalTime' are not equal");
			assertEquals(java.time.LocalDate.of(2022, 2, 2),										o_record.FieldLocalDate,			"values for field 'LocalDate' are not equal");
			assertEquals(de.forestj.lib.Helper.fromISO8601UTC("2022-02-02T03:08:16Z"),				o_record.FieldLocalDateTime,		"values for field 'LocalDateTime' are not equal");
			assertEquals((byte)64,																	o_record.FieldByteCol,				"values for field 'ByteCol' are not equal");
			assertEquals(2195.12f,																	o_record.FieldFloatCol,				"values for field 'FloatCol' are not equal");
			assertEquals(-755.585877d,																o_record.FieldDoubleCol,			"values for field 'DoubleCol' are not equal");
			assertEquals(new java.math.BigDecimal("47.03507874239581"),								o_record.FieldDecimal,				"values for field 'Decimal' are not equal");
			assertEquals(true,																		o_record.FieldBool,					"values for field 'Bool' are not equal");
			assertEquals("its esteem bed. In last an or we",										o_record.FieldText2,				"values for field 'Text2' are not equal");
			assertEquals("Three Fo",																o_record.FieldShortText2,			"values for field 'ShortText2' are not equal");
		} else if (p_i_record == 2) {
			assertEquals(3,																			o_record.FieldId,					"values for field 'Id' are not equal");
			assertEquals("15b19fdc-2d37-4481-8e88-bc022a4ed715",									o_record.FieldUUID,					"values for field 'UUID' are not equal");
			assertEquals("A B C D E F G H ",														o_record.FieldShortText,			"values for field 'ShortText' are not equal");
			assertEquals("he expression mr no travelling. Preference he he at travelling. ",		o_record.FieldText,					"values for field 'Text' are not equal");
			assertEquals((short)1003,																o_record.FieldSmallInt,				"values for field 'SmallInt' are not equal");
			assertEquals(700123000,																	o_record.FieldInt,					"values for field 'Int' are not equal");
			assertEquals(973697365456213587L,														o_record.FieldBigInt,				"values for field 'BigInt' are not equal");
			assertEquals(o_datetimeFormat.parse("2033-03-03 06:16:32"),								o_record.FieldTimestamp,			"values for field 'Timestamp' are not equal");
			assertEquals(o_dateFormat.parse("2033-03-03"),											o_record.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(o_timeFormat.parse("06:16:32"),											o_record.FieldTime,					"values for field 'Time' are not equal");
			assertEquals(java.time.LocalTime.of(5, 16, 32),											o_record.FieldLocalTime,			"values for field 'LocalTime' are not equal");
			assertEquals(java.time.LocalDate.of(2033, 3, 3),										o_record.FieldLocalDate,			"values for field 'LocalDate' are not equal");
			assertEquals(de.forestj.lib.Helper.fromISO8601UTC("2033-03-03T05:16:32Z"),				o_record.FieldLocalDateTime,		"values for field 'LocalDateTime' are not equal");
			assertEquals((byte)32,																	o_record.FieldByteCol,				"values for field 'ByteCol' are not equal");
			assertEquals(4390.24f,																	o_record.FieldFloatCol,				"values for field 'FloatCol' are not equal");
			assertEquals(3585.195292d,																o_record.FieldDoubleCol,			"values for field 'DoubleCol' are not equal");
			assertEquals(new java.math.BigDecimal("67.58769685598953"),								o_record.FieldDecimal,				"values for field 'Decimal' are not equal");
			assertEquals(true,																		o_record.FieldBool,					"values for field 'Bool' are not equal");
			assertEquals("nt wise as left. Visited civilly",										o_record.FieldText2,				"values for field 'Text2' are not equal");
			assertEquals("ur Five ",																o_record.FieldShortText2,			"values for field 'ShortText2' are not equal");
		} else if (p_i_record == 3) {
			assertEquals(4,																			o_record.FieldId,					"values for field 'Id' are not equal");
			assertEquals("e1ac53e1-72e9-41d2-8278-17c1c8be79f2",									o_record.FieldUUID,					"values for field 'UUID' are not equal");
			assertEquals(" a b c d e f g h",														o_record.FieldShortText,			"values for field 'ShortText' are not equal");
			assertEquals("resolution. So striking at of to welcomed resolved. Northward by",		o_record.FieldText,					"values for field 'Text' are not equal");
			assertEquals((short)1004,																o_record.FieldSmallInt,				"values for field 'SmallInt' are not equal");
			assertEquals(600321000,																	o_record.FieldInt,					"values for field 'Int' are not equal");
			assertEquals(555672589158833618L,														o_record.FieldBigInt,				"values for field 'BigInt' are not equal");
			assertEquals(o_datetimeFormat.parse("2044-04-04 08:32:04"),								o_record.FieldTimestamp,			"values for field 'Timestamp' are not equal");
			assertEquals(o_dateFormat.parse("2044-04-04"),											o_record.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(o_timeFormat.parse("08:32:04"),											o_record.FieldTime,					"values for field 'Time' are not equal");
			assertEquals(java.time.LocalTime.of(7, 32, 4),											o_record.FieldLocalTime,			"values for field 'LocalTime' are not equal");
			assertEquals(java.time.LocalDate.of(2044, 4, 4),										o_record.FieldLocalDate,			"values for field 'LocalDate' are not equal");
			assertEquals(de.forestj.lib.Helper.fromISO8601UTC("2044-04-04T07:32:04Z"),				o_record.FieldLocalDateTime,		"values for field 'LocalDateTime' are not equal");
			assertEquals((byte)16,																	o_record.FieldByteCol,				"values for field 'ByteCol' are not equal");
			assertEquals(7317.07f,																	o_record.FieldFloatCol,				"values for field 'FloatCol' are not equal");
			assertEquals(681.56234d,																o_record.FieldDoubleCol,			"values for field 'DoubleCol' are not equal");
			assertEquals(new java.math.BigDecimal("481.99316491999418"),							o_record.FieldDecimal,				"values for field 'Decimal' are not equal");
			assertEquals(true,																		o_record.FieldBool,					"values for field 'Bool' are not equal");
			assertEquals("am demesne so colonel he calling",										o_record.FieldText2,				"values for field 'Text2' are not equal");
			assertEquals("Six Seve",																o_record.FieldShortText2,			"values for field 'ShortText2' are not equal");
		} else if (p_i_record == 4) {
			assertEquals(5,																			o_record.FieldId,					"values for field 'Id' are not equal");
			assertEquals("f33dc48a-8656-4cd1-970f-c3c14571038a",									o_record.FieldUUID,					"values for field 'UUID' are not equal");
			assertEquals("1 2 3 4 5 6 7 8 ",														o_record.FieldShortText,			"values for field 'ShortText' are not equal");
			assertEquals("described up household therefore attention. Excellence          ",		o_record.FieldText,					"values for field 'Text' are not equal");
			assertEquals((short)1005,																o_record.FieldSmallInt,				"values for field 'SmallInt' are not equal");
			assertEquals(512000000,																	o_record.FieldInt,					"values for field 'Int' are not equal");
			assertEquals(536914555663547894L,														o_record.FieldBigInt,				"values for field 'BigInt' are not equal");
			assertEquals(o_datetimeFormat.parse("2055-05-05 10:04:08"),								o_record.FieldTimestamp,			"values for field 'Timestamp' are not equal");
			assertEquals(o_dateFormat.parse("2055-05-05"),											o_record.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(o_timeFormat.parse("10:04:08"),											o_record.FieldTime,					"values for field 'Time' are not equal");
			assertEquals(java.time.LocalTime.of(9, 4, 8),											o_record.FieldLocalTime,			"values for field 'LocalTime' are not equal");
			assertEquals(java.time.LocalDate.of(2055, 5, 5),										o_record.FieldLocalDate,			"values for field 'LocalDate' are not equal");
			assertEquals(de.forestj.lib.Helper.fromISO8601UTC("2055-05-05T09:04:08Z"),				o_record.FieldLocalDateTime,		"values for field 'LocalDateTime' are not equal");
			assertEquals((byte)8,																	o_record.FieldByteCol,				"values for field 'ByteCol' are not equal");
			assertEquals(4390.24f,																	o_record.FieldFloatCol,				"values for field 'FloatCol' are not equal");
			assertEquals(4726.24936d,																o_record.FieldDoubleCol,			"values for field 'DoubleCol' are not equal");
			assertEquals(new java.math.BigDecimal("19.38838036399012"),								o_record.FieldDecimal,				"values for field 'Decimal' are not equal");
			assertEquals(false,																		o_record.FieldBool,					"values for field 'Bool' are not equal");
			assertEquals("So unreserved do interested incr",										o_record.FieldText2,				"values for field 'Text2' are not equal");
			assertEquals("n Eight ",																o_record.FieldShortText2,			"values for field 'ShortText2' are not equal");
		} else if (p_i_record == 5) {
			assertEquals(6,																			o_record.FieldId,					"values for field 'Id' are not equal");
			assertEquals("9fbac7dd-af73-4052-bf93-10762d7966bd",									o_record.FieldUUID,					"values for field 'UUID' are not equal");
			assertEquals(" 9 8 7 6 5 4 3 2",														o_record.FieldShortText,			"values for field 'ShortText' are not equal");
			assertEquals("decisively nay man yet impression for contrasted remarkably. The",		o_record.FieldText,					"values for field 'Text' are not equal");
			assertEquals((short)1006,																o_record.FieldSmallInt,				"values for field 'SmallInt' are not equal");
			assertEquals(421000000,																	o_record.FieldInt,					"values for field 'Int' are not equal");
			assertEquals(785633065136485210L,														o_record.FieldBigInt,				"values for field 'BigInt' are not equal");
			assertEquals(o_datetimeFormat.parse("2066-06-06 12:08:16"),								o_record.FieldTimestamp,			"values for field 'Timestamp' are not equal");
			assertEquals(o_dateFormat.parse("2066-06-06"),											o_record.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(o_timeFormat.parse("12:08:16"),											o_record.FieldTime,					"values for field 'Time' are not equal");
			assertEquals(java.time.LocalTime.of(11, 8, 16),											o_record.FieldLocalTime,			"values for field 'LocalTime' are not equal");
			assertEquals(java.time.LocalDate.of(2066, 6, 6),										o_record.FieldLocalDate,			"values for field 'LocalDate' are not equal");
			assertEquals(de.forestj.lib.Helper.fromISO8601UTC("2066-06-06T11:08:16Z"),				o_record.FieldLocalDateTime,		"values for field 'LocalDateTime' are not equal");
			assertEquals((byte)4,																	o_record.FieldByteCol,				"values for field 'ByteCol' are not equal");
			assertEquals(8799.24f,																	o_record.FieldFloatCol,				"values for field 'FloatCol' are not equal");
			assertEquals(-8922.053556d,																o_record.FieldDoubleCol,			"values for field 'DoubleCol' are not equal");
			assertEquals(new java.math.BigDecimal("73.12946012133004"),								o_record.FieldDecimal,				"values for field 'Decimal' are not equal");
			assertEquals(false,																		o_record.FieldBool,					"values for field 'Bool' are not equal");
			assertEquals("easing sentiments. Vanity day gi",										o_record.FieldText2,				"values for field 'Text2' are not equal");
			assertEquals("Nine Ten",																o_record.FieldShortText2,			"values for field 'ShortText2' are not equal");
		} else if (p_i_record == 6) {
			assertEquals(7,																			o_record.FieldId,					"values for field 'Id' are not equal");
			assertEquals(null,																		o_record.FieldUUID,					"values for field 'UUID' are not equal");
			assertEquals(null,																		o_record.FieldShortText,			"values for field 'ShortText' are not equal");
			assertEquals(null,																		o_record.FieldText,					"values for field 'Text' are not equal");
			assertEquals((short)0,																	o_record.FieldSmallInt,				"values for field 'SmallInt' are not equal");
			assertEquals(0,																			o_record.FieldInt,					"values for field 'Int' are not equal");
			assertEquals(0L,																		o_record.FieldBigInt,				"values for field 'BigInt' are not equal");
			assertEquals(null,																		o_record.FieldTimestamp,			"values for field 'Timestamp' are not equal");
			assertEquals(null,																		o_record.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(null,																		o_record.FieldTime,					"values for field 'Time' are not equal");
			assertEquals(null,																		o_record.FieldLocalTime,			"values for field 'LocalTime' are not equal");
			assertEquals(null,																		o_record.FieldLocalDate,			"values for field 'LocalDate' are not equal");
			assertEquals(null,																		o_record.FieldLocalDateTime,		"values for field 'LocalDateTime' are not equal");
			assertEquals((byte)0,																	o_record.FieldByteCol,				"values for field 'ByteCol' are not equal");
			assertEquals(0.0f,																		o_record.FieldFloatCol,				"values for field 'FloatCol' are not equal");
			assertEquals(0.0d,																		o_record.FieldDoubleCol,			"values for field 'DoubleCol' are not equal");
			assertEquals(new java.math.BigDecimal("0.0"),											o_record.FieldDecimal,				"values for field 'Decimal' are not equal");
			assertEquals(false,																		o_record.FieldBool,					"values for field 'Bool' are not equal");
			assertEquals(null,																		o_record.FieldText2,				"values for field 'Text2' are not equal");
			assertEquals(null,																		o_record.FieldShortText2,			"values for field 'ShortText2' are not equal");
		} else if (p_i_record == 7) {
			assertEquals(8,																			o_record.FieldId,					"values for field 'Id' are not equal");
			assertEquals("1babc8e1-e8c7-4ad4-bf4d-61bde1838c6b",									o_record.FieldUUID,					"values for field 'UUID' are not equal");
			assertEquals("Java Development",														o_record.FieldShortText,			"values for field 'ShortText' are not equal");
			assertEquals("spoke happy for you are out. Fertile how old address did showing",		o_record.FieldText,					"values for field 'Text' are not equal");
			assertEquals((short)1007,																o_record.FieldSmallInt,				"values for field 'SmallInt' are not equal");
			assertEquals(301010101,																	o_record.FieldInt,					"values for field 'Int' are not equal");
			assertEquals(779589670663214588L,														o_record.FieldBigInt,				"values for field 'BigInt' are not equal");
			assertEquals(o_datetimeFormat.parse("2077-07-07 14:16:32"),								o_record.FieldTimestamp,			"values for field 'Timestamp' are not equal");
			assertEquals(o_dateFormat.parse("2077-07-07"),											o_record.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(o_timeFormat.parse("14:16:32"),											o_record.FieldTime,					"values for field 'Time' are not equal");
			assertEquals(java.time.LocalTime.of(13, 16, 32),										o_record.FieldLocalTime,			"values for field 'LocalTime' are not equal");
			assertEquals(java.time.LocalDate.of(2077, 7, 7),										o_record.FieldLocalDate,			"values for field 'LocalDate' are not equal");
			assertEquals(de.forestj.lib.Helper.fromISO8601UTC("2077-07-07T13:16:32Z"),				o_record.FieldLocalDateTime,		"values for field 'LocalDateTime' are not equal");
			assertEquals((byte)2,																	o_record.FieldByteCol,				"values for field 'ByteCol' are not equal");
			assertEquals(3996.24f,																	o_record.FieldFloatCol,				"values for field 'FloatCol' are not equal");
			assertEquals(-6766.160668d,																o_record.FieldDoubleCol,			"values for field 'DoubleCol' are not equal");
			assertEquals(new java.math.BigDecimal("240.35392490677043"),							o_record.FieldDecimal,				"values for field 'Decimal' are not equal");
			assertEquals(false,																		o_record.FieldBool,					"values for field 'Bool' are not equal");
			assertEquals("ving points within six not law. ",										o_record.FieldText2,				"values for field 'Text2' are not equal");
			assertEquals("Eleven T",																o_record.FieldShortText2,			"values for field 'ShortText2' are not equal");
		} else if (p_i_record == 8) {
			assertEquals(9,																			o_record.FieldId,					"values for field 'Id' are not equal");
			assertEquals("e979c923-654a-44b7-a70a-099942c9a834",									o_record.FieldUUID,					"values for field 'UUID' are not equal");
			assertEquals(".NET Development",														o_record.FieldShortText,			"values for field 'ShortText' are not equal");
			assertEquals("because sitting replied six. Had arose guest visit going off.   ",		o_record.FieldText,					"values for field 'Text' are not equal");
			assertEquals((short)1008,																o_record.FieldSmallInt,				"values for field 'SmallInt' are not equal");
			assertEquals(220202020,																	o_record.FieldInt,					"values for field 'Int' are not equal");
			assertEquals(846022040001245036L,														o_record.FieldBigInt,				"values for field 'BigInt' are not equal");
			assertEquals(o_datetimeFormat.parse("2088-08-08 16:32:04"),								o_record.FieldTimestamp,			"values for field 'Timestamp' are not equal");
			assertEquals(o_dateFormat.parse("2088-08-08"),											o_record.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(o_timeFormat.parse("16:32:04"),											o_record.FieldTime,					"values for field 'Time' are not equal");
			assertEquals(java.time.LocalTime.of(15, 32, 04),										o_record.FieldLocalTime,			"values for field 'LocalTime' are not equal");
			assertEquals(java.time.LocalDate.of(2088, 8, 8),										o_record.FieldLocalDate,			"values for field 'LocalDate' are not equal");
			assertEquals(de.forestj.lib.Helper.fromISO8601UTC("2088-08-08T15:32:04Z"),				o_record.FieldLocalDateTime,		"values for field 'LocalDateTime' are not equal");
			assertEquals((byte)1,																	o_record.FieldByteCol,				"values for field 'ByteCol' are not equal");
			assertEquals(3090.56f,																	o_record.FieldFloatCol,				"values for field 'FloatCol' are not equal");
			assertEquals(9461.026778d,																o_record.FieldDoubleCol,			"values for field 'DoubleCol' are not equal");
			assertEquals(new java.math.BigDecimal("80.70784981354087"),								o_record.FieldDecimal,				"values for field 'Decimal' are not equal");
			assertEquals(true,																		o_record.FieldBool,					"values for field 'Bool' are not equal");
			assertEquals("Few impression difficulty his us",										o_record.FieldText2,				"values for field 'Text2' are not equal");
			assertEquals("welve Th",																o_record.FieldShortText2,			"values for field 'ShortText2' are not equal");
		}
	}
	
	private static void compareOtherRecords(int p_i_record, FixedLengthRecordOtherData o_record) throws Exception {
		java.text.DateFormat o_datetimeFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.text.DateFormat o_dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.text.DateFormat o_timeFormat = new java.text.SimpleDateFormat("HH:mm:ss");
		
		if (p_i_record == 0) {
			assertEquals("00000A",																	o_record.FieldStringId,				"values for field 'StringId' are not equal");
			assertEquals(45872,																		o_record.FieldInt,					"values for field 'Int' are not equal");
			assertEquals(o_datetimeFormat.parse("2011-01-01 02:04:08"),								o_record.FieldTimestamp,			"values for field 'Timestamp' are not equal");
			assertEquals(o_dateFormat.parse("2011-01-01"),											o_record.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(o_timeFormat.parse("02:04:08"),											o_record.FieldTime,					"values for field 'Time' are not equal");
			assertEquals("A B C D E F G",															o_record.FieldShortText,			"values for field 'ShortText' are not equal");
		} else if (p_i_record == 1) {
			assertEquals("00000B",																	o_record.FieldStringId,				"values for field 'StringId' are not equal");
			assertEquals(62348,																		o_record.FieldInt,					"values for field 'Int' are not equal");
			assertEquals(o_datetimeFormat.parse("2022-02-02 04:08:16"),								o_record.FieldTimestamp,			"values for field 'Timestamp' are not equal");
			assertEquals(o_dateFormat.parse("2022-02-02"),											o_record.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(o_timeFormat.parse("04:08:16"),											o_record.FieldTime,					"values for field 'Time' are not equal");
			assertEquals(" H I J K L M ",															o_record.FieldShortText,			"values for field 'ShortText' are not equal");
		} else if (p_i_record == 2) {
			assertEquals("00000A",																	o_record.FieldStringId,				"values for field 'Id' are not equal");
			assertEquals(45486,																		o_record.FieldInt,					"values for field 'Int' are not equal");
			assertEquals(o_datetimeFormat.parse("2033-03-03 06:16:32"),								o_record.FieldTimestamp,			"values for field 'Timestamp' are not equal");
			assertEquals(o_dateFormat.parse("2033-03-03"),											o_record.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(o_timeFormat.parse("06:16:32"),											o_record.FieldTime,					"values for field 'Time' are not equal");
			assertEquals("N O P Q R S T",															o_record.FieldShortText,			"values for field 'ShortText' are not equal");
		} else if (p_i_record == 3) {
			assertEquals("00000B",																	o_record.FieldStringId,				"values for field 'StringId' are not equal");
			assertEquals(97322,																		o_record.FieldInt,					"values for field 'Int' are not equal");
			assertEquals(o_datetimeFormat.parse("2044-04-04 08:32:04"),								o_record.FieldTimestamp,			"values for field 'Timestamp' are not equal");
			assertEquals(o_dateFormat.parse("2044-04-04"),											o_record.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(o_timeFormat.parse("08:32:04"),											o_record.FieldTime,					"values for field 'Time' are not equal");
			assertEquals(" U V W X Y Z ",															o_record.FieldShortText,			"values for field 'ShortText' are not equal");
		} else if (p_i_record == 4) {
			assertEquals("00000A",																	o_record.FieldStringId,				"values for field 'StringId' are not equal");
			assertEquals(36582,																		o_record.FieldInt,					"values for field 'Int' are not equal");
			assertEquals(o_datetimeFormat.parse("2055-05-05 10:04:08"),								o_record.FieldTimestamp,			"values for field 'Timestamp' are not equal");
			assertEquals(o_dateFormat.parse("2055-05-05"),											o_record.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(o_timeFormat.parse("10:04:08"),											o_record.FieldTime,					"values for field 'Time' are not equal");
			assertEquals("a b c d e f g",															o_record.FieldShortText,			"values for field 'ShortText' are not equal");
		} else if (p_i_record == 5) {
			assertEquals("00000B",																	o_record.FieldStringId,				"values for field 'StringId' are not equal");
			assertEquals(0,																			o_record.FieldInt,					"values for field 'Int' are not equal");
			assertEquals(null,																		o_record.FieldTimestamp,			"values for field 'Timestamp' are not equal");
			assertEquals(null,																		o_record.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(null,																		o_record.FieldTime,					"values for field 'Time' are not equal");
			assertEquals(null,																		o_record.FieldShortText,			"values for field 'ShortText' are not equal");
		} else if (p_i_record == 6) {
			assertEquals("00000C",																	o_record.FieldStringId,				"values for field 'StringId' are not equal");
			assertEquals(22558,																		o_record.FieldInt,					"values for field 'Int' are not equal");
			assertEquals(o_datetimeFormat.parse("2066-06-06 12:08:16"),								o_record.FieldTimestamp,			"values for field 'Timestamp' are not equal");
			assertEquals(o_dateFormat.parse("2066-06-06"),											o_record.FieldDate,					"values for field 'Date' are not equal");
			assertEquals(o_timeFormat.parse("12:08:16"),											o_record.FieldTime,					"values for field 'Time' are not equal");
			assertEquals(" h i j k l m ",															o_record.FieldShortText,			"values for field 'ShortText' are not equal");
		}
	}
	
	private static void compareAnotherRecords(int p_i_record, FixedLengthRecordAnotherData o_record) throws Exception {
		if (p_i_record == 0) {
			assertEquals("00000A",																	o_record.FieldStringId,				"values for field 'StringId' are not equal");
			assertEquals(1448.83f,																	o_record.FieldFloatCol,				"values for field 'FloatCol' are not equal");
			assertEquals(1511.171755d,																o_record.FieldDoubleCol,			"values for field 'DoubleCol' are not equal");
			assertEquals(new java.math.BigDecimal("208.22104724543748"),							o_record.FieldDecimal,				"values for field 'Decimal' are not equal");
			assertEquals(32522,																		o_record.FieldInt,					"values for field 'Int' are not equal");
		} else if (p_i_record == 1) {
			assertEquals("00000A",																	o_record.FieldStringId,				"values for field 'StringId' are not equal");
			assertEquals(2195.12f,																	o_record.FieldFloatCol,				"values for field 'FloatCol' are not equal");
			assertEquals(-755.585877d,																o_record.FieldDoubleCol,			"values for field 'DoubleCol' are not equal");
			assertEquals(new java.math.BigDecimal("47.03507874239581"),								o_record.FieldDecimal,				"values for field 'Decimal' are not equal");
			assertEquals(11258,																		o_record.FieldInt,					"values for field 'Int' are not equal");
		} else if (p_i_record == 2) {
			assertEquals("00000A",																	o_record.FieldStringId,				"values for field 'StringId' are not equal");
			assertEquals(4390.24f,																	o_record.FieldFloatCol,				"values for field 'FloatCol' are not equal");
			assertEquals(3585.195292d,																o_record.FieldDoubleCol,			"values for field 'DoubleCol' are not equal");
			assertEquals(new java.math.BigDecimal("67.58769685598953"),								o_record.FieldDecimal,				"values for field 'Decimal' are not equal");
			assertEquals(13598,																		o_record.FieldInt,					"values for field 'Int' are not equal");
		} else if (p_i_record == 3) {
			assertEquals("00000B",																	o_record.FieldStringId,				"values for field 'StringId' are not equal");
			assertEquals(0.0f,																		o_record.FieldFloatCol,				"values for field 'FloatCol' are not equal");
			assertEquals(0.0d,																		o_record.FieldDoubleCol,			"values for field 'DoubleCol' are not equal");
			assertEquals(new java.math.BigDecimal("0.0"),											o_record.FieldDecimal,				"values for field 'Decimal' are not equal");
			assertEquals(0,																			o_record.FieldInt,					"values for field 'Int' are not equal");
		}
	}
	
	private static void compareRecordsWithSubtypes(int p_i_record, FixedLengthRecordDataWithSubtypes o_record) throws Exception {
		java.text.DateFormat o_dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		
        if (p_i_record == 0) {
        	assertEquals(o_dateFormat.parse("2010-10-10"),											o_record.FieldDate,					"values for field 'Date' are not equal");
        	assertEquals("Last notice   1",															o_record.FieldLastNotice,			"values for field 'LastNotice' are not equal");
        	
            int i = 1;

            for (FixedLengthRecordSubtypeOne o_one : o_record.FieldListOnes) {
                if (i == 1) {
                	assertEquals(111,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("Test1     ",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 2) {
                	assertEquals(222,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("Test2     ",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 3) {
                	assertEquals(333,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("Test3     ",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 4) {
                	assertEquals(444,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("Test4     ",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 5) {
                	assertEquals(555,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("Test5     ",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 6) {
                	assertEquals(666,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("Test6     ",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 7) {
                	assertEquals(0,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals(null,																o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 8) {
                	assertEquals(0,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals(null,																o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 9) {
                	assertEquals(0,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals(null,																o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 10) {
                	assertEquals(0,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals(null,																o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 11) {
                	assertEquals(0,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals(null,																o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                }

                i++;
            }

            i = 1;

            for (FixedLengthRecordSubtypeTwo o_two : o_record.FieldListTwos) {
                if (i == 1) {
                	assertEquals(13579.246d,														o_two.FieldDoubleValue,				"values for field 'DoubleValue' are not equal");
                } else if (i == 2) {
                	assertEquals(-951.753d,															o_two.FieldDoubleValue,				"values for field 'DoubleValue' are not equal");
                } else if (i == 3) {
                	assertEquals(0.0d,																o_two.FieldDoubleValue,				"values for field 'DoubleValue' are not equal");
                } else if (i == 4) {
                	assertEquals(0.0d,																o_two.FieldDoubleValue,				"values for field 'DoubleValue' are not equal");
                }

                i++;
            }
        } else if (p_i_record == 1) {
        	assertEquals(o_dateFormat.parse("2020-02-02"),											o_record.FieldDate,					"values for field 'Date' are not equal");
        	assertEquals("Last notice   2",															o_record.FieldLastNotice,			"values for field 'LastNotice' are not equal");
            
            int i = 1;

            for (FixedLengthRecordSubtypeOne o_one : o_record.FieldListOnes) {
                if (i == 1) {
                	assertEquals(0,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals(null,																o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 2) {
                	assertEquals(0,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals(null,																o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 3) {
                	assertEquals(0,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals(null,																o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 4) {
                	assertEquals(0,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals(null,																o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 5) {
                	assertEquals(0,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals(null,																o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 6) {
                	assertEquals(0,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals(null,																o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 7) {
                	assertEquals(7,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("     Test7",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 8) {
                	assertEquals(8,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("     Test8",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 9) {
                	assertEquals(9,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("     Test9",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 10) {
                	assertEquals(10,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("    Test10",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 11) {
                	assertEquals(11,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("    Test11",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                }

                i++;
            }
            
            i = 1;

            for (FixedLengthRecordSubtypeTwo o_two : o_record.FieldListTwos) {
                if (i == 1) {
                	assertEquals(0.0,																o_two.FieldDoubleValue,				"values for field 'DoubleValue' are not equal");
                } else if (i == 2) {
                	assertEquals(0.0,																o_two.FieldDoubleValue,				"values for field 'DoubleValue' are not equal");
                } else if (i == 3) {
                	assertEquals(-4623527.958d,														o_two.FieldDoubleValue,				"values for field 'DoubleValue' are not equal");
                } else if (i == 4) {
                	assertEquals(-66538.974d,														o_two.FieldDoubleValue,				"values for field 'DoubleValue' are not equal");
                }

                i++;
            }
        } else if (p_i_record == 2) {
        	assertEquals(o_dateFormat.parse("2030-03-03"),											o_record.FieldDate,					"values for field 'Date' are not equal");
        	assertEquals("Last notice   3",															o_record.FieldLastNotice,			"values for field 'LastNotice' are not equal");
            
            int i = 1;

            for (FixedLengthRecordSubtypeOne o_one : o_record.FieldListOnes) {
            	if (i == 1) {
                	assertEquals(111,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("Test1     ",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 2) {
                	assertEquals(222,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("Test2     ",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 3) {
                	assertEquals(333,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("Test3     ",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 4) {
                	assertEquals(444,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("Test4     ",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 5) {
                	assertEquals(555,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("Test5     ",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 6) {
                	assertEquals(666,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("Test6     ",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 7) {
                	assertEquals(7,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("     Test7",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 8) {
                	assertEquals(8,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("     Test8",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 9) {
                	assertEquals(9,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("     Test9",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 10) {
                	assertEquals(10,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("    Test10",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 11) {
                	assertEquals(11,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("    Test11",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                }

                i++;
            }
            
            i = 1;

            for (FixedLengthRecordSubtypeTwo o_two : o_record.FieldListTwos) {
            	if (i == 1) {
                	assertEquals(13579.246d,														o_two.FieldDoubleValue,				"values for field 'DoubleValue' are not equal");
                } else if (i == 2) {
                	assertEquals(-951.753d,															o_two.FieldDoubleValue,				"values for field 'DoubleValue' are not equal");
                } else if (i == 3) {
                	assertEquals(-4623527.958d,														o_two.FieldDoubleValue,				"values for field 'DoubleValue' are not equal");
                } else if (i == 4) {
                	assertEquals(-66538.974d,														o_two.FieldDoubleValue,				"values for field 'DoubleValue' are not equal");
                }

                i++;
            }
        } else if (p_i_record == 3) {
        	assertEquals(o_dateFormat.parse("2040-04-04"),											o_record.FieldDate,					"values for field 'Date' are not equal");
        	assertEquals("Last notice   4",															o_record.FieldLastNotice,			"values for field 'LastNotice' are not equal");
        	
            int i = 1;

            for (FixedLengthRecordSubtypeOne o_one : o_record.FieldListOnes) {
            	if (i == 1) {
                	assertEquals(111,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("Test1     ",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 2) {
                	assertEquals(0,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals(null,																o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 3) {
                	assertEquals(333,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("Test3     ",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 4) {
                	assertEquals(0,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals(null,																o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 5) {
                	assertEquals(555,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("Test5     ",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 6) {
                	assertEquals(0,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals(null,																o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 7) {
                	assertEquals(7,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("     Test7",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 8) {
                	assertEquals(0,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals(null,																o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 9) {
                	assertEquals(9,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("     Test9",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 10) {
                	assertEquals(0,																	o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals(null,																o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                } else if (i == 11) {
                	assertEquals(11,																o_one.FieldThreeDigitId,			"values for field 'ThreeDigitsId' are not equal");
                	assertEquals("    Test11",														o_one.FieldShortText,				"values for field 'ShortText' are not equal");
                }

                i++;
            }
            
            i = 1;

            for (FixedLengthRecordSubtypeTwo o_two : o_record.FieldListTwos) {
            	if (i == 1) {
                	assertEquals(13579.246d,														o_two.FieldDoubleValue,				"values for field 'DoubleValue' are not equal");
                } else if (i == 2) {
                	assertEquals(0.0d,																o_two.FieldDoubleValue,				"values for field 'DoubleValue' are not equal");
                } else if (i == 3) {
                	assertEquals(-4623527.958d,														o_two.FieldDoubleValue,				"values for field 'DoubleValue' are not equal");
                } else if (i == 4) {
                	assertEquals(0.0d,																o_two.FieldDoubleValue,				"values for field 'DoubleValue' are not equal");
                }

                i++;
            }
        }
    }
	
	
	private static void flrWriteTests(String p_s_testDirectory, String p_s_flrFileName, String p_s_contentCompareFileName, int p_i_mode) throws Exception {
		String s_file = p_s_testDirectory + p_s_flrFileName;
		String s_contentCompareFile = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "flr" + de.forestj.lib.io.File.DIR + p_s_contentCompareFileName;
		
		FixedLengthRecordData o_flrData = new FixedLengthRecordData();
		de.forestj.lib.io.FixedLengthRecordFile o_flrFile = new de.forestj.lib.io.FixedLengthRecordFile(o_flrData, "^000.*$");
		createStackData(p_i_mode, o_flrFile);
		o_flrFile.writeFile(s_file);
		
		/* compare file hashes */
		assertEquals(
			de.forestj.lib.io.File.hashFile(s_file, "SHA-512"),
			de.forestj.lib.io.File.hashFile(s_contentCompareFile, "SHA-512"),
			"flr file has not the expected content"
		);
	}
	
	private static void createStackData(int p_i_mode, de.forestj.lib.io.FixedLengthRecordFile p_o_flrFile) throws Exception {
		/*
		 * mode = 0 -> only one stack with 9 records
		 * mode = 1 -> 3 stacks with group headers and records
		 * mode = 2 -> 3 stacks with group footers and records
		 * mode = 3 -> 3 stacks with group headers, footers and record
		 * mode = 4 -> 3 stacks with group headers, footers and 3 different types of records
		 * mode = 5 -> 3 stacks with group headers, footers and 4 different types of records - one type has two subtypes
		 */
		
		java.text.DateFormat o_datetimeFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.text.DateFormat o_dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.text.DateFormat o_timeFormat = new java.text.SimpleDateFormat("HH:mm:ss");
		
		int i_stackNumber = 0;
		int i_recordNumber = 0;
		
		de.forestj.lib.io.FixedLengthRecordFile.FixedLengthRecordStack o_stack = p_o_flrFile.createNewStack();
		
		if ( (p_i_mode == 1) || (p_i_mode == 3) || (p_i_mode == 4) || (p_i_mode == 5) ) {
			FixedLengthRecordGroupHeaderData o_groupHeader = new FixedLengthRecordGroupHeaderData();
			o_groupHeader.FieldCustomerNumber = 123;
			o_groupHeader.FieldDate = java.time.LocalDate.of(2011, 1, 1);
			o_groupHeader.FieldDoubleWithSeparator = 314598.443589d;
			
			o_stack.setGroupHeader(o_groupHeader);
		}
		
		FixedLengthRecordData o_flr = new FixedLengthRecordData();
		o_flr.FieldId = 				1;																			
		o_flr.FieldUUID = 				"9d08862f-a9d0-4970-bba2-eb95dc9245f8";									
		o_flr.FieldShortText = 			"Das ist einfach ";														
		o_flr.FieldText = 				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et.";		
		o_flr.FieldSmallInt = 			(short)1001;																
		o_flr.FieldInt = 				900000123;																	
		o_flr.FieldBigInt = 			653398433456789458L;														
		o_flr.FieldTimestamp = 			o_datetimeFormat.parse("2011-01-01 02:04:08");								
		o_flr.FieldDate = 				o_dateFormat.parse("2011-01-01");											
		o_flr.FieldTime = 				o_timeFormat.parse("02:04:08");											
		o_flr.FieldLocalTime = 			java.time.LocalTime.of(1, 4, 8);											
		o_flr.FieldLocalDate = 			java.time.LocalDate.of(2011, 1, 1);										
		o_flr.FieldLocalDateTime = 		de.forestj.lib.Helper.fromISO8601UTC("2011-01-01T01:04:08Z");				
		o_flr.FieldByteCol = 			(byte)127;																	
		o_flr.FieldFloatCol = 			1448.83f;																	
		o_flr.FieldDoubleCol = 			1511.171755d;																
		o_flr.FieldDecimal = 			new java.math.BigDecimal("208.22104724543748");							
		o_flr.FieldBool = 				false;																		
		o_flr.FieldText2 = 				"Living valley had silent eat mer";										
		o_flr.FieldShortText2 = 		"One Two ";
		
		o_stack.addFixedLengthRecord(i_recordNumber++, o_flr);
		
		if ( (p_i_mode == 4) || (p_i_mode == 5) ) {
			FixedLengthRecordOtherData o_flrOther = new FixedLengthRecordOtherData();
			o_flrOther.FieldStringId = 			"00000A";
			o_flrOther.FieldInt = 				45872;																	
			o_flrOther.FieldTimestamp = 		o_datetimeFormat.parse("2011-01-01 02:04:08");								
			o_flrOther.FieldDate = 				o_dateFormat.parse("2011-01-01");											
			o_flrOther.FieldTime = 				o_timeFormat.parse("02:04:08");
			o_flrOther.FieldShortText = 		"A B C D E F G";
			
			o_stack.addFixedLengthRecord(i_recordNumber++, o_flrOther);
		}
		
		o_flr = new FixedLengthRecordData();
		o_flr.FieldId = 				2;																			
		o_flr.FieldUUID = 				"7ac8ac59-055c-46e2-894b-2ae02f7ed26e";									
		o_flr.FieldShortText = 			"Test Test Test T";														
		o_flr.FieldText = 				"Procuring education on consulted assurance in do. Is sympathize.";		
		o_flr.FieldSmallInt = 			(short)1002;																
		o_flr.FieldInt = 				800000321;																	
		o_flr.FieldBigInt = 			726273033145988523L;														
		o_flr.FieldTimestamp = 			o_datetimeFormat.parse("2022-02-02 04:08:16");								
		o_flr.FieldDate = 				o_dateFormat.parse("2022-02-02");											
		o_flr.FieldTime = 				o_timeFormat.parse("04:08:16");											
		o_flr.FieldLocalTime = 			java.time.LocalTime.of(3, 8, 16);											
		o_flr.FieldLocalDate = 			java.time.LocalDate.of(2022, 2, 2);										
		o_flr.FieldLocalDateTime = 		de.forestj.lib.Helper.fromISO8601UTC("2022-02-02T03:08:16Z");				
		o_flr.FieldByteCol = 			(byte)64;																	
		o_flr.FieldFloatCol = 			2195.12f;																	
		o_flr.FieldDoubleCol = 			-755.585877d;																
		o_flr.FieldDecimal = 			new java.math.BigDecimal("47.03507874239581");							
		o_flr.FieldBool = 				true;																		
		o_flr.FieldText2 = 				"its esteem bed. In last an or we";										
		o_flr.FieldShortText2 = 		"Three Fo";
		
		o_stack.addFixedLengthRecord(i_recordNumber++, o_flr);
		
		if ( (p_i_mode == 4) || (p_i_mode == 5) ) {
			FixedLengthRecordOtherData o_flrOther = new FixedLengthRecordOtherData();
			o_flrOther.FieldStringId = 			"00000B";
			o_flrOther.FieldInt = 				62348;																	
			o_flrOther.FieldTimestamp = 		o_datetimeFormat.parse("2022-02-02 04:08:16");								
			o_flrOther.FieldDate = 				o_dateFormat.parse("2022-02-02");											
			o_flrOther.FieldTime = 				o_timeFormat.parse("04:08:16");		
			o_flrOther.FieldShortText = 		" H I J K L M ";
			
			o_stack.addFixedLengthRecord(i_recordNumber++, o_flrOther);
		}
		
		o_flr = new FixedLengthRecordData();
		o_flr.FieldId = 				3;																			
		o_flr.FieldUUID = 				"15b19fdc-2d37-4481-8e88-bc022a4ed715";									
		o_flr.FieldShortText = 			"A B C D E F G H ";														
		o_flr.FieldText = 				"he expression mr no travelling. Preference he he at travelling. ";		
		o_flr.FieldSmallInt = 			(short)1003;																
		o_flr.FieldInt = 				700123000;																	
		o_flr.FieldBigInt = 			973697365456213587L;														
		o_flr.FieldTimestamp = 			o_datetimeFormat.parse("2033-03-03 06:16:32");								
		o_flr.FieldDate = 				o_dateFormat.parse("2033-03-03");											
		o_flr.FieldTime = 				o_timeFormat.parse("06:16:32");											
		o_flr.FieldLocalTime = 			java.time.LocalTime.of(5, 16, 32);											
		o_flr.FieldLocalDate = 			java.time.LocalDate.of(2033, 3, 3);										
		o_flr.FieldLocalDateTime = 		de.forestj.lib.Helper.fromISO8601UTC("2033-03-03T05:16:32Z");				
		o_flr.FieldByteCol = 			(byte)32;																	
		o_flr.FieldFloatCol = 			4390.24f;																	
		o_flr.FieldDoubleCol = 			3585.195292d;																
		o_flr.FieldDecimal = 			new java.math.BigDecimal("67.58769685598953");							
		o_flr.FieldBool = 				true;																		
		o_flr.FieldText2 = 				"nt wise as left. Visited civilly";										
		o_flr.FieldShortText2 = 		"ur Five ";
		
		o_stack.addFixedLengthRecord(i_recordNumber++, o_flr);
		
		if (p_i_mode == 5) {
            FixedLengthRecordDataWithSubtypes o_flrWithSubtypes = new FixedLengthRecordDataWithSubtypes();
            o_flrWithSubtypes.FieldDate = o_dateFormat.parse("2010-10-10");
            o_flrWithSubtypes.FieldLastNotice = "Last notice   1";
            
            o_flrWithSubtypes.FieldListOnes = new java.util.ArrayList<FixedLengthRecordSubtypeOne>();
            	FixedLengthRecordSubtypeOne o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 111;
	            	o_one.FieldShortText = "Test1     ";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 222;
	            	o_one.FieldShortText = "Test2     ";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 333;
	            	o_one.FieldShortText = "Test3     ";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 444;
	            	o_one.FieldShortText = "Test4     ";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 555;
	            	o_one.FieldShortText = "Test5     ";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 666;
	            	o_one.FieldShortText = "Test6     ";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            
            o_flrWithSubtypes.FieldListTwos = new java.util.ArrayList<FixedLengthRecordSubtypeTwo>();
            	FixedLengthRecordSubtypeTwo o_two = new FixedLengthRecordSubtypeTwo();
            		o_two.FieldDoubleValue = 13579.246d;
            	o_flrWithSubtypes.FieldListTwos.add(o_two);
            	o_two = new FixedLengthRecordSubtypeTwo();
	        		o_two.FieldDoubleValue = -951.753d;
	        	o_flrWithSubtypes.FieldListTwos.add(o_two);
                            
            o_stack.addFixedLengthRecord(i_recordNumber++, o_flrWithSubtypes);
        }
		
		if ( (p_i_mode == 4) || (p_i_mode == 5) ) {
			FixedLengthRecordAnotherData o_flrAnother = new FixedLengthRecordAnotherData();
			o_flrAnother.FieldStringId = 			"00000A";
			o_flrAnother.FieldFloatCol = 			1448.83f;																	
			o_flrAnother.FieldDoubleCol = 			1511.171755d;																
			o_flrAnother.FieldDecimal = 			new java.math.BigDecimal("208.22104724543748");							
			o_flrAnother.FieldInt = 				32522;																	
			
			o_stack.addFixedLengthRecord(i_recordNumber++, o_flrAnother);
		}
		
		if ( (p_i_mode == 2) || (p_i_mode == 3) || (p_i_mode == 4) || (p_i_mode == 5) ) { 
			FixedLengthRecordGroupFooterData o_groupFooter = new FixedLengthRecordGroupFooterData();
			o_groupFooter.FieldAmountRecords = (p_i_mode >= 4) ? ((p_i_mode == 4) ? 6 : 7) : 3;
			o_groupFooter.FieldSumInt = ((p_i_mode == 4) || (p_i_mode == 5)) ? 1200132092 : 1200061721;
			
			o_stack.setGroupFooter(o_groupFooter);
			
			p_o_flrFile.addStack(i_stackNumber++, o_stack);
			o_stack = p_o_flrFile.createNewStack();
			i_recordNumber = 0;
		}
		
		if ( (p_i_mode == 1) || (p_i_mode == 3) || (p_i_mode == 4) || (p_i_mode == 5) ) {
			if (p_i_mode == 1) {
				p_o_flrFile.addStack(i_stackNumber++, o_stack);
				o_stack = p_o_flrFile.createNewStack();
				i_recordNumber = 0;
			}
			
			FixedLengthRecordGroupHeaderData o_groupHeader = new FixedLengthRecordGroupHeaderData();
			o_groupHeader.FieldCustomerNumber = 321;
			o_groupHeader.FieldDate = java.time.LocalDate.of(2022, 4, 2);
			o_groupHeader.FieldDoubleWithSeparator = 157783.965224d;
			
			o_stack.setGroupHeader(o_groupHeader);
		}
		
		o_flr = new FixedLengthRecordData();
		o_flr.FieldId = 				4;																			
		o_flr.FieldUUID = 				"e1ac53e1-72e9-41d2-8278-17c1c8be79f2";									
		o_flr.FieldShortText = 			" a b c d e f g h";														
		o_flr.FieldText = 				"resolution. So striking at of to welcomed resolved. Northward by";		
		o_flr.FieldSmallInt = 			(short)1004;																
		o_flr.FieldInt = 				600321000;																	
		o_flr.FieldBigInt = 			555672589158833618L;														
		o_flr.FieldTimestamp = 			o_datetimeFormat.parse("2044-04-04 08:32:04");								
		o_flr.FieldDate = 				o_dateFormat.parse("2044-04-04");											
		o_flr.FieldTime = 				o_timeFormat.parse("08:32:04");											
		o_flr.FieldLocalTime = 			java.time.LocalTime.of(7, 32, 4);											
		o_flr.FieldLocalDate = 			java.time.LocalDate.of(2044, 4, 4);										
		o_flr.FieldLocalDateTime = 		de.forestj.lib.Helper.fromISO8601UTC("2044-04-04T07:32:04Z");				
		o_flr.FieldByteCol = 			(byte)16;																	
		o_flr.FieldFloatCol = 			7317.07f;																	
		o_flr.FieldDoubleCol = 			681.56234d;																
		o_flr.FieldDecimal = 			new java.math.BigDecimal("481.99316491999418");							
		o_flr.FieldBool = 				true;																		
		o_flr.FieldText2 = 				"am demesne so colonel he calling";										
		o_flr.FieldShortText2 = 		"Six Seve";
		
		o_stack.addFixedLengthRecord(i_recordNumber++, o_flr);
		
		if ( (p_i_mode == 4) || (p_i_mode == 5) ) {
			FixedLengthRecordOtherData o_flrOther = new FixedLengthRecordOtherData();
			o_flrOther.FieldStringId = 			"00000A";
			o_flrOther.FieldInt = 				45486;																	
			o_flrOther.FieldTimestamp = 		o_datetimeFormat.parse("2033-03-03 06:16:32");								
			o_flrOther.FieldDate = 				o_dateFormat.parse("2033-03-03");											
			o_flrOther.FieldTime = 				o_timeFormat.parse("06:16:32");					
			o_flrOther.FieldShortText = 		"N O P Q R S T";
			
			o_stack.addFixedLengthRecord(i_recordNumber++, o_flrOther);
		}
		
		if (p_i_mode == 5) {
            FixedLengthRecordDataWithSubtypes o_flrWithSubtypes = new FixedLengthRecordDataWithSubtypes();
            o_flrWithSubtypes.FieldDate = o_dateFormat.parse("2020-02-02");
            o_flrWithSubtypes.FieldLastNotice = "Last notice   2";
            
            o_flrWithSubtypes.FieldListOnes = new java.util.ArrayList<FixedLengthRecordSubtypeOne>();
            	FixedLengthRecordSubtypeOne o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 0;
	            	o_one.FieldShortText = null;
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
	        	o_one = new FixedLengthRecordSubtypeOne();
		        	o_one.FieldThreeDigitId = 0;
		        	o_one.FieldShortText = null;
		        	o_flrWithSubtypes.FieldListOnes.add(o_one);
		    	o_one = new FixedLengthRecordSubtypeOne();
		    		o_one.FieldThreeDigitId = 0;
		    		o_one.FieldShortText = null;
		    		o_flrWithSubtypes.FieldListOnes.add(o_one);
				o_one = new FixedLengthRecordSubtypeOne();
					o_one.FieldThreeDigitId = 0;
	            	o_one.FieldShortText = null;
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
	        	o_one = new FixedLengthRecordSubtypeOne();
	        		o_one.FieldThreeDigitId = 0;
	            	o_one.FieldShortText = null;
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	        		o_one.FieldThreeDigitId = 0;
	            	o_one.FieldShortText = null;
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
	        	o_one = new FixedLengthRecordSubtypeOne();
	        		o_one.FieldThreeDigitId = 7;
	            	o_one.FieldShortText = "     Test7";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 8;
	            	o_one.FieldShortText = "     Test8";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 9;
	            	o_one.FieldShortText = "     Test9";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 10;
	            	o_one.FieldShortText = "    Test10";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 11;
	            	o_one.FieldShortText = "    Test11";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            
            o_flrWithSubtypes.FieldListTwos = new java.util.ArrayList<FixedLengthRecordSubtypeTwo>();
            	FixedLengthRecordSubtypeTwo o_two = new FixedLengthRecordSubtypeTwo();
	            	o_two.FieldDoubleValue = 0.0;
	        		o_flrWithSubtypes.FieldListTwos.add(o_two);
	        	o_two = new FixedLengthRecordSubtypeTwo();
        			o_two.FieldDoubleValue = 0.0;
            		o_flrWithSubtypes.FieldListTwos.add(o_two);
            	o_two = new FixedLengthRecordSubtypeTwo();
	        		o_two.FieldDoubleValue = -4623527.958d;
	        		o_flrWithSubtypes.FieldListTwos.add(o_two);
            	o_two = new FixedLengthRecordSubtypeTwo();
	        		o_two.FieldDoubleValue = -66538.974d;
	        		o_flrWithSubtypes.FieldListTwos.add(o_two);
                            
            o_stack.addFixedLengthRecord(i_recordNumber++, o_flrWithSubtypes);
        }
		
		if ( (p_i_mode == 4) || (p_i_mode == 5) ) {
			FixedLengthRecordAnotherData o_flrAnother = new FixedLengthRecordAnotherData();
			o_flrAnother.FieldStringId = 			"00000A";
			o_flrAnother.FieldFloatCol = 			2195.12f;																	
			o_flrAnother.FieldDoubleCol = 			-755.585877d;																
			o_flrAnother.FieldDecimal = 			new java.math.BigDecimal("47.03507874239581");							
			o_flrAnother.FieldInt = 				11258;																	
			
			o_stack.addFixedLengthRecord(i_recordNumber++, o_flrAnother);
		}
		
		o_flr = new FixedLengthRecordData();
		o_flr.FieldId = 				5;																			
		o_flr.FieldUUID = 				"f33dc48a-8656-4cd1-970f-c3c14571038a";									
		o_flr.FieldShortText = 			"1 2 3 4 5 6 7 8 ";														
		o_flr.FieldText = 				"described up household therefore attention. Excellence          ";		
		o_flr.FieldSmallInt = 			(short)1005;																
		o_flr.FieldInt = 				512000000;																	
		o_flr.FieldBigInt = 			536914555663547894L;														
		o_flr.FieldTimestamp = 			o_datetimeFormat.parse("2055-05-05 10:04:08");								
		o_flr.FieldDate = 				o_dateFormat.parse("2055-05-05");											
		o_flr.FieldTime = 				o_timeFormat.parse("10:04:08");											
		o_flr.FieldLocalTime = 			java.time.LocalTime.of(9, 4, 8);											
		o_flr.FieldLocalDate = 			java.time.LocalDate.of(2055, 5, 5);										
		o_flr.FieldLocalDateTime = 		de.forestj.lib.Helper.fromISO8601UTC("2055-05-05T09:04:08Z");				
		o_flr.FieldByteCol = 			(byte)8;																	
		o_flr.FieldFloatCol = 			4390.24f;																	
		o_flr.FieldDoubleCol = 			4726.24936d;																
		o_flr.FieldDecimal = 			new java.math.BigDecimal("19.38838036399012");							
		o_flr.FieldBool = 				false;																		
		o_flr.FieldText2 = 				"So unreserved do interested incr";										
		o_flr.FieldShortText2 = 		"n Eight ";
		
		o_stack.addFixedLengthRecord(i_recordNumber++, o_flr);
		
		o_flr = new FixedLengthRecordData();
		o_flr.FieldId = 				6;																			
		o_flr.FieldUUID = 				"9fbac7dd-af73-4052-bf93-10762d7966bd";									
		o_flr.FieldShortText = 			" 9 8 7 6 5 4 3 2";														
		o_flr.FieldText = 				"decisively nay man yet impression for contrasted remarkably. The";		
		o_flr.FieldSmallInt = 			(short)1006;																
		o_flr.FieldInt = 				421000000;																	
		o_flr.FieldBigInt = 			785633065136485210L;														
		o_flr.FieldTimestamp = 			o_datetimeFormat.parse("2066-06-06 12:08:16");								
		o_flr.FieldDate = 				o_dateFormat.parse("2066-06-06");											
		o_flr.FieldTime = 				o_timeFormat.parse("12:08:16");											
		o_flr.FieldLocalTime = 			java.time.LocalTime.of(11, 8, 16);											
		o_flr.FieldLocalDate = 			java.time.LocalDate.of(2066, 6, 6);										
		o_flr.FieldLocalDateTime = 		de.forestj.lib.Helper.fromISO8601UTC("2066-06-06T11:08:16Z");				
		o_flr.FieldByteCol = 			(byte)4;																	
		o_flr.FieldFloatCol = 			8799.24f;																	
		o_flr.FieldDoubleCol = 			-8922.053556d;																
		o_flr.FieldDecimal = 			new java.math.BigDecimal("73.12946012133004");							
		o_flr.FieldBool = 				false;																		
		o_flr.FieldText2 = 				"easing sentiments. Vanity day gi";										
		o_flr.FieldShortText2 = 		"Nine Ten";
		
		o_stack.addFixedLengthRecord(i_recordNumber++, o_flr);
		
		if ( (p_i_mode == 4) || (p_i_mode == 5) ) {
			FixedLengthRecordOtherData o_flrOther = new FixedLengthRecordOtherData();
			o_flrOther.FieldStringId = 			"00000B";
			o_flrOther.FieldInt = 				97322;																	
			o_flrOther.FieldTimestamp = 		o_datetimeFormat.parse("2044-04-04 08:32:04");								
			o_flrOther.FieldDate = 				o_dateFormat.parse("2044-04-04");											
			o_flrOther.FieldTime = 				o_timeFormat.parse("08:32:04");					
			o_flrOther.FieldShortText = 		" U V W X Y Z ";
			
			o_stack.addFixedLengthRecord(i_recordNumber++, o_flrOther);
		}
		
		if ( (p_i_mode == 2) || (p_i_mode == 3) || (p_i_mode == 4) || (p_i_mode == 5) ) {
			FixedLengthRecordGroupFooterData o_groupFooter = new FixedLengthRecordGroupFooterData();
			o_groupFooter.FieldAmountRecords = (p_i_mode >= 4) ? ((p_i_mode == 4) ? 6 : 7) : 3;
			o_groupFooter.FieldSumInt = ((p_i_mode == 4) || (p_i_mode == 5)) ? 766737533 : 766660500;
			
			o_stack.setGroupFooter(o_groupFooter);
			
			p_o_flrFile.addStack(i_stackNumber++, o_stack);
			o_stack = p_o_flrFile.createNewStack();
			i_recordNumber = 0;
		}
		
		if ( (p_i_mode == 1) || (p_i_mode == 3) || (p_i_mode == 4) || (p_i_mode == 5) ) {
			if (p_i_mode == 1) {
				p_o_flrFile.addStack(i_stackNumber++, o_stack);
				o_stack = p_o_flrFile.createNewStack();
				i_recordNumber = 0;
			}
			
			FixedLengthRecordGroupHeaderData o_groupHeader = new FixedLengthRecordGroupHeaderData();
			o_groupHeader.FieldCustomerNumber = 132;
			o_groupHeader.FieldDate = java.time.LocalDate.of(2033, 6, 3);
			o_groupHeader.FieldDoubleWithSeparator = 453665.357896d;
			
			o_stack.setGroupHeader(o_groupHeader);
		}
		
		o_flr = new FixedLengthRecordData();
		o_flr.FieldId = 				7;																			
		o_flr.FieldUUID = 				null;									
		o_flr.FieldShortText = 			null;														
		o_flr.FieldText = 				null;		
		o_flr.FieldSmallInt = 			(short)0;																
		o_flr.FieldInt = 				0;																	
		o_flr.FieldBigInt = 			0L;														
		o_flr.FieldTimestamp = 			null;								
		o_flr.FieldDate = 				null;											
		o_flr.FieldTime = 				null;											
		o_flr.FieldLocalTime = 			null;											
		o_flr.FieldLocalDate = 			null;										
		o_flr.FieldLocalDateTime = 		null;				
		o_flr.FieldByteCol = 			(byte)0;																	
		o_flr.FieldFloatCol = 			0.0f;																	
		o_flr.FieldDoubleCol = 			0.0d;																
		o_flr.FieldDecimal = 			new java.math.BigDecimal("0.0");							
		o_flr.FieldBool = 				false;																		
		o_flr.FieldText2 = 				null;										
		o_flr.FieldShortText2 = 		null;
		
		o_stack.addFixedLengthRecord(i_recordNumber++, o_flr);
		
		if ( (p_i_mode == 4) || (p_i_mode == 5) ) {
			FixedLengthRecordAnotherData o_flrAnother = new FixedLengthRecordAnotherData();
			o_flrAnother.FieldStringId = 			"00000A";
			o_flrAnother.FieldFloatCol = 			4390.24f;																	
			o_flrAnother.FieldDoubleCol = 			3585.195292d;																
			o_flrAnother.FieldDecimal = 			new java.math.BigDecimal("67.58769685598953");							
			o_flrAnother.FieldInt = 				13598;																	
			
			o_stack.addFixedLengthRecord(i_recordNumber++, o_flrAnother);
		}
		
		if ( (p_i_mode == 4) || (p_i_mode == 5) ) {
			FixedLengthRecordAnotherData o_flrAnother = new FixedLengthRecordAnotherData();
			o_flrAnother.FieldStringId = 			"00000B";
			o_flrAnother.FieldFloatCol = 			0.0f;																	
			o_flrAnother.FieldDoubleCol = 			0.0d;																
			o_flrAnother.FieldDecimal = 			new java.math.BigDecimal("0.0");							
			o_flrAnother.FieldInt = 				0;																	
			
			o_stack.addFixedLengthRecord(i_recordNumber++, o_flrAnother);
		}
		
		o_flr = new FixedLengthRecordData();
		o_flr.FieldId = 				8;																			
		o_flr.FieldUUID = 				"1babc8e1-e8c7-4ad4-bf4d-61bde1838c6b";									
		o_flr.FieldShortText = 			"Java Development";														
		o_flr.FieldText = 				"spoke happy for you are out. Fertile how old address did showing";		
		o_flr.FieldSmallInt = 			(short)1007;																
		o_flr.FieldInt = 				301010101;																	
		o_flr.FieldBigInt = 			779589670663214588L;														
		o_flr.FieldTimestamp = 			o_datetimeFormat.parse("2077-07-07 14:16:32");								
		o_flr.FieldDate = 				o_dateFormat.parse("2077-07-07");											
		o_flr.FieldTime = 				o_timeFormat.parse("14:16:32");											
		o_flr.FieldLocalTime = 			java.time.LocalTime.of(13, 16, 32);											
		o_flr.FieldLocalDate = 			java.time.LocalDate.of(2077, 7, 7);										
		o_flr.FieldLocalDateTime = 		de.forestj.lib.Helper.fromISO8601UTC("2077-07-07T13:16:32Z");				
		o_flr.FieldByteCol = 			(byte)2;																	
		o_flr.FieldFloatCol = 			3996.24f;																	
		o_flr.FieldDoubleCol = 			-6766.160668d;																
		o_flr.FieldDecimal = 			new java.math.BigDecimal("240.35392490677043");							
		o_flr.FieldBool = 				false;																		
		o_flr.FieldText2 = 				"ving points within six not law. ";										
		o_flr.FieldShortText2 = 		"Eleven T";
		
		o_stack.addFixedLengthRecord(i_recordNumber++, o_flr);
		
		if ( (p_i_mode == 4) || (p_i_mode == 5) ) {
			FixedLengthRecordOtherData o_flrOther = new FixedLengthRecordOtherData();
			o_flrOther.FieldStringId = 			"00000A";
			o_flrOther.FieldInt = 				36582;																	
			o_flrOther.FieldTimestamp = 		o_datetimeFormat.parse("2055-05-05 10:04:08");								
			o_flrOther.FieldDate = 				o_dateFormat.parse("2055-05-05");											
			o_flrOther.FieldTime = 				o_timeFormat.parse("10:04:08");					
			o_flrOther.FieldShortText = 		"a b c d e f g";
			
			o_stack.addFixedLengthRecord(i_recordNumber++, o_flrOther);
		}
		
		if ( (p_i_mode == 4) || (p_i_mode == 5) ) {
			FixedLengthRecordOtherData o_flrOther = new FixedLengthRecordOtherData();
			o_flrOther.FieldStringId = 			"00000B";
			o_flrOther.FieldInt = 				0;																	
			o_flrOther.FieldTimestamp = 		null;								
			o_flrOther.FieldDate = 				null;											
			o_flrOther.FieldTime = 				null;					
			o_flrOther.FieldShortText = 		null;
			
			o_stack.addFixedLengthRecord(i_recordNumber++, o_flrOther);
		}
		
		o_flr = new FixedLengthRecordData();
		o_flr.FieldId = 				9;																			
		o_flr.FieldUUID = 				"e979c923-654a-44b7-a70a-099942c9a834";									
		o_flr.FieldShortText = 			".NET Development";														
		o_flr.FieldText = 				"because sitting replied six. Had arose guest visit going off.   ";		
		o_flr.FieldSmallInt = 			(short)1008;																
		o_flr.FieldInt = 				220202020;																	
		o_flr.FieldBigInt = 			846022040001245036L;														
		o_flr.FieldTimestamp = 			o_datetimeFormat.parse("2088-08-08 16:32:04");								
		o_flr.FieldDate = 				o_dateFormat.parse("2088-08-08");											
		o_flr.FieldTime = 				o_timeFormat.parse("16:32:04");											
		o_flr.FieldLocalTime = 			java.time.LocalTime.of(15, 32, 4);											
		o_flr.FieldLocalDate = 			java.time.LocalDate.of(2088, 8, 8);										
		o_flr.FieldLocalDateTime = 		de.forestj.lib.Helper.fromISO8601UTC("2088-08-08T15:32:04Z");				
		o_flr.FieldByteCol = 			(byte)1;																	
		o_flr.FieldFloatCol = 			3090.56f;																	
		o_flr.FieldDoubleCol = 			9461.026778d;																
		o_flr.FieldDecimal = 			new java.math.BigDecimal("80.70784981354087");							
		o_flr.FieldBool = 				true;																		
		o_flr.FieldText2 = 				"Few impression difficulty his us";										
		o_flr.FieldShortText2 = 		"welve Th";
		
		o_stack.addFixedLengthRecord(i_recordNumber++, o_flr);
		
		if ( (p_i_mode == 4) || (p_i_mode == 5) ) {
			FixedLengthRecordOtherData o_flrOther = new FixedLengthRecordOtherData();
			o_flrOther.FieldStringId = 			"00000C";
			o_flrOther.FieldInt = 				22558;																	
			o_flrOther.FieldTimestamp = 		o_datetimeFormat.parse("2066-06-06 12:08:16");								
			o_flrOther.FieldDate = 				o_dateFormat.parse("2066-06-06");											
			o_flrOther.FieldTime = 				o_timeFormat.parse("12:08:16");					
			o_flrOther.FieldShortText = 		" h i j k l m ";
			
			o_stack.addFixedLengthRecord(i_recordNumber++, o_flrOther);
		}
		
		if (p_i_mode == 5) {
            FixedLengthRecordDataWithSubtypes o_flrWithSubtypes = new FixedLengthRecordDataWithSubtypes();
            o_flrWithSubtypes.FieldDate = o_dateFormat.parse("2030-03-03");
            o_flrWithSubtypes.FieldLastNotice = "Last notice   3";
            
            o_flrWithSubtypes.FieldListOnes = new java.util.ArrayList<FixedLengthRecordSubtypeOne>();
            	FixedLengthRecordSubtypeOne o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 111;
	            	o_one.FieldShortText = "Test1     ";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
	        	o_one = new FixedLengthRecordSubtypeOne();
		        	o_one.FieldThreeDigitId = 222;
		        	o_one.FieldShortText = "Test2     ";
		        	o_flrWithSubtypes.FieldListOnes.add(o_one);
		    	o_one = new FixedLengthRecordSubtypeOne();
		    		o_one.FieldThreeDigitId = 333;
		    		o_one.FieldShortText = "Test3     ";
		    		o_flrWithSubtypes.FieldListOnes.add(o_one);
				o_one = new FixedLengthRecordSubtypeOne();
					o_one.FieldThreeDigitId = 444;
	            	o_one.FieldShortText = "Test4     ";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
	        	o_one = new FixedLengthRecordSubtypeOne();
	        		o_one.FieldThreeDigitId = 555;
	            	o_one.FieldShortText = "Test5     ";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	        		o_one.FieldThreeDigitId = 666;
	            	o_one.FieldShortText = "Test6     ";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
	        	o_one = new FixedLengthRecordSubtypeOne();
	        		o_one.FieldThreeDigitId = 7;
	            	o_one.FieldShortText = "     Test7";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 8;
	            	o_one.FieldShortText = "     Test8";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 9;
	            	o_one.FieldShortText = "     Test9";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 10;
	            	o_one.FieldShortText = "    Test10";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 11;
	            	o_one.FieldShortText = "    Test11";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            
            o_flrWithSubtypes.FieldListTwos = new java.util.ArrayList<FixedLengthRecordSubtypeTwo>();
            	FixedLengthRecordSubtypeTwo o_two = new FixedLengthRecordSubtypeTwo();
	            	o_two.FieldDoubleValue = 13579.246d;
	        		o_flrWithSubtypes.FieldListTwos.add(o_two);
	        	o_two = new FixedLengthRecordSubtypeTwo();
        			o_two.FieldDoubleValue = -951.753d;
            		o_flrWithSubtypes.FieldListTwos.add(o_two);
            	o_two = new FixedLengthRecordSubtypeTwo();
	        		o_two.FieldDoubleValue = -4623527.958d;
	        		o_flrWithSubtypes.FieldListTwos.add(o_two);
            	o_two = new FixedLengthRecordSubtypeTwo();
	        		o_two.FieldDoubleValue = -66538.974d;
	        		o_flrWithSubtypes.FieldListTwos.add(o_two);
                            
            o_stack.addFixedLengthRecord(i_recordNumber++, o_flrWithSubtypes);
            
            o_flrWithSubtypes = new FixedLengthRecordDataWithSubtypes();
            o_flrWithSubtypes.FieldDate = o_dateFormat.parse("2040-04-04");
            o_flrWithSubtypes.FieldLastNotice = "Last notice   4";
            
            o_flrWithSubtypes.FieldListOnes = new java.util.ArrayList<FixedLengthRecordSubtypeOne>();
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 111;
	            	o_one.FieldShortText = "Test1     ";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
	        	o_one = new FixedLengthRecordSubtypeOne();
		        	o_one.FieldThreeDigitId = 0;
		        	o_one.FieldShortText = null;
		        	o_flrWithSubtypes.FieldListOnes.add(o_one);
		    	o_one = new FixedLengthRecordSubtypeOne();
		    		o_one.FieldThreeDigitId = 333;
		    		o_one.FieldShortText = "Test3     ";
		    		o_flrWithSubtypes.FieldListOnes.add(o_one);
				o_one = new FixedLengthRecordSubtypeOne();
					o_one.FieldThreeDigitId = 0;
		        	o_one.FieldShortText = null;
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
	        	o_one = new FixedLengthRecordSubtypeOne();
	        		o_one.FieldThreeDigitId = 555;
	            	o_one.FieldShortText = "Test5     ";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 0;
		        	o_one.FieldShortText = null;
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
	        	o_one = new FixedLengthRecordSubtypeOne();
	        		o_one.FieldThreeDigitId = 7;
	            	o_one.FieldShortText = "     Test7";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 0;
		        	o_one.FieldShortText = null;
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 9;
	            	o_one.FieldShortText = "     Test9";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 0;
		        	o_one.FieldShortText = null;
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            	o_one = new FixedLengthRecordSubtypeOne();
	            	o_one.FieldThreeDigitId = 11;
	            	o_one.FieldShortText = "    Test11";
	            	o_flrWithSubtypes.FieldListOnes.add(o_one);
            
            o_flrWithSubtypes.FieldListTwos = new java.util.ArrayList<FixedLengthRecordSubtypeTwo>();
            	o_two = new FixedLengthRecordSubtypeTwo();
	            	o_two.FieldDoubleValue = 13579.246d;
	        		o_flrWithSubtypes.FieldListTwos.add(o_two);
	        	o_two = new FixedLengthRecordSubtypeTwo();
        			o_two.FieldDoubleValue = 0.0d;
            		o_flrWithSubtypes.FieldListTwos.add(o_two);
            	o_two = new FixedLengthRecordSubtypeTwo();
	        		o_two.FieldDoubleValue = -4623527.958d;
	        		o_flrWithSubtypes.FieldListTwos.add(o_two);
            	o_two = new FixedLengthRecordSubtypeTwo();
	        		o_two.FieldDoubleValue = 0.0d;
	        		o_flrWithSubtypes.FieldListTwos.add(o_two);
                            
            o_stack.addFixedLengthRecord(i_recordNumber++, o_flrWithSubtypes);
        }
		
		if ( (p_i_mode == 2) || (p_i_mode == 3) || (p_i_mode == 4) || (p_i_mode == 5) ) {
			FixedLengthRecordGroupFooterData o_groupFooter = new FixedLengthRecordGroupFooterData();
			o_groupFooter.FieldAmountRecords = (p_i_mode >= 4) ? ((p_i_mode == 4) ? 8 : 10) : 2;
			o_groupFooter.FieldSumInt = ((p_i_mode == 4) || (p_i_mode == 5)) ? 260642429 : 260606060;
			
			o_stack.setGroupFooter(o_groupFooter);
		}
		
		p_o_flrFile.addStack(i_stackNumber++, o_stack);
	}
	@Test
	public void testFixedLengthRecordStandardTransposeMethods() {
		try {
			de.forestj.lib.LoggingConfig.initiateTestLogging();
			
			/* String */
			
			assertEquals(
				"This is just a test This is just a test This is just a test This is just a test",
				(String)de.forestj.lib.io.StandardTransposeMethods.TransposeString("This is just a test This is just a test This is just a test This is just a test"),
				"FixedLengthRecord StandardTransposeMethods TransposeString(String) method does not return expected value"
			);
			
			assertEquals(
				"This is just a test This is just a ",
				de.forestj.lib.io.StandardTransposeMethods.TransposeString("This is just a test This is just a test This is just a test This is just a test", 35),
				"FixedLengthRecord StandardTransposeMethods TransposeString(Object, Integer) method does not return expected value"
			);
			
			/* Boolean */
			
			assertEquals(
				"true",
				String.valueOf(de.forestj.lib.io.StandardTransposeMethods.TransposeBoolean("true")),
				"FixedLengthRecord StandardTransposeMethods TransposeBoolean(String) method does not return expected value"
			);
			
			assertEquals(
				"true",
				String.valueOf(de.forestj.lib.io.StandardTransposeMethods.TransposeBoolean("1")),
				"FixedLengthRecord StandardTransposeMethods TransposeBoolean(String) method does not return expected value"
			);
			
			assertEquals(
				"true",
				String.valueOf(de.forestj.lib.io.StandardTransposeMethods.TransposeBoolean("y")),
				"FixedLengthRecord StandardTransposeMethods TransposeBoolean(String) method does not return expected value"
			);
			
			assertEquals(
				"true",
				String.valueOf(de.forestj.lib.io.StandardTransposeMethods.TransposeBoolean("j")),
				"FixedLengthRecord StandardTransposeMethods TransposeBoolean(String) method does not return expected value"
			);
			
			assertEquals(
				"false",
				String.valueOf(de.forestj.lib.io.StandardTransposeMethods.TransposeBoolean("0")),
				"FixedLengthRecord StandardTransposeMethods TransposeBoolean(String) method does not return expected value"
			);
			
			assertEquals(
				"false",
				String.valueOf(de.forestj.lib.io.StandardTransposeMethods.TransposeBoolean("test")),
				"FixedLengthRecord StandardTransposeMethods TransposeBoolean(String) method does not return expected value"
			);
			
			assertEquals(
				"1",
				de.forestj.lib.io.StandardTransposeMethods.TransposeBoolean(true, 1),
				"FixedLengthRecord StandardTransposeMethods TransposeBoolean(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"0",
				de.forestj.lib.io.StandardTransposeMethods.TransposeBoolean(false, 1),
				"FixedLengthRecord StandardTransposeMethods TransposeBoolean(Object, Integer) method does not return expected value"
			);
			
			/* Byte */
			
			assertEquals(
				(byte)1,
				(byte)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeByte("000001"),
				"FixedLengthRecord StandardTransposeMethods TransposeByte(String) method does not return expected value"
			);
			
			assertEquals(
				(byte)127,
				(byte)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeByte("0127"),
				"FixedLengthRecord StandardTransposeMethods TransposeByte(String) method does not return expected value"
			);
			
			assertEquals(
				(byte)-1,
				(byte)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeByteWithSign("-00001"),
				"FixedLengthRecord StandardTransposeMethods TransposeByteWithSign(String) method does not return expected value"
			);
			
			assertEquals(
				(byte)127,
				(byte)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeByteWithSign("+000127"),
				"FixedLengthRecord StandardTransposeMethods TransposeByteWithSign(String) method does not return expected value"
			);
			
			assertEquals(
				(byte)-112,
				(byte)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeByteWithSign("-000112"),
				"FixedLengthRecord StandardTransposeMethods TransposeByteWithSign(String) method does not return expected value"
			);
			
			assertEquals(
				"000001",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeByte((byte)1, 6),
				"FixedLengthRecord StandardTransposeMethods TransposeByte(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"0000127",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeByte((byte)127, 7),
				"FixedLengthRecord StandardTransposeMethods TransposeByte(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"-0001",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeByteWithSign((byte)-1, 5),
				"FixedLengthRecord StandardTransposeMethods TransposeByteWithSign(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"+00127",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeByteWithSign((byte)127, 6),
				"FixedLengthRecord StandardTransposeMethods TransposeByteWithSign(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"-000112",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeByteWithSign((byte)-112, 7),
				"FixedLengthRecord StandardTransposeMethods TransposeByteWithSign(Object, Integer) method does not return expected value"
			);
			
			/* Short */
			
			assertEquals(
				(short)1,
				(short)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeShort("000001"),
				"FixedLengthRecord StandardTransposeMethods TransposeShort(String) method does not return expected value"
			);
			
			assertEquals(
				(short)32767,
				(short)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeShort("032767"),
				"FixedLengthRecord StandardTransposeMethods TransposeShort(String) method does not return expected value"
			);
			
			assertEquals(
				(short)-1,
				(short)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeShortWithSign("-00001"),
				"FixedLengthRecord StandardTransposeMethods TransposeShortWithSign(String) method does not return expected value"
			);
			
			assertEquals(
				(short)32767,
				(short)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeShortWithSign("+032767"),
				"FixedLengthRecord StandardTransposeMethods TransposeShortWithSign(String) method does not return expected value"
			);
			
			assertEquals(
				(short)-16348,
				(short)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeShortWithSign("-00016348"),
				"FixedLengthRecord StandardTransposeMethods TransposeShortWithSign(String) method does not return expected value"
			);
			
			assertEquals(
				"000001",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeShort((short)1, 6),
				"FixedLengthRecord StandardTransposeMethods TransposeShort(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"0032767",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeShort((short)32767, 7),
				"FixedLengthRecord StandardTransposeMethods TransposeShort(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"-0001",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeShortWithSign((short)-1, 5),
				"FixedLengthRecord StandardTransposeMethods TransposeShortWithSign(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"+032767",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeShortWithSign((short)32767, 7),
				"FixedLengthRecord StandardTransposeMethods TransposeShortWithSign(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"-00016348",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeShortWithSign((short)-16348, 9),
				"FixedLengthRecord StandardTransposeMethods TransposeShortWithSign(Object, Integer) method does not return expected value"
			);
			
			/* Integer */
			
			assertEquals(
				1,
				(int)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeInteger("000001"),
				"FixedLengthRecord StandardTransposeMethods TransposeInteger(String) method does not return expected value"
			);
			
			assertEquals(
				2147483647,
				(int)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeInteger("002147483647"),
				"FixedLengthRecord StandardTransposeMethods TransposeInteger(String) method does not return expected value"
			);
			
			assertEquals(
				-1,
				(int)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeIntegerWithSign("-00001"),
				"FixedLengthRecord StandardTransposeMethods TransposeIntegerWithSign(String) method does not return expected value"
			);
			
			assertEquals(
				2147483647,
				(int)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeIntegerWithSign("+02147483647"),
				"FixedLengthRecord StandardTransposeMethods TransposeIntegerWithSign(String) method does not return expected value"
			);
			
			assertEquals(
				-1073741823,
				(int)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeIntegerWithSign("-0001073741823"),
				"FixedLengthRecord StandardTransposeMethods TransposeIntegerWithSign(String) method does not return expected value"
			);
			
			assertEquals(
				"000001",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeInteger(1, 6),
				"FixedLengthRecord StandardTransposeMethods TransposeInteger(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"002147483647",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeInteger(2147483647, 12),
				"FixedLengthRecord StandardTransposeMethods TransposeInteger(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"-0001",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeIntegerWithSign(-1, 5),
				"FixedLengthRecord StandardTransposeMethods TransposeIntegerWithSign(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"+02147483647",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeIntegerWithSign(2147483647, 12),
				"FixedLengthRecord StandardTransposeMethods TransposeIntegerWithSign(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"-0001073741823",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeIntegerWithSign(-1073741823, 14),
				"FixedLengthRecord StandardTransposeMethods TransposeIntegerWithSign(Object, Integer) method does not return expected value"
			);
			
			/* Long */
			
			assertEquals(
				(long)1,
				(long)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeLong("000001"),
				"FixedLengthRecord StandardTransposeMethods TransposeLong(String) method does not return expected value"
			);
			
			assertEquals(
				(long)9223372036854775807L,
				(long)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeLong("09223372036854775807"),
				"FixedLengthRecord StandardTransposeMethods TransposeLong(String) method does not return expected value"
			);
			
			assertEquals(
				(long)-1,
				(long)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeLongWithSign("-00001"),
				"FixedLengthRecord StandardTransposeMethods TransposeLongWithSign(String) method does not return expected value"
			);
			
			assertEquals(
				(long)9223372036854775807L,
				(long)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeLongWithSign("+009223372036854775807"),
				"FixedLengthRecord StandardTransposeMethods TransposeLongWithSign(String) method does not return expected value"
			);
			
			assertEquals(
				(long)-4611686018427387903L,
				(long)de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeLongWithSign("-0004611686018427387903"),
				"FixedLengthRecord StandardTransposeMethods TransposeLongWithSign(String) method does not return expected value"
			);
			
			assertEquals(
				"000001",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeLong((long)1, 6),
				"FixedLengthRecord StandardTransposeMethods TransposeLong(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"0009223372036854775807",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeLong((long)9223372036854775807L, 22),
				"FixedLengthRecord StandardTransposeMethods TransposeLong(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"-0001",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeLongWithSign((long)-1, 5),
				"FixedLengthRecord StandardTransposeMethods TransposeLongWithSign(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"+09223372036854775807",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeLongWithSign((long)9223372036854775807L, 21),
				"FixedLengthRecord StandardTransposeMethods TransposeLongWithSign(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"-0004611686018427387903",
				de.forestj.lib.io.StandardTransposeMethods.Numbers.TransposeLongWithSign((long)-4611686018427387903L, 23),
				"FixedLengthRecord StandardTransposeMethods TransposeLongWithSign(Object, Integer) method does not return expected value"
			);
			
			/* util date */
			
			assertEquals(
				new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("14.03.2020 06:02:03"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_ISO8601("2020-03-14T05:02:03Z"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_ISO8601(String) method does not return expected value"
			);
			
			assertEquals(
				new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("14.03.2020 06:02:03"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_ISO8601("2020-03-14 05:02:03Z"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_ISO8601(String) method does not return expected value"
			);
			
			assertEquals(
				"2020-03-14 05:02:03Z",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_ISO8601_NoSeparator(new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("14.03.2020 06:02:03"), 20),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_ISO8601(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"2020-03-14T05:02:03Z",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_ISO8601(new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("14.03.2020 06:02:03"), 20),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_ISO8601(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"2020-03-14T05:02:03Z",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_ISO8601(new java.text.SimpleDateFormat("dd.MM.yyyy'T'HH:mm:ss").parse("14.03.2020T06:02:03"), 20),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_ISO8601(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("14.03.2020 06:02:03"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_RFC1123("Sat, 14 Mar 2020 05:02:03 GMT"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_RFC1123(String) method does not return expected value"
			);
			
			assertEquals(
				"Sat, 14 Mar 2020 15:02:03 GMT",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_RFC1123(new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("14.03.2020 16:02:03"), 29),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_RFC1123(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("01.01.2011 02:04:08"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_yyyymmddhhiiss("20110101020408"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_yyyymmddhhiiss(String) method does not return expected value"
			);
			
			assertEquals(
				"20110101020408",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_yyyymmddhhiiss(new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("01.01.2011 02:04:08"), 14),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_yyyymmddhhiiss(Object, Integer) method does not return expected value"
			);
		
			assertEquals(
				new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("01.10.2011 02:04:08"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_ddmmyyyyhhiiss_Dot("01.10.2011 02:04:08"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_ddmmyyyyhhiiss_Dot(String) method does not return expected value"
			);
			
			assertEquals(
				"01.10.2011 02:04:08",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_ddmmyyyyhhiiss_Dot(new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("01.10.2011 02:04:08"), 19),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_ddmmyyyyhhiiss_Dot(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("01/10/2011 02:04:08"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_ddmmyyyyhhiiss_Slash("01/10/2011 02:04:08"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_ddmmyyyyhhiiss_Slash(String) method does not return expected value"
			);
			
			assertEquals(
				"01/10/2011 02:04:08",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_ddmmyyyyhhiiss_Slash(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("01/10/2011 02:04:08"), 19),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_ddmmyyyyhhiiss_Slash(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				new java.text.SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse("2011.10.01 02:04:08"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_yyyymmddhhiiss_Dot("2011.10.01 02:04:08"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_yyyymmddhhiiss_Dot(String) method does not return expected value"
			);
			
			assertEquals(
				"2011.10.01 02:04:08",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_yyyymmddhhiiss_Dot(new java.text.SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse("2011.10.01 02:04:08"), 19),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_yyyymmddhhiiss_Dot(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("2011/10/01 02:04:08"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_yyyymmddhhiiss_Slash("2011/10/01 02:04:08"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_yyyymmddhhiiss_Slash(String) method does not return expected value"
			);
			
			assertEquals(
				"2011/10/01 02:04:08",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_yyyymmddhhiiss_Slash(new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("2011/10/01 02:04:08"), 19),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_yyyymmddhhiiss_Slash(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				new java.text.SimpleDateFormat("MM.dd.yyyy HH:mm:ss").parse("10.01.2011 02:04:08"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_mmddyyyyhhiiss_Dot("10.01.2011 02:04:08"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_mmddyyyyhhiiss_Dot(String) method does not return expected value"
			);
			
			assertEquals(
				"10.01.2011 02:04:08",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_mmddyyyyhhiiss_Dot(new java.text.SimpleDateFormat("MM.dd.yyyy HH:mm:ss").parse("10.01.2011 02:04:08"), 19),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_mmddyyyyhhiiss_Dot(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("10/01/2011 02:04:08"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_mmddyyyyhhiiss_Slash("10/01/2011 02:04:08"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_mmddyyyyhhiiss_Slash(String) method does not return expected value"
			);
			
			assertEquals(
				"10/01/2011 02:04:08",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_mmddyyyyhhiiss_Slash(new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("10/01/2011 02:04:08"), 19),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_mmddyyyyhhiiss_Slash(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				new java.text.SimpleDateFormat("dd.MM.yyyy").parse("01.01.2011"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_yyyymmdd("20110101"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_yyyymmdd(String) method does not return expected value"
			);
			
			assertEquals(
				"20110101",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_yyyymmdd(new java.text.SimpleDateFormat("dd.MM.yyyy").parse("01.01.2011"), 8),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_yyyymmdd(Object, Integer) method does not return expected value"
			);
		
			assertEquals(
				new java.text.SimpleDateFormat("dd.MM.yyyy").parse("01.10.2011"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_ddmmyyyy_Dot("01.10.2011"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_ddmmyyyy_Dot(String) method does not return expected value"
			);
			
			assertEquals(
				"01.10.2011",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_ddmmyyyy_Dot(new java.text.SimpleDateFormat("dd.MM.yyyy").parse("01.10.2011"), 10),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_ddmmyyyy_Dot(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				new java.text.SimpleDateFormat("dd/MM/yyyy").parse("01/10/2011"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_ddmmyyyy_Slash("01/10/2011"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_ddmmyyyy_Slash(String) method does not return expected value"
			);
			
			assertEquals(
				"01/10/2011",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_ddmmyyyy_Slash(new java.text.SimpleDateFormat("dd/MM/yyyy").parse("01/10/2011"), 10),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_ddmmyyyy_Slash(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				new java.text.SimpleDateFormat("yyyy.MM.dd").parse("2011.10.01"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_yyyymmdd_Dot("2011.10.01"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_yyyymmdd_Dot(String) method does not return expected value"
			);
			
			assertEquals(
				"2011.10.01",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_yyyymmdd_Dot(new java.text.SimpleDateFormat("yyyy.MM.dd").parse("2011.10.01"), 10),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_yyyymmdd_Dot(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				new java.text.SimpleDateFormat("yyyy/MM/dd").parse("2011/10/01"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_yyyymmdd_Slash("2011/10/01"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_yyyymmdd_Slash(String) method does not return expected value"
			);
			
			assertEquals(
				"2011/10/01",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_yyyymmdd_Slash(new java.text.SimpleDateFormat("yyyy/MM/dd").parse("2011/10/01"), 10),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_yyyymmdd_Slash(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				new java.text.SimpleDateFormat("MM.dd.yyyy").parse("10.01.2011"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_mmddyyyy_Dot("10.01.2011"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_mmddyyyy_Dot(String) method does not return expected value"
			);
			
			assertEquals(
				"10.01.2011",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_mmddyyyy_Dot(new java.text.SimpleDateFormat("MM.dd.yyyy").parse("10.01.2011"), 10),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_mmddyyyy_Dot(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				new java.text.SimpleDateFormat("MM/dd/yyyy").parse("10/01/2011"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_mmddyyyy_Slash("10/01/2011"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_mmddyyyy_Slash(String) method does not return expected value"
			);
			
			assertEquals(
				"10/01/2011",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_mmddyyyy_Slash(new java.text.SimpleDateFormat("MM/dd/yyyy").parse("10/01/2011"), 10),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_mmddyyyy_Slash(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				new java.text.SimpleDateFormat("HHmmss").parse("020304"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_hhiiss("020304"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_hhiiss(String) method does not return expected value"
			);
			
			assertEquals(
				"020304",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_hhiiss(new java.text.SimpleDateFormat("HHmmss").parse("020304"), 6),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_hhiiss(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				new java.text.SimpleDateFormat("HHmm").parse("0203"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_hhii("0203"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_hhii(String) method does not return expected value"
			);
			
			assertEquals(
				"0203",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_hhii(new java.text.SimpleDateFormat("HHmm").parse("0203"), 4),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_hhii(Object, Integer) method does not return expected value"
			);
		
			assertEquals(
				new java.text.SimpleDateFormat("HH:mm:ss").parse("02:03:04"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_hhiiss_Colon("02:03:04"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_hhiiss_Colon(String) method does not return expected value"
			);
			
			assertEquals(
				"02:03:04",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_hhiiss_Colon(new java.text.SimpleDateFormat("HH:mm:ss").parse("02:03:04"), 8),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_hhiiss_Colon(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				new java.text.SimpleDateFormat("HH:mm").parse("02:03"),
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_hhii_Colon("02:03"),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_hhii_Colon(String) method does not return expected value"
			);
			
			assertEquals(
				"02:03",
				de.forestj.lib.io.StandardTransposeMethods.UtilDate.TransposeDate_hhii_Colon(new java.text.SimpleDateFormat("HH:mm").parse("02:03"), 5),
				"FixedLengthRecord StandardTransposeMethods TransposeDate_hhii_Colon(Object, Integer) method does not return expected value"
			);
			
			/* LocalDateTime */
			
			java.time.LocalDateTime o_localDateTime = java.time.LocalDateTime.of(2020, 03, 14, 06, 02, 03).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime();
		
			assertEquals(
					o_localDateTime,
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_ISO8601("2020-03-14T05:02:03Z"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_ISO8601(String) method does not return expected value"
			);
			
			assertEquals(
				o_localDateTime,
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_ISO8601("2020-03-14 05:02:03Z"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_ISO8601(String) method does not return expected value"
			);
			
			assertEquals(
				"2020-03-14T05:02:03Z",
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_ISO8601(o_localDateTime, 20),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_ISO8601(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				"2020-03-14T05:02:03Z",
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_ISO8601(o_localDateTime, 20),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_ISO8601(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				o_localDateTime,
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_RFC1123("Sat, 14 Mar 2020 05:02:03 GMT"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_RFC1123(String) method does not return expected value"
			);
			
			assertEquals(
				"Sat, 14 Mar 2020 15:02:03 GMT",
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_RFC1123(o_localDateTime.plusHours(10), 29),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_RFC1123(Object, Integer) method does not return expected value"
			);
			
			o_localDateTime = java.time.LocalDateTime.of(2011, 01, 01, 02, 04, 8).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime();
			
			assertEquals(
				o_localDateTime,
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_yyyymmddhhiiss("20110101020408"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_yyyymmddhhiiss(String) method does not return expected value"
			);
			
			assertEquals(
				"20110101020408",
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_yyyymmddhhiiss(o_localDateTime, 14),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_yyyymmddhhiiss(Object, Integer) method does not return expected value"
			);
		
			o_localDateTime = java.time.LocalDateTime.of(2011, 10, 01, 02, 04, 8).atZone(java.time.ZoneId.systemDefault()).withZoneSameInstant(java.time.ZoneId.of("Europe/Berlin")).toLocalDateTime();
		
			assertEquals(
				o_localDateTime,
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_ddmmyyyyhhiiss_Dot("01.10.2011 02:04:08"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_ddmmyyyyhhiiss_Dot(String) method does not return expected value"
			);
			
			assertEquals(
				"01.10.2011 02:04:08",
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_ddmmyyyyhhiiss_Dot(o_localDateTime, 19),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_ddmmyyyyhhiiss_Dot(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				o_localDateTime,
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_ddmmyyyyhhiiss_Slash("01/10/2011 02:04:08"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_ddmmyyyyhhiiss_Slash(String) method does not return expected value"
			);
			
			assertEquals(
				"01/10/2011 02:04:08",
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_ddmmyyyyhhiiss_Slash(o_localDateTime, 19),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_ddmmyyyyhhiiss_Slash(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				o_localDateTime,
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_yyyymmddhhiiss_Dot("2011.10.01 02:04:08"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_yyyymmddhhiiss_Dot(String) method does not return expected value"
			);
			
			assertEquals(
				"2011.10.01 02:04:08",
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_yyyymmddhhiiss_Dot(o_localDateTime, 19),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_yyyymmddhhiiss_Dot(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				o_localDateTime,
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_yyyymmddhhiiss_Slash("2011/10/01 02:04:08"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_yyyymmddhhiiss_Slash(String) method does not return expected value"
			);
			
			assertEquals(
				"2011/10/01 02:04:08",
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_yyyymmddhhiiss_Slash(o_localDateTime, 19),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_yyyymmddhhiiss_Slash(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				o_localDateTime,
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_mmddyyyyhhiiss_Dot("10.01.2011 02:04:08"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_mmddyyyyhhiiss_Dot(String) method does not return expected value"
			);
			
			assertEquals(
				"10.01.2011 02:04:08",
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_mmddyyyyhhiiss_Dot(o_localDateTime, 19),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_mmddyyyyhhiiss_Dot(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				o_localDateTime,
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_mmddyyyyhhiiss_Slash("10/01/2011 02:04:08"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_mmddyyyyhhiiss_Slash(String) method does not return expected value"
			);
			
			assertEquals(
				"10/01/2011 02:04:08",
				de.forestj.lib.io.StandardTransposeMethods.LocalDateTime.TransposeLocalDateTime_mmddyyyyhhiiss_Slash(o_localDateTime, 19),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDateTime_mmddyyyyhhiiss_Slash(Object, Integer) method does not return expected value"
			);
			
			/* LocalDate */
			
			java.time.LocalDate o_localDate = java.time.LocalDate.of(2011, 01, 01);
			
			assertEquals(
				o_localDate,
				de.forestj.lib.io.StandardTransposeMethods.LocalDate.TransposeLocalDate_yyyymmdd("20110101"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDate_yyyymmdd(String) method does not return expected value"
			);
			
			assertEquals(
				"20110101",
				de.forestj.lib.io.StandardTransposeMethods.LocalDate.TransposeLocalDate_yyyymmdd(o_localDate, 8),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDate_yyyymmdd(Object, Integer) method does not return expected value"
			);
		
			o_localDate = java.time.LocalDate.of(2011, 10, 01);
			
			assertEquals(
				o_localDate,
				de.forestj.lib.io.StandardTransposeMethods.LocalDate.TransposeLocalDate_ddmmyyyy_Dot("01.10.2011"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDate_ddmmyyyy_Dot(String) method does not return expected value"
			);
			
			assertEquals(
				"01.10.2011",
				de.forestj.lib.io.StandardTransposeMethods.LocalDate.TransposeLocalDate_ddmmyyyy_Dot(o_localDate, 10),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDate_ddmmyyyy_Dot(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				o_localDate,
				de.forestj.lib.io.StandardTransposeMethods.LocalDate.TransposeLocalDate_ddmmyyyy_Slash("01/10/2011"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDate_ddmmyyyy_Slash(String) method does not return expected value"
			);
			
			assertEquals(
				"01/10/2011",
				de.forestj.lib.io.StandardTransposeMethods.LocalDate.TransposeLocalDate_ddmmyyyy_Slash(o_localDate, 10),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDate_ddmmyyyy_Slash(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				o_localDate,
				de.forestj.lib.io.StandardTransposeMethods.LocalDate.TransposeLocalDate_yyyymmdd_Dot("2011.10.01"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDate_yyyymmdd_Dot(String) method does not return expected value"
			);
			
			assertEquals(
				"2011.10.01",
				de.forestj.lib.io.StandardTransposeMethods.LocalDate.TransposeLocalDate_yyyymmdd_Dot(o_localDate, 10),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDate_yyyymmdd_Dot(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				o_localDate,
				de.forestj.lib.io.StandardTransposeMethods.LocalDate.TransposeLocalDate_yyyymmdd_Slash("2011/10/01"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDate_yyyymmdd_Slash(String) method does not return expected value"
			);
			
			assertEquals(
				"2011/10/01",
				de.forestj.lib.io.StandardTransposeMethods.LocalDate.TransposeLocalDate_yyyymmdd_Slash(o_localDate, 10),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDate_yyyymmdd_Slash(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				o_localDate,
				de.forestj.lib.io.StandardTransposeMethods.LocalDate.TransposeLocalDate_mmddyyyy_Dot("10.01.2011"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDate_mmddyyyy_Dot(String) method does not return expected value"
			);
			
			assertEquals(
				"10.01.2011",
				de.forestj.lib.io.StandardTransposeMethods.LocalDate.TransposeLocalDate_mmddyyyy_Dot(o_localDate, 10),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDate_mmddyyyy_Dot(Object, Integer) method does not return expected value"
			);
			
			assertEquals(
				o_localDate,
				de.forestj.lib.io.StandardTransposeMethods.LocalDate.TransposeLocalDate_mmddyyyy_Slash("10/01/2011"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDate_mmddyyyy_Slash(String) method does not return expected value"
			);
			
			assertEquals(
				"10/01/2011",
				de.forestj.lib.io.StandardTransposeMethods.LocalDate.TransposeLocalDate_mmddyyyy_Slash(o_localDate, 10),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalDate_mmddyyyy_Slash(Object, Integer) method does not return expected value"
			);
				
			/* LocalTime */
			
			java.time.LocalTime o_localTime = java.time.LocalTime.of(2, 3, 4);
			
			assertEquals(
				o_localTime,
				de.forestj.lib.io.StandardTransposeMethods.LocalTime.TransposeLocalTime_hhiiss("020304"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalTime_hhiiss(String) method does not return expected value"
			);
			
			assertEquals(
				"020304",
				de.forestj.lib.io.StandardTransposeMethods.LocalTime.TransposeLocalTime_hhiiss(o_localTime, 6),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalTime_hhiiss(Object, Integer) method does not return expected value"
			);
			
			o_localTime = java.time.LocalTime.of(2, 3);
			
			assertEquals(
				o_localTime,
				de.forestj.lib.io.StandardTransposeMethods.LocalTime.TransposeLocalTime_hhii("0203"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalTime_hhii(String) method does not return expected value"
			);
			
			assertEquals(
				"0203",
				de.forestj.lib.io.StandardTransposeMethods.LocalTime.TransposeLocalTime_hhii(o_localTime, 4),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalTime_hhii(Object, Integer) method does not return expected value"
			);
		
			o_localTime = java.time.LocalTime.of(2, 3, 4);
			
			assertEquals(
				o_localTime,
				de.forestj.lib.io.StandardTransposeMethods.LocalTime.TransposeLocalTime_hhiiss_Colon("02:03:04"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalTime_hhiiss_Colon(String) method does not return expected value"
			);
			
			assertEquals(
				"02:03:04",
				de.forestj.lib.io.StandardTransposeMethods.LocalTime.TransposeLocalTime_hhiiss_Colon(o_localTime, 8),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalTime_hhiiss_Colon(Object, Integer) method does not return expected value"
			);
			
			o_localTime = java.time.LocalTime.of(2, 3);
			
			assertEquals(
				o_localTime,
				de.forestj.lib.io.StandardTransposeMethods.LocalTime.TransposeLocalTime_hhii_Colon("02:03"),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalTime_hhii_Colon(String) method does not return expected value"
			);
			
			assertEquals(
				"02:03",
				de.forestj.lib.io.StandardTransposeMethods.LocalTime.TransposeLocalTime_hhii_Colon(o_localTime, 5),
				"FixedLengthRecord StandardTransposeMethods TransposeLocalTime_hhii_Colon(Object, Integer) method does not return expected value"
			);
			
			/* Float */
			
			assertEquals(
				1f,
				(float)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat("000001", 0),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(String) method does not return expected value"
			);
			
			assertEquals(
				-1f,
				(float)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat("-00001", 0),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(String) method does not return expected value"
			);
			
			assertEquals(
				2147483647f,
				(float)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat("002147483647", 0),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(String) method does not return expected value"
			);
			
			assertEquals(
				214748.3647f,
				(float)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat("00214748.3647", 0),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(String) method does not return expected value"
			);
			
			assertEquals(
				214748.3647f,
				(float)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat("+00214748.3647", 0),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(String) method does not return expected value"
			);
		
			assertEquals(
				-2147.483647f,
				(float)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat("-0002147,483647", 0),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(String) method does not return expected value"
			);
			
			assertEquals(
				214748.3647f,
				(float)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat("002147483647", 8),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(String, Integer) method does not return expected value"
			);
			
			assertEquals(
				214748.3647f,
				(float)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat("+002147483647", 9),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(String, Integer) method does not return expected value"
			);
			
			assertEquals(
				-21474.83647f,
				(float)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat("-002147483647", 8),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(String, Integer) method does not return expected value"
			);
			
			assertEquals(
				"000001",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat(1f, 6, 0, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"000001",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat(-1f, 6, 0, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"00214748",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat(214748f, 8, 0, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"00,214,748.12",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat(214748.1230101f, 8, 2),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(Object, Integer, Integer) method does not return expected value"
			);
			
			assertEquals(
				"00.214.748,12",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat(214748.1230101f, 8, 2, ",", "."),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"00214748,12",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat(-214748.1230101f, 8, 2, ",", null),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"0021474812",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat(-214748.1230101f, 8, 2, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"00214748,1250",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat(214748.1230101f, 8, 4, ",", null),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"002147481250",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat(-214748.1230101f, 8, 4, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+000001",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloatWithSign(1f, 6, 0, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeFloatWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"-000001",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloatWithSign(-1f, 6, 0, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeFloatWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+00214748",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloatWithSign(214748f, 8, 0, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeFloatWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+00,214,748.12",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloatWithSign(214748.1230101f, 8, 2),
				"FixedLengthRecord StandardTransposeMethods TransposeFloatWithSign(Object, Integer, Integer) method does not return expected value"
			);
			
			assertEquals(
				"+00.214.748,12",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloatWithSign(214748.1230101f, 8, 2, ",", "."),
				"FixedLengthRecord StandardTransposeMethods TransposeFloatWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"-00214748,12",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloatWithSign(-214748.1230101f, 8, 2, ",", null),
				"FixedLengthRecord StandardTransposeMethods TransposeFloatWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+0021474812",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloatWithSign(214748.1230101f, 8, 2, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeFloatWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"-00214748,1250",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloatWithSign(-214748.1230101f, 8, 4, ",", null),
				"FixedLengthRecord StandardTransposeMethods TransposeFloatWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"-002147481250",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloatWithSign(-214748.1230101f, 8, 4, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeFloatWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"0000000000",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat(0f, 6, 4, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+0000000000",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloatWithSign(0f, 6, 4, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeFloatWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"000000,0000",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat(0f, 6, 4, ",", null),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+000000,0000",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloatWithSign(0f, 6, 4, ",", null),
				"FixedLengthRecord StandardTransposeMethods TransposeFloatWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"000.000,0000",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloat(0f, 6, 4, ",", "."),
				"FixedLengthRecord StandardTransposeMethods TransposeFloat(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+000.000,0000",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeFloatWithSign(0f, 6, 4, ",", "."),
				"FixedLengthRecord StandardTransposeMethods TransposeFloatWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			/* Double */
			
			assertEquals(
				1d,
				(double)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble("000001", 0),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(String) method does not return expected value"
			);
			
			assertEquals(
				-1d,
				(double)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble("-00001", 0),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(String) method does not return expected value"
			);
			
			assertEquals(
				2147483647d,
				(double)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble("002147483647", 0),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(String) method does not return expected value"
			);
			
			assertEquals(
				214748.3647d,
				(double)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble("00214748.3647", 0),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(String) method does not return expected value"
			);
			
			assertEquals(
				214748.3647d,
				(double)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble("+00214748.3647", 0),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(String) method does not return expected value"
			);
		
			assertEquals(
				-2147.483647d,
				(double)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble("-0002147,483647", 0),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(String) method does not return expected value"
			);
			
			assertEquals(
				214748.3647d,
				(double)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble("002147483647", 8),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(String, Integer) method does not return expected value"
			);
			
			assertEquals(
				214748.3647d,
				(double)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble("+002147483647", 9),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(String, Integer) method does not return expected value"
			);
			
			assertEquals(
				-21474.83647d,
				(double)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble("-002147483647", 8),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(String, Integer) method does not return expected value"
			);
			
			assertEquals(
				"000001",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble(1d, 6, 0, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"000001",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble(-1d, 6, 0, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"00214748",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble(214748d, 8, 0, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"00,214,748.12",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble(214748.1230101d, 8, 2),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(Object, Integer, Integer) method does not return expected value"
			);
			
			assertEquals(
				"00.214.748,12",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble(214748.1230101d, 8, 2, ",", "."),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"00214748,1230",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble(-214748.12301019812d, 8, 4, ",", null),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"002147481230",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble(214748.12301019812d, 8, 4, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"00214748,1230101981",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble(-214748.12301019812d, 8, 10, ",", null),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"002147481230101981",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble(-214748.12301019812d, 8, 10, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+000001",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDoubleWithSign(1d, 6, 0, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeDoubleWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"-000001",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDoubleWithSign(-1d, 6, 0, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeDoubleWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+00214748",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDoubleWithSign(214748d, 8, 0, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeDoubleWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+00,214,748.12",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDoubleWithSign(214748.1230101d, 8, 2),
				"FixedLengthRecord StandardTransposeMethods TransposeDoubleWithSign(Object, Integer, Integer) method does not return expected value"
			);
			
			assertEquals(
				"+00.214.748,12",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDoubleWithSign(214748.1230101d, 8, 2, ",", "."),
				"FixedLengthRecord StandardTransposeMethods TransposeDoubleWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"-00214748,1230",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDoubleWithSign(-214748.12301019812d, 8, 4, ",", null),
				"FixedLengthRecord StandardTransposeMethods TransposeDoubleWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+002147481230",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDoubleWithSign(214748.12301019812d, 8, 4, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeDoubleWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"-00214748,1230101981",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDoubleWithSign(-214748.12301019812d, 8, 10, ",", null),
				"FixedLengthRecord StandardTransposeMethods TransposeDoubleWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"-002147481230101981",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDoubleWithSign(-214748.12301019812d, 8, 10, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeDoubleWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"000000000000000000",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble(0d, 8, 10, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+000000000000000000",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDoubleWithSign(0d, 8, 10, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeDoubleWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"00000000,0000000000",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble(0d, 8, 10, ",", null),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+00000000,0000000000",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDoubleWithSign(0d, 8, 10, ",", null),
				"FixedLengthRecord StandardTransposeMethods TransposeDoubleWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"00.000.000,0000000000",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDouble(0d, 8, 10, ",", "."),
				"FixedLengthRecord StandardTransposeMethods TransposeDouble(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+00.000.000,0000000000",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeDoubleWithSign(0d, 8, 10, ",", "."),
				"FixedLengthRecord StandardTransposeMethods TransposeDoubleWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			/* BigDecimal */
			
			assertEquals(
				new java.math.BigDecimal("1"),
				(java.math.BigDecimal)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal("000001", 0),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(String) method does not return expected value"
			);
			
			assertEquals(
				new java.math.BigDecimal("-1"),
				(java.math.BigDecimal)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal("-00001", 0),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(String) method does not return expected value"
			);
			
			assertEquals(
				new java.math.BigDecimal("0.0"),
				(java.math.BigDecimal)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal("00000000000000000000000", 13),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(String) method does not return expected value"
			);
			
			assertEquals(
				new java.math.BigDecimal("0.0"),
				(java.math.BigDecimal)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal("+00000000000000000000000", 13),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(String) method does not return expected value"
			);
			
			assertEquals(
				new java.math.BigDecimal("0.0"),
				(java.math.BigDecimal)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal("-00000000000000000000000", 13),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(String) method does not return expected value"
			);
			
			assertEquals(
				new java.math.BigDecimal("2147483647"),
				(java.math.BigDecimal)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal("002147483647", 0),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(String) method does not return expected value"
			);
			
			assertEquals(
				new java.math.BigDecimal("214748.3647"),
				(java.math.BigDecimal)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal("00214748.3647", 0),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(String) method does not return expected value"
			);
			
			assertEquals(
				new java.math.BigDecimal("214748.3647"),
				(java.math.BigDecimal)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal("+00214748.3647", 0),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(String) method does not return expected value"
			);
		
			assertEquals(
				new java.math.BigDecimal("-2147.483647"),
				(java.math.BigDecimal)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal("-0002147,483647", 0),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(String) method does not return expected value"
			);
			
			assertEquals(
				new java.math.BigDecimal("214748.3647"),
				(java.math.BigDecimal)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal("002147483647", 8),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(String, Integer) method does not return expected value"
			);
			
			assertEquals(
				new java.math.BigDecimal("214748.3647"),
				(java.math.BigDecimal)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal("+002147483647", 9),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(String, Integer) method does not return expected value"
			);
			
			assertEquals(
				new java.math.BigDecimal("-21474.83647"),
				(java.math.BigDecimal)de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal("-002147483647", 8),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(String, Integer) method does not return expected value"
			);
			
			assertEquals(
				"000001",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal(new java.math.BigDecimal("1"), 6, 0, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"000001",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal(new java.math.BigDecimal("-1"), 6, 0, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"00214748",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal(new java.math.BigDecimal("214748"), 8, 0, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"00,214,748.12",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal(new java.math.BigDecimal("214748.1230101"), 8, 2),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(Object, Integer, Integer) method does not return expected value"
			);
			
			assertEquals(
				"00.214.748,12",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal(new java.math.BigDecimal("214748.1230101"), 8, 2, ",", "."),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"00214748,1230",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal(new java.math.BigDecimal("-214748.12301019812"), 8, 4, ",", null),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"002147481230",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal(new java.math.BigDecimal("214748.12301019812"), 8, 4, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"00214748,12301019812",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal(new java.math.BigDecimal("-214748.12301019812001"), 8, 11, ",", null),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"0021474812301019812",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal(new java.math.BigDecimal("-214748.12301019812001"), 8, 11, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimal(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+000001",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimalWithSign(new java.math.BigDecimal("1"), 6, 0, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimalWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"-000001",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimalWithSign(new java.math.BigDecimal("-1"), 6, 0, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimalWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+00214748",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimalWithSign(new java.math.BigDecimal("214748"), 8, 0, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimalWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+00,214,748.12",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimalWithSign(new java.math.BigDecimal("214748.1230101"), 8, 2),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimalWithSign(Object, Integer, Integer) method does not return expected value"
			);
			
			assertEquals(
				"+00.214.748,12",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimalWithSign(new java.math.BigDecimal("214748.1230101"), 8, 2, ",", "."),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimalWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"-00214748,1230",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimalWithSign(new java.math.BigDecimal("-214748.12301019812"), 8, 4, ",", null),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimalWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+002147481230",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimalWithSign(new java.math.BigDecimal("214748.12301019812"), 8, 4, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimalWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"-00214748,12301019812",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimalWithSign(new java.math.BigDecimal("-214748.12301019812009"), 8, 11, ",", null),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimalWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"-0021474812301019812",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimalWithSign(new java.math.BigDecimal("-214748.12301019812009"), 8, 11, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimalWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"0000000000000000000",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal(new java.math.BigDecimal("0"), 8, 11, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimalWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+0000000000000000000",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimalWithSign(new java.math.BigDecimal("0"), 8, 11, null, null),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimalWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"00000000,00000000000",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal(new java.math.BigDecimal("0"), 8, 11, ",", null),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimalWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+00000000,00000000000",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimalWithSign(new java.math.BigDecimal("0"), 8, 11, ",", null),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimalWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"00.000.000,00000000000",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal(new java.math.BigDecimal("0"), 8, 11, ",", "."),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimalWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
			
			assertEquals(
				"+00.000.000,00000000000",
				de.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimalWithSign(new java.math.BigDecimal("0"), 8, 11, ",", "."),
				"FixedLengthRecord StandardTransposeMethods TransposeBigDecimalWithSign(Object, Integer, Integer, String, String) method does not return expected value"
			);
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
