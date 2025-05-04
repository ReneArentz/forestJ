package net.forestany.forestj.lib.net.https;

/**
 * Configuration class for tiny https/soap/rest server object. All configurable settings are listed and adjustable in this class. Please look on the comments of the set-property-methods to see further details.
 */
public class Config {

	/* Constants */
	
	/**
	 * HTTP_LINEBREAK constant
	 */
	public static final String HTTP_LINEBREAK = "\r\n";
	/**
	 * IN_ENCODING constant
	 */
	public static final String IN_ENCODING = "UTF-8";
	/**
	 * OUT_ENCODING constant
	 */
	public static final String OUT_ENCODING = "UTF-8";
	/**
	 * KNOWN_EXTENSION_LIST as constant list
	 */
	public static final java.util.Map<String, String> KNOWN_EXTENSION_LIST = java.util.Map.ofEntries(
		java.util.Map.entry(".avif", "image/avif"),
		java.util.Map.entry(".bmp", "image/bmp"),
		java.util.Map.entry(".css", "text/css"),
		java.util.Map.entry(".csv", "text/csv"),
		java.util.Map.entry(".eot", "application/vnd.ms-fontobject"),
		java.util.Map.entry(".gif", "image/gif"),
		java.util.Map.entry(".htm", "text/html"),
		java.util.Map.entry(".html", "text/html"),
		java.util.Map.entry(".ico", "image/x-icon"),
		java.util.Map.entry(".jpeg", "image/jpeg"),
		java.util.Map.entry(".jpg", "image/jpeg"),
		java.util.Map.entry(".js", "text/javascript"),
		java.util.Map.entry(".json", "application/json"),
		java.util.Map.entry(".jsonld", "application/ld+json"),
		java.util.Map.entry(".otf", "font/otf"),
		java.util.Map.entry(".pdf", "application/pdf"),
		java.util.Map.entry(".png", "image/png"),
		java.util.Map.entry(".rtf", "application/rtf"),
		java.util.Map.entry(".svg", "image/svg+xml"),
		java.util.Map.entry(".tif", "image/tiff"),
		java.util.Map.entry(".tiff", "image/tiff"),
		java.util.Map.entry(".ttf", "font/ttf"),
		java.util.Map.entry(".txt", "text/plain"),
		java.util.Map.entry(".webp", "image/webp"),
		java.util.Map.entry(".woff", "font/woff"),
		java.util.Map.entry(".woff2", "font/woff2"),
		java.util.Map.entry(".wsdl", "text/xml"),
		java.util.Map.entry(".xhtml", "application/xhtml+xml"),
		java.util.Map.entry(".xml", "application/xml"),
		java.util.Map.entry(".xsd", "text/xml"),
		java.util.Map.entry(".xslt", "text/xml")
	);
	
	/* Fields */
	
	private Mode e_mode;
	private net.forestany.forestj.lib.net.sock.recv.ReceiveType e_socketReceiveType;
	private int i_executorServicePoolAmount;
	private String s_domain;
	private String s_host;
	private int i_port;
	private int i_timeoutMilliseconds;
	private int i_maxTerminations;
	private int i_maxPayload;
	private int i_amountCyclesToleratingDelay;
	private javax.net.ssl.SSLContext o_sslContext;
	private boolean b_debugNetworkTrafficOn;
	private boolean b_printExceptionStracktrace;
	
	private boolean b_checkReachability;
	private int i_intervalMilliseconds;
	private net.forestany.forestj.lib.net.sock.send.SendTCP<java.net.Socket> o_sendingSocketInstanceForHttpClient;
	
	private String s_rootDirectory;
	private java.util.List<String> a_allowSourceList;
	private boolean b_notUsingCookies;
	private boolean b_clientUseCookiesFromPreviousRequest;
	private String s_sessionDirectory;
	private net.forestany.forestj.lib.DateInterval o_sessionMaxAge;
	private boolean b_sessionRefresh;
	private java.util.Map<String, String> a_allowExtensionList;
	
	private java.nio.charset.Charset o_inEncoding;
	private java.nio.charset.Charset o_outEncoding;
	
	private net.forestany.forestj.lib.net.https.dynm.ForestSeed o_forestSeed;
	private net.forestany.forestj.lib.net.https.soap.WSDL o_wsdl;
	private net.forestany.forestj.lib.net.https.rest.ForestREST o_forestREST;
	
	/* Properties */
	
	/**
	 * get mode
	 * 
	 * @return Mode
	 */
 	public Mode getMode() {
		return this.e_mode;
	}
	
