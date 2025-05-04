package net.forestany.forestj.lib.net.https.dynm;

/**
 * Abstract class for ForestSeed implementation. Makes it possible to prepare content and access other resource, like DB, and set temporary values in Seed list for rendering process.
 */
public abstract class ForestSeed {
	
	/* Fields */
	
	/**
	 * seed
	 */
	protected net.forestany.forestj.lib.net.https.Seed o_seed;
	/**
	 * line break
	 */
	protected String s_linebreak;
	
	/* Properties */
	
	/**
	 * get seed
	 * 
	 * @return net.forestany.forestj.lib.net.https.Seed
	 */
	protected net.forestany.forestj.lib.net.https.Seed getSeed() {
		return this.o_seed;
	}
	
	/**
	 * get line break
	 * 
	 * @return String
	 */
	protected String getLineBreak() {
		return this.s_linebreak;
	}
	
	/**
	 * set line break
	 * 
	 * @param p_s_value					set used line break within html or htm files which using dynamic content
	 * @throws NullPointerException		line break parameter is null or empty
	 */
	protected void setLineBreak(String p_s_value) throws NullPointerException {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			throw new NullPointerException("Line break parameter is null or empty");
		}
		
		this.s_linebreak = p_s_value;
	}
	
	/* Methods */
	
	/**
	 * empty constructor
	 */
	public ForestSeed() {
		
	}
	
	/**
	 * The core dynamic method which takes a seed instance as parameter, so anything within preparing content has access to all request/response/other resources
	 * 
	 * @param p_o_value			seed instance object
	 * @throws Exception		any exception which occurred while fetching content
	 */
	public void fetchContent(net.forestany.forestj.lib.net.https.Seed p_o_value) throws Exception {
		this.o_seed = p_o_value;
		this.s_linebreak = net.forestany.forestj.lib.io.File.NEWLINE;
		this.prepareContent();
	}
	
	/**
	 * Method which must be created and will be called during fetching content
	 * 
	 * @throws Exception		any exception which occurred while preparing content
	 */
	abstract public void prepareContent() throws Exception;
}
