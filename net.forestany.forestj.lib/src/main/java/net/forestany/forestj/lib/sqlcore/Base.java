package net.forestany.forestj.lib.sqlcore;

/**
 * Base class for any kind of sql connection to a database and fetching queries to it
 * requires JDBC or NoSQL MDB java driver
 * supported databases: @see BaseGateway
 */
public abstract class Base {
	
	/* Fields */
	
	/**
	 * amount of queries
	 */
	protected int i_amountQueries = 0;
	/**
	 * list of queries as strings
	 */
	protected java.util.List<String> a_queries = new java.util.ArrayList<String>();
	/**
	 * base-gateway
	 */
	protected BaseGateway e_baseGateway;
	/**
	 * string query
	 */
	protected String s_query = null;
	/**
	 * temp connection string
	 */
	protected String s_tempConnectionString = null;
	/**
	 * skip query for last insert id flag
	 */
	protected boolean b_skipQueryLastInsertId = false;
	
	/* Properties */
	
	/**
	 * get amount of queries
	 * 
	 * @return int
	 */
	public int AmountQueries() {
		return this.i_amountQueries;
	}
	
	/**
	 * get list of queries
	 * 
	 * @return java.util.List&lt;String&gt;
	 */
	public java.util.List<String> Queries() {
		return this.a_queries;
	}
	
	/**
	 * set flag to skip query for last insert id
	 * 
	 * @param p_b_value boolean
	 */
	public void setSkipQueryLastInsertId(boolean p_b_value) {
		this.b_skipQueryLastInsertId = p_b_value;
	}
	
	/* Methods */
	
	/**
	 * empty constructor
	 */
	public Base() {
		
	}
	
	/**
	 * Test connection to database with current connection
	 * 
	 * @return true - connection successful, false - connection failed
	 */
	abstract public boolean testConnection();
	
	/**
	 * Check if current connection is not closed
	 * 
	 * @return true - connection is still valid, false - connection closed, a new one must be established to continue database communication
	 */
	abstract public boolean isClosed();
	
	/**
	 * Method for closing database connection, ignoring any sql exceptions
	 */
	abstract public void closeConnection();
	
	/**
	 * Fetch a query or a amount of queries separated by QueryAbstract.s_querySeparator, with auto commit
	 * 
	 * @param p_o_sqlQuery				query object of Query class &lt;? must be of type QueryAbstract&gt;
	 * @return							list of hash maps, key(string) -> column name + value(object) -> column value of a database record
	 * @throws IllegalAccessException	exception accessing column type, column name or just column value of current result set record
	 */
	abstract public java.util.List<java.util.LinkedHashMap<String, Object>> fetchQuery(IQuery<?> p_o_sqlQuery) throws IllegalAccessException;
	
	/**
	 * Fetch a query or a amount of queries separated by QueryAbstract.s_querySeparator
	 * 
	 * @param p_o_sqlQuery				query object of Query class &lt;? must be of type QueryAbstract&gt;
	 * @param p_b_autoTransaction			transaction flag: true - commit database after each execution of query object automatically, false - do not commit automatically
	 * @return							list of hash maps, key(string) -> column name + value(object) -> column value of a database record
	 * @throws IllegalAccessException	exception accessing column type, column name or just column value of current result set record
	 */
	abstract public java.util.List<java.util.LinkedHashMap<String, Object>> fetchQuery(IQuery<?> p_o_sqlQuery, boolean p_b_autoTransaction) throws IllegalAccessException;

	/**
	 * Sets manual auto commit value
	 * 
	 * @param p_b_autoCommit			true - auto commit is set, false - auto commit is not set so a whole transaction can start
	 * @throws IllegalAccessException	could not manipulate auto commit setting in current database connection
	 */
	abstract public void manualAutoCommit(boolean p_b_autoCommit) throws IllegalAccessException;
	
	/**
	 * Manual commit current transaction
	 * 
	 * @throws IllegalAccessException	could not commit in current database connection
	 */
	abstract public void manualCommit() throws IllegalAccessException;
	
	/**
	 * Manual rollback current transaction
	 * 
	 * @throws IllegalAccessException	could not rollback in current database connection 
	 */
	abstract public void manualRollback() throws IllegalAccessException;
}
