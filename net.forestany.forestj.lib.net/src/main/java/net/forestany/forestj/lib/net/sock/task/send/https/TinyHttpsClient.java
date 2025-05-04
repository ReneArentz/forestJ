package net.forestany.forestj.lib.net.sock.task.send.https;

/**
 * Task class to create a tiny https client. You can create https, soap or rest requests and receive the response to it on properties within Seed instance. Only TCP supported.
 * Only HTTP/1.0 or HTTP/1.1.
 * 
 * @param <T>	java socket class parameter
 */
public class TinyHttpsClient<T extends javax.net.ssl.SSLSocket> extends net.forestany.forestj.lib.net.sock.task.Task<javax.net.ssl.SSLSocket> {
	
	/* Constants */
	
	/**
	 * allowed parameter types as constant list
	 */
	public static final java.util.List<String> a_allowedParameterTypes = java.util.Arrays.asList("byte", "short", "int", "long", "float", "double", "char", "boolean", "string", "java.lang.String", "java.lang.Short", "java.lang.Byte", "java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double", "java.lang.Boolean", "java.lang.Character");
	
	/* Fields */
	
	private net.forestany.forestj.lib.net.https.Seed o_seed;
	private boolean b_requestSet;
	private net.forestany.forestj.lib.net.http.RequestType e_requestType;
	private java.util.Map<String,Object> m_requestParamters;
	private java.util.Map<String,String> m_attachments;
	private String s_downloadFilename;
	private boolean b_downloadFileExtensionNeeded;
	private boolean b_useLog;
	private String s_proxyAddress;
	private int i_proxyPort;
	private net.forestany.forestj.lib.net.http.PostType e_contentType;
	private String s_authenticationUser;
	private String s_authenticationPassword;
	private String s_proxyAuthenticationUser;
	private String s_proxyAuthenticationPassword;
	private Object o_soapRequest;
	private Object o_soapResponse;
	private net.forestany.forestj.lib.net.https.soap.SoapFault o_soapFault;
	private net.forestany.forestj.lib.net.https.dynm.Cookie o_tempCookie;
	
	/* Properties */
	
	/**
	 * get seed instance
	 * 
	 * @return net.forestany.forestj.lib.net.https.Seed
	 */
	public net.forestany.forestj.lib.net.https.Seed getSeed() {
		return this.o_seed;
	}
	
	/**
	 * get request parameters
	 * 
	 * @return java.util.Map&lt;String, Object&gt;
	 */
	public java.util.Map<String, Object> getRequestParameters() {
		return this.m_requestParamters;
	}
	
	/**
	 * get download file name
	 * 
	 * @return String
	 */
	public String getDownloadFilename() {
		return this.s_downloadFilename;
	}
	
	/**
	 * set download file name
	 * 
	 * @param p_s_value String
	 */
	public void setDownloadFilename(String p_s_value) {
		this.s_downloadFilename = p_s_value;
	}
	
	/**
	 * get download file extension needed flag
	 * 
	 * @return boolean
	 */
	public boolean getDownloadFileExtensionNeeded() {
		return this.b_downloadFileExtensionNeeded;
	}
	
