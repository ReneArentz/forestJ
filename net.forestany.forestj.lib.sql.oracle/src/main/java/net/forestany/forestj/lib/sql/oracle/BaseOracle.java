package net.forestany.forestj.lib.sql.oracle;

/**
 * BaseMariaDB class for connection to a database and fetching queries to it.
 * requires JDBC.
 */
public class BaseOracle extends net.forestany.forestj.lib.sql.BaseJDBC {
	
	/* Fields */
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * BaseMariaDB constructor, initiating database server connection
	 * 
	 * @param p_s_host						address of database server, optional port specification e.g. 'localhost:3306'
	 * @throws IllegalArgumentException		illegal database gateway or illegal host parameter
	 * @throws IllegalAccessException		error creating database server connection
	 */
	public BaseOracle(String p_s_host) throws IllegalArgumentException, IllegalAccessException {
		this(p_s_host, "");
	}
	
	/**
	 * BaseMariaDB constructor, initiating database server connection
	 * 
	 * @param p_s_host						address of database server, optional port specification e.g. 'localhost:3306'
	 * @param p_s_datasource				parameter for selecting a database within target database server
	 * @throws IllegalArgumentException		illegal database gateway or illegal host parameter
	 * @throws IllegalAccessException		error creating database server connection
	 */
	public BaseOracle(String p_s_host, String p_s_datasource) throws IllegalArgumentException, IllegalAccessException {
		this(p_s_host, p_s_datasource, "", "");
	}
	
	/**
	 * BaseMariaDB constructor, initiating database server connection
	 * 
	 * @param p_s_host						address of database server, optional port specification e.g. 'localhost:3306'
	 * @param p_s_datasource				parameter for selecting a database within target database server
	 * @param p_s_user						user name for database login
	 * @throws IllegalArgumentException		illegal database gateway or illegal host parameter
	 * @throws IllegalAccessException		error creating database server connection
	 */
	public BaseOracle(String p_s_host, String p_s_datasource, String p_s_user) throws IllegalArgumentException, IllegalAccessException {
		this(p_s_host, p_s_datasource, p_s_user, "");
	}
	
	/**
	 * BaseMariaDB constructor, initiating database server connection
	 * 
	 * @param p_s_host						address of database server, optional port specification e.g. 'localhost:3306'
	 * @param p_s_datasource				parameter for selecting a database within target database server
	 * @param p_s_user						user name for database login
	 * @param p_s_password					user's password for database login
	 * @throws IllegalArgumentException		illegal database gateway or illegal host parameter
	 * @throws IllegalAccessException		error creating database server connection
	 */
	public BaseOracle(String p_s_host, String p_s_datasource, String p_s_user, String p_s_password) throws IllegalArgumentException, IllegalAccessException {
		super(net.forestany.forestj.lib.sqlcore.BaseGateway.ORACLE, p_s_host, p_s_datasource, p_s_user, p_s_password);
	}
}