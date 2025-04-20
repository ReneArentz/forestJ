package net.forestany.forestj.lib.sql;

import net.forestany.forestj.lib.sqlcore.BaseGateway;

/**
 * SQL class to generate a create sql query based on column structures, called by toString method.
 */
public class Create extends QueryAbstract {
	
	/* Fields */
	
	/**
	 * list of columns
	 */
	public java.util.List<ColumnStructure> a_columns = new java.util.ArrayList<ColumnStructure>();
	
	/* Properties */
	
	/**
	 * get columns as list
	 * 
	 * @return java.util.List&lt;ColumnStructure&gt;
	 */
	public java.util.List<ColumnStructure> getColumns() {
		return this.a_columns;
	}
	
	/* Methods */
	
	/**
	 * constructor will be called with Query object, because all necessary database gateway, sql type and table information are part of Query class
	 * 
	 * @param p_o_query						query object with table and sql type information
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object
	 */
	public Create(Query<?> p_o_query) throws IllegalArgumentException {
		super(p_o_query);
	}
	
	/**
	 * create 'create' sql string query
	 */
	@Override
	public String toString() {
		String s_foo = "";
		
		try {
			/* check if we have column for create query, not necessary for MONGODB */
			if ( (this.e_base != BaseGateway.NOSQLMDB) && (this.a_columns.size() <= 0) ) {
				throw new Exception("ColumnsStructure object list is empty");
			}
			
			boolean b_exc = false;
			
			switch (this.e_base) {
				case MARIADB:
				case SQLITE:
				case NOSQLMDB:
					s_foo = "CREATE TABLE " + "`" + this.s_table + "`" + " (";
					
					/* add columns structures to create query */ 
					for (ColumnStructure o_columnStructure : this.a_columns) {
						s_foo += o_columnStructure.toString() + ", ";
					}
					
					/* remove last ',' separator */ 
					s_foo = s_foo.substring(0, s_foo.length() - 2);
					s_foo += ")";
				break;
				case MSSQL:
					s_foo = "CREATE TABLE " + "[" + this.s_table + "]" + " (";
					
					/* add columns structures to create query */ 
					for (ColumnStructure o_columnStructure : this.a_columns) {
						s_foo += o_columnStructure.toString() + ", ";
					}
					
					/* remove last ',' separator */ 
					s_foo = s_foo.substring(0, s_foo.length() - 2);
					s_foo += ")";
				break;
				case ORACLE:
				case PGSQL:
					s_foo = "CREATE TABLE " + "\"" + this.s_table + "\"" + " (";
					
					/* add columns structures to create query */ 
					for (ColumnStructure o_columnStructure : this.a_columns) {
						s_foo += o_columnStructure.toString() + ", ";
					}
					
					/* remove last ',' separator */ 
					s_foo = s_foo.substring(0, s_foo.length() - 2);
					s_foo += ")";
				break;
				default:
					b_exc = true;
				break;
			}
			
			if (b_exc) {
				throw new Exception("BaseGateway[" + this.e_base + "] not implemented");
			}
		} catch (Exception o_exc) { /* just set exception as query return, so database interface will have an exception as well */
			s_foo = " >>>>> Create class Exception: [" + o_exc.toString() + "] <<<<< ";
		}
		
		return s_foo;
	}
}
