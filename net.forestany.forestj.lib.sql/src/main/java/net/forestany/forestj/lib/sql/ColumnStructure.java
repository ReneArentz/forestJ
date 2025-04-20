package net.forestany.forestj.lib.sql;

import net.forestany.forestj.lib.sqlcore.BaseGateway;
import net.forestany.forestj.lib.sqlcore.SqlType;

/**
 * Column structure class with column, name, new name, constraints, column type and alter operations properties.
 */
public class ColumnStructure extends QueryAbstract {
	
	/* Fields */
	
	/**
	 * name
	 */
	public String s_name = "";
	/**
	 * new name
	 */
	public String s_newName = "";
	/**
	 * constraint list
	 */
	private java.util.List<String> a_constraintList = new java.util.ArrayList<String>();
	/**
	 * constraint default value
	 */
	private Object o_constraintDefaultValue = null;
	/**
	 * column type
	 */
	private String s_columnType = "";
	/**
	 * column type length
	 */
	public int i_columnTypeLength = 0;
	/**
	 * column type decimal length
	 */
	public int i_columnTypeDecimalLength = 0;
	/**
	 * alter operation
	 */
	private String s_alterOperation = "";
	
	/* Properties */
	
	/**
	 * add constraint
	 * 
	 * @param p_s_value constraint value
	 * @throws IllegalArgumentException constraint is not in defined list
	 */
	public void addConstraint(String p_s_value) throws IllegalArgumentException {
		boolean b_accept = false;
		
		/* check if p_s_value is a valid sql constraint */
		for (int i = 0; i < this.a_sqlConstraints.length; i++) {
			if (this.a_sqlConstraints[i] == p_s_value) {
				b_accept = true;
			}
		}
		
		if (b_accept) {
			this.a_constraintList.add(p_s_value);
		} else {
			throw new IllegalArgumentException("Constraint[" + p_s_value + "] is not in defined list[" + String.join(", ", this.a_sqlConstraints) + "]");
		}
	}
	
	/**
	 * get constraint list
	 * 
	 * @return java.util.List&lt;String&gt;
	 */
	public java.util.List<String> getConstraintList() {
		return this.a_constraintList;
	}
	
	/**
	 * set column type
	 * 
	 * @param p_s_value column type value
	 * @throws IllegalArgumentException value is not in defined list
	 */
	public void setColumnType(String p_s_value) throws IllegalArgumentException {
		boolean b_accept = false;
		
		/* check if p_s_value is a valid sql column type */
		for (int i = 0; i < this.a_sqlColumnTypes.length; i++) {
			if (this.a_sqlColumnTypes[i] == p_s_value) {
				b_accept = true;
			}
		}
		
		if (b_accept) {
			this.s_columnType = p_s_value;
		} else {
			throw new IllegalArgumentException("Value[" + p_s_value + "] is not in defined list[" + String.join(", ", this.a_sqlColumnTypes) + "]");
		}
	}
	
	/**
	 * set alter operation
	 * 
	 * @param p_s_value alter operation value
	 * @throws IllegalArgumentException value is not in defined list
	 */
	public void setAlterOperation(String p_s_value) throws IllegalArgumentException {
		boolean b_accept = false;
		
		/* check if p_s_value is a valid sql alter operation */
		for (int i = 0; i < this.a_alterOperations.length; i++) {
			if (this.a_alterOperations[i] == p_s_value) {
				b_accept = true;
			}
		}
		
		if (b_accept) {
			this.s_alterOperation = p_s_value;
		} else {
			throw new IllegalArgumentException("Value[" + p_s_value + "] is not in defined list[" + String.join(", ", this.a_alterOperations) + "]");
		}
	}
	
	/**
	 * get alter operation
	 * 
	 * @return String
	 */
	public String getAlterOperation() {
		return this.s_alterOperation;
	}
	
	/**
	 * set constraint default value, will be parsed with "parseValue"-method
	 * 
	 * @param p_o_value any object instance
	 * @throws IllegalArgumentException could not parse value
	 */
	public void setConstraintDefaultValue(Object p_o_value) throws IllegalArgumentException {
		this.o_constraintDefaultValue = this.parseValue(p_o_value);
	}
	
	/**
	 * get constraint default value
	 * 
	 * @return Object
	 */
	public Object getConstraintDefaultValue() {
		return this.o_constraintDefaultValue;
	}
	
	/* Methods */
	
