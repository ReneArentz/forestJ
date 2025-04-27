package net.forestany.forestj.lib.net.msg;

/**
 * Encapsulation of a network message where all information will be serialized into bytes.
 * Message box id is encoded into 2 bytes [0 .. 65535].
 * Amount of messages holding related data encoded into 2 bytes [0 .. 65535].
 * Message number is encoded into 2 bytes [0 .. 65535].
 * Message type length (amount of characters which describing the type of the message) is encoded into 1 byte [0 .. 255].
 * Message type is encoded into 255 bytes [UTF-8 characters].
 * 
 * Following overall message lengths are valid: 1472, 1484, 1500, 8160, 8176, 8192.
 */
public class Message {
	
	/* Constants */
	private static final int MSGBOXIDLEN = 2;
	private static final int MSGAMOUNTLEN = 2;
	private static final int MSGNRLEN = 2;
	private static final int MSGTYPELEN = 1;
	private static final int MSGTYPE = 255;
	
	private static final int MSGLEN_1472 = 1472;
	private static final int MSGLEN_1484 = 1484;
	private static final int MSGLEN_1500 = 1500;
	private static final int MSGLEN_8164 = 8164;
	private static final int MSGLEN_8176 = 8176;
	private static final int MSGLEN_8192 = 8192;
	/**
	 * MSGLENGTHS constants in an array
	 */
	public static final int[] MSGLENGTHS = {MSGLEN_1472, MSGLEN_1484, MSGLEN_1500, MSGLEN_8164, MSGLEN_8176, MSGLEN_8192};
	/**
	 * MSGLENGTHS constants as a string
	 */
	public static String MSGLENGTHS_STR = MSGLEN_1472 + ", " + MSGLEN_1484 + ", " + MSGLEN_1500 + ", " + MSGLEN_8164 + ", " + MSGLEN_8176 + ", " + MSGLEN_8192;
	
	/* Fields */
	
	private int i_messageLength;
	private int i_dataBlockLength;
	private byte[] a_messageBoxId;
	private byte[] a_messageAmount;
	private byte[] a_messageNumber;
	private byte[] a_typeLength;
	private byte[] a_type;
	private byte[] a_dataLength;
	private byte[] a_data;
	
	/* Properties */
	
	/**
	 * get message length
	 * 
	 * @return int
	 */
	public int getMessageLength() {
		return this.i_messageLength;
	}
	
	/**
	 * get data block length
	 * 
	 * @return int
	 */
	public int getDataBlockLength() {
		return this.i_dataBlockLength;
	}
	
	/**
	 * get message box id
	 * 
	 * @return int
	 */
	public int getMessageBoxId() {
		return net.forestany.forestj.lib.Helper.byteArrayToInt(this.a_messageBoxId);
	}
	
	/**
	 * set message box id
	 * 
	 * @param p_i_messageBoxId int
	 * @throws IllegalArgumentException must be a positive int value and lower than a max. value
	 */
	public void setMessageBoxId(int p_i_messageBoxId) throws IllegalArgumentException {
		/* check message box id min value */
		if (p_i_messageBoxId <= 0) {
			throw new IllegalArgumentException("Message box id must be a positive integer");
		}
		
		/* check message box id max value, 2^x = 1 << x -> (1 byte) -> 255 = 2 ^ [8 * (1)] - 1 | (2 byte) -> 65535 = 2 ^ [8 * (2)] - 1 */
		if (p_i_messageBoxId > ( (1 << (8 * MSGBOXIDLEN)) - 1 ) ) {
			throw new IllegalArgumentException("Invalid message box id[" + p_i_messageBoxId + "]. Must be a positive integer lower than[" + ( (1 << (8 * MSGBOXIDLEN)) - 1 ) + "]");
		}
		
		/* cast message box id int to bytes */
		byte[] a_castedMessageBoxId = net.forestany.forestj.lib.Helper.intToByteArray(p_i_messageBoxId);
		
		/* copy bytes to byte array for message box id */
		for (int i = this.a_messageBoxId.length; i > 0; i--) {
			/* reached last byte? -> break */
			if ( ((i - 1) + (a_castedMessageBoxId.length - this.a_messageBoxId.length)) < 0 ) {
				break;
			}
			
			/* copy byte to array */
			this.a_messageBoxId[i - 1] = a_castedMessageBoxId[(i - 1) + (a_castedMessageBoxId.length - this.a_messageBoxId.length)];
		}
	}
	
	/**
	 * get message amount
	 * 
	 * @return int
	 */
	public int getMessageAmount() {
		return net.forestany.forestj.lib.Helper.byteArrayToInt(this.a_messageAmount);
	}
	
