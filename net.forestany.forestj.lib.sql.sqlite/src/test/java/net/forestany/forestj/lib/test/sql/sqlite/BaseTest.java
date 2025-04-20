package net.forestany.forestj.lib.test.sql.sqlite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * base test class for pgsql tests
 */
public class BaseTest {
	/**
	 * base test method for pgsql tests
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testBaseSQLite() {
		try {
			String s_currentDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory();
			String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testBase" + net.forestany.forestj.lib.io.File.DIR;
			
			if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
				net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			}
			
			net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
			assertTrue(
				net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
				"directory[" + s_testDirectory + "] does not exist"
			);
			
			net.forestany.forestj.lib.test.sql.sqlite.SetBase.setBase(s_testDirectory);
			net.forestany.forestj.lib.test.sqltest.BaseTest.testBase();
			
			net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			assertFalse(
				net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
				"directory[" + s_testDirectory + "] does exist"
			);
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