	/**
	 * Column structure constructor, need at least query object as parameter for table information
	 * 
	 * @param p_o_query							query object with database gateway and table information
	 * @throws IllegalArgumentException			invalid database gateway value from query parameter object or could not parse java.util.Date to string
	 */
	public ColumnStructure(Query<?> p_o_query) throws IllegalArgumentException {
		this(p_o_query, "", "", null, "", 0, 0, "");
	}
	
	/**
	 * Column structure constructor, need at least query object as parameter for table information
	 * 
	 * @param p_o_query							query object with database gateway and table information
	 * @param p_s_name							define column's name
	 * @throws IllegalArgumentException			invalid database gateway value from query parameter object or could not parse java.util.Date to string
	 */
	public ColumnStructure(Query<?> p_o_query, String p_s_name) throws IllegalArgumentException {
		this(p_o_query, p_s_name, "", null, "", 0, 0, "");
	}
	
	/**
	 * Column structure constructor, need at least query object as parameter for table information
	 * 
	 * @param p_o_query							query object with database gateway and table information
	 * @param p_s_name							define column's name
	 * @param p_s_newName						define new name for column
	 * @throws IllegalArgumentException			invalid database gateway value from query parameter object or could not parse java.util.Date to string
	 */
	public ColumnStructure(Query<?> p_o_query, String p_s_name, String p_s_newName) throws IllegalArgumentException {
		this(p_o_query, p_s_name, p_s_newName, null, "", 0, 0, "");
	}
	
	/**
	 * Column structure constructor, need at least query object as parameter for table information
	 * 
	 * @param p_o_query							query object with database gateway and table information
	 * @param p_s_name							define column's name
	 * @param p_s_newName						define new name for column
	 * @param p_o_constraintDefaultValue		define constraint default value for column
	 * @throws IllegalArgumentException			invalid database gateway value from query parameter object or could not parse java.util.Date to string
	 */
	public ColumnStructure(Query<?> p_o_query, String p_s_name, String p_s_newName, Object p_o_constraintDefaultValue) throws IllegalArgumentException {
		this(p_o_query, p_s_name, p_s_newName, p_o_constraintDefaultValue, "", 0, 0, "");
	}
	
	/**
	 * Column structure constructor, need at least query object as parameter for table information
	 * 
	 * @param p_o_query							query object with database gateway and table information
	 * @param p_s_name							define column's name
	 * @param p_s_newName						define new name for column
	 * @param p_o_constraintDefaultValue		define constraint default value for column
	 * @param p_s_columnType					define column type
	 * @throws IllegalArgumentException			invalid database gateway value from query parameter object or could not parse java.util.Date to string
	 */
	public ColumnStructure(Query<?> p_o_query, String p_s_name, String p_s_newName, Object p_o_constraintDefaultValue, String p_s_columnType) throws IllegalArgumentException {
		this(p_o_query, p_s_name, p_s_newName, p_o_constraintDefaultValue, p_s_columnType, 0, 0, "");
	}
	
	/**
	 * Column structure constructor, need at least query object as parameter for table information
	 * 
	 * @param p_o_query							query object with database gateway and table information
	 * @param p_s_name							define column's name
	 * @param p_s_newName						define new name for column
	 * @param p_o_constraintDefaultValue		define constraint default value for column
	 * @param p_s_columnType					define column type
	 * @param p_i_columnTypeLength				define column type length for column
	 * @throws IllegalArgumentException			invalid database gateway value from query parameter object or could not parse java.util.Date to string
	 */
	public ColumnStructure(Query<?> p_o_query, String p_s_name, String p_s_newName, Object p_o_constraintDefaultValue, String p_s_columnType, Integer p_i_columnTypeLength) throws IllegalArgumentException {
		this(p_o_query, p_s_name, p_s_newName, p_o_constraintDefaultValue, p_s_columnType, p_i_columnTypeLength, 0, "");
	}
	
	/**
	 * Column structure constructor, need at least query object as parameter for table information
	 * 
	 * @param p_o_query							query object with database gateway and table information
	 * @param p_s_name							define column's name
	 * @param p_s_newName						define new name for column
	 * @param p_o_constraintDefaultValue		define constraint default value for column
	 * @param p_s_columnType					define column type
	 * @param p_i_columnTypeLength				define column type length for column
	 * @param p_i_columnTypeDecimalLength		define column type decimal length for column
	 * @throws IllegalArgumentException			invalid database gateway value from query parameter object or could not parse java.util.Date to string
	 */
	public ColumnStructure(Query<?> p_o_query, String p_s_name, String p_s_newName, Object p_o_constraintDefaultValue, String p_s_columnType, Integer p_i_columnTypeLength, Integer p_i_columnTypeDecimalLength) throws IllegalArgumentException {
		this(p_o_query, p_s_name, p_s_newName, p_o_constraintDefaultValue, p_s_columnType, p_i_columnTypeLength, p_i_columnTypeDecimalLength, "");
	}
	
