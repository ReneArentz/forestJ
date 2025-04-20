package net.forestany.forestj.lib.test.sql.oracle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * base test class for oracle tests
 */
public class BaseTest {
	/**
	 * base test method for oracle tests
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testBaseOracle() {
		try {
			net.forestany.forestj.lib.test.sql.oracle.SetBase.setBase();
			net.forestany.forestj.lib.test.sqltest.BaseTest.testBase();
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
