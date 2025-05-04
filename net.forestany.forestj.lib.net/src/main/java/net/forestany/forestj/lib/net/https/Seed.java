package net.forestany.forestj.lib.net.https;

/**
 * Seed class is the core of forestJ tiny server application, which holds all necessary data about request header, response header, body data, post data, post file data, session data, central configuration settings, temporary object list for dynamic handling of all requests.
 */
public class Seed {
	
	/* Fields */
	
	private Config o_config;
	private RequestHeader o_requestHeader;
	private ResponseHeader o_responseHeader;
	private byte[] a_requestBody;
	private byte[] a_responseBody;
	private int i_returnCode;
	
	private java.util.Map<String, String> a_postData;
	private java.util.List<net.forestany.forestj.lib.net.https.dynm.FileData> a_fileData;
	private java.util.Map<String, String> a_sessionData;
	
	private StringBuilder o_buffer;
	private java.util.Map<String, Object> a_temp;
	
	private String s_salt;
	
	/* Properties */
	
	/**
	 * get config instance
	 * 
	 * @return Config
	 */
	public Config getConfig() {
		return this.o_config;
	}
	
	/**
	 * set config
	 * 
	 * @param p_o_value					configuration instance parameter
	 * @throws NullPointerException		configuration instance parameter is null
	 */
	public void setConfig(Config p_o_value) throws NullPointerException {
		if (p_o_value == null) {
			throw new NullPointerException("Config parameter is null");
		}
		
		this.o_config = p_o_value;
	}
	
	/**
	 * get salt
	 * 
	 * @return String
	 */
	public String getSalt() {
		return this.s_salt;
	}
	
	/**
	 * set salt
	 * 
	 * @param p_s_value						salt value for log purpose
	 * @throws NullPointerException			salt parameter value is null or empty
	 */
	public void setSalt(String p_s_value) throws NullPointerException {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			throw new NullPointerException("Salt parameter is null or empty");
		}
		
		this.s_salt = p_s_value;
	}
		
	/**
	 * get request header
	 * 
	 * @return RequestHeader
	 */
	public RequestHeader getRequestHeader() {
		return this.o_requestHeader;
	}

	/**
	 * get response header
	 * 
	 * @return ResponseHeader
	 */
	public ResponseHeader getResponseHeader() {
		return this.o_responseHeader;
	}
	
	/**
	 * get request body
	 * 
	 * @return byte[]
	 */
	public byte[] getRequestBody() {
		return this.a_requestBody;
	}
	
	/**
	 * set request body
	 * 
	 * @param p_a_value byte[]
	 */
	public void setRequestBody(byte[] p_a_value) {
		this.a_requestBody = p_a_value;
	}
	
	/**
	 * get response body
	 * 
	 * @return byte[]
	 */
	public byte[] getResponseBody() {
		return this.a_responseBody;
	}
	
	/**
	 * set response body
	 * 
	 * @param p_a_value byte[]
	 */
	public void setResponseBody(byte[] p_a_value) {
		this.a_responseBody = p_a_value;
	}
	
	/**
	 * get return code
	 * 
	 * @return int
	 */
	public int getReturnCode() {
		return this.i_returnCode;
	}
	
	/**
	 * set return code
	 * 
	 * @param p_i_value int
	 */
	public void setReturnCode(int p_i_value) {
		this.i_returnCode = p_i_value;
		this.o_responseHeader.setReturnCode(this.i_returnCode);
	}
	
	/**
	 * get post data
	 * 
	 * @return java.util.Map&lt;String, String&gt;
	 */
	public java.util.Map<String, String> getPostData() {
		return this.a_postData;
	}
	
	/**
	 * get file data
	 * 
	 * @return java.util.List&lt;net.forestany.forestj.lib.net.https.dynm.FileData&gt;
	 */
	public java.util.List<net.forestany.forestj.lib.net.https.dynm.FileData> getFileData() {
		return this.a_fileData;
	}
	
	/**
	 * get session data
	 * 
	 * @return java.util.Map&lt;String, String&gt;
	 */
	public java.util.Map<String, String> getSessionData() {
		return this.a_sessionData;
	}
	
	/**
	 * get buffer
	 * 
	 * @return StringBuilder
	 */
	public StringBuilder getBuffer() {
		return this.o_buffer;
	}
	
	/**
	 * get temp
	 * 
	 * @return java.util.Map&lt;String, Object&gt;
	 */
	public java.util.Map<String, Object> getTemp() {
		return this.a_temp;
	}
	
	/* Methods */
	
	/**
	 * Seed constructor, initializing all settings, request header instance, response header instance and lists
	 * 
	 * @param p_o_config				configuration instance parameter
	 * @throws NullPointerException		configuration instance parameter is null, or domain parameter from configuration is null or empty
	 */
	public Seed(Config p_o_config) throws NullPointerException {
		this.setConfig(p_o_config);
		this.o_requestHeader = new RequestHeader(this.o_config.getDomain());
		this.o_responseHeader = new ResponseHeader();
		this.a_requestBody = null;
		this.a_responseBody = null;
		this.i_returnCode = -1;
		
		this.a_postData = new java.util.HashMap<String, String>();
		this.a_fileData = new java.util.ArrayList<net.forestany.forestj.lib.net.https.dynm.FileData>();
		this.a_sessionData = new java.util.HashMap<String, String>();
		
		this.o_buffer = new StringBuilder();
		this.a_temp = new java.util.HashMap<String, Object>();
		
		this.s_salt = null;
	}
}
