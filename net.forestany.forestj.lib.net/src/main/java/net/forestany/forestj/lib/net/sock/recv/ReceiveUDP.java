package net.forestany.forestj.lib.net.sock.recv;

/**
 * Generic socket class to receive network traffic over a socket instance. Several methods supporting receiving data over UDP as server or socket instance.
 *
 * @param <T>	java socket class parameter
 */
public class ReceiveUDP<T extends java.net.DatagramSocket> extends net.forestany.forestj.lib.net.sock.Socket<T> {
	
	/* Fields */
	
	private ReceiveType e_type;
	private int i_executorServicePoolAmount;
	private boolean b_stopServer;
	private java.net.NetworkInterface o_multicastNetworkInterface;
	private java.util.List<net.forestany.forestj.lib.net.sock.task.Token> a_tokenInstances = new java.util.ArrayList<net.forestany.forestj.lib.net.sock.task.Token>();
	
	/* Properties */
	
	/**
	 * get executor service pool amount
	 * 
	 * @return int
	 */
	public int getExecutorServicePoolAmount() {
		return this.i_executorServicePoolAmount;
	}
	
	/**
	 * set executor service pool amount
	 * 
	 * @param p_i_value int
	 */
	public void setExecutorServicePoolAmount(int p_i_value) {
		this.i_executorServicePoolAmount = p_i_value;
	}
	
	/**
	 * get multicast network interface name
	 * 
	 * @return java.net.NetworkInterface
	 */
	public java.net.NetworkInterface getMulticastNetworkInterfaceName() {
		return this.o_multicastNetworkInterface;
	}
	
