package net.forestany.forestj.lib.net.https;

/**
 * HTTP Request Header class which can hold all necessary minimal header information for a request header and a parsing method to parse received request to this object class.
 */
public class RequestHeader {
	
	/* Constants */
	
	/**
	 * CLIENT constant
	 */
	public static final String CLIENT = "forestJ Tiny Https Client 1.0";
	
	/* Fields */
	
	private String s_domain;
	private String s_method;
	private String s_requestPath;
	private String s_convertedPath;
	private String s_path;
	private String s_file;
	private java.util.Map<String, String> a_parameters;
	private String s_protocol;
	private boolean b_connectionClosed;
	private int i_contentLength;
	private String s_contentType;
	private String s_boundary;
	private String s_cookie;
	private String s_host;
	private int i_port;
	private String s_referrer;
	private String s_userAgent;
	private String s_accept;
	private String s_acceptCharset;
	private boolean b_noCacheControl;
	private String s_authorization;
	
	/* Properties */
	
	/**
	 * get method
	 * 
	 * @return String
	 */
	public String getMethod() {
		return this.s_method;
	}
	
	/**
	 * set method by string parameter
	 * 
	 * @param p_s_value						http method as string
	 * @throws IllegalArgumentException		invalid http method or invalid parameter value
	 */
	public void setMethod(String p_s_value) throws IllegalArgumentException {
		if (!java.util.Arrays.asList("GET", "POST", "PUT", "DELETE", "DOWNLOAD").contains(p_s_value)) {
			throw new IllegalArgumentException("HTTP-Method must be [GET, POST, PUT, DELETE or DOWNLOAD], but is set to '" + p_s_value + "'");
		}
		
		this.s_method = p_s_value;
	}
	
	/**
	 * set method by request type parameter
	 * 
	 * @param p_e_requestType					request type parameter
	 * @throws IllegalArgumentException			not implemented request type parameter
	 */
	public void setMethod(net.forestany.forestj.lib.net.http.RequestType p_e_requestType) throws IllegalArgumentException {
		boolean b_exc = false;
		
		switch (p_e_requestType) {
			case DELETE:
				this.s_method = "DELETE";
				break;
			case DOWNLOAD:
				this.s_method = "DOWNLOAD";
				break;
			case GET:
				this.s_method = "GET";
				break;
			case POST:
				this.s_method = "POST";
				break;
			case PUT:
				this.s_method = "PUT";
				break;
			default:
				b_exc = true;
				break;
		}
		
		if (b_exc) {
			throw new IllegalArgumentException("HTTP-Method must be [GET, POST, PUT, DELETE or DOWNLOAD], but used unimplemented method '" + p_e_requestType + "'");
		}
	}
	
	/**
	 * get request path
	 * 
	 * @return String
	 */
	public String getRequestPath() {
		return this.s_requestPath;
	}
	
	/**
	 * set request path
	 * 
	 * @param p_s_value String
	 */
	public void setRequestPath(String p_s_value) {
		this.s_requestPath = p_s_value;
	}
	
	/**
	 * get converted path
	 * 
	 * @return String
	 */
	public String getConvertedPath() {
		return this.s_convertedPath;
	}
	
	/**
	 * get path
	 * 
	 * @return String
	 */
	public String getPath() {
		return this.s_path;
	}
	
	/**
	 * set path
	 * 
	 * @param p_s_value String
	 */
	public void setPath(String p_s_value) {
		this.s_path = p_s_value;
	}
	
	/**
	 * get file
	 * 
	 * @return String
	 */
	public String getFile() {
		return this.s_file;
	}
	
	/**
	 * set file
	 * 
	 * @param p_s_value String
	 */
	public void setFile(String p_s_value) {
		this.s_file = p_s_value;
	}
	
	/**
	 * get parameters
	 * 
	 * @return java.util.Map&lt;String, String&gt;
	 */
	public java.util.Map<String, String> getParameters() {
		return this.a_parameters;
	}
	
