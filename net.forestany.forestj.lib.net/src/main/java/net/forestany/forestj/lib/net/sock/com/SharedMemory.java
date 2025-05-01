package net.forestany.forestj.lib.net.sock.com;

/**
 * Abstract class to extend and watch any class in use to get to know which fields of a class are changed in time.
 *
 * @param <T>	Class definition of inherited class, e.g. Test extends net.forestany.forestj.lib.net.sock.com.SharedMemory&lt;Test.class&gt;
 */
public abstract class SharedMemory<T> {
	
	/* Constants */
	
	private final java.util.List<String> a_reservedFields = java.util.Arrays.asList("a_reservedFields", "o_lock", "a_fields", "o_mirror", "o_mirrorClass", "o_mirrorBidirectional", "o_mirrorClassBidirectional");
	private final java.util.concurrent.locks.ReentrantReadWriteLock o_lock = new java.util.concurrent.locks.ReentrantReadWriteLock();

	/* Fields */
	
	/**
	 * fields
	 */
	private java.util.List<String> a_fields = new java.util.ArrayList<String>();
	/**
	 * mirror instance
	 */
	private T o_mirror = null;
	/**
	 * mirror class
	 */
	protected Class<T> o_mirrorClass = null;
	/**
	 * mirror bidirectional instance
	 */
	private T o_mirrorBidirectional = null;
	
	/* Properties */
	
	/**
	 * get amount fields
	 * 
	 * @return int
	 */
	public int getAmountFields() {
		return this.a_fields.size();
	}
	
	/* Methods */
	
	/**
	 * Superior constructor of a class inheriting abstract SharedMemory class
	 * 
	 * @throws NullPointerException				mirror class not set by Init method
	 */
	public SharedMemory() throws NullPointerException {
		this.init();
		
		/* check if mirror class has been set in Init method */
		if (this.o_mirrorClass == null) {
			throw new NullPointerException("You must specify an image class within the Init-method.");
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("mirror class set for SharedMemory: '" + this.o_mirrorClass.getTypeName() + "'");
		
		/* iterate each class field */
		for (int i = 0; i < this.getClass().getDeclaredFields().length; i++) {
			java.lang.reflect.Field o_field =  this.getClass().getDeclaredFields()[i];
			
			/* check if a field in inheriting class is not colliding with a reserved field name of SharedMemory class */
			if (a_reservedFields.contains(o_field.getName())) {
				throw new IllegalStateException("Cannot use field[" + o_field.getName() + "]. Reserved field names " + this.a_reservedFields.toString() + " are not accessible");
			}
			
			/* because of security reasons, each field which will be watched must be private only */
			if (!java.lang.reflect.Modifier.isPrivate(o_field.getModifiers())) {
				throw new IllegalStateException("Field[" + o_field.getName() + "] must be private only");
			}
			
			/* add field to list to get to know when it's value is changed in time */
			this.a_fields.add(o_field.getName());
			
													net.forestany.forestj.lib.Global.ilogConfig("added field '" + o_field.getName() + "' to watch list");
		}
	}
	
	/**
	 * Abstract Init function so any class inheriting from SharedMemory&lt;T&gt; must have this method.
	 * declaring mirror class individually for every inheritance for later use.
	 */
	abstract protected void init();
	
	/**
	 * Method to check if a field exists in inherited class
	 * 
	 * @param p_s_field			field name
	 * @return boolean			true - field exist, false - field does not exist
	 */
	protected boolean fieldExists(String p_s_field) {
		try {
			/* use other method index return value */
			return (this.returnFieldNumber(p_s_field) < 0) ? false : true;
		} catch (Exception o_exc) {
			return false;
		}
	}
	
	/**
	 * Method to retrieve field value of inherited class
	 * 
	 * @param <T2>							generic object type
	 * @param p_s_field						field name
	 * @return &lt;T2&gt;					unknown object type until method in use
	 * @throws NoSuchFieldException			field does not exist
	 * @throws IllegalAccessException		cannot access field, must be public
	 * @throws Exception					caught exception from cast
	 */
	public <T2> T2 getField(String p_s_field) throws NoSuchFieldException, IllegalAccessException, Exception {
		return getField(p_s_field, false);
	}
	
	/**
	 * Method to retrieve field value of inherited class
	 * 
	 * @param <T2>							generic object type
	 * @param p_s_field						field name
	 * @param p_b_convertNullForTransport	true - convert to NullValue class value to transport null values over the network, false - normal retrieve
	 * @return &lt;T2&gt;					unknown object type until method in use
	 * @throws NoSuchFieldException			field does not exist
	 * @throws IllegalAccessException		cannot access field, must be public
	 * @throws Exception					caught exception from cast
	 */
	@SuppressWarnings("unchecked")
	public <T2> T2 getField(String p_s_field, boolean p_b_convertNullForTransport) throws NoSuchFieldException, IllegalAccessException, Exception {
		/* set read lock, so only one thread at a time has access to a field */
		this.o_lock.readLock().lock();
		
												if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("get field value from '" + p_s_field + "'");
		
		T2 o_foo = null;
		Exception o_caughtException = null;
		
		try {
			/* check if field is not in the list of reserved field names of ShareMemory class */
			if (a_reservedFields.contains(p_s_field)) {
				throw new IllegalAccessException("Cannot get field[" + p_s_field + "]. Reserved field names " + this.a_reservedFields.toString() + " are not accessible");
			}
			
			/* check if field really exists */
			if (!this.fieldExists(p_s_field)) {
				throw new NoSuchFieldException("Field[" + p_s_field + "] does not exist");
			}
			
			/* get field object */
			java.lang.reflect.Field o_field =  this.getClass().getDeclaredField(p_s_field);
			/* the reflected object should suppress checks for Java language access control when it is used */
			o_field.setAccessible(true);
			/* get class type */
			Class<T2> o_class = (Class<T2>) o_field.getType();
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
			}
			
			/* cast object of field */
			o_foo = o_class.cast(o_field.get(this));
		} catch (Exception o_exc) {
			/* remind caught exception */
			o_caughtException = o_exc;
		} finally {
			/* unset read lock */
			this.o_lock.readLock().unlock();
		}
		
		/* throw reminded caught exception */
		if (o_caughtException != null) {
			throw o_caughtException;
		}
		
		/* if casted object value is null, convert to NullValue class value to transport null values over the network */
		if ( (o_foo == null) && (p_b_convertNullForTransport) ) {
			o_foo = (T2)new NullValue();
		}
		
		/* return casted object */
		return o_foo;
	}
	
