package net.forestany.forestj.lib.io;

/**
 * Collection of standard transpose methods
 */
public class StandardTransposeMethods {
	/**
	 * empty constructor
	 */
	public StandardTransposeMethods() {
		
	}
	
	/** 
	 * Creates empty value string
	 * 
	 * @param p_s_emptyCharacter			character where empty value will be based on, e.g. ' ' or '0'
	 * @param p_i_length					target length of empty value string
	 * @return								empty value string
	 * @throws IllegalArgumentException		parameter for empty character must be only '1' character long
	 */
	private static String EmptyValue(String p_s_emptyCharacter, int p_i_length) throws IllegalArgumentException {
		/* check empty character parameter */
		if (p_s_emptyCharacter.length() != 1) {
			throw new IllegalArgumentException("Parameter for empty character must be only '1' character long");
		}
		
		String s_foo = "";
		
		/* iterate until length has been reached */
		for (int i = 0; i < p_i_length; i++) {
			/* add character parameter to empty value string */
			s_foo += p_s_emptyCharacter.charAt(0);
		}
		
		return s_foo;
	}
	
	/* String */
	
	/**
	 * Transpose string value
	 * 
	 * @param p_s_value					string value
	 * @return							string type
	 * @throws ClassCastException		cannot cast string
	 */
	public static Object TransposeString(String p_s_value) throws ClassCastException {
		/* return string value */
		return String.valueOf(p_s_value);
	}
	
	/**
	 * Transpose object to string value
	 * 
	 * @param p_o_value					object value
	 * @param p_i_length				set length for string value
	 * @return							string value
	 * @throws ClassCastException		cannot cast parameter to string type
	 */
	public static String TransposeString(Object p_o_value, int p_i_length) throws ClassCastException {
		/* if parameter is null, return empty value string */
		if (p_o_value == null) {
			return EmptyValue(" ", p_i_length);
		}
		
		/* return string with set length */
		return p_o_value.toString().substring(0, p_i_length);
	}
	
	/* Boolean */
	
	/**
	 * Transpose boolean string value to boolean value
	 * 
	 * @param p_s_value					string value
	 * @return							boolean type
	 * @throws ClassCastException		cannot cast string to boolean type
	 */
	public static Object TransposeBoolean(String p_s_value) throws ClassCastException {
		/* recognize '1', 'true', 'y' and 'j' as boolean true, otherwise we recognize false */
		if ( (p_s_value.contentEquals("1")) || (p_s_value.contentEquals("true")) || (p_s_value.contentEquals("y")) || (p_s_value.contentEquals("j")) ) {
			p_s_value = "true";
		} else {
			p_s_value = "false";
		}
		
		/* return boolean value */
		return Boolean.valueOf(p_s_value);
	}
	
	/**
	 * Transpose boolean to string value
	 * 
	 * @param p_o_value					boolean value
	 * @param p_i_length				set length for string value
	 * @return							string value
	 * @throws ClassCastException		cannot cast parameter to boolean type
	 */
	public static String TransposeBoolean(Object p_o_value, int p_i_length) throws ClassCastException {
		/* handle boolean with set string length of '1' */
		if (p_i_length == 1) {
			/* return string with set length */
			if ((boolean)p_o_value) {
				return "1"; 
			} else {
				return "0";
			}
		} else {
			return "0";
		}
	}
	
	/**
	 * Number transpose methods
	 */
	public class Numbers {
		/**
		 * empty constructor
		 */
		public Numbers() {
			
		}
		
		/* Byte */

		/**
		 * Transpose byte string value to byte type
		 * 
		 * @param p_s_value					byte string value
		 * @return							byte type
		 * @throws ClassCastException		cannot cast string value to byte type
		 */
		public static Object TransposeByte(String p_s_value) throws ClassCastException {
			/* check byte value */
			if (!net.forestany.forestj.lib.Helper.isByte(p_s_value)) {
				throw new ClassCastException("Value '" + p_s_value + "' cannot be cast to Byte");
			}
			
			/* return byte value */
			return Byte.valueOf(p_s_value);
		}
		
		/**
		 * Transpose byte value to byte string value
		 * 
		 * @param p_o_value					byte value
		 * @param p_i_length				set length for byte string value
		 * @return							byte string value
		 * @throws ClassCastException		cannot cast parameter to byte type
		 */
		public static String TransposeByte(Object p_o_value, int p_i_length) throws ClassCastException {
			/* check if parameter is a byte */
			if (p_o_value instanceof Byte) {
				/* return byte string with set length */
				return String.format((java.util.Locale) null, "%0" + p_i_length + "d", (byte)p_o_value);
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not a Byte");
			}
		}
		
		/**
		 * Transpose byte string value with sign to byte type
		 * 
		 * @param p_s_value					byte string value with sign
		 * @return							byte type
		 * @throws ClassCastException		cannot cast string value to byte type
		 */
		public static Object TransposeByteWithSign(String p_s_value) throws ClassCastException {
			byte by_multiply = (byte)1;
			
			/* check sign */
			if (p_s_value.startsWith("+")) {
				/* remove '+' sign */
				p_s_value = p_s_value.substring(1);
			} else if (p_s_value.startsWith("-")) {
				/* remove '-' sign */
				p_s_value = p_s_value.substring(1);
				/* set multiply sign to '-1' */
				by_multiply = (byte)-1;
			}
			
			/* check byte value */
			if (!net.forestany.forestj.lib.Helper.isByte(p_s_value)) {
				throw new ClassCastException("Value '" + p_s_value + "' cannot be cast to Byte");
			}
			
			/* return byte value with sign */
			return (byte)(Byte.valueOf(p_s_value) * by_multiply);
		}
		
		/**
		 * Transpose byte value to byte string value with sign
		 * 
		 * @param p_o_value					byte value
		 * @param p_i_length				set length for byte string value
		 * @return							byte string value with sign
		 * @throws ClassCastException		cannot cast parameter to byte type
		 */
		public static String TransposeByteWithSign(Object p_o_value, int p_i_length) throws ClassCastException {
			/* check if parameter is a byte */
			if (p_o_value instanceof Byte) {
				/* check if byte value is negative */
				if ((byte)p_o_value < 0) {
					/* turn negative to positive value */
					byte by_foo = (byte)( (byte)-1 * (byte)p_o_value );
					/* return byte string with negative sign and set length */
					return "-" + String.format((java.util.Locale) null, "%0" + (p_i_length - 1) + "d", by_foo);
				} else {
					/* return byte string with positive sign and set length */
					return "+" + String.format((java.util.Locale) null, "%0" + (p_i_length - 1) + "d", (byte)p_o_value);
				}
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not a Byte");
			}
		}
		
		/* Short */
		
		/**
		 * Transpose short string value to short type
		 * 
		 * @param p_s_value					short string value
		 * @return							short type
		 * @throws ClassCastException		cannot cast string value to short type
		 */
		public static Object TransposeShort(String p_s_value) throws ClassCastException {
			/* check short value */
			if (!net.forestany.forestj.lib.Helper.isShort(p_s_value)) {
				throw new ClassCastException("Value '" + p_s_value + "' cannot be cast to Short");
			}
			
			/* return short value */
			return Short.valueOf(p_s_value);
		}
		
		/**
		 * Transpose short value to short string value
		 * 
		 * @param p_o_value					short value
		 * @param p_i_length				set length for short string value
		 * @return							short string value
		 * @throws ClassCastException		cannot cast parameter to short type
		 */
		public static String TransposeShort(Object p_o_value, int p_i_length) throws ClassCastException {
			/* check if parameter is a short */
			if (p_o_value instanceof Short) {
				/* return short string with set length */
				return String.format((java.util.Locale) null, "%0" + p_i_length + "d", (short)p_o_value);
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not a Short");
			}
		}
		
		/**
		 * Transpose short string value with sign to short type
		 * 
		 * @param p_s_value					short string value with sign
		 * @return							short type
		 * @throws ClassCastException		cannot cast string value to short type
		 */
		public static Object TransposeShortWithSign(String p_s_value) throws ClassCastException {
			short sh_multiply = (short)1;
			
			/* check sign */
			if (p_s_value.startsWith("+")) {
				/* remove '+' sign */
				p_s_value = p_s_value.substring(1);
			} else if (p_s_value.startsWith("-")) {
				/* remove '-' sign */
				p_s_value = p_s_value.substring(1);
				/* set multiply sign to '-1' */
				sh_multiply = (short)-1;
			}
			
			/* check short value */
			if (!net.forestany.forestj.lib.Helper.isShort(p_s_value)) {
				throw new ClassCastException("Value '" + p_s_value + "' cannot be cast to Short");
			}
			
			/* return short value with sign */
			return (short)(Short.valueOf(p_s_value) * sh_multiply);
		}
		
		/**
		 * Transpose short value to short string value with sign
		 * 
		 * @param p_o_value					short value
		 * @param p_i_length				set length for short string value
		 * @return							short string value with sign
		 * @throws ClassCastException		cannot cast parameter to short type
		 */
		public static String TransposeShortWithSign(Object p_o_value, int p_i_length) throws ClassCastException {
			/* check if parameter is a short */
			if (p_o_value instanceof Short) {
				/* check if short value is negative */
				if ((short)p_o_value < 0) {
					/* turn negative to positive value */
					short sh_foo = (short)( (short)-1 * (short)p_o_value );
					/* return short string with negative sign and set length */
					return "-" + String.format((java.util.Locale) null, "%0" + (p_i_length - 1) + "d", sh_foo);
				} else {
					/* return short string with positive sign and set length */
					return "+" + String.format((java.util.Locale) null, "%0" + (p_i_length - 1) + "d", (short)p_o_value);
				}
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not a Short");
			}
		}
		
		/* Integer */
		
		/**
		 * Transpose integer string value to integer type
		 * 
		 * @param p_s_value					integer string value
		 * @return							integer type
		 * @throws ClassCastException		cannot cast string value to integer type
		 */
		public static Object TransposeInteger(String p_s_value) throws ClassCastException {
			/* check integer value */
			if (!net.forestany.forestj.lib.Helper.isInteger(p_s_value)) {
				throw new ClassCastException("Value '" + p_s_value + "' cannot be cast to Integer");
			}
			
			/* return integer value */
			return Integer.valueOf(p_s_value);
		}
		
		/**
		 * Transpose integer value to integer string value
		 * 
		 * @param p_o_value					integer value
		 * @param p_i_length				set length for integer string value
		 * @return							integer string value
		 * @throws ClassCastException		cannot cast parameter to integer type
		 */
		public static String TransposeInteger(Object p_o_value, int p_i_length) throws ClassCastException {
			/* check if parameter is an integer */
			if (p_o_value instanceof Integer) {
				/* return integer string with set length */
				return String.format((java.util.Locale) null, "%0" + p_i_length + "d", (int)p_o_value);
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not an Integer");
			}
		}
		
