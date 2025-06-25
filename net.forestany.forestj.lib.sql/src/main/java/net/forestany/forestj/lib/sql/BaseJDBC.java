package net.forestany.forestj.lib.sql;

import net.forestany.forestj.lib.sqlcore.Base;
import net.forestany.forestj.lib.sqlcore.BaseGateway;
import net.forestany.forestj.lib.sqlcore.IQuery;
import net.forestany.forestj.lib.sqlcore.SqlType;

/**
 * BaseJDBC class for any kind of sql connection to a database and fetching queries to it.
 * requires JDBC.
 * supported databases: @see BaseGateway
 */
public class BaseJDBC extends Base {
	
	/* Fields */
	
	/**
	 * current connection
	 */
	protected java.sql.Connection o_currentConnection = null;
	/**
	 * current prepared statement
	 */
	protected java.sql.PreparedStatement o_currentPreparedStatement = null;
	/**
	 * current result
	 */
	protected java.sql.ResultSet o_currentResult = null;
	/**
	 * current result metadata
	 */
	protected java.sql.ResultSetMetaData o_currentResultMetaData = null;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * BaseJDBC constructor, initiating database server connection
	 * 
	 * @param p_e_baseGateway				enumeration value of BaseGateway class, specifies which database interface will be used with JDBC
	 * @param p_s_host						address of database server, optional port specification e.g. 'localhost:3306'
	 * @throws IllegalArgumentException		illegal database gateway or illegal host parameter
	 * @throws IllegalAccessException		error creating database server connection
	 */
	protected BaseJDBC(BaseGateway p_e_baseGateway, String p_s_host) throws IllegalArgumentException, IllegalAccessException {
		this(p_e_baseGateway, p_s_host, "");
	}
	
	/**
	 * BaseJDBC constructor, initiating database server connection
	 * 
	 * @param p_e_baseGateway				enumeration value of BaseGateway class, specifies which database interface will be used with JDBC
	 * @param p_s_host						address of database server, optional port specification e.g. 'localhost:3306'
	 * @param p_s_datasource				parameter for selecting a database within target database server
	 * @throws IllegalArgumentException		illegal database gateway or illegal host parameter
	 * @throws IllegalAccessException		error creating database server connection
	 */
	protected BaseJDBC(BaseGateway p_e_baseGateway, String p_s_host, String p_s_datasource) throws IllegalArgumentException, IllegalAccessException {
		this(p_e_baseGateway, p_s_host, p_s_datasource, "", "");
	}
	
	/**
	 * BaseJDBC constructor, initiating database server connection
	 * 
	 * @param p_e_baseGateway				enumeration value of BaseGateway class, specifies which database interface will be used with JDBC
	 * @param p_s_host						address of database server, optional port specification e.g. 'localhost:3306'
	 * @param p_s_datasource				parameter for selecting a database within target database server
	 * @param p_s_user						user name for database login
	 * @throws IllegalArgumentException		illegal database gateway or illegal host parameter
	 * @throws IllegalAccessException		error creating database server connection
	 */
	protected BaseJDBC(BaseGateway p_e_baseGateway, String p_s_host, String p_s_datasource, String p_s_user) throws IllegalArgumentException, IllegalAccessException {
		this(p_e_baseGateway, p_s_host, p_s_datasource, p_s_user, "");
	}
	
