package net.forestany.forestj.lib.net.sftp;

/**
 * Encapsulation of tunnel credential properties
 */
public class TunnelCredentials {
	
	/* Fields */
	
	private String s_tunnelHost;
	private int i_tunnelLocalPort;
	private int i_tunnelPort;
	private String s_tunnelUser;
	private String s_tunnelPassword;
	private String s_tunnelFilePathAuthentication;
	
	/* Properties */
	
	/**
	 * get tunnel host
	 * 
	 * @return String
	 */
	public String getTunnelHost() {
		return this.s_tunnelHost;
	}
	
	/**
	 * set tunnel host
	 * 
	 * @param p_s_value String
	 * @throws IllegalArgumentException tunnel host parameter is null or empty
	 */
	public void setTunnelHost(String p_s_value) throws IllegalArgumentException {
		/* check if tunnel parameter is not null or empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			throw new IllegalArgumentException("Tunnel host is null or empty");
		}
		
		this.s_tunnelHost = p_s_value;
	}
	
	/**
	 * get tunnel local port
	 * 
	 * @return int
	 */
	public int getTunnelLocalPort() {
		return this.i_tunnelLocalPort;
	}
	
	/**
	 * set tunnel local port
	 * 
	 * @param p_i_value int
	 * @throws IllegalArgumentException parameter must be between '1' and '65535'
	 */
	public void setTunnelLocalPort(int p_i_value) throws IllegalArgumentException {
		/* check valid tunnel local port number */
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Tunnel local port must be at least '1', but was set to '" + p_i_value + "'");
		}
		
		/* check valid tunnel local port number */
		if (p_i_value > 65535) {
			throw new IllegalArgumentException("Tunnel local port must be lower equal '65535', but was set to '" + p_i_value + "'");
		}
		
		this.i_tunnelLocalPort = p_i_value;
	}
	
	/**
	 * get tunnel port
	 * 
	 * @return int
	 */
	public int getTunnelPort() {
		return this.i_tunnelPort;
	}
	
	/**
	 * set tunnel port
	 * 
	 * @param p_i_value int
	 * @throws IllegalArgumentException parameter must be between '1' and '65535'
	 */
	public void setTunnelPort(int p_i_value) throws IllegalArgumentException {
		/* check valid tunnel port number */
		if (p_i_value < 1) {
			throw new IllegalArgumentException("Tunnel port must be at least '1', but was set to '" + p_i_value + "'");
		}
		
		/* check valid tunnel port number */
		if (p_i_value > 65535) {
			throw new IllegalArgumentException("Tunnel port must be lower equal '65535', but was set to '" + p_i_value + "'");
		}
		
		this.i_tunnelPort = p_i_value;
	}
	
	/**
	 * get tunnel user
	 * 
	 * @return String
	 */
	public String getTunnelUser() {
		return this.s_tunnelUser;
	}
	
	/**
	 * set tunnel user
	 * 
	 * @param p_s_value String
	 */
	public void setTunnelUser(String p_s_value) {
		/* user necessary */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			throw new IllegalArgumentException("Tunnel user is null or empty");
		}
		
		this.s_tunnelUser = p_s_value;
	}
	
	/**
	 * get tunnel password
	 * 
	 * @return String
	 */
	public String getTunnelPassword() {
		return this.s_tunnelPassword;
	}
	
	/**
	 * set tunnel password
	 * 
	 * @param p_s_value String
	 */
	public void setTunnelPassword(String p_s_value) {
		this.s_tunnelPassword = p_s_value;
	}
	
	/**
	 * get tunnel file path authentication(private key)
	 * 
	 * @return String
	 */
	public String getTunnelFilePathAuthentication() {
		return this.s_tunnelFilePathAuthentication;
	}
	
	/**
	 * set tunnel file path authentication(private key)
	 * 
	 * @param p_s_value String
	 */
	public void setTunnelFilePathAuthentication(String p_s_value) {
		this.s_tunnelFilePathAuthentication = p_s_value;
	}
	
	/* Methods */
	
	/**
	 * TunnelCredentials constructor
	 * 
	 * @param p_s_tunnelHost									tunnel host value, address of the server where you do ssh tunneling
	 * @param p_i_tunnelLocalPort								tunnel local port value, port for ssh tunnel forwarding, which will be used to establish your sftp connection locally
	 * @param p_i_tunnelPort									tunnel port value, port of server where you do ssh tunneling
	 * @param p_s_tunnelUser									tunnel user value for the server where you do ssh tunneling
	 * @param p_s_tunnelPassword								password for tunnel user or for authentication file (null for none)
	 * @param p_s_tunnelFilePathAuthentication					file path to authentication file with private key
	 * @throws IllegalArgumentException							wrong tunnel host value, invalid tunnel port number, missing user or password
	 */
	public TunnelCredentials(String p_s_tunnelHost, int p_i_tunnelLocalPort, int p_i_tunnelPort, String p_s_tunnelUser, String p_s_tunnelPassword, String p_s_tunnelFilePathAuthentication) throws IllegalArgumentException {
		/* check if tunnel parameter is not null or empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_tunnelHost)) {
			throw new IllegalArgumentException("Tunnel host is null or empty");
		}
		
												net.forestany.forestj.lib.Global.ilogConfig("check tunnel port and tunnel credential validity");
		
		/* check valid tunnel local port number */
		if (p_i_tunnelLocalPort < 1) {
			throw new IllegalArgumentException("Tunnel local port must be at least '1', but was set to '" + p_i_tunnelLocalPort + "'");
		}
		
		/* check valid tunnel local port number */
		if (p_i_tunnelLocalPort > 65535) {
			throw new IllegalArgumentException("Tunnel local port must be lower equal '65535', but was set to '" + p_i_tunnelLocalPort + "'");
		}
												
		/* check valid tunnel port number */
		if (p_i_tunnelPort < 1) {
			throw new IllegalArgumentException("Tunnel port must be at least '1', but was set to '" + p_i_tunnelPort + "'");
		}
		
		/* check valid tunnel port number */
		if (p_i_tunnelPort > 65535) {
			throw new IllegalArgumentException("Tunnel port must be lower equal '65535', but was set to '" + p_i_tunnelPort + "'");
		}
		
		/* user necessary */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_tunnelUser)) {
			throw new IllegalArgumentException("Tunnel user is null or empty");
		}
		
		/* password or file with private key necessary */
		if ( (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_tunnelPassword)) && (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_tunnelFilePathAuthentication)) ) {
			throw new IllegalArgumentException("Tunnel password or tunnel file path with authentication file is null or empty");
		}
		
		this.s_tunnelHost = p_s_tunnelHost;
		this.i_tunnelLocalPort = p_i_tunnelLocalPort;
		this.i_tunnelPort = p_i_tunnelPort;
		this.s_tunnelUser = p_s_tunnelUser;
		this.s_tunnelPassword = p_s_tunnelPassword;
		this.s_tunnelFilePathAuthentication = p_s_tunnelFilePathAuthentication;
	}
}
