package net.forestany.forestj.lib.net.sock.task.recv;

/**
 * Generic task class to receive network traffic over a socket instance. Several methods supporting UDP and TCP, receiving data in combination with a message box or multiple message boxes.
 *
 * @param <T>	java socket class parameter
 */
public class Receive<T> extends net.forestany.forestj.lib.net.sock.task.Task<T> {
	/**
	 * standard constructor
	 */
	public Receive() {
		
	}
	
	/**
	 * Creating receiving socket task instance with all it's parameters and settings
	 * 
	 * @param p_e_communicationType					specifies communication type of socket task
	 * @param p_e_communicationCardinality			specifies communication cardinality of socket task
	 * @param p_i_queueTimeoutMilliseconds			determine timeout in milliseconds for sending/receiving bytes
	 * @throws IllegalArgumentException				invalid timeout value for queue
	 */
	public Receive(net.forestany.forestj.lib.net.sock.com.Type p_e_communicationType, net.forestany.forestj.lib.net.sock.com.Cardinality p_e_communicationCardinality, int p_i_queueTimeoutMilliseconds) throws IllegalArgumentException {
		super(p_e_communicationType, p_e_communicationCardinality, p_i_queueTimeoutMilliseconds);
	}
	
	/**
	 * Creating receiving socket task instance with all it's parameters and settings
	 * 
	 * @param p_e_communicationType					specifies communication type of socket task
	 * @param p_e_communicationCardinality			specifies communication cardinality of socket task
	 * @param p_i_queueTimeoutMilliseconds			determine timeout in milliseconds for sending/receiving bytes
	 * @param p_e_communicationSecurity				specifies communication security of socket task
	 * 
	 * @throws IllegalArgumentException				invalid timeout value for queue
	 */
	public Receive(net.forestany.forestj.lib.net.sock.com.Type p_e_communicationType, net.forestany.forestj.lib.net.sock.com.Cardinality p_e_communicationCardinality, int p_i_queueTimeoutMilliseconds, net.forestany.forestj.lib.net.sock.com.Security p_e_communicationSecurity) throws IllegalArgumentException {
		super(p_e_communicationType, p_e_communicationCardinality, p_i_queueTimeoutMilliseconds, p_e_communicationSecurity);
	}
	
	/**
	 * Creating receiving socket task instance with all it's parameters and settings
	 * 
	 * @param p_e_communicationType					specifies communication type of socket task
	 * @param p_e_communicationCardinality			specifies communication cardinality of socket task
	 * @param p_i_queueTimeoutMilliseconds			determine timeout in milliseconds for sending/receiving bytes
	 * @param p_e_communicationSecurity				specifies communication security of socket task
	 * @param p_b_useMarshalling					true - use marshalling methods to transport data over network
	 * @param p_i_marshallingDataLengthInBytes		set data length in bytes for marshalling, must be between [1..4]
	 * @param p_b_marshallingUsePropertyMethods		true - access object parameter fields via property methods e.g. T getXYZ()
	 * @param p_s_marshallingOverrideMessageType    override message type with this string and do not get it automatically from object, thus the type can be set generally from other systems with other programming languages
	 * @param p_b_marshallingSystemUsesLittleEndian	(NOT IMPLEMENTED) true - current execution system uses little endian, false - current execution system uses big endian
	 * @throws IllegalArgumentException				invalid timeout value for queue
	 */
	public Receive(net.forestany.forestj.lib.net.sock.com.Type p_e_communicationType, net.forestany.forestj.lib.net.sock.com.Cardinality p_e_communicationCardinality, int p_i_queueTimeoutMilliseconds, net.forestany.forestj.lib.net.sock.com.Security p_e_communicationSecurity, boolean p_b_useMarshalling, int p_i_marshallingDataLengthInBytes, boolean p_b_marshallingUsePropertyMethods, String p_s_marshallingOverrideMessageType, boolean p_b_marshallingSystemUsesLittleEndian) throws IllegalArgumentException {
		super(p_e_communicationType, p_e_communicationCardinality, p_i_queueTimeoutMilliseconds, p_e_communicationSecurity, null, p_b_useMarshalling, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_s_marshallingOverrideMessageType, p_b_marshallingSystemUsesLittleEndian);
	}
	
	/**
	 * method to get socket task class type for creating new instances with declared constructor
	 * @return		class type of socket task as Class&lt;?&gt;
	 */
	public Class<?> getSocketTaskClassType() {
		return net.forestany.forestj.lib.net.sock.task.recv.Receive.class;
	}
	
