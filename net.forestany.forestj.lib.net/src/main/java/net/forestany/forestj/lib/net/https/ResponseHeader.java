package net.forestany.forestj.lib.net.https;

/**
 * HTTP Response Header class which can hold all necessary minimal header information for a response header and a parsing method to parse received response to this object class.
 */
public class ResponseHeader {
	
	/* Constants */
	
	/**
	 * SERVER constant
	 */
	public static final String SERVER = "forestJ Tiny Https Server 1.0";
	/**
	 * RETURN_CODES as constant list
	 */
	public static final java.util.Map<Integer, String> RETURN_CODES = java.util.Map.ofEntries(
		java.util.Map.entry(200, "OK"),
		java.util.Map.entry(204, "No Content"),
		java.util.Map.entry(301, "Moved Permanently"),
		java.util.Map.entry(400, "Bad Request"),
		java.util.Map.entry(401, "Unauthorized"),
		java.util.Map.entry(403, "Forbidden"),
		java.util.Map.entry(404, "Not Found"),
		java.util.Map.entry(405, "Method Not Allowed"),
		java.util.Map.entry(413, "Payload Too Large"),
		java.util.Map.entry(414, "URI Too Long"),
		java.util.Map.entry(415, "Unsupported Media Type"),
		java.util.Map.entry(421, "Misdirected Request"),
		java.util.Map.entry(500, "Internal Server Error"),
		java.util.Map.entry(501, "Not Implemented"),
		java.util.Map.entry(505, "HTTP Version Not Supported")
	);
	
	/* Fields */
	
	private int i_returnCode;
	private String s_returnMessage;
	private int i_contentLength;
	private String s_contentType;
	private net.forestany.forestj.lib.net.https.dynm.Cookie o_cookie;
	private java.time.LocalDateTime o_lastModified;
	private boolean b_connectionKeepAlive;
	private String s_boundary;
	private String s_server;
	private String s_keepAlive;
	
	/* Properties */
	
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
	 * @param p_i_value						a valid HTTP return code
	 * @throws IllegalArgumentException		invalid HTTP return code, or a return code which is not intended by forestJ
	 */
	public void setReturnCode(int p_i_value) throws IllegalArgumentException {
		/* check return code for response */
		if (!ResponseHeader.RETURN_CODES.containsKey(p_i_value)) {
			throw new IllegalArgumentException("Invalid return code '" + p_i_value + "', use one of these return codes: [" + net.forestany.forestj.lib.Helper.joinList(ResponseHeader.RETURN_CODES.keySet().stream().collect(java.util.stream.Collectors.toList()), '|') + "]");
		}
		
		this.i_returnCode = p_i_value;
	}
	
	/**
	 * get return message
	 * 
	 * @return String
	 */
	public String getReturnMessage() {
		return this.s_returnMessage;
	}
	
