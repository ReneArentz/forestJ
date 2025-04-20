package net.forestany.forestj.lib.sql;

/**
 * SQL class to generate a select sql query based on column objects, join clauses, where clauses, order by clause and limit clause, called by toString method.
 */
public class Select extends QueryAbstract {
	
	/* Fields */
	
	/**
	 * distinct flag
	 */
	public boolean b_distinct;
	/**
	 * list of columns 
	 */
	public java.util.List<Column> a_columns = new java.util.ArrayList<Column>();
	/**
	 * list of joins
	 */
	public java.util.List<Join> a_joins = new java.util.ArrayList<Join>();
	/**
	 * list of where clauses
	 */
	public java.util.List<Where> a_where = new java.util.ArrayList<Where>();
	/**
	 * list of group by clauses
	 */
	public java.util.List<Column> a_groupBy = new java.util.ArrayList<Column>();
	/**
	 * list of having clauses
	 */
	public java.util.List<Where> a_having = new java.util.ArrayList<Where>();
	/**
	 * order by clause 
	 */
	public OrderBy o_orderBy = null;
	/**
	 * limit clause
	 */
	public Limit o_limit = null;
	
	/* Properties */
	
	/**
	 * property method to know if there is a column in the select query statement which uses aggregation like MIN, MAX, COUNT etc.
	 * @return	true - has aggregations, false - has no aggregations
	 */
	public boolean hasColumnsWithAggregations() {
		for (Column o_column : this.a_columns) {
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_column.getSqlAggregation())) {
				return true;
			}
		}
		
		return false;
	}
	
	/* Methods */
	
	/**
	 * constructor will be called with Query object, because all necessary database gateway, sql type and table information are part of Query class
	 * 
	 * @param p_o_query						query object with table and sql type information
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object
	 */
	public Select(Query<?> p_o_query) throws IllegalArgumentException {
		super(p_o_query);
	}
	
	/**
	 * create select sql string query
	 */
	@Override
	public String toString() {
		String s_foo = "";
		
		try {
			if (this.a_columns.size() <= 0) {
				throw new Exception("Columns object list is empty");
			}
			
			s_foo = "SELECT ";
			
			/* use DISTINCT expression */
			if (this.b_distinct) {
				s_foo += " DISTINCT ";
			}
			
			/* add all column with ', ' separator to query */
			for (Column o_column : this.a_columns) {
				s_foo += o_column.toString() + ", ";
			}
			
			/* remove last ', ' separator */
			s_foo = s_foo.substring(0, s_foo.length() - 2);
			
			boolean b_exc = false;
			
			/* add table depending on database gateway */
			switch (this.e_base) {
				case MARIADB:
				case SQLITE:
				case NOSQLMDB:
					s_foo += " FROM " + "`" + this.s_table + "`";
				break;
				case MSSQL:
					s_foo += " FROM " + "[" + this.s_table + "]";
				break;
				case PGSQL:
				case ORACLE:
					s_foo += " FROM " + "\"" + this.s_table + "\"";
				break;
				default:
					b_exc = true;
				break;
			}
			
			if (b_exc) {
				throw new Exception("BaseGateway[" + this.e_base + "] not implemented");
			}
			
			/* add join clauses to query */
			if (this.a_joins.size() > 0) {
				for (Join o_join : this.a_joins) {
					s_foo += " " + o_join.toString();
				}
			}
			
			/* add where clauses to query */
			if (this.a_where.size() > 0) {
				s_foo += " WHERE ";
			
				for (Where o_where : this.a_where) {
					s_foo += o_where.toString();
				}
			}
			
			/* add group by clauses to query */
			if (this.a_groupBy.size() > 0) {
				s_foo += " GROUP BY ";
				
				for (Column o_groupBy : this.a_groupBy) {
					s_foo += o_groupBy.toString(false) + ", ";
				}
				
				/* remove last ', ' separator */
				s_foo = s_foo.substring(0, s_foo.length() - 2);
				
				/* add having clauses to query */
				if (this.a_having.size() > 0) {
					s_foo += " HAVING ";
				
					for (Where o_having : this.a_having) {
						s_foo += o_having.toString();
					}
				}
			}
			
			/* add order by clause to query */
			if ( (this.o_orderBy != null) && (this.o_orderBy.getAmount() > 0) ) {
				s_foo += this.o_orderBy.toString();
			}
			
			/* add limit clauses to query */
			if ( (this.o_limit != null) &&  (this.o_limit.i_interval != 0) ) {
				s_foo += this.o_limit.toString();
			}
		} catch (Exception o_exc) { /* just set exception as query return, so database interface will have an exception as well */
			s_foo = " >>>>> Select class Exception: [" + o_exc.toString() + "] <<<<< ";
		}
		
		return s_foo;
	}
}