	/**
	 * method to clone this socket task with another socket task instance
	 * @param p_o_sourceTask		another socket task instance as source for all it's parameters and settings
	 */
	public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<T> p_o_sourceTask) {
		this.cloneBasicFields(p_o_sourceTask);
	}
	
	/**
	 * runTask method of receiving socket task which can always vary depending on the implementation. Supporting in this class UDP_RECEIVE, UDP_RECEIVE_WITH_ACK, TCP_RECEIVE and TCP_RECEIVE_WITH_ANSWER.
	 * 
	 * @throws Exception	any exception of implementation that could happen will be caught by abstract Task class, see details in protocol methods in net.forestany.forestj.lib.net.sock.task.recv.Receive
	 */
	protected void runTask() throws Exception {
		if ( (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE) || (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE_WITH_ACK) || (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_MULTICAST_RECEIVER) ) {
			/* receive UDP packets */
			this.udpReceive();
		} else if (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE) {
			/* check if we want to use tcp socket for bidirectional communication */
			if (this.getCommunicationCardinality() == net.forestany.forestj.lib.net.sock.com.Cardinality.EqualBidirectional) {
				this.tcpBidirectional();
			} else {
				if (this.b_objectTransmission) {
					/* receive TCP packets, but a whole object, so several messages of a message box until the object has been transferred */
					this.tcpReceiveObjectTransmission();
				} else {
					/* receive TCP packet */
					this.tcpReceive();
				}
			}
		} else if (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE_WITH_ANSWER) {
			/* receive TCP packet and answer immediately with receiveSocketTask property */
			this.tcpReceiveWithAnswer();
		}
	}
	
	/**
	 * Method for handling a received UDP datagram packet, optional with sending an acknowledge(ACK) over UDP which is not UDP standard but could be useful sometimes
	 * 
	 * @throws Exception											any other Exception, see below in other classes for details
	 * @see java.lang.Thread										java.lang.Thread - sleep method
	 * @see net.forestany.forestj.lib.Cryptography								net.forestany.forestj.lib.Cryptography - Decrypt_AES_GCM and Decrypt methods
	 * @see net.forestany.forestj.lib.net.msg.Message							net.forestany.forestj.lib.net.msg.Message - Constructor and SetMessageFromByteArray methods
	 */
	private void udpReceive() throws Exception {
												net.forestany.forestj.lib.Global.ilogFiner("Received datagram packet, length = " + this.o_datagramPacket.getLength() + " bytes");
		
		/* if we received no data we have no message information as well */
		if (this.o_datagramPacket.getLength() < 1) {
			return;
		}
												
		/* communication type requires to send an acknowledge(ACK) over UDP */
		if (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE_WITH_ACK) {
			/* we wait some time so the other side of communication can prepare to receive UDP ACK */
			Thread.sleep(this.i_udpSendACKTimeoutMilliseconds);
			
													net.forestany.forestj.lib.Global.ilogFiner("Received data, now sendACK(" + this.o_datagramPacket.getAddress().getHostAddress() + ", " + this.o_datagramPacket.getPort() + ")");
			
			/* send UDP ACK to datagram source address and source port */
			this.sendUdpAck(this.o_datagramPacket.getAddress().getHostAddress(), this.o_datagramPacket.getPort());
		}
		
		/* get message length */
		int i_length = this.o_datagramPacket.getLength();
		/* get message data */
		byte[] a_bytes = this.o_datagramPacket.getData();
		
		/* our buffer expects fewer bytes than we received */
		if (this.getBufferLength() < a_bytes.length) {
													net.forestany.forestj.lib.Global.ilogFiner("Our buffer expects fewer bytes than we received: '" + this.getBufferLength() + "' < '" + a_bytes.length + "'");
			
			/* new byte array with expected buffer length */
			byte[] a_foo = new byte[this.getBufferLength()];
			
			/* copy byte array data */
			for (int i = 0; i < a_foo.length; i++) {
				a_foo[i] = a_bytes[i];
			}
			
													net.forestany.forestj.lib.Global.ilogFiner("Only read '" + a_foo.length + "' bytes of received data");
			
			a_bytes = a_foo;
		}
		
		/* decrypt received bytes if encryption is active */
		if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) {
			a_bytes = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_bytes, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
			i_length = a_bytes.length;
		} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) {
			a_bytes = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_bytes, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
			i_length = a_bytes.length;
		} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
			a_bytes = this.o_cryptography.decrypt(a_bytes);
			i_length = a_bytes.length;
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("received data (optional already decrypted), data length = " + a_bytes.length + " bytes");
												
		/* create message object with message length and message data */
		net.forestany.forestj.lib.net.msg.Message o_message = new net.forestany.forestj.lib.net.msg.Message(i_length);
		o_message.setMessageFromByteArray(a_bytes);
		
												net.forestany.forestj.lib.Global.ilogFine("Received message from[" + this.o_datagramPacket.getSocketAddress().toString() + "]: message number[" + o_message.getMessageNumber() + "] of [" + o_message.getMessageAmount() + "] with length[" + o_message.getMessageLength() + "], data length[" + o_message.getDataLength() + "], data block length[" + o_message.getDataBlockLength() + "], message box id[" + o_message.getMessageBoxId() + "]");
		
		/* debug received message if b_debugNetworkTrafficOn is set to true */
		this.debugMessage(a_bytes, o_message);
		
		int i_messageBoxId = 0;
		
		/* determine message box id if we have a cardinality with many message boxes and one socket */
		if (this.e_communicationCardinality == net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket) {
			i_messageBoxId = o_message.getMessageBoxId();
			
			/* check message box id min. value */
			if (i_messageBoxId < 1) {
				throw new IllegalArgumentException("Invalid receiving message. Message box id[" + i_messageBoxId + "] must be greater than [0].");
			}
			
			/* check message box id max. value */
			if (i_messageBoxId > this.a_messageBoxes.size()) {
				throw new IllegalArgumentException("Invalid receiving message. Message box id[" + i_messageBoxId + "] is not available.");
			}
			
			i_messageBoxId--;
		}
		
		/* enqueue message object */
		while (!this.a_messageBoxes.get(i_messageBoxId).enqueueMessage(o_message)) {
													net.forestany.forestj.lib.Global.ilogWarning("Could not enqueue message object, timeout for '" + this.i_queueTimeoutMilliseconds + "' milliseconds");
			
			/* wait queue timeout length to enqueue message object again */
			Thread.sleep(this.i_queueTimeoutMilliseconds);
		};
	}
	
	/**
	 * Send UDP ACK to datagram source address and source port. All exceptions will be handled within this method and are not thrown to parent methods. 
	 * 
	 * @param p_s_udpSendACKAddress				destination address for UDP ACK
	 * @param p_i_udpSendACKPort				destination port for UDP ACK
	 */
	private void sendUdpAck(String p_s_udpSendACKAddress, int p_i_udpSendACKPort) {
		try {
			/* check UDP socket send port min. value */
			if (p_i_udpSendACKPort < 1) {
				throw new IllegalArgumentException("UDP send ACK port must be at least '1', but was set to '" + p_i_udpSendACKPort + "'");
			}
			
			/* check UDP socket send port max. value */
			if (p_i_udpSendACKPort > 65535) {
				throw new IllegalArgumentException("UDP send ACK port must be lower equal '65535', but was set to '" + p_i_udpSendACKPort + "'");
			}
			
			/* create UDP socket for sending ACK */
			java.net.DatagramSocket o_sendSocket = new java.net.DatagramSocket();
			
			try {
				/* set destination address to source of received UDP datagram packet */
				java.net.InetSocketAddress o_sendSocketAddress = new java.net.InetSocketAddress(p_s_udpSendACKAddress, p_i_udpSendACKPort);
				
				/* prepare ACK byte of abstract Task class */
				byte[] a_bytes = new byte[] {(byte)net.forestany.forestj.lib.net.sock.task.Task.BY_ACK_BYTE};
				
				/* encrypt acknowledge byte if encryption is active */
				if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) {
					a_bytes = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(a_bytes, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
				} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) {
					a_bytes = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(a_bytes, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
				} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
					a_bytes = this.o_cryptography.encrypt(a_bytes);
				}
				
				/* set buffer size to one byte */
				o_sendSocket.setReceiveBufferSize(a_bytes.length);
				o_sendSocket.setSendBufferSize(a_bytes.length);
				
				/* create datagram packet object with ACK byte and destination address */
				java.net.DatagramPacket o_datagramPacket = new java.net.DatagramPacket(a_bytes, a_bytes.length, o_sendSocketAddress);
				/* send datagram packet with UDP socket object */
				o_sendSocket.send(o_datagramPacket);
				
														net.forestany.forestj.lib.Global.ilogFiner("sent UDP ACK o_sendSocket.send()");
			} catch (java.io.IOException o_exc) {
				net.forestany.forestj.lib.Global.logException("java.io.IOException Receive-sendUdpAck method: ", o_exc);
			} finally {
				/* close UDP socket object */
				if ( (o_sendSocket != null) && (!o_sendSocket.isClosed()) ) {
					o_sendSocket.close();
				}
			}
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException("Exception Receive-sendUdpAck method: ", o_exc);
		}
	}
	
	/**
	 * Method for handling receiving bytes from a TCP connection, until all data is gathered for a whole object
	 * 
	 * @throws IllegalStateException								amount of data is not a multiple of buffer length
	 * @throws Exception											any other Exception, see below in other classes for details
	 * @see java.lang.Thread										java.lang.Thread - sleep method
	 * @see net.forestany.forestj.lib.Cryptography								net.forestany.forestj.lib.Cryptography - Decrypt_AES_GCM and Decrypt methods
	 * @see net.forestany.forestj.lib.net.msg.Message							net.forestany.forestj.lib.net.msg.Message - Constructor and Message methods
	 * @see net.forestany.forestj.lib.net.msg.Messagebox							net.forestany.forestj.lib.net.msg.MessageBox - Accessing MessageBox queue
	 */
	private void tcpReceiveObjectTransmission() throws IllegalStateException, Exception {
												net.forestany.forestj.lib.Global.ilogFiner("Amount bytes protocol, starting");
		
		/* use AmountBytesProtocol to know how many bytes and in that way how many messages we are expecting */
		int i_amountBytes = this.amountBytesProtocol();			
		
												net.forestany.forestj.lib.Global.ilogFiner("Amount bytes protocol, finished, length = " + i_amountBytes + " bytes");
		
		/* no encryption active */
		if ( (this.e_communicationSecurity != net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) && (this.e_communicationSecurity != net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) && (this.e_communicationSecurity != net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) && (this.e_communicationSecurity != net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
			/* check if amount of data is not a multiple of buffer length */
			if ((i_amountBytes % this.i_bufferLength) != 0) {
				throw new IllegalStateException("Invalid amount of data[" + i_amountBytes + "]. Amount of data is not a multiple of buffer length[" + this.i_bufferLength + "].");
			}
		}
		
		/* receive data bytes of all expected messages for object */
		byte[] a_receivedData = this.receiveBytes(i_amountBytes);
		
		/* if we received no data we have no message information as well */
		if (a_receivedData.length < 1) {
			return;
		}
		
		/* decrypt received bytes if encryption is active */
		if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) {
			a_receivedData = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_receivedData, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
		} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) {
			a_receivedData = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_receivedData, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
		} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
			a_receivedData = this.o_cryptography.decrypt(a_receivedData);
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("received data (optional already decrypted), length = " + a_receivedData.length + " bytes");
		
		int i_decreaseForEncryption = 0;
		
		/* check for length correction if encryption is active */
		if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
			if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) ) {
				i_decreaseForEncryption = 28;
			} else {
				i_decreaseForEncryption = 16;
			}
			
			/* check if amount of decrypted data is not a multiple of buffer length */
			if ((a_receivedData.length % (this.i_bufferLength - i_decreaseForEncryption)) != 0) {
				throw new IllegalStateException("Invalid amount of data[" + a_receivedData.length + "]. Amount of data is not a multiple of buffer length[" + (this.i_bufferLength - i_decreaseForEncryption) + "].");
			}
		}
		
		/* calculate how many messages we expect to receive until all object data has been gathered */
		int i_messages = a_receivedData.length / (this.i_bufferLength - i_decreaseForEncryption);
		int i_messageBoxId = 0;
		
												net.forestany.forestj.lib.Global.ilogFine("Received message from[" + ((java.net.Socket)this.o_socket.getSocket()).getRemoteSocketAddress().toString() + "]: messages[" + i_messages + "], length[" + a_receivedData.length + "]");
		
		/* receive other expected messages until all object data has been gathered */
		for (int i = 0; i < i_messages; i++) {
			/* create message data array */
			byte[] a_messageData = new byte[(this.i_bufferLength - i_decreaseForEncryption)];
			
			/* copy received data into message data array */
			for (int i_bytePointer = 0; i_bytePointer < (this.i_bufferLength - i_decreaseForEncryption); i_bytePointer++) {
				a_messageData[i_bytePointer] = a_receivedData[i_bytePointer + (i * (this.i_bufferLength - i_decreaseForEncryption))];
			}
			
			/* create message object with message length and message data */
			net.forestany.forestj.lib.net.msg.Message o_message = new net.forestany.forestj.lib.net.msg.Message((this.i_bufferLength - i_decreaseForEncryption));
			o_message.setMessageFromByteArray(a_messageData);
			
													net.forestany.forestj.lib.Global.ilogFine("Received message number[" + o_message.getMessageNumber() + "] of [" + o_message.getMessageAmount() + "] with length[" + o_message.getMessageLength() + "], data length[" + o_message.getDataLength() + "], data block length[" + o_message.getDataBlockLength() + "], message box id[" + o_message.getMessageBoxId() + "]");
			
			/* debug received message if b_debugNetworkTrafficOn is set to true */
			this.debugMessage(a_messageData, o_message);
			
			i_messageBoxId = 0;
			
			/* determine message box id if we have a cardinality with many message boxes and one socket */
			if (this.e_communicationCardinality == net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket) {
				i_messageBoxId = o_message.getMessageBoxId();
				
				/* check message box id min. value */
				if (i_messageBoxId < 1) {
					throw new IllegalArgumentException("Invalid receiving message. Message box id[" + i_messageBoxId + "] must be greater than [0].");
				}
				
				/* check message box id max. value */
				if (i_messageBoxId > this.a_messageBoxes.size()) {
					throw new IllegalArgumentException("Invalid receiving message. Message box id[" + i_messageBoxId + "] is not available.");
				}
				
				i_messageBoxId--;
			}
			
			/* enqueue message object */
			while (!this.a_messageBoxes.get(i_messageBoxId).enqueueMessage(o_message)) {
														net.forestany.forestj.lib.Global.ilogWarning("Could not enqueue message, timeout for '" + this.i_queueTimeoutMilliseconds + "' milliseconds");
				
				/* wait queue timeout length to enqueue message object again */
				Thread.sleep(this.i_queueTimeoutMilliseconds);
			};
		}
		
		/* with asymmetric encryption we have to close our ssl socket ourselves */
		if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.ASYMMETRIC) && (!((java.net.Socket)this.o_socket.getSocket()).isClosed()) ) {
			((java.net.Socket)this.o_socket.getSocket()).close();
		}
	}
	
	/**
	 * Method for receiving an object from a TCP connection, until all data is gathered and an answer can be send with separate receive socket task
	 * 
	 * @throws IllegalStateException								amount of data is not a multiple of buffer length
	 * @throws Exception											any other Exception, see below in other classes for details, but also any Exception within receive socket task or casting request object and/or answer object
	 * @see java.lang.Thread										java.lang.Thread - sleep method
	 * @see net.forestany.forestj.lib.Cryptography								net.forestany.forestj.lib.Cryptography - Decrypt_AES_GCM and Decrypt methods
	 * @see net.forestany.forestj.lib.net.msg.Message							net.forestany.forestj.lib.net.msg.Message - Constructor and Message methods
	 * @see net.forestany.forestj.lib.net.msg.Messagebox							net.forestany.forestj.lib.net.msg.MessageBox - Accessing MessageBox queue
	 */
	private void tcpReceiveWithAnswer() throws Exception {
												net.forestany.forestj.lib.Global.ilogFiner("Amount bytes protocol, starting");
		
		/* use AmountBytesProtocol to know how many bytes and in that way how many messages we are expecting */
		int i_amountBytes = this.amountBytesProtocol();			
		
												net.forestany.forestj.lib.Global.ilogFiner("Amount bytes protocol, finished, length = " + i_amountBytes + " bytes");
		
		/* no encryption active */
		if ( (this.e_communicationSecurity != net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) && (this.e_communicationSecurity != net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) && (this.e_communicationSecurity != net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) && (this.e_communicationSecurity != net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
			/* check if amount of data is not a multiple of buffer length */
			if ((i_amountBytes % this.i_bufferLength) != 0) {
				throw new IllegalStateException("Invalid amount of data[" + i_amountBytes + "]. Amount of data is not a multiple of buffer length[" + this.i_bufferLength + "].");
			}
		}
		
		/* receive data bytes of all expected messages for request object */
		byte[] a_receivedData = this.receiveBytes(i_amountBytes);
		
		/* if we received no data we have no message information as well */
		if (a_receivedData.length < 1) {
			return;
		}
		
		/* decrypt received bytes if encryption is active */
		if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) {
			a_receivedData = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_receivedData, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
		} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) {
			a_receivedData = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_receivedData, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
		} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
			a_receivedData = this.o_cryptography.decrypt(a_receivedData);
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("received data (optional already decrypted), length = " + a_receivedData.length + " bytes");
		
		int i_decreaseForEncryption = 0;
		
		/* check for length correction if encryption is active  */
		if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
			if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) ) {
				i_decreaseForEncryption = 28;
			} else {
				i_decreaseForEncryption = 16;
			}
			
			/* check if amount of decrypted data is not a multiple of buffer length */
			if ((a_receivedData.length % (this.i_bufferLength - i_decreaseForEncryption)) != 0) {
				throw new IllegalStateException("Invalid amount of data[" + a_receivedData.length + "]. Amount of data is not a multiple of buffer length[" + (this.i_bufferLength - i_decreaseForEncryption) + "].");
			}
		}
		
		/* help variables to create request object out of received data */
		Object o_object = null;
		String s_objectType = null;
		int i_objectDataLength = 0;
		java.util.List<Byte> a_bytes = new java.util.ArrayList<Byte>();
		
		/* calculate how many messages we expect to receive until all object data has been gathered */
		int i_messages = a_receivedData.length / (this.i_bufferLength - i_decreaseForEncryption);
		
												net.forestany.forestj.lib.Global.ilogFine("Received message from[" + ((java.net.Socket)this.o_socket.getSocket()).getRemoteSocketAddress().toString() + "]: messages[" + i_messages + "], length[" + a_receivedData.length + "]");
		
		/* receive other expected messages until all object data has been gathered */
		for (int i = 0; i < i_messages; i++) {
			/* create message data array */
			byte[] a_messageData = new byte[(this.i_bufferLength - i_decreaseForEncryption)];
			
			/* copy received data into message data array */
			for (int i_bytePointer = 0; i_bytePointer < (this.i_bufferLength - i_decreaseForEncryption); i_bytePointer++) {
				a_messageData[i_bytePointer] = a_receivedData[i_bytePointer + (i * (this.i_bufferLength - i_decreaseForEncryption))];
			}
			
			/* create message object with message length and message data */
			net.forestany.forestj.lib.net.msg.Message o_message = new net.forestany.forestj.lib.net.msg.Message((this.i_bufferLength - i_decreaseForEncryption));
			o_message.setMessageFromByteArray(a_messageData);
			/* get object type and increase object data length */
			s_objectType = o_message.getType();
			i_objectDataLength += o_message.getDataLength();
			
													net.forestany.forestj.lib.Global.ilogFiner("Received message number[" + o_message.getMessageNumber() + "] of [" + o_message.getMessageAmount() + "] with length[" + o_message.getMessageLength() + "], data length[" + o_message.getDataLength() + "], data block length[" + o_message.getDataBlockLength() + "], message box id[" + o_message.getMessageBoxId() + "]");
			
			/* debug received message if b_debugNetworkTrafficOn is set to true */
			this.debugMessage(a_messageData, o_message);
			
			/* gather bytes for request object */
			for (int j = 0; j < o_message.getDataLength(); j++) {
				a_bytes.add(o_message.getData()[j]);
			}
		}
		
		/* create byte array for request object */
		byte[] a_objectBytes = new byte[i_objectDataLength];
		int i = 0;
		
		/* copy received bytes to byte array for request object */
		for (byte by_byte : a_bytes) {
			a_objectBytes[i++] = by_byte;
		}
		
		/* check if we use marshalling for network communication */
		if (this.getUseMarshalling()) {
			/* get unmarshalled object from messages */
			o_object = net.forestany.forestj.lib.net.msg.MessageBox.unmarshallObjectFromMessage(s_objectType, a_objectBytes, this.getMarshallingUsePropertyMethods(), this.getMarshallingSystemUsesLittleEndian());
		} else { /* no marshalling for network communication */
			/* cast byte array to request object */
			try (
				java.io.ByteArrayInputStream o_byteArrayInputStream = new java.io.ByteArrayInputStream(a_objectBytes);
				java.io.ObjectInput o_objectInput = new java.io.ObjectInputStream(o_byteArrayInputStream);
			) {
				o_object = o_objectInput.readObject(); 
			} catch (Exception o_exc) {
				o_object = null;
			}
			
			/* only cast if serialized request object could be read */
			if (o_object != null) {
				try {
					/* cast request object by string object type value */
					 Class<?> o_class = Class.forName(s_objectType);
					 o_object = o_class.cast(o_object);
				} catch (ClassNotFoundException o_exc) {
					o_object = null;
				}
			}
		}
		
		/* check if cast was successful */
		if (o_object == null) {
			throw new NullPointerException("Could not deserialize and cast object from received bytes");
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("pass request object to receive socket task (user/developer implementation)");
		
		/* pass request object to receive socket task (user/developer implementation) */
		this.o_receiveSocketTask.setRequestObject(o_object);
		
												net.forestany.forestj.lib.Global.ilogFine("execute receive socket task (user/developer implementation)");
		
		/* execute receive socket task (user/developer implementation) */
		this.o_receiveSocketTask.run();
		
												net.forestany.forestj.lib.Global.ilogFiner("retrieve answer object of receive socket task (user/developer implementation)");
		
		/* retrieve answer object of receive socket task (user/developer implementation) */
		o_object = this.o_receiveSocketTask.getAnswerObject();
		
		/* check if answer object was retrieved successful */
		if (o_object == null) {
			throw new NullPointerException("Cannot send answer, answer object is null");
		}
		
		/* byte array for sending answer object to other communication side */
		byte[] a_answerBytes = null;
		byte[] a_answerObjectBytes = null;
		String s_type = "type is null";
		
												net.forestany.forestj.lib.Global.ilogFine("serialize answer object from receive socket task (user/developer implementation)");
		
		/* check if we use marshalling for network communication */
		if (this.getUseMarshalling()) {
			try {
				/* call marshall object method */
				a_answerObjectBytes = net.forestany.forestj.lib.net.msg.Marshall.marshallObject(o_object, this.getMarshallingDataLengthInBytes(), this.getMarshallingUsePropertyMethods(), this.getMarshallingSystemUsesLittleEndian());
				
				/* get universal primitive type name if available */
				if ( (o_object.getClass() == Boolean.class) || (o_object.getClass() == boolean.class) ) {
				    s_type = "bool";
				} else if ( (o_object.getClass().isArray()) && ( (o_object.getClass().getComponentType() == Boolean.class) || (o_object.getClass().getComponentType() == boolean.class) ) ) {
				    s_type = "bool[]";
				} else if ( (o_object.getClass() == Byte.class) || (o_object.getClass() == byte.class) ) {
				    s_type = "byte";
				} else if ( (o_object.getClass().isArray()) && ( (o_object.getClass().getComponentType() == Byte.class) || (o_object.getClass().getComponentType() == byte.class) ) ) {
				    s_type = "byte[]";
				} else if ( (o_object.getClass() == Character.class) || (o_object.getClass() == char.class) ) {
				    s_type = "char";
				} else if ( (o_object.getClass().isArray()) && ( (o_object.getClass().getComponentType() == Character.class) || (o_object.getClass().getComponentType() == char.class) ) ) {
				    s_type = "char[]";
				} else if ( (o_object.getClass() == Float.class) || (o_object.getClass() == float.class) ) {
				    s_type = "float";
				} else if ( (o_object.getClass().isArray()) && ( (o_object.getClass().getComponentType() == Float.class) || (o_object.getClass().getComponentType() == float.class) ) ) {
				    s_type = "float[]";
				} else if ( (o_object.getClass() == Double.class) || (o_object.getClass() == double.class) ) {
				    s_type = "double";
				} else if ( (o_object.getClass().isArray()) && ( (o_object.getClass().getComponentType() == Double.class) || (o_object.getClass().getComponentType() == double.class) ) ) {
				    s_type = "double[]";
				} else if ( (o_object.getClass() == Short.class) || (o_object.getClass() == short.class) ) {
				    s_type = "short";
				} else if ( (o_object.getClass().isArray()) && ( (o_object.getClass().getComponentType() == Short.class) || (o_object.getClass().getComponentType() == short.class) ) ) {
				    s_type = "short[]";
				} else if ( (o_object.getClass() == Integer.class) || (o_object.getClass() == int.class) ) {
				    s_type = "int";
				} else if ( (o_object.getClass().isArray()) && ( (o_object.getClass().getComponentType() == Integer.class) || (o_object.getClass().getComponentType() == int.class) ) ) {
				    s_type = "int[]";
				} else if ( (o_object.getClass() == Long.class) || (o_object.getClass() == long.class) ) {
				    s_type = "long";
				} else if ( (o_object.getClass().isArray()) && ( (o_object.getClass().getComponentType() == Long.class) || (o_object.getClass().getComponentType() == long.class) ) ) {
				    s_type = "long[]";
				} else if (o_object.getClass() == String.class) {
				    s_type = "string";
				} else if ( (o_object.getClass().isArray()) && (o_object.getClass().getComponentType() == String.class) ) {
				    s_type = "string[]";
				} else if (o_object.getClass() == java.util.Date.class) {
				    s_type = "Date";
				} else if ( (o_object.getClass().isArray()) && (o_object.getClass().getComponentType() == java.util.Date.class) ) {
				    s_type = "Date[]";
				} else if (o_object.getClass() == java.time.LocalTime.class) {
				    s_type = "LocalTime";
				} else if ( (o_object.getClass().isArray()) && (o_object.getClass().getComponentType() == java.time.LocalTime.class) ) {
				    s_type = "LocalTime[]";
				} else if (o_object.getClass() == java.time.LocalDate.class) {
				    s_type = "LocalDate";
				} else if ( (o_object.getClass().isArray()) && (o_object.getClass().getComponentType() == java.time.LocalDate.class) ) {
				    s_type = "LocalDate[]";
				} else if (o_object.getClass() == java.time.LocalDateTime.class) {
				    s_type = "DateTime";
				} else if ( (o_object.getClass().isArray()) && (o_object.getClass().getComponentType() == java.time.LocalDateTime.class) ) {
				    s_type = "DateTime[]";
				} else if (o_object.getClass() == java.math.BigDecimal.class) {
				    s_type = "decimal";
				} else if ( (o_object.getClass().isArray()) && (o_object.getClass().getComponentType() == java.math.BigDecimal.class) ) {
				    s_type = "decimal[]";
				} else {
				    if (this.getMarshallingOverrideMessageType() != null) {
				        /* override message type with parameter, thus the type can be set generally from other systems with other programming languages */
				        s_type = this.getMarshallingOverrideMessageType();
				    } else {
				        /* no primitive type -> set object type name */
				    	s_type = o_object.getClass().getTypeName();
				    }
				}
			} catch (Exception o_exc) {
				throw new Exception("Could not marshall answer object");
			}
		} else { /* no marshalling for network communication */
			/* create stream objects to handle data for network message */
			try (
				java.io.ByteArrayOutputStream o_byteArrayOutputStream = new java.io.ByteArrayOutputStream();
				java.io.ObjectOutputStream o_objectOutputStream = new java.io.ObjectOutputStream(o_byteArrayOutputStream);
			) {
				/* read answer object data and serialize it to byte array */
				o_objectOutputStream.writeObject(o_object);
				o_objectOutputStream.flush();
				a_answerObjectBytes = o_byteArrayOutputStream.toByteArray();
				
				s_type = o_object.getClass().getTypeName();
			} catch (Exception o_exc) {
				throw new Exception("Could not serialize answer object");
			}
		}
		
		/* get data block length for network message */
		int i_dataBlockLength = net.forestany.forestj.lib.net.msg.Message.calculateDataBlockLength((this.i_bufferLength - i_decreaseForEncryption));
		int i_dataLength = a_answerObjectBytes.length;
		
		/* calculate how many messages we need to transport answer object with network messages */
		i_messages = 1 + (i_dataLength / i_dataBlockLength);
		a_answerBytes = new byte[i_messages * (this.i_bufferLength - i_decreaseForEncryption)];
		int i_answerPointer = 0;
		
		/* split answer object data into several messages */
		for (i = 0; i < i_messages; i++) {
			/* create network message object with buffer length and encryption correction value */
			net.forestany.forestj.lib.net.msg.Message o_message = new net.forestany.forestj.lib.net.msg.Message((this.i_bufferLength - i_decreaseForEncryption));
			
			/* set message information, like message box id, amount of messages, message number and type */
			o_message.setMessageBoxId(1);
			o_message.setMessageAmount(i_messages);
			o_message.setMessageNumber(i + 1);
			o_message.setType(s_type);
			
			/* create byte array for part of object data */
			byte[] a_data = new byte[i_dataBlockLength];
			int j = 0;
			
			/* iterate all data bytes until we reached message data block length */
			for (j = 0; j < i_dataBlockLength; j++) {
				/* reached last byte? -> break */
				if ( j + (i * i_dataBlockLength) >= a_answerObjectBytes.length ) {
					break;
				}
				
				/* copy data byte to byte array */
				a_data[j] = a_answerObjectBytes[j + (i * i_dataBlockLength)];
			}
			
			/* give byte array as data part to network message object */
			o_message.setData(a_data);
			
			/* part of object data may not need complete data block length, especially the last message or just one message, so this is not obsolete because the for loop before could be break before j == i_dataBlockLength - 1 */
			if (j != i_dataBlockLength - 1) {
				/* update message data length */
				o_message.setDataLength(j);
			}
			
			/* get message byte array */
			a_data = o_message.getByteArrayFromMessage();
			
			/* copy message byte array to byte array of answer object */
			for (j = 0; j < a_answerBytes.length; j++) {
				a_answerBytes[i_answerPointer++] = a_data[j];
			}
		}
		
		/* encrypt answer bytes if encryption is active */
		if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) {
			a_answerBytes = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(a_answerBytes, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
		} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) {
			a_answerBytes = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(a_answerBytes, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
		} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
			a_answerBytes = this.o_cryptography.encrypt(a_answerBytes);
		}
		
												net.forestany.forestj.lib.Global.ilogFine("Amount bytes protocol, starting");
		
		/* use AmountBytesProtocol to send to other communication side how many bytes are to be expected */
		this.amountBytesProtocol(a_answerBytes.length);
		
												net.forestany.forestj.lib.Global.ilogFiner("Amount bytes protocol, finished");
		
												net.forestany.forestj.lib.Global.ilogFine("Sending answer message data, amount of bytes: " + a_answerBytes.length);
		
		/* sending answer object within message context */
		this.sendBytes(a_answerBytes);
		
		/* with asymmetric encryption we have to close our ssl socket ourselves */
		if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.ASYMMETRIC) && (!((java.net.Socket)this.o_socket.getSocket()).isClosed()) ) {
			((java.net.Socket)this.o_socket.getSocket()).close();
		}
	}
}