	/**
	 * set multicast network interface name
	 * 
	 * @param p_s_value String
	 * @throws java.net.SocketException parameter is null or empty, invalid network interface name
	 */
	public void setMulticastNetworkInterfaceName(String p_s_value) throws java.net.SocketException {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			throw new IllegalArgumentException("Multicast network interface name must have a value, but is empty");
		} else {
			this.o_multicastNetworkInterface = java.net.NetworkInterface.getByName(p_s_value);
		}
	}
	
	/* Methods */
	
	/**
	 * Create receive socket instance for UDP network connection, executing within a thread. Receive buffer size = 1500 bytes. Infinite thread executions of socket instance. 60 seconds timeout as default.
	 * 
	 * @param p_o_classType										java class type of socket instance, e.g. java.net.ServerSocket.class
	 * @param p_e_type											receiving type of socket instance - SERVER or CLIENT
	 * @param p_i_port											port where receiving socket instance shall listen
	 * @param p_o_socketTask									socket task object with the core execution process of a socket
	 * @throws IllegalArgumentException							invalid parameter values for port, receive buffer size or timeout
	 * @throws NullPointerException								socket task parameter is null
	 * @throws java.net.UnknownHostException					issue local host name could not be resolved into an address
	 */
	public ReceiveUDP(Class<T> p_o_classType, ReceiveType p_e_type, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketTask) throws IllegalArgumentException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_e_type, java.net.InetAddress.getLocalHost().getHostAddress(), p_i_port, p_o_socketTask, 60000);
	}
	
	/**
	 * Create receive socket instance for UDP network connection, executing within a thread. Receive buffer size = 1500 bytes. Infinite thread executions of socket instance. 60 seconds timeout as default.
	 * 
	 * @param p_o_classType										java class type of socket instance, e.g. java.net.ServerSocket.class
	 * @param p_e_type											receiving type of socket instance - SERVER or CLIENT
	 * @param p_s_host											address of receiving socket instance
	 * @param p_i_port											port where receiving socket instance shall listen
	 * @param p_o_socketTask									socket task object with the core execution process of a socket
	 * @throws IllegalArgumentException							invalid parameter values for port, receive buffer size or timeout
	 * @throws NullPointerException								socket task parameter is null
	 * @throws java.net.UnknownHostException					issue local host name could not be resolved into an address
	 */
	public ReceiveUDP(Class<T> p_o_classType, ReceiveType p_e_type, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketTask) throws IllegalArgumentException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_e_type, p_s_host, p_i_port, p_o_socketTask, 60000);
	}
	
	/**
	 * Create receive socket instance for UDP network connection, executing within a thread. Receive buffer size = 1500 bytes. Infinite thread executions of socket instance.
	 * 
	 * @param p_o_classType										java class type of socket instance, e.g. java.net.ServerSocket.class
	 * @param p_e_type											receiving type of socket instance - SERVER or CLIENT
	 * @param p_i_port											port where receiving socket instance shall listen
	 * @param p_o_socketTask									socket task object with the core execution process of a socket
	 * @param p_i_timeoutMilliseconds							set timeout value for socket instance - how long the socket should wait for incoming data so it will not hold forever
	 * @throws IllegalArgumentException							invalid parameter values for port, receive buffer size or timeout
	 * @throws NullPointerException								socket task parameter is null
	 * @throws java.net.UnknownHostException					issue local host name could not be resolved into an address
	 */
	public ReceiveUDP(Class<T> p_o_classType, ReceiveType p_e_type, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketTask, int p_i_timeoutMilliseconds) throws IllegalArgumentException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_e_type, java.net.InetAddress.getLocalHost().getHostAddress(), p_i_port, p_o_socketTask, p_i_timeoutMilliseconds, -1);
	}
	
	/**
	 * Create receive socket instance for UDP network connection, executing within a thread. Receive buffer size = 1500 bytes. Infinite thread executions of socket instance.
	 * 
	 * @param p_o_classType										java class type of socket instance, e.g. java.net.ServerSocket.class
	 * @param p_e_type											receiving type of socket instance - SERVER or CLIENT
	 * @param p_s_host											address of receiving socket instance
	 * @param p_i_port											port where receiving socket instance shall listen
	 * @param p_o_socketTask									socket task object with the core execution process of a socket
	 * @param p_i_timeoutMilliseconds							set timeout value for socket instance - how long the socket should wait for incoming data so it will not hold forever
	 * @throws IllegalArgumentException							invalid parameter values for port, receive buffer size or timeout
	 * @throws NullPointerException								socket task parameter is null
	 * @throws java.net.UnknownHostException					issue local host name could not be resolved into an address
	 */
	public ReceiveUDP(Class<T> p_o_classType, ReceiveType p_e_type, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketTask, int p_i_timeoutMilliseconds) throws IllegalArgumentException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_e_type, p_s_host, p_i_port, p_o_socketTask, p_i_timeoutMilliseconds, -1);
	}
	
	/**
	 * Create receive socket instance for UDP network connection, executing within a thread. Receive buffer size = 1500 bytes.
	 * 
	 * @param p_o_classType										java class type of socket instance, e.g. java.net.ServerSocket.class
	 * @param p_e_type											receiving type of socket instance - SERVER or CLIENT
	 * @param p_i_port											port where receiving socket instance shall listen
	 * @param p_o_socketTask									socket task object with the core execution process of a socket
	 * @param p_i_timeoutMilliseconds							set timeout value for socket instance - how long the socket should wait for incoming data so it will not hold forever
	 * @param p_i_maxTerminations								set a max. value for thread executions of socket instance
	 * @throws IllegalArgumentException							invalid parameter values for port, receive buffer size or timeout
	 * @throws NullPointerException								socket task parameter is null
	 * @throws java.net.UnknownHostException					issue local host name could not be resolved into an address
	 */
	public ReceiveUDP(Class<T> p_o_classType, ReceiveType p_e_type, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketTask, int p_i_timeoutMilliseconds, int p_i_maxTerminations) throws IllegalArgumentException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_e_type, java.net.InetAddress.getLocalHost().getHostAddress(), p_i_port, p_o_socketTask, p_i_timeoutMilliseconds, p_i_maxTerminations, 1500);
	}
	
	/**
	 * Create receive socket instance for UDP network connection, executing within a thread. Receive buffer size = 1500 bytes.
	 * 
	 * @param p_o_classType										java class type of socket instance, e.g. java.net.ServerSocket.class
	 * @param p_e_type											receiving type of socket instance - SERVER or CLIENT
	 * @param p_s_host											address of receiving socket instance
	 * @param p_i_port											port where receiving socket instance shall listen
	 * @param p_o_socketTask									socket task object with the core execution process of a socket
	 * @param p_i_timeoutMilliseconds							set timeout value for socket instance - how long the socket should wait for incoming data so it will not hold forever
	 * @param p_i_maxTerminations								set a max. value for thread executions of socket instance
	 * @throws IllegalArgumentException							invalid parameter values for port, receive buffer size or timeout
	 * @throws NullPointerException								socket task parameter is null
	 * @throws java.net.UnknownHostException					issue local host name could not be resolved into an address
	 */
	public ReceiveUDP(Class<T> p_o_classType, ReceiveType p_e_type, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketTask, int p_i_timeoutMilliseconds, int p_i_maxTerminations) throws IllegalArgumentException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_e_type, p_s_host, p_i_port, p_o_socketTask, p_i_timeoutMilliseconds, p_i_maxTerminations, 1500);
	}
	
	/**
	 * Create receive socket instance for UDP network connection, executing within a thread.
	 * 
	 * @param p_o_classType										java class type of socket instance, e.g. java.net.ServerSocket.class
	 * @param p_e_type											receiving type of socket instance - SERVER or CLIENT
	 * @param p_i_port											port where receiving socket instance shall listen
	 * @param p_o_socketTask									socket task object with the core execution process of a socket
	 * @param p_i_timeoutMilliseconds							set timeout value for socket instance - how long the socket should wait for incoming data so it will not hold forever
	 * @param p_i_maxTerminations								set a max. value for thread executions of socket instance
	 * @param p_i_receiveBufferSize								receive buffer size for socket instance, e.g. ethernet packets have max. data length of 1500 bytes
	 * @throws IllegalArgumentException							invalid parameter values for port, receive buffer size or timeout
	 * @throws NullPointerException								socket task parameter is null
	 * @throws java.net.UnknownHostException					issue local host name could not be resolved into an address
	 */
	public ReceiveUDP(Class<T> p_o_classType, ReceiveType p_e_type, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketTask, int p_i_timeoutMilliseconds, int p_i_maxTerminations, int p_i_receiveBufferSize) throws IllegalArgumentException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_e_type, java.net.InetAddress.getLocalHost().getHostAddress(), p_i_port, p_o_socketTask, p_i_timeoutMilliseconds, p_i_maxTerminations, p_i_receiveBufferSize);
	}
	
	/**
	 * Create receive socket instance for UDP network connection, executing within a thread.
	 * 
	 * @param p_o_classType										java class type of socket instance, e.g. java.net.ServerSocket.class
	 * @param p_e_type											receiving type of socket instance - SERVER or CLIENT
	 * @param p_s_host											address of receiving socket instance
	 * @param p_i_port											port where receiving socket instance shall listen
	 * @param p_o_socketTask									socket task object with the core execution process of a socket
	 * @param p_i_timeoutMilliseconds							set timeout value for socket instance - how long the socket should wait for incoming data so it will not hold forever
	 * @param p_i_maxTerminations								set a max. value for thread executions of socket instance
	 * @param p_i_receiveBufferSize								receive buffer size for socket instance, e.g. ethernet packets have max. data length of 1500 bytes
	 * @throws IllegalArgumentException							invalid parameter values for port, receive buffer size or timeout
	 * @throws NullPointerException								socket task parameter is null
	 * @throws java.net.UnknownHostException					issue local host name could not be resolved into an address
	 */
	public ReceiveUDP(Class<T> p_o_classType, ReceiveType p_e_type, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketTask, int p_i_timeoutMilliseconds, int p_i_maxTerminations, int p_i_receiveBufferSize) throws IllegalArgumentException, NullPointerException, java.net.UnknownHostException {
		/* set variables with parameter and default values */
		this.o_classType = p_o_classType;
		this.e_type = p_e_type;
		this.i_executorServicePoolAmount = 0;
		this.b_stop = false;
		this.b_stopServer = false;
		this.i_terminations = 0;
		
		/* check port min. value */
		if (p_i_port < 1) {
			throw new IllegalArgumentException("Receive port must be at least '1', but was set to '" + p_i_port + "'");
		}
		
		/* check port max. value */
		if (p_i_port > 65535) {
			throw new IllegalArgumentException("Receive port must be lower equal '65535', but was set to '" + p_i_port + "'");
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("\t" + "set port '" + p_i_port + "'");
		
		/* check receive buffer size min. value */
		if (p_i_receiveBufferSize < 1) {
			throw new IllegalArgumentException("Buffer size must be at least '1', but was set to '" + p_i_receiveBufferSize + "'");
		} else {
			this.i_bufferSize = p_i_receiveBufferSize;
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("\t" + "set receive buffer size '" + p_i_receiveBufferSize + "'");
		
		/* set socket timeout value if it is a positive integer value */
		if (p_i_timeoutMilliseconds > 0) {
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "set socket timeout value '" + p_i_timeoutMilliseconds + "'");
			
			this.i_timeoutMilliseconds = p_i_timeoutMilliseconds;
		} else {
			throw new IllegalArgumentException("Receive timeout must be at least '1' millisecond, but was set to '" + p_i_timeoutMilliseconds + "' millisecond(s)");
		}
		
		if (p_i_maxTerminations < 0) {
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "there is no termination limit");
			
			/* there is no termination limit */
			this.i_maxTerminations = -1;
		} else {
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "set max. termination limit '" + p_i_maxTerminations + "'");
			
			/* set max. termination limit */ 
			this.i_maxTerminations = p_i_maxTerminations;
		}
		
		/* get local host value if host parameter is 'localhost' or '127.0.0.1' */
		if ( (p_s_host.toLowerCase().contentEquals("localhost")) || (p_s_host.toLowerCase().contentEquals("127.0.0.1")) ) {
			p_s_host = java.net.InetAddress.getLocalHost().getHostAddress();
			
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "set host to local host value '" + p_s_host + "'");
		}
		
		try {
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "set socket address value with host and port parameter '" + p_s_host + ":" + p_i_port + "'");
			
			/* set socket address value with host and port parameter */
			this.o_socketAddress = new java.net.InetSocketAddress(java.net.InetAddress.getByName(p_s_host), p_i_port);
		} catch (java.net.UnknownHostException o_exc) {
			throw new IllegalArgumentException("Unknown host[" + p_s_host + "] - " + o_exc.getMessage());
		}
		
		/* set socket task from parameter */
		if (p_o_socketTask == null) {
			throw new NullPointerException("No socket task specified");
		} else {
			this.o_socketTask = p_o_socketTask;
		}
	}
	
	/**
	 * Core execution process method of a socket. Receiving data as a simple socket or as a server instance.
	 */
	@Override
	public void run() {
		this.b_stop = false;
		this.b_stopServer = false;
		
		if (this.e_type == ReceiveType.SOCKET) {
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "run as receiving socket(UDP) instance");
			
			this.runSocket();
		} else if (this.e_type == ReceiveType.SERVER) {
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "run as receiving server(UDP) instance");
			
			this.runServer();
		}
	}
	
	/**
	 * This method stops the socket and ends any network communication.
	 */
	public void stop() {
		this.b_stop = true;
		this.b_stopServer = true;
		
		/* cancel socket task token */
		if ((this.o_socketTask != null) && (this.o_socketTask.getToken() != null)) {
			this.o_socketTask.getToken().cancel();
		}
		
		/* cancel all token instances */
		for(net.forestany.forestj.lib.net.sock.task.Token o_token : this.a_tokenInstances) {
			if (o_token != null) {
				o_token.cancel();
			}
		}
		
		this.a_tokenInstances.clear();
		
		/* close socket */
		if ( (this.o_socketWrapper != null) && (!this.o_socketWrapper.getSocket().isClosed()) ) {
			/* check if we have a multicast socket */
			if (this.o_classType == java.net.MulticastSocket.class) {
				try {
					/* leave multicast group */
					((java.net.MulticastSocket)this.o_socketWrapper.getSocket()).leaveGroup(this.o_socketAddress, this.o_multicastNetworkInterface);
				} catch (java.io.IOException o_exc) {
					net.forestany.forestj.lib.Global.logException("IOException ReceiveUDP-runServer method, leaving multicast group: ", o_exc);
				}
			}
			
			try {
				this.o_socketWrapper.getSocket().close();
			} catch (Exception o_exc) {
				/* nothing to do */
			}
		}
	}
	
	/**
	 * Execute receive socket instance.
	 */
	private void runSocket() {
		try {
			/* check if socket task is set */
			if (this.o_socketTask == null) {
				throw new NullPointerException("There was no socket task specified");
			}
			
			/* check for valid multicast address */
            if (this.o_classType == java.net.MulticastSocket.class) {
            	if (((java.net.InetSocketAddress)this.o_socketAddress).getAddress() instanceof java.net.Inet4Address) {
	            	if (!net.forestany.forestj.lib.Helper.isIpv4MulticastAddress(((java.net.InetSocketAddress)this.o_socketAddress).getAddress().getHostAddress())) {
	                    throw new IllegalArgumentException("Invalid Multicast address '" + ((java.net.InetSocketAddress)this.o_socketAddress).getAddress().getHostAddress() + "'. Valid: 224.0.0.0 - 239.255.255.255");
	                }
            	} else if (((java.net.InetSocketAddress)this.o_socketAddress).getAddress() instanceof java.net.Inet6Address) {
            		if (!net.forestany.forestj.lib.Helper.isIpv6MulticastAddress(((java.net.InetSocketAddress)this.o_socketAddress).getAddress().getHostAddress())) {
	                    throw new IllegalArgumentException("Invalid Multicast IPv6 address '" + ((java.net.InetSocketAddress)this.o_socketAddress).getAddress().getHostAddress() + "'.");
	                }
            	}
            }
			
													net.forestany.forestj.lib.Global.ilogConfig(((this.o_socketTask.getCommunicationType() != null) ? this.o_socketTask.getCommunicationType() + " " : "") + "socket bind to " + this.o_socketAddress);
			
			/* create receive socket and bind to configured address */
			this.o_socketWrapper = new net.forestany.forestj.lib.net.sock.Wrapper<T>(this.o_classType, (this.o_classType == java.net.MulticastSocket.class) ? net.forestany.forestj.lib.net.sock.WrapperType.UDP_MULTICAST_RECEIVER : net.forestany.forestj.lib.net.sock.WrapperType.UDP_SERVER, this.o_socketAddress);
			
													net.forestany.forestj.lib.Global.ilogConfig("created receive socket and binded to configured address");
			
			/* set receive buffer size */
			this.o_socketWrapper.getSocket().setReceiveBufferSize(this.i_bufferSize);
			/* set socket timeout value */
			this.o_socketWrapper.getSocket().setSoTimeout(this.i_timeoutMilliseconds);
			
			/* check if we have a multicast socket */
			if (this.o_classType == java.net.MulticastSocket.class) {
				/* join multicast group */
				((java.net.MulticastSocket)this.o_socketWrapper.getSocket()).joinGroup(this.o_socketAddress, this.o_multicastNetworkInterface);
			}
			
			/* endless loop for our receiving socket instance */
			while (!this.b_stop) {
				try {
					/* create byte array with expected receiving buffer size */
					byte[] a_datagramPacketBytes = new byte[this.o_socketWrapper.getSocket().getReceiveBufferSize()];
					/* create UDP datagram packet object for later use */
					java.net.DatagramPacket o_receivingDatagramPacket = new java.net.DatagramPacket(a_datagramPacketBytes, this.o_socketWrapper.getSocket().getReceiveBufferSize());
					
															net.forestany.forestj.lib.Global.ilogFine("this.o_socketWrapper.getSocket().receive");
					
					/* socket instance waiting for connection */
					this.o_socketWrapper.getSocket().receive(o_receivingDatagramPacket);
					
					/* set buffer length, if needed later */
					this.o_socketTask.setBufferLength(this.i_bufferSize);
					/* pass socket instance for answering request to socket task */
					this.o_socketTask.setSocket(this.o_socketWrapper);
					/* pass received UDP datagram packet object to socket task */
					this.o_socketTask.setDatagramPacket(o_receivingDatagramPacket);
					/* create socket task token instance */
					this.o_socketTask.setToken(new net.forestany.forestj.lib.net.sock.task.Token());
					/* run socket task */
					this.o_socketTask.run();
					
					if (!this.b_stop) { /* set stop flag from socket task, if it is not already set, to end endless loop */
						this.b_stop = this.o_socketTask.getStop();
					}
				} catch (java.net.SocketTimeoutException o_exc) {
					net.forestany.forestj.lib.Global.ilogFiner("SocketTimeoutException ReceiveUDP-runSocket method: " + o_exc.getMessage() + " - Terminations: " + (this.i_terminations + 1) + "/" + this.i_maxTerminations);
				} catch (java.io.IOException o_exc) {
					if (!this.b_stop) { /* exception occurred without control stop */
						net.forestany.forestj.lib.Global.logException("IOException ReceiveUDP-runSocket method: ", o_exc);
						net.forestany.forestj.lib.Global.ilogSevere("\tTerminations: " + (this.i_terminations + 1) + "/" + this.i_maxTerminations);
					}
				} finally {
					/* check amount of cycles in socket instance thread and if max. value for thread executions have been exceeded */
					this.i_terminations++;
					
					if (this.i_maxTerminations > -1) {
						if (this.i_terminations >= this.i_maxTerminations) {
							this.b_stop = true;
						}
					}
					
															net.forestany.forestj.lib.Global.ilogFine("request cycle completed by socket instance; stopped: " + this.b_stop);
				}
			}
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException("Exception ReceiveUDP-runSocket method: ", o_exc);
		} finally {
			/* close socket */
			if ( (this.o_socketWrapper.getSocket() != null) && (!this.o_socketWrapper.getSocket().isClosed()) ) {
				/* check if we have a multicast socket */
				if (this.o_classType == java.net.MulticastSocket.class) {
					try {
						/* leave multicast group */
						((java.net.MulticastSocket)this.o_socketWrapper.getSocket()).leaveGroup(this.o_socketAddress, this.o_multicastNetworkInterface);
					} catch (java.io.IOException o_exc) {
						net.forestany.forestj.lib.Global.logException("IOException ReceiveUDP-runServer method, leaving multicast group: ", o_exc);
					}
				}
				
				this.o_socketWrapper.getSocket().close();
			}
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("ReceiveUDP-runSocket stopped");
	}
	
	/**
	 * Execute receive server instance.
	 */
	private void runServer() {
		/* thread pool instance */
		java.util.concurrent.ExecutorService o_executorServicePool = java.util.concurrent.Executors.newCachedThreadPool();
		
		try {
			/* give thread pool instance a fixed amount of threads */
			if (this.i_executorServicePoolAmount > 0) {
				o_executorServicePool = java.util.concurrent.Executors.newFixedThreadPool(this.i_executorServicePoolAmount);
			}
			
			/* check if socket task is set */
			if (this.o_socketTask == null) {
				throw new NullPointerException("There was no socket task specified");
			}
			
			/* check for valid multicast address */
            if (this.o_classType == java.net.MulticastSocket.class) {
            	if (((java.net.InetSocketAddress)this.o_socketAddress).getAddress() instanceof java.net.Inet4Address) {
	            	if (!net.forestany.forestj.lib.Helper.isIpv4MulticastAddress(((java.net.InetSocketAddress)this.o_socketAddress).getAddress().getHostAddress())) {
	                    throw new IllegalArgumentException("Invalid Multicast IPv4 address '" + ((java.net.InetSocketAddress)this.o_socketAddress).getAddress().getHostAddress() + "'. Valid: 224.0.0.0 - 239.255.255.255");
	                }
            	} else if (((java.net.InetSocketAddress)this.o_socketAddress).getAddress() instanceof java.net.Inet6Address) {
            		if (!net.forestany.forestj.lib.Helper.isIpv6MulticastAddress(((java.net.InetSocketAddress)this.o_socketAddress).getAddress().getHostAddress())) {
	                    throw new IllegalArgumentException("Invalid Multicast IPv6 address '" + ((java.net.InetSocketAddress)this.o_socketAddress).getAddress().getHostAddress() + "'.");
	                }
            	}
            }
			
													net.forestany.forestj.lib.Global.ilogConfig(((this.o_socketTask.getCommunicationType() != null) ? this.o_socketTask.getCommunicationType() + " " : "") + "socket bind to " + this.o_socketAddress);
			
			/* create receive server and bind to configured address */
			this.o_socketWrapper = new net.forestany.forestj.lib.net.sock.Wrapper<T>(this.o_classType, (this.o_classType == java.net.MulticastSocket.class) ? net.forestany.forestj.lib.net.sock.WrapperType.UDP_MULTICAST_RECEIVER : net.forestany.forestj.lib.net.sock.WrapperType.UDP_SERVER, this.o_socketAddress);
			
													net.forestany.forestj.lib.Global.ilogConfig("created receive server and binded to configured address");
			
			/* set receive buffer size */
			this.o_socketWrapper.getSocket().setReceiveBufferSize(this.i_bufferSize);
			/* set server timeout value */
			this.o_socketWrapper.getSocket().setSoTimeout(this.i_timeoutMilliseconds);
			
			/* check if we have a multicast socket */
			if (this.o_classType == java.net.MulticastSocket.class) {
				/* join multicast group */
				((java.net.MulticastSocket)this.o_socketWrapper.getSocket()).joinGroup(this.o_socketAddress, this.o_multicastNetworkInterface);
			}
			
			/* endless loop for our receiving server instance */
			while (!this.b_stopServer) {
				try {
					/* create new instance of socket task, because for every request we need a new one; otherwise we just overwrite the socket instance and do chaos to our communication and so on */
					@SuppressWarnings("unchecked")
					net.forestany.forestj.lib.net.sock.task.Task<T> o_socketTaskInstance = (net.forestany.forestj.lib.net.sock.task.Task<T>)Class.forName(this.o_socketTask.getSocketTaskClassType().getTypeName()).getDeclaredConstructor().newInstance();
					o_socketTaskInstance.cloneFromOtherTask(this.o_socketTask);
					
					/* create byte array with expected receiving buffer size */
					byte[] a_datagramPacketBytes = new byte[this.o_socketWrapper.getSocket().getReceiveBufferSize()];
					/* create UDP datagram packet object for later use */
					java.net.DatagramPacket o_receivingDatagramPacket = new java.net.DatagramPacket(a_datagramPacketBytes, this.o_socketWrapper.getSocket().getReceiveBufferSize());
					
															net.forestany.forestj.lib.Global.ilogFine("this.o_socketWrapper.getSocket().receive");
					
					/* server instance waiting for connection */
					this.o_socketWrapper.getSocket().receive(o_receivingDatagramPacket);
					
					/* create socket task token instance */
					net.forestany.forestj.lib.net.sock.task.Token o_token = new net.forestany.forestj.lib.net.sock.task.Token();
					this.a_tokenInstances.add(o_token);
					o_socketTaskInstance.setToken(o_token);
					
					/* set buffer length, if needed later */
					o_socketTaskInstance.setBufferLength(this.i_bufferSize);
					/* pass server instance for answering request to socket task */
					o_socketTaskInstance.setSocket(this.o_socketWrapper);
					/* pass received UDP datagram packet object to socket task */
					o_socketTaskInstance.setDatagramPacket(o_receivingDatagramPacket);
					/* run socket task within thread pool */
					o_executorServicePool.execute(o_socketTaskInstance);
				} catch (java.net.SocketTimeoutException o_exc) {
					net.forestany.forestj.lib.Global.ilogFiner("SocketTimeoutException ReceiveUDP-runServer method: " + o_exc.getMessage() + " - Terminations: " + (this.i_terminations + 1) + "/" + this.i_maxTerminations);
				} catch (java.io.IOException o_exc) {
					if (!this.b_stopServer) { /* exception occurred without control stop */
						net.forestany.forestj.lib.Global.logException("IOException ReceiveUDP-runServer method: ", o_exc);
						net.forestany.forestj.lib.Global.ilogSevere("\tTerminations: " + (this.i_terminations + 1) + "/" + this.i_maxTerminations);
					}
				} finally {
					/* check amount of cycles in server instance thread and if max. value for thread executions have been exceeded */
					this.i_terminations++;
					
					if (this.i_maxTerminations > -1) {
						if (this.i_terminations >= this.i_maxTerminations) {
							this.b_stopServer = true;
						}
					}
					
															net.forestany.forestj.lib.Global.ilogFine("request passed to thread pool by server instance; stopped: " + this.b_stopServer);
				}
			}
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException("Exception ReceiveUDP-runServer method: ", o_exc);
		} finally {
			/* shut down thread pool */
			o_executorServicePool.shutdown();
			
			try {
				/* wait 5 seconds for thread pool termination */
				o_executorServicePool.awaitTermination(5L, java.util.concurrent.TimeUnit.SECONDS);
				
				/* close socket */
				if ( (this.o_socketWrapper.getSocket() != null) && (!this.o_socketWrapper.getSocket().isClosed()) ) {
					/* check if we have a multicast socket */
					if (this.o_classType == java.net.MulticastSocket.class) {
						try {
							/* leave multicast group */
							((java.net.MulticastSocket)this.o_socketWrapper.getSocket()).leaveGroup(this.o_socketAddress, this.o_multicastNetworkInterface);
						} catch (java.io.IOException o_exc) {
							net.forestany.forestj.lib.Global.logException("IOException ReceiveUDP-runServer method, leaving multicast group: ", o_exc);
						}
					}
					
					this.o_socketWrapper.getSocket().close();
				}
			} catch (InterruptedException o_exc) {
				/* nothing to do */
			}
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("ReceiveUDP-runServer stopped");
	}
}
