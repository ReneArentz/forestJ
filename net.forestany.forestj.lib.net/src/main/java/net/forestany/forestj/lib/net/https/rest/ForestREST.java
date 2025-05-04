package net.forestany.forestj.lib.net.https.rest;

/**
 * Abstract class for ForestRest implementation. Inheriting class has to implement all methods to handle http methods GET, POST, PUT and DELETE.
 */
public abstract class ForestREST {
	
	/* Fields */
	
	/**
	 * seed
	 */
	protected net.forestany.forestj.lib.net.https.Seed o_seed;
	/**
	 * response content type
	 */
	protected String s_responseContentType;
	
	/* Properties */
	
	/**
	 * get seed instance
	 * 
	 * @return net.forestany.forestj.lib.net.https.Seed
	 */
	protected net.forestany.forestj.lib.net.https.Seed getSeed() {
		return this.o_seed;
	}
	
	/**
	 * get response content type
	 * 
	 * @return String
	 */
	public String getResponseContentType() {
		return this.s_responseContentType;
	}
	
	/**
	 * set response content type by using file extension value
	 * 
	 * @param p_s_fileExtension					file extension value
	 * @throws IllegalArgumentException			invalid file extension value is not listed within known extension list within net.forestany.forestj.lib.net.https.Config class
	 */
	public void setResponseContentTypeByFileExtension(String p_s_fileExtension) throws IllegalArgumentException {
		/* check file extension parameter */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_fileExtension)) {
			this.s_responseContentType = null;
			return;
		}
		
		String s_extension = null;
		
		/* check for valid extension in configured allowed list */
		for (java.util.Map.Entry<String, String> o_allowExtension : this.getSeed().getConfig().getAllowExtensionList().entrySet()) {
			if (o_allowExtension.getKey().endsWith(p_s_fileExtension)) {
				/* file extension found in allow list */
				s_extension = o_allowExtension.getKey();
				break;
			}
		}
		
		/* return 403 if file extension is forbidden */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(s_extension)) {
			throw new NullPointerException("Invalid file extension value '" + p_s_fileExtension + "' which is not allowed for response");
		}
		
		/* get content type by file extension */
		this.s_responseContentType = net.forestany.forestj.lib.net.https.Config.KNOWN_EXTENSION_LIST.get(s_extension);
	}
	
	/* Methods */
	
	/**
	 * empty constructor
	 */
	public ForestREST() {
		
	}
	
	/**
	 * The core rest method which takes a seed instance as parameter, so anything within handling http methods has access to all request/response/other resources
	 * 
	 * @param p_o_value			seed instance object
	 * @return					returning response for rest request as String
	 * @throws Exception		any exception which occurred while handling http methods
	 */
	public String handleREST(net.forestany.forestj.lib.net.https.Seed p_o_value) throws Exception {
		this.o_seed = p_o_value;
		this.s_responseContentType = null;
		
		if (this.getSeed().getRequestHeader().getMethod().contentEquals("GET")) {
			return this.handleGET();
		} else if (this.getSeed().getRequestHeader().getMethod().contentEquals("POST")) {
			return this.handlePOST();
		} else if (this.getSeed().getRequestHeader().getMethod().contentEquals("PUT")) {
			return this.handlePUT();
		} else if (this.getSeed().getRequestHeader().getMethod().contentEquals("DELETE")) {
			return this.handleDELETE();
		}
		
		throw new Exception("HTTP method " + this.getSeed().getRequestHeader().getMethod() + " with Mode " + this.getSeed().getConfig().getMode() + " not implemented");
	}
	
	/**
	 * Handling http GET method
	 * 
	 * @return					returning response for rest GET request as String
	 * @throws Exception		any exception which occurred while handling http GET method
	 */
	abstract public String handleGET() throws Exception;
	
	/**
	 * Handling http POST method
	 * 
	 * @return					returning response for rest POST request as String
	 * @throws Exception		any exception which occurred while handling http POST method
	 */
	abstract public String handlePOST() throws Exception;
	
	/**
	 * Handling http PUT method
	 * 
	 * @return					returning response for rest PUT request as String
	 * @throws Exception		any exception which occurred while handling http PUT method
	 */
	abstract public String handlePUT() throws Exception;
	
	/**
	 * Handling http DELETE method
	 * 
	 * @return					returning response for rest DELETE request as String
	 * @throws Exception		any exception which occurred while handling http DELETE method
	 */
	abstract public String handleDELETE() throws Exception;
}
