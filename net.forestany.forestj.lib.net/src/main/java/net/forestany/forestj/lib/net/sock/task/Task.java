package net.forestany.forestj.lib.net.sock.task;

/**
 * Abstract class for all kind of socket tasks. It will handle the core execution process of a socket with handling all the network traffic.
 * Some general methods are added to work with network traffic over sockets.
 * 
 * @param <T>	java socket class parameter
 */
abstract public class Task<T> implements Runnable {

	/* Constants */
	
	/**
	 * ACK BYTE constant
	 */
	protected static final byte BY_ACK_BYTE = (byte)0xA5; /* 1010 0101 */
	/**
	 * RECEIVE_MAX_UNKNOWN_AMOUNT_IN_MIB constant
	 */
	public static final int RECEIVE_MAX_UNKNOWN_AMOUNT_IN_MIB = 16;
	/**
	 * TOLERATING_DELAY_IN_MS constant
	 */
	public static final int TOLERATING_DELAY_IN_MS = 25;
	/**
	 * AMOUNT_CYCLES_TOLERATING_DELAY constant
	 */
	public static final int AMOUNT_CYCLES_TOLERATING_DELAY = 5;
	
	/* Delegates */
	
	/**
	 * interface delegate definition which can be instanced outside of sock.task.Task&lt;T&gt; class to post progress anywhere of sending/receiving bytes
	 * but only for TCP sockets
	 */
	public interface IDelegate {
		/**
		 * progress method for task receiving/sending bytes
		 * 
		 * @param p_i_bytes amount of bytes
		 * @param p_i_totalBytes total amount of bytes to be received/sent
		 */
		void PostProgress(int p_i_bytes, int p_i_totalBytes);
	}
	
	/* Fields */
	
	/**
	 * type
	 */
	protected net.forestany.forestj.lib.net.sock.Type e_type;
	/**
	 * communication type
	 */
	protected net.forestany.forestj.lib.net.sock.com.Type e_communicationType;
	/**
	 * communication cardinality
	 */
	protected net.forestany.forestj.lib.net.sock.com.Cardinality e_communicationCardinality;
	/**
	 * communication security
	 */
	protected net.forestany.forestj.lib.net.sock.com.Security e_communicationSecurity;
	/**
	 * common secret passphrase
	 */
	protected String s_commonSecretPassphrase;
	/**
	 * cryptography instance
	 */
	protected net.forestany.forestj.lib.Cryptography o_cryptography;
	/**
	 * buffer length
	 */
	protected int i_bufferLength;
	/**
	 * recieve max unknown amount
	 */
	protected int i_receiveMaxUnknownAmount;
	/**
	 * amount cycles tolerating delay
	 */
	protected int i_amountCyclesToleratingDelay;
	/**
	 * socket
	 */
	protected net.forestany.forestj.lib.net.sock.Wrapper<?> o_socket;
	/**
	 * datagram packet
	 */
	protected java.net.DatagramPacket o_datagramPacket;
	/**
	 * queue timeout milliseconds
	 */
	protected int i_queueTimeoutMilliseconds;
	/**
	 * message boxes
	 */
	protected java.util.List<net.forestany.forestj.lib.net.msg.MessageBox> a_messageBoxes;
	/**
	 * answer message boxes
	 */
	protected java.util.List<net.forestany.forestj.lib.net.msg.MessageBox> a_answerMessageBoxes;
	/**
	 * stop flag
	 */
	protected boolean b_stop;
	/**
	 * udp receive ack timeout milliseconds
	 */
	protected int i_udpReceiveACKTimeoutMilliseconds;
	/**
	 * udp send ack timeout milliseconds
	 */
	protected int i_udpSendACKTimeoutMilliseconds;
	/**
	 * object transmisison flag
	 */
	protected boolean b_objectTransmission;
	/**
	 * receive socket task
	 */
	protected Task<T> o_receiveSocketTask;
	/**
	 * request object
	 */
	protected Object o_requestObject;
	/**
	 * answer object
	 */
	protected Object o_answerObject;
	/**
	 * list of objects
	 */
	protected java.util.List<Object> a_objects;
	/**
	 * debug network traffic on flag
	 */
	protected boolean b_debugNetworkTrafficOn;
	/**
	 * use marshalling flag
	 */
	private boolean b_useMarshalling;
	/**
	 * marshalling data length in bytes
	 */
	private int i_marshallingDataLengthInBytes;
	/**
	 * marshalling use property methods
	 */
	private boolean b_marshallingUsePropertyMethods;
	/**
	 * marshalling override message type
	 */
	private String s_marshallingOverrideMessageType;
	/**
	 * marshalling system uses little endian flag
	 */
	private boolean b_marshallingSystemUsesLittleEndian;
	/**
	 * delegate
	 */
	protected IDelegate itf_delegate;
	/**
	 * toke instance
	 */
	protected Token o_token;
	
	/* Properties */
	
	/**
	 * get type
	 * 
	 * @return net.forestany.forestj.lib.net.sock.Type
	 */
	public net.forestany.forestj.lib.net.sock.Type getType() {
		return this.e_type;
	}
	
	/**
	 * get communication type
	 * 
	 * @return net.forestany.forestj.lib.net.sock.com.Type
	 */
	public net.forestany.forestj.lib.net.sock.com.Type getCommunicationType() {
		return this.e_communicationType;
	}
	
	/**
	 * get communication cardinality
	 * 
	 * @return net.forestany.forestj.lib.net.sock.com.Cardinality
	 */
	public net.forestany.forestj.lib.net.sock.com.Cardinality getCommunicationCardinality() {
		return this.e_communicationCardinality;
	}
	
	/**
	 * get communication security
	 * 
	 * @return net.forestany.forestj.lib.net.sock.com.Security
	 */
	protected net.forestany.forestj.lib.net.sock.com.Security getCommunicationSecurity() {
		return this.e_communicationSecurity;
	}
	
	/**
	 * get common secret passphrase
	 * 
	 * @return String
	 */
	private String getCommonSecretPassphrase() {
		return this.s_commonSecretPassphrase;
	}
	
