package net.forestany.forestj.lib.test.sql.oracle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * record test class for oracle tests
 */
public class RecordTest {
	/**
	 * record test method for oracle tests
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testRecordOracle() {
		try {
			net.forestany.forestj.lib.test.sql.oracle.SetBase.setBase();
			net.forestany.forestj.lib.test.sqltest.RecordTest.testRecord();
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
