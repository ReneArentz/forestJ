package net.forestany.forestj.lib.net.msg;

import net.forestany.forestj.lib.io.StandardTransposeMethods;

/**
 * Static methods for marshalling objects with fields of primitive types of supported types.
 */
public class Marshall {
	
	/* Fields */
	
	private static final java.util.List<Class<?>> a_allowedTypes = java.util.Arrays.asList(
		boolean.class,
		Boolean.class,
		Byte.class,
		byte.class,
		Character.class,
		char.class,
		Float.class,
		float.class,
		Double.class,
		double.class,
		Short.class,
		short.class,
		Integer.class,
		int.class,
		Long.class,
		long.class,
		String.class,
		java.util.Date.class,
		java.time.LocalTime.class,
		java.time.LocalDate.class,
		java.time.LocalDateTime.class,
		java.math.BigDecimal.class
	);
		
	/* Properties */
	
	/* Methods */
	
	/**
	 * empty constructor
	 */
	public Marshall() {
		
	}
	
	/**
	 * Marshall object with all fields of primitive types or supported types. Transfering data as big endian. Handle data as big endian. Do not use property methods to retrieve field values. 1 byte is used used to marshall the length of data.
	 * 
	 * @param p_o_object										object parameter
	 * @return													byte array of marshalled object
	 * @throws NullPointerException								parameter object is null
	 * @throws IllegalArgumentException							data length in bytes must be between 1..4
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws NoSuchMethodException							could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 		could not invoke method from object
	 * @throws IllegalAccessException							could not invoke method, access violation
	 */
	public static byte[] marshallObject(Object p_o_object) throws NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		return Marshall.marshallObject(p_o_object, 1);
	}
	
	/**
	 * Marshall object with all fields of primitive types or supported types. Transfering data as big endian. Handle data as big endian. Do not use property methods to retrieve field values.
	 * 
	 * @param p_o_object										object parameter
	 * @param p_i_dataLengthInBytes								define how many bytes are used to marshall the length of data
	 * @return													byte array of marshalled object
	 * @throws NullPointerException								parameter object is null
	 * @throws IllegalArgumentException							data length in bytes must be between 1..4
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws NoSuchMethodException							could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 		could not invoke method from object
	 * @throws IllegalAccessException							could not invoke method, access violation
	 */
	public static byte[] marshallObject(Object p_o_object, int p_i_dataLengthInBytes) throws NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		return Marshall.marshallObject(p_o_object, p_i_dataLengthInBytes, false);
	}
	
	/**
	 * Marshall object with all fields of primitive types or supported types. Transfering data as big endian. Handle data as big endian.
	 * 
	 * @param p_o_object										object parameter
	 * @param p_i_dataLengthInBytes								define how many bytes are used to marshall the length of data
	 * @param p_b_usePropertyMethods							access object parameter fields via property methods e.g. T getXYZ()
	 * @return													byte array of marshalled object
	 * @throws NullPointerException								parameter object is null
	 * @throws IllegalArgumentException							data length in bytes must be between 1..4
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws NoSuchMethodException							could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 		could not invoke method from object
	 * @throws IllegalAccessException							could not invoke method, access violation
	 */
	public static byte[] marshallObject(Object p_o_object, int p_i_dataLengthInBytes, boolean p_b_usePropertyMethods) throws NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		return Marshall.marshallObject(p_o_object, p_i_dataLengthInBytes, p_b_usePropertyMethods, false);
	}
	
	/**
	 * Marshall object with all fields of primitive types or supported types. Transfering data as big endian.
	 * 
	 * @param p_o_object										object parameter
	 * @param p_i_dataLengthInBytes								define how many bytes are used to marshall the length of data
	 * @param p_b_usePropertyMethods							access object parameter fields via property methods e.g. T getXYZ()
	 * @param p_b_systemUsesLittleEndian						(NOT IMPLEMENTED) true - current execution system uses little endian, false - current execution system uses big endian
	 * @return													byte array of marshalled object
	 * @throws NullPointerException								parameter object is null
	 * @throws IllegalArgumentException							data length in bytes must be between 1..4, object parameter is of type java.util.Collection or java.util.Map
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws NoSuchMethodException							could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 		could not invoke method from object
	 * @throws IllegalAccessException							could not invoke method, access violation
	 */
	public static byte[] marshallObject(Object p_o_object, int p_i_dataLengthInBytes, boolean p_b_usePropertyMethods, boolean p_b_systemUsesLittleEndian) throws NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		/* little endian system data is not supported right now */
		if (p_b_systemUsesLittleEndian) {
			throw new UnsupportedOperationException("Supporting little endian system data is NOT IMPLEMENTED");
		}
		
		/* check if object parameter is not null */
		if (p_o_object == null) {
			net.forestany.forestj.lib.Global.ilogWarning("Object parameter is null - sending 5 zero bytes");
			/* if object parameter is null, sending 5 zero bytes */
			return new byte[] { 0, 0, 0, 0, 0 };
		}
		
		/* check object parameter type */
		if ( (p_o_object instanceof java.util.Collection) || (p_o_object instanceof java.util.Map) ) {
			throw new IllegalArgumentException("Object parameter is of type java.util.Collection or java.util.Map");
		}

		/* data length parameter must be between 1..4 */
		if ( (p_i_dataLengthInBytes < 1) || (p_i_dataLengthInBytes > 4) ) {
			throw new IllegalArgumentException("Data length in bytes parameter must be between 1..4");
		}
		
		/* dynamic byte list of complete marshalled object */
		java.util.List<Byte> a_marshalledObject = new java.util.ArrayList<Byte>();
		int i_amountFields = 0;
		
		/* handle primitive supported type */
		if ( (Marshall.a_allowedTypes.contains(p_o_object.getClass())) || ( (p_o_object.getClass().isArray()) && (Marshall.a_allowedTypes.contains(p_o_object.getClass().getComponentType())) ) ) {
			/* dynamic byte list for field data length and data */
			java.util.List<Byte> a_dataLengthAndData = new java.util.ArrayList<Byte>();
			short sh_arrayAmount = 0;
			byte by_type = -1;
			String s_name = "";
			
			/* marshalling data by object information */
			java.util.concurrent.atomic.AtomicInteger o_typeAsAtomicInteger = new java.util.concurrent.atomic.AtomicInteger(-1);
			java.util.concurrent.atomic.AtomicInteger o_arrayAmountAsAtomicInteger = new java.util.concurrent.atomic.AtomicInteger(0);
			
			if (!marshallDataByFieldOrObjectInformation((p_o_object.getClass().isArray()) ? p_o_object.getClass().getComponentType() : p_o_object.getClass(), null, s_name, o_typeAsAtomicInteger, p_o_object.getClass().isArray(), false, p_o_object, p_i_dataLengthInBytes, a_dataLengthAndData, o_arrayAmountAsAtomicInteger))
			{
                throw new IllegalArgumentException("array elements for field/property '" + s_name + "' exceeds max. supported amount of elements(65535)");
            }
			
			by_type = o_typeAsAtomicInteger.byteValue();
			sh_arrayAmount = o_arrayAmountAsAtomicInteger.shortValue();
			
			/* check if field name is not longer than 255 characters, otherwise we must skip the field */
			if (s_name.length() > 255) {
				throw new IllegalArgumentException("name '" + s_name + "' is longer than 255 characters, which is not supported");
			}
			
			/* add name length to dynamic byte list */
			a_marshalledObject.add((byte)0);
			
			/* add type with endian info, array flag, and data length bits (2) */
			long l_typeWithAdditionalInfo = 0;
			l_typeWithAdditionalInfo |= (long)by_type;
			
			/* set information for little endian */
			if (p_b_systemUsesLittleEndian) {
				l_typeWithAdditionalInfo |= 0x8000;
			} else {
				l_typeWithAdditionalInfo &= ~(0x8000);
			}
			
			/* set information for array flag */
			if (p_o_object.getClass().isArray()) {
				l_typeWithAdditionalInfo |= 0x4000;
			} else {
				l_typeWithAdditionalInfo &= ~(0x4000);
			}
			
			/* set information for data length */
			if (p_i_dataLengthInBytes == 1) {
				l_typeWithAdditionalInfo &= ~(0x3000);
			} else if (p_i_dataLengthInBytes == 2) {
				l_typeWithAdditionalInfo |= 0x1000;
			} else if (p_i_dataLengthInBytes == 3) {
				l_typeWithAdditionalInfo |= 0x2000;
			} else if (p_i_dataLengthInBytes == 4) {
				l_typeWithAdditionalInfo |= 0x3000;
			}
			
			/* create byte array out of marshalled information with the length of '2' */
			byte[] a_info = net.forestany.forestj.lib.Helper.amountToNByteArray(l_typeWithAdditionalInfo, 2);
													net.forestany.forestj.lib.Global.ilogFiner("marshalled information bytes(2): " + net.forestany.forestj.lib.Helper.printByteArray( a_info, false ) );
			
			/* add marshalled information to dynamic byte list */
			net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(a_info, a_marshalledObject);
			
			/* create byte array out of marshalled array amount with the length of '2' */
			byte[] a_arrayAmount = net.forestany.forestj.lib.Helper.amountToNByteArray(sh_arrayAmount, 2);
													net.forestany.forestj.lib.Global.ilogFiner("marshalled array amount(2): " + net.forestany.forestj.lib.Helper.printByteArray( a_arrayAmount, false ) );
			
			/* add marshalled array amount to dynamic byte list */
			net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(a_arrayAmount, a_marshalledObject);
			
			if (a_dataLengthAndData.size() > 0) {
				/* create byte array out of data length and data */
				byte[] a_bytesDataLengthAndData = new byte[a_dataLengthAndData.size()];
				
				for (int i = 0; i < a_dataLengthAndData.size(); i++) {
					a_bytesDataLengthAndData[i] = a_dataLengthAndData.get(i);
				}
				
														net.forestany.forestj.lib.Global.ilogFiner("marshalled data length and data: " + net.forestany.forestj.lib.Helper.printByteArray(a_bytesDataLengthAndData, false) );
				
				/* add marshalled data length and data to dynamic byte list */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(a_bytesDataLengthAndData, a_marshalledObject);
			}
			
			String s_empty = "                              ";
			
			net.forestany.forestj.lib.Global.ilogFiner(
				"marshalled field: " +
				s_empty.substring(0, s_empty.length() - 18) +
				s_name + 
				s_empty.substring(0, s_empty.length() - s_name.length()) + 
				"" + 
				s_empty.substring(0, s_empty.length() - 0) + 
				((p_o_object.getClass().isArray()) ? p_o_object.getClass().getComponentType().getTypeName() : p_o_object.getClass().getTypeName()) + 
				s_empty.substring(0, s_empty.length() - ((p_o_object.getClass().isArray()) ? p_o_object.getClass().getComponentType().getTypeName().length() : p_o_object.getClass().getTypeName().length())) + 
				"" + 
				s_empty.substring(0, s_empty.length() - 0)
			);
		} else { /* handle object class */
			/* iterate each field of java parameter object */
			for (java.lang.reflect.Field o_field : p_o_object.getClass().getDeclaredFields()) {
				/* get value if field is public */
				boolean b_isPublic = java.lang.reflect.Modifier.isPublic( o_field.getModifiers() );
				
				/* if field is not public and we are not using property methods, so we must skip this field */
				if ( (!b_isPublic) && (!p_b_usePropertyMethods) ) {
															net.forestany.forestj.lib.Global.ilogFiner("field '" + o_field.getName()  + "' is not public and we are not using property methods, so we must skip this field");
					continue;
				}
				
				/* gather all field information */
				String s_name = o_field.getName();
				String s_isPublic = "Public: " + b_isPublic;
				Class<?> o_type = (Class<?>) ((o_field.getType().isArray()) ? o_field.getType().getComponentType() : o_field.getType());
				Class<?> o_genericType = null;
				short sh_arrayAmount = 0;
				byte by_type = -1;
				
				/* check if field is a list, map or set */
				if (
					(o_field.getType() == java.util.List.class) || 
					(o_field.getType() == java.util.Map.class)  || 
					(o_field.getType() == java.util.Set.class)
				) {
					/* generic map must have just two parameterized type declaration */
					if (
						(o_field.getType() == java.util.Map.class) &&
						( ((java.lang.reflect.ParameterizedType) o_field.getGenericType()).getActualTypeArguments().length == 2 )
					) {
						/* parameterized type declaration of key part of map must be 'java.lang.Integer' */
						if (((java.lang.reflect.ParameterizedType) o_field.getGenericType()).getActualTypeArguments()[0] != Integer.class) {
							continue;
						}
	
						o_genericType = (Class<?>)((java.lang.reflect.ParameterizedType) o_field.getGenericType()).getActualTypeArguments()[1];
					} else if ( ((java.lang.reflect.ParameterizedType) o_field.getGenericType()).getActualTypeArguments().length == 1 ) {
						/* generic list or set must have just one parameterized type declaration */
						o_genericType = (Class<?>)((java.lang.reflect.ParameterizedType) o_field.getGenericType()).getActualTypeArguments()[0];
					} else { /* parameterized type declaration which are not equal '1' is not supported, so we must skip this field */
																net.forestany.forestj.lib.Global.ilogFiner("parameterized type declaration which are not equal '1' is not supported, so we must skip this field '" + s_name + "'");
						continue;
					}
				}
				
				/* check if field name is not longer than 255 characters, otherwise we must skip the field */
				if (s_name.length() > 255) {
															net.forestany.forestj.lib.Global.ilogFiner("field name '" + s_name + "' is longer than 255 characters, which is not supported, so we must skip this field");
					continue;
				}
				
				/* dynamic byte list for field data length and data */
				java.util.List<Byte> a_dataLengthAndData = new java.util.ArrayList<Byte>();
				
				/* retrieve value from field */
				Object o_fieldValue = null;
				
				try {
					o_fieldValue = Marshall.getFieldValue(p_o_object, o_field, p_b_usePropertyMethods);
				} catch (Exception o_exc) {
					/* could not retrieve field value, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogFiner("could not retrieve field value from '" + s_name + "', so we must skip this field");
					continue;
				}
				
				/* marshalling data by field type information */
				try {
					java.util.concurrent.atomic.AtomicInteger o_typeAsAtomicInteger = new java.util.concurrent.atomic.AtomicInteger(-1);
					java.util.concurrent.atomic.AtomicInteger o_arrayAmountAsAtomicInteger = new java.util.concurrent.atomic.AtomicInteger(0);
					
					if (!marshallDataByFieldOrObjectInformation(o_type, o_genericType, s_name, o_typeAsAtomicInteger, o_field.getType().isArray(), o_genericType != null, o_fieldValue, p_i_dataLengthInBytes, a_dataLengthAndData, o_arrayAmountAsAtomicInteger))
					{
                        /* invalid type of generic list or invalid amount of elements, so we must skip this field */
                        continue;
                    }
					
					by_type = o_typeAsAtomicInteger.byteValue();
					sh_arrayAmount = o_arrayAmountAsAtomicInteger.shortValue();
				} catch (ClassCastException o_exc) {
					/* cast on field was not successful, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogWarning("cast on field '" + s_name + "' was not successful, so we must skip this field: " + o_exc);
					continue;
				}
				
				if (by_type == -1) {
					/* type of field is not supported for marshalling, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogFine("type of field '" + s_name + "' is not supported for marshalling, so we must skip this field");
					continue;
				}
				
				i_amountFields++;
				
				if (i_amountFields > 65535) {
					/* amount of fields exceeds max. supported value, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogWarning("amount of fields '" + i_amountFields + "' exceeds max. supported value(65535), so we must skip this field");
					continue;
				}
				
				/* add name length to dynamic byte list */
				a_marshalledObject.add((byte)s_name.length());
				
				/* get string bytes as utf-8 */
				byte[] by_foo = s_name.getBytes(java.nio.charset.StandardCharsets.UTF_8);
				
				/* add name to dynamic byte list */
				for (byte by_byte : by_foo) {
					a_marshalledObject.add(by_byte);
				}
				
				/* add type with endian info, array flag, and data length bits (2) */
				long l_typeWithAdditionalInfo = 0;
				l_typeWithAdditionalInfo |= (long)by_type;
				
				/* set information for little endian */
				if (p_b_systemUsesLittleEndian) {
					l_typeWithAdditionalInfo |= 0x8000;
				} else {
					l_typeWithAdditionalInfo &= ~(0x8000);
				}
				
				/* set information for array flag */
				if ( (o_field.getType().isArray()) || (o_genericType != null) ) {
					l_typeWithAdditionalInfo |= 0x4000;
				} else {
					l_typeWithAdditionalInfo &= ~(0x4000);
				}
				
				/* set information for data length */
				if (p_i_dataLengthInBytes == 1) {
					l_typeWithAdditionalInfo &= ~(0x3000);
				} else if (p_i_dataLengthInBytes == 2) {
					l_typeWithAdditionalInfo |= 0x1000;
				} else if (p_i_dataLengthInBytes == 3) {
					l_typeWithAdditionalInfo |= 0x2000;
				} else if (p_i_dataLengthInBytes == 4) {
					l_typeWithAdditionalInfo |= 0x3000;
				}
				
				/* create byte array out of marshalled information with the length of '2' */
				byte[] a_info = net.forestany.forestj.lib.Helper.amountToNByteArray(l_typeWithAdditionalInfo, 2);
														net.forestany.forestj.lib.Global.ilogFiner("marshalled information bytes(2): " + net.forestany.forestj.lib.Helper.printByteArray( a_info, false ) );
				
				/* add marshalled information to dynamic byte list */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(a_info, a_marshalledObject);
				
				/* create byte array out of marshalled array amount with the length of '2' */
				byte[] a_arrayAmount = net.forestany.forestj.lib.Helper.amountToNByteArray(sh_arrayAmount, 2);
														net.forestany.forestj.lib.Global.ilogFiner("marshalled array amount(2): " + net.forestany.forestj.lib.Helper.printByteArray( a_arrayAmount, false ) );
				
				/* add marshalled array amount to dynamic byte list */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(a_arrayAmount, a_marshalledObject);
				
				if (a_dataLengthAndData.size() > 0) {
					/* create byte array out of data length and data */
					byte[] a_bytesDataLengthAndData = new byte[a_dataLengthAndData.size()];
					
					for (int i = 0; i < a_dataLengthAndData.size(); i++) {
						a_bytesDataLengthAndData[i] = a_dataLengthAndData.get(i);
					}
					
															net.forestany.forestj.lib.Global.ilogFiner("marshalled data length and data: " + net.forestany.forestj.lib.Helper.printByteArray(a_bytesDataLengthAndData, false) );
					
					/* add marshalled data length and data to dynamic byte list */
					net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(a_bytesDataLengthAndData, a_marshalledObject);
				}
				
				String s_empty = "                              ";
				
				net.forestany.forestj.lib.Global.ilogFiner(
					"marshalled field: " +
					s_empty.substring(0, s_empty.length() - 18) +
					s_name + 
					s_empty.substring(0, s_empty.length() - s_name.length()) + 
					s_isPublic + 
					s_empty.substring(0, s_empty.length() - s_isPublic.length()) + 
					o_type.getTypeName() + 
					s_empty.substring(0, s_empty.length() - o_type.getTypeName().length()) + 
					((o_genericType != null) ? o_genericType.getTypeName() : "") + 
					s_empty.substring(0, s_empty.length() - ((o_genericType != null) ? o_genericType.getTypeName().length() : 0))
				);
			}
		}
		
		/* create byte array out of amount of marshalled fields with the length of '2' */
		byte[] a_fields = net.forestany.forestj.lib.Helper.amountToNByteArray(i_amountFields, 2);
												net.forestany.forestj.lib.Global.ilogFiner("amount of marshalled fields(2): " + net.forestany.forestj.lib.Helper.printByteArray( a_fields, false ) );
		
		byte[] a_return = new byte[a_fields.length + a_marshalledObject.size()];
		
		/* assume amount of fields to return array */
		a_return[0] = a_fields[0];
		a_return[1] = a_fields[1];
		
		/* assume marshalled object to return array */
		for (int i = 0; i < a_marshalledObject.size(); i++) {
			a_return[i + 2] = a_marshalledObject.get(i);
		}
		
		return a_return;
	}
	
	/**
	 * Retrieve field value from an object
	 * 
	 * @param p_o_object					object instance where we want to retrieve a field's value
	 * @param p_o_field						all information about the field
	 * @param p_b_usePropertyMethods		access object parameter fields via property methods e.g. T getXYZ()
	 * @return								field's value as object
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 */
	private static Object getFieldValue(Object p_o_object, java.lang.reflect.Field p_o_field, boolean p_b_usePropertyMethods) throws NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		Object o_foo = null;
		
		/* access field values directly over public fields */
		if (!p_b_usePropertyMethods) {
			/* call field directly to get object data values */
			try {
				o_foo = p_o_field.get(p_o_object);
			} catch (Exception o_exc) {
				throw new NoSuchFieldException("Class instance field[" + p_o_field.getName() + "] returns no data or is not accessible");
			}
		} else { /* access field values over public getter methods */
			java.lang.reflect.Method o_method = null;
			
			/* store get-property-method of java type object */
			try {
				o_method = p_o_object.getClass().getDeclaredMethod("get" + p_o_field.getName());
				
				if (!java.lang.reflect.Modifier.isPublic( o_method.getModifiers() )) {
					throw new IllegalAccessException("Method [" + o_method.getName() + "] from object with class(" + p_o_object.getClass().getTypeName() + ") is not public and in this way it is not accessible");
				}
			} catch (NoSuchMethodException | SecurityException o_exc) {
				throw new NoSuchMethodException("Class instance method[" + "get" + p_o_field.getName() + "] does not exist within object with class(" + p_o_object.getClass().getTypeName() + ")");
			}
			
			/* invoke get-property-method to get object data of current element */
			o_foo = o_method.invoke(p_o_object);
		}
		
		return o_foo;
	}
	
	/**
	 * Marshalling data by field or object information
	 * 
	 * @param p_o_type					expected type of data which should be marshalled
	 * @param p_o_genericType			expected type of generic list data which should be marshalled
	 * @param p_s_name					name of the current object field or empty string for primitive object only
	 * @param p_by_type					atomic integer to change this parameter by reference, returning recognized type as byte value for marshalled data
	 * @param p_b_isArray				true - marshalled data is an array, false - marshalled data is not an array
	 * @param p_b_isGenericList			true - marshalled data is generic list, false - marshalled data is not a generic list
	 * @param p_o_value					value object of data which should be marshalled
	 * @param p_i_dataLengthInBytes		define how many bytes are used to marshall the length of data
	 * @param p_a_dataLengthAndData		array of bytes where we want to marshall our content
	 * @param p_sh_arrayAmount			atomic integer to change this parameter by reference, returning amount of array elements as byte value for marshalled data
	 * @return							true - marshalling was successful, false - exceeds max. supported amount of array elements or invalid type of generic list or invalid amount of elements
	 */
	private static boolean marshallDataByFieldOrObjectInformation(Class<?> p_o_type, Class<?> p_o_genericType, String p_s_name, java.util.concurrent.atomic.AtomicInteger p_by_type, boolean p_b_isArray, boolean p_b_isGenericList, Object p_o_value, int p_i_dataLengthInBytes, java.util.List<Byte> p_a_dataLengthAndData, java.util.concurrent.atomic.AtomicInteger p_sh_arrayAmount) {
		if ( (p_o_type == Boolean.class) || (p_o_genericType == Boolean.class) || (p_o_type == boolean.class) || (p_o_genericType == boolean.class) ) {
			p_by_type.set(0);
			
			if ( (!p_b_isArray) && (!p_b_isGenericList) ) {
				boolean b_foo = (boolean)p_o_value;
				
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(1L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				if (b_foo) {
					p_a_dataLengthAndData.add((byte)1);
				} else {
					p_a_dataLengthAndData.add((byte)0);
				}
			} else if (p_b_isArray) {
				boolean[] a_foo = (boolean[])p_o_value;
				
				if (a_foo != null) {
					if (a_foo.length > 65535) {
						/* array elements for this field exceeds max. supported amount of elements, so we must skip this field */
																net.forestany.forestj.lib.Global.ilogWarning("array elements for field '" + p_s_name + "' exceeds max. supported amount of elements(65535), so we must skip this field");
						return false;
					}
					
					p_sh_arrayAmount.set(a_foo.length);
					
					for (boolean b_foo : a_foo) {
						/* add data length */
						net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(1L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
						
						/* add data */
						if (b_foo) {
							p_a_dataLengthAndData.add((byte)1);
						} else {
							p_a_dataLengthAndData.add((byte)0);
						}
					}
				}
			} else if (p_b_isGenericList) {
				p_sh_arrayAmount.set(Marshall.iterateGenericListOrMap(Boolean.class, p_o_value, p_a_dataLengthAndData, p_i_dataLengthInBytes));
				
				if (p_sh_arrayAmount.get() < 0) {
					/* invalid type of generic list or invalid amount of elements, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogWarning("invalid type of generic list or invalid amount of elements for field '" + p_s_name + "' - [generic type = '" + p_o_genericType.getTypeName() + "'], so we must skip this field");
					return false;
				}
			}
		} else if ( (p_o_type == Byte.class) || (p_o_genericType == Byte.class) || (p_o_type == byte.class) || (p_o_genericType == byte.class) ) {
			p_by_type.set(1);
			
			if ( (!p_b_isArray) && (!p_b_isGenericList) ) {
				byte by_foo = (byte)p_o_value;
				
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(1L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				p_a_dataLengthAndData.add(by_foo);
			} else if (p_b_isArray) {
				byte[] a_foo = (byte[])p_o_value;
				
				if (a_foo != null) {
					if (a_foo.length > 65535) {
						/* array elements for this field exceeds max. supported amount of elements, so we must skip this field */
																net.forestany.forestj.lib.Global.ilogWarning("array elements for field '" + p_s_name + "' exceeds max. supported amount of elements(65535), so we must skip this field");
						return false;
					}
					
					p_sh_arrayAmount.set(a_foo.length);
					
					for (byte by_foo : a_foo) {
						/* add data length */
						net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(1L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
						
						/* add data */
						p_a_dataLengthAndData.add(by_foo);
					}
				}
			} else if (p_b_isGenericList) {
				p_sh_arrayAmount.set(Marshall.iterateGenericListOrMap(Byte.class, p_o_value, p_a_dataLengthAndData, p_i_dataLengthInBytes));
				
				if (p_sh_arrayAmount.get() < 0) {
					/* invalid type of generic list or invalid amount of elements, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogWarning("invalid type of generic list or invalid amount of elements for field '" + p_s_name + "' - [generic type = '" + p_o_genericType.getTypeName() + "'], so we must skip this field");
					return false;
				}
			}
		} else if ( (p_o_type == Character.class) || (p_o_genericType == Character.class) || (p_o_type == char.class) || (p_o_genericType == char.class) ) {
			p_by_type.set(3);
			
			if ( (!p_b_isArray) && (!p_b_isGenericList) ) {
				char c_foo = (char)p_o_value;
				
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(1L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				p_a_dataLengthAndData.add((byte)c_foo);
			} else if (p_b_isArray) {
				char[] a_foo = (char[])p_o_value;
				
				if (a_foo == null) {
					p_sh_arrayAmount.set(0);
				} else {
					if (a_foo.length > 65535) {
						/* array elements for this field exceeds max. supported amount of elements, so we must skip this field */
																net.forestany.forestj.lib.Global.ilogWarning("array elements for field '" + p_s_name + "' exceeds max. supported amount of elements(65535), so we must skip this field");
						return false;
					}
					
					p_sh_arrayAmount.set(a_foo.length);
					
					for (char c_foo : a_foo) {
						/* add data length */
						net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(1L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
						
						/* add data */
						p_a_dataLengthAndData.add((byte)c_foo);
					}
				}
			} else if (p_b_isGenericList) {
				p_sh_arrayAmount.set(Marshall.iterateGenericListOrMap(Character.class, p_o_value, p_a_dataLengthAndData, p_i_dataLengthInBytes));
				
				if (p_sh_arrayAmount.get() < 0) {
					/* invalid type of generic list or invalid amount of elements, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogWarning("invalid type of generic list or invalid amount of elements for field '" + p_s_name + "' - [generic type = '" + p_o_genericType.getTypeName() + "'], so we must skip this field");
					return false;
				}
			}
		} else if ( (p_o_type == Float.class) || (p_o_genericType == Float.class) || (p_o_type == float.class) || (p_o_genericType == float.class) ) {
			p_by_type.set(4);
			
			if ( (!p_b_isArray) && (!p_b_isGenericList) ) {
				float f_foo = (float)p_o_value;
				
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(4L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				int i_bits = Float.floatToIntBits(f_foo);
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.intToByteArray(i_bits), p_a_dataLengthAndData, 4);
			} else if (p_b_isArray) {
				float[] a_foo = (float[])p_o_value;
				
				if (a_foo != null) {
					if (a_foo.length > 65535) {
						/* array elements for this field exceeds max. supported amount of elements, so we must skip this field */
																net.forestany.forestj.lib.Global.ilogWarning("array elements for field '" + p_s_name + "' exceeds max. supported amount of elements(65535), so we must skip this field");
						return false;
					}
					
					p_sh_arrayAmount.set(a_foo.length);
					
					for (float f_foo : a_foo) {
						/* add data length */
						net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(4L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
						
						/* add data */
						int i_bits = Float.floatToIntBits(f_foo);
						net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.intToByteArray(i_bits), p_a_dataLengthAndData, 4);
					}
				}
			} else if (p_b_isGenericList) {
				p_sh_arrayAmount.set(Marshall.iterateGenericListOrMap(Float.class, p_o_value, p_a_dataLengthAndData, p_i_dataLengthInBytes));
				
				if (p_sh_arrayAmount.get() < 0) {
					/* invalid type of generic list or invalid amount of elements, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogWarning("invalid type of generic list or invalid amount of elements for field '" + p_s_name + "' - [generic type = '" + p_o_genericType.getTypeName() + "'], so we must skip this field");
					return false;
				}
			}
		} else if ( (p_o_type == Double.class) || (p_o_genericType == Double.class) || (p_o_type == double.class) || (p_o_genericType == double.class) ) {
			p_by_type.set(5);
			
			if ( (!p_b_isArray) && (!p_b_isGenericList) ) {
				double d_foo = (double)p_o_value;
				
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(8L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				long l_bits = Double.doubleToLongBits(d_foo);
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.longToByteArray(l_bits), p_a_dataLengthAndData, 8);
			} else if (p_b_isArray) {
				double[] a_foo = (double[])p_o_value;
				
				if (a_foo != null) {
					if (a_foo.length > 65535) {
						/* array elements for this field exceeds max. supported amount of elements, so we must skip this field */
																net.forestany.forestj.lib.Global.ilogWarning("array elements for field '" + p_s_name + "' exceeds max. supported amount of elements(65535), so we must skip this field");
						return false;
					}
					
					p_sh_arrayAmount.set(a_foo.length);
					
					for (double d_foo : a_foo) {
						/* add data length */
						net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(8L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
						
						/* add data */
						long l_bits = Double.doubleToLongBits(d_foo);
						net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.longToByteArray(l_bits), p_a_dataLengthAndData, 8);
					}
				}
			} else if (p_b_isGenericList) {
				p_sh_arrayAmount.set(Marshall.iterateGenericListOrMap(Double.class, p_o_value, p_a_dataLengthAndData, p_i_dataLengthInBytes));
				
				if (p_sh_arrayAmount.get() < 0) {
					/* invalid type of generic list or invalid amount of elements, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogWarning("invalid type of generic list or invalid amount of elements for field '" + p_s_name + "' - [generic type = '" + p_o_genericType.getTypeName() + "'], so we must skip this field");
					return false;
				}
			}
		} else if ( (p_o_type == Short.class) || (p_o_genericType == Short.class) || (p_o_type == short.class) || (p_o_genericType == short.class) ) {
			p_by_type.set(6);
			
			if ( (!p_b_isArray) && (!p_b_isGenericList) ) {
				short sh_foo = (short)p_o_value;
				
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(2L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */					
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.shortToByteArray(sh_foo), p_a_dataLengthAndData, 2);
			} else if (p_b_isArray) {
				short[] a_foo = (short[])p_o_value;
				
				if (a_foo != null) {
					if (a_foo.length > 65535) {
						/* array elements for this field exceeds max. supported amount of elements, so we must skip this field */
																net.forestany.forestj.lib.Global.ilogWarning("array elements for field '" + p_s_name + "' exceeds max. supported amount of elements(65535), so we must skip this field");
						return false;
					}
					
					p_sh_arrayAmount.set(a_foo.length);
					
					for (short sh_foo : a_foo) {
						/* add data length */
						net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(2L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
													
						/* add data */
						net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.shortToByteArray(sh_foo), p_a_dataLengthAndData, 2);
					}
				}
			} else if (p_b_isGenericList) {
				p_sh_arrayAmount.set(Marshall.iterateGenericListOrMap(Short.class, p_o_value, p_a_dataLengthAndData, p_i_dataLengthInBytes));
				
				if (p_sh_arrayAmount.get() < 0) {
					/* invalid type of generic list or invalid amount of elements, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogWarning("invalid type of generic list or invalid amount of elements for field '" + p_s_name + "' - [generic type = '" + p_o_genericType.getTypeName() + "'], so we must skip this field");
					return false;
				}
			}
		} else if ( (p_o_type == Integer.class) || (p_o_genericType == Integer.class) || (p_o_type == int.class) || (p_o_genericType == int.class) ) {
			p_by_type.set(8);
			
			if ( (!p_b_isArray) && (!p_b_isGenericList) ) {
				int i_foo = (int)p_o_value;
				
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(4L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
					
				/* add data */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.intToByteArray(i_foo), p_a_dataLengthAndData, 4);
			} else if (p_b_isArray) {
				int[] a_foo = (int[])p_o_value;
				
				if (a_foo != null) {
					if (a_foo.length > 65535) {
						/* array elements for this field exceeds max. supported amount of elements, so we must skip this field */
																net.forestany.forestj.lib.Global.ilogWarning("array elements for field '" + p_s_name + "' exceeds max. supported amount of elements(65535), so we must skip this field");
						return false;
					}
					
					p_sh_arrayAmount.set(a_foo.length);
					
					for (int i_foo : a_foo) {
						/* add data length */
						net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(4L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
						
						/* add data */
						net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.intToByteArray(i_foo), p_a_dataLengthAndData, 4);
					}
				}
			} else if (p_b_isGenericList) {
				p_sh_arrayAmount.set(Marshall.iterateGenericListOrMap(Integer.class, p_o_value, p_a_dataLengthAndData, p_i_dataLengthInBytes));
				
				if (p_sh_arrayAmount.get() < 0) {
					/* invalid type of generic list or invalid amount of elements, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogWarning("invalid type of generic list or invalid amount of elements for field '" + p_s_name + "' - [generic type = '" + p_o_genericType.getTypeName() + "'], so we must skip this field");
					return false;
				}
			}
		} else if ( (p_o_type == Long.class) || (p_o_genericType == Long.class) || (p_o_type == long.class) || (p_o_genericType == long.class) ) {
			p_by_type.set(10);
			
			if ( (!p_b_isArray) && (!p_b_isGenericList) ) {
				long l_foo = (long)p_o_value;
				
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(8L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.longToByteArray(l_foo), p_a_dataLengthAndData, 8);
			} else if (p_b_isArray) {
				long[] a_foo = (long[])p_o_value;
				
				if (a_foo != null) {
					if (a_foo.length > 65535) {
						/* array elements for this field exceeds max. supported amount of elements, so we must skip this field */
																net.forestany.forestj.lib.Global.ilogWarning("array elements for field '" + p_s_name + "' exceeds max. supported amount of elements(65535), so we must skip this field");
						return false;
					}
					
					p_sh_arrayAmount.set(a_foo.length);
					
					for (long l_foo : a_foo) {
						/* add data length */
						net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(8L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
						
						/* add data */
						net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.longToByteArray(l_foo), p_a_dataLengthAndData, 8);
					}
				}
			} else if (p_b_isGenericList) {
				p_sh_arrayAmount.set(Marshall.iterateGenericListOrMap(Long.class, p_o_value, p_a_dataLengthAndData, p_i_dataLengthInBytes));
				
				if (p_sh_arrayAmount.get() < 0) {
					/* invalid type of generic list or invalid amount of elements, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogWarning("invalid type of generic list or invalid amount of elements for field '" + p_s_name + "' - [generic type = '" + p_o_genericType.getTypeName() + "'], so we must skip this field");
					return false;
				}
			}
		} else if ( (p_o_type == String.class) || (p_o_genericType == String.class) ) {
			p_by_type.set(12);
			
			if ( (!p_b_isArray) && (!p_b_isGenericList) ) {
				String s_foo = (String)p_o_value;
				
				if (s_foo != null) {
					/* get string bytes as utf-8 */
					byte[] by_foo = s_foo.getBytes(java.nio.charset.StandardCharsets.UTF_8);
					
					/* add data length */
					net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(by_foo.length, p_i_dataLengthInBytes), p_a_dataLengthAndData);
					
					/* add data */
					for (byte by_byte : by_foo) {
						p_a_dataLengthAndData.add(by_byte);
					}
				} else {
					/* add data length '0' */
					net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				}
			} else if (p_b_isArray) {
				String[] a_foo = (String[])p_o_value;
				
				if (a_foo != null) {
					if (a_foo.length > 65535) {
						/* array elements for this field exceeds max. supported amount of elements, so we must skip this field */
																net.forestany.forestj.lib.Global.ilogWarning("array elements for field '" + p_s_name + "' exceeds max. supported amount of elements(65535), so we must skip this field");
						return false;
					}
					
					p_sh_arrayAmount.set(a_foo.length);
					
					for (String s_foo : a_foo) {
						if (s_foo != null) {
							/* get string bytes as utf-8 */
							byte[] by_foo = s_foo.getBytes(java.nio.charset.StandardCharsets.UTF_8);
							
							/* add data length */
							net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(by_foo.length, p_i_dataLengthInBytes), p_a_dataLengthAndData);
							
							/* add data */
							for (byte by_byte : by_foo) {
								p_a_dataLengthAndData.add(by_byte);
							}
						} else {
							/* add data length '0' */
							net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0, p_i_dataLengthInBytes), p_a_dataLengthAndData);
						}
					}
				}
			} else if (p_b_isGenericList) {
				p_sh_arrayAmount.set(Marshall.iterateGenericListOrMap(String.class, p_o_value, p_a_dataLengthAndData, p_i_dataLengthInBytes));
				
				if (p_sh_arrayAmount.get() < 0) {
					/* invalid type of generic list or invalid amount of elements, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogWarning("invalid type of generic list or invalid amount of elements for field '" + p_s_name + "' - [generic type = '" + p_o_genericType.getTypeName() + "'], so we must skip this field");
					return false;
				}
			}
		} else if ( (p_o_type == java.util.Date.class) || (p_o_genericType == java.util.Date.class) ) {
			p_by_type.set(13);
			
			if ( (!p_b_isArray) && (!p_b_isGenericList) ) {
				java.util.Date o_foo = (java.util.Date)p_o_value;
				
				if (o_foo != null) {
					String s_foo = net.forestany.forestj.lib.Helper.utilDateToISO8601UTC(o_foo);
					
					/* get string bytes as utf-8 */
					byte[] by_foo = s_foo.getBytes(java.nio.charset.StandardCharsets.UTF_8);
					
					/* add data length */
					net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(by_foo.length, p_i_dataLengthInBytes), p_a_dataLengthAndData);
					
					/* add data */
					for (byte by_byte : by_foo) {
						p_a_dataLengthAndData.add(by_byte);
					}
				} else {
					/* add data length '0' */
					net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				}
			} else if (p_b_isArray) {
				java.util.Date[] a_foo = (java.util.Date[])p_o_value;
				
				if (a_foo != null) {
					if (a_foo.length > 65535) {
						/* array elements for this field exceeds max. supported amount of elements, so we must skip this field */
																net.forestany.forestj.lib.Global.ilogWarning("array elements for field '" + p_s_name + "' exceeds max. supported amount of elements(65535), so we must skip this field");
						return false;
					}
					
					p_sh_arrayAmount.set(a_foo.length);
					
					for (java.util.Date o_foo : a_foo) {
						if (o_foo != null) {
							String s_foo = net.forestany.forestj.lib.Helper.utilDateToISO8601UTC(o_foo);
							
							/* get string bytes as utf-8 */
							byte[] by_foo = s_foo.getBytes(java.nio.charset.StandardCharsets.UTF_8);
							
							/* add data length */
							net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(by_foo.length, p_i_dataLengthInBytes), p_a_dataLengthAndData);
							
							/* add data */
							for (byte by_byte : by_foo) {
								p_a_dataLengthAndData.add(by_byte);
							}
						} else {
							/* add data length '0' */
							net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0, p_i_dataLengthInBytes), p_a_dataLengthAndData);
						}
					}
				}
			} else if (p_b_isGenericList) {
				p_sh_arrayAmount.set(Marshall.iterateGenericListOrMap(java.util.Date.class, p_o_value, p_a_dataLengthAndData, p_i_dataLengthInBytes));
				
				if (p_sh_arrayAmount.get() < 0) {
					/* invalid type of generic list or invalid amount of elements, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogWarning("invalid type of generic list or invalid amount of elements for field '" + p_s_name + "' - [generic type = '" + p_o_genericType.getTypeName() + "'], so we must skip this field");
					return false;
				}
			}
		} else if ( (p_o_type == java.time.LocalTime.class) || (p_o_genericType == java.time.LocalTime.class) ) {
			p_by_type.set(13);
			
			if ( (!p_b_isArray) && (!p_b_isGenericList) ) {
				java.time.LocalTime o_foo = (java.time.LocalTime)p_o_value;
				
				if (o_foo != null) {
					String s_foo = net.forestany.forestj.lib.Helper.toISO8601UTC( java.time.LocalDateTime.of(java.time.LocalDate.of(9999, 1, 1), o_foo) );
					
					/* get string bytes as utf-8 */
					byte[] by_foo = s_foo.getBytes(java.nio.charset.StandardCharsets.UTF_8);
					
					/* add data length */
					net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(by_foo.length, p_i_dataLengthInBytes), p_a_dataLengthAndData);
					
					/* add data */
					for (byte by_byte : by_foo) {
						p_a_dataLengthAndData.add(by_byte);
					}
				} else {
					/* add data length '0' */
					net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				}
			} else if (p_b_isArray) {
				java.time.LocalTime[] a_foo = (java.time.LocalTime[])p_o_value;
				
				if (a_foo != null) {
					if (a_foo.length > 65535) {
						/* array elements for this field exceeds max. supported amount of elements, so we must skip this field */
																net.forestany.forestj.lib.Global.ilogWarning("array elements for field '" + p_s_name + "' exceeds max. supported amount of elements(65535), so we must skip this field");
						return false;
					}
					
					p_sh_arrayAmount.set(a_foo.length);
					
					for (java.time.LocalTime o_foo : a_foo) {
						if (o_foo != null) {
							String s_foo = net.forestany.forestj.lib.Helper.toISO8601UTC( java.time.LocalDateTime.of(java.time.LocalDate.of(9999, 1, 1), o_foo) );
							
							/* get string bytes as utf-8 */
							byte[] by_foo = s_foo.getBytes(java.nio.charset.StandardCharsets.UTF_8);
							
							/* add data length */
							net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(by_foo.length, p_i_dataLengthInBytes), p_a_dataLengthAndData);
							
							/* add data */
							for (byte by_byte : by_foo) {
								p_a_dataLengthAndData.add(by_byte);
							}
						} else {
							/* add data length '0' */
							net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0, p_i_dataLengthInBytes), p_a_dataLengthAndData);
						}
					}
				}
			} else if (p_b_isGenericList) {
				p_sh_arrayAmount.set(Marshall.iterateGenericListOrMap(java.time.LocalTime.class, p_o_value, p_a_dataLengthAndData, p_i_dataLengthInBytes));
				
				if (p_sh_arrayAmount.get() < 0) {
					/* invalid type of generic list or invalid amount of elements, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogWarning("invalid type of generic list or invalid amount of elements for field '" + p_s_name + "' - [generic type = '" + p_o_genericType.getTypeName() + "'], so we must skip this field");
					return false;
				}
			}
		} else if ( (p_o_type == java.time.LocalDate.class) || (p_o_genericType == java.time.LocalDate.class) ) {
			p_by_type.set(13);
			
			if ( (!p_b_isArray) && (!p_b_isGenericList) ) {
				java.time.LocalDate o_foo = (java.time.LocalDate)p_o_value;
				
				if (o_foo != null) {
					String s_foo = net.forestany.forestj.lib.Helper.toISO8601UTC( java.time.LocalDateTime.of(o_foo, java.time.LocalTime.of(0, 0, 0)) );
					
					/* get string bytes as utf-8 */
					byte[] by_foo = s_foo.getBytes(java.nio.charset.StandardCharsets.UTF_8);
					
					/* add data length */
					net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(by_foo.length, p_i_dataLengthInBytes), p_a_dataLengthAndData);
					
					/* add data */
					for (byte by_byte : by_foo) {
						p_a_dataLengthAndData.add(by_byte);
					}
				} else {
					/* add data length '0' */
					net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				}
			} else if (p_b_isArray) {
				java.time.LocalDate[] a_foo = (java.time.LocalDate[])p_o_value;
				
				if (a_foo != null) {
					if (a_foo.length > 65535) {
						/* array elements for this field exceeds max. supported amount of elements, so we must skip this field */
																net.forestany.forestj.lib.Global.ilogWarning("array elements for field '" + p_s_name + "' exceeds max. supported amount of elements(65535), so we must skip this field");
						return false;
					}
					
					p_sh_arrayAmount.set(a_foo.length);
					
					for (java.time.LocalDate o_foo : a_foo) {
						if (o_foo != null) {
							String s_foo = net.forestany.forestj.lib.Helper.toISO8601UTC( java.time.LocalDateTime.of(o_foo, java.time.LocalTime.of(0, 0, 0)) );
							
							/* get string bytes as utf-8 */
							byte[] by_foo = s_foo.getBytes(java.nio.charset.StandardCharsets.UTF_8);
							
							/* add data length */
							net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(by_foo.length, p_i_dataLengthInBytes), p_a_dataLengthAndData);
							
							/* add data */
							for (byte by_byte : by_foo) {
								p_a_dataLengthAndData.add(by_byte);
							}
						} else {
							/* add data length '0' */
							net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0, p_i_dataLengthInBytes), p_a_dataLengthAndData);
						}
					}
				}
			} else if (p_b_isGenericList) {
				p_sh_arrayAmount.set(Marshall.iterateGenericListOrMap(java.time.LocalDate.class, p_o_value, p_a_dataLengthAndData, p_i_dataLengthInBytes));
				
				if (p_sh_arrayAmount.get() < 0) {
					/* invalid type of generic list or invalid amount of elements, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogWarning("invalid type of generic list or invalid amount of elements for field '" + p_s_name + "' - [generic type = '" + p_o_genericType.getTypeName() + "'], so we must skip this field");
					return false;
				}
			}
		} else if ( (p_o_type == java.time.LocalDateTime.class) || (p_o_genericType == java.time.LocalDateTime.class) ) {
			p_by_type.set(13);
			
			if ( (!p_b_isArray) && (!p_b_isGenericList) ) {
				java.time.LocalDateTime o_foo = (java.time.LocalDateTime)p_o_value;
				
				if (o_foo != null) {
					String s_foo = net.forestany.forestj.lib.Helper.toISO8601UTC( o_foo );
					
					/* get string bytes as utf-8 */
					byte[] by_foo = s_foo.getBytes(java.nio.charset.StandardCharsets.UTF_8);
					
					/* add data length */
					net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(by_foo.length, p_i_dataLengthInBytes), p_a_dataLengthAndData);
					
					/* add data */
					for (byte by_byte : by_foo) {
						p_a_dataLengthAndData.add(by_byte);
					}
				} else {
					/* add data length '0' */
					net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				}
			} else if (p_b_isArray) {
				java.time.LocalDateTime[] a_foo = (java.time.LocalDateTime[])p_o_value;
				
				if (a_foo != null) {
					if (a_foo.length > 65535) {
						/* array elements for this field exceeds max. supported amount of elements, so we must skip this field */
																net.forestany.forestj.lib.Global.ilogWarning("array elements for field '" + p_s_name + "' exceeds max. supported amount of elements(65535), so we must skip this field");
						return false;
					}
					
					p_sh_arrayAmount.set(a_foo.length);
					
					for (java.time.LocalDateTime o_foo : a_foo) {
						if (o_foo != null) {
							String s_foo = net.forestany.forestj.lib.Helper.toISO8601UTC( o_foo );
							
							/* get string bytes as utf-8 */
							byte[] by_foo = s_foo.getBytes(java.nio.charset.StandardCharsets.UTF_8);
							
							/* add data length */
							net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(by_foo.length, p_i_dataLengthInBytes), p_a_dataLengthAndData);
							
							/* add data */
							for (byte by_byte : by_foo) {
								p_a_dataLengthAndData.add(by_byte);
							}
						} else {
							/* add data length '0' */
							net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0, p_i_dataLengthInBytes), p_a_dataLengthAndData);
						}
					}
				}
			} else if (p_b_isGenericList) {
				p_sh_arrayAmount.set(Marshall.iterateGenericListOrMap(java.time.LocalDateTime.class, p_o_value, p_a_dataLengthAndData, p_i_dataLengthInBytes));
				
				if (p_sh_arrayAmount.get() < 0) {
					/* invalid type of generic list or invalid amount of elements, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogWarning("invalid type of generic list or invalid amount of elements for field '" + p_s_name + "' - [generic type = '" + p_o_genericType.getTypeName() + "'], so we must skip this field");
					return false;
				}
			}
		} else if ( (p_o_type == java.math.BigDecimal.class) || (p_o_genericType == java.math.BigDecimal.class) ) {
			p_by_type.set(14);
			
			if ( (!p_b_isArray) && (!p_b_isGenericList) ) {
				java.math.BigDecimal o_foo = (java.math.BigDecimal)p_o_value;
				
				if (o_foo == null) {
					o_foo = new java.math.BigDecimal(0d);
				}
				
				String s_foo = net.forestany.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimalWithSign(o_foo, 13, 20, null, null);
				
				/* get string bytes as utf-8 */
				byte[] by_foo = s_foo.getBytes(java.nio.charset.StandardCharsets.UTF_8);
				
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(by_foo.length, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				for (byte by_byte : by_foo) {
					p_a_dataLengthAndData.add(by_byte);
				}
			} else if (p_b_isArray) {
				java.math.BigDecimal[] a_foo = (java.math.BigDecimal[])p_o_value;
				
				if (a_foo != null) {
					if (a_foo.length > 65535) {
						/* array elements for this field exceeds max. supported amount of elements, so we must skip this field */
																net.forestany.forestj.lib.Global.ilogWarning("array elements for field '" + p_s_name + "' exceeds max. supported amount of elements(65535), so we must skip this field");
						return false;
					}
					
					p_sh_arrayAmount.set(a_foo.length);
					
					for (java.math.BigDecimal o_foo : a_foo) {
						if (o_foo == null) {
							o_foo = new java.math.BigDecimal(0d);
						}
						
						String s_foo = net.forestany.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimalWithSign(o_foo, 13, 20, null, null);
						
						/* get string bytes as utf-8 */
						byte[] by_foo = s_foo.getBytes(java.nio.charset.StandardCharsets.UTF_8);
						
						/* add data length */
						net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(by_foo.length, p_i_dataLengthInBytes), p_a_dataLengthAndData);
						
						/* add data */
						for (byte by_byte : by_foo) {
							p_a_dataLengthAndData.add(by_byte);
						}
					}
				}
			} else if (p_b_isGenericList) {
				p_sh_arrayAmount.set(Marshall.iterateGenericListOrMap(java.math.BigDecimal.class, p_o_value, p_a_dataLengthAndData, p_i_dataLengthInBytes));
				
				if (p_sh_arrayAmount.get() < 0) {
					/* invalid type of generic list or invalid amount of elements, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogWarning("invalid type of generic list or invalid amount of elements for field '" + p_s_name + "' - [generic type = '" + p_o_genericType.getTypeName() + "'], so we must skip this field");
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Handle generic list after retrieving field value from an object
	 * 
	 * @param p_o_genericClassType		tells us the parameterized type of the generic list
	 * @param p_o_fieldValue			object value where we have our generic list, only Collection, Set and Map are supported
	 * @param p_a_dataLengthAndData		array of bytes where we want to marshall our generic list's content
	 * @param p_i_dataLengthInBytes		define how many bytes are used to marshall the length of data
	 * @return							amount of elements in generic list or -1 if class type is not supported, object value is not a Collection, Set or Map, amount of elements exceeds max. supported value (65535)
	 */
	private static <T> int iterateGenericListOrMap(Class<T> p_o_genericClassType, Object p_o_fieldValue, java.util.List<Byte> p_a_dataLengthAndData, int p_i_dataLengthInBytes) {
		int i_return = -1;
		
		/* check field value parameter */
		if (p_o_fieldValue == null) {
			/* no generic list available -> null */
			return 0;
		}
		
		if (p_o_fieldValue instanceof java.util.Set<?>) { /* we have set for iteration */
			/* we will always marshall Sets ordered */
			@SuppressWarnings("unchecked")
			java.util.Collection<T> a_foo = ((java.util.Set<T>)p_o_fieldValue).stream().sorted().collect(java.util.stream.Collectors.toList());
			
			if (a_foo.size() > 65535) {
				/* array elements for this field with generic list exceeds max. supported amount of elements, so we must skip this field */
														net.forestany.forestj.lib.Global.ilogWarning("array elements for field with generic list exceeds max. supported amount of elements(65535), so we must skip this field");
				return -1;
			}
			
			i_return = (short)a_foo.size();
			
			/* iterate each set object */
			for (T t_foo : a_foo) {
				if (!marshallElementOfGenericListOrMap(p_o_genericClassType, t_foo, p_a_dataLengthAndData, p_i_dataLengthInBytes)) {
					/* class type not supported for marshalling, so we will skip this field */
					return -1;
				}
			}
		} else if (p_o_fieldValue instanceof java.util.Collection<?>) { /* we have a collection for iteration */
			@SuppressWarnings("unchecked")
			java.util.Collection<T> a_foo = (java.util.Collection<T>)p_o_fieldValue;
			
			if (a_foo.size() > 65535) {
				/* array elements for this field with generic list exceeds max. supported amount of elements, so we must skip this field */
														net.forestany.forestj.lib.Global.ilogWarning("array elements for field with generic list exceeds max. supported amount of elements(65535), so we must skip this field");
				return -1;
			}
			
			i_return = (short)a_foo.size();
			
			/* iterate each collection object */
			for (T t_foo : a_foo) {
				if (!marshallElementOfGenericListOrMap(p_o_genericClassType, t_foo, p_a_dataLengthAndData, p_i_dataLengthInBytes)) {
					/* class type not supported for marshalling, so we will skip this field */
					return -1;
				}
			}
		} else if (p_o_fieldValue instanceof java.util.Map<?, ?>) { /* we have a map for iteration */
			@SuppressWarnings("unchecked")
			java.util.Map<Integer, T> a_foo = (java.util.Map<Integer, T>)p_o_fieldValue;
			
			if (a_foo.size() > 65535) {
				/* array elements for this field with generic list exceeds max. supported amount of elements, so we must skip this field */
														net.forestany.forestj.lib.Global.ilogWarning("array elements for field with generic list exceeds max. supported amount of elements(65535), so we must skip this field");
				return -1;
			}
			
			i_return = (short)a_foo.size();
			
			/* sort by integer key with tree map */
			a_foo = new java.util.TreeMap<Integer, T>(a_foo);
			
			/* iterate each map object */
			for (java.util.Map.Entry<Integer, T> t_fooEntry : a_foo.entrySet()) {
				if (!marshallElementOfGenericListOrMap(p_o_genericClassType, t_fooEntry.getValue(), p_a_dataLengthAndData, p_i_dataLengthInBytes)) {
					/* class type of generic list not supported for marshalling, so we must skip this field */
															net.forestany.forestj.lib.Global.ilogFiner("class type of generic list not supported for marshalling, so we must skip this field");

					return -1;
				}
			}
		} else {
			/* field with generic list is not of type Set, Collection or Map, so we must skip this field */
												net.forestany.forestj.lib.Global.ilogFiner("field with generic list is not of type Set, Collection or Map, so we must skip this field");
			
			return -1;
		}
		
		return i_return;
	}
	
	/**
	 * Handle element of generic list or map and marshall data content
	 * 
	 * @param p_o_genericClassType		tells us the parameterized type of the generic list
	 * @param p_o_foo					element of generic list or map
	 * @param p_a_dataLengthAndData		array of bytes where we want to marshall our generic list's content
	 * @param p_i_dataLengthInBytes		define how many bytes are used to marshall the length of data
	 * @return							true - element could be marshalled, false - class type of generic list or map not supported for marshalling
	 */
	private static boolean marshallElementOfGenericListOrMap(Class<?> p_o_genericClassType, Object p_o_foo, java.util.List<Byte> p_a_dataLengthAndData, int p_i_dataLengthInBytes) {
		if (p_o_genericClassType == Boolean.class) {
			if (p_o_foo == null) {
				/* add data length '0' */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
			} else {
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(1L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				if ((boolean)p_o_foo) {
					p_a_dataLengthAndData.add((byte)1);
				} else {
					p_a_dataLengthAndData.add((byte)0);
				}
			}
		} else if (p_o_genericClassType == Byte.class) {
			if (p_o_foo == null) {
				/* add data length '0' */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
			} else {
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(1L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				p_a_dataLengthAndData.add((byte)p_o_foo);
			}
		} else if (p_o_genericClassType == Character.class) {
			if (p_o_foo == null) {
				/* add data length '0' */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
			} else {
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(1L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				p_a_dataLengthAndData.add((byte)(char)p_o_foo);
			}
		} else if (p_o_genericClassType == Float.class) {
			if (p_o_foo == null) {
				/* add data length '0' */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
			} else {
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(4L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				int i_bits = Float.floatToIntBits((float)p_o_foo);
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.intToByteArray(i_bits), p_a_dataLengthAndData, 4);
			}
		} else if (p_o_genericClassType == Double.class) {
			if (p_o_foo == null) {
				/* add data length '0' */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
			} else {
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(8L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				long l_bits = Double.doubleToLongBits((double)p_o_foo);
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.longToByteArray(l_bits), p_a_dataLengthAndData, 8);
			}
		} else if (p_o_genericClassType == Short.class) {
			if (p_o_foo == null) {
				/* add data length '0' */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
			} else {
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(2L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				short sh_foo = (short)p_o_foo;
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.shortToByteArray(sh_foo), p_a_dataLengthAndData, 2);
			}
		} else if (p_o_genericClassType == Integer.class) {
			if (p_o_foo == null) {
				/* add data length '0' */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
			} else {
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(4L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				int i_foo = (int)p_o_foo;
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.intToByteArray(i_foo), p_a_dataLengthAndData, 4);
			}
		} else if (p_o_genericClassType == Long.class) {
			if (p_o_foo == null) {
				/* add data length '0' */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
			} else {
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(8L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				long l_foo = (long)p_o_foo;
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.longToByteArray(l_foo), p_a_dataLengthAndData, 8);
			}
		} else if (p_o_genericClassType == String.class) {
			String s_foo = (String)p_o_foo;
			
			if (s_foo != null) {
				/* get string bytes as utf-8 */
				byte[] by_foo = s_foo.getBytes(java.nio.charset.StandardCharsets.UTF_8);
				
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(by_foo.length, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				for (byte by_byte : by_foo) {
					p_a_dataLengthAndData.add(by_byte);
				}
			} else {
				/* add data length '0' */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
			}
		} else if (p_o_genericClassType == java.util.Date.class) {
			java.util.Date o_foo = (java.util.Date)p_o_foo;
			
			if (o_foo != null) {
				String s_foo = net.forestany.forestj.lib.Helper.utilDateToISO8601UTC(o_foo);
				
				/* get string bytes as utf-8 */
				byte[] by_foo = s_foo.getBytes(java.nio.charset.StandardCharsets.UTF_8);
				
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(by_foo.length, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				for (byte by_byte : by_foo) {
					p_a_dataLengthAndData.add(by_byte);
				}
			} else {
				/* add data length '0' */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
			}
		} else if (p_o_genericClassType == java.time.LocalTime.class) {
			java.time.LocalTime o_foo = (java.time.LocalTime)p_o_foo;
			
			if (o_foo != null) {
				String s_foo = net.forestany.forestj.lib.Helper.toISO8601UTC( java.time.LocalDateTime.of(java.time.LocalDate.of(9999, 1, 1), o_foo) );
				
				/* get string bytes as utf-8 */
				byte[] by_foo = s_foo.getBytes(java.nio.charset.StandardCharsets.UTF_8);
				
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(by_foo.length, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				for (byte by_byte : by_foo) {
					p_a_dataLengthAndData.add(by_byte);
				}
			} else {
				/* add data length '0' */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
			}
		} else if (p_o_genericClassType == java.time.LocalDate.class) {
			java.time.LocalDate o_foo = (java.time.LocalDate)p_o_foo;
			
			if (o_foo != null) {
				String s_foo = net.forestany.forestj.lib.Helper.toISO8601UTC( java.time.LocalDateTime.of(o_foo, java.time.LocalTime.of(0, 0, 0)) );
				
				/* get string bytes as utf-8 */
				byte[] by_foo = s_foo.getBytes(java.nio.charset.StandardCharsets.UTF_8);
				
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(by_foo.length, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				for (byte by_byte : by_foo) {
					p_a_dataLengthAndData.add(by_byte);
				}
			} else {
				/* add data length '0' */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
			}
		} else if (p_o_genericClassType == java.time.LocalDateTime.class) {
			java.time.LocalDateTime o_foo = (java.time.LocalDateTime)p_o_foo;
			
			if (o_foo != null) {
				String s_foo = net.forestany.forestj.lib.Helper.toISO8601UTC( o_foo );
				
				/* get string bytes as utf-8 */
				byte[] by_foo = s_foo.getBytes(java.nio.charset.StandardCharsets.UTF_8);
				
				/* add data length */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(by_foo.length, p_i_dataLengthInBytes), p_a_dataLengthAndData);
				
				/* add data */
				for (byte by_byte : by_foo) {
					p_a_dataLengthAndData.add(by_byte);
				}
			} else {
				/* add data length '0' */
				net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(0L, p_i_dataLengthInBytes), p_a_dataLengthAndData);
			}
		} else if (p_o_genericClassType == java.math.BigDecimal.class) {
			java.math.BigDecimal o_foo = (java.math.BigDecimal)p_o_foo;
			
			if (o_foo == null) {
				o_foo = new java.math.BigDecimal(0d);
			}
			
			String s_foo = StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimalWithSign(o_foo, 13, 20, null, null);
			
			/* get string bytes as utf-8 */
			byte[] by_foo = s_foo.getBytes(java.nio.charset.StandardCharsets.UTF_8);
			
			/* add data length */
			net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.Helper.amountToNByteArray(by_foo.length, p_i_dataLengthInBytes), p_a_dataLengthAndData);
			
			/* add data */
			for (byte by_byte : by_foo) {
				p_a_dataLengthAndData.add(by_byte);
			}
		} else {
			/* class type of generic list not supported for marshalling, so we must skip this field */
													net.forestany.forestj.lib.Global.ilogFiner("class type of generic list not supported for marshalling, so we must skip this field");

			return false;
		}
		
		return true;
	}
	
	/**
	 * Unmarshall object with all fields of primitive types or supported types. Handling data in array parameter as big endian. Do not use property methods to retrieve field values.
	 * 
	 * @param p_o_class											class information for casting returning object
	 * @param p_a_data											array of data as bytes which will be unmarshalled
	 * @return													new instance of object with p_o_class information parameter
	 * @throws NullPointerException								class information or data parameter are not set
	 * @throws InstantiationException							class that declares the underlying constructor represents an abstract class
	 * @throws IllegalAccessException							constructor objectis enforcing Java language access control and the underlying constructor is inaccessible
	 * @throws IllegalArgumentException							illegal or missing arguments for constructor
	 * @throws java.lang.reflect.InvocationTargetException		the underlying constructor throws an exception
	 * @throws NoSuchMethodException							constructor could not be found or could not retrieve method by method name
	 * @throws SecurityException								security vulnerabilites while calling the constructor
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws java.text.ParseException							cannot convert string iso-8601 utc to java.util.Date, details in exception message
	 */
	public static Object unmarshallObject(Class<?> p_o_class, byte[] p_a_data) throws NullPointerException, InstantiationException, IllegalAccessException, IllegalArgumentException, java.lang.reflect.InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, java.text.ParseException {
		return Marshall.unmarshallObject(p_o_class, p_a_data, false);
	}
	
	/**
	 * Unmarshall object with all fields of primitive types or supported types. Handling data in array parameter as big endian.
	 * 
	 * @param p_o_class											class information for casting returning object
	 * @param p_a_data											array of data as bytes which will be unmarshalled
	 * @param p_b_usePropertyMethods							access object parameter fields via property methods e.g. T getXYZ()
	 * @return													new instance of object with p_o_class information parameter
	 * @throws NullPointerException								class information or data parameter are not set
	 * @throws InstantiationException							class that declares the underlying constructor represents an abstract class
	 * @throws IllegalAccessException							constructor objectis enforcing Java language access control and the underlying constructor is inaccessible
	 * @throws IllegalArgumentException							illegal or missing arguments for constructor
	 * @throws java.lang.reflect.InvocationTargetException		the underlying constructor throws an exception
	 * @throws NoSuchMethodException							constructor could not be found or could not retrieve method by method name
	 * @throws SecurityException								security vulnerabilites while calling the constructor
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws java.text.ParseException							cannot convert string iso-8601 utc to java.util.Date, details in exception message
	 */
	public static Object unmarshallObject(Class<?> p_o_class, byte[] p_a_data, boolean p_b_usePropertyMethods) throws NullPointerException, InstantiationException, IllegalAccessException, IllegalArgumentException, java.lang.reflect.InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, java.text.ParseException {
		return Marshall.unmarshallObject(p_o_class, p_a_data, p_b_usePropertyMethods, false);
	}
	
	/**
	 * Unmarshall object with all fields of primitive types or supported types. Handling data in array parameter as big endian.
	 * 
	 * @param p_o_class											class information for casting returning object
	 * @param p_a_data											array of data as bytes which will be unmarshalled
	 * @param p_b_usePropertyMethods							access object parameter fields via property methods e.g. T getXYZ()
	 * @param p_b_systemUsesLittleEndian						(NOT IMPLEMENTED) true - current execution system uses little endian, false - current execution system uses big endian
	 * @return													new instance of object with p_o_class information parameter
	 * @throws NullPointerException								class information or data parameter are not set
	 * @throws InstantiationException							class that declares the underlying constructor represents an abstract class
	 * @throws IllegalAccessException							constructor objectis enforcing Java language access control and the underlying constructor is inaccessible
	 * @throws IllegalArgumentException							illegal or missing arguments for constructor
	 * @throws java.lang.reflect.InvocationTargetException		the underlying constructor throws an exception
	 * @throws NoSuchMethodException							constructor could not be found or could not retrieve method by method name
	 * @throws SecurityException								security vulnerabilites while calling the constructor
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws java.text.ParseException							cannot convert string iso-8601 utc to java.util.Date, details in exception message
	 */
	public static Object unmarshallObject(Class<?> p_o_class, byte[] p_a_data, boolean p_b_usePropertyMethods, boolean p_b_systemUsesLittleEndian) throws NullPointerException, InstantiationException, IllegalAccessException, IllegalArgumentException, java.lang.reflect.InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, java.text.ParseException {
		/* return object */
		Object o_return = null;
		
		/* little endian system data is not supported right now */
		if (p_b_systemUsesLittleEndian) {
			throw new UnsupportedOperationException("Supporting little endian system data is NOT IMPLEMENTED");
		}
		
		/* check if we have a generic class parameter */
		if (p_o_class == null) {
			throw new NullPointerException("generic class parameter is null");
		}
		
		/* check if we have any data to unmarshall */
		if (p_a_data == null) {
			throw new NullPointerException("data parameter is null");
		}
		
		/* if we have byte array of 5 elements and all are zero, then we can return null */
		if ( (p_a_data.length == 5) && (p_a_data[0] == 0) && (p_a_data[1] == 0) && (p_a_data[2] == 0) && (p_a_data[3] == 0) && (p_a_data[4] == 0) ) {
			return null;
		}
			
		/* check first two bytes for amount of fields - if they are both zero, we have not an object with fields but just a primitive supported variable */
		if ( (p_a_data.length > 1) && (p_a_data[0] == 0) && (p_a_data[1] == 0) ) {
			/* if we expect net.forestany.forestj.lib.net.sock.com.NullValue class as primitive, we return null */
			//TODO uncomment
			//if (p_o_class == net.forestany.forestj.lib.net.sock.com.NullValue.class) {
			//	return null;
			//}
			
			/* handle primitive supported type */
			if (Marshall.a_allowedTypes.contains(p_o_class)) {
				try {
					int i_bytePointer = 2;
					
					/* read 1 byte name length */
					int i_nameLength = Short.toUnsignedInt( net.forestany.forestj.lib.Helper.byteArrayToShort( new byte[] { p_a_data[i_bytePointer++] } ) );
					
					String s_fieldName = "";
					
					/* read name bytes if length greater than zero */
					if (i_nameLength > 0) {
						/* read bytes for name */
						byte[] a_nameBytes = new byte[i_nameLength];
						
						for (int j = 0; j < i_nameLength; j++) {
							a_nameBytes[j] = p_a_data[i_bytePointer++];
						}
						
						/* store field name */
						s_fieldName = new String(a_nameBytes);
					}
					
					/* read 2 bytes info */
					byte[] a_info = new byte[2];
					
					for (int j = 0; j < 2; j++) {
						a_info[j] = p_a_data[i_bytePointer++];
					}
					
					short sh_info = net.forestany.forestj.lib.Helper.byteArrayToShort( a_info );
					
					/* get little endian bit */
					boolean b_littleEndianFlag = ( (sh_info & 0x8000) == 0x8000 );
					
					/* get array flag bit */
					boolean b_arrayFlag = ( (sh_info & 0x4000) == 0x4000 );
					
					/* get data length amount (2 bits) */
					int i_dataLengthAmount = 0;
					
					if ( ( (sh_info & 0x2000) == 0 ) && ( (sh_info & 0x1000) == 0 ) ) {
						i_dataLengthAmount = 1;
					} else if ( ( (sh_info & 0x2000) == 0 ) && ( (sh_info & 0x1000) == 0x1000 ) ) {
						i_dataLengthAmount = 2;
					} else if ( ( (sh_info & 0x2000) == 0x2000 ) && ( (sh_info & 0x1000) == 0 ) ) {	
						i_dataLengthAmount = 3;
					} else if ( ( (sh_info & 0x2000) == 0x2000 ) && ( (sh_info & 0x1000) == 0x1000 ) ) {
						i_dataLengthAmount = 4;
					}
					
					/* get type (12 bits) */
					int i_type = (sh_info & 0x0FFF);
					
					/* read 2 bytes array amount */
					byte[] a_arrayAmount = new byte[2];
					
					for (int j = 0; j < 2; j++) {
						a_arrayAmount[j] = p_a_data[i_bytePointer++];
					}
					
					int i_arrayAmount = net.forestany.forestj.lib.Helper.byteArrayToShort( a_arrayAmount );
	
					String s_empty = "                              ";
					
					net.forestany.forestj.lib.Global.ilogFiner(
						s_fieldName + 
						s_empty.substring(0, s_empty.length() - s_fieldName.length()) + 
						b_littleEndianFlag + 
						s_empty.substring(0, s_empty.length() - ((b_littleEndianFlag) ? 4 : 5)) + 
						b_arrayFlag + 
						s_empty.substring(0, s_empty.length() - ((b_arrayFlag) ? 4 : 5)) + 
						i_dataLengthAmount + 
						s_empty.substring(0, s_empty.length() - String.valueOf(i_dataLengthAmount).length()) + 
						i_type + 
						s_empty.substring(0, s_empty.length() - String.valueOf(i_type).length()) +
						i_arrayAmount + 
						s_empty.substring(0, s_empty.length() - String.valueOf(i_arrayAmount).length())
					);				
					
					java.util.List<byte[]> a_dataList = new java.util.ArrayList<byte[]>();
					
					/* array amount > 0 ? */
					if (i_arrayAmount > 0) {
						/* for k -> array amount */
						for (int k = 0; k < i_arrayAmount; k++) {
							/* read data length (1-4 bytes, depending on data length amount) */
							byte[] a_dataLength = new byte[i_dataLengthAmount];
	
							for (int j = 0; j < i_dataLengthAmount; j++) {
								a_dataLength[j] = p_a_data[i_bytePointer++];
							}
							
							int i_dataLength = net.forestany.forestj.lib.Helper.byteArrayToInt( a_dataLength );
							
							/* read data with data length */
							byte[] a_data = new byte[i_dataLength];
							
							for (int j = 0; j < i_dataLength; j++) {
								a_data[j] = p_a_data[i_bytePointer++];
							}
							
							a_dataList.add(a_data);
						}
					} else if (!b_arrayFlag) { /* only continue read if we are sure that it is not an array and we have array amount < 1 */
						/* read data length (1-4 bytes, depending on data length amount) */
						byte[] a_dataLength = new byte[i_dataLengthAmount];
						
						for (int j = 0; j < i_dataLengthAmount; j++) {
							a_dataLength[j] = p_a_data[i_bytePointer++];
						}
						
						int i_dataLength = net.forestany.forestj.lib.Helper.byteArrayToInt( a_dataLength );
						
						/* read data with data length */
						byte[] a_data = new byte[i_dataLength];
						
						for (int j = 0; j < i_dataLength; j++) {
							a_data[j] = p_a_data[i_bytePointer++];
						}
						
						a_dataList.add(a_data);
					}
					
					/* prepare data for return object */
					o_return = Marshall.unmarshallDataWithFieldOrObjectInformation(i_type, p_o_class, o_return, s_fieldName, null, p_b_usePropertyMethods, b_arrayFlag, false, a_dataList);
				} catch (IndexOutOfBoundsException o_exc) {
					net.forestany.forestj.lib.Global.ilogWarning("data accessed with invalid index: " + o_exc);
					throw new IndexOutOfBoundsException("data accessed with invalid index: " + o_exc);
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.ilogSevere("error while set return variable: " + o_exc);
					throw o_exc;
				}
			} else {
				/* type is not supported */
				throw new IllegalArgumentException("Object parameter is not of a supported type '" + p_o_class.getTypeName() + "'");
			}
		} else { /* expect object with fields */
			/* create new instance of return object and handle any exception which can occur */
			try {
				o_return = p_o_class.getDeclaredConstructor().newInstance();
			} catch (InstantiationException o_exc) {
				throw new InstantiationException("could not create new instance of return object; class that declares the underlying constructor represents an abstract class: " + o_exc);
			} catch (IllegalAccessException o_exc) {
				throw new IllegalAccessException("could not create new instance of return object; constructor objectis enforcing Java language access control and the underlying constructor is inaccessible: " + o_exc);
			} catch (IllegalArgumentException o_exc) {
				throw new IllegalArgumentException("could not create new instance of return object; illegal or missing arguments for constructor: " + o_exc);
			} catch (java.lang.reflect.InvocationTargetException o_exc) {
				throw new java.lang.reflect.InvocationTargetException(o_exc, "could not create new instance of return object; the underlying constructor throws an exception: " + o_exc);
			} catch (NoSuchMethodException o_exc) {
				throw new NoSuchMethodException("could not create new instance of return object; constructor could not be found: " + o_exc);
			} catch (SecurityException o_exc) {
				throw new SecurityException("could not create new instance of return object; security vulnerabilites while calling the constructor: " + o_exc);
			}
			
			try {
				/* read amount of fields */
				int i_amountFields = Short.toUnsignedInt( net.forestany.forestj.lib.Helper.byteArrayToShort( new byte[] { p_a_data[0], p_a_data[1] } ) );
				int i_bytePointer = 2;
				
				for (int i = 0; i < i_amountFields; i++) {
					/* read 1 byte name length */
					int i_nameLength = Short.toUnsignedInt( net.forestany.forestj.lib.Helper.byteArrayToShort( new byte[] { p_a_data[i_bytePointer++] } ) );
					
					/* read bytes for name */
					byte[] a_nameBytes = new byte[i_nameLength];
					
					for (int j = 0; j < i_nameLength; j++) {
						a_nameBytes[j] = p_a_data[i_bytePointer++];
					}
					
					/* store field name */
					String s_fieldName = new String(a_nameBytes);
					
					/* read 2 bytes info */
					byte[] a_info = new byte[2];
					
					for (int j = 0; j < 2; j++) {
						a_info[j] = p_a_data[i_bytePointer++];
					}
					
					short sh_info = net.forestany.forestj.lib.Helper.byteArrayToShort( a_info );
					
					/* get little endian bit */
					boolean b_littleEndianFlag = ( (sh_info & 0x8000) == 0x8000 );
					
					/* get array flag bit */
					boolean b_arrayFlag = ( (sh_info & 0x4000) == 0x4000 );
					
					/* get data length amount (2 bits) */
					int i_dataLengthAmount = 0;
					
					if ( ( (sh_info & 0x2000) == 0 ) && ( (sh_info & 0x1000) == 0 ) ) {
						i_dataLengthAmount = 1;
					} else if ( ( (sh_info & 0x2000) == 0 ) && ( (sh_info & 0x1000) == 0x1000 ) ) {
						i_dataLengthAmount = 2;
					} else if ( ( (sh_info & 0x2000) == 0x2000 ) && ( (sh_info & 0x1000) == 0 ) ) {	
						i_dataLengthAmount = 3;
					} else if ( ( (sh_info & 0x2000) == 0x2000 ) && ( (sh_info & 0x1000) == 0x1000 ) ) {
						i_dataLengthAmount = 4;
					}
					
					/* get type (12 bits) */
					int i_type = (sh_info & 0x0FFF);
					
					/* read 2 bytes array amount */
					byte[] a_arrayAmount = new byte[2];
					
					for (int j = 0; j < 2; j++) {
						a_arrayAmount[j] = p_a_data[i_bytePointer++];
					}
					
					int i_arrayAmount = net.forestany.forestj.lib.Helper.byteArrayToShort( a_arrayAmount );
	
					String s_empty = "                              ";
					
					net.forestany.forestj.lib.Global.ilogFiner(
						s_fieldName + 
						s_empty.substring(0, s_empty.length() - s_fieldName.length()) + 
						b_littleEndianFlag + 
						s_empty.substring(0, s_empty.length() - ((b_littleEndianFlag) ? 4 : 5)) + 
						b_arrayFlag + 
						s_empty.substring(0, s_empty.length() - ((b_arrayFlag) ? 4 : 5)) + 
						i_dataLengthAmount + 
						s_empty.substring(0, s_empty.length() - String.valueOf(i_dataLengthAmount).length()) + 
						i_type + 
						s_empty.substring(0, s_empty.length() - String.valueOf(i_type).length()) +
						i_arrayAmount + 
						s_empty.substring(0, s_empty.length() - String.valueOf(i_arrayAmount).length())
					);				
					
					java.util.List<byte[]> a_dataList = new java.util.ArrayList<byte[]>();
					
					/* array amount > 0 ? */
					if (i_arrayAmount > 0) {
						/* for k -> array amount */
						for (int k = 0; k < i_arrayAmount; k++) {
							/* read data length (1-4 bytes, depending on data length amount) */
							byte[] a_dataLength = new byte[i_dataLengthAmount];
	
							for (int j = 0; j < i_dataLengthAmount; j++) {
								a_dataLength[j] = p_a_data[i_bytePointer++];
							}
							
							int i_dataLength = net.forestany.forestj.lib.Helper.byteArrayToInt( a_dataLength );
							
							/* read data with data length */
							byte[] a_data = new byte[i_dataLength];
							
							for (int j = 0; j < i_dataLength; j++) {
								a_data[j] = p_a_data[i_bytePointer++];
							}
							
							a_dataList.add(a_data);
						}
					} else if (!b_arrayFlag) { /* only continue read if we are sure that it is not an array and we have array amount < 1 */
						/* read data length (1-4 bytes, depending on data length amount) */
						byte[] a_dataLength = new byte[i_dataLengthAmount];
						
						for (int j = 0; j < i_dataLengthAmount; j++) {
							a_dataLength[j] = p_a_data[i_bytePointer++];
						}
						
						int i_dataLength = net.forestany.forestj.lib.Helper.byteArrayToInt( a_dataLength );
						
						/* read data with data length */
						byte[] a_data = new byte[i_dataLength];
						
						for (int j = 0; j < i_dataLength; j++) {
							a_data[j] = p_a_data[i_bytePointer++];
						}
						
						a_dataList.add(a_data);
					}
					
					/* get field reflection */
					java.lang.reflect.Field o_field = o_return.getClass().getDeclaredField(s_fieldName);
					
					/* holding information about generic type */
					Class<?> o_genericType = null;
					
					/* check if current field is a dynamic generic list */
					if (
						(o_field.getType() == java.util.List.class) || 
						(o_field.getType() == java.util.Map.class) || 
						(o_field.getType() == java.util.Set.class)
					) {
						/* generic map must have just two parameterized type declaration */
						if (
							(o_field.getType() == java.util.Map.class) &&
							( ((java.lang.reflect.ParameterizedType) o_field.getGenericType()).getActualTypeArguments().length == 2 )
						) {
							/* parameterized type declaration of key part of map must be 'java.lang.Integer' */
							if (((java.lang.reflect.ParameterizedType) o_field.getGenericType()).getActualTypeArguments()[0] != Integer.class) {
								throw new IllegalArgumentException("parameterized type declaration of key part of map is not 'java.lang.Integer' of field '" + s_fieldName + "'");
							}
	
							o_genericType = (Class<?>)((java.lang.reflect.ParameterizedType) o_field.getGenericType()).getActualTypeArguments()[1];
						} else if ( ((java.lang.reflect.ParameterizedType) o_field.getGenericType()).getActualTypeArguments().length == 1 ) {
							/* generic list or set must have just one parameterized type declaration */
							o_genericType = (Class<?>)((java.lang.reflect.ParameterizedType) o_field.getGenericType()).getActualTypeArguments()[0];
						} else { /* parameterized type declaration which are not equal '1' is not supported, so we must skip this field */
							throw new IllegalArgumentException("parameterized type declaration which are not equal '1' is not supported; field: '" + s_fieldName + "'");
						}
					}
	
					/* pass data to object fields */
					@SuppressWarnings("unused")
					Object o_unused = Marshall.unmarshallDataWithFieldOrObjectInformation(i_type, o_genericType, o_return, s_fieldName, o_field, p_b_usePropertyMethods, b_arrayFlag, o_genericType != null, a_dataList);
				}
			} catch (IndexOutOfBoundsException o_exc) {
														net.forestany.forestj.lib.Global.ilogWarning("data accessed with invalid index: " + o_exc);
				
				throw new IndexOutOfBoundsException("data accessed with invalid index: " + o_exc);
			} catch (Exception o_exc) {
														net.forestany.forestj.lib.Global.ilogSevere("error while set a field property: " + o_exc);
				throw o_exc;
			}
		}
		
		return o_return;
	}
	
	/**
	 * Unmarshalling data by field or object information
	 * 
	 * @param p_i_type											read marshalled type of data
	 * @param p_o_dateTimeType									details to datetime type (java.util.Date, java.time.LocalTime, java.time.LocalDate, java.time.LocalDateTime)
	 * @param p_o_return										object parameter where data will be added into object fields
	 * @param p_s_fieldName										field name to cast value to object's field
	 * @param p_o_field											all information about the generic list field
	 * @param p_b_usePropertyMethods							access object parameter fields via property methods e.g. T getXYZ() 
	 * @param p_b_arrayFlag										marshalled data is of type array
	 * @param p_b_isGenericList									marshalled data is of type generic list
	 * @param p_a_dataList										marshalled data as list of multiple byte arrays for primitive objects, arrays and generic lists
	 * @return													primitive object which will be returned by unmarshallObject, otherwise just null which can be ignored
	 * @throws NoSuchMethodException							could not find a field by field name to set it's value
	 * @throws NoSuchFieldException								could not find a method by method name, using field name
	 * @throws java.lang.reflect.InvocationTargetException		could not invoke method from object to set field's value
	 * @throws IllegalAccessException							could not invoke method, access violation
	 * @throws java.text.ParseException							cannot convert string to datetime type, details in exception message
	 */
	private static Object unmarshallDataWithFieldOrObjectInformation(int p_i_type, Class<?> p_o_dateTimeType, Object p_o_return, String p_s_fieldName, java.lang.reflect.Field p_o_field, boolean p_b_usePropertyMethods, boolean p_b_arrayFlag, boolean p_b_isGenericList, java.util.List<byte[]> p_a_dataList) throws NoSuchMethodException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, IllegalAccessException, java.text.ParseException {
		Object o_foo = null;
		
		if (p_i_type == 0) { /* data for type Boolean */
			if (!p_b_arrayFlag) { /* just a field */
				/* set field value */
				if (p_a_dataList.get(0)[0] == 0x1) {
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)true, p_b_usePropertyMethods);
				} else {
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)false, p_b_usePropertyMethods);
				}
			} else if ( (!p_b_isGenericList) && (p_b_arrayFlag) ) { /* usual array */
				if (p_a_dataList.size() < 1) { /* set null if we have no data for usual array */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)null, p_b_usePropertyMethods);
				} else {
					/* prepare usual array */
					boolean[] a_temp = new boolean[p_a_dataList.size()];
					
					/* counter */
					int i_cnt = 0;
					
					for (byte[] a_byteChunks : p_a_dataList) {
						/* set array value */
						if (a_byteChunks[0] == 0x1) {
							a_temp[i_cnt++] = true;
						} else {
							a_temp[i_cnt++] = false;
						}
					}
					
					/* set field value */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)a_temp, p_b_usePropertyMethods);
				}
			} else if ( (p_b_isGenericList) && (p_b_arrayFlag) ) { /* dynamic generic list */
				Marshall.setGenericListField(Boolean.class, p_o_field, p_o_return, p_a_dataList, p_b_usePropertyMethods);
			}
		} else if ( (p_i_type == 1) || (p_i_type == 2) ) { /* data for type Byte or SByte */
			if (!p_b_arrayFlag) { /* just a field */
				/* set field value */
				o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)p_a_dataList.get(0)[0], p_b_usePropertyMethods);
			} else if ( (!p_b_isGenericList) && (p_b_arrayFlag) ) { /* usual array */
				if (p_a_dataList.size() < 1) { /* set null if we have no data for usual array */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)null, p_b_usePropertyMethods);
				} else {
					/* prepare usual array */
					byte[] a_temp = new byte[p_a_dataList.size()];
					
					/* counter */
					int i_cnt = 0;
					
					for (byte[] a_byteChunks : p_a_dataList) {
						/* set array value */
						a_temp[i_cnt++] = a_byteChunks[0];
					}
					
					/* set field value */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)a_temp, p_b_usePropertyMethods);
				}
			} else if ( (p_b_isGenericList) && (p_b_arrayFlag) ) { /* dynamic generic list */
				Marshall.setGenericListField(Byte.class, p_o_field, p_o_return, p_a_dataList, p_b_usePropertyMethods);
			}
		} else if (p_i_type == 3) { /* data for type Character */
			if (!p_b_arrayFlag) { /* just a field */
				/* set field value */
				char c_temp = (char)(0xFF & p_a_dataList.get(0)[0]);
				o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)c_temp, p_b_usePropertyMethods);
			} else if ( (!p_b_isGenericList) && (p_b_arrayFlag) ) { /* usual array */
				if (p_a_dataList.size() < 1) { /* set null if we have no data for usual array */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)null, p_b_usePropertyMethods);
				} else {
					/* prepare usual array */
					char[] a_temp = new char[p_a_dataList.size()];
					
					/* counter */
					int i_cnt = 0;
					
					for (byte[] a_byteChunks : p_a_dataList) {
						/* set array value */
						a_temp[i_cnt++] = (char)(0xFF & a_byteChunks[0]);
					}
					
					/* set field value */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)a_temp, p_b_usePropertyMethods);
				}
			} else if ( (p_b_isGenericList) && (p_b_arrayFlag) ) { /* dynamic generic list */
				Marshall.setGenericListField(Character.class, p_o_field, p_o_return, p_a_dataList, p_b_usePropertyMethods);
			}
		} else if (p_i_type == 4) { /* data for type Float */
			if (!p_b_arrayFlag) { /* just a field */
				/* set field value */
				o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)Float.intBitsToFloat( net.forestany.forestj.lib.Helper.byteArrayToInt(p_a_dataList.get(0)) ), p_b_usePropertyMethods);
			} else if ( (!p_b_isGenericList) && (p_b_arrayFlag) ) { /* usual array */
				if (p_a_dataList.size() < 1) { /* set null if we have no data for usual array */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)null, p_b_usePropertyMethods);
				} else {
					/* prepare usual array */
					float[] a_temp = new float[p_a_dataList.size()];
					
					/* counter */
					int i_cnt = 0;
					
					for (byte[] a_byteChunks : p_a_dataList) {
						/* set array value */
						a_temp[i_cnt++] = Float.intBitsToFloat( net.forestany.forestj.lib.Helper.byteArrayToInt(a_byteChunks) );
					}
					
					/* set field value */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)a_temp, p_b_usePropertyMethods);
				}
			} else if ( (p_b_isGenericList) && (p_b_arrayFlag) ) { /* dynamic generic list */
				Marshall.setGenericListField(Float.class, p_o_field, p_o_return, p_a_dataList, p_b_usePropertyMethods);
			}
		} else if (p_i_type == 5) { /* data for type Double */
			if (!p_b_arrayFlag) { /* just a field */
				/* set field value */
				o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)Double.longBitsToDouble( net.forestany.forestj.lib.Helper.byteArrayToLong(p_a_dataList.get(0)) ), p_b_usePropertyMethods);
			} else if ( (!p_b_isGenericList) && (p_b_arrayFlag) ) { /* usual array */
				if (p_a_dataList.size() < 1) { /* set null if we have no data for usual array */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)null, p_b_usePropertyMethods);
				} else {
					/* prepare usual array */
					double[] a_temp = new double[p_a_dataList.size()];
					
					/* counter */
					int i_cnt = 0;
					
					for (byte[] a_byteChunks : p_a_dataList) {
						/* set array value */
						a_temp[i_cnt++] = Double.longBitsToDouble( net.forestany.forestj.lib.Helper.byteArrayToLong(a_byteChunks) );
					}
					
					/* set field value */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)a_temp, p_b_usePropertyMethods);
				}
			} else if ( (p_b_isGenericList) && (p_b_arrayFlag) ) { /* dynamic generic list */
				Marshall.setGenericListField(Double.class, p_o_field, p_o_return, p_a_dataList, p_b_usePropertyMethods);
			}
		} else if ( (p_i_type == 6) || (p_i_type == 7) ) { /* data for type Short or Unsigned Short */
			if (!p_b_arrayFlag) { /* just a field */
				/* set field value */
				o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)net.forestany.forestj.lib.Helper.byteArrayToShort(p_a_dataList.get(0)), p_b_usePropertyMethods);
			} else if ( (!p_b_isGenericList) && (p_b_arrayFlag) ) { /* usual array */
				if (p_a_dataList.size() < 1) { /* set null if we have no data for usual array */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)null, p_b_usePropertyMethods);
				} else {
					/* prepare usual array */
					short[] a_temp = new short[p_a_dataList.size()];
					
					/* counter */
					int i_cnt = 0;
					
					for (byte[] a_byteChunks : p_a_dataList) {
						/* set array value */
						a_temp[i_cnt++] = net.forestany.forestj.lib.Helper.byteArrayToShort(a_byteChunks);
					}
					
					/* set field value */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)a_temp, p_b_usePropertyMethods);
				}
			} else if ( (p_b_isGenericList) && (p_b_arrayFlag) ) { /* dynamic generic list */
				Marshall.setGenericListField(Short.class, p_o_field, p_o_return, p_a_dataList, p_b_usePropertyMethods);
			}
		} else if ( (p_i_type == 8) || (p_i_type == 9) ) { /* data for type Integer or Unsigned Integer */
			if (!p_b_arrayFlag) { /* just a field */
				/* set field value */
				o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)net.forestany.forestj.lib.Helper.byteArrayToInt(p_a_dataList.get(0)), p_b_usePropertyMethods);
			} else if ( (!p_b_isGenericList) && (p_b_arrayFlag) ) { /* usual array */
				if (p_a_dataList.size() < 1) { /* set null if we have no data for usual array */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)null, p_b_usePropertyMethods);
				} else {
					/* prepare usual array */
					int[] a_temp = new int[p_a_dataList.size()];
					
					/* counter */
					int i_cnt = 0;
					
					for (byte[] a_byteChunks : p_a_dataList) {
						/* set array value */
						a_temp[i_cnt++] = net.forestany.forestj.lib.Helper.byteArrayToInt(a_byteChunks);
					}
					
					/* set field value */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)a_temp, p_b_usePropertyMethods);
				}
			} else if ( (p_b_isGenericList) && (p_b_arrayFlag) ) { /* dynamic generic list */
				Marshall.setGenericListField(Integer.class, p_o_field, p_o_return, p_a_dataList, p_b_usePropertyMethods);
			}
		} else if ( (p_i_type == 10) || (p_i_type == 11) ) { /* data for type Long or Unsigned Long */
			if (!p_b_arrayFlag) { /* just a field */
				/* set field value */
				o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)net.forestany.forestj.lib.Helper.byteArrayToLong(p_a_dataList.get(0)), p_b_usePropertyMethods);
			} else if ( (!p_b_isGenericList) && (p_b_arrayFlag) ) { /* usual array */
				if (p_a_dataList.size() < 1) { /* set null if we have no data for usual array */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)null, p_b_usePropertyMethods);
				} else {
					/* prepare usual array */
					long[] a_temp = new long[p_a_dataList.size()];
					
					/* counter */
					int i_cnt = 0;
					
					for (byte[] a_byteChunks : p_a_dataList) {
						/* set array value */
						a_temp[i_cnt++] = net.forestany.forestj.lib.Helper.byteArrayToLong(a_byteChunks);
					}
					
					/* set field value */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)a_temp, p_b_usePropertyMethods);
				}
			} else if ( (p_b_isGenericList) && (p_b_arrayFlag) ) { /* dynamic generic list */
				Marshall.setGenericListField(Long.class, p_o_field, p_o_return, p_a_dataList, p_b_usePropertyMethods);
			}
		} else if (p_i_type == 12) { /* data for type String */
			if (!p_b_arrayFlag) { /* just a field */
				/* set field value */
				o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)new String(p_a_dataList.get(0)), p_b_usePropertyMethods);
			} else if ( (!p_b_isGenericList) && (p_b_arrayFlag) ) { /* usual array */
				if (p_a_dataList.size() < 1) { /* set null if we have no data for usual array */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)null, p_b_usePropertyMethods);
				} else {
					/* prepare usual array */
					String[] a_temp = new String[p_a_dataList.size()];
					
					/* counter */
					int i_cnt = 0;
					
					for (byte[] a_byteChunks : p_a_dataList) {
						if (a_byteChunks.length < 1) {
							/* data has no length, so we add null */
							a_temp[i_cnt++] = null;
						} else {
							/* set array value */
							a_temp[i_cnt++] = new String(a_byteChunks);
						}
					}
					
					/* set field value */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)a_temp, p_b_usePropertyMethods);
				}
			} else if ( (p_b_isGenericList) && (p_b_arrayFlag) ) { /* dynamic generic list */
				Marshall.setGenericListField(String.class, p_o_field, p_o_return, p_a_dataList, p_b_usePropertyMethods);
			}
		} else if (p_i_type == 13) { /* data for type DateTime */
			if (!p_b_arrayFlag) { /* just a field */
				if (p_a_dataList.get(0).length == 0) { /* no data for field, set it to null */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)null, p_b_usePropertyMethods);
				} else { /* retrieve field data */
					if (
						( (p_o_dateTimeType != null) && (p_o_dateTimeType == java.util.Date.class) ) || 
						( (p_o_field != null) && (p_o_field.getType() == java.util.Date.class) )
					) {
						/* set field value */
						o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)net.forestany.forestj.lib.Helper.fromISO8601UTCToUtilDate( new String(p_a_dataList.get(0)) ), p_b_usePropertyMethods);
					} else if (
						( (p_o_dateTimeType != null) && (p_o_dateTimeType == java.time.LocalTime.class) ) || 
						( (p_o_field != null) && (p_o_field.getType() == java.time.LocalTime.class) )
					) {
						/* set field value */
						o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)net.forestany.forestj.lib.Helper.fromISO8601UTC( new String(p_a_dataList.get(0)) ).toLocalTime(), p_b_usePropertyMethods);
					} else if (
						( (p_o_dateTimeType != null) && (p_o_dateTimeType == java.time.LocalDate.class) ) || 
						( (p_o_field != null) && (p_o_field.getType() == java.time.LocalDate.class) )
					) {
						/* set field value */
						o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)net.forestany.forestj.lib.Helper.fromISO8601UTC( new String(p_a_dataList.get(0)) ).toLocalDate(), p_b_usePropertyMethods);
					} else if (
						( (p_o_dateTimeType != null) && (p_o_dateTimeType == java.time.LocalDateTime.class) ) || 
						( (p_o_field != null) && (p_o_field.getType() == java.time.LocalDateTime.class) )
					) {
						/* set field value */
						o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)net.forestany.forestj.lib.Helper.fromISO8601UTC( new String(p_a_dataList.get(0)) ), p_b_usePropertyMethods);
					}
				}
			} else if ( (!p_b_isGenericList) && (p_b_arrayFlag) ) { /* usual array */
				if (p_a_dataList.size() < 1) { /* set null if we have no data for usual array */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)null, p_b_usePropertyMethods);
				} else { /* retrieve array data */
					if (
						( (p_o_dateTimeType != null) && (p_o_dateTimeType == java.util.Date.class) ) || 
						( (p_o_field != null) && (p_o_field.getType().isArray()) && (p_o_field.getType().getComponentType() == java.util.Date.class) )
					) {
						/* prepare usual array */
						java.util.Date[] a_temp = new java.util.Date[p_a_dataList.size()];
						
						/* counter */
						int i_cnt = 0;
						
						for (byte[] a_byteChunks : p_a_dataList) {
							if (a_byteChunks.length < 1) {
								/* data has no length, so we add null */
								a_temp[i_cnt++] = null;
							} else {
								/* set array value */
								a_temp[i_cnt++] = net.forestany.forestj.lib.Helper.fromISO8601UTCToUtilDate( new String(a_byteChunks) );
							}
						}
						
						/* set field value */
						o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)a_temp, p_b_usePropertyMethods);
					} else if (
						( (p_o_dateTimeType != null) && (p_o_dateTimeType == java.time.LocalTime.class) ) || 
						( (p_o_field != null) && (p_o_field.getType().isArray()) && (p_o_field.getType().getComponentType() == java.time.LocalTime.class) )
					) {
						/* prepare usual array */
						java.time.LocalTime[] a_temp = new java.time.LocalTime[p_a_dataList.size()];
						
						/* counter */
						int i_cnt = 0;
						
						for (byte[] a_byteChunks : p_a_dataList) {
							if (a_byteChunks.length < 1) {
								/* data has no length, so we add null */
								a_temp[i_cnt++] = null;
							} else {
								/* set array value */
								a_temp[i_cnt++] = net.forestany.forestj.lib.Helper.fromISO8601UTC( new String(a_byteChunks) ).toLocalTime();
							}
						}
						
						/* set field value */
						o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)a_temp, p_b_usePropertyMethods);
					} else if (
						( (p_o_dateTimeType != null) && (p_o_dateTimeType == java.time.LocalDate.class) ) || 
						( (p_o_field != null) && (p_o_field.getType().isArray()) && (p_o_field.getType().getComponentType() == java.time.LocalDate.class) )
					) {
						/* prepare usual array */
						java.time.LocalDate[] a_temp = new java.time.LocalDate[p_a_dataList.size()];
						
						/* counter */
						int i_cnt = 0;
						
						for (byte[] a_byteChunks : p_a_dataList) {
							if (a_byteChunks.length < 1) {
								/* data has no length, so we add null */
								a_temp[i_cnt++] = null;
							} else {
								/* set array value */
								a_temp[i_cnt++] = net.forestany.forestj.lib.Helper.fromISO8601UTC( new String(a_byteChunks) ).toLocalDate();
							}
						}
						
						/* set field value */
						o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)a_temp, p_b_usePropertyMethods);
					} else if (
						( (p_o_dateTimeType != null) && (p_o_dateTimeType == java.time.LocalDateTime.class) ) || 
						( (p_o_field != null) && (p_o_field.getType().isArray()) && (p_o_field.getType().getComponentType() == java.time.LocalDateTime.class) )
					) {
						/* prepare usual array */
						java.time.LocalDateTime[] a_temp = new java.time.LocalDateTime[p_a_dataList.size()];
						
						/* counter */
						int i_cnt = 0;
						
						for (byte[] a_byteChunks : p_a_dataList) {
							if (a_byteChunks.length < 1) {
								/* data has no length, so we add null */
								a_temp[i_cnt++] = null;
							} else {
								/* set array value */
								a_temp[i_cnt++] = net.forestany.forestj.lib.Helper.fromISO8601UTC( new String(a_byteChunks) );
							}
						}
						
						/* set field value */
						o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)a_temp, p_b_usePropertyMethods);
					}
				}
			} else if ( (p_b_isGenericList) && (p_b_arrayFlag) ) { /* dynamic generic list */
				/* retrieve list data */
				if ( (p_o_dateTimeType != null) && (p_o_dateTimeType == java.util.Date.class) ) {
					Marshall.setGenericListField(java.util.Date.class, p_o_field, p_o_return, p_a_dataList, p_b_usePropertyMethods);
				} else if ( (p_o_dateTimeType != null) && (p_o_dateTimeType == java.time.LocalTime.class) ) {
					Marshall.setGenericListField(java.time.LocalTime.class, p_o_field, p_o_return, p_a_dataList, p_b_usePropertyMethods);
				} else if ( (p_o_dateTimeType != null) && (p_o_dateTimeType == java.time.LocalDate.class) ) {
					Marshall.setGenericListField(java.time.LocalDate.class, p_o_field, p_o_return, p_a_dataList, p_b_usePropertyMethods);
				} else if ( (p_o_dateTimeType != null) && (p_o_dateTimeType == java.time.LocalDateTime.class) ) {
					Marshall.setGenericListField(java.time.LocalDateTime.class, p_o_field, p_o_return, p_a_dataList, p_b_usePropertyMethods);
				}
			}
		} else if (p_i_type == 14) { /* data for type BigDecimal */
			if (!p_b_arrayFlag) { /* just a field */
				if (p_a_dataList.get(0).length < 1) { /* set null if we have no data for usual array */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)new java.math.BigDecimal(0d), p_b_usePropertyMethods);
				} else {
					/* set field value */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, net.forestany.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal(new String(p_a_dataList.get(0)), 14), p_b_usePropertyMethods);
				}
			} else if ( (!p_b_isGenericList) && (p_b_arrayFlag) ) { /* usual array */
				if (p_a_dataList.size() < 1) { /* set null if we have no data for usual array */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)null, p_b_usePropertyMethods);
				} else {
					/* prepare usual array */
					java.math.BigDecimal[] a_temp = new java.math.BigDecimal[p_a_dataList.size()];
					
					/* counter */
					int i_cnt = 0;
					
					for (byte[] a_byteChunks : p_a_dataList) {
						if (a_byteChunks.length < 1) {
							/* data has no length, so we add null */
							a_temp[i_cnt++] = new java.math.BigDecimal(0d);
						} else {
							/* set array value */
							a_temp[i_cnt++] = (java.math.BigDecimal)net.forestany.forestj.lib.io.StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal(new String(a_byteChunks), 14);
						}
					}
					
					/* set field value */
					o_foo = Marshall.setFieldValue(p_s_fieldName, p_o_return, (Object)a_temp, p_b_usePropertyMethods);
				}
			} else if ( (p_b_isGenericList) && (p_b_arrayFlag) ) { /* dynamic generic list */
				Marshall.setGenericListField(java.math.BigDecimal.class, p_o_field, p_o_return, p_a_dataList, p_b_usePropertyMethods);
			}
		} else {
			throw new UnsupportedOperationException("Type with number '" + p_i_type + "' is not supported");
		}
		
		return o_foo;
	}
	
	/**
	 * Method to set property field of an object with simple object value, so no cast will be done, parameter object must have the get correct type before 
	 * 
	 * @param p_s_fieldName					field name to cast value to object's field
	 * @param p_o_object					object parameter where data will be added into object fields
	 * @param p_o_objectValue				object value for property field
	 * @param p_b_usePropertyMethods		access object parameter fields via property methods e.g. T getXYZ() 
	 * @throws NoSuchFieldException			could not find a field by field name to set it's value
	 * @throws NoSuchMethodException		could not find a method by method name, using field name
	 * @throws InvocationTargetException 	could not invoke method from object to set field's value
	 * @throws IllegalAccessException		could not invoke method, access violation
	 */
	private static Object setFieldValue(String p_s_fieldName, Object p_o_object, Object p_o_objectValue, boolean p_b_usePropertyMethods) throws NoSuchMethodException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		/* directly return object value */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_fieldName)) {
			return p_o_objectValue;
		} else if (p_b_usePropertyMethods) { /* check if we use property methods with invoke to set object data values */
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
			o_method.invoke(p_o_object, p_o_objectValue);
		} else {
			/* call field directly to set object data values */
			try {
				p_o_object.getClass().getDeclaredField(p_s_fieldName).set(p_o_object, p_o_objectValue);
			} catch (Exception o_exc) {
				throw new NoSuchFieldException("Property field[" + p_s_fieldName + "] is not accessible for object: " + p_o_object.getClass().getTypeName());
			}
		}
		
		return null;
	}

	/**
	 * Setting generic list or map field after received data, each element stored within a single byte array
	 * 
	 * @param p_o_genericClassType									tells us the parameterized type of the generic list or map
	 * @param p_o_field												all information about the generic list field
	 * @param p_o_returnObject										object instance where we want to set the generic list field
	 * @param p_a_dataList											array of bytes where we want to unmarshall our generic list's content
	 * @param p_b_usePropertyMethods								access object parameter fields via property methods e.g. T getXYZ()
	 * @throws NoSuchMethodException								could not find a field by field name to set it's value
	 * @throws NoSuchFieldException									could not find a method by method name, using field name
	 * @throws java.lang.reflect.InvocationTargetException			could not invoke method from object to set field's value
	 * @throws IllegalAccessException								could not invoke method, access violation
	 */
	private static <T> void setGenericListField(Class<T> p_o_genericClassType, java.lang.reflect.Field p_o_field, Object p_o_returnObject, java.util.List<byte[]> p_a_dataList, boolean p_b_usePropertyMethods) throws NoSuchMethodException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		Object o_foo = null;
		
		if (p_o_genericClassType == Boolean.class) {
			if (p_o_field.getType() == java.util.List.class) {
				/* prepare list array */
				java.util.List<Boolean> a_temp = new java.util.ArrayList<Boolean>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else if (p_a_dataList.get(i)[0] == 0x1) {
						a_temp.add(true);
					} else {
						a_temp.add(false);
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Set.class) {
				/* prepare set array */
				java.util.Set<Boolean> a_temp = new java.util.HashSet<Boolean>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set set value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else if (p_a_dataList.get(i)[0] == 0x1) {
						a_temp.add(true);
					} else {
						a_temp.add(false);
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Map.class) {
				/* prepare map array */
				java.util.Map<Integer, Boolean> a_temp = new java.util.HashMap<Integer, Boolean>();
				
				/* counter */
				int i_cnt = 0;
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set map value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.put(i_cnt++, null);
					} else if (p_a_dataList.get(i)[0] == 0x1) {
						a_temp.put(i_cnt++, true);
					} else {
						a_temp.put(i_cnt++, false);
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			}
			
			/* set field value */
			Marshall.setFieldValue(p_o_field.getName(), p_o_returnObject, o_foo, p_b_usePropertyMethods);
		} else if (p_o_genericClassType == Byte.class) {
			if (p_o_field.getType() == java.util.List.class) {
				/* prepare list array */
				java.util.List<Byte> a_temp = new java.util.ArrayList<Byte>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add(p_a_dataList.get(i)[0]);
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Set.class) {
				/* prepare set array */
				java.util.Set<Byte> a_temp = new java.util.HashSet<Byte>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add(p_a_dataList.get(i)[0]);
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Map.class) {
				/* prepare map array */
				java.util.Map<Integer, Byte> a_temp = new java.util.HashMap<Integer, Byte>();
				
				/* counter */
				int i_cnt = 0;
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set map value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.put(i_cnt++, null);
					} else {
						a_temp.put(i_cnt++, p_a_dataList.get(i)[0]);
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			}
			
			/* set field value */
			Marshall.setFieldValue(p_o_field.getName(), p_o_returnObject, o_foo, p_b_usePropertyMethods);
		} else if (p_o_genericClassType == Character.class) {
			if (p_o_field.getType() == java.util.List.class) {
				/* prepare list array */
				java.util.List<Character> a_temp = new java.util.ArrayList<Character>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add((char)(0xFF & p_a_dataList.get(i)[0]));
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Set.class) {
				/* prepare set array */
				java.util.Set<Character> a_temp = new java.util.HashSet<Character>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add((char)(0xFF & p_a_dataList.get(i)[0]));
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Map.class) {
				/* prepare map array */
				java.util.Map<Integer, Character> a_temp = new java.util.HashMap<Integer, Character>();
				
				/* counter */
				int i_cnt = 0;
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set map value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.put(i_cnt++, null);
					} else {
						a_temp.put(i_cnt++, (char)(0xFF & p_a_dataList.get(i)[0]));
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			}
			
			/* set field value */
			Marshall.setFieldValue(p_o_field.getName(), p_o_returnObject, o_foo, p_b_usePropertyMethods);
		} else if (p_o_genericClassType == Float.class) {
			if (p_o_field.getType() == java.util.List.class) {
				/* prepare list array */
				java.util.List<Float> a_temp = new java.util.ArrayList<Float>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( Float.intBitsToFloat( net.forestany.forestj.lib.Helper.byteArrayToInt( p_a_dataList.get(i) ) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Set.class) {
				/* prepare set array */
				java.util.Set<Float> a_temp = new java.util.HashSet<Float>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( Float.intBitsToFloat( net.forestany.forestj.lib.Helper.byteArrayToInt( p_a_dataList.get(i) ) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Map.class) {
				/* prepare map array */
				java.util.Map<Integer, Float> a_temp = new java.util.HashMap<Integer, Float>();
				
				/* counter */
				int i_cnt = 0;
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set map value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.put(i_cnt++, null);
					} else {
						a_temp.put(i_cnt++, Float.intBitsToFloat( net.forestany.forestj.lib.Helper.byteArrayToInt( p_a_dataList.get(i) ) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			}
			
			/* set field value */
			Marshall.setFieldValue(p_o_field.getName(), p_o_returnObject, o_foo, p_b_usePropertyMethods);
		} else if (p_o_genericClassType == Double.class) {
			if (p_o_field.getType() == java.util.List.class) {
				/* prepare list array */
				java.util.List<Double> a_temp = new java.util.ArrayList<Double>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( Double.longBitsToDouble( net.forestany.forestj.lib.Helper.byteArrayToLong( p_a_dataList.get(i) ) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Set.class) {
				/* prepare set array */
				java.util.Set<Double> a_temp = new java.util.HashSet<Double>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( Double.longBitsToDouble( net.forestany.forestj.lib.Helper.byteArrayToLong( p_a_dataList.get(i) ) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Map.class) {
				/* prepare map array */
				java.util.Map<Integer, Double> a_temp = new java.util.HashMap<Integer, Double>();
				
				/* counter */
				int i_cnt = 0;
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set map value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.put(i_cnt++, null);
					} else {
						a_temp.put(i_cnt++, Double.longBitsToDouble( net.forestany.forestj.lib.Helper.byteArrayToLong( p_a_dataList.get(i) ) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			}
			
			/* set field value */
			Marshall.setFieldValue(p_o_field.getName(), p_o_returnObject, o_foo, p_b_usePropertyMethods);
		} else if (p_o_genericClassType == Short.class) {
			if (p_o_field.getType() == java.util.List.class) {
				/* prepare list array */
				java.util.List<Short> a_temp = new java.util.ArrayList<Short>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( net.forestany.forestj.lib.Helper.byteArrayToShort( p_a_dataList.get(i) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Set.class) {
				/* prepare set array */
				java.util.Set<Short> a_temp = new java.util.HashSet<Short>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( net.forestany.forestj.lib.Helper.byteArrayToShort( p_a_dataList.get(i) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Map.class) {
				/* prepare map array */
				java.util.Map<Integer, Short> a_temp = new java.util.HashMap<Integer, Short>();
				
				/* counter */
				int i_cnt = 0;
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set map value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.put(i_cnt++, null);
					} else {
						a_temp.put(i_cnt++, net.forestany.forestj.lib.Helper.byteArrayToShort( p_a_dataList.get(i) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			}
			
			/* set field value */
			Marshall.setFieldValue(p_o_field.getName(), p_o_returnObject, o_foo, p_b_usePropertyMethods);
		} else if (p_o_genericClassType == Integer.class) {
			if (p_o_field.getType() == java.util.List.class) {
				/* prepare list array */
				java.util.List<Integer> a_temp = new java.util.ArrayList<Integer>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( net.forestany.forestj.lib.Helper.byteArrayToInt( p_a_dataList.get(i) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Set.class) {
				/* prepare set array */
				java.util.Set<Integer> a_temp = new java.util.HashSet<Integer>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( net.forestany.forestj.lib.Helper.byteArrayToInt( p_a_dataList.get(i) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Map.class) {
				/* prepare map array */
				java.util.Map<Integer, Integer> a_temp = new java.util.HashMap<Integer, Integer>();
				
				/* counter */
				int i_cnt = 0;
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set map value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.put(i_cnt++, null);
					} else {
						a_temp.put(i_cnt++, net.forestany.forestj.lib.Helper.byteArrayToInt( p_a_dataList.get(i) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			}
			
			/* set field value */
			Marshall.setFieldValue(p_o_field.getName(), p_o_returnObject, o_foo, p_b_usePropertyMethods);
		} else if (p_o_genericClassType == Long.class) {
			if (p_o_field.getType() == java.util.List.class) {
				/* prepare list array */
				java.util.List<Long> a_temp = new java.util.ArrayList<Long>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( net.forestany.forestj.lib.Helper.byteArrayToLong( p_a_dataList.get(i) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Set.class) {
				/* prepare set array */
				java.util.Set<Long> a_temp = new java.util.HashSet<Long>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( net.forestany.forestj.lib.Helper.byteArrayToLong( p_a_dataList.get(i) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Map.class) {
				/* prepare map array */
				java.util.Map<Integer, Long> a_temp = new java.util.HashMap<Integer, Long>();
				
				/* counter */
				int i_cnt = 0;
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set map value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.put(i_cnt++, null);
					} else {
						a_temp.put(i_cnt++, net.forestany.forestj.lib.Helper.byteArrayToLong( p_a_dataList.get(i) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			}
			
			/* set field value */
			Marshall.setFieldValue(p_o_field.getName(), p_o_returnObject, o_foo, p_b_usePropertyMethods);
		} else if (p_o_genericClassType == String.class) {
			if (p_o_field.getType() == java.util.List.class) {
				/* prepare list array */
				java.util.List<String> a_temp = new java.util.ArrayList<String>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( new String( p_a_dataList.get(i) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Set.class) {
				/* prepare set array */
				java.util.Set<String> a_temp = new java.util.HashSet<String>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( new String( p_a_dataList.get(i) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Map.class) {
				/* prepare map array */
				java.util.Map<Integer, String> a_temp = new java.util.HashMap<Integer, String>();
				
				/* counter */
				int i_cnt = 0;
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set map value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.put(i_cnt++, null);
					} else {
						a_temp.put(i_cnt++, new String( p_a_dataList.get(i) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			}
			
			/* set field value */
			Marshall.setFieldValue(p_o_field.getName(), p_o_returnObject, o_foo, p_b_usePropertyMethods);
		} else if (p_o_genericClassType == java.util.Date.class) {
			if (p_o_field.getType() == java.util.List.class) {
				/* prepare list array */
				java.util.List<java.util.Date> a_temp = new java.util.ArrayList<java.util.Date>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						try {
							a_temp.add( net.forestany.forestj.lib.Helper.fromISO8601UTCToUtilDate( new String( p_a_dataList.get(i) ) ) );
						} catch (java.text.ParseException o_exc) {
							/* add null if we have a parse exception */
							a_temp.add(null);
						}
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Set.class) {
				/* prepare set array */
				java.util.Set<java.util.Date> a_temp = new java.util.HashSet<java.util.Date>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						try {
							a_temp.add( net.forestany.forestj.lib.Helper.fromISO8601UTCToUtilDate( new String( p_a_dataList.get(i) ) ) );
						} catch (java.text.ParseException o_exc) {
							/* add null if we have a parse exception */
							a_temp.add(null);
						}
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Map.class) {
				/* prepare map array */
				java.util.Map<Integer, java.util.Date> a_temp = new java.util.HashMap<Integer, java.util.Date>();
				
				/* counter */
				int i_cnt = 0;
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set map value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.put(i_cnt++, null);
					} else {
						try {
							a_temp.put(i_cnt++, net.forestany.forestj.lib.Helper.fromISO8601UTCToUtilDate( new String( p_a_dataList.get(i) ) ) );
						} catch (java.text.ParseException o_exc) {
							/* add null if we have a parse exception */
							a_temp.put(i_cnt++, null);
						}
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			}
			
			/* set field value */
			Marshall.setFieldValue(p_o_field.getName(), p_o_returnObject, o_foo, p_b_usePropertyMethods);
		} else if (p_o_genericClassType == java.time.LocalTime.class) {
			if (p_o_field.getType() == java.util.List.class) {
				/* prepare list array */
				java.util.List<java.time.LocalTime> a_temp = new java.util.ArrayList<java.time.LocalTime>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( net.forestany.forestj.lib.Helper.fromISO8601UTC( new String( p_a_dataList.get(i) ) ).toLocalTime() );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Set.class) {
				/* prepare set array */
				java.util.Set<java.time.LocalTime> a_temp = new java.util.HashSet<java.time.LocalTime>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( net.forestany.forestj.lib.Helper.fromISO8601UTC( new String( p_a_dataList.get(i) ) ).toLocalTime() );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Map.class) {
				/* prepare map array */
				java.util.Map<Integer, java.time.LocalTime> a_temp = new java.util.HashMap<Integer, java.time.LocalTime>();
				
				/* counter */
				int i_cnt = 0;
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set map value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.put(i_cnt++, null);
					} else {
						a_temp.put( i_cnt++, net.forestany.forestj.lib.Helper.fromISO8601UTC( new String( p_a_dataList.get(i) ) ).toLocalTime() );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			}
			
			/* set field value */
			Marshall.setFieldValue(p_o_field.getName(), p_o_returnObject, o_foo, p_b_usePropertyMethods);
		} else if (p_o_genericClassType == java.time.LocalDate.class) {
			if (p_o_field.getType() == java.util.List.class) {
				/* prepare list array */
				java.util.List<java.time.LocalDate> a_temp = new java.util.ArrayList<java.time.LocalDate>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( net.forestany.forestj.lib.Helper.fromISO8601UTC( new String( p_a_dataList.get(i) ) ).toLocalDate() );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Set.class) {
				/* prepare set array */
				java.util.Set<java.time.LocalDate> a_temp = new java.util.HashSet<java.time.LocalDate>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( net.forestany.forestj.lib.Helper.fromISO8601UTC( new String( p_a_dataList.get(i) ) ).toLocalDate() );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Map.class) {
				/* prepare map array */
				java.util.Map<Integer, java.time.LocalDate> a_temp = new java.util.HashMap<Integer, java.time.LocalDate>();
				
				/* counter */
				int i_cnt = 0;
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set map value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.put(i_cnt++, null);
					} else {
						a_temp.put( i_cnt++, net.forestany.forestj.lib.Helper.fromISO8601UTC( new String( p_a_dataList.get(i) ) ).toLocalDate() );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			}
			
			/* set field value */
			Marshall.setFieldValue(p_o_field.getName(), p_o_returnObject, o_foo, p_b_usePropertyMethods);
		} else if (p_o_genericClassType == java.time.LocalDateTime.class) {
			if (p_o_field.getType() == java.util.List.class) {
				/* prepare list array */
				java.util.List<java.time.LocalDateTime> a_temp = new java.util.ArrayList<java.time.LocalDateTime>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( net.forestany.forestj.lib.Helper.fromISO8601UTC( new String( p_a_dataList.get(i) ) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Set.class) {
				/* prepare set array */
				java.util.Set<java.time.LocalDateTime> a_temp = new java.util.HashSet<java.time.LocalDateTime>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( net.forestany.forestj.lib.Helper.fromISO8601UTC( new String( p_a_dataList.get(i) ) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Map.class) {
				/* prepare map array */
				java.util.Map<Integer, java.time.LocalDateTime> a_temp = new java.util.HashMap<Integer, java.time.LocalDateTime>();
				
				/* counter */
				int i_cnt = 0;
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set map value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.put(i_cnt++, null);
					} else {
						a_temp.put( i_cnt++, net.forestany.forestj.lib.Helper.fromISO8601UTC( new String( p_a_dataList.get(i) ) ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			}
			
			/* set field value */
			Marshall.setFieldValue(p_o_field.getName(), p_o_returnObject, o_foo, p_b_usePropertyMethods);
		} else if (p_o_genericClassType == java.math.BigDecimal.class) {
			if (p_o_field.getType() == java.util.List.class) {
				/* prepare list array */
				java.util.List<java.math.BigDecimal> a_temp = new java.util.ArrayList<java.math.BigDecimal>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( (java.math.BigDecimal)StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal( new String( p_a_dataList.get(i) ), 14 ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Set.class) {
				/* prepare set array */
				java.util.Set<java.math.BigDecimal> a_temp = new java.util.HashSet<java.math.BigDecimal>();
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set list value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.add(null);
					} else {
						a_temp.add( (java.math.BigDecimal)StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal( new String( p_a_dataList.get(i) ), 14 ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			} else if (p_o_field.getType() == java.util.Map.class) {
				/* prepare map array */
				java.util.Map<Integer, java.math.BigDecimal> a_temp = new java.util.HashMap<Integer, java.math.BigDecimal>();
				
				/* counter */
				int i_cnt = 0;
				
				for (int i = 0; i < p_a_dataList.size(); i++) {
					/* set map value */
					if (p_a_dataList.get(i).length < 1) {
						/* we have an array element with no data, so we add a null */
						a_temp.put(i_cnt++, null);
					} else {
						a_temp.put( i_cnt++, (java.math.BigDecimal)StandardTransposeMethods.FloatingPointNumbers.TransposeBigDecimal( new String( p_a_dataList.get(i) ), 14 ) );
					}
				}
				
				/* cast to general object */
				o_foo = (Object)a_temp;
			}
			
			/* set field value */
			Marshall.setFieldValue(p_o_field.getName(), p_o_returnObject, o_foo, p_b_usePropertyMethods);
		} else {
			/* class type of generic list not supported for unmarshalling, so we will not set this field */
													net.forestany.forestj.lib.Global.ilogWarning("class type of generic list not supported for unmarshalling, so we will not set this field '" + p_o_field.getName() + "'");
		}
	}
}
