package net.forestany.forestj.lib.net.http;

/**
 * Generating a http/https request to a webserver, supporting http(s) request types GET, POST and DOWNLOAD.
 */
public class Request {

	/* Delegates */
	
	/**
	 * interface delegate definition which can be instanced outside of Request class to post progress anywhere when a request download is running
	 */
	public interface IDelegate {
		/**
		 * progress method for web request
		 * 
		 * @param p_d_progress download progress
		 * @param p_s_filename filename where content will be saved
		 */
		void PostProgress(double p_d_progress, String p_s_filename);
	}
	
	/* Fields */
	
	private RequestType e_type;
	private String s_address;
	private java.nio.charset.Charset o_charset;
	private String s_proxyAddress;
	private int i_proxyPort;
	private PostType e_contentType;
	private String s_authenticationUser;
	private String s_authenticationPassword;
	private java.util.Map<String,Object> a_requestParamters;
	private String s_lineBreak;
	private java.util.Map<String,String> a_attachments;
	private String s_downloadFilename;
	private boolean b_downloadFileExtensionNeeded;
	private boolean b_overwriteDownload;
	private int i_responseCode;
	private String s_responseMessage;
	private boolean b_useLog;
	
	/**
	 * allowed parameter types, constant list
	 */
	public static final java.util.List<String> a_allowedParameterTypes = java.util.Arrays.asList("byte", "short", "int", "long", "float", "double", "char", "boolean", "string", "java.lang.String", "java.lang.Short", "java.lang.Byte", "java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double", "java.lang.Boolean", "java.lang.Character");
	
	private IDelegate itf_delegate;
	
	/* Properties */
	
	/**
	 * get type
	 * 
	 * @return RequestType
	 */
	public RequestType getType() {
		return this.e_type;
	}
	
	/**
	 * set type
	 * 
	 * @param p_e_value RequestType
	 */
	private void setType(RequestType p_e_value) {
		this.e_type = p_e_value;
	}
	
	/**
	 * get address
	 * 
	 * @return String
	 */
	public String getAddress() {
		return this.s_address;
	}
	
	/**
	 * set address
	 * 
	 * @param p_s_value String
	 */
	public void setAddress(String p_s_value) {
		this.s_address = p_s_value;
	}
	
	/**
	 * get charset
	 * 
	 * @return java.nio.charset.Charset
	 */
	public java.nio.charset.Charset getCharset() {
		return this.o_charset;
	}
	
	/**
	 * set charset 
	 * 
	 * @param p_o_value java.nio.charset.Charset
	 */
	public void setCharset(java.nio.charset.Charset p_o_value) {
		this.o_charset = p_o_value;
	}
	
	/**
	 * get proxy address
	 * 
	 * @return String
	 */
	public String getProxyAdress() {
		return this.s_proxyAddress;
	}
	
	/**
	 * set proxy address
	 * 
	 * @param p_s_value String
	 */
	public void setProxyAdress(String p_s_value) {
		this.s_proxyAddress = p_s_value;
	}
	
	/**
	 * get proxy port
	 * 
	 * @return int
	 */
	public int getProxyPort() {
		return this.i_proxyPort;
	}
	
	/**
	 * set proxy port
	 * 
	 * @param p_i_value int
	 */
	public void setProxyPort(int p_i_value) {
		this.i_proxyPort = p_i_value;
	}
	
	/**
	 * get content type
	 * 
	 * @return PostType
	 */
	public PostType getContentType() {
		return this.e_contentType;
	}
	
	/**
	 * set content type
	 * 
	 * @param p_e_value PostType
	 */
	public void setContentType(PostType p_e_value) {
		this.e_contentType = p_e_value;
	}
	
	/**
	 * get authentication user
	 * 
	 * @return String
	 */
	public String getAuthenticationUser() {
		return this.s_authenticationUser;
	}
	
	/**
	 * set authentication user
	 * 
	 * @param p_s_value String
	 */
	public void setAuthenticationUser(String p_s_value) {
		this.s_authenticationUser = p_s_value;
	}
	
	/**
	 * get authentication password
	 * 
	 * @return String
	 */
	public String getAuthenticationPassword() {
		return this.s_authenticationPassword;
	}
	
	/**
	 * set authentication password
	 * 
	 * @param p_s_value String
	 */
	public void setAuthenticationPassword(String p_s_value) {
		this.s_authenticationPassword = p_s_value;
	}
	
