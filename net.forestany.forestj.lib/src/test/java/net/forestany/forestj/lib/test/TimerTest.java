package net.forestany.forestj.lib.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TimerTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testTimer() {
		try {
			String s_currentDirectory = net.forestany.forestj.lib.io.File.getCurrentDirectory();
			String s_testDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "testTimer" + net.forestany.forestj.lib.io.File.DIR;
			
			if ( net.forestany.forestj.lib.io.File.folderExists(s_testDirectory) ) {
				net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			}
			
			net.forestany.forestj.lib.io.File.createDirectory(s_testDirectory);
			assertTrue(
					net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
					"directory[" + s_testDirectory + "] does not exist"
			);
				
			String s_file = s_testDirectory + "timer.log";
			net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(s_file, true);
			assertTrue(
					net.forestany.forestj.lib.io.File.exists(s_file),
					"file[" + s_file + "] does not exist"
			);
			assertTrue(
					o_file.getFileLines() == 0,
					"file lines != 0"
			);
			
			java.time.LocalTime o_startTime = java.time.LocalTime.now().plusSeconds(10);
			String s_dateInterval = "PT5S";
			
			net.forestany.forestj.lib.Timer o_timer = null;
			
			net.forestany.forestj.lib.TimerTask o_task = new net.forestany.forestj.lib.TimerTask(new net.forestany.forestj.lib.DateInterval(s_dateInterval), o_startTime) {
				private int i_cnt = 1;
				
				@Override public void runTimerTask() throws Exception {
					net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(s_file);
					o_file.appendLine(java.time.LocalTime.now() + "\t" + "Work in progress..." + "Counter: " + i_cnt++);
				}
			};
			
			o_task.excludeWeekday(java.time.DayOfWeek.SATURDAY);
			o_task.excludeWeekday(java.time.DayOfWeek.SUNDAY);
			o_timer = new net.forestany.forestj.lib.Timer(o_task);
			
			o_timer.startTimer();
            
			Thread.sleep(26000);
    		
			o_timer.stopTimer();
			
			o_file = new net.forestany.forestj.lib.io.File(s_file);
			
			java.time.DayOfWeek o_dayOfWeek = java.time.DayOfWeek.from(java.time.LocalDate.now()); 
			
			if ( (o_dayOfWeek == java.time.DayOfWeek.SATURDAY) || (o_dayOfWeek == java.time.DayOfWeek.SUNDAY) ) {
				assertTrue(
						o_file.getFileLines() == 0,
						"file lines != 0"
				);
			} else {
				assertTrue(
						o_file.getFileLines() == 4,
						"file lines != 4"
				);
				
				assertTrue(
						o_file.readLine(1).endsWith("Counter: 1"),
						"line #1 does not end with 'Counter: 1'"
				);
				assertTrue(
						o_file.readLine(2).endsWith("Counter: 2"),
						"line #2 does not end with 'Counter: 2'"
				);
				assertTrue(
						o_file.readLine(3).endsWith("Counter: 3"),
						"line #3 does not end with 'Counter: 3'"
				);
				assertTrue(
						o_file.readLine(4).endsWith("Counter: 4"),
						"line #4 does not end with 'Counter: 4'"
				);
			}
			
			net.forestany.forestj.lib.io.File.deleteDirectory(s_testDirectory);
			assertFalse(
					net.forestany.forestj.lib.io.File.folderExists(s_testDirectory),
					"directory[" + s_testDirectory + "] does exist"
			);
        } catch(Exception o_exc) {
        	fail(o_exc.getMessage());
        }
	}
}
