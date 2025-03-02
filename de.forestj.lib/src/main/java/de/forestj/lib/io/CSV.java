package de.forestj.lib.io;

/**
 * 
 * CSV class to encode and decode java objects to csv files.
 * line break, delimiter and array delimiter changeable.
 * access to object fields can be directly on public fields or with public property methods (getXX setXX) on private fields.
 * NOTE: mostly only primitive types supported for encoding and decoding.
 *
 */
public class CSV {

	/* Fields */
	
	private String s_delimiter;
	private String s_arrayDelimiter;
	private String s_lineBreak;
	private boolean b_usePropertyMethods;
	private boolean b_returnArrayElementNullNotZero;
	private java.nio.charset.Charset o_charset;
	public static final java.util.List<String> a_allowedTypes = java.util.Arrays.asList("byte", "short", "int", "long", "float", "double", "char", "boolean", "string", "java.lang.String", "java.lang.Short", "java.lang.Byte", "java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double", "java.lang.Boolean", "java.lang.Character", "java.math.BigDecimal", "java.util.Date", "java.time.LocalDateTime", "java.time.LocalDate", "java.time.LocalTime");
	
	/* Properties */
	
	public String getDelimiter() {
		return this.s_delimiter;
	}
	
	public void setDelimiter(String p_s_value) throws IllegalArgumentException {
		if (p_s_value.length() != 1) {
			throw new IllegalArgumentException("Delimiter must have length(1) - ['" + p_s_value + "' -> length(" + p_s_value.length() + ")]");
		}
		
		this.s_delimiter = p_s_value;
		de.forestj.lib.Global.ilogConfig("updated delimiter to '" + this.s_delimiter + "'");
	}
	
	public String getArrayDelimiter() {
		return this.s_arrayDelimiter;
	}
	
	public void setArrayDelimiter(String p_s_value) throws IllegalArgumentException {
		if (p_s_value.length() != 1) {
			throw new IllegalArgumentException("ArrayDelimiter must have length(1) - ['" + p_s_value + "' -> length(" + p_s_value.length() + ")]");
		}
		
		this.s_arrayDelimiter = p_s_value;
		de.forestj.lib.Global.ilogConfig("updated array delimiter to '" + this.s_arrayDelimiter + "'");
	}
	
	public String getLineBreak() {
		return this.s_lineBreak;
	}
	
	public void setLineBreak(String p_s_lineBreak) throws IllegalArgumentException {
		if (p_s_lineBreak.length() < 1) {
			throw new IllegalArgumentException("Line break must have at least a length of 1, but length is '" + p_s_lineBreak.length() + "'");
		}
		
		this.s_lineBreak = p_s_lineBreak;
		de.forestj.lib.Global.ilogConfig("updated line break to [" + de.forestj.lib.Helper.bytesToHexString(this.s_lineBreak.getBytes(), true) + "]");
	}
	
	public boolean getUsePropertyMethods() {
		return this.b_usePropertyMethods;
	}
	
	/**
	 * Determine if get and set property methods shall be used handling Objects.
	 * convention: get'exactFieldName' and set'exactFieldName' must be used.
	 */
	public void setUsePropertyMethods(boolean p_b_value) {
		this.b_usePropertyMethods = p_b_value;
	}
	
	public boolean getReturnArrayElementNullNotZero() {
		return this.b_returnArrayElementNullNotZero;
	}
	
	/**
	 * Determine if you want to return empty values as null or 0
	 */
	public void setReturnArrayElementNullNotZero(boolean p_b_value) {
		this.b_returnArrayElementNullNotZero = p_b_value;
	}
	
	public void setCharset(java.nio.charset.Charset p_o_charset) throws NullPointerException {
		if (p_o_charset == null) {
			throw new NullPointerException("Charset parameter is null");
		}
		
		this.o_charset = p_o_charset;
		de.forestj.lib.Global.ilogConfig("updated charset to '" + this.o_charset.toString() + "'");
	}
	
	/* Methods */
	
	/**
	 * Constructor, setting field delimiter as ',' and array element delimiter as '~', standard charset UTF-8
	 * 
	 * @throws IllegalArgumentException invalid length of field or array element delimiter
	 */
	public CSV() throws Exception {
		this(",", "~");
	}
	
	/**
	 * Constructor, setting field delimiter as parameter and array element delimiter as '~', standard charset UTF-8
	 * 
	 * @param p_s_delimiter field delimiter e.g. ',' or ';'
	 * @throws IllegalArgumentException invalid length of field or array element delimiter
	 */
	public CSV(String p_s_delimiter) throws IllegalArgumentException {
		this(p_s_delimiter, "~");
	}
	
	/**
	 * Constructor, standard charset UTF-8
	 * 
	 * @param p_s_delimiter					field delimiter e.g. ',' or ';'
	 * @param p_s_arrayDelimiter			array element delimiter e.g. '~'
	 * @throws IllegalArgumentException		invalid length of field or array element delimiter
	 */
	public CSV(String p_s_delimiter, String p_s_arrayDelimiter) throws IllegalArgumentException {
		this.setDelimiter(p_s_delimiter);
		this.setArrayDelimiter(p_s_arrayDelimiter);
		this.setUsePropertyMethods(false);
		this.setLineBreak(de.forestj.lib.io.File.NEWLINE);
		this.setReturnArrayElementNullNotZero(false);
		this.setCharset(java.nio.charset.Charset.forName("UTF-8"));
	}
	
	/**
	 * Method to encode a object to a csv content string
	 * 
	 * @param p_o_object	any object which will be encoded to a csv content string
	 * supported types:
	 * byte, short, int, long, float, double, char, boolean, string,
	 * java.lang.String, java.lang.Short, java.lang.Byte, java.lang.Integer,
	 * java.lang.Long, java.lang.Float, java.lang.Double, java.lang.Boolean, 
	 * java.lang.Character, java.math.BigDecimal, java.util.Date,
	 * java.time.LocalDateTime, java.time.LocalDate, java.time.LocalTime
	 * @return				String
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalArgumentException		invalid type parameter
	 */
	public String printCSV(Object p_o_object) throws java.io.IOException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, java.lang.reflect.InvocationTargetException, IllegalArgumentException {
		return this.printCSV(p_o_object, true);
	}
	
	/**
	 * Method to encode a object to a csv content string
	 * 
	 * @param p_o_object	any object which will be encoded to a csv content string
	 * supported types:
	 * byte, short, int, long, float, double, char, boolean, string,
	 * java.lang.String, java.lang.Short, java.lang.Byte, java.lang.Integer,
	 * java.lang.Long, java.lang.Float, java.lang.Double, java.lang.Boolean, 
	 * java.lang.Character, java.math.BigDecimal, java.util.Date,
	 * java.time.LocalDateTime, java.time.LocalDate, java.time.LocalTime
	 * @param p_b_generateHeaderLine		true - add first line as header line, false - no header line
	 * @return				String
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalArgumentException		invalid type parameter
	 */
	public String printCSV(Object p_o_object, boolean p_b_generateHeaderLine) throws java.io.IOException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, java.lang.reflect.InvocationTargetException, IllegalArgumentException {
		String s_csv = "";
		
		/* check if parameter object is an array list */
		if (p_o_object instanceof java.util.List) {
			/* cast parameter object as list with unknown generic type */
			java.util.List<?> a_objects = (java.util.List<?>)p_o_object;
			
			/* only execute if we have more than one array element */
			if (a_objects.size() > 0) {
				/* add header line */
				if (p_b_generateHeaderLine) {
					s_csv += this.generateHeaderLine(a_objects.get(0));
				}
				
				/* iterate objects in list and encode data to csv recursively */
				for (int i = 0; i < a_objects.size(); i++) {
					/* skip fields which are also instanceof java.util.List to avoid recursion overflow */
					if (a_objects.get(i) instanceof java.util.List) {
						continue;
					}
					
					/* encode array object element */
					s_csv += this.printCSV(a_objects.get(i), false);
				}
				
				/* remove last line break in csv file */
				s_csv = s_csv.substring(0, s_csv.length() - this.s_lineBreak.length());
			}
		} else { /* handle parameter object as standard object */
			/* add header line */
			if (p_b_generateHeaderLine) {
				s_csv += this.generateHeaderLine(p_o_object);
			}
			
			s_csv += this.encodeObject(p_o_object);
		}
		
		/* return csv string and add line break */
		return s_csv + this.s_lineBreak;
	}
	
	/* encoding data to csv */
	
	/**
	 * Methods to encode an object to a csv file, printing header line, not overwriting existing csv file
	 * 
	 * @param p_o_object		object parameter which will be encoded
	 * supported types:
	 * byte, short, int, long, float, double, char, boolean, string,
	 * java.lang.String, java.lang.Short, java.lang.Byte, java.lang.Integer,
	 * java.lang.Long, java.lang.Float, java.lang.Double, java.lang.Boolean, 
	 * java.lang.Character, java.math.BigDecimal, java.util.Date,
	 * java.time.LocalDateTime, java.time.LocalDate, java.time.LocalTime
	 * @param p_s_csvFile		full path to csv file
	 * @return					de.forestj.lib.io.File object
	 * @throws java.io.IOException			exception creating csv file or replacing content of csv file
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalArgumentException		invalid type parameter
	 */
	public File encodeCSV(Object p_o_object, String p_s_csvFile) throws java.io.IOException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, java.lang.reflect.InvocationTargetException, IllegalArgumentException {
		return this.encodeCSV(p_o_object, p_s_csvFile, true, false);
	}
	
	/**
	 * Methods to encode an object to a csv file, not overwriting existing csv file
	 * 
	 * @param p_o_object		object parameter which will be encoded
	 * supported types:
	 * byte, short, int, long, float, double, char, boolean, string,
	 * java.lang.String, java.lang.Short, java.lang.Byte, java.lang.Integer,
	 * java.lang.Long, java.lang.Float, java.lang.Double, java.lang.Boolean, 
	 * java.lang.Character, java.math.BigDecimal, java.util.Date,
	 * java.time.LocalDateTime, java.time.LocalDate, java.time.LocalTime
	 * @param p_s_csvFile		full path to csv file
	 * @param p_b_printHeader	true - add first line as header line, false - no header line
	 * @return					de.forestj.lib.io.File object
	 * @throws java.io.IOException			exception creating csv file or replacing content of csv file
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalArgumentException		invalid type parameter
	 */
	public File encodeCSV(Object p_o_object, String p_s_csvFile, boolean p_b_printHeader) throws java.io.IOException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, java.lang.reflect.InvocationTargetException, IllegalArgumentException {
		return this.encodeCSV(p_o_object, p_s_csvFile, p_b_printHeader, false);
	}
	
