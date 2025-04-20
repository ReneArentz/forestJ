package net.forestany.forestj.lib.test.sql.oracle;

/**
 * set base settings for oracle tests
 */
public class SetBase {
	/**
	 * static method for base settings for oracle tests
	 * @throws Exception exceptions from BaseOracle constructor
	 */
	public static void setBase() throws Exception {
		net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
		
		o_glob.BaseGateway = net.forestany.forestj.lib.sqlcore.BaseGateway.ORACLE;
		//o_glob.Base = new net.forestany.forestj.lib.sql.oracle.BaseOracle(net.forestany.forestj.lib.test.sqltest.BaseTest.s_baseHost + ":1521", ":xe", "system", "root");
		o_glob.Base = new net.forestany.forestj.lib.sql.oracle.BaseOracle(net.forestany.forestj.lib.test.sqltest.BaseTest.s_baseHost + ":1521", ":free", "system", "root");
	}
}
