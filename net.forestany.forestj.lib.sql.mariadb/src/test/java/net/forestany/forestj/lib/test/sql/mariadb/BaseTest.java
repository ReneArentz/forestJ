package net.forestany.forestj.lib.test.sql.mariadb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * base test class for mariadb tests
 */
public class BaseTest {
	/**
	 * base test method for mariadb tests
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testBaseMariaDB() {
		try {
			net.forestany.forestj.lib.test.sql.mariadb.SetBase.setBase();
			net.forestany.forestj.lib.test.sqltest.BaseTest.testBase();
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
