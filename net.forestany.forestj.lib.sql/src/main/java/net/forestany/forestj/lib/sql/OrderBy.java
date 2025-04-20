package net.forestany.forestj.lib.sql;

import net.forestany.forestj.lib.sqlcore.SqlType;

/**
 * SQL class to generate an order by sql clause based on a list of columns and sort directions, called by toString method.
 */
public class OrderBy extends QueryAbstract {
	
	/* Fields */
	
	private java.util.List<Column> a_columns = new java.util.ArrayList<Column>();
	private java.util.List<Boolean> a_directions = new java.util.ArrayList<Boolean>();
	
	/* Properties */
	
	/**
	 * get amount of order by clause
	 * 
	 * @return int
	 */
	public int getAmount() {
		return this.a_columns.size();
	}
	
	/**
	 * get columns of order by clause
	 * 
	 * @return java.util.List&lt;Column&gt;
	 */
	public java.util.List<Column> getColumns() {
		return this.a_columns;
	}
	
	/**
	 * get directions of order by clause
	 * 
	 * @return java.util.List&lt;Boolean&gt;
	 */
	public java.util.List<Boolean> getDirections() {
		return this.a_directions;
	}
	
	/**
	 * add column to order by clause
	 * 
	 * @param p_o_column column instance
	 */
	public void addColumn(Column p_o_column) {
		this.addColumn(p_o_column, true);
	}
	
	/**
	 * add column to order by clause with direction flag
	 * 
	 * @param p_o_column column instance
	 * @param p_b_direction direction flag, true - ascending, false - descending
	 */
	public void addColumn(Column p_o_column, boolean p_b_direction) {
		this.a_columns.add(p_o_column);
		this.a_directions.add(p_b_direction);
	}
	
	/* Methods */
	
	/**
	 * constructor will be called with Query object, because all necessary database gateway, sql type and table information are part of Query class
	 * 
	 * @param p_o_query						query object with table and sql type information
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object
	 */
	public OrderBy(Query<?> p_o_query) throws IllegalArgumentException {
		this(p_o_query, null, null);
	}
	
	/**
	 * constructor will be called with Query object, because all necessary database gateway, sql type and table information are part of Query class
	 * 
	 * @param p_o_query						query object with table and sql type information
	 * @param p_a_columns					list of columns for order by clause
	 * @param p_a_directions				list of directions for order by clause, true - ASC, false - DESC
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object
	 */
	public OrderBy(Query<?> p_o_query, java.util.List<Column> p_a_columns, java.util.List<Boolean> p_a_directions) throws IllegalArgumentException {
		super(p_o_query);
		
		if ( (p_a_columns != null) && (p_a_directions != null) ) {
			if (p_a_columns.size() != p_a_directions.size()) {
				throw new IllegalArgumentException("Both parameter lists do not have the same size");
			}
			
			/* assume list of columns */
			for (Column o_column : p_a_columns) {
				this.a_columns.add(o_column);
			}
			
			/* assume list of directions */
			for (Boolean b_direction : p_a_directions) {
				this.a_directions.add(b_direction);
			}
		} else if ( (p_a_columns != null) && (p_a_directions == null) ) {
			/* assume list of columns, set direction always ASC */
			for (Column o_column : p_a_columns) {
				this.a_columns.add(o_column);
				this.a_directions.add(true);
			}
		}
	}
	
	/**
	 * create order by sql clause as string
	 */
	@Override
	public String toString() {
		String s_foo = "";
		boolean b_exc = false;
		
		try {
			/* only select sql type allowed */
			if (this.e_sqlType != SqlType.SELECT) {
				throw new Exception("SqlType must be SELECT, but is '" + this.e_sqlType + "'");		
			}
			
			switch (this.e_base) {
				case MARIADB:
				case SQLITE:
				case MSSQL:
				case PGSQL:
				case ORACLE:
				case NOSQLMDB:
					if (this.a_columns.size() <= 0) {
						throw new Exception("Columns object list is empty");
					}
					
					s_foo = " ORDER BY ";
					int i = -1;
					
					/* add each column with direction ASC or DESC */
					for (Column o_column : this.a_columns) {
						s_foo += o_column;
							
						if (this.a_directions.get(++i)) {
							s_foo += " ASC";
						} else {
							s_foo += " DESC";
						}
						
						/* add ', ' separator */
						if (i < (this.a_columns.size() - 1)) {
							s_foo += ", ";
						}
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
			s_foo = " >>>>> OrderBy class Exception: [" + o_exc.toString() + "] <<<<< ";
		}
				
		return s_foo;
	}
}
