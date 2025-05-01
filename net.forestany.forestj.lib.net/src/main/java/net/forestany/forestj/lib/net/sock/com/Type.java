package net.forestany.forestj.lib.net.sock.com;

/**
 * All supported communication types for network communication within the framework.
 * 
 * UDP_SEND							sending UDP data.
 * UDP_RECEIVE						receiving UDP data.
 * UDP_SEND_WITH_ACK				sending UDP data and receiving an acknowledge UDP datagram after send.
 * UDP_RECEIVE_WITH_ACK				receiving UDP data and sending an acknowledge UDP datagram after receive.
 * TCP_SEND							sending TCP data.
 * TCP_RECEIVE						receiving TCP data.
 * TCP_SEND_WITH_ANSWER				sending TCP data and expect a direct answer.
 * TCP_RECEIVE_WITH_ANSWER			receiving TCP data and send a direct answer.
 * UDP_MULTICAST_SENDER				sending UDP data over multicast.
 * UDP_MULTICAST_RECEIVER			receiving UDP data over multicast.
 */
public enum Type {
	/**
	 * UDP_SEND type
	 */
	UDP_SEND,
	/**
	 * UDP_RECEIVE type
	 */
	UDP_RECEIVE,
	/**
	 * UDP_SEND_WITH_ACK type
	 */
	UDP_SEND_WITH_ACK,
	/**
	 * UDP_RECEIVE_WITH_ACK type
	 */
	UDP_RECEIVE_WITH_ACK,
	/**
	 * TCP_SEND type
	 */
	TCP_SEND,
	/**
	 * TCP_RECEIVE type
	 */
	TCP_RECEIVE,
	/**
	 * TCP_SEND_WITH_ANSWER type
	 */
	TCP_SEND_WITH_ANSWER,
	/**
	 * TCP_RECEIVE_WITH_ANSWER type
	 */
	TCP_RECEIVE_WITH_ANSWER,
	/**
	 * UDP_MULTICAST_SENDER type
	 */
	UDP_MULTICAST_SENDER,
	/**
	 * UDP_MULTICAST_RECEIVER type
	 */
	UDP_MULTICAST_RECEIVER
}
