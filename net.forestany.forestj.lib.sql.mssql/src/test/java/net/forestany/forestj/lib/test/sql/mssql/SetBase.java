package net.forestany.forestj.lib.test.sql.mssql;

/**
 * set base settings for mssql tests
 */
public class SetBase {
	/**
	 * static method for base settings for mssql tests
	 * @throws Exception exceptions from BaseMSSQL constructor
	 */
	public static void setBase() throws Exception {
		net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
		
		o_glob.BaseGateway = net.forestany.forestj.lib.sqlcore.BaseGateway.MSSQL;
		o_glob.Base = new net.forestany.forestj.lib.sql.mssql.BaseMSSQL(net.forestany.forestj.lib.test.sqltest.BaseTest.s_baseHost + ":1433;trustServerCertificate", "test", "sa", "sa");
	}
}