		/**
		 * Transpose integer string value with sign to integer type
		 * 
		 * @param p_s_value					integer string value with sign
		 * @return							integer type
		 * @throws ClassCastException		cannot cast string value to integer type
		 */
		public static Object TransposeIntegerWithSign(String p_s_value) throws ClassCastException {
			int i_multiply = 1;
			
			/* check sign */
			if (p_s_value.startsWith("+")) {
				/* remove '+' sign */
				p_s_value = p_s_value.substring(1);
			} else if (p_s_value.startsWith("-")) {
				/* remove '-' sign */
				p_s_value = p_s_value.substring(1);
				/* set multiply sign to '-1' */
				i_multiply = -1;
			}
			
			/* check integer value */
			if (!net.forestany.forestj.lib.Helper.isInteger(p_s_value)) {
				throw new ClassCastException("Value '" + p_s_value + "' cannot be cast to Integer");
			}
			
			/* return integer value with sign */
			return (int)(Integer.valueOf(p_s_value) * i_multiply);
		}
		
		/**
		 * Transpose integer value to integer string value with sign
		 * 
		 * @param p_o_value					integer value
		 * @param p_i_length				set length for integer string value
		 * @return							integer string value with sign
		 * @throws ClassCastException		cannot cast parameter to integer type
		 */
		public static String TransposeIntegerWithSign(Object p_o_value, int p_i_length) throws ClassCastException {
			/* check if parameter is an integer */
			if (p_o_value instanceof Integer) {
				/* check if integer value is negative */
				if ((int)p_o_value < 0) {
					/* turn negative to positive value */
					int i_foo = -1 * (int)p_o_value;
					/* return integer string with negative sign and set length */
					return "-" + String.format((java.util.Locale) null, "%0" + (p_i_length - 1) + "d", i_foo);
				} else {
					/* return integer string with positive sign and set length */
					return "+" + String.format((java.util.Locale) null, "%0" + (p_i_length - 1) + "d", (int)p_o_value);
				}
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not an Integer");
			}
		}
		
		/* Long */
		
		/**
		 * Transpose long string value to long type
		 * 
		 * @param p_s_value					long string value
		 * @return							long type
		 * @throws ClassCastException		cannot cast string value to long type
		 */
		public static Object TransposeLong(String p_s_value) throws ClassCastException {
			/* check long value */
			if (!net.forestany.forestj.lib.Helper.isLong(p_s_value)) {
				throw new ClassCastException("Value '" + p_s_value + "' cannot be cast to Long");
			}
			
			/* return long value */
			return Long.valueOf(p_s_value);
		}
		
		/**
		 * Transpose long value to long string value
		 * 
		 * @param p_o_value					long value
		 * @param p_i_length				set length for long string value
		 * @return							long string value
		 * @throws ClassCastException		cannot cast parameter to long type
		 */
		public static String TransposeLong(Object p_o_value, int p_i_length) throws ClassCastException {
			/* check if parameter is a long */
			if (p_o_value instanceof Long) {
				/* return long string with set length */
				return String.format((java.util.Locale) null, "%0" + p_i_length + "d", (long)p_o_value);
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not a Long");
			}
		}
		
		/**
		 * Transpose long string value with sign to long type
		 * 
		 * @param p_s_value					long string value with sign
		 * @return							long type
		 * @throws ClassCastException		cannot cast string value to long type
		 */
		public static Object TransposeLongWithSign(String p_s_value) throws ClassCastException {
			long l_multiply = (long)1;
			
			/* check sign */
			if (p_s_value.startsWith("+")) {
				/* remove '+' sign */
				p_s_value = p_s_value.substring(1);
			} else if (p_s_value.startsWith("-")) {
				/* remove '-' sign */
				p_s_value = p_s_value.substring(1);
				/* set multiply sign to '-1' */
				l_multiply = (long)-1;
			}
			
			/* check long value */
			if (!net.forestany.forestj.lib.Helper.isLong(p_s_value)) {
				throw new ClassCastException("Value '" + p_s_value + "' cannot be cast to Long");
			}
			
			/* return long value with sign */
			return (long)(Long.valueOf(p_s_value) * l_multiply);
		}
		
		/**
		 * Transpose long value to long string value with sign
		 * 
		 * @param p_o_value					long value
		 * @param p_i_length				set length for long string value
		 * @return							long string value with sign
		 * @throws ClassCastException		cannot cast parameter to long type
		 */
		public static String TransposeLongWithSign(Object p_o_value, int p_i_length) throws ClassCastException {
			/* check if parameter is a long */
			if (p_o_value instanceof Long) {
				/* check if long value is negative */
				if ((long)p_o_value < 0) {
					/* turn negative to positive value */
					long l_foo = (long)( (long)-1 * (long)p_o_value );
					/* return long string with negative sign and set length */
					return "-" + String.format((java.util.Locale) null, "%0" + (p_i_length - 1) + "d", l_foo);
				} else {
					/* return long string with positive sign and set length */
					return "+" + String.format((java.util.Locale) null, "%0" + (p_i_length - 1) + "d", (long)p_o_value);
				}
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not a Long");
			}
		}
	}
	
	/**
	 * java.util.Date transpose methods
	 */
	public class UtilDate {
		/**
		 * empty constructor
		 */
		public UtilDate() {
			
		}
		
		/**
		 * Transpose any string value to util date type
		 * 
		 * @param p_s_value					any string value
		 * @param p_s_format				format string for util date parser
		 * @return							util date type
		 * @throws ClassCastException		cannot cast string value to util date type or invalid format parameter
		 */
		private static Object TransposeDate_any(String p_s_value, String p_s_format) throws ClassCastException {
			java.text.DateFormat o_dateFormat;
			
			try {
				/* check if we use format for RFC 1123 */
				if (p_s_format.endsWith("zzz")) {
					/* set util date format with Locale.US */
					o_dateFormat = new java.text.SimpleDateFormat(p_s_format, java.util.Locale.US);
				} else {
					/* set util date format */
					o_dateFormat = new java.text.SimpleDateFormat(p_s_format);
				}
				
				/* check if format ends with "'Z'" or "z" */
				if ( (p_s_format.endsWith("'Z'")) || (p_s_format.endsWith("zzz")) ) {
					/* set UTC timezone for date format */
					o_dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
				}
			} catch (Exception o_exc) {
				throw new ClassCastException("Illegal format parameter '" + p_s_format + "' - " + o_exc);
			}
			
			try {
				/* return string as util date object */
				return o_dateFormat.parse(p_s_value);
			} catch (java.text.ParseException o_exc) {
				throw new ClassCastException("Value '" + p_s_value + "' cannot be cast to timestamp util date: " + o_exc);
			}
		}
		
		/**
		 * Transpose util date value to any string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for any string value
		 * @param p_s_format				format string for util date parser
		 * @return							any string value
		 * @throws ClassCastException		cannot cast parameter to util date type or invalid format parameter
		 */
		private static String TransposeDate_any(Object p_o_value, int p_i_length, String p_s_format) throws ClassCastException {
			/* if parameter is null, return empty value string */
			if (p_o_value == null) {
				/* use zero as parameter character for these specific formats */
				if ( (p_s_format == "yyyyMMddHHmmss") || (p_s_format == "yyyyMMdd") || (p_s_format == "yyyyMMdd") || (p_s_format == "HHmmss") || (p_s_format == "HHmm") ) {
					return EmptyValue("0", p_i_length);
				} else {
					return EmptyValue(" ", p_i_length);
				}
			}
			
			/* check if parameter is a util date */
			if (p_o_value instanceof java.util.Date) {
				java.text.DateFormat o_dateFormat;
				
				try {
					/* check if we use format for RFC 1123 */
					if (p_s_format.endsWith("zzz")) {
						/* set util date format with Locale.US */
						o_dateFormat = new java.text.SimpleDateFormat(p_s_format, java.util.Locale.US);
					} else {
						/* set util date format */
						o_dateFormat = new java.text.SimpleDateFormat(p_s_format);
					}
					
					/* check if format ends with "'Z'" or "z" */
					if ( (p_s_format.endsWith("'Z'")) || (p_s_format.endsWith("zzz")) ) {
						/* set UTC timezone for date format */
						o_dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
					}
				} catch (Exception o_exc) {
					throw new ClassCastException("Illegal format parameter '" + p_s_format + "' - " + o_exc);
				}
				
				/* create timestamp string */
				String s_foo = o_dateFormat.format((java.util.Date)p_o_value);
				
				/* check if we use format for RFC 1123 */
				if (p_s_format.endsWith("zzz")) {
					/* get rid of 'UTC' at the end and write 'GMT' */
					s_foo = s_foo.substring(0, s_foo.length() - 3) + "GMT";
				}
				
				/* return timestamp string */
				return s_foo.substring(0, p_i_length);
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not a util date");
			}
		}
		
		/**
		 * Transpose ISO8601 timestamp string value to util date type
		 * 
		 * @param p_s_value					ISO8601 timestamp string value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast ISO8601 timestamp string value to util date type
		 */
		public static Object TransposeDate_ISO8601(String p_s_value) throws ClassCastException {
			String s_format = "yyyy-MM-dd'T'HH:mm:ss'Z'";
			
			/* accept parameter value without date and time separator 'T' */
			if (!p_s_value.contains("T")) {
				s_format = "yyyy-MM-dd' 'HH:mm:ss'Z'";
			}
			
			return TransposeDate_any(p_s_value, s_format);
		}
		
		/**
		 * Transpose util date value to ISO8601 timestamp string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for timestamp string value
		 * @return							ISO8601 timestamp string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_ISO8601(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_ISO8601(p_o_value, p_i_length, false);
		}
		
		/**
		 * Transpose util date value to ISO8601 timestamp string value, where util date value has no 'T' separator
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for timestamp string value
		 * @return							ISO8601 timestamp string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_ISO8601_NoSeparator(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_ISO8601(p_o_value, p_i_length, true);
		}
		
		/**
		 * Transpose util date value to ISO8601 timestamp string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for timestamp string value
		 * @param p_b_noSeparator			true - no date and time separator 'T', false - normal ISO8601 format
		 * @return							ISO8601 timestamp string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		private static String TransposeDate_ISO8601(Object p_o_value, int p_i_length, boolean p_b_noSeparator) throws ClassCastException {
			String s_format = "yyyy-MM-dd'T'HH:mm:ss'Z'";
			
			/* no date and time separator 'T' */
			if (p_b_noSeparator) {
				s_format = "yyyy-MM-dd' 'HH:mm:ss'Z'";
			}
			
			return TransposeDate_any(p_o_value, p_i_length, s_format);
		}
	
