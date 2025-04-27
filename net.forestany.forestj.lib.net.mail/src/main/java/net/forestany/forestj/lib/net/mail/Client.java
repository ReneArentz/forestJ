package net.forestany.forestj.lib.net.mail;

/**
 * Mail Client class for a connection to a mail server.
 * supported protocols: POP3, SMTP and IMAP.
 */
public class Client {
	
	/* Fields */
	
	private jakarta.mail.Session o_sessionGet;
	private jakarta.mail.Session o_sessionSend;
	private String s_server;
	private String s_user;
	private boolean b_pop3;
	private String s_protocol;
	private Folder o_currentFolder;
	
	/* Properties */
	
	/**
	 * get current folder
	 * 
	 * @return net.forestany.forestj.lib.net.mail.Folder
	 * @throws IllegalArgumentException pop3 is not supported
	 */
	public Folder getCurrentFolder() throws IllegalArgumentException {
		if (this.s_protocol.startsWith("pop3")) {
			throw new IllegalArgumentException("POP3(s) protocol is not supported");
		}
		
		return this.o_currentFolder;
	}
	
	/* Methods */
	
	/**
	 * Constructor for a smtp client object, only for sending mails, no encrypted and secure data transfer with ssl
	 * 
	 * @param p_s_smtpServer									host address for smtp server, add ';STARTTLS' to enable STARTTLS with smtp, add ';TLS' to enable TLS connection with smtp
	 * @param p_i_smtpServerPort								port for smtp server
	 * @param p_s_user											server user
	 * @param p_s_password										server user password
	 * @throws IllegalArgumentException							invalid or empty(null) parameter value					
	 * @throws java.security.KeyStoreException					cannot use truststore, algorithm failed to check integrity or a certificate could not be loaded
	 * @throws java.io.IOException								could not read truststore file
	 * @throws jakarta.mail.MessagingException					connection and/or authentication failed to smtp server			
	 */
	public Client(String p_s_smtpServer, int p_i_smtpServerPort, String p_s_user, String p_s_password) throws IllegalArgumentException, java.security.KeyStoreException, java.io.IOException, jakarta.mail.MessagingException {
		this(null, p_s_smtpServer, 1, p_i_smtpServerPort, null, null, p_s_user, p_s_password, false);
	}
	
	/**
	 * Constructor for a smtp client object, only for sending mails, no encrypted and secure data transfer with ssl
	 * 
	 * @param p_s_smtpServer									host address for smtp server, add ';STARTTLS' to enable STARTTLS with smtp, add ';TLS' to enable TLS connection with smtp
	 * @param p_i_smtpServerPort								port for smtp server
	 * @param p_s_user											server user
	 * @param p_s_password										server user password
	 * @param p_b_sslSend										true - use ssl to send mails, false - no secure data transfer
	 * @throws IllegalArgumentException							invalid or empty(null) parameter value					
	 * @throws java.security.KeyStoreException					cannot use truststore, algorithm failed to check integrity or a certificate could not be loaded
	 * @throws java.io.IOException								could not read truststore file
	 * @throws jakarta.mail.MessagingException					connection and/or authentication failed to smtp server			
	 */
	public Client(String p_s_smtpServer, int p_i_smtpServerPort, String p_s_user, String p_s_password, boolean p_b_sslSend) throws IllegalArgumentException, java.security.KeyStoreException, java.io.IOException, jakarta.mail.MessagingException {
		this(null, p_s_smtpServer, 1, p_i_smtpServerPort, null, null, p_s_user, p_s_password, false, false, p_b_sslSend, null, null);
	}
	
	/**
	 * Constructor for a smtp client object, only for sending mails with encrypted and secure data transfer with ssl
	 * 
	 * @param p_s_smtpServer									host address for smtp server, add ';STARTTLS' to enable STARTTLS with smtp, add ';TLS' to enable TLS connection with smtp
	 * @param p_i_smtpServerPort								port for smtp server
	 * @param p_s_user											server user
	 * @param p_s_password										server user password
	 * @param p_s_trustStorePath								file path to truststore file
	 * @param p_s_trustStorePassword							password to open truststore file
	 * @throws IllegalArgumentException							invalid or empty(null) parameter value					
	 * @throws java.security.KeyStoreException					cannot use truststore, algorithm failed to check integrity or a certificate could not be loaded
	 * @throws java.io.IOException								could not read truststore file
	 * @throws jakarta.mail.MessagingException					connection and/or authentication failed to smtp server			
	 */
	public Client(String p_s_smtpServer, int p_i_smtpServerPort, String p_s_user, String p_s_password, String p_s_trustStorePath, String p_s_trustStorePassword) throws IllegalArgumentException, java.security.KeyStoreException, java.io.IOException, jakarta.mail.MessagingException {
		this(null, p_s_smtpServer, 1, p_i_smtpServerPort, null, null, p_s_user, p_s_password, false, false, true, p_s_trustStorePath, p_s_trustStorePassword);
	}
	
	/**
	 * Constructor for a mail client object, using parameter for both mail and smtp server(could be identical), using imap protocol, no encrypted and secure data transfer with ssl
	 * 
	 * @param p_s_mailServer									host address for server, add ';STARTTLS' to enable STARTTLS with pop3/imap, add ';TLS' to enable TLS connection with imap/pop3
	 * @param p_i_mailServerPort								port for mail server
	 * @param p_i_smtpServerPort								port for smtp server
	 * @param p_s_user											server user
	 * @param p_s_password										server user password
	 * @throws IllegalArgumentException							invalid or empty(null) parameter value					
	 * @throws java.security.KeyStoreException					cannot use truststore, algorithm failed to check integrity or a certificate could not be loaded
	 * @throws java.io.IOException								could not read truststore file
	 * @throws jakarta.mail.MessagingException					connection and/or authentication failed to mail or smtp server			
	 */
	public Client(String p_s_mailServer, int p_i_mailServerPort, int p_i_smtpServerPort, String p_s_user, String p_s_password) throws IllegalArgumentException, java.security.KeyStoreException, java.io.IOException, jakarta.mail.MessagingException {
		this(p_s_mailServer, p_s_mailServer, p_i_mailServerPort, p_i_smtpServerPort, p_s_user, p_s_password, p_s_user, p_s_password, false);
	}
	
	/**
	 * Constructor for a mail client object, using parameter for both mail and smtp server(could be identical), using imap protocol and with encrypted secure data transfer with ssl
	 * 
	 * @param p_s_mailServer									host address for server, add ';STARTTLS' to enable STARTTLS with pop3/imap, add ';TLS' to enable TLS connection with imap/pop3
	 * @param p_i_mailServerPort								port for mail server
	 * @param p_i_smtpServerPort								port for smtp server
	 * @param p_s_user											server user
	 * @param p_s_password										server user password
	 * @param p_b_sslGet										true - use ssl to retrieve mails, false - no secure data transfer
	 * @param p_b_sslSend										true - use ssl to send mails, false - no secure data transfer
	 * @throws IllegalArgumentException							invalid or empty(null) parameter value					
	 * @throws java.security.KeyStoreException					cannot use truststore, algorithm failed to check integrity or a certificate could not be loaded
	 * @throws java.io.IOException								could not read truststore file
	 * @throws jakarta.mail.MessagingException					connection and/or authentication failed to mail or smtp server			
	 */
	public Client(String p_s_mailServer, int p_i_mailServerPort, int p_i_smtpServerPort, String p_s_user, String p_s_password, boolean p_b_sslGet, boolean p_b_sslSend) throws IllegalArgumentException, java.security.KeyStoreException, java.io.IOException, jakarta.mail.MessagingException {
		this(p_s_mailServer, p_s_mailServer, p_i_mailServerPort, p_i_smtpServerPort, p_s_user, p_s_password, p_s_user, p_s_password, false, p_b_sslGet, p_b_sslSend, null, null);
	}
	
	/**
	 * Constructor for a mail client object, using parameter for both mail and smtp server(could be identical), using imap protocol and with encrypted secure data transfer with ssl
	 * 
	 * @param p_s_mailServer									host address for server, add ';STARTTLS' to enable STARTTLS with pop3/imap, add ';TLS' to enable TLS connection with imap/pop3
	 * @param p_i_mailServerPort								port for mail server
	 * @param p_i_smtpServerPort								port for smtp server
	 * @param p_s_user											server user
	 * @param p_s_password										server user password
	 * @param p_s_trustStorePath								file path to truststore file
	 * @param p_s_trustStorePassword							password to open truststore file
	 * @throws IllegalArgumentException							invalid or empty(null) parameter value					
	 * @throws java.security.KeyStoreException					cannot use truststore, algorithm failed to check integrity or a certificate could not be loaded
	 * @throws java.io.IOException								could not read truststore file
	 * @throws jakarta.mail.MessagingException					connection and/or authentication failed to mail or smtp server			
	 */
	public Client(String p_s_mailServer, int p_i_mailServerPort, int p_i_smtpServerPort, String p_s_user, String p_s_password, String p_s_trustStorePath, String p_s_trustStorePassword) throws IllegalArgumentException, java.security.KeyStoreException, java.io.IOException, jakarta.mail.MessagingException {
		this(p_s_mailServer, p_s_mailServer, p_i_mailServerPort, p_i_smtpServerPort, p_s_user, p_s_password, p_s_user, p_s_password, false, true, true, p_s_trustStorePath, p_s_trustStorePassword);
	}
	
	/**
	 * Constructor for a mail client object, using parameter for both mail and smtp server(could be identical), no encrypted and secure data transfer with ssl
	 * 
	 * @param p_s_mailServer									host address for server, add ';STARTTLS' to enable STARTTLS with pop3/imap, add ';TLS' to enable TLS connection with imap/pop3
	 * @param p_i_mailServerPort								port for mail server
	 * @param p_i_smtpServerPort								port for smtp server
	 * @param p_s_user											server user
	 * @param p_s_password										server user password
	 * @param p_b_usePop3										true - use pop3 protocol to retrieve mails, false - use imap protocol to retrieve mails
	 * @throws IllegalArgumentException							invalid or empty(null) parameter value					
	 * @throws java.security.KeyStoreException					cannot use truststore, algorithm failed to check integrity or a certificate could not be loaded
	 * @throws java.io.IOException								could not read truststore file
	 * @throws jakarta.mail.MessagingException					connection and/or authentication failed to mail or smtp server			
	 */
	public Client(String p_s_mailServer, int p_i_mailServerPort, int p_i_smtpServerPort, String p_s_user, String p_s_password, boolean p_b_usePop3) throws IllegalArgumentException, java.security.KeyStoreException, java.io.IOException, jakarta.mail.MessagingException {
		this(p_s_mailServer, p_s_mailServer, p_i_mailServerPort, p_i_smtpServerPort, p_s_user, p_s_password, p_s_user, p_s_password, p_b_usePop3);
	}
	
	/**
	 * Constructor for a mail client object, using separate parameter for mail server and smtp server, no encrypted and secure data transfer with ssl
	 * 
	 * @param p_s_mailServer									host address for mail server, add ';STARTTLS' to enable STARTTLS with pop3/imap, add ';TLS' to enable TLS connection with imap/pop3
	 * @param p_s_smtpServer									host address for smtp server, add ';STARTTLS' to enable STARTTLS with smtp, add ';TLS' to enable TLS connection with smtp
	 * @param p_i_mailServerPort								port for mail server
	 * @param p_i_smtpServerPort								port for smtp server
	 * @param p_s_mailServerUser								mail server user
	 * @param p_s_mailServerPassword							mail server user password
	 * @param p_s_smtpServerUser								smtp server user
	 * @param p_s_smtpServerPassword							smtp server user password
	 * @param p_b_usePop3										true - use pop3 protocol to retrieve mails, false - use imap protocol to retrieve mails
	 * @throws IllegalArgumentException							invalid or empty(null) parameter value					
	 * @throws java.security.KeyStoreException					cannot use truststore, algorithm failed to check integrity or a certificate could not be loaded
	 * @throws java.io.IOException								could not read truststore file
	 * @throws jakarta.mail.MessagingException					connection and/or authentication failed to mail or smtp server			
	 */
	public Client(
		String p_s_mailServer,
		String p_s_smtpServer,
		int p_i_mailServerPort,
		int p_i_smtpServerPort,
		String p_s_mailServerUser,
		String p_s_mailServerPassword,
		String p_s_smtpServerUser,
		String p_s_smtpServerPassword,
		boolean p_b_usePop3
	) throws IllegalArgumentException, java.security.KeyStoreException, java.io.IOException, jakarta.mail.MessagingException {
		this(p_s_mailServer, p_s_smtpServer, p_i_mailServerPort, p_i_smtpServerPort, p_s_mailServerUser, p_s_mailServerPassword, p_s_smtpServerUser, p_s_smtpServerPassword, p_b_usePop3, false, false, null, null);
	}
	
	/**
	 * Constructor for a mail client object, using separate parameter for mail server and smtp server
	 * 
	 * @param p_s_mailServer									host address for mail server, add ';STARTTLS' to enable STARTTLS with pop3/imap, add ';TLS' to enable TLS connection with imap/pop3
	 * @param p_s_smtpServer									host address for smtp server, add ';STARTTLS' to enable STARTTLS with smtp, add ';TLS' to enable TLS connection with smtp
	 * @param p_i_mailServerPort								port for mail server
	 * @param p_i_smtpServerPort								port for smtp server
	 * @param p_s_mailServerUser								mail server user
	 * @param p_s_mailServerPassword							mail server user password
	 * @param p_s_smtpServerUser								smtp server user
	 * @param p_s_smtpServerPassword							smtp server user password
	 * @param p_b_usePop3										true - use pop3 protocol to retrieve mails, false - use imap protocol to retrieve mails
	 * @param p_b_sslGet										true - use ssl to retrieve mails, false - no secure data transfer
	 * @param p_b_sslSend										true - use ssl to send mails, false - no secure data transfer
	 * @param p_s_trustStorePath								file path to truststore file
	 * @param p_s_trustStorePassword							password to open truststore file
	 * @throws IllegalArgumentException							invalid or empty(null) parameter value					
	 * @throws java.security.KeyStoreException					cannot use truststore, algorithm failed to check integrity or a certificate could not be loaded
	 * @throws java.io.IOException								could not read truststore file
	 * @throws jakarta.mail.MessagingException					connection and/or authentication failed to mail or smtp server			
	 */
	public Client(
		String p_s_mailServer,
		String p_s_smtpServer,
		int p_i_mailServerPort,
		int p_i_smtpServerPort,
		String p_s_mailServerUser,
		String p_s_mailServerPassword,
		String p_s_smtpServerUser,
		String p_s_smtpServerPassword,
		boolean p_b_usePop3,
		boolean p_b_sslGet,
		boolean p_b_sslSend,
		String p_s_trustStorePath,
		String p_s_trustStorePassword
	) throws IllegalArgumentException, java.security.KeyStoreException, java.io.IOException, jakarta.mail.MessagingException {
		this(p_s_mailServer, p_s_smtpServer, p_i_mailServerPort, p_i_smtpServerPort, p_s_mailServerUser, p_s_mailServerPassword, p_s_smtpServerUser, p_s_smtpServerPassword, p_b_usePop3, p_b_sslGet, p_b_sslSend, p_s_trustStorePath, p_s_trustStorePassword, false);
	}
	
