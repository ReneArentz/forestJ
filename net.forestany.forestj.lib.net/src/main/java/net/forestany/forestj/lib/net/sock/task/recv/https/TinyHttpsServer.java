package net.forestany.forestj.lib.net.sock.task.recv.https;

/**
 * Task class to create a tiny https server. You can configure this instance as https, soap or rest server which can receive request and send responses to the clients. All internal data is handled in properties within Seed instance. Only TCP supported.
 * Only HTTP/1.0 or HTTP/1.1.
 * 
 * @param <T>	java socket class parameter
 */
public class TinyHttpsServer<T extends javax.net.ssl.SSLServerSocket> extends net.forestany.forestj.lib.net.sock.task.Task<javax.net.ssl.SSLServerSocket> {
	
	/* Fields */
	
	private net.forestany.forestj.lib.net.https.Seed o_seed;
	private net.forestany.forestj.lib.net.https.dynm.Dynamic o_dynamic;
	private Object o_soapResponse;
	private String s_soapTargetNamespace;
	private String s_soapNamespaceInitial;
	
	/* Properties */
	
	/**
	 * get seed instance
	 * 
	 * @return net.forestany.forestj.lib.net.https.Seed
	 */
	public net.forestany.forestj.lib.net.https.Seed getSeed() {
		return this.o_seed;
	}
	
	/* Methods */
	
	/**
	 * Standard constructor, initializing all values
	 */
	public TinyHttpsServer() {
		super();
		
		this.o_seed = null;
		this.o_dynamic = null;
		this.o_soapResponse = null;
		this.s_soapTargetNamespace = null;
		this.s_soapNamespaceInitial = null;
	}
	
	/**
	 * Creating tiny https server task instance with all it's settings via configuration parameter
	 * 
	 * @param p_o_config					configuration instance parameter
	 * @throws NullPointerException			configuration instance parameter is null, or domain parameter from configuration is null or empty
	 */
	public TinyHttpsServer(net.forestany.forestj.lib.net.https.Config p_o_config) throws NullPointerException {
		super(net.forestany.forestj.lib.net.sock.Type.TCP);
		
		this.o_seed = new net.forestany.forestj.lib.net.https.Seed(p_o_config);
		this.o_dynamic = null;
		this.o_soapResponse = null;
		this.s_soapTargetNamespace = null;
		this.s_soapNamespaceInitial = null;
		this.setReceiveMaxUnknownAmountInMiB(this.getSeed().getConfig().getMaxPayload());
		this.setDebugNetworkTrafficOn(this.getSeed().getConfig().getDebugNetworkTrafficOn());
	}
	
	/**
	 * Method to get socket task class type for creating new instances with declared constructor
	 * @return		class type of socket task as Class&lt;?&gt;
	 */
	public Class<?> getSocketTaskClassType() {
		return TinyHttpsServer.class;
	}
	
	/**
	 * Method to clone this socket task with another socket task instance
	 * @param p_o_sourceTask		another socket task instance as source for all it's parameters and settings
	 */
	public void cloneFromOtherTask(net.forestany.forestj.lib.net.sock.task.Task<javax.net.ssl.SSLServerSocket> p_o_sourceTask) {
		this.cloneBasicFields(p_o_sourceTask);
		
		/* ignore exceptions if a property of source task has no valid value, we will keep it null */
		try { this.o_seed = ( ((TinyHttpsServer<javax.net.ssl.SSLServerSocket>)p_o_sourceTask) ).getSeed(); } catch (Exception o_exc) { /* NOP */ }
	}
	
