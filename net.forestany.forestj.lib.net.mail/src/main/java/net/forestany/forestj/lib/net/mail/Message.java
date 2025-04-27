package net.forestany.forestj.lib.net.mail;

/**
 * Encapsulation of an email message with all important information such as from, to, cc, bcc, subject, content, timing, attachments, etc.
 */
public class Message {
	
	/* Constants */
	
	/**
	 * FILENAMESEPARATOR constant
	 */
	public static final String FIlENAMESEPARATOR = "%_-_%";
	/**
	 * FLAGS
	 */
	public static final String[] FLAGS = {"ANSWERED", "DELETED", "DRAFT", "FLAGGED", "RECENT", "SEEN", "USER"};
	
	/* Fields */
	
	private String s_from;
	private java.util.List<String> a_from;
	
	private String s_to;
	private java.util.List<String> a_to;
	private String s_cc;
	private java.util.List<String> a_cc;
	private String s_bcc;
	private java.util.List<String> a_bcc;
	
	private String s_subject;
	private String s_text;
	private String s_html;
	
	private java.util.Date o_send;
	private java.util.Date o_received;
	private String s_contentType;
	
	private boolean b_expunged;
	private String s_description;
	private String s_disposition;
	private int i_size;
	
	private java.util.List<String> a_attachments;
	private java.util.List<byte[]> a_attachmentsContent;
	private java.util.List<String> a_flags;
	
	private java.util.List<String> a_header;

	private String s_messageId;
	
	/* Properties */
	
	/**
	 * get from
	 * 
	 * @return String
	 */
 	public String getFrom() {
		return this.s_from;
	}
	
