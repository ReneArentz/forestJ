package net.forestany.forestj.lib.test.sql.pgsql;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * base test class for pgsql tests
 */
public class BaseTest {
	/**
	 * base test method for pgsql tests
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testBasePGSQL() {
		try {
			net.forestany.forestj.lib.test.sql.pgsql.SetBase.setBase();
			net.forestany.forestj.lib.test.sqltest.BaseTest.testBase();
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
