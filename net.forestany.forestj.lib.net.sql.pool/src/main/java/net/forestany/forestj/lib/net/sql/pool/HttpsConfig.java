package net.forestany.forestj.lib.net.sql.pool;

/**
 * Configuration class for tiny https/soap/rest server object. All configurable settings are listed and adjustable in this class. Please look on the comments of the set-property-methods to see further details.
 * Supports a base pool instance.
 */
public class HttpsConfig extends net.forestany.forestj.lib.net.https.Config {
	
	/* Fields */

	private net.forestany.forestj.lib.sql.pool.BasePool o_basePool;

    /* Properties */
    
	/**
	 * get base pool instance
	 * 
	 * @return net.forestany.forestj.lib.sql.pool.BasePool
	 */
	public net.forestany.forestj.lib.sql.pool.BasePool getBasePool() {
		return this.o_basePool;
	}
	
	/**
	 * set base pool instance
	 * 
	 * @param p_o_value						Base Pool object instance (net.forestany.forestj.lib.sql.pool.BasePool)
	 * @throws NullPointerException			Base Pool object instance parameter is null
	 */
	public void setBasePool(net.forestany.forestj.lib.sql.pool.BasePool p_o_value) throws NullPointerException {
		if (p_o_value == null) {
			throw new NullPointerException("Base Pool object instance is null");
		}
		
		this.o_basePool = p_o_value;
	}
	
    /* Method */
    
	/**
	 * Constructor of configuration class. Using NORMAL mode and SERVER type. All other settings are adjusted by set-property-methods
	 *
	 * @param p_s_domain						determine domain value for tiny https server configuration
	 * @throws IllegalArgumentException			parameter value does not start with 'https://'
	 */
	public HttpsConfig(String p_s_domain) throws IllegalArgumentException {
		this(p_s_domain, net.forestany.forestj.lib.net.https.Mode.NORMAL, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER);
	}
	
	/**
	 * Constructor of configuration class. Using SERVER type. All other settings are adjusted by set-property-methods
	 * 
	 * @param p_s_domain						determine domain value for tiny https server configuration
	 * @param p_e_mode							determine mode for tiny https server: NORMAL, DYNAMIC, SOAP or REST
	 * @throws IllegalArgumentException			parameter value does not start with 'https://'
	 */
	public HttpsConfig(String p_s_domain, net.forestany.forestj.lib.net.https.Mode p_e_mode) throws IllegalArgumentException {
		this(p_s_domain, p_e_mode, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER);
	}
	
	/**
	 * Constructor of configuration class. All other settings are adjusted by set-property-methods
	 * 
	 * @param p_s_domain						determine domain value for tiny https server configuration
	 * @param p_e_mode							determine mode for tiny https server: NORMAL, DYNAMIC, SOAP or REST
	 * @param p_e_receiveType					determine receive type for socket: SOCKET or SERVER
	 * @throws IllegalArgumentException			parameter value does not start with 'https://'
	 */
	public HttpsConfig(String p_s_domain, net.forestany.forestj.lib.net.https.Mode p_e_mode, net.forestany.forestj.lib.net.sock.recv.ReceiveType p_e_receiveType) throws IllegalArgumentException {
		super(p_s_domain, p_e_mode, p_e_receiveType);
		
		this.o_basePool = null;
	}
}