	/**
	 * set common secret passphrase
	 * 
	 * @param p_s_commonSecretPassphrase						String value
     * @throws IllegalArgumentException							invalid common secret passphrase length [null or length lower than 36 characters]
	 * @throws java.security.NoSuchAlgorithmException 			invalid key factory algorithm
	 * @throws java.security.spec.InvalidKeySpecException 		invalid key specifications, length of common secret passphrase, salt, iteration value or key length option
	 */
	public void setCommonSecretPassphrase(String p_s_commonSecretPassphrase) throws IllegalArgumentException, java.security.NoSuchAlgorithmException, java.security.spec.InvalidKeySpecException {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_commonSecretPassphrase)) {
			throw new IllegalArgumentException("You have not specified a common secret passphrase for using symmetric 128/256-bit communication security");
		}
		
		if (p_s_commonSecretPassphrase.length() < 36) {
			throw new IllegalArgumentException("Common secret passphrase must have at least '36' characters, but has '" + p_s_commonSecretPassphrase.length() + "' characters");
		}
		
		this.s_commonSecretPassphrase = p_s_commonSecretPassphrase;
		
		if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
			if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) {
				this.o_cryptography = new net.forestany.forestj.lib.Cryptography(this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
			} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) {
				this.o_cryptography = new net.forestany.forestj.lib.Cryptography(this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
			}
		}
	}
	
	/**
	 * get buffer length
	 * 
	 * @return int
	 */
	public int getBufferLength() {
		return this.i_bufferLength;
	}
	
	/**
	 * set buffer length
	 * 
	 * @param p_i_bufferLength int
	 * @throws IllegalArgumentException must be at least '1'
	 */
 	public void setBufferLength(int p_i_bufferLength) throws IllegalArgumentException  {
		if (p_i_bufferLength < 1) {
			throw new IllegalArgumentException("Buffer length must be at least '1', but was set to '" + p_i_bufferLength + "'");
		} else {
			this.i_bufferLength = p_i_bufferLength;
		}
	}
	
 	/**
 	 * get receive max unknown amount in MiB
 	 * 
 	 * @return int
 	 */
 	public int getReceiveMaxUnknownAmountInMiB() {
		return this.i_receiveMaxUnknownAmount;
	}
	
 	/**
 	 * set receive max unknown amount in MiB
 	 * 
 	 * @param p_i_receiveMaxUnknownAmount int
 	 * @throws IllegalArgumentException must be at least '1', must be lower than '999'
 	 */
 	public void setReceiveMaxUnknownAmountInMiB(int p_i_receiveMaxUnknownAmount) throws IllegalArgumentException  {
		if (p_i_receiveMaxUnknownAmount < 1) {
			throw new IllegalArgumentException("Receive max. unknown amount in MiB must be at least '1', but was set to '" + p_i_receiveMaxUnknownAmount + "'");
		} else if (p_i_receiveMaxUnknownAmount > 999) {
			throw new IllegalArgumentException("Receive max. unknown amount in MiB must be lower than '999', but was set to '" + p_i_receiveMaxUnknownAmount + "'");
		} else {
			this.i_receiveMaxUnknownAmount = p_i_receiveMaxUnknownAmount;
		}
	}
	
 	/**
 	 * get amount cycles tolerating delay
 	 * 
 	 * @return int
 	 */
 	public int getAmountCyclesToleratingDelay() {
		return this.i_amountCyclesToleratingDelay;
	}
	
 	/**
 	 * set amount cycles tolerating delay
 	 * 
 	 * @param p_i_amountCyclesToleratingDelay int
 	 * @throws IllegalArgumentException must be at least '1', must be lower than '100'
 	 */
 	public void setAmountCyclesToleratingDelay(int p_i_amountCyclesToleratingDelay) throws IllegalArgumentException  {
		if (p_i_amountCyclesToleratingDelay < 1) {
			throw new IllegalArgumentException("Amount of cycles tolerating delay (1 cycle = " + net.forestany.forestj.lib.net.sock.task.Task.TOLERATING_DELAY_IN_MS + "ms) must be at least '1', but was set to '" + p_i_amountCyclesToleratingDelay + "'");
		} else if (p_i_amountCyclesToleratingDelay > 100) {
			throw new IllegalArgumentException("Amount of cycles tolerating delay (1 cycle = " + net.forestany.forestj.lib.net.sock.task.Task.TOLERATING_DELAY_IN_MS + "ms) must be lower than '100 (" + (net.forestany.forestj.lib.net.sock.task.Task.TOLERATING_DELAY_IN_MS * 100) + "ms)', but was set to '" + p_i_amountCyclesToleratingDelay + "'");
		} else {
			this.i_amountCyclesToleratingDelay = p_i_amountCyclesToleratingDelay;
		}
	}
	 
 	/**
 	 * get socket
 	 * 
 	 * @return net.forestany.forestj.lib.net.sock.Wrapper&lt;?&gt;
 	 */
 	@SuppressWarnings("unused")
	private net.forestany.forestj.lib.net.sock.Wrapper<?> getSocket() {
 		return this.o_socket;
 	}
 	
 	/**
 	 * set socket
 	 * 
 	 * @param p_o_socket net.forestany.forestj.lib.net.sock.Wrapper&lt;?&gt;
 	 * @throws NullPointerException socket wrapper parameter is null
 	 */
 	public void setSocket(net.forestany.forestj.lib.net.sock.Wrapper<?> p_o_socket) throws NullPointerException {
 		if (p_o_socket == null) {
			throw new NullPointerException("Socket wrapper parameter is null");
		} else {
			this.o_socket = p_o_socket;
		}
 	}
 	
 	/**
 	 * get datagram packet
 	 * 
 	 * @return java.net.DatagramPacket
 	 */
 	@SuppressWarnings("unused")
	private java.net.DatagramPacket getDatagramPacket() {
 		return this.o_datagramPacket;
 	}
 	 	
 	/**
 	 * set datagram packet
 	 * 
 	 * @param p_o_datagrampacket java.net.DatagramPacket
 	 * @throws NullPointerException received datagram parameter is null
 	 */
 	public void setDatagramPacket(java.net.DatagramPacket p_o_datagrampacket) throws NullPointerException {
 		if (p_o_datagrampacket == null) {
			throw new NullPointerException("Received datagram parameter is null");
		} else {
			this.o_datagramPacket = p_o_datagrampacket;
		}
 	}
 	
 	/**
 	 * get queue timeout milliseconds
 	 * 
 	 * @return int
 	 */
 	public int getQueueTimeoutMilliseconds() {
 		return this.i_queueTimeoutMilliseconds;
 	}
 	
 	/**
 	 * set queue timeout milliseconds
 	 * 
 	 * @param p_i_value int
 	 * @throws IllegalArgumentException must be at least '1'
 	 */
 	public void setQueueTimeoutMilliseconds(int p_i_value) throws IllegalArgumentException {
 		/* check timeout value for queue */
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Queue timeout must be at least '1' millisecond, but was set to '" + p_i_value + "' millisecond(s)");
		}
		
		this.i_queueTimeoutMilliseconds = p_i_value;
 	}
 	
 	/**
 	 * get message boxes
 	 * 
 	 * @return list of message boxes
 	 */
 	private java.util.List<net.forestany.forestj.lib.net.msg.MessageBox> getMessageBoxes() {
 		return this.a_messageBoxes;
 	}
 	
 	/**
 	 * set message boxes
 	 * 
 	 * @param p_a_messageBoxes list of message boxes
 	 * @throws NullPointerException message boxes array parameter is null
 	 */
	public void setMessageBoxes(java.util.List<net.forestany.forestj.lib.net.msg.MessageBox> p_a_messageBoxes) throws NullPointerException {
		if (p_a_messageBoxes == null) {
			throw new NullPointerException("Message boxes array parameter is null");
		} else {
			this.a_messageBoxes = p_a_messageBoxes;
		}
	}
	
	/**
	 * add message box
	 * 
	 * @param p_o_messageBox message box instance
	 * @throws NullPointerException message box parameter is null
	 */
	public void addMessageBox(net.forestany.forestj.lib.net.msg.MessageBox p_o_messageBox) throws NullPointerException {
		if (p_o_messageBox == null) {
			throw new NullPointerException("Message box parameter is null");
		}
		
		if (this.a_messageBoxes == null) {
			this.a_messageBoxes = new java.util.ArrayList<net.forestany.forestj.lib.net.msg.MessageBox>();
		}
		
		this.a_messageBoxes.add(p_o_messageBox);
	}
	
	/**
	 * get answer message boxes
	 * 
	 * @return list of message boxes
	 */
	private java.util.List<net.forestany.forestj.lib.net.msg.MessageBox> getAnswerMessageBoxes() {
 		return this.a_answerMessageBoxes;
 	}
	
	/**
 	 * set answer message boxes
 	 * 
 	 * @param p_a_answerMessageBoxes list of message boxes
 	 * @throws NullPointerException answer message boxes array parameter is null
 	 */
	public void setAnswerMessageBoxes(java.util.List<net.forestany.forestj.lib.net.msg.MessageBox> p_a_answerMessageBoxes) throws NullPointerException {
		if (p_a_answerMessageBoxes == null) {
			throw new NullPointerException("Answer message boxes array parameter is null");
		} else {
			this.a_answerMessageBoxes = p_a_answerMessageBoxes;
		}
	}
	
	/**
	 * add answer message box
	 * 
	 * @param p_o_answerMessageBox answer message box instance
	 * @throws NullPointerException answer message box parameter is null
	 */
	public void addAnswerMessageBox(net.forestany.forestj.lib.net.msg.MessageBox p_o_answerMessageBox) throws NullPointerException {
		if (p_o_answerMessageBox == null) {
			throw new NullPointerException("Answer message box parameter is null");
		}
		
		if (this.a_answerMessageBoxes == null) {
			this.a_answerMessageBoxes = new java.util.ArrayList<net.forestany.forestj.lib.net.msg.MessageBox>();
		}
		
		this.a_answerMessageBoxes.add(p_o_answerMessageBox);
	}
	
	/**
	 * get stop flag
	 * 
	 * @return boolean
	 */
	public boolean getStop() {
		return this.b_stop;
	}
	
	/**
	 * get udp receive ack timeout milliseconds
	 * 
	 * @return int
	 */
	public int getUDPReceiveACKTimeoutMilliseconds() {
		return this.i_udpReceiveACKTimeoutMilliseconds;
	}
	
	/**
	 * set udp receive ack timeout milliseconds
	 * 
	 * @param p_i_udpReceiveACKTimeoutMilliseconds int
	 * @throws IllegalArgumentException must be at least '1'
	 */
	public void setUDPReceiveACKTimeoutMilliseconds(int p_i_udpReceiveACKTimeoutMilliseconds) throws IllegalArgumentException {
		if (p_i_udpReceiveACKTimeoutMilliseconds > 0) {
			this.i_udpReceiveACKTimeoutMilliseconds = p_i_udpReceiveACKTimeoutMilliseconds;
		} else {
			throw new IllegalArgumentException("UDP receive ACK timeout must be at least '1' millisecond, but was set to '" + p_i_udpReceiveACKTimeoutMilliseconds + "' millisecond(s)");
		}
	}
	
	/**
	 * get udp send ack timeout milliseconds
	 * 
	 * @return int
	 */
	public int getUDPSendACKTimeoutMilliseconds() {
		return this.i_udpSendACKTimeoutMilliseconds;
	}
	
	/**
	 * set udp send ack timeout milliseconds
	 * 
	 * @param p_i_udpSendACKTimeoutMilliseconds int
	 * @throws IllegalArgumentException must be at least '1'
	 */
	public void setUDPSendACKTimeoutMilliseconds(int p_i_udpSendACKTimeoutMilliseconds) throws IllegalArgumentException {
		if (p_i_udpSendACKTimeoutMilliseconds > 0) {
			this.i_udpSendACKTimeoutMilliseconds = p_i_udpSendACKTimeoutMilliseconds;
		} else {
			throw new IllegalArgumentException("UDP send ACK timeout must be at least '1' millisecond, but was set to '" + p_i_udpSendACKTimeoutMilliseconds + "' millisecond(s)");
		}
	}
	
	/**
	 * get object transmission flag
	 * 
	 * @return boolean
	 */
	public boolean getObjectTransmission() {
		return this.b_objectTransmission;
	}
	
	/**
	 * set object transmission flag
	 * 
	 * @param p_b_objectTransmission boolean
	 */
	public void setObjectTransmission(boolean p_b_objectTransmission) {
		this.b_objectTransmission = p_b_objectTransmission;
	}
	
	/**
	 * get receive socket task
	 * 
	 * @return net.forestany.forestj.lib.net.sock.task.Task&lt;T&gt;
	 */
	private net.forestany.forestj.lib.net.sock.task.Task<T> getReceiveSocketTask() {
		return this.o_receiveSocketTask;
	}
	
	/**
	 * set receive socket task
	 * 
	 * @param p_o_object net.forestany.forestj.lib.net.sock.task.Task&lt;T&gt;
	 * @throws NullPointerException receive socket task parameter for answer is null
	 */
	public void setReceiveSocketTask(net.forestany.forestj.lib.net.sock.task.Task<T> p_o_object) throws NullPointerException {
		if (p_o_object == null) {
			throw new NullPointerException("Receive socket task parameter for answer is null");
		} else {
			this.o_receiveSocketTask = p_o_object;
		}
	}
	
	/**
	 * get answer object
	 * 
	 * @return Object
	 */
	public Object getAnswerObject() {
		return this.o_answerObject;
	}
	
	/**
	 * set answer object
	 * 
	 * @param p_o_object Object
	 * @throws NullPointerException object parameter for answer object is null
	 */
	protected void setAnswerObject(Object p_o_object) throws NullPointerException {
		if (p_o_object == null) {
			throw new NullPointerException("Object parameter for answer object is null");
		} else {
			this.o_answerObject = p_o_object;
		}
	}
	
	/**
	 * get request object
	 * 
	 * @return Object
	 */
	protected Object getRequestObject() {
		return this.o_requestObject;
	}
	
	/**
	 * set request object
	 * 
	 * @param p_o_object Object
	 * @throws NullPointerException object parameter for request object is null
	 */
	public void setRequestObject(Object p_o_object) throws NullPointerException {
		if (p_o_object == null) {
			throw new NullPointerException("Object parameter for request object is null");
		} else {
			this.o_requestObject = p_o_object;
		}
	}
	
	/**
	 * get objects
	 * 
	 * @return list of objects
	 */
	protected java.util.List<Object> getObjects() {
		return this.a_objects;
	}
	
	/**
	 * set objects
	 * 
	 * @param p_a_objects list of objects
	 * @throws NullPointerException list parameter for object list is null
	 */
	protected void setObjects(java.util.List<Object> p_a_objects) throws NullPointerException {
		if (p_a_objects == null) {
			throw new NullPointerException("List parameter for object list is null");
		} else {
			this.a_objects = p_a_objects;
		}
	}
	
	/**
	 * add object
	 * 
	 * @param p_o_object Object
	 * @throws NullPointerException object parameter is null
	 */
	public void addObject(Object p_o_object) throws NullPointerException {
		if (p_o_object == null) {
			throw new NullPointerException("Object parameter is null");
		}
		
		if (this.a_objects == null) {
			this.a_objects = new java.util.ArrayList<Object>();
		}
		
		this.a_objects.add(p_o_object);
	}
	
	/**
	 * get debug network traffic on flag
	 * 
	 * @return boolean
	 */
	protected boolean getDebugNetworkTrafficOn() {
		return this.b_debugNetworkTrafficOn;
	}
	
	/**
	 * set debug network traffic on flag
	 * 
	 * @param p_b_value boolean
	 */
	public void setDebugNetworkTrafficOn(boolean p_b_value) {
		this.b_debugNetworkTrafficOn = p_b_value;
	}
	
	/**
	 * get use marshalling flag
	 * 
	 * @return boolean
	 */
	public boolean getUseMarshalling() {
		return this.b_useMarshalling;
	}
	
	/**
	 * set use marshalling flag
	 * 
	 * @param p_b_value boolean
	 */
	public void setUseMarshalling(boolean p_b_value) {
		this.b_useMarshalling = p_b_value;
	}
	
	/**
	 * get marshalling data length in bytes
	 * 
	 * @return int
	 */
	public int getMarshallingDataLengthInBytes() {
		return this.i_marshallingDataLengthInBytes;
	}
	
	/**
	 * set marshalling data length in bytes
	 * 
	 * @param p_i_value int
	 * @throws IllegalArgumentException must be at least '1', must be lower equal '4'
	 */
	public void setMarshallingDataLengthInBytes(int p_i_value) throws IllegalArgumentException {
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Data length in bytes must be at least '1', but was set to '" + p_i_value + "'");
		}
		
		if (p_i_value > 4) {
			throw new IllegalArgumentException("Data length in bytes must be lower equal '4', but was set to '" + p_i_value + "'");
		}
		
		this.i_marshallingDataLengthInBytes = p_i_value;
	}
	
	/**
	 * get marshalling use property methods flag
	 * 
	 * @return boolean
	 */
	public boolean getMarshallingUsePropertyMethods() {
		return this.b_marshallingUsePropertyMethods;
	}
	
	/**
	 * set marshalling use property methods flag
	 * 
	 * @param p_b_value boolean
	 */
	public void setMarshallingUsePropertyMethods(boolean p_b_value) {
		this.b_marshallingUsePropertyMethods = p_b_value;
	}
	
	/**
	 * get marshalling override messag type
	 * 
	 * @return String
	 */
	public String getMarshallingOverrideMessageType() {
		return this.s_marshallingOverrideMessageType;
	}
	
	/**
	 * set marshalling override messag type
	 * 
	 * @param p_b_value String
	 */
	public void setMarshallingOverrideMessageType(String p_b_value) {
		this.s_marshallingOverrideMessageType = p_b_value;
	}
	
	/**
	 * get marshalling system uses little endian flag
	 * 
	 * @return boolean
	 */
	public boolean getMarshallingSystemUsesLittleEndian() {
		return this.b_marshallingSystemUsesLittleEndian;
	}
	
	/**
	 * set marshalling system uses little endian flag
	 * 
	 * @param p_b_value boolean
	 */
	public void setMarshallingSystemUsesLittleEndian(boolean p_b_value) {
		this.b_marshallingSystemUsesLittleEndian = p_b_value;
	}
	
	/**
	 * set delegate
	 * 
	 * @param p_itf_delegate IDelegate
	 */
	public void setDelegate(IDelegate p_itf_delegate) {
		if (this.e_type == net.forestany.forestj.lib.net.sock.Type.TCP) {
			this.itf_delegate = p_itf_delegate;
		}
	}
	
	/**
	 * get token instance
	 * 
	 * @return Token
	 */
	public Token getToken() {
		return this.o_token;
	}
	
	/**
	 * set token instance
	 * 
	 * @param p_o_value Token
	 */
	public void setToken(Token p_o_value) {
		if (p_o_value == null) {
			throw new NullPointerException("Token instance is null");
		}
		
		this.o_token = p_o_value;
	}
	
	/* Methods */
	
	/**
	 * Creating socket task instance
	  */
	public Task() {
		this.e_type = null;
		this.e_communicationType = null;
		this.e_communicationCardinality = null;
		this.e_communicationSecurity = null;
		this.s_commonSecretPassphrase = null;
		this.o_cryptography = null;
		this.i_bufferLength = 0;
		this.i_receiveMaxUnknownAmount = net.forestany.forestj.lib.net.sock.task.Task.RECEIVE_MAX_UNKNOWN_AMOUNT_IN_MIB;
		this.i_amountCyclesToleratingDelay = net.forestany.forestj.lib.net.sock.task.Task.AMOUNT_CYCLES_TOLERATING_DELAY;
		this.o_socket = null;
		this.o_datagramPacket = null;
		this.i_queueTimeoutMilliseconds = 1;
		this.a_messageBoxes = null;
		this.a_answerMessageBoxes = null;
		this.b_stop = false;
		this.i_udpReceiveACKTimeoutMilliseconds = 1;
		this.i_udpSendACKTimeoutMilliseconds = 1;
		this.b_objectTransmission = false;
		this.o_receiveSocketTask = null;
		this.o_requestObject = null;
		this.o_answerObject = null;
		this.a_objects = null;
		this.b_debugNetworkTrafficOn = false;
		this.b_useMarshalling = false;
		this.i_marshallingDataLengthInBytes = 1;
		this.b_marshallingUsePropertyMethods = false;
		this.s_marshallingOverrideMessageType = null;
		this.b_marshallingSystemUsesLittleEndian = false;
		this.itf_delegate = null;
		this.o_token = null;
	}
	
	/**
	 * Creating socket task instance with socket type parameter
	 * 
	 * @param p_e_type					specifies socket type of socket task
	 */
	public Task(net.forestany.forestj.lib.net.sock.Type p_e_type) {
		this.e_type = p_e_type;
		this.e_communicationType = null;
		this.e_communicationCardinality = null;
		this.e_communicationSecurity = null;
		this.s_commonSecretPassphrase = null;
		this.o_cryptography = null;
		this.i_bufferLength = 0;
		this.i_receiveMaxUnknownAmount = net.forestany.forestj.lib.net.sock.task.Task.RECEIVE_MAX_UNKNOWN_AMOUNT_IN_MIB;
		this.i_amountCyclesToleratingDelay = net.forestany.forestj.lib.net.sock.task.Task.AMOUNT_CYCLES_TOLERATING_DELAY;
		this.o_socket = null;
		this.o_datagramPacket = null;
		this.i_queueTimeoutMilliseconds = 1;
		this.a_messageBoxes = null;
		this.a_answerMessageBoxes = null;
		this.b_stop = false;
		this.i_udpReceiveACKTimeoutMilliseconds = 1;
		this.i_udpSendACKTimeoutMilliseconds = 1;
		this.b_objectTransmission = false;
		this.o_receiveSocketTask = null;
		this.o_requestObject = null;
		this.o_answerObject = null;
		this.a_objects = null;
		this.b_debugNetworkTrafficOn = false;
		this.b_useMarshalling = false;
		this.i_marshallingDataLengthInBytes = 1;
		this.b_marshallingUsePropertyMethods = false;
		this.s_marshallingOverrideMessageType = null;
		this.b_marshallingSystemUsesLittleEndian = false;
		this.itf_delegate = null;
		this.o_token = null;
	}
	
	/**
	 * Creating socket task instance with all it's parameters and settings
	 * 
	 * @param p_e_communicationType					specifies communication type of socket task
	 * @param p_e_communicationCardinality			specifies communication cardinality of socket task
	 * @param p_i_queueTimeoutMilliseconds			determine timeout in milliseconds for sending/receiving bytes
	 * @throws IllegalArgumentException				invalid timeout value for queue
	 */
	public Task(net.forestany.forestj.lib.net.sock.com.Type p_e_communicationType, net.forestany.forestj.lib.net.sock.com.Cardinality p_e_communicationCardinality, int p_i_queueTimeoutMilliseconds) throws IllegalArgumentException {
		this(p_e_communicationType, p_e_communicationCardinality, p_i_queueTimeoutMilliseconds, null);
	}
	
	/**
	 * Creating socket task instance with all it's parameters and settings
	 * 
	 * @param p_e_communicationType					specifies communication type of socket task
	 * @param p_e_communicationCardinality			specifies communication cardinality of socket task
	 * @param p_i_queueTimeoutMilliseconds			determine timeout in milliseconds for sending/receiving bytes
	 * @param p_e_communicationSecurity				specifies communication security of socket task
	 * @throws IllegalArgumentException				invalid timeout value for queue
	 */
	public Task(net.forestany.forestj.lib.net.sock.com.Type p_e_communicationType, net.forestany.forestj.lib.net.sock.com.Cardinality p_e_communicationCardinality, int p_i_queueTimeoutMilliseconds, net.forestany.forestj.lib.net.sock.com.Security p_e_communicationSecurity) throws IllegalArgumentException {
		this(p_e_communicationType, p_e_communicationCardinality, p_i_queueTimeoutMilliseconds, p_e_communicationSecurity, null);
	}
	
	/**
	 * Creating socket task instance with all it's parameters and settings
	 * 
	 * @param p_e_communicationType					specifies communication type of socket task
	 * @param p_e_communicationCardinality			specifies communication cardinality of socket task
	 * @param p_i_queueTimeoutMilliseconds			determine timeout in milliseconds for sending/receiving bytes
	 * @param p_e_communicationSecurity				specifies communication security of socket task
	 * @param p_itf_delegate						interface delegate to post progress of sending/receiving bytes
	 * @throws IllegalArgumentException				invalid timeout value for queue
	 */
	public Task(net.forestany.forestj.lib.net.sock.com.Type p_e_communicationType, net.forestany.forestj.lib.net.sock.com.Cardinality p_e_communicationCardinality, int p_i_queueTimeoutMilliseconds, net.forestany.forestj.lib.net.sock.com.Security p_e_communicationSecurity, IDelegate p_itf_delegate) throws IllegalArgumentException {
		this(p_e_communicationType, p_e_communicationCardinality, p_i_queueTimeoutMilliseconds, p_e_communicationSecurity, p_itf_delegate, false, 1, false, null, false);
	}
	
	/**
	 * Creating socket task instance with all it's parameters and settings
	 * 
	 * @param p_e_communicationType					specifies communication type of socket task
	 * @param p_e_communicationCardinality			specifies communication cardinality of socket task
	 * @param p_i_queueTimeoutMilliseconds			determine timeout in milliseconds for sending/receiving bytes
	 * @param p_e_communicationSecurity				specifies communication security of socket task
	 * @param p_itf_delegate						interface delegate to post progress of sending/receiving bytes
	 * @param p_b_useMarshalling					true - use marshalling methods to transport data over network
	 * @param p_i_marshallingDataLengthInBytes		set data length in bytes for marshalling, must be between [1..4]
	 * @param p_b_marshallingUsePropertyMethods		true - access object parameter fields via property methods e.g. T getXYZ()
	 * @param p_s_marshallingOverrideMessageType	override message type with this string and do not get it automatically from object, thus the type can be set generally from other systems with other programming languages
	 * @param p_b_marshallingSystemUsesLittleEndian	(NOT IMPLEMENTED) true - current execution system uses little endian, false - current execution system uses big endian
	 * @throws IllegalArgumentException				invalid timeout value for queue
	 */
	public Task(net.forestany.forestj.lib.net.sock.com.Type p_e_communicationType, net.forestany.forestj.lib.net.sock.com.Cardinality p_e_communicationCardinality, int p_i_queueTimeoutMilliseconds, net.forestany.forestj.lib.net.sock.com.Security p_e_communicationSecurity, IDelegate p_itf_delegate, boolean p_b_useMarshalling, int p_i_marshallingDataLengthInBytes, boolean p_b_marshallingUsePropertyMethods, String p_s_marshallingOverrideMessageType, boolean p_b_marshallingSystemUsesLittleEndian) throws IllegalArgumentException {
		this.e_type = null;
		this.e_communicationType = p_e_communicationType;
		this.e_communicationCardinality = p_e_communicationCardinality;
		this.e_communicationSecurity = p_e_communicationSecurity;
		this.s_commonSecretPassphrase = null;
		this.o_cryptography = null;
		this.i_bufferLength = 0;
		this.i_receiveMaxUnknownAmount = net.forestany.forestj.lib.net.sock.task.Task.RECEIVE_MAX_UNKNOWN_AMOUNT_IN_MIB;
		this.i_amountCyclesToleratingDelay = net.forestany.forestj.lib.net.sock.task.Task.AMOUNT_CYCLES_TOLERATING_DELAY;
		this.o_socket = null;
		this.o_datagramPacket = null;
		this.i_queueTimeoutMilliseconds = p_i_queueTimeoutMilliseconds;
		this.a_messageBoxes = null;
		this.a_answerMessageBoxes = null;
		this.b_stop = false;
		this.i_udpReceiveACKTimeoutMilliseconds = 1;
		this.i_udpSendACKTimeoutMilliseconds = 1;
		this.b_objectTransmission = false;
		this.o_receiveSocketTask = null;
		this.o_requestObject = null;
		this.o_answerObject = null;
		this.a_objects = null;
		this.b_debugNetworkTrafficOn = false;
		this.b_useMarshalling = p_b_useMarshalling;
		this.i_marshallingDataLengthInBytes = p_i_marshallingDataLengthInBytes;
		this.b_marshallingUsePropertyMethods = p_b_marshallingUsePropertyMethods;
		this.s_marshallingOverrideMessageType = p_s_marshallingOverrideMessageType;
		this.b_marshallingSystemUsesLittleEndian = p_b_marshallingSystemUsesLittleEndian;
		this.itf_delegate = null;
		this.o_token = null;
		
		/* check timeout value for queue */
		if (this.i_queueTimeoutMilliseconds < 1) {
			throw new IllegalArgumentException("Queue timeout must be at least '1' millisecond, but was set to '" + this.i_queueTimeoutMilliseconds + "' millisecond(s)");
		}
		
		if (
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND || 
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE || 
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND_WITH_ACK || 
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE_WITH_ACK || 
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_MULTICAST_SENDER ||
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_MULTICAST_RECEIVER
		) {
			/* set socket type to UDP */
			this.e_type = net.forestany.forestj.lib.net.sock.Type.UDP;
		} else if (
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND || 
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE || 
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND_WITH_ANSWER || 
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE_WITH_ANSWER
		) {
			/* set socket type to TCP and accept delegate parameter */
			this.e_type = net.forestany.forestj.lib.net.sock.Type.TCP;
			this.itf_delegate = p_itf_delegate;
		}
		
		/* log receive socket task settings */
		net.forestany.forestj.lib.Global.ilogConfig("created socket task");
		net.forestany.forestj.lib.Global.ilogConfig("\t" + "communication type:" + "\t\t\t" + this.e_communicationType);
		net.forestany.forestj.lib.Global.ilogConfig("\t" + "communication cardinality:" + "\t\t\t" + this.e_communicationCardinality);
		net.forestany.forestj.lib.Global.ilogConfig("\t" + "queue timeout ms:" + "\t\t\t" + this.i_queueTimeoutMilliseconds);
		net.forestany.forestj.lib.Global.ilogConfig("\t" + "communication security:" + "\t\t\t" + this.e_communicationSecurity);
	}
		
	/**
	 * abstract method to get socket task class type for creating new instances with declared constructor
	 * @return		class type of socket task as Class&lt;?&gt;
	 */
	abstract public Class<?> getSocketTaskClassType();
	
	/**
	 * abstract method to clone this socket task with another socket task instance
	 * @param p_o_sourceTask		another socket task instance as source for all it's parameters and settings
	 */
	abstract public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<T> p_o_sourceTask);
	
	/**
	 * cloning basic fields of a socket task instance with information of another socket task instance
	 * 
	 * @param p_o_sourceTask		another socket task instance as source for all it's parameters and settings
	 */
	protected void cloneBasicFields(net.forestany.forestj.lib.net.sock.task.Task<T> p_o_sourceTask) {
		this.e_type = null;
		this.e_communicationType = null;
		this.e_communicationCardinality = null;
		this.e_communicationSecurity = null;
		this.s_commonSecretPassphrase = null;
		this.o_cryptography = null;
		this.i_bufferLength = 0;
		this.i_receiveMaxUnknownAmount = net.forestany.forestj.lib.net.sock.task.Task.RECEIVE_MAX_UNKNOWN_AMOUNT_IN_MIB;
		this.i_amountCyclesToleratingDelay = net.forestany.forestj.lib.net.sock.task.Task.AMOUNT_CYCLES_TOLERATING_DELAY;
		this.o_socket = null; /* no cloning necessary */
		this.o_datagramPacket = null; /* no cloning necessary */
		this.i_queueTimeoutMilliseconds = 1;
		this.a_messageBoxes = null;
		this.a_answerMessageBoxes = null;
		this.b_stop = false;
		this.i_udpReceiveACKTimeoutMilliseconds = 1;
		this.i_udpSendACKTimeoutMilliseconds = 1;
		this.b_objectTransmission = false;
		this.o_receiveSocketTask = null;
		this.o_requestObject = null; /* no cloning necessary */
		this.o_answerObject = null; /* no cloning necessary */
		this.a_objects = null; /* no cloning necessary */
		this.b_debugNetworkTrafficOn = false;
		this.b_useMarshalling = false;
		this.i_marshallingDataLengthInBytes = 1;
		this.b_marshallingUsePropertyMethods = false;
		this.s_marshallingOverrideMessageType = null;
		this.b_marshallingSystemUsesLittleEndian = false;
		this.itf_delegate = null; /* no cloning necessary */
		this.o_token = null; /* no cloning necessary */
		
		/* ignore exceptions if a property of source task has no valid value, we will keep it null */
		try { this.e_type = p_o_sourceTask.getType(); } catch (Exception o_exc) { /* NOP */ }
		try { this.e_communicationType = p_o_sourceTask.getCommunicationType(); } catch (Exception o_exc) { /* NOP */ }
		try { this.e_communicationCardinality = p_o_sourceTask.getCommunicationCardinality(); } catch (Exception o_exc) { /* NOP */ }
		try { this.e_communicationSecurity = p_o_sourceTask.getCommunicationSecurity(); } catch (Exception o_exc) { /* NOP */ }
		try { this.setCommonSecretPassphrase(p_o_sourceTask.getCommonSecretPassphrase()); } catch (Exception o_exc) { /* NOP */ }
		try { this.setBufferLength(p_o_sourceTask.getBufferLength()); } catch (Exception o_exc) { /* NOP */ }
		try { this.setReceiveMaxUnknownAmountInMiB(p_o_sourceTask.getReceiveMaxUnknownAmountInMiB()); } catch (Exception o_exc) { /* NOP */ }
		try { this.setQueueTimeoutMilliseconds(p_o_sourceTask.getQueueTimeoutMilliseconds()); } catch (Exception o_exc) { /* NOP */ }
		try { this.setMessageBoxes(p_o_sourceTask.getMessageBoxes()); } catch (Exception o_exc) { /* NOP */ }
		try { this.setAnswerMessageBoxes(p_o_sourceTask.getAnswerMessageBoxes()); } catch (Exception o_exc) { /* NOP */ }
		try { this.setUDPReceiveACKTimeoutMilliseconds(p_o_sourceTask.getUDPReceiveACKTimeoutMilliseconds()); } catch (Exception o_exc) { /* NOP */ }
		try { this.setUDPSendACKTimeoutMilliseconds(p_o_sourceTask.getUDPSendACKTimeoutMilliseconds()); } catch (Exception o_exc) { /* NOP */ }
		try { this.setObjectTransmission(p_o_sourceTask.getObjectTransmission()); } catch (Exception o_exc) { /* NOP */ }
		try { this.setReceiveSocketTask(p_o_sourceTask.getReceiveSocketTask()); } catch (Exception o_exc) { /* NOP */ }
		try { this.setDebugNetworkTrafficOn(p_o_sourceTask.getDebugNetworkTrafficOn()); } catch (Exception o_exc) { /* NOP */ }
		try { this.setUseMarshalling(p_o_sourceTask.getUseMarshalling()); } catch (Exception o_exc) { /* NOP */ }
		try { this.setMarshallingDataLengthInBytes(p_o_sourceTask.getMarshallingDataLengthInBytes()); } catch (Exception o_exc) { /* NOP */ }
		try { this.setMarshallingUsePropertyMethods(p_o_sourceTask.getMarshallingUsePropertyMethods()); } catch (Exception o_exc) { /* NOP */ }
		try { this.setMarshallingOverrideMessageType(p_o_sourceTask.getMarshallingOverrideMessageType()); } catch (Exception o_exc) { /* NOP */ }
		try { this.setMarshallingSystemUsesLittleEndian(p_o_sourceTask.getMarshallingSystemUsesLittleEndian()); } catch (Exception o_exc) { /* NOP */ }
		
		if (
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND || 
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE || 
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND_WITH_ACK || 
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE_WITH_ACK || 
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_MULTICAST_SENDER ||
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_MULTICAST_RECEIVER
		) {
			/* set socket type to UDP */
			this.e_type = net.forestany.forestj.lib.net.sock.Type.UDP;
		} else if (
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND || 
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE || 
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND_WITH_ANSWER || 
			this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE_WITH_ANSWER
		) {
			/* set socket type to TCP and accept delegate parameter */
			this.e_type = net.forestany.forestj.lib.net.sock.Type.TCP;
		}
	}
	
	/**
	 * abstract runTask method of socket task which can always vary depending on the implementation
	 * @throws Exception	any exception of implementation that could happen will be caught
	 */
	abstract protected void runTask() throws Exception;
	
	/**
	 * Core execution process method of a socket task, checking all settings before executing abstract runTask() method
	 * 
	 * @throws NullPointerException			class property is not set
	 * @throws IllegalArgumentException		invalid value for buffer length [lower than 1] or common secret passphrase length [null or length lower than 36 characters]
	 */
	@Override
	public void run() {
		try {
													net.forestany.forestj.lib.Global.ilogFinest("net.sock.task.Task run-method called");
			
			if ( (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE) || (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_RECEIVE_WITH_ACK) || (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_MULTICAST_RECEIVER) ) {
				if (this.o_datagramPacket == null) {
					throw new NullPointerException("Received datagram packet is null");
				}
				
				if (this.a_messageBoxes == null) {
					throw new NullPointerException("Message boxes are null");	
				}
			} else if ( (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND) || (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_SEND_WITH_ACK) || (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.UDP_MULTICAST_SENDER) ) {
				if (this.o_socket.getSocket() == null) {
					throw new NullPointerException("Datagram socket is null");
				}
				
				if (this.o_datagramPacket == null) {
					throw new NullPointerException("Datagram packet is null");
				}
				
				if (this.a_messageBoxes == null) {
					throw new NullPointerException("Message boxes are null");	
				}
			} else if (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE) {
				if (this.o_socket.getSocket() == null) {
					throw new NullPointerException("TCP socket is null");
				}
				
				if (this.i_bufferLength < 1) {
					throw new IllegalArgumentException("Buffer length must be at least '1', but was set to '" + this.i_bufferLength + "'");
				}
				
				if (this.a_messageBoxes == null) {
					throw new NullPointerException("Message boxes are null");	
				}
			} else if (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND) {
				if (this.o_socket.getSocket() == null) {
					throw new NullPointerException("TCP socket is null");
				}
				
				if (this.i_bufferLength < 1) {
					throw new IllegalArgumentException("Buffer length must be at least '1', but was set to '" + this.i_bufferLength + "'");
				}
				
				if (this.a_messageBoxes == null) {
					throw new NullPointerException("Message boxes are null");	
				}
			} else if (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.TCP_RECEIVE_WITH_ANSWER) {
				if (this.o_socket.getSocket() == null) {
					throw new NullPointerException("TCP socket is null");
				}
				
				if (this.i_bufferLength < 1) {
					throw new IllegalArgumentException("Buffer length must be at least '1', but was set to '" + this.i_bufferLength + "'");
				}
				
				if (this.a_messageBoxes == null) {
					throw new NullPointerException("Message boxes are null");	
				}
				
				if (this.o_receiveSocketTask == null) {
					throw new NullPointerException("Receive socket task is null");
				}
			} else if (this.e_communicationType == net.forestany.forestj.lib.net.sock.com.Type.TCP_SEND_WITH_ANSWER) {
				if (this.o_socket.getSocket() == null) {
					throw new NullPointerException("TCP socket is null");
				}
				
				if (this.i_bufferLength < 1) {
					throw new IllegalArgumentException("Buffer length must be at least '1', but was set to '" + this.i_bufferLength + "'");
				}
				
				if (this.a_messageBoxes == null) {
					throw new NullPointerException("Message boxes are null");	
				}
				
				if (this.a_answerMessageBoxes == null) {
					throw new NullPointerException("Answer message boxes are null");	
				}
			}
			
			if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
				if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_commonSecretPassphrase)) {
					throw new IllegalArgumentException("You have not specified a common secret passphrase for using symmetric 128/256-bit communication security");
				}
				
				if (this.s_commonSecretPassphrase.length() < 36) {
					throw new IllegalArgumentException("Common secret passphrase must have at least '36' characters, but has '" + this.s_commonSecretPassphrase.length() + "' characters");
				}
				
				if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
					if (this.o_cryptography == null) {
						throw new NullPointerException("Cryptography object is not initialized");
					}
				}
			}
			
			try {
														net.forestany.forestj.lib.Global.ilogFinest("net.sock.task.Task execute abstract runTask-method");
				
				/* execute abstract runTask method so the core execution of socket task can always vary depending on the implementation  */
				this.runTask();
			} catch (javax.net.ssl.SSLException o_exc) {
				/* handle javax.net.ssl.SSLException within abstract part of socket task */
				net.forestany.forestj.lib.Global.logException("javax.net.ssl.SSLException: ", o_exc);
			}
		} catch (Exception o_exc) {
			/* handle any exception of socket task */
			net.forestany.forestj.lib.Global.logException(o_exc);
		}
	}
	
	/**
	 * check if socket task has messages available in any configured message box
	 * @return		true - message available, false - no message available in any message box, so maybe time for a timeout
	 */
	public boolean messagesAvailable() {
		/* there is not even message box array initialized, so returning false */
		if (this.a_messageBoxes == null) {
			return false;
		}
		
		boolean b_return = false;
		
		/* iterate each message box in array */
		for (net.forestany.forestj.lib.net.msg.MessageBox o_messageBox : this.a_messageBoxes) {
			/* return true if any message box has a message */
			if (o_messageBox.getMessageAmount() > 0) {
				b_return = true;
				break;
			}
		}
		
		return b_return;
	}
	
	/**
	 * Protocol method receive amount of bytes between two socket instances
	 * 
	 * @return														amount of bytes which will be send from other communication side
	 * @throws IllegalArgumentException								invalid amount of bytes parameter or byte array for encryption is empty
	 * @throws IllegalStateException								AmountBytesProtocol failed after several attempts
	 * @throws UnsupportedOperationException						not implemented for UDP protocol
	 * @throws Exception											any other Exception, see below in other classes for details
	 * @see java.lang.Thread										java.lang.Thread - sleep method
	 * @see net.forestany.forestj.lib.Cryptography								net.forestany.forestj.lib.Cryptography - Decrypt_AES_GCM, Decrypt, Encrypt_AES_GCM and Encrypt methods
	 * @see net.forestany.forestj.lib.net.sock.task.Task							net.forestany.forestj.lib.net.sock.task.Task - SendBytes, ReceiveBytes, SendACK and ReceiveACK methods
	 */
	protected int amountBytesProtocol() throws IllegalArgumentException, IllegalStateException, UnsupportedOperationException, Exception {
		return this.amountBytesProtocol(0);
	}
	
	/**
	 * Protocol method to send or receive amount of bytes between two socket instances
	 * 
	 * @param p_b_bytesAmount										amount of bytes we want to sent to other communication side, or 0 if we want to receive amount of bytes information
	 * @return														amount of bytes which will be send from other communication side
	 * @throws IllegalArgumentException								invalid amount of bytes parameter or byte array for encryption is empty
	 * @throws IllegalStateException								AmountBytesProtocol failed after several attempts
	 * @throws UnsupportedOperationException						not implemented for UDP protocol
	 * @throws Exception											any other Exception, see below in other classes for details
	 * @see java.lang.Thread										java.lang.Thread - sleep method
	 * @see net.forestany.forestj.lib.Cryptography								net.forestany.forestj.lib.Cryptography - Decrypt_AES_GCM, Decrypt, Encrypt_AES_GCM and Encrypt methods
	 * @see net.forestany.forestj.lib.net.sock.task.Task							net.forestany.forestj.lib.net.sock.task.Task - SendBytes, ReceiveBytes, SendACK and ReceiveACK methods
	 */
	protected int amountBytesProtocol(int p_b_bytesAmount) throws IllegalArgumentException, IllegalStateException, UnsupportedOperationException, Exception {
		int i_return = -1;
		
		/* only TCP is supported */
		if (this.e_type == net.forestany.forestj.lib.net.sock.Type.TCP) {
			/* parameter value is lesser equal 0, then we want to receive amount of bytes incoming next */
			if (p_b_bytesAmount <= 0) {
				/* help variables */
				byte by_length = 0;
				int i_maxAttempts = 40;
				int i_attempts = 1;
				
				/* receive bytes length, to know in how many bytes the transmission length is encoded */
				do {
					/* byte array for receiving amount of bytes which hold transmission length */
					byte[] a_length = null;
					
					/* receive length, expecting different amount of bytes because of encryption */
					if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) ) {
						a_length = this.receiveBytes(33, 33);
					} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
						a_length = this.receiveBytes(17, 17);
					} else {
						a_length = this.receiveBytes(1, 1);
					}
					
					/* check if we received any bytes */
					if (a_length.length > 0) {
						/* log received bytes length if no encryption is active */
						if ( (this.e_communicationSecurity != net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) && (this.e_communicationSecurity != net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) && (this.e_communicationSecurity != net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) && (this.e_communicationSecurity != net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
																	net.forestany.forestj.lib.Global.ilogFiner("Receiving bytes length: " + net.forestany.forestj.lib.Helper.printByteArray(a_length, false));
						}
						
						/* decrypt received bytes length if encryption is active */
						if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) {
							a_length = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_length, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
						} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) {
							a_length = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_length, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
						} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
							a_length = this.o_cryptography.decrypt(a_length);
						}
						
						/* log received bytes length if encryption is active */
						if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
																	net.forestany.forestj.lib.Global.ilogFiner("Receiving bytes length: " + net.forestany.forestj.lib.Helper.printByteArray(a_length, false));
						}
						
						by_length = a_length[0];
					}
					
					/* bytes length must be greater than 0 */
					if (by_length == 0) {
																net.forestany.forestj.lib.Global.ilogFiner("Length[" + by_length + "] of transmission length must be greater than 0; retry after 25 millisecond");
						
						/* wait 25 milliseconds to receive length again */
						Thread.sleep(25);
						
						if (i_attempts >= i_maxAttempts) { /* all attempts failed, so protocol for receiving length failed completely or was not intended (check availability call over TCP) */
							net.forestany.forestj.lib.Global.ilogWarning("AmountBytesProtocol " + i_attempts + " attempts");
							return 0;
						}
					}
					
					i_attempts++;
				} while (by_length == 0);
				
				/* ------------------------------------------------------ */
				
				/* send acknowledge that bytes length has been received */
				this.sendACK();
				
	            /* ------------------------------------------------------ */
	            
				/* byte array for receiving transmission length */
				byte[] a_transmissionLength = null;
				
				/* receive transmission length, expecting different amount of bytes because of encryption */
				if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) ) {
					a_transmissionLength = this.receiveBytes(by_length + 28, by_length + 28);
				} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
					a_transmissionLength = this.receiveBytes(by_length + 16, by_length + 16);
				} else {
					a_transmissionLength = this.receiveBytes(by_length, by_length);
				}
				
				/* log received transmission length if no encryption is active */
				if ( (this.e_communicationSecurity != net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) && (this.e_communicationSecurity != net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) && (this.e_communicationSecurity != net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) && (this.e_communicationSecurity != net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
															net.forestany.forestj.lib.Global.ilogFiner("Receiving transmission length: " + net.forestany.forestj.lib.Helper.printByteArray(a_transmissionLength, false));
				}
				
				/* decrypt received transmission length if encryption is active */
				if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) {
					a_transmissionLength = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_transmissionLength, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
				} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) {
					a_transmissionLength = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_transmissionLength, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
				} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
					a_transmissionLength = this.o_cryptography.decrypt(a_transmissionLength);
				}
				
				/* log received transmission length if no encryption is active */
				if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
															net.forestany.forestj.lib.Global.ilogFiner("Receiving transmission length: " + net.forestany.forestj.lib.Helper.printByteArray(a_transmissionLength, false));
				}
				
				/* save transmission length as integer for return value */
	            i_return = net.forestany.forestj.lib.Helper.byteArrayToInt(a_transmissionLength);
				
	            /* ------------------------------------------------------ */
	            
	            /* send acknowledge that transmission length has been received */
	            this.sendACK();
			} else {
				/* amount of bytes parameter must not exceed max. value of integer */
				if (p_b_bytesAmount > 2147483646) {
					throw new IllegalArgumentException("Max. amount of data bytes is '2.147.483.646 bytes (2,15 GB)', but parameter amount of input data bytes is '" + p_b_bytesAmount + "'");
				}
				
				/* byte array for sending bytes length of transmission length */
				byte[] a_length = new byte[]{1};
				/* amount of bytes for transmission length */
				int i_length = 0;
				
				if (p_b_bytesAmount > 255) {
					a_length[0]++;
				}
				
				if (p_b_bytesAmount > 65535) {
					a_length[0]++;
				}
				
				if (p_b_bytesAmount > 16777215) {
					a_length[0]++;
				}
				
				/* update amount of bytes */
				i_length = a_length[0];
				
														net.forestany.forestj.lib.Global.ilogFiner("Sending length: " + net.forestany.forestj.lib.Helper.printByteArray(a_length, false));
				
				/* encrypt amount of bytes of transmission length if encryption is active */
				if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) {
					a_length = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(a_length, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
				} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) {
					a_length = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(a_length, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
				} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
					a_length = this.o_cryptography.encrypt(a_length);
				}
				
				/* sending amount of bytes of transmission length */
				this.sendBytes(a_length, a_length.length);
	            
	            /* ------------------------------------------------------ */
	            
				/* receive acknowledge that bytes length has been received correctly */
	            this.receiveACK();
				
				/* ------------------------------------------------------ */
				
	            /* byte array for sending transmission length */
				byte[] a_transmissionLength = net.forestany.forestj.lib.Helper.intToByteArray(p_b_bytesAmount);
				
				if (a_transmissionLength.length != i_length) {
					throw new IllegalStateException("Transmission length amount of bytes[" + a_transmissionLength.length + "] is not equal to calculated amount of bytes for transmission length[" + i_length + "] before");
				}
				
														net.forestany.forestj.lib.Global.ilogFiner("Sending transmission length: " + net.forestany.forestj.lib.Helper.printByteArray(a_transmissionLength, false));
	            
				/* encrypt transmission length if encryption is active */
				if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) {
					a_transmissionLength = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(a_transmissionLength, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
				} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) {
					a_transmissionLength = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(a_transmissionLength, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
				} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
					a_transmissionLength = this.o_cryptography.encrypt(a_transmissionLength);
				}
				
				/* sending transmission length */
				this.sendBytes(a_transmissionLength, a_transmissionLength.length);
	            
	            /* ------------------------------------------------------ */
	            
				/* receive acknowledge that transmission length has been received correctly */
	            this.receiveACK();
			}
		} else if (this.e_type == net.forestany.forestj.lib.net.sock.Type.UDP) { /* UDP is not supported */
			throw new UnsupportedOperationException("Not implemented for UDP protocol");
		} else {
			throw new UnsupportedOperationException("Not implemented");
		}
		
		return i_return;
	}

	/**
	 * Sending acknowledge(ACK) to other communication side
	 * 
	 * @throws UnsupportedOperationException						not implemented for UDP protocol
	 * @throws Exception											any other Exception, see below in other classes for details
	 * @see net.forestany.forestj.lib.Cryptography								net.forestany.forestj.lib.Cryptography - Decrypt_AES_GCM, Decrypt, Encrypt_AES_GCM and Encrypt methods
	 */
	protected void sendACK() throws UnsupportedOperationException, Exception {
		/* UDP is not supported */
		if (this.e_type != net.forestany.forestj.lib.net.sock.Type.TCP) {
			throw new UnsupportedOperationException("Not implemented for UDP protocol");
		}
		
		/* byte array for sending ACK */
		byte[] a_ack = new byte[] {Task.BY_ACK_BYTE};
		
												net.forestany.forestj.lib.Global.ilogFiner("Sending ACK: " + net.forestany.forestj.lib.Helper.printByteArray(a_ack, false));
		
		/* encrypt ACK if encryption is active */
		if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) {
			a_ack = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(a_ack, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
		} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) {
			a_ack = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(a_ack, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
		} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
			a_ack = this.o_cryptography.encrypt(a_ack);
		}
		
		/* sending ACK */
		this.sendBytes(a_ack, a_ack.length);
	}
	
	/**
	 * Receiving acknowledge(ACK) from other communication side
	 * 
	 * @throws UnsupportedOperationException						not implemented for UDP protocol
	 * @throws Exception											any other Exception, see below in other classes for details
	 * @see java.lang.Thread										java.lang.Thread - sleep method
	 * @see net.forestany.forestj.lib.Cryptography								net.forestany.forestj.lib.Cryptography - Decrypt_AES_GCM, Decrypt, Encrypt_AES_GCM and Encrypt methods
	 */
	protected void receiveACK() throws UnsupportedOperationException, Exception {
		/* UDP is not supported */
		if (this.e_type != net.forestany.forestj.lib.net.sock.Type.TCP) {
			throw new UnsupportedOperationException("Not implemented for UDP protocol");
		}
		
		/* help variables */
		byte[] a_ack = null;
		int i_maxAttempts = 40;
		int i_attempts = 1;
		
		do {
			a_ack = null;
			
			/* receive ACK, expecting different amount of bytes because of encryption */
			if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) ) {
				a_ack = this.receiveBytes(33, 33);
			} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
				a_ack = this.receiveBytes(17, 17);
			} else {
				a_ack = this.receiveBytes(1, 1);
			}
			
			/* decrypt received ACK if encryption is active */
			if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) {
				a_ack = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_ack, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
			} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) {
				a_ack = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_ack, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
			} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
				a_ack = this.o_cryptography.decrypt(a_ack);
			}
			
													net.forestany.forestj.lib.Global.ilogFiner("Received ACK: " + net.forestany.forestj.lib.Helper.printByteArray(a_ack, false));
			
			/* received ACK must match expected ACK */
			if (a_ack[0] != Task.BY_ACK_BYTE) {
														net.forestany.forestj.lib.Global.ilogWarning("Invalid ACK[" + net.forestany.forestj.lib.Helper.printByteArray(a_ack, false).trim() + "], must be [" + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {Task.BY_ACK_BYTE}, false).trim() + "]; retry after 25 milliseconds");
				
				/* wait 25 milliseconds to receive length again */
				Thread.sleep(25);
				
				if (i_attempts >= i_maxAttempts) { /* all attempts failed, so protocol for receiving ACK failed completely */
					throw new IllegalStateException("Receiving ACK failed after " + i_attempts + " attempts");
				}
			}
			
			i_attempts++;
		} while (a_ack[0] != Task.BY_ACK_BYTE);
	}
	
	/**
	 * Send data to output stream of socket instance, using buffer length from Task instance
	 * 
	 * @param p_a_data							content data which will be uploaded
	 * @throws UnsupportedOperationException	not implemented for UDP protocol
	 * @throws java.io.IOException				issue sending to output stream object instance
	 */
	protected void sendBytes(byte[] p_a_data) throws UnsupportedOperationException, java.io.IOException {
		this.sendBytes(p_a_data, this.i_bufferLength);
	}
	
	/**
	 * Send data to output stream of socket instance
	 * 
	 * @param p_a_data							content data which will be uploaded
	 * @param p_i_bufferLength					size of buffer which is used send the output stream
	 * @throws UnsupportedOperationException	not implemented for UDP protocol
	 * @throws java.io.IOException				issue sending to output stream object instance
	 */
	protected void sendBytes(byte[] p_a_data, int p_i_bufferLength) throws UnsupportedOperationException, java.io.IOException {
		if (p_i_bufferLength < 1) {
			throw new java.io.IOException("buffer length(sending) must be greater than 0, but it is '" + p_i_bufferLength + "'");
		}
		
		if (this.e_type == net.forestany.forestj.lib.net.sock.Type.TCP) {
			/* create sending buffer and help variables */
			int i_sendDataPointer = 0;
			byte[] a_buffer = new byte[p_i_bufferLength];
			
			/* create clean buffer */
			for (int i = 0; i < p_i_bufferLength; i++) {
				a_buffer[i] = 0;
			}
			
			int i_cycles = (int)java.lang.Math.ceil( ((double)p_a_data.length / (double)p_i_bufferLength) );
			int i_sum = 0;
			int i_progressThreshold = 0;
			
													if (this.b_debugNetworkTrafficOn) net.forestany.forestj.lib.Global.ilogFiner("Iterate '" + i_cycles + "' cycles to transport '" + p_a_data.length + "' bytes with '"  + p_i_bufferLength + "' bytes buffer");
		
			/* iterate cycles to send bytes with buffer */
			for (int i = 0; i < i_cycles; i++) {
				int i_bytesSend = 0;
				
				/* copy data to our buffer until buffer length or overall data length has been reached */
				for (int j = 0; j < p_i_bufferLength; j++) {
					if (i_sendDataPointer >= p_a_data.length) {
																net.forestany.forestj.lib.Global.ilogFinest("Send data pointer '" + i_sendDataPointer + "' >= amount of total bytes '" + p_a_data.length + "' to transport");
						
						break;
					}
					
															if ((this.b_debugNetworkTrafficOn) && (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS))) net.forestany.forestj.lib.Global.ilogMass("Writing byte[" + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {p_a_data[i_sendDataPointer]}, false) + "] to buffer[" + j + "]");
					
					a_buffer[j] = p_a_data[i_sendDataPointer++];
					i_bytesSend++;
				}
			
				/* send to output stream with buffer counter value */
				((java.net.Socket)this.o_socket.getSocket()).getOutputStream().write(a_buffer);
				((java.net.Socket)this.o_socket.getSocket()).getOutputStream().flush();
				
														net.forestany.forestj.lib.Global.ilogFinest("Sended data, cycle '" + (i + 1) + "' of '" + i_cycles + "', amount of bytes: " + i_bytesSend);
			
				/* update delegate for progress or own console progress bar instance */
				if (this.itf_delegate != null) {
		        	i_sum += i_bytesSend;
		        	i_progressThreshold += i_bytesSend;
		        	
		        	/* only post progress if a threshold of 1KB has been made */
		        	if (i_progressThreshold >= 102400) {
		        		this.itf_delegate.PostProgress(i_sum, p_a_data.length);
		        		i_progressThreshold -= 102400;
		        	}
		        }
			}
		} else if (this.e_type == net.forestany.forestj.lib.net.sock.Type.UDP) {
			throw new UnsupportedOperationException("Not implemented for UDP protocol");
		} else {
			throw new UnsupportedOperationException("Not implemented");
		}
	}
	
	/**
	 * Read data from input stream of socket instance, using buffer length from Task instance
	 * 
	 * @param p_i_amountBytes					amount of bytes we expect to read from input stream
	 * @return									input stream content - array of bytes
	 * @throws UnsupportedOperationException	not implemented for UDP protocol
	 * @throws java.io.IOException				issue reading from input stream object from socket instance
	 */
	protected byte[] receiveBytes(int p_i_amountBytes) throws UnsupportedOperationException, java.io.IOException {
		return receiveBytes(p_i_amountBytes, this.i_bufferLength);
	}
	
	/**
	 * Read data from input stream socket instance
	 * 
	 * @param p_i_amountBytes					amount of bytes we expect to read from input stream
	 * @param p_i_bufferLength					size of buffer which is used reading the input stream
	 * @return									input stream content - array of bytes
	 * @throws UnsupportedOperationException	not implemented for UDP protocol
	 * @throws java.io.IOException				issue reading from input stream object from socket instance
	 */
	protected byte[] receiveBytes(int p_i_amountBytes, int p_i_bufferLength) throws UnsupportedOperationException, java.io.IOException {
		return receiveBytes(p_i_amountBytes, p_i_bufferLength, false);
	}
	
	/**
	 * Read data from input stream socket instance
	 * 
	 * @param p_i_amountBytes					amount of bytes we expect to read from input stream
	 * @param p_i_bufferLength					size of buffer which is used reading the input stream
	 * @param p_b_recursive						true - receiveBytes is called from receiveBytes, false - receiveBytes is called from outside
	 * @return									input stream content - array of bytes
	 * @throws UnsupportedOperationException	not implemented for UDP protocol
	 * @throws java.io.IOException				issue reading from input stream object from socket instance
	 */
	private byte[] receiveBytes(int p_i_amountBytes, int p_i_bufferLength, boolean p_b_recursive) throws UnsupportedOperationException, java.io.IOException {
		if (p_i_bufferLength < 1) {
			throw new java.io.IOException("buffer length(receiving) must be greater than 0, but it is '" + p_i_bufferLength + "'");
		}
		
		byte[] a_receivedData = null;
		
		if (this.e_type == net.forestany.forestj.lib.net.sock.Type.TCP) {
			/* if we do not know how much bytes we will read, we have to detect EOT ourselves */
			boolean b_readUnknownAmount = false;
			
			/* if we could not get amount of bytes as parameter we try to get it from input stream */
			if (p_i_amountBytes <= 0) {
				p_i_amountBytes = ((java.net.Socket)this.o_socket.getSocket()).getInputStream().available();
				
				/* if we still have no exact information how many bytes we will receive, we must read from input stream with configured max. limit */
				if (p_i_amountBytes <= 0) {
					p_i_amountBytes = this.i_receiveMaxUnknownAmount * 1024 * 1024;
					b_readUnknownAmount = true;
				}
			}

			/* create receiving byte array, buffer and help variables */
			a_receivedData = new byte[p_i_amountBytes];
			int i_receivedDataPointer = 0;
			byte[] a_buffer = new byte[p_i_bufferLength];
			int i_cycles = (int)java.lang.Math.ceil( ((double)p_i_amountBytes / (double)p_i_bufferLength) );
			int i_sum = 0;
			int i_sumProgress = 0;
			int i_progressThreshold = 0;
			
													if (this.b_debugNetworkTrafficOn) net.forestany.forestj.lib.Global.ilogFiner("Iterate '" + i_cycles + "' cycles to receive '" + p_i_amountBytes + "' bytes with '"  + p_i_bufferLength + "' bytes buffer");
													
			/* iterate cycles to receive bytes with buffer */
			for (int i = 0; i < i_cycles; i++) {
				int i_expectedBytes = p_i_bufferLength;
		        int i_bytesReaded = -1;

		        										if (this.b_debugNetworkTrafficOn) net.forestany.forestj.lib.Global.ilogFiner("this.o_socket.getSocket()).getInputStream().read, expecting " + i_expectedBytes + " bytes with read timeout '" + ((java.net.Socket)this.o_socket.getSocket()).getSoTimeout() + " ms'");
		        
		        /* read from input stream until amount of expected bytes has been reached, or we reached EOT */
		        while ( (i_expectedBytes > 0) && ( (i_bytesReaded = ((java.net.Socket)this.o_socket.getSocket()).getInputStream().read(a_buffer, 0, Math.min(i_expectedBytes, a_buffer.length))) > 0 ) ) {
		        											if (this.b_debugNetworkTrafficOn) net.forestany.forestj.lib.Global.ilogFiner("this.o_inputStream.read, readed " + i_bytesReaded + " bytes of expected " + i_expectedBytes + " bytes");
		        											
		        	i_expectedBytes -= i_bytesReaded;
		            
		            if (i_bytesReaded < 0) {
						throw new IllegalStateException("Could not receive data");
					} else {
						/* copy received bytes to return byte array value */
						for (int j = 0; j < i_bytesReaded; j++) {
							if (i_receivedDataPointer >= a_receivedData.length) {
																		if (this.b_debugNetworkTrafficOn) net.forestany.forestj.lib.Global.ilogFinest("Receive data pointer '" + i_receivedDataPointer + "' >= amount of total bytes '" + a_receivedData.length + "' received");
								
								break;
							}
		
																	if ((this.b_debugNetworkTrafficOn) && (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS))) net.forestany.forestj.lib.Global.ilogMass("Writing byte[" + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {a_buffer[j]}, false) + "] to receivedData[" + i_receivedDataPointer + "]");
																	
							a_receivedData[i_receivedDataPointer++] = a_buffer[j];
						}
						
						i_sum += i_bytesReaded;
						
																if (this.b_debugNetworkTrafficOn) net.forestany.forestj.lib.Global.ilogFiner("Received data, cycle '" + (i + 1) + "' of '" + i_cycles + "', amount of bytes readed: " + i_bytesReaded + ", expected bytes: " + i_expectedBytes + ", sum: " + i_sum);
					}
		            
		            										if (this.b_debugNetworkTrafficOn) net.forestany.forestj.lib.Global.ilogFiner("do we still miss some bytes after read? -> " + ( (i_expectedBytes > 0) && (i_sum != p_i_amountBytes) && (!p_b_recursive) ));
		            
		            /* flag to know when tolerance loops are finished */
		            boolean b_waitedTolerance = false;
		            										
		            /* only if current call is not recursive: we are still expecting some bytes to come, maybe incoming transfer with unknown amount of bytes and some delay in TCP transport */
			        if ( (i_expectedBytes > 0) && (i_sum != p_i_amountBytes) && (!p_b_recursive) ) {
			        											if (this.b_debugNetworkTrafficOn) net.forestany.forestj.lib.Global.ilogFiner("(i_expectedBytes > 0) -> " + (i_expectedBytes > 0) + "\t(i_sum != p_i_amountBytes) -> " + (i_sum != p_i_amountBytes) + "\t(!p_b_recursive) -> " + (!p_b_recursive));

		        		/* prepare receiving buffer for rest of expected bytes */
		        		byte[] a_receivedAnotherTry = new byte[i_expectedBytes];
		        		/* save old timeout value for socket */
			        	int i_oldTimeout = ((java.net.Socket)this.o_socket.getSocket()).getSoTimeout();
			        	
			        											if (this.b_debugNetworkTrafficOn) net.forestany.forestj.lib.Global.ilogFiner("set small timeout value for socket as " + net.forestany.forestj.lib.net.sock.task.Task.TOLERATING_DELAY_IN_MS + " milliseconds");
			        	
			        	/* set small timeout value for socket as TOLERATING_DELAY_IN_MS milliseconds */
			        	((java.net.Socket)this.o_socket.getSocket()).setSoTimeout(net.forestany.forestj.lib.net.sock.task.Task.TOLERATING_DELAY_IN_MS);
			        	
			        	/* accept delay communication with a loop with small amount of iterations as tolerance, each loop with TOLERATING_DELAY_IN_MS milliseconds timeout */
			        	for (int j = 0; j < this.i_amountCyclesToleratingDelay; j++) {
			        		try {
			        													if (this.b_debugNetworkTrafficOn) net.forestany.forestj.lib.Global.ilogFiner("receive rest of expected bytes with "+ (j + 1) + " cycle of " + this.i_amountCyclesToleratingDelay + " cycles");
			        			
			        			/* receive rest of expected bytes */
			        			a_receivedAnotherTry = this.receiveBytes(i_expectedBytes, i_expectedBytes, true);
			        			
			        			/* copy received bytes to received data buffer */
			        			for (int k = 0; k < a_receivedAnotherTry.length; k++) {
			        				if (i_receivedDataPointer >= a_receivedData.length) {
																				if (this.b_debugNetworkTrafficOn) net.forestany.forestj.lib.Global.ilogFinest("Receive data pointer '" + i_receivedDataPointer + "' >= amount of total bytes '" + a_receivedData.length + "' received");

										break;
			        				}

																			if ((this.b_debugNetworkTrafficOn) && (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS))) net.forestany.forestj.lib.Global.ilogMass("Writing byte[" + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {a_buffer[j]}, false) + "] to receivedData[" + i_receivedDataPointer + "]");
									
									a_receivedData[i_receivedDataPointer++] = a_receivedAnotherTry[k];
			        			}
			        			
			        			/* update variable for bytes readed, sum and expected bytes */
			        			i_bytesReaded += a_receivedAnotherTry.length;
			        			i_sum += a_receivedAnotherTry.length;
			        			i_expectedBytes -= a_receivedAnotherTry.length;
			        			
			        			/* if amount of missing expected bytes have been received, we do not need to continue our tolerance loop */
			        			if (i_expectedBytes < 1) {
			        														if (this.b_debugNetworkTrafficOn) net.forestany.forestj.lib.Global.ilogFiner("amount of missing expected bytes have been received, we do not need to continue our tolerance loop");
			        				
			        				break;
								}
			        		} catch (Exception o_exc) {
			        			/* ignore read timeout or any failed receive */
			        		}
			        	}
			        	
			        	b_waitedTolerance = true;

			        	/* restore old timeout value for socket */
			        	((java.net.Socket)this.o_socket.getSocket()).setSoTimeout(i_oldTimeout);
			        }
			        
			        /* we are still have expected bytes to receive and waited all tolerance cycles -> the transfer might be finished and we can break the while loop */
			        if ( (i_expectedBytes > 0) && (b_waitedTolerance) ) {
			        	break;
			        }
			        
			        /* if we reached our amount of bytes we expect to read, we can set expected bytes counter to zero */
			        if (i_sum == p_i_amountBytes) {
			            i_expectedBytes = 0;
			        }
		        }
		        
		        /* update delegate for progress or own console progress bar instance */
		        if (this.itf_delegate != null) {
		        	i_sumProgress += i_bytesReaded;
		        	i_progressThreshold += i_bytesReaded;
		        	
		        	/* only post progress if a threshold of 1KB has been made */
		        	if (i_progressThreshold >= 102400) {
		        		this.itf_delegate.PostProgress(i_sumProgress, p_i_amountBytes);
		        		i_progressThreshold -= 102400;
		        	}
		        }
		        
		        /* we are still have expected bytes to receive and unknown amount of incoming data + tried tolerance delay or recursive call -> the transfer might be finished and we can break the for loop as well */
		        if ( (i_expectedBytes > 0) && ((b_readUnknownAmount) || (p_b_recursive)) ) {
		        	break;
		        }
			}
			
			/* we have read less bytes than we expected */
			if (p_i_amountBytes > i_sum) {
														net.forestany.forestj.lib.Global.ilogFiner("We have read less bytes than we expected: '" + p_i_amountBytes + "' > '" + i_sum + "'");
				
				/* new return byte array with correct length */
				byte[] a_trimmedReceivedData = new byte[i_sum];
				
				/* trim byte array data */
				for (int i = 0; i < i_sum; i++) {
					a_trimmedReceivedData[i] = a_receivedData[i];
				}
				
														net.forestany.forestj.lib.Global.ilogFiner("Created new return byte array with correct length: '" + i_sum + "'");
				
				return a_trimmedReceivedData;
			}
		} else if (this.e_type == net.forestany.forestj.lib.net.sock.Type.UDP) {
			throw new UnsupportedOperationException("Not implemented for UDP protocol");
		} else {
			throw new UnsupportedOperationException("Not implemented");
		}
		
		return a_receivedData;
	}

	/**
	 * method to log all information about message with log-level FINEST for message details and log-level MASS for each byte of message data content
	 * only available if b_debugNetworkTrafficOn is set to true
	 * 
	 * @param p_a_messageBytes				byte array of message
	 * @param p_o_message					message object
	 */
	protected void debugMessage(byte[] p_a_messageBytes, net.forestany.forestj.lib.net.msg.Message p_o_message) {
		/* only if b_debugNetworkTrafficOn is set to true */
		if (this.b_debugNetworkTrafficOn) {
			/* log message details */
													net.forestany.forestj.lib.Global.ilogFinest("\tMessageBoxId: " + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {p_a_messageBytes[0], p_a_messageBytes[1]}, false) );
													net.forestany.forestj.lib.Global.ilogFinest("\tMessageAmount: " + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {p_a_messageBytes[2], p_a_messageBytes[3]}, false) );
													net.forestany.forestj.lib.Global.ilogFinest("\tMessageNumber: " + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {p_a_messageBytes[4], p_a_messageBytes[5]}, false) );
													net.forestany.forestj.lib.Global.ilogFinest("\tMessageTypeLength: " + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {p_a_messageBytes[6]}, false) );
			
			byte[] a_stringBytes = new byte[p_a_messageBytes[6]];
			
			/* log each byte of message data content */
			for (int j = 0; j < p_a_messageBytes[6]; j++) {
				a_stringBytes[j] = p_a_messageBytes[j + 7];
														if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("\t\tMessageType Character[" + j + " + 7]: " + new String(new byte[] {p_a_messageBytes[j + 7]}) + " [" + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {p_a_messageBytes[j + 7]}, false) + "]");
			}
			
													net.forestany.forestj.lib.Global.ilogFinest("\tMessageType: " + new String(a_stringBytes) );
													net.forestany.forestj.lib.Global.ilogFinest("\tMessageDataLength: " + p_o_message.getDataLength() );
		}
	}
	
	/**
	 * Method to use a tcp socket for bidirectional communication, using first message box for sending data and the second message box for receiving data
	 * 
	 * @throws Exception						any other Exception, see below in other classes for details
	 */
	protected void tcpBidirectional() throws Exception {
		/* check if token instance is available */
		if (this.o_token == null) {
			throw new NullPointerException("Token instance for bidirectional task is null");
		}
		
		/* keep socket task in endless loop for bidirectional communication, connection will be closed when communication object or socket task object is closed and token instance is canceled */
		while (!this.o_token.isCanceled()) {
			/* check first message box of socket task for messages to send */
			net.forestany.forestj.lib.net.msg.MessageBox o_messageBox = this.a_messageBoxes.get(0);
				
			/* get message of current message box */
			net.forestany.forestj.lib.net.msg.Message o_message = o_messageBox.currentMessage();
			
			/* if we got a message we want to send it */
			if (o_message != null) {
				/* send TCP packet: just dequeue one message of current message box */
				o_message = o_messageBox.dequeueMessage();
				
				if (o_message != null) {
					/* send message with TCP protocol */
					@SuppressWarnings("unused")
					boolean b_dataSend = this.tcpSend(o_message);
				} else {
					throw new NullPointerException("Could not dequeue message, result is null");
				}
			}
			
			try {
				/* receive TCP packet */
				this.tcpReceive();
			} catch (Exception o_exc) {
				/* skip timeout */
			}
		}
	}

	/**
	 * Send one message from a message box over TCP to other communication side
	 * 
	 * @param p_o_message						message object from a message box
	 * @return									true - data sent successfully, false - something went wrong
	 * @throws Exception						any other Exception, see below in other classes for details
	 * @see net.forestany.forestj.lib.Cryptography			net.forestany.forestj.lib.Cryptography - Encrypt_AES_GCM and Encrypt methods
	 * @see net.forestany.forestj.lib.net.msg.Message		net.forestany.forestj.lib.net.msg.Message - Constructor and Message methods
	 */
	protected boolean tcpSend(net.forestany.forestj.lib.net.msg.Message p_o_message) throws Exception {
		/* convert complete message parameter into byte array */
		byte[] a_bytes = p_o_message.getByteArrayFromMessage();
		
												net.forestany.forestj.lib.Global.ilogFine("Send message to[" + ((java.net.Socket)this.o_socket.getSocket()).getInetAddress().getHostAddress().toString() + ":" + ((java.net.Socket)this.o_socket.getSocket()).getPort() + "] with port[" + ((java.net.Socket)this.o_socket.getSocket()).getLocalPort() + "]: message number[" + p_o_message.getMessageNumber() + "] of [" + p_o_message.getMessageAmount() + "] with length[" + a_bytes.length + "], data length[" + p_o_message.getDataLength() + "], data block length[" + p_o_message.getDataBlockLength() + "], message box id[" + p_o_message.getMessageBoxId() + "]");
		
		/* debug sending message if b_debugNetworkTrafficOn is set to true */
		this.debugMessage(a_bytes, p_o_message);
		
		/* encrypt sending bytes if encryption is active */
		if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) {
			a_bytes = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(a_bytes, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
		} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) {
			a_bytes = net.forestany.forestj.lib.Cryptography.encrypt_AES_GCM(a_bytes, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
		} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
			a_bytes = this.o_cryptography.encrypt(a_bytes);
		}
		
		/* send bytes over TCP */
		this.sendBytes(a_bytes);
		
		return true;
	}
	
	/**
	 * Method for handling receiving bytes from a TCP connection
	 * 
	 * @throws Exception											any other Exception, see below in other classes for details
	 * @see java.lang.Thread										java.lang.Thread - sleep method
	 * @see net.forestany.forestj.lib.Cryptography					net.forestany.forestj.lib.Cryptography - Decrypt_AES_GCM and Decrypt methods
	 * @see net.forestany.forestj.lib.net.msg.Message				net.forestany.forestj.lib.net.msg.Message - Constructor and Message methods
	 * @see net.forestany.forestj.lib.net.msg.MessageBox			net.forestany.forestj.lib.net.msg.MessageBox - Accessing MessageBox queue
	 */
	protected void tcpReceive() throws Exception {
		/* receive message data bytes */
		byte[] a_receivedData = this.receiveBytes(this.i_bufferLength);
		
												net.forestany.forestj.lib.Global.ilogFiner("Received socket packet, length = " + a_receivedData.length + " bytes");
		
		/* if we received no data we have no message information as well */
		if (a_receivedData.length < 1) {
			return;
		}
		
		int i_decreaseForEncryption = 0;
		
		/* decrypt received bytes if encryption is active */
		if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_HIGH) {
			a_receivedData = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_receivedData, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY128BIT);
			i_decreaseForEncryption = 28;
		} else if (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_HIGH) {
			a_receivedData = net.forestany.forestj.lib.Cryptography.decrypt_AES_GCM(a_receivedData, this.s_commonSecretPassphrase, net.forestany.forestj.lib.Cryptography.KEY256BIT);
			i_decreaseForEncryption = 28;
		} else if ( (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_128_BIT_LOW) || (this.e_communicationSecurity == net.forestany.forestj.lib.net.sock.com.Security.SYMMETRIC_256_BIT_LOW) ) {
			a_receivedData = this.o_cryptography.decrypt(a_receivedData);
			i_decreaseForEncryption = 16;
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("received data (optional already decrypted), length = " + a_receivedData.length + " bytes");
		
		/* create message object with message length and message data */
		net.forestany.forestj.lib.net.msg.Message o_message = new net.forestany.forestj.lib.net.msg.Message(this.i_bufferLength - i_decreaseForEncryption);
		o_message.setMessageFromByteArray(a_receivedData);
		
												net.forestany.forestj.lib.Global.ilogFine("Received message from[" + ((java.net.Socket)this.o_socket.getSocket()).getRemoteSocketAddress().toString() + "]: message number[" + o_message.getMessageNumber() + "] of [" + o_message.getMessageAmount() + "] with length[" + o_message.getMessageLength() + "], data length[" + o_message.getDataLength() + "], data block length[" + o_message.getDataBlockLength() + "], message box id[" + o_message.getMessageBoxId() + "]");
		
		/* debug received message if b_debugNetworkTrafficOn is set to true */
		this.debugMessage(a_receivedData, o_message);
		
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
		
		/* use second message box for receiving data for bidirectional communication */
		if (this.e_communicationCardinality == net.forestany.forestj.lib.net.sock.com.Cardinality.EqualBidirectional) {
			i_messageBoxId = 1;
		}
		
		/* enqueue message object */
		while (!this.a_messageBoxes.get(i_messageBoxId).enqueueMessage(o_message)) {
													net.forestany.forestj.lib.Global.ilogWarning("Could not enqueue message object, timeout for '" + this.i_queueTimeoutMilliseconds + "' milliseconds");
			
			/* wait queue timeout length to enqueue message object again */
			Thread.sleep(this.i_queueTimeoutMilliseconds);
		};
	}
}
