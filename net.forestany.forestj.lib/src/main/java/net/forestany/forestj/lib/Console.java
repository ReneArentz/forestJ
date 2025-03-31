package net.forestany.forestj.lib;

/**
 * 
 * Collection of static methods to support and handle user input with console.
 *
 */
public class Console {
	
	/* Fields */
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * empty constructor
	 */
	public Console() {
		
	}
	
	/**
	 * get string line from console input
	 * 
	 * @param	p_s_caption		caption text before user can enter a string line in console
	 * @return					string line
	 * @see		String
	 */
	private static String doInput(String p_s_caption) {
		return doInput(p_s_caption, false);
	}
	
	/**
	 * get string line from console input
	 * 
	 * @param	p_s_caption					caption text before user can enter a string line in console
	 * @param	p_b_acceptEmptyString		enter an empty string will not return an error message
	 * @return								string line
	 * @see		String
	 */
	private static String doInput(String p_s_caption, boolean p_b_acceptEmptyString) {
		return doInput(p_s_caption, p_b_acceptEmptyString, "Please enter a value.");
	}

	@SuppressWarnings("resource")
	/**
	 * get string line from console input
	 * 
	 * @param	p_s_caption					caption text before user can enter a string line in console
	 * @param	p_b_acceptEmptyString		enter an empty string will not return an error message
	 * @param	p_s_inputErrorMessage		overwrite standard english error message
	 * @return								string line
	 * @see		String
	 */
	private static String doInput(String p_s_caption, boolean p_b_acceptEmptyString, String p_s_inputErrorMessage) {
		if (Helper.isStringEmpty(p_s_inputErrorMessage)) {
			throw new IllegalArgumentException("Please specify an input error message.");
		}
		
		String s_foo = null;
		boolean b_loop = false;
		
		do {
			/* print caption text before user can enter a string line in console */
			System.out.print(p_s_caption);
			
			/* read string line from console input */
			s_foo = new java.util.Scanner(System.in).nextLine();
			
			/* accept empty string flag */
			if (!p_b_acceptEmptyString) {
				/* if string line input is empty, print error message */
				if (Helper.isStringEmpty(s_foo)) {
					b_loop = true;
					System.out.print(p_s_inputErrorMessage + "\n");
				} else {
					/* exit input loop */
					b_loop = false;
				}
			}
		} while (b_loop);
		
		return s_foo;
	}
	
	
	/**
	 * get password line from console input
	 * 
	 * @param	p_s_caption		caption text before user can enter a password string in console
	 * @return					password string
	 * @see		String
	 */
	private static String doInputPassword(String p_s_caption) {
		return doInputPassword(p_s_caption, "Please enter a password.");
	}
	
	@SuppressWarnings("resource")
	/**
	 * get password line from console input
	 * 
	 * @param	p_s_caption				caption text before user can enter a password string in console
	 * @param	p_s_inputErrorMessage	overwrite standard english error message
	 * @return							password string
	 * @see		String
	 */
	private static String doInputPassword(String p_s_caption, String p_s_inputErrorMessage) {
		if (Helper.isStringEmpty(p_s_inputErrorMessage)) {
			throw new IllegalArgumentException("Please specify an input error message.");
		}
		
		String s_foo = null;
		boolean b_loop = false;
		
		do {
			/* print caption text before user can enter a password string in console */
			System.out.print(p_s_caption);
			
			/* create console object */
			java.io.Console o_console = System.console();
			
			if (o_console != null) { /* use readPassword method if console object could be created */
				try {
					s_foo = new String(o_console.readPassword());
				} catch (Exception o_exc) {
					/* if exception catched, keep string line empty */
					s_foo = "";
					/* exit input loop */
					b_loop = false;
				}
			} else {
				/* read string line from console input */
				s_foo = new java.util.Scanner(System.in).nextLine();
			}
			
			/* if string line input is empty, print error message */
			if (Helper.isStringEmpty(s_foo)) {
				System.out.print(p_s_inputErrorMessage + "\n");
				b_loop = true;
			} else {
				b_loop = false;
			}
		} while (b_loop);
		
		return s_foo;
	}
	
	
	/**
	 * get console input string value
	 * 
	 * @return								string value
	 * @see		String
	 */
	public static String consoleInputString() {
		return consoleInputString("");
	}
	
