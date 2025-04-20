package net.forestany.forestj.lib.sql.pool;

import net.forestany.forestj.lib.sqlcore.BaseGateway;

/**
 * 
 * Base pool class for creating a pool of sql connections to a database and keeping these alive. Well known structure to have several connections ready for a service application.
 * Using Base class for sql connections and fetching queries. @see Base.
 * requires JDBC or NoSQL MDB java driver.
 * supported databases: @see BaseGateway.
 * 
 */
public class BasePool {
	
	/* Fields */
	
	private boolean b_running;
	private java.util.concurrent.ExecutorService o_executorServicePool;
	private int i_maxAmount;
	private net.forestany.forestj.lib.DateInterval o_timeoutInterval;
	private int i_timeoutMilliseconds;
	private java.util.List<BaseThread> a_baseThreads;
	private BaseGateway e_baseGateway;
	private String s_host;
	private String s_datasource;
	private String s_user;
	private String s_password;
	
	/* Properties */
	
	/**
	 * get base-gateway
	 * @return BaseGateway
	 */
	public BaseGateway getBaseGateway() {
		return this.e_baseGateway;
	}
	
	/**
	 * get max amount
	 * @return int
	 */
	public int getMaxAmount() {
		return this.i_maxAmount;
	}
	
	/**
	 * set max amount
	 * 
	 * @param p_i_value max amount of base pool threads
	 * @throws RuntimeException base pool is running
	 * @throws IllegalArgumentException max amount must be at least '1'
	 */
	public void setMaxAmount(int p_i_value) throws RuntimeException, IllegalArgumentException {
		/* check if base pool is already running */
		if (this.b_running ) {
			throw new RuntimeException("Base pool is running. Please stop base pool first");
		}
		
		/* check if max amount value will be at least '1' */
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Executor service pool max. amount must be at least '1', but was set to '" + p_i_value + "'");
		}
		
		this.i_maxAmount = p_i_value;
		
		if (this.a_baseThreads != null) {
			this.a_baseThreads.clear();
		}
		
