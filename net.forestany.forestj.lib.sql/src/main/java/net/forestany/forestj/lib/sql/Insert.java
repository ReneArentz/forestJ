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
	 * list of multiple records
	 */
	public java.util.List<java.util.List<ColumnValue>> a_multipleRecords = new java.util.ArrayList<java.util.List<ColumnValue>>();
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
			/* check if we have column value pair objects or multiple records for insert query */
			if ((this.a_columnValues.size() <= 0) && (this.a_multipleRecords.size() <= 0)) {
				throw new Exception("ColumnValues object list and MultipleRecords object list are empty");
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

			/* insert query for one record */
			if (this.a_columnValues.size() > 0) {
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
			} else if (this.a_multipleRecords.size() > 0) { /* insert query for multiple records */
				java.util.List<String> a_columns = new java.util.ArrayList<String>();
				boolean b_firstRecord = true;

				/* iterate all records */
				for (java.util.List<ColumnValue> a_record : this.a_multipleRecords) {
					if (b_firstRecord) {
						String s_values = "(";
						
						if ((this.e_base == BaseGateway.ORACLE) && (net.forestany.forestj.lib.Global.get().Base.getOracleInsertManyLegacyMode())) {
							/*
							INSERT INTO "table" ("table"."column2", "table"."column3")
							SELECT ('Hello', 'World') FROM dual UNION ALL
							SELECT ('Hello', 'World') FROM dual UNION ALL
							SELECT ('Hello', 'World') FROM dual;
							*/
							s_values = "SELECT ";
						}

						/* note columns of first record, so we can check if all other records are using the same columns */
						for (ColumnValue o_columnValue : a_record) {
							a_columns.add(o_columnValue.o_column.toString());
							s_foo1 += o_columnValue.o_column.toString() + ", ";
							s_values += o_columnValue.o_value.toString() + ", ";
						}

						/* remove last ', ' separator */
						s_foo1 = s_foo1.substring(0, s_foo1.length() - 2);
						s_values = s_values.substring(0, s_values.length() - 2);

						if ((this.e_base == BaseGateway.ORACLE) && (net.forestany.forestj.lib.Global.get().Base.getOracleInsertManyLegacyMode())) {
							s_values += " FROM dual UNION ALL ";
						} else {
							s_values += "),";
						}

						s_foo2 += s_values;

						b_firstRecord = false;
					} else {
						String s_values = "(";

						if ((this.e_base == BaseGateway.ORACLE) && (net.forestany.forestj.lib.Global.get().Base.getOracleInsertManyLegacyMode())) {
							s_values = "SELECT ";
						}

						for (ColumnValue o_columnValue : a_record) {
							/* check if record is using the same columns as the first record */
							if (!a_columns.contains(o_columnValue.o_column.toString())) {
								throw new Exception("Unknown column '" + o_columnValue.o_column.toString() + "' for multiple record insert. Expected the following columns: " + net.forestany.forestj.lib.Helper.printArrayList(a_columns));
							}

							s_values += o_columnValue.o_value.toString() + ", ";
						}

						/* remove last ', ' separator */
						s_values = s_values.substring(0, s_values.length() - 2);

						if ((this.e_base == BaseGateway.ORACLE) && (net.forestany.forestj.lib.Global.get().Base.getOracleInsertManyLegacyMode())) {
							s_values += " FROM dual UNION ALL ";
						} else {
							s_values += "),";
						}

						s_foo2 += s_values;
					}
				}

				if ((this.e_base == BaseGateway.ORACLE) && (net.forestany.forestj.lib.Global.get().Base.getOracleInsertManyLegacyMode())) {
					/* remove last ' UNION ALL ' separator */
					s_foo2 = s_foo2.substring(0, s_foo2.length() - 11);
				} else {
					/* remove last ',' separator */
					s_foo2 = s_foo2.substring(0, s_foo2.length() - 1);
				}

				s_foo += s_foo1;

				if ((this.e_base == BaseGateway.ORACLE) && (net.forestany.forestj.lib.Global.get().Base.getOracleInsertManyLegacyMode())) {
					s_foo += ") ";
				} else {
					s_foo += ") VALUES ";
				}

				s_foo += s_foo2;
			}
		} catch (Exception o_exc) { /* just set exception as query return, so database interface will have an exception as well */
			s_foo = " >>>>> Insert class Exception: [" + o_exc.toString() + "] <<<<< ";
		}
		
		return s_foo;
	}
}
