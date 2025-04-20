package net.forestany.forestj.lib.sql;

/**
 * Column class with column, name(alias) and aggregation properties.
 */
public class Column extends QueryAbstract {
	
	/* Fields */
	
	/**
	 * column
	 */
	public String s_column = "";
	/**
	 * name
	 */
	public String s_name = "";
	/**
	 * sql aggregation
	 */
	private String s_sqlAggregation = "";
	/**
	 * is join table flag
	 */
	public boolean b_isJoinTable = false;
	
	/* Properties */
	
	/**
	 * Get sql aggregation of column object
	 * 
	 * @return String
	 */
	public String getSqlAggregation() {
		return this.s_sqlAggregation;
	}
	
	/**
	 * Define a aggregation which should be used with this column, after column class has been initiated
	 * Must match with valid sql aggregation from QueryAbstract
	 * @see QueryAbstract
	 * 
	 * @param p_s_value						aggregation parameter value, e.g. 'COUNT'
	 * @throws IllegalArgumentException		invalid aggregation parameter value
	 */
	public void setSqlAggregation(String p_s_value) throws IllegalArgumentException {
		boolean b_accept = false;
		
		for (int i = 0; i < this.a_sqlAggregations.length; i++) {
			if (this.a_sqlAggregations[i] == p_s_value) {
				b_accept = true;
			}
		}
		
		if (b_accept) {
			this.s_sqlAggregation = p_s_value;
		} else {
			throw new IllegalArgumentException("Value[" + p_s_value + "] is not in defined list[" + String.join(", ", this.a_sqlAggregations) + "]");
		}
	}
	
	/* Methods */
	
	/**
	 * Column constructor, need at least query object as parameter for table information
	 * 
	 * @param p_o_query							query object with database gateway and table information
	 * @throws IllegalArgumentException			invalid database gateway value from query parameter object
	 */
	public Column(Query<?> p_o_query) throws IllegalArgumentException {
		this(p_o_query, "", "", "");
	}
	
	/**
	 * Column constructor, need at least query object as parameter for table information
	 * 
	 * @param p_o_query							query object with database gateway and table information
	 * @param p_s_column						column name in database table schema
	 * @throws IllegalArgumentException			invalid database gateway value from query parameter object
	 */
	public Column(Query<?> p_o_query, String p_s_column) throws IllegalArgumentException {
		this(p_o_query, p_s_column, "", "");
	}
	
	/**
	 * Column constructor, need at least query object as parameter for table information
	 * 
	 * @param p_o_query							query object with database gateway and table information
	 * @param p_s_column						column name in database table schema
	 * @param p_s_name							alias for column
	 * @throws IllegalArgumentException			invalid database gateway value from query parameter object
	 */
	public Column(Query<?> p_o_query, String p_s_column, String p_s_name) throws IllegalArgumentException {
		this(p_o_query, p_s_column, p_s_name, "");
	}
	
