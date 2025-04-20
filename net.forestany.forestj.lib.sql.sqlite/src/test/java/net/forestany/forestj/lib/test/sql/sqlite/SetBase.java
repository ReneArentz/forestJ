package net.forestany.forestj.lib.test.sql.sqlite;

/**
 * set base settings for pgsql tests
 */
public class SetBase {
	/**
	 * static method for base settings for pgsql tests
	 * @param p_s_dir test directory
	 * @throws Exception exceptions from BasePGSQL constructor
	 */
	public static void setBase(String p_s_dir) throws Exception {
		net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
		
		o_glob.BaseGateway = net.forestany.forestj.lib.sqlcore.BaseGateway.SQLITE;
		
		if (p_s_dir != null) {
			o_glob.Base = new net.forestany.forestj.lib.sql.sqlite.BaseSQLite(p_s_dir + "testBase.db");
		}
	}
}
