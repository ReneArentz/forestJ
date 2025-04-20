package net.forestany.forestj.lib.test.sql.mariadb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * record test class for mariadb tests
 */
public class RecordTest {
	/**
	 * record test method for mariadb tests
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testRecordMariaDB() {
		try {
			net.forestany.forestj.lib.test.sql.mariadb.SetBase.setBase();
			net.forestany.forestj.lib.test.sqltest.RecordTest.testRecord();
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
