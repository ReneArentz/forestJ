package net.forestany.forestj.lib;

/**
 * 
 * Global singleton class to store central values and objects, global log methods using java.util.logging.
 *
 */
public class Global {

	/* Constants */
	
	/**
	 * OFF log level constant
	 */
	public static final byte OFF = 0x00;
	/**
	 * SEVERE log level constant
	 */
	public static final byte SEVERE = 0x01;
	/**
	 * WARNING log level constant
	 */
	public static final byte WARNING = 0x02;
	/**
	 * INFO log level constant
	 */
	public static final byte INFO = 0x04;
	/**
	 * CONFIG log level constant
	 */
	public static final byte CONFIG = 0x08;
	/**
	 * FINE log level constant
	 */
	public static final byte FINE = 0x10;
	/**
	 * FINER log level constant
	 */
	public static final byte FINER = 0x20;
	/**
	 * FINEST log level constant
	 */
	public static final byte FINEST = 0x40;
	/**
	 * MASS log level constant
	 */
	public static final byte MASS = -128;
	/**
	 * ALMOST_ALL log level constant
	 */
	public static final byte ALMOST_ALL = (byte)0x7F;
	/**
	 * ALL log level constant
	 */
	public static final byte ALL = (byte)0xFF;
	
	/* Delegates */
	
	/**
	 * interface which can be implemented to add or use other log functionality
	 */
	public interface IDelegate {
		/**
		 * method to be implemented for other log functionality
		 * 
		 * @param p_b_internalLog		true - internal log, false - normal log
		 * @param p_by_logLevel			log level
		 * @param p_s_className			name of class where log entry came from
		 * @param p_s_methodName		class method's methods name where log entry came from
		 * @param p_s_logMessage		log message
		 */
		void OtherImplementation(boolean p_b_internalLog, byte p_by_logLevel, String p_s_className, String p_s_methodName, String p_s_logMessage);
	}
	
	/* Fields */
	
	private static Global o_instance;
	private boolean b_logExceptionsBoth;
	private boolean b_logCompleteSqlQuery;
	
	/**
	 * internal log control value
	 */
	public byte by_internalLogControl;
	/**
	 * log control value
	 */
	public byte by_logControl;
	/**
	 * java.security.SecureRandom instance
	 */
	public java.security.SecureRandom SecureRandom;
	/**
	 * internal logger instance
	 */
	public final java.util.logging.Logger ILOG = java.util.logging.Logger.getLogger("i" + java.util.logging.Logger.GLOBAL_LOGGER_NAME);
	/**
	 * logger instance
	 */
	public final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
	/**
	 * delegate field for other log functionality
	 */
	public Global.IDelegate itf_logging = null;
	/**
	 * global base-gateway setting
	 */
	public net.forestany.forestj.lib.sqlcore.BaseGateway BaseGateway;
	/**
	 * global base instance
	 */
	public net.forestany.forestj.lib.sqlcore.Base Base;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * private constructor of singleton class, can set standard values for objects or settings
	 */
	private Global () {
		try {
			this.by_internalLogControl = Global.OFF;
			this.by_logControl = Global.SEVERE + Global.WARNING + Global.INFO;
			/* System.setProperty("https.protocols", "TLSv1.3"); */
			this.SecureRandom = java.security.SecureRandom.getInstanceStrong();
			this.b_logExceptionsBoth = false;
			this.b_logCompleteSqlQuery = false;
		} catch (java.security.NoSuchAlgorithmException o_exc) {
			/* nothing to do */
		}
	}

	/**
	 * method to access singleton instance
	 * @return Global singleton
	 */
	public static synchronized Global get() {
		if (Global.o_instance == null) {
			Global.o_instance = new Global();
		}
		
		return Global.o_instance;
	}
	

	/**
	 * get flag to log complete sql query
	 * @return boolean
	 */
	public boolean getLogCompleteSqlQuery() {
		return this.b_logCompleteSqlQuery;
	}
	
	/**
	 * set flag to log complete sql query.
	 * if log access is not secured, using this is a vulnerability!
	 * 
	 * @param p_b_value				true / false
	 */
	public void setLogCompleteSqlQuery(boolean p_b_value) {
		if (p_b_value) {
			net.forestany.forestj.lib.Global.logWarning("net.forestany.forestj.lib.sql.Base log query flag has been set true");
			net.forestany.forestj.lib.Global.logWarning("if log access is not secured, using this is a security vulnerability!");
		}
		
		this.b_logCompleteSqlQuery = p_b_value;
	}
	
