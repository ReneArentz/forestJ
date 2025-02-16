package de.forestj.lib;

/**
 * 
 * Global singleton class to store central values and objects, global log methods using java.util.logging.
 *
 */
public class Global {

	/* Constants */
	
	public static final byte OFF = 0x00;
	public static final byte SEVERE = 0x01;
	public static final byte WARNING = 0x02;
	public static final byte INFO = 0x04;
	public static final byte CONFIG = 0x08;
	public static final byte FINE = 0x10;
	public static final byte FINER = 0x20;
	public static final byte FINEST = 0x40;
	public static final byte MASS = -128;
	public static final byte ALMOST_ALL = (byte)0x7F;
	public static final byte ALL = (byte)0xFF;
	
	/* Delegates */
	
	/**
	 * interface which can be implemented to add or use other log functionality
	 */
	public interface IDelegate {
		void OtherImplementation(boolean p_b_internalLog, byte p_by_logLevel, String p_s_className, String p_s_methodName, String p_s_logMessage);
	}
	
	/* Fields */
	
	private static Global o_instance;
	
	public byte by_internalLogControl;
	public byte by_logControl;
	public java.security.SecureRandom SecureRandom;
	private boolean b_logExceptionsBoth;
	private boolean b_logCompleteSqlQuery;
	public final java.util.logging.Logger ILOG = java.util.logging.Logger.getLogger("i" + java.util.logging.Logger.GLOBAL_LOGGER_NAME);
	public final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
	