	/**
	 * Constructor for a mail client object, using separate parameter for mail server and smtp server
	 * 
	 * @param p_s_mailServer									host address for mail server, add ';STARTTLS' to enable STARTTLS with pop3/imap, add ';TLS' to enable TLS connection with imap/pop3
	 * @param p_s_smtpServer									host address for smtp server, add ';STARTTLS' to enable STARTTLS with smtp, add ';TLS' to enable TLS connection with smtp
	 * @param p_i_mailServerPort								port for mail server
	 * @param p_i_smtpServerPort								port for smtp server
	 * @param p_s_mailServerUser								mail server user
	 * @param p_s_mailServerPassword							mail server user password
	 * @param p_s_smtpServerUser								smtp server user
	 * @param p_s_smtpServerPassword							smtp server user password
	 * @param p_b_usePop3										true - use pop3 protocol to retrieve mails, false - use imap protocol to retrieve mails
	 * @param p_b_sslGet										true - use ssl to retrieve mails, false - no secure data transfer
	 * @param p_b_sslSend										true - use ssl to send mails, false - no secure data transfer
	 * @param p_s_trustStorePath								file path to truststore file
	 * @param p_s_trustStorePassword							password to open truststore file
	 * @param p_b_useOAuth2										OAuth2 authentication flag for ssl connection
	 * @throws IllegalArgumentException							invalid or empty(null) parameter value					
	 * @throws java.security.KeyStoreException					cannot use truststore, algorithm failed to check integrity or a certificate could not be loaded
	 * @throws java.io.IOException								could not read truststore file
	 * @throws jakarta.mail.MessagingException					connection and/or authentication failed to mail or smtp server			
	 */
	public Client(
		String p_s_mailServer,
		String p_s_smtpServer,
		int p_i_mailServerPort,
		int p_i_smtpServerPort,
		String p_s_mailServerUser,
		String p_s_mailServerPassword,
		String p_s_smtpServerUser,
		String p_s_smtpServerPassword,
		boolean p_b_usePop3,
		boolean p_b_sslGet,
		boolean p_b_sslSend,
		String p_s_trustStorePath,
		String p_s_trustStorePassword,
		boolean p_b_useOAuth2
	) throws IllegalArgumentException, java.security.KeyStoreException, java.io.IOException, jakarta.mail.MessagingException {
		this.s_server = null;
		this.s_user = null;
		boolean b_mailStartTls = false;
		boolean b_smtpStartTls = false;
		boolean b_mailTls = false;
		boolean b_smtpTls = false;
		
		/* assume pop3 parameter flag */
		this.b_pop3 = p_b_usePop3;
		
		/* start with current root folder Folder.INBOX */
		this.o_currentFolder = new Folder(Folder.INBOX);
		
		/* check mail server parameter if there is no smtp server parameter */
		if ( (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_mailServer)) && (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_smtpServer)) ) {
			throw new IllegalArgumentException("Empty value for \"Mail-Server\"-parameter and \"SMTP-Server\"");
		}
		
		/* check user parameter for mail and smtp server */
		if ( (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_mailServerUser)) && (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_smtpServerUser)) ) {
			throw new IllegalArgumentException("Empty value for \"User\"-parameter and \"SMTP-User\"-parameter");
		}
		
		/* check password parameter for mail and smtp server */
		if ( (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_mailServerPassword)) && (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_smtpServerPassword)) ) {
			throw new IllegalArgumentException("Empty value for \"Password\"-parameter and \"SMTP-Password\"-parameter");
		}
		
		/* check min value for mail server parameter */
		if (p_i_mailServerPort < 1) {
			throw new IllegalArgumentException("Mail-Server Port must be at least '1', but was set to '" + p_i_mailServerPort + "'");
		}
		
		/* check max value for mail server parameter */
		if (p_i_mailServerPort > 65535) {
			throw new IllegalArgumentException("Mail-Server Port must be lower equal '65535', but was set to '" + p_i_mailServerPort + "'");
		}
		
		/* check min value for smtp server parameter */
		if (p_i_smtpServerPort < 1) {
			throw new IllegalArgumentException("SMTP-Server Port must be at least '1', but was set to '" + p_i_smtpServerPort + "'");
		}
		
		/* check max value for smtp server parameter */
		if (p_i_smtpServerPort > 65535) {
			throw new IllegalArgumentException("SMTP-Server Port must be lower equal '65535', but was set to '" + p_i_smtpServerPort + "'");
		}
		
		/* if there are truststore path and truststore password parameters available */
		if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_trustStorePath)) && (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_trustStorePassword)) ) {
													net.forestany.forestj.lib.Global.ilogConfig("read truststore file and load keystore with password parameter, to see if parameters are valid and working");
			
			/* read truststore file and load keystore with password parameter, to see if parameters are valid and working */
			try (java.io.FileInputStream o_fileInputStream = new java.io.FileInputStream(p_s_trustStorePath)) {
				java.security.KeyStore o_keystore = java.security.KeyStore.getInstance(java.security.KeyStore.getDefaultType());
				
														net.forestany.forestj.lib.Global.ilogConfig("load truststore file with given password");
				
				o_keystore.load(o_fileInputStream, p_s_trustStorePassword.toCharArray());
			} catch (java.security.KeyStoreException o_exc) {
				throw new java.security.KeyStoreException("Cannot use TrustStore[" + p_s_trustStorePath + "]. Maybe corrupted TrustStore-file or wrong password", o_exc);
			} catch (java.security.NoSuchAlgorithmException o_exc) {
				throw new java.security.KeyStoreException("Algorithm used to check the integrity of the TrustStore[" + p_s_trustStorePath + "] cannot be found", o_exc);
			} catch (java.security.cert.CertificateException o_exc) {
				throw new java.security.KeyStoreException("Any of the certificates in the TrustStore[" + p_s_trustStorePath + "] could not be loaded", o_exc);
			} catch (java.io.IOException o_exc) {
				throw new java.io.IOException("Could not read TrustStore file[" + p_s_trustStorePath + "]", o_exc);
			}
			
													net.forestany.forestj.lib.Global.ilogConfig("assign truststore properties[" + p_s_trustStorePath + "] to javax.next.ssl");
			
			/* assign truststore properties to javax.next.ssl */
			System.setProperty("javax.net.ssl.trustStore", p_s_trustStorePath);
			System.setProperty("javax.net.ssl.trustStorePassword", p_s_trustStorePassword);
		}
		
		/* check for starttls option for pop3/imap */
		if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_mailServer)) && (p_s_mailServer.toLowerCase().endsWith(";starttls")) ) {
			/* remove starttls option from pop3/imap host parameter */
			p_s_mailServer = p_s_mailServer.substring(0, p_s_mailServer.toLowerCase().lastIndexOf(";starttls"));
			b_mailStartTls = true;
			
													net.forestany.forestj.lib.Global.ilogConfig("found STARTTLS option to get mails");
		}
		
		/* check for starttls option for smtp */
		if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_smtpServer)) && (p_s_smtpServer.toLowerCase().endsWith(";starttls")) ) {
			/* remove starttls option from smtp host parameter */
			p_s_smtpServer = p_s_smtpServer.substring(0, p_s_smtpServer.toLowerCase().lastIndexOf(";starttls"));
			b_smtpStartTls = true;
			
													net.forestany.forestj.lib.Global.ilogConfig("found STARTTLS option to send mails");
		}
		
		/* check for tls option for pop3/imap, only if not already starttls option has been found */
		if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_mailServer)) && (p_s_mailServer.toLowerCase().endsWith(";tls")) && (!b_mailStartTls) ) {
			/* remove starttls option from pop3/imap host parameter */
			p_s_mailServer = p_s_mailServer.substring(0, p_s_mailServer.toLowerCase().lastIndexOf(";tls"));
			b_mailTls = true;
			
													net.forestany.forestj.lib.Global.ilogConfig("found TLS option to get mails");
		}
		
		/* check for tls option for smtp, only if not already starttls option has been found */
		if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_smtpServer)) && (p_s_smtpServer.toLowerCase().endsWith(";tls")) && (!b_smtpStartTls) ) {
			/* remove tls option from smtp host parameter */
			p_s_smtpServer = p_s_smtpServer.substring(0, p_s_smtpServer.toLowerCase().lastIndexOf(";tls"));
			b_smtpTls = true;
			
													net.forestany.forestj.lib.Global.ilogConfig("found TLS option to send mails");
		}
		
		/* prepare mail session variable to get mails */
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_mailServer)) {
													net.forestany.forestj.lib.Global.ilogConfig("prepare mail session variable to get mails");
			
			/* properties instance to get mail session for imap/pop3 */
			java.util.Properties o_propertiesGet = new java.util.Properties();
			
			/* use imap protocol by standard */
			String s_getProtocol = "imap";
			
			/* if pop3 flag is set, use pop3 protocol */
			if (this.b_pop3) {
				s_getProtocol = "pop3";
			}
			
			this.s_protocol = s_getProtocol;
			
													net.forestany.forestj.lib.Global.ilogConfig("use '" + this.s_protocol + "' protocol");
			
													net.forestany.forestj.lib.Global.ilogConfig("adopt connection values for '" + this.s_protocol + "' session, timeout 30 seconds, connection timeout 10 seconds");
			
			/* adopt connection values for session, timeout 30 seconds, connection timeout 10 seconds */
			o_propertiesGet.setProperty( "mail." + this.s_protocol + ".host", p_s_mailServer );
			o_propertiesGet.setProperty( "mail." + this.s_protocol + ".port", Integer.valueOf(p_i_mailServerPort).toString() );
			o_propertiesGet.setProperty( "mail." + this.s_protocol + ".user", p_s_mailServerUser );
			o_propertiesGet.setProperty( "mail." + this.s_protocol + ".password", p_s_mailServerPassword );
			o_propertiesGet.setProperty( "mail." + this.s_protocol + ".auth", "true" );
			o_propertiesGet.setProperty( "mail." + this.s_protocol + ".timeout", "30000" );
			o_propertiesGet.setProperty( "mail." + this.s_protocol + ".connectiontimeout", "10000" );
			
			if (p_b_sslGet) { /* set ssl properties if we want to retrieve mail data over ssl with tls 1.2 */
				if (b_mailStartTls) {
															net.forestany.forestj.lib.Global.ilogConfig("enable starttls for protocol " + this.s_protocol);
					
					o_propertiesGet.setProperty( "mail." + this.s_protocol + ".starttls.enable", "true" );
					o_propertiesGet.setProperty( "mail." + this.s_protocol + ".ssl.checkserveridentity", "false" );
				}
				
				if (b_mailTls) {
															net.forestany.forestj.lib.Global.ilogConfig("set ssl properties (socketFactory.class=javax.net.ssl.SSLSocketFactory) if we want to retrieve mail data over ssl with tls version of https.protocols property");
				
						o_propertiesGet.setProperty( "mail." + this.s_protocol + ".ssl.protocols", System.getProperty("https.protocols") );
						o_propertiesGet.setProperty( "mail." + this.s_protocol + ".socketFactory.class", "javax.net.ssl.SSLSocketFactory" );
						
						if (p_b_useOAuth2) {
																	net.forestany.forestj.lib.Global.ilogConfig("set properties for OAuth2 authentication over ssl, access token must be in password parameter");
							
							o_propertiesGet.setProperty("mail." + this.s_protocol + ".ssl.enable", "true");
							o_propertiesGet.setProperty("mail." + this.s_protocol + ".sasl.enable", "true");
							o_propertiesGet.setProperty("mail." + this.s_protocol + ".sasl.mechanisms", "XOAUTH2");
							o_propertiesGet.setProperty("mail." + this.s_protocol + ".auth.login.disable", "true");
							o_propertiesGet.setProperty("mail." + this.s_protocol + ".auth.plain.disable", "true");
						}
				}
				
				
			}
			
													net.forestany.forestj.lib.Global.ilogConfig("create authenticator object with user and password parameters");
			
			/* create authenticator object with user and password parameters */
			jakarta.mail.Authenticator o_getAuthenticator = new jakarta.mail.Authenticator() {
				@Override
				protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
					return new jakarta.mail.PasswordAuthentication( p_s_mailServerUser, p_s_mailServerPassword );
				}
			};
			
													net.forestany.forestj.lib.Global.ilogConfig("create mail session instance to get mails");
			
			/* create mail session instance to get mails */
			this.o_sessionGet = jakarta.mail.Session.getInstance(o_propertiesGet, o_getAuthenticator);
			
			/* use new session instance to connect to mail server and get messages from Folder.INBOX */
			try (jakarta.mail.Store o_store = this.o_sessionGet.getStore(this.s_protocol)) {
														net.forestany.forestj.lib.Global.ilogConfig("connect to mail server");
												        
				/* connect to mail server */
				o_store.connect();
				
														net.forestany.forestj.lib.Global.ilogConfig("create default root folder");
				
				/* create default root folder */
				Folder o_defaultFolder = new Folder(Folder.ROOT);
				
														net.forestany.forestj.lib.Global.ilogConfig("get any mail folder below root folder");
				
				/* get any mail folder below root folder */
				for (jakarta.mail.Folder o_folder : o_store.getDefaultFolder().list()) {
					o_defaultFolder.addChildren(o_folder.getName());
				}
				
														net.forestany.forestj.lib.Global.ilogConfig("look for Folder.INBOX folder and set it as current folder");
				
				/* look for Folder.INBOX folder and set it a s current folder */
				Folder o_foo = o_defaultFolder.getSubFolder(Folder.INBOX);
				this.o_currentFolder = o_foo;
				
				/* if we found Folder.INBOX folder and using imap protocol */
				if ( (o_foo != null) && (!this.b_pop3) ) {
															net.forestany.forestj.lib.Global.ilogConfig("we found Folder.INBOX folder and using imap protocol. get all sub folder of Folder.INBOX");
					
					/* get all sub folder of Folder.INBOX folder and add them as child items */
					for (jakarta.mail.Folder o_folder : o_store.getFolder(this.o_currentFolder.getFullPath()).list()) {
																net.forestany.forestj.lib.Global.ilogFine("add folder '" + o_folder.getName() + "' as sub folder to Folder.INBOX");
						
						this.o_currentFolder.addChildren(o_folder.getName());
					}
				}
			} catch (jakarta.mail.MessagingException o_exc) {
				this.o_sessionGet = null;
				throw new jakarta.mail.MessagingException("Cannot connect to mail server with protocol '" + this.s_protocol + "'", o_exc);
			}
		} else {
													net.forestany.forestj.lib.Global.ilogConfig("no session for getting mails in use");
			
			/* no session for getting mails in use */
			this.o_sessionGet = null;
		}
		
		/* prepare mail session variable to send mails */
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_smtpServer)) {
													net.forestany.forestj.lib.Global.ilogConfig("prepare mail session variable to send mails");
			
			/* properties instance to get mail session for smtp */
			java.util.Properties o_propertiesSend = new java.util.Properties();
			
			/* use smtp protocol by standard */
			String s_sendProtocol = "smtp";
			
													net.forestany.forestj.lib.Global.ilogConfig("use '" + s_sendProtocol + "' protocol");
			
													net.forestany.forestj.lib.Global.ilogConfig("adopt connection values for '" + s_sendProtocol + "' session, timeout 30 seconds, connection timeout 10 seconds");
			
			/* adopt connection values for session, timeout 30 seconds, connection timeout 10 seconds */
			o_propertiesSend.setProperty( "mail." + s_sendProtocol + ".host", p_s_smtpServer );
			o_propertiesSend.setProperty( "mail." + s_sendProtocol + ".port", Integer.valueOf(p_i_smtpServerPort).toString() );
			o_propertiesSend.setProperty( "mail." + s_sendProtocol + ".auth", "true" );
			o_propertiesSend.setProperty( "mail." + s_sendProtocol + ".timeout", "30000" );
			o_propertiesSend.setProperty( "mail." + s_sendProtocol + ".connectiontimeout", "10000" );
			
			if (p_b_sslSend) { /* set ssl properties if we want to send mail data over ssl with tls 1.2 */
				if (b_smtpStartTls) {
															net.forestany.forestj.lib.Global.ilogConfig("enable starttls for protocol smtp");
					
					o_propertiesSend.setProperty( "mail." + s_sendProtocol + ".starttls.enable", "true" );
					o_propertiesSend.setProperty( "mail." + s_sendProtocol + ".ssl.checkserveridentity", "false" );
				}
				
				if (b_smtpTls) {
				    										net.forestany.forestj.lib.Global.ilogConfig("set ssl properties (socketFactory.class=javax.net.ssl.SSLSocketFactory & socketFactory.fallback=false) if we want to send mail data over ssl with tls version of https.protocols property");
					
					o_propertiesSend.setProperty( "mail." + s_sendProtocol + ".ssl.protocols", System.getProperty("https.protocols") );
					o_propertiesSend.setProperty( "mail." + s_sendProtocol + ".socketFactory.class", "javax.net.ssl.SSLSocketFactory" );
					o_propertiesSend.setProperty( "mail." + s_sendProtocol + ".socketFactory.port", Integer.valueOf(p_i_smtpServerPort).toString() );
					o_propertiesSend.setProperty( "mail." + s_sendProtocol + ".socketFactory.fallback", "false" );
					
					if (p_b_useOAuth2) {
																	net.forestany.forestj.lib.Global.ilogConfig("set properties for OAuth2 authentication over ssl, access token must be in password parameter");

							o_propertiesSend.setProperty("mail." + this.s_protocol + ".ssl.enable", "true");
							o_propertiesSend.setProperty("mail." + this.s_protocol + ".sasl.enable", "true");
							o_propertiesSend.setProperty("mail." + this.s_protocol + ".sasl.mechanisms", "XOAUTH2");
							o_propertiesSend.setProperty("mail." + this.s_protocol + ".auth.login.disable", "true");
							o_propertiesSend.setProperty("mail." + this.s_protocol + ".auth.plain.disable", "true");
						}
				}
			}
			
													net.forestany.forestj.lib.Global.ilogConfig("create authenticator object with user and password parameters");
			
			/* create authenticator object with user and password parameters */
			jakarta.mail.Authenticator o_sendAuthenticator = new jakarta.mail.Authenticator() {
				@Override
				protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
					return new jakarta.mail.PasswordAuthentication( p_s_smtpServerUser, p_s_smtpServerPassword );
				}
			};
			
													net.forestany.forestj.lib.Global.ilogConfig("create mail session instance to send mails");
			
			/* create mail session instance to send mails */
			this.o_sessionSend = jakarta.mail.Session.getInstance(o_propertiesSend, o_sendAuthenticator);
			
			/* use new session instance to connect to smtp server */
			try (jakarta.mail.Transport o_transport = this.o_sessionSend.getTransport(s_sendProtocol)) {
														net.forestany.forestj.lib.Global.ilogConfig("connect to smtp server");
				
				/* connect to smtp server */
				o_transport.connect();
			} catch (jakarta.mail.MessagingException o_exc) {
				this.o_sessionSend = null;
				throw new jakarta.mail.MessagingException("Authentication with '" + s_sendProtocol + "' failed", o_exc);
			}
			
			/* store smtp server address and user mail address for later use */
			this.s_server = p_s_smtpServer;
			this.s_user = p_s_smtpServerUser;
		} else {
													net.forestany.forestj.lib.Global.ilogConfig("no session for sending mails in use");
			
			/* no session for sending mails in use */
			this.o_sessionSend = null;
		}
	}
	
	/**
	 * Send a mail message with smtp server
	 * 
	 * @param p_o_mailMessage						mail message object which holds all necessary information incl. html content and/or attachments (both optional)
	 * @throws IllegalArgumentException				smtp user is not set
	 * @throws NullPointerException					we have no session instance for sending mails
	 * @throws jakarta.mail.MessagingException		could not validate mail address, could not create or set mime content or mime body part objects, issue with sending mail message with smtp server
	 * @throws java.io.IOException					attachment file does not exist on local system
	 */
	public void sendMessage(Message p_o_mailMessage) throws IllegalArgumentException, NullPointerException, jakarta.mail.MessagingException, java.io.IOException {
												net.forestany.forestj.lib.Global.ilogFiner("check if we have a session instance for sending mails");
		
		/* check if we have a session instance for sending mails */
		if (this.o_sessionSend == null) {
			throw new NullPointerException("mail client has no settings to send mails. cannot send a message");
		}
		
		/* create javax message object with session instance for sending mails */
		jakarta.mail.Message o_message = new jakarta.mail.internet.MimeMessage(this.o_sessionSend);
		jakarta.mail.internet.InternetAddress o_mailAddress = null;
		
		if (p_o_mailMessage.getFrom() != null) { /* check if we have a session instance for sending mails */
													net.forestany.forestj.lib.Global.ilogFiner("check if we have a session instance for sending mails");
			
			try {
														net.forestany.forestj.lib.Global.ilogFiner("check mail address validity");
				
				/* check mail address validity */
				o_mailAddress = new jakarta.mail.internet.InternetAddress(p_o_mailMessage.getFrom());
			} catch (jakarta.mail.internet.AddressException o_exc) {
				throw new jakarta.mail.MessagingException("Invalid 'From' address", o_exc);
			}
		} else { /* auto fill from address from user and address value for smtp server */
													net.forestany.forestj.lib.Global.ilogFiner("auto fill from address from user and address value for smtp server");
			
													net.forestany.forestj.lib.Global.ilogFiner("check if we have a smtp user");
			
			/* check if we have a smtp user */
			if (net.forestany.forestj.lib.Helper.isStringEmpty(this.s_user)) {
				throw new IllegalArgumentException("SMTP-User is empty");
			}
			
			try {
														net.forestany.forestj.lib.Global.ilogFiner("check mail address validity");
				
				/* check mail address validity */
				if (this.s_user.contains("@")) {
					o_mailAddress = new jakarta.mail.internet.InternetAddress(this.s_user);
				} else {
					o_mailAddress = new jakarta.mail.internet.InternetAddress(this.s_user + "@" + this.s_server);
				}
			} catch (jakarta.mail.internet.AddressException o_exc) {
				throw new jakarta.mail.MessagingException("Invalid user address '" + this.s_user + "' or '" + this.s_user + "@" + this.s_server + "'", o_exc);
			}
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("set from address of javax message object");
		
		/* set from address of javax message object */
		o_message.setFrom(o_mailAddress);
		
												net.forestany.forestj.lib.Global.ilogFiner("add recipients to javax message object");
		
		/* add recipients to javax message object */
		if (p_o_mailMessage.getToList().size() > 0) {
			for (String s_to : p_o_mailMessage.getToList()) {
				o_message.addRecipient(jakarta.mail.Message.RecipientType.TO, new jakarta.mail.internet.InternetAddress(s_to));
			}
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("add cc to javax message object");
		
		/* add cc to javax message object */
		if (p_o_mailMessage.getCCList().size() > 0) {
			for (String s_cc : p_o_mailMessage.getCCList()) {
				o_message.addRecipient(jakarta.mail.Message.RecipientType.CC, new jakarta.mail.internet.InternetAddress(s_cc));
			}
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("add bcc to javax message object");
		
		/* add bcc to javax message object */
		if (p_o_mailMessage.getBCCList().size() > 0) {
			for (String s_bcc : p_o_mailMessage.getBCCList()) {
				o_message.addRecipient(jakarta.mail.Message.RecipientType.BCC, new jakarta.mail.internet.InternetAddress(s_bcc));
			}
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("add subject to javax message object");
		
		/* add subject to javax message object */
		o_message.setSubject(p_o_mailMessage.getSubject());
		
		/* create mime content variable */
		jakarta.mail.internet.MimeMultipart o_mimecontent = null;
		
	    /* if we have plain text content and html content */
    	if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_o_mailMessage.getText())) && (!net.forestany.forestj.lib.Helper.isStringEmpty(p_o_mailMessage.getHtml())) ) {
    												net.forestany.forestj.lib.Global.ilogFiner("we have plain text content and html content");
    		
    												net.forestany.forestj.lib.Global.ilogFiner("set mime content to 'alternative'");
    		
    		/* set mime content to 'alternative' */
    		o_mimecontent = new jakarta.mail.internet.MimeMultipart("alternative");
    		
    												net.forestany.forestj.lib.Global.ilogFiner("add plain text as mime body part");
    		
    		/* add plain text as mime body part */
    		jakarta.mail.internet.MimeBodyPart o_mimeBodyPart = new jakarta.mail.internet.MimeBodyPart();
    		o_mimeBodyPart.setContent(p_o_mailMessage.getText(), "text/plain");
    		o_mimecontent.addBodyPart(o_mimeBodyPart);
    		
    												net.forestany.forestj.lib.Global.ilogFiner("add html text as mime body part");
    		
    		/* add html text as mime body part */
    		o_mimeBodyPart = new jakarta.mail.internet.MimeBodyPart();
    		o_mimeBodyPart.setContent(p_o_mailMessage.getHtml(), "text/html");
    		o_mimecontent.addBodyPart(o_mimeBodyPart);
    		
    												net.forestany.forestj.lib.Global.ilogFiner("set javax mail message content with mime content");
    		
    		/* set javax mail message content with mime content */
    		o_message.setContent( o_mimecontent );
    	} else { /* we only have plain text content */
    												net.forestany.forestj.lib.Global.ilogFiner("we only have plain text content");
    		
    												net.forestany.forestj.lib.Global.ilogFiner("set javax mail message content with plain text");
    		
    		/* set javax mail message content with plain text */
    		o_message.setContent( p_o_mailMessage.getText(), "text/plain" );
    	}
	    
    	if ( (p_o_mailMessage.getAttachments() != null) && (p_o_mailMessage.getAttachments().size() > 0) ) {
    												net.forestany.forestj.lib.Global.ilogFiner("we have attachments for mail message, set mime content parent to 'mixed'");
    		
    		/* we have attachments for mail message, set mime content parent to 'mixed' */
    		jakarta.mail.internet.MimeMultipart o_mimecontentParent = new jakarta.mail.internet.MimeMultipart("mixed");
    		
    		if (o_mimecontent != null) { /* get mime content object plain text content and html content(optional) */
    													net.forestany.forestj.lib.Global.ilogFiner("get mime content object plain text content and html content(optional)");
    			
    													net.forestany.forestj.lib.Global.ilogFiner("add mime content to mime body part of mixed mime multipart");
    			
    			/* add mime content to mime body part of mixed mime multipart */
    			jakarta.mail.BodyPart o_mimeBodyPart = new jakarta.mail.internet.MimeBodyPart();
        		o_mimeBodyPart.setContent(o_mimecontent);
        		o_mimecontentParent.addBodyPart(o_mimeBodyPart);
        	} else { /* just get plain text mime content */
        												net.forestany.forestj.lib.Global.ilogFiner("just get plain text mime content");
        		
        												net.forestany.forestj.lib.Global.ilogFiner("add plain text content from before to mime body part of mixed mime multipart");
        		
        		/* add plain text content from before to mime body part of mixed mime multipart */
        		jakarta.mail.BodyPart o_mimeBodyPart = new jakarta.mail.internet.MimeBodyPart();
        		o_mimeBodyPart.setContent(p_o_mailMessage.getText(), "text/plain");
        		o_mimecontentParent.addBodyPart(o_mimeBodyPart);
        	}
    		
    												net.forestany.forestj.lib.Global.ilogFiner("iterate each attachment");
    		
    		/* iterate each attachment */
    		for (String s_filename : p_o_mailMessage.getAttachments()) {
    													net.forestany.forestj.lib.Global.ilogFinest("check if file '" + s_filename + "' really exists on local system");
    			
    			/* check if file really exists on local system */
    			if (!net.forestany.forestj.lib.io.File.exists(s_filename)) {
    				throw new java.io.IOException("File[" + s_filename + "] does not exist and cannot be attached to E-Mail-Message");
    			}
    			
    													net.forestany.forestj.lib.Global.ilogFinest("add body part with attachment content and file name");
    			
    			/* add body part with attachment content and file name */
				jakarta.mail.internet.MimeBodyPart o_bodyPart = new jakarta.mail.internet.MimeBodyPart();
				
				o_bodyPart.setFileName( new java.io.File(s_filename).getName() );
				o_bodyPart.attachFile(s_filename);
				
				o_mimecontentParent.addBodyPart( o_bodyPart );
			}
			
    												net.forestany.forestj.lib.Global.ilogFiner("overwrite javax mail message content with mixed mime multipart");
    		
    		/* overwrite javax mail message content with mixed mime multipart */
			o_message.setContent( o_mimecontentParent );
		}
    	
    											net.forestany.forestj.lib.Global.ilogFiner("send javax mail message");
    	
    	/* send javax mail message */
		jakarta.mail.Transport.send( o_message );
		
		/* current mail client has imap(s) settings, so we add message to sent folder; pop3(s) is not supported */
		if ( (this.o_sessionGet != null) && (!this.s_protocol.startsWith("pop3")) ) {
													net.forestany.forestj.lib.Global.ilogFiner("current mail client has imap(s) settings, so we add message to sent folder; pop3(s) is not supported");
			
													net.forestany.forestj.lib.Global.ilogFiner("set SEEN flag for message");
													
			o_message.setFlag(jakarta.mail.Flags.Flag.SEEN, true);
													
			/* use new session instance to connect to mail server and get messages from Folder.INBOX */
			try (jakarta.mail.Store o_store = this.o_sessionGet.getStore(this.s_protocol)) {
														net.forestany.forestj.lib.Global.ilogConfig("connect to mail server");
				
				/* connect to mail server */
				o_store.connect();
				
														net.forestany.forestj.lib.Global.ilogFiner("get sent folder below root folder");
				
				/* get sent folder below root folder */
				jakarta.mail.Folder o_folder = o_store.getFolder(Folder.SENT);
				
														net.forestany.forestj.lib.Global.ilogFine("reached sent folder, open it with read write access");
				
				/* reached sent folder, open it with read write access */
				o_folder.open(jakarta.mail.Folder.READ_WRITE);
				
														net.forestany.forestj.lib.Global.ilogFiner("append message to sent folder");
				
				/* append message to sent folder */
				o_folder.appendMessages(new jakarta.mail.Message[] {o_message});
				
														net.forestany.forestj.lib.Global.ilogFiner("close sent folder");
				
				/* close sent folder */
				o_folder.close(false);
			} catch (jakarta.mail.MessagingException o_exc) {
				this.o_sessionGet = null;
				throw new jakarta.mail.MessagingException("Cannot connect to mail server with protocol '" + this.s_protocol + "'", o_exc);
			}
		}
	}
	
	/**
	 * Convert a javax mail flag to string flag value
	 * @param p_o_flag					javax mail flag value
	 * @return String					part of net.forestany.forestj.lib.net.mail.Message.FLAGS
	 */
	private static String flagToString(jakarta.mail.Flags.Flag p_o_flag) {
		if (p_o_flag.equals(jakarta.mail.Flags.Flag.ANSWERED)) {
			return Message.FLAGS[0];
		} else if (p_o_flag.equals(jakarta.mail.Flags.Flag.DELETED)) {
			return Message.FLAGS[1];
		} else if (p_o_flag.equals(jakarta.mail.Flags.Flag.DRAFT)) {
			return Message.FLAGS[2];
		} else if (p_o_flag.equals(jakarta.mail.Flags.Flag.FLAGGED)) {
			return Message.FLAGS[3];
		} else if (p_o_flag.equals(jakarta.mail.Flags.Flag.RECENT)) {
			return Message.FLAGS[4];
		} else if (p_o_flag.equals(jakarta.mail.Flags.Flag.SEEN)) {
			return Message.FLAGS[5];
		} else if (p_o_flag.equals(jakarta.mail.Flags.Flag.USER)) {
			return Message.FLAGS[6];
		} else {
													net.forestany.forestj.lib.Global.ilogWarning("Could not convert javax mail flag '" + p_o_flag.toString() + "' - flag is unkown");
			
			return "UNKNOWN_FLAG";
		}
	}
	
	/**
	 * Converts a string flag value to a javax mail flag
	 * @param p_s_flag						string flag value - part of net.forestany.forestj.lib.net.mail.Message.FLAGS
	 * @return jakarta.mail.Flags.Flag
	 * @throws IllegalArgumentException		parameter flag value is an unknown flag
	 */
	private static jakarta.mail.Flags.Flag stringToFlag(String p_s_flag) throws IllegalArgumentException {
		if (p_s_flag.contentEquals(Message.FLAGS[0])) {
			return jakarta.mail.Flags.Flag.ANSWERED;
		} else if (p_s_flag.contentEquals(Message.FLAGS[1])) {
			return jakarta.mail.Flags.Flag.DELETED;
		} else if (p_s_flag.contentEquals(Message.FLAGS[2])) {
			return jakarta.mail.Flags.Flag.DRAFT;
		} else if (p_s_flag.contentEquals(Message.FLAGS[3])) {
			return jakarta.mail.Flags.Flag.FLAGGED;
		} else if (p_s_flag.contentEquals(Message.FLAGS[4])) {
			return jakarta.mail.Flags.Flag.RECENT;
		} else if (p_s_flag.contentEquals(Message.FLAGS[5])) {
			return jakarta.mail.Flags.Flag.SEEN;
		} else if (p_s_flag.contentEquals(Message.FLAGS[6])) {
			return jakarta.mail.Flags.Flag.USER;
		} else {
			throw new IllegalArgumentException("UNKNOWN_FLAG");
		}
	}
	
	/**
	 * Get all messages from current mail folder, set seen flag of all messages we are reading from current folder, download all attachments from all messages to message objects, can be stored later to local system, do not set delete flag for any message
	 * 
	 * @return list of mail message objects
	 * @throws IllegalArgumentException						current folder is Folder.ROOT where no messages are allowed
	 * @throws jakarta.mail.MessagingException				connection failed to mail or smtp server, wrong folder path, could not open folder with read write access, could not get messages, could not wrap message, issue set/unset a flag of a message, issue closing folder
	 * @throws java.io.UnsupportedEncodingException			the charset conversion failed by retrieving mail addresses, subject or text content
	 * @throws java.io.IOException							issue handling mime content or bodypart content, or could not retrieve attachment content to byte array output stream
	 */
	public java.util.List<Message> getMessages() throws IllegalArgumentException, jakarta.mail.MessagingException, java.io.UnsupportedEncodingException, java.io.IOException {
		return this.getMessages(false);
	}
	
	/**
	 * Get all messages from current mail folder, set seen flag of all messages we are reading from current folder, download all attachments from all messages to message objects, can be stored later to local system
	 * 
	 * @param p_b_setDeleted								true - set delete flag of all messages we are reading from current folder, false - do not set delete flag for any message
	 * @return list of mail message objects
	 * @throws IllegalArgumentException						current folder is Folder.ROOT where no messages are allowed
	 * @throws jakarta.mail.MessagingException				connection failed to mail or smtp server, wrong folder path, could not open folder with read write access, could not get messages, could not wrap message, issue set/unset a flag of a message, issue closing folder
	 * @throws java.io.UnsupportedEncodingException			the charset conversion failed by retrieving mail addresses, subject or text content
	 * @throws java.io.IOException							issue handling mime content or bodypart content, or could not retrieve attachment content to byte array output stream
	 */
	public java.util.List<Message> getMessages(boolean p_b_setDeleted) throws IllegalArgumentException, jakarta.mail.MessagingException, java.io.UnsupportedEncodingException, java.io.IOException {
		return this.getMessages(null, p_b_setDeleted, false);
	}
	
	/**
	 * Get all messages from current mail folder, set seen flag of all messages we are reading from current folder
	 * 
	 * @param p_b_setDeleted								true - set delete flag of all messages we are reading from current folder, false - do not set delete flag for any message
	 * @param p_b_ignoreAttachments							true - ignore attachments of all messages, false - download all attachments from all messages to message objects, can be stored later to local system
	 * @return list of mail message objects
	 * @throws IllegalArgumentException						current folder is Folder.ROOT where no messages are allowed
	 * @throws jakarta.mail.MessagingException				connection failed to mail or smtp server, wrong folder path, could not open folder with read write access, could not get messages, could not wrap message, issue set/unset a flag of a message, issue closing folder
	 * @throws java.io.UnsupportedEncodingException			the charset conversion failed by retrieving mail addresses, subject or text content
	 * @throws java.io.IOException							issue handling mime content or bodypart content, or could not retrieve attachment content to byte array output stream
	 */
	public java.util.List<Message> getMessages(boolean p_b_setDeleted, boolean p_b_ignoreAttachments) throws IllegalArgumentException, jakarta.mail.MessagingException, java.io.UnsupportedEncodingException, java.io.IOException {
		return this.getMessages(null, p_b_setDeleted, p_b_ignoreAttachments, true);
	}
	
	/**
	 * Get all messages from current mail folder
	 * 
	 * @param p_b_setDeleted								true - set delete flag of all messages we are reading from current folder, false - do not set delete flag for any message
	 * @param p_b_ignoreAttachments							true - ignore attachments of all messages, false - download all attachments from all messages to message objects, can be stored later to local system
	 * @param p_b_setSeen									true - set seen flag of all messages we are reading from current folder, false - unset seen flag if reading message auto set seen flag for message
	 * @return list of mail message objects
	 * @throws IllegalArgumentException						current folder is Folder.ROOT where no messages are allowed
	 * @throws jakarta.mail.MessagingException				connection failed to mail or smtp server, wrong folder path, could not open folder with read write access, could not get messages, could not wrap message, issue set/unset a flag of a message, issue closing folder
	 * @throws java.io.UnsupportedEncodingException			the charset conversion failed by retrieving mail addresses, subject or text content
	 * @throws java.io.IOException							issue handling mime content or bodypart content, or could not retrieve attachment content to byte array output stream*/
	public java.util.List<Message> getMessages(boolean p_b_setDeleted, boolean p_b_ignoreAttachments, boolean p_b_setSeen) throws IllegalArgumentException, jakarta.mail.MessagingException, java.io.UnsupportedEncodingException, java.io.IOException {
		return this.getMessages(null, p_b_setDeleted, p_b_ignoreAttachments, p_b_setSeen);
	}
	
	/**
	 * Get all messages from target mail folder, set seen flag of all messages we are reading from target folder
	 * 
	 * @param p_s_folderPath								target mail folder where we want to read all messages from
	 * @param p_b_setDeleted								true - set delete flag of all messages we are reading from target folder, false - do not set delete flag for any message
	 * @param p_b_ignoreAttachments							true - ignore attachments of all messages, false - download all attachments from all messages to message objects, can be stored later to local system
	 * @return list of mail message objects
	 * @throws IllegalArgumentException						current folder is Folder.ROOT where no messages are allowed
	 * @throws jakarta.mail.MessagingException				connection failed to mail or smtp server, wrong folder path, could not open folder with read write access, could not get messages, could not wrap message, issue set/unset a flag of a message, issue closing folder
	 * @throws java.io.UnsupportedEncodingException			the charset conversion failed by retrieving mail addresses, subject or text content
	 * @throws java.io.IOException							issue handling mime content or bodypart content, or could not retrieve attachment content to byte array output stream*/
	public java.util.List<Message> getMessages(String p_s_folderPath, boolean p_b_setDeleted, boolean p_b_ignoreAttachments) throws IllegalArgumentException, jakarta.mail.MessagingException, java.io.UnsupportedEncodingException, java.io.IOException {
		return this.getMessages(p_s_folderPath, p_b_setDeleted, p_b_ignoreAttachments, true);
	}
	
	/**
	 * Get all messages from target mail folder
	 * 
	 * @param p_s_folderPath								target mail folder where we want to read all messages from
	 * @param p_b_setDeleted								true - set delete flag of all messages we are reading from target folder, false - do not set delete flag for any message
	 * @param p_b_ignoreAttachments							true - ignore attachments of all messages, false - download all attachments from all messages to message objects, can be stored later to local system
	 * @param p_b_setSeen									true - set seen flag of all messages we are reading from target folder, false - unset seen flag if reading message auto set seen flag for message
	 * @return list of mail message objects
	 * @throws IllegalArgumentException						current folder is Folder.ROOT where no messages are allowed
	 * @throws jakarta.mail.MessagingException				connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, could not wrap message, issue set/unset a flag of a message, issue closing folder
	 * @throws java.io.UnsupportedEncodingException			the charset conversion failed by retrieving mail addresses, subject or text content
	 * @throws java.io.IOException							issue handling mime content or bodypart content, or could not retrieve attachment content to byte array output stream*/
	public java.util.List<Message> getMessages(String p_s_folderPath, boolean p_b_setDeleted, boolean p_b_ignoreAttachments, boolean p_b_setSeen) throws IllegalArgumentException, jakarta.mail.MessagingException, java.io.UnsupportedEncodingException, java.io.IOException {
		/* check if mail client is set for retrieving mails */
		if (this.o_sessionGet == null) {
			throw new IllegalStateException("mail client has no settings to retrieve mails from server");
		}
		
		/* pop3 is not supported with folder path parameter */
		if ( (this.s_protocol.startsWith("pop3")) && (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_folderPath)) ) {
			throw new IllegalArgumentException("POP3(s) protocol is not supported with folder path parameter");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if current folder is not Folder.ROOT");
		
		/* check if current folder is not Folder.ROOT */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(this.o_currentFolder.getName())) {
			throw new IllegalArgumentException("Current folder is default folder, please specifiy a sub folder like 'inbox' or 'trash'");
		}
		
		/* list of mail message objects */
		java.util.List<Message> a_return = new java.util.ArrayList<Message>();
		
		/* use session instance to connect to mail server */
		try (jakarta.mail.Store o_store = this.o_sessionGet.getStore(this.s_protocol)) {
													net.forestany.forestj.lib.Global.ilogFine("connect to mail server");
    
			/* connect to mail server */
			o_store.connect();
			
													net.forestany.forestj.lib.Global.ilogFine("get default folder");
    
			/* get default folder */
			jakarta.mail.Folder o_folder = o_store.getDefaultFolder();
			
													net.forestany.forestj.lib.Global.ilogFine("get full path to current folder");
			
			/* get full path to current folder */
			String s_folderPath = this.o_currentFolder.getFullPath();
			
			/* if we have a target folder path, overwrite variable and not stay in current folder */
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_folderPath)) {
														net.forestany.forestj.lib.Global.ilogFine("target folder path set, overwrite variable and not stay in current folder");
				
				s_folderPath = p_s_folderPath;
			}
			
			try {
														net.forestany.forestj.lib.Global.ilogFine("iterate folder path");
				
				/* iterate folder path */
				for (String s_folder : s_folderPath.split("/")) {
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_folder)) {
																net.forestany.forestj.lib.Global.ilogFiner("enter folder '" + s_folder + "'");
						
						/* enter folder */
						o_folder = o_folder.getFolder(s_folder);
					}
				}
				
														net.forestany.forestj.lib.Global.ilogFine("reached target folder, open it with read write access");
				
				/* reached target folder, open it with read write access */
				o_folder.open(jakarta.mail.Folder.READ_WRITE);
			} catch (jakarta.mail.MessagingException o_exc) {
				throw new jakarta.mail.MessagingException("Wrong folder path '" + s_folderPath + "' or could not open folder with read write access", o_exc);
			}
			
													net.forestany.forestj.lib.Global.ilogFine("iterate each javax mail message in target folder");
			
			/* iterate each javax mail message in target folder */
			for (jakarta.mail.Message o_message : o_folder.getMessages()) {
														net.forestany.forestj.lib.Global.ilogFiner("check if message already has been seen");
				
				/* check if message already has been seen */
				boolean b_seen = o_message.isSet(jakarta.mail.Flags.Flag.SEEN);
				
														net.forestany.forestj.lib.Global.ilogFiner("add message to return list with wrap method");
				
				/* add message to return list with wrap method */
				a_return.add(this.wrapMessage(o_message, p_b_ignoreAttachments));
				
				/* set deleted flag if parameter is set */
				if (p_b_setDeleted) {
															net.forestany.forestj.lib.Global.ilogFiner("set deleted flag if parameter is set");
					
					o_message.setFlag(jakarta.mail.Flags.Flag.DELETED, true);
				}
				
				if (p_b_setSeen) { /* set seen flag if parameter is set */
															net.forestany.forestj.lib.Global.ilogFiner("set seen flag if parameter is set");
					
					o_message.setFlag(jakarta.mail.Flags.Flag.SEEN, true);
				} else {
					if (!b_seen) { /* unset seen flag if reading message auto set seen flag for message */
																net.forestany.forestj.lib.Global.ilogFiner("unset seen flag if reading message auto set seen flag for message");
						
						o_message.setFlag(jakarta.mail.Flags.Flag.SEEN, false);
					}
				}
			}
			
			/* close target folder, expunge all deleted messages */
			o_folder.close(p_b_setDeleted);
		}
		
		/* return list of mail message objects */
		return a_return;
	}
	
	/**
	 * Wrap javax mail message to own mail message object with all necessary information
	 * 
	 * @param p_o_message								javax mail message object where we retrieve all necessary information
	 * @param p_b_ignoreAttachments						true - ignore attachments of mail message, false - download all attachments of mail message, can be stored later to local system
	 * @return net.forestany.forestj.lib.net.mail.Message
	 * @throws IllegalArgumentException					parameters have invalid value or are empty
	 * @throws jakarta.mail.MessagingException			issue retrieving information from javax mail message parameter
	 * @throws java.io.UnsupportedEncodingException		the charset conversion failed by retrieving mail addresses, subject or text content
	 * @throws java.io.IOException						issue handling mime content or bodypart content, or could not retrieve attachment content to byte array output stream
	 */
	private Message wrapMessage(jakarta.mail.Message p_o_message, boolean p_b_ignoreAttachments) throws IllegalArgumentException, jakarta.mail.MessagingException, java.io.UnsupportedEncodingException, java.io.IOException {
												net.forestany.forestj.lib.Global.ilogFiner("create mail message object with some standard settings");
		
		/* create mail message object with some standard settings */
		Message o_mailMessage = new Message("myself@localhost.com", "No subject available", "No plain text message available");
		
		/* temporary string list */
		java.util.List<String> a_foo = new java.util.ArrayList<String>();
		
												net.forestany.forestj.lib.Global.ilogFiner("get all messages from 'from' property");
		
		/* get all messages from 'from' property */
		if ( (p_o_message.getFrom() != null) && (p_o_message.getFrom().length > 0) ) {
			for (jakarta.mail.Address o_address : p_o_message.getFrom()) {
				a_foo.add(jakarta.mail.internet.MimeUtility.decodeText(o_address.toString()));
			}
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("adopt 'from' addresses to mail message object");
		
		/* adopt 'from' addresses to mail message object */
		o_mailMessage.setFromList(a_foo);
		a_foo.clear();
		
												net.forestany.forestj.lib.Global.ilogFiner("get all messages from 'to' property");
		
		/* get all messages from 'to' property */
		if ( (p_o_message.getRecipients(jakarta.mail.Message.RecipientType.TO) != null) && (p_o_message.getRecipients(jakarta.mail.Message.RecipientType.TO).length > 0) ) {
			for (jakarta.mail.Address o_address : p_o_message.getRecipients(jakarta.mail.Message.RecipientType.TO)) {
				a_foo.add(jakarta.mail.internet.MimeUtility.decodeText(o_address.toString()));
			}
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("adopt 'to' addresses to mail message object");
		
		/* adopt 'to' addresses to mail message object */
		o_mailMessage.setToList(a_foo);
		a_foo.clear();
		
												net.forestany.forestj.lib.Global.ilogFiner("get all messages from 'cc' property");
		
		/* get all messages from 'cc' property */
		if ( (p_o_message.getRecipients(jakarta.mail.Message.RecipientType.CC) != null) && (p_o_message.getRecipients(jakarta.mail.Message.RecipientType.CC).length > 0) ) {
			for (jakarta.mail.Address o_address : p_o_message.getRecipients(jakarta.mail.Message.RecipientType.CC)) {
				a_foo.add(jakarta.mail.internet.MimeUtility.decodeText(o_address.toString()));
			}
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("adopt 'cc' addresses to mail message object");
		
		/* adopt 'cc' addresses to mail message object */
		o_mailMessage.setCCList(a_foo);
		a_foo.clear();
		
												net.forestany.forestj.lib.Global.ilogFiner("get all messages from 'bcc' property");
		
		/* get all messages from 'bcc' property */
		if ( (p_o_message.getRecipients(jakarta.mail.Message.RecipientType.BCC) != null) && (p_o_message.getRecipients(jakarta.mail.Message.RecipientType.BCC).length > 0) ) {
			for (jakarta.mail.Address o_address : p_o_message.getRecipients(jakarta.mail.Message.RecipientType.BCC)) {
				a_foo.add(jakarta.mail.internet.MimeUtility.decodeText(o_address.toString()));
			}
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("adopt 'bcc' addresses to mail message object");
		
		/* adopt 'bcc' addresses to mail message object */
		o_mailMessage.setBCCList(a_foo);
		a_foo.clear();
		
												net.forestany.forestj.lib.Global.ilogFiner("get mail message subject, send time, content time and other details");
		
		/* get mail message subject, send time, content time and other details */
		o_mailMessage.setSubject(jakarta.mail.internet.MimeUtility.decodeText(p_o_message.getSubject()));
		o_mailMessage.setSend(p_o_message.getSentDate());
		o_mailMessage.setReceived(p_o_message.getReceivedDate());
		o_mailMessage.setContentType(p_o_message.getContentType().replace("\r\n", "").replace("\n", ""));
		o_mailMessage.setExpunged(p_o_message.isExpunged());
		o_mailMessage.setDescription(p_o_message.getDescription());
		o_mailMessage.setDisposition(p_o_message.getDisposition());
		o_mailMessage.setSize(p_o_message.getSize());
		
												net.forestany.forestj.lib.Global.ilogFiner("create list of all possible mail message flags");
		
		/* create list of all possible mail message flags */
		java.util.List<jakarta.mail.Flags.Flag> a_flags = new java.util.ArrayList<jakarta.mail.Flags.Flag>();
		
		/* fill list with valid flag values */
		for (String s_foo : Message.FLAGS) {
			a_flags.add(Client.stringToFlag(s_foo));
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("check if mail message parameter has active flag set");
		
		/* check if mail message parameter has active flag set */
		for (jakarta.mail.Flags.Flag o_flag : a_flags) {
			if (p_o_message.isSet(o_flag)) {
														net.forestany.forestj.lib.Global.ilogFinest("add flag '" + o_flag.toString() + "' to mail message");
				
				/* add flag to mail message */
				o_mailMessage.addFlag(flagToString(o_flag));
			}
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("get header information");
		
		/* get header information */
		java.util.Enumeration<?> a_enumeration = p_o_message.getAllHeaders();
		
												net.forestany.forestj.lib.Global.ilogFiner("iterate all header elements");
		
		/* iterate all header elements */
	    while (a_enumeration.hasMoreElements()) {
		    jakarta.mail.Header o_header = (jakarta.mail.Header)a_enumeration.nextElement();
		 
		    										net.forestany.forestj.lib.Global.ilogFiner("add header line to mail message");
		    
		    /* add header line to mail message */
		    o_mailMessage.addHeaderLine(o_header.getName() + ": " + o_header.getValue());
		    
		    /* set message id property if it is contained in header information */
		    if (o_header.getName().toLowerCase().contentEquals("message-id")) {
		    											net.forestany.forestj.lib.Global.ilogFiner("set message id property if it is contained in header information");
		    	
		    	o_mailMessage.setMessageId(o_header.getValue());
		    }
		}
		
		if (p_o_message.isMimeType("text/*")) { /* javax mail message parameter has only plain text as content */
													net.forestany.forestj.lib.Global.ilogFiner("javax mail message parameter has only plain text as content");
			
													net.forestany.forestj.lib.Global.ilogFiner("retrieve plain text content");
			
			/* retrieve plain text content */
			o_mailMessage.setText(jakarta.mail.internet.MimeUtility.decodeText(p_o_message.getContent().toString()));
		} else if ( p_o_message.isMimeType( "multipart/*" ) ) { /* javax mail message parameter is of type mime multipart */
													net.forestany.forestj.lib.Global.ilogFiner("javax mail message parameter is of type mime multipart");
			
													net.forestany.forestj.lib.Global.ilogFiner("cast javax mail message content as mime multipart");
			
			/* cast javax mail message content as mime multipart */
			jakarta.mail.Multipart o_multiPart = (jakarta.mail.Multipart)p_o_message.getContent();
			
			/* mime multipart has plain text and a html part */
			if ( (o_multiPart.getCount() == 2) && (o_multiPart.getBodyPart(0).isMimeType("text/*")) && (o_multiPart.getBodyPart(1).isMimeType("text/html")) ) {
														net.forestany.forestj.lib.Global.ilogFiner("mime multipart has plain text and a html part");
				
														net.forestany.forestj.lib.Global.ilogFiner("retrieve plain text content");
				
				/* retrieve plain text content */
				o_mailMessage.setText(jakarta.mail.internet.MimeUtility.decodeText(o_multiPart.getBodyPart(0).getContent().toString()));
				
														net.forestany.forestj.lib.Global.ilogFiner("retrieve html content");
				
				/* retrieve html content */
				o_mailMessage.setHtml(jakarta.mail.internet.MimeUtility.decodeText(o_multiPart.getBodyPart(1).getContent().toString()));
			} else {
														net.forestany.forestj.lib.Global.ilogFiner("iterate each mime multipart bodypart");
				
				/* iterate each mime multipart bodypart */
				for (int i = 0; i < o_multiPart.getCount(); i++) {
					if ( (i == 0) && (o_multiPart.getCount() > 1) ) { /* first bodypart and more to come */
																net.forestany.forestj.lib.Global.ilogFinest("first bodypart and more to come");
						
						if (o_multiPart.getBodyPart(i).isMimeType("multipart/*")) { /* bodypart is of type mime multipart again */
																	net.forestany.forestj.lib.Global.ilogFinest("bodypart is of type mime multipart again");
							
																	net.forestany.forestj.lib.Global.ilogFinest("cast bodypart content as mime multipart");
							
							/* cast bodypart content as mime multipart */
							jakarta.mail.Multipart o_multiPartRecursive = (jakarta.mail.Multipart)o_multiPart.getBodyPart(i).getContent();
							
							/* bodypart has plain text and a html part */
							if ( (o_multiPartRecursive.getCount() == 2) && (o_multiPartRecursive.getBodyPart(0).isMimeType("text/*")) && (o_multiPartRecursive.getBodyPart(1).isMimeType("text/html")) ) {
																		net.forestany.forestj.lib.Global.ilogFinest("bodypart has plain text and a html part");
								
																		net.forestany.forestj.lib.Global.ilogFinest("retrieve plain text content");
								
								/* retrieve plain text content */
								o_mailMessage.setText(jakarta.mail.internet.MimeUtility.decodeText(o_multiPartRecursive.getBodyPart(0).getContent().toString()));
								
																		net.forestany.forestj.lib.Global.ilogFinest("retrieve html content");
								
								/* retrieve html content */
								o_mailMessage.setHtml(jakarta.mail.internet.MimeUtility.decodeText(o_multiPartRecursive.getBodyPart(1).getContent().toString()));
							} else {
																		net.forestany.forestj.lib.Global.ilogFinest("retrieve content as plain text");
								
								/* retrieve content as plain text */
								o_mailMessage.setText(jakarta.mail.internet.MimeUtility.decodeText(o_multiPartRecursive.getBodyPart(0).getContent().toString()));
							}
						} else if (o_multiPart.getBodyPart(i).isMimeType("text/*")) { /* bodypart is of type plain text */
																	net.forestany.forestj.lib.Global.ilogFinest("bodypart is of type plain text");
							
																	net.forestany.forestj.lib.Global.ilogFinest("retrieve plain text content from body part");
							
							/* retrieve plain text content from body part */
							o_mailMessage.setText(jakarta.mail.internet.MimeUtility.decodeText(o_multiPart.getBodyPart(i).getContent().toString()));
						}
					} else { /* now we iterate a bodyparts which contains an attachment */
																net.forestany.forestj.lib.Global.ilogFinest("now we iterate a bodypart which contains an attachment");
						
																net.forestany.forestj.lib.Global.ilogFinest("get disposition information from bodypart ");
						
						/* get disposition information from bodypart */
						String s_disposition = o_multiPart.getBodyPart(i).getDisposition();
						
						/* disposition value is not set or equal to jakarta.mail.Part.ATTACHMENT */
						if ( ( (s_disposition == null) || (s_disposition.equalsIgnoreCase(jakarta.mail.Part.ATTACHMENT)) ) ) {
																	net.forestany.forestj.lib.Global.ilogFinest("disposition value is not set or equal to jakarta.mail.Part.ATTACHMENT");
							
																	net.forestany.forestj.lib.Global.ilogFinest("add attachment filename and content type to mail message object");
							
							/* add attachment filename and content type to mail message object */
							o_mailMessage.addAttachment(o_multiPart.getBodyPart(i).getFileName() + Message.FIlENAMESEPARATOR + o_multiPart.getBodyPart(i).getContentType().replace("\r\n", "").replace("\n", ""));
							
							/* download attachment content */
							if (!p_b_ignoreAttachments) {
																		net.forestany.forestj.lib.Global.ilogFinest("download attachment content #" + i);
								
								try (java.io.InputStream o_inputStream = o_multiPart.getBodyPart(i).getInputStream()) {
																			net.forestany.forestj.lib.Global.ilogFinest("copy attachment content to byte array");
									
									/* copy attachment content to byte array */
									byte[] a_attachmentContent = this.receiveBytes(o_inputStream, 8192, o_multiPart.getBodyPart(i).getSize());
																			
					                										net.forestany.forestj.lib.Global.ilogFinest("add byte array to mail message object");
									
									/* add byte array to mail message object */
					                o_mailMessage.addAttachmentContent(a_attachmentContent);
				                } finally {
				                											net.forestany.forestj.lib.Global.ilogFinest("close attachment input stream");
				                	
				                	/* close attachment input stream */
				                	o_multiPart.getBodyPart(i).getInputStream().close();
					            }
							}
			            } else {
			            	/* invalid disposition value */
			            	throw new IllegalArgumentException("Unknown MIME Multipart; dipsosition: " + s_disposition);
			            }
					}
				}
			}
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("return mail message object");
		
		/* return mail message object */
		return o_mailMessage;
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
		
												net.forestany.forestj.lib.Global.ilogFinest("Iterate '" + i_cycles + "' cycles to receive '" + i_amountData + "' bytes with '"  + p_i_bufferLength + "' bytes buffer");
			
		/* iterate cycle to receive bytes with buffer */
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
	 * Get amount of all messages from current mail folder
	 * 
	 * @return amount of messages of current folder
	 * @throws IllegalArgumentException						current folder is Folder.ROOT where no messages are allowed
	 * @throws jakarta.mail.MessagingException				connection failed to mail server, wrong folder path, could not open folder with read access, could not get messages, issue reading a flag of a message, issue closing folder
	 */
	public int getMessagesAmount() throws IllegalArgumentException, jakarta.mail.MessagingException {
		return this.getMessagesAmount(null, false);
	}
	
	/**
	 * Get amount of messages from current mail folder
	 * 
	 * @param p_b_unseenOnly								true - just get amount of unseen messages, false - get amount of all messages
	 * @return amount of messages of current folder
	 * @throws IllegalArgumentException						current folder is Folder.ROOT where no messages are allowed
	 * @throws jakarta.mail.MessagingException				connection failed to mail server, wrong folder path, could not open folder with read access, could not get messages, issue reading a flag of a message, issue closing folder
	 */
	public int getMessagesAmount(boolean p_b_unseenOnly) throws IllegalArgumentException, jakarta.mail.MessagingException {
		return this.getMessagesAmount(null, p_b_unseenOnly);
	}
	
	/**
	 * Get amount of all messages from target mail folder
	 * 
	 * @param p_s_folderPath								target mail folder
	 * @return amount of messages of target folder
	 * @throws IllegalArgumentException						current folder is Folder.ROOT where no messages are allowed
	 * @throws jakarta.mail.MessagingException				connection failed to mail server, wrong folder path, could not open folder with read access, could not get messages, issue reading a flag of a message, issue closing folder
	 */
	public int getMessagesAmount(String p_s_folderPath) throws IllegalArgumentException, jakarta.mail.MessagingException {
		return this.getMessagesAmount(p_s_folderPath, false);
	}
	
	/**
	 * Get amount of messages from target mail folder
	 * 
	 * @param p_s_folderPath								target mail folder
	 * @param p_b_unseenOnly								true - just get amount of unseen messages, false - get amount of all messages
	 * @return amount of messages of target folder
	 * @throws IllegalArgumentException						current folder is Folder.ROOT where no messages are allowed
	 * @throws jakarta.mail.MessagingException				connection failed to mail server, wrong folder path, could not open folder with read access, could not get messages, issue reading a flag of a message, issue closing folder
	 */
	public int getMessagesAmount(String p_s_folderPath, boolean p_b_unseenOnly) throws IllegalArgumentException, jakarta.mail.MessagingException {
		int i_return = -1;
		
		/* check if mail client is set for retrieving mails */
		if (this.o_sessionGet == null) {
			throw new IllegalStateException("mail client has no settings to retrieve mails from server");
		}
		
		/* pop3 is only supported with inbox folder path */
		if (this.s_protocol.startsWith("pop3")) {
													net.forestany.forestj.lib.Global.ilogFine("set standard folder path to Folder.INBOX for POP3(s)");
			
			/* set standard folder path to Folder.INBOX for POP3(s) */
			p_s_folderPath = Folder.INBOX;
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if current folder is not Folder.ROOT");
		
		/* check if current folder is not Folder.ROOT */
		if ( (net.forestany.forestj.lib.Helper.isStringEmpty(this.o_currentFolder.getName())) && (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_folderPath)) ) {
			throw new IllegalArgumentException("Current folder is default folder, please specifiy a sub folder like 'inbox' or 'trash'");
		}
		
		/* use session instance to connect to mail server */
		try (jakarta.mail.Store o_store = this.o_sessionGet.getStore(this.s_protocol)) {
													net.forestany.forestj.lib.Global.ilogFine("connect to mail server");
						
			/* connect to mail server */
			o_store.connect();
			
													net.forestany.forestj.lib.Global.ilogFine("get default folder");
    
			/* get default folder */
			jakarta.mail.Folder o_folder = o_store.getDefaultFolder();
			
													net.forestany.forestj.lib.Global.ilogFine("get full path to current folder");
			
			/* get full path to current folder */
			String s_folderPath = this.o_currentFolder.getFullPath();
			
			/* if we have a target folder path, overwrite variable and not stay in current folder */
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_folderPath)) {
														net.forestany.forestj.lib.Global.ilogFine("target folder path set, overwrite variable and not stay in current folder");
				
				s_folderPath = p_s_folderPath;
			}
			
			try {
				if (net.forestany.forestj.lib.Helper.isStringEmpty(s_folderPath)) {
															net.forestany.forestj.lib.Global.ilogFine("folder path is null or empty so we return 0");
					
					return 0;
				}
				
														net.forestany.forestj.lib.Global.ilogFine("iterate folder path");
			
				/* iterate folder path */
				for (String s_folder : s_folderPath.split("/")) {
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_folder)) {
						o_folder = o_folder.getFolder(s_folder);
					}
				}
				
														net.forestany.forestj.lib.Global.ilogFine("reached target folder, open it with read access");
				
				/* reached target folder, open it with read access */
				o_folder.open(jakarta.mail.Folder.READ_ONLY);
			} catch (jakarta.mail.MessagingException o_exc) {
				throw new jakarta.mail.MessagingException("Wrong folder path '" + s_folderPath + "' or could not open folder with read access", o_exc);
			}
			
			/* if we want to get amount of unseen messages we must check seen flag */
			if (p_b_unseenOnly) {
				/* not every POP3(s) server support display between seen and unseen mails */
				if (this.s_protocol.startsWith("pop3")) {
															net.forestany.forestj.lib.Global.ilogWarning("not every POP3(s) server support display between seen and unseen mails");	
				}
				
														net.forestany.forestj.lib.Global.ilogFine("if we want to get amount of unseen messages we must check seen flag");
				
				i_return = 0;
				
														net.forestany.forestj.lib.Global.ilogFine("iterate each message in target folder");
				
				/* iterate each message in target folder */
				for (jakarta.mail.Message o_message : o_folder.getMessages()) {
					/* if not seen flag is set, increment return value */
					if (!o_message.isSet(jakarta.mail.Flags.Flag.SEEN)) {
						i_return++;
					}
				}
			} else {
														net.forestany.forestj.lib.Global.ilogFine("get message amount of target folder");
				
				/* get message amount of target folder */
				i_return = o_folder.getMessages().length;
			}
			
			/* close target folder */
			o_folder.close(false);
		}
		
		return i_return;
	}
	
	/**
	 * Get message from current mail folder with unique message id, set seen flag to message(s) matching message id, download all attachments from message, can be stored later to local system
	 * pop3 protocol is not supported
	 * 
	 * @param p_s_messageId									unique identifier for mail message
	 * @return mail message object
	 * @throws IllegalArgumentException						current folder is Folder.ROOT where no messages are allowed, could not find message with id
	 * @throws jakarta.mail.MessagingException				connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, could not wrap message, issue set/unset a flag of a message, issue closing folder
	 * @throws java.io.UnsupportedEncodingException			the charset conversion failed by retrieving mail addresses, subject or text content
	 * @throws java.io.IOException							issue handling mime content or bodypart content, or could not retrieve attachment content to byte array output stream
	 */
	public Message getMessageById(String p_s_messageId) throws IllegalArgumentException, jakarta.mail.MessagingException, java.io.UnsupportedEncodingException, java.io.IOException {
		return this.getMessageById(p_s_messageId, false);
	}
	
	/**
	 * Get message from current mail folder with unique message id, set seen flag to message(s) matching message id
	 * pop3 protocol is not supported
	 * 
	 * @param p_s_messageId									unique identifier for mail message
	 * @param p_b_ignoreAttachments							true - ignore attachments of message, false - download all attachments from message, can be stored later to local system
	 * @return mail message object
	 * @throws IllegalArgumentException						current folder is Folder.ROOT where no messages are allowed, could not find message with id
	 * @throws jakarta.mail.MessagingException				connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, could not wrap message, issue set/unset a flag of a message, issue closing folder
	 * @throws java.io.UnsupportedEncodingException			the charset conversion failed by retrieving mail addresses, subject or text content
	 * @throws java.io.IOException							issue handling mime content or bodypart content, or could not retrieve attachment content to byte array output stream
	 */
	public Message getMessageById(String p_s_messageId, boolean p_b_ignoreAttachments) throws IllegalArgumentException, jakarta.mail.MessagingException, java.io.UnsupportedEncodingException, java.io.IOException {
		return this.getMessageById(null, p_s_messageId, p_b_ignoreAttachments, true);
	}
	
	/**
	 * Get message from target mail folder with unique message id
	 * pop3 protocol is not supported
	 * 
	 * @param p_s_folderPath								target mail folder where we want to read all messages from
	 * @param p_s_messageId									unique identifier for mail message
	 * @param p_b_ignoreAttachments							true - ignore attachments of message, false - download all attachments from message, can be stored later to local system
	 * @param p_b_setSeen									true - set seen flag to message(s) matching message id, false - unset seen flag if reading message auto set seen flag for message
	 * @return mail message object
	 * @throws IllegalArgumentException						current folder is Folder.ROOT where no messages are allowed, could not find message with id
	 * @throws jakarta.mail.MessagingException				connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, could not wrap message, issue set/unset a flag of a message, issue closing folder
	 * @throws java.io.UnsupportedEncodingException			the charset conversion failed by retrieving mail addresses, subject or text content
	 * @throws java.io.IOException							issue handling mime content or bodypart content, or could not retrieve attachment content to byte array output stream
	 */
	public Message getMessageById(String p_s_folderPath, String p_s_messageId, boolean p_b_ignoreAttachments, boolean p_b_setSeen) throws IllegalArgumentException, jakarta.mail.MessagingException, java.io.UnsupportedEncodingException, java.io.IOException {
		/* return value */
		Message o_mailMessage = null;
		
		/* pop3 is not supported */
		if (this.s_protocol.startsWith("pop3")) {
			throw new IllegalArgumentException("POP3(s) protocol is not supported");
		}
		
		/* check if mail client is set for retrieving mails */
		if (this.o_sessionGet == null) {
			throw new IllegalStateException("mail client has no settings to retrieve mails from server");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if current folder is not Folder.ROOT");
		
		/* check if current folder is not Folder.ROOT */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(this.o_currentFolder.getName())) {
			throw new IllegalArgumentException("Current folder is default folder, please specifiy a sub folder like 'inbox' or 'trash'");
		}
		
		/* check message id parameter */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_messageId)) {
			throw new IllegalArgumentException("Empty message id parameter");
		}
		
		/* use session instance to connect to mail server */
		try (jakarta.mail.Store o_store = this.o_sessionGet.getStore(this.s_protocol)) {
													net.forestany.forestj.lib.Global.ilogFine("connect to mail server");
						
			/* connect to mail server */
			o_store.connect();
			
													net.forestany.forestj.lib.Global.ilogFine("get default folder");
    
			/* get default folder */
			jakarta.mail.Folder o_folder = o_store.getDefaultFolder();
			
													net.forestany.forestj.lib.Global.ilogFine("get full path to current folder");
			
			/* get full path to current folder */
			String s_folderPath = this.o_currentFolder.getFullPath();
			
			/* if we have a target folder path, overwrite variable and not stay in current folder */
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_folderPath)) {
														net.forestany.forestj.lib.Global.ilogFine("target folder path set, overwrite variable and not stay in current folder");
				
				s_folderPath = p_s_folderPath;
			}
			
			try {
														net.forestany.forestj.lib.Global.ilogFine("iterate folder path");
			
				/* iterate folder path */
				for (String s_folder : s_folderPath.split("/")) {
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_folder)) {
						o_folder = o_folder.getFolder(s_folder);
					}
				}
				
														net.forestany.forestj.lib.Global.ilogFine("reached target folder, open it with read write access");
				
				/* reached target folder, open it with read write access */
				o_folder.open(jakarta.mail.Folder.READ_WRITE);
			} catch (jakarta.mail.MessagingException o_exc) {
				throw new jakarta.mail.MessagingException("Wrong folder path '" + s_folderPath + "' or could not open folder with read write access", o_exc);
			}
			
													net.forestany.forestj.lib.Global.ilogFiner("create search term with message id parameter");
			
			/* create search term with message id parameter */
			jakarta.mail.search.SearchTerm o_searchTerm = new jakarta.mail.search.MessageIDTerm(p_s_messageId);
			
													net.forestany.forestj.lib.Global.ilogFiner("look for message, get an array of messages");
			
			/* look for message, get an array of messages */
			jakarta.mail.Message[] a_messages = o_folder.search(o_searchTerm);
						
			if (a_messages.length < 1) { /* no search result */
														net.forestany.forestj.lib.Global.ilogFiner("no search result");
				
				throw new IllegalArgumentException("Could not find e-mail message with message id '" + p_s_messageId + "'");
			} else if (a_messages.length > 1) { /* search result available with more than 1 result */
														net.forestany.forestj.lib.Global.ilogFiner("search result available with more than 1 result");
				
														net.forestany.forestj.lib.Global.ilogFiner("set first message as return value with wrap method");
				
				/* set first message as return value with wrap method */
				o_mailMessage = this.wrapMessage(a_messages[0], p_b_ignoreAttachments);
				
														net.forestany.forestj.lib.Global.ilogFiner("iterate all search results and set or unset seen flag");
				
				/* iterate all search results and set or unset seen flag */
				for (jakarta.mail.Message o_message : a_messages) {
					o_message.setFlag(jakarta.mail.Flags.Flag.SEEN, p_b_setSeen);
				}
			} else {
														net.forestany.forestj.lib.Global.ilogFiner("set first message as return value with wrap method");
				
				/* set first message as return value with wrap method */
				o_mailMessage = this.wrapMessage(a_messages[0], p_b_ignoreAttachments);
				
														net.forestany.forestj.lib.Global.ilogFiner("set or unset seen flag");
				
				/* set or unset seen flag */
				a_messages[0].setFlag(jakarta.mail.Flags.Flag.SEEN, p_b_setSeen);
			}
			
			/* close target folder */
			o_folder.close(false);
		}
		
		/* return mail message object */
		return o_mailMessage;
	}
	
	/**
	 * Move all mail messages from current folder to a target folder
	 * 
	 * @param p_s_targetFolderPath								target mail folder where we want to move all messages to
	 * @throws IllegalArgumentException							current folder or target folder is Folder.ROOT where no messages are allowed, invalid parameter for message id list, could not find any messages
	 * @throws jakarta.mail.MessagingException					connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, issue closing folder
	 */
	public void moveAllMessages(String p_s_targetFolderPath) throws IllegalArgumentException, jakarta.mail.MessagingException {
		this.moveMessages(null, p_s_targetFolderPath);
	}
	
	/**
	 * Move all mail messages from current folder to a target folder
	 * 
	 * @param p_s_targetFolderPath								target mail folder where we want to move all messages to
	 * @param p_s_flag											flag parameter sets flag for all messages in search result
	 * @param p_b_state											state value for flag parameter (true or false)
	 * @throws IllegalArgumentException							current folder or target folder is Folder.ROOT where no messages are allowed, invalid parameter for message id list, could not find any messages
	 * @throws jakarta.mail.MessagingException					connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, issue set/unset a flag of a message, issue closing folder
	 */
	public void moveAllMessages(String p_s_targetFolderPath, String p_s_flag, boolean p_b_state) throws IllegalArgumentException, jakarta.mail.MessagingException {
		this.moveMessages(null, p_s_targetFolderPath, p_s_flag, p_b_state);
	}
	
	/**
	 * Move mail messages from current folder to a target folder
	 * 
	 * @param p_a_messageIds									filter by message id's in a list parameter value
	 * @param p_s_targetFolderPath								target mail folder where we want to move all messages to
	 * @throws IllegalArgumentException							current folder or target folder is Folder.ROOT where no messages are allowed, invalid parameter for message id list, could not find any messages
	 * @throws jakarta.mail.MessagingException					connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, issue closing folder
	 */
	public void moveMessages(java.util.List<String> p_a_messageIds, String p_s_targetFolderPath) throws IllegalArgumentException, jakarta.mail.MessagingException {
		this.moveMessages(p_a_messageIds, p_s_targetFolderPath, null, false);
	}
	
	/**
	 * Move mail messages from current folder to a target folder
	 * 
	 * @param p_a_messageIds									filter by message id's in a list parameter value
	 * @param p_s_targetFolderPath								target mail folder where we want to move messages to
	 * @param p_s_flag											flag parameter sets flag for all messages in search result
	 * @param p_b_state											state value for flag parameter (true or false)
	 * @throws IllegalArgumentException							current folder or target folder is Folder.ROOT where no messages are allowed, invalid parameter for message id list, could not find any messages
	 * @throws jakarta.mail.MessagingException					connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, issue set/unset a flag of a message, issue closing folder
	 */
	public void moveMessages(java.util.List<String> p_a_messageIds, String p_s_targetFolderPath, String p_s_flag, boolean p_b_state) throws IllegalArgumentException, jakarta.mail.MessagingException {
		/* pop3 is not supported */
		if (this.s_protocol.startsWith("pop3")) {
			throw new IllegalArgumentException("POP3(s) protocol is not supported");
		}
		
		/* check if mail client is set for retrieving mails */
		if (this.o_sessionGet == null) {
			throw new IllegalStateException("mail client has no settings to retrieve mails from server");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if current folder is not Folder.ROOT");
		
		/* check if current folder is not Folder.ROOT */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(this.o_currentFolder.getName())) {
			throw new IllegalArgumentException("Current folder is default folder, please specifiy a sub folder like 'inbox' or 'trash'");
		}
		
		/* check if list for message id's is not empty */
		if ( (p_a_messageIds != null) && (p_a_messageIds.size() < 1) ) {
			throw new IllegalArgumentException("Empty message id list parameter");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if target folder is not Folder.ROOT");
		
		/* check if target folder is not Folder.ROOT */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_targetFolderPath)) {
			throw new IllegalArgumentException("Empty target folder path parameter");
		}
		
		/* use session instance to connect to mail server */
		try (jakarta.mail.Store o_store = this.o_sessionGet.getStore(this.s_protocol)) {
													net.forestany.forestj.lib.Global.ilogFine("connect to mail server");
						
			/* connect to mail server */
			o_store.connect();
			
													net.forestany.forestj.lib.Global.ilogFine("get target folder");
    
			/* get target folder */
			jakarta.mail.Folder o_targetFolder = o_store.getDefaultFolder();
			
													net.forestany.forestj.lib.Global.ilogFine("get full path to target folder");
			
			try {
														net.forestany.forestj.lib.Global.ilogFine("iterate target folder path");
			
				/* iterate target folder path */
				for (String s_folder : p_s_targetFolderPath.split("/")) {
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_folder)) {
						o_targetFolder = o_targetFolder.getFolder(s_folder);
					}
				}
				
														net.forestany.forestj.lib.Global.ilogFine("reached target folder, open it with read write access");
				
				/* reached target folder, open it with read write access */
				o_targetFolder.open(jakarta.mail.Folder.READ_WRITE);
			} catch (jakarta.mail.MessagingException o_exc) {
				throw new jakarta.mail.MessagingException("Wrong target folder path '" + p_s_targetFolderPath + "' or could not open target folder with read write access", o_exc);
			}
			
													net.forestany.forestj.lib.Global.ilogFine("get default folder");
    
			/* get default folder */
			jakarta.mail.Folder o_folder = o_store.getDefaultFolder();

													net.forestany.forestj.lib.Global.ilogFine("get full path to current folder");
			
			try {
														net.forestany.forestj.lib.Global.ilogFine("iterate folder path");
			
				/* iterate folder path */
				for (String s_folder : this.o_currentFolder.getFullPath().split("/")) {
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_folder)) {
						o_folder = o_folder.getFolder(s_folder);
					}
				}
				
														net.forestany.forestj.lib.Global.ilogFine("reached folder, open it with read write access");
				
				/* reached folder, open it with read write access */
				o_folder.open(jakarta.mail.Folder.READ_WRITE);
			} catch (jakarta.mail.MessagingException o_exc) {
				throw new jakarta.mail.MessagingException("Wrong folder path '" + this.o_currentFolder.getFullPath() + "' or could not open folder with read write access", o_exc);
			}
				
			/* temporary list value to store all messages */ 
			jakarta.mail.Message[] a_messages = null;
			
			if (p_a_messageIds != null) { /* use message id list to filter messages in current folder */
														net.forestany.forestj.lib.Global.ilogFine("use message id list to filter messages in current folder");
				
														net.forestany.forestj.lib.Global.ilogFine("create search term array with message id list parameter size");
				
				/* create search term array with message id list parameter size */
				jakarta.mail.search.SearchTerm[] a_searchTerms = new jakarta.mail.search.SearchTerm[p_a_messageIds.size()];
				int i = 0;
				
														net.forestany.forestj.lib.Global.ilogFine("create message id term for each search term in array");
				
				/* create message id term for each search term in array */
				for (String s_messageId : p_a_messageIds) {
					a_searchTerms[i++] = new jakarta.mail.search.MessageIDTerm(s_messageId);
				}
				
														net.forestany.forestj.lib.Global.ilogFine("group all search terms in an overall or term");
				
				/* group all search terms in an overall or term */
				jakarta.mail.search.SearchTerm o_orTerm = new jakarta.mail.search.OrTerm(a_searchTerms);
				
														net.forestany.forestj.lib.Global.ilogFine("look for messages, get an array of messages back");
				
				/* look for messages, get an array of messages back */
				a_messages = o_folder.search(o_orTerm);
			} else { /* get all message from current folder */
														net.forestany.forestj.lib.Global.ilogFine("get all message from current folder");
				
				a_messages = o_folder.getMessages();
			}
			
			if (a_messages.length < 1) { /* message result is empty */
				throw new IllegalArgumentException("Could not find any mail messages");
			} else {
				/* if flag parameter is set, set this flag for all messages in search result */
				if (p_s_flag != null) {
															net.forestany.forestj.lib.Global.ilogFine("flag parameter is set, set this flag for all messages in search result");
					
					/* iterate message in search result */
					for (jakarta.mail.Message o_message : a_messages) {
						/* set flag of mail message */
						o_message.setFlag(Client.stringToFlag(p_s_flag), p_b_state);
					}
				}
				
														net.forestany.forestj.lib.Global.ilogFine("copy messages from current folder to target folder");
				
				/* copy messages from current folder to target folder */
				o_folder.copyMessages(a_messages, o_targetFolder);
				
				/* if flag parameter is not set or other flag than deleted is used, delete all messages in current folder which have been copied */
				if ( (p_s_flag == null) || (Client.stringToFlag(p_s_flag) != jakarta.mail.Flags.Flag.DELETED) ) {
															net.forestany.forestj.lib.Global.ilogFine("flag parameter is not set or other flag than deleted is used, delete all messages in current folder which have been copied");
					
					for (jakarta.mail.Message o_message : a_messages) {
						o_message.setFlag(jakarta.mail.Flags.Flag.DELETED, true);
					}
				}
			}
			
			/* close target folder */
			o_targetFolder.close(false);
			
			/* close current folder, expunge all deleted messages */
			o_folder.close(true);
		}
	}
	
	/**
	 * Set or unset flag value of a mail message found by message id in current mail folder
	 * 
	 * @param p_s_messageId										unique identifier for mail message
	 * @param p_s_flag											flag parameter sets flag for all messages in search result											
	 * @param p_b_state											state value for flag parameter (true or false)
	 * @throws IllegalArgumentException							current folder or target folder is Folder.ROOT where no messages are allowed, invalid parameter for message id list, could not find any messages
	 * @throws jakarta.mail.MessagingException					connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, issue set/unset a flag of a message, issue closing folder
	 */
	private void setFlag(String p_s_messageId, String p_s_flag, boolean p_b_state) throws IllegalArgumentException, jakarta.mail.MessagingException {
		/* pop3 is not supported */
		if (this.s_protocol.startsWith("pop3")) {
			throw new IllegalArgumentException("POP3(s) protocol is not supported");
		}
		
		/* check if mail client is set for retrieving mails */
		if (this.o_sessionGet == null) {
			throw new IllegalStateException("mail client has no settings to retrieve mails from server");
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("check if current folder is not Folder.ROOT");
		
		/* check if current folder is not Folder.ROOT */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(this.o_currentFolder.getName())) {
			throw new IllegalArgumentException("Current folder is default folder, please specifiy a sub folder like 'inbox' or 'trash'");
		}
		
		/* check message id parameter */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_messageId)) {
			throw new IllegalArgumentException("Empty message id parameter");
		}
		
		/* use session instance to connect to mail server */
		try (jakarta.mail.Store o_store = this.o_sessionGet.getStore(this.s_protocol)) {
													net.forestany.forestj.lib.Global.ilogFiner("connect to mail server");
						
			/* connect to mail server */
			o_store.connect();
			
													net.forestany.forestj.lib.Global.ilogFiner("get default folder");
    
			/* get default folder */
			jakarta.mail.Folder o_folder = o_store.getDefaultFolder();
			
													net.forestany.forestj.lib.Global.ilogFiner("get full path to current folder");
			
			/* get full path to current folder */
			String s_folderPath = this.o_currentFolder.getFullPath();
			
			try {
														net.forestany.forestj.lib.Global.ilogFiner("iterate folder path");
			
				/* iterate folder path */
				for (String s_folder : s_folderPath.split("/")) {
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_folder)) {
						o_folder = o_folder.getFolder(s_folder);
					}
				}
				
														net.forestany.forestj.lib.Global.ilogFiner("reached current folder, open it with read write access");
				
				/* reached target folder, open it with read write access */
				o_folder.open(jakarta.mail.Folder.READ_WRITE);
			} catch (jakarta.mail.MessagingException o_exc) {
				throw new jakarta.mail.MessagingException("Wrong folder path '" + s_folderPath + "' or could not open folder with read write access", o_exc);
			}
			
													net.forestany.forestj.lib.Global.ilogFiner("create search term with message id parameter");
			
			/* create search term with message id parameter */
			jakarta.mail.search.SearchTerm o_searchTerm = new jakarta.mail.search.MessageIDTerm(p_s_messageId);
			
													net.forestany.forestj.lib.Global.ilogFiner("look for message, get an array of messages");
			
			/* look for message, get an array of messages */
			jakarta.mail.Message[] a_messages = o_folder.search(o_searchTerm);
						
			if (a_messages.length < 1) { /* no search result */
														net.forestany.forestj.lib.Global.ilogFiner("no search result");
				
				throw new IllegalArgumentException("Could not find e-mail message with message id '" + p_s_messageId + "'");
			} else if (a_messages.length > 1) { /* search result available with more than 1 result */
														net.forestany.forestj.lib.Global.ilogFiner("search result available with more than 1 result");
				
														net.forestany.forestj.lib.Global.ilogFiner("iterate all search results and set or unset flag");
				
				/* iterate all search results and set or unset flag */
				for (jakarta.mail.Message o_message : a_messages) {
					o_message.setFlag(Client.stringToFlag(p_s_flag), p_b_state);
				}
			} else {
														net.forestany.forestj.lib.Global.ilogFiner("set or unset flag");
				
				/* set or unset flag */
				a_messages[0].setFlag(Client.stringToFlag(p_s_flag), p_b_state);
			}
			
			/* close current folder */
			o_folder.close(false);
		}
	}
	
	/**
	 * Set seen flag value of a mail message found by message id in current mail folder
	 * 
	 * @param p_s_messageId										unique identifier for mail message
	 * @throws IllegalArgumentException							current folder or target folder is Folder.ROOT where no messages are allowed, invalid parameter for message id list, could not find any messages
	 * @throws jakarta.mail.MessagingException					connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, issue set/unset a flag of a message, issue closing folder
	 */
	public void setSeen(String p_s_messageId) throws IllegalArgumentException, jakarta.mail.MessagingException {
		this.setFlag(p_s_messageId, Message.FLAGS[5], true);
	}
	
	/**
	 * Unset seen flag value of a mail message found by message id in current mail folder
	 * 
	 * @param p_s_messageId										unique identifier for mail message
	 * @throws IllegalArgumentException							current folder or target folder is Folder.ROOT where no messages are allowed, invalid parameter for message id list, could not find any messages
	 * @throws jakarta.mail.MessagingException					connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, issue set/unset a flag of a message, issue closing folder
	 */
	public void unsetSeen(String p_s_messageId) throws IllegalArgumentException, jakarta.mail.MessagingException {
		this.setFlag(p_s_messageId, Message.FLAGS[5], false);
	}
	
	/**
	 * Set flagged flag value of a mail message found by message id in current mail folder
	 * 
	 * @param p_s_messageId										unique identifier for mail message
	 * @throws IllegalArgumentException							current folder or target folder is Folder.ROOT where no messages are allowed, invalid parameter for message id list, could not find any messages
	 * @throws jakarta.mail.MessagingException					connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, issue set/unset a flag of a message, issue closing folder
	 */
	public void setFlagged(String p_s_messageId) throws IllegalArgumentException, jakarta.mail.MessagingException {
		this.setFlag(p_s_messageId, Message.FLAGS[3], true);
	}
	
	/**
	 * Unset flagged flag value of a mail message found by message id in current mail folder
	 * 
	 * @param p_s_messageId										unique identifier for mail message
	 * @throws IllegalArgumentException							current folder or target folder is Folder.ROOT where no messages are allowed, invalid parameter for message id list, could not find any messages
	 * @throws jakarta.mail.MessagingException					connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, issue set/unset a flag of a message, issue closing folder
	 */
	public void unsetFlagged(String p_s_messageId) throws IllegalArgumentException, jakarta.mail.MessagingException {
		this.setFlag(p_s_messageId, Message.FLAGS[3], false);
	}
	
	/**
	 * Set answered flag value of a mail message found by message id in current mail folder
	 * 
	 * @param p_s_messageId										unique identifier for mail message
	 * @throws IllegalArgumentException							current folder or target folder is Folder.ROOT where no messages are allowed, invalid parameter for message id list, could not find any messages
	 * @throws jakarta.mail.MessagingException					connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, issue set/unset a flag of a message, issue closing folder
	 */
	public void setAnswered(String p_s_messageId) throws IllegalArgumentException, jakarta.mail.MessagingException {
		this.setFlag(p_s_messageId, Message.FLAGS[0], true);
	}
	
	/**
	 * Unset answered flag value of a mail message found by message id in current mail folder
	 * 
	 * @param p_s_messageId										unique identifier for mail message
	 * @throws IllegalArgumentException							current folder or target folder is Folder.ROOT where no messages are allowed, invalid parameter for message id list, could not find any messages
	 * @throws jakarta.mail.MessagingException					connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, issue set/unset a flag of a message, issue closing folder
	 */
	public void unsetAnswered(String p_s_messageId) throws IllegalArgumentException, jakarta.mail.MessagingException {
		this.setFlag(p_s_messageId, Message.FLAGS[0], false);
	}
	
	/**
	 * Set delete flag value of a mail message found by message id in current mail folder
	 * 
	 * @param p_s_messageId										unique identifier for mail message
	 * @throws IllegalArgumentException							current folder or target folder is Folder.ROOT where no messages are allowed, invalid parameter for message id list, could not find any messages
	 * @throws jakarta.mail.MessagingException					connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, issue set/unset a flag of a message, issue closing folder
	 */
	public void setDelete(String p_s_messageId) throws IllegalArgumentException, jakarta.mail.MessagingException {
		this.setFlag(p_s_messageId, Message.FLAGS[1], true);
	}
	
	/**
	 * Unset delete flag value of a mail message found by message id in current mail folder
	 * 
	 * @param p_s_messageId										unique identifier for mail message
	 * @throws IllegalArgumentException							current folder or target folder is Folder.ROOT where no messages are allowed, invalid parameter for message id list, could not find any messages
	 * @throws jakarta.mail.MessagingException					connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, issue set/unset a flag of a message, issue closing folder
	 */
	public void unsetDelete(String p_s_messageId) throws IllegalArgumentException, jakarta.mail.MessagingException {
		this.setFlag(p_s_messageId, Message.FLAGS[1], false);
	}

	/**
	 * Set recent flag value of a mail message found by message id in current mail folder
	 * 
	 * @param p_s_messageId										unique identifier for mail message
	 * @throws IllegalArgumentException							current folder or target folder is Folder.ROOT where no messages are allowed, invalid parameter for message id list, could not find any messages
	 * @throws jakarta.mail.MessagingException					connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, issue set/unset a flag of a message, issue closing folder
	 */
	public void setRecent(String p_s_messageId) throws IllegalArgumentException, jakarta.mail.MessagingException {
		this.setFlag(p_s_messageId, Message.FLAGS[4], true);
	}
	
	/**
	 * Unset recent flag value of a mail message found by message id in current mail folder
	 * 
	 * @param p_s_messageId										unique identifier for mail message
	 * @throws IllegalArgumentException							current folder or target folder is Folder.ROOT where no messages are allowed, invalid parameter for message id list, could not find any messages
	 * @throws jakarta.mail.MessagingException					connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, issue set/unset a flag of a message, issue closing folder
	 */
	public void unsetRecent(String p_s_messageId) throws IllegalArgumentException, jakarta.mail.MessagingException {
		this.setFlag(p_s_messageId, Message.FLAGS[4], false);
	}
	
	/**
	 * Delete/Expunge all messages in current mail folder
	 * 
	 * @throws IllegalArgumentException							current folder is Folder.ROOT where no messages are allowed
	 * @throws jakarta.mail.MessagingException					connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, issue set/unset a flag of a message, issue closing folder
	 */
	public void expungeFolder() throws IllegalArgumentException, jakarta.mail.MessagingException {
		this.expungeFolder(null);
	}
	
	/**
	 * Delete/Expunge all messages in target mail folder
	 * 
	 * @param p_s_folderPath									target mail folder where we want to delete all messages
	 * @throws IllegalArgumentException							target folder is Folder.ROOT where no messages are allowed, invalid parameter for target folder
	 * @throws jakarta.mail.MessagingException					connection failed to mail server, wrong folder path, could not open folder with read write access, could not get messages, issue set/unset a flag of a message, issue closing folder
	 */
	public void expungeFolder(String p_s_folderPath) throws IllegalArgumentException, jakarta.mail.MessagingException {
		/* pop3 is not supported */
		if (this.s_protocol.startsWith("pop3")) {
			throw new IllegalArgumentException("POP3(s) protocol is not supported");
		}
		
		/* check if mail client is set for retrieving mails */
		if (this.o_sessionGet == null) {
			throw new IllegalStateException("mail client has no settings to retrieve mails from server");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if target folder and current folder are not Folder.ROOT");
		
		/* check if target folder and current folder are not Folder.ROOT */
		if ( (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_folderPath)) && (net.forestany.forestj.lib.Helper.isStringEmpty(this.o_currentFolder.getName())) ) {
			throw new IllegalArgumentException("Target folder and current folder are default folder, please specifiy a sub folder of default folder like 'inbox' or 'trash'");
		}
		
		/* use session instance to connect to mail server */
		try (jakarta.mail.Store o_store = this.o_sessionGet.getStore(this.s_protocol)) {
													net.forestany.forestj.lib.Global.ilogFine("connect to mail server");
						
			/* connect to mail server */
			o_store.connect();
			
													net.forestany.forestj.lib.Global.ilogFine("get default folder");
    
			/* get default folder */
			jakarta.mail.Folder o_folder = o_store.getDefaultFolder();
			
													net.forestany.forestj.lib.Global.ilogFine("get full path to current folder");
			
			/* get full path to current folder */
			String s_folderPath = this.o_currentFolder.getFullPath();
			
			/* if we have a target folder path, overwrite variable and not stay in current folder */
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_folderPath)) {
														net.forestany.forestj.lib.Global.ilogFine("target folder path set, overwrite variable and not stay in current folder");
				
				s_folderPath = p_s_folderPath;
			}
			
			try {
														net.forestany.forestj.lib.Global.ilogFine("iterate folder path");
			
				/* iterate folder path */
				for (String s_folder : s_folderPath.split("/")) {
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_folder)) {
						o_folder = o_folder.getFolder(s_folder);
					}
				}
				
														net.forestany.forestj.lib.Global.ilogFine("reached folder, open it with read write access");
				
				/* reached folder, open it with read write access */
				o_folder.open(jakarta.mail.Folder.READ_WRITE);
				
														net.forestany.forestj.lib.Global.ilogFine("get all messages from folder and set delete flag for each message");
				
				/* get all messages from folder */
				for (jakarta.mail.Message o_message : o_folder.getMessages()) {
					/* set delete flag for each message */
					o_message.setFlag(jakarta.mail.Flags.Flag.DELETED, true);
				}
			} catch (jakarta.mail.MessagingException o_exc) {
				throw new jakarta.mail.MessagingException("Wrong folder path '" + s_folderPath + "' or could not open folder with read write access", o_exc);
			}
			
			/* close folder, expunge all deleted messages */
			o_folder.close(true);
		}
	}
	
	/**
	 * Change current mail folder to another mail folder, parent or sub folder
	 * 
	 * @param p_s_folderName							folder name of a sub folder, or '..' for parent folder
	 * @throws IllegalArgumentException					invalid target folder parameter or sub folder does not exist
	 * @throws jakarta.mail.MessagingException			connection failed to mail server, or wrong folder path
	 */
	public void changeToFolder(String p_s_folderName) throws IllegalArgumentException, jakarta.mail.MessagingException {
		/* pop3 is not supported */
		if (this.s_protocol.startsWith("pop3")) {
			throw new IllegalArgumentException("POP3(s) protocol is not supported");
		}
		
		/* check if mail client is set for retrieving mails */
		if (this.o_sessionGet == null) {
			throw new IllegalStateException("mail client has no settings to retrieve mails from server");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if target folder parameter does not contain a '/' character");
		
		/* check if target folder parameter does not contain a '/' character */
		if (p_s_folderName.contains("/")) {
			throw new IllegalArgumentException("Invalid character '/' in parameter '" + p_s_folderName + "'");
		}
		
		if (p_s_folderName.contentEquals("..")) { /* go to parent mail folder */
													net.forestany.forestj.lib.Global.ilogFine("go to parent mail folder");
			
													net.forestany.forestj.lib.Global.ilogFine("check if we are not already at Folder.ROOT level");
			
			/* check if we are not already at Folder.ROOT level */
			if (this.o_currentFolder.getParent() != null) {
														net.forestany.forestj.lib.Global.ilogFine("change current folder to parent folder");
				
				/* change current folder to parent folder */
				this.o_currentFolder = this.o_currentFolder.getParent();
			}
		} else { /* go to a mail sub folder */
													net.forestany.forestj.lib.Global.ilogFine("go to a mail sub folder");
			
													net.forestany.forestj.lib.Global.ilogFine("get sub folder object");
			
			/* get sub folder object */
			Folder o_foo = this.o_currentFolder.getSubFolder(p_s_folderName);
			
			/* check if sub folder really exists with target folder parameter */
			if (o_foo == null) {
														net.forestany.forestj.lib.Global.ilogFine("sub folder does not exist with target folder parameter");
				
				throw new IllegalArgumentException("Sub folder '" + p_s_folderName + "' does not exist under '" + ( (net.forestany.forestj.lib.Helper.isStringEmpty(this.o_currentFolder.getName())) ? "/" : this.o_currentFolder.getName() ) + "'");
			}
			
													net.forestany.forestj.lib.Global.ilogFine("change current folder to sub folder");
			
			/* change current folder to sub folder */
			this.o_currentFolder = o_foo;
		}
		
		/* use session instance to connect to mail server */
		try (jakarta.mail.Store o_store = this.o_sessionGet.getStore(this.s_protocol)) {
													net.forestany.forestj.lib.Global.ilogFine("connect to mail server");
						
			/* connect to mail server */
			o_store.connect();
			
													net.forestany.forestj.lib.Global.ilogFine("get default folder");
    
			/* get default folder */
			jakarta.mail.Folder o_folder = o_store.getDefaultFolder();
			
													net.forestany.forestj.lib.Global.ilogFine("get full path to current folder");
			
			/* get full path to current folder */
			String s_folderPath = this.o_currentFolder.getFullPath();
			
			try {
														net.forestany.forestj.lib.Global.ilogFine("iterate folder path");
			
				/* iterate folder path */
				for (String s_folder : s_folderPath.split("/")) {
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_folder)) {
						o_folder = o_folder.getFolder(s_folder);
					}
				}
				
														net.forestany.forestj.lib.Global.ilogFine("get all sub folder of changed current folder");
				
				/* get all sub folder of changed current folder */
				for (jakarta.mail.Folder o_subFolder : o_folder.list()) {
					this.o_currentFolder.addChildren(o_subFolder.getName());
				}
			} catch (jakarta.mail.MessagingException o_exc) {
				throw new jakarta.mail.MessagingException("Wrong folder path '" + s_folderPath + "'", o_exc);
			}
		}
	}
	
	/**
	 * Creates a sub folder at current mail folder
	 * 
	 * @param p_s_folderName							folder name of a sub folder
	 * @throws IllegalArgumentException					invalid target folder parameter or sub folder already exists
	 * @throws jakarta.mail.MessagingException			connection failed to mail server, or wrong folder path or issue creating and subscribing sub folder
	 */
	public void createSubFolder(String p_s_folderName) throws IllegalArgumentException, jakarta.mail.MessagingException {
		/* pop3 is not supported */
		if (this.s_protocol.startsWith("pop3")) {
			throw new IllegalArgumentException("POP3(s) protocol is not supported");
		}
		
		/* check if mail client is set for retrieving mails */
		if (this.o_sessionGet == null) {
			throw new IllegalStateException("mail client has no settings to retrieve mails from server");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if target folder parameter does not contain a '/' character");
		
		/* check if target folder parameter does not contain a '/' character */
		if (p_s_folderName.contains("/")) {
			throw new IllegalArgumentException("Invalid character '/' in parameter '" + p_s_folderName + "'");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if target folder parameter does not contain a '/' character");
		
		/* check if target folder parameter is not equal '.' or '..' */
		if ( (p_s_folderName.contentEquals(".")) || (p_s_folderName.contentEquals("..")) ) {
			throw new IllegalArgumentException("Invalid sub folder name '" + p_s_folderName + "'");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if sub folder already exists");
		
		/* check if sub folder already exists */
		if (this.o_currentFolder.getSubFolder(p_s_folderName) != null) {
			throw new IllegalArgumentException("Sub folder '" + p_s_folderName + "' does exist under '" + ( (net.forestany.forestj.lib.Helper.isStringEmpty(this.o_currentFolder.getName())) ? "/" : this.o_currentFolder.getName() ) + "'");
		}
		
		/* use session instance to connect to mail server */
		try (jakarta.mail.Store o_store = this.o_sessionGet.getStore(this.s_protocol)) {
													net.forestany.forestj.lib.Global.ilogFine("connect to mail server");
						
			/* connect to mail server */
			o_store.connect();
			
													net.forestany.forestj.lib.Global.ilogFine("get default folder");
    
			/* get default folder */
			jakarta.mail.Folder o_folder = o_store.getDefaultFolder();
			
													net.forestany.forestj.lib.Global.ilogFine("get full path to current folder");
			
			/* get full path to current folder */
			String s_folderPath = this.o_currentFolder.getFullPath();
			
			try {
														net.forestany.forestj.lib.Global.ilogFine("iterate folder path");
			
				/* iterate folder path */
				for (String s_folder : s_folderPath.split("/")) {
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_folder)) {
						o_folder = o_folder.getFolder(s_folder);
					}
				}
				
														net.forestany.forestj.lib.Global.ilogFine("set folder object to target folder");
				
				/* set folder object to target folder */
				o_folder = o_folder.getFolder(p_s_folderName);
			
				/* check again if folder already exists */
				if (!o_folder.exists()) {
															net.forestany.forestj.lib.Global.ilogFine("create sub folder with holding folders and holding message settings");
					
					/* create sub folder with holding folders and holding message settings */ 
					o_folder.create(jakarta.mail.Folder.HOLDS_FOLDERS + jakarta.mail.Folder.HOLDS_MESSAGES);
					
															net.forestany.forestj.lib.Global.ilogFine("set sub folder as subscribed for imap");
					
					/* set sub folder as subscribed for imap */
					o_folder.setSubscribed(true);
					
															net.forestany.forestj.lib.Global.ilogFine("add sub folder to current folder as child item");
					
					/* add sub folder to current folder as child item */
					this.o_currentFolder.addChildren(p_s_folderName);
				} else {
					/* folder already exists */
					throw new IllegalArgumentException("Sub folder '" + p_s_folderName + "' does exist under '" + this.o_currentFolder.getName() + "'");
				}
			} catch (jakarta.mail.MessagingException o_exc) {
				throw new jakarta.mail.MessagingException("Wrong folder path '" + s_folderPath + "' or issue with creating sub folder", o_exc);
			}
		}
	}
	
	/**
	 * Deletes current mail folder, no other sub folders and messages allowed
	 * 
	 * @throws IllegalArgumentException					invalid current folder parameter or sub folder(s) and message(s) still there
	 * @throws jakarta.mail.MessagingException			connection failed to mail server, or wrong folder path or issue deleting and unsubscribing current folder
	 */
	public void deleteFolder() throws IllegalArgumentException, jakarta.mail.MessagingException {
		/* pop3 is not supported */
		if (this.s_protocol.startsWith("pop3")) {
			throw new IllegalArgumentException("POP3(s) protocol is not supported");
		}
		
		/* check if mail client is set for retrieving mails */
		if (this.o_sessionGet == null) {
			throw new IllegalStateException("mail client has no settings to retrieve mails from server");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if current folder is not Folder.ROOT");
		
		/* check if current folder is not Folder.ROOT */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(this.o_currentFolder.getName())) {
			throw new IllegalArgumentException("Cannot delete Folder.ROOT");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if current folder has any sub folders, these must be deleted first ");
		
		/* check if current folder has any sub folders, these must be deleted first */
		if (this.o_currentFolder.getChildren().size() > 0) {
			throw new IllegalArgumentException("Current folder has '" + this.o_currentFolder.getChildren().size() + "' sub folder(s). Delete all sub folders and messages first");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if current folder has any messags left, these must be deleted first ");
		
		/* check if current folder has any messages left, these must be deleted first */
		int i_amount = this.getMessagesAmount();
		
		if (i_amount > 0) {
			throw new IllegalArgumentException("Current folder has '" + i_amount + "' message(s). Delete all messages first");
		}
		
		/* use session instance to connect to mail server */
		try (jakarta.mail.Store o_store = this.o_sessionGet.getStore(this.s_protocol)) {
													net.forestany.forestj.lib.Global.ilogFine("connect to mail server");
						
			/* connect to mail server */
			o_store.connect();
			
													net.forestany.forestj.lib.Global.ilogFine("get default folder");
    
			/* get default folder */
			jakarta.mail.Folder o_folder = o_store.getDefaultFolder();
			
													net.forestany.forestj.lib.Global.ilogFine("get full path to current folder");
			
			/* get full path to current folder */
			String s_folderPath = this.o_currentFolder.getFullPath();
			
			try {
														net.forestany.forestj.lib.Global.ilogFine("iterate folder path");
			
				/* iterate folder path */
				for (String s_folder : s_folderPath.split("/")) {
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_folder)) {
						o_folder = o_folder.getFolder(s_folder);
					}
				}
				
														net.forestany.forestj.lib.Global.ilogFine("unsubscribe folder for imap");
				
				/* unsubscribe folder for imap */
				o_folder.setSubscribed(false);
				
														net.forestany.forestj.lib.Global.ilogFine("delete target folder");
				
				/* delete target folder */
				o_folder.delete(false);
				
														net.forestany.forestj.lib.Global.ilogFine("clear all children of parent folder");
				
				/* clear all children of parent folder */
				this.o_currentFolder.getParent().clearChildren();
				
														net.forestany.forestj.lib.Global.ilogFine("change current folder to parent folder");
				
				/* change current folder to parent folder */
				this.changeToFolder("..");
			} catch (jakarta.mail.MessagingException o_exc) {
				throw new jakarta.mail.MessagingException("Wrong folder path '" + s_folderPath + "' or issue with deleting folder", o_exc);
			}
		}
	}

	/**
	 * Rename a sub folder in current mail folder
	 * 
	 * @param p_s_folder								invalid target folder parameter or sub folder(s) and message(s) still there
	 * @param p_s_newFolderName							new name for target folder
	 * @throws IllegalArgumentException					invalid target folder parameter or invalid new folder name, target folder does not exist, or new folder name already exists as a sub folder
	 * @throws jakarta.mail.MessagingException			connection failed to mail server, or wrong folder path or issue renaming sub folder
	 */
	public void renameSubFolder(String p_s_folder, String p_s_newFolderName) throws IllegalArgumentException, jakarta.mail.MessagingException {
		/* pop3 is not supported */
		if (this.s_protocol.startsWith("pop3")) {
			throw new IllegalArgumentException("POP3(s) protocol is not supported");
		}
		
		/* check if mail client is set for retrieving mails */
		if (this.o_sessionGet == null) {
			throw new IllegalStateException("mail client has no settings to retrieve mails from server");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if target folder parameter does not contain a '/' character");
		
		/* check if target folder parameter does not contain a '/' character */
		if (p_s_folder.contains("/")) {
			throw new IllegalArgumentException("Invalid character '/' in folder parameter '" + p_s_folder + "'");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if new folder parameter does not contain a '/' character");
		
		/* check if new folder parameter does not contain a '/' character */
		if (p_s_newFolderName.contains("/")) {
			throw new IllegalArgumentException("Invalid character '/' in new folder parameter '" + p_s_newFolderName + "'");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if folder parameter is not equal '.' or '..'");
		
		/* check if folder parameter is not equal '.' or '..' */
		if (java.util.Arrays.asList(".", "..").contains(p_s_folder.toLowerCase())) {
			throw new IllegalArgumentException("Invalid folder name '" + p_s_folder + "'");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if new folder parameter is not equal '.' or '..'");
		
		/* check if new folder parameter is not equal '.' or '..' */
		if (java.util.Arrays.asList(".", "..").contains(p_s_newFolderName.toLowerCase())) {
			throw new IllegalArgumentException("Invalid new folder name '" + p_s_newFolderName + "'");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if target folder exists as sub folder under current folder");
		
		/* check if target folder exists as sub folder under current folder */
		if (this.o_currentFolder.getSubFolder(p_s_folder) == null) {
			throw new IllegalArgumentException("Sub folder '" + p_s_newFolderName + "' does not exist under '" + ( (net.forestany.forestj.lib.Helper.isStringEmpty(this.o_currentFolder.getName())) ? "/" : this.o_currentFolder.getName() ) + "'");
		}
		
												net.forestany.forestj.lib.Global.ilogFine("check if new folder name does not exist as sub folder under current folder");
		
		/* check if new folder name does not exist as sub folder under current folder */
		if (this.o_currentFolder.getSubFolder(p_s_newFolderName) != null) {
			throw new IllegalArgumentException("Sub folder '" + p_s_newFolderName + "' already exists under '" + ( (net.forestany.forestj.lib.Helper.isStringEmpty(this.o_currentFolder.getName())) ? "/" : this.o_currentFolder.getName() ) + "'");
		}
		
		/* use session instance to connect to mail server */
		try (jakarta.mail.Store o_store = this.o_sessionGet.getStore(this.s_protocol)) {
													net.forestany.forestj.lib.Global.ilogFine("connect to mail server");
						
			/* connect to mail server */
			o_store.connect();
			
													net.forestany.forestj.lib.Global.ilogFine("get default folder");
    
			/* get default folder */
			jakarta.mail.Folder o_folder = o_store.getDefaultFolder();
			
													net.forestany.forestj.lib.Global.ilogFine("get default folder for rename path");
			
			/* get default folder for rename path */
			jakarta.mail.Folder o_renameFolder = o_store.getDefaultFolder();
			
													net.forestany.forestj.lib.Global.ilogFine("get full path to current folder");
			
			/* get full path to current folder */
			String s_folderPath = this.o_currentFolder.getFullPath();
			
			try {
														net.forestany.forestj.lib.Global.ilogFine("iterate folder path");
			
				/* iterate folder path */
				for (String s_folder : s_folderPath.split("/")) {
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_folder)) {
						o_folder = o_folder.getFolder(s_folder);
						o_renameFolder = o_renameFolder.getFolder(s_folder);
					}
				}
				
														net.forestany.forestj.lib.Global.ilogFine("set variable to target folder");
				
				/* set variable to target folder */
				o_folder = o_folder.getFolder(p_s_folder);
				
														net.forestany.forestj.lib.Global.ilogFine("set variable to new sub folder name");
				
				/* set variable to new sub folder name */
				o_renameFolder = o_renameFolder.getFolder(p_s_newFolderName);
				
				/* check again if target sub folder exists */
				if (o_folder.exists()) {
					/* check again if new sub folder name exists */
					if (!o_renameFolder.exists()) {
																net.forestany.forestj.lib.Global.ilogFine("rename target sub folder to new sub folder name");
						
						/* rename target sub folder to new sub folder name */
						o_folder.renameTo(o_renameFolder);
						
																net.forestany.forestj.lib.Global.ilogFine("set renamed folder as subscribed for imap");
					
						/* set renamed folder as subscribed for imap */
						o_renameFolder.setSubscribed(true);
						
																net.forestany.forestj.lib.Global.ilogFine("clear all children of parent folder");
						
						/* clear all children of parent folder */
						this.o_currentFolder.getParent().clearChildren();
						
						String s_foo = this.o_currentFolder.getName();
						
																net.forestany.forestj.lib.Global.ilogFine("change current folder to parent folder");
						
						/* change current folder to parent folder */
						this.changeToFolder("..");
						
																net.forestany.forestj.lib.Global.ilogFine("change current folder to folder '" + s_foo + "' where we renamed a sub folder");
						
						/* change current folder to folder where we renamed a sub folder */
						this.changeToFolder(s_foo);
					} else {
						throw new IllegalArgumentException("Sub folder '" + p_s_newFolderName + "' already exists under '" + this.o_currentFolder.getName() + "'");
					}
				} else {
					throw new IllegalArgumentException("Sub folder '" + p_s_folder + "' does not exist under '" + this.o_currentFolder.getName() + "'");
				}
			} catch (jakarta.mail.MessagingException o_exc) {
				throw new jakarta.mail.MessagingException("Wrong folder path '" + s_folderPath + "' or issue with renaming sub folder", o_exc);
			}
		}
	}
}