	/**
	 * get flag to log exceptions on internal logger and normal logger.
	 * @return boolean
	 */
	public boolean getLogExceptionsBoth() {
		return this.b_logExceptionsBoth;
	}
	
	/**
	 * set flag to log exceptions on internal logger and normal logger.
	 * 
	 * @param p_b_value				true / false
	 */
	public void setLogExceptionsBoth(boolean p_b_value) {
		this.b_logExceptionsBoth = p_b_value;
	}
	
	/**
	 * reset java util logging handlers and release any log configuration/files
	 */
	public void resetLog() {
		java.util.logging.LogManager.getLogManager().reset();
	}
	
	/**
	 * Check if a level for logging is active
	 * 
	 * @param p_by_byteLevel		byte value level [Global.SEVERE, Global.WARNING, Global.INFO, Global.CONFIG, Global.FINE, Global.FINER, Global.FINEST, Global.MASS]
	 * @return						true - log level is active, false - log level is not active
	 */
	public static synchronized boolean isLevel(byte p_by_byteLevel) {
		return ( (net.forestany.forestj.lib.Global.get().by_logControl & p_by_byteLevel) == p_by_byteLevel );
	}
	
	/**
	 * Check if a level for internal logging is active
	 * 
	 * @param p_by_byteLevel		byte value level [Global.SEVERE, Global.WARNING, Global.INFO, Global.CONFIG, Global.FINE, Global.FINER, Global.FINEST, Global.MASS]
	 * @return						true - internal log level is active, false - internal log level is not active
	 */
	public static synchronized boolean isILevel(byte p_by_byteLevel) {
		return ( (net.forestany.forestj.lib.Global.get().by_internalLogControl & p_by_byteLevel) == p_by_byteLevel );
	}
	
	
	/**
	 * Create log message with SEVERE log level, if log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void logSevere(String p_s_log) {
		if ( (net.forestany.forestj.lib.Global.get().by_logControl & net.forestany.forestj.lib.Global.SEVERE) == net.forestany.forestj.lib.Global.SEVERE ) {
			Global.o_instance.LOG.logp(java.util.logging.Level.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, net.forestany.forestj.lib.Global.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create log message with WARNING log level, if log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void logWarning(String p_s_log) {
		if ( (net.forestany.forestj.lib.Global.get().by_logControl & net.forestany.forestj.lib.Global.WARNING) == net.forestany.forestj.lib.Global.WARNING ) {
			Global.o_instance.LOG.logp(java.util.logging.Level.WARNING, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, net.forestany.forestj.lib.Global.WARNING, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create log message with INFO log level, if log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void log(String p_s_log) {
		if ( (net.forestany.forestj.lib.Global.get().by_logControl & net.forestany.forestj.lib.Global.INFO) == net.forestany.forestj.lib.Global.INFO ) {
			Global.o_instance.LOG.logp(java.util.logging.Level.INFO, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, net.forestany.forestj.lib.Global.INFO, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create log message with CONFIG log level, if log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void logConfig(String p_s_log) {
		if ( (net.forestany.forestj.lib.Global.get().by_logControl & net.forestany.forestj.lib.Global.CONFIG) == net.forestany.forestj.lib.Global.CONFIG ) {
			Global.o_instance.LOG.logp(java.util.logging.Level.CONFIG, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, net.forestany.forestj.lib.Global.CONFIG, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create log message with FINE log level, if log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void logFine(String p_s_log) {
		if ( (net.forestany.forestj.lib.Global.get().by_logControl & net.forestany.forestj.lib.Global.FINE) == net.forestany.forestj.lib.Global.FINE ) {
			Global.o_instance.LOG.logp(java.util.logging.Level.FINE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, net.forestany.forestj.lib.Global.FINE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create log message with FINER log level, if log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void logFiner(String p_s_log) {
		if ( (net.forestany.forestj.lib.Global.get().by_logControl & net.forestany.forestj.lib.Global.FINER) == net.forestany.forestj.lib.Global.FINER ) {
			Global.o_instance.LOG.logp(java.util.logging.Level.FINER, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, net.forestany.forestj.lib.Global.FINER, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create log message with FINEST log level, if log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void logFinest(String p_s_log) {
		if ( (net.forestany.forestj.lib.Global.get().by_logControl & net.forestany.forestj.lib.Global.FINEST) == net.forestany.forestj.lib.Global.FINEST ) {
			Global.o_instance.LOG.logp(java.util.logging.Level.FINEST, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, net.forestany.forestj.lib.Global.FINEST, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create log message with MASS log level, if log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void logMass(String p_s_log) {
		if ( (net.forestany.forestj.lib.Global.get().by_logControl & net.forestany.forestj.lib.Global.MASS) == net.forestany.forestj.lib.Global.MASS ) {
			Global.o_instance.LOG.logp(java.util.logging.Level.FINEST, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, net.forestany.forestj.lib.Global.FINEST, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	
	/**
	 * method to log an exception with java.util.logging tools
	 * 
	 * @param p_o_exc		exception which will be add to the log as severe
	 * @see	  Exception
	 */
	public static synchronized void logException(Exception p_o_exc) {
		net.forestany.forestj.lib.Global.logException(null, p_o_exc);
	}
	