	/**
	 * set message amount
	 * 
	 * @param p_i_messageAmount int
	 * @throws IllegalArgumentException must be a positive int value and lower than a max. value
	 */
	public void setMessageAmount(int p_i_messageAmount) throws IllegalArgumentException {
		/* check message amount min value */
		if (p_i_messageAmount <= 0) {
			throw new IllegalArgumentException("Message amount must be a positive integer");
		}
		
		/* check message amount max value, 2^x = 1 << x -> (1 byte) -> 255 = 2 ^ [8 * (1)] - 1 | (2 byte) -> 65535 = 2 ^ [8 * (2)] - 1 */
		if (p_i_messageAmount > ( (1 << (8 * MSGAMOUNTLEN)) - 1 ) ) {
			throw new IllegalArgumentException("Invalid message amount[" + p_i_messageAmount + "]. Must be a positive integer lower than[" + ( (1 << (8 * MSGAMOUNTLEN)) - 1 ) + "]");
		}
		
		/* cast message amount int to bytes */
		byte[] a_castedMessageAmount = net.forestany.forestj.lib.Helper.intToByteArray(p_i_messageAmount);
		
		/* copy bytes to byte array for message amount */
		for (int i = this.a_messageAmount.length; i > 0; i--) {
			/* reached last byte? -> break */
			if ( ((i - 1) + (a_castedMessageAmount.length - this.a_messageAmount.length)) < 0 ) {
				break;
			}
			
			/* copy byte to array */
			this.a_messageAmount[i - 1] = a_castedMessageAmount[(i - 1) + (a_castedMessageAmount.length - this.a_messageAmount.length)];
		}
	}
	
	/**
	 * get messgae number
	 * 
	 * @return int
	 */
	public int getMessageNumber() {
		return net.forestany.forestj.lib.Helper.byteArrayToInt(this.a_messageNumber);
	}
	
	/**
	 * set message number
	 * 
	 * @param p_i_messageNumber int
	 * @throws IllegalArgumentException must be a positive int value and lower than a max. value
	 */
	public void setMessageNumber(int p_i_messageNumber) throws IllegalArgumentException {
		/* check message number min value */
		if (p_i_messageNumber <= 0) {
			throw new IllegalArgumentException("Message number must be a positive integer");
		}
		
		/* check message number max value, 2^x = 1 << x -> (1 byte) -> 255 = 2 ^ [8 * (1)] - 1 | (2 byte) -> 65535 = 2 ^ [8 * (2)] - 1 */
		if (p_i_messageNumber > ( (1 << (8 * MSGNRLEN)) - 1 ) ) {
			throw new IllegalArgumentException("Invalid message number[" + p_i_messageNumber + "]. Must be a positive integer lower than[" + ( (1 << (8 * MSGNRLEN)) - 1 ) + "]");
		}
		
		/* cast message number int to bytes */
		byte[] a_castedMessageNumber = net.forestany.forestj.lib.Helper.intToByteArray(p_i_messageNumber);
		
		/* copy bytes to byte array for message number */
		for (int i = this.a_messageNumber.length; i > 0; i--) {
			/* reached last byte? -> break */
			if ( ((i - 1) + (a_castedMessageNumber.length - this.a_messageNumber.length)) < 0 ) {
				break;
			}
			
			/* copy byte to array */
			this.a_messageNumber[i - 1] = a_castedMessageNumber[(i - 1) + (a_castedMessageNumber.length - this.a_messageNumber.length)];
		}
	}
	
	/**
	 * get type
	 * 
	 * @return String
	 */
	public String getType() {
		/* create string bytes array */
		byte[] a_stringBytes = new byte[this.a_typeLength[0]];
		
		/* iterate each character byte, length stored in type length array (MSGTYPELEN -> 1 byte) */
		for (int i = 0; i < this.a_typeLength[0]; i++) {
			/* copy character byte to string byte array */
			a_stringBytes[i] = this.a_type[i];
		}
		
		/* return String with string bytes array */
		return new String(a_stringBytes);
	}
	
	/**
	 * set type
	 * 
	 * @param p_s_type String
	 * @throws IllegalArgumentException length must not exceed max length
	 */
	public void setType(String p_s_type) throws IllegalArgumentException {
		/* convert parameter string to byte array */
		byte[] a_stringType = p_s_type.getBytes(java.nio.charset.StandardCharsets.UTF_8);
		
		/* check if byte array length does not exceed max value */
		if (a_stringType.length > Message.MSGTYPE) {
			throw new IllegalArgumentException("Type has max length[" + Message.MSGTYPE + "], but parameter is byte[" + p_s_type.length() + "] long");
		}
		
		/* convert length of byte array to a byte value itself; because MSGTYPE -> 255 we always get only one byte as return value */
		Integer i_typeLength = Integer.valueOf(a_stringType.length);
		this.a_typeLength[0] = i_typeLength.byteValue();
		
		/* copy byte array to type array */
		for (int i = 0; i < a_stringType.length; i++) {
			this.a_type[i] = a_stringType[i];
		}
	}
	