	/**
	 * Methods to encode an object to a csv file
	 * 
	 * @param p_o_object		object parameter which will be encoded
	 * supported types:
	 * byte, short, int, long, float, double, char, boolean, string,
	 * java.lang.String, java.lang.Short, java.lang.Byte, java.lang.Integer,
	 * java.lang.Long, java.lang.Float, java.lang.Double, java.lang.Boolean, 
	 * java.lang.Character, java.math.BigDecimal, java.util.Date,
	 * java.time.LocalDateTime, java.time.LocalDate, java.time.LocalTime
	 * @param p_s_csvFile		full path to csv file
	 * @param p_b_printHeader	true - add first line as header line, false - no header line
	 * @param p_b_overwrite		true - overwrite existing csv file, false - keep existing csv file
	 * @return					de.forestj.lib.io.File object
	 * @throws java.io.IOException			exception creating csv file or replacing content of csv file
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalArgumentException		invalid type parameter
	 */
	public File encodeCSV(Object p_o_object, String p_s_csvFile, boolean p_b_printHeader, boolean p_b_overwrite) throws java.io.IOException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, java.lang.reflect.InvocationTargetException, IllegalArgumentException {
		String s_csv = "";
		
		/* check if parameter object is an array list */
		if (p_o_object instanceof java.util.List) {
			/* cast current object as list with unknown generic type */
			java.util.List<?> a_objects = (java.util.List<?>)p_o_object;
			
			/* only execute if we have more than one array element */
			if (a_objects.size() > 0) {
				/* add header line */
				if (p_b_printHeader) {
					s_csv += this.generateHeaderLine(a_objects.get(0));
				}
				
				/* iterate objects in list and encode data to csv recursively */
				for (int i = 0; i < a_objects.size(); i++) {
					/* encode array object element and add line break */
					s_csv += this.encodeObject(a_objects.get(i)) + this.s_lineBreak;
				}
				
				/* remove last line break in csv file */
				s_csv = s_csv.substring(0, s_csv.length() - this.s_lineBreak.length());
			}
		} else {
			/* add header line */
			if (p_b_printHeader) {
				s_csv += this.generateHeaderLine(p_o_object);
			}
			
			/* encode object */
			s_csv += this.encodeObject(p_o_object);
		}
		
		/* if file does not exist we must create an new file */
		if (!File.exists(p_s_csvFile)) {
			if (p_b_overwrite) {
				p_b_overwrite = false;
			}
		}
		
		/* open (new) file */
		de.forestj.lib.io.File o_file = new de.forestj.lib.io.File(p_s_csvFile, this.o_charset, !p_b_overwrite, de.forestj.lib.io.File.NEWLINE);
		
		/* save csv encoded data into file */
		o_file.replaceContent(s_csv);
		
		/* return file object */
		return o_file;
	}
	
	/**
	 * Generate header line for csv file
	 * 
	 * @param p_o_object	object parameter to get object field names for header fields
	 * supported types:
	 * byte, short, int, long, float, double, char, boolean, string,
	 * java.lang.String, java.lang.Short, java.lang.Byte, java.lang.Integer,
	 * java.lang.Long, java.lang.Float, java.lang.Double, java.lang.Boolean, 
	 * java.lang.Character, java.math.BigDecimal, java.util.Date,
	 * java.time.LocalDateTime, java.time.LocalDate, java.time.LocalTime
	 * @return				csv header line
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 */
	private String generateHeaderLine(Object p_o_object) throws NoSuchFieldException {
		String s_csv = "";
		
		/* iterate all fields of parameter object */
		for (java.lang.reflect.Field o_field : p_o_object.getClass().getDeclaredFields()) {
													de.forestj.lib.Global.ilogFiner("iterate all fields - field '" + o_field.getName() + "' with type '" + o_field.getType().getTypeName() + "'");
			
			/* if field of parameter object is of type list */
			if (o_field.getType().getTypeName().contains("java.util.List")) {
				/* retrieve field type of list */
				java.lang.reflect.ParameterizedType stringListType = (java.lang.reflect.ParameterizedType)p_o_object.getClass().getDeclaredField(o_field.getName()).getGenericType();
		        Class<?> o_type = (Class<?>)stringListType.getActualTypeArguments()[0];
		        
		        										de.forestj.lib.Global.ilogFiner("iterate all fields - array element type '" + o_type.getTypeName() + "'");
				
		        /* if type of field list is not contained in the list of allowed types, skip this field */
		        if (!a_allowedTypes.contains(o_type.getTypeName())) {
		        											de.forestj.lib.Global.ilogFinest("skip field - type '" + o_type.getTypeName() + "' not allowed");
					continue;
		        }
			} else if (o_field.getType().getTypeName().endsWith("[]")) { /* if field array type of parameter object is not contained in the list of allowed types, skip this field */
				if (!a_allowedTypes.contains(o_field.getType().getTypeName().substring(0, o_field.getType().getTypeName().length() - 2))) {
															de.forestj.lib.Global.ilogFinest("skip array field - type '" + o_field.getType().getTypeName() + "' not allowed");
					continue;
	        	}
			} else if (!a_allowedTypes.contains(o_field.getType().getTypeName())) { /* if field type of parameter object is not contained in the list of allowed types, skip this field */
														de.forestj.lib.Global.ilogFinest("skip field - type '" + o_field.getType().getTypeName() + "' not allowed");
				continue;
			} else if ( (java.lang.reflect.Modifier.isStatic(o_field.getModifiers())) || (java.lang.reflect.Modifier.isFinal(o_field.getModifiers())) ) { /* if field is static or final, skip this field */
														de.forestj.lib.Global.ilogFinest("skip field - field is static or final");
				continue;
			}
			
			/* add csv header name and csv delimiter to csv line */
			s_csv += o_field.getName() + this.s_delimiter;
		}
		
		/* remove last csv delimiter in csv line */
		s_csv = s_csv.substring(0, s_csv.length() - this.s_delimiter.length());
		
		/* return csv line with line break */
		return s_csv + this.s_lineBreak;
	}
	
