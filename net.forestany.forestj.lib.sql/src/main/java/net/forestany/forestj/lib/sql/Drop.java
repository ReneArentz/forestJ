package net.forestany.forestj.lib.sql;

import net.forestany.forestj.lib.sqlcore.BaseGateway;

/**
 * SQL class to generate a drop sql query with optional IF EXISTS property, called by toString method.
 */
public class Drop extends QueryAbstract {
	
	/* Fields */
	
	/**
	 * if exists flag - true: add IF EXISTS to drop query, but not if database gateway is ORACLE
	 */
	public boolean IfExists = false;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * constructor will be called with Query object, because all necessary database gateway, sql type and table information are part of Query class
	 * 
	 * @param p_o_query						query object with table and sql type information
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object
	 */
	public Drop(Query<?> p_o_query) throws IllegalArgumentException {
		super(p_o_query);
	}
	
	/**
	 * create drop sql string query
	 */
	@Override
	public String toString() {
		String s_foo = "DROP TABLE ";
		
		/* add IF EXISTS to drop query, but not if database gateway is ORACLE */
		if ( (this.IfExists) && (this.e_base != BaseGateway.ORACLE) ) {
			s_foo += " IF EXISTS ";
		}
		
		try {
			boolean b_exc = false;
			
			switch (this.e_base) {
				case MARIADB:
				case SQLITE:
				case NOSQLMDB:
					s_foo += "`" + this.s_table + "`";
				break;
				case MSSQL:
					s_foo += "[" + this.s_table + "]";
				break;
				case ORACLE:
				case PGSQL:
					s_foo += "\"" + this.s_table + "\"";
				break;
				default:
					b_exc = true;
				break;
			}
			
			if (b_exc) {
				throw new Exception("BaseGateway[" + this.e_base + "] not implemented");
			}
		} catch (Exception o_exc) { /* just set exception as query return, so database interface will have an exception as well */
			s_foo = " >>>>> Drop class Exception: [" + o_exc.toString() + "] <<<<< ";
		}
		
		return s_foo;
	}
}