	/**
	 * get request parameters
	 * 
	 * @return java.util.Map&lt;String,Object&gt;
	 */
	public java.util.Map<String,Object> getRequestParameters() {
		return this.a_requestParamters;
	}
	
	/**
	 * get download filename
	 * 
	 * @return String
	 */
	public String getDownloadFilename() {
		return this.s_downloadFilename;
	}
	
	/**
	 * set download filename
	 * 
	 * @param p_s_value String
	 */
	public void setDownloadFilename(String p_s_value) {
		this.s_downloadFilename = p_s_value;
	}
	
	/**
	 * get download file-extension needed flag
	 * 
	 * @return boolean
	 */
	public boolean getDownloadFileExtensionNeeded() {
		return this.b_downloadFileExtensionNeeded;
	}
	
	/**
	 * set download file-extension needed flag
	 * 
	 * @param p_b_value boolean
	 */
	public void setDownloadFileExtensionNeeded(boolean p_b_value) {
		this.b_downloadFileExtensionNeeded = p_b_value;
	}
	
	/**
	 * get overwrite download flag
	 * 
	 * @return boolean
	 */
	public boolean getOverwriteDownload() {
		return this.b_overwriteDownload;
	}
	
	/**
	 * set overwrite download flag
	 * 
	 * @param p_b_value boolean
	 */
	public void setOverwriteDownload(boolean p_b_value) {
		this.b_overwriteDownload = p_b_value;
	}
	
	/**
	 * get line break
	 * 
	 * @return String
	 */
	public String getLineBreak() {
		return this.s_lineBreak;
	}
	
	/**
	 * set line break
	 * 
	 * @param p_s_lineBreak String
	 */
	public void setLineBreak(String p_s_lineBreak) {
		this.s_lineBreak = p_s_lineBreak;
	}
	
	/**
	 * get response code
	 * 
	 * @return int
	 */
	public int getResponseCode() {
		return this.i_responseCode;
	}
	
	/**
	 * get response message
	 * 
	 * @return String
	 */
	public String getResponseMessage() {
		return this.s_responseMessage;
	}
	
	/**
	 * get use log flag
	 * 
	 * @return boolean
	 */
	public boolean getUseLog() {
		return this.b_useLog;
	}
	
	/**
	 * set use log flag
	 * 
	 * @param p_b_value boolean
	 */
	public void setUseLog(boolean p_b_value) {
		this.b_useLog = p_b_value;
	}
	
	/**
	 * set delegate
	 * 
	 * @param p_itf_delegate delegate instance
	 */
	public void setDelegate(IDelegate p_itf_delegate) {
		this.itf_delegate = p_itf_delegate;
	}
	
	/* Methods */
	
	/**
	 * Create http(s) request object with all necessary information before executing the request to a webserver, post transfer encoding will be BIT_7, line break from net.forestany.forestj.lib.io.File.NEWLINE.
	 * standard charset from net.forestany.forestj.lib.io.File.CHARSET.
	 * no post content type, but needed for POST request.
	 * no proxy
	 * 
	 * @param p_e_type				type of web request: GET, POST or DOWNLOAD
	 * @param p_s_address			address of https web server
	 */
	public Request(RequestType p_e_type, String p_s_address) {
		this(p_e_type, p_s_address, java.nio.charset.Charset.forName(net.forestany.forestj.lib.io.File.CHARSET));
	}
	
	/**
	 * Create http(s) request object with all necessary information before executing the request to a webserver, post transfer encoding will be BIT_7, line break from net.forestany.forestj.lib.io.File.NEWLINE.
	 * no post content type, but needed for POST request.
	 * no proxy.
	 * 
	 * @param p_e_type				type of web request: GET, POST or DOWNLOAD
	 * @param p_s_address			address of https web server
	 * @param p_o_charset			charset which will be used sending and receiving request data
	 */
	public Request(RequestType p_e_type, String p_s_address, java.nio.charset.Charset p_o_charset) {
		this(p_e_type, p_s_address, p_o_charset, null);
	}
	
