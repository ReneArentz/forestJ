package net.forestany.forestj.lib.io;

/**
 * 
 * Abstract fixed length record class - automatically detecting fields and handling fixed length of each field
 *
 * @param <T>	Class definition of current class, e.g. Test extends net.forestany.forestj.lib.io.FixedLengthRecord&lt;Test.class&gt;
 */
public abstract class FixedLengthRecord<T> {
	
	/* Delegates */
	
	/**
	 * interface which can be implemented as standard transpose method to transpose a string value to an object of your choice
	 */
	public interface IReadDelegate {
		/**
		 * transpose method to transpose a string value to an object
		 * 
		 * @param p_s_value					string value which should be transposed
		 * @return Object
		 * @throws ClassCastException		we could not parse a value to predicted field object type
		 */
		Object TransposeValue(String p_s_value) throws ClassCastException;
	}
	
	/**
	 * interface which can be implemented as standard transpose method to transpose an object to a string value with a specific format
	 */
	public interface IWriteDelegate {
		/**
		 * transpose method to transpose an object to a string value
		 * 
		 * @param p_o_value					object instance
		 * @param p_i_length				known length of string field
		 * @return String
		 * @throws ClassCastException		we could not parse a value to predicted field object type
		 */
		String TransposeValue(Object p_o_value, int p_i_length) throws ClassCastException;
	}
	
	/**
	 * interface which can be implemented as standard transpose method to transpose a floating point number value to an object of your choice
	 */
	public interface IReadFPNDelegate {
		/**
		 * transpose method to transpose a floating point number string value to an object
		 * 
		 * @param p_s_value							string value which should be transposed
		 * @param p_i_positionDecimalSeparator		position of the decimal separator	
		 * @return Object
		 * @throws ClassCastException				we could not parse a value to predicted field object type
		 */
		Object TransposeValue(String p_s_value, int p_i_positionDecimalSeparator) throws ClassCastException;
	}
	
	/**
	 * interface which can be implemented as standard transpose method to transpose a floating point number to a string value with a specific format
	 */
	public interface IWriteFPNDelegate {
		/**
		 * transpose method to transpose a floating point number instance to a string value
		 * 
		 * @param p_o_value						floating point number object instance
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @param p_s_decimalSeparator			string for decimal separator, use '$' for system settings
		 * @param p_s_groupSeparator			string for group separator, use '$' for system settings
		 * @return String
		 * @throws ClassCastException			we could not parse a value to predicted field object type
		 */
		String TransposeValue(Object p_o_value, int p_i_amountDigits, int p_i_amountFractionDigits, String p_s_decimalSeparator, String p_s_groupSeparator) throws ClassCastException;
	}
	
	/* Fields */
	
	/**
	 * Structure description of fixed length record
	 */
	protected java.util.LinkedHashMap<Integer, StructureElement> Structure = null;
	/**
	 * List of unique fields
	 */
	protected java.util.List<String> Unique = new java.util.ArrayList<String>();
	/**
	 * List of order by fields
	 */
	protected java.util.Map<String, Boolean> OrderBy = new java.util.HashMap<String, Boolean>();
	
	private java.util.HashMap<String, java.util.List<Object>> a_uniqueTemp = new java.util.HashMap<String, java.util.List<Object>>();
	
	/**
	 * Fixed length record image class
	 */
	protected Class<T> FLRImageClass = null;
	/**
	 * Known overall length of fixed length record
	 */
	protected int KnownOverallLength = 0;
	/**
	 * Flag to allow empty unique fields
	 */
	protected boolean AllowEmptyUniqueFields = false;
	
	/* Properties */
	
	/**
	 * get known overall length of flr
	 * 
	 * @return int
	 */
	public int getKnownOverallLength() {
		return this.KnownOverallLength;
	}
	
	/**
	 *  clear unique temp map
	 */
	public void clearUniqueTemp() {
		this.a_uniqueTemp.clear();
	}
	
	/* Methods */
	
	/**
	 * Fixed length record class constructor, initiating all values and properties and checks if all necessary restrictions are set
	 * 
	 * @throws NullPointerException								fixed length record image class, unique or order by have no values
	 * @throws NoSuchFieldException								given field values in structure, unique or order by do not exists is current class
	 * @throws java.lang.reflect.InvocationTargetException		if the underlying constructor throws an exception
	 * @throws ClassNotFoundException							class cannot be located
	 * @throws IllegalAccessException							cannot access field, must be public
	 * @throws java.lang.InstantiationException					if the class that declares the underlying constructor represents an abstract class
	 * @throws NoSuchMethodException							could not retrieve declared constructor
	 */
	public FixedLengthRecord() throws NullPointerException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		/* first call init function to get initial values and properties from inherited class */
		this.init();
		
		/* check fixed length record image class is not null */
		if (this.FLRImageClass == null) {
			throw new NullPointerException("You must specify a fixed length record image class within the Init-method.");
		}
		
		/* check structure is not empty */
		if ( (this.Structure == null) || (this.Structure.size() < 1) ) {
			throw new IllegalArgumentException("You must specify a structure within the Init-method.");
		}
		
		/* iterate each structure element */
		for (java.util.Map.Entry<Integer, StructureElement> o_structureElement : this.Structure.entrySet()) {
			/* we have a structure element with a subtype */
            if (o_structureElement.getValue().getSubType() != null) {
                /* length of structure element must be '0' */
                if (o_structureElement.getValue().getLength() != 0) {
                    throw new IllegalArgumentException("Length of structure element with subtype is not '0', but is '" + o_structureElement.getValue().getLength() + "'.");
                }

                /* check subtype amount value */
                if (o_structureElement.getValue().getSubTypeAmount() < 1) {
                    throw new IllegalArgumentException("Amount of structure element with subtype must be greater than '0', but is '" + o_structureElement.getValue().getSubTypeAmount() + "'.");
                }
                
                /* create new instance of subtype in fixed length record class */
                FixedLengthRecord<?> o_subtype = null;
        		
        		/* check if class type of object is a inner class */
        		if (o_structureElement.getValue().getSubType().getTypeName().contains("$")) {
        			/* get target class */
        			Class<?> o_targetClass = Class.forName(o_structureElement.getValue().getSubType().getTypeName());
        			
        			/* get instance of parent class */
        			Object o_parentClass = Class.forName(o_structureElement.getValue().getSubType().getTypeName().split("\\$")[0]).getDeclaredConstructor().newInstance();
        			boolean b_found = false;
        			
        			/* look for declared inner classes in parent class */
        			for (Class<?> o_subClass : o_parentClass.getClass().getDeclaredClasses()) {
        				/* inner class must match with Class<?> type parameter of dynamic generic list */
        				if (o_subClass == o_targetClass) {
        					b_found = true;
        					/* create new object instance of inner class, with help of parent class instance */
        					o_subtype = (FixedLengthRecord<?>) o_targetClass.getDeclaredConstructor(o_parentClass.getClass()).newInstance(o_parentClass);
        					break;
        				}
        			}
        			
        			/* throw exception if inner class could not be found */
        			if (!b_found) {
        				throw new ClassNotFoundException("Could not found inner class in scope '" + o_targetClass.getTypeName() + "'");
        			}
        		} else {
        			/* create new instance of inherited class */
        			o_subtype = (FixedLengthRecord<?>) o_structureElement.getValue().getSubType().getDeclaredConstructor().newInstance();
        		}
                
                /* add sum of amount of subtype objects for overall length */
                this.KnownOverallLength += (((FixedLengthRecord<?>) o_subtype).getKnownOverallLength() * o_structureElement.getValue().getSubTypeAmount());
                
                /* update subtype known overall length for later use */
                if (o_structureElement.getValue().getSubTypeKnownOverallLength() == -1) {
                    o_structureElement.getValue().setSubTypeKnownOverallLength(((FixedLengthRecord<?>) o_subtype).getKnownOverallLength());
                }
            }
			
			/* sum up the field lengths */
			this.KnownOverallLength += o_structureElement.getValue().getLength();
		}
		
