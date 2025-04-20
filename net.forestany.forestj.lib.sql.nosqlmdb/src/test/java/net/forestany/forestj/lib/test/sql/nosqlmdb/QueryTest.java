package net.forestany.forestj.lib.test.sql.nosqlmdb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * query test class for nosqlmdb tests
 */
public class QueryTest {
	/**
	 * query test method for nosqlmdb tests
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testQueryNOSQMDB() {
		try {
			net.forestany.forestj.lib.test.sql.nosqlmdb.SetBase.setBase();
			net.forestany.forestj.lib.test.sqltest.QueryTest.testQuery();
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