	/**
	 * get protocol
	 * 
	 * @return String
	 */
	public String getProtocol() {
		return this.s_protocol;
	}
	
	/**
	 * set protocol
	 * 
	 * @param p_s_value String
	 */
	public void setProtocol(String p_s_value) {
		this.s_protocol = p_s_value;
	}
	
	/**
	 * get connection close flag
	 * 
	 * @return boolean
	 */
	public boolean getConnectionClose() {
		return this.b_connectionClosed;
	}
	
	/**
	 * set connection close flag
	 * 
	 * @param p_b_value boolean
	 */
	public void setConnectionClose(boolean p_b_value) {
		this.b_connectionClosed = p_b_value;
	}
	
	/**
	 * get content length
	 * 
	 * @return int
	 */
	public int getContentLength() {
		return this.i_contentLength;
	}
	
	/**
	 * set content length
	 * 
	 * @param p_i_value								content-length of request
	 * @throws IllegalArgumentException				content-length must be a positive integer value
	 */
	public void setContentLength(int p_i_value) throws IllegalArgumentException {
		if (p_i_value < 0) {
			throw new IllegalArgumentException("Content-Length must be a positive integer value");
		} else {
			this.i_contentLength = p_i_value;
		}
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
	 * get boundary
	 * 
	 * @return String
	 */
	public String getBoundary() {
		return this.s_boundary;
	}
	
	/**
	 * get cookie
	 * 
	 * @return String 
	 */
	public String getCookie() {
		return this.s_cookie;
	}
	
	/**
	 * set cookie
	 * 
	 * @param p_s_value String
	 */
	public void setCookie(String p_s_value) {
		this.s_cookie = p_s_value;
	}
	
	/**
	 * get host
	 * 
	 * @return String
	 */
	public String getHost() {
		return this.s_host;
	}
	
	/**
	 * set host
	 * 
	 * @param p_s_value String
	 */
	public void setHost(String p_s_value) {
		this.s_host = p_s_value;
	}
	
	/**
	 * get port
	 * 
	 * @return int
	 */
	public int getPort() {
		return this.i_port;
	}
	
	/**
	 * set port
	 * 
	 * @param p_i_value int
	 */
	public void setPort(int p_i_value) {
		this.i_port = p_i_value;
	}
	
	/**
	 * get referrer
	 * 
	 * @return String
	 */
	public String getReferrer() {
		return this.s_referrer;
	}
	
	/**
	 * get user agent
	 * 
	 * @return String
	 */
	public String getUserAgent() {
		return this.s_userAgent;
	}
	
	/**
	 * set user agent
	 * 
	 * @param p_s_value String
	 */
	public void setUserAgent(String p_s_value) {
		this.s_userAgent = p_s_value;
	}
	
	/**
	 * get accept
	 * 
	 * @return String
	 */
	public String getAccept() {
		return this.s_accept;
	}
	
	/**
	 * set accept
	 * 
	 * @param p_s_value String
	 */
	public void setAccept(String p_s_value) {
		this.s_accept = p_s_value;
	}
	
	/**
	 * get accept charset
	 * 
	 * @return String
	 */
	public String getAcceptCharset() {
		return this.s_acceptCharset;
	}
	
	/**
	 * set accept charset
	 * 
	 * @param p_s_value String
	 */
	public void setAcceptCharset(String p_s_value) {
		this.s_acceptCharset = p_s_value;
	}
	
	/**
	 * set no cache control flag
	 * 
	 * @param p_b_value boolean
	 */
	public void setNoCacheControl(boolean p_b_value) {
		this.b_noCacheControl = p_b_value;
	}
	
	/**
	 * get authorization
	 * 
	 * @return String
	 */
	public String getAuthorization() {
		return this.s_authorization;
	}
	
	/**
	 * set authorization
	 * 
	 * @param p_s_value String
	 */
	public void setAuthorization(String p_s_value) {
		this.s_authorization = p_s_value;
	}
	
	/* Methods */
	
	/**
	 * Request header constructor
	 * 
	 * @param p_s_domain				set domain value for reading referrer correctly
	 * @throws NullPointerException		domain parameter is null or empty
	 */
	public RequestHeader(String p_s_domain) throws NullPointerException {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_domain)) {
			throw new NullPointerException("Domain parameter is null or empty");
		}
		
