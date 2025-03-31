package net.forestany.forestj.lib;

/**
 * Class to create log configuration for java.util.logging without having an external file.
 * supports log to file and log to console.
 */
public class LoggingConfig {

	/* Fields */
	
	private boolean b_useConsole;
	private boolean b_useFile;
	private java.util.logging.Level e_level;
	private java.util.logging.Level e_consoleLevel;
	private java.util.logging.Level e_fileLevel;
	private String s_filePath;
	private String s_filePattern;
	private boolean b_fileAppend;
	private int i_fileLimit;
	private int i_fileCount;
	private String s_formatLogLine;
	
	/* Properties */
	
	/**
	 * get use console logging
	 * @return boolean
	 */
	public boolean getUseConsole() {
		return this.b_useConsole;
	}
	
	/**
	 * set use console logging
	 * @param p_b_value		true - use console
	 */
	public void setUseConsole(boolean p_b_value) {
		this.b_useConsole = p_b_value;
	}
	
	/**
	 * get use file logging
	 * @return boolean
	 */
	public boolean getUseFile() {
		return this.b_useFile;
	}
	
	/**
	 * set use file logging
	 * @param p_b_value		true - use file
	 */
	public void setUseFile(boolean p_b_value) {
		this.b_useFile = p_b_value;
	}
	
	/**
	 * get logging level
	 * @return java.util.logging.Level
	 */
	public java.util.logging.Level getLevel() {
		return this.e_level;
	}
	
	/**
	 * set logging level
	 * @param p_e_value							logging level
	 * @throws IllegalArgumentException			parameter is empty
	 */
	public void setLevel(java.util.logging.Level p_e_value) throws IllegalArgumentException {
		if (p_e_value == null) {
			throw new IllegalArgumentException("Level parameter is empty");
		}
		
		this.e_level = p_e_value;
	}
	
	/**
	 * get console logging level
	 * @return java.util.logging.Level
	 */
	public java.util.logging.Level getConsoleLevel() {
		return this.e_consoleLevel;
	}
	
	/**
	 * set console logging level
	 * @param p_e_value							logging level
	 * @throws IllegalArgumentException			parameter is empty
	 */
	public void setConsoleLevel(java.util.logging.Level p_e_value) throws IllegalArgumentException {
		if (p_e_value == null) {
			throw new IllegalArgumentException("Console level parameter is empty");
		}
		
		this.e_consoleLevel = p_e_value;
	}
	
	/**
	 * get file logging level
	 * @return java.util.logging.Level
	 */
	public java.util.logging.Level getFileLevel() {
		return this.e_fileLevel;
	}
	
	/**
	 * set file logging level
	 * @param p_e_value							logging level
	 * @throws IllegalArgumentException			parameter is empty
	 */
	public void setFileLevel(java.util.logging.Level p_e_value) throws IllegalArgumentException {
		if (p_e_value == null) {
			throw new IllegalArgumentException("File level parameter is empty");
		}
		
		this.e_fileLevel = p_e_value;
	}
	
	/**
	 * get file path for logging file
	 * @return String 
	 */
	public String getFilePath() {
		return this.s_filePath;
	}
	
	/**
	 * set file path for logging file
	 * @param p_s_value								file path
	 * @throws IllegalArgumentException				parameter is empty
	 * @throws java.io.FileNotFoundException		file for logging does not exist
	 */
	public void setFilePath(String p_s_value) throws IllegalArgumentException, java.io.FileNotFoundException {
		if (Helper.isStringEmpty(p_s_value)) {
			throw new IllegalArgumentException("File path parameter is empty");
		}
		
		/* handle directory separator, java.util.logging uses '/' */
		p_s_value = p_s_value.replace("\\", "/");
		
		/* check if file path ends with system directory separator */
		if (!p_s_value.endsWith("/")) { 
			throw new IllegalArgumentException("File path[" + p_s_value + "] for logging must end with '/'");
		}
		
		/* check if file path really exists */
		if ( (!p_s_value.contentEquals("%h" + net.forestany.forestj.lib.io.File.DIR)) && (!p_s_value.contentEquals("%t" + net.forestany.forestj.lib.io.File.DIR)) ) { 
	    	if (!net.forestany.forestj.lib.io.File.folderExists(p_s_value.replace("/", net.forestany.forestj.lib.io.File.DIR))) {
	    		throw new java.io.FileNotFoundException("File path[" + p_s_value + "] for logging does not exist");
	    	}
		}
		
		/* take value as file path */
		this.s_filePath = p_s_value;
	}
	