		/* create new array of base threads, because max amount has been changed now */
		this.a_baseThreads = new java.util.ArrayList<BaseThread>();
	}
	
	/**
	 * get timeout interval
	 * 
	 * @return net.forestany.forestj.lib.DateInterval
	 */
	public net.forestany.forestj.lib.DateInterval getTimeoutInterval() {
		return this.o_timeoutInterval;
	}
	
	/**
	 * set timeout interval
	 * @param p_o_value timeout interval for base pool
	 * @throws RuntimeException base pool is running
	 * @throws IllegalArgumentException timeout interval parameter is null
	 */
	public void setTimeoutInterval(net.forestany.forestj.lib.DateInterval p_o_value) throws RuntimeException, IllegalArgumentException {
		/* check if base pool is already running */
		if (this.b_running) {
			throw new RuntimeException("Base pool is running. Please stop base pool first");
		}
		
		/* check if parameter is null */
		if (p_o_value == null) {
			throw new IllegalArgumentException("timeout interval parameter is null");
		}
		
		this.o_timeoutInterval = p_o_value;
	}
	
	/**
	 * get timeout milliseconds
	 * 
	 * @return int
	 */
	public int getTimeoutMilliseconds() {
		return this.i_timeoutMilliseconds;
	}
	
	/**
	 * set timeout milliseconds
	 * 
	 * @param p_i_value timeout milliseconds value
	 * @throws RuntimeException base pool is running
	 * @throws IllegalArgumentException timeout milliseconds must be at least '1'
	 */
	public void setTimeoutMilliseconds(int p_i_value) throws RuntimeException, IllegalArgumentException {
		/* check if base pool is already running */
		if (this.b_running) {
			throw new RuntimeException("Base pool is running. Please stop base pool first");
		}
		
		/* check if timeout in milliseconds value will be at least '1' */
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Timeout in milliseconds must be at least '1', but was set to '" + p_i_value + "'");
		}
		
		this.i_timeoutMilliseconds = p_i_value;
	}
	
	/* Methods */
	
	/**
	 * Constructor of base pool class, settings all parameters for sql connection to database + settings: timeout interval for keep-alive and timeout(ms) for base thread in general.
	 * amount of base threads will be '1'.
	 * timeout milliseconds will be '1000'.
	 * timeout interval for base threads will be '10 sec'.
	 * 
	 * @param p_e_baseGateway				enumeration value of BaseGateway class, specifies which database interface will be used with JDBC
	 * @param p_s_host						address of database server, optional port specification e.g. 'localhost:3306'
	 * @param p_s_datasource				parameter for selecting a database within target database server
	 * @param p_s_user						user name for database login
	 * @param p_s_password					user's password for database login
	 * @throws RuntimeException				base pool is already running
	 * @throws IllegalArgumentException		invalid parameter for max amount, timeout interval or timeout milliseconds parameter
	 */
	public BasePool(BaseGateway p_e_baseGateway, String p_s_host, String p_s_datasource, String p_s_user, String p_s_password) throws RuntimeException, IllegalArgumentException {
		this(1, new net.forestany.forestj.lib.DateInterval("PT10S"), 1000, p_e_baseGateway, p_s_host, p_s_datasource, p_s_user, p_s_password);
	}
	
	/**
	 * Constructor of base pool class, settings all parameters for sql connection to database + settings: timeout interval for keep-alive and timeout(ms) for base thread in general.
	 * amount of base threads will be '1'.
	 * timeout milliseconds will be '1000'.
	 * 
	 * @param p_o_timeoutInterval			timeout interval for thread idle - if thread is idle for this interval, database connection will be tested and renewed if necessary
	 * @param p_e_baseGateway				enumeration value of BaseGateway class, specifies which database interface will be used with JDBC
	 * @param p_s_host						address of database server, optional port specification e.g. 'localhost:3306'
	 * @param p_s_datasource				parameter for selecting a database within target database server
	 * @param p_s_user						user name for database login
	 * @param p_s_password					user's password for database login
	 * @throws RuntimeException				base pool is already running
	 * @throws IllegalArgumentException		invalid parameter for max amount, timeout interval or timeout milliseconds parameter
	 */
	public BasePool(net.forestany.forestj.lib.DateInterval p_o_timeoutInterval, BaseGateway p_e_baseGateway, String p_s_host, String p_s_datasource, String p_s_user, String p_s_password) throws RuntimeException, IllegalArgumentException {
		this(1, p_o_timeoutInterval, 1000, p_e_baseGateway, p_s_host, p_s_datasource, p_s_user, p_s_password);
	}
	
	/**
	 * Constructor of base pool class, settings all parameters for sql connection to database + settings: timeout interval for keep-alive and timeout(ms) for base thread in general.
	 * amount of base threads will be '1'.
	 * 
	 * @param p_o_timeoutInterval			timeout interval for thread idle - if thread is idle for this interval, database connection will be tested and renewed if necessary
	 * @param p_i_timeoutMilliseconds		timeout in milliseconds for base thread waiting in idle
	 * @param p_e_baseGateway				enumeration value of BaseGateway class, specifies which database interface will be used with JDBC
	 * @param p_s_host						address of database server, optional port specification e.g. 'localhost:3306'
	 * @param p_s_datasource				parameter for selecting a database within target database server
	 * @param p_s_user						user name for database login
	 * @param p_s_password					user's password for database login
	 * @throws RuntimeException				base pool is already running
	 * @throws IllegalArgumentException		invalid parameter for max amount, timeout interval or timeout milliseconds parameter
	 */
	public BasePool(net.forestany.forestj.lib.DateInterval p_o_timeoutInterval, int p_i_timeoutMilliseconds, BaseGateway p_e_baseGateway, String p_s_host, String p_s_datasource, String p_s_user, String p_s_password) throws RuntimeException, IllegalArgumentException {
		this(1, p_o_timeoutInterval, p_i_timeoutMilliseconds, p_e_baseGateway, p_s_host, p_s_datasource, p_s_user, p_s_password);
	}
	
	/**
	 * Constructor of base pool class, settings all parameters for sql connection to database + settings: amount of base threads, timeout interval for keep-alive and timeout(ms) for base thread in general.
	 * 
	 * @param p_i_maxAmount					integer value for fixed amount of threads for base thread pool instance
	 * @param p_o_timeoutInterval			timeout interval for thread idle - if thread is idle for this interval, database connection will be tested and renewed if necessary
	 * @param p_i_timeoutMilliseconds		timeout in milliseconds for base thread waiting in idle
	 * @param p_e_baseGateway				enumeration value of BaseGateway class, specifies which database interface will be used with JDBC
	 * @param p_s_host						address of database server, optional port specification e.g. 'localhost:3306'
	 * @param p_s_datasource				parameter for selecting a database within target database server
	 * @param p_s_user						user name for database login
	 * @param p_s_password					user's password for database login
	 * @throws RuntimeException				base pool is already running
	 * @throws IllegalArgumentException		invalid parameter for max amount, timeout interval or timeout milliseconds parameter
	 */
	public BasePool(int p_i_maxAmount, net.forestany.forestj.lib.DateInterval p_o_timeoutInterval, int p_i_timeoutMilliseconds, BaseGateway p_e_baseGateway, String p_s_host, String p_s_datasource, String p_s_user, String p_s_password) throws RuntimeException, IllegalArgumentException {
		this.b_running = false;
		this.o_executorServicePool = null;
		this.setMaxAmount(p_i_maxAmount);
		this.setTimeoutInterval(p_o_timeoutInterval);
		this.setTimeoutMilliseconds(p_i_timeoutMilliseconds);
		this.e_baseGateway = p_e_baseGateway;
		this.s_host = p_s_host;
		this.s_datasource = p_s_datasource;
		this.s_user = p_s_user;
		this.s_password = p_s_password;
	}
	
	/**
	 * Starting base pool, creating thread pool instance and all base threads which will connect to (no)sql database
	 * 
	 * @throws RuntimeException				base pool is already running
	 * @throws IllegalArgumentException		illegal database gateway or illegal host parameter
	 * @throws IllegalAccessException		error creating database server connection
	 */
	public void start() throws RuntimeException, IllegalArgumentException, IllegalAccessException {
		net.forestany.forestj.lib.Global.ilog("Starting base pool");
		
		/* check if base pool is already running */
		if (b_running) {
			throw new RuntimeException("Base pool is running. Please stop base pool first");
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("set running flag");
		
		/* set running flag */
		this.b_running = true;
		
												net.forestany.forestj.lib.Global.ilogConfig("create base thread pool instance and give it a fixed amount of threads: '" + this.i_maxAmount + "'");
		
		/* create thread pool instance and give it a fixed amount of threads */
		this.o_executorServicePool = java.util.concurrent.Executors.newFixedThreadPool(this.i_maxAmount);
		

		for (int i = 0; i < this.i_maxAmount; i++) {
			this.a_baseThreads.add(new BaseThread(this.e_baseGateway, this.s_host, this.s_datasource, this.s_user, this.s_password, this.o_timeoutInterval, this.i_timeoutMilliseconds));
			
													net.forestany.forestj.lib.Global.ilogConfig("add base thread object #" + (i + 1) + " to base pool for execution");
													
			/* add socket object #i to thread pool for execution */
			o_executorServicePool.execute(this.a_baseThreads.get(i));
		}
	}
	
	/**
	 * Stopping base pool, stopping each base thread and shutting down base thread pool within 5 seconds
	 * 
	 * @throws RuntimeException				base pool is not running
	 */
	public void stop() throws RuntimeException {
		net.forestany.forestj.lib.Global.ilog("Stopping base pool");
		
		/* check if communication is not running */
		if (!b_running) {
			throw new RuntimeException("Base pool is not running. Please start base pool first");
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("unset running flag");
		
		/* unset running flag */
		this.b_running = false;
		
												net.forestany.forestj.lib.Global.ilogConfig("stop each base thread");
		
		/* stop each base thread */
		for (int i = 0; i < this.i_maxAmount; i++) {
			this.a_baseThreads.get(i).stop();
		}
		
		if (this.o_executorServicePool != null) {
													net.forestany.forestj.lib.Global.ilogConfig("shutdown base thread pool, waiting 5 seconds");
			
			/* shutdown communication thread pool, waiting 5 seconds */
			this.o_executorServicePool.shutdown();
			
			try {
				this.o_executorServicePool.awaitTermination(5L, java.util.concurrent.TimeUnit.SECONDS);
			} catch (InterruptedException o_exc) {
				/* nothing to do */
			}
		}
		
		net.forestany.forestj.lib.Global.ilog("Base pool stopped");
	}
	
	/**
	 * Fetch a query or a amount of queries separated by QueryAbstract.s_querySeparator, with auto commit
	 * 
	 * @param p_o_sqlQuery						query object of Query class &lt;? must be of type QueryAbstract&gt;
	 * @return									list of hash maps, key(string) -> column name + value(object) -> column value of a database record
	 * @throws IllegalAccessException			exception accessing column type, column name or just column value of current result set record
	 * @throws RuntimeException					base pool is not running
	 * @throws InterruptedException				base pool thread for fetching query is interrupted
	 */
	public java.util.List<java.util.LinkedHashMap<String, Object>> fetchQuery(net.forestany.forestj.lib.sql.Query<?> p_o_sqlQuery) throws RuntimeException, IllegalAccessException, InterruptedException {
		/* check if communication is not running */
		if (!b_running) {
			throw new RuntimeException("Base pool is not running. Please start base pool first");
		}
		
		/* prepare return value */
		java.util.List<java.util.LinkedHashMap<String, Object>> a_rows = null;
		int i_maxAttempts = 30;
		int i_attempts = 1;
		
		/* execute query with one base thread object in base pool */
		do {
			/* iterate each base thread to find one which is not locked */
			for (int i = 0; i < this.i_maxAmount; i++) {
				/* check if base thread is not locked */
				if (!this.a_baseThreads.get(i).isLocked()) {
					try {
						net.forestany.forestj.lib.Global.ilogFiner("using base thread #" + (i + 1) + " for " + Thread.currentThread().getName());
						/* execute sql query with base thread */
						a_rows = this.a_baseThreads.get(i).fetchQuery(p_o_sqlQuery);
						break;
					} catch (NullPointerException o_exc) {
						net.forestany.forestj.lib.Global.ilogFiner("Base connection object is null on thread #" + (i + 1) + ": " + o_exc.getMessage());
					} catch (Exception o_exc) {
						net.forestany.forestj.lib.Global.ilogFiner("Could not lock thread #" + (i + 1) + ": " + o_exc.getMessage());
					}
				}
			}
			
			/* check if we have no result after iterating each base thread */
			if (a_rows == null) {
				net.forestany.forestj.lib.Global.ilogFinest("attempt #" + i_attempts + " failed");
				
				/* wait 1000 milliseconds */
				Thread.sleep(1000);
				
				if (i_attempts >= i_maxAttempts) { /* all attempts failed, so could not execute query and retrieve a result */
					net.forestany.forestj.lib.Global.ilogWarning("Could not execute query and retrieve a result, all threads are busy or connection was lost");
					break;
				}
			}
			
			i_attempts++;
		} while (a_rows == null);
		
		/* return result */
		return a_rows;
	}
	
	/**
	 * 
	 * Internal base thread class, implements runnable for thread execution, which hold a database connection object to fetch a sql query and return result rows
	 *
	 */
	private class BaseThread implements Runnable {
		
		/* Fields */
		
		private final java.util.concurrent.locks.ReentrantLock o_lock = new java.util.concurrent.locks.ReentrantLock();
		private net.forestany.forestj.lib.sqlcore.Base o_baseConnection = null;
		private long l_currentTimestamp = 0;
		private boolean b_stop = false;
		private BaseGateway e_baseGateway;
		private String s_host;
		private String s_datasource;
		private String s_user;
		private String s_password;
		private net.forestany.forestj.lib.DateInterval o_timeoutInterval;
		private int i_timeoutMilliseconds;
	
		/* Properties */
		
		public boolean isLocked() {
			return this.o_lock.isLocked();
		}
		
		/* Methods */
		
		/**
		 * Base thread constructor, initiating database server connection
		 * 
		 * @param p_e_baseGateway				enumeration value of BaseGateway class, specifies which database interface will be used with JDBC
		 * @param p_s_host						address of database server, optional port specification e.g. 'localhost:3306'
		 * @param p_s_datasource				parameter for selecting a database within target database server
		 * @param p_s_user						user name for database login
		 * @param p_s_password					user's password for database login
		 * @param p_o_timeoutInterval			timeout interval for thread idle - if thread is idle for this interval, database connection will be tested and renewed if necessary
		 * @param p_i_timeoutMilliseconds		timeout in milliseconds for base thread waiting in idle
		 * @throws IllegalArgumentException		illegal database gateway or illegal host parameter
		 * @throws IllegalAccessException		error creating database server connection
		 */
		public BaseThread(BaseGateway p_e_baseGateway, String p_s_host, String p_s_datasource, String p_s_user, String p_s_password, net.forestany.forestj.lib.DateInterval p_o_timeoutInterval, int p_i_timeoutMilliseconds) throws IllegalArgumentException, IllegalAccessException {
			this.e_baseGateway = p_e_baseGateway;
			this.s_host = p_s_host;
			this.s_datasource = p_s_datasource;
			this.s_user = p_s_user;
			this.s_password = p_s_password;
			this.o_timeoutInterval = p_o_timeoutInterval;
			this.i_timeoutMilliseconds = p_i_timeoutMilliseconds;
			
			if (this.e_baseGateway == BaseGateway.MARIADB) {
				this.o_baseConnection = new net.forestany.forestj.lib.sql.mariadb.BaseMariaDB(this.s_host, this.s_datasource, this.s_user, this.s_password);
			} else if (this.e_baseGateway == BaseGateway.MSSQL) {
				this.o_baseConnection = new net.forestany.forestj.lib.sql.mssql.BaseMSSQL(this.s_host, this.s_datasource, this.s_user, this.s_password);
			} else if (this.e_baseGateway == BaseGateway.ORACLE) {
				this.o_baseConnection = new net.forestany.forestj.lib.sql.oracle.BaseOracle(this.s_host, this.s_datasource, this.s_user, this.s_password);
			} else if (this.e_baseGateway == BaseGateway.PGSQL) {
				this.o_baseConnection = new net.forestany.forestj.lib.sql.pgsql.BasePGSQL(this.s_host, this.s_datasource, this.s_user, this.s_password);
			} else if (this.e_baseGateway == BaseGateway.SQLITE) {
				this.o_baseConnection = new net.forestany.forestj.lib.sql.sqlite.BaseSQLite(this.s_host, this.s_datasource, this.s_user, this.s_password);
			} else if (this.e_baseGateway == BaseGateway.NOSQLMDB) {
				this.o_baseConnection = new net.forestany.forestj.lib.sql.nosqlmdb.BaseNoSQLMDB(this.s_host, this.s_datasource, this.s_user, this.s_password);
			} else {
				throw new IllegalArgumentException("Unknown database gateway '" + this.e_baseGateway + "'");
			}
		}
		
		/**
		 * Base thread run method which will test connection with database connection object after defined interval
		 */
		@Override
		public void run() {
			/* update timestamp for base thread */
			this.l_currentTimestamp = System.currentTimeMillis(); 
			
			/* run routine in while loop while stop flag has not been set with stop-method */
			while (!this.b_stop) {
				try {
					/* wait timeout here */
					Thread.sleep(this.i_timeoutMilliseconds);
				} catch (InterruptedException e) {
					/* nothing to do, sleep just got interrupted */
				}
				
				/* check if timeout interval run out and base thread can be locked */
				if ( ( (System.currentTimeMillis() - this.o_timeoutInterval.toDuration()) >= this.l_currentTimestamp ) && (this.o_lock.tryLock()) ) {
					try {
																net.forestany.forestj.lib.Global.ilogFiner("base thread timeout occurred. base thread locked itself. keep connection alive by testing connection of current connection object");
						
						/* keep connection alive by testing connection of current connection object */
						if (!this.o_baseConnection.testConnection()) {
							/* closing database connection to clear connection object */
							this.o_baseConnection.closeConnection();
							
							/* renew connection object */
							if (this.e_baseGateway == BaseGateway.MARIADB) {
								this.o_baseConnection = new net.forestany.forestj.lib.sql.mariadb.BaseMariaDB(this.s_host, this.s_datasource, this.s_user, this.s_password);
							} else if (this.e_baseGateway == BaseGateway.MSSQL) {
								this.o_baseConnection = new net.forestany.forestj.lib.sql.mssql.BaseMSSQL(this.s_host, this.s_datasource, this.s_user, this.s_password);
							} else if (this.e_baseGateway == BaseGateway.ORACLE) {
								this.o_baseConnection = new net.forestany.forestj.lib.sql.oracle.BaseOracle(this.s_host, this.s_datasource, this.s_user, this.s_password);
							} else if (this.e_baseGateway == BaseGateway.PGSQL) {
								this.o_baseConnection = new net.forestany.forestj.lib.sql.pgsql.BasePGSQL(this.s_host, this.s_datasource, this.s_user, this.s_password);
							} else if (this.e_baseGateway == BaseGateway.SQLITE) {
								this.o_baseConnection = new net.forestany.forestj.lib.sql.sqlite.BaseSQLite(this.s_host, this.s_datasource, this.s_user, this.s_password);
							} else if (this.e_baseGateway == BaseGateway.NOSQLMDB) {
								this.o_baseConnection = new net.forestany.forestj.lib.sql.nosqlmdb.BaseNoSQLMDB(this.s_host, this.s_datasource, this.s_user, this.s_password);
							} else {
								throw new IllegalArgumentException("Unknown database gateway '" + this.e_baseGateway + "'");
							}
						}
					} catch (Exception o_exc) {
						net.forestany.forestj.lib.Global.ilogSevere("exception within base thread timeout, testing connection: " + o_exc.getMessage());
						break;
					} finally {
						/* update timestamp for base thread */
						this.l_currentTimestamp = System.currentTimeMillis();
						
						/* unlock base thread */
						this.o_lock.unlock();
						
																net.forestany.forestj.lib.Global.ilogFiner("base thread unlocked itself for TestConnection or Reconnect");
					}
				}
			}
			
			/* base thread is coming to an end, closing database connection */
			this.o_baseConnection.closeConnection();
		}
		
		/**
		 * Stopping base thread by setting stop flag
		 */
		public void stop() {
			this.b_stop = true;
		}
		
		/**
		 * Fetch a query or a amount of queries separated by QueryAbstract.s_querySeparator, commit database after each execution of query object automatically
		 * 
		 * @param p_o_sqlQuery						query object of Query class <? must be of type QueryAbstract>
		 * @return									list of hash maps, key(string) -> column name + value(object) -> column value of a database record
		 * @throws NullPointerException				base connection object is null
		 * @throws IllegalAccessException			exception accessing column type, column name or just column value of current result set record
		 */
		public java.util.List<java.util.LinkedHashMap<String, Object>> fetchQuery(net.forestany.forestj.lib.sql.Query<?> p_o_sqlQuery) throws NullPointerException, IllegalAccessException {
			/* check if base connection is not null */
			if (o_baseConnection == null) {
				throw new NullPointerException("base connection object is null");
			}
			
			/* try to lock base thread */
			if (this.o_lock.tryLock()) {
				try {
															net.forestany.forestj.lib.Global.ilogFiner("base thread locked by '" + Thread.currentThread().getName() + "' for FetchQuery");
					
					/* update timestamp for base thread */
					this.l_currentTimestamp = System.currentTimeMillis(); 
					
					/* execute sql query with base connection object, using auto commit */
					return this.o_baseConnection.fetchQuery(p_o_sqlQuery, true);
				} catch (Exception o_exc) {
					throw o_exc;
				} finally {
					/* update timestamp for base thread */
					this.l_currentTimestamp = System.currentTimeMillis(); 
					
					/* unlock base thread */
					this.o_lock.unlock();
					
															net.forestany.forestj.lib.Global.ilogFiner("base thread unlocked by '" + Thread.currentThread().getName() + "' for FetchQuery");
				}
			} else {
				/* could not lock base thread -> return null */
				return null;
			}
		}
	}
}