	/**
	 * method to log an exception with java.util.logging tools
	 * 
	 * @param p_s_caption	caption before exception message
	 * @param p_o_exc		exception which will be add to the log as severe
	 * @see	  Exception
	 */
	public static synchronized void logException(String p_s_caption, Exception p_o_exc) {
		/* add caption to log message or just take the exception message */
		if (Helper.isStringEmpty(p_s_caption)) {
			if ( (net.forestany.forestj.lib.Global.get().getLogExceptionsBoth()) && ( (net.forestany.forestj.lib.Global.get().by_logControl & net.forestany.forestj.lib.Global.SEVERE) == net.forestany.forestj.lib.Global.SEVERE ) ) {
				Global.o_instance.LOG.logp(java.util.logging.Level.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_o_exc.getMessage());
			}
			
			if ( (net.forestany.forestj.lib.Global.get().by_internalLogControl & net.forestany.forestj.lib.Global.SEVERE) == net.forestany.forestj.lib.Global.SEVERE ) {
				Global.o_instance.ILOG.logp(java.util.logging.Level.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_o_exc.getMessage());
			}
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, net.forestany.forestj.lib.Global.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_o_exc.getMessage());
			}
		} else {
			if ( (net.forestany.forestj.lib.Global.get().getLogExceptionsBoth()) && ( (net.forestany.forestj.lib.Global.get().by_logControl & net.forestany.forestj.lib.Global.SEVERE) == net.forestany.forestj.lib.Global.SEVERE ) ) {
				Global.o_instance.LOG.logp(java.util.logging.Level.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_caption + p_o_exc.getMessage());
			}
			
			if ( (net.forestany.forestj.lib.Global.get().by_internalLogControl & net.forestany.forestj.lib.Global.SEVERE) == net.forestany.forestj.lib.Global.SEVERE ) {
				Global.o_instance.ILOG.logp(java.util.logging.Level.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_caption + p_o_exc.getMessage());
			}
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, net.forestany.forestj.lib.Global.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_caption + p_o_exc.getMessage());
			}
		}
		
		/* add stack trace elements to log with indent */
		for (StackTraceElement o_foo : p_o_exc.getStackTrace()) {
			if ( (net.forestany.forestj.lib.Global.get().getLogExceptionsBoth()) && ( (net.forestany.forestj.lib.Global.get().by_logControl & net.forestany.forestj.lib.Global.SEVERE) == net.forestany.forestj.lib.Global.SEVERE ) ) {
				Global.o_instance.LOG.logp(java.util.logging.Level.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), "\t" + o_foo.toString());
			}
			
			if ( (net.forestany.forestj.lib.Global.get().by_internalLogControl & net.forestany.forestj.lib.Global.SEVERE) == net.forestany.forestj.lib.Global.SEVERE ) {
				Global.o_instance.ILOG.logp(java.util.logging.Level.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), "\t" + o_foo.toString());
			}
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, net.forestany.forestj.lib.Global.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), "\t" + o_foo.toString());
			}
		}
	}

	
	/**
	 * Create internal log message with SEVERE log level, if internal log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void ilogSevere(String p_s_log) {
		if ( (net.forestany.forestj.lib.Global.get().by_internalLogControl & net.forestany.forestj.lib.Global.SEVERE) == net.forestany.forestj.lib.Global.SEVERE ) {
			Global.o_instance.ILOG.logp(java.util.logging.Level.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(true, net.forestany.forestj.lib.Global.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create internal log message with WARNING log level, if internal log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void ilogWarning(String p_s_log) {
		if ( (net.forestany.forestj.lib.Global.get().by_internalLogControl & net.forestany.forestj.lib.Global.WARNING) == net.forestany.forestj.lib.Global.WARNING ) {
			Global.o_instance.ILOG.logp(java.util.logging.Level.WARNING, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(true, net.forestany.forestj.lib.Global.WARNING, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create internal log message with INFO log level, if internal log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void ilog(String p_s_log) {
		if ( (net.forestany.forestj.lib.Global.get().by_internalLogControl & net.forestany.forestj.lib.Global.INFO) == net.forestany.forestj.lib.Global.INFO ) {
			Global.o_instance.ILOG.logp(java.util.logging.Level.INFO, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(true, net.forestany.forestj.lib.Global.INFO, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create internal log message with INFO log level, if internal log level is included in logControl field, limit each log line to a constant amount of characters
	 * 
	 * @param p_s_log		log message which will be logged
	 * @param p_i_limit		constant limit amount of characters
	 */
	public static synchronized void ilogLarge(String p_s_log, int p_i_limit) {
		if ((p_i_limit > 0) && (p_s_log.length() > p_i_limit)) {
		       if ( (net.forestany.forestj.lib.Global.get().by_internalLogControl & net.forestany.forestj.lib.Global.INFO) == net.forestany.forestj.lib.Global.INFO ) {
					Global.o_instance.ILOG.logp(java.util.logging.Level.INFO, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log.substring(0, p_i_limit));
					
					/* call other log implementation */
					if (Global.o_instance.itf_logging != null) {
						Global.o_instance.itf_logging.OtherImplementation(true, net.forestany.forestj.lib.Global.INFO, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log.substring(0, p_i_limit));
					}
					
					/* call recursive to log the complete log message */
					ilogLarge(p_s_log.substring(p_i_limit), p_i_limit);
				}
		   } else {
			   if ( (net.forestany.forestj.lib.Global.get().by_internalLogControl & net.forestany.forestj.lib.Global.INFO) == net.forestany.forestj.lib.Global.INFO ) {
					Global.o_instance.ILOG.logp(java.util.logging.Level.INFO, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
					
					/* call other log implementation */
					if (Global.o_instance.itf_logging != null) {
						Global.o_instance.itf_logging.OtherImplementation(true, net.forestany.forestj.lib.Global.INFO, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
					}
				}
		   }
	}
	
	/**
	 * Create internal log message with CONFIG log level, if internal log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void ilogConfig(String p_s_log) {
		if ( (net.forestany.forestj.lib.Global.get().by_internalLogControl & net.forestany.forestj.lib.Global.CONFIG) == net.forestany.forestj.lib.Global.CONFIG ) {
			Global.o_instance.ILOG.logp(java.util.logging.Level.CONFIG, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(true, net.forestany.forestj.lib.Global.CONFIG, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create internal log message with FINE log level, if internal log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void ilogFine(String p_s_log) {
		if ( (net.forestany.forestj.lib.Global.get().by_internalLogControl & net.forestany.forestj.lib.Global.FINE) == net.forestany.forestj.lib.Global.FINE ) {
			Global.o_instance.ILOG.logp(java.util.logging.Level.FINE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(true, net.forestany.forestj.lib.Global.FINE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create internal log message with FINER log level, if internal log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void ilogFiner(String p_s_log) {
		if ( (net.forestany.forestj.lib.Global.get().by_internalLogControl & net.forestany.forestj.lib.Global.FINER) == net.forestany.forestj.lib.Global.FINER ) {
			Global.o_instance.ILOG.logp(java.util.logging.Level.FINER, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(true, net.forestany.forestj.lib.Global.FINER, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create internal log message with FINEST log level, if internal log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void ilogFinest(String p_s_log) {
		if ( (net.forestany.forestj.lib.Global.get().by_internalLogControl & net.forestany.forestj.lib.Global.FINEST) == net.forestany.forestj.lib.Global.FINEST ) {
			Global.o_instance.ILOG.logp(java.util.logging.Level.FINEST, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(true, net.forestany.forestj.lib.Global.FINEST, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create internal log message with MASS log level, if internal log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void ilogMass(String p_s_log) {
		if ( (net.forestany.forestj.lib.Global.get().by_internalLogControl & net.forestany.forestj.lib.Global.MASS) == net.forestany.forestj.lib.Global.MASS ) {
			Global.o_instance.ILOG.logp(java.util.logging.Level.FINEST, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(true, net.forestany.forestj.lib.Global.FINEST, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
}
