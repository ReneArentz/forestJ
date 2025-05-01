package net.forestany.forestj.lib.net.sock;

/**
 * All supported wrapper types of sockets.
 * 
 * UDP_SERVER
 * UDP_CLIENT
 * TCP_SERVER
 * TCP_CLIENT
 * TCP_TLS_SERVER
 * TCP_TLS_CLIENT
 * UDP_MULTICAST_SENDER
 * UDP_MULTICAST_RECEIVER
 */

public enum WrapperType {
	/**
	 * UDP_SERVER wrapper type
	 */
	UDP_SERVER,
	/**
	 * UDP_CLIENT wrapper type
	 */
	UDP_CLIENT,
	/**
	 * TCP_SERVER wrapper type
	 */
	TCP_SERVER,
	/**
	 * TCP_CLIENT wrapper type
	 */
	TCP_CLIENT,
	/**
	 * TCP_TLS_SERVER wrapper type
	 */
	TCP_TLS_SERVER,
	/**
	 * TCP_TLS_CLIENT wrapper type
	 */
	TCP_TLS_CLIENT,
	/**
	 * UDP_MULTICAST_SENDER wrapper type
	 */
	UDP_MULTICAST_SENDER,
	/**
	 * UDP_MULTICAST_RECEIVER wrapper type
	 */
	UDP_MULTICAST_RECEIVER
}
