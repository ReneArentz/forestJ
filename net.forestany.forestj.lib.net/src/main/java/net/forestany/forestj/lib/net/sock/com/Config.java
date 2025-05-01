package net.forestany.forestj.lib.net.sock.com;

/**
 * Configuration class for communication object for network communication exchange. All configurable settings are listed and adjustable in this class. Please look on the comments of the set-property-methods to see further details.
 * Special features in handling shared memory objects and bidirectional shared memory settings, so that all changes in fields of inherited class of shared memory object are transferred over the network to the other communication side automatically.
 */
public class Config {

	/* Fields */
	
	private Type e_communicationType;
	private net.forestany.forestj.lib.net.sock.recv.ReceiveType e_socketReceiveType;
	private Cardinality e_cardinality;
	private int i_executorServicePoolAmount;
	
	private Security e_security;
	private String s_commonSecretPassphrase;
	private java.util.List<javax.net.ssl.SSLContext> a_sslContextList;
	
	private int i_amountMessageBoxes;
	private int i_amountSockets;
	private java.util.List<Integer> a_messageBoxLengths;
	private java.util.List<String> a_hosts;
	private java.util.List<Integer> a_ports;
	
	private java.util.List<net.forestany.forestj.lib.net.sock.task.Task<?>> a_socketTasks;
	
	private int i_socketExecutorServicePoolAmount;
	private int i_receiverTimeoutMilliseconds;
	private int i_senderTimeoutMilliseconds;
	private boolean b_checkReachability;
	private int i_maxTerminations;
	private int i_senderIntervalMilliseconds;
	private int i_queueTimeoutMilliseconds;
	
	private int i_udpReceiveAckTimeoutMilliseconds;
	private int i_udpSendAckTimeoutMilliseconds;
	private int i_udpMulticastSenderTTL;
	private String s_udpMulticastReceiverNetworkInterfaceName;
	
	private String s_localAddress;
	private int i_localPort;
	
	private boolean b_objectTransmission;

	private java.util.List<net.forestany.forestj.lib.net.sock.task.Task<?>> a_receiveSocketTasks;
	
	private SharedMemory<?> o_sharedMemory;
	private int i_sharedMemoryTimeoutMilliseconds;
	private net.forestany.forestj.lib.DateInterval o_sharedMemoryIntervalCompleteRefresh;
	private java.util.List<String> a_biHosts;
	private java.util.List<Integer> a_biPorts;
	private Config o_sharedMemoryBidirectionalConfig;
	private boolean b_sharedMemoryBidirectionalConfigSet;
	
	private boolean b_closed;
	
	private boolean b_debugNetworkTrafficOn;
	
	private boolean b_useMarshalling;
	private boolean b_useMarshallingWholeObject;
	private int i_marshallingDataLengthInBytes;
	private boolean b_marshallingUsePropertyMethods;
	private String s_marshallingOverrideMessageType;
	private boolean b_marshallingSystemUsesLittleEndian;
		
	private java.util.List<net.forestany.forestj.lib.net.sock.task.Task.IDelegate> itf_delegates;
	
	/* Properties */
	
	/**
	 * get communication type
	 * 
	 * @return Type
	 */
	public Type getCommunicationType() {
		return this.e_communicationType;
	}
	
	/**
	 * get socket receive type
	 * 
	 * @return net.forestany.forestj.lib.net.sock.recv.ReceiveType
	 */
	public net.forestany.forestj.lib.net.sock.recv.ReceiveType getSocketReceiveType() {
		return this.e_socketReceiveType;
	}
	
	/**
	 * set socket receive type
	 * 
	 * @param p_e_value						determine receive type for all sockets: SOCKET or SERVER
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @see net.forestany.forestj.lib.net.sock.recv.ReceiveType
	 */
	public void setSocketReceiveType(net.forestany.forestj.lib.net.sock.recv.ReceiveType p_e_value) throws IllegalStateException {
		this.checkClosed();
		this.e_socketReceiveType = p_e_value;
	}
	
	/**
	 * get cardinality
	 * 
	 * @return Cardinality
	 */
	public Cardinality getCardinality() {
		return this.e_cardinality;
	}
	
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
	 * @param p_i_value						integer value for fixed amount of threads for thread pool instance of communication object
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws IllegalArgumentException		negative integer value as parameter
	 */
	public void setExecutorServicePoolAmount(int p_i_value) throws IllegalStateException {
		this.checkClosed();
		
		if (p_i_value < 0) {
			throw new IllegalArgumentException("Socket executor service pool amount must be at least '0', but was set to '" + p_i_value + "'");
		}
		
		this.i_executorServicePoolAmount = p_i_value;
	}
	
	/**
	 * get communication security
	 * 
	 * @return Security
	 */
	public Security getCommunicationSecurity() {
		return this.e_security;
	}
	
	/**
	 * set communication security
	 * 
	 * @param p_e_value						determine communication security for socket
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws IllegalArgumentException		communication security is not supported for UDP communication
	 * @see net.forestany.forestj.lib.net.sock.com.Security
	 */
	public void setCommunicationSecurity(Security p_e_value) throws IllegalStateException, IllegalArgumentException {
		this.checkClosed();
		
		if ( (p_e_value == Security.ASYMMETRIC) && (
			this.e_communicationType == Type.UDP_SEND ||  
			this.e_communicationType == Type.UDP_RECEIVE || 
			this.e_communicationType == Type.UDP_SEND_WITH_ACK || 
			this.e_communicationType == Type.UDP_RECEIVE_WITH_ACK ||
			this.e_communicationType == Type.UDP_MULTICAST_SENDER || 
			this.e_communicationType == Type.UDP_MULTICAST_RECEIVER
		) ) {
			throw new IllegalArgumentException("Cannot use communication security[" + Security.ASYMMETRIC + "] with communication type[" + this.e_communicationType + "]");
		}
		
		this.e_security = p_e_value;
	}
	
	/**
	 * get common secret passphrase
	 * 
	 * @return String
	 */
	public String getCommonSecretPassphrase() {
		return this.s_commonSecretPassphrase;
	}
	
	/**
	 * set common secret passphrase
	 * 
	 * @param p_s_value						common secret passphrase for low security communication
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws IllegalArgumentException		secret passphrase is null or not long enough (at least 36 characters)
	 */
	public void setCommonSecretPassphrase(String p_s_value) throws IllegalStateException, IllegalArgumentException {
		this.checkClosed();
		
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			throw new IllegalArgumentException("Common secret passphrase is null");
		}
		
		if (p_s_value.length() < 36) {
			throw new IllegalArgumentException("Common secret passphrase must have at least '36' characters, but has '" + p_s_value.length() + "' characters");
		}
		
