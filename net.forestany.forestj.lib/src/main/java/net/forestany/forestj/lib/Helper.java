package net.forestany.forestj.lib;
/**
 * 
 * Collection of static methods to help most often used program sequences which are helpful.
 *
 */
public class Helper {
	
	/* Constants */
	
	/**
	 * uppercase characters
	 */
	public static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/**
	 * lowercase characters
	 */
    public static final String LOWERCASE_CHARACTERS = UPPERCASE_CHARACTERS.toLowerCase(java.util.Locale.ROOT);
    /**
     * digits characters
     */
    public static final String DIGITS_CHARACTERS = "0123456789";
    /**
     * alphanumeric characters
     */
    public static final String ALPHANUMERIC_CHARACTERS = UPPERCASE_CHARACTERS + LOWERCASE_CHARACTERS + DIGITS_CHARACTERS;
	
	/* Fields */
	
	/* Properties */
	
	/* Methods */
	
    /**
	 * empty constructor
	 */
	public Helper() {
		
	}
    
	/**
	 * checks if a given string is empty
	 * 
	 * @param	p_s_string	String parameter variable
	 * @return				true - String is empty, false - String is not empty
	 * @see		String
	 */
	public static boolean isStringEmpty(String p_s_string) {
		return (p_s_string == null || p_s_string.trim().length() == 0);
	}
	
	/**
	 * checks if a given object is a string
	 * 
	 * @param	p_o_object	Object parameter variable
	 * @return				true - Object is String, false - Object is not String
	 * @see		String
	 * @see		Object
	 */
	public static boolean isString(Object p_o_object) {
	    if (p_o_object instanceof String) {
	       return true;
	    } else {
	        return false;
	    }
	}
	
	/**
	 * checks if a given string is a byte
	 * 
	 * @param	p_s_string	String parameter variable
	 * @return				true - String is Byte, false - String is not byte
	 * @see		String
	 * @see		Byte
	 */
	public static boolean isByte(String p_s_string) {
	    try {
	    	Byte.parseByte(p_s_string);
	    } catch (NumberFormatException | NullPointerException e) {
	        return false;
	    }
	    
	    return true;
	}
	
	/**
	 * checks if a given string is a short
	 * 
	 * @param	p_s_string	String parameter variable
	 * @return				true - String is Short, false - String is not Short
	 * @see		String
	 * @see		Short
	 */
	public static boolean isShort(String p_s_string) {
	    try {
	    	Short.parseShort(p_s_string);
	    } catch (NumberFormatException | NullPointerException e) {
	        return false;
	    }
	    
	    return true;
	}
	
	/**
	 * checks if a given string is an integer
	 * 
	 * @param	p_s_string	String parameter variable
	 * @return				true - String is Integer, false - String is not Integer
	 * @see		String
	 * @see		Integer
	 */
	public static boolean isInteger(String p_s_string) {
	    try {
	        Integer.parseInt(p_s_string);
	    } catch (NumberFormatException | NullPointerException e) {
	        return false;
	    }
	    
	    return true;
	}
	
	/**
	 * checks if a given string is a long
	 * 
	 * @param	p_s_string	String parameter variable
	 * @return				true - String is Long, false - String is not Long
	 * @see		String
	 * @see		Long
	 */
	public static boolean isLong(String p_s_string) {
	    try {
	        Long.parseLong(p_s_string);
	    } catch (NumberFormatException | NullPointerException e) {
	        return false;
	    }
	    
	    return true;
	}
	
	/**
	 * checks if a given string is a float
	 * 
	 * @param	p_s_string	String parameter variable
	 * @return				true - String is Float, false - String is not Float
	 * @see		String
	 * @see		Float
	 */
	public static boolean isFloat(String p_s_string) {
	    try {
	    	p_s_string = p_s_string.replace(',', '.');
	    	Float.parseFloat(p_s_string);
	    } catch (NumberFormatException | NullPointerException e) {
	        return false;
	    }
	    
	    return true;
	}
	
	/**
	 * checks if a given string is a double
	 * 
	 * @param	p_s_string	String parameter variable
	 * @return				true - String is Double, false - String is not Double
	 * @see		String
	 * @see		Double
	 */
	public static boolean isDouble(String p_s_string) {
	    try {
	    	p_s_string = p_s_string.replace(',', '.');
	    	Double.parseDouble(p_s_string);
	    } catch (NumberFormatException | NullPointerException e) {
	        return false;
	    }
	    
	    return true;
	}
	
	/**
	 * checks if a given string is a boolean
	 * 
	 * @param	p_s_string	String parameter variable
	 * @return				true - String is Boolean, false - String is not Boolean
	 * @see		String
	 * @see		Boolean
	 */
	public static boolean isBoolean(String p_s_string) {
	    return Boolean.parseBoolean(p_s_string);
	}
	
	/**
	 * checks if a given string matches a regular expression
	 * 
	 * @param	p_s_string	String parameter variable
	 * @param	p_s_regex	String regular expression parameter variable
	 * @return				true - String matches Regex, false - String not matches Regex
	 * @see		String
	 */
	public static boolean matchesRegex(String p_s_string, String p_s_regex) {
		return java.util.regex.Pattern.matches(p_s_regex, p_s_string);
	}
	
	/**
	 * returns amount of sub strings within a string
	 * 
	 * @param	p_s_string		String parameter variable
	 * @param	p_s_subString	Sub-String parameter variable
	 * @return					amount of sub string within string parameter
	 * @see		String
	 * @see		Integer
	 */
	public static int countSubStrings(String p_s_string, String p_s_subString) {
		return (int) java.util.regex.Pattern.compile(p_s_subString).matcher(p_s_string).results().count();
	}
	
	/**
	 * checks if a given string matches a date format
	 * 
	 * @param	p_s_string	String parameter variable
	 * 						valid formats: [dd-MM-yyyy], [dd.MM.yyyy], [dd/MM/yyyy], [yyyy/MM/dd], [yyyy-MM-dd], [MM/dd/yyyy], [yyyy/dd/MM]
	 * 
	 * @return				true - String matches a date format, false - String does not match a date format
	 * @see		String
	 * @see		java.time.format.DateTimeFormatter
	 * @see		java.time.LocalDate
	 */
	public static boolean isDate(String p_s_string) {
		/* patterns for date formats */
		java.time.format.DateTimeFormatter o_dateFormatter = java.time.format.DateTimeFormatter.ofPattern(""
			+ "[dd-MM-yyyy]"
			+ "[dd.MM.yyyy]"
			+ "[dd/MM/yyyy]"
			+ "[yyyy/MM/dd]"
			+ "[yyyy-MM-dd]"
			+ "[yyyy.MM.dd]"
			+ "[yyyyMMdd]"
		);
		
		try {
			java.time.LocalDate.parse(p_s_string, o_dateFormatter);
			return true;
		} catch (java.time.DateTimeException o_exc) {
			/* alternate patterns for date formats */
			o_dateFormatter = java.time.format.DateTimeFormatter.ofPattern(""
				+ "[MM/dd/yyyy]"
				+ "[yyyy/dd/MM]"
				+ "[yyyyMMdd]"
			);
			
			try {
				java.time.LocalDate.parse(p_s_string, o_dateFormatter);
				return true;
			} catch (java.time.DateTimeException o_subExc) {
				return false;
			}
		}
	}
	
	/**
	 * checks if a given string matches a time format
	 * 
	 * @param	p_s_string	String parameter variable
	 * 						valid formats: [HH:mm:ss], [HH:mm]
	 * 
	 * @return				true - String matches time format, false - String not matches time format
	 * @see		String
	 * @see		java.time.format.DateTimeFormatter
	 * @see		java.time.LocalTime
	 */
	public static boolean isTime(String p_s_string) {
		/* patterns for time formats */
		java.time.format.DateTimeFormatter o_timeFormatter = java.time.format.DateTimeFormatter.ofPattern(""
			+ "[HH:mm:ss]"
			+ "[HH:mm]"
			+ "[HHmmss]"
			+ "[HHmm]"
		);
		
		try {
			java.time.LocalTime.parse(p_s_string, o_timeFormatter);
			return true;
		} catch (java.time.DateTimeException o_exc) {
			return false;
		}
	}
	
