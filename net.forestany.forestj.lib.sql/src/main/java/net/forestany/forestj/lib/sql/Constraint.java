package net.forestany.forestj.lib.sql;

import net.forestany.forestj.lib.sqlcore.SqlType;

/**
 * Constraint class with constraint, name, new name, alter operation and column list properties.
 */
public class Constraint extends QueryAbstract {
	
	/* Fields */
	
	/**
	 * constraint
	 */
	private String s_constraint = "";
	/**
	 * name
	 */
	public String s_name = "";
	/**
	 * new name
	 */
	public String s_newName = "";
	/**
	 * alter operation
	 */
	private String s_alterOperation = "ADD";
	/**
	 * list of columns as string
	 */
	public java.util.List<String> a_columns = new java.util.ArrayList<String>();
	
	/* Properties */
	
	/**
	 * set constraint value
	 * 
	 * @param p_s_value constraint value
	 * @throws IllegalArgumentException value is not in defined list
	 */
	public void setConstraint(String p_s_value) throws IllegalArgumentException {
		boolean b_accept = false;
		
		/* check if p_s_value is a valid sql index constraint */
		for (int i = 0; i < this.a_sqlIndexConstraints.length; i++) {
			if (this.a_sqlIndexConstraints[i] == p_s_value) {
				b_accept = true;
			}
		}
		
		if (b_accept) {
			this.s_constraint = p_s_value;
		} else {
			throw new IllegalArgumentException("Value[" + p_s_value + "] is not in defined list[" + String.join(", ", this.a_sqlIndexConstraints) + "]");
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
	 * get constraint value
	 * 
	 * @return String
	 */
	public String getConstraint() {
		return this.s_constraint;
	}
	
	/* Methods */
	
	/**
	 * Constraint constructor, need at least query object as parameter for table information
	 * default alter operation is 'ADD'
	 * 
	 * @param p_o_query							query object with database gateway and table information
	 * @throws IllegalArgumentException			invalid database gateway value from query parameter object
	 */
	public Constraint(Query<?> p_o_query) throws IllegalArgumentException {
		this(p_o_query, "", "", "", "ADD");
	}
	
	/**
	 * Constraint constructor, need at least query object as parameter for table information
	 * default alter operation is 'ADD'
	 * 
	 * @param p_o_query							query object with database gateway and table information
	 * @param p_s_constraint					define constraint
	 * @throws IllegalArgumentException			invalid database gateway value from query parameter object
	 */
	public Constraint(Query<?> p_o_query, String p_s_constraint) throws IllegalArgumentException {
		this(p_o_query, p_s_constraint, "", "", "ADD");
	}
	
	/**
	 * Constraint constructor, need at least query object as parameter for table information
	 * default alter operation is 'ADD'
	 * 
	 * @param p_o_query							query object with database gateway and table information
	 * @param p_s_constraint					define constraint
	 * @param p_s_name							define name for constraint
	 * @throws IllegalArgumentException			invalid database gateway value from query parameter object
	 */
	public Constraint(Query<?> p_o_query, String p_s_constraint, String p_s_name) throws IllegalArgumentException {
		this(p_o_query, p_s_constraint, p_s_name, "", "ADD");
	}
	
	/**
	 * Constraint constructor, need at least query object as parameter for table information
	 * default alter operation is 'ADD'
	 * 
	 * @param p_o_query							query object with database gateway and table information
	 * @param p_s_constraint					define constraint
	 * @param p_s_name							define name for constraint
	 * @param p_s_newName						define new name for constraint
	 * @throws IllegalArgumentException			invalid database gateway value from query parameter object
	 */
	public Constraint(Query<?> p_o_query, String p_s_constraint, String p_s_name, String p_s_newName) throws IllegalArgumentException {
		this(p_o_query, p_s_constraint, p_s_name, p_s_newName, "ADD");
	}
	
	/**
	 * Constraint constructor, need at least query object as parameter for table information
	 * default alter operation is 'ADD'
	 * 
	 * @param p_o_query							query object with database gateway and table information
	 * @param p_s_constraint					define constraint
	 * @param p_s_name							define name for constraint
	 * @param p_s_newName						define new name for constraint
	 * @param p_s_alterOperation				define alter operation for constraint
	 * @throws IllegalArgumentException			invalid database gateway value from query parameter object
	 */
	public Constraint(Query<?> p_o_query, String p_s_constraint, String p_s_name, String p_s_newName, String p_s_alterOperation) throws IllegalArgumentException {
		super(p_o_query);
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_constraint)) { 
			this.setConstraint(p_s_constraint);
		}
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_name)) {
			this.s_name = p_s_name;
		}
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_newName)) {
			this.s_newName = p_s_newName;
		}
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_alterOperation)) {
			this.setAlterOperation(p_s_alterOperation);
		}
	}
	
	/**
	 * create constraint part of a sql string query
	 */
	@Override
	public String toString() {
		String s_foo = "";
		
		try {
			boolean b_exc = false;
			
			/* check for implemented sql type */
			if ( (this.e_sqlType != SqlType.CREATE) && (this.e_sqlType != SqlType.ALTER) && (this.e_sqlType != SqlType.DROP) ) {
				throw new Exception("SqlType[" + this.e_sqlType + "] not implemented");
			}
			
			/* if alter operation is 'DROP' we need at least one column object */
			if ( (this.s_alterOperation != "DROP") && (this.a_columns.size() <= 0) ) {
				throw new Exception("Columns object list is empty");
			}
			
			switch (this.e_base) {
				case MARIADB:
				case NOSQLMDB:
					if (this.s_alterOperation == "ADD") { /* add new constraint */
						/* add constraint with name to query */
						s_foo = "ADD " + this.s_constraint + " `" + this.s_name + "` (";
						
						/* add all columns of constraint to query */
						for (int i = 0; i < this.a_columns.size(); i++) {
							if (i == this.a_columns.size() - 1) {
								s_foo += "`" + this.a_columns.get(i) + "`";
							} else {
								s_foo += "`" + this.a_columns.get(i) + "`" + ", ";
							}
						}
						
						/* close constraint */
						s_foo += ")";
					} else if (this.s_alterOperation == "CHANGE") { /* change existing constraint */
						/* new name must be set for changing existing constraint */
						if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_newName)) {
							throw new Exception("No new name for changing constraint");
						}
						
						/* add constraint with new name to query */
						s_foo = "ADD " + this.s_constraint + " `" + this.s_newName + "` (";
						
						/* add all columns of constraint to query */
						for (int i = 0; i < this.a_columns.size(); i++) {
							if (i == this.a_columns.size() - 1) {
								s_foo += "`" + this.a_columns.get(i) + "`";
							} else {
								s_foo += "`" + this.a_columns.get(i) + "`" + ", ";
							}
						}
						
						/* close constraint */
						s_foo += ")";
						/* drop old constraint */
						s_foo += ", DROP INDEX `" + this.s_name + "`";
					} else if (this.s_alterOperation == "DROP") { /* drop constraint */
						s_foo = "DROP INDEX `" + this.s_name + "`";
					}
				break;
				case SQLITE:
					/* change UNQIUE constraint to INDEX */
					if (this.s_constraint == "UNIQUE") {
						this.s_constraint += " INDEX";
					}
					
					if (this.s_alterOperation == "ADD") { /* add new constraint */
						/* add constraint with name to query */
						s_foo = "CREATE " + this.s_constraint + " `" + this.s_name + "` ON `" + this.s_table + "` (";
						
						/* add all columns of constraint to query */
						for (int i = 0; i < this.a_columns.size(); i++) {
							if (i == this.a_columns.size() - 1) {
								s_foo += "`" + this.a_columns.get(i) + "`";
							} else {
								s_foo += "`" + this.a_columns.get(i) + "`" + ", ";
							}
						}
						
						/* close constraint */
						s_foo += ")";
					} else if (this.s_alterOperation == "CHANGE") { /* change existing constraint */
						/* drop old constraint with query separator */
						s_foo = "DROP INDEX `" + this.s_name + "`" + this.s_querySeparator;
						
						/* new name must be set for changing existing constraint */
						if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_newName)) {
							throw new Exception("No new name for changing constraint");
						}
						
						/* add constraint with new name to query */
						s_foo += "CREATE " + this.s_constraint + " `" + this.s_newName + "` ON `" + this.s_table + "` (";
						
						/* add all columns of constraint to query */
						for (int i = 0; i < this.a_columns.size(); i++) {
							if (i == this.a_columns.size() - 1) {
								s_foo += "`" + this.a_columns.get(i) + "`";
							} else {
								s_foo += "`" + this.a_columns.get(i) + "`" + ", ";
							}
						}
						
						/* close constraint */
						s_foo += ")";
					} else if (this.s_alterOperation == "DROP") { /* drop constraint */
						s_foo = "DROP INDEX `" + this.s_name + "`";
					}
				break;
				case MSSQL:
					if (this.s_constraint == "UNIQUE") {
						this.s_constraint += " INDEX";
					}
					
					if (this.s_alterOperation == "ADD") { /* add new constraint */
						/* add constraint with name to query */
						s_foo = "CREATE " + this.s_constraint + " [" + this.s_name + "] ON [" + this.s_table + "] (";
						
						/* add all columns of constraint to query */
						for (int i = 0; i < this.a_columns.size(); i++) {
							if (i == this.a_columns.size() - 1) {
								s_foo += "[" + this.a_columns.get(i) + "]";
							} else {
								s_foo += "[" + this.a_columns.get(i) + "]" + ", ";
							}
						}
						
						/* close constraint */
						s_foo += ")";
					} else if (this.s_alterOperation == "CHANGE") { /* change existing constraint */
						/* drop old constraint with query separator */
						s_foo = "DROP INDEX [" + this.s_name + "] ON [" + this.s_table + "]" + this.s_querySeparator;
						
						/* new name must be set for changing existing constraint */
						if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_newName)) {
							throw new Exception("No new name for changing constraint");
						}
						
						/* add constraint with new name to query */
						s_foo += "CREATE " + this.s_constraint + " [" + this.s_newName + "] ON [" + this.s_table + "] (";
						
						/* add all columns of constraint to query */
						for (int i = 0; i < this.a_columns.size(); i++) {
							if (i == this.a_columns.size() - 1) {
								s_foo += "[" + this.a_columns.get(i) + "]";
							} else {
								s_foo += "[" + this.a_columns.get(i) + "]" + ", ";
							}
						}
						
						/* close constraint */
						s_foo += ")";
					} else if (this.s_alterOperation == "DROP") { /* drop constraint */
						s_foo = "DROP INDEX [" + this.s_name + "] ON [" + this.s_table + "]";
					}
				break;
				case PGSQL:
					if (this.s_alterOperation == "ADD") { /* add new constraint */
						/* add constraint with name to query as INDEX or as CONSTRAINT */
						if (this.s_constraint == "INDEX") {
							s_foo = "CREATE INDEX \"" + this.s_name + "\" ON \"" + this.s_table + "\" (";
						} else {
							s_foo = "ADD CONSTRAINT \"" + this.s_name + "\" " + this.s_constraint + " (";
						}
						
						/* add all columns of constraint to query */
						for (int i = 0; i < this.a_columns.size(); i++) {
							if (i == this.a_columns.size() - 1) {
								s_foo += "\"" + this.a_columns.get(i) + "\"";
							} else {
								s_foo += "\"" + this.a_columns.get(i) + "\"" + ", ";
							}
						}
						
						/* close constraint */
						s_foo += ")";
					} else if (this.s_alterOperation == "CHANGE") { /* change existing constraint */
						/* new name must be set for changing existing constraint */
						if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_newName)) {
							throw new Exception("No new name for changing constraint");
						}
						
						/* add constraint with new name to query as INDEX or as CONSTRAINT */
						if (this.s_constraint == "INDEX") {
							s_foo = "CREATE INDEX \"" + this.s_newName + "\" ON \"" + this.s_table + "\" (";
						} else {
							s_foo = "ADD CONSTRAINT \"" + this.s_newName + "\" " + this.s_constraint + " (";
						}
						
						/* add all columns of constraint to query */
						for (int i = 0; i < this.a_columns.size(); i++) {
							if (i == this.a_columns.size() - 1) {
								s_foo += "\"" + this.a_columns.get(i) + "\"";
							} else {
								s_foo += "\"" + this.a_columns.get(i) + "\"" + ", ";
							}
						}
						
						/* close constraint */
						s_foo += ")";

						/* drop old constraint with query separator or just additionally if it is not an index */
						if (this.s_constraint == "INDEX") {
							s_foo += this.s_querySeparator + "DROP INDEX \"" + this.s_name + "\"" + " ON \"" + this.s_table + "\"";
						} else {
							s_foo += ", DROP CONSTRAINT \"" + this.s_name + "\"";
						}
					} else if (this.s_alterOperation == "DROP") { /* drop constraint */
						if (this.s_constraint == "INDEX") { /* drop index constraint */
							s_foo = "DROP INDEX \"" + this.s_name + "\"";
						} else { /* drop constraint */
							s_foo = "DROP CONSTRAINT \"" + this.s_name + "\"";
						}
					}
				break;
				case ORACLE:
					if (this.s_alterOperation == "ADD") { /* add new constraint */
						/* add constraint with name to query as INDEX or as CONSTRAINT */
						if (this.s_constraint == "INDEX") {
							s_foo = "CREATE INDEX \"" + this.s_name + "\" ON \"" + this.s_table + "\" (";
						} else {
							s_foo = "ADD CONSTRAINT \"" + this.s_name + "\" " + this.s_constraint + " (";
						}
						
						/* add all columns of constraint to query */
						for (int i = 0; i < this.a_columns.size(); i++) {
							if (i == this.a_columns.size() - 1) {
								s_foo += "\"" + this.a_columns.get(i) + "\"";
							} else {
								s_foo += "\"" + this.a_columns.get(i) + "\"" + ", ";
							}
						}
						
						/* close constraint */
						s_foo += ")";
					} else if (this.s_alterOperation == "CHANGE") { /* change existing constraint */
						/* new name must be set for changing existing constraint */
						if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_newName)) {
							throw new Exception("No new name for changing constraint");
						}
						
						/* drop old constraint with query separator */
						if (this.s_constraint == "INDEX") {
							s_foo = "DROP INDEX \"" + this.s_name + "\"" + this.s_querySeparator;
						} else {
							s_foo = "DROP CONSTRAINT \"" + this.s_name + "\"" + this.s_querySeparator + "ALTER TABLE \"" + this.s_table + "\" ";
						}
						
						/* add constraint with new name to query as INDEX or as CONSTRAINT */
						if (this.s_constraint == "INDEX") {
							s_foo += "CREATE INDEX \"" + this.s_newName + "\" ON \"" + this.s_table + "\" (";
						} else {
							s_foo += "ADD CONSTRAINT \"" + this.s_newName + "\" " + this.s_constraint + " (";
						}
						
						/* add all columns of constraint to query */
						for (int i = 0; i < this.a_columns.size(); i++) {
							if (i == this.a_columns.size() - 1) {
								s_foo += "\"" + this.a_columns.get(i) + "\"";
							} else {
								s_foo += "\"" + this.a_columns.get(i) + "\"" + ", ";
							}
						}
						
						/* close constraint */
						s_foo += ")";
					} else if (this.s_alterOperation == "DROP") { /* drop constraint */
						if (this.s_constraint == "INDEX") { /* drop index constraint */
							s_foo = "DROP INDEX \"" + this.s_name + "\"";
						} else { /* drop constraint */
							s_foo += "DROP CONSTRAINT \"" + this.s_name + "\"";
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
			s_foo = " >>>>> Constraint class Exception: [" + o_exc.toString() + "] <<<<< ";
		}
		
		return s_foo;
	}
}
