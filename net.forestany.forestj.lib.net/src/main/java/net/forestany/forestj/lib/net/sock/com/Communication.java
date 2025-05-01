package net.forestany.forestj.lib.net.sock.com;

/**
 * Communication class for network data exchange. All configurable settings are adjusted by communication config class which is the only parameter in the constructor.
 * All internal functionality will be handled by an internal thread pool which will run all socket instances with it's socket tasks, or shared memory instances in combination with sockets.
 * Special features like handling shared memory objects and bidirectional shared memory network data exchange can be configured, so that all changes in fields of inherited class of shared memory object are transferred over the network to the other communication side and vice versa automatically.
 */
public class Communication {
	
	/* Constants */
	
	private final int i_dequeueWaitMilliseconds = 25;
	
	/* Fields */
	
	private Config o_config;
	private java.util.List<net.forestany.forestj.lib.net.msg.MessageBox> a_messageBoxes;
	private java.util.List<net.forestany.forestj.lib.net.sock.Socket<?>> a_sockets;
	private java.util.List<net.forestany.forestj.lib.net.msg.MessageBox> a_answerMessageBoxes;
	private java.util.concurrent.ExecutorService o_executorServicePool;
	private SharedMemoryThread o_sharedMemoryThread;
	private Communication o_communicationBidirectional;
	private boolean b_running;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * Constructor for communication class
	 * 
	 * @param p_o_config												all configurable settings are adjusted by communication config class which is the only parameter
	 * @throws IllegalStateException									settings in config lead to an invalid state and will not be accepted
	 * @throws IllegalArgumentException									settings in config have invalid values and will not be accepted
	 * @throws NullPointerException										settings in config have no value which are needed for initiating communication
	 * @throws java.security.NoSuchAlgorithmException 					using manual security - invalid key factory algorithm
	 * @throws java.security.spec.InvalidKeySpecException 				using manual security - invalid key specifications, length of common secret passphrase, salt, iteration value or key length option
	 * @throws java.net.UnknownHostException							issue local host name could not be resolved into an address
	 * @throws java.io.IOException										issue creating TCP or TCP with TLS socket instance
	 */
	@SuppressWarnings("unchecked")
	public Communication(Config p_o_config) throws IllegalStateException, IllegalArgumentException, NullPointerException, java.security.NoSuchAlgorithmException, java.security.spec.InvalidKeySpecException, java.net.UnknownHostException, java.io.IOException {
		/* set some default values */
		this.o_config = p_o_config;
		this.o_executorServicePool = null;
		this.o_sharedMemoryThread = null;
		this.o_communicationBidirectional = null;
		this.b_running = false;
		
		/* if shared memory object is set */
		if (this.o_config.getSharedMemory() != null) {
			/* check for valid cardinality */
			if ( (this.o_config.getCardinality() == Cardinality.OneMessageBoxToManySockets) || (this.o_config.getCardinality() == Cardinality.EqualBidirectional) ) {
				throw new IllegalStateException("Cannot use cardinality[" + this.o_config.getCardinality() + "] with shared memory");
			}
			
			/* check for valid communication type */
			if ( (this.o_config.getCommunicationType() == Type.TCP_RECEIVE_WITH_ANSWER) || (this.o_config.getCommunicationType() == Type.TCP_SEND_WITH_ANSWER) ) {
				throw new IllegalStateException("Cannot use communication type[" + Type.TCP_RECEIVE_WITH_ANSWER + "|" + Type.TCP_RECEIVE_WITH_ANSWER + "] with shared memory");
			}
		}
		
		/* check if bidirectional cardinality is used with tcp standard send or receive */
		if (this.o_config.getCardinality() == Cardinality.EqualBidirectional) {
			if ( (this.o_config.getCommunicationType() != Type.TCP_RECEIVE) && (this.o_config.getCommunicationType() != Type.TCP_SEND) ) {
				throw new IllegalStateException("Cannot use cardinality[" + Cardinality.EqualBidirectional + "] only with communication type[" + Type.TCP_RECEIVE + "|" + Type.TCP_SEND + "], but not with [" + this.o_config.getCommunicationType() + "]");
			}
		}
		
		/* check for valid cardinality in combination with amount of message boxes and amount of sockets */
		if ( (this.o_config.getCardinality() == Cardinality.Equal) && (this.o_config.getAmountMessageBoxes() != this.o_config.getAmountSockets()) ) {
			throw new IllegalStateException("With cardinality[Equal] amount of message boxes[" + this.o_config.getAmountMessageBoxes() + "] must equal amount of sockets[" + this.o_config.getAmountSockets() + "]");
		} else if ( (this.o_config.getCardinality() == Cardinality.EqualBidirectional) && (this.o_config.getAmountMessageBoxes() != (this.o_config.getAmountSockets() * 2)) ) {
			throw new IllegalStateException("With cardinality[Equal-Bidirectional] amount of message boxes[" + this.o_config.getAmountMessageBoxes() + "] must be twice as much as amount of sockets[" + this.o_config.getAmountSockets() + "]");
		} else if ( (this.o_config.getCardinality() == Cardinality.OneMessageBoxToManySockets) && ( (this.o_config.getAmountMessageBoxes() != 1) || (this.o_config.getAmountMessageBoxes() >= this.o_config.getAmountSockets()) ) ) {
			throw new IllegalStateException("With cardinality[One-MessageBox-To-Many-Sockets] amount of message boxes[" + this.o_config.getAmountMessageBoxes() + "] must be [1] and lower than amount of sockets[" + this.o_config.getAmountSockets() + "]");
		} else if ( (this.o_config.getCardinality() == Cardinality.ManyMessageBoxesToOneSocket) && ( (this.o_config.getAmountSockets() != 1) || (this.o_config.getAmountMessageBoxes() <= this.o_config.getAmountSockets()) ) ) {
			throw new IllegalStateException("With cardinality[Many-MessageBoxes-To-One-Socket] amount of message boxes[" + this.o_config.getAmountMessageBoxes() + "] must greater than amount of sockets[" + this.o_config.getAmountSockets() + "] and amount of sockets must be [1]");
		}
		
		/* check amount message boxes value */
		if (this.o_config.getAmountMessageBoxes() < 1) {
			throw new IllegalArgumentException("Amount of message boxes must be at least '1', but was set to '" + this.o_config.getAmountMessageBoxes() + "'");
		}
		
		/* check amount sockets value */
		if (this.o_config.getAmountSockets() < 1) {
			throw new IllegalArgumentException("Amount of sockets must be at least '1', but was set to '" + this.o_config.getAmountSockets() + "'");
		}
		
		/* check amount of message box length values and amount of message boxes, must match */
		if (this.o_config.getMessageBoxLengths().size() != this.o_config.getAmountMessageBoxes()) {
			throw new IllegalArgumentException("Amount of message box lengths[" + this.o_config.getMessageBoxLengths().size()  + "] is not equal amount of message boxes[" + this.o_config.getAmountMessageBoxes() + "]");
		}
		
		/* check amount of host addresses and amount of sockets, must match */
		if (this.o_config.getHosts().size() != this.o_config.getAmountSockets()) {
			throw new IllegalArgumentException("Amount of hosts and ports[" + this.o_config.getHosts().size()  + "] is not equal amount of sockets[" + this.o_config.getAmountSockets() + "]");
		}
		
		/* check amount of ports and amount of sockets, must match */
		if (this.o_config.getPorts().size() != this.o_config.getAmountSockets()) {
			throw new IllegalArgumentException("Amount of hosts and ports[" + this.o_config.getPorts().size()  + "] is not equal amount of sockets[" + this.o_config.getAmountSockets() + "]");
		}
		
		/* if socket tasks have been determined */
		if (this.o_config.getSocketTasks() != null) {
			/* check amount of sockets tasks and amount of sockets, must match */
			if (this.o_config.getSocketTasks().size() != this.o_config.getAmountSockets()) {
				throw new IllegalArgumentException("Amount of socket tasks[" + this.o_config.getSocketTasks().size()  + "] is not equal amount of sockets[" + this.o_config.getAmountSockets() + "]");
			}
			
			/* if interface delegates have been determined */
			if (this.o_config.getDelegates() != null) {
				/* check amount of sockets tasks and amount of interface delegates, must match */
				if (this.o_config.getSocketTasks().size() != this.o_config.getDelegates().size()) {
					throw new IllegalArgumentException("Amount of socket tasks[" + this.o_config.getSocketTasks().size()  + "] is not equal amount of interface delegates[" + this.o_config.getDelegates().size() + "]");
				}
			}
		}
		
		/* using asymmetric security, so ssl/tls settings are needed here */
		if (this.o_config.getCommunicationSecurity() == Security.ASYMMETRIC) {
			if ( /* receiving side */
				(this.o_config.getCommunicationType() == Type.TCP_RECEIVE) || 
				(this.o_config.getCommunicationType() == Type.TCP_RECEIVE_WITH_ANSWER) ||
				(this.o_config.isSharedMemoryBidirectionalConfigSet())
			) {
				/* check if we have a list of own instantiated ssl context objects */
				if (this.o_config.getSSLContextList() == null) {
					throw new NullPointerException("No ssl context list initialized");
				}
				
				/* check amount of ssl context objects and amount of sockets, must match */
				if (this.o_config.getSSLContextList().size() != this.o_config.getAmountSockets()) {
					throw new IllegalArgumentException("Amount of ssl context list[" + this.o_config.getSSLContextList().size()  + "] is not equal amount of sockets[" + this.o_config.getAmountSockets() + "]");
				}
			} else if ( /* sending side */
				(this.o_config.getCommunicationType() == Type.TCP_SEND) || 
				(this.o_config.getCommunicationType() == Type.TCP_SEND_WITH_ANSWER) ||
				(this.o_config.isSharedMemoryBidirectionalConfigSet())
			) {
				/* check for truststore filepath and truststore password */
				if ( (net.forestany.forestj.lib.Helper.isStringEmpty(System.getProperty("javax.net.ssl.trustStore"))) || (net.forestany.forestj.lib.Helper.isStringEmpty(System.getProperty("javax.net.ssl.trustStorePassword"))) ) {
					throw new NullPointerException("Please specify jvm system properties[javax.net.ssl.trustStore] and [javax.net.ssl.trustStorePassword] for communication security[" + this.o_config.getCommunicationSecurity() + "]");
				}
			}
		}
		
		/* create instances of message boxes list and sockets list */
		this.a_messageBoxes = new java.util.ArrayList<net.forestany.forestj.lib.net.msg.MessageBox>();
		this.a_sockets = new java.util.ArrayList<net.forestany.forestj.lib.net.sock.Socket<?>>();
		this.a_answerMessageBoxes = new java.util.ArrayList<net.forestany.forestj.lib.net.msg.MessageBox>();
		
		if ( /* receiving side */
			this.o_config.getCommunicationType() == Type.UDP_RECEIVE || 
			this.o_config.getCommunicationType() == Type.UDP_RECEIVE_WITH_ACK || 
			this.o_config.getCommunicationType() == Type.TCP_RECEIVE || 
			this.o_config.getCommunicationType() == Type.TCP_RECEIVE_WITH_ANSWER ||
			this.o_config.getCommunicationType() == Type.UDP_MULTICAST_RECEIVER
		) {
			/* check if socket receive type is not null */
			if (this.o_config.getSocketReceiveType() == null) {
				throw new NullPointerException("Socket receive type[null], must be set for communication type[" + this.o_config.getCommunicationType() + "]");
			}
			
			/* log internal warning if receiver timeout is lower than 1 second */
			if ( (this.o_config.getReceiverTimeoutMilliseconds() < 1000) && (this.o_config.getCardinality() != Cardinality.EqualBidirectional) ) {
				net.forestany.forestj.lib.Global.ilogWarning("Receiver timeout milliseconds[" + this.o_config.getReceiverTimeoutMilliseconds() + "] for receiving socket is lower equal '1000 milliseconds'");
			}
			
			/* log internal warning if queue timeout is greater than 100 millisecond */
			if (this.o_config.getQueueTimeoutMilliseconds() > 100) {
				net.forestany.forestj.lib.Global.ilogWarning("Queue timeout milliseconds[" + this.o_config.getQueueTimeoutMilliseconds() + "] for receiving socket is greater equal '100 milliseconds'");
			}
			
			if (this.o_config.getCommunicationType() == Type.UDP_RECEIVE_WITH_ACK) {
				/* if socket is receiving UDP data with acknowledge message, log internal warning if UDP send acknowledge timeout is greater than 1 second */
				if (this.o_config.getUDPSendAckTimeoutMilliseconds() > 1000) {
					net.forestany.forestj.lib.Global.ilogWarning("UDP send ACK timeout milliseconds[" + this.o_config.getUDPSendAckTimeoutMilliseconds() + "] for receiving is greater equal '1000 milliseconds'");
				}
			}
		} else if ( /* sending side */
			this.o_config.getCommunicationType() == Type.UDP_SEND || 
			this.o_config.getCommunicationType() == Type.UDP_SEND_WITH_ACK || 
			this.o_config.getCommunicationType() == Type.TCP_SEND || 
			this.o_config.getCommunicationType() == Type.TCP_SEND_WITH_ANSWER || 
			this.o_config.getCommunicationType() == Type.UDP_MULTICAST_SENDER
		) {
			if (
				this.o_config.getCommunicationType() == Type.TCP_SEND || 
				this.o_config.getCommunicationType() == Type.TCP_SEND_WITH_ANSWER
			) {
				/* if socket is sending TCP (with direct answer), log internal warning if sender timeout is lower than 1 second */
				if ( (this.o_config.getSenderTimeoutMilliseconds() < 1000) && (this.o_config.getCardinality() != Cardinality.EqualBidirectional) ) {
					net.forestany.forestj.lib.Global.ilogWarning("Sender timeout milliseconds[" + this.o_config.getSenderTimeoutMilliseconds() + "] for sending socket is lower equal '1000 milliseconds'");
				}
			}
			
			/* log internal warning if queue timeout is greater than 100 millisecond */
			if (this.o_config.getQueueTimeoutMilliseconds() > 100) {
				net.forestany.forestj.lib.Global.ilogWarning("Queue timeout milliseconds[" + this.o_config.getQueueTimeoutMilliseconds() + "] for sending socket is greater equal '100 milliseconds'");
			}
			
			if (this.o_config.getCommunicationType() == Type.UDP_SEND_WITH_ACK) {
				/* if socket is sending UDP data with acknowledge message, log internal warning if UDP receive acknowledge timeout is greater than 1 second */
				if (this.o_config.getUDPReceiveAckTimeoutMilliseconds() > 1000) {
					net.forestany.forestj.lib.Global.ilogWarning("UDP receive ACK timeout milliseconds[" + this.o_config.getUDPReceiveAckTimeoutMilliseconds() + "] for sending is greater equal '1000 milliseconds'");
				}
			}
		}
		
		int i_decreaseForEncryption = 0;
		
		/* check for length correction if manual encryption is active */
		if ( (this.o_config.getCommunicationSecurity() == Security.SYMMETRIC_128_BIT_HIGH) || (this.o_config.getCommunicationSecurity() == Security.SYMMETRIC_256_BIT_HIGH) ) {
			i_decreaseForEncryption = 28;
		} else if ( (this.o_config.getCommunicationSecurity() == Security.SYMMETRIC_128_BIT_LOW) || (this.o_config.getCommunicationSecurity() == Security.SYMMETRIC_256_BIT_LOW) ) {
			/* log internal warning if using low communication security with WAN or DMZ or network segments not fully under control */
			net.forestany.forestj.lib.Global.ilogWarning("Vulnerable communication warning! Do not use [" + Security.SYMMETRIC_128_BIT_LOW + ", " + Security.SYMMETRIC_256_BIT_LOW + "] communication security with WAN or DMZ applications or any other network segments where you do not have complete control!");
			i_decreaseForEncryption = 16;
		}
		
		/* create message box instances */
		for (int i = 0; i < this.o_config.getAmountMessageBoxes(); i++) {
			this.a_messageBoxes.add( new net.forestany.forestj.lib.net.msg.MessageBox(i + 1, (this.o_config.getMessageBoxLengths().get(i)) - i_decreaseForEncryption) );
			this.a_answerMessageBoxes.add( new net.forestany.forestj.lib.net.msg.MessageBox(i + 1, (this.o_config.getMessageBoxLengths().get(i)) - i_decreaseForEncryption) );
		}
		
		/* iterate for each planned socket instance */
		for (int i = 0; i < this.o_config.getAmountSockets(); i++) {
			/* create variable for socket task */ 
			net.forestany.forestj.lib.net.sock.task.Task<?> o_socketTask = null;
					
			if (this.o_config.getSocketTasks() != null) { /* socket tasks are determined in the config */
				if ( /* UDP communication type */
					this.o_config.getCommunicationType() == Type.UDP_SEND ||
					this.o_config.getCommunicationType() == Type.UDP_RECEIVE ||
					this.o_config.getCommunicationType() == Type.UDP_SEND_WITH_ACK ||
					this.o_config.getCommunicationType() == Type.UDP_RECEIVE_WITH_ACK || 
					this.o_config.getCommunicationType() == Type.UDP_MULTICAST_RECEIVER || 
					this.o_config.getCommunicationType() == Type.UDP_MULTICAST_SENDER
				) {
					/* check that all socket tasks are of type UDP */
					if (this.o_config.getSocketTasks().get(i).getType() != net.forestany.forestj.lib.net.sock.Type.UDP) {
						throw new IllegalStateException("Socket type[" + this.o_config.getSocketTasks().get(i).getType() + "] of socket task #" + (i + 1) + " must be [UDP] for communication type[" + this.o_config.getCommunicationType() + "]");
					}
				} else if ( /* TCP communication type */
					this.o_config.getCommunicationType() == Type.TCP_SEND ||
					this.o_config.getCommunicationType() == Type.TCP_RECEIVE ||
					this.o_config.getCommunicationType() == Type.TCP_SEND_WITH_ANSWER ||
					this.o_config.getCommunicationType() == Type.TCP_RECEIVE_WITH_ANSWER
				) {
					/* check that all socket tasks are of type TCP */
					if (this.o_config.getSocketTasks().get(i).getType() != net.forestany.forestj.lib.net.sock.Type.TCP) {
						throw new IllegalStateException("Socket type[" + this.o_config.getSocketTasks().get(i).getType() + "] of socket task #" + (i + 1) + " must be [TCP] for communication type[" + this.o_config.getCommunicationType() + "]");
					}
				}
				
				/* set socket task variable */
				o_socketTask = this.o_config.getSocketTasks().get(i);
				
				/* if interface delegates have been determined and communication type is TCP only */
				if (
					(this.o_config.getDelegates() != null)
					&&
					(
						this.o_config.getCommunicationType() == Type.TCP_SEND ||
						this.o_config.getCommunicationType() == Type.TCP_RECEIVE ||
						this.o_config.getCommunicationType() == Type.TCP_SEND_WITH_ANSWER ||
						this.o_config.getCommunicationType() == Type.TCP_RECEIVE_WITH_ANSWER
					)
				) {
					/* set interface delegate for socket task from determined list */
					o_socketTask.setDelegate(this.o_config.getDelegates().get(i));
				}
			} else {
				if ( /* receiving side */
					this.o_config.getCommunicationType() == Type.UDP_RECEIVE || 
					this.o_config.getCommunicationType() == Type.UDP_RECEIVE_WITH_ACK || 
					this.o_config.getCommunicationType() == Type.TCP_RECEIVE || 
					this.o_config.getCommunicationType() == Type.TCP_RECEIVE_WITH_ANSWER || 
					this.o_config.getCommunicationType() == Type.UDP_MULTICAST_RECEIVER
				) {
					if (this.o_config.getCommunicationSecurity() == Security.ASYMMETRIC) {
																net.forestany.forestj.lib.Global.ilogConfig("create receiving socket task instance with ssl/tls server socket and asymmetric security");
																
						/* create receiving socket task instance with ssl/tls server socket and asymmetric security */
						o_socketTask = new net.forestany.forestj.lib.net.sock.task.recv.Receive<javax.net.ssl.SSLServerSocket>(this.o_config.getCommunicationType(), this.o_config.getCardinality(), this.o_config.getQueueTimeoutMilliseconds(), this.o_config.getCommunicationSecurity());
					} else {
																net.forestany.forestj.lib.Global.ilogConfig("create receiving socket task instance with server socket");
																
						/* create receiving socket task instance with server socket */
						o_socketTask = new net.forestany.forestj.lib.net.sock.task.recv.Receive<java.net.ServerSocket>(this.o_config.getCommunicationType(), this.o_config.getCardinality(), this.o_config.getQueueTimeoutMilliseconds(), this.o_config.getCommunicationSecurity());
					}
					
															net.forestany.forestj.lib.Global.ilogConfig("\t" + "set UDP send ACK timeout value:" + "\t\t\t" + this.o_config.getUDPSendAckTimeoutMilliseconds());
					
					/* set UDP send ACK timeout value */
					o_socketTask.setUDPSendACKTimeoutMilliseconds(this.o_config.getUDPSendAckTimeoutMilliseconds());
					
					/* check if we receive TCP requests and answer directly */
					if (this.o_config.getCommunicationType() == Type.TCP_RECEIVE_WITH_ANSWER) {
						/* check if receive socket tasks are available */
						if ( (this.o_config.getReceiveSocketTasks() == null) || (this.o_config.getReceiveSocketTasks().size() == 0) ) {
							throw new NullPointerException("No receive socket task(s) specified for communication type[" + this.o_config.getCommunicationType()  + "]");
						} else {
							/* check if enough receive socket tasks are available */
							if (i > (this.o_config.getReceiveSocketTasks().size() - 1)) {
								throw new NullPointerException("Could not load receive socket task for socket #" + i);
							} else {
								/* set receive socket task */
								o_socketTask.setReceiveSocketTask( net.forestany.forestj.lib.net.sock.task.Task.class.cast(this.o_config.getReceiveSocketTasks().get(i)) );
							}
						}
					}
				} else if ( /* sending side */
					this.o_config.getCommunicationType() == Type.UDP_SEND || 
					this.o_config.getCommunicationType() == Type.UDP_SEND_WITH_ACK || 
					this.o_config.getCommunicationType() == Type.TCP_SEND || 
					this.o_config.getCommunicationType() == Type.TCP_SEND_WITH_ANSWER || 
					this.o_config.getCommunicationType() == Type.UDP_MULTICAST_SENDER
				) {
					if (this.o_config.getCommunicationSecurity() == Security.ASYMMETRIC) {
																net.forestany.forestj.lib.Global.ilogConfig("create sending socket task instance with ssl/tls socket and asymmetric security");
						
						/* create sending socket task instance with ssl/tls socket and asymmetric security */
						o_socketTask = new net.forestany.forestj.lib.net.sock.task.send.Send<javax.net.ssl.SSLSocket>(this.o_config.getCommunicationType(), this.o_config.getCardinality(), this.o_config.getQueueTimeoutMilliseconds(), this.o_config.getCommunicationSecurity());
					} else {
																net.forestany.forestj.lib.Global.ilogConfig("create sending socket task instance with socket");
																
						/* create sending socket task instance with socket */
						o_socketTask = new net.forestany.forestj.lib.net.sock.task.send.Send<java.net.Socket>(this.o_config.getCommunicationType(), this.o_config.getCardinality(), this.o_config.getQueueTimeoutMilliseconds(), this.o_config.getCommunicationSecurity());
					}
					
															net.forestany.forestj.lib.Global.ilogConfig("\t" + "set UDP receive ACK timeout value:" + "\t\t\t" + this.o_config.getUDPReceiveAckTimeoutMilliseconds());
															
					/* set UDP receive ACK timeout value */
					o_socketTask.setUDPReceiveACKTimeoutMilliseconds(this.o_config.getUDPReceiveAckTimeoutMilliseconds());
				}
				
				/* if interface delegates have been determined and communication type is TCP only */
				if (
					(this.o_config.getDelegates() != null)
					&&
					(this.o_config.getDelegates().size() == 1)
					&&
					(
						this.o_config.getCommunicationType() == Type.TCP_SEND ||
						this.o_config.getCommunicationType() == Type.TCP_RECEIVE ||
						this.o_config.getCommunicationType() == Type.TCP_SEND_WITH_ANSWER ||
						this.o_config.getCommunicationType() == Type.TCP_RECEIVE_WITH_ANSWER
					)
				) {
					/* set interface delegate for sending socket task from determined list - first element */
					o_socketTask.setDelegate(this.o_config.getDelegates().get(0));
				}
			}
			
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "set object transmission flag:" + "\t\t\t" + this.o_config.getObjectTransmission());
			
			/* set object transmission flag */
			o_socketTask.setObjectTransmission(this.o_config.getObjectTransmission());
			
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "set flag for debug network traffic:" + "\t\t\t" + this.o_config.getDebugNetworkTrafficOn());
			
