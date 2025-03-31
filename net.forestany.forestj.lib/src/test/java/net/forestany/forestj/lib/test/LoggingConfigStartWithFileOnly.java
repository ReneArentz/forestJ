package net.forestany.forestj.lib.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LoggingConfigStartWithFileOnly {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testLoggingConfig() {
		try {
			if (net.forestany.forestj.lib.io.File.exists(java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingFromStart.log.0")) {
				net.forestany.forestj.lib.io.File.deleteFile(java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingFromStart.log.0");
			}
			
			String s_logFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "logging" + net.forestany.forestj.lib.io.File.DIR + "logstart.txt";
			
			net.forestany.forestj.lib.Global.get().resetLog();
			net.forestany.forestj.lib.LoggingConfig o_loggingConfig = new net.forestany.forestj.lib.LoggingConfig(s_logFile);
			o_loggingConfig.loadConfig();
			
			net.forestany.forestj.lib.Global.get().by_logControl = 0x7F; /* all levels */
			net.forestany.forestj.lib.Global.get().by_internalLogControl = 0xD; /* only severe, info, config */
			
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
			
			java.util.List<String> a_lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingFromStart.log.0" ));
			
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
			
			java.nio.file.Files.delete(java.nio.file.Paths.get( java.nio.file.Paths.get("").toAbsolutePath().toString() + net.forestany.forestj.lib.io.File.DIR + "testLoggingFromStart.log.0" ));
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
}