	/**
	 * Method to set a field value of inherited class
	 * 
	 * @param p_s_field					field name, will be changed to 'Column' + p_s_field
	 * @param p_o_value					object value which will be set as field value
	 * @throws NoSuchFieldException		field does not exist
	 * @throws IllegalAccessException	cannot access field, must be public
	 * @throws Exception				caught exception from setting field value
	 */
	public void setField(String p_s_field, Object p_o_value) throws NoSuchFieldException, IllegalAccessException, Exception {
		/* set write lock, so only one thread at a time has access to a field */
		this.o_lock.writeLock().lock();
		
		Exception o_caughtException = null;
		
		try {
			/* check if field is not in the list of reserved field names of ShareMemory class */
			if (a_reservedFields.contains(p_s_field)) {
				throw new IllegalAccessException("Cannot set field[" + p_s_field + "]. Reserved field names " + this.a_reservedFields.toString() + " are not accessible");
			}
			
			/* check if field really exists */
			if (!this.fieldExists(p_s_field)) {
				throw new NoSuchFieldException("Field[" + p_s_field + "] does not exist");
			}
			
			/* get field object */
			java.lang.reflect.Field o_field =  this.getClass().getDeclaredField(p_s_field);
			
													if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("set field value for: '" + p_s_field + "'" + "\t\tfield type: " + o_field.getType().getTypeName() + "\t\tvalue type: " + ( (p_o_value == null) ? "null" : p_o_value.getClass().getTypeName() ) );
			
			/* if object value is of instance NullValue, then we just have our null */
			if (p_o_value instanceof NullValue) {
				p_o_value = null;
			}
			
			/* the reflected object should suppress checks for Java language access control when it is used */
			o_field.setAccessible(true);
			/* set field value, accessing 'this' class and field with it's name */
			o_field.set(this, p_o_value);
		} catch (Exception o_exc) {
			o_caughtException = o_exc;
		} finally {
			/* unset write lock */
			this.o_lock.writeLock().unlock();
		}
		
		/* throw reminded caught exception */
		if (o_caughtException != null) {
			throw o_caughtException;
		}
	}
	