		this.s_commonSecretPassphrase = p_s_value;
	}
	
	/**
	 * get ssl context list
	 * 
	 * @return list of own instantiated ssl context objects for every socket
	 */
	public java.util.List<javax.net.ssl.SSLContext> getSSLContextList() {
		return this.a_sslContextList;
	}
	
	/**
	 * set ssl context list
	 * 
	 * @param p_a_value						list of own instantiated ssl context objects for every socket
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 */
	public void setSSLContextList(java.util.List<javax.net.ssl.SSLContext> p_a_value) throws IllegalStateException {
		this.checkClosed();
		this.a_sslContextList = p_a_value;
	}
	
	/**
	 * add sll context to list
	 * 
	 * @param p_o_value						instantiated ssl context object which will be added to list of ssl context objects for every socket
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws NullPointerException			ssl context parameter is null
	 */
	public void addSSLContextToList(javax.net.ssl.SSLContext p_o_value) throws IllegalStateException, NullPointerException {
		this.checkClosed();
		
		if (p_o_value == null) {
			throw new NullPointerException("SSL context parameter is null");
		}
		
		/* create new list of ssl context objects if it is null */
		if (this.a_sslContextList == null) {
			this.a_sslContextList = new java.util.ArrayList<javax.net.ssl.SSLContext>();
		}
		
		this.a_sslContextList.add(p_o_value);
	}
	
	/**
	 * get amount
	 * 
	 * @return int
	 */
	public int getAmount() {
		if (this.i_amountMessageBoxes != this.i_amountSockets) {
			return 0;
		} else {
			return this.i_amountMessageBoxes;
		}
	}
	
	/**
	 * set amount
	 * 
	 * @param p_i_value						determine amount of sockets and message boxes for communication
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished or shared memory object has been set
	 * @throws IllegalArgumentException		amount parameter must be greater than '0'
	 */
	public void setAmount(int p_i_value) throws IllegalStateException, IllegalArgumentException {
		this.checkClosed();
		this.checkSharedMemoryset("amount");
		
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Amount must be at least '1', but was set to '" + p_i_value + "'");
		}
		
		this.i_amountMessageBoxes = p_i_value;
		this.i_amountSockets = p_i_value;
	}
	
	/**
	 * get amount message boxes
	 * 
	 * @return int
	 */
	public int getAmountMessageBoxes() {
		return this.i_amountMessageBoxes;
	}
	
	/**
	 * set amount message boxes
	 * 
	 * @param p_i_value						determine amount of message boxes for communication
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished or shared memory object has been set
	 * @throws IllegalArgumentException		amount parameter must be greater than '0'
	 */
	public void setAmountMessageBoxes(int p_i_value) throws IllegalStateException, IllegalArgumentException {
		this.checkClosed();
		this.checkSharedMemoryset("amount message boxes");
				
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Amount of message boxes must be at least '1', but was set to '" + p_i_value + "'");
		}
		
		this.i_amountMessageBoxes = p_i_value;
	}
	
	/**
	 * get amount sockets
	 * 
	 * @return int
	 */
	public int getAmountSockets() {
		return this.i_amountSockets;
	}
	
	/**
	 * set amount sockets
	 * 
	 * @param p_i_value						determine amount of sockets for communication
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished or shared memory object has been set
	 * @throws IllegalArgumentException		amount parameter must be greater than '0'
	 */
	public void setAmountSockets(int p_i_value) throws IllegalStateException, IllegalArgumentException {
		this.checkClosed();
		this.checkSharedMemoryset("amount sockets");
		
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Amount of sockets must be at least '1', but was set to '" + p_i_value + "'");
		}
		
		this.i_amountSockets = p_i_value;
	}
	
	/**
	 * get message box lengths
	 * 
	 * @return list of lengths for all message boxes [1500 or 8192]
	 */
	public java.util.List<Integer> getMessageBoxLengths() {
		return this.a_messageBoxLengths;
	}
	
	/**
	 * set message box lengths
	 * 
	 * @param p_a_value						list of lengths for all message boxes [1500 or 8192]
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws NullPointerException			parameter is null
	 * @throws IllegalArgumentException		message box length must be '1500' or '8192'
	 */
	public void setMessageBoxLengths(java.util.List<Integer> p_a_value) throws IllegalStateException, NullPointerException, IllegalArgumentException {
		this.checkClosed();
		
		if (p_a_value == null) {
			throw new NullPointerException("List of message box lengths is null");
		}
		
		/* check each message box length in parameter list */
		for (Integer i : p_a_value) {
			if ( (i != 1500) && (i != 8192) ) {
				throw new IllegalArgumentException("Invalid message box length[" + i + "] in parameter list. Valid values are [1500, 8192]");
			}
		}
		
		this.a_messageBoxLengths = p_a_value;
	}
	
	/**
	 * add message box length to list
	 * 
	 * @param p_i_value						new value for list of message box lengths [1500 or 8192]
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws IllegalArgumentException		message box length must be '1500' or '8192'
	 */
	public void addMessageBoxLength(Integer p_i_value) throws IllegalStateException, IllegalArgumentException {
		this.checkClosed();
		
		if ( (p_i_value != 1500) && (p_i_value != 8192) ) {
			throw new IllegalArgumentException("Invalid message box length[" + p_i_value + "] parameter. Valid values are [1500, 8192]");
		}
		
		/* create new list of message box lengths if it is null */
		if (this.a_messageBoxLengths == null) {
			this.a_messageBoxLengths = new java.util.ArrayList<Integer>();
		}
		
		this.a_messageBoxLengths.add(p_i_value);
	}
	
	/**
	 * get hosts
	 * 
	 * @return list of String
	 */
	public java.util.List<String> getHosts() {
		return this.a_hosts;
	}
	
	/**
	 * get ports
	 * 
	 * @return list of Integer
	 */
	public java.util.List<Integer> getPorts() {
		return this.a_ports;
	}
	
	/**
	 * set hosts and ports
	 * 
	 * @param p_a_value						list of pairs, hosts and ports parameter list for every socket, auto check for duplicates
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws NullPointerException			parameter value is null or a host value is null
	 * @throws IllegalArgumentException		invalid port value, must be between 1..65535
	 * @throws UnsupportedOperationException	duplicate found in parameter list
	 */
	public void setHostsAndPorts(java.util.List<java.util.AbstractMap.SimpleEntry<String, Integer>> p_a_value) throws IllegalStateException, NullPointerException, IllegalArgumentException, UnsupportedOperationException {
		this.checkClosed();
		
		/* check parameter value */
		if (p_a_value == null) {
			throw new NullPointerException("List parameter of hosts and ports for every socket is null");
		}
		
		this.a_hosts = new java.util.ArrayList<String>();
		this.a_ports = new java.util.ArrayList<Integer>();
		
		java.util.List<String> a_checkDuplicates = new java.util.ArrayList<String>();
		int i = 0;
		
		/* iterate each pair of host and port in parameter list */
		for (java.util.AbstractMap.SimpleEntry<String, Integer> o_pair : p_a_value) {
			i++;
			
			/* check if host value is not empty */
			if (net.forestany.forestj.lib.Helper.isStringEmpty(o_pair.getKey())) {
				throw new NullPointerException("Host #" + i + " is null");
			}
			
			/* check port min. value */
			if (o_pair.getValue() < 1) {
				throw new IllegalArgumentException("Port #" + i + " must be at least '1', but was set to '" + o_pair.getValue() + "'");
			}
			
			/* check port max. value */
			if (o_pair.getValue() > 65535) {
				throw new IllegalArgumentException("Port #" + i + " must be lower equal '65535', but was set to '" + o_pair.getValue() + "'");
			} 
			
			String s_duplicate = o_pair.getKey() + ":" + o_pair.getValue();
			
			/* check for existing duplicate */
			if (a_checkDuplicates.contains(s_duplicate)) {
				this.a_hosts = new java.util.ArrayList<String>();
				this.a_ports = new java.util.ArrayList<Integer>();
				throw new UnsupportedOperationException("Duplicate found[" + s_duplicate + "] in list of target hosts and ports which is not allowed");
			}
			
			/* add value to temp list for checking duplicates */
			a_checkDuplicates.add(s_duplicate);
			
			this.a_hosts.add(o_pair.getKey());
			this.a_ports.add(o_pair.getValue());
		}
	}
	
	/**
	 * add host and port pair
	 * 
	 * @param p_p_value						new pair value of host and port parameter for configuration list for every socket, auto check for duplicates
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws NullPointerException			parameter value is null or a host value is null
	 * @throws IllegalArgumentException		invalid port value, must be between 1..65535
	 * @throws UnsupportedOperationException	duplicate found in parameter list
	 */
	public void addHostAndPort(java.util.AbstractMap.SimpleEntry<String, Integer> p_p_value) throws IllegalStateException, NullPointerException, IllegalArgumentException, UnsupportedOperationException {
		this.checkClosed();
		
		/* check parameter value */
		if (p_p_value == null) {
			throw new NullPointerException("Host and Port pair parameter is null");
		}
		
		/* check if host value is not empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_p_value.getKey())) {
			throw new NullPointerException("Host is null");
		}
		
		/* check port min. value */
		if (p_p_value.getValue() < 1) {
			throw new IllegalArgumentException("Port must be at least '1', but was set to '" + p_p_value.getValue() + "'");
		}
		
		/* check port max. value */
		if (p_p_value.getValue() > 65535) {
			throw new IllegalArgumentException("Port must be lower equal '65535', but was set to '" + p_p_value.getValue() + "'");
		}
		
		/* create new hosts list instance if it is null */
		if (this.a_hosts == null) {
			this.a_hosts = new java.util.ArrayList<String>();
		}
		
		/* create new ports list instance if it is null */
		if (this.a_ports == null) {
			this.a_ports = new java.util.ArrayList<Integer>();
		}
		
		java.util.List<String> a_checkDuplicates = new java.util.ArrayList<String>();
		
		/* gather all existing hosts and ports in config settings */
		for (int i = 0; i < this.a_hosts.size(); i++) {
			String s_duplicate = this.a_hosts.get(i) + ":" + this.a_ports.get(i);
			a_checkDuplicates.add(s_duplicate);
		}
		
		String s_duplicate = p_p_value.getKey() + ":" + p_p_value.getValue();
		
		/* check for existing duplicate */
		if (a_checkDuplicates.contains(s_duplicate)) {
			throw new UnsupportedOperationException("New entry would be a duplicate[" + s_duplicate + "] in list of target hosts and ports which is not allowed");
		}
		
		this.a_hosts.add(p_p_value.getKey());
		this.a_ports.add(p_p_value.getValue());
	}
	
	/**
	 * get socket tasks
	 * 
	 * @return list of socket tasks for every socket instance in communication
	 */
	public java.util.List<net.forestany.forestj.lib.net.sock.task.Task<?>> getSocketTasks() {
		return this.a_socketTasks;
	}
	
	/**
	 * set socket tasks
	 * 
	 * @param p_a_value						list of socket tasks for every socket instance in communication
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws NullPointerException			parameter value is null
	 */
	public void setSocketTasks(java.util.List<net.forestany.forestj.lib.net.sock.task.Task<?>> p_a_value) throws IllegalStateException, NullPointerException {
		this.checkClosed();
		
		/* check parameter value */
		if (p_a_value == null) {
			throw new NullPointerException("List of socket tasks parameter is null");
		}
		
		int i = 1;
		
		/* check if any socket task in parameter list is null */
		for (net.forestany.forestj.lib.net.sock.task.Task<?> o_foo : p_a_value) {
			if (o_foo == null) {
				throw new NullPointerException("Socket task #" + i++ + " in parameter list is null");
			}
		}
		
		this.a_socketTasks = p_a_value;
	}
	
	/**
	 * add socket task
	 * 
	 * @param p_o_value						socket task for a socket instance in communication which will be added to config list of socket tasks
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws NullPointerException			parameter value is null
	 */
	public void addSocketTask(net.forestany.forestj.lib.net.sock.task.Task<?> p_o_value) throws IllegalStateException, NullPointerException {
		this.checkClosed();
		
		/* check parameter value */
		if (p_o_value == null) {
			throw new NullPointerException("Socket task parameter is null");
		}
		
		/* create new socket task list instance if it is null */
		if (this.a_socketTasks == null) {
			this.a_socketTasks = new java.util.ArrayList<net.forestany.forestj.lib.net.sock.task.Task<?>>();
		}
		
		this.a_socketTasks.add(p_o_value);
	}
	
	/**
	 * get socket executor service pool amount
	 * 
	 * @return int
	 */
	public int getSocketExecutorServicePoolAmount() {
		return this.i_socketExecutorServicePoolAmount;
	}
	
	/**
	 * set socket executor service pool amount
	 * 
	 * @param p_i_value						integer value for fixed amount of threads for thread pool instance of socket object
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws IllegalArgumentException		negative integer value as parameter
	 */
	public void setSocketExecutorServicePoolAmount(int p_i_value) throws IllegalStateException, IllegalArgumentException {
		this.checkClosed();
		
		if (p_i_value < 0) {
			throw new IllegalArgumentException("Socket executor service pool amount must be at least '0', but was set to '" + p_i_value + "'");
		}
		
		this.i_socketExecutorServicePoolAmount = p_i_value;
	}
	
	/**
	 * get receiver timeout milliseconds
	 * 
	 * @return int
	 */
	public int getReceiverTimeoutMilliseconds() {
		return this.i_receiverTimeoutMilliseconds;
	}
	
	/**
	 * set receiver timeout milliseconds
	 * 
	 * @param p_i_value						integer value for receiver timeout in milliseconds of socket object - how long will a receive socket block execution
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws IllegalArgumentException		invalid parameter value
	 */
	public void setReceiverTimeoutMilliseconds(int p_i_value) throws IllegalStateException, IllegalArgumentException {
		this.checkClosed();
		
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Receiver timeout must be at least '1' millisecond, but was set to '" + p_i_value + "' millisecond(s)");
		}
		
		this.i_receiverTimeoutMilliseconds = p_i_value;
	}
	
	/**
	 * get sender timeout milliseconds
	 * 
	 * @return int
	 */
	public int getSenderTimeoutMilliseconds() {
		return this.i_senderTimeoutMilliseconds;
	}
	
	/**
	 * set sender timeout milliseconds
	 * 
	 * @param p_i_value						integer value for sender timeout in milliseconds of socket object - how long will a sender socket will wait until connection has been established
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws IllegalArgumentException		invalid parameter value
	 */
	public void setSenderTimeoutMilliseconds(int p_i_value) throws IllegalStateException, IllegalArgumentException {
		this.checkClosed();
		
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Sender timeout must be at least '1' millisecond, but was set to '" + p_i_value + "' millisecond(s)");
		}
		
		this.i_senderTimeoutMilliseconds = p_i_value;
	}
	
	/**
	 * get check reachability flag
	 * 
	 * @return boolean
	 */
	public boolean getCheckReachability() {
		return this.b_checkReachability;
	}
	
	/**
	 * only for sending socket instances
	 * 
	 * @param p_b_value						true - check if destination is reachable, false - do not check reachability
	 */
	public void setCheckReachability(boolean p_b_value) {
		this.b_checkReachability = p_b_value;
	}
	
	/**
	 * get max terminations
	 * 
	 * @return int
	 */
	public int getMaxTerminations() {
		return this.i_maxTerminations;
	}
	
	/**
	 * set max terminations
	 * 
	 * @param p_i_value						set a max. value for thread executions of socket instance
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 */
	public void setMaxTerminations(int p_i_value) throws IllegalStateException {
		this.checkClosed();
		
		if (p_i_value < 0) {
			p_i_value = -1;
		}
		
		this.i_maxTerminations = p_i_value;
	}
	
	/**
	 * get sender interval milliseconds
	 * 
	 * @return int
	 */
	public int getSenderIntervalMilliseconds() {
		return this.i_senderIntervalMilliseconds;
	}
	
	/**
	 * set sender interval milliseconds
	 * 
	 * @param p_i_value						interval for waiting for other communication side or new data to send
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 */
	public void setSenderIntervalMilliseconds(int p_i_value) throws IllegalStateException {
		this.checkClosed();
		
		if (p_i_value < 1) {
			p_i_value = 0;
		}
		
		this.i_senderIntervalMilliseconds = p_i_value;
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
	 * @param p_i_value						determine timeout in milliseconds for sending/receiving bytes in socket task instances
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws IllegalArgumentException		invalid parameter value
	 */
	public void setQueueTimeoutMilliseconds(int p_i_value) throws IllegalStateException, IllegalArgumentException {
		this.checkClosed();
		
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Queue timeout must be at least '1' millisecond, but was set to '" + p_i_value + "' millisecond(s)");
		}
		
		this.i_queueTimeoutMilliseconds = p_i_value;
	}
	
	/**
	 * get udp receive ack timeout milliseconds
	 * 
	 * @return int
	 */
	public int getUDPReceiveAckTimeoutMilliseconds() {
		return this.i_udpReceiveAckTimeoutMilliseconds;
	}
	
	/**
	 * set udp receive ack timeout milliseconds
	 * 
	 * @param p_i_value						determine timeout in milliseconds for sending socket task to receive an acknowledge message via UDP
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws IllegalArgumentException		invalid parameter value
	 */
	public void setUDPReceiveAckTimeoutMilliseconds(int p_i_value) throws IllegalStateException, IllegalArgumentException {
		this.checkClosed();
		
		if (p_i_value < 1) {
			throw new IllegalArgumentException("UDP receive ACK timeout must be at least '1' millisecond, but was set to '" + p_i_value + "' millisecond(s)");
		}
		
		this.i_udpReceiveAckTimeoutMilliseconds = p_i_value;
	}
	
	/**
	 * get udp send ack timeout milliseconds
	 * 
	 * @return int
	 */
	public int getUDPSendAckTimeoutMilliseconds() {
		return this.i_udpSendAckTimeoutMilliseconds;
	}
	
	/**
	 * set udp send ack timeout milliseconds
	 * 
	 * @param p_i_value						determine timeout in milliseconds for receiving socket task before sending an acknowledge message via UDP - wait some time so the other side of communication can prepare to receive UDP acknowledge message
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws IllegalArgumentException		invalid parameter value
	 */
	public void setUDPSendAckTimeoutMilliseconds(int p_i_value) throws IllegalStateException, IllegalArgumentException {
		this.checkClosed();
		
		if (p_i_value < 1) {
			throw new IllegalArgumentException("UDP send ACK timeout must be at least '1' millisecond, but was set to '" + p_i_value + "' millisecond(s)");
		}
		
		this.i_udpSendAckTimeoutMilliseconds = p_i_value;
	}
	
	/**
	 * get udp multicast sender ttl
	 * 
	 * @return int
	 */
	public int getUDPMulticastSenderTTL() {
		return this.i_udpMulticastSenderTTL;
	}
	
	/**
	 * set udp multicast sender ttl
	 * 
	 * @param p_i_value						by specifying the TTL, we can therefore limit the lifetime of the packet and thus the distance it can travel. Default is '1'.
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws IllegalArgumentException		invalid parameter value
	 */
	public void setUDPMulticastSenderTTL(int p_i_value) throws IllegalStateException, IllegalArgumentException {
		this.checkClosed();
		
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Multicast TTL must be at least '1', but was set to '" + p_i_value + "'");
		}
		
		this.i_udpMulticastSenderTTL = p_i_value;
	}
	
	/**
	 * get udp multicast receiver network interface name
	 * 
	 * @return String
	 */
	public String getUDPMulticastReceiverNetworkInterfaceName() {
		return this.s_udpMulticastReceiverNetworkInterfaceName;
	}
	
	/**
	 * set udp multicast receiver network interface name
	 * 
	 * @param p_s_value						set multicast network interface on which we want to receive multicast udp data
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws IllegalArgumentException		could not find network interface by that name
	 */
	public void setUDPMulticastReceiverNetworkInterfaceName(String p_s_value) throws IllegalStateException {
		this.checkClosed();
		
		try {
			java.net.NetworkInterface.getByName(p_s_value);
		} catch (Exception o_exc) {
			throw new IllegalArgumentException("could not find network interface by that name; " + o_exc.getMessage());
		}
		
		this.s_udpMulticastReceiverNetworkInterfaceName = p_s_value;
	}
	
	/**
	 * get local address
	 * 
	 * @return String
	 */
	public String getLocalAddress() {
		return this.s_localAddress;
	}
	
	/**
	 * set local address
	 * 
	 * @param p_s_value						set local address for sending socket instances
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 */
	public void setLocalAddress(String p_s_value) throws IllegalStateException {
		this.checkClosed();
		this.s_localAddress = p_s_value;
	}
	
	/**
	 * get local port
	 * 
	 * @return int
	 */
	public int getLocalPort() {
		return this.i_localPort;
	}
	
	/**
	 * set local port
	 * 
	 * @param p_i_value						set local port for sending socket instances, must be between [1..65535]
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws IllegalArgumentException		invalid port value, must be between 1..65535
	 */
	public void setLocalPort(int p_i_value) throws IllegalStateException, IllegalArgumentException {
		this.checkClosed();
		
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Local port must be at least '1', but was set to '" + p_i_value + "'");
		}
		
		if (p_i_value > 65535) {
			throw new IllegalArgumentException("Local port must be lower equal '65535', but was set to '" + p_i_value + "'");
		}
		
		this.i_localPort = p_i_value;
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
	 * @param p_b_value						true - send/receive TCP packets, but a whole object, so several messages of a message box until the object has been transferred, false - send/receive TCP packets
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 */
	public void setObjectTransmission(boolean p_b_value) throws IllegalStateException {
		this.checkClosed();
		this.b_objectTransmission = p_b_value;
	}
	
	/**
	 * get debug network traffic on flag
	 * 
	 * @return boolean
	 */
	public boolean getDebugNetworkTrafficOn() {
		return this.b_debugNetworkTrafficOn;
	}
	
	/**
	 * set debug network traffic on flag
	 * 
	 * @param p_b_value						true - show detailed network traffic in internal log
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
	 * @param p_b_value						true - use marshalling methods to transport data over network
	 */
	public void setUseMarshalling(boolean p_b_value) {
		this.b_useMarshalling = p_b_value;
	}
	
	/**
	 * get use marshalling whole object flag
	 * 
	 * @return boolean
	 */
	public boolean getUseMarshallingWholeObject() {
		return this.b_useMarshallingWholeObject;
	}
	
	/**
	 * set use marshalling whole object flag
	 * 
	 * @param p_b_value						true - use marshalling methods for whole parameter object to transport data over network, especially with shared memory all fields will be transported within a cycle
	 */
	public void setUseMarshallingWholeObject(boolean p_b_value) {
		this.b_useMarshallingWholeObject = p_b_value;
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
	 * @param p_i_value						set data length in bytes for marshalling, must be between [1..4]
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws IllegalArgumentException		invalid value, must be between 1..4
	 */
	public void setMarshallingDataLengthInBytes(int p_i_value) throws IllegalStateException, IllegalArgumentException {
		this.checkClosed();
		
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
	 * @param p_b_value						true - access object parameter fields via property methods e.g. T getXYZ()
	 */
	public void setMarshallingUsePropertyMethods(boolean p_b_value) {
		this.b_marshallingUsePropertyMethods = p_b_value;
	}
	
	/**
	 * get marshalling override message type
	 * 
	 * @return String
	 */
	public String getMarshallingOverrideMessageType() {
		return this.s_marshallingOverrideMessageType;
	}
	
	/**
	 * set marshalling override message type
	 * 
	 * @param p_s_value						override message type with this string and do not get it automatically from object, thus the type can be set generally from other systems with other programming languages
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 */
	public void setMarshallingOverrideMessageType(String p_s_value) throws IllegalStateException {
		this.checkClosed();
		this.s_marshallingOverrideMessageType = p_s_value;
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
	 * @param p_b_value						(NOT IMPLEMENTED) true - current execution system uses little endian, false - current execution system uses big endian
	 */
	public void setMarshallingSystemUsesLittleEndian(boolean p_b_value) {
		this.b_marshallingSystemUsesLittleEndian = p_b_value;
	}
	
	/**
	 * get delegates
	 * 
	 * @return list of interface delegates to post progress of sending/receiving bytes for every socket task
	 */
	public java.util.List<net.forestany.forestj.lib.net.sock.task.Task.IDelegate> getDelegates() {
		return this.itf_delegates;
	}
	
	/**
	 * set delegates
	 * 
	 * @param p_a_value						list of interface delegates to post progress of sending/receiving bytes for every socket task
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 */
	public void setDelegates(java.util.List<net.forestany.forestj.lib.net.sock.task.Task.IDelegate> p_a_value) throws IllegalStateException {
		this.checkClosed();
		this.itf_delegates = p_a_value;
	}
	
	/**
	 * add delegate
	 * 
	 * @param p_itf_delegate				interface delegate to post progress of sending/receiving bytes for socket task list 
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws NullPointerException			parameter is null
	 */
	public void addDelegate(net.forestany.forestj.lib.net.sock.task.Task.IDelegate p_itf_delegate) throws IllegalStateException, NullPointerException {
		this.checkClosed();
		
		if (p_itf_delegate == null) {
			throw new NullPointerException("Delegate parameter is null");
		}
		
		/* create new interface delegate list instance if it is null */
		if (this.itf_delegates == null) {
			this.itf_delegates = new java.util.ArrayList<net.forestany.forestj.lib.net.sock.task.Task.IDelegate>();
		}
		
		this.itf_delegates.add(p_itf_delegate);
	}
	
	/**
	 * get receive socket tasks
	 * 
	 * @return list of receiving socket tasks to receive bytes as answer directly after sent a request to the other communication side
	 */
	public java.util.List<net.forestany.forestj.lib.net.sock.task.Task<?>> getReceiveSocketTasks() {
		return this.a_receiveSocketTasks;
	}
	
	/**
	 * set receive socket tasks
	 * 
	 * @param p_a_value						list of receiving socket tasks to receive bytes as answer directly after sent a request to the other communication side
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished OR invalid communication type for receive socket tasks, must be TCP_RECEIVE_WITH_ANSWER
	 */
	public void setReceiveSocketTasks(java.util.List<net.forestany.forestj.lib.net.sock.task.Task<?>> p_a_value) throws IllegalStateException {
		this.checkClosed();
		
		if (this.e_communicationType != Type.TCP_RECEIVE_WITH_ANSWER) {
			throw new IllegalStateException("Can only use communication type[" + Type.TCP_RECEIVE_WITH_ANSWER  + "] with receive socket tasks");
		}
		
		this.a_receiveSocketTasks = p_a_value;
	}
	
	/**
	 * add receive socket task
	 * 
	 * @param p_o_value						receiving socket task to receive bytes as answer directly after sent a request to the other communication side for socket task list
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished OR invalid communication type for receive socket tasks, must be TCP_RECEIVE_WITH_ANSWER
	 * @throws NullPointerException			parameter is null
	 */
	public void addReceiveSocketTask(net.forestany.forestj.lib.net.sock.task.Task<?> p_o_value) throws IllegalStateException, NullPointerException {
		this.checkClosed();
		
		if (p_o_value == null) {
			throw new NullPointerException("Receive socket task parameter is null");
		}
		
		/* create new receive socket task list instance if it is null */
		if (this.a_receiveSocketTasks == null) {
			this.a_receiveSocketTasks = new java.util.ArrayList<net.forestany.forestj.lib.net.sock.task.Task<?>>();
		}
		
		this.a_receiveSocketTasks.add(p_o_value);
	}
	
	/**
	 * get shared memory
	 * 
	 * @return shared memory object for communication network exchange. Each field if inherited class of shared memory instance will have it's own message box for sending/receiving
	 */
	public SharedMemory<?> getSharedMemory() {
		return this.o_sharedMemory;
	}
	
	/**
	 * set shared memory
	 * 
	 * @param p_o_value						set shared memory object for communication network exchange. Each field if inherited class of shared memory instance will have it's own message box for sending/receiving
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished OR invalid communication type for shared memory, must not be TCP_RECEIVE_WITH_ANSWER | TCP_SEND_WITH_ANSWER OR invalid cardinality = OneMessageBoxToManySockets
	 * @throws NullPointerException			parameter is null
	 * @throws IllegalArgumentException		invalid message box length configured
	 */
	public void setSharedMemory(SharedMemory<?> p_o_value) throws IllegalStateException, NullPointerException, IllegalArgumentException {
		this.checkClosed();
		
		if (p_o_value == null) {
			throw new NullPointerException("Shared memory parameter is null");
		}
		
		/* check if message box length has been configured */
		if ( (this.a_messageBoxLengths == null) || (this.a_messageBoxLengths.size() == 0) ) {
			throw new IllegalArgumentException("You must specify at least one message box length, but it has '0' entries or is null");
		}
		
		/* check for communication type */
		if ( (this.e_communicationType == Type.TCP_RECEIVE_WITH_ANSWER) || (this.e_communicationType == Type.TCP_SEND_WITH_ANSWER) ) {
			throw new IllegalStateException("Cannot use communication type[" + Type.TCP_RECEIVE_WITH_ANSWER + "|" + Type.TCP_SEND_WITH_ANSWER + "] with shared memory");
		}
		
		/* check communication cardinality */
		if (this.e_cardinality == Cardinality.OneMessageBoxToManySockets) {
			throw new IllegalStateException("Cannot use cardinality[" + this.e_cardinality + "] with shared memory, we must have one message box for each field in inherited class of shared memory object");
		}
		
		this.o_sharedMemory = p_o_value;
		
		if (!this.b_useMarshallingWholeObject) {
			/* each shared memory field gets own message box and own socket */
			this.i_amountMessageBoxes = this.o_sharedMemory.getAmountFields();
			this.i_amountSockets = this.o_sharedMemory.getAmountFields();
		} else {
			/* shared memory will be transferred as whole object, so we only need one message box and one socket */
			this.i_amountMessageBoxes = 1;
			this.i_amountSockets = 1;
			
			/* update cardinality to equal */
			this.e_cardinality = Cardinality.Equal;
		}
		
		/* correct the amount of sockets value to 1 */
		if (this.e_cardinality == Cardinality.ManyMessageBoxesToOneSocket) {
			this.i_amountSockets = 1;
		}
		
		/* we must multiply configured message box length value for each field in inherited class of shared memory object, if we do not use whole object transfer */
		if ( (!this.b_useMarshallingWholeObject) && (this.a_messageBoxLengths.size() != this.o_sharedMemory.getAmountFields()) ) {
			Integer i_temp = this.a_messageBoxLengths.get(0);
			
			this.a_messageBoxLengths = new java.util.ArrayList<Integer>();
			
			for (int i = 0; i < this.o_sharedMemory.getAmountFields(); i++) {
				this.a_messageBoxLengths.add(i_temp);
			}
		}
		
		/* clear any configured interface delegates */
		this.itf_delegates = null;
	}
	
	/**
	 * get shared memory timeout milliseconds
	 * 
	 * @return int
	 */
	public int getSharedMemoryTimeoutMilliseconds() {
		return this.i_sharedMemoryTimeoutMilliseconds;
	}
	
	/**
	 * set shared memory timeout milliseconds
	 * 
	 * @param p_i_value						set timeout value for thread instance - how long the shared memory thread should wait after incoming/outgoing data cycle
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws IllegalArgumentException		invalid parameter value
	 */
	public void setSharedMemoryTimeoutMilliseconds(int p_i_value) throws IllegalStateException, IllegalArgumentException {
		this.checkClosed();
		
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Shared memory timeout must be at least '1' millisecond, but was set to '" + p_i_value + "' millisecond(s)");
		}
		
		this.i_sharedMemoryTimeoutMilliseconds = p_i_value;
		
		if (this.o_sharedMemoryBidirectionalConfig != null) {
			this.o_sharedMemoryBidirectionalConfig.i_sharedMemoryTimeoutMilliseconds = p_i_value;
		}
	}
	
	/**
	 * get shared memory interval complete refresh
	 * 
	 * @return net.forestany.forestj.lib.DateInterval
	 */
	public net.forestany.forestj.lib.DateInterval getSharedMemoryIntervalCompleteRefresh() {
		return this.o_sharedMemoryIntervalCompleteRefresh;
	}
	
	/**
	 * set shared memory interval complete refresh
	 * 
	 * @param p_o_value						set interval object for sender cycle. At the end of the interval all field values will be send to other communication side
	 * @throws IllegalStateException		config has been close, because bidirectional setting was finished
	 * @throws NullPointerException			parameter is null
	 */
	public void setSharedMemoryIntervalCompleteRefresh(net.forestany.forestj.lib.DateInterval p_o_value) throws IllegalStateException, NullPointerException {
		this.checkClosed();
		
		if (p_o_value == null) {
			throw new NullPointerException("Shared memory interval for complete refresh parameter is null");
		}
		
		this.o_sharedMemoryIntervalCompleteRefresh = p_o_value;
		
		if (this.o_sharedMemoryBidirectionalConfig != null) {
			this.o_sharedMemoryBidirectionalConfig.o_sharedMemoryIntervalCompleteRefresh = p_o_value;
		}
	}
	
	/**
	 * get bidirectional hosts list
	 * 
	 * @return list of String
	 */
	public java.util.List<String> getBiHosts() {
		return this.a_biHosts;
	}
	
	/**
	 * get bidirectional ports list
	 * 
	 * @return list of Integer
	 */
	public java.util.List<Integer> getBiPorts() {
		return this.a_biPorts;
	}
	
	/**
	 * get shared memory bidirectional configuration
	 * 
	 * @return Config
	 */
	public Config getSharedMemoryBidirectional() {
		return this.o_sharedMemoryBidirectionalConfig;
	}
	
	/**
	 * Prepare all settings for shared memory bidirectional network communication exchange, so that all changes in fields of inherited class of shared memory object are transferred over the network to the other communication side automatically.
	 * Receive type will be SERVER. No own instantiated ssl context objects.
	 * 
	 * @param p_a_value						list of pairs, hosts and ports parameter list for every socket which will communicate with the other communication side, auto check for duplicates
	 * @throws IllegalStateException		shared memory object is not configured in current communication config instance
	 * @throws NullPointerException			parameter is null or has no entries, host is null
	 * @throws IllegalArgumentException		invalid port value, must be between 1..65535
	 */
	public void setSharedMemoryBidirectional(java.util.List<java.util.AbstractMap.SimpleEntry<String, Integer>> p_a_value) throws IllegalStateException, NullPointerException, IllegalArgumentException {
		this.setSharedMemoryBidirectional(p_a_value, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER, null);
	}
	
	/**
	 * Prepare all settings for shared memory bidirectional network communication exchange, so that all changes in fields of inherited class of shared memory object are transferred over the network to the other communication side automatically.
	 * Receive type will be SERVER 
	 * 
	 * @param p_a_value						list of pairs, hosts and ports parameter list for every socket which will communicate with the other communication side, auto check for duplicates
	 * @param p_a_sslContextList			list of own instantiated ssl context objects for every socket handling communication for the other side
	 * @throws IllegalStateException		shared memory object is not configured in current communication config instance
	 * @throws NullPointerException			parameter is null or has no entries, host is null
	 * @throws IllegalArgumentException		invalid port value, must be between 1..65535
	 */
	public void setSharedMemoryBidirectional(java.util.List<java.util.AbstractMap.SimpleEntry<String, Integer>> p_a_value, java.util.List<javax.net.ssl.SSLContext> p_a_sslContextList) throws IllegalStateException, NullPointerException, IllegalArgumentException {
		this.setSharedMemoryBidirectional(p_a_value, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER, p_a_sslContextList);
	}
	
	/**
	 * Prepare all settings for shared memory bidirectional network communication exchange, so that all changes in fields of inherited class of shared memory object are transferred over the network to the other communication side automatically 
	 * 
	 * @param p_a_value						list of pairs, hosts and ports parameter list for every socket which will communicate with the other communication side, auto check for duplicates
	 * @param p_e_socketReceiveType			determine receive type for all sockets receiving message from the other communication side: SOCKET or SERVER
	 * @param p_a_sslContextList			list of own instantiated ssl context objects for every socket handling communication for the other side
	 * @throws IllegalStateException		shared memory object is not configured in current communication config instance
	 * @throws NullPointerException			parameter is null or has no entries, host is null
	 * @throws IllegalArgumentException		invalid port value, must be between 1..65535
	 */
	public void setSharedMemoryBidirectional(java.util.List<java.util.AbstractMap.SimpleEntry<String, Integer>> p_a_value, net.forestany.forestj.lib.net.sock.recv.ReceiveType p_e_socketReceiveType, java.util.List<javax.net.ssl.SSLContext> p_a_sslContextList) throws IllegalStateException, NullPointerException, IllegalArgumentException {
		/* check shared memory object setting */
		if (this.o_sharedMemory == null) {
			throw new IllegalStateException("Shared memory instance not specified");
		}
		
		/* check parameter */
		if (p_a_value == null) {
			throw new NullPointerException("Parameter value is 'null' for bidirectional hosts and ports");
		}
		
		/* check parameter entries */
		if (p_a_value.size() < 1) {
			throw new NullPointerException("Please specify at least '1' entry for bidirectional hosts and ports");
		}
		
		this.a_biHosts = new java.util.ArrayList<String>();
		this.a_biPorts = new java.util.ArrayList<Integer>();
		
		java.util.List<String> a_checkDuplicates = new java.util.ArrayList<String>();
		int i = 0;
		
		/* iterate each pair of host and port in parameter list */
		for (java.util.AbstractMap.SimpleEntry<String, Integer> o_pair : p_a_value) {
			i++;
			
			/* check if host value is not empty */
			if (net.forestany.forestj.lib.Helper.isStringEmpty(o_pair.getKey())) {
				throw new NullPointerException("Host #" + i + " is null");
			}
			
			/* check port min. value */
			if (o_pair.getValue() < 1) {
				throw new IllegalArgumentException("Port #" + i + " must be at least '1', but was set to '" + o_pair.getValue() + "'");
			}
			
			/* check port max. value */
			if (o_pair.getValue() > 65535) {
				throw new IllegalArgumentException("Port #" + i + " must be lower equal '65535', but was set to '" + o_pair.getValue() + "'");
			} 
			
			String s_duplicate = o_pair.getKey() + ":" + o_pair.getValue();
			
			/* check for existing duplicate */
			if (a_checkDuplicates.contains(s_duplicate)) {
				this.a_biHosts = new java.util.ArrayList<String>();
				this.a_biPorts = new java.util.ArrayList<Integer>();
				throw new UnsupportedOperationException("Duplicate found[" + s_duplicate + "] in list of target hosts and ports which is not allowed");
			}
			
			/* add value to temp list for checking duplicates */
			a_checkDuplicates.add(s_duplicate);
			
			this.a_biHosts.add(o_pair.getKey());
			this.a_biPorts.add(o_pair.getValue());
		}
		
		/* set same communication type as this config */
		Type e_biCommunicationType = this.e_communicationType;
		
		/* set opposite communication type */
		if (this.e_communicationType == Type.UDP_RECEIVE) {
			e_biCommunicationType = Type.UDP_SEND;
		} else if (this.e_communicationType == Type.UDP_SEND) {
			e_biCommunicationType = Type.UDP_RECEIVE;
		} else if (this.e_communicationType == Type.UDP_RECEIVE_WITH_ACK) {
			e_biCommunicationType = Type.UDP_SEND_WITH_ACK;
		} else if (this.e_communicationType == Type.UDP_SEND_WITH_ACK) {
			e_biCommunicationType = Type.UDP_RECEIVE_WITH_ACK;
		} else if (this.e_communicationType == Type.TCP_RECEIVE) {
			e_biCommunicationType = Type.TCP_SEND;
		} else if (this.e_communicationType == Type.TCP_SEND) {
			e_biCommunicationType = Type.TCP_RECEIVE;
		} else if (this.e_communicationType == Type.UDP_MULTICAST_RECEIVER) {
			e_biCommunicationType = Type.UDP_MULTICAST_SENDER;
		} else if (this.e_communicationType == Type.UDP_MULTICAST_SENDER) {
			e_biCommunicationType = Type.UDP_MULTICAST_RECEIVER;
		}
		
		/* create config for shared memory bidirectional network communication exchange */
		this.o_sharedMemoryBidirectionalConfig = new Config(e_biCommunicationType, this.e_cardinality);
		
		/* adopt all settings except receive type and ssl context list */
		this.o_sharedMemoryBidirectionalConfig.e_socketReceiveType = p_e_socketReceiveType;
		this.o_sharedMemoryBidirectionalConfig.i_socketExecutorServicePoolAmount = this.i_socketExecutorServicePoolAmount;
		
		this.o_sharedMemoryBidirectionalConfig.e_security = this.e_security;
		this.o_sharedMemoryBidirectionalConfig.s_commonSecretPassphrase = this.s_commonSecretPassphrase;
		this.o_sharedMemoryBidirectionalConfig.a_sslContextList = p_a_sslContextList;
		
		this.o_sharedMemoryBidirectionalConfig.i_amountMessageBoxes = this.i_amountMessageBoxes;
		this.o_sharedMemoryBidirectionalConfig.i_amountSockets = this.i_amountSockets;
		this.o_sharedMemoryBidirectionalConfig.a_messageBoxLengths = this.a_messageBoxLengths;
		this.o_sharedMemoryBidirectionalConfig.a_hosts = this.a_biHosts;
		this.o_sharedMemoryBidirectionalConfig.a_ports = this.a_biPorts;
		
		this.o_sharedMemoryBidirectionalConfig.a_socketTasks = this.a_socketTasks;
		
		this.o_sharedMemoryBidirectionalConfig.i_receiverTimeoutMilliseconds = this.i_receiverTimeoutMilliseconds;
		this.o_sharedMemoryBidirectionalConfig.i_senderTimeoutMilliseconds = this.i_senderTimeoutMilliseconds;
		this.o_sharedMemoryBidirectionalConfig.b_checkReachability = this.b_checkReachability;
		this.o_sharedMemoryBidirectionalConfig.i_maxTerminations = this.i_maxTerminations;
		this.o_sharedMemoryBidirectionalConfig.i_senderIntervalMilliseconds = this.i_senderIntervalMilliseconds;
		this.o_sharedMemoryBidirectionalConfig.i_queueTimeoutMilliseconds = this.i_queueTimeoutMilliseconds;
		
		this.o_sharedMemoryBidirectionalConfig.i_udpReceiveAckTimeoutMilliseconds = this.i_udpReceiveAckTimeoutMilliseconds;
		this.o_sharedMemoryBidirectionalConfig.i_udpSendAckTimeoutMilliseconds = this.i_udpSendAckTimeoutMilliseconds;
		this.o_sharedMemoryBidirectionalConfig.i_udpMulticastSenderTTL = this.i_udpMulticastSenderTTL;
		this.o_sharedMemoryBidirectionalConfig.s_udpMulticastReceiverNetworkInterfaceName = this.s_udpMulticastReceiverNetworkInterfaceName;
		
		this.o_sharedMemoryBidirectionalConfig.s_localAddress = this.s_localAddress;
		this.o_sharedMemoryBidirectionalConfig.i_localPort = this.i_localPort;
		
		this.o_sharedMemoryBidirectionalConfig.b_objectTransmission = this.b_objectTransmission;
		
		this.o_sharedMemoryBidirectionalConfig.a_receiveSocketTasks = this.a_receiveSocketTasks;
		
		this.o_sharedMemoryBidirectionalConfig.o_sharedMemory = this.o_sharedMemory;
		this.o_sharedMemoryBidirectionalConfig.i_sharedMemoryTimeoutMilliseconds = this.i_sharedMemoryTimeoutMilliseconds;
		this.o_sharedMemoryBidirectionalConfig.o_sharedMemoryIntervalCompleteRefresh = this.o_sharedMemoryIntervalCompleteRefresh;
		this.o_sharedMemoryBidirectionalConfig.a_biHosts = null;
		this.o_sharedMemoryBidirectionalConfig.a_biPorts = null;
		this.o_sharedMemoryBidirectionalConfig.o_sharedMemoryBidirectionalConfig = null;
		
		/* set flag that shared memory bidirectional config has been set */
		this.o_sharedMemoryBidirectionalConfig.b_sharedMemoryBidirectionalConfigSet = true;
		
		this.o_sharedMemoryBidirectionalConfig.b_debugNetworkTrafficOn = this.b_debugNetworkTrafficOn;
		
		this.o_sharedMemoryBidirectionalConfig.b_useMarshalling = this.b_useMarshalling;
		this.o_sharedMemoryBidirectionalConfig.b_useMarshallingWholeObject = this.b_useMarshallingWholeObject;
		this.o_sharedMemoryBidirectionalConfig.i_marshallingDataLengthInBytes = this.i_marshallingDataLengthInBytes;
		this.o_sharedMemoryBidirectionalConfig.b_marshallingUsePropertyMethods = this.b_marshallingUsePropertyMethods;
		this.o_sharedMemoryBidirectionalConfig.s_marshallingOverrideMessageType = this.s_marshallingOverrideMessageType;
		this.o_sharedMemoryBidirectionalConfig.b_marshallingSystemUsesLittleEndian = this.b_marshallingSystemUsesLittleEndian;
		
		/* set flag that configuration has been closed and cannot be adjusted anymore */
		this.b_closed = true;
		this.o_sharedMemoryBidirectionalConfig.b_closed = this.b_closed;
	}
	
	/**
	 * is shared memory bidirectional config set
	 * 
	 * @return boolean
	 */
	public boolean isSharedMemoryBidirectionalConfigSet() {
		return this.b_sharedMemoryBidirectionalConfigSet;
	}
	
	/* Methods */
	
	/**
	 * Constructor of configuration class. All other settings are adjusted by set-property-methods
	 * 
	 * @param p_e_communicationType					communication type enumeration to determine if this communication instance will send or receive network data
	 * @param p_e_cardinality						specifies communication cardinality of communication instance
	 */
	public Config(Type p_e_communicationType, Cardinality p_e_cardinality) {
		this.e_communicationType = p_e_communicationType;
		this.e_socketReceiveType = null;
		this.e_cardinality = p_e_cardinality;
		this.i_socketExecutorServicePoolAmount = 0;
		
		this.e_security = null;
		this.s_commonSecretPassphrase = null;
		this.a_sslContextList = null;
		
		this.i_amountMessageBoxes = 0;
		this.i_amountSockets = 0;
		this.a_messageBoxLengths = null;
		this.a_hosts = null;
		this.a_ports = null;
		
		this.a_socketTasks = null;
		
		this.i_receiverTimeoutMilliseconds = 1;
		this.i_senderTimeoutMilliseconds = 1;
		this.b_checkReachability = false;
		this.i_maxTerminations = -1;
		this.i_senderIntervalMilliseconds = 0;
		this.i_queueTimeoutMilliseconds = 1;
		
		this.i_udpReceiveAckTimeoutMilliseconds = 1;
		this.i_udpSendAckTimeoutMilliseconds = 1;
		this.i_udpMulticastSenderTTL = 1;
		this.s_udpMulticastReceiverNetworkInterfaceName = null;
		
		this.s_localAddress = null;
		this.i_localPort = 0;
		
		this.b_objectTransmission = false;
		
		this.a_receiveSocketTasks = null;
		
		this.o_sharedMemory = null;
		this.i_sharedMemoryTimeoutMilliseconds = 1;
		this.o_sharedMemoryIntervalCompleteRefresh = null;
		this.a_biHosts = null;
		this.a_biPorts = null;
		this.o_sharedMemoryBidirectionalConfig = null;
		this.b_sharedMemoryBidirectionalConfigSet = false;
		
		this.b_closed = false;
		
		this.b_debugNetworkTrafficOn = false;
		
		this.b_useMarshalling = false;
		this.b_useMarshallingWholeObject = false;
		this.i_marshallingDataLengthInBytes = 1;
		this.b_marshallingUsePropertyMethods = false;
		this.s_marshallingOverrideMessageType = null;
		this.b_marshallingSystemUsesLittleEndian = false;
		
		this.itf_delegates = null;
	}

	/**
	 * Overwrite truststore setting of current jvm which is in use
	 * 
	 * @param p_s_trustStoreLocation			file path to truststore file
	 * @param p_s_trustStorePassword			password for truststore file
	 */
	public void setTrustStoreProperties(String p_s_trustStoreLocation, String p_s_trustStorePassword) {
		System.setProperty("javax.net.ssl.trustStore", p_s_trustStoreLocation);
		System.setProperty("javax.net.ssl.trustStorePassword", p_s_trustStorePassword);
	}

	/**
	 * internal method to check if configuration has been closed and cannot be adjusted anymore
	 * 
	 * @throws IllegalStateException				config has been close, because bidirectional setting was finished
	 */
	private void checkClosed() throws IllegalStateException {
		if (this.b_closed) {
			throw new IllegalStateException("Config has been closed, because bidirectional setting was finished");
		}
	}

	/**
	 * internal method to check if configuration has been closed and cannot be adjusted anymore, because shared memory object will handle these settings
	 * 
	 * @throws IllegalStateException				config has been close, because shared memory object will handle these settings
	 */
	private void checkSharedMemoryset(String p_s_involvedSetting) throws IllegalStateException {
		if (this.o_sharedMemory != null) {
			throw new IllegalStateException("Configuration of " + p_s_involvedSetting + " is closed. Controlled by shared memory object.");
		}
	}
}
