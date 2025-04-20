package net.forestany.forestj.lib.sql;

/**
 * SQL class to generate a delete sql query with optional where objects, called by toString method.
 */
public class Delete extends QueryAbstract {
	
	/* Fields */
	
	/**
	 * list of where clauses
	 */
	public java.util.List<Where> a_where = new java.util.ArrayList<Where>();
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * constructor will be called with Query object, because all necessary database gateway, sql type and table information are part of Query class
	 * 
	 * @param p_o_query						query object with table and sql type information
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object
	 */
	public Delete(Query<?> p_o_query) throws IllegalArgumentException {
		super(p_o_query);
	}
	
	/**
	 * create delete sql string query
	 */
	@Override
	public String toString() {
		String s_foo = "";
		
		try {
			boolean b_exc = false;
			
			switch (this.e_base) {
				case MARIADB:
				case SQLITE:
				case NOSQLMDB:
					s_foo = "DELETE FROM " + "`" + this.s_table + "`";
				break;
				case MSSQL:
					s_foo = "DELETE FROM " + "[" + this.s_table + "]";
				break;
				case ORACLE:
				case PGSQL:
					s_foo = "DELETE FROM " + "\"" + this.s_table + "\"";
				break;
				default:
					b_exc = true;
				break;
			}
			
			if (b_exc) {
				throw new Exception("BaseGateway[" + this.e_base + "] not implemented");
			}
			
			/* add where clause if where objects are available */
			if (this.a_where.size() > 0) {
				s_foo += " WHERE ";
			
				for (Where o_where : this.a_where) {
					s_foo += o_where.toString();
				}
			}
		} catch (Exception o_exc) { /* just set exception as query return, so database interface will have an exception as well */
			s_foo = " >>>>> Delete class Exception: [" + o_exc.toString() + "] <<<<< ";
		}
		
		return s_foo;
	}
}
