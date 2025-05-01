package net.forestany.forestj.lib.net.sock.send;

/**
 * Generic socket class to send network traffic over a socket instance. Several methods supporting sending data over UDP.
 *
 * @param <T>	java socket class parameter
 */
public class SendUDP<T extends java.net.DatagramSocket> extends net.forestany.forestj.lib.net.sock.Socket<T> {
	
	/* Fields */
	
	private java.net.SocketAddress o_socketLocalAddress;
	private int i_intervalMilliseconds;
	private int i_multicastTTL;
	
	/* Properties */
	
	/**
	 * get multicast ttl
	 * 
	 * @return int
	 */
	public int getMulticastTTL() {
		return this.i_multicastTTL;
	}
	
	/**
	 * set multicast ttl
	 * 
	 * @param p_i_value int
	 * @throws IllegalArgumentException must be a positive integer value
	 */
	public void setMulticastTTL(int p_i_value) throws IllegalArgumentException {
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Multicast TTL must be at least '1', but was set to '" + p_i_value + "'");
		} else {
			this.i_multicastTTL = p_i_value;
		}
	}
	
	/* Methods */
	
	/**
	 * Create send socket instance for UDP network connection, executing within a thread. Auto determine local address of this socket instance. Buffer size = 1500 bytes.  Execute socket thread just once. 60 seconds timeout as default.
	 * 
	 * @param p_o_classType								java class type of socket instance, e.g. java.net.Socket.class
	 * @param p_s_host									address of destination for socket instance
	 * @param p_i_port									port of destination for socket instance
	 * @param p_o_socketReceiveTask						socket task object with the core execution process of a socket
	 * @throws IllegalArgumentException					invalid parameter values for ports, buffer size or timeout
	 * @throws NullPointerException						socket task parameter is null
	 * @throws java.net.UnknownHostException			issue local host name could not be resolved into an address
	 */
	public SendUDP(Class<T> p_o_classType, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketReceiveTask) throws IllegalArgumentException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_s_host, p_i_port, p_o_socketReceiveTask, 60000);
	}
	
	/**
	 * Create send socket instance for UDP network connection, executing within a thread. Auto determine local address of this socket instance. Buffer size = 1500 bytes.  Execute socket thread just once.
	 * 
	 * @param p_o_classType								java class type of socket instance, e.g. java.net.Socket.class
	 * @param p_s_host									address of destination for socket instance
	 * @param p_i_port									port of destination for socket instance
	 * @param p_o_socketReceiveTask						socket task object with the core execution process of a socket
	 * @param p_i_intervalMilliseconds					interval for waiting for other communication side or new data to send
	 * @throws IllegalArgumentException					invalid parameter values for ports, buffer size or timeout
	 * @throws NullPointerException						socket task parameter is null
	 * @throws java.net.UnknownHostException			issue local host name could not be resolved into an address
	 */
	public SendUDP(Class<T> p_o_classType, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketReceiveTask, int p_i_intervalMilliseconds) throws IllegalArgumentException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_s_host, p_i_port, p_o_socketReceiveTask, p_i_intervalMilliseconds, 1);
	}
	
	/**
	 * Create send socket instance for UDP network connection, executing within a thread. Auto determine local address of this socket instance. Buffer size = 1500 bytes.
	 * 
	 * @param p_o_classType								java class type of socket instance, e.g. java.net.Socket.class
	 * @param p_s_host									address of destination for socket instance
	 * @param p_i_port									port of destination for socket instance
	 * @param p_o_socketReceiveTask						socket task object with the core execution process of a socket
	 * @param p_i_intervalMilliseconds					interval for waiting for other communication side or new data to send
	 * @param p_i_maxTerminations						set a max. value for thread executions of socket instance
	 * @throws IllegalArgumentException					invalid parameter values for ports, buffer size or timeout
	 * @throws NullPointerException						socket task parameter is null
	 * @throws java.net.UnknownHostException			issue local host name could not be resolved into an address
	 */
	public SendUDP(Class<T> p_o_classType, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketReceiveTask, int p_i_intervalMilliseconds, int p_i_maxTerminations) throws IllegalArgumentException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_s_host, p_i_port, p_o_socketReceiveTask, p_i_intervalMilliseconds, p_i_maxTerminations, 1500);
	}
	
	/**
	 * Create send socket instance for UDP network connection, executing within a thread. Auto determine local address of this socket instance.
	 * 
	 * @param p_o_classType								java class type of socket instance, e.g. java.net.Socket.class
	 * @param p_s_host									address of destination for socket instance
	 * @param p_i_port									port of destination for socket instance
	 * @param p_o_socketReceiveTask						socket task object with the core execution process of a socket
	 * @param p_i_intervalMilliseconds					interval for waiting for other communication side or new data to send
	 * @param p_i_maxTerminations						set a max. value for thread executions of socket instance
	 * @param p_i_bufferSize							buffer size for socket instance, e.g. ethernet packets have max. data length of 1500 bytes
	 * @throws IllegalArgumentException					invalid parameter values for ports, buffer size or timeout
	 * @throws NullPointerException						socket task parameter is null
	 * @throws java.net.UnknownHostException			issue local host name could not be resolved into an address
	 */
	public SendUDP(Class<T> p_o_classType, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketReceiveTask, int p_i_intervalMilliseconds, int p_i_maxTerminations, int p_i_bufferSize) throws IllegalArgumentException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_s_host, p_i_port, p_o_socketReceiveTask, p_i_intervalMilliseconds, p_i_maxTerminations, p_i_bufferSize, java.net.InetAddress.getLocalHost().getHostAddress(), 0);
	}
	
	/**
	 * Create send socket instance for UDP network connection, executing within a thread. Auto determine local address of this socket instance.
	 * 
	 * @param p_o_classType								java class type of socket instance, e.g. java.net.Socket.class
	 * @param p_s_host									address of destination for socket instance
	 * @param p_i_port									port of destination for socket instance
	 * @param p_o_socketReceiveTask						socket task object with the core execution process of a socket
	 * @param p_i_intervalMilliseconds					interval for waiting for other communication side or new data to send
	 * @param p_i_maxTerminations						set a max. value for thread executions of socket instance
	 * @param p_i_bufferSize							buffer size for socket instance, e.g. ethernet packets have max. data length of 1500 bytes
	 * @param p_i_localPort								local port of this socket instance
	 * @throws IllegalArgumentException					invalid parameter values for ports, buffer size or timeout
	 * @throws NullPointerException						socket task parameter is null
	 * @throws java.net.UnknownHostException			issue local host name could not be resolved into an address
	 */
	public SendUDP(Class<T> p_o_classType, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketReceiveTask, int p_i_intervalMilliseconds, int p_i_maxTerminations, int p_i_bufferSize, int p_i_localPort) throws IllegalArgumentException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_s_host, p_i_port, p_o_socketReceiveTask, p_i_intervalMilliseconds, p_i_maxTerminations, p_i_bufferSize, java.net.InetAddress.getLocalHost().getHostAddress(), p_i_localPort);
	}
	
	/**
	 * Create send socket instance for UDP network connection, executing within a thread.
	 * 
	 * @param p_o_classType								java class type of socket instance, e.g. java.net.Socket.class
	 * @param p_s_host									address of destination for socket instance
	 * @param p_i_port									port of destination for socket instance
	 * @param p_o_socketReceiveTask						socket task object with the core execution process of a socket
	 * @param p_i_intervalMilliseconds					interval for waiting for other communication side or new data to send
	 * @param p_i_maxTerminations						set a max. value for thread executions of socket instance
	 * @param p_i_bufferSize							buffer size for socket instance, e.g. ethernet packets have max. data length of 1500 bytes
	 * @param p_s_localAddress							local address of this socket instance
	 * @param p_i_localPort								local port of this socket instance
	 * @throws IllegalArgumentException					invalid parameter values for ports, buffer size or timeout
	 * @throws NullPointerException						socket task parameter is null
	 * @throws java.net.UnknownHostException			issue local host name could not be resolved into an address
	 */
	public SendUDP(Class<T> p_o_classType, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketReceiveTask, int p_i_intervalMilliseconds, int p_i_maxTerminations, int p_i_bufferSize, String p_s_localAddress, int p_i_localPort) throws IllegalArgumentException, NullPointerException, java.net.UnknownHostException {
		/* set variables with parameter and default values */
		this.o_classType = p_o_classType;
		this.b_stop = false;
		this.i_terminations = 0;
		this.i_multicastTTL = 1;
		
		/* check port min. value */
		if (p_i_port < 1) {
			throw new IllegalArgumentException("Send port must be at least '1', but was set to '" + p_i_port + "'");
		}
		
		/* check port max. value */
		if (p_i_port > 65535) {
			throw new IllegalArgumentException("Send port must be lower equal '65535', but was set to '" + p_i_port + "'");
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("\t" + "set port '" + p_i_port + "'");
		
		if (p_i_intervalMilliseconds < 0) {
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "there is no interval for waiting for other communication side or new data to send");
			
			/* there is no interval for waiting for other communication side or new data to send */
			this.i_intervalMilliseconds = 0;
		} else {
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "set interval '" + p_i_intervalMilliseconds + "' for waiting for other communication side or new data to send");
			
			/* set interval for waiting for other communication side or new data to send */
			this.i_intervalMilliseconds = p_i_intervalMilliseconds;
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
		
		/* check buffer size min. value */
		if (p_i_bufferSize < 1) {
			throw new IllegalArgumentException("Buffer size must be at least '1', but was set to '" + p_i_bufferSize + "'");
		} else {
			this.i_bufferSize = p_i_bufferSize;
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("\t" + "set buffer size '" + p_i_bufferSize + "'");
		
		/* check local port parameter for receiving answer of request */
		if (p_i_localPort > 0) {
			/* check local port min. value */
			if (p_i_localPort < 1) {
				throw new IllegalArgumentException("Local send port must be at least '1', but was set to '" + p_i_localPort + "'");
			}
			
			/* check local port max. value */
			if (p_i_localPort > 65535) {
				throw new IllegalArgumentException("Local send port must be lower equal '65535', but was set to '" + p_i_localPort + "'");
			}
			
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "set socket local address instance '" + p_s_localAddress + ":" + p_i_localPort + "'");
			
			/* set socket local address instance */
			this.o_socketLocalAddress = new java.net.InetSocketAddress(p_s_localAddress, p_i_localPort);
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
		if (p_o_socketReceiveTask == null) {
			throw new NullPointerException("No socket task specified");
		} else {
			this.o_socketTask = p_o_socketReceiveTask;
		}
	}
	
	/**
	 * Core execution process method of a socket. Sending data as a simple socket instance.
	 */
	@Override
	public void run() {
		this.b_stop = false;
		this.runSocket();
	}
	
	/**
	 * This method stops the socket and ends any network communication.
	 */
	public void stop() {
		this.b_stop = true;
		
		/* cancel socket task token */
		if ((this.o_socketTask != null) && (this.o_socketTask.getToken() != null)) {
			this.o_socketTask.getToken().cancel();
		}
		
		/* close socket */
		if ( (this.o_socketWrapper != null) && (!this.o_socketWrapper.getSocket().isClosed()) ) {
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
	                    throw new IllegalArgumentException("Invalid Multicast IPv4 address '" + ((java.net.InetSocketAddress)this.o_socketAddress).getAddress().getHostAddress() + "'. Valid: 224.0.0.0 - 239.255.255.255");
	                }
            	} else if (((java.net.InetSocketAddress)this.o_socketAddress).getAddress() instanceof java.net.Inet6Address) {
            		if (!net.forestany.forestj.lib.Helper.isIpv6MulticastAddress(((java.net.InetSocketAddress)this.o_socketAddress).getAddress().getHostAddress())) {
	                    throw new IllegalArgumentException("Invalid Multicast IPv6 address '" + ((java.net.InetSocketAddress)this.o_socketAddress).getAddress().getHostAddress() + "'.");
	                }
            	}
            }
			
													net.forestany.forestj.lib.Global.ilogConfig(((this.o_socketTask.getCommunicationType() != null) ? this.o_socketTask.getCommunicationType() + " " : "") + "socket connect to " + this.o_socketAddress);
			
			/* endless loop for our sending socket instance */
			while (!this.b_stop) {
														net.forestany.forestj.lib.Global.ilogFinest("prepare socket instance for connection");
														
				/* prepare socket instance for connection */
				this.o_socketWrapper = new net.forestany.forestj.lib.net.sock.Wrapper<T>(this.o_classType, (this.o_classType == java.net.MulticastSocket.class) ? net.forestany.forestj.lib.net.sock.WrapperType.UDP_MULTICAST_SENDER : net.forestany.forestj.lib.net.sock.WrapperType.UDP_CLIENT, this.o_socketAddress);
			
				if (this.o_socketLocalAddress != null) {
															net.forestany.forestj.lib.Global.ilogFinest("bind socket instance to configured local address");
					
					/* bind socket instance to configured local address */
					try {
						this.o_socketWrapper.getSocket().bind(this.o_socketLocalAddress);
					} catch (Exception o_exc) {
						net.forestany.forestj.lib.Global.ilogSevere("could not bind to '" + this.o_socketLocalAddress.toString() + "'");
					}
				}
				
				/* check if we have a multicast socket */
				if (this.o_classType == java.net.MulticastSocket.class) {
					try {
						/* try setOption method */
						((java.net.MulticastSocket)this.o_socketWrapper.getSocket()).setOption(java.net.StandardSocketOptions.IP_MULTICAST_TTL, this.i_multicastTTL);
					} catch (NoSuchMethodError o_excFoo) {
						try {
							/* keep this option, if setOption not works */
							((java.net.MulticastSocket)this.o_socketWrapper.getSocket()).setTimeToLive(this.i_multicastTTL);
						} catch (Exception o_excBar) {
							/* nothing to do, set TTL wont work */
						}
					}
				}
				
				/* set receive buffer size to socket instance */
				this.o_socketWrapper.getSocket().setReceiveBufferSize(this.i_bufferSize);
				/* set send buffer size to socket instance */
				this.o_socketWrapper.getSocket().setSendBufferSize(this.i_bufferSize);
				
				try {
					/* pass socket instance for answering request to socket task */
					this.o_socketTask.setSocket(this.o_socketWrapper);
					/* pass received UDP datagram packet object to socket task */
					this.o_socketTask.setDatagramPacket(new java.net.DatagramPacket("".getBytes(), 0, this.o_socketAddress));
					/* create socket task token instance */
					this.o_socketTask.setToken(new net.forestany.forestj.lib.net.sock.task.Token());
					/* run socket task */
					this.o_socketTask.run();
					
					if (!this.b_stop) { /* set stop flag from socket task, if it is not already set, to end endless loop */
						this.b_stop = this.o_socketTask.getStop();
					}
				} catch (IllegalArgumentException|NullPointerException o_exc) {
					net.forestany.forestj.lib.Global.logException("IllegalArgumentException|NullPointerException SocketSendUDP-runSocket method: ", o_exc);
					net.forestany.forestj.lib.Global.ilogSevere("\tTerminations: " + (this.i_terminations + 1) + "/" + this.i_maxTerminations);
				} finally {
					/* close socket */
					if ( (this.o_socketWrapper.getSocket() != null) && (!this.o_socketWrapper.getSocket().isClosed()) ) {
						this.o_socketWrapper.getSocket().close();
					}
					
					/* check amount of cycles in socket instance thread and if max. value for thread executions have been exceeded */
					this.i_terminations++;
					
					if (this.i_maxTerminations > -1) {
						if (this.i_terminations >= this.i_maxTerminations) {
							this.b_stop = true;
						}
					}
					
					/* if socket instance still is running, wait configured milliseconds for other communication side before closing communication and execute another try */
					if (!this.b_stop) {
						if (this.i_intervalMilliseconds > 0) {
							Thread.sleep(this.i_intervalMilliseconds);
						}
					}
					
															net.forestany.forestj.lib.Global.ilogFinest("request cycle completed by socket instance; stopped: " + this.b_stop);
				}
			}
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException("Exception SendUDP-runSocket method: ", o_exc);
		}

												net.forestany.forestj.lib.Global.ilogConfig("SendUDP-runSocket method stopped");
	}
}
