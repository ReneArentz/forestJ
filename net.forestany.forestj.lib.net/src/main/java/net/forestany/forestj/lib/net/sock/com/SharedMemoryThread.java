package net.forestany.forestj.lib.net.sock.com;

/**
 * Class to run a thread to watch a shared memory instance. In that way we can see how the fields of a class are changed in time.
 * SharedMemoryThread can act as a receiver or sender, but not both at the same time. Changed fields will be enqueued or dequeued for network exchange working together with frameworks Communication Class.
 * Each field if inherited class of shared memory instance will have it's own message box for sending/receiving.
 */
public class SharedMemoryThread implements Runnable {
	
	/* Fields */
	
	private Communication o_communication;
	private SharedMemory<?> o_instanceToWatch;
	private int i_timeoutMilliseconds;
	private boolean b_receive;
	private boolean b_stop;
	private long l_lastCompleteRefresh;
	private net.forestany.forestj.lib.DateInterval o_interval;
	private boolean b_bidirectional;
	private boolean b_marshallingWholeObject;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * Create shared memory thread with all necessary information. Is original source site. No interval object, sender will only send changed fields all the time.
	 * 
	 * @param p_o_communication						communication object to enqueue or dequeue network data
	 * @param p_o_instanceToWatch					instance object to be watched by this thread
	 * @param p_i_timeoutMilliseconds				set timeout value for thread instance - how long the shared memory thread should wait after incoming/outgoing data cycle
	 * @param p_e_communicationType					communication type enumeration to determine if shared memory thread will send or receive network data
	 * @throws Exception							any exception which can happen during creating a new instance of instance to be watched
	 * @throws NullPointerException					communication object or instance to watch object parameter is null
	 * @throws IllegalArgumentException				invalid timeout value
	 * @throws NoSuchFieldException					a field does not exist
	 * @throws IllegalAccessException				cannot access field, must be public
	 */
	public SharedMemoryThread(Communication p_o_communication, SharedMemory<?> p_o_instanceToWatch, int p_i_timeoutMilliseconds, Type p_e_communicationType) throws Exception {
		this(p_o_communication, p_o_instanceToWatch, p_i_timeoutMilliseconds, p_e_communicationType, null);
	}
	
	/**
	 * Create shared memory thread with all necessary information. Is original source site
	 * 
	 * @param p_o_communication						communication object to enqueue or dequeue network data
	 * @param p_o_instanceToWatch					instance object to be watched by this thread
	 * @param p_i_timeoutMilliseconds				set timeout value for thread instance - how long the shared memory thread should wait after incoming/outgoing data cycle
	 * @param p_e_communicationType					communication type enumeration to determine if shared memory thread will send or receive network data
	 * @param p_o_interval							interval object for sender cycle. At the end of the interval all field values will be send to other communication side
	 * @throws Exception							any exception which can happen during creating a new instance of instance to be watched
	 * @throws NullPointerException					communication object or instance to watch object parameter is null
	 * @throws IllegalArgumentException				invalid timeout value
	 * @throws NoSuchFieldException					a field does not exist
	 * @throws IllegalAccessException				cannot access field, must be public
	 */
	public SharedMemoryThread(Communication p_o_communication, SharedMemory<?> p_o_instanceToWatch, int p_i_timeoutMilliseconds, Type p_e_communicationType, net.forestany.forestj.lib.DateInterval p_o_interval) throws Exception {
		this(p_o_communication, p_o_instanceToWatch, p_i_timeoutMilliseconds, p_e_communicationType, p_o_interval, false);
	}
	
	/**
	 * Create shared memory thread with all necessary information.
	 * 
	 * @param p_o_communication						communication object to enqueue or dequeue network data
	 * @param p_o_instanceToWatch					instance object to be watched by this thread
	 * @param p_i_timeoutMilliseconds				set timeout value for thread instance - how long the shared memory thread should wait after incoming/outgoing data cycle
	 * @param p_e_communicationType					communication type enumeration to determine if shared memory thread will send or receive network data
	 * @param p_o_interval							interval object for sender cycle. At the end of the interval all field values will be send to other communication side
	 * @param p_b_bidirectional						true - indicate that this shared memory thread communicate with the original source site of data, false - shared memory thread is original source site
	 * @throws Exception							any exception which can happen during creating a new instance of instance to be watched
	 * @throws NullPointerException					communication object or instance to watch object parameter is null
	 * @throws IllegalArgumentException				invalid timeout value
	 * @throws NoSuchFieldException					a field does not exist
	 * @throws IllegalAccessException				cannot access field, must be public
	 */
	public SharedMemoryThread(Communication p_o_communication, SharedMemory<?> p_o_instanceToWatch, int p_i_timeoutMilliseconds, Type p_e_communicationType, net.forestany.forestj.lib.DateInterval p_o_interval, boolean p_b_bidirectional) throws Exception {
		this(p_o_communication, p_o_instanceToWatch, p_i_timeoutMilliseconds, p_e_communicationType, p_o_interval, p_b_bidirectional, false);
	}
	
