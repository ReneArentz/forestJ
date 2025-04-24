package net.forestany.forestj.lib.net.ftp;

/**
 * Selection of possible FTP protocols for the client as enumeration.
 *
 * FTP
 * FTPS
 * FTPS_TLS_SessionResumption - with enforced session resumption
 */
public enum Protocol {
	/**
	 * FTP protocol
	 */
	FTP,
	/**
	 * FTPS protocol
	 */
	FTPS,
	/**
	 * FTPS protocol with tls session resumption
	 */
	FTPS_TLS_SessionResumption
}