	/**
	 * Column structure constructor, need at least query object as parameter for table information
	 * 
	 * @param p_o_query							query object with database gateway and table information
	 * @param p_s_name							define column's name
	 * @param p_s_newName						define new name for column
	 * @param p_o_constraintDefaultValue		define constraint default value for column
	 * @param p_s_columnType					define column type
	 * @param p_i_columnTypeLength				define column type length for column
	 * @param p_i_columnTypeDecimalLength		define column type decimal length for column
	 * @param p_s_alterOperation				define alter operation for column
	 * @throws IllegalArgumentException			invalid database gateway value from query parameter object or could not parse java.util.Date to string
	 */
	public ColumnStructure(Query<?> p_o_query, String p_s_name, String p_s_newName, Object p_o_constraintDefaultValue, String p_s_columnType, Integer p_i_columnTypeLength, Integer p_i_columnTypeDecimalLength, String p_s_alterOperation) throws IllegalArgumentException {
		super(p_o_query);
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_name)) {
			this.s_name = p_s_name;
		}
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_newName)) {
			this.s_newName = p_s_newName;
		}
		
		if (p_o_constraintDefaultValue != null) {
			this.setConstraintDefaultValue(p_o_constraintDefaultValue);
		}
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_columnType)) {
			this.setColumnType(p_s_columnType);
		}
		
		if (p_i_columnTypeLength > 0) {
			this.i_columnTypeLength = p_i_columnTypeLength;
		}
			
		if (p_i_columnTypeDecimalLength > 0) {
			this.i_columnTypeDecimalLength = p_i_columnTypeDecimalLength;
		}
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_alterOperation)) {
			this.setAlterOperation(p_s_alterOperation);
		}
	}
	
	/**
	 * create column structure part of a sql string query
	 */
	@Override
	public String toString() {
		String s_foo = "";
		
		try {
			/* check for implemented sql type */
			if ( (this.e_sqlType != SqlType.CREATE) && (this.e_sqlType != SqlType.ALTER) && (this.e_sqlType != SqlType.DROP) ) {
				throw new Exception("SqlType[" + this.e_sqlType + "] not implemented");
			}
			
			boolean b_exc = false;
			
			switch (this.e_base) {
				case MARIADB:
				case NOSQLMDB:
					if (this.s_alterOperation == "DROP") { /* add 'DROP' columns structure to query */
						s_foo += "DROP `" + this.s_name + "`";
					} else {
						if (this.s_alterOperation == "ADD") {
							/* new name is also name with 'ADD' columns structure */
							this.s_newName = this.s_name;
							
							if (this.e_sqlType == SqlType.ALTER) { /* add 'ADD' columns structure to query */
								s_foo += "ADD ";
							}
						} else if (this.s_alterOperation == "CHANGE") { /* add 'CHANGE' columns structure to query with old column name */
							s_foo += "CHANGE `" + this.s_name + "` ";
						}
					
						/* add column name or new column name with 'CHANGE' alter operation */
						s_foo += "`" + this.s_newName + "`";
						
						/* add column type */
						if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_columnType)) {
							throw new Exception("ColumnType not set for sql column");
						}
						
						s_foo += " " + this.s_columnType;
						
						/* add column type length */
						if (this.i_columnTypeLength > 0) {
							s_foo += "(" + this.i_columnTypeLength;
							
							/* additionally add column type decimal length */
							if (this.i_columnTypeDecimalLength > 0) {
								s_foo += "," + this.i_columnTypeDecimalLength;
							}
							
							s_foo += ")";
						}
						
						/* add constraints to current columns structure */
						if (this.a_constraintList.size() > 0) {
							for (String s_constraint : this.a_constraintList) {
								s_foo += " " + s_constraint;
								
								/* check if we want to add default constraint value */
								if (s_constraint == "DEFAULT") {
									if (this.o_constraintDefaultValue == null) {
										throw new Exception("No value for constraint DEFAULT");
									}
									
									/* allow CURRENT_TIMESTAMP as default value */
									if (this.o_constraintDefaultValue.toString().contentEquals("[forestjSQLValue]CURRENT_TIMESTAMP[/forestjSQLValue]")) {
										this.o_constraintDefaultValue = (Object)"CURRENT_TIMESTAMP";
									}
									
									/* allow NULL as default value */
									if (this.o_constraintDefaultValue.toString().contentEquals("[forestjSQLValue]NULL[/forestjSQLValue]")) {
										this.o_constraintDefaultValue = (Object)"NULL";
									}
																		
									/* add default constraint value */
									s_foo += " " + this.o_constraintDefaultValue.toString();
								}
							}
						}
					}
				break;
				case SQLITE:
					/* new name is also name */
					this.s_newName = this.s_name;
					
					/* add column name */
					s_foo += "`" + this.s_newName + "`";
					
					/* add column type */
					if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_columnType)) {
						throw new Exception("ColumnType not set for sql column");
					}
					
					s_foo += " " + this.s_columnType;
					
					/* add column type length */
					if (this.i_columnTypeLength > 0) {
						s_foo += "(" + this.i_columnTypeLength;
						
						/* additionally add column type decimal length */
						if (this.i_columnTypeDecimalLength > 0) {
							s_foo += "," + this.i_columnTypeDecimalLength;
						}
						
						s_foo += ")";
					}
					
					/* add constraints to current columns structure */
					if (this.a_constraintList.size() > 0) {
						for (String s_constraint : this.a_constraintList) {
							s_foo += " " + s_constraint;
							
							/* check if we want to add default constraint value */
							if (s_constraint == "DEFAULT") {
								if (this.o_constraintDefaultValue == null) {
									throw new Exception("No value for constraint DEFAULT");
								}
								
								/* allow CURRENT_TIMESTAMP as default value */
								if (this.o_constraintDefaultValue.toString().contentEquals("[forestjSQLValue]CURRENT_TIMESTAMP[/forestjSQLValue]")) {
									this.o_constraintDefaultValue = (Object)"CURRENT_TIMESTAMP";
								}
								
								/* allow NULL as default value */
								if (this.o_constraintDefaultValue.toString().contentEquals("[forestjSQLValue]NULL[/forestjSQLValue]")) {
									this.o_constraintDefaultValue = (Object)"NULL";
								}
								
								java.util.regex.Matcher o_matcher;
								java.util.regex.Pattern o_pattern = java.util.regex.Pattern.compile("\\[forestjSQLValue\\](.*?)\\[/forestjSQLValue\\]");
								o_matcher = o_pattern.matcher(this.o_constraintDefaultValue.toString());
								
								/* remove 'forestjSQLValue' tags for default constraint value */
								if (o_matcher.find()) {
									this.o_constraintDefaultValue = (Object)"'" + o_matcher.group(1) + "'";
								}
								
								/* add default constraint value */
								s_foo += " " + this.o_constraintDefaultValue.toString();
							}
						}
					}
				break;
				case MSSQL:
					if (this.s_alterOperation == "DROP") { /* add 'DROP' columns structure to query */
						s_foo += "COLUMN [" + this.s_name + "]";
					} else {
						if (this.s_alterOperation == "ADD") {
							/* new name is also name with 'ADD' columns structure */
							this.s_newName = this.s_name;
						} else if (this.s_alterOperation == "CHANGE") {
							s_foo += "COLUMN ";
							
							if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_newName)) {
								/* new name is also name if new name is empty */
								this.s_newName = this.s_name;
							}
						}
					
						/* add column name */
						s_foo += "[" + this.s_newName + "]";
						
						/* add column type */
						if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_columnType)) {
							throw new Exception("ColumnType not set for sql column");
						}
						
						s_foo += " " + this.s_columnType;
						
						/* add column type length */
						if (this.i_columnTypeLength > 0) {
							s_foo += "(" + this.i_columnTypeLength;
							
							/* additionally add column type decimal length */
							if (this.i_columnTypeDecimalLength > 0) {
								s_foo += "," + this.i_columnTypeDecimalLength;
							}
							
							s_foo += ")";
						}
						
						/* add constraints to current columns structure */
						if (this.a_constraintList.size() > 0) {
							for (String s_constraint : this.a_constraintList) {
								/* changing default on column is not supported */
								if ( (this.s_alterOperation == "CHANGE") && (s_constraint == "DEFAULT") ) {
									continue;
								}
								
								s_foo += " " + s_constraint;
								
								/* check if we want to add default constraint value */
								if (s_constraint == "DEFAULT") {
									if (this.o_constraintDefaultValue == null) {
										throw new Exception("No value for constraint DEFAULT");
									}
									
									/* allow CURRENT_TIMESTAMP as default value */
									if (this.o_constraintDefaultValue.toString().contentEquals("[forestjSQLValue]CURRENT_TIMESTAMP[/forestjSQLValue]")) {
										this.o_constraintDefaultValue = (Object)"CURRENT_TIMESTAMP";
									}
									
									/* allow NULL as default value */
									if (this.o_constraintDefaultValue.toString().contentEquals("[forestjSQLValue]NULL[/forestjSQLValue]")) {
										this.o_constraintDefaultValue = (Object)"NULL";
									}
									
									/* add default constraint value */
									s_foo += " " + this.o_constraintDefaultValue.toString();
								}
							}
						}
					}
				break;
				case PGSQL:
					if (this.s_alterOperation == "DROP") { /* add 'DROP' columns structure to query */
						s_foo += "DROP \"" + this.s_name + "\"";
					} else {
						if (this.s_alterOperation == "ADD") {
							if (this.e_sqlType == SqlType.ALTER) {
								/* add 'ADD' columns structure to query if ALTER query */
								s_foo += "ADD ";
							}
						} else if (this.s_alterOperation == "CHANGE") {
							/* disallow changing 'Id' column */
							if (this.s_name == "Id") {
								throw new Exception("Cannot change settings for sql column \"Id\"");
							}
							
							/* add 'ALER COLUMN' columns structure to query */
							s_foo += "ALTER COLUMN ";
						}
						
						String s_fooName = this.s_name;
						
						/* if alter operation is 'CHANGE' and new name is not empty, set column name as new name */
						if ( (this.s_alterOperation == "CHANGE") && (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_newName)) ) {
							s_fooName = this.s_newName;
						}
					
						/* add column name to query */
						s_foo += "\"" + s_fooName + "\"";
						
						/* add column type to query */
						if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_columnType)) {
							throw new Exception("ColumnType not set for sql column");
						}
						
						/* if alter operation is 'CHANGE' add 'TYPE' to query */
						if (this.s_alterOperation == "CHANGE") {
							s_foo += " TYPE";
						}
						
						/* add column type to query, special notation for column type 'bit' */
						if (this.s_columnType == "bit") {
							s_foo += " smallint DEFAULT 0 CHECK (\"" + s_fooName + "\" >= 0 AND \"" + s_fooName + "\" <= 1)";
						} else {
							s_foo += " " + this.s_columnType;
						}
						
						/* add column type length to query */
						if (this.i_columnTypeLength > 0) {
							s_foo += "(" + this.i_columnTypeLength;
							
							/* additionally add column type decimal length to query */
							if (this.i_columnTypeDecimalLength > 0) {
								s_foo += "," + this.i_columnTypeDecimalLength;
							}
							
							s_foo += ")";
						}
						
						boolean b_setNotNull = false;
						boolean b_setDefault = false;
						
						/* add constraints to current columns structure */
						if (this.a_constraintList.size() > 0) {
							for (String s_constraint : this.a_constraintList) {
								if (this.s_alterOperation == "CHANGE") { /* change a column */
									if (s_constraint == "NOT NULL") { /* change column with 'SET NOT NULL' constraint */
										s_foo += ", ALTER COLUMN \"" + s_fooName + "\" SET NOT NULL";
										
										b_setNotNull = true;
									} else if (s_constraint == "DEFAULT") { /* change column with 'SET DEFAULT' constraint */
										s_foo += ", ALTER COLUMN \"" + s_fooName + "\" SET DEFAULT ";
										
										if (this.o_constraintDefaultValue == null) {
											throw new Exception("No value for constraint DEFAULT");
										}
										
										/* allow CURRENT_TIMESTAMP as default value */
										if (this.o_constraintDefaultValue.toString().contentEquals("[forestjSQLValue]CURRENT_TIMESTAMP[/forestjSQLValue]")) {
											this.o_constraintDefaultValue = (Object)"CURRENT_TIMESTAMP";
										}
										
										/* allow NULL as default value */
										if (this.o_constraintDefaultValue.toString().contentEquals("[forestjSQLValue]NULL[/forestjSQLValue]")) {
											this.o_constraintDefaultValue = (Object)"NULL";
										}
										
										/* add constraint default value */ 
										s_foo += " " + this.o_constraintDefaultValue.toString();
										
										b_setDefault = true;
									}
								} else { /* create a column */
									if (s_constraint == "PRIMARY KEY") {
										/* if constraint is 'PRIMARY KEY' add column with serial notation */
										s_foo = "";
										
										if (this.s_alterOperation == "ADD") {
											if (this.e_sqlType == SqlType.ALTER) {
												s_foo += "ADD ";
											}
										}
										
										s_foo += "\"" + s_fooName + "\" serial " + s_constraint;
										break;
									} else {
										/* add constraint to query */
										s_foo += " " + s_constraint;
										
										/* check if we want to use constraint default value */
										if (s_constraint == "DEFAULT") {
											if (this.o_constraintDefaultValue == null) {
												throw new Exception("No value for constraint DEFAULT");
											}
											
											/* allow CURRENT_TIMESTAMP as default value */
											if (this.o_constraintDefaultValue.toString().contentEquals("[forestjSQLValue]CURRENT_TIMESTAMP[/forestjSQLValue]")) {
												this.o_constraintDefaultValue = (Object)"CURRENT_TIMESTAMP";
											}
											
											/* allow NULL as default value */
											if (this.o_constraintDefaultValue.toString().contentEquals("[forestjSQLValue]NULL[/forestjSQLValue]")) {
												this.o_constraintDefaultValue = (Object)"NULL";
											}
											
											/* add constraint default value */ 
											s_foo += " " + this.o_constraintDefaultValue.toString();
										}
									}
								}
							}
						}
						
						/* if alter operation is 'CHANGE' and column type is not 'bit' */
						if ( (this.s_alterOperation == "CHANGE") && (this.s_columnType != "bit") ) {
							if (!b_setNotNull) { /* erase NOT NULL from column */
								s_foo += ", ALTER COLUMN \"" + s_fooName + "\" DROP NOT NULL";
							}
							
							if (!b_setDefault) { /* erase DEFAULT from column */
								s_foo += ", ALTER COLUMN \"" + s_fooName + "\" DROP DEFAULT";
							}
						}
					}
				break;
				case ORACLE:
					if (this.s_alterOperation == "DROP") { /* add 'DROP' columns structure to query */
						s_foo += "\"" + this.s_name + "\"";
					} else {
						String s_fooName = this.s_name;
						
						/* if alter operation is 'CHANGE' and new name is not empty, set column name as new name */
						if ( (this.s_alterOperation == "CHANGE") && (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_newName)) ) {
							s_fooName = this.s_newName;
						}
					
						/* add column name to query */
						s_foo += "\"" + s_fooName + "\"";
						
						/* add column type to query */
						if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_columnType)) {
							throw new Exception("ColumnType not set for sql column");
						}
						
						s_foo += " " + this.s_columnType;
						
						/* add column type length to query */
						if (this.i_columnTypeLength > 0) {
							s_foo += "(" + this.i_columnTypeLength;
							
							/* additionally add column type decimal length to query */
							if (this.i_columnTypeDecimalLength > 0) {
								s_foo += "," + this.i_columnTypeDecimalLength;
							}
							
							s_foo += ")";
						}
						
						/* add constraints to current columns structure */
						if (this.a_constraintList.size() > 0) {
							for (String s_constraint : this.a_constraintList) {
								if (s_constraint == "PRIMARY KEY") { /* if constraint is 'PRIMARY KEY' write constraint with special notation */
									s_foo = "\"" + s_fooName + "\" NUMBER GENERATED by default on null as IDENTITY " + s_constraint;
									break;
								} else if (s_constraint == "DEFAULT") {
									/* add constraint 'DEFAULT' to query */
									s_foo += " " + s_constraint;
									
									if (this.o_constraintDefaultValue == null) {
										throw new Exception("No value for constraint DEFAULT");
									}
									
									if (this.o_constraintDefaultValue instanceof String) {
										/* remove 'forestjSQLValue' tags from constraint default value if column type is 'TIME' or 'TIMESTAMP', because we must add something manually */
										if ( (this.s_columnType == "INTERVAL DAY(0) TO SECOND(0)") || (this.s_columnType == "TIMESTAMP") ) {
											java.util.regex.Matcher o_matcher;
											java.util.regex.Pattern o_pattern = java.util.regex.Pattern.compile("\\[forestjSQLValue\\](.*?)\\[/forestjSQLValue\\]");
											o_matcher = o_pattern.matcher(this.o_constraintDefaultValue.toString());
											
											if (o_matcher.find()) {
												this.o_constraintDefaultValue = (Object)o_matcher.group(1);
											}
										}
										
										if (this.s_columnType == "INTERVAL DAY(0) TO SECOND(0)") { /* remove '+0 ' of constraint default value */
											if (this.o_constraintDefaultValue.toString().startsWith("+0 ")) {
												this.o_constraintDefaultValue = (Object)this.o_constraintDefaultValue.toString().substring(3);
											}
											
											s_foo += " [forestjSQLValue]0 " + this.o_constraintDefaultValue.toString() + "[/forestjSQLValue]";
										} else if (this.s_columnType == "TIMESTAMP") { /* add 'timestamp ' to constraint default value */
											if (this.o_constraintDefaultValue.toString().contentEquals("CURRENT_TIMESTAMP")) {
												s_foo += " " + this.o_constraintDefaultValue.toString();
											} else {
												if (net.forestany.forestj.lib.Helper.isDateTime(this.o_constraintDefaultValue.toString())) {
													s_foo += " timestamp [forestjSQLValue]" + this.o_constraintDefaultValue.toString() + "[/forestjSQLValue]";
												} else if (net.forestany.forestj.lib.Helper.isDate(this.o_constraintDefaultValue.toString())) {
													s_foo += " timestamp [forestjSQLValue]" + this.o_constraintDefaultValue.toString() + " 00:00:00[/forestjSQLValue]";
												} else {
													s_foo += " [forestjSQLValue]" + this.o_constraintDefaultValue.toString() + "[/forestjSQLValue]";
												}
											}
										} else {
											/* allow NULL as default value */
											if (this.o_constraintDefaultValue.toString().contentEquals("[forestjSQLValue]NULL[/forestjSQLValue]")) {
												this.o_constraintDefaultValue = (Object)"NULL";
											}
											
											s_foo += " " + this.o_constraintDefaultValue.toString();
										}
										
										if (this.a_constraintList.contains("NULL")) {
											/* add 'NULL' constraint to query */
											s_foo += " NULL";
										} else if (this.a_constraintList.contains("NOT NULL")) {
											/* add 'NOT NULL' constraint to query */
											s_foo += " NOT NULL";
										}
									} else {
										/* add constraint default value to query */
										s_foo += " " + this.o_constraintDefaultValue.toString();
									}
								} else {
									/* skip constraints 'NULL' or 'NOT NULL' if constraint 'DEFAULT' is in constraint list */
									if ( ( (s_constraint == "NULL") || (s_constraint == "NOT NULL") ) && (this.a_constraintList.contains("DEFAULT")) ) {
										continue;
									}
									
									/* add constraint to query */
									s_foo += " " + s_constraint;
								}
							}
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
			s_foo = " >>>>> Column structure class Exception: [" + o_exc.toString() + "] <<<<< ";
		}
			
		return s_foo;
	}
	
	/**
	 * Determine valid column type, type length and type decimal length, based on sql type parameter and database gateway information with allocation matrix
	 * 
	 * @param p_s_sqlType					general sql type, valid values:
	 * 										text [36], text [255], text, integer [small], integer [int], integer [big], datetime, time, double, decimal, bool
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object or invalid sql type
	 */
	public void columnTypeAllocation(String p_s_sqlType) throws IllegalArgumentException {
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
		
		/* check sql type parameter */
		 switch (p_s_sqlType) {
		 	case "text [36]":	
		 	case "text [255]":
			case "text":
			case "integer [small]":
			case "integer [int]":
			case "integer [big]":
			case "datetime":
			case "time":
			case "double":
			case "decimal":
			case "bool":
			break;
			default:
				b_exc = true;
			break;
		}
		 
		if (b_exc) {
			 throw new IllegalArgumentException("Invalid SqlType[" + p_s_sqlType + "]");
		}
		
		/* create allocation matrix with helping mappings */
		java.util.Map<String, Integer> a_mapping= new java.util.HashMap<String, Integer>();
		a_mapping.put(BaseGateway.MARIADB.toString(), 0);
		a_mapping.put(BaseGateway.SQLITE.toString(), 1);
		a_mapping.put(BaseGateway.MSSQL.toString(), 2);
		a_mapping.put(BaseGateway.PGSQL.toString(), 3);
		a_mapping.put(BaseGateway.ORACLE.toString(), 4);
		a_mapping.put(BaseGateway.NOSQLMDB.toString(), 5);
		
		a_mapping.put("text [36]", 0);
		a_mapping.put("text [255]", 1);
		a_mapping.put("text", 2);
		a_mapping.put("integer [small]", 3);
		a_mapping.put("integer [int]", 4);
		a_mapping.put("integer [big]", 5);
		a_mapping.put("datetime", 6);
		a_mapping.put("time", 7);
		a_mapping.put("double", 8);
		a_mapping.put("decimal", 9);
		a_mapping.put("bool", 10);
		
		a_mapping.put("columnType", 0);
		a_mapping.put("columnLength", 1);
		a_mapping.put("decimalLength", 2);
		
		String[][][] a_allocation = {
			/* MARIADB */ {
				{"VARCHAR", "36", "0"},	
				{"VARCHAR", "255", "0"},
				{"TEXT", "0", "0"},
				{"SMALLINT", "6", "0"},
				{"INT", "10", "0"},
				{"BIGINT", "20", "0"},
				{"TIMESTAMP", "0", "0"},
				{"TIME", "0", "0"},
				{"DOUBLE", "0", "0"},
				{"DECIMAL", "38", "9"},
				{"BIT", "1", "0"}
			},
			/* SQLITE */ {
				{"varchar", "36", "0"},	
				{"varchar", "255", "0"},
				{"text", "0", "0"},
				{"smallint", "0", "0"},
				{"integer", "0", "0"},
				{"bigint", "0", "0"},
				{"datetime", "0", "0"},
				{"time", "0", "0"},
				{"double", "0", "0"},
				{"decimal", "38", "9"},
				{"bit", "1", "0"}
			},
			/* MSSQL */ {
				{"nvarchar", "36", "0"},	
				{"nvarchar", "255", "0"},
				{"text", "0", "0"},
				{"smallint", "0", "0"},
				{"int", "0", "0"},
				{"bigint", "0", "0"},
				{"datetime", "0", "0"},
				{"time", "0", "0"},
				{"float", "0", "0"},
				{"decimal", "38", "9"},
				{"bit", "0", "0"}
			},
			/* PGSQL */ {
				{"varchar", "36", "0"},	
				{"varchar", "255", "0"},
				{"text", "0", "0"},
				{"smallint", "0", "0"},
				{"integer", "0", "0"},
				{"bigint", "0", "0"},
				{"timestamp", "0", "0"},
				{"time", "0", "0"},
				{"double precision", "0", "0"},
				{"decimal", "38", "9"},
				{"bit", "0", "0"}
			},
			/* ORACLE */ {
				{"VARCHAR2", "36", "0"},	
				{"VARCHAR2", "255", "0"},
				{"CLOB", "0", "0"},
				{"NUMBER", "5", "0"},
				{"NUMBER", "10", "0"},
				{"LONG", "0", "0"},
				{"TIMESTAMP", "0", "0"},
				{"INTERVAL DAY(0) TO SECOND(0)", "0", "0"},
				{"BINARY_DOUBLE", "0", "0"},
				{"NUMBER", "38", "9"},
				{"CHAR", "1", "0"}
			},
			/* NoSQLMDB */ {
				{"VARCHAR", "0", "0"},	
				{"VARCHAR", "0", "0"},
				{"TEXT", "0", "0"},
				{"SMALLINT", "0", "0"},
				{"INTEGER", "0", "0"},
				{"BIGINT", "0", "0"},
				{"TIMESTAMP", "0", "0"},
				{"TIME", "0", "0"},
				{"DOUBLE", "0", "0"},
				{"DECIMAL", "0", "0"},
				{"BOOL", "0", "0"}
			}
		};
		
		/* get column properties of allocation matrix */
		String s_columnType = a_allocation[a_mapping.get(this.e_base.toString())][a_mapping.get(p_s_sqlType)][a_mapping.get("columnType")];
		int i_columnLength = Integer.parseInt(a_allocation[a_mapping.get(this.e_base.toString())][a_mapping.get(p_s_sqlType)][a_mapping.get("columnLength")]);
		int i_columnDecimalLength = Integer.parseInt(a_allocation[a_mapping.get(this.e_base.toString())][a_mapping.get(p_s_sqlType)][a_mapping.get("decimalLength")]);
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_columnType)) {
			this.setColumnType(s_columnType);
		}
		
		if (i_columnLength > 0) {
			this.i_columnTypeLength = i_columnLength;
		}
		
		if (i_columnDecimalLength > 0) {
			this.i_columnTypeDecimalLength = i_columnDecimalLength;
		}
	}

	/**
	 * Assume column information from column structure parameter to current instance for sqlite
	 * 
	 * @param p_o_column					column structure parameter with column type, type length and type decimal length information
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object	
	 */
	public void assumeColumnTypeSqlite(ColumnStructure p_o_column) throws IllegalArgumentException {
		if (this.e_base == BaseGateway.SQLITE) {
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_o_column.s_columnType)) {
				this.setColumnType(p_o_column.s_columnType);
			}
			
			if (p_o_column.i_columnTypeLength > 0) {
				this.i_columnTypeLength = p_o_column.i_columnTypeLength;
			}
				
			if (p_o_column.i_columnTypeDecimalLength > 0) {
				this.i_columnTypeDecimalLength = p_o_column.i_columnTypeDecimalLength;
			}
		}
	}
}