	/**
	 * Easy method to return all fields with their values of inherited class
	 * @return String	a string line of all fields with their values "field_name = field_value|"
	 */
	public String returnFields() {
		String s_foo = "";
		
		/* iterate each field of inherited class */
		for (int i = 0; i < this.getClass().getDeclaredFields().length; i++) {
			/* get field */
			java.lang.reflect.Field o_field =  this.getClass().getDeclaredFields()[i];

			/* check if field is not in the list of reserved field names of ShareMemory class */
			if (!a_reservedFields.contains(o_field.getName())) {
				try {
					/* get field value */
					Object o_foo = this.getField(o_field.getName());
					/* set field string value to 'NULL', in case value is null */
					String s_object = "NULL";
					
					/* if field value is not null, use toString method */
					if (o_foo != null) {
						s_object = o_foo.toString();
					}
					
					/* add field name and its value to return string */
					s_foo += o_field.getName() + " = " + s_object + "|";
				} catch (Exception o_exc) {
					/* just continue if field name or field value cannot be retrieved */
					s_foo += o_field.getName() + " = COULD_NOT_RETRIEVE|";
				}
			}
		}
		
		return s_foo;
	}
	
	/**
	 * Return field number of class which inherited abstract SharedMemory class with string parameter
	 * 
	 * @param p_s_field						field name
	 * @return								-1 - field does not exist, 0..n - field number
	 * @throws IllegalAccessException		field is not accessible
	 */
	public int returnFieldNumber(String p_s_field) throws IllegalAccessException {
		int i_return = -1;
		
		/* check if field is not in the list of reserved field names of ShareMemory class */
		if (a_reservedFields.contains(p_s_field)) {
			throw new IllegalAccessException("Cannot get field[" + p_s_field + "]. Reserved field names " + this.a_reservedFields.toString() +" are not accessible");
		}
		
		/* iterate each field of inherited class */
		for (int i = 0; i < this.a_fields.size(); i++) {
			/* field name must match parameter value */
			if (this.a_fields.get(i).contentEquals(p_s_field)) {
				i_return = i + 1; /* +1, because return value is initialized with -1 */
				break;
			}
		}
		
		return i_return;
	}
	
	/**
	 * Return field name of class which inherited abstract SharedMemory class with index parameter
	 * 
	 * @param p_i_fieldNumber				field number as index
	 * @return								field name
	 * @throws IndexOutOfBoundsException	if the field number is out of range (index &lt; 0 || index &gt;= size())
	 */
	public String returnFieldName(int p_i_fieldNumber) throws IndexOutOfBoundsException {
		/* get field name by index field number */
		return this.a_fields.get(--p_i_fieldNumber);
	}
	
	/**
	 * Get a list of all fields of current class which have changed
	 * 
	 * @return java.util.List of String
	 * @throws Exception						any exception which can happen during creating a new instance
	 * @throws NoSuchFieldException				a field does not exist
	 * @throws IllegalAccessException			cannot access field, must be public
	 * @throws ClassCastException				if the object is not null and is not assignable to the type T
	 */
	public java.util.List<String> getChangedFields() throws Exception {
		return this.getChangedFields(false);
	}
	
	/**
	 * Get a list of all fields of current class which have changed
	 * 
	 * @param p_b_updateMirror					true - update mirror object field values, so on next check we get the new changed fields			
	 * @return java.util.List of String
	 * @throws Exception						any exception which can happen during creating a new instance
	 * @throws NoSuchFieldException				a field does not exist
	 * @throws IllegalAccessException			cannot access field, must be public
	 * @throws ClassCastException				if the object is not null and is not assignable to the type T
	 */
	public java.util.List<String> getChangedFields(boolean p_b_updateMirror) throws Exception {
		return this.getChangedFields(p_b_updateMirror, false);
	}
	
