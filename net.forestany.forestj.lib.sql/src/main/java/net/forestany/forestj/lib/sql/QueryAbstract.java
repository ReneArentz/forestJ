package net.forestany.forestj.lib.sql;

import net.forestany.forestj.lib.sqlcore.BaseGateway;
import net.forestany.forestj.lib.sqlcore.SqlType;

/**
 * Abstract query class holding all necessary information about operators, filters, aggregations, index constraints, alter operations.
 */
public abstract class QueryAbstract {
	
	/* Fields */
	
	/**
	 * constant list of operators
	 */
	protected final String[] a_operators = {"=", "<", "<=", ">", ">=", "<>", "LIKE", "NOT LIKE", "IN", "NOT IN", "IS", "IS NOT"};
	/**
	 * constant list of filter operators
	 */
	protected final String[] a_filterOperators = {"AND", "OR", "XOR"};
	/**
	 * constant list of join types
	 */
	protected final String[] a_joinTypes = {"INNER JOIN", "NATURAL JOIN", "CROSS JOIN", "OUTER JOIN", "LEFT OUTER JOIN", "RIGHT OUTER JOIN", "FULL OUTER JOIN"};
	/**
	 * constant list of sql aggregations
	 */
	protected final String[] a_sqlAggregations = {"AVG", "COUNT", "MAX", "MIN", "SUM"};
	/**
	 * constant list of index constraints
	 */
	protected final String[] a_sqlIndexConstraints = {"UNIQUE", "PRIMARY KEY", "INDEX"};
	/**
	 * constant list of alter operations
	 */
	protected final String[] a_alterOperations = {"ADD", "CHANGE", "DROP"};
	/**
	 * constant query separator
	 */
	protected final String s_querySeparator = "::forestjSQLQuerySeparator::";
	/**
	 * array for sql column types
	 */
	protected String[] a_sqlColumnTypes;
	/**
	 * array for sql constraints
	 */
	protected String[] a_sqlConstraints;
	
	/**
	 * query
	 */
	protected String s_query;
	/**
	 * base
	 */
	protected BaseGateway e_base;
	/**
	 * sql type
	 */
	protected SqlType e_sqlType;
	/**
	 * table
	 */
	public String s_table;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * Abstract constructor with query parameter
	 * 
	 * @param p_o_query		query object parameter
	 * @throws IllegalArgumentException	invalid database gateway value from query parameter object
	 */
	public QueryAbstract(Query<?> p_o_query) throws IllegalArgumentException {
		this.s_query = null;
		
		if (p_o_query != null) {
			this.e_base = p_o_query.getBaseGateway();
			this.e_sqlType = p_o_query.getSqlType();
			this.s_table = p_o_query.getTable();
			this.init();
		}
	}
	
