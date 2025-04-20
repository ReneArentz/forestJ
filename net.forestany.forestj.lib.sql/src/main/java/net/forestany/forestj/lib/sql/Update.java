package net.forestany.forestj.lib.sql;

/**
 * SQL class to generate a update sql query based on column value pair objects and where clauses, called by toString method.
 */
public class Update extends QueryAbstract {
	
	/* Fields */
	
	/**
	 * list of column values
	 */
	public java.util.List<ColumnValue> a_columnValues = new java.util.ArrayList<ColumnValue>();
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
	public Update(Query<?> p_o_query) throws IllegalArgumentException {
		super(p_o_query);
	}
	
	/**
	 * create update sql string query
	 */
	@Override
	public String toString() {
		return toString(true);
	}
	
	/**
	 * create update sql string query
	 * 
	 * @param p_b_returnWithWhere	true - with where clause, false - without where clause
	 * @return String
	 */
	public String toString(boolean p_b_returnWithWhere) {
		String s_foo = "";
		
		try {
			/* check if we have any column value pair objects for update query */
			if (this.a_columnValues.size() <= 0) {
				throw new Exception("ColumnValues object list is empty");
			}
			
			/* check if we have any where clauses, so we do not update all records by accident */
			if (this.a_where.size() <= 0) {
				throw new Exception("Where object list is empty");
			}
			
			boolean b_exc = false;
			
			switch (this.e_base) {
				case MARIADB:
				case SQLITE:
				case NOSQLMDB:
					s_foo = "UPDATE " + "`" + this.s_table + "`" + " SET ";
				break;
				case MSSQL:
					s_foo = "UPDATE " + "[" + this.s_table + "]" + " SET ";
				break;
				case ORACLE:
				case PGSQL:
					s_foo = "UPDATE " + "\"" + this.s_table + "\"" + " SET ";
				break;
				default:
					b_exc = true;
				break;
			}
			
			if (b_exc) {
				throw new Exception("BaseGateway[" + this.e_base + "] not implemented");
			}
			
			/* add all column value pairs to update query */
			for (ColumnValue o_columnValue : this.a_columnValues) {
				s_foo += o_columnValue.o_column.toString() + " = " + o_columnValue.o_value.toString() + ", ";
			}
			
			/* remove last ', ' separator */
			s_foo = s_foo.substring(0, s_foo.length() - 2);
			
			/* add all where clauses to update query */
			if ( (this.a_where.size() > 0) && (p_b_returnWithWhere) ) {
				s_foo += " WHERE ";
			
				for (Where o_where : this.a_where) {
					s_foo += o_where.toString();
				}
			}
		} catch (Exception o_exc) { /* just set exception as query return, so database interface will have an exception as well */
			s_foo = " >>>>> Update class Exception: [" + o_exc.toString() + "] <<<<< ";
		}
		
		return s_foo;
	}
}