			/* set flag for debug network traffic */
			o_socketTask.setDebugNetworkTrafficOn(this.o_config.getDebugNetworkTrafficOn());
			
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "set flag for using marshalling:" + "\t\t\t" + this.o_config.getUseMarshalling());
			
			/* set flag for using marshalling */
			o_socketTask.setUseMarshalling(this.o_config.getUseMarshalling());
			
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "set marshalling data length in bytes [1..4]:" + "\t\t\t" + this.o_config.getMarshallingDataLengthInBytes());
						
			/* set marshalling data length in bytes [1..4] */
			o_socketTask.setMarshallingDataLengthInBytes(this.o_config.getMarshallingDataLengthInBytes());
			
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "set marshalling use property methods flag:" + "\t\t\t" + this.o_config.getMarshallingUsePropertyMethods());
			
			/* set marshalling use property methods flag */
			o_socketTask.setMarshallingUsePropertyMethods(this.o_config.getMarshallingUsePropertyMethods());
			
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "set marshalling override messag etype:" + "\t\t\t" + this.o_config.getMarshallingOverrideMessageType());
			
			/* set marshalling override message type */
			o_socketTask.setMarshallingOverrideMessageType(this.o_config.getMarshallingOverrideMessageType());
			
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "set marshalling little endian flag:" + "\t\t\t" + this.o_config.getMarshallingSystemUsesLittleEndian());
			
			/* set marshalling little endian flag */
			o_socketTask.setMarshallingSystemUsesLittleEndian(this.o_config.getMarshallingSystemUsesLittleEndian());
			
			/* if we use manual security */
			if ( (this.o_config.getCommunicationSecurity() == Security.SYMMETRIC_128_BIT_HIGH) || (this.o_config.getCommunicationSecurity() == Security.SYMMETRIC_256_BIT_HIGH) || (this.o_config.getCommunicationSecurity() == Security.SYMMETRIC_128_BIT_LOW) || (this.o_config.getCommunicationSecurity() == Security.SYMMETRIC_256_BIT_LOW) ) {
				/* check if common secret passphrase is set */
				if (net.forestany.forestj.lib.Helper.isStringEmpty(this.o_config.getCommonSecretPassphrase())) {
					throw new NullPointerException("Common secret passphrase is not specified for communication security[" + this.o_config.getCommunicationSecurity() + "]");
				}
				
				/* check common secret passphrase min. length */
				if (this.o_config.getCommonSecretPassphrase().length() < 36) {
					throw new IllegalArgumentException("Common secret passphrase must have at least '36' characters, but has '" + this.o_config.getCommonSecretPassphrase().length() + "' characters");
				}
				
														net.forestany.forestj.lib.Global.ilogConfig("\t" + "set common secret passphrase for manual manual security: " + this.o_config.getCommunicationSecurity());
				
				/* set common secret passphrase */
				o_socketTask.setCommonSecretPassphrase(this.o_config.getCommonSecretPassphrase());
			}
			
			/* temp lists to pass instantiated message boxes to receiving/sending socket task */
			java.util.List<net.forestany.forestj.lib.net.msg.MessageBox> a_tempMessageBoxes = new java.util.ArrayList<net.forestany.forestj.lib.net.msg.MessageBox>();
			java.util.List<net.forestany.forestj.lib.net.msg.MessageBox> a_tempAnswerMessageBoxes = new java.util.ArrayList<net.forestany.forestj.lib.net.msg.MessageBox>();
			int i_bufferSize = 1;
			
			/* add message box and answer message box instance to socket task based on communication cardinality + get buffer size from message box instance */
			if (this.o_config.getCardinality() == Cardinality.Equal) { /* i -> i */
				i_bufferSize = this.a_messageBoxes.get(i).getMessageLength();
				
				a_tempMessageBoxes.add(this.a_messageBoxes.get(i));
				o_socketTask.setMessageBoxes(a_tempMessageBoxes);
				
				a_tempAnswerMessageBoxes.add(this.a_answerMessageBoxes.get(i));
				o_socketTask.setAnswerMessageBoxes(a_tempAnswerMessageBoxes);
			} else if (this.o_config.getCardinality() == Cardinality.EqualBidirectional) { /* each socket and each socket task should have two message boxes, one for sending and one for receiving */
				i_bufferSize = this.a_messageBoxes.get(0).getMessageLength();
				
				/* check that each message box has the same buffer size for receiving/sending socket task */
				for (net.forestany.forestj.lib.net.msg.MessageBox o_messageBox : this.a_messageBoxes) {
					if (o_messageBox.getMessageLength() != i_bufferSize) {
						throw new IllegalArgumentException("With cardinality[" + this.o_config.getCardinality() + "] all message boxes must have the same message length[" + i_bufferSize + "], but found message length[" + o_messageBox.getMessageLength() + "]");
					}
				}
				
				/* add one message box for sending and one for receiving from the overall message box list from config */
				a_tempMessageBoxes.add(this.a_messageBoxes.get(i * 2));
				a_tempMessageBoxes.add(this.a_messageBoxes.get((i * 2) + 1));
				o_socketTask.setMessageBoxes(a_tempMessageBoxes);
				
				o_socketTask.setAnswerMessageBoxes(this.a_answerMessageBoxes);
			} else if (this.o_config.getCardinality() == Cardinality.OneMessageBoxToManySockets) { /* first instantiated message box in list */
				i_bufferSize = this.a_messageBoxes.get(0).getMessageLength();
				
				a_tempMessageBoxes.add(this.a_messageBoxes.get(0));
				o_socketTask.setMessageBoxes(a_tempMessageBoxes);
				
				a_tempAnswerMessageBoxes.add(this.a_answerMessageBoxes.get(0));
				o_socketTask.setAnswerMessageBoxes(a_tempAnswerMessageBoxes);
			} else if (this.o_config.getCardinality() == Cardinality.ManyMessageBoxesToOneSocket) { /* complete message box list */
				i_bufferSize = this.a_messageBoxes.get(0).getMessageLength();
				
				/* check that each message box has the same buffer size for receiving/sending socket task */
				for (net.forestany.forestj.lib.net.msg.MessageBox o_messageBox : this.a_messageBoxes) {
					if (o_messageBox.getMessageLength() != i_bufferSize) {
						throw new IllegalArgumentException("With cardinality[" + this.o_config.getCardinality() + "] all message boxes must have the same message length[" + i_bufferSize + "], but found message length[" + o_messageBox.getMessageLength() + "]");
					}
				}
				
				o_socketTask.setMessageBoxes(this.a_messageBoxes);
				o_socketTask.setAnswerMessageBoxes(this.a_answerMessageBoxes);
			} else {
				throw new IllegalStateException("Unknown communication cardinality within communication constructor: " + this.o_config.getCardinality());
			}
			
													net.forestany.forestj.lib.Global.ilogConfig("read buffer size '" + i_bufferSize + "' from message box instance for socket creation");
			
			/* remember length correction if manual encryption is active */
			if ( (this.o_config.getCommunicationSecurity() == Security.SYMMETRIC_128_BIT_HIGH) || (this.o_config.getCommunicationSecurity() == Security.SYMMETRIC_256_BIT_HIGH) || (this.o_config.getCommunicationSecurity() == Security.SYMMETRIC_128_BIT_LOW) || (this.o_config.getCommunicationSecurity() == Security.SYMMETRIC_256_BIT_LOW) ) {
				i_bufferSize += i_decreaseForEncryption;
			}
			
			if ( (this.o_config.getCommunicationType() == Type.UDP_RECEIVE) || (this.o_config.getCommunicationType() == Type.UDP_RECEIVE_WITH_ACK) ) {
														net.forestany.forestj.lib.Global.ilogConfig("create UDP receive socket with all adjusted settings incl. socket task");
				
				/* create UDP receive socket with all adjusted settings incl. socket task */
				net.forestany.forestj.lib.net.sock.recv.ReceiveUDP<java.net.DatagramSocket> o_socket = new net.forestany.forestj.lib.net.sock.recv.ReceiveUDP<java.net.DatagramSocket>(
					java.net.DatagramSocket.class,
					this.o_config.getSocketReceiveType(),
					this.o_config.getHosts().get(i),
					this.o_config.getPorts().get(i),
					net.forestany.forestj.lib.net.sock.task.Task.class.cast(o_socketTask),
					this.o_config.getReceiverTimeoutMilliseconds(),
					this.o_config.getMaxTerminations(),
					i_bufferSize
				);
				
														net.forestany.forestj.lib.Global.ilogConfig("\t" + "set fixed amount of threads for thread pool instance in socket object: '" + this.o_config.getSocketExecutorServicePoolAmount() + "'");
				
				/* set fixed amount of threads for thread pool instance in socket object */
				o_socket.setExecutorServicePoolAmount(this.o_config.getSocketExecutorServicePoolAmount());
				
														net.forestany.forestj.lib.Global.ilogConfig("add socket to list");
				
				/* add socket to list */
				this.a_sockets.add(o_socket);
			} else if ( (this.o_config.getCommunicationType() == Type.UDP_SEND) || (this.o_config.getCommunicationType() == Type.UDP_SEND_WITH_ACK) ) {
														net.forestany.forestj.lib.Global.ilogConfig("create UDP send socket with all adjusted settings incl. socket task");
				
				/* create UDP send socket with all adjusted settings incl. socket task */
				net.forestany.forestj.lib.net.sock.send.SendUDP<java.net.DatagramSocket> o_socket = new net.forestany.forestj.lib.net.sock.send.SendUDP<java.net.DatagramSocket>(
					java.net.DatagramSocket.class,
					this.o_config.getHosts().get(i),
					this.o_config.getPorts().get(i),
					net.forestany.forestj.lib.net.sock.task.Task.class.cast(o_socketTask),
					this.o_config.getSenderIntervalMilliseconds(),
					this.o_config.getMaxTerminations(),
					i_bufferSize,
					this.o_config.getLocalAddress(),
					this.o_config.getLocalPort()
				);
				
														net.forestany.forestj.lib.Global.ilogConfig("add socket to list");
				
				/* add socket to list */
				this.a_sockets.add(o_socket);
			} else if (this.o_config.getCommunicationType() == Type.UDP_MULTICAST_RECEIVER) {
														net.forestany.forestj.lib.Global.ilogConfig("create UDP multicast receive socket with all adjusted settings incl. socket task");
				
				/* create UDP receive socket with all adjusted settings incl. socket task */
				net.forestany.forestj.lib.net.sock.recv.ReceiveUDP<java.net.MulticastSocket> o_socket = new net.forestany.forestj.lib.net.sock.recv.ReceiveUDP<java.net.MulticastSocket>(
					java.net.MulticastSocket.class,
					this.o_config.getSocketReceiveType(),
					this.o_config.getHosts().get(i),
					this.o_config.getPorts().get(i),
					net.forestany.forestj.lib.net.sock.task.Task.class.cast(o_socketTask),
					this.o_config.getReceiverTimeoutMilliseconds(),
					this.o_config.getMaxTerminations(),
					i_bufferSize
				);
				
														net.forestany.forestj.lib.Global.ilogConfig("\t" + "set multicast receiver network interface name in socket object: '" + this.o_config.getUDPMulticastReceiverNetworkInterfaceName() + "'");
				
				/* set multicast receiver network interface name */
				o_socket.setMulticastNetworkInterfaceName(this.o_config.getUDPMulticastReceiverNetworkInterfaceName());
				
												net.forestany.forestj.lib.Global.ilogConfig("\t" + "set fixed amount of threads for thread pool instance in socket object: '" + this.o_config.getSocketExecutorServicePoolAmount() + "'");
				
				/* set fixed amount of threads for thread pool instance in socket object */
				o_socket.setExecutorServicePoolAmount(this.o_config.getSocketExecutorServicePoolAmount());
				
												net.forestany.forestj.lib.Global.ilogConfig("add socket to list");
				
				/* add socket to list */
				this.a_sockets.add(o_socket);
			} else if (this.o_config.getCommunicationType() == Type.UDP_MULTICAST_SENDER) {
														net.forestany.forestj.lib.Global.ilogConfig("create UDP multicast send socket with all adjusted settings incl. socket task");
				
				/* create UDP send socket with all adjusted settings incl. socket task */
				net.forestany.forestj.lib.net.sock.send.SendUDP<java.net.MulticastSocket> o_socket = new net.forestany.forestj.lib.net.sock.send.SendUDP<java.net.MulticastSocket>(
					java.net.MulticastSocket.class,
					this.o_config.getHosts().get(i),
					this.o_config.getPorts().get(i),
					net.forestany.forestj.lib.net.sock.task.Task.class.cast(o_socketTask),
					this.o_config.getSenderIntervalMilliseconds(),
					this.o_config.getMaxTerminations(),
					i_bufferSize,
					this.o_config.getLocalAddress(),
					this.o_config.getLocalPort()
				);
				
														net.forestany.forestj.lib.Global.ilogConfig("\t" + "set multicast sender TTL in socket object: '" + this.o_config.getUDPMulticastSenderTTL() + "'");
				
				/* set multicast sender TTL */
				o_socket.setMulticastTTL(this.o_config.getUDPMulticastSenderTTL());
				
												net.forestany.forestj.lib.Global.ilogConfig("add socket to list");
				
				/* add socket to list */
				this.a_sockets.add(o_socket);
			} else if ( (this.o_config.getCommunicationType() == Type.TCP_RECEIVE) || (this.o_config.getCommunicationType() == Type.TCP_RECEIVE_WITH_ANSWER) ) {
				net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<?> o_socket = null;
				
				if (this.o_config.getCommunicationSecurity() == Security.ASYMMETRIC) {
															net.forestany.forestj.lib.Global.ilogConfig("create TCP receive socket with all adjusted settings incl. socket task and asymmetric security");
					
					/* create TCP receive socket with all adjusted settings incl. socket task and asymmetric security */
					o_socket = new net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<javax.net.ssl.SSLServerSocket>(
						javax.net.ssl.SSLServerSocket.class,
						this.o_config.getSocketReceiveType(),
						this.o_config.getHosts().get(i),
						this.o_config.getPorts().get(i),
						net.forestany.forestj.lib.net.sock.task.Task.class.cast(o_socketTask),
						this.o_config.getReceiverTimeoutMilliseconds(),
						this.o_config.getMaxTerminations(),
						i_bufferSize,
						this.o_config.getSSLContextList().get(i)
					);
				} else {
															net.forestany.forestj.lib.Global.ilogConfig("create TCP receive socket with all adjusted settings incl. socket task");
					
					/* create TCP receive socket with all adjusted settings incl. socket task */
					o_socket = new net.forestany.forestj.lib.net.sock.recv.ReceiveTCP<java.net.ServerSocket>(
						java.net.ServerSocket.class,
						this.o_config.getSocketReceiveType(),
						this.o_config.getHosts().get(i),
						this.o_config.getPorts().get(i),
						net.forestany.forestj.lib.net.sock.task.Task.class.cast(o_socketTask),
						this.o_config.getReceiverTimeoutMilliseconds(),
						this.o_config.getMaxTerminations(),
						i_bufferSize,
						null
					);
				}
				
														net.forestany.forestj.lib.Global.ilogConfig("\t" + "set fixed amount of threads for thread pool instance in socket object: '" + this.o_config.getSocketExecutorServicePoolAmount() + "'");
				
				/* set fixed amount of threads for thread pool instance in socket object */
				o_socket.setExecutorServicePoolAmount(this.o_config.getSocketExecutorServicePoolAmount());
				
														net.forestany.forestj.lib.Global.ilogConfig("add socket to list");
				
				/* add socket to list */
				this.a_sockets.add(o_socket);
			} else if ( (this.o_config.getCommunicationType() == Type.TCP_SEND) || (this.o_config.getCommunicationType() == Type.TCP_SEND_WITH_ANSWER) ) {
				net.forestany.forestj.lib.net.sock.send.SendTCP<?> o_socket = null;
				
				if (this.o_config.getCommunicationSecurity() == Security.ASYMMETRIC) {
					javax.net.ssl.SSLContext o_sslContext = null;
					
					if (this.o_config.getSSLContextList() != null) {
						if (i < this.o_config.getSSLContextList().size()) {
							o_sslContext = this.o_config.getSSLContextList().get(i);
						}
					}
					
															net.forestany.forestj.lib.Global.ilogConfig("create TCP send socket with all adjusted settings incl. socket task and asymmetric security");
					
					/* create TCP send socket with all adjusted settings incl. socket task and asymmetric security */
					o_socket = new net.forestany.forestj.lib.net.sock.send.SendTCP<javax.net.ssl.SSLSocket>(
						javax.net.ssl.SSLSocket.class,
						this.o_config.getHosts().get(i),
						this.o_config.getPorts().get(i),
						net.forestany.forestj.lib.net.sock.task.Task.class.cast(o_socketTask),
						this.o_config.getSenderTimeoutMilliseconds(),
						this.o_config.getCheckReachability(),
						this.o_config.getMaxTerminations(),
						this.o_config.getSenderIntervalMilliseconds(),
						i_bufferSize,
						this.o_config.getLocalAddress(),
						this.o_config.getLocalPort(),
						o_sslContext
					);
				} else {
															net.forestany.forestj.lib.Global.ilogConfig("create TCP send socket with all adjusted settings incl. socket task");
					
					/* create TCP send socket with all adjusted settings incl. socket task */
					o_socket = new net.forestany.forestj.lib.net.sock.send.SendTCP<java.net.Socket>(
						java.net.Socket.class,
						this.o_config.getHosts().get(i),
						this.o_config.getPorts().get(i),
						net.forestany.forestj.lib.net.sock.task.Task.class.cast(o_socketTask),
						this.o_config.getSenderTimeoutMilliseconds(),
						this.o_config.getCheckReachability(),
						this.o_config.getMaxTerminations(),
						this.o_config.getSenderIntervalMilliseconds(),
						i_bufferSize,
						this.o_config.getLocalAddress(),
						this.o_config.getLocalPort(),
						null
					);
				}
				
														net.forestany.forestj.lib.Global.ilogConfig("add socket to list");
				
				/* add socket to list */
				this.a_sockets.add(o_socket);
			}
		}
		
		/* if a configuration for shared memory bidirectional network communication exchange has been set */
		if (this.o_config.getSharedMemoryBidirectional() != null) {
													net.forestany.forestj.lib.Global.ilogConfig("create new communication instance for shared memory bidirectional network communication exchange");
			
			/* create new communication instance for shared memory bidirectional network communication exchange */
			this.o_communicationBidirectional = new Communication(this.o_config.getSharedMemoryBidirectional());
		}
	}
	
	/**
	 * Start network data exchange communication and give any socket object into thread pool + start optional shared memory thread with thread pool
	 * 
	 * @throws Exception							any exception which can happen during creating a new instance of instance to be watched
	 * @throws NullPointerException					communication object or instance to watch object parameter is null
	 * @throws IllegalArgumentException				invalid timeout value
	 * @throws NoSuchFieldException					a field does not exist
	 * @throws IllegalAccessException				cannot access field, must be public
	 * @throws RuntimeException						communication is already running or no sockets are configured
	 */
	public void start() throws Exception {
		net.forestany.forestj.lib.Global.ilog("Start communication, " + this.o_config.getCommunicationType());
		
		/* check if communication is already running */
		if (b_running) {
			throw new RuntimeException("Communication is running. Please stop communication first");
		}
		
		/* set running flag */
		this.b_running = true;
		
		if (this.o_config.getExecutorServicePoolAmount() > 0) {
													net.forestany.forestj.lib.Global.ilogFine("create thread pool instance and give it a fixed amount of threads: '" + this.o_config.getExecutorServicePoolAmount() + "'");
			
			/* create thread pool instance and give it a fixed amount of threads */
			this.o_executorServicePool = java.util.concurrent.Executors.newFixedThreadPool(this.o_config.getExecutorServicePoolAmount());
		} else {
													net.forestany.forestj.lib.Global.ilogFine("create thread pool instance");
			
			/* create thread pool instance */
			this.o_executorServicePool = java.util.concurrent.Executors.newCachedThreadPool();
		}
		
		/* check socket size */
		if (this.a_sockets.size() == 0) {
			throw new RuntimeException("no sockets are configured");
		}
		
		/* if shared memory object is set */
		if (this.o_config.getSharedMemory() != null) {
													net.forestany.forestj.lib.Global.ilogConfig("create shared memory thread");
			
			/* create shared memory thread */
			this.o_sharedMemoryThread = new SharedMemoryThread(
				this,
				this.o_config.getSharedMemory(),
				this.o_config.getSharedMemoryTimeoutMilliseconds(),
				this.o_config.getCommunicationType(),
				this.o_config.getSharedMemoryIntervalCompleteRefresh(),
				this.o_config.isSharedMemoryBidirectionalConfigSet(),
				this.o_config.getUseMarshallingWholeObject()
			);
			
													net.forestany.forestj.lib.Global.ilogFine("add shared memory thread to thread pool for execution");
			
			/* add shared memory thread to thread pool for execution */
			o_executorServicePool.execute(this.o_sharedMemoryThread);
		}
		
		for (int i = 0; i < this.a_sockets.size(); i++) {
													net.forestany.forestj.lib.Global.ilogFine("add socket object #" + (i + 1) + " to thread pool for execution");
													
			/* add socket object #i to thread pool for execution */
			o_executorServicePool.execute(this.a_sockets.get(i));
		}
		
		if (this.o_communicationBidirectional != null) {
													net.forestany.forestj.lib.Global.ilogFine("start bidirectional communication");
			
			/* start bidirectional communication */
			this.o_communicationBidirectional.start();
		}
	}
	
	/**
	 * Stop network data exchange communication
	 * 
	 * @throws RuntimeException				communication is not running
	 */
	public void stop() throws RuntimeException {
		net.forestany.forestj.lib.Global.ilog("Stop communication, " + this.o_config.getCommunicationType());
		
		/* check if communication is not running */
		if (!b_running) {
			throw new RuntimeException("Communication is not running. Please start communication first");
		}
		
		/* unset running flag */
		this.b_running = false;
		
		if (this.o_sharedMemoryThread != null) {
													net.forestany.forestj.lib.Global.ilogFine("stop shared memory thread");
			
			/* stop shared memory thread */
			this.o_sharedMemoryThread.stop();
		}
		
		/* iterate each socket instance */
		for (int i = 0; i < this.a_sockets.size(); i++) {
													net.forestany.forestj.lib.Global.ilogFine("stop socket instance #" + (i + 1));
			
			/* stop socket instance */
			this.a_sockets.get(i).stop();
		}
		
		if (this.o_executorServicePool != null) {
													net.forestany.forestj.lib.Global.ilogFine("shutdown communication thread pool, waiting 5 seconds");
			
			/* shutdown communication thread pool, waiting 5 seconds */
			this.o_executorServicePool.shutdown();
			
			try {
				this.o_executorServicePool.awaitTermination(5L, java.util.concurrent.TimeUnit.SECONDS);
			} catch (InterruptedException o_exc) {
				/* nothing to do */
			}
		}
		
		if (this.o_communicationBidirectional != null) {
													net.forestany.forestj.lib.Global.ilogFine("stop bidirectional communication");
			
			/* stop bidirectional communication */
			this.o_communicationBidirectional.stop();
		}
	}
	
	/**
	 * Enqueue object into first message box
	 * 
	 * @param p_o_object											object which will be enqueued
	 * @return														true - success, false - failure
	 * @throws RuntimeException										communication is not running, wrong communication type or object does not implement Serializable interface 
	 * @throws IllegalArgumentException								if marshalling true = data length in bytes must be between 1..4
	 * @throws NullPointerException									if marshalling true = parameter object is null
	 * @throws NoSuchFieldException									if marshalling true = could not retrieve field type by field name
	 * @throws NoSuchMethodException								if marshalling true = could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 			if marshalling true = could not invoke method from object
	 * @throws IllegalAccessException								if marshalling true = could not invoke method, access violation
	 
	 */
	public boolean enqueue(Object p_o_object) throws RuntimeException, IllegalArgumentException, NullPointerException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		return this.enqueue(1, p_o_object);
	}
	
	/**
	 * Enqueue object into message box with id parameter
	 * 
	 * @param p_i_messageBoxId										message box id parameter
	 * @param p_o_object											object which will be enqueued
	 * @return														true - success, false - failure
	 * @throws RuntimeException										communication is not running, wrong communication type or object does not implement Serializable interface 
	 * @throws IllegalArgumentException								invalid message box id parameter, or if marshalling true = data length in bytes must be between 1..4
	 * @throws NullPointerException									if marshalling true = parameter object is null
	 * @throws NoSuchFieldException									if marshalling true = could not retrieve field type by field name
	 * @throws NoSuchMethodException								if marshalling true = could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 			if marshalling true = could not invoke method from object
	 * @throws IllegalAccessException								if marshalling true = could not invoke method, access violation
	 */
	public boolean enqueue(int p_i_messageBoxId, Object p_o_object) throws RuntimeException, IllegalArgumentException, NullPointerException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		/* check if communication is not running */
		if (!b_running) {
			throw new RuntimeException("Communication is not running. Please start communication first");
		}
		
		/* if communication is running a TCP server with direct answer, we will never enqueue any network data */
		if (this.o_config.getCommunicationType() == Type.TCP_RECEIVE_WITH_ANSWER) {
			throw new RuntimeException("Cannot enqueue object if this side is running with communication type[" + this.o_config.getCommunicationType() + "].");
		}
		
		/* check if object implements java.io.Serializable class, if no marshalling is used */
		if ( (!(p_o_object instanceof java.io.Serializable)) && (!this.o_config.getUseMarshalling()) && (!this.o_config.getUseMarshallingWholeObject()) ) {
			throw new RuntimeException("Object[" + p_o_object.getClass().toString() + "(" + p_o_object.toString() + ")] does not implement Serializable interface");
		}
		
		/* check message box id min. value */
		if (p_i_messageBoxId < 1) {
			throw new IllegalArgumentException("Invalid message box. Message box id[" + p_i_messageBoxId + "] must be greater than [0].");
		}
		
		/* check message box id max. value */
		if (p_i_messageBoxId > this.a_messageBoxes.size()) {
			throw new IllegalArgumentException("Invalid message box. Message box id[" + p_i_messageBoxId + "] is not available.");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("execute enqueue object method on message box #" + p_i_messageBoxId);
		
		/* decrease message box id, because we give a parameter with value > 0 */
		p_i_messageBoxId--;
		
		if (this.o_config.getUseMarshalling()) {
			/* execute enqueue object method on message box with marshalling */
			return this.a_messageBoxes.get(p_i_messageBoxId).enqueueObjectWithMarshalling(p_o_object, this.o_config.getMarshallingDataLengthInBytes(), this.o_config.getMarshallingUsePropertyMethods(), this.o_config.getMarshallingOverrideMessageType(), this.o_config.getMarshallingSystemUsesLittleEndian());
		} else {
			/* execute enqueue object method on message box */
			return this.a_messageBoxes.get(p_i_messageBoxId).enqueueObject(p_o_object);
		}
	}
	
	/**
	 * Dequeue object from first message box
	 * 
	 * @return														object received from network data exchange communication or null
	 * @throws RuntimeException										communication is not running or wrong communication type 
	 * @throws IllegalArgumentException								if marshalling true = data length in bytes must be between 1..4
	 * @throws NullPointerException									if marshalling true = parameter object is null
	 * @throws NoSuchFieldException									if marshalling true = could not retrieve field type by field name
	 * @throws NoSuchMethodException								if marshalling true = could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 			if marshalling true = could not invoke method from object
	 * @throws IllegalAccessException								if marshalling true = could not invoke method, access violation
	 */
	public Object dequeue() throws RuntimeException, IllegalArgumentException, NullPointerException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		/* for tcp bidirectional communication we use second message box for receiving data */
		if (this.o_config.getCardinality() == Cardinality.EqualBidirectional) {
			return this.dequeue(2);
		} else {
			return this.dequeue(1);
		}
	}
	
	/**
	 * Dequeue object from message box with id parameter
	 * 
	 * @param p_i_messageBoxId										message box id parameter
	 * @return														object received from network data exchange communication or null
	 * @throws RuntimeException										communication is not running or wrong communication type 
	 * @throws IllegalArgumentException								invalid message box id parameter, or if marshalling true = data length in bytes must be between 1..4
	 * @throws NullPointerException									if marshalling true = parameter object is null
	 * @throws NoSuchFieldException									if marshalling true = could not retrieve field type by field name
	 * @throws NoSuchMethodException								if marshalling true = could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 			if marshalling true = could not invoke method from object
	 * @throws IllegalAccessException								if marshalling true = could not invoke method, access violation
	 */
	public Object dequeue(int p_i_messageBoxId) throws RuntimeException, IllegalArgumentException, NullPointerException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		/* check if communication is not running */
		if (!b_running) {
			throw new RuntimeException("Communication is not running. Please start communication first");
		}
		
		/* if communication is running a TCP server with direct answer, we will never dequeue any network data */
		if (this.o_config.getCommunicationType() == Type.TCP_RECEIVE_WITH_ANSWER) {
			throw new RuntimeException("Cannot dequeue object if this side is running with communication type[" + this.o_config.getCommunicationType() + "].");
		}
		
		/* check message box id min. value */
		if (p_i_messageBoxId < 1) {
			throw new IllegalArgumentException("Invalid message box. Message box id[" + p_i_messageBoxId + "] must be greater than [0].");
		}
		
		/* check message box id max. value */
		if (p_i_messageBoxId > this.a_messageBoxes.size()) {
			throw new IllegalArgumentException("Invalid message box. Message box id[" + p_i_messageBoxId + "] is not available.");
		}
		
		/* decrease message box id, because we give a parameter with value > 0 */
		p_i_messageBoxId--;
		
		if (this.o_config.getCommunicationType() == Type.TCP_SEND_WITH_ANSWER) {
			if (this.o_config.getUseMarshalling()) {
				/* if we expect direct answer from other communication side, dequeue from answer message box with marshalling */
				return this.a_answerMessageBoxes.get(p_i_messageBoxId).dequeueObjectWithMarshalling(this.o_config.getMarshallingUsePropertyMethods(), this.o_config.getMarshallingSystemUsesLittleEndian());
			} else {
				/* if we expect direct answer from other communication side, dequeue from answer message box */
				return this.a_answerMessageBoxes.get(p_i_messageBoxId).dequeueObject();
			}
		} else {
			if (this.o_config.getUseMarshalling()) {
				/* dequeue from message box with id parameter with marshalling */
				return this.a_messageBoxes.get(p_i_messageBoxId).dequeueObjectWithMarshalling(this.o_config.getMarshallingUsePropertyMethods(), this.o_config.getMarshallingSystemUsesLittleEndian());
			} else {
				/* dequeue from message box with id parameter */
				return this.a_messageBoxes.get(p_i_messageBoxId).dequeueObject();
			}
		}
	}

	/**
	 * Dequeue object from first message box, do several attempts with an overall timeout
	 * 
	 * @param p_i_timeoutMilliseconds								try this amount of time to receive data
	 * @return														object received from network data exchange communication or null
	 * @throws RuntimeException										communication is not running or wrong communication type 
	 * @throws InterruptedException									if any thread has interrupted the current thread
	 * @throws IllegalArgumentException								if marshalling true = data length in bytes must be between 1..4
	 * @throws NullPointerException									if marshalling true = parameter object is null
	 * @throws NoSuchFieldException									if marshalling true = could not retrieve field type by field name
	 * @throws NoSuchMethodException								if marshalling true = could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 			if marshalling true = could not invoke method from object
	 * @throws IllegalAccessException								if marshalling true = could not invoke method, access violation
	 */
	public Object dequeueWithWaitLoop(int p_i_timeoutMilliseconds) throws RuntimeException, InterruptedException, IllegalArgumentException, NullPointerException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		/* for tcp bidirectional communication we use second message box for receiving data */
		if (this.o_config.getCardinality() == Cardinality.EqualBidirectional) {
			return this.dequeueWithWaitLoop(p_i_timeoutMilliseconds, 2);
		} else {
			return this.dequeueWithWaitLoop(p_i_timeoutMilliseconds, 1);
		}
	}
	
	/**
	 * Dequeue object from message box with id parameter, do several attempts with an overall timeout
	 * 
	 * @param p_i_timeoutMilliseconds								try this amount of time to receive data
	 * @param p_i_messageBoxId										message box id parameter
	 * @return														object received from network data exchange communication or null
	 * @throws RuntimeException										communication is not running or wrong communication type 
	 * @throws InterruptedException									if any thread has interrupted the current thread
	 * @throws IllegalArgumentException								invalid message box id parameter, or if marshalling true = data length in bytes must be between 1..4
	 * @throws NullPointerException									if marshalling true = parameter object is null
	 * @throws NoSuchFieldException									if marshalling true = could not retrieve field type by field name
	 * @throws NoSuchMethodException								if marshalling true = could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 			if marshalling true = could not invoke method from object
	 * @throws IllegalAccessException								if marshalling true = could not invoke method, access violation
	 */
	public Object dequeueWithWaitLoop(int p_i_timeoutMilliseconds, int p_i_messageBoxId) throws RuntimeException, InterruptedException, IllegalArgumentException, NullPointerException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		Object o_return = null;
		
		int j = 0;
						
		while (o_return == null) {
			/* check if data has been received */
			o_return = this.dequeue(p_i_messageBoxId);
			
			if (o_return == null) {
				Thread.sleep(this.i_dequeueWaitMilliseconds);
				
				j++;
				
				/* try for amount of time parameter (ms) to receive data */
				if (j > (p_i_timeoutMilliseconds / this.i_dequeueWaitMilliseconds)) {
					/* time has elapsed */
					break;
				}
			} else {
				/* log information how many cycles of wait has been executed */
				net.forestany.forestj.lib.Global.ilogFiner("waited '" + j + "' cycles for receiving data with a wait ratio(ms) of: " + p_i_timeoutMilliseconds + " / " + this.i_dequeueWaitMilliseconds + " = " + ( p_i_timeoutMilliseconds / this.i_dequeueWaitMilliseconds ) );
			}
		}
		
		return o_return;
	}
}
