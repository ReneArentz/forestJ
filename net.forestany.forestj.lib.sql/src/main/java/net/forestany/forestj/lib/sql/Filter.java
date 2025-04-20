package net.forestany.forestj.lib.sql;

/**
 * Filter class holding basic information like column and filter value, but also operator and/or filter operator.
 */
public class Filter {
	/**
	 * column
	 */
	public String s_column;
	/**
	 * value
	 */
	public Object o_value;
	/**
	 * operator
	 */
	public String s_operator;
	/**
	 * filter operator
	 */
	public String s_filterOperator;
	
	/**
	 * constructor leaving filter operator as null
	 * 
	 * @param p_s_column			column name in table structure
	 * @param p_o_value				filter value, could be any kind
	 * @param p_s_operator			operator between column and value, e.g. '=', '&lt;&gt;', '&gt;', ...
	 */
	public Filter(String p_s_column, Object p_o_value, String p_s_operator) {
		this(p_s_column, p_o_value, p_s_operator, null);
	}
	
	/**
	 * constructor which sets all class properties
	 * 
	 * @param p_s_column			column name in table structure
	 * @param p_o_value				filter value, could be any kind
	 * @param p_s_operator			operator between column and value, e.g. '=', '&lt;&gt;', '&gt;', ...
	 * @param p_s_filterOperator	operator between multiple filter objects, e.g. 'AND', 'OR', ...
	 */
	public Filter(String p_s_column, Object p_o_value, String p_s_operator, String p_s_filterOperator) {
		this.s_column = p_s_column;
		this.o_value = p_o_value;
		this.s_operator = p_s_operator;
		this.s_filterOperator = p_s_filterOperator;
	}
}
