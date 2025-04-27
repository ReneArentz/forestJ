package net.forestany.forestj.lib.net.msg;

/**
 * Network message box which can hold a huge number of messages. Functionality corresponds to that of a queue with reentrant lock.
 * A complete network message or an entire object can be enqueued/dequeued to the message box.
 * All data will be serialized into bytes, so objects need to implement java.io.Serializable interface. Alternatively a forestJ implementation of marshalling can be used.
 */
public class MessageBox {

	/* Fields */
	
	private final java.util.concurrent.locks.ReentrantLock o_lock = new java.util.concurrent.locks.ReentrantLock();

	private int i_messageBoxId;
	private int i_messageLength;
	private int i_limit;
	private java.util.Queue<Message> o_messageQueue;
	
	/* Properties */
	
	/**
	 * get message box id
	 * 
	 * @return int
	 */
	public int getMessageBoxId() {
		return this.i_messageBoxId;
	}
	
	/**
	 * get message length
	 * 
	 * @return int
	 */
	public int getMessageLength() {
		return this.i_messageLength;
	}
	
	/**
	 * get limit
	 * 
	 * @return int
	 */
	public int getLimit() {
		return this.i_limit;
	}
	
	/**
	 *  get message amount
	 * @return int
	 */
	public int getMessageAmount() {
		if (this.o_messageQueue != null) {
			return this.o_messageQueue.size();
		} else {
			return 0;
		}
	}
	
	/* Methods */
	
	/**
	 * Message box constructor, no limit
	 * 
	 * @param p_i_messageBoxId					id integer value for message box
	 * @param p_i_messageLength					message length integer value
	 * @throws IllegalArgumentException			invalid parameter value for message box id, message length
	 */
	public MessageBox(int p_i_messageBoxId, int p_i_messageLength) throws IllegalArgumentException {
		this(p_i_messageBoxId, p_i_messageLength, 0);
	}
	
	/**
	 * Message box constructor
	 * 
	 * @param p_i_messageBoxId					id integer value for message box
	 * @param p_i_messageLength					message length integer value
	 * @param p_i_limit							limit amount for messages within message box
	 * @throws IllegalArgumentException			invalid parameter value for message box id, message length or limit value
	 */
	public MessageBox(int p_i_messageBoxId, int p_i_messageLength, int p_i_limit) throws IllegalArgumentException {
		/* check message box id parameter */
		if (p_i_messageBoxId < 1) {
			throw new IllegalArgumentException("Message box id paramter[" + p_i_messageBoxId + "] must be a positive integer");
		}
		
		/* check message length parameter */
		if (!java.util.Arrays.stream(Message.MSGLENGTHS).anyMatch(i -> i == p_i_messageLength)) {
			throw new IllegalArgumentException("Invalid message length[" + p_i_messageLength + "] parameter. Valid values are [" + Message.MSGLENGTHS_STR + "]");
		}
		
		/* check limit parameter, zero for unlimited */
		if (p_i_limit < 0) {
			throw new IllegalArgumentException("Limit paramter[" + p_i_limit + "] must be a positive integer or zero");
		}
		
		this.i_messageBoxId = p_i_messageBoxId;
		this.i_messageLength = p_i_messageLength;
		this.i_limit = p_i_limit;
		
		/* initialize queue */
		this.o_messageQueue = new java.util.LinkedList<Message>();
	}
	