	/**
	 * Init function declaring sql column types and valid constraints for selected database gateway
	 * 
	 * @throws IllegalArgumentException		invalid database gateway value
	 */
	protected void init() throws IllegalArgumentException {
		java.util.List<String> a_sqlColumnTypes = new java.util.ArrayList<String>();
		java.util.List<String> a_sqlConstraints = new java.util.ArrayList<String>();
		boolean b_exc = false;
		
		switch (this.e_base) {
			case MARIADB:
				a_sqlColumnTypes = java.util.Arrays.asList("VARCHAR", "TEXT", "SMALLINT", "INT", "BIGINT", "TIMESTAMP", "TIME", "DOUBLE", "DECIMAL", "BIT");
				a_sqlConstraints = java.util.Arrays.asList("NULL", "NOT NULL", "UNIQUE", "PRIMARY KEY", "DEFAULT", "INDEX", "AUTO_INCREMENT", "SIGNED", "UNSIGNED");
			break;
			case SQLITE:
				a_sqlColumnTypes = java.util.Arrays.asList("varchar", "text", "smallint", "integer", "bigint", "datetime", "time", "double", "decimal", "bit");
				a_sqlConstraints = java.util.Arrays.asList("NULL", "NOT NULL", "UNIQUE", "PRIMARY KEY", "DEFAULT", "INDEX", "AUTOINCREMENT");
			break;
			case MSSQL:
				a_sqlColumnTypes = java.util.Arrays.asList("nvarchar", "text", "smallint", "int", "bigint", "datetime", "time", "float", "decimal", "bit");
				a_sqlConstraints = java.util.Arrays.asList("NULL", "NOT NULL", "UNIQUE", "PRIMARY KEY", "DEFAULT", "INDEX", "IDENTITY(1,1)");
			break;
			case PGSQL:
				a_sqlColumnTypes = java.util.Arrays.asList("varchar", "text", "smallint", "integer", "bigint", "timestamp", "time", "double precision", "decimal", "bit", "serial");
				a_sqlConstraints = java.util.Arrays.asList("NULL", "NOT NULL", "UNIQUE", "PRIMARY KEY", "DEFAULT", "INDEX", "");
			break;
			case ORACLE:
				a_sqlColumnTypes = java.util.Arrays.asList("VARCHAR2", "CLOB", "DOUBLE PRECISION", "BINARY_FLOAT", "LONG", "TIMESTAMP", "INTERVAL DAY(0) TO SECOND(0)", "BINARY_DOUBLE", "NUMBER", "CHAR");
				a_sqlConstraints = java.util.Arrays.asList("NULL", "NOT NULL", "UNIQUE", "PRIMARY KEY", "DEFAULT", "INDEX", "");
			break;
			case NOSQLMDB:
				a_sqlColumnTypes = java.util.Arrays.asList("VARCHAR", "TEXT", "SMALLINT", "INTEGER", "BIGINT", "TIMESTAMP", "TIME", "DOUBLE", "DECIMAL", "BOOL");
				a_sqlConstraints = java.util.Arrays.asList("NULL", "NOT NULL", "UNIQUE", "PRIMARY KEY", "DEFAULT", "INDEX", "AUTO_INCREMENT");
			break;
			default:
				b_exc= true;
			break;
		}
		
		if ( (b_exc) || (a_sqlColumnTypes.size() == 0)  || (a_sqlConstraints.size() == 0) ) {
			throw new IllegalArgumentException("SQL column types for BaseGateway[" + e_base + "] not implemented");
		}
		
		/* assume sql column types to local field */
		this.a_sqlColumnTypes = new String[a_sqlColumnTypes.size()];
		int i = 0;
		
		for (String s_sqlColumnType : a_sqlColumnTypes) {
			this.a_sqlColumnTypes[i++] = s_sqlColumnType;
		}
		
		/* assume sql constraints to local field */
		this.a_sqlConstraints = new String[a_sqlConstraints.size()];
		i = 0;
		
		for (String s_sqlConstraint : a_sqlConstraints) {
			this.a_sqlConstraints[i++] = s_sqlConstraint;
		}
	}
	
