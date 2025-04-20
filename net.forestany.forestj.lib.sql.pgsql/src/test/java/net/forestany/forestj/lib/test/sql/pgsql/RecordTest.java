package net.forestany.forestj.lib.test.sql.pgsql;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * record test class for pgsql tests
 */
public class RecordTest {
	/**
	 * record test method for pgsql tests
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testRecordPGSQL() {
		try {
			net.forestany.forestj.lib.test.sql.pgsql.SetBase.setBase();
			net.forestany.forestj.lib.test.sqltest.RecordTest.testRecord();
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
