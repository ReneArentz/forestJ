package net.forestany.forestj.lib.sql;

import net.forestany.forestj.lib.sqlcore.BaseGateway;
import net.forestany.forestj.lib.sqlcore.IQuery;
import net.forestany.forestj.lib.sqlcore.SqlType;

/**
 * Abstract record class - automatically detecting column fields and give easy DML methods usage in connection with a global used base property 'net.forestany.forestj.lib.Global.get().Base'
 *
 * @param <T>	Class definition of current class, e.g. Test extends net.forestany.forestj.lib.sql.Record&lt;Test.class&gt;
 */
public abstract class Record<T> {
	
	/* Delegates */
	
	/**
	 * interface delegate definition which can be instanced outside of Record class to process sql queries with another Base instead of Global.Base
	 */
	public interface IDelegate {
		/**
		 * method to fetch a query with another Base instance instead of Global.Base
		 * 
		 * @param p_o_sqlQuery							query object of Query class &lt;? must be of type QueryAbstract&gt;
		 * @return java.util.List&lt;java.util.LinkedHashMap&lt;String, Object&gt;&gt; list of hash maps, key(string) -> column name + value(object) -> column value of a database record
		 * @throws IllegalAccessException				exception accessing column type, column name or just column value of current result set record
		 * @throws RuntimeException						base instance is not connected
		 * @throws InterruptedException					base execution was interrupted
		 */
		java.util.List<java.util.LinkedHashMap<String, Object>> OtherBaseSourceImplementation(IQuery<?> p_o_sqlQuery) throws IllegalAccessException, RuntimeException, InterruptedException;
	}
	
	/* Fields */
	
	/**
	 * table
	 */
	protected String Table = null;
	/**
	 * list of primary columns
	 */
	protected java.util.List<String> Primary = new java.util.ArrayList<String>();
	/**
	 * list of unique columns
	 */
	protected java.util.List<String> Unique = new java.util.ArrayList<String>();
	/**
	 * list of order by settings
	 */
	protected java.util.Map<String, Boolean> OrderBy = new java.util.HashMap<String, Boolean>();
	
	/**
	 * start
	 */
	protected int Start = 0;
	/**
	 * interval
	 */
	public int Interval = 50;
	/**
	 * page
	 */
	public int Page = 1;
	/**
	 * amount records
	 */
	protected int AmountRecords = 0;
	/**
	 * auto transaction flag
	 */
	public boolean AutoTransaction = true;

	/**
	 * list of columns
	 */
	public java.util.List<String> Columns = new java.util.ArrayList<String>();
	/**
	 * list of filters
	 */
	public java.util.List<Filter> Filters = new java.util.ArrayList<Filter>();
	/**
	 * list of sort settings
	 */
	public java.util.Map<String, Boolean> Sort = new java.util.LinkedHashMap<String, Boolean>();
	/**
	 * record image instance
	 */
	private T o_recordImage = null;
	/**
	 * record image class
	 */
	protected Class<T> RecordImageClass = null;
	/**
	 * other base source
	 */
	private IDelegate OtherBaseSource = null;
	
	/* Properties */
	
	/**
	 * set other base source
	 * @param p_itf_delegate delegate parameter
	 */
	public void setOtherBaseSource(IDelegate p_itf_delegate)
	{
		this.OtherBaseSource = p_itf_delegate;
	}
	
	/* Methods */
	
	/**
	 * Record class constructor, initiating all values and properties and checks if all necessary restrictions are set
	 * 
	 * @throws NullPointerException			record image class, primary, unique or order by have no values
	 * @throws IllegalArgumentException		table name is invalid(empty)
	 * @throws NoSuchFieldException			given field values in primary, unique or order by do not exists is current class
	 */
	public Record() throws NullPointerException, IllegalArgumentException, NoSuchFieldException {
		/* first call init function to get initial values and properties from inherited class */
		this.init();
		
		/* check record image class is not null */
		if (this.RecordImageClass == null) {
			throw new NullPointerException("You must specify a record image class within the Init-method.");
		}
		
		/* check table is not empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(this.Table)) {
			throw new IllegalArgumentException("You must specify a table within the Init-method.");
		}
		
		/* check primary values are available */
		if (this.Primary.size() < 1) {
			throw new NullPointerException("You must specify at least one primary within the Init-method.");
		}
		
		/* check if each field in primary list really exists in current class */
		for (String s_primary : this.Primary) {
			if (!this.fieldExists(s_primary)) {
				throw new NoSuchFieldException("Primary[" + s_primary + "] is not a field of current record class.");
			}
		}
		
		/* check unique values are available */
		if (this.Unique.size() < 1) {
			throw new NullPointerException("You must specify at least one unique constraint within the Init-method.");
		}
		
		/* check if each field in unique list really exists in current class */
		for (String s_unique : this.Unique) {
			/* it is possible that a unique constraint exists of multiple columns, separated by semicolon */
			if (s_unique.contains(";")) {
				String[] a_uniques = s_unique.split(";");
				
				/* iterate each unique field */
				for (int i = 0; i < a_uniques.length; i++) {
					/* check if unique field really exists */
					if (!this.fieldExists(a_uniques[i])) {
						throw new NoSuchFieldException("Unique[" + s_unique + "] is not a field of current record class.");
					}
				}
			} else {
				/* check if unique field really exists */
				if (!this.fieldExists(s_unique)) {
					throw new NoSuchFieldException("Unique[" + s_unique + "] is not a field of current record class.");
				}
			}
		}
		
		/* check order by values are available */
		if (this.OrderBy.size() < 1) {
			throw new NullPointerException("You must specify at least one order by within the Init-method.");
		}
		
		/* check if each field in order by list really exists in current class */
		for (String s_field : this.OrderBy.keySet()) {
			if (!this.fieldExists(s_field)) {
				throw new NoSuchFieldException("OrderBy[" + s_field + "] is not a field of current record class.");
			}
		}
		
												if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("created record object with table '" + this.Table + "'");
	}
	
	/**
	 * Abstract Init function so any class inheriting from Record&lt;T&gt; must have this method
	 * declaring table, start, interval, primary, unique, order by and most of all record image class
	 */
	abstract protected void init();
	
	/**
	 * Easy static method to return stored rows in a linked hash map out in a string which can be shown anywhere
	 * 
	 * @param p_a_rows		stored rows in a linked hash map
	 * @return String		a string with table header and all row column values
	 */
	public static String printRows(java.util.List<java.util.LinkedHashMap<String, Object>> p_a_rows) {
		/* string builder variable to store all column names and column values */
		StringBuilder o_stringBuilder = new StringBuilder();
				
		/* check if parameter has any values */
		if ( (p_a_rows != null) && (p_a_rows.size() > 0) ) {
			/* flag for printing header */
			boolean b_once = false;
			
			/* iterate each row */
			for (java.util.HashMap<String, Object> o_row : p_a_rows) {
				if (!b_once) {
					/* print header and all column names */
					o_row.forEach((s_column, o_value) -> o_stringBuilder.append(s_column + " | ") );
					o_stringBuilder.append("\n");
					b_once = true;
				}
				
				/* print row column values */
				o_row.forEach((s_column, o_value) -> o_stringBuilder.append(o_value + " | ") );
				o_stringBuilder.append("\n");
			}
		} else { /* parameter is null or has no rows */
			o_stringBuilder.append("No rows available");
		}
		
		return o_stringBuilder.toString();
	}
	
	/**
	 * Method to retrieve column value of current record class
	 * 
	 * @param <T2>						generic object type
	 * @param p_s_column				column name, will be changed to 'Column' + p_s_column
	 * @return &lt;T2&gt;				unknown object type until method in use
	 * @throws NoSuchFieldException		column field does not exist
	 * @throws IllegalAccessException	cannot access column field, must be public
	 */
	@SuppressWarnings("unchecked")
	protected <T2> T2 getColumnValue(String p_s_column) throws NoSuchFieldException, IllegalAccessException {
		p_s_column = "Column" + p_s_column;
		
												if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value from '" + p_s_column + "'");
		
		T2 o_foo = null;
		/* get class type */
		Class<T2> o_class = (Class<T2>) this.getClass().getDeclaredField(p_s_column).getType();
		/* get class name */
		String s_class = o_class.getName();
		
		/* switch class value by class name for casting */
		switch(s_class) {
			case "boolean":
			case "java.lang.Boolean":
				o_class = (Class<T2>) Boolean.class;
				break;
			case "double":
			case "java.lang.Double":
				o_class = (Class<T2>) Double.class;
				break;
			case "float":
			case "java.lang.Float":
				o_class = (Class<T2>) Float.class;
				break;
			case "long":
			case "java.lang.Long":
				o_class = (Class<T2>) Long.class;
				break;
			case "int":
			case "java.lang.Integer":
				o_class = (Class<T2>) Integer.class;
				break;
			case "short":
			case "java.lang.Short":
				o_class = (Class<T2>) Short.class;
				break;
			case "char":
			case "java.lang.Character":
				o_class = (Class<T2>) Character.class;
				break;
			case "byte":
			case "java.lang.Byte":
				o_class = (Class<T2>) Byte.class;
				break;
			case "string":
			case "java.lang.String":
				o_class = (Class<T2>) String.class;
				break;
			case "java.util.Date":
				o_class = (Class<T2>) java.util.Date.class;
				break;
			case "java.time.LocalDateTime":
				o_class = (Class<T2>) java.time.LocalDateTime.class;
				break;
			case "java.time.LocalDate":
				o_class = (Class<T2>) java.time.LocalDate.class;
				break;
			case "java.time.LocalTime":
				o_class = (Class<T2>) java.time.LocalTime.class;
				break;
			case "java.math.BigDecimal":
				o_class = (Class<T2>) java.math.BigDecimal.class;
				break;
		}
		
		/* cast object of column field */
		o_foo = o_class.cast(this.getClass().getDeclaredField(p_s_column).get(this));

		/* return casted object */
		return o_foo;
	}
	
