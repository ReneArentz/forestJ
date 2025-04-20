package net.forestany.forestj.lib.sql;

import net.forestany.forestj.lib.sqlcore.SqlType;

/**
 * SQL class to generate a relation sql clause based on a column object, a value object, operator and filter operator, called by toString method.
 */
public class Where extends QueryAbstract {
	
	/* Fields */

	/**
	 * column
	 */
	public Column o_column = null;
	/**
	 * value
	 */
	public Object o_value = null;
	/**
	 * operator
	 */
	private String s_operator = "";
	/**
	 * filter operator
	 */
	private String s_filterOperator = "";
	/**
	 * bracket start flag
	 */
	public boolean b_bracketStart = false;
	/**
	 * bracket end flag
	 */
	public boolean b_bracketEnd = false;
	/**
	 * is join table flag
	 */
	public boolean b_isJoinTable = false;
	
	/* Properties */
	
	/**
	 * get operator
	 * 
	 * @return String
	 */
	public String getOperator() {
		return this.s_operator;
	}
	
	/**
	 * set operator
	 * 
	 * @param p_s_value operator value
	 * @throws IllegalArgumentException value is not in defined list
	 */
	public void setOperator(String p_s_value) throws IllegalArgumentException {
		boolean b_accept = false;
		
		/* check if p_s_value is a valid sql operator */
		for (int i = 0; i < this.a_operators.length; i++) {
			if (this.a_operators[i] == p_s_value) {
				b_accept = true;
			}
		}
		
		if (b_accept) {
			this.s_operator = p_s_value;
		} else {
			throw new IllegalArgumentException("Value[" + p_s_value + "] is not in defined list[" + String.join(", ", this.a_operators) + "]");
		}
	}
	
	/**
	 * get filter operator
	 * 
	 * @return String
	 */
	public String getFilterOperator() {
		return this.s_filterOperator;
	}
	
	/**
	 * set filter operator
	 * 
	 * @param p_s_value filter operator value
	 * @throws IllegalArgumentException value is not in defined list
	 */
	public void setFilterOperator(String p_s_value) throws IllegalArgumentException {
		boolean b_accept = false;
		
		/* check if p_s_value is a valid sql filter operator */
		for (int i = 0; i < this.a_filterOperators.length; i++) {
			if (this.a_filterOperators[i] == p_s_value) {
				b_accept = true;
			}
		}
		
		if (b_accept) {
			this.s_filterOperator = p_s_value;
		} else {
			throw new IllegalArgumentException("Value[" + p_s_value + "] is not in defined list[" + String.join(", ", this.a_filterOperators) + "]");
		}
	}
	
	/* Methods */
	
	/**
	 * constructor will be called with Query object, because all necessary database gateway, sql type and table information are part of Query class
	 * 
	 * @param p_o_query						query object with table and sql type information
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object or invalid operator or filter operator value
	 */
	public Where(Query<?> p_o_query) throws IllegalArgumentException {
		this(p_o_query, null, null, "", "", false, false);
	}
	
	/**
	 * constructor will be called with Query object, because all necessary database gateway, sql type and table information are part of Query class
	 * 
	 * @param p_o_query						query object with table and sql type information
	 * @param p_o_column					column object of where clause
	 * @param p_o_value						value object of where clause
	 * @param p_s_operator					operator between both column, e.g. '=', '&lt;&gt;', '&gt;', ...
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object or invalid operator or filter operator value
	 */
	public Where(Query<?> p_o_query, Column p_o_column, Object p_o_value, String p_s_operator) throws IllegalArgumentException {
		this(p_o_query, p_o_column, p_o_value, p_s_operator, "", false, false);
	}
	
	/**
	 * constructor will be called with Query object, because all necessary database gateway, sql type and table information are part of Query class
	 * 
	 * @param p_o_query						query object with table and sql type information
	 * @param p_o_column					column object of where clause
	 * @param p_o_value						value object of where clause
	 * @param p_s_operator					operator between both column, e.g. '=', '&lt;&gt;', '&gt;', ...
	 * @param p_s_filterOperator			operator between multiple relation objects, e.g. 'AND', 'OR', ...
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object or invalid operator or filter operator value
	 */
	public Where(Query<?> p_o_query, Column p_o_column, Object p_o_value, String p_s_operator, String p_s_filterOperator) throws IllegalArgumentException {
		this(p_o_query, p_o_column, p_o_value, p_s_operator, p_s_filterOperator, false, false);
	}
	