		this.s_domain = p_s_domain;
		this.s_method = "";
		this.s_requestPath = "";
		this.s_convertedPath = "";
		this.s_path = "";
		this.s_file = "";
		this.a_parameters = new java.util.HashMap<String, String>();
		this.s_protocol = "";
		this.b_connectionClosed = false;
		this.i_contentLength = -1;
		this.s_contentType = "";
		this.s_boundary = "";
		this.s_cookie = "";
		this.s_host = "";
		this.i_port = -1;
		this.s_referrer = "";
		this.s_userAgent = "";
		this.s_accept = "";
		this.s_acceptCharset = "";
		this.b_noCacheControl = true;
		this.s_authorization = "";
	}
	
	/**
	 * Parse request http header to this instance and fill all recognized properties
	 * 
	 * @param p_a_requestHeaderLines	all http request header lines
	 * @return							http status code
	 */
	public int parseRequestHeader(String[] p_a_requestHeaderLines) {
		return this.parseRequestHeader(p_a_requestHeaderLines, false);
	}
	
	/**
	 * Parse request http header to this instance and fill all recognized properties
	 * 
	 * @param p_a_requestHeaderLines	all http request header lines
	 * @param p_b_no301					true - request is not a file, so we just add a '/' at the end and redirect with 301
	 * @return							http status code
	 */
	public int parseRequestHeader(String[] p_a_requestHeaderLines, boolean p_b_no301) {
		/* check if http request header lines are not empty, otherwise we have an empty message */
		if ( (p_a_requestHeaderLines == null) || (p_a_requestHeaderLines.length < 1) ) {
			net.forestany.forestj.lib.Global.ilogWarning("400 Bad Request: No request message available");
			return 400;
		}
		
		String s_firstLine = p_a_requestHeaderLines[0];
		
		/* first line must contain 2 white spaces */
		if (!s_firstLine.contains(" ")) {
			net.forestany.forestj.lib.Global.ilogWarning("400 Bad Request: Invalid first line of request '" + s_firstLine + "'");
			return 400;
		}
		
		/* split all values from first line */
		String[] a_firstLine = s_firstLine.split(" ");
		
		/* first line must contain 3 values: method, path and protocol */
		if (a_firstLine.length != 3) {
			net.forestany.forestj.lib.Global.ilogWarning("400 Bad Request: Invalid amount of data in first line of request '" + s_firstLine + "', must be 'HTTP-Method Path HTTP-Protocol'");
			return 400;
		}
		
		/* check http method value */
		if (a_firstLine[0].contentEquals("GET")) {
			this.s_method = "GET";
		} else if (a_firstLine[0].contentEquals("POST")) {
			this.s_method = "POST";
		} else if (a_firstLine[0].contentEquals("PUT")) {
			this.s_method = "PUT";
		} else if (a_firstLine[0].contentEquals("DELETE")) {
			this.s_method = "DELETE";
		} else {
			net.forestany.forestj.lib.Global.ilogWarning("405 Method Not Allowed: Invalid HTTP-Method in request '" + a_firstLine[0] + "', must be [GET|POST|PUT|DELETE]'");
			return 405;
		}
		
		/* check if http protocol is 1.0 or 1.1 */
		if ( (a_firstLine[2].contentEquals("HTTP/1.1")) || (a_firstLine[2].contentEquals("HTTP/1.0")) ) {
			this.s_protocol = a_firstLine[2];
		} else {
			net.forestany.forestj.lib.Global.ilogWarning("505 HTTP Version Not Supported: Unsupported HTTP-Protocol in request '" + a_firstLine[2] + "', must be [HTTP/1.0|HTTP/1.1]'");
			return 505;
		}
		
		
		/* assume request path from first line */
		this.s_requestPath = a_firstLine[1];
		
		/* request path must start with '/' */
		if (!this.s_requestPath.startsWith("/")) {
			net.forestany.forestj.lib.Global.ilogWarning("400 Bad Request: Request path does not start with '/'");
			return 400;
		}
		
		/* iterate all other request header lines */
		for (String s_requestHeaderLine : p_a_requestHeaderLines) {
			/* header name and value are separated by a ':' */
			if (s_requestHeaderLine.contains(":")) {
				/* get header name */
				String s_headerName = s_requestHeaderLine.substring(0, s_requestHeaderLine.indexOf(":")).trim().toLowerCase();
				/* get header value */ 
				String s_headerValue = s_requestHeaderLine.substring(s_requestHeaderLine.indexOf(":") + 1).trim();
				
				switch (s_headerName) {
					case "connection":
						if (s_headerValue.toLowerCase().contentEquals("close")) {
							this.b_connectionClosed = true;
						}
						break;
					case "content-length":
						/* parse content length to an integer value */
						if (net.forestany.forestj.lib.Helper.isInteger(s_headerValue)) {
							this.i_contentLength = Integer.parseInt(s_headerValue);
						} else {
							net.forestany.forestj.lib.Global.ilogWarning("400 Bad Request: Invalid value for 'Content-Length' request header '" + s_headerValue + "', must be an integer value'");
							return 400;
						}
						break;
					case "content-type":
						/* recognize boundary value for POST requests */
						if (s_headerValue.contains("boundary")) {
							this.s_contentType = s_headerValue.substring(0, s_headerValue.indexOf(";")).trim();
							this.s_boundary = s_headerValue.substring(s_headerValue.indexOf("boundary=") + new String("boundary=").length()).trim();
						} else {
							this.s_contentType = s_headerValue;
						}
						break;
					case "cookie":
						this.s_cookie = s_headerValue;
						break;
					case "host":
						this.s_host = s_headerValue;
						break;
					case "referer":
						this.s_referrer = s_headerValue;
						break;
					case "user-agent":
						this.s_userAgent = s_headerValue;
						break;
					case "accept":
						this.s_accept = s_headerValue;
						break;
					case "accept-charset":
						this.s_acceptCharset = s_headerValue;
						break;
					case "authorization":
						this.s_authorization = s_headerValue;
						break;
				}
			}
		}
		
		/* convert request path to a system directory path */
		this.s_convertedPath = this.s_requestPath.replace("/", net.forestany.forestj.lib.io.File.DIR);
		
		/* do we request an element within a path or do we stay on top-level? */
		if (!this.s_convertedPath.contentEquals(net.forestany.forestj.lib.io.File.DIR)) {
			/* remove first directory character */
			this.s_convertedPath = this.s_convertedPath.substring(1);
			
			String s_tempPath = "";
			String s_tempFile = "";
			String s_tempParameters = "";
			
			/* fill temp path from start of converted path to last folder */ 
			if (this.s_convertedPath.contains(net.forestany.forestj.lib.io.File.DIR)) {
				s_tempPath =  this.s_convertedPath.substring(0, this.s_convertedPath.lastIndexOf(net.forestany.forestj.lib.io.File.DIR));
			}
			
			/* remove last directory character if converted path ends with 'wsdl\' or 'WSDL\' */
			if ( (this.s_convertedPath.endsWith("wsdl" + net.forestany.forestj.lib.io.File.DIR)) || (this.s_convertedPath.endsWith("WSDL" + net.forestany.forestj.lib.io.File.DIR)) ) {
				this.s_convertedPath = this.s_convertedPath.substring(0, this.s_convertedPath.length() - 1);
			}
			
			/* check for request parameters in request url */
			if (this.s_convertedPath.contains("?")) {
				/* read file and parameters from path */
				s_tempFile = this.s_convertedPath.substring(this.s_convertedPath.lastIndexOf(net.forestany.forestj.lib.io.File.DIR) + 1, this.s_convertedPath.lastIndexOf("?"));
				s_tempParameters =  this.s_convertedPath.substring(this.s_convertedPath.lastIndexOf("?") + 1);
				
				String[] a_parameters = null;
				
				/* check if we have multiple request parameters divided by '&' or just one key value parameter */
				if (this.s_convertedPath.contains("&")) {
					a_parameters = s_tempParameters.split("&");
				} else {
					a_parameters = new String[]{s_tempParameters};
				}
				
				/* iterate each request parameter */
				for (String s_parameterPair : a_parameters) {
					/* key and value are divided by '=' */
					if (s_parameterPair.contains("=")) {
						/* get request parameter key and value */
						String s_parameterKey = s_parameterPair.substring(0, s_parameterPair.indexOf("="));
						String s_parameterValue = s_parameterPair.substring(s_parameterPair.indexOf("=") + 1);
						
						/* decode request parameter key and value, because they could be encoded, e.g. ' ' -> '%20' */
						try {
							s_parameterKey = java.net.URLDecoder.decode(s_parameterKey, java.nio.charset.StandardCharsets.UTF_8.name());
							s_parameterValue = java.net.URLDecoder.decode(s_parameterValue, java.nio.charset.StandardCharsets.UTF_8.name());
						} catch (java.io.UnsupportedEncodingException o_exc) {
							net.forestany.forestj.lib.Global.ilogSevere("500 Internal Server Error: could not decode URI parameter pair with charset 'UTF-8': " + o_exc.getMessage());
							return 500;
						}
						
						this.a_parameters.put(s_parameterKey, s_parameterValue);
					} else { /* or the key has just no value -> null */
						this.a_parameters.put(s_parameterPair, null);
					}
				}
			} else { /* no request parameters, just read request file from converted path */
				s_tempFile = this.s_convertedPath.substring(this.s_convertedPath.lastIndexOf(net.forestany.forestj.lib.io.File.DIR) + 1);
			}
			
			/* check if we automatically do a redirect with 301, if parameter is false, file from converted path is empty, file from converted path has no '.' and request parameter wsdl/WSDL does not exist in request */
			if ( (!p_b_no301) && (!net.forestany.forestj.lib.Helper.isStringEmpty(s_tempFile)) && (!s_tempFile.contains(".")) && (!this.a_parameters.containsKey("wsdl")) && (!this.a_parameters.containsKey("WSDL")) ) {
				net.forestany.forestj.lib.Global.ilogWarning("301 Moved Permanently: Request is not a file '" + s_tempFile + "', so we just add a '/' at the end and redirect");
				return 301;
			}
			
			/* decode request path and file, because they could be encoded, e.g. ' ' -> '%20' */
			try {
				this.s_path = java.net.URLDecoder.decode(s_tempPath, java.nio.charset.StandardCharsets.UTF_8.name());
				this.s_file = java.net.URLDecoder.decode(s_tempFile, java.nio.charset.StandardCharsets.UTF_8.name());
			} catch (java.io.UnsupportedEncodingException o_exc) {
				net.forestany.forestj.lib.Global.ilogSevere("500 Internal Server Error: could not decode URI path and file with charset 'UTF-8': " + o_exc.getMessage());
				return 500;
			}

			/* if referer in header is not empty and does not contain a '.' at the end */
			if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_referrer)) && (!this.s_referrer.substring(this.s_referrer.lastIndexOf("/")).contains(".")) ) {
				/* get relative path without domain */
				String s_relativePath = this.s_referrer.substring(this.s_domain.length());
				
				/* remove first '/' character */
				if (s_relativePath.startsWith("/")) {
					s_relativePath = s_relativePath.substring(1);
				}
				
				/* add '/' at the end if it is missing */
				if (!s_relativePath.endsWith("/")) {
					s_relativePath += "/";
				}
				
				/* convert relative path to a system directory path */
				s_relativePath = s_relativePath.replace("/", net.forestany.forestj.lib.io.File.DIR);

				/* if it is not starting with relative path, change path value with relative value */
				if (!this.s_path.startsWith(s_relativePath)) {
					this.s_path = s_relativePath + this.s_path;
				}
			}
		}
		
		/* check if request path URI is not to long */
		if (this.s_requestPath.length() > 2048) {
			net.forestany.forestj.lib.Global.ilogWarning("414 URI Too Long: Request uri to long[" + this.s_requestPath.length() + "]");
			return 414;
		}
		
		/* http request header passed successfully, return 200 */
		return 200;
	}
	
	/**
	 * this method will return a http 1.0/1.1 valid request header
	 */
	public String toString() {
		String s_foo = "";
		String s_foo2 = this.s_method;
		
		if (this.s_method.contentEquals("DOWNLOAD")) {
			s_foo2 = "GET";
		}
		
		/* create first line with method, path and protocol */
		s_foo += s_foo2 + " " + ( (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_requestPath)) ? this.s_requestPath : "/" ) + " HTTP/1.1" + Config.HTTP_LINEBREAK; 
		
		/* add accept value if it is not empty */
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_accept)) {
			s_foo += "Accept: " + this.s_accept + Config.HTTP_LINEBREAK;
		}
		
		/* add accept charset value if it is not empty */
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_acceptCharset)) {
			s_foo += "Accept-Charset: " + this.s_acceptCharset + Config.HTTP_LINEBREAK;
		}
		
		/* set no cache control if it is set to true */
		if (this.b_noCacheControl) {
			s_foo += "Cache-Control: no-cache" + Config.HTTP_LINEBREAK;
		}
		
		/* set connection to 'keep-alive' or 'close' */
		s_foo += "Connection: " + ( (!this.b_connectionClosed) ? "keep-alive" : "close" ) + Config.HTTP_LINEBREAK;
		
		/* add further keep-alive settings */
		if (!this.b_connectionClosed) {
			s_foo += "Keep-Alive: timeout=5, max=10" + Config.HTTP_LINEBREAK;
		}
		
		/* add host value, is required for any request */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_host)) {
			throw new IllegalArgumentException("No host value specified for https client header");
		} else {
			s_foo += "Host: " + this.s_host + Config.HTTP_LINEBREAK;
		}
		
		/* add user agent value, from properties or from constant */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_userAgent)) {
			s_foo += "User-Agent: " + RequestHeader.CLIENT + Config.HTTP_LINEBREAK;
		} else {
			s_foo += "User-Agent: " + this.s_userAgent + Config.HTTP_LINEBREAK;
		}
		
		/* add content type value if it is not empty */
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_contentType)) {
			s_foo += "Content-Type: " + this.s_contentType + Config.HTTP_LINEBREAK;
		}
		
		/* add content length if it is greater equal zero */
		if (this.i_contentLength >= 0) {
			s_foo += "Content-Length: " + this.i_contentLength + Config.HTTP_LINEBREAK;
		}
		
		/* add cookie value if it is not empty */
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_cookie)) {
			s_foo += "Cookie: " + this.s_cookie + Config.HTTP_LINEBREAK;
		}
		
		/* add authorization value if it is not empty */
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_authorization)) {
			s_foo += "Authorization: " + this.s_authorization + Config.HTTP_LINEBREAK;
		}
		
		/* add line break, so we have two line breaks after http request header - this will always separate request header from request body(POST) */
		s_foo += Config.HTTP_LINEBREAK;
		
		return s_foo;
	}
}