	/**
	 * Method to set a column value of current record class
	 * 
	 * @param p_s_column				column name, will be changed to 'Column' + p_s_column
	 * @param o_value					object value which will be set as column value
	 * @throws NoSuchFieldException		column field does not exist
	 * @throws IllegalAccessException	cannot access column field, must be public
	 */
	protected void setColumnValue(String p_s_column, Object o_value) throws NoSuchFieldException, IllegalAccessException {
		p_s_column = "Column" + p_s_column;
		
		if (o_value != null) {
													if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("set column value for: '" + p_s_column + "'" + "\t\tfield type: " + this.getClass().getDeclaredField(p_s_column).getType().getTypeName() + "\t\tvalue type: " + o_value.getClass().getTypeName());
			
			if (this.getClass().getDeclaredField(p_s_column).getType().getTypeName().contentEquals("java.time.LocalDateTime")) {
				/* if we have column field with type java.time.LocalDateTime we have to do a cast from java.sql.Timestamp */
				o_value = (java.sql.Timestamp.class.cast(o_value)).toLocalDateTime();
			} else if (this.getClass().getDeclaredField(p_s_column).getType().getTypeName().contentEquals("java.time.LocalDate")) { 
				/* if we have column field with type java.time.LocalDate we have to do a cast from java.sql.Timestamp */
				o_value = (java.sql.Timestamp.class.cast(o_value)).toLocalDateTime().toLocalDate();
			} else if (this.getClass().getDeclaredField(p_s_column).getType().getTypeName().contentEquals("java.time.LocalTime")) {
				/* if we have column field with type java.time.LocalTime we have to do a cast from java.sql.Timestamp */
				o_value = (java.sql.Time.class.cast(o_value)).toLocalTime();
			} else if ( (this.getClass().getDeclaredField(p_s_column).getType().getTypeName().toLowerCase().contains("bigdecimal")) && (o_value.getClass().getTypeName().toLowerCase().contains("string")) ) {
				/* recognize empty null value for java.math.BigDecimal */
				if ( (o_value.toString().length() == 0) || (o_value.toString().contentEquals("NULL")) ) {
					o_value = null;
				}
			} else if ( (net.forestany.forestj.lib.Global.get().BaseGateway == BaseGateway.PGSQL) && (this.getClass().getDeclaredField(p_s_column).getType().getTypeName().toLowerCase().contains("boolean")) && (o_value.getClass().getTypeName().toLowerCase().contains("short")) ) {
				/* MSSQL + PGSQL stores bit columns as tinyint, in ORACLE we just store one character */
				if (o_value.toString().compareTo("0") == 0) {
					o_value = (Object)false;
				} else {
					o_value = (Object)true;
				}
			} else if ( (net.forestany.forestj.lib.Global.get().BaseGateway == BaseGateway.NOSQLMDB) && (this.getClass().getDeclaredField(p_s_column).getType().getTypeName().toLowerCase().contains("short")) && ( (o_value.getClass().getTypeName().toLowerCase().contains("integer")) || (o_value.getClass().getTypeName().toLowerCase().contains("long")) ) ) {
				/* nosqlmdb does not support short or smallint, only int32 and long - so we must transpose int value to short value */
				o_value = (Object)Short.valueOf(o_value.toString());
			}
		}
		
		/* set column value, accessing 'this' class and field with column name */
		this.getClass().getDeclaredField(p_s_column).set(this, o_value);
	}
	
	/**
	 * Easy method to return all columns with their values of current record class
	 * @return String	a string line of all columns with their values "column_name = column_value|"
	 */
	public String returnColumns() {
		String s_foo = "";
		
		/* iterate each field of current record class */
		for (int i = 0; i < this.getClass().getDeclaredFields().length; i++) {
			/* get field */
			java.lang.reflect.Field o_field =  this.getClass().getDeclaredFields()[i];
			
			/* check if field starts with 'Column', but is not equal to 'Columns' */
			if ( (o_field.getName().startsWith("Column")) && (o_field.getName().compareTo("Columns") != 0) ) {
				try {
					/* get column value */
					Object o_foo = o_field.get(this);
					/* set column string value to 'NULL', in case value is null */
					String s_object = "NULL";
					
					/* if column value is not null, use toString method */
					if (o_foo != null) {
						s_object = o_foo.toString();
					}
					
					/* add column name and its value to return string */
					s_foo += o_field.getName().substring(6) + " = " + s_object + "|";
				} catch (Exception o_exc) {
					/* just continue if column name or column value cannot be retrieved */
					s_foo += o_field.getName().substring(6) + " = COULD_NOT_RETRIEVE|";
				}
			}
		}
		
		return s_foo;
	}
	
	/**
	 * Assume column values of a linked hash map row record to columns of current record class and its record image
	 * 
	 * @param o_row												columns and their values in a linked hash map
	 * @throws java.lang.reflect.InvocationTargetException		if the underlying constructor throws an exception
	 * @throws java.lang.InstantiationException					if the class that declares the underlying constructor represents an abstract class
	 * @throws IllegalAccessException							cannot access column field, must be public
	 * @throws NoSuchMethodException							could not retrieve declared constructor
	 * @throws ClassNotFoundException							class cannot be located
	 */
	@SuppressWarnings("unchecked")
	protected void takeOverRow(java.util.LinkedHashMap<String, Object> o_row) throws java.lang.reflect.InvocationTargetException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException, ClassNotFoundException {
		/* create new instance of inherited class */
		this.o_recordImage = null;
		
		/* check if class type of object is a inner class */
		if (this.RecordImageClass.getTypeName().contains("$")) {
			/* get target class */
			Class<?> o_targetClass = Class.forName(this.RecordImageClass.getTypeName());
			
			/* get instance of parent class */
			Object o_parentClass = Class.forName(this.RecordImageClass.getTypeName().split("\\$")[0]).getDeclaredConstructor().newInstance();
			boolean b_found = false;
			
			/* look for declared inner classes in parent class */
			for (Class<?> o_subClass : o_parentClass.getClass().getDeclaredClasses()) {
				/* inner class must match with Class<?> type parameter of dynamic generic list */
				if (o_subClass == o_targetClass) {
					b_found = true;
					/* create new object instance of inner class, with help of parent class instance */
					this.o_recordImage = (T) o_targetClass.getDeclaredConstructor(o_parentClass.getClass()).newInstance(o_parentClass);
					break;
				}
			}
			
			/* throw exception if inner class could not be found */
			if (!b_found) {
				throw new ClassNotFoundException("Could not found inner class in scope '" + o_targetClass.getTypeName() + "'");
			}
		} else {
			/* create new instance of inherited class */
			this.o_recordImage = this.RecordImageClass.getDeclaredConstructor().newInstance();
		}
		
		/* iterate each column in row */
		o_row.forEach( (s_column, o_value) -> {
			try {
														if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("check if field exists '" + s_column + "'");
				
				/* check if column exists as field in current record class */
				if (fieldExists(s_column)) {
															if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("set column value for record object");
					
					/* set column value in current record class */
					this.setColumnValue(s_column, o_value);
					
															if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("set column value for record image");
					
					/* set column value in record image */
					(this.getClass().cast(this.o_recordImage)).setColumnValue(s_column, o_value);
				} else {
					net.forestany.forestj.lib.Global.ilogWarning(s_column + " does not exist");
				}
			} catch (Exception o_exc) {
				/* just continue if field does not exist */
				net.forestany.forestj.lib.Global.ilogWarning(o_value.toString() + " ;;; cannot set '" + s_column + "' with type '" + ( (o_value == null) ? "null" : o_value.getClass().getTypeName() ) + "' - " + o_exc);
			}
		} );
	}
	
	/**
	 * Method to check if a field exists in current record class
	 * 
	 * @param p_s_field			colum name
	 * @return boolean			true - field exist, false - field does not exist
	 */
	protected boolean fieldExists(String p_s_field) {
		/* return value */
		boolean b_found = false;
		
		/* iterate each field of current record class */
		for (int i = 0; i < this.getClass().getDeclaredFields().length; i++) {
			/* get field */
			java.lang.reflect.Field o_field =  this.getClass().getDeclaredFields()[i];
			
			/* check if field starts with 'Column', but is not equal to 'Columns' */
			if ( (o_field.getName().startsWith("Column")) && (o_field.getName().compareTo("Columns") != 0) ) {
				/* field name without 'Column' prefix must match parameter value */
				if (o_field.getName().substring(6).compareTo(p_s_field) == 0) {
					/* set return value to true */
					b_found = true;
				}
			}
		}
		
		return b_found;
	}
	
	/**
	 * Get a record by its current primary values of inherited class and assume record columns to public properties
	 * 
	 * @return													true - record has been found, false - no record has been found
	 * @throws IllegalArgumentException							list of primary values as parameter have not the same amount as list of primary columns
	 * @throws NoSuchFieldException								column field does not exist
	 * @throws IllegalAccessException							cannot access column field, must be public
	 * @throws java.sql.SQLException							exception accessing column type, column name or just column value of current result set record
	 * @throws java.text.ParseException 						could not parse column string value to java.util.Date
	 * @throws java.lang.reflect.InvocationTargetException		if the underlying constructor throws an exception
	 * @throws java.lang.InstantiationException					if the class that declares the underlying constructor represents an abstract class
	 * @throws NoSuchMethodException							could not retrieve declared constructor
	 * @throws ClassNotFoundException							class cannot be located
	 * @throws RuntimeException									base pool is not running
	 * @throws InterruptedException								base pool thread for fetching query is interrupted
	 */
	public boolean getRecord() throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException, java.sql.SQLException, java.text.ParseException, java.lang.reflect.InvocationTargetException, java.lang.InstantiationException, NoSuchMethodException, ClassNotFoundException, RuntimeException, InterruptedException {
		return this.getRecord(null);
	}
	
