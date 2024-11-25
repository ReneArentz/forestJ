package de.forestj.lib.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LoggingConfigStartWithFileOnly {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testLoggingConfig() {
		try {
			if (de.forestj.lib.io.File.exists(java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingFromStart.log.0")) {
				de.forestj.lib.io.File.deleteFile(java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingFromStart.log.0");
			}
			
			String s_logFile = de.forestj.lib.io.File.getCurrentDirectory() + de.forestj.lib.io.File.DIR + "src" + de.forestj.lib.io.File.DIR + "test" + de.forestj.lib.io.File.DIR + "resources" + de.forestj.lib.io.File.DIR + "logging" + de.forestj.lib.io.File.DIR + "logstart.txt";
			
			de.forestj.lib.Global.get().resetLog();
			de.forestj.lib.LoggingConfig o_loggingConfig = new de.forestj.lib.LoggingConfig(s_logFile);
			o_loggingConfig.loadConfig();
			
			de.forestj.lib.Global.get().by_logControl = 0x7F; /* all levels */
			de.forestj.lib.Global.get().by_internalLogControl = 0xD; /* only severe, info, config */
			
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
			
			java.util.List<String> a_lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingFromStart.log.0" ));
			
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
							a_lines.get(i).contains("ilogSevere"),
							"#5 line != 'ilogSevere'"
					);
				} else if (i == 5) {
					assertTrue(
							a_lines.get(i).contains("ilog"),
							"#6 line != 'ilog'"
					);
				} else if (i == 6) {
					assertTrue(
							a_lines.get(i).contains("ilogConfig"),
							"#7 line != 'ilogConfig'"
					);
				}
			}
			
			java.nio.file.Files.delete(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + de.forestj.lib.io.File.DIR + "testLoggingFromStart.log.0" ));
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
