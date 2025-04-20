package net.forestany.forestj.lib.test.sql.nosqlmdb;

/**
 * set base settings for nosqlmdb tests
 */
public class SetBase {
	/**
	 * static method for base settings for nosqlmdb tests
	 * @throws Exception exceptions from BaseNoSQLMDB constructor
	 */
	public static void setBase() throws Exception {
		net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
		
		o_glob.BaseGateway = net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB;
		o_glob.Base = new net.forestany.forestj.lib.sql.nosqlmdb.BaseNoSQLMDB(net.forestany.forestj.lib.test.sqltest.BaseTest.s_baseHost + ":27017", "test");
	}
}
