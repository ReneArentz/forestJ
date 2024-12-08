package de.forestj.lib;

/**
 * 
 * Class to represent a date interval from string input.
 * Can transform object to readable string, multi-language capable.
 * Transformation to millisecond duration value available.
 *
 */
public class DateInterval {

	/* Fields */
	
	private int y;
	private int m;
	private int d;
	private int h;
	private int i;
	private int s;
	
	private java.util.Locale o_locale;
	
	/* Properties */
	
	public java.util.Locale getLocale() {
		return this.o_locale;
	}
	
	public void setLocale(java.util.Locale p_o_locale) throws NullPointerException {
		if (p_o_locale == null) {
			throw new NullPointerException("Locale parameter missing.");
		}
		
		this.o_locale = p_o_locale;
	}
	
	/* Methods */
	
	/**
	 * creates date interval object
	 * 
	 * @param	p_s_dateInterval	String encoded date interval e.g. P1Y2M3DT4H5M6S
	 * @throws 	IllegalArgumentException
	 * @see		Helper
	 */
	public DateInterval(String p_s_dateInterval) throws IllegalArgumentException {
		this(p_s_dateInterval, null);
	}
	
	/**
	 * creates date interval object
	 * 
	 * @param	p_s_dateInterval	String encoded date interval e.g. P1Y2M3DT4H5M6S
	 * @param	p_o_locale			locale object for language settings
	 * @throws 	IllegalArgumentException
	 * @see		Helper
	 * @see		java.util.Locale
	 */
	public DateInterval(String p_s_dateInterval, java.util.Locale p_o_locale) throws IllegalArgumentException {
		this.y = 0;
		this.m = 0;
		this.d = 0;
		this.h = 0;
		this.i = 0;
		this.s = 0;
		
		this.o_locale = p_o_locale;
		
		/* check if string parameter has a value */
		if (Helper.isStringEmpty(p_s_dateInterval)) {
			throw new IllegalArgumentException("String encoded date interval parameter is null or empty.");
		}
		
		/* check if string parameter is a valid date interval format string */
		if (!Helper.isDateInterval(p_s_dateInterval)) {
			throw new IllegalArgumentException("Parameter[" + p_s_dateInterval + "] does not match date interval format.");
		}
		
		/* split date interval parameter into info values */
		String[] a_info = p_s_dateInterval.split("[\\d]*", -1);
		java.util.ArrayList<String> a_infoList = new java.util.ArrayList<String>();
		
		/* only take elements which are not empty */
		for (int i = 0; i < a_info.length; i++) {
			if (!Helper.isStringEmpty(a_info[i])) {
				a_infoList.add(a_info[i]);
			}
		}
		
		/* split date interval parameter into digit values */
		String[] a_values = p_s_dateInterval.split("[A-Za-z]{1}", -1);
		java.util.ArrayList<String> a_valuesList = new java.util.ArrayList<String>();
		
		/* only take elements which are not empty */
		for (int i = 0; i < a_values.length; i++) {
			if (!Helper.isStringEmpty(a_values[i])) {
				a_valuesList.add(a_values[i]);
			}
		}
		
		int i = 0;
		String s_mode = "";
		
		/* disassemble each info for setting each date interval value */
		for (String s_char : a_infoList) {
			switch (s_char) {
				case "P":
					s_mode = "date";
					break;
				case "T":
					s_mode = "time";
					break;
				
				case "Y":
					this.y = Integer.parseInt(a_valuesList.get(i));
					i++;
					break;
				case "D":
					this.d = Integer.parseInt(a_valuesList.get(i));
					i++;
					break;
				
				case "H":
					this.h = Integer.parseInt(a_valuesList.get(i));
					i++;
					break;
				case "S":
					this.s = Integer.parseInt(a_valuesList.get(i));
					i++;
					break;
			}
			
			switch (s_char) {
				case "M":
					if (s_mode == "date") {
						this.m = Integer.parseInt(a_valuesList.get(i));
						i++;
					} else if (s_mode == "time") {
						this.i = Integer.parseInt(a_valuesList.get(i));
						i++;
					}
				break;
			}
		}
	}
	
