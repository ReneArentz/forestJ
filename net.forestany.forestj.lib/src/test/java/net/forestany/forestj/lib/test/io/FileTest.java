package net.forestany.forestj.lib.test.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FileTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testFile() {
		try {
			String s_currentDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory();
			String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testFile" + net.forestany.forestj.lib.io.File.DIR;
			
			if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
				net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			}
			
			net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
			assertTrue(
					net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
					"directory[" + s_testDirectory + "] does not exist"
			);
				
				String s_file = s_testDirectory + "file.txt";
				net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(s_file, true);
				o_file.appendLine("First line");
				o_file.appendLine("Second line");
				o_file.writeLine("new second line", 2);
				o_file.writeLine("some line between", 2);
				assertTrue(
						net.forestany.forestj.lib.io.File.exists(s_file),
						"file[" + s_file + "] does not exist"
				);
				assertTrue(
						o_file.getFileLines() == 4,
						"file lines != 4"
				);
				assertTrue(
						o_file.readLine(1).contentEquals("First line"),
						"line #1 is not 'First line'"
				);
				assertTrue(
						o_file.readLine(2).contentEquals("some line between"),
						"line #2 is not 'some line between'"
				);
				assertTrue(
						o_file.readLine(3).contentEquals("new second line"),
						"line #3 is not 'new second line'"
				);
				assertTrue(
						o_file.readLine(4).contentEquals("Second line"),
						"line #4 is not 'Second line'"
				);
				
				assertTrue(
						net.forestany.forestj.lib.io.File.hasFileExtension(s_file),
						"file[" + s_file + "] has no file extension"
				);
				
				assertTrue(
						net.forestany.forestj.lib.io.File.isFile(s_file),
						"file[" + s_file + "] is not a file"
				);
				assertFalse(
						net.forestany.forestj.lib.io.File.isFile(s_testDirectory),
						"directory[" + s_testDirectory + "] is a file"
				);
				
				assertTrue(
						net.forestany.forestj.lib.io.File.isDirectory(s_testDirectory),
						"directory[" + s_testDirectory + "] is not a directory"
				);
				assertFalse(
						net.forestany.forestj.lib.io.File.isDirectory(s_file),
						"file[" + s_file + "] is a directory"
				);
				
				assertTrue(
						net.forestany.forestj.lib.io.File.fileLength(s_file) == 61,
						"file[" + s_file + "] length != 61"
				);
				
				o_file.replaceLine("First line replace", 1);
				o_file.replaceLine("some line between replace", 2);
				assertTrue(
						o_file.getFileLines() == 4,
						"file lines != 4"
				);
				assertTrue(
						o_file.readLine(1).contentEquals("First line replace"),
						"line #1 is not 'First line replace'"
				);
				assertTrue(
						o_file.readLine(2).contentEquals("some line between replace"),
						"line #2 is not 'some line between replace'"
				);
				assertTrue(
						o_file.readLine(3).contentEquals("new second line"),
						"line #3 is not 'new second line'"
				);
				assertTrue(
						o_file.readLine(4).contentEquals("Second line"),
						"line #4 is not 'Second line'"
				);
				
				o_file.replaceContent("Just two lines" + net.forestany.forestj.lib.io.File.NEWLINE + "And this is the second" + net.forestany.forestj.lib.io.File.NEWLINE + "Oh!");
				assertTrue(
						o_file.getFileLines() == 3,
						"file lines != 3"
				);
				assertTrue(
						o_file.readLine(1).contentEquals("Just two lines"),
						"line #1 is not 'Just two lines'"
				);
				assertTrue(
						o_file.readLine(2).contentEquals("And this is the second"),
						"line #2 is not 'And this is the second'"
				);
				assertTrue(
						o_file.readLine(3).contentEquals("Oh!"),
						"line #3 is not 'Oh!'"
				);
				
				o_file.deleteLine(1);
				assertTrue(
						o_file.getFileLines() == 2,
						"file lines != 2"
				);
				assertTrue(
						o_file.readLine(1).contentEquals("And this is the second"),
						"line #1 is not 'And this is the second'"
				);
				assertTrue(
						o_file.readLine(2).contentEquals("Oh!"),
						"line #2 is not 'Oh!'"
				);
				
				o_file.truncateContent();
				assertTrue(
						o_file.getFileLines() == 0,
						"file lines != 0"
				);
				
				o_file.replaceContent("First" + net.forestany.forestj.lib.io.File.NEWLINE + "Second" + net.forestany.forestj.lib.io.File.NEWLINE + "Third" + net.forestany.forestj.lib.io.File.NEWLINE + "Fourth" + net.forestany.forestj.lib.io.File.NEWLINE + "Fifth");
				assertTrue(
						o_file.getFileLines() == 5,
						"file lines != 5"
				);
				assertTrue(
						o_file.readLine(1).contentEquals("First"),
						"line #1 is not 'First'"
				);
				assertTrue(
						o_file.readLine(2).contentEquals("Second"),
						"line #2 is not 'Second'"
				);
				assertTrue(
						o_file.readLine(3).contentEquals("Third"),
						"line #3 is not 'Third'"
				);
				assertTrue(
						o_file.readLine(4).contentEquals("Fourth"),
						"line #4 is not 'Fourth'"
				);
				assertTrue(
						o_file.readLine(5).contentEquals("Fifth"),
						"line #5 is not 'Fifth'"
				);
				
				String s_fileNew = s_testDirectory + "fileNew.txt";
				o_file.renameFile("fileNew.txt");
				assertTrue(
						net.forestany.forestj.lib.io.File.exists(s_fileNew),
						"file[" + s_fileNew + "] does not exist"
				);
				
				o_file = new net.forestany.forestj.lib.io.File(s_fileNew);
				assertTrue(
						o_file.getFileLines() == 5,
						"file lines != 5"
				);
				assertTrue(
						o_file.readLine(1).contentEquals("First"),
						"line #1 is not 'First'"
				);
				assertTrue(
						o_file.readLine(2).contentEquals("Second"),
						"line #2 is not 'Second'"
				);
				assertTrue(
						o_file.readLine(3).contentEquals("Third"),
						"line #3 is not 'Third'"
				);
				assertTrue(
						o_file.readLine(4).contentEquals("Fourth"),
						"line #4 is not 'Fourth'"
				);
				assertTrue(
						o_file.readLine(5).contentEquals("Fifth"),
						"line #5 is not 'Fifth'"
				);
					
				assertTrue(
						o_file.hash("SHA-512").contentEquals("85be5ad52ca9a965c1eaf88fd33e1bedc6c54e4a020c6ef4c837a2d711282c9ce05b03e6a57238ae44dc2560b177db68c1179042a0c95d933582dacdba72edb1"),
						"SHA-512 value is not '85be5ad52ca9a965c1eaf88fd33e1bedc6c54e4a020c6ef4c837a2d711282c9ce05b03e6a57238ae44dc2560b177db68c1179042a0c95d933582dacdba72edb1'"
				);
				assertTrue(
						net.forestany.forestj.lib.io.File.hashFile(s_fileNew, "SHA-512").contentEquals("85be5ad52ca9a965c1eaf88fd33e1bedc6c54e4a020c6ef4c837a2d711282c9ce05b03e6a57238ae44dc2560b177db68c1179042a0c95d933582dacdba72edb1"),
						"SHA-512 value is not '85be5ad52ca9a965c1eaf88fd33e1bedc6c54e4a020c6ef4c837a2d711282c9ce05b03e6a57238ae44dc2560b177db68c1179042a0c95d933582dacdba72edb1'"
				);
				
				String s_fileContentFromList = s_testDirectory + "fileContentFromList.txt";
				net.forestany.forestj.lib.io.File o_fileContentFromList = new net.forestany.forestj.lib.io.File(s_fileContentFromList, true);
				java.util.List<String> a_lines = java.util.Arrays.asList("one", "two", "three", "four", "five", "six");
				o_fileContentFromList.setFileContentFromList(a_lines);
				
				net.forestany.forestj.lib.io.File o_fileContentFromListCheck = new net.forestany.forestj.lib.io.File(s_fileContentFromList);
				assertTrue(
						o_fileContentFromListCheck.getFileLines() == 6,
						"file lines != 6"
				);
				assertTrue(
						o_fileContentFromListCheck.readLine(1).contentEquals("one"),
						"line #1 is not 'one'"
				);
				assertTrue(
						o_fileContentFromListCheck.readLine(2).contentEquals("two"),
						"line #2 is not 'two'"
				);
				assertTrue(
						o_fileContentFromListCheck.readLine(3).contentEquals("three"),
						"line #3 is not 'three'"
				);
				assertTrue(
						o_fileContentFromListCheck.readLine(4).contentEquals("four"),
						"line #4 is not 'four'"
				);
				assertTrue(
						o_fileContentFromListCheck.readLine(5).contentEquals("five"),
						"line #5 is not 'five'"
				);
				assertTrue(
						o_fileContentFromListCheck.readLine(6).contentEquals("six"),
						"line #5 is not 'six'"
				);
				
				net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory + "sub");
				assertTrue(
						net.forestany.forestj.lib.io.File.folderExists(s_testDirectory + "sub"),
						"directory[" + s_testDirectory + "sub" + "] does not exist"
				);
				
				String s_fileCopy = s_testDirectory + "sub" + net.forestany.forestj.lib.io.File.DIR + "file.txt";
				net.forestany.forestj.lib.io.File.copyFile(s_fileNew, s_fileCopy);
				assertTrue(
						net.forestany.forestj.lib.io.File.exists(s_fileCopy),
						"file[" + s_fileCopy + "] does not exist"
				);
				
				net.forestany.forestj.lib.io.File.moveFile(s_fileCopy, s_file);
				assertTrue(
						net.forestany.forestj.lib.io.File.exists(s_file),
						"file[" + s_file + "] does not exist"
				);
				assertFalse(
						net.forestany.forestj.lib.io.File.exists(s_fileCopy),
						"file[" + s_fileCopy + "] does exist"
				);
				
				net.forestany.forestj.lib.io.File.deleteFile(s_file);
				assertFalse(
						net.forestany.forestj.lib.io.File.exists(s_file),
						"file[" + s_file + "] does exist"
				);		
				
				net.forestany.forestj.lib.io.File.deleteFile(s_fileNew);
				assertFalse(
						net.forestany.forestj.lib.io.File.exists(s_fileNew),
						"file[" + s_fileNew + "] does exist"
				);
				
				o_file = new net.forestany.forestj.lib.io.File(s_testDirectory + "sub" + net.forestany.forestj.lib.io.File.DIR + "file1.txt", true);
				o_file.appendLine("1");
				assertTrue(
						net.forestany.forestj.lib.io.File.exists(s_testDirectory + "sub" + net.forestany.forestj.lib.io.File.DIR + "file1.txt"),
						"file[" + s_testDirectory + "sub" + net.forestany.forestj.lib.io.File.DIR + "file1.txt" + "] does not exist"
				);
				
				o_file = new net.forestany.forestj.lib.io.File(s_testDirectory + "sub" + net.forestany.forestj.lib.io.File.DIR + "file2.txt", true);
				o_file.appendLine("2");
				assertTrue(
						net.forestany.forestj.lib.io.File.exists(s_testDirectory + "sub" + net.forestany.forestj.lib.io.File.DIR + "file2.txt"),
						"file[" + s_testDirectory + "sub" + net.forestany.forestj.lib.io.File.DIR + "file2.txt" + "] does not exist"
				);
				
				o_file = new net.forestany.forestj.lib.io.File(s_testDirectory + "sub" + net.forestany.forestj.lib.io.File.DIR + "file3.txt", true);
				o_file.appendLine("3");
				assertTrue(
						net.forestany.forestj.lib.io.File.exists(s_testDirectory + "sub" + net.forestany.forestj.lib.io.File.DIR + "file3.txt"),
						"file[" + s_testDirectory + "sub" + net.forestany.forestj.lib.io.File.DIR + "file3.txt" + "] does not exist"
				);
				
				net.forestany.forestj.lib.io.File.copyDirectory(s_testDirectory + "sub", s_testDirectory + "copy");
				assertTrue(
						net.forestany.forestj.lib.io.File.exists(s_testDirectory + "copy" + net.forestany.forestj.lib.io.File.DIR + "file1.txt"),
						"file[" + s_testDirectory + "copy" + net.forestany.forestj.lib.io.File.DIR + "file1.txt" + "] does not exist"
				);
				assertTrue(
						net.forestany.forestj.lib.io.File.exists(s_testDirectory + "copy" + net.forestany.forestj.lib.io.File.DIR + "file2.txt"),
						"file[" + s_testDirectory + "copy" + net.forestany.forestj.lib.io.File.DIR + "file2.txt" + "] does not exist"
				);
				assertTrue(
						net.forestany.forestj.lib.io.File.exists(s_testDirectory + "copy" + net.forestany.forestj.lib.io.File.DIR + "file3.txt"),
						"file[" + s_testDirectory + "copy" + net.forestany.forestj.lib.io.File.DIR + "file3.txt" + "] does not exist"
				);
				
				net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory + "dest");
				assertTrue(
						net.forestany.forestj.lib.io.File.folderExists(s_testDirectory + "dest" + net.forestany.forestj.lib.io.File.DIR),
						"directory[" + s_testDirectory + "dest" + net.forestany.forestj.lib.io.File.DIR + "] does not exist"
				);
				
				net.forestany.forestj.lib.io.File.moveDirectory(s_testDirectory + "copy", s_testDirectory + "dest" + net.forestany.forestj.lib.io.File.DIR + "copy");
				assertFalse(
						net.forestany.forestj.lib.io.File.folderExists(s_testDirectory + "copy" + net.forestany.forestj.lib.io.File.DIR),
						"directory[" + s_testDirectory + "copy" + net.forestany.forestj.lib.io.File.DIR + "] does exist"
				);
				assertTrue(
						net.forestany.forestj.lib.io.File.folderExists(s_testDirectory + "dest" + net.forestany.forestj.lib.io.File.DIR + "copy" + net.forestany.forestj.lib.io.File.DIR),
						"directory[" + s_testDirectory + "dest" + net.forestany.forestj.lib.io.File.DIR + "copy" + net.forestany.forestj.lib.io.File.DIR + "] does not exist"
				);
				assertTrue(
						net.forestany.forestj.lib.io.File.exists(s_testDirectory + "dest" + net.forestany.forestj.lib.io.File.DIR + "copy" + net.forestany.forestj.lib.io.File.DIR + "file1.txt"),
						"file[" + s_testDirectory + "dest" + net.forestany.forestj.lib.io.File.DIR + "copy" + net.forestany.forestj.lib.io.File.DIR + "file1.txt" + "] does not exist"
				);
				assertTrue(
						net.forestany.forestj.lib.io.File.exists(s_testDirectory + "dest" + net.forestany.forestj.lib.io.File.DIR + "copy" + net.forestany.forestj.lib.io.File.DIR + "file2.txt"),
						"file[" + s_testDirectory + "dest" + net.forestany.forestj.lib.io.File.DIR + "copy" + net.forestany.forestj.lib.io.File.DIR + "file2.txt" + "] does not exist"
				);
				assertTrue(
						net.forestany.forestj.lib.io.File.exists(s_testDirectory + "dest" + net.forestany.forestj.lib.io.File.DIR + "copy" + net.forestany.forestj.lib.io.File.DIR + "file3.txt"),
						"file[" + s_testDirectory + "dest" + net.forestany.forestj.lib.io.File.DIR + "copy" + net.forestany.forestj.lib.io.File.DIR + "file3.txt" + "] does not exist"
				);
				
				o_file = new net.forestany.forestj.lib.io.File(s_testDirectory + "dest" + net.forestany.forestj.lib.io.File.DIR + "fileAlone.txt", true);
				o_file.appendLine("alone");
				
				assertTrue(
						net.forestany.forestj.lib.io.File.hashDirectory(s_testDirectory + "dest", "SHA-256").contentEquals("60344d3375b44f0acee666aaefa9116098ad071ec93978861c498d0e054ad0a5"),
						"SHA-256 value is not '60344d3375b44f0acee666aaefa9116098ad071ec93978861c498d0e054ad0a5'"
				);
				assertTrue(
						net.forestany.forestj.lib.io.File.hashDirectory(s_testDirectory + "dest", "SHA-256", true).contentEquals("3c87be84cbffcb04c3d7497af0df1435b7060bd537dc5b6b4b1c0eb322631e54"),
						"SHA-256 value is not '3c87be84cbffcb04c3d7497af0df1435b7060bd537dc5b6b4b1c0eb322631e54'"
				);
				
				int i = 0;
				
				for (net.forestany.forestj.lib.io.ListingElement o_listingElement : net.forestany.forestj.lib.io.File.listDirectory(s_testDirectory + "dest" + net.forestany.forestj.lib.io.File.DIR + "copy")) {
					if (i == 0) {
						assertTrue(
								o_listingElement.getName().contentEquals("file1.txt"),
								"first element in list has not the name 'file1.txt'"
						);
					} else if (i == 1) {
						assertTrue(
								o_listingElement.getName().contentEquals("file2.txt"),
								"first element in list has not the name 'file2.txt'"
						);
					} else if (i == 3) {
						assertTrue(
								o_listingElement.getName().contentEquals("file3.txt"),
								"first element in list has not the name 'file3.txt'"
						);
					}
					
					i++;
				}
				
				net.forestany.forestj.lib.io.File.renameDirectory(s_testDirectory + "dest", s_testDirectory + "destRename");
				assertTrue(
						net.forestany.forestj.lib.io.File.folderExists(s_testDirectory + "destRename" + net.forestany.forestj.lib.io.File.DIR),
						"directory[" + s_testDirectory + "destRename" + net.forestany.forestj.lib.io.File.DIR + "] does not exist"
				);
				assertFalse(
						net.forestany.forestj.lib.io.File.folderExists(s_testDirectory + "dest" + net.forestany.forestj.lib.io.File.DIR),
						"directory[" + s_testDirectory + "dest" + net.forestany.forestj.lib.io.File.DIR + "] does exist"
				);
				
				StringBuilder o_stringBuilder = new StringBuilder();
				o_stringBuilder.append("one" + net.forestany.forestj.lib.io.File.NEWLINE + "two" + net.forestany.forestj.lib.io.File.NEWLINE + "three" + net.forestany.forestj.lib.io.File.NEWLINE + "four");
				net.forestany.forestj.lib.io.File.replaceFileContent(s_testDirectory + "destRename" + net.forestany.forestj.lib.io.File.DIR + "fileAlone.txt", o_stringBuilder);
				o_file = new net.forestany.forestj.lib.io.File(s_testDirectory + "destRename" + net.forestany.forestj.lib.io.File.DIR + "fileAlone.txt");
				assertTrue(
						o_file.getFileLines() == 4,
						"file lines != 4"
				);
				assertTrue(
						net.forestany.forestj.lib.io.File.fileLength(s_testDirectory + "destRename" + net.forestany.forestj.lib.io.File.DIR + "fileAlone.txt") == 21,
						"file length != 21"
				);
				
				o_file = new net.forestany.forestj.lib.io.File(s_testDirectory + "fileContent.txt", true);
				net.forestany.forestj.lib.io.File.replaceFileContent(s_testDirectory + "fileContent.txt", net.forestany.forestj.lib.io.File.generateRandomFileContent_1KB());
				assertTrue(
						net.forestany.forestj.lib.io.File.fileLength(s_testDirectory + "fileContent.txt") == 1024,
						"file length != 1024"
				);
				
				net.forestany.forestj.lib.io.File.replaceFileContent(s_testDirectory + "fileContent.txt", net.forestany.forestj.lib.io.File.generateRandomFileContent_1MB());
				assertTrue(
						net.forestany.forestj.lib.io.File.fileLength(s_testDirectory + "fileContent.txt") == 1048576,
						"file length != 1048576"
				);
				
				net.forestany.forestj.lib.io.File.replaceFileContent(s_testDirectory + "fileContent.txt", net.forestany.forestj.lib.io.File.generateRandomFileContent_10MB());
				assertTrue(
						net.forestany.forestj.lib.io.File.fileLength(s_testDirectory + "fileContent.txt") == 10485760,
						"file length != 10485760"
				);
				
				net.forestany.forestj.lib.io.File.replaceFileContent(s_testDirectory + "fileContent.txt", net.forestany.forestj.lib.io.File.generateRandomFileContent_50MB());
				assertTrue(
						net.forestany.forestj.lib.io.File.fileLength(s_testDirectory + "fileContent.txt") == 52428800,
						"file length != 52428800"
				);
				
				long l_length = (long)net.forestany.forestj.lib.Helper.randomIntegerRange(1048576, 10485760);
				net.forestany.forestj.lib.io.File.replaceFileContent(s_testDirectory + "fileContent.txt", net.forestany.forestj.lib.io.File.generateRandomFileContent(l_length));
				assertTrue(
						net.forestany.forestj.lib.io.File.fileLength(s_testDirectory + "fileContent.txt") == l_length,
						"file length != " + l_length
				);
				
				l_length = (long)net.forestany.forestj.lib.Helper.randomIntegerRange(1048576, 10485760);
				int i_lineLength = net.forestany.forestj.lib.Helper.randomIntegerRange(128, 512);
				net.forestany.forestj.lib.io.File.replaceFileContent(s_testDirectory + "fileContent.txt", net.forestany.forestj.lib.io.File.generateRandomFileContent(i_lineLength, l_length));
				assertTrue(
						net.forestany.forestj.lib.io.File.fileLength(s_testDirectory + "fileContent.txt") == l_length,
						"file length != " + l_length
				);
				
			net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			assertFalse(
					net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
					"directory[" + s_testDirectory + "] does exist"
			);
			
			if ( net.forestany.forestj.lib.io.File.folderExists(s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testFileFolderStructure" + net.forestany.forestj.lib.io.File.DIR) ) {
				net.forestany.forestj.lib.io.File.deleteDirectory(s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testFileFolderStructure");
			}
			
			net.forestany.forestj.lib.io.File.createHexFileFolderStructure(s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testFileFolderStructure");
			assertTrue(
					net.forestany.forestj.lib.io.File.listDirectory(s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testFileFolderStructure").size() == 256,
					"folder amount in file folder structure != 256"
			);
			net.forestany.forestj.lib.io.File.deleteDirectory(s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testFileFolderStructure");
			assertFalse(
					net.forestany.forestj.lib.io.File.folderExists(s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testFileFolderStructure" + net.forestany.forestj.lib.io.File.DIR),
					"directory[" + s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testFileFolderStructure" + net.forestany.forestj.lib.io.File.DIR + "] does exist"
			);
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
