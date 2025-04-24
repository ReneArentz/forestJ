package net.forestany.forestj.lib.net.sftp;

/**
 * SFTP Client class for a connection to a sftp server. SSH tunneling supported.
 * Authentication with user and password, or with key authentication file possible.
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
	 * interface delegate definition which can be instanced outside of sftp.Client class to post progress anywhere of download/upload methods
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
	 * interface delegate definition which can be instanced outside of sftp.Client class to post progress anywhere of amount of files been processed for download/upload
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
	private String s_filePathAuthentication;
	private String s_filePathKnownHosts;
	private boolean b_strictHostKeyChecking;
	private com.jcraft.jsch.Session o_sessionSftp;
	private com.jcraft.jsch.ChannelSftp o_channelSftp;
	private boolean b_loggedIn;
	private int i_bufferSize;
	private int i_dirSum;
	private int i_dirFiles;
	private IDelegate itf_delegate;
	private IDelegateFolder itf_delegateFolder;
	private net.forestany.forestj.lib.ConsoleProgressBar o_progressBar;
	
	private TunnelCredentials o_tunnelCredentials;
	private com.jcraft.jsch.Session o_sessionTunnel;
	private com.jcraft.jsch.ChannelDirectTCPIP o_channelTunnel;
	
	/* Properties */
	
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
	 * Constructor for a sftp client object, using uri parameter specifications.
	 * using Client.BUFFERSIZE(8192) for input/output buffer.
	 * 
	 * @param p_s_uri							sftp uri, must start with 'sftp://' and contain host, port, user and password information
	 * @throws IllegalArgumentException			invalid sftp uri
	 * @throws java.io.IOException				issues starting connection to the sftp server
	 */
	public Client(String p_s_uri) throws IllegalArgumentException, java.io.IOException {
		this(p_s_uri, null);
	}
	
	/**
	 * Constructor for a sftp client object, using uri parameter specifications.
	 * using Client.BUFFERSIZE(8192) for input/output buffer.
	 * 
	 * @param p_s_uri							sftp uri, must start with 'sftp://' and contain host, port, user and password information
	 * @param p_s_filePathKnownHosts			file path to known hosts file
	 * @throws IllegalArgumentException			invalid sftp uri
	 * @throws java.io.IOException				issues starting connection to the sftp server
	 */
	public Client(String p_s_uri, String p_s_filePathKnownHosts) throws IllegalArgumentException, java.io.IOException {
		this(p_s_uri, p_s_filePathKnownHosts, Client.BUFFERSIZE, true);
	}
	
	/**
	 * Constructor for a sftp client object, using uri parameter specifications
	 * 
	 * @param p_s_uri							sftp uri, must start with 'sftp://' and contain host, port, user and password information
	 * @param p_s_filePathKnownHosts			file path to known hosts file
	 * @param p_i_bufferSize					specify buffer size for sending and receiving data
	 * @throws IllegalArgumentException			invalid sftp uri
	 * @throws java.io.IOException				issues starting connection to the sftp server
	 */
	public Client(String p_s_uri, String p_s_filePathKnownHosts, int p_i_bufferSize) throws IllegalArgumentException, java.io.IOException {
		this(p_s_uri, p_s_filePathKnownHosts, p_i_bufferSize, true);
	}
	
	/**
	 * Constructor for a sftp client object, using uri parameter specifications
	 * 
	 * @param p_s_uri							sftp uri, must start with 'sftp://' and contain host, port, user and password information
	 * @param p_s_filePathKnownHosts			file path to known hosts file
	 * @param p_i_bufferSize					specify buffer size for sending and receiving data
	 * @param p_b_strictHostKeyChecking			true - check server connection with known hosts, false - ignore unknown server fingerprint (insecure)
	 * @throws IllegalArgumentException			invalid sftp uri
	 * @throws java.io.IOException				issues starting connection to the sftp server
	 */
	public Client(String p_s_uri, String p_s_filePathKnownHosts, int p_i_bufferSize, boolean p_b_strictHostKeyChecking) throws IllegalArgumentException, java.io.IOException {
		this(p_s_uri, p_s_filePathKnownHosts, p_i_bufferSize, p_b_strictHostKeyChecking, null);
	}
	
	/**
	 * Constructor for a sftp client object, using uri parameter specifications
	 * 
	 * @param p_s_uri							sftp uri, must start with 'sftp://' and contain host, port, user and password information
	 * @param p_s_filePathKnownHosts			file path to known hosts file
	 * @param p_i_bufferSize					specify buffer size for sending and receiving data
	 * @param p_b_strictHostKeyChecking			true - check server connection with known hosts, false - ignore unknown server fingerprint (insecure)
	 * @param p_o_tunnelCredentials				all tunnel credentials properties for ssh tunneling
	 * @throws IllegalArgumentException			invalid sftp uri
	 * @throws java.io.IOException				issues starting connection to the sftp server
	 */
	public Client(String p_s_uri, String p_s_filePathKnownHosts, int p_i_bufferSize, boolean p_b_strictHostKeyChecking, TunnelCredentials p_o_tunnelCredentials) throws IllegalArgumentException, java.io.IOException {
		this.o_sessionSftp = null;
		this.o_channelSftp = null;
		this.b_loggedIn = false;
		this.i_bufferSize = p_i_bufferSize;
		this.itf_delegate = null;
		this.itf_delegateFolder = null;
		this.o_progressBar = null;
		
		this.o_tunnelCredentials = p_o_tunnelCredentials;
		this.o_sessionTunnel = null;
		this.o_channelTunnel = null;
		
		/* check if sftp uri is valid */
		if (!net.forestany.forestj.lib.Helper.matchesRegex(p_s_uri, "(sftp://)([a-zA-Z0-9-_]{4,}):([^\\s]{4,})@([a-zA-Z0-9-_\\.]{4,}):([0-9]{2,5})")) {
			throw new IllegalArgumentException("Invalid sftp uri. Valid sftp uri would be 'sftp://user:password@example.com(:22)'");
		} else {
													net.forestany.forestj.lib.Global.ilogConfig("using uri: " + net.forestany.forestj.lib.Helper.disguiseSubstring(p_s_uri, "//", "@", '*'));
			
			String s_protocol = "sftp://";
			
			/* recognize protocol at start of uri and remove it from uri parameter */
			if (p_s_uri.startsWith(s_protocol)) {
				p_s_uri = p_s_uri.substring(7);
			}
			
			/* get user and password value */
			String[] a_uriParts = p_s_uri.split("@");
			String s_user = a_uriParts[0].substring(0, a_uriParts[0].indexOf(":"));
			String s_password = a_uriParts[0].substring(a_uriParts[0].indexOf(":") + 1);
			
			String s_host = a_uriParts[1];
			
			
			/* remove last '/' character and get host-address */
			if ( (s_host.indexOf("/") >= 0) && (s_host.substring(s_host.indexOf("/")).contentEquals("/")) ) {
				s_host = s_host.substring(0, (s_host.length() - 1));
			}
			
			/* get port from uri if it is available */
			int i_port = 22;
			
			if (s_host.contains(":")) {
				i_port = Integer.valueOf(s_host.substring(s_host.indexOf(":") + 1));
				s_host = s_host.substring(0, s_host.indexOf(":"));
			}
			
													net.forestany.forestj.lib.Global.ilogConfig("retrieved user, password, host-address and port from uri");
			
													net.forestany.forestj.lib.Global.ilogConfig("create sftp wrapper");
			
			/* create sftp wrapper */
			this.createSFTPWrapper(s_protocol + s_host, i_port, s_user, s_password, null, p_s_filePathKnownHosts, p_b_strictHostKeyChecking);
		}
	}
	
	/**
	 * Constructor for a sftp client object, using separate host, port, user and password parameter specifications.
	 * using Client.BUFFERSIZE(8192) for input/output buffer.
	 * 
	 * @param p_s_host										sftp host value, must start with 'sftp://'
	 * @param p_i_port										sftp port value
	 * @param p_s_user										user value
	 * @param p_s_filePathAuthentication					file path to authentication file with private key
	 * @param p_s_password									password for authentication file (null for none)
	 * @throws IllegalArgumentException						wrong sftp host value, invalid sftp port number, missing user or password
	 * @throws java.io.IOException							issues starting connection to the sftp server
	 */
	public Client(String p_s_host, int p_i_port, String p_s_user, String p_s_filePathAuthentication, String p_s_password) throws IllegalArgumentException, java.io.IOException {
		this(p_s_host, p_i_port, p_s_user, p_s_filePathAuthentication, p_s_password, null);
	}
	
	/**
	 * Constructor for a sftp client object, using separate host, port, user and authentication file parameter specifications.
	 * using Client.BUFFERSIZE(8192) for input/output buffer.
	 * 
	 * @param p_s_host										sftp host value, must start with 'sftp://'
	 * @param p_i_port										sftp port value
	 * @param p_s_user										user value
	 * @param p_s_filePathAuthentication					file path to authentication file with private key
	 * @param p_s_password									password for authentication file (null for none)
	 * @param p_s_filePathKnownHosts						file path to known hosts file
	 * @throws IllegalArgumentException						wrong sftp host value, invalid sftp port number, missing user or password
	 * @throws java.io.IOException							issues starting connection to the sftp server
	 */
	public Client(String p_s_host, int p_i_port, String p_s_user, String p_s_filePathAuthentication, String p_s_password, String p_s_filePathKnownHosts) throws IllegalArgumentException, java.io.IOException {
		this(p_s_host, p_i_port, p_s_user, p_s_filePathAuthentication, p_s_password, p_s_filePathKnownHosts, Client.BUFFERSIZE);
	}
	
	/**
	 * Constructor for a sftp client object, using separate host, port, user and authentication file parameter specifications.
	 * using Client.BUFFERSIZE(8192) for input/output buffer.
	 * 
	 * @param p_s_host										sftp host value, must start with 'sftp://'
	 * @param p_i_port										sftp port value
	 * @param p_s_user										user value
	 * @param p_s_filePathAuthentication					file path to authentication file with private key
	 * @param p_s_password									password for authentication file (null for none)
	 * @param p_s_filePathKnownHosts						file path to known hosts file
	 * @param p_i_bufferSize								specify buffer size for sending and receiving data
	 * @throws IllegalArgumentException						wrong sftp host value, invalid sftp port number, missing user or password
	 * @throws java.io.IOException							issues starting connection to the sftp server
	 */
	public Client(String p_s_host, int p_i_port, String p_s_user, String p_s_filePathAuthentication, String p_s_password, String p_s_filePathKnownHosts, int p_i_bufferSize) throws IllegalArgumentException, java.io.IOException {
		this(p_s_host, p_i_port, p_s_user, p_s_filePathAuthentication, p_s_password, p_s_filePathKnownHosts, p_i_bufferSize, true);
	}
	
	/**
	 * Constructor for a sftp client object, using separate host, port, user, password, authentication file, known hosts file and buffer size parameter specifications
	 * 
	 * @param p_s_host										sftp host value, must start with 'sftp://'
	 * @param p_i_port										sftp port value
	 * @param p_s_user										user value
	 * @param p_s_filePathAuthentication					file path to authentication file with private key
	 * @param p_s_password									password for authentication file (null for none)
	 * @param p_s_filePathKnownHosts						file path to known hosts file
	 * @param p_i_bufferSize								specify buffer size for sending and receiving data
	 * @param p_b_strictHostKeyChecking						true - check server connection with known hosts, false - ignore unknown server fingerprint (insecure)
	 * @throws IllegalArgumentException						wrong sftp host value, invalid sftp port number, missing user or password
	 * @throws java.io.IOException							issues starting connection to the sftp server
	 */
	public Client(String p_s_host, int p_i_port, String p_s_user, String p_s_filePathAuthentication, String p_s_password, String p_s_filePathKnownHosts, int p_i_bufferSize, boolean p_b_strictHostKeyChecking) throws IllegalArgumentException, java.io.IOException {
		this(p_s_host, p_i_port, p_s_user, p_s_filePathAuthentication, p_s_password, p_s_filePathKnownHosts, p_i_bufferSize, p_b_strictHostKeyChecking, null);
	}
	
	/**
	 * Constructor for a sftp client object, using separate host, port, user, password, authentication file, known hosts file and buffer size parameter specifications
	 * 
	 * @param p_s_host										sftp host value, must start with 'sftp://'
	 * @param p_i_port										sftp port value
	 * @param p_s_user										user value
	 * @param p_s_filePathAuthentication					file path to authentication file with private key
	 * @param p_s_password									password for authentication file (null for none)
	 * @param p_s_filePathKnownHosts						file path to known hosts file
	 * @param p_i_bufferSize								specify buffer size for sending and receiving data
	 * @param p_b_strictHostKeyChecking						true - check server connection with known hosts, false - ignore unknown server fingerprint (insecure)
	 * @param p_o_tunnelCredentials				all tunnel credentials properties for ssh tunneling
	 * @throws IllegalArgumentException						wrong sftp host value, invalid sftp port number, missing user or password
	 * @throws java.io.IOException							issues starting connection to the sftp server
	 */
	public Client(String p_s_host, int p_i_port, String p_s_user, String p_s_filePathAuthentication, String p_s_password, String p_s_filePathKnownHosts, int p_i_bufferSize, boolean p_b_strictHostKeyChecking, TunnelCredentials p_o_tunnelCredentials) throws IllegalArgumentException, java.io.IOException {
		this.o_sessionSftp = null;
		this.o_channelSftp = null;
		this.b_loggedIn = false;
		this.i_bufferSize = p_i_bufferSize;
		this.i_dirSum = 0;
		this.i_dirFiles = 0;
		this.itf_delegate = null;
		this.itf_delegateFolder = null;
		this.o_progressBar = null;
		
		this.o_tunnelCredentials = p_o_tunnelCredentials;
		this.o_sessionTunnel = null;
		this.o_channelTunnel = null;
		
		this.createSFTPWrapper(p_s_host, p_i_port, p_s_user, p_s_password, p_s_filePathAuthentication, p_s_filePathKnownHosts, p_b_strictHostKeyChecking);
	}
	
	/**
	 * Merging all information to instantiate an sftp client object and start the connection
	 * 
	 * @param p_s_host										sftp host value, must start with 'sftp://'
	 * @param p_i_port										sftp port value
	 * @param p_s_user										user value
	 * @param p_s_password									user password value or password for authentication file (null for none)
	 * @param p_s_filePathAuthentication					file path to authentication file with private key
	 * @param p_s_filePathKnownHosts						file path to known hosts file
	 * @param p_b_strictHostKeyChecking						true - check server connection with known hosts, false - ignore unknown server fingerprint (insecure)
	 * @throws IllegalArgumentException						wrong sftp host value, invalid sftp port number, missing user or password
	 * @throws java.io.IOException							issues starting connection to the sftp server
	 */
	private void createSFTPWrapper(String p_s_host, int p_i_port, String p_s_user, String p_s_password, String p_s_filePathAuthentication, String p_s_filePathKnownHosts, boolean p_b_strictHostKeyChecking) throws IllegalArgumentException, java.io.IOException {
		/* check if host parameter is not null or empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_host)) {
			throw new IllegalArgumentException("SFTP host is null or empty");
		}
		
		/* host address must start with 'sftp://' */
		if (!p_s_host.startsWith("sftp://")) {
			throw new IllegalArgumentException("SFTP host does not start with 'sftp://'");
		} else {
			p_s_host = p_s_host.substring(7);
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("check port and credential validity");
		
		/* check valid sftp port number */
		if (p_i_port < 1) {
			throw new IllegalArgumentException("SFTP port must be at least '1', but was set to '" + p_i_port + "'");
		}
		
		/* check valid sftp port number */
		if (p_i_port > 65535) {
			throw new IllegalArgumentException("SFTP port must be lower equal '65535', but was set to '" + p_i_port + "'");
		}
		
		/* user necessary */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_user)) {
			throw new IllegalArgumentException("SFTP user is null or empty");
		}
		
		/* password or file with private key necessary */
		if ( (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_password)) && (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_filePathAuthentication)) ) {
			throw new IllegalArgumentException("SFTP password or file path with authentication file is null or empty");
		}
		
		this.s_host = p_s_host;
		this.i_port = p_i_port;
		this.s_user = p_s_user;
		this.s_password = p_s_password;
		this.s_filePathAuthentication = p_s_filePathAuthentication;
		this.s_filePathKnownHosts = p_s_filePathKnownHosts;
		this.b_strictHostKeyChecking = p_b_strictHostKeyChecking;
		
												net.forestany.forestj.lib.Global.ilogConfig("start login procedure");
		
		this.login();
	}
	
	/**
	 * Create sftp client object and start connection to sftp server with sftp channel
	 * 
	 * @throws java.io.IOException		issues starting connection to the sftp server
	 */
	public void login() throws java.io.IOException {
		/* if current instance is logged in, we must re-login */
		if (this.b_loggedIn) {
													net.forestany.forestj.lib.Global.ilogConfig("current instance is logged in, we must re-login so logout procedure will be called");
			
			this.logout();
		}
		
		try {
			/* create sftp connection with ssh tunnel */
			if (this.o_tunnelCredentials != null) {
														net.forestany.forestj.lib.Global.ilogConfig("create sftp connection with ssh tunnel");
				
				this.loginWithTunnel();
			} else { /* create sftp connection without ssh tunnel */
														net.forestany.forestj.lib.Global.ilogConfig("create sftp connection without ssh tunnel");
					
				this.loginWithoutTunnel();
			}
		} catch (com.jcraft.jsch.JSchException o_exc) {
			/* if we have unknown host key and session tunnel is not null */
			if ((o_exc instanceof com.jcraft.jsch.JSchUnknownHostKeyException) && (this.o_sessionTunnel != null) && (this.o_sessionTunnel.getHostKey() != null)) {
				net.forestany.forestj.lib.Global.ilogSevere("tunnel host key: " + this.o_sessionTunnel.getHostKey().getHost() + "\t" + this.o_sessionTunnel.getHostKey().getType() + "\t" + this.o_sessionTunnel.getHostKey().getKey());
			}
			
			/* if we have unknown host key and sftp tunnel is not null */
			if ((o_exc instanceof com.jcraft.jsch.JSchUnknownHostKeyException) && (this.o_sessionSftp != null) && (this.o_sessionSftp.getHostKey() != null)) {
				net.forestany.forestj.lib.Global.ilogSevere("sftp host key: " + this.o_sessionSftp.getHostKey().getHost() + "\t" + this.o_sessionSftp.getHostKey().getType() + "\t" + this.o_sessionSftp.getHostKey().getKey());
			}
			
			/* handle exception when connection failed - logout and throw exception */
			this.logout();
			throw new java.io.IOException(
				"SFTP-Error: cannot connect with '" + this.s_user + "' at '" + this.s_host + ":" + this.i_port + "'" + 
				((this.o_tunnelCredentials != null) ? " - using ssh tunnel with '" + this.o_tunnelCredentials.getTunnelUser() + "' at '" + this.o_tunnelCredentials.getTunnelHost() + ":" + this.o_tunnelCredentials.getTunnelPort() + "' over forwarding port '" + this.o_tunnelCredentials.getTunnelLocalPort() + "'" : "") + 
				"; " + o_exc.getMessage());
		}
		
		try {
			/* create sftp channel instance from session instance */
													net.forestany.forestj.lib.Global.ilogConfig("create 'sftp' channel instance from session instance");
				
			this.o_channelSftp = (com.jcraft.jsch.ChannelSftp) this.o_sessionSftp.openChannel("sftp");
			
			/* could not create sftp channel - logout and throw exception */
			if (this.o_channelSftp == null) {
														net.forestany.forestj.lib.Global.ilogWarning("create sftp channel instance from session instance");
				this.logout();
				throw new java.io.IOException("SFTP-Error: issue opening sftp channel with '" + this.s_user + "' at '" + this.s_host + "'");
			}
			
													net.forestany.forestj.lib.Global.ilogConfig("start connection with sftp channel instance");
			
			/* start connection with sftp channel instance */
			this.o_channelSftp.connect();
		} catch (com.jcraft.jsch.JSchException o_exc) {
			/* handle exception when connection failed - logout and throw exception */
			this.logout();
			throw new java.io.IOException("SFTP-Error: issue opening sftp channel to sftp session with '" + this.s_user + "' at '" + this.s_host + ":" + this.i_port + "'" + "; " + o_exc.getMessage());
		}
	}
	
	/**
	 * Method to create sftp session and sftp channel
	 * 
	 * @throws com.jcraft.jsch.JSchException		exception when connection failed and some parameters are not correct
	 */
	private void loginWithoutTunnel() throws com.jcraft.jsch.JSchException {
		net.forestany.forestj.lib.Global.ilogConfig("create jcraft jsch instance to create a sftp session");
		
		/* create jcraft jsch instance to create a sftp session */
		com.jcraft.jsch.JSch o_jsch = new com.jcraft.jsch.JSch();
		/* flag for key or password authentication */
		boolean b_keyAuthentication = false;
		
		/* add known hosts file */
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_filePathKnownHosts)) {
													net.forestany.forestj.lib.Global.ilogConfig("add known hosts file '" + this.s_filePathKnownHosts + "' as identity");
		
			o_jsch.setKnownHosts(this.s_filePathKnownHosts);
		}
		
		/* check if parameter is a file path to authentication file */
		if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_filePathAuthentication)) && (net.forestany.forestj.lib.io.File.exists(this.s_filePathAuthentication)) ) {
			b_keyAuthentication = true;
		
													net.forestany.forestj.lib.Global.ilogConfig("add authentication file '" + this.s_filePathAuthentication + "' as identity");
		
			/* use password to access authentication private key */
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_password)) {
				/* add authentication file as identity and use password to access authentication private key */
				o_jsch.addIdentity(this.s_filePathAuthentication, this.s_password);
			} else {
				/* add authentication file as identity */
				o_jsch.addIdentity(this.s_filePathAuthentication);
			}
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("create sftp session with '" + this.s_user + "' at '" + this.s_host + ":" + this.i_port + "'");
		
		/* create sftp session */
		this.o_sessionSftp = o_jsch.getSession(this.s_user, this.s_host, this.i_port);

												net.forestany.forestj.lib.Global.ilogConfig("set PreferredAuthentications to 'publickey,keyboard-interactive'");

		this.o_sessionSftp.setConfig("PreferredAuthentications", "publickey,password,keyboard-interactive");
		
		/* set password for sftp login, if authentication file parameter was not set */
		if (!b_keyAuthentication) {
													net.forestany.forestj.lib.Global.ilogConfig("set password for sftp login");
		
			this.o_sessionSftp.setPassword(this.s_password);
		
													net.forestany.forestj.lib.Global.ilogConfig("set PreferredAuthentications to 'password,keyboard-interactive', because with password login we do not use 'publickey'");
		
			this.o_sessionSftp.setConfig("PreferredAuthentications", "password,keyboard-interactive");
		}
		
		/* ignore unknown server fingerprint (insecure)? */
		if (!this.b_strictHostKeyChecking) {
													net.forestany.forestj.lib.Global.ilogConfig("set config: strict host key checking = no");
													net.forestany.forestj.lib.Global.logWarning("net.forestany.forestj.lib.net.sftp.Client strict host key checking been set to 'no'");
													net.forestany.forestj.lib.Global.logWarning("ignoring unknown server fingerprint with sftp connection, using this is a security vulnerability!");
		
			/* set config: strict host key checking = no */
			this.o_sessionSftp.setConfig("StrictHostKeyChecking", "no");
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("set connection timeout to 30 seconds");
		
		/* set connection timeout to 30 seconds */
		this.o_sessionSftp.setTimeout(30000);
		
												net.forestany.forestj.lib.Global.ilogConfig("start connection to sftp host");
		
		/* start connection to sftp host */
		this.o_sessionSftp.connect();
	}
	
	/**
	 * Method to create sftp session and sftp channel with ssh tunnel, using ssh session and ssh channel
	 * 
	 * @throws com.jcraft.jsch.JSchException		exception when connection failed and some parameters are not correct
	 */
	private void loginWithTunnel() throws com.jcraft.jsch.JSchException {
												net.forestany.forestj.lib.Global.ilogConfig("create jcraft jsch instance to create a ssh tunnel");
		
		/* create jcraft jsch instance to create a sftp session */
		com.jcraft.jsch.JSch o_jschTunnel = new com.jcraft.jsch.JSch();
		/* flag for tunnel key or password authentication */
		boolean b_tunnelKeyAuthentication = false;
		
		/* add known hosts file */
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_filePathKnownHosts)) {
													net.forestany.forestj.lib.Global.ilogConfig("add known hosts file '" + this.s_filePathKnownHosts + "' as identity for ssh tunnel");
		
			o_jschTunnel.setKnownHosts(this.s_filePathKnownHosts);
		}
		
		/* check if parameter is a file path to authentication file */
		if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(this.o_tunnelCredentials.getTunnelFilePathAuthentication())) && (net.forestany.forestj.lib.io.File.exists(this.o_tunnelCredentials.getTunnelFilePathAuthentication())) ) {
			b_tunnelKeyAuthentication = true;
		
													net.forestany.forestj.lib.Global.ilogConfig("add authentication file '" + this.o_tunnelCredentials.getTunnelFilePathAuthentication() + "' as identity for ssh tunnel");
		
			/* use password to access authentication private key */
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.o_tunnelCredentials.getTunnelPassword())) {
				/* add authentication file as identity for ssh tunnel and use password to access authentication private key */
				o_jschTunnel.addIdentity(this.o_tunnelCredentials.getTunnelFilePathAuthentication(), this.o_tunnelCredentials.getTunnelPassword());
			} else {
				/* add authentication file as identity for ssh tunnel */
				o_jschTunnel.addIdentity(this.o_tunnelCredentials.getTunnelFilePathAuthentication());
			}
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("create ssh tunnel session with '" + this.o_tunnelCredentials.getTunnelUser() + "' at '" + this.o_tunnelCredentials.getTunnelHost() + ":" + this.o_tunnelCredentials.getTunnelPort() + "'");
		
		/* create ssh tunnel session */
		this.o_sessionTunnel = o_jschTunnel.getSession(this.o_tunnelCredentials.getTunnelUser(), this.o_tunnelCredentials.getTunnelHost(), this.o_tunnelCredentials.getTunnelPort());

												net.forestany.forestj.lib.Global.ilogConfig("set PreferredAuthentications to 'publickey,keyboard-interactive'");

		this.o_sessionTunnel.setConfig("PreferredAuthentications", "publickey,password,keyboard-interactive");
		
		/* set password for ssh tunnel, if authentication file parameter was not set */
		if (!b_tunnelKeyAuthentication) {
													net.forestany.forestj.lib.Global.ilogConfig("set password for ssh tunnel");
		
			this.o_sessionTunnel.setPassword(this.o_tunnelCredentials.getTunnelPassword());
		
													net.forestany.forestj.lib.Global.ilogConfig("set PreferredAuthentications to 'password,keyboard-interactive', because with password login we do not use 'publickey' for ssh tunnel");
		
			this.o_sessionTunnel.setConfig("PreferredAuthentications", "password,keyboard-interactive");
		}
		
		/* ignore unknown server fingerprint (insecure)? */
		if (!this.b_strictHostKeyChecking) {
													net.forestany.forestj.lib.Global.ilogConfig("set config: strict host key checking = no, for ssh tunnel");
													net.forestany.forestj.lib.Global.logWarning("net.forestany.forestj.lib.net.sftp.Client strict host key checking been set to 'no', for ssh tunnel");
													net.forestany.forestj.lib.Global.logWarning("ignoring unknown server fingerprint with sftp connection, using this is a security vulnerability!");
		
			/* set config: strict host key checking = no */
			this.o_sessionTunnel.setConfig("StrictHostKeyChecking", "no");
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("set connection timeout to 30 seconds for ssh tunnel");
		
		/* set connection timeout to 30 seconds for ssh tunnel */
		this.o_sessionTunnel.setTimeout(30000);
		
												net.forestany.forestj.lib.Global.ilogConfig("register local port '" + this.o_tunnelCredentials.getTunnelLocalPort() + "' forwarding for ssh tunnel to connect to '" + this.s_host + ":" + this.i_port + "'");
		
		/* register local port forwarding for ssh tunnel */
		this.o_sessionTunnel.setPortForwardingL(this.o_tunnelCredentials.getTunnelLocalPort(), this.s_host, this.i_port);
		
												net.forestany.forestj.lib.Global.ilogConfig("start connection to ssh tunnel");
		
		/* start connection to ssh tunnel */
		this.o_sessionTunnel.connect();
		
												net.forestany.forestj.lib.Global.ilogConfig("create 'direct-tcpip' channel instance from ssh tunnel instance");
		
		/* create 'direct-tcpip' channel instance from ssh tunnel instance */
		this.o_channelTunnel = (com.jcraft.jsch.ChannelDirectTCPIP) this.o_sessionTunnel.openChannel("direct-tcpip");
		
												net.forestany.forestj.lib.Global.ilogConfig("create jcraft jsch instance to create a sftp session");
		
		/* create jcraft jsch instance to create a sftp session */
		com.jcraft.jsch.JSch o_jsch = new com.jcraft.jsch.JSch();
		/* flag for key or password authentication */
		boolean b_keyAuthentication = false;
		
		/* add known hosts file */
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_filePathKnownHosts)) {
													net.forestany.forestj.lib.Global.ilogConfig("add known hosts file '" + this.s_filePathKnownHosts + "' as identity");
		
			o_jsch.setKnownHosts(this.s_filePathKnownHosts);
		}
		
		/* check if parameter is a file path to authentication file */
		if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_filePathAuthentication)) && (net.forestany.forestj.lib.io.File.exists(this.s_filePathAuthentication)) ) {
			b_keyAuthentication = true;
		
													net.forestany.forestj.lib.Global.ilogConfig("add authentication file '" + this.s_filePathAuthentication + "' as identity");
		
			/* use password to access authentication private key */
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(this.s_password)) {
				/* add authentication file as identity and use password to access authentication private key */
				o_jsch.addIdentity(this.s_filePathAuthentication, this.s_password);
			} else {
				/* add authentication file as identity */
				o_jsch.addIdentity(this.s_filePathAuthentication);
			}
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("create sftp session over ssh tunnel with '" + this.s_user + "' at 'localhost:" + this.o_tunnelCredentials.getTunnelLocalPort() + "'");
												
		/* create sftp session over ssh tunnel */
		this.o_sessionSftp = o_jsch.getSession(this.s_user, "localhost", this.o_tunnelCredentials.getTunnelLocalPort());
		
							net.forestany.forestj.lib.Global.ilogConfig("set PreferredAuthentications to 'publickey,keyboard-interactive'");
		
		this.o_sessionSftp.setConfig("PreferredAuthentications", "publickey,password,keyboard-interactive");
		
		/* set password for sftp login, if authentication file parameter was not set */
		if (!b_keyAuthentication) {
													net.forestany.forestj.lib.Global.ilogConfig("set password for sftp login");
		
			this.o_sessionSftp.setPassword(this.s_password);
		
													net.forestany.forestj.lib.Global.ilogConfig("set PreferredAuthentications to 'password,keyboard-interactive' for ssh tunnel, because with password login we do not use 'publickey'");
		
			this.o_sessionSftp.setConfig("PreferredAuthentications", "password,keyboard-interactive");
		}
		
		/* ignore unknown server fingerprint (insecure)? */
		if (!this.b_strictHostKeyChecking) {
													net.forestany.forestj.lib.Global.ilogConfig("set config: strict host key checking = no");
													net.forestany.forestj.lib.Global.logWarning("net.forestany.forestj.lib.net.sftp.Client strict host key checking been set to 'no'");
													net.forestany.forestj.lib.Global.logWarning("ignoring unknown server fingerprint with sftp connection, using this is a security vulnerability!");
		
			/* set config: strict host key checking = no */
			this.o_sessionSftp.setConfig("StrictHostKeyChecking", "no");
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("set connection timeout to 30 seconds");
		
		/* set connection timeout to 30 seconds for ssh tunnel */
		this.o_sessionSftp.setTimeout(30000);
		
												net.forestany.forestj.lib.Global.ilogConfig("start connection to sftp host");
		
		/* start connection to sftp host */
		this.o_sessionSftp.connect();
	}
	
	/**
	 * Logout and disconnect sftp/ssh-tunnel connection/session and null client object
	 */
	public void logout() {
		b_loggedIn = false;
		
		/* logout and disconnect sftp session and channel */
		try {
			if (this.o_channelSftp != null) {
														net.forestany.forestj.lib.Global.ilogConfig("disconnect sftp channel");
				
				this.o_channelSftp.disconnect();
				this.o_channelSftp = null;
			}
		} finally {
			if (this.o_sessionSftp != null) {
														net.forestany.forestj.lib.Global.ilogConfig("disconnect sftp session");
				
				this.o_sessionSftp.disconnect();
				this.o_sessionSftp = null;
			}
		}
		
		/* logout and disconnect ssh tunnel session and channel */
		try {
			if (this.o_channelTunnel != null) {
														net.forestany.forestj.lib.Global.ilogConfig("disconnect ssh tunnel channel");
				
				this.o_channelTunnel.disconnect();
				this.o_channelTunnel = null;
			}
		} finally {
			if (this.o_sessionTunnel != null) {
														net.forestany.forestj.lib.Global.ilogConfig("disconnect ssh tunnel session");
				
				this.o_sessionTunnel.disconnect();
				this.o_sessionTunnel = null;
			}
		}
	}
	
	/**
	 * Creates a directory on sftp server, create all directories until target directory automatically
	 * 
	 * @param p_s_dir						directory with path to be created
	 * @throws IllegalArgumentException		directory parameter is null or empty
	 * @throws java.io.IOException			issue with make directory command
	 */
	public void mkDir(String p_s_dir) throws IllegalArgumentException, java.io.IOException {
		this.mkDir(p_s_dir, true);
	}
	
	/**
	 * Creates a directory on sftp server
	 * 
	 * @param p_s_dir						directory with path to be created
	 * @param p_b_autoCreate				true - create all directories until target directory automatically, false - expect that the complete path to target directory already exists
	 * @throws IllegalArgumentException		directory parameter is null or empty
	 * @throws java.io.IOException			issue with make directory command
	 */
	public void mkDir(String p_s_dir, boolean p_b_autoCreate) throws IllegalArgumentException, java.io.IOException {
		this.mkDir(p_s_dir, p_b_autoCreate, false);
	}
	
	/**
	 * Creates a directory on sftp server
	 * 
	 * @param p_s_dir						directory with path to be created
	 * @param p_b_autoCreate				true - create all directories until target directory automatically, false - expect that the complete path to target directory already exists
	 * @param p_b_batch						method in internal batch call, so we do not check if directory exists before executing command
	 * @throws IllegalArgumentException		directory parameter is null or empty
	 * @throws java.io.IOException			issue with make directory command
	 */
	private void mkDir(String p_s_dir, boolean p_b_autoCreate, boolean p_b_batch) throws IllegalArgumentException, java.io.IOException {
		/* check directory path is null or empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_dir)) {
			throw new IllegalArgumentException("directory path is null or empty");
		}
		
		/* replace all backslashes with slash */
		p_s_dir = p_s_dir.trim().replace('\\', '/');
		
		/* directory path must start with root '/' */
		if (p_s_dir.startsWith("/")) {
			p_s_dir = p_s_dir.substring(1);
		}
		
		/* remove '/' character if directory parameter ends with it */
		if (p_s_dir.endsWith("/")) {
			p_s_dir = p_s_dir.substring(0, p_s_dir.length() - 1);
		}
		
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
						this.o_channelSftp.mkdir("/" + a_createDirectories.get(i));
					}
				}
				
														net.forestany.forestj.lib.Global.ilogFine("create directory '/" + p_s_dir + "'");
				
				/* create directory */
				this.o_channelSftp.mkdir("/" + p_s_dir);
			}
		} catch (com.jcraft.jsch.SftpException o_exc) {
			/* handle sftp exception from jsch library */
			throw new java.io.IOException("SFTP error with creating directory", o_exc);
		}
	}
	
	/**
	 * List all entries of a target sftp directory, not recursive - stay in target sftp directory
	 * 
	 * @param p_s_dir					path to target sftp directory
	 * @param p_b_hideDirectories		true - won't list sub directories, false - list sub directories in result
	 * @param p_b_showTempFiles			true - list temp files with '.lock' extension at the end, false - won't list temporary files with '.lock' extension
	 * @param p_b_showHiddenFiles		true - list temp files starting with '.', false - won't list files starting with '.'
	 * @return							list of Entry object(s) on target sftp directory
	 * @throws java.io.IOException		issue with list directory command
	 */
	public java.util.List<Entry> ls(String p_s_dir, boolean p_b_hideDirectories, boolean p_b_showTempFiles, boolean p_b_showHiddenFiles) throws java.io.IOException {
		return this.ls(p_s_dir, p_b_hideDirectories, p_b_showTempFiles, p_b_showHiddenFiles, false);
	}
	
	/**
	 * List all entries of a target sftp directory
	 * 
	 * @param p_s_dir					path to target sftp directory
	 * @param p_b_hideDirectories		true - won't list sub directories, false - list sub directories in result
	 * @param p_b_showTempFiles			true - list temp files with '.lock' extension at the end, false - won't list temporary files with '.lock' extension
	 * @param p_b_showHiddenFiles		true - list temp files starting with '.', false - won't list files starting with '.'
	 * @param p_b_recursive				true - include all sub directories in result, false - stay in target sftp directory
	 * @return							list of Entry object(s) on target sftp directory
	 * @throws java.io.IOException		issue with list directory command
	 */
	public java.util.List<Entry> ls(String p_s_dir, boolean p_b_hideDirectories, boolean p_b_showTempFiles, boolean p_b_showHiddenFiles, boolean p_b_recursive) throws java.io.IOException {
		java.util.List<Entry> a_list = new java.util.ArrayList<Entry>();
		
		/* check if directory path is null or empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_dir)) {
			throw new IllegalArgumentException("directory path is null or empty");
		}
		
		/* replace all backslashes with slash */
		p_s_dir = p_s_dir.trim().replace('\\', '/');
		
												net.forestany.forestj.lib.Global.ilogFine("list all elements with directory path");
		
		try {
			/* list all elements with directory path */
			java.util.Vector<com.jcraft.jsch.ChannelSftp.LsEntry> a_elements = this.o_channelSftp.ls(p_s_dir);
			
			if (a_elements != null) { /* result is not empty */
														net.forestany.forestj.lib.Global.ilogFine("iterate each directory element");
				
				/* iterate each directory element */
				for (com.jcraft.jsch.ChannelSftp.LsEntry o_dirElement : a_elements) {
					if (
						(o_dirElement != null) &&
						(!o_dirElement.getFilename().contentEquals(".")) &&
						(!o_dirElement.getFilename().contentEquals("..")) &&
						(!( (o_dirElement.getAttrs().isDir()) && (p_b_hideDirectories) )) &&
						(!( (o_dirElement.getFilename().endsWith(".lock")) && (!p_b_showTempFiles) )) &&
						(!( (o_dirElement.getFilename().startsWith(".")) && (!p_b_showHiddenFiles) ))
						
					) {
																net.forestany.forestj.lib.Global.ilogFiner("create new sftp entry object with all directory element information for: '" + o_dirElement.getFilename() + "'");
						
						/* create new sftp entry object with all directory element information and add it to return list value */
						Entry o_ftpEntry = new Entry(
							o_dirElement.getFilename(),
							String.valueOf(o_dirElement.getAttrs().getGId()),
							String.valueOf(o_dirElement.getAttrs().getUId()),
							p_s_dir + "/",
							o_dirElement.getAttrs().getPermissionsString().substring(1),
							o_dirElement.getAttrs().getSize(),
							new java.util.Date(o_dirElement.getAttrs().getMTime() * 1000L),
							o_dirElement.getAttrs().isDir()
						);
						
																net.forestany.forestj.lib.Global.ilogFiner("add directory element to return list value");
						
						/* add directory element to return list value */
						a_list.add(o_ftpEntry);
					}
					
					/* check if we want to list sub directories */
					if ( (p_b_recursive) && (o_dirElement != null) && (!o_dirElement.getFilename().contentEquals(".")) && (!o_dirElement.getFilename().contentEquals("..")) && (o_dirElement.getAttrs().isDir()) ) {
																net.forestany.forestj.lib.Global.ilogFiner("get a list of all directory elements in sub directory for: '" + o_dirElement.getFilename() + "'");
						
						/* get a list of all directory elements in sub directory */
						java.util.List<Entry> a_recursiveResult = this.ls(p_s_dir + "/" + o_dirElement.getFilename(), p_b_hideDirectories, p_b_showTempFiles, p_b_showHiddenFiles, p_b_recursive);
	
																net.forestany.forestj.lib.Global.ilogFiner("add directory elements to return list value");
						
						/* add directory elements to return list value */
						for (Entry o_recursiveResult : a_recursiveResult) {
							a_list.add(o_recursiveResult);
						}
					}
				}
			} else {
													net.forestany.forestj.lib.Global.ilogFine("directory path has no elements");	
			}
		} catch (com.jcraft.jsch.SftpException o_exc) {
			/* handle sftp exception from jsch library */
			throw new java.io.IOException("SFTP error with list directory", o_exc);
		}
			
		return a_list;
	}
	
	/**
	 * Retrieve sftp attributes of an element on sftp server
	 * 
	 * @param p_s_path			path to target sftp file or directory
	 * @return					sftp attributes object or null if it could not be found
	 */
	private com.jcraft.jsch.SftpATTRS getSFTPAttr(String p_s_path) {
		/* return value */
		com.jcraft.jsch.SftpATTRS o_sftpAttrs = null;
		
		/* check path parameter */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_path)) {
			/* path parameter is null or empty, so we return null as result */
			return o_sftpAttrs;
		}
		
		/* replace all backslashes with slash */
		p_s_path = p_s_path.trim().replace('\\', '/');
		
		/* directory path must start with root '/' */
		if (!p_s_path.startsWith("/")) {
			p_s_path = "/" + p_s_path;
		}
		
		try {
													net.forestany.forestj.lib.Global.ilogFinest("retrieve sftp attributes from '" + p_s_path + "'");
			
			/* retrieve sftp attributes */
			o_sftpAttrs = this.o_channelSftp.lstat(p_s_path);
		} catch (com.jcraft.jsch.SftpException o_exc) {
			/* could not retrieve sftp attributes, so we return null as result */
			return null;
		}
		
		return o_sftpAttrs;
	}
	
	/**
	 * Check if a file at target file path on sftp server exists
	 * 
	 * @param p_s_filePath				path to target file on sftp server
	 * @return							true - exists, false - does not exist
	 */
	public boolean fileExists(String p_s_filePath) {
		/* if path parameter ends with '/' we assume it is a directory */
		if (p_s_filePath.endsWith("/")) {
			/* check if directory exists */ 
			return this.directoryExists(p_s_filePath);
		}
		
		if (this.getSFTPAttr(p_s_filePath) == null) { /* we cannot receive sftp attributes from path, it does not exist */
			return false;
		} else { /* received sftp attributes, path exists */
			return true;
		}
	}
	
	/**
	 * Check if a directory at target dir path on sftp server exists
	 * 
	 * @param p_s_directoryPath			path to target directory on sftp server
	 * @return							true - exists, false - does not exist
	 */
	public boolean directoryExists(String p_s_directoryPath) {
		/* get sftp attributes from directory path */
		com.jcraft.jsch.SftpATTRS o_sftpAttrs = this.getSFTPAttr(p_s_directoryPath);
		
		if (o_sftpAttrs == null) { /* we cannot receive sftp attributes from path, it does not exist */
			return false;
		} else { /* received sftp attributes, return directory attribute */
			return o_sftpAttrs.isDir();
		}
	}
	
	/**
	 * Get file length of a file on sftp server
	 * 
	 * @param p_s_filePath				path to target file on sftp server
	 * @return							file size as long value
	 */
	public long getLength(String p_s_filePath) {
		/* get sftp attributes from file path */
		com.jcraft.jsch.SftpATTRS o_sftpAttrs = this.getSFTPAttr(p_s_filePath);
		
		if ( (o_sftpAttrs == null) || (o_sftpAttrs.isDir()) ) { /* we cannot receive sftp attributes from path, or path is a directory */
			return -1;
		} else { /* return size/length attribute */
			return o_sftpAttrs.getSize();
		}
	}
	
	/**
	 * Deletes a file on sftp server 
	 *
	 * @param p_s_filePath					path to target file on sftp server
	 * @throws IllegalArgumentException		invalid path to target file or file does not exist on sftp server
	 * @throws java.io.IOException			issue with delete command
	 */
	public void delete(String p_s_filePath) throws IllegalArgumentException, java.io.IOException {
		this.delete(p_s_filePath, false);
	}
	
	/**
	 * Deletes a file on sftp server 
	 *
	 * @param p_s_filePath					path to target file on sftp server
	 * @param p_b_batch						method in internal batch call, no check if file still exists on sftp server
	 * @throws IllegalArgumentException		invalid path to target file or file does not exist on sftp server
	 * @throws java.io.IOException			issue with delete command
	 */
	private void delete(String p_s_filePath, boolean p_b_batch) throws IllegalArgumentException, java.io.IOException {
		/* check if file path is null or empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_filePath)) {
			throw new IllegalArgumentException("file path is null or empty");
		}
		
		/* replace all backslashes with slash */
		p_s_filePath = p_s_filePath.trim().replace('\\', '/');
		
		/* if this is not a batch call, we check if file really exists on sftp server */
		if ( (!p_b_batch) && (!this.fileExists(p_s_filePath)) ) {
			throw new IllegalArgumentException("File '" + p_s_filePath + "' does not exist on sftp server");
		}
		
		try {
													net.forestany.forestj.lib.Global.ilogFine("delete file: '" + p_s_filePath + "'");
			
			/* delete file */
			this.o_channelSftp.rm(p_s_filePath);
		} catch (com.jcraft.jsch.SftpException o_exc) {
			/* handle sftp exception from jsch library */
			throw new java.io.IOException("SFTP error with removing file", o_exc);
		}
	}
	
	/**
	 * Remove a directory on sftp server with all it's sub directories and elements
	 * 
	 * @param p_s_dir						path to target directory on sftp server
	 * @throws IllegalArgumentException		invalid path to target directory or directory does not exist on sftp server
	 * @throws java.io.IOException			issue with rmdir command
	 */
	public void rmDir(String p_s_dir) throws IllegalArgumentException, java.io.IOException {
		this.rmDir(p_s_dir, false);
	}
	
	/**
	 * Remove a directory on sftp server with all it's sub directories and elements
	 * 
	 * @param p_s_dir						path to target directory on sftp server
	 * @param p_b_batch						method in internal batch call, no check if directory really exists on sftp server
	 * @throws IllegalArgumentException		invalid path to target directory or directory does not exist on sftp server
	 * @throws java.io.IOException			issue with rmdir command
	 */
	private void rmDir(String p_s_dir, boolean p_b_batch) throws IllegalArgumentException, java.io.IOException {
		/* check if directory path is null or empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_dir)) {
			throw new IllegalArgumentException("directory path is null or empty");
		}
		
		/* replace all backslashes with slash */
		p_s_dir = p_s_dir.trim().replace('\\', '/');
		
		/* if this is not a batch call, we check if directory really exists on sftp server */
		if ( (!p_b_batch) && (!this.directoryExists(p_s_dir)) ) {
			throw new IllegalArgumentException("directory '" + p_s_dir +"' does not exist on sftp server");
		}
		
		try {
			/* list all elements with directory path */
			java.util.Vector<com.jcraft.jsch.ChannelSftp.LsEntry> a_elements = (java.util.Vector<com.jcraft.jsch.ChannelSftp.LsEntry>)this.o_channelSftp.ls(p_s_dir);
		
	        if (a_elements != null) { /* result is not empty */
	        	/* count files of directory and all sub directories for delegate, but only if it is not a batch call */
	        	if ( (this.itf_delegateFolder != null) && (!p_b_batch) ) {
	        												net.forestany.forestj.lib.Global.ilogFiner("count files of directory and all sub directories for delegate");
	        		
					this.i_dirFiles = this.ls(p_s_dir, true, false, true, true).size();
					
														net.forestany.forestj.lib.Global.ilogFiner("elements to be deleted: " + this.i_dirFiles);
				}
	        	
	        	/* iterate each directory element */
				for (com.jcraft.jsch.ChannelSftp.LsEntry o_dirElement : a_elements) {
					/* exclude null, '.', '..', and does not end with '.lock' */
					if ( (o_dirElement != null) && (!o_dirElement.getFilename().contentEquals(".")) && (!o_dirElement.getFilename().contentEquals("..")) && (!o_dirElement.getFilename().endsWith(".lock")) ) {
						if (o_dirElement.getAttrs().isDir()) {
																	net.forestany.forestj.lib.Global.ilogFinest("directory element[" + o_dirElement.getFilename() + "] is a directory, so we call Rmdir recursively as batch call");
							
							/* directory element is a directory, so we call RmDir recursively as batch call */
							this.rmDir(p_s_dir + "/" + o_dirElement.getFilename(), true);
						} else {
																	net.forestany.forestj.lib.Global.ilogFinest("directory element[" + o_dirElement.getFilename() + "] is a file, so we delete the file as batch call");
							
							/* directory element is a file, so we delete the file as batch call */
							this.delete(p_s_dir + "/" + o_dirElement.getFilename(), true);
							
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
			} else {
													net.forestany.forestj.lib.Global.ilogFinest("directory path has no elements");	
			}
        } catch (com.jcraft.jsch.SftpException o_exc) {
			/* handle sftp exception from jsch library */
			throw new java.io.IOException("SFTP error with list directory", o_exc);
		}
		
        /* iteration finished, we can reset delete counter */
        if ( (this.itf_delegateFolder != null) && (!p_b_batch) ) {
													net.forestany.forestj.lib.Global.ilogFiner("iteration finished, we can reset delete counter");
			
			this.i_dirSum = 0;
			this.i_dirFiles = 0;
		}
		
        try {
													net.forestany.forestj.lib.Global.ilogFine("delete directory: '" + p_s_dir + "'");
			
			/* delete directory */
			this.o_channelSftp.rmdir(p_s_dir);
		} catch (com.jcraft.jsch.SftpException o_exc) {
			/* handle sftp exception from jsch library */
			throw new java.io.IOException("SFTP error with removing directory", o_exc);
		}
	}
	
	/**
	 * Rename directory or file on sftp server, do not delete existing files with new name
	 * 
	 * @param p_s_old						name of target directory or file on sftp server
	 * @param p_s_new						new name for directory or file
	 * @throws IllegalArgumentException		invalid path to target file or directory or file or directory does not exist on sftp server
	 * @throws java.io.IOException			issue with rename command 
	 */
	public void rename(String p_s_old, String p_s_new) throws IllegalArgumentException, java.io.IOException {
		this.rename(p_s_old, p_s_new, false);
	}
	
	/**
	 * Rename directory or file on sftp server
	 * 
	 * @param p_s_old						name of target directory or file on sftp server
	 * @param p_s_new						new name for directory or file
	 * @param p_b_overwrite					if a file already exists with new name, delete it
	 * @throws IllegalArgumentException		invalid path to target file or directory or file or directory does not exist on sftp server
	 * @throws java.io.IOException			issue with rename command 
	 */
	public void rename(String p_s_old, String p_s_new, boolean p_b_overwrite) throws IllegalArgumentException, java.io.IOException {
		this.rename(p_s_old, p_s_new, p_b_overwrite, false);
	}
	
	/**
	 * Rename directory or file on sftp server
	 * 
	 * @param p_s_old						name of target directory or file on sftp server
	 * @param p_s_new						new name for directory or file
	 * @param p_b_overwrite					if a file already exists with new name, delete it
	 * @param p_b_batch						method in internal batch call, no check if directory or file really exists on sftp server
	 * @throws IllegalArgumentException		invalid path to target file or directory or file or directory does not exist on sftp server
	 * @throws java.io.IOException			issue with rename command 
	 */
	private void rename(String p_s_old, String p_s_new, boolean p_b_overwrite, boolean p_b_batch) throws IllegalArgumentException, java.io.IOException {
		/* check if old and new name are empty or both are equal */
		if ( (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_old)) || (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_new)) || (p_s_old.trim().contentEquals(p_s_new.trim())) ) {
			throw new IllegalArgumentException("Old or new name are empty or both are equal");
		}
		
		/* check if directory or file which we want to rename exists, but only if it is not a batch call */
		if ( (!p_b_batch) && ( (!this.fileExists(p_s_old)) && (!this.directoryExists(p_s_old)) ) ) {
			throw new IllegalArgumentException("Directory or File '" + p_s_old + "' does not exist on sftp server");
		}
		
		/* check if a file exists with new file name, in case we want to overwrite it anyway we delete file with the new file name */
		if (this.fileExists(p_s_new)) {
			if (!p_b_overwrite) { /* file already exists */
				throw new IllegalArgumentException("File '" + p_s_new + "' already exists on sftp server");
			} else { /* file already exists so we delete it */
														net.forestany.forestj.lib.Global.ilogFine("delete file with new name");
				
				this.delete(p_s_new, true);
				
														net.forestany.forestj.lib.Global.ilogFine("deleted file with new name");
			}
		} else if (this.directoryExists(p_s_new)) { /* check if a directory exists with new name */
			/* directory cannot be overwritten */
			throw new IllegalArgumentException("Directory '" + p_s_new + "' already exists on sftp server and cannot be overwritten. Please delete target directory first");
		}
		
		/* replace all backslashes with slash */
		p_s_old = p_s_old.trim().replace('\\', '/');
		p_s_new = p_s_new.trim().replace('\\', '/');
		
		try {
													net.forestany.forestj.lib.Global.ilogFine("rename file: '" + p_s_old + "' to '" + p_s_new + "'");
			
			/* rename file */
			this.o_channelSftp.rename(p_s_old, p_s_new);
		} catch (com.jcraft.jsch.SftpException o_exc) {
			/* handle sftp exception from jsch library */
			throw new java.io.IOException("SFTP error with renaming file", o_exc);
		}
	}
	
	/**
	 * Download content from a file on the sftp server
	 * 
	 * @param p_s_filePathSourceSftp				path to target file on sftp server
	 * @return										file content - array of bytes
	 * @throws IllegalArgumentException				invalid path to target file or file size could not be retrieved
	 * @throws java.io.IOException					issue reading file stream or get amount of content length from input stream
	 */
	public byte[] download(String p_s_filePathSourceSftp) throws IllegalArgumentException, java.io.IOException {
		/* check file path parameter */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_filePathSourceSftp)) {
			throw new IllegalArgumentException("Please specify a file path for download");
		}
		
		/* replace all backslashes with slash */
		p_s_filePathSourceSftp = p_s_filePathSourceSftp.trim().replace('\\', '/');
		
		/* retrieve file length on sftp server, at we check if file really exists */
		long l_fileSize = this.getLength(p_s_filePathSourceSftp);
		
		if (l_fileSize < 0) { /* file does not exist on sftp server */
			throw new IllegalArgumentException("File '" + p_s_filePathSourceSftp + "' does not exist on sftp server");
		}
		
		/* initialize console progress bar */
		if (this.o_progressBar != null) {
			this.o_progressBar.init("Downloading . . .", "Download finished ['" + p_s_filePathSourceSftp.substring(p_s_filePathSourceSftp.lastIndexOf("/") + 1) + "']", p_s_filePathSourceSftp.substring(p_s_filePathSourceSftp.lastIndexOf("/") + 1));
		}
		
		byte[] a_data = null;
		
		try {
			/* receiving bytes from input stream */
			a_data = this.receiveBytes(this.o_channelSftp.get(p_s_filePathSourceSftp), this.i_bufferSize, Long.valueOf(l_fileSize).intValue());
		} catch (com.jcraft.jsch.SftpException o_exc) {
			/* handle sftp exception from jsch library */
			throw new java.io.IOException("SFTP error with get", o_exc);
		} finally {
			/* close console progress bar */
			if (this.o_progressBar != null) {
				this.o_progressBar.close();
			}
		}
		
		/* get a return value, but no content so we set data to null */
		if ( (a_data != null) && (a_data.length <= 0) ) {
			a_data = null;
		}
		
		return a_data;
	}
	
	/**
	 * Download content from a file on the sftp server to a local file, local file will be overwritten
	 * 
	 * @param p_s_filePathSourceSftp				path to target file on sftp server
	 * @param p_s_filePathDestinationLocal			path to destination file on local system
	 * @throws IllegalArgumentException				invalid path to target file, invalid path to local system
	 * @throws java.io.IOException					destination directory could not be created, issue reading file stream or issue writing to local system
	 */
	public void download(String p_s_filePathSourceSftp, String p_s_filePathDestinationLocal) throws IllegalArgumentException, java.io.IOException {
		this.download(p_s_filePathSourceSftp, p_s_filePathDestinationLocal, true);
	}
	
	/**
	 * Download content from a file on the sftp server to a local file
	 * 
	 * @param p_s_filePathSourceSftp				path to target file on sftp server
	 * @param p_s_filePathDestinationLocal			path to destination file on local system
	 * @param p_b_overwrite							true - if local file already exists, delete it, false - if local file already exists do not download and return true
	 * @throws IllegalArgumentException				invalid path to target file, invalid path to local system
	 * @throws java.io.IOException					destination directory could not be created, issue reading file stream or issue writing to local system
	 */
	public void download(String p_s_filePathSourceSftp, String p_s_filePathDestinationLocal, boolean p_b_overwrite) throws IllegalArgumentException, java.io.IOException {
		/* download file content to byte array from sftp server */
		byte[] a_data = this.download(p_s_filePathSourceSftp);
		
		/* download failed */
		if (a_data == null) {
													net.forestany.forestj.lib.Global.ilogWarning("download failed");
			
			return;
		}
		
		/* check if we must delete old local file */
		if ( (net.forestany.forestj.lib.io.File.exists(p_s_filePathDestinationLocal)) && (p_b_overwrite) ) {
													net.forestany.forestj.lib.Global.ilogFine("delete old local file");
			
			/* delete old local file */
			net.forestany.forestj.lib.io.File.deleteFile(p_s_filePathDestinationLocal);
		}
		
		/* create local directory if it not exists */
		if (!net.forestany.forestj.lib.io.File.isDirectory(p_s_filePathDestinationLocal.substring(0, p_s_filePathDestinationLocal.lastIndexOf(net.forestany.forestj.lib.io.File.DIR)))) {
													net.forestany.forestj.lib.Global.ilogFine("create local directory if it not exists");
			
			net.forestany.forestj.lib.io.File.createDirectory(p_s_filePathDestinationLocal.substring(0, p_s_filePathDestinationLocal.lastIndexOf(net.forestany.forestj.lib.io.File.DIR)), true);
		}
		
												net.forestany.forestj.lib.Global.ilogFine("write downloaded bytes to local file");
		
		/* write downloaded bytes to local file */
		try (java.io.FileOutputStream o_fileOutputStream = new java.io.FileOutputStream(p_s_filePathDestinationLocal)) {
			o_fileOutputStream.write(a_data);
		}
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
		/* check if stream object is available */
		if (p_o_inputStream == null) {
			throw new java.io.IOException("could not create input stream");
		}
		
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
	 * Upload local file to a file on sftp server, no append
	 * 
	 * @param p_s_filePathSourceLocal			path to source file on local system
	 * @param p_s_filePathDestinationSftp		path to destination file on sftp server
	 * @throws IllegalArgumentException			invalid path to local file, or invalid path to destination file
	 * @throws java.io.IOException				could not auto create target directory, upload content data, rename destination file, reading local file or delete existing file
	 */
	public void upload(String p_s_filePathSourceLocal, String p_s_filePathDestinationSftp) throws IllegalArgumentException, java.io.IOException {
		this.upload(p_s_filePathSourceLocal, p_s_filePathDestinationSftp, false);
	}
	
	/**
	 * Upload local file to a file on sftp server, append mode possible
	 * 
	 * @param p_s_filePathSourceLocal			path to source file on local system
	 * @param p_s_filePathDestinationSftp		path to destination file on sftp server
	 * @param p_b_append						true - append content data to existing file, false - overwrite file
	 * @throws IllegalArgumentException			invalid path to local file, or invalid path to destination file
	 * @throws java.io.IOException				could not auto create target directory, upload content data, rename destination file, reading local file or delete existing file
	 */
	public void upload(String p_s_filePathSourceLocal, String p_s_filePathDestinationSftp, boolean p_b_append) throws IllegalArgumentException, java.io.IOException {
		this.upload(p_s_filePathSourceLocal, p_s_filePathDestinationSftp, p_b_append, false);
	}
	
	/**
	 * Upload local file to a file on sftp server, append mode possible
	 * 
	 * @param p_s_filePathSourceLocal			path to source file on local system
	 * @param p_s_filePathDestinationSftp		path to destination file on sftp server
	 * @param p_b_append						true - append content data to existing file, false - overwrite file
	 * @param p_b_batch							method in internal batch call, no check if destination file or directory really exists on sftp server
	 * @throws IllegalArgumentException			invalid path to local file, or invalid path to destination file
	 * @throws java.io.IOException				could not auto create target directory, upload content data, rename destination file, reading local file or delete existing file
	 */
	private void upload(String p_s_filePathSourceLocal, String p_s_filePathDestinationSftp, boolean p_b_append, boolean p_b_batch) throws IllegalArgumentException, java.io.IOException {
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
			throw new IllegalArgumentException("no bytes retrieved from local file, cannot continue with upload");
		}
		
		/* start upload procedure */
		this.upload(a_data, p_s_filePathDestinationSftp, p_b_append, p_b_batch);
	}
	
	/**
	 * Upload content data to a file on sftp server, append mode possible
	 * 
	 * @param p_a_data							content data as byte array
	 * @param p_s_filePathDestinationSftp		path to destination file on sftp server
	 * @param p_b_append						true - append content data to existing file, false - overwrite file
	 * @throws IllegalArgumentException			invalid content data, or invalid path to destination file
	 * @throws java.io.IOException				could not auto create target directory, upload content data, rename destination file or delete existing file
	 */
	public void upload(byte[] p_a_data, String p_s_filePathDestinationSftp, boolean p_b_append) throws IllegalArgumentException, java.io.IOException {
		this.upload(p_a_data, p_s_filePathDestinationSftp, p_b_append, false);
	}
	
	/**
	 * Upload content data to a file on sftp server, append mode possible
	 * 
	 * @param p_a_data							content data as byte array
	 * @param p_s_filePathDestinationSftp		path to destination file on sftp server
	 * @param p_b_append						true - append content data to existing file, false - overwrite file
	 * @param p_b_batch							method in internal batch call, no check for login state and no check if destination file or directory really exists on sftp server
	 * @throws IllegalArgumentException			invalid content data, or invalid path to destination file
	 * @throws java.io.IOException				could not auto create target directory, upload content data, rename destination file or delete existing file
	 */
	private void upload(byte[] p_a_data, String p_s_filePathDestinationSftp, boolean p_b_append, boolean p_b_batch) throws IllegalArgumentException, java.io.IOException {
		/* if we are in a batch call, we do not append data to a file on sftp server */
		if (p_b_batch) {
			p_b_append = false;
		}
		
		/* byte array parameter is null or has no content */
		if ((p_a_data == null) || (p_a_data.length == 0)) {
			throw new IllegalArgumentException("Please specify data for upload");
		}
		
		/* file path parameter for upload is null or empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_filePathDestinationSftp)) {
			throw new IllegalArgumentException("Please specify a local file path for upload");
		}
		
		/* replace all backslashes with slash */
		p_s_filePathDestinationSftp = p_s_filePathDestinationSftp.trim().replace('\\', '/');
		
		/* file path for destination on sftp server must start with root '/' */
		if (!p_s_filePathDestinationSftp.startsWith("/")) {
			p_s_filePathDestinationSftp = "/" + p_s_filePathDestinationSftp;
		}
		
		/* file path for destination on sftp server must not start with '.lock' */
		if (p_s_filePathDestinationSftp.endsWith(".lock")) {
			throw new IllegalArgumentException("Filename for upload must not end with '.lock'");
		}
		
		/* variable for filename on upload */
		String s_filename = "";
		
		/* separate destination path and file name */
		if (p_s_filePathDestinationSftp.lastIndexOf("/") > 0) {
			String s_path = p_s_filePathDestinationSftp.substring(0, p_s_filePathDestinationSftp.lastIndexOf("/"));
			s_filename = p_s_filePathDestinationSftp.substring(p_s_filePathDestinationSftp.lastIndexOf("/") + 1);
			
			/* if this is no batch call, check if destination path exists */
			if ( (!p_b_batch) && (!this.directoryExists(s_path)) ) {
														net.forestany.forestj.lib.Global.ilogFine("destination path does not exist, so we create it");
				
				/* destination path does not exist, so we create it */
				this.mkDir(s_path);
			}
		}
		
		/* check if destination file on sftp server already exists */
		if (this.fileExists(p_s_filePathDestinationSftp)) {
			if (p_b_append) {
														net.forestany.forestj.lib.Global.ilogFine("rename destination file by add '.lock' extension");
				
				/* rename destination file by add '.lock' extension */
				this.rename(p_s_filePathDestinationSftp, p_s_filePathDestinationSftp + ".lock", true, true);
			} else {
														net.forestany.forestj.lib.Global.ilogFine("destination file on sftp server already exists, so we delete it");
				
				/* delete file with batch call */
				this.delete(p_s_filePathDestinationSftp, true);
			}
		}
		
		boolean b_alternativeAppend = false;
		
		try (java.io.OutputStream o_outputStream = this.o_channelSftp.put(p_s_filePathDestinationSftp + ".lock", ( (p_b_append) ? com.jcraft.jsch.ChannelSftp.APPEND : com.jcraft.jsch.ChannelSftp.OVERWRITE ) )) {
			/* check if stream object is available */
			if (o_outputStream == null) {
				throw new java.io.IOException("could not create output stream to file '" + p_s_filePathDestinationSftp + ".lock'");
			}
			
			/* initialize console progress bar */
			if (this.o_progressBar != null) {
				this.o_progressBar.init("Uploading . . .", "Upload finished ['" + s_filename + "']", s_filename);
			}
			
			/* sending bytes to output stream */
			this.sendBytes(o_outputStream, p_a_data, this.i_bufferSize);
		} catch (com.jcraft.jsch.SftpException o_exc) {
			/* somehow in this special case, sftp server does not support APPEND */
			if ( (p_b_append) && (o_exc.id == 3) && (o_exc.getMessage().contentEquals("Permission denied")) ) {
				b_alternativeAppend = true;
				
				/* just do something so that progress bar can close fine */
				if (this.o_progressBar != null) {
					for (int i = 0; i < 10000000; i++) {
						i = i + 1 - 1;
					}
				}
			} else {
				/* handle sftp exception from jsch library */
				throw new java.io.IOException("SFTP error with put", o_exc);
			}
		} finally {
			/* close console progress bar */
			if (this.o_progressBar != null) {
				this.o_progressBar.close();
			}
		}
		
		/* remove '.lock' extension */
		this.rename(p_s_filePathDestinationSftp + ".lock", p_s_filePathDestinationSftp, true, true);
		
		/* run alternative execution for APPEND */
		if (b_alternativeAppend) {
													net.forestany.forestj.lib.Global.ilogFine("run alternative execution for APPEND");
													net.forestany.forestj.lib.Global.ilogFiner("download file content from existing file '" + p_s_filePathDestinationSftp + "'");
			
			/* download file content from existing file */
			byte[] a_fileContent = this.download(p_s_filePathDestinationSftp);
			
			/* check download value */
			if (a_fileContent == null) {
				throw new java.io.IOException("could not download file content from '" + p_s_filePathDestinationSftp + "' for appending data");
			} else {
														net.forestany.forestj.lib.Global.ilogFiner("create new byte array for new file content, length = " + (a_fileContent.length + p_a_data.length));
														
				/* create new byte array for new file content */
				byte[] a_newContent = new byte[a_fileContent.length + p_a_data.length];
				
				int i = 0;
				
														net.forestany.forestj.lib.Global.ilogFiner("add content from existing file, length = " + a_fileContent.length);
														
				/* add content from existing file */
				for (int j = 0; j < a_fileContent.length; j++) {
					a_newContent[i++] = a_fileContent[j];
				}
				
														net.forestany.forestj.lib.Global.ilogFiner("add content from byte array parameter, length = " + p_a_data.length);
														
				/* add content from byte array parameter */
				for (int j = 0; j < p_a_data.length; j++) {
					a_newContent[i++] = p_a_data[j];
				}
				
														net.forestany.forestj.lib.Global.ilogFiner("overwrite existing file with new file content");
														
				/* overwrite existing file with new file content */
				this.upload(a_newContent, p_s_filePathDestinationSftp, false, true);
			}
		}
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
	 * Download a complete folder from sftp server to local system, not downloading any sub directories and it's files not overwriting files and re-download them
	 * 
	 * @param p_s_sourceDirectorySftp					path of source directory on sftp server
	 * @param p_s_destinationDirectoryLocal				path to destination directory on local system
	 * @throws IllegalArgumentException					invalid path to source or destination directory
	 * @throws java.io.IOException						destination directory could not be created, issue reading file stream or issue writing to local system
	 */
	public void downloadFolder(String p_s_sourceDirectorySftp, String p_s_destinationDirectoryLocal) throws IllegalArgumentException, java.io.IOException {
		this.downloadFolder(p_s_sourceDirectorySftp, p_s_destinationDirectoryLocal, false, false);
	}
	
	/**
	 * Download a complete folder from sftp server to local system, not downloading any sub directories and it's files
	 * 
	 * @param p_s_sourceDirectorySftp					path of source directory on sftp server
	 * @param p_s_destinationDirectoryLocal				path to destination directory on local system
	 * @param p_b_overwrite								true - if local file or directory already exists, delete it, false - if local file or directory already exists do not download and return true
	 * @throws IllegalArgumentException					invalid path to source or destination directory
	 * @throws java.io.IOException						destination directory could not be created, issue reading file stream or issue writing to local system
	 */
	public void downloadFolder(String p_s_sourceDirectorySftp, String p_s_destinationDirectoryLocal, boolean p_b_overwrite) throws IllegalArgumentException, java.io.IOException {
		this.downloadFolder(p_s_sourceDirectorySftp, p_s_destinationDirectoryLocal, p_b_overwrite, false);
	}
	
	/**
	 * Download a complete folder from sftp server to local system
	 * 
	 * @param p_s_sourceDirectorySftp					path of source directory on sftp server
	 * @param p_s_destinationDirectoryLocal				path to destination directory on local system
	 * @param p_b_overwrite								true - if local file or directory already exists, delete it, false - if local file or directory already exists do not download and return true
	 * @param p_b_recursive								true - include all sub directories and it's files, false - stay in source sftp directory
	 * @throws IllegalArgumentException					invalid path to source or destination directory
	 * @throws java.io.IOException						destination directory could not be created, issue reading file stream or issue writing to local system
	 */
	public void downloadFolder(String p_s_sourceDirectorySftp, String p_s_destinationDirectoryLocal, boolean p_b_overwrite, boolean p_b_recursive) throws IllegalArgumentException, java.io.IOException {
		this.downloadFolder(p_s_sourceDirectorySftp, p_s_destinationDirectoryLocal, p_b_overwrite, p_b_recursive, false);
	}
	
	/**
	 * Download a complete folder from sftp server to local system
	 * 
	 * @param p_s_sourceDirectorySftp					path of source directory on sftp server
	 * @param p_s_destinationDirectoryLocal				path to destination directory on local system
	 * @param p_b_overwrite								true - if local file or directory already exists, delete it, false - if local file or directory already exists do not download and return true
	 * @param p_b_recursive								true - include all sub directories and it's files, false - stay in source sftp directory
	 * @param p_b_batch									method in internal batch call, no check if source directory really exists on sftp server
	 * @throws IllegalArgumentException					invalid path to source or destination directory
	 * @throws java.io.IOException						destination directory could not be created, issue reading file stream or issue writing to local system
	 */
	private void downloadFolder(String p_s_sourceDirectorySftp, String p_s_destinationDirectoryLocal, boolean p_b_overwrite, boolean p_b_recursive, boolean p_b_batch) throws IllegalArgumentException, java.io.IOException {
		/* check source directory parameter for download from sftp server */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_sourceDirectorySftp)) {
			throw new IllegalArgumentException("Please specify a source directory for download from sftp server, parameter is 'null'");
		}
		
		/* replace all backslashes with slash */
		p_s_sourceDirectorySftp = p_s_sourceDirectorySftp.trim().replace('\\', '/');
		
		/* check destination directory parameter for download directory on local system */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_destinationDirectoryLocal)) {
			throw new IllegalArgumentException("Please specify a destination directory for download on local system, parameter is 'null'");
		}
		
		/* not a batch call, check if directory on sftp server really exists */
		if ( (!p_b_batch) && (!this.directoryExists(p_s_sourceDirectorySftp)) ) {
			throw new IllegalArgumentException("Please specify a valid source directory[" + p_s_sourceDirectorySftp + "] for download from the sftp server");
		}
		
		/* if local destination directory does not exist, create it with auto create */
		if (!net.forestany.forestj.lib.io.File.isDirectory(p_s_destinationDirectoryLocal)) {
													net.forestany.forestj.lib.Global.ilogFine("local destination directory does not exist, create it with auto create");
													
			net.forestany.forestj.lib.io.File.createDirectory(p_s_destinationDirectoryLocal, true);
		}
		
		try {
			/* get all directories and files of directory path */
			java.util.Vector<com.jcraft.jsch.ChannelSftp.LsEntry> a_elements = (java.util.Vector<com.jcraft.jsch.ChannelSftp.LsEntry>)this.o_channelSftp.ls(p_s_sourceDirectorySftp);
			
			if (a_elements != null) {
				/* count files of directory and all sub directories for delegate, but only if it is not a batch call */
				if ( (this.itf_delegateFolder != null) && (!p_b_batch) ) {
															net.forestany.forestj.lib.Global.ilogFiner("count files of directory and all sub directories for delegate");
					
					this.i_dirFiles = this.ls(p_s_sourceDirectorySftp, true, false, true, p_b_recursive).size();
					
															net.forestany.forestj.lib.Global.ilogFiner("elements to be downloaded: " + this.i_dirFiles);
				}
				
				/* iterate each directory element */
				for (com.jcraft.jsch.ChannelSftp.LsEntry o_dirElement : a_elements) {
					/* directory element must be not null, not a temporary file, not '.' and not '..' */
					if ( (o_dirElement != null) && (!o_dirElement.getFilename().endsWith(".lock")) && (!o_dirElement.getFilename().contentEquals(".")) && (!o_dirElement.getFilename().contentEquals("..")) ) {
						if (o_dirElement.getAttrs().isDir()) { /* directory element is a directory */
							/* create local directory with the same name if it does not exist */
							if (!net.forestany.forestj.lib.io.File.isDirectory(p_s_destinationDirectoryLocal)) {
																		net.forestany.forestj.lib.Global.ilogFiner("create local directory: " + p_s_destinationDirectoryLocal + net.forestany.forestj.lib.io.File.DIR + o_dirElement.getFilename());
								
								net.forestany.forestj.lib.io.File.createDirectory(p_s_destinationDirectoryLocal + net.forestany.forestj.lib.io.File.DIR + o_dirElement.getFilename());
							}
							
							/* recursive flag set, download all sub directories and its elements with recursive batch call */
							if (p_b_recursive) {
																		net.forestany.forestj.lib.Global.ilogFiner("download sub directory: " + o_dirElement.getFilename());
								
								this.downloadFolder(p_s_sourceDirectorySftp + "/" + o_dirElement.getFilename(), p_s_destinationDirectoryLocal + net.forestany.forestj.lib.io.File.DIR + o_dirElement.getFilename(), p_b_overwrite, p_b_recursive, true);
							}
						} else { /* directory element is a file */
							/* download file if it does not exist locally, file size does not match, or we want to do a clean download */
							if ( (!net.forestany.forestj.lib.io.File.exists(p_s_destinationDirectoryLocal + net.forestany.forestj.lib.io.File.DIR + o_dirElement.getFilename())) || (p_b_overwrite) ) {
								/* try to download file multiple times if there may be insignificant connection issues */
								boolean b_retry = true;
								int i_attempts = 1;
								int i_maxAttempts = 10;
								
								do {
									this.download(p_s_sourceDirectorySftp + "/" + o_dirElement.getFilename(), p_s_destinationDirectoryLocal + net.forestany.forestj.lib.io.File.DIR + o_dirElement.getFilename(), p_b_overwrite);
									
									if (net.forestany.forestj.lib.io.File.exists(p_s_destinationDirectoryLocal + net.forestany.forestj.lib.io.File.DIR + o_dirElement.getFilename())) {
																			net.forestany.forestj.lib.Global.ilogFiner("downloaded file: " + p_s_destinationDirectoryLocal + net.forestany.forestj.lib.io.File.DIR + o_dirElement.getFilename());
																			
										b_retry = false;
									}
									
									if (b_retry) {
										try {
											Thread.sleep(250);
										} catch (InterruptedException o_exc) {
											/* nothing to do */
										}
									}
									
									if (i_attempts++ >= i_maxAttempts) {
										throw new java.io.IOException("Downloading file '" + p_s_destinationDirectoryLocal + net.forestany.forestj.lib.io.File.DIR + o_dirElement.getFilename() + "' failed, after " + i_attempts + " attempts");
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
		} catch (com.jcraft.jsch.SftpException o_exc) {
			/* handle sftp exception from jsch library */
			throw new java.io.IOException("SFTP error with list directory", o_exc);
		}
		
		/* iteration finished, we can reset download counter */
		if ( (this.itf_delegateFolder != null) && (!p_b_batch) ) {
			this.i_dirSum = 0;
			this.i_dirFiles = 0;
		}
	}
	
	/**
	 * Upload a complete folder from local system to a sftp server, not uploading any sub directories and it's files
	 * 
	 * @param p_s_sourceDirectoryLocal					path to source directory on local system
	 * @param p_s_destinationdirectorySftp				path of destination directory on sftp server
	 * @throws IllegalArgumentException					invalid path to source or destination directory
	 * @throws java.io.IOException						could not auto create target directory, upload content data, rename destination file, reading local file or delete existing file
	 */
	public void uploadFolder(String p_s_sourceDirectoryLocal, String p_s_destinationdirectorySftp) throws IllegalArgumentException, java.io.IOException {
		this.uploadFolder(p_s_sourceDirectoryLocal, p_s_destinationdirectorySftp, false);
	}
	
	/**
	 * Upload a complete folder from local system to a sftp server
	 * 
	 * @param p_s_sourceDirectoryLocal					path to source directory on local system
	 * @param p_s_destinationdirectorySftp				path of destination directory on sftp server
	 * @param p_b_recursive								true - include all sub directories and it's files, false - stay in source directory on local system
	 * @throws IllegalArgumentException					invalid path to source or destination directory
	 * @throws java.io.IOException						could not auto create target directory, upload content data, rename destination file, reading local file or delete existing file
	 */
	public void uploadFolder(String p_s_sourceDirectoryLocal, String p_s_destinationdirectorySftp, boolean p_b_recursive) throws IllegalArgumentException, java.io.IOException {
		/* check file path parameter for upload directory on local system */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_sourceDirectoryLocal)) {
			throw new IllegalArgumentException("Please specify a source directory for upload on the local system");
		}
		
		/* check file path parameter for upload directory on sftp server */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_destinationdirectorySftp)) {
			throw new IllegalArgumentException("Please specify a destination directory for upload on the sftp server");
		}
		
		/* replace all backslashes with slash */
		p_s_destinationdirectorySftp = p_s_destinationdirectorySftp.trim().replace('\\', '/');
		
		/* check if file path parameter on local system really exists */
		if (!net.forestany.forestj.lib.io.File.isDirectory(p_s_sourceDirectoryLocal)) {
			throw new IllegalArgumentException("Please specify a valid source directory for upload, directory '" + p_s_sourceDirectoryLocal + "' does not exist");
		}
		
		/* create directory on sftp server if it does not exist, MkDir will check that */
		this.mkDir(p_s_destinationdirectorySftp, true, false);
		
		/* list all directory elements on local system, optional with all sub directories */
		java.util.List<net.forestany.forestj.lib.io.ListingElement> a_list = net.forestany.forestj.lib.io.File.listDirectory(p_s_sourceDirectoryLocal, p_b_recursive);
		
		/* count all elements which must be uploaded for delegate upload counter */
		if (this.itf_delegateFolder != null) {
													net.forestany.forestj.lib.Global.ilogFiner("count all elements which must be uploaded for delegate upload counter");
						
			this.i_dirFiles = a_list.size();
			
			/* do not count directory elements, because these will be created automatically */
			for (net.forestany.forestj.lib.io.ListingElement o_listingElement : a_list) {
				if (o_listingElement.getIsDirectory()) {
					this.i_dirFiles--;
				}
			}
			
				
													net.forestany.forestj.lib.Global.ilogFiner("elements to be uploaded: " + this.i_dirFiles);
		}
		
		/* iterate each directory element */
		for (net.forestany.forestj.lib.io.ListingElement o_listingElement : a_list) {
			/* just get directory or file name as temporary variable */
			String s_foo = o_listingElement.getFullName().substring(p_s_sourceDirectoryLocal.length());
			
			/* replace all backslashes with slash */
			s_foo = s_foo.replace('\\', '/');
			
													net.forestany.forestj.lib.Global.ilogFiner("destination directory: '" + p_s_destinationdirectorySftp + "' and new remote element: '" + s_foo + "'");
			
			if (o_listingElement.getIsDirectory()) { /* local element is directory */
				/* create directory on sftp server if it does not exist, MkDir will check that */
				this.mkDir(p_s_destinationdirectorySftp + "/" + s_foo, true, false);
				
														net.forestany.forestj.lib.Global.ilogFiner("created directory: " + p_s_destinationdirectorySftp + "/" + s_foo);
			} else { /* local element is file */
				/* upload file, will be overwritten(delete and re-upload) if it already exists */
				this.upload(o_listingElement.getFullName(), p_s_destinationdirectorySftp + "/" + s_foo, false, true);
				
														net.forestany.forestj.lib.Global.ilogFiner("uploaded file  : " + p_s_destinationdirectorySftp + "/" + s_foo);
				
				/* increase upload counter and call delegate */
				if (this.itf_delegateFolder != null) {
					this.i_dirSum++;
					this.itf_delegateFolder.PostProgressFolder(this.i_dirSum, this.i_dirFiles);
					
															net.forestany.forestj.lib.Global.ilogFiner("uploaded: " + this.i_dirSum + "/" + this.i_dirFiles);
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
