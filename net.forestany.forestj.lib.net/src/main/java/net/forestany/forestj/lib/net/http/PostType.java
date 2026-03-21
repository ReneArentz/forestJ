package net.forestany.forestj.lib.net.http;

/**
 * All possible types for http(s) post actions.
 * 
 * HTMLATTACHMENTS - multipart/form-data
 * HTML - application/x-www-form-urlencoded
 * JSON - application/json
 */
public enum PostType {
	/**
	 * HTMLATTACHMENTS post type
	 */
	HTMLATTACHMENTS("multipart/form-data"),
	/**
	 * HTML post type
	 */
	HTML("application/x-www-form-urlencoded"),
	/**
	 * JSON post type
	 */
	JSON("application/json"),
    /**
     * SOAP post type
     */
    SOAPXML("application/soap+xml"),
    /**
     * XML post type
     */
    XML("application/xml"),
    /**
     * XML text post type
     */
    XMLTEXT("text/xml"),
    /**
     * SOAP post type with charset
     */
    SOAPXMLWITHCHARSET("application/soap+xml"),
    /**
     * XML post type with charset
     */
    XMLWITHCHARSET("application/xml"),
    /**
     * XML text post type with charset
     */
    XMLTEXTWITHCHARSET("text/xml");

    private String ContentType;
    
    PostType(String p_s_value) {
        this.ContentType = p_s_value;
    }
 
    /**
     * get string value behind enumeration
     * 
     * @return String
     */
    public String getContentType() {
        return this.ContentType;
    }
}