	/**
	 * set download file extension needed flag
	 * 
	 * @param p_b_value boolean
	 */
	public void setDownloadFileExtensionNeeded(boolean p_b_value) {
		this.b_downloadFileExtensionNeeded = p_b_value;
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
	 * get proxy address
	 * 
	 * @return String
	 */
	public String getProxyAddress() {
		return this.s_proxyAddress;
	}
	
	/**
	 * set proxy address
	 * 
	 * @param p_s_value String
	 */
	public void setProxyAddress(String p_s_value) {
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
	 * @param p_i_value						port value for proxy connection
	 * @throws IllegalArgumentException		port value must be at least '1' and lower equal '65535'
	 */
	public void setProxyPort(int p_i_value) throws IllegalArgumentException {
		/* check port min. value */
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Port must be at least '1', but was set to '" + p_i_value + "'");
		}
		
		/* check port max. value */
		if (p_i_value > 65535) {
			throw new IllegalArgumentException("Port must be lower equal '65535', but was set to '" + p_i_value + "'");
		}
		
		this.i_proxyPort = p_i_value;
	}
	
	/**
	 * get content type
	 * 
	 * @return net.forestany.forestj.lib.net.http.PostType
	 */
	public net.forestany.forestj.lib.net.http.PostType getContentType() {
		return this.e_contentType;
	}
	
	/**
	 * set content type
	 * 
	 * @param p_e_value net.forestany.forestj.lib.net.http.PostType
	 */
	public void setContentType(net.forestany.forestj.lib.net.http.PostType p_e_value) {
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
	 * get proxy authentication user
	 * 
	 * @return String
	 */
	public String getProxyAuthenticationUser() {
		return this.s_proxyAuthenticationUser;
	}
	
	/**
	 * set proxy authentication user
	 * 
	 * @param p_s_value String
	 */
	public void setProxyAuthenticationUser(String p_s_value) {
		this.s_proxyAuthenticationUser = p_s_value;
	}
	
	/**
	 * get proxy authentication password
	 * 
	 * @return String
	 */
	public String getProxyAuthenticationPassword() {
		return this.s_proxyAuthenticationPassword;
	}
	
	/**
	 * set proxy authentication password
	 * 
	 * @param p_s_value String
	 */
	public void setProxyAuthenticationPassword(String p_s_value) {
		this.s_proxyAuthenticationPassword = p_s_value;
	}
	
	/**
	 * set delegate
	 * 
	 * @param p_itf_delegate net.forestany.forestj.lib.net.sock.task.Task.IDelegate
	 */
	public void setDelegate(net.forestany.forestj.lib.net.sock.task.Task.IDelegate p_itf_delegate) {
		this.itf_delegate = p_itf_delegate;
	}
	
	/**
	 * get return code
	 * 
	 * @return int
	 */
	public int getReturnCode() {
		return this.getSeed().getResponseHeader().getReturnCode();
	}
	
	/**
	 * get return message
	 * 
	 * @return String
	 */
	public String getReturnMessage() {
		return this.getSeed().getResponseHeader().getReturnMessage();
	}
	
	/**
	 * get response body
	 * 
	 * @return byte[]
	 */
	public byte[] getResponseBody() {
		return this.getSeed().getResponseBody();
	}
	
	/**
	 * get response
	 * 
	 * @return String
	 */
	public String getResponse() {
		return new String(this.getSeed().getResponseBody(), this.getSeed().getConfig().getInEncoding());
	}
	
	/**
	 * set soap request
	 * 
	 * @param p_o_object Object
	 */
	public void setSOAPRequest(Object p_o_object) {
		this.o_soapRequest = p_o_object;
	}
	
	/**
	 * get soap response
	 * 
	 * @return							returning SOAP response as forecasted object which can be casted
	 * @throws IllegalStateException	cannot return SOAP response. A SOAP fault has been occurred, see getSOAPFault()
	 */
	public Object getSOAPResponse() throws IllegalStateException {
		if (this.o_soapFault != null) {
			throw new IllegalStateException("Cannot return SOAP response. A SOAP fault has been occured, see getSOAPFault().");
		}
		
		return this.o_soapResponse;
	}
	
	/**
	 * get soap fault
	 * 
	 * @return net.forestany.forestj.lib.net.https.soap.SoapFault
	 */
	public net.forestany.forestj.lib.net.https.soap.SoapFault getSOAPFault() {
		return this.o_soapFault;
	}
	
	/* Methods */
	
	/**
	 * Standard constructor, initializing all values
	 */
	public TinyHttpsClient() {
		super();
		
		this.o_seed = null;
		this.e_requestType = null;
		this.b_requestSet = false;
		this.m_requestParamters = new java.util.LinkedHashMap<String,Object>();
		this.m_attachments = new java.util.LinkedHashMap<String,String>();
		this.b_useLog = false;
		
		this.s_downloadFilename = null;
		this.b_downloadFileExtensionNeeded = false;
		this.s_proxyAddress = null;
		this.i_proxyPort = -1;
		this.e_contentType = null;
		this.s_authenticationUser = null;
		this.s_authenticationPassword = null;
		this.s_proxyAuthenticationUser = null;
		this.s_proxyAuthenticationPassword = null;
		
		this.o_soapRequest = null;
		this.o_soapResponse = null;
		this.o_soapFault = null;
		
		this.o_tempCookie = null;
	}
	
	/**
	 * Creating tiny https client task instance with all it's settings via configuration parameter
	 * 
	 * @param p_o_config					configuration instance parameter
	 * @throws NullPointerException			configuration instance parameter is null, or domain parameter from configuration is null or empty
	 */
	public TinyHttpsClient(net.forestany.forestj.lib.net.https.Config p_o_config) throws NullPointerException {
		super(net.forestany.forestj.lib.net.sock.Type.TCP);
		
		this.o_seed = new net.forestany.forestj.lib.net.https.Seed(p_o_config);
		this.e_requestType = null;
		this.b_requestSet = false;
		this.m_requestParamters = new java.util.LinkedHashMap<String,Object>();
		this.m_attachments = new java.util.LinkedHashMap<String,String>();
		this.b_useLog = false;
		
		this.s_downloadFilename = null;
		this.b_downloadFileExtensionNeeded = false;
		this.s_proxyAddress = null;
		this.i_proxyPort = -1;
		this.e_contentType = null;
		this.s_authenticationUser = null;
		this.s_authenticationPassword = null;
		this.s_proxyAuthenticationUser = null;
		this.s_proxyAuthenticationPassword = null;
		
		this.o_soapRequest = null;
		this.o_soapResponse = null;
		this.o_soapFault = null;
		
		this.o_tempCookie = null;
		
		/* set Task debug network traffic flag from configuration parameter */
		this.setDebugNetworkTrafficOn(this.getSeed().getConfig().getDebugNetworkTrafficOn());
	}
	
	/**
	 * Method to get socket task class type for creating new instances with declared constructor
	 * @return		class type of socket task as Class&lt;?&gt;
	 */
	public Class<?> getSocketTaskClassType() {
		return TinyHttpsClient.class;
	}
	
	/**
	 * Method to clone this socket task with another socket task instance
	 * @param p_o_sourceTask		another socket task instance as source for all it's parameters and settings
	 */
	public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<javax.net.ssl.SSLSocket> p_o_sourceTask) {
		this.cloneBasicFields(p_o_sourceTask);
		
		/* ignore exceptions if a property of source task has no valid value, we will keep it null */
		try { this.o_seed = ( ((TinyHttpsClient<javax.net.ssl.SSLSocket>)p_o_sourceTask) ).getSeed(); } catch (Exception o_exc) { /* NOP */ }
	}
	
	/**
	 * Set destination address, primarily this method is for SOAP mode as the request type will be auto set to POST
	 * 
	 * @param p_s_url							destination address
	 * @throws NullPointerException				destination address parameter is null or empty
	 * @throws IllegalArgumentException			no Seed instance available
	 * @throws java.net.UnknownHostException	issue local host name could not be resolved into an address
	 */
	public void setRequest(String p_s_url) throws NullPointerException, IllegalArgumentException, java.net.UnknownHostException {
		this.setRequest(p_s_url, null);
	}
	
	/**
	 * Set destination address, primarily this method is for SOAP mode as the request type will be auto set to POST, where SOAP request object will be set as well
	 * 
	 * @param p_s_url							destination address
	 * @param p_o_soapRequest					SOAP request object which can be used with an XML instance to create a xml-valid SOAP request
	 * @throws IllegalStateException			client is not in SOAP mode
	 * @throws NullPointerException				destination address parameter is null or empty
	 * @throws IllegalArgumentException			no Seed instance available
	 * @throws java.net.UnknownHostException	issue local host name could not be resolved into an address
	 */
	public void setSOAPRequest(String p_s_url, Object p_o_soapRequest) throws IllegalStateException, NullPointerException, IllegalArgumentException, java.net.UnknownHostException {
		/* check if our client is in SOAP mode */
		if (this.getSeed().getConfig().getMode() != net.forestany.forestj.lib.net.https.Mode.SOAP) {
			throw new IllegalStateException("Mode '" + this.getSeed().getConfig().getMode() + "' of " + net.forestany.forestj.lib.net.https.RequestHeader.CLIENT + " is not '" + net.forestany.forestj.lib.net.https.Mode.SOAP + "'");
		}
		
		/* set SOAP request object */
		this.setSOAPRequest(p_o_soapRequest);
		/* set other request settings */
		this.setRequest(p_s_url);
	}
	
	/**
	 * Set destination address and request type of tiny https client
	 * 
	 * @param p_s_url							destination address
	 * @param p_e_requestType					request type or http method
	 * @throws NullPointerException				destination address or request type parameter is null or empty
	 * @throws IllegalArgumentException			no Seed instance available or request type parameter not implemented
	 * @throws java.net.UnknownHostException	issue local host name could not be resolved into an address
	 */
	public void setRequest(String p_s_url, net.forestany.forestj.lib.net.http.RequestType p_e_requestType) throws NullPointerException, IllegalArgumentException, java.net.UnknownHostException {
		/* check if Seed instance is available */
		if (this.getSeed() == null) {
			throw new IllegalArgumentException("Configuration for tiny https client is not specified");
		}
		
		/* re-initialize values */
		this.o_seed = new net.forestany.forestj.lib.net.https.Seed(this.getSeed().getConfig());
		this.m_requestParamters = new java.util.LinkedHashMap<String,Object>();
		this.m_attachments = new java.util.LinkedHashMap<String,String>();
		this.b_useLog = false;
		
		this.s_downloadFilename = null;
		this.b_downloadFileExtensionNeeded = false;
		this.e_contentType = null;
		this.s_authenticationUser = null;
		this.s_authenticationPassword = null;
		
		/* check destination address parameter */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_url)) {
			throw new NullPointerException("URL parameter is null or empty");
		}
		
		/* check request type parameter, auto set on POST if our task instance is in SOAP mode */
		if ( (p_e_requestType == null) && (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.SOAP) ) {
			p_e_requestType = net.forestany.forestj.lib.net.http.RequestType.POST;
		} else if (p_e_requestType == null) {
			throw new NullPointerException("Request type parameter is null");
		}
		
		this.e_requestType = p_e_requestType;
		this.getSeed().getRequestHeader().setMethod(p_e_requestType);
		
		int i_port = 0;
		
		/* check if destination address starts correctly */
		if (p_s_url.startsWith("http://")) { /* remove 'http://' from start */
			this.getSeed().getRequestHeader().setProtocol("http://");
			p_s_url = p_s_url.substring(7);
			i_port = 80;
		} else if (p_s_url.startsWith("https://")) { /* remove 'https://' from start */
			this.getSeed().getRequestHeader().setProtocol("https://");
			p_s_url = p_s_url.substring(8);
			i_port = 443;
		} else {
			throw new IllegalArgumentException("URL parameter must start with 'http://' or 'https://'");
		}
		
		String s_requestPath = "/";
		String s_host = "";
		
		/* get request path and host part */
		if (p_s_url.contains("/")) {
			s_requestPath = p_s_url.substring(p_s_url.indexOf("/"));
			s_host = p_s_url.substring(0, p_s_url.indexOf("/"));
		} else {
			s_host = p_s_url;
		}
		
