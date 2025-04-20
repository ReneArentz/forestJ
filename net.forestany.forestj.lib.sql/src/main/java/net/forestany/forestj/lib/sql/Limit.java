package net.forestany.forestj.lib.sql;

import net.forestany.forestj.lib.sqlcore.SqlType;

/**
 * SQL class to generate a limit sql clause based on a start and interval value, called by toString method.
 */
public class Limit extends QueryAbstract {
	
	/* Fields */
	
	/**
	 * start
	 */
	public int i_start = 0;
	/**
	 * interval
	 */
	public int i_interval = 0;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * constructor will be called with Query object, because all necessary database gateway, sql type and table information are part of Query class
	 * 
	 * @param p_o_query						query object with table and sql type information
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object
	 */
	public Limit(Query<?> p_o_query) throws IllegalArgumentException {
		this(p_o_query, 0, 0);
	}
	
	/**
	 * constructor will be called with Query object, because all necessary database gateway, sql type and table information are part of Query class
	 * 
	 * @param p_o_query						query object with table and sql type information
	 * @param p_i_start						start integer value for interval
	 * @param p_i_interval					interval integer value
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object
	 */
	public Limit(Query<?> p_o_query, int p_i_start, int p_i_interval) throws IllegalArgumentException {
		super(p_o_query);
		
		this.i_start = p_i_start;
		this.i_interval = p_i_interval;
	}
	
	/**
	 * create limit sql clause as string
	 */
	@Override
	public String toString() {
		String s_foo = "";
		boolean b_exc = false;

		try {
			/* only select sql type allowed */
			if (this.e_sqlType != SqlType.SELECT) {
				throw new Exception("SqlType must be SELECT, but is '" + this.e_sqlType + "'");		
			}
			
			switch (this.e_base) {
				case MARIADB:
				case SQLITE:
				case NOSQLMDB:
					s_foo = " LIMIT " + this.i_start + ", " + this.i_interval;
				break;
				case MSSQL:
				case ORACLE:
					s_foo = " OFFSET " + this.i_start + " ROWS FETCH NEXT " + this.i_interval + " ROWS ONLY";
				break;
				case PGSQL:
					s_foo = " LIMIT " + this.i_interval + " OFFSET " + this.i_start;
				break;
				default:
					b_exc = true;
				break;
			}
			
			if (b_exc) {
				throw new Exception("BaseGateway[" + this.e_base + "] not implemented");
			}
		} catch (Exception o_exc) { /* just set exception as query return, so database interface will have an exception as well */
			s_foo = " >>>>> Limit class Exception: [" + o_exc.toString() + "] <<<<< ";
		}
		
		return s_foo;
	}
}