	/**
	 * returns millisecond duration long number of a date interval object
	 * 
	 * @return	long value
	 * @see		Long
	 */
	public long toDuration() {
		return
				this.d * 24 * 60 * 60 * 1000 +
				this.h * 60 * 60 * 1000 +
				this.i * 60 * 1000 +
				this.s * 1000;
	}
	
	/**
	 * returns duration long number in seconds of a date interval object
	 * 
	 * @return	long value
	 * @see		Long
	 */
	public long toDurationInSeconds() {
		return
				this.d * 24 * 60 * 60 +
				this.h * 60 * 60 +
				this.i * 60 +
				this.s;
	}
	
	/**
	 * assume date interval data from another date interval object
	 * 
	 * @param	p_o_dateInterval	date interval object
	 * @see		DateInterval
	 */
	public void setDateInterval(DateInterval p_o_dateInterval) {
		this.y = p_o_dateInterval.y;
		this.m = p_o_dateInterval.m;
		this.d = p_o_dateInterval.d;
		this.h = p_o_dateInterval.h;
		this.i = p_o_dateInterval.i;
		this.s = p_o_dateInterval.s;
	}
	
	/**
	 * return date interval object as DE string if locale parameter is null, other locale settings need to be implemented
	 *
	 * @return	String
	 * @see		String
	 */
	public String toString() {
		return this.toString(this.o_locale);
	}
	
	/**
	 * return date interval object as string
	 * @param	String[]	direct definition of date interval words
	 * 						must contain 6 elements for 'year', 'month', 'day', 'hour', 'minute', 'second'
	 * @return	String
	 * @see		String
	 */
	public String toString(String[] p_a_definitions) throws IllegalArgumentException {
		String s_foo = "";
		
		if (p_a_definitions.length != 6) {
			throw new IllegalArgumentException("Parameter for direct defintion of date interval must contain '6' words.");
		}
		
		if (this.y != 0) {
			s_foo += this.y + " " + p_a_definitions[0] + " ";
		}
		
		if (this.m != 0) {
			s_foo += this.m + " " + p_a_definitions[1] + " ";
		}
		
		if (this.d != 0) {
			s_foo += this.d + " " + p_a_definitions[2] + " ";
		}
		
		if (this.h != 0) {
			s_foo += this.h + " " + p_a_definitions[3] + " ";
		}
		
		if (this.i != 0) {
			s_foo += this.i + " " + p_a_definitions[4] + " ";
		}
		
		if (this.s != 0) {
			s_foo += this.s + " " + p_a_definitions[5] + " ";
		}
		
		return s_foo.trim();
	}
	
	/**
	 * return date interval object as DE string if locale parameter is null, other locale settings need to be implemented
	 * 
	 * @param	p_o_locale	locale object for language settings
	 * @return	String
	 * @see		String
	 */
	public String toString(java.util.Locale p_o_locale) {
		String s_foo = "";
		
		String[] a_word = new String[6];
		
		if ( (p_o_locale == null) || (p_o_locale.getLanguage().toLowerCase().contentEquals("de")) ) {
			a_word[0] = "Jahr(e)";
			a_word[1] = "Monat(e)";
			a_word[2] = "Tag(e)";
			a_word[3] = "Stunde(n)";
			a_word[4] = "Minute(n)";
			a_word[5] = "Sekunde(n)";
		} else if (p_o_locale.getLanguage().toLowerCase().contentEquals("en")) {
			a_word[0] = "year(s)";
			a_word[1] = "month(s)";
			a_word[2] = "day(s)";
			a_word[3] = "hour(s)";
			a_word[4] = "minute(s)";
			a_word[5] = "second(s)";
		}
		
		if (this.y != 0) {
			s_foo += this.y + " " + a_word[0] + " ";
		}
		
		if (this.m != 0) {
			s_foo += this.m + " " + a_word[1] + " ";
		}
		
		if (this.d != 0) {
			s_foo += this.d + " " + a_word[2] + " ";
		}
		
		if (this.h != 0) {
			s_foo += this.h + " " + a_word[3] + " ";
		}
		
		if (this.i != 0) {
			s_foo += this.i + " " + a_word[4] + " ";
		}
		
		if (this.s != 0) {
			s_foo += this.s + " " + a_word[5] + " ";
		}
		
		return s_foo.trim();
	}
}
