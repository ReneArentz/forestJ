package net.forestany.forestj.lib.sql.nosqlmdb;

import org.bson.Document;

import net.forestany.forestj.lib.sql.*;

/**
 * Transpose a net.forestany.forestj.lib.sql.Query object to bson command list for nosqlmdb use.
 */
public class BaseNoSQLMDBTranspose {
	
	/* Fields */
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * empty constructor
	 */
	public BaseNoSQLMDBTranspose() {
		
	}
	
	/**
	 * transpose method which will handle all the work by using net.forestany.forestj.lib.sql.Query object as parameter
	 * automatic identification of query type and peculiarities of queries
	 * 
	 * @param p_o_sqlQuery					net.forestany.forestj.lib.sql.Query object			
	 * @return								bson command list for nosqlmdb use
	 * @throws IllegalArgumentException		illegal constellation within sql query where we cannot transpose do bson command list for nosqlmdb, e.g. XOR filter operator
	 */
	public static java.util.List<Document> transpose(Query<?> p_o_sqlQuery) throws IllegalArgumentException {
		java.util.List<Document> a_return = null;
		
		switch(p_o_sqlQuery.getSqlType()) {
			case CREATE:
				a_return = transposeCreateQuery(p_o_sqlQuery);
				break;
			case ALTER:
				a_return = transposeAlterQuery(p_o_sqlQuery);
				break;
			case INSERT:
				a_return = transposeInsertQuery(p_o_sqlQuery);
				break;
			case UPDATE:
				a_return = transposeUpdateQuery(p_o_sqlQuery);
				break;
			case DELETE:
				a_return = transposeDeleteQuery(p_o_sqlQuery);
				break;
			case TRUNCATE:
				a_return = transposeTruncateQuery(p_o_sqlQuery);
				break;
			case DROP:
				a_return = transposeDropQuery(p_o_sqlQuery);
				break;
			case SELECT:
				a_return = transposeSelectQuery(p_o_sqlQuery);
				break;
		}
		
		return a_return;
	}
	
	private static java.util.List<Document> transposeCreateQuery(Query<?> p_o_sqlQuery) throws IllegalArgumentException {
		@SuppressWarnings("unchecked")
		Create o_createQuery = (Create)((Query<Create>)p_o_sqlQuery).getQuery();
		String s_collection = p_o_sqlQuery.getTable();
		java.util.List<Document> a_return = new java.util.ArrayList<Document>();
		
		/* create table as nosqlmdb collection */
		a_return.add(new Document("create", s_collection));
		
		/* list of primary keys or unique keys */
		java.util.List<Document> a_puks = new java.util.ArrayList<Document>();
		
		/* iterate columns structures of create query */ 
		for (ColumnStructure o_columnStructure : o_createQuery.getColumns()) {
			if (o_columnStructure.getAlterOperation() == "ADD") {
				/* column name */
				String s_name = o_columnStructure.s_name;
				
				/* add constraints to current columns structure */
				if (o_columnStructure.getConstraintList().size() > 0) {
					for (String s_constraint : o_columnStructure.getConstraintList()) {
						if ( (s_constraint.contentEquals("PRIMARY KEY")) || (s_constraint.contentEquals("UNIQUE")) ) {
							a_puks.add(
								new Document("key",
									new Document(s_name, 1)
								)
								.append("name", s_collection + "_" + s_name + "_puk")
								.append("unique", 1)
							);
						} 
					}
				}
			}
		}
		
		/* check if we need to create some indexes for primary key or unique columns */
		if (a_puks.size() > 0) {
			a_return.add(
				new Document()
					.append("createIndexes", s_collection)
					.append("indexes", a_puks
				)
			);
		}
		
		return a_return;
	}
	
	private static java.util.List<Document> transposeAlterQuery(Query<?> p_o_sqlQuery) throws IllegalArgumentException {
		@SuppressWarnings("unchecked")
		Alter o_alterQuery = (Alter)((Query<Alter>)p_o_sqlQuery).getQuery();
		String s_collection = p_o_sqlQuery.getTable();
		java.util.List<Document> a_return = new java.util.ArrayList<Document>();
		
		/* can only handle adding/changing/deleting columns or constraints but not both at the same time */
		if (!( (o_alterQuery.a_columns.size() > 0) ^ (o_alterQuery.a_constraints.size() > 0) )) {
			throw new IllegalArgumentException("Columns and Constraints object lists are both empty or both set");
		} else {
			if (o_alterQuery.a_columns.size() > 0) {
				/* list of primary keys or unique keys */
				java.util.List<Document> a_puks = new java.util.ArrayList<Document>();
				
				/* variables to gather column alter operations */
				Document o_addColumns = null;
				Document o_changeColumns = null;
				Document o_deleteColumns = null;
				
				/* handle all alter columns within query */
				for (ColumnStructure o_columnStructure : o_alterQuery.a_columns) {
					if (o_columnStructure.getAlterOperation().contentEquals("ADD")) { /* add a column */
						/* column name */
						String s_name = o_columnStructure.s_name;
						
						/* use new name */
						if (o_columnStructure.s_newName.length() > 0) {
							s_name = o_columnStructure.s_newName;
						}
						
						if (o_addColumns == null) {
							o_addColumns = new Document(s_name, null);
						} else {
							o_addColumns.append(s_name, null);
						}
						
						/* add constraints to current columns structure */
						if (o_columnStructure.getConstraintList().size() > 0) {
							for (String s_constraint : o_columnStructure.getConstraintList()) {
								if ( (s_constraint.contentEquals("PRIMARY KEY")) || (s_constraint.contentEquals("UNIQUE")) ) {
									a_puks.add(
										new Document("key",
											new Document(s_name, 1)
										)
										.append("name", s_collection + "_" + s_name + "_puk")
										.append("unique", 1)
									);
								} 
							}
						}
					} else if (o_columnStructure.getAlterOperation().contentEquals("CHANGE")) { /* change a column */
						/* change column name */
						if (o_columnStructure.s_newName.length() > 0) {
							if (o_changeColumns == null) {
								o_changeColumns = new Document(o_columnStructure.s_name, o_columnStructure.s_newName);
							} else {
								o_changeColumns.append(o_columnStructure.s_name, o_columnStructure.s_newName);
							}
						}
					} else if (o_columnStructure.getAlterOperation().contentEquals("DROP")) { /* drop a column */ 
						if (o_deleteColumns == null) {
							o_deleteColumns = new Document(o_columnStructure.s_name, 0);
						} else {
							o_deleteColumns.append(o_columnStructure.s_name, 0);
						}
					}
				}
				
				/* only create commands if we have new columns in query */
				if (o_addColumns != null) {
					a_return.add(
						new Document()
							.append("update", s_collection)
							.append("updates", java.util.Arrays.asList(
								new Document("q", new Document())
								.append("u", new Document(
									"$set",
										o_addColumns
								))
								.append("multi", true)
							))
					);
				
					/* check if we need to create some indexes for primary key or unique columns */
					if (a_puks.size() > 0) {
						a_return.add(
							new Document()
								.append("createIndexes", s_collection)
								.append("indexes", a_puks
							)
						);
					}
				}
				
				/* only create commands if we change any columns name in query */
				if (o_changeColumns != null) {
					a_return.add(
						new Document()
							.append("update", s_collection)
							.append("updates", java.util.Arrays.asList(
								new Document("q", new Document())
								.append("u", new Document(
									"$rename",
										o_changeColumns
								))
								.append("multi", true)
							))
					);
				}
				
				/* only create commands if we delete some columns in query */
				if (o_deleteColumns != null) {
					a_return.add(
						new Document()
							.append("update", s_collection)
							.append("updates", java.util.Arrays.asList(
								new Document("q", new Document())
								.append("u", new Document(
									"$unset",
										o_deleteColumns
								))
								.append("multi", true)
							))
					);
				}
			} else if (o_alterQuery.a_constraints.size() > 0) {
				/* list to gather constraint alter operations */
				java.util.List<String> a_deleteConstraintsBecauseOfChange = new java.util.ArrayList<String>();
				java.util.List<Document> a_constraints = new java.util.ArrayList<Document>();
				java.util.List<String> a_deleteConstraints = new java.util.ArrayList<String>();
				
				/* handle all alter constraints within query */
				for (Constraint o_constraint : o_alterQuery.a_constraints) {
					if (o_constraint.getAlterOperation().contentEquals("ADD")) { /* add new constraint */
						/* gather all columns for unique key constraint */
						Document o_columns = null;
						
						/* add all columns of constraint to document variable */
						for (String s_column : o_constraint.a_columns) {
							if (o_columns == null) {
								o_columns = new Document(s_column, 1);
							} else {
								o_columns.append(s_column, 1);
							}
						}
						
						/* add unique key for createIndexes command */
						a_constraints.add(
							new Document("key",
								o_columns
							)
							.append("name", s_collection + "_" + o_constraint.s_name + ( (o_constraint.getConstraint().contentEquals("UNIQUE")) ? "_puk" : "_ik" ))
							.append("unique", ( (o_constraint.getConstraint().contentEquals("UNIQUE")) ? 1 : 0 ))
						);
					} else if (o_constraint.getAlterOperation().contentEquals("CHANGE")) { /* change existing constraint */
						a_deleteConstraintsBecauseOfChange.add(s_collection + "_" + o_constraint.s_name + ( (o_constraint.getConstraint().contentEquals("UNIQUE")) ? "_puk" : "_ik" ));
						
						/* gather all columns for unique key constraint */
						Document o_columns = null;
						
						/* add all columns of constraint to document variable */
						for (String s_column : o_constraint.a_columns) {
							if (o_columns == null) {
								o_columns = new Document(s_column, 1);
							} else {
								o_columns.append(s_column, 1);
							}
						}
						
						/* add unique key for createIndexes command */
						a_constraints.add(
							new Document("key",
								o_columns
							)
							.append("name", s_collection + "_" + o_constraint.s_newName + ( (o_constraint.getConstraint().contentEquals("UNIQUE")) ? "_puk" : "_ik" ))
							.append("unique", ( (o_constraint.getConstraint().contentEquals("UNIQUE")) ? 1 : 0 ))
						);
					} else if (o_constraint.getAlterOperation().contentEquals("DROP")) { /* drop constraint */
						a_deleteConstraints.add(s_collection + "_" + o_constraint.s_name + ( (o_constraint.getConstraint().contentEquals("UNIQUE")) ? "_puk" : "_ik" ));
					}
				}
				
				/* only create commands if we delete some indexes in query because of changing a constraint */
				if (a_deleteConstraintsBecauseOfChange.size() > 0) {
					a_return.add(
						new Document()
							.append("dropIndexes", s_collection)
							.append("index", a_deleteConstraintsBecauseOfChange)
					);
				}
				
				/* check if we need to create some indexes for unique columns */
				if (a_constraints.size() > 0) {
					a_return.add(
						new Document()
							.append("createIndexes", s_collection)
							.append("indexes", a_constraints
						)
					);
				}
				
				/* only create commands if we delete some indexes in query */
				if (a_deleteConstraints.size() > 0) {
					a_return.add(
						new Document()
							.append("dropIndexes", s_collection)
							.append("index", a_deleteConstraints)
					);
				}
			}
		}
		
		return a_return;
	}
	