	/**
	 * get console input string value
	 * 
	 * @param	p_b_acceptEmptyString		enter an empty string will not return an error message
	 * @return								string value
	 * @see		String
	 */
	public static String consoleInputString(boolean p_b_acceptEmptyString) {
		return consoleInputString("", p_b_acceptEmptyString);
	}
	
	/**
	 * get console input string value
	 * 
	 * @param	p_s_caption					caption text before user can enter a input string value
	 * @return								string value
	 * @see		String
	 */
	public static String consoleInputString(String p_s_caption) {
		return doInput(p_s_caption);
	}
	
	/**
	 * get console input string value
	 * 
	 * @param	p_s_caption					caption text before user can enter a input string value
	 * @param	p_b_acceptEmptyString		enter an empty string will not return an error message
	 * @return								string value
	 * @see		String
	 */
	public static String consoleInputString(String p_s_caption, boolean p_b_acceptEmptyString) {
		return doInput(p_s_caption, p_b_acceptEmptyString);
	}
	
	/**
	 * get console input string value
	 * 
	 * @param	p_s_caption					caption text before user can enter a input string value
	 * @param	p_b_acceptEmptyString		enter an empty string will not return an error message
	 * @param	p_s_inputErrorMessage		overwrite standard english input error message
	 * @return								string value
	 * @see		String
	 */
	public static String consoleInputString(String p_s_caption, boolean p_b_acceptEmptyString, String p_s_inputErrorMessage) {
		return doInput(p_s_caption, p_b_acceptEmptyString, p_s_inputErrorMessage);
	}
	
	
	/**
	 * get console input password string value
	 * 
	 * @return								password string value
	 * @see		String
	 */
	public static String consoleInputPassword() {
		return consoleInputPassword("");
	}
	
	/**
	 * get console input password string value
	 * 
	 * @param	p_s_caption					caption text before user can enter a input password string value
	 * @return								password string value
	 * @see		String
	 */
	public static String consoleInputPassword(String p_s_caption) {
		return doInputPassword(p_s_caption);
	}
	
	/**
	 * get console input password string value
	 * 
	 * @param	p_s_caption					caption text before user can enter a input password string value
	 * @param	p_s_inputErrorMessage		overwrite standard english input error message
	 * @return								password string value
	 * @see		String
	 */
	public static String consoleInputPassword(String p_s_caption, String p_s_inputErrorMessage) {
		return doInputPassword(p_s_caption, p_s_inputErrorMessage);
	}
	
	
	/**
	 * get console input character value
	 * 
	 * @return								character value
	 * @see		Character
	 */
	public static char consoleInputCharacter() {
		return consoleInputCharacter("");
	}
	
	/**
	 * get console input character value
	 * 
	 * @param	p_s_caption					caption text before user can enter a input character value
	 * @return								character value
	 * @see		Character
	 */
	public static char consoleInputCharacter(String p_s_caption) {
		/* use doInput to get input string line, then just take the first character as input value */
		String s_foo = doInput(p_s_caption);
		return s_foo.charAt(0);
	}
	
	
	/**
	 * get console input numeric string value
	 * 
	 * @return								numeric string value
	 * @see		String
	 */
	public static String consoleInputNumericString() {
		return consoleInputNumericString("");
	}
	
	/**
	 * get console input numeric string value
	 * 
	 * @param	p_s_caption					caption text before user can enter an input numeric string value
	 * @return								numeric string value
	 * @see		String
	 */
	public static String consoleInputNumericString(String p_s_caption) {
		return consoleInputNumericString(p_s_caption, "The entered value is not numeric[" + Integer.MIN_VALUE + " .. " + Integer.MAX_VALUE + "].");
	}
	