		/* read optional port value within destination address */
		if (s_host.contains(":")) {
			/* split by ':' */
			String[] a_foo = s_host.split(":");
			/* set host part */
			s_host = a_foo[0];

			/* check if port value is an integer */
			if (!net.forestany.forestj.lib.Helper.isInteger(a_foo[1])) {
				throw new IllegalArgumentException("Invalid value '" + a_foo[1] + "' for destination port, must be an integer number between 1 and 65535");
			} else {
				/* store port value */
				i_port = Integer.parseInt(a_foo[1]);
				
				/* check port min. value */
				if (i_port < 1) {
					throw new IllegalArgumentException("Port must be at least '1', but was set to '" + i_port + "'");
				}
				
				/* check port max. value */
				if (i_port > 65535) {
					throw new IllegalArgumentException("Port must be lower equal '65535', but was set to '" + i_port + "'");
				}
			}
		}
		
		/* overwrite request settings */
		this.getSeed().getRequestHeader().setRequestPath(s_requestPath);
		this.getSeed().getRequestHeader().setHost(s_host);
		this.getSeed().getRequestHeader().setPort(i_port);
		this.getSeed().getRequestHeader().setConnectionClose(true);
		
		/* override destination settings of our sending socket instance */
		this.getSeed().getConfig().getSendingSocketInstance().overrideDestinationAddress(s_host, i_port, false, false);
		
		/* inject cookies from last request */
		if ( (this.getSeed().getConfig().getClientUseCookiesFromPreviousRequest()) && (this.o_tempCookie != null) ) {
			this.getSeed().getRequestHeader().setCookie( this.o_tempCookie.clientCookieToString() );
		}
		