	private static java.util.List<Document> transposeInsertQuery(Query<?> p_o_sqlQuery) throws IllegalArgumentException {
		@SuppressWarnings("unchecked")
		Insert o_insertQuery = (Insert)((Query<Insert>)p_o_sqlQuery).getQuery();
		String s_collection = p_o_sqlQuery.getTable();
		java.util.List<Document> a_return = new java.util.ArrayList<Document>();
		
		/* add autoincrement command  */
		if (o_insertQuery.o_nosqlmdbColumnAutoIncrement != null) {
			a_return.add(new Document("autoincrement_collection", s_collection).append("autoincrement_column", o_insertQuery.o_nosqlmdbColumnAutoIncrement.s_column));
		}
		
		/* store values we retrieve from insert query */
		java.util.List<java.util.AbstractMap.SimpleEntry<String, Object>> a_values = new java.util.ArrayList<java.util.AbstractMap.SimpleEntry<String, Object>>();
		
		@SuppressWarnings("unused")
		String s_foo = net.forestany.forestj.lib.sql.Query.convertToPreparedStatementQuery(net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB, o_insertQuery.toString(), a_values, false);
		
		logValuesFromQuery(a_values);
		
		/* check if amount of values for query statement and column values of insert query are equal */
		if (a_values.size() != o_insertQuery.a_columnValues.size()) {
			throw new IllegalArgumentException("Amount of values does not match between query statement and query column values [" + a_values.size() + " != " + o_insertQuery.a_columnValues.size() + "]");
		}
		
		/* document variable for all column value pairs in insert query  */
		Document o_insertColumnValuePairs = null;
		
		/* handle all column value pairs of insert query */
		int i = 0;
		
		for (ColumnValue o_columnValue : o_insertQuery.a_columnValues) {
			if (o_insertColumnValuePairs == null) {
				o_insertColumnValuePairs = new Document("_id", net.forestany.forestj.lib.Helper.generateUUID().replace("-", "").substring(0, 24));
				
				if (o_insertQuery.o_nosqlmdbColumnAutoIncrement != null) {
					o_insertColumnValuePairs.append(o_insertQuery.o_nosqlmdbColumnAutoIncrement.s_column, "FORESTJ_REPLACE_AUTOINCREMENT_VALUE");
				}
			}
			
			java.util.AbstractMap.SimpleEntry<String, Object> o_entry = a_values.get(i++);
			
			o_insertColumnValuePairs.append( o_columnValue.o_column.s_column, transposeValueFromQuery(o_entry) );
		}
		
		if (o_insertColumnValuePairs != null) {
			a_return.add(
				new Document()
					.append("insert", s_collection)
					.append("documents", java.util.Arrays.asList(
							o_insertColumnValuePairs
						)
					)
					.append("ordered", true)
			);
		}
		
		return a_return;
	}
	
	private static java.util.List<Document> transposeUpdateQuery(Query<?> p_o_sqlQuery) throws IllegalArgumentException {
		@SuppressWarnings("unchecked")
		Update o_updateQuery = (Update)((Query<Update>)p_o_sqlQuery).getQuery();
		String s_collection = p_o_sqlQuery.getTable();
		java.util.List<Document> a_return = new java.util.ArrayList<Document>();
		
		/* store values we retrieve from update query */
		java.util.List<java.util.AbstractMap.SimpleEntry<String, Object>> a_values = new java.util.ArrayList<java.util.AbstractMap.SimpleEntry<String, Object>>();
		
		@SuppressWarnings("unused")
		String s_foo = net.forestany.forestj.lib.sql.Query.convertToPreparedStatementQuery(net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB, o_updateQuery.toString(false), a_values, false);
		
		logValuesFromQuery(a_values);
		
		/* check if amount of values for query statement and column values of update query are equal */
		if (a_values.size() != o_updateQuery.a_columnValues.size()) {
			throw new IllegalArgumentException("Amount of values does not match between query statement and query column values [" + a_values.size() + " != " + o_updateQuery.a_columnValues.size() + "]");
		}
		
		/* document variable for all column value pairs in insert query  */
		Document o_updateColumnValuePairs = null;
		
		/* handle all column value pairs of insert query */
		int i = 0;
		
		for (ColumnValue o_columnValue : o_updateQuery.a_columnValues) {
			java.util.AbstractMap.SimpleEntry<String, Object> o_entry = a_values.get(i++);
			
			if (o_updateColumnValuePairs == null) {
				o_updateColumnValuePairs = new Document( o_columnValue.o_column.s_column, transposeValueFromQuery(o_entry) );
			} else {
				o_updateColumnValuePairs.append( o_columnValue.o_column.s_column, transposeValueFromQuery(o_entry) );
			}
		}
		
		/* document variable for all where clauses in update query  */
		Document o_updateFilter = null;
		
		/* handle where clauses */
		if (o_updateQuery.a_where.size() > 0) {
			o_updateFilter = transposeWhereClause(o_updateQuery.a_where);
		}
		
		if ( (o_updateColumnValuePairs != null) && (o_updateFilter == null) ) { /* update query without filter */
			a_return.add(
				new Document()
					.append("update", s_collection)
					.append("updates", java.util.Arrays.asList(
						new Document("q", new Document()) /* use no filter */
						.append("u", new Document(
							"$set",
								o_updateColumnValuePairs
						))
						.append("multi", true)
					))
			);	
		} else if ( (o_updateColumnValuePairs != null) && (o_updateFilter != null) ) { /* update query with filter */
			a_return.add(
				new Document()
					.append("update", s_collection)
					.append("updates", java.util.Arrays.asList(
						new Document("q", 
							o_updateFilter
						)
						.append("u", new Document(
							"$set",
								o_updateColumnValuePairs
						))
						.append("multi", true)
					))
			);
		}
		
		return a_return;
	}
	