	/**
	 * Create http(s) request object with all necessary information before executing the request to a webserver, post transfer encoding will be BIT_7, line break from net.forestany.forestj.lib.io.File.NEWLINE.
	 * no proxy.
	 * 
	 * @param p_e_type				type of web request: GET, POST or DOWNLOAD
	 * @param p_s_address			address of https web server
	 * @param p_o_charset			charset which will be used sending and receiving request data
	 * @param p_e_contentType		specify post content type: HTMLATTACHMENTS, HTML or JSON
	 */
	public Request(RequestType p_e_type, String p_s_address, java.nio.charset.Charset p_o_charset, PostType p_e_contentType) {
		this(p_e_type, p_s_address, p_o_charset, p_e_contentType, "");
	}
	
	/**
	 * Create http(s) request object with all necessary information before executing the request to a webserver, post transfer encoding will be BIT_7, line break from net.forestany.forestj.lib.io.File.NEWLINE.
	 * standard charset from net.forestany.forestj.lib.io.File.CHARSET.
	 * no proxy.
	 * 
	 * @param p_e_type				type of web request: GET, POST or DOWNLOAD
	 * @param p_s_address			address of https web server
	 * @param p_e_contentType		specify post content type: HTMLATTACHMENTS, HTML or JSON
	 */
	public Request(RequestType p_e_type, String p_s_address, PostType p_e_contentType) {
		this(p_e_type, p_s_address, java.nio.charset.Charset.forName(net.forestany.forestj.lib.io.File.CHARSET), p_e_contentType, "");
	}
	
	/**
	 * Create http(s) request object with all necessary information before executing the request to a webserver, post transfer encoding will be BIT_7, line break from net.forestany.forestj.lib.io.File.NEWLINE.
	 * standard charset from net.forestany.forestj.lib.io.File.CHARSET.
	 * no post content type, but needed for POST request.
	 * proxy port: 80
	 * 
	 * @param p_e_type				type of web request: GET, POST or DOWNLOAD
	 * @param p_s_address			address of https web server
	 * @param p_s_proxyAddress		specify a proxy address which will be used for the web request
	 */
	public Request(RequestType p_e_type, String p_s_address, String p_s_proxyAddress) {
		this(p_e_type, p_s_address, java.nio.charset.Charset.forName(net.forestany.forestj.lib.io.File.CHARSET), null, p_s_proxyAddress);
	}
	
	/**
	 * Create http(s) request object with all necessary information before executing the request to a webserver, post transfer encoding will be BIT_7, line break from net.forestany.forestj.lib.io.File.NEWLINE.
	 * standard charset from net.forestany.forestj.lib.io.File.CHARSET.
	 * proxy port: 80.
	 * 
	 * @param p_e_type				type of web request: GET, POST or DOWNLOAD
	 * @param p_s_address			address of https web server
	 * @param p_e_contentType		specify post content type: HTMLATTACHMENTS, HTML or JSON
	 * @param p_s_proxyAddress		specify a proxy address which will be used for the web request
	 */
	public Request(RequestType p_e_type, String p_s_address, PostType p_e_contentType, String p_s_proxyAddress) {
		this(p_e_type, p_s_address, java.nio.charset.Charset.forName(net.forestany.forestj.lib.io.File.CHARSET), p_e_contentType, p_s_proxyAddress, 80);
	}
	
	/**
	 * Create http(s) request object with all necessary information before executing the request to a webserver, post transfer encoding will be BIT_7, line break from net.forestany.forestj.lib.io.File.NEWLINE.
	 * standard charset from net.forestany.forestj.lib.io.File.CHARSET.
	 * no post content type, but needed for POST request.
	 * 
	 * @param p_e_type				type of web request: GET, POST or DOWNLOAD
	 * @param p_s_address			address of https web server
	 * @param p_s_proxyAddress		specify a proxy address which will be used for the web request
	 * @param p_i_proxyPort			specify a proxy port which will be used for the web request
	 */
	public Request(RequestType p_e_type, String p_s_address, String p_s_proxyAddress, int p_i_proxyPort) {
		this(p_e_type, p_s_address, java.nio.charset.Charset.forName(net.forestany.forestj.lib.io.File.CHARSET), null, p_s_proxyAddress, p_i_proxyPort);
	}
	