	/**
	 * get console input numeric string value
	 * 
	 * @param	p_s_caption					caption text before user can enter an input numeric string value
	 * @param	p_s_errorMessage			overwrite standard english error message
	 * @return								numeric string value
	 * @see		String
	 */
	public static String consoleInputNumericString(String p_s_caption, String p_s_errorMessage) {
		return consoleInputNumericString(p_s_caption, p_s_errorMessage, "Please enter a value.");
	}
	
	/**
	 * get console input numeric string value
	 * 
	 * @param	p_s_caption					caption text before user can enter an input numeric string value
	 * @param	p_s_errorMessage			overwrite standard english error message
	 * @param	p_s_inputErrorMessage		overwrite standard english input error message
	 * @return								numeric string value
	 * @see		String
	 */
	public static String consoleInputNumericString(String p_s_caption, String p_s_errorMessage, String p_s_inputErrorMessage) {
		if (Helper.isStringEmpty(p_s_errorMessage)) {
			throw new IllegalArgumentException("Please specify an error message.");
		}
		
		if (Helper.isStringEmpty(p_s_inputErrorMessage)) {
			throw new IllegalArgumentException("Please specify an input error message.");
		}
		
		String s_foo = null;
		
		/* check if input string value is a valid integer value */
		while (!Helper.isInteger(s_foo = doInput(p_s_caption, false, p_s_inputErrorMessage))) {
			System.out.print(p_s_errorMessage + "\n");
		}
		
		return s_foo;
	}
	
	
	/**
	 * get console input value
	 * 
	 * @return								integer value
	 * @see		Integer
	 */
	public static int consoleInputInteger() {
		return consoleInputInteger("");
	}
	
	/**
	 * get console input value
	 * 
	 * @param	p_s_caption					caption text before user can enter an input value
	 * @return								integer value
	 * @see		Integer
	 */
	public static int consoleInputInteger(String p_s_caption) {
		return Integer.valueOf(consoleInputNumericString(p_s_caption));
	}
	
	/**
	 * get console input value
	 * 
	 * @param	p_s_caption					caption text before user can enter an input value
	 * @param	p_s_errorMessage			overwrite standard english error message
	 * @return								integer value
	 * @see		Integer
	 */
	public static int consoleInputInteger(String p_s_caption, String p_s_errorMessage) {
		return Integer.valueOf(consoleInputNumericString(p_s_caption, p_s_errorMessage));
	}
	
	/**
	 * get console input value
	 * 
	 * @param	p_s_caption					caption text before user can enter an input value
	 * @param	p_s_errorMessage			overwrite standard english error message
	 * @param	p_s_inputErrorMessage		overwrite standard english input error message
	 * @return								integer value
	 * @see		Integer
	 */
	public static int consoleInputInteger(String p_s_caption, String p_s_errorMessage, String p_s_inputErrorMessage) {
		return Integer.valueOf(consoleInputNumericString(p_s_caption, p_s_errorMessage, p_s_inputErrorMessage));
	}
	
	
	/**
	 * get console short value
	 * 
	 * @return								short value
	 * @see		Short
	 */
	public static short consoleInputShort() {
		return consoleInputShort("");
	}
	
	/**
	 * get console short value
	 * 
	 * @param	p_s_caption					caption text before user can enter a short value
	 * @return								short value
	 * @see		Short
	 */
	public static short consoleInputShort(String p_s_caption) {
		return consoleInputShort(p_s_caption, "The entered value is not of type short[" + Short.MIN_VALUE + " .. " + Short.MAX_VALUE + "].");
	}
	
	/**
	 * get console short value
	 * 
	 * @param	p_s_caption					caption text before user can enter a short value
	 * @param	p_s_errorMessage			overwrite standard english error message
	 * @return								short value
	 * @see		Short
	 */
	public static short consoleInputShort(String p_s_caption, String p_s_errorMessage) {
		return consoleInputShort(p_s_caption, p_s_errorMessage, "Please enter a value.");
	}
	
