package net.forestany.forestj.lib.net.sock;

/**
 * Abstract socket class for creating all kind of sockets within forestJ-lib framework. Implements Runnable class, because any socket will run in its own thread.
 * General class properties for all kind of sockets available.
 * 
 * @param <T> java socket class parameter, supported are: 
 * 					java.net.DatagramSocket.class,
 * 					java.net.Socket.class,
 * 					java.net.ServerSocket.class,
 * 					javax.net.ssl.SSLSocket.class,
 * 					javax.net.ssl.SSLServerSocket.class,
 * 					java.net.MulticastSocket.class
 */
public abstract class Socket<T> implements Runnable {
	
	/* Fields */
	
	/**
	 * stop
	 */
	protected boolean b_stop;
	/**
	 * timeout milliseconds
	 */
	protected int i_timeoutMilliseconds;
	/**
	 * buffer size
	 */
	protected int i_bufferSize;
	/**
	 * max terminations
	 */
	protected int i_maxTerminations;
	/**
	 * terminations
	 */
	protected int i_terminations;
	/**
	 * class type
	 */
	protected Class<T> o_classType;
	/**
	 * socket wrapper
	 */
	protected Wrapper<T> o_socketWrapper;
	/**
	 * ssl context
	 */
	protected javax.net.ssl.SSLContext o_sslContext;
	/**
	 * socket address
	 */
	protected java.net.SocketAddress o_socketAddress;
	/**
	 * socket task
	 */
	protected net.forestany.forestj.lib.net.sock.task.Task<T> o_socketTask;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * empty constructor
	 */
	public Socket() {
		
	}
	
	/**
	 * Core execution process method of a socket. Either receiving or sending network data as server or client.
	 */
	@Override
	abstract public void run();
	
	/**
	 * This method stops the socket and ends any network communication.
	 */
	abstract public void stop();
}
