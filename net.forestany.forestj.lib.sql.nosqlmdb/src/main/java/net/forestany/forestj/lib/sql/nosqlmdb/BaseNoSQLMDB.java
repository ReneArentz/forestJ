package net.forestany.forestj.lib.sql.nosqlmdb;

import org.bson.Document;

import net.forestany.forestj.lib.sqlcore.Base;
import net.forestany.forestj.lib.sqlcore.BaseGateway;
import net.forestany.forestj.lib.sqlcore.IQuery;
import net.forestany.forestj.lib.sqlcore.SqlType;

/**
 * DISCLAIMER: the author and all supporters of this library are clearly opposed to the use of the name of this database technology.
 * Some path and class names must be used, but wherever possible we will use the name 'nosqlmdb' instead.
 *
 * BaseNoSQLMDB class for nosqlmdb connection to a database and fetching queries to it.
 * requires mdb-driver-sync.
 */
public class BaseNoSQLMDB extends Base {

	/* Fields */
	
	private com.mongodb.client.MongoClient o_nosqlmdbClient = null;
	private com.mongodb.client.MongoDatabase o_nosqlmdbDatabase = null;
	private com.mongodb.client.ClientSession o_nosqlmdbSession = null;
	private Document o_result = null;
	private int i_lastInsertId = -1;
	
	/* Properties */
	
	/* Methods */

	/**
	 * BaseNoSQLMDB constructor, initiating database server connection
	 * 
	 * @param p_s_host						address of database server, optional port specification e.g. 'localhost:3306'
	 * @throws IllegalArgumentException		illegal database gateway or illegal host parameter
	 * @throws IllegalAccessException		error creating database server connection
	 */
	public BaseNoSQLMDB(String p_s_host) throws IllegalArgumentException, IllegalAccessException {
		this(p_s_host, null);
	}
	
	/**
	 * BaseNoSQLMDB constructor, initiating database server connection
	 * 
	 * @param p_s_host						address of database server, optional port specification e.g. 'localhost:3306'
	 * @param p_s_datasource				parameter for selecting a database within target database server
	 * @throws IllegalArgumentException		illegal database gateway or illegal host parameter
	 * @throws IllegalAccessException		error creating database server connection
	 */
	public BaseNoSQLMDB(String p_s_host, String p_s_datasource) throws IllegalArgumentException, IllegalAccessException {
		this(p_s_host, p_s_datasource, null, null);
	}
	
	/**
	 * BaseNoSQLMDB constructor, initiating database server connection
	 * 
	 * @param p_s_host						address of database server, optional port specification e.g. 'localhost:3306'
	 * @param p_s_datasource				parameter for selecting a database within target database server
	 * @param p_s_user						user name for database login
	 * @throws IllegalArgumentException		illegal database gateway or illegal host parameter
	 * @throws IllegalAccessException		error creating database server connection
	 */
	public BaseNoSQLMDB(String p_s_host, String p_s_datasource, String p_s_user) throws IllegalArgumentException, IllegalAccessException {
		this(p_s_host, p_s_datasource, p_s_user, null);
	}
	
