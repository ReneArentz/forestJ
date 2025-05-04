package net.forestany.forestj.lib.net.https.dynm;

/**
 * Encapsulation of Cookie data with all possible settings.
 */
public class Cookie {
	
	/* Fields */
	
	private String s_cookieUUID;
	private net.forestany.forestj.lib.DateInterval o_maxAge;
	private String s_domain;
	private String s_path;
	private boolean b_secure;
	private boolean b_httpOnly;
	private CookieSameSite e_sameSite;
	private String s_key;
	private String s_value;
	private java.util.Map<String, String> m_cookies;
	private long l_maxAge;
	private String s_expires;
	
	/* Properties */
	
	/**
	 * get cookie uuid
	 * 
	 * @return String
	 */
	public String getCookieUUID() {
		return this.s_cookieUUID;
	}
	
	/**
	 * set cookie uuid
	 * 
	 * @param p_s_value String
	 */
	public void setCookieUUID(String p_s_value) {
		this.s_cookieUUID = p_s_value;
	}
	
	/**
	 * get max age
	 * 
	 * @return net.forestany.forestj.lib.DateInterval
	 */
	public net.forestany.forestj.lib.DateInterval getMaxAge() {
		return this.o_maxAge;
	}
	
	/**
	 * set max age
	 * 
	 * @param p_o_value net.forestany.forestj.lib.DateInterval
	 */
	public void setMaxAge(net.forestany.forestj.lib.DateInterval p_o_value) {
		this.o_maxAge = p_o_value;
	}
	
	/**
	 * get domain
	 * 
	 * @return String
	 */
	public String getDomain() {
		return this.s_domain;
	}
	
	/**
	 * set domain
	 * 
	 * @param p_s_value String
	 */
	public void setDomain(String p_s_value) {
		this.s_domain = p_s_value;
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
	 * get secure
	 * 
	 * @return boolean
	 */
	public boolean getSecure() {
		return this.b_secure;
	}
	
	/**
	 * set secure
	 * 
	 * @param p_b_value boolean
	 */
	public void setSecure(boolean p_b_value) {
		this.b_secure = p_b_value;
	}
	
	/**
	 * get http only flag
	 * 
	 * @return boolean
	 */
	public boolean getHttpOnly() {
		return this.b_httpOnly;
	}
	
	/**
	 * set http only flag
	 * 
	 * @param p_b_value boolean
	 */
	public void setHttpOnly(boolean p_b_value) {
		this.b_httpOnly = p_b_value;
	}
	
	/**
	 * get same site
	 * 
	 * @return CookieSameSite
	 */
	public CookieSameSite getSameSite() {
		return this.e_sameSite;
	}
	
	/**
	 * set same site
	 * 
	 * @param p_e_value CookieSameSite
	 */
	public void setSameSite(CookieSameSite p_e_value) {
		this.e_sameSite = p_e_value;
	}
	
	/**
	 * get key
	 * 
	 * @return String
	 */
	public String getKey() {
		return this.s_key;
	}
	
	/**
	 * set key
	 * 
	 * @param p_s_value String
	 */
	public void setKey(String p_s_value) {
		this.s_key = p_s_value;
	}
	
	/**
	 * get value
	 * 
	 * @return String
	 */
	public String getValue() {
		return this.s_value;
	}
	
	/**
	 * set value
	 * 
	 * @param p_s_value String
	 */
	public void setValue(String p_s_value) {
		this.s_value = p_s_value;
	}
	
	/**
	 * add http cookie
	 * 
	 * @param p_s_key					key parameter of a cookie
	 * @param p_s_value					value parameter of a cookie
	 * @throws NullPointerException		key parameter is null or empty
	 */
	public void addHTTPCookie(String p_s_key, String p_s_value) throws NullPointerException {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_key)) {
			throw new NullPointerException("Key parameter is null");
		}
		
