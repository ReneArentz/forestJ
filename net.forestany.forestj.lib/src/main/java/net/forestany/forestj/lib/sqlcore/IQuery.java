package net.forestany.forestj.lib.sqlcore;

/**
 * Interface for query class to use it as generic type in other classes
 * 
 * @param <T> Query type class which must implement this interface
 */
public interface IQuery<T> {
	/**
	 * get base-gateway of current query
	 * 
	 * @return BaseGateway
	 */
	public BaseGateway getBaseGateway();
	/**
	 * get sql type of current query
	 * 
	 * @return SqlType
	 */
    public SqlType getSqlType();
    /**
     * get table name of current query
     * 
     * @return table
     */
    public String getTable();

    /**
     * get query object
     * 
     * @return &lt;T&gt;
     */
    public T getQuery();

    /**
     * set query with string
     * 
     * @param p_s_query string
     */
    public void setQuery(String p_s_query);

    /**
     * convert query to string
     * 
     * @return String
     */
    public String toString();

    /**
     * get all constaints of a sql type
     * 
     * @param p_s_constraintType	sql type as string
     * @return String
     */
    public String constraintTypeAllocation(String p_s_constraintType);
}