	/**
	 * runTask method of receiving https/soap/rest requests
	 * 
	 * @throws Exception	any exception of implementation that could happen will be caught by abstract Task class, see details in protocol methods in net.forestany.forestj.lib.net.sock.task.Task
	 */
	protected void runTask() throws Exception {
		/* only TCP supported */
		if (this.e_type == net.forestany.forestj.lib.net.sock.Type.TCP) {
			try {
				boolean b_keepAlive = false;
				
				/* repeat until keep alive is set to false */
				do {
					/* re-initialize variables */
					this.o_seed = new net.forestany.forestj.lib.net.https.Seed(this.getSeed().getConfig());
					this.o_dynamic = null;
					this.o_soapResponse = null;
					this.s_soapTargetNamespace = null;
					this.s_soapNamespaceInitial = null;
					
					/* generate new salt value */
					this.getSeed().setSalt( net.forestany.forestj.lib.Helper.generateRandomString(8, net.forestany.forestj.lib.Helper.ALPHANUMERIC_CHARACTERS) );
					
					if (b_keepAlive) {
						b_keepAlive = false;
					}
					
					/* check if Seed instance is available */
					if (this.getSeed() == null) {
						throw new IllegalArgumentException("Configuration for tiny https server is not specified");
					}
					
					/* check if domain property is configured */
					if (net.forestany.forestj.lib.Helper.isStringEmpty(this.getSeed().getConfig().getDomain())) {
						throw new IllegalArgumentException("Configuration for tiny https server must specify a domain value");
					}
					
					/* get request source address and port */
					java.net.InetAddress o_requestSourceAddress = ((java.net.Socket)this.o_socket.getSocket()).getInetAddress();
					int i_requestSourcePort = ((java.net.Socket)this.o_socket.getSocket()).getPort();
					
															net.forestany.forestj.lib.Global.ilog(this.getSeed().getSalt() + " " + "handle incoming socket communication from " + o_requestSourceAddress.getHostAddress() + ":" + i_requestSourcePort);
		
															net.forestany.forestj.lib.Global.ilogFine(this.getSeed().getSalt() + " " + "handle request");
					
					/* handle incoming request */
					this.getSeed().setReturnCode( this.handleRequest(o_requestSourceAddress) );
					
															net.forestany.forestj.lib.Global.ilogFine(this.getSeed().getSalt() + " " + "handled request");
					
					/* log POST data - because of DATA PROTECTION not active */									
					/*if (this.getSeed().getPostData().size() > 0) {
																net.forestany.forestj.lib.Global.ilogMass(this.getSeed().getSalt());
																net.forestany.forestj.lib.Global.ilogMass(this.getSeed().getSalt() + " " + "Post data");
																net.forestany.forestj.lib.Global.ilogMass(this.getSeed().getSalt());
						
						this.getSeed().getPostData().entrySet().forEach(o_postDataEntry -> {
																	net.forestany.forestj.lib.Global.ilogMass(this.getSeed().getSalt() + " " + o_postDataEntry.getKey() + " = " + o_postDataEntry.getValue());
						});
						
																net.forestany.forestj.lib.Global.ilogMass(this.getSeed().getSalt());
					}*/
					
					/* log FILE data - because of DATA PROTECTION not active */
					/*if (this.getSeed().getFileData().size() > 0) {
																net.forestany.forestj.lib.Global.ilogMass(this.getSeed().getSalt());
																net.forestany.forestj.lib.Global.ilogMass(this.getSeed().getSalt() + " " + "File data");
																net.forestany.forestj.lib.Global.ilogMass(this.getSeed().getSalt());
						
						this.getSeed().getFileData().forEach(o_fileDataEntry -> {
																	net.forestany.forestj.lib.Global.ilogMass(this.getSeed().getSalt() + " " + o_fileDataEntry.getFieldName() + " | " + o_fileDataEntry.getFileName() + " | " + o_fileDataEntry.getContentType() + " | " + o_fileDataEntry.getFileData().length);
						});
						
																net.forestany.forestj.lib.Global.ilogMass(this.getSeed().getSalt());
					}*/
					
															net.forestany.forestj.lib.Global.ilog(this.getSeed().getSalt() + " " + "sending response [" + this.getSeed().getReturnCode() + "] for '" + this.getSeed().getRequestHeader().getRequestPath() + "' to [" + o_requestSourceAddress.getHostAddress() + ":" + i_requestSourcePort + "]");
					
					this.sendResponse();
					
															net.forestany.forestj.lib.Global.ilog(this.getSeed().getSalt() + " " + "sent response");
					
					if (!this.getSeed().getResponseHeader().getConnectionKeepAlive()) {
						/* closing connection, because we are not keeping the connection alive */
						((java.net.Socket)this.o_socket.getSocket()).close();
						
																net.forestany.forestj.lib.Global.ilogFine(this.getSeed().getSalt() + " " + "socket communication closed with [" + ((java.net.Socket)this.o_socket.getSocket()).getPort() + "]");
					} else {
						/* not recommended */
						b_keepAlive = true;
					}
				} while (b_keepAlive);
			} catch (Exception o_exc) {
				net.forestany.forestj.lib.Global.ilogSevere(this.getSeed().getSalt() + " " + "Exception runTask: " + o_exc);
				
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
	 * handle incoming https/soap/rest request
	 * 
	 * @param p_o_requestSourceAddress			InetAddres parameter object for ipv4 check 
	 * @return									http status return code, e.g. 200 or 404
	 */
	private int handleRequest(java.net.InetAddress p_o_requestSourceAddress) {
		byte[] a_receivedData = null;
		
												net.forestany.forestj.lib.Global.ilogFine(this.getSeed().getSalt() + " " + "receive data from client");
		
		/* receive https response */
		try {
			a_receivedData = this.receiveBytes(-1);
		} catch (Exception o_exc) {
			net.forestany.forestj.lib.Global.ilogSevere(this.getSeed().getSalt() + " " + "500 Internal Server Error: Exception while receiving data; " + o_exc.getMessage());
			return 500;
		}
		
												net.forestany.forestj.lib.Global.ilogFine(this.getSeed().getSalt() + " " + "received data from client");
		
		/* check if request does not exceed max. payload */
		if ( (a_receivedData != null) && (a_receivedData.length >= this.getSeed().getConfig().getMaxPayload() * 1024 * 1024) ) {
			net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "413 Payload Too Large: Reached max. payload of '" + net.forestany.forestj.lib.Helper.formatBytes(this.getSeed().getConfig().getMaxPayload() * 1024 * 1024) + "'");
			return 413;
		}
				
		/* get byte position in request data, where we have the border between http header and body data */
		int i_borderHeaderFromContent = net.forestany.forestj.lib.net.https.dynm.Dynamic.getNextLineBreak(a_receivedData, this.getSeed().getConfig().getInEncoding(), 0, true, net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK);

		/* check if we have two line breaks */
		if (i_borderHeaderFromContent < 0) {
			net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: HTTP request does not contain two line breaks");
			return 400;
		} else {
			i_borderHeaderFromContent += net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK.length() * 2;
		}
		
		/* check if we have a request body */
		if (i_borderHeaderFromContent < a_receivedData.length) {
			/* define request body byte array */
			this.getSeed().setRequestBody(new byte[a_receivedData.length - i_borderHeaderFromContent]);
			
			/* get request body data */
			for (int i = i_borderHeaderFromContent; i < a_receivedData.length; i++) {
				this.getSeed().getRequestBody()[i - i_borderHeaderFromContent] = a_receivedData[i];
			}
		}
		
		byte[] a_headerData = new byte[i_borderHeaderFromContent - 4];
		
		/* get request header data */
		for (int i = 0; i < i_borderHeaderFromContent - 4; i++) {
			a_headerData[i] = a_receivedData[i];
		}
		
		/* retrieve request header lines from header byte data, use incoming encoding setting */
		String[] a_requestHeaderLines = new String(a_headerData, this.getSeed().getConfig().getInEncoding()).split(net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK);
		
		/* read request header, do not automatically return 301 in SOAP or REST mode */
		int i_foo = this.getSeed().getRequestHeader().parseRequestHeader(a_requestHeaderLines, ( (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.SOAP) || (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.REST) ));
		
		/* if we have not return code 200, exit here */
		if (i_foo != 200) {
			return i_foo;
		}
		
		/* check incoming ip address */
		if (this.getSeed().getConfig().getAllowSourceList().size() > 0) {
													net.forestany.forestj.lib.Global.ilogFine(this.getSeed().getSalt() + " " + "check if incoming ip address '" + p_o_requestSourceAddress.getHostAddress() + "' is allowed");
			
			/* check if we have a ipv4 address, because ipv6 is not implemented yet */
			if (p_o_requestSourceAddress instanceof java.net.Inet4Address) {
				boolean b_allowed = false;
				
				/* iterate each source address which is allowed */
				for (String s_allowAddress : this.getSeed().getConfig().getAllowSourceList()) {
					if (net.forestany.forestj.lib.Helper.isIpv4Address(s_allowAddress)) { /* only one ipv4 address */
						if (p_o_requestSourceAddress.getHostAddress().contentEquals(s_allowAddress)) {
							/* our incoming address matches an allowed address */
							b_allowed = true;
							break;
						}
					} else if (net.forestany.forestj.lib.Helper.isIpv4AddressWithSuffix(s_allowAddress)) { /* ipv4 address with suffix */
						if (net.forestany.forestj.lib.Helper.isIpv4WithinRange(p_o_requestSourceAddress.getHostAddress(), s_allowAddress)) {
							/* our incoming address is within allowed range */
							b_allowed = true;
							break;
						}
					}
				}
				
				/* if incoming address is not allowed */
				if (!b_allowed) {
					net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "403 Forbidden: HTTP request source address is not in allow list");
					return 403;
				}
			} else {
				net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "403 Forbidden: HTTP request source address is not IPv4, compability with IPv4 allow list is not implemented");
				return 403;
			}
		}
		
		/* check if accept-charset value contains configured out encoding charset of tiny https server */
		if (!net.forestany.forestj.lib.Helper.isString(this.getSeed().getRequestHeader().getAcceptCharset())) {
			if ( ! this.getSeed().getRequestHeader().getAcceptCharset().toLowerCase().contains( this.getSeed().getConfig().getOutEncoding().name().toLowerCase()) ) {
				net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: HTTP request accept-charset does not contain server response charset '" + this.getSeed().getConfig().getOutEncoding().name() + " != " + this.getSeed().getRequestHeader().getAcceptCharset() + "'");
				return 400;
			}
		}
		
		/* check root directory */
		if ( !net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getConfig().getRootDirectory() ) ) {
			/* check if root directory really exists */
			if (!net.forestany.forestj.lib.io.File.folderExists( this.getSeed().getConfig().getRootDirectory() )) {
				net.forestany.forestj.lib.Global.ilogSevere(this.getSeed().getSalt() + " " + "500 Internal Server Error: root directory '" + this.getSeed().getConfig().getRootDirectory() + "' does not exist");
				return 500;
			}
		} else {
			/* root directory is required */
			net.forestany.forestj.lib.Global.ilogSevere(this.getSeed().getSalt() + " " + "500 Internal Server Error: no root directory configured for tiny https server");
			return 500;
		}
		
