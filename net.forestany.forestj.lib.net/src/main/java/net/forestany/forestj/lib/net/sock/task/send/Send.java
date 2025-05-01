package net.forestany.forestj.lib.net.sock.task.send;

/**
 * Generic task class to send network traffic over a socket instance. Several methods supporting UDP and TCP, sending data in combination with a message box or multiple message boxes.
 *
 * @param <T>	java socket class parameter
 */
public class Send<T> extends net.forestany.forestj.lib.net.sock.task.Task<T> {
	/**
	 * standard constructor
	 */
	public Send() {
		
	}
	
	/**
	 * Creating sending socket task instance with all it's parameters and settings
	 * 
	 * @param p_e_communicationType					specifies communication type of socket task
	 * @param p_e_communicationCardinality			specifies communication cardinality of socket task
	 * @param p_i_queueTimeoutMilliseconds			determine timeout in milliseconds for sending/receiving bytes
	 * @throws IllegalArgumentException				invalid timeout value for queue
	 */
	public Send(net.forestany.forestj.lib.net.sock.com.Type p_e_communicationType, net.forestany.forestj.lib.net.sock.com.Cardinality p_e_communicationCardinality, int p_i_queueTimeoutMilliseconds) throws IllegalArgumentException {
		super(p_e_communicationType, p_e_communicationCardinality, p_i_queueTimeoutMilliseconds);
	}
	
	/**
	 * Creating sending socket task instance with all it's parameters and settings
	 * 
	 * @param p_e_communicationType					specifies communication type of socket task
	 * @param p_e_communicationCardinality			specifies communication cardinality of socket task
	 * @param p_i_queueTimeoutMilliseconds			determine timeout in milliseconds for sending/receiving bytes
	 * @param p_e_communicationSecurity				specifies communication security of socket task
	 * @throws IllegalArgumentException				invalid timeout value for queue
	 */
	public Send(net.forestany.forestj.lib.net.sock.com.Type p_e_communicationType, net.forestany.forestj.lib.net.sock.com.Cardinality p_e_communicationCardinality, int p_i_queueTimeoutMilliseconds, net.forestany.forestj.lib.net.sock.com.Security p_e_communicationSecurity) throws IllegalArgumentException {
		super(p_e_communicationType, p_e_communicationCardinality, p_i_queueTimeoutMilliseconds, p_e_communicationSecurity);
	}
	
	/**
	 * Creating sending socket task instance with all it's parameters and settings
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
	public Send(net.forestany.forestj.lib.net.sock.com.Type p_e_communicationType, net.forestany.forestj.lib.net.sock.com.Cardinality p_e_communicationCardinality, int p_i_queueTimeoutMilliseconds, net.forestany.forestj.lib.net.sock.com.Security p_e_communicationSecurity, boolean p_b_useMarshalling, int p_i_marshallingDataLengthInBytes, boolean p_b_marshallingUsePropertyMethods, String p_s_marshallingOverrideMessageType, boolean p_b_marshallingSystemUsesLittleEndian) throws IllegalArgumentException {
		super(p_e_communicationType, p_e_communicationCardinality, p_i_queueTimeoutMilliseconds, p_e_communicationSecurity, null, p_b_useMarshalling, p_i_marshallingDataLengthInBytes, p_b_marshallingUsePropertyMethods, p_s_marshallingOverrideMessageType, p_b_marshallingSystemUsesLittleEndian);
	}
		
	/**
	 * method to get socket task class type for creating new instances with declared constructor
	 * @return		class type of socket task as Class&lt;?&gt;
	 */
	public Class<?> getSocketTaskClassType() {
		return net.forestany.forestj.lib.net.sock.task.send.Send.class;
	}
	
