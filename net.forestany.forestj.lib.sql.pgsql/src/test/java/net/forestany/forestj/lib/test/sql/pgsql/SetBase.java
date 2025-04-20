package net.forestany.forestj.lib.test.sql.pgsql;

/**
 * set base settings for pgsql tests
 */
public class SetBase {
	/**
	 * static method for base settings for pgsql tests
	 * @throws Exception exceptions from BasePGSQL constructor
	 */
	public static void setBase() throws Exception {
		net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
		
		o_glob.BaseGateway = net.forestany.forestj.lib.sqlcore.BaseGateway.PGSQL;
		o_glob.Base = new net.forestany.forestj.lib.sql.pgsql.BasePGSQL(net.forestany.forestj.lib.test.sqltest.BaseTest.s_baseHost + ":5432", "test", "postgres", "root");
	}
}
