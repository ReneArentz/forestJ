package net.forestany.forestj.lib.net.ftp;

/**
 * FTP Client class for a connection to a ftp(s) server with protocols.
 * FTP, FTPS or FTPS with tls session resumption.
 * Methods work like the known commands in most CLI implementations, but will always use the complete path to a file or directory from root '/'.
 */
public class Client {

	/* Constants */
	
	/**
	 * buffer size constant
	 */
	private static final int BUFFERSIZE = 8192;
	
	/* Delegates */
	
	/**
	 * interface delegate definition which can be instanced outside of ftp.Client class to post progress anywhere of download/upload methods
	 */
	public interface IDelegate {
		/**
		 * progress method for ftp request
		 * 
		 * @param p_d_progress download or upload progress
		 */
		void PostProgress(double p_d_progress);
	}
	
	/**
	 * interface delegate definition which can be instanced outside of ftp.Client class to post progress anywhere of amount of files been processed for download/upload
	 */
	public interface IDelegateFolder {
		/**
		 * folder progress method for ftp request
		 * 
		 * @param p_i_filesProcessed update amount of files processed for download or upload
		 * @param p_i_files amount of all files
		 */
		void PostProgressFolder(int p_i_filesProcessed, int p_i_files);
	}
	
	/* Fields */
	
	private String s_host;
	private int i_port;
	private String s_user;
	private String s_password;
	private boolean b_passiveMode;
	private Protocol e_protocol;
	private org.apache.commons.net.ftp.FTPClient o_ftpClient;
	private org.apache.commons.net.ftp.FTPSClient o_ftpsClient;
	private TLSSessionResumption o_ftpsClientTLSSessionResumption;
	private javax.net.ssl.SSLContext o_sslContext;
	private javax.net.ssl.X509TrustManager o_trustManager;
	private boolean b_loggedIn;
	private boolean b_useMListDir;
	private int i_bufferSize;
	private int i_ftpReplyCode;
	private String s_ftpReply;
	private IDelegate itf_delegate;
	private IDelegateFolder itf_delegateFolder;
	private int i_dirSum;
	private int i_dirFiles;
	private boolean b_skipCompletePendingCommand;
	private net.forestany.forestj.lib.ConsoleProgressBar o_progressBar;
	
	/* Properties */
	
	/**
	 * get ftp reply code
	 * 
	 * @return int
	 */
	public int getFTPReplyCode() {
		return this.i_ftpReplyCode;
	}
	
	/**
	 * get ftp reply
	 * 
	 * @return String
	 */
	public String getFTPReply() {
		return this.s_ftpReply;
	}
	
	/**
	 * set use mlist directory flag
	 * 
	 * @param p_b_value boolean
	 */
	public void setUseMListDir(boolean p_b_value) {
		this.b_useMListDir = p_b_value;
	}
	
	/**
	 * set buffer size
	 * 
	 * @param p_i_value buffer size  value
	 * @throws IllegalArgumentException must be at least '1'
	 */
	public void setBufferSize(int p_i_value) throws IllegalArgumentException {
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Buffer size must be at least '1', not '" + p_i_value + "'");
		}
		
