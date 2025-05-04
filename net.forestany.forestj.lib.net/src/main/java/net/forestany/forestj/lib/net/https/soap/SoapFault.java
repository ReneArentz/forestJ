package net.forestany.forestj.lib.net.https.soap;

/**
 * Encapsulation of a SOAP Fault message with code, message, actor and detail values.
 */
public class SoapFault {
	
	/* Fields */
	
	private String s_code;
	private String s_message;
	private String s_actor;
	private String s_detail;
	
	/* Properties */
	
	/**
	 * get code
	 * 
	 * @return String
	 */
	public String getCode() {
		return this.s_code;
	}
	
	/**
	 * set code
	 * 
	 * @param p_s_value String
	 */
	public void setCode(String p_s_value) {
		this.s_code = p_s_value;
	}
	
	/**
	 * get message
	 * 
	 * @return String
	 */
	public String getMessage() {
		return this.s_message;
	}
	
	/**
	 * set message
	 * 
	 * @param p_s_value String
	 */
	public void setMessage(String p_s_value) {
		this.s_message = p_s_value;
	}
	
	/**
	 * get actor
	 * 
	 * @return String
	 */
	public String getActor() {
		return this.s_actor;
	}
	
	/**
	 * set actor
	 * 
	 * @param p_s_value String
	 */
	public void setActor(String p_s_value) {
		this.s_actor = p_s_value;
	}
	
	/**
	 * get detail
	 * 
	 * @return String
	 */
	public String getDetail() {
		return this.s_detail;
	}
	
	/**
	 * set detail
	 * 
	 * @param p_s_value String
	 */
	public void setDetail(String p_s_value) {
		this.s_detail = p_s_value;
	}
	
	/* Methods */
	
	/**
	 * SOAP Fault constructor
	 * 
	 * @param p_s_code			code value
	 * @param p_s_message		message value
	 * @param p_s_detail		detail value (optional)
	 * @param p_s_actor			actor value (optional)
	 */
	public SoapFault(String p_s_code, String p_s_message, String p_s_detail, String p_s_actor) {
		this.setCode(p_s_code);
		this.setMessage(p_s_message);
		this.setDetail(p_s_detail);
		this.setActor(p_s_actor);
	}
	
	/**
	 * Creates a string line with all of SOAP Fault's values
	 */
	public String toString() {
		String s_foo = "SoapFault:" + net.forestany.forestj.lib.io.File.NEWLINE;
		
		s_foo += "Code = " + this.getCode() + net.forestany.forestj.lib.io.File.NEWLINE;
		s_foo += "Message = " + this.getMessage() + net.forestany.forestj.lib.io.File.NEWLINE;
		s_foo += "Detail = " + this.getDetail() + net.forestany.forestj.lib.io.File.NEWLINE;
		s_foo += "Actor = " + this.getActor();
		
		return s_foo;
	}
	
	/**
	 * Creates a complete xml SOAP Fault message for response purposes, with xml header, envelope and body
	 * 
	 * @param p_s_encoding			encoding value for the xml header attribute
	 * @return 						xml SOAP Fault message string
	 */
	public String toXML(String p_s_encoding) {
		String s_xml = "<?xml version=\"1.0\" encoding=\"" + p_s_encoding + "\" ?>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		
		s_xml += "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		s_xml += "\t<soap:Body>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		s_xml += "\t\t<soap:Fault>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		
		if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_code)) {
			s_xml += "\t\t\t<faultcode/>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		} else {
			s_xml += "\t\t\t<faultcode>" + this.s_code + "</faultcode>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		}
		
		if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_message)) {
			s_xml += "\t\t\t<faultstring/>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		} else {
			s_xml += "\t\t\t<faultstring>" + this.s_message + "</faultstring>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		}
		
		if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_detail)) {
			s_xml += "\t\t\t<detail/>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		} else {
			s_xml += "\t\t\t<detail>" + this.s_detail + "</detail>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		}
		
		if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_actor)) {
			s_xml += "\t\t\t<faultactor/>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		} else {
			s_xml += "\t\t\t<faultactor>" + this.s_actor + "</faultactor>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		}
		
		s_xml += "\t\t</soap:Fault>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		s_xml += "\t</soap:Body>" + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
		s_xml += "</soap:Envelope>";
		
		return s_xml;
	}
}
