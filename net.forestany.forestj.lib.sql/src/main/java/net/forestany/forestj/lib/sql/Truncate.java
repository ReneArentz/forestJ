package net.forestany.forestj.lib.sql;

/**
 * SQL class to generate a truncate sql query, called by toString method.
 */
public class Truncate extends QueryAbstract {
	
	/* Fields */
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * constructor will be called with Query object, because all necessary database gateway, sql type and table information are part of Query class
	 * 
	 * @param p_o_query						query object with table and sql type information
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object
	 */
	public Truncate(Query<?> p_o_query) throws IllegalArgumentException {
		super(p_o_query);
	}
	
	/**
	 * create truncate sql string query
	 */
	@Override
	public String toString() {
		String s_foo = "";
		
		try {
			boolean b_exc = false;
			
			switch (this.e_base) {
				case MARIADB:
				case NOSQLMDB:
					s_foo = "TRUNCATE TABLE " + "`" + this.s_table + "`";
					break;
				case SQLITE:
					s_foo = "DELETE FROM " + "`" + this.s_table + "`";
					s_foo += this.s_querySeparator;
					s_foo += "VACUUM";
				break;
				case MSSQL:
					s_foo = "TRUNCATE TABLE " + "[" + this.s_table + "]";
				break;
				case ORACLE:
				case PGSQL:
					s_foo = "TRUNCATE TABLE " + "\"" + this.s_table + "\"";
				break;
				default:
					b_exc = true;
				break;
			}
			
			if (b_exc) {
				throw new Exception("BaseGateway[" + this.e_base + "] not implemented");
			}
		} catch (Exception o_exc) { /* just set exception as query return, so database interface will have an exception as well */
			s_foo = " >>>>> Truncate class Exception: [" + o_exc.toString() + "] <<<<< ";
		}
		
		return s_foo;
	}
}