		this.m_cookies.put(p_s_key, p_s_value);
	}
	
	/**
	 * get max age as long
	 * 
	 * @return long
	 */
	public long getMaxAgeAsLong() {
		return this.l_maxAge;
	}
	
	/**
	 * set max age as long
	 * 
	 * @param p_l_value long
	 */
	public void setMaxAgeAsLong(long p_l_value) {
		this.l_maxAge = p_l_value;
	}
	
	/**
	 * get expires
	 * 
	 * @return String
	 */
	public String getExpires() {
		return this.s_expires;
	}
	
	/**
	 * set expires
	 * 
	 * @param p_s_value String
	 */
	public void setExpires(String p_s_value) {
		this.s_expires = p_s_value;
	}
	
	/* Methods */
	
	/**
	 * Cookie constructor, auto generating UUID if parameter is null or empty
	 * 
	 * @param p_o_maxAge				cookie max age in seconds, stored as date interval
	 * @param p_s_domain				cookie domain setting
	 */
	public Cookie(net.forestany.forestj.lib.DateInterval p_o_maxAge, String p_s_domain) {
		this.setCookieUUID( net.forestany.forestj.lib.Helper.generateUUID() );
		this.setMaxAge(p_o_maxAge);
		this.setDomain(p_s_domain);
		
		this.s_key = null;
		this.s_value = null;
		this.l_maxAge = -1;
		this.s_expires = null;
		
		this.m_cookies = new java.util.HashMap<String, String>();
	}
	
	/**
	 * Cookie constructor
	 * 
	 * @param p_s_cookieUUID			cookie UUID for clear identification
	 * @param p_o_maxAge				cookie max age in seconds, stored as date interval
	 * @param p_s_domain				cookie domain setting
	 */
	public Cookie(String p_s_cookieUUID, net.forestany.forestj.lib.DateInterval p_o_maxAge, String p_s_domain) {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_cookieUUID)) {
			this.setCookieUUID( net.forestany.forestj.lib.Helper.generateUUID() );
		} else {
			this.setCookieUUID(p_s_cookieUUID);
		}
		
		this.setMaxAge(p_o_maxAge);
		this.setDomain(p_s_domain);
		
		this.s_key = null;
		this.s_value = null;
		this.l_maxAge = -1;
		this.s_expires = null;
		
		this.m_cookies = new java.util.HashMap<String, String>();
	}
	
	/**
	 * create a http valid cookie line for response header
	 */
	public String toString() {
		return this.toString(false);
	}
	
	/**
	 * create a http valid cookie line for response header, server side only
	 * 
	 * @param p_b_received			print received cookie line of a response header with additional information like Max-Age, Expires and all cookie key-value-pairs
	 * @return 						cookie line as string
	 */
	public String toString(boolean p_b_received) {
		String s_foo = "";
		
		s_foo += "Set-Cookie: forestAny-UUID=" + this.s_cookieUUID;
		
		if (this.o_maxAge != null) {
			s_foo += "; Max-Age=" + this.o_maxAge.toDurationInSeconds();
		}
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_domain)) {
			s_foo += "; Domain=" + this.s_domain;
		}
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_path)) {
			s_foo += "; Path=" + this.s_path;
		} else {
			s_foo += "; Path=/";
		}
		
		if (this.b_secure) {
			s_foo += "; Secure";
		}
		
		if (this.b_httpOnly) {
			s_foo += "; HttpOnly";
		}
		
		if (this.e_sameSite != null) {
			if (this.e_sameSite == CookieSameSite.NONE) {
				s_foo += "; SameSite=None";
			} else if (this.e_sameSite == CookieSameSite.STRICT) {
				s_foo += "; SameSite=Strict";
			} else if (this.e_sameSite == CookieSameSite.LAX) {
				s_foo += "; SameSite=Lax";
			}
			
			if ( (this.e_sameSite == CookieSameSite.NONE) && (!this.b_secure) ) {
				s_foo += "; Secure";
			}
		}
		
		if (p_b_received) {
			s_foo += "; Max-Age(long)=" + l_maxAge;
			s_foo += "; Expires=" + s_expires;
			
			for (java.util.Map.Entry<String, String> o_cookie : this.m_cookies.entrySet()) {
				s_foo += "; " + o_cookie.getKey() + "=" + o_cookie.getValue();
			}
		}
		
		return s_foo;
	}
	
	/**
	 * create a http valid cookie line for request header, client side only
	 * 
	 * @return 						client cookie line as string
	 */
	public String clientCookieToString() {
		String s_foo = "";
		
		boolean b_first = false;
		
		for (java.util.Map.Entry<String, String> o_cookie : this.m_cookies.entrySet()) {
			if (!b_first) {
				s_foo += o_cookie.getKey() + "=" + o_cookie.getValue();
				b_first = true;
			} else {
				s_foo += ";" + o_cookie.getKey() + "=" + o_cookie.getValue();
			}
		}
		
		if (this.o_maxAge != null) {
			s_foo += "; Max-Age=" + this.o_maxAge.toDurationInSeconds();
		}
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_domain)) {
			s_foo += "; Domain=" + this.s_domain;
		}
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_path)) {
			s_foo += "; Path=" + this.s_path;
		} else {
			s_foo += "; Path=/";
		}
		
		if (this.b_secure) {
			s_foo += "; Secure";
		}
		
		if (this.b_httpOnly) {
			s_foo += "; HttpOnly";
		}
		
		if (this.e_sameSite != null) {
			if (this.e_sameSite == CookieSameSite.NONE) {
				s_foo += "; SameSite=None";
			} else if (this.e_sameSite == CookieSameSite.STRICT) {
				s_foo += "; SameSite=Strict";
			} else if (this.e_sameSite == CookieSameSite.LAX) {
				s_foo += "; SameSite=Lax";
			}
			
			if ( (this.e_sameSite == CookieSameSite.NONE) && (!this.b_secure) ) {
				s_foo += "; Secure";
			}
		}
		
		return s_foo;
	}
}