	/**
	 * get data length
	 * 
	 * @return int
	 */
	public int getDataLength() {
		return net.forestany.forestj.lib.Helper.byteArrayToInt(this.a_dataLength);
	}
	
	/**
	 * set data length
	 * 
	 * @param p_i_dataLength int
	 */
	public void setDataLength(int p_i_dataLength) {
		/* set data length byte array to zero values; it's length is determined in Message constructor */
		for (int i = 0; i < this.a_dataLength.length; i++) {
			this.a_dataLength[i] = 0;
		}
		
		/* cast data length int to bytes */
		byte[] a_castedDataLength = net.forestany.forestj.lib.Helper.intToByteArray(p_i_dataLength);
		
		/* copy bytes to byte array for data length */
		for (int i = this.a_dataLength.length; i > 0; i--) {
			/* reached last byte? -> break */
			if ( ((i - 1) + (a_castedDataLength.length - this.a_dataLength.length)) < 0 ) {
				break;
			}
			
			/* copy byte to array */
			this.a_dataLength[i - 1] = a_castedDataLength[(i - 1) + (a_castedDataLength.length - this.a_dataLength.length)];
		}
	}
	
	/**
	 * get data
	 * 
	 * @return byte[]
	 */
	public byte[] getData() {
		return this.a_data;
	}
	
	/**
	 * set data
	 * 
	 * @param p_a_bytes byte[]
	 * @throws IllegalArgumentException amount of data bytes does exceed determined data block length from constructor
	 */
	public void setData(byte[] p_a_bytes) throws IllegalArgumentException {
		/* check if amount of data bytes does not exceed determined data block length from constructor */
		if (p_a_bytes.length > this.i_dataBlockLength) {
			throw new IllegalArgumentException("Data is byte[" + this.i_dataBlockLength + "], but parameter is byte[" + p_a_bytes.length + "]");
		}
		
		/* set data length byte array with data length integer value */
		this.setDataLength(p_a_bytes.length);
		
		/* copy parameter data to data bytes array */
		for (int i = 0; i < p_a_bytes.length; i++) {
			this.a_data[i] = p_a_bytes[i]; 
		}
	}
	
	/* Methods */
	
	/**
	 * Create network message length with a specific length
	 * 
	 * @param p_i_length						overall length of network message, valid values are within Message.MSGLENGTHS
	 * @throws IllegalArgumentException			exceeded max message length
	 */
	public Message(int p_i_length) throws IllegalArgumentException {
		/* check message length parameter */
		if (p_i_length > MSGLEN_8192) {
			throw new IllegalArgumentException("Invalid length[" + p_i_length + "] for message. Max message length is [" + MSGLEN_8192 + "]");
		}
		
		/* auto assign message length */
		if (p_i_length <= MSGLEN_1472) {
			p_i_length = MSGLEN_1472;
		} else if ((p_i_length > MSGLEN_1472) && (p_i_length <= MSGLEN_1484)) {
			p_i_length = MSGLEN_1484;
		} else if ((p_i_length > MSGLEN_1484) && (p_i_length <= MSGLEN_1500)) {
			p_i_length = MSGLEN_1500;
		} else if ((p_i_length > MSGLEN_1500) && (p_i_length <= MSGLEN_8164)) {
			p_i_length = MSGLEN_8164;
		} else if ((p_i_length > MSGLEN_8164) && (p_i_length <= MSGLEN_8176)) {
			p_i_length = MSGLEN_8176;
		} else {
			p_i_length = MSGLEN_8192;
		}
		
		/* initiate byte arrays */
		this.i_messageLength = p_i_length;
		this.a_messageBoxId = new byte[Message.MSGBOXIDLEN];
		this.a_messageAmount = new byte[Message.MSGAMOUNTLEN];
		this.a_messageNumber = new byte[Message.MSGNRLEN];
		this.a_typeLength = new byte[Message.MSGTYPELEN];
		this.a_type = new byte[Message.MSGTYPE];
		
		/* get data block length by taking message length and subtracting all byte array lengths */
		this.i_dataBlockLength = this.i_messageLength - this.a_messageBoxId.length - this.a_messageAmount.length - this.a_messageNumber.length - this.a_typeLength.length - this.a_type.length;
		
		/* determine how many bytes we need to store data length --- length for data length = log10(data block length) / log10(2) / 8 */
		int i_lengthByteData = (int)java.lang.Math.ceil(java.lang.Math.log10(this.i_dataBlockLength) / java.lang.Math.log10(2));
		i_lengthByteData = (int)java.lang.Math.ceil((double)i_lengthByteData / 8.0d);
		
		/* initiate data length byte array */
		this.a_dataLength = new byte[i_lengthByteData];
		
		/* data block length decreased with bytes we need to store data length */
		this.i_dataBlockLength -= i_lengthByteData;
		
		/* initiate data byte array */
		this.a_data = new byte[this.i_dataBlockLength];
	}
	
