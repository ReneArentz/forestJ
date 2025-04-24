package net.forestany.forestj.lib.net.ftp;

/**
 * FTPS client class with enforced tls session resumption.
 * 
 * big issue: https://issues.apache.org/jira/browse/NET-408.
 * In Java 16, it is still possible to restore the situation that existed previously by using the --illegal-access command-line switch to allow general reflective access to the JDK internals. However, in Java 17, the situation changes again: This release removes that command-line switch.
 */
public class TLSSessionResumption extends org.apache.commons.net.ftp.FTPSClient {
	/**
	 * prepare data socket for tls session resumption
	 * 
	 * @param p_o_socket			socket we use ftps
	 * @throws java.io.IOException	exception occurred during preparing data socket
	 */
	@Override
    protected void _prepareDataSocket_(final java.net.Socket p_o_socket) throws java.io.IOException {
        if (p_o_socket instanceof javax.net.ssl.SSLSocket) { /* control socket is SSL */
            /* get ssl session */
            final javax.net.ssl.SSLSession o_session = ((javax.net.ssl.SSLSocket) _socket_).getSession();
            
            if (o_session.isValid()) {
            	/* get ssl context of session */
                final javax.net.ssl.SSLSessionContext o_context = o_session.getSessionContext();
                
                /* set host and port to ssl cache within session context */
                try {
                	/* make cache field and put method accessible */
                    final java.lang.reflect.Field o_sessionHostPortCache = o_context.getClass().getDeclaredField("sessionHostPortCache");
                    o_sessionHostPortCache.setAccessible(true);
                    
                    final Object o_cache = o_sessionHostPortCache.get(o_context);
                    final java.lang.reflect.Method o_method = o_cache.getClass().getDeclaredMethod("put", Object.class, Object.class);
                    o_method.setAccessible(true);
                    
                    /* invoke host name and port on put method for tls session resumption */
                    o_method.invoke(
                		o_cache,
                		String.format("%s:%s", p_o_socket.getInetAddress().getHostName(), String.valueOf(p_o_socket.getPort()))
                        	.toLowerCase(java.util.Locale.ROOT),
                        o_session
                    );
                    
                    /* invoke host address and port on put method for tls session resumption */
                    o_method.invoke(
                		o_cache,
                		String.format("%s:%s", p_o_socket.getInetAddress().getHostAddress(), String.valueOf(p_o_socket.getPort()))
                        	.toLowerCase(java.util.Locale.ROOT),
                        o_session
                    );
                } catch (Exception o_exc) {
                    throw new java.io.IOException(o_exc);
                }
            } else {
                throw new java.io.IOException("invalid SSL session");
            }
        }
    }
	
	/**
	 * constructor calling base class constructor
	 */
	public TLSSessionResumption() {
		super();
	}
	
	/**
	 * constructor with ssl context
	 * 
	 * @param p_o_sslContext ssl context parameter
	 */
	public TLSSessionResumption(javax.net.ssl.SSLContext p_o_sslContext) {
		super(p_o_sslContext);
	}
}