	/**
	 * get console short value
	 * 
	 * @param	p_s_caption					caption text before user can enter a short value
	 * @param	p_s_errorMessage			overwrite standard english error message
	 * @param	p_s_inputErrorMessage		overwrite standard english input error message
	 * @return								short value
	 * @see		Short
	 */
	public static short consoleInputShort(String p_s_caption, String p_s_errorMessage, String p_s_inputErrorMessage) {
		if (Helper.isStringEmpty(p_s_errorMessage)) {
			throw new IllegalArgumentException("Please specify an error message.");
		}
		
		if (Helper.isStringEmpty(p_s_inputErrorMessage)) {
			throw new IllegalArgumentException("Please specify an input error message.");
		}
		
		String s_foo = null;
		
		/* check if input string value is a valid short value */
		while (!Helper.isShort(s_foo = doInput(p_s_caption, false, p_s_inputErrorMessage))) {
			System.out.print(p_s_errorMessage + "\n");
		}
		
		return Short.valueOf(s_foo);
	}
	
	
	/**
	 * get console long value
	 * 
	 * @return								long value
	 * @see		Long
	 */
	public static long consoleInputLong() {
		return consoleInputLong("");
	}
	
	/**
	 * get console long value
	 * 
	 * @param	p_s_caption					caption text before user can enter a long value
	 * @return								long value
	 * @see		Long
	 */
	public static long consoleInputLong(String p_s_caption) {
		return consoleInputLong(p_s_caption, "The entered value is not of type long[" + Long.MIN_VALUE + " .. " + Long.MAX_VALUE + "].");
	}
	
	/**
	 * get console long value
	 * 
	 * @param	p_s_caption					caption text before user can enter a long value
	 * @param	p_s_errorMessage			overwrite standard english error message
	 * @return								long value
	 * @see		Long
	 */
	public static long consoleInputLong(String p_s_caption, String p_s_errorMessage) {
		return consoleInputLong(p_s_caption, p_s_errorMessage, "Please enter a value.");
	}
	
	/**
	 * get console long value
	 * 
	 * @param	p_s_caption					caption text before user can enter a long value
	 * @param	p_s_errorMessage			overwrite standard english error message
	 * @param	p_s_inputErrorMessage		overwrite standard english input error message
	 * @return								long value
	 * @see		Long
	 */
	public static long consoleInputLong(String p_s_caption, String p_s_errorMessage, String p_s_inputErrorMessage) {
		if (Helper.isStringEmpty(p_s_errorMessage)) {
			throw new IllegalArgumentException("Please specify an error message.");
		}
		
		if (Helper.isStringEmpty(p_s_inputErrorMessage)) {
			throw new IllegalArgumentException("Please specify an input error message.");
		}
		
		String s_foo = null;
		
		/* check if input string value is a valid long value */
		while (!Helper.isLong(s_foo = doInput(p_s_caption, false, p_s_inputErrorMessage))) {
			System.out.print(p_s_errorMessage + "\n");
		}
		
		return Long.valueOf(s_foo);
	}
	
	
	/**
	 * get console float value
	 * 
	 * @return								float value
	 * @see		Float
	 */
	public static float consoleInputFloat() {
		return consoleInputFloat("");
	}
	
	/**
	 * get console float value
	 * 
	 * @param	p_s_caption					caption text before user can enter a float value
	 * @return								long value
	 * @see		Float
	 */
	public static float consoleInputFloat(String p_s_caption) {
		return consoleInputFloat(p_s_caption, "The entered value is not of type float.");
	}
	
	/**
	 * get console float value
	 * 
	 * @param	p_s_caption					caption text before user can enter a float value
	 * @param	p_s_errorMessage			overwrite standard english error message
	 * @return								long value
	 * @see		Float
	 */
	public static float consoleInputFloat(String p_s_caption, String p_s_errorMessage) {
		return consoleInputFloat(p_s_caption, p_s_errorMessage, "Please enter a value.");
	}
	