	private static java.util.List<Document> transposeDeleteQuery(Query<?> p_o_sqlQuery) throws IllegalArgumentException {
		@SuppressWarnings("unchecked")
		Delete o_deleteQuery = (Delete)((Query<Delete>)p_o_sqlQuery).getQuery();
		String s_collection = p_o_sqlQuery.getTable();
		java.util.List<Document> a_return = new java.util.ArrayList<Document>();
		
		/* document variable for all where clauses in delete query  */
		Document o_deleteFilter = null;
		
		/* handle where clauses */
		if (o_deleteQuery.a_where.size() > 0) {
			o_deleteFilter = transposeWhereClause(o_deleteQuery.a_where);
		}
		
		/* check if we have a valid filter - we do not accept empty filter for security reasons, otherwise you can use Truncate */
		if (o_deleteFilter != null) {
			a_return.add(
				new Document()
					.append("delete", s_collection)
					.append("deletes", java.util.Arrays.asList(
						new Document("q", o_deleteFilter)
						.append("limit", 0)
					))
					.append("ordered", true)
			);
		}
		
		return a_return;
	}
	
	private static java.util.List<Document> transposeTruncateQuery(Query<?> p_o_sqlQuery) throws IllegalArgumentException {
		java.util.List<Document> a_return = new java.util.ArrayList<Document>();
		
		/* delete all documents in collection */
		a_return.add(
			new Document()
				.append("delete", p_o_sqlQuery.getTable())
				.append("deletes", java.util.Arrays.asList(
					new Document("q", new Document())
					.append("limit", 0)
				))
				.append("ordered", true)
		);
		
		return a_return;
	}
	
	private static java.util.List<Document> transposeDropQuery(Query<?> p_o_sqlQuery) throws IllegalArgumentException {
		java.util.List<Document> a_return = new java.util.ArrayList<Document>();
		
		/* drop collection */
		a_return.add( new Document("drop", p_o_sqlQuery.getTable()) );
		
		return a_return;
	}
	