	/**
	 * Get data block length by giving overall message length
	 * 
	 * @param p_i_length					overall length of network message, valid values are within Message.MSGLENGTHS	
	 * @return int
	 * @throws IllegalArgumentException		invalid length for network message		
	 */
	public static int calculateDataBlockLength(int p_i_length) throws IllegalArgumentException {
		int i_return = 0;
		
		/* check length parameter */
		if (!java.util.Arrays.stream(Message.MSGLENGTHS).anyMatch(i -> i == p_i_length)) {
			throw new IllegalArgumentException("Invalid length[" + p_i_length + "] for message. Valid values are [" + Message.MSGLENGTHS_STR + "]");
		}
			
		/* get data block length by taking message length and subtracting all byte array lengths */
		i_return = p_i_length - Message.MSGBOXIDLEN - Message.MSGAMOUNTLEN - Message.MSGNRLEN - Message.MSGTYPELEN - Message.MSGTYPE;
		
		/* determine how many bytes we need to store data length --- length for data length = log10(data block length) / log10(2) / 8 */
		int i_lengthByteData = (int)java.lang.Math.ceil(java.lang.Math.log10(i_return) / java.lang.Math.log10(2));
		i_lengthByteData = (int)java.lang.Math.ceil((double)i_lengthByteData / 8.0d);
		
		/* data block length decreased with bytes we need to store data length */
		i_return -= i_lengthByteData;
		
		/* return data block length */
		return i_return;
	}
	
	/**
	 * Get complete message object with all information as byte array
	 * 
	 * @return byte[] array
	 */
	public byte[] getByteArrayFromMessage() {
		/* return value */
		byte[] a_message = new byte[this.i_messageLength];
		
		/* index values */
		int i, j, k, l, m, n, o = 0;
		
		/* handle message box id */
		for (i = 0; i < this.a_messageBoxId.length; i++) {
			if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("MessageBoxId{" + (i + 1) + " of " + this.a_messageBoxId.length + "}: [" + (i) + "] " + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {this.a_messageBoxId[i]}, false) );
			a_message[i] = this.a_messageBoxId[i];
		}
		