	/**
	 * BaseNoSQLMDB constructor, initiating database server connection
	 * 
	 * @param p_s_host						address of database server, optional port specification e.g. 'localhost:3306'
	 * @param p_s_datasource				parameter for selecting a database within target database server
	 * @param p_s_user						user name for database login
	 * @param p_s_password					user's password for database login
	 * @throws IllegalArgumentException		illegal database gateway or illegal host parameter
	 * @throws IllegalAccessException		error creating database server connection
	 */
	public BaseNoSQLMDB(String p_s_host, String p_s_datasource, String p_s_user, String p_s_password) throws IllegalArgumentException, IllegalAccessException {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_host)) {
			throw new IllegalArgumentException("No base-host/base-file was selected");
		}
		
		String s_temp_user = null;
		String s_temp_pw = null;
		String s_temp_connectionUrl = "";
		
		this.e_baseGateway = net.forestany.forestj.lib.sqlcore.BaseGateway.NOSQLMDB;
		
        /* initiate server connection */
		if ((!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_user)) && (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_password))) {
			s_temp_user = p_s_user;
			s_temp_pw = p_s_password;
		} else if ((!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_user)) && (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_password))) {
			s_temp_user = p_s_user;
			s_temp_pw = null;
		}
		
		/* choose database gateway and create new connection object with connection settings */
		boolean b_exc = false;
		
		switch (this.e_baseGateway) {
			case NOSQLMDB:
				/* nosqlmdb://user1:pwd1@host1 */
				s_temp_connectionUrl = "mongodb://";
				
				if (s_temp_user != null) {
					s_temp_connectionUrl += s_temp_user;
				
					if (s_temp_pw != null) {
						s_temp_connectionUrl += ":" + "%forestJpassword%";
					}
					
					s_temp_connectionUrl += "@";
				}
				
				s_temp_connectionUrl += p_s_host;
				this.s_tempConnectionString = s_temp_connectionUrl;
			break;
			default:
				b_exc = true;
			break;
		}
		
		if (b_exc) {
			throw new IllegalArgumentException("BaseGateway[" + this.e_baseGateway + "] not implemented");
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("try database connection with: '" + s_temp_connectionUrl.replace("%forestJpassword%", "********") + "'");
												
		if (s_temp_pw != null) {
			s_temp_connectionUrl = s_temp_connectionUrl.replace("%forestJpassword%", s_temp_pw);
		}

												net.forestany.forestj.lib.Global.ilogConfig("create nosqlmdb client object");
		
		/* create nosqlmdb-client object, this will not throw an exception, we can only check the connection on first use of the object */
		this.o_nosqlmdbClient = com.mongodb.client.MongoClients.create(s_temp_connectionUrl);

												net.forestany.forestj.lib.Global.ilogConfig("start session");
		
		/* create a nosqlmdb-client session for transaction options */
		this.o_nosqlmdbSession = this.o_nosqlmdbClient.startSession();
		
		if (p_s_datasource != null) {
			try {
														net.forestany.forestj.lib.Global.ilogConfig("get database '" + p_s_datasource + "'");
				this.o_nosqlmdbDatabase = this.o_nosqlmdbClient.getDatabase(p_s_datasource);
				
				if (!this.testConnection()) {
					throw new Exception("Ping command to database '" + p_s_datasource + "' was not successful");
				}
				
														net.forestany.forestj.lib.Global.ilogConfig("database connection to '" + this.e_baseGateway + "' established");
			} catch (Exception o_exc) {
				throw new IllegalAccessException("Could not connect to nosqlmdb database '" + p_s_datasource + "'; " + o_exc.getMessage());
			}
		}
	}
	
	/**
	 * Test connection to database with current connection
	 * 
	 * @return true - connection successful, false - connection failed
	 */
	public boolean testConnection() {
		Document o_tempResult = this.o_nosqlmdbDatabase.runCommand(new Document("ping", new org.bson.BsonInt64(1)));
		
		/* check if ping command returns '"ok" : 1.0' */
		if ( (!o_tempResult.containsKey("ok")) && ((Double)o_tempResult.get("ok") != 1.0d) ) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Check if current connection is not closed
	 * 
	 * @return true - connection is still valid, false - connection closed, a new one must be established to continue database communication
	 */
	public boolean isClosed() {
		try {
			return this.o_nosqlmdbSession.getServerSession().isClosed();
		} catch (Exception o_exc) {
			return false;
		}	
	}
	
	/**
	 * Method for closing database connection, ignoring any sql exceptions
	 */
	public void closeConnection() {
		if (this.o_nosqlmdbSession != null) {
	        try { this.o_nosqlmdbSession.close(); } catch (Exception o_exc) { /* ignored */ }
	    }
	    
	    if (this.o_nosqlmdbClient != null) {
	        try { this.o_nosqlmdbClient.close(); } catch (Exception o_exc) { /* ignored */ }
	    }
	    
	    										net.forestany.forestj.lib.Global.ilogConfig("database connection closed");
	}
	
	/**
	 * Method to convert json string to a list of bson documents
	 * 
	 * @param p_s_json										json string
	 * @return java.util.List&lt;org.bson.Document&gt;		list of bson documents
	 */
	public static java.util.List<Document> jsontoListOfBSONDocuments(String p_s_json){
		java.util.List<Document> a_return = null;
		Document o_document = Document.parse("{ \"list\":" + p_s_json + "}");
	    Object o_list = o_document.get("list");
	    
	    if(o_list instanceof java.util.List<?>) {
	    	@SuppressWarnings("unchecked")
	    	java.util.List<Document> a_temp = (java.util.List<Document>) o_document.get("list");
	    	a_return = a_temp;
	    }
	    
	    return a_return;
	}
	
	/**
	 * Method to log all results of a nosqlmdb command within MASS level
	 * 
	 * @param p_o_result			bson document object
	 */
	private void logCommandResults(Document p_o_result) {
		String s_empty = "                                        ";
	
		if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("command results:");
		
		/* log result bson document */
		for (java.util.Map.Entry<String, Object> o_entry : p_o_result.entrySet()) {
			if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass(
				"\t" + 
				o_entry.getKey() + /* key value */
				s_empty.substring(0, s_empty.length() - o_entry.getKey().length()) + /* white spaces */ 
				o_entry.getValue().getClass().getTypeName() + /* type value */ 
				s_empty.substring(0, s_empty.length() - o_entry.getValue().getClass().getTypeName().length()) + /* white spaces */ 
				o_entry.getValue().toString() /* value */
			);
		}
	}
	
	/**
	 * Fetch a query or a amount of queries separated by QueryAbstract.s_querySeparator, with auto commit
	 * 
	 * @param p_o_sqlQuery				query object of Query class &lt;? must be of type QueryAbstract&gt;
	 * @return							list of hash maps, key(string) -> column name + value(object) -> column value of a database record
	 * @throws IllegalAccessException	exception accessing column type, column name or just column value of current result set record
	 */
	public java.util.List<java.util.LinkedHashMap<String, Object>> fetchQuery(IQuery<?> p_o_sqlQuery) throws IllegalAccessException {
		return this.fetchQuery(p_o_sqlQuery, true);
	}
	
	/**
	 * Fetch a query or a amount of queries separated by QueryAbstract.s_querySeparator
	 * 
	 * @param p_o_sqlQuery				query object of Query class &lt;? must be of type QueryAbstract&gt;
	 * @param p_b_autoTransaction		transaction flag: true - commit database after each execution of query object automatically, false - do not commit automatically
	 * @return							list of hash maps, key(string) -> column name + value(object) -> column value of a database record
	 * @throws IllegalAccessException	exception accessing column type, column name or just column value of current result set record
	 */
	public java.util.List<java.util.LinkedHashMap<String, Object>> fetchQuery(IQuery<?> p_o_sqlQuery, boolean p_b_autoTransaction) throws IllegalAccessException {
		/* prepare return value */
		java.util.List<java.util.LinkedHashMap<String, Object>> a_rows = new java.util.ArrayList<java.util.LinkedHashMap<String, Object>>();
		
		boolean b_ignoreResult = false;
		
		/* check if query BaseGateway matches BaseGateway of current connection */
		if (p_o_sqlQuery.getBaseGateway() != this.e_baseGateway) {
			throw new IllegalAccessException("Query has invalid BaseGateway setting: '" + p_o_sqlQuery.getBaseGateway() + "(query)' != '" + this.e_baseGateway + "(base)'");
		}
		
		try {
			/* convert sql query object to string and check for query separator, not supported for nosqlmdb library */
			if (p_o_sqlQuery.toString().contains(((net.forestany.forestj.lib.sql.Query<?>) p_o_sqlQuery).getQuerySeparator())) {
				throw new Exception("Multiple sql queries, separated by query separator are not supported for nosqlmdb library");
			}
			
			/* automatically start manual transaction */
			if (p_b_autoTransaction) {
				this.manualStartTransaction();
			}
			
													if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("transpose sql query to nosqlmdb command(s)");
			
			java.util.List<java.util.AbstractMap.SimpleEntry<String, Object>> a_values = new java.util.ArrayList<java.util.AbstractMap.SimpleEntry<String, Object>>();
			@SuppressWarnings("unused")
			String s_queryFoo = net.forestany.forestj.lib.sql.Query.convertToPreparedStatementQuery(this.e_baseGateway, p_o_sqlQuery.toString(), a_values, false);
													
			java.util.List<Document> a_commands = BaseNoSQLMDBTranspose.transpose((net.forestany.forestj.lib.sql.Query<?>)p_o_sqlQuery);
			
			/* log nosqlmdb commands */
			if (net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) {
				for (Document o_command : a_commands) {
					net.forestany.forestj.lib.Global.ilogFiner(o_command.toBsonDocument().toJson());
				}
			}
			
			if (p_o_sqlQuery.getSqlType() == SqlType.INSERT) { /* execute insert query */
				try {
					Document o_insertCommand = this.getInsertCommandWithAutoIncrement(a_commands);
															if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("execute insert command");
					this.o_result = this.o_nosqlmdbDatabase.runCommand(o_insertCommand);
															if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("insert command executed");
					if ( (!this.o_result.containsKey("ok")) && ((Double)this.o_result.get("ok") != 1.0d) ) {
						throw new Exception("Nosqlmdb command has no value for result key 'ok' or result key's value 'ok' is not '1.0'");
					}
					
					this.logCommandResults(this.o_result);
				} catch (Exception o_exc) {
					throw o_exc;
				} finally {
					b_ignoreResult = true;
				}
			} else { /* execute query */
				try {
					/* iterate each command and execute it, getting bson document as result */
					for (Document o_command : a_commands) {
																if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("execute command");
						this.o_result = this.o_nosqlmdbDatabase.runCommand(o_command);
																if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("command executed");
						if ( (!this.o_result.containsKey("ok")) && ((Double)this.o_result.get("ok") != 1.0d) ) {
							throw new Exception("Nosqlmdb command has no value for result key 'ok' or result key's value 'ok' is not '1.0'");
						}
						
						this.logCommandResults(this.o_result);
					}
				} catch (Exception o_exc) {
					throw o_exc;
				} finally {
					b_ignoreResult = true;
				}
			}
		} catch (Exception o_exc) {
			/* if transaction flag is set, roll-back transaction */
			if (p_b_autoTransaction) {
				this.manualRollback();
			}
			
			throw new IllegalAccessException("Could not execute nosqlmdb command; " + o_exc.getMessage());
		}
		
		/* check if query got a result */
		if ( (this.o_result == null) && (!b_ignoreResult) ) {
			/* if transaction flag is set, roll-back transaction */
			if (p_b_autoTransaction) {
				this.manualRollback();
			}
			
			throw new IllegalAccessException("The query could not be executed or has no valid result.");
		}
		
		/* on SELECT query, prepare to return result rows */
		if (p_o_sqlQuery.getSqlType() == SqlType.SELECT) {
			try {
				@SuppressWarnings("unchecked")
				java.util.List<Document> a_result = ( (java.util.List<Document>)( (Document)this.o_result.get("cursor") ).get("firstBatch") );
				
				if (a_result.size() > 0) {
															if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get collection meta data");
					Document o_collectionMetaData = getCollectionMetaData(a_result.get(0), p_o_sqlQuery.getTable());
					
					/* fetch nosqlmdb result into hash map array */
					for (Document o_document : a_result) {
																if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("fetch row");
						a_rows.add(this.fetchRow(o_document, o_collectionMetaData));
					}
				}
			} catch (Exception o_exc) {
				throw new IllegalAccessException("Could not fetch row, issue get metadata and column information; " + o_exc.getMessage());
			}
		} else {
			try {
				/* returning amount of affected rows by nosqlmdb commands (and last inserted id if insert statement) */
				java.util.LinkedHashMap<String, Object> o_row = new java.util.LinkedHashMap<String, Object>();
														if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get affected rows");
				double d_ok = Double.valueOf(this.o_result.get("ok").toString());
				o_row.put("AffectedRows", (int)d_ok);
														net.forestany.forestj.lib.Global.ilogFinest("affected rows: '" + o_row.get("AffectedRows") + "'");
				
				/* add last inserted id for result */
				if (p_o_sqlQuery.getSqlType() == SqlType.INSERT) {
															net.forestany.forestj.lib.Global.ilogFinest("store LastInsertId '" + this.i_lastInsertId + "' in result row");
					o_row.put("LastInsertId", this.i_lastInsertId);
				}
				
				/* overwrite affected rows value for UPDATE or DELETE queries with 'n' result key value */ 
				if ( (p_o_sqlQuery.getSqlType() == SqlType.UPDATE) || (p_o_sqlQuery.getSqlType() == SqlType.DELETE) ) {
															if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("overwrite affected rows value for UPDATE or DELETE queries");
					if (this.o_result.containsKey("n")) {
						o_row.put("AffectedRows", (Integer)this.o_result.get("n"));
																net.forestany.forestj.lib.Global.ilogFinest("affected rows: '" + o_row.get("AffectedRows") + "'");
					}
				}
				
														if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("add row object to result list");
				a_rows.add(o_row);
			} catch (Exception o_exc) {
				/* do not throw exception here, maybe only log this information */
				System.err.println("Could not count affected rows or could not fetch last insert id; " + o_exc.getMessage());
			}
		}
		
		/* if transaction flag is set, commit transaction */
		if (p_b_autoTransaction) {
			this.manualCommit();
		}
		
		this.o_result = null;
		this.i_lastInsertId = -1;
		
		return a_rows;
	}
	
	/**
	 * Method to get meta data information for collection columns like column type
	 * 
	 * @param p_o_resultFirstDocument			first bson document of result for column key set
	 * @param p_s_collection					collection name
	 * @return									result bson document with type information of columns in result set
	 * @throws Exception						if type information could not be queried or has not been found
	 */
	private Document getCollectionMetaData(Document p_o_resultFirstDocument, String p_s_collection) throws Exception {
		Document o_fieldTypes = null;
		Document o_joinColumns = null;
		
		for (java.util.Map.Entry<String, Object> o_entry : p_o_resultFirstDocument.entrySet()) {
			/* column name */
			String s_column = o_entry.getKey();
			
			/* remove aggregation from name, which automatically was set as prefix in transpose class */
			for (String s_aggregation : java.util.Arrays.asList("AVG_", "COUNT_", "MAX_", "MIN_", "SUM_")) {
				if (s_column.startsWith(s_aggregation)) {
					s_column = s_column.substring(s_aggregation.length());
				}
			}
			
			/* skip '_id' column */
			if (s_column.contentEquals("_id")) {
				continue;
			}
			
			/* handle columns which are retrieved from the join collection */
			if (s_column.startsWith("join_")) {
				o_joinColumns = getCollectionMetaData((Document)o_entry.getValue(), s_column.substring(5));
				continue;
			}
			
			if (o_fieldTypes == null) {
				o_fieldTypes = new Document(s_column + "Type", new Document("$type", "$" + s_column));
			} else {
				o_fieldTypes.append(s_column + "Type", new Document("$type", "$" + s_column));
			}
		}
		
		if (o_fieldTypes == null) {
			throw new Exception("Could not list columns for field types query on nosqlmdb library");
		}
		
		Document o_command = new Document()
		.append("aggregate", p_s_collection)
		.append("pipeline", java.util.Arrays.asList(
			new Document("$project", o_fieldTypes),
			new Document("$limit", 1),
			new Document("$skip", 0)
		))
		.append("cursor", new Document()); /* needed for aggregate */
		
												if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass(o_command.toBsonDocument().toJson());
												if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("execute command");
												
		Document o_metadataResult = this.o_nosqlmdbDatabase.runCommand(o_command);
		
												if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("command executed");
		
		if ( (!o_metadataResult.containsKey("ok")) && ((Double)o_metadataResult.get("ok") != 1.0d) ) {
			throw new Exception("Nosqlmdb command has no value for result key 'ok' or result key's value 'ok' is not '1.0'");
		}
		
		this.logCommandResults(o_metadataResult);
		
		@SuppressWarnings("unchecked")
		java.util.List<Document> a_result = ( (java.util.List<Document>)( (Document)o_metadataResult.get("cursor") ).get("firstBatch") );
		
		if (a_result.size() < 1) {
			throw new Exception("Could not get result for listing columns for field types query on nosqlmdb library");
		}
		
		/* add meta data information about the join columns as well */
		if (o_joinColumns != null) {
			for (java.util.Map.Entry<String, Object> o_entry : o_joinColumns.entrySet()) {
				/* skip '_id' column */
				if (o_entry.getKey().contentEquals("_id")) {
					continue;
				}
				
				a_result.get(0).append(o_entry.getKey(), o_entry.getValue());
			}
		}
		
		return a_result.get(0);
	}
	
	/**
	 * Method to create a record hash map of current result set record
	 * 
	 * @return							record hash map, key(string) -> column name + value(object) -> column value
	 * @throws IllegalAccessException	exception accessing column type, column name or just column value of current result set record
	 */
	private java.util.LinkedHashMap<String, Object> fetchRow(Document p_o_document, Document p_o_collectionMetaData) throws Exception {
		java.util.LinkedHashMap<String, Object> o_row = new java.util.LinkedHashMap<String, Object>();
		
		Document o_adjustedDocument = null;
		
		/* get all column values on same bson document level */
		for (java.util.Map.Entry<String, Object> o_entry : p_o_document.entrySet()) {
			/* handle join fields */
			if (o_entry.getKey().startsWith("join_")) {
				for (java.util.Map.Entry<String, Object> o_joinEntry : ((Document)o_entry.getValue()).entrySet()) {
					if (o_adjustedDocument == null) {
						o_adjustedDocument = new Document(o_joinEntry.getKey(), o_joinEntry.getValue());
					} else {
						o_adjustedDocument.append(o_joinEntry.getKey(), o_joinEntry.getValue());
					}
				}
				
				/* skip key object pair, starting with key 'join_' */
				continue;
			}
			
			if (o_adjustedDocument == null) {
				o_adjustedDocument = new Document(o_entry.getKey(), o_entry.getValue());
			} else {
				o_adjustedDocument.append(o_entry.getKey(), o_entry.getValue());
			}
		}
		
		/* date time formatter instance with datetime format for later use */
		java.time.format.DateTimeFormatter dtf_datetime_instance = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		/* date time formatter instance with time format for later use */
		java.time.format.DateTimeFormatter dtf_time_instance = java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss");

		/* iterate adjusted document to create row object */
		for (java.util.Map.Entry<String, Object> o_entry : o_adjustedDocument.entrySet()) {
			/* column name */
			String s_column = o_entry.getKey();
			
			/* remove aggregation from name, which automatically was set as prefix in transpose class */
			for (String s_aggregation : java.util.Arrays.asList("AVG_", "COUNT_", "MAX_", "MIN_", "SUM_")) {
				if (s_column.startsWith(s_aggregation)) {
					s_column = s_column.substring(s_aggregation.length());
				}
			}
			
			/* skip '_id' column */
			if (s_column.contentEquals("_id")) {
				continue;
			}
			
			String s_type = null;
			
			if (!p_o_collectionMetaData.containsKey(s_column + "Type")) {
				throw new Exception("Could not determine type for field '" + s_column + "'");
			} else {
				s_type = p_o_collectionMetaData.get(s_column + "Type").toString();
														if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("column >>> " + s_column + "Type = " + s_type + ( (net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? " >>> Value as string: " + ( (o_entry.getValue() != null) ? o_entry.getValue().toString() : "null" ) : "" ) );
			}
			
			boolean b_exc = false;
			boolean b_excDate = false;

			/* re-check type int and long, because column can be mixed type */
			if ( (s_type.contentEquals("int")) || (s_type.contentEquals("long")) ) {
				if (o_entry.getValue() instanceof Double) {
					s_type = "double";
															if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("column >>> " + s_column + "Type = " + s_type + ", because value type is Double");
				} else if (o_entry.getValue() instanceof java.math.BigDecimal) {
					s_type = "decimal";
															if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("column >>> " + s_column + "Type = " + s_type + ", because value type is BigDecimal");
				}
			}
			
			switch (s_type) {
				case "int":
					if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with Integer.class.cast");
					o_row.put(s_column, Integer.class.cast(o_entry.getValue()));
					break;
				case "long":
					if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with Long.class.cast");
					o_row.put(s_column, Long.class.cast(o_entry.getValue()));
					break;
				case "date":
				case "timestamp":
					/* all date and timestamp values are stored as UTC values within nosqlmdb */
					if ( (o_entry.getValue() == null) || (net.forestany.forestj.lib.Helper.isStringEmpty(o_entry.getValue().toString())) || (o_entry.getValue().toString().contentEquals("NULL")) ) {
						/* value is null */
						if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("column value is NULL");
						o_row.put(s_column, null);
					} else if (o_entry.getValue().getClass().getTypeName().contentEquals("java.util.Date")) {
						/* we must avoid automatic timezone transfer, so we get the clean utc value as string and parse it to a local date time */
						String s_utcValue = net.forestany.forestj.lib.Helper.utilDateToISO8601UTC( java.util.Date.class.cast(o_entry.getValue())).replace("Z", "");
						java.time.LocalDateTime o_localDateTime = net.forestany.forestj.lib.Helper.fromDateTimeString(s_utcValue);
						
						/* check explicit for a time value, starting with '1970-01-01' */
						if (s_utcValue.startsWith("1970-01-01")) { /* we have a time value */
							/* create java.sql.Time object with local date time's local time value */
							if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with java.sql.Time.valueOf + java.util.Date.class.cast + net.forestany.forestj.lib.Helper.utilDateToISO8601UTC + net.forestany.forestj.lib.Helper.fromDateTimeString");
							o_row.put(s_column, java.sql.Time.valueOf( dtf_time_instance.format(o_localDateTime.toLocalTime()) ));
						} else { /* normal timestamp */
							/* create java.sql.Timestamp object with local date time value */
							if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with java.sql.Timestamp.valueOf + java.util.Date.class.cast + net.forestany.forestj.lib.Helper.utilDateToISO8601UTC + net.forestany.forestj.lib.Helper.fromDateTimeString");
							o_row.put(s_column, java.sql.Timestamp.valueOf( dtf_datetime_instance.format(o_localDateTime) ));
						}
					} else {
						b_excDate = true;
					}
					break;
				case "double":
					if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with Double.class.cast");
					o_row.put(s_column, Double.class.cast(o_entry.getValue()));
					break;
				case "decimal":
					if ( (o_entry.getValue() == null) || (net.forestany.forestj.lib.Helper.isStringEmpty(o_entry.getValue().toString())) || (o_entry.getValue().toString().contentEquals("NULL")) ) {
						if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("column value is NULL");
						o_row.put(s_column, null);
					} else {
						if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with java.math.BigDecimal");
						o_row.put(s_column, new java.math.BigDecimal(o_entry.getValue().toString()));
					}
					break;
				case "bool":
					if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with Boolean.class.cast");
					o_row.put(s_column, Boolean.class.cast(o_entry.getValue()));
					break;
				case "string":
				case "regex":
					if ( (o_entry.getValue() == null) || (net.forestany.forestj.lib.Helper.isStringEmpty(o_entry.getValue().toString())) || (o_entry.getValue().toString().contentEquals("NULL")) ) {
						if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("column value is NULL");
						o_row.put(s_column, null);
					} else {
						if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with String.class.cast");
						o_row.put(s_column, o_entry.getValue().toString());
					}
					break;
				case "null":
					if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("column value is NULL");
					o_row.put(s_column, null);
					break;
				default:
					b_exc = true;
					break;
			}
			
			if (b_exc) {
				throw new Exception("Invalid type '" + s_type + "' for column '" + s_column + "'");
			}
			
			if (b_excDate) {
				throw new Exception("Invalid value type '" + o_entry.getValue().getClass().getTypeName() + "' for column '" + s_column + "', expected 'java.util.Date'");
			}
		}
		
		return o_row;
	}

	/**
	 * Transpose insert commands to one simple insert command where autoincrement column value has already been incremented by other queries before that
	 * 
	 * @param p_a_insertCommands			list of bson document commands as insert commands
	 * @return								one simple insert command with incremented value
	 * @throws Exception					could not retrieve value of autoincrement column or value is not a numeric integer value
	 */
	private Document getInsertCommandWithAutoIncrement(java.util.List<Document> p_a_insertCommands) throws Exception {
		Document o_return = null;
		String s_autoIncrementColumn = null;
		
		/* iterate each command and execute it, getting bson document as result */
		for (Document o_command : p_a_insertCommands) {
			if (o_command.containsKey("autoincrement_collection")) {
				s_autoIncrementColumn = o_command.get("autoincrement_column").toString();
				
				/* create select query with autoincrement column, limit 1 and order by desc; fast query if there is an index on autoincrement column */
				net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select> o_querySelectMaxAutoIncrement = new net.forestany.forestj.lib.sql.Query<net.forestany.forestj.lib.sql.Select>(BaseGateway.NOSQLMDB, SqlType.SELECT, o_command.get("autoincrement_collection").toString());
				net.forestany.forestj.lib.sql.Column o_autoIcrementColumn = new net.forestany.forestj.lib.sql.Column(o_querySelectMaxAutoIncrement, s_autoIncrementColumn);
				o_querySelectMaxAutoIncrement.getQuery().a_columns.add(o_autoIcrementColumn);
				o_querySelectMaxAutoIncrement.getQuery().o_orderBy = new net.forestany.forestj.lib.sql.OrderBy(o_querySelectMaxAutoIncrement, java.util.Arrays.asList(o_autoIcrementColumn), java.util.Arrays.asList(false));
				o_querySelectMaxAutoIncrement.getQuery().o_limit = new net.forestany.forestj.lib.sql.Limit(o_querySelectMaxAutoIncrement, 0 , 1);
				
				/* transpose sql query to nosqlmdb command */
				java.util.List<Document> a_autoIncrementCommands = BaseNoSQLMDBTranspose.transpose(o_querySelectMaxAutoIncrement);
				
				/* log nosqlmdb commands */
				for (Document o_commandTemp : a_autoIncrementCommands) {
					net.forestany.forestj.lib.Global.ilogFiner(o_commandTemp.toBsonDocument().toJson());
				}
				
				/* check if we get only one nosqlmdb command */
				if (a_autoIncrementCommands.size() != 1) {
					throw new Exception("Nosqlmdb command for autoincrement query has multiple commands '" + a_autoIncrementCommands.size() + "', not '1'");
				}
				
														if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("execute command");
				
				/* execute nosqlmdb command */
				Document o_autoIncrementResult = this.o_nosqlmdbDatabase.runCommand(a_autoIncrementCommands.get(0));
				
														if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("command executed");
				
				/* check if nosqlmdb command returns a valid result */
				if ( (!o_autoIncrementResult.containsKey("ok")) && ((Double)o_autoIncrementResult.get("ok") != 1.0d) ) {
					throw new Exception("Nosqlmdb command has no value for result key 'ok' or result key's value 'ok' is not '1.0'");
				}
				
				this.logCommandResults(( (Document)o_autoIncrementResult.get("cursor") ));
				
				@SuppressWarnings("unchecked")
				java.util.List<Document> a_result = ( (java.util.List<Document>)( (Document)o_autoIncrementResult.get("cursor") ).get("firstBatch") );
				
				if (a_result.size() < 1) {
					this.i_lastInsertId = 0;
				} else {
					String s_maybeAnInteger = ( (Document)a_result.get(0) ).get(s_autoIncrementColumn).toString();
					
					if (net.forestany.forestj.lib.Helper.isInteger(s_maybeAnInteger)) {
						this.i_lastInsertId = Integer.valueOf(s_maybeAnInteger);
					} else {
						throw new Exception("Autoincrement column '" + s_autoIncrementColumn + "' does not contain numeric integer values. Value '" + s_maybeAnInteger + "' cannot be incremented");
					}
				}
				
				this.i_lastInsertId++;
			} else {
				if ( (s_autoIncrementColumn != null) && (this.i_lastInsertId > 0) ) {
					@SuppressWarnings("unchecked")
					java.util.List<Document> a_insertDocuments = (java.util.List<Document>)o_command.get("documents");
					
					for (Document o_document : a_insertDocuments) {
						o_document.replace(s_autoIncrementColumn, "FORESTJ_REPLACE_AUTOINCREMENT_VALUE", this.i_lastInsertId);
					}
					
															if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass(o_command.toBsonDocument().toJson());
				}
				
				/* take command as insert command return value */
				o_return = o_command;
			}
		}
		
		return o_return;
	}
	
	/**
	 * Sets manual auto commit value
	 * 
	 * @param p_b_autoCommit			true - auto commit is set, false - auto commit is not set so a whole transaction can start
	 * @throws IllegalAccessException	could not manipulate auto commit setting in current database connection
	 */
	public void manualAutoCommit(boolean p_b_autoCommit) throws IllegalAccessException {
		throw new IllegalAccessException("not implemented");
	}
	
	/**
	 * Manual commit current transaction
	 * 
	 * @throws IllegalAccessException	could not commit in current database connection
	 */
	public void manualCommit() throws IllegalAccessException {
		if (this.o_nosqlmdbSession != null) {
	        try {
	        	this.o_nosqlmdbSession.commitTransaction();
	        	
	        											net.forestany.forestj.lib.Global.ilogFinest("manual commit executed");
	        } catch (Exception o_exc) {
	        	throw new IllegalAccessException("Could not commit manual within nosqlmdb connection; " + o_exc.getMessage());
	        }
	    }
	}
	
	/**
	 * Manual rollback current transaction
	 * 
	 * @throws IllegalAccessException	could not rollback in current database connection 
	 */
	public void manualRollback() throws IllegalAccessException {
		if (this.o_nosqlmdbSession != null) {
	        try {
	        	this.o_nosqlmdbSession.abortTransaction();
	        	
	        											net.forestany.forestj.lib.Global.ilogFinest("manual rollback executed");
	        } catch (Exception o_exc) {
	        	throw new IllegalAccessException("Could not rollback manual within nosqlmdb connection;" + o_exc.getMessage());
	        }
	    }
	}
	
	/**
	 * Manual start a transaction
	 * 
	 * @throws IllegalAccessException	could not start a transaction in current database connection 
	 */
	public void manualStartTransaction() throws IllegalAccessException {
		if (this.o_nosqlmdbSession != null) {
	        try {
	        	this.o_nosqlmdbSession.startTransaction();
	        	
	        											net.forestany.forestj.lib.Global.ilogFinest("manual transaction started");
	        } catch (Exception o_exc) {
	        	throw new IllegalAccessException("Could not start transaction manual within nosqlmdb connection;" + o_exc.getMessage());
	        }
	    }
	}
}
