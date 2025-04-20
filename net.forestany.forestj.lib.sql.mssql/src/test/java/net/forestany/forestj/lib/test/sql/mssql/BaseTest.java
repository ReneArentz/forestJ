package net.forestany.forestj.lib.test.sql.mssql;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * base test class for mssql tests
 */
public class BaseTest {
	/**
	 * base test method for mssql tests
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testBaseMSSQL() {
		try {
			net.forestany.forestj.lib.test.sql.mssql.SetBase.setBase();
			net.forestany.forestj.lib.test.sqltest.BaseTest.testBase();
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