		/* handle message amount */
		for (j = 0; j < this.a_messageAmount.length; j++) {
			if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("MessageAmount{" + (j + 1) + " of " + this.a_messageAmount.length + "}: [" + (i + j) + "] " + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {this.a_messageAmount[j]}, false) );
			a_message[i + j] = this.a_messageAmount[j];
		}
		
		/* handle message number */
		for (k = 0; k < this.a_messageNumber.length; k++) {
			if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("MessageNumber{" + (k + 1) + " of " + this.a_messageNumber.length + "}: [" + (i + j + k) + "] " + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {this.a_messageNumber[k]}, false) );
			a_message[i + j + k] = this.a_messageNumber[k];
		}
		
		/* handle message type length */
		for (l = 0; l < this.a_typeLength.length; l++) {
			if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("MessageTypeLength{" + (l + 1) + " of " + this.a_typeLength.length + "}: [" + (i + j + k + l) + "] " + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {this.a_typeLength[l]}, false) );
			a_message[i + j + k + l] = this.a_typeLength[l];
		}
		
		/* handle message type */
		for (m = 0; m < this.a_type.length; m++) {
			if (m < this.a_typeLength[0]) {
				if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("MessageType{" + (m + 1) + " of " + this.a_type.length + "}: [" + (i + j + k + l + m) + "] " + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {this.a_type[m]}, false) );
			}
			
			a_message[i + j + k + l + m] = this.a_type[m];
		}
		
		/* handle message data length */
		for (n = 0; n < this.a_dataLength.length; n++) {
			if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("MessageDataLength{" + (n + 1) + " of " + this.a_dataLength.length + "}: [" + (i + j + k + l + m + n) + "] " + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {this.a_dataLength[n]}, false) );
			a_message[i + j + k + l + m + n] = this.a_dataLength[n];
		}
		
		/* handle message data */
		for (o = 0; o < this.a_data.length; o++) {
			/* reached last byte? -> break */
			if ((i + j + k + l + m + n + o) >= this.i_messageLength) {
				break;
			}
			
			if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("MessageData{" + (o + 1) + " of " + this.a_data.length + "}: [" + (i + j + k + l + m + n + o) + "] " + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {this.a_data[o]}, false) );
			a_message[i + j + k + l + m + n + o] = this.a_data[o];
		}
		
		/* return byte array */
		return a_message;
	}
	
	/**
	 * Set complete message object with all information from a byte array
	 * 
	 * @param p_a_message						byte array containing all message information
	 * @throws IllegalArgumentException			parameter byte array has not the minimal message length or is bigger than the maximal message length
	 */
	public void setMessageFromByteArray(byte[] p_a_message) throws IllegalArgumentException {
		int i_minimalMessageLength = (this.a_messageBoxId.length + this.a_messageAmount.length + this.a_messageNumber.length + this.a_typeLength.length + this.a_type.length);
		
		/* check for minimal message length */
		if (p_a_message.length < i_minimalMessageLength) {
			throw new IllegalArgumentException("Amount of message bytes[" + p_a_message.length + "] must be greater than minimal message length[" + i_minimalMessageLength + "]");
		}
		
		/* check for maximal message length */
		if (p_a_message.length > this.i_messageLength) {
			throw new IllegalArgumentException("Amount of message bytes[" + p_a_message.length + "] must be lower than allowed message length[" + this.i_messageLength + "]");
		}
		
		/* index values */
		int i, j, k, l, m, n, o = 0;
		
		/* read message box id from byte array */
		for (i = 0; i < this.a_messageBoxId.length; i++) {
			if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("MessageBoxId{" + (i + 1) + " of " + this.a_messageBoxId.length + "}: [" + (i) + "] " + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {p_a_message[i]}, false) );
			this.a_messageBoxId[i] = p_a_message[i];
		}
		
		/* read message amount from byte array */
		for (j = 0; j < this.a_messageAmount.length; j++) {
			if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("MessageAmount{" + (j + 1) + " of " + this.a_messageAmount.length + "}: [" + (i + j) + "] " + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {p_a_message[i + j]}, false) );
			this.a_messageAmount[j] = p_a_message[i + j];
		}
		
		/* read message number from byte array */
		for (k = 0; k < this.a_messageNumber.length; k++) {
			if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("MessageNumber{" + (k + 1) + " of " + this.a_messageNumber.length + "}: [" + (i + j + k) + "] " + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {p_a_message[i + j + k]}, false) );
			this.a_messageNumber[k] = p_a_message[i + j + k];
		}
		
		/* read message type length from byte array */
		for (l = 0; l < this.a_typeLength.length; l++) {
			if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("MessageTypeLength{" + (l + 1) + " of " + this.a_typeLength.length + "}: [" + (i + j + k + l) + "] " + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {p_a_message[i + j + k + l]}, false) );
			this.a_typeLength[l] = p_a_message[i + j + k + l];
		}
		
		/* read message type from byte array */
		for (m = 0; m < this.a_type.length; m++) {
			if (m < this.a_typeLength[0]) {
				if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("MessageType{" + (m + 1) + " of " + this.a_type.length + "}: [" + (i + j + k + l + m) + "] " + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {p_a_message[i + j + k + l + m]}, false) );
			}
			
			this.a_type[m] = p_a_message[i + j + k + l + m];
		}
		
		/* read message data length from byte array */
		for (n = 0; n < this.a_dataLength.length; n++) {
			if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("MessageDataLength{" + (n + 1) + " of " + this.a_dataLength.length + "}: [" + (i + j + k + l + m + n) + "] " + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {p_a_message[i + j + k + l + m + n]}, false) );
			this.a_dataLength[n] = p_a_message[i + j + k + l + m + n];
		}
		
		/* read message data from byte array */
		for (o = 0; o < this.a_data.length; o++) {
			/* reached last byte? -> break */
			if ((i + j + k + l + m + n + o) >= this.i_messageLength) {
				break;
			}
			
			if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("MessageData{" + (o + 1) + " of " + this.a_data.length + "}: [" + (i + j + k + l + m + n + o) + "] " + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {p_a_message[i + j + k + l + m + n + o]}, false) );
			this.a_data[o] = p_a_message[i + j + k + l + m + n + o];
		}
	}
}