	/**
	 * constructor will be called with Query object, because all necessary database gateway, sql type and table information are part of Query class
	 * 
	 * @param p_o_query						query object with table and sql type information
	 * @param p_o_column					column object of where clause
	 * @param p_o_value						value object of where clause
	 * @param p_s_operator					operator between both column, e.g. '=', '&lt;&gt;', '&gt;', ...
	 * @param p_s_filterOperator			operator between multiple relation objects, e.g. 'AND', 'OR', ...
	 * @param p_b_bracketStart				flag to add '(' bracket before relation object
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object or invalid operator or filter operator value
	 */
	public Where(Query<?> p_o_query, Column p_o_column, Object p_o_value, String p_s_operator, String p_s_filterOperator, boolean p_b_bracketStart) throws IllegalArgumentException {
		this(p_o_query, p_o_column, p_o_value, p_s_operator, p_s_filterOperator, p_b_bracketStart, false);
	}
	
	/**
	 * constructor will be called with Query object, because all necessary database gateway, sql type and table information are part of Query class
	 * 
	 * @param p_o_query						query object with table and sql type information
	 * @param p_o_column					column object of where clause
	 * @param p_o_value						value object of where clause
	 * @param p_s_operator					operator between both column, e.g. '=', '&lt;&gt;', '&gt;', ...
	 * @param p_s_filterOperator			operator between multiple relation objects, e.g. 'AND', 'OR', ...
	 * @param p_b_bracketStart				flag to add '(' bracket before relation object
	 * @param p_b_bracketEnd				flag to add ')' bracket after relation object
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object or invalid operator or filter operator value
	 */
	public Where(Query<?> p_o_query, Column p_o_column, Object p_o_value, String p_s_operator, String p_s_filterOperator, boolean p_b_bracketStart, boolean p_b_bracketEnd) throws IllegalArgumentException {
		super(p_o_query);
		
		if (p_o_column != null) {
			this.o_column = p_o_column;
		}
		
		this.o_value = this.parseValue(p_o_value);
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_operator)) {
			this.setOperator(p_s_operator);
		}
			
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_filterOperator)) {
			this.setFilterOperator(p_s_filterOperator);
		}
		
		if (p_b_bracketStart && p_b_bracketEnd) {
			throw new IllegalArgumentException("Cannot add '(' and ')' bracket in the same where object");
		}
		
		this.b_bracketStart = p_b_bracketStart;
		this.b_bracketEnd = p_b_bracketEnd;
	}
	
	/**
	 * create where sql string query
	 */
	@Override
	public String toString() {
		String s_foo = "";
		boolean b_exc = false;
		
		try {
			/* only select, update or delete sql type allowed */
			if ( (this.e_sqlType != SqlType.SELECT) && (this.e_sqlType != SqlType.UPDATE) && (this.e_sqlType != SqlType.DELETE) ) {
				throw new Exception("SqlType must be SELECT|UPDATE|DELETE, but is '" + this.e_sqlType + "'");		
			}
			
			switch (this.e_base) {
				case MARIADB:
				case SQLITE:
				case MSSQL:
				case PGSQL:
				case ORACLE:
				case NOSQLMDB:
					/* add filter operator if it is not empty */
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_filterOperator)) {
						s_foo = " " + this.s_filterOperator + " ";
					}
					
					if ( (this.b_bracketStart) && (!this.b_bracketEnd) )  {
						/* add '(' bracket */
						s_foo += "(";
					}
					
					/* add where to query */
					s_foo += this.o_column.toString(false) + " " + this.s_operator + " " + this.o_value.toString();
					
					if ( (this.b_bracketEnd) && (!this.b_bracketStart) )  {
						/* add ')' bracket */
						s_foo += ")";
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
			s_foo = " >>>>> Where class Exception: [" + o_exc.toString() + "] <<<<< ";
		}
		
		return s_foo;
	}
}
