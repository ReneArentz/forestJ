package net.forestany.forestj.lib.test.sql.mariadb;

/**
 * set base settings for mariadb tests
 */
public class SetBase {
	/**
	 * static method for base settings for mariadb tests
	 * @throws Exception exceptions from BaseMariaDB constructor
	 */
	public static void setBase() throws Exception {
		net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
		
		o_glob.BaseGateway = net.forestany.forestj.lib.sqlcore.BaseGateway.MARIADB;
		o_glob.Base = new net.forestany.forestj.lib.sql.mariadb.BaseMariaDB(net.forestany.forestj.lib.test.sqltest.BaseTest.s_baseHost + ":3306", "test", "root", "root");
	}
}
