package net.forestany.forestj.sandbox.util;

public class ConsoleTest {
	public static void testConsole() throws Exception {
    	// Boolean
    	System.out.println("\n\n\n" + "consoleInputCharacter - press enter - enter invalid value - enter valid value");
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputBoolean() );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputBoolean("consoleInputBoolean: ") );
    	
    	// Character
    	System.out.println("\n\n\n" + "consoleInputCharacter - press enter - enter invalid value - enter valid value");
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputCharacter() );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputCharacter("consoleInputCharacter: ") );
    	
    	// Date
    	System.out.println("\n\n\n" + "consoleInputDate - press enter - enter invalid value - enter valid value");
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDate() );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDate("consoleInputDate: ") );
    	try {
    		net.forestany.forestj.lib.Console.consoleInputDate("consoleInputDate: ", null, null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputDate - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		net.forestany.forestj.lib.Console.consoleInputDate("consoleInputDate: ", null, "No Date.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputDate - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDate("consoleInputDate: ", null, "No Date.") );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDate("consoleInputDate: ", null, "No Date.", "Please enter a value.") );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDate("consoleInputDate [a-f0-9]*: ", "[a-f0-9]*", "No Date.", "Please enter a value.") );
    	
    	// Time
    	System.out.println("\n\n\n" + "consoleInputTime - press enter - enter invalid value - enter valid value");
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputTime() );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputTime("consoleInputTime: ") );
    	try {
    		net.forestany.forestj.lib.Console.consoleInputTime("consoleInputTime: ", null, null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputTime - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		net.forestany.forestj.lib.Console.consoleInputTime("consoleInputTime: ", null, "No Time.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputTime - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputTime("consoleInputTime: ", null, "No Time.") );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputTime("consoleInputTime: ", null, "No Time.", "Please enter a value.") );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputTime("consoleInputTime [a-f0-9]*: ", "[a-f0-9]*", "No Time.", "Please enter a value.") );
    	
    	// DateTime
    	System.out.println("\n\n\n" + "consoleInputDateTime - press enter - enter invalid value - enter valid value");
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDateTime() );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDateTime("consoleInputDateTime: ") );
    	try {
    		net.forestany.forestj.lib.Console.consoleInputDateTime("consoleInputDateTime: ", null, null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputDateTime - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		net.forestany.forestj.lib.Console.consoleInputDateTime("consoleInputDateTime: ", null, "No DateTime.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputDateTime - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDateTime("consoleInputDateTime: ", null, "No DateTime.") );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDateTime("consoleInputDateTime: ", null, "No DateTime.", "Please enter a value.") );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDateTime("consoleInputDateTime [a-f0-9]*: ", "[a-f0-9]*", "No DateTime.", "Please enter a value.") );
    	
    	// DateInterval
    	System.out.println("\n\n\n" + "consoleInputDateInterval - press enter - enter invalid value - enter valid value");
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDateInterval() );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDateInterval("consoleInputDateInterval: ") );
    	try {
    		net.forestany.forestj.lib.Console.consoleInputDateInterval("consoleInputDateInterval: ", null, null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputDateInterval - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		net.forestany.forestj.lib.Console.consoleInputDateInterval("consoleInputDateInterval: ", null, "No DateTime.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputDateInterval - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDateInterval("consoleInputDateInterval: ", null, "No DateInterval.") );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDateInterval("consoleInputDateInterval: ", null, "No DateInterval.", "Please enter a value.") );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDateInterval("consoleInputDateInterval [a-f0-9]*: ", "[a-f0-9]*", "No DateInterval.", "Please enter a value.") );
    	
    	// Float
    	System.out.println("\n\n\n" + "consoleInputFloat - press enter - enter invalid value - enter valid value");
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputFloat() );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputFloat("consoleInputFloat: ") );
    	try {
    		net.forestany.forestj.lib.Console.consoleInputFloat("consoleInputFloat: ", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputFloat - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		net.forestany.forestj.lib.Console.consoleInputFloat("consoleInputFloat: ", "No Float.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputFloat - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputFloat("consoleInputFloat: ", "No Float.") );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputFloat("consoleInputFloat: ", "No Float.", "Please enter a value.") );
    	
    	// Double
    	System.out.println("\n\n\n" + "consoleInputDouble - press enter - enter invalid value - enter valid value");
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDouble() );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDouble("consoleInputDouble: ") );
    	try {
    		net.forestany.forestj.lib.Console.consoleInputDouble("consoleInputDouble: ", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputDouble - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		net.forestany.forestj.lib.Console.consoleInputDouble("consoleInputDouble: ", "No Double.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputDouble - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDouble("consoleInputDouble: ", "No Double.") );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputDouble("consoleInputDouble: ", "No Double.", "Please enter a value.") );
    	
    	// Short    	
    	System.out.println("\n\n\n" + "consoleInputShort - press enter - enter invalid value - enter valid value");
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputShort() );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputShort("consoleInputShort: ") );
    	try {
    		net.forestany.forestj.lib.Console.consoleInputShort("consoleInputShort: ", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputShort - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		net.forestany.forestj.lib.Console.consoleInputShort("consoleInputShort: ", "No Short.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputShort - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputShort("consoleInputShort: ", "No Short.") );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputShort("consoleInputShort: ", "No Short.", "Please enter a value.") );
    	
    	// Integer
    	System.out.println("\n\n\n" + "consoleInputInteger - press enter - enter invalid value - enter valid value");
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputInteger() );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputInteger("consoleInputInteger: ") );
    	try {
    		net.forestany.forestj.lib.Console.consoleInputInteger("consoleInputInteger: ", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputInteger - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		net.forestany.forestj.lib.Console.consoleInputInteger("consoleInputInteger: ", "No Integer.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputInteger - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputInteger("consoleInputInteger: ", "No Integer.") );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputInteger("consoleInputInteger: ", "No Integer.", "Please enter a value.") );
    	
    	// Long
    	System.out.println("\n\n\n" + "consoleInputLong - press enter - enter invalid value - enter valid value");
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputLong() );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputLong("consoleInputLong: ") );
    	try {
    		net.forestany.forestj.lib.Console.consoleInputLong("consoleInputLong: ", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputLong - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		net.forestany.forestj.lib.Console.consoleInputLong("consoleInputLong: ", "No Long.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputLong - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputLong("consoleInputLong: ", "No Long.") );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputLong("consoleInputLong: ", "No Long.", "Please enter a value.") );
    	
    	// Numeric String
    	System.out.println("\n\n\n" + "consoleInputNumericString - press enter - enter invalid value - enter valid value");
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputNumericString() );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputNumericString("consoleInputNumericString: ") );
    	try {
    		net.forestany.forestj.lib.Console.consoleInputNumericString("consoleInputNumericString: ", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputNumericString - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		net.forestany.forestj.lib.Console.consoleInputNumericString("consoleInputNumericString: ", "No NumericString.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputNumericString - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputNumericString("consoleInputNumericString: ", "No NumericString.") );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputNumericString("consoleInputNumericString: ", "No NumericString.", "Please enter a value.") );
    	
    	// String
    	System.out.println("\n\n\n" + "consoleInputString - press enter - enter invalid value - enter valid value");
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputString() );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputString("consoleInputString: ") );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputString(true) );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputString("consoleInputString: ", false) );
    	try {
    		net.forestany.forestj.lib.Console.consoleInputString("consoleInputString: ", false, null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputString - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputString("consoleInputString: ", false, "Please enter a value.") );
    	
    	// String Password
    	System.out.println("\n\n\n" + "consoleInputPassword - press enter - enter invalid value - enter valid value");
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputPassword() );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputPassword("consoleInputPassword: ") );
    	try {
    		net.forestany.forestj.lib.Console.consoleInputPassword("consoleInputPassword: ", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputPassword - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputPassword("consoleInputPassword: ", "Please enter a value.") );
    	
    	// Regex
    	System.out.println("\n\n\n" + "consoleInputRegex [a-f0-9]* - press enter - enter invalid value - enter valid value");
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputRegex("consoleInputRegex: ", "[a-f0-9]*") );
    	try {
    		net.forestany.forestj.lib.Console.consoleInputRegex("consoleInputRegex: ", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputRegex - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		net.forestany.forestj.lib.Console.consoleInputRegex("consoleInputRegex: ", "[a-f0-9]*", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputRegex - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	try {
    		net.forestany.forestj.lib.Console.consoleInputRegex("consoleInputRegex: ", "[a-f0-9]*", "No Regex.", null);
    	} catch (IllegalArgumentException o_exc) {
    		System.out.println("consoleInputRegex - Expected IllegalArgumentException: " + o_exc.getMessage());
    	}
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputRegex("consoleInputRegex: ", "[a-f0-9]*", "Does not match criteria.") );
    	System.out.println( net.forestany.forestj.lib.Console.consoleInputRegex("consoleInputRegex: ", "[a-f0-9]*", "Does not match criteria.", "Please enter a value.") );
    }
}