	/**
	 * set mode
	 * 
	 * @param p_e_value						determine mode for tiny https server: NORMAL, DYNAMIC, SOAP or REST
	 * @see Mode
	 */
	public void setMode(Mode p_e_value) {
		this.e_mode = p_e_value;
	}
	
	/**
	 * get socket receive type
	 * 
	 * @return net.forestany.forestj.lib.net.sock.recv.ReceiveType
	 */
	public net.forestany.forestj.lib.net.sock.recv.ReceiveType getSocketReceiveType() {
		return this.e_socketReceiveType;
	}
	
	/**
	 * set socket receive type
	 * 
	 * @param p_e_value						determine receive type for socket: SOCKET or SERVER
	 * @see net.forestany.forestj.lib.net.sock.recv.ReceiveType
	 */
	public void setSocketReceiveType(net.forestany.forestj.lib.net.sock.recv.ReceiveType p_e_value) {
		this.e_socketReceiveType = p_e_value;
	}
	
	/**
	 * get executor service pool amount
	 * 
	 * @return int
	 */
	public int getExecutorServicePoolAmount() {
		return this.i_executorServicePoolAmount;
	}
	
	/**
	 * set executor service pool amount
	 * 
	 * @param p_i_value						integer value for fixed amount of threads for thread pool instance of tiny https server object
	 * @throws IllegalArgumentException		negative integer value as parameter
	 */
	public void setExecutorServicePoolAmount(int p_i_value) throws IllegalArgumentException {
		if (p_i_value < 0) {
			throw new IllegalArgumentException("Socket executor service pool amount must be at least '0', but was set to '" + p_i_value + "'");
		}
		
		this.i_executorServicePoolAmount = p_i_value;
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
	 * @param p_s_value							domain parameter value
	 * @throws NullPointerException				parameter value is null
	 * @throws IllegalArgumentException			parameter value does not start with 'https://'
	 */
	public void setDomain(String p_s_value) throws NullPointerException, IllegalArgumentException {
		/* check if domain value is not empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			throw new NullPointerException("Domain parameter is null");
		}
		
		/* check if domain value starts with https:// */
		if (!p_s_value.startsWith("https://")) {
			throw new IllegalArgumentException("Domain parameter does not start with 'https://'");
		}
		
		this.s_domain = p_s_value;
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
	 * @param p_s_value						host parameter value
	 * @throws NullPointerException			parameter value is null
	 */
	public void setHost(String p_s_value) throws NullPointerException {
		/* check if host value is not empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			throw new NullPointerException("Host parameter is null");
		}
		
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
	 * @param p_i_value							port parameter value
	 * @throws IllegalArgumentException			invalid port parameter
	 */
	public void setPort(int p_i_value) throws IllegalArgumentException {
		/* check port min. value */
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Port must be at least '1', but was set to '" + p_i_value + "'");
		}
		
		/* check port max. value */
		if (p_i_value > 65535) {
			throw new IllegalArgumentException("Port must be lower equal '65535', but was set to '" + p_i_value + "'");
		} 
		
		this.i_port = p_i_value;
	}
	
	/**
	 * get timeout milliseconds
	 * 
	 * @return int
	 */
	public int getTimeoutMilliseconds() {
		return this.i_timeoutMilliseconds;
	}
	
	/**
	 * set timeout milliseconds
	 * 
	 * @param p_i_value						integer value for timeout in milliseconds of socket object - how long will a receive socket block execution
	 * @throws IllegalArgumentException		invalid parameter value
	 */
	public void setTimeoutMilliseconds(int p_i_value) throws IllegalArgumentException {
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Receiver timeout must be at least '1' millisecond, but was set to '" + p_i_value + "' millisecond(s)");
		}
		
		this.i_timeoutMilliseconds = p_i_value;
	}
	
	/**
	 * get max terminations
	 * 
	 * @return int
	 */
	public int getMaxTerminations() {
		return this.i_maxTerminations;
	}
	
	/**
	 * set max terminations
	 * 
	 * @param p_i_value						set a max. value for thread executions of socket instance
	 */
	public void setMaxTerminations(int p_i_value) {
		if (p_i_value < 0) {
			p_i_value = -1;
		}
		
		this.i_maxTerminations = p_i_value;
	}
	
	/**
	 * get max payload
	 * 
	 * @return int
	 */
	public int getMaxPayload() {
		return this.i_maxPayload;
	}
	
	/**
	 * set max payload
	 * 
	 * @param p_i_value						set a max. value for receiving payload in MiB (1..999)
	 * @throws IllegalArgumentException		invalid parameter value
	 */
	public void setMaxPayload(int p_i_value) throws IllegalArgumentException {
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Receive max. unknown amount in MiB must be at least '1', but was set to '" + p_i_value + "'");
		} else if (p_i_value > 999) {
			throw new IllegalArgumentException("Receive max. unknown amount in MiB must be lower than '999', but was set to '" + p_i_value + "'");
		} else {
			this.i_maxPayload = p_i_value;
		}
	}
	
