package net.forestany.forestj.lib.test.sql.mariadb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * query test class for mariadb tests
 */
public class QueryTest {
	/**
	 * query test method for mariadb tests
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testQueryMariaDB() {
		try {
			net.forestany.forestj.lib.test.sql.mariadb.SetBase.setBase();
			net.forestany.forestj.lib.test.sqltest.QueryTest.testQuery();
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
