package net.forestany.forestj.lib.sql;

import net.forestany.forestj.lib.sqlcore.BaseGateway;
import net.forestany.forestj.lib.sqlcore.IQuery;
import net.forestany.forestj.lib.sqlcore.SqlType;

/**
 * Main query class with table, base gateway and sql type information to create any kind of query to process with a database interface.
 *
 * @param <T>	Sql query class definition of current class based on QueryAbstract class, e.g. Select extends net.forestany.forestj.lib.sql.QueryAbstract.
 */
public class Query<T extends QueryAbstract> implements IQuery<T> {

	/* Fields */
	
	private BaseGateway e_base;
	private SqlType e_sqlType;
	private String s_table;
	private T o_query;
	
	/* Properties */
	
	/**
	 * get base-gateway
	 * 
	 * @return BaseGateway
	 */
	public BaseGateway getBaseGateway() {
		return this.e_base;
	}
	
	/**
	 * get sql type
	 * 
	 * @return SqlType
	 */
	public SqlType getSqlType() {
		return this.e_sqlType;
	}
	
	/**
	 * get table
	 * 
	 * @return String
	 */
	public String getTable() {
		return this.s_table;
	}
	
	/**
	 * get query
	 * 
	 * @return &lt;T&gt; query instance extending QueryAbstract and implementing IQuery
	 */
	public T getQuery() {
		return this.o_query;
	}
	