	/**
	 * Create shared memory thread with all necessary information.
	 * 
	 * @param p_o_communication							communication object to enqueue or dequeue network data
	 * @param p_o_instanceToWatch						instance object to be watched by this thread
	 * @param p_i_timeoutMilliseconds					set timeout value for thread instance - how long the shared memory thread should wait after incoming/outgoing data cycle
	 * @param p_e_communicationType						communication type enumeration to determine if shared memory thread will send or receive network data
	 * @param p_o_interval								interval object for sender cycle. At the end of the interval all field values will be send to other communication side
	 * @param p_b_bidirectional							true - indicate that this shared memory thread communicate with the original source site of data, false - shared memory thread is original source site
	 * @param p_b_marshallingWholeObject				true - use marshalling methods for whole parameter object to transport data over network, especially with shared memory all fields will be transported within a cycle 
	 * @throws Exception								any exception which can happen during creating a new instance of instance to be watched
	 * @throws NullPointerException						communication object or instance to watch object parameter is null
	 * @throws IllegalArgumentException					invalid timeout value
	 * @throws NoSuchFieldException						a field does not exist
	 * @throws IllegalAccessException					cannot access field, must be public
	 */
	public SharedMemoryThread(Communication p_o_communication, SharedMemory<?> p_o_instanceToWatch, int p_i_timeoutMilliseconds, Type p_e_communicationType, net.forestany.forestj.lib.DateInterval p_o_interval, boolean p_b_bidirectional, boolean p_b_marshallingWholeObject) throws Exception {
		/* check if communication parameter is not null */
		if (p_o_communication == null) {
			throw new NullPointerException("Communication parameter is null");
		}
		
		/* check if instance object to watch parameter is not null */
		if (p_o_instanceToWatch == null) {
			throw new NullPointerException("Instance to watch parameter is null");
		}
		
		/* check timeout parameter */
		if (p_i_timeoutMilliseconds < 1) {
			throw new IllegalArgumentException("Shared memory timeout must be at least '1' millisecond, but was set to '" + p_i_timeoutMilliseconds + "' millisecond(s)");
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("\t" + "shared memory thread timeout value in ms: '" + p_i_timeoutMilliseconds + "'");
		
		this.o_communication = p_o_communication;
		this.o_instanceToWatch = p_o_instanceToWatch;
		
		/* update mirror object for instance object to watch */
		this.o_instanceToWatch.updateMirror(false);
		/* update bidirectional mirror object for instance object to watch */
		this.o_instanceToWatch.updateMirror(true);
		
		/* set timeout parameter value */
		this.i_timeoutMilliseconds = p_i_timeoutMilliseconds;
		
		/* set receive flag based on communication type */
		if (
			p_e_communicationType == Type.UDP_RECEIVE || 
			p_e_communicationType == Type.UDP_RECEIVE_WITH_ACK || 
			p_e_communicationType == Type.TCP_RECEIVE
		) {
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "shared memory thread will receive data");
			this.b_receive = true;
		} else {
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "shared memory thread will send data");
			this.b_receive = false;
		}
		
		this.b_stop = false;
		this.l_lastCompleteRefresh = 0L;
		this.o_interval = null;
		
		/* set date interval object */
		if (p_o_interval != null) {
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "shared memory thread date interval object set: '" + p_o_interval + "'");
													
			this.o_interval = p_o_interval;
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("\t" + "set flag to indicate that this shared memory thread communicate with the original source site of data: '" + p_b_bidirectional + "'");
		
		/* set flag to indicate that this shared memory thread communicate with the original source site of data */
		this.b_bidirectional = p_b_bidirectional;
		
		/* bidirectional side must not do first cycle and send all null values - collision with original source is avoided in that way */
		if (this.b_bidirectional) {
			this.l_lastCompleteRefresh = System.currentTimeMillis();
		}
		
