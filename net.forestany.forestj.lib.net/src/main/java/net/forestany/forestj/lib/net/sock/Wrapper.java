package net.forestany.forestj.lib.net.sock;

/**
 * 
 * Generic class to create a socket instance of any supported wrapper type and access it only with this class by getSocket method.
 *
 * @param <T>	java socket class parameter, supported are:
 * 					java.net.DatagramSocket.class,
 * 					java.net.Socket.class,
 * 					java.net.ServerSocket.class,
 * 					javax.net.ssl.SSLSocket.class,
 * 					javax.net.ssl.SSLServerSocket.class,
 * 					java.net.MulticastSocket.class
 */
public class Wrapper<T> {
	
	/* Fields */
	
	private final Class<T> o_classType;
	private WrapperType e_type;
	
	private java.net.DatagramSocket o_datagramSocket;
	private java.net.MulticastSocket o_multicastSocket;
	
	private java.net.Socket o_socket;
	private java.net.ServerSocket o_serverSocket;
	
	private javax.net.ssl.SSLSocket o_sslSocket;
	private javax.net.ssl.SSLServerSocket o_sslServerSocket;
	
	/* Properties */
	
	/**
	 * get type
	 * 
	 * @return WrapperType
	 */
	public WrapperType getType() {
		return this.e_type;
	}
	
	/**
	 * get socket
	 * 
	 * @return java socket class
	 */
	@SuppressWarnings("unchecked")
	public T getSocket() {
		if (this.e_type == WrapperType.UDP_CLIENT) {
			return (T) this.o_datagramSocket;
		} else if (this.e_type == WrapperType.UDP_SERVER) {
			return (T) this.o_datagramSocket;
		} else if (this.e_type == WrapperType.TCP_CLIENT) {
			return (T) this.o_socket;
		} else if (this.e_type == WrapperType.TCP_SERVER) {
			return (T) this.o_serverSocket;
		} else if (this.e_type == WrapperType.TCP_TLS_CLIENT) {
			return (T) this.o_sslSocket;
		} else if (this.e_type == WrapperType.TCP_TLS_SERVER) {
			return (T) this.o_sslServerSocket;	
		} else if (this.e_type == WrapperType.UDP_MULTICAST_SENDER) {
			return (T) this.o_multicastSocket;
		} else if (this.e_type == WrapperType.UDP_MULTICAST_RECEIVER) {
			return (T) this.o_multicastSocket;	
		}
		
		return null;
	}
	
	/* Methods */
	
	/**
	 * Create socket instance with wrapper class, no own ssl context
	 * 
	 * @param p_o_classType										java socket class parameter, supported are: 
	 * 																java.net.DatagramSocket.class,
	 * 																java.net.Socket.class,
	 * 																java.net.ServerSocket.class,
	 * 																javax.net.ssl.SSLSocket.class,
	 * 																javax.net.ssl.SSLServerSocket.class
	 * @param p_e_type											socket wrapper type, see net.forestany.forestj.lib.net.sock.WrapperType
	 * @throws IllegalArgumentException							invalid class type parameter, or socket address parameter for UDP socket client instance
	 * @throws java.net.SocketException							issue creating UDP socket instance
	 * @throws java.io.IOException								issue creating TCP or TCP with TLS socket instance
	 * @throws java.security.NoSuchAlgorithmException			issue accessing javax.net.ssl.SSLContext.getDefault() for creating a TCP with TLS socket instance
	 * 
	 */
	public Wrapper(Class<T> p_o_classType, WrapperType p_e_type) throws IllegalArgumentException, java.net.SocketException, java.io.IOException, java.security.NoSuchAlgorithmException {
		this(p_o_classType, p_e_type, null, null);
	}
	
	/**
	 * Create socket instance with wrapper class, no own ssl context
	 * 
	 * @param p_o_classType										java socket class parameter, supported are: 
	 * 																java.net.DatagramSocket.class,
	 * 																java.net.Socket.class,
	 * 																java.net.ServerSocket.class,
	 * 																javax.net.ssl.SSLSocket.class,
	 * 																javax.net.ssl.SSLServerSocket.class
	 * @param p_e_type											socket wrapper type, see net.forestany.forestj.lib.net.sock.WrapperType
	 * @param p_o_socketAddress									socket address object for UDP socket client instance
	 * @throws IllegalArgumentException							invalid class type parameter, or socket address parameter for UDP socket client instance
	 * @throws java.net.SocketException							issue creating UDP socket instance
	 * @throws java.io.IOException								issue creating TCP or TCP with TLS socket instance
	 * @throws java.security.NoSuchAlgorithmException			issue accessing javax.net.ssl.SSLContext.getDefault() for creating a TCP with TLS socket instance
	 * 
	 */
	public Wrapper(Class<T> p_o_classType, WrapperType p_e_type, java.net.SocketAddress p_o_socketAddress) throws IllegalArgumentException, java.net.SocketException, java.io.IOException, java.security.NoSuchAlgorithmException {
		this(p_o_classType, p_e_type, p_o_socketAddress, null);
	}
	