	/**
	 * Method for parsing a value to a query, preventing sql-injection
	 *  
	 * @param p_o_value						value object
	 * @return								object, but in general returning always a string casted as object
	 * @throws IllegalArgumentException		could not parse java.util.Date to string or invalid database gateway
	 */
	public Object parseValue(Object p_o_value) throws IllegalArgumentException {
		/* temp variables */
		String s_foo = null;
		short sh_foo = 0;
		int i_foo = 0;
		long l_foo = 0;
		double d_foo = 0.f;
		boolean b_foo = false;
		java.math.BigDecimal o_bigDecimalFoo = null;
		
		/* return flags */
		boolean b_returnString = false;
		boolean b_returnShort = false;
		boolean b_returnInteger = false;
		boolean b_returnLong = false;
		boolean b_returnDouble = false;
		boolean b_returnBoolean = false;
		boolean b_returnBigDecimal = false;
		
		/* if parameter is null we just return 'NULL' string */
		if (p_o_value == null) {
			s_foo = "NULL";
			b_returnString = true;
		}
		
		/* if object parameter is a string or an instance of java.util.Date */
		if ( (p_o_value instanceof String) || (p_o_value instanceof java.util.Date) ) {
			/* return value as string */
			b_returnString = true;
			
			if (p_o_value instanceof String) { /* if it is a string call toString object method, if length is 0 then set value to 'NULL' */
				s_foo = p_o_value.toString();
				
				if (s_foo.length() == 0) {
					s_foo = "NULL";
				}
			} else if (p_o_value instanceof java.util.Date) { /* check if object parameter is instance of java.util.Date */
				if (new java.text.SimpleDateFormat("HH:mm:ss").format(p_o_value).equals("00:00:00")) {
					s_foo = new java.text.SimpleDateFormat("dd.MM.yyyy").format(p_o_value);
				} else {
					/* create timestamp string */
					s_foo = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(p_o_value);
				}
			}
			
			boolean b_exc = false;
			
			/* date conversion for sql query [any date] | [any date time] to [yyyy-MM-dd(T|' ')hh:mm:ss] */
			switch (this.e_base) {
				case MARIADB:
				case SQLITE:
				case PGSQL:
				case NOSQLMDB:
					if (net.forestany.forestj.lib.Helper.isDate(s_foo)) {
						java.time.LocalDate o_localDate = net.forestany.forestj.lib.Helper.fromDateString(s_foo);
						s_foo = o_localDate.getYear() + "-" + String.format("%02d", o_localDate.getMonthValue()) + "-" + String.format("%02d", o_localDate.getDayOfMonth());
					} else if (net.forestany.forestj.lib.Helper.isDateTime(s_foo)) {
						java.time.LocalDateTime o_localDateTime = net.forestany.forestj.lib.Helper.fromDateTimeString(s_foo);
						s_foo = o_localDateTime.getYear() + "-" + String.format("%02d", o_localDateTime.getMonthValue()) + "-" + String.format("%02d", o_localDateTime.getDayOfMonth()) + " " + String.format("%02d", o_localDateTime.getHour()) + ":" + String.format("%02d", o_localDateTime.getMinute()) + ":" + String.format("%02d", o_localDateTime.getSecond());
					}
				break;
				case ORACLE:
					if ( (s_foo.startsWith("01.01.1970 ")) || (net.forestany.forestj.lib.Helper.isTime(s_foo)) ) { /* recognize time value */
						/* cut off '01.01.1970 ' from start */
						if (s_foo.startsWith("01.01.1970 ")) {
							s_foo = s_foo.substring(11);
						}
						
						return (Object)("TO_DSINTERVAL([forestjSQLValue]+0 " + s_foo + "[/forestjSQLValue])");
					} else if (net.forestany.forestj.lib.Helper.isDate(s_foo)) {
						java.time.LocalDate o_localDate = net.forestany.forestj.lib.Helper.fromDateString(s_foo);
						s_foo = o_localDate.getYear() + "-" + String.format("%02d", o_localDate.getMonthValue()) + "-" + String.format("%02d", o_localDate.getDayOfMonth());
					} else if (net.forestany.forestj.lib.Helper.isDateTime(s_foo)) {
						java.time.LocalDateTime o_localDateTime = net.forestany.forestj.lib.Helper.fromDateTimeString(s_foo);
						s_foo = o_localDateTime.getYear() + "-" + String.format("%02d", o_localDateTime.getMonthValue()) + "-" + String.format("%02d", o_localDateTime.getDayOfMonth()) + " " + String.format("%02d", o_localDateTime.getHour()) + ":" + String.format("%02d", o_localDateTime.getMinute()) + ":" + String.format("%02d", o_localDateTime.getSecond());
					}
				break;
				case MSSQL:
					if (net.forestany.forestj.lib.Helper.isDate(s_foo)) {
						java.time.LocalDate o_localDate = net.forestany.forestj.lib.Helper.fromDateString(s_foo);
						s_foo = o_localDate.getYear() + "-" + String.format("%02d", o_localDate.getMonthValue()) + "-" + String.format("%02d", o_localDate.getDayOfMonth());
					} else if (net.forestany.forestj.lib.Helper.isDateTime(s_foo)) {
						java.time.LocalDateTime o_localDateTime = net.forestany.forestj.lib.Helper.fromDateTimeString(s_foo);
						s_foo = o_localDateTime.getYear() + "-" + String.format("%02d", o_localDateTime.getMonthValue()) + "-" + String.format("%02d", o_localDateTime.getDayOfMonth()) + "T" + String.format("%02d", o_localDateTime.getHour()) + ":" + String.format("%02d", o_localDateTime.getMinute()) + ":" + String.format("%02d", o_localDateTime.getSecond());
					}
				break;
				default:
					b_exc = true;
				break;
			}
			
			if (b_exc) {
	        	throw new IllegalArgumentException("BaseGateway[" + this.e_base + "] not implemented");
	        }
		}
		
		/* cast java.time.LocalDateTime value */
		if (p_o_value instanceof java.time.LocalDateTime) {
			if (this.e_base == BaseGateway.MSSQL) {
				s_foo = ((java.time.LocalDateTime) p_o_value).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
			} else if (this.e_base == BaseGateway.ORACLE) {
				s_foo = ((java.time.LocalDateTime) p_o_value).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss"));
			} else {
				s_foo = ((java.time.LocalDateTime) p_o_value).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss"));
			}
			
			b_returnString = true;
		}
		
		/* cast java.time.LocalDate value */
		if (p_o_value instanceof java.time.LocalDate) {
			s_foo = ((java.time.LocalDate) p_o_value).format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			b_returnString = true;
		}
		
		/* cast java.time.LocalTime value */
		if (p_o_value instanceof java.time.LocalTime) {
			if (this.e_base == BaseGateway.ORACLE) {
				s_foo = ((java.time.LocalTime) p_o_value).format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
				return (Object)("TO_DSINTERVAL([forestjSQLValue]+0 " + s_foo + "[/forestjSQLValue])");
			} else {
				s_foo = ((java.time.LocalTime) p_o_value).format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
			}
			
			b_returnString = true;
		}
		
		/* cast boolean value */
		if (p_o_value instanceof Boolean) {
			b_foo = ((Boolean)p_o_value).booleanValue();
			b_returnBoolean = true;
		}
		
		/* cast short value */
		if ( (p_o_value instanceof Short) || ( (p_o_value != null) && (p_o_value.getClass().getName().equals("short")) ) ) {
			sh_foo = (Short)p_o_value;
			b_returnShort = true;
		}
		
		/* cast integer value */
		if ( (p_o_value instanceof Integer) || ( (p_o_value != null) && (p_o_value.getClass().getName().equals("int")) ) ) {
			i_foo = (Integer)p_o_value;
			b_returnInteger = true;
		}
		
		/* cast long value */
		if ( (p_o_value instanceof Long) || ( (p_o_value != null) && (p_o_value.getClass().getName().equals("long")) ) ) {
			l_foo = (Long)p_o_value;
			b_returnLong = true;
		}
		
		/* cast double or float value */
		if ( (p_o_value instanceof Double) || (p_o_value instanceof Float) ) {
			if (p_o_value instanceof Float) {
				d_foo = Double.parseDouble(p_o_value.toString());
			} else {
				d_foo = (Double)p_o_value;
			}
			
			b_returnDouble = true;
		}
		
		/* cast java.math.BigDecimal value */
		if (p_o_value instanceof java.math.BigDecimal) {
			o_bigDecimalFoo = java.math.BigDecimal.class.cast(p_o_value);
			b_returnBigDecimal = true;
		}
		
		/* return string with value tag */
		if (b_returnString) {
			s_foo = s_foo.replace("[forestjSQLValue]", "").replace("[/forestjSQLValue]", "");
			return (Object)("[forestjSQLValue]" + s_foo + "[/forestjSQLValue]");
		}
		
		/* return short with value tag */
		if (b_returnShort) {
			return (Object)("[forestjSQLValue]" + sh_foo + "[/forestjSQLValue]");
		}
		
		/* return integer with value tag */
		if (b_returnInteger) {
			return (Object)("[forestjSQLValue]" + i_foo + "[/forestjSQLValue]");
		}
		
		/* return long with value tag */
		if (b_returnLong) {
			return (Object)("[forestjSQLValue]" + l_foo + "[/forestjSQLValue]");
		}
		
		/* return double with value tag */
		if (b_returnDouble) {
			return (Object)("[forestjSQLValue]" + d_foo + "[/forestjSQLValue]");
		}
		
		/* return java.math.BigDecimal with value tag */
		if (b_returnBigDecimal) {
			return (Object)("[forestjSQLValue][forestjBigDecimal]" + o_bigDecimalFoo + "[/forestjBigDecimal][/forestjSQLValue]");
		}
		
		/* return boolean with value tag */
		if (b_returnBoolean) {
			return (Object)("[forestjSQLValue]" + b_foo + "[/forestjSQLValue]");
		}
		
		/* return empty string */
		return (Object)"";
	}
	
	/**
	 * Abstract toString function so any query class inheriting from QueryAbstract must have this method
	 */
	abstract public String toString();
}
