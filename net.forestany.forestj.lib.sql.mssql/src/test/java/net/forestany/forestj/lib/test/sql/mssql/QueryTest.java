package net.forestany.forestj.lib.test.sql.mssql;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * query test class for mssql tests
 */
public class QueryTest {
	/**
	 * query test method for mssql tests
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testQueryMSSQL() {
		try {
			net.forestany.forestj.lib.test.sql.mssql.SetBase.setBase();
			net.forestany.forestj.lib.test.sqltest.QueryTest.testQuery();
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