	/**
	 * set return message
	 * 
	 * @param p_s_value String
	 */
	public void setReturnMessage(String p_s_value) {
		this.s_returnMessage = p_s_value;
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
	 * @param p_i_value int
	 */
	public void setContentLength(int p_i_value) {
		this.i_contentLength = p_i_value;
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
	 * get cookie
	 * 
	 * @return net.forestany.forestj.lib.net.https.dynm.Cookie
	 */
	public net.forestany.forestj.lib.net.https.dynm.Cookie getCookie() {
		return this.o_cookie;
	}
	
	/**
	 * set cookie
	 * 
	 * @param p_o_value net.forestany.forestj.lib.net.https.dynm.Cookie
	 */
	public void setCookie(net.forestany.forestj.lib.net.https.dynm.Cookie p_o_value) {
		this.o_cookie = p_o_value;
	}
	
	/**
	 * get last modified
	 * 
	 * @return java.time.LocalDateTime
	 */
	public java.time.LocalDateTime getLastModified() {
		return this.o_lastModified;
	}
	
	/**
	 * set last modified
	 * 
	 * @param p_o_value java.time.LocalDateTime
	 */
	public void setLastModified(java.time.LocalDateTime p_o_value) {
		this.o_lastModified = p_o_value;
	}
	
	/**
	 * get connection keep alive
	 * 
	 * @return boolean
	 */
	public boolean getConnectionKeepAlive() {
		return this.b_connectionKeepAlive;
	}
	
	/**
	 * set connection keep alive
	 * 
	 * @param p_b_value boolean
	 */
	public void setConnectionKeepAlive(boolean p_b_value) {
		this.b_connectionKeepAlive = p_b_value;
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
	 * get server
	 * 
	 * @return String
	 */
	public String getServer() {
		return this.s_server;
	}
	
	/**
	 * get keep alive
	 * 
	 * @return String
	 */
	public String getKeepAlive() {
		return this.s_keepAlive;
	}
	
	/* Methods */
	
	/**
	 * Response header constructor, initialize basic values
	 */
	public ResponseHeader() {
		this.i_contentLength = -1;
		this.s_contentType = "";
		this.o_cookie = null;
		this.o_lastModified = null;
		this.b_connectionKeepAlive = false;
		this.s_boundary = "";
		this.s_server = "";
		this.s_keepAlive = null;
	}
	
	/**
	 * Parse response http header to this instance and fill all recognized properties
	 * 
	 * @param p_a_responseHeaderLines	all http response header lines
	 * @return							http status code
	 */
	public int parseResponseHeader(String[] p_a_responseHeaderLines) {
		/* check if http response header lines are not empty, otherwise we have an empty message */
		if ( (p_a_responseHeaderLines == null) || (p_a_responseHeaderLines.length < 1) ) {
			net.forestany.forestj.lib.Global.ilogWarning("400 Bad Request: No response message available");
			return 400;
		}
		
		String s_firstLine = p_a_responseHeaderLines[0];
		
		/* first line must contain 2 white spaces */
		if (!s_firstLine.contains(" ")) {
			net.forestany.forestj.lib.Global.ilogWarning("400 Bad Request: Invalid first line of response '" + s_firstLine + "'");
			return 400;
		}
		
		/* split all values from first line */
		String[] a_firstLine = s_firstLine.split(" ");
		
		/* first line must contain 3 values: protocol, return code and return message */
		if (a_firstLine.length < 3) {
			net.forestany.forestj.lib.Global.ilogWarning("400 Bad Request: Invalid amount of data in first line of response '" + s_firstLine + "', must be 'HTTP-Protocol Return-Code Return-Message'");
			return 400;
		}
		
		/* check if http protocol is 1.0 or 1.1 */
		if (!( (a_firstLine[0].contentEquals("HTTP/1.1")) || (a_firstLine[0].contentEquals("HTTP/1.0")) )) {
			net.forestany.forestj.lib.Global.ilogWarning("505 HTTP Version Not Supported: Unsupported HTTP-Protocol in response '" + a_firstLine[0] + "', must be [HTTP/1.0|HTTP/1.1]'");
			return 505;
		}
		
		/* check if return code is an integer value */
		if (!net.forestany.forestj.lib.Helper.isInteger(a_firstLine[1])) {
			net.forestany.forestj.lib.Global.ilogWarning("400 Bad Request: Invalid value for 'Return-Code' response header '" + a_firstLine[1] + "', must be an integer value'");
			return 400;
		}
		
		/* get return code */
		this.i_returnCode = Integer.parseInt(a_firstLine[1]);
		
		/* get return message from the seconds white space on forward 'HTTP/1.1 200 ...' */
		this.s_returnMessage = s_firstLine.substring(s_firstLine.indexOf(" ", 12)).trim();
		
		/* iterate all other response header lines */
		for (String s_responseHeaderLine : p_a_responseHeaderLines) {
			/* header name and value are separated by a ':' */
			if (s_responseHeaderLine.contains(":")) {
				/* get header name */
				String s_headerName = s_responseHeaderLine.substring(0, s_responseHeaderLine.indexOf(":")).trim().toLowerCase();
				/* get header value */
				String s_headerValue = s_responseHeaderLine.substring(s_responseHeaderLine.indexOf(":") + 1).trim();
				
				switch (s_headerName) {
					case "connection":
						if (s_headerValue.toLowerCase().contentEquals("close")) {
							this.b_connectionKeepAlive = false;
						} else {
							this.b_connectionKeepAlive = true;
						}
						break;
					case "content-length":
						/* parse content length to an integer value */
						if (net.forestany.forestj.lib.Helper.isInteger(s_headerValue)) {
							this.i_contentLength = Integer.parseInt(s_headerValue);
						} else {
							net.forestany.forestj.lib.Global.ilogWarning("400 Bad Request: Invalid value for 'Content-Length' response header '" + s_headerValue + "', must be an integer value'");
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
					case "set-cookie":
						/* parse cookie line which can have a lot of values and settings, using Cookie class */
						net.forestany.forestj.lib.net.https.dynm.Cookie o_cookie = new net.forestany.forestj.lib.net.https.dynm.Cookie(null, null);
						
						/* split cookie line with ';' */
						String[] a_foo = s_headerValue.split(";");
						boolean b_first = false;
						
						/* iterate all cookie values and settings */
						for (String s_foo : a_foo) {
							/* cookie key and value are separated by '=' */
							String[] a_foo2 = s_foo.split("=");
							
							if (!b_first) {
								/* set key and value and store this pair as usual http cookie */
								o_cookie.setKey( a_foo2[0].trim() );
								o_cookie.setValue( a_foo2[1] );
								o_cookie.addHTTPCookie(o_cookie.getKey(), o_cookie.getValue());
								b_first = true;
							} else {
								/* parse all other values and settings */
								switch (a_foo2[0].trim().toLowerCase()) {
									case "domain":
										o_cookie.setDomain(a_foo2[1]);
										break;
									case "path":
										o_cookie.setPath(a_foo2[1]);
										break;
									case "secure":
										o_cookie.setSecure(true);
										break;
									case "httponly":
										o_cookie.setHttpOnly(true);
										break;
									case "samesite":
										net.forestany.forestj.lib.net.https.dynm.CookieSameSite e_sameSite = net.forestany.forestj.lib.net.https.dynm.CookieSameSite.NONE;
										
										if (a_foo2[1].trim().toLowerCase().contentEquals("none")) {
											e_sameSite = net.forestany.forestj.lib.net.https.dynm.CookieSameSite.NONE;
										} else if (a_foo2[1].trim().toLowerCase().contentEquals("lax")) {
											e_sameSite = net.forestany.forestj.lib.net.https.dynm.CookieSameSite.LAX;
										} else if (a_foo2[1].trim().toLowerCase().contentEquals("strict")) {
											e_sameSite = net.forestany.forestj.lib.net.https.dynm.CookieSameSite.STRICT;
										}
										
										o_cookie.setSameSite(e_sameSite);
										break;
									case "expires":
										o_cookie.setExpires(a_foo2[1]);
										break;
									case "max-age":
										/* check if max-age is a valid long value */
										if (!net.forestany.forestj.lib.Helper.isLong(a_foo2[1])) {
											net.forestany.forestj.lib.Global.ilogWarning("400 Bad Request: Invalid value for 'Max-Age' response header cookie '" + s_foo + "', must be a long value'");
											return 400;
										} else {
											o_cookie.setMaxAgeAsLong(Long.valueOf(a_foo2[1]));
										}
										break;
									default:
										/* or just add another key and value pair as usual http cookie */
										o_cookie.addHTTPCookie(a_foo2[0].trim(), a_foo2[1]);
										break;
								}
							}
						}
						
						/* store all cookie information to property */
						this.setCookie(o_cookie);
						break;
					case "server":
						this.s_server = s_headerValue;
						break;
					case "keep_alive":
						this.s_keepAlive = s_headerValue;
						break;
				}
			}
		}
		
		/* http response header passed successfully, return 200 */
		return 200;
	}
	
	/**
	 * this method will return a http 1.0/1.1 valid response header
	 */
	public String toString() {
		return this.toString(false);
	}
	
	/**
	 * this method will return a http 1.0/1.1 valid response header
	 * 
	 * @param p_b_received				true - print response header information of a received header
	 * @return String		
	 */
	public String toString(boolean p_b_received) {
		String s_foo = "";
		
		/* first line of response header: protocol, return code and return message */
		s_foo += "HTTP/1.1 " + this.i_returnCode + " " + ResponseHeader.RETURN_CODES.get(this.i_returnCode) + Config.HTTP_LINEBREAK;
		
		/* if this is a 301 response, we return this header immediately */
		if (this.i_returnCode == 301) {
			return s_foo;
		}
		
		/* add Date, Allow and Cache-Control */
		s_foo += "Date: " + net.forestany.forestj.lib.Helper.toRFC1123(java.time.LocalDateTime.now()) + Config.HTTP_LINEBREAK;
		s_foo += "Allow: GET, POST, PUT, DELETE" + Config.HTTP_LINEBREAK;
		s_foo += "Cache-Control: no-cache" + Config.HTTP_LINEBREAK;
		
		/* keep alive flag is set, but not recommended with current ReceiveTCP structure */
		if (this.b_connectionKeepAlive) {
			s_foo += "Connection: Keep-Alive" + Config.HTTP_LINEBREAK;
			
			if (!p_b_received) {
				s_foo += "Keep-Alive: timeout=5, max=10" + Config.HTTP_LINEBREAK;
			}
		} else {
			/* set connection to close */
			s_foo += "Connection: Close" + Config.HTTP_LINEBREAK;
		}
		
		/* add content length and content type */
		if (this.i_contentLength > 0) {
			s_foo += "Content-Length: " + this.i_contentLength + Config.HTTP_LINEBREAK;
			s_foo += "Content-Type: " + this.s_contentType + Config.HTTP_LINEBREAK;
		}
		
		/* call cookie toString method to add cookie to response header */
		if (this.o_cookie != null) {
			s_foo += this.o_cookie.toString() + Config.HTTP_LINEBREAK;
		}
		
		/* add last modified to response header */
		if (this.o_lastModified != null) {
			s_foo += "Last-Modified: " + net.forestany.forestj.lib.Helper.toRFC1123(this.o_lastModified) + Config.HTTP_LINEBREAK;
		}
		
		if (p_b_received) { /* show keep alive and server values from received response header */
			if (this.s_keepAlive != null) {
				s_foo += "Keep-Alive: " + this.s_keepAlive + Config.HTTP_LINEBREAK;
			}
			
			s_foo += "Server: " + this.s_server + Config.HTTP_LINEBREAK;
		} else {
			/* set server constant for own response header */
			s_foo += "Server: " + ResponseHeader.SERVER + Config.HTTP_LINEBREAK;
		}
		
		/* add line break, so we have two line breaks after http response header - this will always separate response header from response body(POST) */
		s_foo += Config.HTTP_LINEBREAK;
		
		return s_foo;
	}
}