	/**
	 * Get a record by parameter primary values and assume record columns to public properties
	 * 
	 * @param p_a_primaryValues									list of primary values
	 * @return													true - record has been found, false - no record has been found
	 * @throws IllegalArgumentException							list of primary values as parameter have not the same amount as list of primary columns
	 * @throws NoSuchFieldException								column field does not exist
	 * @throws IllegalAccessException							cannot access column field, must be public
	 * @throws java.sql.SQLException							exception accessing column type, column name or just column value of current result set record
	 * @throws java.text.ParseException 						could not parse column string value to java.util.Date
	 * @throws java.lang.reflect.InvocationTargetException		if the underlying constructor throws an exception
	 * @throws java.lang.InstantiationException					if the class that declares the underlying constructor represents an abstract class
	 * @throws NoSuchMethodException							could not retrieve declared constructor
	 * @throws ClassNotFoundException							class cannot be located
	 * @throws RuntimeException									base pool is not running
	 * @throws InterruptedException								base pool thread for fetching query is interrupted
	 */
	public boolean getRecord(java.util.List<Object> p_a_primaryValues) throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException, java.sql.SQLException, java.text.ParseException, java.lang.reflect.InvocationTargetException, java.lang.InstantiationException, NoSuchMethodException, ClassNotFoundException, RuntimeException, InterruptedException {
		/* if we have primary values as parameter it must be the same size as primary fields existing */
		if ( (p_a_primaryValues != null) && (this.Primary.size() != p_a_primaryValues.size()) ) {
			throw new IllegalArgumentException("Primary input values and primary fields are not of the same amount");
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("create query select object");
		
		/* create select query */
		Query<Select> o_querySelect = new Query<Select>(net.forestany.forestj.lib.Global.get().BaseGateway, SqlType.SELECT, this.Table);
		/* just select all columns */
		o_querySelect.getQuery().a_columns.add( new Column(o_querySelect, "*") );
		
												net.forestany.forestj.lib.Global.ilogFiner("created query select object");
		
		/* iterate each primary field */
		for (int i = 0; i < this.Primary.size(); i++) {
													net.forestany.forestj.lib.Global.ilogFinest("create where clause object with '" + this.Primary.get(i) + "'");
			
			/* create where clause for each primary field, get value from parameter list or from inherited class */
			Where o_where = new Where(o_querySelect, new Column(o_querySelect, this.Primary.get(i)), ( (p_a_primaryValues != null) ? p_a_primaryValues.get(i) : this.getColumnValue(this.Primary.get(i)) ), "=");
			
													net.forestany.forestj.lib.Global.ilogFinest("created where clause object");
			
			/* add 'AND' filter operator for concatenating all where clauses */
			if (i != 0) {
				o_where.setFilterOperator("AND");
			}
			
			/* add where clause to select query */
			o_querySelect.getQuery().a_where.add(o_where);
			
													net.forestany.forestj.lib.Global.ilogFinest("added where clause object to query");
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("execute query");
		
		/* execute select query */
		java.util.List<java.util.LinkedHashMap<String, Object>> a_rows = (this.OtherBaseSource != null) ? this.OtherBaseSource.OtherBaseSourceImplementation(o_querySelect) : net.forestany.forestj.lib.Global.get().Base.fetchQuery(o_querySelect, this.AutoTransaction);
		
												net.forestany.forestj.lib.Global.ilogFiner("executed query");
		
		if (a_rows.size() == 1) { /* if return amount is equal 1, take over result to current record object */
													net.forestany.forestj.lib.Global.ilogFiner("take over result to current record object");
			this.takeOverRow(a_rows.get(0));
													net.forestany.forestj.lib.Global.ilogFiner("result values adopted to current record object");
			return true;
		} else { /* return amount is not equal 1, record has not been found */
													net.forestany.forestj.lib.Global.ilogFiner("record has not been found");
			return false;
		}
	}
	
	/**
	 * Get one record, independent of primary key, by parameter individual unique key and parameter values to that unique key and assume record columns to public properties
	 * 
	 * @param p_a_unique										parameter unique key which can be individual and contain all columns of inherited class
	 * @param p_a_values										parameter values to unique key
	 * @return													true - record has been found, false - no record has been found
	 * @throws IllegalArgumentException							a column does not exist in unique key or list of unique key values have not the same amount as list of unique columns
	 * @throws NoSuchFieldException								column field does not exist
	 * @throws IllegalAccessException							cannot access column field, must be public
	 * @throws java.sql.SQLException							exception accessing column type, column name or just column value of current result set record
	 * @throws java.text.ParseException 						could not parse column string value to java.util.Date
	 * @throws java.lang.reflect.InvocationTargetException		if the underlying constructor throws an exception
	 * @throws java.lang.InstantiationException					if the class that declares the underlying constructor represents an abstract class
	 * @throws NoSuchMethodException							could not retrieve declared constructor
	 * @throws ClassNotFoundException							class cannot be located
	 * @throws RuntimeException									base pool is not running
	 * @throws InterruptedException								base pool thread for fetching query is interrupted
	 */
	public boolean getOneRecord(java.util.List<String> p_a_unique, java.util.List<Object> p_a_values) throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException, java.sql.SQLException, java.text.ParseException, java.lang.reflect.InvocationTargetException, java.lang.InstantiationException, NoSuchMethodException, ClassNotFoundException, RuntimeException, InterruptedException {
												net.forestany.forestj.lib.Global.ilogFiner("check if each column in unique key really exists as column");
		
		/* check if each column in unique key really exists as column in inherited class */
		for (String s_unique : p_a_unique) {
													net.forestany.forestj.lib.Global.ilogFinest("check column '" + s_unique + "'");
													
			if (!this.fieldExists(s_unique)) {
				throw new IllegalArgumentException("Column[" + s_unique + "] does not exist");
			}
		}
	
												net.forestany.forestj.lib.Global.ilogFiner("each column in unique key really exists");
		
		/* save old primary key */
		java.util.List<String> OldPrimary = this.Primary;
		
												net.forestany.forestj.lib.Global.ilogFiner("set unique key as temporarily primary key");
		
		/* set unique key as temporarily primary key */
		this.Primary = p_a_unique;
		
		/* get record with parameter value list */
		boolean b_foo = this.getRecord(p_a_values);
		
												net.forestany.forestj.lib.Global.ilogFiner("restore old primary key");
		
		/* restore old primary key */
		this.Primary = OldPrimary;
		
		/* return result of GetRecord */
		return b_foo;
	}
	
	/**
	 * Get multiple amount of records with paging, select can be combined with optional columns, filter and order by lists within record class which are automatically part for each inherited class
	 * 
	 * @return													all records in a result list
	 * @throws IllegalArgumentException							a column does not exist in optional columns, filter or order by lists
	 * @throws IllegalAccessException							exception accessing column type, column name or just column value of current result set record
	 * @throws RuntimeException									base pool is not running
	 * @throws InterruptedException								base pool thread for fetching query is interrupted
	 */
	public java.util.List<T> getRecords() throws IllegalArgumentException, IllegalAccessException, RuntimeException, InterruptedException {
		return this.getRecords(false);
	}
	
	/**
	 * Get multiple amount of records, select can be combined with optional columns, filter and order by lists within record class which are automatically part for each inherited class
	 * supports paging
	 * 
	 * @param p_b_unlimited										true - return all records, false - use paging with page and interval properties
	 * @return													all records in a result list
	 * @throws IllegalArgumentException							a column does not exist in optional columns, filter or order by lists
	 * @throws IllegalAccessException							exception accessing column type, column name or just column value of current result set record
	 * @throws RuntimeException									base pool is not running
	 * @throws InterruptedException								base pool thread for fetching query is interrupted
	 */
	@SuppressWarnings("unchecked")
	public java.util.List<T> getRecords(boolean p_b_unlimited) throws IllegalArgumentException, IllegalAccessException, RuntimeException, InterruptedException {
												net.forestany.forestj.lib.Global.ilogFiner("create query select object");
		
		/* create select query object */
		Query<Select> o_querySelect = new Query<Select>(net.forestany.forestj.lib.Global.get().BaseGateway, SqlType.SELECT, this.Table);
		
												net.forestany.forestj.lib.Global.ilogFiner("created query select object");
		
		/* adding optional columns for the query if they are set */
		if (this.Columns.size() > 0) {
													net.forestany.forestj.lib.Global.ilogFiner("check if each column in Columns property really exists");
			
			/* iterate each column */
			for (String s_column : this.Columns) {
														net.forestany.forestj.lib.Global.ilogFinest("check column '" + s_column + "'");
				
				/* check if each column really exists as column in inherited class */
				if (!this.fieldExists(s_column)) {
					throw new IllegalArgumentException("Column[" + s_column + "] does not exist");
				}
				
														net.forestany.forestj.lib.Global.ilogFinest("add column to query select");
				
				/* add column to select query */
				o_querySelect.getQuery().a_columns.add( new Column(o_querySelect, s_column) );
				
														net.forestany.forestj.lib.Global.ilogFinest("added column to query select");
			}
			
													net.forestany.forestj.lib.Global.ilogFiner("each column in Columns property really exists");
		} else {
													net.forestany.forestj.lib.Global.ilogFiner("just select all columns with '*'");
			
			/* just select all columns */
			o_querySelect.getQuery().a_columns.add( new Column(o_querySelect, "*") );
		}
		
		/* implement optional filter */
		if (this.Filters.size() > 0) {
			/* flag if where clause has been started */
			boolean b_initWhere = false;
			
													net.forestany.forestj.lib.Global.ilogFiner("check if each column in Filters property really exists");
			
			/* iterate each filter column */
			for (Filter o_filter : this.Filters) {
														net.forestany.forestj.lib.Global.ilogFinest("check column '" + o_filter.s_column + "'");
				
				/* check if each filter column really exists as column in inherited class */
				if (!this.fieldExists(o_filter.s_column)) {
					throw new IllegalArgumentException("Filter Column[" + o_filter.s_column + "] does not exist");
				}
				
														net.forestany.forestj.lib.Global.ilogFinest("add column to new where clause object");
				
				/* create where clause object */
				Where o_where = new Where(o_querySelect, new Column(o_querySelect, o_filter.s_column), o_filter.o_value, o_filter.s_operator);
				
														net.forestany.forestj.lib.Global.ilogFinest("added column to new where clause object");
				
				if (b_initWhere) { /* set filter operator if where clause has been started */
					/* add filter operator if it is not empty */
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_filter.s_filterOperator)) {
						o_where.setFilterOperator(o_filter.s_filterOperator);
					}
				}
				
				/* at least here, where clause has been started */
				b_initWhere = true;
				
														net.forestany.forestj.lib.Global.ilogFinest("add where clause object to query select");
				
				/* add where clause to select query */
				o_querySelect.getQuery().a_where.add( o_where );
				
														net.forestany.forestj.lib.Global.ilogFinest("added where clause object to query select");
			}
			
													net.forestany.forestj.lib.Global.ilogFiner("each column in Filters property really exists");
		}
		
		/* temporarily order by map */
		java.util.Map<String, Boolean> m_temp = new java.util.LinkedHashMap<String, Boolean>();
				
		if (this.Sort.size() > 0) { /* implement order by from optional sort hash map */
													net.forestany.forestj.lib.Global.ilogFiner("implement order by from optional sort hash map");
			m_temp = this.Sort;
		} else if (this.OrderBy.size() > 0) { /* implement order by from defined order by hash map from inherited class */
													net.forestany.forestj.lib.Global.ilogFiner("implement order by from defined order by hash map from inherited class");
			m_temp = this.OrderBy;
		}
		
		/* if we have an optional sort hash map or defined order by hash map from inherited class */
		if (m_temp.size() > 0) {
			/* temp lists to implement order by to select query */
			java.util.List<Column> a_columns = new java.util.ArrayList<Column>();
			java.util.List<String> a_stringColumns = new java.util.ArrayList<String>();
			java.util.List<Boolean> a_directions = new java.util.ArrayList<Boolean>();
			
			/* assume all sort columns and directions from temporarily order by map, based on optional sort hash map or definition from inherited class */
			m_temp.forEach((s_column, b_direction) -> {
				a_stringColumns.add(s_column);
				a_directions.add(b_direction);
			});
			
													net.forestany.forestj.lib.Global.ilogFiner("check if each column in OrderBy property really exists");
			
			/* iterate each sort column */
			for (String s_column : a_stringColumns) {
														net.forestany.forestj.lib.Global.ilogFinest("check column '" + s_column + "'");
				
				/* check if each sort column really exists as column in inherited class */
				if (!this.fieldExists(s_column)) {
					throw new IllegalArgumentException("Sort Column[" + s_column + "] does not exist");
				} else {
															net.forestany.forestj.lib.Global.ilogFinest("add column to temp column list");
					
					/* assume sort column to temp column list */
					a_columns.add( new Column(o_querySelect, s_column) );
					
															net.forestany.forestj.lib.Global.ilogFinest("added column to temp column list");
				}
			}
			
													net.forestany.forestj.lib.Global.ilogFiner("each column in OrderBy property really exists");
			
													net.forestany.forestj.lib.Global.ilogFiner("create order by object for query select");
													
			/* add order by to select query */
			o_querySelect.getQuery().o_orderBy = new OrderBy(o_querySelect, a_columns, a_directions);
			
													net.forestany.forestj.lib.Global.ilogFiner("created order by object for query select");
		}
		
		/* implement limit if not unlimited is set */
		if (!p_b_unlimited) {
			/* page must be at least '1' */
			if (this.Page < 1) {
														net.forestany.forestj.lib.Global.ilogFiner("set Page to '1'");
				
				this.Page = 1;
			}
			
			if (this.Page > 1) { /* if page is greater than 1, we need to calculate a new start value */
														net.forestany.forestj.lib.Global.ilogFiner("Page > '1'");
				
				/* get amount of record */
				this.AmountRecords = this.getCount();
				
														net.forestany.forestj.lib.Global.ilogFiner("calculate pages to show all records");
				
				/* calculate pages to show all records */
				int i_pages = this.AmountRecords / this.Interval + ((this.AmountRecords % this.Interval == 0) ? 0 : 1);
				
														net.forestany.forestj.lib.Global.ilogFiner("calculated pages: '"+ i_pages  +"'");
				
				/* page cannot be greater than amount of calculated pages for all records */
				if (this.Page >= i_pages) {
					this.Page = i_pages;
				}
				
														net.forestany.forestj.lib.Global.ilogFiner("calculate new start value");
				
				/* calculate new start value */
				this.Start = (this.Page - 1) * this.Interval;
				
														net.forestany.forestj.lib.Global.ilogFiner("calculated start: '"+ this.Start  +"'");
			}
			
													net.forestany.forestj.lib.Global.ilogFiner("create limit object for query select with start '" + this.Start + "' and interval '" + this.Interval + "'");
			
			/* add limit cause to select query */
			o_querySelect.getQuery().o_limit = new Limit(o_querySelect, this.Start, this.Interval);
		}
		
		/* result list */
		java.util.List<T> a_result = new java.util.ArrayList<T>();
		
												net.forestany.forestj.lib.Global.ilogFiner("execute query");
		
		/* execute select queries and get all rows */
		java.util.List<java.util.LinkedHashMap<String, Object>> a_rows = (this.OtherBaseSource != null) ? this.OtherBaseSource.OtherBaseSourceImplementation(o_querySelect) : net.forestany.forestj.lib.Global.get().Base.fetchQuery(o_querySelect, this.AutoTransaction);
		
												net.forestany.forestj.lib.Global.ilogFiner("executed query");
		
												net.forestany.forestj.lib.Global.ilogFiner("iterate each result record");
					
		/* iterate each result record */
		a_rows.forEach((o_row) -> {
			try {
				net.forestany.forestj.lib.Global.ilogFinest("take over result to current record object");
				
				/* create new instance of inherited class */
				T o_temp = null;
				
				/* check if class type of object is a inner class */
				if (this.RecordImageClass.getTypeName().contains("$")) {
					/* get target class */
					Class<?> o_targetClass = Class.forName(this.RecordImageClass.getTypeName());
					
					/* get instance of parent class */
					Object o_parentClass = Class.forName(this.RecordImageClass.getTypeName().split("\\$")[0]).getDeclaredConstructor().newInstance();
					boolean b_found = false;
					
					/* look for declared inner classes in parent class */
					for (Class<?> o_subClass : o_parentClass.getClass().getDeclaredClasses()) {
						/* inner class must match with Class<?> type parameter of dynamic generic list */
						if (o_subClass == o_targetClass) {
							b_found = true;
							/* create new object instance of inner class, with help of parent class instance */
							o_temp = (T) o_targetClass.getDeclaredConstructor(o_parentClass.getClass()).newInstance(o_parentClass);
							break;
						}
					}
					
					/* throw exception if inner class could not be found */
					if (!b_found) {
						throw new ClassNotFoundException("Could not found inner class in scope '" + o_targetClass.getTypeName() + "'");
					}
				} else {
					/* create new instance of inherited class */
					o_temp = this.RecordImageClass.getDeclaredConstructor().newInstance();
				}
				
														net.forestany.forestj.lib.Global.ilogFinest("take over result to new instance of inherited class");
				
				/* assume result in new instance */
				(this.getClass().cast(o_temp)).takeOverRow(o_row);
				
														net.forestany.forestj.lib.Global.ilogFinest("result values adopted to new instance of inherited class");
				
														net.forestany.forestj.lib.Global.ilogFinest("add new instance to result list");
				a_result.add(o_temp);
					
														net.forestany.forestj.lib.Global.ilogFinest("added new instance to result list");
			} catch (Exception o_exc) {
				 /* skip record if we cannot assume result in a new instance */
														net.forestany.forestj.lib.Global.ilogFinest("skipped record if we cannot adopt result in a new instance");
			}
		});
		
												net.forestany.forestj.lib.Global.ilogFiner("return result list");
		
		/* return result list */
		return a_result;
	}
	
