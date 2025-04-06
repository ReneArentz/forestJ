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
			+ "[HH:mm:ss.SSSSSSSSS]"
			+ "[HH:mm:ss.SSSSSS]"
			+ "[HH:mm:ss.SSS]"
			+ "[HH:mm:ss]"
			+ "[HH:mm]"
			+ "[HHmmssSSSSSSSSS]"
			+ "[HHmmssSSSSSS]"
			+ "[HHmmssSSS]"
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
			+ "[HH:mm:ss.SSSSSSSSS]"
			+ "[HH:mm:ss.SSSSSS]"
			+ "[HH:mm:ss.SSS]"
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
			+ "[HH:mm:ss.SSSSSSSSS]"
			+ "[HH:mm:ss.SSSSSS]"
			+ "[HH:mm:ss.SSS]"
			+ "[HH:mm:ss]"
			+ "[HH:mm]"
			+ "[HHmmssSSSSSSSSS]"
			+ "[HHmmssSSSSSS]"
			+ "[HHmmssSSS]"
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
			+ "[HH:mm:ss.SSSSSSSSS]"
			+ "[HH:mm:ss.SSSSSS]"
			+ "[HH:mm:ss.SSS]"
			+ "[HH:mm:ss]"
			+ "[HH:mm]"
			+ "[HHmmssSSSSSSSSS]"
			+ "[HHmmssSSSSSS]"
			+ "[HHmmssSSS]"
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
	 * checks if a given string matches as an ipv4 multicast address 224.0.0.0 - 239.255.255.255
	 * 
	 * @param	p_s_string	String parameter variable
	 * @return				true - String matches IPv4 Multicast, false - String not matches IPv4 Muticast
	 */
	public static boolean isIpv4MulticastAddress(String p_s_string) {
        if (isIpv4Address(p_s_string)) {
            String s_firstOctet = p_s_string.split("\\.")[0];
            int i_firstOctet = 0;

            try {
                i_firstOctet = Integer.parseInt(s_firstOctet);
            } catch (Exception o_exc) {
                return false;
            }

            if ((i_firstOctet >= 224) && (i_firstOctet <= 239)) {
                return true;
            }
        }

        return false;
    }
	
	/**
	 * checks if a given string matches an ipv6 address
	 * 
	 * @param	p_s_string	String parameter variable
	 * @return				true - String matches IPv6, false - String not matches IPv6
	 * @see		String
	 */
	public static boolean isIpv6Address(String p_s_string) {
		return matchesRegex(p_s_string, "^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$");
	}
	
	/**
	 * checks if a given string matches as an ipv6 multicast address
	 * 
	 * @param	p_s_string	String parameter variable
	 * @return				true - String matches IPv6 Multicast, false - String not matches IPv6 Muticast
	 */
	public static boolean isIpv6MulticastAddress(String p_s_string) {
        if (isIpv6Address(p_s_string)) {
            try {
            	java.net.InetAddress o_foo = java.net.InetAddress.getByName(p_s_string);
            	return o_foo.isMulticastAddress();
            } catch (Exception o_exc) {
                return false;
            }
        }

        return false;
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
	
	/**
     * get all ipv4 addresses of all network interfaces of current machine
     * 
     * @return 									KeyValuePair list where key holds the name of the network interface and the value the ipv4 address
     * @throws java.net.SocketException			if an I/O error occurs, or if the platform does not have at least one configured network interface
     */
    public static java.util.List<java.util.AbstractMap.SimpleEntry<String, String>> GetNetworkInterfacesIpv4() throws java.net.SocketException
    {
    	return GetNetworkInterfaces(false);
    }
    
    /**
     * get all ipv6 addresses of all network interfaces of current machine
     * 
     * @return 									KeyValuePair list where key holds the name of the network interface and the value the ipv6 address
     * @throws java.net.SocketException			if an I/O error occurs, or if the platform does not have at least one configured network interface
     */
    public static java.util.List<java.util.AbstractMap.SimpleEntry<String, String>> GetNetworkInterfacesIpv6() throws java.net.SocketException
    {
    	return GetNetworkInterfaces(true);
    }
    
    /**
     * get all ip addresses of all network interfaces of current machine
     * 
     * @param p_b_trueIPv6falseIPv4				true - search for ipv6 addresses, false - search for ipv4 addresses
     * @return 									KeyValuePair list where key holds the name of the network interface and the value the ip address
     * @thorws java.net.SocketException			if an I/O error occurs, or if the platform does not have at least one configured network interface
     */
    private static java.util.List<java.util.AbstractMap.SimpleEntry<String, String>> GetNetworkInterfaces(boolean p_b_trueIPv6falseIPv4) throws java.net.SocketException
    {
    	java.util.List<java.util.AbstractMap.SimpleEntry<String, String>> a_networkInterfaces = new java.util.ArrayList<java.util.AbstractMap.SimpleEntry<String, String>>();
    	
    	/* iterate all network interfaces */
    	for (java.net.NetworkInterface o_networkInterface : java.util.Collections.list(java.net.NetworkInterface.getNetworkInterfaces())) {
    		java.util.Enumeration<java.net.InetAddress> inetAddresses = o_networkInterface.getInetAddresses();
    		java.util.List<java.net.InetAddress> a_inetAddressesList = java.util.Collections.list(inetAddresses);
    		
    		/* network interface must be active and have configured addresses */
    		if ((o_networkInterface.isUp()) && (a_inetAddressesList.size() > 0)) {
    			String s_name = o_networkInterface.getName();
    			String s_ips = "";
    			
    			/* iterate unicast ipaddress information */
    			for (java.net.InetAddress o_inetAddress : a_inetAddressesList) {
    				/* address must be ipv4 or ipv6 */
    				if (
    					((p_b_trueIPv6falseIPv4) && (o_inetAddress instanceof java.net.Inet6Address)) || 
    					((!p_b_trueIPv6falseIPv4) && (o_inetAddress instanceof java.net.Inet4Address))
    				) {
    					String s_foo = o_inetAddress.getHostAddress();
    					
    					if (s_foo.contains("%")) {
    						s_foo = s_foo.substring(0, s_foo.indexOf("%"));
    					}
    					
    					s_ips += s_foo + "|";
    				}
    			}
    		
              /* delete last pipe */
              if (s_ips.endsWith("|")) {
                  s_ips = s_ips.substring(0, s_ips.length() - 1);
              }

              /* check if we have multiple ip addresses to one network interface */
              if (s_ips.contains("|")) {
                  int i = 1;

                  for (String s_ip : s_ips.split("\\|")) {
                      /* give network interface an additional number for multiple ip address */
                	  a_networkInterfaces.add(new java.util.AbstractMap.SimpleEntry<String, String>(s_name + " #" + i++, s_ip));
                  }
              } else {
                  /* add network interface name and its ip address */
                  a_networkInterfaces.add(new java.util.AbstractMap.SimpleEntry<String, String>(s_name, s_ips));
              }
    		}
    	}

        return a_networkInterfaces;
    }

    /**
     * Comparing two object and all its fields if types are supported
	 * unsupported field types will be skipped and are not part of the comparison
	 * 
	 * supported field types:
	 * byte, short, int, long, float, double, char, boolean, string,
	 * java.lang.String, java.lang.Short, java.lang.Byte, java.lang.Integer,
	 * java.lang.Long, java.lang.Float, java.lang.Double, java.lang.Boolean, 
	 * java.lang.Character, java.math.BigDecimal, java.util.Date,
	 * java.time.LocalDateTime, java.time.LocalDate, java.time.LocalTime
	 * 
	 * @param p_o_objectOne										first object
	 * @param p_o_objectTwo										second object
	 * @param p_b_usePropertyMethods							true - fields will be accessed by property methods (getXXX, setXXX), false - fields will be accesed directly(must be public)
	 * @return													true - both objects are equal, false - both objects are not equal
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws NoSuchMethodException							could not retrieve method by method name
	 * @throws IllegalAccessException							could not invoke method, access violation
	 * @throws java.lang.reflect.InvocationTargetException 		could not invoke method from object
	 */
	public static boolean objectsEqualUsingReflections(Object p_o_objectOne, Object p_o_objectTwo, boolean p_b_usePropertyMethods) throws NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		return Helper.objectsEqualUsingReflections(p_o_objectOne, p_o_objectTwo, p_b_usePropertyMethods, false);
	}
	
	/**
	 * Comparing two object and all its fields if types are supported, only public fields or property methods are supported
	 * unsupported field types will be skipped and are not part of the comparison
	 * 
	 * supported field types:
	 * byte, short, int, long, float, double, char, boolean, string,
	 * java.lang.String, java.lang.Short, java.lang.Byte, java.lang.Integer,
	 * java.lang.Long, java.lang.Float, java.lang.Double, java.lang.Boolean, 
	 * java.lang.Character, java.math.BigDecimal, java.util.Date,
	 * java.time.LocalDateTime, java.time.LocalDate, java.time.LocalTime
	 * 
	 * @param p_o_objectOne										first object
	 * @param p_o_objectTwo										second object
	 * @param p_b_usePropertyMethods							true - fields will be accessed by property methods (getXXX, setXXX), false - fields will be accessed directly(must be public)
	 * @param p_b_deepComparison								true - accept unsupported field types and compare each accessible filed, false - skip unsupported field types
	 * @return													true - both objects are equal, false - both objects are not equal
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws NoSuchMethodException							could not retrieve method by method name
	 * @throws IllegalAccessException							could not invoke method, access violation
	 * @throws java.lang.reflect.InvocationTargetException 		could not invoke method from object
	 */
	public static boolean objectsEqualUsingReflections(Object p_o_objectOne, Object p_o_objectTwo, boolean p_b_usePropertyMethods, boolean p_b_deepComparison) throws NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		if ( (p_o_objectOne == null) && (p_o_objectTwo == null) ) {
			return true;
		} else if ( (p_o_objectOne == null) ^ (p_o_objectTwo == null) ) {
			return false;
		}
		
		net.forestany.forestj.lib.Global.ilogMass("compare two objects '" + p_o_objectOne.getClass().getTypeName() + "' and '" + p_o_objectTwo.getClass().getTypeName() + "' with '" + ((p_b_usePropertyMethods) ? "using property methods" : "direct access to fields") + "'" );
		
		/* both object parameter must be of same type */
		if (!p_o_objectOne.getClass().getTypeName().contentEquals(p_o_objectTwo.getClass().getTypeName())) {
			net.forestany.forestj.lib.Global.ilogWarning("both object parameter have not the same type; '" + p_o_objectOne.getClass().getTypeName() + "' != '" + p_o_objectTwo.getClass().getTypeName() + "'");
			return false;
		}
		
		/* list of allowed types for generic comparison */
		java.util.List<String> a_allowedTypes = java.util.Arrays.asList("byte", "short", "int", "long", "float", "double", "char", "boolean", "string", "java.lang.String", "java.lang.Short", "java.lang.Byte", "java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double", "java.lang.Boolean", "java.lang.Character", "java.math.BigDecimal", "java.util.Date", "java.time.LocalDateTime", "java.time.LocalDate", "java.time.LocalTime");
		
		/* if both objects are allowed types, we do not need deep comparison */
		if (a_allowedTypes.contains(p_o_objectOne.getClass().getTypeName())) {
			p_b_deepComparison = false;
		}
		
		if (p_o_objectOne instanceof java.util.List) {
			/* cast parameter objects to array list */
			@SuppressWarnings("unchecked")
			java.util.List<Object> o_fooOne = (java.util.List<Object>)p_o_objectOne;
			@SuppressWarnings("unchecked")
			java.util.List<Object> o_fooTwo = (java.util.List<Object>)p_o_objectTwo;
			
			/* both object parameter as lists must have the same size to be equal */
			if (o_fooOne.size() != o_fooTwo.size()) {
				net.forestany.forestj.lib.Global.ilogWarning("both object parameter as lists have not the same size; '" + o_fooOne.size() + "' != '" + o_fooTwo.size() + "'");
				return false;
			}
			
			/* only execute if we have more than one array element */
			if (o_fooOne.size() > 0) {
				/* iterate objects in list and encode data to csv recursively */
				for (int i = 0; i < o_fooOne.size(); i++) {
					if (!net.forestany.forestj.lib.Helper.objectsEqualUsingReflections(o_fooOne.get(i), o_fooTwo.get(i), p_b_usePropertyMethods)) {
						net.forestany.forestj.lib.Global.ilogWarning("both objects in lists are not equal; '" + o_fooOne.get(i).toString() + "' != '" + o_fooTwo.get(i).toString() + "'");
						return false;
					}
				}
			}
		} else {
			/* iterate all fields of parameter object */
			for (java.lang.reflect.Field o_field : p_o_objectOne.getClass().getDeclaredFields()) {
				net.forestany.forestj.lib.Global.ilogFiner(o_field.getName() + " - " + o_field.getType().getTypeName() + " - [" + java.lang.reflect.Modifier.toString(o_field.getModifiers()) + "]");
				
				/* skip fields that starts with 'this$' */
				if (o_field.getName().startsWith("this$")) {
					continue;
				}
				
				/* if field of parameter object is of type list */
				if (o_field.getType().isAssignableFrom(java.util.List.class)) {
					/* retrieve field type of list */
					java.lang.reflect.ParameterizedType stringListType = (java.lang.reflect.ParameterizedType)p_o_objectOne.getClass().getDeclaredField(o_field.getName()).getGenericType();
			        Class<?> o_type = (Class<?>)stringListType.getActualTypeArguments()[0];

			        /* if type of field list is not contained in the list of allowed types, skip this field */
			        if (!a_allowedTypes.contains(o_type.getTypeName())) {
			        	if (!p_b_deepComparison) {
			        		net.forestany.forestj.lib.Global.ilogMass("object list generic type '" + o_type.getTypeName() + "' is not supported; object list field will be skipped");
							continue;
			        	}
			        }
				} else if (o_field.getType().getTypeName().endsWith("[]")) { /* if field array type of parameter object is not contained in the list of allowed types, skip this field */
					if ((!p_b_deepComparison) && (!a_allowedTypes.contains(o_field.getType().getTypeName().substring(0, o_field.getType().getTypeName().length() - 2)))) {
		        		net.forestany.forestj.lib.Global.ilogMass("object array field type '" + o_field.getType().getTypeName() + "' is not supported; field will be skipped");
						continue;
		        	}
				} else if (!a_allowedTypes.contains(o_field.getType().getTypeName())) { /* if field type of parameter object is not contained in the list of allowed types, skip this field */
					if (!p_b_deepComparison) {
		        		net.forestany.forestj.lib.Global.ilogMass("object field type '" + o_field.getType().getTypeName() + "' is not supported; field will be skipped");
						continue;
		        	}
				}
				
				/* help variable for accessing object field of both parameter objects */
				Object o_objectOne = null;
				Object o_objectTwo = null;
				
				/* check if we use property methods with invoke to get object data values */
				if (p_b_usePropertyMethods) {
					java.lang.reflect.Method o_method = null;
					
					/* store get-property-method of java type object */
					try {
						o_method = p_o_objectOne.getClass().getDeclaredMethod("get" + o_field.getName());
					} catch (NoSuchMethodException | SecurityException o_exc) {
						/* property method does not exist, so we can skip this field */
						net.forestany.forestj.lib.Global.ilogMass("Class instance method[" + "get" + o_field.getName() + "] does not exist for class instance(" + p_o_objectOne.getClass().getTypeName() + ")");
						continue;
					}
					
					/* invoke get-property-method to get object data of current element */
					o_objectOne = o_method.invoke(p_o_objectOne);
					o_objectTwo = o_method.invoke(p_o_objectTwo);
				} else {
					/* if field is not public, skip this field */
					if (!(java.lang.reflect.Modifier.isPublic(o_field.getModifiers()))) {
						net.forestany.forestj.lib.Global.ilogMass("object field is not public; field will be skipped");
						continue;
					}
					
					/* call field directly to get object data values */
					try {
						o_objectOne = p_o_objectOne.getClass().getDeclaredField(o_field.getName()).get(p_o_objectOne);
						o_objectTwo = p_o_objectTwo.getClass().getDeclaredField(o_field.getName()).get(p_o_objectTwo);
					} catch (IllegalAccessException o_exc) {
						throw new IllegalAccessException("Access violation for field[" + o_field.getName() + "], modifiers[" + java.lang.reflect.Modifier.toString(o_field.getModifiers()) + "]: " + o_exc.getMessage());
					}
				}
				
				/* if both object are string but one is null, check of empty strings */
				/* if one object is null and the other is an empty string we accept it as equal - setting both to null */
				if ( ((o_objectOne != null) ^ (o_objectTwo != null)) && ( (o_field.getType().getTypeName().contentEquals("java.lang.String")) || (o_field.getType().getTypeName().toLowerCase().contentEquals("string")) ) ) {
					if ( (o_objectOne != null) && (isStringEmpty(o_objectOne.toString())) ) {
						o_objectOne = null;
					}
					
					if ( (o_objectTwo != null) && (isStringEmpty(o_objectTwo.toString())) ) {
						o_objectTwo = null;
					}
				}
				
				if ( (o_objectOne == null) ^ (o_objectTwo == null) ) { /* if one object is null but not the other, they are not equal */
					net.forestany.forestj.lib.Global.ilogWarning("one object is null but not the other object '" + ( (o_objectOne == null) ? "null" : o_objectOne.toString() + "[" + o_objectOne.getClass().getTypeName() + "]") + "' != '" + ((o_objectTwo == null) ? "null" : o_objectTwo.toString() + "[" + o_objectTwo.getClass().getTypeName() + "]'"));
					return false;
				} else if ( (o_objectOne != null) && (o_objectTwo != null) ) { /* if help variable got access to object field */
					/* if field of parameter object is of type list */
					if (o_field.getType().getTypeName().contains("java.util.List")) {
						/* cast current field of parameter object as list with unknown generic type */
						java.util.List<?> a_objectsOne = (java.util.List<?>)o_objectOne;
						java.util.List<?> a_objectsTwo = (java.util.List<?>)o_objectTwo;
						
						/* both object arrays must have the same size of elements */
						if (a_objectsOne.size() != a_objectsTwo.size()) {
							net.forestany.forestj.lib.Global.ilogWarning("both object parameter lists have not the same size '" + a_objectsOne.size() + "' != '" + a_objectsTwo.size() + "'");
							return false;
						}
						
						/* only execute if we have more than one array element */
						if (a_objectsOne.size() > 0) {
							/* iterate objects in list and encode data to csv recursively */
							for (int i = 0; i < a_objectsOne.size(); i++) {
								net.forestany.forestj.lib.Global.ilogFiner(o_field.getName() + " array element objects equal: " + ( ( (a_objectsOne.get(i) == null) && (a_objectsTwo.get(i) == null) ) || ( ( (a_objectsOne.get(i) != null) && (a_objectsTwo.get(i) != null) ) && (a_objectsOne.get(i).equals(a_objectsTwo.get(i))) ) ) + "\t" + ( (a_objectsOne.get(i) == null) ? "null" : a_objectsOne.get(i).toString() ) + "\t\t" + ( (a_objectsTwo.get(i) == null) ? "null" : a_objectsTwo.get(i).toString() ));
								
								if (p_b_deepComparison) {
					        		/* deep comparison of both objects */
									if (!Helper.objectsEqualUsingReflections(a_objectsOne.get(i), a_objectsTwo.get(i), p_b_usePropertyMethods, p_b_deepComparison)) {
										net.forestany.forestj.lib.Global.ilogWarning("both list elements for field '" + o_field.getName() + "' are not equal '" +  a_objectsOne.get(i) + "' != '" + a_objectsTwo.get(i) + "'");
										return false;
									}
					        	} else {
									/* compare each array element of both object arrays or accept if both elements in array are null */
									if (!( ( (a_objectsOne.get(i) == null) && (a_objectsTwo.get(i) == null) ) || (a_objectsOne.get(i).equals(a_objectsTwo.get(i))) )) {
										net.forestany.forestj.lib.Global.ilogWarning("both object parameter array elements are not equal or not both null '" + ( (a_objectsOne.get(i) == null) ? "null" : a_objectsOne.get(i).toString() ) + "' != '" + ( (a_objectsTwo.get(i) == null) ? "null" : a_objectsTwo.get(i).toString() ) + "'");
										return false;
									}
					        	}
							}
						}
					} else if (o_field.getType().getTypeName().endsWith("[]")) { /* handle usual arrays */
						/* get array type as string */
						String s_arrayType = o_objectOne.getClass().getTypeName().substring(0, o_objectOne.getClass().getTypeName().length() - 2);
						
						/* check if we can handle this type of array, otherwise this and the whole field will be just skipped */
						if (a_allowedTypes.contains(s_arrayType)) {
							s_arrayType = s_arrayType.toLowerCase();
							
							if ( (s_arrayType.contentEquals("boolean")) || (s_arrayType.contentEquals("java.lang.boolean")) ) {
								/* cast current field of parameter object as array */
								boolean[] a_objectsOne = (boolean[])o_objectOne;
								boolean[] a_objectsTwo = (boolean[])o_objectTwo;
								
								/* both arrays must have the same length */
								if (a_objectsOne.length != a_objectsTwo.length) {
									net.forestany.forestj.lib.Global.ilogWarning("both arrays for field '" + o_field.getName() + "' have not the same length '" + a_objectsOne.length + " != " + a_objectsTwo.length + "'");
									return false;
								}
								
								/* check each array element for equality */
								for (int i = 0; i < a_objectsOne.length; i++) {
									if (a_objectsOne[i] != a_objectsTwo[i]) {
										net.forestany.forestj.lib.Global.ilogWarning("both array elements for field '" + o_field.getName() + "' are not equal '" +  a_objectsOne[i] + "' != '" + a_objectsTwo[i] + "'");
										return false;
									}
								}
							} else if ( (s_arrayType.contentEquals("byte")) || (s_arrayType.contentEquals("java.lang.byte")) ) {
								/* cast current field of parameter object as array */
								byte[] a_objectsOne = (byte[])o_objectOne;
								byte[] a_objectsTwo = (byte[])o_objectTwo;
								
								/* both arrays must have the same length */
								if (a_objectsOne.length != a_objectsTwo.length) {
									net.forestany.forestj.lib.Global.ilogWarning("both arrays for field '" + o_field.getName() + "' have not the same length '" + a_objectsOne.length + " != " + a_objectsTwo.length + "'");
									return false;
								}
								
								/* check each array element for equality */
								for (int i = 0; i < a_objectsOne.length; i++) {
									if (a_objectsOne[i] != a_objectsTwo[i]) {
										net.forestany.forestj.lib.Global.ilogWarning("both array elements for field '" + o_field.getName() + "' are not equal '" +  a_objectsOne[i] + "' != '" + a_objectsTwo[i] + "'");
										return false;
									}
								}
							} else if ( (s_arrayType.contentEquals("char")) || (s_arrayType.contentEquals("java.lang.character")) ) {
								/* cast current field of parameter object as array */
								char[] a_objectsOne = (char[])o_objectOne;
								char[] a_objectsTwo = (char[])o_objectTwo;
								
								/* both arrays must have the same length */
								if (a_objectsOne.length != a_objectsTwo.length) {
									net.forestany.forestj.lib.Global.ilogWarning("both arrays for field '" + o_field.getName() + "' have not the same length '" + a_objectsOne.length + " != " + a_objectsTwo.length + "'");
									return false;
								}
								
								/* check each array element for equality */
								for (int i = 0; i < a_objectsOne.length; i++) {
									if (a_objectsOne[i] != a_objectsTwo[i]) {
										net.forestany.forestj.lib.Global.ilogWarning("both array elements for field '" + o_field.getName() + "' are not equal '" +  a_objectsOne[i] + "' != '" + a_objectsTwo[i] + "'");
										return false;
									}
								}
							} else if ( (s_arrayType.contentEquals("float")) || (s_arrayType.contentEquals("java.lang.float")) ) {
								/* cast current field of parameter object as array */
								float[] a_objectsOne = (float[])o_objectOne;
								float[] a_objectsTwo = (float[])o_objectTwo;
								
								/* both arrays must have the same length */
								if (a_objectsOne.length != a_objectsTwo.length) {
									net.forestany.forestj.lib.Global.ilogWarning("both arrays for field '" + o_field.getName() + "' have not the same length '" + a_objectsOne.length + " != " + a_objectsTwo.length + "'");
									return false;
								}
								
								/* check each array element for equality */
								for (int i = 0; i < a_objectsOne.length; i++) {
									if (a_objectsOne[i] != a_objectsTwo[i]) {
										net.forestany.forestj.lib.Global.ilogWarning("both array elements for field '" + o_field.getName() + "' are not equal '" +  a_objectsOne[i] + "' != '" + a_objectsTwo[i] + "'");
										return false;
									}
								}
							} else if ( (s_arrayType.contentEquals("double")) || (s_arrayType.contentEquals("java.lang.double")) ) {
								/* cast current field of parameter object as array */
								double[] a_objectsOne = (double[])o_objectOne;
								double[] a_objectsTwo = (double[])o_objectTwo;
								
								/* both arrays must have the same length */
								if (a_objectsOne.length != a_objectsTwo.length) {
									net.forestany.forestj.lib.Global.ilogWarning("both arrays for field '" + o_field.getName() + "' have not the same length '" + a_objectsOne.length + " != " + a_objectsTwo.length + "'");
									return false;
								}
								
								/* check each array element for equality */
								for (int i = 0; i < a_objectsOne.length; i++) {
									if (a_objectsOne[i] != a_objectsTwo[i]) {
										net.forestany.forestj.lib.Global.ilogWarning("both array elements for field '" + o_field.getName() + "' are not equal '" +  a_objectsOne[i] + "' != '" + a_objectsTwo[i] + "'");
										return false;
									}
								}
							} else if ( (s_arrayType.contentEquals("short")) || (s_arrayType.contentEquals("java.lang.short")) ) {
								/* cast current field of parameter object as array */
								short[] a_objectsOne = (short[])o_objectOne;
								short[] a_objectsTwo = (short[])o_objectTwo;
								
								/* both arrays must have the same length */
								if (a_objectsOne.length != a_objectsTwo.length) {
									net.forestany.forestj.lib.Global.ilogWarning("both arrays for field '" + o_field.getName() + "' have not the same length '" + a_objectsOne.length + " != " + a_objectsTwo.length + "'");
									return false;
								}
								
								/* check each array element for equality */
								for (int i = 0; i < a_objectsOne.length; i++) {
									if (a_objectsOne[i] != a_objectsTwo[i]) {
										net.forestany.forestj.lib.Global.ilogWarning("both array elements for field '" + o_field.getName() + "' are not equal '" +  a_objectsOne[i] + "' != '" + a_objectsTwo[i] + "'");
										return false;
									}
								}
							} else if ( (s_arrayType.contentEquals("int")) || (s_arrayType.contentEquals("java.lang.integer")) ) {
								/* cast current field of parameter object as array */
								int[] a_objectsOne = (int[])o_objectOne;
								int[] a_objectsTwo = (int[])o_objectTwo;
								
								/* both arrays must have the same length */
								if (a_objectsOne.length != a_objectsTwo.length) {
									net.forestany.forestj.lib.Global.ilogWarning("both arrays for field '" + o_field.getName() + "' have not the same length '" + a_objectsOne.length + " != " + a_objectsTwo.length + "'");
									return false;
								}
								
								/* check each array element for equality */
								for (int i = 0; i < a_objectsOne.length; i++) {
									if (a_objectsOne[i] != a_objectsTwo[i]) {
										net.forestany.forestj.lib.Global.ilogWarning("both array elements for field '" + o_field.getName() + "' are not equal '" +  a_objectsOne[i] + "' != '" + a_objectsTwo[i] + "'");
										return false;
									}
								}
							} else if ( (s_arrayType.contentEquals("long")) || (s_arrayType.contentEquals("java.lang.long")) ) {
								/* cast current field of parameter object as array */
								long[] a_objectsOne = (long[])o_objectOne;
								long[] a_objectsTwo = (long[])o_objectTwo;
								
								/* both arrays must have the same length */
								if (a_objectsOne.length != a_objectsTwo.length) {
									net.forestany.forestj.lib.Global.ilogWarning("both arrays for field '" + o_field.getName() + "' have not the same length '" + a_objectsOne.length + " != " + a_objectsTwo.length + "'");
									return false;
								}
								
								/* check each array element for equality */
								for (int i = 0; i < a_objectsOne.length; i++) {
									if (a_objectsOne[i] != a_objectsTwo[i]) {
										net.forestany.forestj.lib.Global.ilogWarning("both array elements for field '" + o_field.getName() + "' are not equal '" +  a_objectsOne[i] + "' != '" + a_objectsTwo[i] + "'");
										return false;
									}
								}
							} else if ( (s_arrayType.contentEquals("string")) || (s_arrayType.contentEquals("java.lang.string")) ) {
								/* cast current field of parameter object as array */
								String[] a_objectsOne = (String[])o_objectOne;
								String[] a_objectsTwo = (String[])o_objectTwo;
								
								/* both arrays must have the same length */
								if (a_objectsOne.length != a_objectsTwo.length) {
									net.forestany.forestj.lib.Global.ilogWarning("both arrays for field '" + o_field.getName() + "' have not the same length '" + a_objectsOne.length + " != " + a_objectsTwo.length + "'");
									return false;
								}
								
								/* check each array element for equality */
								for (int i = 0; i < a_objectsOne.length; i++) {
									/* both array elements are null */
									if ( (a_objectsOne[i] == null) && (a_objectsTwo[i] == null) ) {
										continue;
									}
									
									/* if one array element is null but not the other, they are not equal */
									if ( (a_objectsOne[i] == null) ^ (a_objectsTwo[i] == null) ) {
										net.forestany.forestj.lib.Global.ilogWarning("for field '" + o_field.getName() + "' one array element is null but not the other array element '" + ( (a_objectsOne[i] == null) ? "null" : a_objectsOne[i] + "[" + a_objectsOne[i].getClass().getTypeName() + "]") + "' != '" + ((a_objectsTwo[i] == null) ? "null" : a_objectsTwo[i] + "[" + a_objectsTwo.getClass().getTypeName() + "]") + "'");
										return false;
									}
									
									if (!a_objectsOne[i].contentEquals(a_objectsTwo[i])) {
										net.forestany.forestj.lib.Global.ilogWarning("both array elements for field '" + o_field.getName() + "' are not equal '" +  a_objectsOne[i] + "' != '" + a_objectsTwo[i] + "'");
										return false;
									}
								}
							} else if (s_arrayType.contentEquals("java.util.date")) {
								/* cast current field of parameter object as array */
								java.util.Date[] a_objectsOne = (java.util.Date[])o_objectOne;
								java.util.Date[] a_objectsTwo = (java.util.Date[])o_objectTwo;
								
								/* both arrays must have the same length */
								if (a_objectsOne.length != a_objectsTwo.length) {
									net.forestany.forestj.lib.Global.ilogWarning("both arrays for field '" + o_field.getName() + "' have not the same length '" + a_objectsOne.length + " != " + a_objectsTwo.length + "'");
									return false;
								}
								
								/* check each array element for equality */
								for (int i = 0; i < a_objectsOne.length; i++) {
									/* both array elements are null */
									if ( (a_objectsOne[i] == null) && (a_objectsTwo[i] == null) ) {
										continue;
									}
									
									/* if one array element is null but not the other, they are not equal */
									if ( (a_objectsOne[i] == null) ^ (a_objectsTwo[i] == null) ) {
										net.forestany.forestj.lib.Global.ilogWarning("for field '" + o_field.getName() + "' one array element is null but not the other array element '" + ( (a_objectsOne[i] == null) ? "null" : a_objectsOne[i].toString() + "[" + a_objectsOne[i].getClass().getTypeName() + "]") + "' != '" + ((a_objectsTwo[i] == null) ? "null" : a_objectsTwo[i].toString() + "[" + a_objectsTwo.getClass().getTypeName() + "]") + "'");
										return false;
									}
									
									if (!a_objectsOne[i].equals(a_objectsTwo[i])) {
										net.forestany.forestj.lib.Global.ilogWarning("both array elements for field '" + o_field.getName() + "' are not equal '" +  a_objectsOne[i] + "' != '" + a_objectsTwo[i] + "'");
										return false;
									}
								}
							} else if (s_arrayType.contentEquals("java.time.localtime")) {
								/* cast current field of parameter object as array */
								java.time.LocalTime[] a_objectsOne = (java.time.LocalTime[])o_objectOne;
								java.time.LocalTime[] a_objectsTwo = (java.time.LocalTime[])o_objectTwo;
								
								/* both arrays must have the same length */
								if (a_objectsOne.length != a_objectsTwo.length) {
									net.forestany.forestj.lib.Global.ilogWarning("both arrays for field '" + o_field.getName() + "' have not the same length '" + a_objectsOne.length + " != " + a_objectsTwo.length + "'");
									return false;
								}
								
								/* check each array element for equality */
								for (int i = 0; i < a_objectsOne.length; i++) {
									/* both array elements are null */
									if ( (a_objectsOne[i] == null) && (a_objectsTwo[i] == null) ) {
										continue;
									}
									
									/* if one array element is null but not the other, they are not equal */
									if ( (a_objectsOne[i] == null) ^ (a_objectsTwo[i] == null) ) {
										net.forestany.forestj.lib.Global.ilogWarning("for field '" + o_field.getName() + "' one array element is null but not the other array element '" + ( (a_objectsOne[i] == null) ? "null" : a_objectsOne[i].toString() + "[" + a_objectsOne[i].getClass().getTypeName() + "]") + "' != '" + ((a_objectsTwo[i] == null) ? "null" : a_objectsTwo[i].toString() + "[" + a_objectsTwo.getClass().getTypeName() + "]") + "'");
										return false;
									}
									
									if (!a_objectsOne[i].equals(a_objectsTwo[i])) {
										net.forestany.forestj.lib.Global.ilogWarning("both array elements for field '" + o_field.getName() + "' are not equal '" +  a_objectsOne[i] + "' != '" + a_objectsTwo[i] + "'");
										return false;
									}
								}
							} else if (s_arrayType.contentEquals("java.time.localdate")) {
								/* cast current field of parameter object as array */
								java.time.LocalDate[] a_objectsOne = (java.time.LocalDate[])o_objectOne;
								java.time.LocalDate[] a_objectsTwo = (java.time.LocalDate[])o_objectTwo;
								
								/* both arrays must have the same length */
								if (a_objectsOne.length != a_objectsTwo.length) {
									net.forestany.forestj.lib.Global.ilogWarning("both arrays for field '" + o_field.getName() + "' have not the same length '" + a_objectsOne.length + " != " + a_objectsTwo.length + "'");
									return false;
								}
								
								/* check each array element for equality */
								for (int i = 0; i < a_objectsOne.length; i++) {
									/* both array elements are null */
									if ( (a_objectsOne[i] == null) && (a_objectsTwo[i] == null) ) {
										continue;
									}
									
									/* if one array element is null but not the other, they are not equal */
									if ( (a_objectsOne[i] == null) ^ (a_objectsTwo[i] == null) ) {
										net.forestany.forestj.lib.Global.ilogWarning("for field '" + o_field.getName() + "' one array element is null but not the other array element '" + ( (a_objectsOne[i] == null) ? "null" : a_objectsOne[i].toString() + "[" + a_objectsOne[i].getClass().getTypeName() + "]") + "' != '" + ((a_objectsTwo[i] == null) ? "null" : a_objectsTwo[i].toString() + "[" + a_objectsTwo.getClass().getTypeName() + "]") + "'");
										return false;
									}
									
									if (!a_objectsOne[i].equals(a_objectsTwo[i])) {
										net.forestany.forestj.lib.Global.ilogWarning("both array elements for field '" + o_field.getName() + "' are not equal '" +  a_objectsOne[i] + "' != '" + a_objectsTwo[i] + "'");
										return false;
									}
								}
							} else if (s_arrayType.contentEquals("java.time.localdatetime")) {
								/* cast current field of parameter object as array */
								java.time.LocalDateTime[] a_objectsOne = (java.time.LocalDateTime[])o_objectOne;
								java.time.LocalDateTime[] a_objectsTwo = (java.time.LocalDateTime[])o_objectTwo;
								
								/* both arrays must have the same length */
								if (a_objectsOne.length != a_objectsTwo.length) {
									net.forestany.forestj.lib.Global.ilogWarning("both arrays for field '" + o_field.getName() + "' have not the same length '" + a_objectsOne.length + " != " + a_objectsTwo.length + "'");
									return false;
								}
								
								/* check each array element for equality */
								for (int i = 0; i < a_objectsOne.length; i++) {
									/* both array elements are null */
									if ( (a_objectsOne[i] == null) && (a_objectsTwo[i] == null) ) {
										continue;
									}
									
									/* if one array element is null but not the other, they are not equal */
									if ( (a_objectsOne[i] == null) ^ (a_objectsTwo[i] == null) ) {
										net.forestany.forestj.lib.Global.ilogWarning("for field '" + o_field.getName() + "' one array element is null but not the other array element '" + ( (a_objectsOne[i] == null) ? "null" : a_objectsOne[i].toString() + "[" + a_objectsOne[i].getClass().getTypeName() + "]") + "' != '" + ((a_objectsTwo[i] == null) ? "null" : a_objectsTwo[i].toString() + "[" + a_objectsTwo.getClass().getTypeName() + "]") + "'");
										return false;
									}
									
									if (!a_objectsOne[i].equals(a_objectsTwo[i])) {
										net.forestany.forestj.lib.Global.ilogWarning("both array elements for field '" + o_field.getName() + "' are not equal '" +  a_objectsOne[i] + "' != '" + a_objectsTwo[i] + "'");
										return false;
									}
								}
							} else if (s_arrayType.contentEquals("java.math.bigdecimal")) {
								/* cast current field of parameter object as array */
								java.math.BigDecimal[] a_objectsOne = (java.math.BigDecimal[])o_objectOne;
								java.math.BigDecimal[] a_objectsTwo = (java.math.BigDecimal[])o_objectTwo;
								
								/* both arrays must have the same length */
								if (a_objectsOne.length != a_objectsTwo.length) {
									net.forestany.forestj.lib.Global.ilogWarning("both arrays for field '" + o_field.getName() + "' have not the same length '" + a_objectsOne.length + " != " + a_objectsTwo.length + "'");
									return false;
								}
								
								/* check each array element for equality */
								for (int i = 0; i < a_objectsOne.length; i++) {
									/* both array elements are null */
									if ( (a_objectsOne[i] == null) && (a_objectsTwo[i] == null) ) {
										continue;
									}
									
									/* if one array element is null but not the other, they are not equal */
									if ( (a_objectsOne[i] == null) ^ (a_objectsTwo[i] == null) ) {
										net.forestany.forestj.lib.Global.ilogWarning("for field '" + o_field.getName() + "' one array element is null but not the other array element '" + ( (a_objectsOne[i] == null) ? "null" : a_objectsOne[i].toString() + "[" + a_objectsOne[i].getClass().getTypeName() + "]") + "' != '" + ((a_objectsTwo[i] == null) ? "null" : a_objectsTwo[i].toString() + "[" + a_objectsTwo.getClass().getTypeName() + "]") + "'");
										return false;
									}
									
									if (!a_objectsOne[i].equals(a_objectsTwo[i])) {
										net.forestany.forestj.lib.Global.ilogWarning("both array elements for field '" + o_field.getName() + "' are not equal '" +  a_objectsOne[i] + "' != '" + a_objectsTwo[i] + "'");
										return false;
									}
								}	
							}
						}
					} else {
						net.forestany.forestj.lib.Global.ilogFiner(o_field.getName() + " objects equal: " + o_objectOne.equals(o_objectTwo) + "\t" + o_objectOne.toString() + "\t\t" + o_objectTwo.toString());
						
						/* only execute deep comparison if both objects are not allowed types */
						if ( (p_b_deepComparison) && (!(a_allowedTypes.contains(o_objectOne.getClass().getTypeName()))) ) {
							/* deep comparison of both objects */
							if (!Helper.objectsEqualUsingReflections(o_objectOne, o_objectTwo, p_b_usePropertyMethods, p_b_deepComparison)) {
								return false;
							}
			        	} else {
							/* check if field values of both parameter objects are equal */
							if (!o_objectOne.equals(o_objectTwo)) {
								net.forestany.forestj.lib.Global.ilogWarning("both object parameter are not equal '" + o_objectOne.toString() + "' != '" + o_objectTwo.toString() + "'");
								return false;
							}
			        	}
					}
				} else {
					net.forestany.forestj.lib.Global.ilogFiner(o_field.getName() + " objects equal: true\tnull\t\tnull");
				}
			}
		}
		
		return true;
	}
}