	/**
	 * Create http(s) request object with all necessary information before executing the request to a webserver, post transfer encoding will be BIT_7, line break from net.forestany.forestj.lib.io.File.NEWLINE.
	 * standard charset from net.forestany.forestj.lib.io.File.CHARSET.
	 * 
	 * @param p_e_type				type of web request: GET, POST or DOWNLOAD
	 * @param p_s_address			address of https web server
	 * @param p_e_contentType		specify post content type: HTMLATTACHMENTS, HTML or JSON
	 * @param p_s_proxyAddress		specify a proxy address which will be used for the web request
	 * @param p_i_proxyPort			specify a proxy port which will be used for the web request
	 */
	public Request(RequestType p_e_type, String p_s_address, PostType p_e_contentType, String p_s_proxyAddress, int p_i_proxyPort) {
		this(p_e_type, p_s_address, java.nio.charset.Charset.forName(net.forestany.forestj.lib.io.File.CHARSET), p_e_contentType, p_s_proxyAddress, p_i_proxyPort);
	}
	
	/**
	 * Create http(s) request object with all necessary information before executing the request to a webserver, post transfer encoding will be BIT_7, line break from net.forestany.forestj.lib.io.File.NEWLINE.
	 * proxy port: 80.
	 * 
	 * @param p_e_type				type of web request: GET, POST or DOWNLOAD
	 * @param p_s_address			address of https web server
	 * @param p_o_charset			charset which will be used sending and receiving request data
	 * @param p_e_contentType		specify post content type: HTMLATTACHMENTS, HTML or JSON
	 * @param p_s_proxyAddress		specify a proxy address which will be used for the web request
	 */
	public Request(RequestType p_e_type, String p_s_address, java.nio.charset.Charset p_o_charset, PostType p_e_contentType, String p_s_proxyAddress) {
		this(p_e_type, p_s_address, p_o_charset, p_e_contentType, p_s_proxyAddress, 80);
	}
	
	/**
	 * Create http(s) request object with all necessary information before executing the request to a webserver, post transfer encoding will be BIT_7, line break from net.forestany.forestj.lib.io.File.NEWLINE
	 * 
	 * @param p_e_type				type of web request: GET, POST or DOWNLOAD
	 * @param p_s_address			address of https web server
	 * @param p_o_charset			charset which will be used sending and receiving request data
	 * @param p_e_contentType		specify post content type: HTMLATTACHMENTS, HTML or JSON
	 * @param p_s_proxyAddress		specify a proxy address which will be used for the web request
	 * @param p_i_proxyPort			specify a proxy port which will be used for the web request
	 */
	public Request(RequestType p_e_type, String p_s_address, java.nio.charset.Charset p_o_charset, PostType p_e_contentType, String p_s_proxyAddress, int p_i_proxyPort) {
		this.setType(p_e_type);
		this.setAddress(p_s_address);
		this.setCharset(p_o_charset);
		this.setContentType(p_e_contentType);
		this.setProxyAdress(p_s_proxyAddress);
		this.setProxyPort(p_i_proxyPort);
		this.setAuthenticationUser("");
		this.setAuthenticationPassword("");
		this.a_requestParamters = new java.util.LinkedHashMap<String,Object>();
		this.s_lineBreak = net.forestany.forestj.lib.io.File.NEWLINE;
		this.b_overwriteDownload = true;
		this.a_attachments = new java.util.LinkedHashMap<String,String>();
		this.b_useLog = false;
		this.itf_delegate = null;
	}
	
	/**
	 * adds request parameter to web request, if parameter type is an allowed type
	 * 
	 * @param p_s_parameterName					type of request parameter, e.g. string, boolean, or double
	 * @param p_o_parameterValue				value of request parameter
	 * @throws IllegalArgumentException			parameter type is not an allowed type
	 */
	public void addRequestParameter(String p_s_parameterName, Object p_o_parameterValue) throws IllegalArgumentException {
		if (!a_allowedParameterTypes.contains(p_o_parameterValue.getClass().getTypeName())) {
			throw new IllegalArgumentException("Parameter[" + p_s_parameterName + "] with type[" + p_o_parameterValue.getClass().getTypeName() + "] is not an allowed parameter type: " + a_allowedParameterTypes.toString());
		}
		
		this.a_requestParamters.put(p_s_parameterName, p_o_parameterValue);
		
												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("added request parameter: '" + p_s_parameterName + "' = '" + p_o_parameterValue + "'");
	}
	
	/**
	 * adds file path to web request which will be attached for web request execution
	 * 
	 * @param p_s_parameterName			attachment name
	 * @param p_s_filePath				attachment file path
	 */
	public void addAttachement(String p_s_parameterName, String p_s_filePath) {
		this.a_attachments.put(p_s_parameterName, p_s_filePath);
		
												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("added attachment to request: '" + p_s_parameterName + "' = '" + p_s_filePath + "'");
	}
	