		/**
		 * Transpose RFC1123 timestamp string value to util date type
		 * 
		 * @param p_s_value					RFC1123 timestamp string value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast RFC1123 timestamp string value to util date type
		 */
		public static Object TransposeDate_RFC1123(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "EEE, dd MMM yyyy HH:mm:ss zzz");
		}
		
		/**
		 * Transpose util date value to RFC1123 timestamp string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for RFC1123 timestamp string value
		 * @return							RFC1123 timestamp string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_RFC1123(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "EEE, dd MMM yyyy HH:mm:ss zzz");
		}
		
		/**
		 * Transpose timestamp string value to util date type
		 * 
		 * @param p_s_value					timestamp string value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast timestamp string value to util date type
		 */
		public static Object TransposeDate_yyyymmddhhiiss(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "yyyyMMddHHmmss");
		}
		
		/**
		 * Transpose util date value to timestamp string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for timestamp string value
		 * @return							timestamp string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_yyyymmddhhiiss(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "yyyyMMddHHmmss");
		}
		
		/**
		 * Transpose DMY timestamp with dot string value to util date type
		 * 
		 * @param p_s_value					DMY timestamp with dot string value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast DMY timestamp with dot string value to util date type
		 */
		public static Object TransposeDate_ddmmyyyyhhiiss_Dot(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "dd.MM.yyyy HH:mm:ss");
		}
		
		/**
		 * Transpose util date value to DMY timestamp with dot string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for DMY timestamp with dot string value
		 * @return							DMY timestamp with dot string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_ddmmyyyyhhiiss_Dot(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "dd.MM.yyyy HH:mm:ss");
		}
	
		/**
		 * Transpose DMY timestamp with slash string value to util date type
		 * 
		 * @param p_s_value					DMY timestamp with slash string value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast DMY timestamp with slash string value to util date type
		 */
		public static Object TransposeDate_ddmmyyyyhhiiss_Slash(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "dd/MM/yyyy HH:mm:ss");
		}
		
		/**
		 * Transpose util date value to DMY timestamp with slash string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for DMY timestamp with slash string value
		 * @return							DMY timestamp with slash string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_ddmmyyyyhhiiss_Slash(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "dd/MM/yyyy HH:mm:ss");
		}
		
		/**
		 * Transpose YMD timestamp with dot string value to util date type
		 * 
		 * @param p_s_value					YMD timestamp with dot string value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast YMD timestamp with dot string value to util date type
		 */
		public static Object TransposeDate_yyyymmddhhiiss_Dot(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "yyyy.MM.dd HH:mm:ss");
		}
		
		/**
		 * Transpose util date value to YMD timestamp with dot string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for DMY timestamp with dot string value
		 * @return							YMD timestamp with dot string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_yyyymmddhhiiss_Dot(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "yyyy.MM.dd HH:mm:ss");
		}
	
		/**
		 * Transpose YMD timestamp with slash string value to util date type
		 * 
		 * @param p_s_value					YMD timestamp with slash string value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast YMD timestamp with slash string value to util date type
		 */
		public static Object TransposeDate_yyyymmddhhiiss_Slash(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "yyyy/MM/dd HH:mm:ss");
		}
		
		/**
		 * Transpose util date value to YMD timestamp with slash string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for DMY timestamp with slash string value
		 * @return							YMD timestamp with slash string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_yyyymmddhhiiss_Slash(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "yyyy/MM/dd HH:mm:ss");
		}
		
		/**
		 * Transpose MDY timestamp with dot string value to util date type
		 * 
		 * @param p_s_value					MDY timestamp with dot string value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast MDY timestamp with dot string value to util date type
		 */
		public static Object TransposeDate_mmddyyyyhhiiss_Dot(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "MM.dd.yyyy HH:mm:ss");
		}
		
		/**
		 * Transpose util date value to MDY timestamp with dot string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for MDY timestamp with dot string value
		 * @return							MDY timestamp with dot string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_mmddyyyyhhiiss_Dot(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "MM.dd.yyyy HH:mm:ss");
		}
	
		/**
		 * Transpose MDY timestamp with slash string value to util date type
		 * 
		 * @param p_s_value					MDY timestamp with slash string value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast MDY timestamp with slash string value to util date type
		 */
		public static Object TransposeDate_mmddyyyyhhiiss_Slash(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "MM/dd/yyyy HH:mm:ss");
		}
		
		/**
		 * Transpose util date value to MDY timestamp with slash string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for MDY timestamp with slash string value
		 * @return							MDY timestamp with slash string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_mmddyyyyhhiiss_Slash(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "MM/dd/yyyy HH:mm:ss");
		}
		
		/**
		 * Transpose date string value to util date type
		 * 
		 * @param p_s_value					date string value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast date string value to util date type
		 */
		public static Object TransposeDate_yyyymmdd(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "yyyyMMdd");
		}
		
		/**
		 * Transpose util date value to date string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for date string value
		 * @return							date string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_yyyymmdd(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "yyyyMMdd");
		}
		
		/**
		 * Transpose DMY date with dot string value to util date type
		 * 
		 * @param p_s_value					DMY date with dot string value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast DMY date with dot string value to util date type
		 */
		public static Object TransposeDate_ddmmyyyy_Dot(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "dd.MM.yyyy");
		}
		
		/**
		 * Transpose util date value to DMY date with dot string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for DMY date with dot string value
		 * @return							DMY date with dot string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_ddmmyyyy_Dot(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "dd.MM.yyyy");
		}
	
		/**
		 * Transpose DMY date with slash string value to util date type
		 * 
		 * @param p_s_value					DMY date with slash string value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast DMY date with slash string value to util date type
		 */
		public static Object TransposeDate_ddmmyyyy_Slash(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "dd/MM/yyyy");
		}
		
		/**
		 * Transpose util date value to DMY date with slash string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for DMY date with slash string value
		 * @return							DMY date with slash string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_ddmmyyyy_Slash(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "dd/MM/yyyy");
		}
		
		/**
		 * Transpose YMD date with dot string value to util date type
		 * 
		 * @param p_s_value					YMD date with dot string value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast YMD date with dot string value to util date type
		 */
		public static Object TransposeDate_yyyymmdd_Dot(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "yyyy.MM.dd");
		}
		
		/**
		 * Transpose util date value to YMD date with dot string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for DMY date with dot string value
		 * @return							YMD date with dot string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_yyyymmdd_Dot(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "yyyy.MM.dd");
		}
	
		/**
		 * Transpose YMD date with slash string value to util date type
		 * 
		 * @param p_s_value					YMD date with slash string value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast YMD date with slash string value to util date type
		 */
		public static Object TransposeDate_yyyymmdd_Slash(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "yyyy/MM/dd");
		}
		
		/**
		 * Transpose util date value to YMD date with slash string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for DMY date with slash string value
		 * @return							YMD date with slash string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_yyyymmdd_Slash(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "yyyy/MM/dd");
		}
		
		/**
		 * Transpose MDY date with dot string value to util date type
		 * 
		 * @param p_s_value					MDY date with dot string value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast MDY date with dot string value to util date type
		 */
		public static Object TransposeDate_mmddyyyy_Dot(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "MM.dd.yyyy");
		}
		
		/**
		 * Transpose util date value to MDY date with dot string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for MDY date with dot string value
		 * @return							MDY date with dot string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_mmddyyyy_Dot(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "MM.dd.yyyy");
		}
	
		/**
		 * Transpose MDY date with slash string value to util date type
		 * 
		 * @param p_s_value					MDY date with slash string value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast MDY date with slash string value to util date type
		 */
		public static Object TransposeDate_mmddyyyy_Slash(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "MM/dd/yyyy");
		}
		
		/**
		 * Transpose util date value to MDY date with slash string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for MDY date with slash string value
		 * @return							MDY date with slash string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_mmddyyyy_Slash(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "MM/dd/yyyy");
		}
		
		/**
		 * Transpose time string value to util date type
		 * 
		 * @param p_s_value					time string value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast time string value to util date type
		 */
		public static Object TransposeDate_hhiiss(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "HHmmss");
		}
		
		/**
		 * Transpose util date value to time string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for time string value
		 * @return							time string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_hhiiss(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "HHmmss");
		}
		
		/**
		 * Transpose time string value to util date type
		 * 
		 * @param p_s_value					time string value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast time string value to util date type
		 */
		public static Object TransposeDate_hhii(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "HHmm");
		}
		
		/**
		 * Transpose util date value to time string value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for time string value
		 * @return							time string value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_hhii(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "HHmm");
		}
		
		/**
		 * Transpose time string with colon value to util date type
		 * 
		 * @param p_s_value					time string with colon value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast time string with colon value to util date type
		 */
		public static Object TransposeDate_hhiiss_Colon(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "HH:mm:ss");
		}
		
		/**
		 * Transpose util date value to time string with colon value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for time string with colon value
		 * @return							time string with colon value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_hhiiss_Colon(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "HH:mm:ss");
		}
		
		/**
		 * Transpose time string with colon value to util date type
		 * 
		 * @param p_s_value					time string with colon value
		 * @return							util date type
		 * @throws ClassCastException		cannot cast time string with colon value to util date type
		 */
		public static Object TransposeDate_hhii_Colon(String p_s_value) throws ClassCastException {
			return TransposeDate_any(p_s_value, "HH:mm");
		}
		
		/**
		 * Transpose util date value to time string with colon value
		 * 
		 * @param p_o_value					util date value
		 * @param p_i_length				set length for time string with colon value
		 * @return							time string with colon value
		 * @throws ClassCastException		cannot cast parameter to util date type
		 */
		public static String TransposeDate_hhii_Colon(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeDate_any(p_o_value, p_i_length, "HH:mm");
		}
	}
	
	/**
	 * java.time.LocalDateTime transpose methods
	 */
	public class LocalDateTime {
		/**
		 * empty constructor
		 */
		public LocalDateTime() {
			
		}
		
		/**
		 * Transpose ISO8601 timestamp string value to local date time type
		 * 
		 * @param p_s_value					ISO8601 timestamp string value
		 * @return							local date time type
		 * @throws ClassCastException		cannot cast ISO8601 timestamp string value to local date time type
		 */
		public static Object TransposeLocalDateTime_ISO8601(String p_s_value) throws ClassCastException {
			try {
				/* return string as local date time object */
				return net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value);
			} catch (java.time.DateTimeException o_exc) {
				throw new ClassCastException("Value '" + p_s_value + "' cannot be cast to timestamp local date time: " + o_exc);
			}
		}
		
		/**
		 * Transpose local date time value to ISO8601 timestamp string value
		 * 
		 * @param p_o_value					local date time value
		 * @param p_i_length				set length for timestamp string value
		 * @return							ISO8601 timestamp string value
		 * @throws ClassCastException		cannot cast parameter to local date time type
		 */
		public static String TransposeLocalDateTime_ISO8601(Object p_o_value, int p_i_length) throws ClassCastException {
			/* if parameter is null, return empty value string */
			if (p_o_value == null) {
				return EmptyValue(" ", p_i_length);
			}
			
			/* check if parameter is a local date time */
			if (p_o_value instanceof java.time.LocalDateTime) {
				/* create timestamp string */
				String s_foo = net.forestany.forestj.lib.Helper.toISO8601UTC((java.time.LocalDateTime)p_o_value);
				
				/* return timestamp string */
				return s_foo.substring(0, p_i_length);
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not a local date time");
			}
		}
		
		/**
		 * Transpose RFC1123 timestamp string value to local date time type
		 * 
		 * @param p_s_value					RFC1123 timestamp string value
		 * @return							local date time type
		 * @throws ClassCastException		cannot cast RFC1123 timestamp string value to local date time type
		 */
		public static Object TransposeLocalDateTime_RFC1123(String p_s_value) throws ClassCastException {
			try {
				/* create zoned date time in UTC */
				java.time.ZonedDateTime o_zonedDatetime = java.time.ZonedDateTime.parse(p_s_value, java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME);
				/* adjust to system time zone */
				java.time.ZonedDateTime o_utcZonedDateTime = o_zonedDatetime.withZoneSameInstant(java.time.ZoneId.systemDefault());
				
				/* return string as local date time object */
				return o_utcZonedDateTime.toLocalDateTime();
			} catch (java.time.DateTimeException o_exc) {
				throw new ClassCastException("Value '" + p_s_value + "' cannot be cast to timestamp local date time: " + o_exc);
			}
		}
		
		/**
		 * Transpose local date time value to RFC1123 timestamp string value
		 * 
		 * @param p_o_value					local date time value
		 * @param p_i_length				set length for RFC1123 timestamp string value
		 * @return							RFC1123 timestamp string value
		 * @throws ClassCastException		cannot cast parameter to local date time type
		 */
		public static String TransposeLocalDateTime_RFC1123(Object p_o_value, int p_i_length) throws ClassCastException {
			/* if parameter is null, return empty value string */
			if (p_o_value == null) {
				return EmptyValue(" ", p_i_length);
			}
			
			/* check if parameter is a local date time */
			if (p_o_value instanceof java.time.LocalDateTime) {
				/* create timestamp string */
				String s_foo = net.forestany.forestj.lib.Helper.toRFC1123((java.time.LocalDateTime)p_o_value);
				
				/* return timestamp string */
				return s_foo.substring(0, p_i_length);
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not a local date time");
			}
		}
		
		/**
		 * Transpose timestamp string value to local date time type
		 * 
		 * @param p_s_value					timestamp string value
		 * @return							local date time type
		 * @throws ClassCastException		cannot cast timestamp string value to local date time type
		 */
		private static Object TransposeLocalDateTime_any(String p_s_value) throws ClassCastException {
			try {
				/* return string as local date time object */
				return net.forestany.forestj.lib.Helper.fromDateTimeString(p_s_value);
			} catch (java.time.DateTimeException o_exc) {
				throw new ClassCastException("Value '" + p_s_value + "' cannot be cast to timestamp local date time: " + o_exc);
			}
		}
		
		/**
		 * Transpose local date time value to timestamp string value
		 * 
		 * @param p_o_value					local date time value
		 * @param p_i_length				set length for timestamp string value
		 * @param p_s_format				format string for local date time parser
		 * @return							timestamp string value
		 * @throws ClassCastException		cannot cast parameter to local date time type
		 */
		private static String TransposeLocalDateTime_any(Object p_o_value, int p_i_length, String p_s_format) throws ClassCastException {
			/* if parameter is null, return empty value string */
			if (p_o_value == null) {
				/* use zero as parameter character for these specific formats */
				if ( (p_s_format == "yyyyMMddHHmmss") || (p_s_format == "yyyyMMdd") || (p_s_format == "yyyyMMdd") || (p_s_format == "HHmmss") || (p_s_format == "HHmm") ) {
					return EmptyValue("0", p_i_length);
				} else {
					return EmptyValue(" ", p_i_length);
				}
			}
			
			/* check if parameter is a local date time */
			if (p_o_value instanceof java.time.LocalDateTime) {
				java.time.format.DateTimeFormatter o_dateTimeFormatter;
				
				try {
					o_dateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern(p_s_format);
				} catch (Exception o_exc) {
					throw new ClassCastException("Illegal format parameter '" + p_s_format + "' - " + o_exc);
				}
				
				/* create timestamp string */
				String s_foo = o_dateTimeFormatter.format((java.time.LocalDateTime)p_o_value);
				
				/* return timestamp string */
				return s_foo.substring(0, p_i_length);
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not a local date time");
			}
		}
		
		/**
		 * Transpose timestamp string value to local date time type
		 * 
		 * @param p_s_value					timestamp string value
		 * @return							local date time type
		 * @throws ClassCastException		cannot cast timestamp string value to local date time type
		 */
		public static Object TransposeLocalDateTime_yyyymmddhhiiss(String p_s_value) throws ClassCastException {
			return TransposeLocalDateTime_any(p_s_value);
		}
		
		/**
		 * Transpose local date time value to timestamp string value
		 * 
		 * @param p_o_value					local date time value
		 * @param p_i_length				set length for timestamp string value
		 * @return							timestamp string value
		 * @throws ClassCastException		cannot cast parameter to local date time type
		 */
		public static String TransposeLocalDateTime_yyyymmddhhiiss(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalDateTime_any(p_o_value, p_i_length, "yyyyMMddHHmmss");
		}
		
		/**
		 * Transpose timestamp string('yyyy-MM-dd HH:mm:ss') value to local date time type
		 * 
		 * @param p_s_value					timestamp string value
		 * @return							local date time type
		 * @throws ClassCastException		cannot cast timestamp string value to local date time type
		 */
		public static Object TransposeLocalDateTime_yyyymmddhhiiss_ISO(String p_s_value) throws ClassCastException {
			return TransposeLocalDateTime_any(p_s_value);
		}
		
		/**
		 * Transpose local date time value to timestamp string value 'yyyy-MM-dd HH:mm:ss'
		 * 
		 * @param p_o_value					local date time value
		 * @param p_i_length				set length for timestamp string value
		 * @return							timestamp string value
		 * @throws ClassCastException		cannot cast parameter to local date time type
		 */
		public static String TransposeLocalDateTime_yyyymmddhhiiss_ISO(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalDateTime_any(p_o_value, p_i_length, "yyyy-MM-dd HH:mm:ss");
		}
		
		/**
		
		
		/**
		 * Transpose DMY timestamp with dot string value to local date time type
		 * 
		 * @param p_s_value					DMY timestamp with dot string value
		 * @return							local date time type
		 * @throws ClassCastException		cannot cast DMY timestamp with dot string value to local date time type
		 */
		public static Object TransposeLocalDateTime_ddmmyyyyhhiiss_Dot(String p_s_value) throws ClassCastException {
			return TransposeLocalDateTime_any(p_s_value);
		}
		
		/**
		 * Transpose local date time value to DMY timestamp with dot string value
		 * 
		 * @param p_o_value					local date time value
		 * @param p_i_length				set length for DMY timestamp with dot string value
		 * @return							DMY timestamp with dot string value
		 * @throws ClassCastException		cannot cast parameter to local date time type
		 */
		public static String TransposeLocalDateTime_ddmmyyyyhhiiss_Dot(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalDateTime_any(p_o_value, p_i_length, "dd.MM.yyyy HH:mm:ss");
		}
	
		/**
		 * Transpose DMY timestamp with slash string value to local date time type
		 * 
		 * @param p_s_value					DMY timestamp with slash string value
		 * @return							local date time type
		 * @throws ClassCastException		cannot cast DMY timestamp with slash string value to local date time type
		 */
		public static Object TransposeLocalDateTime_ddmmyyyyhhiiss_Slash(String p_s_value) throws ClassCastException {
			return TransposeLocalDateTime_any(p_s_value);
		}
		
		/**
		 * Transpose local date time value to DMY timestamp with slash string value
		 * 
		 * @param p_o_value					local date time value
		 * @param p_i_length				set length for DMY timestamp with slash string value
		 * @return							DMY timestamp with slash string value
		 * @throws ClassCastException		cannot cast parameter to local date time type
		 */
		public static String TransposeLocalDateTime_ddmmyyyyhhiiss_Slash(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalDateTime_any(p_o_value, p_i_length, "dd/MM/yyyy HH:mm:ss");
		}
		
		/**
		 * Transpose YMD timestamp with dot string value to local date time type
		 * 
		 * @param p_s_value					YMD timestamp with dot string value
		 * @return							local date time type
		 * @throws ClassCastException		cannot cast YMD timestamp with dot string value to local date time type
		 */
		public static Object TransposeLocalDateTime_yyyymmddhhiiss_Dot(String p_s_value) throws ClassCastException {
			return TransposeLocalDateTime_any(p_s_value);
		}
		
		/**
		 * Transpose local date time value to YMD timestamp with dot string value
		 * 
		 * @param p_o_value					local date time value
		 * @param p_i_length				set length for DMY timestamp with dot string value
		 * @return							YMD timestamp with dot string value
		 * @throws ClassCastException		cannot cast parameter to local date time type
		 */
		public static String TransposeLocalDateTime_yyyymmddhhiiss_Dot(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalDateTime_any(p_o_value, p_i_length, "yyyy.MM.dd HH:mm:ss");
		}
	
		/**
		 * Transpose YMD timestamp with slash string value to local date time type
		 * 
		 * @param p_s_value					YMD timestamp with slash string value
		 * @return							local date time type
		 * @throws ClassCastException		cannot cast YMD timestamp with slash string value to local date time type
		 */
		public static Object TransposeLocalDateTime_yyyymmddhhiiss_Slash(String p_s_value) throws ClassCastException {
			return TransposeLocalDateTime_any(p_s_value);
		}
		
		/**
		 * Transpose local date time value to YMD timestamp with slash string value
		 * 
		 * @param p_o_value					local date time value
		 * @param p_i_length				set length for DMY timestamp with slash string value
		 * @return							YMD timestamp with slash string value
		 * @throws ClassCastException		cannot cast parameter to local date time type
		 */
		public static String TransposeLocalDateTime_yyyymmddhhiiss_Slash(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalDateTime_any(p_o_value, p_i_length, "yyyy/MM/dd HH:mm:ss");
		}
		
		/**
		 * Transpose MDY timestamp with dot string value to local date time type
		 * 
		 * @param p_s_value					MDY timestamp with dot string value
		 * @return							local date time type
		 * @throws ClassCastException		cannot cast MDY timestamp with dot string value to local date time type
		 */
		public static Object TransposeLocalDateTime_mmddyyyyhhiiss_Dot(String p_s_value) throws ClassCastException {
			/* to support MDY we must switch month and day value */
			String s_month = p_s_value.substring(0, 2);
			String s_day = p_s_value.substring(3, 5);
			p_s_value = s_day + "." + s_month + p_s_value.substring(5);
			
			return TransposeLocalDateTime_any(p_s_value);
		}
		
		/**
		 * Transpose local date time value to MDY timestamp with dot string value
		 * 
		 * @param p_o_value					local date time value
		 * @param p_i_length				set length for MDY timestamp with dot string value
		 * @return							MDY timestamp with dot string value
		 * @throws ClassCastException		cannot cast parameter to local date time type
		 */
		public static String TransposeLocalDateTime_mmddyyyyhhiiss_Dot(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalDateTime_any(p_o_value, p_i_length, "MM.dd.yyyy HH:mm:ss");
		}
	
		/**
		 * Transpose MDY timestamp with slash string value to local date time type
		 * 
		 * @param p_s_value					MDY timestamp with slash string value
		 * @return							local date time type
		 * @throws ClassCastException		cannot cast MDY timestamp with slash string value to local date time type
		 */
		public static Object TransposeLocalDateTime_mmddyyyyhhiiss_Slash(String p_s_value) throws ClassCastException {
			/* to support MDY we must switch month and day value */
			String s_month = p_s_value.substring(0, 2);
			String s_day = p_s_value.substring(3, 5);
			p_s_value = s_day + "/" + s_month + p_s_value.substring(5);
			
			return TransposeLocalDateTime_any(p_s_value);
		}
		
		/**
		 * Transpose local date time value to MDY timestamp with slash string value
		 * 
		 * @param p_o_value					local date time value
		 * @param p_i_length				set length for MDY timestamp with slash string value
		 * @return							MDY timestamp with slash string value
		 * @throws ClassCastException		cannot cast parameter to local date time type
		 */
		public static String TransposeLocalDateTime_mmddyyyyhhiiss_Slash(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalDateTime_any(p_o_value, p_i_length, "MM/dd/yyyy HH:mm:ss");
		}
	}
	
	/**
	 * java.time.LocalDate transpose methods
	 */
	public class LocalDate {
		/** 
		 * empty constructor
		 */
		public LocalDate() {
			
		}
				
		/**
		 * Transpose timestamp string value to local date type
		 * 
		 * @param p_s_value					timestamp string value
		 * @return							local date type
		 * @throws ClassCastException		cannot cast timestamp string value to local date type
		 */
		private static Object TransposeLocalDate_any(String p_s_value) throws ClassCastException {
			try {
				/* return string as local date object */
				return net.forestany.forestj.lib.Helper.fromDateString(p_s_value);
			} catch (java.time.DateTimeException o_exc) {
				throw new ClassCastException("Value '" + p_s_value + "' cannot be cast to timestamp local date: " + o_exc);
			}
		}
		
		/**
		 * Transpose local date value to timestamp string value
		 * 
		 * @param p_o_value					local date value
		 * @param p_i_length				set length for timestamp string value
		 * @param p_s_format				format string for local date parser
		 * @return							timestamp string value
		 * @throws ClassCastException		cannot cast parameter to local date type
		 */
		private static String TransposeLocalDate_any(Object p_o_value, int p_i_length, String p_s_format) throws ClassCastException {
			/* if parameter is null, return empty value string */
			if (p_o_value == null) {
				/* use zero as parameter character for these specific formats */
				if ( (p_s_format == "yyyyMMddHHmmss") || (p_s_format == "yyyyMMdd") || (p_s_format == "yyyyMMdd") || (p_s_format == "HHmmss") || (p_s_format == "HHmm") ) {
					return EmptyValue("0", p_i_length);
				} else {
					return EmptyValue(" ", p_i_length);
				}
			}
			
			/* check if parameter is a local date */
			if (p_o_value instanceof java.time.LocalDate) {
				java.time.format.DateTimeFormatter o_dateTimeFormatter;
				
				try {
					o_dateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern(p_s_format);
				} catch (Exception o_exc) {
					throw new ClassCastException("Illegal format parameter '" + p_s_format + "' - " + o_exc);
				}
				
				/* create timestamp string */
				String s_foo = o_dateTimeFormatter.format((java.time.LocalDate)p_o_value);
				
				/* return timestamp string */
				return s_foo.substring(0, p_i_length);
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not a local date");
			}
		}
		
		/**
		 * Transpose date string value to local date type
		 * 
		 * @param p_s_value					date string value
		 * @return							local date type
		 * @throws ClassCastException		cannot cast date string value to local date type
		 */
		public static Object TransposeLocalDate_yyyymmdd(String p_s_value) throws ClassCastException {
			return TransposeLocalDate_any(p_s_value);
		}
		
		/**
		 * Transpose local date value to date string value
		 * 
		 * @param p_o_value					local date value
		 * @param p_i_length				set length for date string value
		 * @return							date string value
		 * @throws ClassCastException		cannot cast parameter to local date type
		 */
		public static String TransposeLocalDate_yyyymmdd(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalDate_any(p_o_value, p_i_length, "yyyyMMdd");
		}
		
		/**
		 * Transpose date string value to local date type
		 * 
		 * @param p_s_value					date string value
		 * @return							local date type
		 * @throws ClassCastException		cannot cast date string value to local date type
		 */
		public static Object TransposeLocalDate_yyyymmdd_ISO(String p_s_value) throws ClassCastException {
			return TransposeLocalDate_any(p_s_value);
		}
		
		/**
		 * Transpose local date value to date string value
		 * 
		 * @param p_o_value					local date value
		 * @param p_i_length				set length for date string value
		 * @return							date string value
		 * @throws ClassCastException		cannot cast parameter to local date type
		 */
		public static String TransposeLocalDate_yyyymmdd_ISO(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalDate_any(p_o_value, p_i_length, "yyyy-MM-dd");
		}
		
		/**
		 * Transpose DMY date with dot string value to local date type
		 * 
		 * @param p_s_value					DMY date with dot string value
		 * @return							local date type
		 * @throws ClassCastException		cannot cast DMY date with dot string value to local date type
		 */
		public static Object TransposeLocalDate_ddmmyyyy_Dot(String p_s_value) throws ClassCastException {
			return TransposeLocalDate_any(p_s_value);
		}
		
		/**
		 * Transpose local date value to DMY date with dot string value
		 * 
		 * @param p_o_value					local date value
		 * @param p_i_length				set length for DMY date with dot string value
		 * @return							DMY date with dot string value
		 * @throws ClassCastException		cannot cast parameter to local date type
		 */
		public static String TransposeLocalDate_ddmmyyyy_Dot(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalDate_any(p_o_value, p_i_length, "dd.MM.yyyy");
		}
	
		/**
		 * Transpose DMY date with slash string value to local date type
		 * 
		 * @param p_s_value					DMY date with slash string value
		 * @return							local date type
		 * @throws ClassCastException		cannot cast DMY date with slash string value to local date type
		 */
		public static Object TransposeLocalDate_ddmmyyyy_Slash(String p_s_value) throws ClassCastException {
			return TransposeLocalDate_any(p_s_value);
		}
		
		/**
		 * Transpose local date value to DMY date with slash string value
		 * 
		 * @param p_o_value					local date value
		 * @param p_i_length				set length for DMY date with slash string value
		 * @return							DMY date with slash string value
		 * @throws ClassCastException		cannot cast parameter to local date type
		 */
		public static String TransposeLocalDate_ddmmyyyy_Slash(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalDate_any(p_o_value, p_i_length, "dd/MM/yyyy");
		}
		
		/**
		 * Transpose YMD date with dot string value to local date type
		 * 
		 * @param p_s_value					YMD date with dot string value
		 * @return							local date type
		 * @throws ClassCastException		cannot cast YMD date with dot string value to local date type
		 */
		public static Object TransposeLocalDate_yyyymmdd_Dot(String p_s_value) throws ClassCastException {
			return TransposeLocalDate_any(p_s_value);
		}
		
		/**
		 * Transpose local date value to YMD date with dot string value
		 * 
		 * @param p_o_value					local date value
		 * @param p_i_length				set length for DMY date with dot string value
		 * @return							YMD date with dot string value
		 * @throws ClassCastException		cannot cast parameter to local date type
		 */
		public static String TransposeLocalDate_yyyymmdd_Dot(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalDate_any(p_o_value, p_i_length, "yyyy.MM.dd");
		}
	
		/**
		 * Transpose YMD date with slash string value to local date type
		 * 
		 * @param p_s_value					YMD date with slash string value
		 * @return							local date type
		 * @throws ClassCastException		cannot cast YMD date with slash string value to local date type
		 */
		public static Object TransposeLocalDate_yyyymmdd_Slash(String p_s_value) throws ClassCastException {
			return TransposeLocalDate_any(p_s_value);
		}
		
		/**
		 * Transpose local date value to YMD date with slash string value
		 * 
		 * @param p_o_value					local date value
		 * @param p_i_length				set length for DMY date with slash string value
		 * @return							YMD date with slash string value
		 * @throws ClassCastException		cannot cast parameter to local date type
		 */
		public static String TransposeLocalDate_yyyymmdd_Slash(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalDate_any(p_o_value, p_i_length, "yyyy/MM/dd");
		}
		
		/**
		 * Transpose MDY date with dot string value to local date type
		 * 
		 * @param p_s_value					MDY date with dot string value
		 * @return							local date type
		 * @throws ClassCastException		cannot cast MDY date with dot string value to local date type
		 */
		public static Object TransposeLocalDate_mmddyyyy_Dot(String p_s_value) throws ClassCastException {
			/* to support MDY we must switch month and day value */
			String s_month = p_s_value.substring(0, 2);
			String s_day = p_s_value.substring(3, 5);
			p_s_value = s_day + "." + s_month + p_s_value.substring(5);
			
			return TransposeLocalDate_any(p_s_value);
		}
		
		/**
		 * Transpose local date value to MDY date with dot string value
		 * 
		 * @param p_o_value					local date value
		 * @param p_i_length				set length for MDY date with dot string value
		 * @return							MDY date with dot string value
		 * @throws ClassCastException		cannot cast parameter to local date type
		 */
		public static String TransposeLocalDate_mmddyyyy_Dot(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalDate_any(p_o_value, p_i_length, "MM.dd.yyyy");
		}
	
		/**
		 * Transpose MDY date with slash string value to local date type
		 * 
		 * @param p_s_value					MDY date with slash string value
		 * @return							local date type
		 * @throws ClassCastException		cannot cast MDY date with slash string value to local date type
		 */
		public static Object TransposeLocalDate_mmddyyyy_Slash(String p_s_value) throws ClassCastException {
			/* to support MDY we must switch month and day value */
			String s_month = p_s_value.substring(0, 2);
			String s_day = p_s_value.substring(3, 5);
			p_s_value = s_day + "/" + s_month + p_s_value.substring(5);
			
			return TransposeLocalDate_any(p_s_value);
		}
		
		/**
		 * Transpose local date value to MDY date with slash string value
		 * 
		 * @param p_o_value					local date value
		 * @param p_i_length				set length for MDY date with slash string value
		 * @return							MDY date with slash string value
		 * @throws ClassCastException		cannot cast parameter to local date type
		 */
		public static String TransposeLocalDate_mmddyyyy_Slash(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalDate_any(p_o_value, p_i_length, "MM/dd/yyyy");
		}
	}
	
	/**
	 * java.time.LocalTime transpose methods
	 */
	public class LocalTime {
		/** 
		 * empty constructor
		 */
		public LocalTime() {
			
		}
		
		/**
		 * Transpose timestamp string value to local time type
		 * 
		 * @param p_s_value					timestamp string value
		 * @return							local time type
		 * @throws ClassCastException		cannot cast timestamp string value to local time type
		 */
		private static Object TransposeLocalTime_any(String p_s_value) throws ClassCastException {
			try {
				/* return string as local time object */
				return net.forestany.forestj.lib.Helper.fromTimeString(p_s_value);
			} catch (java.time.DateTimeException o_exc) {
				throw new ClassCastException("Value '" + p_s_value + "' cannot be cast to timestamp local time: " + o_exc);
			}
		}
		
		/**
		 * Transpose local time value to timestamp string value
		 * 
		 * @param p_o_value					local time value
		 * @param p_i_length				set length for timestamp string value
		 * @param p_s_format				format string for local time parser
		 * @return							timestamp string value
		 * @throws ClassCastException		cannot cast parameter to local time type
		 */
		private static String TransposeLocalTime_any(Object p_o_value, int p_i_length, String p_s_format) throws ClassCastException {
			/* if parameter is null, return empty value string */
			if (p_o_value == null) {
				/* use zero as parameter character for these specific formats */
				if ( (p_s_format == "yyyyMMddHHmmss") || (p_s_format == "yyyyMMdd") || (p_s_format == "yyyyMMdd") || (p_s_format == "HHmmss") || (p_s_format == "HHmm") ) {
					return EmptyValue("0", p_i_length);
				} else {
					return EmptyValue(" ", p_i_length);
				}
			}
			
			/* check if parameter is a local time */
			if (p_o_value instanceof java.time.LocalTime) {
				java.time.format.DateTimeFormatter o_dateTimeFormatter;
				
				try {
					o_dateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern(p_s_format);
				} catch (Exception o_exc) {
					throw new ClassCastException("Illegal format parameter '" + p_s_format + "' - " + o_exc);
				}
				
				/* create timestamp string */
				String s_foo = o_dateTimeFormatter.format((java.time.LocalTime)p_o_value);
				
				/* return timestamp string */
				return s_foo.substring(0, p_i_length);
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not a local time");
			}
		}
		
		/**
		 * Transpose time string value to local time type
		 * 
		 * @param p_s_value					time string value
		 * @return							local time type
		 * @throws ClassCastException		cannot cast time string value to local time type
		 */
		public static Object TransposeLocalTime_hhiiss(String p_s_value) throws ClassCastException {
			return TransposeLocalTime_any(p_s_value);
		}
		
		/**
		 * Transpose local time value to time string value
		 * 
		 * @param p_o_value					local time value
		 * @param p_i_length				set length for time string value
		 * @return							time string value
		 * @throws ClassCastException		cannot cast parameter to local time type
		 */
		public static String TransposeLocalTime_hhiiss(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalTime_any(p_o_value, p_i_length, "HHmmss");
		}
		
		/**
		 * Transpose time string value to local time type
		 * 
		 * @param p_s_value					time string value
		 * @return							local time type
		 * @throws ClassCastException		cannot cast time string value to local time type
		 */
		public static Object TransposeLocalTime_hhii(String p_s_value) throws ClassCastException {
			return TransposeLocalTime_any(p_s_value);
		}
		
		/**
		 * Transpose local time value to time string value
		 * 
		 * @param p_o_value					local time value
		 * @param p_i_length				set length for time string value
		 * @return							time string value
		 * @throws ClassCastException		cannot cast parameter to local time type
		 */
		public static String TransposeLocalTime_hhii(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalTime_any(p_o_value, p_i_length, "HHmm");
		}
		
		/**
		 * Transpose time string with colon value to local time type
		 * 
		 * @param p_s_value					time string with colon value
		 * @return							local time type
		 * @throws ClassCastException		cannot cast time string with colon value to local time type
		 */
		public static Object TransposeLocalTime_hhiiss_Colon(String p_s_value) throws ClassCastException {
			return TransposeLocalTime_any(p_s_value);
		}
		
		/**
		 * Transpose local time value to time string with colon value
		 * 
		 * @param p_o_value					local time value
		 * @param p_i_length				set length for time string with colon value
		 * @return							time string with colon value
		 * @throws ClassCastException		cannot cast parameter to local time type
		 */
		public static String TransposeLocalTime_hhiiss_Colon(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalTime_any(p_o_value, p_i_length, "HH:mm:ss");
		}
		
		/**
		 * Transpose time string with colon value to local time type
		 * 
		 * @param p_s_value					time string with colon value
		 * @return							local time type
		 * @throws ClassCastException		cannot cast time string with colon value to local time type
		 */
		public static Object TransposeLocalTime_hhii_Colon(String p_s_value) throws ClassCastException {
			return TransposeLocalTime_any(p_s_value);
		}
		
		/**
		 * Transpose local time value to time string with colon value
		 * 
		 * @param p_o_value					local time value
		 * @param p_i_length				set length for time string with colon value
		 * @return							time string with colon value
		 * @throws ClassCastException		cannot cast parameter to local time type
		 */
		public static String TransposeLocalTime_hhii_Colon(Object p_o_value, int p_i_length) throws ClassCastException {
			return TransposeLocalTime_any(p_o_value, p_i_length, "HH:mm");
		}
	}
	
	/**
	 * Floating point numbers transpose methods
	 */
	public class FloatingPointNumbers {
		/** 
		 * empty constructor
		 */
		public FloatingPointNumbers() {
			
		}
		
		/* Float */

		/**
		 * Transpose float string value to float type
		 * 
		 * @param p_s_value							float string value
		 * @param p_i_positionDecimalSeparator		position of decimal separator	
		 * @return									float type
		 * @throws ClassCastException				cannot cast string value to float type
		 */
		public static Object TransposeFloat(String p_s_value, int p_i_positionDecimalSeparator) throws ClassCastException {
			/* check if we have an optional position of decimal separator */
			if (p_i_positionDecimalSeparator > 0) {
				/* insert decimal separator */
				p_s_value = p_s_value.substring(0, p_i_positionDecimalSeparator) + "." + p_s_value.substring(p_i_positionDecimalSeparator);
			}
			
			/* check float value */
			if (!net.forestany.forestj.lib.Helper.isFloat(p_s_value)) {
				throw new ClassCastException("Value '" + p_s_value + "' cannot be cast to Float");
			}
			
			/* replace decimal separator */
			p_s_value = p_s_value.replace(',', '.');
			
			/* return float value */
			return Float.valueOf(p_s_value);
		}
		
		/**
		 * Transpose float value to float string value, using system separators
		 * 
		 * @param p_o_value						float value
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @return								float string value
		 * @throws ClassCastException			cannot cast parameter to float type
		 */
		public static String TransposeFloat(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits) throws ClassCastException {
			return TransposeFloat(p_o_value, p_i_amountDigits, p_i_amountFractionDigits, "$", "$");
		}
		
		/**
		 * Transpose float value to float string value, use '$' for decimal separator to use system settings, no grouping
		 * 
		 * @param p_o_value						float value
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @param p_s_decimalSeparator			string for decimal separator
		 * @return								float string value
		 * @throws ClassCastException			cannot cast parameter to float type
		 */
		public static String TransposeFloat(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits, String p_s_decimalSeparator) throws ClassCastException {
			return TransposeFloat(p_o_value, p_i_amountDigits, p_i_amountFractionDigits, p_s_decimalSeparator, null);
		}
		
		/**
		 * Transpose float value to float string value, use '$' for decimal and group separator to use system separators
		 * 
		 * @param p_o_value						float value
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @param p_s_decimalSeparator			string for decimal separator
		 * @param p_s_groupSeparator			string for group separator
		 * @return								float string value
		 * @throws ClassCastException			cannot cast parameter to float type
		 */
		public static String TransposeFloat(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits, String p_s_decimalSeparator, String p_s_groupSeparator) throws ClassCastException {
			/* if parameter is null, use object value as 0 */
			if (p_o_value == null) {
				p_o_value = (Object)0.f;
			}
			
			/* check if parameter is a float */
			if (p_o_value instanceof Float) {
				/* get decimal format from current system */
				java.text.DecimalFormatSymbols o_decimalFormatSymbols = new java.text.DecimalFormatSymbols(java.util.Locale.getDefault());
				
				/* overwrite decimal separator if it is not empty and not set to '$' */
				if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_decimalSeparator)) && (p_s_decimalSeparator.charAt(0) != '$') ) {
					o_decimalFormatSymbols.setDecimalSeparator(p_s_decimalSeparator.charAt(0));
				}
				
				/* overwrite group separator if it is not empty and not set to '$' */
				if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_groupSeparator)) && (p_s_groupSeparator.charAt(0) != '$') ) {
					o_decimalFormatSymbols.setGroupingSeparator(p_s_groupSeparator.charAt(0));
				}
				
				/* create decimal format with decimal symbols and set amount of digits and fraction digits */
				java.text.DecimalFormat o_decimalFormat = new java.text.DecimalFormat("###,###,###,###,###.##", o_decimalFormatSymbols);
				o_decimalFormat.setMinimumIntegerDigits(p_i_amountDigits);
				o_decimalFormat.setMinimumFractionDigits(p_i_amountFractionDigits);
				
				/* set rounding mode */
				o_decimalFormat.setRoundingMode(java.math.RoundingMode.HALF_EVEN);
				
				/* no group separator available */
				if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_groupSeparator)) {
					/* disable grouping */
					o_decimalFormat.setGroupingUsed(false);
				}
				
				/* disable positive and negative prefixes, because we have TransposeFloatWithSign for that */
				o_decimalFormat.setPositivePrefix("");
				o_decimalFormat.setNegativePrefix("");
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_decimalSeparator)) {
					/* return float string, remove decimal separator, because we only want digits */
					return o_decimalFormat.format((float)p_o_value).replace(""+o_decimalFormatSymbols.getDecimalSeparator(), "");
				} else {
					/* return float string */
					return o_decimalFormat.format((float)p_o_value);
				}
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not a Float");
			}
		}
		
		/**
		 * Transpose float value to float string value, using system separators
		 * 
		 * @param p_o_value						float value
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @return								float string value
		 * @throws ClassCastException			cannot cast parameter to float type
		 */
		public static String TransposeFloatWithSign(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits) throws ClassCastException {
			return TransposeFloatWithSign(p_o_value, p_i_amountDigits, p_i_amountFractionDigits, "$", "$");
		}
		
		/**
		 * Transpose float value to float string value, use '$' for decimal separator to use system settings, no grouping
		 * 
		 * @param p_o_value						float value
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @param p_s_decimalSeparator			string for decimal separator
		 * @return								float string value
		 * @throws ClassCastException			cannot cast parameter to float type
		 */
		public static String TransposeFloatWithSign(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits, String p_s_decimalSeparator) throws ClassCastException {
			return TransposeFloatWithSign(p_o_value, p_i_amountDigits, p_i_amountFractionDigits, p_s_decimalSeparator, null);
		}
		
		/**
		 * Transpose float value to float string value, use '$' for decimal and group separator to use system separators
		 * 
		 * @param p_o_value						float value
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @param p_s_decimalSeparator			string for decimal separator
		 * @param p_s_groupSeparator			string for group separator
		 * @return								float string value
		 * @throws ClassCastException			cannot cast parameter to float type
		 */
		public static String TransposeFloatWithSign(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits, String p_s_decimalSeparator, String p_s_groupSeparator) throws ClassCastException {
			/* if parameter is null, use object value as 0 */
			if (p_o_value == null) {
				p_o_value = (Object)0.f;
			}
			
			/* check if parameter is a float */
			if (p_o_value instanceof Float) {
				/* get decimal format from current system */
				java.text.DecimalFormatSymbols o_decimalFormatSymbols = new java.text.DecimalFormatSymbols(java.util.Locale.getDefault());
				
				/* overwrite decimal separator if it is not empty and not set to '$' */
				if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_decimalSeparator)) && (p_s_decimalSeparator.charAt(0) != '$') ) {
					o_decimalFormatSymbols.setDecimalSeparator(p_s_decimalSeparator.charAt(0));
				}
				
				/* overwrite group separator if it is not empty and not set to '$' */
				if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_groupSeparator)) && (p_s_groupSeparator.charAt(0) != '$') ) {
					o_decimalFormatSymbols.setGroupingSeparator(p_s_groupSeparator.charAt(0));
				}
				
				/* create decimal format with decimal symbols and set amount of digits and fraction digits */
				java.text.DecimalFormat o_decimalFormat = new java.text.DecimalFormat("###,###,###,###,###.##", o_decimalFormatSymbols);
				o_decimalFormat.setMinimumIntegerDigits(p_i_amountDigits);
				o_decimalFormat.setMinimumFractionDigits(p_i_amountFractionDigits);
				
				/* set rounding mode */
				o_decimalFormat.setRoundingMode(java.math.RoundingMode.HALF_EVEN);
				
				/* no group separator available */
				if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_groupSeparator)) {
					/* disable grouping */
					o_decimalFormat.setGroupingUsed(false);
				}
				
				/* set positive and negative prefixes */
				o_decimalFormat.setPositivePrefix("+");
				o_decimalFormat.setNegativePrefix("-");
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_decimalSeparator)) {
					/* return float string, remove decimal separator, because we only want digits */
					return o_decimalFormat.format((float)p_o_value).replace(""+o_decimalFormatSymbols.getDecimalSeparator(), "");
				} else {
					/* return float string */
					return o_decimalFormat.format((float)p_o_value);
				}
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not a Float");
			}
		}
		
		/* Double */
		
		/**
		 * Transpose double string value to double type
		 * 
		 * @param p_s_value							double string value
		 * @param p_i_positionDecimalSeparator		position of decimal separator	
		 * @return									double type
		 * @throws ClassCastException				cannot cast string value to double type
		 */
		public static Object TransposeDouble(String p_s_value, int p_i_positionDecimalSeparator) throws ClassCastException {
			/* check if we have an optional position of decimal separator */
			if (p_i_positionDecimalSeparator > 0) {
				/* insert decimal separator */
				p_s_value = p_s_value.substring(0, p_i_positionDecimalSeparator) + "." + p_s_value.substring(p_i_positionDecimalSeparator);
			}
			
			/* check double value */
			if (!net.forestany.forestj.lib.Helper.isDouble(p_s_value)) {
				throw new ClassCastException("Value '" + p_s_value + "' cannot be cast to Double");
			}
			
			/* replace decimal separator */
			p_s_value = p_s_value.replace(',', '.');
			
			/* return double value */
			return Double.valueOf(p_s_value);
		}
		
		/**
		 * Transpose double value to double string value, using system separators
		 * 
		 * @param p_o_value						double value
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @return								double string value
		 * @throws ClassCastException			cannot cast parameter to double type
		 */
		public static String TransposeDouble(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits) throws ClassCastException {
			return TransposeDouble(p_o_value, p_i_amountDigits, p_i_amountFractionDigits, "$", "$");
		}
		
		/**
		 * Transpose double value to double string value, use '$' for decimal separator to use system settings, no grouping
		 * 
		 * @param p_o_value						double value
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @param p_s_decimalSeparator			string for decimal separator
		 * @return								double string value
		 * @throws ClassCastException			cannot cast parameter to double type
		 */
		public static String TransposeDouble(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits, String p_s_decimalSeparator) throws ClassCastException {
			return TransposeDouble(p_o_value, p_i_amountDigits, p_i_amountFractionDigits, p_s_decimalSeparator, null);
		}
		
		/**
		 * Transpose double value to double string value, use '$' for decimal and group separator to use system separators
		 * 
		 * @param p_o_value						double value
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @param p_s_decimalSeparator			string for decimal separator
		 * @param p_s_groupSeparator			string for group separator
		 * @return								double string value
		 * @throws ClassCastException			cannot cast parameter to double type
		 */
		public static String TransposeDouble(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits, String p_s_decimalSeparator, String p_s_groupSeparator) throws ClassCastException {
			/* if parameter is null, use object value as 0 */
			if (p_o_value == null) {
				p_o_value = (Object)0.d;
			}
			
			/* check if parameter is a double */
			if (p_o_value instanceof Double) {
				/* get decimal format from current system */
				java.text.DecimalFormatSymbols o_decimalFormatSymbols = new java.text.DecimalFormatSymbols(java.util.Locale.getDefault());
				
				/* overwrite decimal separator if it is not empty and not set to '$' */
				if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_decimalSeparator)) && (p_s_decimalSeparator.charAt(0) != '$') ) {
					o_decimalFormatSymbols.setDecimalSeparator(p_s_decimalSeparator.charAt(0));
				}
				
				/* overwrite group separator if it is not empty and not set to '$' */
				if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_groupSeparator)) && (p_s_groupSeparator.charAt(0) != '$') ) {
					o_decimalFormatSymbols.setGroupingSeparator(p_s_groupSeparator.charAt(0));
				}
				
				/* create decimal format with decimal symbols and set amount of digits and fraction digits */
				java.text.DecimalFormat o_decimalFormat = new java.text.DecimalFormat("###,###,###,###,###.##", o_decimalFormatSymbols);
				o_decimalFormat.setMinimumIntegerDigits(p_i_amountDigits);
				o_decimalFormat.setMinimumFractionDigits(p_i_amountFractionDigits);
				
				/* set rounding mode */
				o_decimalFormat.setRoundingMode(java.math.RoundingMode.HALF_EVEN);
				
				/* no group separator available */
				if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_groupSeparator)) {
					/* disable grouping */
					o_decimalFormat.setGroupingUsed(false);
				}
				
				/* disable positive and negative prefixes, because we have TransposeDoubleWithSign for that */
				o_decimalFormat.setPositivePrefix("");
				o_decimalFormat.setNegativePrefix("");
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_decimalSeparator)) {
					/* return double string, remove decimal separator, because we only want digits */
					return o_decimalFormat.format((double)p_o_value).replace(""+o_decimalFormatSymbols.getDecimalSeparator(), "");
				} else {
					/* return double string */
					return o_decimalFormat.format((double)p_o_value);
				}
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not a Double");
			}
		}
		
		/**
		 * Transpose double value to double string value, using system separators
		 * 
		 * @param p_o_value						double value
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @return								double string value
		 * @throws ClassCastException			cannot cast parameter to double type
		 */
		public static String TransposeDoubleWithSign(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits) throws ClassCastException {
			return TransposeDoubleWithSign(p_o_value, p_i_amountDigits, p_i_amountFractionDigits, "$", "$");
		}
		
		/**
		 * Transpose double value to double string value, use '$' for decimal separator to use system settings, no grouping
		 * 
		 * @param p_o_value						double value
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @param p_s_decimalSeparator			string for decimal separator
		 * @return								double string value
		 * @throws ClassCastException			cannot cast parameter to double type
		 */
		public static String TransposeDoubleWithSign(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits, String p_s_decimalSeparator) throws ClassCastException {
			return TransposeDoubleWithSign(p_o_value, p_i_amountDigits, p_i_amountFractionDigits, p_s_decimalSeparator, null);
		}
		
		/**
		 * Transpose double value to double string value, use '$' for decimal and group separator to use system separators
		 * 
		 * @param p_o_value						double value
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @param p_s_decimalSeparator			string for decimal separator
		 * @param p_s_groupSeparator			string for group separator
		 * @return								double string value
		 * @throws ClassCastException			cannot cast parameter to double type
		 */
		public static String TransposeDoubleWithSign(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits, String p_s_decimalSeparator, String p_s_groupSeparator) throws ClassCastException {
			/* if parameter is null, use object value as 0 */
			if (p_o_value == null) {
				p_o_value = (Object)0.d;
			}
			
			/* check if parameter is a double */
			if (p_o_value instanceof Double) {
				/* get decimal format from current system */
				java.text.DecimalFormatSymbols o_decimalFormatSymbols = new java.text.DecimalFormatSymbols(java.util.Locale.getDefault());
				
				/* overwrite decimal separator if it is not empty and not set to '$' */
				if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_decimalSeparator)) && (p_s_decimalSeparator.charAt(0) != '$') ) {
					o_decimalFormatSymbols.setDecimalSeparator(p_s_decimalSeparator.charAt(0));
				}
				
				/* overwrite group separator if it is not empty and not set to '$' */
				if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_groupSeparator)) && (p_s_groupSeparator.charAt(0) != '$') ) {
					o_decimalFormatSymbols.setGroupingSeparator(p_s_groupSeparator.charAt(0));
				}
				
				/* create decimal format with decimal symbols and set amount of digits and fraction digits */
				java.text.DecimalFormat o_decimalFormat = new java.text.DecimalFormat("###,###,###,###,###.##", o_decimalFormatSymbols);
				o_decimalFormat.setMinimumIntegerDigits(p_i_amountDigits);
				o_decimalFormat.setMinimumFractionDigits(p_i_amountFractionDigits);
				
				/* set rounding mode */
				o_decimalFormat.setRoundingMode(java.math.RoundingMode.HALF_EVEN);
				
				/* no group separator available */
				if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_groupSeparator)) {
					/* disable grouping */
					o_decimalFormat.setGroupingUsed(false);
				}
				
				/* set positive and negative prefixes */
				o_decimalFormat.setPositivePrefix("+");
				o_decimalFormat.setNegativePrefix("-");
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_decimalSeparator)) {
					/* return double string, remove decimal separator, because we only want digits */
					return o_decimalFormat.format((double)p_o_value).replace(""+o_decimalFormatSymbols.getDecimalSeparator(), "");
				} else {
					/* return double string */
					return o_decimalFormat.format((double)p_o_value);
				}
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not a Double");
			}
		}
		
		/* BigDecimal */
		
		/**
		 * Transpose big decimal string value to big decimal type
		 * 
		 * @param p_s_value							big decimal string value
		 * @param p_i_positionDecimalSeparator		position of decimal separator	
		 * @return									big decimal type
		 * @throws ClassCastException				cannot cast string value to big decimal type
		 */
		public static Object TransposeBigDecimal(String p_s_value, int p_i_positionDecimalSeparator) throws ClassCastException {
			/* check if string value only has '0' characters with optional '+' or '-' sign at the start */
			if ( (p_s_value.matches("^[0]+$")) || (p_s_value.matches("^\\+[0]+$")) || (p_s_value.matches("^-[0]+$")) ) {
				/* use '0.0' as string value from here */
				p_s_value = "0.0";
			} else if (p_i_positionDecimalSeparator > 0) { /* check if we have an optional position of decimal separator */
				/* insert decimal separator */
				p_s_value = p_s_value.substring(0, p_i_positionDecimalSeparator) + "." + p_s_value.substring(p_i_positionDecimalSeparator);
			}
			
			/* check big decimal value */
			if (!net.forestany.forestj.lib.Helper.isDouble(p_s_value)) {
				throw new ClassCastException("Value '" + p_s_value + "' cannot be cast to BigDecimal");
			}
			
			/* replace decimal separator */
			p_s_value = p_s_value.replace(',', '.');
			
			/* return big decimal value */
			return new java.math.BigDecimal(p_s_value);
		}
		
		/**
		 * Transpose big decimal value to big decimal string value, using system separators
		 * 
		 * @param p_o_value						big decimal value
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @return								big decimal string value
		 * @throws ClassCastException			cannot cast parameter to big decimal type
		 */
		public static String TransposeBigDecimal(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits) throws ClassCastException {
			return TransposeBigDecimal(p_o_value, p_i_amountDigits, p_i_amountFractionDigits, "$", "$");
		}
		
		/**
		 * Transpose big decimal value to big decimal string value, use '$' for decimal separator to use system settings, no grouping
		 * 
		 * @param p_o_value						big decimal value
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @param p_s_decimalSeparator			string for decimal separator
		 * @return								big decimal string value
		 * @throws ClassCastException			cannot cast parameter to big decimal type
		 */
		public static String TransposeBigDecimal(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits, String p_s_decimalSeparator) throws ClassCastException {
			return TransposeBigDecimal(p_o_value, p_i_amountDigits, p_i_amountFractionDigits, p_s_decimalSeparator, null);
		}
		
		/**
		 * Transpose big decimal value to big decimal string value, use '$' for decimal and group separator to use system separators
		 * 
		 * @param p_o_value						big decimal value
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @param p_s_decimalSeparator			string for decimal separator
		 * @param p_s_groupSeparator			string for group separator
		 * @return								big decimal string value
		 * @throws ClassCastException			cannot cast parameter to big decimal type
		 */
		public static String TransposeBigDecimal(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits, String p_s_decimalSeparator, String p_s_groupSeparator) throws ClassCastException {
			/* if parameter is null, use object value as 0 */
			if (p_o_value == null) {
				p_o_value = (Object)new java.math.BigDecimal("0.0");
			}
			
			/* check if parameter is a big decimal */
			if (p_o_value instanceof java.math.BigDecimal) {
				/* get decimal format from current system */
				java.text.DecimalFormatSymbols o_decimalFormatSymbols = new java.text.DecimalFormatSymbols(java.util.Locale.getDefault());
				
				/* overwrite decimal separator if it is not empty and not set to '$' */
				if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_decimalSeparator)) && (p_s_decimalSeparator.charAt(0) != '$') ) {
					o_decimalFormatSymbols.setDecimalSeparator(p_s_decimalSeparator.charAt(0));
				}
				
				/* overwrite group separator if it is not empty and not set to '$' */
				if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_groupSeparator)) && (p_s_groupSeparator.charAt(0) != '$') ) {
					o_decimalFormatSymbols.setGroupingSeparator(p_s_groupSeparator.charAt(0));
				}
				
				/* create decimal format with decimal symbols and set amount of digits and fraction digits */
				java.text.DecimalFormat o_decimalFormat = new java.text.DecimalFormat("###,###,###,###,###.##", o_decimalFormatSymbols);
				o_decimalFormat.setMinimumIntegerDigits(p_i_amountDigits);
				o_decimalFormat.setMinimumFractionDigits(p_i_amountFractionDigits);
				
				/* set rounding mode */
				o_decimalFormat.setRoundingMode(java.math.RoundingMode.HALF_EVEN);
				
				/* no group separator available */
				if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_groupSeparator)) {
					/* disable grouping */
					o_decimalFormat.setGroupingUsed(false);
				}
				
				/* disable positive and negative prefixes, because we have TransposeDoubleWithSign for that */
				o_decimalFormat.setPositivePrefix("");
				o_decimalFormat.setNegativePrefix("");
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_decimalSeparator)) {
					/* return big decimal string, remove decimal separator, because we only want digits */
					return o_decimalFormat.format(((java.math.BigDecimal)p_o_value).setScale(o_decimalFormat.getMinimumFractionDigits(), o_decimalFormat.getRoundingMode())).replace(""+o_decimalFormatSymbols.getDecimalSeparator(), "");
				} else {
					/* return big decimal string */
					return o_decimalFormat.format(((java.math.BigDecimal)p_o_value).setScale(o_decimalFormat.getMinimumFractionDigits(), o_decimalFormat.getRoundingMode()));
				}
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not a BigDecimal");
			}
		}
		
		/**
		 * Transpose big decimal value to big decimal string value, using system separators
		 * 
		 * @param p_o_value						big decimal value
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @return								big decimal string value
		 * @throws ClassCastException			cannot cast parameter to big decimal type
		 */
		public static String TransposeBigDecimalWithSign(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits) throws ClassCastException {
			return TransposeBigDecimalWithSign(p_o_value, p_i_amountDigits, p_i_amountFractionDigits, "$", "$");
		}
		
		/**
		 * Transpose big decimal value to big decimal string value, use '$' for decimal separator to use system settings, no grouping
		 * 
		 * @param p_o_value						big decimal value
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @param p_s_decimalSeparator			string for decimal separator
		 * @return								big decimal string value
		 * @throws ClassCastException			cannot cast parameter to big decimal type
		 */
		public static String TransposeBigDecimalWithSign(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits, String p_s_decimalSeparator) throws ClassCastException {
			return TransposeBigDecimalWithSign(p_o_value, p_i_amountDigits, p_i_amountFractionDigits, p_s_decimalSeparator, null);
		}
		
		/**
		 * Transpose big decimal value to big decimal string value, use '$' for decimal and group separator to use system separators
		 * 
		 * @param p_o_value						big decimal value
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @param p_s_decimalSeparator			string for decimal separator
		 * @param p_s_groupSeparator			string for group separator
		 * @return								big decimal string value
		 * @throws ClassCastException			cannot cast parameter to big decimal type
		 */
		public static String TransposeBigDecimalWithSign(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits, String p_s_decimalSeparator, String p_s_groupSeparator) throws ClassCastException {
			/* if parameter is null, use object value as 0 */
			if (p_o_value == null) {
				p_o_value = (Object)new java.math.BigDecimal("0.0");
			}
			
			/* check if parameter is a big decimal */
			if (p_o_value instanceof java.math.BigDecimal) {
				/* get decimal format from current system */
				java.text.DecimalFormatSymbols o_decimalFormatSymbols = new java.text.DecimalFormatSymbols(java.util.Locale.getDefault());
				
				/* overwrite decimal separator if it is not empty and not set to '$' */
				if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_decimalSeparator)) && (p_s_decimalSeparator.charAt(0) != '$') ) {
					o_decimalFormatSymbols.setDecimalSeparator(p_s_decimalSeparator.charAt(0));
				}
				
				/* overwrite group separator if it is not empty and not set to '$' */
				if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_groupSeparator)) && (p_s_groupSeparator.charAt(0) != '$') ) {
					o_decimalFormatSymbols.setGroupingSeparator(p_s_groupSeparator.charAt(0));
				}
				
				/* create decimal format with decimal symbols and set amount of digits and fraction digits */
				java.text.DecimalFormat o_decimalFormat = new java.text.DecimalFormat("###,###,###,###,###.##", o_decimalFormatSymbols);
				o_decimalFormat.setMinimumIntegerDigits(p_i_amountDigits);
				o_decimalFormat.setMinimumFractionDigits(p_i_amountFractionDigits);
				
				/* set rounding mode */
				o_decimalFormat.setRoundingMode(java.math.RoundingMode.HALF_EVEN);
				
				/* no group separator available */
				if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_groupSeparator)) {
					/* disable grouping */
					o_decimalFormat.setGroupingUsed(false);
				}
				
				/* set positive and negative prefixes */
				o_decimalFormat.setPositivePrefix("+");
				o_decimalFormat.setNegativePrefix("-");
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_decimalSeparator)) {
					/* return big decimal string, remove decimal separator, because we only want digits */
					return o_decimalFormat.format(((java.math.BigDecimal)p_o_value).setScale(o_decimalFormat.getMinimumFractionDigits(), o_decimalFormat.getRoundingMode())).replace(""+o_decimalFormatSymbols.getDecimalSeparator(), "");
				} else {
					/* return big decimal string */
					return o_decimalFormat.format(((java.math.BigDecimal)p_o_value).setScale(o_decimalFormat.getMinimumFractionDigits(), o_decimalFormat.getRoundingMode()));
				}
			} else {
				throw new ClassCastException("Parameter '" + p_o_value + "' is not a BigDecimal");
			}
		}
	}
}
