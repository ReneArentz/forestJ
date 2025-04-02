package net.forestany.forestj.lib.test.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FileSystemWatcherTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testFileSystemWatcher() {
		try {
			String s_currentDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory();
			String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testFileSystemWatcher" + net.forestany.forestj.lib.io.File.DIR;
			
			if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
				net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			}
			
			net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
			assertTrue(
					net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
					"directory[" + s_testDirectory + "] does not exist"
			);
				
			String s_file = s_testDirectory + "fileSystemWatcher.log";
			net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(s_file, true);
			assertTrue(
					net.forestany.forestj.lib.io.File.exists(s_file),
					"file[" + s_file + "] does not exist"
			);
			assertTrue(
					o_file.getFileLines() == 0,
					"file lines != 0"
			);
			
			String s_watchDirectory = s_testDirectory + net.forestany.forestj.lib.io.File.DIR + "foo" + net.forestany.forestj.lib.io.File.DIR;
			
			if ( net.forestany.forestj.lib.io.File.folderExists(s_watchDirectory) ) {
				net.forestany.forestj.lib.io.File.deleteDirectory(s_watchDirectory);
			}
			
			net.forestany.forestj.lib.io.File.createDirectory(s_watchDirectory);
			assertTrue(
					net.forestany.forestj.lib.io.File.folderExists(s_watchDirectory),
					"directory[" + s_watchDirectory + "] does not exist"
			);
			
			/**/
			
			java.time.LocalTime o_startTime = java.time.LocalTime.now().plusSeconds(2);
			String s_dateInterval = "PT1S";
			boolean b_recursive = true;
			
			String s_fileExtensionFilter = "*.txt|*_log.xml";
			
			net.forestany.forestj.lib.io.FileSystemWatcher o_fileSystemWatcher = new net.forestany.forestj.lib.io.FileSystemWatcher(s_watchDirectory, new net.forestany.forestj.lib.DateInterval(s_dateInterval), o_startTime) {
				private int i_cnt = 1;
				
				@Override public void createEvent(net.forestany.forestj.lib.io.ListingElement p_o_listingElement) {
					try {
						net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(s_file);
						o_file.appendLine(java.time.LocalTime.now() + "\t" + "File created: " + p_o_listingElement.getName() + " - " + i_cnt++);
					} catch (Exception o_exc) {
						net.forestany.forestj.lib.Global.logException(o_exc);
					}
				}

				@Override public void changeEvent(net.forestany.forestj.lib.io.ListingElement p_o_listingElement) {
					try {
						net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(s_file);
						o_file.appendLine(java.time.LocalTime.now() + "\t" + "File changed: " + p_o_listingElement.getName() + " - at " + p_o_listingElement.getLastModifiedTime() + " - " + i_cnt++);
					} catch (Exception o_exc) {
						net.forestany.forestj.lib.Global.logException(o_exc);
					}
				}

				@Override public void deleteEvent(net.forestany.forestj.lib.io.ListingElement p_o_listingElement) {
					try {
						net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(s_file);
						o_file.appendLine(java.time.LocalTime.now() + "\t" + "File deleted: " + p_o_listingElement.getFullName() + " - " + i_cnt++);
					} catch (Exception o_exc) {
						net.forestany.forestj.lib.Global.logException(o_exc);
					}
				}

				@Override public void accessEvent(net.forestany.forestj.lib.io.ListingElement p_o_listingElement) {
					try {
						net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(s_file);
						o_file.appendLine(java.time.LocalTime.now() + "\t" + "File accessed: " + p_o_listingElement.getName() + " - " + i_cnt++);
					} catch (Exception o_exc) {
						net.forestany.forestj.lib.Global.logException(o_exc);
					}
				}
			};
			
			o_fileSystemWatcher.excludeWeekday(java.time.DayOfWeek.SATURDAY);
			o_fileSystemWatcher.excludeWeekday(java.time.DayOfWeek.SUNDAY);
			o_fileSystemWatcher.setCreate(true);
			o_fileSystemWatcher.setChange(true);
			o_fileSystemWatcher.setDelete(true);
			o_fileSystemWatcher.setAccess(false);
			o_fileSystemWatcher.setRecursive(b_recursive);
			o_fileSystemWatcher.setFileExtensionFilter(s_fileExtensionFilter);
			
			o_fileSystemWatcher.start();
			
			Thread.sleep(2100);
			
			String s_fileFoo = s_watchDirectory + "firstFile.txt";
			net.forestany.forestj.lib.io.File o_fileFoo = new net.forestany.forestj.lib.io.File(s_fileFoo, true);
			
			Thread.sleep(1100); o_fileFoo.appendLine("one");
			Thread.sleep(1100); o_fileFoo.appendLine("two");
			Thread.sleep(1100); o_fileFoo.appendLine("three");
			Thread.sleep(1100); net.forestany.forestj.lib.io.File.deleteFile(s_fileFoo);
			
			s_fileFoo = s_watchDirectory + "ignore.xml";
			o_fileFoo = new net.forestany.forestj.lib.io.File(s_fileFoo, true);
			
			Thread.sleep(1100); o_fileFoo.appendLine("one");
			Thread.sleep(1100); o_fileFoo.appendLine("two");
			Thread.sleep(1100); o_fileFoo.appendLine("three");
			Thread.sleep(1100); net.forestany.forestj.lib.io.File.deleteFile(s_fileFoo);
			
			s_fileFoo = s_watchDirectory + "not_ignore_log.xml";
			o_fileFoo = new net.forestany.forestj.lib.io.File(s_fileFoo, true);
			
			Thread.sleep(1100); o_fileFoo.appendLine("one");
			Thread.sleep(1100); o_fileFoo.appendLine("two");
			Thread.sleep(1100); o_fileFoo.appendLine("three");
			Thread.sleep(1100); net.forestany.forestj.lib.io.File.deleteFile(s_fileFoo);
			
			String s_watchSubDirectory = s_watchDirectory + net.forestany.forestj.lib.io.File.DIR + "sub" + net.forestany.forestj.lib.io.File.DIR;
			net.forestany.forestj.lib.io.File.createDirectory(s_watchSubDirectory);
			assertTrue(
					net.forestany.forestj.lib.io.File.folderExists(s_watchSubDirectory),
					"directory[" + s_watchSubDirectory + "] does not exist"
			);
			
			s_fileFoo = s_watchSubDirectory + "firstFile.txt";
			o_fileFoo = new net.forestany.forestj.lib.io.File(s_fileFoo, true);
			
			Thread.sleep(1100); o_fileFoo.appendLine("one");
			Thread.sleep(1100); o_fileFoo.appendLine("two");
			Thread.sleep(1100); o_fileFoo.appendLine("three");
			Thread.sleep(1100); net.forestany.forestj.lib.io.File.deleteFile(s_fileFoo);
			
			s_fileFoo = s_watchSubDirectory + "ignore.xml";
			o_fileFoo = new net.forestany.forestj.lib.io.File(s_fileFoo, true);
			
			Thread.sleep(1100); o_fileFoo.appendLine("one");
			Thread.sleep(1100); o_fileFoo.appendLine("two");
			Thread.sleep(1100); o_fileFoo.appendLine("three");
			Thread.sleep(1100); net.forestany.forestj.lib.io.File.deleteFile(s_fileFoo);
			
			s_fileFoo = s_watchSubDirectory + "not_ignore_log.xml";
			o_fileFoo = new net.forestany.forestj.lib.io.File(s_fileFoo, true);
			
			Thread.sleep(1100); o_fileFoo.appendLine("one");
			Thread.sleep(1100); o_fileFoo.appendLine("two");
			Thread.sleep(1100); o_fileFoo.appendLine("three");
			Thread.sleep(1100); net.forestany.forestj.lib.io.File.deleteFile(s_fileFoo);
			Thread.sleep(1100);
			
            o_fileSystemWatcher.stop();
			
			/**/
            
			o_file = new net.forestany.forestj.lib.io.File(s_file);
			
			java.time.DayOfWeek o_dayOfWeek = java.time.DayOfWeek.from(java.time.LocalDate.now()); 
			
			if ( (o_dayOfWeek == java.time.DayOfWeek.SATURDAY) || (o_dayOfWeek == java.time.DayOfWeek.SUNDAY) ) {
				assertTrue(
						o_file.getFileLines() == 0,
						"file lines != 0"
				);
			} else {
				assertTrue(
						o_file.getFileLines() == 20,
						"file lines != 20"
				);
				
				for (int i = 0; i < o_file.getFileLines(); i++) {
					assertTrue(
							o_file.readLine((i + 1)).endsWith(" - " + (i + 1)),
							"line #" + (i + 1) + " does not end with '- " + (i + 1) + "'"
					);
				}
			}
			
			net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			assertFalse(
					net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
					"directory[" + s_testDirectory + "] does exist"
			);
        } catch(Exception o_exc) {
        	fail(o_exc.getMessage());
        }
	}
}
