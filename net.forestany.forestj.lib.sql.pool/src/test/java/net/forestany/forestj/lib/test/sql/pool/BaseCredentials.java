package net.forestany.forestj.lib.test.sql.pool;

/**
 * base credential class for base pool test
 */
public class BaseCredentials {
	/**
	 * base-gateway of current Global.BaseGateway
	 */
	public net.forestany.forestj.lib.sqlcore.BaseGateway e_baseGatewayBaseThread = null;
	/**
	 * host base thread
	 */
	public String s_hostBaseThread = null;
	/**
	 * datasource base thread
	 */
	public String s_datasourceBaseThread = null;
	/**
	 * user base thread
	 */
	public String s_userBaseThread = null;
	/**
	 * password base thread
	 */
	public String s_passwordBaseThread = null;
	
	/**
	 * base-gateways which are used for base pool test
	 * sqlite is the only unit test we can try on any platform, please feel free to use other database gateways or test it in a separate sandbox
	 * 
	 * @return java.util.Map&lt;String, Integer&gt;
	 */
	public static java.util.Map<String, Integer> BaseGateways() {
		java.util.Map<String, Integer> a_return = new java.util.HashMap<String, Integer>();
		
		a_return.put(net.forestany.forestj.lib.sqlcore.BaseGateway.MARIADB.toString(), 0);
		a_return.put(net.forestany.forestj.lib.sqlcore.BaseGateway.SQLITE.toString(), 1);
		a_return.put(net.forestany.forestj.lib.sqlcore.BaseGateway.MSSQL.toString(), 2);
		a_return.put(net.forestany.forestj.lib.sqlcore.BaseGateway.PGSQL.toString(), 3);
		a_return.put(net.forestany.forestj.lib.sqlcore.BaseGateway.ORACLE.toString(), 4);
		a_return.put(net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB.toString(), 5);
		
		return a_return;
	}
	
	/**
	 * base-credential class constructor
	 * 
	 * @param p_s_testDirectory test directory
	 * @throws IllegalArgumentException illegal database gateway or illegal host parameter
	 * @throws IllegalAccessException error creating database server connection
	 */
	public BaseCredentials(String p_s_testDirectory) throws IllegalArgumentException, IllegalAccessException {
		net.forestany.forestj.lib.Global o_glob = net.forestany.forestj.lib.Global.get();
		
		this.e_baseGatewayBaseThread = o_glob.BaseGateway;
		
		String s_ip = net.forestany.forestj.lib.test.sqltest.BaseTest.s_baseHost;
		
		if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.MARIADB) {
			this.s_hostBaseThread = s_ip + ":3306";
			this.s_datasourceBaseThread = "test";
			this.s_userBaseThread = "root";
			this.s_passwordBaseThread = "root";
			o_glob.Base = new net.forestany.forestj.lib.sql.mariadb.BaseMariaDB(this.s_hostBaseThread, this.s_datasourceBaseThread, this.s_userBaseThread, this.s_passwordBaseThread);
		} else if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.SQLITE) {
			this.s_hostBaseThread = p_s_testDirectory + "testBase.db";
			o_glob.Base = new net.forestany.forestj.lib.sql.sqlite.BaseSQLite( this.s_hostBaseThread);
		} else if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.PGSQL) {
			this.s_hostBaseThread = s_ip + ":5432";
			this.s_datasourceBaseThread = "test";
			this.s_userBaseThread = "postgres";
			this.s_passwordBaseThread = "root";
			o_glob.Base = new net.forestany.forestj.lib.sql.pgsql.BasePGSQL(this.s_hostBaseThread, this.s_datasourceBaseThread, this.s_userBaseThread, this.s_passwordBaseThread);
		} else if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.MSSQL) {
			this.s_hostBaseThread = s_ip + ":1433;trustServerCertificate";
			this.s_datasourceBaseThread = "test";
			this.s_userBaseThread = "sa";
			this.s_passwordBaseThread = "sa";
			o_glob.Base = new net.forestany.forestj.lib.sql.mssql.BaseMSSQL(this.s_hostBaseThread, this.s_datasourceBaseThread, this.s_userBaseThread, this.s_passwordBaseThread);
		} else if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.ORACLE) {
			this.s_hostBaseThread = s_ip + ":1521";
			//this.s_datasourceBaseThread = ":xe";
			this.s_datasourceBaseThread = ":free";
			this.s_userBaseThread = "system";
			this.s_passwordBaseThread = "root";
			o_glob.Base = new net.forestany.forestj.lib.sql.oracle.BaseOracle(this.s_hostBaseThread, this.s_datasourceBaseThread, this.s_userBaseThread, this.s_passwordBaseThread);
		} else if (o_glob.BaseGateway == net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB) {
			this.s_hostBaseThread = s_ip + ":27017";
			this.s_datasourceBaseThread = "test";
			o_glob.Base = new net.forestany.forestj.lib.sql.nosqlmdb.BaseNoSQLMDB(this.s_hostBaseThread, this.s_datasourceBaseThread);
		}
	}
}
