package net.forestany.forestj.lib.sql;

import net.forestany.forestj.lib.sqlcore.BaseGateway;

/**
 * SQL class to generate an insert sql query based on column value pair objects, called by toString method.
 */
public class Insert extends QueryAbstract {
	
	/* Fields */
	
	/**
	 * list of column values
	 */
	public java.util.List<ColumnValue> a_columnValues = new java.util.ArrayList<ColumnValue>();
	/**
	 * mssql last insert id column
	 */
	public Column o_mssqlLastInsertIdColumn = null;
	/**
	 * nosqlmdb column for auto increment
	 */
	public Column o_nosqlmdbColumnAutoIncrement = null;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * constructor will be called with Query object, because all necessary database gateway, sql type and table information are part of Query class
	 * 
	 * @param p_o_query						query object with table and sql type information
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object
	 */
	public Insert(Query<?> p_o_query) throws IllegalArgumentException {
		super(p_o_query);
	}
	
	/**
	 * create insert sql string query
	 */
	@Override
	public String toString() {
		String s_foo = "";
		
		try {
			/* check if we have column value pair objects for insert query */
			if (this.a_columnValues.size() <= 0) {
				throw new Exception("ColumnValues object list is empty");
			}
			
			String s_foo1 = "";
			String s_foo2 = "";
			
			boolean b_exc = false;
			
			switch (this.e_base) {
				case MARIADB:
				case SQLITE:
				case NOSQLMDB:
					s_foo = "INSERT INTO " + "`" + this.s_table + "`" + " (";
				break;
				case MSSQL:
					s_foo = "INSERT INTO " + "[" + this.s_table + "]" + " (";
				break;
				case ORACLE:
				case PGSQL:
					s_foo = "INSERT INTO " + "\"" + this.s_table + "\"" + " (";
				break;
				default:
					b_exc = true;
				break;
			}
			
			if (b_exc) {
				throw new Exception("BaseGateway[" + this.e_base + "] not implemented");
			}
			
			/* add all column value pairs to insert query */
			for (ColumnValue o_columnValue : this.a_columnValues) {
				s_foo1 += o_columnValue.o_column.toString() + ", ";
				s_foo2 += o_columnValue.o_value.toString() + ", ";
			}
			
			/* remove last ',' separator */
			s_foo1 = s_foo1.substring(0, s_foo1.length() - 2);
			s_foo2 = s_foo2.substring(0, s_foo2.length() - 2);
			
			s_foo += s_foo1;
			
			/* alternative if SELECT IDENT_CURRENT('table_name') is not working, use this insert query then with executeQuery and a ResultSet as return */
			if ( (this.e_base == BaseGateway.MSSQL) && (this.o_mssqlLastInsertIdColumn != null) ) {
				s_foo += ") OUTPUT [INSERTED].[" + this.o_mssqlLastInsertIdColumn.s_column + "] AS 'LastInsertId' VALUES (";
			} else {
				s_foo += ") VALUES (";
			}
			
			s_foo += s_foo2;
			s_foo += ")";
		} catch (Exception o_exc) { /* just set exception as query return, so database interface will have an exception as well */
			s_foo = " >>>>> Insert class Exception: [" + o_exc.toString() + "] <<<<< ";
		}
		
		return s_foo;
	}
}