	/**
	 * set query
	 * 
	 * @param p_s_query query as string
	 * @throws IllegalArgumentException parameter is null or empty
	 */
	public void setQuery(String p_s_query) throws IllegalArgumentException {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_query)) {
			throw new IllegalArgumentException("Query parameter is null or empty");
		}
		
		this.o_query.s_query = p_s_query;
	}
	
	/**
	 * get query separator
	 * 
	 * @return String
	 */
	public String getQuerySeparator() {
		return this.o_query.s_querySeparator;
	}
	
	/* Methods */
	
	/**
	 * constructor of query class
	 * 
	 * @param p_e_base		database gateway enumeration value
	 * @param p_e_type		sql type enumeration value
	 * @param p_s_table		table name
	 * @throws IllegalArgumentException	invalid database gateway or invalid sql type
	 */
	@SuppressWarnings("unchecked")
	public Query(BaseGateway p_e_base, SqlType p_e_type, String p_s_table) throws IllegalArgumentException {
		/* take over construct parameters */
		this.e_base = p_e_base;
		this.e_sqlType = p_e_type;
		this.s_table = p_s_table;
		
		boolean b_exc = false;
		
		/* check base gateway parameter */
        switch (this.e_base) {
			case MARIADB:
			case SQLITE:
			case MSSQL:
			case PGSQL:
			case ORACLE:
			case NOSQLMDB:
				break;
			default:
				b_exc = true;
			break;
		}
        
        if (b_exc) {
        	throw new IllegalArgumentException("Invalid BaseGateway[" + this.e_base + "]");
        }
		
		/* create query object */
		switch (this.e_sqlType) {
			case SELECT:
				this.o_query = (T) new Select(this);
			break;
			case INSERT:
				this.o_query = (T) new Insert(this);
			break;
			case UPDATE:
				this.o_query = (T) new Update(this);
			break;
			case DELETE:
				this.o_query = (T) new Delete(this);
			break;
			case TRUNCATE:
				this.o_query = (T) new Truncate(this);
			break;
			case CREATE:
				this.o_query = (T) new Create(this);
			break;
			case ALTER:
				this.o_query = (T) new Alter(this);
			break;
			case DROP:
				this.o_query = (T) new Drop(this);
			break;
			default:
				b_exc = true;
			break;
		}
		
		if (b_exc) {
			throw new IllegalArgumentException("Invalid SqlType[" + this.e_sqlType + "]");
        }
	}
	
	/**
	 *  create sql string query
	 */
	public String toString() {
		if (this.o_query.s_query != null) { /* load query string if it is set */
			return this.o_query.s_query;
		} else { /* use toString to get query string */
			return this.o_query.toString();
		}
	}
	
	/**
	 * create sql string query as prepared statement, all values are replaced by '?'
	 * auto format DateTime, Date and Time values to sql conform strings
	 * 
	 * @param p_e_base					database gateway enumeration value
	 * @param p_s_query					sql query as string object
	 * @param p_a_values				empty list of prepared statement values
	 * @return String
	 * @throws NullPointerException		list of prepared statement values is null
	 * @throws IllegalArgumentException	list of prepared statement values is not empty
	 */
	public static String convertToPreparedStatementQuery(BaseGateway p_e_base, String p_s_query, java.util.List<java.util.AbstractMap.SimpleEntry<String, Object>> p_a_values) throws NullPointerException, IllegalArgumentException {
		return convertToPreparedStatementQuery(p_e_base, p_s_query, p_a_values, true);
	}
	
	/**
	 * create sql string query as prepared statement, all values are replaced by '?'
	 * 
	 * @param p_e_base					database gateway enumeration value
	 * @param p_s_query					sql query as string object
	 * @param p_a_values				empty list of prepared statement values
	 * @param p_b_formatDateTimeValues	auto format DateTime, Date and Time values to sql conform strings
	 * @return String
	 * @throws NullPointerException		list of prepared statement values is null
	 * @throws IllegalArgumentException	list of prepared statement values is not empty
	 */
	public static String convertToPreparedStatementQuery(BaseGateway p_e_base, String p_s_query, java.util.List<java.util.AbstractMap.SimpleEntry<String, Object>> p_a_values, boolean p_b_formatDateTimeValues) throws NullPointerException, IllegalArgumentException {
		/* check if parameter object list is initiated */
		if (p_a_values == null) {
			throw new NullPointerException("Parameter object list is null");
		}
		
		if (p_a_values.size() != 0) {
			throw new IllegalArgumentException("List of prepared statement values must be empty");
		}
		
		/* fetch user input values out of query for prepared statement and safe execution */
		String s_queryFoo;
		
		/* PGSQL + MSSQL + ORACLE: you can only pass values to DML statements */
		if ( ((p_e_base == BaseGateway.PGSQL) || (p_e_base == BaseGateway.MSSQL) || (p_e_base == BaseGateway.ORACLE)) && ((p_s_query.startsWith("CREATE TABLE")) || (p_s_query.startsWith("ALTER TABLE"))) ) {
													if (net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) {
														net.forestany.forestj.lib.Global.ilogFiner("query prepared statement: '" + p_s_query.replace("[forestjSQLValue]", "'").replace("[/forestjSQLValue]", "'") + "'");
													}
			
			return p_s_query.replace("[forestjSQLValue]", "'").replace("[/forestjSQLValue]", "'");
		}
		
		/* get values out of sql statement to pass them within a prepared statement */
		java.util.regex.Matcher o_matcher;
		java.util.regex.Pattern o_pattern = java.util.regex.Pattern.compile("\\[forestjSQLValue\\](.*?)\\[/forestjSQLValue\\]");
		o_matcher = o_pattern.matcher(p_s_query);
		
		while (o_matcher.find()) {
			String s_value = o_matcher.group(1);
			
			if ( (s_value.compareTo("true") == 0) || (s_value.compareTo("false") == 0) ) {
				if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("value isBoolean" + ( (net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? ": " + s_value : "" ) );
				
				/* MSSQL + PGSQL stores bit columns as tinyint, in ORACLE we just store one character */
				if ( (p_e_base == BaseGateway.MSSQL) || (p_e_base == BaseGateway.PGSQL) || (p_e_base == BaseGateway.ORACLE) ) {
					if (s_value.compareTo("true") == 0) {
						if ((p_e_base == BaseGateway.PGSQL) || (p_e_base == BaseGateway.MSSQL) || (p_e_base == BaseGateway.ORACLE)) {
							p_a_values.add( new java.util.AbstractMap.SimpleEntry<String, Object>( "short", (Object)Short.valueOf("1") ) );
						} else {
							p_a_values.add( new java.util.AbstractMap.SimpleEntry<String, Object>( "boolean", (Object)"1" ) );
						}
					} else {
						if ((p_e_base == BaseGateway.PGSQL) || (p_e_base == BaseGateway.MSSQL) || (p_e_base == BaseGateway.ORACLE)) {
							p_a_values.add( new java.util.AbstractMap.SimpleEntry<String, Object>( "short", (Object)Short.valueOf("0") ) );
						} else {
							p_a_values.add( new java.util.AbstractMap.SimpleEntry<String, Object>( "boolean", (Object)"0" ) );
						}
					}
				} else {
					p_a_values.add( new java.util.AbstractMap.SimpleEntry<String, Object>( "boolean", (Object)Boolean.parseBoolean(o_matcher.group(1)) ) );
				}
			} else if (net.forestany.forestj.lib.Helper.isShort(s_value)) {
				if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("value isShort" + ( (net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? ": " + s_value : "" ) );
				p_a_values.add( new java.util.AbstractMap.SimpleEntry<String, Object>( "short", (Object)Short.parseShort(o_matcher.group(1)) ) );
			} else if (net.forestany.forestj.lib.Helper.isInteger(s_value)) {
				if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("value isInteger" + ( (net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? ": " + s_value : "" ) );
				p_a_values.add( new java.util.AbstractMap.SimpleEntry<String, Object>( "integer", (Object)Integer.parseInt(o_matcher.group(1)) ) );
			} else if (net.forestany.forestj.lib.Helper.isLong(s_value)) {
				if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("value isLong" + ( (net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? ": " + s_value : "" ) );
				p_a_values.add( new java.util.AbstractMap.SimpleEntry<String, Object>( "long", (Object)Long.parseLong(o_matcher.group(1)) ) );
			} else if (net.forestany.forestj.lib.Helper.isDouble(s_value)) {
				if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("value isDouble" + ( (net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? ": " + s_value : "" ) );
				p_a_values.add( new java.util.AbstractMap.SimpleEntry<String, Object>( "double", (Object)Double.parseDouble(o_matcher.group(1)) ) );
			} else if (net.forestany.forestj.lib.Helper.isFloat(s_value)) {
				if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("value isFloat" + ( (net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? ": " + s_value : "" ) );
				p_a_values.add( new java.util.AbstractMap.SimpleEntry<String, Object>( "float", (Object)Float.parseFloat(o_matcher.group(1)) ) );
			} else if ( (!s_value.startsWith("1970-01-01")) && (net.forestany.forestj.lib.Helper.isDateTime(s_value)) ) {
				if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("value isDateTime" + ( (net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? ": " + s_value : "" ) );
				
				if (p_b_formatDateTimeValues) {
					p_a_values.add( new java.util.AbstractMap.SimpleEntry<String, Object>( "localdatetime", (Object) ( (p_e_base != BaseGateway.MSSQL) ? net.forestany.forestj.lib.Helper.fromDateTimeString(s_value).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss")) : net.forestany.forestj.lib.Helper.fromDateTimeString(s_value).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")) ) ) );
				} else {
					p_a_values.add( new java.util.AbstractMap.SimpleEntry<String, Object>( "localdatetime", (Object)net.forestany.forestj.lib.Helper.fromDateTimeString(s_value) ) );
				}
			} else if (net.forestany.forestj.lib.Helper.isDate(s_value)) {
				if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("value isDate" + ( (net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? ": " + s_value : "" ) );

				if (p_b_formatDateTimeValues) {
					p_a_values.add( new java.util.AbstractMap.SimpleEntry<String, Object>( "localdate", (Object) net.forestany.forestj.lib.Helper.fromDateString(s_value).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")) ) );
				} else {
					p_a_values.add( new java.util.AbstractMap.SimpleEntry<String, Object>( "localdate", (Object) net.forestany.forestj.lib.Helper.fromDateString(s_value) ) );
				}
			} else if ( (net.forestany.forestj.lib.Helper.isTime(s_value)) || ((s_value.startsWith("1970-01-01")) && (net.forestany.forestj.lib.Helper.isTime(s_value.substring(11)))) ) {
				if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("value isTime" + ( (net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? ": " + s_value : "" ) );
				
				if (s_value.startsWith("1970-01-01")) {
					s_value = s_value.substring(11);
				}
				
				if (p_b_formatDateTimeValues) {
					p_a_values.add( new java.util.AbstractMap.SimpleEntry<String, Object>( "localtime", (Object) net.forestany.forestj.lib.Helper.fromTimeString(s_value).format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")) ) );
				} else {
					p_a_values.add( new java.util.AbstractMap.SimpleEntry<String, Object>( "localtime", (Object) net.forestany.forestj.lib.Helper.fromTimeString(s_value) ) );
				}
			} else if ( (s_value.startsWith("[forestjBigDecimal]")) && (s_value.endsWith("[/forestjBigDecimal]")) ) {
				s_value = s_value.replace("[forestjBigDecimal]", "").replace("[/forestjBigDecimal]", "");
				if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("value isBigDecimal" + ( (net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? ": " + s_value : "" ) );
				p_a_values.add( new java.util.AbstractMap.SimpleEntry<String, Object>( "bigdecimal", (Object)(new java.math.BigDecimal(s_value)) ) );
			} else {
				if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("value isString" + ( (net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? ": " + s_value : "" ) );
				p_a_values.add( new java.util.AbstractMap.SimpleEntry<String, Object>( "string", (Object)o_matcher.group(1) ) );
			}
		}

		s_queryFoo = o_matcher.replaceAll("?");
		
												if (net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) {
													net.forestany.forestj.lib.Global.ilogFiner("query prepared statement: '" + s_queryFoo + "'");
												}
		
		return s_queryFoo;
	}
	
	/**
	 * create standard sql query
	 * 
	 * @param p_s_preparedStatement		prepared statement
	 * @param p_a_values				list of prepared statement values
	 * @return String
	 */
	public static String convertPreparedStatementSqlQueryToStandard(String p_s_preparedStatement, java.util.List<java.util.AbstractMap.SimpleEntry<String, Object>> p_a_values) {
	    int i_pointer = 0;
	    int i_counter = 0;
	    
	    while ((i_pointer = p_s_preparedStatement.indexOf("?")) > 0) {
	      String s_before = p_s_preparedStatement.substring(0, i_pointer);
	      String s_after = p_s_preparedStatement.substring(i_pointer + 1);
	      
	      String s_type = (p_a_values.size() <= i_counter) ? "NO TYPE" : p_a_values.get(i_counter).getKey();
	      String s_value = (p_a_values.size() <= i_counter) ? "NO VALUE IN LIST" : p_a_values.get(i_counter++).getValue().toString();
	      String s_quote = "'";
	      
	      /* use no quotes for digit, boolean or null values */
	      if (
	    	  (s_type.contentEquals("short")) ||
	    	  (s_type.contentEquals("integer")) ||
	    	  (s_type.contentEquals("long")) ||
	    	  (s_type.contentEquals("float")) ||
	    	  (s_type.contentEquals("double")) ||
	    	  (s_type.contentEquals("bigdecimal")) ||
	    	  (s_type.contentEquals("boolean")) ||
	    	  (s_value.contentEquals("false")) ||
			  (s_value.contentEquals("NULL"))
		  ) {
	    	  s_quote = "";
	      }
	      
	      p_s_preparedStatement = s_before + s_quote + s_value + s_quote + s_after;
	    }
	    
	    return p_s_preparedStatement;
	  }
	
	/**
	 * Get constraint type matching constraint parameter and database gateway enumeration value
	 * 
	 * @param p_s_constraintType			constraint type parameter
	 * @return								constraint type matching database gateway
	 * @throws IllegalArgumentException		invalid database gateway or invalid constraint type
	 */
	public String constraintTypeAllocation(String p_s_constraintType) throws IllegalArgumentException {
		boolean b_exc = false;
		
		/* check base gateway parameter */
        switch (this.e_base) {
			case MARIADB:
			case SQLITE:
			case MSSQL:
			case PGSQL:
			case ORACLE:
			case NOSQLMDB:
				break;
			default:
				b_exc = true;
			break;
		}
        
        if (b_exc) {
        	throw new IllegalArgumentException("Invalid BaseGateway[" + this.e_base + "]");
        }
		
		/* check constraint parameter */
		 switch (p_s_constraintType) {
		 	case "NULL":
			case "NOT NULL":
			case "UNIQUE":
			case "PRIMARY KEY":
			case "DEFAULT":
			case "INDEX":
			case "AUTO_INCREMENT":
			break;
			default:
				b_exc = true;
			break;
		}
		 
		if (b_exc) {
			 throw new IllegalArgumentException("Invalid Constraint[" + p_s_constraintType + "]");
		}
		
		java.util.Map<String, Integer> a_mapping= new java.util.HashMap<String, Integer>();
		a_mapping.put(BaseGateway.MARIADB.toString(), 0);
		a_mapping.put(BaseGateway.SQLITE.toString(), 1);
		a_mapping.put(BaseGateway.MSSQL.toString(), 2);
		a_mapping.put(BaseGateway.PGSQL.toString(), 3);
		a_mapping.put(BaseGateway.ORACLE.toString(), 4);
		a_mapping.put(BaseGateway.NOSQLMDB.toString(), 5);
		
		a_mapping.put("NULL", 0);
		a_mapping.put("NOT NULL", 1);
		a_mapping.put("UNIQUE", 2);
		a_mapping.put("PRIMARY KEY", 3);
		a_mapping.put("DEFAULT", 4);
		a_mapping.put("INDEX", 5);
		a_mapping.put("AUTO_INCREMENT", 6);
		
		String[][] a_allocation = {
				{"NULL", "NOT NULL", "UNIQUE", "PRIMARY KEY", "DEFAULT", "INDEX", "AUTO_INCREMENT"}, /* MARIADB */
				{"NULL", "NOT NULL", "UNIQUE", "PRIMARY KEY", "DEFAULT", "INDEX", "AUTOINCREMENT"}, /* SQLITE */
				{"NULL", "NOT NULL", "UNIQUE", "PRIMARY KEY", "DEFAULT", "INDEX", "IDENTITY(1,1)"}, /* MSSQL */
				{"NULL", "NOT NULL", "UNIQUE", "PRIMARY KEY", "DEFAULT", "INDEX", ""}, /* PGSQL */
				{"NULL", "NOT NULL", "UNIQUE", "PRIMARY KEY", "DEFAULT", "INDEX", ""}, /* ORACLE */
				{"NULL", "NOT NULL", "UNIQUE", "PRIMARY KEY", "DEFAULT", "INDEX", "AUTO_INCREMENT"} /* MONGODB */
		};
		
		/* get constraint type of allocation matrix */
		return a_allocation[a_mapping.get(this.e_base.toString())][a_mapping.get(p_s_constraintType)];
	}
}