	/**
	 * BaseJDBC constructor, initiating database server connection
	 * 
	 * @param p_e_baseGateway				enumeration value of BaseGateway class, specifies which database interface will be used with JDBC
	 * @param p_s_host						address of database server, optional port specification e.g. 'localhost:3306'
	 * @param p_s_datasource				parameter for selecting a database within target database server
	 * @param p_s_user						user name for database login
	 * @param p_s_password					user's password for database login
	 * @throws IllegalArgumentException		illegal database gateway or illegal host parameter
	 * @throws IllegalAccessException		error creating database server connection
	 */
	protected BaseJDBC(BaseGateway p_e_baseGateway, String p_s_host, String p_s_datasource, String p_s_user, String p_s_password) throws IllegalArgumentException, IllegalAccessException {
		if (p_e_baseGateway == null) {
			throw new IllegalArgumentException("No base-gateway was selected");
		}
		
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_host)) {
			throw new IllegalArgumentException("No base-host/base-file was selected");
		}
		
		String s_temp_user = null;
		String s_temp_pw = null;
		String s_temp_connectionUrl = null;
		
		this.e_baseGateway = p_e_baseGateway;
		
        /* initiate server connection */
		if ((!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_user)) && (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_password))) {
			s_temp_user = p_s_user;
			s_temp_pw = p_s_password;
		} else if ((!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_user)) && (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_password))) {
			s_temp_user = p_s_user;
			s_temp_pw = null;
		}
		
		/* choose database gateway and create new connection object with connection settings */
		try {
			boolean b_exc = false;
			
			switch (this.e_baseGateway) {
				case MARIADB:
					/* jdbc:mariadb://localhost:3306/DB?user=root&password=myPassword */
					s_temp_connectionUrl = "jdbc:mariadb://" + p_s_host + "/" + p_s_datasource;
					
					if (s_temp_user != null) {
						s_temp_connectionUrl += "?user=" + s_temp_user;
						
						if (s_temp_pw != null) {
							s_temp_connectionUrl += "&password=" + "%forestJpassword%";
						}
					}
				break;
				case MSSQL:
					/* jdbc:sqlserver://server\\instance;databaseName=DB;user=root;password=myPassword;encrypt=true;trustServerCertificate=true */
					
					/* check if we want to use encryption and trust server certificate */
					if (p_s_host.endsWith(";trustServerCertificate")) {
						/* 'remove trustServerCertificate' at the end */
						p_s_host = p_s_host.substring(0, p_s_host.lastIndexOf(";trustServerCertificate"));
						
						/* create connection url with additional parameters for encryption - we always trust server certificate, truststore option is not implemented now */
						s_temp_connectionUrl = "jdbc:sqlserver://" + p_s_host + ";databaseName=" + p_s_datasource + ";encrypt=true;trustServerCertificate=true";
					} else {
						s_temp_connectionUrl = "jdbc:sqlserver://" + p_s_host + ";databaseName=" + p_s_datasource;
					}
					
					if (s_temp_user != null) {
						s_temp_connectionUrl += ";user=" + s_temp_user;
						
						if (s_temp_pw != null) {
							s_temp_connectionUrl += ";password=" + "%forestJpassword%";
						}
					}
				break;
				case PGSQL:
					/* jdbc:postgresql:[//host[:port]/][database][?property1=value1[&property2=value2]...] */
					s_temp_connectionUrl = "jdbc:postgresql://" + p_s_host + "/" + p_s_datasource;
					
					if (s_temp_user != null) {
						s_temp_connectionUrl += "?user=" + s_temp_user;
						
						if (s_temp_pw != null) {
							s_temp_connectionUrl += "&password=" + "%forestJpassword%";
						}
					}
				break;
				case SQLITE:
					/* jdbc:sqlite:C:/filepath/sqlite.db */
					s_temp_connectionUrl = "jdbc:sqlite:" + p_s_host;
					s_tempConnectionString = s_temp_connectionUrl;
				break;
				case ORACLE:
					/* jdbc:oracle:thin:user/password@localhost:1521:SID */
					/* jdbc:oracle:thin:user/password@localhost:1521/ServiceName */
					
					s_temp_connectionUrl = "jdbc:oracle:thin";
					
					if (s_temp_user != null) {
						s_temp_connectionUrl += ":" + s_temp_user;
					
						if (s_temp_pw != null) {
							s_temp_connectionUrl += "/" + "%forestJpassword%";
						}
						
						s_temp_connectionUrl += "@";
					}
					
					s_temp_connectionUrl += p_s_host + p_s_datasource;
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
			
			this.o_currentConnection = java.sql.DriverManager.getConnection(s_temp_connectionUrl);
			
													net.forestany.forestj.lib.Global.ilogConfig("database connection to '" + this.e_baseGateway + "' established");
		} catch (java.sql.SQLException o_exc) {
			throw new IllegalAccessException("The connection to the database is not possible; SQLState=" + o_exc.getSQLState() + "; ErrorCode=" + o_exc.getErrorCode() + "; Message=" + o_exc.getMessage());
		}
	}
	
	/**
	 * Test connection to database with current connection
	 * 
	 * @return true - connection successful, false - connection failed
	 */
	public boolean testConnection() {
		try {
			String s_testConnectionQuery = null;
			
			if (this.e_baseGateway == BaseGateway.MARIADB) {
				this.o_currentPreparedStatement = this.o_currentConnection.prepareStatement(s_testConnectionQuery = "SELECT 1");
														if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("test connection with '" + s_testConnectionQuery + "'");
				java.sql.ResultSet o_tempResult = this.o_currentPreparedStatement.executeQuery();

			    if (o_tempResult.next()) {
			    	return true;
			    }
			} else if (this.e_baseGateway == BaseGateway.MSSQL) {
				this.o_currentPreparedStatement = this.o_currentConnection.prepareStatement(s_testConnectionQuery = "SELECT 1");
														if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("test connection with '" + s_testConnectionQuery + "'");
				java.sql.ResultSet o_tempResult = this.o_currentPreparedStatement.executeQuery();

			    if (o_tempResult.next()) {
			    	return true;
			    }
			} else if (this.e_baseGateway == BaseGateway.SQLITE) {
				this.o_currentPreparedStatement = this.o_currentConnection.prepareStatement(s_testConnectionQuery = "SELECT 1"); /* or 'SELECT name FROM sqlite_schema' */
														if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("test connection with '" + s_testConnectionQuery + "'");
				java.sql.ResultSet o_tempResult = this.o_currentPreparedStatement.executeQuery();

			    if (o_tempResult.next()) {
			    	return true;
			    }
			} else if (this.e_baseGateway == BaseGateway.PGSQL) {
				this.o_currentPreparedStatement = this.o_currentConnection.prepareStatement(s_testConnectionQuery = "SELECT 1");
														if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("test connection with '" + s_testConnectionQuery + "'");
				java.sql.ResultSet o_tempResult = this.o_currentPreparedStatement.executeQuery();

			    if (o_tempResult.next()) {
			    	return true;
			    }
			} else if (this.e_baseGateway == BaseGateway.ORACLE) {
				this.o_currentPreparedStatement = this.o_currentConnection.prepareStatement(s_testConnectionQuery = "SELECT 1 FROM DUAL");
														if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("test connection with '" + s_testConnectionQuery + "'");
				java.sql.ResultSet o_tempResult = this.o_currentPreparedStatement.executeQuery();

				if (o_tempResult.next()) {
			    	return true;
			    }
			}
			
			return true;
		} catch (java.sql.SQLException o_exc) {
			return false;
		}
	}
	
	/**
	 * Check if current connection is not closed
	 * 
	 * @return true - connection is still valid, false - connection closed, a new one must be established to continue database communication
	 */
	public boolean isClosed() {
		try {
			return this.o_currentConnection.isClosed();
		} catch (java.sql.SQLException o_exc) {
			return false;
		}	
	}
	
	/**
	 * Method for closing database connection, ignoring any sql exceptions
	 */
	public void closeConnection() {
		if (this.o_currentResult != null) {
	        try { this.o_currentResult.close(); } catch (java.sql.SQLException o_exc) { /* ignored */ }
	    }
		
	    if (this.o_currentPreparedStatement != null) {
	        try { this.o_currentPreparedStatement.close(); } catch (java.sql.SQLException o_exc) { /* ignored */ }
	    }
	    
	    if (this.o_currentConnection != null) {
	        try { this.o_currentConnection.close(); } catch (java.sql.SQLException o_exc) { /* ignored */ }
	    }
	    
	    										net.forestany.forestj.lib.Global.ilogConfig("database connection closed");
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
		int i_affectedRows = -1;
		
		/* check if query BaseGateway matches BaseGateway of current connection */
		if (p_o_sqlQuery.getBaseGateway() != this.e_baseGateway) {
			throw new IllegalAccessException("Query has invalid BaseGateway setting: '" + p_o_sqlQuery.getBaseGateway() + "(query)' != '" + this.e_baseGateway + "(base)'");
		}
		
		/* convert sql query object to string and execute sql query */
		this.s_query = p_o_sqlQuery.toString();
		
		/* remove query separator at the end of query string */
		if (this.s_query.endsWith(((Query<?>) p_o_sqlQuery).getQuerySeparator())) {
			this.s_query = this.s_query.substring(0, this.s_query.length() - ((Query<?>) p_o_sqlQuery).getQuerySeparator().length());
		}
		
		try {
			/* date time formatter instance with datetime format for later use */
			java.time.format.DateTimeFormatter dtf_datetime_instance = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			/* date time formatter instance with date format for later use */
			java.time.format.DateTimeFormatter dtf_date_instance = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");

			/* date time formatter instance with time format for later use */
			java.time.format.DateTimeFormatter dtf_time_instance = java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss");

			/* if transaction flag is set, begin transaction */
			if (p_b_autoTransaction) {
				this.o_currentConnection.setAutoCommit(false);
			}
			
			/* check if we have multiple queries in one statement */
			String[] a_queries = this.s_query.split(((Query<?>) p_o_sqlQuery).getQuerySeparator());
			
			/* execute every query one by one */
			for (int i = 0; i < a_queries.length; i++) {
				if (!net.forestany.forestj.lib.Helper.isStringEmpty(a_queries[i])) {
					/* fetch user input values out of query for prepared statement and safe execution */
					java.util.List<java.util.AbstractMap.SimpleEntry<String, Object>> a_values = new java.util.ArrayList<java.util.AbstractMap.SimpleEntry<String, Object>>();
					String s_queryFoo = net.forestany.forestj.lib.sql.Query.convertToPreparedStatementQuery(this.e_baseGateway, a_queries[i].toString(), a_values, false);
					
					/* store query for exception purpose */
					this.s_query = net.forestany.forestj.lib.sql.Query.convertPreparedStatementSqlQueryToStandard(s_queryFoo, a_values);
					
					/* log whole query on finer level - warning has been written in the log as setting this flag true, because with insecure log access this is a vulnerability! */
					if (net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) {
						net.forestany.forestj.lib.Global.ilogFiner("sql query: '" + this.s_query + "'");
					}
					
					/* if last query with SQLITE is 'VACUUM' we must commit our transaction first and reopen our connection */
					if ( (p_b_autoTransaction) && (this.e_baseGateway == BaseGateway.SQLITE) && (i == (a_queries.length - 1)) && (a_queries[i].toString().contentEquals("VACUUM")) ) {
						/* commit our transaction */
						this.o_currentConnection.commit();
						this.o_currentConnection.setAutoCommit(true);
						
																net.forestany.forestj.lib.Global.ilogConfig("reopen database connection for '" + this.e_baseGateway + "', because 'VACUUM' has been used");
						
						/* reopen our connection */
						this.closeConnection();
						this.o_currentConnection = java.sql.DriverManager.getConnection(this.s_tempConnectionString);
						
						/* no auto transaction for 'VACUUM' query */
						p_b_autoTransaction = false;
					}
					
					/* prepare query */
					this.o_currentPreparedStatement = this.o_currentConnection.prepareStatement(s_queryFoo);
					
					/* pass values to sql statement */
					for (int j = 0; j < a_values.size(); j++) {
						String s_type = a_values.get(j).getKey();
						Object o_foo = a_values.get(j).getValue();
						
						if (s_type.contentEquals("boolean")) {
							net.forestany.forestj.lib.Global.ilogFinest("add 'boolean' value to prepared statement" + ((net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? " [" + o_foo.toString() + "]" : ""));
							this.o_currentPreparedStatement.setBoolean(j + 1, Boolean.class.cast(o_foo));
						} else if (s_type.contentEquals("short")) {
							net.forestany.forestj.lib.Global.ilogFinest("add 'short' value to prepared statement" + ((net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? " [" + o_foo.toString() + "]" : ""));
							this.o_currentPreparedStatement.setShort(j + 1, Short.class.cast(o_foo));
						} else if (s_type.contentEquals("integer")) {
							net.forestany.forestj.lib.Global.ilogFinest("add 'integer' value to prepared statement" + ((net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? " [" + o_foo.toString() + "]" : ""));
							this.o_currentPreparedStatement.setInt(j + 1, Integer.class.cast(o_foo));
						} else if (s_type.contentEquals("long")) {
							net.forestany.forestj.lib.Global.ilogFinest("add 'long' value to prepared statement" + ((net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? " [" + o_foo.toString() + "]" : ""));
							this.o_currentPreparedStatement.setLong(j + 1, Long.class.cast(o_foo));
						} else if (s_type.contentEquals("double")) {
							net.forestany.forestj.lib.Global.ilogFinest("add 'double' value to prepared statement" + ((net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? " [" + o_foo.toString() + "]" : ""));
							this.o_currentPreparedStatement.setDouble(j + 1, Double.class.cast(o_foo));
						} else if (s_type.contentEquals("float")) {
							net.forestany.forestj.lib.Global.ilogFinest("add 'float' value to prepared statement" + ((net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? " [" + o_foo.toString() + "]" : ""));
							this.o_currentPreparedStatement.setFloat(j + 1, Float.class.cast(o_foo));
						} else if (s_type.contentEquals("bigdecimal")) {
							net.forestany.forestj.lib.Global.ilogFinest("add 'bigdecimal' value to prepared statement" + ((net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? " [" + o_foo.toString() + "]" : ""));
							this.o_currentPreparedStatement.setBigDecimal(j + 1, java.math.BigDecimal.class.cast(o_foo));
						} else if (s_type.contentEquals("localdatetime")) {
							net.forestany.forestj.lib.Global.ilogFinest("add 'localdatetime' value to prepared statement" + ((net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? " [" + o_foo.toString() + "]" : ""));
							
							/* use setTimestamp for ORACLE */
							if (this.e_baseGateway == BaseGateway.ORACLE) {
								this.o_currentPreparedStatement.setTimestamp(j + 1, java.sql.Timestamp.valueOf( dtf_datetime_instance.format((java.time.LocalDateTime)o_foo) ));
							} else {
								this.o_currentPreparedStatement.setObject(j + 1, (java.time.LocalDateTime)o_foo);
							}
						} else if (s_type.contentEquals("localdate")) {
							net.forestany.forestj.lib.Global.ilogFinest("add 'localdate' value to prepared statement" + ((net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? " [" + o_foo.toString() + "]" : ""));
							
							if (this.e_baseGateway == BaseGateway.ORACLE) {
								this.o_currentPreparedStatement.setDate(j + 1, java.sql.Date.valueOf( dtf_date_instance.format((java.time.LocalDate)o_foo) ));
							} else {
								this.o_currentPreparedStatement.setObject(j + 1, (java.time.LocalDate)o_foo);
							}
						} else if (s_type.contentEquals("localtime")) {
							net.forestany.forestj.lib.Global.ilogFinest("add 'localtime' value to prepared statement" + ((net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? " [" + o_foo.toString() + "]" : ""));
							
							/* MSSQL receive time values as string only */
							if (this.e_baseGateway == BaseGateway.MSSQL) {
								this.o_currentPreparedStatement.setString(j + 1, o_foo.toString());
							} else if (this.e_baseGateway == BaseGateway.ORACLE) {
								this.o_currentPreparedStatement.setTime(j + 1, java.sql.Time.valueOf( dtf_time_instance.format((java.time.LocalTime)o_foo) ));
							} else {
								this.o_currentPreparedStatement.setObject(j + 1, (java.time.LocalTime)o_foo);
							}
						} else if (s_type.contentEquals("string")) {
							net.forestany.forestj.lib.Global.ilogFinest("add 'string' value to prepared statement" + ((net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? " [" + o_foo.toString() + "]" : ""));
							
							if ( (o_foo.toString().compareTo("true") == 0) || (o_foo.toString().compareTo("false") == 0) ) {
								this.o_currentPreparedStatement.setBoolean(j + 1, Boolean.parseBoolean(o_foo.toString()));
							} else if (o_foo.toString().contentEquals("NULL")) {
								this.o_currentPreparedStatement.setObject(j + 1, null);
							} else {
								this.o_currentPreparedStatement.setString(j + 1, o_foo.toString());
							}
						} else {
							net.forestany.forestj.lib.Global.ilogFinest("add 'object' value to prepared statement" + ((net.forestany.forestj.lib.Global.get().getLogCompleteSqlQuery()) ? " [" + o_foo.toString() + "]" : ""));
							this.o_currentPreparedStatement.setObject(j + 1, o_foo);
						}
					}
					
					/* execute query */
					if (p_o_sqlQuery.getSqlType() == SqlType.SELECT) {
																if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("execute prepared statement");
						this.o_currentResult = this.o_currentPreparedStatement.executeQuery();
																if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("prepared statement executed");
					} else {
						try {
																	if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("execute prepared update statement");
							i_affectedRows = this.o_currentPreparedStatement.executeUpdate();
																	if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("prepared update statement executed; affected rows: " + i_affectedRows);
						} catch (java.sql.SQLException o_exc) {
							/* sqlite and alter query only support executeQuery method */
							if ( (this.e_baseGateway == BaseGateway.SQLITE) && (p_o_sqlQuery.getSqlType() == SqlType.ALTER) ) {
																		if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("execute prepared alter statement for " + this.e_baseGateway);
								this.o_currentResult = this.o_currentPreparedStatement.executeQuery();
																		if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("prepared alter statement executed for " + this.e_baseGateway);
							} else {
								throw o_exc;
							}
						} finally {
							b_ignoreResult = true;
						}
					}
				} else {
					net.forestany.forestj.lib.Global.ilogFine("empty query string skipped, query #" + i);
				}
			}
		} catch (java.sql.SQLException o_exc) {
			/* if transaction flag is set, roll-back transaction */
			if (p_b_autoTransaction) {
				try {
					this.o_currentConnection.rollback();
					this.o_currentConnection.setAutoCommit(true);
				} catch (java.sql.SQLException o_excRollback) {
					throw new IllegalAccessException("Could not rollback transaction; SQLState=" + o_excRollback.getSQLState() + "; ErrorCode=" + o_excRollback.getErrorCode() + "; Message=" + o_excRollback.getMessage() + " - - - " + "The query could not be executed; SQLState=" + o_exc.getSQLState() + "; ErrorCode=" + o_exc.getErrorCode() + "; Message=" + o_exc.getMessage() + "; Query=" + this.s_query);
				}
			}
			
			throw new IllegalAccessException("The query could not be executed; SQLState=" + o_exc.getSQLState() + "; ErrorCode=" + o_exc.getErrorCode() + "; Message=" + o_exc.getMessage() + "; Query=" + this.s_query);
		}
		
		/* check if query got a result */
		if ( (this.o_currentResult == null) && (!b_ignoreResult) ) {
			/* if transaction flag is set, roll-back transaction */
			if (p_b_autoTransaction) {
				try {
					this.o_currentConnection.rollback();
					this.o_currentConnection.setAutoCommit(true);
				} catch (java.sql.SQLException o_excRollback) {
					throw new IllegalAccessException("Could not rollback transaction; SQLState=" + o_excRollback.getSQLState() + "; ErrorCode=" + o_excRollback.getErrorCode() + "; Message=" + o_excRollback.getMessage() + " - - - " + "The query could not be executed or has no valid result.");
				}
			}
			
			throw new IllegalAccessException("The query could not be executed or has no valid result.");
		}
		
		/* on SELECT query, prepare to return result rows */
		if (p_o_sqlQuery.getSqlType() == SqlType.SELECT) {
			try {
														if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get select meta data");
				this.o_currentResultMetaData = this.o_currentResult.getMetaData();
			
				/* fetch sql result into hash map array */
				while (this.o_currentResult.next()) {
															if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("fetch row");
					a_rows.add(this.fetchRow());
				}
			} catch (java.sql.SQLException o_exc) {
				throw new IllegalAccessException("Could not fetch row, issue get metadata and column information; SQLState=" + o_exc.getSQLState() + "; ErrorCode=" + o_exc.getErrorCode() + "; Message=" + o_exc.getMessage() + "; Query=" + this.s_query);
			}
		} else {
			String s_lastInsertIdQuery = "this.o_currentPreparedStatement.getUpdateCount()";
			
			try {
														if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get update count");
				
				/* returning amount of affected rows by sql query (and last inserted id if insert statement) */
				java.util.LinkedHashMap<String, Object> o_row = new java.util.LinkedHashMap<String, Object>();
				
				if (i_affectedRows >= 0) {
					o_row.put("AffectedRows", i_affectedRows);
				} else {
					o_row.put("AffectedRows", this.o_currentPreparedStatement.getUpdateCount());
				}
				
														net.forestany.forestj.lib.Global.ilogFinest("update count is '" + o_row.get("AffectedRows") + "'");
				
				int i_lastInsertId = -1;
				
				/* differ by each database gateway for returning last inserted id, some are simple with jdbc support, others are very complex */
				if ( (p_o_sqlQuery.getSqlType() == SqlType.INSERT) && (!this.b_skipQueryLastInsertId) ) {
					if (this.e_baseGateway == BaseGateway.MARIADB) {
						this.o_currentPreparedStatement = this.o_currentConnection.prepareStatement(s_lastInsertIdQuery = "SELECT LAST_INSERT_ID()");
																if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get last insert id with '" + s_lastInsertIdQuery + "'");
						java.sql.ResultSet o_tempResult = this.o_currentPreparedStatement.executeQuery();
	
					    if (o_tempResult.next()) {
					    	i_lastInsertId = o_tempResult.getInt(1);
					    }
					} else if (this.e_baseGateway == BaseGateway.MSSQL) {
						this.o_currentPreparedStatement = this.o_currentConnection.prepareStatement(s_lastInsertIdQuery = "SELECT IDENT_CURRENT('" + p_o_sqlQuery.getTable() + "')");
																if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get last insert id with '" + s_lastInsertIdQuery + "'");
						java.sql.ResultSet o_tempResult = this.o_currentPreparedStatement.executeQuery();
	
					    if (o_tempResult.next()) {
					    	i_lastInsertId = o_tempResult.getInt(1);
					    }
					} else if (this.e_baseGateway == BaseGateway.SQLITE) {
						this.o_currentPreparedStatement = this.o_currentConnection.prepareStatement(s_lastInsertIdQuery = "SELECT last_insert_rowid()");
																if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get last insert id with '" + s_lastInsertIdQuery + "'");
						java.sql.ResultSet o_tempResult = this.o_currentPreparedStatement.executeQuery();
	
					    if (o_tempResult.next()) {
					    	i_lastInsertId = o_tempResult.getInt(1);
					    }
					} else if (this.e_baseGateway == BaseGateway.PGSQL) {
						this.o_currentPreparedStatement = this.o_currentConnection.prepareStatement(s_lastInsertIdQuery = "SELECT LASTVAL()");
																if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get last insert id with '" + s_lastInsertIdQuery + "'");
						java.sql.ResultSet o_tempResult = this.o_currentPreparedStatement.executeQuery();
	
					    if (o_tempResult.next()) {
					    	i_lastInsertId = o_tempResult.getInt(1);
					    }
					} else if (this.e_baseGateway == BaseGateway.ORACLE) {
						/* we need to find the column in ORACLE with has a sequence */
						this.o_currentPreparedStatement = this.o_currentConnection.prepareStatement(s_lastInsertIdQuery = "SELECT DATA_DEFAULT FROM USER_TAB_COLUMNS WHERE TABLE_NAME = '" + p_o_sqlQuery.getTable() + "'");
						java.sql.ResultSet o_tempResult = this.o_currentPreparedStatement.executeQuery();
	
						String s_sequence = null;
						
						/* iterate each column result */
						while (o_tempResult.next()) {
							String s_columnDefaultValue = o_tempResult.getString(1);
							
							/* sequence ends with '.nextval' */
					    	if (s_columnDefaultValue.toLowerCase().endsWith(".nextval")) {
					    		/* get sequence name, e.g. "SYSTEM"."ISEQ$$_74013".nextval */
					    		s_sequence = s_columnDefaultValue.replace(".nextval", "");
					    		/* we always consider that the first column in table with a sequence is a value we want to have as last inserted identifier value */
					    		break;
					    	}
					    }
						
						/* continue if a sequence name has been found */
						if (s_sequence != null) {
							/* get CURRVAL of found sequence */
							this.o_currentPreparedStatement = this.o_currentConnection.prepareStatement(s_lastInsertIdQuery = "SELECT " + s_sequence + ".CURRVAL FROM DUAL");
																	if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get last insert id with '" + s_lastInsertIdQuery + "'");
							o_tempResult = this.o_currentPreparedStatement.executeQuery();
	
							/* sequence must be of type integer/number */
						    if (o_tempResult.next()) {
						    	i_lastInsertId = o_tempResult.getInt(1);
						    }
						}
					}
					
															net.forestany.forestj.lib.Global.ilogFinest("store LastInsertId '" + i_lastInsertId + "' in result row");
					o_row.put("LastInsertId", i_lastInsertId);
				}
				
														if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("add row object to result list");
				a_rows.add(o_row);
			} catch (java.sql.SQLException o_exc) {
				/* do not throw exception here, maybe only log this information */
				System.err.println("Could not count affected rows or could not fetch last insert id; SQLState=" + o_exc.getSQLState() + "; ErrorCode=" + o_exc.getErrorCode() + "; Message=" + o_exc.getMessage() + "; Query=" + s_lastInsertIdQuery);
			}
		}
		
		/* if transaction flag is set, commit transaction */
		if (p_b_autoTransaction) {
			try {
				this.o_currentConnection.commit();
				this.o_currentConnection.setAutoCommit(true);
			} catch (java.sql.SQLException o_exc) {
				throw new IllegalAccessException("Could not commit transaction; SQLState=" + o_exc.getSQLState() + "; ErrorCode=" + o_exc.getErrorCode() + "; Message=" + o_exc.getMessage());
			}
		}
		
		return a_rows;
	}
	
	/**
	 * Method to create a record hash map of current result set record
	 * 
	 * @return							record hash map, key(string) -> column name + value(object) -> column value
	 * @throws java.sql.SQLException	exception accessing column type, column name or just column value of current result set record
	 */
	private java.util.LinkedHashMap<String, Object> fetchRow() throws java.sql.SQLException {
		java.util.LinkedHashMap<String, Object> o_row = new java.util.LinkedHashMap<String, Object>();
		
		/* date time formatter instance with datetime format for later use */
		java.time.format.DateTimeFormatter dtf_datetime_instance = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		/* date time formatter instance with time format for later use */
		java.time.format.DateTimeFormatter dtf_time_instance = java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss");

		for (int i = 0; i < this.o_currentResultMetaData.getColumnCount(); i++) {
			int i_type = this.o_currentResultMetaData.getColumnType(i + 1);
			String s_name = this.o_currentResultMetaData.getColumnName(i + 1);
			String s_type = this.o_currentResultMetaData.getColumnTypeName(i + 1);
			
													if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("column >>> " + s_name + " = " + s_type + "(" + this.o_currentResultMetaData.getPrecision(i + 1) + "," + this.o_currentResultMetaData.getScale(i + 1) + ") + " + i_type);
			
			/* unfortunately we need some oracle assignments for column types */
			if ((this.e_baseGateway == BaseGateway.ORACLE) && (i_type == 2) && (this.o_currentResultMetaData.getPrecision(i + 1) == 38) && (this.o_currentResultMetaData.getScale(i + 1) == 9)) {
				/* ORACLE: NUMBER(38,9) is a DECIMAL */
				i_type = java.sql.Types.DECIMAL;
			} else if ((this.e_baseGateway == BaseGateway.ORACLE) && (i_type == 2) && (this.o_currentResultMetaData.getPrecision(i + 1) == 38) && (this.o_currentResultMetaData.getScale(i + 1) == 0)) {
				/* ORACLE: NUMBER(38,0) is a INTEGER */
				i_type = java.sql.Types.INTEGER;
			} else if ((this.e_baseGateway == BaseGateway.ORACLE) && (i_type == 2) && (this.o_currentResultMetaData.getPrecision(i + 1) == 5) && (this.o_currentResultMetaData.getScale(i + 1) == 0)) {
				/* ORACLE: NUMBER(5,0) is a SMALLINT */
				i_type = java.sql.Types.SMALLINT;
			} else if ((this.e_baseGateway == BaseGateway.ORACLE) && (i_type == java.sql.Types.CHAR) && (this.o_currentResultMetaData.getPrecision(i + 1) == 1) && (this.o_currentResultMetaData.getScale(i + 1) == 0)) {
				/* ORACLE: CHAR(1,0) is a BOOLEAN */
				i_type = java.sql.Types.BOOLEAN;
			} else if ((this.e_baseGateway == BaseGateway.ORACLE) && (i_type == 2) && (this.o_currentResultMetaData.getPrecision(i + 1) == 126) && (this.o_currentResultMetaData.getScale(i + 1) == -127)) {
				/* ORACLE: NUMBER(126,-127) is a FLOAT */
				i_type = java.sql.Types.DOUBLE;
			} else if ((this.e_baseGateway == BaseGateway.ORACLE) && (i_type == 2)) {
				/* ORACLE: Any other NUMBER is a INTEGER */
				i_type = java.sql.Types.INTEGER;
			} else if ((this.e_baseGateway == BaseGateway.ORACLE) && (i_type == java.sql.Types.LONGVARCHAR)) {
				/* ORACLE: ANY other LONGVARCHAR is a BIGINT */
				i_type = java.sql.Types.BIGINT;
			} else if ((this.e_baseGateway == BaseGateway.ORACLE) && (i_type == 101)) {
				/* ORACLE: 101 is a DOUBLE */
				i_type = java.sql.Types.DOUBLE;
			}
			
			/* call the correct get-methods from current result into our row-pair list object */
			switch (i_type) {
				case java.sql.Types.TINYINT:
				case java.sql.Types.SMALLINT:
					if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with result.getShort");
					o_row.put(s_name, this.o_currentResult.getShort(s_name));
					break;
				case java.sql.Types.INTEGER:
				case java.sql.Types.ROWID:
					if ( (this.e_baseGateway == BaseGateway.SQLITE) && (s_type != null) && (s_type.equals("BIT")) ) {
						if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with result.getBoolean");
						o_row.put(s_name, this.o_currentResult.getBoolean(s_name));
					} else {
						if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with result.getInt");
						o_row.put(s_name, this.o_currentResult.getInt(s_name));
					}
					break;
				case java.sql.Types.BIGINT:
					if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with result.getLong");
					o_row.put(s_name, this.o_currentResult.getLong(s_name));
					break;
				case java.sql.Types.BIT:
				case java.sql.Types.BOOLEAN:
					if ( (this.e_baseGateway == BaseGateway.MARIADB) && (s_type != null) && (s_type.equals("BIT")) ) {
						if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with result.getBoolean");
						o_row.put(s_name, this.o_currentResult.getBoolean(s_name));
					} else {
						if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with result.getString");
						String s_foo = this.o_currentResult.getString(s_name);
						
						if (s_foo.contentEquals("1")) { /* read char '1' as true */
							o_row.put(s_name, true);
						} else { /* otherwise everything else as false */
							o_row.put(s_name, false);
						}
					}
					break;
				case java.sql.Types.CHAR:
				case java.sql.Types.VARCHAR:
				case java.sql.Types.NCHAR:
				case java.sql.Types.NVARCHAR:
				case java.sql.Types.LONGVARCHAR:
				case java.sql.Types.CLOB:
					if ( (this.e_baseGateway == BaseGateway.SQLITE) && (s_type != null) && (s_type.equals("TIME")) ) {
						String s_value = this.o_currentResult.getString(s_name);
						
						if ( (s_value == null) || (s_value.length() < 1) ) {
							if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("column value is NULL");
							o_row.put(s_name, null);
						} else if (net.forestany.forestj.lib.Helper.isDateTime(s_value)) {
							if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column TIME value with java.sql.Time.valueOf + net.forestany.forestj.lib.Helper.fromDateTimeString");
							o_row.put(s_name, java.sql.Time.valueOf( dtf_time_instance.format(net.forestany.forestj.lib.Helper.fromDateTimeString(s_value).toLocalTime()) ));
						} else if (net.forestany.forestj.lib.Helper.isTime(s_value)) {
							if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column TIME value with java.sql.Time.valueOf + net.forestany.forestj.lib.Helper.fromTimeString");
							o_row.put(s_name, java.sql.Time.valueOf( dtf_time_instance.format(net.forestany.forestj.lib.Helper.fromTimeString(s_value)) ));
						} else {
							throw new java.sql.SQLException("Invalid datetime or time format for SQLITE");
						}
					} else {
						if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with result.getString");
						o_row.put(s_name, this.o_currentResult.getString(s_name));
					}
					break;
				case java.sql.Types.DATE:
					if (this.e_baseGateway == BaseGateway.SQLITE) {
						String s_value = this.o_currentResult.getString(s_name);
						
						if ( (s_value == null) || (s_value.length() < 1) ) {
							if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("column value is NULL");
							o_row.put(s_name, null);
						} else if (net.forestany.forestj.lib.Helper.isDateTime(s_value)) {
							if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column DATETIME value with java.sql.Timestamp.valueOf + net.forestany.forestj.lib.Helper.fromDateTimeString");
							o_row.put(s_name, java.sql.Timestamp.valueOf( dtf_datetime_instance.format(net.forestany.forestj.lib.Helper.fromDateTimeString(s_value)) ));
						} else if (net.forestany.forestj.lib.Helper.isDate(s_value)) {
							if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column DATE value with java.sql.Timestamp.valueOf + java.time.LocalDateTime.of + net.forestany.forestj.lib.Helper.fromDateString");
							o_row.put(s_name, java.sql.Timestamp.valueOf( dtf_datetime_instance.format(java.time.LocalDateTime.of( net.forestany.forestj.lib.Helper.fromDateString(s_value), java.time.LocalTime.of(0, 0, 0))) ));
						} else {
							throw new java.sql.SQLException("Invalid datetime or date format for SQLITE");
						}
					} else {
						if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with result.getDate");
						o_row.put(s_name, this.o_currentResult.getDate(s_name));
					}
					break;
				case java.sql.Types.TIME:
				case -104: /* INTERVALDS for ORACLE */
					if (this.e_baseGateway == BaseGateway.SQLITE) {
						if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column TIME value with result.getString with support of java.sql.Time.valueOf + net.forestany.forestj.lib.Helper.fromTimeString");
						o_row.put(s_name, java.sql.Time.valueOf( dtf_time_instance.format(net.forestany.forestj.lib.Helper.fromTimeString(this.o_currentResult.getString(s_name))) ));
					} else if (this.e_baseGateway == BaseGateway.ORACLE) {
						/* it is complicated with ORACLE times */
						String s_temp = this.o_currentResult.getString(s_name);
						
						if ( (s_temp == null) || (s_temp.length() < 1) ) {
							if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("column value is NULL");
							o_row.put(s_name, null);
						} else {
							if (s_temp.contains(".")) {
								s_temp = s_temp.substring(0, s_temp.indexOf("."));
							}
							
							if (s_temp.contains(" ")) {
								s_temp = s_temp.substring(s_temp.indexOf(" ") + 1);
							}
							
							String[] a_foo = s_temp.split(":");
							int i_foo = 0;
							
							if (a_foo.length == 3) {
								if (a_foo[i_foo].length() == 2) {
									s_temp = a_foo[i_foo];
								} else {
									s_temp = "0" + a_foo[i_foo];
								}
								
								i_foo++;
								s_temp += ":";
								
								if (a_foo[i_foo].length() == 2) {
									s_temp += a_foo[i_foo];
								} else {
									s_temp += "0" + a_foo[i_foo];
								}
								
								i_foo++;
								s_temp += ":";
								
								if (a_foo[i_foo].length() == 2) {
									s_temp += a_foo[i_foo];
								} else {
									s_temp += "0" + a_foo[i_foo];
								}
							} else if (a_foo.length == 2) {
								if (a_foo[i_foo].length() == 2) {
									s_temp = a_foo[i_foo];
								} else {
									s_temp = "0" + a_foo[i_foo];
								}
								
								i_foo++;
								s_temp += ":";
								
								if (a_foo[i_foo].length() == 2) {
									s_temp += a_foo[i_foo];
								} else {
									s_temp += "0" + a_foo[i_foo];
								}
								
								s_temp += ":00";
							}
							
							if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with java.sql.Time.valueOf + net.forestany.forestj.lib.Helper.fromTimeString - oracle is complicated");
							o_row.put(s_name, java.sql.Time.valueOf( dtf_time_instance.format(net.forestany.forestj.lib.Helper.fromTimeString(s_temp)) ));
						}
					} else {
						if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with result.getTime");
						o_row.put(s_name, this.o_currentResult.getTime(s_name));
					}
					break;
				case java.sql.Types.TIMESTAMP:
					if (this.e_baseGateway == BaseGateway.SQLITE) {
						if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column TIMESTAMP value with result.getString with support of java.sql.Timestamp.valueOf + net.forestany.forestj.lib.Helper.fromDateTimeString");
						o_row.put(s_name, java.sql.Timestamp.valueOf( dtf_datetime_instance.format(net.forestany.forestj.lib.Helper.fromDateTimeString(this.o_currentResult.getString(s_name))) ));
					} else {
						if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with result.getTimestamp");
						o_row.put(s_name, this.o_currentResult.getTimestamp(s_name));
					}
					break;
				case java.sql.Types.DECIMAL:
				case java.sql.Types.NUMERIC:
					if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with result.getBigDecimal");
					o_row.put(s_name, this.o_currentResult.getBigDecimal(s_name));
					break;
				case java.sql.Types.DOUBLE:
					if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with result.getDouble");
					o_row.put(s_name, this.o_currentResult.getDouble(s_name));
					break;
				case java.sql.Types.REAL:
				case java.sql.Types.FLOAT:
					if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get column value with result.getFloat");
					o_row.put(s_name, this.o_currentResult.getFloat(s_name));
					break;
				case java.sql.Types.NULL:
				default:
					if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("column value is NULL");
					o_row.put(s_name, null);
					break;
			}
		}
		
		return o_row;
	}

	/**
	 * Sets manual auto commit value
	 * 
	 * @param p_b_autoCommit			true - auto commit is set, false - auto commit is not set so a whole transaction can start
	 * @throws IllegalAccessException	could not manipulate auto commit setting in current database connection
	 */
	public void manualAutoCommit(boolean p_b_autoCommit) throws IllegalAccessException {
		try {
			this.o_currentConnection.setAutoCommit(p_b_autoCommit);
			
													net.forestany.forestj.lib.Global.ilogFinest("sql auto commit: " + p_b_autoCommit);
		} catch (java.sql.SQLException o_excRollback) {
			throw new IllegalAccessException("Could not adjust auto commit; SQLState=" + o_excRollback.getSQLState() + "; ErrorCode=" + o_excRollback.getErrorCode() + "; Message=" + o_excRollback.getMessage());
		}
	}
	
	/**
	 * Manual commit current transaction
	 * 
	 * @throws IllegalAccessException	could not commit in current database connection
	 */
	public void manualCommit() throws IllegalAccessException {
		try {
			this.o_currentConnection.commit();
			
													net.forestany.forestj.lib.Global.ilogFinest("manual commit executed");
		} catch (java.sql.SQLException o_excRollback) {
			throw new IllegalAccessException("Could not commit transaction; SQLState=" + o_excRollback.getSQLState() + "; ErrorCode=" + o_excRollback.getErrorCode() + "; Message=" + o_excRollback.getMessage());
		}
	}
	
	/**
	 * Manual rollback current transaction
	 * 
	 * @throws IllegalAccessException	could not rollback in current database connection 
	 */
	public void manualRollback() throws IllegalAccessException {
		try {
			this.o_currentConnection.rollback();
			
													net.forestany.forestj.lib.Global.ilogFinest("manual rollback executed");
		} catch (java.sql.SQLException o_excRollback) {
			throw new IllegalAccessException("Could not rollback transaction; SQLState=" + o_excRollback.getSQLState() + "; ErrorCode=" + o_excRollback.getErrorCode() + "; Message=" + o_excRollback.getMessage());
		}
	}
}