	/**
	 * get console float value
	 * 
	 * @param	p_s_caption					caption text before user can enter a float value
	 * @param	p_s_errorMessage			overwrite standard english error message
	 * @param	p_s_inputErrorMessage		overwrite standard english input error message
	 * @return								float value
	 * @see		Float
	 */
	public static float consoleInputFloat(String p_s_caption, String p_s_errorMessage, String p_s_inputErrorMessage) {
		if (Helper.isStringEmpty(p_s_errorMessage)) {
			throw new IllegalArgumentException("Please specify an error message.");
		}
		
		if (Helper.isStringEmpty(p_s_inputErrorMessage)) {
			throw new IllegalArgumentException("Please specify an input error message.");
		}
		
		String s_foo = null;
		
		/* check if input string value is a valid float value */
		while (!Helper.isFloat(s_foo = doInput(p_s_caption, false, p_s_inputErrorMessage))) {
			System.out.print(p_s_errorMessage + "\n");
		}
		
		return Float.valueOf(s_foo.replace(',', '.'));
	}
	
	
	/**
	 * get console double value
	 * 
	 * @return								double value
	 * @see		Double
	 */
	public static double consoleInputDouble() {
		return consoleInputDouble("");
	}
	
	/**
	 * get console double value
	 * 
	 * @param	p_s_caption					caption text before user can enter a double value
	 * @return								double value
	 * @see		Double
	 */
	public static double consoleInputDouble(String p_s_caption) {
		return consoleInputDouble(p_s_caption, "The entered value is not of type double.");
	}
	
	/**
	 * get console double value
	 * 
	 * @param	p_s_caption					caption text before user can enter a double value
	 * @param	p_s_errorMessage			overwrite standard english error message
	 * @return								double value
	 * @see		Double
	 */
	public static double consoleInputDouble(String p_s_caption, String p_s_errorMessage) {
		return consoleInputDouble(p_s_caption, p_s_errorMessage, "Please enter a value.");
	}
	
	/**
	 * get console double value
	 * 
	 * @param	p_s_caption					caption text before user can enter a double value
	 * @param	p_s_errorMessage			overwrite standard english error message
	 * @param	p_s_inputErrorMessage		overwrite standard english input error message
	 * @return								double value
	 * @see		Double
	 */
	public static double consoleInputDouble(String p_s_caption, String p_s_errorMessage, String p_s_inputErrorMessage) {
		if (Helper.isStringEmpty(p_s_errorMessage)) {
			throw new IllegalArgumentException("Please specify an error message.");
		}
		
		if (Helper.isStringEmpty(p_s_inputErrorMessage)) {
			throw new IllegalArgumentException("Please specify an input error message.");
		}
		
		String s_foo = null;
		
		/* check if input string value is a valid double value */
		while (!Helper.isDouble(s_foo = doInput(p_s_caption, false, p_s_inputErrorMessage))) {
			System.out.print(p_s_errorMessage + "\n");
		}
		
		return Double.valueOf(s_foo.replace(',', '.'));
	}
	
	
	/**
	 * get console boolean value
	 * 
	 * @return					boolean value
	 * @see		Boolean
	 */
	public static boolean consoleInputBoolean() {
		return consoleInputBoolean("");
	}
	
	/**
	 * get console boolean value
	 * 
	 * @param	p_s_caption					caption text before user can enter a boolean value
	 * @return								boolean value
	 * @see		Boolean
	 */
	public static boolean consoleInputBoolean(String p_s_caption) {
		String s_foo = doInput(p_s_caption);
		return Helper.isBoolean(s_foo);
	}
	
	
	/**
	 * get console date string value
	 * 
	 * @return					date string value
	 * @see		String
	 */
	public static String consoleInputDate() {
		return consoleInputDate("", null, "The entered value is not of type date.");
	}
	