	/**
	 * checks if a given string matches a date time format
	 * 
	 * @param	p_s_string	String parameter variable
	 * 						valid date part formats: [dd-MM-yyyy], [dd.MM.yyyy], [dd/MM/yyyy], [yyyy/MM/dd], [yyyy-MM-dd], [MM/dd/yyyy], [yyyy/dd/MM]
	 * 						valid time part formats: [HH:mm:ss], [HH:mm]
	 * 
	 * @return				true - String matches date time format, false - String not matches date time format
	 * @see		String
	 * @see		java.time.format.DateTimeFormatter
	 * @see		java.time.LocalDateTime
	 */
	public static boolean isDateTime(String p_s_string) {
		String[] a_dateAndTimeParts = null;
		
		if (p_s_string.contains("T")) {
			/* date and time are separated by 'T' */
			a_dateAndTimeParts = p_s_string.split("T");
			
			if (a_dateAndTimeParts.length != 2) {
				/* only one 'T' in parameter string value is valid */
				return false;
			}
		} else if (p_s_string.contains(" ")) {
			/* date and time are separated by ' ' */
			a_dateAndTimeParts = p_s_string.split(" ");
			
			if (a_dateAndTimeParts.length != 2) {
				/* only one ' ' in parameter string value is valid */
				return false;
			}
		} else {
			/* date and time are not separated by 'T' or ' ' */
			return false;
		}
		
		/* remove UTC 'Z' at the end of time part */
		if (a_dateAndTimeParts[1].endsWith("Z")) {
			a_dateAndTimeParts[1] = a_dateAndTimeParts[1].substring(0, a_dateAndTimeParts[1].length() - 1);
		}
		
		/* check if both parts are matching date and time format */
		if (isDate(a_dateAndTimeParts[0]) && isTime(a_dateAndTimeParts[1])) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * checks if a given string matches a date interval regular expression
	 * 
	 * @param	p_s_string	String parameter variable
	 * @return				true - String matches date interval, false - String not matches date interval
	 * @see		String
	 */
	public static boolean isDateInterval(String p_s_string) {
		return matchesRegex(p_s_string, "((P(((\\d)+Y(\\d)+M((\\d)+(W|D))?)|((\\d)+(Y|M)(\\d)+(W|D))|((\\d)+(Y|M|W|D)))T(((\\d)+H(\\d)+M(\\d)+S)|((\\d)+H(\\d)+(M|S))|((\\d)+M(\\d)+S)|((\\d)+(H|M|S))))|(PT(((\\d)+H(\\d)+M(\\d)+S)|((\\d)+H(\\d)+(M|S))|((\\d)+M(\\d)+S)|((\\d)+(H|M|S))))|(P(((\\d)+Y(\\d)+M((\\d)+(W|D))?)|((\\d)+(Y|M)(\\d)+(W|D))|((\\d)+(Y|M|W|D)))))$");
	}
	
	/**
	 * convert a local date time object to a RFC 1123 conform date string
	 * 
	 * @param	p_o_datetime					DateTime parameter variable
	 * @return									RFC 1123 conform date string
	 * @see		java.time.LocalDateTime
	 * @see		String
	 */
	public static String toRFC1123(java.time.LocalDateTime p_o_datetime) {
		java.time.ZonedDateTime o_zonedDatetime = p_o_datetime.atZone(java.time.ZoneId.systemDefault());
		return java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(o_zonedDatetime.withZoneSameInstant(java.time.ZoneId.of("UTC")));
	}
	
	/**
	 * convert a local date time object to a iso-8601 utc conform date string
	 * 
	 * @param	p_o_datetime					DateTime parameter variable
	 * @return									iso-8601 utc conform date string
	 * @see		java.time.LocalDateTime
	 * @see		String
	 */
	public static String toISO8601UTC(java.time.LocalDateTime p_o_datetime) {
		java.time.ZonedDateTime o_zonedDatetime = p_o_datetime.atZone(java.time.ZoneId.systemDefault());
		return java.time.format.DateTimeFormatter.ISO_INSTANT.format(o_zonedDatetime.withZoneSameInstant(java.time.ZoneId.of("UTC")));
	}

	/**
	 * convert a iso-8601 utc conform date string to a local date time object
	 * 
	 * @param	p_s_string						iso-8601 utc conform date string
	 * @return									local date time object
	 * @throws	java.time.DateTimeException		cannot convert string, details in exception message
	 * @see		String
	 * @see		java.time.LocalDateTime
	 */
	public static java.time.LocalDateTime fromISO8601UTC(String p_s_string) throws java.time.DateTimeException {
		String[] a_dateAndTimeParts = null;
		
		if (p_s_string.contains("T")) {
			/* date and time are separated by 'T' */
			a_dateAndTimeParts = p_s_string.split("T");
			
			if (a_dateAndTimeParts.length != 2) {
				/* only one 'T' in parameter string value is valid */
				throw new java.time.DateTimeException("Invalid date time string[" + p_s_string + "]; only one 'T' in parameter string value is valid");
			}
		} else if (p_s_string.contains(" ")) {
			/* date and time are separated by ' ' */
			a_dateAndTimeParts = p_s_string.split(" ");
			
			if (a_dateAndTimeParts.length != 2) {
				/* only one ' ' in parameter string value is valid */
				throw new java.time.DateTimeException("Invalid date time string[" + p_s_string + "]; only one ' ' in parameter string value is valid");
			}
		} else {
			if (isDate(p_s_string)) {
				/* if we only have a date part, we add '00:00:00' as time part */
				p_s_string += "T00:00:00";
				
				/* date and time are separated by 'T' */
				a_dateAndTimeParts = p_s_string.split("T");
				
				if (a_dateAndTimeParts.length != 2) {
					/* only one 'T' in parameter string value is valid */
					throw new java.time.DateTimeException("Invalid date time string[" + p_s_string + "]; only one 'T' in parameter string value is valid");
				}
			} else {
				/* date and time are not separated by 'T' or ' ' */
				throw new java.time.DateTimeException("Invalid date time string[" + p_s_string + "]; date and time are not separated by 'T' or ' '");
			}
		}
		
		/* remove milliseconds of time part */
		if (a_dateAndTimeParts[1].contains(".")) {
			a_dateAndTimeParts[1] = a_dateAndTimeParts[1].substring(0, a_dateAndTimeParts[1].indexOf("."));
		}
		
		/* remove UTC 'Z' at the end of time part */
		if (a_dateAndTimeParts[1].endsWith("Z")) {
			a_dateAndTimeParts[1] = a_dateAndTimeParts[1].substring(0, a_dateAndTimeParts[1].length() - 1);
		}
		
		/* check if both parts are matching date and time format */
		if (!(isDate(a_dateAndTimeParts[0]) && isTime(a_dateAndTimeParts[1]))) {
			throw new java.time.DateTimeException("Invalid date time string[" + p_s_string + "]; both parts are not matching date(" + isDate(a_dateAndTimeParts[0]) + ") and time(" + isTime(a_dateAndTimeParts[1]) + ") format");
		}

		/* patterns for date formats */
		java.time.format.DateTimeFormatter o_dateFormatter = java.time.format.DateTimeFormatter.ofPattern(""
			+ "[dd-MM-yyyy]"
			+ "[dd.MM.yyyy]"
			+ "[dd/MM/yyyy]"
			+ "[yyyy/MM/dd]"
			+ "[yyyy-MM-dd]"
			+ "[yyyy.MM.dd]"
			+ "[yyyyMMdd]"
		);
		
		/* alternate patterns for date formats */
		java.time.format.DateTimeFormatter o_dateFormatterAlt = java.time.format.DateTimeFormatter.ofPattern(""
			+ "[MM/dd/yyyy]"
			+ "[yyyy/dd/MM]"
			+ "[yyyyMMdd]"
		);
		
		/* patterns for time formats */
		java.time.format.DateTimeFormatter o_timeFormatter = java.time.format.DateTimeFormatter.ofPattern(""
			+ "[HH:mm:ss]"
			+ "[HH:mm]"
		);
		
		java.time.LocalDate o_localDate = null;
		
		try {
			o_localDate = java.time.LocalDate.parse(a_dateAndTimeParts[0], o_dateFormatter);
		} catch (java.time.DateTimeException o_exc) {
			o_localDate = java.time.LocalDate.parse(a_dateAndTimeParts[0], o_dateFormatterAlt);
		}
		
		java.time.LocalTime o_localTime = java.time.LocalTime.parse(a_dateAndTimeParts[1], o_timeFormatter);
		
		java.time.ZonedDateTime o_zonedDatetime = java.time.LocalDateTime.of(o_localDate, o_localTime).atZone(java.time.ZoneId.of("UTC"));
		java.time.ZonedDateTime o_utcZonedDateTime = o_zonedDatetime.withZoneSameInstant(java.time.ZoneId.systemDefault());
		
		return o_utcZonedDateTime.toLocalDateTime();
	}
	
	/**
	 * convert a java util date object to a iso-8601 utc conform date string
	 * 
	 * @param	p_o_date						Date parameter variable
	 * @return									iso-8601 utc conform date string
	 * @see		java.util.Date
	 * @see		String
	 */
	public static String utilDateToISO8601UTC(java.util.Date p_o_date) {
		java.text.DateFormat o_dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		o_dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
		return o_dateFormat.format(p_o_date);
	}

	/**
	 * convert a iso-8601 utc conform date string to a java util date object
	 * 
	 * @param	p_s_date						iso-8601 utc conform date string
	 * @return									local date time object
	 * @throws java.text.ParseException			cannot convert string, details in exception message
	 * @see		String
	 * @see		java.util.Date
	 */
	public static java.util.Date fromISO8601UTCToUtilDate(String p_s_date) throws java.text.ParseException {
		java.text.DateFormat o_dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		o_dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
		java.util.Date o_date = o_dateFormat.parse(p_s_date);
		return o_date;
	}
	
	/**
	 * convert a local date time object to a iso-8601 conform date time string, but keep local timezone - no 'Z'
	 * 
	 * @param	p_o_datetime					DateTime parameter variable
	 * @return									iso-8601 conform date time string
	 * @see		java.time.LocalDateTime
	 * @see		String
	 */
	public static String toDateTimeString(java.time.LocalDateTime p_o_datetime) {
		String s_zoneOffset = java.time.OffsetDateTime.now().getOffset().toString();
		String s_plus_or_minus = s_zoneOffset.substring(0, 1);
		s_zoneOffset = s_zoneOffset.substring(1);
		int i_hours = Integer.valueOf(s_zoneOffset.split(":")[0]);
		int i_minutes = Integer.valueOf(s_zoneOffset.split(":")[1]);
		
		if (s_plus_or_minus.contentEquals("+")) {
			p_o_datetime = p_o_datetime.plusHours(i_hours).plusMinutes(i_minutes);
		} else {
			p_o_datetime = p_o_datetime.minusHours(i_hours).minusMinutes(i_minutes);
		}
		
		java.time.ZonedDateTime o_zonedDatetime = p_o_datetime.atZone(java.time.ZoneId.systemDefault());
		String s_foo = java.time.format.DateTimeFormatter.ISO_INSTANT.format(o_zonedDatetime.withZoneSameInstant(java.time.ZoneId.of("UTC")));
		return s_foo.substring(0, s_foo.length() - 1);
	}

	/**
	 * convert a conform date time string to a local date time object, but keep local timezone - no 'Z'
	 * 
	 * @param	p_s_string						conform date time string
	 * @return									local date time object
	 * @throws	java.time.DateTimeException		cannot convert string, details in exception message
	 * @see		String
	 * @see		java.time.LocalDateTime
	 */
	public static java.time.LocalDateTime fromDateTimeString(String p_s_string) throws java.time.DateTimeException {
		String[] a_dateAndTimeParts = null;
		
		if (p_s_string.contains("T")) {
			/* date and time are separated by 'T' */
			a_dateAndTimeParts = p_s_string.split("T");
			
			if (a_dateAndTimeParts.length != 2) {
				/* only one 'T' in parameter string value is valid */
				throw new java.time.DateTimeException("Invalid date time string[" + p_s_string + "]; only one 'T' in parameter string value is valid");
			}
		} else if (p_s_string.contains(" ")) {
			/* date and time are separated by ' ' */
			a_dateAndTimeParts = p_s_string.split(" ");
			
			if (a_dateAndTimeParts.length != 2) {
				/* only one ' ' in parameter string value is valid */
				throw new java.time.DateTimeException("Invalid date time string[" + p_s_string + "]; only one ' ' in parameter string value is valid");
			}
		} else if ( (p_s_string.length() == 14) && (p_s_string.matches("[0-9]+")) ) { /* 14 digits in a row -> yyyyMMddHHmmss */
			/* separate date and time part */
			a_dateAndTimeParts = new String[2];
			a_dateAndTimeParts[0] = p_s_string.substring(0, 8); /* date part yyyyMMdd */
			a_dateAndTimeParts[1] = p_s_string.substring(8); /* time part HHmmss */
		} else {
			/* date and time are not separated by 'T' or ' ' */
			throw new java.time.DateTimeException("Invalid date time string[" + p_s_string + "]; date and time are not separated by 'T' or ' '");
		}
		
		/* remove UTC 'Z' at the end of time part */
		if (a_dateAndTimeParts[1].endsWith("Z")) {
			a_dateAndTimeParts[1] = a_dateAndTimeParts[1].substring(0, a_dateAndTimeParts[1].length() - 1);
		}
		
		/* check if both parts are matching date and time format */
		if (!(isDate(a_dateAndTimeParts[0]) && isTime(a_dateAndTimeParts[1]))) {
			throw new java.time.DateTimeException("Invalid date time string[" + p_s_string + "]; both parts are not matching date(" + isDate(a_dateAndTimeParts[0]) + ") and time(" + isTime(a_dateAndTimeParts[1]) + ") format");
		}

		/* patterns for date formats */
		java.time.format.DateTimeFormatter o_dateFormatter = java.time.format.DateTimeFormatter.ofPattern(""
			+ "[dd-MM-yyyy]"
			+ "[dd.MM.yyyy]"
			+ "[dd/MM/yyyy]"
			+ "[yyyy/MM/dd]"
			+ "[yyyy-MM-dd]"
			+ "[yyyy.MM.dd]"
			+ "[yyyyMMdd]"
		);
		
		/* alternate patterns for date formats */
		java.time.format.DateTimeFormatter o_dateFormatterAlt = java.time.format.DateTimeFormatter.ofPattern(""
			+ "[MM/dd/yyyy]"
			+ "[yyyy/dd/MM]"
			+ "[yyyyMMdd]"
		);
		
		/* patterns for time formats */
		java.time.format.DateTimeFormatter o_timeFormatter = java.time.format.DateTimeFormatter.ofPattern(""
			+ "[HH:mm:ss]"
			+ "[HH:mm]"
			+ "[HHmmss]"
			+ "[HHmm]"
		);
		
		java.time.LocalDate o_localDate = null;
		
		try {
			o_localDate = java.time.LocalDate.parse(a_dateAndTimeParts[0], o_dateFormatter);
		} catch (java.time.DateTimeException o_exc) {
			o_localDate = java.time.LocalDate.parse(a_dateAndTimeParts[0], o_dateFormatterAlt);
		}
		
		java.time.LocalTime o_localTime = java.time.LocalTime.parse(a_dateAndTimeParts[1], o_timeFormatter);
		
		return java.time.LocalDateTime.of(o_localDate, o_localTime);
	}
	
	/**
	 * convert a conform date string to a local date object, but keep local timezone - no 'Z'
	 * 
	 * @param	p_s_string						conform date string
	 * @return									local date object
	 * @throws	java.time.DateTimeException		cannot convert string, details in exception message
	 * @see		String
	 * @see		java.time.LocalDate
	 */
	public static java.time.LocalDate fromDateString(String p_s_string) throws java.time.DateTimeException {
		/* check if parameter matching date format */
		if (!isDate(p_s_string)) {
			throw new java.time.DateTimeException("Invalid date string[" + p_s_string + "]; not matching date format");
		}

		/* patterns for date formats */
		java.time.format.DateTimeFormatter o_dateFormatter = java.time.format.DateTimeFormatter.ofPattern(""
			+ "[dd-MM-yyyy]"
			+ "[dd.MM.yyyy]"
			+ "[dd/MM/yyyy]"
			+ "[yyyy/MM/dd]"
			+ "[yyyy-MM-dd]"
			+ "[yyyy.MM.dd]"
			+ "[yyyyMMdd]"
		);
		
		/* alternate patterns for date formats */
		java.time.format.DateTimeFormatter o_dateFormatterAlt = java.time.format.DateTimeFormatter.ofPattern(""
			+ "[MM/dd/yyyy]"
			+ "[yyyy/dd/MM]"
			+ "[yyyyMMdd]"
		);
		
		java.time.LocalDate o_localDate = null;
		
		try {
			o_localDate = java.time.LocalDate.parse(p_s_string, o_dateFormatter);
		} catch (java.time.DateTimeException o_exc) {
			o_localDate = java.time.LocalDate.parse(p_s_string, o_dateFormatterAlt);
		}
		
		return o_localDate;
	}
	
	/**
	 * convert a conform time string to a local time object, but keep local timezone - no 'Z'
	 * 
	 * @param	p_s_string						conform time string
	 * @return									local time object
	 * @throws	java.time.DateTimeException		cannot convert string, details in exception message
	 * @see		String
	 * @see		java.time.LocalTime
	 */
	public static java.time.LocalTime fromTimeString(String p_s_string) throws java.time.DateTimeException {
		/* check if parameter matching time format */
		if (!isTime(p_s_string)) {
			throw new java.time.DateTimeException("Invalid time string[" + p_s_string + "]; not matching time format");
		}

		/* patterns for time formats */
		java.time.format.DateTimeFormatter o_timeFormatter = java.time.format.DateTimeFormatter.ofPattern(""
			+ "[HH:mm:ss]"
			+ "[HH:mm]"
			+ "[HHmmss]"
			+ "[HHmm]"
		);
		
		java.time.LocalTime o_localTime = java.time.LocalTime.parse(p_s_string, o_timeFormatter);
		
		return o_localTime;
	}
	
	/**
	 * convert a java util date object to a iso-8601 conform date string, but keep local timezone - no 'Z'
	 * 
	 * @param	p_o_date						Date parameter variable
	 * @return									iso-8601 conform date string
	 * @see		java.util.Date
	 * @see		String
	 */
	public static String utilDateToDateTimeString(java.util.Date p_o_date) {
		java.text.DateFormat o_dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return o_dateFormat.format(p_o_date);
	}

	/**
	 * convert a iso-8601 conform date string to a java util date object, but keep local timezone - no 'Z'
	 * 
	 * @param	p_s_date						iso-8601 conform date string
	 * @return									local date time object
	 * @throws java.text.ParseException			cannot convert string, details in exception message
	 * @see		String
	 * @see		java.util.Date
	 */
	public static java.util.Date fromDateTimeStringToUtilDate(String p_s_date) throws java.text.ParseException {
		java.text.DateFormat o_dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		java.util.Date o_date = o_dateFormat.parse(p_s_date);
		return o_date;
	}
	
	/**
	 * creates a random integer value between min..max parameter value
	 * 
	 * @param	p_i_min		minimal integer range
	 * @param	p_i_max		maximal integer range
	 * @return				random integer value
	 * @see		Integer
	 */
	public static int randomIntegerRange(int p_i_min, int p_i_max) {
		int r = (int)Math.round( (Math.random() * ( (Double.valueOf(p_i_max) - Double.valueOf(p_i_min)) + 1.0 )) + Double.valueOf(p_i_min) );
		return (r > p_i_max) ? p_i_max : r;
	}
	
	/**
	 * creates a random double value between min..max parameter value
	 * 
	 * @param	p_d_min		minimal double range
	 * @param	p_d_max		maximal double range
	 * @return				random double value
	 * @see		Double
	 */
	public static double randomDoubleRange(double p_d_min, double p_d_max) {
	    return ( ( Math.random() * (p_d_max - p_d_min) ) + p_d_min );
	}

	/**
	 * converts a byte array to a short
	 * 
	 * @param	p_a_bytes	byte array, length 1..2 or return value will be 0
	 * @return				short value, 0 if parameter is null
	 * @see		Short
	 * @see		Byte
	 */
	public static short byteArrayToShort(byte[] p_a_bytes) {
		short sh_return = 0;
		
		if (p_a_bytes != null) {
			if ( (p_a_bytes.length > 0) && (p_a_bytes.length < 3) ) {
				for (int i = p_a_bytes.length; i > 0; i--) {
					sh_return |= ( ( 0x00FF << ((p_a_bytes.length - i) * 8) ) & (p_a_bytes[i - 1] << ((p_a_bytes.length - i) * 8)));
				}
			}
		}
		
		return sh_return;
	}
	
	/**
	 * converts a byte array to an integer
	 * 
	 * @param	p_a_bytes	byte array, length 1..4 or return value will be 0
	 * @return				integer value, 0 if parameter is null
	 * @see		Integer
	 * @see		Byte
	 */
	public static int byteArrayToInt(byte[] p_a_bytes) {
		int i_return = 0;
		
		if (p_a_bytes != null) {
			if ( (p_a_bytes.length > 0) && (p_a_bytes.length < 5) ) {
				for (int i = p_a_bytes.length; i > 0; i--) {
					i_return |= ( ( 0x000000FF << ((p_a_bytes.length - i) * 8) ) & (p_a_bytes[i - 1] << ((p_a_bytes.length - i) * 8)));
				}
			}
		}
		
		return i_return;
	}
	
	/**
	 * converts a byte array to a long
	 * 
	 * @param	p_a_bytes	byte array, length 1..8 or return value will be 0
	 * @return				long value, 0 if parameter is null
	 * @see		Long
	 * @see		Byte
	 */
	public static long byteArrayToLong(byte[] p_a_bytes) {
		long l_return = 0;
		
		if (p_a_bytes != null) {
			if ( (p_a_bytes.length > 0) && (p_a_bytes.length < 9) ) {
				for (int i = p_a_bytes.length; i > 0; i--) {
					l_return |= ( ( 0x00000000000000FFl << ((p_a_bytes.length - i) * 8) ) & ((long)p_a_bytes[i - 1] << ((p_a_bytes.length - i) * 8)));
				}
			}
		}
		
		return l_return;
	}
	
	/**
	 * converts a short to a byte array
	 * 
	 * @param	p_sh_short		short value
	 * @return					byte array, length 1..2 - null if invalid short value
	 * @see		Byte
	 * @see		Short
	 */
	public static byte[] shortToByteArray(short p_sh_short) {
		byte[] a_return = null;
		
		if ( (p_sh_short >= Short.MIN_VALUE) && (p_sh_short <= Short.MAX_VALUE) ) {
			byte by_arrayLength = 1;
			
			if ((p_sh_short > 255) || (p_sh_short < 0)) {
				by_arrayLength++;
			}
			
			a_return = new byte[by_arrayLength];
			
			for (int i = by_arrayLength; i > 0; i--) {
				a_return[by_arrayLength - i] |= (0x00FF & (p_sh_short >> ((i - 1) * 8)));
			}
		}
		
		return a_return;
	}
	
	/**
	 * converts an integer to a byte array
	 * 
	 * @param	p_i_integer	integer value
	 * @return				byte array, length 1..4 - null if invalid integer value
	 * @see		Byte
	 * @see		Integer
	 */
	public static byte[] intToByteArray(int p_i_integer) {
		byte[] a_return = null;
		
		if ( (p_i_integer >= Integer.MIN_VALUE) && (p_i_integer <= Integer.MAX_VALUE) ) {
			byte by_arrayLength = 1;
			
			if ((p_i_integer > 255) || (p_i_integer < 0)) {
				by_arrayLength++;
			}
			
			if ((p_i_integer > 65535) || (p_i_integer < 0)) {
				by_arrayLength++;
			}
			
			if ((p_i_integer > 16777215) || (p_i_integer < 0)) {
				by_arrayLength++;
			}
			
			a_return = new byte[by_arrayLength];
			
			for (int i = by_arrayLength; i > 0; i--) {
				a_return[by_arrayLength - i] |= (0x000000FF & (p_i_integer >> ((i - 1) * 8)));
			}
		}
		
		return a_return;
	}
	
	/**
	 * converts a long to a byte array
	 * 
	 * @param	p_l_long	long value
	 * @return				byte array, length 1..8 - null if invalid long value
	 * @see		Byte
	 * @see		Long
	 */
	public static byte[] longToByteArray(long p_l_long) {
		byte[] a_return = null;
		
		if ( (p_l_long >= Long.MIN_VALUE) && (p_l_long <= Long.MAX_VALUE) ) {
			byte by_arrayLength = 1;
			
			if ((p_l_long > 255) || (p_l_long < 0)) {
				by_arrayLength++;
			}
			
			if ((p_l_long > 65535l) || (p_l_long < 0)) {
				by_arrayLength++;
			}
			
			if ((p_l_long > 16777215l) || (p_l_long < 0)) {
				by_arrayLength++;
			}
			
			if ((p_l_long > 4294967295l) || (p_l_long < 0)) {
				by_arrayLength++;
			}
			
			if ((p_l_long > 1099511627775l) || (p_l_long < 0)) {
				by_arrayLength++;
			}
			
			if ((p_l_long > 281474976710655l) || (p_l_long < 0)) {
				by_arrayLength++;
			}
			
			if ((p_l_long > 72057594037927935l) || (p_l_long < 0)) {
				by_arrayLength++;
			}
			
			a_return = new byte[by_arrayLength];
			
			for (int i = by_arrayLength; i > 0; i--) {
				a_return[by_arrayLength - i] |= (0x00000000000000FF & (p_l_long >> ((i - 1) * 8)));
			}
		}
		
		return a_return;
	}
	
	/**
	 * converts a long amount to a byte array with at least N bytes
	 * 
	 * @param	p_l_amount	amount long value
	 * @param	p_i_nBytes	amount of bytes which must be returned at least
	 * @return				byte array, length 1..8 - null if invalid long value
	 * @throws	IllegalArgumentException	amount long value must be a positive long value, or amount of bytes parameter is not between 1..8
	 * @throws  IllegalStateException		cannot handle amount of bytes parameter with amount long value parameter
	 * @see		Byte
	 * @see		Long
	 */
	public static byte[] amountToNByteArray(long p_l_amount, int p_i_nBytes) throws IllegalArgumentException, IllegalStateException {
		if (p_l_amount < 0) {
			throw new IllegalArgumentException("Amount parameter must be a positive long value");
		}
		
		if ( (p_i_nBytes < 1) || (p_i_nBytes > 8) ) {
			throw new IllegalArgumentException("N bytes parameter must be between 1..8");
		}
		
		if ( (p_i_nBytes == 1) && (p_l_amount > 255) ) {
			throw new IllegalStateException("Can not handle an amount of '" + p_l_amount + "' greater than '255' with '1' byte");
		}
		
		if ( (p_i_nBytes == 2) && (p_l_amount > 65535) ) {
			throw new IllegalStateException("Can not handle an amount of '" + p_l_amount + "' greater than '65535' with '2' byte");
		}
		
		if ( (p_i_nBytes == 3) && (p_l_amount > 16777215) ) {
			throw new IllegalStateException("Can not handle an amount of '" + p_l_amount + "' greater than '16777215' with '3' byte");
		}
		
		if ( (p_i_nBytes == 4) && (p_l_amount > 4294967295l) ) {
			throw new IllegalStateException("Can not handle an amount of '" + p_l_amount + "' greater than '4294967295' with '4' byte");
		}
		
		if ( (p_i_nBytes == 5) && (p_l_amount > 1099511627775l) ) {
			throw new IllegalStateException("Can not handle an amount of '" + p_l_amount + "' greater than '1099511627775' with '5' byte");
		}
		
		if ( (p_i_nBytes == 6) && (p_l_amount > 281474976710655l) ) {
			throw new IllegalStateException("Can not handle an amount of '" + p_l_amount + "' greater than '281474976710655' with '6' byte");
		}
		
		if ( (p_i_nBytes == 7) && (p_l_amount > 72057594037927935l) ) {
			throw new IllegalStateException("Can not handle an amount of '" + p_l_amount + "' greater than '72057594037927935' with '7' byte");
		}
		
		if ( (p_i_nBytes == 8) && (p_l_amount > 9223372036854775807l) ) {
			throw new IllegalStateException("Can not handle an amount of '" + p_l_amount + "' greater than '9223372036854775807' with '8' byte");
		}
		
		byte[] a_return = new byte[p_i_nBytes];
		byte[] a_longToByteArray = net.forestany.forestj.lib.Helper.longToByteArray(p_l_amount);
		
		for (int i = p_i_nBytes; i > 0; i--) {
			if ((a_longToByteArray.length - i) < 0) {
				a_return[p_i_nBytes - i] = (byte)0;
			} else {
				a_return[p_i_nBytes - i] = a_longToByteArray[a_longToByteArray.length - i];
			}
		}
		
		return a_return;
	}
	
	/**
	 * converts a byte array to an string, showing all bytes as binary data
	 * 
	 * @param	p_a_bytes	byte array parameter
	 * @return				String value
	 * @see		Byte
	 * @see		String
	 */
	public static String printByteArray(byte[] p_a_bytes) {
		return printByteArray(p_a_bytes, true);
	}
	
	/**
	 * converts a byte array to an string, showing all bytes as binary data
	 * 
	 * @param	p_a_bytes		byte array parameter
	 * @param	p_b_fourBytes	true - showing at least four bytes, false - showing bytes which are significant (!= 0)
	 * @return					String value
	 * @see		Byte
	 * @see		String
	 */
	public static String printByteArray(byte[] p_a_bytes, boolean p_b_fourBytes) {
		String s_return = "";
		
		if (p_a_bytes != null) {
			if ( (p_a_bytes.length < 4) && (p_b_fourBytes) ) {
				for (int i = p_a_bytes.length; i < 4; i++) {
					s_return += "00000000 ";
				}
			}
			
			for (byte by_byte : p_a_bytes) {
			   s_return += String.format("%8s", Integer.toBinaryString(by_byte & 0xFF)).replace(' ', '0') + " ";
			}
			
			if (s_return.length() > 0) {
				s_return = s_return.substring(0, s_return.length() - 1);
			}
		}
		
		return s_return;
	}

	/**
	 * prints a generic list's elements surrounded by '[' + ']' and separated by ',' 
	 * 
	 * @param	p_a_list		generic list
	 * @return					String value
	 * @see		java.util.List
	 * @see		String
	 */
	public static String printArrayList(java.util.List<?> p_a_list) {
		String s_return = "";
		
		for (Object o_element : p_a_list) {
			s_return += o_element.toString() + ", "; 
		}
		
		s_return = s_return.substring(0, s_return.length() - 2);
		
		return "[" + s_return + "]";
	}

	/**
	 * format a long value which represents high number of bytes to a string without binary prefix (KB, MB, GB, ...) 
	 * 
	 * @param	p_l_bytes		long value
	 * @return					String value
	 * @see		Long
	 * @see		String
	 */
	public static String formatBytes(long p_l_bytes) {
		return formatBytes(p_l_bytes, false);
	}
	
	/**
	 * format a long value which represents high number of bytes to a string without binary prefix (KB, MB, GB, ...) 
	 * 
	 * @param	p_l_bytes			long value
	 * @param	p_b_binaryPrefix	true - binary prefix (KiB, MiB, GiB, ...), false - no binary prefix (KB, BM, GB, ...)
	 * @return						String value
	 * @see		Long
	 * @see		String
	 */
	public static String formatBytes(long p_l_bytes, boolean p_b_binaryPrefix) {
		boolean b_negative = false;
		
		if (p_l_bytes < 0) {
			p_l_bytes *= -1;
			b_negative = true;
		}
		
		if (p_b_binaryPrefix) {
			java.util.List<String> a_units = java.util.Arrays.asList("B", "KiB", "MiB", "GiB", "TiB", "PiB");
			
			if (p_l_bytes == 0) {
				return "0 " + a_units.get(0);
			}
			
			double d_bytes = Long.valueOf(p_l_bytes).doubleValue();
			double d_foo = Math.log(p_l_bytes) / Math.log(1024);
			d_foo = Math.floor(d_foo);
			int i = Double.valueOf(d_foo).intValue();
			d_foo = Math.pow(1024, d_foo);
			d_foo = d_bytes / d_foo;
			
			java.text.DecimalFormat o_decimalFormat = new java.text.DecimalFormat("###0.##");
			
			if (i >= a_units.size()) {
				return (((b_negative) ? "-" : "") + o_decimalFormat.format(d_foo));
			} else {
				return (((b_negative) ? "-" : "") + o_decimalFormat.format(d_foo) + " " + a_units.get(i) );
			}
		} else {
			java.util.List<String> a_units = java.util.Arrays.asList("B", "KB", "MB", "GB", "TB", "PB");
			
			if (p_l_bytes == 0) {
				return "0 " + a_units.get(0);
			}
			
			double d_bytes = Long.valueOf(p_l_bytes).doubleValue();
			double d_foo = Math.log(p_l_bytes) / Math.log(1000);
			d_foo = Math.floor(d_foo);
			int i = Double.valueOf(d_foo).intValue();
			d_foo = Math.pow(1000, d_foo);
			d_foo = d_bytes / d_foo;
			
			java.text.DecimalFormat o_decimalFormat = new java.text.DecimalFormat("###0.##");
			
			if (i >= a_units.size()) {
				return (((b_negative) ? "-" : "") + o_decimalFormat.format(d_foo));
			} else {
				return (((b_negative) ? "-" : "") + o_decimalFormat.format(d_foo) + " " + a_units.get(i) );
			}
		}
	}
	
	/**
	 * Add all bytes from a static byte array to a dynamic byte list
	 * 
	 * @param p_a_bytes						static byte array
	 * @param p_a_dynamicByteList			dynamic byte list
	 * @throws NullPointerException			static byte array or dynamic byte list parameters is null
	 * @throws IllegalArgumentException		amount of static bytes is greater than amount of determined fixed bytes
	 */
	public static void addStaticByteArrayToDynamicByteList(byte[] p_a_bytes, java.util.List<Byte> p_a_dynamicByteList) throws NullPointerException, IllegalArgumentException {
		Helper.addStaticByteArrayToDynamicByteList(p_a_bytes, p_a_dynamicByteList, 0);
	}
	
	/**
	 * Add all bytes from a static byte array to a dynamic byte list
	 * 
	 * @param p_a_bytes						static byte array
	 * @param p_a_dynamicByteList			dynamic byte list
	 * @param p_i_fixedAmountOfBytes		determine fixed amount of bytes which should be add to dynamic byte list
	 * @throws NullPointerException			static byte array or dynamic byte list parameters is null
	 * @throws IllegalArgumentException		amount of static bytes is greater than amount of determined fixed bytes
	 */
	public static void addStaticByteArrayToDynamicByteList(byte[] p_a_bytes, java.util.List<Byte> p_a_dynamicByteList, int p_i_fixedAmountOfBytes) throws NullPointerException, IllegalArgumentException {
		if ( (p_a_bytes == null) || (p_a_bytes.length < 1) ) {
			throw new NullPointerException("Static byte array parameter is null or has no elements");
		}
		
		if (p_a_dynamicByteList == null) {
			throw new NullPointerException("Dynamic byte list parameter is null");
		}
		
		if (p_i_fixedAmountOfBytes > 0) {
			if (p_a_bytes.length > p_i_fixedAmountOfBytes) {
				throw new IllegalArgumentException("Amount of static bytes '" + p_a_bytes.length + "' is greater than amount of determined fixed bytes '" + p_i_fixedAmountOfBytes + "'");
			}
			
			for (int i = 0; i < (p_i_fixedAmountOfBytes - p_a_bytes.length); i++) {
				p_a_dynamicByteList.add((byte)0);
			}
		}
		
		for (byte by : p_a_bytes) {
			p_a_dynamicByteList.add(by);
		}
	}
	
	/**
	 * hash byte array with hash-algorithm
	 * 
	 * @param	p_s_algorithm								hash-algorithm: 'SHA-256', 'SHA-384', 'SHA-512'
	 * @param	p_a_bytes									byte array
	 * @return												String value
	 * @throws	IllegalArgumentException					if hash-algorithm is not 'SHA-256', 'SHA-384' or 'SHA-512'
	 * @throws  java.security.NoSuchAlgorithmException 		wrong algorithm to hash byte array
	 * @see		Byte
	 * @see		String
	 */
	public static String hashByteArray(String p_s_algorithm, byte[] p_a_bytes) throws IllegalArgumentException, java.security.NoSuchAlgorithmException {
		if (!java.util.Arrays.asList("SHA-256", "SHA-384", "SHA-512").contains(p_s_algorithm)) {
			throw new IllegalArgumentException("Invalid algorithm '" + p_s_algorithm + "', please use a valid algorithm['" + String.join("', '", java.util.Arrays.asList("SHA-256", "SHA-384", "SHA-512")) + "']");
		}
		
		java.security.MessageDigest o_messageDigest = java.security.MessageDigest.getInstance(p_s_algorithm);
		return Helper.bytesToHexString(o_messageDigest.digest(p_a_bytes), false);
	}
	
	/**
	 * convert byte array to hex string
	 * 
	 * @param	p_a_hashbytes				byte array
	 * @param 	p_b_printEachByteAsHex		true - each byte as hex value, false - just as byte value
	 * @return								String value
	 * @see		Byte
	 * @see		String
	 */
	public static String bytesToHexString(byte[] p_a_hashbytes, boolean p_b_printEachByteAsHex) {
		try (java.util.Formatter o_formatter = new java.util.Formatter()) {
			for (byte by_hashbyte : p_a_hashbytes) {
				if (p_b_printEachByteAsHex) {
					o_formatter.format("0x%02x ", by_hashbyte);
				} else {
					o_formatter.format("%02x", by_hashbyte);
				}
		    }
			
		    return o_formatter.toString().trim();
		}
	}

	/**
	 * converts a hex string in format '0x7A 0x5', '0x7A0x5', '0X7A 0x5', '0X7A0X5' or '7A5' to a byte array
	 * @param p_s_string					hex string parameter
	 * @return								converted byte array
	 * @throws IllegalArgumentException		hex string parameter has not an even length of values
	 * @throws IllegalStateException		invalid hex string found and could not parse to a byte value
	 */
	public static byte[] hexStringToBytes(String p_s_string) throws IllegalArgumentException, IllegalStateException {
        /* replace all whitepaces and writing of hex values to get the single values only */
        p_s_string = p_s_string.replace(" ", "").replace("0x", "").replace("0X", "");

        /* check if our hex string has an even length and not '0x7A 0x5' or '7A5' */
        if ((p_s_string.length() % 2) != 0) {
            throw new IllegalArgumentException("Hex string has not an even length of values: " + p_s_string.length());
        }

        /* prepare our return byte array */
        byte[] a_return = new byte[p_s_string.length() / 2];
        /* index for our return byte array in upcoming loop */
        int j = 0;

        /* iterate hex string */
        for (int i = 0; i < p_s_string.length(); i++) {
            try {
                /* parse hex string to byte value, reading two characters, incrementing i while that */
                byte by_foo = (byte)((Character.digit(p_s_string.charAt(i), 16) << 4) + Character.digit(p_s_string.charAt(++i), 16));
                /* add byte to our return byte array */
                a_return[j++] = by_foo;
            } catch (Exception o_exc) {
                /* invalid hex string found */
                throw new IllegalStateException("Invalid hex string found '" + p_s_string.charAt(i - 1) + p_s_string.charAt(i) + "' - " + o_exc.getMessage());
            }
        }
        
        /* return byte array */
        return a_return;
    }
	
	/**
	 * concatenate all generic list elements within a string, separated by delimiter char
	 * 
	 * @param 	<T>				type of list element
	 * @param	p_a_list		generic list
	 * @param	p_c_delimiter	delimiter
	 * @return					integer index of search object, -1 if object has not been found
	 * @see		java.util.List
	 * @see		Object
	 */
	public static <T> String joinList(java.util.List<T> p_a_list, char p_c_delimiter) {
		String s_foo = "";
		
		/* check if list is not null and has at least one element */
		if ( (p_a_list == null) || (p_a_list.size() < 1) ) {
			return s_foo;
		}
		
		/* concatenate all list elements with delimiter between */
		for (T o_foo : p_a_list) {
			s_foo += o_foo.toString() + p_c_delimiter;
		}
		
		/* check if concatenated string ends with delimiter */
		if  (s_foo.endsWith("" + p_c_delimiter)) {
			/* delete last delimiter */
			s_foo = s_foo.substring(0, s_foo.length() - 1);
		}
		
		/* return concatenated string */
		return s_foo;
	}
	
	/**
	 * get index of object in a generic list, not duplicate safe
	 * 
	 * @param 	<T>				type of list element
	 * @param	p_a_list		generic list
	 * @param	p_o_search		object to be searched
	 * @return					integer index of search object, -1 if object has not been found
	 * @see		java.util.List
	 * @see		Object
	 */
	public static <T> int getIndexOfObjectInList(java.util.List<T> p_a_list, T p_o_search) {
		int i_index = -1;
		boolean b_found = false;
		
		if (p_a_list != null) {
			for (T o_foo : p_a_list) {
				i_index++;
				
				if (o_foo.equals(p_o_search)) {
					b_found = true;
					break;
				}
			}
			
			if (!b_found) {
				i_index = -1;
			}
		}
		
		return i_index;
	}
	
	/**
	 * check if an index is valid for a generic list
	 * 
	 * @param 	<T>								type of list element
	 * @param	p_a_list						generic list
	 * @param	p_i_index						integer index
	 * @return									true - parameter index is valid, false - parameter index is not valid
	 * @throws	IllegalArgumentException		list parameter is null
	 * @see		java.util.List
	 * @see		Integer
	 */
	public static <T> boolean isIndexValid(java.util.List<T> p_a_list, int p_i_index) throws IllegalArgumentException {
		if (p_a_list == null) {
			throw new IllegalArgumentException("List parameter is null.");
		}
		
		if ( (p_i_index > -1) && (p_i_index < p_a_list.size()) ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * format a duration object value to a duration string (-)HH:mm:ss
	 * 
	 * @param	p_o_duration						duration value
	 * @return										duration String value
	 * @throws	IllegalArgumentException			duration parameter is null
	 * @see		java.time.Duration
	 * @see		String
	 */
	public static String formatDuration(java.time.Duration p_o_duration) throws IllegalArgumentException {
		if (p_o_duration == null) {
			throw new IllegalArgumentException("Duration parameter is null.");
		}
		
	    long l_seconds = p_o_duration.getSeconds();
	    long l_absSeconds = Math.abs(l_seconds);
	    
	    String s_positive = String.format(
	        "%02d:%02d:%02d",
	        l_absSeconds / 3600 / 60,
	        (l_absSeconds / 3600 % 60),
	        (l_absSeconds % 3600 / 60));
	    
	    return ( (l_seconds < 0) ? ("-" + s_positive) : s_positive );
	}

	/**
	 * generates a random string using ALPHANUMERIC_CHARACTERS
	 * 
	 * @param p_i_length			length of the generated random string
	 * @return String
	 * @see java.security.SecureRandom
	 */
	public static String generateRandomString(int p_i_length) {
		return generateRandomString(p_i_length, ALPHANUMERIC_CHARACTERS);
	}
	
	/**
	 * generates a random string
	 * you can use Helper constants like ALPHANUMERIC_CHARACTERS, UPPERCASE_CHARACTERS, LOWERCASE_CHARACTERS or DIGITS_CHARACTERS to specify which characters should be used
	 * 
	 * @param p_i_length			length of the generated random string
	 * @param p_s_validCharacters	a string of all valid characters which should be used within random generation
	 * @return String
	 * @see java.security.SecureRandom
	 */
	public static String generateRandomString(int p_i_length, String p_s_validCharacters) {
    	/* create a buffer for secure generate characters */
		char[] a_buffer = new char[p_i_length];
		/* create an array of valid characters for secure generation */
    	char[] a_validCharacters = p_s_validCharacters.toCharArray();
    	
    	/* for each character which should be generate to length p_i_length */
        for (int i = 0; i < a_buffer.length; i++) {
        	/* generate a new character out of our valid character array */
            a_buffer[i] = a_validCharacters[net.forestany.forestj.lib.Global.get().SecureRandom.nextInt(a_validCharacters.length)];
        }
        
        /* return generate character array buffer as string */
        return new String(a_buffer);
    }
	
	/**
	 * generates a random uuid string with 32 hexadecimal characters and 4 hyphens '-'
	 * @return String
	 */
	public static String generateUUID() {
		String s_foo = generateRandomString(32, DIGITS_CHARACTERS + LOWERCASE_CHARACTERS.substring(0, 6));
		return s_foo.substring(0, 8) + "-" +  s_foo.substring(8, 12) + "-" + s_foo.substring(12, 16) + "-" + s_foo.substring(16, 20) + "-" + s_foo.substring(20);
	}

	/**
	 * disguise a substring within a string, e.g. 'ftps://user:password@example.com:21' to 'ftps://+++++++++++++@example.com:21'
	 * but only the occurrence of first index of start value and last index of end value
	 * 
	 * @param p_s_value						string where a part of will be disguised
	 * @param p_s_start						recognisable string value where disguise should start after it
	 * @param p_s_end						recognisable string value where disguise should end
	 * @param p_c_disguiseCharacter			disguise character which will replace normal characters in found substring
	 * @return								string with disguised substring
	 * @throws IllegalArgumentException		string parameters are null or empty
	 */
	public static String disguiseSubstring(String p_s_value, String p_s_start, String p_s_end, char p_c_disguiseCharacter) throws IllegalArgumentException {
		String s_return = "";
		
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			throw new IllegalArgumentException("String value where substring should be disguised is empty or null");
		}
		
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_start)) {
			throw new IllegalArgumentException("String value where start of substring is recognized is empty or null");
		}
		
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_end)) {
			throw new IllegalArgumentException("String value where end of substring is recognized is empty or null");
		}
		
		/* get index where start of substring is recognized */
		int i_startDisguise = p_s_value.indexOf(p_s_start) + p_s_start.length();
		/* get index where end of substring is recognized */
		int i_endDisguise = p_s_value.lastIndexOf(p_s_end);
		
		/* iterate each character in string parameter */
		for (int i = 0; i < p_s_value.length(); i++) {
			if ( (i < i_startDisguise) || (i >= i_endDisguise) ) { /* if we are not within substring, just assume normal characters of string */
				s_return += p_s_value.charAt(i);
			} else { /* if we are within substring, replace normal characters of string with disguise character */
				s_return += p_c_disguiseCharacter;
			}
		}
		
		/* return string with disguised substring */
		return s_return;
	}

	/**
	 * checks if a given string matches an ipv4 address
	 * 
	 * @param	p_s_string	String parameter variable
	 * @return				true - String matches IPv4, false - String not matches IPv4
	 * @see		String
	 */
	public static boolean isIpv4Address(String p_s_string) {
		return matchesRegex(p_s_string, "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
	}
	
	/**
	 * checks if a given string matches an ipv4 address with suffix
	 * 
	 * @param	p_s_string	String parameter variable
	 * @return				true - String matches IPv4 with suffix, false - String not matches IPv4 with suffix
	 * @see		String
	 */
	public static boolean isIpv4AddressWithSuffix(String p_s_string) {
		return matchesRegex(p_s_string, "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)/(?:[0-9]|1[0-9]|2[0-9]|3[0-2]?)$");
	}
		
	/**
	 * convert an ipv4 address to integer value
	 * 
	 * @param p_s_ipv4						String parameter variable with ipv4 address as value
	 * @return								converted integer value
	 * @throws IllegalArgumentException		parameter value is null or empty or invalid ipv4 address value
	 */
	public static int ipv4ToInt(String p_s_ipv4) throws IllegalArgumentException {
		if (isStringEmpty(p_s_ipv4)) {
			throw new IllegalArgumentException("Parameter value for IPv4 address to be checked is empty or null");
		}
		
		if (!isIpv4Address(p_s_ipv4)) {
			throw new IllegalArgumentException("IPv4 parameter value '" + p_s_ipv4 + "' is not a valid IPv4 address");
		}
		
		String[] a_ipOctets = p_s_ipv4.split("\\.");
		
		return (( Integer.parseInt(a_ipOctets[0]) << 24 ) & 0xFF000000) 
			 | (( Integer.parseInt(a_ipOctets[1]) << 16 ) & 0x00FF0000) 
			 | (( Integer.parseInt(a_ipOctets[2]) << 8  ) & 0x0000FF00) 
			 | (( Integer.parseInt(a_ipOctets[3]) << 0  ) & 0x000000FF);
	}
	
	/**
	 * get lowest and highest ipv4 address(as integers) from subnet(ipv4 address with suffix) parameter, excluding net- and broadcast-address
	 * 
	 * @param p_s_subnet					subnet as ipv4 address with suffix
	 * @return								two integer values as array, first element is lowest ip, seconds element is highest ip
	 * @throws IllegalArgumentException		parameter value is null or empty, invalid ipv4 address value or invalid suffix value
	 */
	public static int[] getRangeOfSubnet(String p_s_subnet) throws IllegalArgumentException {
		int[] a_return = new int[2];
		
		/* check parameter value */
		if (isStringEmpty(p_s_subnet)) {
			throw new IllegalArgumentException("Parameter value for IPv4 address with CIDR part is empty or null");
		}
		
		/* check parameter notation */
		if (!p_s_subnet.contains("/")) {
			throw new IllegalArgumentException("Invalid parameter value '" + p_s_subnet + "' for IPv4 address with Suffix part is empty or null");
		}
		
		/* split parameter value into ip and suffix parts */
		String[] a_cidrParts = p_s_subnet.split("/");
		String s_ipv4 = a_cidrParts[0];
		String s_suffix = a_cidrParts[1];
		
		/* check ip part */
		if (!isIpv4Address(s_ipv4)) {
			throw new IllegalArgumentException("IPv4 part '" + s_ipv4 + "' is not a valid IPv4 address");
		}
		
		/*  check suffix value */
		if (!isInteger(s_suffix)) {
			throw new IllegalArgumentException("Suffix part '" + s_suffix + "' is not an integer");
		}
		
		/* check min. and max. range of suffix value */
		if ( (Integer.parseInt(s_suffix) < 0) && (Integer.parseInt(s_suffix) > 32) ) {
			throw new IllegalArgumentException("Suffix part '" + s_suffix + "' must be between '0..32'");
		}
		
		/* convert ip part to int */ 
		int i_ip = ipv4ToInt(s_ipv4);

		/* convert suffix part to netmask integer value */
		int i_mask = (-1) << (32 - Integer.parseInt(s_suffix));

		/* calculate lowest ip value, excluding net address */
		a_return[0] = (i_ip & i_mask) + 1;

		/* calculate highest ip value, excluding broadcast address */
		a_return[1] = a_return[0] + (~i_mask) - 2;
		
		return a_return;
	}
	
	/**
	 * convert ipv4 address from integer value to string value
	 * 
	 * @param p_i_ipv4		Integer parameter variable with ipv4 as value
	 * @return				ipv4 address as string value or null if conversion failed	
	 */
	public static String ipv4IntToString(int p_i_ipv4) {
		byte[] by_ip = new byte[4];
		by_ip[0] = (byte) ((p_i_ipv4 >> 24) & 0xFF);
		by_ip[1] = (byte) ((p_i_ipv4 >> 16) & 0xFF);
		by_ip[2] = (byte) ((p_i_ipv4 >> 8 ) & 0xFF);
		by_ip[3] = (byte) ((p_i_ipv4 >> 0 ) & 0xFF);

		try {
			return java.net.InetAddress.getByAddress(by_ip).getHostAddress();
		} catch (java.net.UnknownHostException o_exc) {
			return null;
		}
	}
	
	/**
	 * check if ipv4 address is within range of a subnet
	 * 
	 * @param p_s_ipv4						ipv4 address which will be checked
	 * @param p_s_subnet					subnet as ipv4 address with suffix
	 * @return								true - ipv4 address is within subnet, false - ipv4 address is not within subnet
	 * @throws IllegalArgumentException		parameter value is null or empty, invalid ipv4 address value or invalid suffix value
	 */
	public static boolean isIpv4WithinRange(String p_s_ipv4, String p_s_subnet) throws IllegalArgumentException {
		boolean b_foo = false;
		
		/* check ipv4 address */
		int i_ip = ipv4ToInt(p_s_ipv4);
		
		/* check if subnet parameter is not empty */
		if (isStringEmpty(p_s_subnet)) {
			throw new IllegalArgumentException("String value for IPv4 address with Suffix part is empty or null");
		}
		
		if (p_s_subnet.contains("/")) {
			/* found suffix part, so we can calculate first and last ipv4 address */
			int[] a_ipRange = getRangeOfSubnet(p_s_subnet);
			b_foo = ( (i_ip >= a_ipRange[0]) && (i_ip <= a_ipRange[1]) );
		} else {
			/* no suffix part, so both ipv4 parameters must match as integers */
			if (i_ip == ipv4ToInt(p_s_subnet)) {
				b_foo = true;
			}
		}
		
		return b_foo;
	}
}