	/**
	 * Encode object to csv data string
	 * 
	 * @param p_o_object	object parameter to get object field names for header fields
	 * supported types:
	 * byte, short, int, long, float, double, char, boolean, string,
	 * java.lang.String, java.lang.Short, java.lang.Byte, java.lang.Integer,
	 * java.lang.Long, java.lang.Float, java.lang.Double, java.lang.Boolean, 
	 * java.lang.Character, java.math.BigDecimal, java.util.Date,
	 * java.time.LocalDateTime, java.time.LocalDate, java.time.LocalTime
	 * @return 				csv content String
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalArgumentException		invalid type parameter
	 */
	private String encodeObject(Object p_o_object) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, java.lang.reflect.InvocationTargetException, IllegalArgumentException {
		String s_csv = "";
		
		/* iterate all fields of parameter object */
		for (java.lang.reflect.Field o_field : p_o_object.getClass().getDeclaredFields()) {
			/* help variable for retrieving array element type */
			String s_arrayType = null;
			
													de.forestj.lib.Global.ilogFiner("iterate all fields - field '" + o_field.getName() + "' with type '" + o_field.getType().getTypeName() + "'");
			
			/* if field of parameter object is of type list */
			if (o_field.getType().getTypeName().contains("java.util.List")) {
				/* retrieve field type of list */
				java.lang.reflect.ParameterizedType stringListType = (java.lang.reflect.ParameterizedType)p_o_object.getClass().getDeclaredField(o_field.getName()).getGenericType();
		        Class<?> o_type = (Class<?>)stringListType.getActualTypeArguments()[0];
		        s_arrayType = o_type.getTypeName();
		        
		        										de.forestj.lib.Global.ilogFiner("iterate all fields - array element type '" + o_type.getTypeName() + "'");
		        
		        /* if type of field list is not contained in the list of allowed types, skip this field */
		        if (!a_allowedTypes.contains(s_arrayType)) {
															de.forestj.lib.Global.ilogFinest("skip field - type '" + s_arrayType + "' not allowed");
					continue;
		        }
			} else if (o_field.getType().getTypeName().endsWith("[]")) { /* if field array type of parameter object is not contained in the list of allowed types, skip this field */
				if (!a_allowedTypes.contains(o_field.getType().getTypeName().substring(0, o_field.getType().getTypeName().length() - 2))) {
															de.forestj.lib.Global.ilogFinest("skip array field - type '" + o_field.getType().getTypeName() + "' not allowed");
					continue;
	        	}
			} else if (!a_allowedTypes.contains(o_field.getType().getTypeName())) { /* if field type of parameter object is not contained in the list of allowed types, skip this field */
														de.forestj.lib.Global.ilogFinest("skip field - type '" + o_field.getType().getTypeName() + "' not allowed");
				continue;
			} else if ( (java.lang.reflect.Modifier.isStatic(o_field.getModifiers())) || (java.lang.reflect.Modifier.isFinal(o_field.getModifiers())) ) { /* if field is static or final, skip this field */
														de.forestj.lib.Global.ilogFinest("skip field - field is static or final");
				continue;
			}
			
			/* help variable for accessing object field */
			Object o_object = null;
			
			/* check if we use property methods with invoke to get object data values */
			if (this.b_usePropertyMethods) {
				java.lang.reflect.Method o_method = null;
				
				/* store get-property-method of java type object */
				try {
					o_method = p_o_object.getClass().getDeclaredMethod("get" + o_field.getName());
				} catch (NoSuchMethodException | SecurityException o_exc) {
					throw new NoSuchMethodException("Class instance method[" + "get" + o_field.getName() + "] does not exist for class instance(" + p_o_object.getClass().getTypeName() + ")");
				}
				
				/* invoke get-property-method to get object data of current element */
				o_object = o_method.invoke(p_o_object);
			} else {
				/* call field directly to get object data values */
				try {
					o_object = p_o_object.getClass().getDeclaredField(o_field.getName()).get(p_o_object);
				} catch (IllegalAccessException o_exc) {
					throw new IllegalAccessException("Access violation for field[" + o_field.getName() + "]: " + o_exc.getMessage());
				}
			}
			
			/* if help variable got access to object field */
			if (o_object != null) {
				/* if field of parameter object is of type list */
				if (o_field.getType().getTypeName().contains("java.util.List")) {
					/* if type of field list could not be retrieved, throw an exception */
					if (s_arrayType == null) {
						throw new NullPointerException("Could not retrieve array element type of java.util.List for field(" + o_field.getName() + ")");
					}
					
					/* help variable for storing list values */
					String s_arrayValues = "";
					
					/* cast current current field of parameter object as list with unknown generic type */
					java.util.List<?> a_objects = (java.util.List<?>)o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.size() > 0) {
						/* iterate objects in list and encode data to csv recursively */
						for (int i = 0; i < a_objects.size(); i++) {
							/* encode array object element and add csv array delimiter */
							s_arrayValues += objectValueToString(a_objects.get(i), s_arrayType) + this.s_arrayDelimiter;
						}
						
						/* remove last csv array delimiter in array values */
						s_arrayValues = s_arrayValues.substring(0, s_arrayValues.length() - this.s_arrayDelimiter.length());
					}
					
					/* if array values contains csv delimiter or a double quote, we have to escape array values and escape double quotes within */
					if ( (s_arrayValues.contains(this.s_delimiter)) || (s_arrayValues.contains("\"")) ) {
						s_arrayValues = "\"" + s_arrayValues.replaceAll("\"", "\"\"") + "\"";
					}
					
					/* add array values and csv delimiter as new csv column */
					s_csv += s_arrayValues + this.s_delimiter;
				} else if (o_field.getType().getTypeName().endsWith("[]")) {
					/* handle usual arrays */
					
					/* get array type */
					s_arrayType = o_field.getType().getTypeName().substring(0, o_field.getType().getTypeName().length() - 2);
					
					/* help variable for storing list values */
					String s_arrayValues = "";
										
					if ( (s_arrayType.contentEquals("boolean")) || (s_arrayType.contentEquals("java.lang.Boolean")) ) {
						/* cast current field of parameter object as array */
						boolean[] a_objects = (boolean[])o_object;
						
						/* only execute if we have more than one array element */
						if (a_objects.length > 0) {
							/* iterate objects in list and encode data to csv recursively */
							for (int i = 0; i < a_objects.length; i++) {
								/* encode array object element and add csv array delimiter */
								s_arrayValues += objectValueToString(a_objects[i], s_arrayType) + this.s_arrayDelimiter;
							}
							
							/* remove last csv array delimiter in array values */
							s_arrayValues = s_arrayValues.substring(0, s_arrayValues.length() - this.s_arrayDelimiter.length());
						}
					} else if ( (s_arrayType.contentEquals("byte")) || (s_arrayType.contentEquals("java.lang.Byte")) ) {
						/* cast current field of parameter object as array */
						byte[] a_objects = (byte[])o_object;
						
						/* only execute if we have more than one array element */
						if (a_objects.length > 0) {
							/* iterate objects in list and encode data to csv recursively */
							for (int i = 0; i < a_objects.length; i++) {
								/* encode array object element and add csv array delimiter */
								s_arrayValues += objectValueToString(a_objects[i], s_arrayType) + this.s_arrayDelimiter;
							}
							
							/* remove last csv array delimiter in array values */
							s_arrayValues = s_arrayValues.substring(0, s_arrayValues.length() - this.s_arrayDelimiter.length());
						}
					} else if ( (s_arrayType.contentEquals("char")) || (s_arrayType.contentEquals("java.lang.Character")) ) {
						/* cast current field of parameter object as array */
						char[] a_objects = (char[])o_object;
						
						/* only execute if we have more than one array element */
						if (a_objects.length > 0) {
							/* iterate objects in list and encode data to csv recursively */
							for (int i = 0; i < a_objects.length; i++) {
								/* encode array object element and add csv array delimiter */
								s_arrayValues += objectValueToString(a_objects[i], s_arrayType) + this.s_arrayDelimiter;
							}
							
							/* remove last csv array delimiter in array values */
							s_arrayValues = s_arrayValues.substring(0, s_arrayValues.length() - this.s_arrayDelimiter.length());
						}
					} else if ( (s_arrayType.contentEquals("float")) || (s_arrayType.contentEquals("java.lang.Float")) ) {
						/* cast current field of parameter object as array */
						float[] a_objects = (float[])o_object;
						
						/* only execute if we have more than one array element */
						if (a_objects.length > 0) {
							/* iterate objects in list and encode data to csv recursively */
							for (int i = 0; i < a_objects.length; i++) {
								/* encode array object element and add csv array delimiter */
								s_arrayValues += objectValueToString(a_objects[i], s_arrayType) + this.s_arrayDelimiter;
							}
							
							/* remove last csv array delimiter in array values */
							s_arrayValues = s_arrayValues.substring(0, s_arrayValues.length() - this.s_arrayDelimiter.length());
						}
					} else if ( (s_arrayType.contentEquals("double")) || (s_arrayType.contentEquals("java.lang.Double")) ) {
						/* cast current field of parameter object as array */
						double[] a_objects = (double[])o_object;
						
						/* only execute if we have more than one array element */
						if (a_objects.length > 0) {
							/* iterate objects in list and encode data to csv recursively */
							for (int i = 0; i < a_objects.length; i++) {
								/* encode array object element and add csv array delimiter */
								s_arrayValues += objectValueToString(a_objects[i], s_arrayType) + this.s_arrayDelimiter;
							}
							
							/* remove last csv array delimiter in array values */
							s_arrayValues = s_arrayValues.substring(0, s_arrayValues.length() - this.s_arrayDelimiter.length());
						}
					} else if ( (s_arrayType.contentEquals("short")) || (s_arrayType.contentEquals("java.lang.Short")) ) {
						/* cast current field of parameter object as array */
						short[] a_objects = (short[])o_object;
						
						/* only execute if we have more than one array element */
						if (a_objects.length > 0) {
							/* iterate objects in list and encode data to csv recursively */
							for (int i = 0; i < a_objects.length; i++) {
								/* encode array object element and add csv array delimiter */
								s_arrayValues += objectValueToString(a_objects[i], s_arrayType) + this.s_arrayDelimiter;
							}
							
							/* remove last csv array delimiter in array values */
							s_arrayValues = s_arrayValues.substring(0, s_arrayValues.length() - this.s_arrayDelimiter.length());
						}
					} else if ( (s_arrayType.contentEquals("int")) || (s_arrayType.contentEquals("java.lang.Integer")) ) {
						/* cast current field of parameter object as array */
						int[] a_objects = (int[])o_object;
						
						/* only execute if we have more than one array element */
						if (a_objects.length > 0) {
							/* iterate objects in list and encode data to csv recursively */
							for (int i = 0; i < a_objects.length; i++) {
								/* encode array object element and add csv array delimiter */
								s_arrayValues += objectValueToString(a_objects[i], s_arrayType) + this.s_arrayDelimiter;
							}
							
							/* remove last csv array delimiter in array values */
							s_arrayValues = s_arrayValues.substring(0, s_arrayValues.length() - this.s_arrayDelimiter.length());
						}
					} else if ( (s_arrayType.contentEquals("long")) || (s_arrayType.contentEquals("java.lang.Long")) ) {
						/* cast current field of parameter object as array */
						long[] a_objects = (long[])o_object;
						
						/* only execute if we have more than one array element */
						if (a_objects.length > 0) {
							/* iterate objects in list and encode data to csv recursively */
							for (int i = 0; i < a_objects.length; i++) {
								/* encode array object element and add csv array delimiter */
								s_arrayValues += objectValueToString(a_objects[i], s_arrayType) + this.s_arrayDelimiter;
							}
							
							/* remove last csv array delimiter in array values */
							s_arrayValues = s_arrayValues.substring(0, s_arrayValues.length() - this.s_arrayDelimiter.length());
						}
					} else if ( (s_arrayType.contentEquals("string")) || (s_arrayType.contentEquals("java.lang.String")) ) {
						/* cast current field of parameter object as array */
						String[] a_objects = (String[])o_object;
						
						/* only execute if we have more than one array element */
						if (a_objects.length > 0) {
							/* iterate objects in list and encode data to csv recursively */
							for (int i = 0; i < a_objects.length; i++) {
								/* encode array object element and add csv array delimiter */
								s_arrayValues += objectValueToString(a_objects[i], s_arrayType) + this.s_arrayDelimiter;
							}
							
							/* remove last csv array delimiter in array values */
							s_arrayValues = s_arrayValues.substring(0, s_arrayValues.length() - this.s_arrayDelimiter.length());
						}
					} else if (s_arrayType.contentEquals("java.util.Date")) {
						/* cast current field of parameter object as array */
						java.util.Date[] a_objects = (java.util.Date[])o_object;
						
						/* only execute if we have more than one array element */
						if (a_objects.length > 0) {
							/* iterate objects in list and encode data to csv recursively */
							for (int i = 0; i < a_objects.length; i++) {
								/* encode array object element and add csv array delimiter */
								s_arrayValues += objectValueToString(a_objects[i], s_arrayType) + this.s_arrayDelimiter;
							}
							
							/* remove last csv array delimiter in array values */
							s_arrayValues = s_arrayValues.substring(0, s_arrayValues.length() - this.s_arrayDelimiter.length());
						}
					} else if (s_arrayType.contentEquals("java.time.LocalTime")) {
						/* cast current field of parameter object as array */
						java.time.LocalTime[] a_objects = (java.time.LocalTime[])o_object;
						
						/* only execute if we have more than one array element */
						if (a_objects.length > 0) {
							/* iterate objects in list and encode data to csv recursively */
							for (int i = 0; i < a_objects.length; i++) {
								/* encode array object element and add csv array delimiter */
								s_arrayValues += objectValueToString(a_objects[i], s_arrayType) + this.s_arrayDelimiter;
							}
							
							/* remove last csv array delimiter in array values */
							s_arrayValues = s_arrayValues.substring(0, s_arrayValues.length() - this.s_arrayDelimiter.length());
						}
					} else if (s_arrayType.contentEquals("java.time.LocalDate")) {
						/* cast current field of parameter object as array */
						java.time.LocalDate[] a_objects = (java.time.LocalDate[])o_object;
						
						/* only execute if we have more than one array element */
						if (a_objects.length > 0) {
							/* iterate objects in list and encode data to csv recursively */
							for (int i = 0; i < a_objects.length; i++) {
								/* encode array object element and add csv array delimiter */
								s_arrayValues += objectValueToString(a_objects[i], s_arrayType) + this.s_arrayDelimiter;
							}
							
							/* remove last csv array delimiter in array values */
							s_arrayValues = s_arrayValues.substring(0, s_arrayValues.length() - this.s_arrayDelimiter.length());
						}
					} else if (s_arrayType.contentEquals("java.time.LocalDateTime")) {
						/* cast current field of parameter object as array */
						java.time.LocalDateTime[] a_objects = (java.time.LocalDateTime[])o_object;
						
						/* only execute if we have more than one array element */
						if (a_objects.length > 0) {
							/* iterate objects in list and encode data to csv recursively */
							for (int i = 0; i < a_objects.length; i++) {
								/* encode array object element and add csv array delimiter */
								s_arrayValues += objectValueToString(a_objects[i], s_arrayType) + this.s_arrayDelimiter;
							}
							
							/* remove last csv array delimiter in array values */
							s_arrayValues = s_arrayValues.substring(0, s_arrayValues.length() - this.s_arrayDelimiter.length());
						}
					} else if (s_arrayType.contentEquals("java.math.BigDecimal")) {
						/* cast current field of parameter object as array */
						java.math.BigDecimal[] a_objects = (java.math.BigDecimal[])o_object;
						
						/* only execute if we have more than one array element */
						if (a_objects.length > 0) {
							/* iterate objects in list and encode data to csv recursively */
							for (int i = 0; i < a_objects.length; i++) {
								/* encode array object element and add csv array delimiter */
								s_arrayValues += objectValueToString(a_objects[i], s_arrayType) + this.s_arrayDelimiter;
							}
							
							/* remove last csv array delimiter in array values */
							s_arrayValues = s_arrayValues.substring(0, s_arrayValues.length() - this.s_arrayDelimiter.length());
						}
					}
					
					/* if array values contains csv delimiter or a double quote, we have to escape array values and escape double quotes within */
					if ( (s_arrayValues.contains(this.s_delimiter)) || (s_arrayValues.contains("\"")) ) {
						s_arrayValues = "\"" + s_arrayValues.replaceAll("\"", "\"\"") + "\"";
					}
					
					/* add array values and csv delimiter as new csv column */
					s_csv += s_arrayValues + this.s_delimiter;
				} else {
					/* add object value and csv delimiter as new csv column */
					s_csv += objectValueToString(o_object, o_field.getType().getTypeName()) + this.s_delimiter;
				}
			} else {
				/* add empty value and csv delimiter as new csv column */
				s_csv += this.s_delimiter;
			}
		}
		