	/**
	 * get console date string value
	 * 
	 * @return						date string value
	 * @param	p_s_caption			caption text before user can enter a date string value
	 * @see		String
	 */
	public static String consoleInputDate(String p_s_caption) {
		return consoleInputDate(p_s_caption, null, "The entered value is not of type date.");
	}
	
	/**
	 * get console date string value
	 * 
	 * @return						date string value
	 * @param	p_s_caption			caption text before user can enter a date string value
	 * @param	p_s_errorMessage	overwrite standard english error message
	 * @param	p_s_regex			manual regex criteria
	 * @see		String
	 */
	public static String consoleInputDate(String p_s_caption, String p_s_regex, String p_s_errorMessage) {
		return consoleInputDate(p_s_caption, p_s_regex, p_s_errorMessage, "Please enter a value.");
	}
	
	/**
	 * get console date string value
	 * 
	 * @return							date string value
	 * @param	p_s_caption				caption text before user can enter a date string value
	 * @param	p_s_errorMessage		overwrite standard english error message
	 * @param	p_s_inputErrorMessage	overwrite standard english input error message
	 * @param	p_s_regex				manual regex criteria
	 * @see		String
	 */
	public static String consoleInputDate(String p_s_caption, String p_s_regex, String p_s_errorMessage, String p_s_inputErrorMessage) {
		if (Helper.isStringEmpty(p_s_errorMessage)) {
			throw new IllegalArgumentException("Please specify an error message.");
		}
		
		if (Helper.isStringEmpty(p_s_inputErrorMessage)) {
			throw new IllegalArgumentException("Please specify an input error message.");
		}
		
		String s_foo = "";
		
		if (Helper.isStringEmpty(p_s_regex)) { /* use standard date check */
			while (!Helper.isDate(s_foo = doInput(p_s_caption, false, p_s_inputErrorMessage))) {
				System.out.print(p_s_errorMessage + "\n");
			}
		} else {
			/* use manual regex criteria */
			while (!Helper.matchesRegex(s_foo = doInput(p_s_caption, false, p_s_inputErrorMessage), p_s_regex)) {
				System.out.print(p_s_errorMessage + "\n");
			}
		}
		
		return s_foo;
	}
	
	
	/**
	 * get console time string value
	 * 
	 * @return					time string value
	 * @see		String
	 */
	public static String consoleInputTime() {
		return consoleInputTime("", null, "The entered value is not of type time.");
	}
	
	/**
	 * get console time string value
	 * 
	 * @return						time string value
	 * @param	p_s_caption			caption text before user can enter a time string value
	 * @see		String
	 */
	public static String consoleInputTime(String p_s_caption) {
		return consoleInputTime(p_s_caption, null, "The entered value is not of type time.");
	}
	
	/**
	 * get console time string value
	 * 
	 * @return						time string value
	 * @param	p_s_caption			caption text before user can enter a time string value
	 * @param	p_s_errorMessage	overwrite standard english error message
	 * @param	p_s_regex			manual regex criteria
	 * @see		String
	 */
	public static String consoleInputTime(String p_s_caption, String p_s_regex, String p_s_errorMessage) {
		return consoleInputTime(p_s_caption, p_s_regex, p_s_errorMessage, "Please enter a value.");
	}
	