	public Global.IDelegate itf_logging = null;
	
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
			de.forestj.lib.Global.logWarning("de.forestj.lib.sql.Base log query flag has been set true");
			de.forestj.lib.Global.logWarning("if log access is not secured, using this is a security vulnerability!");
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
		return ( (de.forestj.lib.Global.get().by_logControl & p_by_byteLevel) == p_by_byteLevel );
	}
	
	/**
	 * Check if a level for internal logging is active
	 * 
	 * @param p_by_byteLevel		byte value level [Global.SEVERE, Global.WARNING, Global.INFO, Global.CONFIG, Global.FINE, Global.FINER, Global.FINEST, Global.MASS]
	 * @return						true - internal log level is active, false - internal log level is not active
	 */
	public static synchronized boolean isILevel(byte p_by_byteLevel) {
		return ( (de.forestj.lib.Global.get().by_internalLogControl & p_by_byteLevel) == p_by_byteLevel );
	}
	
	
	/**
	 * Create log message with SEVERE log level, if log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void logSevere(String p_s_log) {
		if ( (de.forestj.lib.Global.get().by_logControl & de.forestj.lib.Global.SEVERE) == de.forestj.lib.Global.SEVERE ) {
			Global.o_instance.LOG.logp(java.util.logging.Level.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, de.forestj.lib.Global.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create log message with WARNING log level, if log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void logWarning(String p_s_log) {
		if ( (de.forestj.lib.Global.get().by_logControl & de.forestj.lib.Global.WARNING) == de.forestj.lib.Global.WARNING ) {
			Global.o_instance.LOG.logp(java.util.logging.Level.WARNING, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, de.forestj.lib.Global.WARNING, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create log message with INFO log level, if log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void log(String p_s_log) {
		if ( (de.forestj.lib.Global.get().by_logControl & de.forestj.lib.Global.INFO) == de.forestj.lib.Global.INFO ) {
			Global.o_instance.LOG.logp(java.util.logging.Level.INFO, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, de.forestj.lib.Global.INFO, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create log message with CONFIG log level, if log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void logConfig(String p_s_log) {
		if ( (de.forestj.lib.Global.get().by_logControl & de.forestj.lib.Global.CONFIG) == de.forestj.lib.Global.CONFIG ) {
			Global.o_instance.LOG.logp(java.util.logging.Level.CONFIG, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, de.forestj.lib.Global.CONFIG, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create log message with FINE log level, if log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void logFine(String p_s_log) {
		if ( (de.forestj.lib.Global.get().by_logControl & de.forestj.lib.Global.FINE) == de.forestj.lib.Global.FINE ) {
			Global.o_instance.LOG.logp(java.util.logging.Level.FINE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, de.forestj.lib.Global.FINE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create log message with FINER log level, if log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void logFiner(String p_s_log) {
		if ( (de.forestj.lib.Global.get().by_logControl & de.forestj.lib.Global.FINER) == de.forestj.lib.Global.FINER ) {
			Global.o_instance.LOG.logp(java.util.logging.Level.FINER, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, de.forestj.lib.Global.FINER, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create log message with FINEST log level, if log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void logFinest(String p_s_log) {
		if ( (de.forestj.lib.Global.get().by_logControl & de.forestj.lib.Global.FINEST) == de.forestj.lib.Global.FINEST ) {
			Global.o_instance.LOG.logp(java.util.logging.Level.FINEST, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, de.forestj.lib.Global.FINEST, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create log message with MASS log level, if log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void logMass(String p_s_log) {
		if ( (de.forestj.lib.Global.get().by_logControl & de.forestj.lib.Global.MASS) == de.forestj.lib.Global.MASS ) {
			Global.o_instance.LOG.logp(java.util.logging.Level.FINEST, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, de.forestj.lib.Global.FINEST, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
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
		de.forestj.lib.Global.logException(null, p_o_exc);
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
			if ( (de.forestj.lib.Global.get().getLogExceptionsBoth()) && ( (de.forestj.lib.Global.get().by_logControl & de.forestj.lib.Global.SEVERE) == de.forestj.lib.Global.SEVERE ) ) {
				Global.o_instance.LOG.logp(java.util.logging.Level.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_o_exc.getMessage());
			}
			
			if ( (de.forestj.lib.Global.get().by_internalLogControl & de.forestj.lib.Global.SEVERE) == de.forestj.lib.Global.SEVERE ) {
				Global.o_instance.ILOG.logp(java.util.logging.Level.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_o_exc.getMessage());
			}
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, de.forestj.lib.Global.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_o_exc.getMessage());
			}
		} else {
			if ( (de.forestj.lib.Global.get().getLogExceptionsBoth()) && ( (de.forestj.lib.Global.get().by_logControl & de.forestj.lib.Global.SEVERE) == de.forestj.lib.Global.SEVERE ) ) {
				Global.o_instance.LOG.logp(java.util.logging.Level.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_caption + p_o_exc.getMessage());
			}
			
			if ( (de.forestj.lib.Global.get().by_internalLogControl & de.forestj.lib.Global.SEVERE) == de.forestj.lib.Global.SEVERE ) {
				Global.o_instance.ILOG.logp(java.util.logging.Level.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_caption + p_o_exc.getMessage());
			}
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, de.forestj.lib.Global.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_caption + p_o_exc.getMessage());
			}
		}
		
		/* add stack trace elements to log with indent */
		for (StackTraceElement o_foo : p_o_exc.getStackTrace()) {
			if ( (de.forestj.lib.Global.get().getLogExceptionsBoth()) && ( (de.forestj.lib.Global.get().by_logControl & de.forestj.lib.Global.SEVERE) == de.forestj.lib.Global.SEVERE ) ) {
				Global.o_instance.LOG.logp(java.util.logging.Level.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), "\t" + o_foo.toString());
			}
			
			if ( (de.forestj.lib.Global.get().by_internalLogControl & de.forestj.lib.Global.SEVERE) == de.forestj.lib.Global.SEVERE ) {
				Global.o_instance.ILOG.logp(java.util.logging.Level.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), "\t" + o_foo.toString());
			}
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(false, de.forestj.lib.Global.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), "\t" + o_foo.toString());
			}
		}
	}

	
	/**
	 * Create internal log message with SEVERE log level, if internal log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void ilogSevere(String p_s_log) {
		if ( (de.forestj.lib.Global.get().by_internalLogControl & de.forestj.lib.Global.SEVERE) == de.forestj.lib.Global.SEVERE ) {
			Global.o_instance.ILOG.logp(java.util.logging.Level.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(true, de.forestj.lib.Global.SEVERE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create internal log message with WARNING log level, if internal log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void ilogWarning(String p_s_log) {
		if ( (de.forestj.lib.Global.get().by_internalLogControl & de.forestj.lib.Global.WARNING) == de.forestj.lib.Global.WARNING ) {
			Global.o_instance.ILOG.logp(java.util.logging.Level.WARNING, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(true, de.forestj.lib.Global.WARNING, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create internal log message with INFO log level, if internal log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void ilog(String p_s_log) {
		if ( (de.forestj.lib.Global.get().by_internalLogControl & de.forestj.lib.Global.INFO) == de.forestj.lib.Global.INFO ) {
			Global.o_instance.ILOG.logp(java.util.logging.Level.INFO, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(true, de.forestj.lib.Global.INFO, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
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
		       if ( (de.forestj.lib.Global.get().by_internalLogControl & de.forestj.lib.Global.INFO) == de.forestj.lib.Global.INFO ) {
					Global.o_instance.ILOG.logp(java.util.logging.Level.INFO, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log.substring(0, p_i_limit));
					
					/* call other log implementation */
					if (Global.o_instance.itf_logging != null) {
						Global.o_instance.itf_logging.OtherImplementation(true, de.forestj.lib.Global.INFO, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log.substring(0, p_i_limit));
					}
					
					/* call recursive to log the complete log message */
					ilogLarge(p_s_log.substring(p_i_limit), p_i_limit);
				}
		   } else {
			   if ( (de.forestj.lib.Global.get().by_internalLogControl & de.forestj.lib.Global.INFO) == de.forestj.lib.Global.INFO ) {
					Global.o_instance.ILOG.logp(java.util.logging.Level.INFO, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
					
					/* call other log implementation */
					if (Global.o_instance.itf_logging != null) {
						Global.o_instance.itf_logging.OtherImplementation(true, de.forestj.lib.Global.INFO, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
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
		if ( (de.forestj.lib.Global.get().by_internalLogControl & de.forestj.lib.Global.CONFIG) == de.forestj.lib.Global.CONFIG ) {
			Global.o_instance.ILOG.logp(java.util.logging.Level.CONFIG, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(true, de.forestj.lib.Global.CONFIG, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create internal log message with FINE log level, if internal log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void ilogFine(String p_s_log) {
		if ( (de.forestj.lib.Global.get().by_internalLogControl & de.forestj.lib.Global.FINE) == de.forestj.lib.Global.FINE ) {
			Global.o_instance.ILOG.logp(java.util.logging.Level.FINE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(true, de.forestj.lib.Global.FINE, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create internal log message with FINER log level, if internal log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void ilogFiner(String p_s_log) {
		if ( (de.forestj.lib.Global.get().by_internalLogControl & de.forestj.lib.Global.FINER) == de.forestj.lib.Global.FINER ) {
			Global.o_instance.ILOG.logp(java.util.logging.Level.FINER, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(true, de.forestj.lib.Global.FINER, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create internal log message with FINEST log level, if internal log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void ilogFinest(String p_s_log) {
		if ( (de.forestj.lib.Global.get().by_internalLogControl & de.forestj.lib.Global.FINEST) == de.forestj.lib.Global.FINEST ) {
			Global.o_instance.ILOG.logp(java.util.logging.Level.FINEST, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(true, de.forestj.lib.Global.FINEST, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
	
	/**
	 * Create internal log message with MASS log level, if internal log level is included in logControl field
	 * 
	 * @param p_s_log	log message which will be logged
	 */
	public static synchronized void ilogMass(String p_s_log) {
		if ( (de.forestj.lib.Global.get().by_internalLogControl & de.forestj.lib.Global.MASS) == de.forestj.lib.Global.MASS ) {
			Global.o_instance.ILOG.logp(java.util.logging.Level.FINEST, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			
			/* call other log implementation */
			if (Global.o_instance.itf_logging != null) {
				Global.o_instance.itf_logging.OtherImplementation(true, de.forestj.lib.Global.FINEST, (Thread.currentThread().getStackTrace())[2].getClassName(), (Thread.currentThread().getStackTrace())[2].getMethodName(), p_s_log);
			}
		}
	}
}
