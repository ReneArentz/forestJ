package net.forestany.forestj.lib.net.sock.send;

/**
 * Generic socket class to send network traffic over a socket instance. Several methods supporting sending data over TCP (optional with TLS).
 *
 * @param <T>	java socket class parameter
 */
public class SendTCP<T extends java.net.Socket> extends net.forestany.forestj.lib.net.sock.Socket<T> {
	
	/* Fields */
	
	private java.net.SocketAddress o_socketLocalAddress;
	private int i_intervalMilliseconds;
	private boolean b_usingProxy; 
	private net.forestany.forestj.lib.net.sock.Wrapper<T> o_proxySocket;
	
	/* Properties */
	
	/**
	 * get using proxy flag
	 * 
	 * @return boolean
	 */
	public boolean getUsingProxy() {
		return this.b_usingProxy;
	}
	
	/**
	 * set using proxy flag
	 * 
	 * @param p_b_value boolean
	 */
	public void setUsingProxy(boolean p_b_value) {
		this.b_usingProxy = p_b_value;
	}
	
	/* Methods */
	
	/**
	 * Create send socket instance for TCP network connection, executing within a thread. Auto determine local address of this socket instance. Buffer size = 1500 bytes. No interval for waiting for other communication side or new data to send. Execute socket thread just once. Do not check reachability. 60 seconds timeout as default.
	 * 
	 * @param p_o_classType								java class type of socket instance, e.g. java.net.Socket.class
	 * @param p_s_host									address of destination for socket instance
	 * @param p_i_port									port of destination for socket instance
	 * @param p_o_socketReceiveTask						socket task object with the core execution process of a socket
	 * @throws IllegalArgumentException					invalid parameter values for ports, buffer size or timeout
	 * @throws IllegalStateException					destination host is not reachable with that host and port parameter
	 * @throws NullPointerException						socket task parameter is null
	 * @throws java.net.UnknownHostException			issue local host name could not be resolved into an address
	 */
	public SendTCP(Class<T> p_o_classType, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketReceiveTask) throws IllegalArgumentException, IllegalStateException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_s_host, p_i_port, p_o_socketReceiveTask, 60000);
	}
	
	/**
	 * Create send socket instance for TCP network connection, executing within a thread. Auto determine local address of this socket instance. Buffer size = 1500 bytes. No interval for waiting for other communication side or new data to send. Execute socket thread just once. Do not check reachability.
	 * 
	 * @param p_o_classType								java class type of socket instance, e.g. java.net.Socket.class
	 * @param p_s_host									address of destination for socket instance
	 * @param p_i_port									port of destination for socket instance
	 * @param p_o_socketReceiveTask						socket task object with the core execution process of a socket
	 * @param p_i_timeoutMilliseconds					set timeout value for socket instance - how long the socket should wait for incoming data so it will not hold forever
	 * @throws IllegalArgumentException					invalid parameter values for ports, buffer size or timeout
	 * @throws IllegalStateException					destination host is not reachable with that host and port parameter
	 * @throws NullPointerException						socket task parameter is null
	 * @throws java.net.UnknownHostException			issue local host name could not be resolved into an address
	 */
	public SendTCP(Class<T> p_o_classType, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketReceiveTask, int p_i_timeoutMilliseconds) throws IllegalArgumentException, IllegalStateException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_s_host, p_i_port, p_o_socketReceiveTask, p_i_timeoutMilliseconds, false);
	}
	
	/**
	 * Create send socket instance for TCP network connection, executing within a thread. Auto determine local address of this socket instance. Buffer size = 1500 bytes. No interval for waiting for other communication side or new data to send. Execute socket thread just once.
	 * 
	 * @param p_o_classType								java class type of socket instance, e.g. java.net.Socket.class
	 * @param p_s_host									address of destination for socket instance
	 * @param p_i_port									port of destination for socket instance
	 * @param p_o_socketReceiveTask						socket task object with the core execution process of a socket
	 * @param p_i_timeoutMilliseconds					set timeout value for socket instance - how long the socket should wait for incoming data so it will not hold forever
	 * @param p_b_checkReachability						true - check if destination is reachable, false - do not check reachability
	 * @throws IllegalArgumentException					invalid parameter values for ports, buffer size or timeout
	 * @throws IllegalStateException					destination host is not reachable with that host and port parameter
	 * @throws NullPointerException						socket task parameter is null
	 * @throws java.net.UnknownHostException			issue local host name could not be resolved into an address
	 */
	public SendTCP(Class<T> p_o_classType, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketReceiveTask, int p_i_timeoutMilliseconds, boolean p_b_checkReachability) throws IllegalArgumentException, IllegalStateException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_s_host, p_i_port, p_o_socketReceiveTask, p_i_timeoutMilliseconds, p_b_checkReachability, 1);
	}
	
	/**
	 * Create send socket instance for TCP network connection, executing within a thread. Auto determine local address of this socket instance. Buffer size = 1500 bytes. No interval for waiting for other communication side or new data to send.
	 * 
	 * @param p_o_classType								java class type of socket instance, e.g. java.net.Socket.class
	 * @param p_s_host									address of destination for socket instance
	 * @param p_i_port									port of destination for socket instance
	 * @param p_o_socketReceiveTask						socket task object with the core execution process of a socket
	 * @param p_i_timeoutMilliseconds					set timeout value for socket instance - how long the socket should wait for incoming data so it will not hold forever
	 * @param p_b_checkReachability						true - check if destination is reachable, false - do not check reachability
	 * @param p_i_maxTerminations						set a max. value for thread executions of socket instance
	 * @throws IllegalArgumentException					invalid parameter values for ports, buffer size or timeout
	 * @throws IllegalStateException					destination host is not reachable with that host and port parameter
	 * @throws NullPointerException						socket task parameter is null
	 * @throws java.net.UnknownHostException			issue local host name could not be resolved into an address
	 */
	public SendTCP(Class<T> p_o_classType, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketReceiveTask, int p_i_timeoutMilliseconds, boolean p_b_checkReachability, int p_i_maxTerminations) throws IllegalArgumentException, IllegalStateException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_s_host, p_i_port, p_o_socketReceiveTask, p_i_timeoutMilliseconds, p_b_checkReachability, p_i_maxTerminations, 0);
	}
	
	/**
	 * Create send socket instance for TCP network connection, executing within a thread. Auto determine local address of this socket instance. Buffer size = 1500 bytes.
	 * 
	 * @param p_o_classType								java class type of socket instance, e.g. java.net.Socket.class
	 * @param p_s_host									address of destination for socket instance
	 * @param p_i_port									port of destination for socket instance
	 * @param p_o_socketReceiveTask						socket task object with the core execution process of a socket
	 * @param p_i_timeoutMilliseconds					set timeout value for socket instance - how long the socket should wait for incoming data so it will not hold forever
	 * @param p_b_checkReachability						true - check if destination is reachable, false - do not check reachability
	 * @param p_i_maxTerminations						set a max. value for thread executions of socket instance
	 * @param p_i_intervalMilliseconds					interval for waiting for other communication side or new data to send
	 * @throws IllegalArgumentException					invalid parameter values for ports, buffer size or timeout
	 * @throws IllegalStateException					destination host is not reachable with that host and port parameter
	 * @throws NullPointerException						socket task parameter is null
	 * @throws java.net.UnknownHostException			issue local host name could not be resolved into an address
	 */
	public SendTCP(Class<T> p_o_classType, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketReceiveTask, int p_i_timeoutMilliseconds, boolean p_b_checkReachability, int p_i_maxTerminations, int p_i_intervalMilliseconds) throws IllegalArgumentException, IllegalStateException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_s_host, p_i_port, p_o_socketReceiveTask, p_i_timeoutMilliseconds, p_b_checkReachability, p_i_maxTerminations, p_i_intervalMilliseconds, 1500);
	}
	
	/**
	 * Create send socket instance for TCP network connection, executing within a thread. Auto determine local address of this socket instance.
	 * 
	 * @param p_o_classType								java class type of socket instance, e.g. java.net.Socket.class
	 * @param p_s_host									address of destination for socket instance
	 * @param p_i_port									port of destination for socket instance
	 * @param p_o_socketReceiveTask						socket task object with the core execution process of a socket
	 * @param p_i_timeoutMilliseconds					set timeout value for socket instance - how long the socket should wait for incoming data so it will not hold forever
	 * @param p_b_checkReachability						true - check if destination is reachable, false - do not check reachability
	 * @param p_i_maxTerminations						set a max. value for thread executions of socket instance
	 * @param p_i_intervalMilliseconds					interval for waiting for other communication side or new data to send
	 * @param p_i_bufferSize							buffer size for socket instance, e.g. ethernet packets have max. data length of 1500 bytes
	 * @throws IllegalArgumentException					invalid parameter values for ports, buffer size or timeout
	 * @throws IllegalStateException					destination host is not reachable with that host and port parameter
	 * @throws NullPointerException						socket task parameter is null
	 * @throws java.net.UnknownHostException			issue local host name could not be resolved into an address
	 */
	public SendTCP(Class<T> p_o_classType, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketReceiveTask, int p_i_timeoutMilliseconds, boolean p_b_checkReachability, int p_i_maxTerminations, int p_i_intervalMilliseconds, int p_i_bufferSize) throws IllegalArgumentException, IllegalStateException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_s_host, p_i_port, p_o_socketReceiveTask, p_i_timeoutMilliseconds, p_b_checkReachability, p_i_maxTerminations, p_i_intervalMilliseconds, p_i_bufferSize, java.net.InetAddress.getLocalHost().getHostAddress(), 0);
	}
	
	/**
	 * Create send socket instance for TCP network connection, executing within a thread. Auto determine local address of this socket instance.
	 * 
	 * @param p_o_classType								java class type of socket instance, e.g. java.net.Socket.class
	 * @param p_s_host									address of destination for socket instance
	 * @param p_i_port									port of destination for socket instance
	 * @param p_o_socketReceiveTask						socket task object with the core execution process of a socket
	 * @param p_i_timeoutMilliseconds					set timeout value for socket instance - how long the socket should wait for incoming data so it will not hold forever
	 * @param p_b_checkReachability						true - check if destination is reachable, false - do not check reachability
	 * @param p_i_maxTerminations						set a max. value for thread executions of socket instance
	 * @param p_i_intervalMilliseconds					interval for waiting for other communication side or new data to send
	 * @param p_i_bufferSize							buffer size for socket instance, e.g. ethernet packets have max. data length of 1500 bytes
	 * @param p_i_localPort								local port of this socket instance
	 * @throws IllegalArgumentException					invalid parameter values for ports, buffer size or timeout
	 * @throws IllegalStateException					destination host is not reachable with that host and port parameter
	 * @throws NullPointerException						socket task parameter is null
	 * @throws java.net.UnknownHostException			issue local host name could not be resolved into an address
	 */
	public SendTCP(Class<T> p_o_classType, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketReceiveTask, int p_i_timeoutMilliseconds, boolean p_b_checkReachability, int p_i_maxTerminations, int p_i_intervalMilliseconds, int p_i_bufferSize, int p_i_localPort) throws IllegalArgumentException, IllegalStateException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_s_host, p_i_port, p_o_socketReceiveTask, p_i_timeoutMilliseconds, p_b_checkReachability, p_i_maxTerminations, p_i_intervalMilliseconds, p_i_bufferSize, java.net.InetAddress.getLocalHost().getHostAddress(), p_i_localPort);
	}
	
	/**
	 * Create send socket instance for TCP network connection, executing within a thread.
	 * 
	 * @param p_o_classType								java class type of socket instance, e.g. java.net.Socket.class
	 * @param p_s_host									address of destination for socket instance
	 * @param p_i_port									port of destination for socket instance
	 * @param p_o_socketReceiveTask						socket task object with the core execution process of a socket
	 * @param p_i_timeoutMilliseconds					set timeout value for socket instance - how long the socket should wait for incoming data so it will not hold forever
	 * @param p_b_checkReachability						true - check if destination is reachable, false - do not check reachability
	 * @param p_i_maxTerminations						set a max. value for thread executions of socket instance
	 * @param p_i_intervalMilliseconds					interval for waiting for other communication side or new data to send
	 * @param p_i_bufferSize							buffer size for socket instance, e.g. ethernet packets have max. data length of 1500 bytes
	 * @param p_s_localAddress							local address of this socket instance
	 * @param p_i_localPort								local port of this socket instance
	 * @throws IllegalArgumentException					invalid parameter values for ports, buffer size or timeout
	 * @throws IllegalStateException					destination host is not reachable with that host and port parameter
	 * @throws NullPointerException						socket task parameter is null
	 * @throws java.net.UnknownHostException			issue local host name could not be resolved into an address
	 */
	public SendTCP(Class<T> p_o_classType, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketReceiveTask, int p_i_timeoutMilliseconds, boolean p_b_checkReachability, int p_i_maxTerminations, int p_i_intervalMilliseconds, int p_i_bufferSize, String p_s_localAddress, int p_i_localPort) throws IllegalArgumentException, IllegalStateException, NullPointerException, java.net.UnknownHostException {
		this(p_o_classType, p_s_host, p_i_port, p_o_socketReceiveTask, p_i_timeoutMilliseconds, p_b_checkReachability, p_i_maxTerminations, p_i_intervalMilliseconds, p_i_bufferSize, p_s_localAddress, p_i_localPort, null);
	}
	
	/**
	 * Create send socket instance for TCP network connection, executing within a thread.
	 * 
	 * @param p_o_classType								java class type of socket instance, e.g. java.net.Socket.class
	 * @param p_s_host									address of destination for socket instance
	 * @param p_i_port									port of destination for socket instance
	 * @param p_o_socketReceiveTask						socket task object with the core execution process of a socket
	 * @param p_i_timeoutMilliseconds					set timeout value for socket instance - how long the socket should wait for incoming data so it will not hold forever
	 * @param p_b_checkReachability						true - check if destination is reachable, false - do not check reachability
	 * @param p_i_maxTerminations						set a max. value for thread executions of socket instance
	 * @param p_i_intervalMilliseconds					interval for waiting for other communication side or new data to send
	 * @param p_i_bufferSize							buffer size for socket instance, e.g. ethernet packets have max. data length of 1500 bytes
	 * @param p_s_localAddress							local address of this socket instance
	 * @param p_i_localPort								local port of this socket instance
	 * @param p_o_sslContext							own ssl context for tLS receive socket instance
	 * @throws IllegalArgumentException					invalid parameter values for ports, buffer size or timeout
	 * @throws IllegalStateException					destination host is not reachable with that host and port parameter
	 * @throws NullPointerException						socket task parameter is null
	 * @throws java.net.UnknownHostException			issue local host name could not be resolved into an address
	 */
	public SendTCP(Class<T> p_o_classType, String p_s_host, int p_i_port, net.forestany.forestj.lib.net.sock.task.Task<T> p_o_socketReceiveTask, int p_i_timeoutMilliseconds, boolean p_b_checkReachability, int p_i_maxTerminations, int p_i_intervalMilliseconds, int p_i_bufferSize, String p_s_localAddress, int p_i_localPort, javax.net.ssl.SSLContext p_o_sslContext) throws IllegalArgumentException, IllegalStateException, NullPointerException, java.net.UnknownHostException {
		/* set variables with parameter and default values */
		this.b_stop = false;
		this.b_usingProxy = false;
		this.i_terminations = 0;
		this.o_classType = p_o_classType;
		this.o_sslContext = p_o_sslContext;
		this.o_proxySocket = null;
		
		/* check port min. value */
		if (p_i_port < 1) {
			throw new IllegalArgumentException("Send port must be at least '1', but was set to '" + p_i_port + "'");
		}
		
		/* check port max. value */
		if (p_i_port > 65535) {
			throw new IllegalArgumentException("Send port must be lower equal '65535', but was set to '" + p_i_port + "'");
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("\t" + "set port '" + p_i_port + "'");
		
		/* check buffer size min. value */
		if (p_i_bufferSize < 4) {
			throw new IllegalArgumentException("Buffer size must be at least '4', but was set to '" + p_i_bufferSize + "'");
		} else {
			this.i_bufferSize = p_i_bufferSize;
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("\t" + "set buffer size '" + p_i_bufferSize + "'");
		
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
		
		if (p_i_intervalMilliseconds < 0) {
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "there is no interval for waiting for other communication side or new data to send");
			
			/* there is no interval for waiting for other communication side or new data to send */
			this.i_intervalMilliseconds = 0;
		} else {
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "set interval '" + p_i_intervalMilliseconds + "' for waiting for other communication side or new data to send");
			
			/* set interval for waiting for other communication side or new data to send */
			this.i_intervalMilliseconds = p_i_intervalMilliseconds;
		}
		
		/* check local port parameter for receiving answer of request */
		if (p_i_localPort > 0) {
			/* check local port min. value */
			if (p_i_localPort < 1) {
				throw new IllegalArgumentException("Local send port must be at least '1', but was set to '" + p_i_port + "'");
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
		
		/* check destination reachability */
		if ( (p_b_checkReachability) && (!this.isReachable()) ) {
			throw new IllegalStateException("Host[" + this.o_socketAddress + "] is not reachable");
		}
		
		/* set socket task from parameter */
		if (p_o_socketReceiveTask == null) {
			throw new NullPointerException("No socket task specified");
		} else {
			this.o_socketTask = p_o_socketReceiveTask;
		}
	}
	
	/**
	 * Check destination reachability with host and port settings.
	 * 
	 * @return			true - destination is reachable, false - destination is not reachable, maybe wrong host or port parameter
	 */
	private boolean isReachable() {
		try {
			/* socket object */
			net.forestany.forestj.lib.net.sock.Wrapper<T> o_tempSocketWrapper = null;
			
			if (this.o_sslContext != null) { /* create tls socket with own ssl context to check reachability */
														net.forestany.forestj.lib.Global.ilogConfig("create tls socket with own ssl context to check reachability");
													
				o_tempSocketWrapper = new net.forestany.forestj.lib.net.sock.Wrapper<T>(this.o_classType, net.forestany.forestj.lib.net.sock.WrapperType.TCP_TLS_CLIENT, null, this.o_sslContext);
			} else {
				if (this.o_classType == javax.net.ssl.SSLSocket.class) {/* create tls socket with default ssl context to check reachability */
															net.forestany.forestj.lib.Global.ilogConfig("create tls socket with default ssl context to check reachability");
													
					o_tempSocketWrapper = new net.forestany.forestj.lib.net.sock.Wrapper<T>(this.o_classType, net.forestany.forestj.lib.net.sock.WrapperType.TCP_TLS_CLIENT, null, null);
				} else { /* create socket to check reachability */
															net.forestany.forestj.lib.Global.ilogConfig("create socket to check reachability");
					
					o_tempSocketWrapper = new net.forestany.forestj.lib.net.sock.Wrapper<T>(this.o_classType, net.forestany.forestj.lib.net.sock.WrapperType.TCP_CLIENT);
				}
			}
			
													net.forestany.forestj.lib.Global.ilogConfig("connect to destination[" + this.o_socketAddress + "] to check reachability");
			
			/* connect to destination to check reachability */
			o_tempSocketWrapper.getSocket().connect(this.o_socketAddress, this.i_timeoutMilliseconds);
	        
			/* close socket */
			if (!o_tempSocketWrapper.getSocket().isClosed()) {
				o_tempSocketWrapper.getSocket().close();
			}
			
													net.forestany.forestj.lib.Global.ilogConfig("no exception occurred so destination is reachable");
			
			/* no exception occurred so destination is reachable */
	        return true;
		} catch (java.net.ConnectException o_exc) {
			try {
				/* wait socket timeout interval for closing the test communication */
				Thread.sleep(this.i_timeoutMilliseconds);
			} catch (InterruptedException o_threadExc) {
				/* nothing to do */
			}
			
													net.forestany.forestj.lib.Global.ilogConfig("exception occurred during connect so destination is not reachable: " + o_exc.getMessage());
			
			/* exception occurred during connect so destination is not reachable */
			return false;
	    } catch (java.io.IOException o_exc) {
	    											net.forestany.forestj.lib.Global.ilogConfig("io exception occurred so destination is not reachable: " + o_exc.getMessage());
	    	
	        return false;
	    } catch (Exception o_exc) {
	    											net.forestany.forestj.lib.Global.ilogConfig("general exception occurred so destination is not reachable: " + o_exc.getMessage());
	    											
	    	return false;
	    }
	}
	
	/**
	 * Override destination host and port settings, so we can send requests to different destinations with one sending socket instance
	 * 
	 * @param p_s_host									address of destination for socket instance
	 * @param p_i_port									port of destination for socket instance
	 * @param p_b_usingProxy							true - using proxy as destination, false - not using proxy
	 * @param p_b_checkReachability						true - check if destination is reachable, false - do not check reachability
	 * @throws IllegalArgumentException					invalid parameter values for ports, buffer size or timeout
	 * @throws IllegalStateException					destination host is not reachable with that host and port parameter
	 * @throws java.net.UnknownHostException			issue local host name could not be resolved into an address
	 */
	public void overrideDestinationAddress(String p_s_host, int p_i_port, boolean p_b_usingProxy, boolean p_b_checkReachability) throws IllegalArgumentException, IllegalStateException, java.net.UnknownHostException {
		/* check port min. value */
		if (p_i_port < 1) {
			throw new IllegalArgumentException("Send port must be at least '1', but was set to '" + p_i_port + "'");
		}
		
		/* check port max. value */
		if (p_i_port > 65535) {
			throw new IllegalArgumentException("Send port must be lower equal '65535', but was set to '" + p_i_port + "'");
		}
		
		/* get host value if host parameter is 'localhost' or '127.0.0.1' */
		if ( (p_s_host.toLowerCase().contentEquals("localhost")) || (p_s_host.toLowerCase().contentEquals("127.0.0.1")) ) {
			p_s_host = java.net.InetAddress.getLocalHost().getHostAddress();
			
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "set host to local host value '" + p_s_host + "'");
		}
		
		try {
			net.forestany.forestj.lib.Global.ilogConfig("\t" + "override socket address value with host and port parameter '" + p_s_host + ":" + p_i_port + "'");

			/* set socket address value with host and port parameter */
			this.o_socketAddress = new java.net.InetSocketAddress(java.net.InetAddress.getByName(p_s_host), p_i_port);
		} catch (java.net.UnknownHostException o_exc) {
			throw new IllegalArgumentException("Unknown host[" + p_s_host + "] - " + o_exc.getMessage());
		}
			
		/* check destination reachability */
		if ( (p_b_checkReachability) && (!this.isReachable()) ) {
			throw new IllegalStateException("Host[" + this.o_socketAddress + "] is not reachable");
		}
		
		this.setUsingProxy(p_b_usingProxy);
	}
	
	/**
	 * Upgrade current socket(proxy socket) to a TLS socket connection
	 * 
	 * @param p_s_host											address of destination for tls socket instance
	 * @param p_i_port											port of destination for tls socket instance
	 * @param p_b_checkReachability								true - check if destination is reachable, false - do not check reachability
	 * @throws IllegalStateException							can only upgrade TCP-Client socket without TLS to TLS socket
	 * @throws java.io.IOException								issue creating TCP or TCP with TLS socket instance
	 * @throws java.security.NoSuchAlgorithmException			issue accessing javax.net.ssl.SSLContext.getDefault() for creating a TCP with TLS socket instance
	*/
	public void upgradeCurrentSocketToTLS(String p_s_host, int p_i_port, boolean p_b_checkReachability) throws IllegalStateException, java.io.IOException, java.security.NoSuchAlgorithmException {
		if (this.o_socketWrapper.getType() != net.forestany.forestj.lib.net.sock.WrapperType.TCP_CLIENT) {
			throw new IllegalStateException("Can only upgrade TCP-Client socket without TLS to TLS socket");
		}
		
		/* check port min. value */
		if (p_i_port < 1) {
			throw new IllegalArgumentException("Send port must be at least '1', but was set to '" + p_i_port + "'");
		}
		
		/* check port max. value */
		if (p_i_port > 65535) {
			throw new IllegalArgumentException("Send port must be lower equal '65535', but was set to '" + p_i_port + "'");
		}
		
		/* get host value if host parameter is 'localhost' or '127.0.0.1' */
		if ( (p_s_host.toLowerCase().contentEquals("localhost")) || (p_s_host.toLowerCase().contentEquals("127.0.0.1")) ) {
			p_s_host = java.net.InetAddress.getLocalHost().getHostAddress();
			
													net.forestany.forestj.lib.Global.ilogConfig("\t" + "set host to local host value '" + p_s_host + "'");
		}
		
		try {
			net.forestany.forestj.lib.Global.ilogConfig("\t" + "override socket address value with host and port parameter '" + p_s_host + ":" + p_i_port + "'");

			/* set socket address value with host and port parameter */
			this.o_socketAddress = new java.net.InetSocketAddress(java.net.InetAddress.getByName(p_s_host), p_i_port);
		} catch (java.net.UnknownHostException o_exc) {
			throw new IllegalArgumentException("Unknown host[" + p_s_host + "] - " + o_exc.getMessage());
		}
			
		/* check destination reachability */
		if ( (p_b_checkReachability) && (!this.isReachable()) ) {
			throw new IllegalStateException("Host[" + this.o_socketAddress + "] is not reachable");
		}
		
		this.o_proxySocket = this.o_socketWrapper;
		
		if (this.o_sslContext == null) {
			/* create socket instance */
			this.o_socketWrapper = new net.forestany.forestj.lib.net.sock.Wrapper<T>( (javax.net.ssl.SSLSocket)javax.net.ssl.SSLContext.getDefault().getSocketFactory().createSocket( ((java.net.Socket)this.o_proxySocket.getSocket()), p_s_host, p_i_port, true ) );
		} else {
			/* create socket instance with ssl context parameter */
			this.o_socketWrapper = new net.forestany.forestj.lib.net.sock.Wrapper<T>( (javax.net.ssl.SSLSocket)this.o_sslContext.getSocketFactory().createSocket( ((java.net.Socket)this.o_proxySocket.getSocket()), p_s_host, p_i_port, true ) );
		}
		
		/* pass socket instance for answering request to socket task */
		this.o_socketTask.setSocket(this.o_socketWrapper);
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
			
													net.forestany.forestj.lib.Global.ilogConfig(((this.o_socketTask.getCommunicationType() != null) ? this.o_socketTask.getCommunicationType() + " " : "") + "socket bind to " + this.o_socketAddress);
													
			/* remind current time in milliseconds */
			long l_foo = System.currentTimeMillis();
			
			/* counter for max. exception terminations */
			int i_exceptionTerminations = 1;
			
			/* endless loop for our sending socket instance */
			while (!this.b_stop) {
				/* sending over TCP default or TCP with direct answer communication type, but not with Equal-Bidirectional */
				if ( ( (this.o_socketTask.getCommunicationType() == net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND) || (this.o_socketTask.getCommunicationType() == net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND_WITH_ANSWER) ) && (this.o_socketTask.getCommunicationCardinality() != net.forestany.forestj.lib.net.sock.com.Cardinality.EqualBidirectional) ) {
					/* if we have no data available to be sent wait interval value for new data */
					if (!this.o_socketTask.messagesAvailable()) {
						if (this.i_intervalMilliseconds > 0) {
							/* wait interval value */
							Thread.sleep(this.i_intervalMilliseconds);
						}
						
						/* continue to next loop iteration */
						continue;
					}
				}
				
				if (this.b_usingProxy) {
															net.forestany.forestj.lib.Global.ilogFinest("prepare socket instance for connection to a proxy server, event if this SendTCP instance has SSL-Socket as class type");
					
					@SuppressWarnings("unchecked")
					Class<T> o_temp = (Class<T>) java.net.Socket.class;
					this.o_socketWrapper = new net.forestany.forestj.lib.net.sock.Wrapper<T>(o_temp, net.forestany.forestj.lib.net.sock.WrapperType.TCP_CLIENT);
				} else {
					if (this.o_sslContext != null) { /* prepare tls socket instance for connection with own ssl context */
																net.forestany.forestj.lib.Global.ilogFinest("prepare tls socket instance for connection with own ssl context");
						
						this.o_socketWrapper = new net.forestany.forestj.lib.net.sock.Wrapper<T>(this.o_classType, net.forestany.forestj.lib.net.sock.WrapperType.TCP_TLS_CLIENT, null, this.o_sslContext);
					} else {
						if (this.o_classType == javax.net.ssl.SSLSocket.class) { /* prepare tls socket instance for connection with default ssl context */
																	net.forestany.forestj.lib.Global.ilogFinest("prepare tls socket instance for connection with default ssl context");
						
							this.o_socketWrapper = new net.forestany.forestj.lib.net.sock.Wrapper<T>(this.o_classType, net.forestany.forestj.lib.net.sock.WrapperType.TCP_TLS_CLIENT, null, null);
						} else { /* prepare socket instance for connection */
																	net.forestany.forestj.lib.Global.ilogFinest("prepare socket instance for connection");
						
							this.o_socketWrapper = new net.forestany.forestj.lib.net.sock.Wrapper<T>(this.o_classType, net.forestany.forestj.lib.net.sock.WrapperType.TCP_CLIENT);
						}
					}
				}
				
				if (this.o_socketLocalAddress != null) {/* bind socket instance to configured local address */
															net.forestany.forestj.lib.Global.ilogFinest("bind socket instance to configured local address");
					
					this.o_socketWrapper.getSocket().bind(this.o_socketLocalAddress);
				}
				
				/* set receive buffer size to socket instance */
				this.o_socketWrapper.getSocket().setReceiveBufferSize(this.i_bufferSize);
				/* set send buffer size to socket instance */
				this.o_socketWrapper.getSocket().setSendBufferSize(this.i_bufferSize);
				/* set socket timeout value if it is a positive integer value */
				this.o_socketWrapper.getSocket().setSoTimeout(this.i_timeoutMilliseconds);
				
				try {
															net.forestany.forestj.lib.Global.ilogFiner("this.o_socketWrapper.getSocket().connect to destination[" + this.o_socketAddress + "] with timeout [" + this.o_socketWrapper.getSocket().getSoTimeout() + "ms]");
															
					this.o_socketWrapper.getSocket().connect(this.o_socketAddress, this.o_socketWrapper.getSocket().getSoTimeout());
					
															net.forestany.forestj.lib.Global.ilogFine("connected; sending request");
					
					/* pass buffer size length to socket task */
					this.o_socketTask.setBufferLength(this.i_bufferSize);
					/* pass socket instance for answering request to socket task */
					this.o_socketTask.setSocket(this.o_socketWrapper);
					/* create socket task token instance */
					this.o_socketTask.setToken(new net.forestany.forestj.lib.net.sock.task.Token());
					/* run socket task */
					this.o_socketTask.run();
					
					if (!this.b_stop) { /* set stop flag from socket task, if it is not already set, to end endless loop */
						this.b_stop = this.o_socketTask.getStop();
					}
					
				} catch (java.net.SocketTimeoutException o_exc) {
					net.forestany.forestj.lib.Global.ilogFiner("SocketTimeoutException SendTCP-runSocket method: " + o_exc.getMessage() + " - Terminations: " + (this.i_terminations + 1) + "/" + this.i_maxTerminations);
				} catch (java.io.IOException o_exc) {
					if ( (o_exc.getMessage().contentEquals("Connection refused: connect")) || (o_exc.getMessage().contentEquals("Connection timed out: connect")) || (o_exc.getMessage().contentEquals("Connection timed out: no further information")) || (o_exc.getMessage().contentEquals("Connection refused: no further information")) || (o_exc.getMessage().contentEquals("Connection reset")) ) {
						net.forestany.forestj.lib.Global.ilogWarning("IOException SendTCP-runSocket method: " + o_exc.getMessage() + " - Terminations: " + (this.i_terminations + 1) + "/" + this.i_maxTerminations + " - Expected Terminations: " + i_exceptionTerminations + "/10");
						
						/* check if current time in milliseconds minus reminder is greater than the configured socket timeout, in that case we stop the socket instance */
						if ( (System.currentTimeMillis() - l_foo) > this.i_timeoutMilliseconds) {
																	net.forestany.forestj.lib.Global.ilogFiner("current time in milliseconds minus reminder is greater than the configured socket timeout, in that case we stop the socket instance");
							
							this.b_stop = true;
						}
						
						/* ignore max. terminations if this socket instance should just try once, to prevent stopping the socket instance - maybe the server or a proxy are very slow */
						if ( (this.i_maxTerminations == 1) && ((this.i_terminations + 1) >= this.i_maxTerminations) ) {
																	net.forestany.forestj.lib.Global.ilogFiner("ignore max. terminations if this socket instance should just try once, to prevent stopping the socket instance");
													
							this.i_terminations = -1;
							this.b_stop = false;
							i_exceptionTerminations++;
						}
						
						/* if our exceptionally carried out attempts nevertheless fail more than 10 times, we draw the line here */
						if (i_exceptionTerminations > 10) {
																	net.forestany.forestj.lib.Global.ilogWarning("our exceptionally carried out attempts for socket instance which should just try one, nevertheless fail more than 10 times - stopping instance now");
							this.b_stop = true;
						}
					} else {
						net.forestany.forestj.lib.Global.logException("IOException SendTCP-runSocket method: ", o_exc);
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
					
					/* if socket instance still is running, wait configured milliseconds for other communication side before closing communication and execute another try */
					if (!this.b_stop) {
						if ( (this.i_intervalMilliseconds > 0) && (this.o_socketWrapper.getSocket().isConnected()) ) {
							Thread.sleep(this.i_intervalMilliseconds);
						}
					}
					
					/* close communication and socket instance */
					try {
						if (this.o_proxySocket != null) {
																	net.forestany.forestj.lib.Global.ilogFiner("close communication of proxy socket instance, closed: " + this.o_proxySocket.getSocket().isClosed());
										
							if (!this.o_proxySocket.getSocket().isClosed()) {
								this.o_proxySocket.getSocket().close();
							}
						}
		
																net.forestany.forestj.lib.Global.ilogFiner("close communication and socket instance, closed: " + this.o_socketWrapper.getSocket().isClosed());
						
						if (!this.o_socketWrapper.getSocket().isClosed()) {
							this.o_socketWrapper.getSocket().close();
						}
					} catch (java.io.IOException o_exc) {
						/* nothing to do */
					}
					
															net.forestany.forestj.lib.Global.ilogFiner("request cycle completed by socket instance; stopped: " + this.b_stop);
				}
			}
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException("Exception SendTCP-runSocket method: ", o_exc);
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("SendTCP-runSocket method stopped");
	}
}