	/**
	 * get logging file pattern
	 * @return String 
	 */
	public String getFilePattern() {
		return this.s_filePattern;
	}
	
	/**
	 * set logging file pattern
	 * @param p_s_value							logging file pattern
	 * @throws IllegalArgumentException			parameter is empty
	 */
	public void setFilePattern(String p_s_value) throws IllegalArgumentException {
		if (Helper.isStringEmpty(p_s_value)) {
			throw new IllegalArgumentException("File pattern parameter is empty");
		}
		
		if (p_s_value.length() < 6) {
			throw new IllegalArgumentException("File pattern[" + p_s_value + "] must be at least 6 characters long");
		}
		
		this.s_filePattern = p_s_value;
	}
	
	/**
	 * get logging file append flag
	 * @return boolean
	 */
	public boolean getFileAppend() {
		return this.b_fileAppend;
	}
	
	/**
	 * set logging file append flag
	 * @param p_b_value			file append flag value
	 */
	public void setFileAppend(boolean p_b_value) {
		this.b_fileAppend = p_b_value;
	}
	
	/**
	 * get logging file limit
	 * @return int
	 */
	public int getFileLimit() {
		return this.i_fileLimit;
	}
	
	/**
	 * set logging file limit
	 * @param p_i_value								file limit value in bytes
	 * @throws IllegalArgumentException				log file should not exceed 1 GB
	 */
	public void setFileLimit(int p_i_value) throws IllegalArgumentException {
		/* log file has no limit */
		if (p_i_value < 0) {
			p_i_value = 0;
		}
		
		/* log file should not exceed 1 GB */
		if (p_i_value > 1000000000) {
			throw new IllegalArgumentException("File limit[" + p_i_value + "] cannot be greater than '1000000000 (1 GB)'");
		}
		
		this.i_fileLimit = p_i_value;
	}
	
	/**
	 * get logging file count
	 * @return int
	 */
	public int getFileCount() {
		return this.i_fileCount;
	}
	
	/**
	 * set logging file count
	 * @param p_i_value								file count
	 * @throws IllegalArgumentException				at least '1', max '100'
	 */
	public void setFileCount(int p_i_value) throws IllegalArgumentException {
		if (p_i_value < 1) {
			throw new IllegalArgumentException("File limit[" + p_i_value + "] must be at least '1'");
		}
		
		if (p_i_value > 100) {
			throw new IllegalArgumentException("File limit[" + p_i_value + "] cannot be greater than '100'");
		}
		
		this.i_fileCount = p_i_value;
	}
	
	/**
	 * get format log line
	 * @return String
	 */
	public String getFormatLogLine() {
		return this.s_formatLogLine;
	}
	
	/**
	 * set format log line
	 * @param p_s_value							format log line value
	 * @throws IllegalArgumentException			parameter is empty, must contain '%1$' or '%5$'
	 */
	public void setFormatLogLine(String p_s_value) throws IllegalArgumentException {
		if (Helper.isStringEmpty(p_s_value)) {
			throw new IllegalArgumentException("Format log line parameter is empty");
		}
		
		if ( (!p_s_value.contains("%1$")) || (!p_s_value.contains("%5$")) ) {
			throw new IllegalArgumentException("Format log line[" + p_s_value + "] must contain at least '%1$' for log date value and '%5$' for log message value");
		}
		
		this.s_formatLogLine = p_s_value;
	}
	