		/* now request is set and can be called with executeRequest() */
		this.b_requestSet = true;
	}
	
	/**
	 * Adds request parameter to web request, if parameter type is an allowed type
	 * 
	 * @param p_s_parameterName					type of request parameter, e.g. string, boolean, or double
	 * @param p_o_parameterValue				value of request parameter
	 * @throws IllegalArgumentException			parameter type is not an allowed type
	 */
	public void addRequestParameter(String p_s_parameterName, Object p_o_parameterValue) throws IllegalArgumentException {
		/* check if object value has allowed type */
		if (!TinyHttpsClient.a_allowedParameterTypes.contains(p_o_parameterValue.getClass().getTypeName())) {
			throw new IllegalArgumentException("Parameter[" + p_s_parameterName + "] with type[" + p_o_parameterValue.getClass().getTypeName() + "] is not an allowed parameter type: " + a_allowedParameterTypes.toString());
		}
		
		/* add request parameter name and value */
		m_requestParamters.put(p_s_parameterName, p_o_parameterValue);
		
												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("added request parameter: '" + p_s_parameterName + "' = '" + p_o_parameterValue + "'");
	}
	
	/**
	 * Adds filepath to web request which will be attached for web request execution
	 * 
	 * @param p_s_parameterName			file name for request
	 * @param p_s_filePath				local file path of attachment
	 */
	public void addAttachement(String p_s_parameterName, String p_s_filePath) {
		/* add request parameter attachment name and filepath */
		this.m_attachments.put(p_s_parameterName, p_s_filePath);
		
												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("added attachment to request: '" + p_s_parameterName + "' = '" + p_s_filePath + "'");
	}
	
	/**
	 * Execute a request, by running sending socket instance.
	 * Optional proxy settings available for execution.
	 * 
	 * @throws IllegalStateException				flag not set that the request can be executed, you must first call setRequest(...), or 	destination host is not reachable 
	 * @throws java.net.UnknownHostException		issue local host name could not be resolved into an address
	 */
	public void executeRequest() throws IllegalStateException, java.net.UnknownHostException {
		/* check if all necessary request information has been set */
		if (!this.b_requestSet) {
			throw new IllegalStateException("Missing request parameters, e.g. host address and port. Please use the setRequest(...) method.");
		}
		
		/* check for authentication */
		if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_authenticationUser)) && (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_authenticationPassword)) ) {
			/* set authentication user and password as base64 for request */
			String s_foo = this.s_authenticationUser + ":" + this.s_authenticationPassword;
			this.getSeed().getRequestHeader().setAuthorization("Basic " + java.util.Base64.getEncoder().encodeToString(s_foo.getBytes()));
			
													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("created base64 authentication string and added it to web request properties");
		}
		
		/* check if we have proxy address and port settings */
		if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_proxyAddress)) && (this.i_proxyPort > 0) ) {
			/* prepare communication with proxy server */
			this.getSeed().getConfig().getSendingSocketInstance().overrideDestinationAddress(this.s_proxyAddress, this.i_proxyPort, true, false);
			
													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogConfig("changed socket destination to proxy address: '" + this.s_proxyAddress + ":" + this.i_proxyPort + "'");
		}
		
		/* run socket sending instance */
		this.getSeed().getConfig().getSendingSocketInstance().run();
	}
	
	/**
	 * runTask method for sending https/soap/rest request
	 * 
	 * @throws Exception	any exception of implementation that could happen will be caught by abstract Task class, see details in protocol methods in net.forestany.forestj.lib.net.sock.task.Task
	 */
	protected void runTask() throws Exception {
		/* only TCP supported */
		if (this.e_type == net.forestany.forestj.lib.net.sock.Type.TCP) {
			try {
				/* check if Seed instance is availalbe */
				if (this.getSeed() == null) {
					throw new IllegalArgumentException("Configuration for tiny https client is not specified");
				}

				/* do optional proxy routine */
				this.optionalProxy();
				
		        byte[] a_requestData = null;
		        
		        /* check mode and use individual routine to prepare request */
		        if (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.NORMAL) {
		        	/* prepare https request */
		        	a_requestData = this.handleNormal();
		        } else if (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.DYNAMIC) {
		        	throw new UnsupportedOperationException("Client mode 'dynamic' is not implemented");
		        } else if (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.SOAP) {
		        	/* prepare SOAP request */
		        	a_requestData = this.handleSOAP();
		        } else if (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.REST) {
		        	/* prepare REST request */
		        	a_requestData = this.handleREST();
		        }
		        
		        /* check if we have prepared request data to sent */
		        if (a_requestData == null) {
		        	throw new IllegalStateException("Request data is null, cannot send http(s) request");
		        }

		        										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("sending https/SOAP/REST request to destination");
		        
		        /* sending https/SOAP/REST request to destination */
		        this.sendBytes(a_requestData, a_requestData.length);
		        
		        										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("sent https/SOAP/REST request to destination");
		        
		        int i_returnCode = 500;
		        
		        										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("receive https/SOAP/REST response");
		        
		        /* use individual routine to handle response */
		        if (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.NORMAL) {
		        	/* handle https response */
		        	i_returnCode = this.handleNormalResponse();
		        } else if (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.DYNAMIC) {
		        	throw new UnsupportedOperationException("Client mode 'dynamic' is not implemented");
		        } else if (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.SOAP) {
		        	/* handle SOAP response */
		        	i_returnCode = this.handleSOAPResponse();
		        } else if (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.REST) {
		        	/* handle REST response */
		        	i_returnCode = this.handleRESTResponse();
		        }
		        
		        /* set return code from handled response */
		        this.getSeed().getResponseHeader().setReturnCode( i_returnCode );
		    } catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.ilogSevere("Exception runTask: " + o_exc);
				
				if (this.getSeed().getConfig().getPrintExceptionStracktrace()) {
					net.forestany.forestj.lib.Global.logException(o_exc);
				}
			}
		} else if (this.e_type == net.forestany.forestj.lib.net.sock.Type.UDP) {
			throw new UnsupportedOperationException("Not implemented for UDP protocol");
		} else {
			throw new UnsupportedOperationException("Not implemented");
		}
	}

	/**
	 * do optional proxy routine
	 */
	private void optionalProxy() throws Exception {
		/* only do optional proxy routine if we have a proxy address and port */
		if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_proxyAddress)) && (this.i_proxyPort > 0) ) {
													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("prepare proxy CONNECT header");
			
			/* prepare CONNECT header */
			String s_proxyConnectHeader = "";
			s_proxyConnectHeader += "CONNECT " + this.getSeed().getRequestHeader().getHost() + ":" + this.getSeed().getRequestHeader().getPort() + " HTTP/1.1" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
			s_proxyConnectHeader += "Host: " + this.getSeed().getRequestHeader().getHost() + ":" + this.getSeed().getRequestHeader().getPort() + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
			
			/* check for authentication for proxy */
			if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_proxyAuthenticationUser)) && (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_proxyAuthenticationPassword)) ) {
				/* set authentication user and password as base64 for CONNECT header */
				String s_foo = this.s_proxyAuthenticationUser + ":" + this.s_proxyAuthenticationPassword;
				s_proxyConnectHeader += "Proxy-Authorization: Basic " + java.util.Base64.getEncoder().encodeToString(s_foo.getBytes()) + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
				
														if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("created base64 proxy authentication string and added it to proxy request");
			}
			
			/* add additional line break */
			s_proxyConnectHeader += net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
			
			/* use outgoing encoding configuration */
			s_proxyConnectHeader = new String(s_proxyConnectHeader.getBytes(), this.getSeed().getConfig().getOutEncoding());
			/* create proxy request byte array */
			byte[] a_proxyRequest = new byte[s_proxyConnectHeader.getBytes().length];
			int i;
			
			for (i = 0; i < s_proxyConnectHeader.getBytes().length; i++) {
				a_proxyRequest[i] = s_proxyConnectHeader.getBytes()[i];
			}
			
													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("sending request to proxy");
	        
			/* send proxy CONNECT request */
	        this.sendBytes(a_proxyRequest, a_proxyRequest.length);
	        
	        										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("sent request to proxy");
	        
	        byte[] a_responseData = null;
			
	        										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("receive proxy response");
	        
	        /* receive proxy CONNECT response */
			try {
				a_responseData = this.receiveBytes(-1);
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.ilogSevere("500 Internal Server Error: Exception while receiving data; " + o_exc.getMessage());
				this.getSeed().getResponseHeader().setReturnCode(500);
			}
			
			/* check if we've got a response */
			if ( (a_responseData == null) || (a_responseData.length < 1) ) {
				net.forestany.forestj.lib.Global.ilogWarning("204 No Content: HTTP request from proxy is null or empty");
				this.getSeed().getResponseHeader().setReturnCode(204);
			}
			
			/* check max. payload of response */
			if (a_responseData.length >= this.getSeed().getConfig().getMaxPayload() * 1024 * 1024) {
				net.forestany.forestj.lib.Global.ilogWarning("413 Payload Too Large: Reached max. payload of '" + net.forestany.forestj.lib.Helper.formatBytes(this.getSeed().getConfig().getMaxPayload() * 1024 * 1024) + "'");
				this.getSeed().getResponseHeader().setReturnCode(413);
			}
			
			/* convert response byte array to lower case string */
			String s_foo = new String(a_responseData).toLowerCase();
			
			/* we expect code '200', message 'ok' and the text 'connection established' */
			if (!( (s_foo.contains("200")) && ( (s_foo.contains("ok")) || (s_foo.contains("connection established")) ) )) {
				net.forestany.forestj.lib.Global.ilogSevere("500 Internal Server Error: Proxy server returns unexpected answer; " + new String(a_responseData).trim());
				this.getSeed().getResponseHeader().setReturnCode(500);
				this.getSeed().getResponseHeader().setReturnMessage("Proxy server returns unexpected answer; " + new String(a_responseData).trim());
				return;
			}
			
													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("proxy response: " + new String(a_responseData));
			
			/* upgrade current socket(proxy socket) to a TLS socket connection */
			this.getSeed().getConfig().getSendingSocketInstance().upgradeCurrentSocketToTLS(this.getSeed().getRequestHeader().getHost(), this.getSeed().getRequestHeader().getPort(), false);
		}
	}
	
	/**
	 * prepare https request
	 *
	 * @throws IllegalArgumentException					invalid download filename location or invalid content type
	 * @throws java.io.IOException						issues to check download filename location, url encoding data or reading all bytes from an attachment
	 * @throws java.io.UnsupportedEncodingException		if the configured outgoing encoding is not supported when encoding data
	 */
	private byte[] handleNormal() throws IllegalArgumentException, java.io.IOException, java.io.UnsupportedEncodingException {
		/* dynamic byte array for POST/PUT/DELETE request */
		java.util.List<Byte> a_postBytes = new java.util.ArrayList<Byte>();
		
		/* check if we have any request parameters and web request type is 'GET' or 'DOWNLOAD' */
        if ( (this.m_requestParamters.size() > 0) && ( (this.e_requestType == net.forestany.forestj.lib.net.http.RequestType.GET) || (this.e_requestType == net.forestany.forestj.lib.net.http.RequestType.DOWNLOAD) ) ) {
        											if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("gather request parameters with request type is 'GET' or 'DOWNLOAD'");
        	
        	/* string variable for request parameters */
        	String s_requestParameters = "";
    		
        	/* iterate all request parameters with key->value pairs */
            for (java.util.Map.Entry<String,Object> m_requestParameter : this.m_requestParamters.entrySet()) {
                /* add key->value pair request parameter url encoded to variable */
            	s_requestParameters += java.net.URLEncoder.encode(m_requestParameter.getKey(), this.getSeed().getConfig().getOutEncoding().displayName()) + "=" + java.net.URLEncoder.encode(String.valueOf(m_requestParameter.getValue()), this.getSeed().getConfig().getOutEncoding().displayName()) + "&";
            }
            
            /* if request parameters ends with '&' */
            if (s_requestParameters.endsWith("&")) {
            	/* remove last '&' character from request parameters */
            	s_requestParameters = s_requestParameters.substring(0, s_requestParameters.length() - 1);
            }
        	
            										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("request parameters: '" + s_requestParameters + "'");
            
            /* add request parameters for type 'GET' or 'DOWNLOAD' to request path */
            this.getSeed().getRequestHeader().setRequestPath( this.getSeed().getRequestHeader().getRequestPath() + "?" + s_requestParameters);
        }
		
		if (this.e_requestType == net.forestany.forestj.lib.net.http.RequestType.DOWNLOAD) { /* web request type 'DOWNLOAD' */
			/* check if download filename location is set */
        	if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_downloadFilename)) {
        		throw new IllegalArgumentException("Please specify a download filename location");
        	}
        	
        	/* check if download filename location exists */
        	if (!net.forestany.forestj.lib.io.File.folderExists(this.s_downloadFilename)) {
        		throw new java.io.IOException("Folder of download filename location[" + this.s_downloadFilename + "] does not exist. Please create the download folder location before execution");
        	}
        	
        	if (this.b_downloadFileExtensionNeeded) { /* check if download filename location has a file extension */
        		if (!net.forestany.forestj.lib.io.File.hasFileExtension(this.s_downloadFilename)) {
            		throw new IllegalArgumentException("Download filename location[" + this.s_downloadFilename + "] must have a valid file extension");
            	}	
        	}
        } else if (
        	(this.e_requestType == net.forestany.forestj.lib.net.http.RequestType.POST) || 
        	(this.e_requestType == net.forestany.forestj.lib.net.http.RequestType.PUT) || 
        	(
        		(this.e_requestType == net.forestany.forestj.lib.net.http.RequestType.DELETE) && (this.e_contentType != null)
        	)
        ) { /* web request type 'POST', 'PUT' or 'DELETE', but only 'DELETE' if content type is not null */
        	/* random value for https file upload protocol */
        	String s_boundary = "------------" + Long.toHexString(System.currentTimeMillis());
        	
        											if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("created random value(boundary) for http file upload protocol: '" + s_boundary + "'");
        	
        	/* check if content type is not null, necessary for type 'POST', 'PUT' */
            if (this.e_contentType == null) {
            	throw new IllegalArgumentException("No content type for '" + this.e_requestType + "' web request specified");
            }
            
            /* check if we have any attachments for web request 'POST' */
            if (this.m_attachments.size() > 0) {
            	/* set content type with random boundary */
        		this.getSeed().getRequestHeader().setContentType(net.forestany.forestj.lib.net.http.PostType.HTMLATTACHMENTS.getContentType() + "; boundary=" + s_boundary);
            } else {
            	/* set normal content type */
            	this.getSeed().getRequestHeader().setContentType(this.e_contentType.getContentType());
            }
            
            										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("set content type for web request '" + this.e_requestType + "': '" + this.getSeed().getRequestHeader().getContentType() + "'");
            
            /* ***** */
            /* start - preparing post data for web request 'POST' */
            /* ***** */
            										
            										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("start preparing post data");
            
            /* attachments available */
			if (this.m_attachments.size() > 0) {
														if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("iterate all request parameters with key->value pairs");
        		
        		/* iterate all request parameters with key->value pairs */
        		for (java.util.Map.Entry<String,Object> m_requestParameter : this.m_requestParamters.entrySet()) {
        			/* start new post data with two hyphens, boundary and line break */
        			net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(new String("--" + s_boundary + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK).getBytes(this.getSeed().getConfig().getOutEncoding()), a_postBytes);
        			/* add content disposition and parameter name */
        			net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(new String("Content-Disposition: form-data; name=\"" + m_requestParameter.getKey() + "\"" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK).getBytes(this.getSeed().getConfig().getOutEncoding()), a_postBytes);
        			/* add post data value and complete post data element */
        			net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(new String(net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK + String.valueOf(m_requestParameter.getValue()) + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK).getBytes(this.getSeed().getConfig().getOutEncoding()), a_postBytes);
        		    
        													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFiner("request parameter '" + m_requestParameter.getKey() + "' added");
        		}
            	
            											if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("iterate all attachments with key->value pairs");
            	
            	for (java.util.Map.Entry<String,String> m_attachment : this.m_attachments.entrySet()) {
            		if ( (net.forestany.forestj.lib.io.File.exists(m_attachment.getValue())) && (net.forestany.forestj.lib.io.File.isFile(m_attachment.getValue())) ) {
            			/* start new post data with two hyphens, boundary and line break */
            			net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(new String("--" + s_boundary + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK).getBytes(this.getSeed().getConfig().getOutEncoding()), a_postBytes);
            			/* add content disposition, parameter name and file name */
            			net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(new String("Content-Disposition: form-data; name=\"" + m_attachment.getKey() + "\"; filename=\"" + new java.io.File(m_attachment.getValue()).getName() + "\"" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK).getBytes(this.getSeed().getConfig().getOutEncoding()), a_postBytes);
            			/* add content type by guess with file name */
            			String s_fileGuessedContentType = java.net.URLConnection.guessContentTypeFromName(new java.io.File(m_attachment.getValue()).getName());
            			net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(new String("Content-Type: " + s_fileGuessedContentType + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK).getBytes(this.getSeed().getConfig().getOutEncoding()), a_postBytes);
            			/* add line break and flush print writer which indicates that file data is incoming */
            			net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(new String(net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK).getBytes(this.getSeed().getConfig().getOutEncoding()), a_postBytes);
            			/* copy file stream into dynamic byte list */
            			net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(net.forestany.forestj.lib.io.File.readAllBytes(m_attachment.getValue()), a_postBytes);
            			/* add line break and indicate end of boundary */
            			net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(new String(net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK).getBytes(this.getSeed().getConfig().getOutEncoding()), a_postBytes);
            		    
            		    										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFiner("attachment '" + m_attachment.getKey() + "' added with guessed content type: '" + s_fileGuessedContentType + "'");
            		} else {
            													net.forestany.forestj.lib.Global.ilogWarning("attachment file '" + m_attachment.getKey() + "' does not exist or is not a file: '" + m_attachment.getValue() + "'");
            		}
            	}
            	
        		/* add end of HTMLATTACHMENTS(multipart/form-data) */
            	net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(new String("--" + s_boundary + "--" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK).getBytes(this.getSeed().getConfig().getOutEncoding()), a_postBytes);
    		} else { /* no attachments available */
            	/* check if we have any request parameters */
            	if (this.m_requestParamters.size() > 0) {
            												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("iterate all request parameters with key->value pairs");
            		
            		String s_postBody = "";
            		
            		/* check amount of post request parameters */
            		if (this.m_requestParamters.size() > 0) {
	            		/* iterate all request parameters with key->value pairs */
	            		for (java.util.Map.Entry<String,Object> m_requestParameter : this.m_requestParamters.entrySet()) {
	            			/* add parameter name and value to post body */
	            			s_postBody += java.net.URLEncoder.encode( m_requestParameter.getKey(), this.getSeed().getConfig().getOutEncoding().displayName() ) + "=" + java.net.URLEncoder.encode( String.valueOf(m_requestParameter.getValue()), this.getSeed().getConfig().getOutEncoding().displayName() ) + "&";
	            			
	            													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFiner("request parameter '" + m_requestParameter.getKey() + "' added");
	            		}
	            		
	            		/* remove last '&' */
	            		s_postBody = s_postBody.substring(0, (s_postBody.length() - 1));
	            		
	            		/* add post body data to dynamic byte list */
	            		net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(new String(s_postBody).getBytes(this.getSeed().getConfig().getOutEncoding()), a_postBytes);
            		} else {
            			/* add empty post body data to dynamic byte list */
            			net.forestany.forestj.lib.Helper.addStaticByteArrayToDynamicByteList(new String(s_postBody + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK).getBytes(this.getSeed().getConfig().getOutEncoding()), a_postBytes);
            		}
            		
            												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("added all request parameters to post body data");
            	}
            }
            
            										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("finished preparing post data");
            										
            										if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("update content-length to '" + a_postBytes.size() + "'");
            
            this.getSeed().getRequestHeader().setContentLength(a_postBytes.size());
            										
            /* *** */
            /* end - preparing post data of web request 'POST' */
            /* *** */
        }
		
		/* ***** */
        /* start - preparing complete web request as byte array */
        /* ***** */
		
												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("add request header to https/REST request");

		/* create string temporary variable for https request header */
		String s_requestHeader = new String(this.getSeed().getRequestHeader().toString().getBytes(), this.getSeed().getConfig().getOutEncoding());
		
		/* create byte array for https request, including header and body length */
		byte[] a_foo = new byte[s_requestHeader.getBytes().length + ( (a_postBytes.size() > 0) ? a_postBytes.size() : 0 )];
		int i;
		
		/* copy each byte from request header to byte array */
		for (i = 0; i < s_requestHeader.getBytes().length; i++) {
			a_foo[i] = s_requestHeader.getBytes()[i];
		}
		
		if (a_postBytes.size() > 0) {
													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("add body data to https/REST request");
													
			/* copy each byte from post body to byte array */
			for (int j = 0; j < a_postBytes.size(); j++) {
				a_foo[j + i] = a_postBytes.get(j);
			}
		}
		
		/* ***** */
        /* end - preparing complete web request as byte array */
        /* ***** */
		
		/* return prepared https request as byte array for sending with TCP */
		return a_foo;
	}
	
	/**
	 * prepare SOAP request
	 * 
	 * @throws NullPointerException						object for SOAP request or wsdl instance are not available
	 * @throws IllegalArgumentException					content-length must be a positive integer
	 * @throws java.io.IOException						exception while encoding sending SOAP xml data
	 */
	private byte[] handleSOAP() throws NullPointerException, IllegalArgumentException, java.io.IOException {
		/* check if object for SOAP request is available */
		if (this.o_soapRequest == null) {
			throw new NullPointerException("No SOAP request object available for request");
		}
		
		/* check if wsdl instance is available in configuration */
		if (this.getSeed().getConfig().getWSDL() == null) {
			throw new NullPointerException("No WSDL configuration enabled for SOAP protocol");
		}
		
		String s_soapXml = "";
		
		/* encode object for SOAP request to xml data */
		try {
													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("encoding object for SOAP request to xml data");
			
			s_soapXml = this.getSeed().getConfig().getWSDL().getSchema().xmlEncode(this.o_soapRequest).replaceAll("<\\?(.*?)\\?>", "");
			
													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("encoded object for SOAP request to xml data");
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.ilogSevere("500 Internal Server Error: Exception while encoding sending SOAP xml data; " + o_exc);
    		throw new java.io.IOException("Exception while encoding sending SOAP xml data; " + o_exc);
		}
		
		/* wsdl types schema has target namespace */
		if (!net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getConfig().getWSDL().getSchema().getTargetNamespace() )) {
			/* adding target namespace to xml data */
			s_soapXml = s_soapXml.substring(0, s_soapXml.indexOf(">")) + " xmlns=\"" + this.getSeed().getConfig().getWSDL().getSchema().getTargetNamespace() + "\"" + s_soapXml.substring(s_soapXml.indexOf(">"));
		}
		
		/* no support for namespace initial on client side, we have no setting or property where we hold this information */
		/*} else if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(this.getSeed().getConfig().getWSDL().getSchema().getTargetNamespace())) && (!net.forestany.forestj.lib.Helper.isStringEmpty( NamespaceInitial )) ) {
			/* wsdl types schema has target namespace and namespace initial */
			
			/* adding target namespace and namespace initial to each xml-tag to xml data */
			/*s_soapXml = s_soapXml.substring(0, s_soapXml.indexOf(">")) + " xmlns:" + this.getSeed().getConfig().getWSDL().getSchema().getNamespaceVariable() + "=\"" + this.getSeed().getConfig().getWSDL().getSchema().getTargetNamespace() + "\"" + s_soapXml.substring(s_soapXml.indexOf(">"));
			
			s_soapXml = s_soapXml.replaceAll("</", "</" + this.getSeed().getConfig().getWSDL().getSchema().getNamespaceVariable() + ":");
			s_soapXml = s_soapXml.replaceAll("<", "<" + this.getSeed().getConfig().getWSDL().getSchema().getNamespaceVariable() + ":");
			s_soapXml = s_soapXml.replaceAll("<" + this.getSeed().getConfig().getWSDL().getSchema().getNamespaceVariable() + ":" + "/" + this.getSeed().getConfig().getWSDL().getSchema().getNamespaceVariable() + ":", "</" + this.getSeed().getConfig().getWSDL().getSchema().getNamespaceVariable() + ":");
		}*/
		
		/* prepare SOAP xml request */
		String s_xml = "<?xml version=\"1.0\" encoding=\"" + this.getSeed().getConfig().getOutEncoding().toString() + "\" ?>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		
		s_xml += "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		s_xml += "\t<soap:Body>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		
		/* add SOAP xml encoded data */
		s_xml += s_soapXml + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		
		s_xml += "\t</soap:Body>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		s_xml += "</soap:Envelope>";
		
		/* convert SOAP xml string to byte array */
		byte[] a_soapRequest = new String(s_xml).getBytes(this.getSeed().getConfig().getOutEncoding());
		/* set content type and content length */
		this.getSeed().getRequestHeader().setContentType(net.forestany.forestj.lib.net.https.Config.KNOWN_EXTENSION_LIST.get(".xsd") + "; charset=" + this.getSeed().getConfig().getOutEncoding().name());
		this.getSeed().getRequestHeader().setContentLength(a_soapRequest.length);
		
												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("add request header to SOAP request");
		
		/* create string temporary variable for SOAP request header */
		String s_requestHeader = new String(this.getSeed().getRequestHeader().toString().getBytes(), this.getSeed().getConfig().getOutEncoding());
		
		/* create byte array for SOAP request, including header and SOAP body length */
		byte[] a_foo = new byte[s_requestHeader.getBytes().length + a_soapRequest.length];
		int i;
		
		/* copy each byte from request header to byte array */
		for (i = 0; i < s_requestHeader.getBytes().length; i++) {
			a_foo[i] = s_requestHeader.getBytes()[i];
		}
		
												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("add body data to SOAP request");
		
		/* copy each byte from SOAP body to byte array */
		for (int j = 0; j < a_soapRequest.length; j++) {
			a_foo[j + i] = a_soapRequest[j];
		}
		
		/* return prepared SOAP request as byte array for sending with TCP */
		return a_foo;
	}
	
	/**
	 * prepare REST request
	 *
	 * @throws IllegalArgumentException					invalid download filename location or invalid content type
	 * @throws java.io.IOException						issues to check download filename location, url encoding data or reading all bytes from an attachment
	 * @throws java.io.UnsupportedEncodingException		if the configured outgoing encoding is not supported when encoding data
	 */
	private byte[] handleREST() throws IllegalArgumentException, java.io.IOException, java.io.UnsupportedEncodingException {
		/* using same method as for https request */
		return this.handleNormal();
	}

	/**
	 * receive response
	 */
	private int receiveResponse() {
		byte[] a_responseData = null;
		
												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("receive data from destination");
		
		/* receive https response */
		try {
			a_responseData = this.receiveBytes(-1);
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.ilogSevere("500 Internal Server Error: Exception while receiving data; " + o_exc.getMessage());
			return 500;
		}
		
		/* check if we have received anything at all */
		if ( (a_responseData == null) || (a_responseData.length < 1) ) {
			net.forestany.forestj.lib.Global.ilogWarning("204 No Content: HTTP request is null or empty");
			return 204;
		}
		
												if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("received data from destination");
		
		/* check if response does not exceed max. payload */
		if (a_responseData.length >= this.getSeed().getConfig().getMaxPayload() * 1024 * 1024) {
			net.forestany.forestj.lib.Global.ilogWarning("413 Payload Too Large: Reached max. payload of '" + net.forestany.forestj.lib.Helper.formatBytes(this.getSeed().getConfig().getMaxPayload() * 1024 * 1024) + "'");
			return 413;
		}
		
		/* get byte position in response data, where we have the border between http header and body data */
		int i_borderHeaderFromContent = net.forestany.forestj.lib.net.https.dynm.Dynamic.getNextLineBreak(a_responseData, this.getSeed().getConfig().getInEncoding(), 0, true, net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK);

		/* check if we have two line breaks */
		if (i_borderHeaderFromContent < 0) {
			net.forestany.forestj.lib.Global.ilogWarning("400 Bad Request: HTTP response does not contain two line breaks");
			return 400;
		} else {
			i_borderHeaderFromContent += net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK.length() * 2;
		}
		
		/* check if we have a response body */
		if (i_borderHeaderFromContent < a_responseData.length) {
			/* define response body byte array */
			this.getSeed().setResponseBody( new byte[a_responseData.length - i_borderHeaderFromContent] );
			
			/* get response body data */
			for (int i = i_borderHeaderFromContent; i < a_responseData.length; i++) {
				this.getSeed().getResponseBody()[i - i_borderHeaderFromContent] = a_responseData[i];
			}
		}
		
		byte[] a_headerData = new byte[i_borderHeaderFromContent - 4];
		
		/* get response header data */
		for (int i = 0; i < i_borderHeaderFromContent - 4; i++) {
			a_headerData[i] = a_responseData[i];
		}
		
		/* retrieve response header lines from header byte data, use incoming encoding setting */
		String[] a_responseHeaderLines = new String(a_headerData, this.getSeed().getConfig().getInEncoding()).split(net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK);
		
		/* read response header */
		int i_foo = this.getSeed().getResponseHeader().parseResponseHeader(a_responseHeaderLines);
		
		/* maybe we received header data, but our body data is still missing (content length > 0) */
		if ( (i_foo == 200) && (this.getSeed().getResponseBody() == null) && (this.getSeed().getResponseHeader().getContentLength() > 0) ) {
			
													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("try to receive body data again, cause header told us we have content length: " + this.getSeed().getResponseHeader().getContentLength());
			
			/* check if content length value from received header does not exceed max. payload value */
			if (this.getSeed().getResponseHeader().getContentLength() >= this.getSeed().getConfig().getMaxPayload() * 1024 * 1024) {
				net.forestany.forestj.lib.Global.ilogWarning("413 Payload Too Large: Reached max. payload of '" + net.forestany.forestj.lib.Helper.formatBytes(this.getSeed().getConfig().getMaxPayload() * 1024 * 1024) + "'");
				return 413;
			}
			
													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("receive data from destination");
			
			/* do another receive, now with content length as parameter */
			try {
				a_responseData = this.receiveBytes(this.getSeed().getResponseHeader().getContentLength());
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.ilogSevere("500 Internal Server Error: Exception while receiving data; " + o_exc.getMessage());
				return 500;
			}
			
			/* check if we have received body data */
			if ( (a_responseData == null) || (a_responseData.length < 1) ) {
				net.forestany.forestj.lib.Global.ilogWarning("204 No Content: HTTP request body is null or empty");
				return 204;
			}
			
													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("received data from destination");
			
			/* define response body byte array */
			this.getSeed().setResponseBody( new byte[a_responseData.length] );
			
			/* get response body data */
			for (int i = 0; i < a_responseData.length; i++) {
				this.getSeed().getResponseBody()[i] = a_responseData[i];
			}
		}
		
		/* store received cookies in current client instance for next request */
		if (this.getSeed().getConfig().getClientUseCookiesFromPreviousRequest()) {
			this.o_tempCookie = this.getSeed().getResponseHeader().getCookie();
		}
		
		/* if we have not return code 200, exit here */
		if (i_foo != 200) {
			return i_foo;
		}
		
		/* return received http status code */
		return this.getSeed().getResponseHeader().getReturnCode();
	}
	
	/**
	 * handle https response 
	 */
	private int handleNormalResponse() {
		/* receive https response */
		int i_foo = this.receiveResponse();
		
		/* if we have not return code 200, exit here */
		if (i_foo != 200) {
			return i_foo;
		}
		
		if (this.e_requestType == net.forestany.forestj.lib.net.http.RequestType.DOWNLOAD) {
													if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("writing received body data into file output stream");
			
			/* open file stream to download filename location */
			try (java.io.FileOutputStream o_fileOutputStream = new java.io.FileOutputStream(this.s_downloadFilename)) {
				/* write received body data to file */
				o_fileOutputStream.write(this.getSeed().getResponseBody());
				
				/* update return message */
				this.getSeed().getResponseHeader().setReturnMessage("File[" + ( (this.getSeed().getRequestHeader().getRequestPath().lastIndexOf("/") > 0) ? this.getSeed().getRequestHeader().getRequestPath().substring(this.getSeed().getRequestHeader().getRequestPath().lastIndexOf("/") + 1) : this.getSeed().getRequestHeader().getRequestPath() ) + "] downloaded to '" + this.s_downloadFilename + "'");
			} catch (Exception o_exc) {
														net.forestany.forestj.lib.Global.ilogWarning("exception occurred while writing response of web request to a file");
				
				/* exception occurred while writing response of web request to a file */
				this.getSeed().getResponseHeader().setReturnCode(500);
				this.getSeed().getResponseHeader().setReturnMessage("Error in web request - " + o_exc.getMessage());
			}
		}
		
		/* return received http status code */
		return this.getSeed().getResponseHeader().getReturnCode();
	}

	/**
	 * handle SOAP response 
	 */
	private int handleSOAPResponse() {
		/* receive SOAP response */
		int i_foo = this.receiveResponse();
		
		/* if we have not return code 200, exit here */
		if (i_foo != 200) {
			return i_foo;
		}
		
		if (this.getSeed().getResponseBody() != null) { /* continue if we have received SOAP xml data */
			java.nio.charset.Charset o_inCharset = null;
			
			/* check for incoming charset value in response header */
			if (this.getSeed().getResponseHeader().getContentType().contains("charset=")) {
														if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("read charset encoding out of response header");
				
				/* read charset encoding out of response header */
				String s_encoding = this.getSeed().getResponseHeader().getContentType().substring(this.getSeed().getRequestHeader().getContentType().indexOf("charset=") + 8);
				s_encoding = s_encoding.toLowerCase();
				
				/* delete double quotes */
				if ( (s_encoding.startsWith("\"")) && (s_encoding.endsWith("\"")) ) {
					s_encoding = s_encoding.substring(1, s_encoding.length() - 1);
				}
				
														if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("try to create a charset object with read charset encoding value: " + s_encoding);
				
				/* try to create a charset object with read charset encoding value */
				try {
					o_inCharset = java.nio.charset.Charset.forName(s_encoding);
				} catch (java.nio.charset.IllegalCharsetNameException | java.nio.charset.UnsupportedCharsetException o_exc) {
					net.forestany.forestj.lib.Global.ilogWarning("400 Bad Request: Invalid charset '" + s_encoding + "' within response in HTTP header content type '" + this.getSeed().getResponseHeader().getContentType() + "'; " + o_exc.getMessage());
					return 400;
				}
			} else { /* use configured incoming encoding property */
				o_inCharset = this.getSeed().getConfig().getInEncoding();
			}
			
			/* convert received SOAP response byte array to string with charset object */
			String s_soapResponse = new String(this.getSeed().getResponseBody(), o_inCharset);
			
			/* read all xml lines */
			s_soapResponse = s_soapResponse.replaceAll("[\\r\\n\\t]", "").replaceAll(">\\s*<", "><");
			
			/* clean up xml file */
			s_soapResponse = s_soapResponse.replaceAll("<\\?(.*?)\\?>", "");
			s_soapResponse = s_soapResponse.replaceAll("<!--(.*?)-->", "");
			s_soapResponse = s_soapResponse.replaceAll("<soap:", "<");
			s_soapResponse = s_soapResponse.replaceAll("</soap:", "</");
			
			/* validate xml */
			java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("(<[^<>]*?<[^<>]*?>|<[^<>]*?>[^<>]*?>)");
			java.util.regex.Matcher o_matcher = o_regex.matcher(s_soapResponse);
			
			/* if regex-matcher has match, the xml file is not valid */
		    while (o_matcher.find()) {
		        throw new IllegalArgumentException("Invalid xml-file. Please check xml-file at \"" + o_matcher.group(0) + "\".");
		    }
			
		    java.util.List<String> a_xmlTags = new java.util.ArrayList<String>();
		    
		    /* add all xml-tags to a list for parsing */
		    o_regex = java.util.regex.Pattern.compile("(<[^<>/]*?/>)|(<[^<>/]*?>[^<>]*?</[^<>/]*?>)|(<[^<>]*?>)|(</[^<>/]*?>)");
		    o_matcher = o_regex.matcher(s_soapResponse);
		    
		    /* save all xml tags in one array */
		    while (o_matcher.find()) {
		    	String s_xmlTag = o_matcher.group(0);
		    	
		    	/* xml-tag must start with '<' and ends with '>' */
		    	if ( (!s_xmlTag.startsWith("<")) && (!s_xmlTag.endsWith(">")) ) {
		    		throw new IllegalArgumentException("Invalid xml-tag. Please check xml-file at \"" + s_xmlTag + "\".");
		    	}
		    	
		    	a_xmlTags.add(s_xmlTag);
		    }
		    
		    /* first element must be 'Envelope' */
		    if (!a_xmlTags.get(0).startsWith("<Envelope")) {
		    	net.forestany.forestj.lib.Global.ilogWarning("400 Bad Request: SOAP response must start with 'Envelope'-tag");
				return 400;
		    }
		    
		    int i_bodyTag = -1;
		    
		    /* look for next 'Body'-tag within 'Envelope' */
		    for (int i = 1; i < a_xmlTags.size(); i++) {
		    	if (a_xmlTags.get(i).contentEquals("<Body>")) {
		    		i_bodyTag = i;
		    		break;
		    	}
		    }
		    
		    /* if we found no 'Body'-tag, abort here */
		    if (i_bodyTag < 0) {
		    	net.forestany.forestj.lib.Global.ilogWarning("400 Bad Request: SOAP response must have a 'Body'-tag");
				return 400;
		    }
		    
		    /* reset return variables */
		    this.o_soapFault = null;
		    this.o_soapResponse = null;
		    
		    /* found 'Fault'-tag within 'Body' so we do not receive our expected SOAP response, but a SOAP fault response */
		    if (a_xmlTags.get(i_bodyTag + 1).startsWith("<Fault")) {
		    	int i_faultEnd = -1;
		    	
		    	/* look for 'Fault'-ending-tag */
		    	for (int i = i_bodyTag + 1; i < a_xmlTags.size(); i++) {
					if (a_xmlTags.get(i).contentEquals("</Fault>")) {
						i_faultEnd = i;
						break;
					}
				}
		    	
		    	/* if 'Fault'-tag has not been closed, abort here */
		    	if (i_faultEnd < 0) {
			    	net.forestany.forestj.lib.Global.ilogWarning("400 Bad Request: SOAP response does not closed the 'Fault'-tag");
					return 400;
			    }
		    	
		    	String s_code = null;
		    	String s_message = null;
		    	String s_detail = null;
		    	String s_actor = null;
		    	
		    	/* read 'Fault'-children-tags */
		    	for (int i = i_bodyTag + 2; i < i_faultEnd; i++) {
		    		if (a_xmlTags.get(i).startsWith("<faultcode>")) { /* read fault code */
		    			s_code = a_xmlTags.get(i).substring(11, a_xmlTags.get(i).indexOf("</faultcode>"));
		    		} else if (a_xmlTags.get(i).startsWith("<faultstring>")) { /* read fault string */
		    			s_message = a_xmlTags.get(i).substring(13, a_xmlTags.get(i).indexOf("</faultstring>"));
		    		} else if (a_xmlTags.get(i).startsWith("<detail>")) { /* read fault detail */
		    			s_detail = a_xmlTags.get(i).substring(8, a_xmlTags.get(i).indexOf("</detail>"));
		    		} else if (a_xmlTags.get(i).startsWith("<faultactor>")) { /* read fault actor */
		    			s_actor = a_xmlTags.get(i).substring(12, a_xmlTags.get(i).indexOf("</faultactor>"));
		    		}
				}
		    	
		    											if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("create and store SOAP fault object");
		    	
		    	/* create and store SOAP fault object for later use */
		    	this.o_soapFault = new net.forestany.forestj.lib.net.https.soap.SoapFault(s_code, s_message, s_detail, s_actor);
		    } else { /* handle 'Body' data from SOAP response */
		    	int i_bodyEnd = -1;
		    	
		    	/* look for 'Body'-ending-tag */
		    	for (int i = i_bodyTag + 1; i < a_xmlTags.size(); i++) {
					if (a_xmlTags.get(i).contentEquals("</Body>")) {
						i_bodyEnd = i;
						break;
					}
				}
		    	
		    	/* if 'Body'-tag has not been closed, abort here */
		    	if (i_bodyEnd < 0) {
			    	net.forestany.forestj.lib.Global.ilogWarning("400 Bad Request: SOAP response does not closed the 'Body'-tag");
					return 400;
			    }
		    	
		    	java.util.List<String> a_soapBodyXmlTags = new java.util.ArrayList<String>();
			    
		    	/* gather all SOAP xml tags in our list */
		    	for (int i = i_bodyTag + 1; i < i_bodyEnd; i++) {
		    		a_soapBodyXmlTags.add(a_xmlTags.get(i));
		    	}
		    	
		    	/* clean up xml namespace stuff */
		    	if (a_soapBodyXmlTags.get(0).contains(" xmlns:")) {
		    		String s_namespaceVar = a_soapBodyXmlTags.get(0).substring(1 , a_soapBodyXmlTags.get(0).indexOf(":"));
					
		    		/* remove xmlns attribute */
		    		if ( a_soapBodyXmlTags.get(0).substring( a_soapBodyXmlTags.get(0).indexOf(" xmlns:") + 7 ).indexOf(" ") >= 0) {
		    			String s_one = a_soapBodyXmlTags.get(0).substring(0, a_soapBodyXmlTags.get(0).indexOf(" xmlns:"));
		    			String s_two = a_soapBodyXmlTags.get(0).substring( a_soapBodyXmlTags.get(0).indexOf(" xmlns:") + 7 );
		    			
		    			a_soapBodyXmlTags.set(0, s_one + s_two.substring(s_two.indexOf(" ")));
		    		} else {
		    			String s_one = a_soapBodyXmlTags.get(0).substring(0, a_soapBodyXmlTags.get(0).indexOf(" xmlns:"));
		    			String s_two = a_soapBodyXmlTags.get(0).substring( a_soapBodyXmlTags.get(0).indexOf(" xmlns:") );
		    			
		    			a_soapBodyXmlTags.set(0, s_one + s_two.substring(s_two.indexOf(">")));
		    		}
		    		
		    		for (int i = 0; i < a_soapBodyXmlTags.size(); i++) {
		    			a_soapBodyXmlTags.set(i, a_soapBodyXmlTags.get(i).replace("<" + s_namespaceVar + ":", "<").replace("</" + s_namespaceVar + ":", "</"));
		    		}
		    	} else if (a_soapBodyXmlTags.get(0).contains(" xmlns=")) {
		    		/* remove xmlns attribute */
		    		if ( a_soapBodyXmlTags.get(0).substring( a_soapBodyXmlTags.get(0).indexOf(" xmlns=") + 7 ).indexOf(" ") >= 0) {
		    			String s_one = a_soapBodyXmlTags.get(0).substring(0, a_soapBodyXmlTags.get(0).indexOf(" xmlns="));
		    			String s_two = a_soapBodyXmlTags.get(0).substring( a_soapBodyXmlTags.get(0).indexOf(" xmlns=") + 7 );
		    			
		    			a_soapBodyXmlTags.set(0, s_one + s_two.substring(s_two.indexOf(" ")));
		    		} else {
		    			String s_one = a_soapBodyXmlTags.get(0).substring(0, a_soapBodyXmlTags.get(0).indexOf(" xmlns="));
		    			String s_two = a_soapBodyXmlTags.get(0).substring( a_soapBodyXmlTags.get(0).indexOf(" xmlns=") );
		    			
		    			a_soapBodyXmlTags.set(0, s_one + s_two.substring(s_two.indexOf(">")));
		    		}
		    	}
		    	
		    											if (this.b_useLog) net.forestany.forestj.lib.Global.ilogFine("decode received and cleaned up SOAP xml data");
		    	
		    	/* decode received and cleaned up SOAP xml data to an object and store it as SOAP response property */
		    	try {
		    		this.o_soapResponse = this.getSeed().getConfig().getWSDL().getSchema().xmlDecode(a_soapBodyXmlTags);
		    	} catch (Exception o_exc) {
		    		net.forestany.forestj.lib.Global.ilogSevere("500 Internal Server Error: Exception while decoding received SOAP xml data; " + o_exc);
		    		return 500;
		    	}
		    }
		} else {
			net.forestany.forestj.lib.Global.ilogSevere("500 Internal Server Error: no SOAP xml data received");
    		return 500;
		}
		
		/* return received http status code */
		return this.getSeed().getResponseHeader().getReturnCode();
	}

	/**
	 * handle REST response
	 */
	private int handleRESTResponse() {
		/* using same method as for https response */
		return this.handleNormalResponse();
	}
}