	/**
	 * Get amount of records of current table declared in inherited class, select can be combined with optional filter list
	 * 
	 * @return													records count 0 .. x or -1 if an error occurred
	 * @throws IllegalArgumentException							a column does not exist in optional filter list
	 * @throws IllegalAccessException							exception accessing column type, column name or just column value of current result set record
	 * @throws RuntimeException									base pool is not running
	 * @throws InterruptedException								base pool thread for fetching query is interrupted
	 */
	public int getCount() throws IllegalArgumentException, IllegalAccessException, RuntimeException, InterruptedException {
												net.forestany.forestj.lib.Global.ilogFiner("create query select object");
		
		/* create select query */
		Query<Select> o_querySelect = new Query<Select>(net.forestany.forestj.lib.Global.get().BaseGateway, SqlType.SELECT, this.Table);
		
												net.forestany.forestj.lib.Global.ilogFiner("created query select object");
												
												net.forestany.forestj.lib.Global.ilogFiner("add 'COUNT(*) AS AmountRecord' to query select object");
		
		/* add count aggregation for amount of records */
		o_querySelect.getQuery().a_columns.add( new Column(o_querySelect, "*", "AmountRecords", "COUNT") );
		
												net.forestany.forestj.lib.Global.ilogFiner("added 'COUNT(*) AS AmountRecord' to query select object");
		
		/* implement optional filter */
		if (this.Filters.size() > 0) {
			/* flag if where clause has been started */
			boolean b_initWhere = false;
			
													net.forestany.forestj.lib.Global.ilogFiner("check if each column in Filters property really exists");
			
			/* iterate each filter column */
			for (Filter o_filter : this.Filters) {
														net.forestany.forestj.lib.Global.ilogFinest("check column '" + o_filter.s_column + "'");
				
				/* check if each filter column really exists as column in inherited class */
				if (!this.fieldExists(o_filter.s_column)) {
					throw new IllegalArgumentException("Filter Column[" + o_filter.s_column + "] does not exist");
				}
				
														net.forestany.forestj.lib.Global.ilogFinest("add column to new where clause object");
				
				/* create where clause object */
				Where o_where = new Where(o_querySelect, new Column(o_querySelect, o_filter.s_column), o_filter.o_value, o_filter.s_operator);
				
														net.forestany.forestj.lib.Global.ilogFinest("added column to new where clause object");
				
				if (b_initWhere) { /* set filter operator if where clause has been started */
					/* add filter operator if it is not empty */
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_filter.s_filterOperator)) {
						o_where.setFilterOperator(o_filter.s_filterOperator);
					}
				}
				
				/* at least here, where clause has been started */
				b_initWhere = true;
				
														net.forestany.forestj.lib.Global.ilogFinest("add where clause object to query select");
				
				/* add where clause to select query */
				o_querySelect.getQuery().a_where.add( o_where );
				
														net.forestany.forestj.lib.Global.ilogFinest("added where clause object to query select");
			}
			