 	/**
 	 * set from
 	 * 
 	 * @param p_s_value String
 	 * @throws IllegalArgumentException invalid value
 	 */
	public void setFrom(String p_s_value) throws IllegalArgumentException {
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			this.a_from.clear();
			
			if (!this.validateAddress(p_s_value)) {
				throw new IllegalArgumentException("Invalid e-mail-address '" + p_s_value + "'");
			}
			
			this.s_from = p_s_value;
			this.a_from.add(this.s_from);
		} else {
			this.s_from = null;
		}
	}
	
	/**
	 * get from list
	 * 
	 * @return java.util.List&lt;String&gt;
	 */
	public java.util.List<String> getFromList() {
		return this.a_from;
	}
	
	/**
	 * set from list
	 * 
	 * @param p_a_value java.util.List&lt;String&gt;
	 * @throws IllegalArgumentException parameter is empty or null
	 */
	public void setFromList(java.util.List<String> p_a_value) throws IllegalArgumentException {
		if ( (p_a_value == null) || (p_a_value.size() < 1) ) {
			throw new IllegalArgumentException("Empty e-mail-address list parameter or more than just one list element");
		} else {
			this.a_from.clear();
			
			for (String s_foo : p_a_value) {
				this.a_from.add(s_foo);
			}
		}
	}
	
	/**
	 * get to
	 * @return String
	 */
	public String getTo() {
		return this.s_to;
	}
	
	/**
	 * add to
	 * 
	 * @param p_s_value String
	 * @throws IllegalArgumentException invalid value
	 */
	public void addTo(String p_s_value) throws IllegalArgumentException {
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			if (p_s_value.contains(";")) {
				for (String s_foo : p_s_value.split(";")) {
					if (!this.validateAddress(s_foo)) {
						throw new IllegalArgumentException("Invalid e-mail-address '" + s_foo + "'");
					} else {
						this.a_to.add(s_foo);
					}
				}
				
				this.s_to += ";" + p_s_value;
			} else if (p_s_value.contains(",")) {
				for (String s_foo : p_s_value.split(",")) {
					if (!this.validateAddress(s_foo)) {
						throw new IllegalArgumentException("Invalid e-mail-address '" + s_foo + "'");
					} else {
						this.a_to.add(s_foo);
					}
				}
				
				this.s_to += "," + p_s_value;
			} else {
				if (!this.validateAddress(p_s_value)) {
					throw new IllegalArgumentException("Invalid e-mail-address '" + p_s_value + "'");
				} else {
					this.a_to.add(p_s_value);
					this.s_to = p_s_value;
				}
			}
		}
	}
	
	/**
	 * set to
	 * 
	 * @param p_s_value String
	 * @throws IllegalArgumentException invalid value
	 */
	public void setTo(String p_s_value) throws IllegalArgumentException {
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			this.a_to.clear();
			
			if (p_s_value.contains(";")) {
				for (String s_foo : p_s_value.split(";")) {
					if (!this.validateAddress(s_foo)) {
						throw new IllegalArgumentException("Invalid e-mail-address '" + s_foo + "'");
					} else {
						this.a_to.add(s_foo);
					}
				}
			} else if (p_s_value.contains(",")) {
				for (String s_foo : p_s_value.split(",")) {
					if (!this.validateAddress(s_foo)) {
						throw new IllegalArgumentException("Invalid e-mail-address '" + s_foo + "'");
					} else {
						this.a_to.add(s_foo);
					}
				}
			} else {
				if (!this.validateAddress(p_s_value)) {
					throw new IllegalArgumentException("Invalid e-mail-address '" + p_s_value + "'");
				} else {
					this.a_to.add(p_s_value);
				}
			}
			
			this.s_to = p_s_value;
		} else {
			throw new IllegalArgumentException("Empty value for \"To\"-field of E-Mail-Message.");
		}
	}
	
	/**
	 * get to list
	 * 
	 * @return java.util.List&lt;String&gt;
	 */
	public java.util.List<String> getToList() {
		return this.a_to;
	}
	
	/**
	 * set to list
	 * 
	 * @param p_a_value java.util.List&lt;String&gt;
	 * @throws IllegalArgumentException parameter is empty or null
	 */
	public void setToList(java.util.List<String> p_a_value) throws IllegalArgumentException {
		if ( (p_a_value == null) || (p_a_value.size() < 1) ) {
			throw new IllegalArgumentException("Empty e-mail-address list[TO] parameter");
		} else {
			this.a_to.clear();
			
			for (String s_foo : p_a_value) {
				this.a_to.add(s_foo);
			}
		}
	}
	
	/**
	 * get cc
	 * 
	 * @return String
	 */
	public String getCC() {
		return this.s_cc;
	}
	
	/**
	 * add cc
	 * 
	 * @param p_s_value String
	 * @throws IllegalArgumentException invalid value
	 */
	public void addCC(String p_s_value) throws IllegalArgumentException {
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			if (p_s_value.contains(";")) {
				for (String s_foo : p_s_value.split(";")) {
					if (!this.validateAddress(s_foo)) {
						throw new IllegalArgumentException("Invalid e-mail-address '" + s_foo + "'");
					} else {
						this.a_cc.add(s_foo);
					}
				}
				
				this.s_cc += ";" + p_s_value;
			} else if (p_s_value.contains(",")) {
				for (String s_foo : p_s_value.split(",")) {
					if (!this.validateAddress(s_foo)) {
						throw new IllegalArgumentException("Invalid e-mail-address '" + s_foo + "'");
					} else {
						this.a_cc.add(s_foo);
					}
				}
				
				this.s_cc += "," + p_s_value;
			} else {
				if (!this.validateAddress(p_s_value)) {
					throw new IllegalArgumentException("Invalid e-mail-address '" + p_s_value + "'");
				} else {
					this.a_cc.add(p_s_value);
					this.s_cc = p_s_value;
				}
			}
		}
	}
	
	/**
	 * set cc
	 * 
	 * @param p_s_value String
	 * @throws IllegalArgumentException invalid value
	 */
	public void setCC(String p_s_value) throws IllegalArgumentException {
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			this.a_cc.clear();
			
			if (p_s_value.contains(";")) {
				for (String s_foo : p_s_value.split(";")) {
					if (!this.validateAddress(s_foo)) {
						throw new IllegalArgumentException("Invalid e-mail-address '" + s_foo + "'");
					} else {
						this.a_cc.add(s_foo);
					}
				}
			} else if (p_s_value.contains(",")) {
				for (String s_foo : p_s_value.split(",")) {
					if (!this.validateAddress(s_foo)) {
						throw new IllegalArgumentException("Invalid e-mail-address '" + s_foo + "'");
					} else {
						this.a_cc.add(s_foo);
					}
				}
			} else {
				if (!this.validateAddress(p_s_value)) {
					throw new IllegalArgumentException("Invalid e-mail-address '" + p_s_value + "'");
				} else {
					this.a_cc.add(p_s_value);
				}
			}
			
			this.s_cc = p_s_value;
		} else {
			this.s_cc = null;
		}
	}
	
	/**
	 * get cc list
	 * 
	 * @return java.util.List&lt;String&gt;
	 */
	public java.util.List<String> getCCList() {
		return this.a_cc;
	}
	
	/**
	 * set cc list
	 * 
	 * @param p_a_value java.util.List&lt;String&gt;
	 * @throws IllegalArgumentException parameter is empty or null
	 */
	public void setCCList(java.util.List<String> p_a_value) throws IllegalArgumentException {
		if (p_a_value == null) {
			throw new IllegalArgumentException("Empty e-mail-address list[CC] parameter");
		} else {
			this.a_cc.clear();
			
			for (String s_foo : p_a_value) {
				this.a_cc.add(s_foo);
			}
		}
	}
	
	/**
	 * get bcc
	 * 
	 * @return String
	 */
	public String getBCC() {
		return this.s_bcc;
	}
	
	/**
	 * add bcc
	 * 
	 * @param p_s_value String
	 * @throws IllegalArgumentException invalid value
	 */
	public void addBCC(String p_s_value) throws IllegalArgumentException {
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			if (p_s_value.contains(";")) {
				for (String s_foo : p_s_value.split(";")) {
					if (!this.validateAddress(s_foo)) {
						throw new IllegalArgumentException("Invalid e-mail-address '" + s_foo + "'");
					} else {
						this.a_bcc.add(s_foo);
					}
				}
				
				this.s_bcc += ";" + p_s_value;
			} else if (p_s_value.contains(",")) {
				for (String s_foo : p_s_value.split(",")) {
					if (!this.validateAddress(s_foo)) {
						throw new IllegalArgumentException("Invalid e-mail-address '" + s_foo + "'");
					} else {
						this.a_bcc.add(s_foo);
					}
				}
				
				this.s_bcc += "," + p_s_value;
			} else {
				if (!this.validateAddress(p_s_value)) {
					throw new IllegalArgumentException("Invalid e-mail-address '" + p_s_value + "'");
				} else {
					this.a_bcc.add(p_s_value);
					this.s_bcc = p_s_value;
				}
			}
		}
	}
	
	/**
	 * set bcc
	 * 
	 * @param p_s_value String
	 * @throws IllegalArgumentException invalid value
	 */
	public void setBCC(String p_s_value) throws IllegalArgumentException {
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			this.a_bcc.clear();
			
			if (p_s_value.contains(";")) {
				for (String s_foo : p_s_value.split(";")) {
					if (!this.validateAddress(s_foo)) {
						throw new IllegalArgumentException("Invalid e-mail-address '" + s_foo + "'");
					} else {
						this.a_bcc.add(s_foo);
					}
				}
			} else if (p_s_value.contains(",")) {
				for (String s_foo : p_s_value.split(",")) {
					if (!this.validateAddress(s_foo)) {
						throw new IllegalArgumentException("Invalid e-mail-address '" + s_foo + "'");
					} else {
						this.a_bcc.add(s_foo);
					}
				}
			} else {
				if (!this.validateAddress(p_s_value)) {
					throw new IllegalArgumentException("Invalid e-mail-address '" + p_s_value + "'");
				} else {
					this.a_bcc.add(p_s_value);
				}
			}
			
			this.s_bcc = p_s_value;
		} else {
			this.s_bcc = null;
		}
	}
	
	/**
	 * get bcc list
	 * 
	 * @return java.util.List&lt;String&gt;
	 */
	public java.util.List<String> getBCCList() {
		return this.a_bcc;
	}
	
	/**
	 * set bcc list
	 * 
	 * @param p_a_value java.util.List&lt;String&gt;
	 * @throws IllegalArgumentException parameter is empty or null
	 */
	public void setBCCList(java.util.List<String> p_a_value) throws IllegalArgumentException {
		if (p_a_value == null) {
			throw new IllegalArgumentException("Empty e-mail-address list[BCC] parameter");
		} else {
			this.a_bcc.clear();
			
			for (String s_foo : p_a_value) {
				this.a_bcc.add(s_foo);
			}
		}
	}
	
	/**
	 * get subject
	 * 
	 * @return String
	 */
	public String getSubject() {
		return this.s_subject;
	}
	
	/**
	 * set subject
	 * 
	 * @param p_s_value String
	 * @throws IllegalArgumentException parameter is empty or null
	 */
	public void setSubject(String p_s_value) throws IllegalArgumentException {
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			this.s_subject = p_s_value;
		} else {
			throw new IllegalArgumentException("Empty value for \"Subject\"-field of E-Mail-Message");
		}
	}
	
	/**
	 * get text
	 * 
	 * @return String
	 */
	public String getText() {
		return this.s_text;
	}
	
	/**
	 * set text
	 * 
	 * @param p_s_value String
	 * @throws IllegalArgumentException parameter is empty or null
	 */
	public void setText(String p_s_value) throws IllegalArgumentException {
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			this.s_text = p_s_value;
		} else {
			throw new IllegalArgumentException("Empty value for \"Text\"-field of E-Mail-Message");
		}
	}
	
	/**
	 * get html
	 * 
	 * @return String
	 */
	public String getHtml() {
		return this.s_html;
	}
	
	/**
	 * set html
	 * 
	 * @param p_s_value String
	 */
	public void setHtml(String p_s_value) {
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			this.s_html = p_s_value;
		}
	}
	
	/**
	 * get send
	 * 
	 * @return java.util.Date
	 */
	public java.util.Date getSend() {
		return this.o_send;
	}
	
	/**
	 * set send
	 * 
	 * @param p_o_value java.util.Date
	 */
	public void setSend(java.util.Date p_o_value) {
		this.o_send = p_o_value;
	}
	
	/**
	 * get received
	 * 
	 * @return java.util.Date
	 */
	public java.util.Date getReceived() {
		return this.o_received;
	}
	
	/**
	 * set received
	 * 
	 * @param p_o_value java.util.Date
	 */
	public void setReceived(java.util.Date p_o_value) {
		this.o_received = p_o_value;
	}
	
	/**
	 * get content type
	 * 
	 * @return String
	 */
	public String getContentType() {
		return this.s_contentType;
	}
	
	/**
	 * set content type
	 * 
	 * @param p_s_value String
	 */
	public void setContentType(String p_s_value) {
		this.s_contentType = p_s_value;
	}
	
	/**
	 * get expunged flag
	 * 
	 * @return boolean
	 */
	public boolean getExpunged() {
		return this.b_expunged;
	}
	
	/**
	 * set expunged flag
	 * 
	 * @param p_b_value boolean
	 */
	public void setExpunged(boolean p_b_value) {
		this.b_expunged = p_b_value;
	}
	
	/**
	 * get description
	 * 
	 * @return String
	 */
	public String getDescription() {
		return this.s_description;
	}
	
	/**
	 * set description
	 * 
	 * @param p_s_value String
	 */
	public void setDescription(String p_s_value) {
		this.s_description = p_s_value;
	}
	
	/**
	 * get disposition
	 * 
	 * @return String
	 */
	public String getDisposition() {
		return this.s_disposition;
	}
	
	/**
	 * set disposition
	 * 
	 * @param p_s_value String
	 */
	public void setDisposition(String p_s_value) {
		this.s_disposition = p_s_value;
	}
	
	/**
	 * get size
	 * 
	 * @return int
	 */
	public int getSize() {
		return this.i_size;
	}
	
	/**
	 * set size
	 * 
	 * @param p_i_value int
	 */
	public void setSize(int p_i_value) {
		this.i_size = p_i_value;
	}
	
	/**
	 * has attachments flag
	 * 
	 * @return boolean
	 */
	public boolean hasAttchments() {
		if ( (this.a_attachmentsContent != null) && (this.a_attachmentsContent.size() > 0) ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * get attachments
	 * 
	 * @return java.util.List&lt;String&gt;
	 */
	public java.util.List<String> getAttachments() {
		return this.a_attachments;
	}
	
	/**
	 * set attachments
	 * 
	 * @param p_a_value java.util.List&lt;String&gt;
	 * @throws IllegalArgumentException java.util.List&lt;String&gt; has no entires
	 */
	public void setAttachments(java.util.List<String> p_a_value) throws IllegalArgumentException {
		if (p_a_value != null) {
			if (p_a_value.size() < 1) {
				throw new IllegalArgumentException("Attachment-List has no entries");
			}
			
			this.a_attachments.clear();
			
			for (String s_foo : p_a_value) {
				this.a_attachments.add(s_foo);
			}
		}
	}
	
	/**
	 * add attachment
	 * 
	 * @param p_s_filePath String
	 * @throws IllegalArgumentException parameter is empty or null
	 */
	public void addAttachment(String p_s_filePath) throws IllegalArgumentException {
		if (this.a_attachments == null) {
			this.a_attachments = new java.util.ArrayList<String>();
		}
		
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_filePath)) {
			throw new IllegalArgumentException("Empty value for adding attachment to E-Mail-Message");
		} else {
			this.a_attachments.add(p_s_filePath);
		}
	}
	
	/**
	 * get attachments content
	 * 
	 * @return java.util.List&lt;byte&gt;
	 */
	public java.util.List<byte[]> getAttachmentsContent() {
		return this.a_attachmentsContent;
	}
	
	/**
	 * set attachments content
	 * 
	 * @param p_a_value java.util.List&lt;byte&gt;
	 * @throws IllegalArgumentException java.util.List&lt;byte&gt; has no entries
	 */
	public void setAttachmentsContent(java.util.List<byte[]> p_a_value) throws IllegalArgumentException {
		if (p_a_value != null) {
			if (p_a_value.size() < 1) {
				throw new IllegalArgumentException("AttachmentContent-List has no entries");
			}
			
			this.a_attachmentsContent.clear();
			
			for (byte[] a_foo : p_a_value) {
				if (a_foo.length < 1) {
					throw new IllegalArgumentException("Attachment has no bytes");
				} else {
					this.a_attachmentsContent.add(a_foo);
				}
			}
		}
	}
	
	/**
	 * add attachment content
	 * 
	 * @param p_a_bytes byte[]
	 * @throws IllegalArgumentException parameter is null or empty
	 */
	public void addAttachmentContent(byte[] p_a_bytes) throws IllegalArgumentException {
		if (this.a_attachmentsContent == null) {
			this.a_attachmentsContent = new java.util.ArrayList<byte[]>();
		}
		
		if ( (p_a_bytes == null) || (p_a_bytes.length < 0) ) {
			throw new IllegalArgumentException("Empty value for adding attachment bytes to E-Mail-Message");
		} else {
			this.a_attachmentsContent.add(p_a_bytes);
		}
	}
	
	/**
	 * get flags
	 * 
	 * @return java.util.List&lt;String&gt;
	 */
	public java.util.List<String> getFlags() {
		return this.a_flags;
	}
	
	/**
	 * set flags
	 * 
	 * @param p_a_value java.util.List&lt;String&gt;
	 * @throws IllegalArgumentException java.util.List&lt;String&gt; has no entries
	 */
	public void setFlags(java.util.List<String> p_a_value) throws IllegalArgumentException {
		if (p_a_value != null) {
			if (p_a_value.size() < 1) {
				throw new IllegalArgumentException("Attachment-List has no entries");
			}
			
			this.a_flags.clear();
			
			for (String s_foo : p_a_value) {
				if (!java.util.Arrays.asList(Message.FLAGS).contains(s_foo)) {
					throw new IllegalArgumentException("Flag[" + s_foo + "] is invalid and cannot be attached to E-Mail-Message");
				} else {
					this.a_flags.add(s_foo);
				}
			}
		}
	}
	
	/**
	 * add flag
	 * 
	 * @param p_s_flag String
	 * @throws IllegalArgumentException empty or invalid value
	 */
	public void addFlag(String p_s_flag) throws IllegalArgumentException {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_flag)) {
			throw new IllegalArgumentException("Empty value for adding flag to E-Mail-Message");
		} else {
			if (!java.util.Arrays.asList(Message.FLAGS).contains(p_s_flag)) {
				throw new IllegalArgumentException("Flag[" + p_s_flag + "] is invalid and cannot be attached to E-Mail-Message");
			}
			
			this.a_flags.add(p_s_flag);
		}
	}
	
	/**
	 * get header list
	 * 
	 * @return java.util.List&lt;String&gt;
	 */
	public java.util.List<String> getHeader() {
		return this.a_header;
	}
	
	/**
	 * add header line
	 * 
	 * @param p_s_value String
	 * @throws IllegalArgumentException parameter is null or empty
	 */
	public void addHeaderLine(String p_s_value) throws IllegalArgumentException {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			throw new IllegalArgumentException("Empty value for adding header line to E-Mail-Message");
		} else {
			this.a_header.add(p_s_value);
		}
	}
	
	/**
	 * get message id
	 * 
	 * @return String
	 */
	public String getMessageId() {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_messageId)) {
			return "NO_MESSAGE_ID";
		} else {
			return this.s_messageId;
		}
	}
	
	/**
	 * set message id
	 * 
	 * @param p_s_value String
	 * @throws IllegalArgumentException parameter is null or empty
	 */
	public void setMessageId(String p_s_value) throws IllegalArgumentException {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			throw new IllegalArgumentException("Empty value for message id");
		} else {
			this.s_messageId = p_s_value;
		}
	}
	
	/* Methods */
	
	/**
	 * Create email message object, no source mail address, no cc, no bcc
	 * 
	 * @param p_s_to								destination mail address of message (multiple addresses separated by ';')
	 * @param p_s_subject							subject of message
	 * @param p_s_text								text content of message
	 * @throws IllegalArgumentException				parameters have invalid value or are empty
	 */
	public Message(String p_s_to, String p_s_subject, String p_s_text) throws IllegalArgumentException {
		this(null, p_s_to, null, null, p_s_subject, p_s_text);
	}
	
	/**
	 * Create email message object, no source mail address, no bcc
	 * 
	 * @param p_s_to								destination mail address of message (multiple addresses separated by ';')
	 * @param p_s_cc								cc mail address of message (multiple addresses separated by ';')
	 * @param p_s_subject							subject of message
	 * @param p_s_text								text content of message
	 * @throws IllegalArgumentException				parameters have invalid value or are empty
	 */
	public Message(String p_s_to, String p_s_cc, String p_s_subject, String p_s_text) throws IllegalArgumentException {
		this(null, p_s_to, p_s_cc, null, p_s_subject, p_s_text);
	}
	
	/**
	 * Create email message object, no source mail address
	 * 
	 * @param p_s_to								destination mail address of message (multiple addresses separated by ';')
	 * @param p_s_cc								cc mail address of message (multiple addresses separated by ';')
	 * @param p_s_bcc								bcc mail address of message (multiple addresses separated by ';')
	 * @param p_s_subject							subject of message
	 * @param p_s_text								text content of message
	 * @throws IllegalArgumentException				parameters have invalid value or are empty
	 */
	public Message(String p_s_to, String p_s_cc, String p_s_bcc, String p_s_subject, String p_s_text) throws IllegalArgumentException {
		this(null, p_s_to, p_s_cc, p_s_bcc, p_s_subject, p_s_text);
	}
	
	/**
	 * Create email message object
	 * 
	 * @param p_s_from								source mail address of message (multiple addresses separated by ';')
	 * @param p_s_to								destination mail address of message (multiple addresses separated by ';')
	 * @param p_s_cc								cc mail address of message (multiple addresses separated by ';')
	 * @param p_s_bcc								bcc mail address of message (multiple addresses separated by ';')
	 * @param p_s_subject							subject of message
	 * @param p_s_text								text content of message
	 * @throws IllegalArgumentException				parameters have invalid value or are empty
	 */
	public Message(String p_s_from, String p_s_to, String p_s_cc, String p_s_bcc, String p_s_subject, String p_s_text) throws IllegalArgumentException {
		/* init list variables */
		this.a_from = new java.util.ArrayList<String>();
		this.a_to = new java.util.ArrayList<String>();
		this.a_cc = new java.util.ArrayList<String>();
		this.a_bcc = new java.util.ArrayList<String>();
		this.a_flags = new java.util.ArrayList<String>();
		this.a_header = new java.util.ArrayList<String>();
		
		/* adopt parameter values */
		this.setFrom(p_s_from);
		this.setTo(p_s_to);
		this.setCC(p_s_cc);
		this.setBCC(p_s_bcc);
		this.setSubject(p_s_subject);
		this.setText(p_s_text);
		
		/* set other variables to null or -1 for size */
		this.s_html = null;
		this.o_send = null;
		this.o_received = null;
		this.s_contentType = null;
		
		this.b_expunged = false;
		this.s_description = null;
		this.s_disposition = null;
		this.i_size = -1;
	}

	/**
	 * Checks if a mail address is valid or not
	 * @param p_s_address				mail address value, e.g. test@host.com
	 * @return							true - valid, false - not valid
	 */
	private boolean validateAddress(String p_s_address) {
		try {
			@SuppressWarnings("unused")
			jakarta.mail.internet.InternetAddress o_mailAddress = new jakarta.mail.internet.InternetAddress(p_s_address);
		} catch (jakarta.mail.internet.AddressException o_exc) {
			return false;	
		}
		
		return true;
	}

	/**
	 * Returns a multiline string with all information of the mail message object, no additional and no header information
	 * 
	 * @return String
	 */
	public String toString() {
		return this.toString(false);
	}
	
	/**
	 * Returns a multiline string with all information of the mail message object, no header information
	 * 
	 * @param p_b_extended				return additional information like bcc, content type, size or message id
	 * @return String
	 */
	public String toString(boolean p_b_extended) {
		return this.toString(p_b_extended, false);
	}
	
	/**
	 * Returns a multiline string with all information of the mail message object
	 * 
	 * @param p_b_extended				return additional information like bcc, content type, size or message id
	 * @param p_b_returnHeader			return header information lines
	 * @return String
	 */
	public String toString(boolean p_b_extended, boolean p_b_returnHeader) {
		String s_foo = "";
		s_foo += "From:\t\t" + String.join(", ", this.getFromList());
		s_foo += "\n" + "To:\t\t" + String.join(", ", this.getToList());
		s_foo += "\n" + "CC:\t\t" + String.join(", ", this.getCCList());
		if (p_b_extended) { s_foo += "\n" + "BCC:\t\t" + String.join(", ", this.getBCCList()); }
		s_foo += "\n" + "Subject:\t" + this.getSubject();
		s_foo += "\n" + "Sent:\t\t" + this.getSend();
		if (p_b_extended) { s_foo += "\n" + "Received:\t" + this.getReceived(); }
		if (p_b_extended) { s_foo += "\n" + "Content-Type:\t" + this.getContentType(); }
		if (p_b_extended) { s_foo += "\n" + "Expunged:\t" + this.getExpunged(); }
		if (p_b_extended) { s_foo += "\n" + "Description:\t" + this.getDescription(); }
		if (p_b_extended) { s_foo += "\n" + "Disposition:\t" + this.getDisposition(); }
		if (p_b_extended) { s_foo += "\n" + "Size:\t\t" + this.getSize(); }
		s_foo += "\n" + "Flags:\t\t" + String.join(", ", this.a_flags);
		if (p_b_extended) { s_foo += "\n" + "Message-ID:\t" + this.getMessageId(); }
		
		/* return header information lines if available */
		if ( (p_b_returnHeader) && (this.a_header.size() > 0) ) {
			s_foo += "\n" + "Header:";
			
			for (String s_foo2 : this.a_header) {
				s_foo += "\n" + "\t\t\t" + s_foo2;
			}
		}
		
		/* if we return additional information, return plain text message as well if html message is available */
		if ( (p_b_extended) && (!net.forestany.forestj.lib.Helper.isStringEmpty(this.getHtml())) ) {
			s_foo += "\n" + "+++++++++++++++++++";
			s_foo += "\n" + "+++++ Message +++++";
			s_foo += "\n" + "+++++++++++++++++++";
			s_foo += "\n" + this.getText();
		}
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.getHtml())) { /* return html message if available */
			s_foo += "\n" + "+++++++++++++++++++";
			s_foo += "\n" + "++ HTML Message  ++";
			s_foo += "\n" + "+++++++++++++++++++";
			s_foo += "\n" + this.getHtml();
		} else { /* otherwise only return plain text message */
			s_foo += "\n" + "+++++++++++++++++++";
			s_foo += "\n" + "+++++ Message +++++";
			s_foo += "\n" + "+++++++++++++++++++";
			s_foo += "\n" + this.getText();
		}
		
		/* if attachments are available, return attachment file names only */
		if ( (this.getAttachments() != null) && (this.getAttachments().size() > 0) ) {
			s_foo += "\n";
			s_foo += "\n" + "+++++++++++++++++++";
			s_foo += "\n" + "+++ Attachments +++";
			s_foo += "\n" + "+++++++++++++++++++";
			
			s_foo += "\n\n" + "Message-ID:\t" + this.getMessageId() + "\n";
			
			int i = 1;
			
			for (String s_attachment : this.getAttachments()) {
				String s_filename = s_attachment.split(Message.FIlENAMESEPARATOR)[0];
				String s_contentType = s_attachment.split(Message.FIlENAMESEPARATOR)[1];
				s_foo += "\n#" + i++ + "\t" + s_filename + "(" + s_contentType + ")";
			}
		}
		
		return s_foo;
	}

	/**
	 * Store an attachment to local system, overwrite existing file
	 * 
	 * @param p_i_number					number of attachment within mail message
	 * @param p_s_path						path to local system with file name
	 * @throws IllegalArgumentException		invalid number parameter
	 * @throws java.io.IOException			invalid path to local system, does not exist, cannot delete existing file or issue writing file content to local system
	 * @throws NullPointerException			mail message has no attachments
	 */
	public void saveAttachment(int p_i_number, String p_s_path) throws IllegalArgumentException, java.io.IOException, NullPointerException {
		this.saveAttachment(p_i_number, p_s_path, true);
	}
	
	/**
	 * Store an attachment to local system
	 * 
	 * @param p_i_number					number of attachment within mail message
	 * @param p_s_path						path to local system with file name
	 * @param p_b_overwrite					true - overwrite existing file, false - do not change anything on local system
	 * @throws IllegalArgumentException		invalid number parameter
	 * @throws java.io.IOException			invalid path to local system, does not exist, cannot delete existing file or issue writing file content to local system
	 * @throws NullPointerException			mail message has no attachments
	 */
	public void saveAttachment(int p_i_number, String p_s_path, boolean p_b_overwrite) throws IllegalArgumentException, java.io.IOException, NullPointerException {
		/* check number parameter */
		if (p_i_number < 1) {
			throw new IllegalArgumentException("Number parameter must be at least '1'");
		}
		
		if ( (this.getAttachmentsContent() != null) && (this.getAttachmentsContent().size() > 0) ) {
			/* check number parameter range */
			if (p_i_number > this.getAttachments().size()) {
				throw new IllegalArgumentException("Number parameter out of range, only '" + this.getAttachments().size() + "' attachments available");
			}
			
			/* check if path to local system really exists */
			if (net.forestany.forestj.lib.io.File.isDirectory(p_s_path)) {
				if (!p_s_path.endsWith(net.forestany.forestj.lib.io.File.DIR)) {
					p_s_path += net.forestany.forestj.lib.io.File.DIR;
				}
				
				/* add attachment file name to path */
				p_s_path += this.getAttachments().get(p_i_number - 1).split(Message.FIlENAMESEPARATOR)[0];
			}
			
			/* check if file already exists */
			boolean b_exists = net.forestany.forestj.lib.io.File.exists(p_s_path);
			
			if ( (b_exists) && (p_b_overwrite) ) { /* delete file on local system */
				net.forestany.forestj.lib.io.File.deleteFile(p_s_path);
				b_exists = false;
			}
			
			/* write file content to local system */
			if (!b_exists) {
				try (java.io.FileOutputStream o_fileOutputStream = new java.io.FileOutputStream(p_s_path)) {
					o_fileOutputStream.write(this.getAttachmentsContent().get(p_i_number - 1));
				}
			}
		} else {
			throw new NullPointerException("E-Mail-Message has no attachments");
		}
	}

	/**
	 * Store all attachment to local system, overwrite existing files with the same name
	 * 
	 * @param p_s_path						path to local system with file name
	 * @throws IllegalArgumentException		invalid path to local system
	 * @throws java.io.IOException			cannot delete existing file or issue writing file content to local system
	 * @throws NullPointerException			mail message has no attachments
	 */
	public void saveAllAttachments(String p_s_path) throws IllegalArgumentException, java.io.IOException, NullPointerException {
		this.saveAllAttachments(p_s_path, true);
	}
	
	/**
	 * Store all attachment to local system
	 * 
	 * @param p_s_path						path to local system with file name
	 * @param p_b_overwrite					true - overwrite existing file, false - do not change anything on local system
	 * @throws IllegalArgumentException		invalid path to local system
	 * @throws java.io.IOException			cannot delete existing file or issue writing file content to local system
	 * @throws NullPointerException			mail message has no attachments
	 */
	public void saveAllAttachments(String p_s_path, boolean p_b_overwrite) throws IllegalArgumentException, java.io.IOException, NullPointerException {
		/* check if path to local system really exists */
		if (!net.forestany.forestj.lib.io.File.isDirectory(p_s_path)) {
			throw new IllegalArgumentException("Path[" + p_s_path + "] is not a valid path");
		}
		
		if ( (this.getAttachmentsContent() != null) && (this.getAttachmentsContent().size() > 0) ) {
			for (int i = 0; i < this.getAttachments().size(); i++) {
				this.saveAttachment(i + 1, p_s_path, p_b_overwrite);
			}
		}
	}

	/**
	 * Check if mail message has active flag.
	 * ANSWERED, DELETED, DRAFT, FLAGGED, RECENT, SEEN or USER.
	 * 
	 * @param p_s_flag						flag string value
	 * @return								true - flag is set, false - flag is not set
	 * @throws IllegalArgumentException		flag parameter is null or empty or flag parameter value does not correspond to specified values
	 */
	public boolean hasFlag(String p_s_flag) throws IllegalArgumentException {
		/* check if parameter is null or empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_flag)) {
			throw new IllegalArgumentException("Empty parameter for checking flag of E-Mail-Message.");
		} else {
			/* change all letters to upper case */
			p_s_flag = p_s_flag.toUpperCase();
			
			/* check if parameter value corresponds to specified values  */
			if (!java.util.Arrays.asList(Message.FLAGS).contains(p_s_flag)) {
				throw new IllegalArgumentException("Flag[" + p_s_flag + "] is invalid.");
			}
			
			/* check if parameter value is in mail message flag list */
			if (this.a_flags.contains(p_s_flag)) {
				return true;
			}
		}
		
		/* parameter value is not in mail message flag list */
		return false;
	}
}
