package net.forestany.forestj.lib.test.sql.sqlite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * query test class for pgsql tests
 */
public class QueryTest {
	/**
	 * query test method for pgsql tests
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testQuerySQLite() {
		try {
			net.forestany.forestj.lib.test.sql.sqlite.SetBase.setBase(null);
			net.forestany.forestj.lib.test.sqltest.QueryTest.testQuery();
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