	/**
	 * method to clone this socket task with another socket task instance
	 * @param p_o_sourceTask		another socket task instance as source for all it's parameters and settings
	 */
	public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<T> p_o_sourceTask) {
		this.cloneBasicFields(p_o_sourceTask);
	}
	
	/**
	 * runTask method of sending socket task which can always vary depending on the implementation. Supporting in this class UDP_SEND, UDP_SEND_WITH_ACK, TCP_SEND and TCP_SEND_WITH_ANSWER.
	 * 
	 * @throws Exception	any exception of implementation that could happen will be caught by abstract Task class, see details in protocol methods in net.forestany.forestj.lib.net.sock.task.send.Send
	 */
	protected void runTask() throws Exception {
		/* flag if data has been sent within socket task */
		boolean b_dataSend = false;
		
		if ( (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND) || (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND_WITH_ACK) || (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_MULTICAST_SENDER) ) {
			/* send UDP packets */
			b_dataSend = this.udpSend();
		} else if (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND) {
			/* check if we want to use tcp socket for bidirectional communication */
			if (this.getCommunicationCardinality() == net.forestany.forestj.lib.net.sock.com.Cardinality.EqualBidirectional) {
				this.tcpBidirectional();
			} else {
				/* check all message boxes of socket task */
				for (net.forestany.forestj.lib.net.msg.MessageBox o_messageBox : this.a_messageBoxes) {
					/* get message of current message box */
					net.forestany.forestj.lib.net.msg.Message o_message = o_messageBox.currentMessage();
					
					/* if we got a message we want to send it */
					if (o_message != null) {
						if (this.b_objectTransmission) {
							/* sending TCP packets, but a whole object, so several messages of a message box until the object has been transferred */
							b_dataSend = this.tcpSendObjectTransmission(o_message, o_messageBox);
						} else {
							/* send TCP packet: just dequeue one message of current message box */
							o_message = o_messageBox.dequeueMessage();
							
							if (o_message != null) {
								/* send message with TCP protocol */
								b_dataSend = this.tcpSend(o_message);
							} else {
								throw new NullPointerException("Could not dequeue message, result is null");
							}
						}
						
						/* break for loop here, so the next send will happen with next thread cycle */
						break;
					}
				}
			}
		} else if (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND_WITH_ANSWER) {
			/* send TCP packet and receive answer immediately with answerMessageBox property */
			b_dataSend = this.tcpSendWithAnswer();
		}
		
		/* if no data has been sent we wait queue timeout length  */
		if (!b_dataSend) {
													net.forestany.forestj.lib.Global.ilogFinest("no data send, timeout for '" + this.i_queueTimeoutMilliseconds + "' milliseconds");
			
			/* wait queue timeout length to enqueue message object again */
			Thread.sleep(this.i_queueTimeoutMilliseconds);
		}
	}
	
	/**
	 * Method for sending a UDP datagram packet, optional with receiving an acknowledge(ACK) over UDP which is not UDP standard but could be useful sometimes
	 * 
	 * @throws NullPointerException									message could not be dequeued from message box
	 * @throws Exception											any other Exception, see below in other classes for details
	 * @see net.forestany.forestj.lib.Cryptography								net.forestany.forestj.lib.Cryptography - Encrypt_AES_GCM and Encrypt methods
	 * @see net.forestany.forestj.lib.net.msg.Message							net.forestany.forestj.lib.net.msg.Message - Constructor and Message methods
	 * @see net.forestany.forestj.lib.net.msg.Messagebox							net.forestany.forestj.lib.net.msg.MessageBox - Accessing MessageBox queue
	 */
	private boolean udpSend() throws NullPointerException, Exception {
		/* flag if data has been sent within socket task */
		boolean b_dataSend = false;
		
		/* check all message boxes of socket task */
		for (net.forestany.forestj.lib.net.msg.MessageBox o_messageBox : this.a_messageBoxes) {
			/* get message of current message box */
			net.forestany.forestj.lib.net.msg.Message o_message = o_messageBox.currentMessage();
			
			/* if we got a message we want to send it */
			if (o_message != null) {
				/* just dequeue one message of current message box */
				o_message = o_messageBox.dequeueMessage();
				
				if (o_message != null) {
					/* convert message into byte array */
					byte[] a_bytes = o_message.getByteArrayFromMessage();
					
															net.forestany.forestj.lib.Global.ilogFine("Send message to[" + this.o_datagramPacket.getSocketAddress().toString() + "] with port[" + ((java.net.DatagramSocket)this.o_socket.getSocket()).getLocalPort() + "]: message number[" + o_message.getMessageNumber() + "] of [" + o_message.getMessageAmount() + "] with length[" + a_bytes.length + "], data length[" + o_message.getDataLength() + "], data block length[" + o_message.getDataBlockLength() + "], message box id[" + o_messageBox.getMessageBoxId() + "]");
					
					/* debug sending message if b_debugNetworkTrafficOn is set to true */
					this.debugMessage(a_bytes, o_message);
					
					/* encrypt sending bytes if encryption is active */
					if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) {
						byte[] a_encrypted = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(a_bytes, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
						this.o_datagramPacket.setData(a_encrypted);
						this.o_datagramPacket.setLength(a_encrypted.length);
					} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) {
						byte[] a_encrypted = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(a_bytes, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
						this.o_datagramPacket.setData(a_encrypted);
						this.o_datagramPacket.setLength(a_encrypted.length);
					} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
						byte[] a_encrypted = this.o_cryptography.encrypt(a_bytes);
						this.o_datagramPacket.setData(a_encrypted);
						this.o_datagramPacket.setLength(a_encrypted.length);
					} else {
						this.o_datagramPacket.setData(a_bytes);
						this.o_datagramPacket.setLength(a_bytes.length);
					}
					
					/* sending bytes via UDP */
					((java.net.DatagramSocket)this.o_socket.getSocket()).send(this.o_datagramPacket);
					
					/* update flag */
					b_dataSend = true;
					
					/* communication type requires to receive an acknowledge(ACK) over UDP */
					if (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND_WITH_ACK) {
						/* execute method to receive an acknowledge(ACK) */
						if (!this.receiveUdpAck()) {
																	net.forestany.forestj.lib.Global.ilogWarning("Could not receive ACK");
						}
					}
					
					/* break for loop here, so the next send will happen with next thread cycle */
					break;
				} else {
					throw new NullPointerException("Could not dequeue message, result is null");
				}
			}
		}
		
		return b_dataSend;
	}
	
	/**
	 * Receive UDP ACK using own datagram source address and source port. All exceptions will be handled within this method and are not thrown to parent methods.
	 * 
	 * @return				true - ACK has been received so communication is successful, false - nothing received so something went wrong
	 */
	private boolean receiveUdpAck() {
		boolean b_receivedACK = false;
		
		try {
			/* check UDP port min. value */
			if (((java.net.DatagramSocket)this.o_socket.getSocket()).getLocalPort() < 1) {
				throw new IllegalArgumentException("UDP receive ACK port must be at least '1', but was set to '" + ((java.net.DatagramSocket)this.o_socket.getSocket()).getLocalPort() + "'");
			}
			
			/* check UDP port max. value */
			if (((java.net.DatagramSocket)this.o_socket.getSocket()).getLocalPort() > 65535) {
				throw new IllegalArgumentException("UDP receive ACK port must be lower equal '65535', but was set to '" + ((java.net.DatagramSocket)this.o_socket.getSocket()).getLocalPort() + "'");
			}
			
			java.net.InetSocketAddress o_receiveSocketAddress = new java.net.InetSocketAddress(((java.net.DatagramSocket)this.o_socket.getSocket()).getLocalAddress().getHostAddress(), ((java.net.DatagramSocket)this.o_socket.getSocket()).getLocalPort());
			
													net.forestany.forestj.lib.Global.ilogFiner("SocketTaskSend, receiveUDPACK(" + ((java.net.DatagramSocket)this.o_socket.getSocket()).getLocalAddress().getHostAddress() + ", " + o_receiveSocketAddress.getPort() + ")");
			
			/* close current UDP socket, so we can use it to receive the ACK from the other communication side */
			if ( (((java.net.DatagramSocket)this.o_socket.getSocket()) != null) && (!((java.net.DatagramSocket)this.o_socket.getSocket()).isClosed()) ) {
				((java.net.DatagramSocket)this.o_socket.getSocket()).close();
			}
			
			int i_length = 1;
			
			/* check for length correction if encryption is active  */
			if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) ) {
				i_length = 29;
			} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
				i_length = 17;
			}
			
			/* create UDP receive socket */
			java.net.DatagramSocket o_receiveSocket = new java.net.DatagramSocket(o_receiveSocketAddress);
			/* set expected length */
			o_receiveSocket.setReceiveBufferSize(i_length);
			/* set timeout */
			o_receiveSocket.setSoTimeout(this.i_udpReceiveACKTimeoutMilliseconds);
			
			try {
				/* byte array for receiving bytes from UDP datagram */
				byte[] a_datagramPacketBytes = new byte[o_receiveSocket.getReceiveBufferSize()];
				/* create UDP datagram packet object */
				java.net.DatagramPacket o_datagramPacket = new java.net.DatagramPacket(a_datagramPacketBytes, o_receiveSocket.getReceiveBufferSize());
				
														net.forestany.forestj.lib.Global.ilogFiner("wait to receive UDP ACK o_receiveSocket.receive()");
				
				/* wait to receive UDP ACK */
				o_receiveSocket.receive(o_datagramPacket);
				
				/* check if received byte array length is greater than 0 */
				if (o_datagramPacket.getLength() < 1) {
					throw new NullPointerException("Could not receive ACK - length: " + o_datagramPacket.getLength() + " < 1");
				}
				
				/* decrypt received bytes if encryption is active */
				if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) {
					a_datagramPacketBytes = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_datagramPacketBytes, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
				} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) {
					a_datagramPacketBytes = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_datagramPacketBytes, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
				} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
					a_datagramPacketBytes = this.o_cryptography.decrypt(a_datagramPacketBytes);
				}
				
				/* ACK variable */
				byte by_ack = 0x00;
				
				if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
					/* read decrypted UDP ACK */
					by_ack = a_datagramPacketBytes[0];
				} else {
					/* read UDP ACK without encryption */
					by_ack = o_datagramPacket.getData()[0];
				}
				
				/* check if received UDP ACK matches the ACK constant defined in net.forestany.forestj.lib.net.sock.task.Task class */
				if (by_ack != net.forestany.forestj.lib.net.sock.task.Task.BY_ACK_BYTE) {
					throw new java.io.IOException("Invalid ACK[" + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {by_ack}, false).trim() + "], must be [" + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {net.forestany.forestj.lib.net.sock.task.Task.BY_ACK_BYTE}, false).trim() + "].");
				} else {
					/* update return value */
					b_receivedACK = true;
					
															net.forestany.forestj.lib.Global.ilogFiner("received UDP ACK");
				}
			} catch (java.net.SocketTimeoutException o_exc) {
				net.forestany.forestj.lib.Global.ilogFiner("SocketTimeoutException SocketTaskSend-receiveACK method: " + o_exc.getMessage());
			} catch (java.io.IOException o_exc) {
				net.forestany.forestj.lib.Global.logException("IOException SocketTaskSend-receiveACK method: ", o_exc);
			} finally {
				/* close UDP receive socket */
				if ( (o_receiveSocket != null) && (!o_receiveSocket.isClosed()) ) {
					o_receiveSocket.close();
				}
			}
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException("Exception SocketTaskSend-receiveACK method: ", o_exc);
		}
		
		return b_receivedACK;
	}
	
	/**
	 * Method for sending and transferring an object with a TCP connection with messages
	 * 
	 * @param p_o_message						first message of object which will be transferred				
	 * @param p_o_messageBox					message box where object is stored into several messages
	 * @return									true - object sent successfully, false - something went wrong
	 * @throws Exception						any other Exception, see below in other classes for details
	 * @see java.lang.Thread					java.lang.Thread - sleep method
	 * @see net.forestany.forestj.lib.Cryptography			net.forestany.forestj.lib.Cryptography - Encrypt_AES_GCM and Encrypt methods
	 * @see net.forestany.forestj.lib.net.msg.Message		net.forestany.forestj.lib.net.msg.Message - Constructor and Message methods
	 * @see net.forestany.forestj.lib.net.msg.Messagebox		net.forestany.forestj.lib.net.msg.MessageBox - Accessing MessageBox queue
	 */
	private boolean tcpSendObjectTransmission(net.forestany.forestj.lib.net.msg.Message p_o_message, net.forestany.forestj.lib.net.msg.MessageBox p_o_messageBox) throws Exception {
		/* overall data length which will be transferred to other communication side */
		int i_transmissionDataLength = 0;
		/* array where all messages will be converted into bytes */
		java.util.List<Byte> a_messagesBytes = new java.util.ArrayList<Byte>();
		
		try {
			/* help variables and amount of messages to be dequeued to transfer stored object */
			int i_messageAmount = p_o_message.getMessageAmount();
			int i_messageNumber = 0;
			int i_dequeueCount = 0;
			
			/* gather all message bytes, based on message amount */
			do {
				/* dequeue message from message box */
				net.forestany.forestj.lib.net.msg.Message o_message = p_o_messageBox.dequeueMessage();
				
				if (o_message != null) { /* dequeue successful */
					i_dequeueCount = 0;
					i_messageNumber = o_message.getMessageNumber();
					byte[] a_messageBytes = o_message.getByteArrayFromMessage();
					i_transmissionDataLength += a_messageBytes.length;
					
					/* debug received message if b_debugNetworkTrafficOn is set to true */
					this.debugMessage(a_messageBytes, o_message);
					
					/* gather bytes */
					for (int i = 0; i < a_messageBytes.length; i++) {
						a_messagesBytes.add(a_messageBytes[i]);
					}
				} else { /* something went wrong with dequeuing message box */
					int i_wait = 10;
					
															net.forestany.forestj.lib.Global.ilogFinest("Cannot dequeue message, timeout for '" + i_wait + "' milliseconds");
															
					/* wait 10 milliseconds to dequeue message from message box object again */
					Thread.sleep(i_wait);
					
					i_dequeueCount++;
					
					/* if 3000 times dequeue failed, something went wrong */
					if (i_dequeueCount > (30000 / i_wait)) {
						throw new NullPointerException("Could not dequeue message, result is null");
					}
				}
			} while ((i_messageAmount - i_messageNumber) != 0);
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.logException("Exception Send-tcpSendObjectTransmission method: ", o_exc);
		}
		
		/* create byte array which will be send to other communication side over TCP */
		byte[] a_bytes = new byte[i_transmissionDataLength];
		int i = 0;
		
		/* copy all gathered message bytes into byte array */
		for (byte by_byte : a_messagesBytes) {
			a_bytes[i++] = by_byte;
		}
		
		/* encrypt sending bytes if encryption is active */
		if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) {
			a_bytes = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(a_bytes, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
		} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) {
			a_bytes = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(a_bytes, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
		} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
			a_bytes = this.o_cryptography.encrypt(a_bytes);
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("Amount bytes protocol, starting");
		
		/* use AmountBytesProtocol to send to other communication side how many bytes are to be expected */
		this.amountBytesProtocol(a_bytes.length);
		
												net.forestany.forestj.lib.Global.ilogFiner("Amount bytes protocol, finished");
		
												net.forestany.forestj.lib.Global.ilogFine("Sending data, amount of bytes: " + a_bytes.length);
												
		/* send bytes over TCP */
		this.sendBytes(a_bytes);
		
		return true;
	}
	
	/**
	 * Method for sending and transferring an object with a TCP connection with messages and receiving an answer with separate answer message box within socket task
	 * 
	 * @throws IllegalStateException								amount of data is not a multiple of buffer length
	 * @throws Exception											any other Exception, see below in other classes for details
	 * @see java.lang.Thread										java.lang.Thread - sleep method
	 * @see net.forestany.forestj.lib.Cryptography								net.forestany.forestj.lib.Cryptography - Encrypt_AES_GCM and Encrypt methods
	 * @see net.forestany.forestj.lib.net.msg.Message							net.forestany.forestj.lib.net.msg.Message - Constructor and Message methods
	 * @see net.forestany.forestj.lib.net.msg.Messagebox							net.forestany.forestj.lib.net.msg.MessageBox - Accessing MessageBox queue
	 */
	private boolean tcpSendWithAnswer() throws IllegalStateException, Exception {
		/* flag if data has been sent within socket task */
		boolean b_dataSend = false;
		
		/* check all message boxes of socket task */
		for (net.forestany.forestj.lib.net.msg.MessageBox o_messageBox : this.a_messageBoxes) {
			/* get message of current message box */
			net.forestany.forestj.lib.net.msg.Message o_message = o_messageBox.currentMessage();
			
			/* if we got a message we want to send it */
			if (o_message != null) {
				/* overall data length which will be transferred to other communication side */
				int i_transmissionDataLength = 0;
				/* array where all messages will be converted into bytes */
				java.util.List<Byte> a_messagesBytes = new java.util.ArrayList<Byte>();
				
				try {
					/* help variables and amount of messages to be dequeued to transfer stored object */
					int i_messageAmount = o_message.getMessageAmount();
					int i_messageNumber = 0;
					int i_dequeueCount = 0;
					
					/* gather all message bytes, based on message amount */
					do {
						/* dequeue message from message box */
						o_message = o_messageBox.dequeueMessage();
						
						if (o_message != null) { /* dequeue successful */
							i_dequeueCount = 0;
							i_messageNumber = o_message.getMessageNumber();
							byte[] a_messageBytes = o_message.getByteArrayFromMessage();
							i_transmissionDataLength += a_messageBytes.length;
							
							/* debug received message if b_debugNetworkTrafficOn is set to true */
							this.debugMessage(a_messageBytes, o_message);
							
							/* gather bytes */
							for (int i = 0; i < a_messageBytes.length; i++) {
								a_messagesBytes.add(a_messageBytes[i]);
							}
						} else { /* something went wrong with dequeuing message box */
							int i_wait = 10;
							
																	net.forestany.forestj.lib.Global.ilogFinest("Cannot dequeue message, timeout for '" + i_wait + "' milliseconds");
																	
							/* wait 10 milliseconds to dequeue message from message box object again */
							Thread.sleep(i_wait);
							
							i_dequeueCount++;
							
							/* if 3000 times dequeue failed, something went wrong */
							if (i_dequeueCount > (30000 / i_wait)) {
								throw new NullPointerException("Could not dequeue message, result is null");
							}
						}
					} while ((i_messageAmount - i_messageNumber) != 0);
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.logException("Exception Send-tcpSendWithAnswer method: ", o_exc);
				}
				
				/* create byte array which will be send to other communication side over TCP */
				byte[] a_bytes = new byte[i_transmissionDataLength];
				int i = 0;
				
				/* copy all gathered message bytes into byte array */
				for (byte by_byte : a_messagesBytes) {
					a_bytes[i++] = by_byte;
				}
				
				/* encrypt sending bytes if encryption is active */
				if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) {
					a_bytes = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(a_bytes, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
				} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) {
					a_bytes = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(a_bytes, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
				} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
					a_bytes = this.o_cryptography.encrypt(a_bytes);
				}
				
														net.forestany.forestj.lib.Global.ilogFiner("Amount bytes protocol, starting");
		
				/* use AmountBytesProtocol to send to other communication side how many bytes are to be expected */
				this.amountBytesProtocol(a_bytes.length);
				
														net.forestany.forestj.lib.Global.ilogFiner("Amount bytes protocol, finished");
				
														net.forestany.forestj.lib.Global.ilogFine("Sending data, amount of bytes: " + a_bytes.length);
														
				/* send bytes over TCP */
				this.sendBytes(a_bytes);
				
				/* update flag */
				b_dataSend = true;
				
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
				
				/* receive bytes for answer object */
				byte[] a_receivedData = this.receiveBytes(i_amountBytes);
				
				/* decrypt received bytes if encryption is active */
				if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) {
					a_receivedData = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_receivedData, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
				} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) {
					a_receivedData = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_receivedData, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
				} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
					a_receivedData = this.o_cryptography.decrypt(a_receivedData);
				}
				
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
				
				/* calculate how many messages we received for answer message object */
				int i_messages = a_receivedData.length / (this.i_bufferLength - i_decreaseForEncryption);
				
														net.forestany.forestj.lib.Global.ilogFine("Received answer message from[" + ((java.net.Socket)this.o_socket.getSocket()).getRemoteSocketAddress().toString() + "] with port[" + ((java.net.Socket)this.o_socket.getSocket()).getPort() + "]: messages[" + i_messages + "], length[" + a_receivedData.length + "]");
				
				for (i = 0; i < i_messages; i++) {
					byte[] a_messageData = new byte[(this.i_bufferLength - i_decreaseForEncryption)];
					
					/* copy received answer data into message data array */
					for (int i_bytePointer = 0; i_bytePointer < (this.i_bufferLength - i_decreaseForEncryption); i_bytePointer++) {
						a_messageData[i_bytePointer] = a_receivedData[i_bytePointer + (i * (this.i_bufferLength - i_decreaseForEncryption))];
					}
					
					/* create message object with message length and message data */
					o_message = new net.forestany.forestj.lib.net.msg.Message((this.i_bufferLength - i_decreaseForEncryption));
					o_message.setMessageFromByteArray(a_messageData);
					
															net.forestany.forestj.lib.Global.ilogFiner("Received answer message number[" + o_message.getMessageNumber() + "] of [" + o_message.getMessageAmount() + "] with length[" + o_message.getMessageLength() + "], data length[" + o_message.getDataLength() + "], data block length[" + o_message.getDataBlockLength() + "], message box id[" + o_message.getMessageBoxId() + "]");
					
					/* debug received answer message if b_debugNetworkTrafficOn is set to true */
					this.debugMessage(a_messageData, o_message);
					
					int i_messageBoxId = 0;
					
					/* determine message box id if we have a cardinality with many message boxes and one socket */
					if (this.e_communicationCardinality == net.forestany.forestj.lib.net.sock.com.Cardinality.ManyMessageBoxesToOneSocket) {
						i_messageBoxId = o_message.getMessageBoxId();
						
						/* check message box id min. value */
						if (i_messageBoxId < 1) {
							throw new IllegalArgumentException("Invalid receiving message. Message box id[" + i_messageBoxId + "] must be greater than [0].");
						}
						
						/* check message box id max. value */
						if (i_messageBoxId > this.a_answerMessageBoxes.size()) {
							throw new IllegalArgumentException("Invalid receiving message. Message box id[" + i_messageBoxId + "] is not available.");
						}
						
						i_messageBoxId--;
					}
					
					/* enqueue answer message object to answer message box */
					while (!this.a_answerMessageBoxes.get(i_messageBoxId).enqueueMessage(o_message)) {
																net.forestany.forestj.lib.Global.ilogWarning("Could not enqueue message, timeout for '" + this.i_queueTimeoutMilliseconds + "' milliseconds");
						
						/* wait queue timeout length to enqueue answer message object again */
						Thread.sleep(this.i_queueTimeoutMilliseconds);
					};
				}
				
				/* break for loop here, so the next send will happen with next thread cycle */
				break;
			}
		}
		
		return b_dataSend;
	}	
}
