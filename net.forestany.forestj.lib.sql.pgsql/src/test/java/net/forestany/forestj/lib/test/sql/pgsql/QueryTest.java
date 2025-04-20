package net.forestany.forestj.lib.test.sql.pgsql;

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
	public void testQueryPGSQL() {
		try {
			net.forestany.forestj.lib.test.sql.pgsql.SetBase.setBase();
			net.forestany.forestj.lib.test.sqltest.QueryTest.testQuery();
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