		/* remove last csv delimiter in csv line */
		s_csv = s_csv.substring(0, s_csv.length() - this.s_delimiter.length());
		
		return s_csv;
	}
	
	/**
	 * Encode object value to a string value so it can be appended to csv data string
	 * escaping double quotes within a value
	 * 
	 * @param p_o_object	object parameter to encode value
	 * supported types:
	 * byte, short, int, long, float, double, char, boolean, string,
	 * java.lang.String, java.lang.Short, java.lang.Byte, java.lang.Integer,
	 * java.lang.Long, java.lang.Float, java.lang.Double, java.lang.Boolean, 
	 * java.lang.Character, java.math.BigDecimal, java.util.Date,
	 * java.time.LocalDateTime, java.time.LocalDate, java.time.LocalTime
	 * @return 				String
	 * @throws IllegalArgumentException		invalid type parameter
	 */
	private String objectValueToString(Object p_o_object, String p_s_type) throws IllegalArgumentException {
		String s_foo = "";
		
		if (p_o_object != null) {
			/* transpose primitive type to class type */
			switch (p_s_type.toLowerCase()) {
				case "short":
					p_s_type = "java.lang.Short";
					break;
				case "byte":
					p_s_type = "java.lang.Byte";
					break;
				case "int":
					p_s_type = "java.lang.Integer";
					break;
				case "long":
					p_s_type = "java.lang.Long";
					break;
				case "float":
					p_s_type = "java.lang.Float";
					break;
				case "double":
					p_s_type = "java.lang.Double";
					break;
				case "boolean":
					p_s_type = "java.lang.Boolean";
					break;
				case "char":
					p_s_type = "java.lang.Character";
					break;
				case "string":
					p_s_type = "java.lang.String";
					break;
			}
			
			/* cast object data to string value by class type */
			if (p_s_type.contentEquals("java.lang.String")) {
				s_foo = String.class.cast(p_o_object).toString();
			} else if (p_s_type.contentEquals("java.lang.Short")) {
				java.lang.Short o_foo = java.lang.Short.class.cast(p_o_object);
				s_foo = o_foo.toString();
			} else if (p_s_type.contentEquals("java.lang.Byte")) {
				java.lang.Byte o_foo = java.lang.Byte.class.cast(p_o_object);
				s_foo = o_foo.toString();
			} else if (p_s_type.contentEquals("java.lang.Integer")) {
				java.lang.Integer o_foo = java.lang.Integer.class.cast(p_o_object);
				s_foo = o_foo.toString();
			} else if (p_s_type.contentEquals("java.lang.Long")) {
				java.lang.Long o_foo = java.lang.Long.class.cast(p_o_object);
				s_foo = o_foo.toString();
			} else if (p_s_type.contentEquals("java.lang.Float")) {
				java.lang.Float o_foo = java.lang.Float.class.cast(p_o_object);
				s_foo = o_foo.toString();
			} else if (p_s_type.contentEquals("java.lang.Double")) {
				java.lang.Double o_foo = java.lang.Double.class.cast(p_o_object);
				s_foo = o_foo.toString();
			} else if (p_s_type.contentEquals("java.lang.Boolean")) {
				java.lang.Boolean o_foo = java.lang.Boolean.class.cast(p_o_object);
				s_foo = o_foo.toString();
			} else if (p_s_type.contentEquals("java.lang.Character")) {
				java.lang.Character o_foo = java.lang.Character.class.cast(p_o_object);
				s_foo = o_foo.toString();
			} else if (p_s_type.contentEquals("java.math.BigDecimal")) {
				java.math.BigDecimal o_bigDecimal = java.math.BigDecimal.class.cast(p_o_object);
				s_foo = o_bigDecimal.toString();
			} else if (p_s_type.contentEquals("java.util.Date")) {
				java.util.Date o_date = java.util.Date.class.cast(p_o_object);
				s_foo = de.forestj.lib.Helper.utilDateToISO8601UTC(o_date);
			} else if (p_s_type.contentEquals("java.time.LocalDateTime")) {
				java.time.LocalDateTime o_localDateTime = java.time.LocalDateTime.class.cast(p_o_object);
				s_foo = de.forestj.lib.Helper.toISO8601UTC(o_localDateTime);
			} else if (p_s_type.contentEquals("java.time.LocalDate")) {
				java.time.LocalDate o_localDate = java.time.LocalDate.class.cast(p_o_object);
				s_foo = de.forestj.lib.Helper.toISO8601UTC(java.time.LocalDateTime.of(o_localDate, java.time.LocalTime.of(0, 0)));
			} else if (p_s_type.contentEquals("java.time.LocalTime")) {
				java.time.LocalTime o_localTime = java.time.LocalTime.class.cast(p_o_object);
				s_foo = de.forestj.lib.Helper.toISO8601UTC(java.time.LocalDateTime.of(java.time.LocalDate.of(1900, 1, 1), o_localTime));
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] for " + p_o_object.getClass().getTypeName());
			}
		}
		
		/* if value contains csv delimiter or a double quote, we have to escape the value and escape double quotes within */
		if ( (s_foo.contains(this.s_delimiter)) || (s_foo.contains("\"")) ) {
			if (s_foo.contains("\"")) {
				s_foo = "\"" + s_foo.replaceAll("\"", "\"\"") + "\"";
			} else {
				s_foo = "\"" + s_foo + "\"";
			}
		}
		
		return s_foo;
	}

	/* decoding csv to data */
	
	/**
	 * Decode csv file to an object given as parameter
	 * 
	 * @param p_s_csvFile	full path to csv file
	 * @param p_o_object	object parameter where csv data will be decoded and cast into object fields
	 * supported types:
	 * byte, short, int, long, float, double, char, boolean, string,
	 * java.lang.String, java.lang.Short, java.lang.Byte, java.lang.Integer,
	 * java.lang.Long, java.lang.Float, java.lang.Double, java.lang.Boolean, 
	 * java.lang.Character, java.math.BigDecimal, java.util.Date,
	 * java.time.LocalDateTime, java.time.LocalDate, java.time.LocalTime
	 * @throws IllegalArgumentException		Invalid object or csv file path parameters
	 * @throws java.io.IOException			exception reading csv file
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws InstantiationException		could not instantiate object
	 * @throws ClassNotFoundException		inner class could not be found
	 */
	public void decodeCSV(String p_s_csvFile, Object p_o_object) throws IllegalArgumentException, java.io.IOException, NoSuchFieldException, NoSuchMethodException, java.text.ParseException, java.time.DateTimeException, IllegalAccessException, InstantiationException, java.lang.reflect.InvocationTargetException, ClassNotFoundException {
		this.decodeCSV(p_s_csvFile, p_o_object, null);
	}
	
	/**
	 * Decode csv file to an object given as parameter
	 * 
	 * @param p_s_csvFile				full path to csv file
	 * @param p_o_object				object parameter where csv data will be decoded and cast into object fields
	 * supported types:
	 * byte, short, int, long, float, double, char, boolean, string,
	 * java.lang.String, java.lang.Short, java.lang.Byte, java.lang.Integer,
	 * java.lang.Long, java.lang.Float, java.lang.Double, java.lang.Boolean, 
	 * java.lang.Character, java.math.BigDecimal, java.util.Date,
	 * java.time.LocalDateTime, java.time.LocalDate, java.time.LocalTime
	 * @param p_o_arrayElementClass		Class<?> type if object is a dynamic generic list, so casting can be done
	 * @throws IllegalArgumentException		Invalid object or csv file path parameters
	 * @throws java.io.IOException			exception reading csv file
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws InstantiationException		could not instantiate object
	 * @throws ClassNotFoundException		inner class could not be found
	 */
	public void decodeCSV(String p_s_csvFile, Object p_o_object, Class<?> p_o_arrayElementClass) throws IllegalArgumentException, java.io.IOException, NoSuchFieldException, NoSuchMethodException, java.text.ParseException, java.time.DateTimeException, IllegalAccessException, InstantiationException, java.lang.reflect.InvocationTargetException, ClassNotFoundException {
		/* check if parameter object has a value */
		if (p_o_object == null) {
			throw new IllegalArgumentException("Cannot decode csv file with empty object = null");
		}
		
		/* check if file exists */
		if (!File.exists(p_s_csvFile)) {
			throw new IllegalArgumentException("CSV file[" + p_s_csvFile + "] does not exist.");
		}
		
		/* open csv file */
		de.forestj.lib.io.File o_file = new de.forestj.lib.io.File(p_s_csvFile, this.o_charset, false, de.forestj.lib.io.File.NEWLINE);
		
		/* read all csv file lines */
		String s_csv = o_file.getFileContent();
		
		/* validate csv file content */
	    this.validateCSV(s_csv);
	    
	    /* decode csv file content */
	    this.decodeCSVFile(s_csv, p_o_object, p_o_arrayElementClass);
	}
	
	/**
	 * Check content of csv file if all delimiters and quotes are logical right and can be read for decoding
	 * 
	 * @param p_s_csv			string content of a csv file
	 * @throws Exception		delimiter is not correct
	 */
	private void validateCSV(String p_s_csv) throws IllegalStateException {
		/* state variable if we are currently in a double quoted value or not */
		boolean b_doubleQuoteActive = false;
		
		/* help variable if an escaped double quote is at start or end of the column */
		boolean b_tripleDoubleQuote = false;
		
		/* iterate csv for each character */
		for (int i = 0; i < p_s_csv.length(); i++) {
			/* if we found a not unescaped double quote */
			if (
				((i == 0) && (i != p_s_csv.length() - 1) && (p_s_csv.charAt(i) == '"') && (p_s_csv.charAt(i + 1) != '"')) ||
				(
					((i != 0) && (p_s_csv.charAt(i) == '"') && (p_s_csv.charAt(i - 1) != '"')) &&
					((i != p_s_csv.length() - 1) && (p_s_csv.charAt(i) == '"') && (p_s_csv.charAt(i + 1) != '"'))
				) ||
				(b_tripleDoubleQuote)
			) {
				/* if we are at the end of a double quoted value */
				if (b_doubleQuoteActive) {
					/* unset state */
					b_doubleQuoteActive = false;
					
					/* check if after a double quoted value we have a new delimiter */
					if (p_s_csv.charAt(i + 1) != this.s_delimiter.charAt(0)) {
						throw new IllegalStateException("Expected '" + this.s_delimiter + "' delimiter, but found '" + p_s_csv.charAt(i + 1) +"' after a double quoted value '\"'");
					}
				} else {
					/* we have a new double quoted value incoming, set state */
					b_doubleQuoteActive = true;
				}
				
				/* unset flag */
				if (b_tripleDoubleQuote) {
					b_tripleDoubleQuote = false;
				}
			}
			
			/* set flag if an escaped double quote is at start or end of the column */
			if ( (i != 0) && (i != p_s_csv.length() - 1) && (p_s_csv.charAt(i - 1) == '"') && (p_s_csv.charAt(i) == '"') && (p_s_csv.charAt(i + 1) == '"') ) {
				b_tripleDoubleQuote = true;
			}
		}
	}
	
	/**
	 * Decode csv string content to an object given as parameter
	 * 
	 * @param p_s_csvContent			content of a csv file
	 * @param p_o_object				object parameter where csv data will be decoded and cast into object fields
	 * supported types:
	 * byte, short, int, long, float, double, char, boolean, string,
	 * java.lang.String, java.lang.Short, java.lang.Byte, java.lang.Integer,
	 * java.lang.Long, java.lang.Float, java.lang.Double, java.lang.Boolean, 
	 * java.lang.Character, java.math.BigDecimal, java.util.Date,
	 * java.time.LocalDateTime, java.time.LocalDate, java.time.LocalTime
	 * @param p_o_arrayElementClass		Class<?> type if object is a dynamic generic list, so casting can be done
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws InstantiationException		could not instantiate object
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 * @throws ClassNotFoundException		inner class could not be found
	 */
	private void decodeCSVFile(String p_s_csvContent, Object p_o_object, Class<?> p_o_arrayElementClass) throws NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, InstantiationException, java.text.ParseException, java.time.DateTimeException, ClassNotFoundException {
		/* split csv file content by line break into multiple lines and save it into a string array */
		String[] a_csvLines = p_s_csvContent.split(this.s_lineBreak);
		
		/* check if csv file content is not empty */
		if (a_csvLines.length > 0) {
			/* help variable for starting decoding csv file lines */
			int i_start = 0;
			
			/* generate header line out of parameter object */
			String s_headerLine = this.generateHeaderLine(p_o_object).replaceAll(this.s_lineBreak, "");
			
			/* if parameter array element class is set, generate header line based on this class */
			if (p_o_arrayElementClass != null) {
				/* check if class type of array element is a inner class */
				if (p_o_arrayElementClass.getTypeName().contains("$")) {
					/* get instance of parent class */
					Object o_parentClass = Class.forName(p_o_arrayElementClass.getTypeName().split("\\$")[0]).getDeclaredConstructor().newInstance();
					boolean b_found = false;
					
					/* look for declared inner classes in parent class */
					for (Class<?> o_subClass : o_parentClass.getClass().getDeclaredClasses()) {
						/* inner class must match with Class<?> type parameter of dynamic generic list */
						if (o_subClass == p_o_arrayElementClass) {
							b_found = true;
							/* generate header line with new instance of inner class, with help of parent class instance */
							s_headerLine = this.generateHeaderLine(p_o_arrayElementClass.getDeclaredConstructor(o_parentClass.getClass()).newInstance(o_parentClass)).replaceAll(this.s_lineBreak, "");
							break;
						}
					}
					
					/* throw exception if inner class could not be found */
					if (!b_found) {
						throw new ClassNotFoundException("Could not found inner class in scope '" + p_o_arrayElementClass.getTypeName() + "'");
					}
				} else {
					/* generate header line with class declared constructor instance */
					s_headerLine = this.generateHeaderLine(p_o_arrayElementClass.getDeclaredConstructor().newInstance()).replaceAll(this.s_lineBreak, "");
				}
			}
			
			/* retrieve field names by splitting header line with csv delimiter */
			String[] a_fieldNames = s_headerLine.split(this.s_delimiter);
			
													de.forestj.lib.Global.ilogFiner("Generated header line:\t" + s_headerLine);
													de.forestj.lib.Global.ilogFiner("First line from file:\t" + a_csvLines[0]);
			
			/* compare generated header line with first line of csv file content */
			if (a_csvLines[0].contentEquals(s_headerLine)) {
				/* generated header line matches with first line, so we can skip this line */
				i_start++;
			}
			
			/* helper variable to store list object */
			Object o_tempListObject = null;
			
			/* check if parameter object is an array list */
			if (p_o_object instanceof java.util.List) {
				/* if parameter object is an array list, we need an array element class */
				if (p_o_arrayElementClass == null) {
					throw new NullPointerException("Cannot decode csv file with array object element = null, we need at least an empty object for reflection");
				}
				
				/* cast parameter object to array list */
				@SuppressWarnings("unchecked")
				java.util.List<Object> o_foo = (java.util.List<Object>)p_o_object;
				/* store casted array list to help variable */
				o_tempListObject = o_foo;
			}
			
			/* iterate each line of csv file content */
			for (int i = i_start; i < a_csvLines.length; i++) {
				/* array to store csv columns of a csv line */
				java.util.List<String> a_csvColumns = new java.util.ArrayList<String>();
				
				/* parameter array element class is set */
				if (p_o_arrayElementClass != null) {
					/* check if class type of array element is a inner class */
					if (p_o_arrayElementClass.getTypeName().contains("$")) {
						/* get instance of parent class */
						Object o_parentClass = Class.forName(p_o_arrayElementClass.getTypeName().split("\\$")[0]).getDeclaredConstructor().newInstance();
						boolean b_found = false;
						
						/* look for declared inner classes in parent class */
						for (Class<?> o_subClass : o_parentClass.getClass().getDeclaredClasses()) {
							/* inner class must match with Class<?> type parameter of dynamic generic list */
							if (o_subClass == p_o_arrayElementClass) {
								b_found = true;
								/* generate new instance of inner class, with help of parent class instance */
								p_o_object = p_o_arrayElementClass.getDeclaredConstructor(o_parentClass.getClass()).newInstance(o_parentClass);
								break;
							}
						}
						
						/* throw exception if inner class could not be found */
						if (!b_found) {
							throw new ClassNotFoundException("Could not found inner class in scope '" + p_o_arrayElementClass.getTypeName() + "'");
						}
					} else {
						/* create a new object instance with parameter array element class and overwrite p_o_object */
						p_o_object = p_o_arrayElementClass.getDeclaredConstructor().newInstance();
					}
				}
				
				/* state variable if we are currently in a double quoted value or not */
				boolean b_doubleQuoteActive = false;
				
				/* help variable to store a csv column */
				String s_csvColumn = "";
				
				/* help variable if an escaped double quote is at start or end of the column */
				boolean b_tripleDoubleQuote = false;
				
				/* iterate csv for each character */
				for (int j = 0; j < a_csvLines[i].length(); j++) {
					/* if we found a not unescaped double quote */
					if (
						((j == 0) && (j != a_csvLines[i].length() - 1) && (a_csvLines[i].charAt(j) == '"') && (a_csvLines[i].charAt(j + 1) != '"')) ||
						(
							((j != 0) && (a_csvLines[i].charAt(j) == '"') && (a_csvLines[i].charAt(j - 1) != '"')) &&
							((j != a_csvLines[i].length() - 1) && (a_csvLines[i].charAt(j) == '"') && (a_csvLines[i].charAt(j + 1) != '"'))
						) ||
						(b_tripleDoubleQuote)
					) {
						/* if we are at the end of a double quoted value */
						if (b_doubleQuoteActive) {
							/* unset state */
							b_doubleQuoteActive = false;
						} else {
							/* we have a new double quoted value incoming, set state */
							b_doubleQuoteActive = true;
						}
						
						/* unset flag */
						if (b_tripleDoubleQuote) {
							b_tripleDoubleQuote = false;
						}
					}
					
					/* set flag if an escaped double quote is at start or end of the column */
					if ( (j != 0) && (j != a_csvLines[i].length() - 1) && (a_csvLines[i].charAt(j - 1) == '"') && (a_csvLines[i].charAt(j) == '"') && (a_csvLines[i].charAt(j + 1) == '"') ) {
						b_tripleDoubleQuote = true;
					}
					
					/* if current character is csv delimiter, or the last character of csv line and not a character within a double quoted value */
					if ( ( (a_csvLines[i].charAt(j) == this.s_delimiter.charAt(0)) || (j == a_csvLines[i].length() - 1) ) && (!b_doubleQuoteActive) ) {
						/* if current character is the last character of csv line and not csv delimiter */
						if ( (j == a_csvLines[i].length() - 1) && (a_csvLines[i].charAt(j) != this.s_delimiter.charAt(0)) ) {
							/* add character to current csv column */
							s_csvColumn += a_csvLines[i].charAt(j);
						}
						
						/* add csv column to csv column line array */
						a_csvColumns.add(s_csvColumn);
						
						/* clear help variable */
						s_csvColumn = "";
						
						/* if last csv column has no value and there is just a delimiter */
						if ( (j == a_csvLines[i].length() - 1) && (a_csvLines[i].charAt(j) == this.s_delimiter.charAt(0)) ) {
							/* add empty csv column to csv column line array */
							a_csvColumns.add(s_csvColumn);
						}
					} else {
						/* add character to current csv column */
						s_csvColumn += a_csvLines[i].charAt(j);
					}
				}
				
				int k = 0;
				
				/* iterate all csv columns retrieved from current csv line */
				for (String s_csvColumnValue : a_csvColumns) {
					/* if csv column value is a double quoted value */
					if ( (s_csvColumnValue.startsWith("\"")) && (s_csvColumnValue.endsWith("\"")) ) {
						/* remove double quote from start and end of csv column value */
						s_csvColumnValue = s_csvColumnValue.substring(1, s_csvColumnValue.length() - 1);
					}
					
					/* if csv column value contains double double quotes '""' */
					if (s_csvColumnValue.contains("\"\"")) {
						/* replace all double double quotes '""' with single double quotes '"' */
						s_csvColumnValue = s_csvColumnValue.replaceAll("\"\"", "\"");
					}
					
															de.forestj.lib.Global.ilogFiner("Field name '" + a_fieldNames[k] + "' of line '" + (i + 1) + "' gets value '" + s_csvColumnValue + "'");
					
					/* set current csv column value into parameter object with property name of generated header line */
					this.setFieldOrProperty(a_fieldNames[k++], p_o_object, s_csvColumnValue);
				}
				
				/* parameter array element class is set */
				if (p_o_arrayElementClass != null) {
					/* cast temporary list object into help variable */
					@SuppressWarnings("unchecked")
					java.util.List<Object> o_temp = (java.util.List<Object>)o_tempListObject;
					
					/* add parameter object to temporary list object */
					o_temp.add(p_o_object);
					
					/* update help variable and continue with iteration */
					o_tempListObject = o_temp;
				}
			}
			
			/* parameter array element class is set */
			if (p_o_arrayElementClass != null) {
				/* all lines have been added to help variable(temporary list object), so we overwrite p_o_object with our help variable */
				p_o_object = o_tempListObject;
			}
			
		}
	}
	
	/**
	 * Method to set property field of an object with value of csv file record
	 * 
	 * @param p_s_fieldName		property field name
	 * @param p_o_object		object parameter where csv data will be decoded and cast into object fields
	 * supported types:
	 * byte, short, int, long, float, double, char, boolean, string,
	 * java.lang.String, java.lang.Short, java.lang.Byte, java.lang.Integer,
	 * java.lang.Long, java.lang.Float, java.lang.Double, java.lang.Boolean, 
	 * java.lang.Character, java.math.BigDecimal, java.util.Date,
	 * java.time.LocalDateTime, java.time.LocalDate, java.time.LocalTime
	 * @param p_s_objectValue	string value of csv file record
	 * @throws IllegalArgumentException		invalid type parameter, or invalid value length for conversion
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws NullPointerException			retrieved field or method is null
	 */
	private void setFieldOrProperty(String p_s_fieldName, Object p_o_object, String p_s_objectValue) throws IllegalArgumentException, java.text.ParseException, java.time.DateTimeException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, NullPointerException {
		/* check if property is of type 'java.util.List' */
		if (p_o_object.getClass().getDeclaredField(p_s_fieldName).getType().getTypeName().contentEquals("java.util.List")) {
			/* check if object value is not empty */
			if (!de.forestj.lib.Helper.isStringEmpty(p_s_objectValue)) {
				/* retrieve field type of list */
				java.lang.reflect.ParameterizedType stringListType = (java.lang.reflect.ParameterizedType)p_o_object.getClass().getDeclaredField(p_s_fieldName).getGenericType();
		        Class<?> o_type = (Class<?>)stringListType.getActualTypeArguments()[0];
		        String s_arrayType = o_type.getTypeName();
		        
		        /* if type of field list is contained in the list of allowed types */
		        if (a_allowedTypes.contains(s_arrayType)) {
		        	/* help variable for retrieving object field */
		        	java.util.List<Object> o_foo = null;
		        	
					/* check if we use property methods with invoke to set object data values */
					if (this.b_usePropertyMethods) {
						java.lang.reflect.Method o_method = null;
						
						/* store get-property-method of java type object */
						try {
							o_method = p_o_object.getClass().getDeclaredMethod("get" + p_s_fieldName);
						} catch (NoSuchMethodException | SecurityException o_exc) {
							throw new NoSuchMethodException("Class instance method[" + "get" + p_s_fieldName + "] does not exist for class instance(" + p_o_object.getClass().getTypeName() + ")");
						}
						
						/* invoke get-property-method to get object data field of current element and cast it to a list object */
						@SuppressWarnings("unchecked")
			        	java.util.List<Object> o_temp = (java.util.List<Object>)o_method.invoke(p_o_object);
						o_foo = o_temp;
					} else {
						/* call field directly to set object data values */
						try {
							/* get object data field of current element and cast it to a list object */
							@SuppressWarnings("unchecked")
				        	java.util.List<Object> o_temp = (java.util.List<Object>)p_o_object.getClass().getDeclaredField(p_s_fieldName).get(p_o_object);
							o_foo = o_temp;
						} catch (NoSuchFieldException o_exc) {
							throw new NoSuchFieldException("Access violation for field[" + p_s_fieldName + "]: " + o_exc.getMessage());
						}
					}
					
					/* check if help variable is set */
					if (o_foo == null) {
						throw new NullPointerException("Could not retrieve object data field[" + p_s_fieldName + "]");
					}
					
					/* check if object value contains csv array delimiter */
					if (p_s_objectValue.contains(this.s_arrayDelimiter)) {
		        		/* list of dangling operators which must be escaped if used as array delimiter */
						java.util.List<String> a_danglingOperators = java.util.Arrays.asList("-","+","/","*","x","^","X");
		        		/* store array delimiter in help variable */
						String s_arrayDelimiter = this.s_arrayDelimiter;
		        		
						/* if array delimiter is a dangling operator */
		        		if (a_danglingOperators.contains(s_arrayDelimiter)) {
		        			/* escape array delimiter with backslash '\' */
		        			s_arrayDelimiter = "\\" + s_arrayDelimiter;
		        		}
		        		
		        		/* split array element values by csv array delimiter */
		        		String[] a_arrayElementValues = p_s_objectValue.split(s_arrayDelimiter);
		        		
		        		/* iterate all array element values */
		        		for (String s_arrayElementValue : a_arrayElementValues) {
		        			/* cast array element value to object and add it to parameter object value */
		        			o_foo.add(this.stringToObjectValue(s_arrayElementValue, s_arrayType));
		        		}
		        	} else {
		        		/* cast array element value to object and add it to parameter object value, without splitting by csv array delimiter */
		        		o_foo.add(this.stringToObjectValue(p_s_objectValue, s_arrayType));
		        	}
		        }
			}
		} else if (p_o_object.getClass().getDeclaredField(p_s_fieldName).getType().getTypeName().endsWith("[]")) { /* handle parameter object value as standard array */
			/* check if object value is not empty */
			if (!de.forestj.lib.Helper.isStringEmpty(p_s_objectValue)) {
				/* retrieve field type of array */
				String s_arrayType = p_o_object.getClass().getDeclaredField(p_s_fieldName).getType().getTypeName().substring(0, p_o_object.getClass().getDeclaredField(p_s_fieldName).getType().getTypeName().length() - 2);
		        
		        /* if type of field array is contained in the list of allowed types */
		        if (a_allowedTypes.contains(s_arrayType)) {
		        	/* help variable for setting object array */
		        	Object[] o_foo = null;
		        	
		        	/* check if object value contains csv array delimiter */
					if (p_s_objectValue.contains(this.s_arrayDelimiter)) {
		        		/* list of dangling operators which must be escaped if used as array delimiter */
						java.util.List<String> a_danglingOperators = java.util.Arrays.asList("-","+","/","*","x","^","X");
		        		/* store array delimiter in help variable */
						String s_arrayDelimiter = this.s_arrayDelimiter;
		        		
						/* if array delimiter is a dangling operator */
		        		if (a_danglingOperators.contains(s_arrayDelimiter)) {
		        			/* escape array delimiter with backslash '\' */
		        			s_arrayDelimiter = "\\" + s_arrayDelimiter;
		        		}
		        		
		        		/* split array element values by csv array delimiter */
		        		String[] a_arrayElementValues = p_s_objectValue.split(s_arrayDelimiter);
		        		
		        		/* create array instance */
		        		if ( (s_arrayType.contentEquals("boolean")) || (s_arrayType.contentEquals("java.lang.Boolean")) ) {
		        			o_foo = new Boolean[a_arrayElementValues.length];
						} else if ( (s_arrayType.contentEquals("byte")) || (s_arrayType.contentEquals("java.lang.Byte")) ) {
							o_foo = new Byte[a_arrayElementValues.length];
						} else if ( (s_arrayType.contentEquals("char")) || (s_arrayType.contentEquals("java.lang.Character")) ) {
							o_foo = new Character[a_arrayElementValues.length];
						} else if ( (s_arrayType.contentEquals("float")) || (s_arrayType.contentEquals("java.lang.Float")) ) {
							o_foo = new Float[a_arrayElementValues.length];
						} else if ( (s_arrayType.contentEquals("double")) || (s_arrayType.contentEquals("java.lang.Double")) ) {
							o_foo = new Double[a_arrayElementValues.length];
						} else if ( (s_arrayType.contentEquals("short")) || (s_arrayType.contentEquals("java.lang.Short")) ) {
							o_foo = new Short[a_arrayElementValues.length];
						} else if ( (s_arrayType.contentEquals("int")) || (s_arrayType.contentEquals("java.lang.Integer")) ) {
							o_foo = new Integer[a_arrayElementValues.length];
						} else if ( (s_arrayType.contentEquals("long")) || (s_arrayType.contentEquals("java.lang.Long")) ) {
							o_foo = new Long[a_arrayElementValues.length];
						} else if ( (s_arrayType.contentEquals("string")) || (s_arrayType.contentEquals("java.lang.String")) ) {
							o_foo = new String[a_arrayElementValues.length];
						} else if (s_arrayType.contentEquals("java.util.Date")) {
							o_foo = new java.util.Date[a_arrayElementValues.length];
						} else if (s_arrayType.contentEquals("java.time.LocalTime")) {
							o_foo = new java.time.LocalTime[a_arrayElementValues.length];
						} else if (s_arrayType.contentEquals("java.time.LocalDate")) {
							o_foo = new java.time.LocalDate[a_arrayElementValues.length];
						} else if (s_arrayType.contentEquals("java.time.LocalDateTime")) {
							o_foo = new java.time.LocalDateTime[a_arrayElementValues.length];
						} else if (s_arrayType.contentEquals("java.math.BigDecimal")) {
							o_foo = new java.math.BigDecimal[a_arrayElementValues.length];
						}
		        		
		        		/* iterate all array element values */
		        		int i_cnt = 0;
		        		
		        		for (String s_arrayElementValue : a_arrayElementValues) {
		        			/* cast array element value to object and add it to parameter object value */
		        			o_foo[i_cnt++] = this.stringToObjectValue(s_arrayElementValue, s_arrayType);
		        		}
		        	} else {
		        		/* create array instance */
		        		if ( (s_arrayType.contentEquals("boolean")) || (s_arrayType.contentEquals("java.lang.Boolean")) ) {
		        			o_foo = new Boolean[1];
						} else if ( (s_arrayType.contentEquals("byte")) || (s_arrayType.contentEquals("java.lang.Byte")) ) {
							o_foo = new Byte[1];
						} else if ( (s_arrayType.contentEquals("char")) || (s_arrayType.contentEquals("java.lang.Character")) ) {
							o_foo = new Character[1];
						} else if ( (s_arrayType.contentEquals("float")) || (s_arrayType.contentEquals("java.lang.Float")) ) {
							o_foo = new Float[1];
						} else if ( (s_arrayType.contentEquals("double")) || (s_arrayType.contentEquals("java.lang.Double")) ) {
							o_foo = new Double[1];
						} else if ( (s_arrayType.contentEquals("short")) || (s_arrayType.contentEquals("java.lang.Short")) ) {
							o_foo = new Short[1];
						} else if ( (s_arrayType.contentEquals("int")) || (s_arrayType.contentEquals("java.lang.Integer")) ) {
							o_foo = new Integer[1];
						} else if ( (s_arrayType.contentEquals("long")) || (s_arrayType.contentEquals("java.lang.Long")) ) {
							o_foo = new Long[1];
						} else if ( (s_arrayType.contentEquals("string")) || (s_arrayType.contentEquals("java.lang.String")) ) {
							o_foo = new String[1];
						} else if (s_arrayType.contentEquals("java.util.Date")) {
							o_foo = new java.util.Date[1];
						} else if (s_arrayType.contentEquals("java.time.LocalTime")) {
							o_foo = new java.time.LocalTime[1];
						} else if (s_arrayType.contentEquals("java.time.LocalDate")) {
							o_foo = new java.time.LocalDate[1];
						} else if (s_arrayType.contentEquals("java.time.LocalDateTime")) {
							o_foo = new java.time.LocalDateTime[1];
						} else if (s_arrayType.contentEquals("java.math.BigDecimal")) {
							o_foo = new java.math.BigDecimal[1];
						}
		        		
		        		/* cast array element value to object and add it to parameter object value, without splitting by csv array delimiter */
		        		o_foo[0] = this.stringToObjectValue(p_s_objectValue, s_arrayType);
		        	}
					
					/* check if we use property methods with invoke to set object data values */
					if (this.b_usePropertyMethods) {
						java.lang.reflect.Method o_method = null;
						boolean b_methodFound = false;
						
						/* look for set-property-method of current parameter object value */
						for (java.lang.reflect.Method o_methodSearch : p_o_object.getClass().getDeclaredMethods()) {
							if (o_methodSearch.getName().contentEquals("set" + p_s_fieldName)) {
								o_method = o_methodSearch;
								b_methodFound = true;
							}
						}
						
						if (!b_methodFound) {
							throw new NoSuchMethodException("Method[" + "set" + p_s_fieldName + "] does not exist for object: " + p_o_object.getClass().getTypeName());
						}
						
						/* class types are not equal primitive types, so unfortunately we must call invoke with the primitive array as parameter */
						if (s_arrayType.contentEquals("boolean")) {
							boolean[] o_bar = new boolean[o_foo.length];
							
							for (int i = 0; i < o_foo.length; i++) {
								o_bar[i] = (boolean)o_foo[i];
							}
							
							/* invoke set-property-method to set object array field of current element */
							o_method.invoke(p_o_object, new Object[] {o_bar});
						} else if (s_arrayType.contentEquals("byte")) {
							byte[] o_bar = new byte[o_foo.length];
							
							for (int i = 0; i < o_foo.length; i++) {
								o_bar[i] = (byte)o_foo[i];
							}
							
							/* invoke set-property-method to set object array field of current element */
							o_method.invoke(p_o_object, new Object[] {o_bar});
						} else if (s_arrayType.contentEquals("char")) {
							char[] o_bar = new char[o_foo.length];
							
							for (int i = 0; i < o_foo.length; i++) {
								o_bar[i] = (char)o_foo[i];
							}
							
							/* invoke set-property-method to set object array field of current element */
							o_method.invoke(p_o_object, new Object[] {o_bar});
						} else if (s_arrayType.contentEquals("float")) {
							float[] o_bar = new float[o_foo.length];
							
							for (int i = 0; i < o_foo.length; i++) {
								o_bar[i] = (float)o_foo[i];
							}
							
							/* invoke set-property-method to set object array field of current element */
							o_method.invoke(p_o_object, new Object[] {o_bar});
						} else if (s_arrayType.contentEquals("double")) {
							double[] o_bar = new double[o_foo.length];
							
							for (int i = 0; i < o_foo.length; i++) {
								o_bar[i] = (double)o_foo[i];
							}
							
							/* invoke set-property-method to set object array field of current element */
							o_method.invoke(p_o_object, new Object[] {o_bar});
						} else if (s_arrayType.contentEquals("short")) {
							short[] o_bar = new short[o_foo.length];
							
							for (int i = 0; i < o_foo.length; i++) {
								o_bar[i] = (short)o_foo[i];
							}
							
							/* invoke set-property-method to set object array field of current element */
							o_method.invoke(p_o_object, new Object[] {o_bar});
						} else if (s_arrayType.contentEquals("int")) {
							int[] o_bar = new int[o_foo.length];
							
							for (int i = 0; i < o_foo.length; i++) {
								o_bar[i] = (int)o_foo[i];
							}
							
							/* invoke set-property-method to set object array field of current element */
							o_method.invoke(p_o_object, new Object[] {o_bar});
						} else if (s_arrayType.contentEquals("long")) {
							long[] o_bar = new long[o_foo.length];
							
							for (int i = 0; i < o_foo.length; i++) {
								o_bar[i] = (long)o_foo[i];
							}
							
							/* invoke set-property-method to set object array field of current element */
							o_method.invoke(p_o_object, new Object[] {o_bar});
						} else {
							/* invoke set-property-method to set object array field of current element */
							o_method.invoke(p_o_object, new Object[] {o_foo});
						}
					} else {
						/* call array field directly to set object array values */
						try {
							/* class types are not equal primitive types, so unfortunately we must call invoke with the primitive array as parameter */
							if (s_arrayType.contentEquals("boolean")) {
								boolean[] o_bar = new boolean[o_foo.length];
								
								for (int i = 0; i < o_foo.length; i++) {
									o_bar[i] = (boolean)o_foo[i];
								}
								
								p_o_object.getClass().getDeclaredField(p_s_fieldName).set(p_o_object, o_bar);
							} else if (s_arrayType.contentEquals("byte")) {
								byte[] o_bar = new byte[o_foo.length];
								
								for (int i = 0; i < o_foo.length; i++) {
									o_bar[i] = (byte)o_foo[i];
								}
								
								p_o_object.getClass().getDeclaredField(p_s_fieldName).set(p_o_object, o_bar);
							} else if (s_arrayType.contentEquals("char")) {
								char[] o_bar = new char[o_foo.length];
								
								for (int i = 0; i < o_foo.length; i++) {
									o_bar[i] = (char)o_foo[i];
								}
								
								p_o_object.getClass().getDeclaredField(p_s_fieldName).set(p_o_object, o_bar);
							} else if (s_arrayType.contentEquals("float")) {
								float[] o_bar = new float[o_foo.length];
								
								for (int i = 0; i < o_foo.length; i++) {
									o_bar[i] = (float)o_foo[i];
								}
								
								p_o_object.getClass().getDeclaredField(p_s_fieldName).set(p_o_object, o_bar);
							} else if (s_arrayType.contentEquals("double")) {
								double[] o_bar = new double[o_foo.length];
								
								for (int i = 0; i < o_foo.length; i++) {
									o_bar[i] = (double)o_foo[i];
								}
								
								p_o_object.getClass().getDeclaredField(p_s_fieldName).set(p_o_object, o_bar);
							} else if (s_arrayType.contentEquals("short")) {
								short[] o_bar = new short[o_foo.length];
								
								for (int i = 0; i < o_foo.length; i++) {
									o_bar[i] = (short)o_foo[i];
								}
								
								p_o_object.getClass().getDeclaredField(p_s_fieldName).set(p_o_object, o_bar);
							} else if (s_arrayType.contentEquals("int")) {
								int[] o_bar = new int[o_foo.length];
								
								for (int i = 0; i < o_foo.length; i++) {
									o_bar[i] = (int)o_foo[i];
								}
								
								p_o_object.getClass().getDeclaredField(p_s_fieldName).set(p_o_object, o_bar);
							} else if (s_arrayType.contentEquals("long")) {
								long[] o_bar = new long[o_foo.length];
								
								for (int i = 0; i < o_foo.length; i++) {
									o_bar[i] = (long)o_foo[i];
								}
								
								p_o_object.getClass().getDeclaredField(p_s_fieldName).set(p_o_object, o_bar);
							} else {
								p_o_object.getClass().getDeclaredField(p_s_fieldName).set(p_o_object, o_foo);
							}
						} catch (NoSuchFieldException o_exc) {
							throw new NoSuchFieldException("Property[" + p_s_fieldName + "] is not accessible for object: " + p_o_object.getClass().getTypeName() + " - " + o_exc.getMessage());
						}
					}
		        }
			}
		} else { /* handle parameter object value as standard object */
			/* check if we use property methods with invoke to set object data values */
			if (this.b_usePropertyMethods) {
				java.lang.reflect.Method o_method = null;
				boolean b_methodFound = false;
				
				/* look for set-property-method of current parameter object value */
				for (java.lang.reflect.Method o_methodSearch : p_o_object.getClass().getDeclaredMethods()) {
					if (o_methodSearch.getName().contentEquals("set" + p_s_fieldName)) {
						o_method = o_methodSearch;
						b_methodFound = true;
					}
				}
				
				if (!b_methodFound) {
					throw new NoSuchMethodException("Method[" + "set" + p_s_fieldName + "] does not exist for object: " + p_o_object.getClass().getTypeName());
				}
				
				/* invoke set-property-method to set object data field of current element and cast string to object value */
				o_method.invoke(p_o_object, this.stringToObjectValue(p_s_objectValue, p_o_object.getClass().getDeclaredField(p_s_fieldName).getType().getTypeName()));
			} else {
				/* call field directly to set object data values and cast string to object value */
				try {
					p_o_object.getClass().getDeclaredField(p_s_fieldName).set(p_o_object, this.stringToObjectValue(p_s_objectValue, p_o_object.getClass().getDeclaredField(p_s_fieldName).getType().getTypeName()));
				} catch (NoSuchFieldException o_exc) {
					throw new NoSuchFieldException("Property[" + p_s_fieldName + "] is not accessible for object: " + p_o_object.getClass().getTypeName() + " - " + o_exc.getMessage());
				}
			}
		}
	}
	
	/**
	 * Convert a string value from a csv file record to an object to decode it into an object
	 * 
	 * @param p_s_value		string value from a csv file record
	 * @param p_s_type		type of destination object field
	 * supported types:
	 * byte, short, int, long, float, double, char, boolean, string,
	 * java.lang.String, java.lang.Short, java.lang.Byte, java.lang.Integer,
	 * java.lang.Long, java.lang.Float, java.lang.Double, java.lang.Boolean, 
	 * java.lang.Character, java.math.BigDecimal, java.util.Date,
	 * java.time.LocalDateTime, java.time.LocalDate, java.time.LocalTime
	 * @return				Object
	 * @throws IllegalArgumentException		invalid type parameter, or invalid value length for conversion
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	private Object stringToObjectValue(String p_s_value, String p_s_type) throws IllegalArgumentException, java.text.ParseException, java.time.DateTimeException {
		Object o_foo = null;
		
		/* transpose primitive type to class type */
		switch (p_s_type.toLowerCase()) {
			case "short":
				p_s_type = "java.lang.Short";
				break;
			case "byte":
				p_s_type = "java.lang.Byte";
				break;
			case "int":
				p_s_type = "java.lang.Integer";
				break;
			case "long":
				p_s_type = "java.lang.Long";
				break;
			case "float":
				p_s_type = "java.lang.Float";
				break;
			case "double":
				p_s_type = "java.lang.Double";
				break;
			case "boolean":
				p_s_type = "java.lang.Boolean";
				break;
			case "char":
				p_s_type = "java.lang.Character";
				break;
			case "string":
				p_s_type = "java.lang.String";
				break;
		}
		
		/* check if value is not empty */
		if (!p_s_value.contentEquals("")) {
			if (p_s_type.contentEquals("java.lang.String")) {
				o_foo = p_s_value;
			} else if (p_s_type.contentEquals("java.lang.Short")) {
				o_foo = Short.parseShort(p_s_value);
			} else if (p_s_type.contentEquals("java.lang.Byte")) {
				o_foo = Byte.parseByte(p_s_value);
			} else if (p_s_type.contentEquals("java.lang.Integer")) {
				o_foo = Integer.parseInt(p_s_value);
			} else if (p_s_type.contentEquals("java.lang.Long")) {
				o_foo = Long.parseLong(p_s_value);
			} else if (p_s_type.contentEquals("java.lang.Float")) {
				o_foo = Float.parseFloat(p_s_value);
			} else if (p_s_type.contentEquals("java.lang.Double")) {
				o_foo = Double.parseDouble(p_s_value);
			} else if (p_s_type.contentEquals("java.lang.Boolean")) {
				o_foo = Boolean.parseBoolean(p_s_value);
			} else if (p_s_type.contentEquals("java.lang.Character")) {
				if (p_s_value.length() != 1) {
					throw new IllegalArgumentException("Value must have length(1) for character - ['" + p_s_value + "' -> length(" + p_s_value.length() + ")]");
				}
				
				o_foo = (Character)p_s_value.charAt(0);
			} else if (p_s_type.contentEquals("java.math.BigDecimal")) {
				java.text.DecimalFormat o_decimalFormat = new java.text.DecimalFormat();
				o_decimalFormat.setParseBigDecimal(true);
				o_foo = o_decimalFormat.parseObject(p_s_value);
				o_foo = new java.math.BigDecimal(p_s_value);
			} else if (p_s_type.contentEquals("java.util.Date")) {
				o_foo = de.forestj.lib.Helper.fromISO8601UTCToUtilDate(p_s_value);
			} else if (p_s_type.contentEquals("java.time.LocalDateTime")) {
				o_foo = de.forestj.lib.Helper.fromISO8601UTC(p_s_value);
			} else if (p_s_type.contentEquals("java.time.LocalDate")) {
				o_foo = de.forestj.lib.Helper.fromISO8601UTC(p_s_value).toLocalDate();
			} else if (p_s_type.contentEquals("java.time.LocalTime")) {
				o_foo = de.forestj.lib.Helper.fromISO8601UTC(p_s_value).toLocalTime();
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] for value[" + p_s_value + "]");
			}
		} else { /* if value is empty */
			if (p_s_type.contentEquals("java.lang.String")) {
				o_foo = "";
			} else if (p_s_type.contentEquals("java.lang.Short")) {
				if (this.b_returnArrayElementNullNotZero) {
					o_foo = null;
				} else {
					o_foo = 0;
				}
			} else if (p_s_type.contentEquals("java.lang.Byte")) {
				if (this.b_returnArrayElementNullNotZero) {
					o_foo = null;
				} else {
					o_foo = 0;
				}
			} else if (p_s_type.contentEquals("java.lang.Integer")) {
				if (this.b_returnArrayElementNullNotZero) {
					o_foo = null;
				} else {
					o_foo = 0;
				}
			} else if (p_s_type.contentEquals("java.lang.Long")) {
				if (this.b_returnArrayElementNullNotZero) {
					o_foo = null;
				} else {
					o_foo = 0;
				}
			} else if (p_s_type.contentEquals("java.lang.Float")) {
				if (this.b_returnArrayElementNullNotZero) {
					o_foo = null;
				} else {
					o_foo = 0.0f;
				}
			} else if (p_s_type.contentEquals("java.lang.Double")) {
				if (this.b_returnArrayElementNullNotZero) {
					o_foo = null;
				} else {
					o_foo = 0.0d;
				}
			} else if (p_s_type.contentEquals("java.lang.Boolean")) {
				if (this.b_returnArrayElementNullNotZero) {
					o_foo = null;
				} else {
					o_foo = false;
				}
			} else if (p_s_type.contentEquals("java.lang.Character")) {
				if (this.b_returnArrayElementNullNotZero) {
					o_foo = null;
				} else {
					o_foo = 0;
				}
			} else if (p_s_type.contentEquals("java.math.BigDecimal")) {
				if (this.b_returnArrayElementNullNotZero) {
					o_foo = null;
				} else {
					java.text.DecimalFormat o_decimalFormat = new java.text.DecimalFormat();
					o_decimalFormat.setParseBigDecimal(true);
					o_foo = o_decimalFormat.parseObject("0");
					o_foo = new java.math.BigDecimal(p_s_value);
				}
			} else if (p_s_type.contentEquals("java.util.Date")) {
				o_foo = null;
			} else if (p_s_type.contentEquals("java.time.LocalDateTime")) {
				o_foo = null;
			} else if (p_s_type.contentEquals("java.time.LocalDate")) {
				o_foo = null;
			} else if (p_s_type.contentEquals("java.time.LocalTime")) {
				o_foo = null;
			}
		}
		
		return o_foo;
	}
}