	/**
	 * get console time string value
	 * 
	 * @return							time string value
	 * @param	p_s_caption				caption text before user can enter a time string value
	 * @param	p_s_errorMessage		overwrite standard english error message
	 * @param	p_s_inputErrorMessage	overwrite standard english input error message
	 * @param	p_s_regex				manual regex criteria
	 * @see		String
	 */
	public static String consoleInputTime(String p_s_caption, String p_s_regex, String p_s_errorMessage, String p_s_inputErrorMessage) {
		if (Helper.isStringEmpty(p_s_errorMessage)) {
			throw new IllegalArgumentException("Please specify an error message.");
		}
		
		if (Helper.isStringEmpty(p_s_inputErrorMessage)) {
			throw new IllegalArgumentException("Please specify an input error message.");
		}
		
		String s_foo = "";
		
		if (Helper.isStringEmpty(p_s_regex)) { /* use standard time check */
			while (!Helper.isTime(s_foo = doInput(p_s_caption, false, p_s_inputErrorMessage))) {
				System.out.print(p_s_errorMessage + "\n");
			}
		} else {
			/* use manual regex criteria */
			while (!Helper.matchesRegex(s_foo = doInput(p_s_caption, false, p_s_inputErrorMessage), p_s_regex)) {
				System.out.print(p_s_errorMessage + "\n");
			}
		}
		
		return s_foo;
	}
	
	
	/**
	 * get console date time string value
	 * 
	 * @return					date time string value
	 * @see		String
	 */
	public static String consoleInputDateTime() {
		return consoleInputDateTime("", null, "The entered value is not of type date time.");
	}
	
	/**
	 * get console date time string value
	 * 
	 * @return						date time string value
	 * @param	p_s_caption			caption text before user can enter a date time string value
	 * @see		String
	 */
	public static String consoleInputDateTime(String p_s_caption) {
		return consoleInputDateTime(p_s_caption, null, "The entered value is not of type date time.");
	}
	
	/**
	 * get console date time string value
	 * 
	 * @return						date time string value
	 * @param	p_s_caption			caption text before user can enter a date time string value
	 * @param	p_s_errorMessage	overwrite standard english error message
	 * @param	p_s_regex			manual regex criteria
	 * @see		String
	 */
	public static String consoleInputDateTime(String p_s_caption, String p_s_regex, String p_s_errorMessage) {
		return consoleInputDateTime(p_s_caption, p_s_regex, p_s_errorMessage, "Please enter a value.");
	}
	
	/**
	 * get console date time string value
	 * 
	 * @return							date time string value
	 * @param	p_s_caption				caption text before user can enter a date time string value
	 * @param	p_s_errorMessage		overwrite standard english error message
	 * @param	p_s_inputErrorMessage	overwrite standard english input error message
	 * @param	p_s_regex				manual regex criteria
	 * @see		String
	 */
	public static String consoleInputDateTime(String p_s_caption, String p_s_regex, String p_s_errorMessage, String p_s_inputErrorMessage) {
		if (Helper.isStringEmpty(p_s_errorMessage)) {
			throw new IllegalArgumentException("Please specify an error message.");
		}
		
		if (Helper.isStringEmpty(p_s_inputErrorMessage)) {
			throw new IllegalArgumentException("Please specify an input error message.");
		}
		
		String s_foo = "";
		
		if (Helper.isStringEmpty(p_s_regex)) { /* use standard date time check */
			while (!Helper.isDateTime(s_foo = doInput(p_s_caption, false, p_s_inputErrorMessage))) {
				System.out.print(p_s_errorMessage + "\n");
			}
		} else {
			/* use manual regex criteria */
			while (!Helper.matchesRegex(s_foo = doInput(p_s_caption, false, p_s_inputErrorMessage), p_s_regex)) {
				System.out.print(p_s_errorMessage + "\n");
			}
		}
		
		return s_foo;
	}

	
	/**
	 * get console date interval string value
	 * 
	 * @return					date interval string value
	 * @see		String
	 */
	public static String consoleInputDateInterval() {
		return consoleInputDateInterval("", null, "The entered value is not of type date interval.");
	}
	
	/**
	 * get console date interval string value
	 * 
	 * @return						date interval string value
	 * @param	p_s_caption			caption text before user can enter a date interval string value
	 * @see		String
	 */
	public static String consoleInputDateInterval(String p_s_caption) {
		return consoleInputDateInterval(p_s_caption, null, "The entered value is not of type date interval.");
	}
	