	/**
	 * Create socket instance with wrapper class
	 * 
	 * @param p_o_classType										java socket class parameter, supported are: 
	 * 																java.net.DatagramSocket.class,
	 * 																java.net.Socket.class,
	 * 																java.net.ServerSocket.class,
	 * 																javax.net.ssl.SSLSocket.class,
	 * 																javax.net.ssl.SSLServerSocket.class
	 * @param p_e_type											socket wrapper type, see net.forestany.forestj.lib.net.sock.WrapperType
	 * @param p_o_sslContext									own instance of ssl context to create a tls server or client socket instance
	 * @throws IllegalArgumentException							invalid class type parameter, or socket address parameter for UDP socket client instance
	 * @throws java.net.SocketException							issue creating UDP socket instance
	 * @throws java.io.IOException								issue creating TCP or TCP with TLS socket instance
	 * @throws java.security.NoSuchAlgorithmException			issue accessing javax.net.ssl.SSLContext.getDefault() for creating a TCP with TLS socket instance
	 * 
	 */
	public Wrapper(Class<T> p_o_classType, WrapperType p_e_type, javax.net.ssl.SSLContext p_o_sslContext) throws IllegalArgumentException, java.net.SocketException, java.io.IOException, java.security.NoSuchAlgorithmException {
		this(p_o_classType, p_e_type, null, p_o_sslContext);
	}
	