	/**
	 * execute web request and get response from web server as string message return value.
	 * get additional response code and message which will be stored in class properties.
	 * downloading a file will be combined with DownloadFilename property.
	 * 
	 * @return String
	 * @throws java.io.UnsupportedEncodingException 			could not encode request parameter
	 * @throws java.net.MalformedURLException 					invalid server address format
	 * @throws java.io.IOException								could not establish connection or folder of download filename location does not exist
	 * @throws IllegalArgumentException							no download filename location specified, no file extension found, or no post content type specified for post web request
	 */
	public String executeWebRequest() throws java.io.UnsupportedEncodingException, java.net.MalformedURLException, java.io.IOException {
		/* response variable */
		String s_response = "";
		
		/* check if we have any request parameters and web request type is 'GET' or 'DOWNLOAD' */
        if ( (this.a_requestParamters.size() > 0) && ( (this.e_type == RequestType.GET) || (this.e_type == RequestType.DOWNLOAD) ) ) {
        											if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("gather request parameters with request type is 'GET' or 'DOWNLOAD'");
        	
        	/* string variable for request parameters */
        	String s_requestParameters = "";
    		
        	/* iterate all request parameters with key->value pairs */
            for (java.util.Map.Entry<String,Object> m_requestParameter : this.a_requestParamters.entrySet()) {
                /* add key->value pair request parameter url encoded to variable */
            	s_requestParameters += java.net.URLEncoder.encode(m_requestParameter.getKey(), this.o_charset.displayName()) + "=" + java.net.URLEncoder.encode(String.valueOf(m_requestParameter.getValue()), this.o_charset.displayName()) + "&";
            }
            
            /* if request parameters ends with '&' */
            if (s_requestParameters.endsWith("&")) {
            	/* remove last '&' character from request parameters */
            	s_requestParameters = s_requestParameters.substring(0, s_requestParameters.length() - 1);
            }
        	
            										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("request parameters: '" + s_requestParameters + "'");
            
            /* add request parameters for type 'GET' or 'DOWNLOAD' to web request address */
        	this.s_address += "?" + s_requestParameters;
        }
        
        /* variable for proxy settings */
		java.net.Proxy o_proxy = null;
		
		/* if proxy address was set */
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_proxyAddress)) {
													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("create proxy object with address and port: '" + this.s_proxyAddress + ":" + this.i_proxyPort + "'");
			
			/* create proxy object with address and port */
			o_proxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, new java.net.InetSocketAddress(this.s_proxyAddress, this.i_proxyPort));
		}
		
												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("create new url object with web request address: '" + this.s_address + "'");
		
		/* create new url object with web request address */
		java.net.URL o_url = java.net.URI.create(this.s_address).toURL();
		
		javax.net.ssl.HttpsURLConnection o_httpsUrlConnection = null;
		
		/* cast url object to https url connection with proxy settings(optional) */
		if (o_proxy != null) {
													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("cast url object to https url connection with proxy");
			
			o_httpsUrlConnection = (javax.net.ssl.HttpsURLConnection)o_url.openConnection(o_proxy);
		} else {
													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("cast url object to https url connection; no proxy");
			
			o_httpsUrlConnection = (javax.net.ssl.HttpsURLConnection)o_url.openConnection();
		}
		
		/* if authentication user and password were set */
		if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_authenticationUser)) && (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_authenticationPassword)) ) {
			/* create base64 authentication string and add it to web request properties */
			String s_authentication = this.s_authenticationUser + ":" + this.s_authenticationPassword;
			String s_authenticationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString(s_authentication.getBytes());
			o_httpsUrlConnection.setRequestProperty("Authorization", s_authenticationHeaderValue);
			
													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("created base64 authentication string and added it to web request properties");
		}
		
		if (this.e_type == RequestType.GET) { /* web request type 'GET' */
													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("set request type to 'GET'");
			
			/* set request type to 'GET' */
        	o_httpsUrlConnection.setRequestMethod("GET");
		} else if (this.e_type == RequestType.DOWNLOAD) { /* web request type 'DOWNLOAD' */
													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("set request type to 'GET' for download execution");
			
			/* set request type to 'GET' for download execution */
        	o_httpsUrlConnection.setRequestMethod("GET");
        	
        	/* check if download filename location is set */
        	if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_downloadFilename)) {
        		throw new IllegalArgumentException("Please specify a download filename location");
        	}
        	
        	if (!net.forestany.forestj.lib.io.File.folderExists(this.s_downloadFilename)) {
        		throw new java.io.IOException("Folder of download filename location[" + this.s_downloadFilename + "] does not exist. Please create the download folder location before execution");
        	}
        	
        	if (this.b_downloadFileExtensionNeeded) {
        		if (!net.forestany.forestj.lib.io.File.hasFileExtension(this.s_downloadFilename)) {
            		throw new IllegalArgumentException("Download filename location[" + this.s_downloadFilename + "] must have a valid file extension");
            	}	
        	}
        	
        	if (net.forestany.forestj.lib.io.File.exists(this.s_downloadFilename)) {
        	    if (!this.b_overwriteDownload) {
        	        throw new IllegalArgumentException("File[" + this.s_downloadFilename + "] already exists and will not be overwritten");
        	    } else {
        	    											if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("delete file '" + this.s_downloadFilename + "'");

        	    	net.forestany.forestj.lib.io.File.deleteFile(this.s_downloadFilename);
        	    }
        	}
        } else if (this.e_type == RequestType.POST) { /* web request type 'POST' */
        	/* random value for https file upload protocol */
        	String s_boundary = "------------" + Long.toHexString(System.currentTimeMillis());
        	
        											if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("created random value(boundary) for http file upload protocol: '" + s_boundary + "'");
        	
        											if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("set request type to 'POST'");
        	
        	/* set request type to 'POST' and other request properties */
        	o_httpsUrlConnection.setRequestMethod("POST");
        	o_httpsUrlConnection.setDoInput(true);
        	o_httpsUrlConnection.setDoOutput(true);
            o_httpsUrlConnection.setUseCaches(false);
            o_httpsUrlConnection.setRequestProperty("Cache-Control", "no-cache");
            
            /* check if content type is not null, necessary for type 'POST' */
            if (this.e_contentType == null) {
            	throw new IllegalArgumentException("No content type for POST web request specified");
            }
            
            /* check if we have any attachments for web request 'POST' */
            if (this.a_attachments.size() > 0) {
            	/* set content type with random boundary */
        		o_httpsUrlConnection.setRequestProperty("Content-Type", net.forestany.forestj.lib.net.http.PostType.HTMLATTACHMENTS.getContentType() + "; boundary=" + s_boundary);
            } else {
            	/* set normal content type */
            	o_httpsUrlConnection.setRequestProperty("Content-Type", this.e_contentType.getContentType());
            }
            
            										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("set content type for web request 'POST': '" + o_httpsUrlConnection.getRequestProperty("Content-Type") + "'");
            
            /* ***** */
            /* start - sending post data for web request 'POST' */
            /* ***** */
            
            										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("start building post data");
            
            /* variable for web request 'POST' output stream for easy handling of adding post data, auto close of output stream when try block is closed */
			try (java.io.OutputStream o_outputStream = o_httpsUrlConnection.getOutputStream()) {
				if (this.a_attachments.size() > 0) {
															if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("iterate all request parameters with key->value pairs");
            		
            		/* iterate all request parameters with key->value pairs */
            		for (java.util.Map.Entry<String,Object> m_requestParameter : this.a_requestParamters.entrySet()) {
            			/* start new post data with two hyphens, boundary and line break */
            			o_outputStream.write(new String("--" + s_boundary + this.s_lineBreak).getBytes(this.o_charset));
            			/* add content disposition and parameter name */
            			o_outputStream.write(new String("Content-Disposition: form-data; name=\"" + m_requestParameter.getKey() + "\"" + this.s_lineBreak).getBytes(this.o_charset));
            			/* add post data value and complete post data element with flush */
            			o_outputStream.write(new String(this.s_lineBreak + String.valueOf(m_requestParameter.getValue()) + this.s_lineBreak).getBytes(this.o_charset));
            		    
            													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("request parameter '" + m_requestParameter.getKey() + "' added");
            		}
	            	
	            											if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("iterate all attachments with key->value pairs");
	            	
	            	for (java.util.Map.Entry<String,String> m_attachment : this.a_attachments.entrySet()) {
	            		if ( (net.forestany.forestj.lib.io.File.exists(m_attachment.getValue())) && (net.forestany.forestj.lib.io.File.isFile(m_attachment.getValue())) ) {
	            			/* start new post data with two hyphens, boundary and line break */
	            			o_outputStream.write(new String("--" + s_boundary + this.s_lineBreak).getBytes(this.o_charset));
	            			/* add content disposition, parameter name and file name */
	            			o_outputStream.write(new String("Content-Disposition: form-data; name=\"" + m_attachment.getKey() + "\"; filename=\"" + new java.io.File(m_attachment.getValue()).getName() + "\"" + this.s_lineBreak).getBytes(this.o_charset));
	            			/* add content type by guess with file name */
	            			String s_fileGuessedContentType = java.net.URLConnection.guessContentTypeFromName(new java.io.File(m_attachment.getValue()).getName());
	            			o_outputStream.write(new String("Content-Type: " + s_fileGuessedContentType + this.s_lineBreak).getBytes(this.o_charset));
	            			/* add line break and flush print writer which indicates that file data is incoming */
	            			o_outputStream.write(new String(this.s_lineBreak).getBytes(this.o_charset));
	            			/* copy file stream into output stream */
	            			java.nio.file.Files.copy(new java.io.File(m_attachment.getValue()).toPath(), o_outputStream);
	            		    /* add line break and flush print writer to indicate end of boundary */
	            		    o_outputStream.write(new String(this.s_lineBreak).getBytes(this.o_charset));
	            		    
	            		    										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("attachment '" + m_attachment.getKey() + "' added with guessed content type: '" + s_fileGuessedContentType + "'");
	            		} else {
	            													net.forestany.forestj.lib.Global.ilogWarning("attachment file '" + m_attachment.getKey() + "' does not exist or is not a file: '" + m_attachment.getValue() + "'");
	            		}
	            	}
	            	
            		/* add end of HTMLATTACHMENTS(multipart/form-data) */
            		o_outputStream.write(new String("--" + s_boundary + "--" + this.s_lineBreak).getBytes(this.o_charset));
        		    /* flush output stream at the end */
        			o_outputStream.flush();
	            } else { /* no attachments available */
	            	/* check if we have any request parameters */
	            	if (this.a_requestParamters.size() > 0) {
	            												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("iterate all request parameters with key->value pairs");
	            		
	            		String s_postBody = "";
	            		
	            		/* check amount of post request parameters */
	            		if (this.a_requestParamters.size() > 0) {
		            		/* iterate all request parameters with key->value pairs */
		            		for (java.util.Map.Entry<String,Object> m_requestParameter : this.a_requestParamters.entrySet()) {
		            			/* add parameter name and value to post body */
		            			s_postBody += java.net.URLEncoder.encode( m_requestParameter.getKey(), this.o_charset.displayName() ) + "=" + java.net.URLEncoder.encode( String.valueOf(m_requestParameter.getValue()), this.o_charset.displayName() ) + "&";
		            			
		            													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("request parameter '" + m_requestParameter.getKey() + "' added");
		            		}
		            		
		            		/* remove last '&' */
		            		s_postBody = s_postBody.substring(0, (s_postBody.length() - 1));
		            		
		            		/* add post body data to output stream */
		            		o_outputStream.write(new String(s_postBody).getBytes(this.o_charset));
	            		} else {
	            			/* add empty post body data to output stream */
		            		o_outputStream.write(new String(s_postBody + this.s_lineBreak).getBytes(this.o_charset));
	            		}
	            		
            		    /* flush output stream at the end */
            			o_outputStream.flush();
	            		
	            												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("added all request parameters to post body data");
	            	}
	            }
            }
            
            										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("finished building post data");
            
            /* *** */
            /* end - sending post data of web request 'POST' */
            /* *** */
        } else {
        	throw new IllegalArgumentException("web request type '" + this.e_type + "' is not supported");
        }
		
		/* ***** */
        /* start - reading response for web request */
        /* ***** */
		
												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("starting receiving response data");
		
		/* reading input stream into file output stream if web request is a 'DOWNLOAD' */
    	if (this.e_type == RequestType.DOWNLOAD) {
    												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("reading input stream into file output stream");
    		
    		try (
	        	/* use input stream for web request response */
				java.io.InputStream o_inputStream = o_httpsUrlConnection.getInputStream();
    			java.io.FileOutputStream o_fileOutputStream = new java.io.FileOutputStream(this.s_downloadFilename);
		    ) {
														if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("get content length");
				
				/* get content length */
				long l_contentLength = o_httpsUrlConnection.getContentLengthLong();

														if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("content length: '" + l_contentLength + "'");
    			
    			/* help variables to read and write stream instances */
                byte[] a_buffer = new byte[1024];
                int i_length = 0;
                long l_sum = 0;
                
                										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("read input stream until the end");
                
                /* read input stream until the end */
                while ((i_length = o_inputStream.read(a_buffer)) > 0) {
                	/* write in output file stream */
                    o_fileOutputStream.write(a_buffer, 0, i_length);
                    
                    /* post progress */
                    if (this.itf_delegate != null) {
                    	l_sum += i_length;
        				this.itf_delegate.PostProgress((double)l_sum/l_contentLength, ( (o_url.getPath().lastIndexOf("/") > 0) ? o_url.getPath().substring(o_url.getPath().lastIndexOf("/") + 1) : o_url.getPath() ) );
        			}
                }
                
                										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("store information in string return value that download request has been saved to local file");
                
                s_response += "File[" + ( (o_url.getPath().lastIndexOf("/") > 0) ? o_url.getPath().substring(o_url.getPath().lastIndexOf("/") + 1) : o_url.getPath() ) + "] downloaded to '" + this.s_downloadFilename + "'";
            } catch (Exception o_exc) {
	        											net.forestany.forestj.lib.Global.ilogWarning("exception occurred while waiting/reading response for/of web request");
            	
            	/* exception occurred while waiting/reading response for/of web request */
	        	s_response += "Error in web request - " + o_exc.getMessage();
	        } finally {
	        											if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("get response code and message");
	        	
	        	/* get response code and message */
	    		this.i_responseCode = o_httpsUrlConnection.getResponseCode();
	    		this.s_responseMessage = o_httpsUrlConnection.getResponseMessage();
	    		
	    												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("response code: '" + this.i_responseCode + "'");
	    		
    													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("response message: '" + this.s_responseMessage + "'");
	    	}
    	} else { /* reading input stream into string if web request is a 'GET' or 'POST' */
    												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("reading input stream into string return value");
    									
    		try (
	        	/* use buffered reader as input stream for web request response */
				java.io.BufferedReader o_inputStream = new java.io.BufferedReader(
		        	new java.io.InputStreamReader(
		        			o_httpsUrlConnection.getInputStream(), this.o_charset.displayName()
		        	)
		        )
			) {
														if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("get content length");
				
				/* get content length */
				long l_contentLength = o_httpsUrlConnection.getContentLengthLong();

														if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("content length: '" + l_contentLength + "'");
    			
    			/* help variable reading input stream */
		        String s_input = "";
		        
		        /* help variable to count streamed/downloaded content length */
		        long l_sum = 0;
		        
		        										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("read input stream until the end");
		        
		        /* read input stream until the end and add to response variable */
		        while ((s_input = o_inputStream.readLine()) != null) { 
		        	s_response += s_input;
		        	
		        	/* post progress */
                    if (this.itf_delegate != null) {
                    	l_sum += s_input.length();
        				this.itf_delegate.PostProgress((double)l_sum/l_contentLength, ( (o_url.getPath().lastIndexOf("/") > 0) ? o_url.getPath().substring(o_url.getPath().lastIndexOf("/") + 1) : o_url.getPath() ) );
        			}
		        }
		    } catch (Exception o_exc) {
	        											net.forestany.forestj.lib.Global.ilogWarning("exception occurred while waiting/reading response for/of web request");
	        	
		    	/* exception occurred while waiting/reading response for/of web request */
	        	s_response += "Error in web request - " + o_exc.getMessage();
	        } finally {
	        											if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("get response code and message");
	        	
	        	/* get response code and message */
	    		this.i_responseCode = o_httpsUrlConnection.getResponseCode();
	    		this.s_responseMessage = o_httpsUrlConnection.getResponseMessage();
	    		
	    												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("response code: '" + this.i_responseCode + "'");
	    		
    													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("response message: '" + this.s_responseMessage + "'");
	       }
    	}
		
    											if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("finished receiving response data");
    	
        /* *** */
        /* end - reading response for web request */
        /* *** */
        
    	if (o_httpsUrlConnection != null) {
    												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("close http connection instance");
    		
	    	/* close http connection instance */
	        o_httpsUrlConnection.disconnect();
	        /* set url and http connection instance to null for cleaning up memory */
	        o_httpsUrlConnection = null;
	        o_url = null;
    	}
        
    											if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("return web request response");
    	
        /* return web request response */
		return s_response;
	}
}