	/**
	 * Get a list of all fields of current class which have changed
	 * 
	 * @param p_b_updateMirror					true - update mirror object field values, so on next check we get the new changed fields			
	 * @param p_b_bidirectionalMirror			true - update bidirectional mirror object field values, so on next check we get the new changed fields
	 * @return java.util.List of String
	 * @throws Exception						any exception which can happen during creating a new instance
	 * @throws NoSuchFieldException				a field does not exist
	 * @throws IllegalAccessException			cannot access field, must be public
	 * @throws ClassCastException				if the object is not null and is not assignable to the type T
	 */
	public java.util.List<String> getChangedFields(boolean p_b_updateMirror, boolean p_b_bidirectionalMirror) throws Exception {
		if ( (p_b_bidirectionalMirror) && (this.o_mirrorBidirectional == null) ) {
			/* bidirectional mirror object is null, we must update bidirectional mirror to create a new instance of it */
			this.updateMirror(p_b_bidirectionalMirror);
		} else if (this.o_mirror == null) {
			/* mirror object is null, we must update mirror to create a new instance of it */
			this.updateMirror();
		}
	
		java.util.List<String> a_changedFields = new java.util.ArrayList<String>();
		
		/* iterate each field of inherited class */
		for (int i = 0; i < this.a_fields.size(); i++) {
			/* check if field is not in the list of reserved field names of ShareMemory class */
			if (!a_reservedFields.contains(this.a_fields.get(i))) {
				/* get field value object */
				Object o_foo = this.getField(this.a_fields.get(i), true);
				Object o_foo2 = null;
				
				if (p_b_bidirectionalMirror) {
					/* get field value object of bidirectional mirror object */
					o_foo2 = (this.getClass().cast(this.o_mirrorBidirectional)).getField(this.a_fields.get(i), true);
				} else {
					/* get field value object of mirror object */
					o_foo2 = (this.getClass().cast(this.o_mirror)).getField(this.a_fields.get(i), true);
				}
				
				/* if inherited field value object and (bidirectional) mirror field value object are not equal OR both are not of instance NullValue */
				if (!(o_foo.equals(o_foo2) || ( (o_foo instanceof NullValue) && (o_foo2 instanceof NullValue) ) )) {
					/* add field to changed fields list */
					a_changedFields.add(this.a_fields.get(i));
				}
			}
		}
		
		/* update mirror or bidirectional mirror object field values, so on next check we get the new changed fields */
		if (p_b_updateMirror) {
			this.updateMirror(p_b_bidirectionalMirror);
		}
		
		return a_changedFields;
	}
	
	/**
	 * Update mirror object fields with inherited class fields. If mirror field objects are null, new instances will be created
	 * 
	 * @throws Exception							any exception which can happen during creating a new instance
	 * @throws NoSuchFieldException					a field does not exist
	 * @throws IllegalAccessException				cannot access field, must be public
	 */
	public void updateMirror() throws Exception {
		this.updateMirror(false);
	}
	
	/**
	 * Update (bidirectional) mirror object fields with inherited class fields. If mirror field objects are null, new instances will be created
	 * 
	 * @param p_b_updateBidirectionalMirror			true - update all fields of bidirectional mirror object, false - update all fields of mirror object
	 * @throws Exception							any exception which can happen during creating a new instance
	 * @throws NoSuchFieldException					a field does not exist
	 * @throws IllegalAccessException				cannot access field, must be public
	 */
	public void updateMirror(boolean p_b_updateBidirectionalMirror) throws Exception {
		/* placeholder variable for mirror or bidirectional mirror object */
		T o_inheritedInstance = null;
		
		if (p_b_updateBidirectionalMirror) {
			/* set placeholder variable with bidirectional mirror object */
			o_inheritedInstance = this.o_mirrorBidirectional;
		} else {
			/* set placeholder variable with mirror object */
			o_inheritedInstance = this.o_mirror;
		}
		
		Exception o_caughtException = null;
		
		if (o_inheritedInstance == null) {
			/* set write lock, so only one thread at a time has access to a field */
			this.o_lock.writeLock().lock();
			
			try {
				/* create new instance of (bidirectional) mirror object */
				o_inheritedInstance = this.o_mirrorClass.getDeclaredConstructor().newInstance();
			} catch (Exception o_exc) {
				o_caughtException = o_exc;
			} finally {
				/* unset write lock */
				this.o_lock.writeLock().unlock();
			}
			
			/* throw reminded caught exception */
			if (o_caughtException != null) {
				throw o_caughtException;
			}
		}

		/* iterate each field of inherited class */
		for (int i = 0; i < this.a_fields.size(); i++) {
			/* check if field is not in the list of reserved field names of ShareMemory class */
			if (!a_reservedFields.contains(this.a_fields.get(i))) {
				/* update (bidirectional) mirror object field value with inherited class field value */
				(this.getClass().cast(o_inheritedInstance)).setField(this.a_fields.get(i), this.getField(this.a_fields.get(i)));
			}
		}
		
		if (p_b_updateBidirectionalMirror) {
			/* reverse placeholder variable with bidirectional mirror object */
			this.o_mirrorBidirectional = o_inheritedInstance;
		} else {
			/* reverse placeholder variable with mirror object */
			this.o_mirror = o_inheritedInstance;
		}
	}
	
	/**
	 * Update whole mirror object with inherited parameter object
	 * 
	 * @param p_o_object							object as base for updating whole mirror
	 * @throws Exception							any exception which can happen during creating a new instance
	 * @throws NoSuchFieldException					a field does not exist
	 * @throws IllegalAccessException				cannot access field, must be public
	 */
	public void updateWholeMirror(SharedMemory<?> p_o_object) throws Exception {
		this.updateWholeMirror(p_o_object, false);
	}
	
