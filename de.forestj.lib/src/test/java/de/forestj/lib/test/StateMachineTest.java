package de.forestj.lib.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StateMachineTest {
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testStateMachineSimple() {
		try {
			String s_currentDirectory = de.forestj.lib.io.File.getCurrentDirectory();
			String s_testDirectory = s_currentDirectory + de.forestj.lib.io.File.DIR;
			String s_file = s_testDirectory + "StateMachineSimple.log";
			
			if (de.forestj.lib.io.File.exists(s_file)) {
				de.forestj.lib.io.File.deleteFile(s_file);
			}
			
			de.forestj.lib.io.File o_file = new de.forestj.lib.io.File(s_file, true);
			assertTrue(
					de.forestj.lib.io.File.exists(s_file),
					"file[" + s_file + "] does not exist"
			);
			assertTrue(
					o_file.getFileLines() == 0,
					"file lines != 0"
			);
			
			java.util.List<String> a_states = java.util.Arrays.asList("BEGINSTATE", "STATE1", "STATE2", "STATE3", "FINALSTATE");
			java.util.List<String> a_returnCodes = java.util.Arrays.asList("TRANSFER", "START", "IDLE");
			de.forestj.lib.StateMachine o_stateMachine = new de.forestj.lib.StateMachine(a_states, a_returnCodes);
			
			o_stateMachine.addTransition("BEGINSTATE", "START", "STATE1");
			o_stateMachine.addTransition("BEGINSTATE", "IDLE", "BEGINSTATE");
			o_stateMachine.addTransition("STATE1", "TRANSFER", "STATE2");
			o_stateMachine.addTransition("STATE2", "TRANSFER", "STATE3");
			o_stateMachine.addTransition("STATE3", "TRANSFER", "FINALSTATE");
			o_stateMachine.addTransition("FINALSTATE", de.forestj.lib.StateMachine.EXIT, de.forestj.lib.StateMachine.EXIT);
			
			/* define state machine methods */
			o_stateMachine.addStateMethod(o_stateMachine.new StateMethodContainer(
				"BEGINSTATE",
				(java.util.List<Object> p_a_genericList) -> {
					p_a_genericList.set(1, ((int) p_a_genericList.get(1)) + 1);
					
					int i_random = de.forestj.lib.Helper.randomIntegerRange(1, 100);
					
					if ( (i_random >= 26) && (i_random <= 50) ) {
						((de.forestj.lib.io.File)p_a_genericList.get(0)).appendLine("BEGINSTATE");
						return "START";
					} else {
						((de.forestj.lib.io.File)p_a_genericList.get(0)).appendLine("BEGINSTATE - IDLE");
						p_a_genericList.set(2, ((int) p_a_genericList.get(2)) + 1);
						return "IDLE";
					}
				}
			));
			
			o_stateMachine.addStateMethod(o_stateMachine.new StateMethodContainer(
				"STATE1",
				(java.util.List<Object> p_a_genericList) -> {
					((de.forestj.lib.io.File)p_a_genericList.get(0)).appendLine("STATE1");
					p_a_genericList.set(1, ((int) p_a_genericList.get(1)) + 1);
					return "TRANSFER";
				}
			));
			
			o_stateMachine.addStateMethod(o_stateMachine.new StateMethodContainer(
				"STATE2",
				(java.util.List<Object> p_a_genericList) -> {
					((de.forestj.lib.io.File)p_a_genericList.get(0)).appendLine("STATE2");
					p_a_genericList.set(1, ((int) p_a_genericList.get(1)) + 1);
					return "TRANSFER";
				}
			));
			
			o_stateMachine.addStateMethod(o_stateMachine.new StateMethodContainer(
				"STATE3",
				(java.util.List<Object> p_a_genericList) -> {
					((de.forestj.lib.io.File)p_a_genericList.get(0)).appendLine("STATE3");
					p_a_genericList.set(1, ((int) p_a_genericList.get(1)) + 1);
					return "TRANSFER";
				}
			));
			
			o_stateMachine.addStateMethod(o_stateMachine.new StateMethodContainer(
				"FINALSTATE",
				(java.util.List<Object> p_a_genericList) -> {
					((de.forestj.lib.io.File)p_a_genericList.get(0)).appendLine("FINALSTATE");
					p_a_genericList.set(1, ((int) p_a_genericList.get(1)) + 1);
					return de.forestj.lib.StateMachine.EXIT;
				}
			));
			
			/* execute state machine until EXIT state */
			String s_returnCode = de.forestj.lib.StateMachine.EXIT;
			String s_currentState = "BEGINSTATE";
			java.util.List<Object> a_genericList = new java.util.ArrayList<Object>();
			a_genericList.add(o_file);
			a_genericList.add(0);
			a_genericList.add(0);
			
			do {
				s_returnCode = o_stateMachine.executeStateMethod(s_currentState, a_genericList);
	    		s_currentState = o_stateMachine.lookupTransitions(s_currentState, s_returnCode);
		    } while (!s_returnCode.contentEquals(de.forestj.lib.StateMachine.EXIT));
			
			int i_amountLines = (int)a_genericList.get(1);
			int i_amountIDLE = (int)a_genericList.get(2);
			de.forestj.lib.io.File o_checkFile = new de.forestj.lib.io.File(s_file);
			
			assertTrue(
					o_checkFile.getFileLines() == i_amountLines,
					"file lines(" + o_checkFile.getFileLines() + ") != " + i_amountLines
			);
			
			int i_checkAmountIDLE = 0;
			
			for (int i = 1; i <= o_checkFile.getFileLines(); i++) {
				String s_line = o_checkFile.readLine(i);
				
				if (s_line.endsWith("IDLE")) {
					i_checkAmountIDLE++;
				}
			}
			
			assertTrue(
					i_checkAmountIDLE == i_amountIDLE,
					"i_checkAmountIDLE(" + i_checkAmountIDLE + ") != " + i_amountIDLE
			);
			
			de.forestj.lib.io.File.deleteFile(s_file);
			assertFalse(
					de.forestj.lib.io.File.exists(s_file),
					"file[" + s_file + "] does exist"
			);
		} catch(Exception o_exc) {
        	fail(o_exc.getMessage());
        }
	}

	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testStateMachineTimer() {
		try {
			String s_currentDirectory = de.forestj.lib.io.File.getCurrentDirectory();
			String s_testDirectory = s_currentDirectory + de.forestj.lib.io.File.DIR;
			String s_file = s_testDirectory + "StateMachineTimer.log";
			
			if (de.forestj.lib.io.File.exists(s_file)) {
				de.forestj.lib.io.File.deleteFile(s_file);
			}
			
			de.forestj.lib.io.File o_file = new de.forestj.lib.io.File(s_file, true);
			assertTrue(
					de.forestj.lib.io.File.exists(s_file),
					"file[" + s_file + "] does not exist"
			);
			assertTrue(
					o_file.getFileLines() == 0,
					"file lines != 0"
			);
			
			StateMachineTimerTask o_task = new StateMachineTimerTask(new de.forestj.lib.DateInterval("PT1S"));
			de.forestj.lib.Timer o_timer = new de.forestj.lib.Timer(o_task);
			
			o_timer.startTimer();
	        int i = 0;
			
			while(true) {
				Thread.sleep(1000);
				
				if (i >= 20) {
					break;
				}
				
				i++;
			}
			
			o_timer.stopTimer();
			
			java.util.List<Object> a_genericList = o_task.getGenericList();
			
			if (a_genericList == null) {
				throw new Exception("No return list from timer task");
			}
			
			int i_amountLines = (int)a_genericList.get(1);
			int i_amountCIRCLE_END = (int)a_genericList.get(2);
			de.forestj.lib.io.File o_checkFile = new de.forestj.lib.io.File(s_file);
			
			assertTrue(
					o_checkFile.getFileLines() == i_amountLines,
					"file lines(" + o_checkFile.getFileLines() + ") != " + i_amountLines
			);
			
			int i_checkAmountCIRCLE_END = 0;
			
			for (i = 1; i <= o_checkFile.getFileLines(); i++) {
				String s_line = o_checkFile.readLine(i);
				
				if (s_line.endsWith("CIRCLE_END")) {
					i_checkAmountCIRCLE_END++;
				}
			}
			
			assertTrue(
					i_checkAmountCIRCLE_END == i_amountCIRCLE_END,
					"i_checkAmountCIRCLE_END(" + i_checkAmountCIRCLE_END + ") != " + i_amountCIRCLE_END
			);
			
			de.forestj.lib.io.File.deleteFile(s_file);
			assertFalse(
					de.forestj.lib.io.File.exists(s_file),
					"file[" + s_file + "] does exist"
			);
		} catch(Exception o_exc) {
	    	fail(o_exc.getMessage());
	    }
	}
	
	private class StateMachineTimerTask extends de.forestj.lib.TimerTask {
		/* Fields */
		
		private de.forestj.lib.StateMachine o_stateMachine;
		private String s_currentState;
		private String s_returnCode;
		private java.util.List<Object> a_genericList;
		
		/* Properties */
		
		public java.util.List<Object> getGenericList() {
			if ( (this.a_genericList != null) && (this.a_genericList.size() == 3) ) {
				return this.a_genericList;
			} else {
				return null;
			}
		}
		
		/* Methods */
		
		public StateMachineTimerTask(de.forestj.lib.DateInterval p_o_interval) {
			super(p_o_interval);
			this.buildStateMachine();
		}
		
		public StateMachineTimerTask(de.forestj.lib.DateInterval p_o_interval, java.time.LocalTime p_o_startTime) {
			super(p_o_interval, p_o_startTime);
			this.buildStateMachine();
		}

		private void buildStateMachine() {
			String s_currentDirectory = de.forestj.lib.io.File.getCurrentDirectory();
			String s_testDirectory = s_currentDirectory + de.forestj.lib.io.File.DIR;
			String s_file = s_testDirectory + "StateMachineTimer.log";
			de.forestj.lib.io.File o_file = null;
			
			try {
				o_file = new de.forestj.lib.io.File(s_file);
			} catch (Exception o_exc) {
				de.forestj.lib.Global.logException(o_exc);
			}
			
			java.util.List<String> a_states = java.util.Arrays.asList("RED", "RED_YELLOW", "GREEN", "YELLOW");
			java.util.List<String> a_returnCodes = java.util.Arrays.asList("CIRCLE_END", "CAR_CONTACT");
			this.o_stateMachine = new de.forestj.lib.StateMachine(a_states, a_returnCodes);
			
			this.o_stateMachine.addTransition("RED", "CAR_CONTACT", "RED_YELLOW");
			this.o_stateMachine.addTransition("RED", "CIRCLE_END", "RED");
			this.o_stateMachine.addTransition("RED", de.forestj.lib.StateMachine.EXIT, de.forestj.lib.StateMachine.EXIT);
			this.o_stateMachine.addTransition("RED_YELLOW", "CIRCLE_END", "GREEN");
			this.o_stateMachine.addTransition("GREEN", "CIRCLE_END", "YELLOW");
			this.o_stateMachine.addTransition("YELLOW", "CIRCLE_END", "RED");
			
			/* define state machine methods */
			this.o_stateMachine.addStateMethod(this.o_stateMachine.new StateMethodContainer(
				"RED",
				(java.util.List<Object> p_a_genericList) -> {
					p_a_genericList.set(1, ((int) p_a_genericList.get(1)) + 1);
					
					int i_random = de.forestj.lib.Helper.randomIntegerRange(1, 100);
					
					if ( (i_random >= 1) && (i_random <= 40) ) {
						((de.forestj.lib.io.File)p_a_genericList.get(0)).appendLine("RED - CIRCLE_END");
						p_a_genericList.set(2, ((int) p_a_genericList.get(2)) + 1);
						return "CIRCLE_END";
					} else if ( (i_random >= 41) && (i_random <= 75) ) {
						((de.forestj.lib.io.File)p_a_genericList.get(0)).appendLine("RED - CAR_CONTACT");
						return "CAR_CONTACT";
					} else {
						((de.forestj.lib.io.File)p_a_genericList.get(0)).appendLine("RED - EXIT");
						return "EXIT";
					}
				}
			));
			
			this.o_stateMachine.addStateMethod(this.o_stateMachine.new StateMethodContainer(
				"RED_YELLOW",
				(java.util.List<Object> p_a_genericList) -> {
					((de.forestj.lib.io.File)p_a_genericList.get(0)).appendLine("RED_YELLOW");
					p_a_genericList.set(1, ((int) p_a_genericList.get(1)) + 1);
					return "CIRCLE_END";
				}
			));
			
			this.o_stateMachine.addStateMethod(this.o_stateMachine.new StateMethodContainer(
				"GREEN",
				(java.util.List<Object> p_a_genericList) -> {
					((de.forestj.lib.io.File)p_a_genericList.get(0)).appendLine("GREEN");
					p_a_genericList.set(1, ((int) p_a_genericList.get(1)) + 1);
					return "CIRCLE_END";
				}
			));
			
			this.o_stateMachine.addStateMethod(this.o_stateMachine.new StateMethodContainer(
				"YELLOW",
				(java.util.List<Object> p_a_genericList) -> {
					((de.forestj.lib.io.File)p_a_genericList.get(0)).appendLine("YELLOW");
					p_a_genericList.set(1, ((int) p_a_genericList.get(1)) + 1);
					return "CIRCLE_END";
				}
			));
				
			/* define init values */
			this.s_returnCode = de.forestj.lib.StateMachine.EXIT;
			this.s_currentState = "RED";
			this.a_genericList = new java.util.ArrayList<Object>();
			this.a_genericList.add(o_file);
			this.a_genericList.add(0);
			this.a_genericList.add(0);
		}
		
		@Override public void runTimerTask() throws Exception {
			this.s_returnCode = this.o_stateMachine.executeStateMethod(this.s_currentState, this.a_genericList);
	    	
			if (this.s_returnCode.contentEquals(de.forestj.lib.StateMachine.EXIT)) {
	    		this.cancel();
	    	}
	    	
	    	this.s_currentState = this.o_stateMachine.lookupTransitions(this.s_currentState, this.s_returnCode);
		}
	}
}