	/**
	 * Enqueue object to message box queue
	 * 
	 * @param p_o_object						object parameter, must implement java.io.Serializable interface
	 * @return									true - object enqueued as message, false - limit of message queue reached or exception occurred
	 * @throws IllegalArgumentException			object parameter has not implemented java.io.Serializable interface
	 */
	public boolean enqueueObject(Object p_o_object) throws IllegalArgumentException {
		/* check if object has implemented java.io.Serializable interface */
		if (!(p_o_object instanceof java.io.Serializable)) {
			throw new IllegalArgumentException("Object[" + p_o_object.getClass().toString() + "(" + p_o_object.toString() + ")] has not implemented java.io.Serializable interface");
		}
		
		/* check message box limit */
		if ( (this.i_limit > 0) && (this.o_messageQueue.size() >= this.i_limit) ) {
													net.forestany.forestj.lib.Global.ilogFinest("message box limit reached: " + this.i_limit);
			
			return false;
		}
		
		/* acquires the lock only if it is not held by another thread */
		if (o_lock.tryLock()) {
			boolean b_return = true;
			
			/* create stream objects to handle data for network message */
			try (
				java.io.ByteArrayOutputStream o_byteArrayOutputStream = new java.io.ByteArrayOutputStream();
				java.io.ObjectOutputStream o_objectOutputStream = new java.io.ObjectOutputStream(o_byteArrayOutputStream);
			) {
				/* read object data and serialize it to byte array */
				o_objectOutputStream.writeObject(p_o_object);
				o_objectOutputStream.flush();
				byte[] a_bytes = o_byteArrayOutputStream.toByteArray();
				
				/* get data block length for network message */
				int i_dataBlockLength = Message.calculateDataBlockLength(this.i_messageLength);
				
				/* calculate how many messages we need to transport object with network messages */
				int i_messages = 1 + (a_bytes.length / i_dataBlockLength);
				
				/* check if amount of messages for object will not exceed message box limit */
				if ( (this.i_limit > 0) && ((this.o_messageQueue.size() + i_messages) >= this.i_limit) ) {
															net.forestany.forestj.lib.Global.ilogFinest("message box limit '" + this.i_limit + "' reached with '" + i_messages + "' message(s) for object");
					
					b_return = false;
				} else {
					/* split object data into several messages */
					for (int i = 0; i < i_messages; i++) {
						/* create network message object with message length property of message box */
						Message o_message = new Message(this.i_messageLength);
						
						/* set message information, like message box id, amount of messages, message number and type */
						o_message.setMessageBoxId(this.i_messageBoxId);
						o_message.setMessageAmount(i_messages);
						o_message.setMessageNumber(i + 1);
						o_message.setType(p_o_object.getClass().getTypeName());
						
						/* create byte array for part of object data */
						byte[] a_data = new byte[i_dataBlockLength];
						int j = 0;
						
						/* iterate all data bytes until we reached message data block length */
						for (j = 0; j < i_dataBlockLength; j++) {
							/* reached last byte? -> break */
							if ( j + (i * i_dataBlockLength) >= a_bytes.length ) {
								break;
							}
							
							/* copy data byte to byte array */
							a_data[j] = a_bytes[j + (i * i_dataBlockLength)];
						}
						
						/* give byte array as data part to network message object */
						o_message.setData(a_data);
						
						/* part of object data may not need complete data block length, especially the last message or just one message, so this is not obsolete because the for loop before could be break before j == i_dataBlockLength - 1 */
						if (j != i_dataBlockLength - 1) {
							/* update message data length */
							o_message.setDataLength(j);
						}

						/* add network message to message box queue */
						this.o_messageQueue.add(o_message);
					}
				}
			} catch (Exception o_exc) {
														net.forestany.forestj.lib.Global.ilogWarning("could not enqueue object: " + o_exc.getMessage());
				
				b_return = false;
			} finally {
				/* release lock */
				o_lock.unlock();
			}
			
			return b_return;
		} else {
			/* lock is held by another thread */
			return false;
		}
	}
	