		this.i_bufferSize = p_i_value;
	}
	
	/**
	 * property method to use the same methods on each ftp(s) client object, without differ in each way between the three supported client protocols
	 * 
	 * @return org.apache.commons.net.ftp.FTPClient
	 */
	private org.apache.commons.net.ftp.FTPClient junction() {
		if (this.e_protocol == Protocol.FTP) {
			return this.o_ftpClient;
		} else if (this.e_protocol == Protocol.FTPS) {
			return this.o_ftpsClient;
		} else if (this.e_protocol == Protocol.FTPS_TLS_SessionResumption) {
			return this.o_ftpsClientTLSSessionResumption;
		} else {
			return null;
		}
	}
	
	/**
	 * set skip complete pending command flag
	 * 
	 * @param p_b_value boolean
	 */
	public void setSkipCompletePendingCommand(boolean p_b_value) {
		this.b_skipCompletePendingCommand = p_b_value;
	}
	
	/**
	 * set delegate
	 * 
	 * @param p_itf_delegate delegate instance
	 */
	public void setDelegate(IDelegate p_itf_delegate) {
		this.itf_delegate = p_itf_delegate;
	}
	
	/**
	 * set folder delegate
	 * 
	 * @param p_itf_delegateFolder folder delegate instance
	 */
	public void setDelegateFolder(IDelegateFolder p_itf_delegateFolder) {
		this.itf_delegateFolder = p_itf_delegateFolder;
	}
	
	/**
	 * set progress bar
	 * 
	 * @param p_o_progressBar net.forestany.forestj.lib.ConsoleProgressBar
	 */
	public void setProgressBar(net.forestany.forestj.lib.ConsoleProgressBar p_o_progressBar) {
		this.o_progressBar = p_o_progressBar;
	}
	
	/* Methods */
	
	/**
	 * Constructor for a ftp(s) client object, using uri parameter specifications.
	 * no ssl context, no trust manager, using Client.BUFFERSIZE(8192) for input/output buffer.
	 * 
	 * @param p_s_uri							ftp(s) uri, must start with 'ftp://' or 'ftps://' and contain host, port, user and password information
	 * @throws IllegalArgumentException			invalid ftp(s) uri
	 * @throws java.io.IOException				issues starting connection to the ftp(s) server
	 */
	public Client(String p_s_uri) throws IllegalArgumentException, java.io.IOException {
		this(p_s_uri, Client.BUFFERSIZE);
	}
	
	/**
	 * Constructor for a ftp(s) client object, using uri parameter specifications.
	 * no ssl context, no trust manager.
	 * 
	 * @param p_s_uri							ftp(s) uri, must start with 'ftp://' or 'ftps://' and contain host, port, user and password information
	 * @param p_i_bufferSize					specify buffer size for sending and receiving data
	 * @throws IllegalArgumentException			invalid ftp(s) uri
	 * @throws java.io.IOException				issues starting connection to the ftp(s) server
	 */
	public Client(String p_s_uri, int p_i_bufferSize) throws IllegalArgumentException, java.io.IOException {
		this(p_s_uri, p_i_bufferSize, (javax.net.ssl.SSLContext)null);
	}
	
	/**
	 * Constructor for a ftp(s) client object, using uri parameter specifications.
	 * no trust manager.
	 * 
	 * @param p_s_uri							ftp(s) uri, must start with 'ftp://' or 'ftps://' and contain host, port, user and password information
	 * @param p_i_bufferSize					specify buffer size for sending and receiving data
	 * @param p_o_sslContext					instance of ssl context for ftps client object
	 * @throws IllegalArgumentException			invalid ftp(s) uri
	 * @throws java.io.IOException				issues starting connection to the ftp(s) server
	 */
	public Client(String p_s_uri, int p_i_bufferSize, javax.net.ssl.SSLContext p_o_sslContext) throws IllegalArgumentException, java.io.IOException {
		this(p_s_uri, p_i_bufferSize, p_o_sslContext, (javax.net.ssl.X509TrustManager)null);
	}
	
	/**
	 * Constructor for a ftp(s) client object, using uri parameter specifications.
	 * no ssl context.
	 * 
	 * @param p_s_uri							ftp(s) uri, must start with 'ftp://' or 'ftps://' and contain host, port, user and password information
	 * @param p_i_bufferSize					specify buffer size for sending and receiving data
	 * @param p_o_trustManager					instance of trust manager for ftps client object
	 * @throws IllegalArgumentException			invalid ftp(s) uri
	 * @throws java.io.IOException				issues starting connection to the ftp(s) server
	 */
	public Client(String p_s_uri, int p_i_bufferSize, javax.net.ssl.X509TrustManager p_o_trustManager) throws IllegalArgumentException, java.io.IOException {
		this(p_s_uri, p_i_bufferSize, null, p_o_trustManager);
	}
	
	/**
	 * Constructor for a ftp(s) client object, using uri parameter specifications
	 * 
	 * @param p_s_uri							ftp(s) uri, must start with 'ftp://' or 'ftps://' and contain host, port, user and password information
	 * @param p_i_bufferSize					specify buffer size for sending and receiving data
	 * @param p_o_sslContext					instance of ssl context for ftps client object
	 * @param p_o_trustManager					instance of trust manager for ftps client object
	 * @throws IllegalArgumentException			invalid ftp(s) uri
	 * @throws java.io.IOException				issues starting connection to the ftp(s) server
	 */
	public Client(String p_s_uri, int p_i_bufferSize, javax.net.ssl.SSLContext p_o_sslContext, javax.net.ssl.X509TrustManager p_o_trustManager) throws IllegalArgumentException, java.io.IOException {
		this.e_protocol = null;
		this.o_ftpClient = null;
		this.o_ftpsClient = null;
		this.o_ftpsClientTLSSessionResumption = null;
		this.o_sslContext = null;
		this.o_trustManager = null;
		this.b_loggedIn = false;
		this.i_bufferSize = p_i_bufferSize;
		
		
		/* check if ftp(s) uri is valid */
		if (!net.forestany.forestj.lib.Helper.matchesRegex(p_s_uri, "(ftps://|ftp://)([a-zA-Z0-9-_]{4,}):([^\\s]{4,})@([a-zA-Z0-9-_\\.]{4,}):([0-9]{2,5})")) {
			throw new IllegalArgumentException("Invalid ftp(s) uri. Valid ftp(s) uri would be 'ftp(s)://user:password@example.com:21'");
		} else {
													net.forestany.forestj.lib.Global.ilogConfig("using uri: " + net.forestany.forestj.lib.Helper.disguiseSubstring(p_s_uri, "//", "@", '*'));
			
			String s_protocol = "ftps://";
			
			/* recognize protocol at start of uri and remove it from uri parameter */
			if (p_s_uri.startsWith("ftps://")) {
				p_s_uri = p_s_uri.substring(7);
			} else if (p_s_uri.startsWith("sftp://")) {
				p_s_uri = p_s_uri.substring(7);
				s_protocol = "sftp://";
			} else {
				p_s_uri = p_s_uri.substring(6);
				s_protocol = "ftp://";
			}
			
			/* get user and password value */
			String[] a_uriParts = p_s_uri.split("@");
			String s_user = a_uriParts[0].substring(0, a_uriParts[0].indexOf(":"));
			String s_password = a_uriParts[0].substring(a_uriParts[0].indexOf(":") + 1);
			
			String s_host = a_uriParts[1];
			int i_port = 0;
			
			/* remove last '/' character */
			if ( (s_host.indexOf("/") >= 0) && (s_host.substring(s_host.indexOf("/")).contentEquals("/")) ) {
				s_host = s_host.substring(0, (s_host.length() - 1));
			}
			
			/* get host-address and port from uri */
			i_port = Integer.valueOf(s_host.substring(s_host.indexOf(":") + 1));
			s_host = s_host.substring(0, s_host.indexOf(":"));
			
													net.forestany.forestj.lib.Global.ilogConfig("retrieved user, password, host-address and port from uri");
			
													net.forestany.forestj.lib.Global.ilogConfig("create ftp(s) wrapper with no tls session resumption and passive mode set to true");
			
			if (p_o_sslContext != null) {
														net.forestany.forestj.lib.Global.ilogConfig("create ftp(s) wrapper with ssl context parameter");
			}
			
			if (p_o_trustManager != null) {
														net.forestany.forestj.lib.Global.ilogConfig("create ftp(s) wrapper with trust manager parameter");
			}
			
			/* create ftp(s) wrapper with no tls session resumption and passive mode set to true, optional ssl context and optional trust manager possible */
			this.createFTPWrapper(s_protocol + s_host, i_port, s_user, s_password, false, true, p_o_sslContext, p_o_trustManager);
		}
	}
	
	/**
	 * Constructor for a ftp(s) client object, using separate host, port, user and password parameter specifications.
	 * no ssl context, no trust manager, using Client.BUFFERSIZE(8192) for input/output buffer, using passive mode, no tls session resumption.
	 * 
	 * @param p_s_host									ftp(s) host value, must start with 'ftp://' or 'ftps://'
	 * @param p_i_port									ftp(s) port value
	 * @param p_s_user									user value
	 * @param p_s_password								user password value
	 * @throws IllegalArgumentException					wrong ftp(s) host value, invalid ftp(s) port number, missing user or password
	 * @throws java.io.IOException						issues starting connection to the ftp(s) server
	 */
	public Client(String p_s_host, int p_i_port, String p_s_user, String p_s_password) throws IllegalArgumentException, java.io.IOException {
		this(p_s_host, p_i_port, p_s_user, p_s_password, false);
	}
	
	/**
	 * Constructor for a ftp(s) client object, using separate host, port, user and password parameter specifications.
	 * no ssl context, no trust manager, using Client.BUFFERSIZE(8192) for input/output buffer, using passive mode.
	 * 
	 * @param p_s_host									ftp(s) host value, must start with 'ftp://' or 'ftps://'
	 * @param p_i_port									ftp(s) port value
	 * @param p_s_user									user value
	 * @param p_s_password								user password value
	 * @param p_b_tlsSessionResumption					true - use ftps client object with enforced tls session resumption, false- use ftps client object
	 * @throws IllegalArgumentException					wrong ftp(s) host value, invalid ftp(s) port number, missing user or password
	 * @throws java.io.IOException						issues starting connection to the ftp(s) server
	 */
	public Client(String p_s_host, int p_i_port, String p_s_user, String p_s_password, boolean p_b_tlsSessionResumption) throws IllegalArgumentException, java.io.IOException {
		this(p_s_host, p_i_port, p_s_user, p_s_password, p_b_tlsSessionResumption, true);
	}
	
	/**
	 * Constructor for a ftp(s) client object, using separate host, port, user and password parameter specifications.
	 * no ssl context, no trust manager, using Client.BUFFERSIZE(8192) for input/output buffer.
	 * 
	 * @param p_s_host									ftp(s) host value, must start with 'ftp://' or 'ftps://'
	 * @param p_i_port									ftp(s) port value
	 * @param p_s_user									user value
	 * @param p_s_password								user password value
	 * @param p_b_tlsSessionResumption					true - use ftps client object with enforced tls session resumption, false- use ftps client object
	 * @param p_b_passiveMode							true - using passive mode, false - using active mode
	 * @throws IllegalArgumentException					wrong ftp(s) host value, invalid ftp(s) port number, missing user or password
	 * @throws java.io.IOException						issues starting connection to the ftp(s) server
	 */
	public Client(String p_s_host, int p_i_port, String p_s_user, String p_s_password, boolean p_b_tlsSessionResumption, boolean p_b_passiveMode) throws IllegalArgumentException, java.io.IOException {
		this(p_s_host, p_i_port, p_s_user, p_s_password, p_b_tlsSessionResumption, p_b_passiveMode, Client.BUFFERSIZE, null, null);
	}
	
	/**
	 * Constructor for a ftp(s) client object, using separate host, port, user and password parameter specifications.
	 * no ssl context, no trust manager.
	 * 
	 * @param p_s_host									ftp(s) host value, must start with 'ftp://' or 'ftps://'
	 * @param p_i_port									ftp(s) port value
	 * @param p_s_user									user value
	 * @param p_s_password								user password value
	 * @param p_b_tlsSessionResumption					true - use ftps client object with enforced tls session resumption, false- use ftps client object
	 * @param p_b_passiveMode							true - using passive mode, false - using active mode
	 * @param p_i_bufferSize							specify buffer size for sending and receiving data
	 * @throws IllegalArgumentException					wrong ftp(s) host value, invalid ftp(s) port number, missing user or password
	 * @throws java.io.IOException						issues starting connection to the ftp(s) server
	 */
	public Client(String p_s_host, int p_i_port, String p_s_user, String p_s_password, boolean p_b_tlsSessionResumption, boolean p_b_passiveMode, int p_i_bufferSize) throws IllegalArgumentException, java.io.IOException {
		this(p_s_host, p_i_port, p_s_user, p_s_password, p_b_tlsSessionResumption, p_b_passiveMode, p_i_bufferSize, null, null);
	}
	
	/**
	 * Constructor for a ftp(s) client object, using separate host, port, user and password parameter specifications.
	 * no trust manager, using Client.BUFFERSIZE(8192) for input/output buffer.
	 * 
	 * @param p_s_host									ftp(s) host value, must start with 'ftp://' or 'ftps://'
	 * @param p_i_port									ftp(s) port value
	 * @param p_s_user									user value
	 * @param p_s_password								user password value
	 * @param p_o_sslContext							instance of ssl context for ftps client object
	 * @throws IllegalArgumentException					wrong ftp(s) host value, invalid ftp(s) port number, missing user or password
	 * @throws java.io.IOException						issues starting connection to the ftp(s) server
	 */
	public Client(String p_s_host, int p_i_port, String p_s_user, String p_s_password, javax.net.ssl.SSLContext p_o_sslContext) throws IllegalArgumentException, java.io.IOException {
		this(p_s_host, p_i_port, p_s_user, p_s_password, false, true, Client.BUFFERSIZE, p_o_sslContext, null);
	}
	
	/**
	 * Constructor for a ftp(s) client object, using separate host, port, user and password parameter specifications.
	 * no ssl context, using Client.BUFFERSIZE(8192) for input/output buffer.
	 * 
	 * @param p_s_host									ftp(s) host value, must start with 'ftp://' or 'ftps://'
	 * @param p_i_port									ftp(s) port value
	 * @param p_s_user									user value
	 * @param p_s_password								user password value
	 * @param p_o_trustManager							instance of trust manager for ftps client object
	 * @throws IllegalArgumentException					wrong ftp(s) host value, invalid ftp(s) port number, missing user or password
	 * @throws java.io.IOException						issues starting connection to the ftp(s) server
	 */
	public Client(String p_s_host, int p_i_port, String p_s_user, String p_s_password, javax.net.ssl.X509TrustManager p_o_trustManager) throws IllegalArgumentException, java.io.IOException {
		this(p_s_host, p_i_port, p_s_user, p_s_password, false, true, Client.BUFFERSIZE, null, p_o_trustManager);
	}
	
	/**
	 * Constructor for a ftp(s) client object, using separate host, port, user and password parameter specifications
	 * 
	 * @param p_s_host									ftp(s) host value, must start with 'ftp://' or 'ftps://'
	 * @param p_i_port									ftp(s) port value
	 * @param p_s_user									user value
	 * @param p_s_password								user password value
	 * @param p_b_tlsSessionResumption					true - use ftps client object with enforced tls session resumption, false- use ftps client object
	 * @param p_b_passiveMode							true - using passive mode, false - using active mode
	 * @param p_i_bufferSize							specify buffer size for sending and receiving data
	 * @param p_o_sslContext							instance of ssl context for ftps client object
	 * @param p_o_trustManager							instance of trust manager for ftps client object
	 * @throws IllegalArgumentException					wrong ftp(s) host value, invalid ftp(s) port number, missing user or password
	 * @throws java.io.IOException						issues starting connection to the ftp(s) server
	 */
	public Client(String p_s_host, int p_i_port, String p_s_user, String p_s_password, boolean p_b_tlsSessionResumption, boolean p_b_passiveMode, int p_i_bufferSize, javax.net.ssl.SSLContext p_o_sslContext, javax.net.ssl.X509TrustManager p_o_trustManager) throws IllegalArgumentException, java.io.IOException {
		this.e_protocol = null;
		this.o_ftpClient = null;
		this.o_ftpsClient = null;
		this.o_ftpsClientTLSSessionResumption = null;
		this.o_sslContext = null;
		this.o_trustManager = null;
		this.b_loggedIn = false;
		this.i_bufferSize = p_i_bufferSize;
		this.itf_delegate = null;
		this.itf_delegateFolder = null;
		this.i_dirSum = 0;
		this.i_dirFiles = 0;
		this.b_skipCompletePendingCommand = false;
		this.o_progressBar = null;
		
		/* create ftp(s) wrapper */
		this.createFTPWrapper(p_s_host, p_i_port, p_s_user, p_s_password, p_b_tlsSessionResumption, p_b_passiveMode, p_o_sslContext, p_o_trustManager);
	}
	
	/**
	 * Merging all information to instantiate an ftp(s) client object and start the connection
	 * 
	 * @param p_s_host									ftp(s) host value, must start with 'ftp://' or 'ftps://'
	 * @param p_i_port									ftp(s) port value
	 * @param p_s_user									user value
	 * @param p_s_password								user password value
	 * @param p_b_tlsSessionResumption					true - use ftps client object with enforced tls session resumption, false- use ftps client object
	 * @param p_b_passiveMode							true - using passive mode, false - using active mode
	 * @param p_o_sslContext							instance of ssl context for ftps client object
	 * @param p_o_trustManager							instance of trust manager for ftps client object
	 * @throws IllegalArgumentException					wrong ftp(s) host value, invalid ftp(s) port number, missing user or password
	 * @throws java.io.IOException						issues starting connection to the ftp(s) server
	 */
	private void createFTPWrapper(String p_s_host, int p_i_port, String p_s_user, String p_s_password, boolean p_b_tlsSessionResumption, boolean p_b_passiveMode, javax.net.ssl.SSLContext p_o_sslContext, javax.net.ssl.X509TrustManager p_o_trustManager) throws IllegalArgumentException, java.io.IOException {
		/* check if host parameter is not null or empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_host)) {
			throw new IllegalArgumentException("FTP host is null or empty");
		}
		
		/* host address must start with 'ftp://' or 'ftps://' */
		if ( (!p_s_host.startsWith("ftp://")) && (!p_s_host.startsWith("ftps://")) ) {
			throw new IllegalArgumentException("FTP host does not start with 'ftp://' or 'ftps://'");
		} else {
			if (p_s_host.startsWith("ftps://")) {
				p_s_host = p_s_host.substring(7);
				
				if (p_b_tlsSessionResumption) {
															net.forestany.forestj.lib.Global.ilogConfig("read ftps with tls session resumption");
					this.e_protocol = Protocol.FTPS_TLS_SessionResumption;
				} else {
															net.forestany.forestj.lib.Global.ilogConfig("read ftps");
					this.e_protocol = Protocol.FTPS;
				}
			} else {
														net.forestany.forestj.lib.Global.ilogConfig("read ftp");
				p_s_host = p_s_host.substring(6);
				this.e_protocol = Protocol.FTP;
			}
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("check port and credential validity");
		
		/* check valid ftp(s) port number */
		if (p_i_port < 1) {
			throw new IllegalArgumentException("FTP port must be at least '1', but was set to '" + p_i_port + "'");
		}
		
		/* check valid ftp(s) port number */
		if (p_i_port > 65535) {
			throw new IllegalArgumentException("FTP port must be lower equal '65535', but was set to '" + p_i_port + "'");
		}
		
		/* user necessary */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_user)) {
			throw new IllegalArgumentException("FTP user is null or empty");
		}
		
		/* password necessary */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_password)) {
			throw new IllegalArgumentException("FTP password is null or empty");
		}
		
		this.s_host = p_s_host;
		this.i_port = p_i_port;
		this.s_user = p_s_user;
		this.s_password = p_s_password;
		this.b_passiveMode = p_b_passiveMode;
		this.b_useMListDir = false;
		this.s_ftpReply = null;
		this.i_ftpReplyCode = -1;
		this.o_sslContext = p_o_sslContext;
		this.o_trustManager = p_o_trustManager;
		
												net.forestany.forestj.lib.Global.ilogConfig("start login procedure");
		
		/* start login procedure */
		this.login();
	}
	
	/**
	 * Create ftp(s) client object and start connection to ftp(s) server, entering passive/active mode, possible trust manager and/or ssl context will be adapted as well.
	 * set file transfer mode and file type to binary file type.
	 * 
	 * @throws java.io.IOException		issues starting connection to the ftp(s) server
	 */
	public void login() throws java.io.IOException {
		/* if current instance is logged in, we must re-login */
		if (this.b_loggedIn) {
													net.forestany.forestj.lib.Global.ilogConfig("current instance is logged in, we must re-login so logout procedure will be called");
			
			this.logout();
		}
		
		if (this.e_protocol == Protocol.FTP) { /* create ftp(s) client */
													net.forestany.forestj.lib.Global.ilogConfig("create ftp(s) client");
			
			this.o_ftpClient = new org.apache.commons.net.ftp.FTPClient();
		} else if (this.e_protocol == Protocol.FTPS) {
			if (this.o_sslContext == null) { /* create ftps client */
														net.forestany.forestj.lib.Global.ilogConfig("create ftps client");
				
				this.o_ftpsClient = new org.apache.commons.net.ftp.FTPSClient();
			} else { /* create ftps client with own instance of ssl context */
														net.forestany.forestj.lib.Global.ilogConfig("create ftps clientwith own instance of ssl context");
				
				this.o_ftpsClient = new org.apache.commons.net.ftp.FTPSClient(this.o_sslContext);
			}
			
			/* set trust manager for ftps client */
			if (this.o_trustManager != null) {
														net.forestany.forestj.lib.Global.ilogConfig("set trust manager for ftps client");
				
				this.o_ftpsClient.setTrustManager(this.o_trustManager);
			}
		} else if (this.e_protocol == Protocol.FTPS_TLS_SessionResumption) { /* create ftps client with tls session resumption */
			if (this.o_sslContext == null) {
														net.forestany.forestj.lib.Global.ilogConfig("create ftps client with tls session resumption");
				
				this.o_ftpsClientTLSSessionResumption = new TLSSessionResumption();
			} else { /* use own instance of ssl context */
														net.forestany.forestj.lib.Global.ilogConfig("create ftps client with tls session resumption and own instance of ssl context");
				
				this.o_ftpsClientTLSSessionResumption = new TLSSessionResumption(this.o_sslContext);
			}
			
			/* set trust manager for ftps client with tls session resumption */
			if (this.o_trustManager != null) {
														net.forestany.forestj.lib.Global.ilogConfig("set trust manager for ftps client with tls session resumption");
				
				this.o_ftpsClientTLSSessionResumption.setTrustManager(this.o_trustManager);
			}
			
			/* set extended master secret to 'false' */
			System.setProperty("jdk.tls.useExtendedMasterSecret", "false");
			
													net.forestany.forestj.lib.Global.ilogConfig("set jdk tls extended master secret to 'false'");
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("set connection timeout to 30 seconds");
		
		/* set connection timeout to 30 seconds */
		this.junction().setConnectTimeout(30000);
		
												net.forestany.forestj.lib.Global.ilogConfig("start connection to ftp(s) host");
		
		/* start connection to ftp(s) host */
		this.junction().connect(this.s_host, this.i_port);
		
		try {
													net.forestany.forestj.lib.Global.ilogConfig("login with credentials");
			
			/* login with credentials */
			this.junction().login(this.s_user, this.s_password);
			
													net.forestany.forestj.lib.Global.ilogConfig("retrieve login result");
			
			/* retrieve login result */
			this.b_loggedIn = org.apache.commons.net.ftp.FTPReply.isPositiveCompletion(this.junction().getReplyCode());
			
			/* get ftp(s) reply code and reply message */
			this.s_ftpReply = String.join("; ", java.util.Arrays.asList(this.junction().getReplyStrings()));
			this.i_ftpReplyCode = this.junction().getReplyCode();
			
													net.forestany.forestj.lib.Global.ilogConfig("reply code and message: " + this.i_ftpReplyCode + " - " + this.s_ftpReply);
			
			/* login result is not successful */
			if (!this.b_loggedIn) {
				throw new java.io.IOException("FTP-Error: " + java.util.Arrays.asList(this.junction().getReplyStrings()));
			}
			
			if (!this.junction().changeWorkingDirectory("/")) {
				throw new java.io.IOException("FTP-Error with path [/]: " + java.util.Arrays.asList(this.junction().getReplyStrings()));
			}
			
			/* for ftps connection only */
			if ( (this.e_protocol == Protocol.FTPS) || (this.e_protocol == Protocol.FTPS_TLS_SessionResumption) ) {
														net.forestany.forestj.lib.Global.ilogConfig("set protection buffer size to '0' and data channel protection to private 'P' for ftps client");
				
				/* Set protection buffer size */
				( (org.apache.commons.net.ftp.FTPSClient) this.junction() ).execPBSZ(0);
				/* Set data channel protection to private */
				( (org.apache.commons.net.ftp.FTPSClient) this.junction() ).execPROT("P");
			}
			
			if (this.b_passiveMode) { /* enter local passive mode */
														net.forestany.forestj.lib.Global.ilogConfig("enter local passive mode");
				
				this.junction().enterLocalPassiveMode();
			} else { /* enter local active mode */
														net.forestany.forestj.lib.Global.ilogConfig("enter local active mode");
				
				this.junction().enterLocalActiveMode();
			}
			
													net.forestany.forestj.lib.Global.ilogConfig("set file transfer mode and file type to org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE");
			
			/* set file transfer mode and file type to binary file type */
			this.junction().setFileTransferMode(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
			this.junction().setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
		} catch (java.io.IOException o_exc) {
			/* exception occurred, so we disconnect the connection and clean up variables */
			this.junction().disconnect();
			this.o_ftpClient = null;
			this.o_ftpsClient = null;
			this.o_ftpsClientTLSSessionResumption = null;
			throw o_exc;
		}
	}
	
	/**
	 * Logout and disconnect all ftp(s) connections and null client objects
	 */
	public void logout() {
		b_loggedIn = false;
		
		/* logout and disconnect ftp client */
		if (this.o_ftpClient != null) {
			try {
				try {
															net.forestany.forestj.lib.Global.ilogConfig("logout ftp client");
					
					this.o_ftpClient.logout();
				} finally {
															net.forestany.forestj.lib.Global.ilogConfig("disconnect ftp client");
					
					this.o_ftpClient.disconnect();
					
															net.forestany.forestj.lib.Global.ilogConfig("get ftp server reply");
					
					/* get ftp reply code and reply message */
					this.s_ftpReply = String.join("; ", java.util.Arrays.asList(this.o_ftpClient.getReplyStrings()));
					this.i_ftpReplyCode = this.o_ftpClient.getReplyCode();
					
															net.forestany.forestj.lib.Global.ilogConfig("reply code and message: " + this.i_ftpReplyCode + " - " + this.s_ftpReply);
				}
			} catch (java.io.IOException o_exc) {
														net.forestany.forestj.lib.Global.ilogSevere("error with logout and disconnect ftp client: " + o_exc.getMessage());
			} finally {
				this.o_ftpClient = null;
			}
		}
		
		/* logout and disconnect ftps client */
		if (this.o_ftpsClient != null) {
			try {
				try {
															net.forestany.forestj.lib.Global.ilogConfig("logout ftps client");
					
					this.o_ftpsClient.logout();
				} finally {
															net.forestany.forestj.lib.Global.ilogConfig("disconnect ftps client");
					
					this.o_ftpsClient.disconnect();
					
															net.forestany.forestj.lib.Global.ilogConfig("get ftps server reply");
					
					/* get ftps reply code and reply message */
					this.s_ftpReply = String.join("; ", java.util.Arrays.asList(this.o_ftpsClient.getReplyStrings()));
					this.i_ftpReplyCode = this.o_ftpsClient.getReplyCode();
					
															net.forestany.forestj.lib.Global.ilogConfig("reply code and message: " + this.i_ftpReplyCode + " - " + this.s_ftpReply);
				}
			} catch (java.io.IOException o_exc) {
														net.forestany.forestj.lib.Global.ilogSevere("error with logout and disconnect ftps client: " + o_exc.getMessage());
			} finally {
				this.o_ftpsClient = null;
			}
		}
		
		/* logout and disconnect ftps client with tls session resumption */
		if (this.o_ftpsClientTLSSessionResumption != null) {
			try {
				try {
															net.forestany.forestj.lib.Global.ilogConfig("logout ftps client with tls session resumption");
					
					this.o_ftpsClientTLSSessionResumption.logout();
				} finally {
															net.forestany.forestj.lib.Global.ilogConfig("disconnect ftps client with tls session resumption");
					
					this.o_ftpsClientTLSSessionResumption.disconnect();
					
															net.forestany.forestj.lib.Global.ilogConfig("get ftps server reply");
					
					/* get ftps reply code and reply message */
					this.s_ftpReply = String.join("; ", java.util.Arrays.asList(this.o_ftpsClientTLSSessionResumption.getReplyStrings()));
					this.i_ftpReplyCode = this.o_ftpsClientTLSSessionResumption.getReplyCode();
					
															net.forestany.forestj.lib.Global.ilogConfig("reply code and message: " + this.i_ftpReplyCode + " - " + this.s_ftpReply);
				}
			} catch (java.io.IOException o_exc) {
														net.forestany.forestj.lib.Global.ilogSevere("error with logout and disconnect ftps client with tls session resumption: " + o_exc.getMessage());
			} finally {
				this.o_ftpsClientTLSSessionResumption = null;
			}
		}
	}
	
	/**
	 * Method call to wait for pending command, incl. getting ftp(s) reply code and message 
	 */
	private boolean completePendingCommand() {
		/* skip waiting for pending command */
		if (!this.b_skipCompletePendingCommand) {
			try {
														net.forestany.forestj.lib.Global.ilogFine("complete pending command");
				
				/* wait for pending command */
				this.junction().completePendingCommand();
				
				/* get ftp(s) reply code and reply message */
				this.s_ftpReply = String.join("; ", java.util.Arrays.asList(this.junction().getReplyStrings()));
				this.i_ftpReplyCode = this.junction().getReplyCode();
				
														net.forestany.forestj.lib.Global.ilogFine("reply code and message: " + this.i_ftpReplyCode + " - " + this.s_ftpReply);
			} catch (java.io.IOException o_exc) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
			
			/* return positive completion state */
			return org.apache.commons.net.ftp.FTPReply.isPositiveCompletion(this.junction().getReplyCode());
		} else {
			return true;
		}
	}
	
	/**
	 * Method to check login state and connection, initiate a re-login if necessary
	 */
	private boolean checkLoginState() {
		/* check state of logged in variable */
		this.b_loggedIn = checkLoginStateVariable();
		
		/* if we are logged in, all is fine */
		if (this.b_loggedIn) {
			return this.b_loggedIn;
		}
		
		/* we are not logged in anymore, so we must re-login */
		try {
													net.forestany.forestj.lib.Global.ilogFinest("we are not logged in anymore, so we must re-login");
			
			this.login();
		} catch (IllegalStateException o_exc) {
													net.forestany.forestj.lib.Global.ilogSevere("could not re-login: " + o_exc.getMessage());
			
			return false;
		} catch (java.io.IOException o_exc) {
													net.forestany.forestj.lib.Global.ilogSevere("could not re-login: " + o_exc.getMessage());
			
			return false;
		}
		
		/* return new login result */
		return this.b_loggedIn;
	}
	
	/**
	 * Checking login state variable by using ftp(s) STAT method to see if connection is still alive
	 */
	private boolean checkLoginStateVariable() {
		/* if we are not logged in, return this state immediately */
		if (!this.b_loggedIn) {
			return b_loggedIn;
		}
		
		/* check if our client instance is null */
		if (this.junction() == null) {
													net.forestany.forestj.lib.Global.ilogFinest("client instance is null, so we are not logged in anymore");
			
			this.b_loggedIn = false;
			return false;
		}
		
		/* logged in, try a status command to see if connection is still alive */
		try {
													net.forestany.forestj.lib.Global.ilogFinest("try a stat command to see if connection is still alive");
			
			int i_foo = this.junction().stat();
			i_foo = i_foo + 1;
			
			return this.b_loggedIn;
		} catch (java.io.IOException o_exc) {
													net.forestany.forestj.lib.Global.ilogFinest("connection lost; exception occured: " + o_exc.getMessage());
			
			/* exception occurred with status command, so we are not logged in anymore */
			this.b_loggedIn = false;
			return false;
		}
	}
	
	/**
	 * Creates a directory on ftp(s) server.
	 * checking login state before executing MkDir.
	 * 
	 * @param p_s_dir						directory with path to be created
	 * @return								true - directory has been created, false - exception occurred
	 * @throws java.io.IOException			issue with make directory command
	 */
	public boolean mkDir(String p_s_dir) throws java.io.IOException {
		return this.mkDir(p_s_dir, true, false);
	}
	
	/**
	 * Creates a directory on ftp(s) server
	 * 
	 * @param p_s_dir						directory with path to be created
	 * @param p_b_autoCreate				true - create all directories until target directory automatically, false - expect that the complete path to target directory already exists
	 * @return 								true - directory has been created, false - exception occurred
	 * @throws java.io.IOException			issue with make directory command
	 */
	public boolean mkDir(String p_s_dir, boolean p_b_autoCreate) throws java.io.IOException {
		if (!this.checkLoginState()) {
			return false;
		}
		
		return this.mkDir(p_s_dir, p_b_autoCreate, false);
	}
	
	/**
	 * Creates a directory on ftp(s) server
	 * 
	 * @param p_s_dir						directory with path to be created
	 * @param p_b_autoCreate				true - create all directories until target directory automatically, false - expect that the complete path to target directory already exists
	 * @param p_b_batch						method in internal batch call, so we do not check if directory exists before executing command and no check for login state
	 * @return								true - directory has been created, false - exception occurred
	 * @throws java.io.IOException			issue with make directory command
	 */
	private boolean mkDir(String p_s_dir, boolean p_b_autoCreate, boolean p_b_batch) throws java.io.IOException {
		/* check directory path is null or empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_dir)) {
													net.forestany.forestj.lib.Global.ilogFine("directory path is null or empty");
			
			return false;
		}
		
		/* replace all backslashes with slash */
		p_s_dir = p_s_dir.trim().replace('\\', '/');
		
		/* directory path must start with root '/' */
		if (!p_s_dir.startsWith("/")) {
			p_s_dir = "/" + p_s_dir;
		}
		
		/* remove '/' character if directory parameter ends with it */
		if (p_s_dir.endsWith("/")) {
			p_s_dir = p_s_dir.substring(0, p_s_dir.length() - 1);
		}
		
		boolean b_foo = false;
		
		try {
			/* if this is a batch call, we do not need to check if directory already exists */
			if ( (p_b_batch) || (!this.directoryExists(p_s_dir)) ) {
				/* create path to target directory if we are not at root directory */
				if ( (p_b_autoCreate) && (p_s_dir.indexOf('/') >= 0) ) {
															net.forestany.forestj.lib.Global.ilogFine("create path to target directory on sftp server");
					
					/* get all directories in path as a list */
					String[] a_foo = p_s_dir.substring(0, p_s_dir.lastIndexOf('/')).split("/");
					java.util.List<String> a_createDirectories = new java.util.ArrayList<String>();
					
					/* for each directory in path to target directory */
					for (int i = 0; i < a_foo.length; i++) {
						String s_foo = "";
						
						for (int j = 0; j < (a_foo.length - i); j++) {
							s_foo += a_foo[j] + "/";
						}
						
						/* reduce directory path */
						s_foo = s_foo.substring(0, s_foo.length() - 1);
						
						/* if a directory already exists we can break the loop here */
						if (this.directoryExists(s_foo)) {
							break;
						}
						
						/* add directory path to list */
						a_createDirectories.add(s_foo);
					}
					
															net.forestany.forestj.lib.Global.ilogFine("create each directory within path in reverse");
					
					/* create each directory within path in reverse */
					for (int i = (a_createDirectories.size() - 1); i >= 0; i--) {
																net.forestany.forestj.lib.Global.ilogFiner("create directory '/" + a_createDirectories.get(i) + "'");
						
						/* create directory within target path */
						b_foo = this.junction().makeDirectory("/" + a_createDirectories.get(i));
					}
				}
				
														net.forestany.forestj.lib.Global.ilogFine("create directory '/" + p_s_dir + "'");
				
				/* create directory */
				b_foo = this.junction().makeDirectory("/" + p_s_dir);
			}
		} catch (Exception o_exc) {
			/* handle exception */
			net.forestany.forestj.lib.Global.ilogSevere("FTP(S) error with creating directory; " + o_exc);

			b_foo = false;
		}
		
		/* get ftp(s) reply code and reply message */
		this.s_ftpReply = String.join("; ", java.util.Arrays.asList(this.junction().getReplyStrings()));
		this.i_ftpReplyCode = this.junction().getReplyCode();
		
												net.forestany.forestj.lib.Global.ilogFine("reply code and message: " + this.i_ftpReplyCode + " - " + this.s_ftpReply);
		
		/* return result of last make directory command */
		return b_foo;
	}
	
	/**
	 * List all entries of a target ftp(s) directory, not listing any sub directories.
	 * checking login state before executing Ls.
	 * 
	 * @param p_s_dir					path to target ftp(s) directory
	 * @param p_b_hideDirectories		true - won't list sub directories, false - list sub directories in result
	 * @param p_b_showTempFiles			true - list temp files with '.lock' extension at the end, false - won't list temporary files with '.lock' extension
	 * @return							list of Entry object(s) on target ftp(s) directory
	 */
	public java.util.List<net.forestany.forestj.lib.net.ftpcore.Entry> ls(String p_s_dir, boolean p_b_hideDirectories, boolean p_b_showTempFiles) {
		return this.ls(p_s_dir, p_b_hideDirectories, p_b_showTempFiles, false);
	}
	
	/**
	 * List all entries of a target ftp(s) directory.
	 * checking login state before executing Ls.
	 * 
	 * @param p_s_dir					path to target ftp(s) directory
	 * @param p_b_hideDirectories		true - won't list sub directories, false - list sub directories in result
	 * @param p_b_showTempFiles			true - list temp files with '.lock' extension at the end, false - won't list temporary files with '.lock' extension
	 * @param p_b_recursive				true - include all sub directories in result, false - stay in target ftp(s) directory
	 * @return							list of Entry object(s) on target ftp(s) directory
	 */
	public java.util.List<net.forestany.forestj.lib.net.ftpcore.Entry> ls(String p_s_dir, boolean p_b_hideDirectories, boolean p_b_showTempFiles, boolean p_b_recursive) {
		java.util.List<net.forestany.forestj.lib.net.ftpcore.Entry> a_list = new java.util.ArrayList<net.forestany.forestj.lib.net.ftpcore.Entry>();
		
		/* check if directory path is null or empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_dir)) {
													net.forestany.forestj.lib.Global.ilogFiner("directory path is null or empty");
			
			return a_list;
		}
		
		/* replace all backslashes with slash */
		p_s_dir = p_s_dir.trim().replace('\\', '/');
		
												net.forestany.forestj.lib.Global.ilogFiner("get all directories and files of directory path");
		
		/* get all directories and files of directory path */
		org.apache.commons.net.ftp.FTPFile[] a_elements = this.listFTPFiles(p_s_dir);
		
		if (a_elements != null) {
			/* iterate each directory element */
			for (org.apache.commons.net.ftp.FTPFile o_dirElement : a_elements) {
				/* check with parameters if we want to list directories and temporary files with '.lock' ending */
				if (
					(o_dirElement != null) &&
					(!o_dirElement.getName().contentEquals(".")) &&
					(!o_dirElement.getName().contentEquals("..")) &&
					(!( (o_dirElement.isDirectory()) && (p_b_hideDirectories) )) &&
					(!( (o_dirElement.getName().endsWith(".lock")) && (!p_b_showTempFiles) ))
				) {
															net.forestany.forestj.lib.Global.ilogFinest("create new ftp(s) entry object with all directory element information for: '" + o_dirElement.getName() + "'");
					
					/* get access rights */
					String s_access = "";
															
					if (this.b_useMListDir) {
						/* just get the UNIX.mode value */
						int i_foo = o_dirElement.getRawListing().indexOf(";UNIX.mode=");
						s_access = o_dirElement.getRawListing().substring(i_foo + 11, i_foo + 11 + 4);
					} else {
						/* just get the 9 characters from start */
						s_access = o_dirElement.getRawListing().substring(1, 10);
					}
															
					/* create new ftp(s) entry object with all directory element information and add it to return list value */
					net.forestany.forestj.lib.net.ftpcore.Entry o_ftpEntry = new net.forestany.forestj.lib.net.ftpcore.Entry(o_dirElement.getName(), o_dirElement.getGroup(), o_dirElement.getUser(), ((p_s_dir == "/") ? "" : p_s_dir) + "/", s_access, o_dirElement.getSize(), o_dirElement.getTimestamp().getTime(), o_dirElement.isDirectory());
					
															net.forestany.forestj.lib.Global.ilogFinest("add directory element to return list value");
					
					/* add directory element to return list value */
					a_list.add(o_ftpEntry);
				}
				
				/* check if we want to list sub directories */
				if ( (p_b_recursive) && (o_dirElement != null) && (!o_dirElement.getName().contentEquals(".")) && (!o_dirElement.getName().contentEquals("..")) && (o_dirElement.isDirectory()) ) {
															net.forestany.forestj.lib.Global.ilogFinest("get a list of all directory elements in sub directory for: '" + o_dirElement.getName() + "'");
					
					/* get a list of all directory elements in sub directory */
					java.util.List<net.forestany.forestj.lib.net.ftpcore.Entry> a_recursiveResult = this.ls( ((p_s_dir == "/") ? "" : p_s_dir) + "/" + o_dirElement.getName(), p_b_hideDirectories, p_b_showTempFiles, p_b_recursive);
					
															net.forestany.forestj.lib.Global.ilogFinest("add directory elements to return list value");
					
					/* add directory elements to return list value */
					for (net.forestany.forestj.lib.net.ftpcore.Entry o_recursiveResult : a_recursiveResult) {
						a_list.add(o_recursiveResult);
					}
				}
			}
		} else {
													net.forestany.forestj.lib.Global.ilogFiner("directory path has no elements");
		}
		
		return a_list;
	}
	
	/**
	 * Using mlistdir or listFiles command to get a list of all entries on target ftp(s) directory, depending on property this.b_useMListDir
	 * 
	 * @param p_s_dir		path to target ftp(s) directory
	 * @return				array of org.apache.commons.net.ftp.FTPFile[] objects
	 */
	private org.apache.commons.net.ftp.FTPFile[] listFTPFiles(String p_s_dir) {
		org.apache.commons.net.ftp.FTPFile[] a_elements = null;
		
		String s_foo = null;
		int i_foo = -1;
		
		try {
													net.forestany.forestj.lib.Global.ilogFiner((this.b_useMListDir) ? "execute mlistDir with directory path" : "execute listFiles with directory path");
			
			/* get list of directory elements */
			a_elements = (this.b_useMListDir) ? this.junction().mlistDir(p_s_dir) : this.junction().listFiles(p_s_dir);
			
			/* get ftp(s) reply code and reply message */
			s_foo = String.join("; ", java.util.Arrays.asList(this.junction().getReplyStrings()));
			i_foo = this.junction().getReplyCode();
			
													net.forestany.forestj.lib.Global.ilogFiner("reply code and message: " + i_foo + " - " + s_foo);
		} catch (java.io.IOException o_exc) {
			net.forestany.forestj.lib.Global.logException(o_exc);
		}
		
		/* set ftp(s) reply code and reply message */
		this.s_ftpReply = s_foo;
		this.i_ftpReplyCode = i_foo;
		
		/* return list of directory elements */
		return a_elements;
	}
	
	/**
	 * Get element instance within a ftp(s) directory if name matches and it is not a temporary file
	 * 
	 * @param p_s_path		path to target element on ftp(s) server
	 * @return 				org.apache.commons.net.ftp.FTPFile instance with further information properties
	 */
	private org.apache.commons.net.ftp.FTPFile getElement(String p_s_path) {
		/* check if file path is null or empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_path)) {
													net.forestany.forestj.lib.Global.ilogFiner("path is null or empty");
			
			return null;
		}
		
		/* replace all backslashes with slash */
		p_s_path = p_s_path.trim().replace('\\', '/');
		
		/* path must start with root '/' */
		if (!p_s_path.startsWith("/")) {
			p_s_path = "/" + p_s_path;
		}
		
		/* remove '/' character if path parameter ends with it */
		if (p_s_path.endsWith("/")) {
			p_s_path = p_s_path.substring(0, p_s_path.length() - 1);
		}
		
		/* get path and element from parameter */
		String s_path = p_s_path.substring(0, p_s_path.lastIndexOf("/"));
		String s_element = p_s_path.substring(p_s_path.lastIndexOf("/") + 1);
		
		/* get all directories and files of file path */
		org.apache.commons.net.ftp.FTPFile[] a_elements = this.listFTPFiles(s_path);
		
		if (a_elements != null) {
			/* check each directory element to find what we are looking for */
			for (org.apache.commons.net.ftp.FTPFile o_dirElement : a_elements) {
														net.forestany.forestj.lib.Global.ilogFinest(o_dirElement.getName() + " == " + s_element);
				
				/* element name must match */
				if (
					(o_dirElement != null) && 
					(!o_dirElement.getName().contentEquals(".")) && 
					(!o_dirElement.getName().contentEquals("..")) && 
					(o_dirElement.getName().contentEquals(s_element))
				) {
															net.forestany.forestj.lib.Global.ilogFinest("found element");
					
					/* found element */
					return o_dirElement;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Check if a file at target file path on ftp(s) server exists.
	 * checking login state before executing FileExists.
	 * 
	 * @param p_s_filePath				path to target file on ftp(s) server
	 * @return							true - exists, false - does not exist
	 */
	public boolean fileExists(String p_s_filePath) {
		if (!this.checkLoginState()) {
			return false;
		}

		return this.fileExists(p_s_filePath, false);
	}
	
	/**
	 * Check if a file at target file path on ftp(s) server exists
	 * 
	 * @param p_s_filePath				path to target file on ftp(s) server
	 * @param p_b_batch					method in internal batch call, no check for login state
	 * @return							true - exists, false - does not exist
	 */
	private boolean fileExists(String p_s_filePath, boolean p_b_batch) {
		org.apache.commons.net.ftp.FTPFile o_foo = this.getElement(p_s_filePath);
		
		if ( (o_foo != null) && (o_foo.isFile()) ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Check if a directory at target dir path on ftp(s) server exists.
	 * checking login state before executing DirectoryExists.
	 * 
	 * @param p_s_directoryPath			path to target directory on ftp(s) server
	 * @return							true - exists, false - does not exist
	 */
	public boolean directoryExists(String p_s_directoryPath) {
		if (!this.checkLoginState()) {
			return false;
		}
		
		return this.directoryExists(p_s_directoryPath, false);
	}
	
	/**
	 * Check if a directory at target dir path on ftp(s) server exists
	 * 
	 * @param p_s_directoryPath			path to target directory on ftp(s) server
	 * @param p_b_batch					method in internal batch call, no check for login state
	 * @return							true - exists, false - does not exist
	 */
	private boolean directoryExists(String p_s_directoryPath, boolean p_b_batch) {
		org.apache.commons.net.ftp.FTPFile o_foo = this.getElement(p_s_directoryPath);
		
		if ( (o_foo != null) && (o_foo.isDirectory()) ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Get file length of a file on ftp(s) server.
	 * checking login state before executing GetLength.
	 * 
	 * @param p_s_filePath				path to target file on ftp(s) server
	 * @return							file size as long value
	 */
	public long getLength(String p_s_filePath) {
		if (!this.checkLoginState()) {
			return -1;
		}
		
		return this.getLength(p_s_filePath, false);
	}
	
	/**
	 * Get file length of a file on ftp(s) server
	 * 
	 * @param p_s_filePath				path to target file on ftp(s) server
	 * @param p_b_batch					method in internal batch call, no check for login state
	 * @return							file size as long value
	 */
	private long getLength(String p_s_filePath, boolean p_b_batch) {
												net.forestany.forestj.lib.Global.ilogFiner("check if file exists to get file length");
		
		/* check if file exists to get file length */
		org.apache.commons.net.ftp.FTPFile o_foo = this.getElement(p_s_filePath);
		
		if ( (o_foo != null) && (o_foo.isFile()) ) {
			return o_foo.getSize();
		} else {
													net.forestany.forestj.lib.Global.ilogFiner("file does not exist, returning -1");
			
			return -1;
		}
	}
	
	/**
	 * Deletes a file on ftp(s) server.
	 * checking login state before executing Delete.
	 *
	 * @param p_s_filePath				path to target file on ftp(s) server
	 * @return							true - deleted, false - issue with deletion
	 */
	public boolean delete(String p_s_filePath) {
		if (!this.checkLoginState()) {
			return false;
		}
		
		return this.delete(p_s_filePath, false);
	}
	
	/**
	 * Deletes a file on ftp(s) server 
	 *
	 * @param p_s_filePath				path to target file on ftp(s) server
	 * @param p_b_batch					method in internal batch call, no check for login state and no check if file still exists on ftp(s) server
	 * @return							true - deleted, false - issue with deletion
	 */
	private boolean delete(String p_s_filePath, boolean p_b_batch) {
		/* check if file path is null or empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_filePath)) {
													net.forestany.forestj.lib.Global.ilogFine("file path is null or empty");
			
			return false;
		}
		
		/* replace all backslashes with slash */
		p_s_filePath = p_s_filePath.trim().replace('\\', '/');
		
		/* if this is not a batch call, we check if file really exists on ftp(s) server */
		if ( (!p_b_batch) && (!this.fileExists(p_s_filePath, p_b_batch)) ) {
													net.forestany.forestj.lib.Global.ilogWarning("File '" + p_s_filePath + "' does not exist on ftp(s) server");
			
			return false;
		}
		
		boolean b_foo = false;
		
		/* delete file */
		try {
													net.forestany.forestj.lib.Global.ilogFine("delete file: '" + p_s_filePath + "'");
			
			b_foo = this.junction().deleteFile(p_s_filePath);
		} catch (java.io.IOException o_exc) {
													net.forestany.forestj.lib.Global.ilogSevere("could not delete file: " + o_exc.getMessage());
		}
		
		/* get ftp(s) reply code and reply message */
		this.s_ftpReply = String.join("; ", java.util.Arrays.asList(this.junction().getReplyStrings()));
		this.i_ftpReplyCode = this.junction().getReplyCode();
		
												net.forestany.forestj.lib.Global.ilogFine("reply code and message: " + this.i_ftpReplyCode + " - " + this.s_ftpReply);
		
		return b_foo;
	}
	
	/**
	 * Remove a directory on ftp(s) server with all it's sub directories and elements.
	 * checking login state before executing RmDir.
	 * 
	 * @param p_s_dir					path to target directory on ftp(s) server
	 * @return							true - deleted, false - issue with deletion
	 */
	public boolean rmDir(String p_s_dir) {
		if (!this.checkLoginState()) {
			return false;
		}
		
		return this.rmDir(p_s_dir, false);
	}
	
	/**
	 * Remove a directory on ftp(s) server with all it's sub directories and elements
	 * 
	 * @param p_s_dir					path to target directory on ftp(s) server
	 * @param p_b_batch					method in internal batch call, no check for login state and no check if directory really exists on ftp(s) server
	 * @return							true - deleted, false - issue with deletion
	 */
	private boolean rmDir(String p_s_dir, boolean p_b_batch) {
		/* check if directory path is null or empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_dir)) {
													net.forestany.forestj.lib.Global.ilogFine("directory path is null or empty");
			
			return false;
		}
		
		/* replace all backslashes with slash */
		p_s_dir = p_s_dir.trim().replace('\\', '/');
		
		/* if this is not a batch call, we check if directory really exists on ftp(s) server */
		if ((!p_b_batch) && (!this.directoryExists(p_s_dir, p_b_batch)) ) {
													net.forestany.forestj.lib.Global.ilogWarning("Directory '" + p_s_dir + "' does not exist on ftp(s) server");
			
			return false;
		}
		
		/* get all directories and files of directory path */
		org.apache.commons.net.ftp.FTPFile[] a_elements = this.listFTPFiles(p_s_dir);
		
        boolean b_return = true;
        
        /* if directory path has elements */
		if (a_elements != null) {
			/* count files of directory and all sub directories for delegate, but only if it is not a batch call */
			if ( (this.itf_delegateFolder != null) && (!p_b_batch) ) {
														net.forestany.forestj.lib.Global.ilogFiner("count files of directory and all sub directories for delegate");
				
				this.i_dirFiles = this.ls(p_s_dir, true, false, true).size();
				
														net.forestany.forestj.lib.Global.ilogFiner("elements to be deleted: " + this.i_dirFiles);
			}
			
													net.forestany.forestj.lib.Global.ilogFine("iterate each directory element");
			
			/* iterate each directory element */
			for (org.apache.commons.net.ftp.FTPFile o_dirElement : a_elements) {
				if ( (o_dirElement != null) && (!o_dirElement.getName().contentEquals(".")) && (!o_dirElement.getName().contentEquals("..")) ) {
					if (o_dirElement.isDirectory()) {
																net.forestany.forestj.lib.Global.ilogFinest("directory element[" + o_dirElement.getName() + "] is a directory, so we call Rmdir recursively as batch call");
						
						/* directory element is a directory, so we call RmDir recursively as batch call */
						b_return &= this.rmDir(p_s_dir + "/" + o_dirElement.getName(), true);
					} else if ( (o_dirElement.isFile()) && (!o_dirElement.getName().endsWith(".lock")) ) {
																net.forestany.forestj.lib.Global.ilogFinest("directory element[" + o_dirElement.getName() + "] is a file, so we delete the file as batch call");
						
						/* directory element is a file, so we delete the file as batch call */
						b_return &= this.delete(p_s_dir + "/" + o_dirElement.getName(), true);
						
						/* increase delete counter and call delegate */
						if (this.itf_delegateFolder != null) {
																	net.forestany.forestj.lib.Global.ilogFinest("increase delete counter and call delegate");
							
							this.i_dirSum++;
							this.itf_delegateFolder.PostProgressFolder(this.i_dirSum, this.i_dirFiles);
							
							
																	net.forestany.forestj.lib.Global.ilogFiner("deleted: " + this.i_dirSum + "/" + this.i_dirFiles);
						}
					}
				}
			}
		}
		
		/* iteration finished, we can reset delete counter */
		if ( (this.itf_delegateFolder != null) && (!p_b_batch) ) {
													net.forestany.forestj.lib.Global.ilogFiner("iteration finished, we can reset delete counter");
			
			this.i_dirSum = 0;
			this.i_dirFiles = 0;
		}
		
		/* delete directory */
		try {
													net.forestany.forestj.lib.Global.ilogFine("delete directory: '" + p_s_dir + "'");
			
			b_return &= this.junction().removeDirectory(p_s_dir);
		} catch (java.io.IOException o_exc) {
													net.forestany.forestj.lib.Global.ilogSevere("could not delete directory: " + o_exc.getMessage());
			
			b_return = false;
		}
		
		/* get ftp(s) reply code and reply message */
		this.s_ftpReply = String.join("; ", java.util.Arrays.asList(this.junction().getReplyStrings()));
		this.i_ftpReplyCode = this.junction().getReplyCode();
		
												net.forestany.forestj.lib.Global.ilogFine("reply code and message: " + this.i_ftpReplyCode + " - " + this.s_ftpReply);
		
		return b_return;
	}
	
	/**
	 * Rename directory or file on ftp(s) server, do not delete existing files with new name.
	 * checking login state before executing Rename.
	 * 
	 * @param p_s_old				name of target directory or file on ftp(s) server
	 * @param p_s_new				new name for directory or file
	 * @return						true - rename successful, false - issue with rename command
	 */
	public boolean rename(String p_s_old, String p_s_new) {
		return this.rename(p_s_old, p_s_new, false);
	}
	
	/**
	 * Rename directory or file on ftp(s) server.
	 * checking login state before executing Rename.
	 * 
	 * @param p_s_old				name of target directory or file on ftp(s) server
	 * @param p_s_new				new name for directory or file
	 * @param p_b_overwrite			if a file already exists with new name, delete it
	 * @return						true - rename successful, false - issue with rename command
	 */
	public boolean rename(String p_s_old, String p_s_new, boolean p_b_overwrite) {
		if (!this.checkLoginState()) {
			return false;
		}
		
		return this.rename(p_s_old, p_s_new, p_b_overwrite, false);
	}
	
	/**
	 * Rename directory or file on ftp(s) server
	 * 
	 * @param p_s_old				name of target directory or file on ftp(s) server
	 * @param p_s_new				new name for directory or file
	 * @param p_b_overwrite			if a file already exists with new name, delete it
	 * @param p_b_batch				method in internal batch call, no check for login state and no check if directory or file really exists on ftp(s) server
	 * @return						true - rename successful, false - issue with rename command
	 */
	private boolean rename(String p_s_old, String p_s_new, boolean p_b_overwrite, boolean p_b_batch) {
		/* check if old and new name are empty or both are equal */
		if ( (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_old)) || (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_new)) || (p_s_old.trim().contentEquals(p_s_new.trim())) ) {
													net.forestany.forestj.lib.Global.ilogFine("old or new name are empty or both are equal");
			
			return false;
		}
		
		/* check if directory or file which we want to rename exists, but only if it is not a batch call */
		if ( (!p_b_batch) && ( (!this.fileExists(p_s_old, p_b_batch)) && (!this.directoryExists(p_s_old, p_b_batch)) ) ) {
													net.forestany.forestj.lib.Global.ilogWarning("Directory or File '" + p_s_old + "' does not exist on ftp(s) server");
			
			return false;
		}
		
		/* check if a file exists with new file name, in case we want to overwrite it anyway we delete file with the new file name */
		if (this.fileExists(p_s_new, p_b_batch)) {
			if (!p_b_overwrite) { /* file already exists */
														net.forestany.forestj.lib.Global.ilogWarning("File '" + p_s_new + "' already exists on ftp(s) server");
				
				return false;
			} else { /* file already exists so we delete it */
														net.forestany.forestj.lib.Global.ilogFine("delete file with new name");
				
				this.delete(p_s_new, true);
				
														net.forestany.forestj.lib.Global.ilogFine("deleted file with new name");
			}
		} else if (this.directoryExists(p_s_new, p_b_batch)) { /* check if a directory exists with new name */
													net.forestany.forestj.lib.Global.ilogWarning("Directory '" + p_s_new + "' already exists on ftp(s) server and cannot be overwritten. Please delete target directory first");
			
			/* directory cannot be overwritten */
			return false;
		}
		
		/* replace all backslashes with slash */
		p_s_old = p_s_old.trim().replace('\\', '/');
		p_s_new = p_s_new.trim().replace('\\', '/');
		
		boolean b_foo = false;
		
		/* rename file */
		try {
													net.forestany.forestj.lib.Global.ilogFine("rename element: '" + p_s_old + "' to '" + p_s_new + "'");
			
			b_foo = this.junction().rename(p_s_old, p_s_new);
		} catch (java.io.IOException o_exc) {
													net.forestany.forestj.lib.Global.ilogSevere("could not rename element: " + o_exc.getMessage());
			
			b_foo = false;
		}
		
		/* get ftp(s) reply code and reply message */
		this.s_ftpReply = String.join("; ", java.util.Arrays.asList(this.junction().getReplyStrings()));
		this.i_ftpReplyCode = this.junction().getReplyCode();
		
												net.forestany.forestj.lib.Global.ilogFine("reply code and message: " + this.i_ftpReplyCode + " - " + this.s_ftpReply);
		
		return b_foo;
	}
	
	/**
	 * Download content from a file on the ftp(s) server.
	 * checking login state before executing Download.
	 * 
	 * @param p_s_filePathSourceFtp					path to target file on ftp(s) server
	 * @return										file content - array of bytes
	 * @throws IllegalArgumentException				invalid path to target file
	 * @throws java.io.IOException					issue reading file stream or get amount of content length from input stream
	 */
	public byte[] download(String p_s_filePathSourceFtp) throws IllegalArgumentException, java.io.IOException {
		if (!this.checkLoginState()) {
			return null;
		}
		
		return this.download(p_s_filePathSourceFtp, 0, false);
	}
	
	/**
	 * Download content from a file on the ftp(s) server
	 * 
	 * @param p_s_filePathSourceFtp					path to target file on ftp(s) server
	 * @param p_i_filesize							file length which is needed within a batch call in case inputstream.available() is not working
	 * @param p_b_batch								method in internal batch call, no check for login state and no check if file really exists on ftp(s) server
	 * @return										file content - array of bytes
	 * @throws IllegalArgumentException				invalid path to target file
	 * @throws java.io.IOException					issue reading file stream or get amount of content length from input stream
	 */
	private byte[] download(String p_s_filePathSourceFtp, int p_i_fileSize, boolean p_b_batch) throws IllegalArgumentException, java.io.IOException {
		/* check file path parameter */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_filePathSourceFtp)) {
			throw new IllegalArgumentException("Please specify a file path for download");
		}
		
		/* replace all backslashes with slash */
		p_s_filePathSourceFtp = p_s_filePathSourceFtp.trim().replace('\\', '/');
		
		/* file size parameter is needed if this is a batch call */
		if ( (p_i_fileSize <= 0) && (p_b_batch) ) {
			throw new IllegalArgumentException("Please specify a file size for download");
		}
		
		/* variable for filename without path */
		String s_filename = "";
		
		if (!p_b_batch) { /* not a batch call, so we check if file really exists and retrieve file size */
													net.forestany.forestj.lib.Global.ilogFine("check if file really exists");
			
			/* check if file really exists */
			org.apache.commons.net.ftp.FTPFile o_ftpFile = this.getElement(p_s_filePathSourceFtp);
			
			if (o_ftpFile == null) { /* file not found */
				throw new java.io.IOException("File '" + p_s_filePathSourceFtp + "' does not exist on ftp(s) server");
			} else { /* retrieve file name and file size */
														net.forestany.forestj.lib.Global.ilogFine("retrieve file name and file size");
				
				s_filename = o_ftpFile.getName();
				p_i_fileSize = Long.valueOf(o_ftpFile.getSize()).intValue();
				
														net.forestany.forestj.lib.Global.ilogFine("retrieved file name '" + s_filename + "' and file size '" + p_i_fileSize + "'");										
			}
		} else { /* retrieve file name from parameter */
													net.forestany.forestj.lib.Global.ilogFine("retrieve file name from parameter");
			
			s_filename = p_s_filePathSourceFtp.substring(p_s_filePathSourceFtp.lastIndexOf("/") + 1);
			
													net.forestany.forestj.lib.Global.ilogFine("retrieved file name: " + s_filename);
		}
		
		byte[] a_data = null;
		
		/* retrieve file stream */
		try (java.io.InputStream o_inputStream = this.junction().retrieveFileStream(p_s_filePathSourceFtp)) {
			/* check if stream object is available */
			if (o_inputStream == null) {
													net.forestany.forestj.lib.Global.ilogWarning("could not create input stream to file '" + p_s_filePathSourceFtp + "'");
				
				return null;
			}
			
			/* initialize console progress bar */
			if (this.o_progressBar != null) {
				this.o_progressBar.init("Downloading . . .", "Download finished ['" + s_filename + "']", s_filename);
			}
			
													net.forestany.forestj.lib.Global.ilogFiner("receiving bytes from input stream");
			
			/* receiving bytes from input stream */
			a_data = this.receiveBytes(o_inputStream, this.i_bufferSize, p_i_fileSize);

													net.forestany.forestj.lib.Global.ilogFiner("received bytes from input stream");
		} finally {
			boolean b_retry = true;
			int i_maxAttempts = 40;
			int i_attempts = 1;
			
			/* waiting for complete pending command for downloading data */
			do {
				if (!this.completePendingCommand()) {
														net.forestany.forestj.lib.Global.ilogWarning("could not complete pending command");
				} else {
					b_retry = false;
				}
				
				try {
					Thread.sleep(25);
				} catch (InterruptedException o_exc) {
					/* nothing to do */
				}
					
				if (i_attempts++ >= i_maxAttempts) {
					throw new java.io.IOException("Waiting complete pending command failed, after " + i_attempts + " attempts");
				}
			} while (b_retry);
			
			/* get a return value, but no content so we set data to null */
			if ( (a_data != null) && (a_data.length <= 0) ) {
				a_data = null;
			}
			
			/* get ftp(s) reply code and reply message */
			this.s_ftpReply = String.join("; ", java.util.Arrays.asList(this.junction().getReplyStrings()));
			this.i_ftpReplyCode = this.junction().getReplyCode();
			
													net.forestany.forestj.lib.Global.ilogFine("reply code and message: " + this.i_ftpReplyCode + " - " + this.s_ftpReply);
			
			/* close console progress bar */
			if (this.o_progressBar != null) {
				this.o_progressBar.close();
			}
		}
		
		return a_data;
	}
	
	/**
	 * Download content from a file on the ftp(s) server to a local file, local file will be overwritten.
	 * checking login state before executing Download.
	 * 
	 * @param p_s_filePathSourceFtp					path to target file on ftp(s) server
	 * @param p_s_filePathDestinationLocal			path to destination file on local system
	 * @return										true - download successful, false - download failed
	 * @throws IllegalArgumentException				invalid path to target file, invalid path to local system
	 * @throws java.io.IOException					destination directory could not be created, issue reading file stream or issue writing to local system
	 */
	public boolean download(String p_s_filePathSourceFtp, String p_s_filePathDestinationLocal) throws IllegalArgumentException, java.io.IOException {
		return this.download(p_s_filePathSourceFtp, p_s_filePathDestinationLocal, true);
	}
	
	/**
	 * Download content from a file on the ftp(s) server to a local file.
	 * checking login state before executing Download.
	 * 
	 * @param p_s_filePathSourceFtp					path to target file on ftp(s) server
	 * @param p_s_filePathDestinationLocal			path to destination file on local system
	 * @param p_b_overwrite							true - if local file already exists, delete it, false - if local file already exists do not download and return true
	 * @return										true - download successful, false - download failed
	 * @throws IllegalArgumentException				invalid path to target file, invalid path to local system
	 * @throws java.io.IOException					destination directory could not be created, issue reading file stream or issue writing to local system
	 */
	public boolean download(String p_s_filePathSourceFtp, String p_s_filePathDestinationLocal, boolean p_b_overwrite) throws IllegalArgumentException, java.io.IOException {
		if (!this.checkLoginState()) {
			return false;
		}
		
		return this.download(p_s_filePathSourceFtp, p_s_filePathDestinationLocal, p_b_overwrite, 0, false);
	}
	
	/**
	 * Download content from a file on the ftp(s) server to a local file
	 * 
	 * @param p_s_filePathSourceFtp					path to target file on ftp(s) server
	 * @param p_s_filePathDestinationLocal			path to destination file on local system
	 * @param p_b_overwrite							true - if local file already exists, delete it, false - if local file already exists do not download and return true
	 * @param p_b_batch								method in internal batch call, no check for login state and no check if file really exists on ftp(s) server
	 * @return										true - download successful, false - download failed
	 * @throws IllegalArgumentException				invalid path to target file, invalid path to local system
	 * @throws java.io.IOException					destination directory could not be created, issue reading file stream or issue writing to local system
	 */
	private boolean download(String p_s_filePathSourceFtp, String p_s_filePathDestinationLocal, boolean p_b_overwrite, int p_i_fileSize, boolean p_b_batch) throws IllegalArgumentException, java.io.IOException {
		/* if file already exists locally and we do not want to overwrite it, just return true */
		if ( (net.forestany.forestj.lib.io.File.exists(p_s_filePathDestinationLocal)) && (!p_b_overwrite) ) {
													net.forestany.forestj.lib.Global.ilogFine("file already exists locally and we do not want to overwrite it");
			
			return true;
		}
		
		/* download file content to byte array from ftp(s) server */
		byte[] a_data = this.download(p_s_filePathSourceFtp, p_i_fileSize, p_b_batch);
		
		/* download failed */
		if (a_data == null) {
													net.forestany.forestj.lib.Global.ilogWarning("download failed");
			
			return false;
		}
		
		/* check if we must delete old local file */
		if ( (net.forestany.forestj.lib.io.File.exists(p_s_filePathDestinationLocal)) && (p_b_overwrite) ) {
													net.forestany.forestj.lib.Global.ilogFine("delete old local file");
			
			/* delete old local file */
			net.forestany.forestj.lib.io.File.deleteFile(p_s_filePathDestinationLocal);
		}
		
		/* create local directory if it not exists */
		if (!net.forestany.forestj.lib.io.File.isDirectory(p_s_filePathDestinationLocal.substring(0, p_s_filePathDestinationLocal.lastIndexOf(net.forestany.forestj.lib.io.File.DIR)))) {
													net.forestany.forestj.lib.Global.ilogFine("create local directory '" + p_s_filePathDestinationLocal.substring(0, p_s_filePathDestinationLocal.lastIndexOf(net.forestany.forestj.lib.io.File.DIR)) + "' if it not exists");
			
			net.forestany.forestj.lib.io.File.createDirectory(p_s_filePathDestinationLocal.substring(0, p_s_filePathDestinationLocal.lastIndexOf(net.forestany.forestj.lib.io.File.DIR)), true);
		}
		
												net.forestany.forestj.lib.Global.ilogFine("write downloaded bytes to local file '" + p_s_filePathDestinationLocal + "'");
		
		/* write downloaded bytes to local file */
		try (java.io.FileOutputStream o_fileOutputStream = new java.io.FileOutputStream(p_s_filePathDestinationLocal)) {
			o_fileOutputStream.write(a_data);
		}
		
		return true;
	}
	
	/**
	 * Read data from input stream object instance
	 * 
	 * @param p_o_inputStream					input stream object instance
	 * @param p_i_bufferLength					size of buffer which is used reading the input stream
	 * @param p_i_amountData					alternative value to inputstream.available()
	 * @return									input stream content - array of bytes
	 * @throws java.io.IOException				issue reading from input stream object instance
	 */
	private byte[] receiveBytes(java.io.InputStream p_o_inputStream, int p_i_bufferLength, int p_i_amountData) throws java.io.IOException {
		/* try to get amount of data from input stream parameter */
		int i_amountData = p_o_inputStream.available();
		
		/* if we could not get amount of data from input stream parameter or it is to low, take value from other parameter */
		if ( (i_amountData <= 0) || (i_amountData < p_i_amountData) ) {
			i_amountData = p_i_amountData;
		}
		
		/* create receiving byte array, buffer and help variables */
		byte[] a_receivedData = new byte[i_amountData];
		int i_receivedDataPointer = 0;
		byte[] a_buffer = new byte[p_i_bufferLength];
		int i_cycles = (int)java.lang.Math.ceil( ((double)i_amountData / (double)p_i_bufferLength) );
		int i_sum = 0;
		int i_sumProgress = 0;
		
												net.forestany.forestj.lib.Global.ilogFinest("Iterate '" + i_cycles + "' cycles to receive '" + i_amountData + "' bytes with '"  + p_i_bufferLength + "' bytes buffer");
			
		/* iterate cycles to receive bytes with buffer */
		for (int i = 0; i < i_cycles; i++) {
			int i_bytesReadCycle = 0;
			int i_expectedBytes = p_i_bufferLength;
	        int i_bytesReading = -1;
	        
	        										net.forestany.forestj.lib.Global.ilogFinest("p_o_inputStream.read, expecting " + i_expectedBytes + " bytes");
	        
	        /* read from input stream until amount of expected bytes has been reached */
	        while ( (i_expectedBytes > 0) && ( (i_bytesReading = p_o_inputStream.read(a_buffer, 0, Math.min(i_expectedBytes, a_buffer.length))) > 0 ) ) {
	        											net.forestany.forestj.lib.Global.ilogFinest("this.o_inputStream.read, readed " + i_bytesReading + " bytes of expected " + i_expectedBytes + " bytes");
	        	
	        	i_expectedBytes -= i_bytesReading;
	            
	            if (i_bytesReading < 0) {
					throw new IllegalStateException("Could not receive data");
				} else {
					/* copy received bytes to return byte array value */
					for (int j = 0; j < i_bytesReading; j++) {
						if (i_receivedDataPointer >= a_receivedData.length) {
																	if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("Receive data pointer '" + i_receivedDataPointer + "' >= amount of total bytes '" + a_receivedData.length + "' received");
							
							break;
						}
	
						a_receivedData[i_receivedDataPointer++] = a_buffer[j];
						i_bytesReadCycle++;
					}
					
					i_sum += i_bytesReading;
					
															net.forestany.forestj.lib.Global.ilogFinest("Received data, cycle '" + (i + 1) + "' of '" + i_cycles + "', amount of bytes: " + i_bytesReadCycle);
				}
	        }
	        
	        /* update delegate for progress or own console progress bar instance */
	        if ( (this.itf_delegate != null) || (this.o_progressBar != null) ) {
	        	i_sumProgress += i_bytesReading;
	        	
	        	if (this.itf_delegate != null) {
	        		this.itf_delegate.PostProgress((double)i_sumProgress / i_amountData);
	        	}
	        	
				if (this.o_progressBar != null) {
					this.o_progressBar.report((double)i_sumProgress / i_amountData);
				}
			}
		}
		
		/* we have read less bytes than we expected */
		if (i_amountData > i_sum) {
													net.forestany.forestj.lib.Global.ilogFinest("We have read less bytes than we expected: '" + i_amountData + "' > '" + i_sum + "'");
			
			/* new return byte array with correct length */
			byte[] a_trimmedReceivedData = new byte[i_sum];
			
			/* trim byte array data */
			for (int i = 0; i < i_sum; i++) {
				a_trimmedReceivedData[i] = a_receivedData[i];
			}
			
													net.forestany.forestj.lib.Global.ilogFinest("Created new return byte array with correct length: '" + i_sum + "'");
			
			return a_trimmedReceivedData;
		}
		
		return a_receivedData;
	}
	
	/**
	 * Upload local file to a file on ftp(s) server, no append.
	 * checking login state before executing Upload.
	 * 
	 * @param p_s_filePathSourceLocal			path to source file on local system
	 * @param p_s_filePathDestinationFtp		path to destination file on ftp(s) server
	 * @return									true - upload successful, false - upload failed
	 * @throws IllegalArgumentException			invalid path to local file, or invalid path to destination file
	 * @throws java.io.IOException				could not auto create target directory, upload content data, rename destination file, reading local file or delete existing file
	 */
	public boolean upload(String p_s_filePathSourceLocal, String p_s_filePathDestinationFtp) throws IllegalArgumentException, java.io.IOException {
		return this.upload(p_s_filePathSourceLocal, p_s_filePathDestinationFtp, false);
	}
	
	/**
	 * Upload local file to a file on ftp(s) server, append mode possible.
	 * checking login state before executing Upload.
	 * 
	 * @param p_s_filePathSourceLocal			path to source file on local system
	 * @param p_s_filePathDestinationFtp		path to destination file on ftp(s) server
	 * @param p_b_append						true - append content data to existing file, false - overwrite file
	 * @return									true - upload successful, false - upload failed
	 * @throws IllegalArgumentException			invalid path to local file, or invalid path to destination file
	 * @throws java.io.IOException				could not auto create target directory, upload content data, rename destination file, reading local file or delete existing file
	 */
	public boolean upload(String p_s_filePathSourceLocal, String p_s_filePathDestinationFtp, boolean p_b_append) throws IllegalArgumentException, java.io.IOException {
		if (!this.checkLoginState()) {
			return false;
		}
		
		return this.upload(p_s_filePathSourceLocal, p_s_filePathDestinationFtp, p_b_append, false);
	}
		
	/**
	 * Upload local file to a file on ftp(s) server, append mode possible
	 * 
	 * @param p_s_filePathSourceLocal			path to source file on local system
	 * @param p_s_filePathDestinationFtp		path to destination file on ftp(s) server
	 * @param p_b_append						true - append content data to existing file, false - overwrite file
	 * @param p_b_batch							method in internal batch call, no check for login state and no check if destination file or directory really exists on ftp(s) server
	 * @return									true - upload successful, false - upload failed
	 * @throws IllegalArgumentException			invalid path to local file, or invalid path to destination file
	 * @throws java.io.IOException				could not auto create target directory, upload content data, rename destination file, reading local file or delete existing file
	 */
	private boolean upload(String p_s_filePathSourceLocal, String p_s_filePathDestinationFtp, boolean p_b_append, boolean p_b_batch) throws IllegalArgumentException, java.io.IOException {
		/* check file path for upload source and if file really exists locally */
		if ( (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_filePathSourceLocal)) || (!net.forestany.forestj.lib.io.File.exists(p_s_filePathSourceLocal)) ) {
			throw new IllegalArgumentException("Please specify a valid source data file for upload");
		}
		
		/* create help variables for upload procedure */
		byte[] a_buffer = new byte[this.i_bufferSize];
		byte[] a_data = null;
		int i_read = 0;
		java.io.ByteArrayOutputStream o_byteArrayOutputStream = new java.io.ByteArrayOutputStream();
		
												net.forestany.forestj.lib.Global.ilogFine("read all bytes from source file");
		
		/* read all bytes from source file */
		try (java.io.FileInputStream o_fileInputStream = new java.io.FileInputStream(p_s_filePathSourceLocal)) {
		    while ((i_read = o_fileInputStream.read(a_buffer)) != -1) {
		    	o_byteArrayOutputStream.write(a_buffer, 0, i_read);
	        }
		} finally {
			try {
														net.forestany.forestj.lib.Global.ilogFine("convert byte array output stream to byte array and close it");
				
				/* convert byte array output stream to byte array and close it */
				if (o_byteArrayOutputStream != null) {
					a_data = o_byteArrayOutputStream.toByteArray();
					o_byteArrayOutputStream.close();
				}
			} catch (Exception o_exc) {
														net.forestany.forestj.lib.Global.ilogWarning("could not convert byte array output stream: " + o_exc.getMessage());
				
				a_data = null;
			}
		}
		
		/* no bytes retrieved from local file, cannot continue with upload */
		if (a_data == null) {
													net.forestany.forestj.lib.Global.ilogWarning("no bytes retrieved from local file, cannot continue with upload");
			
			return false;
		}
		
		/* start upload procedure */
		return this.upload(a_data, p_s_filePathDestinationFtp, p_b_append, p_b_batch);
	}
	
	/**
	 * Upload content data to a file on ftp(s) server, append mode possible.
	 * checking login state before executing Upload.
	 * 
	 * @param p_a_data							content data as byte array
	 * @param p_s_filePathDestinationFtp		path to destination file on ftp(s) server
	 * @param p_b_append						true - append content data to existing file, false - overwrite file
	 * @return									true - upload successful, false - upload failed
	 * @throws IllegalArgumentException			invalid content data, or invalid path to destination file
	 * @throws java.io.IOException				could not auto create target directory, upload content data, rename destination file or delete existing file
	 */
	public boolean upload(byte[] p_a_data, String p_s_filePathDestinationFtp, boolean p_b_append) throws IllegalArgumentException, java.io.IOException {
		if (!this.checkLoginState()) {
			return false;
		}
		
		return this.upload(p_a_data, p_s_filePathDestinationFtp, p_b_append, false);
	}
	
	/**
	 * Upload content data to a file on ftp(s) server, append mode possible
	 * 
	 * @param p_a_data							content data as byte array
	 * @param p_s_filePathDestinationFtp		path to destination file on ftp(s) server
	 * @param p_b_append						true - append content data to existing file, false - overwrite file
	 * @param p_b_batch							method in internal batch call, no check for login state and no check if destination file or directory really exists on ftp(s) server
	 * @return									true - upload successful, false - upload failed
	 * @throws IllegalArgumentException			invalid content data, or invalid path to destination file
	 * @throws java.io.IOException				could not auto create target directory, upload content data, rename destination file or delete existing file
	 */
	private boolean upload(byte[] p_a_data, String p_s_filePathDestinationFtp, boolean p_b_append, boolean p_b_batch) throws IllegalArgumentException, java.io.IOException {
		/* if we are in a batch call, we do not append data to a file on ftp(s) server */
		if (p_b_batch) {
			p_b_append = false;
		}
		
		/* byte array parameter is null or has no content */
		if ((p_a_data == null) || (p_a_data.length == 0)) {
			throw new IllegalArgumentException("Please specify data for upload");
		}
		
		/* file path parameter for upload is null or empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_filePathDestinationFtp)) {
			throw new IllegalArgumentException("Please specify a local file path for upload");
		}
		
		/* replace all backslashes with slash */
		p_s_filePathDestinationFtp = p_s_filePathDestinationFtp.trim().replace('\\', '/');
		
		/* file path for destination on ftp(s) server must start with root '/' */
		if (!p_s_filePathDestinationFtp.startsWith("/")) {
			p_s_filePathDestinationFtp = "/" + p_s_filePathDestinationFtp;
		}
		
		/* file path for destination on ftp(s) server must not start with '.lock' */
		if (p_s_filePathDestinationFtp.endsWith(".lock")) {
			throw new IllegalArgumentException("Filename for upload must not end with '.lock'");
		}
		
		/* variable for filename on upload */
		String s_filename = "";
		
		/* separate destination path and file name */
		if (p_s_filePathDestinationFtp.lastIndexOf("/") > 0) {
			String s_path = p_s_filePathDestinationFtp.substring(0, p_s_filePathDestinationFtp.lastIndexOf("/"));
			s_filename = p_s_filePathDestinationFtp.substring(p_s_filePathDestinationFtp.lastIndexOf("/") + 1);
			
			/* if this is no batch call, check if destination path exists */
			if ( (!p_b_batch) && (!this.directoryExists(s_path, p_b_batch)) ) {
														net.forestany.forestj.lib.Global.ilogFine("destination path does not exist, so we create it");
				
				/* destination path does not exist, so we create it */
				this.mkDir(s_path, p_b_batch);
			}
		} else { /* we are at root level */
			s_filename = p_s_filePathDestinationFtp.substring(1);
		}
		
		boolean b_return = false;
		boolean b_exists = false;
		
		/* check if destination file on ftp(s) server already exists */
		if (this.fileExists(p_s_filePathDestinationFtp, p_b_batch)) {
													net.forestany.forestj.lib.Global.ilogFine("destination file on ftp(s) server already exists");
			
			b_exists = true;
		} else {
													net.forestany.forestj.lib.Global.ilogFine("destination file on ftp(s) server does not exist, set append parameter to 'false'");
			
			p_b_append = false;
		}
		
		if (!p_b_append) { /* we are uploading a new file to the ftp(s) server */
			/* delete file on ftp(s) server if it already exists */
			if ( (b_exists) && (!this.delete(p_s_filePathDestinationFtp, p_b_batch)) ) {
				throw new java.io.IOException("FTP-Error with file [" + p_s_filePathDestinationFtp + "], cannot delete: " + java.util.Arrays.asList(this.junction().getReplyStrings()));
			}
			
													net.forestany.forestj.lib.Global.ilogFine("create file '" + p_s_filePathDestinationFtp + "' on ftp(s) server with '.lock' as extension");
			
			/* create file on ftp(s) server with '.lock' as extension */
			try (java.io.OutputStream o_outputStream = this.junction().storeFileStream(p_s_filePathDestinationFtp + ".lock")) {
				/* check if stream object is available */
				if (o_outputStream == null) {
															net.forestany.forestj.lib.Global.ilogWarning("could not create output stream to file '" + p_s_filePathDestinationFtp + ".lock'");
					
					return false;
				}
				
				/* initialize console progress bar */
				if (this.o_progressBar != null) {
					this.o_progressBar.init("Uploading . . .", "Upload finished ['" + s_filename + "']", s_filename);
				}
				
				/* sending bytes to output stream */
				this.sendBytes(o_outputStream, p_a_data, this.i_bufferSize);
				
				b_return = true;
			} finally {
				if (b_return) {
					/* we need to complete pending command after output stream is closed, because we used ftpclient.storeFileStream */
					boolean b_retry = true;
					int i_attempts = 1;
					int i_maxAttempts = 40;
					
					do {
						if (!this.completePendingCommand()) {
																net.forestany.forestj.lib.Global.ilogWarning("could not complete pending command");
						} else {
							b_retry = false;
						}
						
						try {
							Thread.sleep(25);
						} catch (InterruptedException o_exc) {
							/* nothing to do */
						}
							
						if (i_attempts++ >= i_maxAttempts) {
							throw new java.io.IOException("Waiting complete pending command failed, after " + i_attempts + " attempts");
						}
					} while (b_retry);
				}
				/* get ftp(s) reply code and reply message */
				this.s_ftpReply = String.join("; ", java.util.Arrays.asList(this.junction().getReplyStrings()));
				this.i_ftpReplyCode = this.junction().getReplyCode();
				
														net.forestany.forestj.lib.Global.ilogFine("reply code and message: " + this.i_ftpReplyCode + " - " + this.s_ftpReply);
				
				/* close console progress bar */
				if (this.o_progressBar != null) {
					this.o_progressBar.close();
				}
				
														net.forestany.forestj.lib.Global.ilogFine("remove '.lock' extension");
				
				/* remove '.lock' extension */
				b_return = this.junction().rename(p_s_filePathDestinationFtp + ".lock", p_s_filePathDestinationFtp);
			}
		} else { /* we are appending data to an existing file on the ftp(s) server */
													net.forestany.forestj.lib.Global.ilogFine("rename existing file with '.lock' extension");
			
			/* rename existing file with '.lock' extension */
			if (!this.junction().rename(p_s_filePathDestinationFtp, p_s_filePathDestinationFtp + ".lock")) {
				throw new java.io.IOException("FTP-Error with file [" + p_s_filePathDestinationFtp + "], cannot rename with '.lock' extension: " + java.util.Arrays.asList(this.junction().getReplyStrings()));
			}
			
													net.forestany.forestj.lib.Global.ilogFine("append bytes[" + p_a_data.length + "] to file '" + p_s_filePathDestinationFtp + "' on ftp(s) server with '.lock' as extension");
			
			/* append bytes to file on ftp(s) server with '.lock' as extension  */
			try (java.io.OutputStream o_outputStream = this.junction().appendFileStream(p_s_filePathDestinationFtp + ".lock")) {
				/* check if stream object is available */
				if (o_outputStream == null) {
															net.forestany.forestj.lib.Global.ilogWarning("could not create output stream to file '" + p_s_filePathDestinationFtp + ".lock'");
					
					return false;
				}
				
				/* initialize console progress bar */
				if (this.o_progressBar != null) {
					this.o_progressBar.init("Uploading . . .", "Upload finished ['" + s_filename + "']", s_filename);
				}
				
				/* sending bytes to output stream */
				this.sendBytes(o_outputStream, p_a_data, this.i_bufferSize);
				
				b_return = true;
			} finally {
				if (b_return) {
					/* we need to complete pending command after output stream is closed, because we used ftpclient.appendFileStream */ 
					boolean b_retry = true;
					int i_attempts = 1;
					int i_maxAttempts = 40;
					
					do {
						if (!this.completePendingCommand()) {
																net.forestany.forestj.lib.Global.ilogWarning("could not complete pending command");
						} else {
							b_retry = false;
						}
						
						try {
							Thread.sleep(25);
						} catch (InterruptedException o_exc) {
							/* nothing to do */
						}
							
						if (i_attempts++ >= i_maxAttempts) {
							throw new java.io.IOException("Waiting complete pending command failed, after " + i_attempts + " attempts");
						}
					} while (b_retry);
				}
				
				/* get ftp(s) reply code and reply message */
				this.s_ftpReply = String.join("; ", java.util.Arrays.asList(this.junction().getReplyStrings()));
				this.i_ftpReplyCode = this.junction().getReplyCode();
				
														net.forestany.forestj.lib.Global.ilogFine("reply code and message: " + this.i_ftpReplyCode + " - " + this.s_ftpReply);
				
				/* close console progress bar */
				if (this.o_progressBar != null) {
					this.o_progressBar.close();
				}
				
														net.forestany.forestj.lib.Global.ilogFine("remove '.lock' extension");
				
				/* remove '.lock' extension */
				b_return = this.junction().rename(p_s_filePathDestinationFtp + ".lock", p_s_filePathDestinationFtp);
			}
		}
		
		return b_return;
	}
	
	/**
	 * Send data to output stream object instance
	 * 
	 * @param p_o_outputStream					output stream object instance
	 * @param p_a_data							content data which will be uploaded
	 * @param p_i_bufferLength					size of buffer which is used send the output stream
	 * @throws java.io.IOException				issue sending to output stream object instance
	 */
	private void sendBytes(java.io.OutputStream p_o_outputStream, byte[] p_a_data, int p_i_bufferLength) throws java.io.IOException {
		/* create sending buffer and help variables */
		int i_sendDataPointer = 0;
		byte[] a_buffer = new byte[p_i_bufferLength];
		int i_cycles = (int)java.lang.Math.ceil( ((double)p_a_data.length / (double)p_i_bufferLength) );
		int i_sum = 0;
		
												net.forestany.forestj.lib.Global.ilogFinest("Iterate '" + i_cycles + "' cycles to transport '" + p_a_data.length + "' bytes with '"  + p_i_bufferLength + "' bytes buffer");
		
		/* iterate cycles to send bytes with buffer */
		for (int i = 0; i < i_cycles; i++) {
			int i_bytesSend = 0;
			
			/* copy data to our buffer until buffer length or overall data length has been reached */
			for (int j = 0; j < p_i_bufferLength; j++) {
				if (i_sendDataPointer >= p_a_data.length) {
															net.forestany.forestj.lib.Global.ilogFinest("Send data pointer '" + i_sendDataPointer + "' >= amount of total bytes '" + p_a_data.length + "' to transport");
					
					break;
				}
				
														if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("Writing byte[" + net.forestany.forestj.lib.Helper.printByteArray(new byte[] {p_a_data[i_sendDataPointer]}, false) + "] to buffer[" + j + "]");
				
				a_buffer[j] = p_a_data[i_sendDataPointer++];
				i_bytesSend++;
			}
			
			/* send to output stream with buffer counter value */
			p_o_outputStream.write(a_buffer, 0, i_bytesSend);
			p_o_outputStream.flush();
			
													net.forestany.forestj.lib.Global.ilogFinest("Sended data, cycle '" + (i + 1) + "' of '" + i_cycles + "', amount of bytes: " + i_bytesSend);
			
			/* update delegate for progress or own console progress bar instance */
			if ( (this.itf_delegate != null) || (this.o_progressBar != null) ) {
	        	i_sum += i_bytesSend;
	        	
	        	if (this.itf_delegate != null) {
	        		this.itf_delegate.PostProgress((double)i_sum / p_a_data.length);
	        	}
	        	
				if (this.o_progressBar != null) {
					this.o_progressBar.report((double)i_sum / p_a_data.length);
				}
			}
		}
	}
	
	/**
	 * Download a complete folder from ftp(s) server to local system, not downloading any sub directories and it's files and not overwriting files and re-download them.
	 * checking login state before executing DownloadFolder.
	 * 
	 * @param p_s_sourceDirectoryFtp					path of source directory on ftp(s) server
	 * @param p_s_destinationDirectoryLocal				path to destination directory on local system
	 * @throws IllegalArgumentException					invalid path to source or destination directory
	 * @throws java.io.IOException						destination directory could not be created, issue reading file stream or issue writing to local system
	 */
	public void downloadFolder(String p_s_sourceDirectoryFtp, String p_s_destinationDirectoryLocal) throws IllegalArgumentException, java.io.IOException {
		this.downloadFolder(p_s_sourceDirectoryFtp, p_s_destinationDirectoryLocal, false, false);
	}
	
	/**
	 * Download a complete folder from ftp(s) server to local system, not downloading any sub directories and it's files.
	 * checking login state before executing DownloadFolder.
	 * 
	 * @param p_s_sourceDirectoryFtp					path of source directory on ftp(s) server
	 * @param p_s_destinationDirectoryLocal				path to destination directory on local system
	 * @param p_b_overwrite								true - if local file or directory already exists, delete it, false - if local file or directory already exists do not download and return true
	 * @throws IllegalArgumentException					invalid path to source or destination directory
	 * @throws java.io.IOException						destination directory could not be created, issue reading file stream or issue writing to local system
	 */
	public void downloadFolder(String p_s_sourceDirectoryFtp, String p_s_destinationDirectoryLocal, boolean p_b_overwrite) throws IllegalArgumentException, java.io.IOException {
		this.downloadFolder(p_s_sourceDirectoryFtp, p_s_destinationDirectoryLocal, p_b_overwrite, false);
	}
	
	/**
	 * Download a complete folder from ftp(s) server to local system.
	 * checking login state before executing DownloadFolder.
	 * 
	 * @param p_s_sourceDirectoryFtp					path of source directory on ftp(s) server
	 * @param p_s_destinationDirectoryLocal				path to destination directory on local system
	 * @param p_b_overwrite								true - if local file or directory already exists, delete it, false - if local file or directory already exists do not download and return true
	 * @param p_b_recursive								true - include all sub directories and it's files, false - stay in source ftp(s) directory
	 * @throws IllegalArgumentException					invalid path to source or destination directory
	 * @throws java.io.IOException						destination directory could not be created, issue reading file stream or issue writing to local system
	 */
	public void downloadFolder(String p_s_sourceDirectoryFtp, String p_s_destinationDirectoryLocal, boolean p_b_overwrite, boolean p_b_recursive) throws IllegalArgumentException, java.io.IOException {
		if (this.checkLoginState()) {
			this.downloadFolder(p_s_sourceDirectoryFtp, p_s_destinationDirectoryLocal, p_b_overwrite, p_b_recursive, false);
		}
	}
	
	/**
	 * Download a complete folder from ftp(s) server to local system
	 * 
	 * @param p_s_sourceDirectoryFtp					path of source directory on ftp(s) server
	 * @param p_s_destinationDirectoryLocal				path to destination directory on local system
	 * @param p_b_overwrite								true - if local file or directory already exists, delete it, false - if local file or directory already exists do not download and return true
	 * @param p_b_recursive								true - include all sub directories and it's files, false - stay in source ftp(s) directory
	 * @param p_b_batch									method in internal batch call, no check for login state and no check if source directory really exists on ftp(s) server
	 * @throws IllegalArgumentException					invalid path to source or destination directory
	 * @throws java.io.IOException						destination directory could not be created, issue reading file stream or issue writing to local system
	 */
	private void downloadFolder(String p_s_sourceDirectoryFtp, String p_s_destinationDirectoryLocal, boolean p_b_overwrite, boolean p_b_recursive, boolean p_b_batch) throws IllegalArgumentException, java.io.IOException {
		/* check source directory parameter for download from ftp(s) server */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_sourceDirectoryFtp)) {
			throw new IllegalArgumentException("Please specify a source directory for download from ftp(s) server, parameter is 'null'");
		}
		
		/* replace all backslashes with slash */
		p_s_sourceDirectoryFtp = p_s_sourceDirectoryFtp.trim().replace('\\', '/');
		
		/* check destination directory parameter for download directory on local system */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_destinationDirectoryLocal)) {
			throw new IllegalArgumentException("Please specify a destination directory for download on local system, parameter is 'null'");
		}
		
		/* delete last DIR character from local destination directory */
		if (p_s_destinationDirectoryLocal.endsWith(net.forestany.forestj.lib.io.File.DIR)) {
			p_s_destinationDirectoryLocal = p_s_destinationDirectoryLocal.substring(0, ( p_s_destinationDirectoryLocal.length() - net.forestany.forestj.lib.io.File.DIR.length() ) );
		}
		
		/* not a batch call, check if directory on ftp(s) server really exists */
		if ( (!p_b_batch) && (!this.directoryExists(p_s_sourceDirectoryFtp, p_b_batch)) ) {
			throw new IllegalArgumentException("Please specify a valid source directory[" + p_s_sourceDirectoryFtp + "] for download from the ftp(s) server");
		}
		
		/* if local destination directory does not exist, create it with auto create */
		if (!net.forestany.forestj.lib.io.File.isDirectory(p_s_destinationDirectoryLocal)) {
													net.forestany.forestj.lib.Global.ilogFine("local destination directory '" + p_s_destinationDirectoryLocal.substring(p_s_destinationDirectoryLocal.lastIndexOf(net.forestany.forestj.lib.io.File.DIR)) + "' does not exist, create it with auto create");
			
			net.forestany.forestj.lib.io.File.createDirectory(p_s_destinationDirectoryLocal, true);
		}
		
		/* get all directories and files of directory path */
		org.apache.commons.net.ftp.FTPFile[] a_elements = this.listFTPFiles(p_s_sourceDirectoryFtp);
		
		if (a_elements != null) {
			/* count files of directory and all sub directories for delegate, but only if it is not a batch call */
			if ( (this.itf_delegateFolder != null) && (!p_b_batch) ) {
														net.forestany.forestj.lib.Global.ilogFiner("count files of directory and all sub directories for delegate");
			
				this.i_dirFiles = this.ls(p_s_sourceDirectoryFtp, true, false, p_b_recursive).size();
				
														net.forestany.forestj.lib.Global.ilogFiner("elements to be downloaded: " + this.i_dirFiles);
			}
			
			/* iterate each directory element */
			for (org.apache.commons.net.ftp.FTPFile o_dirElement : a_elements) {
				/* directory element must be not null and not a temporary file */
				if ( (o_dirElement != null) && (!o_dirElement.getName().endsWith(".lock")) && (!o_dirElement.getName().contentEquals(".")) && (!o_dirElement.getName().contentEquals("..")) ) {
					if (o_dirElement.isDirectory()) { /* directory element is a directory */
						/* create local directory with the same name if it does not exist */
						if (!net.forestany.forestj.lib.io.File.isDirectory(p_s_destinationDirectoryLocal)) {
																	net.forestany.forestj.lib.Global.ilogFiner("create local directory: ." + net.forestany.forestj.lib.io.File.DIR + o_dirElement.getName());
							
							net.forestany.forestj.lib.io.File.createDirectory(p_s_destinationDirectoryLocal + net.forestany.forestj.lib.io.File.DIR + o_dirElement.getName());
						}
						
						/* recursive flag set, download all sub directories and its elements with recursive batch call */
						if (p_b_recursive) {
																	net.forestany.forestj.lib.Global.ilogFiner("download sub directory: '" + o_dirElement.getName() + "' to '" + p_s_destinationDirectoryLocal + net.forestany.forestj.lib.io.File.DIR + o_dirElement.getName() + "'");
							
							this.downloadFolder(p_s_sourceDirectoryFtp + "/" + o_dirElement.getName(), p_s_destinationDirectoryLocal + net.forestany.forestj.lib.io.File.DIR + o_dirElement.getName(), p_b_overwrite, p_b_recursive, true);
						}
					} else { /* directory element is a file */
						/* download file if it does not exist locally, file size does not match, or we want to do a clean download */
						if ( (!net.forestany.forestj.lib.io.File.exists(p_s_destinationDirectoryLocal + net.forestany.forestj.lib.io.File.DIR + o_dirElement.getName())) || (net.forestany.forestj.lib.io.File.fileLength(p_s_destinationDirectoryLocal + net.forestany.forestj.lib.io.File.DIR + o_dirElement.getName()) != o_dirElement.getSize()) || (p_b_overwrite) ) {
							boolean b_retry = true;
							int i_attempts = 1;
							int i_maxAttempts = 40;
							
							do {
								if (!this.download(p_s_sourceDirectoryFtp + "/" + o_dirElement.getName(), p_s_destinationDirectoryLocal + net.forestany.forestj.lib.io.File.DIR + o_dirElement.getName(), p_b_overwrite, Long.valueOf(o_dirElement.getSize()).intValue(), true)) {
																			net.forestany.forestj.lib.Global.ilogWarning("could not download file '" + p_s_sourceDirectoryFtp + "/" + o_dirElement.getName() + "'");
								} else {
																			net.forestany.forestj.lib.Global.ilogFiner("downloaded file '" + p_s_destinationDirectoryLocal + net.forestany.forestj.lib.io.File.DIR + o_dirElement.getName() + "'");
									b_retry = false;
								}
								
								try {
									Thread.sleep(25);
								} catch (InterruptedException o_exc) {
									/* nothing to do */
								}
									
								if (i_attempts++ >= i_maxAttempts) {
									throw new java.io.IOException("Downloading file failed, after " + i_attempts + " attempts");
								}
							} while (b_retry);
						}
						
						/* increase download counter and call delegate */
						if (this.itf_delegateFolder != null) {
							this.i_dirSum++;
							this.itf_delegateFolder.PostProgressFolder(this.i_dirSum, this.i_dirFiles);
							
																	net.forestany.forestj.lib.Global.ilogFiner("downloaded: " + this.i_dirSum + "/" + this.i_dirFiles);
						}
					}
				}
			}
		}
		
		/* iteration finished, we can reset download counter */
		if ( (this.itf_delegateFolder != null) && (!p_b_batch) ) {
			this.i_dirSum = 0;
			this.i_dirFiles = 0;
		}
	}
	
	/**
	 * Upload a complete folder from local system to a ftp(s) server, not uploading any sub directories and it's files.
	 * checking login state before executing UploadFolder.
	 * 
	 * @param p_s_sourceDirectoryLocal					path to source directory on local system
	 * @param p_s_destinationdirectoryFtp				path of destination directory on ftp(s) server
	 * @throws IllegalArgumentException					invalid path to source or destination directory
	 * @throws java.io.IOException						could not auto create target directory, upload content data, rename destination file, reading local file or delete existing file
	 */
	public void uploadFolder(String p_s_sourceDirectoryLocal, String p_s_destinationdirectoryFtp) throws IllegalArgumentException, java.io.IOException {
		this.uploadFolder(p_s_sourceDirectoryLocal, p_s_destinationdirectoryFtp, false);
	}
	
	/**
	 * Upload a complete folder from local system to a ftp(s) server.
	 * checking login state before executing UploadFolder.
	 * 
	 * @param p_s_sourceDirectoryLocal					path to source directory on local system
	 * @param p_s_destinationdirectoryFtp				path of destination directory on ftp(s) server
	 * @param p_b_recursive								true - include all sub directories and it's files, false - stay in source directory on local system
	 * @throws IllegalArgumentException					invalid path to source or destination directory
	 * @throws java.io.IOException						could not auto create target directory, upload content data, rename destination file, reading local file or delete existing file
	 */
	public void uploadFolder(String p_s_sourceDirectoryLocal, String p_s_destinationdirectoryFtp, boolean p_b_recursive) throws IllegalArgumentException, java.io.IOException {
		if (!this.checkLoginState()) {
			return;
		}
		
		/* check file path parameter for upload directory on local system */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_sourceDirectoryLocal)) {
			throw new IllegalArgumentException("Please specify a source directory for upload on the local system");
		}
		
		/* check file path parameter for upload directory on ftp(s) server */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_destinationdirectoryFtp)) {
			throw new IllegalArgumentException("Please specify a destination directory for upload on the ftp(s) server");
		}
		
		/* replace all backslashes with slash */
		p_s_destinationdirectoryFtp = p_s_destinationdirectoryFtp.trim().replace('\\', '/');
		
		/* check if file path parameter on local system really exists */
		if (!net.forestany.forestj.lib.io.File.isDirectory(p_s_sourceDirectoryLocal)) {
			throw new IllegalArgumentException("Please specify a valid source directory for upload, directory '" + p_s_sourceDirectoryLocal + "' does not exist");
		}
		
		/* check if directory exists on ftp(s) server */
		if (!this.directoryExists(p_s_destinationdirectoryFtp, true)) {
													net.forestany.forestj.lib.Global.ilogFine("directory '" + p_s_destinationdirectoryFtp + "' does not exist, must be created on ftp(s) server");
			
			/* create directory on ftp(s) server */
			if (!this.mkDir(p_s_destinationdirectoryFtp, true)) {
				throw new java.io.IOException("Could not create destination directory[" + p_s_destinationdirectoryFtp + "]");
			}
		}
		
		/* list all directory elements on local system, optional with all sub directories */
		java.util.List<net.forestany.forestj.lib.io.ListingElement> a_list = net.forestany.forestj.lib.io.File.listDirectory(p_s_sourceDirectoryLocal, p_b_recursive);
		
		/* count all elements which must be uploaded for delegate upload counter */
		if (this.itf_delegateFolder != null) {
													net.forestany.forestj.lib.Global.ilogFine("count all elements which must be uploaded for delegate upload counter");
						
			this.i_dirFiles = a_list.size();
			
			/* do not count directory elements, because these will be created automatically */
			for (net.forestany.forestj.lib.io.ListingElement o_listingElement : a_list) {
				if (o_listingElement.getIsDirectory()) {
					this.i_dirFiles--;
				}
			}
				
													net.forestany.forestj.lib.Global.ilogFine("elements to be uploaded: " + this.i_dirFiles);
		}
		
		/* iterate each directory element */
		for (net.forestany.forestj.lib.io.ListingElement o_listingElement : a_list) {
			/* just get directory or file name as temporary variable */
			String s_foo = o_listingElement.getFullName().substring(p_s_sourceDirectoryLocal.length());
			
			/* replace all backslashes with slash */
			s_foo = s_foo.replace('\\', '/');
			
													net.forestany.forestj.lib.Global.ilogFiner("destination directory: '" + p_s_destinationdirectoryFtp + "' and new remote element: '" + s_foo + "'");
			
			if (o_listingElement.getIsDirectory()) { /* local element is directory */
				/* create directory on ftp(s) server */
				if (!this.directoryExists(p_s_destinationdirectoryFtp + "/" + s_foo, true)) {
					this.mkDir(p_s_destinationdirectoryFtp + "/" + s_foo, true);
					
															net.forestany.forestj.lib.Global.ilogFiner("created directory: " + p_s_destinationdirectoryFtp + "/" + s_foo);
				}
			} else { /* local element is file */
				/* upload file, will be overwritten(delete and re-upload) if it already exists */
				if (this.upload(o_listingElement.getFullName(), p_s_destinationdirectoryFtp + "/" + s_foo, false, true)) {
															net.forestany.forestj.lib.Global.ilogFiner("uploaded file  : " + p_s_destinationdirectoryFtp + "/" + s_foo);	
				} else {
															net.forestany.forestj.lib.Global.ilogWarning("could not upload file  : " + p_s_destinationdirectoryFtp + "/" + s_foo);	
				}
				
				/* increase upload counter and call delegate */
				if (this.itf_delegateFolder != null) {
					this.i_dirSum++;
					this.itf_delegateFolder.PostProgressFolder(this.i_dirSum, this.i_dirFiles);
					
															net.forestany.forestj.lib.Global.ilogFine("uploaded: " + this.i_dirSum + "/" + this.i_dirFiles);
				}
			}
		}
		
		/* iteration finished, we can reset upload counter */
		if (this.itf_delegateFolder != null) {
			this.i_dirSum = 0;
			this.i_dirFiles = 0;
		}
	}
}