		/* set flag so that all fields within shared memory will be transported in one cycle */
		this.b_marshallingWholeObject = p_b_marshallingWholeObject;
	}
	
	/**
	 * This method stops the shared memory thread.
	 */
	public void stop() {
		this.b_stop = true;
	}
	
	/**
	 * Core execution process method of shared memory thread. Sending or receiving data based on receive flag.
	 */
	@Override
	public void run() {
		try {
													net.forestany.forestj.lib.Global.ilogConfig("shared memory thread started");
			
			/* endless loop for our shared memory thread instance */
			while (!this.b_stop) {
				if (this.b_receive) {
					/* receiving data */
					this.receiverCycle();
				} else {
					/* sending data */ 
					this.senderCycle();
				}
				
				/* execute thread timeout */
				Thread.sleep(this.i_timeoutMilliseconds);
			}
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("shared memory thread stopped");
	}
	
	/**
	 * Sender execution process method.
	 * 
	 * @throws Exception						any exception which can happen during creating a new instance of instance to be watched, exception within enqueuing network data
	 * @throws IndexOutOfBoundsException		if the field number is out of range (index < 0 || index >= size())
	 * @throws IllegalAccessException			field is not accessible
	 * @throws NoSuchFieldException				field does not exist
	 * @throws ClassCastException				if the object is not null and is not assignable to the type T
	 * @throws RuntimeException					communication is not running, wrong communication type or object does not implement Serializable interface
	 * @throws IllegalArgumentException			invalid message box id parameter for enqueue method 
	 */
	private void senderCycle() throws Exception {
		if (this.l_lastCompleteRefresh == 0L) { /* end of sender interval or first send of shared memory thread -> will send all fields */
													net.forestany.forestj.lib.Global.ilogFine("end of sender interval or first send of shared memory thread -> will send all fields");
			
			/* update timestamp */
			this.l_lastCompleteRefresh = System.currentTimeMillis();
			
			/* enqueue the whole object */
			if (this.b_marshallingWholeObject) {
														net.forestany.forestj.lib.Global.ilogFiner("enqueue whole object of '" + this.o_instanceToWatch.getClass().getTypeName() + "'");
				
				/* enqueue whole object */
				while (!this.o_communication.enqueue(
						this.o_instanceToWatch
				)) {
														net.forestany.forestj.lib.Global.ilogFiner("enqueue whole object was not successful, wait timeout value '" + this.i_timeoutMilliseconds + " ms'");
														
					/* if enqueue was not successful, wait timeout value */
					Thread.sleep(this.i_timeoutMilliseconds);							
				}
			} else {
				/* iterate all fields */
				for (int i = 0; i < this.o_instanceToWatch.getAmountFields(); i++) {
					/* get field name */
					String s_field = this.o_instanceToWatch.returnFieldName(i + 1);
					
															net.forestany.forestj.lib.Global.ilogFiner("enqueue field '" + s_field + "'");
					
					while (!this.o_communication.enqueue(
						this.o_instanceToWatch.returnFieldNumber(s_field), this.o_instanceToWatch.getField(s_field, true)
					)) {
																net.forestany.forestj.lib.Global.ilogFiner("enqueue was not successful, wait timeout value '" + this.i_timeoutMilliseconds + " ms'");
						
						/* if enqueue was not successful, wait timeout value */
						Thread.sleep(this.i_timeoutMilliseconds);
					}
				}
			}
			
													net.forestany.forestj.lib.Global.ilogFine("update (bidirectional) mirror object for instance object to watch");
			
			/* update (bidirectional) mirror object for instance object to watch */
			this.o_instanceToWatch.updateMirror(this.b_bidirectional);
		} else { /* usual sender cycle */
			/* get changed fields */
			java.util.List<String> a_changedFields = this.o_instanceToWatch.getChangedFields(true, this.b_bidirectional);
			
			/* check if we have any changes fields */
			if (a_changedFields.size() > 0) {
														net.forestany.forestj.lib.Global.ilogFine(a_changedFields.size() + " fields have been changed");
				
				/* enqueue the whole object if fields have changed */
				if (this.b_marshallingWholeObject) {
															net.forestany.forestj.lib.Global.ilogFiner("enqueue whole object of '" + this.o_instanceToWatch.getClass().getTypeName() + "'");
					
					/* enqueue whole object */
					while (!this.o_communication.enqueue(
						this.o_instanceToWatch
					)) {
																net.forestany.forestj.lib.Global.ilogFiner("enqueue whole object was not successful, wait timeout value '" + this.i_timeoutMilliseconds + " ms'");
														
						/* if enqueue was not successful, wait timeout value */
						Thread.sleep(this.i_timeoutMilliseconds);							
					}
				} else {
					/* iterate only changed fields */
					for (String s_field : a_changedFields) {
						
																net.forestany.forestj.lib.Global.ilogFiner("enqueue field '" + s_field + "'");
						
						/* enqueue field data, using field number as message box number */
						while (!this.o_communication.enqueue(
							this.o_instanceToWatch.returnFieldNumber(s_field), this.o_instanceToWatch.getField(s_field, true)
						)) {
																	net.forestany.forestj.lib.Global.ilogFiner("enqueue was not successful, wait timeout value '" + this.i_timeoutMilliseconds + " ms'");
						
							/* if enqueue was not successful, wait timeout value */
							Thread.sleep(this.i_timeoutMilliseconds);
						}
					}
				}
			} else {
														net.forestany.forestj.lib.Global.ilogFine("no fields have been changed");
			}
		}
		
		/* if there is an interval for sender and timestamp is not null */
		if ( (this.o_interval != null) && (this.l_lastCompleteRefresh != 0L) ) {
			/* if interval has expired */
			if (System.currentTimeMillis() > ( this.l_lastCompleteRefresh + this.o_interval.toDuration() ) ) {
				/* set timestamp to zero -> end of sender interval, next cycle will send all fields */
				this.l_lastCompleteRefresh = 0L;
			}
		}
	}
	
	/**
	 * Receiver execution process method.
	 * 
	 * @throws Exception						any exception which can happen during creating a new instance of instance to be watched, exception within dequeuing network data
	 * @throws IndexOutOfBoundsException		if the field number is out of range (index < 0 || index >= size())
	 * @throws IllegalAccessException			field is not accessible
	 * @throws NoSuchFieldException				field does not exist
	 * @throws ClassCastException				if the object is not null and is not assignable to the type T
	 * @throws RuntimeException					communication is not running or wrong communication type
	 * @throws IllegalArgumentException			invalid message box id parameter for enqueue method 
	 */
	private void receiverCycle() throws Exception {
		/* cycle flag */
		boolean b_cycleReceivedSomething = false;
		
		/* receive whole object */
		if (this.b_marshallingWholeObject) {
			/* dequeue shared memory object from communication message box */
			SharedMemory<?> o_receivedSharedMemory = (SharedMemory<?>)this.o_communication.dequeue();
			
			if (o_receivedSharedMemory != null) {
				/* update cycle flag */
				b_cycleReceivedSomething = true;
				
				if (this.b_bidirectional) {
					/* update bidirectional mirror field */
					this.o_instanceToWatch.updateWholeMirror(o_receivedSharedMemory, false);
				} else {
					/* update mirror field */
					this.o_instanceToWatch.updateWholeMirror(o_receivedSharedMemory, true);
				}
			}
		} else { /* check each message box for the corresponding field  */
			/* iterate all fields in each receiver each cycle */
			for (int i = 0; i < this.o_instanceToWatch.getAmountFields(); i++) {
				Object o_object = null;
				
													net.forestany.forestj.lib.Global.ilogFiner("dequeue object from communication message box '" + (i + 1) + "'");
				
				/* dequeue object from communication message box */
				o_object = this.o_communication.dequeue(i + 1);
				
				if (o_object != null) {
					/* update cycle flag */
					b_cycleReceivedSomething = true;
					
					/* get field name by message box number */
					String s_field = this.o_instanceToWatch.returnFieldName(i + 1);
					
															net.forestany.forestj.lib.Global.ilogFiner("set received field value for field '" + s_field + "'");
					
					/* set received field value */
					this.o_instanceToWatch.setField(s_field, o_object);
					
															net.forestany.forestj.lib.Global.ilogFiner("update" + ((this.b_bidirectional) ? " bidirectional " : " ") + "mirror field");
					
					if (this.b_bidirectional) {
						/* update bidirectional mirror field */
						this.o_instanceToWatch.updateMirrorField(s_field, false);
					} else {
						/* update mirror field */
						this.o_instanceToWatch.updateMirrorField(s_field, true);
					}
				}
			}
		}
		
		/* if nothing received in cycle over all message boxes, we do a timeout */
		if (!b_cycleReceivedSomething) {
										net.forestany.forestj.lib.Global.ilogFiner("nothing received in receiver cycle, wait timeout value '" + this.i_timeoutMilliseconds + " ms'");
		
			/* if nothing received in receiver cycle, wait timeout value */
			Thread.sleep(this.i_timeoutMilliseconds);
		}
	}
}