	/**
	 * Column constructor, need at least query object as parameter for table information
	 * 
	 * @param p_o_query							query object with database gateway and table information
	 * @param p_s_column						column name in database table schema
	 * @param p_s_name							alias for column
	 * @param p_s_sqlAggregation				aggregation parameter value, e.g. 'COUNT'
	 * @throws IllegalArgumentException			invalid database gateway value from query parameter object
	 */
	public Column(Query<?> p_o_query, String p_s_column, String p_s_name, String p_s_sqlAggregation) throws IllegalArgumentException {
		super(p_o_query);
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_column))
			this.s_column = p_s_column;
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_name))
			this.s_name = p_s_name;
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_sqlAggregation))
			this.setSqlAggregation(p_s_sqlAggregation);
	}
	
	/**
	 * create column part of a sql string query
	 */
	@Override
	public String toString() {
		return this.toString(true);
	}
	
	/**
	 * create column part of a sql string query
	 * 
	 * @param p_b_printName		true - add name with AS to the statement, false - no name used
	 * @return String
	 */
	public String toString(boolean p_b_printName) {
		String s_foo = "";
		
		try {
			boolean b_exc = false;
			boolean b_exc2 = false;
			
			switch (this.e_base) {
				case MARIADB:
				case NOSQLMDB:
					switch (this.e_sqlType) {
						case SELECT:
							if (this.s_column == "*") {
								s_foo = "*";
							} else {
								if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_table)) {
									s_foo = "`" + this.s_table + "`" + "." + "`" + this.s_column + "`";
								} else {
									s_foo = "`" + this.s_column + "`";
								}
							}
							
							if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_sqlAggregation)) {
								s_foo = this.s_sqlAggregation + "(" + s_foo + ")";
							}
							
							if ( (p_b_printName) && (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_name)) ) {
								s_foo = s_foo + " AS" + " '" + this.s_name + "'";
							}
						break;
						case INSERT:
						case UPDATE:
						case DELETE:
							if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_table)) {
								s_foo = "`" + this.s_table + "`" + "." + "`" + this.s_column + "`";
							} else {
								s_foo = "`" + this.s_column + "`";
							}
						break;
						default:
							b_exc2 = true;
						break;
					}
					
					if (b_exc2) {
						throw new Exception("SqlType[" + this.e_sqlType + "] not implemented");
					}
				break;
				case SQLITE:
					switch (this.e_sqlType) {
						case SELECT:
							if (this.s_column == "*") {
								s_foo = "*";
							} else {
								if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_table)) {
									s_foo = "`" + this.s_table + "`" + "." + "`" + this.s_column + "`";
								} else {
									s_foo = "`" + this.s_column + "`";
								}
							}
							
							if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_sqlAggregation)) {
								s_foo = this.s_sqlAggregation + "(" + s_foo + ")";
							}
							
							if ( (p_b_printName) && (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_name)) ) {
								s_foo = s_foo + " AS" + " '" + this.s_name + "'";
							}
						break;
						case INSERT:
						case UPDATE:
							s_foo = "`" + this.s_column + "`";
						break;
						case DELETE:
							if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_table)) {
								s_foo = "`" + this.s_table + "`" + "." + "`" + this.s_column + "`";
							} else {
								s_foo = "`" + this.s_column + "`";
							}
						break;
						default:
							b_exc2 = true;
						break;
					}
					
					if (b_exc2) {
						throw new Exception("SqlType[" + this.e_sqlType + "] not implemented");
					}
				break;
				case MSSQL:
					switch (this.e_sqlType) {
						case SELECT:
							if (this.s_column == "*") {
								s_foo = "*";
							} else {
								if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_table)) {
									s_foo = "[" + this.s_table + "]" + "." + "[" + this.s_column + "]";
								} else {
									s_foo = "[" + this.s_column + "]";
								}
							}
							
							if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_sqlAggregation)) {
								s_foo = this.s_sqlAggregation + "(" + s_foo + ")";
							}
							
							if ( (p_b_printName) && (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_name)) ) {
								s_foo = s_foo + " AS" + " '" + this.s_name + "'";
							}
						break;
						case INSERT:
						case UPDATE:
						case DELETE:
							if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_table)) {
								s_foo = "[" + this.s_table + "]" + "." + "[" + this.s_column + "]";
							} else {
								s_foo = "[" + this.s_column + "]";
							}
						break;
						default:
							b_exc2 = true;
						break;
					}
					
					if (b_exc2) {
						throw new Exception("SqlType[" + this.e_sqlType + "] not implemented");
					}
				break;
				case PGSQL:
				case ORACLE:
					switch (this.e_sqlType) {
						case SELECT:
							if (this.s_column == "*") {
								s_foo = "*";
							} else {
								if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_table)) {
									s_foo = "\"" + this.s_table + "\"" + "." + "\"" + this.s_column + "\"";
								} else {
									s_foo = "\"" + this.s_column + "\"";
								}
							}
							
							if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_sqlAggregation)) {
								s_foo = this.s_sqlAggregation + "(" + s_foo + ")";
							}
							
							if ( (p_b_printName) && (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_name)) ) {
								s_foo = s_foo + " AS" + " \"" + this.s_name + "\"";
							}
						break;
						case INSERT:
						case UPDATE:
							s_foo = "\"" + this.s_column + "\"";
						break;
						case DELETE:
							if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_table)) {
								s_foo = "\"" + this.s_table + "\"" + "." + "\"" + this.s_column + "\"";
							} else {
								s_foo = "\"" + this.s_column + "\"";
							}
						break;
						default:
							b_exc2 = true;
						break;
					}
					
					if (b_exc2) {
						throw new Exception("SqlType[" + this.e_sqlType + "] not implemented");
					}
				break;
				default:
					b_exc = true;
				break;
			}
			
			if (b_exc) {
				throw new Exception("BaseGateway[" + this.e_base + "] not implemented");
			}
		} catch (Exception o_exc) { /* just set exception as query return, so database interface will have an exception as well */
			s_foo = " >>>>> Column class Exception: [" + o_exc.toString() + "] <<<<< ";
		}
		
		return s_foo;
	}
}
