package net.forestany.forestj.lib.sql;

import net.forestany.forestj.lib.sqlcore.SqlType;

/**
 * SQL class to generate a alter sql query based on fields and properties, called by toString method
 */
public class Alter extends QueryAbstract {

	/* Fields */
	
	/**
	 * list of columns
	 */
	public java.util.List<ColumnStructure> a_columns = new java.util.ArrayList<ColumnStructure>();
	/**
	 * list of constraints
	 */
	public java.util.List<Constraint> a_constraints = new java.util.ArrayList<Constraint>();
	
	/**
	 * list of sqlite column definitions
	 */
	public java.util.List<java.util.Properties> a_sqliteColumnsDefinition = new java.util.ArrayList<java.util.Properties>();
	/**
	 * list of sqlite indexes
	 */
	public java.util.List<java.util.Properties> a_sqliteIndexes = new java.util.ArrayList<java.util.Properties>();
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * constructor will be called with Query object, because all necessary database gateway, sql type and table information are part of Query class
	 * 
	 * @param p_o_query						query object with table and sql type information
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object
	 */
	public Alter(Query<?> p_o_query) throws IllegalArgumentException {
		super(p_o_query);
	}
	
	/**
	 * create alter sql string query
	 */
	@Override
	public String toString() {
		String s_foo = "";
		
		try {
			boolean b_exc = false;
			
			switch (this.e_base) {
				case MARIADB:
				case NOSQLMDB:
					/* start alter query */
					s_foo = "ALTER TABLE " + "`" + this.s_table + "`" + " ";
					
					if ( (this.a_columns.size() <= 0) && (this.a_constraints.size() <= 0) ) {
						throw new Exception("Columns and Constraints object lists are empty");
					} else {
						if (this.a_columns.size() > 0) {
							/* list all alter columns within query */
							for (ColumnStructure o_column : this.a_columns) {
								s_foo += o_column.toString() + ", ";
							}
						} else if (this.a_constraints.size() > 0) {
							/* list all alter constraints within query */
							for (Constraint o_constraint : this.a_constraints) {
								s_foo += o_constraint.toString() + ", ";
							}
						}
						
						/* remove last ', ' separator */
						s_foo = s_foo.substring(0, s_foo.length() - 2);
					}
				break;
				case SQLITE:
					if (!( (this.a_columns.size() > 0) ^ (this.a_constraints.size() > 0) )) {
						throw new Exception("Columns and Constraints object lists are both empty or both set");
					} else {
						/* handle column changes for sqlite */
						if (this.a_columns.size() >= 1) {
							int i = 1;
							ColumnStructure o_changeColumn = null;
							java.util.List<String> a_deleteColumns = new java.util.ArrayList<String>();
							
							/* iterate each column */
							for (ColumnStructure o_column : this.a_columns) {
								/* start alter query */
								s_foo += "ALTER TABLE " + "`" + this.s_table + "`" + " ";
								
								/* change a column, only one possible for one alter query */
								if (o_column.getAlterOperation().contentEquals("CHANGE")) {
									if (this.a_columns.size() > 1) {
										throw new Exception("Columns object lists must contain only one item for CHANGE operation");
									}
									
									o_changeColumn = o_column;
									break;
								}
								
								/* drop a column, notice column which should be deleted for not creating it new with new temp table */
								if (o_column.getAlterOperation().contentEquals("DROP")) {
									a_deleteColumns.add(o_column.s_name);
								}
								
								if (o_column.getAlterOperation().contentEquals("ADD")) {
									s_foo += "ADD ";
								}
								
								if (this.a_columns.size() == i++) {
									s_foo += o_column.toString();
								} else {
									s_foo += o_column.toString() + this.s_querySeparator;
								}
							}
							
							/* if we want to change a column or dropping multiple columns */
							if ( (o_changeColumn != null) || (a_deleteColumns.size() > 0) ) {
								s_foo = "";
								
								/* get all columns and indices of current table */
								if (this.a_sqliteColumnsDefinition.size() < 1) {
									throw new Exception("No sqlite columns information loaded");
								}
								
								java.util.List<String> a_columnsNew = new java.util.ArrayList<String>();
								java.util.List<String> a_columnsOld = new java.util.ArrayList<String>();
								
								/* generate a random prefix for temp table name, not starting with a digit */
								String s_randomPrefix = null;
								
								do {
									s_randomPrefix = net.forestany.forestj.lib.Helper.generateRandomString(16) + "_";
								} while (Character.isDigit(s_randomPrefix.charAt(0)));
								
								/* CREATE TABLE random prefix + "table" (all columns with column new name) */
								Query<Create> o_queryNew = new Query<Create>(this.e_base, SqlType.CREATE, s_randomPrefix + this.s_table);
					
								for (java.util.Properties o_columnDefinition : this.a_sqliteColumnsDefinition) {
									ColumnStructure o_column = new ColumnStructure(o_queryNew);
									o_column.s_name = o_columnDefinition.getProperty("name").toString();
									o_column.columnTypeAllocation(o_columnDefinition.getProperty("columnType").toString());
									o_column.setAlterOperation("ADD");
									
									if (a_deleteColumns.contains(o_columnDefinition.get("name").toString())) {
										/* skip columns which should be deleted */
										continue;
									} else if ( (o_changeColumn != null) && (o_column.s_name.equals(o_changeColumn.s_name)) ) {
										/* handle change column */
										o_column.s_name = o_changeColumn.s_newName;
										o_column.assumeColumnTypeSqlite(o_changeColumn);
										a_columnsNew.add(o_changeColumn.s_newName);
										a_columnsOld.add(o_changeColumn.s_name);
									} else {
										/* assume all other columns */
										a_columnsNew.add(o_columnDefinition.get("name").toString());
										a_columnsOld.add(o_columnDefinition.get("name").toString());
									}
									
									/* add all stored constraint settings for column */
									if (o_columnDefinition.containsKey("constraints")) {
										String[] a_constraints = o_columnDefinition.getProperty("constraints").split(";");
										
										for (int j = 0; j < a_constraints.length; j++) {
											o_column.addConstraint(o_queryNew.constraintTypeAllocation(a_constraints[j]));
											
											if ( (a_constraints[j].compareTo("DEFAULT") == 0) && (o_columnDefinition.containsKey("constraintDefaultValue")) ) {
												o_column.setConstraintDefaultValue((Object)o_columnDefinition.getProperty("constraintDefaultValue"));
											}
										}
									}
									
									o_queryNew.getQuery().a_columns.add(o_column);
								}
								
								/* Create table does not return a value */
								s_foo += o_queryNew.toString() + this.s_querySeparator;
								
								/* INSERT INTO random prefix + "table" (all columns with column new name) SELECT (all columns with column old name) FROM "table" */
								Query<Insert> o_queryInsert = new Query<Insert>(this.e_base, SqlType.INSERT, s_randomPrefix + this.s_table);
								o_queryInsert.setQuery("INSERT INTO `" + s_randomPrefix + this.s_table + "` (`" + String.join("`,`", a_columnsNew) + "`) SELECT `" + String.join("`,`", a_columnsOld) + "` FROM `" + this.s_table + "`");
								s_foo += o_queryInsert.toString() + this.s_querySeparator;
								
								/* DROP "table" */
								Query<Drop> o_queryDrop = new Query<Drop>(this.e_base, SqlType.DROP, this.s_table);
								s_foo += o_queryDrop.toString() + this.s_querySeparator;
								
								/* ALTER TABLE random prefix + "table" RENAME TO "table" */
								Query<Alter> o_queryAlter = new Query<Alter>(this.e_base, SqlType.ALTER, s_randomPrefix + this.s_table);
								o_queryAlter.setQuery("ALTER TABLE `" + s_randomPrefix + this.s_table + "` RENAME TO `" + this.s_table + "`");
								s_foo += o_queryAlter.toString() + this.s_querySeparator;
								
								/* add old indices to table */
								if (this.a_sqliteIndexes.size() > 0) {
									for (java.util.Properties o_index : this.a_sqliteIndexes) {
										if (net.forestany.forestj.lib.Helper.isStringEmpty(o_index.get("columns").toString())) {
											throw new Exception("Sqlite[" + o_index.get("name").toString() + "] index has no columns");
										}
										
										String s_name = o_index.get("name").toString();
										boolean b_unique = o_index.get("unique").toString().equals("1");
										String[] a_indexColumns = o_index.get("columns").toString().split(";");
										
										boolean b_skip = false;
										
										/* check for columns to be deleted not going as indices to table */
										for (String s_indexColumn : a_indexColumns) {
											if (a_deleteColumns.contains(s_indexColumn)) {
												b_skip = true;
											}
										}
										
										/* skip deleted columns */
										if (b_skip) {
											continue;
										}
										
										/* if we want to change a column, we have to change it's name for indices as well */
										if ( (o_changeColumn != null) && (s_name.equals(o_changeColumn.s_name)) ) {
											s_name = s_name.replace(o_changeColumn.s_name, o_changeColumn.s_newName);
											
											for (int j = 0; j < a_indexColumns.length; j++) {
												if (a_indexColumns[j].equals(o_changeColumn.s_name)) {
													a_indexColumns[j] = o_changeColumn.s_newName;
												}
											}
										}
										
										/* create index for table */
										String s_constraintType = "INDEX";
										
										if (b_unique) {
											s_constraintType = "UNIQUE";
										}
										
										Query<Alter> o_queryAlterSqlite = new Query<Alter>(this.e_base, SqlType.ALTER, this.s_table);
										Constraint o_constraint = new Constraint(o_queryAlterSqlite, s_constraintType, o_index.get("name").toString(), "", "ADD");
										
										for (String s_indexColumn : a_indexColumns) {
											o_constraint.a_columns.add(s_indexColumn);
										}
										
										o_queryAlterSqlite.getQuery().a_constraints.add(o_constraint);
										
										s_foo += o_queryAlterSqlite.toString() + this.s_querySeparator;
									}
								}
							}
						} else if (this.a_constraints.size() == 1) {
							/* list all alter constraints within query */
							for (Constraint o_constraint : this.a_constraints) {
								s_foo += o_constraint.toString() + ", ";
							}
							
							/* remove last ', ' separator */
							s_foo = s_foo.substring(0, s_foo.length() - 2);
						} else {
							throw new Exception("Constraints object lists must contain only one item");
						}
					}
				break;
				case MSSQL:
					/* start alter query */
					s_foo = "ALTER TABLE " + "[" + this.s_table + "]" + " ";
					
					if ( (this.a_columns.size() <= 0) && (this.a_constraints.size() <= 0) ) {
						throw new Exception("Columns and Constraints object lists are empty");
					} else {
						/* list all alter columns within query */
						if (this.a_columns.size() > 0) {
							boolean b_once = false;
							int i = 1;
							
							/* iterate each column */
							for (ColumnStructure o_column : this.a_columns) {
								if (o_column.getAlterOperation().contentEquals("ADD")) {
									/* just add 'ADD ' once within alter query */
									if (!b_once) {
										s_foo += "ADD ";
										b_once = true;
									}
								} else if (o_column.getAlterOperation().contentEquals("CHANGE")) {
									s_foo = "";
									
									/* use mssql execute command for renaming a column, and set it as first query command */
									if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_column.s_newName)) {
										s_foo += "EXEC sp_rename \"[" + this.s_table + "].[" + o_column.s_name + "]\", \"" + o_column.s_newName + "\", \"COLUMN\"" + this.s_querySeparator;
									}
									
									/* renew start alter query */
									s_foo += "ALTER TABLE " + "[" + this.s_table + "]" + " ALTER ";
								} else if (o_column.getAlterOperation().contentEquals("DROP")) {
									/* just add 'DROP ' once within alter query */
									if (!b_once) {
										s_foo += "DROP ";
										b_once = true;
									}
								}
								
								/* add each column to alter sql query */
								if (this.a_columns.size() == i++) {
									s_foo += o_column.toString();
								} else {
									/* if we change a column, we use query separator, because of mssql execute command */
									if (o_column.getAlterOperation().contentEquals("CHANGE")) {
										s_foo += o_column.toString() + this.s_querySeparator;
									} else {
										s_foo += o_column.toString() + ", ";
									}
								}
							}
						} else if (this.a_constraints.size() > 0) { /* list all alter constraints within query */
							s_foo = "";
							
							for (Constraint o_constraint : this.a_constraints) {
								s_foo += o_constraint.toString() + this.s_querySeparator;
							}
							
							/* remove last ', ' separator */
							s_foo = s_foo.substring(0, s_foo.length() - this.s_querySeparator.length());
						}
					}
				break;
				case ORACLE:
					/* start alter query */
					s_foo = "ALTER TABLE " + "\"" + this.s_table + "\"" + " ";
					
					if ( (this.a_columns.size() <= 0) && (this.a_constraints.size() <= 0) ) {
						throw new Exception("Columns and Constraints object lists are empty");
					} else {
						if (this.a_columns.size() > 0) { /* list all alter columns within query */
							boolean b_closeAdd = false;
							boolean b_closeModify = false;
							boolean b_closeDrop = false;
							
							/* add 'ADD ' command bracket and notice that we need to close it */
							if (this.a_columns.get(0).getAlterOperation().contentEquals("ADD")) {
								s_foo += "ADD (";
								b_closeAdd = true;
							}
							
							/* add 'MODIFY ' command bracket and notice that we need to close it */
							if (this.a_columns.get(0).getAlterOperation().contentEquals("CHANGE")) {
								s_foo += "MODIFY (";
								b_closeModify = true;
								
								/* use additional alter query for renaming a column, and set it as first query command separated with query separator */
								if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.a_columns.get(0).s_newName)) {
									s_foo = "ALTER TABLE " + "\"" + this.s_table + "\"" + " RENAME COLUMN \"" + this.a_columns.get(0).s_name + "\" TO \"" + this.a_columns.get(0).s_newName + "\"" + this.s_querySeparator + s_foo;
								}
							}
							
							/* add 'DROP ' command bracket and notice that we need to close it */
							if (this.a_columns.get(0).getAlterOperation().contentEquals("DROP")) {
								s_foo += "DROP (";
								b_closeDrop = true;
							}
							
							/* add each column to alter query */
							for (ColumnStructure o_column : this.a_columns) {
								s_foo += o_column.toString() + ", ";
							}
							
							/* remove last ', ' separator */
							s_foo = s_foo.substring(0, s_foo.length() - 2);
							
							/* close command bracket */
							if ( (b_closeAdd) || (b_closeModify) || (b_closeDrop) ) {
								s_foo += ")";
							}
						} else if (this.a_constraints.size() > 0) { /* list all alter constraints within query */
							int i = 1;
							
							/* iterate each constraint object */
							for (Constraint o_constraint : this.a_constraints) {
								/* alter index constraint -> empty current alter query */
								if (o_constraint.getConstraint().contentEquals("INDEX")) {
									s_foo = "";
								}
								
								if (this.a_constraints.size() == i++) { /* add constraint to alter query, without ', ' separator */
									s_foo += o_constraint.toString();
								} else {
									/* handle alter index constraint as separate alter query */
									if (o_constraint.getConstraint().contentEquals("INDEX")) {
										s_foo += o_constraint.toString() + this.s_querySeparator;
									} else { /* add constraint to alter query */
										s_foo += o_constraint.toString() + ", ";
									}
								}
							}
						}
					}
				break;
				case PGSQL:
					/* start alter query */
					s_foo = "ALTER TABLE " + "\"" + this.s_table + "\"" + " ";
					
					if ( (this.a_columns.size() <= 0) && (this.a_constraints.size() <= 0) ) {
						throw new Exception("Columns and Constraints object lists are empty");
					} else {
						if (this.a_columns.size() > 0) { /* list all alter columns within query */
							/* use additional alter query for renaming a column, and set it as first query command separated with query separator */
							if (this.a_columns.get(0).getAlterOperation().contentEquals("CHANGE")) {
								if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.a_columns.get(0).s_newName)) {
									s_foo = "ALTER TABLE " + "\"" + this.s_table + "\"" + " RENAME COLUMN \"" + this.a_columns.get(0).s_name + "\" TO \"" + this.a_columns.get(0).s_newName + "\"" + this.s_querySeparator + s_foo;
								}
							}
							
							/* add each column to alter query */
							for (ColumnStructure o_column : this.a_columns) {
								s_foo += o_column.toString() + ", ";
							}
							
							/* remove last ', ' separator */
							s_foo = s_foo.substring(0, s_foo.length() - 2);
						} else if (this.a_constraints.size() > 0) { /* list all alter constraints within query */
							int i = 1;
							
							/* iterate each constraint object */
							for (Constraint o_constraint : this.a_constraints) {
								/* alter index constraint -> empty current alter query */
								if (o_constraint.getConstraint().contentEquals("INDEX")) {
									s_foo = "";
								}
								
								if (this.a_constraints.size() == i++) { /* add constraint to alter query, without ', ' separator */
									s_foo += o_constraint.toString();
								} else {
									/* handle alter index constraint as separate alter query */
									if (o_constraint.getConstraint().contentEquals("INDEX")) {
										s_foo += o_constraint.toString() + this.s_querySeparator;
									} else { /* add constraint to alter query */
										s_foo += o_constraint.toString() + ", ";
									}
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
			s_foo = " >>>>> Alter class Exception: [" + o_exc.toString() + "] <<<<< ";
		}
		
		return s_foo;
	}
}
