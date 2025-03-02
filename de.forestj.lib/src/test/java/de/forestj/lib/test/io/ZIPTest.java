package de.forestj.lib.test.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ZIPTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testZIP() {
		try {
			de.forestj.lib.LoggingConfig.initiateTestLogging();
			
			String s_currentDirectory = de.forestj.lib.io.File.getCurrentDirectory();
			String s_zipDirectory = s_currentDirectory + de.forestj.lib.io.File.DIR + "testZIP" + de.forestj.lib.io.File.DIR;
			
			if ( de.forestj.lib.io.File.folderExists(s_zipDirectory) ) {
				de.forestj.lib.io.File.deleteDirectory(s_zipDirectory);
			}
			
			de.forestj.lib.io.File.createDirectory(s_zipDirectory);
			assertTrue(
					de.forestj.lib.io.File.folderExists(s_zipDirectory),
					"directory[" + s_zipDirectory + "] does not exist"
			);
			
				de.forestj.lib.io.File o_file = new de.forestj.lib.io.File(s_zipDirectory + "fileZip1.txt", true);
			
				assertTrue(
						o_file.getFileContent().length() == 0,
						"file length != 0"
				);				
				de.forestj.lib.io.File.replaceFileContent(s_zipDirectory + "fileZip1.txt", de.forestj.lib.io.File.generateRandomFileContent_50MB());
				assertTrue(
						de.forestj.lib.io.File.fileLength(s_zipDirectory + "fileZip1.txt") == 52428800,
						"file length != 52428800"
				);			
				de.forestj.lib.io.ZIP.zip(s_zipDirectory + "fileZip1.txt", s_zipDirectory + "fileZip1.zip");
				
				assertTrue(
						de.forestj.lib.io.File.exists(s_zipDirectory + "fileZip1.zip"),
						"file[" + s_zipDirectory + "fileZip1.zip" + "] does not exist"
				);				
				assertTrue(
						de.forestj.lib.io.ZIP.checkArchive(s_zipDirectory + "fileZip1.zip"),
						"archive[" + s_zipDirectory + "fileZip1.zip" + "] is not valid"
				);			
				
				de.forestj.lib.io.File.deleteFile(s_zipDirectory + "fileZip1.zip");
				assertFalse(
						de.forestj.lib.io.File.exists(s_zipDirectory + "fileZip1.zip"),
						"file[" + s_zipDirectory + "fileZip1.zip" + "] does exist"
				);					
				
				String s_subDirectory = s_zipDirectory + "sub" + de.forestj.lib.io.File.DIR;
				
				de.forestj.lib.io.File.createDirectory(s_subDirectory);
				
				assertTrue(
						de.forestj.lib.io.File.folderExists(s_subDirectory),
						"directory[" + s_subDirectory + "] does not exist"
				);				
				o_file = new de.forestj.lib.io.File(s_subDirectory + "fileZip1.txt", true);
				
				assertTrue(
						o_file.getFileContent().length() == 0,
						"file length != 0"
				);				
				o_file = new de.forestj.lib.io.File(s_subDirectory + "fileZip2.txt", true);
				
				assertTrue(
						o_file.getFileContent().length() == 0,
						"file length != 0"
				);				
				o_file = new de.forestj.lib.io.File(s_subDirectory + "fileZip3.txt", true);
				
				assertTrue(
						o_file.getFileContent().length() == 0,
						"file length != 0"
				);				
				de.forestj.lib.io.File.replaceFileContent(s_subDirectory + "fileZip1.txt", de.forestj.lib.io.File.generateRandomFileContent_1MB());
				assertTrue(
						de.forestj.lib.io.File.fileLength(s_subDirectory + "fileZip1.txt") == 1048576,
						"file length != 1048576"
				);				
				de.forestj.lib.io.File.replaceFileContent(s_subDirectory + "fileZip2.txt", de.forestj.lib.io.File.generateRandomFileContent_50MB());
				assertTrue(
						de.forestj.lib.io.File.fileLength(s_subDirectory + "fileZip2.txt") == 52428800,
						"file length != 52428800"
				);				
				de.forestj.lib.io.File.replaceFileContent(s_subDirectory + "fileZip3.txt", de.forestj.lib.io.File.generateRandomFileContent_10MB());
				assertTrue(
						de.forestj.lib.io.File.fileLength(s_subDirectory + "fileZip3.txt") == 10485760,
						"file length != 10485760"
				);				
				de.forestj.lib.io.ZIP.zip(s_subDirectory, s_zipDirectory + "folder.zip");
				
				assertTrue(
						de.forestj.lib.io.File.exists(s_zipDirectory + "folder.zip"),
						"file[" + s_zipDirectory + "folder.zip" + "] does not exist"
				);				
				assertTrue(
						de.forestj.lib.io.ZIP.checkArchive(s_zipDirectory + "folder.zip"),
						"archive[" + s_zipDirectory + "folder.zip" + "] is not valid"
				);
				assertTrue(
						de.forestj.lib.io.ZIP.getSize(s_zipDirectory + "folder.zip") == 63963136,
						"zip size != 63963136"
				);
				de.forestj.lib.io.File.deleteFile(s_zipDirectory + "folder.zip");
				assertFalse(
						de.forestj.lib.io.File.exists(s_zipDirectory + "folder.zip"),
						"file[" + s_zipDirectory + "folder.zip" + "] does exist"
				);	
				
				de.forestj.lib.io.ZIP.zip(s_zipDirectory, s_zipDirectory + "all.zip");
				
				assertTrue(
						de.forestj.lib.io.File.exists(s_zipDirectory + "all.zip"),
						"file[" + s_zipDirectory + "all.zip" + "] does not exist"
				);				
				assertTrue(
						de.forestj.lib.io.ZIP.checkArchive(s_zipDirectory + "all.zip"),
						"archive[" + s_zipDirectory + "all.zip" + "] is not valid"
				);
				assertTrue(
						de.forestj.lib.io.ZIP.getSize(s_zipDirectory + "all.zip") == 116391936,
						"zip size != 116391936"
				);
				
				de.forestj.lib.io.File.deleteDirectory(s_subDirectory);
				assertFalse(
						de.forestj.lib.io.File.folderExists(s_subDirectory),
						"directory[" + s_subDirectory + "] does exist"
				);
				de.forestj.lib.io.File.deleteFile(s_zipDirectory + "fileZip1.txt");
				assertFalse(
						de.forestj.lib.io.File.exists(s_zipDirectory + "fileZip1.txt"),
						"file[" + s_zipDirectory + "fileZip1.txt" + "] does exist"
				);	
				
				String s_unzipDirectory = s_zipDirectory + "unzip" + de.forestj.lib.io.File.DIR;
							
				de.forestj.lib.io.ZIP.unzip(s_zipDirectory + "all.zip", s_unzipDirectory, true, true);
				
				assertTrue(
						de.forestj.lib.io.File.fileLength(s_unzipDirectory + "fileZip1.txt") == 52428800,
						"file length != 52428800"
				);	
				assertTrue(
						de.forestj.lib.io.File.fileLength(s_unzipDirectory + "sub" + de.forestj.lib.io.File.DIR + "fileZip1.txt") == 1048576,
						"file length != 1048576"
				);				
				assertTrue(
						de.forestj.lib.io.File.fileLength(s_unzipDirectory + "sub" + de.forestj.lib.io.File.DIR + "fileZip2.txt") == 52428800,
						"file length != 52428800"
				);				
				assertTrue(
						de.forestj.lib.io.File.fileLength(s_unzipDirectory + "sub" + de.forestj.lib.io.File.DIR + "fileZip3.txt") == 10485760,
						"file length != 10485760"
				);
				
			de.forestj.lib.io.File.deleteDirectory(s_zipDirectory);
			assertFalse(
					de.forestj.lib.io.File.folderExists(s_zipDirectory),
					"directory[" + s_zipDirectory + "] does exist"
			);		
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