	private static java.util.List<Document> transposeSelectQuery(Query<?> p_o_sqlQuery) throws IllegalArgumentException {
		@SuppressWarnings("unchecked")
		Select o_selectQuery = (Select)((Query<Select>)p_o_sqlQuery).getQuery();
		
		if (o_selectQuery.a_columns.size() <= 0) {
			throw new IllegalArgumentException("Columns object list is empty for select query");
		}
		
		boolean b_hasColumnsWithAggregationsButOnlyCountAll = true;
		
		if (o_selectQuery.hasColumnsWithAggregations()) {
			for (Column o_column : o_selectQuery.a_columns) {
				if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(o_column.getSqlAggregation())) && (!o_column.getSqlAggregation().contentEquals("COUNT")) ) {
					b_hasColumnsWithAggregationsButOnlyCountAll = false;
				}
			}
		}
		
		if (o_selectQuery.b_distinct) {
			return transposeSelectQueryDistinct(p_o_sqlQuery);
		} else if ((o_selectQuery.a_joins.size() < 1) && (o_selectQuery.a_groupBy.size() < 1) && (b_hasColumnsWithAggregationsButOnlyCountAll)) {
			return transposeSelectQuerySimple(p_o_sqlQuery);
		} else {
			return transposeSelectQueryComplex(p_o_sqlQuery);
		}
	}

	private static java.util.List<Document> transposeSelectQueryDistinct(Query<?> p_o_sqlQuery) throws IllegalArgumentException {
		@SuppressWarnings("unchecked")
		Select o_selectQuery = (Select)((Query<Select>)p_o_sqlQuery).getQuery();
		String s_collection = p_o_sqlQuery.getTable();
		java.util.List<Document> a_return = new java.util.ArrayList<Document>();
		
		if ( (o_selectQuery.a_columns.size() != 1) || (o_selectQuery.a_columns.get(0).s_column.contentEquals("*")) ) {
			throw new IllegalArgumentException("Only one column is allowed for using distinct operator for nosqlmdb, and no '*' as column");
		}
		
		/* document variable for all where clauses in select query  */
		Document o_filter = null;
		
		/* handle where clauses */
		if (o_selectQuery.a_where.size() > 0) {
			o_filter = transposeWhereClause(o_selectQuery.a_where);
		}
		
		/* check if we have a valid filter */
		if (o_filter != null) {
			a_return.add(
				new Document()
					.append("distinct", s_collection)
					.append("key", o_selectQuery.a_columns.get(0).s_column)
					.append("query", o_filter)
			);
		} else {
			a_return.add(
				new Document()
					.append("distinct", s_collection)
					.append("key", o_selectQuery.a_columns.get(0).s_column)
			);
		}
		
		return a_return;
	}
	
	private static java.util.List<Document> transposeSelectQuerySimple(Query<?> p_o_sqlQuery) throws IllegalArgumentException {
		@SuppressWarnings("unchecked")
		Select o_selectQuery = (Select)((Query<Select>)p_o_sqlQuery).getQuery();
		String s_collection = p_o_sqlQuery.getTable();
		java.util.List<Document> a_return = new java.util.ArrayList<Document>();
		
		/* document variable for find command */
		Document o_select = new Document().append("find", s_collection);
		
		/* document variable for columns projection */
		Document o_columns = transposeColumns(o_selectQuery.a_columns);;
		
		/* add columns from select query to nosqlmdb command */
		if (o_columns != null) {
			o_select.append("projection", o_columns);
		}
		
		/* document variable for all where clauses in select query  */
		Document o_filter = null;
		
		/* handle where clauses */
		if (o_selectQuery.a_where.size() > 0) {
			o_filter = transposeWhereClause(o_selectQuery.a_where);
		}
		
		if (o_filter != null) {
			o_select.append("filter", o_filter);
		}
		
		/* document variable for order by list */
		Document o_orderBy = transposeOrderBy(o_selectQuery.o_orderBy);
		
		if (o_orderBy != null) {
			o_select.append("sort", o_orderBy);
		}
		
		/* add limit clauses of query */
		if ( (o_selectQuery.o_limit != null) &&  (o_selectQuery.o_limit.i_interval > 0) ) {
			o_select.append("skip", o_selectQuery.o_limit.i_start);
			o_select.append("limit", o_selectQuery.o_limit.i_interval);
		}
		
		a_return.add(o_select);
		
		return a_return;
	}
	
	private static java.util.List<Document> transposeSelectQueryComplex(Query<?> p_o_sqlQuery) throws IllegalArgumentException {
		@SuppressWarnings("unchecked")
		Select o_selectQuery = (Select)((Query<Select>)p_o_sqlQuery).getQuery();
		
		if ( (o_selectQuery.a_joins.size() > 0) && (o_selectQuery.a_groupBy.size() < 1) && (!o_selectQuery.hasColumnsWithAggregations()) ) { /* only a join to handle */
			return transposeSelectQueryComplexOnlyJoin(p_o_sqlQuery);
		} else if ( (o_selectQuery.a_joins.size() < 1) && (o_selectQuery.a_groupBy.size() > 0) && (o_selectQuery.hasColumnsWithAggregations()) ) { /* only group by to handle */
			return transposeSelectQueryComplexOnlyGroupBy(p_o_sqlQuery);
		} else { /* a join and group by to handle */
			return transposeSelectQueryComplexJoinAndGroupBy(p_o_sqlQuery);
		}
	}
	
	private static java.util.List<Document> transposeSelectQueryComplexOnlyJoin(Query<?> p_o_sqlQuery) throws IllegalArgumentException {
		@SuppressWarnings("unchecked")
		Select o_selectQuery = (Select)((Query<Select>)p_o_sqlQuery).getQuery();
		String s_collection = p_o_sqlQuery.getTable();
		java.util.List<Document> a_return = new java.util.ArrayList<Document>();
		
		if (o_selectQuery.a_joins.size() != 1) {
			throw new IllegalArgumentException("Nosqlmdb library can only handle one join, not '" + o_selectQuery.a_joins.size() + "'");
		}
		
		/* get join information */
		Join o_join = o_selectQuery.a_joins.get(0);
		
		if (o_join.a_relations.size() != 1) {
			throw new IllegalArgumentException("Nosqlmdb library can only handle one relation within a join, not '" + o_join.a_relations.size() + "'");
		}
		
		/* get relation information */
		Relation o_relation = o_join.a_relations.get(0);
		
		/* we will not handle operator, as we only can use equal, so we only use columnLeft and columnRight info */
		
		/* create */
		Document o_joinDocument = new Document("from", o_join.s_table)
			.append("localField", o_relation.o_columnLeft.s_column)
			.append("foreignField", o_relation.o_columnRight.s_column)
			.append("as", "join_" + o_join.s_table);
		
		/* document variable for all where clauses in select query  */
		Document o_filterDocument = null;
		
		/* handle where clauses */
		if (o_selectQuery.a_where.size() > 0) {
			/* iterate all where clauses */
			for (int i = 0; i < o_selectQuery.a_where.size(); i++) {
				/* if where clause table is equal to join table, set flag */
				if (o_selectQuery.a_where.get(i).o_column.s_table.contentEquals(o_join.s_table)) {
					o_selectQuery.a_where.get(i).b_isJoinTable = true;
				}
			}
			
			o_filterDocument = transposeWhereClause(o_selectQuery.a_where);
		}
		
		if (o_selectQuery.a_columns.size() > 0) {
			/* iterate all columns */
			for (int i = 0; i < o_selectQuery.a_columns.size(); i++) {
				/* if column table is equal to join table, set flag */
				if (o_selectQuery.a_columns.get(i).s_table.contentEquals(o_join.s_table)) {
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_selectQuery.a_columns.get(i).getSqlAggregation())) {
						throw new IllegalArgumentException("Invalid column with aggregation on a join table at the same time. Only aggregation on main table allowed");
					}
					
					o_selectQuery.a_columns.get(i).b_isJoinTable = true;
				}
			}
		}
		
		/* document variable for columns projection */
		Document o_columns = transposeColumns(o_selectQuery.a_columns);
		
		if (o_selectQuery.o_orderBy.getColumns().size() > 0) {
			/* iterate all columns of order by clause */
			for (int i = 0; i < o_selectQuery.o_orderBy.getColumns().size(); i++) {
				/* if column table is equal to join table, set flag */
				if (o_selectQuery.o_orderBy.getColumns().get(i).s_table.contentEquals(o_join.s_table)) {
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_selectQuery.a_columns.get(i).getSqlAggregation())) {
						throw new IllegalArgumentException("Invalid column with aggregation on a join table at the same time. Only aggregation on main table allowed");
					}
					
					o_selectQuery.o_orderBy.getColumns().get(i).b_isJoinTable = true;
				}
			}
		}
		
		/* document variable for order by list */
		Document o_orderBy = transposeOrderBy(o_selectQuery.o_orderBy);
		
		/* create list for aggregate pipeline */
		java.util.List<Document> a_pipeline = new java.util.ArrayList<Document>();
		
		/* add lookup */
		a_pipeline.add(new Document("$lookup", o_joinDocument));
		
		/* add unwind */
		a_pipeline.add(new Document("$unwind", "$join_" + o_join.s_table));
		
		/* add filter */
		if (o_filterDocument != null) {
			a_pipeline.add(new Document("$match", o_filterDocument));
		}
		
		/* add projection */
		if (o_columns != null) {
			a_pipeline.add(new Document("$project", o_columns));
		}
		
		/* add sort */
		if (o_orderBy != null) {
			a_pipeline.add(new Document("$sort", o_orderBy));
		}
		
		/* add limit clauses of query */
		if ( (o_selectQuery.o_limit != null) &&  (o_selectQuery.o_limit.i_interval > 0) ) {
			a_pipeline.add(new Document("$skip", o_selectQuery.o_limit.i_start));
			a_pipeline.add(new Document("$limit", o_selectQuery.o_limit.i_interval));
		}
		
		/* finish aggregate command */
		a_return.add(
			new Document()
				.append("aggregate", s_collection)
				.append("pipeline", a_pipeline)
				.append("cursor", new Document()) /* needed for aggregate */
		);
		
		return a_return;
	}
	
	private static java.util.List<Document> transposeSelectQueryComplexOnlyGroupBy(Query<?> p_o_sqlQuery) throws IllegalArgumentException {
		@SuppressWarnings("unchecked")
		Select o_selectQuery = (Select)((Query<Select>)p_o_sqlQuery).getQuery();
		String s_collection = p_o_sqlQuery.getTable();
		java.util.List<Document> a_return = new java.util.ArrayList<Document>();
		
		/* document variable for first sort, if we use aggregation like MIN or MAX */
		Document o_firstSort = null;
		
		java.util.List<Column> a_aggregations = new java.util.ArrayList<Column>();
		
		for (Column o_column : o_selectQuery.a_columns) {
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_column.getSqlAggregation())) {
				a_aggregations.add(o_column);
				
				if (o_column.getSqlAggregation().contentEquals("MAX")) {
					if (o_firstSort == null) {
						o_firstSort = new Document(o_column.s_column, -1); /* -1 -> DESC */
					} else {
						o_firstSort.append(o_column.s_column, -1); /* -1 -> DESC */
					}
				} else if (o_column.getSqlAggregation().contentEquals("MIN")) {
					if (o_firstSort == null) {
						o_firstSort = new Document(o_column.s_column, 1); /* 1 -> ASC */
					} else {
						o_firstSort.append(o_column.s_column, 1); /* 1 -> ASC */
					}
				}
			}
		}
		
		/* document variable for group by clause */
		Document o_groupBy = null;
		
		for (Column o_column : o_selectQuery.a_groupBy) {
			if (o_groupBy == null) {
				o_groupBy = new Document(o_column.s_column, "$" + o_column.s_column);
			} else {
				o_groupBy.append(o_column.s_column, "$" + o_column.s_column);
			}
		}
		
		/* document variable for columns projection */
		Document o_columns = transposeColumns(o_selectQuery.a_columns);
		
		java.util.List<Where> a_where = new java.util.ArrayList<Where>();
		
		/* handle having object */
		for (Where o_having : o_selectQuery.a_having) {
			a_where.add(o_having);
		}
		
		/* handle where clauses */
		for (Where o_where : o_selectQuery.a_where) {
			if (net.forestany.forestj.lib.Helper.isStringEmpty(o_where.getFilterOperator())) {
				o_where.setFilterOperator("AND");
			}
			
			a_where.add(o_where);
		}
		
		/* document variable for all having and where clauses in select query  */
		Document o_filterDocument = null;
		
		if (a_where.size() > 0) {
			o_filterDocument = transposeWhereClause(a_where);
		}
		
		/* document variable for order by list */
		Document o_orderBy = transposeOrderBy(o_selectQuery.o_orderBy);
		
		/* create group columns */
		Document o_groupColumns = new Document("_id", o_groupBy);
		
		/* add other aggregations to group columns */
		for (Column o_column : a_aggregations) {
			String s_column = o_column.s_column;
			
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_column.getSqlAggregation())) {
				s_column = o_column.getSqlAggregation() + "_" + s_column;
			} else if (o_column.b_isJoinTable) {
				s_column = "join_" + o_column.s_table + "." + s_column;
			}
			
			String s_aggregateOperator = null;
			
			if (o_column.getSqlAggregation().contentEquals("AVG")) {
				s_aggregateOperator = "$avg";
			} else if (o_column.getSqlAggregation().contentEquals("COUNT")) {
				s_aggregateOperator = "$addToSet";
			} else if (o_column.getSqlAggregation().contentEquals("MAX")) {
				s_aggregateOperator = "$max";
			} else if (o_column.getSqlAggregation().contentEquals("MIN")) {
				s_aggregateOperator = "$min";
			} else if (o_column.getSqlAggregation().contentEquals("SUM")) {
				s_aggregateOperator = "$sum";
			} else {
				throw new IllegalArgumentException("Invalid aggregation operator: '" + o_column.getSqlAggregation() + "'");
			}
			
			o_groupColumns.append(s_column, new Document(s_aggregateOperator, "$" + o_column.s_column));
		}
		
		/* put all fields into Record for $replaceRoot later */
		o_groupColumns.append("Record", new Document("$first", "$$ROOT"));
		
		/* aggregate columns for replace root aggregate */
		Document o_replaceRootAggregateColumns = null;
		
		for (Column o_column : a_aggregations) {
			String s_column = o_column.s_column;
			
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_column.getSqlAggregation())) {
				s_column = o_column.getSqlAggregation() + "_" + s_column;
			} else if (o_column.b_isJoinTable) {
				s_column = "join_" + o_column.s_table + "." + s_column;
			}
			
			if (o_replaceRootAggregateColumns == null) {
				o_replaceRootAggregateColumns = new Document(s_column, "$" + s_column);
			} else {
				o_replaceRootAggregateColumns.append(s_column, "$" + s_column);
			}
		}
		
		/* create replace root aggregate */
		Document o_replaceRoot = new Document("$replaceRoot", /* this is how we still hold all fields after $group, but we need to merge with aggregation fields of $group */
			new Document("newRoot",
				new Document("$mergeObjects", java.util.Arrays.asList( "$Record", o_replaceRootAggregateColumns ))
			)
		);
		
		/* create list for aggregate pipeline */
		java.util.List<Document> a_pipeline = new java.util.ArrayList<Document>();
		
		/* add first sort */
		if (o_firstSort != null) {
			a_pipeline.add(new Document("$sort", o_firstSort));
		}
		
		/* add group */
		a_pipeline.add(new Document("$group", o_groupColumns));
				
		/* add replace root aggregate */
		a_pipeline.add(o_replaceRoot);
		
		/* add projection */
		if (o_columns != null) {
			a_pipeline.add(new Document("$project", o_columns));
		}
		
		/* add filter */
		if (o_filterDocument != null) {
			a_pipeline.add(new Document("$match", o_filterDocument));
		}
		
		/* add sort */
		if (o_orderBy != null) {
			a_pipeline.add(new Document("$sort", o_orderBy));
		}
		
		/* add limit clauses of query */
		if ( (o_selectQuery.o_limit != null) &&  (o_selectQuery.o_limit.i_interval > 0) ) {
			a_pipeline.add(new Document("$skip", o_selectQuery.o_limit.i_start));
			a_pipeline.add(new Document("$limit", o_selectQuery.o_limit.i_interval));
		}
		
		/* finish aggregate command */
		a_return.add(
			new Document()
				.append("aggregate", s_collection)
				.append("pipeline", a_pipeline)
				.append("cursor", new Document()) /* needed for aggregate */
		);
		
		return a_return;
	}
	
	private static java.util.List<Document> transposeSelectQueryComplexJoinAndGroupBy(Query<?> p_o_sqlQuery) throws IllegalArgumentException {
		@SuppressWarnings("unchecked")
		Select o_selectQuery = (Select)((Query<Select>)p_o_sqlQuery).getQuery();
		String s_collection = p_o_sqlQuery.getTable();
		java.util.List<Document> a_return = new java.util.ArrayList<Document>();
		
		if (o_selectQuery.a_joins.size() != 1) {
			throw new IllegalArgumentException("Nosqlmdb library can only handle one join, not '" + o_selectQuery.a_joins.size() + "'");
		}
		
		/* get join information */
		Join o_join = o_selectQuery.a_joins.get(0);
		
		if (o_join.a_relations.size() != 1) {
			throw new IllegalArgumentException("Nosqlmdb library can only handle one relation within a join, not '" + o_join.a_relations.size() + "'");
		}
		
		/* get relation information */
		Relation o_relation = o_join.a_relations.get(0);
		
		/* we will not handle operator, as we only can use equal, so we only use columnLeft and columnRight info */
		
		/* create */
		Document o_joinDocument = new Document("from", o_join.s_table)
			.append("localField", o_relation.o_columnLeft.s_column)
			.append("foreignField", o_relation.o_columnRight.s_column)
			.append("as", "join_" + o_join.s_table);
				
		/* document variable for first sort, if we use aggregation like MIN or MAX */
		Document o_firstSort = null;
		
		java.util.List<Column> a_aggregations = new java.util.ArrayList<Column>();
		
		for (Column o_column : o_selectQuery.a_columns) {
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_column.getSqlAggregation())) {
				a_aggregations.add(o_column);
				
				if (o_column.getSqlAggregation().contentEquals("MAX")) {
					if (o_firstSort == null) {
						o_firstSort = new Document(o_column.s_column, -1); /* -1 -> DESC */
					} else {
						o_firstSort.append(o_column.s_column, -1); /* -1 -> DESC */
					}
				} else if (o_column.getSqlAggregation().contentEquals("MIN")) {
					if (o_firstSort == null) {
						o_firstSort = new Document(o_column.s_column, 1); /* 1 -> ASC */
					} else {
						o_firstSort.append(o_column.s_column, 1); /* 1 -> ASC */
					}
				}
			}
		}
		
		/* document variable for group by clause */
		Document o_groupBy = null;
		
		for (Column o_column : o_selectQuery.a_groupBy) {
			if (o_groupBy == null) {
				o_groupBy = new Document(o_column.s_column, "$" + o_column.s_column);
			} else {
				o_groupBy.append(o_column.s_column, "$" + o_column.s_column);
			}
		}
		
		if (o_selectQuery.a_columns.size() > 0) {
			/* iterate all columns */
			for (int i = 0; i < o_selectQuery.a_columns.size(); i++) {
				/* if column table is equal to join table, set flag */
				if (o_selectQuery.a_columns.get(i).s_table.contentEquals(o_join.s_table)) {
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_selectQuery.a_columns.get(i).getSqlAggregation())) {
						throw new IllegalArgumentException("Invalid column with aggregation on a join table at the same time. Only aggregation on main table allowed");
					}
					
					o_selectQuery.a_columns.get(i).b_isJoinTable = true;
				}
			}
		}
		
		/* document variable for columns projection */
		Document o_columns = transposeColumns(o_selectQuery.a_columns);
		
		java.util.List<Where> a_where = new java.util.ArrayList<Where>();
		
		/* handle having object */
		for (Where o_having : o_selectQuery.a_having) {
			/* if having clause table is equal to join table, set flag */
			if (o_having.o_column.s_table.contentEquals(o_join.s_table)) {
				o_having.b_isJoinTable = true;
			}
			
			a_where.add(o_having);
		}
		
		/* handle where clauses */
		for (Where o_where : o_selectQuery.a_where) {
			/* if where clause table is equal to join table, set flag */
			if (o_where.o_column.s_table.contentEquals(o_join.s_table)) {
				o_where.b_isJoinTable = true;
			}
			
			if (net.forestany.forestj.lib.Helper.isStringEmpty(o_where.getFilterOperator())) {
				o_where.setFilterOperator("AND");
			}
			
			a_where.add(o_where);
		}
		
		/* document variable for all having and where clauses in select query  */
		Document o_filterDocument = null;
		
		if (a_where.size() > 0) {
			o_filterDocument = transposeWhereClause(a_where);
		}
		
		if (o_selectQuery.o_orderBy.getColumns().size() > 0) {
			/* iterate all columns of order by clause */
			for (int i = 0; i < o_selectQuery.o_orderBy.getColumns().size(); i++) {
				/* if column table is equal to join table, set flag */
				if (o_selectQuery.o_orderBy.getColumns().get(i).s_table.contentEquals(o_join.s_table)) {
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_selectQuery.a_columns.get(i).getSqlAggregation())) {
						throw new IllegalArgumentException("Invalid column with aggregation on a join table at the same time. Only aggregation on main table allowed");
					}
					
					o_selectQuery.o_orderBy.getColumns().get(i).b_isJoinTable = true;
				}
			}
		}
		
		/* document variable for order by list */
		Document o_orderBy = transposeOrderBy(o_selectQuery.o_orderBy);
		
		/* create group columns */
		Document o_groupColumns = new Document("_id", o_groupBy);
		
		/* add other aggregations to group columns */
		for (Column o_column : a_aggregations) {
			String s_column = o_column.s_column;
			
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_column.getSqlAggregation())) {
				s_column = o_column.getSqlAggregation() + "_" + s_column;
			} else if (o_column.b_isJoinTable) {
				s_column = "join_" + o_column.s_table + "." + s_column;
			}
			
			String s_aggregateOperator = null;
			
			if (o_column.getSqlAggregation().contentEquals("AVG")) {
				s_aggregateOperator = "$avg";
			} else if (o_column.getSqlAggregation().contentEquals("COUNT")) {
				s_aggregateOperator = "$addToSet";
			} else if (o_column.getSqlAggregation().contentEquals("MAX")) {
				s_aggregateOperator = "$max";
			} else if (o_column.getSqlAggregation().contentEquals("MIN")) {
				s_aggregateOperator = "$min";
			} else if (o_column.getSqlAggregation().contentEquals("SUM")) {
				s_aggregateOperator = "$sum";
			} else {
				throw new IllegalArgumentException("Invalid aggregation operator: '" + o_column.getSqlAggregation() + "'");
			}
			
			o_groupColumns.append(s_column, new Document(s_aggregateOperator, "$" + o_column.s_column));
		}
		
		/* put all fields into Record for $replaceRoot later */
		o_groupColumns.append("Record", new Document("$first", "$$ROOT"));
		
		/* aggregate columns for replace root aggregate */
		Document o_replaceRootAggregateColumns = null;
		
		for (Column o_column : a_aggregations) {
			String s_column = o_column.s_column;
			
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_column.getSqlAggregation())) {
				s_column = o_column.getSqlAggregation() + "_" + s_column;
			} else if (o_column.b_isJoinTable) {
				s_column = "join_" + o_column.s_table + "." + s_column;
			}
			
			if (o_replaceRootAggregateColumns == null) {
				o_replaceRootAggregateColumns = new Document(s_column, "$" + s_column);
			} else {
				o_replaceRootAggregateColumns.append(s_column, "$" + s_column);
			}
		}
		
		/* create replace root aggregate */
		Document o_replaceRoot = new Document("$replaceRoot", /* this is how we still hold all fields after $group, but we need to merge with aggregation fields of $group */
			new Document("newRoot",
				new Document("$mergeObjects", java.util.Arrays.asList( "$Record", o_replaceRootAggregateColumns ))
			)
		); 
		
		/* create list for aggregate pipeline */
		java.util.List<Document> a_pipeline = new java.util.ArrayList<Document>();
		
		/* add first sort */
		if (o_firstSort != null) {
			a_pipeline.add(new Document("$sort", o_firstSort));
		}
		
		/* add lookup */
		a_pipeline.add(new Document("$lookup", o_joinDocument));
		
		/* add unwind */
		a_pipeline.add(new Document("$unwind", "$join_" + o_join.s_table));
		
		/* add group */
		a_pipeline.add(new Document("$group", o_groupColumns));
		
		/* add replace root aggregate */
		a_pipeline.add(o_replaceRoot);
		
		/* add projection */
		if (o_columns != null) {
			a_pipeline.add(new Document("$project", o_columns));
		}
		
		/* add filter */
		if (o_filterDocument != null) {
			a_pipeline.add(new Document("$match", o_filterDocument));
		}
		
		/* add sort */
		if (o_orderBy != null) {
			a_pipeline.add(new Document("$sort", o_orderBy));
		}
		
		/* add limit clauses of query */
		if ( (o_selectQuery.o_limit != null) &&  (o_selectQuery.o_limit.i_interval > 0) ) {
			a_pipeline.add(new Document("$skip", o_selectQuery.o_limit.i_start));
			a_pipeline.add(new Document("$limit", o_selectQuery.o_limit.i_interval));
		}
		
		/* finish aggregate command */
		a_return.add(
			new Document()
				.append("aggregate", s_collection)
				.append("pipeline", a_pipeline)
				.append("cursor", new Document()) /* needed for aggregate */
		);
		
		return a_return;
	}
	
	private static Object transposeValueFromQuery(java.util.AbstractMap.SimpleEntry<String, Object> p_o_entry) throws IllegalArgumentException {
		if (p_o_entry.getKey().contentEquals("object")) {
			return p_o_entry.getValue();
		} else if (p_o_entry.getKey().contentEquals("string")) {
			return String.class.cast(p_o_entry.getValue());
		} else if (p_o_entry.getKey().contentEquals("boolean")) {
			return Boolean.class.cast(p_o_entry.getValue());
		} else if (p_o_entry.getKey().contentEquals("short")) {
			return Short.class.cast(p_o_entry.getValue());
		} else if (p_o_entry.getKey().contentEquals("integer")) {
			return Integer.class.cast(p_o_entry.getValue());
		} else if (p_o_entry.getKey().contentEquals("long")) {
			return Long.class.cast(p_o_entry.getValue());
		} else if (p_o_entry.getKey().contentEquals("double")) {
			return Double.class.cast(p_o_entry.getValue());
		} else if (p_o_entry.getKey().contentEquals("float")) {
			return Float.class.cast(p_o_entry.getValue());
		} else if (p_o_entry.getKey().contentEquals("bigdecimal")) {
			return java.math.BigDecimal.class.cast(p_o_entry.getValue());
		} else if (p_o_entry.getKey().contentEquals("localdatetime")) {
			return (java.time.LocalDateTime)p_o_entry.getValue();
		} else if (p_o_entry.getKey().contentEquals("localdate")) {
			return (java.time.LocalDate)p_o_entry.getValue();
		} else if (p_o_entry.getKey().contentEquals("localtime")) {
			return (java.time.LocalTime)p_o_entry.getValue();
		} else {
			throw new IllegalArgumentException("Illegal type '" + p_o_entry.getKey() + "'");
		}
	}
	
	private static String transposeOperatorFromQuery(String p_s_operator) throws IllegalArgumentException {
		String s_nosqlMDBOperator = null;
		
		switch (p_s_operator) {
			case "=":
			case "IS":
				s_nosqlMDBOperator = "$eq";
			break;
			case "<>":
			case "IS NOT":
				s_nosqlMDBOperator = "$ne";
			break;
			case "<":
				s_nosqlMDBOperator = "$lt";
			break;
			case "<=":
				s_nosqlMDBOperator = "$lte";
			break;
			case ">":
				s_nosqlMDBOperator = "$gt";
			break;
			case ">=":
				s_nosqlMDBOperator = "$gte";
			break;
			case "LIKE":
				// no new bson.Document, but change value to "/.*" + value + ".*/"
			break;
			case "NOT LIKE":
				s_nosqlMDBOperator = "$not";
			break;
			case "IN":
				s_nosqlMDBOperator = "$in";
			break;
			case "NOT IN":
				s_nosqlMDBOperator = "$nin";
			break;
			default:
				s_nosqlMDBOperator = "$eq";
			break;
		}
		
		return s_nosqlMDBOperator;
	}
	
	private static Document transposeColumns(java.util.List<Column> p_a_columns) throws IllegalArgumentException {
		/* document variable for columns projection */
		Document o_columns = null;
		
		/* retrieve columns from sql query */
		for (Column o_column : p_a_columns) {
			/* ignore "*" column */
			if (!o_column.s_column.contentEquals("*")) {
				if (o_columns == null) {
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_column.s_name)) {
						if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_column.getSqlAggregation())) {
							if (o_column.getSqlAggregation().contentEquals("COUNT")) {
								o_columns = new Document(o_column.s_name, new Document("$size", "$" + o_column.getSqlAggregation() + "_" + o_column.s_column));
							} else {
								o_columns = new Document(o_column.s_name, "$" + o_column.getSqlAggregation() + "_" + o_column.s_column);
							}
						} else {
							o_columns = new Document(o_column.s_name, "$" + o_column.s_column);
						}
					} else {
						if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_column.getSqlAggregation())) {
							if (o_column.getSqlAggregation().contentEquals("COUNT")) {
								o_columns = new Document(o_column.getSqlAggregation() + "_" + o_column.s_column, new Document("$size", "$" + o_column.getSqlAggregation() + "_" + o_column.s_column));
							} else {
								o_columns = new Document(o_column.getSqlAggregation() + "_" + o_column.s_column, 1);
							}
						} else if (o_column.b_isJoinTable) {
							o_columns = new Document(o_column.s_column, "$join_" + o_column.s_table + "." + o_column.s_column);
						} else {
							o_columns = new Document(o_column.s_column, 1);
						}
					}
				} else {
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_column.s_name)) {
						if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_column.getSqlAggregation())) {
							if (o_column.getSqlAggregation().contentEquals("COUNT")) {
								o_columns.append(o_column.s_name, new Document("$size", "$" + o_column.getSqlAggregation() + "_" + o_column.s_column));
							} else {
								o_columns.append(o_column.s_name, "$" + o_column.getSqlAggregation() + "_" + o_column.s_column);
							}
						} else {
							o_columns.append(o_column.s_name, "$" + o_column.s_column);
						}
					} else {
						if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_column.getSqlAggregation())) {
							if (o_column.getSqlAggregation().contentEquals("COUNT")) {
								o_columns.append(o_column.getSqlAggregation() + "_" + o_column.s_column, new Document("$size", "$" + o_column.getSqlAggregation() + "_" + o_column.s_column));
							} else {
								o_columns.append(o_column.getSqlAggregation() + "_" + o_column.s_column, 1);
							}
						} else if (o_column.b_isJoinTable) {
							o_columns.append("join_" + o_column.s_table + "." + o_column.s_column, 1);
						} else {
							o_columns.append(o_column.s_column, 1);
						}
					}
				}
			}
		}
		
		if (o_columns != null) {
			o_columns.append("_id", 0);
		}
		
		return o_columns;
	}
	
	private static Document transposeOrderBy(OrderBy p_o_orderBy) throws IllegalArgumentException {
		/* document variable for order by list */
		Document o_orderBy = null;
		
		/* add order by clause to query */
		if ( (p_o_orderBy != null) && (p_o_orderBy.getAmount() > 0) ) {
			int i = -1;
			
			/* add each column with direction ASC or DESC */
			for (Column o_column : p_o_orderBy.getColumns()) {
				String s_column = o_column.s_column;
				
				if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_column.getSqlAggregation())) {
					s_column = o_column.getSqlAggregation() + "_" + o_column.s_column;
				} else if (o_column.b_isJoinTable) {
					s_column = "join_" + o_column.s_table + "." + o_column.s_column;
				}
				
				if (p_o_orderBy.getDirections().get(++i)) {
					if (o_orderBy == null) {
						o_orderBy = new Document(s_column, 1); /* 1 -> ASC */
					} else {
						o_orderBy.append(s_column, 1); /* 1 -> ASC */
					}
				} else {
					if (o_orderBy == null) {
						o_orderBy = new Document(s_column, -1); /* -1 -> DESC */
					} else {
						o_orderBy.append(s_column, -1); /* -1 -> DESC */
					}
				}
			}
		}
		
		return o_orderBy;
	}
	
	private static Document transposeWhereClause(java.util.List<Where> p_a_whereClauses) throws IllegalArgumentException {
		return transposeWhereClause(p_a_whereClauses, 0, p_a_whereClauses.size() - 1, null);
	}
	
	private static Document transposeWhereClause(java.util.List<Where> p_a_whereClauses, int p_i_min, int p_i_max, String p_s_lastFilterOperator) throws IllegalArgumentException {
		/* check if parameter of where clauses is valid */
		if ( (p_a_whereClauses == null) || (p_a_whereClauses.size() < 1) ) {
			throw new IllegalArgumentException("List of where clauses is null or has no elements.");
		}
		
		/* return value */
		Document o_return = null;
		
		if (p_i_min == p_i_max) { /* check if we only have one where clause */
			java.util.AbstractMap.SimpleEntry<String, Object> o_valueEntry = whereClauseGetValue(p_a_whereClauses.get(p_i_min));
			o_return = whereClauseToBSONDocument(p_a_whereClauses.get(p_i_min), o_return, o_valueEntry);
		} else { /* handle multiple where clauses */
			/* check all brackets of where clauses */
			whereClauseCheckBrackets(p_a_whereClauses);
			
			java.util.AbstractMap.SimpleEntry<String, Object> o_valueEntry = whereClauseGetValue(p_a_whereClauses.get(p_i_min));
			
			if (p_s_lastFilterOperator == null) { /* first element */
				String s_nextFilterOperator = p_a_whereClauses.get(p_i_min + 1).getFilterOperator();
				
				/* XOR is not supported for nosqlmdb transpose library */
				if (s_nextFilterOperator.toUpperCase().contentEquals("XOR")) {
					throw new IllegalArgumentException("XOR filter operator is not supported for nosqlmdb transpose library");
				}
				
				Document o_current = whereClauseToBSONDocument(p_a_whereClauses.get(p_i_min), null, o_valueEntry);
				
				/* find other where clauses with current clause with new filter operator */
				Document o_next = transposeWhereClause(p_a_whereClauses, p_i_min + 1, p_i_max, s_nextFilterOperator);
				
				if (o_next != null) {
					/* add new filter operator with sub document list */
					o_return = o_current.append( ( (s_nextFilterOperator.toUpperCase().contentEquals("AND")) ? "$and" : "$or" ) , java.util.Arrays.asList( o_next ));
				} else {
					/* set current where clause as return object */
					o_return = o_current;
				}
			} else {
				String s_nextFilterOperator = p_a_whereClauses.get(p_i_min + 1).getFilterOperator();
				
				/* XOR is not supported for nosqlmdb transpose library */
				if (s_nextFilterOperator.toUpperCase().contentEquals("XOR")) {
					throw new IllegalArgumentException("XOR filter operator is not supported for nosqlmdb transpose library");
				}
				
				o_return = whereClauseToBSONDocument(p_a_whereClauses.get(p_i_min), o_return, o_valueEntry);
				
				/* find other where clauses with current clause with new filter operator */
				Document o_next = transposeWhereClause(p_a_whereClauses, p_i_min + 1, p_i_max, s_nextFilterOperator);
				
				if (o_next != null) {
					for (java.util.Map.Entry<String, Object> o_entry : o_next.entrySet()) {
						o_return.append(o_entry.getKey(), o_entry.getValue());
					}
				}
				
				/* filter operator will change if there is no bracket end */
				if (!p_s_lastFilterOperator.toUpperCase().contentEquals(s_nextFilterOperator.toUpperCase())) {
					if ( (!p_a_whereClauses.get(p_i_min).b_bracketStart) && (p_a_whereClauses.get(p_i_min).b_bracketEnd) ) {
						Document o_before = null;
						Document o_after = null;
						boolean b_one = false;
						
						for (java.util.Map.Entry<String, Object> o_entry : o_return.entrySet()) {
							if (!b_one) {
								o_before = new Document(o_entry.getKey(), o_entry.getValue());
							} else {
								if (o_after == null) {
									o_after = new Document(o_entry.getKey(), o_entry.getValue());
								} else {
									o_after.append(o_entry.getKey(), o_entry.getValue());
								}
							}
							
							b_one = true;
						}
						
						o_return = o_before;
						o_return.append( ( (s_nextFilterOperator.toUpperCase().contentEquals("AND")) ? "$and" : "$or" ) , java.util.Arrays.asList( o_after ) );
					} else {
						o_return = new Document( ( (s_nextFilterOperator.toUpperCase().contentEquals("AND")) ? "$and" : "$or" ) , java.util.Arrays.asList( o_return ) );
					}
				}
			}
		}
		
		return o_return;
	}
	
	private static Document whereClauseToBSONDocument(Where p_o_where, Document p_o_document, java.util.AbstractMap.SimpleEntry<String, Object> p_o_valueEntry) throws IllegalArgumentException {
		String s_column = p_o_where.o_column.s_column;
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_o_where.o_column.getSqlAggregation())) {
			s_column = p_o_where.o_column.getSqlAggregation() + "_" + s_column;
		} else if (p_o_where.b_isJoinTable) {
			s_column = "join_" + p_o_where.o_column.s_table + "." + s_column;
		}
		
		if (p_o_document == null) { /* must create bson document object */
			/* handle LIKE and NOT LIKE operator */
			if (p_o_where.getOperator().toUpperCase().contentEquals("LIKE")) {
				p_o_document = new Document(s_column, new Document("$regex", ".*" + transposeValueFromQuery(p_o_valueEntry).toString().replace("%", "") + ".*") );	
			} else if (p_o_where.getOperator().toUpperCase().contentEquals("NOT LIKE")) {
				p_o_document = new Document(s_column, new Document( transposeOperatorFromQuery(p_o_where.getOperator()), new Document("$regex", ".*" + transposeValueFromQuery(p_o_valueEntry).toString().replace("%", "") + ".*")));
			} else {
				p_o_document = new Document(s_column, new Document( transposeOperatorFromQuery(p_o_where.getOperator()), transposeValueFromQuery(p_o_valueEntry)));
			}
		} else { /* append to parameter bson document object */
			/* handle LIKE and NOT LIKE operator */
			if (p_o_where.getOperator().toUpperCase().contentEquals("LIKE")) {
				p_o_document.append(s_column, new Document("$regex", ".*" + transposeValueFromQuery(p_o_valueEntry).toString().replace("%", "") + ".*" ) );	
			} else if (p_o_where.getOperator().toUpperCase().contentEquals("NOT LIKE")) {
				p_o_document.append(s_column, new Document( transposeOperatorFromQuery(p_o_where.getOperator()), new Document("$regex", ".*" + transposeValueFromQuery(p_o_valueEntry).toString().replace("%", "") + ".*")));
			} else {
				p_o_document.append(s_column, new Document( transposeOperatorFromQuery(p_o_where.getOperator()), transposeValueFromQuery(p_o_valueEntry)));
			}
		}
		
		return p_o_document;
	}
	
	private static java.util.AbstractMap.SimpleEntry<String, Object> whereClauseGetValue(Where p_o_whereClause) {
		/* store value we can retrieve from where clause */
		java.util.List<java.util.AbstractMap.SimpleEntry<String, Object>> a_values = new java.util.ArrayList<java.util.AbstractMap.SimpleEntry<String, Object>>();
		
		@SuppressWarnings("unused")
		String s_foo = net.forestany.forestj.lib.sql.Query.convertToPreparedStatementQuery(net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB, p_o_whereClause.toString(), a_values, false);
		
		if (a_values.size() != 1) {
			throw new IllegalArgumentException("Could not retrieve value from where clause");
		}
		
		logValuesFromQuery(a_values);
		
		return a_values.get(0);
	}
	
	private static void whereClauseCheckBrackets(java.util.List<Where> p_a_whereClauses) throws IllegalArgumentException {
		/* iterate reverse */
		for (int i = 0; i < p_a_whereClauses.size(); i++) {
			/* check brackets, starting with bracket start */
			if ( (p_a_whereClauses.get(i).b_bracketStart) && (!p_a_whereClauses.get(i).b_bracketEnd) ) {
				boolean b_bracketClosed = false;
				String s_filterOperator = null;
				
				/* within brackets all filter operator must be the same */
				for (int j = i; j < p_a_whereClauses.size(); j++) {
					if ( (i != j) && (p_a_whereClauses.get(j).b_bracketStart) && (!p_a_whereClauses.get(j).b_bracketEnd) ) {
						throw new IllegalArgumentException("Bracket nested within each other in where clause list are not supported for nosqlmdb transpose library");
					}
					
					/* if we have a filter operator and no bracket start */
					if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_a_whereClauses.get(j).getFilterOperator())) && (! ( (p_a_whereClauses.get(j).b_bracketStart) && (!p_a_whereClauses.get(j).b_bracketEnd) ) ) ) {
						if (s_filterOperator == null) { /* save filter operator for comparison for all clauses within brackets */
							s_filterOperator = p_a_whereClauses.get(j).getFilterOperator().toUpperCase();
						} else if (!s_filterOperator.contentEquals(p_a_whereClauses.get(j).getFilterOperator().toUpperCase())) { /* filter operator differs */
							throw new IllegalArgumentException("All filter operators must be the same within where clause brackets");
						}
					}
					
					/* bracket end found */
					if ( (!p_a_whereClauses.get(j).b_bracketStart) && (p_a_whereClauses.get(j).b_bracketEnd) ) {
						b_bracketClosed = true;
						break;
					}
				}
				
				/* check if bracket was closed correctly */
				if (!b_bracketClosed ) {
					throw new IllegalArgumentException("A bracket in where clause was never closed or opened");
				}
			}
		}
	}

	private static void logValuesFromQuery(java.util.List<java.util.AbstractMap.SimpleEntry<String, Object>> p_a_values) {
		String s_empty = "                              ";
		
		for (java.util.AbstractMap.SimpleEntry<String, Object> o_entry : p_a_values) {
			if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass(
				"\t" + 
				o_entry.getKey() + /* key value */
				s_empty.substring(0, s_empty.length() - o_entry.getKey().length()) + /* white spaces */ 
				o_entry.getValue().getClass().getTypeName() + /* type value */
				s_empty.substring(0, s_empty.length() - o_entry.getValue().getClass().getTypeName().length()) + /* white spaces */
				( (net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? o_entry.getValue().toString() : "" ) /* value */
			);
		}
	}
}
