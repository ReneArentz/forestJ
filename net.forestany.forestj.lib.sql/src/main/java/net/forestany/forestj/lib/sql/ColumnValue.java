package net.forestany.forestj.lib.sql;

/**
 * Column value class with column and value property.
 */
public class ColumnValue extends QueryAbstract {
	
	/* Fields */
	
	/**
	 * column
	 */
	public Column o_column = null;
	/**
	 * value
	 */
	public Object o_value = null;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * Constructor setting column and value to null
	 * 
	 * @throws IllegalArgumentException		could not parse java.util.Date to string or invalid database gateway
	 */
	public ColumnValue() throws IllegalArgumentException {
		this(null, null);
	}
	
	/**
	 * Constructor setting column and value with parameters
	 * 
	 * @param p_o_column					parameter column object
	 * @param p_o_value						parmeter value object
	 * @throws IllegalArgumentException		could not parse java.util.Date to string or invalid database gateway
	 */
	public ColumnValue(Column p_o_column, Object p_o_value) throws IllegalArgumentException {
		/* nothing to give to parent abstract class */
		super(null);
		
		/* set necessary information from column object */
		this.e_base = p_o_column.e_base;
		this.e_sqlType = p_o_column.e_sqlType;
		this.s_table = p_o_column.s_table;
		
		this.o_column = p_o_column;
		/* parse value with special method */
		this.o_value = this.parseValue(p_o_value);
	}
	
	/**
	 * need to override toString method, just returning an empty string
	 */
	@Override
	public String toString() {
		return new String();
	}
}
