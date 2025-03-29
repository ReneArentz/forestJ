package net.forestany.forestj.lib.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LoggingConfigAndGlobalTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testLoggingConfig() {
		try {
			String s_otherLogFile = java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingOtherLoggingImplementation.log";
			
			if (net.forestany.forestj.lib.io.File.exists(java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfigAll.log.0")) {
				net.forestany.forestj.lib.io.File.deleteFile(java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfigAll.log.0");
			}
			
			if (net.forestany.forestj.lib.io.File.exists(java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfig.log.0")) {
				net.forestany.forestj.lib.io.File.deleteFile(java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfig.log.0");
			}
			
			if (net.forestany.forestj.lib.io.File.exists(java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfigInternal.log.0")) {
				net.forestany.forestj.lib.io.File.deleteFile(java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfigInternal.log.0");
			}
			
			if (net.forestany.forestj.lib.io.File.exists(System.getProperty("java.io.tmpdir") + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfigFromFile.log.0")) {
				net.forestany.forestj.lib.io.File.deleteFile(System.getProperty("java.io.tmpdir") + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfigFromFile.log.0");
			}
			
			if (net.forestany.forestj.lib.io.File.exists(java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfigInternalFromFile.log.0")) {
				net.forestany.forestj.lib.io.File.deleteFile(java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfigInternalFromFile.log.0");
			}
			
			if (net.forestany.forestj.lib.io.File.exists(s_otherLogFile)) {
				net.forestany.forestj.lib.io.File.deleteFile(s_otherLogFile);
			}
			
			/* ##################################################################################################### */
			
			net.forestany.forestj.lib.Global.get().resetLog();
			
			net.forestany.forestj.lib.LoggingConfig o_loggingConfigAll = new net.forestany.forestj.lib.LoggingConfig();
			
			o_loggingConfigAll.setLevel(java.util.logging.Level.FINEST);
			o_loggingConfigAll.setUseConsole(false);
			o_loggingConfigAll.setConsoleLevel(java.util.logging.Level.FINEST);
			
			o_loggingConfigAll.setUseFile(true);
			o_loggingConfigAll.setFileLevel(java.util.logging.Level.FINEST);
			o_loggingConfigAll.setFilePath(java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR);
			o_loggingConfigAll.setFilePattern("testLoggingConfigAll.log");
			o_loggingConfigAll.setFileLimit(1000000); /* ~ 1.0 MB */
			o_loggingConfigAll.setFileCount(25);
			
			o_loggingConfigAll.loadConfig();
			
			net.forestany.forestj.lib.Global.get().by_logControl = net.forestany.forestj.lib.Global.ALL; /* all levels */
			net.forestany.forestj.lib.Global.get().by_internalLogControl = net.forestany.forestj.lib.Global.get().by_logControl;
			
			net.forestany.forestj.lib.Global.logSevere("logSevere");
			net.forestany.forestj.lib.Global.logWarning("logWarning");
			net.forestany.forestj.lib.Global.log("log");
			net.forestany.forestj.lib.Global.logConfig("logConfig");
			net.forestany.forestj.lib.Global.logFine("logFine");
			net.forestany.forestj.lib.Global.logFiner("logFiner");
			net.forestany.forestj.lib.Global.logFinest("logFinest");
			
			net.forestany.forestj.lib.Global.ilogSevere("ilogSevere");
			net.forestany.forestj.lib.Global.ilogWarning("ilogWarning");
			net.forestany.forestj.lib.Global.ilog("ilog");
			net.forestany.forestj.lib.Global.ilogConfig("ilogConfig");
			net.forestany.forestj.lib.Global.ilogFine("ilogFine");
			net.forestany.forestj.lib.Global.ilogFiner("ilogFiner");
			net.forestany.forestj.lib.Global.ilogFinest("ilogFinest");
			
			net.forestany.forestj.lib.Global.get().resetLog();
			
			java.util.List<String> a_lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfigAll.log.0" ));
			
			for (int i = 0; i < a_lines.size(); i++) {
				if (i == 0) {
					assertTrue(
							a_lines.get(i).contains("logSevere"),
							"#1 line != 'logSevere'"
					);
				} else if (i == 1) {
					assertTrue(
							a_lines.get(i).contains("logWarning"),
							"#2 line != 'logWarning'"
					);
				} else if (i == 2) {
					assertTrue(
							a_lines.get(i).contains("log"),
							"#3 line != 'log'"
					);
				} else if (i == 3) {
					assertTrue(
							a_lines.get(i).contains("logConfig"),
							"#4 line != 'logConfig'"
					);
				} else if (i == 4) {
					assertTrue(
							a_lines.get(i).contains("logFine"),
							"#5 line != 'logFine'"
					);
				} else if (i == 5) {
					assertTrue(
							a_lines.get(i).contains("logFiner"),
							"#6 line != 'logFiner'"
					);
				} else if (i == 6) {
					assertTrue(
							a_lines.get(i).contains("logFinest"),
							"#7 line != 'logFinest'"
					);
				} else if (i == 7) {
					assertTrue(
							a_lines.get(i).contains("ilogSevere"),
							"#8 line != 'ilogSevere'"
					);
				} else if (i == 8) {
					assertTrue(
							a_lines.get(i).contains("ilogWarning"),
							"#9 line != 'ilogWarning'"
					);
				} else if (i == 9) {
					assertTrue(
							a_lines.get(i).contains("ilog"),
							"#10 line != 'ilog'"
					);
				} else if (i == 10) {
					assertTrue(
							a_lines.get(i).contains("ilogConfig"),
							"#11 line != 'ilogConfig'"
					);
				} else if (i == 11) {
					assertTrue(
							a_lines.get(i).contains("ilogFine"),
							"#12 line != 'ilogFine'"
					);
				} else if (i == 12) {
					assertTrue(
							a_lines.get(i).contains("ilogFiner"),
							"#13 line != 'ilogFiner'"
					);
				} else if (i == 13) {
					assertTrue(
							a_lines.get(i).contains("ilogFinest"),
							"#14 line != 'ilogFinest'"
					);
				}
			}
			
			java.nio.file.Files.delete(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfigAll.log.0" ));
			
			/* ##################################################################################################### */
			
			net.forestany.forestj.lib.LoggingConfig o_loggingConfig = new net.forestany.forestj.lib.LoggingConfig();
			
			o_loggingConfig.setLevel(java.util.logging.Level.FINEST);
			o_loggingConfig.setUseConsole(false);
			o_loggingConfig.setConsoleLevel(java.util.logging.Level.FINEST);
			
			o_loggingConfig.setUseFile(true);
			o_loggingConfig.setFileLevel(java.util.logging.Level.FINEST);
			o_loggingConfig.setFilePath(java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR);
			o_loggingConfig.setFilePattern("testLoggingConfig.log");
			o_loggingConfig.setFileLimit(1000000); /* ~ 1.0 MB */
			o_loggingConfig.setFileCount(25);
			
			o_loggingConfig.loadConfig(net.forestany.forestj.lib.Global.get().LOG);
			
			net.forestany.forestj.lib.LoggingConfig o_loggingConfigInternal = new net.forestany.forestj.lib.LoggingConfig();
			
			o_loggingConfigInternal.setLevel(java.util.logging.Level.FINEST);
			o_loggingConfigInternal.setUseConsole(false);
			o_loggingConfigInternal.setConsoleLevel(java.util.logging.Level.FINEST);
			
			o_loggingConfigInternal.setUseFile(true);
			o_loggingConfigInternal.setFileLevel(java.util.logging.Level.FINEST);
			o_loggingConfigInternal.setFilePath(java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR);
			o_loggingConfigInternal.setFilePattern("testLoggingConfigInternal.log");
			o_loggingConfigInternal.setFileLimit(1000000); /* ~ 1.0 MB */
			o_loggingConfigInternal.setFileCount(25);
			
			java.util.logging.Formatter o_formatter = new java.util.logging.Formatter() {
				@Override
		        public String format(java.util.logging.LogRecord o_logRecord) {
					return "Start of line:" 
						+ " " + o_logRecord.getMessage()
						+ "\n"
					;
		        }
			};
			
			o_loggingConfigInternal.loadConfig(net.forestany.forestj.lib.Global.get().ILOG, o_formatter);
			
			net.forestany.forestj.lib.Global.get().by_logControl = net.forestany.forestj.lib.Global.ALL; /* all levels */
			net.forestany.forestj.lib.Global.get().by_internalLogControl = net.forestany.forestj.lib.Global.SEVERE + net.forestany.forestj.lib.Global.INFO + net.forestany.forestj.lib.Global.FINE + net.forestany.forestj.lib.Global.FINER; /* only severe, info, fine, finer */
			
			net.forestany.forestj.lib.Global.logSevere("logSevere");
			net.forestany.forestj.lib.Global.ilogSevere("ilogSevere");
			net.forestany.forestj.lib.Global.logWarning("logWarning");
			net.forestany.forestj.lib.Global.ilogWarning("ilogWarning");
			net.forestany.forestj.lib.Global.log("log");
			net.forestany.forestj.lib.Global.ilog("ilog");
			net.forestany.forestj.lib.Global.logConfig("logConfig");
			net.forestany.forestj.lib.Global.ilogConfig("ilogConfig");
			net.forestany.forestj.lib.Global.logFine("logFine");
			net.forestany.forestj.lib.Global.ilogFine("ilogFine");
			net.forestany.forestj.lib.Global.logFiner("logFiner");
			net.forestany.forestj.lib.Global.ilogFiner("ilogFiner");
			net.forestany.forestj.lib.Global.logFinest("logFinest");
			net.forestany.forestj.lib.Global.ilogFinest("ilogFinest");
			
			net.forestany.forestj.lib.Global.get().resetLog();
			
			a_lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfig.log.0" ));
			
			for (int i = 0; i < a_lines.size(); i++) {
				if (i == 0) {
					assertTrue(
							a_lines.get(i).contains("logSevere"),
							"#1 line != 'logSevere'"
					);
				} else if (i == 1) {
					assertTrue(
							a_lines.get(i).contains("logWarning"),
							"#2 line != 'logWarning'"
					);
				} else if (i == 2) {
					assertTrue(
							a_lines.get(i).contains("log"),
							"#3 line != 'log'"
					);
				} else if (i == 3) {
					assertTrue(
							a_lines.get(i).contains("logConfig"),
							"#4 line != 'logConfig'"
					);
				} else if (i == 4) {
					assertTrue(
							a_lines.get(i).contains("logFine"),
							"#5 line != 'logFine'"
					);
				} else if (i == 5) {
					assertTrue(
							a_lines.get(i).contains("logFiner"),
							"#6 line != 'logFiner'"
					);
				} else if (i == 6) {
					assertTrue(
							a_lines.get(i).contains("logFinest"),
							"#7 line != 'logFinest'"
					);
				}
			}
			
			java.nio.file.Files.delete(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfig.log.0" ));
			
			a_lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfigInternal.log.0" ));
			
			for (int i = 0; i < a_lines.size(); i++) {
				if (i == 0) {
					assertTrue(
							a_lines.get(i).contains("ilogSevere"),
							"#1 line != 'ilogSevere'"
					);
				} else if (i == 1) {
					assertTrue(
							a_lines.get(i).contains("ilog"),
							"#2 line != 'ilog'"
					);
				} else if (i == 2) {
					assertTrue(
							a_lines.get(i).contains("ilogFine"),
							"#3 line != 'ilogFine'"
					);
				} else if (i == 3) {
					assertTrue(
							a_lines.get(i).contains("ilogFiner"),
							"#4 line != 'ilogFiner'"
					);
				}
			}
			
			java.nio.file.Files.delete(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfigInternal.log.0" ));
			
			/* ##################################################################################################### */
			
			String s_logFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "logging" + net.forestany.forestj.lib.io.File.DIR + "log.txt";
			String s_ilogFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "logging" + net.forestany.forestj.lib.io.File.DIR + "ilog.txt";
			
			net.forestany.forestj.lib.LoggingConfig o_loggingConfigFromFile = new net.forestany.forestj.lib.LoggingConfig(s_logFile);
			
			o_loggingConfigFromFile.loadConfig(net.forestany.forestj.lib.Global.get().LOG);
			
			net.forestany.forestj.lib.LoggingConfig o_loggingConfigInternalFromFile = new net.forestany.forestj.lib.LoggingConfig(s_ilogFile);
			
			java.util.logging.Formatter o_anotherFormatter = new java.util.logging.Formatter() {
				@Override
		        public String format(java.util.logging.LogRecord o_logRecord) {
					return "Start of line:" 
						+ " " + o_logRecord.getMessage()
						+ "\n"
					;
		        }
			};
			
			o_loggingConfigInternalFromFile.loadConfig(net.forestany.forestj.lib.Global.get().ILOG, o_anotherFormatter);
			
			net.forestany.forestj.lib.Global.get().itf_logging = new net.forestany.forestj.lib.Global.IDelegate() {
				@Override
				public void OtherImplementation(boolean p_b_internalLog, byte p_by_logLevel, String p_s_className, String p_s_methodName, String p_s_logMessage) {
					try {
						if (p_by_logLevel > 0x00) {
							net.forestany.forestj.lib.io.File o_otherLogFile = new net.forestany.forestj.lib.io.File(s_otherLogFile, !net.forestany.forestj.lib.io.File.exists(s_otherLogFile));
							o_otherLogFile.appendLine("[" + java.time.LocalDateTime.now() +  "]" + " " + ( (p_b_internalLog) ? "[Internal]" : "[Normal]" ) + " " + "[" + net.forestany.forestj.lib.LoggingConfig.byteLevelToStringLoggingLevel(p_by_logLevel) + "]" + " " + "[" + p_s_className + "]" + " " + "[" + p_s_methodName + "]" + "   " + p_s_logMessage);
						}
					} catch (Exception o_exc) {
						fail(o_exc.getMessage());
					}
				}
			};
			
			net.forestany.forestj.lib.Global.get().by_logControl = net.forestany.forestj.lib.Global.ALL; /* all levels */
			net.forestany.forestj.lib.Global.get().by_internalLogControl = net.forestany.forestj.lib.Global.SEVERE + net.forestany.forestj.lib.Global.INFO + net.forestany.forestj.lib.Global.CONFIG; /* only severe, info, config */
			
			net.forestany.forestj.lib.Global.logSevere("logSevere");
			net.forestany.forestj.lib.Global.ilogSevere("ilogSevere");
			net.forestany.forestj.lib.Global.logWarning("logWarning");
			net.forestany.forestj.lib.Global.ilogWarning("ilogWarning");
			net.forestany.forestj.lib.Global.log("log");
			net.forestany.forestj.lib.Global.ilog("ilog");
			net.forestany.forestj.lib.Global.logConfig("logConfig");
			net.forestany.forestj.lib.Global.ilogConfig("ilogConfig");
			net.forestany.forestj.lib.Global.logFine("logFine");
			net.forestany.forestj.lib.Global.ilogFine("ilogFine");
			net.forestany.forestj.lib.Global.logFiner("logFiner");
			net.forestany.forestj.lib.Global.ilogFiner("ilogFiner");
			net.forestany.forestj.lib.Global.logFinest("logFinest");
			net.forestany.forestj.lib.Global.ilogFinest("ilogFinest");
			
			net.forestany.forestj.lib.Global.get().resetLog();
			
			a_lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get( System.getProperty("java.io.tmpdir") + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfigFromFile.log.0" ));
			
			for (int i = 0; i < a_lines.size(); i++) {
				if (i == 0) {
					assertTrue(
							a_lines.get(i).contains("logSevere"),
							"#1 line != 'logSevere'"
					);
				} else if (i == 1) {
					assertTrue(
							a_lines.get(i).contains("logWarning"),
							"#2 line != 'logWarning'"
					);
				} else if (i == 2) {
					assertTrue(
							a_lines.get(i).contains("log"),
							"#3 line != 'log'"
					);
				} else if (i == 3) {
					assertTrue(
							a_lines.get(i).contains("logConfig"),
							"#4 line != 'logConfig'"
					);
				} else if (i == 4) {
					assertTrue(
							a_lines.get(i).contains("logFine"),
							"#5 line != 'logFine'"
					);
				} else if (i == 5) {
					assertTrue(
							a_lines.get(i).contains("logFiner"),
							"#6 line != 'logFiner'"
					);
				} else if (i == 6) {
					assertTrue(
							a_lines.get(i).contains("logFinest"),
							"#7 line != 'logFinest'"
					);
				}
			}
			
			java.nio.file.Files.delete(java.nio.file.Paths.get( System.getProperty("java.io.tmpdir") + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfigFromFile.log.0" ));
			
			a_lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfigInternalFromFile.log.0" ));
			
			for (int i = 0; i < a_lines.size(); i++) {
				if (i == 0) {
					assertTrue(
							a_lines.get(i).contains("ilogSevere"),
							"#1 line != 'ilogSevere'"
					);
				} else if (i == 1) {
					assertTrue(
							a_lines.get(i).contains("ilog"),
							"#2 line != 'ilog'"
					);
				} else if (i == 2) {
					assertTrue(
							a_lines.get(i).contains("ilogConfig"),
							"#3 line != 'ilogConfig'"
					);
				}
			}
			
			java.nio.file.Files.delete(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingConfigInternalFromFile.log.0" ));
			
			a_lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get( s_otherLogFile ));
			
			for (int i = 0; i < a_lines.size(); i++) {
				if (i == 0) {
					assertTrue(
							a_lines.get(i).contains("logSevere"),
							"#1 line != 'logSevere'"
					);
				} else if (i == 1) {
					assertTrue(
							a_lines.get(i).contains("ilogSevere"),
							"#2 line != 'ilogSevere'"
					);
				} else if (i == 2) {
					assertTrue(
							a_lines.get(i).contains("logWarning"),
							"#3 line != 'logWarning'"
					);
				} else if (i == 3) {
					assertTrue(
							a_lines.get(i).contains("log"),
							"#4 line != 'log'"
					);
				} else if (i == 4) {
					assertTrue(
							a_lines.get(i).contains("ilog"),
							"#5 line != 'ilog'"
					);
				} else if (i == 5) {
					assertTrue(
							a_lines.get(i).contains("logConfig"),
							"#6 line != 'logConfig'"
					);
				} else if (i == 6) {
					assertTrue(
							a_lines.get(i).contains("ilogConfig"),
							"#7 line != 'ilogConfig'"
					);
				} else if (i == 7) {
					assertTrue(
							a_lines.get(i).contains("logFine"),
							"#8 line != 'logFine'"
					);
				} else if (i == 8) {
					assertTrue(
							a_lines.get(i).contains("logFiner"),
							"#9 line != 'logFiner'"
					);
				} else if (i == 9) {
					assertTrue(
							a_lines.get(i).contains("logFinest"),
							"#10 line != 'logFinest'"
					);
				}
			}
			
			if (net.forestany.forestj.lib.io.File.exists(s_otherLogFile)) {
				net.forestany.forestj.lib.io.File.deleteFile(s_otherLogFile);
			}
						
			/* ##################################################################################################### */
			
			String s_logNotCompleteFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "logging" + net.forestany.forestj.lib.io.File.DIR + "log_not_complete.txt";
			
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
		        new net.forestany.forestj.lib.LoggingConfig(s_logNotCompleteFile);
		    });

		    String expectedMessage = "config file must activate at least console or file logging";
		    String actualMessage = exception.getMessage();

		    assertTrue(
				actualMessage.contains(expectedMessage),
				"config file for logging is not incomplete"
			);
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
