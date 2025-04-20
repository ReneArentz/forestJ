package net.forestany.forestj.lib.test.sql.oracle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * query test class for oracle tests
 */
public class QueryTest {
	/**
	 * query test method for oracle tests
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testQueryOracle() {
		try {
			net.forestany.forestj.lib.test.sql.oracle.SetBase.setBase();
			net.forestany.forestj.lib.test.sqltest.QueryTest.testQuery();
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
