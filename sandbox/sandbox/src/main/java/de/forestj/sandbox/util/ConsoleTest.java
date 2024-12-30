package de.forestj.sandbox.util;

public class ConsoleTest {
	public static void testConsole() throws Exception {
    	// Boolean
    	System.out.println("\n\n\n" + "consoleInputCharacter - press enter - enter invalid value - enter valid value");
    	System.out.println( de.forestj.lib.Console.consoleInputBoolean() );
    	System.out.println( de.forestj.lib.Console.consoleInputBoolean("consoleInputBoolean: ") );
    	
    	// Character
    	System.out.println("\n\n\n" + "consoleInputCharacter - press enter - enter invalid value - enter valid value");
    	System.out.println( de.forestj.lib.Console.consoleInputCharacter() );
    	System.out.println( de.forestj.lib.Console.consoleInputCharacter("consoleInputCharacter: ") );
    	
    	// Date
    	System.out.println("\n\n\n" + "consoleInputDate - press enter - enter invalid value - enter valid value");
    	System.out.println( de.forestj.lib.Console.consoleInputDate() );
    	System.out.println( de.forestj.lib.Console.consoleInputDate("consoleInputDate: ") );
    	try {
    		de.forestj.lib.Console.consoleInputDate("consoleInputDate: ", null, null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputDate - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		de.forestj.lib.Console.consoleInputDate("consoleInputDate: ", null, "No Date.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputDate - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( de.forestj.lib.Console.consoleInputDate("consoleInputDate: ", null, "No Date.") );
    	System.out.println( de.forestj.lib.Console.consoleInputDate("consoleInputDate: ", null, "No Date.", "Please enter a value.") );
    	System.out.println( de.forestj.lib.Console.consoleInputDate("consoleInputDate [a-f0-9]*: ", "[a-f0-9]*", "No Date.", "Please enter a value.") );
    	
    	// Time
    	System.out.println("\n\n\n" + "consoleInputTime - press enter - enter invalid value - enter valid value");
    	System.out.println( de.forestj.lib.Console.consoleInputTime() );
    	System.out.println( de.forestj.lib.Console.consoleInputTime("consoleInputTime: ") );
    	try {
    		de.forestj.lib.Console.consoleInputTime("consoleInputTime: ", null, null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputTime - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		de.forestj.lib.Console.consoleInputTime("consoleInputTime: ", null, "No Time.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputTime - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( de.forestj.lib.Console.consoleInputTime("consoleInputTime: ", null, "No Time.") );
    	System.out.println( de.forestj.lib.Console.consoleInputTime("consoleInputTime: ", null, "No Time.", "Please enter a value.") );
    	System.out.println( de.forestj.lib.Console.consoleInputTime("consoleInputTime [a-f0-9]*: ", "[a-f0-9]*", "No Time.", "Please enter a value.") );
    	
    	// DateTime
    	System.out.println("\n\n\n" + "consoleInputDateTime - press enter - enter invalid value - enter valid value");
    	System.out.println( de.forestj.lib.Console.consoleInputDateTime() );
    	System.out.println( de.forestj.lib.Console.consoleInputDateTime("consoleInputDateTime: ") );
    	try {
    		de.forestj.lib.Console.consoleInputDateTime("consoleInputDateTime: ", null, null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputDateTime - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		de.forestj.lib.Console.consoleInputDateTime("consoleInputDateTime: ", null, "No DateTime.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputDateTime - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( de.forestj.lib.Console.consoleInputDateTime("consoleInputDateTime: ", null, "No DateTime.") );
    	System.out.println( de.forestj.lib.Console.consoleInputDateTime("consoleInputDateTime: ", null, "No DateTime.", "Please enter a value.") );
    	System.out.println( de.forestj.lib.Console.consoleInputDateTime("consoleInputDateTime [a-f0-9]*: ", "[a-f0-9]*", "No DateTime.", "Please enter a value.") );
    	
    	// DateInterval
    	System.out.println("\n\n\n" + "consoleInputDateInterval - press enter - enter invalid value - enter valid value");
    	System.out.println( de.forestj.lib.Console.consoleInputDateInterval() );
    	System.out.println( de.forestj.lib.Console.consoleInputDateInterval("consoleInputDateInterval: ") );
    	try {
    		de.forestj.lib.Console.consoleInputDateInterval("consoleInputDateInterval: ", null, null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputDateInterval - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		de.forestj.lib.Console.consoleInputDateInterval("consoleInputDateInterval: ", null, "No DateTime.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputDateInterval - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( de.forestj.lib.Console.consoleInputDateInterval("consoleInputDateInterval: ", null, "No DateInterval.") );
    	System.out.println( de.forestj.lib.Console.consoleInputDateInterval("consoleInputDateInterval: ", null, "No DateInterval.", "Please enter a value.") );
    	System.out.println( de.forestj.lib.Console.consoleInputDateInterval("consoleInputDateInterval [a-f0-9]*: ", "[a-f0-9]*", "No DateInterval.", "Please enter a value.") );
    	
    	// Float
    	System.out.println("\n\n\n" + "consoleInputFloat - press enter - enter invalid value - enter valid value");
    	System.out.println( de.forestj.lib.Console.consoleInputFloat() );
    	System.out.println( de.forestj.lib.Console.consoleInputFloat("consoleInputFloat: ") );
    	try {
    		de.forestj.lib.Console.consoleInputFloat("consoleInputFloat: ", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputFloat - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		de.forestj.lib.Console.consoleInputFloat("consoleInputFloat: ", "No Float.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputFloat - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( de.forestj.lib.Console.consoleInputFloat("consoleInputFloat: ", "No Float.") );
    	System.out.println( de.forestj.lib.Console.consoleInputFloat("consoleInputFloat: ", "No Float.", "Please enter a value.") );
    	
    	// Double
    	System.out.println("\n\n\n" + "consoleInputDouble - press enter - enter invalid value - enter valid value");
    	System.out.println( de.forestj.lib.Console.consoleInputDouble() );
    	System.out.println( de.forestj.lib.Console.consoleInputDouble("consoleInputDouble: ") );
    	try {
    		de.forestj.lib.Console.consoleInputDouble("consoleInputDouble: ", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputDouble - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		de.forestj.lib.Console.consoleInputDouble("consoleInputDouble: ", "No Double.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputDouble - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( de.forestj.lib.Console.consoleInputDouble("consoleInputDouble: ", "No Double.") );
    	System.out.println( de.forestj.lib.Console.consoleInputDouble("consoleInputDouble: ", "No Double.", "Please enter a value.") );
    	
    	// Short    	
    	System.out.println("\n\n\n" + "consoleInputShort - press enter - enter invalid value - enter valid value");
    	System.out.println( de.forestj.lib.Console.consoleInputShort() );
    	System.out.println( de.forestj.lib.Console.consoleInputShort("consoleInputShort: ") );
    	try {
    		de.forestj.lib.Console.consoleInputShort("consoleInputShort: ", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputShort - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		de.forestj.lib.Console.consoleInputShort("consoleInputShort: ", "No Short.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputShort - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( de.forestj.lib.Console.consoleInputShort("consoleInputShort: ", "No Short.") );
    	System.out.println( de.forestj.lib.Console.consoleInputShort("consoleInputShort: ", "No Short.", "Please enter a value.") );
    	
    	// Integer
    	System.out.println("\n\n\n" + "consoleInputInteger - press enter - enter invalid value - enter valid value");
    	System.out.println( de.forestj.lib.Console.consoleInputInteger() );
    	System.out.println( de.forestj.lib.Console.consoleInputInteger("consoleInputInteger: ") );
    	try {
    		de.forestj.lib.Console.consoleInputInteger("consoleInputInteger: ", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputInteger - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		de.forestj.lib.Console.consoleInputInteger("consoleInputInteger: ", "No Integer.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputInteger - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( de.forestj.lib.Console.consoleInputInteger("consoleInputInteger: ", "No Integer.") );
    	System.out.println( de.forestj.lib.Console.consoleInputInteger("consoleInputInteger: ", "No Integer.", "Please enter a value.") );
    	
    	// Long
    	System.out.println("\n\n\n" + "consoleInputLong - press enter - enter invalid value - enter valid value");
    	System.out.println( de.forestj.lib.Console.consoleInputLong() );
    	System.out.println( de.forestj.lib.Console.consoleInputLong("consoleInputLong: ") );
    	try {
    		de.forestj.lib.Console.consoleInputLong("consoleInputLong: ", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputLong - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		de.forestj.lib.Console.consoleInputLong("consoleInputLong: ", "No Long.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputLong - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( de.forestj.lib.Console.consoleInputLong("consoleInputLong: ", "No Long.") );
    	System.out.println( de.forestj.lib.Console.consoleInputLong("consoleInputLong: ", "No Long.", "Please enter a value.") );
    	
    	// Numeric String
    	System.out.println("\n\n\n" + "consoleInputNumericString - press enter - enter invalid value - enter valid value");
    	System.out.println( de.forestj.lib.Console.consoleInputNumericString() );
    	System.out.println( de.forestj.lib.Console.consoleInputNumericString("consoleInputNumericString: ") );
    	try {
    		de.forestj.lib.Console.consoleInputNumericString("consoleInputNumericString: ", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputNumericString - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		de.forestj.lib.Console.consoleInputNumericString("consoleInputNumericString: ", "No NumericString.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputNumericString - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( de.forestj.lib.Console.consoleInputNumericString("consoleInputNumericString: ", "No NumericString.") );
    	System.out.println( de.forestj.lib.Console.consoleInputNumericString("consoleInputNumericString: ", "No NumericString.", "Please enter a value.") );
    	
    	// String
    	System.out.println("\n\n\n" + "consoleInputString - press enter - enter invalid value - enter valid value");
    	System.out.println( de.forestj.lib.Console.consoleInputString() );
    	System.out.println( de.forestj.lib.Console.consoleInputString("consoleInputString: ") );
    	System.out.println( de.forestj.lib.Console.consoleInputString(true) );
    	System.out.println( de.forestj.lib.Console.consoleInputString("consoleInputString: ", false) );
    	try {
    		de.forestj.lib.Console.consoleInputString("consoleInputString: ", false, null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputString - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( de.forestj.lib.Console.consoleInputString("consoleInputString: ", false, "Please enter a value.") );
    	
    	// String Password
    	System.out.println("\n\n\n" + "consoleInputPassword - press enter - enter invalid value - enter valid value");
    	System.out.println( de.forestj.lib.Console.consoleInputPassword() );
    	System.out.println( de.forestj.lib.Console.consoleInputPassword("consoleInputPassword: ") );
    	try {
    		de.forestj.lib.Console.consoleInputPassword("consoleInputPassword: ", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputPassword - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( de.forestj.lib.Console.consoleInputPassword("consoleInputPassword: ", "Please enter a value.") );
    	
    	// Regex
    	System.out.println("\n\n\n" + "consoleInputRegex [a-f0-9]* - press enter - enter invalid value - enter valid value");
    	System.out.println( de.forestj.lib.Console.consoleInputRegex("consoleInputRegex: ", "[a-f0-9]*") );
    	try {
    		de.forestj.lib.Console.consoleInputRegex("consoleInputRegex: ", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputRegex - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		de.forestj.lib.Console.consoleInputRegex("consoleInputRegex: ", "[a-f0-9]*", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputRegex - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		de.forestj.lib.Console.consoleInputRegex("consoleInputRegex: ", "[a-f0-9]*", "No Regex.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputRegex - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( de.forestj.lib.Console.consoleInputRegex("consoleInputRegex: ", "[a-f0-9]*", "Does not match criteria.") );
    	System.out.println( de.forestj.lib.Console.consoleInputRegex("consoleInputRegex: ", "[a-f0-9]*", "Does not match criteria.", "Please enter a value.") );
    }

	public static void testConsoleProgressBar() throws Exception {
    	WorkSimulationWithDelegate o_workSim = null;
    	
    	de.forestj.lib.ConsoleProgressBar o_progressBar_1 = new de.forestj.lib.ConsoleProgressBar();
    			
		o_workSim = new WorkSimulationWithDelegate(
			new WorkSimulationWithDelegate.IDelegate() {
				@Override public void PostProgress(int p_i_progress) {
					o_progressBar_1.report((double)p_i_progress / 100.0d);
				}
			}
		);
		
		o_progressBar_1.init("Start progress () . . .", "Done ().");
		
		o_workSim.start();
		o_workSim.join();
		
		o_progressBar_1.close();
		
		/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
		System.out.println("\n" + "++++++++++++++++++++++++++++++++++++++++++" + "\n");
		/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
		
		de.forestj.lib.ConsoleProgressBar o_progressBar_2 = new de.forestj.lib.ConsoleProgressBar(18);
		
		o_workSim = new WorkSimulationWithDelegate(
			new WorkSimulationWithDelegate.IDelegate() { 
				@Override public void PostProgress(int p_i_progress) {
					o_progressBar_2.report((double)p_i_progress / 100.0d);
				}
			}
		);
		
		o_progressBar_2.init("Start progress (18) . . .", "Done (18).");
		
		o_workSim.start();
		o_workSim.join();
		
		o_progressBar_2.close();
    	
		/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
		System.out.println("\n" + "++++++++++++++++++++++++++++++++++++++++++" + "\n");
		/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
		
		de.forestj.lib.ConsoleProgressBar o_progressBar_3 = new de.forestj.lib.ConsoleProgressBar((long)75, 18);
		
		o_workSim = new WorkSimulationWithDelegate(
			new WorkSimulationWithDelegate.IDelegate() { 
				@Override public void PostProgress(int p_i_progress) {
					o_progressBar_3.report((double)p_i_progress / 100.0d);
				}
			}
		);
		
		o_progressBar_3.init("Start progress (75, 18) . . .", "Done (75, 18).");
		
		o_workSim.start();
		o_workSim.join();
		
		o_progressBar_3.close();
		
		/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
		System.out.println("\n" + "++++++++++++++++++++++++++++++++++++++++++" + "\n");
		/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
		
		de.forestj.lib.ConsoleProgressBar o_progressBar_4 = new de.forestj.lib.ConsoleProgressBar((long)75, 18, 10);
		
		o_workSim = new WorkSimulationWithDelegate(
			new WorkSimulationWithDelegate.IDelegate() { 
				@Override public void PostProgress(int p_i_progress) {
					o_progressBar_4.report((double)p_i_progress / 100.0d);
				}
			}
		);
		
		o_progressBar_4.init("Start progress (75, 18, 10) . . .", "Done (75, 18, 10).", "Marquee test with large 38 text length");
		
		o_workSim.start();
		o_workSim.join();
		
		o_progressBar_4.close();
		
		/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
		System.out.println("\n" + "++++++++++++++++++++++++++++++++++++++++++" + "\n");
		/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
		
		de.forestj.lib.ConsoleProgressBar o_progressBar_5 = new de.forestj.lib.ConsoleProgressBar(10, 8);
		
		o_workSim = new WorkSimulationWithDelegate(
			new WorkSimulationWithDelegate.IDelegate() { 
				@Override public void PostProgress(int p_i_progress) {
					o_progressBar_5.report((double)p_i_progress / 100.0d);
				}
			}
		);
		
		o_progressBar_5.init("Start progress (10, 8) . . .", "Done (10, 8).", "Marquee test with large 38 text length");
		
		o_workSim.start();
		o_workSim.join();
		
		o_progressBar_5.close();
		
		/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
		System.out.println("\n" + "++++++++++++++++++++++++++++++++++++++++++" + "\n");
		/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
		
		de.forestj.lib.ConsoleProgressBar o_progressBar_6 = new de.forestj.lib.ConsoleProgressBar((long)75, 18, 10, 12);
		
		o_workSim = new WorkSimulationWithDelegate(
			new WorkSimulationWithDelegate.IDelegate() { 
				@Override public void PostProgress(int p_i_progress) {
					o_progressBar_6.report((double)p_i_progress / 100.0d);
				}
			}
		);
		
		o_progressBar_6.init("Start progress (75, 18, 10, 12) . . .", "Done (75, 18, 10, 12).", "Marquee test with large 38 text length");
		
		o_workSim.start();
		o_workSim.join();
		
		o_progressBar_6.close();
		
		/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
		System.out.println("\n" + "++++++++++++++++++++++++++++++++++++++++++" + "\n");
		/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ */
		
		o_workSim = new WorkSimulationWithDelegate(
			new WorkSimulationWithDelegate.IDelegate() { 
				@Override public void PostProgress(int p_i_progress) {
					o_progressBar_6.report((double)p_i_progress / 100.0d);
				}
			}
		);
		
		o_progressBar_6.init("Start progress (75, 18, 10, 12) . . .", "Done (75, 18, 10, 12).", "Short Marquee");
		
		o_workSim.start();
		o_workSim.join();
		
		o_progressBar_6.close();
    }
}
