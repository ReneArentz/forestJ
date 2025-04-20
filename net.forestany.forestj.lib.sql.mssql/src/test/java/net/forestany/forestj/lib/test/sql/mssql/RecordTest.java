package net.forestany.forestj.lib.test.sql.mssql;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * record test class for mssql tests
 */
public class RecordTest {
	/**
	 * record test method for mssql tests
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testRecordMSSQL() {
		try {
			net.forestany.forestj.lib.test.sql.mssql.SetBase.setBase();
			net.forestany.forestj.lib.test.sqltest.RecordTest.testRecord();
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