	/**
	 * Enqueue and marshall object with all fields of primitive types or supported types to message box queue. Transfering data as big endian. Handle data as big endian. Do not use property methods to retrieve field values. 1 byte is used used to marshall the length of data.
	 * 
	 * @param p_o_object										object parameter
	 * @return													true - object enqueued as message, false - limit of message queue reached or exception occurred
	 * @throws NullPointerException								parameter object is null
	 * @throws IllegalArgumentException							data length in bytes must be between 1..4
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws NoSuchMethodException							could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 		could not invoke method from object
	 * @throws IllegalAccessException							could not invoke method, access violation
	 */
	public boolean enqueueObjectWithMarshalling(Object p_o_object) throws NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		return this.enqueueObjectWithMarshalling(p_o_object, 1);
	}
	
	/**
	 * Enqueue and marshall object with all fields of primitive types or supported types to message box queue. Transfering data as big endian. Handle data as big endian. Do not use property methods to retrieve field values.
	 * 
	 * @param p_o_object										object parameter
	 * @param p_i_dataLengthInBytes								define how many bytes are used to marshall the length of data
	 * @return													true - object enqueued as message, false - limit of message queue reached or exception occurred
	 * @throws NullPointerException								parameter object is null
	 * @throws IllegalArgumentException							data length in bytes must be between 1..4
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws NoSuchMethodException							could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 		could not invoke method from object
	 * @throws IllegalAccessException							could not invoke method, access violation
	 */
	public boolean enqueueObjectWithMarshalling(Object p_o_object, int p_i_dataLengthInBytes) throws NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		return this.enqueueObjectWithMarshalling(p_o_object, p_i_dataLengthInBytes, false);
	}
	
	/**
	 * Enqueue and marshall object with all fields of primitive types or supported types to message box queue. Transfering data as big endian. Handle data as big endian.
	 * 
	 * @param p_o_object										object parameter
	 * @param p_i_dataLengthInBytes								define how many bytes are used to marshall the length of data
	 * @param p_b_usePropertyMethods							access object parameter fields via property methods e.g. T getXYZ()
	 * @return													true - object enqueued as message, false - limit of message queue reached or exception occurred
	 * @throws NullPointerException								parameter object is null
	 * @throws IllegalArgumentException							data length in bytes must be between 1..4
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws NoSuchMethodException							could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 		could not invoke method from object
	 * @throws IllegalAccessException							could not invoke method, access violation
	 */
	public boolean enqueueObjectWithMarshalling(Object p_o_object, int p_i_dataLengthInBytes, boolean p_b_usePropertyMethods) throws NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		return this.enqueueObjectWithMarshalling(p_o_object, p_i_dataLengthInBytes, p_b_usePropertyMethods, null);
	}
	
	/**
	 * Enqueue and marshall object with all fields of primitive types or supported types to message box queue. Transfering data as big endian.
	 * 
	 * @param p_o_object										object parameter
	 * @param p_i_dataLengthInBytes								define how many bytes are used to marshall the length of data
	 * @param p_b_usePropertyMethods							access object parameter fields via property methods e.g. T getXYZ()
	 * @param p_s_overrideMessageType       					override message type with this string and do not get it automatically from object, thus the type can be set generally from other systems with other programming languages
	 * @return													true - object enqueued as message, false - limit of message queue reached or exception occurred
	 * @throws NullPointerException								parameter object is null
	 * @throws IllegalArgumentException							data length in bytes must be between 1..4
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws NoSuchMethodException							could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 		could not invoke method from object
	 * @throws IllegalAccessException							could not invoke method, access violation
	 */
	public boolean enqueueObjectWithMarshalling(Object p_o_object, int p_i_dataLengthInBytes, boolean p_b_usePropertyMethods, String p_s_overrideMessageType) throws NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
	    return this.enqueueObjectWithMarshalling(p_o_object, p_i_dataLengthInBytes, p_b_usePropertyMethods, p_s_overrideMessageType, false);
	}
	
	/**
	 * Enqueue and marshall object with all fields of primitive types or supported types to message box queue. Transfering data as big endian.
	 * 
	 * @param p_o_object										object parameter
	 * @param p_i_dataLengthInBytes								define how many bytes are used to marshall the length of data
	 * @param p_b_usePropertyMethods							access object parameter fields via property methods e.g. T getXYZ()
	 * @param p_s_overrideMessageType       					override message type with this string and do not get it automatically from object, thus the type can be set generally from other systems with other programming languages
	 * @param p_b_systemUsesLittleEndian						true - current execution system uses little endian, false - current execution system uses big endian
	 * @return													true - object enqueued as message, false - limit of message queue reached or exception occurred
	 * @throws NullPointerException								parameter object is null
	 * @throws IllegalArgumentException							data length in bytes must be between 1..4
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws NoSuchMethodException							could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 		could not invoke method from object
	 * @throws IllegalAccessException							could not invoke method, access violation
	 */
	public boolean enqueueObjectWithMarshalling(Object p_o_object, int p_i_dataLengthInBytes, boolean p_b_usePropertyMethods, String p_s_overrideMessageType, boolean p_b_systemUsesLittleEndian) throws NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		/* check message box limit */
		if ( (this.i_limit > 0) && (this.o_messageQueue.size() >= this.i_limit) ) {
													net.forestany.forestj.lib.Global.ilogFinest("message box limit reached: " + this.i_limit);
			
			return false;
		}
		
		/* acquires the lock only if it is not held by another thread */
		if (o_lock.tryLock()) {
			boolean b_return = true;
			
			/* marshall object parameter to handle data for network message */
			try {
				/* call marshall object method */
				byte[] a_bytes = Marshall.marshallObject(p_o_object, p_i_dataLengthInBytes, p_b_usePropertyMethods, p_b_systemUsesLittleEndian);
				
														net.forestany.forestj.lib.Global.ilogFiner("marshalled object to " + a_bytes.length + " bytes");
				
				/* get data block length for network message */
				int i_dataBlockLength = Message.calculateDataBlockLength(this.i_messageLength);
				
				/* calculate how many messages we need to transport object with network messages */
				int i_messages = 1 + (a_bytes.length / i_dataBlockLength);
				
				/* check if amount of messages for object will not exceed message box limit */
				if ( (this.i_limit > 0) && ((this.o_messageQueue.size() + i_messages) >= this.i_limit) ) {
															net.forestany.forestj.lib.Global.ilogFinest("message box limit '" + this.i_limit + "' reached with '" + i_messages + "' message(s) for object");
					
					b_return = false;
				} else {
					/* split object data into several messages */
					for (int i = 0; i < i_messages; i++) {
						/* create network message object with message length property of message box */
						Message o_message = new Message(this.i_messageLength);
						
						/* set message information, like message box id, amount of messages, message number */
						o_message.setMessageBoxId(this.i_messageBoxId);
						o_message.setMessageAmount(i_messages);
						o_message.setMessageNumber(i + 1);
						
						/* get universal primitive type name if available */
						if (p_o_object == null) {
						    o_message.setType("null");
						} else if ( (p_o_object.getClass() == Boolean.class) || (p_o_object.getClass() == boolean.class) ) {
						    o_message.setType("bool");
						} else if ( (p_o_object.getClass().isArray()) && ( (p_o_object.getClass().getComponentType() == Boolean.class) || (p_o_object.getClass().getComponentType() == boolean.class) ) ) {
						    o_message.setType("bool[]");
						} else if ( (p_o_object.getClass() == Byte.class) || (p_o_object.getClass() == byte.class) ) {
						    o_message.setType("byte");
						} else if ( (p_o_object.getClass().isArray()) && ( (p_o_object.getClass().getComponentType() == Byte.class) || (p_o_object.getClass().getComponentType() == byte.class) ) ) {
						    o_message.setType("byte[]");
						} else if ( (p_o_object.getClass() == Character.class) || (p_o_object.getClass() == char.class) ) {
						    o_message.setType("char");
						} else if ( (p_o_object.getClass().isArray()) && ( (p_o_object.getClass().getComponentType() == Character.class) || (p_o_object.getClass().getComponentType() == char.class) ) ) {
						    o_message.setType("char[]");
						} else if ( (p_o_object.getClass() == Float.class) || (p_o_object.getClass() == float.class) ) {
						    o_message.setType("float");
						} else if ( (p_o_object.getClass().isArray()) && ( (p_o_object.getClass().getComponentType() == Float.class) || (p_o_object.getClass().getComponentType() == float.class) ) ) {
						    o_message.setType("float[]");
						} else if ( (p_o_object.getClass() == Double.class) || (p_o_object.getClass() == double.class) ) {
						    o_message.setType("double");
						} else if ( (p_o_object.getClass().isArray()) && ( (p_o_object.getClass().getComponentType() == Double.class) || (p_o_object.getClass().getComponentType() == double.class) ) ) {
						    o_message.setType("double[]");
						} else if ( (p_o_object.getClass() == Short.class) || (p_o_object.getClass() == short.class) ) {
						    o_message.setType("short");
						} else if ( (p_o_object.getClass().isArray()) && ( (p_o_object.getClass().getComponentType() == Short.class) || (p_o_object.getClass().getComponentType() == short.class) ) ) {
						    o_message.setType("short[]");
						} else if ( (p_o_object.getClass() == Integer.class) || (p_o_object.getClass() == int.class) ) {
						    o_message.setType("int");
						} else if ( (p_o_object.getClass().isArray()) && ( (p_o_object.getClass().getComponentType() == Integer.class) || (p_o_object.getClass().getComponentType() == int.class) ) ) {
						    o_message.setType("int[]");
						} else if ( (p_o_object.getClass() == Long.class) || (p_o_object.getClass() == long.class) ) {
						    o_message.setType("long");
						} else if ( (p_o_object.getClass().isArray()) && ( (p_o_object.getClass().getComponentType() == Long.class) || (p_o_object.getClass().getComponentType() == long.class) ) ) {
						    o_message.setType("long[]");
						} else if (p_o_object.getClass() == String.class) {
						    o_message.setType("string");
						} else if ( (p_o_object.getClass().isArray()) && (p_o_object.getClass().getComponentType() == String.class) ) {
						    o_message.setType("string[]");
						} else if (p_o_object.getClass() == java.util.Date.class) {
						    o_message.setType("Date");
						} else if ( (p_o_object.getClass().isArray()) && (p_o_object.getClass().getComponentType() == java.util.Date.class) ) {
						    o_message.setType("Date[]");
						} else if (p_o_object.getClass() == java.time.LocalTime.class) {
						    o_message.setType("LocalTime");
						} else if ( (p_o_object.getClass().isArray()) && (p_o_object.getClass().getComponentType() == java.time.LocalTime.class) ) {
						    o_message.setType("LocalTime[]");
						} else if (p_o_object.getClass() == java.time.LocalDate.class) {
						    o_message.setType("LocalDate");
						} else if ( (p_o_object.getClass().isArray()) && (p_o_object.getClass().getComponentType() == java.time.LocalDate.class) ) {
						    o_message.setType("LocalDate[]");
						} else if (p_o_object.getClass() == java.time.LocalDateTime.class) {
						    o_message.setType("DateTime");
						} else if ( (p_o_object.getClass().isArray()) && (p_o_object.getClass().getComponentType() == java.time.LocalDateTime.class) ) {
						    o_message.setType("DateTime[]");
						} else if (p_o_object.getClass() == java.math.BigDecimal.class) {
						    o_message.setType("decimal");
						} else if ( (p_o_object.getClass().isArray()) && (p_o_object.getClass().getComponentType() == java.math.BigDecimal.class) ) {
						    o_message.setType("decimal[]");
						} else {
						    if (p_s_overrideMessageType != null) {
						        /* override message type with parameter, thus the type can be set generally from other systems with other programming languages */
						        o_message.setType(p_s_overrideMessageType);
						    } else {
						        /* no primitive type -> set object type name */
						    	o_message.setType(p_o_object.getClass().getTypeName());
						    }
						}
						
						/* create byte array for part of object data */
						byte[] a_data = new byte[i_dataBlockLength];
						int j = 0;
						
						/* iterate all data bytes until we reached message data block length */
						for (j = 0; j < i_dataBlockLength; j++) {
							/* reached last byte? -> break */
							if ( j + (i * i_dataBlockLength) >= a_bytes.length ) {
								break;
							}
							
							/* copy data byte to byte array */
							a_data[j] = a_bytes[j + (i * i_dataBlockLength)];
						}
						
						/* give byte array as data part to network message object */
						o_message.setData(a_data);
						
						/* part of object data may not need complete data block length, especially the last message or just one message, so this is not obsolete because the for loop before could be break before j == i_dataBlockLength - 1 */
						if (j != i_dataBlockLength - 1) {
							/* update message data length */
							o_message.setDataLength(j);
						}

						/* add network message to message box queue */
						this.o_messageQueue.add(o_message);
					}
				}
			} catch (Exception o_exc) {
														net.forestany.forestj.lib.Global.ilogWarning("could not enqueue object: " + o_exc.getMessage());
				
				b_return = false;
			} finally {
				/* release lock */
				o_lock.unlock();
			}
			
			return b_return;
		} else {
			/* lock is held by another thread */
			return false;
		}
	}
	
	/**
	 * Enqueue message object to message box queue
	 * 
	 * @param p_o_message						network message object
	 * @return									true - message enqueued, false - limit of message queue reached or exception occurred
	 */
	public boolean enqueueMessage(Message p_o_message) {
		/* check message box limit */
		if ( (this.i_limit > 0) && (this.o_messageQueue.size() >= this.i_limit) ) {
													net.forestany.forestj.lib.Global.ilogFinest("message box limit reached: " + this.i_limit);
			
			return false;
		}
		
		/* message object length does not correspond to message box message length */
		if (p_o_message.getMessageLength() != this.i_messageLength) {
													net.forestany.forestj.lib.Global.ilogFinest("message object length '" + p_o_message.getMessageLength() + "' does not correspond to message box message length '" + this.i_messageLength + "'");
			
			return false;
		}
		
		/* acquires the lock only if it is not held by another thread */
		if (o_lock.tryLock()) {
			boolean b_return = true;
			
			try {
				/* add network message to message box queue */
				this.o_messageQueue.add(p_o_message);
			} catch (Exception o_exc) {
														net.forestany.forestj.lib.Global.ilogWarning("could not enqueue message: " + o_exc.getMessage());
				b_return = false;
			} finally {
				/* release lock */
				o_lock.unlock();
			}
			
			return b_return;
		} else {
			/* lock is held by another thread */
			return false;
		}
	}
	
	/**
	 * Retrieves, but does not remove, the head message of message box queue
	 * 
	 * @return network message object
	 */
	public Message currentMessage() {
		Message o_return = null;
		
		/* check if message box queue is not empty */
		if (this.o_messageQueue.size() <= 0) {
			return o_return;
		}
		
		/* acquires the lock only if it is not held by another thread */
		if (o_lock.tryLock()) {
			try {
				/* get head message of message box queue */
				o_return = this.o_messageQueue.element();
			} catch (Exception o_exc) {
				/* nothing to do */
			} finally {
				/* release lock */
				o_lock.unlock();
			}
			
			/* return head message */
			return o_return;
		} else {
			/* lock is held by another thread */
			return o_return;
		}
	}
	
	/**
	 * Dequeue object from message box queue
	 * 
	 * @return object casted to it's original type - null if transmission is not complete, data could not be merged, or original class type cannot be found
	 */
 	public Object dequeueObject() {
 		/* return value, standard = null */
		Object o_return = null;
		
		/* check if message box queue is not empty */
		if (this.o_messageQueue.size() <= 0) {
			return o_return;
		}
		
		/* acquires the lock only if it is not held by another thread */
		if (o_lock.tryLock()) {
			/* create variables to dequeue object from message box */
			Message o_message = null;
			int i_messageDataLength = 0;
			java.util.List<Byte> a_bytes = new java.util.ArrayList<Byte>();
			boolean b_enoughMessages = true;
			
			try {
				/* get head message of message box queue */
				o_message = this.o_messageQueue.element();
				
				/* get amount of message we need to merge object together */ 
				int i_messageAmount = o_message.getMessageAmount();
				
				/* check if we have enough messages in our message box queue for merging */
				if (i_messageAmount > this.o_messageQueue.size()) {
															net.forestany.forestj.lib.Global.ilogFiner("not enough messages[" + i_messageAmount + " > " + this.o_messageQueue.size() + "], transmission not complete");
					
					b_enoughMessages = false;
				} else {
					/* dequeue until all messages for object have been retrieved */
					do {
						/* dequeue message from message box queue */
						o_message = this.o_messageQueue.remove();
						
						/* calculate overall data length for object */
						i_messageDataLength += o_message.getDataLength();
						
						/* gather bytes */
						for (int i = 0; i < o_message.getDataLength(); i++) {
							/* add byte to dynamic byte list */
							a_bytes.add(o_message.getData()[i]);
						}
					} while ((i_messageAmount - o_message.getMessageNumber()) != 0);
				}
			} catch (Exception o_exc) {
				/* nothing to do */
				
														net.forestany.forestj.lib.Global.ilogWarning("could not dequeue object: " + o_exc.getMessage());
			} finally {
				/* release lock */
				o_lock.unlock();
			}
			
			/* not enough messages for merging object together, returning null */
			if (!b_enoughMessages) {
				return o_return;
			}
			
			/* create byte array which holds all object's byte data */
			byte[] a_messageBytes = new byte[i_messageDataLength];
			int i = 0;
			
			/* copy content bytes from dynamic byte list to byte array */
			for (byte by_byte : a_bytes) {
				a_messageBytes[i++] = by_byte;
			}
			
			/* read byte array to object to complete merging object data */
			try (
				java.io.ByteArrayInputStream o_byteArrayInputStream = new java.io.ByteArrayInputStream(a_messageBytes);
				java.io.ObjectInput o_objectInput = new java.io.ObjectInputStream(o_byteArrayInputStream);
			) {
				o_return = o_objectInput.readObject(); 
			} catch (Exception o_exc) {
														net.forestany.forestj.lib.Global.ilogWarning("could not read merged object data: " + o_exc.getMessage());
				o_return = null;
			}
			
			/* only cast if deserialized object could be read */
			if (o_return != null) {
				try {
					/* cast return object by string object type value */
					 Class<?> o_class = Class.forName(o_message.getType());
					 o_return = o_class.cast(o_return);
				} catch (ClassNotFoundException o_exc) {
															net.forestany.forestj.lib.Global.ilogWarning("could not find class for object cast: " + o_exc.getMessage());
					
					o_return = null;
				}
			}
			
			/* return dequeued object */
			return o_return;
		} else {
			/* lock is held by another thread */
			return o_return;
		}
	}
	
 	/**
 	 * Dequeue object from message box queue and unmarshall it's data. Receiving data as big endian. Handle data as big endian. Do not use property methods to retrieve field values.
 	 * 
 	 * @return object casted to it's original type - null if transmission is not complete, data could not be merged, or original class type cannot be found
 	 */
 	public Object dequeueObjectWithMarshalling() {
 		return this.dequeueObjectWithMarshalling(false);
 	}
 	
 	/**
 	 * Dequeue object from message box queue and unmarshall it's data. Receiving data as big endian. Handle data as big endian.
 	 * 
 	 * @param p_b_usePropertyMethods				access object parameter fields via property methods e.g. T getXYZ()
 	 * @return object casted to it's original type - null if transmission is not complete, data could not be merged, or original class type cannot be found
 	 */
 	public Object dequeueObjectWithMarshalling(boolean p_b_usePropertyMethods) {
 		return this.dequeueObjectWithMarshalling(p_b_usePropertyMethods, false);
 	}
 	
 	/**
 	 * Dequeue object from message box queue and unmarshall it's data. Receiving data as big endian.
 	 * 
 	 * @param p_b_usePropertyMethods				access object parameter fields via property methods e.g. T getXYZ()
 	 * @param p_b_systemUsesLittleEndian			true - current execution system uses little endian, false - current execution system uses big endian
 	 * @return object casted to it's original type - null if transmission is not complete, data could not be merged, or original class type cannot be found
 	 */
 	public Object dequeueObjectWithMarshalling(boolean p_b_usePropertyMethods, boolean p_b_systemUsesLittleEndian) {
 		/* return value, standard = null */
		Object o_return = null;
		
		/* check if message box queue is not empty */
		if (this.o_messageQueue.size() <= 0) {
			return o_return;
		}
		
		/* acquires the lock only if it is not held by another thread */
		if (o_lock.tryLock()) {
			/* create variables to dequeue object from message box */
			Message o_message = null;
			int i_messageDataLength = 0;
			java.util.List<Byte> a_bytes = new java.util.ArrayList<Byte>();
			boolean b_enoughMessages = true;
			
			try {
				/* get head message of message box queue */
				o_message = this.o_messageQueue.element();
				
				/* get amount of message we need to merge object together */ 
				int i_messageAmount = o_message.getMessageAmount();
				
				/* check if we have enough messages in our message box queue for merging */
				if (i_messageAmount > this.o_messageQueue.size()) {
															net.forestany.forestj.lib.Global.ilogFiner("not enough messages[" + i_messageAmount + " > " + this.o_messageQueue.size() + "], transmission not complete");
					
					b_enoughMessages = false;
				} else {
					/* dequeue until all messages for object have been retrieved */
					do {
						/* dequeue message from message box queue */
						o_message = this.o_messageQueue.remove();
						
						/* calculate overall data length for object */
						i_messageDataLength += o_message.getDataLength();
						
						/* gather bytes */
						for (int i = 0; i < o_message.getDataLength(); i++) {
							/* add byte to dynamic byte list */
							a_bytes.add(o_message.getData()[i]);
						}
					} while ((i_messageAmount - o_message.getMessageNumber()) != 0);
				}
			} catch (Exception o_exc) {
				/* nothing to do */
				
														net.forestany.forestj.lib.Global.ilogWarning("could not dequeue object: " + o_exc.getMessage());
			} finally {
				/* release lock */
				o_lock.unlock();
			}
			
			/* not enough messages for merging object together, returning null */
			if (!b_enoughMessages) {
				return o_return;
			}
			
			/* create byte array which holds all object's byte data */
			byte[] a_messageBytes = new byte[i_messageDataLength];
			int i = 0;
			
			/* copy content bytes from dynamic byte list to byte array */
			for (byte by_byte : a_bytes) {
				a_messageBytes[i++] = by_byte;
			}
			
			/* return dequeued unmarshalled object */
			return MessageBox.unmarshallObjectFromMessage(o_message.getType(), a_messageBytes, p_b_usePropertyMethods, p_b_systemUsesLittleEndian);
		} else {
			/* lock is held by another thread */
			return o_return;
		}
 	}
 	
 	/**
 	 * Unmarshall object from message instance an it's content byte array.
 	 * 
 	 * @param p_s_type								type string of message instance
 	 * @param p_a_messageBytes						message content as array of bytes
 	 * @param p_b_usePropertyMethods				access object parameter fields via property methods e.g. T getXYZ()
 	 * @param p_b_systemUsesLittleEndian			true - current execution system uses little endian, false - current execution system uses big endian
 	 * @return object casted to it's original type - null if transmission is not complete, data could not be merged, or original class type cannot be found
 	 */
 	public static Object unmarshallObjectFromMessage(String p_s_type, byte[] p_a_messageBytes, boolean p_b_usePropertyMethods, boolean p_b_systemUsesLittleEndian) {
 		/* return variable */
 		Object o_return = null;
 		
 		/* generic class object */
		Class<?> o_targetType = null;
		String s_type = p_s_type;
		
		/* get class name and create new instance for return object by string object type value */
		try {
			/* map primitive types to class types */
			if (s_type.contentEquals("null")) {
				return null;
			} else if (s_type.contentEquals("bool")) {
				o_targetType = Boolean.class;
			} else if (s_type.contentEquals("bool[]")) {
				o_targetType = Boolean.class;
			} else if (s_type.contentEquals("byte")) {
				o_targetType = Byte.class;
			} else if (s_type.contentEquals("byte[]")) {
				o_targetType = Byte.class;
			} else if (s_type.contentEquals("char")) {
				o_targetType = Character.class;
			} else if (s_type.contentEquals("char[]")) {
				o_targetType = Character.class;
			} else if (s_type.contentEquals("float")) {
				o_targetType = Float.class;
			} else if (s_type.contentEquals("float[]")) {
				o_targetType = Float.class;
			} else if (s_type.contentEquals("double")) {
				o_targetType = Double.class;
			} else if (s_type.contentEquals("double[]")) {
				o_targetType = Double.class;
			} else if (s_type.contentEquals("short")) {
				o_targetType = Short.class;
			} else if (s_type.contentEquals("short[]")) {
				o_targetType = Short.class;
			} else if (s_type.contentEquals("int")) {
				o_targetType = Integer.class;
			} else if (s_type.contentEquals("int[]")) {
				o_targetType = Integer.class;
			} else if (s_type.contentEquals("long")) {
				o_targetType = Long.class;
			} else if (s_type.contentEquals("long[]")) {
				o_targetType = Long.class;
			} else if (s_type.contentEquals("string")) {
				o_targetType = String.class;
			} else if (s_type.contentEquals("string[]")) {
				o_targetType = String.class;
			} else if (s_type.contentEquals("Date")) {
				o_targetType = java.util.Date.class;
			} else if (s_type.contentEquals("Date[]")) {
				o_targetType = java.util.Date.class;
			} else if (s_type.contentEquals("LocalTime")) {
				o_targetType = java.time.LocalTime.class;
			} else if (s_type.contentEquals("LocalTime[]")) {
				o_targetType = java.time.LocalTime.class;
			} else if (s_type.contentEquals("LocalDate")) {
				o_targetType = java.time.LocalDate.class;
			} else if (s_type.contentEquals("LocalDate[]")) {
				o_targetType = java.time.LocalDate.class;
			} else if (s_type.contentEquals("DateTime")) {
				o_targetType = java.time.LocalDateTime.class;
			} else if (s_type.contentEquals("DateTime[]")) {
				o_targetType = java.time.LocalDateTime.class;
			} else if (s_type.contentEquals("decimal")) {
				o_targetType = java.math.BigDecimal.class;
			} else if (s_type.contentEquals("decimal[]")) {
				o_targetType = java.math.BigDecimal.class;
			} else {
				/* get origin type of enqueued data */
				o_targetType = Class.forName(s_type);
			}
		} catch (ClassNotFoundException o_exc) {
													net.forestany.forestj.lib.Global.ilogFinest("could not find class or create a new instance for object: " + o_exc.getMessage());
			
			return null;
		}
		
		/* unmarshall byte array to object to complete merging object data */
		try {
													net.forestany.forestj.lib.Global.ilogFiner("unmarshall object from " + p_a_messageBytes.length + " bytes");
			
			o_return = net.forestany.forestj.lib.net.msg.Marshall.unmarshallObject(o_targetType, p_a_messageBytes, p_b_usePropertyMethods, p_b_systemUsesLittleEndian);
		} catch (Exception o_exc) {
													net.forestany.forestj.lib.Global.ilogWarning("could not unmarshall merged object data: " + o_exc.getMessage());
			o_return = null;
		}
				
		return o_return;
 	}
 	
 	/**
 	 * Dequeue message from message box queue
 	 * 
 	 * @return network message object
 	 */
	public Message dequeueMessage() {
		Message o_return = null;
		
		/* check if message box queue is not empty */
		if (this.o_messageQueue.size() <= 0) {
			return o_return;
		}
		
		/* acquires the lock only if it is not held by another thread */
		if (o_lock.tryLock()) {
			try {
				/* dequeue message from message box queue */
				o_return = this.o_messageQueue.remove();
			} catch (Exception o_exc) {
				/* nothing to do */
			} finally {
				/* release lock */
				o_lock.unlock();
			}
			
			/* return dequeued message */
			return o_return;
		} else {
			/* lock is held by another thread */
			return o_return;
		}
	}
}