	/**
	 * get amount cycles tolerating delay
	 * 
	 * @return int
	 */
	public int getAmountCyclesToleratingDelay() {
		return this.i_amountCyclesToleratingDelay;
	}

	/**
	 * set amount cycles tolerating delay
	 * 
	 * @param p_i_amountCyclesToleratingDelay				set amount of cycles tolerating delay (1[50ms]..100[5000ms]) while receiving data
	 * @throws IllegalArgumentException						invalid parameter value
	 */
	public void setAmountCyclesToleratingDelay(int p_i_amountCyclesToleratingDelay) throws IllegalArgumentException  {
		if (p_i_amountCyclesToleratingDelay < 1) {
			throw new IllegalArgumentException("Amount of cycles tolerating delay (1 cycle = 50ms) must be at least '1', but was set to '" + p_i_amountCyclesToleratingDelay + "'");
		} else if (p_i_amountCyclesToleratingDelay > 100) {
			throw new IllegalArgumentException("Amount of cycles tolerating delay (1 cycle = 50ms) must be lower than '100 (5000ms)', but was set to '" + p_i_amountCyclesToleratingDelay + "'");
		} else {
			this.i_amountCyclesToleratingDelay = p_i_amountCyclesToleratingDelay;
		}
	}
	
	/**
	 * get ssl context
	 * 
	 * @return javax.net.ssl.SSLContext
	 */
	public javax.net.ssl.SSLContext getSSLContext() {
		return this.o_sslContext;
	}
	
	/**
	 * set ssl context
	 * 
	 * @param p_o_value						instantiated ssl context object
	 * @throws NullPointerException			ssl context parameter is null
	 */
	public void setSSLContext(javax.net.ssl.SSLContext p_o_value) throws NullPointerException {
		if (p_o_value == null) {
			throw new NullPointerException("SSL context parameter is null");
		}
		
		this.o_sslContext = p_o_value;
	}
		
	/**
	 * get debug network traffic on flag
	 * 
	 * @return boolean
	 */
	public boolean getDebugNetworkTrafficOn() {
		return this.b_debugNetworkTrafficOn;
	}
	
	/**
	 * set debug network traffic on flag
	 * 
	 * @param p_b_value						true - show detailed network traffic in internal log
	 */
	public void setDebugNetworkTrafficOn(boolean p_b_value) {
		this.b_debugNetworkTrafficOn = p_b_value;
	}
	
	/**
	 * get print exception stack trace flag
	 * 
	 * @return boolean
	 */
	public boolean getPrintExceptionStracktrace() {
		return this.b_printExceptionStracktrace;
	}
	
	/**
	 * set print exception stack trace flag
	 * 
	 * @param p_b_value						true - show detailed of occurred exception of serve/client task
	 */
	public void setPrintExceptionStracktrace(boolean p_b_value) {
		this.b_printExceptionStracktrace = p_b_value;
	}
	
	/**
	 * get check reachability flag
	 * 
	 * @return boolean
	 */
	public boolean getCheckReachability() {
		return this.b_checkReachability;
	}
	
	/**
	 * set check reachability flag
	 * 
	 * @param p_b_value						true - check reachability of configured destination host + port
	 */
	public void setCheckReachability(boolean p_b_value) {
		this.b_checkReachability = p_b_value;
	}
	
	/**
	 * get interval milliseconds
	 * 
	 * @return int
	 */
	public int getIntervalMilliseconds() {
		return this.i_intervalMilliseconds;
	}
	
	/**
	 * set interval milliseconds
	 * 
	 * @param p_i_value						integer value for interval in milliseconds of socket object - how long will a sender socket wait for new data
	 * @throws IllegalArgumentException		invalid parameter value
	 */
	public void setIntervalMilliseconds(int p_i_value) throws IllegalArgumentException {
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Sender interval must be at least '1' millisecond, but was set to '" + p_i_value + "' millisecond(s)");
		}
		
