package net.forestany.forestj.lib.test.sql.pool;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * record test class for pgsql tests
 */
public class RecordCopyAndCompareTest {
	/**
	 * record test method for pgsql tests
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testRecordCopyAndCompare() {
		try {
			String s_currentDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory();
			String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testRecord" + net.forestany.forestj.lib.io.File.DIR;
			
			if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
				net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			}
			
			net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
			assertTrue(
				net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
				"directory[" + s_testDirectory + "] does not exist"
			);
			
            // net.forestany.forestj.lib.test.sqltest.RecordCopyAndCompareTest o_foo = new net.forestany.forestj.lib.test.sqltest.RecordCopyAndCompareTest(
            //     new net.forestany.forestj.lib.sql.pgsql.BasePGSQL(net.forestany.forestj.lib.test.sqltest.BaseTest.s_baseHost + ":5432", "test", "postgres", "root"),
            //     new net.forestany.forestj.lib.sql.sqlite.BaseSQLite(s_testDirectory + "testBase.db"),
            //     net.forestany.forestj.lib.sqlcore.BaseGateway.PGSQL,
            //     net.forestany.forestj.lib.sqlcore.BaseGateway.SQLITE
            // );

            net.forestany.forestj.lib.test.sqltest.RecordCopyAndCompareTest o_foo = new net.forestany.forestj.lib.test.sqltest.RecordCopyAndCompareTest(
                new net.forestany.forestj.lib.sql.pgsql.BasePGSQL(net.forestany.forestj.lib.test.sqltest.BaseTest.s_baseHost + ":5432", "test", "postgres", "root"),
                new net.forestany.forestj.lib.sql.mariadb.BaseMariaDB(net.forestany.forestj.lib.test.sqltest.BaseTest.s_baseHost + ":3306", "test", "root", "root"),
                net.forestany.forestj.lib.sqlcore.BaseGateway.PGSQL,
                net.forestany.forestj.lib.sqlcore.BaseGateway.MARIADB
            );

            o_foo.testRecordCopyAndCompare();
			
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

