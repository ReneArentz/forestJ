package de.forestj.lib.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LoggingConfigAndGlobalTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testLoggingConfig() {
		try {
			String s_otherLogFile = java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingOtherLoggingImplementation.log";
			
			if (de.forestj.lib.io.File.exists(java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingConfigAll.log.0")) {
				de.forestj.lib.io.File.deleteFile(java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingConfigAll.log.0");
			}
			
			if (de.forestj.lib.io.File.exists(java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingConfig.log.0")) {
				de.forestj.lib.io.File.deleteFile(java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingConfig.log.0");
			}
			
			if (de.forestj.lib.io.File.exists(java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingConfigInternal.log.0")) {
				de.forestj.lib.io.File.deleteFile(java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingConfigInternal.log.0");
			}
			
			if (de.forestj.lib.io.File.exists(System.getProperty("java.io.tmpdir") + de.forestj.lib.io.File.DIR + "testLoggingConfigFromFile.log.0")) {
				de.forestj.lib.io.File.deleteFile(System.getProperty("java.io.tmpdir") + de.forestj.lib.io.File.DIR + "testLoggingConfigFromFile.log.0");
			}
			
			if (de.forestj.lib.io.File.exists(java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingConfigInternalFromFile.log.0")) {
				de.forestj.lib.io.File.deleteFile(java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingConfigInternalFromFile.log.0");
			}
			
			if (de.forestj.lib.io.File.exists(s_otherLogFile)) {
				de.forestj.lib.io.File.deleteFile(s_otherLogFile);
			}
			
			/* ##################################################################################################### */
			
			de.forestj.lib.Global.get().resetLog();
			
			de.forestj.lib.LoggingConfig o_loggingConfigAll = new de.forestj.lib.LoggingConfig();
			
			o_loggingConfigAll.setLevel(java.util.logging.Level.FINEST);
			o_loggingConfigAll.setUseConsole(false);
			o_loggingConfigAll.setConsoleLevel(java.util.logging.Level.FINEST);
			
			o_loggingConfigAll.setUseFile(true);
			o_loggingConfigAll.setFileLevel(java.util.logging.Level.FINEST);
			o_loggingConfigAll.setFilePath(java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR);
			o_loggingConfigAll.setFilePattern("testLoggingConfigAll.log");
			o_loggingConfigAll.setFileLimit(1000000); /* ~ 1.0 MB */
			o_loggingConfigAll.setFileCount(25);
			
			o_loggingConfigAll.loadConfig();
			
			de.forestj.lib.Global.get().by_logControl = de.forestj.lib.Global.ALL; /* all levels */
			de.forestj.lib.Global.get().by_internalLogControl = de.forestj.lib.Global.get().by_logControl;
			
			de.forestj.lib.Global.logSevere("logSevere");
			de.forestj.lib.Global.logWarning("logWarning");
			de.forestj.lib.Global.log("log");
			de.forestj.lib.Global.logConfig("logConfig");
			de.forestj.lib.Global.logFine("logFine");
			de.forestj.lib.Global.logFiner("logFiner");
			de.forestj.lib.Global.logFinest("logFinest");
			
			de.forestj.lib.Global.ilogSevere("ilogSevere");
			de.forestj.lib.Global.ilogWarning("ilogWarning");
			de.forestj.lib.Global.ilog("ilog");
			de.forestj.lib.Global.ilogConfig("ilogConfig");
			de.forestj.lib.Global.ilogFine("ilogFine");
			de.forestj.lib.Global.ilogFiner("ilogFiner");
			de.forestj.lib.Global.ilogFinest("ilogFinest");
			
			de.forestj.lib.Global.get().resetLog();
			
			java.util.List<String> a_lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingConfigAll.log.0" ));
			
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
			
			java.nio.file.Files.delete(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingConfigAll.log.0" ));
			
			/* ##################################################################################################### */
			
			de.forestj.lib.LoggingConfig o_loggingConfig = new de.forestj.lib.LoggingConfig();
			
			o_loggingConfig.setLevel(java.util.logging.Level.FINEST);
			o_loggingConfig.setUseConsole(false);
			o_loggingConfig.setConsoleLevel(java.util.logging.Level.FINEST);
			
			o_loggingConfig.setUseFile(true);
			o_loggingConfig.setFileLevel(java.util.logging.Level.FINEST);
			o_loggingConfig.setFilePath(java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR);
			o_loggingConfig.setFilePattern("testLoggingConfig.log");
			o_loggingConfig.setFileLimit(1000000); /* ~ 1.0 MB */
			o_loggingConfig.setFileCount(25);
			
			o_loggingConfig.loadConfig(de.forestj.lib.Global.get().LOG);
			
			de.forestj.lib.LoggingConfig o_loggingConfigInternal = new de.forestj.lib.LoggingConfig();
			
			o_loggingConfigInternal.setLevel(java.util.logging.Level.FINEST);
			o_loggingConfigInternal.setUseConsole(false);
			o_loggingConfigInternal.setConsoleLevel(java.util.logging.Level.FINEST);
			
			o_loggingConfigInternal.setUseFile(true);
			o_loggingConfigInternal.setFileLevel(java.util.logging.Level.FINEST);
			o_loggingConfigInternal.setFilePath(java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR);
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
			
			o_loggingConfigInternal.loadConfig(de.forestj.lib.Global.get().ILOG, o_formatter);
			
			de.forestj.lib.Global.get().by_logControl = de.forestj.lib.Global.ALL; /* all levels */
			de.forestj.lib.Global.get().by_internalLogControl = de.forestj.lib.Global.SEVERE + de.forestj.lib.Global.INFO + de.forestj.lib.Global.FINE + de.forestj.lib.Global.FINER; /* only severe, info, fine, finer */
			
			de.forestj.lib.Global.logSevere("logSevere");
			de.forestj.lib.Global.ilogSevere("ilogSevere");
			de.forestj.lib.Global.logWarning("logWarning");
			de.forestj.lib.Global.ilogWarning("ilogWarning");
			de.forestj.lib.Global.log("log");
			de.forestj.lib.Global.ilog("ilog");
			de.forestj.lib.Global.logConfig("logConfig");
			de.forestj.lib.Global.ilogConfig("ilogConfig");
			de.forestj.lib.Global.logFine("logFine");
			de.forestj.lib.Global.ilogFine("ilogFine");
			de.forestj.lib.Global.logFiner("logFiner");
			de.forestj.lib.Global.ilogFiner("ilogFiner");
			de.forestj.lib.Global.logFinest("logFinest");
			de.forestj.lib.Global.ilogFinest("ilogFinest");
			
			de.forestj.lib.Global.get().resetLog();
			
			a_lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingConfig.log.0" ));
			
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
			
			java.nio.file.Files.delete(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingConfig.log.0" ));
			
			a_lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingConfigInternal.log.0" ));
			
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
			
			java.nio.file.Files.delete(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingConfigInternal.log.0" ));
			
			/* ##################################################################################################### */
			
			String s_logFile = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "logging" + de.forestj.lib.io.File.DIR + "log.txt";
			String s_ilogFile = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "logging" + de.forestj.lib.io.File.DIR + "ilog.txt";
			
			de.forestj.lib.LoggingConfig o_loggingConfigFromFile = new de.forestj.lib.LoggingConfig(s_logFile);
			
			o_loggingConfigFromFile.loadConfig(de.forestj.lib.Global.get().LOG);
			
			de.forestj.lib.LoggingConfig o_loggingConfigInternalFromFile = new de.forestj.lib.LoggingConfig(s_ilogFile);
			
			java.util.logging.Formatter o_anotherFormatter = new java.util.logging.Formatter() {
				@Override
		        public String format(java.util.logging.LogRecord o_logRecord) {
					return "Start of line:" 
						+ " " + o_logRecord.getMessage()
						+ "\n"
					;
		        }
			};
			
			o_loggingConfigInternalFromFile.loadConfig(de.forestj.lib.Global.get().ILOG, o_anotherFormatter);
			
			de.forestj.lib.Global.get().itf_logging = new de.forestj.lib.Global.IDelegate() {
				@Override
				public void OtherImplementation(boolean p_b_internalLog, byte p_by_logLevel, String p_s_className, String p_s_methodName, String p_s_logMessage) {
					try {
						if (p_by_logLevel > 0x00) {
							de.forestj.lib.io.File o_otherLogFile = new de.forestj.lib.io.File(s_otherLogFile, !de.forestj.lib.io.File.exists(s_otherLogFile));
							o_otherLogFile.appendLine("[" + java.time.LocalDateTime.now() +  "]" + " " + ( (p_b_internalLog) ? "[Internal]" : "[Normal]" ) + " " + "[" + de.forestj.lib.LoggingConfig.byteLevelToStringLoggingLevel(p_by_logLevel) + "]" + " " + "[" + p_s_className + "]" + " " + "[" + p_s_methodName + "]" + "   " + p_s_logMessage);
						}
					} catch (Exception o_exc) {
						fail(o_exc.getMessage());
					}
				}
			};
			
			de.forestj.lib.Global.get().by_logControl = de.forestj.lib.Global.ALL; /* all levels */
			de.forestj.lib.Global.get().by_internalLogControl = de.forestj.lib.Global.SEVERE + de.forestj.lib.Global.INFO + de.forestj.lib.Global.CONFIG; /* only severe, info, config */
			
			de.forestj.lib.Global.logSevere("logSevere");
			de.forestj.lib.Global.ilogSevere("ilogSevere");
			de.forestj.lib.Global.logWarning("logWarning");
			de.forestj.lib.Global.ilogWarning("ilogWarning");
			de.forestj.lib.Global.log("log");
			de.forestj.lib.Global.ilog("ilog");
			de.forestj.lib.Global.logConfig("logConfig");
			de.forestj.lib.Global.ilogConfig("ilogConfig");
			de.forestj.lib.Global.logFine("logFine");
			de.forestj.lib.Global.ilogFine("ilogFine");
			de.forestj.lib.Global.logFiner("logFiner");
			de.forestj.lib.Global.ilogFiner("ilogFiner");
			de.forestj.lib.Global.logFinest("logFinest");
			de.forestj.lib.Global.ilogFinest("ilogFinest");
			
			de.forestj.lib.Global.get().resetLog();
			
			a_lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get( System.getProperty("java.io.tmpdir") + de.forestj.lib.io.File.DIR + "testLoggingConfigFromFile.log.0" ));
			
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
			
			java.nio.file.Files.delete(java.nio.file.Paths.get( System.getProperty("java.io.tmpdir") + de.forestj.lib.io.File.DIR + "testLoggingConfigFromFile.log.0" ));
			
			a_lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingConfigInternalFromFile.log.0" ));
			
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
			
			java.nio.file.Files.delete(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingConfigInternalFromFile.log.0" ));
			
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
			
			if (de.forestj.lib.io.File.exists(s_otherLogFile)) {
				de.forestj.lib.io.File.deleteFile(s_otherLogFile);
			}
						
			/* ##################################################################################################### */
			
			String s_logNotCompleteFile = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "logging" + de.forestj.lib.io.File.DIR + "log_not_complete.txt";
			
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
		        new de.forestj.lib.LoggingConfig(s_logNotCompleteFile);
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