	/* Methods */
	
	/**
	 * Constructor which is loading default values for log configuration.
	 * default logging to file, not console.
	 * default log level is INFO.
	 * default filepath is system temporary directory.
	 * default file size limit is 1 MiB.
	 * default file amount limit is 25.
	 */
	public LoggingConfig() {
		/* set default values */
		this.b_useConsole = true;
		this.b_useFile = false;
		this.e_level = java.util.logging.Level.INFO;
		this.e_consoleLevel = null;
		this.e_fileLevel = null;
		this.s_filePath = "%t" + net.forestany.forestj.lib.io.File.DIR;
		this.s_filePattern = "javaLog_%g_%u.log";
		this.b_fileAppend = true;
		this.i_fileLimit = 1000000;
		this.i_fileCount = 25;
		this.s_formatLogLine = "[%1$tF %1$tT.%1tL] [%4$-7s] [%2$-80s] %5$s%n";
	}
	
	/**
	 * Constructor which is loading config values from a file for log configuration
	 * 
	 * @param	p_s_configFile					path to config file
	 * @throws	IllegalArgumentException		config file does not exist, or a config value is illegal
	 * @throws	NullPointerException			parameter name is not set
	 * @throws	java.io.FileNotFoundException	path where log files shall be stored does not exist
	 */
	public LoggingConfig(String p_s_configFile) throws IllegalArgumentException, NullPointerException, java.io.FileNotFoundException {
		/* set default values */
		this.b_useConsole = false;
		this.b_useFile = false;
		this.e_level = null;
		this.e_consoleLevel = null;
		this.e_fileLevel = null;
		this.s_filePath = "%t" + net.forestany.forestj.lib.io.File.DIR;
		this.s_filePattern = "javaLog_%g_%u.log";
		this.b_fileAppend = true;
		this.i_fileLimit = 1000000;
		this.i_fileCount = 25;
		this.s_formatLogLine = "[%1$tF %1$tT.%1tL] [%4$-7s] [%2$-80s] %5$s%n";
		
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_configFile)) {
			/* default value useConsole=true if we are not using a config file */
			this.b_useConsole = true;
		} else {
			if (!net.forestany.forestj.lib.io.File.exists(p_s_configFile)) {
				throw new IllegalArgumentException("config file[" + p_s_configFile + "] for log configuration does not exist");
			}
			
			java.util.List<String> a_configLines = null;
			
			try {
				a_configLines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get( p_s_configFile ));
			} catch (java.io.IOException e) {
				/* already checked if config file exists */
			}
			
			for (int i = 0; i < a_configLines.size(); i++) {
				String s_line = a_configLines.get(i);
				
				if ( ( net.forestany.forestj.lib.Helper.isStringEmpty(s_line.trim()) ) || (!s_line.contains("=")) ) {
					continue;
				}
				
				String[] a_lineConfigPair = s_line.split("=");
				
				String s_configName = a_lineConfigPair[0].trim().toLowerCase();
				String s_configValue = a_lineConfigPair[1].trim();
				
				if (s_configName.contentEquals("useconsole")) {
					this.setUseConsole(Boolean.parseBoolean(s_configValue));
				} else if (s_configName.contentEquals("usefile")) {
					this.setUseFile(Boolean.parseBoolean(s_configValue));
				} else if (s_configName.contentEquals("level")) {
					this.setLevel(this.stringLeveltoLoggingLevel(s_configValue));
				} else if (s_configName.contentEquals("consolelevel")) {
					this.setConsoleLevel(this.stringLeveltoLoggingLevel(s_configValue));
				} else if (s_configName.contentEquals("filelevel")) {
					this.setFileLevel(this.stringLeveltoLoggingLevel(s_configValue));
				} else if (s_configName.contentEquals("filepath")) {
					if (s_configValue.toLowerCase().contentEquals("currentdirectory")) {
						this.setFilePath(java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR);
					} else {
						this.setFilePath(s_configValue);
					}
				} else if (s_configName.contentEquals("filepattern")) {
					this.setFilePattern(s_configValue);
				} else if (s_configName.contentEquals("fileappend")) {
					this.setUseConsole(Boolean.parseBoolean(s_configValue));
				} else if (s_configName.contentEquals("filelimit")) {
					this.setFileLimit(Integer.parseInt(s_configValue));
				} else if (s_configName.contentEquals("filecount")) {
					this.setFileCount(Integer.parseInt(s_configValue));
				} else if (s_configName.contentEquals("formatlogline")) {
					this.setFormatLogLine(s_configValue);
				}
			}
		}
		
		/* check useConsole and useFile values */
		if ( (!this.b_useConsole) && (!this.b_useFile) ) {
			throw new IllegalArgumentException("config file must activate at least console or file logging");
		}
		
		/* check log level from file */
		if ( (this.e_level == null) && (this.e_consoleLevel == null) && (this.e_fileLevel == null) ) {
			throw new IllegalArgumentException("config file must sepcify at least one log level[Level, ConsoleLevel or FileLevel]");
		}
		
		/* set level with console level or with file level */
		if ( (this.e_level == null) && (this.e_consoleLevel != null) ) {
			this.e_level = this.e_consoleLevel;
		} else if ( (this.e_level == null) && (this.e_fileLevel != null) ) {
			this.e_level = this.e_fileLevel;
		}
	}
	
	/**
	 * Converts a string log level parameter to a matching java util logging level
	 * 
	 * @param 	p_s_level				string log level parameter
	 * @return							matching java util logging level
	 * @throws	NullPointerException	parameter is not set
	 * @see								java.util.logging.Level
	 */
	private java.util.logging.Level stringLeveltoLoggingLevel(String p_s_level) throws NullPointerException {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_level)) {
			throw new NullPointerException("Level parameter is null");
		}
		
		if (p_s_level.toUpperCase().contentEquals("OFF")) {
			return java.util.logging.Level.OFF;
		} else if (p_s_level.toUpperCase().contentEquals("SEVERE")) {
			return java.util.logging.Level.SEVERE;
		} else if (p_s_level.toUpperCase().contentEquals("WARNING")) {
			return java.util.logging.Level.WARNING;
		} else if (p_s_level.toUpperCase().contentEquals("INFO")) {
			return java.util.logging.Level.INFO;
		} else if (p_s_level.toUpperCase().contentEquals("CONFIG")) {
			return java.util.logging.Level.CONFIG;
		} else if (p_s_level.toUpperCase().contentEquals("FINE")) {
			return java.util.logging.Level.FINE;
		} else if (p_s_level.toUpperCase().contentEquals("FINER")) {
			return java.util.logging.Level.FINER;
		} else if (p_s_level.toUpperCase().contentEquals("FINEST")) {
			return java.util.logging.Level.FINEST;
		} else if (p_s_level.toUpperCase().contentEquals("MASS")) {
			return java.util.logging.Level.FINEST;
		} else if (p_s_level.toUpperCase().contentEquals("ALL")) {
			return java.util.logging.Level.ALL;
		} else {
			return java.util.logging.Level.SEVERE; 
		}
	}
	
	/**
	 * Converts a byte log level parameter to a matching net.forestany.forestj.lib.Global logging level
	 * 
	 * @param 	p_by_level					byte log level parameter
	 * @return								matching net.forestany.forestj.lib.Global logging level
	 * @throws	IllegalArgumentException	parameter is not set correctly
	 * @see									net.forestany.forestj.lib.Global
	 */
	public static String byteLevelToStringLoggingLevel(byte p_by_level) throws IllegalArgumentException {
		if (p_by_level == net.forestany.forestj.lib.Global.OFF) {
			return "OFF";
		} else if (p_by_level == net.forestany.forestj.lib.Global.SEVERE) {
			return "SEVERE";
		} else if (p_by_level == net.forestany.forestj.lib.Global.WARNING) {
			return "WARNING";
		} else if (p_by_level == net.forestany.forestj.lib.Global.INFO) {
			return "INFO";
		} else if (p_by_level == net.forestany.forestj.lib.Global.CONFIG) {
			return "CONFIG";
		} else if (p_by_level == net.forestany.forestj.lib.Global.FINE) {
			return "FINE";
		} else if (p_by_level == net.forestany.forestj.lib.Global.FINER) {
			return "FINER";
		} else if (p_by_level == net.forestany.forestj.lib.Global.FINEST) {
			return "FINEST";
		} else if (p_by_level == net.forestany.forestj.lib.Global.MASS) {
			return "MASS";
		} else if (p_by_level == net.forestany.forestj.lib.Global.ALL) {
			return "ALL";
		} else {
			throw new IllegalArgumentException("Level parameter is illegal");
		}
	}
	
	/**
	 * Method to load log configuration settings for all logger, should be started very early in the program
	 * each setting-line must end with a linebreak \n
	 * 
	 * @throws java.io.IOException		if there is an error in the given log configuration string stream or there are IO problems opening the files
	 */
	public void loadConfig() throws java.io.IOException {	
		String s_logConfig = "";
		
		/* check if we use console and file or just console or just file logging */
		if ( (this.b_useConsole) && (this.b_useFile) ) {
			s_logConfig += "handlers = java.util.logging.ConsoleHandler, java.util.logging.FileHandler" + "\n";
		} else if ( (this.b_useConsole) && (!this.b_useFile) ) {
			s_logConfig += "handlers = java.util.logging.ConsoleHandler" + "\n";
		} else if ( (!this.b_useConsole) && (this.b_useFile) ) {
			s_logConfig += "handlers = java.util.logging.FileHandler" + "\n";
		} else {
			/* default would be file logging */
			s_logConfig += "handlers = java.util.logging.FileHandler" + "\n";
		}
		
		/* set console level with main level if it is not set */
		if (this.e_consoleLevel == null) {
			this.e_consoleLevel = this.e_level;
		}
		
		/* set file level with main level if it is not set */
		if (this.e_fileLevel == null) {
			this.e_fileLevel = this.e_level;
		}
		
		/* add main level */
		s_logConfig += ".level =" + this.e_level + "\n";
		
		/* add console log configuration settings */
		if (this.b_useConsole) {
			s_logConfig += "java.util.logging.ConsoleHandler.level = " + this.e_consoleLevel + "\n";
			s_logConfig += "java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter" + "\n";
		}
		
		/* add file log configuration settings */
		if (this.b_useFile) {
			s_logConfig += "java.util.logging.FileHandler.level = " + this.e_fileLevel + "\n";
			s_logConfig += "java.util.logging.FileHandler.pattern = " + this.s_filePath + this.s_filePattern + "\n";
			s_logConfig += "java.util.logging.FileHandler.append = " + this.b_fileAppend + "\n";
			s_logConfig += "java.util.logging.FileHandler.limit = " + this.i_fileLimit + "\n";
			s_logConfig += "java.util.logging.FileHandler.count = " + this.i_fileCount + "\n";
			s_logConfig += "java.util.logging.FileHandler.formatter = java.util.logging.SimpleFormatter" + "\n";
		}
		
		/* set log line format */
		s_logConfig += "java.util.logging.SimpleFormatter.format = " + this.s_formatLogLine;
		
		/* read/load configuration settings as string stream */
		java.util.logging.LogManager.getLogManager().readConfiguration(new java.io.ByteArrayInputStream(s_logConfig.toString().getBytes("UTF-8")));
	}
	
	/**
	 * Method to load log configuration settings to a logger with a standardized formatter, should be started very early in the program
	 * 
	 * @param	p_o_logger					logger object where the settings will be set
	 * @throws	NullPointerException		Logger or Formatter parameter are not set
	 * @throws 	java.io.IOException			if there are IO problems opening the files
	 * @throws 	SecurityException 			if a security manager exists and if the caller does not have LoggingPermission
	 * @see		java.util.logging.Logger
	 */
	public void loadConfig(java.util.logging.Logger p_o_logger) throws NullPointerException, SecurityException, java.io.IOException {
		if (p_o_logger == null) {
			throw new NullPointerException("Logger parameter is null");
		}
		
		java.util.logging.Formatter o_formatter = new java.util.logging.Formatter() {
			@Override
	        public String format(java.util.logging.LogRecord o_logRecord) {
				return "" 
					+ "[" +java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + "]"
					+ " [" + String.format("%1$7s", o_logRecord.getLevel().getLocalizedName()) + "]"
					+ " [" + String.format("%1$50s", o_logRecord.getSourceClassName()) + "]"
					+ " [" + String.format("%1$25s", o_logRecord.getSourceMethodName()) + "]"
					+ " " + o_logRecord.getMessage()
					+ "\n"
				;
	        }
		};
		
		/* set console level with main level if it is not set */
		if (this.e_consoleLevel == null) {
			this.e_consoleLevel = this.e_level;
		}
		
		/* set file level with main level if it is not set */
		if (this.e_fileLevel == null) {
			this.e_fileLevel = this.e_level;
		}
		
		/* add logger level */
		p_o_logger.setLevel(this.e_level);
		p_o_logger.setUseParentHandlers(false);
		
		/* add console log handler and settings */
		if (this.b_useConsole) {
			java.util.logging.ConsoleHandler o_consoleHandler = new java.util.logging.ConsoleHandler();
			o_consoleHandler.setLevel(this.e_consoleLevel);
			o_consoleHandler.setFormatter(o_formatter);
			
			p_o_logger.addHandler(o_consoleHandler);
		}
		
		/* add file log configuration settings */
		if (this.b_useFile) {
			java.util.logging.FileHandler o_fileHandler = new java.util.logging.FileHandler(this.s_filePath + this.s_filePattern, this.i_fileLimit, this.i_fileCount, this.b_fileAppend);
			o_fileHandler.setLevel(this.e_fileLevel);
			o_fileHandler.setFormatter(o_formatter);
			
			p_o_logger.addHandler(o_fileHandler);
		}
	}
	
	/**
	 * Method to load log configuration settings to a logger with a standardized formatter, should be started very early in the program
	 * 
	 * @param	p_o_logger					logger object where the settings will be set
	 * @param	p_o_formatter				formatter object which tells the logger how to format log lines in console or file
	 * @throws	NullPointerException		Logger or Formatter parameter are not set
	 * @throws 	java.io.IOException			if there are IO problems opening the files
	 * @throws 	SecurityException 			if a security manager exists and if the caller does not have LoggingPermission
	 * @see		java.util.logging.Logger
	 * @see		java.util.logging.Formatter
	 */
	public void loadConfig(java.util.logging.Logger p_o_logger, java.util.logging.Formatter p_o_formatter) throws NullPointerException, SecurityException, java.io.IOException {	
		if (p_o_logger == null) {
			throw new NullPointerException("Logger parameter is null");
		}
		
		if (p_o_formatter == null) {
			throw new NullPointerException("Formatter parameter is null");
		}
		
		/* set console level with main level if it is not set */
		if (this.e_consoleLevel == null) {
			this.e_consoleLevel = this.e_level;
		}
		
		/* set file level with main level if it is not set */
		if (this.e_fileLevel == null) {
			this.e_fileLevel = this.e_level;
		}
		
		/* add logger level */
		p_o_logger.setLevel(this.e_level);
		p_o_logger.setUseParentHandlers(false);
		
		/* add console log handler and settings */
		if (this.b_useConsole) {
			java.util.logging.ConsoleHandler o_consoleHandler = new java.util.logging.ConsoleHandler();
			o_consoleHandler.setLevel(this.e_consoleLevel);
			o_consoleHandler.setFormatter(p_o_formatter);
			
			p_o_logger.addHandler(o_consoleHandler);
		}
		
		/* add file log configuration settings */
		if (this.b_useFile) {
			java.util.logging.FileHandler o_fileHandler = new java.util.logging.FileHandler(this.s_filePath + this.s_filePattern, this.i_fileLimit, this.i_fileCount, this.b_fileAppend);
			o_fileHandler.setLevel(this.e_fileLevel);
			o_fileHandler.setFormatter(p_o_formatter);
			
			p_o_logger.addHandler(o_fileHandler);
		}
	}
	
	/**
	 * Method to initiate test logging settings, only using console clogging
	 * @throws Exception		anything what might happen during loading config
	 */
	public static void initiateTestLogging() throws Exception {
		try {
			net.forestany.forestj.lib.Global.get().resetLog();
			
			net.forestany.forestj.lib.LoggingConfig o_loggingConfigAll = new net.forestany.forestj.lib.LoggingConfig();
			o_loggingConfigAll.setLevel(java.util.logging.Level.FINEST);
			o_loggingConfigAll.setUseConsole(true);
			
			o_loggingConfigAll.setConsoleLevel(java.util.logging.Level.SEVERE);
			//o_loggingConfigAll.setConsoleLevel(java.util.logging.Level.WARNING);
			//o_loggingConfigAll.setConsoleLevel(java.util.logging.Level.INFO);
			//o_loggingConfigAll.setConsoleLevel(java.util.logging.Level.CONFIG);
			//o_loggingConfigAll.setConsoleLevel(java.util.logging.Level.FINE);
			//o_loggingConfigAll.setConsoleLevel(java.util.logging.Level.FINER);
			//o_loggingConfigAll.setConsoleLevel(java.util.logging.Level.FINEST);
			
			o_loggingConfigAll.loadConfig();
			
			net.forestany.forestj.lib.Global.get().by_logControl = net.forestany.forestj.lib.Global.OFF;
			net.forestany.forestj.lib.Global.get().by_internalLogControl = net.forestany.forestj.lib.Global.SEVERE;
			//net.forestany.forestj.lib.Global.get().by_internalLogControl = net.forestany.forestj.lib.Global.SEVERE + net.forestany.forestj.lib.Global.WARNING;
			//net.forestany.forestj.lib.Global.get().by_internalLogControl = net.forestany.forestj.lib.Global.SEVERE + net.forestany.forestj.lib.Global.WARNING + net.forestany.forestj.lib.Global.INFO;
			//net.forestany.forestj.lib.Global.get().by_internalLogControl = net.forestany.forestj.lib.Global.SEVERE + net.forestany.forestj.lib.Global.WARNING + net.forestany.forestj.lib.Global.INFO + net.forestany.forestj.lib.Global.CONFIG;
			//net.forestany.forestj.lib.Global.get().by_internalLogControl = net.forestany.forestj.lib.Global.SEVERE + net.forestany.forestj.lib.Global.WARNING + net.forestany.forestj.lib.Global.INFO + net.forestany.forestj.lib.Global.CONFIG + net.forestany.forestj.lib.Global.FINE;
			//net.forestany.forestj.lib.Global.get().by_internalLogControl = net.forestany.forestj.lib.Global.SEVERE + net.forestany.forestj.lib.Global.WARNING + net.forestany.forestj.lib.Global.INFO + net.forestany.forestj.lib.Global.CONFIG + net.forestany.forestj.lib.Global.FINE + net.forestany.forestj.lib.Global.FINER;
			//net.forestany.forestj.lib.Global.get().by_internalLogControl = net.forestany.forestj.lib.Global.ALMOST_ALL;
			//net.forestany.forestj.lib.Global.get().by_internalLogControl = net.forestany.forestj.lib.Global.ALL;
		} catch (Exception o_exc) {
			throw o_exc;
		}
	}
}