		/* check if each field in unique list really exists in current class */
		for (String s_unique : this.Unique) {
			/* it is possible that a unique constraint exists of multiple columns, separated by semicolon */
			if (s_unique.contains(";")) {
				String[] a_uniques = s_unique.split(";");
				
				/* iterate each unique field */
				for (int i = 0; i < a_uniques.length; i++) {
					/* check if unique field really exists */
					if (!this.fieldExists(a_uniques[i])) {
						throw new NoSuchFieldException("Unique[" + s_unique + "] is not a field of current record class.");
					}
				}
			} else {
				/* check if unique field really exists */
				if (!this.fieldExists(s_unique)) {
					throw new NoSuchFieldException("Unique[" + s_unique + "] is not a field of current record class.");
				}
			}
		}
		
		/* check if each field in order by list really exists in current class */
		for (String s_field : this.OrderBy.keySet()) {
			if (!this.fieldExists(s_field)) {
				throw new NoSuchFieldException("OrderBy[" + s_field + "] is not a field of current record class.");
			}
		}
		
												if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("created fixed length record object");
	}
	
	/**
	 * Abstract Init function so any class inheriting from FixedLengthRecord&lt;T&gt; must have this method
	 * declaring structure of the fixed length record, unique, order by and most of all flr image class
	 */
	abstract protected void init();
	
	/**
	 * Method to check if a field exists in current fixed length record class
	 * 
	 * @param p_s_field			colum name
	 * @return boolean			true - field exist, false - field does not exist
	 */
	protected boolean fieldExists(String p_s_field) {
		/* return value */
		boolean b_found = false;
		
		/* iterate each field of current fixed length record class */
		for (int i = 0; i < this.getClass().getDeclaredFields().length; i++) {
			/* get field */
			java.lang.reflect.Field o_field =  this.getClass().getDeclaredFields()[i];
			
			/* check if field starts with 'Field', but is not equal to 'Fields' */
			if ( (o_field.getName().startsWith("Field")) && (o_field.getName().compareTo("Fields") != 0) ) {
				/* field name without 'Field' prefix must match parameter value */
				if (o_field.getName().substring(5).compareTo(p_s_field) == 0) {
					/* set return value to true */
					b_found = true;
				}
			}
		}
		
		return b_found;
	}
	
	/**
	 * Method to retrieve field value of current fixed length record class
	 * 
	 * @param <T2>						generic object type
	 * @param p_s_field					field name, will be changed to 'Field' + p_s_field
	 * @return &lt;T2&gt;				unknown object type until method in use
	 * @throws NoSuchFieldException		field does not exist
	 * @throws IllegalAccessException	cannot access field, must be public
	 */
	@SuppressWarnings("unchecked")
	protected <T2> T2 getFieldValue(String p_s_field) throws NoSuchFieldException, IllegalAccessException {
		p_s_field = "Field" + p_s_field;
		
												if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get field value from '" + p_s_field + "'");
		
		T2 o_foo = null;
		/* get class type */
		Class<T2> o_class = (Class<T2>) this.getClass().getDeclaredField(p_s_field).getType();
		/* get class name */
		String s_class = o_class.getName();
		
		/* switch class value by class name for casting */
		switch(s_class) {
			case "boolean":
			case "java.lang.Boolean":
				o_class = (Class<T2>) Boolean.class;
				break;
			case "double":
			case "java.lang.Double":
				o_class = (Class<T2>) Double.class;
				break;
			case "float":
			case "java.lang.Float":
				o_class = (Class<T2>) Float.class;
				break;
			case "long":
			case "java.lang.Long":
				o_class = (Class<T2>) Long.class;
				break;
			case "int":
			case "java.lang.Integer":
				o_class = (Class<T2>) Integer.class;
				break;
			case "short":
			case "java.lang.Short":
				o_class = (Class<T2>) Short.class;
				break;
			case "char":
			case "java.lang.Character":
				o_class = (Class<T2>) Character.class;
				break;
			case "byte":
			case "java.lang.Byte":
				o_class = (Class<T2>) Byte.class;
				break;
			case "string":
			case "java.lang.String":
				o_class = (Class<T2>) String.class;
				break;
			case "java.util.Date":
				o_class = (Class<T2>) java.util.Date.class;
				break;
			case "java.time.LocalDateTime":
				o_class = (Class<T2>) java.time.LocalDateTime.class;
				break;
			case "java.time.LocalDate":
				o_class = (Class<T2>) java.time.LocalDate.class;
				break;
			case "java.time.LocalTime":
				o_class = (Class<T2>) java.time.LocalTime.class;
				break;
			case "java.math.BigDecimal":
				o_class = (Class<T2>) java.math.BigDecimal.class;
				break;
		}
		
		/* cast object of field */
		o_foo = o_class.cast(this.getClass().getDeclaredField(p_s_field).get(this));

		/* return casted object */
		return o_foo;
	}
	
	/**
	 * Method to set a field value of current fixed record record class
	 * 
	 * @param p_s_field					field name, will be changed to 'Field' + p_s_field
	 * @param o_value					object value which will be set as field value
	 * @throws NoSuchFieldException		field does not exist
	 * @throws IllegalAccessException	cannot access field, must be public
	 */
	protected void setFieldValue(String p_s_field, Object o_value) throws NoSuchFieldException, IllegalAccessException {
		p_s_field = "Field" + p_s_field;
		
		if (o_value != null) {
													if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("set field value for: '" + p_s_field + "'" + "\t\tfield type: " + this.getClass().getDeclaredField(p_s_field).getType().getTypeName() + "\t\tvalue type: " + o_value.getClass().getTypeName());
			
			if (this.getClass().getDeclaredField(p_s_field).getType().getTypeName().contentEquals("java.time.LocalDateTime")) {
				/* if we have field with type java.time.LocalDateTime we have to do a cast */
				o_value = (java.time.LocalDateTime.class.cast(o_value));
			} else if (this.getClass().getDeclaredField(p_s_field).getType().getTypeName().contentEquals("java.time.LocalDate")) { 
				/* if we have field with type java.time.LocalDate we have to do a cast */
				o_value = (java.time.LocalDate.class.cast(o_value));
			} else if (this.getClass().getDeclaredField(p_s_field).getType().getTypeName().contentEquals("java.time.LocalTime")) {
				/* if we have field with type java.time.LocalTime we have to do a cast */
				o_value = (java.time.LocalTime.class.cast(o_value));
			} else if ( (this.getClass().getDeclaredField(p_s_field).getType().getTypeName().toLowerCase().contains("bigdecimal")) && (o_value.getClass().getTypeName().toLowerCase().contains("string")) ) {
				/* recognize empty null value for java.math.BigDecimal */
				if ( (o_value.toString().length() == 0) || (o_value.toString().contentEquals("NULL")) ) {
					o_value = null;
				}
			}
		}
		
		/* set column value, accessing 'this' class and field with column name */
		this.getClass().getDeclaredField(p_s_field).set(this, o_value);
	}
	
	/**
	 * Easy method to return all fields with their values of current fixed length record class
	 * @return String	a string line of all fields with their values "field_name = field_value|"
	 */
	public String returnFields() {
		String s_foo = "";
		
		/* iterate each field of current fixed length record class */
		for (int i = 0; i < this.getClass().getDeclaredFields().length; i++) {
			/* get field */
			java.lang.reflect.Field o_field =  this.getClass().getDeclaredFields()[i];
			
			/* check if field starts with 'Field', but is not equal to 'Fields' */
			if ( (o_field.getName().startsWith("Field")) && (o_field.getName().compareTo("Fields") != 0) ) {
				try {
					String s_object = "NOT_INITIALIZED";
					
					if (o_field.get(this) instanceof java.util.List<?>) {
						@SuppressWarnings("unchecked")
						java.util.List<FixedLengthRecord<?>> a_items = (java.util.List<FixedLengthRecord<?>>)o_field.get(this);
						
						if (a_items.size() > 0) {
							s_object = "";
							
							for (FixedLengthRecord<?> o_item : a_items) {
								s_object += "{" + o_item.returnFields() + "}, ";
							}
							
							s_object = "[" + s_object.substring(0, s_object.length() - 2) + "]";
						} else {
							s_object = "[]";
						}
					} else {
						/* get field value */
						Object o_foo = o_field.get(this);
						/* set field string value to 'NULL', in case value is null */
						s_object = "NULL";
						
						/* if field value is not null, use toString method */
						if (o_foo != null) {
							s_object = o_foo.toString();
						}
					}
					
					/* add field name and its value to return string */
					s_foo += o_field.getName().substring(5) + " = " + s_object + "|";
				} catch (Exception o_exc) {
					/* just continue if field name or field value cannot be retrieved */
					s_foo += o_field.getName().substring(5) + " = COULD_NOT_RETRIEVE|";
				}
			}
		}
		
		return s_foo;
	}
	
	/**
	 * Assume field values of a string line to fields of current fixed length record class and its record image
	 * 
	 * @param p_s_line											fields and their values in a string line
	 * @return &lt;T&gt;										class of FLRImageClass property
	 * @throws IllegalArgumentException							line length does not match known overall length of fixed length record
	 * @throws ClassCastException								we could not parse a value to predicted field object type
	 * @throws java.lang.reflect.InvocationTargetException		if the underlying constructor throws an exception
	 * @throws NoSuchFieldException								field does not exist
	 * @throws java.lang.InstantiationException					if the class that declares the underlying constructor represents an abstract class
	 * @throws IllegalAccessException							cannot access field, must be public
	 * @throws NoSuchMethodException							could not retrieve declared constructor
	 * @throws ClassNotFoundException							class cannot be located
	 * @throws IllegalStateException							field value for unique field already exists
	 */
	@SuppressWarnings("unchecked")
	protected T readFieldsFromString(String p_s_line) throws IllegalArgumentException, ClassCastException, java.lang.reflect.InvocationTargetException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException, ClassNotFoundException, NoSuchFieldException, IllegalStateException {
		/* create new instance of fixed length record class */
		T o_temp = null;
		
		/* check if class type of object is a inner class */
		if (this.FLRImageClass.getTypeName().contains("$")) {
			/* get target class */
			Class<?> o_targetClass = Class.forName(this.FLRImageClass.getTypeName());
			
			/* get instance of parent class */
			Object o_parentClass = Class.forName(this.FLRImageClass.getTypeName().split("\\$")[0]).getDeclaredConstructor().newInstance();
			boolean b_found = false;
			
			/* look for declared inner classes in parent class */
			for (Class<?> o_subClass : o_parentClass.getClass().getDeclaredClasses()) {
				/* inner class must match with Class<?> type parameter of dynamic generic list */
				if (o_subClass == o_targetClass) {
					b_found = true;
					/* create new object instance of inner class, with help of parent class instance */
					o_temp = (T) o_targetClass.getDeclaredConstructor(o_parentClass.getClass()).newInstance(o_parentClass);
					break;
				}
			}
			
			/* throw exception if inner class could not be found */
			if (!b_found) {
				throw new ClassNotFoundException("Could not found inner class in scope '" + o_targetClass.getTypeName() + "'");
			}
		} else {
			/* create new instance of inherited class */
			o_temp = this.FLRImageClass.getDeclaredConstructor().newInstance();
		}
		
		/* check if line length matches known overall length */
		if (p_s_line.length() != this.KnownOverallLength) {
			throw new IllegalArgumentException("Line length parameter '" + p_s_line.length() + "' does not match known overall length of all flr fields '" + this.KnownOverallLength + "'");
		}
		
		int i_position = 0;
		
		/* iterate each structure element */
		for (java.util.Map.Entry<Integer, StructureElement> o_structureElement : this.Structure.entrySet()) {
			/* check if we have a constant field */
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_structureElement.getValue().getConstant())) {
				/* increase position pointer */
				i_position += o_structureElement.getValue().getLength();
				
				/* go on with next structure element */
				continue;
			}
			
			/* we have a structure element with a subtype -> generic list */
            if (o_structureElement.getValue().getSubType() != null) {
                /* check subtype known overall length */
                if (o_structureElement.getValue().getSubTypeKnownOverallLength() <= 0) {
                    throw new IllegalArgumentException("Subtype known overall length has not been set, it is '" + o_structureElement.getValue().getSubTypeKnownOverallLength() + "'.");
                }

                /* create instance of generic list */
                java.util.List<Object> o_genericList = new java.util.ArrayList<Object>();
                
                /* iterate expected amount of generic list field values */
                for (int i = 0; i < o_structureElement.getValue().getSubTypeAmount(); i++) {
                    /* get generic list field value out of line parameter */
                    String s_value = p_s_line.substring(i_position, i_position + o_structureElement.getValue().getSubTypeKnownOverallLength());
                    /* increase position pointer */
                    i_position += o_structureElement.getValue().getSubTypeKnownOverallLength();

                    /* create instance of subtype object */
                    FixedLengthRecord<?> o_subtypeInstance = null;
            		
            		/* check if class type of object is a inner class */
            		if (o_structureElement.getValue().getSubType().getTypeName().contains("$")) {
            			/* get target class */
            			Class<?> o_targetClass = Class.forName(o_structureElement.getValue().getSubType().getTypeName());
            			
            			/* get instance of parent class */
            			Object o_parentClass = Class.forName(o_structureElement.getValue().getSubType().getTypeName().split("\\$")[0]).getDeclaredConstructor().newInstance();
            			boolean b_found = false;
            			
            			/* look for declared inner classes in parent class */
            			for (Class<?> o_subClass : o_parentClass.getClass().getDeclaredClasses()) {
            				/* inner class must match with Class<?> type parameter of dynamic generic list */
            				if (o_subClass == o_targetClass) {
            					b_found = true;
            					/* create new object instance of inner class, with help of parent class instance */
            					o_subtypeInstance = (FixedLengthRecord<?>) o_targetClass.getDeclaredConstructor(o_parentClass.getClass()).newInstance(o_parentClass);
            					break;
            				}
            			}
            			
            			/* throw exception if inner class could not be found */
            			if (!b_found) {
            				throw new ClassNotFoundException("Could not found inner class in scope '" + o_targetClass.getTypeName() + "'");
            			}
            		} else {
            			/* create new instance of inherited class */
            			o_subtypeInstance = (FixedLengthRecord<?>) o_structureElement.getValue().getSubType().getDeclaredConstructor().newInstance();
            		}
                    
                    /* read fields from substring line for subtype */
                    o_subtypeInstance = (FixedLengthRecord<?>) o_subtypeInstance.readFieldsFromString(s_value);

                    /* check unique fields of subtype instance */
                    if (o_subtypeInstance.Unique.size() > 0) {
                        /* iterate unique keys */
                        for (String s_unique : o_subtypeInstance.Unique) {
                            /* retrieve unique field value variable */
                            Object o_fieldValue = o_subtypeInstance.getFieldValue(s_unique);
                            /* unique key name for subtype */
                            String s_uniqueName = "__Subtype__" + s_unique;

                            /* empty field values in unique fields are allowed and can be skipped */
                            if (o_subtypeInstance.AllowEmptyUniqueFields) {
                                /* field value is null or an empty string */
                                if ((o_fieldValue == null) || (o_fieldValue.toString().trim().length() < 1)) {
                                    continue;
                                }

                                try {
                                    /* field value can be parsed to int and is equal to zero */
                                    if (Integer.parseInt(o_fieldValue.toString()) == 0) {
                                        continue;
                                    }
                                } catch (Exception o_exc) {
                                    /* nothing to do */
                                }
                            }

		                    /* unique values are stored within temp map for current field */
							if (this.a_uniqueTemp.containsKey(s_uniqueName)) {
								/* check if field value already exists */
								if ( this.a_uniqueTemp.get(s_uniqueName).contains(o_fieldValue) ) {
									throw new IllegalStateException("field value for unique field '" + s_uniqueName.substring(11) + "' already exists");
								} else {
									/* add field value to unique temp list */
									this.a_uniqueTemp.get(s_uniqueName).add(o_fieldValue);
								}
							} else { /* no values for this fields stored so far */
								/* create unique temp list for field and add value */
								this.a_uniqueTemp.put(s_uniqueName, new java.util.ArrayList<>());
								this.a_uniqueTemp.get(s_uniqueName).add(o_fieldValue);
							}
                        }
                    }
                    
                    /* add instance to generic list */
                    o_genericList.add(o_subtypeInstance);
                }

                /* clear all unique temp values for subtypes */
                java.util.List<String> a_listRemoveSubtypes = new java.util.ArrayList<String>();
                
                for (String s_key : this.a_uniqueTemp.keySet()) {
                	if (s_key.startsWith("__Subtype__")) {
                		a_listRemoveSubtypes.add(s_key);
                	}
                }
                
                for (String s_key : a_listRemoveSubtypes) {
                    this.a_uniqueTemp.remove(s_key);
                }

                /* set generic list value */
                ((FixedLengthRecord<?>)o_temp).setFieldValue(o_structureElement.getValue().getField(), o_genericList);
            } else { /* we have a normal structure element */
            	/* field value variable */
				Object o_fieldValue = null;
				/* get field value out of line parameter */
				String s_value = p_s_line.substring(i_position, i_position + o_structureElement.getValue().getLength());
				/* increase position pointer */
				i_position += o_structureElement.getValue().getLength();
				
				/* check if we have a transpose method available */
				if ( (o_structureElement.getValue().getReadTranspose() == null) && (o_structureElement.getValue().getReadFPNTranspose() == null) ) {
					throw new NoSuchMethodException("Structure element[" + o_structureElement.getValue().getField() + "] has no read transpose method pointer");
				}
				
				/* check if field does not contain only white spaces or only '0' */
				if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(s_value.trim())) && (!s_value.matches("^[0]+$")) ) {
															if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("field '" + o_structureElement.getValue().getField() + "(" + i_position + ".." + (i_position + o_structureElement.getValue().getLength()) + ")'\t\tunique '" + (this.Unique.contains(o_structureElement.getValue().getField()) ? "yes" : "no ") + "'\t\tvalue '" + s_value + "'\t\tconverted value '" + ( (o_structureElement.getValue().getReadTranspose() == null) ? o_structureElement.getValue().getReadFPNTranspose().TransposeValue(s_value, o_structureElement.getValue().getPositionDecimalSeparator()) : o_structureElement.getValue().getReadTranspose().TransposeValue(s_value) ) + "'");
					
					/* get field value */
					o_fieldValue = ( (o_structureElement.getValue().getReadTranspose() == null) ? o_structureElement.getValue().getReadFPNTranspose().TransposeValue(s_value, o_structureElement.getValue().getPositionDecimalSeparator()) : o_structureElement.getValue().getReadTranspose().TransposeValue(s_value) );
				} else if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(s_value.trim())) && (s_value.matches("^[0]+$")) ) { /* value has only character '0' */
					/* get class type */
					Class<?> o_class = (Class<?>) this.getClass().getDeclaredField("Field" + o_structureElement.getValue().getField()).getType();
					/* get class name */
					String s_class = o_class.getName();
					
					/* set null if we have a date/time object */
					if ( (s_class.contentEquals("java.util.Date")) || (s_class.contentEquals("java.time.LocalDateTime")) || (s_class.contentEquals("java.time.LocalDate")) || (s_class.contentEquals("java.time.LocalTime")) ) {
																if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("field '" + o_structureElement.getValue().getField() + "(" + i_position + ".." + (i_position + o_structureElement.getValue().getLength()) + ")'\t\tunique '" + (this.Unique.contains(o_structureElement.getValue().getField()) ? "yes" : "no ") + "'\t\tvalue '" + s_value + "'\t\tconverted value 'null'");
						
						/* keep field value as 'null' */
						
					} else { /* proceed normal */
																if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("field '" + o_structureElement.getValue().getField() + "(" + i_position + ".." + (i_position + o_structureElement.getValue().getLength()) + ")'\t\tunique '" + (this.Unique.contains(o_structureElement.getValue().getField()) ? "yes" : "no ") + "'\t\tvalue '" + s_value + "'\t\tconverted value '" + ( (o_structureElement.getValue().getReadTranspose() == null) ? o_structureElement.getValue().getReadFPNTranspose().TransposeValue(s_value, o_structureElement.getValue().getPositionDecimalSeparator()) : o_structureElement.getValue().getReadTranspose().TransposeValue(s_value) ) + "'");
						
						/* get field value */
						o_fieldValue = ( (o_structureElement.getValue().getReadTranspose() == null) ? o_structureElement.getValue().getReadFPNTranspose().TransposeValue(s_value, o_structureElement.getValue().getPositionDecimalSeparator()) : o_structureElement.getValue().getReadTranspose().TransposeValue(s_value) );
					}
				} else { /* field value contains only white spaces, we can set it to null */
															if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("field '" + o_structureElement.getValue().getField() + "(" + i_position + ".." + (i_position + o_structureElement.getValue().getLength()) + ")'\t\tunique '" + (this.Unique.contains(o_structureElement.getValue().getField()) ? "yes" : "no ") + "'\t\tvalue '" + s_value + "'\t\tconverted value 'null'");
					
					/* keep field value as 'null' */
				}
				
				/* check unique fields */
				if (this.Unique.contains(o_structureElement.getValue().getField())) {
					/* unique values are stored within temp map for current field */
					if (this.a_uniqueTemp.containsKey(o_structureElement.getValue().getField())) {
						/* check if field value already exists */
						if ( this.a_uniqueTemp.get(o_structureElement.getValue().getField()).contains(o_fieldValue) ) {
							throw new IllegalStateException("field value for unique field '" + o_structureElement.getValue().getField() + "' already exists");
						} else {
							/* add field value to unique temp list */
							this.a_uniqueTemp.get(o_structureElement.getValue().getField()).add(o_fieldValue);
						}
					} else { /* no values for this fields stored so far */
						/* create unique temp list for field and add value */
						this.a_uniqueTemp.put(o_structureElement.getValue().getField(), new java.util.ArrayList<>());
						this.a_uniqueTemp.get(o_structureElement.getValue().getField()).add(o_fieldValue);
					}
				}
				
				/* set field value */
				((FixedLengthRecord<?>)o_temp).setFieldValue(o_structureElement.getValue().getField(), o_fieldValue);
			}
		}
		
		return o_temp;
	}
	
	/**
	 * Writes all fields of fixed length record class into one string line, based on transpose write methods of structure elements and it's properties
	 * 
	 * @return													one string line with all field values
	 * @throws NoSuchFieldException								field does not exist
	 * @throws java.lang.reflect.InvocationTargetException		if the underlying constructor throws an exception
	 * @throws ClassNotFoundException							class cannot be located
	 * @throws IllegalAccessException							cannot access field, must be public
	 * @throws java.lang.InstantiationException					if the class that declares the underlying constructor represents an abstract class
	 * @throws NoSuchMethodException							could not retrieve declared constructor
	 */
	protected String writeFieldsToString() throws NoSuchFieldException, java.lang.reflect.InvocationTargetException, ClassNotFoundException, IllegalAccessException, java.lang.InstantiationException, NoSuchMethodException {
		String s_foo = "";
		
		/* iterate each structure element */
		for (java.util.Map.Entry<Integer, StructureElement> o_structureElement : this.Structure.entrySet()) {
			/* we have a structure element with a subtype -> generic list */
            if (o_structureElement.getValue().getSubType() != null) {
                /* check subtype known overall length */
                if (o_structureElement.getValue().getSubTypeKnownOverallLength() <= 0) {
                    throw new IllegalArgumentException("Subtype known overall length has not been set, it is '" + o_structureElement.getValue().getSubTypeKnownOverallLength() + "'.");
                }

                /* get field name value */
                String s_field = o_structureElement.getValue().getField();
                
                /* check if field value is set */
                if (net.forestany.forestj.lib.Helper.isStringEmpty(s_field)) {
                	throw new IllegalArgumentException("Field property of structure element has no value");
                }
                
                /* get generic list as object */
                Object o_foo = this.getFieldValue(s_field);
                
                /* check if generic list field value is set */
                if (o_foo == null) {
                	throw new NullPointerException("Generic list field value is null");
                }
                
                /* cast current object as list with unknown generic type */
                @SuppressWarnings("unchecked")
				java.util.List<FixedLengthRecord<?>> o_temp = (java.util.List<FixedLengthRecord<?>>)o_foo;
                
                /* iterate all configured amount of subtype instances */
                for (int i = 0; i < o_structureElement.getValue().getSubTypeAmount(); i++) {
                    /* check if we have an instance in our iteration */
                    if (i < o_temp.size()) {
                        /* add list element to current line of fixed length record */
                        s_foo += o_temp.get(i).writeFieldsToString();
                    } else { /* we have empty instances */
                        /* create new instance of subtype in fixed length record class */
                    	FixedLengthRecord<?> o_subtype = null;
                		
                		/* check if class type of object is a inner class */
                		if (o_structureElement.getValue().getSubType().getTypeName().contains("$")) {
                			/* get target class */
                			Class<?> o_targetClass = Class.forName(o_structureElement.getValue().getSubType().getTypeName());
                			
                			/* get instance of parent class */
                			Object o_parentClass = Class.forName(o_structureElement.getValue().getSubType().getTypeName().split("\\$")[0]).getDeclaredConstructor().newInstance();
                			boolean b_found = false;
                			
                			/* look for declared inner classes in parent class */
                			for (Class<?> o_subClass : o_parentClass.getClass().getDeclaredClasses()) {
                				/* inner class must match with Class<?> type parameter of dynamic generic list */
                				if (o_subClass == o_targetClass) {
                					b_found = true;
                					/* create new object instance of inner class, with help of parent class instance */
                					o_subtype = (FixedLengthRecord<?>) o_targetClass.getDeclaredConstructor(o_parentClass.getClass()).newInstance(o_parentClass);
                					break;
                				}
                			}
                			
                			/* throw exception if inner class could not be found */
                			if (!b_found) {
                				throw new ClassNotFoundException("Could not found inner class in scope '" + o_targetClass.getTypeName() + "'");
                			}
                		} else {
                			/* create new instance of inherited class */
                			o_subtype = (FixedLengthRecord<?>) o_structureElement.getValue().getSubType().getDeclaredConstructor().newInstance();
                		}

                        /* add empty list element to current line of fixed length record */
                        s_foo += o_subtype.writeFieldsToString();
                    }
                }
            } else if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_structureElement.getValue().getConstant())) { /* check if we have a constant field */
				/* add constant to line */
				s_foo += o_structureElement.getValue().getConstant();
			} else if (o_structureElement.getValue().getWriteTranspose() == null) {
				/* use transpose method to get floating point number field value */
				s_foo += o_structureElement.getValue().getWriteFPNTranspose().TransposeValue(
					this.getFieldValue(o_structureElement.getValue().getField()),
					o_structureElement.getValue().getAmountDigits(),
					o_structureElement.getValue().getAmountFractionDigits(),
					o_structureElement.getValue().getDecimalSeparator(),
					o_structureElement.getValue().getGroupSeparator()
				);
			} else {
				/* use transpose method to get field value */
				s_foo += o_structureElement.getValue().getWriteTranspose().TransposeValue(this.getFieldValue(o_structureElement.getValue().getField()), o_structureElement.getValue().getLength());
			}
		}
		
		return s_foo;
	}
	
	/* Internal Classes */
	
	/**
	 * Encapsulation of a flr's structure element
	 */
	public class StructureElement {
		
		/* Fields */
		
		private String s_constant;
		private String s_field;
		private int i_length;
		private IReadDelegate del_readTranspose;
		private IWriteDelegate del_writeTranspose;
		private IReadFPNDelegate del_readFPNTranspose;
		private IWriteFPNDelegate del_writeFPNTranspose;
		private int i_positionDecimalSeparator;
		private int i_amountDigits;
		private int i_amountFractionDigits;
		private String s_decimalSeparator;
		private String s_groupSeparator;
		private int i_subtypeAmount;
		private Class<?> o_subtype;
		private int i_subtypeKnownOverallLength;
		
		/* Properties */
		
		/**
		 * get constant value
		 * 
		 * @return String
		 */
		public String getConstant() {
			return this.s_constant;
		}
		
		/**
		 * set constant value
		 * 
		 * @param p_s_value		a constant within the flr
		 */
		public void setConstant(String p_s_value) {
			this.s_constant = p_s_value;
		}
		
		/**
		 * get name of field
		 * 
		 * @return String
		 */
		public String getField() {
			return this.s_field;
		}
		
		/**
		 * set name of field
		 * 
		 * @param p_s_value		name of field
		 */
		public void setField(String p_s_value) {
			this.s_field = p_s_value;
		}
		
		/**
		 * get length of field within flr
		 * 
		 * @return int
		 */
		public int getLength() {
			return this.i_length;
		}
		
		/**
		 * set length of field within flr
		 * 
		 * @param p_i_value		length of field within flr
		 */
		public void setLength(int p_i_value) {
			this.i_length = p_i_value;
		}
		
		/**
		 * get read method delegate
		 * 
		 * @return IReadDelegate - method delegate to transpose a string value to an object of your choice
		 */
		public IReadDelegate getReadTranspose() {
			return this.del_readTranspose;
		}
		
		/**
		 * set read method delegate
		 * 
		 * @param p_del_readTranspose		method delegate to transpose a string value to an object of your choice
		 */
		public void setReadTranspose(IReadDelegate p_del_readTranspose) {
			this.del_readTranspose = p_del_readTranspose;
		}
		
		/**
		 * get write method delegate
		 * 
		 * @return IWriteDelegate - method delegate to transpose an object to a string value with a specific format
		 */
		public IWriteDelegate getWriteTranspose() {
			return this.del_writeTranspose;
		}
		
		/**
		 * set write method delegate
		 * 
		 * @param p_del_readTranspose		method delegate to transpose an object to a string value with a specific format
		 */
		public void setWriteTranspose(IWriteDelegate p_del_readTranspose) {
			this.del_writeTranspose = p_del_readTranspose;
		}
		
		/**
		 * get read method floating point number delegate
		 * 
		 * @return IReadFPNDelegate - method delegate to transpose a string value to a floating point number
		 */
		public IReadFPNDelegate getReadFPNTranspose() {
			return this.del_readFPNTranspose;
		}
		
		/**
		 * set read method floating point number delegate
		 * 
		 * @param p_del_readFPNTranspose	method delegate to transpose a string value to a floating point number
		 */
		public void setReadFPNTranspose(IReadFPNDelegate p_del_readFPNTranspose) {
			this.del_readFPNTranspose = p_del_readFPNTranspose;
		}
		
		/**
		 * get write method floating point number delegate
		 * 
		 * @return IWriteFPNDelegate - method delegate to transpose a floating point number to a string value with a specific format
		 */
		public IWriteFPNDelegate getWriteFPNTranspose() {
			return this.del_writeFPNTranspose;
		}
		
		/**
		 * set write method floating point number delegate
		 * 
		 * @param p_del_writeFPNTranspose	method delegate to transpose a floating point number to a string value with a specific format
		 */
		public void setWriteFPNTranspose(IWriteFPNDelegate p_del_writeFPNTranspose) {
			this.del_writeFPNTranspose = p_del_writeFPNTranspose;
		}
		
		/**
		 * get position decimal separator
		 * 
		 * @return int
		 */
		public int getPositionDecimalSeparator() {
			return this.i_positionDecimalSeparator;
		}
		
		/**
		 * set position decimal separator
		 * 
		 * @param p_i_value		position of decimal separator within a floating point number string (read only)
		 */
		public void setPositionDecimalSeparator(int p_i_value) {
			this.i_positionDecimalSeparator = p_i_value;
		}
		
		/**
		 * get amount digits value
		 * 
		 * @return int
		 */
		public int getAmountDigits() {
			return this.i_amountDigits;
		}
		
		/**
		 * set amount digits value
		 * 
		 * @param p_i_value		amount of digits for a floating point number (write only)
		 */
		public void setAmountDigits(int p_i_value) {
			this.i_amountDigits = p_i_value;
		}
		
		/**
		 * get amount fraction digits value
		 * 
		 * @return int
		 */
		public int getAmountFractionDigits() {
			return this.i_amountFractionDigits;
		}
		
		/**
		 * set amount fraction digits value
		 * 
		 * @param p_i_value		amount of fractional digits for a floating point number (write only)
		 */
		public void setAmountFractionDigits(int p_i_value) {
			this.i_amountFractionDigits = p_i_value;
		}
		
		/**
		 * get decimal separator
		 * 
		 * @return String
		 */
		public String getDecimalSeparator() {
			return this.s_decimalSeparator;
		}
		
		/**
		 * set decimal separator
		 * 
		 * @param p_s_value		string for decimal separator, use '$' for system settings
		 */
		public void setDecimalSeparator(String p_s_value) {
			this.s_decimalSeparator = p_s_value;
		}
		
		/**
		 * get group separator
		 * 
		 * @return String
		 */
		public String getGroupSeparator() {
			return this.s_groupSeparator;
		}
		
		/**
		 * set group separator
		 * 
		 * @param p_s_value		string for group separator, use '$' for system settings
		 */
		public void setGroupSeparator(String p_s_value) {
			this.s_groupSeparator = p_s_value;
		}
		
		/**
		 * get sub type amount
		 * 
		 * @return int
		 */
		public int getSubTypeAmount() {
			return this.i_subtypeAmount;
		}
		
		/**
		 * set sub type amount
		 * 
		 * @param p_i_value		known amount of sub-type instances
		 */
		public void setSubTypeAmount(int p_i_value) {
			this.i_subtypeAmount = p_i_value;
		}
		
		/**
		 * get sub type class
		 * 
		 * @return Class&lt;?&gt;
		 */
		public Class<?> getSubType() {
			return this.o_subtype;
		}
		
		/**
		 * set sub type class
		 * @param p_o_value		known class of sub-type instances as Class&lt;?&gt;
		 */
		public void setSubType(Class<?> p_o_value) {
			this.o_subtype = p_o_value;
		}
		
		/**
		 * get sub type known overall length
		 * 
		 * @return int
		 */
		public int getSubTypeKnownOverallLength() {
			return this.i_subtypeKnownOverallLength;
		}
		
		/**
		 * set sub type known overall length
		 * 
		 * @param p_i_value		set int value for the known overall length of a fixed length record sub type 
		 */
		public void setSubTypeKnownOverallLength(int p_i_value) {
			this.i_subtypeKnownOverallLength = p_i_value;
		}
		
		/* Methods */
		
		/**
		 * StructureElement constructor
		 * 
		 * @param p_s_constant			constant value within fixed length record
		 */
		public StructureElement(String p_s_constant) {
			this(null, p_s_constant.length(), null, null, null, null, -1, -1, -1, null, null, p_s_constant, -1, null);
		}
		
		/**
		 * StructureElement constructor
		 * 
		 * @param p_s_field			name of field
		 * @param p_i_length		length of field within flr
		 */
		public StructureElement(String p_s_field, int p_i_length) {
			this(p_s_field, p_i_length, null, null, null, null, -1, -1, -1, null, null, null, -1, null);
		}
		
		/**
		 * StructureElement constructor
		 * 
		 * @param p_s_field					name of field
		 * @param p_i_amountSubtypes		amount of subtype objects within flr
		 * @param p_o_subtype				subtype class
		 */
		public StructureElement(String p_s_field, int p_i_amountSubtypes, Class<?> p_o_subtype) {
			this(p_s_field, 0, null, null, null, null, -1, -1, -1, null, null, null, p_i_amountSubtypes, p_o_subtype);
		}
				
		/**
		 * StructureElement constructor
		 * 
		 * @param p_s_field						name of field
		 * @param p_i_length					length of field within flr
		 * @param p_del_readTranspose			method delegate to transpose a string value to an object of your choice
		 * @param p_del_writeTranspose			method delegate to transpose an object to a string value with a specific format
		 */
		public StructureElement(String p_s_field, int p_i_length, IReadDelegate p_del_readTranspose, IWriteDelegate p_del_writeTranspose) {
			this(p_s_field, p_i_length, p_del_readTranspose, p_del_writeTranspose, null, null, -1, -1, -1, null, null, null, -1, null);
		}
		
		/**
		 * StructureElement constructor
		 * 
		 * @param p_s_field						name of field
		 * @param p_i_length					length of field within flr
		 * @param p_del_readFPNTranspose		method delegate to transpose a string value to a floating point number
		 * @param p_del_writeFPNTranspose		method delegate to transpose a floating point number to a string value with a specific format
		 */
		public StructureElement(String p_s_field, int p_i_length, IReadFPNDelegate p_del_readFPNTranspose, IWriteFPNDelegate p_del_writeFPNTranspose) {
			this(p_s_field, p_i_length, null, null, p_del_readFPNTranspose, p_del_writeFPNTranspose, -1, -1, -1, null, null, null, -1, null);
		}
		
		/**
		 * StructureElement constructor
		 * 
		 * @param p_s_field						name of field
		 * @param p_i_length					length of field within flr
		 * @param p_del_readFPNTranspose		method delegate to transpose a string value to a floating point number
		 * @param p_del_writeFPNTranspose		method delegate to transpose a floating point number to a string value with a specific format
		 * @param p_i_positionDecimalSeparator		position of decimal separator within a floating point number string (read only)
		 */
		public StructureElement(String p_s_field, int p_i_length, IReadFPNDelegate p_del_readFPNTranspose, IWriteFPNDelegate p_del_writeFPNTranspose, int p_i_positionDecimalSeparator) {
			this(p_s_field, p_i_length, null, null, p_del_readFPNTranspose, p_del_writeFPNTranspose, p_i_positionDecimalSeparator, -1, -1, null, null, null, -1, null);
		}
		
		/**
		 * StructureElement constructor
		 * 
		 * @param p_s_field						name of field
		 * @param p_i_length					length of field within flr
		 * @param p_del_readFPNTranspose		method delegate to transpose a string value to a floating point number
		 * @param p_del_writeFPNTranspose		method delegate to transpose a floating point number to a string value with a specific format
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 */
		public StructureElement(String p_s_field, int p_i_length, IReadFPNDelegate p_del_readFPNTranspose, IWriteFPNDelegate p_del_writeFPNTranspose, int p_i_amountDigits, int p_i_amountFractionDigits) {
			this(p_s_field, p_i_length, null, null, p_del_readFPNTranspose, p_del_writeFPNTranspose, p_i_amountDigits, p_i_amountDigits, p_i_amountFractionDigits, null, null, null, -1, null);
		}
		
		/**
		 * StructureElement constructor
		 * 
		 * @param p_s_field							name of field
		 * @param p_i_length						length of field within flr
		 * @param p_del_readFPNTranspose			method delegate to transpose a string value to a floating point number
		 * @param p_del_writeFPNTranspose			method delegate to transpose a floating point number to a string value with a specific format
		 * @param p_i_positionDecimalSeparator		position of decimal separator within a floating point number string (read only)
		 * @param p_i_amountDigits					amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits			amount of fractional digits for a floating point number (write only)
		 */
		public StructureElement(String p_s_field, int p_i_length, IReadFPNDelegate p_del_readFPNTranspose, IWriteFPNDelegate p_del_writeFPNTranspose, int p_i_positionDecimalSeparator, int p_i_amountDigits, int p_i_amountFractionDigits) {
			this(p_s_field, p_i_length, null, null, p_del_readFPNTranspose, p_del_writeFPNTranspose, p_i_positionDecimalSeparator, p_i_amountDigits, p_i_amountFractionDigits, null, null, null, -1, null);
		}
		
		/**
		 * StructureElement constructor, use '$' for decimal and group separator to use system separators
		 * 
		 * @param p_s_field						name of field
		 * @param p_i_length					length of field within flr
		 * @param p_del_readFPNTranspose		method delegate to transpose a string value to a floating point number
		 * @param p_del_writeFPNTranspose		method delegate to transpose a floating point number to a string value with a specific format
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @param p_s_decimalSeparator			string for decimal separator, use '$' for system settings
		 * @param p_s_groupSeparator			string for group separator, use '$' for system settings
		 */
		public StructureElement(String p_s_field, int p_i_length, IReadFPNDelegate p_del_readFPNTranspose, IWriteFPNDelegate p_del_writeFPNTranspose, int p_i_amountDigits, int p_i_amountFractionDigits, String p_s_decimalSeparator, String p_s_groupSeparator) {
			this(p_s_field, p_i_length, null, null, p_del_readFPNTranspose, p_del_writeFPNTranspose, p_i_amountDigits, p_i_amountDigits, p_i_amountFractionDigits, p_s_decimalSeparator, p_s_groupSeparator, null, -1, null);
		}
		
		/**
		 * StructureElement constructor, use '$' for decimal and group separator to use system separators
		 * 
		 * @param p_s_field						name of field
		 * @param p_i_length					length of field within flr
		 * @param p_del_readFPNTranspose		method delegate to transpose a string value to a floating point number
		 * @param p_del_writeFPNTranspose		method delegate to transpose a floating point number to a string value with a specific format
		 * @param p_i_positionDecimalSeparator		position of decimal separator within a floating point number string (read only)
		 * @param p_i_amountDigits				amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits		amount of fractional digits for a floating point number (write only)
		 * @param p_s_decimalSeparator			string for decimal separator, use '$' for system settings
		 * @param p_s_groupSeparator			string for group separator, use '$' for system settings
		 */
		public StructureElement(String p_s_field, int p_i_length, IReadFPNDelegate p_del_readFPNTranspose, IWriteFPNDelegate p_del_writeFPNTranspose, int p_i_positionDecimalSeparator, int p_i_amountDigits, int p_i_amountFractionDigits, String p_s_decimalSeparator, String p_s_groupSeparator) {
			this(p_s_field, p_i_length, null, null, p_del_readFPNTranspose, p_del_writeFPNTranspose, p_i_positionDecimalSeparator, p_i_amountDigits, p_i_amountFractionDigits, p_s_decimalSeparator, p_s_groupSeparator, null, -1, null);
		}
				
		/**
		 * StructureElement constructor, use '$' for decimal and group separator to use system separators
		 * 
		 * @param p_s_field							name of field
		 * @param p_i_length						length of field within flr
		 * @param p_del_readTranspose				method delegate to transpose a string value to an object of your choice
		 * @param p_del_writeTranspose				method delegate to transpose an object to a string value with a specific format
		 * @param p_del_readFPNTranspose			method delegate to transpose a string value to a floating point number
		 * @param p_del_writeFPNTranspose			method delegate to transpose a floating point number to a string value with a specific format
		 * @param p_i_positionDecimalSeparator		position of decimal separator within a floating point number string (read only)
		 * @param p_i_amountDigits					amount of digits for a floating point number (write only)
		 * @param p_i_amountFractionDigits			amount of fractional digits for a floating point number (write only)
		 * @param p_s_decimalSeparator				string for decimal separator, use '$' for system settings
		 * @param p_s_groupSeparator				string for group separator, use '$' for system settings
		 * @param p_s_constant						constant value within fixed length record
		 * @param p_i_subTypeAmount					known amount of sub-type instances
		 * @param p_o_subType						known class of sub-type instances
		 */
		public StructureElement(
			String p_s_field,
			int p_i_length,
			IReadDelegate p_del_readTranspose,
			IWriteDelegate p_del_writeTranspose,
			IReadFPNDelegate p_del_readFPNTranspose,
			IWriteFPNDelegate p_del_writeFPNTranspose,
			int p_i_positionDecimalSeparator,
			int p_i_amountDigits,
			int p_i_amountFractionDigits,
			String p_s_decimalSeparator,
			String p_s_groupSeparator,
			String p_s_constant,
            int p_i_subTypeAmount,
            Class<?> p_o_subType
		) {
			this.setField(p_s_field);
			this.setLength(p_i_length);
			this.setReadTranspose(p_del_readTranspose);
			this.setWriteTranspose(p_del_writeTranspose);
			this.setReadFPNTranspose(p_del_readFPNTranspose);
			this.setWriteFPNTranspose(p_del_writeFPNTranspose);
			this.setPositionDecimalSeparator(p_i_positionDecimalSeparator);
			this.setAmountDigits(p_i_amountDigits);
			this.setAmountFractionDigits(p_i_amountFractionDigits);
			this.setDecimalSeparator(p_s_decimalSeparator);
			this.setGroupSeparator(p_s_groupSeparator);
			this.setConstant(p_s_constant);
			this.setSubTypeAmount(p_i_subTypeAmount);
			this.setSubType(p_o_subType);
			this.setSubTypeKnownOverallLength(-1);
		}
		
		/**
		 * Checks if structure element object is equal to another structure element object
		 * 
		 * @param p_o_structureElement			structure element object for comparison
		 * @return								true - equal, false - not equal
		 */
		public boolean isEqual(StructureElement p_o_structureElement) {
			if (
				(this.getConstant().contentEquals(p_o_structureElement.getConstant())) &&
				(this.getField().contentEquals(p_o_structureElement.getField())) &&
				(this.getLength() == p_o_structureElement.getLength()) &&
				(this.getPositionDecimalSeparator() == p_o_structureElement.getPositionDecimalSeparator()) &&
				(this.getAmountDigits() == p_o_structureElement.getAmountDigits()) &&
				(this.getAmountFractionDigits() == p_o_structureElement.getAmountFractionDigits()) &&
				(this.getDecimalSeparator() == p_o_structureElement.getDecimalSeparator()) &&
				(this.getGroupSeparator() == p_o_structureElement.getGroupSeparator()) &&
				(this.getSubTypeAmount() == p_o_structureElement.getSubTypeAmount()) &&
                (((this.getSubType() == null) && (p_o_structureElement.getSubType() == null)) || ((this.getSubType() != null) && (this.getSubType().equals(p_o_structureElement.getSubType()))))
			) {
				return true;
			}
			
			return false;
		}
		
		/**
		 * Return all structure element fields in one string
		 */
		@Override
		public String toString() {
			String s_foo = "Structure Element: ";
			
			for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
				try {
					if (o_field.getName().startsWith("this$")) {
						continue;
					}
					
					s_foo += o_field.getName() + " = " + o_field.get(this).toString() + "|";
				} catch (Exception o_exc) {
					s_foo += o_field.getName() + " = ERR:AccessViolation|";
				}
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 1);
					
			return s_foo;
		}
	}
}