		this.i_intervalMilliseconds = p_i_value;
	}
	
	/**
	 * get sending socket instance
	 * 
	 * @return net.forestany.forestj.lib.net.sock.send.SendTCP&lt;?&gt; sending socket instance
	 */
	public net.forestany.forestj.lib.net.sock.send.SendTCP<?> getSendingSocketInstance() {
		return this.o_sendingSocketInstanceForHttpClient;
	}
	
	/**
	 * set sending socket instance
	 * 
	 * @param p_o_value						sending socket instance parameter value
	 * @throws NullPointerException			parameter value is null
	 */
	public void setSendingSocketInstanceForHttpClient(net.forestany.forestj.lib.net.sock.send.SendTCP<?> p_o_value) throws NullPointerException {
		/* check if sending socket instance is null */
		if (p_o_value == null) {
			throw new NullPointerException("Sending socket instance parameter is null");
		}
		
		@SuppressWarnings("unchecked")
		net.forestany.forestj.lib.net.sock.send.SendTCP<java.net.Socket> o_foo = (net.forestany.forestj.lib.net.sock.send.SendTCP<java.net.Socket>) p_o_value;
		this.o_sendingSocketInstanceForHttpClient = o_foo;
	}
	
	/**
	 * get root directory
	 * 
	 * @return String
	 */
	public String getRootDirectory() {
		return this.s_rootDirectory;
	}
	
	/**
	 * set root directory
	 * 
	 * @param p_s_value						root directory parameter value
	 * @throws NullPointerException			parameter value is null
	 */
	public void setRootDirectory(String p_s_value) throws NullPointerException {
		/* check if root directory value is not empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			throw new NullPointerException("Root directory parameter is null");
		}
		
		if (!p_s_value.endsWith(net.forestany.forestj.lib.io.File.DIR)) {
			p_s_value += net.forestany.forestj.lib.io.File.DIR;
		}
		
		this.s_rootDirectory = p_s_value;
	}
	
	/**
	 * get allow source list
	 * 
	 * @return java.util.List&lt;String&gt;
	 */
	public java.util.List<String> getAllowSourceList() {
		return this.a_allowSourceList;
	}
	
	/**
	 * set allow source list
	 * 
	 * @param p_a_value						instantiated allow source list object
	 * @throws NullPointerException			allow list parameter is null
	 */
	public void setAllowSourceList(java.util.List<String> p_a_value) throws NullPointerException {
		if (p_a_value == null) {
			throw new NullPointerException("Allow source list parameter is null");
		}
		
		this.a_allowSourceList = p_a_value;
	}
	
	/**
	 * get not using cookies flag
	 * 
	 * @return boolean
	 */
	public boolean getNotUsingCookies() {
		return this.b_notUsingCookies;
	}
	
	/**
	 * set not using cookies flag
	 * 
	 * @param p_b_value						true - session cookies will not be used and are not available
	 */
	public void setNotUsingCookies(boolean p_b_value) {
		this.b_notUsingCookies = p_b_value;
	}
	
	/**
	 * get client use cookies from previous request flag
	 * 
	 * @return boolean
	 */
	public boolean getClientUseCookiesFromPreviousRequest() {
		return this.b_clientUseCookiesFromPreviousRequest;
	}
	
	/**
	 * set client use cookies from previous request flag
	 * 
	 * @param p_b_value						true - use session cookies from previous client request, only client
	 */
	public void setClientUseCookiesFromPreviousRequest(boolean p_b_value) {
		this.b_clientUseCookiesFromPreviousRequest = p_b_value;
	}
	
	/**
	 * get session directory
	 * 
	 * @return String
	 */
	public String getSessionDirectory() {
		return this.s_sessionDirectory;
	}
	
	/**
	 * set session directory
	 * 
	 * @param p_s_value						session directory parameter value
	 * @throws NullPointerException			parameter value is null
	 */
	public void setSessionDirectory(String p_s_value) throws NullPointerException {
		/* check if root directory value is not empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			throw new NullPointerException("Session directory parameter is null");
		}
		
		this.s_sessionDirectory = p_s_value;
	}
	
	/**
	 * get session max age
	 * 
	 * @return net.forestany.forestj.lib.DateInterval
	 */
	public net.forestany.forestj.lib.DateInterval getSessionMaxAge() {
		return this.o_sessionMaxAge;
	}
	
	/**
	 * set session max age
	 * 
	 * @param p_o_value							cookie max. age parameter value, if parameter is null -> default value is '10 minutes'
	 * @throws IllegalArgumentException			parameter value is greater than '7 days'
	 */
	public void setSessionMaxAge(net.forestany.forestj.lib.DateInterval p_o_value) throws IllegalArgumentException {
		if (p_o_value != null) {
			if (p_o_value.toDurationInSeconds() > new net.forestany.forestj.lib.DateInterval("P7D").toDurationInSeconds()) {
				throw new IllegalArgumentException("MaxAge setting for cookies must not be greater than '7 day(s)', but was set to '" + p_o_value.toString(java.util.Locale.ENGLISH) + "'");
			}
		} else {
			/* use default max. age of 10 minutes */
			p_o_value = new net.forestany.forestj.lib.DateInterval("PT10M");
		}
		
		this.o_sessionMaxAge = p_o_value;
	}
	
	/**
	 * get session refresh flag
	 * 
	 * @return boolean
	 */
	public boolean getSessionRefresh() {
		return this.b_sessionRefresh;
	}
	
	/**
	 * set session refresh flag
	 * 
	 * @param p_b_value						true - session uuid will be refreshed on every request
	 */
	public void setSessionRefresh(boolean p_b_value) {
		this.b_sessionRefresh = p_b_value;
	}
		
	/**
	 * get allow extension list
	 * 
	 * @return java.util.Map&lt;String, String&gt; instantiated allow extension list
	 */
	public java.util.Map<String, String> getAllowExtensionList() {
		return this.a_allowExtensionList;
	}
	
	/**
	 * set allow extension list
	 * 
	 * @param p_a_value						instantiated allow extension list object
	 * @throws NullPointerException			allow list parameter is null
	 */
	public void setAllowExtensionList(java.util.Map<String, String> p_a_value) throws NullPointerException {
		if (p_a_value == null) {
			throw new NullPointerException("Allow extension list parameter is null");
		}
		
		this.a_allowExtensionList = p_a_value;
	}
	
	/**
	 * get incoming encoding
	 * 
	 * @return java.nio.charset.Charset
	 */
	public java.nio.charset.Charset getInEncoding() {
		return this.o_inEncoding;
	}
	
	/**
	 * set incoming encoding
	 * 
	 * @param p_o_value						in encoding object
	 * @throws NullPointerException			in encoding parameter is null
	 */
	public void setInEncoding(java.nio.charset.Charset p_o_value) throws NullPointerException {
		if (p_o_value == null) {
			throw new NullPointerException("Incoming encoding parameter is null");
		}
		
		this.o_inEncoding = p_o_value;
	}
	
	/**
	 * set incoming encoding by name
	 * 
	 * @param p_s_value						in encoding name
	 * @throws NullPointerException			in encoding parameter is null
	 */
	public void setInEncodingByName(String p_s_value) throws NullPointerException {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			throw new NullPointerException("Incoming encoding parameter is null");
		}
		
		this.o_inEncoding = java.nio.charset.Charset.forName(p_s_value);
	}
	
	/**
	 * get outgoing encoding
	 * 
	 * @return java.nio.charset.Charset
	 */
	public java.nio.charset.Charset getOutEncoding() {
		return this.o_outEncoding;
	}
	
	/**
	 * set outgoing encoding
	 * 
	 * @param p_o_value						out encoding object
	 * @throws NullPointerException			out encoding parameter is null
	 */
	public void setOutEncoding(java.nio.charset.Charset p_o_value) throws NullPointerException {
		if (p_o_value == null) {
			throw new NullPointerException("Outgoing encoding parameter is null");
		}
		
		this.o_outEncoding = p_o_value;
	}
	
	/**
	 * set outgoing encoding by name
	 * 
	 * @param p_s_value						out encoding name
	 * @throws NullPointerException			out encoding parameter is null
	 */
	public void setOutEncodingByName(String p_s_value) throws NullPointerException {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			throw new NullPointerException("Outgoing encoding parameter is null");
		}
		
		this.o_outEncoding = java.nio.charset.Charset.forName(p_s_value);
	}
	
	/**
	 * get forest seed instance
	 * 
	 * @return net.forestany.forestj.lib.net.https.dynm.ForestSeed
	 */
	public net.forestany.forestj.lib.net.https.dynm.ForestSeed getForestSeed() {
		return this.o_forestSeed;
	}
	
	/**
	 * set forest seed instance
	 * 
	 * @param p_o_value						index branch object instance
	 * @throws NullPointerException			index branch object instance parameter is null
	 */
	public void setForestSeed(net.forestany.forestj.lib.net.https.dynm.ForestSeed p_o_value) throws NullPointerException {
		if (p_o_value == null) {
			throw new NullPointerException("Index branch object instance is null");
		}
		
		this.o_forestSeed = p_o_value;
	}
	
	/**
	 * get wsdl instance
	 * 
	 * @return net.forestany.forestj.lib.net.https.soap.WSDL
	 */
	public net.forestany.forestj.lib.net.https.soap.WSDL getWSDL() {
		return this.o_wsdl;
	}
	
	/**
	 * set wsdl instance
	 * 
	 * @param p_o_value						wsdl object instance
	 * @throws NullPointerException			wsdl object instance parameter is null
	 */
	public void setWSDL(net.forestany.forestj.lib.net.https.soap.WSDL p_o_value) throws NullPointerException {
		if (p_o_value == null) {
			throw new NullPointerException("WSDL object instance is null");
		}
		
		this.o_wsdl = p_o_value;
	}
	
	/**
	 * get forest rest instance
	 * 
	 * @return net.forestany.forestj.lib.net.https.rest.ForestREST
	 */
	public net.forestany.forestj.lib.net.https.rest.ForestREST getForestREST() {
		return this.o_forestREST;
	}
	
	/**
	 * set forest rest instance
	 * 
	 * @param p_o_value						REST object instance
	 * @throws NullPointerException			REST object instance parameter is null
	 */
	public void setForestREST(net.forestany.forestj.lib.net.https.rest.ForestREST p_o_value) throws NullPointerException {
		if (p_o_value == null) {
			throw new NullPointerException("REST object instance is null");
		}
		
		this.o_forestREST = p_o_value;
	}
	
	/* Methods */
	
	/**
	 * Constructor of configuration class. Using NORMAL mode and SERVER type. All other settings are adjusted by set-property-methods
	 *
	 * @param p_s_domain						determine domain value for tiny https server configuration
	 * @throws IllegalArgumentException			parameter value does not start with 'https://'
	 */
	public Config(String p_s_domain) throws IllegalArgumentException {
		this(p_s_domain, Mode.NORMAL, net.forestany.forestj.lib.net.sock.recv.ReceiveType.SERVER);
	}
	
	/**
	 * Constructor of configuration class. Using SERVER type. All other settings are adjusted by set-property-methods
	 * 
	 * @param p_s_domain						determine domain value for tiny https server configuration
	 * @param p_e_mode							determine mode for tiny https server: NORMAL, DYNAMIC, SOAP or REST
	 * @throws IllegalArgumentException			parameter value does not start with 'https://'
	 */
	public Config(String p_s_domain, Mode p_e_mode) throws IllegalArgumentException {
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
	public Config(String p_s_domain, Mode p_e_mode, net.forestany.forestj.lib.net.sock.recv.ReceiveType p_e_receiveType) throws IllegalArgumentException {
		this.e_mode = p_e_mode;
		this.e_socketReceiveType = p_e_receiveType;
		this.i_executorServicePoolAmount = 0;
		this.setDomain(p_s_domain);
		this.s_host = null;
		this.i_port = 0;
		this.i_timeoutMilliseconds = 1;
		this.i_maxTerminations = -1;
		this.i_maxPayload = net.forestany.forestj.lib.net.sock.task.Task.RECEIVE_MAX_UNKNOWN_AMOUNT_IN_MIB;
		this.i_amountCyclesToleratingDelay = net.forestany.forestj.lib.net.sock.task.Task.AMOUNT_CYCLES_TOLERATING_DELAY;
		this.o_sslContext = null;
		this.b_debugNetworkTrafficOn = false;
		this.b_printExceptionStracktrace = false;
		
		this.s_rootDirectory = null;
		this.a_allowSourceList = new java.util.ArrayList<String>();
		this.b_notUsingCookies = false;
		this.b_clientUseCookiesFromPreviousRequest = true;
		this.o_sessionMaxAge = null;
		this.b_sessionRefresh = false;
		this.s_sessionDirectory = null;
		
		this.b_checkReachability = false;
		this.i_intervalMilliseconds = 1;
		this.o_sendingSocketInstanceForHttpClient = null;
		
		this.o_forestSeed = null;
		this.o_wsdl = null;
		this.o_forestREST = null;
		
		this.setAllowExtensionList(Config.KNOWN_EXTENSION_LIST);
		this.setInEncodingByName(Config.IN_ENCODING);
		this.setOutEncodingByName(Config.OUT_ENCODING);
	}
}