	/**
	 * Create socket instance with wrapper class
	 * 
	 * @param p_o_classType										java socket class parameter, supported are: 
	 * 																java.net.DatagramSocket.class,
	 * 																java.net.Socket.class,
	 * 																java.net.ServerSocket.class,
	 * 																javax.net.ssl.SSLSocket.class,
	 * 																javax.net.ssl.SSLServerSocket.class
	 * @param p_e_type											socket wrapper type, see net.forestany.forestj.lib.net.sock.WrapperType
	 * @param p_o_socketAddress									socket address object for UDP socket client instance
	 * @param p_o_sslContext									own instance of ssl context to create a tls server or client socket instance
	 * @throws IllegalArgumentException							invalid class type parameter, or socket address parameter for UDP socket client instance
	 * @throws java.net.SocketException							issue creating UDP socket instance
	 * @throws java.io.IOException								issue creating TCP or TCP with TLS socket instance
	 * @throws java.security.NoSuchAlgorithmException			issue accessing javax.net.ssl.SSLContext.getDefault() for creating a TCP with TLS socket instance
	 * 
	 */
	public Wrapper(Class<T> p_o_classType, WrapperType p_e_type, java.net.SocketAddress p_o_socketAddress, javax.net.ssl.SSLContext p_o_sslContext) throws IllegalArgumentException, java.net.SocketException, java.io.IOException, java.security.NoSuchAlgorithmException {
		this.o_classType = p_o_classType;
		this.e_type = p_e_type;
		
		if (this.e_type == WrapperType.UDP_CLIENT) {
			/* check class type parameter */
			if (this.o_classType != java.net.DatagramSocket.class) {
				throw new IllegalArgumentException("Expected [" + java.net.DatagramSocket.class.getName() + "] class object for socket wrapper");
			}
			
			/* check socket address parameter for UDP socket client instance */
			if (p_o_socketAddress == null) {
				throw new IllegalArgumentException("Socket address not specified. Object is null");
			}
			
			/* create socket instance */
			this.o_datagramSocket = new java.net.DatagramSocket(null);
		} else if (this.e_type == WrapperType.UDP_SERVER) {
			/* check class type parameter */
			if (this.o_classType != java.net.DatagramSocket.class) {
				throw new IllegalArgumentException("Expected [" + java.net.DatagramSocket.class.getName() + "] class object for socket wrapper");
			}
			
			/* create socket instance */
			this.o_datagramSocket = new java.net.DatagramSocket(p_o_socketAddress);
		} else if (this.e_type == WrapperType.TCP_CLIENT) {
			/* check class type parameter */
			if (this.o_classType != java.net.Socket.class) {
				throw new IllegalArgumentException("Expected [" + java.net.Socket.class.getName() + "] class object for socket wrapper");
			}
			
			/* create socket instance */
			this.o_socket = javax.net.SocketFactory.getDefault().createSocket();
		} else if (this.e_type == WrapperType.TCP_SERVER) {
			/* check class type parameter */
			if (this.o_classType != java.net.ServerSocket.class) {
				throw new IllegalArgumentException("Expected [" + java.net.ServerSocket.class.getName() + "] class object for socket wrapper");
			}
			
			/* create socket instance */
			this.o_serverSocket =  javax.net.ServerSocketFactory.getDefault().createServerSocket();
		} else if (this.e_type == WrapperType.TCP_TLS_CLIENT) {
			/* check class type parameter */
			if (this.o_classType != javax.net.ssl.SSLSocket.class) {
				throw new IllegalArgumentException("Expected [" + javax.net.ssl.SSLSocket.class.getName() + "] class object for socket wrapper");
			}
			
			if (p_o_sslContext == null) {
				/* create socket instance */
				this.o_sslSocket = (javax.net.ssl.SSLSocket) javax.net.ssl.SSLContext.getDefault().getSocketFactory().createSocket();
			} else {
				/* create socket instance with ssl context parameter */
				this.o_sslSocket = (javax.net.ssl.SSLSocket) p_o_sslContext.getSocketFactory().createSocket();
			}
		} else if (this.e_type == WrapperType.TCP_TLS_SERVER) {
			/* check class type parameter */
			if (this.o_classType != javax.net.ssl.SSLServerSocket.class) {
				throw new IllegalArgumentException("Expected [" + javax.net.ssl.SSLServerSocket.class.getName() + "] class object for socket wrapper");
			}
			
			/* check ssl context parameter */
			if (p_o_sslContext == null) {
				throw new IllegalArgumentException("SSL context not specified. Object is null");
			}
			
			/* create socket instance with ssl context parameter */
			this.o_sslServerSocket = (javax.net.ssl.SSLServerSocket) p_o_sslContext.getServerSocketFactory().createServerSocket();
			/* further security settings */
			this.o_sslServerSocket.setEnabledProtocols(new String[] {"TLSv1.3"});
			this.o_sslServerSocket.setEnabledCipherSuites(new String[] {"TLS_AES_128_GCM_SHA256"});
		} else if (this.e_type == WrapperType.UDP_MULTICAST_SENDER) {
			/* check class type parameter */
			if (this.o_classType != java.net.MulticastSocket.class) {
				throw new IllegalArgumentException("Expected [" + java.net.MulticastSocket.class.getName() + "] class object for socket wrapper");
			}
			
			/* create socket instance */
			this.o_multicastSocket = new java.net.MulticastSocket();
		} else if (this.e_type == WrapperType.UDP_MULTICAST_RECEIVER) {
			/* check class type parameter */
			if (this.o_classType != java.net.MulticastSocket.class) {
				throw new IllegalArgumentException("Expected [" + java.net.MulticastSocket.class.getName() + "] class object for socket wrapper");
			}
			
			/* check socket address parameter for UDP socket client instance */
			if (p_o_socketAddress == null) {
				throw new IllegalArgumentException("Socket address not specified. Object is null");
			}
			
			/* create socket instance */
			this.o_multicastSocket = new java.net.MulticastSocket(((java.net.InetSocketAddress)p_o_socketAddress).getPort());
		}
	}
	
	/**
	 * Fast constructor by already giving the TCP client socket instance as parameter
	 * 
	 * @param p_o_socket		socket instance for TCP client, must be of type java.net.Socket
	 */
	@SuppressWarnings("unchecked")
	public Wrapper(java.net.Socket p_o_socket) {
		/* set class type and wrapper type */
		this.o_classType = (Class<T>) java.net.Socket.class;
		this.e_type = WrapperType.TCP_CLIENT;
		/* set socket instance from parameter */
		this.o_socket = p_o_socket;
	}
	
	/**
	 * Fast constructor by already giving the TCP client with TLS socket instance as parameter
	 * 
	 * @param p_o_sslSocket		socket instance for TCP client with TLS, must be of type javax.net.ssl.SSLSocket
	 */
	@SuppressWarnings("unchecked")
	public Wrapper(javax.net.ssl.SSLSocket p_o_sslSocket) {
		/* set class type and wrapper type */
		this.o_classType = (Class<T>) javax.net.ssl.SSLSocket.class;
		this.e_type = WrapperType.TCP_TLS_CLIENT;
		/* set socket instance from parameter */
		this.o_sslSocket = p_o_sslSocket;
	}
}
