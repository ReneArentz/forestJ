package net.forestany.forestj.lib.net.sock.task;

/**
 * communication token class
 */
public class Token {
	
	/* Fields */
	
	@SuppressWarnings("unused")
	private String s_tokenId;
	private boolean b_canceled;
		
	/* Properties */
	
	/**
	 * get canceled flag
	 * 
	 * @return boolean
	 */
	public boolean isCanceled() {
		return this.b_canceled;
	}
	
	/* Methods */
	
	/**
	 * Create token with a unique id
	 */
	public Token() {
		this.s_tokenId = net.forestany.forestj.lib.Helper.generateUUID();
		this.b_canceled = false;
	}
	
	/**
	 * Set canceled flag to true
	 */
	public void cancel() {
		this.b_canceled = true;
	}
}