	/**
	 * Update (bidirectional) mirror object with inherited parameter object
	 * 
	 * @param p_o_object							object as base for updating whole (bidirectional) mirror
	 * @param p_b_updateBidirectionalMirror			true - update bidirectional mirror object, false - update mirror object
	 * @throws Exception							any exception which can happen during creating a new instance
	 * @throws NoSuchFieldException					a field does not exist
	 * @throws IllegalAccessException				cannot access field, must be public
	 */
	public void updateWholeMirror(SharedMemory<?> p_o_object, boolean p_b_updateBidirectionalMirror) throws Exception {
		/* iterate all fields from shared memory object parameter */
		for (int i = 1; i <= p_o_object.getAmountFields(); i++) {
			String s_field = p_o_object.returnFieldName(i);
			
			/* check if field exists in this current shared memory class */
			if (this.fieldExists(s_field)) {
				/* update field value */
				this.setField(s_field, p_o_object.getField(s_field));
			}
		}
		
		/* update (bidirectional) mirror object */
		this.updateMirror(p_b_updateBidirectionalMirror);
	}
		
	/**
	 * Initiate mirror and bidirectional mirror object fields with inherited class fields. If mirror field objects are null, new instances will be created
	 * 
	 * @throws Exception							any exception which can happen during creating a new instance
	 * @throws NoSuchFieldException					a field does not exist
	 * @throws IllegalAccessException				cannot access field, must be public
	 */
	public void initiateMirrors() throws Exception {
		/* initiate mirror object */
		if (this.o_mirror == null) {
			this.updateMirror(false);
		}
		
		/* initiate bidirectional mirror object */
		if (this.o_mirrorBidirectional == null) {
			this.updateMirror(true);
		}
	}
	
	/**
	 * Update (bidirectional) mirror object field value with inherited class field value by field name. If mirror field object is null a new instance will be created
	 * 
	 * @param p_s_field								field name
	 * @param p_b_updateBidirectionalMirror			true - update field of bidirectional mirror object, false - update field of mirror object
	 * @throws Exception							any exception which can happen during creating a new instance
	 * @throws NoSuchFieldException					field does not exist
	 * @throws IllegalAccessException				cannot access field, must be public
	 */
	public void updateMirrorField(String p_s_field, boolean p_b_updateBidirectionalMirror) throws Exception {
		/* check if field really exists in inherited class */
		if (!this.fieldExists(p_s_field)) {
			throw new NoSuchFieldException("Field[" + p_s_field + "] does not exist");
		}
		
		/* check if field is not in the list of reserved field names of ShareMemory class */
		if (a_reservedFields.contains(p_s_field)) {
			throw new IllegalAccessException("Cannot set field[" + p_s_field + "]. Reserved field names " + this.a_reservedFields.toString() + " are not accessible");
		}
		
		/* placeholder variable for mirror or bidirectional mirror object */
		T o_inheritedInstance = null;
		
		if (p_b_updateBidirectionalMirror) {
			/* set placeholder variable with bidirectional mirror object */
			o_inheritedInstance = this.o_mirrorBidirectional;
		} else {
			/* set placeholder variable with mirror object */
			o_inheritedInstance = this.o_mirror;
		}
		
		Exception o_caughtException = null;
		
		if (o_inheritedInstance == null) {
			/* set write lock, so only one thread at a time has access to a field */
			this.o_lock.writeLock().lock();
			
			try {
				/* create new instance of (bidirectional) mirror object */
				o_inheritedInstance = this.o_mirrorClass.getDeclaredConstructor().newInstance();
			} catch (Exception o_exc) {
				o_caughtException = o_exc;
			} finally {
				/* unset write lock */
				this.o_lock.writeLock().unlock();
			}
			
			/* throw reminded caught exception */
			if (o_caughtException != null) {
				throw o_caughtException;
			}
		}

		/* update (bidirectional) mirror object field value with inherited class field value */
		(this.getClass().cast(o_inheritedInstance)).setField(p_s_field, this.getField(p_s_field));
		
		if (p_b_updateBidirectionalMirror) {
			/* reverse placeholder variable with bidirectional mirror object */
			this.o_mirrorBidirectional = o_inheritedInstance;
		} else {
			/* reverse placeholder variable with mirror object */
			this.o_mirror = o_inheritedInstance;
		}
	}
}