	/**
	 * get console date interval string value
	 * 
	 * @return						date interval string value
	 * @param	p_s_caption			caption text before user can enter a date interval string value
	 * @param	p_s_errorMessage	overwrite standard english error message
	 * @param	p_s_regex			manual regex criteria
	 * @see		String
	 */
	public static String consoleInputDateInterval(String p_s_caption, String p_s_regex, String p_s_errorMessage) {
		return consoleInputDateInterval(p_s_caption, p_s_regex, p_s_errorMessage, "Please enter a value.");
	}
	
	/**
	 * get console date interval string value
	 * 
	 * @return							date interval string value
	 * @param	p_s_caption				caption text before user can enter a date interval string value
	 * @param	p_s_errorMessage		overwrite standard english error message
	 * @param	p_s_inputErrorMessage	overwrite standard english input error message
	 * @param	p_s_regex				manual regex criteria
	 * @see		String
	 */
	public static String consoleInputDateInterval(String p_s_caption, String p_s_regex, String p_s_errorMessage, String p_s_inputErrorMessage) {
		if (Helper.isStringEmpty(p_s_errorMessage)) {
			throw new IllegalArgumentException("Please specify an error message.");
		}
		
		if (Helper.isStringEmpty(p_s_inputErrorMessage)) {
			throw new IllegalArgumentException("Please specify an input error message.");
		}
		
		String s_foo = "";
		
		if (Helper.isStringEmpty(p_s_regex)) { /* use standard date interval check */
			while (!Helper.isDateInterval(s_foo = doInput(p_s_caption, false, p_s_inputErrorMessage))) {
				System.out.print(p_s_errorMessage + "\n");
			}
		} else {
			/* use manual regex criteria */
			while (!Helper.matchesRegex(s_foo = doInput(p_s_caption, false, p_s_inputErrorMessage), p_s_regex)) {
				System.out.print(p_s_errorMessage + "\n");
			}
		}
		
		return s_foo;
	}
	
	
	/**
	 * get console string value with regex criteria
	 * 
	 * @return							string value matching regex expression
	 * @param	p_s_caption				caption text before user can enter a string value
	 * @param	p_s_regex				manual regex criteria
	 * @see		String
	 */
	public static String consoleInputRegex(String p_s_caption, String p_s_regex) {
		return consoleInputRegex(p_s_caption, p_s_regex, "The entered value does not match the regular expression criteria.");
	}
	
	/**
	 * get console string value with regex criteria
	 * 
	 * @return							string value matching regex expression
	 * @param	p_s_caption				caption text before user can enter a string value
	 * @param	p_s_regex				manual regex criteria
	 * @param	p_s_errorMessage		overwrite standard english error message
	 * @see		String
	 */
	public static String consoleInputRegex(String p_s_caption, String p_s_regex, String p_s_errorMessage) {
		return consoleInputRegex(p_s_caption, p_s_regex, p_s_errorMessage, "Please enter a value.");
	}
	
	/**
	 * get console string value with regex criteria
	 * 
	 * @return							string value matching regex expression
	 * @param	p_s_caption				caption text before user can enter a string value
	 * @param	p_s_regex				manual regex criteria
	 * @param	p_s_errorMessage		overwrite standard english error message
	 * @param	p_s_inputErrorMessage	overwrite standard english input error message
	 * @see		String
	 */
	public static String consoleInputRegex(String p_s_caption, String p_s_regex, String p_s_errorMessage, String p_s_inputErrorMessage) {
		if (Helper.isStringEmpty(p_s_regex)) {
			throw new IllegalArgumentException("Please specify a regular expression criteria.");
		}
		
		if (Helper.isStringEmpty(p_s_errorMessage)) {
			throw new IllegalArgumentException("Please specify an error message.");
		}
		
		if (Helper.isStringEmpty(p_s_inputErrorMessage)) {
			throw new IllegalArgumentException("Please specify an input error message.");
		}
		
		String s_foo = "";
		
		while (!Helper.matchesRegex(s_foo = doInput(p_s_caption, false, p_s_inputErrorMessage), p_s_regex)) {
			System.out.print(p_s_errorMessage + "\n");
		}
		
		return s_foo;
	}
}