		/* check session directory */
		if ( !net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getConfig().getSessionDirectory() ) ) {
			/* check if session directory really exists */
			if (!net.forestany.forestj.lib.io.File.folderExists( this.getSeed().getConfig().getSessionDirectory() )) {
				net.forestany.forestj.lib.Global.ilogSevere(this.getSeed().getSalt() + " " + "500 Internal Server Error: session directory '" + this.getSeed().getConfig().getSessionDirectory() + "' does not exist");
				return 500;
			}
			
			/* check if session directory is not a sub directory of root directory, if root directory is configured */
			if ( ( !net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getConfig().getRootDirectory() ) ) && (net.forestany.forestj.lib.io.File.isSubDirectory( this.getSeed().getConfig().getSessionDirectory(), this.getSeed().getConfig().getRootDirectory() )) ) {
				net.forestany.forestj.lib.Global.ilogSevere(this.getSeed().getSalt() + " " + "500 Internal Server Error: session directory '" + this.getSeed().getConfig().getSessionDirectory() + "' is a sub directory of root '" + this.getSeed().getConfig().getRootDirectory() + "'. This is not allowed because of security vulnerability");
				return 500;
			}
		} else if (!this.getSeed().getConfig().getNotUsingCookies()) {
			/* session directory is required if cookies are used */
			net.forestany.forestj.lib.Global.ilogSevere(this.getSeed().getSalt() + " " + "500 Internal Server Error: no session directory configured for tiny https server");
			return 500;
		}
	
		/* tiny https server support dynamic communication with GET and POST */
		if ( (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.DYNAMIC) || (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.REST) ) {
			/* check if we accept session cookies */
			if (!this.getSeed().getConfig().getNotUsingCookies()) {
														net.forestany.forestj.lib.Global.ilogFine(this.getSeed().getSalt() + " " + "handle session cookies");
														
				String s_cookieUUID = null;
				
				/* check if our request has a session cookie */
				if (!net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getRequestHeader().getCookie() )) {
					String s_cookie = this.getSeed().getRequestHeader().getCookie();
					
					/* check if our cookie key for session UUID is available, otherwise there will be an new session cookie and a new session file */
					if (s_cookie.contains("forestAny-UUID")) {
						/* split cookie line with ';' to get our cookie key */
						String[] a_cookieLine = s_cookie.split(";");
						
						/* iterate each cookie key value pair */
						for (String s_foo : a_cookieLine) {
							/* store our cookie key value pair */
							if (s_foo.trim().startsWith("forestAny-UUID")) {
								s_cookie = s_foo.trim();
								break;
							}
						}
						
						/* check if it is really our cookie key value pair */
						if (!s_cookie.contains("=")) {
							net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: cannot detect cookie key value pair with '='-sign in '" + this.getSeed().getRequestHeader().getCookie() + "'");
							return 400;
						}
						
						/* split cookie key and value by '=' */
						String[] a_foo = s_cookie.split("=");
						
						/* cookie key name must be 'forestAny-UUID' */
						if (!a_foo[0].contentEquals("forestAny-UUID")) {
							net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: expected cookie key 'forestAny-UUID' with UUID value, but received '" + s_cookie + "'");
							return 400;
						}
						
						/* store cookie uuid value within variable */
						s_cookieUUID = a_foo[1];
					}
				}
				
				/* start setting cookie properties for response, cookie instance, http only and strict */
				this.getSeed().getResponseHeader().setCookie( new net.forestany.forestj.lib.net.https.dynm.Cookie(s_cookieUUID, this.getSeed().getConfig().getSessionMaxAge(), this.getSeed().getConfig().getDomain().replace("https://", "")) );
				this.getSeed().getResponseHeader().getCookie().setHttpOnly(true);
				this.getSeed().getResponseHeader().getCookie().setSameSite(net.forestany.forestj.lib.net.https.dynm.CookieSameSite.STRICT);
				
				/* control age of session files */
				try {
					/* iterate each session file in session directory */
					for (net.forestany.forestj.lib.io.ListingElement o_file : net.forestany.forestj.lib.io.File.listDirectory(this.getSeed().getConfig().getSessionDirectory())) {
						/* get file name */
						String s_sessionFile = o_file.getFullName();
						/* open session file */
						net.forestany.forestj.lib.io.File o_sessionFile = new net.forestany.forestj.lib.io.File(s_sessionFile, false);
						/* read session age from line 1 */
						java.time.LocalDateTime o_cookieAge = net.forestany.forestj.lib.Helper.fromISO8601UTC( o_sessionFile.readLine(1) );
						
						/* check if age of session file is to old -> delete file */
						if ( o_cookieAge.isBefore( java.time.LocalDateTime.now().minusSeconds( this.getSeed().getConfig().getSessionMaxAge().toDurationInSeconds() ) ) ) {
																	net.forestany.forestj.lib.Global.ilogFiner(this.getSeed().getSalt() + " " + "delete session file '" + s_sessionFile + "', [" + o_cookieAge + "] < [" + java.time.LocalDateTime.now().minusSeconds( this.getSeed().getConfig().getSessionMaxAge().toDurationInSeconds() ) + "]");
							
							net.forestany.forestj.lib.io.File.deleteFile(s_sessionFile);
						}
					}
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.ilogSevere(this.getSeed().getSalt() + " " + "500 Internal Server Error: could not list session directory, control age of session file or delete session file, exception message: " + o_exc.getMessage());
					return 500;
				}
				
				/* create session file name by session directory and cookie uuid */
				String s_sessionFile = this.getSeed().getConfig().getSessionDirectory() + this.getSeed().getResponseHeader().getCookie().getCookieUUID() + ".txt";
				
				/* try to read session file */
				try {
					/* check if session file exists, if not it was delete because of cookie age */
					if (net.forestany.forestj.lib.io.File.exists(s_sessionFile)) {
						/* read session file */
						net.forestany.forestj.lib.io.File o_sessionFile = new net.forestany.forestj.lib.io.File(s_sessionFile, false);
						
						/* iterate each line, starting in line 2 */
						for (int i = 2; i <= o_sessionFile.getFileLines(); i++) {
							/* read line */
							String s_line = o_sessionFile.readLine(i);
							
							if (s_line.contains("=")) {
								/* split session line in key and value by '=' */
								String[] a_lineValues = s_line.split("=");
								/* add session key and value to our internal session data map */
								this.getSeed().getSessionData().put( a_lineValues[0].replace("&equal;", "="), a_lineValues[1].replace("&equal;", "="));
							}
						}
					}
				} catch (Exception o_exc) {
					net.forestany.forestj.lib.Global.ilogSevere(this.getSeed().getSalt() + " " + "500 Internal Server Error: could not read/write session file '" + s_sessionFile + "', exception message: " + o_exc.getMessage());
					return 500;
				}
			}
		}
		
		/* handle HTTP methods with the predicted modes */
		if ( (this.getSeed().getRequestHeader().getMethod().contentEquals("GET")) && ( (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.NORMAL) || (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.DYNAMIC) ) ) {
													net.forestany.forestj.lib.Global.ilog(this.getSeed().getSalt() + " " + "GET '" + this.getSeed().getRequestHeader().getRequestPath() + "' from " + ((java.net.Socket)this.o_socket.getSocket()).getInetAddress().getHostAddress() + ":" + ((java.net.Socket)this.o_socket.getSocket()).getPort());
				
			/* prepare returning requested file */
			return this.handleFileRequest();
		} else if ( (this.getSeed().getRequestHeader().getMethod().contentEquals("POST")) && (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.DYNAMIC) ) {
													net.forestany.forestj.lib.Global.ilog(this.getSeed().getSalt() + " " + "POST '" + this.getSeed().getRequestHeader().getRequestPath() + "' from " + ((java.net.Socket)this.o_socket.getSocket()).getInetAddress().getHostAddress() + ":" + ((java.net.Socket)this.o_socket.getSocket()).getPort());
			
			/* for POST request, we need body data after header lines */
			if ( (this.getSeed().getRequestBody() == null) || (this.getSeed().getRequestBody().length < 1) ) {
				net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "204 No Content: HTTPS request is null or empty");
				return 204;
			}
			
			/* create dynamic instance */
			this.o_dynamic = new net.forestany.forestj.lib.net.https.dynm.Dynamic(this.getSeed());
			/* handle body data from request */
			i_foo = this.o_dynamic.handlePostRequest();
			
			if (i_foo != 200) { /* if something happened while handling body data, abort here */
				return i_foo;
			} else { /* prepare returning requested file */
				return this.handleFileRequest();
			}
		} else if ( ( (this.getSeed().getRequestHeader().getMethod().contentEquals("GET")) || (this.getSeed().getRequestHeader().getMethod().contentEquals("POST")) ) && (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.SOAP) ) {
													net.forestany.forestj.lib.Global.ilog(this.getSeed().getSalt() + " " + this.getSeed().getRequestHeader().getMethod() + " '" + this.getSeed().getRequestHeader().getRequestPath() + "' from " + ((java.net.Socket)this.o_socket.getSocket()).getInetAddress().getHostAddress() + ":" + ((java.net.Socket)this.o_socket.getSocket()).getPort());
			
			/* check if we have received anything at all for SOAP POST request */
			if ( (this.getSeed().getRequestHeader().getMethod().contentEquals("POST")) && ((this.getSeed().getRequestBody() == null) || (this.getSeed().getRequestBody().length < 1)) ) {
				net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "204 No Content: SOAP request is null or empty");
				return 204;
			}
				
			/* handling SOAP request */
			return this.handleSOAPRequest();
		} else if ( 
				(
					(this.getSeed().getRequestHeader().getMethod().contentEquals("GET")) ||
					(this.getSeed().getRequestHeader().getMethod().contentEquals("POST")) ||
					(this.getSeed().getRequestHeader().getMethod().contentEquals("PUT")) ||
					(this.getSeed().getRequestHeader().getMethod().contentEquals("DELETE"))
				)
			&&
				(this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.REST)
		) {
													net.forestany.forestj.lib.Global.ilog(this.getSeed().getSalt() + " " + this.getSeed().getRequestHeader().getMethod() + " '" + this.getSeed().getRequestHeader().getRequestPath() + "' from " + ((java.net.Socket)this.o_socket.getSocket()).getInetAddress().getHostAddress() + ":" + ((java.net.Socket)this.o_socket.getSocket()).getPort());
			
			/* for GET request and REST mode, we already parsed request header and can continue directly with preparing the response */										
			if (!this.getSeed().getRequestHeader().getMethod().contentEquals("GET")) {
				boolean b_skipHandlePost = false;
				
				/* we have no body data in our request */
				if ( (this.getSeed().getRequestBody() == null) || (this.getSeed().getRequestBody().length < 1) ) {
					/* DELETE method does not force body data, but could work with it if the implementation wants to */
					if (this.getSeed().getRequestHeader().getMethod().contentEquals("DELETE")) {
						b_skipHandlePost = true;
					} else { /* POST and PUT need body data to proceed */
						net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "204 No Content: REST request is null or empty");
						return 204;
					}
				}
				
				if (!b_skipHandlePost) {
					/* create dynamic instance */
					this.o_dynamic = new net.forestany.forestj.lib.net.https.dynm.Dynamic(this.getSeed());
					/* handle body data from request */
					i_foo = this.o_dynamic.handlePostRequest();
					
					/* if something happened while handling body data, abort here */
					if (i_foo != 200) {
						return i_foo;
					}
				}
			}
			
			return 200;
		} else {
			net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "501 Not Implemented: HTTP method " + this.getSeed().getRequestHeader().getMethod() + " with Mode " + this.getSeed().getConfig().getMode() + " not implemented");
			return 501;
		}
	}

	/**
	 * prepare returning requested file
	 */
	private int handleFileRequest() {
		String s_fileAbsolutePath = null;
		String s_extension = null;
		
		if (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getRequestHeader().getFile() )) { /* we have no file value in request header */
			if ( (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getRequestHeader().getPath() )) && (net.forestany.forestj.lib.io.File.exists(this.getSeed().getConfig().getRootDirectory() + "index.html")) ) {
				/* our root directory contains a 'index.html' file */
				s_fileAbsolutePath = this.getSeed().getConfig().getRootDirectory() + "index.html";
				s_extension = ".html";
			} else if ( (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getRequestHeader().getPath() )) && (net.forestany.forestj.lib.io.File.exists(this.getSeed().getConfig().getRootDirectory() + "index.htm")) ) {
				/* our root directory contains a 'index.htm' file */
				s_fileAbsolutePath = this.getSeed().getConfig().getRootDirectory() + "index.htm";
				s_extension = ".htm";
			} else if (net.forestany.forestj.lib.io.File.exists(this.getSeed().getConfig().getRootDirectory() + this.getSeed().getRequestHeader().getPath() + net.forestany.forestj.lib.io.File.DIR + "index.html")) {
				/* our requested path contains a 'index.html' file */
				s_fileAbsolutePath = this.getSeed().getConfig().getRootDirectory() + this.getSeed().getRequestHeader().getPath() + net.forestany.forestj.lib.io.File.DIR + "index.html";
				s_extension = ".html";
			} else if (net.forestany.forestj.lib.io.File.exists(this.getSeed().getConfig().getRootDirectory() + this.getSeed().getRequestHeader().getPath() + net.forestany.forestj.lib.io.File.DIR + "index.htm")) {
				/* our requested path contains a 'index.htm' file */
				s_fileAbsolutePath = this.getSeed().getConfig().getRootDirectory() + this.getSeed().getRequestHeader().getPath() + net.forestany.forestj.lib.io.File.DIR + "index.htm";
				s_extension = ".htm";
			} else {
				net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "404 Not Found: Resource 'index.html' or 'index.htm' not found");
				return 404;
			}
			
			/* update file value in request header */
			this.getSeed().getRequestHeader().setFile("index" + s_extension);
		} else {
			boolean b_hasAllowedExtension = false;
			
			/* check for valid extension of requested file */
			for (java.util.Map.Entry<String, String> o_allowExtension : this.getSeed().getConfig().getAllowExtensionList().entrySet()) {
				if (this.getSeed().getRequestHeader().getFile().endsWith(o_allowExtension.getKey())) {
					/* file extension found in allow list */
					b_hasAllowedExtension = true;
					s_extension = o_allowExtension.getKey();
					break;
				}
			}
			
			/* return 403 if file extension is forbidden */
			if (!b_hasAllowedExtension) {
				net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "403 Forbidden: Resource '" + this.getSeed().getRequestHeader().getPath() + net.forestany.forestj.lib.io.File.DIR + this.getSeed().getRequestHeader().getFile() + "'");
				return 403;
			}
			
			if (net.forestany.forestj.lib.io.File.exists(this.getSeed().getConfig().getRootDirectory() + this.getSeed().getRequestHeader().getPath() + net.forestany.forestj.lib.io.File.DIR + this.getSeed().getRequestHeader().getFile())) {
				/* safe absolute path to requested file */
				s_fileAbsolutePath = this.getSeed().getConfig().getRootDirectory() + this.getSeed().getRequestHeader().getPath() + net.forestany.forestj.lib.io.File.DIR + this.getSeed().getRequestHeader().getFile();
			} else {
				/* requested file does not exist */
				net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "404 Not Found: Resource '" + this.getSeed().getRequestHeader().getPath() + net.forestany.forestj.lib.io.File.DIR + this.getSeed().getRequestHeader().getFile() + "'");
				return 404;
			}
		}
		
		/* get content type by file extension */
		String s_contentType = net.forestany.forestj.lib.net.https.Config.KNOWN_EXTENSION_LIST.get(s_extension);
		
		/* check if requested file does not exceed max. payload */
		if (net.forestany.forestj.lib.io.File.fileLength(s_fileAbsolutePath) > this.getSeed().getConfig().getMaxPayload() * 1024 * 1024) {
			net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "413 Payload Too Large: File length for answer is to long, max. payload is '" + net.forestany.forestj.lib.Helper.formatBytes(this.getSeed().getConfig().getMaxPayload() * 1024 * 1024) + "'");
			return 413;
		}
		
		try {
			/* if we respond with a text|json|xml file we are using our outgoing encoding setting, read all bytes from file and add charset value to the content type as well */
			if ( s_contentType.startsWith("text") || s_contentType.contains("json") || s_contentType.contains("xml") ) {
				this.getSeed().getResponseHeader().setContentType(s_contentType + "; charset=" + this.getSeed().getConfig().getOutEncoding().name());
				this.getSeed().setResponseBody( net.forestany.forestj.lib.io.File.readAllBytes(s_fileAbsolutePath, this.getSeed().getConfig().getOutEncoding()) );
				this.getSeed().getResponseHeader().setContentLength(this.getSeed().getResponseBody().length);
				this.getSeed().getResponseHeader().setLastModified(net.forestany.forestj.lib.io.File.lastModified(s_fileAbsolutePath));
			} else { /* just set content type ad read all bytes from file */
				this.getSeed().getResponseHeader().setContentType(s_contentType);
				this.getSeed().setResponseBody( net.forestany.forestj.lib.io.File.readAllBytes(s_fileAbsolutePath) );
				this.getSeed().getResponseHeader().setContentLength(this.getSeed().getResponseBody().length);
				this.getSeed().getResponseHeader().setLastModified(net.forestany.forestj.lib.io.File.lastModified(s_fileAbsolutePath));
			}
		} catch (java.io.IOException o_exc) {
			net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "404 Not Found: Resource '" + s_fileAbsolutePath + "'; " + o_exc.getMessage());
			return 404;
		}
		
		return 200;
	}
	
	/**
	 * handling incoming SOAP request 
	 */
	private int handleSOAPRequest() {
		/* check if wsdl configuration is available */
		if (this.getSeed().getConfig().getWSDL() == null) {
			net.forestany.forestj.lib.Global.ilogSevere(this.getSeed().getSalt() + " " + "500 Internal Server Error: No WSDL configuration enabled for SOAP protocol");
			return 500;
		}
		
		if (this.getSeed().getRequestHeader().getMethod().contentEquals("GET")) { /* handling incoming SOAP GET request */
			/* iterate each service port configuration from wsdl */
			for (net.forestany.forestj.lib.net.https.soap.WSDL.ServicePort o_servicePort : this.getSeed().getConfig().getWSDL().getService().getServicePorts()) {
				/* get address location from service port, removing 'https://' prefix and host part */
				String s_addressLocation = o_servicePort.getAddressLocation().replace("https://", "").replace(this.getSeed().getRequestHeader().getHost(), "");
				
				/* we only accept GET requests to wsdl or xsd schema file */
				if ( (this.getSeed().getRequestHeader().getRequestPath().contentEquals(s_addressLocation + "?wsdl")) || (this.getSeed().getRequestHeader().getRequestPath().contentEquals(s_addressLocation + "?WSDL")) ) {
					/* check if we want to get the wsdl configuration by '?wsdl' or '?WSDL' at the end of request path */
					this.getSeed().getRequestHeader().setPath( this.getSeed().getRequestHeader().getFile() );
					this.getSeed().getRequestHeader().setFile( this.getSeed().getConfig().getWSDL().getWSDLFile() );
					
					return this.handleFileRequest();
				} else if (this.getSeed().getRequestHeader().getRequestPath().contentEquals(s_addressLocation + "/" + this.getSeed().getConfig().getWSDL().getWSDLFile())) {
					/* if wsdl file is directly requested */
					this.getSeed().getRequestHeader().setPath( s_addressLocation );
					this.getSeed().getRequestHeader().setFile( this.getSeed().getConfig().getWSDL().getWSDLFile() );
					
					return this.handleFileRequest();
				} else if ( (!net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getConfig().getWSDL().getXSDFile() )) && (this.getSeed().getRequestHeader().getRequestPath().contentEquals(s_addressLocation + "/" + this.getSeed().getConfig().getWSDL().getXSDFile())) ) {
					/* if xsd schema file is directly requested */
					this.getSeed().getRequestHeader().setPath( s_addressLocation );
					this.getSeed().getRequestHeader().setFile( this.getSeed().getConfig().getWSDL().getXSDFile() );
					
					return this.handleFileRequest();
				}
			}
			
			/* forbidden requests happened */
			net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "403 Forbidden: Request to '" + this.getSeed().getRequestHeader().getRequestPath() + "' is forbidden for SOAP web server (only access to .wsdl and .xsd allowed)");
			return 403;
		} else if (this.getSeed().getRequestHeader().getMethod().contentEquals("POST")) { /* handling incoming SOAP POST request */
			java.nio.charset.Charset o_inCharset = null;
			
			/* check for incoming charset value in request header */
			if (this.getSeed().getRequestHeader().getContentType().contains("charset=")) {
														net.forestany.forestj.lib.Global.ilogFine(this.getSeed().getSalt() + " " + "read charset encoding out of request header");
				
				/* read charset encoding out of request header */
				String s_encoding = this.getSeed().getRequestHeader().getContentType().substring(this.getSeed().getRequestHeader().getContentType().indexOf("charset=") + 8);
				s_encoding = s_encoding.toLowerCase();
				
				/* delete double quotes */
				if ( (s_encoding.startsWith("\"")) && (s_encoding.endsWith("\"")) ) {
					s_encoding = s_encoding.substring(1, s_encoding.length() - 1);
				}
				
														net.forestany.forestj.lib.Global.ilogFine(this.getSeed().getSalt() + " " + "try to create a charset object with read charset encoding value: " + s_encoding);
				
				/* try to create a charset object with read charset encoding value */
				try {
					o_inCharset = java.nio.charset.Charset.forName(s_encoding);
				} catch (java.nio.charset.IllegalCharsetNameException | java.nio.charset.UnsupportedCharsetException o_exc) {
					net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: Invalid charset '" + s_encoding + "' within request in HTTP header content type '" + this.getSeed().getRequestHeader().getContentType() + "'; " + o_exc.getMessage());
					return 400;
				}
			} else { /* use configured incoming encoding property */
				o_inCharset = this.getSeed().getConfig().getInEncoding();
			}
			
			/* convert received SOAP request byte array to string with charset object */
			String s_soapRequest = new String(this.getSeed().getRequestBody(), o_inCharset);
			
			/* read all xml lines */
			s_soapRequest = s_soapRequest.replaceAll("[\\r\\n\\t]", "").replaceAll(">\\s*<", "><");
			
			/* clean up xml file */
			s_soapRequest = s_soapRequest.replaceAll("<\\?(.*?)\\?>", "");
			s_soapRequest = s_soapRequest.replaceAll("<!--(.*?)-->", "");
			s_soapRequest = s_soapRequest.replaceAll("<soap:", "<");
			s_soapRequest = s_soapRequest.replaceAll("</soap:", "</");
			
			/* validate xml */
			java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("(<[^<>]*?<[^<>]*?>|<[^<>]*?>[^<>]*?>)");
			java.util.regex.Matcher o_matcher = o_regex.matcher(s_soapRequest);
			
			/* if regex-matcher has match, the xml file is not valid */
		    while (o_matcher.find()) {
		        throw new IllegalArgumentException("Invalid xml-file. Please check xml-file at \"" + o_matcher.group(0) + "\".");
		    }
			
		    java.util.List<String> a_xmlTags = new java.util.ArrayList<String>();
		    
		    /* add all xml-tags to a list for parsing */
		    o_regex = java.util.regex.Pattern.compile("(<[^<>/]*?/>)|(<[^<>/]*?>[^<>]*?</[^<>/]*?>)|(<[^<>]*?>)|(</[^<>/]*?>)");
		    o_matcher = o_regex.matcher(s_soapRequest);
		    
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
		    	net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: SOAP request must start with 'Envelope'-tag");
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
		    	net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: SOAP request must have a 'Body'-tag");
				return 400;
		    }
		    
		    /* handle Body data from SOAP request */
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
		    	net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: SOAP request does not closed the 'Body'-tag");
				return 400;
		    }
	    	
	    	java.util.List<String> a_soapBodyXmlTags = new java.util.ArrayList<String>();
		    	
	    	/* gather all SOAP xml tags in our list */
	    	for (int i = i_bodyTag + 1; i < i_bodyEnd; i++) {
	    		a_soapBodyXmlTags.add(a_xmlTags.get(i));
	    	}
	    	
	    	/* clean up xml namespace stuff */
	    	if (a_soapBodyXmlTags.get(0).contains(" xmlns:")) {
	    		String s_namespaceInitial = a_soapBodyXmlTags.get(0).substring(1 , a_soapBodyXmlTags.get(0).indexOf(":"));
				String s_namespace = a_soapBodyXmlTags.get(0).substring(a_soapBodyXmlTags.get(0).indexOf(s_namespaceInitial + "=\"") + s_namespaceInitial.length() + 2);
				s_namespace = s_namespace.substring(0, s_namespace.indexOf("\""));
				
				this.s_soapTargetNamespace = s_namespace;
				this.s_soapNamespaceInitial = s_namespaceInitial;
				
				/* check if target namespace from request matches our target namespace in our wsdl xsd schema */
				if (!this.getSeed().getConfig().getWSDL().getSchema().getTargetNamespace().contentEquals(this.s_soapTargetNamespace)) {
					net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: received SOAP target namespace '" + this.s_soapTargetNamespace + "' does not match with wsdl xsd schema target namespace '" + this.getSeed().getConfig().getWSDL().getSchema().getTargetNamespace() + "'");
					return 400;
				}
				
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
	    			a_soapBodyXmlTags.set(i, a_soapBodyXmlTags.get(i).replace("<" + s_namespaceInitial + ":", "<").replace("</" + s_namespaceInitial + ":", "</"));
	    		}
	    	} else if (a_soapBodyXmlTags.get(0).contains(" xmlns=")) {
	    		String s_namespace = a_soapBodyXmlTags.get(0).substring(a_soapBodyXmlTags.get(0).indexOf(" xmlns=\"") + 8);
				s_namespace = s_namespace.substring(0, s_namespace.indexOf("\""));
				
				this.s_soapTargetNamespace = s_namespace;
				
				/* check if target namespace from request matches our target namespace in our wsdl xsd schema */
				if (!this.getSeed().getConfig().getWSDL().getSchema().getTargetNamespace().contentEquals(this.s_soapTargetNamespace)) {
					net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: received SOAP target namespace '" + this.s_soapTargetNamespace + "' does not match with wsdl xsd schema target namespace '" + this.getSeed().getConfig().getWSDL().getSchema().getTargetNamespace() + "'");
					return 400;
				}
				
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
	    	
	    	String s_inputMessageName = null;
	    	String s_operationName = null;
	    	String s_elementName = null;
	    	
	    	/* get input message name */
    		if (a_soapBodyXmlTags.get(0).contains(" ")) {
    			s_inputMessageName = a_soapBodyXmlTags.get(0).substring(1, a_soapBodyXmlTags.get(0).indexOf(" "));
    		} else {
    			s_inputMessageName = a_soapBodyXmlTags.get(0).substring(1, a_soapBodyXmlTags.get(0).length() - 1);
    		}
    		
    		/* check if input message name exists as a valid operation in our wsdl configuration */
    		if (!this.getSeed().getConfig().getWSDL().containsOperationByInputMessagePartElementValue(s_inputMessageName)) {
    			/* SOAP operation not found, so we generate a SOAP fault object for response */
    			this.o_soapResponse = (Object)new net.forestany.forestj.lib.net.https.soap.SoapFault("soap:client", "Soap operation with input message name '" + s_inputMessageName + "' not found. Please verify your requests with wsdl file.", null, null);
    			return 200;
    		} else { /* store operation name */
    			s_operationName = this.getSeed().getConfig().getWSDL().getOperationByInputMessagePartElementValue(s_inputMessageName).getPortTypeOperationName();
    		}
    		
    		/* check if operation name exists within SOAP operation list of our wsdl configuration */
    		if (!this.getSeed().getConfig().getWSDL().getSOAPOperations().containsKey(s_operationName)) {
	    		this.o_soapResponse = (Object)new net.forestany.forestj.lib.net.https.soap.SoapFault("soap:server", "No SOAP operation declared to process input message.", null, null);
    			return 200;
			}
	    	
    		/* get input message element attribute value */
    		s_elementName = this.getSeed().getConfig().getWSDL().getOperationByInputMessagePartElementValue(s_inputMessageName).getInputMessage().getPartElement();
			
    		/* replace input message value tags with input message element attribute value, because these are used for xml decoding based on xsd schema types in wsdl configuration */
			for (int i = 0; i < a_soapBodyXmlTags.size(); i++) {
				if (a_soapBodyXmlTags.get(i).startsWith("<" + s_inputMessageName)) {
					a_soapBodyXmlTags.set(i, a_soapBodyXmlTags.get(i).replace("<" + s_inputMessageName, "<" + s_elementName));
				} else if (a_soapBodyXmlTags.get(i).startsWith("</" + s_inputMessageName)) {
					a_soapBodyXmlTags.set(i, a_soapBodyXmlTags.get(i).replace("</" + s_inputMessageName, "</" + s_elementName));
				}
			}
			
													net.forestany.forestj.lib.Global.ilogFine(this.getSeed().getSalt() + " " + "replace input message value tags '" + s_inputMessageName + "' with input message element attribute value '" + s_elementName + "'");
			
    		Object o_soapRequest = null;
    		
													net.forestany.forestj.lib.Global.ilogFine(this.getSeed().getSalt() + " " + "decode received and cleaned up SOAP xml data");
	    	
			/* decode received and cleaned up SOAP xml data to an object */
	    	try {
	    		/* remember no fields from abstract are supported */
	    		o_soapRequest = this.getSeed().getConfig().getWSDL().getSchema().xmlDecode(a_soapBodyXmlTags);
	    	} catch (Exception o_exc) {
	    		this.o_soapResponse = (Object)new net.forestany.forestj.lib.net.https.soap.SoapFault("soap:server", "Exception while decoding received SOAP xml data.", o_exc.getMessage(), null);
    			return 200;
	    	}
	    	
	    	/* get SOAP operation interface by operation name */
	    	net.forestany.forestj.lib.net.https.soap.WSDL.SOAPOperationInterface o_soi = this.getSeed().getConfig().getWSDL().getSOAPOperations().get(s_operationName);
	    	
	    	/* secure check if SOAP operation interface is not null */
	    	if (o_soi == null) {
	    		this.o_soapResponse = (Object)new net.forestany.forestj.lib.net.https.soap.SoapFault("soap:server", "SOAP operation '" + s_operationName + "' is not implemented by server.", null, null);
    			return 200;
	    	}
	    	
	    	/* execute implemented SOAP operation, giving our decoded received SOAP xml data and our Seed instance as parameter - expecting a SOAP response object for client request */
	    	try {
	    		this.o_soapResponse = (Object)o_soi.SOAPOperation(o_soapRequest, this.getSeed());
	    	} catch (Exception o_exc) {
	    		this.o_soapResponse = (Object)new net.forestany.forestj.lib.net.https.soap.SoapFault("soap:server", "Exception occured while processing SOAP operation.", o_exc.getMessage(), null);
    			return 200;
	    	}
	    	
	    	String s_soapResponse = null;
	    	
	    	/* encode expected SOAP response object to an xml string */
	    	try {
	    		s_soapResponse = this.getSeed().getConfig().getWSDL().getSchema().xmlEncode(this.o_soapResponse);
	    	} catch (Exception o_exc) {
	    		this.o_soapResponse = (Object)new net.forestany.forestj.lib.net.https.soap.SoapFault("soap:server", "Exception while encoding return value xml data of SOAP operation.", o_exc.getMessage(), null);
    			return 200;
	    	}
	    	
	    	/* remove xml header from SOAP response xml string */
	    	s_soapResponse = s_soapResponse.replaceAll("<\\?(.*?)\\?>\r\n", "");
	    	s_soapResponse = s_soapResponse.replaceAll("<\\?(.*?)\\?>\n", "");
	    	
	    	/* get output message value */
	    	String s_outputMessage = s_soapResponse.substring(0, s_soapResponse.indexOf(">") + 1);
	    	
	    	/* filter out any possible attributes */
	    	if (s_outputMessage.contains(" ")) {
	    		s_outputMessage = s_outputMessage.substring(1, s_outputMessage.indexOf(" "));
    		} else {
    			s_outputMessage = s_outputMessage.substring(1, s_outputMessage.length() - 1);
    		}
	    	
	    	/* get our portType operation object and thus the output message */
	    	if (!this.getSeed().getConfig().getWSDL().getOperationByInputMessagePartElementValue(s_inputMessageName).getOutputMessage().getPartElement().contentEquals(s_outputMessage)) {
    			/* output message could not be found */
	    		this.o_soapResponse = (Object)new net.forestany.forestj.lib.net.https.soap.SoapFault("soap:server", "Invalid soap output message '" + s_outputMessage + "' within soap response body. Please verify server response with wsdl file.", null, null);
    			return 200;
    		} else {
    			/* replace output message value tags with output message part element value, because client expecting this response based on xsd schema types in wsdl configuration */
    			String s_outputMessageElementValue = this.getSeed().getConfig().getWSDL().getOperationByInputMessagePartElementValue(s_inputMessageName).getOutputMessage().getPartElement();
    			s_soapResponse = s_soapResponse.replaceAll("<" + s_outputMessage, "<" + s_outputMessageElementValue);
    			s_soapResponse = s_soapResponse.replaceAll("</" + s_outputMessage, "</" + s_outputMessageElementValue);
    			
    													net.forestany.forestj.lib.Global.ilogFine(this.getSeed().getSalt() + " " + "replaced output message value tags '" + s_outputMessage + "' with output message part element value '" + s_outputMessageElementValue + "'");
    		}
	    	
	    	/* store SOAP encoded xml response string as object for later use */
	    	this.o_soapResponse = (Object)s_soapResponse;
		} else {
			net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "405 Method Not Allowed: HTTP method '" + this.getSeed().getRequestHeader().getMethod() + "' not allowed for SOAP web server (only GET or POST)");
			return 405;
		}
		
		return 200;
	}
	
	/**
	 * send https/soap/rest response to received request
	 */
	private void sendResponse() throws Exception {
		if (this.getSeed().getReturnCode() != 200) { /* return code while handling request is NOT 'OK' */
			if (this.getSeed().getReturnCode() != 301) { /* prepare fail response */
				this.getSeed().setResponseBody( new String(("HTTP/1.1 " + this.getSeed().getReturnCode() + " " + net.forestany.forestj.lib.net.https.ResponseHeader.RETURN_CODES.get(this.getSeed().getReturnCode())).getBytes(), this.getSeed().getConfig().getOutEncoding()).getBytes() );
				this.getSeed().getResponseHeader().setContentLength(this.getSeed().getResponseBody().length);
				this.getSeed().getResponseHeader().setContentType(net.forestany.forestj.lib.net.https.Config.KNOWN_EXTENSION_LIST.get(".txt") + "; charset=" + this.getSeed().getConfig().getOutEncoding().name());
			} else { /* prepare redirect response */
				/* chrome wants two linebreaks at the end */
				this.getSeed().setResponseBody( 
					new String(
						("Location: " + this.getSeed().getConfig().getDomain() + this.getSeed().getRequestHeader().getRequestPath() + "/" + 
						net.forestany.forestj.lib.io.File.NEWLINE +	net.forestany.forestj.lib.io.File.NEWLINE
						).getBytes(),
						this.getSeed().getConfig().getOutEncoding()
					).getBytes() );
			}
		} else { /* return code while handling request is 'OK' */
			if ( (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.DYNAMIC) || (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.REST) ) { /* respond with dynamic or REST content */
				boolean b_skipCookies = false;
				
				/* do steps for rendering dynamic content if response content type starts with 'text/html' */
				if ( (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.DYNAMIC) && (this.getSeed().getResponseHeader().getContentType().startsWith( net.forestany.forestj.lib.net.https.Config.KNOWN_EXTENSION_LIST.get(".html") )) ) {
					/* create dynamic instance if it is null */
					if (this.o_dynamic == null) {
						this.o_dynamic = new net.forestany.forestj.lib.net.https.dynm.Dynamic(this.getSeed());
					}
					
					/* render dynamic content */
					String s_dynamicReturn = this.o_dynamic.renderDynamic();
					
					/* return value of rendering dynamic content is not 'OK' */
					if (!s_dynamicReturn.contentEquals("OK")) {
						/* prepare fail response */
						net.forestany.forestj.lib.Global.ilogSevere(this.getSeed().getSalt() + " " + "500 Internal Server Error - exception within forestAny code; " + s_dynamicReturn);
						this.getSeed().setReturnCode(500);
						this.getSeed().setResponseBody( new String((500 + " " + net.forestany.forestj.lib.net.https.ResponseHeader.RETURN_CODES.get(500) + " - " + s_dynamicReturn).getBytes(), this.getSeed().getConfig().getOutEncoding()).getBytes() );
						this.getSeed().getResponseHeader().setContentLength(this.getSeed().getResponseBody().length);
						this.getSeed().getResponseHeader().setContentType(net.forestany.forestj.lib.net.https.Config.KNOWN_EXTENSION_LIST.get(".txt") + "; charset=" + this.getSeed().getConfig().getOutEncoding().name());
						
						/* skip cookies routine for response */
						b_skipCookies = true;
					}
				} else if (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.REST) { /* do steps for rendering REST content */
					/* create dynamic instance if it is null */
					if (this.o_dynamic == null) {
						this.o_dynamic = new net.forestany.forestj.lib.net.https.dynm.Dynamic(this.getSeed());
					}
					
					/* render REST content */
					String s_dynamicReturn = this.o_dynamic.renderREST();
					
					/* return value of rendering REST content is not 'OK' */
					if (!s_dynamicReturn.contentEquals("OK")) {
						if (s_dynamicReturn.startsWith("400;")) { /* bad request returned from REST implementation */
							net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: " + s_dynamicReturn.substring(4));
							this.getSeed().setReturnCode(400);
							this.getSeed().setResponseBody( new String((400 + " " + net.forestany.forestj.lib.net.https.ResponseHeader.RETURN_CODES.get(400) + " - " + s_dynamicReturn.substring(4)).getBytes(), this.getSeed().getConfig().getOutEncoding()).getBytes() );
							this.getSeed().getResponseHeader().setContentLength(this.getSeed().getResponseBody().length);
							this.getSeed().getResponseHeader().setContentType(net.forestany.forestj.lib.net.https.Config.KNOWN_EXTENSION_LIST.get(".txt") + "; charset=" + this.getSeed().getConfig().getOutEncoding().name());
						} else { /* prepare fail response */
							net.forestany.forestj.lib.Global.ilogSevere(this.getSeed().getSalt() + " " + "500 Internal Server Error - exception within handling REST implementation; " + s_dynamicReturn);
							this.getSeed().setReturnCode(500);
							this.getSeed().setResponseBody( new String((500 + " " + net.forestany.forestj.lib.net.https.ResponseHeader.RETURN_CODES.get(500) + " - " + s_dynamicReturn).getBytes(), this.getSeed().getConfig().getOutEncoding()).getBytes() );
							this.getSeed().getResponseHeader().setContentLength(this.getSeed().getResponseBody().length);
							this.getSeed().getResponseHeader().setContentType(net.forestany.forestj.lib.net.https.Config.KNOWN_EXTENSION_LIST.get(".txt") + "; charset=" + this.getSeed().getConfig().getOutEncoding().name());
						}
						
						/* skip cookies routine for response */
						b_skipCookies = true;
					}
				}
				
				/* execute cookies routine for response */
				if (!b_skipCookies) {
					/* check if we accept session cookies by configuration value */
					if (!this.getSeed().getConfig().getNotUsingCookies()) {
						/* create session file name by session directory and cookie uuid in response header */
						String s_sessionFile = this.getSeed().getConfig().getSessionDirectory() + this.getSeed().getResponseHeader().getCookie().getCookieUUID() + ".txt";
						
						/* check if session file exists: true -> delete it */
						if (net.forestany.forestj.lib.io.File.exists(s_sessionFile)) {
							net.forestany.forestj.lib.io.File.deleteFile(s_sessionFile);
						}
						
						/* configuration value for session refresh is set */
						if (this.getSeed().getConfig().getSessionRefresh()) {
							/* create new session cookie uuid */
							this.getSeed().getResponseHeader().getCookie().setCookieUUID( net.forestany.forestj.lib.Helper.generateUUID() );
							/* update session file name because of session refresh */
							s_sessionFile = this.getSeed().getConfig().getSessionDirectory() + this.getSeed().getResponseHeader().getCookie().getCookieUUID() + ".txt";
						}
						
						/* create session file */
						net.forestany.forestj.lib.io.File o_sessionFile = new net.forestany.forestj.lib.io.File(s_sessionFile, true);
						/* append first line with ISO-8601-UTC date-time string */
						o_sessionFile.appendLine( net.forestany.forestj.lib.Helper.toISO8601UTC( java.time.LocalDateTime.now() ) );
						
						/* check if current request has any session data */
						if (this.getSeed().getSessionData().size() > 0) {
							/* iterate each session key and value pair */
							this.getSeed().getSessionData().entrySet().forEach(o_sessionDataEntry -> {
								try {
									/* append session key and value pair to session file, divided by '='; replacing '=' within key and value with '&equal;' */
									o_sessionFile.appendLine(o_sessionDataEntry.getKey().replace("=", "&equal;") + "=" + o_sessionDataEntry.getValue().replace("=", "&equal;"));
								} catch (Exception o_exc) {
									/* prepare fail response, because session key and value pair could not be append to session file */
									net.forestany.forestj.lib.Global.ilogSevere(this.getSeed().getSalt() + " " + "500 Internal Server Error: could not append session key '" + o_sessionDataEntry.getKey() + "' with value to file '" + o_sessionFile.getFileName() + "'");
									this.getSeed().setReturnCode(500);
									this.getSeed().setResponseBody( new String((500 + " " + net.forestany.forestj.lib.net.https.ResponseHeader.RETURN_CODES.get(500)).getBytes(), this.getSeed().getConfig().getOutEncoding()).getBytes() );
									this.getSeed().getResponseHeader().setContentLength(this.getSeed().getResponseBody().length);
									this.getSeed().getResponseHeader().setContentType(net.forestany.forestj.lib.net.https.Config.KNOWN_EXTENSION_LIST.get(".txt") + "; charset=" + this.getSeed().getConfig().getOutEncoding().name());
								}
							});
						}
					}
				}
			} else if ( (this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.SOAP) && (this.getSeed().getRequestHeader().getMethod().contentEquals("POST")) ) { /* respond with SOAP content if http method is POST */
				if (this.o_soapResponse != null) { /* SOAP response object is available */
					String s_soapResponse = "";
					
					if (this.o_soapResponse instanceof net.forestany.forestj.lib.net.https.soap.SoapFault) {
						/* SOAP response object is instance of SOAP-Fault and will be prepared as xml string */
						s_soapResponse = ((net.forestany.forestj.lib.net.https.soap.SoapFault)this.o_soapResponse).toXML(this.getSeed().getConfig().getOutEncoding().displayName());
					} else {
						/* SOAP response object, xml string, must be bagged within SOAP Envelope */
						s_soapResponse = "<?xml version=\"1.0\" encoding=\"" + this.getSeed().getConfig().getOutEncoding().displayName() + "\" ?>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
						
						s_soapResponse += "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
						s_soapResponse += "\t<soap:Body>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
						
						/* add SOAP response object xml string to response 'Body'-Tag */
						String s_soapResponseXml = this.o_soapResponse.toString();
						
						if ( (!net.forestany.forestj.lib.Helper.isStringEmpty( this.s_soapTargetNamespace )) && (net.forestany.forestj.lib.Helper.isStringEmpty( this.s_soapNamespaceInitial )) ) {
							/* request has target namespace, but no namespace initial */
							
							/* adding target namespace to xml data */
							s_soapResponseXml = s_soapResponseXml.substring(0, s_soapResponseXml.indexOf(">")) + " xmlns=\"" + this.s_soapTargetNamespace + "\"" + s_soapResponseXml.substring(s_soapResponseXml.indexOf(">"));
						} else if ( (!net.forestany.forestj.lib.Helper.isStringEmpty( this.s_soapTargetNamespace )) && (!net.forestany.forestj.lib.Helper.isStringEmpty( this.s_soapNamespaceInitial )) ) {
							/* request has target namespace and namespace initial */
							s_soapResponseXml = s_soapResponseXml.substring(0, s_soapResponseXml.indexOf(">")) + " xmlns:" + this.s_soapNamespaceInitial + "=\"" + this.s_soapTargetNamespace + "\"" + s_soapResponseXml.substring(s_soapResponseXml.indexOf(">"));
							
							/* adding target namespace and namespace initial to each xml-tag to xml data */
							s_soapResponseXml = s_soapResponseXml.replaceAll("</", "</" + this.s_soapNamespaceInitial + ":");
							s_soapResponseXml = s_soapResponseXml.replaceAll("<", "<" + this.s_soapNamespaceInitial + ":");
							s_soapResponseXml = s_soapResponseXml.replaceAll("<" + this.s_soapNamespaceInitial + ":" + "/" + this.s_soapNamespaceInitial + ":", "</" + this.s_soapNamespaceInitial + ":");
						}
						
						/* clean up target namespace and namespace initial for next request */
						this.s_soapTargetNamespace = null;
						this.s_soapNamespaceInitial = null;
						
						s_soapResponse += s_soapResponseXml;
						
						s_soapResponse += "\t</soap:Body>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
						s_soapResponse += "</soap:Envelope>";
					}
					
					/* convert SOAP response string to byte array with outgoing encoding settings */
					this.getSeed().setResponseBody( new String(s_soapResponse.getBytes(), this.getSeed().getConfig().getOutEncoding()).getBytes() );
					/* update content length and content type within response header */
					this.getSeed().getResponseHeader().setContentLength(this.getSeed().getResponseBody().length);
					this.getSeed().getResponseHeader().setContentType(net.forestany.forestj.lib.net.https.Config.KNOWN_EXTENSION_LIST.get(".xsd") + "; charset=" + this.getSeed().getConfig().getOutEncoding().name());
				} else {
					/* prepare fail response, because session key and value pair could not be append to session file */
					net.forestany.forestj.lib.Global.ilogSevere(this.getSeed().getSalt() + " " + "500 Internal Server Error: SOAP response object is null");
					this.getSeed().setReturnCode(500);
					this.getSeed().setResponseBody( new String((500 + " " + net.forestany.forestj.lib.net.https.ResponseHeader.RETURN_CODES.get(500)).getBytes(), this.getSeed().getConfig().getOutEncoding()).getBytes() );
					this.getSeed().getResponseHeader().setContentLength(this.getSeed().getResponseBody().length);
					this.getSeed().getResponseHeader().setContentType(net.forestany.forestj.lib.net.https.Config.KNOWN_EXTENSION_LIST.get(".txt") + "; charset=" + this.getSeed().getConfig().getOutEncoding().name());
				}
			}
			
			/* with current net.forestany.forestj.lib.net.sock.recv.ReceiveTCP structure we cannot keep alive */
			/*if (!this.getSeed().getRequestHeader().getConnectionClose()) {
				this.getSeed().getResponseHeader().setConnectionKeepAlive(true);
			}*/
		}
		
		/* re-check if response does not exceed max. payload value */
		if ( (this.getSeed().getResponseBody() != null) && (this.getSeed().getResponseBody().length > this.getSeed().getConfig().getMaxPayload() * 1024 * 1024) ) {
			net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "413 Payload Too Large: File length for answer is to long, max. payload is '" + net.forestany.forestj.lib.Helper.formatBytes(this.getSeed().getConfig().getMaxPayload() * 1024 * 1024) + "'");
			this.getSeed().setReturnCode(413);
			this.getSeed().setResponseBody( new String((413 + " " + net.forestany.forestj.lib.net.https.ResponseHeader.RETURN_CODES.get(413)).getBytes(), this.getSeed().getConfig().getOutEncoding()).getBytes() );
			this.getSeed().getResponseHeader().setContentLength(this.getSeed().getResponseBody().length);
			this.getSeed().getResponseHeader().setContentType(net.forestany.forestj.lib.net.https.Config.KNOWN_EXTENSION_LIST.get(".txt") + "; charset=" + this.getSeed().getConfig().getOutEncoding().name());
		}
		
												net.forestany.forestj.lib.Global.ilogFine(this.getSeed().getSalt() + " " + "add response header to https/REST/SOAP response");

		/* create string temporary variable for response header */
		String s_responseHeader = new String(this.getSeed().getResponseHeader().toString().getBytes(), this.getSeed().getConfig().getOutEncoding());
		
		/* create byte array for response, including header and body length */
		byte[] a_foo = new byte[s_responseHeader.getBytes().length + ( (this.getSeed().getResponseBody() != null) ? this.getSeed().getResponseBody().length : 0 )];
		int i;
		
		/* copy each byte from response header to byte array */
		for (i = 0; i < s_responseHeader.getBytes().length; i++) {
			a_foo[i] = s_responseHeader.getBytes()[i];
		}
		
		if (this.getSeed().getResponseBody() != null) {
													net.forestany.forestj.lib.Global.ilogFine(this.getSeed().getSalt() + " " + "add body data to https/REST/SOAP response");
			
			/* copy each byte from response body to byte array */
			for (int j = 0; j < this.getSeed().getResponseBody().length; j++) {
				a_foo[j + i] = this.getSeed().getResponseBody()[j];
			}
		}
		
		/* sending https/SOAP/REST response to client */
		this.sendBytes(a_foo, a_foo.length);
	}
}