													net.forestany.forestj.lib.Global.ilogFiner("each column in Filters property really exists");
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("execute query");
		
		/* execute select query and get result */
		java.util.List<java.util.LinkedHashMap<String, Object>> a_rows = (this.OtherBaseSource != null) ? this.OtherBaseSource.OtherBaseSourceImplementation(o_querySelect) : net.forestany.forestj.lib.Global.get().Base.fetchQuery(o_querySelect, this.AutoTransaction);
		
												net.forestany.forestj.lib.Global.ilogFiner("executed query");
		
		if (net.forestany.forestj.lib.Global.get().BaseGateway != BaseGateway.NOSQLMDB) {
													net.forestany.forestj.lib.Global.ilogFiner("result must be exactly one row");
			
			/* result must be exactly one row */
			if (a_rows.size() == 1) {
														net.forestany.forestj.lib.Global.ilogFiner("get 'AmountRecords' or 'amountrecords' as result count value");
				
				/* get 'AmountRecords' or 'amountrecords' as result count value */
				if (a_rows.get(0).containsKey("AmountRecords")) {
					return Integer.parseInt(a_rows.get(0).get("AmountRecords").toString());
				} else if (a_rows.get(0).containsKey("amountrecords")) {
					return Integer.parseInt(a_rows.get(0).get("amountrecords").toString());
				} else { /* could not find column 'AmountRecords', so we return -1 as error */
															net.forestany.forestj.lib.Global.ilogFiner("could not find column 'AmountRecords', so we return -1 as error");
					
					return -1;
				}
			} else { /* false result, so we return -1 as error */
														net.forestany.forestj.lib.Global.ilogFiner("false result, so we return -1 as error");
				
				return -1;
			}
		} else {
													net.forestany.forestj.lib.Global.ilogFiner("just return rows size for nosqlmdb");
			
			/* just return rows size for nosqlmdb */
			return a_rows.size();
		}
	}

	/**
	 * Inserts a record with current columns of inherited class, skip primary fields/columns
	 * checking primary key and all unique key violation which are given as information within inherited class
	 * 
	 * @return Integer											primary 'LastInsertId', but when this is not available then the amount of 'AffectedRows', -1 as error
	 * @throws IllegalArgumentException							a column does not exist in optional filter list
	 * @throws java.sql.SQLException							exception accessing column type, column name or just column value of current result set record
	 * @throws java.text.ParseException 						could not parse column string value to java.util.Date
	 * @throws NoSuchFieldException								column field does not exist
	 * @throws IllegalAccessException							cannot access column field, must be public
	 * @throws RuntimeException									base pool is not running
	 * @throws InterruptedException								base pool thread for fetching query is interrupted
	 */
	public int insertRecord() throws IllegalArgumentException, java.sql.SQLException, java.text.ParseException, NoSuchFieldException, IllegalAccessException, RuntimeException, InterruptedException {
		return insertRecord(false);
	}
	
	/**
	 * Inserts a record with current columns of inherited class
	 * checking primary key and all unique key violation which are given as information within inherited class
	 * 
	 * @param p_b_withPrimary									true - set values for primary fields/columns as well, false - skip primary fields/columns
	 * @return Integer											primary 'LastInsertId', but when this is not available then the amount of 'AffectedRows', -1 as error
	 * @throws IllegalStateException							primary key or unique key violation occurred
	 * @throws IllegalArgumentException							a column does not exist in optional filter list
	 * @throws java.sql.SQLException							exception accessing column type, column name or just column value of current result set record
	 * @throws java.text.ParseException 						could not parse column string value to java.util.Date
	 * @throws NoSuchFieldException								column field does not exist
	 * @throws IllegalAccessException							cannot access column field, must be public
	 * @throws RuntimeException									base pool is not running
	 * @throws InterruptedException								base pool thread for fetching query is interrupted
	 */
	public int insertRecord(boolean p_b_withPrimary) throws IllegalStateException, IllegalArgumentException, java.sql.SQLException, java.text.ParseException, NoSuchFieldException, IllegalAccessException, RuntimeException, InterruptedException {
		/* check uniqueness of record within table for insert query */
		int i_return = 0;
		
		/* check primary columns if we insert record with primary value */
		if (p_b_withPrimary) {
														net.forestany.forestj.lib.Global.ilogFiner("insert record with primary value");
			
														net.forestany.forestj.lib.Global.ilogFiner("create a backup of current filter list");
			
			/* create a backup of current filter list */
			java.util.List<Filter> a_backupFilters = this.Filters;
			/* clear current filter list */
			this.Filters.clear();
			
														net.forestany.forestj.lib.Global.ilogFiner("iterate each primary field/column");
			
			/* iterate each primary field/column */
			this.Primary.forEach((s_primary) -> {
				try {
															net.forestany.forestj.lib.Global.ilogFinest("add primary field/column to Filters property list: '" + s_primary + "'");
					
					/* add primary field/column with it's value to filter list */
					this.Filters.add( new Filter(s_primary, this.getColumnValue(s_primary), "=", "AND") );
					
															net.forestany.forestj.lib.Global.ilogFinest("added primary field/column to Filters property list");
				} catch (Exception o_exc) {
					/* skip a field/column if value cannot be retrieved */
															net.forestany.forestj.lib.Global.ilogFinest("skipped a field/column if value cannot be retrieved: '" + s_primary + "'");
				}
			});
			
													net.forestany.forestj.lib.Global.ilogFiner("get amount of records with current primary key and it's values");
			
			/* get amount of records with current primary key and it's values */
			i_return = this.getCount();
			
													net.forestany.forestj.lib.Global.ilogFiner("amount: '" + i_return + "'");
			
													net.forestany.forestj.lib.Global.ilogFiner("restore filter list with backup");
			
			/* restore filter list with backup */
			this.Filters = a_backupFilters;
						
			/* if amount of records is greater than zero, we cannot insert that record */
			if (i_return > 0) {
															net.forestany.forestj.lib.Global.ilogFiner("Primary key violation occurred, create primary key and it's values to throw an exception");
				
				String s_primaries = "";
				String s_primaryValues = "";
				
				/* gather all primary fields/columns and it's values */
				for (String s_primary : this.Primary) {
					s_primaries += s_primary + ", ";
					s_primaryValues += this.getColumnValue(s_primary).toString() + ", ";
				}
				
				/* remove last ', ' separator */
				if (s_primaries.length() > 1) {
					s_primaries = s_primaries.substring(0, (s_primaries.length() - 2));
					s_primaryValues = s_primaryValues.substring(0, (s_primaryValues.length() - 2));
				}
				
				/* create an exception that we cannot insert a record, because of primary key violation */
				throw new IllegalStateException("Primary key violation - primary key[" + s_primaries + "](" + s_primaryValues + ") already exists for [" + this.Table + "]");
			}
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("check unique constraints");
		
		/* check unique constraints */
		for (String s_unique : this.Unique) {
													net.forestany.forestj.lib.Global.ilogFinest("check unique constraint: '" + s_unique + "'");
			
													net.forestany.forestj.lib.Global.ilogFinest("create a backup of current filter list");
			
			/* create a backup of current filter list */
			java.util.List<Filter> a_backupFilters = this.Filters;
			/* clear current filter list */
			this.Filters.clear();
			
			/* it is possible that a unique constraint exists of multiple columns, separated by semicolon */
			if (s_unique.contains(";")) {
				String[] a_uniques = s_unique.split(";");
				
				/* iterate each unique key field/column */
				for (int i = 0; i < a_uniques.length; i++) {
					/* add unique field/column with it's value to filter list */
					this.Filters.add( new Filter(a_uniques[i], this.getColumnValue(a_uniques[i]), "=", "AND") );
				}
			} else {
				/* add unique field/column with it's value to filter list */
				this.Filters.add( new Filter(s_unique, this.getColumnValue(s_unique), "=", "AND") );
			}
			
														net.forestany.forestj.lib.Global.ilogFinest("get amount of records with current unique key and it's values");
			
			/* get amount of records with current unique key and it's values */
			i_return = this.getCount();
			
														net.forestany.forestj.lib.Global.ilogFinest("amount: '" + i_return + "'");
			
														net.forestany.forestj.lib.Global.ilogFinest("restore filter list with backup");
			
			/* restore filter list with backup */
			this.Filters = a_backupFilters;
			
			/* if amount of records is greater than zero, we cannot insert that record */
			if (i_return > 0) {
														net.forestany.forestj.lib.Global.ilogFinest("Unique key violation occurred, create unique key and it's values to throw an exception");
				
				String s_uniqueValues = "";
				
				/* it is possible that a unique constraint exists of multiple columns, separated by semicolon */
				if (s_unique.contains(";")) {
					/* split unique key */
					String[] a_uniques = s_unique.split(";");
					
					/* iterate each unique field/column */
					for (int i = 0; i < a_uniques.length; i++) {
						/* gather all unique field/column values */
						s_uniqueValues = this.getColumnValue(a_uniques[i]).toString() + ", ";
					}
					
					/* remove last ', ' separator */
					if (s_uniqueValues.length() > 1) {
						s_uniqueValues = s_uniqueValues.substring(0, (s_uniqueValues.length() - 2));
					}
				} else {
					/* add unique field/column value */
					s_uniqueValues = this.getColumnValue(s_unique).toString();
				}
				
				/* create an exception that we cannot insert a record, because of unique key violation */
				throw new IllegalStateException("Unique key violation - unique constraint invalid for [" + s_unique + "](" + s_uniqueValues + ") in table [" + this.Table + "]; unique key already exists");
			}
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("create insert query");
		
		/* create insert query */
		Query<Insert> o_queryInsert = new Query<Insert>(net.forestany.forestj.lib.Global.get().BaseGateway, SqlType.INSERT, this.Table);
		
												net.forestany.forestj.lib.Global.ilogFiner("created insert query");
		
		/* for nosqlmdb we must set 'Id' as auto increment column */
		if ( (net.forestany.forestj.lib.Global.get().BaseGateway == BaseGateway.NOSQLMDB) && (!p_b_withPrimary) && (this.Primary.contains("Id")) && (this.Primary.size() == 1) ) {
													net.forestany.forestj.lib.Global.ilogFiner("for nosqlmdb we must set 'Id' as auto increment column");
			
			o_queryInsert.getQuery().o_nosqlmdbColumnAutoIncrement = new Column(o_queryInsert, "Id");
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("read out column fields to get values for insert query");
		
		/* read out column fields to get values for insert query */
		for (int i = 0; i < this.getClass().getDeclaredFields().length; i++) {
			/* get field */
			java.lang.reflect.Field o_field =  this.getClass().getDeclaredFields()[i];
			
			/* check if field starts with 'Column', but is not equal to 'Columns' */
			if ( (o_field.getName().startsWith("Column")) && (o_field.getName().compareTo("Columns") != 0) ) {
				/* get field name without 'Column' prefix */
				String s_column = o_field.getName().substring(6);
				
														net.forestany.forestj.lib.Global.ilogFinest("check if field/column '" + s_column + "' is not part of primary key OR p_b_withPrimary = '" + p_b_withPrimary + "'");
				
				/* check if field/column is not part of primary key or we explicitly allow these fields/columns as well */
				if ( (!this.Primary.contains(s_column)) || (p_b_withPrimary) ) {
															net.forestany.forestj.lib.Global.ilogFinest("add field/column '" + s_column + "' to column value pair list of insert query");
					
					/* add field/column to column value pair list of insert query */
					o_queryInsert.getQuery().a_columnValues.add( new ColumnValue(new Column(o_queryInsert, s_column), this.getColumnValue(s_column)) );
					
															net.forestany.forestj.lib.Global.ilogFinest("added field/column to column value pair list of insert query");
				}
			}
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("execute insert query");
		
		/* execute insert query and get result */
		java.util.List<java.util.LinkedHashMap<String, Object>> a_rows = (this.OtherBaseSource != null) ? this.OtherBaseSource.OtherBaseSourceImplementation(o_queryInsert) : net.forestany.forestj.lib.Global.get().Base.fetchQuery(o_queryInsert, this.AutoTransaction);
		
												net.forestany.forestj.lib.Global.ilogFiner("insert query executed");
		
		/* result must be exactly one row */
		if (a_rows.size() == 1) {
			if (a_rows.get(0).containsKey("LastInsertId")) { /* check if 'LastInsertId' is available */
														net.forestany.forestj.lib.Global.ilogFiner("return 'LastInsertId' value");
				
				/* return 'LastInsertId' value */
				return Integer.parseInt(a_rows.get(0).get("LastInsertId").toString());
			} else if (a_rows.get(0).containsKey("AffectedRows")) { /* check if 'AffectedRows' is available */
														net.forestany.forestj.lib.Global.ilogFiner("return 0 if 'AffectedRows' value is lower than one");
				
				/* return 0 if 'AffectedRows' value is lower than one */
				if (Integer.parseInt(a_rows.get(0).get("AffectedRows").toString()) < 1) {
					return 0;
				} else {
															net.forestany.forestj.lib.Global.ilogFiner("return 'AffectedRows' value");
					
					/* return 'AffectedRows' value */
					return Integer.parseInt(a_rows.get(0).get("AffectedRows").toString());
				}
			} else { /* if we have not 'LastInsertId' and not 'AffectedRows', return -1 as error */
														net.forestany.forestj.lib.Global.ilogFiner("if we have not 'LastInsertId' and not 'AffectedRows', return -1 as error");
				
				return -1;
			}
		} else { /* result is not just one row, return -1 as error */
													net.forestany.forestj.lib.Global.ilogFiner("result is not just one row, return -1 as error");
			
			return -1;
		}
	}
	
	/**
	 * Updates a record with current columns of inherited class, with unique fields/columns as well
	 * checking primary key and all unique key violation which are given as information within inherited class
	 * 
	 * @return													primary 'AffectedRows' amount value, -1 as error
	 * @throws IllegalStateException							primary key or unique key violation occurred
	 * @throws IllegalArgumentException							a column does not exist in optional filter list
	 * @throws java.sql.SQLException							exception accessing column type, column name or just column value of current result set record
	 * @throws java.text.ParseException 						could not parse column string value to java.util.Date
	 * @throws NoSuchFieldException								column field does not exist
	 * @throws IllegalAccessException							cannot access column field, must be public
	 * @throws RuntimeException									base pool is not running
	 * @throws InterruptedException								base pool thread for fetching query is interrupted
	 */
	public int updateRecord() throws IllegalStateException, IllegalArgumentException, java.sql.SQLException, java.text.ParseException, NoSuchFieldException, IllegalAccessException, RuntimeException, InterruptedException {
		return updateRecord(true);
	}
	
	/**
	 * Updates a record with current columns of inherited class
	 * checking primary key and all unique key violation which are given as information within inherited class
	 * 
	 * @param p_b_withUnique									true - set values for unique fields/columns as well, false - skip unique fields/columns
	 * @return													primary 'AffectedRows' amount value, -1 as error
	 * @throws IllegalStateException							primary key or unique key violation occurred
	 * @throws IllegalArgumentException							a column does not exist in optional filter list
	 * @throws java.sql.SQLException							exception accessing column type, column name or just column value of current result set record
	 * @throws java.text.ParseException 						could not parse column string value to java.util.Date
	 * @throws NoSuchFieldException								column field does not exist
	 * @throws IllegalAccessException							cannot access column field, must be public
	 * @throws RuntimeException									base pool is not running
	 * @throws InterruptedException								base pool thread for fetching query is interrupted
	 */
	public int updateRecord(boolean p_b_withUnique) throws IllegalStateException, IllegalArgumentException, java.sql.SQLException, java.text.ParseException, NoSuchFieldException, IllegalAccessException, RuntimeException, InterruptedException {
		/* flag to determine if anything has changed on the record, preventing unnecessary update queries */
		boolean b_field_has_changed = false;
		
		/* check if record image is loaded */
		if (this.o_recordImage == null) {
			throw new IllegalStateException("RecordImage not loaded");
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("check if any fields/columns has changed compared to the record image");
		
		/* check if any fields/columns has changed compared to the record image */
		for (int i = 0; i < this.getClass().getDeclaredFields().length; i++) {
			/* get field */
			java.lang.reflect.Field o_field =  this.getClass().getDeclaredFields()[i];
			
			/* check if field starts with 'Column', but is not equal to 'Columns' */
			if ( (o_field.getName().startsWith("Column")) && (o_field.getName().compareTo("Columns") != 0) ) {
				/* get field name without 'Column' prefix */
				String s_column = o_field.getName().substring(6);
				
														net.forestany.forestj.lib.Global.ilogFinest("check if field/column '" + s_column + "' has changed");
				
				/* compare field/column value of current record and stored image record as this record was retrieved at least once */
				if ( !this.getColumnValue(s_column).equals(this.getClass().cast(this.o_recordImage).getColumnValue(s_column)) ) {
															net.forestany.forestj.lib.Global.ilogFinest("field/column '" + s_column + "' has changed");
					
					/* values are not equal, so at least one field has changed */
					b_field_has_changed = true;
					/* break here because one difference is sufficient */
					break;
				}
			}
		}
	
		/* there is nothing to change if flag is false */
		if (!b_field_has_changed) {
													net.forestany.forestj.lib.Global.ilogFiner("there is nothing to change");
			
			return 0;
		}
		
		/* flag to determine if primary key has changed */
		boolean b_primaryKeyChanged = false;
		
												net.forestany.forestj.lib.Global.ilogFiner("iterate each primary field/column and check if value has changed compared to the record image");
		
		/* iterate each primary field/column */
		for (String s_primary : this.Primary) {
													net.forestany.forestj.lib.Global.ilogFinest("check if primary field/column '" + s_primary + "' has changed");
			
			/* compare field/column value of current record and stored image record as this record was retrieved at least once */
			if ( !this.getColumnValue(s_primary).equals(this.getClass().cast(this.o_recordImage).getColumnValue(s_primary)) ) {
														net.forestany.forestj.lib.Global.ilogFinest("primary field/column '" + s_primary + "' has changed");
				
				/* values are not equal, so at least one primary field has changed */
				b_primaryKeyChanged = true;
				/* break here because one difference is sufficient */
				break;
			}
		}
		
		/* check primary key if it has changed */
		if (b_primaryKeyChanged) {
														net.forestany.forestj.lib.Global.ilogFiner("create a backup of current filter list");
			
			/* create a backup of current filter list */
			java.util.List<Filter> a_backupFilters = this.Filters;
			/* clear current filter list */
			this.Filters.clear();
			
														net.forestany.forestj.lib.Global.ilogFiner("iterate each primary field/column");
			
			/* iterate each primary field/column */
			this.Primary.forEach((s_primary) -> {
				try {
															net.forestany.forestj.lib.Global.ilogFinest("add primary field/column to Filters property list: '" + s_primary + "'");
					
					/* add primary field/column with it's value to filter list */
					this.Filters.add( new Filter(s_primary, this.getColumnValue(s_primary), "=", "AND") );
					
															net.forestany.forestj.lib.Global.ilogFinest("added primary field/column to Filters property list");
				} catch (Exception o_exc) {
					/* skip a field/column if value cannot be retrieved */
															net.forestany.forestj.lib.Global.ilogFinest("skipped a field/column if value cannot be retrieved: '" + s_primary + "'");
				}
			});
			
													net.forestany.forestj.lib.Global.ilogFiner("get amount of records with current primary key and it's values");
			
			/* get amount of records with current primary key and it's values */
			int i_return = this.getCount();
			
													net.forestany.forestj.lib.Global.ilogFiner("amount: '" + i_return + "'");
			
													net.forestany.forestj.lib.Global.ilogFiner("restore filter list with backup");
			
			/* restore filter list with backup */
			this.Filters = a_backupFilters;
			
			/* if amount of records is greater than zero, we cannot insert that record */
			if (i_return > 0) {
														net.forestany.forestj.lib.Global.ilogFiner("Primary key violation occurred, create primary key and it's values to throw an exception");
				
				String s_primaries = "";
				String s_primaryValues = "";
				
				/* gather all primary fields/columns and it's values */
				for (String s_primary : this.Primary) {
					s_primaries += s_primary + ", ";
					s_primaryValues += this.getColumnValue(s_primary).toString() + ", ";
				}
				
				/* remove last ', ' separator */
				if (s_primaries.length() > 1) {
					s_primaries = s_primaries.substring(0, (s_primaries.length() - 2));
					s_primaryValues = s_primaryValues.substring(0, (s_primaryValues.length() - 2));
				}
				
				/* create an exception that we cannot update the record, because of primary key violation */
				throw new IllegalStateException("Primary key violation - primary key[" + s_primaries + "](" + s_primaryValues + ") already exists for [" + this.Table + "]");
			}
		}
		
		/* flag to determine if a unique key has changed */
		boolean b_uniqueChanged = false;
		/* gather unique keys in this list */
		java.util.List<String> a_checkUniques = new java.util.ArrayList<String>();
		
												net.forestany.forestj.lib.Global.ilogFiner("iterate each unique key to check if any values changed compared to the record image");
		
		/* iterate each unique key */
		for (String s_unique : this.Unique) {
			/* it is possible that a unique constraint exists of multiple columns, separated by semicolon */
			if (s_unique.contains(";")) {
				String[] a_uniques = s_unique.split(";");
				
				/* iterate each unique key field/column */
				for (int i = 0; i < a_uniques.length; i++) {
					/* compare field/column value of current record and stored image record as this record was retrieved at least once */
					if ( !this.getColumnValue(a_uniques[i]).equals(this.getClass().cast(this.o_recordImage).getColumnValue(a_uniques[i])) ) {
																net.forestany.forestj.lib.Global.ilogFiner("unique constraint values changed: '" + s_unique + "'");
						
						/* add unique key to list */
						a_checkUniques.add(s_unique);
						/* set flag */
						b_uniqueChanged = true;
						/* do not break because we want to check all unique keys */
					}
				}
			} else {
				/* compare field/column value of current record and stored image record as this record was retrieved at least once */
				if ( !this.getColumnValue(s_unique).equals(this.getClass().cast(this.o_recordImage).getColumnValue(s_unique)) ) {
															net.forestany.forestj.lib.Global.ilogFiner("unique constraint value changed: '" + s_unique + "'");
					
					/* add unique key to list */
					a_checkUniques.add(s_unique);
					/* set flag */
					b_uniqueChanged = true;
					/* do not break because we want to check all unique keys */
				}
			}
		}
		
		/* check unique keys if at least one has changed values */
		if (b_uniqueChanged) {
													net.forestany.forestj.lib.Global.ilogFiner("check unique constraints where value(s) has been changed");
			
			for (String s_unique : a_checkUniques) {
														net.forestany.forestj.lib.Global.ilogFinest("check unique constraint: '" + s_unique + "'");
				
														net.forestany.forestj.lib.Global.ilogFinest("create a backup of current filter list");
				
				/* create a backup of current filter list */
				java.util.List<Filter> a_backupFilters = this.Filters;
				/* clear current filter list */
				this.Filters.clear();
				
				/* it is possible that a unique constraint exists of multiple columns, separated by semicolon */
				if (s_unique.contains(";")) {
					String[] a_uniques = s_unique.split(";");
					
					/* iterate each unique key field/column */
					for (int i = 0; i < a_uniques.length; i++) {
						/* add unique field/column with it's value to filter list */
						this.Filters.add( new Filter(a_uniques[i], this.getColumnValue(a_uniques[i]), "=", "AND") );
					}
				} else {
					/* add unique field/column with it's value to filter list */
					this.Filters.add( new Filter(s_unique, this.getColumnValue(s_unique), "=", "AND") );
				}
				
														net.forestany.forestj.lib.Global.ilogFinest("get amount of records with current unique key and it's values");
				
				/* get amount of records with current unique key and it's values */
				int i_return = this.getCount();
				
														net.forestany.forestj.lib.Global.ilogFinest("amount: '" + i_return + "'");
				
														net.forestany.forestj.lib.Global.ilogFinest("restore filter list with backup");
				
				/* restore filter list with backup */
				this.Filters = a_backupFilters;
				
				/* if amount of records is greater than zero, we cannot insert that record */
				if (i_return > 0) {
															net.forestany.forestj.lib.Global.ilogFinest("Unique key violation occurred, create unique key and it's values to throw an exception");
					
					String s_uniqueValues = "";
					
					/* it is possible that a unique constraint exists of multiple columns, separated by semicolon */
					if (s_unique.contains(";")) {
						/* split unique key */
						String[] a_uniques = s_unique.split(";");
						
						/* iterate each unique field/column */
						for (int i = 0; i < a_uniques.length; i++) {
							/* gather all unique field/column values */
							s_uniqueValues = this.getColumnValue(a_uniques[i]).toString() + ", ";
						}
						
						/* remove last ', ' separator */
						if (s_uniqueValues.length() > 1) {
							s_uniqueValues = s_uniqueValues.substring(0, (s_uniqueValues.length() - 2));
						}
					} else {
						/* add unique field/column value */
						s_uniqueValues = this.getColumnValue(s_unique).toString();
					}
					
					/* create an exception that we cannot update the record, because of unique key violation */
					throw new IllegalStateException("Unique key violation - unique constraint invalid for [" + s_unique + "](" + s_uniqueValues + ") in table [" + this.Table + "]; unique key already exists");
				}
			}
		}
			
												net.forestany.forestj.lib.Global.ilogFiner("create update query");
		
		/* create update query */
		Query<Update> o_queryUpdate = new Query<Update>(net.forestany.forestj.lib.Global.get().BaseGateway, SqlType.UPDATE, this.Table);
		
												net.forestany.forestj.lib.Global.ilogFiner("created update query");
		
		/* read out column fields to get values for update query */
		for (int i = 0; i < this.getClass().getDeclaredFields().length; i++) {
			/* get field */
			java.lang.reflect.Field o_field =  this.getClass().getDeclaredFields()[i];
						
			/* check if field starts with 'Column', but is not equal to 'Columns' */
			if ( (o_field.getName().startsWith("Column")) && (o_field.getName().compareTo("Columns") != 0) ) {
				/* get field name without 'Column' prefix */
				String s_column = o_field.getName().substring(6);
				
														net.forestany.forestj.lib.Global.ilogFinest("check if field/column '" + s_column + "' is not part of primary key AND p_b_withUnique = '" + p_b_withUnique + "' OR not part of a unique key");
				
				/* check if field/column is not part of primary key, because these fields should not be touched within an update normally */
				/* check also for field/column not part if a unique or we explicitly allow these fields/columns with p_b_withUnique parameter */
				if ( (!this.Primary.contains(s_column)) && ( (p_b_withUnique) || (!this.Unique.contains(s_column)) ) ) {
															net.forestany.forestj.lib.Global.ilogFinest("add field/column '" + s_column + "' to column value pair list of update query");
					
					/* add field/column to column value pair list of update query */
					o_queryUpdate.getQuery().a_columnValues.add( new ColumnValue(new Column(o_queryUpdate, s_column), this.getColumnValue(s_column)) );
					
															net.forestany.forestj.lib.Global.ilogFinest("added field/column to column value pair list of update query");
				}
			}
		}
		
		/* flag if where clause has been started */
		boolean b_initWhere = false;
		
												net.forestany.forestj.lib.Global.ilogFiner("take primary key fields for the update filter");
		
		/* take primary key fields for the update filter */
		for (String s_primary : this.Primary) {
													net.forestany.forestj.lib.Global.ilogFinest("create where clause object with primary key field '" + s_primary + "'");
			
			Where o_where = new Where(o_queryUpdate, new Column(o_queryUpdate, s_primary), this.getColumnValue(s_primary), "=");
			
													net.forestany.forestj.lib.Global.ilogFinest("created where clause object");
			
			if (b_initWhere) { /* set filter operator if where clause has been started */
				/* add 'AND' filter operator */
				o_where.setFilterOperator("AND");
			}
			
			/* at least here where clause has been started */
			b_initWhere = true;
			
													net.forestany.forestj.lib.Global.ilogFinest("add where clause object to query update");
			
			/* add where clause to update query */
			o_queryUpdate.getQuery().a_where.add(o_where);
			
													net.forestany.forestj.lib.Global.ilogFinest("added where clause object to query update");
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("execute query update");
		
		/* execute update query and get result */
		java.util.List<java.util.LinkedHashMap<String, Object>> a_rows = (this.OtherBaseSource != null) ? this.OtherBaseSource.OtherBaseSourceImplementation(o_queryUpdate) : net.forestany.forestj.lib.Global.get().Base.fetchQuery(o_queryUpdate, this.AutoTransaction);
		
												net.forestany.forestj.lib.Global.ilogFiner("executed query update");
		
		/* result must be exactly one row */
		if (a_rows.size() == 1) {
													net.forestany.forestj.lib.Global.ilogFiner("result must be exactly one row");
			
			if (a_rows.get(0).containsKey("AffectedRows")) { /* check if 'AffectedRows' is available */
				/* return 0 if 'AffectedRows' value is lower than one */
				if (Integer.parseInt(a_rows.get(0).get("AffectedRows").toString()) < 1) {
															net.forestany.forestj.lib.Global.ilogFiner("return 0 if 'AffectedRows' value is lower than one");
					
					return 0;
				} else {
															net.forestany.forestj.lib.Global.ilogFiner("return 'AffectedRows' value");
					
					/* return 'AffectedRows' value */
					return Integer.parseInt(a_rows.get(0).get("AffectedRows").toString());
				}
			} else { /* if we have not 'AffectedRows', return -1 as error */
														net.forestany.forestj.lib.Global.ilogFiner("if we have not 'AffectedRows', return -1 as error");
				
				return -1;
			}
		} else { /* result is not just one row, return -1 as error */
													net.forestany.forestj.lib.Global.ilogFiner("result is not just one row, return -1 as error");
			
			return -1;
		}
	}
	
	/**
	 * Deletes a record with current primary columns of inherited class
	 * 
	 * @return								primary 'AffectedRows' amount value, -1 as error
	 * @throws IllegalAccessException		cannot access column field, must be public
	 * @throws NoSuchFieldException			column field does not exist
	 * @throws java.sql.SQLException		exception accessing column type, column name or just column value of current result set record
	 * @throws java.text.ParseException 	could not parse column string value to java.util.Date
	 * @throws RuntimeException									base pool is not running
	 * @throws InterruptedException								base pool thread for fetching query is interrupted
	 */
	public int deleteRecord() throws IllegalAccessException, NoSuchFieldException, java.sql.SQLException, java.text.ParseException, RuntimeException, InterruptedException {
												net.forestany.forestj.lib.Global.ilogFiner("create query delete");
		
		/* create delete query */
		Query<Delete> o_queryDelete = new Query<Delete>(net.forestany.forestj.lib.Global.get().BaseGateway, SqlType.DELETE, this.Table);
		
												net.forestany.forestj.lib.Global.ilogFiner("created query delete");
		
		/* flag if where clause has been started */
		boolean b_initWhere = false;
		
												net.forestany.forestj.lib.Global.ilogFiner("take primary key fields for the delete filter");
		
		/* take primary key fields for the delete filter */
		for (String s_primary : this.Primary) {
													net.forestany.forestj.lib.Global.ilogFinest("create where clause object with primary key field '" + s_primary + "'");
			
			Where o_where = new Where(o_queryDelete, new Column(o_queryDelete, s_primary), this.getColumnValue(s_primary), "=");
			
													net.forestany.forestj.lib.Global.ilogFinest("created where clause object");
			
			if (b_initWhere) { /* set filter operator if where clause has been started */
				/* add 'AND' filter operator */
				o_where.setFilterOperator("AND");
			}
			
			/* at least here where clause has been started */
			b_initWhere = true;
			
													net.forestany.forestj.lib.Global.ilogFinest("add where clause object to query delete");
			
			/* add where clause to delete query */
			o_queryDelete.getQuery().a_where.add(o_where);
			
													net.forestany.forestj.lib.Global.ilogFinest("added where clause object to query delete");
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("execute query delete");
		
		/* execute delete query and get result */
		java.util.List<java.util.LinkedHashMap<String, Object>> a_rows = (this.OtherBaseSource != null) ? this.OtherBaseSource.OtherBaseSourceImplementation(o_queryDelete) : net.forestany.forestj.lib.Global.get().Base.fetchQuery(o_queryDelete, this.AutoTransaction);
		
												net.forestany.forestj.lib.Global.ilogFiner("executed query delete");
		
		/* result must be exactly one row */
		if (a_rows.size() == 1) {
													net.forestany.forestj.lib.Global.ilogFiner("check if 'AffectedRows' is available");
			
			if (a_rows.get(0).containsKey("AffectedRows")) { /* check if 'AffectedRows' is available */
				/* return 0 if 'AffectedRows' value is lower than one */
				if (Integer.parseInt(a_rows.get(0).get("AffectedRows").toString()) < 1) {
															net.forestany.forestj.lib.Global.ilogFiner("return 0 if 'AffectedRows' value is lower than one");
					
					return 0;
				} else {
															net.forestany.forestj.lib.Global.ilogFiner("return 'AffectedRows' value");
					
					/* return 'AffectedRows' value */
					return Integer.parseInt(a_rows.get(0).get("AffectedRows").toString());
				}
			} else { /* if we have not 'AffectedRows', return -1 as error */
														net.forestany.forestj.lib.Global.ilogFiner("if we have not 'AffectedRows', return -1 as error");
				
				return -1;
			}
		} else { /* result is not just one row, return -1 as error */
													net.forestany.forestj.lib.Global.ilogFiner("result is not just one row, return -1 as error");
			
			return -1;
		}
	}
	
	/**
	 * USE WITH CAUTION
	 * Truncates a whole table with information of inherited class
	 * 
	 * @return								primary 'AffectedRows' amount value, -1 as error
	 * @throws IllegalAccessException		exception accessing column type, column name or just column value of current result set record
	 * @throws RuntimeException									base pool is not running
	 * @throws InterruptedException								base pool thread for fetching query is interrupted
	 */
	public int truncateTable() throws IllegalAccessException, RuntimeException, InterruptedException {
												net.forestany.forestj.lib.Global.ilogFiner("create query truncate");
		
		/* create truncate query */
		Query<Truncate> o_queryTruncate = new Query<Truncate>(net.forestany.forestj.lib.Global.get().BaseGateway, SqlType.TRUNCATE, this.Table);
		
												net.forestany.forestj.lib.Global.ilogFiner("created query truncate");
		
												net.forestany.forestj.lib.Global.ilogFiner("execute query truncate");
		
		/* execute truncate query and get result */
		java.util.List<java.util.LinkedHashMap<String, Object>> a_rows = (this.OtherBaseSource != null) ? this.OtherBaseSource.OtherBaseSourceImplementation(o_queryTruncate) : net.forestany.forestj.lib.Global.get().Base.fetchQuery(o_queryTruncate, this.AutoTransaction);
		
												net.forestany.forestj.lib.Global.ilogFiner("executed query truncate");
		
		/* result must be exactly one row */
		if (a_rows.size() == 1) {
													net.forestany.forestj.lib.Global.ilogFiner("check if 'AffectedRows' is available");
			
			if (a_rows.get(0).containsKey("AffectedRows")) { /* check if 'AffectedRows' is available */
				/* return 0 if 'AffectedRows' value is lower than one */
				if (Integer.parseInt(a_rows.get(0).get("AffectedRows").toString()) < 1) {
															net.forestany.forestj.lib.Global.ilogFiner("return 0 if 'AffectedRows' value is lower than one");
					
					return 0;
				} else {
															net.forestany.forestj.lib.Global.ilogFiner("return 'AffectedRows' value");
					
					/* return 'AffectedRows' value */
					return Integer.parseInt(a_rows.get(0).get("AffectedRows").toString());
				}
			} else { /* if we have not 'AffectedRows', return -1 as error */
														net.forestany.forestj.lib.Global.ilogFiner("if we have not 'AffectedRows', return -1 as error");
				
				return -1;
			}
		} else { /* result is not just one row, return -1 as error */
													net.forestany.forestj.lib.Global.ilogFiner("result is not just one row, return -1 as error");
			
			return -1;
		}
	}
}
